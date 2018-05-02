import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;

import java.io.IOException;

public class NaiveBayes {


    public static void main(String[] args) {
        try {
            TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
            TurkishSpellChecker spellChecker = new TurkishSpellChecker(morphology);

            System.out.println("Give suggestions.");
            String[] toSuggest = {"Kraamanda", "okumuştk", "yapbileceksen", "oukyamıyorum"};
            for (String s : toSuggest) {
                System.out.println(s + " -> " + spellChecker.suggestForWord(s));
            }

        }catch (IOException e){

        }

    }
}
