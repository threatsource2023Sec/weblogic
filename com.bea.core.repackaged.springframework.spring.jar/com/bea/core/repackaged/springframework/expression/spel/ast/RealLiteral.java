package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;

public class RealLiteral extends Literal {
   private final TypedValue value;

   public RealLiteral(String payload, int pos, double value) {
      super(payload, pos);
      this.value = new TypedValue(value);
      this.exitTypeDescriptor = "D";
   }

   public TypedValue getLiteralValue() {
      return this.value;
   }

   public boolean isCompilable() {
      return true;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      mv.visitLdcInsn(this.value.getValue());
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
