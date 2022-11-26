package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.parsing.Location;
import com.bea.core.repackaged.springframework.beans.factory.parsing.Problem;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ProblemReporter;
import com.bea.core.repackaged.springframework.core.io.DescriptiveResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.StandardAnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

final class ConfigurationClass {
   private final AnnotationMetadata metadata;
   private final Resource resource;
   @Nullable
   private String beanName;
   private final Set importedBy = new LinkedHashSet(1);
   private final Set beanMethods = new LinkedHashSet();
   private final Map importedResources = new LinkedHashMap();
   private final Map importBeanDefinitionRegistrars = new LinkedHashMap();
   final Set skippedBeanMethods = new HashSet();

   public ConfigurationClass(MetadataReader metadataReader, String beanName) {
      Assert.notNull(beanName, (String)"Bean name must not be null");
      this.metadata = metadataReader.getAnnotationMetadata();
      this.resource = metadataReader.getResource();
      this.beanName = beanName;
   }

   public ConfigurationClass(MetadataReader metadataReader, @Nullable ConfigurationClass importedBy) {
      this.metadata = metadataReader.getAnnotationMetadata();
      this.resource = metadataReader.getResource();
      this.importedBy.add(importedBy);
   }

   public ConfigurationClass(Class clazz, String beanName) {
      Assert.notNull(beanName, (String)"Bean name must not be null");
      this.metadata = new StandardAnnotationMetadata(clazz, true);
      this.resource = new DescriptiveResource(clazz.getName());
      this.beanName = beanName;
   }

   public ConfigurationClass(Class clazz, @Nullable ConfigurationClass importedBy) {
      this.metadata = new StandardAnnotationMetadata(clazz, true);
      this.resource = new DescriptiveResource(clazz.getName());
      this.importedBy.add(importedBy);
   }

   public ConfigurationClass(AnnotationMetadata metadata, String beanName) {
      Assert.notNull(beanName, (String)"Bean name must not be null");
      this.metadata = metadata;
      this.resource = new DescriptiveResource(metadata.getClassName());
      this.beanName = beanName;
   }

   public AnnotationMetadata getMetadata() {
      return this.metadata;
   }

   public Resource getResource() {
      return this.resource;
   }

   public String getSimpleName() {
      return ClassUtils.getShortName(this.getMetadata().getClassName());
   }

   public void setBeanName(String beanName) {
      this.beanName = beanName;
   }

   @Nullable
   public String getBeanName() {
      return this.beanName;
   }

   public boolean isImported() {
      return !this.importedBy.isEmpty();
   }

   public void mergeImportedBy(ConfigurationClass otherConfigClass) {
      this.importedBy.addAll(otherConfigClass.importedBy);
   }

   public Set getImportedBy() {
      return this.importedBy;
   }

   public void addBeanMethod(BeanMethod method) {
      this.beanMethods.add(method);
   }

   public Set getBeanMethods() {
      return this.beanMethods;
   }

   public void addImportedResource(String importedResource, Class readerClass) {
      this.importedResources.put(importedResource, readerClass);
   }

   public void addImportBeanDefinitionRegistrar(ImportBeanDefinitionRegistrar registrar, AnnotationMetadata importingClassMetadata) {
      this.importBeanDefinitionRegistrars.put(registrar, importingClassMetadata);
   }

   public Map getImportBeanDefinitionRegistrars() {
      return this.importBeanDefinitionRegistrars;
   }

   public Map getImportedResources() {
      return this.importedResources;
   }

   public void validate(ProblemReporter problemReporter) {
      if (this.getMetadata().isAnnotated(Configuration.class.getName()) && this.getMetadata().isFinal()) {
         problemReporter.error(new FinalConfigurationProblem());
      }

      Iterator var2 = this.beanMethods.iterator();

      while(var2.hasNext()) {
         BeanMethod beanMethod = (BeanMethod)var2.next();
         beanMethod.validate(problemReporter);
      }

   }

   public boolean equals(Object other) {
      return this == other || other instanceof ConfigurationClass && this.getMetadata().getClassName().equals(((ConfigurationClass)other).getMetadata().getClassName());
   }

   public int hashCode() {
      return this.getMetadata().getClassName().hashCode();
   }

   public String toString() {
      return "ConfigurationClass: beanName '" + this.beanName + "', " + this.resource;
   }

   private class FinalConfigurationProblem extends Problem {
      public FinalConfigurationProblem() {
         super(String.format("@Configuration class '%s' may not be final. Remove the final modifier to continue.", ConfigurationClass.this.getSimpleName()), new Location(ConfigurationClass.this.getResource(), ConfigurationClass.this.getMetadata()));
      }
   }
}
