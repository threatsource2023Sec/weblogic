package com.sun.faces.context;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class StringArrayValuesMap extends BaseContextMap {
   public boolean equals(Object obj) {
      if (obj != null && obj.getClass() == ExternalContextImpl.theUnmodifiableMapClass) {
         Map objMap = (Map)obj;
         if (this.size() != objMap.size()) {
            return false;
         } else {
            String[] thisKeys = (String[])this.keySet().toArray(new String[this.size()]);
            Object[] objKeys = objMap.keySet().toArray();
            Arrays.sort(thisKeys);
            Arrays.sort(objKeys);
            if (!Arrays.equals(thisKeys, objKeys)) {
               return false;
            } else {
               String[] arr$ = thisKeys;
               int len$ = thisKeys.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  Object key = arr$[i$];
                  Object[] thisVal = (Object[])this.get(key);
                  Object[] objVal = (Object[])((Object[])objMap.get(key));
                  if (!Arrays.equals(thisVal, objVal)) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   protected int hashCode(Object someObject) {
      int hashCode = 7 * someObject.hashCode();

      Map.Entry entry;
      for(Iterator i$ = this.entrySet().iterator(); i$.hasNext(); hashCode += Arrays.hashCode((Object[])((Object[])entry.getValue()))) {
         Object o = i$.next();
         entry = (Map.Entry)o;
         hashCode += entry.getKey().hashCode();
      }

      return hashCode;
   }

   public boolean containsValue(Object value) {
      if (value != null && value.getClass().isArray()) {
         Set entrySet = this.entrySet();
         Iterator i$ = entrySet.iterator();

         Map.Entry entry;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            Object anEntrySet = i$.next();
            entry = (Map.Entry)anEntrySet;
         } while(!Arrays.equals((Object[])((Object[])value), (Object[])((Object[])entry.getValue())));

         return true;
      } else {
         return false;
      }
   }
}
