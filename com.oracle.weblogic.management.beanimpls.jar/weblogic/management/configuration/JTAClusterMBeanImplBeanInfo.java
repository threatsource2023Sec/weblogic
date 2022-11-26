package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class JTAClusterMBeanImplBeanInfo extends JTAMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JTAClusterMBean.class;

   public JTAClusterMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JTAClusterMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JTAClusterMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This interface provides access to the JTA configuration attributes.  The methods defined herein are applicable for JTA configuration at the clluster level.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JTAClusterMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AbandonTimeoutSeconds")) {
         getterName = "getAbandonTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAbandonTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("AbandonTimeoutSeconds", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("AbandonTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum amount of time, in seconds, a transaction manager persists in attempting to complete the second phase of a two-phase commit transaction. </p>  <p>During the second phase of a two-phase commit transaction, the transaction manager continues to try to complete the transaction until all resource managers indicate that the transaction is completed. After the abandon transaction timer expires, no further attempt is made to resolve the transaction. If the transaction is in a prepared state before being abandoned, the transaction manager rolls back the transaction to release any locks held on behalf of the abandoned transaction.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeCompletionIterationLimit")) {
         getterName = "getBeforeCompletionIterationLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBeforeCompletionIterationLimit";
         }

         currentResult = new PropertyDescriptor("BeforeCompletionIterationLimit", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("BeforeCompletionIterationLimit", currentResult);
         currentResult.setValue("description", "<p>The maximum number of cycles that the transaction manager performs the <code>beforeCompletion</code> synchronization callback for this cluster.</p>  <p>Nothing prevents a Synchronization object from registering another during <code>beforeCompletion</code>, even those whose <code>beforeCompletions</code> have already been called. For example, an EJB can call another in its <code>ejbStore()</code> method. To accommodate this, the transaction manager calls all Synchronization objects, then repeats the cycle if new ones have been registered. This count sets a limit to the number of cycles that synchronization occurs.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckpointIntervalSeconds")) {
         getterName = "getCheckpointIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckpointIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("CheckpointIntervalSeconds", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("CheckpointIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The interval at which the transaction manager creates a new transaction log file and checks all old transaction log files to see if they are ready to be deleted.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(1800));
         currentResult.setValue("legalMin", new Integer(10));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletionTimeoutSeconds")) {
         getterName = "getCompletionTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompletionTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("CompletionTimeoutSeconds", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("CompletionTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum amount of time, in seconds, a transaction manager waits for all resource managers to respond and indicate if the transaction can be committed or rolled back.</p>  <ul><li> The default value is 0, which sets the value to approximately twice the default <code>transaction-timeout</code> value with a maximum value of 120 seconds. This value provides backward compatibility for prior releases without this setting.</li> <li>If the specified value is -1, the maximum value supported by this attribute is used. </li> <li>If the specified value exceeds the value set for <code>abandon-timeout-seconds</code>, the value of <code>abandon-timeout-seconds</code> is used. </li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrossDomainRecoveryRetryInterval")) {
         getterName = "getCrossDomainRecoveryRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossDomainRecoveryRetryInterval";
         }

         currentResult = new PropertyDescriptor("CrossDomainRecoveryRetryInterval", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("CrossDomainRecoveryRetryInterval", currentResult);
         currentResult.setValue("description", "<p>The interval at which a store lock for a given server in a recovery domain will be checked for takeover eligibility.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.3.0 Replaced with getSiteDomainRecoveryRetryInterval. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrossSiteRecoveryLeaseExpiration")) {
         getterName = "getCrossSiteRecoveryLeaseExpiration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossSiteRecoveryLeaseExpiration";
         }

         currentResult = new PropertyDescriptor("CrossSiteRecoveryLeaseExpiration", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("CrossSiteRecoveryLeaseExpiration", currentResult);
         currentResult.setValue("description", "<p>The time after which a lease expires making it eligible for recovery by another site.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrossSiteRecoveryLeaseUpdate")) {
         getterName = "getCrossSiteRecoveryLeaseUpdate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossSiteRecoveryLeaseUpdate";
         }

         currentResult = new PropertyDescriptor("CrossSiteRecoveryLeaseUpdate", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("CrossSiteRecoveryLeaseUpdate", currentResult);
         currentResult.setValue("description", "<p>The time in seconds in which to update at lease timestamp.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrossSiteRecoveryRetryInterval")) {
         getterName = "getCrossSiteRecoveryRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossSiteRecoveryRetryInterval";
         }

         currentResult = new PropertyDescriptor("CrossSiteRecoveryRetryInterval", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("CrossSiteRecoveryRetryInterval", currentResult);
         currentResult.setValue("description", "<p>The interval at which a lease for a given server in a recovery domain will be checked for takeover eligibility.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Determiners")) {
         getterName = "getDeterminers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeterminers";
         }

         currentResult = new PropertyDescriptor("Determiners", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("Determiners", currentResult);
         currentResult.setValue("description", "<p>Specifies a list of one or more transaction resources (determiners) separated by line breaks. A determiner's in-doubt transaction records are used during transaction recovery when a TLog is not present.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForgetHeuristics")) {
         getterName = "getForgetHeuristics";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForgetHeuristics";
         }

         currentResult = new PropertyDescriptor("ForgetHeuristics", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("ForgetHeuristics", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the transaction manager automatically performs an XA Resource <code>forget</code> operation for heuristic transaction completions.</p>  <p>When enabled, the transaction manager automatically performs an XA Resource <code>forget()</code> operation for all resources as soon as the transaction learns of a heuristic outcome. Disable this feature only if you know what to do with the resource when it reports a heuristic decision.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxResourceRequestsOnServer")) {
         getterName = "getMaxResourceRequestsOnServer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxResourceRequestsOnServer";
         }

         currentResult = new PropertyDescriptor("MaxResourceRequestsOnServer", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxResourceRequestsOnServer", currentResult);
         currentResult.setValue("description", "<p>Maximum number of concurrent requests to resources allowed for each server.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(10));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxResourceUnavailableMillis")) {
         getterName = "getMaxResourceUnavailableMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxResourceUnavailableMillis";
         }

         currentResult = new PropertyDescriptor("MaxResourceUnavailableMillis", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxResourceUnavailableMillis", currentResult);
         currentResult.setValue("description", "<p>Maximum duration time, in milliseconds, that a resource is declared dead. After the duration, the resource is declared available again, even if the resource provider does not explicitly re-register the resource.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRetrySecondsBeforeDeterminerFail")) {
         getterName = "getMaxRetrySecondsBeforeDeterminerFail";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetrySecondsBeforeDeterminerFail";
         }

         currentResult = new PropertyDescriptor("MaxRetrySecondsBeforeDeterminerFail", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxRetrySecondsBeforeDeterminerFail", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in seconds, WebLogic Server waits for the determiner to recover from a failure. If the determiner does not recover after this period, WebLogic Server sets the TLog health state to HEALTH_FAILED.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTransactions")) {
         getterName = "getMaxTransactions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTransactions";
         }

         currentResult = new PropertyDescriptor("MaxTransactions", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxTransactions", currentResult);
         currentResult.setValue("description", "<p>The maximum number of simultaneous in-progress transactions allowed on a server in this cluster.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTransactionsHealthIntervalMillis")) {
         getterName = "getMaxTransactionsHealthIntervalMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTransactionsHealthIntervalMillis";
         }

         currentResult = new PropertyDescriptor("MaxTransactionsHealthIntervalMillis", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxTransactionsHealthIntervalMillis", currentResult);
         currentResult.setValue("description", "<p>The interval for which the transaction map must be full for the JTA subsystem to declare its health as CRITICAL.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(1000L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxUniqueNameStatistics")) {
         getterName = "getMaxUniqueNameStatistics";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxUniqueNameStatistics";
         }

         currentResult = new PropertyDescriptor("MaxUniqueNameStatistics", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxUniqueNameStatistics", currentResult);
         currentResult.setValue("description", "<p>The maximum number of unique transaction names for which statistics are maintained.</p>  <p>The first 1001 unique transaction names are maintained as their own transaction name and stored in each statistic. After the 1001st transaction name is reached, the transaction name is stored as <code>weblogic.transaction.statistics.namedOverflow</code>, and the transaction statistic is also merged and maintained in <code>weblogic.transaction.statistics.namedOverflow</code>.</p>  <p>A transaction name typically represents a category of business transactions, such as \"funds-transfer.\"</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxXACallMillis")) {
         getterName = "getMaxXACallMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxXACallMillis";
         }

         currentResult = new PropertyDescriptor("MaxXACallMillis", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MaxXACallMillis", currentResult);
         currentResult.setValue("description", "<p>Maximum allowed time duration, in milliseconds,  for XA calls to resources. If a particular XA call to a resource exceeds the limit, the resource is declared unavailable.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationCheckpointIntervalSeconds")) {
         getterName = "getMigrationCheckpointIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMigrationCheckpointIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("MigrationCheckpointIntervalSeconds", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("MigrationCheckpointIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The time interval, in seconds,  that the checkpoint is done for the migrated transaction logs (TLOGs).</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParallelXADispatchPolicy")) {
         getterName = "getParallelXADispatchPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelXADispatchPolicy";
         }

         currentResult = new PropertyDescriptor("ParallelXADispatchPolicy", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("ParallelXADispatchPolicy", currentResult);
         currentResult.setValue("description", "<p>The dispatch policy to use when performing XA operations in parallel. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParallelXAEnabled")) {
         getterName = "getParallelXAEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelXAEnabled";
         }

         currentResult = new PropertyDescriptor("ParallelXAEnabled", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("ParallelXAEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates that XA calls are executed in parallel if there are available threads.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PurgeResourceFromCheckpointIntervalSeconds")) {
         getterName = "getPurgeResourceFromCheckpointIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPurgeResourceFromCheckpointIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("PurgeResourceFromCheckpointIntervalSeconds", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("PurgeResourceFromCheckpointIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The interval that a particular resource must be accessed within for it to be included in the checkpoint record.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoverySiteName")) {
         getterName = "getRecoverySiteName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecoverySiteName";
         }

         currentResult = new PropertyDescriptor("RecoverySiteName", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("RecoverySiteName", currentResult);
         currentResult.setValue("description", "<p>The name of the site whose transactions this site/domain will recover in the event that intra-cluster transaction service migration is not successful or efficient.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.2.1.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoveryThresholdMillis")) {
         getterName = "getRecoveryThresholdMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecoveryThresholdMillis";
         }

         currentResult = new PropertyDescriptor("RecoveryThresholdMillis", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("RecoveryThresholdMillis", currentResult);
         currentResult.setValue("description", "<p>The interval that recovery is attempted until the resource becomes available.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(60000L));
         currentResult.setValue("setterDeprecated", "7.0.0.0 Replaced with nothing. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityInteropMode")) {
         getterName = "getSecurityInteropMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityInteropMode";
         }

         currentResult = new PropertyDescriptor("SecurityInteropMode", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("SecurityInteropMode", currentResult);
         currentResult.setValue("description", "<p>Specifies the security mode of the communication channel used for XA calls between servers that participate in a global transaction. All server instances in a domain must have the same security mode setting.</p>  <p>Security Interoperability Mode options:</p> <ul> <li><b>default</b>  The transaction coordinator makes calls using the kernel identity over an admin channel if it is enabled, and <code>anonymous</code> otherwise. Man-in-the-middle attacks are possible if the admin channel is not enabled.</li>  <li><b>performance</b>  The transaction coordinator makes calls using <code>anonymous</code> at all times. This implies a security risk since a malicious third party could then try to affect the outcome of transactions using a man-in-the-middle attack.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SerializeEnlistmentsGCIntervalMillis")) {
         getterName = "getSerializeEnlistmentsGCIntervalMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSerializeEnlistmentsGCIntervalMillis";
         }

         currentResult = new PropertyDescriptor("SerializeEnlistmentsGCIntervalMillis", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("SerializeEnlistmentsGCIntervalMillis", currentResult);
         currentResult.setValue("description", "<p>The time interval, in milliseconds, at which internal objects used to serialize resource enlistment are cleaned up.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShutdownGracePeriod")) {
         getterName = "getShutdownGracePeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShutdownGracePeriod";
         }

         currentResult = new PropertyDescriptor("ShutdownGracePeriod", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("ShutdownGracePeriod", currentResult);
         currentResult.setValue("description", "<p>Indicates how long the server should wait for active transactions to complete before allowing shutdown.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(180));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutSeconds")) {
         getterName = "getTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("TimeoutSeconds", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("TimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum amount of time, in seconds, an active transaction is allowed to be in the first phase of a two-phase commit transaction. If the specified amount of time expires, the transaction is automatically rolled back.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnregisterResourceGracePeriod")) {
         getterName = "getUnregisterResourceGracePeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnregisterResourceGracePeriod";
         }

         currentResult = new PropertyDescriptor("UnregisterResourceGracePeriod", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("UnregisterResourceGracePeriod", currentResult);
         currentResult.setValue("description", "<p>The amount of time, in seconds, a  transaction manager waits for transactions involving the resource to complete before unregistering a resource. This grace period helps minimize the risk of abandoned transactions because of an unregistered resource, such as a JDBC data source module packaged with an application.</p>  <p>During the specified grace period, the <code>unregisterResource</code> call blocks until the call returns and no new transactions are started for the associated resource. If the number of outstanding transactions for the resource goes to <code>0</code>, the <code>unregisterResource</code> call returns immediately.</p>  <p>At the end of the grace period, if outstanding transactions are associated with the resource, the <code>unregisterResource</code> call returns and a log message is written to the server on which the resource was previously registered. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterwideRecoveryEnabled")) {
         getterName = "isClusterwideRecoveryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterwideRecoveryEnabled";
         }

         currentResult = new PropertyDescriptor("ClusterwideRecoveryEnabled", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("ClusterwideRecoveryEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates that cluster-wide recovery is used for distributed transactions.</p>  <p>When enabled, recovery operations for a distributed transaction are applied to all the servers of the cluster hosting a InterposedTransactionManager rather than just the server hosting the InterposedTransactionManager.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TLOGWriteWhenDeterminerExistsEnabled")) {
         getterName = "isTLOGWriteWhenDeterminerExistsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTLOGWriteWhenDeterminerExistsEnabled";
         }

         currentResult = new PropertyDescriptor("TLOGWriteWhenDeterminerExistsEnabled", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("TLOGWriteWhenDeterminerExistsEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates two-phase transaction recovery logs are written even if one or more determiners are configured.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TightlyCoupledTransactionsEnabled")) {
         getterName = "isTightlyCoupledTransactionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTightlyCoupledTransactionsEnabled";
         }

         currentResult = new PropertyDescriptor("TightlyCoupledTransactionsEnabled", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("TightlyCoupledTransactionsEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates tight coupling of transaction branches that span different transaction manager systems. </p>  <p>When enabled, WebLogic uses the transaction identifier of a transaction imported by the InterposedTransactionManager for XA calls rather than an internally mapped Xid. This applies to inter-domain WebLogic transactions and transactions imported from Tuxedo. This allows for tight coupling of transaction branches for transactions that span across different transaction manager systems.</p>  <p>If a transaction between WebLogic and Tuxedo resources uses a GridLink Data Source with GridLink Affinity enabled, the XA Affinity context is automatically used for the transaction.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TwoPhaseEnabled")) {
         getterName = "isTwoPhaseEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTwoPhaseEnabled";
         }

         currentResult = new PropertyDescriptor("TwoPhaseEnabled", JTAClusterMBean.class, getterName, setterName);
         descriptors.put("TwoPhaseEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates that the two-phase commit protocol is used to coordinate transactions across two or more resource managers.</p> <p> If not selected:</p> <ul> <li> Two-phase commit is disabled and any attempt to use two-phase commit results in a RollbackException being thrown.</li> <li> All transaction logging is disabled, including checkpoint records.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
