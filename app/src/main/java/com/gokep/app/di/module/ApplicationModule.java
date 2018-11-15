/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import android.support.v7.app.AppCompatActivity;
import com.gokep.app.utils.AppLogger;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import dagger.Module;
import dagger.Provides;
import com.gokep.app.BuildConfig;
import com.gokep.app.R;
import com.gokep.app.data.AppDataManager;
import com.gokep.app.data.DataManager;
import com.gokep.app.data.db.AppDbHelper;
import com.gokep.app.data.db.DbHelper;
import com.gokep.app.data.network.ApiHeader;
import com.gokep.app.data.network.ApiHelper;
import com.gokep.app.data.network.AppApiHelper;
import com.gokep.app.data.prefs.AppPreferencesHelper;
import com.gokep.app.data.prefs.PreferencesHelper;
import com.gokep.app.di.ApplicationContext;
import com.gokep.app.di.PreferenceInfo;
import com.gokep.app.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import com.gokep.app.di.ApiInfo;
import com.gokep.app.di.DatabaseInfo;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "";
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

//    @Provides
//    @Singleton
//    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
//                                                           PreferencesHelper preferencesHelper) {
//        return new ApiHeader.ProtectedApiHeader(
//                apiKey,
//                preferencesHelper.getCurrentUserId(),
//                preferencesHelper.getAccessToken());
//    }



    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/google_sans_display_bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
