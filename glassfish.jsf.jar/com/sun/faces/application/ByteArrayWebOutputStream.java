package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

class ByteArrayWebOutputStream extends ServletOutputStream {
   public static final ServletOutputStream NOOP_STREAM = new NoOpOutputStream();
   private static final Logger LOGGER;
   private DirectByteArrayOutputStream baos = new DirectByteArrayOutputStream(1024);
   private boolean committed;

   public ByteArrayWebOutputStream() {
   }

   public void write(int n) {
      this.baos.write(n);
   }

   public void resetByteArray() {
      this.baos.reset();
   }

   public byte[] toByteArray() {
      return this.baos.toByteArray();
   }

   public void writeTo(Writer writer, String encoding) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Converting buffered ServletOutputStream bytes to chars using " + encoding);
      }

      ByteBuffer byteBuffer = this.baos.getByteBuffer();
      CharsetDecoder decoder = Charset.forName(encoding).newDecoder();

      try {
         CharBuffer charBuffer = decoder.decode(byteBuffer);
         writer.write(charBuffer.array());
      } catch (IOException var6) {
         throw new FacesException(var6);
      }
   }

   public boolean isCommitted() {
      return this.committed;
   }

   public void close() throws IOException {
      this.committed = true;
   }

   public void flush() throws IOException {
      this.committed = true;
   }

   public void writeTo(OutputStream stream) {
      try {
         stream.write(this.baos.getByteBuffer().array());
      } catch (IOException var3) {
         throw new FacesException(var3);
      }
   }

   public boolean isReady() {
      throw new UnsupportedOperationException("Not supported");
   }

   public void setWriteListener(WriteListener wl) {
      throw new UnsupportedOperationException("Not supported");
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }

   private static class NoOpOutputStream extends ServletOutputStream {
      public void write(int b) throws IOException {
      }

      public void write(byte[] b) throws IOException {
      }

      public void write(byte[] b, int off, int len) throws IOException {
      }

      public void flush() throws IOException {
      }

      public void close() throws IOException {
      }

      protected NoOpOutputStream() {
      }

      public void print(String s) throws IOException {
      }

      public void print(boolean b) throws IOException {
      }

      public void print(char c) throws IOException {
      }

      public void print(int i) throws IOException {
      }

      public void print(long l) throws IOException {
      }

      public void print(float v) throws IOException {
      }

      public void print(double v) throws IOException {
      }

      public void println() throws IOException {
      }

      public void println(String s) throws IOException {
      }

      public void println(boolean b) throws IOException {
      }

      public void println(char c) throws IOException {
      }

      public void println(int i) throws IOException {
      }

      public void println(long l) throws IOException {
      }

      public void println(float v) throws IOException {
      }

      public void println(double v) throws IOException {
      }

      public boolean isReady() {
         throw new UnsupportedOperationException("Not supported");
      }

      public void setWriteListener(WriteListener wl) {
         throw new UnsupportedOperationException("Not supported");
      }
   }

   private static class DirectByteArrayOutputStream extends ByteArrayOutputStream {
      public DirectByteArrayOutputStream(int initialCapacity) {
         super(initialCapacity);
      }

      public ByteBuffer getByteBuffer() {
         return ByteBuffer.wrap(this.buf, 0, this.count);
      }
   }
}
