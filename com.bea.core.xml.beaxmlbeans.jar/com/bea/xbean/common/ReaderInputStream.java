package com.bea.xbean.common;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class ReaderInputStream extends PushedInputStream {
   private Reader reader;
   private Writer writer;
   private char[] buf;
   public static int defaultBufferSize = 2048;

   public ReaderInputStream(Reader reader, String encoding) throws UnsupportedEncodingException {
      this(reader, encoding, defaultBufferSize);
   }

   public ReaderInputStream(Reader reader, String encoding, int bufferSize) throws UnsupportedEncodingException {
      if (bufferSize <= 0) {
         throw new IllegalArgumentException("Buffer size <= 0");
      } else {
         this.reader = reader;
         this.writer = new OutputStreamWriter(this.getOutputStream(), encoding);
         this.buf = new char[bufferSize];
      }
   }

   public void fill(int requestedBytes) throws IOException {
      do {
         int chars = this.reader.read(this.buf);
         if (chars < 0) {
            return;
         }

         this.writer.write(this.buf, 0, chars);
         this.writer.flush();
      } while(this.available() <= 0);

   }
}
