package org.jboss.weld.util.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;

public abstract class ListToSet extends AbstractSet {
   protected abstract List delegate();

   public Iterator iterator() {
      return this.delegate().iterator();
   }

   public int size() {
      return this.delegate().size();
   }
}
