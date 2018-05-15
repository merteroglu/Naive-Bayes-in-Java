import java.util.*;

public class NGrams {

    private Hashtable<String,Integer> table2Gram;
    private Hashtable<String,Integer> table3Gram;


    public NGrams(String words) {
        table2Gram = new Hashtable<String, Integer>();
        table3Gram = new Hashtable<String, Integer>();
        getNGram(2,words);
        getNGram(3,words);
        //removeLessThan50(2);
        //removeLessThan50(3);
    }

    public Hashtable<String,Integer> getNGram(int size , String words){
        Hashtable<String,Integer> tableNGram;
        if(size == 2) tableNGram = table2Gram; else tableNGram = table3Gram;

        for (int i = 0; i < words.length() - size + 1; i++) {
            String subStr = words.substring(i,i+size);
            try{
                tableNGram.put(subStr,tableNGram.get(subStr).intValue()+1);
            }catch (Exception e){
                tableNGram.put(subStr,1);
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

    public Hashtable<String,Integer> getTableNGrams(){
        Hashtable<String,Integer> tableMerged = new Hashtable<>();
        tableMerged.putAll(table2Gram);
        tableMerged.putAll(table3Gram);
        return tableMerged;
    }

}
