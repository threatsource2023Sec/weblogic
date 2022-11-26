package weblogic.servlet.ejb2jsp.gui;

import javax.swing.tree.DefaultMutableTreeNode;

class MyNode extends DefaultMutableTreeNode {
   String label;

   public MyNode(Object o) {
      super(o);
   }

   public MyNode(Object o, String label) {
      super(o);
      this.label = label;
   }

   public MyNode(Object o, boolean b) {
      super(o, b);
   }

   public MyNode(Object o, boolean b, String label) {
      super(o, b);
      this.label = label;
   }

   public String toString() {
      return this.label != null ? this.label : super.toString();
   }
}
