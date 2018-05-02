import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;

import java.util.ArrayList;
import java.util.List;

public class ZemberekUtils {
    TurkishMorphology turkishMorphology;
    TurkishSpellChecker turkishSpellChecker;

    public ZemberekUtils() {
        try{
            turkishMorphology = TurkishMorphology.createWithDefaults();
            turkishSpellChecker = new TurkishSpellChecker(turkishMorphology);
        }catch (Exception e){

        }
    }

    public String[] normalization(String[] words){
        List<String> stringList = new ArrayList<String>();
        for (String s : words){
            try {
                stringList.add(turkishSpellChecker.suggestForWord(s).get(0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return (String[]) stringList.toArray();
    }

}
