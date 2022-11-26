package weblogic.corba.j2ee.naming;

import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import weblogic.iiop.IIOPRemoteRef;
import weblogic.iiop.Utils;
import weblogic.rmi.cluster.RemoteReplicaService;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaInfo;
import weblogic.rmi.cluster.ReplicaVersion;

public class RemoteReplicaClientImpl implements RemoteReplicaClient {
   public IIOPRemoteRef lookupNewReplica(String url, ReplicaID replicaID, ReplicaVersion replicaVersion, String partitionName) {
      Hashtable env = new Hashtable();
      env.put("java.naming.provider.url", url);

      try {
         Context context = new InitialContext(env);
         RemoteReplicaService service = (RemoteReplicaService)PortableRemoteObject.narrow(context.lookup("weblogic.cluster.RemoteReplicaService"), RemoteReplicaService.class);
         ReplicaInfo replicaInfo = service.findReplica(replicaID, replicaVersion, partitionName);
         return (IIOPRemoteRef)replicaInfo.getRemoteRef();
      } catch (NamingException var9) {
         throw new RuntimeException("Fix this", var9);
      } catch (RemoteException var10) {
         throw Utils.mapRemoteToCORBAException(var10);
      }
   }
}
