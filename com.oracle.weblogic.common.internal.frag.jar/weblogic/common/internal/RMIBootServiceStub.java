package weblogic.common.internal;

import java.util.Map;
import weblogic.rjvm.RJVM;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.ClientRuntimeDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.security.acl.SecurityService;

public final class RMIBootServiceStub {
   private static final String STUB_NAME = ServerHelper.getStubClassName("weblogic.common.internal.RMIBootServiceImpl");
   private static final String[] bootServiceInterfaces = new String[]{SecurityService.class.getName(), StubInfoIntf.class.getName()};
   private static final ClientMethodDescriptor desc = new ClientMethodDescriptor("*", false, false, false, false, 0);
   private static final ClientRuntimeDescriptor bootServiceDescriptor;
   private static final int DEFAULT_OVERRIDE_TIMEOUT = -1;

   public static SecurityService getStub(RJVM hostVM, String channel) {
      return getStub(hostVM, channel, -1, "DOMAIN", (String)null);
   }

   public static SecurityService getStub(RJVM hostVM, String channel, String partitionName, String partitionUrl) {
      return getStub(hostVM, channel, -1, partitionName, partitionUrl);
   }

   public static SecurityService getStub(RJVM hostVM, String channel, int timeout, String partitionName, String partitionUrl) {
      BasicRemoteRef basicRef = new BasicRemoteRef(27, hostVM.getID(), channel);
      StubInfo info = new StubInfo(basicRef, bootServiceDescriptor, STUB_NAME, (String)null, partitionUrl, partitionName);
      if (timeout != -1) {
         info.setJndiSpecifiedTimeout(timeout);
      }

      return (SecurityService)StubFactory.getStub(info);
   }

   static {
      bootServiceDescriptor = new ClientRuntimeDescriptor(bootServiceInterfaces, (String)null, (Map)null, desc, STUB_NAME);
   }
}
