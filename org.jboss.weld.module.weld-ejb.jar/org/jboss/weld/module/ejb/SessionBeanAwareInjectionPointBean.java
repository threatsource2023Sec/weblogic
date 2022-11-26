package org.jboss.weld.module.ejb;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.builtin.InjectionPointBean;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.injection.ForwardingInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;

class SessionBeanAwareInjectionPointBean extends InjectionPointBean {
   private static final ThreadLocal CONTEXTUAL_SESSION_BEANS = new ThreadLocal() {
      protected Set initialValue() {
         return new HashSet();
      }
   };

   SessionBeanAwareInjectionPointBean(BeanManagerImpl manager) {
      super(manager);
   }

   protected InjectionPoint newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      ip = super.newInstance(ip, creationalContext);
      if (ip != null) {
         ip = wrapIfNecessary(ip);
      }

      return ip;
   }

   public static void registerContextualInstance(EjbDescriptor descriptor) {
      ((Set)CONTEXTUAL_SESSION_BEANS.get()).add(descriptor.getBeanClass());
   }

   public static void unregisterContextualInstance(EjbDescriptor descriptor) {
      Set classes = (Set)CONTEXTUAL_SESSION_BEANS.get();
      classes.remove(descriptor.getBeanClass());
      if (classes.isEmpty()) {
         CONTEXTUAL_SESSION_BEANS.remove();
      }

   }

   public static InjectionPoint wrapIfNecessary(InjectionPoint ip) {
      return (InjectionPoint)(ip.getBean() instanceof SessionBean && !((Set)CONTEXTUAL_SESSION_BEANS.get()).contains(ip.getBean().getBeanClass()) ? new NonContextualSessionBeanInjectionPoint(ip) : ip);
   }

   private static class NonContextualSessionBeanInjectionPoint extends ForwardingInjectionPoint implements Serializable {
      private static final long serialVersionUID = 6338875301221129389L;
      private final InjectionPoint delegate;

      public NonContextualSessionBeanInjectionPoint(InjectionPoint delegate) {
         this.delegate = delegate;
      }

      protected InjectionPoint delegate() {
         return this.delegate;
      }

      public Bean getBean() {
         return null;
      }
   }
}
