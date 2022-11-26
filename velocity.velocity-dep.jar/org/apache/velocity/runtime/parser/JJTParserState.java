package org.apache.velocity.runtime.parser;

import java.util.Stack;
import org.apache.velocity.runtime.parser.node.Node;

class JJTParserState {
   private Stack nodes = new Stack();
   private Stack marks = new Stack();
   private int sp = 0;
   private int mk = 0;
   private boolean node_created;

   boolean nodeCreated() {
      return this.node_created;
   }

   void reset() {
      this.nodes.removeAllElements();
      this.marks.removeAllElements();
      this.sp = 0;
      this.mk = 0;
   }

   Node rootNode() {
      return (Node)this.nodes.elementAt(0);
   }

   void pushNode(Node n) {
      this.nodes.push(n);
      ++this.sp;
   }

   Node popNode() {
      if (--this.sp < this.mk) {
         this.mk = (Integer)this.marks.pop();
      }

      return (Node)this.nodes.pop();
   }

   Node peekNode() {
      return (Node)this.nodes.peek();
   }

   int nodeArity() {
      return this.sp - this.mk;
   }

   void clearNodeScope(Node n) {
      while(this.sp > this.mk) {
         this.popNode();
      }

      this.mk = (Integer)this.marks.pop();
   }

   void openNodeScope(Node n) {
      this.marks.push(new Integer(this.mk));
      this.mk = this.sp;
      n.jjtOpen();
   }

   void closeNodeScope(Node n, int num) {
      this.mk = (Integer)this.marks.pop();

      while(num-- > 0) {
         Node c = this.popNode();
         c.jjtSetParent(n);
         n.jjtAddChild(c, num);
      }

      n.jjtClose();
      this.pushNode(n);
      this.node_created = true;
   }

   void closeNodeScope(Node n, boolean condition) {
      if (condition) {
         int a = this.nodeArity();
         this.mk = (Integer)this.marks.pop();

         while(a-- > 0) {
            Node c = this.popNode();
            c.jjtSetParent(n);
            n.jjtAddChild(c, a);
         }

         n.jjtClose();
         this.pushNode(n);
         this.node_created = true;
      } else {
         this.mk = (Integer)this.marks.pop();
         this.node_created = false;
      }

   }
}
