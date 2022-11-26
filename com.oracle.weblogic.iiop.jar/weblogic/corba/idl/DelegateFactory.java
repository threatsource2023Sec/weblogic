package weblogic.corba.idl;

import org.omg.CORBA.portable.Delegate;
import weblogic.corba.j2ee.naming.RemoteReplicaClient;
import weblogic.corba.j2ee.naming.RemoteReplicaClientImpl;
import weblogic.iiop.IiopConfigurationFacade;
import weblogic.iiop.ior.IOR;

public final class DelegateFactory {
   private static RemoteReplicaClient replicaClient = new RemoteReplicaClientImpl();

   public static Delegate createDelegate(IOR ior) {
      if (!IiopConfigurationFacade.isLocal(ior)) {
         return new RemoteDelegateImpl(ior, replicaClient);
      } else {
         return (Delegate)(IiopConfigurationFacade.getObjectId(ior) == 8 ? new NamingServiceInitialReferenceDelegateImpl(ior) : new DelegateImpl(ior));
      }
   }
}
