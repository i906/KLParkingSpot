package my.i906.klparkingspot.di;

import javax.inject.Singleton;

import dagger.Component;
import my.i906.klparkingspot.fragment.BaseFragment;
import my.i906.klparkingspot.ui.BaseActivity;

@Singleton
@Component(modules = {})
public interface Graph {

    void inject(BaseActivity activity);
    void inject(BaseFragment fragment);
}
