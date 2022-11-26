package weblogic.iiop.contexts;

import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.iiop.ior.IOR;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.server.RemoteReference;

@Contract
public interface IORToReferenceConverter {
   RemoteReference toRemoteReference(IOR var1);

   IOR toIOR(RemoteReference var1);

   IOR getReplacementIor(RemoteReference var1, ReplicaList var2) throws IOException;
}
