package weblogic.utils.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import weblogic.utils.FileUtils;

public class ClassFinder102 implements ClassFinder {
   protected File[] classpath;
   protected boolean[] zips;

   public ClassFinder102() {
      this.classpath = FileUtils.splitPath(System.getProperty("java.class.path"));
      this.zipScan();
   }

   public ClassFinder102(String path) {
      this.classpath = FileUtils.splitPath(path);
      this.zipScan();
   }

   public ClassFinder102(File[] classpath) {
      this.classpath = classpath;
      this.zipScan();
   }

   public void setClasspath(String path) {
      this.classpath = FileUtils.splitPath(path);
      this.zipScan();
   }

   public void setClasspath(File[] classpath) {
      this.classpath = classpath;
      this.zipScan();
   }

   protected void zipScan() {
      if (this.classpath != null && this.classpath.length != 0) {
         this.zips = new boolean[this.classpath.length];

         for(int i = 0; i < this.zips.length; ++i) {
            this.zips[i] = false;
            if (this.classpath[i].exists() && !this.classpath[i].isDirectory()) {
               InputStream in = null;

               try {
                  in = new FileInputStream(this.classpath[i]);
                  int a = in.read();
                  int b = in.read();
                  int c = in.read();
                  int d = in.read();
                  if (a == 80 && b == 75 && c == 3 && d == 4) {
                     this.zips[i] = true;
                  }
               } catch (Exception var15) {
               } finally {
                  if (in != null) {
                     try {
                        in.close();
                     } catch (Exception var14) {
                     }
                  }

               }
            }
         }

      } else {
         this.zips = new boolean[0];
      }
   }

   public ClassBytes find(String clss) {
      for(int i = 0; i < this.classpath.length; ++i) {
         if (!this.zips[i] && !this.classpath[i].getName().endsWith(".zip")) {
            File f = this.findClassInDirectory(clss, this.classpath[i]);
            if (f != null && f.exists()) {
               return new ClassBytes102(f, this.classpath[i]);
            }
         }
      }

      return null;
   }

   public InputStream getResourceStream(String resource) throws IOException {
      resource = resource.replace('/', File.separatorChar);
      resource = resource.replace('.', File.separatorChar);

      for(int i = 0; i < this.classpath.length; ++i) {
         if (this.classpath[i].isDirectory()) {
            File f = new File(this.classpath[i], resource);
            if (f.exists()) {
               return new FileInputStream(f);
            }
         }
      }

      throw new FileNotFoundException(resource);
   }

   public InputStream getClassAsStream(String clss) throws IOException, ClassNotFoundException {
      return this.find(clss).getStream();
   }

   public byte[] getClassAsBytes(String clss) throws IOException, ClassNotFoundException {
      ClassBytes cb = this.find(clss);
      if (cb == null) {
         throw new ClassNotFoundException(clss);
      } else {
         return cb.getBytes();
      }
   }

   protected File findClassInDirectory(String clss, File dir) {
      String fileName = clss.replace('.', File.separatorChar) + ".class";
      File f = new File(dir, fileName);
      return f.exists() ? f : null;
   }
}
