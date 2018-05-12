
public class Main {

    public static void main(String[] args) {
        //Helper helper = new Helper();
        //List<DocFile> docFiles = helper.getAllDocFiles();
        ZemberekUtils zemberekUtils = new ZemberekUtils();
        System.out.println( zemberekUtils.turkishSpellChecker.check("zorro"));
    }

}
