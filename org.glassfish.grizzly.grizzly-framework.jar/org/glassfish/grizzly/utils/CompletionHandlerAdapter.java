package org.glassfish.grizzly.utils;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.impl.FutureImpl;

public class CompletionHandlerAdapter implements CompletionHandler {
   private static final GenericAdapter DIRECT_ADAPTER = new GenericAdapter() {
      public Object adapt(Object result) {
         return result;
      }
   };
   private final GenericAdapter adapter;
   private final FutureImpl future;
   private final CompletionHandler completionHandler;

   public CompletionHandlerAdapter(FutureImpl future) {
      this(future, (CompletionHandler)null);
   }

   public CompletionHandlerAdapter(FutureImpl future, CompletionHandler completionHandler) {
      this(future, completionHandler, (GenericAdapter)null);
   }

   public CompletionHandlerAdapter(FutureImpl future, CompletionHandler completionHandler, GenericAdapter adapter) {
      this.future = future;
      this.completionHandler = completionHandler;
      if (adapter != null) {
         this.adapter = adapter;
      } else {
         this.adapter = getDirectAdapter();
      }

   }

   public void cancelled() {
      if (this.completionHandler != null) {
         this.completionHandler.cancelled();
      }

      if (this.future != null) {
         this.future.cancel(false);
      }

   }

   public void failed(Throwable throwable) {
      if (this.completionHandler != null) {
         this.completionHandler.failed(throwable);
      }

      if (this.future != null) {
         this.future.failure(throwable);
      }

   }

   public void completed(Object result) {
      Object adaptedResult = this.adapt(result);
      if (this.completionHandler != null) {
         this.completionHandler.completed(adaptedResult);
      }

      if (this.future != null) {
         this.future.result(adaptedResult);
      }

   }

   public void updated(Object result) {
      Object adaptedResult = this.adapt(result);
      if (this.completionHandler != null) {
         this.completionHandler.updated(adaptedResult);
      }

   }

   protected Object adapt(Object result) {
      return this.adapter.adapt(result);
   }

   private static GenericAdapter getDirectAdapter() {
      return DIRECT_ADAPTER;
   }
}
