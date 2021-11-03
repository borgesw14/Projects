package com.demo.crypto.enigma.model;

import com.demo.crypto.enigma.util.Alphabet;

import java.util.Collection;

public class AbwehrMachine {
    private static final char[] DEFAULT_INITIAL_POSITIONS = {'A', 'A', 'A'};
	private final char[] initialPositions;
	//private Collection<SteckerCable> initialSteckeredPairs;

	private AbstractEnigmaRotor slowRotor;
	private AbstractEnigmaRotor middleRotor;
	private AbstractEnigmaRotor fastRotor;
	private AbstractEnigmaReflector reflector;
	//private Steckerbrett steckerbrett;

	public AbwehrMachine() {
		this(DEFAULT_INITIAL_POSITIONS);
	}

    /*
	public AbwehrMachine(char[] initialPositions) {
		this(initialPositions, null);
	}
    */
    
	public AbwehrMachine(char[] initialPositions) {
		this.initialPositions = initialPositions;

		slowRotor = new EnigmaGRotor1(initialPositions[0], null);
		middleRotor = new EnigmaGRotor2(initialPositions[1], slowRotor);
		fastRotor = new EnigmaGRotor3(initialPositions[2], middleRotor);

		reflector = new AbwehrReflector();

	}

	/**
	 * set this Enigma machine's rotors to the given rotor positions. rotorPositions[0] maps to the slow (rightmost) rotor.
	 *
	 * @param rotorPositions
	 */
	public void setRotors(char[] rotorPositions) {
		fastRotor.setOffset(Alphabet.indexOf(rotorPositions[2]));
		middleRotor.setOffset(Alphabet.indexOf(rotorPositions[1]));
		slowRotor.setOffset(Alphabet.indexOf(rotorPositions[0]));
	}

	/**
	 * set up this Enigma machine's steckerboard from with the given SteckerCables. this is not additive; any extant steckers are removed before plugging in the new SteckerCables.
	 *
	 * @param steckeredPairs
	 */
   
     /*
	public void setSteckers(final Collection<SteckerCable> steckeredPairs) {
		steckerbrett.clear();
		steckerbrett.stecker(steckeredPairs);
	}
   */
	
    /**
	 * reset this Enigma machine back to its initial. this does NOT reset the rotors to the A position or clear the steckerboard -- just reset to the state this EnigmaMachine's
	 * instantiation state.
	 */
    
	public void reset() {
		setRotors(initialPositions);

	}
    
	/**
	 * an alias for {@link #encrypt(String)}
	 *
	 * @param cipherText
	 * @return
	 * @see #encrypt(String)
	 */
	public String decrypt(final String cipherText) { // just an alias #encrypt()
		return encrypt(cipherText);
	}

	/**
	 * encrypt the given plain text letter-by-letter based on the current state of this Enigma machine.
	 *
	 * @param plainText
	 * @return
	 * @see #decrypt(String)
	 */
	public String encrypt(final String plainText) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char character : plainText.toUpperCase().toCharArray()) {
			stringBuilder.append(encrypt(character));
		}

		return stringBuilder.toString();
	}

	/**
	 * encipher the given letter based on the current state of this Enigma machine.
	 *
	 * @param input
	 * @return
	 */
	private char encrypt(char input) {
		fastRotor.step();

		int originAbsoluteIndex = -1;
		try {
			originAbsoluteIndex = Alphabet.indexOf(input);
		} catch (NullPointerException nullPointerException) {
			throw new IllegalArgumentException("Messages may only contain letters; letter substitutions should be used in place of numbers and punctuation characters.");
		}

		int indexEnteringReflector = slowRotor.getOutputIndex(middleRotor.getOutputIndex(fastRotor.getOutputIndex(originAbsoluteIndex)));

		int indexLeavingReflector = reflector.getOutputIndex(indexEnteringReflector);

		int indexToLightBoard = fastRotor.getOutputIndexInverse(middleRotor.getOutputIndexInverse(slowRotor.getOutputIndexInverse(indexLeavingReflector)));

		return Alphabet.ALPHABET_ARRAY[indexToLightBoard];
	}

	/**
	 * an alias for {@link #encrypt(char)}
	 *
	 * @param cipherCharacter
	 * @return
	 */
	public char decrypt(char cipherCharacter) { // just an alias for #encrypt()
		return encrypt(cipherCharacter);
	}
}
