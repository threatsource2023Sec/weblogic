package weblogic.cluster.replication;

import weblogic.rmi.spi.HostID;

public interface QuerySessionRequestMessage {
   QuerySessionResponseMessage execute(HostID var1);

   String getID();
}
