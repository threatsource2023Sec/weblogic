package weblogic.ejb.container.cache;

import weblogic.ejb.container.InternalException;

public class MultiValueQueryCacheElement extends QueryCacheElement {
   public MultiValueQueryCacheElement(QueryCacheElement[] v) {
      super((Object)v);
   }

   public void setInvalidatable(boolean b) {
   }

   public Object getReturnValue(Object txOrThread, boolean isLocal) throws InternalException {
      QueryCacheElement[] qces = (QueryCacheElement[])((QueryCacheElement[])this.value);
      Object[] result = new Object[qces.length];

      for(int i = 0; i < qces.length; ++i) {
         Object res = qces[i].getReturnValue(txOrThread, isLocal);
         result[i] = res == weblogic.ejb.container.interfaces.QueryCache.NULL_VALUE ? null : res;
      }

      return result;
   }

   public boolean enroll(Object txOrThread) throws InternalException {
      QueryCacheElement[] qces = (QueryCacheElement[])((QueryCacheElement[])this.value);

      for(int i = 0; i < qces.length; ++i) {
         if (!qces[i].enroll(txOrThread)) {
            return false;
         }
      }

      return true;
   }
}
