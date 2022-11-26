package com.oracle.injection.provider.weld;

import javax.el.ExpressionFactory;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.ApplicationWrapper;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.weld.module.web.el.WeldELContextListener;

public class WeldApplicationFactory extends ApplicationFactory {
   private final ApplicationFactory m_applicationFactoryDelegate;
   private final BeanManagerFactory m_beanManagerFactory;

   public WeldApplicationFactory(ApplicationFactory applicationFactoryDelegate) {
      this(applicationFactoryDelegate, new JNDIBeanManagerFactory());
   }

   protected WeldApplicationFactory(ApplicationFactory applicationFactoryDelegate, BeanManagerFactory beanManagerFactory) {
      this.m_applicationFactoryDelegate = applicationFactoryDelegate;
      this.m_beanManagerFactory = beanManagerFactory;
   }

   public Application getApplication() {
      return new WeldApplication(this.m_applicationFactoryDelegate.getApplication(), this.m_beanManagerFactory);
   }

   public void setApplication(Application application) {
      this.m_applicationFactoryDelegate.setApplication(application);
   }

   private static class JNDIBeanManagerFactory implements BeanManagerFactory {
      private JNDIBeanManagerFactory() {
      }

      public BeanManager getBeanManager() {
         try {
            return (BeanManager)(new InitialContext()).lookup("java:comp/BeanManager");
         } catch (NamingException var2) {
            var2.printStackTrace();
            return null;
         }
      }

      // $FF: synthetic method
      JNDIBeanManagerFactory(Object x0) {
         this();
      }
   }

   interface BeanManagerFactory {
      BeanManager getBeanManager();
   }

   static class WeldApplication extends ApplicationWrapper {
      private final Application m_delegateApplication;
      private final BeanManagerFactory m_beanManagerFactory;
      private final BeanManager beanManager;

      WeldApplication(Application delegateApplication, BeanManagerFactory beanManagerFactory) {
         this.m_delegateApplication = delegateApplication;
         this.m_beanManagerFactory = beanManagerFactory;
         BeanManager beanManager = this.m_beanManagerFactory.getBeanManager();
         if (beanManager != null) {
            this.m_delegateApplication.addELResolver(beanManager.getELResolver());
            this.m_delegateApplication.addELContextListener(new WeldELContextListener());
            this.beanManager = beanManager;
         } else {
            this.beanManager = null;
         }

      }

      public Application getWrapped() {
         return this.m_delegateApplication;
      }

      public ExpressionFactory getExpressionFactory() {
         ExpressionFactory originalExpressionFactory = this.m_delegateApplication.getExpressionFactory();
         return this.beanManager != null ? this.beanManager.wrapExpressionFactory(originalExpressionFactory) : originalExpressionFactory;
      }
   }
}
