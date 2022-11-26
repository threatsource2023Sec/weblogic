package org.apache.taglibs.standard.lang.jstl;

import java.util.List;
import java.util.Map;

public class BinaryOperatorExpression extends Expression {
   Expression mExpression;
   List mOperators;
   List mExpressions;

   public Expression getExpression() {
      return this.mExpression;
   }

   public void setExpression(Expression pExpression) {
      this.mExpression = pExpression;
   }

   public List getOperators() {
      return this.mOperators;
   }

   public void setOperators(List pOperators) {
      this.mOperators = pOperators;
   }

   public List getExpressions() {
      return this.mExpressions;
   }

   public void setExpressions(List pExpressions) {
      this.mExpressions = pExpressions;
   }

   public BinaryOperatorExpression(Expression pExpression, List pOperators, List pExpressions) {
      this.mExpression = pExpression;
      this.mOperators = pOperators;
      this.mExpressions = pExpressions;
   }

   public String getExpressionString() {
      StringBuffer buf = new StringBuffer();
      buf.append("(");
      buf.append(this.mExpression.getExpressionString());

      for(int i = 0; i < this.mOperators.size(); ++i) {
         BinaryOperator operator = (BinaryOperator)this.mOperators.get(i);
         Expression expression = (Expression)this.mExpressions.get(i);
         buf.append(" ");
         buf.append(operator.getOperatorSymbol());
         buf.append(" ");
         buf.append(expression.getExpressionString());
      }

      buf.append(")");
      return buf.toString();
   }

   public Object evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      Object value = this.mExpression.evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);

      for(int i = 0; i < this.mOperators.size(); ++i) {
         BinaryOperator operator = (BinaryOperator)this.mOperators.get(i);
         if (operator.shouldCoerceToBoolean()) {
            value = Coercions.coerceToBoolean(value, pLogger);
         }

         if (operator.shouldEvaluate(value)) {
            Expression expression = (Expression)this.mExpressions.get(i);
            Object nextValue = expression.evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);
            value = operator.apply(value, nextValue, pContext, pLogger);
         }
      }

      return value;
   }
}
