import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordList {
    private List<String> lst;
   // private String guesses = "wordle-allowed-guesses.txt";
    private String words = "wordle-answers-alphabetical.txt";

    public WordList(){
        lst = new ArrayList<String>();
        try{
       //     File file = new File(guesses);
       //     FileReader fr = new FileReader(file);
       //     BufferedReader br = new BufferedReader(fr);
       //     String line = "";
       //     while ((line = br.readLine()) != null){
       //         lst.add(line);
        //    }
            File file2 = new File(words);
            FileReader fr2 = new FileReader(file2);
            BufferedReader br2 = new BufferedReader(fr2);
            String line2 = "";
            while ((line2 = br2.readLine()) != null){
                lst.add(line2);
            }
            br2.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public List<String> getList(){
        return lst;
    }
}
