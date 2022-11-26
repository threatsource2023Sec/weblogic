package org.apache.taglibs.standard.lang.jstl;

import java.util.Map;

public abstract class Literal extends Expression {
   Object mValue;

   public Object getValue() {
      return this.mValue;
   }

   public void setValue(Object pValue) {
      this.mValue = pValue;
   }

   public Literal(Object pValue) {
      this.mValue = pValue;
   }

   public Object evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      return this.mValue;
   }
}
