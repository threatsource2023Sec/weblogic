package weblogic.ejb.container.injection;

import com.oracle.injection.InjectionContainer;
import com.oracle.injection.ejb.InterceptorMapping;
import com.oracle.injection.integration.WeblogicContainerIntegrationService;
import com.oracle.injection.provider.weld.WeldInjectionContainer;
import com.oracle.injection.spi.ContainerIntegrationService;
import com.oracle.pitchfork.interfaces.MetadataParseException;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.interceptor.AroundConstruct;
import javax.interceptor.InvocationContext;
import org.jboss.weld.ejb.spi.InterceptorBindings;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.injection.PitchforkContext;

public class InjectionBasedEjbComponentContributor extends EjbComponentContributor {
   private final ModuleContext mc;
   private final ModuleExtensionContext mec;
   private final InjectionContainer injectionContainer;
   private final ContainerIntegrationService containerIntegrationService;
   private boolean contributingPojos = false;

   public InjectionBasedEjbComponentContributor(DeploymentInfo di, ClassLoader cl, PitchforkContext pc, InjectionContainer ic, ModuleContext mc, ModuleExtensionContext mec) {
      super(di, cl, pc);
      this.mc = mc;
      this.mec = mec;
      this.injectionContainer = ic;
      this.containerIntegrationService = ic.getIntegrationService();
   }

   protected void contribute(Jsr250MetadataI meta, J2eeEnvironmentBean envBean) {
      if (this.contributingPojos) {
         this.containerIntegrationService.addInjectionMetaData(meta.getComponentClass(), meta);
      } else {
         this.contributeEjbInterceptors(meta);
         super.contribute(meta, envBean);
         this.containerIntegrationService.addInjectionMetaData(meta.getComponentClass(), meta);
         this.containerIntegrationService.addInjectionMetaData(meta.getComponentClass().getSuperclass(), meta);
      }

   }

   public void contribute(EnricherI enricher) {
      this.contributingPojos = false;
      super.contribute(enricher);
      this.contributePojos(enricher);
   }

   private void contributePojos(EnricherI enricher) {
      this.contributingPojos = true;
      Set pojoClasses = this.mc.getRegistry().getAnnotatedPojoClasses();
      if (pojoClasses != null && this.mec.getPojoEnvironmentBean() != null) {
         Iterator var3 = pojoClasses.iterator();

         while(var3.hasNext()) {
            Class oneClass = (Class)var3.next();
            String className = oneClass.getName();
            this.contribute(enricher, className, className, this.mec.getPojoEnvironmentBean());
         }
      }

      pojoClasses = null;
      this.mc.getRegistry().clearAnnotatedPojoClasses();
   }

