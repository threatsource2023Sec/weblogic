package org.glassfish.grizzly;

public interface Reader {
   int READ_EVENT = 1;
   int COMPLETE_EVENT = 2;
   int INCOMPLETE_EVENT = 3;

   GrizzlyFuture read(Connection var1);

   GrizzlyFuture read(Connection var1, Buffer var2);

   void read(Connection var1, Buffer var2, CompletionHandler var3);

   void read(Connection var1, Buffer var2, CompletionHandler var3, Interceptor var4);
}
