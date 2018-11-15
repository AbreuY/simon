package com.gokep.app.data.network.model.response;

import java.util.List;

import com.gokep.app.data.network.model.Story;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoryResponse {

    @SerializedName("stories")
    @Expose
    private List<Story> stories = null;

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
