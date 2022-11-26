package org.glassfish.grizzly.ssl;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.streams.StreamReader;
import org.glassfish.grizzly.streams.StreamWriter;
import org.glassfish.grizzly.streams.TransformerStreamWriter;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.conditions.Condition;

public class SSLStreamWriter extends TransformerStreamWriter {
   public SSLStreamWriter(StreamWriter underlyingWriter) {
      super(underlyingWriter, new SSLEncoderTransformer());
   }

   public Future handshake(SSLStreamReader sslStreamReader, SSLEngineConfigurator configurator) throws IOException {
      return this.handshake(sslStreamReader, configurator, (CompletionHandler)null);
   }

   public Future handshake(SSLStreamReader sslStreamReader, SSLEngineConfigurator configurator, CompletionHandler completionHandler) throws IOException {
      Connection connection = this.getConnection();
      SSLEngine sslEngine = SSLUtils.getSSLEngine(this.getConnection());
      if (sslEngine == null) {
         sslEngine = configurator.createSSLEngine();
         SSLUtils.setSSLEngine(connection, sslEngine);
         checkBuffers(connection, sslEngine);
      }

      boolean isLoggingFinest = logger.isLoggable(Level.FINEST);
      if (isLoggingFinest) {
         logger.log(Level.FINEST, "connection={0} engine={1} handshakeStatus={2}", new Object[]{connection, sslEngine, sslEngine.getHandshakeStatus()});
      }

      SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();
      if (handshakeStatus == HandshakeStatus.NOT_HANDSHAKING) {
         sslEngine.beginHandshake();
      }

      FutureImpl future = SafeFutureImpl.create();
      HandshakeCompletionHandler hsCompletionHandler = new HandshakeCompletionHandler(future, completionHandler, sslEngine);
      sslStreamReader.notifyCondition(new SSLHandshakeCondition(sslStreamReader, this, configurator, sslEngine, hsCompletionHandler), hsCompletionHandler);
      return future;
   }

   private static void checkBuffers(Connection connection, SSLEngine sslEngine) {
      int packetBufferSize = sslEngine.getSession().getPacketBufferSize();
      if (connection.getReadBufferSize() < packetBufferSize) {
         connection.setReadBufferSize(packetBufferSize);
      }

      if (connection.getWriteBufferSize() < packetBufferSize) {
         connection.setWriteBufferSize(packetBufferSize);
      }

   }

   protected static final class HandshakeCompletionHandler extends CompletionHandlerAdapter {
      final SSLEngine sslEngine;

      public HandshakeCompletionHandler(FutureImpl future, CompletionHandler completionHandler, SSLEngine sslEngine) {
         super(future, completionHandler);
         this.sslEngine = sslEngine;
      }

      protected SSLEngine adapt(Integer result) {
         return this.sslEngine;
      }
   }

   protected static class SSLHandshakeCondition implements Condition {
      private final SSLEngineConfigurator configurator;
      private final Connection connection;
      private final SSLEngine sslEngine;
      private final StreamReader streamReader;
      private final StreamWriter streamWriter;
      private final HandshakeCompletionHandler completionHandler;

      public SSLHandshakeCondition(StreamReader streamReader, StreamWriter streamWriter, SSLEngineConfigurator configurator, SSLEngine sslEngine, HandshakeCompletionHandler completionHandler) {
         this.connection = streamReader.getConnection();
         this.configurator = configurator;
         this.sslEngine = sslEngine;
         this.completionHandler = completionHandler;
         this.streamReader = streamReader;
         this.streamWriter = streamWriter;
      }

      public boolean check() {
         try {
            return this.doHandshakeStep();
         } catch (IOException var2) {
            this.completionHandler.failed(var2);
            throw new RuntimeException("Unexpected handshake exception");
         }
      }

      public boolean doHandshakeStep() throws IOException {
         boolean isLoggingFinest = SSLStreamWriter.logger.isLoggable(Level.FINEST);
         SSLEngineResult.HandshakeStatus handshakeStatus = this.sslEngine.getHandshakeStatus();
         if (handshakeStatus != HandshakeStatus.FINISHED && handshakeStatus != HandshakeStatus.NOT_HANDSHAKING) {
            do {
               if (isLoggingFinest) {
                  SSLStreamWriter.logger.log(Level.FINEST, "Loop Engine: {0} handshakeStatus={1}", new Object[]{this.sslEngine, this.sslEngine.getHandshakeStatus()});
               }

               switch (handshakeStatus) {
                  case NEED_UNWRAP:
                     if (isLoggingFinest) {
                        SSLStreamWriter.logger.log(Level.FINEST, "NEED_UNWRAP Engine: {0}", this.sslEngine);
                     }

                     return false;
                  case NEED_WRAP:
                     if (isLoggingFinest) {
                        SSLStreamWriter.logger.log(Level.FINEST, "NEED_WRAP Engine: {0}", this.sslEngine);
                     }

                     this.streamWriter.writeBuffer(Buffers.EMPTY_BUFFER);
                     this.streamWriter.flush();
                     handshakeStatus = this.sslEngine.getHandshakeStatus();
                     break;
                  case NEED_TASK:
                     if (isLoggingFinest) {
                        SSLStreamWriter.logger.log(Level.FINEST, "NEED_TASK Engine: {0}", this.sslEngine);
                     }

                     SSLUtils.executeDelegatedTask(this.sslEngine);
                     handshakeStatus = this.sslEngine.getHandshakeStatus();
                     break;
                  default:
                     throw new RuntimeException("Invalid Handshaking State" + handshakeStatus);
               }
            } while(handshakeStatus != HandshakeStatus.FINISHED);

            return true;
         } else {
            return true;
         }
      }
   }
}
