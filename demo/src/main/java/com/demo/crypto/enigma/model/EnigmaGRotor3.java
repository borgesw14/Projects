package com.demo.crypto.enigma.model;

public class EnigmaGRotor3 extends AbstractEnigmaRotor{
    private static final char[] SUBSTITUTIONS = {'U', 'Q', 'N', 'T', 'L', 'S', 'Z', 'F', 'M', 'R', 'E', 'H', 'D', 'P', 'X', 'K', 'I', 'B', 'V', 'Y', 'G', 'J', 'C', 'W', 'O', 'A'};
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
