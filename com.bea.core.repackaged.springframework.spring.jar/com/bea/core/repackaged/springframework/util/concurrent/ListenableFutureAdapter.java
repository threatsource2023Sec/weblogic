package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.ExecutionException;

public abstract class ListenableFutureAdapter extends FutureAdapter implements ListenableFuture {
   protected ListenableFutureAdapter(ListenableFuture adaptee) {
      super(adaptee);
   }

   public void addCallback(ListenableFutureCallback callback) {
      this.addCallback(callback, callback);
   }

   public void addCallback(final SuccessCallback successCallback, final FailureCallback failureCallback) {
      ListenableFuture listenableAdaptee = (ListenableFuture)this.getAdaptee();
      listenableAdaptee.addCallback(new ListenableFutureCallback() {
         public void onSuccess(@Nullable Object result) {
            Object adapted = null;
            if (result != null) {
               try {
                  adapted = ListenableFutureAdapter.this.adaptInternal(result);
               } catch (ExecutionException var5) {
                  Throwable cause = var5.getCause();
                  this.onFailure((Throwable)(cause != null ? cause : var5));
                  return;
               } catch (Throwable var6) {
                  this.onFailure(var6);
                  return;
               }
            }

            successCallback.onSuccess(adapted);
         }

         public void onFailure(Throwable ex) {
            failureCallback.onFailure(ex);
         }
      });
   }
}
