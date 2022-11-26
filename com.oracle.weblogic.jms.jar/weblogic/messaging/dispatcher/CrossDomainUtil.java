package weblogic.messaging.dispatcher;

public interface CrossDomainUtil {
   boolean isRemoteDomain(DispatcherWrapper var1);

   boolean isSameDomain(Dispatcher var1, Dispatcher var2);

   boolean isSameDomain(Dispatcher var1, DispatcherWrapper var2);
}
