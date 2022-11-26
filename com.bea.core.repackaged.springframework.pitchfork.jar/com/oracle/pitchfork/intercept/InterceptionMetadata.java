package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.aspectj.annotation.BeanFactoryAspectJAdvisorsBuilder;
import com.bea.core.repackaged.springframework.aop.framework.Advised;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper;
import com.bea.core.repackaged.springframework.aop.interceptor.ExposeInvocationInterceptor;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.AbstractApplicationContext;
import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.intercept.internal.InterceptorExclusionManagerImpl;
import com.oracle.pitchfork.interfaces.BeanCreator;
import com.oracle.pitchfork.interfaces.TargetWrapper;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import com.oracle.pitchfork.interfaces.intercept.__ProxyControl;
import com.oracle.pitchfork.spi.TargetWrapperImpl;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.Timer;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;

public class InterceptionMetadata extends Jsr250Metadata implements InterceptionMetadataI {
   private static final Log log = LogFactory.getLog(InterceptionMetadata.class);
   private static final Set EXCLUDED_BUSINESS_INTERFACES = new HashSet();
   private final InterceptorExclusionManager exclusionManager = new InterceptorExclusionManagerImpl();
   private final List interceptorMetadataList = new LinkedList();
   private final List selfInterceptorMethodList = new LinkedList();
   private final List selfTimeoutInterceptorMethodList = new LinkedList();
   private final Set orderers = new HashSet();
   private final Map interceptorOrderMap = new HashMap();
   private List businessInterfaces;
   private Class beanControlInterface;
   private final Set containerControlInterfaces = new HashSet();
   private Set beanControlInterfaceMethods;
   private boolean isMessageDrivenBean;
   private boolean isCDIEnabled;
   private BeanDiscoveryMode beanDiscoveryMode;
   private final BeanCreator beanCreator;

   public InterceptionMetadata(DeploymentUnitMetadataI dum, String name, Class componentClass, boolean useSpringExtensionModel) {
      super(dum, name, componentClass);
      this.beanCreator = this.initBeanCreator(useSpringExtensionModel);
   }

   public void setComponentContext(ApplicationContext componentContext, BeanDefinitionRegistry bdr) {
      super.setComponentContext(componentContext, bdr);
      Iterator var3 = this.interceptorOrderMap.values().iterator();

      while(var3.hasNext()) {
         List list = (List)var3.next();
         Iterator var5 = list.iterator();

         while(var5.hasNext()) {
            InterceptorMetadataI im = (InterceptorMetadataI)var5.next();
            ((InterceptorMetadata)im).setComponentContext(componentContext, bdr);
         }
      }

      var3 = this.interceptorMetadataList.iterator();

      while(var3.hasNext()) {
         InterceptorMetadataI im = (InterceptorMetadataI)var3.next();
         ((InterceptorMetadata)im).setComponentContext(componentContext, bdr);
      }

   }

   protected void invokePostConstructAndRegisterShutdownHook(Object instance) {
   }

   public void registerSelfInterceptorMethod(Method selfInterceptorMethod) {
      if (selfInterceptorMethod != null) {
         ReflectionUtils.makeAccessible(selfInterceptorMethod);
         this.selfInterceptorMethodList.add(selfInterceptorMethod);
      }

   }

   public void registerSelfTimeoutInterceptorMethod(Method method) {
      if (method != null) {
         ReflectionUtils.makeAccessible(method);
         this.selfTimeoutInterceptorMethodList.add(method);
      }

   }

   public void registerContainerControlInterface(Class containerControlInterface) {
      this.containerControlInterfaces.add(containerControlInterface);
   }

   public Set getContainerControlInterfaces() {
      return this.containerControlInterfaces;
   }

   public void setBeanControlInterface(Class beanControlInterface) {
      this.beanControlInterface = beanControlInterface;
   }

   public void setBeanControlInterfaceMethods(Set beanControlInterfaceMethods) {
      this.beanControlInterfaceMethods = beanControlInterfaceMethods;
   }

   public void setExcludeClassInterceptors(AnnotatedElement methodOrClass) {
      this.exclusionManager.setExcludeClassInterceptors(methodOrClass);
   }

   public void setExcludeDefaultInterceptors(AnnotatedElement methodOrClass) {
      this.exclusionManager.setExcludeDefaultInterceptors(methodOrClass);
   }

