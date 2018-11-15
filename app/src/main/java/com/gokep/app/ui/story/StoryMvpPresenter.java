package com.gokep.app.ui.story;

import com.gokep.app.ui.base.MvpPresenter;

public interface StoryMvpPresenter<V extends StoryView> extends MvpPresenter<V> {

    void getStories();
}
