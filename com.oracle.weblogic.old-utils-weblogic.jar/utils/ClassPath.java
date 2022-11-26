package utils;

import weblogic.utils.classloaders.ClasspathClassLoader;

public class ClassPath {
   public static void main(String[] args) throws Exception {
      System.out.println((new ClasspathClassLoader()).getClassPath());
   }
}
