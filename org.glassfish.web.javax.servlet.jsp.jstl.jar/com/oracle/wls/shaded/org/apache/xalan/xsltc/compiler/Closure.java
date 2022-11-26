package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

public interface Closure {
   boolean inInnerClass();

   Closure getParentClosure();

   String getInnerClassName();

   void addVariable(VariableRefBase var1);
}
