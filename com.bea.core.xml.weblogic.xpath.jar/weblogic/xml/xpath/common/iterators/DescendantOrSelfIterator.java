package weblogic.xml.xpath.common.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import weblogic.xml.xpath.common.Interrogator;

public final class DescendantOrSelfIterator implements Iterator {
   private static final boolean DEBUG = false;
   private Interrogator mInterrogator;
   private LinkedList mIteratorStack;
   private Object mCurrent;

   public DescendantOrSelfIterator(Interrogator i, Object node) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else if (node == null) {
         throw new IllegalArgumentException("null node");
      } else {
         this.mInterrogator = i;
         this.mCurrent = node;
         this.mIteratorStack = new LinkedList();
         this.mCurrent = node;
         this.push(i.getChildren(node));
      }
   }

   private void push(Iterator i) {
      this.mIteratorStack.addLast(i);
   }

   private Iterator pop() {
      return (Iterator)this.mIteratorStack.removeLast();
   }

   private Iterator peek() {
      return (Iterator)this.mIteratorStack.getLast();
   }

   public boolean hasNext() {
      return this.mCurrent != null;
   }

   public Object next() {
      if (this.mCurrent == null) {
         throw new NoSuchElementException();
      } else {
         Object saveCurrent = this.mCurrent;

         Iterator i;
         for(i = this.peek(); !i.hasNext(); i = this.peek()) {
            this.pop();
            if (this.mIteratorStack.size() == 0) {
               this.mCurrent = null;
               return saveCurrent;
            }
         }

         this.mCurrent = i.next();
         Iterator children = this.mInterrogator.getChildren(this.mCurrent);
         this.push(children);
         return saveCurrent;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   private void dump(Object node) {
      System.out.println("[ppp] " + node);
      Iterator c = this.mInterrogator.getChildren(node);

      while(c.hasNext()) {
         System.out.println("[xxx] " + c.next());
      }

   }
}
