package weblogic.rmi.internal;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.WriteAbortedException;
import java.rmi.RemoteException;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.server.Collectable;
import weblogic.rmi.extensions.server.CollocatedRemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.OutboundRequest;

final class CollocatedRemoteRef extends BasicRemoteRef implements CollocatedRemoteReference {
   private static final long serialVersionUID = 5777536927492434313L;
   private final ServerReference sor;

   public CollocatedRemoteRef(ServerReference sor) {
      super(sor.getObjectID(), LocalServerIdentity.getIdentity());
      this.sor = sor;
      if (sor instanceof Collectable) {
         ((Collectable)sor).incrementRefCount();
      }

   }

   public ServerReference getServerReference() {
      return this.sor;
   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, int contextID, Object context, String partitionURL) throws IOException {
      OIDManager.getInstance().getServerReference(this.getObjectID());
      ServerRequest request = new ServerRequest(this.sor, md, partitionURL);
      if (md.hasAsyncResponse()) {
         request.setContext(contextID, context);
      }

      return request;
   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionName, String partitionURL) throws IOException {
      return this.getOutboundRequest(md, partitionURL);
   }

   /** @deprecated */
   @Deprecated
   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionURL) throws IOException {
      OIDManager.getInstance().getServerReference(this.getObjectID());
      return new ServerRequest(this.sor, md, partitionURL);
   }

   public Object writeReplace() throws ObjectStreamException {
      try {
         OIDManager.getInstance().ensureExported(this.sor);
         return this.sor.getRemoteRef();
      } catch (RemoteException var2) {
         throw new WriteAbortedException("Unexpected exception", var2);
      }
   }
}
