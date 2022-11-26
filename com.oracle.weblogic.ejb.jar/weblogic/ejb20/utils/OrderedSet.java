package weblogic.ejb20.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class OrderedSet extends ArrayList implements Set {
   private static final long serialVersionUID = 4253358509937919074L;

   public OrderedSet() {
   }

   public OrderedSet(Collection c) {
      this.addAll(c);
   }

   public boolean add(Object o) {
      if (null == o) {
         return false;
      } else {
         return this.contains(o) ? false : super.add(o);
      }
   }

   public boolean addAll(Collection c) {
      if (null == c) {
         return false;
      } else {
         boolean modified = false;
         Iterator it = c.iterator();

         while(it.hasNext()) {
            if (this.add(it.next())) {
               modified = true;
            }
         }

         return modified;
      }
   }
}
