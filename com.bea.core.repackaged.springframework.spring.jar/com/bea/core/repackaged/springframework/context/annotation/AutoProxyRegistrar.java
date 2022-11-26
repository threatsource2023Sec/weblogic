package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.config.AopConfigUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import java.util.Iterator;
import java.util.Set;

public class AutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
   private final Log logger = LogFactory.getLog(this.getClass());

   public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
      boolean candidateFound = false;
      Set annTypes = importingClassMetadata.getAnnotationTypes();
      Iterator var5 = annTypes.iterator();

      while(var5.hasNext()) {
         String annType = (String)var5.next();
         AnnotationAttributes candidate = AnnotationConfigUtils.attributesFor(importingClassMetadata, (String)annType);
         if (candidate != null) {
            Object mode = candidate.get("mode");
            Object proxyTargetClass = candidate.get("proxyTargetClass");
            if (mode != null && proxyTargetClass != null && AdviceMode.class == mode.getClass() && Boolean.class == proxyTargetClass.getClass()) {
               candidateFound = true;
               if (mode == AdviceMode.PROXY) {
                  AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
                  if ((Boolean)proxyTargetClass) {
                     AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
                     return;
                  }
               }
            }
         }
      }

      if (!candidateFound && this.logger.isInfoEnabled()) {
         String name = this.getClass().getSimpleName();
         this.logger.info(String.format("%s was imported but no annotations were found having both 'mode' and 'proxyTargetClass' attributes of type AdviceMode and boolean respectively. This means that auto proxy creator registration and configuration may not have occurred as intended, and components may not be proxied as expected. Check to ensure that %s has been @Import'ed on the same class where these annotations are declared; otherwise remove the import of %s altogether.", name, name, name));
      }

   }
}
