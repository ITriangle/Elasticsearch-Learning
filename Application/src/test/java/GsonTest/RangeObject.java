package GsonTest;

/**
 * Created by seentech on 2017/2/13.
 */
public class RangeObject {
    private String field;
    private String gt;
    private String gte;
    private String lt;
    private String lte;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getGte() {
        return gte;
    }

    public void setGte(String gte) {
        this.gte = gte;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getLte() {
        return lte;
    }

    public void setLte(String lte) {
        this.lte = lte;
    }

    @Override
    public String toString() {
        return "RangeObject{" +
                "field='" + field + '\'' +
                ", gt='" + gt + '\'' +
                ", gte='" + gte + '\'' +
                ", lt='" + lt + '\'' +
                ", lte='" + lte + '\'' +
                '}';
    }
}
