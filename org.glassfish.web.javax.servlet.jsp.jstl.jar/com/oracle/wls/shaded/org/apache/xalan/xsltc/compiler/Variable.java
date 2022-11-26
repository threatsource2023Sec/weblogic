package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Field;
import com.oracle.wls.shaded.org.apache.bcel.generic.ACONST_NULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.DCONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.ICONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.BooleanType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.IntType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.RealType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

final class Variable extends VariableBase {
   public int getIndex() {
      return this._local != null ? this._local.getIndex() : -1;
   }

   public void parseContents(Parser parser) {
      super.parseContents(parser);
      SyntaxTreeNode parent = this.getParent();
      if (parent instanceof Stylesheet) {
         this._isLocal = false;
         Variable var = parser.getSymbolTable().lookupVariable(this._name);
         if (var != null) {
            int us = this.getImportPrecedence();
            int them = var.getImportPrecedence();
            if (us == them) {
               String name = this._name.toString();
               this.reportError(this, parser, "VARIABLE_REDEF_ERR", name);
            } else {
               if (them > us) {
                  this._ignore = true;
                  return;
               }

               var.disable();
            }
         }

         ((Stylesheet)parent).addVariable(this);
         parser.getSymbolTable().addVariable(this);
      } else {
         this._isLocal = true;
      }

   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._select != null) {
         this._type = this._select.typeCheck(stable);
      } else if (this.hasContents()) {
         this.typeCheckContents(stable);
         this._type = Type.ResultTree;
      } else {
         this._type = Type.Reference;
      }

      return Type.Void;
   }

   public void initialize(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (this.isLocal() && !this._refs.isEmpty()) {
         if (this._local == null) {
            this._local = methodGen.addLocalVariable2(this.getEscapedName(), this._type.toJCType(), (InstructionHandle)null);
         }

         if (!(this._type instanceof IntType) && !(this._type instanceof NodeType) && !(this._type instanceof BooleanType)) {
            if (this._type instanceof RealType) {
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new DCONST(0.0)));
            } else {
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ACONST_NULL()));
            }
         } else {
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ICONST(0)));
         }

         this._local.setStart(il.append(this._type.STORE(this._local.getIndex())));
      }

   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (this._refs.isEmpty()) {
         this._ignore = true;
      }

      if (!this._ignore) {
         this._ignore = true;
         String name = this.getEscapedName();
         if (this.isLocal()) {
            this.translateValue(classGen, methodGen);
            boolean createLocal = this._local == null;
            if (createLocal) {
               this.mapRegister(methodGen);
            }

            InstructionHandle storeInst = il.append(this._type.STORE(this._local.getIndex()));
            if (createLocal) {
               this._local.setStart(storeInst);
            }
         } else {
            String signature = this._type.toSignature();
            if (classGen.containsField(name) == null) {
               classGen.addField(new Field(1, cpg.addUtf8(name), cpg.addUtf8(signature), (com.oracle.wls.shaded.org.apache.bcel.classfile.Attribute[])null, cpg.getConstantPool()));
               il.append(classGen.loadTranslet());
               this.translateValue(classGen, methodGen);
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref(classGen.getClassName(), name, signature))));
            }
         }

      }
   }
}
