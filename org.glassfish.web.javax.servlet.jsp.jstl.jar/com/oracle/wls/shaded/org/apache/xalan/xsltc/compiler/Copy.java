package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class Copy extends Instruction {
   private UseAttributeSets _useSets;

   public void parseContents(Parser parser) {
      String useSets = this.getAttribute("use-attribute-sets");
      if (useSets.length() > 0) {
         if (!Util.isValidQNames(useSets)) {
            ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", useSets, this);
            parser.reportError(3, err);
         }

         this._useSets = new UseAttributeSets(useSets, parser);
      }

      this.parseChildren(parser);
   }

   public void display(int indent) {
      this.indent(indent);
      Util.println("Copy");
      this.indent(indent + 4);
      this.displayContents(indent + 4);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._useSets != null) {
         this._useSets.typeCheck(stable);
      }

      this.typeCheckContents(stable);
      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      LocalVariableGen name = methodGen.addLocalVariable2("name", Util.getJCRefType("Ljava/lang/String;"), (InstructionHandle)null);
      LocalVariableGen length = methodGen.addLocalVariable2("length", Util.getJCRefType("I"), (InstructionHandle)null);
      il.append(methodGen.loadDOM());
      il.append(methodGen.loadCurrentNode());
      il.append(methodGen.loadHandler());
      int cpy = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "shallowCopy", "(I" + TRANSLET_OUTPUT_SIG + ")" + "Ljava/lang/String;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(cpy, 3)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      name.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(name.getIndex()))));
      BranchHandle ifBlock1 = il.append((BranchInstruction)(new IFNULL((InstructionHandle)null)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(name.getIndex())));
      int lengthMethod = cpg.addMethodref("java.lang.String", "length", "()I");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(lengthMethod)));
      length.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ISTORE(length.getIndex()))));
      if (this._useSets != null) {
         SyntaxTreeNode parent = this.getParent();
         if (!(parent instanceof LiteralElement) && !(parent instanceof LiteralElement)) {
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(length.getIndex())));
            BranchHandle ifBlock2 = il.append((BranchInstruction)(new IFEQ((InstructionHandle)null)));
            this._useSets.translate(classGen, methodGen);
            ifBlock2.setTarget(il.append(NOP));
         } else {
            this._useSets.translate(classGen, methodGen);
         }
      }

      this.translateContents(classGen, methodGen);
      length.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(length.getIndex()))));
      BranchHandle ifBlock3 = il.append((BranchInstruction)(new IFEQ((InstructionHandle)null)));
      il.append(methodGen.loadHandler());
      name.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(name.getIndex()))));
      il.append(methodGen.endElement());
      InstructionHandle end = il.append(NOP);
      ifBlock1.setTarget(end);
      ifBlock3.setTarget(end);
      methodGen.removeLocalVariable(name);
      methodGen.removeLocalVariable(length);
   }
}
