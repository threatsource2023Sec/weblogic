package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;

public interface DestructionAwareBeanPostProcessor extends BeanPostProcessor {
   void postProcessBeforeDestruction(Object var1, String var2) throws BeansException;

   default boolean requiresDestruction(Object bean) {
      return true;
   }
}
