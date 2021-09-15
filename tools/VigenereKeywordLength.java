package tools;

import java.util.ArrayList;

public class VigenereKeywordLength {
    public static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
    public static int numOfSequences = 15;

    /* 
    getSequences takes a cyphertext string and breaks it up into a certeain number of sequences based on probable keylengths.
    */
    
    public ArrayList<ArrayList<String>> getSequences(String cypherText) {
        ArrayList<ArrayList<String>> sequences = new ArrayList<ArrayList<String>>();
        


        for (int i = 2; i <= numOfSequences; i++) {

            /* if (cypherText.length()/2 < primes[i]) {
                break;
            } */

            //initialize arraylist with strings
            ArrayList<String> sequence = new ArrayList<String>();
            for (int j = 0; j < i; j++) {
                sequence.add("");
            }

            for (int j = 0; j < cypherText.length(); j++) {
                //first index
                int sequenceIndex = (j+1) % i;

                String currentSequence = sequence.remove(sequenceIndex);
                currentSequence = currentSequence + cypherText.charAt(j);
                sequence.add(sequenceIndex, currentSequence);
            }

            sequences.add(sequence);
        }
        return sequences;
    }

    // returns the avg Index of Coincidence for each prospect key length
    public double getAvgIC(ArrayList<String> sequence) {

        double avgic = 0;

        for (int i = 0; i < sequence.size(); i++) {
            avgic += indexOfCoincidence.getIC(indexOfCoincidence.getCharFreq(sequence.get(i)), sequence.get(i));
        }

        avgic = avgic/sequence.size();

        return avgic;
    }

    // return the most probable key lengths based on avera
    public static ArrayList<Integer> getProbableKeyLengths(double[] avgICValues) {
        ArrayList<Integer> probableKeyLengths = new ArrayList<Integer>();
        double total = 0;
        double percentDiffCutoff = .2; //cutt off for percent difference required to be a probable key

        //get the avg IC
        for (int i = 0; i < avgICValues.length; i++) {
            total += avgICValues[i];
        }
        double avgIC = total/avgICValues.length;

        //determine the percent difference between each value and the average
        for (int i = 0; i < avgICValues.length; i++) {
            double percentDiff = (avgICValues[i] - avgIC)/avgIC;

            if (percentDiff >= percentDiffCutoff) {
                probableKeyLengths.add(i+2);
            }
        }

        return probableKeyLengths;
    }

    public static void main(String[] args) {
        
        String cypherText = "vptnvffuntshtarptymjwzirappljmhhqvsubwlzzygvtyitarptyiougxiuydtgzhhvvmumshwkzgstfmekvmpkswdgbilvjljmglmjfqwioiivknulvvfemioiemojtywdsajtwmtcgluysdsumfbieugmvalvxkjduetukatymvkqzhvqvgvptytjwwldyeevquhlulwpkt";

        cypherText = cypherText.toLowerCase();
        cypherText = cypherText.replaceAll("//s", "");

        VigenereKeywordLength vkl = new VigenereKeywordLength();

        ArrayList<ArrayList<String>> sequences = vkl.getSequences(cypherText);

        double avgICValues[] = new double[numOfSequences - 1];

        for (int i = 0; i < sequences.size(); i++) {

            System.out.print("if key length were " + sequences.get(i).size() + ": \t");

            for (int j = 0; j < sequences.get(i).size(); j++) {
                System.out.println(sequences.get(i).get(j));
            }

            System.out.println(vkl.getAvgIC(sequences.get(i)));
            avgICValues[i] = vkl.getAvgIC(sequences.get(i));
            //System.out.println(sequences.get(i));
        }

        ArrayList<Integer> probableKeylengths = getProbableKeyLengths(avgICValues);

        System.out.print("Probable Key Lenghts: ");
        for (Integer integer : probableKeylengths) {
            System.out.print(integer+ " ");
        }


    }
}
