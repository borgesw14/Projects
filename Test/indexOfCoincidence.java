import java.util.Scanner;

public class indexOfCoincidence {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cypherText = sc.nextLine();
        sc.close();

        cypherText.toLowerCase();
        double ic = 0;



        double pArr[] = new double[26];
        for(int i = 0; i < pArr.length; i++){
            pArr[i] = 0;
        }


        //calculate the frequency of each char
        for(int i = 0; i < cypherText.length(); i++){
            pArr[cypherText.charAt(i)- 'a']++;
        }
        for(int i = 0; i < pArr.length; i++){
            pArr[i] = pArr[i]/pArr.length;
        }

        for (double d : pArr) {
            System.out.println(d);
        }

        System.out.println(ic);
        
    }
}
