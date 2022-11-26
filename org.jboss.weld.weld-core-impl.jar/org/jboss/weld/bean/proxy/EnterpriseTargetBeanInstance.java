package org.jboss.weld.bean.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;

public class EnterpriseTargetBeanInstance extends AbstractBeanInstance implements Serializable {
   private static final long serialVersionUID = 2825052095047112162L;
   private final Class beanType;
   private final MethodHandler methodHandler;

   public EnterpriseTargetBeanInstance(Class baseType, MethodHandler methodHandler) {
      this.beanType = baseType;
      this.methodHandler = methodHandler;
   }

   public EnterpriseTargetBeanInstance(Set types, MethodHandler methodHandler) {
      this(computeInstanceType(types), methodHandler);
   }

   public Object getInstance() {
      return null;
   }

   public Class getInstanceType() {
      return this.beanType;
   }

   public Object invoke(Object instance, Method method, Object... arguments) throws Throwable {
      return this.methodHandler.invoke((Object)null, method, method, arguments);
   }
}
