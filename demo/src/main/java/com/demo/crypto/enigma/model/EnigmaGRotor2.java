package com.demo.crypto.enigma.model;

public class EnigmaGRotor2 extends AbstractEnigmaRotor{
    private static final char[] SUBSTITUTIONS = {'H', 'O', 'Z', 'G', 'P', 'J', 'T', 'M', 'O', 'B', 'L', 'N', 'C', 'I', 'F', 'D', 'Y', 'A', 'W', 'V', 'E', 'U', 'S', 'R', 'K', 'X'};
	private static final char[] TURNOVER_CHARACTERS = {'S','T','V','Y','Z','A','C','D','F','G','H','K','M','N','Q'}; // if rotor steps forward from any one of these positions, the rotor to its left will move forward
    private static char CURRENT_TURNOVER = TURNOVER_CHARACTERS[0];


	public EnigmaGRotor2(AbstractEnigmaRotor leftRotor) {
		this('A', leftRotor);
	}

	public EnigmaGRotor2(char initialPosition, AbstractEnigmaRotor leftRotor) {
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
