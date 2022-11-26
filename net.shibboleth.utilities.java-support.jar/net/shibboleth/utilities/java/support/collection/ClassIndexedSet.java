package net.shibboleth.utilities.java.support.collection;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class ClassIndexedSet extends AbstractSet implements Set {
   private final HashSet set = new HashSet();
   private final HashMap index = new HashMap();

   public boolean add(@Nonnull Object o) {
      return this.add(o, false);
   }

   public boolean add(@Nonnull Object o, boolean replace) {
      Constraint.isNotNull(o, "Null elements are not allowed");
      boolean replacing = false;
      Class indexClass = this.getIndexClass(o);
      Object existing = this.get(indexClass);
      if (existing != null) {
         replacing = true;
         if (!replace) {
            throw new IllegalArgumentException("Set already contains a member of index class " + indexClass.getName());
         }

         this.remove(existing);
      }

      this.index.put(indexClass, o);
      this.set.add(o);
      return replacing;
   }

   public void clear() {
      this.set.clear();
      this.index.clear();
   }

   public boolean remove(@Nullable Object o) {
      if (o != null && this.set.contains(o)) {
         this.removeFromIndex(o);
         this.set.remove(o);
         return true;
      } else {
         return false;
      }
   }

   @Nonnull
   public Iterator iterator() {
      return new ClassIndexedSetIterator(this, this.set.iterator());
   }

   public int size() {
      return this.set.size();
   }

   public boolean contains(@Nullable Class clazz) {
      return this.get(clazz) != null;
   }

   @Nullable
   public Object get(@Nullable Class clazz) {
      return this.index.get(clazz);
   }

   @Nonnull
   protected Class getIndexClass(@Nonnull Object o) {
      Constraint.isNotNull(o, "Object can not be null");
      return o.getClass();
   }

   private void removeFromIndex(Object o) {
      this.index.remove(this.getIndexClass(o));
   }

   public String toString() {
      return this.set.toString();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj != null && this.getClass() == obj.getClass() ? this.set.equals(((ClassIndexedSet)obj).set) : false;
      }
   }

   public int hashCode() {
      return this.set.hashCode();
   }

   protected class ClassIndexedSetIterator implements Iterator {
      private final ClassIndexedSet set;
      private final Iterator iterator;
      private boolean nextCalled;
      private boolean removeStateValid;
      private Object current;

      protected ClassIndexedSetIterator(ClassIndexedSet parentSet, Iterator parentIterator) {
         this.set = parentSet;
         this.iterator = parentIterator;
         this.current = null;
         this.nextCalled = false;
         this.removeStateValid = false;
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public Object next() {
         this.current = this.iterator.next();
         this.nextCalled = true;
         this.removeStateValid = true;
         return this.current;
      }

      public void remove() {
         if (!this.nextCalled) {
            throw new IllegalStateException("remove() was called before calling next()");
         } else if (!this.removeStateValid) {
            throw new IllegalStateException("remove() has already been called since the last call to next()");
         } else {
            this.iterator.remove();
            this.set.removeFromIndex(this.current);
            this.removeStateValid = false;
         }
      }
   }
}
