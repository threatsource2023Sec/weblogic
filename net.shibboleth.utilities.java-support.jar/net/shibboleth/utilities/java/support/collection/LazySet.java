package net.shibboleth.utilities.java.support.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LazySet implements Set, Serializable {
   private static final long serialVersionUID = -1596445680460115174L;
   private Set delegate = Collections.emptySet();

   public boolean add(Object element) {
      if (this.delegate.isEmpty()) {
         this.delegate = Collections.singleton(element);
         return true;
      } else {
         this.delegate = this.createImplementation();
         return this.delegate.add(element);
      }
   }

   public boolean addAll(Collection collection) {
      this.delegate = this.createImplementation();
      return this.delegate.addAll(collection);
   }

   public void clear() {
      this.delegate = Collections.emptySet();
   }

   public boolean contains(Object element) {
      return this.delegate.contains(element);
   }

   public boolean containsAll(Collection collection) {
      return this.delegate.containsAll(collection);
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public Iterator iterator() {
      this.delegate = this.createImplementation();
      return this.delegate.iterator();
   }

   public boolean remove(Object element) {
      this.delegate = this.createImplementation();
      return this.delegate.remove(element);
   }

   public boolean removeAll(Collection collection) {
      this.delegate = this.createImplementation();
      return this.delegate.removeAll(collection);
   }

   public boolean retainAll(Collection collection) {
      this.delegate = this.createImplementation();
      return this.delegate.retainAll(collection);
   }

   public int size() {
      return this.delegate.size();
   }

   public Object[] toArray() {
      return this.delegate.toArray();
   }

   public Object[] toArray(Object[] type) {
      return this.delegate.toArray(type);
   }

   private Set createImplementation() {
      return (Set)(this.delegate instanceof HashSet ? this.delegate : new HashSet(this.delegate));
   }

   public String toString() {
      return this.delegate.toString();
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj != null && this.getClass() == obj.getClass() ? this.delegate.equals(((LazySet)obj).delegate) : false;
      }
   }
}
