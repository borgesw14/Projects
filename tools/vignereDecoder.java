package tools;

import java.util.Hashtable;
import java.util.ArrayList;

class vignereDecoder{    

    public String decoder(ArrayList<Character> encodedMsg,String key)
    {
            Hashtable<Character, Integer> NumTable = new Hashtable<Character, Integer>();
            Hashtable<Integer, Character> EngTable = new Hashtable<Integer, Character>();
            int count = 0;
            int kIndex = 0;
            String decodedText = "";
            Integer newLetter = 0;
            
            //populate table with english letter value pairs
            for(char ch = 'a'; ch<='z'; ch++)
            {
                NumTable.put(ch,count);
                EngTable.put(count,ch);
                count++;
            }



                for(int i = 0; i < encodedMsg.size(); i++)
                {
                    //check for punctuation and spaces, decode if it is a letter, add character to decoded text if it is punctuation
                    if(NumTable.containsKey(encodedMsg.get(i)))
                    {    
                         //get integer value of character in our encoded message at the current index and int value of character in our key at given key index. Subtract both to get int value of the decoded letter.
                        newLetter = NumTable.get(encodedMsg.get(i)) - NumTable.get(key.charAt(kIndex));

                        //if our letter is negative we add 26 to that letter and reassign the value
                        if(newLetter < 0)
                            newLetter = 26 + newLetter;
                        

                        //add decoded already decoded text and new letter together by grabbing the character assigned to the new value we obtained in previous steps.
                        decodedText = decodedText + EngTable.get(newLetter);
                    
                        //reset our key index if we have reached the final letter to repeat the key.
                        if(kIndex < key.length()-1)
                            kIndex++;
                        else
                            kIndex = 0;
                    }
                    else
                    {
                        decodedText = decodedText + encodedMsg.get(i);
                        continue;
                    }
                    //make sure you can find the decoded character in our english table
                }

                return decodedText;
    }

    public String msgToString(ArrayList<Character> encodedMsg){
        String eMsgString = "";
        for(int i = 0; i < encodedMsg.size(); i++)
        {
            eMsgString = eMsgString + encodedMsg.get(i);
        }
        return eMsgString;
    }

    public ArrayList<String> findKey(ArrayList<Character> encodedMsg){
        String[] chipers = VignereCracker.generateCeaserCiphers(msgToString(encodedMsg));
        double[] chiStats = new double[26];
        ArrayList<String> probableKeys = new ArrayList<String>();
        int count = 0;

        for(int i = 0; i <chipers.length; i++)
        {
            chiStats[i] = VignereCracker.getChiSqrd(chipers[i]);
        }

        //sort chiStats
        for (int i = 0; i < chiStats.length-1; i++)
            for (int j = 0; j < chiStats.length-i-1; j++)
                if (chiStats[j] > chiStats[j+1])
                {
                    // swap arr[j+1] and arr[j]
                    double temp = chiStats[j];
                    chiStats[j] = chiStats[j+1];
                    chiStats[j+1] = temp;

                    String sTemp = chipers[j];
                    chipers[j] = chipers[j+1];
                    chipers[j+1] = sTemp;
                }

        while(probableKeys.size() <=5)
        {
            probableKeys.add(chipers[count]);
            count++;
        }

        return probableKeys;
    }

    
}