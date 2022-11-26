package weblogic.management.provider.internal.federatedconfig;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class FederatedConfigDequeDescendingIterator implements Iterator {
   private ListIterator listIterator;

   public FederatedConfigDequeDescendingIterator(LinkedList linkedList) {
      this.listIterator = linkedList.listIterator(linkedList.size());
   }

   public boolean hasNext() {
      return this.listIterator.hasPrevious();
   }

   public Object next() {
      return this.listIterator.previous();
   }

   public void remove() {
      this.listIterator.remove();
   }
}
