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
    public ArrayList<Character> encodedMsg = new ArrayList<Character>();
    public ArrayList<String> testKeys = new ArrayList<String>();

    public static void main(String[] args) {
        String fileInPath = "";
        String fileOutName = "";
        String DesiredOut = "";
        String decoded = "";

        VigenereDecoder iDecoder = new VigenereDecoder();
        VigenereCracker vc = new VigenereCracker();
        VigenereKeywordLength vkl = new VigenereKeywordLength();
        Discriminator discriminator = new Discriminator();
        Scanner usrIn = new Scanner(System.in);
        Driver driver = new Driver();

        // read file in
        fileInPath = args[1];
        reader(fileInPath, driver);

        // set file out
        DesiredOut = args[2];

        // get key lengths
        ArrayList<ArrayList<String>> sequences = vkl.getSequences(vc.msgToString(driver.encodedMsg));

        double avgICValues[] = new double[VigenereKeywordLength.numOfSequences - 1];

        for (int i = 0; i < sequences.size(); i++) {
            avgICValues[i] = vkl.getAvgIC(sequences.get(i));
        }

        ArrayList<Integer> probableKeylengths = vkl.getProbableKeyLengths(avgICValues);

        // Run discriminator
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

            for (ArrayList<String> keys : keyList) {
                driver.testKeys = keys;
                for (int i = 0; i < driver.testKeys.size(); i++) {
                    decoded = iDecoder.decoder(driver.encodedMsg, driver.testKeys.get(i));
                    fileOutName = DesiredOut;
                    // check if file name has .txt typing if not, add .txt
                    if (!fileOutName.contains(".txt"))
                        fileOutName = fileOutName + ".txt";
                    fileWrite(driver.testKeys.get(i), decoded, "result/" + fileOutName + "key" + (i + 1));
                }
            }

            usrIn.close();

        }

    }

    public static void reader(String filePath, Driver driver) {
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
                        driver.encodedMsg.add(Character.toLowerCase(character));
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
