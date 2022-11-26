package weblogic.jdbc.common.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import weblogic.common.resourcepool.IGroupingPooledResourceLinkedList;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.collections.EmbeddedListElement;

public class IndexedGroupingPooledResourceLinkedList implements IGroupingPooledResourceLinkedList {
   EmbeddedList list = new EmbeddedList();
   IndexedGroupingPooledResourceSet indexedSet = new IndexedGroupingPooledResourceSet();

   public void addFirst(PooledResource obj) {
      PooledResourceInfo pri = obj.getPooledResourceInfo();
      this.list.add((EmbeddedListElement)obj);
      if (pri != null || obj instanceof ConnectionEnv) {
         this.indexedSet.add(obj);
      }

   }

   public void addLast(PooledResource obj) {
      throw new UnsupportedOperationException();
   }

   public Object removeFirst() {
      Object first = this.list.last();
      if (first != null) {
         this.list.remove(first);
         boolean removed = this.indexedSet.remove(first);
         if (!removed) {
         }
      }

      return first;
   }

   public Object removeLast() {
      Object last = this.list.first();
      if (last != null) {
         this.list.remove(last);
         boolean removed = this.indexedSet.remove(last);
         if (!removed) {
         }
      }

      return last;
   }

   public PooledResource removeMatching(PooledResourceInfo info) {
      PooledResource pr = this.indexedSet.removeMatching(info);
      if (pr == null) {
         return null;
      } else {
         this.list.remove(pr);
         return pr;
      }
   }

   public int sizeHigh() {
      return this.indexedSet.sizeHigh();
   }

   public boolean add(PooledResource e) {
      this.addFirst(e);
      return this.contains(e);
   }

   public void add(int index, PooledResource element) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int index, Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      this.list.clear();
      this.indexedSet.clear();
   }

   public boolean contains(Object o) {
      return this.list.contains(o);
   }

   public boolean containsAll(Collection c) {
      return this.list.containsAll(c);
   }

   public PooledResource get(int index) {
      return (PooledResource)this.list.get(index);
   }

   public int indexOf(Object o) {
      return this.list.indexOf(o);
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public Iterator iterator() {
      return this.list.iterator();
   }

   public int lastIndexOf(Object o) {
      return this.list.lastIndexOf(o);
   }

   public ListIterator listIterator() {
      return this.list.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.list.listIterator(index);
   }

   public boolean remove(Object o) {
      this.indexedSet.remove(o);
      return this.list.remove(o);
   }

   public PooledResource remove(int index) {
      PooledResource pr = (PooledResource)this.list.get(index);
      if (pr != null) {
         this.indexedSet.remove(pr);
      }

      this.list.remove(pr);
      return pr;
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public PooledResource set(int index, PooledResource element) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.list.size();
   }

   public List subList(int fromIndex, int toIndex) {
      return this.list.subList(fromIndex, toIndex);
   }

   public Object[] toArray() {
      return this.list.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.list.toArray(a);
   }

   public int getSize(PooledResourceInfo group) {
      return this.indexedSet.getSize(group);
   }

   public List getSubList(PooledResourceInfo group) {
      return this.indexedSet.getSubList(group);
   }

   public void resetStatistics() {
      this.indexedSet.resetStatistics();
   }
}
