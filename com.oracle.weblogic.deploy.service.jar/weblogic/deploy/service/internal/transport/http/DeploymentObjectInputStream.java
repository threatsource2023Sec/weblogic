package weblogic.deploy.service.internal.transport.http;

import java.io.IOException;
import java.io.InputStream;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.deploy.common.Debug;
import weblogic.rmi.utils.io.RemoteObjectReplacer;

public class DeploymentObjectInputStream extends WLObjectInputStream implements PeerInfoable {
   final PeerInfo peerInfo;

   public DeploymentObjectInputStream(InputStream in, String version) throws IOException {
      super(in);
      this.peerInfo = version != null && version.length() != 0 ? PeerInfo.getPeerInfo(version) : null;
      if (Debug.isServiceTransportDebugEnabled()) {
         Debug.serviceTransportDebug("PeerInfo on '" + this + "' is: " + this.peerInfo);
      }

      this.setReplacer(RemoteObjectReplacer.getReplacer());
   }

   public PeerInfo getPeerInfo() {
      return this.peerInfo;
   }
}
