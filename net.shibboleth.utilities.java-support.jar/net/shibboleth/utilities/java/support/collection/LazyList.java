package net.shibboleth.utilities.java.support.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LazyList implements List, Serializable {
   private static final long serialVersionUID = -7741904523916701817L;
   private List delegate = Collections.emptyList();

   public boolean add(Object item) {
      if (this.delegate.isEmpty()) {
         this.delegate = Collections.singletonList(item);
         return true;
      } else {
         this.delegate = this.buildList();
         return this.delegate.add(item);
      }
   }

   public void add(int index, Object element) {
      this.delegate = this.buildList();
      this.delegate.add(index, element);
   }

   public boolean addAll(Collection collection) {
      this.delegate = this.buildList();
      return this.delegate.addAll(collection);
   }

   public boolean addAll(int index, Collection collection) {
      this.delegate = this.buildList();
      return this.delegate.addAll(index, collection);
   }

   public void clear() {
      this.delegate = Collections.emptyList();
   }

   public boolean contains(Object element) {
      return this.delegate.contains(element);
   }

   public boolean containsAll(Collection collections) {
      return this.delegate.containsAll(collections);
   }

   public Object get(int index) {
      return this.delegate.get(index);
   }

   public int indexOf(Object element) {
      return this.delegate.indexOf(element);
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public Iterator iterator() {
      this.delegate = this.buildList();
      return this.delegate.iterator();
   }

   public int lastIndexOf(Object element) {
      return this.delegate.lastIndexOf(element);
   }

   public ListIterator listIterator() {
      this.delegate = this.buildList();
      return this.delegate.listIterator();
   }

   public ListIterator listIterator(int index) {
      this.delegate = this.buildList();
      return this.delegate.listIterator(index);
   }

   public boolean remove(Object element) {
      this.delegate = this.buildList();
      return this.delegate.remove(element);
   }

   public Object remove(int index) {
      this.delegate = this.buildList();
      return this.delegate.remove(index);
   }

   public boolean removeAll(Collection collection) {
      this.delegate = this.buildList();
      return this.delegate.removeAll(collection);
   }

   public boolean retainAll(Collection collection) {
      this.delegate = this.buildList();
      return this.delegate.retainAll(collection);
   }

   public Object set(int index, Object element) {
      this.delegate = this.buildList();
      return this.delegate.set(index, element);
   }

   public int size() {
      return this.delegate.size();
   }

   public List subList(int fromIndex, int toIndex) {
      return this.delegate.subList(fromIndex, toIndex);
   }

   public Object[] toArray() {
      return this.delegate.toArray();
   }

   public Object[] toArray(Object[] type) {
      return this.delegate.toArray(type);
   }

   protected List buildList() {
      return (List)(this.delegate instanceof ArrayList ? this.delegate : new ArrayList(this.delegate));
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
         return obj != null && this.getClass() == obj.getClass() ? this.delegate.equals(((LazyList)obj).delegate) : false;
      }
   }
}
