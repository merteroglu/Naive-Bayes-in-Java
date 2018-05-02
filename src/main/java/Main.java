import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;

import java.io.IOException;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        try {
            TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
            TurkishSpellChecker spellChecker = new TurkishSpellChecker(morphology);

            String[] toSuggest = {"sporcuların", "kitaplar", "yapbileceksen", "okuyorum"};
            String wordm = spellChecker.suggestForWord("nesela").get(0);
            List<WordAnalysis> wordAnalysisList = morphology.analyze(wordm);
            System.out.println(wordAnalysisList.get(0).getStems().get(0));


        }catch (IOException e){

        }

    }
}
