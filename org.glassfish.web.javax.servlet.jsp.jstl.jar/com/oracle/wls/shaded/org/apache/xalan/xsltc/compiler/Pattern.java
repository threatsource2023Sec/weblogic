package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

public abstract class Pattern extends Expression {
   public abstract Type typeCheck(SymbolTable var1) throws TypeCheckError;

   public abstract void translate(ClassGenerator var1, MethodGenerator var2);

   public abstract double getPriority();
}
