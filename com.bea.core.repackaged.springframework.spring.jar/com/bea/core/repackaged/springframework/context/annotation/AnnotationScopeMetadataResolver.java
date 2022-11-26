package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.util.Assert;

public class AnnotationScopeMetadataResolver implements ScopeMetadataResolver {
   private final ScopedProxyMode defaultProxyMode;
   protected Class scopeAnnotationType = Scope.class;

   public AnnotationScopeMetadataResolver() {
      this.defaultProxyMode = ScopedProxyMode.NO;
   }

   public AnnotationScopeMetadataResolver(ScopedProxyMode defaultProxyMode) {
      Assert.notNull(defaultProxyMode, (String)"'defaultProxyMode' must not be null");
      this.defaultProxyMode = defaultProxyMode;
   }

   public void setScopeAnnotationType(Class scopeAnnotationType) {
      Assert.notNull(scopeAnnotationType, (String)"'scopeAnnotationType' must not be null");
      this.scopeAnnotationType = scopeAnnotationType;
   }

   public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
      ScopeMetadata metadata = new ScopeMetadata();
      if (definition instanceof AnnotatedBeanDefinition) {
         AnnotatedBeanDefinition annDef = (AnnotatedBeanDefinition)definition;
         AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(annDef.getMetadata(), (Class)this.scopeAnnotationType);
         if (attributes != null) {
            metadata.setScopeName(attributes.getString("value"));
            ScopedProxyMode proxyMode = (ScopedProxyMode)attributes.getEnum("proxyMode");
            if (proxyMode == ScopedProxyMode.DEFAULT) {
               proxyMode = this.defaultProxyMode;
            }

            metadata.setScopedProxyMode(proxyMode);
         }
      }

      return metadata;
   }
}
