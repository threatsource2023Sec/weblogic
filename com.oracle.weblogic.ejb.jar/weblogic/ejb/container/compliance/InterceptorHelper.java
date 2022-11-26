package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Timer;
import javax.interceptor.InvocationContext;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.MethodParamsBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.SessionBeanBean;

public final class InterceptorHelper {
   public static Set getAroundInvokeMethodInBean(ClassLoader cl, SessionBeanBean sbb) throws ComplianceException {
      return sbb.getAroundInvokes() == null ? Collections.emptySet() : getAroundInvokeMethods(cl, sbb.getAroundInvokes(), sbb.getEjbClass());
   }

   public static Set getAroundInvokeMethodInBean(ClassLoader cl, MessageDrivenBeanBean mdbb) throws ComplianceException {
      return mdbb.getAroundInvokes() == null ? Collections.emptySet() : getAroundInvokeMethods(cl, mdbb.getAroundInvokes(), mdbb.getEjbClass());
   }

   public static Set getAroundTimeoutMethodInBean(ClassLoader cl, SessionBeanBean sbb) throws ComplianceException {
      return sbb.getAroundTimeouts() == null ? Collections.emptySet() : getAroundTimeoutMethods(cl, sbb.getAroundTimeouts(), sbb.getEjbClass());
   }

   public static Set getAroundTimeoutMethodInBean(ClassLoader cl, MessageDrivenBeanBean mdbb) throws ComplianceException {
      return mdbb.getAroundTimeouts() == null ? Collections.emptySet() : getAroundTimeoutMethods(cl, mdbb.getAroundTimeouts(), mdbb.getEjbClass());
   }

   private static Set getAroundInvokeMethods(ClassLoader cl, AroundInvokeBean[] aibs, String beanClassName) throws ComplianceException {
      Set methods = new HashSet();
      AroundInvokeBean[] var4 = aibs;
      int var5 = aibs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         AroundInvokeBean aib = var4[var6];
         String className = aib.getClassName() != null ? aib.getClassName() : beanClassName;
         String methodName = aib.getMethodName();

         try {
            Class clazz = loadClass(cl, className);
            methods.add(clazz.getDeclaredMethod(methodName, InvocationContext.class));
         } catch (NoSuchMethodException var11) {
            throw new ComplianceException(getFmt().AROUNDINVOKE_METHOD_WITH_INVALIDE_SIGNATURE(methodName, className));
         }
      }

