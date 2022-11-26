package org.glassfish.grizzly.streams;

import java.io.EOFException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.ResultAware;
import org.glassfish.grizzly.utils.conditions.Condition;

public abstract class AbstractStreamReader implements StreamReader {
   private static final boolean DEBUG = false;
   private static final Logger LOGGER = Grizzly.logger(AbstractStreamReader.class);
   protected final Connection connection;
   protected final Input input;
   protected final AtomicBoolean isClosed = new AtomicBoolean(false);

   private static void msg(String msg) {
      LOGGER.log(Level.INFO, "READERSTREAM:DEBUG:{0}", msg);
   }

   private static void displayBuffer(String str, Buffer wrapper) {
      msg(str);
      msg("\tposition()     = " + wrapper.position());
      msg("\tlimit()        = " + wrapper.limit());
      msg("\tcapacity()     = " + wrapper.capacity());
   }

   protected AbstractStreamReader(Connection connection, Input streamInput) {
      this.input = streamInput;
      this.connection = connection;
   }

   public boolean readBoolean() throws IOException {
      return this.readByte() == 1;
   }

   public byte readByte() throws IOException {
      return this.input.read();
   }

   public char readChar() throws IOException {
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         if (buffer != null && buffer.remaining() >= 2) {
            char result = buffer.getChar();
            buffer.shrink();
            return result;
         }
      }

