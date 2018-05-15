package Bayes;

public class Classification {

    private String category;

    private double probability;

    public Classification(String category,double probability) {
        this.category = category;
        this.probability = probability;
    }

    public String getCategory() {
        return category;
    }

    public double getProbability() {
        return probability;
    }

}
