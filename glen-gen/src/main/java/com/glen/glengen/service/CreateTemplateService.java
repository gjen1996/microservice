package com.glen.glengen.service;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;

/**
 * @author 盖玉成
 * @date 2020/9/8 16:24
 * @description 该类的作用
 */
public interface CreateTemplateService {
    R createTables(JSONObject param) throws Exception;
}
