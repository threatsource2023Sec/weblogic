package org.glassfish.grizzly;

public interface CompletionHandler {
   void cancelled();

   void failed(Throwable var1);

   void completed(Object var1);

   void updated(Object var1);
}
