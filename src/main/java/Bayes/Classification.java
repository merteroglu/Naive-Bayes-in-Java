package Bayes;

public class Classification {

    private String category;

    private float probability;

    public Classification(String category,float probability) {
        this.category = category;
        this.probability = probability;
    }

    public String getCategory() {
        return category;
    }

    public float getProbability() {
        return probability;
    }

}
