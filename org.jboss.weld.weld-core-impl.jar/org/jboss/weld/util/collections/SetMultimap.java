package org.jboss.weld.util.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class SetMultimap extends AbstractMultimap {
   private static final long serialVersionUID = -7310409235342796148L;

   public static SetMultimap newSetMultimap() {
      return new SetMultimap(HashMap::new, HashSet::new, (Multimap)null);
   }

   public static SetMultimap newSetMultimap(Multimap multimap) {
      return new SetMultimap(HashMap::new, HashSet::new, multimap);
   }

   public static SetMultimap newConcurrentSetMultimap() {
      return newConcurrentSetMultimap(() -> {
         return Collections.synchronizedSet(new HashSet());
      });
   }

   public static SetMultimap newConcurrentSetMultimap(Supplier valueSupplier) {
      return new SetMultimap(ConcurrentHashMap::new, valueSupplier, (Multimap)null);
   }

   private SetMultimap(Supplier mapSupplier, Supplier collectionSupplier, Multimap multimap) {
      super(mapSupplier, collectionSupplier, multimap);
   }
}
