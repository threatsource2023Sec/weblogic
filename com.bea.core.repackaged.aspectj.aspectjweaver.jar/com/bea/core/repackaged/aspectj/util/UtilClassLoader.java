package com.bea.core.repackaged.aspectj.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UtilClassLoader extends URLClassLoader {
   List dirs;
   private URL[] urlsForDebugString;

   public UtilClassLoader(URL[] urls, File[] dirs) {
      super(urls);
      LangUtil.throwIaxIfNotAssignable((Object[])dirs, File.class, "dirs");
      this.urlsForDebugString = urls;
      ArrayList dcopy = new ArrayList();
      if (!LangUtil.isEmpty((Object[])dirs)) {
         dcopy.addAll(Arrays.asList(dirs));
      }

      this.dirs = Collections.unmodifiableList(dcopy);
   }

   public URL getResource(String name) {
      return ClassLoader.getSystemResource(name);
   }

   public InputStream getResourceAsStream(String name) {
      return ClassLoader.getSystemResourceAsStream(name);
   }

   public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      ClassNotFoundException thrown = null;
      Class result = this.findLoadedClass(name);
      if (null != result) {
         resolve = false;
      } else {
         try {
            result = this.findSystemClass(name);
         } catch (ClassNotFoundException var7) {
            thrown = var7;
         }
      }

      if (null == result) {
         try {
            result = super.loadClass(name, resolve);
         } catch (ClassNotFoundException var6) {
            thrown = var6;
         }

         if (null != result) {
            return result;
         }
      }

      if (null == result) {
         byte[] data = this.readClass(name);
         if (data != null) {
            result = this.defineClass(name, data, 0, data.length);
         }
      }

      if (null == result) {
         throw null != thrown ? thrown : new ClassNotFoundException(name);
      } else {
         if (resolve) {
            this.resolveClass(result);
         }

         return result;
      }
   }

   private byte[] readClass(String className) throws ClassNotFoundException {
      String fileName = className.replace('.', '/') + ".class";
      Iterator iter = this.dirs.iterator();

      File file;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         file = new File((File)iter.next(), fileName);
      } while(!file.canRead());

      return this.getClassData(file);
   }

   private byte[] getClassData(File f) {
      try {
         FileInputStream stream = new FileInputStream(f);
         ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
         byte[] b = new byte[4096];

         int n;
         while((n = stream.read(b)) != -1) {
            out.write(b, 0, n);
         }

         stream.close();
         out.close();
         return out.toByteArray();
      } catch (IOException var6) {
         return null;
      }
   }

   public String toString() {
      return "UtilClassLoader(urls=" + Arrays.asList(this.urlsForDebugString) + ", dirs=" + this.dirs + ")";
   }
}
