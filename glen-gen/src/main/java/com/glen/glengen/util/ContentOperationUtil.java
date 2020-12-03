package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/11-11:44
 * @Description
 */

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Glen
 * @create 2020/9/11 11:44 
 * @Description
 */
public class ContentOperationUtil {
    public static R contentOperation(JSONObject params , StringBuffer content){
        FileOutputStream fop = null;
        File file;
        try {

            file = new File(params.getString("fileUrl"), params.getString("classFileName"));
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = content.toString().getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return R.ok();
    }
}
