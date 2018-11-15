/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data;

import com.gokep.app.data.db.DbHelper;
import com.gokep.app.data.network.ApiHelper;
import com.gokep.app.data.prefs.PreferencesHelper;
import com.gokep.app.data.db.DbHelper;
import com.gokep.app.data.network.ApiHelper;
import com.gokep.app.data.prefs.PreferencesHelper;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {
}
