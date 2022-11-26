package org.apache.taglibs.standard.lang.jstl;

import java.util.Map;

public class ExpressionString {
   Object[] mElements;

   public Object[] getElements() {
      return this.mElements;
   }

   public void setElements(Object[] pElements) {
      this.mElements = pElements;
   }

   public ExpressionString(Object[] pElements) {
      this.mElements = pElements;
   }

   public String evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < this.mElements.length; ++i) {
         Object elem = this.mElements[i];
         if (elem instanceof String) {
            buf.append((String)elem);
         } else if (elem instanceof Expression) {
            Object val = ((Expression)elem).evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);
            if (val != null) {
               buf.append(val.toString());
            }
         }
      }

      return buf.toString();
   }

   public String getExpressionString() {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < this.mElements.length; ++i) {
         Object elem = this.mElements[i];
         if (elem instanceof String) {
            buf.append((String)elem);
         } else if (elem instanceof Expression) {
            buf.append("${");
            buf.append(((Expression)elem).getExpressionString());
            buf.append("}");
         }
      }

      return buf.toString();
   }
}
