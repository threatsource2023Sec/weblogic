package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;

public abstract class AbstractLRSProxyCollection implements Set, LRSProxy, Predicate, CollectionChangeTracker {
   private static final Localizer _loc = Localizer.forPackage(AbstractLRSProxyCollection.class);
   private Class _elementType = null;
   private CollectionChangeTrackerImpl _ct = null;
   private OpenJPAStateManager _sm = null;
   private int _field = -1;
   private OpenJPAStateManager _origOwner = null;
   private int _origField = -1;
   private int _count = -1;
   private boolean _iterated = false;

   public AbstractLRSProxyCollection(Class elementType, boolean ordered) {
      this._elementType = elementType;
      this._ct = new CollectionChangeTrackerImpl(this, false, ordered, false);
      this._ct.setAutoOff(false);
   }

   public void setOwner(OpenJPAStateManager sm, int field) {
      if (sm == null || this._origOwner == null || this._origOwner == sm && this._origField == field) {
         this._sm = sm;
         this._field = field;
         if (sm != null) {
            this._origOwner = sm;
            this._origField = field;
         }

      } else {
         throw new InvalidStateException(_loc.get("transfer-lrs", (Object)this._origOwner.getMetaData().getField(this._origField)));
      }
   }

   public OpenJPAStateManager getOwner() {
      return this._sm;
   }

   public int getOwnerField() {
      return this._field;
   }

   public ChangeTracker getChangeTracker() {
      return this;
   }

   public Object copy(Object orig) {
      return null;
   }

   public boolean add(Object o) {
      Proxies.assertAllowedType(o, this._elementType);
      Proxies.dirty(this, false);
      this._ct.added(o);
      return true;
   }

   public boolean addAll(Collection all) {
      Proxies.dirty(this, false);
      boolean added = false;

      for(Iterator itr = all.iterator(); itr.hasNext(); added = true) {
         Object add = itr.next();
         Proxies.assertAllowedType(add, this._elementType);
         this._ct.added(add);
      }

      return added;
   }

   public boolean remove(Object o) {
      if (!this.contains(o)) {
         return false;
      } else {
         Proxies.dirty(this, false);
         Proxies.removed(this, o, false);
         this._ct.removed(o);
         return true;
      }
   }

   public boolean removeAll(Collection all) {
      Proxies.dirty(this, false);
      boolean removed = false;
      Iterator itr = all.iterator();

      while(itr.hasNext()) {
         Object rem = itr.next();
         if (this.remove(rem)) {
            Proxies.removed(this, rem, false);
            this._ct.removed(rem);
            removed = true;
         }
      }

      return removed;
   }

   public boolean retainAll(Collection all) {
      if (all.isEmpty()) {
         this.clear();
         return true;
      } else {
         Proxies.dirty(this, false);
         Itr itr = (Itr)this.iterator();

         boolean var5;
         try {
            boolean removed = false;

            while(itr.hasNext()) {
               Object rem = itr.next();
               if (!all.contains(rem)) {
                  Proxies.removed(this, rem, false);
                  this._ct.removed(rem);
                  removed = true;
               }
            }

            var5 = removed;
         } finally {
            itr.close();
         }

         return var5;
      }
   }

   public void clear() {
      Proxies.dirty(this, false);
      Itr itr = (Itr)this.iterator();

      try {
         while(itr.hasNext()) {
            Object rem = itr.next();
            Proxies.removed(this, rem, false);
            this._ct.removed(rem);
         }
      } finally {
         itr.close();
      }

   }

   public boolean contains(Object o) {
      if (this._elementType != null && !this._elementType.isInstance(o)) {
         return false;
      } else if (this._ct.getAdded().contains(o)) {
         return true;
      } else if (this._ct.getRemoved().contains(o)) {
         return false;
      } else {
         return this.has(o);
      }
   }

   public boolean containsAll(Collection all) {
      Iterator itr = all.iterator();

      do {
         if (!itr.hasNext()) {
            return true;
         }
      } while(this.contains(itr.next()));

      return false;
   }

   public Object[] toArray() {
      return this.asList().toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.asList().toArray(a);
   }

   private List asList() {
      Itr itr = (Itr)this.iterator();

      try {
         List list = new ArrayList();

         while(itr.hasNext()) {
            list.add(itr.next());
         }

         ArrayList var3 = list;
         return var3;
      } finally {
         itr.close();
      }
   }

   public int size() {
      if (this._count == -1) {
         this._count = this.count();
      }

      return this._count == Integer.MAX_VALUE ? this._count : this._count + this._ct.getAdded().size() - this._ct.getRemoved().size();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public Iterator iterator() {
      this._iterated = true;
      IteratorChain chain = new IteratorChain();
      chain.addIterator(new FilterIterator(this.itr(), this));
      chain.addIterator((new ArrayList(this._ct.getAdded())).iterator());
      return new Itr(chain);
   }

   boolean isIterated() {
      return this._iterated;
   }

   void setIterated(boolean it) {
      this._iterated = it;
   }

   protected Object writeReplace() throws ObjectStreamException {
      return this.asList();
   }

   protected abstract Iterator itr();

   protected abstract boolean has(Object var1);

   protected abstract int count();

   public boolean evaluate(Object o) {
      return !this._ct.getRemoved().contains(o);
   }

   public boolean isTracking() {
      return this._ct.isTracking();
   }

   public void startTracking() {
      this._ct.startTracking();
      this.reset();
   }

   public void stopTracking() {
      this._ct.stopTracking();
      this.reset();
   }

   private void reset() {
      if (this._count != Integer.MAX_VALUE) {
         this._count = -1;
      }

   }

   public Collection getAdded() {
      return this._ct.getAdded();
   }

   public Collection getRemoved() {
      return this._ct.getRemoved();
   }

   public Collection getChanged() {
      return this._ct.getChanged();
   }

   public void added(Object val) {
      this._ct.added(val);
   }

   public void removed(Object val) {
      this._ct.removed(val);
   }

   public int getNextSequence() {
      return this._ct.getNextSequence();
   }

   public void setNextSequence(int seq) {
      this._ct.setNextSequence(seq);
   }

   private class Itr implements Iterator, Closeable {
      private static final int OPEN = 0;
      private static final int LAST_ELEM = 1;
      private static final int CLOSED = 2;
      private final IteratorChain _itr;
      private Object _last = null;
      private int _state = 0;

      public Itr(IteratorChain itr) {
         this._itr = itr;
      }

      public boolean hasNext() {
         if (this._state == 2) {
            return false;
         } else if (!this._itr.hasNext()) {
            this.free();
            this._state = 1;
            return false;
         } else {
            return true;
         }
      }

      public Object next() {
         if (this._state != 0) {
            throw new NoSuchElementException();
         } else {
            this._last = this._itr.next();
            return this._last;
         }
      }

      public void remove() {
         if (this._state != 2 && this._last != null) {
            Proxies.dirty(AbstractLRSProxyCollection.this, false);
            AbstractLRSProxyCollection.this._ct.removed(this._last);
            Proxies.removed(AbstractLRSProxyCollection.this, this._last, false);
            this._last = null;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void close() {
         this.free();
         this._state = 2;
      }

      private void free() {
         if (this._state == 0) {
            List itrs = this._itr.getIterators();

            for(int i = 0; i < itrs.size(); ++i) {
               Iterator itr = (Iterator)itrs.get(i);
               if (itr instanceof FilterIterator) {
                  itr = ((FilterIterator)itr).getIterator();
               }

               ImplHelper.close(itr);
            }

         }
      }

      protected void finalize() {
         this.close();
      }
   }
}
