package weblogic.cluster.messaging.internal;

import java.io.IOException;

public interface GroupMember extends Comparable {
   ServerConfigurationInformation getConfiguration();

   long getStartTime();

   void send(Message var1) throws IOException;

   void receive(Message var1, Connection var2);

   void setLastMessageArrivalTime(long var1);

   long getLastArrivalTime();

   boolean addConnection(Connection var1);
}
