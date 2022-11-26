package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

class IllegalStateChunkOutput extends ChunkOutput {
   IllegalStateException exception;

   IllegalStateChunkOutput(IllegalStateException e) {
      this.exception = e;
   }

   void writeByte(int i) throws IOException {
      throw this.exception;
   }

   public void write(int i) throws IOException {
      throw this.exception;
   }

   public void write(byte[] b, int off, int len) throws IOException {
      throw this.exception;
   }

   public void write(ByteBuffer buf) throws IOException {
      throw this.exception;
   }

   public void write(char[] c, int off, int len) throws IOException {
      throw this.exception;
   }

   public void print(String s) throws IOException {
      throw this.exception;
   }

   public void println(String s) throws IOException {
      throw this.exception;
   }

   public void println() throws IOException {
      throw this.exception;
   }

   public void commit() throws IOException {
      throw this.exception;
   }

   public void flush() throws IOException {
      throw this.exception;
   }

   public void writeStream(InputStream is, long len, long clen) throws IOException {
      throw this.exception;
   }
}
