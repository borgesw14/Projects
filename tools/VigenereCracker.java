package tools;

import java.util.ArrayList;
import java.util.Collections;

public class VigenereCracker {

    public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /*
    character frequencies for the english language extracted from:
    http://cs.wellesley.edu/~fturbak/codman/letterfreq.html
    */
    public static double[] charFreqEng = {0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074};


    /*
        generates all 26 possible ceaser ciphers from the sequence
        input: an encrypted ceaser cipher sequence
        output: all 26 possible ciphers
    */
    public String[] generateCeaserCiphers(String sequence) {
        String[] ceasersCiphers = new String[26];

        for (int i = 0; i < 26; i++) {
            StringBuilder result = new StringBuilder();
            for (char character : sequence.toCharArray()) {

                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + i) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);

            }

            ceasersCiphers[i] = result.toString();
        }
        return ceasersCiphers;
    }

    /*
        A method to determine the chi-squared statistic of a string of text in relation to english.
        input: a string of text
        output: chi-squared statistic
    */
    public double getChiSqrd(String currentCipher) {
        double chisqrd; //chisqrd values 
        double keyLength = currentCipher.length();
        double letterfrequency = 0;
        double expectedAppearance;
        double[] chiArray = new double[26];
        int count = 0;


        for(char ch = 'a'; ch <= 'z'; ch++){
            chisqrd = 0;

            for(int i = 0; i < currentCipher.length(); i++){
                // letterfrequency
                if(currentCipher.charAt(i) == ch){
                  letterfrequency++;
                }
              }
      
              // expectedAppearance = keylength * probability
              expectedAppearance = keyLength * charFreqEng[count];
      
              // chisqrd = (letterfrequency - expectedAppearance)^2/expectedAppearance
              chisqrd = ((letterfrequency - expectedAppearance) * (letterfrequency - expectedAppearance))/expectedAppearance;
      
              chiArray[count] = chisqrd;
      
              for(int y = 0; y < currentCipher.length(); y++){
                // removing letters that are already calculated
                if(currentCipher.charAt(y) == ch){
                  currentCipher = currentCipher.replace(ch, ' ');
                }
              }
              count++;
        }
        double sum = 0;
        for (double d : chiArray) {
            sum += d;
        }
        return sum;
    }

    /*
    A method to build a string repressenting a probable key from an array of strings representing current probable strings

    */
    public ArrayList<String> keyBuilder(ArrayList<String> currentKeys, ArrayList<Integer> possibleChars) {

        ArrayList<String> newCurrentKeys = new ArrayList<String>();
        for (int i = 0; i < possibleChars.size(); i++) {

            for (int j = 0; j < currentKeys.size(); j++) {
                newCurrentKeys.add(currentKeys.get(j).concat(Character.toString('a' + possibleChars.get(i))));
            }
        }
        return newCurrentKeys;
    }
    
    public String msgToString(ArrayList<Character> encodedMsg){
        String eMsgString = "";
        for(int i = 0; i < encodedMsg.size(); i++)
        {
            eMsgString = eMsgString + encodedMsg.get(i);
        }
        return eMsgString;
    }

    public ArrayList<Integer> getLowestChiIndex(String[] ciphers, double[] chiStats /* ArrayList<Character> encodedMsg */){
        /* VigenereCracker mainCracker = new VigenereCracker();
        String[] ciphers = mainCracker.generateCeaserCiphers(msgToString(encodedMsg));
        
        double[] chiStats = new double[26]; */
        ArrayList<String> tempCiphers = new ArrayList<String>();
        ArrayList<Integer> originalCipherIndex = new ArrayList<Integer>();
        int count = 0;
        Collections.addAll(tempCiphers, ciphers);

        /* for(int i = 0; i < ciphers.length; i++)
        {
            chiStats[i] = mainCracker.getChiSqrd(ciphers[i]);
        } */

        //sort chiStats
        for (int i = 0; i < chiStats.length-1; i++)
            for (int j = 0; j < chiStats.length-i-1; j++)
                if (chiStats[j] > chiStats[j+1])
                {
                    // swap arr[j+1] and arr[j]
                    double temp = chiStats[j];
                    chiStats[j] = chiStats[j+1];
                    chiStats[j+1] = temp;

                    String sTemp = ciphers[j];
                    ciphers[j] = ciphers[j+1];
                    ciphers[j+1] = sTemp;
                }

        while(originalCipherIndex.size() <=2)
        {
            originalCipherIndex.add(tempCiphers.indexOf(ciphers[count]));
            count++;
        }

        return originalCipherIndex;
    }

    /* 
        a method that accepts Arraylist of sequences and returns an arraylist of possible keys
    */
    public ArrayList<String> getKeys(ArrayList<String> sequences) {
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<String[]> ciphers = new ArrayList<String[]>();
        ArrayList<double[]> chiStats = new ArrayList<double[]>();
        ArrayList<ArrayList<Integer>> possibleCharIndexs = new ArrayList<ArrayList<Integer>>();

        //generates the ceasers ciphers for each sequence
        for (String seq : sequences) {
            ciphers.add(generateCeaserCiphers(seq));
        }

        for (int i = 0; i < ciphers.size(); i++) {
            double[] currentChiStats = new double[ciphers.get(i).length];

            for (int j = 0; j < ciphers.get(i).length; j++) {
                currentChiStats[j] = getChiSqrd(ciphers.get(i)[j]);
            }

            chiStats.add(currentChiStats);
        }

        for (int i = 0; i < ciphers.size(); i++) {
            possibleCharIndexs.add(getLowestChiIndex(ciphers.get(i), chiStats.get(i)));
        }

        for (ArrayList<Integer> arrayList : possibleCharIndexs) {
            keys = keyBuilder(keys, arrayList);
        }
        

        return keys;
    }

    public static void main(String[] args) {
        String str = "zkrolyhvlqdslqhdssohs";
        VigenereCracker vc = new VigenereCracker();

        String[] ciphers = vc.generateCeaserCiphers(str);

        for (int i = 0; i < ciphers.length; i++) {
            vc.getChiSqrd(ciphers[i]);
        }
    }
}
