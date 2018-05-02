import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Helper {

    public File[] getLearningFilesList(int type){
        String path = "/Users/mert/documents/1150haber/";

        switch (type){
            case 0:
                path += "ekonomi/";
                break;
            case 1:
                path += "magazin/";
                break;
            case 2:
                path += "saglik/";
                break;
            case 3:
                path += "siyasi/";
                break;
            case 4:
                path += "spor/";
                break;
        }

        path += "learning/";
        File folder = new File(path);
        return folder.listFiles();
    }

    public File[] getTestingFilesList(int type){
        String path = "/Users/mert/documents/1150haber/";

        switch (type){
            case 0:
                path += "ekonomi/";
                break;
            case 1:
                path += "magazin/";
                break;
            case 2:
                path += "saglik/";
                break;
            case 3:
                path += "siyasi/";
                break;
            case 4:
                path += "spor/";
                break;
        }

        path += "testing/";
        File folder = new File(path);
        return folder.listFiles();
    }

    public String[] getFilesWords(File file){
        String lines = "";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null){
                lines += currentLine; // TODO new line karakteri gerekebilir
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lines.split("\\s+");
    }
}
