package com.oracle.weblogic.diagnostics.expressions;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface EvaluatorFactory {
   FixedExpressionEvaluator createEvaluator(String var1, Class var2, Annotation... var3);

   ExpressionEvaluator createEvaluator(Annotation... var1);

   void destroyEvaluator(ExpressionEvaluator var1);
}
