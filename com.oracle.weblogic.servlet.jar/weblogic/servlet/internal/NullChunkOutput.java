package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

class NullChunkOutput extends ChunkOutput {
   void writeByte(int i) throws IOException {
   }

   public void write(int i) throws IOException {
   }

   public void write(byte[] b, int off, int len) throws IOException {
   }

   public void write(char[] c, int off, int len) throws IOException {
   }

   public void write(ByteBuffer buf) throws IOException {
   }

   public void print(String s) throws IOException {
   }

   public void println(String s) throws IOException {
   }

   public void println() throws IOException {
   }

   public void writeStream(InputStream is, long len, long clen) throws IOException {
   }

   public void flush() throws IOException {
   }

   public void commit() throws IOException {
   }
}
