import java.util.*;

public class NGrams {

    Hashtable<String,Integer> table2Gram;
    Hashtable<String,Integer> table3Gram;


    public NGrams() {
        table2Gram = new Hashtable<String, Integer>();
        table3Gram = new Hashtable<String, Integer>();
    }

    public Hashtable<String,Integer> getNGram(int size , List<String[]> wordList){
        Hashtable<String,Integer> tableNGram;
        if(size == 2) tableNGram = table2Gram; else tableNGram = table3Gram;

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

    public void removeLessThan50(int size){
        Hashtable<String,Integer> tableNGram;
        if(size == 2) tableNGram = table2Gram; else tableNGram = table3Gram;

        Iterator<Map.Entry<String, Integer>> iterator = tableNGram.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            if(entry.getValue() < 50){
                iterator.remove();
            }
        }

    }

    public Hashtable<String, Integer> getTable2Gram() {
        return table2Gram;
    }

    public Hashtable<String, Integer> getTable3Gram() {
        return table3Gram;
    }
}
