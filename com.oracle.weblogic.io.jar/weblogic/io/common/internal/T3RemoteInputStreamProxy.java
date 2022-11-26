package weblogic.io.common.internal;

import java.io.IOException;
import java.io.InputStream;
import weblogic.common.T3MiscLogger;

public final class T3RemoteInputStreamProxy implements T3RemoteConstants, OneWayInputServer {
   private InputStream is;
   private int bufferSize;
   private int nextBufferNum;
   private int requestedBufferNum;
   private boolean atEOF;
   private OneWayInputClient onewayClient;

   public T3RemoteInputStreamProxy(String name, InputStream is, int bufferSize, int readAhead, OneWayInputClient onewayClient) {
      this.is = is;
      this.bufferSize = bufferSize;
      this.onewayClient = onewayClient;
      this.nextBufferNum = 0;
      this.requestedBufferNum = -1;
      this.atEOF = false;
      this.read(readAhead);
   }

   public synchronized void read(int bufferNum) {
      if (bufferNum > this.requestedBufferNum) {
         for(this.requestedBufferNum = bufferNum; this.nextBufferNum <= this.requestedBufferNum && !this.atEOF; ++this.nextBufferNum) {
            int totalBytes = 0;

            int bytesThisTime;
            byte[] buffer;
            for(buffer = new byte[this.bufferSize]; totalBytes < this.bufferSize; totalBytes += bytesThisTime) {
               try {
                  bytesThisTime = this.is.read(buffer, totalBytes, this.bufferSize - totalBytes);
               } catch (IOException var6) {
                  System.out.println("Calling onewayClient's error method.");
                  this.onewayClient.error(var6);
                  return;
               }

               if (bytesThisTime == -1) {
                  this.atEOF = true;
                  byte[] bSlice = new byte[totalBytes];
                  System.arraycopy(buffer, 0, bSlice, 0, totalBytes);
                  buffer = bSlice;
                  break;
               }
            }

            this.onewayClient.readResult(this.nextBufferNum, buffer);
         }
      }

   }

   public synchronized void skip(long requestedBytes) {
      try {
         this.onewayClient.skipResult(this.is.skip(requestedBytes));
      } catch (IOException var4) {
         this.onewayClient.error(var4);
      }

   }

   public synchronized void close() {
      try {
         this.is.close();
      } catch (IOException var2) {
         T3MiscLogger.logCloseException(var2);
      }

   }
}
