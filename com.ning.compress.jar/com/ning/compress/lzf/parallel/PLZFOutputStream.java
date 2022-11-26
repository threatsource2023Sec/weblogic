package com.ning.compress.lzf.parallel;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PLZFOutputStream extends FilterOutputStream implements WritableByteChannel {
   private static final int DEFAULT_OUTPUT_BUFFER_SIZE = 65535;
   protected byte[] _outputBuffer;
   protected int _position;
   protected boolean _outputStreamClosed;
   private BlockManager blockManager;
   private final ExecutorService compressExecutor;
   private final ExecutorService writeExecutor;
   volatile Exception writeException;

   public PLZFOutputStream(OutputStream outputStream) {
      this(outputStream, 65535, getNThreads());
   }

   protected PLZFOutputStream(OutputStream outputStream, int nThreads) {
      this(outputStream, 65535, nThreads);
   }

   protected PLZFOutputStream(OutputStream outputStream, int bufferSize, int nThreads) {
      super(outputStream);
      this._position = 0;
      this.writeException = null;
      this._outputStreamClosed = false;
      this.compressExecutor = new ThreadPoolExecutor(nThreads, nThreads, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
      ((ThreadPoolExecutor)this.compressExecutor).allowCoreThreadTimeOut(true);
      this.writeExecutor = Executors.newSingleThreadExecutor();
      this.blockManager = new BlockManager(nThreads * 2, bufferSize);
      this._outputBuffer = this.blockManager.getBlockFromPool();
   }

   protected static int getNThreads() {
      int nThreads = Runtime.getRuntime().availableProcessors();
      OperatingSystemMXBean jmx = ManagementFactory.getOperatingSystemMXBean();
      if (jmx != null) {
         int loadAverage = (int)jmx.getSystemLoadAverage();
         if (nThreads > 1 && loadAverage >= 1) {
            nThreads = Math.max(1, nThreads - loadAverage);
         }
      }

      return nThreads;
   }

   public void write(int singleByte) throws IOException {
      this.checkNotClosed();
      if (this._position >= this._outputBuffer.length) {
         this.writeCompressedBlock();
      }

      this._outputBuffer[this._position++] = (byte)singleByte;
   }

   public void write(byte[] buffer, int offset, int length) throws IOException {
      this.checkNotClosed();
      int BUFFER_LEN = this._outputBuffer.length;
      int free = BUFFER_LEN - this._position;
      if (free > length) {
         System.arraycopy(buffer, offset, this._outputBuffer, this._position, length);
         this._position += length;
      } else {
         System.arraycopy(buffer, offset, this._outputBuffer, this._position, free);
         offset += free;
         length -= free;
         this._position += free;
         this.writeCompressedBlock();

         while(length >= BUFFER_LEN) {
            System.arraycopy(buffer, offset, this._outputBuffer, 0, BUFFER_LEN);
            this._position = BUFFER_LEN;
            this.writeCompressedBlock();
            offset += BUFFER_LEN;
            length -= BUFFER_LEN;
         }

         if (length > 0) {
            System.arraycopy(buffer, offset, this._outputBuffer, 0, length);
         }

         this._position = length;
      }
   }

   public void write(InputStream in) throws IOException {
      this.writeCompressedBlock();

      int read;
      while((read = in.read(this._outputBuffer)) >= 0) {
         this._position = read;
         this.writeCompressedBlock();
      }

   }

   public void write(FileChannel in) throws IOException {
      MappedByteBuffer src = in.map(MapMode.READ_ONLY, 0L, in.size());
      this.write((ByteBuffer)src);
   }

   public synchronized int write(ByteBuffer src) throws IOException {
      int r = src.remaining();
      if (r <= 0) {
         return r;
      } else {
         this.writeCompressedBlock();
         if (src.hasArray()) {
            this.write(src.array(), src.arrayOffset(), src.limit() - src.arrayOffset());
         } else {
            while(src.hasRemaining()) {
               int toRead = Math.min(src.remaining(), this._outputBuffer.length);
               src.get(this._outputBuffer, 0, toRead);
               this._position = toRead;
               this.writeCompressedBlock();
            }
         }

         return r;
      }
   }

   public void flush() throws IOException {
      this.checkNotClosed();
   }

   public boolean isOpen() {
      return !this._outputStreamClosed;
   }

   public void close() throws IOException {
      if (!this._outputStreamClosed) {
         if (this._position > 0) {
            this.writeCompressedBlock();
         }

         byte[] buf = this._outputBuffer;
         if (buf != null) {
            assert this._position == 0;

            this.blockManager.releaseBlockToPool(this._outputBuffer);
            this._outputBuffer = null;
         }

         this.writeExecutor.shutdown();

         try {
            this.writeExecutor.awaitTermination(1L, TimeUnit.HOURS);
            int maxThreads = Runtime.getRuntime().availableProcessors();
            Collection cleanupTasks = new ArrayList(maxThreads);

            for(int i = 0; i < maxThreads; ++i) {
               cleanupTasks.add(new CompressTask((byte[])null, -1, -1, (BlockManager)null));
            }

            this.compressExecutor.invokeAll(cleanupTasks);
            this.compressExecutor.shutdown();
            this.compressExecutor.awaitTermination(1L, TimeUnit.MINUTES);
         } catch (InterruptedException var8) {
            throw new IOException(var8);
         } finally {
            super.flush();
            super.close();
            this._outputStreamClosed = true;
            this.compressExecutor.shutdownNow();
            this.writeExecutor.shutdownNow();
            this.blockManager = null;
            this.checkWriteException();
         }
      }

   }

   public OutputStream getUnderlyingOutputStream() {
      return this.out;
   }

   protected void writeCompressedBlock() throws IOException {
      if (this._position != 0) {
         Future lzfFuture = this.compressExecutor.submit(new CompressTask(this._outputBuffer, 0, this._position, this.blockManager));
         this.writeExecutor.execute(new WriteTask(this.out, lzfFuture, this));
         this._outputBuffer = this.blockManager.getBlockFromPool();
         this._position = 0;
         this.checkWriteException();
      }
   }

   protected void checkWriteException() throws IOException {
      if (this.writeException != null) {
         IOException ioe = this.writeException instanceof IOException ? (IOException)this.writeException : new IOException(this.writeException);
         this.writeException = null;
         throw ioe;
      }
   }

   protected void checkNotClosed() throws IOException {
      if (this._outputStreamClosed) {
         throw new IOException(this.getClass().getName() + " already closed");
      }
   }
}
