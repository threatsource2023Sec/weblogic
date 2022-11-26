package weblogic.servlet.internal;

import com.oracle.pitchfork.interfaces.WebComponentContributorBroker;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.injection.J2eeComponentContributor;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.management.DeploymentException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.websocket.WebSocketListener;

public class WebComponentContributor extends J2eeComponentContributor implements WebComponentCreator {
   public static final String SPRING_WEB_XML_LOCATION = "/META-INF/spring-web.xml";
   private static final DebugLogger diLogger = DebugLogger.getDebugLogger("DebugWebAppDI");
   private WebAppBean webBean;
   private WebAppServletContext context;
   private boolean useDI = true;
   private ConcurrentHashMap diClasses = new ConcurrentHashMap();
   private boolean usesSpringExtensionModel = false;
   private WebComponentContributorBroker webCompContributorBroker;

   public WebComponentContributor(PitchforkContext pitchforkContext) {
      super(pitchforkContext);
      this.webCompContributorBroker = pitchforkContext.getPitchforkUtils().createWebComponentContributorBroker();
   }

   public void initialize(WebAppServletContext ctx) throws DeploymentException {
      this.context = ctx;
      this.webBean = ctx.getWebAppModule().getWebAppBean();
      this.useDI = WarUtils.isDIEnabled(this.webBean);
      if (!this.useDI) {
         dbg("Dependency injection is turned OFF for " + ctx);
      } else {
         this.checkDuplicatedCallback(this.webBean.getPostConstructs(), LifecycleEvent.POST_CONSTRUCT);
         this.checkDuplicatedCallback(this.webBean.getPreDestroys(), LifecycleEvent.PRE_DESTROY);
         String componentFactoryClassName = null;
         if (ctx.getWebAppModule().getWlWebAppBean() != null && ctx.getWebAppModule().getWlWebAppBean().getComponentFactoryClassName().length > 0) {
            componentFactoryClassName = ctx.getWebAppModule().getWlWebAppBean().getComponentFactoryClassName()[0];
         }

         this.usesSpringExtensionModel = this.pitchforkContext.isSpringComponentFactoryClassName();

         try {
            this.webCompContributorBroker.initialize(this.context.getServletClassLoader(), "/META-INF/spring-web.xml", PitchforkContext.getSynthesizedComponentFactoryClassName(componentFactoryClassName), this.usesSpringExtensionModel, this);
         } catch (Throwable var4) {
            diLogger.debug("Exception when creating spring bean factory" + StackTraceUtils.throwable2StackTrace(var4));
            throw new DeploymentException(var4);
         }
      }
   }

   protected void contribute(Jsr250MetadataI jsr250Metadata, J2eeEnvironmentBean environmentGroupBean) {
   }

   public Jsr250MetadataI getMetadata(String componentName) {
      throw new AssertionError("This method is not supported!");
   }

   static void dbg(String s) {
      if (diLogger.isDebugEnabled()) {
         diLogger.debug(s);
      }

   }

   protected void debug(String s) {
      dbg(s);
   }

   public void contribute(EnricherI enricher) {
      Debug.assertion(this.context != null, "not initialized");
      if (this.useDI) {
         Set injectableTargetClasses = super.getInjectableTargetClasses(this.webBean);
         List beanNames = Arrays.asList(enricher.getRegisteredBeanDefinitionNames());
         ServletBean[] var4 = this.webBean.getServlets();
         int var5 = var4.length;

         int var6;
         String clzName;
         for(var6 = 0; var6 < var5; ++var6) {
            ServletBean servletBean = var4[var6];
            clzName = servletBean.getServletClass();
            if (clzName != null) {
               if ("weblogic.websocket.internal.WebSocketServlet".equals(clzName)) {
                  ParamValueBean[] var9 = servletBean.getInitParams();
                  int var10 = var9.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     ParamValueBean pvBean = var9[var11];
                     if (WebSocketListener.class.getName().equals(pvBean.getParamName())) {
                        clzName = pvBean.getParamValue();
                        break;
                     }
                  }
               }

               if (beanNames.contains(clzName) || this.containsAssignableClass(injectableTargetClasses, clzName)) {
                  dbg("injecting metadata for Servlet " + clzName);
                  this.contribute(enricher, clzName);
               }
            }
         }

         FilterBean[] var13 = this.webBean.getFilters();
         var5 = var13.length;

         for(var6 = 0; var6 < var5; ++var6) {
            FilterBean filterBean = var13[var6];
            clzName = filterBean.getFilterClass();
            if (clzName != null && (beanNames.contains(clzName) || this.containsAssignableClass(injectableTargetClasses, clzName))) {
               dbg("injecting metadata for Filter " + clzName);
               this.contribute(enricher, clzName);
            }
         }

         ListenerBean[] var14 = this.webBean.getListeners();
         var5 = var14.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ListenerBean listenerBean = var14[var6];
            clzName = listenerBean.getListenerClass();
            if (clzName != null && (beanNames.contains(clzName) || this.containsAssignableClass(injectableTargetClasses, clzName))) {
               dbg("injecting metadata for Listener " + clzName);
               this.contribute(enricher, clzName);
            }
         }

