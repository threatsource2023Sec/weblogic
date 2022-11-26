package com.bea.xml.stream.util;

import java.util.AbstractCollection;
import java.util.EmptyStackException;
import java.util.Iterator;

public final class Stack extends AbstractCollection {
   private Object[] values;
   private int pointer;

   public Stack() {
      this(15);
   }

   public Stack(int size) {
      if (size < 0) {
         throw new IllegalArgumentException();
      } else {
         this.values = new Object[size];
         this.pointer = 0;
      }
   }

   private Stack(Object[] values, int pointer) {
      this.values = values;
      this.pointer = pointer;
   }

   private void resize() {
      if (this.pointer == 0) {
         this.values = new Object[1];
      } else {
         Object[] o = new Object[this.pointer * 2];
         System.arraycopy(this.values, 0, o, 0, this.pointer);
         this.values = o;
      }
   }

   public boolean add(Object o) {
      this.push(o);
      return true;
   }

   public void clear() {
      for(Object[] v = this.values; this.pointer > 0; v[--this.pointer] = null) {
      }

   }

   public boolean isEmpty() {
      return this.pointer == 0;
   }

   public Iterator iterator() {
      Object[] o = new Object[this.pointer];
      System.arraycopy(this.values, 0, o, 0, this.pointer);
      return new ArrayIterator(o);
   }

   public Object clone() {
      Object[] newValues = new Object[this.pointer];
      System.arraycopy(this.values, 0, newValues, 0, this.pointer);
      return new Stack(newValues, this.pointer);
   }

   public int size() {
      return this.pointer;
   }

   public void push(Object o) {
      if (this.pointer == this.values.length) {
         this.resize();
      }

      this.values[this.pointer++] = o;
   }

   public Object pop() {
      try {
         Object o = this.values[--this.pointer];
         this.values[this.pointer] = null;
         return o;
      } catch (ArrayIndexOutOfBoundsException var2) {
         if (this.pointer < 0) {
            this.pointer = 0;
         }

         throw new EmptyStackException();
      }
   }

   public Object peek() {
      try {
         return this.values[this.pointer - 1];
      } catch (ArrayIndexOutOfBoundsException var2) {
         throw new EmptyStackException();
      }
   }
}
