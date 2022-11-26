package com.bea.core.repackaged.springframework.aop.scope;

import com.bea.core.repackaged.springframework.aop.framework.AopInfrastructureBean;
import com.bea.core.repackaged.springframework.aop.framework.ProxyConfig;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.aop.target.SimpleBeanTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBeanNotInitializedException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Modifier;

public class ScopedProxyFactoryBean extends ProxyConfig implements FactoryBean, BeanFactoryAware, AopInfrastructureBean {
   private final SimpleBeanTargetSource scopedTargetSource = new SimpleBeanTargetSource();
   @Nullable
   private String targetBeanName;
   @Nullable
   private Object proxy;

   public ScopedProxyFactoryBean() {
      this.setProxyTargetClass(true);
   }

   public void setTargetBeanName(String targetBeanName) {
      this.targetBeanName = targetBeanName;
      this.scopedTargetSource.setTargetBeanName(targetBeanName);
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (!(beanFactory instanceof ConfigurableBeanFactory)) {
         throw new IllegalStateException("Not running in a ConfigurableBeanFactory: " + beanFactory);
      } else {
         ConfigurableBeanFactory cbf = (ConfigurableBeanFactory)beanFactory;
         this.scopedTargetSource.setBeanFactory(beanFactory);
         ProxyFactory pf = new ProxyFactory();
         pf.copyFrom(this);
         pf.setTargetSource(this.scopedTargetSource);
         Assert.notNull(this.targetBeanName, (String)"Property 'targetBeanName' is required");
         Class beanType = beanFactory.getType(this.targetBeanName);
         if (beanType == null) {
            throw new IllegalStateException("Cannot create scoped proxy for bean '" + this.targetBeanName + "': Target type could not be determined at the time of proxy creation.");
         } else {
            if (!this.isProxyTargetClass() || beanType.isInterface() || Modifier.isPrivate(beanType.getModifiers())) {
               pf.setInterfaces(ClassUtils.getAllInterfacesForClass(beanType, cbf.getBeanClassLoader()));
            }

            ScopedObject scopedObject = new DefaultScopedObject(cbf, this.scopedTargetSource.getTargetBeanName());
            pf.addAdvice(new DelegatingIntroductionInterceptor(scopedObject));
            pf.addInterface(AopInfrastructureBean.class);
            this.proxy = pf.getProxy(cbf.getBeanClassLoader());
         }
      }
   }

   public Object getObject() {
      if (this.proxy == null) {
         throw new FactoryBeanNotInitializedException();
      } else {
         return this.proxy;
      }
   }

   public Class getObjectType() {
      return this.proxy != null ? this.proxy.getClass() : this.scopedTargetSource.getTargetClass();
   }

   public boolean isSingleton() {
      return true;
   }
}
