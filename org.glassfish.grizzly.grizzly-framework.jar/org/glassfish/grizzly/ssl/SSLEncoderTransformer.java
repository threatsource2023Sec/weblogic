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
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLEncoderTransformer extends AbstractTransformer {
   public static final int NEED_HANDSHAKE_ERROR = 1;
   public static final int BUFFER_UNDERFLOW_ERROR = 2;
   public static final int BUFFER_OVERFLOW_ERROR = 3;
   private static final Logger LOGGER = Grizzly.logger(SSLEncoderTransformer.class);
   private static final TransformationResult HANDSHAKE_NOT_EXECUTED_RESULT = TransformationResult.createErrorResult(1, "Handshake was not executed");
   private final MemoryManager memoryManager;

   public SSLEncoderTransformer() {
      this(MemoryManager.DEFAULT_MEMORY_MANAGER);
   }

   public SSLEncoderTransformer(MemoryManager memoryManager) {
      this.memoryManager = memoryManager;
   }

   public String getName() {
      return SSLEncoderTransformer.class.getName();
   }

   protected TransformationResult transformImpl(AttributeStorage state, Buffer originalMessage) throws TransformationException {
      SSLEngine sslEngine = SSLUtils.getSSLEngine((Connection)state);
      if (sslEngine == null) {
         return HANDSHAKE_NOT_EXECUTED_RESULT;
      } else {
         synchronized(state) {
            return this.wrapAll(sslEngine, originalMessage);
         }
      }
   }

   private TransformationResult wrapAll(SSLEngine sslEngine, Buffer originalMessage) throws TransformationException {
      TransformationResult transformationResult = null;
      Buffer targetBuffer = null;
      Buffer currentTargetBuffer = null;
      ByteBufferArray originalByteBufferArray = originalMessage.toByteBufferArray();
      boolean restore = false;

      for(int i = 0; i < originalByteBufferArray.size(); ++i) {
         int pos = originalMessage.position();
         ByteBuffer originalByteBuffer = ((ByteBuffer[])originalByteBufferArray.getArray())[i];
         currentTargetBuffer = allowDispose(this.memoryManager.allocate(sslEngine.getSession().getPacketBufferSize()));
         ByteBuffer currentTargetByteBuffer = currentTargetBuffer.toByteBuffer();

         try {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "SSLEncoder engine: {0} input: {1} output: {2}", new Object[]{sslEngine, originalByteBuffer, currentTargetByteBuffer});
            }

            SSLEngineResult sslEngineResult = SSLUtils.sslEngineWrap(sslEngine, originalByteBuffer, currentTargetByteBuffer);
            if (pos == originalMessage.position()) {
               restore = true;
               originalMessage.position(pos + sslEngineResult.bytesConsumed());
            }

            SSLEngineResult.Status status = sslEngineResult.getStatus();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "SSLEncoder done engine: {0} result: {1} input: {2} output: {3}", new Object[]{sslEngine, sslEngineResult, originalByteBuffer, currentTargetByteBuffer});
            }

            if (status != Status.OK) {
               if (status == Status.CLOSED) {
                  transformationResult = TransformationResult.createCompletedResult(Buffers.EMPTY_BUFFER, originalMessage);
               } else if (status == Status.BUFFER_UNDERFLOW) {
                  transformationResult = TransformationResult.createErrorResult(2, "Buffer underflow during wrap operation");
               } else if (status == Status.BUFFER_OVERFLOW) {
                  transformationResult = TransformationResult.createErrorResult(3, "Buffer overflow during wrap operation");
               }
               break;
            }

            currentTargetBuffer.position(sslEngineResult.bytesProduced());
            currentTargetBuffer.trim();
            targetBuffer = Buffers.appendBuffers(this.memoryManager, targetBuffer, currentTargetBuffer);
         } catch (SSLException var14) {
            disposeBuffers(currentTargetBuffer, targetBuffer);
            originalByteBufferArray.restore();
            throw new TransformationException(var14);
         }

         if (originalByteBuffer.hasRemaining()) {
            --i;
         }
      }

      assert !originalMessage.hasRemaining();

      if (restore) {
         originalByteBufferArray.restore();
      }

      originalByteBufferArray.recycle();
      if (transformationResult != null) {
         disposeBuffers(currentTargetBuffer, targetBuffer);
         return transformationResult;
      } else {
         return TransformationResult.createCompletedResult(allowDispose(targetBuffer), originalMessage);
      }
   }

   private static void disposeBuffers(Buffer currentBuffer, Buffer bigBuffer) {
      if (currentBuffer != null) {
         currentBuffer.dispose();
      }

      if (bigBuffer != null) {
         bigBuffer.allowBufferDispose(true);
         if (bigBuffer.isComposite()) {
            ((CompositeBuffer)bigBuffer).allowInternalBuffersDispose(true);
         }

         bigBuffer.dispose();
      }

   }

   private static Buffer allowDispose(Buffer buffer) {
      buffer.allowBufferDispose(true);
      if (buffer.isComposite()) {
         ((CompositeBuffer)buffer).allowInternalBuffersDispose(true);
      }

      return buffer;
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input != null && input.hasRemaining();
   }
}
