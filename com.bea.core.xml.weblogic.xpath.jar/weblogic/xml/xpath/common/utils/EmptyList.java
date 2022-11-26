package weblogic.xml.xpath.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import weblogic.xml.xpath.common.iterators.EmptyIterator;

public final class EmptyList implements List {
   private static final List INSTANCE = new EmptyList();
   private Object[] mArray = new Object[0];

   public static List getInstance() {
      return INSTANCE;
   }

   private EmptyList() {
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

   public boolean containsAll(Collection c) {
      return false;
   }

   public Iterator iterator() {
      return EmptyIterator.getInstance();
   }

   public Object[] toArray() {
      return this.mArray;
   }

   public Object[] toArray(Object[] a) {
      try {
         return (Object[])((Object[])Array.newInstance(a.getClass().getComponentType(), 0));
      } catch (NegativeArraySizeException var3) {
         throw new IllegalStateException();
      }
   }

   public boolean equals(Object o) {
      return o instanceof List && ((List)o).size() == 0;
   }

   public int hashCode() {
      return 1;
   }

   public int indexOf(Object o) {
      return -1;
   }

   public int lastIndexOf(Object o) {
      return -1;
   }

   public ListIterator listIterator() {
      return EmptyListIterator.getInstance();
   }

   public ListIterator listIterator(int i) {
      throw new IndexOutOfBoundsException();
   }

   public boolean add(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int i, Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public Object set(int i, Object o) {
      throw new UnsupportedOperationException();
   }

   public void add(int i, Object o) {
      throw new UnsupportedOperationException();
   }

   public Object remove(int i) {
      throw new IndexOutOfBoundsException();
   }

   public Object get(int i) {
      throw new IndexOutOfBoundsException();
   }

   public List subList(int b, int e) {
      throw new IndexOutOfBoundsException();
   }
}
