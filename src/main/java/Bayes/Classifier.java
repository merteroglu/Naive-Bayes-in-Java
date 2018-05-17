package Bayes;

import java.util.*;

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

    public void incrementFeature(String feature,String category,int value){
        Dictionary<String, Integer> features = this.featureCountPerCategory.get(category);
        if (features == null) {
            this.featureCountPerCategory.put(category, new Hashtable<>());
            features = this.featureCountPerCategory.get(category);
        }

        Integer count = features.get(feature);
        if (count == null) {
            features.put(feature, value);
            count = features.get(feature);
        }else{
            features.put(feature, count.intValue() + value);
        }

        Integer totalCount = this.totalFeatureCount.get(feature);
        if (totalCount == null) {
            this.totalFeatureCount.put(feature, value);
            totalCount = this.totalFeatureCount.get(feature);
        }else{
            this.totalFeatureCount.put(feature, totalCount.intValue() + value);
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

    public void removeLessThan50(){
        Map<String, Integer> table = (Map<String, Integer>) totalFeatureCount;
        Iterator<Map.Entry<String, Integer>> iterator = table.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            if(entry.getValue() < 50){
                iterator.remove();
            }
        }

        List<String> topicList = new ArrayList<String>();
        topicList.add("ekonomi");topicList.add("magazin");topicList.add("saglik");topicList.add("siyasi");topicList.add("spor");

        for (String topic : topicList) {
            Map<String, Integer> table2 = (Map<String, Integer>) featureCountPerCategory.get(topic);
            Iterator<Map.Entry<String, Integer>> iterator2 = table2.entrySet().iterator();
            while(iterator2.hasNext()){
                Map.Entry<String, Integer> entry = iterator2.next();
                if(entry.getValue() < 50){
                    iterator2.remove();
                }
            }
        }

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

    public Set<String> getCategories() {
        return ((Hashtable<String, Integer>) this.totalCategoryCount).keySet();
    }

    public int getCategoryCount(String category) {
        Integer count = this.totalCategoryCount.get(category);
        return (count == null) ? 0 : count.intValue();
    }

    public double featureProbability(String feature, String category) {
        final double totalFeatureCount = this.getFeatureCount(feature);

        if (totalFeatureCount == 0) {
            return 0;
        } else {
            return this.getFeatureCount(feature, category) / (double) this.getFeatureCount(feature);
        }
    }

    public double featureWeighedAverage(String feature, String category) {
        double weight = 1.0f;
        double assumedProbability = 0.5f;
        final double basicProbability = featureProbability(feature, category);

        Integer totals = this.totalFeatureCount.get(feature);
        if (totals == null) totals = 0;
        return ((weight * assumedProbability) + (totals * basicProbability)) / (weight + totals);
    }

    public void learn(String category, Hashtable<String,Integer> features) {
        for (String feature : features.keySet()){
            incrementFeature(feature,category,features.get(feature).intValue());
        }
        this.incrementCategory(category);
    }

    private double featuresProbabilityProduct(Hashtable<String,Integer> features, String category) {
        double product = 1.0f;
        for (String feature : features.keySet()){
            for (int i = 0; i < features.get(feature).intValue(); i++) {
                product += Math.log( this.featureWeighedAverage(feature, category));
            }
        }
        return product;
    }

    private double categoryProbability(Hashtable<String,Integer> features, String category) {
        return ((double) this.getCategoryCount(category) / (double) this.getCategoriesTotal())
                * featuresProbabilityProduct(features, category);
    }

    private SortedSet<Classification> categoryProbabilities(Hashtable<String,Integer> features) {

        SortedSet<Classification> probabilities =
                new TreeSet<Classification>(
                        new Comparator<Classification>() {

                            public int compare(Classification o1, Classification o2) {
                                int toReturn = Double.compare(o1.getProbability(), o2.getProbability());
                                if ((toReturn == 0) && !o1.getCategory().equals(o2.getCategory()))
                                    toReturn = -1;
                                return toReturn;
                            }
                        });

        for (String category : this.getCategories())
            probabilities.add(new Classification(category, this.categoryProbability(features, category)));
        return probabilities;
    }

    public Classification classify(Hashtable<String,Integer> features) {
        SortedSet<Classification> probabilities = this.categoryProbabilities(features);

        if (probabilities.size() > 0) {
            return probabilities.last();
        }
        return null;
    }

}
