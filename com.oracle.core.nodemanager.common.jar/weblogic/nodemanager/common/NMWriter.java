package weblogic.nodemanager.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class NMWriter {
   private final MyBufferedOutputStream out;

   public NMWriter(OutputStream outputStream) {
      this.out = new MyBufferedOutputStream(outputStream);
   }

   public NMWriter(OutputStream outputStream, int chunkLength) {
      this.out = new MyBufferedOutputStream(outputStream, new Chunk(chunkLength));
   }

   public void writeLine(String line) throws IOException {
      this.out.writeLine(line);
   }

   public void writeObject(Object obj) throws IOException {
      this.out.writeObject(obj);
   }

   public OutputStream getOutputStream() {
      return this.out.getOutputStream();
   }

   public void copy(InputStream is) throws IOException {
      int read;
      while((read = is.read()) != -1) {
         this.out.writeByte((byte)read);
      }

      this.out.endChunk();
      this.out.flush();
   }

   public void copy(InputStream is, long bytesToCopy) throws IOException {
      int read;
      for(long writtenSize = 0L; (read = is.read()) != -1 && writtenSize < bytesToCopy; ++writtenSize) {
         this.out.writeByte((byte)read);
      }

      this.out.endChunk();
      this.out.flush();
   }

   private static class MyBufferedOutputStream extends BufferedOutputStream {
      private final Chunk chunk;

      public MyBufferedOutputStream(OutputStream outputStream) {
         this(outputStream, new Chunk());
      }

      MyBufferedOutputStream(OutputStream outputStream, Chunk chunk) {
         super(outputStream);
         if (chunk == null) {
            throw new NullPointerException();
         } else {
            this.chunk = chunk;
         }
      }

      public void writeLine(String line) throws IOException {
         byte[] bytes = line.getBytes();
         this.writeBytes(bytes, 0, bytes.length);
         this.flush();
      }

      public void writeObject(Object obj) throws IOException {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);

         try {
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes = baos.toByteArray();
            this.writeBytes(bytes, 0, bytes.length);
            this.flush();
         } finally {
            baos.close();
            oos.close();
         }

      }

      private void writeBytes(byte[] bytes, int start, int length) throws IOException {
         this.chunk.reset();

         for(int i = 0; i < start + length; ++i) {
            this.writeByte(bytes[i]);
         }

         this.endChunk();
      }

      protected void endChunk() throws IOException {
         this.writeChunk();
         this.chunk.reset();
         this.writeChunk();
      }

      protected void writeByte(byte aByte) throws IOException {
         if (this.chunk.atEnd()) {
            this.writeChunk();
            this.chunk.reset();
         }

         this.chunk.write(aByte);
      }

      private void writeChunk() throws IOException {
         this.chunk.close();
         this.write(this.chunk.getBytes(), 0, this.chunk.getPos());
      }

      public OutputStream getOutputStream() {
         return new MyOutputStream(this);
      }

      private static class MyOutputStream extends FilterOutputStream {
         protected MyOutputStream(MyBufferedOutputStream out) {
            super(out);
         }

         public void write(int b) throws IOException {
            if (this.out != null) {
               ((MyBufferedOutputStream)this.out).writeByte((byte)b);
            } else {
               throw new IOException();
            }
         }

         public void close() throws IOException {
            if (this.out != null) {
               ((MyBufferedOutputStream)this.out).endChunk();
               this.out.flush();
               this.out = null;
            }

         }
      }
   }
}
