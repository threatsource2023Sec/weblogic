package weblogic.utils.classfile.expr;

import weblogic.utils.AssertionError;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.ops.BranchOp;

public class ReturnStatement implements Statement {
   private Expression expression;

   public ReturnStatement() {
   }

   public ReturnStatement(Expression expression) {
      this.expression = expression;
   }

   private void codeExpression(CodeAttribute ca, Bytecodes code) {
      if (this.expression != null) {
         this.expression.code(ca, code);
      }

   }

   private void codeReturn(CodeAttribute ca, Bytecodes code) {
      Type t = this.expression == null ? Type.VOID : this.expression.getType();
      if (t == Type.BOOLEAN) {
         code.add(new Op(172));
      } else if (t == Type.BYTE) {
         code.add(new Op(172));
      } else if (t == Type.CHARACTER) {
         code.add(new Op(172));
      } else if (t == Type.SHORT) {
         code.add(new Op(172));
      } else if (t == Type.INT) {
         code.add(new Op(172));
      } else if (t == Type.FLOAT) {
         code.add(new Op(174));
      } else if (t == Type.DOUBLE) {
         code.add(new Op(175));
      } else if (t == Type.LONG) {
         code.add(new Op(173));
      } else if (t == Type.OBJECT) {
         code.add(new Op(176));
      } else if (t == Type.ARRAY) {
         code.add(new Op(176));
      } else {
         if (t != Type.VOID) {
            throw new AssertionError("Unknown type: " + t);
         }

         code.add(new Op(177));
      }

   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.codeExpression(ca, code);
      this.codeReturn(ca, code);
   }

   void codeForFinally(CodeAttribute ca, Bytecodes code, Label finallyHandler) {
      if (this.expression != null) {
         Type t = this.expression.getType();
         if (t != Type.VOID) {
            Scope scope = ca.getScope();
            LocalVariableExpression retSave = scope.createLocalVar(t);
            AssignStatement assign = new AssignStatement(retSave, this.expression);
            assign.code(ca, code);
            code.add(new BranchOp(201, finallyHandler));
            retSave.code(ca, code);
         } else {
            code.add(new BranchOp(201, finallyHandler));
            this.codeExpression(ca, code);
         }
      } else {
         code.add(new BranchOp(201, finallyHandler));
      }

      this.codeReturn(ca, code);
   }

   public int getMaxStack() {
      return this.expression == null ? 1 : this.expression.getMaxStack();
   }
}
