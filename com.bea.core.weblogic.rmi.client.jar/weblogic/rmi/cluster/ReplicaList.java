package weblogic.rmi.cluster;

import java.io.Serializable;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;

public interface ReplicaList extends PiggybackResponse, Serializable {
   long serialVersionUID = -1853168011380861606L;

   int size();

   Version version();

   ReplicaID getReplicaID();

   void setReplicaID(ReplicaID var1);

   ReplicaVersion getReplicaVersion();

   void setReplicaVersion(ReplicaVersion var1);

   boolean isReplicaVersionChanged();

   void add(RemoteReference var1);

   RemoteReference get(int var1);

   RemoteReference getPrimary();

   void clear();

   void remove(RemoteReference var1);

   RemoteReference removeOne(HostID var1);

   RemoteReference[] toArray();

   RemoteReference findReplicaHostedBy(HostID var1);

   Object clone() throws CloneNotSupportedException;

   ReplicaList getListWithRefHostedBy(HostID var1);

   void reset(ReplicaList var1);

   void resetWithoutShuffle(ReplicaList var1);
}
