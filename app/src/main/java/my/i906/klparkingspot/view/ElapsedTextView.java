package my.i906.klparkingspot.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Date;

import my.i906.klparkingspot.R;

public class ElapsedTextView extends TextView {

    public static final long UNIT_SECOND = 1000;
    public static final long UNIT_MINUTE = 60 * UNIT_SECOND;
    public static final long UNIT_HOUR = 60 * UNIT_MINUTE;
    public static final long UNIT_DAY = 24 * UNIT_HOUR;

    protected boolean isUpdateTaskRunning = false;
    protected String mPrefix = "";
    protected Handler mHandler;
    protected ElapsedUpdater mUpdater;
    protected Date mDate;

    public ElapsedTextView(Context context) {
        this(context, null);
    }

    public ElapsedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElapsedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new Handler();
    }

    public void setDate(Date date) {
        mDate = date;
        stopUpdating();
        startUpdating();
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    private long getElapsed() {
        if (mDate == null) return 0;
        Date n = new Date();
        return n.getTime() - mDate.getTime();
    }

    private String getElapsedText() {
        long e = getElapsed();
        long u = getInterval();
        long t = e / u;
        int unitId;

        if (e < UNIT_MINUTE) unitId = R.plurals.label_elapsed_second;
        else if (e < UNIT_HOUR) unitId = R.plurals.label_elapsed_minute;
        else if (e < UNIT_DAY) unitId = R.plurals.label_elapsed_hour;
        else unitId = R.plurals.label_elapsed_day;

        String unit = getResources().getQuantityString(unitId, (int) t);
        String text = getResources().getQuantityString(R.plurals.label_elapsed, (int) t, t, unit);
        return mPrefix + text;
    }

    private long getInterval() {
        long e = getElapsed();

        if (e < UNIT_MINUTE) {
            return UNIT_SECOND;
        } else if (e < UNIT_HOUR) {
            return UNIT_MINUTE;
        } else if (e < UNIT_DAY) {
            return UNIT_HOUR;
        } else {
            return UNIT_DAY;
        }
    }

    private void startUpdating() {
        if (mUpdater == null) mUpdater = new ElapsedUpdater();
        mHandler.post(mUpdater);
        isUpdateTaskRunning = true;
    }

    private void stopUpdating() {
        if (isUpdateTaskRunning) {
            mHandler.removeCallbacks(mUpdater);
            isUpdateTaskRunning = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopUpdating();
    }

    public class ElapsedUpdater implements Runnable {

        @Override
        public void run() {
            setText(getElapsedText());
            mHandler.postDelayed(this, getInterval());
        }
    }
}
