package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;

public class NullLiteral extends Literal {
   public NullLiteral(int pos) {
      super((String)null, pos);
      this.exitTypeDescriptor = "Ljava/lang/Object";
   }

   public TypedValue getLiteralValue() {
      return TypedValue.NULL;
   }

   public String toString() {
      return "null";
   }

   public boolean isCompilable() {
      return true;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      mv.visitInsn(1);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
