package org.jbehave.jbel.evaluator;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;

public class JBelTestUtils {

	public static void testScenario(String scenarioName) throws IOException {
		assertEqual(scenarioName, new JBelScenarioEvaluator(readJBel(scenarioName)));
	}

	public static void testStory(String storyName) throws IOException {
		assertEqual(storyName, new JBelStoryEvaluator(readJBel(storyName)));
	}

	private static String readJBel(String name) throws IOException {
		return readString(name, "jbel");
	}

	private static String readString(String name, String type) throws IOException {
		InputStream s = JBelScenarioEvaluatorTest.class.getResourceAsStream("/" + type + "/" + name + '.' + type);
		try {
			java.util.Scanner scanner = new java.util.Scanner(s).useDelimiter("\\A");
			return scanner.next();
		}
		finally {
			if (s != null) {
				s.close();
			}
		}
	}

	private static void assertEqual(String name, JBelEvaluator jBelEvaluator) throws IOException {
		try {
			Assert.assertEquals(readString(name, "story").trim(), jBelEvaluator.getResult().trim());
		}
		catch (NullPointerException e) {
			System.out.println("error");
			System.out.println(jBelEvaluator.getResult());
			throw e;
		}

	}
}
