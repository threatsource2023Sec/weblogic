package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

abstract class IdKeyPattern extends LocationPathPattern {
   protected RelativePathPattern _left = null;
   private String _index = null;
   private String _value = null;

   public IdKeyPattern(String index, String value) {
      this._index = index;
      this._value = value;
   }

   public String getIndexName() {
      return this._index;
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      return Type.NodeSet;
   }

   public boolean isWildcard() {
      return false;
   }

   public void setLeft(RelativePathPattern left) {
      this._left = left;
   }

   public StepPattern getKernelPattern() {
      return null;
   }

   public void reduceKernelPattern() {
   }

   public String toString() {
      return "id/keyPattern(" + this._index + ", " + this._value + ')';
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int getKeyIndex = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "getKeyIndex", "(Ljava/lang/String;)Lorg/apache/xalan/xsltc/dom/KeyIndex;");
      int lookupId = cpg.addMethodref("com/oracle/wls/shaded/org/apache/xalan/xsltc/dom/KeyIndex", "containsID", "(ILjava/lang/Object;)I");
      int lookupKey = cpg.addMethodref("com/oracle/wls/shaded/org/apache/xalan/xsltc/dom/KeyIndex", "containsKey", "(ILjava/lang/Object;)I");
      int getNodeIdent = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getNodeIdent", "(I)I");
      il.append(classGen.loadTranslet());
      il.append((CompoundInstruction)(new PUSH(cpg, this._index)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(getKeyIndex)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
      il.append((CompoundInstruction)(new PUSH(cpg, this._value)));
      if (this instanceof IdPattern) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(lookupId)));
      } else {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(lookupKey)));
      }

      this._trueList.add(il.append((BranchInstruction)(new IFNE((InstructionHandle)null))));
      this._falseList.add(il.append((BranchInstruction)(new GOTO((InstructionHandle)null))));
   }
}
