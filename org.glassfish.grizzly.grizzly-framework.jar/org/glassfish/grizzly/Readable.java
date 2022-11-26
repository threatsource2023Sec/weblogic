package org.glassfish.grizzly;

public interface Readable {
   GrizzlyFuture read();

   void read(CompletionHandler var1);
}
