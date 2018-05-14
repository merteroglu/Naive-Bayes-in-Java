public class Statistics {
    private int TP;
    private int TN;
    private int FP;
    private int FN;
    private float precision;
    private float recall;
    private float Fmeasure;

    public Statistics() {
        TP = TN = FP = FN = 0;
        precision = recall = Fmeasure = 0f;
    }

    public int getTP() {
        return TP;
    }

    public int getTN() {
        return TN;
    }

    public int getFP() {
        return FP;
    }

    public int getFN() {
        return FN;
    }


    public void setTP(int TP) {
        try{
            this.TP = TP;
        }catch (Exception e){
            this.TP = 0;
        }

    }

    public void setTN(int TN) {
        try{
            this.TN = TN;
        }catch (Exception e){
            this.TN = 0;
        }

    }

    public void setFP(int FP) {
        try{
            this.FP = FP;
        }catch (Exception e){
            this.FP = 0;
        }

    }

    public void setFN(int FN) {
        try {
            this.FN = FN;
        }catch (Exception e){
            this.FN = 0;
        }
    }

    public float getPrecision() {
        try{
            precision = TP / (TP + FP);
        }catch (Exception e){
            precision = 0;
        }
        return precision;
    }

    public float getRecall() {
        try {
            recall = TP / (TP + FN);
        }catch (Exception e){
            recall = 0;
        }
        return recall;
    }

    public float getFmeasure() {
        float r = getRecall();
        float p = getPrecision();
        try{
            Fmeasure = (2 * r * p) / (p + r);
        }catch (Exception e){
            Fmeasure = 0;
        }
        return Fmeasure;
    }

    @Override
    public String toString() {
        getPrecision();
        getRecall();
        getFmeasure();
        return "{" +
                "TP=" + TP +
                ", TN=" + TN +
                ", FP=" + FP +
                ", FN=" + FN +
                ", precision=" + precision +
                ", recall=" + recall +
                ", Fmeasure=" + Fmeasure +
                '}';
    }
}
