package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

interface MonitorCodeGenerator {
   void init(ClassInstrumentor var1, ClassVisitor var2, MethodVisitor var3, MonitorSpecificationBase var4, Type[] var5, Type var6, RegisterFile var7, String var8, boolean var9, boolean var10);

   void emitBeforeExec(int var1, boolean var2);

   void emitAfterExec(int var1, boolean var2);

   void emitBeforeCall(boolean var1, int var2, boolean var3, boolean var4);

   void emitAfterCall(int var1, boolean var2);
}
