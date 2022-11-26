package com.oracle.weblogic.diagnostics.expressions;

public interface FixedExpressionEvaluator extends ExpressionEvaluator {
   Object evaluate();

   String getFixedExpression();
}
