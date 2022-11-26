package org.python.modules.jffi;

public interface JITMethodGenerator {
   boolean isSupported(JITSignature var1);

   void generate(AsmClassBuilder var1, String var2, JITSignature var3);
}
