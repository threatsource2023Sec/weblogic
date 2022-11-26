package weblogic.utils.classfile.expr;

import weblogic.utils.AssertionError;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

public class ExpressionStatement implements Statement {
   Expression expression;

   public ExpressionStatement(Expression expression) {
      this.expression = expression;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.expression.code(ca, code);
      Type t = this.expression.getType();
      if (t == Type.BOOLEAN) {
         code.add(new Op(87));
      } else if (t == Type.BYTE) {
         code.add(new Op(87));
      } else if (t == Type.CHARACTER) {
         code.add(new Op(87));
      } else if (t == Type.SHORT) {
         code.add(new Op(87));
      } else if (t == Type.INT) {
         code.add(new Op(87));
      } else if (t == Type.FLOAT) {
         code.add(new Op(87));
      } else if (t == Type.DOUBLE) {
         code.add(new Op(88));
      } else if (t == Type.LONG) {
         code.add(new Op(88));
      } else {
         if (t != Type.OBJECT) {
            if (t == Type.VOID) {
               return;
            }

            throw new AssertionError("Unknown type: " + t);
         }

         code.add(new Op(87));
      }

   }

   public int getMaxStack() {
      return this.expression.getMaxStack();
   }
}
