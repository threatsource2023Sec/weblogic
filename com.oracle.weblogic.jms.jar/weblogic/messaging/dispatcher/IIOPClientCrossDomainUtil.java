package weblogic.messaging.dispatcher;

public final class IIOPClientCrossDomainUtil implements CrossDomainUtil {
   public boolean isRemoteDomain(DispatcherWrapper dispatcher) {
      return false;
   }

   public boolean isSameDomain(Dispatcher dispatcher1, Dispatcher dispatcher2) {
      return true;
   }

   public boolean isSameDomain(Dispatcher dispatcher, DispatcherWrapper wrapper) {
      return true;
   }
}
