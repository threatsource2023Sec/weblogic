package com.trilead.ssh2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

public class StreamGobbler extends InputStream {
   private InputStream is;
   private final GobblerThread t;
   private final Object synchronizer = new Object();
   private boolean isEOF = false;
   private boolean isClosed = false;
   private IOException exception = null;
   private byte[] buffer = new byte[2048];
   private int read_pos = 0;
   private int write_pos = 0;

   public StreamGobbler(InputStream is) {
      this.is = is;
      this.t = new GobblerThread();
      this.t.setDaemon(true);
      this.t.start();
   }

   public int read() throws IOException {
      synchronized(this.synchronizer) {
         if (this.isClosed) {
            throw new IOException("This StreamGobbler is closed.");
         } else {
            while(this.read_pos == this.write_pos) {
               if (this.exception != null) {
                  throw this.exception;
               }

               if (this.isEOF) {
                  return -1;
               }

               try {
                  this.synchronizer.wait();
               } catch (InterruptedException var4) {
                  throw new InterruptedIOException();
               }
            }

            int b = this.buffer[this.read_pos++] & 255;
            return b;
         }
      }
   }

   public int available() throws IOException {
      synchronized(this.synchronizer) {
         if (this.isClosed) {
            throw new IOException("This StreamGobbler is closed.");
         } else {
            return this.write_pos - this.read_pos;
         }
      }
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public void close() throws IOException {
      synchronized(this.synchronizer) {
         if (!this.isClosed) {
            this.isClosed = true;
            this.isEOF = true;
            this.synchronizer.notifyAll();
            this.is.close();
         }
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && len >= 0 && off + len <= b.length && off + len >= 0 && off <= b.length) {
         if (len == 0) {
            return 0;
         } else {
            synchronized(this.synchronizer) {
               if (this.isClosed) {
                  throw new IOException("This StreamGobbler is closed.");
               } else {
                  while(this.read_pos == this.write_pos) {
                     if (this.exception != null) {
                        throw this.exception;
                     }

                     if (this.isEOF) {
                        return -1;
                     }

                     try {
                        this.synchronizer.wait();
                     } catch (InterruptedException var7) {
                        throw new InterruptedIOException();
                     }
                  }

                  int avail = this.write_pos - this.read_pos;
                  avail = avail > len ? len : avail;
                  System.arraycopy(this.buffer, this.read_pos, b, off, avail);
                  this.read_pos += avail;
                  return avail;
               }
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   class GobblerThread extends Thread {
      public void run() {
         byte[] buff = new byte[8192];

         while(true) {
            try {
               int avail = StreamGobbler.this.is.read(buff);
               synchronized(StreamGobbler.this.synchronizer) {
                  if (avail <= 0) {
                     StreamGobbler.this.isEOF = true;
                     StreamGobbler.this.synchronizer.notifyAll();
                     break;
                  }

                  int space_available = StreamGobbler.this.buffer.length - StreamGobbler.this.write_pos;
                  if (space_available < avail) {
                     int unread_size = StreamGobbler.this.write_pos - StreamGobbler.this.read_pos;
                     int need_space = unread_size + avail;
                     byte[] new_buffer = StreamGobbler.this.buffer;
                     if (need_space > StreamGobbler.this.buffer.length) {
                        int inc = need_space / 3;
                        inc = inc < 256 ? 256 : inc;
                        inc = inc > 8192 ? 8192 : inc;
                        new_buffer = new byte[need_space + inc];
                     }

                     if (unread_size > 0) {
                        System.arraycopy(StreamGobbler.this.buffer, StreamGobbler.this.read_pos, new_buffer, 0, unread_size);
                     }

                     StreamGobbler.this.buffer = new_buffer;
                     StreamGobbler.this.read_pos = 0;
                     StreamGobbler.this.write_pos = unread_size;
                  }

                  System.arraycopy(buff, 0, StreamGobbler.this.buffer, StreamGobbler.this.write_pos, avail);
                  StreamGobbler.this.write_pos = avail;
                  StreamGobbler.this.synchronizer.notifyAll();
               }
            } catch (IOException var13) {
               IOException e = var13;
               synchronized(StreamGobbler.this.synchronizer) {
                  StreamGobbler.this.exception = e;
                  StreamGobbler.this.synchronizer.notifyAll();
                  break;
               }
            }
         }

      }
   }
}
