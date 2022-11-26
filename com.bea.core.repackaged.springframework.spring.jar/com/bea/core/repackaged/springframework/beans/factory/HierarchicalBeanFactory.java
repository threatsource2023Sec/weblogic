package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface HierarchicalBeanFactory extends BeanFactory {
   @Nullable
   BeanFactory getParentBeanFactory();

   boolean containsLocalBean(String var1);
}
