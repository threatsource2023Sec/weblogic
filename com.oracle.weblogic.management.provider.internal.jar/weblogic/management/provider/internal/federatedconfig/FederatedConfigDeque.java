package weblogic.management.provider.internal.federatedconfig;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class FederatedConfigDeque extends LinkedList {
   public FederatedConfigDeque() {
   }

   public FederatedConfigDeque(Collection c) {
      super(c);
   }

   public Iterator descendingIterator() {
      return new FederatedConfigDequeDescendingIterator(this);
   }
}
