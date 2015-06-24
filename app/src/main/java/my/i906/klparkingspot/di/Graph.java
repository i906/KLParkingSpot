package my.i906.klparkingspot.di;

import javax.inject.Singleton;

import dagger.Component;
import my.i906.klparkingspot.api.ApiModule;
import my.i906.klparkingspot.fragment.BaseFragment;
import my.i906.klparkingspot.ui.BaseActivity;

@Singleton
@Component(modules = {
        ApiModule.class
})
public interface Graph {

    void inject(BaseActivity activity);
    void inject(BaseFragment fragment);
}
