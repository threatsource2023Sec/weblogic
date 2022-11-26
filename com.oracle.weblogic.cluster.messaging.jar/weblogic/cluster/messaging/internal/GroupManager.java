package weblogic.cluster.messaging.internal;

public interface GroupManager {
   Group getLocalGroup();

   Group[] getRemoteGroups();

   GroupMember getLocalMember();

   void startRemoteGroups();

   void stopRemoteGroups();

   void sendRemoteGroups(Message var1);

   void handleMessage(Message var1, Connection var2);

   void setMessageListener(MessageListener var1);

   ServerConfigurationInformation getServerConfigForWire();

   void addToGroup(ServerConfigurationInformation var1);

   void removeFromGroup(String var1);
}
