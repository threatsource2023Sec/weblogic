package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;

public final class InputStreamHandler extends InputStream {
   private byte[] bytes = null;
   private int idx = 0;
   private boolean eof = false;
   private boolean closed = false;
   private BlockGetter bg = null;
   private int id = 0;
   private boolean marked = false;

   public void setBlockGetter(BlockGetter bg, int id) {
      synchronized(this) {
         this.bg = bg;
         this.id = id;
      }
   }

   public int read() throws IOException {
      synchronized(this) {
         this.checkIfClosed();
         if (this.eof) {
            return -1;
         } else {
            this.refreshCacheIfNeeded();
            return this.eof ? -1 : this.bytes[this.idx++] & 255;
         }
      }
   }

   public int read(byte[] inBytes, int offset, int length) throws IOException {
      if (inBytes == null) {
         throw new NullPointerException();
      } else if (offset >= 0 && offset <= inBytes.length && length >= 0 && offset + length <= inBytes.length && offset + length >= 0) {
         if (length == 0) {
            return 0;
         } else {
            synchronized(this) {
               this.checkIfClosed();
               if (this.eof) {
                  return -1;
               } else {
                  int bytes_read = 0;

                  try {
                     for(int bytes_remaining = length - bytes_read; bytes_remaining > 0; bytes_remaining = length - bytes_read) {
                        this.refreshCacheIfNeeded();
                        if (this.eof) {
                           int var10000 = bytes_read;
                           return var10000;
                        }

                        int thisChunkSize = Math.min(this.bytes.length - this.idx, bytes_remaining);
                        System.arraycopy(this.bytes, this.idx, inBytes, offset, thisChunkSize);
                        bytes_read += thisChunkSize;
                        this.idx += thisChunkSize;
                        offset += thisChunkSize;
                     }
                  } catch (IOException var9) {
                  }

                  return bytes_read;
               }
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int read(byte[] inBytes) throws IOException {
      if (inBytes == null) {
         throw new NullPointerException();
      } else {
         int bytesRead = true;

         try {
            int bytesRead = this.read(inBytes, 0, inBytes.length);
            return bytesRead;
         } catch (IOException var4) {
            throw new IOException(var4.getMessage());
         }
      }
   }

   public long skip(long n) throws IOException {
      if (n <= 0L) {
         return 0L;
      } else {
         this.checkIfClosed();
         int bytesRead = 0;

         try {
            if (!this.eof) {
               while((long)bytesRead < n && this.read() != -1) {
                  ++bytesRead;
               }
            }
         } catch (IOException var5) {
            throw new IOException(var5.getMessage());
         }

         return (long)bytesRead;
      }
   }

   public int available() throws IOException {
      int canBeRead = false;

      try {
         int canBeRead = this.bg.available(this.id);
         return canBeRead;
      } catch (IOException var3) {
         throw new IOException(var3.getMessage());
      }
   }

   public void mark(int readLimit) {
      if (this.bg.markSupported(this.id)) {
         this.bg.mark(this.id, readLimit);
         this.marked = true;
      }

   }

   public void reset() throws IOException {
      if (this.marked) {
         try {
            this.bg.reset(this.id);
            this.resetCache();
         } catch (IOException var2) {
            throw new IOException(var2.getMessage());
         }
      } else {
         throw new IOException("Stream was not marked.");
      }
   }

   public boolean markSupported() {
      return this.bg.markSupported(this.id);
   }

   private void refreshCacheIfNeeded() throws IOException {
      if (this.bytes == null || this.idx >= this.bytes.length) {
         try {
            this.bytes = this.bg.getBlock(this.id);
            this.idx = 0;
            if (this.bytes == null) {
               this.eof = true;
            }
         } catch (RemoteException var2) {
            this.resetCache();
            throw new IOException(var2.toString());
         }
      }

   }

   private void resetCache() {
      this.bytes = null;
      this.eof = false;
      this.closed = false;
      this.idx = 0;
   }

   private void checkIfClosed() throws IOException {
      if (this.closed) {
         throw new IOException("Stream already closed");
      }
   }

   public void close() {
      synchronized(this) {
         if (!this.closed) {
            this.closed = true;
            this.bg.close(this.id);
            this.eof = true;
            this.bytes = null;
            this.bg = null;
         }
      }
   }
}
