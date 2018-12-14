package com.pyg.pojo;

import java.io.Serializable;

public class TbGoodsKey implements Serializable {
    private Long id;

    private String smallPic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic == null ? null : smallPic.trim();
    }
}