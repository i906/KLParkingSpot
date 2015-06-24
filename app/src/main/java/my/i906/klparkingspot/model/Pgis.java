package my.i906.klparkingspot.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict=false)
public class Pgis {

    @ElementList(entry="MALL", inline=true)
    private List<Mall> malls;

    public List<Mall> getMalls() {
        return malls;
    }

    @Override
    public String toString() {
        return "Pgis{" +
                "malls=" + malls +
                '}';
    }
}
