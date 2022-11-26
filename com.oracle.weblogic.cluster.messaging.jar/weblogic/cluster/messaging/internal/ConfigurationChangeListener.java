package weblogic.cluster.messaging.internal;

public interface ConfigurationChangeListener {
   void serverAddedToConfiguration(ServerConfigurationInformation var1);

   void serverRemovedConfiguration(ServerConfigurationInformation var1);
}
