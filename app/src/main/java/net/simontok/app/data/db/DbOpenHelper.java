/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.simontok.app.data.db.model.DaoMaster;
import net.simontok.app.di.ApplicationContext;
import net.simontok.app.di.DatabaseInfo;
import net.simontok.app.utils.AppLogger;

import net.simontok.app.data.db.model.DaoMaster;
import net.simontok.app.di.ApplicationContext;
import net.simontok.app.di.DatabaseInfo;

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        AppLogger.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
