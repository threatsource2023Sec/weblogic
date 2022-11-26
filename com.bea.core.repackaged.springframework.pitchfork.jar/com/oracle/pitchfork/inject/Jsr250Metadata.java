package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.AbstractApplicationContext;
import com.oracle.pitchfork.JeeLogger;
import com.oracle.pitchfork.interfaces.ContextDataProvider;
import com.oracle.pitchfork.interfaces.LifecycleCallbackException;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBContext;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.omg.CORBA.ORB;

public class Jsr250Metadata implements Jsr250MetadataI {
   public static final String KEY = Jsr250Metadata.class.getName();
   protected static final String EJBCONTEXT_BINDING = "java:comp/EJBContext";
   protected static final String TIMERSERVICE_BINDING = "java:comp/TimerService";
   protected static final String USER_TRANSACTION_BINDING = "java:comp/UserTransaction";
   protected static final String ORB_BINDING = "java:comp/ORB";
   private static final String JNDI_COMPENV_PREFIX = "java:comp/env/";
   private static final String JNDI_APP_PREFIX = "java:";
   protected final Log log = LogFactory.getLog(this.getClass());
   private final String componentName;
   private final Class componentClass;
   private final DeploymentUnitMetadata deploymentUnitMetadata;
   private AbstractBeanDefinition beanDefinition;
   private final Map lifecycleCallbackMethods = new HashMap();
   private final List injections = new ArrayList();
   private ApplicationContext componentContext;
   private BeanDefinitionRegistry beanDefinitionRegistry;
   private Method timeoutMethod;
   private boolean hasScheduledTimeoutMethods = false;
   private ContextDataProvider ctxDataProvider;

   public Jsr250Metadata(DeploymentUnitMetadataI dum, String compName, Class componentClass) {
      this.componentName = compName;
      this.componentClass = componentClass;
      this.deploymentUnitMetadata = (DeploymentUnitMetadata)dum;
      this.deploymentUnitMetadata.registerDeployedComponentMetadata(this);
   }

   public String getComponentName() {
      return this.componentName;
   }

   public Class getComponentClass() {
      return this.componentClass;
   }

   public void setComponentContext(ApplicationContext compCtx, BeanDefinitionRegistry bdr) {
      this.componentContext = compCtx;
      this.beanDefinitionRegistry = bdr;
   }

   public void setBeanDefinition(AbstractBeanDefinition bd) {
      this.beanDefinition = bd;
      bd.setAttribute(KEY, this);
      if (!bd.hasBeanClass()) {
         if (this.log.isInfoEnabled()) {
            this.log.info("Class not specified in Spring configuration for '" + this.getComponentName() + "': taking from JEE metadata");
         }

         bd.setBeanClass(this.getComponentClass());
      }

   }

   public ApplicationContext getComponentContext() {
      return this.componentContext;
   }

   public void registerLifecycleEventCallbackMethod(LifecycleEvent le, Method m) {
      if (m != null) {
         ReflectionUtils.makeAccessible(m);
         List methods = (List)this.lifecycleCallbackMethods.get(le);
         if (methods == null) {
            methods = new ArrayList();
         }

         ((List)methods).add(m);
         this.lifecycleCallbackMethods.put(le, methods);
         if (this.log.isInfoEnabled()) {
            this.log.info("Component lifecycle method: Registered " + m + " on " + this.getClass().getName() + " for " + le);
         }
      }

   }

   public List getLifecycleEventCallbackMethod(LifecycleEvent le) {
      return (List)this.lifecycleCallbackMethods.get(le);
   }

   public AbstractBeanDefinition getBeanDefinition() {
      return this.beanDefinition;
   }

   public List getInjections() {
      return this.injections;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append("; hashCode=").append(this.hashCode());
      sb.append("; componentName='").append(this.componentName).append("'");
      sb.append(": injections=");
      sb.append(this.injections);
      sb.append("; beanDefinition=").append(this.beanDefinition);
      sb.append("; componentContext=").append(this.componentContext);
      return sb.toString();
   }

