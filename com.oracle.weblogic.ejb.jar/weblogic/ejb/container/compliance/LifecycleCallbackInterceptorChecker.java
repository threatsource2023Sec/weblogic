package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.utils.ErrorCollectionException;

public final class LifecycleCallbackInterceptorChecker extends BaseComplianceChecker {
   private final EjbJarBean ejbJarBean;
   private final ClassLoader classLoader;

   LifecycleCallbackInterceptorChecker(DeploymentInfo di) {
      this.ejbJarBean = di.getEjbDescriptorBean().getEjbJarBean();
      this.classLoader = Thread.currentThread().getContextClassLoader();
   }

   public void checkOnlyOneLifecycleCallbackMethodperLEperClass() throws ComplianceException {
      SessionBeanBean[] var1 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         SessionBeanBean sbb = var1[var3];
         this.validateNotTwoSameTypeLifecycleCallbackPerClass(sbb.getPostConstructs(), "PostConstruct");
         this.validateNotTwoSameTypeLifecycleCallbackPerClass(sbb.getPreDestroys(), "PreDestroy");
         if ("Stateful".equalsIgnoreCase(sbb.getSessionType())) {
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(sbb.getPostActivates(), "PostActivate");
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(sbb.getPrePassivates(), "PrePassivate");
         }
      }

