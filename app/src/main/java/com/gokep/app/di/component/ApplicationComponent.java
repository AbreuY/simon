/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import com.gokep.app.GoKEP;
import dagger.Component;
import com.gokep.app.di.ApplicationContext;
import com.gokep.app.di.module.ApplicationModule;

import com.gokep.app.data.DataManager;
import com.gokep.app.service.SyncService;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GoKEP app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}