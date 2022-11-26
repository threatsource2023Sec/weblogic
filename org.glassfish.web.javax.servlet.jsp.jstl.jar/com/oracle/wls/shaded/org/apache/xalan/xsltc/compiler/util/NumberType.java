package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

public abstract class NumberType extends Type {
   public boolean isNumber() {
      return true;
   }

   public boolean isSimple() {
      return true;
   }
}
