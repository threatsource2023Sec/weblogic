package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolStack;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerChannelStream;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.Debug;

public class BasicReplicaList implements ReplicaList, Externalizable, Cloneable {
   private static final boolean filterReplicaOnChannel = Boolean.getBoolean("weblogic.rmi.t3.replicaList.customChannel.excludeDefaultChannels");
   private static final DebugLogger debugCluster = DebugLogger.getDebugLogger("DebugCluster");
   private static final DebugLogger debugDetailCluster = DebugLogger.getDebugLogger("DebugClusterVerbose");
   private static final long serialVersionUID = 8963841168514959418L;
   private ArrayList replicas;
   private ReplicaID replicaID;
   private ReplicaVersion replicaVersion;
   private transient Map hostToReplicaMap;
   private transient Version version;
   protected transient RemoteReference localRef;
   private static boolean enableQOSOnStub = true;

   public BasicReplicaList(RemoteReference primary) {
      this();
      this.initialize();
      if (primary != null) {
         this.add(primary);
      }

   }

   public int size() {
      synchronized(this) {
         return this.replicas.size();
      }
   }

   public Version version() {
      return this.version;
   }

   public void add(RemoteReference replica) {
      if (replica.getHostID().isLocal()) {
         this.localRef = replica;
      }

      synchronized(this) {
         if (!this.replicas.contains(replica)) {
            this.replicas.add(replica);
            this.version.addServer(replica.getHostID());
         }

      }
   }

   public RemoteReference get(int idx) {
      synchronized(this) {
         return idx >= 0 && idx < this.replicas.size() ? (RemoteReference)this.replicas.get(idx) : null;
      }
   }

   public RemoteReference getPrimary() {
      synchronized(this) {
         return this.replicas.isEmpty() ? null : (RemoteReference)this.replicas.get(0);
      }
   }

   public void clear() {
      synchronized(this) {
         this.replicas.clear();
         this.version = new Version(0L);
         this.clearHostToReplicaMap();
      }
   }

   public void remove(RemoteReference ref) {
      Debug.assertion(ref != null);
      if (ref.getHostID().isLocal()) {
         this.localRef = null;
      }

      synchronized(this) {
         this.replicas.remove(ref);
         if (this.hostToReplicaMap != null) {
            HostID replicaHost = ref.getHostID();
            this.hostToReplicaMap.remove(replicaHost);
         }

         this.version.removeServer(ref.getHostID());
         int sizeOfReplicas = this.replicas.size();
      }
   }

   public RemoteReference removeOne(HostID id) {
      if (id.isLocal()) {
         this.localRef = null;
      }

      int sizeOfReplicas = true;
      synchronized(this) {
         ListIterator it = this.replicas.listIterator();

         RemoteReference replica;
         do {
            if (!it.hasNext()) {
               return null;
            }

            replica = (RemoteReference)it.next();
         } while(!replica.getHostID().equals(id));

         it.remove();
         int sizeOfReplicas = this.replicas.size();
         if (this.hostToReplicaMap != null) {
            this.hostToReplicaMap.remove(id);
         }

         this.version.removeServer(replica.getHostID());
         return replica;
      }
   }

   protected Iterator iterator() {
      synchronized(this) {
         return this.replicas.iterator();
      }
   }

   public RemoteReference[] toArray() {
      synchronized(this) {
         return (RemoteReference[])this.replicas.toArray(new RemoteReference[this.replicas.size()]);
      }
   }

   public RemoteReference findReplicaHostedBy(HostID hostID) {
      RemoteReference replica;
      synchronized(this) {
         replica = (RemoteReference)this.getHostToReplicaMap().get(hostID);
         if (replica == null) {
            replica = this.findAndCacheReplica(hostID);
         }
      }

      if (replica != null && !replica.getHostID().equals(hostID)) {
         throw new AssertionError("host ID of new replica (" + replica.getHostID() + ") must equal " + hostID);
      } else {
         return replica;
      }
   }

