package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.Location;
import com.bea.core.repackaged.springframework.beans.factory.parsing.Problem;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ProblemReporter;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.core.NestedIOException;
import com.bea.core.repackaged.springframework.core.OrderComparator;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.core.env.CompositePropertySource;
import com.bea.core.repackaged.springframework.core.env.ConfigurableEnvironment;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.MutablePropertySources;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.io.support.DefaultPropertySourceFactory;
import com.bea.core.repackaged.springframework.core.io.support.EncodedResource;
import com.bea.core.repackaged.springframework.core.io.support.PropertySourceFactory;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePropertySource;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.core.type.StandardAnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import com.bea.core.repackaged.springframework.core.type.filter.AssignableTypeFilter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.stereotype.Component;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ConfigurationClassParser {
   private static final PropertySourceFactory DEFAULT_PROPERTY_SOURCE_FACTORY = new DefaultPropertySourceFactory();
   private static final Comparator DEFERRED_IMPORT_COMPARATOR = (o1, o2) -> {
      return AnnotationAwareOrderComparator.INSTANCE.compare(o1.getImportSelector(), o2.getImportSelector());
   };
   private final Log logger = LogFactory.getLog(this.getClass());
   private final MetadataReaderFactory metadataReaderFactory;
   private final ProblemReporter problemReporter;
   private final Environment environment;
   private final ResourceLoader resourceLoader;
   private final BeanDefinitionRegistry registry;
   private final ComponentScanAnnotationParser componentScanParser;
   private final ConditionEvaluator conditionEvaluator;
   private final Map configurationClasses = new LinkedHashMap();
   private final Map knownSuperclasses = new HashMap();
   private final List propertySourceNames = new ArrayList();
   private final ImportStack importStack = new ImportStack();
   private final DeferredImportSelectorHandler deferredImportSelectorHandler = new DeferredImportSelectorHandler();

   public ConfigurationClassParser(MetadataReaderFactory metadataReaderFactory, ProblemReporter problemReporter, Environment environment, ResourceLoader resourceLoader, BeanNameGenerator componentScanBeanNameGenerator, BeanDefinitionRegistry registry) {
      this.metadataReaderFactory = metadataReaderFactory;
      this.problemReporter = problemReporter;
      this.environment = environment;
      this.resourceLoader = resourceLoader;
      this.registry = registry;
      this.componentScanParser = new ComponentScanAnnotationParser(environment, resourceLoader, componentScanBeanNameGenerator, registry);
      this.conditionEvaluator = new ConditionEvaluator(registry, environment, resourceLoader);
   }

   public void parse(Set configCandidates) {
      Iterator var2 = configCandidates.iterator();

      while(var2.hasNext()) {
         BeanDefinitionHolder holder = (BeanDefinitionHolder)var2.next();
         BeanDefinition bd = holder.getBeanDefinition();

         try {
            if (bd instanceof AnnotatedBeanDefinition) {
               this.parse(((AnnotatedBeanDefinition)bd).getMetadata(), holder.getBeanName());
            } else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition)bd).hasBeanClass()) {
               this.parse(((AbstractBeanDefinition)bd).getBeanClass(), holder.getBeanName());
            } else {
               this.parse(bd.getBeanClassName(), holder.getBeanName());
            }
         } catch (BeanDefinitionStoreException var6) {
            throw var6;
         } catch (Throwable var7) {
            throw new BeanDefinitionStoreException("Failed to parse configuration class [" + bd.getBeanClassName() + "]", var7);
         }
      }

      this.deferredImportSelectorHandler.process();
   }

   protected final void parse(@Nullable String className, String beanName) throws IOException {
      Assert.notNull(className, (String)"No bean class name for configuration class bean definition");
      MetadataReader reader = this.metadataReaderFactory.getMetadataReader(className);
      this.processConfigurationClass(new ConfigurationClass(reader, beanName));
   }

   protected final void parse(Class clazz, String beanName) throws IOException {
      this.processConfigurationClass(new ConfigurationClass(clazz, beanName));
   }

   protected final void parse(AnnotationMetadata metadata, String beanName) throws IOException {
      this.processConfigurationClass(new ConfigurationClass(metadata, beanName));
   }

   public void validate() {
      Iterator var1 = this.configurationClasses.keySet().iterator();

      while(var1.hasNext()) {
         ConfigurationClass configClass = (ConfigurationClass)var1.next();
         configClass.validate(this.problemReporter);
      }

   }

   public Set getConfigurationClasses() {
      return this.configurationClasses.keySet();
   }

   protected void processConfigurationClass(ConfigurationClass configClass) throws IOException {
      if (!this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION)) {
         ConfigurationClass existingClass = (ConfigurationClass)this.configurationClasses.get(configClass);
         if (existingClass != null) {
            if (configClass.isImported()) {
               if (existingClass.isImported()) {
                  existingClass.mergeImportedBy(configClass);
               }

               return;
            }

            this.configurationClasses.remove(configClass);
            this.knownSuperclasses.values().removeIf(configClass::equals);
         }

         SourceClass sourceClass = this.asSourceClass(configClass);

         do {
            sourceClass = this.doProcessConfigurationClass(configClass, sourceClass);
         } while(sourceClass != null);

         this.configurationClasses.put(configClass, configClass);
      }
   }

   @Nullable
   protected final SourceClass doProcessConfigurationClass(ConfigurationClass configClass, SourceClass sourceClass) throws IOException {
      if (configClass.getMetadata().isAnnotated(Component.class.getName())) {
         this.processMemberClasses(configClass, sourceClass);
      }

      Iterator var3 = AnnotationConfigUtils.attributesForRepeatable(sourceClass.getMetadata(), PropertySources.class, PropertySource.class).iterator();

      AnnotationAttributes importResource;
      while(var3.hasNext()) {
         importResource = (AnnotationAttributes)var3.next();
         if (this.environment instanceof ConfigurableEnvironment) {
            this.processPropertySource(importResource);
         } else {
            this.logger.info("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() + "]. Reason: Environment must implement ConfigurableEnvironment");
         }
      }

      Set componentScans = AnnotationConfigUtils.attributesForRepeatable(sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
      if (!componentScans.isEmpty() && !this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN)) {
         Iterator var13 = componentScans.iterator();

         while(var13.hasNext()) {
            AnnotationAttributes componentScan = (AnnotationAttributes)var13.next();
            Set scannedBeanDefinitions = this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
            Iterator var7 = scannedBeanDefinitions.iterator();

            while(var7.hasNext()) {
               BeanDefinitionHolder holder = (BeanDefinitionHolder)var7.next();
               BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
               if (bdCand == null) {
                  bdCand = holder.getBeanDefinition();
               }

               if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
                  this.parse(bdCand.getBeanClassName(), holder.getBeanName());
               }
            }
         }
      }

      this.processImports(configClass, sourceClass, this.getImports(sourceClass), true);
      importResource = AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), (Class)ImportResource.class);
      if (importResource != null) {
         String[] resources = importResource.getStringArray("locations");
         Class readerClass = importResource.getClass("reader");
         String[] var19 = resources;
         int var21 = resources.length;

         for(int var22 = 0; var22 < var21; ++var22) {
            String resource = var19[var22];
            String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
            configClass.addImportedResource(resolvedResource, readerClass);
         }
      }

      Set beanMethods = this.retrieveBeanMethodMetadata(sourceClass);
      Iterator var17 = beanMethods.iterator();

      while(var17.hasNext()) {
         MethodMetadata methodMetadata = (MethodMetadata)var17.next();
         configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
      }

      this.processInterfaces(configClass, sourceClass);
      if (sourceClass.getMetadata().hasSuperClass()) {
         String superclass = sourceClass.getMetadata().getSuperClassName();
         if (superclass != null && !superclass.startsWith("java") && !this.knownSuperclasses.containsKey(superclass)) {
            this.knownSuperclasses.put(superclass, configClass);
            return sourceClass.getSuperClass();
         }
      }

      return null;
   }

   private void processMemberClasses(ConfigurationClass configClass, SourceClass sourceClass) throws IOException {
      Collection memberClasses = sourceClass.getMemberClasses();
      if (!memberClasses.isEmpty()) {
         List candidates = new ArrayList(memberClasses.size());
         Iterator var5 = memberClasses.iterator();

         SourceClass candidate;
         while(var5.hasNext()) {
            candidate = (SourceClass)var5.next();
            if (ConfigurationClassUtils.isConfigurationCandidate(candidate.getMetadata()) && !candidate.getMetadata().getClassName().equals(configClass.getMetadata().getClassName())) {
               candidates.add(candidate);
            }
         }

         OrderComparator.sort((List)candidates);
         var5 = candidates.iterator();

         while(var5.hasNext()) {
            candidate = (SourceClass)var5.next();
            if (this.importStack.contains(configClass)) {
               this.problemReporter.error(new CircularImportProblem(configClass, this.importStack));
            } else {
               this.importStack.push(configClass);

               try {
                  this.processConfigurationClass(candidate.asConfigClass(configClass));
               } finally {
                  this.importStack.pop();
               }
            }
         }
      }

   }

   private void processInterfaces(ConfigurationClass configClass, SourceClass sourceClass) throws IOException {
      Iterator var3 = sourceClass.getInterfaces().iterator();

      while(var3.hasNext()) {
         SourceClass ifc = (SourceClass)var3.next();
         Set beanMethods = this.retrieveBeanMethodMetadata(ifc);
         Iterator var6 = beanMethods.iterator();

         while(var6.hasNext()) {
            MethodMetadata methodMetadata = (MethodMetadata)var6.next();
            if (!methodMetadata.isAbstract()) {
               configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
            }
         }

         this.processInterfaces(configClass, ifc);
      }

   }

   private Set retrieveBeanMethodMetadata(SourceClass sourceClass) {
      AnnotationMetadata original = sourceClass.getMetadata();
      Set beanMethods = original.getAnnotatedMethods(Bean.class.getName());
      if (((Set)beanMethods).size() > 1 && original instanceof StandardAnnotationMetadata) {
         try {
            AnnotationMetadata asm = this.metadataReaderFactory.getMetadataReader(original.getClassName()).getAnnotationMetadata();
            Set asmMethods = asm.getAnnotatedMethods(Bean.class.getName());
            if (asmMethods.size() >= ((Set)beanMethods).size()) {
               Set selectedMethods = new LinkedHashSet(asmMethods.size());
               Iterator var7 = asmMethods.iterator();

               while(true) {
                  while(var7.hasNext()) {
                     MethodMetadata asmMethod = (MethodMetadata)var7.next();
                     Iterator var9 = ((Set)beanMethods).iterator();

                     while(var9.hasNext()) {
                        MethodMetadata beanMethod = (MethodMetadata)var9.next();
                        if (beanMethod.getMethodName().equals(asmMethod.getMethodName())) {
                           selectedMethods.add(beanMethod);
                           break;
                        }
                     }
                  }

                  if (selectedMethods.size() == ((Set)beanMethods).size()) {
                     beanMethods = selectedMethods;
                  }
                  break;
               }
            }
         } catch (IOException var11) {
            this.logger.debug("Failed to read class file via ASM for determining @Bean method order", var11);
         }
      }

      return (Set)beanMethods;
   }

   private void processPropertySource(AnnotationAttributes propertySource) throws IOException {
      String name = propertySource.getString("name");
      if (!StringUtils.hasLength(name)) {
         name = null;
      }

      String encoding = propertySource.getString("encoding");
      if (!StringUtils.hasLength(encoding)) {
         encoding = null;
      }

      String[] locations = propertySource.getStringArray("value");
      Assert.isTrue(locations.length > 0, "At least one @PropertySource(value) location is required");
      boolean ignoreResourceNotFound = propertySource.getBoolean("ignoreResourceNotFound");
      Class factoryClass = propertySource.getClass("factory");
      PropertySourceFactory factory = factoryClass == PropertySourceFactory.class ? DEFAULT_PROPERTY_SOURCE_FACTORY : (PropertySourceFactory)BeanUtils.instantiateClass(factoryClass);
      String[] var8 = locations;
      int var9 = locations.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String location = var8[var10];

         try {
            String resolvedLocation = this.environment.resolveRequiredPlaceholders(location);
            Resource resource = this.resourceLoader.getResource(resolvedLocation);
            this.addPropertySource(factory.createPropertySource(name, new EncodedResource(resource, encoding)));
         } catch (FileNotFoundException | UnknownHostException | IllegalArgumentException var14) {
            if (!ignoreResourceNotFound) {
               throw var14;
            }

            if (this.logger.isInfoEnabled()) {
               this.logger.info("Properties location [" + location + "] not resolvable: " + var14.getMessage());
            }
         }
      }

   }

   private void addPropertySource(com.bea.core.repackaged.springframework.core.env.PropertySource propertySource) {
      String name = propertySource.getName();
      MutablePropertySources propertySources = ((ConfigurableEnvironment)this.environment).getPropertySources();
      if (this.propertySourceNames.contains(name)) {
         com.bea.core.repackaged.springframework.core.env.PropertySource existing = propertySources.get(name);
         if (existing != null) {
            com.bea.core.repackaged.springframework.core.env.PropertySource newSource = propertySource instanceof ResourcePropertySource ? ((ResourcePropertySource)propertySource).withResourceName() : propertySource;
            if (existing instanceof CompositePropertySource) {
               ((CompositePropertySource)existing).addFirstPropertySource((com.bea.core.repackaged.springframework.core.env.PropertySource)newSource);
            } else {
               if (existing instanceof ResourcePropertySource) {
                  existing = ((ResourcePropertySource)existing).withResourceName();
               }

               CompositePropertySource composite = new CompositePropertySource(name);
               composite.addPropertySource((com.bea.core.repackaged.springframework.core.env.PropertySource)newSource);
               composite.addPropertySource((com.bea.core.repackaged.springframework.core.env.PropertySource)existing);
               propertySources.replace(name, composite);
            }

            return;
         }
      }

      if (this.propertySourceNames.isEmpty()) {
         propertySources.addLast(propertySource);
      } else {
         String firstProcessed = (String)this.propertySourceNames.get(this.propertySourceNames.size() - 1);
         propertySources.addBefore(firstProcessed, propertySource);
      }

      this.propertySourceNames.add(name);
   }

   private Set getImports(SourceClass sourceClass) throws IOException {
      Set imports = new LinkedHashSet();
      Set visited = new LinkedHashSet();
      this.collectImports(sourceClass, imports, visited);
      return imports;
   }

   private void collectImports(SourceClass sourceClass, Set imports, Set visited) throws IOException {
      if (visited.add(sourceClass)) {
         Iterator var4 = sourceClass.getAnnotations().iterator();

         while(var4.hasNext()) {
            SourceClass annotation = (SourceClass)var4.next();
            String annName = annotation.getMetadata().getClassName();
            if (!annName.equals(Import.class.getName())) {
               this.collectImports(annotation, imports, visited);
            }
         }

         imports.addAll(sourceClass.getAnnotationAttributes(Import.class.getName(), "value"));
      }

   }

   private void processImports(ConfigurationClass configClass, SourceClass currentSourceClass, Collection importCandidates, boolean checkForCircularImports) {
      if (!importCandidates.isEmpty()) {
         if (checkForCircularImports && this.isChainedImportOnStack(configClass)) {
            this.problemReporter.error(new CircularImportProblem(configClass, this.importStack));
         } else {
            this.importStack.push(configClass);

            try {
               Iterator var5 = importCandidates.iterator();

               while(var5.hasNext()) {
                  SourceClass candidate = (SourceClass)var5.next();
                  Class candidateClass;
                  if (candidate.isAssignable(ImportSelector.class)) {
                     candidateClass = candidate.loadClass();
                     ImportSelector selector = (ImportSelector)BeanUtils.instantiateClass(candidateClass, ImportSelector.class);
                     ParserStrategyUtils.invokeAwareMethods(selector, this.environment, this.resourceLoader, this.registry);
                     if (selector instanceof DeferredImportSelector) {
                        this.deferredImportSelectorHandler.handle(configClass, (DeferredImportSelector)selector);
                     } else {
                        String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
                        Collection importSourceClasses = this.asSourceClasses(importClassNames);
                        this.processImports(configClass, currentSourceClass, importSourceClasses, false);
                     }
                  } else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
                     candidateClass = candidate.loadClass();
                     ImportBeanDefinitionRegistrar registrar = (ImportBeanDefinitionRegistrar)BeanUtils.instantiateClass(candidateClass, ImportBeanDefinitionRegistrar.class);
                     ParserStrategyUtils.invokeAwareMethods(registrar, this.environment, this.resourceLoader, this.registry);
                     configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());
                  } else {
                     this.importStack.registerImport(currentSourceClass.getMetadata(), candidate.getMetadata().getClassName());
                     this.processConfigurationClass(candidate.asConfigClass(configClass));
                  }
               }
            } catch (BeanDefinitionStoreException var15) {
               throw var15;
            } catch (Throwable var16) {
               throw new BeanDefinitionStoreException("Failed to process import candidates for configuration class [" + configClass.getMetadata().getClassName() + "]", var16);
            } finally {
               this.importStack.pop();
            }
         }

      }
   }

   private boolean isChainedImportOnStack(ConfigurationClass configClass) {
      if (this.importStack.contains(configClass)) {
         String configClassName = configClass.getMetadata().getClassName();

         for(AnnotationMetadata importingClass = this.importStack.getImportingClassFor(configClassName); importingClass != null; importingClass = this.importStack.getImportingClassFor(importingClass.getClassName())) {
            if (configClassName.equals(importingClass.getClassName())) {
               return true;
            }
         }
      }

      return false;
   }

   ImportRegistry getImportRegistry() {
      return this.importStack;
   }

   private SourceClass asSourceClass(ConfigurationClass configurationClass) throws IOException {
      AnnotationMetadata metadata = configurationClass.getMetadata();
      return metadata instanceof StandardAnnotationMetadata ? this.asSourceClass(((StandardAnnotationMetadata)metadata).getIntrospectedClass()) : this.asSourceClass(metadata.getClassName());
   }

   SourceClass asSourceClass(@Nullable Class classType) throws IOException {
      if (classType == null) {
         return new SourceClass(Object.class);
      } else {
         try {
            Annotation[] var2 = classType.getAnnotations();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Annotation ann = var2[var4];
               AnnotationUtils.validateAnnotation(ann);
            }

            return new SourceClass(classType);
         } catch (Throwable var6) {
            return this.asSourceClass(classType.getName());
         }
      }
   }

   private Collection asSourceClasses(String... classNames) throws IOException {
      List annotatedClasses = new ArrayList(classNames.length);
      String[] var3 = classNames;
      int var4 = classNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String className = var3[var5];
         annotatedClasses.add(this.asSourceClass(className));
      }

      return annotatedClasses;
   }

   SourceClass asSourceClass(@Nullable String className) throws IOException {
      if (className == null) {
         return new SourceClass(Object.class);
      } else if (className.startsWith("java")) {
         try {
            return new SourceClass(ClassUtils.forName(className, this.resourceLoader.getClassLoader()));
         } catch (ClassNotFoundException var3) {
            throw new NestedIOException("Failed to load class [" + className + "]", var3);
         }
      } else {
         return new SourceClass(this.metadataReaderFactory.getMetadataReader(className));
      }
   }

   private static class CircularImportProblem extends Problem {
      public CircularImportProblem(ConfigurationClass attemptedImport, Deque importStack) {
         super(String.format("A circular @Import has been detected: Illegal attempt by @Configuration class '%s' to import class '%s' as '%s' is already present in the current import stack %s", ((ConfigurationClass)importStack.element()).getSimpleName(), attemptedImport.getSimpleName(), attemptedImport.getSimpleName(), importStack), new Location(((ConfigurationClass)importStack.element()).getResource(), attemptedImport.getMetadata()));
      }
   }

   private class SourceClass implements Ordered {
      private final Object source;
      private final AnnotationMetadata metadata;

      public SourceClass(Object source) {
         this.source = source;
         if (source instanceof Class) {
            this.metadata = new StandardAnnotationMetadata((Class)source, true);
         } else {
            this.metadata = ((MetadataReader)source).getAnnotationMetadata();
         }

      }

      public final AnnotationMetadata getMetadata() {
         return this.metadata;
      }

      public int getOrder() {
         Integer order = ConfigurationClassUtils.getOrder(this.metadata);
         return order != null ? order : Integer.MAX_VALUE;
      }

      public Class loadClass() throws ClassNotFoundException {
         if (this.source instanceof Class) {
            return (Class)this.source;
         } else {
            String className = ((MetadataReader)this.source).getClassMetadata().getClassName();
            return ClassUtils.forName(className, ConfigurationClassParser.this.resourceLoader.getClassLoader());
         }
      }

      public boolean isAssignable(Class clazz) throws IOException {
         return this.source instanceof Class ? clazz.isAssignableFrom((Class)this.source) : (new AssignableTypeFilter(clazz)).match((MetadataReader)this.source, ConfigurationClassParser.this.metadataReaderFactory);
      }

      public ConfigurationClass asConfigClass(ConfigurationClass importedBy) {
         return this.source instanceof Class ? new ConfigurationClass((Class)this.source, importedBy) : new ConfigurationClass((MetadataReader)this.source, importedBy);
      }

      public Collection getMemberClasses() throws IOException {
         Object sourceToProcess = this.source;
         ArrayList members;
         int var6;
         int var7;
         if (sourceToProcess instanceof Class) {
            Class sourceClass = (Class)sourceToProcess;

            try {
               Class[] declaredClasses = sourceClass.getDeclaredClasses();
               members = new ArrayList(declaredClasses.length);
               Class[] var14 = declaredClasses;
               var6 = declaredClasses.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  Class declaredClass = var14[var7];
                  members.add(ConfigurationClassParser.this.asSourceClass(declaredClass));
               }

               return members;
            } catch (NoClassDefFoundError var11) {
               sourceToProcess = ConfigurationClassParser.this.metadataReaderFactory.getMetadataReader(sourceClass.getName());
            }
         }

         MetadataReader sourceReader = (MetadataReader)sourceToProcess;
         String[] memberClassNames = sourceReader.getClassMetadata().getMemberClassNames();
         members = new ArrayList(memberClassNames.length);
         String[] var5 = memberClassNames;
         var6 = memberClassNames.length;

         for(var7 = 0; var7 < var6; ++var7) {
            String memberClassName = var5[var7];

            try {
               members.add(ConfigurationClassParser.this.asSourceClass(memberClassName));
            } catch (IOException var10) {
               if (ConfigurationClassParser.this.logger.isDebugEnabled()) {
                  ConfigurationClassParser.this.logger.debug("Failed to resolve member class [" + memberClassName + "] - not considering it as a configuration class candidate");
               }
            }
         }

         return members;
      }

      public SourceClass getSuperClass() throws IOException {
         return this.source instanceof Class ? ConfigurationClassParser.this.asSourceClass(((Class)this.source).getSuperclass()) : ConfigurationClassParser.this.asSourceClass(((MetadataReader)this.source).getClassMetadata().getSuperClassName());
      }

      public Set getInterfaces() throws IOException {
         Set result = new LinkedHashSet();
         int var4;
         if (this.source instanceof Class) {
            Class sourceClass = (Class)this.source;
            Class[] var3 = sourceClass.getInterfaces();
            var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Class ifcClass = var3[var5];
               result.add(ConfigurationClassParser.this.asSourceClass(ifcClass));
            }
         } else {
            String[] var7 = this.metadata.getInterfaceNames();
            int var8 = var7.length;

            for(var4 = 0; var4 < var8; ++var4) {
               String className = var7[var4];
               result.add(ConfigurationClassParser.this.asSourceClass(className));
            }
         }

         return result;
      }

      public Set getAnnotations() {
         Set result = new LinkedHashSet();
         if (this.source instanceof Class) {
            Class sourceClass = (Class)this.source;
            Annotation[] var3 = sourceClass.getAnnotations();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Annotation ann = var3[var5];
               Class annType = ann.annotationType();
               if (!annType.getName().startsWith("java")) {
                  try {
                     result.add(ConfigurationClassParser.this.asSourceClass(annType));
                  } catch (Throwable var10) {
                  }
               }
            }
         } else {
            Iterator var11 = this.metadata.getAnnotationTypes().iterator();

            while(var11.hasNext()) {
               String className = (String)var11.next();
               if (!className.startsWith("java")) {
                  try {
                     result.add(this.getRelated(className));
                  } catch (Throwable var9) {
                  }
               }
            }
         }

         return result;
      }

      public Collection getAnnotationAttributes(String annType, String attribute) throws IOException {
         Map annotationAttributes = this.metadata.getAnnotationAttributes(annType, true);
         if (annotationAttributes != null && annotationAttributes.containsKey(attribute)) {
            String[] classNames = (String[])((String[])annotationAttributes.get(attribute));
            Set result = new LinkedHashSet();
            String[] var6 = classNames;
            int var7 = classNames.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String className = var6[var8];
               result.add(this.getRelated(className));
            }

            return result;
         } else {
            return Collections.emptySet();
         }
      }

      private SourceClass getRelated(String className) throws IOException {
         if (this.source instanceof Class) {
            try {
               Class clazz = ClassUtils.forName(className, ((Class)this.source).getClassLoader());
               return ConfigurationClassParser.this.asSourceClass(clazz);
            } catch (ClassNotFoundException var3) {
               if (className.startsWith("java")) {
                  throw new NestedIOException("Failed to load class [" + className + "]", var3);
               } else {
                  return ConfigurationClassParser.this.new SourceClass(ConfigurationClassParser.this.metadataReaderFactory.getMetadataReader(className));
               }
            }
         } else {
            return ConfigurationClassParser.this.asSourceClass(className);
         }
      }

      public boolean equals(Object other) {
         return this == other || other instanceof SourceClass && this.metadata.getClassName().equals(((SourceClass)other).metadata.getClassName());
      }

      public int hashCode() {
         return this.metadata.getClassName().hashCode();
      }

      public String toString() {
         return this.metadata.getClassName();
      }
   }

   private static class DefaultDeferredImportSelectorGroup implements DeferredImportSelector.Group {
      private final List imports = new ArrayList();

      public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
         String[] var3 = selector.selectImports(metadata);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String importClassName = var3[var5];
            this.imports.add(new DeferredImportSelector.Group.Entry(metadata, importClassName));
         }

      }

      public Iterable selectImports() {
         return this.imports;
      }
   }

   private static class DeferredImportSelectorGrouping {
      private final DeferredImportSelector.Group group;
      private final List deferredImports = new ArrayList();

      DeferredImportSelectorGrouping(DeferredImportSelector.Group group) {
         this.group = group;
      }

      public void add(DeferredImportSelectorHolder deferredImport) {
         this.deferredImports.add(deferredImport);
      }

      public Iterable getImports() {
         Iterator var1 = this.deferredImports.iterator();

         while(var1.hasNext()) {
            DeferredImportSelectorHolder deferredImport = (DeferredImportSelectorHolder)var1.next();
            this.group.process(deferredImport.getConfigurationClass().getMetadata(), deferredImport.getImportSelector());
         }

         return this.group.selectImports();
      }
   }

   private static class DeferredImportSelectorHolder {
      private final ConfigurationClass configurationClass;
      private final DeferredImportSelector importSelector;

      public DeferredImportSelectorHolder(ConfigurationClass configClass, DeferredImportSelector selector) {
         this.configurationClass = configClass;
         this.importSelector = selector;
      }

      public ConfigurationClass getConfigurationClass() {
         return this.configurationClass;
      }

      public DeferredImportSelector getImportSelector() {
         return this.importSelector;
      }
   }

   private class DeferredImportSelectorGroupingHandler {
      private final Map groupings;
      private final Map configurationClasses;

      private DeferredImportSelectorGroupingHandler() {
         this.groupings = new LinkedHashMap();
         this.configurationClasses = new HashMap();
      }

      public void register(DeferredImportSelectorHolder deferredImport) {
         Class group = deferredImport.getImportSelector().getImportGroup();
         DeferredImportSelectorGrouping grouping = (DeferredImportSelectorGrouping)this.groupings.computeIfAbsent(group != null ? group : deferredImport, (key) -> {
            return new DeferredImportSelectorGrouping(this.createGroup(group));
         });
         grouping.add(deferredImport);
         this.configurationClasses.put(deferredImport.getConfigurationClass().getMetadata(), deferredImport.getConfigurationClass());
      }

      public void processGroupImports() {
         Iterator var1 = this.groupings.values().iterator();

         while(var1.hasNext()) {
            DeferredImportSelectorGrouping grouping = (DeferredImportSelectorGrouping)var1.next();
            grouping.getImports().forEach((entry) -> {
               ConfigurationClass configurationClass = (ConfigurationClass)this.configurationClasses.get(entry.getMetadata());

               try {
                  ConfigurationClassParser.this.processImports(configurationClass, ConfigurationClassParser.this.asSourceClass(configurationClass), ConfigurationClassParser.this.asSourceClasses(entry.getImportClassName()), false);
               } catch (BeanDefinitionStoreException var4) {
                  throw var4;
               } catch (Throwable var5) {
                  throw new BeanDefinitionStoreException("Failed to process import candidates for configuration class [" + configurationClass.getMetadata().getClassName() + "]", var5);
               }
            });
         }

      }

      private DeferredImportSelector.Group createGroup(@Nullable Class type) {
         Class effectiveType = type != null ? type : DefaultDeferredImportSelectorGroup.class;
         DeferredImportSelector.Group group = (DeferredImportSelector.Group)BeanUtils.instantiateClass(effectiveType);
         ParserStrategyUtils.invokeAwareMethods(group, ConfigurationClassParser.this.environment, ConfigurationClassParser.this.resourceLoader, ConfigurationClassParser.this.registry);
         return group;
      }

      // $FF: synthetic method
      DeferredImportSelectorGroupingHandler(Object x1) {
         this();
      }
   }

   private class DeferredImportSelectorHandler {
      @Nullable
      private List deferredImportSelectors;

      private DeferredImportSelectorHandler() {
         this.deferredImportSelectors = new ArrayList();
      }

      public void handle(ConfigurationClass configClass, DeferredImportSelector importSelector) {
         DeferredImportSelectorHolder holder = new DeferredImportSelectorHolder(configClass, importSelector);
         if (this.deferredImportSelectors == null) {
            DeferredImportSelectorGroupingHandler handler = ConfigurationClassParser.this.new DeferredImportSelectorGroupingHandler();
            handler.register(holder);
            handler.processGroupImports();
         } else {
            this.deferredImportSelectors.add(holder);
         }

      }

      public void process() {
         List deferredImports = this.deferredImportSelectors;
         this.deferredImportSelectors = null;

         try {
            if (deferredImports != null) {
               DeferredImportSelectorGroupingHandler handler = ConfigurationClassParser.this.new DeferredImportSelectorGroupingHandler();
               deferredImports.sort(ConfigurationClassParser.DEFERRED_IMPORT_COMPARATOR);
               deferredImports.forEach(handler::register);
               handler.processGroupImports();
            }
         } finally {
            this.deferredImportSelectors = new ArrayList();
         }

      }

      // $FF: synthetic method
      DeferredImportSelectorHandler(Object x1) {
         this();
      }
   }

   private static class ImportStack extends ArrayDeque implements ImportRegistry {
      private final MultiValueMap imports;

      private ImportStack() {
         this.imports = new LinkedMultiValueMap();
      }

      public void registerImport(AnnotationMetadata importingClass, String importedClass) {
         this.imports.add(importedClass, importingClass);
      }

      @Nullable
      public AnnotationMetadata getImportingClassFor(String importedClass) {
         return (AnnotationMetadata)CollectionUtils.lastElement((List)this.imports.get(importedClass));
      }

      public void removeImportingClass(String importingClass) {
         Iterator var2 = this.imports.values().iterator();

         while(true) {
            while(var2.hasNext()) {
               List list = (List)var2.next();
               Iterator iterator = list.iterator();

               while(iterator.hasNext()) {
                  if (((AnnotationMetadata)iterator.next()).getClassName().equals(importingClass)) {
                     iterator.remove();
                     break;
                  }
               }
            }

            return;
         }
      }

      public String toString() {
         StringBuilder builder = new StringBuilder("[");
         Iterator iterator = this.iterator();

         while(iterator.hasNext()) {
            builder.append(((ConfigurationClass)iterator.next()).getSimpleName());
            if (iterator.hasNext()) {
               builder.append("->");
            }
         }

         return builder.append(']').toString();
      }

      // $FF: synthetic method
      ImportStack(Object x0) {
         this();
      }
   }
}
