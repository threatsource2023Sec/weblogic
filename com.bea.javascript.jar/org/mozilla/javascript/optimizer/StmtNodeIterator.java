package org.mozilla.javascript.optimizer;

import java.util.Stack;
import org.mozilla.javascript.Node;

public class StmtNodeIterator {
   private Stack itsStack = new Stack();
   private Node itsStart;
   private Node itsCurrentNode;

   public StmtNodeIterator(Node var1) {
      this.itsStart = var1;
   }

   private Node findFirstInterestingNode(Node var1) {
      if (var1 == null) {
         return null;
      } else if (var1.getType() != 133 && var1.getType() != 138 && var1.getType() != 110) {
         return var1;
      } else if (var1.getFirst() == null) {
         return this.findFirstInterestingNode(var1.getNext());
      } else {
         this.itsStack.push(var1);
         return this.findFirstInterestingNode(var1.getFirst());
      }
   }

   public Node nextNode() {
      if (this.itsCurrentNode == null) {
         return this.itsCurrentNode = this.findFirstInterestingNode(this.itsStart);
      } else {
         this.itsCurrentNode = this.itsCurrentNode.getNext();
         if (this.itsCurrentNode != null) {
            return this.itsCurrentNode = this.findFirstInterestingNode(this.itsCurrentNode);
         } else {
            while(!this.itsStack.isEmpty()) {
               Node var1 = (Node)this.itsStack.pop();
               if (var1.getNext() != null) {
                  return this.itsCurrentNode = this.findFirstInterestingNode(var1.getNext());
               }
            }

            return null;
         }
      }
   }
}
