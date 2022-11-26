package weblogic.managedbean;

import com.oracle.pitchfork.interfaces.ManagedBeanContributorBroker;
import com.oracle.pitchfork.interfaces.MetadataParseException;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.interceptor.InvocationContext;
import weblogic.application.ModuleException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.wl.ManagedBeanBean;
import weblogic.j2ee.descriptor.wl.ManagedBeansBean;
import weblogic.j2ee.injection.J2eeComponentContributor;
import weblogic.j2ee.injection.PitchforkContext;

public class ManagedBeanContributor extends J2eeComponentContributor {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugManagedBean");
   private final ManagedBeansBean managedBeansBean;
   private final ConcurrentHashMap metadataMap = new ConcurrentHashMap();
   private final ManagedBeanContributorBroker contributorBroker;
   private boolean defaultInterceptorAdded = false;

   public ManagedBeanContributor(ManagedBeansBean bean, ClassLoader classLoader, PitchforkContext pitchforkContext) {
      super(pitchforkContext);
      this.managedBeansBean = bean;
      this.classLoader = classLoader;
      this.contributorBroker = pitchforkContext.getPitchforkUtils().createManagedBeanComponentContributorBroker();
   }

   public void initialize() throws ModuleException {
      try {
         this.contributorBroker.initialize(this.classLoader, this);
      } catch (Throwable var2) {
         throw new ModuleException(var2);
      }
   }

