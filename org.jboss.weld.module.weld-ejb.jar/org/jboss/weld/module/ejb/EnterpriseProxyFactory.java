package org.jboss.weld.module.ejb;

import java.lang.reflect.Method;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.weld.bean.proxy.CommonProxiedMethodFilters;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.bytecode.MethodInformation;
import org.jboss.weld.util.bytecode.RuntimeMethodInformation;
import org.jboss.weld.util.collections.ImmutableSet;

class EnterpriseProxyFactory extends ProxyFactory {
   private static final String SUFFIX = "$EnterpriseProxy$";

   EnterpriseProxyFactory(Class proxiedBeanType, SessionBeanImpl bean) {
      super(bean.getBeanManager().getContextId(), proxiedBeanType, ImmutableSet.builder().addAll(bean.getTypes()).addAll(bean.getEjbDescriptor().getRemoteBusinessInterfacesAsClasses()).build(), bean);
   }

   protected void addSpecialMethods(ClassFile proxyClassType, ClassMethod staticConstructor) {
      super.addSpecialMethods(proxyClassType, staticConstructor);

      try {
         proxyClassType.addInterface(EnterpriseBeanInstance.class.getName());
         Method[] var3 = EnterpriseBeanInstance.class.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            BeanLogger.LOG.addingMethodToEnterpriseProxy(method);
            MethodInformation methodInfo = new RuntimeMethodInformation(method);
            this.createInterceptorBody(proxyClassType.addMethod(method), methodInfo, staticConstructor);
         }

      } catch (Exception var8) {
         throw new WeldException(var8);
      }
   }

   protected String getProxyNameSuffix() {
      return "$EnterpriseProxy$";
   }

   protected boolean isMethodAccepted(Method method, Class proxySuperclass) {
      return super.isMethodAccepted(method, proxySuperclass) && CommonProxiedMethodFilters.NON_PRIVATE.accept(method, proxySuperclass);
   }

   protected boolean isUsingProxyInstantiator() {
      return false;
   }
}
