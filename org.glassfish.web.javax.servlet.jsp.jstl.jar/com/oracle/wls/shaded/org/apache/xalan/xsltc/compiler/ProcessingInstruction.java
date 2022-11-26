package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import com.oracle.wls.shaded.org.apache.xml.utils.XML11Char;

final class ProcessingInstruction extends Instruction {
   private AttributeValue _name;
   private boolean _isLiteral = false;

   public void parseContents(Parser parser) {
      String name = this.getAttribute("name");
      if (name.length() > 0) {
         this._isLiteral = Util.isLiteral(name);
         if (this._isLiteral && !XML11Char.isXML11ValidNCName(name)) {
            ErrorMsg err = new ErrorMsg("INVALID_NCNAME_ERR", name, this);
            parser.reportError(3, err);
         }

         this._name = AttributeValue.create(this, name, parser);
      } else {
         this.reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
      }

      if (name.equals("xml")) {
         this.reportError(this, parser, "ILLEGAL_PI_ERR", "xml");
      }

      this.parseChildren(parser);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this._name.typeCheck(stable);
      this.typeCheckContents(stable);
      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (!this._isLiteral) {
         LocalVariableGen nameValue = methodGen.addLocalVariable2("nameValue", Util.getJCRefType("Ljava/lang/String;"), (InstructionHandle)null);
         this._name.translate(classGen, methodGen);
         nameValue.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(nameValue.getIndex()))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(nameValue.getIndex())));
         int check = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "checkNCName", "(Ljava/lang/String;)V");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(check)));
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         nameValue.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(nameValue.getIndex()))));
      } else {
         il.append(methodGen.loadHandler());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         this._name.translate(classGen, methodGen);
      }

      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "stringValueHandler", "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/runtime/StringValueHandler;"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      il.append(methodGen.storeHandler());
      this.translateContents(classGen, methodGen);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.StringValueHandler", "getValueOfPI", "()Ljava/lang/String;"))));
      int processingInstruction = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "processingInstruction", "(Ljava/lang/String;Ljava/lang/String;)V");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(processingInstruction, 3)));
      il.append(methodGen.storeHandler());
   }
}
