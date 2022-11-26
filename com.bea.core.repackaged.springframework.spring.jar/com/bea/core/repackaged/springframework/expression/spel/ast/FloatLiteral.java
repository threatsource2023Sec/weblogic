package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;

public class FloatLiteral extends Literal {
   private final TypedValue value;

   public FloatLiteral(String payload, int pos, float value) {
      super(payload, pos);
      this.value = new TypedValue(value);
      this.exitTypeDescriptor = "F";
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
