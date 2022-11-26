package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class BeanFactoryAccessor implements PropertyAccessor {
   public Class[] getSpecificTargetClasses() {
      return new Class[]{BeanFactory.class};
   }

   public boolean canRead(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return target instanceof BeanFactory && ((BeanFactory)target).containsBean(name);
   }

   public TypedValue read(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      Assert.state(target instanceof BeanFactory, "Target must be of type BeanFactory");
      return new TypedValue(((BeanFactory)target).getBean(name));
   }

   public boolean canWrite(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return false;
   }

   public void write(EvaluationContext context, @Nullable Object target, String name, @Nullable Object newValue) throws AccessException {
      throw new AccessException("Beans in a BeanFactory are read-only");
   }
}
