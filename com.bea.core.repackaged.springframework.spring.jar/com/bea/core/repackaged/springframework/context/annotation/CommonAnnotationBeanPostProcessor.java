package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.annotation.InjectionMetadata;
import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.beans.factory.config.EmbeddedValueResolver;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.jndi.support.SimpleJndiBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceRef;

public class CommonAnnotationBeanPostProcessor extends InitDestroyAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware, Serializable {
   @Nullable
   private static Class webServiceRefClass;
   @Nullable
   private static Class ejbRefClass;
   private final Set ignoredResourceTypes = new HashSet(1);
   private boolean fallbackToDefaultTypeMatch = true;
   private boolean alwaysUseJndiLookup = false;
   private transient BeanFactory jndiFactory = new SimpleJndiBeanFactory();
   @Nullable
   private transient BeanFactory resourceFactory;
   @Nullable
   private transient BeanFactory beanFactory;
   @Nullable
   private transient StringValueResolver embeddedValueResolver;
   private final transient Map injectionMetadataCache = new ConcurrentHashMap(256);

   public CommonAnnotationBeanPostProcessor() {
      this.setOrder(2147483644);
      this.setInitAnnotationType(PostConstruct.class);
      this.setDestroyAnnotationType(PreDestroy.class);
      this.ignoreResourceType("javax.xml.ws.WebServiceContext");
   }

   public void ignoreResourceType(String resourceType) {
      Assert.notNull(resourceType, (String)"Ignored resource type must not be null");
      this.ignoredResourceTypes.add(resourceType);
   }

   public void setFallbackToDefaultTypeMatch(boolean fallbackToDefaultTypeMatch) {
      this.fallbackToDefaultTypeMatch = fallbackToDefaultTypeMatch;
   }

   public void setAlwaysUseJndiLookup(boolean alwaysUseJndiLookup) {
      this.alwaysUseJndiLookup = alwaysUseJndiLookup;
   }

   public void setJndiFactory(BeanFactory jndiFactory) {
      Assert.notNull(jndiFactory, (String)"BeanFactory must not be null");
      this.jndiFactory = jndiFactory;
   }

   public void setResourceFactory(BeanFactory resourceFactory) {
      Assert.notNull(resourceFactory, (String)"BeanFactory must not be null");
      this.resourceFactory = resourceFactory;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      this.beanFactory = beanFactory;
      if (this.resourceFactory == null) {
         this.resourceFactory = beanFactory;
      }

      if (beanFactory instanceof ConfigurableBeanFactory) {
         this.embeddedValueResolver = new EmbeddedValueResolver((ConfigurableBeanFactory)beanFactory);
      }

   }

