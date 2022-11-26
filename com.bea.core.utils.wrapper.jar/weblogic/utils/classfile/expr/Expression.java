package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;

public interface Expression {
   void code(CodeAttribute var1, Bytecodes var2);

   Type getType();

   int getMaxStack();
}
