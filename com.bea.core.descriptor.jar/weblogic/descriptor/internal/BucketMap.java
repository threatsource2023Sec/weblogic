package weblogic.descriptor.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

class BucketMap extends ConcurrentHashMap {
   private List orderedKeys = new CopyOnWriteArrayList();

   public Iterator getBucketIterator(Object key) {
      Deque bucket = this.getBucket(key);
      return bucket == null ? Collections.EMPTY_LIST.iterator() : bucket.iterator();
   }

   public final Deque put(Object key, Deque object) {
      throw new UnsupportedOperationException("use putInBucket()");
   }

   public void putInBucket(Object key, Object object) {
      Deque bucket = this.getBucket(key);
      if (bucket == null) {
         bucket = new ConcurrentLinkedDeque();
         Deque oldBucket = (Deque)super.putIfAbsent(key, bucket);
         if (oldBucket != null) {
            bucket = oldBucket;
         } else {
            this.orderedKeys.add(key);
         }
      }

      ((Deque)bucket).addLast(object);
   }

   public void removeFromBucket(Object key, Object object) {
      Deque bucket = this.getBucket(key);
      if (bucket != null) {
         bucket.remove(object);
         if (bucket.size() == 0) {
            this.orderedKeys.remove(key);
            this.remove(key);
         }
      }

   }

   public Collection allValues() {
      Collection list = new ArrayList(this.size());
      Iterator var2 = super.values().iterator();

      while(var2.hasNext()) {
         Deque d = (Deque)var2.next();
         list.addAll(d);
      }

      return list;
   }

   public List getOrderedKeys() {
      return this.orderedKeys;
   }

   protected final Deque getBucket(Object key) {
      return (Deque)this.get(key);
   }
}
