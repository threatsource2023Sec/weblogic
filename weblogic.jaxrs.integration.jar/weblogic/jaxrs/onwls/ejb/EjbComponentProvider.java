package weblogic.jaxrs.onwls.ejb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import javax.annotation.Priority;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.hk2.api.ClassAnalyzer;
import org.glassfish.jersey.ext.cdi1x.internal.CdiUtil;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.ComponentProvider;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.spi.BeanInfo;
import weblogic.ejb.spi.DeploymentInfo;
import weblogic.ejb.spi.Injector;
import weblogic.jaxrs.integration.internal.JAXRSIntegrationLogger;
import weblogic.servlet.internal.WebAppServletContext;

@Priority(5001)
public final class EjbComponentProvider implements ComponentProvider, ResourceMethodInvocationHandlerProvider, Injector {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugRestJersey2Integration");
   private InitialContext initialContext;
   private List ejbBeanInfo;
   private final Set ejbBeanInfoToIntercept = Collections.newSetFromMap(new ConcurrentHashMap());
   private final List libNames = new CopyOnWriteArrayList();
   static final String EJB_CLASS_ANALYZER = "CdiInjecteeSkippingClassAnalyzer";
   private static final Set EjbComponentAnnotations = Collections.unmodifiableSet(new HashSet() {
      {
         this.add("javax.ejb.Stateful");
         this.add("javax.ejb.Stateless");
         this.add("javax.ejb.Singleton");
      }
   });
   private InjectionManager injectionManager = null;

   public void initialize(InjectionManager injectionManager) {
      this.injectionManager = injectionManager;
      InstanceBinding descriptor = (InstanceBinding)Bindings.service(this).to(ResourceMethodInvocationHandlerProvider.class);
      this.injectionManager.register(descriptor);
      this.initialContext = getInitialContext();
      this.ejbBeanInfo = this.getEjbBeanInfo(injectionManager);
      this.computePotentialEjbSubresources();
   }

   private void computePotentialEjbSubresources() {
      Iterator var1 = this.ejbBeanInfo.iterator();

      while(var1.hasNext()) {
         BeanInfo bean = (BeanInfo)var1.next();
         if (this.hasJaxRsInjectionPoints(bean.getBeanClass())) {
            this.ejbBeanInfoToIntercept.add(bean);
         }
      }

   }

   private boolean hasJaxRsInjectionPoints(Class clazz) {
      Field[] var2 = clazz.getDeclaredFields();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         Field f = var2[var4];
         if (f.isAnnotationPresent(Context.class)) {
            return true;
         }
      }