      return methods;
   }

   private static Set getAroundTimeoutMethods(ClassLoader cl, AroundTimeoutBean[] atbs, String beanClassName) throws ComplianceException {
      Set methods = new HashSet();
      AroundTimeoutBean[] var4 = atbs;
      int var5 = atbs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         AroundTimeoutBean atb = var4[var6];
         String className = atb.getClassName() != null ? atb.getClassName() : beanClassName;
         String methodName = atb.getMethodName();

         try {
            Class clazz = loadClass(cl, className);
            methods.add(clazz.getDeclaredMethod(methodName, InvocationContext.class));
         } catch (NoSuchMethodException var11) {
            throw new ComplianceException(getFmt().AROUNDINVOKE_METHOD_WITH_INVALIDE_SIGNATURE(methodName, className));
         }
      }

      return methods;
   }

   public static Set getPostConstructCallbackMethodinBean(ClassLoader cl, SessionBeanBean sbb) throws ComplianceException {
      return sbb.getPostConstructs() == null ? Collections.emptySet() : getLifecycleCallbackMethodsInBean(cl, sbb.getPostConstructs(), "PostConstruct");
   }

   public static Set getPostConstructCallbackMethodinBean(ClassLoader cl, MessageDrivenBeanBean mdbb) throws ComplianceException {
      return mdbb.getPostConstructs() == null ? Collections.emptySet() : getLifecycleCallbackMethodsInBean(cl, mdbb.getPostConstructs(), "PostConstruct");
   }

   public static Set getPreDestroyCallbackMethodinBean(ClassLoader cl, SessionBeanBean sbb) throws ComplianceException {
      return sbb.getPreDestroys() == null ? Collections.emptySet() : getLifecycleCallbackMethodsInBean(cl, sbb.getPreDestroys(), "PreDestroy");
   }

   public static Set getPreDestroyCallbackMethodinBean(ClassLoader cl, MessageDrivenBeanBean mdbb) throws ComplianceException {
      return mdbb.getPreDestroys() == null ? Collections.emptySet() : getLifecycleCallbackMethodsInBean(cl, mdbb.getPreDestroys(), "PreDestroy");
   }

   public static Set getPostActivateCallbackMethodinBean(ClassLoader cl, SessionBeanBean sbb) throws ComplianceException {
      return sbb.getPostActivates() == null ? Collections.emptySet() : getLifecycleCallbackMethodsInBean(cl, sbb.getPostActivates(), "PostActivate");
   }

   public static Set getPrePassivateCallbackMethodinBean(ClassLoader cl, SessionBeanBean sbb) throws ComplianceException {
      return sbb.getPrePassivates() == null ? Collections.emptySet() : getLifecycleCallbackMethodsInBean(cl, sbb.getPrePassivates(), "PrePassivate");
   }

   public static Set getAroundInvokeMethodInInterceptor(ClassLoader cl, InterceptorBean ib) throws ComplianceException {
      return ib.getAroundInvokes() == null ? Collections.emptySet() : getAroundInvokeMethods(cl, ib.getAroundInvokes(), ib.getInterceptorClass());
   }

   public static Set getAroundTimeoutMethodInInterceptor(ClassLoader cl, InterceptorBean ib) throws ComplianceException {
      return ib.getAroundTimeouts() == null ? Collections.emptySet() : getAroundTimeoutMethods(cl, ib.getAroundTimeouts(), ib.getInterceptorClass());
   }

   public static Set getLifecycleCallbackMethodinInterceptor(ClassLoader cl, InterceptorBean ib) throws ComplianceException {
      Set methods = new HashSet();
      if (ib.getPostConstructs() != null) {
         addLifecycleCallbackMethodsInInterceptor(cl, ib.getPostConstructs(), methods, "PostConstruct");
      }

      if (ib.getPreDestroys() != null) {
         addLifecycleCallbackMethodsInInterceptor(cl, ib.getPreDestroys(), methods, "PreDestroy");
      }

      if (ib.getPostActivates() != null) {
         addLifecycleCallbackMethodsInInterceptor(cl, ib.getPostActivates(), methods, "PostActivate");
      }

      if (ib.getPrePassivates() != null) {
         addLifecycleCallbackMethodsInInterceptor(cl, ib.getPrePassivates(), methods, "PrePassivate");
      }

      if (ib.getAroundConstructs() != null) {
         addLifecycleCallbackMethodsInInterceptor(cl, ib.getAroundConstructs(), methods, "AroundConstruct");
      }

      return methods;
   }

   private static void addLifecycleCallbackMethodsInInterceptor(ClassLoader cl, LifecycleCallbackBean[] lcbs, Set methods, String methodType) throws ComplianceException {
      LifecycleCallbackBean[] var4 = lcbs;
      int var5 = lcbs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LifecycleCallbackBean lcb = var4[var6];
         String className = lcb.getLifecycleCallbackClass();
         String methodName = lcb.getLifecycleCallbackMethod();

         try {
            Class clazz = loadClass(cl, className);
            methods.add(clazz.getDeclaredMethod(methodName, InvocationContext.class));
         } catch (NoSuchMethodException var11) {
            throw new ComplianceException(getFmt().INTERCEPTOR_LIFECYCLE_METHOD_WITH_INVALIDE_SIGNATURE(methodType, methodName, className));
         }
      }

   }

   private static Set getLifecycleCallbackMethodsInBean(ClassLoader cl, LifecycleCallbackBean[] lcbs, String methodType) throws ComplianceException {
      Set methods = new HashSet();
      LifecycleCallbackBean[] var4 = lcbs;
      int var5 = lcbs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LifecycleCallbackBean lcb = var4[var6];
         String className = lcb.getLifecycleCallbackClass();
         String methodName = lcb.getLifecycleCallbackMethod();

         try {
            Class clazz = loadClass(cl, className);
            methods.add(clazz.getDeclaredMethod(methodName));
         } catch (NoSuchMethodException var11) {
            throw new ComplianceException(getFmt().BEAN_LIFECYCLE_METHOD_WITH_INVALIDE_SIGNATURE(methodType, methodName, className));
         }
      }

      return methods;
   }

   private static Method findDeclaredMethod(Class clazz, String methodName, Class[] params) {
      while(!clazz.equals(Object.class)) {
         try {
            return clazz.getDeclaredMethod(methodName, params);
         } catch (NoSuchMethodException var4) {
            clazz = clazz.getSuperclass();
         }
      }

      return null;
   }

   public static Method getTimeoutMethodFromDD(NamedMethodBean namedMethodBean, Class clazz) {
      boolean hasParamsField = false;
      boolean hasEmptyParams = false;
      String methodName = namedMethodBean.getMethodName();
      MethodParamsBean mpb = namedMethodBean.getMethodParams();
      if (mpb != null) {
         hasParamsField = true;
         String[] methodParams = mpb.getMethodParams();
         if (methodParams != null && methodParams.length <= 0) {
            hasEmptyParams = true;
         }
      }

      Method retVal;
      if (!hasParamsField) {
         retVal = findDeclaredMethod(clazz, methodName, new Class[]{Timer.class});
         if (retVal == null) {
            retVal = findDeclaredMethod(clazz, methodName, new Class[0]);
         }
      } else if (!hasEmptyParams) {
         retVal = findDeclaredMethod(clazz, methodName, new Class[]{Timer.class});
      } else {
         retVal = findDeclaredMethod(clazz, methodName, new Class[0]);
      }

      return retVal;
   }

   protected static Class loadClass(ClassLoader cl, String className) throws ComplianceException {
      try {
         return cl.loadClass(className);
      } catch (ClassNotFoundException var3) {
         throw new ComplianceException(getFmt().loadFailure(className));
      }
   }

   private static EJBComplianceTextFormatter getFmt() {
      return EJBComplianceTextFormatter.getInstance();
   }
}
