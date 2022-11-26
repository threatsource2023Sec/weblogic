package org.jboss.weld.util.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class ListMultimap extends AbstractMultimap {
   private static final long serialVersionUID = 6774436969456237682L;

   public ListMultimap() {
      this(HashMap::new, ArrayList::new, (Multimap)null);
   }

   public ListMultimap(Multimap multimap) {
      this(HashMap::new, ArrayList::new, multimap);
   }

   public ListMultimap(Supplier mapSupplier, Supplier collectionSupplier, Multimap multimap) {
      super(mapSupplier, collectionSupplier, multimap);
   }
}
