package org.glassfish.grizzly.streams;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.utils.conditions.Condition;

public abstract class BufferedInput implements Input {
   protected final CompositeBuffer compositeBuffer = CompositeBuffer.newBuffer();
   private volatile boolean isClosed;
   protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
   protected boolean isCompletionHandlerRegistered;
   protected Exception registrationStackTrace;
   protected Condition condition;
   protected CompletionHandler completionHandler;
   protected FutureImpl future;

   protected abstract void onOpenInputSource() throws IOException;

   protected abstract void onCloseInputSource() throws IOException;

   public boolean append(Buffer buffer) {
      if (buffer == null) {
         return false;
      } else {
         this.lock.writeLock().lock();

         try {
            if (this.isClosed) {
               buffer.dispose();
            } else {
               int addSize = buffer.remaining();
               if (addSize > 0) {
                  this.compositeBuffer.append(buffer);
               }

               this.notifyUpdate();
            }
         } finally {
            this.lock.writeLock().unlock();
         }

         return true;
      }
   }

   public boolean prepend(Buffer buffer) {
      if (buffer == null) {
         return false;
      } else {
         this.lock.writeLock().lock();

         try {
            if (this.isClosed) {
               buffer.dispose();
            } else {
               int addSize = buffer.remaining();
               if (addSize > 0) {
                  this.compositeBuffer.prepend(buffer);
               }

               this.notifyUpdate();
            }
         } finally {
            this.lock.writeLock().unlock();
         }

         return true;
      }
   }

   public byte read() throws IOException {
      byte result = this.compositeBuffer.get();
      this.compositeBuffer.shrink();
      return result;
   }

   public void skip(int length) {
      if (length > this.size()) {
         throw new IllegalStateException("Can not skip more bytes than available");
      } else {
         this.compositeBuffer.position(this.compositeBuffer.position() + length);
         this.compositeBuffer.shrink();
      }
   }

   public final boolean isBuffered() {
      return true;
   }

   public Buffer getBuffer() {
      return this.compositeBuffer;
   }

   public Buffer takeBuffer() {
      Buffer duplicate = this.compositeBuffer.duplicate();
      this.compositeBuffer.removeAll();
      return duplicate;
   }

   public int size() {
      return this.compositeBuffer.remaining();
   }

   public void close() {
      this.lock.writeLock().lock();

      try {
         if (!this.isClosed) {
            this.isClosed = true;
            this.compositeBuffer.dispose();
            CompletionHandler localCompletionHandler = this.completionHandler;
            if (localCompletionHandler != null) {
               this.completionHandler = null;
               this.isCompletionHandlerRegistered = false;
               this.notifyFailure(localCompletionHandler, new EOFException("Input is closed"));
            }
         }
      } finally {
         this.lock.writeLock().unlock();
      }

   }

   public GrizzlyFuture notifyCondition(Condition condition, CompletionHandler completionHandler) {
      this.lock.writeLock().lock();

      SafeFutureImpl var4;
      try {
         if (this.isCompletionHandlerRegistered) {
            throw new IllegalStateException("Only one notificator could be registered. Previous registration came from: ", this.registrationStackTrace);
         }

         if (condition.check()) {
            this.notifyCompleted(completionHandler);
            ReadyFutureImpl var11 = ReadyFutureImpl.create((Object)this.compositeBuffer.remaining());
            return var11;
         }

         this.registrationStackTrace = new Exception();
         this.isCompletionHandlerRegistered = true;
         this.completionHandler = completionHandler;
         FutureImpl localFuture = SafeFutureImpl.create();
         this.future = localFuture;
         this.condition = condition;

         try {
            this.onOpenInputSource();
         } catch (IOException var9) {
            this.notifyFailure(completionHandler, var9);
            ReadyFutureImpl var5 = ReadyFutureImpl.create((Throwable)var9);
            return var5;
         }

         var4 = localFuture;
      } finally {
         this.lock.writeLock().unlock();
      }

      return var4;
   }

   private void notifyUpdate() {
      if (this.condition != null && this.condition.check()) {
         this.condition = null;
         CompletionHandler localCompletionHandler = this.completionHandler;
         this.completionHandler = null;
         FutureImpl localFuture = this.future;
         this.future = null;
         this.isCompletionHandlerRegistered = false;

         try {
            this.onCloseInputSource();
            this.notifyCompleted(localCompletionHandler);
            localFuture.result(this.compositeBuffer.remaining());
         } catch (IOException var4) {
            this.notifyFailure(localCompletionHandler, var4);
            localFuture.failure(var4);
         }
      }

   }

   protected void notifyCompleted(CompletionHandler completionHandler) {
      if (completionHandler != null) {
         completionHandler.completed(this.compositeBuffer.remaining());
      }

   }

   protected void notifyFailure(CompletionHandler completionHandler, Throwable failure) {
      if (completionHandler != null) {
         completionHandler.failed(failure);
      }

   }
}
