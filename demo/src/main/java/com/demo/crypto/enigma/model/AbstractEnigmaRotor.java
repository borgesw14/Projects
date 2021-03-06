package com.demo.crypto.enigma.model;

import com.demo.crypto.enigma.util.Alphabet;
import org.apache.commons.lang3.ArrayUtils;

/**
 * representation of a generic Enigma rotor. all rotors have 26 indices, each mapped to one other index. unlike reflectors, rotors are stepped with
 * each enciphered letter to change the overall machine state.
 *
 * @author Mike O'Donnell  github.com/mikerodonnell
 * @see <a href="https://en.wikipedia.org/wiki/Enigma_rotor_details">https://en.wikipedia.org/wiki/Enigma_rotor_details</a>
 */
public abstract class AbstractEnigmaRotor implements Steppable {

	protected final char[] substitutions;
	protected  char turnoverCharacter;
	protected  char[] turnoverArray;
	protected int offset; // offset of 0 = A position, offset of 1 = B position, offset of 25 = Z position.
	protected AbstractEnigmaRotor leftRotor;

	public AbstractEnigmaRotor(char initialPosition, char[] substitutions, char turnoverCharacter, AbstractEnigmaRotor leftRotor) {
		this.substitutions = substitutions;
		this.turnoverCharacter = turnoverCharacter;
		this.offset = Alphabet.indexOf(initialPosition);
		this.leftRotor = leftRotor;
	}

	public AbstractEnigmaRotor(char initialPosition, char[] substitutions, char[] turnoverArray, AbstractEnigmaRotor leftRotor) {
		this.substitutions = substitutions;
		this.turnoverArray = turnoverArray;
		this.offset = Alphabet.indexOf(initialPosition);
		this.leftRotor = leftRotor;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public AbstractEnigmaRotor getLeftRotor() {
		return leftRotor;
	}

	/**
	 * get the index (0 through 25) of the letter of the alphabet that this rotor outputs for the given input index, when current is running forward (right to left)
	 * through the Enigma circuit.
	 *
	 * @param absoluteInputIndex
	 * @return
	 */
	public int getOutputIndex(int absoluteInputIndex) {
		// the value of absoluteInputIndex assumes a static 'A' rotor position where 0 is at the top. if the rotor has any offset currently,
		// we have to add the offset.
		int effectiveInputIndex = ((absoluteInputIndex + getOffset()) % 26); // we have to mod 26 here because the added offset might have put us over 25
		char substituteCharacter = substitutions[effectiveInputIndex];
		return ((26 + Alphabet.indexOf(substituteCharacter) - getOffset()) % 26); // now we have to add 26 and mod 26 again because the subtracted offset might have put us under 0
	}

	/**
	 * get the index (0 through 25) of the letter of the alphabet that this rotor outputs for the given input index, when current is running backward (left to right)
	 * through the Enigma circuit.
	 *
	 * @param absoluteInputIndex
	 * @return
	 */
	public int getOutputIndexInverse(int absoluteInputIndex) {
		int effectiveInputIndex = (absoluteInputIndex + getOffset()) % 26; // we have to mod 26 here because the added offset might have put us over 25
		char originalCharacter = Alphabet.ALPHABET_ARRAY[effectiveInputIndex];
		return ((26 + ArrayUtils.indexOf(substitutions, originalCharacter) - getOffset()) % 26); // now we have to add
																									// 26 and mod 26
																									// again because the
																									// subtracted offset
																									// might have put us
																									// under 0
	}

	/**
	 * modifies the state of this rotor by advancing it one position. depending on the value of turnoverCharacter, this may also advance the rotor to the left of
	 * this one.
	 */
	public void step() {
		offset++;
		int tempTurnOver = 0;

		if(turnoverArray.length != 0)
		{
			for(int i =0; i < turnoverArray.length; i++)
			{
				if(offset == Alphabet.indexOf(turnoverArray[i]) + 1)
					tempTurnOver = Alphabet.indexOf(turnoverArray[i]) + 1;
			}
		}

		if ( (leftRotor != null && (offset == (Alphabet.indexOf(turnoverCharacter) + 1))) || (leftRotor !=null && (offset == tempTurnOver))) {
			leftRotor.step();
		}
		

		if (offset > 25) {
			offset = 0;
		}
	}
}
