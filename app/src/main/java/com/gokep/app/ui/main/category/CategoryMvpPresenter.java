package com.gokep.app.ui.main.category;

import com.gokep.app.ui.base.MvpPresenter;

public interface CategoryMvpPresenter<V extends CategoryView> extends MvpPresenter<V> {

    void fetchCategories();

    String getBannerId();
}
