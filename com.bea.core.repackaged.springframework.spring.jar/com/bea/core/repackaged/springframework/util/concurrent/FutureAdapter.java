package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class FutureAdapter implements Future {
   private final Future adaptee;
   @Nullable
   private Object result;
   private State state;
   private final Object mutex;

   protected FutureAdapter(Future adaptee) {
      this.state = FutureAdapter.State.NEW;
      this.mutex = new Object();
      Assert.notNull(adaptee, (String)"Delegate must not be null");
      this.adaptee = adaptee;
   }

   protected Future getAdaptee() {
      return this.adaptee;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.adaptee.cancel(mayInterruptIfRunning);
   }

   public boolean isCancelled() {
      return this.adaptee.isCancelled();
   }

   public boolean isDone() {
      return this.adaptee.isDone();
   }

   @Nullable
   public Object get() throws InterruptedException, ExecutionException {
      return this.adaptInternal(this.adaptee.get());
   }

   @Nullable
   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.adaptInternal(this.adaptee.get(timeout, unit));
   }

   @Nullable
   final Object adaptInternal(Object adapteeResult) throws ExecutionException {
      synchronized(this.mutex) {
         switch (this.state) {
            case SUCCESS:
               return this.result;
            case FAILURE:
               Assert.state(this.result instanceof ExecutionException, "Failure without exception");
               throw (ExecutionException)this.result;
            case NEW:
               Object var10000;
               try {
                  Object adapted = this.adapt(adapteeResult);
                  this.result = adapted;
                  this.state = FutureAdapter.State.SUCCESS;
                  var10000 = adapted;
               } catch (ExecutionException var6) {
                  this.result = var6;
                  this.state = FutureAdapter.State.FAILURE;
                  throw var6;
               } catch (Throwable var7) {
                  ExecutionException execEx = new ExecutionException(var7);
                  this.result = execEx;
                  this.state = FutureAdapter.State.FAILURE;
                  throw execEx;
               }

               return var10000;
            default:
               throw new IllegalStateException();
         }
      }
   }

   @Nullable
   protected abstract Object adapt(Object var1) throws ExecutionException;

   private static enum State {
      NEW,
      SUCCESS,
      FAILURE;
   }
}
