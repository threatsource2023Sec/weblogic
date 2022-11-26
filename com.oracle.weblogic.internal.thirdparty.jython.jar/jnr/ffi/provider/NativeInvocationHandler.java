package jnr.ffi.provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public class NativeInvocationHandler implements InvocationHandler {
   private volatile Map fastLookupTable;
   private final Map invokerMap;

   public NativeInvocationHandler(Map invokers) {
      this.invokerMap = invokers;
      this.fastLookupTable = Collections.emptyMap();
   }

   public Object invoke(Object self, Method method, Object[] argArray) throws Throwable {
      Invoker invoker = (Invoker)this.fastLookupTable.get(method);
      return invoker != null ? invoker.invoke(self, argArray) : this.lookupAndCacheInvoker(method).invoke(self, argArray);
   }

   private synchronized Invoker lookupAndCacheInvoker(Method method) {
      Invoker invoker;
      if ((invoker = (Invoker)this.fastLookupTable.get(method)) != null) {
         return invoker;
      } else {
         Map map = new IdentityHashMap(this.fastLookupTable);
         map.put(method, invoker = (Invoker)this.invokerMap.get(method));
         if (invoker == null) {
            throw new UnsatisfiedLinkError("no invoker for native method " + method.getName());
         } else {
            this.fastLookupTable = map;
            return invoker;
         }
      }
   }
}
