package org.python.netty.handler.codec;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.python.netty.util.internal.ObjectUtil;

public final class HeadersUtils {
   private HeadersUtils() {
   }

   public static List getAllAsString(Headers headers, Object name) {
      final List allNames = headers.getAll(name);
      return new AbstractList() {
         public String get(int index) {
            Object value = allNames.get(index);
            return value != null ? value.toString() : null;
         }

         public int size() {
            return allNames.size();
         }
      };
   }

   public static String getAsString(Headers headers, Object name) {
      Object orig = headers.get(name);
      return orig != null ? orig.toString() : null;
   }

   public static Iterator iteratorAsString(Iterable headers) {
      return new StringEntryIterator(headers.iterator());
   }

   public static Set namesAsString(Headers headers) {
      return new CharSequenceDelegatingStringSet(headers.names());
   }

   private abstract static class DelegatingStringSet implements Set {
      protected final Set allNames;

      public DelegatingStringSet(Set allNames) {
         this.allNames = (Set)ObjectUtil.checkNotNull(allNames, "allNames");
      }

      public int size() {
         return this.allNames.size();
      }

      public boolean isEmpty() {
         return this.allNames.isEmpty();
      }

      public boolean contains(Object o) {
         return this.allNames.contains(o.toString());
      }

      public Iterator iterator() {
         return new StringIterator(this.allNames.iterator());
      }

      public Object[] toArray() {
         Object[] arr = new Object[this.size()];
         this.fillArray(arr);
         return arr;
      }

      public Object[] toArray(Object[] a) {
         if (a != null && a.length >= this.size()) {
            this.fillArray(a);
            return a;
         } else {
            Object[] arr = (Object[])(new Object[this.size()]);
            this.fillArray(arr);
            return arr;
         }
      }

      private void fillArray(Object[] arr) {
         Iterator itr = this.allNames.iterator();

         for(int i = 0; i < this.size(); ++i) {
            arr[i] = itr.next();
         }

      }

      public boolean remove(Object o) {
         return this.allNames.remove(o);
      }

      public boolean containsAll(Collection c) {
         Iterator var2 = c.iterator();

         Object o;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            o = var2.next();
         } while(this.contains(o));

         return false;
      }

      public boolean removeAll(Collection c) {
         boolean modified = false;
         Iterator var3 = c.iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            if (this.remove(o)) {
               modified = true;
            }
         }

         return modified;
      }

      public boolean retainAll(Collection c) {
         boolean modified = false;
         Iterator it = this.iterator();

         while(it.hasNext()) {
            if (!c.contains(it.next())) {
               it.remove();
               modified = true;
            }
         }

         return modified;
      }

      public void clear() {
         this.allNames.clear();
      }
   }

   private static final class CharSequenceDelegatingStringSet extends DelegatingStringSet {
      public CharSequenceDelegatingStringSet(Set allNames) {
         super(allNames);
      }

      public boolean add(String e) {
         return this.allNames.add(e);
      }

      public boolean addAll(Collection c) {
         return this.allNames.addAll(c);
      }
   }

   private static final class StringIterator implements Iterator {
      private final Iterator iter;

      public StringIterator(Iterator iter) {
         this.iter = iter;
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public String next() {
         Object next = this.iter.next();
         return next != null ? next.toString() : null;
      }

      public void remove() {
         this.iter.remove();
      }
   }

   private static final class StringEntry implements Map.Entry {
      private final Map.Entry entry;
      private String name;
      private String value;

      StringEntry(Map.Entry entry) {
         this.entry = entry;
      }

      public String getKey() {
         if (this.name == null) {
            this.name = ((CharSequence)this.entry.getKey()).toString();
         }

         return this.name;
      }

      public String getValue() {
         if (this.value == null && this.entry.getValue() != null) {
            this.value = ((CharSequence)this.entry.getValue()).toString();
         }

         return this.value;
      }

      public String setValue(String value) {
         String old = this.getValue();
         this.entry.setValue(value);
         return old;
      }

      public String toString() {
         return this.entry.toString();
      }
   }

   private static final class StringEntryIterator implements Iterator {
      private final Iterator iter;

      public StringEntryIterator(Iterator iter) {
         this.iter = iter;
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public Map.Entry next() {
         return new StringEntry((Map.Entry)this.iter.next());
      }

      public void remove() {
         this.iter.remove();
      }
   }
}
