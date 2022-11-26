package org.glassfish.grizzly.streams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.memory.Buffers;

public abstract class AbstractStreamWriter implements StreamWriter {
   protected static final Logger logger = Grizzly.logger(AbstractStreamWriter.class);
   protected static final Integer ZERO = 0;
   protected static final GrizzlyFuture ZERO_READY_FUTURE;
   private final Connection connection;
   private long timeoutMillis = 30000L;
   private final AtomicBoolean isClosed = new AtomicBoolean();
   protected final boolean isOutputBuffered;
   protected final Output output;

   protected AbstractStreamWriter(Connection connection, Output streamOutput) {
      this.connection = connection;
      this.output = streamOutput;
      this.isOutputBuffered = streamOutput.isBuffered();
   }

   public GrizzlyFuture flush() throws IOException {
      return this.flush((CompletionHandler)null);
   }

   public GrizzlyFuture flush(CompletionHandler completionHandler) throws IOException {
      return this.output.flush(completionHandler);
   }

   public boolean isClosed() {
      return this.isClosed.get();
   }

   public void close() throws IOException {
      this.close((CompletionHandler)null);
   }

   public GrizzlyFuture close(CompletionHandler completionHandler) throws IOException {
      return (GrizzlyFuture)(!this.isClosed.getAndSet(true) ? this.output.close(completionHandler) : ReadyFutureImpl.create((int)0));
   }

   public void writeBuffer(Buffer b) throws IOException {
      this.output.write(b);
   }

   public void writeBoolean(boolean data) throws IOException {
      byte value = data ? 1 : 0;
      this.writeByte((byte)value);
   }

   public void writeByte(byte data) throws IOException {
      this.output.write(data);
   }

   public void writeChar(char data) throws IOException {
      if (this.isOutputBuffered) {
         this.output.ensureBufferCapacity(2);
         this.output.getBuffer().putChar(data);
      } else {
         this.output.write((byte)(data >>> 8 & 255));
         this.output.write((byte)(data & 255));
      }

   }

   public void writeShort(short data) throws IOException {
      if (this.isOutputBuffered) {
         this.output.ensureBufferCapacity(2);
         this.output.getBuffer().putShort(data);
      } else {
         this.output.write((byte)(data >>> 8 & 255));
         this.output.write((byte)(data & 255));
      }

   }

   public void writeInt(int data) throws IOException {
      if (this.isOutputBuffered) {
         this.output.ensureBufferCapacity(4);
         this.output.getBuffer().putInt(data);
      } else {
         this.output.write((byte)(data >>> 24 & 255));
         this.output.write((byte)(data >>> 16 & 255));
         this.output.write((byte)(data >>> 8 & 255));
         this.output.write((byte)(data & 255));
      }

   }

   public void writeLong(long data) throws IOException {
      if (this.isOutputBuffered) {
         this.output.ensureBufferCapacity(8);
         this.output.getBuffer().putLong(data);
      } else {
         this.output.write((byte)((int)(data >>> 56 & 255L)));
         this.output.write((byte)((int)(data >>> 48 & 255L)));
         this.output.write((byte)((int)(data >>> 40 & 255L)));
         this.output.write((byte)((int)(data >>> 32 & 255L)));
         this.output.write((byte)((int)(data >>> 24 & 255L)));
         this.output.write((byte)((int)(data >>> 16 & 255L)));
         this.output.write((byte)((int)(data >>> 8 & 255L)));
         this.output.write((byte)((int)(data & 255L)));
      }

   }

   public void writeFloat(float data) throws IOException {
      this.writeInt(Float.floatToIntBits(data));
   }

   public void writeDouble(double data) throws IOException {
      this.writeLong(Double.doubleToLongBits(data));
   }

   public void writeBooleanArray(boolean[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.output.write((byte)(data[i] ? 1 : 0));
      }

   }

   public void writeByteArray(byte[] data) throws IOException {
      this.writeByteArray(data, 0, data.length);
   }

   public void writeByteArray(byte[] data, int offset, int length) throws IOException {
      Buffer buffer = Buffers.wrap(this.connection.getMemoryManager(), data, offset, length);
      this.output.write(buffer);
   }

   public void writeCharArray(char[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.writeChar(data[i]);
      }

   }

   public void writeShortArray(short[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.writeShort(data[i]);
      }

   }

   public void writeIntArray(int[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.writeInt(data[i]);
      }

   }

   public void writeLongArray(long[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.writeLong(data[i]);
      }

   }

   public void writeFloatArray(float[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.writeFloat(data[i]);
      }

   }

   public void writeDoubleArray(double[] data) throws IOException {
      for(int i = 0; i < data.length; ++i) {
         this.writeDouble(data[i]);
      }

   }

   public GrizzlyFuture encode(Transformer encoder, Object object) throws IOException {
      return this.encode(encoder, object, (CompletionHandler)null);
   }

   public GrizzlyFuture encode(Transformer encoder, Object object, CompletionHandler completionHandler) throws IOException {
      Exception exception = null;
      TransformationResult result = encoder.transform(this.connection, object);
      TransformationResult.Status status = result.getStatus();
      if (status == TransformationResult.Status.COMPLETE) {
         this.output.write((Buffer)result.getMessage());
         if (completionHandler != null) {
            completionHandler.completed(this);
         }

         return ReadyFutureImpl.create((Object)this);
      } else {
         if (status == TransformationResult.Status.INCOMPLETE) {
            exception = new IllegalStateException("Encoder returned INCOMPLETE state");
         }

         if (exception == null) {
            exception = new TransformationException(result.getErrorCode() + ": " + result.getErrorDescription());
         }

         return ReadyFutureImpl.create((Throwable)exception);
      }
   }

   public Connection getConnection() {
      return this.connection;
   }

   public long getTimeout(TimeUnit timeunit) {
      return timeunit.convert(this.timeoutMillis, TimeUnit.MILLISECONDS);
   }

   public void setTimeout(long timeout, TimeUnit timeunit) {
      this.timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
   }

   static {
      ZERO_READY_FUTURE = ReadyFutureImpl.create((Object)ZERO);
   }

   public static class DisposeBufferCompletionHandler implements CompletionHandler {
      private final Buffer buffer;

      public DisposeBufferCompletionHandler(Buffer buffer) {
         this.buffer = buffer;
      }

      public void cancelled() {
         this.disposeBuffer();
      }

      public void failed(Throwable throwable) {
         this.disposeBuffer();
      }

      public void completed(Object result) {
         this.disposeBuffer();
      }

      public void updated(Object result) {
      }

      protected void disposeBuffer() {
         this.buffer.dispose();
      }
   }
}
