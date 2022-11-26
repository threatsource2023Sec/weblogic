package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import javax.ejb.MessageDrivenBean;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;

public final class Ejb30MessageDrivenBeanClassChecker extends MessageDrivenBeanClassChecker {
   private final EjbJarBean ejbJarBean;
   private final BeanInfo bi;

   public Ejb30MessageDrivenBeanClassChecker(BeanInfo bi) {
      super(bi);
      this.bi = bi;
      this.ejbJarBean = bi.getDeploymentInfo().getEjbDescriptorBean().getEjbJarBean();
   }

   public void checkMDBInterfaceConstraints() throws ComplianceException {
      LifecycleCallbackBean[] preDestroyCallbackBeans = null;
      LifecycleCallbackBean[] postConstructCallbackBeans = null;
      MessageDrivenBeanBean[] var3 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         MessageDrivenBeanBean mdbb = var3[var5];
         if (mdbb.getEjbClass().equals(this.bi.getBeanClassName())) {
            preDestroyCallbackBeans = mdbb.getPreDestroys();
            postConstructCallbackBeans = mdbb.getPostConstructs();
            break;
         }
      }

      String className;
      try {
         Class beanClass = this.bi.getBeanClass();
         beanClass.getMethod("ejbCreate");
         if (postConstructCallbackBeans != null && postConstructCallbackBeans.length > 0) {
            LifecycleCallbackBean[] var13 = postConstructCallbackBeans;
            var5 = postConstructCallbackBeans.length;

            for(int var14 = 0; var14 < var5; ++var14) {
               LifecycleCallbackBean postConstructCallbackBean = var13[var14];
               className = postConstructCallbackBean.getLifecycleCallbackMethod();
               String className = postConstructCallbackBean.getLifecycleCallbackClass();
               if (className.equals(beanClass.getName()) && !className.equals("ejbCreate")) {
                  throw new ComplianceException(this.getFmt().MDB_POSTCONSTRUCT_NOT_APPLY_EJBCREATE(this.bi.getEJBName()));
               }
            }
         }
      } catch (NoSuchMethodException var10) {
      }

      if (MessageDrivenBean.class.isAssignableFrom(this.bi.getBeanClass()) && preDestroyCallbackBeans != null && preDestroyCallbackBeans.length > 0) {
         LifecycleCallbackBean[] var12 = preDestroyCallbackBeans;
         var4 = preDestroyCallbackBeans.length;

         for(var5 = 0; var5 < var4; ++var5) {
            LifecycleCallbackBean preDestroyCallbackBean = var12[var5];
            String methodName = preDestroyCallbackBean.getLifecycleCallbackMethod();
            className = preDestroyCallbackBean.getLifecycleCallbackClass();
            if (className.equals(this.bi.getBeanClass().getName()) && !methodName.equals("ejbRemove")) {
               throw new ComplianceException(this.getFmt().MDB_PREDESTROY_NOT_APPLY_EJBREMOVE(this.bi.getEJBName()));
            }
         }
      }

      if (this.bi.isClusteredTimers()) {
         this.checkAutocreatedClusteredTimerCount();
      }

   }

   public static void ensureBeanClassMethodsExist(String ejbName, Class intfClass, Class beanClass) throws ComplianceException {
      if (!intfClass.isAssignableFrom(beanClass)) {
         Method[] var3 = intfClass.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];

            try {
               beanClass.getMethod(m.getName(), m.getParameterTypes());
            } catch (NoSuchMethodException var8) {
               throw new ComplianceException(EJBComplianceTextFormatter.getInstance().MT_METHOD_DOESNT_EXIST_IN_BEAN(ejbName, m.getName()));
            }
         }

      }
   }

   private void checkAutocreatedClusteredTimerCount() throws ComplianceException {
      MessageDrivenBeanBean[] var1 = this.ejbJarBean.getEnterpriseBeans().getMessageDrivens();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         MessageDrivenBeanBean mdbb = var1[var3];
         if (this.bi.getBeanClassName().equals(mdbb.getEjbClass())) {
            TimeoutCheckHelper.validateAutocreatedClusteredTimerCount(mdbb.getTimers(), this.bi.getBeanClass());
         }
      }

   }
}
