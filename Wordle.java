import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.stream.*;
import java.util.regex.*;

public class Wordle{
    public static void main(String[]args){
        String mystery = "disco";
        WordList test = new WordList();
        List<String> lst = test.getList();

        List<String> tst = lst;
        for (int i = 0; i < 5; i++){
            String guess = "";
            if (i == 0){
                guess = "adieu";
            }else{
                int len = tst.size();
                int rand = (int) Math.ceil(Math.random() * len) - 1;
                guess = tst.get(rand);
            }
            String combo = guess(mystery, guess);
            if (combo.equals("WIN")){
                System.out.println(guess);
                System.out.println("You Win!");
                break;
            }
            tst.remove(guess);
            System.out.println(guess);
            System.out.println(combo);
            tst = alterList(lst, combo, guess);
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
        List<Character> contain = new ArrayList<>();
        for (int i = 0; i < combo.length(); i++){
            if (combo.charAt(i) == 'G'){
                regex.append(guess.charAt(i));
            }else if (combo.charAt(i) == 'R'){
                regex.append('.');
                block.add(guess.charAt(i));
            }else if (combo.charAt(i) == 'Y'){
                regex.append('.');
                contain.add(guess.charAt(i));
            }
        }

        String pat = regex.toString();
        List<String> ret = new ArrayList<>();
        outer: for (String word : lst){
            if (!word.matches(pat)){
                continue outer;
            }
            for (Character ch : contain){
                if (word.indexOf(ch) == -1){
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
