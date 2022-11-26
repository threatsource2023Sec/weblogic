package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Operation;
import com.bea.core.repackaged.springframework.expression.OperatorOverloader;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class StandardOperatorOverloader implements OperatorOverloader {
   public boolean overridesOperation(Operation operation, @Nullable Object leftOperand, @Nullable Object rightOperand) throws EvaluationException {
      return false;
   }

   public Object operate(Operation operation, @Nullable Object leftOperand, @Nullable Object rightOperand) throws EvaluationException {
      throw new EvaluationException("No operation overloaded by default");
   }
}
