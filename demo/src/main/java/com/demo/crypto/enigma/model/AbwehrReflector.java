package com.demo.crypto.enigma.model;

public class AbwehrReflector extends AbstractEnigmaReflector{
    private static final char[] SUBSTITUTIONS = {'R', 'U', 'L', 'Q', 'M', 'Z', 'J', 'S', 'Y', 'G', 'O', 'C', 'E', 'T', 'K', 'W', 'D', 'A', 'H', 'N', 'B', 'X', 'P', 'V', 'I', 'F'};
	private char initalPos;
	public AbwehrReflector() {
		super(SUBSTITUTIONS);
	}

	public AbwehrReflector(char initialPosition)
	{
		super(SUBSTITUTIONS);
		this.initalPos = initialPosition;
	}

	public AbwehrReflector(char[] substitutions) {
		super(substitutions);
	}

	public void setInitialPos(char init)
	{
		this.initalPos = init;
	}


	@Override
	public String toString() {
		return "Enigma \"Wide\" B Reflector.";
	}
}
