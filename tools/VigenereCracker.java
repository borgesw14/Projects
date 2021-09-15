package tools;

public class VigenereCracker {
    
    public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /* 
    character frequencies for the english language extracted from:
    http://cs.wellesley.edu/~fturbak/codman/letterfreq.html
    */
    public static double[] charFreqEng = {0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074};

    public static String[] generateCeaserCiphers(String sequence) {
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

    public static void main(String[] args) {
        String str = "zkrolyhvlqdslqdsoh";
        String[] ciphers = generateCeaserCiphers(str);

        for (String string : ciphers) {
            System.out.println(string);
        }
    }
}
