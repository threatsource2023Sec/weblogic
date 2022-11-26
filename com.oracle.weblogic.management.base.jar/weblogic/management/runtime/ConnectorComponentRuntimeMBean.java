package weblogic.management.runtime;

import java.util.Properties;
import javax.management.j2ee.statistics.Stats;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.utils.ErrorCollectionException;

public interface ConnectorComponentRuntimeMBean extends ComponentRuntimeMBean, HealthFeedback {
   String NEW = ManagementLogger.getStringNewLoggable().getMessageText();
   String INITIALIZED = ManagementLogger.getStringInitializedLoggable().getMessageText();
   String PREPARED = ManagementLogger.getStringPreparedLoggable().getMessageText();
   String ACTIVATED = ManagementLogger.getStringActivatedLoggable().getMessageText();
   String SUSPENDED = ManagementLogger.getStringSuspendedLoggable().getMessageText();

   int getConnectionPoolCount();

   ConnectorConnectionPoolRuntimeMBean[] getConnectionPools();

   ConnectorConnectionPoolRuntimeMBean getConnectionPool(String var1);

   int getInboundConnectionsCount();

   ConnectorInboundRuntimeMBean[] getInboundConnections();

   ConnectorInboundRuntimeMBean getInboundConnection(String var1);

   String getEISResourceId();

   void suspendAll() throws ErrorCollectionException;

   void suspend(int var1) throws ErrorCollectionException;

   void suspend(int var1, Properties var2) throws ErrorCollectionException;

   void resumeAll() throws ErrorCollectionException;

   void resume(int var1) throws ErrorCollectionException;

   void resume(int var1, Properties var2) throws ErrorCollectionException;

   Properties getConfiguredProperties();

   /** @deprecated */
   @Deprecated
   AppDeploymentMBean getAppDeploymentMBean();

   /** @deprecated */
   @Deprecated
   ConnectorComponentMBean getConnectorComponentMBean();

   ConnectorServiceRuntimeMBean getConnectorServiceRuntime();

   String getVersionId();

   String getActiveVersionId();

   boolean isVersioned();

   boolean isActiveVersion();

   String getJndiName();

   String getState();

   int getSuspendedState();

   String getSchema();

   String getSchema(String var1);

   String getConfigurationVersion();

   String getConfiguration();

   String getConfiguration(String var1);

   String getDescription();

   String[] getDescriptions();

   String getEISType();

   String getSpecVersion();

   String getVendorName();

   String getVersion();

   String getLinkref();

   String getComponentName();

   Stats getStats();

   WorkManagerRuntimeMBean getWorkManagerRuntime();

   ConnectorWorkManagerRuntimeMBean getConnectorWorkManagerRuntime();

   HealthState getHealthState();
}
