package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ClassGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;

public final class AttributeSetMethodGenerator extends MethodGenerator {
   private static final int DOM_INDEX = 1;
   private static final int ITERATOR_INDEX = 2;
   private static final int HANDLER_INDEX = 3;
   private static final com.oracle.wls.shaded.org.apache.bcel.generic.Type[] argTypes = new com.oracle.wls.shaded.org.apache.bcel.generic.Type[3];
   private static final String[] argNames = new String[3];
   private final Instruction _aloadDom = new ALOAD(1);
   private final Instruction _astoreDom = new ASTORE(1);
   private final Instruction _astoreIterator = new ASTORE(2);
   private final Instruction _aloadIterator = new ALOAD(2);
   private final Instruction _astoreHandler = new ASTORE(3);
   private final Instruction _aloadHandler = new ALOAD(3);

   public AttributeSetMethodGenerator(String methodName, ClassGen classGen) {
      super(2, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, argTypes, argNames, methodName, classGen.getClassName(), new InstructionList(), classGen.getConstantPool());
   }

   public Instruction storeIterator() {
      return this._astoreIterator;
   }

   public Instruction loadIterator() {
      return this._aloadIterator;
   }

   public int getIteratorIndex() {
      return 2;
   }

   public Instruction storeHandler() {
      return this._astoreHandler;
   }

   public Instruction loadHandler() {
      return this._aloadHandler;
   }

   public int getLocalIndex(String name) {
      return -1;
   }

   static {
      argTypes[0] = Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;");
      argNames[0] = "dom";
      argTypes[1] = Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xml/dtm/DTMAxisIterator;");
      argNames[1] = "iterator";
      argTypes[2] = Util.getJCRefType(TRANSLET_OUTPUT_SIG);
      argNames[2] = "handler";
   }
}
