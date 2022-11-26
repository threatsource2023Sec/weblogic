package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorsBean;

public interface ManagedBeansBean {
   ManagedBeanBean[] getManagedBeans();

   ManagedBeanBean createManagedBean();

   ManagedBeanBean lookupManagedBean(String var1);

   void destroyManagedBean(ManagedBeanBean var1);

   InterceptorsBean getInterceptors();

   InterceptorsBean createInterceptors();

   void destroyInterceptors(InterceptorsBean var1);

   InterceptorBindingBean[] getInterceptorBindings();

   InterceptorBindingBean createInterceptorBinding();

   void destroyInterceptorBinding(InterceptorBindingBean var1);
}
