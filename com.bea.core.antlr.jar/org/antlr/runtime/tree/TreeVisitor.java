package org.antlr.runtime.tree;

public class TreeVisitor {
   protected TreeAdaptor adaptor;

   public TreeVisitor(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public TreeVisitor() {
      this(new CommonTreeAdaptor());
   }

   public Object visit(Object t, TreeVisitorAction action) {
      boolean isNil = this.adaptor.isNil(t);
      if (action != null && !isNil) {
         t = action.pre(t);
      }

      for(int i = 0; i < this.adaptor.getChildCount(t); ++i) {
         Object child = this.adaptor.getChild(t, i);
         Object visitResult = this.visit(child, action);
         Object childAfterVisit = this.adaptor.getChild(t, i);
         if (visitResult != childAfterVisit) {
            this.adaptor.setChild(t, i, visitResult);
         }
      }

      if (action != null && !isNil) {
         t = action.post(t);
      }

      return t;
   }
}
