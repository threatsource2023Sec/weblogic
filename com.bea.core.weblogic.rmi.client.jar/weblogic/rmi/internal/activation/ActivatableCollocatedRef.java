package weblogic.rmi.internal.activation;

import java.io.IOException;
import java.io.ObjectStreamException;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.CollocatedRemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.ServerRequest;
import weblogic.rmi.spi.OutboundRequest;

final class ActivatableCollocatedRef extends BasicRemoteRef implements ActivatableRemoteReference, CollocatedRemoteReference {
   private static final long serialVersionUID = 5777536927492434313L;
   private final ActivatableServerRef sor;
   private final Object aid;

   public ActivatableCollocatedRef(ActivatableServerRef sor, Object aid) {
      super(sor.getObjectID(), LocalServerIdentity.getIdentity());
      this.sor = sor;
      this.aid = aid;
   }

   public ServerReference getServerReference() {
      return this.sor;
   }

   public Object getActivationID() {
      return this.aid;
   }

   public String toString() {
      return super.toString() + " aid " + this.aid;
   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionName, String partitionURL) throws IOException {
      return this.getOutboundRequest(md, partitionURL);
   }

   /** @deprecated */
   @Deprecated
   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionURL) throws IOException {
      OIDManager.getInstance().getServerReference(this.getObjectID());
      ServerRequest request = new ServerRequest(this.sor, md, partitionURL);
      request.setActivationID(this.aid);
      return request;
   }

   public Object writeReplace() throws ObjectStreamException {
      OIDManager.getInstance().ensureExported(this.sor);
      return this.sor.getActivatableRef(this.getActivationID());
   }
}
