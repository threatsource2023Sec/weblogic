package weblogic.xml.dom;

import java.util.Iterator;

public class ChildIterator implements Iterator {
   private int current = 0;
   private NodeImpl parent;

   public ChildIterator(NodeImpl n) {
      if (n == null) {
         throw new IllegalArgumentException("Parent may not be null");
      } else {
         this.parent = n;
      }
   }

   public Object next() {
      return this.parent.item(this.current++);
   }

   public boolean hasNext() {
      return this.current < this.parent.getLength();
   }

   public void remove() {
      throw new UnsupportedOperationException("Not Supported");
   }
}
