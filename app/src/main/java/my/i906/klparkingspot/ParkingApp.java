package my.i906.klparkingspot;

import android.app.Application;
import android.content.Context;

import my.i906.klparkingspot.di.DaggerGraph;
import my.i906.klparkingspot.di.Graph;

public class ParkingApp extends Application {

    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mGraph = DaggerGraph.builder()
                .build();
    }

    public static Graph graph(Context context) {
        return ((ParkingApp) context.getApplicationContext()).mGraph;
    }
}
