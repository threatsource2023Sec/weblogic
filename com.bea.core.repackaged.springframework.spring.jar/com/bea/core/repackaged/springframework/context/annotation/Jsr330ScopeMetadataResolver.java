package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Jsr330ScopeMetadataResolver implements ScopeMetadataResolver {
   private final Map scopeMap = new HashMap();

   public Jsr330ScopeMetadataResolver() {
      this.registerScope("javax.inject.Singleton", "singleton");
   }

   public final void registerScope(Class annotationType, String scopeName) {
      this.scopeMap.put(annotationType.getName(), scopeName);
   }

   public final void registerScope(String annotationType, String scopeName) {
      this.scopeMap.put(annotationType, scopeName);
   }

   @Nullable
   protected String resolveScopeName(String annotationType) {
      return (String)this.scopeMap.get(annotationType);
   }

   public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
      ScopeMetadata metadata = new ScopeMetadata();
      metadata.setScopeName("prototype");
      if (definition instanceof AnnotatedBeanDefinition) {
         AnnotatedBeanDefinition annDef = (AnnotatedBeanDefinition)definition;
         Set annTypes = annDef.getMetadata().getAnnotationTypes();
         String found = null;
         Iterator var6 = annTypes.iterator();

         while(var6.hasNext()) {
            String annType = (String)var6.next();
            Set metaAnns = annDef.getMetadata().getMetaAnnotationTypes(annType);
            if (metaAnns.contains("javax.inject.Scope")) {
               if (found != null) {
                  throw new IllegalStateException("Found ambiguous scope annotations on bean class [" + definition.getBeanClassName() + "]: " + found + ", " + annType);
               }

               found = annType;
               String scopeName = this.resolveScopeName(annType);
               if (scopeName == null) {
                  throw new IllegalStateException("Unsupported scope annotation - not mapped onto Spring scope name: " + annType);
               }

               metadata.setScopeName(scopeName);
            }
         }
      }

      return metadata;
   }
}
