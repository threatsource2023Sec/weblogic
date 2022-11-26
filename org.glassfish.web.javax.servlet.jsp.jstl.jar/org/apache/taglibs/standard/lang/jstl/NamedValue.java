package org.apache.taglibs.standard.lang.jstl;

import java.util.Map;

public class NamedValue extends Expression {
   String mName;

   public String getName() {
      return this.mName;
   }

   public NamedValue(String pName) {
      this.mName = pName;
   }

   public String getExpressionString() {
      return StringLiteral.toIdentifierToken(this.mName);
   }

   public Object evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      return pResolver == null ? null : pResolver.resolveVariable(this.mName, pContext);
   }
}
