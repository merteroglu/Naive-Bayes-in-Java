import Bayes.Classification;
import Bayes.Classifier;

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


        for(DocFile file : docFiles){
            if(!file.isLearning()){
                if(file.getnGrams().getTableNGrams().size() > 0){
                   Classification c = bayes.classify(file.getnGrams().getTableNGrams());
                    System.out.println(file.getName() + " Bilinen Kategori : " + file.getTopic() + " Bulunan Kategori : " + c.getCategory()
                    + " Olasılık : " + c.getProbability());
                }
            }
        }

    }

}
