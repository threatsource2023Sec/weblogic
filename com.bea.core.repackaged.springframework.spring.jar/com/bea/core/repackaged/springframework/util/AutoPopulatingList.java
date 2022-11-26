package com.bea.core.repackaged.springframework.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AutoPopulatingList implements List, Serializable {
   private final List backingList;
   private final ElementFactory elementFactory;

   public AutoPopulatingList(Class elementClass) {
      this(new ArrayList(), (Class)elementClass);
   }

   public AutoPopulatingList(List backingList, Class elementClass) {
      this(backingList, (ElementFactory)(new ReflectiveElementFactory(elementClass)));
   }

   public AutoPopulatingList(ElementFactory elementFactory) {
      this(new ArrayList(), (ElementFactory)elementFactory);
   }

   public AutoPopulatingList(List backingList, ElementFactory elementFactory) {
      Assert.notNull(backingList, (String)"Backing List must not be null");
      Assert.notNull(elementFactory, (String)"Element factory must not be null");
      this.backingList = backingList;
      this.elementFactory = elementFactory;
   }

   public void add(int index, Object element) {
      this.backingList.add(index, element);
   }

   public boolean add(Object o) {
      return this.backingList.add(o);
   }

   public boolean addAll(Collection c) {
      return this.backingList.addAll(c);
   }

   public boolean addAll(int index, Collection c) {
      return this.backingList.addAll(index, c);
   }

   public void clear() {
      this.backingList.clear();
   }

   public boolean contains(Object o) {
      return this.backingList.contains(o);
   }

   public boolean containsAll(Collection c) {
      return this.backingList.containsAll(c);
   }

   public Object get(int index) {
      int backingListSize = this.backingList.size();
      Object element = null;
      if (index < backingListSize) {
         element = this.backingList.get(index);
         if (element == null) {
            element = this.elementFactory.createElement(index);
            this.backingList.set(index, element);
         }
      } else {
         for(int x = backingListSize; x < index; ++x) {
            this.backingList.add((Object)null);
         }

         element = this.elementFactory.createElement(index);
         this.backingList.add(element);
      }

      return element;
   }

   public int indexOf(Object o) {
      return this.backingList.indexOf(o);
   }

   public boolean isEmpty() {
      return this.backingList.isEmpty();
   }

   public Iterator iterator() {
      return this.backingList.iterator();
   }

   public int lastIndexOf(Object o) {
      return this.backingList.lastIndexOf(o);
   }

   public ListIterator listIterator() {
      return this.backingList.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.backingList.listIterator(index);
   }

   public Object remove(int index) {
      return this.backingList.remove(index);
   }

   public boolean remove(Object o) {
      return this.backingList.remove(o);
   }

   public boolean removeAll(Collection c) {
      return this.backingList.removeAll(c);
   }

   public boolean retainAll(Collection c) {
      return this.backingList.retainAll(c);
   }

   public Object set(int index, Object element) {
      return this.backingList.set(index, element);
   }

   public int size() {
      return this.backingList.size();
   }

   public List subList(int fromIndex, int toIndex) {
      return this.backingList.subList(fromIndex, toIndex);
   }

   public Object[] toArray() {
      return this.backingList.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.backingList.toArray(a);
   }

   public boolean equals(Object other) {
      return this.backingList.equals(other);
   }

   public int hashCode() {
      return this.backingList.hashCode();
   }

   private static class ReflectiveElementFactory implements ElementFactory, Serializable {
      private final Class elementClass;

      public ReflectiveElementFactory(Class elementClass) {
         Assert.notNull(elementClass, (String)"Element class must not be null");
         Assert.isTrue(!elementClass.isInterface(), "Element class must not be an interface type");
         Assert.isTrue(!Modifier.isAbstract(elementClass.getModifiers()), "Element class cannot be an abstract class");
         this.elementClass = elementClass;
      }

      public Object createElement(int index) {
         try {
            return ReflectionUtils.accessibleConstructor(this.elementClass).newInstance();
         } catch (NoSuchMethodException var3) {
            throw new ElementInstantiationException("No default constructor on element class: " + this.elementClass.getName(), var3);
         } catch (InstantiationException var4) {
            throw new ElementInstantiationException("Unable to instantiate element class: " + this.elementClass.getName(), var4);
         } catch (IllegalAccessException var5) {
            throw new ElementInstantiationException("Could not access element constructor: " + this.elementClass.getName(), var5);
         } catch (InvocationTargetException var6) {
            throw new ElementInstantiationException("Failed to invoke element constructor: " + this.elementClass.getName(), var6.getTargetException());
         }
      }
   }

   public static class ElementInstantiationException extends RuntimeException {
      public ElementInstantiationException(String msg) {
         super(msg);
      }

      public ElementInstantiationException(String message, Throwable cause) {
         super(message, cause);
      }
   }

   @FunctionalInterface
   public interface ElementFactory {
      Object createElement(int var1) throws ElementInstantiationException;
   }
}
