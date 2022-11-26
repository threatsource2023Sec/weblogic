package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Archive {
   public static final Archive UNKNOWN_ARCHIVE = new Archive();
   ZipFile zipFile;
   File file;
   protected Hashtable packagesCache;

   protected Archive() {
   }

   public Archive(File file) throws ZipException, IOException {
      this.file = file;
      this.zipFile = new ZipFile(file);
      this.initialize();
   }

   private void initialize() {
      this.packagesCache = new Hashtable();
      Enumeration e = this.zipFile.entries();

      while(e.hasMoreElements()) {
         String fileName = ((ZipEntry)e.nextElement()).getName();
         int last = fileName.lastIndexOf(47);
         String packageName = fileName.substring(0, last + 1);
         String typeName = fileName.substring(last + 1);
         ArrayList types = (ArrayList)this.packagesCache.get(packageName);
         if (types == null) {
            if (typeName.length() != 0) {
               types = new ArrayList();
               types.add(new String[]{typeName, null});
               this.packagesCache.put(packageName, types);
            }
         } else {
            types.add(new String[]{typeName, null});
         }
      }

   }

   public ArchiveFileObject getArchiveFileObject(String fileName, String module, Charset charset) {
      return new ArchiveFileObject(this.file, fileName, charset);
   }

   public boolean contains(String entryName) {
      return this.zipFile.getEntry(entryName) != null;
   }

   public Set allPackages() {
      if (this.packagesCache == null) {
         this.initialize();
      }

      return this.packagesCache.keySet();
   }

   public List getTypes(String packageName) {
      if (this.packagesCache == null) {
         try {
            this.zipFile = new ZipFile(this.file);
         } catch (IOException var2) {
            return Collections.emptyList();
         }

         this.initialize();
      }

      return (List)this.packagesCache.get(packageName);
   }

   public void flush() {
      this.packagesCache = null;
   }

   public void close() {
      try {
         if (this.zipFile != null) {
            this.zipFile.close();
         }

         this.packagesCache = null;
      } catch (IOException var1) {
      }

   }

   public String toString() {
      return "Archive: " + (this.file == null ? "UNKNOWN_ARCHIVE" : this.file.getAbsolutePath());
   }
}
