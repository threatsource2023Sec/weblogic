package weblogic.servlet.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.WebConnection;
import weblogic.utils.http.HttpChunkInputStream;
import weblogic.utils.io.Chunk;

public final class ServletInputStreamImpl extends ServletInputStream {
   private InputStream in = this;
   private long nread;
   private boolean isClosed = false;
   private AbstractHttpConnectionHandler connHandler = null;
   private ReadListenerStateContext readStateContext = null;
   private ServletRequestImpl request = null;
   private WebConnection webConnection = null;
   private boolean isUpgrade = false;

   public ServletInputStreamImpl(InputStream is) {
      this.in = is;
      this.nread = 0L;
   }

   public ServletInputStreamImpl(InputStream is, AbstractHttpConnectionHandler connHandler) {
      this.in = is;
      this.nread = 0L;
      this.connHandler = connHandler;
      this.request = connHandler.getServletRequest();
   }

   long getBytesRead() {
      return this.nread;
   }

   boolean isClosed() {
      return this.isClosed;
   }

   void ensureChunkedConsumed() throws IOException {
      if (this.in instanceof HttpChunkInputStream) {
         HttpChunkInputStream cis = (HttpChunkInputStream)this.in;
         if (cis.hasUnconsumedChunk()) {
            cis.skipAllChunk();
         }
      }

   }

   public int available() throws IOException {
      return this.in.available();
   }

   public void close() throws IOException {
      this.in.close();
      this.isClosed = true;
   }

   public void mark(int readlimit) {
      if (!this.in.markSupported()) {
         this.in = new FilterInputStream(this.in) {
            private int pos = -1;
            private int size = -1;
            private byte[] markBuffer;
            private boolean markSet = false;

            public long skip(long n) throws IOException {
               if (n <= 0L) {
                  return 0L;
               } else {
                  long remaining = n;
                  int nr;
                  if (this.markSet && this.size > this.pos) {
                     nr = this.size - this.pos;
                     if ((long)nr >= n) {
                        this.pos = (int)((long)this.pos + n);
                        return n;
                     }

                     this.markSet = false;
                     remaining = n - (long)nr;
                  }

                  Chunk skipBuffer = Chunk.getChunk();

                  long var7;
                  try {
                     while(remaining > 0L) {
                        nr = this.read(skipBuffer.buf, 0, (int)Math.min((long)Chunk.CHUNK_SIZE, remaining));
                        if (nr < 0) {
                           break;
                        }

                        remaining -= (long)nr;
                     }

                     var7 = n - remaining;
                  } finally {
                     Chunk.releaseChunk(skipBuffer);
                  }

                  return var7;
               }
            }

            public void mark(int limit) {
               if (this.pos != this.size) {
                  int bytesInBuffer = this.size - this.pos;
                  if (this.markBuffer != null && this.markBuffer.length < limit) {
                     byte[] newMarkBuffer = new byte[limit];
                     System.arraycopy(this.markBuffer, this.pos, newMarkBuffer, 0, bytesInBuffer);
                     this.markBuffer = newMarkBuffer;
                  } else {
                     System.arraycopy(this.markBuffer, this.pos, this.markBuffer, 0, bytesInBuffer);
                  }

                  this.size = bytesInBuffer;
               } else {
                  if (this.markBuffer == null || this.markBuffer.length < limit) {
                     this.markBuffer = new byte[limit];
                  }

                  this.size = 0;
               }

               this.markSet = true;
               this.pos = 0;
            }

            public int available() throws IOException {
               int n = this.size - this.pos;
               int avail = super.available();
               return n > Integer.MAX_VALUE - avail ? Integer.MAX_VALUE : n + avail;
            }

            public boolean markSupported() {
               return true;
            }

            public int read() throws IOException {
               if (!this.markSet) {
                  return this.in.read();
               } else {
                  if (this.pos == this.size) {
                     int r = this.in.read();
                     if (r == -1) {
                        return -1;
                     }

                     if (this.size == this.markBuffer.length) {
                        this.markSet = false;
                        return r;
                     }

                     this.markBuffer[this.size++] = (byte)r;
                  }

                  return this.markBuffer[this.pos++] & 255;
               }
            }

            public int read(byte[] buf, int off, int length) throws IOException {
               if (!this.markSet) {
                  return this.in.read(buf, off, length);
               } else {
                  int newBytesRead = 0;
                  int bytesInBuffer = this.size - this.pos;
                  int newBytesRequired = length - bytesInBuffer;
                  int toCopy = Math.min(this.size - this.pos, length);
                  System.arraycopy(this.markBuffer, this.pos, buf, off, toCopy);
                  this.pos += toCopy;
                  if (newBytesRequired > 0) {
                     int newDataOffset = off + bytesInBuffer;
                     newBytesRead = this.in.read(buf, newDataOffset, newBytesRequired);
                     if (newBytesRead == -1) {
                        if (bytesInBuffer == 0) {
                           return -1;
                        }

                        newBytesRead = 0;
                     }

                     if (this.size + newBytesRead > this.markBuffer.length) {
                        this.markSet = false;
                        return bytesInBuffer + newBytesRead;
                     }

                     System.arraycopy(buf, newDataOffset, this.markBuffer, this.size, newBytesRead);
                     this.size += newBytesRead;
                     this.pos += newBytesRead;
                  }

                  return toCopy + newBytesRead;
               }
            }

            public void reset() {
               this.pos = 0;
            }
         };
      }

      this.in.mark(readlimit);
   }

