package com.sun.faces.context;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class StringArrayValuesMap extends BaseContextMap {
   public boolean containsValue(Object value) {
      if (value != null && value.getClass().isArray()) {
         Set entrySet = this.entrySet();
         Iterator var3 = entrySet.iterator();

         Map.Entry entry;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            Object anEntrySet = var3.next();
            entry = (Map.Entry)anEntrySet;
         } while(!Arrays.equals((Object[])((Object[])value), (Object[])((Object[])entry.getValue())));

         return true;
      } else {
         return false;
      }
   }

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
               String[] var5 = thisKeys;
               int var6 = thisKeys.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  Object key = var5[var7];
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

   public int hashCode() {
      return this.hashCode(this);
   }

   protected int hashCode(Object someObject) {
      int hashCode = 7 * someObject.hashCode();

      Map.Entry entry;
      for(Iterator var3 = this.entrySet().iterator(); var3.hasNext(); hashCode += Arrays.hashCode((Object[])((Object[])entry.getValue()))) {
         Object o = var3.next();
         entry = (Map.Entry)o;
         hashCode += entry.getKey().hashCode();
      }

      return hashCode;
   }
}
