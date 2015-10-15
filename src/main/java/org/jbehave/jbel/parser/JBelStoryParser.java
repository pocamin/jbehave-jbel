package org.jbehave.jbel.parser;

import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.jbel.evaluator.JBelStoryEvaluator;

public class JBelStoryParser implements StoryParser {
	private final StoryParser storyParser;

	public JBelStoryParser(StoryParser storyParser) {
		this.storyParser = storyParser;
	}

	public Story parseStory(String storyAsText) {
		return storyParser.parseStory(new JBelStoryEvaluator(storyAsText).toString());
	}

	public Story parseStory(String storyAsText, String storyPath) {
		return storyParser.parseStory(new JBelStoryEvaluator(storyAsText).toString(), storyPath);
	}
}
