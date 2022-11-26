package weblogic.apache.xerces.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import weblogic.apache.xerces.xs.XSObject;
import weblogic.apache.xerces.xs.XSObjectList;

public class XSObjectListImpl extends AbstractList implements XSObjectList {
   public static final XSObjectListImpl EMPTY_LIST = new XSObjectListImpl(new XSObject[0], 0);
   private static final ListIterator EMPTY_ITERATOR = new ListIterator() {
      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public boolean hasPrevious() {
         return false;
      }

      public Object previous() {
         throw new NoSuchElementException();
      }

      public int nextIndex() {
         return 0;
      }

      public int previousIndex() {
         return -1;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void set(Object var1) {
         throw new UnsupportedOperationException();
      }

      public void add(Object var1) {
         throw new UnsupportedOperationException();
      }
   };
   private static final int DEFAULT_SIZE = 4;
   private XSObject[] fArray = null;
   private int fLength = 0;

   public XSObjectListImpl() {
      this.fArray = new XSObject[4];
      this.fLength = 0;
   }

   public XSObjectListImpl(XSObject[] var1, int var2) {
      this.fArray = var1;
      this.fLength = var2;
   }

   public int getLength() {
      return this.fLength;
   }

   public XSObject item(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.fArray[var1] : null;
   }

   public void clearXSObjectList() {
      for(int var1 = 0; var1 < this.fLength; ++var1) {
         this.fArray[var1] = null;
      }

      this.fArray = null;
      this.fLength = 0;
   }

   public void addXSObject(XSObject var1) {
      if (this.fLength == this.fArray.length) {
         XSObject[] var2 = new XSObject[this.fLength + 4];
         System.arraycopy(this.fArray, 0, var2, 0, this.fLength);
         this.fArray = var2;
      }

      this.fArray[this.fLength++] = var1;
   }

   public void addXSObject(int var1, XSObject var2) {
      this.fArray[var1] = var2;
   }

   public boolean contains(Object var1) {
      return var1 == null ? this.containsNull() : this.containsObject(var1);
   }

   public Object get(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         return this.fArray[var1];
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   public int size() {
      return this.getLength();
   }

   public Iterator iterator() {
      return this.listIterator0(0);
   }

   public ListIterator listIterator() {
      return this.listIterator0(0);
   }

   public ListIterator listIterator(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         return this.listIterator0(var1);
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   private ListIterator listIterator0(int var1) {
      return (ListIterator)(this.fLength == 0 ? EMPTY_ITERATOR : new XSObjectListIterator(var1));
   }

   private boolean containsObject(Object var1) {
      for(int var2 = this.fLength - 1; var2 >= 0; --var2) {
         if (var1.equals(this.fArray[var2])) {
            return true;
         }
      }

      return false;
   }

   private boolean containsNull() {
      for(int var1 = this.fLength - 1; var1 >= 0; --var1) {
         if (this.fArray[var1] == null) {
            return true;
         }
      }

      return false;
   }

   public Object[] toArray() {
      Object[] var1 = new Object[this.fLength];
      this.toArray0(var1);
      return var1;
   }

   public Object[] toArray(Object[] var1) {
      if (var1.length < this.fLength) {
         Class var2 = var1.getClass();
         Class var3 = var2.getComponentType();
         var1 = (Object[])Array.newInstance(var3, this.fLength);
      }

      this.toArray0(var1);
      if (var1.length > this.fLength) {
         var1[this.fLength] = null;
      }

      return var1;
   }

   private void toArray0(Object[] var1) {
      if (this.fLength > 0) {
         System.arraycopy(this.fArray, 0, var1, 0, this.fLength);
      }

   }

   private final class XSObjectListIterator implements ListIterator {
      private int index;

      public XSObjectListIterator(int var2) {
         this.index = var2;
      }

      public boolean hasNext() {
         return this.index < XSObjectListImpl.this.fLength;
      }

      public Object next() {
         if (this.index < XSObjectListImpl.this.fLength) {
            return XSObjectListImpl.this.fArray[this.index++];
         } else {
            throw new NoSuchElementException();
         }
      }

      public boolean hasPrevious() {
         return this.index > 0;
      }

      public Object previous() {
         if (this.index > 0) {
            return XSObjectListImpl.this.fArray[--this.index];
         } else {
            throw new NoSuchElementException();
         }
      }

      public int nextIndex() {
         return this.index;
      }

      public int previousIndex() {
         return this.index - 1;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void set(Object var1) {
         throw new UnsupportedOperationException();
      }

      public void add(Object var1) {
         throw new UnsupportedOperationException();
      }
   }
}
