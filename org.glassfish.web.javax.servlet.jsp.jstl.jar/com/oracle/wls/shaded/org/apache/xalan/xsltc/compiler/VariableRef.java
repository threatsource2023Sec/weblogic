package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CHECKCAST;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeSetType;

final class VariableRef extends VariableRefBase {
   public VariableRef(Variable variable) {
      super(variable);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (!this._type.implementedAsMethod()) {
         String name = this._variable.getEscapedName();
         String signature = this._type.toSignature();
         if (this._variable.isLocal()) {
            if (classGen.isExternal()) {
               Closure variableClosure;
               for(variableClosure = this._closure; variableClosure != null && !variableClosure.inInnerClass(); variableClosure = variableClosure.getParentClosure()) {
               }

               if (variableClosure != null) {
                  il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_0);
                  il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(cpg.addFieldref(variableClosure.getInnerClassName(), name, signature))));
               } else {
                  il.append(this._variable.loadInstruction());
               }
            } else {
               il.append(this._variable.loadInstruction());
            }
         } else {
            String className = classGen.getClassName();
            il.append(classGen.loadTranslet());
            if (classGen.isExternal()) {
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new CHECKCAST(cpg.addClass(className))));
            }

            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(cpg.addFieldref(className, name, signature))));
         }

         if (this._variable.getType() instanceof NodeSetType) {
            int clone = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator", "cloneIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(clone, 1)));
         }

      }
   }
}
