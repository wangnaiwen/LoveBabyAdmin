package com.wnw.lovebabyadmin.bean;

import com.wnw.lovebabyadmin.domain.Sc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wnw on 2017/5/24.
 */

public class SerializableMap implements Serializable{
    private Map<String,List<Sc>> map;

    public Map<String,List<Sc>> getMap() {
        return map;
    }

    public void setMap(Map<String,List<Sc>> map) {
        this.map = map;
    }
}
