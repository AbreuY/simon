/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.simontok.app.data.db.model.DaoMaster;
import net.simontok.app.data.db.model.DaoSession;
import net.simontok.app.data.db.model.GeoIp;
import net.simontok.app.utils.AppLogger;

import net.simontok.app.data.db.model.DaoMaster;
import net.simontok.app.data.db.model.DaoSession;

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public void saveGeoIp(GeoIp geoIp) {
        mDaoSession.getGeoIpDao().insertOrReplace(geoIp);

    }

    @Override
    public GeoIp getGeoIp() {
        return mDaoSession.getGeoIpDao().loadAll().get(0);
    }
}
