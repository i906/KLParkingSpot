package my.i906.klparkingspot.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import my.i906.klparkingspot.BuildConfig;
import my.i906.klparkingspot.util.DateFormatTransformer;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.SimpleXMLConverter;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public DbklApi provideDbklApi() {

        DateFormat format = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer(format));
        Serializer ser = new Persister(m);

        RestAdapter.Builder restAdapter = new RestAdapter.Builder()
                .setConverter(new SimpleXMLConverter(ser, false))
                .setEndpoint("http://dbklpgis.scadatron.net");

        if (BuildConfig.DEBUG) restAdapter.setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("klps-am"));

        return restAdapter.build().create(DbklApi.class);
    }
}