   public void inject(Object instance) {
      this.applyInjections(instance, this.injections);
   }

   protected void applyInjections(Object instance, List injections) {
      Iterator var3 = injections.iterator();

      while(var3.hasNext()) {
         InjectionI injection = (InjectionI)var3.next();

         try {
            if (injection.containsValue()) {
               this.performInjection(injection, instance, injection.getValue());
            } else if (injection.isOptional()) {
               try {
                  this.performInjection(injection, instance, this.resolveByName(injection));
               } catch (NamingException var6) {
               }
            } else {
               this.performInjection(injection, instance, this.resolve(injection));
            }
         } catch (NoSuchBeanDefinitionException var7) {
            throw new BeanCreationException(JeeLogger.logNoSuchBeanDefinition(injection), var7);
         }
      }

   }

   private void performInjection(InjectionI injection, Object instance, Object resolved) {
      if (this.log.isInfoEnabled()) {
         this.log.info("Performing " + injection.getInfo().getInjectionName() + " injection on component with name '" + this.componentName + "'; Resolved value is of class" + resolved.getClass().getName());
      }

      injection.apply(instance, resolved);
   }

   public void injectAndPostConstruct(Object instance) {
      this.inject(instance);
      this.invokePostConstructAndRegisterShutdownHook(instance);
   }

   protected void invokePostConstructAndRegisterShutdownHook(Object instance) {
      if (this.deploymentUnitMetadata.isInvokeLifecycleMethod()) {
         this.invokeLifecycleMethods(instance, LifecycleEvent.POST_CONSTRUCT);
      }

   }

   public Object resolve(InjectionI injection) {
      try {
         return this.resolveByName(injection);
      } catch (NamingException var7) {
         if (this.log.isErrorEnabled()) {
            this.log.error("Cannot find JNDI binding for '" + this.composeCompenvLookupLocation(injection.getName()) + "', " + var7.getMessage());
         }

         try {
            return this.componentContext.getBean(injection.getName());
         } catch (NoSuchBeanDefinitionException var6) {
            var6.initCause(var7);

            try {
               return this.resolveByType(injection.getType());
            } catch (NoSuchBeanDefinitionException var5) {
               var5.initCause(var6);
               throw var5;
            }
         }
      }
   }

   protected Object resolveByName(InjectionI injection) throws NamingException {
      if (injection.getValue() == null) {
         try {
            Class type = this.getClassForType(injection.getType().getName());
            if (EJBContext.class.isAssignableFrom(type)) {
               return this.jndiLookup("java:comp/EJBContext");
            }

            if (type == TimerService.class) {
               return this.jndiLookup("java:comp/TimerService");
            }

            if (type == UserTransaction.class) {
               this.cacheInjectionValue("java:comp/UserTransaction", injection);
            } else if (type == ORB.class) {
               this.cacheInjectionValue("java:comp/ORB", injection);
            }
         } catch (ClassNotFoundException var3) {
            throw new BeanCreationException(JeeLogger.logClassNotFound(injection), var3);
         }

         if (injection.getValue() != null) {
            return injection.getValue();
         }
      }

      return this.jndiLookup(this.composeCompenvLookupLocation(injection.getName()));
   }

   protected Object jndiLookup(String jndiName) throws NamingException {
      return InitialContext.doLookup(jndiName);
   }

   protected void cacheInjectionValue(String jndiName, InjectionI injection) throws NamingException {
      injection.setValue(this.jndiLookup(jndiName));
   }

