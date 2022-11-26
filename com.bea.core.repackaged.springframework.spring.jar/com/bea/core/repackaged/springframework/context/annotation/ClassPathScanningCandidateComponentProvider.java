package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.annotation.Lookup;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.ResourceLoaderAware;
import com.bea.core.repackaged.springframework.context.index.CandidateComponentsIndex;
import com.bea.core.repackaged.springframework.context.index.CandidateComponentsIndexLoader;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.EnvironmentCapable;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePatternResolver;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePatternUtils;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.CachingMetadataReaderFactory;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import com.bea.core.repackaged.springframework.core.type.filter.AnnotationTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.AssignableTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.TypeFilter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.stereotype.Component;
import com.bea.core.repackaged.springframework.stereotype.Indexed;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider implements EnvironmentCapable, ResourceLoaderAware {
   static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
   protected final Log logger;
   private String resourcePattern;
   private final List includeFilters;
   private final List excludeFilters;
   @Nullable
   private Environment environment;
   @Nullable
   private ConditionEvaluator conditionEvaluator;
   @Nullable
   private ResourcePatternResolver resourcePatternResolver;
   @Nullable
   private MetadataReaderFactory metadataReaderFactory;
   @Nullable
   private CandidateComponentsIndex componentsIndex;

   protected ClassPathScanningCandidateComponentProvider() {
      this.logger = LogFactory.getLog(this.getClass());
      this.resourcePattern = "**/*.class";
      this.includeFilters = new LinkedList();
      this.excludeFilters = new LinkedList();
   }

   public ClassPathScanningCandidateComponentProvider(boolean useDefaultFilters) {
      this(useDefaultFilters, new StandardEnvironment());
   }

   public ClassPathScanningCandidateComponentProvider(boolean useDefaultFilters, Environment environment) {
      this.logger = LogFactory.getLog(this.getClass());
      this.resourcePattern = "**/*.class";
      this.includeFilters = new LinkedList();
      this.excludeFilters = new LinkedList();
      if (useDefaultFilters) {
         this.registerDefaultFilters();
      }

      this.setEnvironment(environment);
      this.setResourceLoader((ResourceLoader)null);
   }

   public void setResourcePattern(String resourcePattern) {
      Assert.notNull(resourcePattern, (String)"'resourcePattern' must not be null");
      this.resourcePattern = resourcePattern;
   }

   public void addIncludeFilter(TypeFilter includeFilter) {
      this.includeFilters.add(includeFilter);
   }

   public void addExcludeFilter(TypeFilter excludeFilter) {
      this.excludeFilters.add(0, excludeFilter);
   }

   public void resetFilters(boolean useDefaultFilters) {
      this.includeFilters.clear();
      this.excludeFilters.clear();
      if (useDefaultFilters) {
         this.registerDefaultFilters();
      }

   }

   protected void registerDefaultFilters() {
      this.includeFilters.add(new AnnotationTypeFilter(Component.class));
      ClassLoader cl = ClassPathScanningCandidateComponentProvider.class.getClassLoader();

      try {
         this.includeFilters.add(new AnnotationTypeFilter(ClassUtils.forName("javax.annotation.ManagedBean", cl), false));
         this.logger.trace("JSR-250 'javax.annotation.ManagedBean' found and supported for component scanning");
      } catch (ClassNotFoundException var4) {
      }

      try {
         this.includeFilters.add(new AnnotationTypeFilter(ClassUtils.forName("javax.inject.Named", cl), false));
         this.logger.trace("JSR-330 'javax.inject.Named' annotation found and supported for component scanning");
      } catch (ClassNotFoundException var3) {
      }

   }

   public void setEnvironment(Environment environment) {
      Assert.notNull(environment, (String)"Environment must not be null");
      this.environment = environment;
      this.conditionEvaluator = null;
   }

   public final Environment getEnvironment() {
      if (this.environment == null) {
         this.environment = new StandardEnvironment();
      }

      return this.environment;
   }

   @Nullable
   protected BeanDefinitionRegistry getRegistry() {
      return null;
   }

   public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
      this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
      this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
      this.componentsIndex = CandidateComponentsIndexLoader.loadIndex(this.resourcePatternResolver.getClassLoader());
   }

   public final ResourceLoader getResourceLoader() {
      return this.getResourcePatternResolver();
   }

   private ResourcePatternResolver getResourcePatternResolver() {
      if (this.resourcePatternResolver == null) {
         this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
      }

      return this.resourcePatternResolver;
   }

   public void setMetadataReaderFactory(MetadataReaderFactory metadataReaderFactory) {
      this.metadataReaderFactory = metadataReaderFactory;
   }

   public final MetadataReaderFactory getMetadataReaderFactory() {
      if (this.metadataReaderFactory == null) {
         this.metadataReaderFactory = new CachingMetadataReaderFactory();
      }

      return this.metadataReaderFactory;
   }

   public Set findCandidateComponents(String basePackage) {
      return this.componentsIndex != null && this.indexSupportsIncludeFilters() ? this.addCandidateComponentsFromIndex(this.componentsIndex, basePackage) : this.scanCandidateComponents(basePackage);
   }

   private boolean indexSupportsIncludeFilters() {
      Iterator var1 = this.includeFilters.iterator();

      TypeFilter includeFilter;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         includeFilter = (TypeFilter)var1.next();
      } while(this.indexSupportsIncludeFilter(includeFilter));

      return false;
   }

   private boolean indexSupportsIncludeFilter(TypeFilter filter) {
      Class target;
      if (!(filter instanceof AnnotationTypeFilter)) {
         if (filter instanceof AssignableTypeFilter) {
            target = ((AssignableTypeFilter)filter).getTargetType();
            return AnnotationUtils.isAnnotationDeclaredLocally(Indexed.class, target);
         } else {
            return false;
         }
      } else {
         target = ((AnnotationTypeFilter)filter).getAnnotationType();
         return AnnotationUtils.isAnnotationDeclaredLocally(Indexed.class, target) || target.getName().startsWith("javax.");
      }
   }

   @Nullable
   private String extractStereotype(TypeFilter filter) {
      if (filter instanceof AnnotationTypeFilter) {
         return ((AnnotationTypeFilter)filter).getAnnotationType().getName();
      } else {
         return filter instanceof AssignableTypeFilter ? ((AssignableTypeFilter)filter).getTargetType().getName() : null;
      }
   }

   private Set addCandidateComponentsFromIndex(CandidateComponentsIndex index, String basePackage) {
      Set candidates = new LinkedHashSet();

      try {
         Set types = new HashSet();
         Iterator var5 = this.includeFilters.iterator();

         while(var5.hasNext()) {
            TypeFilter filter = (TypeFilter)var5.next();
            String stereotype = this.extractStereotype(filter);
            if (stereotype == null) {
               throw new IllegalArgumentException("Failed to extract stereotype from " + filter);
            }

            types.addAll(index.getCandidateTypes(basePackage, stereotype));
         }

         boolean traceEnabled = this.logger.isTraceEnabled();
         boolean debugEnabled = this.logger.isDebugEnabled();
         Iterator var14 = types.iterator();

         while(var14.hasNext()) {
            String type = (String)var14.next();
            MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(type);
            if (this.isCandidateComponent(metadataReader)) {
               AnnotatedGenericBeanDefinition sbd = new AnnotatedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
               if (this.isCandidateComponent((AnnotatedBeanDefinition)sbd)) {
                  if (debugEnabled) {
                     this.logger.debug("Using candidate component class from index: " + type);
                  }

                  candidates.add(sbd);
               } else if (debugEnabled) {
                  this.logger.debug("Ignored because not a concrete top-level class: " + type);
               }
            } else if (traceEnabled) {
               this.logger.trace("Ignored because matching an exclude filter: " + type);
            }
         }

         return candidates;
      } catch (IOException var11) {
         throw new BeanDefinitionStoreException("I/O failure during classpath scanning", var11);
      }
   }

   private Set scanCandidateComponents(String basePackage) {
      Set candidates = new LinkedHashSet();

      try {
         String packageSearchPath = "classpath*:" + this.resolveBasePackage(basePackage) + '/' + this.resourcePattern;
         Resource[] resources = this.getResourcePatternResolver().getResources(packageSearchPath);
         boolean traceEnabled = this.logger.isTraceEnabled();
         boolean debugEnabled = this.logger.isDebugEnabled();
         Resource[] var7 = resources;
         int var8 = resources.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Resource resource = var7[var9];
            if (traceEnabled) {
               this.logger.trace("Scanning " + resource);
            }

            if (resource.isReadable()) {
               try {
                  MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(resource);
                  if (this.isCandidateComponent(metadataReader)) {
                     ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                     sbd.setResource(resource);
                     sbd.setSource(resource);
                     if (this.isCandidateComponent((AnnotatedBeanDefinition)sbd)) {
                        if (debugEnabled) {
                           this.logger.debug("Identified candidate component class: " + resource);
                        }

                        candidates.add(sbd);
                     } else if (debugEnabled) {
                        this.logger.debug("Ignored because not a concrete top-level class: " + resource);
                     }
                  } else if (traceEnabled) {
                     this.logger.trace("Ignored because not matching any filter: " + resource);
                  }
               } catch (Throwable var13) {
                  throw new BeanDefinitionStoreException("Failed to read candidate component class: " + resource, var13);
               }
            } else if (traceEnabled) {
               this.logger.trace("Ignored because not readable: " + resource);
            }
         }

         return candidates;
      } catch (IOException var14) {
         throw new BeanDefinitionStoreException("I/O failure during classpath scanning", var14);
      }
   }

   protected String resolveBasePackage(String basePackage) {
      return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
   }

   protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
      Iterator var2 = this.excludeFilters.iterator();

      TypeFilter tf;
      do {
         if (!var2.hasNext()) {
            var2 = this.includeFilters.iterator();

            do {
               if (!var2.hasNext()) {
                  return false;
               }

               tf = (TypeFilter)var2.next();
            } while(!tf.match(metadataReader, this.getMetadataReaderFactory()));

            return this.isConditionMatch(metadataReader);
         }

         tf = (TypeFilter)var2.next();
      } while(!tf.match(metadataReader, this.getMetadataReaderFactory()));

      return false;
   }

   private boolean isConditionMatch(MetadataReader metadataReader) {
      if (this.conditionEvaluator == null) {
         this.conditionEvaluator = new ConditionEvaluator(this.getRegistry(), this.environment, this.resourcePatternResolver);
      }

      return !this.conditionEvaluator.shouldSkip(metadataReader.getAnnotationMetadata());
   }

   protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
      AnnotationMetadata metadata = beanDefinition.getMetadata();
      return metadata.isIndependent() && (metadata.isConcrete() || metadata.isAbstract() && metadata.hasAnnotatedMethods(Lookup.class.getName()));
   }

   public void clearCache() {
      if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory) {
         ((CachingMetadataReaderFactory)this.metadataReaderFactory).clearCache();
      }

   }
}
