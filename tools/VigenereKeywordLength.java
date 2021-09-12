package tools;

import java.util.ArrayList;

public class VigenereKeywordLength {
    public static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

    public ArrayList<ArrayList<String>> getSequences(String cypherText) {
        int halfpoint = cypherText.length()/2;
        ArrayList<ArrayList<String>> sequences = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < primes.length; i++) {

            if (halfpoint < primes[i]) {
                break;
            }
            //initialize arraylist with strings
            ArrayList<String> sequence = new ArrayList<String>();
            for (int j = 0; j < primes[i]; j++) {
                sequence.add("");
            }

            for (int j = 0; j < cypherText.length(); j++) {
                //first index
                int sequenceIndex = (j+1) % primes[i];

                String currentSequence = sequence.remove(sequenceIndex);
                currentSequence = currentSequence + cypherText.charAt(j);
                sequence.add(sequenceIndex, currentSequence);
            }

            sequences.add(sequence);
        }
        return sequences;
    }

    public static void main(String[] args) {
        
        String cypherText = "vptzmdrttzysubxaykkwcjmgjmgpwreqeoiivppalrujtlrzpchljftupucywvsyiuuwufirtaxagfpaxzxjqnhbfjvqibxzpotciiaxahmevmmagyczpjxvtndyeuknulvvpbrptygzilbkeppyetvmgpxuknulvjhzdtgrgapygzrptymevppaxygkxwlvtiawlrdmipweqbhpqgngioirnxwhfvvawpjkglxamjewbwpvvmafnlojalh";

        VigenereKeywordLength vkl = new VigenereKeywordLength();

        ArrayList<ArrayList<String>> sequences = vkl.getSequences(cypherText);

        for (int i = 0; i < sequences.size(); i++) {
            // for (int j = 0; j < sequences.get(i).size(); j++) {
            //     System.out.println(sequences.get(i).get(j));
            // }

            System.out.println(sequences.get(i));
        }
    }
}
