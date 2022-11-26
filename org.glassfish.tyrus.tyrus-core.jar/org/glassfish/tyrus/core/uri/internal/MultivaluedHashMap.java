package org.glassfish.tyrus.core.uri.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultivaluedHashMap extends AbstractMultivaluedMap implements Serializable {
   private static final long serialVersionUID = -6052320403766368902L;

   public MultivaluedHashMap() {
      super(new HashMap());
   }

   public MultivaluedHashMap(int initialCapacity) {
      super(new HashMap(initialCapacity));
   }

   public MultivaluedHashMap(int initialCapacity, float loadFactor) {
      super(new HashMap(initialCapacity, loadFactor));
   }

   public MultivaluedHashMap(MultivaluedMap map) {
      this();
      this.putAll(map);
   }

   private void putAll(MultivaluedMap map) {
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         this.store.put(e.getKey(), new ArrayList((Collection)e.getValue()));
      }

   }

   public MultivaluedHashMap(Map map) {
      this();
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         this.putSingle(e.getKey(), e.getValue());
      }

   }
}
