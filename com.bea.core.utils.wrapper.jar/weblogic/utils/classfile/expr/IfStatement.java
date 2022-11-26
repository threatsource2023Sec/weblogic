package weblogic.utils.classfile.expr;

import weblogic.utils.Debug;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.ops.BranchOp;

public class IfStatement implements Statement {
   private final Expression eval;
   private final Statement ifTrue;

   public IfStatement(Expression eval, Statement ifTrue) {
      Debug.assertion(eval.getType() == Type.BOOLEAN);
      this.eval = eval;
      this.ifTrue = ifTrue;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      Label falseLabel = new Label();
      if (this.eval instanceof ConditionalExpression) {
         ((ConditionalExpression)this.eval).codeConditional(ca, code, falseLabel);
      } else {
         this.eval.code(ca, code);
         code.add(new BranchOp(153, falseLabel));
      }

      this.ifTrue.code(ca, code);
      code.add(falseLabel);
   }

   public int getMaxStack() {
      return Math.max(this.eval.getMaxStack(), this.ifTrue.getMaxStack());
   }
}
