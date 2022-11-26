package org.antlr.runtime.misc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DoubleKeyMap {
   Map data = new LinkedHashMap();

   public Object put(Object k1, Object k2, Object v) {
      Map data2 = (Map)this.data.get(k1);
      Object prev = null;
      if (data2 == null) {
         data2 = new LinkedHashMap();
         this.data.put(k1, data2);
      } else {
         prev = ((Map)data2).get(k2);
      }

      ((Map)data2).put(k2, v);
      return prev;
   }

   public Object get(Object k1, Object k2) {
      Map data2 = (Map)this.data.get(k1);
      return data2 == null ? null : data2.get(k2);
   }

   public Map get(Object k1) {
      return (Map)this.data.get(k1);
   }

   public Collection values(Object k1) {
      Map data2 = (Map)this.data.get(k1);
      return data2 == null ? null : data2.values();
   }

   public Set keySet() {
      return this.data.keySet();
   }

   public Set keySet(Object k1) {
      Map data2 = (Map)this.data.get(k1);
      return data2 == null ? null : data2.keySet();
   }

   public Collection values() {
      Set s = new HashSet();
      Iterator i$ = this.data.values().iterator();

      while(i$.hasNext()) {
         Map k2 = (Map)i$.next();
         Iterator i$ = k2.values().iterator();

         while(i$.hasNext()) {
            Object v = i$.next();
            s.add(v);
         }
      }

      return s;
   }
}
