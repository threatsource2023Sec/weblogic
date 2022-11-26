package weblogic.ejb.container.injection;

import com.oracle.pitchfork.intercept.PointcutMatch;
import com.oracle.pitchfork.interfaces.PitchforkUtils;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.MessageDrivenBean;
import javax.ejb.SessionBean;
import javax.ejb.SessionSynchronization;
import javax.interceptor.InvocationContext;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLSessionBean;
import weblogic.ejb.container.interfaces.WLSessionSynchronization;
import weblogic.ejb.container.internal.ContextDataProviderImpl;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.EjbCallbackBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.MethodParamsBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.injection.BaseComponentContributor;
import weblogic.j2ee.injection.J2eeComponentContributor;
import weblogic.j2ee.injection.PitchforkContext;

public class EjbComponentContributor extends J2eeComponentContributor {
   protected static final DebugLogger debugLogger;
   private final DeploymentInfo dinfo;
   private final Map metadataMap = new ConcurrentHashMap();
   private boolean defaultInterceptorAdded = false;

   public EjbComponentContributor(DeploymentInfo di, ClassLoader cl, PitchforkContext pc) {
      super(pc);
      this.dinfo = di;
      if (cl != null) {
         this.classLoader = cl;
      }

   }

   public void contribute(EnricherI enricher) {
      if (this.dinfo.getEjbDescriptorBean().isEjb30()) {
         EjbJarBean ejbJarBean = this.dinfo.getEjbDescriptorBean().getEjbJarBean();
         SessionBeanBean[] var3 = ejbJarBean.getEnterpriseBeans().getSessions();
         int var4 = var3.length;

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            EnterpriseBeanBean ebb = var3[var5];
            SessionBeanInfo sbi = (SessionBeanInfo)this.dinfo.getBeanInfo(ebb.getEjbName());
            this.contribute(enricher, ebb.getEjbName(), sbi.getGeneratedBeanClassName(), ebb);
         }

         MessageDrivenBeanBean[] var8 = ejbJarBean.getEnterpriseBeans().getMessageDrivens();
         var4 = var8.length;

         for(var5 = 0; var5 < var4; ++var5) {
            EnterpriseBeanBean ebb = var8[var5];
            MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)this.dinfo.getBeanInfo(ebb.getEjbName());
            this.contribute(enricher, ebb.getEjbName(), mdbi.getBeanClassNameToInstantiate(), ebb);
         }

      }
   }

   public Jsr250MetadataI newJsr250Metadata(String compName, Class compClass, DeploymentUnitMetadataI dum) {
      PitchforkUtils pu = this.pitchforkContext.getPitchforkUtils();
      InterceptionMetadataI metadata = pu.createEjbProxyMetadata(compName, compClass, dum, this.pitchforkContext.isSpringComponentFactoryClassName());
      metadata.setIsMessageDrivenBean(this.dinfo.getBeanInfo(compName) instanceof MessageDrivenBeanInfo);
      metadata.setIsCDIEnabled(this.dinfo.isCDIEnabled());
      metadata.setBeanDiscoveryMode(this.dinfo.getBeanDiscoveryMode());
      this.metadataMap.put(compName, metadata);
      return metadata;
   }

   public InterceptionMetadataI getMetadata(String componentName) {
      return (InterceptionMetadataI)this.metadataMap.get(componentName);
   }

   private Set getEjbIntfClasses(BeanInfo bi) {
      Set ejbIntfClasses = new HashSet();
      if (bi instanceof ClientDrivenBeanInfo) {
         ClientDrivenBeanInfo cdbi = (ClientDrivenBeanInfo)bi;
         if (cdbi.getLocalInterfaceClass() != null) {
            ejbIntfClasses.add(cdbi.getLocalInterfaceClass());
         }

         if (cdbi.getRemoteInterfaceClass() != null) {
            ejbIntfClasses.add(cdbi.getRemoteInterfaceClass());
         }

         if (bi instanceof SessionBeanInfo) {
            SessionBeanInfo sbi = (SessionBeanInfo)bi;
            if (sbi.hasBusinessLocals()) {
               ejbIntfClasses.addAll(sbi.getBusinessLocals());
            }

            if (sbi.hasBusinessRemotes()) {
               ejbIntfClasses.addAll(sbi.getBusinessRemotes());
            }
         }
      } else {
         if (!(bi instanceof MessageDrivenBeanInfo)) {
            throw new IllegalArgumentException(bi.getBeanClassName() + " is unknow bean type.");
         }

         ejbIntfClasses.add(((MessageDrivenBeanInfo)bi).getMessagingTypeInterfaceClass());
      }

      return ejbIntfClasses;
   }

   private Set getCtrlIntfMethods(Class genBeanInterface, Set ejbIntfClasses, BeanInfo bi) {
      Set ctrlMethods = new HashSet();
      boolean hasNoIntfView = bi.isSessionBean() && ((SessionBeanInfo)bi).hasNoIntfView() || bi instanceof MessageDrivenBeanInfo && ((MessageDrivenBeanInfo)bi).exposesNoInterfaceClientView();
      if (hasNoIntfView) {
         Collections.addAll(ctrlMethods, genBeanInterface.getDeclaredMethods());
      } else {
         Iterator var6 = ejbIntfClasses.iterator();

         while(var6.hasNext()) {
            Class clazz = (Class)var6.next();
            this.addCtrlIntfMethods(genBeanInterface, ctrlMethods, clazz);
         }
      }

      return ctrlMethods;
   }

   private void addCtrlIntfMethods(Class genBeanInterface, Set ctrlIntfMethods, Class ejbIntfClass) {
      Method[] var4 = ejbIntfClass.getDeclaredMethods();
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];

         try {
            ctrlIntfMethods.add(genBeanInterface.getMethod(m.getName(), m.getParameterTypes()));
         } catch (NoSuchMethodException var9) {
         }
      }

      Class[] var10 = ejbIntfClass.getInterfaces();
      var5 = var10.length;

      for(var6 = 0; var6 < var5; ++var6) {
         Class clazz = var10[var6];
         this.addCtrlIntfMethods(genBeanInterface, ctrlIntfMethods, clazz);
      }

   }

   private void addBeanCtrlIntfMethodsForWebService(Class genBeanInterface, Set ctrlIntfMethods, Collection webMethodInfos) {
      Iterator var4 = webMethodInfos.iterator();

      while(var4.hasNext()) {
         MethodInfo mi = (MethodInfo)var4.next();
         Method m = mi.getMethod();

         try {
            ctrlIntfMethods.add(genBeanInterface.getMethod(m.getName(), m.getParameterTypes()));
         } catch (NoSuchMethodException var8) {
         }
      }

   }

   protected void contribute(Jsr250MetadataI metadata, J2eeEnvironmentBean envBean) {
      this.buildInterceptionMetadata((InterceptionMetadataI)metadata, envBean);
      metadata.registerContextDataProvider(ContextDataProviderImpl.getInstance());
   }

   private void buildInterceptionMetadata(InterceptionMetadataI metadata, J2eeEnvironmentBean envBean) {
      String ejbName = null;
      String beanClassName = null;
      AroundInvokeBean[] aroundInvokeBeans = null;
      AroundTimeoutBean[] aroundTimeoutBeans = null;
      Class generatedBeanInterface = null;
      BeanInfo beanInfo = this.dinfo.getBeanInfo(metadata.getComponentName());
      if (envBean instanceof SessionBeanBean) {
         SessionBeanBean sessionBeanBean = (SessionBeanBean)envBean;
         ejbName = sessionBeanBean.getEjbName();
         beanClassName = sessionBeanBean.getEjbClass();
         aroundInvokeBeans = sessionBeanBean.getAroundInvokes();
         aroundTimeoutBeans = sessionBeanBean.getAroundTimeouts();
         generatedBeanInterface = ((ClientDrivenBeanInfo)beanInfo).getGeneratedBeanInterface();
         if (beanInfo.getTimeoutMethod() != null) {
            metadata.setTimeoutMethod(beanInfo.getTimeoutMethod());
         }

         metadata.setHasScheduledTimeouts(!beanInfo.getAutomaticTimerMethods().isEmpty());
      } else {
         if (!(envBean instanceof MessageDrivenBeanBean)) {
            throw new IllegalArgumentException("Bean: " + envBean + " is not a Session or MessageDriven Bean");
         }

         MessageDrivenBeanBean messageDrivenBeanBean = (MessageDrivenBeanBean)envBean;
         ejbName = messageDrivenBeanBean.getEjbName();
         beanClassName = messageDrivenBeanBean.getEjbClass();
         aroundInvokeBeans = messageDrivenBeanBean.getAroundInvokes();
         aroundTimeoutBeans = messageDrivenBeanBean.getAroundTimeouts();
         MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)beanInfo;
         generatedBeanInterface = mdbi.exposesNoInterfaceClientView() ? mdbi.getGeneratedBeanInterface() : mdbi.getMessagingTypeInterfaceClass();
         if (beanInfo.getTimeoutMethod() != null) {
            metadata.setTimeoutMethod(beanInfo.getTimeoutMethod());
         }

         metadata.setHasScheduledTimeouts(!beanInfo.getAutomaticTimerMethods().isEmpty());
      }

      Set ejbIntfClasses = this.getEjbIntfClasses(beanInfo);
      List businessInterfaces = metadata.findBusinessInterfacesFromClassOrAnnotation(metadata.getComponentClass());
      if (envBean instanceof SessionBeanBean) {
         businessInterfaces.removeAll(ejbIntfClasses);
      }

      businessInterfaces.remove(generatedBeanInterface);
      Iterator var11 = businessInterfaces.iterator();

      while(var11.hasNext()) {
         Class businessInterface = (Class)var11.next();
         metadata.addBusinessInterface(businessInterface);
      }

      Set beanControlInterfaceMethods = this.getCtrlIntfMethods(generatedBeanInterface, ejbIntfClasses, beanInfo);
      if (envBean instanceof SessionBeanBean && ((ClientDrivenBeanInfo)beanInfo).hasWebserviceClientView()) {
         Collection wsMInfos = ((ClientDrivenBeanInfo)beanInfo).getAllWebserviceMethodInfos();
         this.addBeanCtrlIntfMethodsForWebService(generatedBeanInterface, beanControlInterfaceMethods, wsMInfos);
      }

      metadata.setBeanControlInterface(generatedBeanInterface);
      metadata.setBeanControlInterfaceMethods(beanControlInterfaceMethods);
      if (beanInfo.isSessionBean()) {
         SessionBeanInfo sbi = (SessionBeanInfo)beanInfo;
         metadata.registerContainerControlInterface(WLEnterpriseBean.class);
         if (SessionBean.class.isAssignableFrom(metadata.getComponentClass())) {
            metadata.registerContainerControlInterface(SessionBean.class);
         }

         if (sbi.isStateful()) {
            metadata.registerContainerControlInterface(WLSessionBean.class);
            if (((StatefulSessionBeanInfo)sbi).implementsSessionSynchronization()) {
               metadata.registerContainerControlInterface(WLSessionSynchronization.class);
               if (SessionSynchronization.class.isAssignableFrom(metadata.getComponentClass())) {
                  metadata.registerContainerControlInterface(SessionSynchronization.class);
               }
            }
         }
      }

      if (envBean instanceof MessageDrivenBeanBean && MessageDrivenBean.class.isAssignableFrom(metadata.getComponentClass())) {
         metadata.registerContainerControlInterface(MessageDrivenBean.class);
      }

      this.preInterceptorProcessing(metadata, beanInfo);
      AssemblyDescriptorBean adb = this.dinfo.getEjbDescriptorBean().getEjbJarBean().getAssemblyDescriptor();
      if (adb != null) {
         InterceptorBindingBean[] ibbs = adb.getInterceptorBindings();
         this.addRequiredCdiInterceptors(metadata);
         this.addDefaultInterceptors(metadata, ibbs);
         this.addClassLevelInterceptors(metadata, ibbs, ejbName, metadata.getComponentClass());
         List constructorLevelInterceptorBindings = new ArrayList();
         this.addMethodLevelInterceptors(metadata, ibbs, ejbName, generatedBeanInterface, beanInfo, constructorLevelInterceptorBindings);
         this.addConstructorLevelInterceptors(metadata, constructorLevelInterceptorBindings);
      }

      this.addSelfInterceptors(metadata, aroundInvokeBeans, aroundTimeoutBeans, beanClassName);
      this.postInterceptorProcessing(metadata, beanInfo);
   }

   protected void preInterceptorProcessing(InterceptionMetadataI meta, BeanInfo bi) {
   }

   protected void postInterceptorProcessing(InterceptionMetadataI meta, BeanInfo bi) {
   }

   protected void addRequiredCdiInterceptors(InterceptionMetadataI jsr250Metadata) {
   }

   private void addDefaultInterceptors(InterceptionMetadataI meta, InterceptorBindingBean[] ibbs) {
      if (!this.defaultInterceptorAdded) {
         Set classNames = new HashSet();
         InterceptorBindingBean[] var4 = ibbs;
         int var5 = ibbs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            InterceptorBindingBean ibb = var4[var6];
            if ("*".equals(ibb.getEjbName())) {
               String[] iceptorClasses = null;
               if (ibb.getInterceptorOrder() != null) {
                  iceptorClasses = ibb.getInterceptorOrder().getInterceptorClasses();
               } else {
                  iceptorClasses = ibb.getInterceptorClasses();
               }

               String[] var9 = iceptorClasses;
               int var10 = iceptorClasses.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String interceptorClassName = var9[var11];
                  if (!classNames.contains(interceptorClassName)) {
                     InterceptorMetadataI im = this.createInterceptorMetadata(interceptorClassName, (Method)null);
                     im.setDefaultInterceptor(true);
                     meta.getDeploymentUnitMetadata().registerDefaultInterceptorMetadata(im);
                     classNames.add(interceptorClassName);
                  }
               }
            }
         }

         this.defaultInterceptorAdded = true;
      }
   }

   private void addClassLevelInterceptors(InterceptionMetadataI metadata, InterceptorBindingBean[] ibbs, String ejbName, Class generatedBeanClass) {
      InterceptorBindingBean[] var5 = ibbs;
      int var6 = ibbs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         InterceptorBindingBean ibb = var5[var7];
         if (ejbName.equals(ibb.getEjbName()) && ibb.getMethod() == null) {
            if (ibb.getInterceptorOrder() != null) {
               this.createInterceptorOrder(metadata, ibb, (Method)null, EjbComponentContributor.InterceptorLevel.CLASS);
            } else {
               String[] var9 = ibb.getInterceptorClasses();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String interceptorClassName = var9[var11];
                  InterceptorMetadataI im = this.createInterceptorMetadata(interceptorClassName, (Method)null);
                  im.setClassInterceptor(true);
                  metadata.registerInterceptorMetadata(im);
               }

               this.setExcludeInterceptors(ibb, metadata, generatedBeanClass);
            }
         }
      }

   }

   private boolean isForThisTimeoutMethod(NamedMethodBean nmb, Method timeoutMethod) {
      if (timeoutMethod == null) {
         return false;
      } else {
         List useMe = new LinkedList();
         useMe.add(timeoutMethod);
         return this.getScheduledTimeoutMethod(nmb, useMe) != null;
      }
   }

   private Method getScheduledTimeoutMethod(NamedMethodBean nmb, Collection methods) {
      if (methods == null) {
         return null;
      } else {
         Iterator var3 = methods.iterator();

         Method method;
         boolean found;
         do {
            String[] params;
            Class[] methodParameters;
            do {
               do {
                  if (!var3.hasNext()) {
                     return null;
                  }

                  method = (Method)var3.next();
               } while(!method.getName().equals(nmb.getMethodName()));

               MethodParamsBean paramsBean = nmb.getMethodParams();
               if (paramsBean == null) {
                  return method;
               }

               params = paramsBean.getMethodParams();
               methodParameters = method.getParameterTypes();
            } while(params.length != methodParameters.length);

            found = true;

            for(int lcv = 0; lcv < params.length; ++lcv) {
               String methodParamName = methodParameters[lcv].getName().trim();
               String paramName = params[lcv].trim();
               if (!methodParamName.equals(paramName)) {
                  found = false;
                  break;
               }
            }
         } while(!found);

         return method;
      }
   }

   protected void addConstructorLevelInterceptors(InterceptionMetadataI metadata, List constructorIBBs) {
      Iterator var3 = constructorIBBs.iterator();

      while(true) {
         while(true) {
            InterceptorBindingBean ibb;
            NamedMethodBean specifiedMethodBean;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               ibb = (InterceptorBindingBean)var3.next();
               specifiedMethodBean = ibb.getMethod();
            } while(specifiedMethodBean.getMethodParams() != null && specifiedMethodBean.getMethodParams().getMethodParams().length != 0);

            if (ibb.getInterceptorOrder() != null) {
               this.createInterceptorOrder(metadata, ibb, (Method)null, EjbComponentContributor.InterceptorLevel.CONSTRUCTOR);
            } else {
               String[] var6 = ibb.getInterceptorClasses();
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String interceptorClassName = var6[var8];
                  InterceptorMetadataI im = this.createInterceptorMetadata(interceptorClassName, (Method)null);
                  im.setConstructorInterceptor(true);
                  metadata.registerInterceptorMetadata(im);
               }

               this.setExcludeInterceptors(ibb, metadata, metadata.getComponentClass());
            }
         }
      }
   }

   protected void addMethodLevelInterceptors(InterceptionMetadataI metadata, InterceptorBindingBean[] ibbs, String ejbName, Class generatedBeanInterface, BeanInfo bi, List constructorIBBs) {
      InterceptorBindingBean[] var7 = ibbs;
      int var8 = ibbs.length;

      label87:
      for(int var9 = 0; var9 < var8; ++var9) {
         InterceptorBindingBean ibb = var7[var9];
         if (ejbName.equals(ibb.getEjbName())) {
            NamedMethodBean specifiedMethodBean = ibb.getMethod();
            if (specifiedMethodBean != null) {
               if (specifiedMethodBean.getMethodName().equals(bi.getBeanClass().getSimpleName())) {
                  constructorIBBs.add(ibb);
               } else {
                  String specifiedMethodName = specifiedMethodBean.getMethodName();
                  List interceptedMethods = new ArrayList();
                  Method scheduledTimeoutMethod = this.getScheduledTimeoutMethod(specifiedMethodBean, bi.getAutomaticTimerMethods());
                  int var19;
                  if (this.isForThisTimeoutMethod(specifiedMethodBean, bi.getTimeoutMethod())) {
                     interceptedMethods.add(bi.getTimeoutMethod());
                  } else if (scheduledTimeoutMethod != null) {
                     interceptedMethods.add(scheduledTimeoutMethod);
                  } else {
                     int index;
                     Method m;
                     if (specifiedMethodBean.getMethodParams() == null) {
                        Method[] var15 = generatedBeanInterface.getDeclaredMethods();
                        int var16 = var15.length;

                        for(index = 0; index < var16; ++index) {
                           m = var15[index];
                           if (specifiedMethodName.equals(m.getName())) {
                              interceptedMethods.add(m);
                           }
                        }
                     } else {
                        String[] params = new String[0];
                        if (specifiedMethodBean.getMethodParams() != null) {
                           params = specifiedMethodBean.getMethodParams().getMethodParams();
                        }

                        Class[] paramTypes = new Class[params.length];
                        index = 0;
                        String[] var26 = params;
                        var19 = params.length;

                        for(int var20 = 0; var20 < var19; ++var20) {
                           String param = var26[var20];
                           paramTypes[index] = this.forName(param, this.classLoader);
                           ++index;
                        }

                        m = this.getDeclaredMethod(generatedBeanInterface, specifiedMethodName, paramTypes);
                        interceptedMethods.add(m);
                     }
                  }

                  Iterator var23 = interceptedMethods.iterator();

                  while(true) {
                     while(true) {
                        if (!var23.hasNext()) {
                           continue label87;
                        }

                        Method interceptedMethod = (Method)var23.next();
                        if (ibb.getInterceptorOrder() != null) {
                           this.createInterceptorOrder(metadata, ibb, interceptedMethod, EjbComponentContributor.InterceptorLevel.METHOD);
                        } else {
                           String[] var27 = ibb.getInterceptorClasses();
                           int var28 = var27.length;

                           for(var19 = 0; var19 < var28; ++var19) {
                              String className = var27[var19];
                              InterceptorMetadataI im = this.createInterceptorMetadata(className, interceptedMethod);
                              if (scheduledTimeoutMethod != null) {
                                 im.setScheduledTimeoutMethod(scheduledTimeoutMethod);
                              }

                              metadata.registerInterceptorMetadata(im);
                           }

                           this.setExcludeInterceptors(ibb, metadata, interceptedMethod);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void addSelfInterceptors(InterceptionMetadataI metadata, AroundInvokeBean[] aroundInvokeBeans, AroundTimeoutBean[] aroundTimeoutBeans, String beanClassName) {
      AroundInvokeBean[] var5 = aroundInvokeBeans;
      int var6 = aroundInvokeBeans.length;

      int var7;
      String className;
      Method m;
      for(var7 = 0; var7 < var6; ++var7) {
         AroundInvokeBean aib = var5[var7];
         className = aib.getClassName() != null ? aib.getClassName() : beanClassName;
         m = this.getDeclaredMethod(this.loadClass(className), aib.getMethodName(), new Class[]{InvocationContext.class});
         metadata.registerSelfInterceptorMethod(m);
      }

      AroundTimeoutBean[] var11 = aroundTimeoutBeans;
      var6 = aroundTimeoutBeans.length;

      for(var7 = 0; var7 < var6; ++var7) {
         AroundTimeoutBean atb = var11[var7];
         className = atb.getClassName() != null ? atb.getClassName() : beanClassName;
         m = this.getDeclaredMethod(this.loadClass(className), atb.getMethodName(), new Class[]{InvocationContext.class});
         metadata.registerSelfTimeoutInterceptorMethod(m);
      }

   }

   private void createInterceptorOrder(InterceptionMetadataI metadata, InterceptorBindingBean ibb, Method matchingMethod, InterceptorLevel interceptorType) {
      String[] var5 = ibb.getInterceptorOrder().getInterceptorClasses();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String className = var5[var7];
         InterceptorMetadataI im = this.createInterceptorMetadata(className, matchingMethod);
         if (interceptorType == EjbComponentContributor.InterceptorLevel.DEFAULT) {
            im.setDefaultInterceptor(true);
         } else if (interceptorType == EjbComponentContributor.InterceptorLevel.CLASS) {
            im.setClassInterceptor(true);
         } else if (interceptorType == EjbComponentContributor.InterceptorLevel.CONSTRUCTOR) {
            im.setConstructorInterceptor(true);
         }

         PointcutMatch pm = new PointcutMatch(metadata.getComponentClass(), matchingMethod);
         metadata.registerInterceptorOrder(pm, im);
      }

   }

   private Class getInterceptorImplClass(String icptrClassName, Class icptrClass) {
      if (Serializable.class.isAssignableFrom(icptrClass)) {
         return icptrClass;
      } else {
         NamingConvention nc = new NamingConvention(icptrClassName);
         return this.loadClass(nc.getInterceptorImplClassName());
      }
   }

   private InterceptorMetadataI createInterceptorMetadata(String interceptorClassName, Method matchingMethod) {
      Class interceptorClass = this.loadClass(interceptorClassName);
      InterceptorBean interceptorBean = null;
      InterceptorBean[] var5 = this.dinfo.getEjbDescriptorBean().getEjbJarBean().getInterceptors().getInterceptors();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         InterceptorBean ib = var5[var7];
         if (interceptorClass.getName().equals(ib.getInterceptorClass())) {
            interceptorBean = ib;
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

      InterceptorMetadataI im = this.createIcptrMetadata(interceptorClass, aroundInvokeMethods, aroundTimeoutMethods, matchingMethod);
      this.buildInterceptorInjectionMetaData(im, interceptorBean);
      this.addLifecycleMethods(im, interceptorBean, true);
      return im;
   }

   protected void buildInterceptorInjectionMetaData(InterceptorMetadataI im, InterceptorBean ib) {
      this.buildInjectionMetadata(im, ib);
      this.buildInjectionMetadataFromWholeDD(im, ib);
   }

   protected InterceptorMetadataI createIcptrMetadata(Class clazz, List aroundInvokeMethods, List aroundTimeoutMethods, Method matchingMethod) {
      return this.pitchforkContext.getPitchforkUtils().createInterceptorMetadata(this.getInterceptorImplClass(clazz.getName(), clazz), aroundInvokeMethods, aroundTimeoutMethods, matchingMethod);
   }

   private void buildInjectionMetadataFromWholeDD(InterceptorMetadataI im, InterceptorBean ib) {
      Iterator var3 = this.gatherAllResourceEnvRefInEJB().iterator();

      while(var3.hasNext()) {
         ResourceEnvRefBean rerb = (ResourceEnvRefBean)var3.next();
         List targetBeans = new ArrayList();
         InjectionTargetBean[] var6 = rerb.getInjectionTargets();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            InjectionTargetBean itb = var6[var8];
            if (ib.getInterceptorClass().equals(itb.getInjectionTargetClass())) {
               targetBeans.add(itb);
            }
         }

         if (!targetBeans.isEmpty()) {
            BaseComponentContributor.ParseResults pr = this.parseInjectionTarget(im, (InjectionTargetBean[])targetBeans.toArray(new InjectionTargetBean[0]), rerb.getResourceEnvRefType(), rerb.getResourceEnvRefName());
            this.insertOrOverwriteInjectionStrategy(im.getInjections(), pr.getInjections());
         }
      }

   }

   private List gatherAllResourceEnvRefInEJB() {
      EnterpriseBeansBean ebb = this.dinfo.getEjbDescriptorBean().getEjbJarBean().getEnterpriseBeans();
      List result = new ArrayList();
      SessionBeanBean[] var3 = ebb.getSessions();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         SessionBeanBean sbb = var3[var5];
         Collections.addAll(result, sbb.getResourceEnvRefs());
      }

      MessageDrivenBeanBean[] var7 = ebb.getMessageDrivens();
      var4 = var7.length;

      for(var5 = 0; var5 < var4; ++var5) {
         MessageDrivenBeanBean mdbb = var7[var5];
         Collections.addAll(result, mdbb.getResourceEnvRefs());
      }

      EntityBeanBean[] var8 = ebb.getEntities();
      var4 = var8.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EntityBeanBean entity = var8[var5];
         Collections.addAll(result, entity.getResourceEnvRefs());
      }

      return result;
   }

   private void setExcludeInterceptors(InterceptorBindingBean ibb, InterceptionMetadataI meta, AnnotatedElement ae) {
      if (ibb.isExcludeClassInterceptors()) {
         meta.setExcludeClassInterceptors(ae);
      }

      if (ibb.isExcludeDefaultInterceptors()) {
         meta.setExcludeDefaultInterceptors(ae);
      }

   }

   protected void addLifecycleMethods(Jsr250MetadataI metadata, J2eeEnvironmentBean envBean) {
      this.addLifecycleMethods(metadata, envBean, false);
   }

   protected void addLifecycleMethods(Jsr250MetadataI metadata, J2eeEnvironmentBean envBean, boolean isInterceptorClass) {
      super.addLifecycleMethods(metadata, envBean);
      if (envBean instanceof EjbCallbackBean) {
         EjbCallbackBean ejbCallbackBean = (EjbCallbackBean)envBean;
         LifecycleCallbackBean[] var5 = ejbCallbackBean.getPostActivates();
         int var6 = var5.length;

         int var7;
         LifecycleCallbackBean lifecycleCallbackBean;
         for(var7 = 0; var7 < var6; ++var7) {
            lifecycleCallbackBean = var5[var7];
            this.addLifecycleMethods(metadata, lifecycleCallbackBean, LifecycleEvent.POST_ACTIVATE);
         }

         var5 = ejbCallbackBean.getPrePassivates();
         var6 = var5.length;

         for(var7 = 0; var7 < var6; ++var7) {
            lifecycleCallbackBean = var5[var7];
            this.addLifecycleMethods(metadata, lifecycleCallbackBean, LifecycleEvent.PRE_PASSIVATE);
         }
      }

      if (!isInterceptorClass) {
         BeanInfo bi = this.dinfo.getBeanInfo(metadata.getComponentName());
         if (bi != null && (!bi.isSessionBean() || ((SessionBeanInfo)bi).isStateless())) {
            Method lcMethod = this.getMatchingLifecycleMethod(metadata.getComponentClass(), "ejbCreate");
            if (lcMethod != null) {
               if (lcMethod.getAnnotation(PostConstruct.class) != null) {
                  return;
               }

               metadata.registerLifecycleEventCallbackMethod(LifecycleEvent.POST_CONSTRUCT, lcMethod);
            }
         }

      }
   }

   private Method getMatchingLifecycleMethod(Class c, String callbackMethodName) {
      if (c != null && !c.getName().equals("java.lang.Object")) {
         Method lifecycleMethod = null;
         Method[] var4 = c.getDeclaredMethods();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            if (callbackMethodName.equals(m.getName()) && m.getParameterTypes().length == 0) {
               lifecycleMethod = m;
               break;
            }
         }

         if (lifecycleMethod == null) {
            lifecycleMethod = this.getMatchingLifecycleMethod(c.getSuperclass(), callbackMethodName);
         }

         return lifecycleMethod;
      } else {
         return null;
      }
   }

   protected void addEnvEntry(EnvEntryBean envEntry, Jsr250MetadataI metadata) {
      if (envEntry.getEnvEntryValue() == null && envEntry.getLookupName() == null) {
         ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(this.dinfo.getApplicationId());
         ApplicationBean appBean = appCtx.getApplicationDD();
         if (appBean != null) {
            EnvEntryBean[] var5 = appBean.getEnvEntries();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               EnvEntryBean appEntry = var5[var7];
               if (envEntry.getEnvEntryName().equals(appEntry.getEnvEntryName())) {
                  envEntry.setEnvEntryValue(appEntry.getEnvEntryValue());
                  envEntry.setLookupName(appEntry.getLookupName());
               }
            }
         }
      }

      super.addEnvEntry(envEntry, metadata);
   }

   protected InterceptorBean[] getInterceptorBeans() {
      InterceptorsBean ibs = this.dinfo.getEjbDescriptorBean().getEjbJarBean().getInterceptors();
      return ibs != null ? ibs.getInterceptors() : null;
   }

   protected final Class loadClass(String className) {
      return super.loadClass(className, this.classLoader);
   }

   protected void debug(String s) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EjbComponentContributor] " + s);
      }

   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }

   protected static enum InterceptorLevel {
      SYSTEM,
      DEFAULT,
      CLASS,
      METHOD,
      CONSTRUCTOR;
   }
}
