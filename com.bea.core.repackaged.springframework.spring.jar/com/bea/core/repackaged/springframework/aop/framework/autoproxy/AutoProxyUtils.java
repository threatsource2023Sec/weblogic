package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;

public abstract class AutoProxyUtils {
   public static final String PRESERVE_TARGET_CLASS_ATTRIBUTE = Conventions.getQualifiedAttributeName(AutoProxyUtils.class, "preserveTargetClass");
   public static final String ORIGINAL_TARGET_CLASS_ATTRIBUTE = Conventions.getQualifiedAttributeName(AutoProxyUtils.class, "originalTargetClass");

   public static boolean shouldProxyTargetClass(ConfigurableListableBeanFactory beanFactory, @Nullable String beanName) {
      if (beanName != null && beanFactory.containsBeanDefinition(beanName)) {
         BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
         return Boolean.TRUE.equals(bd.getAttribute(PRESERVE_TARGET_CLASS_ATTRIBUTE));
      } else {
         return false;
      }
   }

   @Nullable
   public static Class determineTargetClass(ConfigurableListableBeanFactory beanFactory, @Nullable String beanName) {
      if (beanName == null) {
         return null;
      } else {
         if (beanFactory.containsBeanDefinition(beanName)) {
            BeanDefinition bd = beanFactory.getMergedBeanDefinition(beanName);
            Class targetClass = (Class)bd.getAttribute(ORIGINAL_TARGET_CLASS_ATTRIBUTE);
            if (targetClass != null) {
               return targetClass;
            }
         }

         return beanFactory.getType(beanName);
      }
   }

   static void exposeTargetClass(ConfigurableListableBeanFactory beanFactory, @Nullable String beanName, Class targetClass) {
      if (beanName != null && beanFactory.containsBeanDefinition(beanName)) {
         beanFactory.getMergedBeanDefinition(beanName).setAttribute(ORIGINAL_TARGET_CLASS_ATTRIBUTE, targetClass);
      }

   }

   static boolean isOriginalInstance(String beanName, Class beanClass) {
      if (StringUtils.hasLength(beanName) && beanName.length() == beanClass.getName().length() + ".ORIGINAL".length()) {
         return beanName.startsWith(beanClass.getName()) && beanName.endsWith(".ORIGINAL");
      } else {
         return false;
      }
   }
}