   protected Class getClassForType(String typeName) throws ClassNotFoundException {
      if (typeName.indexOf(46) == -1) {
         if ("int".equals(typeName)) {
            return Integer.TYPE;
         }

         if ("float".equals(typeName)) {
            return Float.TYPE;
         }

         if ("double".equals(typeName)) {
            return Double.TYPE;
         }

         if ("char".equals(typeName)) {
            return Character.TYPE;
         }

         if ("boolean".equals(typeName)) {
            return Boolean.TYPE;
         }

         if ("byte".equals(typeName)) {
            return Byte.TYPE;
         }

         if ("long".equals(typeName)) {
            return Long.TYPE;
         }

         if ("short".equals(typeName)) {
            return Short.TYPE;
         }
      }

      return Class.forName(typeName, true, Thread.currentThread().getContextClassLoader());
   }

   private String composeCompenvLookupLocation(String name) {
      if (name.startsWith("java:")) {
         return name;
      } else {
         return name.startsWith("app/") ? "java:" + name : "java:comp/env/" + name;
      }
   }

   protected Object resolveByType(Class type) throws NoSuchBeanDefinitionException {
      return this.getUniqueInstanceOfType(this.componentContext, type);
   }

   public Object getUniqueInstanceOfType(ListableBeanFactory lbf, Class type) {
      Map beansOfType = BeanFactoryUtils.beansOfTypeIncludingAncestors(lbf, type);
      if (beansOfType.isEmpty()) {
         throw new NoSuchBeanDefinitionException(type, "No beans of type " + type.getName() + "; owner=" + lbf);
      } else if (beansOfType.size() > 1) {
         throw new NoSuchBeanDefinitionException(type, beansOfType.size() + " beans of type " + type.getName() + ": must be unique to support injection by inferred type");
      } else {
         return beansOfType.values().iterator().next();
      }
   }

   public void invokeLifecycleMethods(Object bean, LifecycleEvent le) {
      List lifecyleMethods = this.getLifecycleEventCallbackMethod(le);
      if (lifecyleMethods != null) {
         Iterator var4 = lifecyleMethods.iterator();

         while(var4.hasNext()) {
            Method m = (Method)var4.next();
            invokeLifecycleMethod(bean, m, (Object[])null);
         }
      }

   }

   public static void invokeTimeoutMethodInternal(Object bean, Method method, Object timer) {
      Object[] args;
      if (method.getParameterTypes().length == 0) {
         args = new Object[0];
      } else {
         args = new Object[]{timer};
      }

      try {
         method.invoke(bean, args);
      } catch (Exception var5) {
         throw new LifecycleCallbackException(JeeLogger.logTimeoutFailure(bean, method, args), var5);
      }
   }

   public void invokeTimeoutMethod(Object bean, Method m, Timer timer) {
      invokeTimeoutMethodInternal(bean, m, timer);
   }

   public static void invokeLifecycleMethod(Object bean, Method m, Object[] args) {
      if (m != null) {
         try {
            m.invoke(bean, args);
         } catch (Exception var4) {
            throw new LifecycleCallbackException(JeeLogger.logLifecycleCallbackFailure(bean, m, args), var4);
         }
      }

   }

   public DeploymentUnitMetadata getDeploymentUnitMetadata() {
      return this.deploymentUnitMetadata;
   }

   public void refresh() {
      if (this.componentContext instanceof AbstractApplicationContext) {
         ((AbstractApplicationContext)this.componentContext).refresh();
      }

   }

   public void setTimeoutMethod(Method timeoutMethod) {
      if (timeoutMethod != null) {
         ReflectionUtils.makeAccessible(timeoutMethod);
      }

      this.timeoutMethod = timeoutMethod;
   }

   public Method getTimeoutMethod() {
      return this.timeoutMethod;
   }

   public void setHasScheduledTimeouts(boolean hasScheduledTimeouts) {
      this.hasScheduledTimeoutMethods = hasScheduledTimeouts;
   }

   public boolean hasScheduledTimeouts() {
      return this.hasScheduledTimeoutMethods;
   }

   public ContextDataProvider getContextDataProvider() {
      return this.ctxDataProvider;
   }

   public void registerContextDataProvider(ContextDataProvider cdp) {
      this.ctxDataProvider = cdp;
   }
}
