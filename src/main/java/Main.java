import Bayes.Classification;
import Bayes.Classifier;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Helper helper = new Helper();
        List<DocFile> docFiles = helper.getAllDocFiles();

        Classifier bayes = new Classifier();

        for(DocFile file : docFiles){
            if(file.isLearning()){
                if(file.getnGrams().getTableNGrams().size() > 0)
                bayes.learn(file.getTopic(),file.getnGrams().getTableNGrams());
            }
        }

        Hashtable<String, Hashtable<String,Integer>> statistics = new Hashtable<>();
        statistics.put("ekonomi",new Hashtable<>());
        statistics.put("magazin",new Hashtable<>());
        statistics.put("saglik",new Hashtable<>());
        statistics.put("siyasi",new Hashtable<>());
        statistics.put("spor",new Hashtable<>());

        List<String> topicList = new ArrayList<String>();
        topicList.add("ekonomi");topicList.add("magazin");topicList.add("saglik");topicList.add("siyasi");topicList.add("spor");

        for(String t1 : topicList){
            for(String t2 : topicList){
                statistics.get(t1).put(t2,0);
            }
        }

        bayes.removeLessThan50();

        for(DocFile file : docFiles){
            if(!file.isLearning()){
                if(file.getnGrams().getTableNGrams().size() > 0){
                   Classification c = bayes.classify(file.getnGrams().getTableNGrams());
                    System.out.println(file.getName() + " Bilinen Kategori : " + file.getTopic() + " Bulunan Kategori : " + c.getCategory()
                    + " Olasılık : " + c.getProbability());
                   Integer count = statistics.get(file.getTopic()).get(c.getCategory());
                   if(count == null){
                       statistics.get(file.getTopic()).put(c.getCategory(),1);
                   }else{
                       statistics.get(file.getTopic()).put(c.getCategory(),count.intValue() + 1);
                   }
                }
            }
        }

        Statistics ekonomi = new Statistics();
        ekonomi.setTP(statistics.get("ekonomi").get("ekonomi").intValue());
        ekonomi.setTN(statistics.get("magazin").get("magazin").intValue() + statistics.get("saglik").get("saglik").intValue() + statistics.get("siyasi").get("siyasi").intValue() + statistics.get("spor").get("spor").intValue());
        ekonomi.setFP(statistics.get("magazin").get("ekonomi").intValue() + statistics.get("saglik").get("ekonomi").intValue() + statistics.get("siyasi").get("ekonomi").intValue() + statistics.get("spor").get("ekonomi").intValue() );
        ekonomi.setFN(statistics.get("ekonomi").get("magazin").intValue() + statistics.get("ekonomi").get("saglik").intValue() + statistics.get("ekonomi").get("siyasi").intValue() + statistics.get("ekonomi").get("spor").intValue());
        System.out.println("Ekonomi " + ekonomi.toString());

        Statistics magazin = new Statistics();
        magazin.setTP(statistics.get("magazin").get("magazin").intValue());
        magazin.setTN(statistics.get("ekonomi").get("ekonomi").intValue() + statistics.get("saglik").get("saglik").intValue() + statistics.get("siyasi").get("siyasi").intValue() + statistics.get("spor").get("spor").intValue());
        magazin.setFP(statistics.get("ekonomi").get("magazin").intValue() + statistics.get("saglik").get("magazin").intValue() + statistics.get("siyasi").get("magazin").intValue() + statistics.get("spor").get("magazin").intValue() );
        magazin.setFN(statistics.get("magazin").get("ekonomi").intValue() + statistics.get("magazin").get("saglik").intValue() + statistics.get("magazin").get("siyasi").intValue() + statistics.get("magazin").get("spor").intValue());
        System.out.println("Magazin " + magazin.toString());

        Statistics saglik = new Statistics();
        saglik.setTP(statistics.get("saglik").get("saglik").intValue());
        saglik.setTN(statistics.get("ekonomi").get("ekonomi").intValue() + statistics.get("magazin").get("magazin").intValue() + statistics.get("siyasi").get("siyasi").intValue() + statistics.get("spor").get("spor").intValue());
        saglik.setFP(statistics.get("ekonomi").get("saglik").intValue() + statistics.get("magazin").get("saglik").intValue() + statistics.get("siyasi").get("saglik").intValue() + statistics.get("spor").get("saglik").intValue() );
        saglik.setFN(statistics.get("saglik").get("ekonomi").intValue() + statistics.get("saglik").get("magazin").intValue() + statistics.get("saglik").get("siyasi").intValue() + statistics.get("saglik").get("spor").intValue());
        System.out.println("Saglik " + saglik.toString());

        Statistics siyasi = new Statistics();
        siyasi.setTP(statistics.get("siyasi").get("siyasi").intValue());
        siyasi.setTN(statistics.get("ekonomi").get("ekonomi").intValue() + statistics.get("magazin").get("magazin").intValue() + statistics.get("saglik").get("saglik").intValue() + statistics.get("spor").get("spor").intValue());
        siyasi.setFP(statistics.get("ekonomi").get("siyasi").intValue() + statistics.get("magazin").get("siyasi").intValue() + statistics.get("saglik").get("siyasi").intValue() + statistics.get("spor").get("siyasi").intValue() );
        siyasi.setFN(statistics.get("siyasi").get("ekonomi").intValue() + statistics.get("siyasi").get("magazin").intValue() + statistics.get("siyasi").get("saglik").intValue() + statistics.get("siyasi").get("spor").intValue());
        System.out.println("Siyasi " + siyasi.toString());

        Statistics spor = new Statistics();
        spor.setTP(statistics.get("spor").get("spor").intValue());
        spor.setTN(statistics.get("ekonomi").get("ekonomi").intValue() + statistics.get("magazin").get("magazin").intValue() + statistics.get("saglik").get("saglik").intValue() + statistics.get("siyasi").get("siyasi").intValue());
        spor.setFP(statistics.get("ekonomi").get("spor").intValue() + statistics.get("magazin").get("spor").intValue() + statistics.get("saglik").get("spor").intValue() + statistics.get("siyasi").get("spor").intValue() );
        spor.setFN(statistics.get("spor").get("ekonomi").intValue() + statistics.get("spor").get("magazin").intValue() + statistics.get("spor").get("saglik").intValue() + statistics.get("spor").get("siyasi").intValue());
        System.out.println("Spor " + spor.toString());

    }

}
