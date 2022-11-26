package org.glassfish.grizzly.filterchain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ListFacadeFilterChain extends AbstractFilterChain {
   protected final List filters;

   public ListFacadeFilterChain(List filtersImpl) {
      this.filters = filtersImpl;
   }

   public boolean add(Filter filter) {
      if (this.filters.add(filter)) {
         filter.onAdded(this);
         this.notifyChangedExcept(filter);
         return true;
      } else {
         return false;
      }
   }

   public void add(int index, Filter filter) {
      this.filters.add(index, filter);
      filter.onAdded(this);
      this.notifyChangedExcept(filter);
   }

   public boolean addAll(Collection c) {
      Iterator var2 = c.iterator();

      while(var2.hasNext()) {
         Filter filter = (Filter)var2.next();
         this.filters.add(filter);
         filter.onAdded(this);
      }

      this.notifyChangedExcept((Filter)null);
      return true;
   }

   public boolean addAll(int index, Collection c) {
      int i = 0;
      Iterator var4 = c.iterator();

      while(var4.hasNext()) {
         Filter filter = (Filter)var4.next();
         this.filters.add(index + i++, filter);
         filter.onAdded(this);
      }

      this.notifyChangedExcept((Filter)null);
      return true;
   }

   public Filter set(int index, Filter filter) {
      Filter oldFilter = (Filter)this.filters.set(index, filter);
      if (oldFilter != filter) {
         if (oldFilter != null) {
            oldFilter.onRemoved(this);
         }

         filter.onAdded(this);
         this.notifyChangedExcept(filter);
      }

      return oldFilter;
   }

   public final Filter get(int index) {
      return (Filter)this.filters.get(index);
   }

   public int indexOf(Object object) {
      return this.filters.indexOf(object);
   }

   public int lastIndexOf(Object filter) {
      return this.filters.lastIndexOf(filter);
   }

   public boolean contains(Object filter) {
      return this.filters.contains(filter);
   }

   public boolean containsAll(Collection c) {
      return this.filters.containsAll(c);
   }

   public Object[] toArray() {
      return this.filters.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.filters.toArray(a);
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object object) {
      Filter filter = (Filter)object;
      if (this.filters.remove(filter)) {
         filter.onRemoved(this);
         this.notifyChangedExcept(filter);
         return true;
      } else {
         return false;
      }
   }

   public Filter remove(int index) {
      Filter filter = (Filter)this.filters.remove(index);
      if (filter != null) {
         filter.onRemoved(this);
         this.notifyChangedExcept(filter);
         return filter;
      } else {
         return null;
      }
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      return this.filters == null || this.filters.isEmpty();
   }

   public int size() {
      return this.filters.size();
   }

   public void clear() {
      Object[] localFilters = this.filters.toArray();
      this.filters.clear();
      Object[] var2 = localFilters;
      int var3 = localFilters.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object filter = var2[var4];
         ((Filter)filter).onRemoved(this);
      }

   }

   public Iterator iterator() {
      return this.filters.iterator();
   }

   public ListIterator listIterator() {
      return this.filters.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.filters.listIterator(index);
   }

   protected void notifyChangedExcept(Filter filter) {
      Iterator var2 = this.filters.iterator();

      while(var2.hasNext()) {
         Filter currentFilter = (Filter)var2.next();
         if (currentFilter != filter) {
            currentFilter.onFilterChainChanged(this);
         }
      }

   }
}
