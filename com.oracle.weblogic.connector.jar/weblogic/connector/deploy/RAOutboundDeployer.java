package weblogic.connector.deploy;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ValidatingManagedConnectionFactory;
import weblogic.common.ResourceException;
import weblogic.connector.common.Debug;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.outbound.RAOutboundManager;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PlatformConstants;

public final class RAOutboundDeployer {
   private static final String CLASS_NAME = "weblogic.connector.deploy.RAOutboundDeployer";

   public static ConnectionPool prepare(UniversalResourceKey key, ManagedConnectionFactory mcf, OutboundInfo outboundInfo, String applicationName, String componentName, RAOutboundManager raOutboundManager) throws DeploymentException {
      Debug.enter("weblogic.connector.deploy.RAOutboundDeployer", "prepare(...) ");
      String jndi = outboundInfo.getJndiName();
      String moduleName = outboundInfo.getRAInfo().getModuleName();
      ConnectionPool connectionPool = null;
      checkValidTestParameters(mcf, outboundInfo);

      try {
         Debug.pooling("Constructing the connection pool for module '" + moduleName + "' with jndi name:  '" + jndi + "'");
         connectionPool = new ConnectionPool(key, mcf, outboundInfo, applicationName, componentName, raOutboundManager);
         Debug.pooling("Starting the connection pool for module '" + moduleName + "' with jndi name:  '" + jndi + "'");
         connectionPool.start(getPoolProperties(outboundInfo, connectionPool.getName()));
      } catch (ResourceException var14) {
         Debug.pooling("While preparing the connection pool for module '" + moduleName + "' with jndi name:  '" + jndi + "' an exception was thrown.");
         String exMsg = Debug.getExceptionStartPoolFailed(var14.toString());
         throw new DeploymentException(exMsg, var14);
      } finally {
         Debug.pooling("Done preparing the connection pool for module '" + moduleName + "' with jndi name:  '" + jndi + "'.");
         Debug.exit("weblogic.connector.deploy.RAOutboundDeployer", "prepare(...) ");
      }

      return connectionPool;
   }

   private static void checkValidTestParameters(ManagedConnectionFactory mcf, OutboundInfo outboundInfo) throws DeploymentException {
      if (!(mcf instanceof ValidatingManagedConnectionFactory)) {
         Vector invalidReasons = new Vector();
         String reasonString;
         if (outboundInfo.getTestFrequencySeconds() != 0) {
            reasonString = Debug.getExceptionTestFrequencyNonZero();
            invalidReasons.add(reasonString);
         }

         if (outboundInfo.isTestConnectionsOnCreate()) {
            reasonString = Debug.getTestConnectionsOnCreateTrue();
            invalidReasons.add(reasonString);
         }

         if (outboundInfo.isTestConnectionsOnRelease()) {
            reasonString = Debug.getTestConnectionsOnReleaseTrue();
            invalidReasons.add(reasonString);
         }

         if (outboundInfo.isTestConnectionsOnReserve()) {
            reasonString = Debug.getTestConnectionsOnReserveTrue();
            invalidReasons.add(reasonString);
         }

         if (invalidReasons.size() > 0) {
            reasonString = Debug.getExceptionInvalidTestingConfig();
            Iterator iter = invalidReasons.iterator();

            while(iter.hasNext()) {
               reasonString = reasonString + (String)iter.next();
               if (iter.hasNext()) {
                  reasonString = reasonString + PlatformConstants.EOL + "  ";
               }
            }

            throw new DeploymentException(reasonString);
         }
      }

   }

   public static void updateInitialCapacity(ConnectionPool connectionPool, OutboundInfo outboundInfo) {
      try {
         connectionPool.setInitialCapacity(outboundInfo.getInitialCapacity());
      } catch (ResourceException var6) {
         Object rootCause;
         for(rootCause = var6; rootCause instanceof ResourceException && ((ResourceException)((ResourceException)rootCause)).getNested() != null; rootCause = ((ResourceException)((ResourceException)rootCause)).getNested()) {
         }

         AuthenticatedSubject kernelId;
         for(kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction()); rootCause instanceof javax.resource.ResourceException && connectionPool.getRAInstanceManager().getAdapterLayer().getLinkedException((javax.resource.ResourceException)rootCause, kernelId) != null; rootCause = connectionPool.getRAInstanceManager().getAdapterLayer().getLinkedException((javax.resource.ResourceException)rootCause, kernelId)) {
         }

         String rootCauseString = connectionPool.getRAInstanceManager().getAdapterLayer().toString(rootCause, kernelId);
         Debug.logCreateInitialConnectionsError(connectionPool.getName(), rootCauseString);
         Debug.pooling("Failed to open initial connections for " + connectionPool.getName() + " root cause " + rootCauseString + " stacktrace:\n" + connectionPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace((Throwable)rootCause, kernelId));
      }

   }

   public static Properties getPoolProperties(OutboundInfo outboundInfo, String poolName) {
      Properties props = new Properties();
      props.setProperty("name", poolName);
      props.setProperty("maxCapacity", String.valueOf(outboundInfo.getMaxCapacity()));
      props.setProperty("capacityIncrement", String.valueOf(outboundInfo.getCapacityIncrement()));
      props.setProperty("testOnReserve", String.valueOf(outboundInfo.isTestConnectionsOnReserve()));
      props.setProperty("testOnRelease", String.valueOf(outboundInfo.isTestConnectionsOnRelease()));
      props.setProperty("testOnCreate", String.valueOf(outboundInfo.isTestConnectionsOnCreate()));
      props.setProperty("testFrequencySeconds", String.valueOf(outboundInfo.getTestFrequencySeconds()));
      props.setProperty("shrinkEnabled", String.valueOf(outboundInfo.isShrinkingEnabled()));
      props.setProperty("resvTimeoutSeconds", String.valueOf(outboundInfo.getConnectionReserveTimeoutSeconds()));
      props.setProperty("shrinkFrequencySeconds", String.valueOf(outboundInfo.getShrinkFrequencySeconds()));
      props.setProperty("resCreationRetrySeconds", String.valueOf(outboundInfo.getConnectionCreationRetryFrequencySeconds()));
      props.setProperty("maxWaiters", String.valueOf(outboundInfo.getHighestNumWaiters()));
      props.setProperty("maxUnavl", String.valueOf(outboundInfo.getHighestNumUnavailable()));
      props.setProperty("inactiveResTimeoutSeconds", String.valueOf(outboundInfo.getInactiveConnectionTimeoutSeconds()));
      props.setProperty("harvestFreqSecsonds", String.valueOf(outboundInfo.getProfileHarvestFrequencySeconds()));
      props.setProperty("ignoreInUseResources", String.valueOf(outboundInfo.isIgnoreInUseConnectionsEnabled()));
      props.setProperty("matchConnectionsSupported", String.valueOf(outboundInfo.isMatchConnectionsSupported()));
      props.setProperty("initialCapacity", "0");
      return props;
   }
}
