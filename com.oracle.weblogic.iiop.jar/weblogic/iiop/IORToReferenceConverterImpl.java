package weblogic.iiop;

import java.io.IOException;
import java.rmi.RemoteException;
import org.jvnet.hk2.annotations.Service;
import weblogic.iiop.contexts.IORToReferenceConverter;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.rjvm.JVMID;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.StubInfo;

@Service
public class IORToReferenceConverterImpl implements IORToReferenceConverter {
   public RemoteReference toRemoteReference(IOR ior) {
      return new IIOPRemoteRef(ior);
   }

   public IOR toIOR(RemoteReference replica) {
      return ((IIOPRemoteRef)replica).getIOR();
   }

   public IOR getReplacementIor(RemoteReference ref, ReplicaList replicaList) throws IOException {
      Object replaced = IIOPReplacer.getIorReplacer().replaceObject(ref);
      return replaced instanceof IOR ? (IOR)replaced : createIorForLocalReplica((RemoteReference)replaced, replicaList);
   }

   private static IOR createIorForLocalReplica(RemoteReference reference, ReplicaList replicaList) throws RemoteException {
      RemoteReference localReplica = replicaList.findReplicaHostedBy(JVMID.localID());
      ServerReference sref = OIDManager.getInstance().getServerReference(localReplica.getObjectID());
      StubInfo info = (StubInfo)sref.getStubReference();
      String typeid = Utils.getRepositoryID(info);
      return ReplicaIorFactory.createIor(reference, typeid, sref.getApplicationName(), (ClusterComponent)null, sref.getDescriptor(), (Object)null);
   }
}
