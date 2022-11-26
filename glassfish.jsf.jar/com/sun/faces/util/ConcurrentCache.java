package com.sun.faces.util;

import java.util.concurrent.ExecutionException;

public abstract class ConcurrentCache {
   private final Factory _f;

   public ConcurrentCache(Factory f) {
      this._f = f;
   }

   public abstract Object get(Object var1) throws ExecutionException;

   public abstract boolean containsKey(Object var1);

   protected final Factory getFactory() {
      return this._f;
   }

   public interface Factory {
      Object newInstance(Object var1) throws Exception;
   }
}
