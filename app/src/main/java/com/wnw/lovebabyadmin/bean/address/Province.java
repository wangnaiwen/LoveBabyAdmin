package com.wnw.lovebabyadmin.bean.address;

import java.util.ArrayList;

/**
 * Created by wnw on 2016/12/14.
 */

public class Province {
    private String areaId;
    private String areaName;
    private ArrayList<City> cities = new ArrayList<City>();

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

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
