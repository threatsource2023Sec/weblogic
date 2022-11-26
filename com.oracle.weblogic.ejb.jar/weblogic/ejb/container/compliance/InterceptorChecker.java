package weblogic.ejb.container.compliance;

import java.lang.reflect.Modifier;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.utils.ErrorCollectionException;

final class InterceptorChecker extends BaseComplianceChecker {
   private final EjbJarBean ejbJarBean;
   private final BusinessMethodInterceptorChecker businessMethodInterceptorChecker;
   private final LifecycleCallbackInterceptorChecker lifecycleCallbackInterceptorChecker;
   private final ClassLoader classLoader;

   InterceptorChecker(DeploymentInfo di) {
      this.ejbJarBean = di.getEjbDescriptorBean().getEjbJarBean();
      this.businessMethodInterceptorChecker = new BusinessMethodInterceptorChecker(di);
      this.lifecycleCallbackInterceptorChecker = new LifecycleCallbackInterceptorChecker(di);
      this.classLoader = Thread.currentThread().getContextClassLoader();
   }

   public void checkInterceptorNotDeclareTwice() throws ComplianceException {
      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] interceptorBeans = this.ejbJarBean.getInterceptors().getInterceptors();

         for(int i = 0; i < interceptorBeans.length; ++i) {
            String interceptorclass = interceptorBeans[i].getInterceptorClass();

            for(int j = i + 1; j < interceptorBeans.length; ++j) {
               if (interceptorBeans[j].getInterceptorClass().equals(interceptorclass)) {
                  throw new ComplianceException(this.getFmt().INTERCEPTOR_CLASS_DECLARED_IN_DD(interceptorclass.toString()));
               }
            }
         }
      }

   }

   public void checkOnlyOneAroundInvokeMethodPerClass() throws ComplianceException {
      this.businessMethodInterceptorChecker.checkOnlyOneAroundInvokeMethodPerClass();
   }

   public void checkOnlyOneAroundTimeoutMethodPerClass() throws ComplianceException {
      this.businessMethodInterceptorChecker.checkOnlyOneAroundTimeoutMethodPerClass();
   }

   public void checkOnlyOneLifecycleCallbackMethodperLEperClass() throws ComplianceException {
      this.lifecycleCallbackInterceptorChecker.checkOnlyOneLifecycleCallbackMethodperLEperClass();
   }

   public void checkAroundInvokeMethods() throws ErrorCollectionException, ComplianceException {
      this.businessMethodInterceptorChecker.checkAroundInvokeOrTimeoutMethods();
   }

   public void checkLifecycleCallbackMethods() throws ErrorCollectionException, ComplianceException {
      this.lifecycleCallbackInterceptorChecker.checkLifecycleCallbackMethods();
   }

   public void checkDefaultConstructorInInterceptorClass() throws ComplianceException {
      if (this.ejbJarBean.getInterceptors() != null) {
         InterceptorBean[] var1 = this.ejbJarBean.getInterceptors().getInterceptors();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            InterceptorBean ib = var1[var3];
            String className = ib.getInterceptorClass();

            try {
               Class clazz = InterceptorHelper.loadClass(this.classLoader, className);
               int mod = clazz.getConstructor().getModifiers();
               if (!Modifier.isPublic(mod)) {
                  throw new ComplianceException(this.getFmt().INTERCEPTOR_CLASS_WITHOUT_NOARG_CONSTRUCTOR(className));
               }

               if (Modifier.isFinal(clazz.getModifiers())) {
                  throw new ComplianceException(this.getFmt().AROUNDINVOKE_METHOD_CANNOT_BE_FINAL(className));
               }
            } catch (NoSuchMethodException var8) {
               throw new ComplianceException(this.getFmt().INTERCEPTOR_CLASS_WITHOUT_NOARG_CONSTRUCTOR(className));
            }
         }

      }
   }
}