      MessageDrivenBeanBean[] var5 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
         MessageDrivenBeanBean mdbb = var5[var3];
         this.validateNotTwoSameTypeLifecycleCallbackPerClass(mdbb.getPostConstructs(), "PostConstruct");
         this.validateNotTwoSameTypeLifecycleCallbackPerClass(mdbb.getPreDestroys(), "PreDestroy");
      }

      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var6 = this.ejbJarBean.getInterceptors().getInterceptors();
         var2 = var6.length;

         for(var3 = 0; var3 < var2; ++var3) {
            InterceptorBean ib = var6[var3];
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(ib.getAroundConstructs(), "AroundConstruct");
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(ib.getPostConstructs(), "PostConstruct");
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(ib.getPreDestroys(), "PreDestroy");
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(ib.getPostActivates(), "PostActivate");
            this.validateNotTwoSameTypeLifecycleCallbackPerClass(ib.getPrePassivates(), "PrePassivate");
         }
      }

   }

   public static void validateLifeCycleTxAttributes(SessionBeanInfo sbi) throws ComplianceException {
      if (!sbi.isStateless() && !sbi.getAllCallbackMethodInfos().isEmpty()) {
         Set methods = new HashSet();
         Iterator var2 = sbi.getAllCallbackMethodInfos().iterator();

         while(var2.hasNext()) {
            MethodInfo mi = (MethodInfo)var2.next();
            if (-1 != mi.getTransactionAttribute() && 0 != mi.getTransactionAttribute() && 3 != mi.getTransactionAttribute() && 1 != mi.getTransactionAttribute()) {
               methods.add(mi.getMethodName());
            }
         }

         if (!methods.isEmpty()) {
            EJBComplianceTextFormatter fmt = EJBComplianceTextFormatter.getInstance();
            if (sbi.isStateful()) {
               throw new ComplianceException(fmt.LIFECYCLE_TXATTRIBUTE_INCORRECT_SFSB(methods.toString(), sbi.getBeanClass().getName()));
            }

            if (sbi.isSingleton()) {
               throw new ComplianceException(fmt.LIFECYCLE_TXATTRIBUTE_INCORRECT_SINGLETON(methods.toString(), sbi.getBeanClass().getName()));
            }
         }

      }
   }

   public void checkLifecycleCallbackMethods() throws ErrorCollectionException, ComplianceException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getAllLifecycleCallbackMethodsFromBean().iterator();

      Method lcm;
      while(var2.hasNext()) {
         lcm = (Method)var2.next();

         try {
            this.validateLifecycleCallbackMethod(lcm, true);
         } catch (ErrorCollectionException var6) {
            errors.add(var6);
         }
      }

      var2 = this.getAllLifecycleCallbackMethodsFromInterceptor().iterator();

      while(var2.hasNext()) {
         lcm = (Method)var2.next();

         try {
            this.validateLifecycleCallbackMethod(lcm, false);
         } catch (ErrorCollectionException var5) {
            errors.add(var5);
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateLifecycleCallbackMethod(Method lifecycleCallbackMethod, boolean inBean) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      int mod = lifecycleCallbackMethod.getModifiers();
      if (Modifier.isFinal(mod)) {
         errors.add(new ComplianceException(this.getFmt().LIFECYCLE_INTERCEPTOR_METHOD_NOT_BE_FINAL(lifecycleCallbackMethod.getName(), lifecycleCallbackMethod.getDeclaringClass().getName())));
      }

      if (Modifier.isStatic(mod)) {
         errors.add(new ComplianceException(this.getFmt().LIFECYCLE_INTERCEPTOR_METHOD_NOT_BE_STATIC(lifecycleCallbackMethod.getName(), lifecycleCallbackMethod.getDeclaringClass().getName())));
      }

      if (!lifecycleCallbackMethod.getReturnType().equals(Void.TYPE)) {
         if (inBean) {
            errors.add(new ComplianceException(this.getFmt().LIFECYCLE_INTERCEPTOR_METHOD_WITH_INVALID_SIGNATURE(lifecycleCallbackMethod.getName(), lifecycleCallbackMethod.getDeclaringClass().getName(), "void <METHOD>()")));
         } else if (!lifecycleCallbackMethod.getReturnType().equals(Object.class)) {
            errors.add(new ComplianceException(this.getFmt().LIFECYCLE_INTERCEPTOR_METHOD_WITH_INVALID_SIGNATURE(lifecycleCallbackMethod.getName(), lifecycleCallbackMethod.getDeclaringClass().getName(), "void <METHOD>(InvocationContext) or Object <METHOD>(InvocationContext) throws Exception")));
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateNotTwoSameTypeLifecycleCallbackPerClass(LifecycleCallbackBean[] lifecycleCallbackBeans, String lifecycleCallbackType) throws ComplianceException {
      if (lifecycleCallbackBeans != null && lifecycleCallbackBeans.length > 1) {
         for(int i = 0; i < lifecycleCallbackBeans.length; ++i) {
            String className = lifecycleCallbackBeans[i].getLifecycleCallbackClass();

            for(int j = i + 1; j < lifecycleCallbackBeans.length; ++j) {
               if (className.equals(lifecycleCallbackBeans[j].getLifecycleCallbackClass())) {
                  throw new ComplianceException(this.getFmt().TWO_LIFECYCLE_INTERCEPTOR_METHOD_IN_BEAN(lifecycleCallbackType.toString(), className.toString()));
               }
            }
         }
      }

   }

   private Set getAllLifecycleCallbackMethodsFromBean() throws ComplianceException {
      Set methods = new HashSet();
      SessionBeanBean[] var2 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         SessionBeanBean sbb = var2[var4];
         methods.addAll(InterceptorHelper.getPostConstructCallbackMethodinBean(this.classLoader, sbb));
         methods.addAll(InterceptorHelper.getPreDestroyCallbackMethodinBean(this.classLoader, sbb));
         if ("Stateful".equalsIgnoreCase(sbb.getSessionType())) {
            methods.addAll(InterceptorHelper.getPostActivateCallbackMethodinBean(this.classLoader, sbb));
            methods.addAll(InterceptorHelper.getPrePassivateCallbackMethodinBean(this.classLoader, sbb));
         }
      }

      MessageDrivenBeanBean[] var6 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MessageDrivenBeanBean mdbb = var6[var4];
         methods.addAll(InterceptorHelper.getPostConstructCallbackMethodinBean(this.classLoader, mdbb));
         methods.addAll(InterceptorHelper.getPreDestroyCallbackMethodinBean(this.classLoader, mdbb));
      }

      return methods;
   }

   private Set getAllLifecycleCallbackMethodsFromInterceptor() throws ComplianceException {
      Set methods = new HashSet();
      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var2 = this.ejbJarBean.getInterceptors().getInterceptors();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            InterceptorBean ib = var2[var4];
            methods.addAll(InterceptorHelper.getLifecycleCallbackMethodinInterceptor(this.classLoader, ib));
         }
      }

      return methods;
   }
}
