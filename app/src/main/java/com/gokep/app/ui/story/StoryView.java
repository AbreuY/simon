package com.gokep.app.ui.story;

import com.gokep.app.data.network.model.Story;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface StoryView extends MvpView {
    void showStories(List<Story> stories);
}
