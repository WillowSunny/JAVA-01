package com.willow.jvm.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * <p>Description:自定义类加载器，加载第三方加密的class文件并执行</p>
 * Create by willow on 2021/1/10 at 16:33
 */
public class HelloClassLoader extends ClassLoader {

    private String classPath;

    public HelloClassLoader(String classPath){
        this.classPath = classPath;
        System.out.println(this.classPath);
    }

    public String getClassPath(){
        return this.classPath;
    }

    private byte[] loadByte(String className) throws IOException {
        FileInputStream fis = new FileInputStream(getClassPath()+ "/"+className+".xlass");
        int available = fis.available();
        System.out.println(available);
        byte[] data = new byte[available];
        fis.read(data);
        fis.close();
        return data;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classBytes = loadByte(name);
            for(int i =0; i<classBytes.length; i++){
                classBytes[i] = (byte) (255-classBytes[i]);
            }
            return defineClass("Hello",classBytes,0,classBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }

    }


    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        HelloClassLoader helloClassLoader = new HelloClassLoader(System.getProperty("user.dir"));
        Class helloClazz = helloClassLoader.loadClass("Hello");
        Object helloInstance = helloClazz.newInstance();
        Method helloMethod = helloClazz.getDeclaredMethod("hello",null);
        helloMethod.invoke(helloInstance,null);
    }
}
