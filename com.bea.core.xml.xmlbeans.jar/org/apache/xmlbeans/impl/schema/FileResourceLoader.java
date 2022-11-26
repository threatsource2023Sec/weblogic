package org.apache.xmlbeans.impl.schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.xmlbeans.ResourceLoader;

public class FileResourceLoader implements ResourceLoader {
   private File _directory;
   private ZipFile _zipfile;

   public FileResourceLoader(File file) throws IOException {
      if (file.isDirectory()) {
         this._directory = file;
      } else {
         this._zipfile = new ZipFile(file);
      }

   }

   public InputStream getResourceAsStream(String resourceName) {
      try {
         if (this._zipfile != null) {
            ZipEntry entry = this._zipfile.getEntry(resourceName);
            return entry == null ? null : this._zipfile.getInputStream(entry);
         } else {
            return new FileInputStream(new File(this._directory, resourceName));
         }
      } catch (IOException var3) {
         return null;
      }
   }

   public void close() {
      if (this._zipfile != null) {
         try {
            this._zipfile.close();
         } catch (IOException var2) {
         }

         this._zipfile = null;
      }

   }
}
