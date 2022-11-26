package weblogic.tgiop;

import java.io.IOException;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import weblogic.iiop.Connection;
import weblogic.iiop.ConnectionFactory;
import weblogic.iiop.IIOPService;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ior.IOPProfile;
import weblogic.iiop.ior.IOR;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;

@Service
@Rank(30)
public class TGIOPConnectionFactory implements ConnectionFactory {
   public boolean claimsIOR(IOR ior) {
      return KernelStatus.isServer() && IIOPService.isTGIOPEnabled() && this.isTGIOPKey(ObjectKey.getObjectKey(ior));
   }

   private boolean isTGIOPKey(ObjectKey objectKey) {
      return objectKey.isWLEKey() || this.isNonLocalWlsKey(objectKey);
   }

   private boolean isNonLocalWlsKey(ObjectKey objectKey) {
      return objectKey.isWLSKey() && !objectKey.getWLEDomainId().equals(ObjectKey.getLocalDomainID());
   }

   public Connection createConnection(IOR ior, ServerChannel serverChannel) throws IOException {
      IOPProfile profile = ior.getProfile();
      ObjectKey objectKey = ObjectKey.getObjectKey(ior);
      return new TGIOPConnection(objectKey.getWLEDomainId().toString(), profile.getHost(), profile.getPort());
   }

   public Protocol getProtocol() {
      return null;
   }
}
