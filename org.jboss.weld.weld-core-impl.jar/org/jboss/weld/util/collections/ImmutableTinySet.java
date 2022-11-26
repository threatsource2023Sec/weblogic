package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jboss.weld.util.Preconditions;

abstract class ImmutableTinySet extends ImmutableSet {
   static class Tripleton extends ImmutableTinySet implements Serializable {
      private static final long serialVersionUID = 1L;
      private final Object element1;
      private final Object element2;
      private final Object element3;

      Tripleton(Set set) {
         Preconditions.checkNotNull(set);
         Preconditions.checkArgument(set.size() == 3, (Object)set);
         Iterator iterator = set.iterator();
         this.element1 = iterator.next();
         this.element2 = iterator.next();
         this.element3 = iterator.next();
      }

      public int size() {
         return 3;
      }

      public boolean contains(Object o) {
         if (o == null) {
            return false;
         } else {
            return o.equals(this.element1) || o.equals(this.element2) || o.equals(this.element3);
         }
      }

      public int hashCode() {
         return this.element1.hashCode() + this.element2.hashCode() + this.element3.hashCode();
      }

      public Iterator iterator() {
         return new Iterators.IndexIterator(this.size()) {
            Object getElement(int position) {
               switch (position) {
                  case 0:
                     return Tripleton.this.element1;
                  case 1:
                     return Tripleton.this.element2;
                  case 2:
                     return Tripleton.this.element3;
                  default:
                     throw new NoSuchElementException();
               }
            }
         };
      }
   }

   static class Doubleton extends ImmutableTinySet implements Serializable {
      private static final long serialVersionUID = 1L;
      private final Object element1;
      private final Object element2;

      Doubleton(Set set) {
         Preconditions.checkNotNull(set);
         Preconditions.checkArgument(set.size() == 2, (Object)set);
         Iterator iterator = set.iterator();
         this.element1 = iterator.next();
         this.element2 = iterator.next();
      }

      public int size() {
         return 2;
      }

      public boolean contains(Object o) {
         if (o == null) {
            return false;
         } else {
            return o.equals(this.element1) || o.equals(this.element2);
         }
      }

      public int hashCode() {
         return this.element1.hashCode() + this.element2.hashCode();
      }

      public Iterator iterator() {
         return new Iterators.IndexIterator(this.size()) {
            Object getElement(int position) {
               switch (position) {
                  case 0:
                     return Doubleton.this.element1;
                  case 1:
                     return Doubleton.this.element2;
                  default:
                     throw new NoSuchElementException();
               }
            }
         };
      }
   }

   static class Singleton extends ImmutableTinySet implements Serializable {
      private static final long serialVersionUID = 1L;
      private final Object element;

      Singleton(Set set) {
         Preconditions.checkNotNull(set);
         Preconditions.checkArgument(set.size() == 1, (Object)set);
         this.element = set.iterator().next();
      }

      Singleton(Object element) {
         Preconditions.checkNotNull(element);
         this.element = element;
      }

      public int size() {
         return 1;
      }

      public boolean contains(Object o) {
         return o == null ? false : o.equals(this.element);
      }

      public int hashCode() {
         return this.element.hashCode();
      }

      public Iterator iterator() {
         return new Iterators.IndexIterator(this.size()) {
            Object getElement(int position) {
               return Singleton.this.element;
            }
         };
      }
   }
}
