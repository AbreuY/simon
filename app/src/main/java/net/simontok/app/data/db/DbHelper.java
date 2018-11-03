/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.db;

import net.simontok.app.data.db.model.GeoIp;

public interface DbHelper {

    void saveGeoIp(GeoIp geoIp);

    GeoIp getGeoIp();
}
