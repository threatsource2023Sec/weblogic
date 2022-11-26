package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface PropertyAccessor {
   @Nullable
   Class[] getSpecificTargetClasses();

   boolean canRead(EvaluationContext var1, @Nullable Object var2, String var3) throws AccessException;

   TypedValue read(EvaluationContext var1, @Nullable Object var2, String var3) throws AccessException;

   boolean canWrite(EvaluationContext var1, @Nullable Object var2, String var3) throws AccessException;

   void write(EvaluationContext var1, @Nullable Object var2, String var3, @Nullable Object var4) throws AccessException;
}
