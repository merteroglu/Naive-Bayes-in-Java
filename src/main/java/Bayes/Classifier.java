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
        }

        features.put(feature, count.intValue() + value);

        Integer totalCount = this.totalFeatureCount.get(feature);
        if (totalCount == null) {
            this.totalFeatureCount.put(feature, value);
            totalCount = this.totalFeatureCount.get(feature);
        }
        this.totalFeatureCount.put(feature, totalCount.intValue() + value);
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

        Map<String, Integer> tableEkonomi = (Map<String, Integer>) featureCountPerCategory.get("ekonomi");
        Map<String, Integer> tableMagazin = (Map<String, Integer>) featureCountPerCategory.get("magazin");
        Map<String, Integer> tableSaglik = (Map<String, Integer>) featureCountPerCategory.get("saglik");
        Map<String, Integer> tableSiyasi = (Map<String, Integer>) featureCountPerCategory.get("siyasi");
        Map<String, Integer> tableSpor = (Map<String, Integer>) featureCountPerCategory.get("spor");

        Iterator<Map.Entry<String, Integer>> iteratorEkonomi = tableEkonomi.entrySet().iterator();
        while(iteratorEkonomi.hasNext()){
            Map.Entry<String, Integer> entry = iteratorEkonomi.next();
            if(entry.getValue() < 50){
                iteratorEkonomi.remove();
            }
        }

        Iterator<Map.Entry<String, Integer>> iteratorMagazin = tableMagazin.entrySet().iterator();
        while(iteratorMagazin.hasNext()){
            Map.Entry<String, Integer> entry = iteratorMagazin.next();
            if(entry.getValue() < 50){
                iteratorMagazin.remove();
            }
        }

        Iterator<Map.Entry<String, Integer>> iteratorSaglik = tableSaglik.entrySet().iterator();
        while(iteratorSaglik.hasNext()){
            Map.Entry<String, Integer> entry = iteratorSaglik.next();
            if(entry.getValue() < 50){
                iteratorSaglik.remove();
            }
        }

        Iterator<Map.Entry<String, Integer>> iteratorSiyasi = tableSiyasi.entrySet().iterator();
        while(iteratorSiyasi.hasNext()){
            Map.Entry<String, Integer> entry = iteratorSiyasi.next();
            if(entry.getValue() < 50){
                iteratorSiyasi.remove();
            }
        }

        Iterator<Map.Entry<String, Integer>> iteratorSpor = tableSpor.entrySet().iterator();
        while(iteratorSpor.hasNext()){
            Map.Entry<String, Integer> entry = iteratorSpor.next();
            if(entry.getValue() < 50){
                iteratorSpor.remove();
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
            incrementFeature(feature,category,features.get(feature).intValue());
        }
        this.incrementCategory(category);
    }

    private float featuresProbabilityProduct(Collection<String> features, String category) {
        float product = 1.0f;
        for (String feature : features)
            product *= this.featureWeighedAverage(feature, category);
        return product;
    }

    private float categoryProbability(Collection<String> features, String category) {
        return ((float) this.getCategoryCount(category) / (float) this.getCategoriesTotal())
                * featuresProbabilityProduct(features, category);
    }

    private SortedSet<Classification> categoryProbabilities(Collection<String> features) {

        SortedSet<Classification> probabilities =
                new TreeSet<Classification>(
                        new Comparator<Classification>() {

                            public int compare(Classification o1, Classification o2) {
                                int toReturn = Float.compare(o1.getProbability(), o2.getProbability());
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
        SortedSet<Classification> probabilites = this.categoryProbabilities(features.keySet());

        if (probabilites.size() > 0) {
            return probabilites.last();
        }
        return null;
    }




}
