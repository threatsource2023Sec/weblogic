package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;

public interface LHSExpression extends Expression {
   void codeAssign(CodeAttribute var1, Bytecodes var2, Expression var3);
}
