package weblogic.rmi.internal.activation;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.InitialReferenceConstants;
import weblogic.rmi.internal.LeasedRemoteRef;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.OutboundRequest;

public class ActivatableRemoteRef extends BasicRemoteRef implements ActivatableRemoteReference, InitialReferenceConstants, InteropWriteReplaceable {
   private Object aid = null;
   private static final long serialVersionUID = 1197977073266713182L;

   public ActivatableRemoteRef() {
   }

   public ActivatableRemoteRef(int oid, HostID hostID, Object aid) {
      super(oid, hostID);
      this.aid = aid;
   }

   protected void transferContext(OutboundRequest req) throws IOException {
      super.transferContext(req);
      req.setActivationID(this.aid);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ActivatableRemoteRef)) {
         return false;
      } else {
         ActivatableRemoteReference other = (ActivatableRemoteReference)obj;
         return this.getObjectID() == other.getObjectID() && this.getHostID().equals(other.getHostID()) && this.getActivationID().equals(other.getActivationID());
      }
   }

   public int hashCode() {
      return this.aid.hashCode();
   }

   public Object getActivationID() {
      return this.aid;
   }

   public String toString() {
      return super.toString() + ", aid: '" + this.aid + "'";
   }

   public Object interopWriteReplace(PeerInfo info) throws RemoteException {
      if (info.getMajor() == 6 && info.getMinor() == 1) {
         ActivatableServerRef serverRef = (ActivatableServerRef)OIDManager.getInstance().getServerReference(this.getObjectID());
         Activator activator = serverRef.getActivator();
         Object impl = activator.activate(this.aid);
         ServerReference ref = null;

         try {
            ref = ServerHelper.getServerReference((Remote)impl);
         } catch (NoSuchObjectException var7) {
            ref = new BasicServerRef(impl, 3);
         }

         if (ref == null) {
            ref = new BasicServerRef(impl, 3);
         }

         OIDManager.getInstance().ensureExported((ServerReference)ref);
         return new LeasedRemoteRef(((ServerReference)ref).getObjectID(), LocalServerIdentity.getIdentity());
      } else {
         return this;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.aid);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.aid = in.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      if (!this.getHostID().isLocal()) {
         return this;
      } else {
         try {
            ActivatableServerRef serverRef = (ActivatableServerRef)OIDManager.getInstance().getServerReference(this.getObjectID());
            return new ActivatableCollocatedRef(serverRef, this.getActivationID());
         } catch (NoSuchObjectException var2) {
            throw new AssertionError(var2);
         }
      }
   }
}
