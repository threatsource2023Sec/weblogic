package weblogic.nodemanager.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.util.Protocol;

public class NMReader {
   private final Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   private final InputStream in;
   private final Chunk chunk;

   public NMReader(InputStream in) {
      this.in = in;
      this.chunk = new Chunk();
   }

   public NMReader(InputStream in, int chunkLength) {
      this.in = in;
      this.chunk = new Chunk(chunkLength);
   }

   public String readLine() throws IOException {
      int length = 0;

      try {
         length = this.readChunk();
      } catch (Chunk.InvalidChunkLengthException var10) {
         if (Level.ALL.equals(this.nmLog.getLevel())) {
            this.nmLog.info(var10.getMessage());
         }

         if (this.chunk.getPos() == 2) {
            int initialPos = this.chunk.getPos();
            byte[] chunkBytes = this.chunk.getBytes();
            int read = this.in.read(this.chunk.getBytes(), initialPos, chunkBytes.length - initialPos);
            this.chunk.advancePos(read);
         }

         Protocol.protocolException(this.chunk.getBytes());
      }

      if (length == -1) {
         return null;
      } else if (!this.chunk.atEnd()) {
         String ret = new String(this.chunk.getBytes(), 2, length);
         this.readChunkEnd();
         return ret;
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream(2 * length);

         do {
            baos.write(this.chunk.getBytes(), 2, length);
            length = this.readChunk();
            if (length == -1) {
               return null;
            }
         } while(length != 0);

         String var12;
         try {
            var12 = baos.toString();
         } finally {
            baos.close();
         }

         return var12;
      }
   }

   public byte[] readBytes() throws IOException {
      int length = this.readChunk();
      if (length == -1) {
         return null;
      } else if (!this.chunk.atEnd()) {
         byte[] ret = new byte[length];
         System.arraycopy(this.chunk.getBytes(), 2, ret, 0, length);
         this.readChunkEnd();
         return ret;
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream(2 * length);

         do {
            baos.write(this.chunk.getBytes(), 2, length);
            length = this.readChunk();
            if (length == -1) {
               return null;
            }
         } while(length != 0);

         byte[] var3;
         try {
            var3 = baos.toByteArray();
         } finally {
            baos.close();
         }

         return var3;
      }
   }

   public Object readObject() throws IOException, ClassNotFoundException {
      int length = this.readChunk();
      if (length == -1) {
         return null;
      } else {
         Object var5;
         if (!this.chunk.atEnd()) {
            ByteArrayInputStream bais = new ByteArrayInputStream(this.chunk.getBytes(), 2, length);
            ObjectInputStream ois = new ObjectInputStream(bais);

            try {
               Object ret = ois.readObject();
               this.readChunkEnd();
               var5 = ret;
            } finally {
               ois.close();
               bais.close();
            }

            return var5;
         } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(2 * length);

            do {
               baos.write(this.chunk.getBytes(), 2, length);
               length = this.readChunk();
               if (length == -1) {
                  return null;
               }
            } while(length != 0);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            try {
               var5 = ois.readObject();
            } finally {
               baos.close();
               bais.close();
               ois.close();
            }

            return var5;
         }
      }
   }

   public void copy(OutputStream out) throws IOException {
      boolean haveRead = false;

      int length;
      do {
         length = this.readChunk();
         if (length > 0) {
            out.write(this.chunk.getBytes(), 2, length);
            haveRead = true;
         }
      } while(length > 0);

      if (length == 0 && !haveRead) {
         this.readChunkEnd();
      }

      out.flush();
   }

   public void copy(Writer out) throws IOException {
      MyInputStream is = new MyInputStream();
      InputStreamReader reader = new InputStreamReader(is);

      int read;
      while((read = reader.read()) != -1) {
         out.write(read);
      }

   }

   private void readChunkEnd() throws IOException {
      int more = this.readChunk();
      if (more != 0 && more != -1) {
         throw new IOException();
      }
   }

   private int readChunk() throws IOException {
      byte[] chunkBytes = this.chunk.getBytes();
      int chunkLength = this.readLength();
      if (chunkLength == -1) {
         return -1;
      } else {
         if (chunkLength != 0) {
            this.chunk.reset();
            int length = chunkLength;

            do {
               int read = this.in.read(chunkBytes, this.chunk.getPos(), length);
               if (read == -1) {
                  return -1;
               }

               this.chunk.advancePos(read);
               length -= read;
            } while(length > 0);
         }

         return chunkLength;
      }
   }

   private int readLength() throws IOException {
      byte[] chunkBytes = this.chunk.getBytes();
      int sizeLen = 2;

      int read;
      for(int pos = 0; sizeLen > 0; pos += read) {
         read = this.in.read(chunkBytes, pos, sizeLen);
         if (read == -1) {
            return -1;
         }

         sizeLen -= read;
      }

      return this.chunk.getLength();
   }

   private class MyInputStream extends InputStream {
      private int length;
      private int pos;
      byte[] bytes;
      boolean atEOF;
      boolean haveRead;

      private MyInputStream() {
         this.length = 0;
         this.pos = 2;
         this.bytes = NMReader.this.chunk.getBytes();
         this.atEOF = false;
         this.haveRead = false;
      }

      public int read() throws IOException {
         if (this.atEOF) {
            return -1;
         } else {
            if (this.pos == this.length + 2) {
               this.pos = 2;
               this.length = NMReader.this.readChunk();
               if (this.length == 0 && !this.haveRead) {
                  NMReader.this.readChunkEnd();
               }

               if (this.length <= 0) {
                  this.atEOF = true;
                  return -1;
               }

               if (!this.haveRead) {
                  this.haveRead = true;
               }
            }

            int ret = this.bytes[this.pos++];
            return ret;
         }
      }

      // $FF: synthetic method
      MyInputStream(Object x1) {
         this();
      }
   }
}
