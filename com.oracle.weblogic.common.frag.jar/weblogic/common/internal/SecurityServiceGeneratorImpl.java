package weblogic.common.internal;

import org.jvnet.hk2.annotations.Service;
import weblogic.protocol.Protocol;
import weblogic.rjvm.RJVM;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.SecurityServiceGenerator;

@Service
public class SecurityServiceGeneratorImpl implements SecurityServiceGenerator {
   public SecurityService createBootService(RJVM rjvm, Protocol protocol, String partitionName) {
      return new BootServicesStub(rjvm, protocol, partitionName);
   }

   public SecurityService createBootService(RJVM rjvm, Protocol protocol, String channelName, String partitionName, String partitionURL) {
      return new BootServicesStub(rjvm, protocol, channelName, partitionName, partitionURL);
   }

   public SecurityService createRMIBootService(RJVM hostVM, String channel, int timeout, String partitionName, String partitionURL) {
      return RMIBootServiceStub.getStub(hostVM, channel, timeout, partitionName, partitionURL);
   }
}
