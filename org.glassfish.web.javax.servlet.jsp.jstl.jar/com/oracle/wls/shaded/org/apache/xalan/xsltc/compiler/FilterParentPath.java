package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeSetType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ReferenceType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class FilterParentPath extends Expression {
   private Expression _filterExpr;
   private Expression _path;
   private boolean _hasDescendantAxis = false;

   public FilterParentPath(Expression filterExpr, Expression path) {
      (this._path = path).setParent(this);
      (this._filterExpr = filterExpr).setParent(this);
   }

   public void setParser(Parser parser) {
      super.setParser(parser);
      this._filterExpr.setParser(parser);
      this._path.setParser(parser);
   }

   public String toString() {
      return "FilterParentPath(" + this._filterExpr + ", " + this._path + ')';
   }

   public void setDescendantAxis() {
      this._hasDescendantAxis = true;
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      Type ftype = this._filterExpr.typeCheck(stable);
      if (!(ftype instanceof NodeSetType)) {
         if (ftype instanceof ReferenceType) {
            this._filterExpr = new CastExpr(this._filterExpr, Type.NodeSet);
         } else {
            if (!(ftype instanceof NodeType)) {
               throw new TypeCheckError(this);
            }

            this._filterExpr = new CastExpr(this._filterExpr, Type.NodeSet);
         }
      }

      Type ptype = this._path.typeCheck(stable);
      if (!(ptype instanceof NodeSetType)) {
         this._path = new CastExpr(this._path, Type.NodeSet);
      }

      return this._type = Type.NodeSet;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int initSI = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.StepIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
      this._filterExpr.translate(classGen, methodGen);
      LocalVariableGen filterTemp = methodGen.addLocalVariable("filter_parent_path_tmp1", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
      filterTemp.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(filterTemp.getIndex()))));
      this._path.translate(classGen, methodGen);
      LocalVariableGen pathTemp = methodGen.addLocalVariable("filter_parent_path_tmp2", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
      pathTemp.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(pathTemp.getIndex()))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.StepIterator"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      filterTemp.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(filterTemp.getIndex()))));
      pathTemp.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(pathTemp.getIndex()))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(initSI)));
      if (this._hasDescendantAxis) {
         int incl = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMAxisIteratorBase", "includeSelf", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(incl)));
      }

      SyntaxTreeNode parent = this.getParent();
      boolean parentAlreadyOrdered = parent instanceof RelativeLocationPath || parent instanceof FilterParentPath || parent instanceof KeyCall || parent instanceof CurrentCall || parent instanceof DocumentCall;
      if (!parentAlreadyOrdered) {
         int order = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "orderNodes", "(Lorg/apache/xml/dtm/DTMAxisIterator;I)Lorg/apache/xml/dtm/DTMAxisIterator;");
         il.append(methodGen.loadDOM());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
         il.append(methodGen.loadContextNode());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(order, 3)));
      }

   }
}
