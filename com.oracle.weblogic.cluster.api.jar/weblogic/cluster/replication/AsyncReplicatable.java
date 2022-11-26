package weblogic.cluster.replication;

import java.io.Serializable;

public interface AsyncReplicatable extends Serializable {
   Serializable getBatchedChanges();

   void setQueued();

   boolean isQueued();

   void commit();
}
