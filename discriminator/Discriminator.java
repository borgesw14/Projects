package discriminator;
import java.util.ArrayList;

import tools.*;


/**
 * Discriminator
 */
public class Discriminator {

    public static void main(String[] args) {
        String cypherText = "vptnvffuntshtarptymjwzirappljmhhqvsubwlzzygvtyitarptyiougxiuydtgzhhvvmumshwkzgstfmekvmpkswdgbilvjljmglmjfqwioiivknulvvfemioiemojtywdsajtwmtcgluysdsumfbieugmvalvxkjduetukatymvkqzhvqvgvptytjwwldyeevquhlulwpkt";

        cypherText = cypherText.toLowerCase();
        cypherText = cypherText.replaceAll("//s", "");

        VigenereKeywordLength vkl = new VigenereKeywordLength();

        ArrayList<ArrayList<String>> sequences = vkl.getSequences(cypherText);

        double avgICValues[] = new double[VigenereKeywordLength.numOfSequences - 1];

        for (int i = 0; i < sequences.size(); i++) {

            for (int j = 0; j < sequences.get(i).size(); j++) {
                System.out.println(sequences.get(i).get(j));
            }

            System.out.print("if key length were " + sequences.get(i).size() + ": \t");
            System.out.println(vkl.getAvgIC(sequences.get(i)));
            avgICValues[i] = vkl.getAvgIC(sequences.get(i));
            //System.out.println(sequences.get(i));
        }

        ArrayList<Integer> probableKeylengths = vkl.getProbableKeyLengths(avgICValues);

        if (probableKeylengths.size() > 0) {
            System.out.print("Probable Key Lenghts: ");
            for (Integer integer : probableKeylengths) {
                System.out.print(integer+ " ");
            }
        } else {
            System.out.println("This is not Vigenere Cipher-text");
        }

        


    }
}