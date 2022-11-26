package weblogic.common.resourcepool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.collections.EmbeddedListElement;

public class GroupingPooledResourceLinkedList implements IGroupingPooledResourceLinkedList {
   int numEntriesHigh = 0;
   EmbeddedList list = new EmbeddedList();
   Groups groups = new Groups();

   public void addFirst(PooledResource obj) {
      PooledResourceInfo pri = obj.getPooledResourceInfo();
      this.list.add((EmbeddedListElement)obj);
      if (pri != null) {
         this.groups.add(obj);
      }

      if (this.numEntriesHigh < this.size()) {
         this.numEntriesHigh = this.size();
      }

   }

   public void addLast(PooledResource obj) {
      throw new UnsupportedOperationException();
   }

   public Object removeFirst() {
      Object first = this.list.last();
      if (first != null) {
         this.list.remove(first);
         boolean removed = this.groups.removeFirst((PooledResource)first);
         if (!removed) {
         }
      }

      return first;
   }

   public Object removeLast() {
      Object last = this.list.first();
      if (last != null) {
         this.list.remove(last);
         boolean removed = this.groups.remove((PooledResource)last);
         if (!removed) {
         }
      }

      return last;
   }

   public PooledResource removeMatching(PooledResourceInfo info) {
      PooledResource pr = this.groups.removeFirst(info);
      if (pr == null) {
         return null;
      } else {
         this.list.remove(pr);
         return pr;
      }
   }

   public int sizeHigh() {
      return this.numEntriesHigh;
   }

   public boolean add(PooledResource e) {
      throw new UnsupportedOperationException();
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
      this.groups.clear();
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
      this.groups.remove((PooledResource)o);
      return this.list.remove(o);
   }

   public PooledResource remove(int index) {
      PooledResource pr = (PooledResource)this.list.get(index);
      if (pr != null) {
         this.groups.remove(pr);
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
      List resources = this.groups.getGroupList(group);
      return resources == null ? 0 : resources.size();
   }

   public List getSubList(PooledResourceInfo group) {
      return this.groups.getGroupList(group);
   }

   public void resetStatistics() {
      this.numEntriesHigh = this.size();
   }

   class Groups {
      Map groups = new HashMap();

      void add(PooledResource pr) {
         PooledResourceInfo pri = pr.getPooledResourceInfo();
         if (pri != null) {
            Stack group = this.getOrCreate(pri);
            group.push(pr);
         }
      }

      PooledResource removeFirst(PooledResourceInfo pri) {
         Stack group = (Stack)this.groups.get(pri);
         return group != null && !group.isEmpty() ? (PooledResource)group.pop() : null;
      }

      boolean removeFirst(PooledResource pr) {
         PooledResourceInfo pri = pr.getPooledResourceInfo();
         Stack group = (Stack)this.groups.get(pri);
         if (group != null && !group.isEmpty()) {
            PooledResource first = (PooledResource)group.peek();
            if (first == pr) {
               group.pop();
               return true;
            } else {
               return group.remove(pr);
            }
         } else {
            return false;
         }
      }

      boolean remove(PooledResource pr) {
         PooledResourceInfo pri = pr.getPooledResourceInfo();
         Stack group = (Stack)this.groups.get(pri);
         return group == null ? false : group.remove(pr);
      }

      void clear() {
         this.groups.clear();
      }

      List getGroupList(PooledResourceInfo pri) {
         return (List)this.groups.get(pri);
      }

      private Stack getOrCreate(PooledResourceInfo pri) {
         Stack group = (Stack)this.groups.get(pri);
         if (group == null) {
            group = new Stack();
            this.groups.put(pri, group);
         }

         return group;
      }

      public String toString() {
         return this.groups.toString();
      }
   }
}
