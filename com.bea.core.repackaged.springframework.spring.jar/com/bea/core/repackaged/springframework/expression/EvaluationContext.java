package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.List;

public interface EvaluationContext {
   TypedValue getRootObject();

   List getPropertyAccessors();

   List getConstructorResolvers();

   List getMethodResolvers();

   @Nullable
   BeanResolver getBeanResolver();

   TypeLocator getTypeLocator();

   TypeConverter getTypeConverter();

   TypeComparator getTypeComparator();

   OperatorOverloader getOperatorOverloader();

   void setVariable(String var1, @Nullable Object var2);

   @Nullable
   Object lookupVariable(String var1);
}
