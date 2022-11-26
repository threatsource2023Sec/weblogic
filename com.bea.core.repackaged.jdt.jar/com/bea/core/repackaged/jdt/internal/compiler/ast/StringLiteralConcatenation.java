package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;

public class StringLiteralConcatenation extends StringLiteral {
   private static final int INITIAL_SIZE = 5;
   public Expression[] literals;
   public int counter;

   public StringLiteralConcatenation(StringLiteral str1, StringLiteral str2) {
      super(str1.sourceStart, str1.sourceEnd);
      this.source = str1.source;
      this.literals = new StringLiteral[5];
      this.counter = 0;
      this.literals[this.counter++] = str1;
      this.extendsWith(str2);
   }

   public StringLiteralConcatenation extendsWith(StringLiteral lit) {
      this.sourceEnd = lit.sourceEnd;
      int literalsLength = this.literals.length;
      if (this.counter == literalsLength) {
         System.arraycopy(this.literals, 0, this.literals = new StringLiteral[literalsLength + 5], 0, literalsLength);
      }

      int length = this.source.length;
      System.arraycopy(this.source, 0, this.source = new char[length + lit.source.length], 0, length);
      System.arraycopy(lit.source, 0, this.source, length, lit.source.length);
      this.literals[this.counter++] = lit;
      return this;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      output.append("StringLiteralConcatenation{");
      int i = 0;

      for(int max = this.counter; i < max; ++i) {
         this.literals[i].printExpression(indent, output);
         output.append("+\n");
      }

      return output.append('}');
   }

   public char[] source() {
      return this.source;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int i = 0;

         for(int max = this.counter; i < max; ++i) {
            this.literals[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }
}
