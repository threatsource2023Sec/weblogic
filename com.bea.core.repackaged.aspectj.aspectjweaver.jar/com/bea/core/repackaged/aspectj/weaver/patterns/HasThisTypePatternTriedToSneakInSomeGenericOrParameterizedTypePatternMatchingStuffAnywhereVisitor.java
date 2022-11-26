package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor extends AbstractPatternNodeVisitor {
   boolean ohYesItHas = false;

   public Object visit(ExactTypePattern node, Object data) {
      UnresolvedType theExactType = node.getExactType();
      if (theExactType.isParameterizedType()) {
         this.ohYesItHas = true;
      }

      return data;
   }

   public Object visit(WildTypePattern node, Object data) {
      if (node.getUpperBound() != null) {
         this.ohYesItHas = true;
      }

      if (node.getLowerBound() != null) {
         this.ohYesItHas = true;
      }

      if (node.getTypeParameters().size() != 0) {
         this.ohYesItHas = true;
      }

      return data;
   }

   public boolean wellHasItThen() {
      return this.ohYesItHas;
   }
}
