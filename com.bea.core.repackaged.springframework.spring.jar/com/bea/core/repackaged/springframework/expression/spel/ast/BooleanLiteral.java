package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.support.BooleanTypedValue;

public class BooleanLiteral extends Literal {
   private final BooleanTypedValue value;

   public BooleanLiteral(String payload, int pos, boolean value) {
      super(payload, pos);
      this.value = BooleanTypedValue.forValue(value);
      this.exitTypeDescriptor = "Z";
   }

   public BooleanTypedValue getLiteralValue() {
      return this.value;
   }

   public boolean isCompilable() {
      return true;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      if (this.value == BooleanTypedValue.TRUE) {
         mv.visitLdcInsn(1);
      } else {
         mv.visitLdcInsn(0);
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
