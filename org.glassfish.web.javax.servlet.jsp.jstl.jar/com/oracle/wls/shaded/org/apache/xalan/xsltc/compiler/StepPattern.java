package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Field;
import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO_W;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFLT;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNONNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPLT;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import com.oracle.wls.shaded.org.apache.xml.dtm.Axis;
import java.util.Vector;

class StepPattern extends RelativePathPattern {
   private static final int NO_CONTEXT = 0;
   private static final int SIMPLE_CONTEXT = 1;
   private static final int GENERAL_CONTEXT = 2;
   protected final int _axis;
   protected final int _nodeType;
   protected Vector _predicates;
   private Step _step = null;
   private boolean _isEpsilon = false;
   private int _contextCase;
   private double _priority = Double.MAX_VALUE;

   public StepPattern(int axis, int nodeType, Vector predicates) {
      this._axis = axis;
      this._nodeType = nodeType;
      this._predicates = predicates;
   }

   public void setParser(Parser parser) {
      super.setParser(parser);
      if (this._predicates != null) {
         int n = this._predicates.size();

         for(int i = 0; i < n; ++i) {
            Predicate exp = (Predicate)this._predicates.elementAt(i);
            exp.setParser(parser);
            exp.setParent(this);
         }
      }

   }

   public int getNodeType() {
      return this._nodeType;
   }

   public void setPriority(double priority) {
      this._priority = priority;
   }

   public StepPattern getKernelPattern() {
      return this;
   }

   public boolean isWildcard() {
      return this._isEpsilon && !this.hasPredicates();
   }

   public StepPattern setPredicates(Vector predicates) {
      this._predicates = predicates;
      return this;
   }

   protected boolean hasPredicates() {
      return this._predicates != null && this._predicates.size() > 0;
   }

   public double getDefaultPriority() {
      if (this._priority != Double.MAX_VALUE) {
         return this._priority;
      } else if (this.hasPredicates()) {
         return 0.5;
      } else {
         switch (this._nodeType) {
            case -1:
               return -0.5;
            case 0:
               return 0.0;
            default:
               return this._nodeType >= 14 ? 0.0 : -0.5;
         }
      }
   }

   public int getAxis() {
      return this._axis;
   }

   public void reduceKernelPattern() {
      this._isEpsilon = true;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer("stepPattern(\"");
      buffer.append(Axis.getNames(this._axis)).append("\", ").append(this._isEpsilon ? "epsilon{" + Integer.toString(this._nodeType) + "}" : Integer.toString(this._nodeType));
      if (this._predicates != null) {
         buffer.append(", ").append(this._predicates.toString());
      }

      return buffer.append(')').toString();
   }

   private int analyzeCases() {
      boolean noContext = true;
      int n = this._predicates.size();

      for(int i = 0; i < n && noContext; ++i) {
         Predicate pred = (Predicate)this._predicates.elementAt(i);
         if (pred.isNthPositionFilter() || pred.hasPositionCall() || pred.hasLastCall()) {
            noContext = false;
         }
      }

      if (noContext) {
         return 0;
      } else {
         return n == 1 ? 1 : 2;
      }
   }

   private String getNextFieldName() {
      return "__step_pattern_iter_" + this.getXSLTC().nextStepPatternSerial();
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this.hasPredicates()) {
         int n = this._predicates.size();

         Predicate pred;
         for(int i = 0; i < n; ++i) {
            pred = (Predicate)this._predicates.elementAt(i);
            pred.typeCheck(stable);
         }

         this._contextCase = this.analyzeCases();
         Step step = null;
         if (this._contextCase == 1) {
            pred = (Predicate)this._predicates.elementAt(0);
            if (pred.isNthPositionFilter()) {
               this._contextCase = 2;
               step = new Step(this._axis, this._nodeType, this._predicates);
            } else {
               step = new Step(this._axis, this._nodeType, (Vector)null);
            }
         } else if (this._contextCase == 2) {
            int len = this._predicates.size();

            for(int i = 0; i < len; ++i) {
               ((Predicate)this._predicates.elementAt(i)).dontOptimize();
            }

            step = new Step(this._axis, this._nodeType, this._predicates);
         }

         if (step != null) {
            step.setParser(this.getParser());
            step.typeCheck(stable);
            this._step = step;
         }
      }

