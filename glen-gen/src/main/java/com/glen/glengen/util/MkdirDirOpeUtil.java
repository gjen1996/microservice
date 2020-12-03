package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/11-12:02
 * @Description
 */

import com.glen.glencommonsystem.util.R;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author Glen
 * @create 2020/9/11 12:02 
 * @Description
 */
@Slf4j
public class MkdirDirOpeUtil {

    public static R createFile(String directory,String destFileName) {
        File file = new File(directory,destFileName);
        if (file.exists()) {
            return R.error().put("msgDetils","创建单个文件" + destFileName + "失败，目标文件已存在！");
        }
        if (destFileName.endsWith(File.separator)) {
            return R.error().put("msgDetils","创建单个文件" + destFileName + "失败，目标不能是目录！");
        }
        if (!file.getParentFile().exists()) {
            log.info("目标文件所在路径不存在，准备创建...");
            if(createDir(directory).getString("code") == "500") {
                return R.error().put("msgDetils","创建目录文件所在的目录失败！");
            }
        }

        // 创建目标文件
        try {
            if (file.createNewFile()) {
                return R.ok().put("msgDetils","创建单个文件" + destFileName + "成功！");
            } else {
                return R.error().put("msgDetils","创建单个文件" + destFileName + "失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().put("msgDetils","创建单个文件" + destFileName + "失败！");
        }
    }
    /**
     * @author Glen
     * @date 2020/9/10 14:42
     * @Description 创建文件夹
     */

    public static R createDir(String destDirName) {
        File dir = new File(destDirName);
        if(dir.exists()) {
            return R.error().put("msgDetils","创建目录" + destDirName + "失败，目标目录已存在！");
        }
        if(!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建单个目录
        if(dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return R.ok().put("msgDetils","创建单个文件" + destDirName + "成功！");
        } else {
            System.out.println("创建目录" + destDirName + "！");
            return R.error().put("msgDetils","创建单个文件" + destDirName + "失败！");
        }
    }


}
