package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;

public class LongLiteral extends Literal {
   private final TypedValue value;

   public LongLiteral(String payload, int pos, long value) {
      super(payload, pos);
      this.value = new TypedValue(value);
      this.exitTypeDescriptor = "J";
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
