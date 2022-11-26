package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Field;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CHECKCAST;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNONNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ObjectType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ReferenceType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary;

final class Param extends VariableBase {
   private boolean _isInSimpleNamedTemplate = false;

   public String toString() {
      return "param(" + this._name + ")";
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Instruction setLoadInstruction(com.oracle.wls.shaded.org.apache.bcel.generic.Instruction instruction) {
      com.oracle.wls.shaded.org.apache.bcel.generic.Instruction tmp = this._loadInstruction;
      this._loadInstruction = instruction;
      return tmp;
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Instruction setStoreInstruction(com.oracle.wls.shaded.org.apache.bcel.generic.Instruction instruction) {
      com.oracle.wls.shaded.org.apache.bcel.generic.Instruction tmp = this._storeInstruction;
      this._storeInstruction = instruction;
      return tmp;
   }

   public void display(int indent) {
      this.indent(indent);
      System.out.println("param " + this._name);
      if (this._select != null) {
         this.indent(indent + 4);
         System.out.println("select " + this._select.toString());
      }

      this.displayContents(indent + 4);
   }

   public void parseContents(Parser parser) {
      super.parseContents(parser);
      SyntaxTreeNode parent = this.getParent();
      if (parent instanceof Stylesheet) {
         this._isLocal = false;
         Param param = parser.getSymbolTable().lookupParam(this._name);
         if (param != null) {
            int us = this.getImportPrecedence();
            int them = param.getImportPrecedence();
            if (us == them) {
               String name = this._name.toString();
               this.reportError(this, parser, "VARIABLE_REDEF_ERR", name);
            } else {
               if (them > us) {
                  this._ignore = true;
                  return;
               }

               param.disable();
            }
         }

         ((Stylesheet)parent).addParam(this);
         parser.getSymbolTable().addParam(this);
      } else if (parent instanceof Template) {
         Template template = (Template)parent;
         this._isLocal = true;
         template.addParameter(this);
         if (template.isSimpleNamedTemplate()) {
            this._isInSimpleNamedTemplate = true;
         }
      }

   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._select != null) {
         this._type = this._select.typeCheck(stable);
         if (!(this._type instanceof ReferenceType) && !(this._type instanceof ObjectType)) {
            this._select = new CastExpr(this._select, Type.Reference);
         }
      } else if (this.hasContents()) {
         this.typeCheckContents(stable);
      }

      this._type = Type.Reference;
      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (!this._ignore) {
         this._ignore = true;
         String name = BasisLibrary.mapQNameToJavaName(this._name.toString());
         String signature = this._type.toSignature();
         String className = this._type.getClassName();
         if (this.isLocal()) {
            if (this._isInSimpleNamedTemplate) {
               il.append(this.loadInstruction());
               BranchHandle ifBlock = il.append((BranchInstruction)(new IFNONNULL((InstructionHandle)null)));
               this.translateValue(classGen, methodGen);
               il.append(this.storeInstruction());
               ifBlock.setTarget(il.append(NOP));
               return;
            }

            il.append(classGen.loadTranslet());
            il.append((CompoundInstruction)(new PUSH(cpg, name)));
            this.translateValue(classGen, methodGen);
            il.append((CompoundInstruction)(new PUSH(cpg, true)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "addParameter", "(Ljava/lang/String;Ljava/lang/Object;Z)Ljava/lang/Object;"))));
            if (className != "") {
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new CHECKCAST(cpg.addClass(className))));
            }

            this._type.translateUnBox(classGen, methodGen);
            if (this._refs.isEmpty()) {
               il.append(this._type.POP());
               this._local = null;
            } else {
               this._local = methodGen.addLocalVariable2(name, this._type.toJCType(), (InstructionHandle)null);
               this._local.setStart(il.append(this._type.STORE(this._local.getIndex())));
            }
         } else if (classGen.containsField(name) == null) {
            classGen.addField(new Field(1, cpg.addUtf8(name), cpg.addUtf8(signature), (com.oracle.wls.shaded.org.apache.bcel.classfile.Attribute[])null, cpg.getConstantPool()));
            il.append(classGen.loadTranslet());
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
            il.append((CompoundInstruction)(new PUSH(cpg, name)));
            this.translateValue(classGen, methodGen);
            il.append((CompoundInstruction)(new PUSH(cpg, true)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "addParameter", "(Ljava/lang/String;Ljava/lang/Object;Z)Ljava/lang/Object;"))));
            this._type.translateUnBox(classGen, methodGen);
            if (className != "") {
               il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new CHECKCAST(cpg.addClass(className))));
            }

            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref(classGen.getClassName(), name, signature))));
         }

      }
   }
}
