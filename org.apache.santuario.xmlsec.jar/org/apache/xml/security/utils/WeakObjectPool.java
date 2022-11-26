package org.apache.xml.security.utils;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class WeakObjectPool {
   private static final Integer MARKER_VALUE = Integer.MAX_VALUE;
   private final BlockingQueue available = new LinkedBlockingDeque();
   private final Map onLoan = Collections.synchronizedMap(new WeakHashMap());

   protected WeakObjectPool() {
   }

   protected abstract Object createObject() throws Throwable;

   public Object getObject() throws Throwable {
      Object retValue = null;

      WeakReference ref;
      do {
         ref = (WeakReference)this.available.poll();
      } while(ref != null && (retValue = ref.get()) == null);

      if (retValue == null) {
         retValue = this.createObject();
      }

      this.onLoan.put(retValue, MARKER_VALUE);
      return retValue;
   }

   public boolean repool(Object obj) {
      if (obj != null && this.onLoan.containsKey(obj)) {
         synchronized(obj) {
            if (this.onLoan.remove(obj) != null) {
               return this.available.offer(new WeakReference(obj));
            }
         }
      }

      return false;
   }
}
