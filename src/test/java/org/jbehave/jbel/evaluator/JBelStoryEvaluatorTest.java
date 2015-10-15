package org.jbehave.jbel.evaluator;

import java.io.IOException;

import org.junit.Test;

public class JBelStoryEvaluatorTest {

	@Test
	public void testResults() throws IOException {
		JBelTestUtils.testStory("testStory");
	}

}