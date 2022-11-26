package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ElementValuePair;

public class NormalAnnotation extends Annotation {
   public MemberValuePair[] memberValuePairs;

   public NormalAnnotation(TypeReference type, int sourceStart) {
      this.type = type;
      this.sourceStart = sourceStart;
      this.sourceEnd = type.sourceEnd;
   }

   public ElementValuePair[] computeElementValuePairs() {
      int numberOfPairs = this.memberValuePairs == null ? 0 : this.memberValuePairs.length;
      if (numberOfPairs == 0) {
         return Binding.NO_ELEMENT_VALUE_PAIRS;
      } else {
         ElementValuePair[] pairs = new ElementValuePair[numberOfPairs];

         for(int i = 0; i < numberOfPairs; ++i) {
            pairs[i] = this.memberValuePairs[i].compilerElementPair;
         }

         return pairs;
      }
   }

   public MemberValuePair[] memberValuePairs() {
      return this.memberValuePairs == null ? NoValuePairs : this.memberValuePairs;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      super.printExpression(indent, output);
      output.append('(');
      if (this.memberValuePairs != null) {
         int i = 0;

         for(int max = this.memberValuePairs.length; i < max; ++i) {
            if (i > 0) {
               output.append(',');
            }

            this.memberValuePairs[i].print(indent, output);
         }
      }

      output.append(')');
      return output;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.memberValuePairs != null) {
            int memberValuePairsLength = this.memberValuePairs.length;

            for(int i = 0; i < memberValuePairsLength; ++i) {
               this.memberValuePairs[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.memberValuePairs != null) {
            int memberValuePairsLength = this.memberValuePairs.length;

            for(int i = 0; i < memberValuePairsLength; ++i) {
               this.memberValuePairs[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }
}
