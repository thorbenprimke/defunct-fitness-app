package com.femlite.app;

import android.app.Application;

import com.femlite.app.di.ApplicationComponent;
import com.femlite.app.di.ApplicationModule;
import com.femlite.app.di.DaggerApplicationComponent;
import com.femlite.app.di.HasComponent;
import com.femlite.app.model.parse.ParseExercise;
import com.femlite.app.model.parse.ParseWorkout;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by thorben on 11/2/15.
 */
public class FemliteApplication extends Application implements HasComponent<ApplicationComponent> {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(
                new RealmConfiguration.Builder(this)
                        .deleteRealmIfMigrationNeeded()
                        .build());

        initializeComponent();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Bold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Parse Object registration
        ParseObject.registerSubclass(ParseWorkout.class);
        ParseObject.registerSubclass(ParseExercise.class);

        Parse.initialize(this);

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseFacebookUtils.initialize(this);
    }

    // ================================================================================
    // Dependency Injection
    // ================================================================================
    private void initializeComponent() {
        if (applicationComponent != null) {
            return;
        }
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    @Override
    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
