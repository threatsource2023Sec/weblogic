package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface OperatorOverloader {
   boolean overridesOperation(Operation var1, @Nullable Object var2, @Nullable Object var3) throws EvaluationException;

   Object operate(Operation var1, @Nullable Object var2, @Nullable Object var3) throws EvaluationException;
}
