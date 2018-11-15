package com.gokep.app.ui.main.category;

import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface CategoryView extends MvpView {
    void showCategories(List<Category> categoryList);
}
