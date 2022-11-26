package weblogic.io.common.internal;

import java.io.IOException;
import weblogic.common.T3Exception;

public final class T3RemoteOutputStream implements T3RemoteConstants, OneWayOutputClient {
   private int bufferSize;
   private int currentBufferNum;
   private boolean closed;
   private WriteResponse writeRes;
   private CommandResponse flushRes;
   private CommandResponse closeRes;
   private byte[] target;
   private int targetOffset;
   private boolean aborted;
   private String abortError;
   private boolean flushRequired;
   private OneWayOutputServer onewayServer;

   public T3RemoteOutputStream(int bufferSize, int writeBehind) throws T3Exception {
      this.bufferSize = bufferSize;
      if (bufferSize < 1) {
         throw new T3Exception("bufferSize must be positive");
      } else if (writeBehind < 0) {
         throw new T3Exception("writeBehind cannot be negative");
      } else {
         this.closed = false;
         this.writeRes = new WriteResponse(writeBehind);
         this.flushRes = new CommandResponse();
         this.closeRes = new CommandResponse();
         this.target = null;
         this.currentBufferNum = 0;
         this.aborted = false;
         this.flushRequired = false;
      }
   }

   public void setOneWayRemote(OneWayOutputServer obj) {
      this.onewayServer = obj;
   }

   public synchronized void write(byte[] source, int sourceOffset, int sourceRequestedBytes) throws IOException {
      if (this.closed) {
         throw new IOException("Attempt to write to closed file");
      } else if (this.aborted) {
         throw new IOException(this.abortError);
      } else if (sourceRequestedBytes > source.length - sourceOffset) {
         throw new IOException("Insufficient data for write: " + sourceRequestedBytes + " requested, " + (source.length - sourceOffset) + " available");
      } else {
         while(sourceRequestedBytes > 0) {
            if (this.target == null) {
               this.target = new byte[this.bufferSize];
               this.targetOffset = 0;
            }

            int bytesThisTime = Math.min(this.bufferSize - this.targetOffset, sourceRequestedBytes);
            System.arraycopy(source, sourceOffset, this.target, this.targetOffset, bytesThisTime);
            this.targetOffset += bytesThisTime;
            sourceOffset += bytesThisTime;
            sourceRequestedBytes -= bytesThisTime;
            if (this.targetOffset == this.bufferSize) {
               this.writeRes.waitAround(this.currentBufferNum);
               this.sendWrite(this.currentBufferNum, this.target);
               this.flushRequired = true;
               this.target = null;
               ++this.currentBufferNum;
            }
         }

      }
   }

   public void write(int b) throws IOException {
      byte[] ba = new byte[]{(byte)b};
      this.write(ba, 0, 1);
   }

   public synchronized void flush() throws IOException {
      if (this.closed) {
         throw new IOException("Attempt to flush closed file");
      } else if (this.aborted) {
         throw new IOException(this.abortError);
      } else {
         if (this.target != null && this.targetOffset != 0) {
            this.writeRes.waitAround(this.currentBufferNum);
            byte[] slice = new byte[this.targetOffset];
            System.arraycopy(this.target, 0, slice, 0, this.targetOffset);
            this.sendWrite(this.currentBufferNum, slice);
            this.flushRequired = true;
            this.target = null;
            ++this.currentBufferNum;
         }

         if (this.flushRequired) {
            this.sendFlush(this.currentBufferNum - 1);
            this.flushRequired = false;
         }

      }
   }

   public void close() throws IOException {
      if (this.aborted) {
         throw new IOException(this.abortError);
      } else {
         if (!this.closed) {
            this.flush();
            this.closed = true;
            this.sendClose();
            this.writeRes.waitAroundExactly(this.currentBufferNum - 1);
         }

      }
   }

   private void cancel(String error) {
      this.aborted = true;
      this.abortError = error;
      this.writeRes.cancel(error);
      this.flushRes.cancel(error);
      this.closeRes.cancel(error);
   }

   private void sendWrite(int bufferNum, byte[] buffer) {
      this.onewayServer.write(bufferNum, buffer);
   }

   private void sendFlush(int bufferNum) throws IOException {
      this.onewayServer.flush(bufferNum);
      this.flushRes.waitAround();
   }

   private void sendClose() throws IOException {
      this.onewayServer.close();
      this.closeRes.waitAround();
   }

   public void writeResult(int bufferNum) {
      this.writeRes.signal(bufferNum);
   }

   public void flushResult() {
      this.flushRes.signal();
   }

   public void closeResult() {
      this.closeRes.signal();
   }

   public void error(Exception error) {
      this.cancel(error.getMessage());
   }
}
