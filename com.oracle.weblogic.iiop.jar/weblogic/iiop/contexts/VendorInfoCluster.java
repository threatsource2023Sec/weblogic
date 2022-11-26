package weblogic.iiop.contexts;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.omg.CORBA.MARSHAL;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.ReplicaState;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.rjvm.JVMID;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.cluster.Version;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;
import weblogic.utils.Debug;
import weblogic.utils.LocatorUtilities;

public final class VendorInfoCluster extends ServiceContext implements ReplicaList, Externalizable {
   private static final long serialVersionUID = -5343036220579753659L;
   private static IORToReferenceConverter converter = (IORToReferenceConverter)LocatorUtilities.getService(IORToReferenceConverter.class);
   private ArrayList replicas;
   private ReplicaList replicaList;
   private Version version;
   private Map hostToReplicaMap;
   private RemoteReference localRef;
   private ClusterComponent clustInfo;
   private ReplicaState replicaState;

   public VendorInfoCluster() {
      super(1111834883);
      this.replicas = new ArrayList();
      this.replicaState = new ReplicaState();
      this.replicaList = this;
   }

   public VendorInfoCluster(Version version) {
      this();
      this.version = version;
   }

   public VendorInfoCluster(RemoteReference primary) {
      this();
      this.version = new Version(0L);
      this.add(primary);
   }

   public static VendorInfoCluster createResponse(ReplicaList replicaList) {
      return new VendorInfoCluster(replicaList);
   }

   private VendorInfoCluster(ReplicaList replicaList) {
      this();
      this.replicaList = replicaList;
      this.version = replicaList.version();
      this.setReplicaID(replicaList.getReplicaID());
      this.setReplicaVersion(replicaList.getReplicaVersion());
   }

   public static VendorInfoCluster createFromClusterComponent(ClusterComponent clusterComponent) {
      Iterator it = clusterComponent.getIORs().iterator();
      VendorInfoCluster vic = new VendorInfoCluster(converter.toRemoteReference((IOR)it.next()));

      while(it.hasNext()) {
         vic.add(converter.toRemoteReference((IOR)it.next()));
      }

      vic.setClusterInfo(clusterComponent);
      Version version = clusterComponent.getVersion();
      if (version != null) {
         vic.setVersion(version);
      }

      return vic;
   }

   public ReplicaID getReplicaID() {
      return this.replicaState.getReplicaID();
   }

   public void setReplicaID(ReplicaID id) {
      this.replicaState.setReplicaID(id);
   }

   public boolean isReplicaVersionChanged() {
      return this.replicaState.isReplicaVersionChanged();
   }

   public ReplicaVersion getReplicaVersion() {
      return this.replicaState.getReplicaVersion();
   }

   public void setReplicaVersion(ReplicaVersion rv) {
      this.replicaState.setReplicaVersion(rv);
   }

   public void setClusterInfo(ClusterComponent cc) {
      this.clustInfo = new ClusterComponent(cc);
      this.setReplicaID(cc.getReplicaID());
      this.setReplicaVersion(cc.getReplicaVersion());
   }

   void setVersion(Version version) {
      this.version = version;
   }

   public ClusterComponent getClusterInfo() {
      if (this.clustInfo != null) {
         this.clustInfo.setIORs(this.getIors());
      }

      return this.clustInfo;
   }

   public ArrayList getIors() {
      ArrayList iors = new ArrayList(this.replicas.size());
      Iterator var2 = this.replicas.iterator();

      while(var2.hasNext()) {
         RemoteReference replica = (RemoteReference)var2.next();
         iors.add(converter.toIOR(replica));
      }

      return iors;
   }

   protected VendorInfoCluster(CorbaInputStream in) {
      super(1111834883);
      this.replicas = new ArrayList();
      this.replicaState = new ReplicaState();
      this.readEncapsulatedContext(in);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in, long handle) {
      this.version = new Version(in.read_longlong());
      this.readIors(in);
      if (in.bytesLeft(handle) > 0) {
         this.replicaState.read(in);
      }

   }

   private void readIors(CorbaInputStream in) {
      int nreplicas = in.read_long();
      if (nreplicas > 0) {
         this.replicas = new ArrayList(nreplicas);

         for(int i = 0; i < nreplicas; ++i) {
            IOR ior = new IOR(in);
            this.replicas.add(converter.toRemoteReference(ior));
         }
      }

   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_longlong(this.version.getVersion());
      this.writeIors(out);
      this.replicaState.write(out);
   }

   private void writeIors(CorbaOutputStream out) {
      List iorList = this.getIors(this.replicaList);
      out.write_long(iorList.size());
      Iterator var3 = iorList.iterator();

      while(var3.hasNext()) {
         IOR ior = (IOR)var3.next();
         ior.write(out);
      }

   }

   private List getIors(ReplicaList replicaList) {
      List iorList = new ArrayList();

      for(int i = 0; i < replicaList.size(); ++i) {
         this.addReplacement(iorList, replicaList.get(i));
      }

      return iorList;
   }

