package org.mozilla.javascript;

import java.util.Enumeration;

public class ShallowNodeIterator implements Enumeration {
   private Node current;

   public ShallowNodeIterator(Node var1) {
      this.current = var1;
   }

   public boolean hasMoreElements() {
      return this.current != null;
   }

   public Object nextElement() {
      return this.nextNode();
   }

   public Node nextNode() {
      Node var1 = this.current;
      this.current = this.current.next;
      return var1;
   }
}
