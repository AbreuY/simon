package com.gokep.app.ui.setting.lock;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.gokep.app.utils.AppConstants;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;
import com.gokep.app.R;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.utils.AppConstants;

import java.util.List;

public class LockActivity extends SetPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gokep.app.R.layout.activity_lock);

    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        String patternSha1 = PatternUtils.patternToSha1String(pattern);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
        pref.edit().putString("KEY_PATTERN", patternSha1).apply();
        finish();
    }
}