      return this._axis == 3 ? Type.Element : Type.Attribute;
   }

   private void translateKernel(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int check;
      BranchHandle icmp;
      if (this._nodeType == 1) {
         check = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "isElement", "(I)Z");
         il.append(methodGen.loadDOM());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(check, 2)));
         icmp = il.append((BranchInstruction)(new IFNE((InstructionHandle)null)));
         this._falseList.add(il.append((BranchInstruction)(new GOTO_W((InstructionHandle)null))));
         icmp.setTarget(il.append(NOP));
      } else if (this._nodeType == 2) {
         check = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "isAttribute", "(I)Z");
         il.append(methodGen.loadDOM());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(check, 2)));
         icmp = il.append((BranchInstruction)(new IFNE((InstructionHandle)null)));
         this._falseList.add(il.append((BranchInstruction)(new GOTO_W((InstructionHandle)null))));
         icmp.setTarget(il.append(NOP));
      } else {
         check = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
         il.append(methodGen.loadDOM());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(check, 2)));
         il.append((CompoundInstruction)(new PUSH(cpg, this._nodeType)));
         icmp = il.append((BranchInstruction)(new IF_ICMPEQ((InstructionHandle)null)));
         this._falseList.add(il.append((BranchInstruction)(new GOTO_W((InstructionHandle)null))));
         icmp.setTarget(il.append(NOP));
      }

   }

   private void translateNoContext(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append(methodGen.loadCurrentNode());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
      il.append(methodGen.storeCurrentNode());
      if (!this._isEpsilon) {
         il.append(methodGen.loadCurrentNode());
         this.translateKernel(classGen, methodGen);
      }

      int n = this._predicates.size();

      for(int i = 0; i < n; ++i) {
         Predicate pred = (Predicate)this._predicates.elementAt(i);
         Expression exp = pred.getExpr();
         exp.translateDesynthesized(classGen, methodGen);
         this._trueList.append(exp._trueList);
         this._falseList.append(exp._falseList);
      }

      InstructionHandle restore = il.append(methodGen.storeCurrentNode());
      this.backPatchTrueList(restore);
      BranchHandle skipFalse = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      restore = il.append(methodGen.storeCurrentNode());
      this.backPatchFalseList(restore);
      this._falseList.add(il.append((BranchInstruction)(new GOTO((InstructionHandle)null))));
      skipFalse.setTarget(il.append(NOP));
   }

   private void translateSimpleContext(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      LocalVariableGen match = methodGen.addLocalVariable("step_pattern_tmp1", Util.getJCRefType("I"), (InstructionHandle)null, (InstructionHandle)null);
      match.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ISTORE(match.getIndex()))));
      if (!this._isEpsilon) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(match.getIndex())));
         this.translateKernel(classGen, methodGen);
      }

      il.append(methodGen.loadCurrentNode());
      il.append(methodGen.loadIterator());
      int index = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.MatchingIterator", "<init>", "(ILorg/apache/xml/dtm/DTMAxisIterator;)V");
      this._step.translate(classGen, methodGen);
      LocalVariableGen stepIteratorTemp = methodGen.addLocalVariable("step_pattern_tmp2", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
      stepIteratorTemp.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(stepIteratorTemp.getIndex()))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new NEW(cpg.addClass("com.oracle.wls.shaded.org.apache.xalan.xsltc.dom.MatchingIterator"))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(match.getIndex())));
      stepIteratorTemp.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(stepIteratorTemp.getIndex()))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESPECIAL(index)));
      il.append(methodGen.loadDOM());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(match.getIndex())));
      index = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(index, 2)));
      il.append(methodGen.setStartNode());
      il.append(methodGen.storeIterator());
      match.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(match.getIndex()))));
      il.append(methodGen.storeCurrentNode());
      Predicate pred = (Predicate)this._predicates.elementAt(0);
      Expression exp = pred.getExpr();
      exp.translateDesynthesized(classGen, methodGen);
      InstructionHandle restore = il.append(methodGen.storeIterator());
      il.append(methodGen.storeCurrentNode());
      exp.backPatchTrueList(restore);
      BranchHandle skipFalse = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      restore = il.append(methodGen.storeIterator());
      il.append(methodGen.storeCurrentNode());
      exp.backPatchFalseList(restore);
      this._falseList.add(il.append((BranchInstruction)(new GOTO((InstructionHandle)null))));
      skipFalse.setTarget(il.append(NOP));
   }

   private void translateGeneralContext(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int iteratorIndex = 0;
      BranchHandle ifBlock = null;
      String iteratorName = this.getNextFieldName();
      LocalVariableGen node = methodGen.addLocalVariable("step_pattern_tmp1", Util.getJCRefType("I"), (InstructionHandle)null, (InstructionHandle)null);
      node.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ISTORE(node.getIndex()))));
      LocalVariableGen iter = methodGen.addLocalVariable("step_pattern_tmp2", Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
      if (!classGen.isExternal()) {
         Field iterator = new Field(2, cpg.addUtf8(iteratorName), cpg.addUtf8("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;"), (com.oracle.wls.shaded.org.apache.bcel.classfile.Attribute[])null, cpg.getConstantPool());
         classGen.addField(iterator);
         iteratorIndex = cpg.addFieldref(classGen.getClassName(), iteratorName, "Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;");
         il.append(classGen.loadTranslet());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new GETFIELD(iteratorIndex)));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
         iter.setStart(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(iter.getIndex()))));
         ifBlock = il.append((BranchInstruction)(new IFNONNULL((InstructionHandle)null)));
         il.append(classGen.loadTranslet());
      }

      this._step.translate(classGen, methodGen);
      InstructionHandle iterStore = il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ASTORE(iter.getIndex())));
      if (!classGen.isExternal()) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(iter.getIndex())));
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new PUTFIELD(iteratorIndex)));
         ifBlock.setTarget(il.append(NOP));
      } else {
         iter.setStart(iterStore);
      }

      il.append(methodGen.loadDOM());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(node.getIndex())));
      int index = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(index, 2)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(iter.getIndex())));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)SWAP);
      il.append(methodGen.setStartNode());
      LocalVariableGen node2 = methodGen.addLocalVariable("step_pattern_tmp3", Util.getJCRefType("I"), (InstructionHandle)null, (InstructionHandle)null);
      BranchHandle skipNext = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      InstructionHandle next = il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(iter.getIndex())));
      node2.setStart(next);
      InstructionHandle begin = il.append(methodGen.nextNode());
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ISTORE(node2.getIndex())));
      this._falseList.add(il.append((BranchInstruction)(new IFLT((InstructionHandle)null))));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(node2.getIndex())));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(node.getIndex())));
      iter.setEnd(il.append((BranchInstruction)(new IF_ICMPLT(next))));
      node2.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(node2.getIndex()))));
      node.setEnd(il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(node.getIndex()))));
      this._falseList.add(il.append((BranchInstruction)(new IF_ICMPNE((InstructionHandle)null))));
      skipNext.setTarget(begin);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (this.hasPredicates()) {
         switch (this._contextCase) {
            case 0:
               this.translateNoContext(classGen, methodGen);
               break;
            case 1:
               this.translateSimpleContext(classGen, methodGen);
               break;
            default:
               this.translateGeneralContext(classGen, methodGen);
         }
      } else if (this.isWildcard()) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)POP);
      } else {
         this.translateKernel(classGen, methodGen);
      }

   }
}
