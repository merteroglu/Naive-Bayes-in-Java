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

    private String[] normalization(String[] words){
        ArrayList<String> stringList = new ArrayList<String>();
        for (String s : words){
            try {
                stringList.add(turkishSpellChecker.suggestForWord(s).get(0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return  stringList.toArray(new String[0]);
    }

    private String[] getStems(String[] words){
        List<String> stringList = new ArrayList<String>();
        for(String s : words){
            try{
                stringList.add(turkishMorphology.analyze(s).get(0).getStems().get(0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return stringList.toArray(new String[0]);
    }

    public String[] preProcess(String[] words){
        String[] normalized = normalization(words);
        return getStems(normalized);
    }

}
