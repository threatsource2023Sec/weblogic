package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.factory.wiring.BeanWiringInfo;
import com.bea.core.repackaged.springframework.beans.factory.wiring.BeanWiringInfoResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class AnnotationBeanWiringInfoResolver implements BeanWiringInfoResolver {
   @Nullable
   public BeanWiringInfo resolveWiringInfo(Object beanInstance) {
      Assert.notNull(beanInstance, "Bean instance must not be null");
      Configurable annotation = (Configurable)beanInstance.getClass().getAnnotation(Configurable.class);
      return annotation != null ? this.buildWiringInfo(beanInstance, annotation) : null;
   }

   protected BeanWiringInfo buildWiringInfo(Object beanInstance, Configurable annotation) {
      if (!Autowire.NO.equals(annotation.autowire())) {
         return new BeanWiringInfo(annotation.autowire().value(), annotation.dependencyCheck());
      } else {
         return !"".equals(annotation.value()) ? new BeanWiringInfo(annotation.value(), false) : new BeanWiringInfo(this.getDefaultBeanName(beanInstance), true);
      }
   }

   protected String getDefaultBeanName(Object beanInstance) {
      return ClassUtils.getUserClass(beanInstance).getName();
   }
}
