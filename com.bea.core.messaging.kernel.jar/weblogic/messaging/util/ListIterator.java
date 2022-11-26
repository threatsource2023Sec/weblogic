package weblogic.messaging.util;

public final class ListIterator {
   private List list;
   private Element cursor;
   private Element element;

   ListIterator(List list, Element element) {
      this.list = list;
      this.initialize(element);
   }

   public void initialize(Element element) {
      if (element != null && !this.list.contains(element)) {
         throw new IllegalArgumentException("List does not contain the specified element");
      } else {
         this.cursor = element;
         this.element = null;
      }
   }

   public boolean hasNext() {
      return this.cursor != null;
   }

   public boolean hasPrevious() {
      if (this.cursor == null) {
         return this.list.getLast() != null;
      } else {
         return this.cursor.getPrev() != null;
      }
   }

   public Element next() {
      if ((this.element = this.cursor) != null) {
         this.cursor = this.cursor.getNext();
      }

      return this.element;
   }

   public Element previous() {
      if (this.cursor != null) {
         if (this.cursor.getPrev() != null) {
            return this.element = this.cursor = this.cursor.getPrev();
         }
      } else if (this.list.getLast() != null) {
         return this.element = this.cursor = this.list.getLast();
      }

      return this.element;
   }

   public Element remove() {
      if (this.element == null) {
         throw new IllegalStateException();
      } else {
         if (this.element == this.cursor) {
            this.cursor = this.cursor.getNext();
         }

         this.list.remove(this.element);
         Element temp = this.element;
         this.element = null;
         return temp;
      }
   }

   public void set(Object o) {
      if (this.element == null) {
         throw new IllegalStateException();
      } else {
         Element temp = (Element)o;
         this.remove();
         this.add(temp);
         if (this.cursor == this.element) {
            this.cursor = temp;
         }

         this.element = temp;
      }
   }

   public void add(Element temp) {
      if (temp.getList() != null) {
         throw new IllegalArgumentException();
      } else {
         if (this.cursor != null) {
            temp.setNext(this.cursor);
            temp.setPrev(this.cursor.getPrev());
            this.cursor.setPrev(temp);
         } else {
            temp.setNext((Element)null);
            temp.setPrev(this.list.getLast());
            this.list.last = temp;
         }

         if (temp.getPrev() == null) {
            this.list.first = temp;
         } else {
            temp.getPrev().setNext(temp);
         }

         this.element = null;
         temp.setList(this.list);
         ++this.list.size;
      }
   }

   public int nextIndex() {
      if (this.cursor == null) {
         return this.list.size;
      } else {
         Element temp = this.cursor.getPrev();

         int index;
         for(index = 0; temp != null; temp = temp.getPrev()) {
            ++index;
         }

         return index;
      }
   }

   public int previousIndex() {
      if (this.cursor == null) {
         return this.list.size - 1;
      } else {
         Element temp = this.cursor.getPrev();

         int index;
         for(index = -1; temp != null; ++index) {
            temp = temp.getPrev();
         }

         return index;
      }
   }
}
