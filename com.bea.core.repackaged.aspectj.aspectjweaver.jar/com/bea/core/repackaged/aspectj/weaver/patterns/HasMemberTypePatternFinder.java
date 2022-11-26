package com.bea.core.repackaged.aspectj.weaver.patterns;

public class HasMemberTypePatternFinder extends AbstractPatternNodeVisitor {
   private boolean hasMemberTypePattern = false;

   public HasMemberTypePatternFinder(TypePattern aPattern) {
      aPattern.traverse(this, (Object)null);
   }

   public Object visit(HasMemberTypePattern node, Object data) {
      this.hasMemberTypePattern = true;
      return null;
   }

   public boolean hasMemberTypePattern() {
      return this.hasMemberTypePattern;
   }
}
