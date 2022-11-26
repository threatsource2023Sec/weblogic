package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Type;

public interface ConditionalExpression {
   void code(CodeAttribute var1, Bytecodes var2);

   void codeConditional(CodeAttribute var1, Bytecodes var2, Label var3);

   Type getType();

   int getMaxStack();
}
