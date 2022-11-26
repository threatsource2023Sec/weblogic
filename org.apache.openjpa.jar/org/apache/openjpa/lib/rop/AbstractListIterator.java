package org.apache.openjpa.lib.rop;

import java.util.ListIterator;
import org.apache.openjpa.lib.util.Localizer;

abstract class AbstractListIterator implements ListIterator {
   private static final Localizer _loc = Localizer.forPackage(AbstractListIterator.class);

   public void add(Object o) {
      throw new UnsupportedOperationException(_loc.get("read-only").getMessage());
   }

   public void set(Object o) {
      throw new UnsupportedOperationException(_loc.get("read-only").getMessage());
   }

   public void remove() {
      throw new UnsupportedOperationException(_loc.get("read-only").getMessage());
   }
}
