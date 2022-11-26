package org.jboss.weld.bean.proxy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.PassivationCapable;
import org.jboss.weld.Container;
import org.jboss.weld.bean.CommonBean;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.proxy.WeldClientProxy;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;

@SuppressFBWarnings(
   value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
   justification = "bean field is loaded lazily"
)
public class ProxyMethodHandler implements MethodHandler, Serializable, WeldClientProxy.Metadata {
   private static final long serialVersionUID = 5293834510764991583L;
   private final BeanInstance beanInstance;
   private final BeanIdentifier beanId;
   private transient Bean bean;
   private final String contextId;

   public ProxyMethodHandler(String contextId, BeanInstance beanInstance, Bean bean) {
      this.beanInstance = beanInstance;
      this.bean = bean;
      this.contextId = contextId;
      if (bean instanceof CommonBean) {
         this.beanId = ((CommonBean)bean).getIdentifier();
      } else if (bean instanceof PassivationCapable) {
         this.beanId = new StringBeanIdentifier(((PassivationCapable)bean).getId());
      } else {
         this.beanId = null;
      }

   }

   public Object getContextualInstance() {
      return this.getInstance();
   }

   public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      if (thisMethod == null) {
         BeanLogger.LOG.methodHandlerProcessingReturningBeanInstance(self.getClass());
         if (this.beanInstance == null) {
            throw BeanLogger.LOG.beanInstanceNotSetOnProxy(this.getBean());
         } else {
            return this.beanInstance.getInstance();
         }
      } else {
         BeanLogger.LOG.methodHandlerProcessingCall(thisMethod, self.getClass());
         if (thisMethod.getDeclaringClass().equals(TargetInstanceProxy.class)) {
            if (this.beanInstance == null) {
               throw BeanLogger.LOG.beanInstanceNotSetOnProxy(this.getBean());
            } else if (thisMethod.getName().equals("weld_getTargetInstance")) {
               return this.beanInstance.getInstance();
            } else {
               return thisMethod.getName().equals("weld_getTargetClass") ? this.beanInstance.getInstanceType() : null;
            }
         } else if (thisMethod.getName().equals("_initMH")) {
            BeanLogger.LOG.settingNewMethodHandler(args[0], self.getClass());
            return new ProxyMethodHandler(this.contextId, new TargetBeanInstance(args[0]), this.getBean());
         } else if (this.beanInstance == null) {
            throw BeanLogger.LOG.beanInstanceNotSetOnProxy(this.getBean());
         } else {
            Object instance = this.beanInstance.getInstance();
            Object result = this.beanInstance.invoke(instance, thisMethod, args);
            return result != null && result == instance && thisMethod.getReturnType().isAssignableFrom(self.getClass()) ? self : result;
         }
      }
   }

   public Bean getBean() {
      if (this.bean == null) {
         if (this.beanId == null) {
            throw BeanLogger.LOG.proxyHandlerSerializedForNonSerializableBean();
         }

         this.bean = (Bean)((ContextualStore)Container.instance(this.contextId).services().get(ContextualStore.class)).getContextual(this.beanId);
      }

      return this.bean;
   }

   public Object getInstance() {
      return this.beanInstance.getInstance();
   }
}
