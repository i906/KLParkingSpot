package my.i906.klparkingspot.ui;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import my.i906.klparkingspot.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Optional
    @InjectView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(layoutResId);
        ButterKnife.inject(this);
        initToolbar();
    }

    protected void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
}
