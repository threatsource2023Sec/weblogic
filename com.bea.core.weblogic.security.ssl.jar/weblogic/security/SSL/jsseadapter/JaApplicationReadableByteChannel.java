package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;

final class JaApplicationReadableByteChannel implements ReadableByteChannel {
   private final JaSSLEngineRunner.Context sslEngineRunnerContext;

   public int read(ByteBuffer dst) throws IOException {
      if (null == dst) {
         throw new IllegalArgumentException("Non-null destination expected.");
      } else if (dst.remaining() <= 0) {
         return 0;
      } else {
         try {
            this.sslEngineRunnerContext.getSync().lock(JaSSLEngineSynchronizer.LockState.INBOUND);

            try {
               ByteBuffer appIn = this.sslEngineRunnerContext.getBufferAppIn();
               int remainingToRead = calcRemainingToRead(appIn);
               if (remainingToRead <= 0) {
                  JaSSLEngineRunner.RunnerResult result = JaSSLEngineRunner.unwrap(this.sslEngineRunnerContext);
                  if (result == JaSSLEngineRunner.RunnerResult.CLOSED) {
                     byte var31 = -1;
                     return var31;
                  }

                  appIn = this.sslEngineRunnerContext.getBufferAppIn();
                  remainingToRead = calcRemainingToRead(appIn);
               }

               int bytesRead = 0;
               int excessRemaining;
               if (remainingToRead > 0) {
                  try {
                     appIn.flip();
                     excessRemaining = appIn.remaining() - dst.remaining();
                     if (excessRemaining <= 0) {
                        bytesRead = appIn.remaining();
                        dst.put(appIn);
                     } else {
                        int oldLimit = appIn.limit();
                        appIn.limit(oldLimit - excessRemaining);
                        bytesRead = appIn.remaining();
                        dst.put(appIn);
                        appIn.limit(oldLimit);
                     }
                  } finally {
                     appIn.compact();
                  }
               }

               excessRemaining = bytesRead;
               return excessRemaining;
            } finally {
               this.sslEngineRunnerContext.getSync().unlock();
            }
         } catch (InterruptedIOException var27) {
            throw var27;
         } catch (IOException var28) {
            try {
               JaSSLEngineRunner.closeOutbound(this.sslEngineRunnerContext);
            } catch (Exception var23) {
            }

            throw var28;
         } catch (RuntimeException var29) {
            try {
               JaSSLEngineRunner.closeOutbound(this.sslEngineRunnerContext);
            } catch (Exception var24) {
            }

            throw var29;
         }
      }
   }

   public void close() throws IOException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "[{0}] close() called.", this.getClass().getName());
      }

      try {
         JaSSLEngineRunner.RunnerResult var1 = JaSSLEngineRunner.closeInbound(this.sslEngineRunnerContext, false);
      } catch (Exception var3) {
         if (JaLogger.isLoggable(Level.FINER)) {
            JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, var3, "[{0}] Exception occurred during close().", this.getClass().getName());
         }
      }

   }

   public boolean isOpen() {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "[{0}] isOpen() called.", this.getClass().getName());
      }

      return !JaSSLEngineRunner.isInboundDone(this.sslEngineRunnerContext);
   }

   public int available() throws IOException {
      this.sslEngineRunnerContext.getSync().lock(JaSSLEngineSynchronizer.LockState.INBOUND);

      int var2;
      try {
         ByteBuffer appIn = this.sslEngineRunnerContext.getBufferAppIn();
         var2 = calcRemainingToRead(appIn);
      } finally {
         this.sslEngineRunnerContext.getSync().unlock();
      }

      return var2;
   }

   JaApplicationReadableByteChannel(JaSSLEngineRunner.Context sslEngineRunnerContext) {
      if (null == sslEngineRunnerContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLEngineRunner.Context.");
      } else {
         this.sslEngineRunnerContext = sslEngineRunnerContext;
      }
   }

   private static int calcRemainingToRead(ByteBuffer appIn) {
      int remainingToRead = false;

      int remainingToRead;
      try {
         appIn.flip();
         remainingToRead = appIn.remaining();
      } finally {
         appIn.compact();
      }

      return remainingToRead;
   }
}
