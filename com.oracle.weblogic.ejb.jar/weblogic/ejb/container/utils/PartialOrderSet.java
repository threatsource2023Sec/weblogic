package weblogic.ejb.container.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public class PartialOrderSet implements Set {
   public static final int ORDER_BY_TIME = 100;
   public static final int ORDER_BY_VALUE = 101;
   private static final Object PRESENT = new Object();
   private int ordering = 100;
   private boolean initialized = false;
   private boolean isComparable = false;
   private Map contents = null;
   private Node first = null;
   private Node last = null;
   private int size = 0;

   public PartialOrderSet() {
   }

   public PartialOrderSet(int ordering) {
      if (ordering != 100 && ordering != 101) {
         throw new IllegalArgumentException("Illegal ordering specified for " + this.getClass().getName());
      } else {
         this.ordering = ordering;
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public boolean contains(Object o) {
      if (o == null) {
         return false;
      } else {
         if (!this.initialized) {
            this.initialize(o);
         }

         if (!this.isComparable && this.ordering != 100) {
            Integer key = new Integer(o.hashCode());

            for(Node node = (Node)this.contents.get(key); node != null; node = node.getNext()) {
               if (node.getValue().equals(o)) {
                  return true;
               }
            }

            return false;
         } else {
            return this.contents.get(o) != null;
         }
      }
   }

   public Iterator iterator() {
      return new PartialOrderIterator();
   }

   public Object[] toArray() {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public Object[] toArray(Object[] a) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   private void initialize(Object o) {
      if (this.ordering == 101) {
         if (o instanceof Comparable) {
            this.isComparable = true;
         }

         this.contents = new TreeMap();
      } else {
         this.contents = new HashMap();
      }

      this.initialized = true;
   }

   public boolean add(Object o) {
      if (!this.initialized) {
         this.initialize(o);
      }

      if (o == null) {
         throw new IllegalArgumentException("Null not supported.");
      } else {
         boolean result = false;
         if (this.ordering == 100) {
            if (this.contents.get(o) == null) {
               Node node = new Node(o);
               node.setPrev(this.last);
               if (this.last == null) {
                  this.first = node;
               } else {
                  this.last.setNext(node);
               }

               this.last = node;
               this.contents.put(o, node);
               result = true;
            }
         } else if (this.isComparable) {
            result = this.contents.put(o, PRESENT) == null;
         } else if (!this.contains(o)) {
            Integer key = new Integer(o.hashCode());
            Node node = (Node)this.contents.get(key);
            if (node == null) {
               this.contents.put(key, new Node(o));
            } else {
               Node cur = new Node(o);
               cur.next = node.next;
               node.next = cur;
            }

            result = true;
         }

         if (result) {
            ++this.size;
         }

         return result;
      }
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public boolean containsAll(Collection c) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public boolean addAll(int index, Collection c) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public void clear() {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public boolean equals(Object o) {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   public int hashCode() {
      throw new UnsupportedOperationException("Method not implemented.");
   }

   static class Node {
      private Object value = null;
      private Node next = null;
      private Node prev = null;

      public Node(Object value) {
         this.value = value;
      }

      public Object getValue() {
         return this.value;
      }

      public void setNext(Node next) {
         this.next = next;
      }

      public Node getNext() {
         return this.next;
      }

      public void setPrev(Node prev) {
         this.prev = prev;
      }

      public Node getPrev() {
         return this.prev;
      }
   }

   class PartialOrderIterator implements Iterator {
      Iterator iterator = null;
      Node curr = null;

      public PartialOrderIterator() {
         if (PartialOrderSet.this.ordering == 100) {
            this.curr = PartialOrderSet.this.first;
         } else if (!PartialOrderSet.this.initialized) {
            this.iterator = (new HashMap()).keySet().iterator();
         } else {
            this.iterator = PartialOrderSet.this.contents.keySet().iterator();
            if (!PartialOrderSet.this.isComparable && this.iterator.hasNext()) {
               this.curr = (Node)PartialOrderSet.this.contents.get(this.iterator.next());
            }
         }

      }

      public boolean hasNext() {
         return this.curr != null || this.iterator != null && this.iterator.hasNext();
      }

      public Object next() {
         if (this.curr != null) {
            Object result = this.curr.getValue();
            this.curr = this.curr.next;
            if (this.curr == null && this.iterator != null && this.iterator.hasNext()) {
               this.curr = (Node)PartialOrderSet.this.contents.get(this.iterator.next());
            }

            return result;
         } else if (this.iterator == null) {
            throw new NoSuchElementException();
         } else {
            return this.iterator.next();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
