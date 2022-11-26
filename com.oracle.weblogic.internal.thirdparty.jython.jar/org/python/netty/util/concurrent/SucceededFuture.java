package org.python.netty.util.concurrent;

public final class SucceededFuture extends CompleteFuture {
   private final Object result;

   public SucceededFuture(EventExecutor executor, Object result) {
      super(executor);
      this.result = result;
   }

   public Throwable cause() {
      return null;
   }

   public boolean isSuccess() {
      return true;
   }

   public Object getNow() {
      return this.result;
   }
}
