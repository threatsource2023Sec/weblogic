package org.glassfish.tyrus.spi;

public abstract class CompletionHandler {
   public void cancelled() {
   }

   public void failed(Throwable throwable) {
   }

   public void completed(Object result) {
   }

   public void updated(Object result) {
   }
}
