package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;

public class MarkerAnnotation extends Annotation {
   public MarkerAnnotation(TypeReference type, int sourceStart) {
      this.type = type;
      this.sourceStart = sourceStart;
      this.sourceEnd = type.sourceEnd;
   }

   public MemberValuePair[] memberValuePairs() {
      return NoValuePairs;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope) && this.type != null) {
         this.type.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope) && this.type != null) {
         this.type.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
