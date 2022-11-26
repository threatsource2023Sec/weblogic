package com.oracle.injection.integration;

import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Specializes;

class BeanTypeUtils {
   private BeanTypeUtils() {
   }

   static boolean isSessionBeanClass(Class beanClass) {
      return isStatelessSessionBean(beanClass) || isStatefulSessionBean(beanClass) || isSingletonSessionBean(beanClass);
   }

   static boolean isStatelessSessionBean(Class beanClass) {
      return hasAnnotation(beanClass, Stateless.class);
   }

   static boolean isStatefulSessionBean(Class beanClass) {
      return hasAnnotation(beanClass, Stateful.class);
   }

   static boolean isSingletonSessionBean(Class beanClass) {
      return hasAnnotation(beanClass, Singleton.class);
   }

   static boolean isSpecializingBean(Class beanClass) {
      return hasAnnotation(beanClass, Specializes.class);
   }

   private static boolean hasAnnotation(Class beanClass, Class annotation) {
      return beanClass.getAnnotation(annotation) != null;
   }
}
