package org.glassfish.grizzly.streams;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.CompositeBuffer;

public abstract class BufferedOutput implements Output {
   protected static final Integer ZERO = 0;
   protected static final GrizzlyFuture ZERO_READY_FUTURE = ReadyFutureImpl.create((int)0);
   protected final int bufferSize;
   protected CompositeBuffer multiBufferWindow;
   private Buffer buffer;
   private int lastSlicedPosition;
   protected final AtomicBoolean isClosed;

   public BufferedOutput() {
      this(8192);
   }

   public BufferedOutput(int bufferSize) {
      this.isClosed = new AtomicBoolean();
      this.bufferSize = bufferSize;
   }

   protected abstract void onClosed() throws IOException;

   protected abstract GrizzlyFuture flush0(Buffer var1, CompletionHandler var2) throws IOException;

   protected abstract Buffer newBuffer(int var1);

   protected abstract Buffer reallocateBuffer(Buffer var1, int var2);

   public void write(byte data) throws IOException {
      this.ensureBufferCapacity(1);
      this.buffer.put(data);
   }

   public void write(Buffer bufferToWrite) throws IOException {
      if (this.multiBufferWindow == null) {
         this.multiBufferWindow = CompositeBuffer.newBuffer();
      }

      boolean isInternalBufferEmpty = this.buffer == null || this.buffer.position() - this.lastSlicedPosition == 0;
      if (!isInternalBufferEmpty) {
         Buffer slice = this.buffer.slice(this.lastSlicedPosition, this.buffer.position());
         this.multiBufferWindow.append(slice);
         this.lastSlicedPosition = this.buffer.position();
      }

      this.multiBufferWindow.append(bufferToWrite);
      this.ensureBufferCapacity(0);
   }

   public boolean isBuffered() {
      return true;
   }

   public Buffer getBuffer() {
      return this.buffer;
   }

   public void ensureBufferCapacity(int size) throws IOException {
      if (size > this.bufferSize) {
         throw new IllegalArgumentException("Size exceeds max size limit: " + this.bufferSize);
      } else {
         if (this.getBufferedSize() >= this.bufferSize) {
            this.overflow((CompletionHandler)null);
         }

         if (size != 0) {
            if (this.buffer != null) {
               int bufferRemaining = this.buffer.remaining();
               if (bufferRemaining < size) {
                  this.overflow((CompletionHandler)null);
                  this.ensureBufferCapacity(size);
               }
            } else {
               this.buffer = this.newBuffer(this.bufferSize);
            }

         }
      }
   }

   private GrizzlyFuture overflow(CompletionHandler completionHandler) throws IOException {
      GrizzlyFuture future;
      if (this.multiBufferWindow != null) {
         if (this.buffer != null && this.buffer.position() > this.lastSlicedPosition) {
            Buffer slice = this.buffer.slice(this.lastSlicedPosition, this.buffer.position());
            this.lastSlicedPosition = this.buffer.position();
            this.multiBufferWindow.append(slice);
         }

         future = this.flush0(this.multiBufferWindow, completionHandler);
         if (future.isDone()) {
            this.multiBufferWindow.removeAll();
            this.multiBufferWindow.clear();
            if (this.buffer != null) {
               if (!this.buffer.isComposite()) {
                  this.buffer.clear();
               } else {
                  this.buffer = null;
               }

               this.lastSlicedPosition = 0;
            }
         } else {
            this.multiBufferWindow = null;
            this.buffer = null;
            this.lastSlicedPosition = 0;
         }

         return future;
      } else if (this.buffer != null && this.buffer.position() > 0) {
         this.buffer.flip();
         future = this.flush0(this.buffer, completionHandler);
         if (future.isDone() && !this.buffer.isComposite()) {
            this.buffer.clear();
         } else {
            this.buffer = null;
         }

         return future;
      } else {
         return this.flush0((Buffer)null, completionHandler);
      }
   }

   public GrizzlyFuture flush(CompletionHandler completionHandler) throws IOException {
      return this.overflow(completionHandler);
   }

   public GrizzlyFuture close(final CompletionHandler completionHandler) throws IOException {
      if (!this.isClosed.getAndSet(true) && this.buffer != null && this.buffer.position() > 0) {
         final FutureImpl future = SafeFutureImpl.create();

         try {
            this.overflow(new CompletionHandler() {
               public void cancelled() {
                  this.close(BufferedOutput.ZERO);
               }

               public void failed(Throwable throwable) {
                  this.close(BufferedOutput.ZERO);
               }

               public void completed(Integer result) {
                  this.close(result);
               }

               public void updated(Integer result) {
               }

               public void close(Integer result) {
                  try {
                     BufferedOutput.this.onClosed();
                  } catch (IOException var6) {
                  } finally {
                     if (completionHandler != null) {
                        completionHandler.completed(result);
                     }

                     future.result(result);
                  }

               }
            });
         } catch (IOException var4) {
         }

         return future;
      } else {
         if (completionHandler != null) {
            completionHandler.completed(ZERO);
         }

         return ZERO_READY_FUTURE;
      }
   }

   protected int getBufferedSize() {
      int size = 0;
      if (this.multiBufferWindow != null) {
         size = this.multiBufferWindow.remaining();
      }

      if (this.buffer != null) {
         size += this.buffer.position() - this.lastSlicedPosition;
      }

      return size;
   }
}
