package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Stylesheet;

public final class NodeSortRecordGenerator extends ClassGenerator {
   private static final int TRANSLET_INDEX = 4;
   private final Instruction _aloadTranslet = new ALOAD(4);

   public NodeSortRecordGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet) {
      super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
   }

   public Instruction loadTranslet() {
      return this._aloadTranslet;
   }

   public boolean isExternal() {
      return true;
   }
}