   public void registerInterceptorMetadata(InterceptorMetadataI im) {
      if (im.getMatchingMethod() != null) {
         if (log.isDebugEnabled()) {
            log.debug("registering as method interceptor " + im);
         }

         this.interceptorMetadataList.add(im);
      } else {
         if (this.interceptorMetadataList.isEmpty()) {
            this.interceptorMetadataList.add(im);
         } else {
            ListIterator listIterator = this.interceptorMetadataList.listIterator();

            while(listIterator.hasNext()) {
               InterceptorMetadataI interceptorMetadata = (InterceptorMetadataI)listIterator.next();
               if (interceptorMetadata.getMatchingMethod() != null) {
                  listIterator.previous();
                  break;
               }
            }

            listIterator.add(im);
         }

         if (log.isDebugEnabled()) {
            log.debug("registering as class interceptor " + im);
         }
      }

   }

   public List getInterceptorMetadata() {
      List ims = new LinkedList();
      ims.addAll(this.getDeploymentUnitMetadata().getDefaultInterceptorMetadata());
      ims.addAll(this.interceptorMetadataList);
      return Collections.unmodifiableList(ims);
   }

   public void registerInterceptorOrder(PointcutMatch pm, InterceptorMetadataI im) {
      this.orderers.add(pm);
      List interceptorOrder = (List)this.interceptorOrderMap.get(pm);
      if (interceptorOrder == null) {
         interceptorOrder = new LinkedList();
      }

      ((List)interceptorOrder).add(im);
      this.interceptorOrderMap.put(pm, interceptorOrder);
   }

   public void addBusinessInterface(Class businessInterface) {
      if (this.businessInterfaces == null) {
         this.businessInterfaces = new LinkedList();
      }

      this.businessInterfaces.add(businessInterface);
   }

   public List getBusinessInterfaces() {
      if (this.businessInterfaces == null) {
         this.businessInterfaces = this.findBusinessInterfacesFromClassOrAnnotation(this.getComponentClass());
      }

      return this.businessInterfaces;
   }

   public List findBusinessInterfacesFromClassOrAnnotation(Class clazz) {
      Set ctrlIntfs = this.getContainerControlInterfaces();
      List busIntfs = new LinkedList();
      Iterator it = this.getAllInterfacesForClass(clazz).iterator();

      while(it.hasNext()) {
         Class c = (Class)it.next();
         if (!ctrlIntfs.contains(c)) {
            busIntfs.add(c);
         }
      }

      it = busIntfs.iterator();

      while(it.hasNext()) {
         if (EXCLUDED_BUSINESS_INTERFACES.contains(((Class)it.next()).getName())) {
            it.remove();
         }
      }

      return busIntfs;
   }

