package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import java.util.Vector;

final class ProcessingInstructionPattern extends StepPattern {
   private String _name = null;
   private boolean _typeChecked = false;

   public ProcessingInstructionPattern(String name) {
      super(3, 7, (Vector)null);
      this._name = name;
   }

   public double getDefaultPriority() {
      return this._name != null ? 0.0 : -0.5;
   }

   public String toString() {
      return this._predicates == null ? "processing-instruction(" + this._name + ")" : "processing-instruction(" + this._name + ")" + this._predicates;
   }

   public void reduceKernelPattern() {
      this._typeChecked = true;
   }

   public boolean isWildcard() {
      return false;
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this.hasPredicates()) {
         int n = this._predicates.size();

         for(int i = 0; i < n; ++i) {
            Predicate pred = (Predicate)this._predicates.elementAt(i);
            pred.typeCheck(stable);
         }
      }

      return Type.NodeSet;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int gname = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getNodeName", "(I)Ljava/lang/String;");
      int cmp = cpg.addMethodref("java.lang.String", "equals", "(Ljava/lang/Object;)Z");
      il.append(methodGen.loadCurrentNode());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
      il.append(methodGen.storeCurrentNode());
      int n;
      if (!this._typeChecked) {
         il.append(methodGen.loadCurrentNode());
         n = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
         il.append(methodGen.loadDOM());
         il.append(methodGen.loadCurrentNode());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(n, 2)));
         il.append((CompoundInstruction)(new PUSH(cpg, 7)));
         this._falseList.add(il.append((BranchInstruction)(new IF_ICMPEQ((InstructionHandle)null))));
      }

      il.append((CompoundInstruction)(new PUSH(cpg, this._name)));
      il.append(methodGen.loadDOM());
      il.append(methodGen.loadCurrentNode());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(gname, 2)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cmp)));
      this._falseList.add(il.append((BranchInstruction)(new IFEQ((InstructionHandle)null))));
      if (this.hasPredicates()) {
         n = this._predicates.size();

         for(int i = 0; i < n; ++i) {
            Predicate pred = (Predicate)this._predicates.elementAt(i);
            Expression exp = pred.getExpr();
            exp.translateDesynthesized(classGen, methodGen);
            this._trueList.append(exp._trueList);
            this._falseList.append(exp._falseList);
         }
      }

      InstructionHandle restore = il.append(methodGen.storeCurrentNode());
      this.backPatchTrueList(restore);
      BranchHandle skipFalse = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      restore = il.append(methodGen.storeCurrentNode());
      this.backPatchFalseList(restore);
      this._falseList.add(il.append((BranchInstruction)(new GOTO((InstructionHandle)null))));
      skipFalse.setTarget(il.append(NOP));
   }
}
