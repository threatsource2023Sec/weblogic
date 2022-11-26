package org.apache.velocity.test;

import java.io.File;
import java.io.FileInputStream;

class TestClassloader extends ClassLoader {
   private static final String testclass = "../test/classloader/Foo.class";
   private Class fooClass = null;

   public TestClassloader() {
      try {
         File f = new File("../test/classloader/Foo.class");
         byte[] barr = new byte[(int)f.length()];
         FileInputStream fis = new FileInputStream(f);
         fis.read(barr);
         fis.close();
         this.fooClass = this.defineClass("Foo", barr, 0, barr.length);
      } catch (Exception var4) {
         System.out.println("TestClassloader : exception : " + var4);
      }

   }

   public Class findClass(String name) {
      return this.fooClass;
   }
}
