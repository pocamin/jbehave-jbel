package org.jbehave.jbel.evaluator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jbehave.jbel.JBelException;

class JBelScenarioEvaluator implements JBelEvaluator {
	private final static Pattern EL_PATTERN = Pattern.compile("(^@|(?<=[^\\\\])@)([^\\s]*\\s*=\\s*|\\{)");
	private final static String VAR_PREFIX = "$_"; //"" + (char)0 + (char)1 + (char)2 + (char)4;
	private final List<Expression> expressions = new LinkedList<Expression>();
	private final StringBuilder preParsed = new StringBuilder();
	private String parsed = "";
	private final String initialScenario;

	protected JBelScenarioEvaluator(String scenario) {
		this(scenario, new ArrayList<Expression>());
	}

	protected JBelScenarioEvaluator(String scenario, List<Expression> headersExpressions) {
		expressions.addAll(headersExpressions);
		initialScenario = scenario;
		firstPass();
		secondPass();
	}

	private void firstPass() {
		String rest = initialScenario;
		while (rest.length() > 0) {
			Matcher matcher = EL_PATTERN.matcher(rest);
			if (matcher.find()) {
				int start = matcher.start();
				append(rest.substring(0, start)).appendNextId();
				rest = rest.substring(start);
				String expressionAsString = matcher.group();

				if ("@{".equals(expressionAsString)) {
					expressionAsString = findExpression(rest.substring(2));
					rest = rest.substring(expressionAsString.length() + 3);
					addExpression(new Evaluation(expressionAsString, getNextId()));
				} else {
					rest = parseAssignment(rest, expressionAsString);
				}
			} else {
				append(rest);
				rest = "";
			}
		}
	}

	private String parseAssignment(String rest, String expressionAsString) {
		String variableName = expressionAsString.trim().substring(1, expressionAsString.trim().length() - 1).trim();
		rest = rest.substring(expressionAsString.length());
		if (rest.charAt(0) == '\"') {
			String value = '"' + findStringValue(rest.substring(1)) + '"';
			addExpression(new Assignment(variableName, value, getNextId()));
			return rest.substring(value.length());
		} else if (rest.charAt(0) == '{') {
			String value = findExpression(rest.substring(1));
			addExpression(new Assignment(variableName, value, getNextId()));
			return rest.substring(value.length() + 2);
		} else {
			String value = findNextValue(rest);
			addExpression(new Assignment(variableName, value, getNextId()));
			return rest.substring(value.length());
		}

	}

	private String findStringValue(String rest) {
		char oldChar = 0;
		String toReturn = "";
		for (char c : rest.toCharArray()) {
			if (c == '"' && oldChar != '\\') {
				return toReturn;
			}
			toReturn += c;
			oldChar = c;
		}
		throw new JBelException("Parsing Error : Invalid expression starting with : =\"" + rest.substring(0, Math.min(rest.length(), 10)));
	}

	private String findNextValue(String rest) {
		return rest.split("[\\s\\|]")[0];
	}

	private int getNextId() {
		return expressions.size();
	}

	private JBelScenarioEvaluator append(String string) {
		preParsed.append(string);
		return this;
	}

	private JBelScenarioEvaluator appendNextId() {
		preParsed.append(VAR_PREFIX).append(getNextId());
		return this;
	}

	private void addExpression(Expression expression) {
		expressions.add(expression);
	}

	@Override
	public String toString() {
		return "original : \n" + //
				initialScenario + "\n\n" + //
				"javaScript : \n" + //
				getJavascript() + '\n' + //
				"first pass : \n" + preParsed + "\n\n" + //
				"final pass : \n" + getResult();
	}

	protected String getJavascript() {
		StringBuilder sb = new StringBuilder();
		for (Expression e : expressions) {
			sb.append(e.toString()).append('\n');
		}
		return sb.toString();
	}

	public String getResult() {
		return parsed;
	}

	private void secondPass() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByExtension("js");
		try {
			engine.eval(getJavascript());
		}
		catch (ScriptException e) {
			throw new JBelException("cannot process javascript :\n" + getJavascript(), e);
		}

		parsed = preParsed.toString();
		for (int i = 0; i < expressions.size(); i++) {
			parsed = parsed.replace(VAR_PREFIX + i, engine.get(VAR_PREFIX + i).toString());
		}

	}

	private String findExpression(String rest) {
		int open = 0;
		StringBuilder value = new StringBuilder();
		for (char c : rest.toCharArray()) {
			if (c == '{') {
				open++;
			} else if (c == '}') {
				open--;
				if (open < 0) {
					return value.toString();
				}
			}
			value.append(c);
		}

		throw new JBelException("Parsing Error : Invalid expression starting with : ${" + rest.substring(0, Math.min(rest.length(), 10)));
	}

	abstract static class Expression {
		final int id;

		protected Expression(int id) {
			this.id = id;
		}
	}

	private static class Evaluation extends Expression {
		final String toEvaluate;

		private Evaluation(String toEvaluate, int id) {
			super(id);
			this.toEvaluate = toEvaluate;

		}

		@Override
		public String toString() {
			return "var " + VAR_PREFIX + id + "=" + toEvaluate + ";";
		}
	}

	private static class Assignment extends Expression {
		final String name;
		final String value;

		private Assignment(String name, String value, int id) {
			super(id);
			this.name = name.trim();
			this.value = value.trim();
		}

		@Override
		public String toString() {
			return "var " + name + "=" + value + ";\n" +
					"var " + VAR_PREFIX + id + "=" + value + ";";
		}
	}

	public List<Expression> getExpressions() {
		return new ArrayList<Expression>(expressions);
	}
}