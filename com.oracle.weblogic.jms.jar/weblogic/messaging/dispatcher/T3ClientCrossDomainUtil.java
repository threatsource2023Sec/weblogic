package weblogic.messaging.dispatcher;

public final class T3ClientCrossDomainUtil extends CrossDomainUtilCommon implements CrossDomainUtil {
   public boolean isRemoteDomain(DispatcherWrapper dispatcher) {
      return false;
   }
}
