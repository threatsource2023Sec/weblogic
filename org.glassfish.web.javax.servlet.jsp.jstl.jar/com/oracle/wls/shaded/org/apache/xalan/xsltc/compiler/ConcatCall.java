package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import java.util.Vector;

final class ConcatCall extends FunctionCall {
   public ConcatCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      for(int i = 0; i < this.argumentCount(); ++i) {
         Expression exp = this.argument(i);
         if (!exp.typeCheck(stable).identicalTo(Type.String)) {
            this.setArgument(i, new CastExpr(exp, Type.String));
         }
      }

      return this._type = Type.String;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int nArgs = this.argumentCount();
      switch (nArgs) {
         case 0:
            il.append((CompoundInstruction)(new PUSH(cpg, "")));
            break;
         case 1:
            this.argument().translate(classGen, methodGen);
            break;
         default:
            int initBuffer = cpg.addMethodref("java.lang.StringBuffer", "<init>", "()V");
            com.oracle.wls.shaded.org.apache.bcel.generic.Instruction append = new INVOKEVIRTUAL(cpg.addMethodref("java.lang.StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;"));
            int toString = cpg.addMethodref("java.lang.StringBuffer", "toString", "()Ljava/lang/String;");
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass("java.lang.StringBuffer"))));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(initBuffer)));

            for(int i = 0; i < nArgs; ++i) {
               this.argument(i).translate(classGen, methodGen);
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)append);
            }

            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(toString)));
      }

   }
}
