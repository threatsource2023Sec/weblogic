package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanExpressionContext;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class BeanExpressionContextAccessor implements PropertyAccessor {
   public boolean canRead(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return target instanceof BeanExpressionContext && ((BeanExpressionContext)target).containsObject(name);
   }

   public TypedValue read(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      Assert.state(target instanceof BeanExpressionContext, "Target must be of type BeanExpressionContext");
      return new TypedValue(((BeanExpressionContext)target).getObject(name));
   }

   public boolean canWrite(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return false;
   }

   public void write(EvaluationContext context, @Nullable Object target, String name, @Nullable Object newValue) throws AccessException {
      throw new AccessException("Beans in a BeanFactory are read-only");
   }

   public Class[] getSpecificTargetClasses() {
      return new Class[]{BeanExpressionContext.class};
   }
}
