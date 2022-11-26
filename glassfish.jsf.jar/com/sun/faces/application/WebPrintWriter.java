package com.sun.faces.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class WebPrintWriter extends PrintWriter {
   public static final Writer NOOP_WRITER = new NoOpWriter();
   private boolean committed;

   public WebPrintWriter(Writer delegate) {
      super(delegate);
   }

   public void close() {
      this.committed = true;
   }

   public void flush() {
      this.committed = true;
   }

   public boolean isComitted() {
      return this.committed;
   }

   private static class NoOpWriter extends Writer {
      public NoOpWriter() {
      }

      public void write(char[] cbuf, int off, int len) throws IOException {
      }

      public void flush() throws IOException {
      }

      public void close() throws IOException {
      }

      protected NoOpWriter(Object lock) {
      }

      public void write(int c) throws IOException {
      }

      public void write(char[] cbuf) throws IOException {
      }

      public void write(String str) throws IOException {
      }

      public void write(String str, int off, int len) throws IOException {
      }

      public Writer append(CharSequence csq) throws IOException {
         return this;
      }

      public Writer append(CharSequence csq, int start, int end) throws IOException {
         return this;
      }

      public Writer append(char c) throws IOException {
         return this;
      }
   }
}
