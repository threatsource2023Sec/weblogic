package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

final class Comment extends Instruction {
   public void parseContents(Parser parser) {
      this.parseChildren(parser);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this.typeCheckContents(stable);
      return Type.String;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      Text rawText = null;
      if (this.elementCount() == 1) {
         Object content = this.elementAt(0);
         if (content instanceof Text) {
            rawText = (Text)content;
         }
      }

      int comment;
      if (rawText != null) {
         il.append(methodGen.loadHandler());
         if (rawText.canLoadAsArrayOffsetLength()) {
            rawText.loadAsArrayOffsetLength(classGen, methodGen);
            comment = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "comment", "([CII)V");
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(comment, 4)));
         } else {
            il.append((CompoundInstruction)(new PUSH(cpg, rawText.getText())));
            comment = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "comment", "(Ljava/lang/String;)V");
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(comment, 2)));
         }
      } else {
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         il.append(classGen.loadTranslet());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "stringValueHandler", "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/runtime/StringValueHandler;"))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         il.append(methodGen.storeHandler());
         this.translateContents(classGen, methodGen);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.StringValueHandler", "getValue", "()Ljava/lang/String;"))));
         comment = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "comment", "(Ljava/lang/String;)V");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(comment, 2)));
         il.append(methodGen.storeHandler());
      }

   }
}
