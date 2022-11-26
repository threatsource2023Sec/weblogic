package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.List;

public interface MethodResolver {
   @Nullable
   MethodExecutor resolve(EvaluationContext var1, Object var2, String var3, List var4) throws AccessException;
}
