package com.oracle.injection;

import javax.el.ELResolver;
import javax.el.ExpressionFactory;

public interface BeanManager {
   Object getBean(String var1);

   void destroyBean(Object var1);

   Object getBean(Class var1);

   Object newBeanInstance(String var1, boolean var2);

   Object newBeanInstance(Class var1, boolean var2);

   Object newInterceptorInstance(Class var1);

   void injectOnExternalInstance(Object var1) throws InjectionException;

   ExpressionFactory getWrappedExpressionFactory(ExpressionFactory var1);

   ELResolver getELResolver();

   Object getInternalBeanManager();

   void invokePostConstruct(Object var1) throws InjectionException;

   void invokePreDestroy(Object var1) throws InjectionException;

   Object createEjb(String var1);

   void injectEjbInstance(Object var1);

   Object getReference(String var1);
}
