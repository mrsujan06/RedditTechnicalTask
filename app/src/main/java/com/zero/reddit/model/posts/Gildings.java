
package com.zero.reddit.model.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gildings {

    @SerializedName("gid_1")
    @Expose
    private Integer gid1;
    @SerializedName("gid_2")
    @Expose
    private Integer gid2;
    @SerializedName("gid_3")
    @Expose
    private Integer gid3;

    public Integer getGid1() {
        return gid1;
    }

    public void setGid1(Integer gid1) {
        this.gid1 = gid1;
    }

    public Integer getGid2() {
        return gid2;
    }

    public void setGid2(Integer gid2) {
        this.gid2 = gid2;
    }

    public Integer getGid3() {
        return gid3;
    }

    public void setGid3(Integer gid3) {
        this.gid3 = gid3;
    }

}
