package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.List;

@FunctionalInterface
public interface ConstructorResolver {
   @Nullable
   ConstructorExecutor resolve(EvaluationContext var1, String var2, List var3) throws AccessException;
}
