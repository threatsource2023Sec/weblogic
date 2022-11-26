package com.sun.faces.facelets.util;

import java.io.IOException;
import java.io.Writer;

public final class FastWriter extends Writer {
   private char[] buff;
   private int size;

   public FastWriter() {
      this(1024);
   }

   public FastWriter(int initialSize) {
      if (initialSize < 0) {
         throw new IllegalArgumentException("Initial Size cannot be less than 0");
      } else {
         this.buff = new char[initialSize];
      }
   }

   public void close() throws IOException {
   }

   public void flush() throws IOException {
   }

   private void overflow(int len) {
      if (this.size + len > this.buff.length) {
         char[] next = new char[(this.size + len) * 2];
         System.arraycopy(this.buff, 0, next, 0, this.size);
         this.buff = next;
      }

   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.overflow(len);
      System.arraycopy(cbuf, off, this.buff, this.size, len);
      this.size += len;
   }

   public void write(char[] cbuf) throws IOException {
      this.write((char[])cbuf, 0, cbuf.length);
   }

   public void write(int c) throws IOException {
      this.overflow(1);
      this.buff[this.size] = (char)c;
      ++this.size;
   }

   public void write(String str, int off, int len) throws IOException {
      this.write(str.toCharArray(), off, len);
   }

   public void write(String str) throws IOException {
      this.write((char[])str.toCharArray(), 0, str.length());
   }

   public void reset() {
      this.size = 0;
   }

   public String toString() {
      return new String(this.buff, 0, this.size);
   }
}
