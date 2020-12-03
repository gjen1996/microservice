package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/11-12:04
 * @Description
 */

import com.glen.glencommonsystem.util.R;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author Glen
 * @create 2020/9/11 12:04
 * @Description
 */
@Slf4j
public class CopyDirOpeUtil {
    public static R copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
        return R.ok().put("msgDetils", "文件复制成功");
    }

    // 复制文件夹
    public static R copyDirectiory(String sourceDir, String targetDir)
            throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new
                        File(new File(targetDir).getAbsolutePath()
                        + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
        return R.ok().put("msgDetils", "文件夹复制成功");
    }

    //移动文件
    public static R moveFile(String startPath, String endPath, String fileName) {
        log.info(startPath);
        log.info(endPath);
        log.info(fileName);
        try {
            File startFile = new File(startPath + "/"+fileName);
            File tempPath = new File(endPath);
            //获取文件夹路径
            File endFile = new File(endPath+fileName);
            //判断文件夹是否创建，没有创建则创建新文件夹
            if (!tempPath.exists()) {
                tempPath.mkdirs();
            }
            if (startFile.renameTo(endFile)) {
                System.out.println("File is moved successful!");
                log.info("文件移动成功！文件名：《{}》 目标路径：{}", fileName, endPath);
            } else {
                System.out.println("File is failed to move!");
                log.info("文件移动失败！文件名：《{}》 起始路径：{}", fileName, startPath);
            }
        } catch (Exception e) {
            log.info("文件移动异常！文件名：《{}》 起始路径：{}", fileName, startPath);

        }
        return R.ok();
    }
}
