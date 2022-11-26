package weblogic.work;

import weblogic.utils.collections.MaybeMapper;

public interface PriorityRequestQueue {
   Object add(Object var1, long var2, RequestClass var4, RequestManager.Callable var5, Object var6);

   /** @deprecated */
   @Deprecated
   Object pop();

   Object pop(MaybeMapper var1, RequestManager.Callable var2, Object var3);

   int size();

   boolean isEmpty();

   long getMaxValue();
}
