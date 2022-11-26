package org.glassfish.grizzly.ssl;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLEngineResult.Status;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLConnectionContext {
   private static final Logger LOGGER = Grizzly.logger(SSLConnectionContext.class);
   private static final float BUFFER_SIZE_COEF;
   final ByteBufferArray outputByteBufferArray = ByteBufferArray.create();
   final ByteBufferArray inputByteBufferArray = ByteBufferArray.create();
   private Buffer lastOutputBuffer;
   private final InputBufferWrapper inputBuffer = new InputBufferWrapper();
   private InputBufferWrapper lastInputBuffer;
   private boolean isServerMode;
   private SSLEngine sslEngine;
   private volatile int appBufferSize;
   private volatile int netBufferSize;
   private final Connection connection;
   private FilterChain newConnectionFilterChain;

   public SSLConnectionContext(Connection connection) {
      this.connection = connection;
   }

   public SSLEngine getSslEngine() {
      return this.sslEngine;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public void attach() {
      SSLUtils.SSL_CTX_ATTR.set((AttributeStorage)this.connection, this);
   }

   public void configure(SSLEngine sslEngine) {
      this.sslEngine = sslEngine;
      this.isServerMode = !sslEngine.getUseClientMode();
      this.updateBufferSizes();
   }

   public boolean isServerMode() {
      return this.isServerMode;
   }

   void updateBufferSizes() {
      SSLSession session = this.sslEngine.getSession();
      this.appBufferSize = session.getApplicationBufferSize();
      this.netBufferSize = session.getPacketBufferSize();
   }

   public int getAppBufferSize() {
      return this.appBufferSize;
   }

   public int getNetBufferSize() {
      return this.netBufferSize;
   }

   public FilterChain getNewConnectionFilterChain() {
      return this.newConnectionFilterChain;
   }

   public void setNewConnectionFilterChain(FilterChain newConnectionFilterChain) {
      this.newConnectionFilterChain = newConnectionFilterChain;
   }

   Buffer resetLastOutputBuffer() {
      Buffer tmp = this.lastOutputBuffer;
      this.lastOutputBuffer = null;
      return tmp;
   }

   void setLastOutputBuffer(Buffer lastOutputBuffer) {
      this.lastOutputBuffer = lastOutputBuffer;
   }

   InputBufferWrapper resetLastInputBuffer() {
      InputBufferWrapper tmp = this.lastInputBuffer;
      this.lastInputBuffer = null;
      return tmp;
   }

   InputBufferWrapper useInputBuffer() {
      this.lastInputBuffer = this.inputBuffer;
      return this.lastInputBuffer;
   }

   SslResult unwrap(int len, Buffer input, Buffer output, Allocator allocator) {
      output = this.ensureBufferSize(output, this.appBufferSize, allocator);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "unwrap engine: {0} input: {1} output: {2}", new Object[]{this.sslEngine, input, output});
      }

      int inPos = input.position();
      int outPos = output.position();
      ByteBuffer inputByteBuffer = input.toByteBuffer(input.position(), input.position() + len);
      int initPosition = inputByteBuffer.position();

      SSLEngineResult sslEngineResult;
      try {
         if (!output.isComposite()) {
            sslEngineResult = SSLUtils.sslEngineUnwrap(this.sslEngine, inputByteBuffer, output.toByteBuffer());
         } else {
            ByteBufferArray bba = output.toByteBufferArray(this.outputByteBufferArray);
            ByteBuffer[] outputArray = (ByteBuffer[])bba.getArray();

            try {
               sslEngineResult = SSLUtils.sslEngineUnwrap(this.sslEngine, inputByteBuffer, outputArray, 0, bba.size());
            } finally {
               bba.restore();
               bba.reset();
            }
         }
      } catch (SSLException var16) {
         return new SslResult(output, var16);
      }

      SSLEngineResult.Status status = sslEngineResult.getStatus();
      boolean isOverflow = status == Status.BUFFER_OVERFLOW;
      if (allocator != null && isOverflow) {
         this.updateBufferSizes();
         output = this.ensureBufferSize(output, this.appBufferSize, allocator);
         return this.unwrap(len, input, output, (Allocator)null);
      } else if (!isOverflow && status != Status.BUFFER_UNDERFLOW) {
         input.position(inPos + inputByteBuffer.position() - initPosition);
         output.position(outPos + sslEngineResult.bytesProduced());
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "unwrap done engine: {0} result: {1} input: {2} output: {3}", new Object[]{this.sslEngine, sslEngineResult, input, output});
         }

         return new SslResult(output, sslEngineResult);
      } else {
         return new SslResult(output, new SSLException("SSL unwrap error: " + status));
      }
   }

   Buffer wrapAll(Buffer input, Allocator allocator) throws SSLException {
      MemoryManager memoryManager = this.connection.getMemoryManager();
      ByteBufferArray bba = input.toByteBufferArray(this.inputByteBufferArray);
      ByteBuffer[] inputArray = (ByteBuffer[])bba.getArray();
      int inputArraySize = bba.size();
      Buffer output = null;
      SslResult result = null;

      Buffer newOutput;
      try {
         result = this.wrap(input, inputArray, inputArraySize, (Buffer)null, allocator);
         if (result.isError()) {
            throw result.getError();
         }

         output = result.getOutput();
         output.trim();
         if (input.hasRemaining()) {
            do {
               result = this.wrap(input, inputArray, inputArraySize, (Buffer)null, allocator);
               if (result.isError()) {
                  throw result.getError();
               }

               newOutput = result.getOutput();
               newOutput.trim();
               output = Buffers.appendBuffers(memoryManager, output, newOutput);
            } while(input.hasRemaining());
         }

         newOutput = output;
      } finally {
         bba.restore();
         bba.reset();
         if (result != null && result.isError()) {
            if (output != null) {
               output.dispose();
            }

            result.getOutput().dispose();
         }

      }

      return newOutput;
   }

   private SslResult wrap(Buffer input, ByteBuffer[] inputArray, int inputArraySize, Buffer output, Allocator allocator) {
      output = this.ensureBufferSize(output, this.netBufferSize, allocator);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "wrap engine: {0} input: {1} output: {2}", new Object[]{this.sslEngine, input, output});
      }

      int inPos = input.position();
      int outPos = output.position();
      ByteBuffer outputByteBuffer = output.toByteBuffer();

      SSLEngineResult sslEngineResult;
      try {
         sslEngineResult = SSLUtils.sslEngineWrap(this.sslEngine, inputArray, 0, inputArraySize, outputByteBuffer);
      } catch (SSLException var12) {
         return new SslResult(output, var12);
      }

      SSLEngineResult.Status status = sslEngineResult.getStatus();
      if (status == Status.CLOSED) {
         return new SslResult(output, new SSLException("SSLEngine is CLOSED"));
      } else {
         boolean isOverflow = status == Status.BUFFER_OVERFLOW;
         if (allocator != null && isOverflow) {
            this.updateBufferSizes();
            output = this.ensureBufferSize(output, this.netBufferSize, allocator);
            return this.wrap(input, inputArray, inputArraySize, output, (Allocator)null);
         } else if (!isOverflow && status != Status.BUFFER_UNDERFLOW) {
            input.position(inPos + sslEngineResult.bytesConsumed());
            output.position(outPos + sslEngineResult.bytesProduced());
            this.lastOutputBuffer = output;
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "wrap done engine: {0} result: {1} input: {2} output: {3}", new Object[]{this.sslEngine, sslEngineResult, input, output});
            }

            return new SslResult(output, sslEngineResult);
         } else {
            return new SslResult(output, new SSLException("SSL wrap error: " + status));
         }
      }
   }

   SslResult wrap(Buffer input, Buffer output, Allocator allocator) {
      output = this.ensureBufferSize(output, this.netBufferSize, allocator);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "wrap engine: {0} input: {1} output: {2}", new Object[]{this.sslEngine, input, output});
      }

      int inPos = input.position();
      int outPos = output.position();
      ByteBuffer outputByteBuffer = output.toByteBuffer();

      SSLEngineResult sslEngineResult;
      try {
         if (!input.isComposite()) {
            sslEngineResult = SSLUtils.sslEngineWrap(this.sslEngine, input.toByteBuffer(), outputByteBuffer);
         } else {
            ByteBufferArray bba = input.toByteBufferArray(this.inputByteBufferArray);
            ByteBuffer[] inputArray = (ByteBuffer[])bba.getArray();

            try {
               sslEngineResult = SSLUtils.sslEngineWrap(this.sslEngine, inputArray, 0, bba.size(), outputByteBuffer);
            } finally {
               bba.restore();
               bba.reset();
            }
         }
      } catch (SSLException var14) {
         return new SslResult(output, var14);
      }

      SSLEngineResult.Status status = sslEngineResult.getStatus();
      boolean isOverflow = status == Status.BUFFER_OVERFLOW;
      if (allocator != null && isOverflow) {
         this.updateBufferSizes();
         output = this.ensureBufferSize(output, this.netBufferSize, allocator);
         return this.wrap(input, output, (Allocator)null);
      } else if (!isOverflow && status != Status.BUFFER_UNDERFLOW) {
         input.position(inPos + sslEngineResult.bytesConsumed());
         output.position(outPos + sslEngineResult.bytesProduced());
         this.lastOutputBuffer = output;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "wrap done engine: {0} result: {1} input: {2} output: {3}", new Object[]{this.sslEngine, sslEngineResult, input, output});
         }

         return new SslResult(output, sslEngineResult);
      } else {
         return new SslResult(output, new SSLException("SSL wrap error: " + status));
      }
   }

   private Buffer ensureBufferSize(Buffer output, int size, Allocator allocator) {
      int sz = (int)((float)size * BUFFER_SIZE_COEF);
      if (output == null) {
         assert allocator != null;

         output = allocator.grow(this, (Buffer)null, sz);
      } else if (output.remaining() < sz) {
         assert allocator != null;

         output = allocator.grow(this, output, output.capacity() + (sz - output.remaining()));
      }

      return output;
   }

   static {
      String coef = System.getProperty(SSLConnectionContext.class.getName(), "1.5");
      float coeff = 1.5F;

      try {
         coeff = Float.parseFloat(coef);
      } catch (NumberFormatException var3) {
      }

      BUFFER_SIZE_COEF = coeff;
   }

   static final class SslResult {
      private final Buffer output;
      private final SSLException error;
      private final SSLEngineResult sslEngineResult;

      public SslResult(Buffer output, SSLEngineResult sslEngineResult) {
         this.output = output;
         this.sslEngineResult = sslEngineResult;
         this.error = null;
      }

      public SslResult(Buffer output, SSLException error) {
         this.output = output;
         this.error = error;
         this.sslEngineResult = null;
      }

      public Buffer getOutput() {
         return this.output;
      }

      public boolean isError() {
         return this.error != null;
      }

      public SSLException getError() {
         return this.error;
      }

      public SSLEngineResult getSslEngineResult() {
         return this.sslEngineResult;
      }
   }

   interface Allocator {
      Buffer grow(SSLConnectionContext var1, Buffer var2, int var3);
   }
}
