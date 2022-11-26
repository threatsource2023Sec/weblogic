package org.glassfish.grizzly.http.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public final class Enumerator implements Enumeration {
   private Iterator iterator;

   public Enumerator(Collection collection) {
      this(collection.iterator());
   }

   public Enumerator(Collection collection, boolean clone) {
      this(collection.iterator(), clone);
   }

   public Enumerator(Iterable iterable) {
      this(iterable.iterator());
   }

   public Enumerator(Iterable iterable, boolean clone) {
      this(iterable.iterator(), clone);
   }

   public Enumerator(Iterator iterator) {
      this.iterator = null;
      this.iterator = iterator;
   }

   public Enumerator(Iterator iterator, boolean clone) {
      this.iterator = null;
      if (!clone) {
         this.iterator = iterator;
      } else {
         List list = new ArrayList();

         while(iterator.hasNext()) {
            list.add(iterator.next());
         }

         this.iterator = list.iterator();
      }

   }

   public Enumerator(Map map) {
      this(map.values().iterator());
   }

   public Enumerator(Map map, boolean clone) {
      this(map.values().iterator(), clone);
   }

   public boolean hasMoreElements() {
      return this.iterator.hasNext();
   }

   public Object nextElement() throws NoSuchElementException {
      return this.iterator.next();
   }
}
