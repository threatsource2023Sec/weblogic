package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class EnvironmentAccessor implements PropertyAccessor {
   public Class[] getSpecificTargetClasses() {
      return new Class[]{Environment.class};
   }

   public boolean canRead(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return true;
   }

   public TypedValue read(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      Assert.state(target instanceof Environment, "Target must be of type Environment");
      return new TypedValue(((Environment)target).getProperty(name));
   }

   public boolean canWrite(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return false;
   }

   public void write(EvaluationContext context, @Nullable Object target, String name, @Nullable Object newValue) throws AccessException {
   }
}
