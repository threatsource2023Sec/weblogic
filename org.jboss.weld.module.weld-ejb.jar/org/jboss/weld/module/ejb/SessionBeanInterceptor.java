package org.jboss.weld.module.ejb;

import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.interceptor.InvocationContext;
import org.jboss.weld.Container;
import org.jboss.weld.context.ejb.EjbRequestContext;
import org.jboss.weld.manager.BeanManagerImpl;

public class SessionBeanInterceptor extends AbstractEJBRequestScopeActivationInterceptor implements Serializable {
   private static final long serialVersionUID = 2658712435730329384L;
   private volatile BeanManagerImpl beanManager;
   private transient volatile EjbRequestContext ejbRequestContext;

   public Object aroundInvoke(InvocationContext invocation) throws Exception {
      if (this.beanManager == null) {
         this.beanManager = this.obtainBeanManager(invocation);
         this.ejbRequestContext = super.getEjbRequestContext();
      }

      return super.aroundInvoke(invocation);
   }

   private BeanManagerImpl obtainBeanManager(InvocationContext invocation) {
      Object value = invocation.getContextData().get("WELD_CONTEXT_ID_KEY");
      String contextId = "STATIC_INSTANCE";
      if (value instanceof String) {
         contextId = (String)value;
      }

      return Container.instance(contextId).deploymentManager();
   }

   protected EjbRequestContext getEjbRequestContext() {
      return this.ejbRequestContext;
   }

   protected BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   private Object readResolve() throws ObjectStreamException {
      return new SessionBeanInterceptor();
   }
}
