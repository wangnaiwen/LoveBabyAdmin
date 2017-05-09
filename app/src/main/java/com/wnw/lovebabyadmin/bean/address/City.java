package com.wnw.lovebabyadmin.bean.address;

import java.util.ArrayList;

/**
 * Created by wnw on 2016/12/14.
 */

public class City {
    private String areaId;
    private String areaName;
    private ArrayList<County> counties = new ArrayList<>();

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public ArrayList<County> getCounties() {
        return counties;
    }

    public void setCounties(ArrayList<County> counties) {
        this.counties = counties;
    }
}
