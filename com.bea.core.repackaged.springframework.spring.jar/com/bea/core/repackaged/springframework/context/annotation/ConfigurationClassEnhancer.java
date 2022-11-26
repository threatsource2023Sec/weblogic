package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.scope.ScopedProxyFactoryBean;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.SimpleInstantiationStrategy;
import com.bea.core.repackaged.springframework.cglib.core.ClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.DefaultGeneratorStrategy;
import com.bea.core.repackaged.springframework.cglib.core.SpringNamingPolicy;
import com.bea.core.repackaged.springframework.cglib.proxy.Callback;
import com.bea.core.repackaged.springframework.cglib.proxy.CallbackFilter;
import com.bea.core.repackaged.springframework.cglib.proxy.Enhancer;
import com.bea.core.repackaged.springframework.cglib.proxy.Factory;
import com.bea.core.repackaged.springframework.cglib.proxy.MethodInterceptor;
import com.bea.core.repackaged.springframework.cglib.proxy.MethodProxy;
import com.bea.core.repackaged.springframework.cglib.proxy.NoOp;
import com.bea.core.repackaged.springframework.cglib.transform.ClassEmitterTransformer;
import com.bea.core.repackaged.springframework.cglib.transform.TransformingClassGenerator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.SpringObjenesis;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class ConfigurationClassEnhancer {
   private static final Callback[] CALLBACKS;
   private static final ConditionalCallbackFilter CALLBACK_FILTER;
   private static final String BEAN_FACTORY_FIELD = "$$beanFactory";
   private static final Log logger;
   private static final SpringObjenesis objenesis;

   public Class enhance(Class configClass, @Nullable ClassLoader classLoader) {
      if (EnhancedConfiguration.class.isAssignableFrom(configClass)) {
         if (logger.isDebugEnabled()) {
            logger.debug(String.format("Ignoring request to enhance %s as it has already been enhanced. This usually indicates that more than one ConfigurationClassPostProcessor has been registered (e.g. via <context:annotation-config>). This is harmless, but you may want check your configuration and remove one CCPP if possible", configClass.getName()));
         }

         return configClass;
      } else {
         Class enhancedClass = this.createClass(this.newEnhancer(configClass, classLoader));
         if (logger.isTraceEnabled()) {
            logger.trace(String.format("Successfully enhanced %s; enhanced class name is: %s", configClass.getName(), enhancedClass.getName()));
         }

         return enhancedClass;
      }
   }

   private Enhancer newEnhancer(Class configSuperClass, @Nullable ClassLoader classLoader) {
      Enhancer enhancer = new Enhancer();
      enhancer.setSuperclass(configSuperClass);
      enhancer.setInterfaces(new Class[]{EnhancedConfiguration.class});
      enhancer.setUseFactory(false);
      enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
      enhancer.setStrategy(new BeanFactoryAwareGeneratorStrategy(classLoader));
      enhancer.setCallbackFilter(CALLBACK_FILTER);
      enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());
      return enhancer;
   }

   private Class createClass(Enhancer enhancer) {
      Class subclass = enhancer.createClass();
      Enhancer.registerStaticCallbacks(subclass, CALLBACKS);
      return subclass;
   }

   static {
      CALLBACKS = new Callback[]{new BeanMethodInterceptor(), new BeanFactoryAwareMethodInterceptor(), NoOp.INSTANCE};
      CALLBACK_FILTER = new ConditionalCallbackFilter(CALLBACKS);
      logger = LogFactory.getLog(ConfigurationClassEnhancer.class);
      objenesis = new SpringObjenesis();
   }

   private static class BeanMethodInterceptor implements MethodInterceptor, ConditionalCallback {
      private BeanMethodInterceptor() {
      }

      @Nullable
      public Object intercept(Object enhancedConfigInstance, Method beanMethod, Object[] beanMethodArgs, MethodProxy cglibMethodProxy) throws Throwable {
         ConfigurableBeanFactory beanFactory = this.getBeanFactory(enhancedConfigInstance);
         String beanName = BeanAnnotationHelper.determineBeanNameFor(beanMethod);
         if (BeanAnnotationHelper.isScopedProxy(beanMethod)) {
            String scopedBeanName = ScopedProxyCreator.getTargetBeanName(beanName);
            if (beanFactory.isCurrentlyInCreation(scopedBeanName)) {
               beanName = scopedBeanName;
            }
         }

         if (this.factoryContainsBean(beanFactory, "&" + beanName) && this.factoryContainsBean(beanFactory, beanName)) {
            Object factoryBean = beanFactory.getBean("&" + beanName);
            if (!(factoryBean instanceof ScopedProxyFactoryBean)) {
               return this.enhanceFactoryBean(factoryBean, beanMethod.getReturnType(), beanFactory, beanName);
            }
         }

         if (this.isCurrentlyInvokedFactoryMethod(beanMethod)) {
            if (ConfigurationClassEnhancer.logger.isInfoEnabled() && BeanFactoryPostProcessor.class.isAssignableFrom(beanMethod.getReturnType())) {
               ConfigurationClassEnhancer.logger.info(String.format("@Bean method %s.%s is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.", beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName()));
            }

            return cglibMethodProxy.invokeSuper(enhancedConfigInstance, beanMethodArgs);
         } else {
            return this.resolveBeanReference(beanMethod, beanMethodArgs, beanFactory, beanName);
         }
      }

      private Object resolveBeanReference(Method beanMethod, Object[] beanMethodArgs, ConfigurableBeanFactory beanFactory, String beanName) {
         boolean alreadyInCreation = beanFactory.isCurrentlyInCreation(beanName);

         Object var20;
         try {
            if (alreadyInCreation) {
               beanFactory.setCurrentlyInCreation(beanName, false);
            }

            boolean useArgs = !ObjectUtils.isEmpty(beanMethodArgs);
            if (useArgs && beanFactory.isSingleton(beanName)) {
               Object[] var7 = beanMethodArgs;
               int var8 = beanMethodArgs.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Object arg = var7[var9];
                  if (arg == null) {
                     useArgs = false;
                     break;
                  }
               }
            }

            Object beanInstance = useArgs ? beanFactory.getBean(beanName, beanMethodArgs) : beanFactory.getBean(beanName);
            if (!ClassUtils.isAssignableValue(beanMethod.getReturnType(), beanInstance)) {
               if (!beanInstance.equals((Object)null)) {
                  String msg = String.format("@Bean method %s.%s called as bean reference for type [%s] but overridden by non-compatible bean instance of type [%s].", beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName(), beanMethod.getReturnType().getName(), beanInstance.getClass().getName());

                  try {
                     BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
                     msg = msg + " Overriding bean of same name declared in: " + beanDefinition.getResourceDescription();
                  } catch (NoSuchBeanDefinitionException var14) {
                  }

                  throw new IllegalStateException(msg);
               }

               if (ConfigurationClassEnhancer.logger.isDebugEnabled()) {
                  ConfigurationClassEnhancer.logger.debug(String.format("@Bean method %s.%s called as bean reference for type [%s] returned null bean; resolving to null value.", beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName(), beanMethod.getReturnType().getName()));
               }

               beanInstance = null;
            }

            Method currentlyInvoked = SimpleInstantiationStrategy.getCurrentlyInvokedFactoryMethod();
            if (currentlyInvoked != null) {
               String outerBeanName = BeanAnnotationHelper.determineBeanNameFor(currentlyInvoked);
               beanFactory.registerDependentBean(beanName, outerBeanName);
            }

            var20 = beanInstance;
         } finally {
            if (alreadyInCreation) {
               beanFactory.setCurrentlyInCreation(beanName, true);
            }

         }

         return var20;
      }

      public boolean isMatch(Method candidateMethod) {
         return candidateMethod.getDeclaringClass() != Object.class && !ConfigurationClassEnhancer.BeanFactoryAwareMethodInterceptor.isSetBeanFactory(candidateMethod) && BeanAnnotationHelper.isBeanAnnotated(candidateMethod);
      }

      private ConfigurableBeanFactory getBeanFactory(Object enhancedConfigInstance) {
         Field field = ReflectionUtils.findField(enhancedConfigInstance.getClass(), "$$beanFactory");
         Assert.state(field != null, "Unable to find generated bean factory field");
         Object beanFactory = ReflectionUtils.getField(field, enhancedConfigInstance);
         Assert.state(beanFactory != null, "BeanFactory has not been injected into @Configuration class");
         Assert.state(beanFactory instanceof ConfigurableBeanFactory, "Injected BeanFactory is not a ConfigurableBeanFactory");
         return (ConfigurableBeanFactory)beanFactory;
      }

      private boolean factoryContainsBean(ConfigurableBeanFactory beanFactory, String beanName) {
         return beanFactory.containsBean(beanName) && !beanFactory.isCurrentlyInCreation(beanName);
      }

      private boolean isCurrentlyInvokedFactoryMethod(Method method) {
         Method currentlyInvoked = SimpleInstantiationStrategy.getCurrentlyInvokedFactoryMethod();
         return currentlyInvoked != null && method.getName().equals(currentlyInvoked.getName()) && Arrays.equals(method.getParameterTypes(), currentlyInvoked.getParameterTypes());
      }

      private Object enhanceFactoryBean(Object factoryBean, Class exposedType, ConfigurableBeanFactory beanFactory, String beanName) {
         try {
            Class clazz = factoryBean.getClass();
            boolean finalClass = Modifier.isFinal(clazz.getModifiers());
            boolean finalMethod = Modifier.isFinal(clazz.getMethod("getObject").getModifiers());
            if (finalClass || finalMethod) {
               if (exposedType.isInterface()) {
                  if (ConfigurationClassEnhancer.logger.isTraceEnabled()) {
                     ConfigurationClassEnhancer.logger.trace("Creating interface proxy for FactoryBean '" + beanName + "' of type [" + clazz.getName() + "] for use within another @Bean method because its " + (finalClass ? "implementation class" : "getObject() method") + " is final: Otherwise a getObject() call would not be routed to the factory.");
                  }

                  return this.createInterfaceProxyForFactoryBean(factoryBean, exposedType, beanFactory, beanName);
               }

               if (ConfigurationClassEnhancer.logger.isDebugEnabled()) {
                  ConfigurationClassEnhancer.logger.debug("Unable to proxy FactoryBean '" + beanName + "' of type [" + clazz.getName() + "] for use within another @Bean method because its " + (finalClass ? "implementation class" : "getObject() method") + " is final: A getObject() call will NOT be routed to the factory. Consider declaring the return type as a FactoryBean interface.");
               }

               return factoryBean;
            }
         } catch (NoSuchMethodException var8) {
         }

         return this.createCglibProxyForFactoryBean(factoryBean, beanFactory, beanName);
      }

      private Object createInterfaceProxyForFactoryBean(Object factoryBean, Class interfaceType, ConfigurableBeanFactory beanFactory, String beanName) {
         return Proxy.newProxyInstance(factoryBean.getClass().getClassLoader(), new Class[]{interfaceType}, (proxy, method, args) -> {
            return method.getName().equals("getObject") && args == null ? beanFactory.getBean(beanName) : ReflectionUtils.invokeMethod(method, factoryBean, args);
         });
      }

      private Object createCglibProxyForFactoryBean(Object factoryBean, ConfigurableBeanFactory beanFactory, String beanName) {
         Enhancer enhancer = new Enhancer();
         enhancer.setSuperclass(factoryBean.getClass());
         enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
         enhancer.setCallbackType(MethodInterceptor.class);
         Class fbClass = enhancer.createClass();
         Object fbProxy = null;
         if (ConfigurationClassEnhancer.objenesis.isWorthTrying()) {
            try {
               fbProxy = ConfigurationClassEnhancer.objenesis.newInstance(fbClass, enhancer.getUseCache());
            } catch (ObjenesisException var9) {
               ConfigurationClassEnhancer.logger.debug("Unable to instantiate enhanced FactoryBean using Objenesis, falling back to regular construction", var9);
            }
         }

         if (fbProxy == null) {
            try {
               fbProxy = ReflectionUtils.accessibleConstructor(fbClass).newInstance();
            } catch (Throwable var8) {
               throw new IllegalStateException("Unable to instantiate enhanced FactoryBean using Objenesis, and regular FactoryBean instantiation via default constructor fails as well", var8);
            }
         }

         ((Factory)fbProxy).setCallback(0, (obj, method, args, proxy) -> {
            return method.getName().equals("getObject") && args.length == 0 ? beanFactory.getBean(beanName) : proxy.invoke(factoryBean, args);
         });
         return fbProxy;
      }

      // $FF: synthetic method
      BeanMethodInterceptor(Object x0) {
         this();
      }
   }

   private static class BeanFactoryAwareMethodInterceptor implements MethodInterceptor, ConditionalCallback {
      private BeanFactoryAwareMethodInterceptor() {
      }

      @Nullable
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
         Field field = ReflectionUtils.findField(obj.getClass(), "$$beanFactory");
         Assert.state(field != null, "Unable to find generated BeanFactory field");
         field.set(obj, args[0]);
         return BeanFactoryAware.class.isAssignableFrom(ClassUtils.getUserClass(obj.getClass().getSuperclass())) ? proxy.invokeSuper(obj, args) : null;
      }

      public boolean isMatch(Method candidateMethod) {
         return isSetBeanFactory(candidateMethod);
      }

      public static boolean isSetBeanFactory(Method candidateMethod) {
         return candidateMethod.getName().equals("setBeanFactory") && candidateMethod.getParameterCount() == 1 && BeanFactory.class == candidateMethod.getParameterTypes()[0] && BeanFactoryAware.class.isAssignableFrom(candidateMethod.getDeclaringClass());
      }

      // $FF: synthetic method
      BeanFactoryAwareMethodInterceptor(Object x0) {
         this();
      }
   }

   private static class BeanFactoryAwareGeneratorStrategy extends DefaultGeneratorStrategy {
      @Nullable
      private final ClassLoader classLoader;

      public BeanFactoryAwareGeneratorStrategy(@Nullable ClassLoader classLoader) {
         this.classLoader = classLoader;
      }

      protected ClassGenerator transform(ClassGenerator cg) throws Exception {
         ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
            public void end_class() {
               this.declare_field(1, "$$beanFactory", Type.getType(BeanFactory.class), (Object)null);
               super.end_class();
            }
         };
         return new TransformingClassGenerator(cg, transformer);
      }

      public byte[] generate(ClassGenerator cg) throws Exception {
         if (this.classLoader == null) {
            return super.generate(cg);
         } else {
            Thread currentThread = Thread.currentThread();

            ClassLoader threadContextClassLoader;
            try {
               threadContextClassLoader = currentThread.getContextClassLoader();
            } catch (Throwable var9) {
               return super.generate(cg);
            }

            boolean overrideClassLoader = !this.classLoader.equals(threadContextClassLoader);
            if (overrideClassLoader) {
               currentThread.setContextClassLoader(this.classLoader);
            }

            byte[] var5;
            try {
               var5 = super.generate(cg);
            } finally {
               if (overrideClassLoader) {
                  currentThread.setContextClassLoader(threadContextClassLoader);
               }

            }

            return var5;
         }
      }
   }

   private static class ConditionalCallbackFilter implements CallbackFilter {
      private final Callback[] callbacks;
      private final Class[] callbackTypes;

      public ConditionalCallbackFilter(Callback[] callbacks) {
         this.callbacks = callbacks;
         this.callbackTypes = new Class[callbacks.length];

         for(int i = 0; i < callbacks.length; ++i) {
            this.callbackTypes[i] = callbacks[i].getClass();
         }

      }

      public int accept(Method method) {
         for(int i = 0; i < this.callbacks.length; ++i) {
            Callback callback = this.callbacks[i];
            if (!(callback instanceof ConditionalCallback) || ((ConditionalCallback)callback).isMatch(method)) {
               return i;
            }
         }

         throw new IllegalStateException("No callback available for method " + method.getName());
      }

      public Class[] getCallbackTypes() {
         return this.callbackTypes;
      }
   }

   private interface ConditionalCallback extends Callback {
      boolean isMatch(Method var1);
   }

   public interface EnhancedConfiguration extends BeanFactoryAware {
   }
}
