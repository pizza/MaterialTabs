package io.karim.materialtabs.sample;

import com.crashlytics.android.Crashlytics;

import android.app.Application;

import io.fabric.sdk.android.Fabric;

/**
 * Created by karim on 5/20/15.
 */
public class MyApplication extends Application {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using lazy initialization of state) since the time spent in this function directly
     * impacts the performance of starting the first activity, service, or receiver in a process. If you override this method, be sure to call
     * super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
