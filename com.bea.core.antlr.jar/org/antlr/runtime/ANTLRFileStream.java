package org.antlr.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ANTLRFileStream extends ANTLRStringStream {
   protected String fileName;

   public ANTLRFileStream(String fileName) throws IOException {
      this(fileName, (String)null);
   }

   public ANTLRFileStream(String fileName, String encoding) throws IOException {
      this.fileName = fileName;
      this.load(fileName, encoding);
   }

   public void load(String fileName, String encoding) throws IOException {
      if (fileName != null) {
         File f = new File(fileName);
         int size = (int)f.length();
         FileInputStream fis = new FileInputStream(fileName);
         InputStreamReader isr;
         if (encoding != null) {
            isr = new InputStreamReader(fis, encoding);
         } else {
            isr = new InputStreamReader(fis);
         }

         try {
            this.data = new char[size];
            super.n = isr.read(this.data);
         } finally {
            isr.close();
         }

      }
   }

   public String getSourceName() {
      return this.fileName;
   }
}
