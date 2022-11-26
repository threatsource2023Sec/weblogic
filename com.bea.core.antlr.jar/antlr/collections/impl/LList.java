package antlr.collections.impl;

import antlr.collections.List;
import antlr.collections.Stack;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class LList implements List, Stack {
   protected LLCell head = null;
   protected LLCell tail = null;
   protected int length = 0;

   public void add(Object var1) {
      this.append(var1);
   }

   public void append(Object var1) {
      LLCell var2 = new LLCell(var1);
      if (this.length == 0) {
         this.head = this.tail = var2;
         this.length = 1;
      } else {
         this.tail.next = var2;
         this.tail = var2;
         ++this.length;
      }

   }

   protected Object deleteHead() throws NoSuchElementException {
      if (this.head == null) {
         throw new NoSuchElementException();
      } else {
         Object var1 = this.head.data;
         this.head = this.head.next;
         --this.length;
         return var1;
      }
   }

   public Object elementAt(int var1) throws NoSuchElementException {
      int var2 = 0;

      for(LLCell var3 = this.head; var3 != null; var3 = var3.next) {
         if (var1 == var2) {
            return var3.data;
         }

         ++var2;
      }

      throw new NoSuchElementException();
   }

   public Enumeration elements() {
      return new LLEnumeration(this);
   }

   public int height() {
      return this.length;
   }

   public boolean includes(Object var1) {
      for(LLCell var2 = this.head; var2 != null; var2 = var2.next) {
         if (var2.data.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   protected void insertHead(Object var1) {
      LLCell var2 = this.head;
      this.head = new LLCell(var1);
      this.head.next = var2;
      ++this.length;
      if (this.tail == null) {
         this.tail = this.head;
      }

   }

   public int length() {
      return this.length;
   }

   public Object pop() throws NoSuchElementException {
      Object var1 = this.deleteHead();
      return var1;
   }

   public void push(Object var1) {
      this.insertHead(var1);
   }

   public Object top() throws NoSuchElementException {
      if (this.head == null) {
         throw new NoSuchElementException();
      } else {
         return this.head.data;
      }
   }
}
