package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;

public class LocalVariableExpression implements LHSExpression {
   public Scope.LocalVar var;
   public Scope scope;

   public LocalVariableExpression(Scope scope, Scope.LocalVar var) {
      this.scope = scope;
      this.var = var;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      code.add(this.var.getLoadOp());
   }

   public void codeAssign(CodeAttribute ca, Bytecodes code, Expression val) {
      if (val.getType() != this.var.getType()) {
         throw new AssertionError("invalid assignment from '" + val.getType() + "' to '" + this.var.getType() + "'");
      } else {
         val.code(ca, code);
         code.add(this.var.getStoreOp());
      }
   }

   Scope.LocalVar getLocalVar() {
      return this.var;
   }

   public Type getType() {
      return this.var == null ? Type.INVALID : this.var.getType();
   }

   public int getIndex() {
      return this.var.getIndex();
   }

   public int getMaxStack() {
      return this.getType().isWide() ? 2 : 1;
   }

   public void free() {
      this.scope.freeLocalVar(this);
   }
}
