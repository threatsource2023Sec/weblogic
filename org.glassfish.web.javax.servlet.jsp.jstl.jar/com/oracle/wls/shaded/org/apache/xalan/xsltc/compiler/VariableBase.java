package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeSetType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import com.oracle.wls.shaded.org.apache.xml.utils.XML11Char;
import java.util.Vector;

class VariableBase extends TopLevelElement {
   protected QName _name;
   protected String _escapedName;
   protected Type _type;
   protected boolean _isLocal;
   protected LocalVariableGen _local;
   protected com.oracle.wls.shaded.org.apache.bcel.generic.Instruction _loadInstruction;
   protected com.oracle.wls.shaded.org.apache.bcel.generic.Instruction _storeInstruction;
   protected Expression _select;
   protected String select;
   protected Vector _refs = new Vector(2);
   protected Vector _dependencies = null;
   protected boolean _ignore = false;

   public void disable() {
      this._ignore = true;
   }

   public void addReference(VariableRefBase vref) {
      this._refs.addElement(vref);
   }

   public void mapRegister(MethodGenerator methodGen) {
      if (this._local == null) {
         String name = this.getEscapedName();
         com.oracle.wls.shaded.org.apache.bcel.generic.Type varType = this._type.toJCType();
         this._local = methodGen.addLocalVariable2(name, varType, (InstructionHandle)null);
      }

   }

   public void unmapRegister(MethodGenerator methodGen) {
      if (this._local != null) {
         this._local.setEnd(methodGen.getInstructionList().getEnd());
         methodGen.removeLocalVariable(this._local);
         this._refs = null;
         this._local = null;
      }

   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Instruction loadInstruction() {
      com.oracle.wls.shaded.org.apache.bcel.generic.Instruction instr = this._loadInstruction;
      if (this._loadInstruction == null) {
         this._loadInstruction = this._type.LOAD(this._local.getIndex());
      }

      return this._loadInstruction;
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Instruction storeInstruction() {
      com.oracle.wls.shaded.org.apache.bcel.generic.Instruction instr = this._storeInstruction;
      if (this._storeInstruction == null) {
         this._storeInstruction = this._type.STORE(this._local.getIndex());
      }

      return this._storeInstruction;
   }

   public Expression getExpression() {
      return this._select;
   }

   public String toString() {
      return "variable(" + this._name + ")";
   }

   public void display(int indent) {
      this.indent(indent);
      System.out.println("Variable " + this._name);
      if (this._select != null) {
         this.indent(indent + 4);
         System.out.println("select " + this._select.toString());
      }

      this.displayContents(indent + 4);
   }

   public Type getType() {
      return this._type;
   }

   public QName getName() {
      return this._name;
   }

   public String getEscapedName() {
      return this._escapedName;
   }

   public void setName(QName name) {
      this._name = name;
      this._escapedName = Util.escape(name.getStringRep());
   }

   public boolean isLocal() {
      return this._isLocal;
   }

   public void parseContents(Parser parser) {
      String name = this.getAttribute("name");
      if (name.length() > 0) {
         if (!XML11Char.isXML11ValidQName(name)) {
            ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
            parser.reportError(3, err);
         }

         this.setName(parser.getQNameIgnoreDefaultNs(name));
      } else {
         this.reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
      }

      VariableBase other = parser.lookupVariable(this._name);
      if (other != null && other.getParent() == this.getParent()) {
         this.reportError(this, parser, "VARIABLE_REDEF_ERR", name);
      }

      this.select = this.getAttribute("select");
      if (this.select.length() > 0) {
         this._select = this.getParser().parseExpression(this, "select", (String)null);
         if (this._select.isDummy()) {
            this.reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
            return;
         }
      }

      this.parseChildren(parser);
   }

   public void translateValue(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg;
      InstructionList il;
      if (this._select != null) {
         this._select.translate(classGen, methodGen);
         if (this._select.getType() instanceof NodeSetType) {
            cpg = classGen.getConstantPool();
            il = methodGen.getInstructionList();
            int initCNI = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.CachedNodeListIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;)V");
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.CachedNodeListIterator"))));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP_X1);
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(initCNI)));
         }

         this._select.startIterator(classGen, methodGen);
      } else if (this.hasContents()) {
         this.compileResultTree(classGen, methodGen);
      } else {
         cpg = classGen.getConstantPool();
         il = methodGen.getInstructionList();
         il.append((CompoundInstruction)(new PUSH(cpg, "")));
      }

   }
}
