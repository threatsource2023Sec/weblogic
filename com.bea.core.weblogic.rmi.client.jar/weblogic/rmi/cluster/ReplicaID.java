package weblogic.rmi.cluster;

import java.io.Serializable;

public interface ReplicaID extends Serializable {
   String REPLICA_TYPE_EJB = "EJB";

   String getType();

   Object getID();
}
