package com.kenai.jffi;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public final class ClosureManager {
   private final Map poolMap;

   public static ClosureManager getInstance() {
      return ClosureManager.SingletonHolder.INSTANCE;
   }

   private ClosureManager() {
      this.poolMap = new WeakHashMap();
   }

   public final Closure.Handle newClosure(Closure closure, Type returnType, Type[] parameterTypes, CallingConvention convention) {
      return this.newClosure(closure, CallContextCache.getInstance().getCallContext(returnType, parameterTypes, convention));
   }

   public final Closure.Handle newClosure(Closure closure, CallContext callContext) {
      ClosurePool pool = this.getClosurePool(callContext);
      return pool.newClosureHandle(closure);
   }

   public final synchronized ClosurePool getClosurePool(CallContext callContext) {
      Reference ref = (Reference)this.poolMap.get(callContext);
      ClosurePool pool;
      if (ref != null && (pool = (ClosurePool)ref.get()) != null) {
         return pool;
      } else {
         this.poolMap.put(callContext, new SoftReference(pool = new ClosurePool(callContext)));
         return pool;
      }
   }

   public ClosureMagazine newClosureMagazine(CallContext callContext, Method method) {
      Foreign foreign = Foreign.getInstance();
      Class[] methodParameterTypes = method.getParameterTypes();
      boolean callWithPrimitiveArgs = methodParameterTypes.length < 1 || !Closure.Buffer.class.isAssignableFrom(method.getParameterTypes()[0]);
      long magazine = foreign.newClosureMagazine(callContext.getAddress(), method, callWithPrimitiveArgs);
      if (magazine == 0L) {
         throw new RuntimeException("could not allocate new closure magazine");
      } else {
         return new ClosureMagazine(foreign, callContext, magazine);
      }
   }

   // $FF: synthetic method
   ClosureManager(Object x0) {
      this();
   }

   private static final class SingletonHolder {
      static final ClosureManager INSTANCE = new ClosureManager();
   }
}
