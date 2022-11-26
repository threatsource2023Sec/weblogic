package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.LinkedList;
import java.util.Queue;

public class ListenableFutureCallbackRegistry {
   private final Queue successCallbacks = new LinkedList();
   private final Queue failureCallbacks = new LinkedList();
   private State state;
   @Nullable
   private Object result;
   private final Object mutex;

   public ListenableFutureCallbackRegistry() {
      this.state = ListenableFutureCallbackRegistry.State.NEW;
      this.mutex = new Object();
   }

   public void addCallback(ListenableFutureCallback callback) {
      Assert.notNull(callback, (String)"'callback' must not be null");
      synchronized(this.mutex) {
         switch (this.state) {
            case NEW:
               this.successCallbacks.add(callback);
               this.failureCallbacks.add(callback);
               break;
            case SUCCESS:
               this.notifySuccess(callback);
               break;
            case FAILURE:
               this.notifyFailure(callback);
         }

      }
   }

   private void notifySuccess(SuccessCallback callback) {
      try {
         callback.onSuccess(this.result);
      } catch (Throwable var3) {
      }

   }

   private void notifyFailure(FailureCallback callback) {
      Assert.state(this.result instanceof Throwable, "No Throwable result for failure state");

      try {
         callback.onFailure((Throwable)this.result);
      } catch (Throwable var3) {
      }

   }

   public void addSuccessCallback(SuccessCallback callback) {
      Assert.notNull(callback, (String)"'callback' must not be null");
      synchronized(this.mutex) {
         switch (this.state) {
            case NEW:
               this.successCallbacks.add(callback);
               break;
            case SUCCESS:
               this.notifySuccess(callback);
         }

      }
   }

   public void addFailureCallback(FailureCallback callback) {
      Assert.notNull(callback, (String)"'callback' must not be null");
      synchronized(this.mutex) {
         switch (this.state) {
            case NEW:
               this.failureCallbacks.add(callback);
               break;
            case FAILURE:
               this.notifyFailure(callback);
         }

      }
   }

   public void success(@Nullable Object result) {
      synchronized(this.mutex) {
         this.state = ListenableFutureCallbackRegistry.State.SUCCESS;
         this.result = result;

         SuccessCallback callback;
         while((callback = (SuccessCallback)this.successCallbacks.poll()) != null) {
            this.notifySuccess(callback);
         }

      }
   }

   public void failure(Throwable ex) {
      synchronized(this.mutex) {
         this.state = ListenableFutureCallbackRegistry.State.FAILURE;
         this.result = ex;

         FailureCallback callback;
         while((callback = (FailureCallback)this.failureCallbacks.poll()) != null) {
            this.notifyFailure(callback);
         }

      }
   }

   private static enum State {
      NEW,
      SUCCESS,
      FAILURE;
   }
}
