package org.apache.taglibs.standard.lang.jstl;

import java.util.Map;

public class PropertySuffix extends ArraySuffix {
   String mName;

   public String getName() {
      return this.mName;
   }

   public void setName(String pName) {
      this.mName = pName;
   }

   public PropertySuffix(String pName) {
      super((Expression)null);
      this.mName = pName;
   }

   Object evaluateIndex(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      return this.mName;
   }

   String getOperatorSymbol() {
      return ".";
   }

   public String getExpressionString() {
      return "." + StringLiteral.toIdentifierToken(this.mName);
   }
}
