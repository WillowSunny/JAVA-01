package com.willow.jvm.classloader;

import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * <p>Description:打印类加载器的相关信息</p>
 * Create by willow on 2021/1/8 at 22:13
 */
public class JvmClassLoadPrintPath {
    public static void main(String[] args) {
        //启动类加载器
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urLs){
            System.out.println("---> " + url.toExternalForm());
        }

        // 扩展类加载器
        printClassLoader("应用类加载器" , JvmClassLoadPrintPath.class.getClassLoader().getParent());
        //应用类加载器
        printClassLoader("应用类加载器" , JvmClassLoadPrintPath.class.getClassLoader());
    }

    public static void printClassLoader(String name,ClassLoader classLoader){
        if(classLoader != null){
            System.out.println(name + " ClassLoader" + classLoader.toString());
            printURLForClassLoader(classLoader);
        }else{
            System.out.println(name + "ClassLoader -> null");
        }
    }

    public static void printURLForClassLoader(ClassLoader classLoader){
        Object ucp = insightField(classLoader,"ucp");
        Object path = insightField(ucp,"path");
        ArrayList<URL> ps = (ArrayList<URL>) path;
        for(URL url : ps){
            System.out.println(" ---> " + url.toExternalForm());
        }
    }

    private static Object insightField(Object obj, String fieldName){
        try {
            Field field = null;
            if(obj instanceof URLClassLoader){
                field = URLClassLoader.class.getDeclaredField(fieldName);
            }else {
                field = obj.getClass().getDeclaredField(fieldName);
            }
            field.setAccessible(true);
            return field.get(obj);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