      Method[] var6 = clazz.getDeclaredMethods();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         Method m = var6[var4];
         if (m.isAnnotationPresent(Context.class)) {
            return true;
         }
      }

      if (clazz != Object.class) {
         return this.hasJaxRsInjectionPoints(clazz.getSuperclass());
      } else {
         return false;
      }
   }

   private List getEjbBeanInfo(InjectionManager injectionManager) {
      List result = new LinkedList();
      WebAppServletContext wlsServletContext = (WebAppServletContext)injectionManager.getInstance(ServletContext.class);
      if (wlsServletContext == null) {
         return result;
      } else {
         ApplicationContextInternal internal = wlsServletContext.getApplicationContext();
         Module[] var5 = internal.getApplicationModules();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Module m = var5[var7];
            if ("ejb".equals(m.getType()) || "war".equals(m.getType())) {
               String jarId = m.getId();
               String moduleName = null;
               DeploymentInfo deploymentInfo = getDeploymentInfo(internal, jarId);
               if (deploymentInfo != null) {
                  result.addAll(deploymentInfo.getBeanInfos());
                  moduleName = deploymentInfo.getModuleName();
               }

               if (moduleName == null) {
                  moduleName = !jarId.endsWith(".jar") && !jarId.endsWith(".war") ? jarId : jarId.substring(0, jarId.length() - 4);
               }

               this.libNames.add(moduleName);
            }
         }

         DeploymentInfo deploymentInfo = getDeploymentInfo(internal, wlsServletContext.getId());
         if (deploymentInfo != null) {
            result.addAll(deploymentInfo.getBeanInfos());
         }

         return result;
      }
   }

   private static DeploymentInfo getDeploymentInfo(ApplicationContextInternal appCtx, String moduleId) {
      ModuleContext ctx = appCtx.getModuleContext(moduleId);
      if (ctx == null) {
         return null;
      } else {
         ModuleRegistry rx = ctx.getRegistry();
         return rx == null ? null : (DeploymentInfo)rx.get(DeploymentInfo.class.getName());
      }
   }

   private void registerEjbInterceptor(Class clazz) {
      BeanInfo bean = this.findBeanInfo(clazz);
      if (bean != null) {
         this.ejbBeanInfoToIntercept.add(bean);
      }

   }

   private BeanInfo findBeanInfo(Class clazz) {
      Iterator var2 = this.ejbBeanInfo.iterator();

      BeanInfo bean;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         bean = (BeanInfo)var2.next();
      } while(bean.getBeanClass() != clazz);

      return bean;
   }

   public boolean bind(Class component, Set providerContracts) {
      String componentClassName = String.valueOf(component);
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("Class, " + componentClassName + ", is being checked with Jersey EJB component provider.");
      }

      if (this.injectionManager == null) {
         throw new IllegalStateException(JAXRSIntegrationLogger.getEJB_COMPONENT_PROVIDER_NOT_INITIALIZED_PROPERLY());
      } else if (!this.isEjbComponent(component)) {
         return false;
      } else {
         this.registerEjbInterceptor(component);
         Binding binding = Bindings.supplier(new EjbFactory(component, this.initialContext, this)).to(component).to(providerContracts);
         this.injectionManager.register(binding);
         JAXRSIntegrationLogger.logEJB_CLASS_BOUND_WITH_CDI(componentClassName);
         return true;
      }
   }

   public void done() {
      Iterator var1 = this.ejbBeanInfoToIntercept.iterator();

      while(var1.hasNext()) {
         BeanInfo bean = (BeanInfo)var1.next();
         bean.registerInjector(this);
      }

      if (CdiUtil.getBeanManager() == null) {
         this.bindHk2ClassAnalyzer();
      }

      this.registerEjbExceptionMapper();
   }

   private void bindHk2ClassAnalyzer() {
      final ClassAnalyzer defaultClassAnalyzer = (ClassAnalyzer)this.injectionManager.getInstance(ClassAnalyzer.class, "default");
      Binder binder = new AbstractBinder() {
         protected void configure() {
            ((InstanceBinding)((InstanceBinding)this.bind(defaultClassAnalyzer).analyzeWith("default")).to(ClassAnalyzer.class)).named("CdiInjecteeSkippingClassAnalyzer");
         }
      };
      this.injectionManager.register(binder);
   }

   private void registerEjbExceptionMapper() {
      this.injectionManager.register(new AbstractBinder() {
         protected void configure() {
            ((ClassBinding)this.bind(EjbExceptionMapper.class).to(ExceptionMapper.class)).in(Singleton.class);
         }
      });
   }

   private boolean isEjbComponent(Class component) {
      Annotation[] var2 = component.getAnnotations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation a = var2[var4];
         if (EjbComponentAnnotations.contains(a.annotationType().getName())) {
            return true;
         }
      }

      return false;
   }

   public InvocationHandler create(Invocable method) {
      Class resourceClass = method.getHandler().getHandlerClass();
      if (resourceClass != null && this.isEjbComponent(resourceClass)) {
         Method handlingMethod = method.getHandlingMethod();
         Iterator var4 = this.remoteAndLocalIfaces(resourceClass).iterator();

         while(var4.hasNext()) {
            Class iFace = (Class)var4.next();

            try {
               final Method iFaceMethod = iFace.getDeclaredMethod(handlingMethod.getName(), handlingMethod.getParameterTypes());
               if (iFaceMethod != null) {
                  return new InvocationHandler() {
                     public Object invoke(Object target, Method ignored, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        return iFaceMethod.invoke(target, args);
                     }
                  };
               }
            } catch (NoSuchMethodException var7) {
               this.logLookupException(handlingMethod, resourceClass, iFace, var7);
            } catch (SecurityException var8) {
               this.logLookupException(handlingMethod, resourceClass, iFace, var8);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public void injectMembers(Object o) {
      this.injectionManager.inject(o, "CdiInjecteeSkippingClassAnalyzer");
   }

   private void logLookupException(Method method, Class component, Class iFace, Exception ex) {
      JAXRSIntegrationLogger.logEJB_INTERFACE_HANDLING_METHOD_LOOKUP_EXCEPTION(method, String.valueOf(component), String.valueOf(iFace), ex);
   }

   private List remoteAndLocalIfaces(Class resourceClass) {
      List allLocalOrRemoteIfaces = new LinkedList();
      if (resourceClass.isAnnotationPresent(Remote.class)) {
         allLocalOrRemoteIfaces.addAll(Arrays.asList(((Remote)resourceClass.getAnnotation(Remote.class)).value()));
      }

      if (resourceClass.isAnnotationPresent(Local.class)) {
         allLocalOrRemoteIfaces.addAll(Arrays.asList(((Local)resourceClass.getAnnotation(Local.class)).value()));
      }

      Class[] var3 = resourceClass.getInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class i = var3[var5];
         if (i.isAnnotationPresent(Remote.class) || i.isAnnotationPresent(Local.class)) {
            allLocalOrRemoteIfaces.add(i);
         }
      }

      return allLocalOrRemoteIfaces;
   }

   private static InitialContext getInitialContext() {
      try {
         return new InitialContext();
      } catch (Exception var1) {
         throw new IllegalStateException(JAXRSIntegrationLogger.getINITIAL_CONTEXT_NOT_AVAILABLE(), var1);
      }
   }

   private static Object lookup(InitialContext ic, Class c, String name, EjbComponentProvider provider) throws NamingException {
      try {
         return lookupSimpleForm(ic, name, provider);
      } catch (NamingException var5) {
         JAXRSIntegrationLogger.logEJB_CLASS_SIMPLE_LOOKUP_FAILED(c.getName(), var5);
         return lookupFullyQualifiedForm(ic, c, name, provider);
      }
   }

   private static Object lookupSimpleForm(InitialContext ic, String name, EjbComponentProvider provider) throws NamingException {
      if (provider.libNames.isEmpty()) {
         String jndiName = "java:module/" + name;
         return ic.lookup(jndiName);
      } else {
         NamingException ne = null;
         Iterator var4 = provider.libNames.iterator();

         while(var4.hasNext()) {
            String moduleName = (String)var4.next();
            String jndiName = (moduleName.startsWith("/") ? "java:app" : "java:app/") + moduleName + "/" + name;

            try {
               Object result = ic.lookup(jndiName);
               if (result != null) {
                  return result;
               }
            } catch (NamingException var9) {
               ne = var9;
            }
         }

         throw ne != null ? ne : new NamingException();
      }
   }

   private static Object lookupFullyQualifiedForm(InitialContext ic, Class c, String name, EjbComponentProvider provider) throws NamingException {
      if (provider.libNames.isEmpty()) {
         String jndiName = "java:module/" + name + "!" + c.getName();
         return ic.lookup(jndiName);
      } else {
         NamingException ne = null;
         Iterator var5 = provider.libNames.iterator();

         while(var5.hasNext()) {
            String moduleName = (String)var5.next();
            String jndiName = "java:app/" + moduleName + "/" + name + "!" + c.getName();

            try {
               Object result = ic.lookup(jndiName);
               if (result != null) {
                  return result;
               }
            } catch (NamingException var10) {
               ne = var10;
            }
         }

         throw ne != null ? ne : new NamingException();
      }
   }

   private static class EjbFactory implements Supplier {
      final InitialContext ctx;
      final Class clazz;
      final EjbComponentProvider ejbProvider;
      final String name;

      public Object get() {
         try {
            return EjbComponentProvider.lookup(this.ctx, this.clazz, this.name, this.ejbProvider);
         } catch (NamingException var2) {
            JAXRSIntegrationLogger.logException(var2);
            return null;
         }
      }

      public EjbFactory(Class rawType, InitialContext ctx, EjbComponentProvider ejbProvider) {
         this.clazz = rawType;
         this.ctx = ctx;
         this.ejbProvider = ejbProvider;
         this.name = this.figureOutName(rawType);
      }

      private String figureOutName(Class c) {
         String result = null;
         if (c.isAnnotationPresent(Stateless.class)) {
            result = ((Stateless)c.getAnnotation(Stateless.class)).name();
         } else if (c.isAnnotationPresent(javax.ejb.Singleton.class)) {
            result = ((javax.ejb.Singleton)c.getAnnotation(javax.ejb.Singleton.class)).name();
         }

         if (result == null || result.length() == 0) {
            result = c.getSimpleName();
         }

         return result;
      }
   }
}
