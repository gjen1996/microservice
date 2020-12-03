package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-15:35
 * @Description
 */
import lombok.extern.slf4j.Slf4j;


/**
 * @author Glen
 * @create 2020/9/10 15:35
 * @Description
 */
@Slf4j
public class FileOperationUtil {
    /*/**
     * @author Glen
     * @date 2020/9/10 15:35
     * @Description 包名处理
     */
    public static String packageNmae(String packageName) {
        int i;
        int str = packageName.toCharArray().length;
        for (i = 0; i < str; i++) {
            if ((int) '.' == packageName.toCharArray()[i]) {
                if (i == packageName.length() - 1) {
                    packageName = packageName.substring(0, packageName.length() - 1);
                }
            }
        }
        return packageName.replace(".", "/");
    }

    /**
     * @author Glen
     * @date 2020/9/10 16:11
     * @Description 类名处理
     */
    public static String className(String className,Boolean bo) {
        char[] str = className.toCharArray();
        if (str[0] >= 'A' && str[0] <= 'Z') {
            className = className + ".java";
        } else {
            className = String.valueOf(str[0]).toUpperCase() + className.substring(1, className.length()) + ".java";
        }
        if(bo == true){
            className =className.substring(0,className.length()-5);
        }
        return className;
    }

    // 格式化数据库表名 sMdft
    public static String tableName(String tableName) {
        char[] str = tableName.toCharArray();
        int length = str.length;
        char line = '_';
        if (str[0] >= 'a' && str[0] <= 'z') {
            int i;
            int a = 0;
            for (i = 1; i < length; i++) {
                if (str[i] >= 'A' && str[i] <= 'Z') {
                    String body = tableName.replace(str[i], Character.toLowerCase(str[i]));
                    StringBuffer sb = new StringBuffer(body);
                    sb.insert(i + a, "_");
                    tableName = sb.toString();
                    a++;
                }
            }
        } else {
            int num;
            String head = tableName;
            int i = 0;
            for (num = 1; num < length; num++) {
                if (str[num] >= 'A' && str[num] <= 'Z') {
                    String body = tableName.replace(str[num], Character.toLowerCase(str[num]));
                    StringBuffer sb = new StringBuffer(body);
                    sb.insert(num + i, "_");
                    tableName = sb.toString();
                    i++;
                }
            }
            tableName = tableName.replace(str[0], Character.toLowerCase(str[0]));
        }
        return tableName;
    }
    public static void main(String[] args){
        log.info(className("Systemctl",true));
        log.info(className("Systemctl",false));
    }

}
