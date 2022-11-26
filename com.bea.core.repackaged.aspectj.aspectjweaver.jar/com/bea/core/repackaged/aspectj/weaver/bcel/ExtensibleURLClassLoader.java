package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;

public abstract class ExtensibleURLClassLoader extends URLClassLoader {
   private ClassPathManager classPath;

   public ExtensibleURLClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);

      try {
         this.classPath = new ClassPathManager(FileUtil.makeClasspath(urls), (IMessageHandler)null);
      } catch (ExceptionInInitializerError var4) {
         var4.printStackTrace(System.out);
         throw var4;
      }
   }

   protected void addURL(URL url) {
      super.addURL(url);
      this.classPath.addPath(url.getPath(), (IMessageHandler)null);
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      try {
         byte[] bytes = this.getBytes(name);
         if (bytes != null) {
            return this.defineClass(name, bytes);
         } else {
            throw new ClassNotFoundException(name);
         }
      } catch (IOException var3) {
         throw new ClassNotFoundException(name);
      }
   }

   protected Class defineClass(String name, byte[] b, CodeSource cs) throws IOException {
      return this.defineClass(name, b, 0, b.length, cs);
   }

   protected byte[] getBytes(String name) throws IOException {
      byte[] b = null;
      UnresolvedType unresolvedType = null;

      try {
         unresolvedType = UnresolvedType.forName(name);
      } catch (BCException var9) {
         if (var9.getMessage().indexOf("nameToSignature") == -1) {
            var9.printStackTrace(System.err);
         }

         return null;
      }

      ClassPathManager.ClassFile classFile = this.classPath.find(unresolvedType);
      if (classFile != null) {
         try {
            b = FileUtil.readAsByteArray(classFile.getInputStream());
         } finally {
            classFile.close();
         }
      }

      return b;
   }

   private Class defineClass(String name, byte[] bytes) throws IOException {
      String packageName = this.getPackageName(name);
      if (packageName != null) {
         Package pakkage = this.getPackage(packageName);
         if (pakkage == null) {
            this.definePackage(packageName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (URL)null);
         }
      }

      return this.defineClass(name, bytes, (CodeSource)null);
   }

   private String getPackageName(String className) {
      int offset = className.lastIndexOf(46);
      return offset == -1 ? null : className.substring(0, offset);
   }

   public void close() throws IOException {
      super.close();
      this.classPath.closeArchives();
   }
}
