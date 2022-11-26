package weblogic.utils.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassFinder11 extends ClassFinder102 {
   private ZipFile[] zipFiles;

   public ClassFinder11() {
   }

   public ClassFinder11(String path) {
      super(path);
   }

   public ClassFinder11(File[] classpath) {
      super(classpath);
   }

   protected void zipScan() {
      int i;
      for(i = 0; this.zipFiles != null && i < this.zipFiles.length; ++i) {
         if (this.zipFiles[i] != null) {
            try {
               this.zipFiles[i].close();
            } catch (Exception var4) {
            }
         }
      }

      super.zipScan();
      if (this.zips == null) {
         this.zipFiles = null;
      } else {
         this.zipFiles = new ZipFile[this.zips.length];

         for(i = 0; i < this.zipFiles.length; ++i) {
            if (this.zips[i]) {
               try {
                  this.zipFiles[i] = new ZipFile(this.classpath[i]);
               } catch (IOException var3) {
                  this.zips[i] = false;
               }
            }
         }

      }
   }

   public InputStream getResourceStream(String resource) throws IOException {
      resource = resource.replace('/', File.separatorChar);
      String zipRsc = resource.replace(File.separatorChar, '/');

      for(int i = 0; i < this.classpath.length; ++i) {
         if (this.classpath[i].isDirectory()) {
            File f = new File(this.classpath[i], resource);
            if (f.exists()) {
               return new FileInputStream(f);
            }
         } else if (this.zipFiles[i] != null) {
            ZipEntry ze = this.zipFiles[i].getEntry(zipRsc);
            if (ze != null) {
               return this.zipFiles[i].getInputStream(ze);
            }
         }
      }

      throw new FileNotFoundException(resource);
   }

   public ClassBytes find(String clss) {
      String zipName = clss.replace('.', '/') + ".class";
      clss = clss.replace('.', File.separatorChar) + ".class";

      for(int i = 0; i < this.classpath.length; ++i) {
         if (this.classpath[i].isDirectory()) {
            File f = new File(this.classpath[i], clss);
            if (f.exists()) {
               return new ClassBytes102(f, this.classpath[i]);
            }
         } else if (this.zipFiles[i] != null) {
            ZipEntry ent = this.zipFiles[i].getEntry(zipName);
            if (ent != null) {
               return new ClassBytes11(ent, this.zipFiles[i]);
            }
         }
      }

      return null;
   }

   public void finalize() {
      for(int i = 0; this.zipFiles != null && i < this.zipFiles.length; ++i) {
         if (this.zipFiles[i] != null) {
            try {
               this.zipFiles[i].close();
            } catch (Exception var3) {
            }
         }
      }

   }
}
