package org.mozilla.javascript;

import java.util.Stack;

public class PreorderNodeIterator {
   private Node start;
   private Node current;
   private Node currentParent;
   private Stack stack;

   public PreorderNodeIterator(Node var1) {
      this.start = var1;
      this.stack = new Stack();
   }

   public Node currentNode() {
      return this.current;
   }

   public Node getCurrentParent() {
      return this.currentParent;
   }

   public Node nextNode() {
      if (this.current == null) {
         return this.current = this.start;
      } else {
         if (this.current.first != null) {
            this.stack.push(this.current);
            this.currentParent = this.current;
            this.current = this.current.first;
         } else {
            this.current = this.current.next;

            while(true) {
               boolean var1 = this.stack.isEmpty();
               if (var1 || this.current != null) {
                  this.currentParent = var1 ? null : (Node)this.stack.peek();
                  break;
               }

               this.current = (Node)this.stack.pop();
               this.current = this.current.next;
            }
         }

         return this.current;
      }
   }

   public void replaceCurrent(Node var1) {
      this.currentParent.replaceChild(this.current, var1);
      this.current = var1;
   }
}
