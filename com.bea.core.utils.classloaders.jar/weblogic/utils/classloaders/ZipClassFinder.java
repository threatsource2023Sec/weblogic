package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import weblogic.utils.StringUtils;
import weblogic.utils.zip.Handler;

public class ZipClassFinder extends AbstractClassFinder implements PackageIndexedClassFinder {
   private final ZipFile zip;
   private final String path;
   private final boolean isJar;
   private boolean shareable = false;

   public ZipClassFinder(ZipFile zipFile) throws IOException {
      this.isJar = zipFile.getName().endsWith(".jar");
      this.zip = zipFile;
      this.path = (new File(this.zip.getName())).getCanonicalPath();
   }

   public ZipFile getZipFile() {
      return this.zip;
   }

   public Source getSource(String name) {
      while(StringUtils.startsWith(name, '/')) {
         name = name.substring(1);
      }

      ZipEntry ze = this.zip.getEntry(name);
      if (ze == null) {
         return null;
      } else if (this.isJar) {
         return new JarSource((JarFile)this.zip, (JarEntry)ze);
      } else {
         return new ZipSource(this.zip, ze);
      }
   }

   public String getClassPath() {
      return this.path;
   }

   public Enumeration entries() {
      final Enumeration e = this.zip.entries();
      return new Enumeration() {
         public boolean hasMoreElements() {
            return e.hasMoreElements();
         }

         public Source nextElement() {
            return (Source)(ZipClassFinder.this.isJar ? new JarSource((JarFile)ZipClassFinder.this.zip, (JarEntry)e.nextElement()) : new ZipSource(ZipClassFinder.this.zip, (ZipEntry)e.nextElement()));
         }
      };
   }

   public Collection getPackageNames() {
      Set set = new HashSet();
      Enumeration e = this.zip.entries();

      while(e.hasMoreElements()) {
         ZipEntry entry = (ZipEntry)e.nextElement();
         String name;
         if (entry.isDirectory()) {
            name = entry.getName();
            set.add(MultiClassFinder.getResourceDirectoryPackageName(name));
         } else {
            name = entry.getName();
            set.add(MultiClassFinder.getResourcePackageName(name));
         }
      }

      set.add("");
      return set;
   }

   public void close() {
      try {
         this.zip.close();
      } catch (IOException var2) {
      }

   }

   public String toString() {
      return super.toString() + " - path: '" + this.path + "'";
   }

   void markShareable() {
      this.shareable = true;
   }

   public boolean isShareable() {
      return this.shareable;
   }

   static {
      Handler.init();
   }
}
