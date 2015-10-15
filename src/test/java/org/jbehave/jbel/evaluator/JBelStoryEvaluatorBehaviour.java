package org.jbehave.jbel.evaluator;

import java.io.IOException;

import org.junit.Test;

public class JBelStoryEvaluatorBehaviour {

	@Test
	public void testResults() throws IOException {
		JBelTestUtils.testStory("testStory");
	}

}