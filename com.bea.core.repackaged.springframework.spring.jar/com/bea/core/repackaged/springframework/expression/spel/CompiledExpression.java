package com.bea.core.repackaged.springframework.expression.spel;

import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class CompiledExpression {
   public abstract Object getValue(@Nullable Object var1, @Nullable EvaluationContext var2) throws EvaluationException;
}
