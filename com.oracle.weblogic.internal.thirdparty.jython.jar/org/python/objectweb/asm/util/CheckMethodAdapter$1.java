package org.python.objectweb.asm.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.tree.MethodNode;
import org.python.objectweb.asm.tree.analysis.Analyzer;
import org.python.objectweb.asm.tree.analysis.BasicVerifier;

class CheckMethodAdapter$1 extends MethodNode {
   // $FF: synthetic field
   final MethodVisitor val$cmv;

   CheckMethodAdapter$1(int var1, int var2, String var3, String var4, String var5, String[] var6, MethodVisitor var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.val$cmv = var7;
   }

   public void visitEnd() {
      Analyzer var1 = new Analyzer(new BasicVerifier());

      try {
         var1.analyze("dummy", this);
      } catch (Exception var5) {
         if (var5 instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
            throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
         }

         var5.printStackTrace();
         StringWriter var3 = new StringWriter();
         PrintWriter var4 = new PrintWriter(var3, true);
         CheckClassAdapter.printAnalyzerResult(this, var1, var4);
         var4.close();
         throw new RuntimeException(var5.getMessage() + ' ' + var3.toString());
      }

      this.accept(this.val$cmv);
   }
}
