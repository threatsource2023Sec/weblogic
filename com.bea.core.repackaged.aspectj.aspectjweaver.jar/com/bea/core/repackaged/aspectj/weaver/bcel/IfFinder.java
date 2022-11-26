package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.weaver.patterns.AbstractPatternNodeVisitor;
import com.bea.core.repackaged.aspectj.weaver.patterns.AndPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.IfPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.NotPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.OrPointcut;

class IfFinder extends AbstractPatternNodeVisitor {
   boolean hasIf = false;

   public Object visit(IfPointcut node, Object data) {
      if (!node.alwaysFalse() && !node.alwaysTrue()) {
         this.hasIf = true;
      }

      return node;
   }

   public Object visit(AndPointcut node, Object data) {
      if (!this.hasIf) {
         node.getLeft().accept(this, data);
      }

      if (!this.hasIf) {
         node.getRight().accept(this, data);
      }

      return node;
   }

   public Object visit(NotPointcut node, Object data) {
      if (!this.hasIf) {
         node.getNegatedPointcut().accept(this, data);
      }

      return node;
   }

   public Object visit(OrPointcut node, Object data) {
      if (!this.hasIf) {
         node.getLeft().accept(this, data);
      }

      if (!this.hasIf) {
         node.getRight().accept(this, data);
      }

      return node;
   }
}
