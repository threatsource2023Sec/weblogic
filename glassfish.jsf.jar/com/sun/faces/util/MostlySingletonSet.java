package com.sun.faces.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MostlySingletonSet implements Set, Serializable {
   private static final long serialVersionUID = 2818326518724772145L;
   private Set inner;

   public boolean add(Object e) {
      boolean result = true;
      if (null == this.inner) {
         this.inner = Collections.singleton(e);
      } else {
         if (1 == this.inner.size()) {
            HashSet newSet = new HashSet();
            newSet.add(this.inner.iterator().next());
            this.inner = newSet;
         }

         result = this.inner.add(e);
      }

      return result;
   }

   public boolean addAll(Collection c) {
      boolean result = true;
      if (null == this.inner && 1 == c.size()) {
         this.inner = Collections.singleton(c.iterator().next());
      } else {
         if (1 == this.inner.size()) {
            HashSet newSet = new HashSet();
            newSet.add(this.inner.iterator().next());
            this.inner = newSet;
         }

         result = this.inner.addAll(c);
      }

      return result;
   }

   public void clear() {
      if (null != this.inner) {
         if (1 < this.inner.size()) {
            this.inner.clear();
         }

         this.inner = null;
      }

   }

   public boolean remove(Object o) {
      boolean didRemove = false;
      if (null != this.inner) {
         if (1 == this.inner.size()) {
            Object e = this.inner.iterator().next();
            if (null != e && null != o) {
               didRemove = e.equals(o);
            } else {
               didRemove = null == o;
            }

            if (didRemove) {
               this.inner = null;
            }
         } else {
            didRemove = this.inner.remove(o);
            if (didRemove && 1 == this.inner.size()) {
               Set newInner = Collections.singleton(this.inner.iterator().next());
               this.inner.clear();
               this.inner = newInner;
            }
         }
      }

      return didRemove;
   }

   public boolean removeAll(Collection c) {
      boolean result = false;
      if (null != this.inner) {
         if (1 == this.inner.size()) {
            Iterator incomingIter = c.iterator();
            Object oneAndOnlyElement = this.inner.iterator().next();

            while(incomingIter.hasNext()) {
               Object cur = incomingIter.next();
               if (null != oneAndOnlyElement) {
                  if (result = oneAndOnlyElement.equals(cur)) {
                     break;
                  }
               } else if (result = cur == null) {
                  break;
               }
            }

            if (result) {
               this.inner = null;
            }
         } else {
            result = this.inner.removeAll(c);
            if (result && 0 == this.inner.size()) {
               this.inner = null;
            }
         }
      }

      return result;
   }

   public boolean retainAll(Collection c) {
      boolean didModify = false;
      if (null != this.inner) {
         if (1 == this.inner.size()) {
            Iterator incomingIter = c.iterator();
            Object oneAndOnlyElement = this.inner.iterator().next();
            boolean found = false;

            while(incomingIter.hasNext()) {
               Object cur = incomingIter.next();
               if (null != oneAndOnlyElement) {
                  if (found = oneAndOnlyElement.equals(cur)) {
                     break;
                  }
               } else if (found = cur == null) {
                  break;
               }
            }

            if (didModify = !found) {
               this.inner = null;
            }
         } else {
            didModify = this.inner.retainAll(c);
         }
      }

      return didModify;
   }

   public boolean contains(Object o) {
      boolean result = false;
      if (null != this.inner) {
         result = this.inner.contains(o);
      }

      return result;
   }

   public boolean containsAll(Collection c) {
      boolean result = false;
      if (null != this.inner) {
         result = this.inner.containsAll(c);
      }

      return result;
   }

   public boolean isEmpty() {
      boolean result = true;
      if (null != this.inner) {
         result = this.inner.isEmpty();
      }

      return result;
   }

   public int size() {
      int size = 0;
      if (null != this.inner) {
         size = this.inner.size();
      }

      return size;
   }

   public boolean equals(Object obj) {
      boolean result = false;
      if (obj != null) {
         if (obj instanceof MostlySingletonSet) {
            MostlySingletonSet other = (MostlySingletonSet)obj;
            if (this.inner == other.inner || this.inner != null && this.inner.equals(other.inner)) {
               result = true;
            } else {
               result = false;
            }
         } else if (obj instanceof Collection) {
            Collection otherCollection = (Collection)obj;
            if (null != this.inner) {
               result = this.inner.equals(otherCollection);
            } else {
               result = otherCollection.isEmpty();
            }
         }
      }

      return result;
   }

   public int hashCode() {
      int hash = 5;
      hash = 83 * hash + (this.inner != null ? this.inner.hashCode() : 0);
      return hash;
   }

   public String toString() {
      String result = "empty";
      if (null != this.inner) {
         result = this.inner.toString();
      }

      return result;
   }

   public Iterator iterator() {
      Iterator result;
      if (null != this.inner) {
         result = this.inner.iterator();
      } else {
         result = Collections.EMPTY_SET.iterator();
      }

      return result;
   }

   public Object[] toArray() {
      Object[] result = null;
      if (null != this.inner) {
         result = this.inner.toArray();
      }

      return result;
   }

   public Object[] toArray(Object[] a) {
      Object[] result = null;
      if (null != this.inner) {
         result = this.inner.toArray(a);
      }

      return result;
   }
}
