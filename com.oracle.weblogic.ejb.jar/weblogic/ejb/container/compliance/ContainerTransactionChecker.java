package weblogic.ejb.container.compliance;

import java.util.HashSet;
import java.util.Set;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.ContainerTransactionBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.MethodBean;
import weblogic.j2ee.descriptor.SessionBeanBean;

public final class ContainerTransactionChecker extends BaseComplianceChecker {
   private final DeploymentInfo di;
   private final Set ejbNamesWithValidatedCTs = new HashSet();

   public ContainerTransactionChecker(DeploymentInfo di) {
      this.di = di;
   }

   public void checkContainerTransaction() {
      EjbJarBean ejbJar = this.di.getEjbDescriptorBean().getEjbJarBean();
      AssemblyDescriptorBean ad = this.di.getEjbDescriptorBean().getEjbJarBean().getAssemblyDescriptor();
      if (null != ad) {
         ContainerTransactionBean[] var3 = ad.getContainerTransactions();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ContainerTransactionBean ctb = var3[var5];
            MethodBean[] var7 = ctb.getMethods();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               MethodBean meth = var7[var9];
               this.validateContainerTransaction(ejbJar, meth.getEjbName());
            }
         }
      }

   }

   private void validateContainerTransaction(EjbJarBean ejbJar, String ejbName) {
      if (!this.ejbNamesWithValidatedCTs.contains(ejbName)) {
         SessionBeanBean[] var3 = ejbJar.getEnterpriseBeans().getSessions();
         int var4 = var3.length;

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            SessionBeanBean sbb = var3[var5];
            if (sbb.getEjbName().equals(ejbName) && sbb.getTransactionType().equals("Bean")) {
               EJBLogger.logContainerTransactionSetForBeanManagedEJB(ejbName);
            }
         }

         MessageDrivenBeanBean[] var7 = ejbJar.getEnterpriseBeans().getMessageDrivens();
         var4 = var7.length;

         for(var5 = 0; var5 < var4; ++var5) {
            MessageDrivenBeanBean mdb = var7[var5];
            if (mdb.getEjbName().equals(ejbName) && mdb.getTransactionType().equals("Bean")) {
               EJBLogger.logContainerTransactionSetForBeanManagedEJB(ejbName);
            }
         }

         this.ejbNamesWithValidatedCTs.add(ejbName);
      }
   }
}
