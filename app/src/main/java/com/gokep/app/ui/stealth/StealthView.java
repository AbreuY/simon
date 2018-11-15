package com.gokep.app.ui.stealth;

import com.gokep.app.data.network.model.response.Dummy;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface StealthView extends MvpView {
    void showContents(List<Dummy> categoryList);
}
