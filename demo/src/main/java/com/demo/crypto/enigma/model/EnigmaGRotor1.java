package com.demo.crypto.enigma.model;

public class EnigmaGRotor1 extends AbstractEnigmaRotor{
    private static final char[] SUBSTITUTIONS = {'L', 'P', 'G', 'S', 'Z', 'M', 'H', 'A', 'E', 'O', 'Q', 'K', 'V', 'X', 'R', 'F', 'Y', 'B', 'U', 'T', 'N', 'I', 'C', 'J', 'D', 'W'};
	private static final char[] TURNOVER_CHARACTERS = {'S','U','V','W','Z','A','B','C','E','F','G','I','K','L','O','P','Q'}; // if rotor steps forward from any one of these positions, the rotor to its left will move forward
    private static char CURRENT_TURNOVER = TURNOVER_CHARACTERS[0];


	public EnigmaGRotor1(AbstractEnigmaRotor leftRotor) {
		this('A', leftRotor);
	}

	public EnigmaGRotor1(char initialPosition, AbstractEnigmaRotor leftRotor) {
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
