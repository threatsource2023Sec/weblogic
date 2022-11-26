package weblogic.messaging.util;

import java.util.NoSuchElementException;

public class List {
   int size;
   Element first;
   Element last;

   public void add(Element element) {
      if (element.getList() != null) {
         throw new IllegalArgumentException();
      } else {
         element.setNext((Element)null);
         if (this.first == null) {
            this.first = element;
         } else {
            this.last.setNext(element);
         }

         element.setPrev(this.last);
         this.last = element;
         element.setList(this);
         ++this.size;
      }
   }

   public void add(List list) {
      if (list == this) {
         throw new IllegalArgumentException();
      } else if (!list.isEmpty()) {
         if (this.first == null) {
            this.first = list.getFirst();
         } else {
            this.last.setNext(list.getFirst());
            list.getFirst().setPrev(this.last);
         }

         this.last = list.getLast();
         this.size += list.size();

         for(Element element = list.getFirst(); element != null; element = element.getNext()) {
            element.setList(this);
         }

         list.first = list.last = null;
         list.size = 0;
      }
   }

   public void clear() {
      while(this.first != null) {
         this.remove(this.first);
      }

   }

   public boolean contains(Element element) {
      return element.getList() == this;
   }

   public Element remove(Element element) {
      if (!this.contains(element)) {
         throw new NoSuchElementException();
      } else {
         if (element.getNext() != null) {
            element.getNext().setPrev(element.getPrev());
         } else {
            this.last = element.getPrev();
         }

         if (element.getPrev() == null) {
            this.first = element.getNext();
         } else {
            element.getPrev().setNext(element.getNext());
         }

         element.setList((List)null);
         element.setPrev((Element)null);
         element.setNext((Element)null);
         --this.size;
         return element;
      }
   }

   public List split(Element element, boolean after) {
      if (!this.contains(element)) {
         throw new NoSuchElementException();
      } else {
         List list = new List();
         if (after) {
            if (element == this.last) {
               return list;
            }

            list.first = element.getNext();
            list.last = this.last;
            this.last = element;
            list.first.setPrev((Element)null);
            this.last.setNext((Element)null);
         } else {
            list.first = element;
            list.last = this.last;
            if (element == this.first) {
               this.first = this.last = null;
            } else {
               this.last = element.getPrev();
               this.last.setNext((Element)null);
               element.setPrev((Element)null);
            }
         }

         for(element = list.getFirst(); element != null; --this.size) {
            element.setList(list);
            element = element.getNext();
            ++list.size;
         }

         return list;
      }
   }

   public Element getFirst() {
      return this.first;
   }

   public Element getLast() {
      return this.last;
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public ListIterator iterator(Element element) {
      return new ListIterator(this, element);
   }

   public ListIterator iterator() {
      return this.iterator(this.first);
   }

   public Element[] toArray(Element[] a) {
      Element[] array;
      if (a != null && a.length >= this.size) {
         array = a;
      } else {
         array = new Element[this.size];
      }

      int inc = 0;

      for(Element elt = this.first; elt != null; ++inc) {
         array[inc] = elt;
         elt = elt.getNext();
      }

      return array;
   }
}