         Iterator var15 = this.context.getHelper().getTagHandlers(false).iterator();

         while(true) {
            String clzName;
            do {
               do {
                  Object tagListener;
                  if (!var15.hasNext()) {
                     var15 = this.context.getHelper().getTagListeners(false).iterator();

                     while(true) {
                        do {
                           do {
                              if (!var15.hasNext()) {
                                 String facesConfigs = WarUtils.getFacesConfigFiles(this.webBean);
                                 Iterator var18 = this.context.getHelper().getManagedBeanClasses(facesConfigs).iterator();

                                 while(true) {
                                    String clzName;
                                    do {
                                       do {
                                          if (!var18.hasNext()) {
                                             return;
                                          }

                                          Object managedBean = var18.next();
                                          clzName = (String)managedBean;
                                       } while(clzName == null);
                                    } while(!beanNames.contains(clzName) && !this.containsAssignableClass(injectableTargetClasses, clzName));

                                    dbg("injecting metadata for managed bean" + clzName);
                                    this.contribute(enricher, clzName);
                                 }
                              }

                              tagListener = var15.next();
                              clzName = (String)tagListener;
                           } while(clzName == null);
                        } while(!beanNames.contains(clzName) && !this.containsAssignableClass(injectableTargetClasses, clzName));

                        dbg("injecting metadata for tld listener " + clzName);
                        this.contribute(enricher, clzName);
                     }
                  }

                  tagListener = var15.next();
                  clzName = (String)tagListener;
               } while(clzName == null);
            } while(!beanNames.contains(clzName) && !this.containsAssignableClass(injectableTargetClasses, clzName));

            dbg("injecting metadata for taglib handler " + clzName);
            this.contribute(enricher, clzName);
         }
      }
   }

   private boolean containsAssignableClass(Set set, String key) {
      if (set.contains(key)) {
         return true;
      } else {
         Class c = null;

         try {
            c = this.context.getServletClassLoader().loadClass(key);
         } catch (ClassNotFoundException var5) {
            return false;
         }

         do {
            if (c.equals(Object.class)) {
               return false;
            }

            c = c.getSuperclass();
         } while(!set.contains(c.getName()));

         return true;
      }
   }

   private void contribute(EnricherI enricher, String className) {
      this.contribute(enricher, className, className, this.webBean);
   }

   public Jsr250MetadataI newJsr250Metadata(String componentName, Class componentClass, DeploymentUnitMetadataI metadata) {
      Debug.assertion(this.context != null, "not initialized");
      Jsr250MetadataI md = this.webCompContributorBroker.createJsr250Metadata(metadata, componentName, componentClass);
      this.diClasses.put(componentName, md);
      return md;
   }

   private Object getNewInstance(String clzName, ClassLoader cl) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
      Class clz = cl.loadClass(clzName);
      return this.getNewInstance(clz, cl);
   }

   private Object getNewInstance(Class clazz, ClassLoader cl) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
      String clzName = clazz.getName();
      if (this.usesSpringExtensionModel && !clzName.startsWith("weblogic")) {
         return !this.diClasses.containsKey(clzName) ? clazz.newInstance() : this.webCompContributorBroker.getNewInstance(clzName);
      } else {
         Object instance = clazz.newInstance();

         try {
            this.inject(instance);
            this.notifyPostConstruct(instance);
            return instance;
         } catch (Exception var6) {
            return clazz.newInstance();
         }
      }
   }

   public Servlet createServletInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      Debug.assertion(this.context != null, "not initialized");
      return (Servlet)this.getNewInstance(className, this.context.getServletClassLoader());
   }

   public Filter createFilterInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      Debug.assertion(this.context != null, "not initialized");
      return (Filter)this.getNewInstance(className, this.context.getServletClassLoader());
   }

   public EventListener createListenerInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      Debug.assertion(this.context != null, "not initialized");
      return (EventListener)this.getNewInstance(className, this.context.getServletClassLoader());
   }

   public Object createInstance(Class clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException, ClassCastException {
      Debug.assertion(this.context != null, "not initialized");
      return this.getNewInstance(clazz, this.context.getServletClassLoader());
   }

   public void inject(Object obj) {
      if (this.useDI) {
         String componentName = obj.getClass().getName();

         try {
            Jsr250MetadataI metadata = (Jsr250MetadataI)this.diClasses.get(componentName);
            if (metadata != null) {
               metadata.inject(obj);
            }

         } catch (RuntimeException var4) {
            this.diClasses.remove(componentName);
            HTTPLogger.logDependencyInjectionFailed(this.context.getLogContext(), componentName, var4);
            if (diLogger.isDebugEnabled()) {
               diLogger.debug("Dependency injection failed for class " + componentName, var4);
            }

            throw var4;
         }
      }
   }

   public void notifyPostConstruct(Object obj) {
      this.invokeLifecycleMethods(obj, LifecycleEvent.POST_CONSTRUCT);
   }

   public void notifyPreDestroy(Object obj) {
      try {
         this.invokeLifecycleMethods(obj, LifecycleEvent.PRE_DESTROY);
      } catch (Throwable var3) {
      }

   }

   public boolean needDependencyInjection(Object obj) {
      if (this.useDI && obj != null) {
         for(Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (this.diClasses.containsKey(clazz.getName())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void invokeLifecycleMethods(Object obj, LifecycleEvent event) {
      if (this.useDI) {
         Jsr250MetadataI metadata = (Jsr250MetadataI)this.diClasses.get(obj.getClass().getName());
         if (metadata != null) {
            metadata.invokeLifecycleMethods(obj, event);
         }

      }
   }

   private void checkDuplicatedCallback(LifecycleCallbackBean[] beans, LifecycleEvent event) throws DeploymentException {
      if (beans != null && beans.length >= 2) {
         HashMap map = new HashMap(beans.length);
         LifecycleCallbackBean[] var4 = beans;
         int var5 = beans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            LifecycleCallbackBean bean = var4[var6];
            String method = (String)map.put(bean.getLifecycleCallbackClass(), bean.getLifecycleCallbackMethod());
            if (method != null && !method.equals(bean.getLifecycleCallbackMethod())) {
               throw new DeploymentException("There are multiple lifecycle callbacks declared for event " + event + " on class: " + bean.getLifecycleCallbackClass());
            }
         }

      }
   }

   protected void addLifecycleMethods(Jsr250MetadataI jsr250, J2eeEnvironmentBean environmentGroupBean) {
      LifecycleCallbackBean[] beans = this.getComponentCallbackBeans(jsr250, environmentGroupBean.getPostConstructs());

      int i;
      for(i = beans.length - 1; i > -1; --i) {
         this.addLifecycleMethods(jsr250, beans[i], LifecycleEvent.POST_CONSTRUCT);
      }

      beans = this.getComponentCallbackBeans(jsr250, environmentGroupBean.getPreDestroys());

      for(i = 0; i < beans.length; ++i) {
         this.addLifecycleMethods(jsr250, beans[i], LifecycleEvent.PRE_DESTROY);
      }

   }

   private LifecycleCallbackBean[] getComponentCallbackBeans(Jsr250MetadataI jsr250, LifecycleCallbackBean[] beans) {
      if (beans != null && beans.length != 0) {
         TreeSet sortedBeans = this.sortCallbackBeans(jsr250, beans);
         sortedBeans = this.removeOverridenCallbackBeans(sortedBeans);
         return (LifecycleCallbackBean[])sortedBeans.toArray(new LifecycleCallbackBean[sortedBeans.size()]);
      } else {
         return new LifecycleCallbackBean[0];
      }
   }

   private TreeSet sortCallbackBeans(Jsr250MetadataI jsr250, LifecycleCallbackBean[] beans) {
      TreeSet sortedBeans = new TreeSet(new Comparator() {
         public int compare(Object t1, Object t2) {
            LifecycleCallbackBean bean1 = (LifecycleCallbackBean)t1;
            LifecycleCallbackBean bean2 = (LifecycleCallbackBean)t2;
            Class cls1 = WebComponentContributor.this.loadClass(bean1.getLifecycleCallbackClass(), WebComponentContributor.this.classLoader);
            Class cls2 = WebComponentContributor.this.loadClass(bean2.getLifecycleCallbackClass(), WebComponentContributor.this.classLoader);
            if (!cls1.isAssignableFrom(cls2)) {
               return -1;
            } else if (cls1 != cls2) {
               return 1;
            } else {
               return bean1.getLifecycleCallbackMethod().equals(bean2.getLifecycleCallbackMethod()) ? 0 : 1;
            }
         }
      });
      Class componentClass = this.loadClass(jsr250.getComponentName(), this.classLoader);
      LifecycleCallbackBean[] var5 = beans;
      int var6 = beans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         LifecycleCallbackBean bean = var5[var7];
         Class cls = this.loadClass(bean.getLifecycleCallbackClass(), this.classLoader);
         if (cls.isAssignableFrom(componentClass)) {
            sortedBeans.add(bean);
         }
      }

      return sortedBeans;
   }

   private TreeSet removeOverridenCallbackBeans(TreeSet sortedBeans) {
      HashSet overridens = new HashSet(sortedBeans.size());
      Iterator it = sortedBeans.iterator();

      while(it.hasNext()) {
         LifecycleCallbackBean bean = (LifecycleCallbackBean)it.next();
         String methodName = bean.getLifecycleCallbackMethod();
         if (!overridens.contains(methodName)) {
            overridens.add(methodName);
         } else {
            Class cls = this.loadClass(bean.getLifecycleCallbackClass(), this.classLoader);
            Method method = this.getDeclaredMethod(cls, methodName, new Class[0]);
            if (!Modifier.isPrivate(method.getModifiers())) {
               it.remove();
            }
         }
      }

      return sortedBeans;
   }
}
