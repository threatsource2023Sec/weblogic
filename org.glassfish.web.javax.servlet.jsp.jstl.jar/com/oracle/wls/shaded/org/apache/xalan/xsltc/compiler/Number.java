package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Field;
import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CHECKCAST;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNONNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MatchGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.NodeCounterGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.RealType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import java.util.ArrayList;

final class Number extends Instruction implements Closure {
   private static final int LEVEL_SINGLE = 0;
   private static final int LEVEL_MULTIPLE = 1;
   private static final int LEVEL_ANY = 2;
   private static final String[] ClassNames = new String[]{"com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.SingleNodeCounter", "com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.MultipleNodeCounter", "com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.AnyNodeCounter"};
   private static final String[] FieldNames = new String[]{"___single_node_counter", "___multiple_node_counter", "___any_node_counter"};
   private Pattern _from = null;
   private Pattern _count = null;
   private Expression _value = null;
   private AttributeValueTemplate _lang = null;
   private AttributeValueTemplate _format = null;
   private AttributeValueTemplate _letterValue = null;
   private AttributeValueTemplate _groupingSeparator = null;
   private AttributeValueTemplate _groupingSize = null;
   private int _level = 0;
   private boolean _formatNeeded = false;
   private String _className = null;
   private ArrayList _closureVars = null;

   public boolean inInnerClass() {
      return this._className != null;
   }

   public Closure getParentClosure() {
      return null;
   }

   public String getInnerClassName() {
      return this._className;
   }

   public void addVariable(VariableRefBase variableRef) {
      if (this._closureVars == null) {
         this._closureVars = new ArrayList();
      }

      if (!this._closureVars.contains(variableRef)) {
         this._closureVars.add(variableRef);
      }

   }

   public void parseContents(Parser parser) {
      int count = this._attributes.getLength();

      for(int i = 0; i < count; ++i) {
         String name = this._attributes.getQName(i);
         String value = this._attributes.getValue(i);
         if (name.equals("value")) {
            this._value = parser.parseExpression(this, name, (String)null);
         } else if (name.equals("count")) {
            this._count = parser.parsePattern(this, name, (String)null);
         } else if (name.equals("from")) {
            this._from = parser.parsePattern(this, name, (String)null);
         } else if (name.equals("level")) {
            if (value.equals("single")) {
               this._level = 0;
            } else if (value.equals("multiple")) {
               this._level = 1;
            } else if (value.equals("any")) {
               this._level = 2;
            }
         } else if (name.equals("format")) {
            this._format = new AttributeValueTemplate(value, parser, this);
            this._formatNeeded = true;
         } else if (name.equals("lang")) {
            this._lang = new AttributeValueTemplate(value, parser, this);
            this._formatNeeded = true;
         } else if (name.equals("letter-value")) {
            this._letterValue = new AttributeValueTemplate(value, parser, this);
            this._formatNeeded = true;
         } else if (name.equals("grouping-separator")) {
            this._groupingSeparator = new AttributeValueTemplate(value, parser, this);
            this._formatNeeded = true;
         } else if (name.equals("grouping-size")) {
            this._groupingSize = new AttributeValueTemplate(value, parser, this);
            this._formatNeeded = true;
         }
      }

   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._value != null) {
         Type tvalue = this._value.typeCheck(stable);
         if (!(tvalue instanceof RealType)) {
            this._value = new CastExpr(this._value, Type.Real);
         }
      }

      if (this._count != null) {
         this._count.typeCheck(stable);
      }

      if (this._from != null) {
         this._from.typeCheck(stable);
      }

      if (this._format != null) {
         this._format.typeCheck(stable);
      }

      if (this._lang != null) {
         this._lang.typeCheck(stable);
      }

      if (this._letterValue != null) {
         this._letterValue.typeCheck(stable);
      }

      if (this._groupingSeparator != null) {
         this._groupingSeparator.typeCheck(stable);
      }

      if (this._groupingSize != null) {
         this._groupingSize.typeCheck(stable);
      }

