package com.sun.faces.facelets;

import com.sun.faces.facelets.util.FastWriter;
import java.io.IOException;
import java.io.Writer;

final class StateWriter extends Writer {
   private int initialSize;
   private Writer out;
   private FastWriter fast;
   private boolean writtenState;
   private static final ThreadLocal CURRENT_WRITER = new ThreadLocal();

   public static StateWriter getCurrentInstance() {
      return (StateWriter)CURRENT_WRITER.get();
   }

   public StateWriter(Writer initialOut, int initialSize) {
      if (initialSize < 0) {
         throw new IllegalArgumentException("Initial Size cannot be less than 0");
      } else {
         this.initialSize = initialSize;
         this.out = initialOut;
         CURRENT_WRITER.set(this);
      }
   }

   public void writingState() {
      if (!this.writtenState) {
         this.writtenState = true;
         this.out = this.fast = new FastWriter(this.initialSize);
      }

   }

   public boolean isStateWritten() {
      return this.writtenState;
   }

   public void close() throws IOException {
   }

   public void flush() throws IOException {
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.out.write(cbuf, off, len);
   }

   public void write(char[] cbuf) throws IOException {
      this.out.write(cbuf);
   }

   public void write(int c) throws IOException {
      this.out.write(c);
   }

   public void write(String str, int off, int len) throws IOException {
      this.out.write(str, off, len);
   }

   public void write(String str) throws IOException {
      this.out.write(str);
   }

   public String getAndResetBuffer() {
      if (!this.writtenState) {
         throw new IllegalStateException("Did not write state;  no buffer is available");
      } else {
         String result = this.fast.toString();
         this.fast.reset();
         return result;
      }
   }

   public void release() {
      CURRENT_WRITER.set((Object)null);
   }
}
