package weblogic.messaging.dispatcher;

import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;

public final class ServerCrossDomainUtil extends CrossDomainUtilCommon implements CrossDomainUtil {
   public boolean isRemoteDomain(DispatcherWrapper wrapper) {
      DispatcherRemote remote = wrapper.getRemoteDispatcher();
      return remote instanceof DispatcherProxy && RemoteDomainSecurityHelper.isRemoteDomain(((DispatcherProxy)remote).getRJVM());
   }
}
