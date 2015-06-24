package my.i906.klparkingspot.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(strict=false)
public class Mall implements Comparable<Mall> {

    @Element
    private String ID;

    @Element
    private String NAME;

    @Element
    private String TYPE;

    @Element
    private int LOT;

    @Element
    private String STATE;

    @Element
    private int HEALTHY;

    @Element
    private Date DATETIME;

    public String getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    public int getLot() {
        return LOT;
    }

    public Date getLastRefreshed() {
        return DATETIME;
    }

    @Override
    public int compareTo(Mall another) {
        if (DATETIME == null) return 0;
        return DATETIME.compareTo(another.DATETIME);
    }

    @Override
    public String toString() {
        return "Mall{" +
                "ID='" + ID + '\'' +
                ", NAME='" + NAME + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", LOT=" + LOT +
                ", STATE='" + STATE + '\'' +
                ", HEALTHY=" + HEALTHY +
                ", DATETIME=" + DATETIME +
                '}';
    }
}
