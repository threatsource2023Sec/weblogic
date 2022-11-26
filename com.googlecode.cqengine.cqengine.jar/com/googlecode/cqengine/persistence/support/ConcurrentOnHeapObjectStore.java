package com.googlecode.cqengine.persistence.support;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentOnHeapObjectStore extends CollectionWrappingObjectStore {
   public ConcurrentOnHeapObjectStore() {
      super(Collections.newSetFromMap(new ConcurrentHashMap()));
   }

   public ConcurrentOnHeapObjectStore(int initialCapacity, float loadFactor, int concurrencyLevel) {
      super(Collections.newSetFromMap(new ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel)));
   }
}
