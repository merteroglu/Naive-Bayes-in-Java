import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        NGrams nGrams = new NGrams();
        String[] arry1 = new String[]{"mert","mert","eroglu"};
        String[] arry2 = new String[]{"test","deneme","kou","mert"};
        String[] arry3 = new String[]{"telefon","bilgisayar","java","dene","oglu"};
        List<String[]> list = new ArrayList<String[]>();
        list.add(arry1);
        list.add(arry2);
        list.add(arry3);
        Hashtable<String,Integer> tableNGram = nGrams.getNGram(2,list);


    }

}
