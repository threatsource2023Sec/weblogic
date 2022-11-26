package com.bea.core.repackaged.jdt.internal.compiler.ast;

public abstract class MagicLiteral extends Literal {
   public MagicLiteral(int start, int end) {
      super(start, end);
   }

   public boolean isValidJavaStatement() {
      return false;
   }

   public char[] source() {
      return null;
   }
}
