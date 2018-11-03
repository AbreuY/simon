/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import net.simontok.app.BuildConfig;
import net.simontok.app.R;
import net.simontok.app.data.AppDataManager;
import net.simontok.app.data.DataManager;
import net.simontok.app.data.db.AppDbHelper;
import net.simontok.app.data.db.DbHelper;
import net.simontok.app.data.network.ApiHeader;
import net.simontok.app.data.network.ApiHelper;
import net.simontok.app.data.network.AppApiHelper;
import net.simontok.app.data.prefs.AppPreferencesHelper;
import net.simontok.app.data.prefs.PreferencesHelper;
import net.simontok.app.di.ApiInfo;
import net.simontok.app.di.ApplicationContext;
import net.simontok.app.di.DatabaseInfo;
import net.simontok.app.di.PreferenceInfo;
import net.simontok.app.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import net.simontok.app.di.ApiInfo;
import net.simontok.app.di.ApplicationContext;
import net.simontok.app.di.DatabaseInfo;
import net.simontok.app.di.PreferenceInfo;

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
