package weblogic.io.common.internal;

import java.io.IOException;
import weblogic.common.T3Exception;

public final class T3RemoteInputStream implements T3RemoteConstants, OneWayInputClient {
   private int bufferSize;
   private int readAhead;
   private int currentBufferNum;
   private int nextBufferNum;
   private boolean closed;
   private WaitHashtable buffers;
   private SkipResponse skipRes;
   private byte[] source;
   private int sourceOffset;
   private String abortError;
   private boolean stopReadAhead;
   private OneWayInputServer onewayServer;
   private byte[] tmpb = new byte[1];

   public T3RemoteInputStream(int bufferSize, int readAhead) throws T3Exception {
      this.bufferSize = bufferSize;
      this.readAhead = readAhead;
      if (bufferSize < 1) {
         throw new T3Exception("bufferSize must be positive");
      } else if (readAhead < 0) {
         throw new T3Exception("readAhead cannot be negative");
      } else {
         this.closed = false;
         this.skipRes = new SkipResponse();
         this.buffers = new WaitHashtable(2 * (readAhead + 1), 1.0F);
         this.source = null;
         this.currentBufferNum = 0;
         this.stopReadAhead = false;
         this.nextBufferNum = readAhead + 1;
      }
   }

   public void setOneWayRemote(OneWayInputServer obj) {
      this.onewayServer = obj;
   }

   public synchronized int read(byte[] target, int targetOffset, int targetRequestedBytes) throws IOException {
      int targetActualBytes = 0;
      if (this.closed) {
         throw new IOException("Attempt to read from closed file");
      } else if (targetRequestedBytes > target.length - targetOffset) {
         throw new IOException("Insufficient space for read: " + targetRequestedBytes + " requested, " + (target.length - targetOffset) + " available");
      } else {
         while(targetRequestedBytes > 0) {
            if (this.source == null) {
               this.source = (byte[])((byte[])this.buffers.removeWait(new Integer(this.currentBufferNum)));
               if (this.source == null) {
                  if (this.abortError == null) {
                     this.abortError = this.buffers.getError();
                  }

                  throw new IOException(this.abortError);
               }

               this.sourceOffset = 0;
            }

            if (this.source.length == 0) {
               if (targetActualBytes == 0) {
                  targetActualBytes = -1;
               }
               break;
            }

            int bytesThisTime = Math.min(this.source.length - this.sourceOffset, targetRequestedBytes);
            System.arraycopy(this.source, this.sourceOffset, target, targetOffset, bytesThisTime);
            targetOffset += bytesThisTime;
            this.sourceOffset += bytesThisTime;
            targetActualBytes += bytesThisTime;
            targetRequestedBytes -= bytesThisTime;
            if (this.sourceOffset == this.source.length) {
               this.source = null;
               ++this.currentBufferNum;
               if (!this.stopReadAhead) {
                  this.sendRead(this.nextBufferNum);
                  ++this.nextBufferNum;
               }
            }
         }

         return targetActualBytes;
      }
   }

   public synchronized int read() throws IOException {
      int result = this.read(this.tmpb, 0, 1);
      return result == -1 ? -1 : this.tmpb[0] & 255;
   }

   public synchronized long skip(long requestedBytes) throws IOException {
      int actualBytes = 0;
      if (this.closed) {
         throw new IOException("Attempt to skip in closed file");
      } else {
         int oldCurrentBufferNum = this.currentBufferNum;

         while(requestedBytes > 0L && this.currentBufferNum < this.nextBufferNum) {
            if (this.source == null) {
               this.source = (byte[])((byte[])this.buffers.removeWait(new Integer(this.currentBufferNum)));
               if (this.source == null) {
                  throw new IOException(this.abortError);
               }

               this.sourceOffset = 0;
            }

            if (this.source.length == 0) {
               break;
            }

            int bytesThisTime = (int)Math.min((long)(this.source.length - this.sourceOffset), requestedBytes);
            this.sourceOffset += bytesThisTime;
            actualBytes += bytesThisTime;
            requestedBytes -= (long)bytesThisTime;
            if (this.sourceOffset == this.source.length) {
               this.source = null;
               ++this.currentBufferNum;
            }
         }

         if (requestedBytes > 0L) {
            requestedBytes = this.sendSkip(requestedBytes);
            actualBytes = (int)((long)actualBytes + requestedBytes);
         }

         if (oldCurrentBufferNum < this.currentBufferNum && !this.stopReadAhead) {
            this.nextBufferNum = this.currentBufferNum + this.readAhead + 1;
            this.sendRead(this.nextBufferNum - 1);
         }

         return (long)actualBytes;
      }
   }

   public synchronized int available() throws IOException {
      int numAvail = 0;
      if (this.closed) {
         throw new IOException("Attempt to check availability of closed file");
      } else {
         if (this.source != null) {
            numAvail = this.source.length - this.sourceOffset;
         }

         int i = this.currentBufferNum + 1;

         while(true) {
            byte[] temp = (byte[])((byte[])this.buffers.get(new Integer(i)));
            if (temp == null) {
               return numAvail;
            }

            numAvail += temp.length;
            ++i;
         }
      }
   }

   public synchronized void close() throws IOException {
      if (!this.closed) {
         this.closed = true;
         if (this.source == null || this.source.length > 0) {
            while(this.currentBufferNum < this.nextBufferNum) {
               this.source = (byte[])((byte[])this.buffers.removeWait(new Integer(this.currentBufferNum)));
               if (this.source == null || this.source.length == 0) {
                  break;
               }

               ++this.currentBufferNum;
            }
         }

         this.sendClose();
      }

   }

   private void readCallback(int bufferNum, byte[] data) {
      this.buffers.put(new Integer(bufferNum), data);
      if (data.length < this.bufferSize) {
         this.stopReadAhead = true;
         if (data.length > 0) {
            this.buffers.put(new Integer(bufferNum + 1), new byte[0]);
         }
      }

   }

   private void cancel(String error) {
      this.stopReadAhead = true;
      this.abortError = error;
      this.buffers.cancel(error);
      this.skipRes.cancel(error);
   }

   private long sendSkip(long requestedBytes) throws IOException {
      this.onewayServer.skip(requestedBytes);

      try {
         Object result = this.skipRes.waitAround();
         if (result != null) {
            return (Long)result;
         } else {
            throw new IOException("Error waiting for result from skip");
         }
      } catch (IOException var4) {
         throw var4;
      }
   }

   private void sendRead(int bufferNum) {
      this.onewayServer.read(bufferNum);
   }

   private void sendClose() {
      this.onewayServer.close();
   }

   public void readResult(int bufferNum, byte[] buffer) {
      this.readCallback(bufferNum, buffer);
   }

   public void skipResult(long actualBytes) {
      this.skipRes.signal(new Long(actualBytes));
   }

   public void error(Exception error) {
      String errMsg = error.getMessage();
      this.cancel(errMsg);
   }
}
