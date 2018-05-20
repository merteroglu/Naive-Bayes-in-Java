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
        for (Enumeration<Integer> e = totalCategoryCount.elements(); e.hasMoreElements();) {
            toReturn += e.nextElement();
        }
        return toReturn;
    }

    public void incrementFeature(String feature,String category,int value){
        Dictionary<String, Integer> features = featureCountPerCategory.get(category);
        if (features == null) {
            featureCountPerCategory.put(category, new Hashtable<>());
            features = featureCountPerCategory.get(category);
        }

        Integer count = features.get(feature);
        if (count == null) {
            features.put(feature, value);
        }else{
            features.put(feature, count.intValue() + value);
        }

        Integer totalCount = totalFeatureCount.get(feature);
        if (totalCount == null) {
            totalFeatureCount.put(feature, value);
        }else{
            totalFeatureCount.put(feature, totalCount.intValue() + value);
        }

    }

    public void incrementCategory(String category) {
        Integer count = totalCategoryCount.get(category);
        if (count == null) {
            totalCategoryCount.put(category, 1);
        }else{
            totalCategoryCount.put(category, ++count);
        }
    }

    public void removeLessThan50(){
        List<String> topicList = new ArrayList<String>();
        topicList.add("ekonomi");topicList.add("magazin");topicList.add("saglik");topicList.add("siyasi");topicList.add("spor");
        Map<String, Integer> table = (Map<String, Integer>) totalFeatureCount;
        Iterator<Map.Entry<String, Integer>> iterator = table.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            if(entry.getValue() < 50){
                for(String topic : topicList){
                    featureCountPerCategory.get(topic).remove(entry.getKey());
                }
                iterator.remove();
            }
        }
    }

    public int getFeatureCount(String feature, String category) {
        Dictionary<String, Integer> features = featureCountPerCategory.get(category);
        if (features == null) return 0;
        Integer count = features.get(feature);
        return (count == null) ? 0 : count.intValue();
    }

    public int getFeatureCount(String feature) {
        Integer count = totalFeatureCount.get(feature);
        return (count == null) ? 0 : count.intValue();
    }

    public Set<String> getCategories() {
        return ((Hashtable<String, Integer>) totalCategoryCount).keySet();
    }

    public int getCategoryCount(String category) {
        Integer count = totalCategoryCount.get(category);
        return (count == null) ? 0 : count.intValue();
    }

    public double featureProbability(String feature, String category) {
        final double totalFeatureCount = this.getFeatureCount(feature);

        if (totalFeatureCount == 0) {
            return 0;
        } else {
            return (double) getFeatureCount(feature, category) / (double) getFeatureCount(feature);
        }
    }

    public double featureAverage(String feature, String category) {
        final double basicProbability = featureProbability(feature, category);

        Integer totals = totalFeatureCount.get(feature);
        if (totals == null) totals = 0;

        return (1+(totals * basicProbability)) / (1+totals);
    }

    public void learn(String category, Hashtable<String,Integer> features) {
        for (String feature : features.keySet()){
            incrementFeature(feature,category,features.get(feature).intValue());
            incrementCategory(category);
        }
    }

    private double featuresProbabilitySum(Hashtable<String,Integer> features, String category) {
        double product = 0.0f;
        for (String feature : features.keySet()){
            product += (features.get(feature).intValue() * Math.log(featureAverage(feature, category)));
        }
        return product;
    }

    private double categoryProbability(Hashtable<String,Integer> features, String category) {
        return ((double) getCategoryCount(category) / (double) getCategoriesTotal())
                * featuresProbabilitySum(features, category);
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

        for (String category : getCategories())
            probabilities.add(new Classification(category, categoryProbability(features, category)));

        return probabilities;
    }

    public Classification classify(Hashtable<String,Integer> features) {
        SortedSet<Classification> probabilities = categoryProbabilities(features);

        if (probabilities.size() > 0) {
            return probabilities.last();
        }
        return null;
    }

}
