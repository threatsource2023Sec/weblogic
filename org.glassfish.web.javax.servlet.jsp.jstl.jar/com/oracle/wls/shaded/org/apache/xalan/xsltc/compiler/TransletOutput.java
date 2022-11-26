package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.StringType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class TransletOutput extends Instruction {
   private Expression _filename;
   private boolean _append;

   public void display(int indent) {
      this.indent(indent);
      Util.println("TransletOutput: " + this._filename);
   }

   public void parseContents(Parser parser) {
      String filename = this.getAttribute("file");
      String append = this.getAttribute("append");
      if (filename == null || filename.equals("")) {
         this.reportError(this, parser, "REQUIRED_ATTR_ERR", "file");
      }

      this._filename = AttributeValue.create(this, filename, parser);
      if (append == null || !append.toLowerCase().equals("yes") && !append.toLowerCase().equals("true")) {
         this._append = false;
      } else {
         this._append = true;
      }

      this.parseChildren(parser);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      Type type = this._filename.typeCheck(stable);
      if (!(type instanceof StringType)) {
         this._filename = new CastExpr(this._filename, Type.String);
      }

      this.typeCheckContents(stable);
      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      boolean isSecureProcessing = classGen.getParser().getXSLTC().isSecureProcessing();
      int open;
      if (isSecureProcessing) {
         open = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "unallowed_extension_elementF", "(Ljava/lang/String;)V");
         il.append((CompoundInstruction)(new PUSH(cpg, "redirect")));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(open)));
      } else {
         il.append(methodGen.loadHandler());
         open = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "openOutputHandler", "(Ljava/lang/String;Z)" + TRANSLET_OUTPUT_SIG);
         int close = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "closeOutputHandler", "(" + TRANSLET_OUTPUT_SIG + ")V");
         il.append(classGen.loadTranslet());
         this._filename.translate(classGen, methodGen);
         il.append((CompoundInstruction)(new PUSH(cpg, this._append)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(open)));
         il.append(methodGen.storeHandler());
         this.translateContents(classGen, methodGen);
         il.append(classGen.loadTranslet());
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(close)));
         il.append(methodGen.storeHandler());
      }
   }
}
