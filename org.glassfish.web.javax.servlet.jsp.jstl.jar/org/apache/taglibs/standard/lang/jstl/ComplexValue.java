package org.apache.taglibs.standard.lang.jstl;

import java.util.List;
import java.util.Map;

public class ComplexValue extends Expression {
   Expression mPrefix;
   List mSuffixes;

   public Expression getPrefix() {
      return this.mPrefix;
   }

   public void setPrefix(Expression pPrefix) {
      this.mPrefix = pPrefix;
   }

   public List getSuffixes() {
      return this.mSuffixes;
   }

   public void setSuffixes(List pSuffixes) {
      this.mSuffixes = pSuffixes;
   }

   public ComplexValue(Expression pPrefix, List pSuffixes) {
      this.mPrefix = pPrefix;
      this.mSuffixes = pSuffixes;
   }

   public String getExpressionString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.mPrefix.getExpressionString());

      for(int i = 0; this.mSuffixes != null && i < this.mSuffixes.size(); ++i) {
         ValueSuffix suffix = (ValueSuffix)this.mSuffixes.get(i);
         buf.append(suffix.getExpressionString());
      }

      return buf.toString();
   }

   public Object evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      Object ret = this.mPrefix.evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);

      for(int i = 0; this.mSuffixes != null && i < this.mSuffixes.size(); ++i) {
         ValueSuffix suffix = (ValueSuffix)this.mSuffixes.get(i);
         ret = suffix.evaluate(ret, pContext, pResolver, functions, defaultPrefix, pLogger);
      }

      return ret;
   }
}
