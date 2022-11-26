package org.apache.taglibs.standard.lang.jstl;

import java.util.Map;

public abstract class Expression {
   public abstract String getExpressionString();

   public abstract Object evaluate(Object var1, VariableResolver var2, Map var3, String var4, Logger var5) throws ELException;
}
