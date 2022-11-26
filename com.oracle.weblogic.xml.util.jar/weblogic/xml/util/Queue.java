package weblogic.xml.util;

import java.util.Vector;

public class Queue extends Vector {
   public boolean empty() {
      return this.isEmpty();
   }

   public Object peek() {
      return this.firstElement();
   }

   public Object pull() {
      if (this.isEmpty()) {
         return null;
      } else {
         Object first = this.firstElement();
         this.removeElementAt(0);
         return first;
      }
   }

   public Object push(Object item) {
      this.addElement(item);
      return item;
   }

   public int search(Object o) {
      return this.lastIndexOf(o);
   }
}
