package Bayes;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Classifier {

    private Dictionary<String,Dictionary<String,Integer>> featureCountPerCategory;
    private Dictionary<String,Integer> totalFeatureCount;
    private Dictionary<String,Integer> totalCategoryCount;

    public Classifier() {
        featureCountPerCategory = new Hashtable<>();
        totalFeatureCount = new Hashtable<>();
        totalCategoryCount = new Hashtable<>();
    }

    public int getCategoriesTotal() {
        int toReturn = 0;
        for (Enumeration<Integer> e = this.totalCategoryCount.elements(); e.hasMoreElements();) {
            toReturn += e.nextElement();
        }
        return toReturn;
    }

    public void incrementFeature(String feature, Integer value){
        Integer count = this.totalFeatureCount.get(feature);
        if(count == null){
            this.totalFeatureCount.put(feature,value.intValue());
        }else{
            this.totalFeatureCount.put(feature,count.intValue() + value.intValue());
        }
    }

    public void incrementCategory(String category) {
        Integer count = this.totalCategoryCount.get(category);
        if (count == null) {
            this.totalCategoryCount.put(category, 0);
            count = this.totalCategoryCount.get(category);
        }
        this.totalCategoryCount.put(category, ++count);
    }

    public int getFeatureCount(String feature, String category) {
        Dictionary<String, Integer> features = this.featureCountPerCategory.get(category);
        if (features == null) return 0;
        Integer count = features.get(feature);
        return (count == null) ? 0 : count.intValue();
    }

    public int getFeatureCount(String feature) {
        Integer count = this.totalFeatureCount.get(feature);
        return (count == null) ? 0 : count.intValue();
    }

    public int getCategoryCount(String category) {
        Integer count = this.totalCategoryCount.get(category);
        return (count == null) ? 0 : count.intValue();
    }

    public float featureProbability(String feature, String category) {
        final float totalFeatureCount = this.getFeatureCount(feature);

        if (totalFeatureCount == 0) {
            return 0;
        } else {
            return this.getFeatureCount(feature, category) / (float) this.getFeatureCount(feature);
        }
    }

    public float featureWeighedAverage(String feature, String category) {
        return this.featureWeighedAverage(feature, category, 1.0f, 0.5f);
    }

    public float featureWeighedAverage(String feature, String category, float weight, float assumedProbability) {
        final float basicProbability = featureProbability(feature, category);

        Integer totals = this.totalFeatureCount.get(feature);
        if (totals == null) totals = 0;
        return (weight * assumedProbability + totals * basicProbability) / (weight + totals);
    }

    public void learn(String category, Hashtable<String,Integer> features) {
        for (String feature : features.keySet()){
            incrementFeature(feature,features.get(feature).intValue());
        }
        this.incrementCategory(category);
    }


}
