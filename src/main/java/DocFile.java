public class DocFile {
    private String name;
    private String topic;
    private String text;
    private NGrams nGrams;
    private boolean isLearning;

    public DocFile(String name,String topic, String text,boolean isLearning) {
        this.name = name;
        this.topic = topic;
        this.text = text;
        this.isLearning = isLearning;
        this.nGrams = new NGrams(this.text.replace(" ","_"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NGrams getnGrams() {
        return nGrams;
    }

    public void setnGrams(NGrams nGrams) {
        this.nGrams = nGrams;
    }

    public boolean isLearning() {
        return isLearning;
    }

    public void setLearning(boolean learning) {
        isLearning = learning;
    }
}
