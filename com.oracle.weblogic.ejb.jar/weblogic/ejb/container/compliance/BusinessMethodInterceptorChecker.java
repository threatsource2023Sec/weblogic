package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.utils.ErrorCollectionException;

public class BusinessMethodInterceptorChecker extends BaseComplianceChecker {
   private final EjbJarBean ejbJarBean;
   private final ClassLoader classLoader;

   BusinessMethodInterceptorChecker(DeploymentInfo di) {
      this.ejbJarBean = di.getEjbDescriptorBean().getEjbJarBean();
      this.classLoader = Thread.currentThread().getContextClassLoader();
   }

   public void checkOnlyOneAroundInvokeMethodPerClass() throws ComplianceException {
      SessionBeanBean[] var1 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         SessionBeanBean sbb = var1[var3];
         this.validateNotTwoAroundInvokeInOneClass(sbb.getAroundInvokes());
      }

      MessageDrivenBeanBean[] var5 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
         MessageDrivenBeanBean mdbb = var5[var3];
         this.validateNotTwoAroundInvokeInOneClass(mdbb.getAroundInvokes());
      }

      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var6 = this.ejbJarBean.getInterceptors().getInterceptors();
         var2 = var6.length;

         for(var3 = 0; var3 < var2; ++var3) {
            InterceptorBean interceptorBean = var6[var3];
            this.validateNotTwoAroundInvokeInOneClass(interceptorBean.getAroundInvokes());
         }
      }

   }

   public void checkOnlyOneAroundTimeoutMethodPerClass() throws ComplianceException {
      SessionBeanBean[] var1 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         SessionBeanBean sbb = var1[var3];
         this.validateNotTwoAroundTimeoutInOneClass(sbb.getAroundTimeouts());
      }

      MessageDrivenBeanBean[] var5 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
         MessageDrivenBeanBean mdbb = var5[var3];
         this.validateNotTwoAroundTimeoutInOneClass(mdbb.getAroundTimeouts());
      }

      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var6 = this.ejbJarBean.getInterceptors().getInterceptors();
         var2 = var6.length;

         for(var3 = 0; var3 < var2; ++var3) {
            InterceptorBean ib = var6[var3];
            this.validateNotTwoAroundTimeoutInOneClass(ib.getAroundTimeouts());
         }
      }

   }

   public void checkAroundInvokeOrTimeoutMethods() throws ErrorCollectionException, ComplianceException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getAllAroundInvokeMethods().iterator();

      Method aroundTimeoutMethod;
      while(var2.hasNext()) {
         aroundTimeoutMethod = (Method)var2.next();

         try {
            this.validateAroundInvokeOrTimeoutMethod(aroundTimeoutMethod);
         } catch (ErrorCollectionException var6) {
            errors.add(var6);
         }
      }

      var2 = this.getAllAroundTimeoutMethods().iterator();

      while(var2.hasNext()) {
         aroundTimeoutMethod = (Method)var2.next();

         try {
            this.validateAroundInvokeOrTimeoutMethod(aroundTimeoutMethod);
         } catch (ErrorCollectionException var5) {
            errors.add(var5);
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateNotTwoAroundInvokeInOneClass(AroundInvokeBean[] aroundInvokeBeans) throws ComplianceException {
      if (aroundInvokeBeans != null && aroundInvokeBeans.length > 1) {
         for(int i = 0; i < aroundInvokeBeans.length; ++i) {
            String className = aroundInvokeBeans[i].getClassName();

            for(int j = i + 1; j < aroundInvokeBeans.length; ++j) {
               if (className.equals(aroundInvokeBeans[j].getClassName())) {
                  throw new ComplianceException(this.getFmt().TWO_ARROUNDINVOKE_METHOD(className.toString()));
               }
            }
         }
      }

   }

   private void validateNotTwoAroundTimeoutInOneClass(AroundTimeoutBean[] aroundTimeoutBeans) throws ComplianceException {
      if (aroundTimeoutBeans != null && aroundTimeoutBeans.length > 1) {
         for(int i = 0; i < aroundTimeoutBeans.length; ++i) {
            String className = aroundTimeoutBeans[i].getClassName();

            for(int j = i + 1; j < aroundTimeoutBeans.length; ++j) {
               if (className.equals(aroundTimeoutBeans[j].getClassName())) {
                  throw new ComplianceException(this.getFmt().TWO_ARROUNDTIMEOUT_METHOD(className.toString()));
               }
            }
         }
      }

   }

   private void validateAroundInvokeOrTimeoutMethod(Method m) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      int mod = m.getModifiers();
      if (Modifier.isFinal(mod)) {
         errors.add(new ComplianceException(this.getFmt().ARROUNDINVOKE_METHOD_CANNOT_BE_FINAL(m.getName(), m.getDeclaringClass().getName())));
      }

      if (Modifier.isStatic(mod)) {
         errors.add(new ComplianceException(this.getFmt().ARROUNDINVOKE_METHOD_CANNOT_BE_STATIC(m.getName(), m.getDeclaringClass().getName())));
      }

      if (!m.getReturnType().equals(Object.class)) {
         errors.add(new ComplianceException(this.getFmt().ARROUNDINVOKE_METHOD_IS_INVALID(m.getName(), m.getDeclaringClass().getName())));
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private Set getAllAroundInvokeMethods() throws ComplianceException {
      Set methods = new HashSet();
      SessionBeanBean[] var2 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         SessionBeanBean sbb = var2[var4];
         methods.addAll(InterceptorHelper.getAroundInvokeMethodInBean(this.classLoader, sbb));
      }

      MessageDrivenBeanBean[] var6 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MessageDrivenBeanBean mdbb = var6[var4];
         methods.addAll(InterceptorHelper.getAroundInvokeMethodInBean(this.classLoader, mdbb));
      }

      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var7 = this.ejbJarBean.getInterceptors().getInterceptors();
         var3 = var7.length;

         for(var4 = 0; var4 < var3; ++var4) {
            InterceptorBean ib = var7[var4];
            methods.addAll(InterceptorHelper.getAroundInvokeMethodInInterceptor(this.classLoader, ib));
         }
      }

      return methods;
   }

   private Set getAllAroundTimeoutMethods() throws ComplianceException {
      Set methods = new HashSet();
      SessionBeanBean[] var2 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         SessionBeanBean sbb = var2[var4];
         methods.addAll(InterceptorHelper.getAroundTimeoutMethodInBean(this.classLoader, sbb));
      }

      MessageDrivenBeanBean[] var6 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         MessageDrivenBeanBean mdbb = var6[var4];
         methods.addAll(InterceptorHelper.getAroundTimeoutMethodInBean(this.classLoader, mdbb));
      }

      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var7 = this.ejbJarBean.getInterceptors().getInterceptors();
         var3 = var7.length;

         for(var4 = 0; var4 < var3; ++var4) {
            InterceptorBean ib = var7[var4];
            methods.addAll(InterceptorHelper.getAroundTimeoutMethodInInterceptor(this.classLoader, ib));
         }
      }

      return methods;
   }
}