   public boolean markSupported() {
      return true;
   }

   public int read() throws IOException {
      int n = this.in.read();
      if (n > 0) {
         ++this.nread;
      }

      return n;
   }

   public int read(byte[] b) throws IOException {
      int n = this.in.read(b);
      if (n > 0) {
         this.nread += (long)n;
      }

      return n;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      int n = this.in.read(b, off, len);
      if (n > 0) {
         this.nread += (long)n;
      }

      return n;
   }

   public void reset() throws IOException {
      this.in.reset();
      this.nread = 0L;
   }

   public long skip(long n) throws IOException {
      return this.in.skip(n);
   }

   boolean isUpgrade() {
      return this.isUpgrade;
   }

   void setUpgrade(boolean isUpgrade) {
      this.isUpgrade = isUpgrade;
   }

   public boolean isReady() {
      if (this.isClosed) {
         return false;
      } else {
         return this.readStateContext == null ? false : this.readStateContext.isReadReady();
      }
   }

   public boolean isFinished() {
      if (this.isClosed) {
         return true;
      } else if (this.readStateContext == null) {
         return false;
      } else {
         return this.readStateContext.isFinished() || this.readStateContext.isReadComplete();
      }
   }

   public void setReadListener(ReadListener readListener) {
      if (readListener == null) {
         if (this.readStateContext != null) {
            throw new IllegalStateException("Could not switch back to Blocking IO!");
         } else {
            throw new NullPointerException("Listener should not be set to null!");
         }
      } else if (this.readStateContext != null) {
         throw new IllegalStateException("Already set a ReaderListener!");
      } else if (!this.request.isAsyncMode() && !this.isUpgrade()) {
         throw new IllegalStateException("Cannot set ReaderListener for non-async or non-upgrade request!");
      } else if (this.request.isAsyncMode() && !this.request.isAsyncStarted()) {
         throw new IllegalStateException("Cannot set ReaderListener for an async request which is not started status!");
      } else {
         this.readStateContext = new ReadListenerStateContext(readListener, this, this.request.getContext());
         if (this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) {
            this.request.getConnection().getConnectionHandler().getStream().setReadListener(this.readStateContext);
         } else {
            ((MuxableSocketHTTP)this.connHandler.getRawConnection()).setReadListener(this.readStateContext, this, this.request.isAsyncMode(), this.request.getRequestHeaders().isChunked(), this.request.getRequestHeaders().getContentLength());
         }

         if (this.request.isAsyncMode()) {
            this.readStateContext.setAsyncContext(this.request.getAsyncContext());
         }

         if (this.isReady()) {
            this.readStateContext.process();
         } else if (this.readStateContext.isReadComplete()) {
            this.readStateContext.scheduleProcess();
         } else if (this.readStateContext.isReadWait() && !this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) {
            this.connHandler.getRawConnection().registerForReadEvent();
         }

      }
   }

   void setInputStream(InputStream in) {
      this.in = in;
   }

   public InputStream getInputStream() {
      return this.in;
   }

   protected WebConnection getWebConnection() {
      return this.webConnection;
   }

   protected void setWebConnection(WebConnection webConnection) {
      this.webConnection = webConnection;
   }
}
