package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class ParentPattern extends RelativePathPattern {
   private final Pattern _left;
   private final RelativePathPattern _right;

   public ParentPattern(Pattern left, RelativePathPattern right) {
      (this._left = left).setParent(this);
      (this._right = right).setParent(this);
   }

   public void setParser(Parser parser) {
      super.setParser(parser);
      this._left.setParser(parser);
      this._right.setParser(parser);
   }

   public boolean isWildcard() {
      return false;
   }

   public StepPattern getKernelPattern() {
      return this._right.getKernelPattern();
   }

   public void reduceKernelPattern() {
      this._right.reduceKernelPattern();
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this._left.typeCheck(stable);
      return this._right.typeCheck(stable);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      LocalVariableGen local = methodGen.addLocalVariable2("ppt", Util.getJCRefType("I"), (InstructionHandle)null);
      com.oracle.wls.shaded.org.apache.bcel.generic.Instruction loadLocal = new ILOAD(local.getIndex());
      com.oracle.wls.shaded.org.apache.bcel.generic.Instruction storeLocal = new ISTORE(local.getIndex());
      if (this._right.isWildcard()) {
         il.append(methodGen.loadDOM());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
      } else if (this._right instanceof StepPattern) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         local.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)storeLocal));
         this._right.translate(classGen, methodGen);
         il.append(methodGen.loadDOM());
         local.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)loadLocal));
      } else {
         this._right.translate(classGen, methodGen);
         if (this._right instanceof AncestorPattern) {
            il.append(methodGen.loadDOM());
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
         }
      }

      int getParent = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(getParent, 2)));
      SyntaxTreeNode p = this.getParent();
      if (p != null && !(p instanceof Instruction) && !(p instanceof TopLevelElement)) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         InstructionHandle storeInst = il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)storeLocal);
         if (local.getStart() == null) {
            local.setStart(storeInst);
         }

         this._left.translate(classGen, methodGen);
         il.append(methodGen.loadDOM());
         local.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)loadLocal));
      } else {
         this._left.translate(classGen, methodGen);
      }

      methodGen.removeLocalVariable(local);
      if (this._right instanceof AncestorPattern) {
         AncestorPattern ancestor = (AncestorPattern)this._right;
         this._left.backPatchFalseList(ancestor.getLoopHandle());
      }

      this._trueList.append(this._right._trueList.append(this._left._trueList));
      this._falseList.append(this._right._falseList.append(this._left._falseList));
   }

   public String toString() {
      return "Parent(" + this._left + ", " + this._right + ')';
   }
}
