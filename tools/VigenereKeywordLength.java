package tools;

import java.util.ArrayList;

public class VigenereKeywordLength {
    public static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

    /* 
    getSequences takes a cyphertext string and breaks it up into a certeain number of sequences based on probable keylengths.
    */
    
    public ArrayList<ArrayList<String>> getSequences(String cypherText) {
        ArrayList<ArrayList<String>> sequences = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < primes[i]; i++) {

            if (cypherText.length()/primes[i] < primes[i]) {
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

    public double getAvgIC(ArrayList<String> sequence) {

        double avgic = 0;

        for (int i = 0; i < sequence.size(); i++) {
            avgic += indexOfCoincidence.getIC(indexOfCoincidence.getCharFreq(sequence.get(i)), sequence.get(i));
        }

        avgic = avgic/sequence.size();

        return avgic;
    }


    public static void main(String[] args) {
        
        String cypherText = "WMXSCHDSLTGKTGRBNKNEkOSEGIEGXHHXORIERCKRRIGRNKXOHQGAAAGGFLMFCSWUSZOIWONNYKEHHRCNBSORNAYEFFETSOGCINIFXIIXETKOKIRRXLRRIOEZIAPDTNCCSUTXXSYTYNEERMMMDHUMRSAMUCHNEFYSDANHTEROCKXSLUNMWAIGSLYNESRRUEEKDIEILTHT";

        cypherText = cypherText.toLowerCase();
        cypherText = cypherText.replaceAll("//s", "");

        VigenereKeywordLength vkl = new VigenereKeywordLength();

        ArrayList<ArrayList<String>> sequences = vkl.getSequences(cypherText);

        for (int i = 0; i < sequences.size(); i++) {

            System.out.println("if key length were: " + sequences.get(i).size());

            for (int j = 0; j < sequences.get(i).size(); j++) {
                System.out.println(sequences.get(i).get(j));
            }

            System.out.println(vkl.getAvgIC(sequences.get(i)));

            //System.out.println(sequences.get(i));
        }
    }
}
