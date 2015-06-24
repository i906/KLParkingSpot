package my.i906.klparkingspot.api;

import my.i906.klparkingspot.model.Pgis;
import retrofit.http.GET;
import rx.Observable;

public interface DbklApi {

    @GET("/dbklpgisd1.xml")
    Observable<Pgis> getParkingSpots();
}
