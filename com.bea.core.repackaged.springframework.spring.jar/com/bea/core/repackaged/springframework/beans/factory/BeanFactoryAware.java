package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;

public interface BeanFactoryAware extends Aware {
   void setBeanFactory(BeanFactory var1) throws BeansException;
}
