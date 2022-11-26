package weblogic.messaging.dispatcher;

import weblogic.jms.JMSEnvironment;
import weblogic.jms.dispatcher.DispatcherPartitionContext;

public class CrossDomainUtilCommon {
   public boolean isSameDomain(Dispatcher dispatcher1, Dispatcher dispatcher2) {
      if (dispatcher1 instanceof DispatcherImpl && dispatcher2 instanceof DispatcherImpl) {
         return true;
      } else {
         if (dispatcher1 instanceof DispatcherWrapperState && dispatcher2 instanceof DispatcherWrapperState) {
            DispatcherWrapperState state1 = (DispatcherWrapperState)dispatcher1;
            DispatcherWrapperState state2 = (DispatcherWrapperState)dispatcher2;
            if (!(state1.getRemoteDelegate() instanceof DispatcherProxy) || !(state2.getRemoteDelegate() instanceof DispatcherProxy)) {
               return true;
            }

            DispatcherProxy remote1 = (DispatcherProxy)state1.getRemoteDelegate();
            DispatcherProxy remote2 = (DispatcherProxy)state2.getRemoteDelegate();
            String domain1 = remote1.getRJVM().getID().getDomainName();
            String domain2 = remote2.getRJVM().getID().getDomainName();
            if (domain1 != null && domain2 != null && domain1.equals(domain2)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isSameDomain(Dispatcher dispatcher, DispatcherWrapper wrapper) {
      if (dispatcher instanceof DispatcherImpl) {
         DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().lookupDispatcherPartitionContextById((String)null);
         return dpc == null ? false : dpc.isLocal(wrapper);
      } else {
         if (dispatcher instanceof DispatcherWrapperState) {
            DispatcherWrapperState wrapperState = (DispatcherWrapperState)dispatcher;
            if (!(wrapperState.getRemoteDelegate() instanceof DispatcherProxy) || !(wrapper.getRemoteDispatcher() instanceof DispatcherProxy)) {
               return true;
            }

            DispatcherProxy remote1 = (DispatcherProxy)wrapperState.getRemoteDelegate();
            DispatcherProxy remote2 = (DispatcherProxy)wrapper.getRemoteDispatcher();
            String domain1 = remote1.getRJVM().getID().getDomainName();
            String domain2 = remote2.getRJVM().getID().getDomainName();
            if (domain1 != null && domain2 != null && domain1.equals(domain2)) {
               return true;
            }
         }

         return false;
      }
   }
}
