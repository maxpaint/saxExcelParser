package com.mdanylenko.excel.context;

import com.mdanylenko.excel.exception.ErrorCode;
import com.mdanylenko.excel.exception.PrepareContextException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 08.12.2015<br/>
 * Time: 18:35<br/>
 * To change this template use File | Settings | File Templates.
 */
public class HelperClassLoader {

    private Set<Class<?>> classNames;

    public static HelperClassLoader loadResorce(String path) throws PrepareContextException {
        HelperClassLoader classLoader = new HelperClassLoader();
        classLoader.setClassNames(getAllClassesFromPackage(path));
        return classLoader;
    }

    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation){
        Set<Class<?>> result = new HashSet<>();

        OUTER: for (Class<?> clazz : classNames){

            Annotation[] res =  clazz.getAnnotations();
            for (Annotation ann : res){
                if( ann.annotationType().equals(annotation)){
                    result.add(clazz);
                    break OUTER;
                }
            }
        }

        return result;
    }

    private static Set<Class<?>> getAllClassesFromPackage(String path) throws PrepareContextException {
        try{
            Set<Class<?>> result = new HashSet<>();
            ClassLoader classLoader = getClassLoader();
            URL url = forResource(resourceName(path), classLoader);
            File folderScan = new File(url.getFile());

            if(folderScan.isDirectory()){
                File[] classes = folderScan.listFiles();
                for (File clazz : classes){
                    if(clazz.isFile()){
                        String name = getClassName(clazz, path);
                        result.add(classLoader.loadClass(name));
                    }
                }

            }else{
                throw  new PrepareContextException(String.format(ErrorCode.CONTEXT_CLASS_PATH, path));
            }

            return result;
        }catch (Throwable e){
            throw new PrepareContextException(e.getMessage(), e);
        }
    }

    private static String getClassName(File classFile, String scaningPath){
        String classPath  = classFile.getAbsolutePath();
        classPath = classPath.replace('/', '.');
        classPath = classPath.replace('\\', '.');
        classPath = classPath.substring(classPath.indexOf(scaningPath), classPath.length());
        return  removeExtention(classPath);

    }
    private static String removeExtention(String className) {
        return className.substring(0, className.length() - 6);

    }

    private static ClassLoader getClassLoader(){
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader staticClassLoader =  HelperClassLoader.class.getClassLoader();

        return (nonNull(contextClassLoader)) ? contextClassLoader : staticClassLoader;

    }

    private String getPath(String name){
        if (name != null) {
            String resourceName = name.replace(".", "\\");
            if (resourceName.startsWith("/")) {
                resourceName = resourceName.substring(1);
            }
            return resourceName;
        }
        return null;
    }

    private static String resourceName(String name) {
        if (name != null) {
            String resourceName = name.replace(".", "/");
            resourceName = resourceName.replace("\\", "/");
            if (resourceName.startsWith("/")) {
                resourceName = resourceName.substring(1);
            }
            return resourceName;
        }
        return null;
    }

    private static URL forResource(String resourceName, ClassLoader classLoader) throws PrepareContextException {
        URL url = null;
        final Enumeration<URL> urls;
        try {
            urls = classLoader.getResources(resourceName);
            while (urls.hasMoreElements()) {
                url = urls.nextElement();
                if(nonNull(url )){
                    return url;
                }
            }
        } catch (IOException e) {
            throw new PrepareContextException(e.getMessage(), e);
        }


        throw new PrepareContextException(String.format(ErrorCode.CONTEXT_CLASS_PATH, resourceName));
    }

    public Set<Class<?>> getClassNames() {
        return classNames;
    }

    public void setClassNames(Set<Class<?>> classNames) {
        this.classNames = classNames;
    }
}
