package weblogic.cluster.replication;

import java.io.Externalizable;

public interface QuerySessionResponseMessage extends Externalizable {
   String getID();
}
