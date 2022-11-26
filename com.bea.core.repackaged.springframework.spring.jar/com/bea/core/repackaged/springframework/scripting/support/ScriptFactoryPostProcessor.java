package com.bea.core.repackaged.springframework.scripting.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.AopInfrastructureBean;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.PropertyValue;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCurrentlyInCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionValidationException;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.proxy.InterfaceMaker;
import com.bea.core.repackaged.springframework.context.ResourceLoaderAware;
import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.io.DefaultResourceLoader;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptFactory;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class ScriptFactoryPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanClassLoaderAware, BeanFactoryAware, ResourceLoaderAware, DisposableBean, Ordered {
   public static final String INLINE_SCRIPT_PREFIX = "inline:";
   public static final String REFRESH_CHECK_DELAY_ATTRIBUTE = Conventions.getQualifiedAttributeName(ScriptFactoryPostProcessor.class, "refreshCheckDelay");
   public static final String PROXY_TARGET_CLASS_ATTRIBUTE = Conventions.getQualifiedAttributeName(ScriptFactoryPostProcessor.class, "proxyTargetClass");
   public static final String LANGUAGE_ATTRIBUTE = Conventions.getQualifiedAttributeName(ScriptFactoryPostProcessor.class, "language");
   private static final String SCRIPT_FACTORY_NAME_PREFIX = "scriptFactory.";
   private static final String SCRIPTED_OBJECT_NAME_PREFIX = "scriptedObject.";
   protected final Log logger = LogFactory.getLog(this.getClass());
   private long defaultRefreshCheckDelay = -1L;
   private boolean defaultProxyTargetClass = false;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private ConfigurableBeanFactory beanFactory;
   private ResourceLoader resourceLoader = new DefaultResourceLoader();
   final DefaultListableBeanFactory scriptBeanFactory = new DefaultListableBeanFactory();
   private final Map scriptSourceCache = new HashMap();

   public void setDefaultRefreshCheckDelay(long defaultRefreshCheckDelay) {
      this.defaultRefreshCheckDelay = defaultRefreshCheckDelay;
   }

   public void setDefaultProxyTargetClass(boolean defaultProxyTargetClass) {
      this.defaultProxyTargetClass = defaultProxyTargetClass;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (!(beanFactory instanceof ConfigurableBeanFactory)) {
         throw new IllegalStateException("ScriptFactoryPostProcessor doesn't work with non-ConfigurableBeanFactory: " + beanFactory.getClass());
      } else {
         this.beanFactory = (ConfigurableBeanFactory)beanFactory;
         this.scriptBeanFactory.setParentBeanFactory(this.beanFactory);
         this.scriptBeanFactory.copyConfigurationFrom(this.beanFactory);
         this.scriptBeanFactory.getBeanPostProcessors().removeIf((beanPostProcessor) -> {
            return beanPostProcessor instanceof AopInfrastructureBean;
         });
      }
   }

   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
   }

   public int getOrder() {
      return Integer.MIN_VALUE;
   }

   @Nullable
   public Class predictBeanType(Class beanClass, String beanName) {
      if (!ScriptFactory.class.isAssignableFrom(beanClass)) {
         return null;
      } else {
         Assert.state(this.beanFactory != null, "No BeanFactory set");
         BeanDefinition bd = this.beanFactory.getMergedBeanDefinition(beanName);

         try {
            String scriptFactoryBeanName = "scriptFactory." + beanName;
            String scriptedObjectBeanName = "scriptedObject." + beanName;
            this.prepareScriptBeans(bd, scriptFactoryBeanName, scriptedObjectBeanName);
            ScriptFactory scriptFactory = (ScriptFactory)this.scriptBeanFactory.getBean(scriptFactoryBeanName, ScriptFactory.class);
            ScriptSource scriptSource = this.getScriptSource(scriptFactoryBeanName, scriptFactory.getScriptSourceLocator());
            Class[] interfaces = scriptFactory.getScriptInterfaces();
            Class scriptedType = scriptFactory.getScriptedObjectType(scriptSource);
            if (scriptedType != null) {
               return scriptedType;
            }

            if (!ObjectUtils.isEmpty((Object[])interfaces)) {
               return interfaces.length == 1 ? interfaces[0] : this.createCompositeInterface(interfaces);
            }

            if (bd.isSingleton()) {
               return this.scriptBeanFactory.getBean(scriptedObjectBeanName).getClass();
            }
         } catch (Exception var10) {
            if (var10 instanceof BeanCreationException && ((BeanCreationException)var10).getMostSpecificCause() instanceof BeanCurrentlyInCreationException) {
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Could not determine scripted object type for bean '" + beanName + "': " + var10.getMessage());
               }
            } else if (this.logger.isDebugEnabled()) {
               this.logger.debug("Could not determine scripted object type for bean '" + beanName + "'", var10);
            }
         }

         return null;
      }
   }

   public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
      return pvs;
   }

   public Object postProcessBeforeInstantiation(Class beanClass, String beanName) {
      if (!ScriptFactory.class.isAssignableFrom(beanClass)) {
         return null;
      } else {
         Assert.state(this.beanFactory != null, "No BeanFactory set");
         BeanDefinition bd = this.beanFactory.getMergedBeanDefinition(beanName);
         String scriptFactoryBeanName = "scriptFactory." + beanName;
         String scriptedObjectBeanName = "scriptedObject." + beanName;
         this.prepareScriptBeans(bd, scriptFactoryBeanName, scriptedObjectBeanName);
         ScriptFactory scriptFactory = (ScriptFactory)this.scriptBeanFactory.getBean(scriptFactoryBeanName, ScriptFactory.class);
         ScriptSource scriptSource = this.getScriptSource(scriptFactoryBeanName, scriptFactory.getScriptSourceLocator());
         boolean isFactoryBean = false;

         try {
            Class scriptedObjectType = scriptFactory.getScriptedObjectType(scriptSource);
            if (scriptedObjectType != null) {
               isFactoryBean = FactoryBean.class.isAssignableFrom(scriptedObjectType);
            }
         } catch (Exception var15) {
            throw new BeanCreationException(beanName, "Could not determine scripted object type for " + scriptFactory, var15);
         }

         long refreshCheckDelay = this.resolveRefreshCheckDelay(bd);
         if (refreshCheckDelay < 0L) {
            if (isFactoryBean) {
               scriptedObjectBeanName = "&" + scriptedObjectBeanName;
            }

            return this.scriptBeanFactory.getBean(scriptedObjectBeanName);
         } else {
            Class[] interfaces = scriptFactory.getScriptInterfaces();
            RefreshableScriptTargetSource ts = new RefreshableScriptTargetSource(this.scriptBeanFactory, scriptedObjectBeanName, scriptFactory, scriptSource, isFactoryBean);
            boolean proxyTargetClass = this.resolveProxyTargetClass(bd);
            String language = (String)bd.getAttribute(LANGUAGE_ATTRIBUTE);
            if (proxyTargetClass && (language == null || !language.equals("groovy"))) {
               throw new BeanDefinitionValidationException("Cannot use proxyTargetClass=true with script beans where language is not 'groovy': '" + language + "'");
            } else {
               ts.setRefreshCheckDelay(refreshCheckDelay);
               return this.createRefreshableProxy(ts, interfaces, proxyTargetClass);
            }
         }
      }
   }

   protected void prepareScriptBeans(BeanDefinition bd, String scriptFactoryBeanName, String scriptedObjectBeanName) {
      synchronized(this.scriptBeanFactory) {
         if (!this.scriptBeanFactory.containsBeanDefinition(scriptedObjectBeanName)) {
            this.scriptBeanFactory.registerBeanDefinition(scriptFactoryBeanName, this.createScriptFactoryBeanDefinition(bd));
            ScriptFactory scriptFactory = (ScriptFactory)this.scriptBeanFactory.getBean(scriptFactoryBeanName, ScriptFactory.class);
            ScriptSource scriptSource = this.getScriptSource(scriptFactoryBeanName, scriptFactory.getScriptSourceLocator());
            Class[] interfaces = scriptFactory.getScriptInterfaces();
            Class[] scriptedInterfaces = interfaces;
            if (scriptFactory.requiresConfigInterface() && !bd.getPropertyValues().isEmpty()) {
               Class configInterface = this.createConfigInterface(bd, interfaces);
               scriptedInterfaces = (Class[])ObjectUtils.addObjectToArray(interfaces, configInterface);
            }

            BeanDefinition objectBd = this.createScriptedObjectBeanDefinition(bd, scriptFactoryBeanName, scriptSource, scriptedInterfaces);
            long refreshCheckDelay = this.resolveRefreshCheckDelay(bd);
            if (refreshCheckDelay >= 0L) {
               objectBd.setScope("prototype");
            }

            this.scriptBeanFactory.registerBeanDefinition(scriptedObjectBeanName, objectBd);
         }

      }
   }

   protected long resolveRefreshCheckDelay(BeanDefinition beanDefinition) {
      long refreshCheckDelay = this.defaultRefreshCheckDelay;
      Object attributeValue = beanDefinition.getAttribute(REFRESH_CHECK_DELAY_ATTRIBUTE);
      if (attributeValue instanceof Number) {
         refreshCheckDelay = ((Number)attributeValue).longValue();
      } else if (attributeValue instanceof String) {
         refreshCheckDelay = Long.parseLong((String)attributeValue);
      } else if (attributeValue != null) {
         throw new BeanDefinitionStoreException("Invalid refresh check delay attribute [" + REFRESH_CHECK_DELAY_ATTRIBUTE + "] with value '" + attributeValue + "': needs to be of type Number or String");
      }

      return refreshCheckDelay;
   }

   protected boolean resolveProxyTargetClass(BeanDefinition beanDefinition) {
      boolean proxyTargetClass = this.defaultProxyTargetClass;
      Object attributeValue = beanDefinition.getAttribute(PROXY_TARGET_CLASS_ATTRIBUTE);
      if (attributeValue instanceof Boolean) {
         proxyTargetClass = (Boolean)attributeValue;
      } else if (attributeValue instanceof String) {
         proxyTargetClass = Boolean.valueOf((String)attributeValue);
      } else if (attributeValue != null) {
         throw new BeanDefinitionStoreException("Invalid proxy target class attribute [" + PROXY_TARGET_CLASS_ATTRIBUTE + "] with value '" + attributeValue + "': needs to be of type Boolean or String");
      }

      return proxyTargetClass;
   }

   protected BeanDefinition createScriptFactoryBeanDefinition(BeanDefinition bd) {
      GenericBeanDefinition scriptBd = new GenericBeanDefinition();
      scriptBd.setBeanClassName(bd.getBeanClassName());
      scriptBd.getConstructorArgumentValues().addArgumentValues(bd.getConstructorArgumentValues());
      return scriptBd;
   }

   protected ScriptSource getScriptSource(String beanName, String scriptSourceLocator) {
      synchronized(this.scriptSourceCache) {
         ScriptSource scriptSource = (ScriptSource)this.scriptSourceCache.get(beanName);
         if (scriptSource == null) {
            scriptSource = this.convertToScriptSource(beanName, scriptSourceLocator, this.resourceLoader);
            this.scriptSourceCache.put(beanName, scriptSource);
         }

         return scriptSource;
      }
   }

   protected ScriptSource convertToScriptSource(String beanName, String scriptSourceLocator, ResourceLoader resourceLoader) {
      return (ScriptSource)(scriptSourceLocator.startsWith("inline:") ? new StaticScriptSource(scriptSourceLocator.substring("inline:".length()), beanName) : new ResourceScriptSource(resourceLoader.getResource(scriptSourceLocator)));
   }

   protected Class createConfigInterface(BeanDefinition bd, @Nullable Class[] interfaces) {
      InterfaceMaker maker = new InterfaceMaker();
      PropertyValue[] pvs = bd.getPropertyValues().getPropertyValues();
      PropertyValue[] var5 = pvs;
      int var6 = pvs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         PropertyValue pv = var5[var7];
         String propertyName = pv.getName();
         Class propertyType = BeanUtils.findPropertyType(propertyName, interfaces);
         String setterName = "set" + StringUtils.capitalize(propertyName);
         Signature signature = new Signature(setterName, Type.VOID_TYPE, new Type[]{Type.getType(propertyType)});
         maker.add(signature, new Type[0]);
      }

      Signature signature;
      if (bd.getInitMethodName() != null) {
         signature = new Signature(bd.getInitMethodName(), Type.VOID_TYPE, new Type[0]);
         maker.add(signature, new Type[0]);
      }

      if (StringUtils.hasText(bd.getDestroyMethodName())) {
         signature = new Signature(bd.getDestroyMethodName(), Type.VOID_TYPE, new Type[0]);
         maker.add(signature, new Type[0]);
      }

      return maker.create();
   }

   protected Class createCompositeInterface(Class[] interfaces) {
      return ClassUtils.createCompositeInterface(interfaces, this.beanClassLoader);
   }

   protected BeanDefinition createScriptedObjectBeanDefinition(BeanDefinition bd, String scriptFactoryBeanName, ScriptSource scriptSource, @Nullable Class[] interfaces) {
      GenericBeanDefinition objectBd = new GenericBeanDefinition(bd);
      objectBd.setFactoryBeanName(scriptFactoryBeanName);
      objectBd.setFactoryMethodName("getScriptedObject");
      objectBd.getConstructorArgumentValues().clear();
      objectBd.getConstructorArgumentValues().addIndexedArgumentValue(0, (Object)scriptSource);
      objectBd.getConstructorArgumentValues().addIndexedArgumentValue(1, (Object)interfaces);
      return objectBd;
   }

   protected Object createRefreshableProxy(TargetSource ts, @Nullable Class[] interfaces, boolean proxyTargetClass) {
      ProxyFactory proxyFactory = new ProxyFactory();
      proxyFactory.setTargetSource(ts);
      ClassLoader classLoader = this.beanClassLoader;
      if (interfaces != null) {
         proxyFactory.setInterfaces(interfaces);
      } else {
         Class targetClass = ts.getTargetClass();
         if (targetClass != null) {
            proxyFactory.setInterfaces(ClassUtils.getAllInterfacesForClass(targetClass, this.beanClassLoader));
         }
      }

      if (proxyTargetClass) {
         classLoader = null;
         proxyFactory.setProxyTargetClass(true);
      }

      DelegatingIntroductionInterceptor introduction = new DelegatingIntroductionInterceptor(ts);
      introduction.suppressInterface(TargetSource.class);
      proxyFactory.addAdvice(introduction);
      return proxyFactory.getProxy(classLoader);
   }

   public void destroy() {
      this.scriptBeanFactory.destroySingletons();
   }
}
