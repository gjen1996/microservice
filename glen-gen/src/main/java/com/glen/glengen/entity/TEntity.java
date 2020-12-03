package com.glen.glengen.entity;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class TEntity implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String[] convert(String ids) {
        return convert(ids, ",");
    }

    public static String[] convert(String ids, String split) {
        if (StringUtils.isBlank(ids)) {
            return null;
        } else {
            while (ids.length() > 0 && ids.charAt(ids.length() - 1) == split.charAt(0)) {
                ids = ids.substring(0, ids.length() - 1).trim();
            }
            if (StringUtils.isBlank(ids)) {
                return null;
            }
            return ids.split(split);
        }
    }

}
