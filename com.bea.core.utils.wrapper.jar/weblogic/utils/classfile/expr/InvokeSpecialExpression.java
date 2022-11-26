package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.cp.CPMemberType;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

public class InvokeSpecialExpression extends InvokeExpression {
   public InvokeSpecialExpression(CPMemberType meth, Expression ref, Expression[] args) {
      super(meth, ref, args);
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      this.ref.code(ca, code);

      for(int i = 0; i < this.args.length; ++i) {
         this.args[i].code(ca, code);
      }

      code.add(new ConstPoolOp(183, cp, this.meth));
   }
}
