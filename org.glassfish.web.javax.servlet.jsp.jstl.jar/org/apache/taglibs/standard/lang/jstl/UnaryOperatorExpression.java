package org.apache.taglibs.standard.lang.jstl;

import java.util.List;
import java.util.Map;

public class UnaryOperatorExpression extends Expression {
   UnaryOperator mOperator;
   List mOperators;
   Expression mExpression;

   public UnaryOperator getOperator() {
      return this.mOperator;
   }

   public void setOperator(UnaryOperator pOperator) {
      this.mOperator = pOperator;
   }

   public List getOperators() {
      return this.mOperators;
   }

   public void setOperators(List pOperators) {
      this.mOperators = pOperators;
   }

   public Expression getExpression() {
      return this.mExpression;
   }

   public void setExpression(Expression pExpression) {
      this.mExpression = pExpression;
   }

   public UnaryOperatorExpression(UnaryOperator pOperator, List pOperators, Expression pExpression) {
      this.mOperator = pOperator;
      this.mOperators = pOperators;
      this.mExpression = pExpression;
   }

   public String getExpressionString() {
      StringBuffer buf = new StringBuffer();
      buf.append("(");
      if (this.mOperator != null) {
         buf.append(this.mOperator.getOperatorSymbol());
         buf.append(" ");
      } else {
         for(int i = 0; i < this.mOperators.size(); ++i) {
            UnaryOperator operator = (UnaryOperator)this.mOperators.get(i);
            buf.append(operator.getOperatorSymbol());
            buf.append(" ");
         }
      }

      buf.append(this.mExpression.getExpressionString());
      buf.append(")");
      return buf.toString();
   }

   public Object evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      Object value = this.mExpression.evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);
      if (this.mOperator != null) {
         value = this.mOperator.apply(value, pContext, pLogger);
      } else {
         for(int i = this.mOperators.size() - 1; i >= 0; --i) {
            UnaryOperator operator = (UnaryOperator)this.mOperators.get(i);
            value = operator.apply(value, pContext, pLogger);
         }
      }

      return value;
   }
}
