package weblogic.cluster.messaging.internal;

public interface Group {
   GroupMember[] getMembers();

   void send(Message var1);

   boolean isLocal();

   void start();

   void stop();

   ServerConfigurationInformation getConfigInformation(String var1);

   GroupMember findOrCreateGroupMember(ServerConfigurationInformation var1, long var2);

   void forward(Message var1, Connection var2);

   void addToConfigurationSet(ServerConfigurationInformation var1);

   void addServer(ServerConfigurationInformation var1);

   void removeServer(String var1);
}