   public void contribute(EnricherI enricher) {
      ManagedBeanBean[] var2 = this.managedBeansBean.getManagedBeans();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ManagedBeanBean bean = var2[var4];
         this.contribute(enricher, bean.getManagedBeanName(), bean.getManagedBeanClass(), bean);
      }

   }

   public Jsr250MetadataI getMetadata(String componentName) {
      return (Jsr250MetadataI)this.metadataMap.get(componentName);
   }

   public void removeMetatdata(String componentName) {
      this.metadataMap.remove(componentName);
   }

   public Jsr250MetadataI newJsr250Metadata(String componentName, Class componentClass, DeploymentUnitMetadataI metadata) {
      Jsr250MetadataI metaData = this.contributorBroker.createJsr250Metadata(metadata, componentName, componentClass);
      this.metadataMap.put(componentClass.getName(), metaData);
      return metaData;
   }

   protected void contribute(Jsr250MetadataI jsr250Metadata, J2eeEnvironmentBean environmentGroupBean) {
      this.addDefaultInterceptors((InterceptionMetadataI)jsr250Metadata, this.managedBeansBean.getInterceptorBindings(), this.managedBeansBean.getInterceptors());
      this.addClassLevelInterceptors((InterceptionMetadataI)jsr250Metadata, this.managedBeansBean);
      this.addMethodLevelInterceptors((InterceptionMetadataI)jsr250Metadata, this.managedBeansBean);
      this.addSelfInterceptors((InterceptionMetadataI)jsr250Metadata, ((ManagedBeanBean)environmentGroupBean).getAroundInvokes(), ((ManagedBeanBean)environmentGroupBean).getAroundTimeouts(), ((ManagedBeanBean)environmentGroupBean).getManagedBeanClass());
   }

   private void addDefaultInterceptors(InterceptionMetadataI jsr250Metadata, InterceptorBindingBean[] interceptorBindings, InterceptorsBean interceptors) {
      if (!this.defaultInterceptorAdded) {
         if (interceptorBindings == null) {
            this.defaultInterceptorAdded = true;
         } else {
            Set classNames = new HashSet();
            InterceptorBindingBean[] var5 = interceptorBindings;
            int var6 = interceptorBindings.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               InterceptorBindingBean interceptorBindingBean = var5[var7];
               if ("*".equals(interceptorBindingBean.getEjbName())) {
                  String[] iceptorClasses = null;
                  if (interceptorBindingBean.getInterceptorOrder() != null) {
                     iceptorClasses = interceptorBindingBean.getInterceptorOrder().getInterceptorClasses();
                  } else {
                     iceptorClasses = interceptorBindingBean.getInterceptorClasses();
                  }

                  String[] var10 = iceptorClasses;
                  int var11 = iceptorClasses.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     String interceptorClassName = var10[var12];
                     if (!classNames.contains(interceptorClassName)) {
                        InterceptorMetadataI im = this.createInterceptorMetadata(interceptors.lookupInterceptor(interceptorClassName), (Method)null);
                        im.setDefaultInterceptor(true);
                        jsr250Metadata.getDeploymentUnitMetadata().registerDefaultInterceptorMetadata(im);
                        classNames.add(interceptorClassName);
                     }
                  }
               }
            }

            this.defaultInterceptorAdded = true;
         }
      }
   }

   private void addClassLevelInterceptors(InterceptionMetadataI jsr250Metadata, ManagedBeansBean mbb) {
      if (mbb.getInterceptorBindings() != null && mbb.getInterceptors() != null) {
         String beanName = jsr250Metadata.getComponentName();
         InterceptorBindingBean[] var4 = mbb.getInterceptorBindings();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            InterceptorBindingBean ibb = var4[var6];
            if (beanName.equals(ibb.getEjbName()) && ibb.getMethod() == null) {
               String[] var8 = ibb.getInterceptorClasses();
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  String interceptorClassName = var8[var10];
                  if (mbb.getInterceptors() == null) {
                     break;
                  }

                  InterceptorBean ib = mbb.getInterceptors().lookupInterceptor(interceptorClassName);
                  if (ib != null) {
                     InterceptorMetadataI im = this.createInterceptorMetadata(ib, (Method)null);
                     im.setClassInterceptor(true);
                     jsr250Metadata.registerInterceptorMetadata(im);
                  }
               }
            }
         }

      }
   }

   private void setExcludeInterceptors(InterceptorBindingBean interceptorBindingBean, InterceptionMetadataI jsr250Metadata, AnnotatedElement methodOrClass) {
      if (interceptorBindingBean.isExcludeClassInterceptors()) {
         jsr250Metadata.setExcludeClassInterceptors(methodOrClass);
         this.debug("ExcludeClassInterceptors for " + methodOrClass);
      }

   }

   private void addMethodLevelInterceptors(InterceptionMetadataI jsr250Metadata, ManagedBeansBean mbb) {
      if (mbb.getInterceptorBindings() != null && mbb.getInterceptors() != null) {
         String beanName = jsr250Metadata.getComponentName();
         InterceptorBindingBean[] var4 = mbb.getInterceptorBindings();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            InterceptorBindingBean interceptorBindingBean = var4[var6];
            if (beanName.equals(interceptorBindingBean.getEjbName()) && interceptorBindingBean.getMethod() != null) {
               ManagedBeanBean mb = mbb.lookupManagedBean(beanName);
               if (mb != null) {
                  Class beanClass = this.loadClass(mb.getManagedBeanClass(), this.classLoader);
                  List interceptedMethods = new ArrayList();
                  Method[] methodOnClass;
                  int index;
                  if (interceptorBindingBean.getMethod().getMethodParams() == null) {
                     methodOnClass = beanClass.getDeclaredMethods();
                     int var12 = methodOnClass.length;

                     for(int var13 = 0; var13 < var12; ++var13) {
                        Method m = methodOnClass[var13];
                        if (interceptorBindingBean.getMethod().getMethodName().equals(m.getName())) {
                           interceptedMethods.add(m);
                        }
                     }
                  } else {
                     methodOnClass = null;
                     String[] params = new String[0];
                     if (interceptorBindingBean.getMethod().getMethodParams() != null) {
                        params = interceptorBindingBean.getMethod().getMethodParams().getMethodParams();
                     }

                     Class[] paramTypes = new Class[params.length];
                     index = 0;
                     String[] var15 = params;
                     int var16 = params.length;

                     int var17;
                     for(var17 = 0; var17 < var16; ++var17) {
                        String param = var15[var17];
                        paramTypes[index] = this.forName(param, this.classLoader);
                        ++index;
                     }

                     Method methodOnClass = this.getMethod(beanClass, interceptorBindingBean.getMethod().getMethodName(), paramTypes);
                     interceptedMethods.add(methodOnClass);
                     Class[] var26 = methodOnClass.getDeclaringClass().getInterfaces();
                     var16 = var26.length;

                     for(var17 = 0; var17 < var16; ++var17) {
                        Class clazz = var26[var17];
                        methodOnClass = this.getMethod(clazz, interceptorBindingBean.getMethod().getMethodName(), paramTypes);
                        if (methodOnClass != null) {
                           interceptedMethods.add(methodOnClass);
                        }
                     }
                  }

                  Method interceptedMethod;
                  for(Iterator var21 = interceptedMethods.iterator(); var21.hasNext(); this.setExcludeInterceptors(interceptorBindingBean, jsr250Metadata, interceptedMethod)) {
                     interceptedMethod = (Method)var21.next();
                     String[] var24 = interceptorBindingBean.getInterceptorClasses();
                     index = var24.length;

                     for(int var27 = 0; var27 < index; ++var27) {
                        String clsName = var24[var27];
                        if (mbb.getInterceptors() == null) {
                           break;
                        }

                        InterceptorBean ib = mbb.getInterceptors().lookupInterceptor(clsName);
                        if (ib != null) {
                           InterceptorMetadataI im = this.createInterceptorMetadata(ib, interceptedMethod);
                           jsr250Metadata.registerInterceptorMetadata(im);
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private void addSelfInterceptors(InterceptionMetadataI jsr250Metadata, AroundInvokeBean[] aroundInvokeBeans, AroundTimeoutBean[] aroundTimeoutBeans, String beanClassName) {
      AroundInvokeBean[] var5 = aroundInvokeBeans;
      int var6 = aroundInvokeBeans.length;

      int var7;
      Class aroundInvokeClass;
      Method methodOnClass;
      for(var7 = 0; var7 < var6; ++var7) {
         AroundInvokeBean aroundInvokeBean = var5[var7];
         if (aroundInvokeBean.getClassName() != null) {
            aroundInvokeClass = this.loadClass(aroundInvokeBean.getClassName(), this.classLoader);
         } else {
            aroundInvokeClass = this.loadClass(beanClassName, this.classLoader);
         }

         methodOnClass = this.getDeclaredMethod(aroundInvokeClass, aroundInvokeBean.getMethodName(), new Class[]{InvocationContext.class});
         jsr250Metadata.registerSelfInterceptorMethod(methodOnClass);
      }

      AroundTimeoutBean[] var11 = aroundTimeoutBeans;
      var6 = aroundTimeoutBeans.length;

      for(var7 = 0; var7 < var6; ++var7) {
         AroundTimeoutBean aroundTimeoutBean = var11[var7];
         if (aroundTimeoutBean.getClassName() != null) {
            aroundInvokeClass = this.loadClass(aroundTimeoutBean.getClassName(), this.classLoader);
         } else {
            aroundInvokeClass = this.loadClass(beanClassName, this.classLoader);
         }

         methodOnClass = this.getDeclaredMethod(aroundInvokeClass, aroundTimeoutBean.getMethodName(), new Class[]{InvocationContext.class});
         jsr250Metadata.registerSelfTimeoutInterceptorMethod(methodOnClass);
      }

   }

   private InterceptorMetadataI createInterceptorMetadata(InterceptorBean interceptorBean, Method matchingMethod) {
      Class interceptorClass = this.loadClass(interceptorBean.getInterceptorClass(), this.classLoader);
      List aroundInvokeMethods = new ArrayList();
      AroundInvokeBean[] var5 = interceptorBean.getAroundInvokes();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         AroundInvokeBean aroundInvokeBean = var5[var7];
         Class aroundInvokeClass = null;
         if (interceptorClass.getName().equals(aroundInvokeBean.getClassName())) {
            aroundInvokeClass = interceptorClass;
         } else {
            aroundInvokeClass = this.loadClass(aroundInvokeBean.getClassName(), this.classLoader);
         }

         aroundInvokeMethods.add(this.getDeclaredMethod(aroundInvokeClass, aroundInvokeBean.getMethodName(), new Class[]{InvocationContext.class}));
      }

      List aroundTimeoutMethods = new ArrayList();
      AroundTimeoutBean[] var12 = interceptorBean.getAroundTimeouts();
      var7 = var12.length;

      for(int var14 = 0; var14 < var7; ++var14) {
         AroundTimeoutBean aroundTimeoutBean = var12[var14];
         Class aroundTimeoutClass = null;
         if (interceptorClass.getName().equals(aroundTimeoutBean.getClassName())) {
            aroundTimeoutClass = interceptorClass;
         } else {
            aroundTimeoutClass = this.loadClass(aroundTimeoutBean.getClassName(), this.classLoader);
         }

         aroundTimeoutMethods.add(this.getDeclaredMethod(aroundTimeoutClass, aroundTimeoutBean.getMethodName(), new Class[]{InvocationContext.class}));
      }

      InterceptorMetadataI im = this.pitchforkContext.getPitchforkUtils().createInterceptorMetadata(interceptorClass, aroundInvokeMethods, aroundTimeoutMethods, matchingMethod);
      this.buildInjectionMetadata(im, interceptorBean);
      this.addLifecycleMethods(im, interceptorBean);
      return im;
   }

   protected Method getMethod(Class clazz, String name, Class... paramTypes) {
      try {
         return clazz.getDeclaredMethod(name, paramTypes);
      } catch (NoSuchMethodException var5) {
         if (clazz.isInterface()) {
            return null;
         } else if (!Object.class.equals(clazz.getSuperclass())) {
            return this.getMethod(clazz.getSuperclass(), name, paramTypes);
         } else {
            throw new MetadataParseException("Cannot get the method " + name + " on class " + clazz.getName(), var5);
         }
      } catch (Exception var6) {
         throw new MetadataParseException("Cannot get the method " + name + " on class " + clazz.getName(), var6);
      }
   }

   protected void debug(String s) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[ManagedBeanContributor] " + s);
      }

   }
}
