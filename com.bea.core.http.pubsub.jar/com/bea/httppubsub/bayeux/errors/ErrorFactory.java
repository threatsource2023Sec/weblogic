package com.bea.httppubsub.bayeux.errors;

public interface ErrorFactory {
   String getError(int var1);

   String getError(int var1, String... var2);
}
