package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;

public final class ReaderHandler extends Reader {
   private char[] chars = null;
   private int idx = 0;
   private boolean eof = false;
   private boolean closed = false;
   private ReaderBlockGetter rbg = null;
   private int id = 0;
   private boolean marked = false;

   public void setReaderBlockGetter(ReaderBlockGetter rbg, int id) {
      synchronized(this.lock) {
         this.rbg = rbg;
         this.id = id;
      }
   }

   public int read(char[] inChars) throws IOException {
      if (inChars == null) {
         throw new NullPointerException();
      } else {
         int charsRead = true;

         try {
            int charsRead = this.read(inChars, 0, inChars.length);
            return charsRead;
         } catch (IOException var4) {
            throw new IOException(var4.getMessage());
         }
      }
   }

   public int read(char[] recv_buf, int offset, int count) throws IOException {
      if (count <= 0) {
         return 0;
      } else {
         synchronized(this.lock) {
            this.checkIfClosed();
            if (this.eof) {
               return -1;
            } else {
               int chars_copied = 0;

               try {
                  for(int chars_remaining = count - chars_copied; chars_remaining > 0; chars_remaining = count - chars_copied) {
                     this.refreshCacheIfNeeded();
                     if (this.eof) {
                        int var10000 = chars_copied;
                        return var10000;
                     }

                     int thisChunkSize = Math.min(this.chars.length - this.idx, chars_remaining);
                     System.arraycopy(this.chars, this.idx, recv_buf, offset, thisChunkSize);
                     chars_copied += thisChunkSize;
                     this.idx += thisChunkSize;
                     offset += thisChunkSize;
                  }

                  return chars_copied;
               } catch (IOException var9) {
                  return chars_copied;
               }
            }
         }
      }
   }

   public int read() throws IOException {
      synchronized(this.lock) {
         this.checkIfClosed();
         if (this.eof) {
            return -1;
         } else {
            this.refreshCacheIfNeeded();
            return this.eof ? -1 : this.chars[this.idx++] & 255;
         }
      }
   }

   public long skip(long n) throws IOException {
      if (n <= 0L) {
         return 0L;
      } else {
         this.checkIfClosed();
         int charsRead = 0;

         try {
            if (!this.eof) {
               while((long)charsRead < n && this.read() != -1) {
                  ++charsRead;
               }
            }
         } catch (IOException var5) {
            throw new IOException(var5.getMessage());
         }

         return (long)charsRead;
      }
   }

   public void mark(int readLimit) throws IOException {
      try {
         if (this.rbg.markSupported(this.id)) {
            this.rbg.mark(this.id, readLimit);
            this.marked = true;
         }

      } catch (IOException var3) {
         throw new IOException(var3.getMessage());
      }
   }

   public void reset() throws IOException {
      if (this.marked) {
         try {
            this.rbg.reset(this.id);
            this.resetCache();
         } catch (IOException var2) {
            throw new IOException(var2.getMessage());
         }
      } else {
         throw new IOException("Reader was not marked.");
      }
   }

   public boolean markSupported() {
      return this.rbg == null ? false : this.rbg.markSupported(this.id);
   }

   public boolean ready() throws IOException {
      boolean isReady = false;

      try {
         isReady = this.rbg.ready(this.id);
         return isReady;
      } catch (IOException var3) {
         throw new IOException(var3.getMessage());
      }
   }

   private void refreshCacheIfNeeded() throws IOException {
      if (this.chars == null || this.idx >= this.chars.length) {
         try {
            this.chars = this.rbg.getBlock(this.id);
            this.idx = 0;
            if (this.chars == null) {
               this.eof = true;
            }
         } catch (RemoteException var2) {
            this.resetCache();
            throw new IOException(var2.toString());
         }
      }

   }

   private void resetCache() {
      this.chars = null;
      this.eof = false;
      this.closed = false;
      this.idx = 0;
   }

   private void checkIfClosed() throws IOException {
      if (this.closed) {
         throw new IOException("Reader already closed");
      }
   }

   public void close() {
      synchronized(this.lock) {
         if (!this.closed) {
            this.closed = true;
            this.rbg.close(this.id);
            this.eof = true;
            this.chars = null;
            this.rbg = null;
         }
      }
   }
}
