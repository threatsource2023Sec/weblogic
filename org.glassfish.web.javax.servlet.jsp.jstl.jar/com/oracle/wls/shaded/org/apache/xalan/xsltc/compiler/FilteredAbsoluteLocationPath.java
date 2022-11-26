package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class FilteredAbsoluteLocationPath extends Expression {
   private Expression _path;

   public FilteredAbsoluteLocationPath() {
      this._path = null;
   }

   public FilteredAbsoluteLocationPath(Expression path) {
      this._path = path;
      if (path != null) {
         this._path.setParent(this);
      }

   }

   public void setParser(Parser parser) {
      super.setParser(parser);
      if (this._path != null) {
         this._path.setParser(parser);
      }

   }

   public Expression getPath() {
      return this._path;
   }

   public String toString() {
      return "FilteredAbsoluteLocationPath(" + (this._path != null ? this._path.toString() : "null") + ')';
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._path != null) {
         Type ptype = this._path.typeCheck(stable);
         if (ptype instanceof NodeType) {
            this._path = new CastExpr(this._path, Type.NodeSet);
         }
      }

      return this._type = Type.NodeSet;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int initDFI;
      if (this._path != null) {
         initDFI = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.DupFilterIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;)V");
         LocalVariableGen pathTemp = methodGen.addLocalVariable("filtered_absolute_location_path_tmp", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
         this._path.translate(classGen, methodGen);
         pathTemp.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(pathTemp.getIndex()))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.DupFilterIterator"))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         pathTemp.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(pathTemp.getIndex()))));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(initDFI)));
      } else {
         initDFI = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
         il.append(methodGen.loadDOM());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(initDFI, 1)));
      }

   }
}
