package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface TypeComparator {
   boolean canCompare(@Nullable Object var1, @Nullable Object var2);

   int compare(@Nullable Object var1, @Nullable Object var2) throws EvaluationException;
}
