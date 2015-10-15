package org.jbehave.jbel.evaluator;

import java.io.IOException;

import org.jbehave.jbel.JBelException;
import org.junit.Assert;
import org.junit.Test;

public class JBelScenarioEvaluatorTest {

	@Test
	public void testExpressionProcessionScenario() throws IOException {
		JBelTestUtils.testScenario("expressionProcessionScenario");
	}

	@Test
	public void testComplexExpressionParsingScenario() throws IOException {
		JBelTestUtils.testScenario("expressionParsing");
	}

	@Test
	public void testTextProcessingScenario() throws IOException {
		JBelTestUtils.testScenario("textProcessingScenario");
	}

	@Test
	public void testExpressionInVariable() throws IOException {
		JBelTestUtils.testScenario("expressionInVariableScenario");
	}

	@Test(expected = JBelException.class)
	public void testNotCorrectStringAssignment() throws IOException {
		try {
			JBelTestUtils.testScenario("notCorrectStringAssignment");
		}
		catch (JBelException e) {
			Assert.assertEquals("Parsing Error : Invalid expression starting with : =\"I'm not ab", e.getMessage());
			throw e;
		}
	}

	@Test(expected = JBelException.class)
	public void testNotCorrectExpressionAssignment() throws IOException {
		try {
			JBelTestUtils.testScenario("notCorrectExpressionAssignment");
		}
		catch (JBelException e) {
			Assert.assertEquals("Parsing Error : Invalid expression starting with : ${ (function", e.getMessage());
			throw e;
		}
	}

	@Test(expected = JBelException.class)
	public void testNotCorrectExpressionEvaluation() throws IOException {
		try {
			JBelTestUtils.testScenario("notCorrectExpressionEvaluation");
		}
		catch (JBelException e) {
			Assert.assertEquals("Parsing Error : Invalid expression starting with : ${ (function", e.getMessage());
			throw e;
		}
	}

	@Test(expected = JBelException.class)
	public void testJavascriptException() throws IOException {
		try {
			JBelTestUtils.testScenario("javascriptException");
		}
		catch (JBelException e) {
			Assert.assertEquals("cannot process javascript :\n" + "var $_0=test;\n", e.getMessage());
			throw e;
		}
	}

}