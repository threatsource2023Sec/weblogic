package org.glassfish.grizzly;

public class EmptyCompletionHandler implements CompletionHandler {
   public void cancelled() {
   }

   public void failed(Throwable throwable) {
   }

   public void completed(Object result) {
   }

   public void updated(Object result) {
   }
}
