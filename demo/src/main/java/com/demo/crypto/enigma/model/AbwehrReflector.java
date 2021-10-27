package com.demo.crypto.enigma.model;

public class AbwehrReflector extends AbstractEnigmaReflector{
    private static final char[] SUBSTITUTIONS = {'I', 'M', 'E', 'T', 'C', 'G', 'F', 'R', 'A', 'Y', 'S', 'Q', 'B', 'Z', 'X', 'W', 'L', 'H', 'K', 'D', 'V', 'U', 'P', 'O', 'J', 'N'};

	public AbwehrReflector() {
		super(SUBSTITUTIONS);
	}

	public AbwehrReflector(char[] substitutions) {
		super(substitutions);
	}

	@Override
	public String toString() {
		return "Enigma \"Wide\" B Reflector.";
	}
}
