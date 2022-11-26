package weblogic.utils.classfile.expr;

import weblogic.utils.Debug;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.ops.BranchOp;

public class NotNullCondExpression implements ConditionalExpression {
   Expression ref;

   public NotNullCondExpression(Expression ref) {
      Debug.assertion(ref.getType() == Type.OBJECT || ref.getType() == Type.ARRAY);
      this.ref = ref;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.ref.code(ca, code);
   }

   public void codeConditional(CodeAttribute ca, Bytecodes code, Label falseLabel) {
      this.ref.code(ca, code);
      code.add(new BranchOp(198, falseLabel));
   }

   public Type getType() {
      return Type.BOOLEAN;
   }

   public int getMaxStack() {
      return 1;
   }
}
