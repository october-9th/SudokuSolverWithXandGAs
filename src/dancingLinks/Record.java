package dancingLinks;

public class Record {
    double min;
    double max;
    double avg;

    public Record(double min, double max, double avg) {
        this.max = max;
        this.min = min;
        this.avg = avg;
    }
    public Record(int min, int max, double avg) {
        this.max = max;
        this.min = min;
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "Record [min=" + min + ", max=" + max + ", avg=" + avg + "]";
    }
}
