package org.jbehave.jbel;

public class JBelException extends RuntimeException {
	public JBelException(String message, Exception e) {
		super(message, e);
	}

	public JBelException(String message) {
		super(message);
	}
}
