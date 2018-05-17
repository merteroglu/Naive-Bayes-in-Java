import java.io.*;
import java.util.*;


public class Helper {

    public enum DataType{
        learning,
        testing
    }

    private void splitFilesRandomly(int topicType){
        String path = "/Users/mert/documents/1150haber/";
        path += getPathName(topicType);
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

    private String getTopicName(int type){
        switch (type) {
            case 0:
                return "ekonomi";
            case 1:
                return "magazin";
            case 2:
                return "saglik";
            case 3:
                return "siyasi";
            case 4:
                return "spor";
        }
        return "notopic";
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
            //BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"ISO-8859-9"));
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null){
                lines += currentLine; // TODO new line karakteri gerekebilir
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lines.replaceAll("[^a-zA-Z öçşığüÖÇŞİĞÜ]","").toLowerCase().split("\\s+");
    }

    private String[] removeStopWords(String[] words){
        List<String> list = new LinkedList<String>(Arrays.asList(words));

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String str = iterator.next();
            if(StaticValues.stopWords.contains(str)){
                iterator.remove();
            }
        }
        return list.toArray(new String[0]);
    }

    private String mergedWords(String[] words){
        String mergedString = "";
        for(String word : words){
            mergedString += word + " ";
        }
        return mergedString;
    }


    public List<DocFile> getAllDocFiles(){
        List<DocFile> docFiles = new ArrayList<>();
        ZemberekUtils zemberekUtils = new ZemberekUtils();

        for (int i = 0; i < 5; i++) { // topics
            splitFilesRandomly(i);
            File[] filesLearning = getFilesList(i,DataType.learning);
            File[] filesTesting = getFilesList(i,DataType.testing);

            for(File f : filesLearning){
                String[] wordsInF = getFilesWords(f);
                wordsInF = removeStopWords(wordsInF);
                wordsInF = zemberekUtils.preProcess(wordsInF); // normalized and stems
                String mergedWordsInF = mergedWords(wordsInF);
                DocFile docFile = new DocFile(f.getName(),getTopicName(i),mergedWordsInF,true);
                docFiles.add(docFile);
            }

            for(File f : filesTesting){
                String[] wordsInF = getFilesWords(f);
                wordsInF = removeStopWords(wordsInF);
                wordsInF = zemberekUtils.preProcess(wordsInF); // normalized and stems
                String mergedWordsInF = mergedWords(wordsInF);
                DocFile docFile = new DocFile(f.getName(),getTopicName(i),mergedWordsInF,false);
                docFiles.add(docFile);
            }
            System.out.println(getTopicName(i) + " Tamamlandı");
        }
        return docFiles;
    }

}
