package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.impl.LongConstant;

public class LongLiteralMinValue extends LongLiteral {
   static final char[] CharValue = new char[]{'-', '9', '2', '2', '3', '3', '7', '2', '0', '3', '6', '8', '5', '4', '7', '7', '5', '8', '0', '8', 'L'};

   public LongLiteralMinValue(char[] token, char[] reducedForm, int start, int end) {
      super(token, reducedForm, start, end);
      this.constant = LongConstant.fromValue(Long.MIN_VALUE);
   }

   public void computeConstant() {
   }
}
