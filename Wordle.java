import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.stream.*;
import java.util.regex.*;
import java.util.HashMap;

public class Wordle{
    public static void main(String[]args){
        if (args.length < 1){ // error checking arg
            System.out.println("Please provide an argument");
            return;
        }
        if (args[0].length() != 5){ // error checking arg
            System.out.println("Please provide a valid 5-letter word");
            return;
        }

        String mystery = args[0];
        WordList test = new WordList(); // pulling full list of valid words from WordList class
        List<String> lst = test.getList();

        if (!lst.contains(args[0])){ // error checking arg
            System.out.println("Please provide a valid 5-letter word");
            return;
        }

        List<String> tst = lst;
        String guess;
        for (int i = 0; i < 5; i++){ // allowing six guesses
            if (i == 0){
                guess = "adieu"; // starting guess, change as wanted
            }else{
                int len = tst.size();
                int rand = (int) Math.ceil(Math.random() * len) - 1; // generating rand from list
                guess = tst.get(rand);
            }
            String combo = guess(mystery, guess); 
            if (combo.equals("WIN")){ // WIN case
                System.out.println(guess);
                System.out.println("You Win!");
                break;
            }
            tst.remove(guess);
            System.out.println(guess);
            System.out.println(combo);
            tst = alterList(tst, combo, guess); // no win, remove all invalid words by new rules
        }
      //  for (String word : tst){
      //      System.out.println(word);
      //  }
    }

    public static String guess(String mystery, String guess){
        if (guess.equals(mystery)){
            return "WIN";
        }
        StringBuilder sb = new StringBuilder();
        HashSet<Character> hs = new HashSet<>();
        for (int i = 0; i < mystery.length(); i++){
            hs.add(mystery.charAt(i));
        }
        for (int i = 0; i < guess.length(); i++){
            if (guess.charAt(i) == mystery.charAt(i)){
                sb.append('G');
            }else if(hs.contains(guess.charAt(i))){
                sb.append('Y');
            }else{
                sb.append('R');
            }
        }
        return sb.toString();
    }

    public static List<String> alterList(List<String> lst, String combo, String guess){
        StringBuilder regex = new StringBuilder();
        List<Character> block = new ArrayList<>();
        HashMap<Character, Integer> contain = new HashMap<>();
        for (int i = 0; i < combo.length(); i++){
            if (combo.charAt(i) == 'G'){
                regex.append(guess.charAt(i));
            }else if (combo.charAt(i) == 'R'){
                regex.append('.');
                block.add(guess.charAt(i));
            }else if (combo.charAt(i) == 'Y'){
                regex.append('.');
                contain.put(guess.charAt(i), i);
            }
        }

        String pat = regex.toString();
        List<String> ret = new ArrayList<>();
        outer: for (String word : lst){
            if (!word.matches(pat)){
                continue outer;
            }
            for (Character ch : contain.keySet()){
                if (word.indexOf(ch) == -1 || word.indexOf(ch) == contain.get(ch)){
                    continue outer;
                }
            }
            for (Character ch : block){
                if (word.indexOf(ch) != -1){
                    continue outer;
                }
            }

            ret.add(word);
        }
        
        return ret;
    }
}
