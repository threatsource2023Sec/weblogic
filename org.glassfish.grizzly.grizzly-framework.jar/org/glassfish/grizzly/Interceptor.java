package org.glassfish.grizzly;

public interface Interceptor {
   int DEFAULT = 0;
   int COMPLETED = 1;
   int INCOMPLETED = 2;
   int RESET = 4;

   int intercept(int var1, Object var2, Object var3);
}
