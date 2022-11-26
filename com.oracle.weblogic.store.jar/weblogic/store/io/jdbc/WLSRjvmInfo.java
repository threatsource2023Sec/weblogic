package weblogic.store.io.jdbc;

import weblogic.kernel.KernelStatus;
import weblogic.rjvm.LocalRJVM;

public class WLSRjvmInfo extends RjvmInfo {
   public String getServerName() {
      return KernelStatus.isServer() ? LocalRJVM.getLocalRJVM().getServerName() : "client";
   }

   public String getAddress() {
      return KernelStatus.isServer() ? LocalRJVM.getLocalRJVM().getID().getAddress() : "client";
   }

   public String getDomainName() {
      return KernelStatus.isServer() ? LocalRJVM.getLocalRJVM().getID().getDomainName() : "client";
   }
}
