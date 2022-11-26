package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class StringLiteral extends Literal {
   private final TypedValue value;

   public StringLiteral(String payload, int pos, String value) {
      super(payload, pos);
      String valueWithinQuotes = value.substring(1, value.length() - 1);
      valueWithinQuotes = StringUtils.replace(valueWithinQuotes, "''", "'");
      valueWithinQuotes = StringUtils.replace(valueWithinQuotes, "\"\"", "\"");
      this.value = new TypedValue(valueWithinQuotes);
      this.exitTypeDescriptor = "Ljava/lang/String";
   }

   public TypedValue getLiteralValue() {
      return this.value;
   }

   public String toString() {
      return "'" + this.getLiteralValue().getValue() + "'";
   }

   public boolean isCompilable() {
      return true;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      mv.visitLdcInsn(this.value.getValue());
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