   private void addReplacement(List iorList, RemoteReference reference) {
      try {
         iorList.add(converter.getReplacementIor(reference, this.replicaList));
      } catch (IOException | MARSHAL var4) {
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      CorbaOutputStream ios = IiopProtocolFacade.createOutputStream();
      long handle = ios.startEncapsulation();
      this.writeEncapsulation(ios);
      ios.endEncapsulation(handle);
      byte[] buf = ios.getBuffer();
      ios.close();
      out.writeInt(buf.length);
      out.write(buf);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int len = in.readInt();
      byte[] buf = new byte[len];
      in.read(buf);
      CorbaInputStream ins = IiopProtocolFacade.createInputStream(buf);
      long handle = ins.startEncapsulation();
      this.readEncapsulation(ins, handle);
      ins.endEncapsulation(handle);
      ins.close();
   }

   public String toString() {
      return "VendorInfoCluster: version: " + Long.toHexString(this.version.getVersion()) + " " + (this.replicas == null ? 0 : this.replicas.size()) + " IORs";
   }

   public int size() {
      return this.replicas == null ? 0 : this.replicas.size();
   }

   public Version version() {
      return this.version;
   }

   public void add(RemoteReference replica) {
      if (replica.getHostID().equals(JVMID.localID())) {
         this.localRef = replica;
      }

      synchronized(this) {
         this.replicas.add(replica);
         if (replica.getHostID() instanceof JVMID) {
            this.version.addServer(replica.getHostID());
         }

      }
   }

   public RemoteReference get(int idx) {
      Debug.assertion(this.replicas != null && this.replicas.size() > 0);
      return (RemoteReference)this.replicas.get(idx);
   }

   public RemoteReference getPrimary() {
      return this.get(0);
   }

   public void clear() {
      this.replicas.clear();
      this.version = new Version(0L);
      this.clearHostToReplicaMap();
   }

   public void remove(RemoteReference ref) {
      Debug.assertion(ref != null);
      synchronized(this) {
         this.replicas.remove(ref);
         if (this.hostToReplicaMap != null) {
            HostID replicaHost = ref.getHostID();
            this.hostToReplicaMap.remove(replicaHost);
         }

         if (ref.getHostID() instanceof JVMID) {
            this.version.removeServer(ref.getHostID());
         }

      }
   }

   public RemoteReference removeOne(HostID id) {
      int replicasSize = true;
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
         int replicasSize = this.replicas.size();
         if (this.hostToReplicaMap != null) {
            this.hostToReplicaMap.remove(id);
         }

         this.version.removeServer(replica.getHostID());
         return replica;
      }
   }

   public Iterator iterator() {
      return this.replicas.iterator();
   }

   public RemoteReference[] toArray() {
      return (RemoteReference[])this.replicas.toArray(new RemoteReference[this.replicas.size()]);
   }

   public RemoteReference findReplicaHostedBy(HostID hostID) {
      RemoteReference replica = null;
      synchronized(this) {
         if (this.hostToReplicaMap != null) {
            replica = (RemoteReference)this.hostToReplicaMap.get(hostID);
         }

         if (replica == null) {
            for(Iterator var4 = this.replicas.iterator(); var4.hasNext(); replica = null) {
               RemoteReference replica1 = (RemoteReference)var4.next();
               replica = replica1;
               if (hostID.equals(replica1.getHostID())) {
                  if (this.hostToReplicaMap == null) {
                     this.hostToReplicaMap = new HashMap(5);
                  }

                  this.hostToReplicaMap.put(hostID, replica1);
                  break;
               }
            }
         }
      }

      if (replica != null) {
         Debug.assertion(replica.getHostID().equals(hostID), "host ID of new replica (" + replica.getHostID() + ") must equal " + hostID);
      }

      return replica;
   }

   public void reset(ReplicaList newList) {
      if (newList instanceof VendorInfoCluster) {
         VendorInfoCluster list = (VendorInfoCluster)newList;
         if (list.replicas != null && list.size() != 0) {
            synchronized(this) {
               Collections.shuffle(list.replicas);
               this.replicas = list.replicas;
               this.version = list.version;
               this.replicaState.copy(list.replicaState);
               this.clearHostToReplicaMap();
            }
         } else {
            throw new AssertionError("reset() called with null ReplicaList");
         }
      } else {
         throw new AssertionError("reset() called with foreign ReplicaList");
      }
   }

   public void resetWithoutShuffle(ReplicaList newList) {
      if (newList instanceof VendorInfoCluster) {
         VendorInfoCluster list = (VendorInfoCluster)newList;
         if (list.replicas != null && list.size() != 0) {
            synchronized(this) {
               this.replicas = list.replicas;
               this.version = list.version;
               this.clearHostToReplicaMap();
            }
         } else {
            throw new AssertionError("reset() called with null ReplicaList");
         }
      } else {
         throw new AssertionError("reset() called with foreign ReplicaList");
      }
   }

   private void clearHostToReplicaMap() {
      if (this.hostToReplicaMap != null) {
         this.hostToReplicaMap.clear();
      }

   }

   public Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   public ReplicaList getListWithRefHostedBy(HostID hostID) {
      return new VendorInfoCluster(this.localRef);
   }

   protected static void p(String s) {
      System.err.println("<VendorInfoCluster> " + s);
   }
}