      return Type.Void;
   }

   public boolean hasValue() {
      return this._value != null;
   }

   public boolean isDefault() {
      return this._from == null && this._count == null;
   }

   private void compileDefault(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int[] fieldIndexes = this.getXSLTC().getNumberFieldIndexes();
      if (fieldIndexes[this._level] == -1) {
         Field defaultNode = new Field(2, cpg.addUtf8(FieldNames[this._level]), cpg.addUtf8("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/dom/NodeCounter;"), (com.oracle.wls.shaded.org.apache.bcel.classfile.Attribute[])null, cpg.getConstantPool());
         classGen.addField(defaultNode);
         fieldIndexes[this._level] = cpg.addFieldref(classGen.getClassName(), FieldNames[this._level], "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/dom/NodeCounter;");
      }

      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(fieldIndexes[this._level])));
      BranchHandle ifBlock1 = il.append((BranchInstruction)(new IFNONNULL((InstructionHandle)null)));
      int index = cpg.addMethodref(ClassNames[this._level], "getDefaultNodeCounter", "(Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/apache/xalan/xsltc/dom/NodeCounter;");
      il.append(classGen.loadTranslet());
      il.append(methodGen.loadDOM());
      il.append(methodGen.loadIterator());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(index)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      il.append(classGen.loadTranslet());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(fieldIndexes[this._level])));
      BranchHandle ifBlock2 = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      ifBlock1.setTarget(il.append(classGen.loadTranslet()));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(fieldIndexes[this._level])));
      ifBlock2.setTarget(il.append(NOP));
   }

   private void compileConstructor(ClassGenerator classGen) {
      InstructionList il = new InstructionList();
      ConstantPoolGen cpg = classGen.getConstantPool();
      MethodGenerator cons = new MethodGenerator(1, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/Translet;"), Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;")}, new String[]{"dom", "translet", "iterator"}, "<init>", this._className, il, cpg);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_0);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_1);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_2);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(3)));
      int index = cpg.addMethodref(ClassNames[this._level], "<init>", "(Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(index)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)RETURN);
      classGen.addMethod(cons);
   }

   private void compileLocals(NodeCounterGenerator nodeCounterGen, MatchGenerator matchGen, InstructionList il) {
      ConstantPoolGen cpg = nodeCounterGen.getConstantPool();
      LocalVariableGen local = matchGen.addLocalVariable("iterator", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
      int field = cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "_iterator", "Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_0);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(field)));
      local.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(local.getIndex()))));
      matchGen.setIteratorIndex(local.getIndex());
      local = matchGen.addLocalVariable("translet", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/runtime/AbstractTranslet;"), (InstructionHandle)null, (InstructionHandle)null);
      field = cpg.addFieldref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "_translet", "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/Translet;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_0);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(field)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new CHECKCAST(cpg.addClass("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet"))));
      local.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(local.getIndex()))));
      nodeCounterGen.setTransletIndex(local.getIndex());
      local = matchGen.addLocalVariable("document", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), (InstructionHandle)null, (InstructionHandle)null);
      field = cpg.addFieldref(this._className, "_document", "Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)ALOAD_0);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(field)));
      local.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(local.getIndex()))));
      matchGen.setDomIndex(local.getIndex());
   }

   private void compilePatterns(ClassGenerator classGen, MethodGenerator methodGen) {
      this._className = this.getXSLTC().getHelperClassName();
      NodeCounterGenerator nodeCounterGen = new NodeCounterGenerator(this._className, ClassNames[this._level], this.toString(), 33, (String[])null, classGen.getStylesheet());
      InstructionList il = null;
      ConstantPoolGen cpg = nodeCounterGen.getConstantPool();
      int closureLen = this._closureVars == null ? 0 : this._closureVars.size();

      int index;
      for(index = 0; index < closureLen; ++index) {
         VariableBase var = ((VariableRefBase)this._closureVars.get(index)).getVariable();
         nodeCounterGen.addField(new Field(1, cpg.addUtf8(var.getEscapedName()), cpg.addUtf8(var.getType().toSignature()), (com.oracle.wls.shaded.org.apache.bcel.classfile.Attribute[])null, cpg.getConstantPool()));
      }

      this.compileConstructor(nodeCounterGen);
      MatchGenerator matchGen;
      if (this._from != null) {
         il = new InstructionList();
         matchGen = new MatchGenerator(17, com.oracle.wls.shaded.org.apache.bcel.generic.Type.BOOLEAN, new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT}, new String[]{"node"}, "matchesFrom", this._className, il, cpg);
         this.compileLocals(nodeCounterGen, matchGen, il);
         il.append(matchGen.loadContextNode());
         this._from.translate(nodeCounterGen, matchGen);
         this._from.synthesize(nodeCounterGen, matchGen);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IRETURN);
         nodeCounterGen.addMethod(matchGen);
      }

      if (this._count != null) {
         il = new InstructionList();
         matchGen = new MatchGenerator(17, com.oracle.wls.shaded.org.apache.bcel.generic.Type.BOOLEAN, new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT}, new String[]{"node"}, "matchesCount", this._className, il, cpg);
         this.compileLocals(nodeCounterGen, matchGen, il);
         il.append(matchGen.loadContextNode());
         this._count.translate(nodeCounterGen, matchGen);
         this._count.synthesize(nodeCounterGen, matchGen);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IRETURN);
         nodeCounterGen.addMethod(matchGen);
      }

      this.getXSLTC().dumpClass(nodeCounterGen.getJavaClass());
      cpg = classGen.getConstantPool();
      il = methodGen.getInstructionList();
      index = cpg.addMethodref(this._className, "<init>", "(Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass(this._className))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      il.append(classGen.loadTranslet());
      il.append(methodGen.loadDOM());
      il.append(methodGen.loadIterator());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(index)));

      for(int i = 0; i < closureLen; ++i) {
         VariableRefBase varRef = (VariableRefBase)this._closureVars.get(i);
         VariableBase var = varRef.getVariable();
         Type varType = var.getType();
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         il.append(var.loadInstruction());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(cpg.addFieldref(this._className, var.getEscapedName(), varType.toSignature()))));
      }

   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append(classGen.loadTranslet());
      int index;
      if (this.hasValue()) {
         this.compileDefault(classGen, methodGen);
         this._value.translate(classGen, methodGen);
         il.append((CompoundInstruction)(new PUSH(cpg, 0.5)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DADD);
         index = cpg.addMethodref("java.lang.Math", "floor", "(D)D");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(index)));
         index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "setValue", "(D)Lorg/apache/xalan/xsltc/dom/NodeCounter;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
      } else if (this.isDefault()) {
         this.compileDefault(classGen, methodGen);
      } else {
         this.compilePatterns(classGen, methodGen);
      }

      if (!this.hasValue()) {
         il.append(methodGen.loadContextNode());
         index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "setStartNode", "(I)Lorg/apache/xalan/xsltc/dom/NodeCounter;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
      }

      if (this._formatNeeded) {
         if (this._format != null) {
            this._format.translate(classGen, methodGen);
         } else {
            il.append((CompoundInstruction)(new PUSH(cpg, "1")));
         }

         if (this._lang != null) {
            this._lang.translate(classGen, methodGen);
         } else {
            il.append((CompoundInstruction)(new PUSH(cpg, "en")));
         }

         if (this._letterValue != null) {
            this._letterValue.translate(classGen, methodGen);
         } else {
            il.append((CompoundInstruction)(new PUSH(cpg, "")));
         }

         if (this._groupingSeparator != null) {
            this._groupingSeparator.translate(classGen, methodGen);
         } else {
            il.append((CompoundInstruction)(new PUSH(cpg, "")));
         }

         if (this._groupingSize != null) {
            this._groupingSize.translate(classGen, methodGen);
         } else {
            il.append((CompoundInstruction)(new PUSH(cpg, "0")));
         }

         index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "getCounter", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
      } else {
         index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "setDefaultFormatting", "()Lorg/apache/xalan/xsltc/dom/NodeCounter;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
         index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.NodeCounter", "getCounter", "()Ljava/lang/String;");
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
      }

      il.append(methodGen.loadHandler());
      index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.AbstractTranslet", "characters", CHARACTERSW_SIG);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(index)));
   }
}
