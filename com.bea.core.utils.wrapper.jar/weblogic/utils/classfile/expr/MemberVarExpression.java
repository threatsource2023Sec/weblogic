package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPFieldref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

public class MemberVarExpression implements LHSExpression {
   Expression obj;
   CPFieldref fieldRef;

   public MemberVarExpression(Expression obj, CPFieldref field) {
      this.obj = obj;
      this.fieldRef = field;
   }

   public MemberVarExpression(CPFieldref field) {
      this.fieldRef = field;
   }

   public Type getType() {
      return this.fieldRef.getType();
   }

   public int getMaxStack() {
      if (this.obj != null) {
         int val = this.obj.getMaxStack();
         return val >= 2 ? val : 2;
      } else {
         return 2;
      }
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      if (this.obj != null) {
         this.obj.code(ca, code);
         code.add(new ConstPoolOp(180, cp, this.fieldRef));
      } else {
         code.add(new ConstPoolOp(178, cp, this.fieldRef));
      }

   }

   public void codeAssign(CodeAttribute ca, Bytecodes code, Expression val) {
      ConstantPool cp = ca.getConstantPool();
      if (this.obj != null) {
         this.obj.code(ca, code);
         val.code(ca, code);
         code.add(new ConstPoolOp(181, cp, this.fieldRef));
      } else {
         val.code(ca, code);
         code.add(new ConstPoolOp(179, cp, this.fieldRef));
      }

   }
}
