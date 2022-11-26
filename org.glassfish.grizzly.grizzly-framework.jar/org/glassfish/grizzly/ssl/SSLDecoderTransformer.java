package org.glassfish.grizzly.ssl;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngineResult.Status;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLDecoderTransformer extends AbstractTransformer {
   public static final int NEED_HANDSHAKE_ERROR = 1;
   public static final int BUFFER_UNDERFLOW_ERROR = 2;
   public static final int BUFFER_OVERFLOW_ERROR = 3;
   private static final TransformationResult HANDSHAKE_NOT_EXECUTED_RESULT = TransformationResult.createErrorResult(1, "Handshake was not executed");
   private static final Logger LOGGER = Grizzly.logger(SSLDecoderTransformer.class);
   private final MemoryManager memoryManager;

   public SSLDecoderTransformer() {
      this(MemoryManager.DEFAULT_MEMORY_MANAGER);
   }

   public SSLDecoderTransformer(MemoryManager memoryManager) {
      this.memoryManager = memoryManager;
   }

   public String getName() {
      return SSLDecoderTransformer.class.getName();
   }

   protected TransformationResult transformImpl(AttributeStorage state, Buffer originalMessage) throws TransformationException {
      SSLEngine sslEngine = SSLUtils.getSSLEngine((Connection)state);
      if (sslEngine == null) {
         return HANDSHAKE_NOT_EXECUTED_RESULT;
      } else {
         int expectedLength;
         try {
            expectedLength = SSLUtils.getSSLPacketSize(originalMessage);
            if (expectedLength == -1 || originalMessage.remaining() < expectedLength) {
               return TransformationResult.createIncompletedResult(originalMessage);
            }
         } catch (SSLException var11) {
            throw new TransformationException(var11);
         }

         Buffer targetBuffer = this.memoryManager.allocate(sslEngine.getSession().getApplicationBufferSize());
         TransformationResult transformationResult = null;

         try {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "SSLDecoder engine: {0} input: {1} output: {2}", new Object[]{sslEngine, originalMessage, targetBuffer});
            }

            int pos = originalMessage.position();
            SSLEngineResult sslEngineResult;
            if (!originalMessage.isComposite()) {
               sslEngineResult = SSLUtils.sslEngineUnwrap(sslEngine, originalMessage.toByteBuffer(), targetBuffer.toByteBuffer());
            } else {
               ByteBuffer originalByteBuffer = originalMessage.toByteBuffer(pos, pos + expectedLength);
               sslEngineResult = SSLUtils.sslEngineUnwrap(sslEngine, originalByteBuffer, targetBuffer.toByteBuffer());
            }

            originalMessage.position(pos + sslEngineResult.bytesConsumed());
            targetBuffer.position(sslEngineResult.bytesProduced());
            SSLEngineResult.Status status = sslEngineResult.getStatus();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "SSLDecoderr done engine: {0} result: {1} input: {2} output: {3}", new Object[]{sslEngine, sslEngineResult, originalMessage, targetBuffer});
            }

            if (status == Status.OK) {
               targetBuffer.trim();
               return TransformationResult.createCompletedResult(targetBuffer, originalMessage);
            } else if (status == Status.CLOSED) {
               targetBuffer.dispose();
               return TransformationResult.createCompletedResult(Buffers.EMPTY_BUFFER, originalMessage);
            } else {
               targetBuffer.dispose();
               if (status == Status.BUFFER_UNDERFLOW) {
                  transformationResult = TransformationResult.createIncompletedResult(originalMessage);
               } else if (status == Status.BUFFER_OVERFLOW) {
                  transformationResult = TransformationResult.createErrorResult(3, "Buffer overflow during unwrap operation");
               }

               return transformationResult;
            }
         } catch (SSLException var10) {
            targetBuffer.dispose();
            throw new TransformationException(var10);
         }
      }
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input != null && input.hasRemaining();
   }
}
