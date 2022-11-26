package org.apache.taglibs.standard.lang.jstl;

import java.util.Map;

public abstract class ValueSuffix {
   public abstract String getExpressionString();

   public abstract Object evaluate(Object var1, Object var2, VariableResolver var3, Map var4, String var5, Logger var6) throws ELException;
}
