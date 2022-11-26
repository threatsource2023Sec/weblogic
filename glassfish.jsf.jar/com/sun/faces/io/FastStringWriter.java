package com.sun.faces.io;

import java.io.IOException;
import java.io.Writer;

public class FastStringWriter extends Writer {
   protected StringBuilder builder;

   public FastStringWriter() {
      this.builder = new StringBuilder();
   }

   public FastStringWriter(int initialCapacity) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException();
      } else {
         this.builder = new StringBuilder(initialCapacity);
      }
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      if (off >= 0 && off <= cbuf.length && len >= 0 && off + len <= cbuf.length && off + len >= 0) {
         if (len != 0) {
            this.builder.append(cbuf, off, len);
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void flush() throws IOException {
   }

   public void close() throws IOException {
   }

   public void write(String str) {
      this.builder.append(str);
   }

   public void write(String str, int off, int len) {
      this.builder.append(str.substring(off, off + len));
   }

   public StringBuilder getBuffer() {
      return this.builder;
   }

   public String toString() {
      return this.builder.toString();
   }

   public void reset() {
      this.builder.setLength(0);
   }
}
