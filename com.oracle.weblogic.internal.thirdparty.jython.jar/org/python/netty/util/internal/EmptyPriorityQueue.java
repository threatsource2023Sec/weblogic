package org.python.netty.util.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class EmptyPriorityQueue implements PriorityQueue {
   private static final PriorityQueue INSTANCE = new EmptyPriorityQueue();

   private EmptyPriorityQueue() {
   }

   public static EmptyPriorityQueue instance() {
      return (EmptyPriorityQueue)INSTANCE;
   }

   public boolean removeTyped(Object node) {
      return false;
   }

   public boolean containsTyped(Object node) {
      return false;
   }

   public void priorityChanged(Object node) {
   }

   public int size() {
      return 0;
   }

   public boolean isEmpty() {
      return true;
   }

   public boolean contains(Object o) {
      return false;
   }

   public Iterator iterator() {
      return Collections.emptyList().iterator();
   }

   public Object[] toArray() {
      return EmptyArrays.EMPTY_OBJECTS;
   }

   public Object[] toArray(Object[] a) {
      if (a.length > 0) {
         a[0] = null;
      }

      return a;
   }

   public boolean add(Object t) {
      return false;
   }

   public boolean remove(Object o) {
      return false;
   }

   public boolean containsAll(Collection c) {
      return false;
   }

   public boolean addAll(Collection c) {
      return false;
   }

   public boolean removeAll(Collection c) {
      return false;
   }

   public boolean retainAll(Collection c) {
      return false;
   }

   public void clear() {
   }

   public boolean equals(Object o) {
      return o instanceof PriorityQueue && ((PriorityQueue)o).isEmpty();
   }

   public int hashCode() {
      return 0;
   }

   public boolean offer(Object t) {
      return false;
   }

   public Object remove() {
      throw new NoSuchElementException();
   }

   public Object poll() {
      return null;
   }

   public Object element() {
      throw new NoSuchElementException();
   }

   public Object peek() {
      return null;
   }

   public String toString() {
      return EmptyPriorityQueue.class.getSimpleName();
   }
}
