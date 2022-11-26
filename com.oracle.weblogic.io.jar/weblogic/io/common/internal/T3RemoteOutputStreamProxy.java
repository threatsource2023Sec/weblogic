package weblogic.io.common.internal;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.common.T3MiscLogger;

public final class T3RemoteOutputStreamProxy implements T3RemoteConstants, OneWayOutputServer {
   private OutputStream os;
   private int nextBufferNum;
   private OneWayOutputClient onewayClient;
   private static final int SERVER_TIMEOUT_MILLSECS = 120000;

   public T3RemoteOutputStreamProxy(String name, OutputStream os, int bufferSize, OneWayOutputClient onewayClObj) {
      this.os = os;
      this.onewayClient = onewayClObj;
      this.nextBufferNum = 0;
   }

   public synchronized void write(int bufferNum, byte[] buffer) {
      while(true) {
         if (bufferNum != this.nextBufferNum) {
            int oldNextBufferNum = this.nextBufferNum;

            try {
               this.wait(120000L);
            } catch (InterruptedException var5) {
            }

            if (oldNextBufferNum != this.nextBufferNum) {
               continue;
            }

            T3MiscLogger.logWriteTimed(this.nextBufferNum, bufferNum);
            return;
         }

         try {
            this.os.write(buffer);
         } catch (IOException var6) {
            this.onewayClient.error(var6);
         }

         this.onewayClient.writeResult(this.nextBufferNum);
         ++this.nextBufferNum;
         this.notifyAll();
         return;
      }
   }

   public synchronized void flush(int bufferNum) {
      while(true) {
         if (this.nextBufferNum <= bufferNum) {
            int oldNextBufferNum = this.nextBufferNum;

            try {
               this.wait(120000L);
            } catch (InterruptedException var4) {
            }

            if (oldNextBufferNum != this.nextBufferNum) {
               continue;
            }

            T3MiscLogger.logFlushTimed(this.nextBufferNum, bufferNum);
            return;
         }

         try {
            this.os.flush();
         } catch (IOException var5) {
            this.onewayClient.error(var5);
         }

         this.onewayClient.flushResult();
         return;
      }
   }

   public synchronized void close() {
      try {
         this.os.close();
         this.onewayClient.closeResult();
      } catch (IOException var2) {
         T3MiscLogger.logCloseException(var2);
      }

   }
}
