package driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import discriminator.Discriminator;
import tools.VigenereCracker;
import tools.VigenereDecoder;
import tools.VigenereKeywordLength;

public class Driver {
    public static ArrayList<Character> encodedMsg = new ArrayList<Character>();
    public static ArrayList<String> testKeys = new ArrayList<String>();

    public static void main(String[] args) {
        String fileInPath = "";
        String fileOutName = "";
        String DesiredOut = "";
        String decoded = "";
        VigenereDecoder iDecoder = new VigenereDecoder();
        VigenereCracker vc = new VigenereCracker();
        Scanner usrIn = new Scanner(System.in);

        // prompt user for file
        System.out.println("enter encoded cypher text path:");
        fileInPath = usrIn.nextLine();
        // read file in
        reader(fileInPath);

        // prompt user for outfile name
        System.out.println("enter desired output file name:");
        DesiredOut = usrIn.nextLine();

        // get key lengths
        VigenereKeywordLength vkl = new VigenereKeywordLength();
        ArrayList<ArrayList<String>> sequences = vkl.getSequences(vc.msgToString(encodedMsg));

        double avgICValues[] = new double[VigenereKeywordLength.numOfSequences - 1];

        for (int i = 0; i < sequences.size(); i++) {

            /*
             * for (int j = 0; j < sequences.get(i).size(); j++) {
             * System.out.println(sequences.get(i).get(j)); }
             * 
             * System.out.print("if key length were " + sequences.get(i).size() + ": \t");
             * System.out.println(vkl.getAvgIC(sequences.get(i)));
             */
            avgICValues[i] = vkl.getAvgIC(sequences.get(i));
            // System.out.println(sequences.get(i));
        }

        ArrayList<Integer> probableKeylengths = vkl.getProbableKeyLengths(avgICValues);

        // Run discriminator

        Discriminator discriminator = new Discriminator();
        String result = discriminator.Discriminate(probableKeylengths);

        if (result.equals(Discriminator.CODE_ENIGMA)) {
            // decipher for enigma

        } else {
            // Decipher for Vigenere cipher

            // for each probable key length add an arraylist of keys to keyList
            ArrayList<ArrayList<String>> keyList = new ArrayList<ArrayList<String>>();
            for (Integer integer : probableKeylengths) {
                for (int i = 0; i < sequences.size(); i++) {
                    /*
                     * if the number of sequences matches the probable key length pass the sequences
                     * array to vc.getkeys and expect an array of probable keys in return
                     */
                    if (sequences.get(i).size() == integer) {

                        keyList.add(vc.getKeys(sequences.get(i)));
                    }
                }
            }

            for (ArrayList<String> arrayList : keyList) {
                for (String string : arrayList) {
                    System.out.println(string);
                }
            }

            for (ArrayList<String> keys : keyList) {
                testKeys = keys;
                for (int i = 0; i < testKeys.size(); i++) {
                    decoded = iDecoder.decoder(encodedMsg, testKeys.get(i));
                    fileOutName = DesiredOut;
                    // check if file name has .txt typing if not, add .txt
                    if (!fileOutName.contains(".txt"))
                        fileOutName = fileOutName + ".txt";
                    fileWrite(testKeys.get(i), decoded, "result/" + fileOutName);
                }
            }

            usrIn.close();

        }

    }

    public static void reader(String filePath) {
        File f = new File(filePath); // Creation of File Descriptor for input file
        FileReader fr;
        try {
            fr = new FileReader(f); // Creation of File Reader object
            BufferedReader br = new BufferedReader(fr); // Creation of BufferedReader object
            int c = 0;
            try {
                while ((c = br.read()) != -1) // Read char by Char
                {
                    char character = (char) c; // converting integer to char

                    if (Character.isLetter(character))
                        encodedMsg.add(Character.toLowerCase(character));
                    else
                        continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void fileWrite(String key, String plainText, String fileOutPath) {

        try {
            File myObj = new File(fileOutPath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(fileOutPath);
            myWriter.write("KEY:" + key + "\n" + "\n"); // adds key to top of file
            myWriter.write(plainText); // writes out decoded text
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
