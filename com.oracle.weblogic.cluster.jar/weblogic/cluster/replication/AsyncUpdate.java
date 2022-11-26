package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.common.internal.PassivationUtils;
import weblogic.rmi.spi.HostID;

public class AsyncUpdate implements Externalizable {
   private boolean update;
   private ROID id;
   private int version;
   private Serializable change = null;
   private Object key;
   private HostID primaryHost = null;
   private Serializable ro = null;
   private transient Serializable aro = null;
   private transient ResourceGroupKey resourceGroupKey;

   public AsyncUpdate(ROID id, int version, AsyncReplicatable aro, Object key, ResourceGroupKey resourceGroupKey) {
      this.id = id;
      this.version = version;
      this.key = key;
      AsyncReplicatable replicatable = aro;
      Serializable asyncReplicatedSessionChange = aro.getBatchedChanges();

      try {
         replicatable = (AsyncReplicatable)PassivationUtils.copy(aro);
         asyncReplicatedSessionChange = (Serializable)PassivationUtils.copy(aro.getBatchedChanges());
      } catch (Throwable var9) {
         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Failed to copy Replicatable object " + aro + " or changes " + aro.getBatchedChanges() + " due to exception: " + var9);
         }
      }

      this.aro = replicatable;
      this.change = new WrappedSerializable(asyncReplicatedSessionChange, resourceGroupKey);
      this.update = true;
      this.resourceGroupKey = resourceGroupKey;
   }

   public AsyncUpdate(HostID localHost, ROID id, int version, Object key, Serializable aro, ResourceGroupKey resourceGroupKey) {
      this.id = id;
      this.version = version;
      this.key = key;
      Serializable replicatable = aro;

      try {
         replicatable = (Replicatable)PassivationUtils.copy(aro);
      } catch (Throwable var9) {
         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Failed to copy Replicatable object " + aro + " due to exception: " + var9);
         }
      }

      this.aro = (Serializable)replicatable;
      this.ro = new WrappedSerializable(this.aro, resourceGroupKey);
      this.primaryHost = localHost;
      this.update = false;
      this.resourceGroupKey = resourceGroupKey;
   }

   public void recreate(HostID primaryHost, int version) {
      this.update = false;
      this.change = null;
      this.ro = new WrappedSerializable(this.aro, this.resourceGroupKey);
      this.primaryHost = primaryHost;
      this.version = version;
   }

   public void commit() {
      if (this.aro instanceof AsyncReplicatable) {
         ((AsyncReplicatable)this.aro).commit();
      }

   }

   public AsyncUpdate() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeBoolean(this.update);
      if (this.update) {
         this.writeForUpdate(out);
      } else {
         this.writeForCreate(out);
      }

   }

   public void writeForUpdate(ObjectOutput out) throws IOException {
      out.writeObject(this.id);
      out.writeInt(this.version);
      out.writeObject(this.change);
      out.writeObject(this.key);
      if (this.aro instanceof AsyncReplicatable) {
         ((AsyncReplicatable)this.aro).commit();
      }

   }

   public void writeForCreate(ObjectOutput out) throws IOException {
      out.writeObject(this.id);
      out.writeInt(this.version);
      out.writeObject(this.key);
      out.writeObject(this.primaryHost);
      out.writeObject(this.ro);
      if (this.aro instanceof AsyncReplicatable) {
         ((AsyncReplicatable)this.aro).commit();
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.update = in.readBoolean();
      if (this.update) {
         this.readForUpdate(in);
      } else {
         this.readForCreate(in);
      }

   }

   public void readForUpdate(ObjectInput in) throws IOException, ClassNotFoundException {
      this.id = (ROID)in.readObject();
      this.version = in.readInt();
      this.change = (Serializable)in.readObject();
      this.key = in.readObject();
   }

   public void readForCreate(ObjectInput in) throws IOException, ClassNotFoundException {
      this.id = (ROID)in.readObject();
      this.version = in.readInt();
      this.key = in.readObject();
      this.primaryHost = (HostID)in.readObject();
      this.ro = (Serializable)in.readObject();
   }

   public int hashCode() {
      return this.id.hashCode() ^ this.version;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         try {
            AsyncUpdate other = (AsyncUpdate)object;
            return this.hashCode() == other.hashCode() && this.version == other.getVersion() && this.getId().equals(other.getId());
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public Serializable getChange() {
      return this.change;
   }

   public ROID getId() {
      return this.id;
   }

   public Object getKey() {
      return this.key;
   }

   public int getVersion() {
      return this.version;
   }

   public HostID getPrimaryHost() {
      return this.primaryHost;
   }

   public boolean isUpdate() {
      return this.update;
   }

   public Serializable getRO() {
      return this.ro;
   }

   public Serializable getARO() {
      return this.aro;
   }

   public String toString() {
      return "[AsyncUpdate- id: " + this.getId() + " isUpdate:" + this.isUpdate() + " version:" + this.getVersion() + " key: " + this.getKey() + " ro: " + this.getRO() + " aro: " + this.getARO() + " change: " + this.getChange() + " resource group key: " + this.resourceGroupKey + " primaryHost: " + this.getPrimaryHost() + "]";
   }
}
