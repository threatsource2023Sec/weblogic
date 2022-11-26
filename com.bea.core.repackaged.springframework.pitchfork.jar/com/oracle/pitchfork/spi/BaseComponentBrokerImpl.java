package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class BaseComponentBrokerImpl {
   protected ComponentContributor compContributor;
   protected boolean usesSpringExtensionModel;
   protected ApplicationContext springBeanFactory;

   public void initialize(ClassLoader cl, String springConfigResource, String compFactoryName, boolean usesSpringExtensionModel, ComponentContributor cc, DeploymentUnitMetadata metadata) {
      this.compContributor = cc;
      this.usesSpringExtensionModel = usesSpringExtensionModel;
      WLSBootstrap bootstrap = new WLSBootstrap(cl, springConfigResource, compFactoryName, usesSpringExtensionModel);
      this.springBeanFactory = bootstrap.deploy(new String[0], (ResourceLoader)null, this.compContributor, metadata);
   }

   public Object getBean(String ejbName, Class beanClass, boolean createProxy) throws IllegalAccessException, InstantiationException {
      InterceptionMetadataI im = (InterceptionMetadataI)this.compContributor.getMetadata(ejbName);
      List icptrMetas = im.getAroundConstructMetadatas();
      Map icptrInstances = new HashMap();
      if (this.usesSpringExtensionModel) {
         if (icptrMetas.isEmpty()) {
            try {
               return this.springBeanFactory.getBean(ejbName);
            } catch (BeanCreationException var9) {
               InstantiationException ie = new InstantiationException(var9.getMessage());
               ie.initCause(var9);
               throw ie;
            }
         } else {
            return im.constructBean(im.getBeanCreator(), icptrMetas, icptrInstances);
         }
      } else {
         Object bean = icptrMetas.isEmpty() ? beanClass.newInstance() : im.constructBean(im.getBeanCreator(), icptrMetas, icptrInstances);
         im.inject(bean);
         if (createProxy) {
            bean = im.createProxyIfNecessary(new TargetWrapperImpl(bean), icptrInstances);
         }

         return bean;
      }
   }
}
