package com.glen.glengen.compiler;/**
 * @author Glen
 * @create 2020- 09-2020/9/17-11:19
 * @Description
 * @author Glen
 * @create 2020/9/17 11:19
 * @Description
 * @author Glen
 * @create 2020/9/17 11:19
 * @Description
 */

/**
 * @author Glen
 * @create 2020/9/17 11:19 
 * @Description
 */

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class MyClassLoader extends ClassLoader {

    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getData(String className) {
        String path = classPath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}