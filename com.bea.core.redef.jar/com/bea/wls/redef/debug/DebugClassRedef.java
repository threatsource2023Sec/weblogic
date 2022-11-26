package com.bea.wls.redef.debug;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class DebugClassRedef {
   private final BackingStore store;
   private final String annotation;
   private static final Map INSTANCE_CACHE = new ConcurrentWeakHashMap();
   private List entries = new CopyOnWriteArrayList();

   public static DebugClassRedef getInstance(GenericClassLoader gcl) {
      DebugClassRedef instance = (DebugClassRedef)INSTANCE_CACHE.get(gcl);
      if (instance != null) {
         return instance;
      } else {
         synchronized(INSTANCE_CACHE) {
            DebugClassRedef d = (DebugClassRedef)INSTANCE_CACHE.get(gcl);
            if (d != null) {
               return d;
            } else {
               d = new DebugClassRedef(BackingStoreFactory.make(), gcl.getAnnotation().toString());
               INSTANCE_CACHE.put(gcl, d);
               return d;
            }
         }
      }
   }

   public DebugClassRedef(BackingStore store, String annotation) {
      this.store = store;
      this.annotation = annotation;
   }

   public StoreEntry get() {
      StoreEntry entry = new StoreEntry(this.annotation);
      this.entries.add(entry);
      return entry;
   }

   public void commit() {
      Iterator i = this.entries.iterator();

      while(i.hasNext()) {
         this.store.write((StoreEntry)i.next());
      }

      this.entries.clear();
   }

   public void reset() {
      this.commit();
   }
}
