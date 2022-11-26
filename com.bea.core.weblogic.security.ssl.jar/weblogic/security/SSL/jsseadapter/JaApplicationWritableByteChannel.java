package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;

final class JaApplicationWritableByteChannel implements WritableByteChannel {
   private final JaSSLEngineRunner.Context sslEngineRunnerContext;

   public int write(ByteBuffer src) throws IOException {
      if (null == src) {
         throw new IllegalArgumentException("Non-null source expected.");
      } else {
         int origSrcRemaining = src.remaining();
         if (origSrcRemaining <= 0) {
            return 0;
         } else {
            this.sslEngineRunnerContext.getSync().lock(JaSSLEngineSynchronizer.LockState.OUTBOUND);

            try {
               this.sslEngineRunnerContext.setBufferAppOut(src);

               try {
                  src.compact();
                  JaSSLEngineRunner.RunnerResult result = JaSSLEngineRunner.wrap(this.sslEngineRunnerContext);
                  if (null != result && JaSSLEngineRunner.RunnerResult.CLOSED == result) {
                     if (JaLogger.isLoggable(Level.FINER)) {
                        JaLogger.log(Level.FINER, JaLogger.Component.SSLSOCKET, "{0}[{1}]: Unable to write: SSL channel is closed.", this.getClass().getName(), this.hashCode());
                     }

                     throw new ClosedChannelException();
                  }
               } finally {
                  src.flip();
                  this.sslEngineRunnerContext.setBufferAppOut((ByteBuffer)null);
               }
            } finally {
               this.sslEngineRunnerContext.getSync().unlock();
            }

            int var12 = origSrcRemaining - src.remaining();
            return var12;
         }
      }
   }

   public void close() throws IOException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "[{0}] close() called.", this.getClass().getName());
      }

      try {
         JaSSLEngineRunner.RunnerResult var1 = JaSSLEngineRunner.closeOutbound(this.sslEngineRunnerContext);
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

      return !JaSSLEngineRunner.isOutboundDone(this.sslEngineRunnerContext);
   }

   JaApplicationWritableByteChannel(JaSSLEngineRunner.Context sslEngineRunnerContext) {
      if (null == sslEngineRunnerContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLEngineRunner.Context.");
      } else {
         this.sslEngineRunnerContext = sslEngineRunnerContext;
      }
   }
}
