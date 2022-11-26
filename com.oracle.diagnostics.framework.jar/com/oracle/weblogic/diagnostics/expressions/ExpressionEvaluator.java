package com.oracle.weblogic.diagnostics.expressions;

import java.util.Map;

public interface ExpressionEvaluator {
   Map getResolvedValues();

   Object evaluate(String var1, Class var2);

   DiagnosticsELContext getELContext();

   Class getType(String var1);
}
