package weblogic.ejb.container.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MultiMap {
   private Map map = new HashMap();

   public int put(Object key, Object value) {
      List elts = this.get(key);
      elts.add(value);
      return elts.size();
   }

   public List get(Object key) {
      List elts = (List)this.map.get(key);
      if (elts == null) {
         elts = new ArrayList();
         this.map.put(key, elts);
      }

      return (List)elts;
   }

   public Object get(Object key, Object value) {
      List elts = this.get(key);
      Iterator i = elts.iterator();

      Object obj;
      do {
         if (!i.hasNext()) {
            return null;
         }

         obj = i.next();
      } while(obj == null || !obj.equals(value));

      return obj;
   }

   public Object remove(Object key, Object value) {
      List elts = this.get(key);
      if (elts.size() == 0) {
         return null;
      } else {
         Iterator i = elts.iterator();
         boolean removed = false;

         while(i.hasNext()) {
            Object obj = i.next();
            if (obj != null && obj.equals(value)) {
               elts.remove(obj);
               removed = true;
            }
         }

         if (removed) {
            return value;
         } else {
            return null;
         }
      }
   }

   public Set keySet() {
      return this.map.keySet();
   }
}
