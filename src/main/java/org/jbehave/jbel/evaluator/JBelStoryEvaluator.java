package org.jbehave.jbel.evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.jbel.JBelException;

public class JBelStoryEvaluator implements JBelEvaluator {
	private final static Pattern SCENARIO_PATTERN = Pattern.compile("Scenario:");

	private StringBuilder result = new StringBuilder();

	public JBelStoryEvaluator(String originalStory) {

		Matcher matcher = SCENARIO_PATTERN.matcher(originalStory);
		if (!matcher.find()) {
			throw new JBelException("No scenario in provided file");
		}

		JBelScenarioEvaluator header = new JBelScenarioEvaluator(originalStory.substring(0, matcher.start()));
		result.append(header.getResult());

		int from = matcher.start();
		while (matcher.find()) {
			int to = matcher.start();
			result.append(new JBelScenarioEvaluator(originalStory.substring(from, to), header.getExpressions()).getResult());
			from = to;
		}
		result.append(new JBelScenarioEvaluator(originalStory.substring(from, originalStory.length()), header.getExpressions()).getResult());

	}

	public String getResult() {
		return result.toString();
	}
}