   private List getAllInterfacesForClass(Class clazz) {
      if (clazz.isInterface()) {
         return Arrays.asList(clazz);
      } else {
         ArrayList interfaces;
         for(interfaces = new ArrayList(); clazz != null; clazz = clazz.getSuperclass()) {
            Class[] var3 = clazz.getInterfaces();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Class c = var3[var5];
               if (!interfaces.contains(c)) {
                  interfaces.add(c);
               }
            }
         }

         return interfaces;
      }
   }

   private ProxyFactory getProxyFactory() {
      ProxyFactory proxyFactory = new ProxyFactory();
      proxyFactory.setProxyTargetClass(this.supportsTargetClassProxying());
      proxyFactory.setExposeProxy(true);
      return proxyFactory;
   }

   protected boolean supportsTargetClassProxying() {
      return false;
   }

   public Object createProxyIfNecessary(Object target, Map interceptorInstances) {
      ProxyFactory proxyFactory = this.getProxyFactory();
      if (target instanceof TargetSource) {
         proxyFactory.setTargetSource((TargetSource)target);
      } else {
         proxyFactory.setTarget(target);
      }

      Iterator var4 = this.getProxyInterfaces().iterator();

      while(var4.hasNext()) {
         Class proxyInterface = (Class)var4.next();
         proxyFactory.addInterface(proxyInterface);
      }

      Object unwrappedTarget = target;

      try {
         unwrappedTarget = proxyFactory.getTargetSource().getTarget();
      } catch (Exception var6) {
      }

      this.addJeeInterceptors(proxyFactory, unwrappedTarget, interceptorInstances);
      if (!this.getDeploymentUnitMetadata().isLimitToSpec()) {
         this.addSpringAopAdvisors(proxyFactory, unwrappedTarget);
         this.addAspectJAdvisors(proxyFactory);
      }

      if (proxyFactory.getAdvisors().length == 0) {
         return unwrappedTarget;
      } else {
         Object tw;
         if (target instanceof TargetWrapper) {
            tw = (TargetWrapper)target;
         } else {
            tw = new TargetWrapperImpl(target);
         }

         ((TargetWrapper)tw).setInterceptionInstances(interceptorInstances);
         proxyFactory.addAdvice(0, new DelegatingIntroductionInterceptor(new AdvisorChainProxyControl((TargetWrapper)tw)));
         return proxyFactory.getProxy();
      }
   }

   private List getProxyInterfaces() {
      List intfs = new ArrayList();
      intfs.addAll(this.getContainerControlInterfaces());
      if (this.beanControlInterface != null) {
         intfs.add(this.beanControlInterface);
      }

      intfs.addAll(this.getBusinessInterfaces());
      return intfs;
   }

   private List getJeeInterceptorAdvisors(Advised advised) {
      List advisors = new ArrayList(advised.getAdvisors().length);
      Advisor[] var3 = advised.getAdvisors();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Advisor a = var3[var5];
         if (a instanceof JeeInterceptorPointcutAdvisor) {
            advisors.add((JeeInterceptorPointcutAdvisor)a);
         }
      }

      return advisors;
   }

   private BeanCreator initBeanCreator(final boolean useSpringExtensionModel) {
      return new BeanCreator() {
         public Object createInterceptor(InterceptorMetadataI im) {
            return InterceptionMetadata.this.getComponentContext().getBean(im.getComponentName());
         }

         public Object createBean() throws InstantiationException, IllegalAccessException {
            if (useSpringExtensionModel) {
               try {
                  return InterceptionMetadata.this.getComponentContext().getBean(InterceptionMetadata.this.getComponentName());
               } catch (BeanCreationException var3) {
                  InstantiationException ie = new InstantiationException(var3.getMessage());
                  ie.initCause(var3);
                  throw ie;
               }
            } else {
               return InterceptionMetadata.this.getComponentClass().newInstance();
            }
         }

         public Constructor getBeansConstructor() {
            return ReflectionUtils.getDefaultConstructor(InterceptionMetadata.this.getComponentClass());
         }
      };
   }

   public BeanCreator getBeanCreator() {
      return this.beanCreator;
   }

   public List getAroundConstructMetadatas() {
      List icptrMetas = new ArrayList();
      Iterator var2 = this.interceptorOrderMap.values().iterator();

      while(var2.hasNext()) {
         List ims = (List)var2.next();
         Iterator var4 = ims.iterator();

         while(var4.hasNext()) {
            InterceptorMetadataI im = (InterceptorMetadataI)var4.next();
            this.addAroundConstructMetadata(im, icptrMetas);
         }
      }

      var2 = this.getDeploymentUnitMetadata().getDefaultInterceptorMetadata().iterator();

      InterceptorMetadataI im;
      while(var2.hasNext()) {
         im = (InterceptorMetadataI)var2.next();
         this.addAroundConstructMetadata(im, icptrMetas);
      }

      var2 = this.interceptorMetadataList.iterator();

      while(true) {
         do {
            if (!var2.hasNext()) {
               return icptrMetas;
            }

            im = (InterceptorMetadataI)var2.next();
         } while(!im.isClassInterceptor() && !im.isConstructorInterceptor() && !im.isDefaultInterceptor());

         this.addAroundConstructMetadata(im, icptrMetas);
      }
   }

   private void addAroundConstructMetadata(InterceptorMetadataI im, List icptrsMetas) {
      if (im.isSystemInterceptor() || !this.exclusionManager.isExcluded((Method)null, this.getComponentClass(), im)) {
         List list = im.getLifecycleEventListenerMethod(LifecycleEvent.AROUND_CONSTRUCT);
         if (list != null && !list.isEmpty()) {
            icptrsMetas.add(im);
         }

      }
   }

   public Object constructBean(BeanCreator bc, List icptrsMetas, Map icptrsMap) {
      LinkedList mids = new LinkedList();
      Iterator var5 = icptrsMetas.iterator();

      while(var5.hasNext()) {
         InterceptorMetadataI im = (InterceptorMetadataI)var5.next();
         Object instance = icptrsMap.get(im.getComponentName());
         if (instance == null) {
            instance = bc.createInterceptor(im);
         }

         icptrsMap.put(im.getComponentName(), instance);
         Iterator var8 = im.getLifecycleEventListenerMethod(LifecycleEvent.AROUND_CONSTRUCT).iterator();

         while(var8.hasNext()) {
            Method lcm = (Method)var8.next();
            mids.add(new MethodInvocationData(instance, lcm));
         }
      }

      LifecycleEventCallbackInvocationContext invCtx = new AroundConstructInvocationContext(bc, this, this.getContextDataProvider());
      invCtx.proceed(mids);
      return invCtx.getTarget();
   }

   public void invokeLifecycleMethods(Object bean, LifecycleEvent le) {
      if (!(bean instanceof Advised)) {
         super.invokeLifecycleMethods(bean, le);
      } else {
         Advised advised = (Advised)bean;
         Object target = null;

         try {
            target = advised.getTargetSource().getTarget();
         } catch (Exception var19) {
            throw new AssertionError(var19);
         }

         LifecycleEventCallbackInvocationContext lifecycleEventCallbackInvocationContext = new LifecycleEventCallbackInvocationContext(target, this, le, this.getContextDataProvider());
         List lifecycleCallbackMethods = this.getLifecycleEventCallbackMethod(le);
         Method lastMethodInChain = null;
         if (lifecycleCallbackMethods != null && lifecycleCallbackMethods.size() > 0) {
            lastMethodInChain = (Method)lifecycleCallbackMethods.get(lifecycleCallbackMethods.size() - 1);
         }

         LinkedList interceptors = new LinkedList();
         Map dupFinder = new HashMap();
         boolean hasAnOrderer = this.hasAnOrderer(lastMethodInChain);
         Iterator var11 = this.getJeeInterceptorAdvisors(advised).iterator();

         label83:
         while(true) {
            JeeInterceptorInterceptor advice;
            List listeners;
            do {
               JeeInterceptorPointcutAdvisor jipa;
               InterceptorMetadata im;
               do {
                  do {
                     do {
                        if (!var11.hasNext()) {
                           lifecycleEventCallbackInvocationContext.proceed(interceptors);
                           return;
                        }

                        jipa = (JeeInterceptorPointcutAdvisor)var11.next();
                        advice = jipa.getAdvice();
                        im = (InterceptorMetadata)advice.getInterceptorMetadata();
                     } while(im == null);
                  } while(jipa.isFromOrderer() == null && !im.isSystemInterceptor() && !im.isDefaultInterceptor() && !im.isClassInterceptor());
               } while(hasAnOrderer && !jipa.checkOrderer(lastMethodInChain, this.getComponentClass()));

               listeners = im.getLifecycleEventListenerMethod(le);
            } while(listeners == null);

            Iterator var16 = listeners.iterator();

            while(true) {
               Method lcm;
               while(true) {
                  if (!var16.hasNext()) {
                     continue label83;
                  }

                  lcm = (Method)var16.next();
                  Object possibleDup = dupFinder.get(lcm);
                  if (possibleDup != null) {
                     if (possibleDup.equals(advice.getJeeInterceptorInstance())) {
                        continue;
                     }
                     break;
                  }

                  dupFinder.put(lcm, advice.getJeeInterceptorInstance());
                  break;
               }

               interceptors.add(new MethodInvocationData(advice.getJeeInterceptorInstance(), lcm));
            }
         }
      }
   }

   private boolean hasAnOrderer(Method method) {
      return this.orderers.contains(new PointcutMatch(this.getComponentClass(), method)) || this.orderers.contains(new PointcutMatch(this.getComponentClass(), (Method)null));
   }

   public void invokeTimeoutMethod(Object bean, Method timeoutMethod, Timer timer) {
      Object target = bean;

      try {
         if (bean instanceof Advised) {
            target = ((Advised)bean).getTargetSource().getTarget();
         }
      } catch (Exception var16) {
         throw new AssertionError(var16);
      }

      ReflectionUtils.makeAccessible(timeoutMethod);
      LifecycleEventCallbackInvocationContext invocationContext = new LifecycleEventCallbackInvocationContext(target, timeoutMethod, timer, this.getContextDataProvider());
      LinkedList interceptors = new LinkedList();
      boolean hasAnOrderer = this.hasAnOrderer(timeoutMethod);
      if (bean instanceof Advised) {
         Iterator var8 = this.getJeeInterceptorAdvisors((Advised)bean).iterator();

         label78:
         while(true) {
            List listeners;
            Object interceptionTarget;
            do {
               while(true) {
                  JeeInterceptorPointcutAdvisor jipa;
                  do {
                     do {
                        do {
                           if (!var8.hasNext()) {
                              break label78;
                           }

                           jipa = (JeeInterceptorPointcutAdvisor)var8.next();
                        } while(!hasAnOrderer && !jipa.isForTimeout());
                     } while(hasAnOrderer && !jipa.checkOrderer(timeoutMethod, this.getComponentClass()));
                  } while(jipa.isExcluded(timeoutMethod, this.getComponentClass()));

                  JeeInterceptorInterceptor advice = jipa.getAdvice();
                  InterceptorMetadata im = (InterceptorMetadata)advice.getInterceptorMetadata();
                  if (im == null) {
                     InterceptionMetadata iim = advice.getInterceptionMetadata();
                     if (iim != null) {
                        listeners = iim.selfTimeoutInterceptorMethodList;
                        interceptionTarget = target;
                        break;
                     }
                  } else if (im.getScheduledTimeoutMethod() == null || im.getScheduledTimeoutMethod().equals(timeoutMethod)) {
                     interceptionTarget = advice.getJeeInterceptorInstance();
                     listeners = im.getAroundTimeoutMethods();
                     break;
                  }
               }
            } while(listeners == null);

            Iterator var17 = listeners.iterator();

            while(var17.hasNext()) {
               Method lcm = (Method)var17.next();
               ReflectionUtils.makeAccessible(lcm);
               interceptors.add(new MethodInvocationData(interceptionTarget, lcm));
            }
         }
      }

      invocationContext.proceed(interceptors);
   }

   private void addJeeInterceptors(ProxyFactory pf, Object bean, Map instances) {
      Iterator var4 = this.interceptorOrderMap.keySet().iterator();

      while(var4.hasNext()) {
         PointcutMatch pm = (PointcutMatch)var4.next();
         Iterator var6 = ((List)this.interceptorOrderMap.get(pm)).iterator();

         while(var6.hasNext()) {
            InterceptorMetadataI im = (InterceptorMetadataI)var6.next();
            this.addJeeInterceptors(pf, im, pm, instances);
         }
      }

      var4 = this.getDeploymentUnitMetadata().getDefaultInterceptorMetadata().iterator();

      InterceptorMetadataI im;
      while(var4.hasNext()) {
         im = (InterceptorMetadataI)var4.next();
         this.addJeeInterceptors(pf, im, (PointcutMatch)null, instances);
      }

      var4 = this.interceptorMetadataList.iterator();

      while(var4.hasNext()) {
         im = (InterceptorMetadataI)var4.next();
         this.addJeeInterceptors(pf, im, (PointcutMatch)null, instances);
      }

      if (!this.selfInterceptorMethodList.isEmpty() && (!this.isMessageDrivenBean || !this.isCDIEnabled || BeanDiscoveryMode.ALL != this.beanDiscoveryMode)) {
         this.addSelfBusinessInterceptor(pf, bean);
      }

      if (this.getTimeoutMethod() != null && !this.selfTimeoutInterceptorMethodList.isEmpty()) {
         this.addSelfTimeoutInterceptor(pf, bean);
      }

   }

   private int addSpringAopAdvisors(ProxyFactory proxyFactory, Object bean) {
      ConfigurableListableBeanFactory clbf = ((AbstractApplicationContext)this.getComponentContext()).getBeanFactory();
      BeanFactoryAdvisorRetrievalHelper helper = new BeanFactoryAdvisorRetrievalHelper(clbf);
      List eligibleAdvisors = AopUtils.findAdvisorsThatCanApply(helper.findAdvisorBeans(), bean.getClass());
      Iterator var6 = eligibleAdvisors.iterator();

      while(var6.hasNext()) {
         Advisor advisor = (Advisor)var6.next();
         proxyFactory.addAdvisor(advisor);
      }

      return eligibleAdvisors.size();
   }

   private int addAspectJAdvisors(ProxyFactory proxyFactory) {
      BeanFactoryAspectJAdvisorsBuilder builder = new BeanFactoryAspectJAdvisorsBuilder(this.getComponentContext(), this.getDeploymentUnitMetadata().getAspectJAdvisorFactory());
      List eligibleAdvisors = builder.buildAspectJAdvisors();
      if (!eligibleAdvisors.isEmpty()) {
         proxyFactory.addAdvisor(ExposeInvocationInterceptor.ADVISOR);
      }

      Iterator var4 = eligibleAdvisors.iterator();

      while(var4.hasNext()) {
         Advisor a = (Advisor)var4.next();
         proxyFactory.addAdvisor(a);
      }

      return eligibleAdvisors.size();
   }

   private void addJeeInterceptors(ProxyFactory pf, InterceptorMetadataI im, PointcutMatch pm, Map instances) {
      if ((im.isSystemInterceptor() || !this.exclusionManager.isExcluded((Method)null, this.getComponentClass(), im)) && !im.isConstructorInterceptor()) {
         Object interceptorInstance = null;
         Object target = null;

         try {
            target = pf.getTargetSource().getTarget();
         } catch (Exception var17) {
         }

         if (target != null) {
            if (target instanceof TargetWrapper) {
               interceptorInstance = im.getAndClearExternallyCreatedInterceptor(((TargetWrapper)target).getBeanTarget());
            } else {
               interceptorInstance = im.getAndClearExternallyCreatedInterceptor(target);
            }
         }

         if (interceptorInstance == null) {
            interceptorInstance = instances.get(im.getComponentName());
            if (interceptorInstance == null) {
               interceptorInstance = this.getComponentContext().getBean(im.getComponentName());
               if (log.isDebugEnabled()) {
                  log.debug("creating ee interceptor from " + im + " for instance " + interceptorInstance);
               }
            } else if (log.isDebugEnabled()) {
               log.debug("using pre-existing interceptor from " + im + " for instance " + interceptorInstance);
            }
         } else if (log.isDebugEnabled()) {
            log.debug("Using externally created interceptor for " + im + " with instance " + interceptorInstance);
         }

         if (!im.isDefaultInterceptor()) {
            instances.put(im.getComponentName(), interceptorInstance);
         }

         Method aroundInvokeMethod = null;
         List superAroundInvokeMethods = new ArrayList();
         boolean isInterceptorToExclude = !im.isDefaultInterceptor() && this.isMessageDrivenBean && this.isCDIEnabled && BeanDiscoveryMode.ALL == this.beanDiscoveryMode && !im.getComponentName().equals("org.jboss.weld.module.ejb.SessionBeanInterceptor");
         if (!im.getAroundInvokeMethods().isEmpty() && !isInterceptorToExclude) {
            int idx = im.getAroundInvokeMethods().size() - 1;
            aroundInvokeMethod = (Method)im.getAroundInvokeMethods().get(idx);
            superAroundInvokeMethods = im.getAroundInvokeMethods().subList(0, idx);
         }

         JeeInterceptorInterceptor jeeii = new JeeInterceptorInterceptor(im, interceptorInstance, aroundInvokeMethod, this.getContextDataProvider());
         Object exclusions;
         if (!im.isSystemInterceptor()) {
            exclusions = this.exclusionManager.getExclusions();
         } else {
            exclusions = new HashMap();
         }

         JeeInterceptorPointcutAdvisor mipa = new JeeInterceptorPointcutAdvisor(im.getMatchingMethod(), jeeii, this, (Map)exclusions, this.orderers, pm, false);
         mipa.setBeanControlInterfaceAndMethods(this.beanControlInterface, this.beanControlInterfaceMethods);
         this.addSuperInterceptors(pf, interceptorInstance, (List)superAroundInvokeMethods, mipa);
         pf.addAdvisor(mipa);
         if (!im.getAroundTimeoutMethods().isEmpty() && this.isAMatchingTimeoutMethod(im)) {
            int idx = im.getAroundTimeoutMethods().size() - 1;
            Method aroundTimeoutMethod = (Method)im.getAroundTimeoutMethods().get(idx);
            JeeInterceptorInterceptor teeii = new JeeInterceptorInterceptor(im, interceptorInstance, aroundTimeoutMethod, this.getContextDataProvider());
            JeeInterceptorPointcutAdvisor jipa = new JeeInterceptorPointcutAdvisor(teeii, this, (Map)exclusions, this.orderers, false);
            jipa.setBeanControlInterfaceAndMethods(this.beanControlInterface, this.beanControlInterfaceMethods);
            this.addSuperInterceptors(pf, interceptorInstance, im.getAroundTimeoutMethods().subList(0, idx), jipa);
            pf.addAdvisor(jipa);
         }

      }
   }

   private void addSelfBusinessInterceptor(ProxyFactory pf, Object bean) {
      int idx = this.selfInterceptorMethodList.size() - 1;
      Method aroundInvokeMethod = (Method)this.selfInterceptorMethodList.get(idx);
      JeeInterceptorInterceptor jeeii = new JeeInterceptorInterceptor(bean, aroundInvokeMethod, this.getContextDataProvider());
      JeeInterceptorPointcutAdvisor mipa = new JeeInterceptorPointcutAdvisor((Method)null, jeeii, this, this.exclusionManager.getExclusions(), this.orderers, (PointcutMatch)null, true);
      mipa.setBeanControlInterfaceAndMethods(this.beanControlInterface, this.beanControlInterfaceMethods);
      this.addSuperInterceptors(pf, bean, this.selfInterceptorMethodList.subList(0, idx), mipa);
      pf.addAdvisor(mipa);
   }

   private void addSelfTimeoutInterceptor(ProxyFactory pf, Object bean) {
      int idx = this.selfTimeoutInterceptorMethodList.size() - 1;
      Method aroundTimeoutMethod = (Method)this.selfTimeoutInterceptorMethodList.get(idx);
      JeeInterceptorInterceptor jeeii = new JeeInterceptorInterceptor(this, bean, aroundTimeoutMethod);
      JeeInterceptorPointcutAdvisor jipa = new JeeInterceptorPointcutAdvisor(jeeii, this, this.exclusionManager.getExclusions(), this.orderers, true);
      jipa.setBeanControlInterfaceAndMethods(this.beanControlInterface, this.beanControlInterfaceMethods);
      this.addSuperInterceptors(pf, bean, this.selfTimeoutInterceptorMethodList.subList(0, idx), jipa);
      pf.addAdvisor(jipa);
   }

   private void addSuperInterceptors(ProxyFactory pf, Object baseInstance, List superIcptrMethods, JeeInterceptorPointcutAdvisor jipa) {
      if (log.isDebugEnabled() && !superIcptrMethods.isEmpty()) {
         log.debug("creating ee superinterceptor for instance " + baseInstance);
      }

      Iterator var5 = superIcptrMethods.iterator();

      while(var5.hasNext()) {
         Method m = (Method)var5.next();
         JeeInterceptorInterceptor jeeii = new JeeInterceptorInterceptor(baseInstance, m, this.getContextDataProvider());
         pf.addAdvisor(new SuperJeeInterceptorPointcutAdvisor(jipa, jeeii));
      }

   }

   private boolean isAMatchingTimeoutMethod(InterceptorMetadataI im) {
      if (im.getScheduledTimeoutMethod() != null) {
         return true;
      } else if (this.hasScheduledTimeouts() && im.getMatchingMethod() == null) {
         return true;
      } else if (this.getTimeoutMethod() == null) {
         return false;
      } else {
         return im.getMatchingMethod() == null ? true : this.getTimeoutMethod().getName().equals(im.getMatchingMethod().getName());
      }
   }

   public void setIsMessageDrivenBean(boolean isMessageDrivenBean) {
      this.isMessageDrivenBean = isMessageDrivenBean;
   }

   public void setIsCDIEnabled(boolean isCDIEnabled) {
      this.isCDIEnabled = isCDIEnabled;
   }

   public void setBeanDiscoveryMode(BeanDiscoveryMode beanDiscoveryMode) {
      this.beanDiscoveryMode = beanDiscoveryMode;
   }

   static {
      EXCLUDED_BUSINESS_INTERFACES.add("java.lang.Cloneable");
      EXCLUDED_BUSINESS_INTERFACES.add("java.io.Serializable");
      EXCLUDED_BUSINESS_INTERFACES.add("javax.ejb.SessionSynchronization");
      EXCLUDED_BUSINESS_INTERFACES.add("com.bea.core.repackaged.springframework.beans.factory.DisposableBean");
      EXCLUDED_BUSINESS_INTERFACES.add("com.bea.core.repackaged.springframework.beans.factory.InitializingBean");
      EXCLUDED_BUSINESS_INTERFACES.add("com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor");
      EXCLUDED_BUSINESS_INTERFACES.add("com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor");
   }

   private static class AdvisorChainProxyControl implements __ProxyControl {
      private final TargetWrapper target;

      AdvisorChainProxyControl(TargetWrapper target) {
         this.target = target;
      }

      public TargetWrapper __getTarget() {
         return this.target;
      }
   }
}
