import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Helper {

    public enum DataType{
        learning,
        testing
    }

    public void splitFilesRandomly(int topicType){
        String path = "/Users/mert/documents/1150haber/";
        path += getPathName(topicType) + "/";
        String learningPath = path + "learning/";
        String testingPath = path + "testing/";

        File folder = new File(path);
        File learningFolder = new File(learningPath);
        File testingFolder = new File(testingPath);
        File[] files = folder.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.shuffle(fileList);
        int sizePercent75 = (fileList.size() * 75) / 100;
        int i;
        if(!learningFolder.exists()){
            learningFolder.mkdirs();
        }
        if(!testingFolder.exists()){
            testingFolder.mkdirs();
        }
        for (i = 0; i < sizePercent75+1; i++) {
            fileList.get(i).renameTo(new File(learningFolder + "/" + fileList.get(i).getName()));
        }

        for(;i<fileList.size();i++){
            fileList.get(i).renameTo(new File(testingFolder + "/" + fileList.get(i).getName()));
        }

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

    private String[] removeStopWords(String[] words){
        List<String> list = Arrays.asList(words);
        for(String s : list){
            if(StaticValues.stopWords.contains(s)){
                list.remove(s);
            }
        }
        return (String[]) list.toArray();
    }

    public List<String[]> getAllFilesWords(int topicType , DataType dataType){
        List<String[]> stringList = new ArrayList<String[]>();
        File[] files = getFilesList(topicType,dataType);
        for(File f : files){
            stringList.add(removeStopWords(getFilesWords(f)));
        }
        return stringList;
    }

}
