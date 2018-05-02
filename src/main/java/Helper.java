import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Helper {

    public enum DataType{
        learning,
        testing
    }

    private String getPathName(int type){
        String pathName = "";
        switch (type) {
            case 0:
                pathName = "ekonomi/";
                break;
            case 1:
                pathName = "magazin/";
                break;
            case 2:
                pathName = "saglik/";
                break;
            case 3:
                pathName = "siyasi/";
                break;
            case 4:
                pathName = "spor/";
                break;
        }
        return pathName;
    }

    private File[] getFilesList(int type, DataType dataType){
        String path = "/Users/mert/documents/1150haber/";
        path += getPathName(type);
        path += dataType + "/";
        File folder = new File(path);
        return folder.listFiles();
    }

    private String[] getFilesWords(File file){
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

    public List<String[]> getAllFilesWords(int topicType , DataType dataType){
        List<String[]> stringList = new ArrayList<String[]>();
        File[] files = getFilesList(topicType,dataType);
        for(File f : files){
            stringList.add(getFilesWords(f));
        }
        return stringList;
    }

}