   private RemoteReference findAndCacheReplica(HostID hostID) {
      Iterator var2 = this.replicas.iterator();

      RemoteReference replica;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         replica = (RemoteReference)var2.next();
      } while(!hostID.equals(replica.getHostID()));

      this.getHostToReplicaMap().put(hostID, replica);
      return replica;
   }

   private Map getHostToReplicaMap() {
      if (this.hostToReplicaMap == null) {
         this.hostToReplicaMap = new HashMap(5);
      }

      return this.hostToReplicaMap;
   }

   public void reset(ReplicaList newList) {
      this.resetInternal(newList, true);
   }

   public void resetWithoutShuffle(ReplicaList newList) {
      this.resetInternal(newList, false);
   }

   private void resetInternal(ReplicaList newList, boolean shuffle) {
      if (newList instanceof BasicReplicaList) {
         BasicReplicaList list = (BasicReplicaList)newList;
         Debug.assertion(list.size() > 0);
         ArrayList tempReplicas;
         Version tempVersion;
         synchronized(list) {
            tempReplicas = (ArrayList)list.replicas.clone();
            tempVersion = new Version(list.version.getVersion());
         }

         if (shuffle) {
            Collections.shuffle(tempReplicas);
         }

         synchronized(this) {
            this.replicas = tempReplicas;
            this.version = tempVersion;
            this.clearHostToReplicaMap();
         }
      } else {
         throw new AssertionError("reset() called with foreign ReplicaList");
      }
   }

   protected final void clearHostToReplicaMap() {
      if (this.hostToReplicaMap != null) {
         this.hostToReplicaMap.clear();
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("[");
      synchronized(this) {
         if (this.replicas.size() > 0) {
            Iterator it = this.replicas.iterator();

            while(it.hasNext()) {
               RemoteReference ref = (RemoteReference)it.next();
               buf.append(ref.getHostID());
               buf.append("/").append(ref.getObjectID());
               if (it.hasNext()) {
                  buf.append(", ");
               }
            }
         }
      }

      buf.append("]");
      return buf.toString();
   }

   public Object clone() {
      try {
         synchronized(this) {
            return super.clone();
         }
      } catch (CloneNotSupportedException var4) {
         throw (AssertionError)(new AssertionError("impossible exception")).initCause(var4);
      }
   }

   public ReplicaList getListWithRefHostedBy(HostID hostID) {
      RemoteReference ref = this.findReplicaHostedBy(hostID);
      return ref != null ? new BasicReplicaList(ref) : this;
   }

   public BasicReplicaList() {
      this.replicaID = null;
      this.replicaVersion = null;
      this.initialize();
   }

   private void initialize() {
      this.replicas = new ArrayList(1);
      this.version = new Version(0L);
   }

   public boolean isReplicaVersionChanged() {
      return this.replicaVersion != null && this.replicaVersion.hasVersionChanged();
   }

   public ReplicaVersion getReplicaVersion() {
      return this.replicaVersion;
   }

   public void setReplicaVersion(ReplicaVersion rv) {
      if (this.replicaVersion == null) {
         this.replicaVersion = rv;
      } else {
         this.replicaVersion.copy(rv);
      }
   }

   public void setReplicaID(ReplicaID id) {
      this.replicaID = id;
   }

   public ReplicaID getReplicaID() {
      return this.replicaID;
   }

   private boolean doesPeerSupportReplicaID(Object o) {
      return ReplicaVersion.doesPeerSupportReplicaVersion(o);
   }

   private boolean isPeerInfoable(Object o) {
      return o instanceof PeerInfoable;
   }

   public synchronized void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         ArrayList clone = this.replicas;
         if (filterReplicaOnChannel && RJVMEnvironment.getEnvironment().isServer() && out instanceof ServerChannelStream) {
            ServerChannel sc = ((ServerChannelStream)out).getServerChannel();
            if (sc != null && sc.getChannelName() != null && !sc.getChannelName().startsWith("Default")) {
               clone = this.filterReplicaListOnChannel(this.replicas, sc.getChannelName());
            }
         }

         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeArrayList(clone);
         wlOut.writeLong(this.version.getVersion());
      } else {
         out.writeObject(this.replaceReplicaList(this.replicas));
         out.writeLong(this.version.getVersion());
      }

      if (this.doesPeerSupportReplicaID(out) || !this.isPeerInfoable(out)) {
         out.writeObject(this.replicaID);
         out.writeObject(this.replicaVersion);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.replicaID = null;
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         if (enableQOSOnStub && wlIn instanceof ServerChannelStream) {
            Protocol protocol = ((ServerChannelStream)wlIn).getServerChannel().getProtocol();
            ProtocolStack.push(protocol);
            this.replicas = wlIn.readArrayList();
            ProtocolStack.pop();
         } else {
            this.replicas = wlIn.readArrayList();
         }

         this.version = new Version(wlIn.readLong());
      } else {
         this.replicas = (ArrayList)in.readObject();
         this.version = new Version(in.readLong());
         this.replicas = this.resolveReplicaList(this.replicas);
      }

      if (this.doesPeerSupportReplicaID(in)) {
         this.replicaID = (ReplicaID)in.readObject();
         this.replicaVersion = (ReplicaVersion)in.readObject();
      } else if (!this.isPeerInfoable(in)) {
         try {
            this.replicaID = (ReplicaID)in.readObject();
            this.replicaVersion = (ReplicaVersion)in.readObject();
         } catch (IOException var4) {
            this.replicaID = null;
            if (in instanceof WLObjectInput) {
               if (debugDetailCluster.isDebugEnabled()) {
                  debugDetailCluster.debug("BasicReplicaList.readExternal() read ReplicaID got exception: " + var4);
               }
            } else if (debugCluster.isDebugEnabled()) {
               debugCluster.debug("BasicReplicaList.readExternal() read ReplicaID got exception: " + var4);
            }
         }
      }

   }

   private ArrayList replaceReplicaList(ArrayList list) throws IOException {
      if (!KernelStatus.isServer()) {
         return list;
      } else if (list != null && !list.isEmpty()) {
         ArrayList newList = new ArrayList(list.size());
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            RemoteReference reference = (RemoteReference)var3.next();
            newList.add((RemoteReference)RemoteObjectReplacer.getReplacer().replaceObject(reference));
         }

         return newList;
      } else {
         return list;
      }
   }

   private ArrayList resolveReplicaList(ArrayList list) throws IOException {
      if (!KernelStatus.isServer()) {
         return list;
      } else if (list != null && !list.isEmpty()) {
         ArrayList newList = new ArrayList(list.size());
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            RemoteReference reference = (RemoteReference)var3.next();
            newList.add((RemoteReference)RemoteObjectReplacer.getReplacer().resolveObject(reference));
         }

         return newList;
      } else {
         return list;
      }
   }

   ArrayList filterReplicaListOnChannel(ArrayList list, String channelName) throws IOException {
      ArrayList clone = (ArrayList)list.clone();

      for(int i = 0; i < clone.size(); ++i) {
         RemoteReference r = (RemoteReference)clone.get(i);
         if (!this.hasSameChannel(r.getHostID(), channelName)) {
            clone.remove(i);
            --i;
         }
      }

      return clone;
   }

   boolean hasSameChannel(HostID hostID, String channelName) {
      return ServerChannelManager.findServerChannel(hostID, channelName) != null;
   }

   static {
      if (!KernelStatus.isApplet()) {
         String setQOSOnStub = System.getProperty("weblogic.t3.setQOSOnStub");
         if (setQOSOnStub != null) {
            enableQOSOnStub = !setQOSOnStub.equalsIgnoreCase("false");
         }
      }

   }
}