   private void contributeEjbInterceptors(Jsr250MetadataI ejbJsr250Metadata) {
      InterceptorBean[] interceptorBeans = this.getInterceptorBeans();
      List interceptorClassNames = new ArrayList();
      Iterator var4 = this.injectionContainer.getEjbInterceptorMappings().iterator();

      InterceptorBean ib;
      while(var4.hasNext()) {
         InterceptorMapping im = (InterceptorMapping)var4.next();
         InterceptorMetadataI icptrMetadata = this.createIcptrMetadata(this.loadInterceptorClass(im.getInterceptorClass()), im.getAroundInvokeMethods(), im.getAroundTimeoutMethods(), (Method)null);
         ib = this.getIcptrBean(im.getInterceptorClass(), interceptorBeans);
         this.contributeOneInterceptor(ejbJsr250Metadata.getComponentName(), icptrMetadata.getComponentClass(), icptrMetadata, interceptorClassNames, ib);
         ((InterceptionMetadataI)ejbJsr250Metadata).registerInterceptorMetadata(icptrMetadata);
      }

      if (interceptorBeans != null) {
         InterceptorBean[] var11 = interceptorBeans;
         int var12 = interceptorBeans.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            ib = var11[var13];
            String icptrClassName = ib.getInterceptorClass();
            if (!this.isEjbInterceptorTheEjb(ejbJsr250Metadata, icptrClassName) && !interceptorClassNames.contains(icptrClassName)) {
               Class icptrClass = this.loadInterceptorClass(icptrClassName);
               InterceptorMetadataI im = this.createIcptrMetadata(icptrClass, new ArrayList(), new ArrayList(), (Method)null);
               if (this.containerIntegrationService instanceof WeblogicContainerIntegrationService) {
                  ((WeblogicContainerIntegrationService)this.containerIntegrationService).addEjbInterceptorGeneratedNameMapping(ejbJsr250Metadata.getComponentName(), im.getComponentName(), icptrClassName);
               }

               this.contributeOneInterceptor(ejbJsr250Metadata.getComponentName(), icptrClass, im, interceptorClassNames, ib);
            }
         }
      }

   }

   private boolean isEjbInterceptorTheEjb(Jsr250MetadataI ejbMetadata, String interceptorClassName) {
      return ejbMetadata.getComponentClass().getName().equals(interceptorClassName) || ejbMetadata.getComponentClass().getSuperclass().getName().equals(interceptorClassName);
   }

   private void contributeOneInterceptor(String ejbName, Class clazz, InterceptorMetadataI im, List interceptorClassNames, InterceptorBean icptrBean) {
      if (icptrBean != null) {
         this.buildInterceptorInjectionMetaData(im, icptrBean);
      }

      this.containerIntegrationService.addEjbInterceptorInjectionMetaData(ejbName, clazz, im);
      interceptorClassNames.add(im.getComponentClass().getName());
      if (!clazz.equals(im.getComponentClass())) {
         this.containerIntegrationService.addEjbInterceptorInjectionMetaData(ejbName, im.getComponentClass(), im);
         interceptorClassNames.add(im.getComponentClass().getName());
      }

   }

   private InterceptorBean getIcptrBean(String className, InterceptorBean[] ibs) {
      if (ibs != null) {
         InterceptorBean[] var3 = ibs;
         int var4 = ibs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            InterceptorBean ib = var3[var5];
            if (ib.getInterceptorClass().equals(className)) {
               return ib;
            }
         }
      }

      return null;
   }

   protected void addRequiredCdiInterceptors(InterceptionMetadataI jsr250Metadata) {
      WeldInjectionContainer wic = (WeldInjectionContainer)this.injectionContainer;
      Iterator var3 = wic.getEjbInterceptorMappings().iterator();

      while(var3.hasNext()) {
         InterceptorMapping im = (InterceptorMapping)var3.next();
         this.registerOneLifecycleInterceptor(jsr250Metadata, PostConstruct.class, LifecycleEvent.POST_CONSTRUCT, this.loadInterceptorClass(im.getInterceptorClass()), im.getPostConstructMethods());
      }

   }

   protected void addMethodLevelInterceptors(InterceptionMetadataI beanMeta, InterceptorBindingBean[] ibbs, String ejbName, Class generatedBeanInterface, BeanInfo bi, List constructorLevelInterceptorBindings) {
      super.addMethodLevelInterceptors(beanMeta, ibbs, ejbName, generatedBeanInterface, bi, constructorLevelInterceptorBindings);
      this.registerCdiInterceptors(beanMeta, generatedBeanInterface, bi.getTimeoutMethod());
   }

   private void registerCdiInterceptors(InterceptionMetadataI beanMeta, Class generatedBeanInterface, Method timeoutMethod) {
      if (this.injectionContainer instanceof WeldInjectionContainer) {
         WeldInjectionContainer wic = (WeldInjectionContainer)this.injectionContainer;
         InterceptorBindings bindings = wic.getInterceptorBindings(beanMeta.getComponentName());
         if (bindings == null) {
            this.registerAroundConstructBindingInterceptor(beanMeta, wic.getBindingInterceptorClass(beanMeta.getComponentClass()));
         }

         if (bindings != null) {
            this.registerLifecycleIcptr(beanMeta, bindings.getLifecycleInterceptors(InterceptionType.AROUND_CONSTRUCT), AroundConstruct.class, LifecycleEvent.AROUND_CONSTRUCT);
            this.registerLifecycleIcptr(beanMeta, bindings.getLifecycleInterceptors(InterceptionType.POST_CONSTRUCT), PostConstruct.class, LifecycleEvent.POST_CONSTRUCT);
            this.registerLifecycleIcptr(beanMeta, bindings.getLifecycleInterceptors(InterceptionType.PRE_DESTROY), PreDestroy.class, LifecycleEvent.PRE_DESTROY);
            this.registerLifecycleIcptr(beanMeta, bindings.getLifecycleInterceptors(InterceptionType.PRE_PASSIVATE), PrePassivate.class, LifecycleEvent.PRE_PASSIVATE);
            this.registerLifecycleIcptr(beanMeta, bindings.getLifecycleInterceptors(InterceptionType.POST_ACTIVATE), PostActivate.class, LifecycleEvent.POST_ACTIVATE);
            Set processedMethods = new HashSet();
            Map holderMapping = new HashMap();
            Class componentClass = beanMeta.getComponentClass();
            Method[] var9 = generatedBeanInterface.getMethods();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Method method = var9[var11];

               try {
                  Method componentClassMethod = componentClass.getMethod(method.getName(), method.getParameterTypes());
                  Iterator var14 = this.getInterceptors(bindings, componentClassMethod).iterator();

                  while(var14.hasNext()) {
                     Interceptor icptr = (Interceptor)var14.next();
                     InterceptorMethodHolder holder = this.getHolder(icptr.getBeanClass(), holderMapping);
                     InterceptorMetadataI icptrMeta = this.createIcptrMetadata(icptr.getBeanClass(), holder.getAroundInvokeMethods(), holder.getAroundTimeoutMethods(), method);
                     beanMeta.registerInterceptorMetadata(icptrMeta);
                  }

                  processedMethods.add(componentClassMethod);
               } catch (NoSuchMethodException var18) {
                  throw new AssertionError("Failed to find business method " + method.getName() + " on the ejb " + beanMeta.getComponentName(), var18);
               }
            }

            if (timeoutMethod != null && !processedMethods.contains(timeoutMethod)) {
               Iterator var19 = this.getInterceptors(bindings, timeoutMethod).iterator();

               while(var19.hasNext()) {
                  Interceptor icptr = (Interceptor)var19.next();
                  InterceptorMethodHolder holder = this.getHolder(icptr.getBeanClass(), holderMapping);
                  InterceptorMetadataI icptrMeta = this.createIcptrMetadata(icptr.getBeanClass(), holder.getAroundInvokeMethods(), holder.getAroundTimeoutMethods(), timeoutMethod);
                  beanMeta.registerInterceptorMetadata(icptrMeta);
               }
            }

         }
      }
   }

   private List getInterceptors(InterceptorBindings ibs, Method m) {
      List allIcptrs = new ArrayList();
      allIcptrs.addAll(ibs.getMethodInterceptors(InterceptionType.AROUND_INVOKE, m));
      Iterator var4 = ibs.getMethodInterceptors(InterceptionType.AROUND_TIMEOUT, m).iterator();

      while(var4.hasNext()) {
         Interceptor icptr = (Interceptor)var4.next();
         if (!allIcptrs.contains(icptr)) {
            allIcptrs.add(icptr);
         }
      }

      return allIcptrs;
   }

   private void registerLifecycleIcptr(InterceptionMetadataI beanMeta, List icptrs, Class anno, LifecycleEvent le) {
      Iterator var5 = icptrs.iterator();

      while(var5.hasNext()) {
         Interceptor icptr = (Interceptor)var5.next();
         this.registerOneLifecycleInterceptor(beanMeta, anno, le, icptr.getBeanClass(), (List)null);
      }

   }

   private void registerAroundConstructBindingInterceptor(InterceptionMetadataI beanMeta, Class interceptorClass) {
      InterceptorBean interceptorBean = null;
      InterceptorBean[] interceptorBeans = this.getInterceptorBeans();
      if (interceptorBeans != null && interceptorClass != null) {
         InterceptorBean[] var5 = interceptorBeans;
         int var6 = interceptorBeans.length;

         int var7;
         for(var7 = 0; var7 < var6; ++var7) {
            InterceptorBean ib = var5[var7];
            if (interceptorClass.getName().equals(ib.getInterceptorClass())) {
               interceptorBean = ib;
               break;
            }
         }

         List aroundInvokeMethods = new ArrayList();
         AroundInvokeBean[] var13 = interceptorBean.getAroundInvokes();
         var7 = var13.length;

         int var16;
         for(var16 = 0; var16 < var7; ++var16) {
            AroundInvokeBean aib = var13[var16];
            Class aroundInvokeClass;
            if (interceptorClass.getName().equals(aib.getClassName())) {
               aroundInvokeClass = interceptorClass;
            } else {
               aroundInvokeClass = this.loadClass(aib.getClassName());
            }

            aroundInvokeMethods.add(this.getDeclaredMethod(aroundInvokeClass, aib.getMethodName(), new Class[]{InvocationContext.class}));
         }

         List aroundTimeoutMethods = new ArrayList();
         AroundTimeoutBean[] var15 = interceptorBean.getAroundTimeouts();
         var16 = var15.length;

         for(int var18 = 0; var18 < var16; ++var18) {
            AroundTimeoutBean atb = var15[var18];
            Class aroundTimeoutClass;
            if (interceptorClass.getName().equals(atb.getClassName())) {
               aroundTimeoutClass = interceptorClass;
            } else {
               aroundTimeoutClass = this.loadClass(atb.getClassName());
            }

            aroundTimeoutMethods.add(this.getDeclaredMethod(aroundTimeoutClass, atb.getMethodName(), new Class[]{InvocationContext.class}));
         }

         InterceptorMetadataI im = this.createIcptrMetadata(interceptorClass, aroundInvokeMethods, aroundTimeoutMethods, (Method)null);
         this.buildInterceptorInjectionMetaData(im, interceptorBean);
         this.registerLifecycleEventListenerMethods(im, AroundConstruct.class, LifecycleEvent.AROUND_CONSTRUCT, interceptorClass, (List)null);
         im.setClassInterceptor(true);
         beanMeta.registerInterceptorMetadata(im);
      }
   }

   private void registerOneLifecycleInterceptor(InterceptionMetadataI beanMeta, Class annotation, LifecycleEvent lifecycleEvent, Class interceptorClass, List lifecycleMethods) {
      InterceptorMetadataI icptrMeta = this.createIcptrMetadata(interceptorClass, new LinkedList(), new LinkedList(), (Method)null);
      this.registerLifecycleEventListenerMethods(icptrMeta, annotation, lifecycleEvent, interceptorClass, lifecycleMethods);
      icptrMeta.setClassInterceptor(true);
      beanMeta.registerInterceptorMetadata(icptrMeta);
   }

   void registerLifecycleEventListenerMethods(InterceptorMetadataI im, Class annotation, LifecycleEvent lifecycleEvent, Class interceptorClass, List lifecycleMethods) {
      if (lifecycleMethods == null) {
         lifecycleMethods = this.getInterceptorMethods(interceptorClass, annotation);
      }

      Iterator var6 = lifecycleMethods.iterator();

      while(var6.hasNext()) {
         Method m = (Method)var6.next();
         im.registerLifecycleEventListenerMethod(lifecycleEvent, m);
      }

   }

   private InterceptorMethodHolder getHolder(Class c, Map mapping) {
      InterceptorMethodHolder holder = (InterceptorMethodHolder)mapping.get(c);
      if (holder == null) {
         holder = new InterceptorMethodHolder(c);
         mapping.put(c, holder);
      }

      return holder;
   }

   List getInterceptorMethods(Class clazz, Class anno) {
      LinkedList methods = new LinkedList();

      for(Class curClass = clazz; !curClass.equals(Object.class); curClass = curClass.getSuperclass()) {
         Method[] var5 = curClass.getDeclaredMethods();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method m = var5[var7];
            if (!InjectionUtils.isMethodOverridden(curClass, m) && m.getAnnotation(anno) != null) {
               methods.addFirst(m);
            }
         }
      }

      if (methods.isEmpty()) {
         throw new IllegalStateException("Interceptor class " + clazz + " has no method annotated with " + anno);
      } else {
         return methods;
      }
   }

   private Class loadInterceptorClass(String name) {
      try {
         return Class.forName(name, false, this.classLoader);
      } catch (ClassNotFoundException var3) {
         throw new MetadataParseException("Cannot load class " + name, var3);
      }
   }
}
