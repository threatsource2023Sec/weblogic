package weblogic.cluster.messaging.internal;

import java.io.Serializable;

public interface ClusterMessage extends Serializable {
   int FORMATION_MESSAGE_ID = 1;
   int JOIN_REQUEST_MESSAGE_ID = 2;
   int JOIN_RESPONSE_MESSAGE_ID = 3;
   int LEASE_MESSAGE_ID = 4;
   int LEASE_TABLE_UPDATE_ID = 5;
   int GROUP_VIEW_UPDATE_ID = 6;
   int LEADER_HEARTBEAT_ID = 7;
   int STATE_DUMP_ID = 8;
   int PING_ID = 9;
   int LEADER_QUERY = 10;

   ServerInformation getSenderInformation();

   int getMessageType();
}
