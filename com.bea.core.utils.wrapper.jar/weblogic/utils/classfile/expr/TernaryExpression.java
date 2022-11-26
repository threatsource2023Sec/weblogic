package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.ops.BranchOp;

public class TernaryExpression implements Expression {
   private ConditionalExpression eval;
   private Expression ifTrue;
   private Expression ifFalse;

   public TernaryExpression(ConditionalExpression eval, Expression ifTrue, Expression ifFalse) {
      this.eval = eval;
      this.ifTrue = ifTrue;
      this.ifFalse = ifFalse;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      Label falseLabel = new Label();
      Label endLabel = new Label();
      if (this.eval instanceof ConditionalExpression) {
         this.eval.codeConditional(ca, code, falseLabel);
      } else {
         this.eval.code(ca, code);
         code.add(new BranchOp(153, falseLabel));
      }

      this.ifTrue.code(ca, code);
      code.add(new BranchOp(167, endLabel));
      code.add(falseLabel);
      this.ifFalse.code(ca, code);
      code.add(endLabel);
   }

   public Type getType() {
      return this.ifTrue.getType();
   }

   public int getMaxStack() {
      return Math.max(this.eval.getMaxStack(), Math.max(this.ifTrue.getMaxStack(), this.ifFalse.getMaxStack()));
   }
}
