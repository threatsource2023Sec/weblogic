package org.apache.openjpa.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProxyCollections extends Proxies {
   public static void beforeAdd(ProxyCollection coll, int index, Object value) {
      assertAllowedType(value, coll.getElementType());
      if (index == coll.size()) {
         beforeAdd(coll, value);
      } else {
         dirty(coll, true);
      }

   }

   public static void beforeInsertElementAt(ProxyCollection coll, Object value, int index) {
      beforeAdd(coll, index, value);
   }

   public static void beforeAdd(ProxyCollection coll, Object value) {
      assertAllowedType(value, coll.getElementType());
      dirty(coll, false);
   }

   public static boolean afterAdd(ProxyCollection coll, Object value, boolean added) {
      if (added && coll.getChangeTracker() != null) {
         ((CollectionChangeTracker)coll.getChangeTracker()).added(value);
      }

      return added;
   }

   public static void beforeAddElement(ProxyCollection coll, Object value) {
      beforeAdd(coll, value);
   }

   public static void afterAddElement(ProxyCollection coll, Object value) {
      afterAdd(coll, value, true);
   }

   public static void beforeAddFirst(ProxyCollection coll, Object value) {
      beforeAdd(coll, 0, value);
   }

   public static void beforeAddLast(ProxyCollection coll, Object value) {
      beforeAdd(coll, value);
   }

   public static void afterAddLast(ProxyCollection coll, Object value) {
      afterAdd(coll, value, true);
   }

   public static void beforeOffer(ProxyCollection coll, Object value) {
      beforeAdd(coll, value);
   }

   public static boolean afterOffer(ProxyCollection coll, Object value, boolean added) {
      return afterAdd(coll, value, added);
   }

   public static boolean addAll(ProxyCollection coll, int index, Collection values) {
      List list = (List)coll;

      for(Iterator itr = values.iterator(); itr.hasNext(); ++index) {
         list.add(index, itr.next());
      }

      return values.size() > 0;
   }

   public static boolean addAll(ProxyCollection coll, Collection values) {
      boolean added = false;

      for(Iterator itr = values.iterator(); itr.hasNext(); added |= coll.add(itr.next())) {
      }

      return added;
   }

   public static void beforeClear(ProxyCollection coll) {
      dirty(coll, true);
      Iterator itr = coll.iterator();

      while(itr.hasNext()) {
         removed(coll, itr.next(), false);
      }

   }

   public static void beforeRemoveAllElements(ProxyCollection coll) {
      beforeClear(coll);
   }

   public static Iterator afterIterator(final ProxyCollection coll, final Iterator itr) {
      return (Iterator)(itr instanceof ProxyIterator ? itr : new ProxyIterator() {
         private Object _last = null;

         public boolean hasNext() {
            return itr.hasNext();
         }

         public Object next() {
            this._last = itr.next();
            return this._last;
         }

         public void remove() {
            Proxies.dirty(coll, false);
            itr.remove();
            if (coll.getChangeTracker() != null) {
               ((CollectionChangeTracker)coll.getChangeTracker()).removed(this._last);
            }

            Proxies.removed(coll, this._last, false);
         }
      });
   }

   public static ListIterator afterListIterator(ProxyCollection coll, int idx, ListIterator itr) {
      return afterListIterator(coll, itr);
   }

   public static ListIterator afterListIterator(final ProxyCollection coll, final ListIterator itr) {
      return (ListIterator)(itr instanceof ProxyListIterator ? itr : new ProxyListIterator() {
         private Object _last = null;

         public boolean hasNext() {
            return itr.hasNext();
         }

         public int nextIndex() {
            return itr.nextIndex();
         }

         public Object next() {
            this._last = itr.next();
            return this._last;
         }

         public boolean hasPrevious() {
            return itr.hasPrevious();
         }

         public int previousIndex() {
            return itr.previousIndex();
         }

         public Object previous() {
            this._last = itr.previous();
            return this._last;
         }

         public void set(Object o) {
            Proxies.assertAllowedType(o, coll.getElementType());
            Proxies.dirty(coll, false);
            itr.set(o);
            if (coll.getChangeTracker() != null) {
               coll.getChangeTracker().stopTracking();
            }

            Proxies.removed(coll, this._last, false);
            this._last = o;
         }

         public void add(Object o) {
            Proxies.assertAllowedType(o, coll.getElementType());
            Proxies.dirty(coll, false);
            itr.add(o);
            if (coll.getChangeTracker() != null) {
               if (this.hasNext()) {
                  coll.getChangeTracker().stopTracking();
               } else {
                  ((CollectionChangeTracker)coll.getChangeTracker()).added(o);
               }
            }

            this._last = o;
         }

         public void remove() {
            Proxies.dirty(coll, false);
            itr.remove();
            if (coll.getChangeTracker() != null) {
               ((CollectionChangeTracker)coll.getChangeTracker()).removed(this._last);
            }

            Proxies.removed(coll, this._last, false);
         }
      });
   }

   public static void beforeRemove(ProxyCollection coll, int index) {
      dirty(coll, false);
   }

   public static Object afterRemove(ProxyCollection coll, int index, Object removed) {
      if (coll.getChangeTracker() != null) {
         ((CollectionChangeTracker)coll.getChangeTracker()).removed(removed);
      }

      removed(coll, removed, false);
      return removed;
   }

   public static void beforeRemoveElementAt(ProxyCollection coll, int index) {
      beforeRemove(coll, index);
   }

   public static void beforeRemove(ProxyCollection coll, Object o) {
      dirty(coll, false);
   }

   public static boolean afterRemove(ProxyCollection coll, Object o, boolean removed) {
      if (!removed) {
         return false;
      } else {
         if (coll.getChangeTracker() != null) {
            ((CollectionChangeTracker)coll.getChangeTracker()).removed(o);
         }

         removed(coll, o, false);
         return true;
      }
   }

   public static void beforeRemoveElement(ProxyCollection coll, Object o) {
      beforeRemove(coll, o);
   }

   public static boolean afterRemoveElement(ProxyCollection coll, Object o, boolean removed) {
      return afterRemove(coll, o, removed);
   }

   public static void beforeRemoveFirst(ProxyCollection coll) {
      beforeRemove(coll, 0);
   }

   public static Object afterRemoveFirst(ProxyCollection coll, Object removed) {
      return afterRemove(coll, 0, removed);
   }

   public static void beforeRemoveLast(ProxyCollection coll) {
      beforeRemove(coll, coll.size() - 1);
   }

   public static Object afterRemoveLast(ProxyCollection coll, Object removed) {
      return afterRemove(coll, coll.size(), removed);
   }

   public static void beforeRemove(ProxyCollection coll) {
      beforeRemove(coll, 0);
   }

   public static Object afterRemove(ProxyCollection coll, Object removed) {
      return afterRemove(coll, 0, removed);
   }

   public static void beforePoll(ProxyCollection coll) {
      if (!coll.isEmpty()) {
         beforeRemove(coll, 0);
      }

   }

   public static Object afterPoll(ProxyCollection coll, Object removed) {
      if (removed != null) {
         afterRemove(coll, 0, removed);
      }

      return removed;
   }

   public static boolean removeAll(ProxyCollection coll, Collection vals) {
      boolean removed = false;

      for(Iterator itr = vals.iterator(); itr.hasNext(); removed |= coll.remove(itr.next())) {
      }

      return removed;
   }

   public static boolean retainAll(ProxyCollection coll, Collection vals) {
      int size = coll.size();
      Iterator itr = coll.iterator();

      while(itr.hasNext()) {
         if (!vals.contains(itr.next())) {
            itr.remove();
         }
      }

      return coll.size() < size;
   }

   public static void beforeSet(ProxyCollection coll, int index, Object element) {
      assertAllowedType(element, coll.getElementType());
      dirty(coll, true);
   }

   public static Object afterSet(ProxyCollection coll, int index, Object element, Object replaced) {
      if (replaced != element) {
         removed(coll, replaced, false);
      }

      return replaced;
   }

   public static void beforeSetElementAt(ProxyCollection coll, Object element, int index) {
      beforeSet(coll, index, element);
   }

   public static Object afterSetElementAt(ProxyCollection coll, Object element, int index, Object replaced) {
      return afterSet(coll, index, element, replaced);
   }

   public interface ProxyListIterator extends ProxyIterator, ListIterator {
   }

   public interface ProxyIterator extends Iterator {
   }
}
