package com.solarmetric.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPStreamDecorator implements StreamDecorator {
   public InputStream decorate(InputStream in) throws Exception {
      return new GZIPInputStream(in) {
         public void close() throws IOException {
            while(this.available() > 0) {
               this.read();
            }

            super.close();
         }
      };
   }

   public OutputStream decorate(OutputStream out) throws Exception {
      return new GZIPOutputStream(out) {
         public void close() throws IOException {
            this.finish();
            super.close();
         }
      };
   }
}
