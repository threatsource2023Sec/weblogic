package org.cryptacular.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResource implements Resource {
   private File file;

   public FileResource(File file) {
      if (file == null) {
         throw new IllegalArgumentException("File cannot be null.");
      } else {
         this.file = file;
      }
   }

   public InputStream getInputStream() throws IOException {
      return new BufferedInputStream(new FileInputStream(this.file));
   }

   public String toString() {
      return this.file.toString();
   }
}
