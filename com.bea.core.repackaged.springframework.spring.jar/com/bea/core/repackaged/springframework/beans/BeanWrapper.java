package com.bea.core.repackaged.springframework.beans;

import java.beans.PropertyDescriptor;

public interface BeanWrapper extends ConfigurablePropertyAccessor {
   void setAutoGrowCollectionLimit(int var1);

   int getAutoGrowCollectionLimit();

   Object getWrappedInstance();

   Class getWrappedClass();

   PropertyDescriptor[] getPropertyDescriptors();

   PropertyDescriptor getPropertyDescriptor(String var1) throws InvalidPropertyException;
}
