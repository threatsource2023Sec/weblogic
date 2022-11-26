package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.util.Assert;

public class IntLiteral extends Literal {
   private final TypedValue value;

   public IntLiteral(String payload, int pos, int value) {
      super(payload, pos);
      this.value = new TypedValue(value);
      this.exitTypeDescriptor = "I";
   }

   public TypedValue getLiteralValue() {
      return this.value;
   }

   public boolean isCompilable() {
      return true;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      Integer intValue = (Integer)this.value.getValue();
      Assert.state(intValue != null, "No int value");
      if (intValue == -1) {
         mv.visitInsn(2);
      } else if (intValue >= 0 && intValue < 6) {
         mv.visitInsn(3 + intValue);
      } else {
         mv.visitLdcInsn(intValue);
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
