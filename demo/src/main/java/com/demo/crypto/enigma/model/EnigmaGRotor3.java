package com.demo.crypto.enigma.model;

public class EnigmaGRotor3 extends AbstractEnigmaRotor{
    private static final char[] SUBSTITUTIONS = {'C', 'J', 'G', 'D', 'P', 'S', 'H', 'K', 'T', 'U', 'R', 'A', 'W', 'Z', 'X', 'F', 'M', 'Y', 'N', 'Q', 'O', 'B', 'V', 'L', 'I', 'E'};
	private static final char[] TURNOVER_CHARACTERS = {'U','W','X','A','E','F','H','K','M','N','R'}; // if rotor steps forward from any one of these positions, the rotor to its left will move forward
    private static char CURRENT_TURNOVER = TURNOVER_CHARACTERS[0];


	public EnigmaGRotor3(AbstractEnigmaRotor leftRotor) {
		this('A', leftRotor);
	}

	public EnigmaGRotor3(char initialPosition, AbstractEnigmaRotor leftRotor) {
		// TODO: validations! leftRotor must be some different kind of rotor!
		super(initialPosition, SUBSTITUTIONS, TURNOVER_CHARACTERS, leftRotor);
	}

    public void setTurnOver(char TurnOverChar)
    {
        CURRENT_TURNOVER = TurnOverChar;
    }

	@Override
	public String toString() {
		return "Enigma I Rotor I, 1930";
	}
}