      return (char)((this.readByte() & 255) << 8 | this.readByte() & 255);
   }

   public short readShort() throws IOException {
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         if (buffer != null && buffer.remaining() >= 2) {
            short result = buffer.getShort();
            buffer.shrink();
            return result;
         }
      }

      return (short)((this.readByte() & 255) << 8 | this.readByte() & 255);
   }

   public int readInt() throws IOException {
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         if (buffer != null && buffer.remaining() >= 4) {
            int result = buffer.getInt();
            buffer.shrink();
            return result;
         }
      }

      return (this.readShort() & '\uffff') << 16 | this.readShort() & '\uffff';
   }

   public long readLong() throws IOException {
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         if (buffer != null && buffer.remaining() >= 8) {
            long result = buffer.getLong();
            buffer.shrink();
            return result;
         }
      }

      return ((long)this.readInt() & 4294967295L) << 32 | (long)this.readInt() & 4294967295L;
   }

   public final float readFloat() throws IOException {
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         if (buffer != null && buffer.remaining() >= 4) {
            float result = buffer.getFloat();
            buffer.shrink();
            return result;
         }
      }

      return Float.intBitsToFloat(this.readInt());
   }

   public final double readDouble() throws IOException {
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         if (buffer != null && buffer.remaining() >= 8) {
            double result = buffer.getDouble();
            buffer.shrink();
            return result;
         }
      }

      return Double.longBitsToDouble(this.readLong());
   }

   private void arraySizeCheck(int sizeInBytes) {
      if (sizeInBytes > this.available()) {
         throw new BufferUnderflowException();
      }
   }

   public void readBooleanArray(boolean[] data) throws IOException {
      this.arraySizeCheck(data.length);

      for(int ctr = 0; ctr < data.length; ++ctr) {
         data[ctr] = this.readBoolean();
      }

   }

   public void readByteArray(byte[] data) throws IOException {
      this.readByteArray(data, 0, data.length);
   }

   public void readByteArray(byte[] data, int offset, int length) throws IOException {
      this.arraySizeCheck(length);
      if (this.input.isBuffered()) {
         Buffer buffer = this.input.getBuffer();
         buffer.get(data, offset, length);
         buffer.shrink();
      } else {
         for(int i = offset; i < length; ++i) {
            data[i] = this.input.read();
         }
      }

   }

   public void readBytes(Buffer buffer) throws IOException {
      if (buffer.hasRemaining()) {
         this.arraySizeCheck(buffer.remaining());
         if (this.input.isBuffered()) {
            Buffer inputBuffer = this.input.getBuffer();
            int diff = buffer.remaining() - inputBuffer.remaining();
            if (diff >= 0) {
               buffer.put(inputBuffer);
            } else {
               int save = inputBuffer.limit();
               inputBuffer.limit(save + diff);
               buffer.put(inputBuffer);
               inputBuffer.limit(save);
            }

            inputBuffer.shrink();
         } else {
            while(buffer.hasRemaining()) {
               buffer.put(this.input.read());
            }
         }

      }
   }

   public void readCharArray(char[] data) throws IOException {
      this.arraySizeCheck(2 * data.length);

      for(int i = 0; i < data.length; ++i) {
         data[i] = this.readChar();
      }

   }

   public void readShortArray(short[] data) throws IOException {
      this.arraySizeCheck(2 * data.length);

      for(int i = 0; i < data.length; ++i) {
         data[i] = this.readShort();
      }

   }

   public void readIntArray(int[] data) throws IOException {
      this.arraySizeCheck(4 * data.length);

      for(int i = 0; i < data.length; ++i) {
         data[i] = this.readInt();
      }

   }

   public void readLongArray(long[] data) throws IOException {
      this.arraySizeCheck(8 * data.length);

      for(int i = 0; i < data.length; ++i) {
         data[i] = this.readLong();
      }

   }

   public void readFloatArray(float[] data) throws IOException {
      this.arraySizeCheck(4 * data.length);

      for(int i = 0; i < data.length; ++i) {
         data[i] = this.readFloat();
      }

   }

   public void readDoubleArray(double[] data) throws IOException {
      this.arraySizeCheck(8 * data.length);

      for(int i = 0; i < data.length; ++i) {
         data[i] = this.readDouble();
      }

   }

   public void skip(int length) {
      this.input.skip(length);
   }

   public GrizzlyFuture decode(Transformer decoder) {
      return this.decode(decoder, (CompletionHandler)null);
   }

   public GrizzlyFuture decode(Transformer decoder, CompletionHandler completionHandler) {
      FutureImpl future = SafeFutureImpl.create();
      DecodeCompletionHandler completionHandlerWrapper = new DecodeCompletionHandler(future, completionHandler);
      this.notifyCondition(new StreamDecodeCondition(this, decoder, completionHandlerWrapper), completionHandlerWrapper);
      return future;
   }

   public GrizzlyFuture notifyAvailable(int size) {
      return this.notifyAvailable(size, (CompletionHandler)null);
   }

   public GrizzlyFuture notifyAvailable(final int size, CompletionHandler completionHandler) {
      return this.notifyCondition(new Condition() {
         public boolean check() {
            return AbstractStreamReader.this.available() >= size;
         }
      }, completionHandler);
   }

   public GrizzlyFuture notifyCondition(Condition condition) {
      return this.notifyCondition(condition, (CompletionHandler)null);
   }

   public synchronized GrizzlyFuture notifyCondition(Condition condition, CompletionHandler completionHandler) {
      if (this.isClosed()) {
         EOFException exception = new EOFException();
         if (completionHandler != null) {
            completionHandler.failed(exception);
         }

         return ReadyFutureImpl.create((Throwable)exception);
      } else {
         return this.input.notifyCondition(condition, completionHandler);
      }
   }

   public void close() {
      if (this.isClosed.compareAndSet(false, true) && this.input != null) {
         try {
            this.input.close();
         } catch (IOException var2) {
         }
      }

   }

   public boolean isClosed() {
      return this.isClosed.get();
   }

   public final boolean hasAvailable() {
      return this.available() > 0;
   }

   public int available() {
      return this.input.size();
   }

   public boolean isSupportBufferWindow() {
      return this.input.isBuffered();
   }

   public Buffer getBufferWindow() {
      return this.input.getBuffer();
   }

   public Buffer takeBufferWindow() {
      return this.input.takeBuffer();
   }

   public Connection getConnection() {
      return this.connection;
   }

   private static class DecodeCompletionHandler extends CompletionHandlerAdapter implements ResultAware {
      private volatile Object result;

      public DecodeCompletionHandler(FutureImpl future, CompletionHandler completionHandler) {
         super(future, completionHandler);
      }

      public void setResult(Object result) {
         this.result = result;
      }

      protected Object adapt(Object result) {
         return this.result;
      }
   }
}
