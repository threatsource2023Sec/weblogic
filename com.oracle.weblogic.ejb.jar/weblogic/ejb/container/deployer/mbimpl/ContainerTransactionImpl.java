package weblogic.ejb.container.deployer.mbimpl;

import java.util.ArrayList;
import java.util.Collection;
import weblogic.ejb.container.interfaces.ContainerTransaction;
import weblogic.j2ee.descriptor.ContainerTransactionBean;
import weblogic.j2ee.descriptor.MethodBean;

public final class ContainerTransactionImpl implements ContainerTransaction {
   private final MethodBean[] methodBeans;
   private final String transactionAttribute;

   public ContainerTransactionImpl(ContainerTransactionBean mb) {
      this.methodBeans = mb.getMethods();
      this.transactionAttribute = mb.getTransAttribute();
   }

   public Collection getAllMethodDescriptors() {
      Collection result = new ArrayList();
      MethodBean[] var2 = this.methodBeans;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodBean mb = var2[var4];
         result.add(new MethodDescriptorImpl(mb));
      }

      return result;
   }

   public String getTransactionAttribute() {
      return this.transactionAttribute;
   }
}
