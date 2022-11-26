package weblogic.messaging.dispatcher;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.jms.common.JMSDebug;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.common.MessagingUtilities;
import weblogic.rmi.extensions.PortableRemoteObject;

public class DispatcherWrapper implements Externalizable, PartitionAware {
   static final long serialVersionUID = -569390197367234160L;
   private static final byte EXTVERSION = 1;
   private static final byte DISPATCHER_VERSION = 8;
   private String name;
   private DispatcherId dispatcherId;
   private PeerInfo peerInfo;
   protected DispatcherRemote dispatcherRemote;
   protected DispatcherOneWay dispatcherOneWay;
   private String partitionId;
   private String partitionName;
   private String connectionPartitionName;
   protected transient weblogic.jms.dispatcher.DispatcherImpl interopDispatcher;

   protected DispatcherWrapper(DispatcherImpl dispatcher, String partitionId, String partitionName, String connectionPartitionName) {
      this.dispatcherId = dispatcher.getId();
      this.name = dispatcher.getName();
      this.dispatcherRemote = dispatcher;
      this.dispatcherOneWay = dispatcher;
      this.peerInfo = VersionInfoFactory.getPeerInfoForWire();
      this.interopDispatcher = dispatcher.getInteropDispatcher();
      this.partitionId = partitionId;
      this.partitionName = partitionName;
      this.assignConnectionPartitionName(connectionPartitionName);
   }

   public DispatcherRemote getRemoteDispatcher() {
      return this.dispatcherRemote;
   }

   public DispatcherOneWay getOneWayDispatcher() {
      return this.dispatcherOneWay;
   }

   public PeerInfo getPeerInfo() {
      return this.peerInfo;
   }

   public final String getName() {
      return this.name;
   }

   public final DispatcherId getId() {
      return this.dispatcherId;
   }

   public final void setId(DispatcherId dispatcherId) {
      this.dispatcherId = dispatcherId;
   }

   public DispatcherWrapper() {
   }

   public void setPartitionId(String partitionId) throws IOException {
      this.partitionId = partitionId;
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void assignConnectionPartitionName(String partitionName) {
      this.connectionPartitionName = partitionName;
   }

   public String getConnectionPartitionName() {
      return this.connectionPartitionName;
   }

   protected void writeExternalInterop(ObjectOutput oo) throws IOException {
   }

   protected void readExternalInterop(ObjectInput in) throws IOException, ClassNotFoundException {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      out.writeByte(8);
      if (this.name == null) {
         out.writeBoolean(false);
      } else {
         out.writeBoolean(true);
         out.writeUTF(this.name);
      }

      if (this.dispatcherId == null) {
         out.writeBoolean(false);
      } else {
         out.writeBoolean(true);
         this.dispatcherId.writeExternal(out);
      }

      out.writeObject(this.peerInfo);
      if (out instanceof PeerInfoable) {
         PeerInfo outPeerInfo = ((PeerInfoable)out).getPeerInfo();
         if (outPeerInfo.compareTo(PeerInfo.VERSION_DIABLO) < 0) {
            this.writeExternalInterop(out);
            return;
         }
      }

      if (KernelStatus.isApplet() && out instanceof WLObjectOutput) {
         ((WLObjectOutput)out).writeObjectWL(this.dispatcherRemote);
         ((WLObjectOutput)out).writeObjectWL(this.dispatcherOneWay);
         DispatcherUtils.writeVersionedPartitionInfo(out, this);
      } else {
         ClassLoader oldcl = null;

         try {
            oldcl = Thread.currentThread().getContextClassLoader();
            if (!KernelStatus.isServer() && !KernelStatus.isApplet() && !DispatcherImpl.UseClassCL) {
               Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            }

            out.writeObject(this.dispatcherRemote);
            out.writeObject(this.dispatcherOneWay);
            DispatcherUtils.writeVersionedPartitionInfo(out, this);
         } finally {
            Thread.currentThread().setContextClassLoader(oldcl);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte vrsn = in.readByte();
      if (vrsn != 1) {
         throw MessagingUtilities.versionIOException(vrsn, 1, 1);
      } else {
         byte remoteVersion = in.readByte();
         if (remoteVersion != 8) {
            throw MessagingUtilities.versionIOException(remoteVersion, 8, 8);
         } else {
            if (in.readBoolean()) {
               this.name = in.readUTF();
            }

            if (in.readBoolean()) {
               this.dispatcherId = new DispatcherId();
               this.dispatcherId.readExternal(in);
            }

            this.peerInfo = (PeerInfo)in.readObject();
            if (this.peerInfo.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
               this.dispatcherRemote = (DispatcherRemote)PortableRemoteObject.narrow(in.readObject(), DispatcherRemote.class);
               this.dispatcherOneWay = (DispatcherOneWay)PortableRemoteObject.narrow(in.readObject(), DispatcherOneWay.class);
               DispatcherUtils.readVersionedPartitionInfo(in, this, this, this.peerInfo);
               if (this.dispatcherOneWay instanceof PartitionAwareSetter) {
                  DispatcherUtils.assignPartitionAware((PartitionAwareSetter)this.dispatcherOneWay, this.getPartitionId(), this.getPartitionName(), this.getConnectionPartitionName());
               } else if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatherUtilsVerbose.debug("IIOP and interop are not required to have PartitionAware. so a stub like DispatcherRemote_IIOP_WLStub is fine. class is " + this.dispatcherOneWay.getClass().getName());
               }

               if (this.dispatcherRemote instanceof PartitionAwareSetter) {
                  DispatcherUtils.assignPartitionAware((PartitionAwareSetter)this.dispatcherRemote, this.getPartitionId(), this.getPartitionName(), this.getConnectionPartitionName());
               } else if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatherUtilsVerbose.debug("IIOP and interop are not required to have PartitionAware. so a stub like DispatcherRemote_IIOP_WLStub is fine. class is " + this.dispatcherOneWay.getClass().getName());
               }
            } else {
               this.readExternalInterop(in);
            }

         }
      }
   }

   public void partitionAwareAssign(String id, String partName, String connName) throws IOException {
      this.setPartitionId(id);
      this.setPartitionName(partName);
      this.assignConnectionPartitionName(connName);
   }
}
