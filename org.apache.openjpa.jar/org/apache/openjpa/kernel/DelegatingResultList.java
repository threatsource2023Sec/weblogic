package org.apache.openjpa.kernel;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingResultList implements ResultList {
   private final ResultList _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingResultList(ResultList list) {
      this(list, (RuntimeExceptionTranslator)null);
   }

   public DelegatingResultList(ResultList list, RuntimeExceptionTranslator trans) {
      this._del = list;
      this._trans = trans;
   }

   public ResultList getDelegate() {
      return this._del;
   }

   public ResultList getInnermostDelegate() {
      return this._del instanceof DelegatingResultList ? ((DelegatingResultList)this._del).getInnermostDelegate() : this._del;
   }

   public Object writeReplace() throws ObjectStreamException {
      return this._del;
   }

   public int hashCode() {
      try {
         return this.getInnermostDelegate().hashCode();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingResultList) {
            other = ((DelegatingResultList)other).getInnermostDelegate();
         }

         try {
            return this.getInnermostDelegate().equals(other);
         } catch (RuntimeException var3) {
            throw this.translate(var3);
         }
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public boolean isProviderOpen() {
      try {
         return this._del.isProviderOpen();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void close() {
      try {
         this._del.close();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isClosed() {
      try {
         return this._del.isClosed();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int size() {
      try {
         return this._del.size();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isEmpty() {
      try {
         return this._del.isEmpty();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean contains(Object o) {
      try {
         return this._del.contains(o);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Iterator iterator() {
      return this.listIterator();
   }

   public Object[] toArray() {
      try {
         return this._del.toArray();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object[] toArray(Object[] a) {
      try {
         return this._del.toArray(a);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean add(Object o) {
      try {
         return this._del.add(o);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean remove(Object o) {
      try {
         return this._del.remove(o);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean containsAll(Collection c) {
      try {
         return this._del.containsAll(c);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean addAll(Collection c) {
      try {
         return this._del.addAll(c);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean addAll(int index, Collection c) {
      try {
         return this._del.addAll(index, c);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean removeAll(Collection c) {
      try {
         return this._del.removeAll(c);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean retainAll(Collection c) {
      try {
         return this._del.retainAll(c);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void clear() {
      try {
         this._del.clear();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object get(int index) {
      try {
         return this._del.get(index);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object set(int index, Object element) {
      try {
         return this._del.set(index, element);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void add(int index, Object element) {
      try {
         this._del.add(index, element);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object remove(int index) {
      try {
         return this._del.remove(index);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int indexOf(Object o) {
      try {
         return this._del.indexOf(o);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int lastIndexOf(Object o) {
      try {
         return this._del.lastIndexOf(o);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public ListIterator listIterator() {
      try {
         return new DelegatingListIterator(this._del.listIterator());
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public ListIterator listIterator(int index) {
      try {
         return new DelegatingListIterator(this._del.listIterator(index));
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public List subList(int fromIndex, int toIndex) {
      try {
         return this._del.subList(fromIndex, toIndex);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public class DelegatingListIterator implements ListIterator {
      private final ListIterator _del;

      public DelegatingListIterator(ListIterator it) {
         this._del = it;
      }

      public ListIterator getDelegate() {
         return this._del;
      }

      public ListIterator getInnermostDelegate() {
         return this._del instanceof DelegatingListIterator ? ((DelegatingListIterator)this._del).getInnermostDelegate() : this._del;
      }

      public int hashCode() {
         try {
            return this.getInnermostDelegate().hashCode();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            if (other instanceof DelegatingListIterator) {
               other = ((DelegatingListIterator)other).getInnermostDelegate();
            }

            try {
               return this.getInnermostDelegate().equals(other);
            } catch (RuntimeException var3) {
               throw DelegatingResultList.this.translate(var3);
            }
         }
      }

      public boolean hasNext() {
         try {
            return this._del.hasNext();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public Object next() {
         try {
            return this._del.next();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public boolean hasPrevious() {
         try {
            return this._del.hasPrevious();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public Object previous() {
         try {
            return this._del.previous();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public int nextIndex() {
         try {
            return this._del.nextIndex();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public int previousIndex() {
         try {
            return this._del.previousIndex();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public void remove() {
         try {
            this._del.remove();
         } catch (RuntimeException var2) {
            throw DelegatingResultList.this.translate(var2);
         }
      }

      public void set(Object o) {
         try {
            this._del.set(o);
         } catch (RuntimeException var3) {
            throw DelegatingResultList.this.translate(var3);
         }
      }

      public void add(Object o) {
         try {
            this._del.add(o);
         } catch (RuntimeException var3) {
            throw DelegatingResultList.this.translate(var3);
         }
      }
   }
}
