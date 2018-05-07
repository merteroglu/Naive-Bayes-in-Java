import java.util.Hashtable;
import java.util.List;

public class NGrams {

    public Hashtable<String,Integer> getNGram(int size , List<String[]> wordList){
        Hashtable<String,Integer> tableNGram = new Hashtable<String, Integer>();

        for(String[] arr : wordList){
            for(String str : arr){
                for (int i = 0; i < str.length() - size + 1; i++) {
                    String subStr = str.substring(i,i+size);
                    try{
                        tableNGram.put(subStr,tableNGram.get(subStr).intValue() + 1);
                    }catch (Exception e){
                        tableNGram.put(subStr,1);
                    }
                }
            }
        }

        return tableNGram;
    }

}