   public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class beanType, String beanName) {
      super.postProcessMergedBeanDefinition(beanDefinition, beanType, beanName);
      InjectionMetadata metadata = this.findResourceMetadata(beanName, beanType, (PropertyValues)null);
      metadata.checkConfigMembers(beanDefinition);
   }

   public void resetBeanDefinition(String beanName) {
      this.injectionMetadataCache.remove(beanName);
   }

   public Object postProcessBeforeInstantiation(Class beanClass, String beanName) {
      return null;
   }

   public boolean postProcessAfterInstantiation(Object bean, String beanName) {
      return true;
   }

   public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
      InjectionMetadata metadata = this.findResourceMetadata(beanName, bean.getClass(), pvs);

      try {
         metadata.inject(bean, beanName, pvs);
         return pvs;
      } catch (Throwable var6) {
         throw new BeanCreationException(beanName, "Injection of resource dependencies failed", var6);
      }
   }

   /** @deprecated */
   @Deprecated
   public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) {
      return this.postProcessProperties(pvs, bean, beanName);
   }

   private InjectionMetadata findResourceMetadata(String beanName, Class clazz, @Nullable PropertyValues pvs) {
      String cacheKey = StringUtils.hasLength(beanName) ? beanName : clazz.getName();
      InjectionMetadata metadata = (InjectionMetadata)this.injectionMetadataCache.get(cacheKey);
      if (InjectionMetadata.needsRefresh(metadata, clazz)) {
         synchronized(this.injectionMetadataCache) {
            metadata = (InjectionMetadata)this.injectionMetadataCache.get(cacheKey);
            if (InjectionMetadata.needsRefresh(metadata, clazz)) {
               if (metadata != null) {
                  metadata.clear(pvs);
               }

               metadata = this.buildResourceMetadata(clazz);
               this.injectionMetadataCache.put(cacheKey, metadata);
            }
         }
      }

      return metadata;
   }

   private InjectionMetadata buildResourceMetadata(Class clazz) {
      List elements = new ArrayList();
      Class targetClass = clazz;

      do {
         List currElements = new ArrayList();
         ReflectionUtils.doWithLocalFields(targetClass, (field) -> {
            if (webServiceRefClass != null && field.isAnnotationPresent(webServiceRefClass)) {
               if (Modifier.isStatic(field.getModifiers())) {
                  throw new IllegalStateException("@WebServiceRef annotation is not supported on static fields");
               }

               currElements.add(new WebServiceRefElement(field, field, (PropertyDescriptor)null));
            } else if (ejbRefClass != null && field.isAnnotationPresent(ejbRefClass)) {
               if (Modifier.isStatic(field.getModifiers())) {
                  throw new IllegalStateException("@EJB annotation is not supported on static fields");
               }

               currElements.add(new EjbRefElement(field, field, (PropertyDescriptor)null));
            } else if (field.isAnnotationPresent(Resource.class)) {
               if (Modifier.isStatic(field.getModifiers())) {
                  throw new IllegalStateException("@Resource annotation is not supported on static fields");
               }

               if (!this.ignoredResourceTypes.contains(field.getType().getName())) {
                  currElements.add(new ResourceElement(field, field, (PropertyDescriptor)null));
               }
            }

         });
         ReflectionUtils.doWithLocalMethods(targetClass, (method) -> {
            Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
            if (BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
               if (method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
                  PropertyDescriptor pdx;
                  if (webServiceRefClass != null && bridgedMethod.isAnnotationPresent(webServiceRefClass)) {
                     if (Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("@WebServiceRef annotation is not supported on static methods");
                     }

                     if (method.getParameterCount() != 1) {
                        throw new IllegalStateException("@WebServiceRef annotation requires a single-arg method: " + method);
                     }

                     pdx = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
                     currElements.add(new WebServiceRefElement(method, bridgedMethod, pdx));
                  } else if (ejbRefClass != null && bridgedMethod.isAnnotationPresent(ejbRefClass)) {
                     if (Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("@EJB annotation is not supported on static methods");
                     }

                     if (method.getParameterCount() != 1) {
                        throw new IllegalStateException("@EJB annotation requires a single-arg method: " + method);
                     }

                     pdx = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
                     currElements.add(new EjbRefElement(method, bridgedMethod, pdx));
                  } else if (bridgedMethod.isAnnotationPresent(Resource.class)) {
                     if (Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("@Resource annotation is not supported on static methods");
                     }

                     Class[] paramTypes = method.getParameterTypes();
                     if (paramTypes.length != 1) {
                        throw new IllegalStateException("@Resource annotation requires a single-arg method: " + method);
                     }

                     if (!this.ignoredResourceTypes.contains(paramTypes[0].getName())) {
                        PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
                        currElements.add(new ResourceElement(method, bridgedMethod, pd));
                     }
                  }
               }

            }
         });
         elements.addAll(0, currElements);
         targetClass = targetClass.getSuperclass();
      } while(targetClass != null && targetClass != Object.class);

      return new InjectionMetadata(clazz, elements);
   }

   protected Object buildLazyResourceProxy(final LookupElement element, @Nullable final String requestingBeanName) {
      TargetSource ts = new TargetSource() {
         public Class getTargetClass() {
            return element.lookupType;
         }

         public boolean isStatic() {
            return false;
         }

         public Object getTarget() {
            return CommonAnnotationBeanPostProcessor.this.getResource(element, requestingBeanName);
         }

         public void releaseTarget(Object target) {
         }
      };
      ProxyFactory pf = new ProxyFactory();
      pf.setTargetSource(ts);
      if (element.lookupType.isInterface()) {
         pf.addInterface(element.lookupType);
      }

      ClassLoader classLoader = this.beanFactory instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)this.beanFactory).getBeanClassLoader() : null;
      return pf.getProxy(classLoader);
   }

   protected Object getResource(LookupElement element, @Nullable String requestingBeanName) throws NoSuchBeanDefinitionException {
      if (StringUtils.hasLength(element.mappedName)) {
         return this.jndiFactory.getBean(element.mappedName, element.lookupType);
      } else if (this.alwaysUseJndiLookup) {
         return this.jndiFactory.getBean(element.name, element.lookupType);
      } else if (this.resourceFactory == null) {
         throw new NoSuchBeanDefinitionException(element.lookupType, "No resource factory configured - specify the 'resourceFactory' property");
      } else {
         return this.autowireResource(this.resourceFactory, element, requestingBeanName);
      }
   }

   protected Object autowireResource(BeanFactory factory, LookupElement element, @Nullable String requestingBeanName) throws NoSuchBeanDefinitionException {
      String name = element.name;
      Object resource;
      Object autowiredBeanNames;
      if (factory instanceof AutowireCapableBeanFactory) {
         AutowireCapableBeanFactory beanFactory = (AutowireCapableBeanFactory)factory;
         DependencyDescriptor descriptor = element.getDependencyDescriptor();
         if (this.fallbackToDefaultTypeMatch && element.isDefaultName && !factory.containsBean(name)) {
            autowiredBeanNames = new LinkedHashSet();
            resource = beanFactory.resolveDependency(descriptor, requestingBeanName, (Set)autowiredBeanNames, (TypeConverter)null);
            if (resource == null) {
               throw new NoSuchBeanDefinitionException(element.getLookupType(), "No resolvable resource object");
            }
         } else {
            resource = beanFactory.resolveBeanByName(name, descriptor);
            autowiredBeanNames = Collections.singleton(name);
         }
      } else {
         resource = factory.getBean(name, element.lookupType);
         autowiredBeanNames = Collections.singleton(name);
      }

      if (factory instanceof ConfigurableBeanFactory) {
         ConfigurableBeanFactory beanFactory = (ConfigurableBeanFactory)factory;
         Iterator var11 = ((Set)autowiredBeanNames).iterator();

         while(var11.hasNext()) {
            String autowiredBeanName = (String)var11.next();
            if (requestingBeanName != null && beanFactory.containsBean(autowiredBeanName)) {
               beanFactory.registerDependentBean(autowiredBeanName, requestingBeanName);
            }
         }
      }

      return resource;
   }

   static {
      Class clazz;
      try {
         clazz = ClassUtils.forName("javax.xml.ws.WebServiceRef", CommonAnnotationBeanPostProcessor.class.getClassLoader());
         webServiceRefClass = clazz;
      } catch (ClassNotFoundException var2) {
         webServiceRefClass = null;
      }

      try {
         clazz = ClassUtils.forName("javax.ejb.EJB", CommonAnnotationBeanPostProcessor.class.getClassLoader());
         ejbRefClass = clazz;
      } catch (ClassNotFoundException var1) {
         ejbRefClass = null;
      }

   }

   private static class LookupDependencyDescriptor extends DependencyDescriptor {
      private final Class lookupType;

      public LookupDependencyDescriptor(Field field, Class lookupType) {
         super(field, true);
         this.lookupType = lookupType;
      }

      public LookupDependencyDescriptor(Method method, Class lookupType) {
         super(new MethodParameter(method, 0), true);
         this.lookupType = lookupType;
      }

      public Class getDependencyType() {
         return this.lookupType;
      }
   }

   private class EjbRefElement extends LookupElement {
      private final String beanName;

      public EjbRefElement(Member member, AnnotatedElement ae, @Nullable PropertyDescriptor pd) {
         super(member, pd);
         EJB resource = (EJB)ae.getAnnotation(EJB.class);
         String resourceBeanName = resource.beanName();
         String resourceName = resource.name();
         this.isDefaultName = !StringUtils.hasLength(resourceName);
         if (this.isDefaultName) {
            resourceName = this.member.getName();
            if (this.member instanceof Method && resourceName.startsWith("set") && resourceName.length() > 3) {
               resourceName = Introspector.decapitalize(resourceName.substring(3));
            }
         }

         Class resourceType = resource.beanInterface();
         if (Object.class != resourceType) {
            this.checkResourceType(resourceType);
         } else {
            resourceType = this.getResourceType();
         }

         this.beanName = resourceBeanName;
         this.name = resourceName;
         this.lookupType = resourceType;
         this.mappedName = resource.mappedName();
      }

      protected Object getResourceToInject(Object target, @Nullable String requestingBeanName) {
         if (StringUtils.hasLength(this.beanName)) {
            if (CommonAnnotationBeanPostProcessor.this.beanFactory != null && CommonAnnotationBeanPostProcessor.this.beanFactory.containsBean(this.beanName)) {
               Object bean = CommonAnnotationBeanPostProcessor.this.beanFactory.getBean(this.beanName, this.lookupType);
               if (requestingBeanName != null && CommonAnnotationBeanPostProcessor.this.beanFactory instanceof ConfigurableBeanFactory) {
                  ((ConfigurableBeanFactory)CommonAnnotationBeanPostProcessor.this.beanFactory).registerDependentBean(this.beanName, requestingBeanName);
               }

               return bean;
            }

            if (this.isDefaultName && !StringUtils.hasLength(this.mappedName)) {
               throw new NoSuchBeanDefinitionException(this.beanName, "Cannot resolve 'beanName' in local BeanFactory. Consider specifying a general 'name' value instead.");
            }
         }

         return CommonAnnotationBeanPostProcessor.this.getResource(this, requestingBeanName);
      }
   }

   private class WebServiceRefElement extends LookupElement {
      private final Class elementType;
      private final String wsdlLocation;

      public WebServiceRefElement(Member member, AnnotatedElement ae, @Nullable PropertyDescriptor pd) {
         super(member, pd);
         WebServiceRef resource = (WebServiceRef)ae.getAnnotation(WebServiceRef.class);
         String resourceName = resource.name();
         Class resourceType = resource.type();
         this.isDefaultName = !StringUtils.hasLength(resourceName);
         if (this.isDefaultName) {
            resourceName = this.member.getName();
            if (this.member instanceof Method && resourceName.startsWith("set") && resourceName.length() > 3) {
               resourceName = Introspector.decapitalize(resourceName.substring(3));
            }
         }

         if (Object.class != resourceType) {
            this.checkResourceType(resourceType);
         } else {
            resourceType = this.getResourceType();
         }

         this.name = resourceName;
         this.elementType = resourceType;
         if (Service.class.isAssignableFrom(resourceType)) {
            this.lookupType = resourceType;
         } else {
            this.lookupType = resource.value();
         }

         this.mappedName = resource.mappedName();
         this.wsdlLocation = resource.wsdlLocation();
      }

      protected Object getResourceToInject(Object target, @Nullable String requestingBeanName) {
         Service service;
         try {
            service = (Service)CommonAnnotationBeanPostProcessor.this.getResource(this, requestingBeanName);
         } catch (NoSuchBeanDefinitionException var9) {
            if (Service.class == this.lookupType) {
               throw new IllegalStateException("No resource with name '" + this.name + "' found in context, and no specific JAX-WS Service subclass specified. The typical solution is to either specify a LocalJaxWsServiceFactoryBean with the given name or to specify the (generated) Service subclass as @WebServiceRef(...) value.");
            }

            if (StringUtils.hasLength(this.wsdlLocation)) {
               try {
                  Constructor ctor = this.lookupType.getConstructor(URL.class, QName.class);
                  WebServiceClient clientAnn = (WebServiceClient)this.lookupType.getAnnotation(WebServiceClient.class);
                  if (clientAnn == null) {
                     throw new IllegalStateException("JAX-WS Service class [" + this.lookupType.getName() + "] does not carry a WebServiceClient annotation");
                  }

                  service = (Service)BeanUtils.instantiateClass(ctor, new URL(this.wsdlLocation), new QName(clientAnn.targetNamespace(), clientAnn.name()));
               } catch (NoSuchMethodException var7) {
                  throw new IllegalStateException("JAX-WS Service class [" + this.lookupType.getName() + "] does not have a (URL, QName) constructor. Cannot apply specified WSDL location [" + this.wsdlLocation + "].");
               } catch (MalformedURLException var8) {
                  throw new IllegalArgumentException("Specified WSDL location [" + this.wsdlLocation + "] isn't a valid URL");
               }
            } else {
               service = (Service)BeanUtils.instantiateClass(this.lookupType);
            }
         }

         return service.getPort(this.elementType);
      }
   }

   private class ResourceElement extends LookupElement {
      private final boolean lazyLookup;

      public ResourceElement(Member member, AnnotatedElement ae, @Nullable PropertyDescriptor pd) {
         super(member, pd);
         Resource resource = (Resource)ae.getAnnotation(Resource.class);
         String resourceName = resource.name();
         Class resourceType = resource.type();
         this.isDefaultName = !StringUtils.hasLength(resourceName);
         if (this.isDefaultName) {
            resourceName = this.member.getName();
            if (this.member instanceof Method && resourceName.startsWith("set") && resourceName.length() > 3) {
               resourceName = Introspector.decapitalize(resourceName.substring(3));
            }
         } else if (CommonAnnotationBeanPostProcessor.this.embeddedValueResolver != null) {
            resourceName = CommonAnnotationBeanPostProcessor.this.embeddedValueResolver.resolveStringValue(resourceName);
         }

         if (Object.class != resourceType) {
            this.checkResourceType(resourceType);
         } else {
            resourceType = this.getResourceType();
         }

         this.name = resourceName != null ? resourceName : "";
         this.lookupType = resourceType;
         String lookupValue = resource.lookup();
         this.mappedName = StringUtils.hasLength(lookupValue) ? lookupValue : resource.mappedName();
         Lazy lazy = (Lazy)ae.getAnnotation(Lazy.class);
         this.lazyLookup = lazy != null && lazy.value();
      }

      protected Object getResourceToInject(Object target, @Nullable String requestingBeanName) {
         return this.lazyLookup ? CommonAnnotationBeanPostProcessor.this.buildLazyResourceProxy(this, requestingBeanName) : CommonAnnotationBeanPostProcessor.this.getResource(this, requestingBeanName);
      }
   }

   protected abstract class LookupElement extends InjectionMetadata.InjectedElement {
      protected String name = "";
      protected boolean isDefaultName = false;
      protected Class lookupType = Object.class;
      @Nullable
      protected String mappedName;

      public LookupElement(Member member, @Nullable PropertyDescriptor pd) {
         super(member, pd);
      }

      public final String getName() {
         return this.name;
      }

      public final Class getLookupType() {
         return this.lookupType;
      }

      public final DependencyDescriptor getDependencyDescriptor() {
         return this.isField ? new LookupDependencyDescriptor((Field)this.member, this.lookupType) : new LookupDependencyDescriptor((Method)this.member, this.lookupType);
      }
   }
}
