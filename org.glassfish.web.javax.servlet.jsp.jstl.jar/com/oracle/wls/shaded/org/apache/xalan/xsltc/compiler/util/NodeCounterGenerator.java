package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Stylesheet;

public final class NodeCounterGenerator extends ClassGenerator {
   private Instruction _aloadTranslet;

   public NodeCounterGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet) {
      super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
   }

   public void setTransletIndex(int index) {
      this._aloadTranslet = new ALOAD(index);
   }

   public Instruction loadTranslet() {
      return this._aloadTranslet;
   }

   public boolean isExternal() {
      return true;
   }
}
