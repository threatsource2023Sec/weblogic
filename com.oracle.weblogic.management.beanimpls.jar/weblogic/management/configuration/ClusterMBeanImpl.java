package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;
import weblogic.cluster.ClusterValidator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Cluster;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ClusterMBeanImpl extends TargetMBeanImpl implements ClusterMBean, Serializable {
   private int _AdditionalAutoMigrationAttempts;
   private int _AsyncSessionQueueTimeout;
   private String _AutoMigrationTableCreationDDLFile;
   private String _AutoMigrationTableCreationPolicy;
   private String _AutoMigrationTableName;
   private MachineMBean[] _CandidateMachinesForMigratableServers;
   private boolean _ClientCertProxyEnabled;
   private String _ClusterAddress;
   private String _ClusterBroadcastChannel;
   private String _ClusterMessagingMode;
   private String _ClusterType;
   private CoherenceClusterSystemResourceMBean _CoherenceClusterSystemResource;
   private CoherenceTierMBean _CoherenceTier;
   private boolean _ConcurrentSingletonActivationEnabled;
   private int _ConsensusParticipants;
   private JDBCSystemResourceMBean _DataSourceForAutomaticMigration;
   private JDBCSystemResourceMBean _DataSourceForJobScheduler;
   private JDBCSystemResourceMBean _DataSourceForSessionPersistence;
   private int _DatabaseLeasingBasisConnectionRetryCount;
   private long _DatabaseLeasingBasisConnectionRetryDelay;
   private DatabaseLessLeasingBasisMBean _DatabaseLessLeasingBasis;
   private int _DeathDetectorHeartbeatPeriod;
   private String _DefaultLoadAlgorithm;
   private DynamicServersMBean _DynamicServers;
   private boolean _DynamicallyCreated;
   private int _FencingGracePeriodMillis;
   private int _FrontendHTTPPort;
   private int _FrontendHTTPSPort;
   private String _FrontendHost;
   private int _GreedySessionFlushInterval;
   private int _HTTPPingRetryCount;
   private int _HealthCheckIntervalMillis;
   private int _HealthCheckPeriodsUntilFencing;
   private boolean _HttpTraceSupportEnabled;
   private int _IdlePeriodsUntilTimeout;
   private int _InterClusterCommLinkHealthCheckInterval;
   private JTAClusterMBean _JTACluster;
   private String _JobSchedulerTableName;
   private int _MaxServerCountForHttpPing;
   private boolean _MemberDeathDetectorEnabled;
   private int _MemberWarmupTimeoutSeconds;
   private boolean _MessageOrderingEnabled;
   private MigratableTargetMBean[] _MigratableTargets;
   private String _MigrationBasis;
   private long _MillisToSleepBetweenAutoMigrationAttempts;
   private String _MulticastAddress;
   private int _MulticastBufferSize;
   private boolean _MulticastDataEncryption;
   private int _MulticastPort;
   private int _MulticastSendDelay;
   private int _MulticastTTL;
   private String _Name;
   private int _NumberOfServersInClusterAddress;
   private boolean _OneWayRmiForReplicationEnabled;
   private OverloadProtectionMBean _OverloadProtection;
   private boolean _PersistSessionsOnShutdown;
   private String _RemoteClusterAddress;
   private String _ReplicationChannel;
   private boolean _ReplicationTimeoutEnabled;
   private boolean _SecureReplicationEnabled;
   private Set _ServerNames;
   private ServerMBean[] _Servers;
   private int _ServiceActivationRequestResponseTimeout;
   private int _ServiceAgeThresholdSeconds;
   private int _SessionFlushInterval;
   private int _SessionFlushThreshold;
   private boolean _SessionLazyDeserializationEnabled;
   private boolean _SessionStateQueryProtocolEnabled;
   private int _SessionStateQueryRequestTimeout;
   private String _SingletonSQLQueryHelper;
   private int _SingletonServiceRequestTimeout;
   private String _SiteName;
   private String[] _Tags;
   private boolean _TxnAffinityEnabled;
   private int _UnicastDiscoveryPeriodMillis;
   private int _UnicastReadTimeout;
   private String _WANSessionPersistenceTableName;
   private boolean _WeblogicPluginEnabled;
   private transient Cluster _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ClusterMBeanImpl() {
      try {
         this._customizer = new Cluster(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ClusterMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Cluster(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ClusterMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Cluster(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public Set getServerNames() {
      return this._customizer.getServerNames();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isServerNamesInherited() {
      return false;
   }

   public boolean isServerNamesSet() {
      return this._isSet(10);
   }

   public void setServerNames(Set param0) throws InvalidAttributeValueException {
      this._ServerNames = param0;
   }

   public void addServer(ServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._extendArray(this.getServers(), ServerMBean.class, param0));

         try {
            this.setServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServerMBean[] getServers() {
      return this._customizer.getServers();
   }

   public boolean isServersInherited() {
      return false;
   }

   public boolean isServersSet() {
      return this._isSet(11);
   }

   public void removeServer(ServerMBean param0) {
      ServerMBean[] _old = this.getServers();
      ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._removeElement(_old, ServerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setServers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setServers(ServerMBean[] param0) throws InvalidAttributeValueException {
      ServerMBean[] param0 = param0 == null ? new ServerMBeanImpl[0] : param0;
      this._Servers = (ServerMBean[])param0;
   }

   public String getClusterAddress() {
      return this._ClusterAddress;
   }

   public boolean isClusterAddressInherited() {
      return false;
   }

   public boolean isClusterAddressSet() {
      return this._isSet(12);
   }

   public void setClusterAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClusterAddress;
      this._ClusterAddress = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getMulticastAddress() {
      return this._customizer.getMulticastAddress();
   }

   public boolean isMulticastAddressInherited() {
      return false;
   }

   public boolean isMulticastAddressSet() {
      return this._isSet(13);
   }

   public void setMulticastAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      ClusterValidator.validateMulticastAddress(param0);
      String _oldVal = this.getMulticastAddress();
      this._customizer.setMulticastAddress(param0);
      this._postSet(13, _oldVal, param0);
   }

   public void setMulticastBufferSize(int param0) {
      LegalChecks.checkMin("MulticastBufferSize", param0, 64);
      int _oldVal = this._MulticastBufferSize;
      this._MulticastBufferSize = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMulticastBufferSize() {
      return this._MulticastBufferSize;
   }

   public boolean isMulticastBufferSizeInherited() {
      return false;
   }

   public boolean isMulticastBufferSizeSet() {
      return this._isSet(14);
   }

   public int getMulticastPort() {
      return this._MulticastPort;
   }

   public boolean isMulticastPortInherited() {
      return false;
   }

   public boolean isMulticastPortSet() {
      return this._isSet(15);
   }

   public void setMulticastPort(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MulticastPort", (long)param0, 1L, 65535L);
      int _oldVal = this._MulticastPort;
      this._MulticastPort = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getMulticastTTL() {
      return this._MulticastTTL;
   }

   public boolean isMulticastTTLInherited() {
      return false;
   }

   public boolean isMulticastTTLSet() {
      return this._isSet(16);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMulticastTTL(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MulticastTTL", (long)param0, 1L, 255L);
      int _oldVal = this._MulticastTTL;
      this._MulticastTTL = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getMulticastSendDelay() {
      return this._MulticastSendDelay;
   }

   public boolean isMulticastSendDelayInherited() {
      return false;
   }

   public boolean isMulticastSendDelaySet() {
      return this._isSet(17);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMulticastSendDelay(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MulticastSendDelay", (long)param0, 0L, 250L);
      int _oldVal = this._MulticastSendDelay;
      this._MulticastSendDelay = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getDefaultLoadAlgorithm() {
      return this._DefaultLoadAlgorithm;
   }

   public boolean isDefaultLoadAlgorithmInherited() {
      return false;
   }

   public boolean isDefaultLoadAlgorithmSet() {
      return this._isSet(18);
   }

   public void setDefaultLoadAlgorithm(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"round-robin", "weight-based", "random", "round-robin-affinity", "weight-based-affinity", "random-affinity"};
      param0 = LegalChecks.checkInEnum("DefaultLoadAlgorithm", param0, _set);
      String _oldVal = this._DefaultLoadAlgorithm;
      this._DefaultLoadAlgorithm = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getClusterMessagingMode() {
      return this._ClusterMessagingMode;
   }

   public boolean isClusterMessagingModeInherited() {
      return false;
   }

   public boolean isClusterMessagingModeSet() {
      return this._isSet(19);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setClusterMessagingMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"multicast", "unicast"};
      param0 = LegalChecks.checkInEnum("ClusterMessagingMode", param0, _set);
      String _oldVal = this._ClusterMessagingMode;
      this._ClusterMessagingMode = param0;
      this._postSet(19, _oldVal, param0);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public String getClusterBroadcastChannel() {
      return this._ClusterBroadcastChannel;
   }

   public boolean isClusterBroadcastChannelInherited() {
      return false;
   }

   public boolean isClusterBroadcastChannelSet() {
      return this._isSet(20);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setClusterBroadcastChannel(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClusterBroadcastChannel;
      this._ClusterBroadcastChannel = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getServiceAgeThresholdSeconds() {
      return this._ServiceAgeThresholdSeconds;
   }

   public boolean isServiceAgeThresholdSecondsInherited() {
      return false;
   }

   public boolean isServiceAgeThresholdSecondsSet() {
      return this._isSet(21);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setServiceAgeThresholdSeconds(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("ServiceAgeThresholdSeconds", (long)param0, 0L, 65534L);
      int _oldVal = this._ServiceAgeThresholdSeconds;
      this._ServiceAgeThresholdSeconds = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public HashMap start() {
      try {
         return this._customizer.start();
      } catch (RuntimeOperationsException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public HashMap kill() {
      try {
         return this._customizer.kill();
      } catch (RuntimeOperationsException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public void setClientCertProxyEnabled(boolean param0) {
      boolean _oldVal = this._ClientCertProxyEnabled;
      this._ClientCertProxyEnabled = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isClientCertProxyEnabled() {
      return this._ClientCertProxyEnabled;
   }

   public boolean isClientCertProxyEnabledInherited() {
      return false;
   }

   public boolean isClientCertProxyEnabledSet() {
      return this._isSet(22);
   }

   public void setWeblogicPluginEnabled(boolean param0) {
      boolean _oldVal = this._WeblogicPluginEnabled;
      this._WeblogicPluginEnabled = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isWeblogicPluginEnabled() {
      return this._WeblogicPluginEnabled;
   }

   public boolean isWeblogicPluginEnabledInherited() {
      return false;
   }

   public boolean isWeblogicPluginEnabledSet() {
      return this._isSet(23);
   }

   public void addMigratableTarget(MigratableTargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         MigratableTargetMBean[] _new = (MigratableTargetMBean[])((MigratableTargetMBean[])this._getHelper()._extendArray(this.getMigratableTargets(), MigratableTargetMBean.class, param0));

         try {
            this.setMigratableTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MigratableTargetMBean[] getMigratableTargets() {
      return this._customizer.getMigratableTargets();
   }

   public boolean isMigratableTargetsInherited() {
      return false;
   }

   public boolean isMigratableTargetsSet() {
      return this._isSet(24);
   }

   public void removeMigratableTarget(MigratableTargetMBean param0) {
      MigratableTargetMBean[] _old = this.getMigratableTargets();
      MigratableTargetMBean[] _new = (MigratableTargetMBean[])((MigratableTargetMBean[])this._getHelper()._removeElement(_old, MigratableTargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setMigratableTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMigratableTargets(MigratableTargetMBean[] param0) throws InvalidAttributeValueException {
      MigratableTargetMBean[] param0 = param0 == null ? new MigratableTargetMBeanImpl[0] : param0;
      this._MigratableTargets = (MigratableTargetMBean[])param0;
   }

   public int getMemberWarmupTimeoutSeconds() {
      if (!this._isSet(25)) {
         try {
            return this.getClusterMessagingMode().equals("unicast") ? 0 : 30;
         } catch (NullPointerException var2) {
         }
      }

      return this._MemberWarmupTimeoutSeconds;
   }

   public boolean isMemberWarmupTimeoutSecondsInherited() {
      return false;
   }

   public boolean isMemberWarmupTimeoutSecondsSet() {
      return this._isSet(25);
   }

   public void setMemberWarmupTimeoutSeconds(int param0) {
      LegalChecks.checkMin("MemberWarmupTimeoutSeconds", param0, 0);
      int _oldVal = this._MemberWarmupTimeoutSeconds;
      this._MemberWarmupTimeoutSeconds = param0;
      this._postSet(25, _oldVal, param0);
   }

   public void setHttpTraceSupportEnabled(boolean param0) {
      boolean _oldVal = this._HttpTraceSupportEnabled;
      this._HttpTraceSupportEnabled = param0;
      this._postSet(26, _oldVal, param0);
   }

   public boolean isHttpTraceSupportEnabled() {
      return this._HttpTraceSupportEnabled;
   }

   public boolean isHttpTraceSupportEnabledInherited() {
      return false;
   }

   public boolean isHttpTraceSupportEnabledSet() {
      return this._isSet(26);
   }

   public String getFrontendHost() {
      return this._FrontendHost;
   }

   public boolean isFrontendHostInherited() {
      return false;
   }

   public boolean isFrontendHostSet() {
      return this._isSet(27);
   }

   public void setFrontendHost(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FrontendHost;
      this._FrontendHost = param0;
      this._postSet(27, _oldVal, param0);
   }

   public int getFrontendHTTPPort() {
      return this._FrontendHTTPPort;
   }

   public boolean isFrontendHTTPPortInherited() {
      return false;
   }

   public boolean isFrontendHTTPPortSet() {
      return this._isSet(28);
   }

   public void setFrontendHTTPPort(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._FrontendHTTPPort;
      this._FrontendHTTPPort = param0;
      this._postSet(28, _oldVal, param0);
   }

   public int getFrontendHTTPSPort() {
      return this._FrontendHTTPSPort;
   }

   public boolean isFrontendHTTPSPortInherited() {
      return false;
   }

   public boolean isFrontendHTTPSPortSet() {
      return this._isSet(29);
   }

   public void setFrontendHTTPSPort(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._FrontendHTTPSPort;
      this._FrontendHTTPSPort = param0;
      this._postSet(29, _oldVal, param0);
   }

   public int getIdlePeriodsUntilTimeout() {
      return this._IdlePeriodsUntilTimeout;
   }

   public boolean isIdlePeriodsUntilTimeoutInherited() {
      return false;
   }

   public boolean isIdlePeriodsUntilTimeoutSet() {
      return this._isSet(30);
   }

   public void setIdlePeriodsUntilTimeout(int param0) {
      LegalChecks.checkMin("IdlePeriodsUntilTimeout", param0, 3);
      int _oldVal = this._IdlePeriodsUntilTimeout;
      this._IdlePeriodsUntilTimeout = param0;
      this._postSet(30, _oldVal, param0);
   }

   public void setRemoteClusterAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoteClusterAddress;
      this._RemoteClusterAddress = param0;
      this._postSet(31, _oldVal, param0);
   }

   public String getRemoteClusterAddress() {
      return this._RemoteClusterAddress;
   }

   public boolean isRemoteClusterAddressInherited() {
      return false;
   }

   public boolean isRemoteClusterAddressSet() {
      return this._isSet(31);
   }

   public void setWANSessionPersistenceTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WANSessionPersistenceTableName;
      this._WANSessionPersistenceTableName = param0;
      this._postSet(32, _oldVal, param0);
   }

   public String getWANSessionPersistenceTableName() {
      return this._WANSessionPersistenceTableName;
   }

   public boolean isWANSessionPersistenceTableNameInherited() {
      return false;
   }

   public boolean isWANSessionPersistenceTableNameSet() {
      return this._isSet(32);
   }

   public String getReplicationChannel() {
      return this._ReplicationChannel;
   }

   public boolean isReplicationChannelInherited() {
      return false;
   }

   public boolean isReplicationChannelSet() {
      return this._isSet(33);
   }

   public void setReplicationChannel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReplicationChannel;
      this._ReplicationChannel = param0;
      this._postSet(33, _oldVal, param0);
   }

   public int getInterClusterCommLinkHealthCheckInterval() {
      return this._InterClusterCommLinkHealthCheckInterval;
   }

   public boolean isInterClusterCommLinkHealthCheckIntervalInherited() {
      return false;
   }

   public boolean isInterClusterCommLinkHealthCheckIntervalSet() {
      return this._isSet(34);
   }

   public void setInterClusterCommLinkHealthCheckInterval(int param0) {
      int _oldVal = this._InterClusterCommLinkHealthCheckInterval;
      this._InterClusterCommLinkHealthCheckInterval = param0;
      this._postSet(34, _oldVal, param0);
   }

   public void setDataSourceForSessionPersistence(JDBCSystemResourceMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 35, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ClusterMBeanImpl.this.getDataSourceForSessionPersistence();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      JDBCSystemResourceMBean _oldVal = this._DataSourceForSessionPersistence;
      this._DataSourceForSessionPersistence = param0;
      this._postSet(35, _oldVal, param0);
   }

   public JDBCSystemResourceMBean getDataSourceForSessionPersistence() {
      return this._DataSourceForSessionPersistence;
   }

   public String getDataSourceForSessionPersistenceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDataSourceForSessionPersistence();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDataSourceForSessionPersistenceInherited() {
      return false;
   }

   public boolean isDataSourceForSessionPersistenceSet() {
      return this._isSet(35);
   }

   public void setDataSourceForSessionPersistenceAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JDBCSystemResourceMBean.class, new ReferenceManager.Resolver(this, 35) {
            public void resolveReference(Object value) {
               try {
                  ClusterMBeanImpl.this.setDataSourceForSessionPersistence((JDBCSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JDBCSystemResourceMBean _oldVal = this._DataSourceForSessionPersistence;
         this._initializeProperty(35);
         this._postSet(35, _oldVal, this._DataSourceForSessionPersistence);
      }

   }

   public void setDataSourceForJobScheduler(JDBCSystemResourceMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 36, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ClusterMBeanImpl.this.getDataSourceForJobScheduler();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      JDBCSystemResourceMBean _oldVal = this._DataSourceForJobScheduler;
      this._DataSourceForJobScheduler = param0;
      this._postSet(36, _oldVal, param0);
   }

   public JDBCSystemResourceMBean getDataSourceForJobScheduler() {
      return this._DataSourceForJobScheduler;
   }

   public String getDataSourceForJobSchedulerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDataSourceForJobScheduler();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDataSourceForJobSchedulerInherited() {
      return false;
   }

   public boolean isDataSourceForJobSchedulerSet() {
      return this._isSet(36);
   }

   public void setDataSourceForJobSchedulerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JDBCSystemResourceMBean.class, new ReferenceManager.Resolver(this, 36) {
            public void resolveReference(Object value) {
               try {
                  ClusterMBeanImpl.this.setDataSourceForJobScheduler((JDBCSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JDBCSystemResourceMBean _oldVal = this._DataSourceForJobScheduler;
         this._initializeProperty(36);
         this._postSet(36, _oldVal, this._DataSourceForJobScheduler);
      }

   }

   public String getJobSchedulerTableName() {
      return this._JobSchedulerTableName;
   }

   public boolean isJobSchedulerTableNameInherited() {
      return false;
   }

   public boolean isJobSchedulerTableNameSet() {
      return this._isSet(37);
   }

   public void setJobSchedulerTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JobSchedulerTableName;
      this._JobSchedulerTableName = param0;
      this._postSet(37, _oldVal, param0);
   }

   public boolean getPersistSessionsOnShutdown() {
      return this._PersistSessionsOnShutdown;
   }

   public boolean isPersistSessionsOnShutdownInherited() {
      return false;
   }

   public boolean isPersistSessionsOnShutdownSet() {
      return this._isSet(38);
   }

   public void setPersistSessionsOnShutdown(boolean param0) {
      boolean _oldVal = this._PersistSessionsOnShutdown;
      this._PersistSessionsOnShutdown = param0;
      this._postSet(38, _oldVal, param0);
   }

   public void setAsyncSessionQueueTimeout(int param0) {
      int _oldVal = this._AsyncSessionQueueTimeout;
      this._AsyncSessionQueueTimeout = param0;
      this._postSet(39, _oldVal, param0);
   }

   public int getAsyncSessionQueueTimeout() {
      return this._AsyncSessionQueueTimeout;
   }

   public boolean isAsyncSessionQueueTimeoutInherited() {
      return false;
   }

   public boolean isAsyncSessionQueueTimeoutSet() {
      return this._isSet(39);
   }

   public void setGreedySessionFlushInterval(int param0) {
      int _oldVal = this._GreedySessionFlushInterval;
      this._GreedySessionFlushInterval = param0;
      this._postSet(40, _oldVal, param0);
   }

   public int getGreedySessionFlushInterval() {
      return this._GreedySessionFlushInterval;
   }

   public boolean isGreedySessionFlushIntervalInherited() {
      return false;
   }

   public boolean isGreedySessionFlushIntervalSet() {
      return this._isSet(40);
   }

   public void setSessionFlushInterval(int param0) {
      int _oldVal = this._SessionFlushInterval;
      this._SessionFlushInterval = param0;
      this._postSet(41, _oldVal, param0);
   }

   public int getSessionFlushInterval() {
      return this._SessionFlushInterval;
   }

   public boolean isSessionFlushIntervalInherited() {
      return false;
   }

   public boolean isSessionFlushIntervalSet() {
      return this._isSet(41);
   }

   public void setSessionFlushThreshold(int param0) {
      int _oldVal = this._SessionFlushThreshold;
      this._SessionFlushThreshold = param0;
      this._postSet(42, _oldVal, param0);
   }

   public int getSessionFlushThreshold() {
      return this._SessionFlushThreshold;
   }

   public boolean isSessionFlushThresholdInherited() {
      return false;
   }

   public boolean isSessionFlushThresholdSet() {
      return this._isSet(42);
   }

   public void addCandidateMachinesForMigratableServer(MachineMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 43)) {
         MachineMBean[] _new;
         if (this._isSet(43)) {
            _new = (MachineMBean[])((MachineMBean[])this._getHelper()._extendArray(this.getCandidateMachinesForMigratableServers(), MachineMBean.class, param0));
         } else {
            _new = new MachineMBean[]{param0};
         }

         try {
            this.setCandidateMachinesForMigratableServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MachineMBean[] getCandidateMachinesForMigratableServers() {
      return this._CandidateMachinesForMigratableServers;
   }

   public String getCandidateMachinesForMigratableServersAsString() {
      return this._getHelper()._serializeKeyList(this.getCandidateMachinesForMigratableServers());
   }

   public boolean isCandidateMachinesForMigratableServersInherited() {
      return false;
   }

   public boolean isCandidateMachinesForMigratableServersSet() {
      return this._isSet(43);
   }

   public void removeCandidateMachinesForMigratableServer(MachineMBean param0) {
      MachineMBean[] _old = this.getCandidateMachinesForMigratableServers();
      MachineMBean[] _new = (MachineMBean[])((MachineMBean[])this._getHelper()._removeElement(_old, MachineMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setCandidateMachinesForMigratableServers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setCandidateMachinesForMigratableServersAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._CandidateMachinesForMigratableServers);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, MachineMBean.class, new ReferenceManager.Resolver(this, 43, param0) {
                  public void resolveReference(Object value) {
                     try {
                        ClusterMBeanImpl.this.addCandidateMachinesForMigratableServer((MachineMBean)value);
                        ClusterMBeanImpl.this._getHelper().reorderArrayObjects((Object[])ClusterMBeanImpl.this._CandidateMachinesForMigratableServers, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               MachineMBean[] var6 = this._CandidateMachinesForMigratableServers;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  MachineMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeCandidateMachinesForMigratableServer(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         MachineMBean[] _oldVal = this._CandidateMachinesForMigratableServers;
         this._initializeProperty(43);
         this._postSet(43, _oldVal, this._CandidateMachinesForMigratableServers);
      }
   }

   public void setCandidateMachinesForMigratableServers(MachineMBean[] param0) {
      MachineMBean[] param0 = param0 == null ? new MachineMBeanImpl[0] : param0;
      param0 = (MachineMBean[])((MachineMBean[])this._getHelper()._cleanAndValidateArray(param0, MachineMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 43, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return ClusterMBeanImpl.this.getCandidateMachinesForMigratableServers();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      MachineMBean[] _oldVal = this._CandidateMachinesForMigratableServers;
      this._CandidateMachinesForMigratableServers = param0;
      this._postSet(43, _oldVal, param0);
   }

   public JDBCSystemResourceMBean getDataSourceForAutomaticMigration() {
      return this._DataSourceForAutomaticMigration;
   }

   public String getDataSourceForAutomaticMigrationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDataSourceForAutomaticMigration();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDataSourceForAutomaticMigrationInherited() {
      return false;
   }

   public boolean isDataSourceForAutomaticMigrationSet() {
      return this._isSet(44);
   }

   public void setDataSourceForAutomaticMigrationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JDBCSystemResourceMBean.class, new ReferenceManager.Resolver(this, 44) {
            public void resolveReference(Object value) {
               try {
                  ClusterMBeanImpl.this.setDataSourceForAutomaticMigration((JDBCSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JDBCSystemResourceMBean _oldVal = this._DataSourceForAutomaticMigration;
         this._initializeProperty(44);
         this._postSet(44, _oldVal, this._DataSourceForAutomaticMigration);
      }

   }

   public void setDataSourceForAutomaticMigration(JDBCSystemResourceMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 44, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ClusterMBeanImpl.this.getDataSourceForAutomaticMigration();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      JDBCSystemResourceMBean _oldVal = this._DataSourceForAutomaticMigration;
      this._DataSourceForAutomaticMigration = param0;
      this._postSet(44, _oldVal, param0);
   }

   public int getHealthCheckIntervalMillis() {
      return this._HealthCheckIntervalMillis;
   }

   public boolean isHealthCheckIntervalMillisInherited() {
      return false;
   }

   public boolean isHealthCheckIntervalMillisSet() {
      return this._isSet(45);
   }

   public void setHealthCheckIntervalMillis(int param0) {
      int _oldVal = this._HealthCheckIntervalMillis;
      this._HealthCheckIntervalMillis = param0;
      this._postSet(45, _oldVal, param0);
   }

   public int getHealthCheckPeriodsUntilFencing() {
      return this._HealthCheckPeriodsUntilFencing;
   }

   public boolean isHealthCheckPeriodsUntilFencingInherited() {
      return false;
   }

   public boolean isHealthCheckPeriodsUntilFencingSet() {
      return this._isSet(46);
   }

   public void setHealthCheckPeriodsUntilFencing(int param0) {
      LegalChecks.checkMin("HealthCheckPeriodsUntilFencing", param0, 2);
      int _oldVal = this._HealthCheckPeriodsUntilFencing;
      this._HealthCheckPeriodsUntilFencing = param0;
      this._postSet(46, _oldVal, param0);
   }

   public int getFencingGracePeriodMillis() {
      return this._FencingGracePeriodMillis;
   }

   public boolean isFencingGracePeriodMillisInherited() {
      return false;
   }

   public boolean isFencingGracePeriodMillisSet() {
      return this._isSet(47);
   }

   public void setFencingGracePeriodMillis(int param0) {
      int _oldVal = this._FencingGracePeriodMillis;
      this._FencingGracePeriodMillis = param0;
      this._postSet(47, _oldVal, param0);
   }

   public String getSingletonSQLQueryHelper() {
      return this._SingletonSQLQueryHelper;
   }

   public boolean isSingletonSQLQueryHelperInherited() {
      return false;
   }

   public boolean isSingletonSQLQueryHelperSet() {
      return this._isSet(48);
   }

   public void setSingletonSQLQueryHelper(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SingletonSQLQueryHelper;
      this._SingletonSQLQueryHelper = param0;
      this._postSet(48, _oldVal, param0);
   }

   public int getNumberOfServersInClusterAddress() {
      return this._NumberOfServersInClusterAddress;
   }

   public boolean isNumberOfServersInClusterAddressInherited() {
      return false;
   }

   public boolean isNumberOfServersInClusterAddressSet() {
      return this._isSet(49);
   }

   public void setNumberOfServersInClusterAddress(int param0) {
      LegalChecks.checkMin("NumberOfServersInClusterAddress", param0, 1);
      int _oldVal = this._NumberOfServersInClusterAddress;
      this._NumberOfServersInClusterAddress = param0;
      this._postSet(49, _oldVal, param0);
   }

   public void setClusterType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "wan", "man"};
      param0 = LegalChecks.checkInEnum("ClusterType", param0, _set);
      String _oldVal = this._ClusterType;
      this._ClusterType = param0;
      this._postSet(50, _oldVal, param0);
   }

   public String getClusterType() {
      return this._ClusterType;
   }

   public boolean isClusterTypeInherited() {
      return false;
   }

   public boolean isClusterTypeSet() {
      return this._isSet(50);
   }

   public void setMulticastDataEncryption(boolean param0) {
      boolean _oldVal = this._MulticastDataEncryption;
      this._MulticastDataEncryption = param0;
      this._postSet(51, _oldVal, param0);
   }

   public boolean getMulticastDataEncryption() {
      return this._MulticastDataEncryption;
   }

   public boolean isMulticastDataEncryptionInherited() {
      return false;
   }

   public boolean isMulticastDataEncryptionSet() {
      return this._isSet(51);
   }

   public void setAutoMigrationTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AutoMigrationTableName;
      this._AutoMigrationTableName = param0;
      this._postSet(52, _oldVal, param0);
   }

   public String getAutoMigrationTableName() {
      return this._AutoMigrationTableName;
   }

   public boolean isAutoMigrationTableNameInherited() {
      return false;
   }

   public boolean isAutoMigrationTableNameSet() {
      return this._isSet(52);
   }

   public String getAutoMigrationTableCreationPolicy() {
      return this._AutoMigrationTableCreationPolicy;
   }

   public boolean isAutoMigrationTableCreationPolicyInherited() {
      return false;
   }

   public boolean isAutoMigrationTableCreationPolicySet() {
      return this._isSet(53);
   }

   public void setAutoMigrationTableCreationPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Disabled", "Always"};
      param0 = LegalChecks.checkInEnum("AutoMigrationTableCreationPolicy", param0, _set);
      String _oldVal = this._AutoMigrationTableCreationPolicy;
      this._AutoMigrationTableCreationPolicy = param0;
      this._postSet(53, _oldVal, param0);
   }

   public String getAutoMigrationTableCreationDDLFile() {
      return this._AutoMigrationTableCreationDDLFile;
   }

   public boolean isAutoMigrationTableCreationDDLFileInherited() {
      return false;
   }

   public boolean isAutoMigrationTableCreationDDLFileSet() {
      return this._isSet(54);
   }

   public void setAutoMigrationTableCreationDDLFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AutoMigrationTableCreationDDLFile;
      this._AutoMigrationTableCreationDDLFile = param0;
      this._postSet(54, _oldVal, param0);
   }

   public int getAdditionalAutoMigrationAttempts() {
      return this._AdditionalAutoMigrationAttempts;
   }

   public boolean isAdditionalAutoMigrationAttemptsInherited() {
      return false;
   }

   public boolean isAdditionalAutoMigrationAttemptsSet() {
      return this._isSet(55);
   }

   public void setAdditionalAutoMigrationAttempts(int param0) {
      int _oldVal = this._AdditionalAutoMigrationAttempts;
      this._AdditionalAutoMigrationAttempts = param0;
      this._postSet(55, _oldVal, param0);
   }

   public long getMillisToSleepBetweenAutoMigrationAttempts() {
      return this._MillisToSleepBetweenAutoMigrationAttempts;
   }

   public boolean isMillisToSleepBetweenAutoMigrationAttemptsInherited() {
      return false;
   }

   public boolean isMillisToSleepBetweenAutoMigrationAttemptsSet() {
      return this._isSet(56);
   }

   public void setMillisToSleepBetweenAutoMigrationAttempts(long param0) {
      long _oldVal = this._MillisToSleepBetweenAutoMigrationAttempts;
      this._MillisToSleepBetweenAutoMigrationAttempts = param0;
      this._postSet(56, _oldVal, param0);
   }

   public void setReplicationTimeoutEnabled(boolean param0) {
      boolean _oldVal = this._ReplicationTimeoutEnabled;
      this._ReplicationTimeoutEnabled = param0;
      this._postSet(59, _oldVal, param0);
   }

   public String getMigrationBasis() {
      return this._MigrationBasis;
   }

   public boolean isMigrationBasisInherited() {
      return false;
   }

   public boolean isMigrationBasisSet() {
      return this._isSet(57);
   }

   public void setMigrationBasis(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"database", "consensus"};
      param0 = LegalChecks.checkInEnum("MigrationBasis", param0, _set);
      String _oldVal = this._MigrationBasis;
      this._MigrationBasis = param0;
      this._postSet(57, _oldVal, param0);
   }

   public int getConsensusParticipants() {
      return this._ConsensusParticipants;
   }

   public boolean isConsensusParticipantsInherited() {
      return false;
   }

   public boolean isConsensusParticipantsSet() {
      return this._isSet(58);
   }

   public void setConsensusParticipants(int param0) {
      LegalChecks.checkInRange("ConsensusParticipants", (long)param0, 0L, 65536L);
      int _oldVal = this._ConsensusParticipants;
      this._ConsensusParticipants = param0;
      this._postSet(58, _oldVal, param0);
   }

   public boolean isReplicationTimeoutEnabled() {
      return this._ReplicationTimeoutEnabled;
   }

   public boolean isReplicationTimeoutEnabledInherited() {
      return false;
   }

   public boolean isReplicationTimeoutEnabledSet() {
      return this._isSet(59);
   }

   public OverloadProtectionMBean getOverloadProtection() {
      return this._OverloadProtection;
   }

   public boolean isOverloadProtectionInherited() {
      return false;
   }

   public boolean isOverloadProtectionSet() {
      return this._isSet(60) || this._isAnythingSet((AbstractDescriptorBean)this.getOverloadProtection());
   }

   public void setOverloadProtection(OverloadProtectionMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 60)) {
         this._postCreate(_child);
      }

      OverloadProtectionMBean _oldVal = this._OverloadProtection;
      this._OverloadProtection = param0;
      this._postSet(60, _oldVal, param0);
   }

   public DatabaseLessLeasingBasisMBean getDatabaseLessLeasingBasis() {
      return this._DatabaseLessLeasingBasis;
   }

   public boolean isDatabaseLessLeasingBasisInherited() {
      return false;
   }

   public boolean isDatabaseLessLeasingBasisSet() {
      return this._isSet(61) || this._isAnythingSet((AbstractDescriptorBean)this.getDatabaseLessLeasingBasis());
   }

   public void setDatabaseLessLeasingBasis(DatabaseLessLeasingBasisMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 61)) {
         this._postCreate(_child);
      }

      DatabaseLessLeasingBasisMBean _oldVal = this._DatabaseLessLeasingBasis;
      this._DatabaseLessLeasingBasis = param0;
      this._postSet(61, _oldVal, param0);
   }

   public void setHTTPPingRetryCount(int param0) {
      LegalChecks.checkMin("HTTPPingRetryCount", param0, 0);
      int _oldVal = this._HTTPPingRetryCount;
      this._HTTPPingRetryCount = param0;
      this._postSet(62, _oldVal, param0);
   }

   public int getHTTPPingRetryCount() {
      return this._HTTPPingRetryCount;
   }

   public boolean isHTTPPingRetryCountInherited() {
      return false;
   }

   public boolean isHTTPPingRetryCountSet() {
      return this._isSet(62);
   }

   public void setMaxServerCountForHttpPing(int param0) {
      LegalChecks.checkMin("MaxServerCountForHttpPing", param0, 0);
      int _oldVal = this._MaxServerCountForHttpPing;
      this._MaxServerCountForHttpPing = param0;
      this._postSet(63, _oldVal, param0);
   }

   public int getMaxServerCountForHttpPing() {
      return this._MaxServerCountForHttpPing;
   }

   public boolean isMaxServerCountForHttpPingInherited() {
      return false;
   }

   public boolean isMaxServerCountForHttpPingSet() {
      return this._isSet(63);
   }

   public boolean isSecureReplicationEnabled() {
      if (!this._isSet(64)) {
         return this._isSecureModeEnabled();
      } else {
         return this._SecureReplicationEnabled;
      }
   }

   public boolean isSecureReplicationEnabledInherited() {
      return false;
   }

   public boolean isSecureReplicationEnabledSet() {
      return this._isSet(64);
   }

   public void setSecureReplicationEnabled(boolean param0) {
      boolean _oldVal = this._SecureReplicationEnabled;
      this._SecureReplicationEnabled = param0;
      this._postSet(64, _oldVal, param0);
   }

   public void setUnicastDiscoveryPeriodMillis(int param0) {
      LegalChecks.checkMin("UnicastDiscoveryPeriodMillis", param0, 1000);
      int _oldVal = this._UnicastDiscoveryPeriodMillis;
      this._UnicastDiscoveryPeriodMillis = param0;
      this._postSet(65, _oldVal, param0);
   }

   public void setUnicastReadTimeout(int param0) {
      int _oldVal = this._UnicastReadTimeout;
      this._UnicastReadTimeout = param0;
      this._postSet(66, _oldVal, param0);
   }

   public int getUnicastDiscoveryPeriodMillis() {
      return this._UnicastDiscoveryPeriodMillis;
   }

   public boolean isUnicastDiscoveryPeriodMillisInherited() {
      return false;
   }

   public boolean isUnicastDiscoveryPeriodMillisSet() {
      return this._isSet(65);
   }

   public int getUnicastReadTimeout() {
      return this._UnicastReadTimeout;
   }

   public boolean isUnicastReadTimeoutInherited() {
      return false;
   }

   public boolean isUnicastReadTimeoutSet() {
      return this._isSet(66);
   }

   public void setMessageOrderingEnabled(boolean param0) {
      boolean _oldVal = this._MessageOrderingEnabled;
      this._MessageOrderingEnabled = param0;
      this._postSet(67, _oldVal, param0);
   }

   public boolean isMessageOrderingEnabled() {
      return this._MessageOrderingEnabled;
   }

   public boolean isMessageOrderingEnabledInherited() {
      return false;
   }

   public boolean isMessageOrderingEnabledSet() {
      return this._isSet(67);
   }

   public void setOneWayRmiForReplicationEnabled(boolean param0) {
      boolean _oldVal = this._OneWayRmiForReplicationEnabled;
      this._OneWayRmiForReplicationEnabled = param0;
      this._postSet(68, _oldVal, param0);
   }

   public boolean isOneWayRmiForReplicationEnabled() {
      return this._OneWayRmiForReplicationEnabled;
   }

   public boolean isOneWayRmiForReplicationEnabledInherited() {
      return false;
   }

   public boolean isOneWayRmiForReplicationEnabledSet() {
      return this._isSet(68);
   }

   public void setSessionLazyDeserializationEnabled(boolean param0) {
      boolean _oldVal = this._SessionLazyDeserializationEnabled;
      this._SessionLazyDeserializationEnabled = param0;
      this._postSet(69, _oldVal, param0);
   }

   public boolean isSessionLazyDeserializationEnabled() {
      if (!this._isSet(69)) {
         try {
            return ((DomainMBean)this.getParent()).isExalogicOptimizationsEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._SessionLazyDeserializationEnabled;
   }

   public boolean isSessionLazyDeserializationEnabledInherited() {
      return false;
   }

   public boolean isSessionLazyDeserializationEnabledSet() {
      return this._isSet(69);
   }

   public void setDeathDetectorHeartbeatPeriod(int param0) {
      LegalChecks.checkMin("DeathDetectorHeartbeatPeriod", param0, 1);
      int _oldVal = this._DeathDetectorHeartbeatPeriod;
      this._DeathDetectorHeartbeatPeriod = param0;
      this._postSet(70, _oldVal, param0);
   }

   public int getDeathDetectorHeartbeatPeriod() {
      return this._DeathDetectorHeartbeatPeriod;
   }

   public boolean isDeathDetectorHeartbeatPeriodInherited() {
      return false;
   }

   public boolean isDeathDetectorHeartbeatPeriodSet() {
      return this._isSet(70);
   }

   public void setMemberDeathDetectorEnabled(boolean param0) {
      boolean _oldVal = this._MemberDeathDetectorEnabled;
      this._MemberDeathDetectorEnabled = param0;
      this._postSet(71, _oldVal, param0);
   }

   public boolean isMemberDeathDetectorEnabled() {
      return this._MemberDeathDetectorEnabled;
   }

   public boolean isMemberDeathDetectorEnabledInherited() {
      return false;
   }

   public boolean isMemberDeathDetectorEnabledSet() {
      return this._isSet(71);
   }

   public CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource() {
      return this._customizer.getCoherenceClusterSystemResource();
   }

   public String getCoherenceClusterSystemResourceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCoherenceClusterSystemResource();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isCoherenceClusterSystemResourceInherited() {
      return false;
   }

   public boolean isCoherenceClusterSystemResourceSet() {
      return this._isSet(72);
   }

   public void setCoherenceClusterSystemResourceAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, CoherenceClusterSystemResourceMBean.class, new ReferenceManager.Resolver(this, 72) {
            public void resolveReference(Object value) {
               try {
                  ClusterMBeanImpl.this.setCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         CoherenceClusterSystemResourceMBean _oldVal = this._CoherenceClusterSystemResource;
         this._initializeProperty(72);
         this._postSet(72, _oldVal, this._CoherenceClusterSystemResource);
      }

   }

   public void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 72, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ClusterMBeanImpl.this.getCoherenceClusterSystemResource();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      CoherenceClusterSystemResourceMBean _oldVal = this.getCoherenceClusterSystemResource();
      this._customizer.setCoherenceClusterSystemResource(param0);
      this._postSet(72, _oldVal, param0);
   }

   public CoherenceTierMBean getCoherenceTier() {
      return this._CoherenceTier;
   }

   public boolean isCoherenceTierInherited() {
      return false;
   }

   public boolean isCoherenceTierSet() {
      return this._isSet(73) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceTier());
   }

   public void setCoherenceTier(CoherenceTierMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 73)) {
         this._postCreate(_child);
      }

      CoherenceTierMBean _oldVal = this._CoherenceTier;
      this._CoherenceTier = param0;
      this._postSet(73, _oldVal, param0);
   }

   public JTAClusterMBean getJTACluster() {
      return this._JTACluster;
   }

   public boolean isJTAClusterInherited() {
      return false;
   }

   public boolean isJTAClusterSet() {
      return this._isSet(74) || this._isAnythingSet((AbstractDescriptorBean)this.getJTACluster());
   }

   public void setJTACluster(JTAClusterMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 74)) {
         this._postCreate(_child);
      }

      JTAClusterMBean _oldVal = this._JTACluster;
      this._JTACluster = param0;
      this._postSet(74, _oldVal, param0);
   }

   public DynamicServersMBean getDynamicServers() {
      return this._DynamicServers;
   }

   public boolean isDynamicServersInherited() {
      return false;
   }

   public boolean isDynamicServersSet() {
      return this._isSet(75) || this._isAnythingSet((AbstractDescriptorBean)this.getDynamicServers());
   }

   public void setDynamicServers(DynamicServersMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 75)) {
         this._postCreate(_child);
      }

      DynamicServersMBean _oldVal = this._DynamicServers;
      this._DynamicServers = param0;
      this._postSet(75, _oldVal, param0);
   }

   public boolean getTxnAffinityEnabled() {
      return this._TxnAffinityEnabled;
   }

   public boolean isTxnAffinityEnabledInherited() {
      return false;
   }

   public boolean isTxnAffinityEnabledSet() {
      return this._isSet(76);
   }

   public void setTxnAffinityEnabled(boolean param0) {
      boolean _oldVal = this._TxnAffinityEnabled;
      this._TxnAffinityEnabled = param0;
      this._postSet(76, _oldVal, param0);
   }

   public int getDatabaseLeasingBasisConnectionRetryCount() {
      return this._DatabaseLeasingBasisConnectionRetryCount;
   }

   public boolean isDatabaseLeasingBasisConnectionRetryCountInherited() {
      return false;
   }

   public boolean isDatabaseLeasingBasisConnectionRetryCountSet() {
      return this._isSet(77);
   }

   public void setDatabaseLeasingBasisConnectionRetryCount(int param0) {
      LegalChecks.checkMin("DatabaseLeasingBasisConnectionRetryCount", param0, 1);
      int _oldVal = this._DatabaseLeasingBasisConnectionRetryCount;
      this._DatabaseLeasingBasisConnectionRetryCount = param0;
      this._postSet(77, _oldVal, param0);
   }

   public long getDatabaseLeasingBasisConnectionRetryDelay() {
      return this._DatabaseLeasingBasisConnectionRetryDelay;
   }

   public boolean isDatabaseLeasingBasisConnectionRetryDelayInherited() {
      return false;
   }

   public boolean isDatabaseLeasingBasisConnectionRetryDelaySet() {
      return this._isSet(78);
   }

   public void setDatabaseLeasingBasisConnectionRetryDelay(long param0) {
      long _oldVal = this._DatabaseLeasingBasisConnectionRetryDelay;
      this._DatabaseLeasingBasisConnectionRetryDelay = param0;
      this._postSet(78, _oldVal, param0);
   }

   public void setSessionStateQueryProtocolEnabled(boolean param0) {
      boolean _oldVal = this._SessionStateQueryProtocolEnabled;
      this._SessionStateQueryProtocolEnabled = param0;
      this._postSet(79, _oldVal, param0);
   }

   public boolean isSessionStateQueryProtocolEnabled() {
      return this._SessionStateQueryProtocolEnabled;
   }

   public boolean isSessionStateQueryProtocolEnabledInherited() {
      return false;
   }

   public boolean isSessionStateQueryProtocolEnabledSet() {
      return this._isSet(79);
   }

   public int getSessionStateQueryRequestTimeout() {
      return this._SessionStateQueryRequestTimeout;
   }

   public boolean isSessionStateQueryRequestTimeoutInherited() {
      return false;
   }

   public boolean isSessionStateQueryRequestTimeoutSet() {
      return this._isSet(80);
   }

   public void setSessionStateQueryRequestTimeout(int param0) {
      LegalChecks.checkMin("SessionStateQueryRequestTimeout", param0, 1);
      int _oldVal = this._SessionStateQueryRequestTimeout;
      this._SessionStateQueryRequestTimeout = param0;
      this._postSet(80, _oldVal, param0);
   }

   public int getServiceActivationRequestResponseTimeout() {
      return this._ServiceActivationRequestResponseTimeout;
   }

   public boolean isServiceActivationRequestResponseTimeoutInherited() {
      return false;
   }

   public boolean isServiceActivationRequestResponseTimeoutSet() {
      return this._isSet(81);
   }

   public int getSingletonServiceRequestTimeout() {
      return this._SingletonServiceRequestTimeout;
   }

   public boolean isSingletonServiceRequestTimeoutInherited() {
      return false;
   }

   public boolean isSingletonServiceRequestTimeoutSet() {
      return this._isSet(82);
   }

   public void setServiceActivationRequestResponseTimeout(int param0) {
      int _oldVal = this._ServiceActivationRequestResponseTimeout;
      this._ServiceActivationRequestResponseTimeout = param0;
      this._postSet(81, _oldVal, param0);
   }

   public void setSingletonServiceRequestTimeout(int param0) {
      int _oldVal = this._SingletonServiceRequestTimeout;
      this._SingletonServiceRequestTimeout = param0;
      this._postSet(82, _oldVal, param0);
   }

   public boolean isConcurrentSingletonActivationEnabled() {
      return this._ConcurrentSingletonActivationEnabled;
   }

   public boolean isConcurrentSingletonActivationEnabledInherited() {
      return false;
   }

   public boolean isConcurrentSingletonActivationEnabledSet() {
      return this._isSet(83);
   }

   public void setConcurrentSingletonActivationEnabled(boolean param0) {
      boolean _oldVal = this._ConcurrentSingletonActivationEnabled;
      this._ConcurrentSingletonActivationEnabled = param0;
      this._postSet(83, _oldVal, param0);
   }

   public String getSiteName() {
      if (!this._isSet(84)) {
         try {
            return ((DomainMBean)this.getParent()).getSiteName();
         } catch (NullPointerException var2) {
         }
      }

      return this._SiteName;
   }

   public boolean isSiteNameInherited() {
      return false;
   }

   public boolean isSiteNameSet() {
      return this._isSet(84);
   }

   public void setSiteName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SiteName;
      this._SiteName = param0;
      this._postSet(84, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet() || this.isCoherenceTierSet() || this.isDatabaseLessLeasingBasisSet() || this.isDynamicServersSet() || this.isJTAClusterSet() || this.isOverloadProtectionSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 55;
      }

      try {
         switch (idx) {
            case 55:
               this._AdditionalAutoMigrationAttempts = 3;
               if (initOne) {
                  break;
               }
            case 39:
               this._AsyncSessionQueueTimeout = 30;
               if (initOne) {
                  break;
               }
            case 54:
               this._AutoMigrationTableCreationDDLFile = null;
               if (initOne) {
                  break;
               }
            case 53:
               this._AutoMigrationTableCreationPolicy = "Disabled";
               if (initOne) {
                  break;
               }
            case 52:
               this._AutoMigrationTableName = "ACTIVE";
               if (initOne) {
                  break;
               }
            case 43:
               this._CandidateMachinesForMigratableServers = new MachineMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ClusterAddress = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._ClusterBroadcastChannel = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._ClusterMessagingMode = "unicast";
               if (initOne) {
                  break;
               }
            case 50:
               this._ClusterType = "none";
               if (initOne) {
                  break;
               }
            case 72:
               this._customizer.setCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)null);
               if (initOne) {
                  break;
               }
            case 73:
               this._CoherenceTier = new CoherenceTierMBeanImpl(this, 73);
               this._postCreate((AbstractDescriptorBean)this._CoherenceTier);
               if (initOne) {
                  break;
               }
            case 58:
               this._ConsensusParticipants = 0;
               if (initOne) {
                  break;
               }
            case 44:
               this._DataSourceForAutomaticMigration = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._DataSourceForJobScheduler = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._DataSourceForSessionPersistence = null;
               if (initOne) {
                  break;
               }
            case 77:
               this._DatabaseLeasingBasisConnectionRetryCount = 1;
               if (initOne) {
                  break;
               }
            case 78:
               this._DatabaseLeasingBasisConnectionRetryDelay = 1000L;
               if (initOne) {
                  break;
               }
            case 61:
               this._DatabaseLessLeasingBasis = new DatabaseLessLeasingBasisMBeanImpl(this, 61);
               this._postCreate((AbstractDescriptorBean)this._DatabaseLessLeasingBasis);
               if (initOne) {
                  break;
               }
            case 70:
               this._DeathDetectorHeartbeatPeriod = 1;
               if (initOne) {
                  break;
               }
            case 18:
               this._DefaultLoadAlgorithm = "round-robin";
               if (initOne) {
                  break;
               }
            case 75:
               this._DynamicServers = new DynamicServersMBeanImpl(this, 75);
               this._postCreate((AbstractDescriptorBean)this._DynamicServers);
               if (initOne) {
                  break;
               }
            case 47:
               this._FencingGracePeriodMillis = 30000;
               if (initOne) {
                  break;
               }
            case 28:
               this._FrontendHTTPPort = 0;
               if (initOne) {
                  break;
               }
            case 29:
               this._FrontendHTTPSPort = 0;
               if (initOne) {
                  break;
               }
            case 27:
               this._FrontendHost = null;
               if (initOne) {
                  break;
               }
            case 40:
               this._GreedySessionFlushInterval = 3;
               if (initOne) {
                  break;
               }
            case 62:
               this._HTTPPingRetryCount = 3;
               if (initOne) {
                  break;
               }
            case 45:
               this._HealthCheckIntervalMillis = 10000;
               if (initOne) {
                  break;
               }
            case 46:
               this._HealthCheckPeriodsUntilFencing = 6;
               if (initOne) {
                  break;
               }
            case 30:
               this._IdlePeriodsUntilTimeout = 3;
               if (initOne) {
                  break;
               }
            case 34:
               this._InterClusterCommLinkHealthCheckInterval = 30000;
               if (initOne) {
                  break;
               }
            case 74:
               this._JTACluster = new JTAClusterMBeanImpl(this, 74);
               this._postCreate((AbstractDescriptorBean)this._JTACluster);
               if (initOne) {
                  break;
               }
            case 37:
               this._JobSchedulerTableName = "WEBLOGIC_TIMERS";
               if (initOne) {
                  break;
               }
            case 63:
               this._MaxServerCountForHttpPing = 0;
               if (initOne) {
                  break;
               }
            case 25:
               this._MemberWarmupTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 24:
               this._MigratableTargets = new MigratableTargetMBean[0];
               if (initOne) {
                  break;
               }
            case 57:
               this._MigrationBasis = "database";
               if (initOne) {
                  break;
               }
            case 56:
               this._MillisToSleepBetweenAutoMigrationAttempts = 180000L;
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setMulticastAddress("239.192.0.0");
               if (initOne) {
                  break;
               }
            case 14:
               this._MulticastBufferSize = 64;
               if (initOne) {
                  break;
               }
            case 51:
               this._MulticastDataEncryption = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._MulticastPort = 7001;
               if (initOne) {
                  break;
               }
            case 17:
               this._MulticastSendDelay = 3;
               if (initOne) {
                  break;
               }
            case 16:
               this._MulticastTTL = 1;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 49:
               this._NumberOfServersInClusterAddress = 3;
               if (initOne) {
                  break;
               }
            case 60:
               this._OverloadProtection = new OverloadProtectionMBeanImpl(this, 60);
               this._postCreate((AbstractDescriptorBean)this._OverloadProtection);
               if (initOne) {
                  break;
               }
            case 38:
               this._PersistSessionsOnShutdown = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._RemoteClusterAddress = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._ReplicationChannel = "ReplicationChannel";
               if (initOne) {
                  break;
               }
            case 10:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._Servers = new ServerMBean[0];
               if (initOne) {
                  break;
               }
            case 81:
               this._ServiceActivationRequestResponseTimeout = 0;
               if (initOne) {
                  break;
               }
            case 21:
               this._ServiceAgeThresholdSeconds = 180;
               if (initOne) {
                  break;
               }
            case 41:
               this._SessionFlushInterval = 180;
               if (initOne) {
                  break;
               }
            case 42:
               this._SessionFlushThreshold = 10000;
               if (initOne) {
                  break;
               }
            case 80:
               this._SessionStateQueryRequestTimeout = 30;
               if (initOne) {
                  break;
               }
            case 48:
               this._SingletonSQLQueryHelper = "";
               if (initOne) {
                  break;
               }
            case 82:
               this._SingletonServiceRequestTimeout = 30000;
               if (initOne) {
                  break;
               }
            case 84:
               this._SiteName = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 76:
               this._TxnAffinityEnabled = false;
               if (initOne) {
                  break;
               }
            case 65:
               this._UnicastDiscoveryPeriodMillis = 3000;
               if (initOne) {
                  break;
               }
            case 66:
               this._UnicastReadTimeout = 15000;
               if (initOne) {
                  break;
               }
            case 32:
               this._WANSessionPersistenceTableName = "WLS_WAN_PERSISTENCE_TABLE";
               if (initOne) {
                  break;
               }
            case 22:
               this._ClientCertProxyEnabled = false;
               if (initOne) {
                  break;
               }
            case 83:
               this._ConcurrentSingletonActivationEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._HttpTraceSupportEnabled = false;
               if (initOne) {
                  break;
               }
            case 71:
               this._MemberDeathDetectorEnabled = false;
               if (initOne) {
                  break;
               }
            case 67:
               this._MessageOrderingEnabled = true;
               if (initOne) {
                  break;
               }
            case 68:
               this._OneWayRmiForReplicationEnabled = false;
               if (initOne) {
                  break;
               }
            case 59:
               this._ReplicationTimeoutEnabled = true;
               if (initOne) {
                  break;
               }
            case 64:
               this._SecureReplicationEnabled = false;
               if (initOne) {
                  break;
               }
            case 69:
               this._SessionLazyDeserializationEnabled = false;
               if (initOne) {
                  break;
               }
            case 79:
               this._SessionStateQueryProtocolEnabled = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._WeblogicPluginEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "Cluster";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("AdditionalAutoMigrationAttempts")) {
         oldVal = this._AdditionalAutoMigrationAttempts;
         this._AdditionalAutoMigrationAttempts = (Integer)v;
         this._postSet(55, oldVal, this._AdditionalAutoMigrationAttempts);
      } else if (name.equals("AsyncSessionQueueTimeout")) {
         oldVal = this._AsyncSessionQueueTimeout;
         this._AsyncSessionQueueTimeout = (Integer)v;
         this._postSet(39, oldVal, this._AsyncSessionQueueTimeout);
      } else {
         String oldVal;
         if (name.equals("AutoMigrationTableCreationDDLFile")) {
            oldVal = this._AutoMigrationTableCreationDDLFile;
            this._AutoMigrationTableCreationDDLFile = (String)v;
            this._postSet(54, oldVal, this._AutoMigrationTableCreationDDLFile);
         } else if (name.equals("AutoMigrationTableCreationPolicy")) {
            oldVal = this._AutoMigrationTableCreationPolicy;
            this._AutoMigrationTableCreationPolicy = (String)v;
            this._postSet(53, oldVal, this._AutoMigrationTableCreationPolicy);
         } else if (name.equals("AutoMigrationTableName")) {
            oldVal = this._AutoMigrationTableName;
            this._AutoMigrationTableName = (String)v;
            this._postSet(52, oldVal, this._AutoMigrationTableName);
         } else if (name.equals("CandidateMachinesForMigratableServers")) {
            MachineMBean[] oldVal = this._CandidateMachinesForMigratableServers;
            this._CandidateMachinesForMigratableServers = (MachineMBean[])((MachineMBean[])v);
            this._postSet(43, oldVal, this._CandidateMachinesForMigratableServers);
         } else {
            boolean oldVal;
            if (name.equals("ClientCertProxyEnabled")) {
               oldVal = this._ClientCertProxyEnabled;
               this._ClientCertProxyEnabled = (Boolean)v;
               this._postSet(22, oldVal, this._ClientCertProxyEnabled);
            } else if (name.equals("ClusterAddress")) {
               oldVal = this._ClusterAddress;
               this._ClusterAddress = (String)v;
               this._postSet(12, oldVal, this._ClusterAddress);
            } else if (name.equals("ClusterBroadcastChannel")) {
               oldVal = this._ClusterBroadcastChannel;
               this._ClusterBroadcastChannel = (String)v;
               this._postSet(20, oldVal, this._ClusterBroadcastChannel);
            } else if (name.equals("ClusterMessagingMode")) {
               oldVal = this._ClusterMessagingMode;
               this._ClusterMessagingMode = (String)v;
               this._postSet(19, oldVal, this._ClusterMessagingMode);
            } else if (name.equals("ClusterType")) {
               oldVal = this._ClusterType;
               this._ClusterType = (String)v;
               this._postSet(50, oldVal, this._ClusterType);
            } else if (name.equals("CoherenceClusterSystemResource")) {
               CoherenceClusterSystemResourceMBean oldVal = this._CoherenceClusterSystemResource;
               this._CoherenceClusterSystemResource = (CoherenceClusterSystemResourceMBean)v;
               this._postSet(72, oldVal, this._CoherenceClusterSystemResource);
            } else if (name.equals("CoherenceTier")) {
               CoherenceTierMBean oldVal = this._CoherenceTier;
               this._CoherenceTier = (CoherenceTierMBean)v;
               this._postSet(73, oldVal, this._CoherenceTier);
            } else if (name.equals("ConcurrentSingletonActivationEnabled")) {
               oldVal = this._ConcurrentSingletonActivationEnabled;
               this._ConcurrentSingletonActivationEnabled = (Boolean)v;
               this._postSet(83, oldVal, this._ConcurrentSingletonActivationEnabled);
            } else if (name.equals("ConsensusParticipants")) {
               oldVal = this._ConsensusParticipants;
               this._ConsensusParticipants = (Integer)v;
               this._postSet(58, oldVal, this._ConsensusParticipants);
            } else {
               JDBCSystemResourceMBean oldVal;
               if (name.equals("DataSourceForAutomaticMigration")) {
                  oldVal = this._DataSourceForAutomaticMigration;
                  this._DataSourceForAutomaticMigration = (JDBCSystemResourceMBean)v;
                  this._postSet(44, oldVal, this._DataSourceForAutomaticMigration);
               } else if (name.equals("DataSourceForJobScheduler")) {
                  oldVal = this._DataSourceForJobScheduler;
                  this._DataSourceForJobScheduler = (JDBCSystemResourceMBean)v;
                  this._postSet(36, oldVal, this._DataSourceForJobScheduler);
               } else if (name.equals("DataSourceForSessionPersistence")) {
                  oldVal = this._DataSourceForSessionPersistence;
                  this._DataSourceForSessionPersistence = (JDBCSystemResourceMBean)v;
                  this._postSet(35, oldVal, this._DataSourceForSessionPersistence);
               } else if (name.equals("DatabaseLeasingBasisConnectionRetryCount")) {
                  oldVal = this._DatabaseLeasingBasisConnectionRetryCount;
                  this._DatabaseLeasingBasisConnectionRetryCount = (Integer)v;
                  this._postSet(77, oldVal, this._DatabaseLeasingBasisConnectionRetryCount);
               } else {
                  long oldVal;
                  if (name.equals("DatabaseLeasingBasisConnectionRetryDelay")) {
                     oldVal = this._DatabaseLeasingBasisConnectionRetryDelay;
                     this._DatabaseLeasingBasisConnectionRetryDelay = (Long)v;
                     this._postSet(78, oldVal, this._DatabaseLeasingBasisConnectionRetryDelay);
                  } else if (name.equals("DatabaseLessLeasingBasis")) {
                     DatabaseLessLeasingBasisMBean oldVal = this._DatabaseLessLeasingBasis;
                     this._DatabaseLessLeasingBasis = (DatabaseLessLeasingBasisMBean)v;
                     this._postSet(61, oldVal, this._DatabaseLessLeasingBasis);
                  } else if (name.equals("DeathDetectorHeartbeatPeriod")) {
                     oldVal = this._DeathDetectorHeartbeatPeriod;
                     this._DeathDetectorHeartbeatPeriod = (Integer)v;
                     this._postSet(70, oldVal, this._DeathDetectorHeartbeatPeriod);
                  } else if (name.equals("DefaultLoadAlgorithm")) {
                     oldVal = this._DefaultLoadAlgorithm;
                     this._DefaultLoadAlgorithm = (String)v;
                     this._postSet(18, oldVal, this._DefaultLoadAlgorithm);
                  } else if (name.equals("DynamicServers")) {
                     DynamicServersMBean oldVal = this._DynamicServers;
                     this._DynamicServers = (DynamicServersMBean)v;
                     this._postSet(75, oldVal, this._DynamicServers);
                  } else if (name.equals("DynamicallyCreated")) {
                     oldVal = this._DynamicallyCreated;
                     this._DynamicallyCreated = (Boolean)v;
                     this._postSet(7, oldVal, this._DynamicallyCreated);
                  } else if (name.equals("FencingGracePeriodMillis")) {
                     oldVal = this._FencingGracePeriodMillis;
                     this._FencingGracePeriodMillis = (Integer)v;
                     this._postSet(47, oldVal, this._FencingGracePeriodMillis);
                  } else if (name.equals("FrontendHTTPPort")) {
                     oldVal = this._FrontendHTTPPort;
                     this._FrontendHTTPPort = (Integer)v;
                     this._postSet(28, oldVal, this._FrontendHTTPPort);
                  } else if (name.equals("FrontendHTTPSPort")) {
                     oldVal = this._FrontendHTTPSPort;
                     this._FrontendHTTPSPort = (Integer)v;
                     this._postSet(29, oldVal, this._FrontendHTTPSPort);
                  } else if (name.equals("FrontendHost")) {
                     oldVal = this._FrontendHost;
                     this._FrontendHost = (String)v;
                     this._postSet(27, oldVal, this._FrontendHost);
                  } else if (name.equals("GreedySessionFlushInterval")) {
                     oldVal = this._GreedySessionFlushInterval;
                     this._GreedySessionFlushInterval = (Integer)v;
                     this._postSet(40, oldVal, this._GreedySessionFlushInterval);
                  } else if (name.equals("HTTPPingRetryCount")) {
                     oldVal = this._HTTPPingRetryCount;
                     this._HTTPPingRetryCount = (Integer)v;
                     this._postSet(62, oldVal, this._HTTPPingRetryCount);
                  } else if (name.equals("HealthCheckIntervalMillis")) {
                     oldVal = this._HealthCheckIntervalMillis;
                     this._HealthCheckIntervalMillis = (Integer)v;
                     this._postSet(45, oldVal, this._HealthCheckIntervalMillis);
                  } else if (name.equals("HealthCheckPeriodsUntilFencing")) {
                     oldVal = this._HealthCheckPeriodsUntilFencing;
                     this._HealthCheckPeriodsUntilFencing = (Integer)v;
                     this._postSet(46, oldVal, this._HealthCheckPeriodsUntilFencing);
                  } else if (name.equals("HttpTraceSupportEnabled")) {
                     oldVal = this._HttpTraceSupportEnabled;
                     this._HttpTraceSupportEnabled = (Boolean)v;
                     this._postSet(26, oldVal, this._HttpTraceSupportEnabled);
                  } else if (name.equals("IdlePeriodsUntilTimeout")) {
                     oldVal = this._IdlePeriodsUntilTimeout;
                     this._IdlePeriodsUntilTimeout = (Integer)v;
                     this._postSet(30, oldVal, this._IdlePeriodsUntilTimeout);
                  } else if (name.equals("InterClusterCommLinkHealthCheckInterval")) {
                     oldVal = this._InterClusterCommLinkHealthCheckInterval;
                     this._InterClusterCommLinkHealthCheckInterval = (Integer)v;
                     this._postSet(34, oldVal, this._InterClusterCommLinkHealthCheckInterval);
                  } else if (name.equals("JTACluster")) {
                     JTAClusterMBean oldVal = this._JTACluster;
                     this._JTACluster = (JTAClusterMBean)v;
                     this._postSet(74, oldVal, this._JTACluster);
                  } else if (name.equals("JobSchedulerTableName")) {
                     oldVal = this._JobSchedulerTableName;
                     this._JobSchedulerTableName = (String)v;
                     this._postSet(37, oldVal, this._JobSchedulerTableName);
                  } else if (name.equals("MaxServerCountForHttpPing")) {
                     oldVal = this._MaxServerCountForHttpPing;
                     this._MaxServerCountForHttpPing = (Integer)v;
                     this._postSet(63, oldVal, this._MaxServerCountForHttpPing);
                  } else if (name.equals("MemberDeathDetectorEnabled")) {
                     oldVal = this._MemberDeathDetectorEnabled;
                     this._MemberDeathDetectorEnabled = (Boolean)v;
                     this._postSet(71, oldVal, this._MemberDeathDetectorEnabled);
                  } else if (name.equals("MemberWarmupTimeoutSeconds")) {
                     oldVal = this._MemberWarmupTimeoutSeconds;
                     this._MemberWarmupTimeoutSeconds = (Integer)v;
                     this._postSet(25, oldVal, this._MemberWarmupTimeoutSeconds);
                  } else if (name.equals("MessageOrderingEnabled")) {
                     oldVal = this._MessageOrderingEnabled;
                     this._MessageOrderingEnabled = (Boolean)v;
                     this._postSet(67, oldVal, this._MessageOrderingEnabled);
                  } else if (name.equals("MigratableTargets")) {
                     MigratableTargetMBean[] oldVal = this._MigratableTargets;
                     this._MigratableTargets = (MigratableTargetMBean[])((MigratableTargetMBean[])v);
                     this._postSet(24, oldVal, this._MigratableTargets);
                  } else if (name.equals("MigrationBasis")) {
                     oldVal = this._MigrationBasis;
                     this._MigrationBasis = (String)v;
                     this._postSet(57, oldVal, this._MigrationBasis);
                  } else if (name.equals("MillisToSleepBetweenAutoMigrationAttempts")) {
                     oldVal = this._MillisToSleepBetweenAutoMigrationAttempts;
                     this._MillisToSleepBetweenAutoMigrationAttempts = (Long)v;
                     this._postSet(56, oldVal, this._MillisToSleepBetweenAutoMigrationAttempts);
                  } else if (name.equals("MulticastAddress")) {
                     oldVal = this._MulticastAddress;
                     this._MulticastAddress = (String)v;
                     this._postSet(13, oldVal, this._MulticastAddress);
                  } else if (name.equals("MulticastBufferSize")) {
                     oldVal = this._MulticastBufferSize;
                     this._MulticastBufferSize = (Integer)v;
                     this._postSet(14, oldVal, this._MulticastBufferSize);
                  } else if (name.equals("MulticastDataEncryption")) {
                     oldVal = this._MulticastDataEncryption;
                     this._MulticastDataEncryption = (Boolean)v;
                     this._postSet(51, oldVal, this._MulticastDataEncryption);
                  } else if (name.equals("MulticastPort")) {
                     oldVal = this._MulticastPort;
                     this._MulticastPort = (Integer)v;
                     this._postSet(15, oldVal, this._MulticastPort);
                  } else if (name.equals("MulticastSendDelay")) {
                     oldVal = this._MulticastSendDelay;
                     this._MulticastSendDelay = (Integer)v;
                     this._postSet(17, oldVal, this._MulticastSendDelay);
                  } else if (name.equals("MulticastTTL")) {
                     oldVal = this._MulticastTTL;
                     this._MulticastTTL = (Integer)v;
                     this._postSet(16, oldVal, this._MulticastTTL);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("NumberOfServersInClusterAddress")) {
                     oldVal = this._NumberOfServersInClusterAddress;
                     this._NumberOfServersInClusterAddress = (Integer)v;
                     this._postSet(49, oldVal, this._NumberOfServersInClusterAddress);
                  } else if (name.equals("OneWayRmiForReplicationEnabled")) {
                     oldVal = this._OneWayRmiForReplicationEnabled;
                     this._OneWayRmiForReplicationEnabled = (Boolean)v;
                     this._postSet(68, oldVal, this._OneWayRmiForReplicationEnabled);
                  } else if (name.equals("OverloadProtection")) {
                     OverloadProtectionMBean oldVal = this._OverloadProtection;
                     this._OverloadProtection = (OverloadProtectionMBean)v;
                     this._postSet(60, oldVal, this._OverloadProtection);
                  } else if (name.equals("PersistSessionsOnShutdown")) {
                     oldVal = this._PersistSessionsOnShutdown;
                     this._PersistSessionsOnShutdown = (Boolean)v;
                     this._postSet(38, oldVal, this._PersistSessionsOnShutdown);
                  } else if (name.equals("RemoteClusterAddress")) {
                     oldVal = this._RemoteClusterAddress;
                     this._RemoteClusterAddress = (String)v;
                     this._postSet(31, oldVal, this._RemoteClusterAddress);
                  } else if (name.equals("ReplicationChannel")) {
                     oldVal = this._ReplicationChannel;
                     this._ReplicationChannel = (String)v;
                     this._postSet(33, oldVal, this._ReplicationChannel);
                  } else if (name.equals("ReplicationTimeoutEnabled")) {
                     oldVal = this._ReplicationTimeoutEnabled;
                     this._ReplicationTimeoutEnabled = (Boolean)v;
                     this._postSet(59, oldVal, this._ReplicationTimeoutEnabled);
                  } else if (name.equals("SecureReplicationEnabled")) {
                     oldVal = this._SecureReplicationEnabled;
                     this._SecureReplicationEnabled = (Boolean)v;
                     this._postSet(64, oldVal, this._SecureReplicationEnabled);
                  } else if (name.equals("ServerNames")) {
                     Set oldVal = this._ServerNames;
                     this._ServerNames = (Set)v;
                     this._postSet(10, oldVal, this._ServerNames);
                  } else if (name.equals("Servers")) {
                     ServerMBean[] oldVal = this._Servers;
                     this._Servers = (ServerMBean[])((ServerMBean[])v);
                     this._postSet(11, oldVal, this._Servers);
                  } else if (name.equals("ServiceActivationRequestResponseTimeout")) {
                     oldVal = this._ServiceActivationRequestResponseTimeout;
                     this._ServiceActivationRequestResponseTimeout = (Integer)v;
                     this._postSet(81, oldVal, this._ServiceActivationRequestResponseTimeout);
                  } else if (name.equals("ServiceAgeThresholdSeconds")) {
                     oldVal = this._ServiceAgeThresholdSeconds;
                     this._ServiceAgeThresholdSeconds = (Integer)v;
                     this._postSet(21, oldVal, this._ServiceAgeThresholdSeconds);
                  } else if (name.equals("SessionFlushInterval")) {
                     oldVal = this._SessionFlushInterval;
                     this._SessionFlushInterval = (Integer)v;
                     this._postSet(41, oldVal, this._SessionFlushInterval);
                  } else if (name.equals("SessionFlushThreshold")) {
                     oldVal = this._SessionFlushThreshold;
                     this._SessionFlushThreshold = (Integer)v;
                     this._postSet(42, oldVal, this._SessionFlushThreshold);
                  } else if (name.equals("SessionLazyDeserializationEnabled")) {
                     oldVal = this._SessionLazyDeserializationEnabled;
                     this._SessionLazyDeserializationEnabled = (Boolean)v;
                     this._postSet(69, oldVal, this._SessionLazyDeserializationEnabled);
                  } else if (name.equals("SessionStateQueryProtocolEnabled")) {
                     oldVal = this._SessionStateQueryProtocolEnabled;
                     this._SessionStateQueryProtocolEnabled = (Boolean)v;
                     this._postSet(79, oldVal, this._SessionStateQueryProtocolEnabled);
                  } else if (name.equals("SessionStateQueryRequestTimeout")) {
                     oldVal = this._SessionStateQueryRequestTimeout;
                     this._SessionStateQueryRequestTimeout = (Integer)v;
                     this._postSet(80, oldVal, this._SessionStateQueryRequestTimeout);
                  } else if (name.equals("SingletonSQLQueryHelper")) {
                     oldVal = this._SingletonSQLQueryHelper;
                     this._SingletonSQLQueryHelper = (String)v;
                     this._postSet(48, oldVal, this._SingletonSQLQueryHelper);
                  } else if (name.equals("SingletonServiceRequestTimeout")) {
                     oldVal = this._SingletonServiceRequestTimeout;
                     this._SingletonServiceRequestTimeout = (Integer)v;
                     this._postSet(82, oldVal, this._SingletonServiceRequestTimeout);
                  } else if (name.equals("SiteName")) {
                     oldVal = this._SiteName;
                     this._SiteName = (String)v;
                     this._postSet(84, oldVal, this._SiteName);
                  } else if (name.equals("Tags")) {
                     String[] oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("TxnAffinityEnabled")) {
                     oldVal = this._TxnAffinityEnabled;
                     this._TxnAffinityEnabled = (Boolean)v;
                     this._postSet(76, oldVal, this._TxnAffinityEnabled);
                  } else if (name.equals("UnicastDiscoveryPeriodMillis")) {
                     oldVal = this._UnicastDiscoveryPeriodMillis;
                     this._UnicastDiscoveryPeriodMillis = (Integer)v;
                     this._postSet(65, oldVal, this._UnicastDiscoveryPeriodMillis);
                  } else if (name.equals("UnicastReadTimeout")) {
                     oldVal = this._UnicastReadTimeout;
                     this._UnicastReadTimeout = (Integer)v;
                     this._postSet(66, oldVal, this._UnicastReadTimeout);
                  } else if (name.equals("WANSessionPersistenceTableName")) {
                     oldVal = this._WANSessionPersistenceTableName;
                     this._WANSessionPersistenceTableName = (String)v;
                     this._postSet(32, oldVal, this._WANSessionPersistenceTableName);
                  } else if (name.equals("WeblogicPluginEnabled")) {
                     oldVal = this._WeblogicPluginEnabled;
                     this._WeblogicPluginEnabled = (Boolean)v;
                     this._postSet(23, oldVal, this._WeblogicPluginEnabled);
                  } else if (name.equals("customizer")) {
                     Cluster oldVal = this._customizer;
                     this._customizer = (Cluster)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AdditionalAutoMigrationAttempts")) {
         return new Integer(this._AdditionalAutoMigrationAttempts);
      } else if (name.equals("AsyncSessionQueueTimeout")) {
         return new Integer(this._AsyncSessionQueueTimeout);
      } else if (name.equals("AutoMigrationTableCreationDDLFile")) {
         return this._AutoMigrationTableCreationDDLFile;
      } else if (name.equals("AutoMigrationTableCreationPolicy")) {
         return this._AutoMigrationTableCreationPolicy;
      } else if (name.equals("AutoMigrationTableName")) {
         return this._AutoMigrationTableName;
      } else if (name.equals("CandidateMachinesForMigratableServers")) {
         return this._CandidateMachinesForMigratableServers;
      } else if (name.equals("ClientCertProxyEnabled")) {
         return new Boolean(this._ClientCertProxyEnabled);
      } else if (name.equals("ClusterAddress")) {
         return this._ClusterAddress;
      } else if (name.equals("ClusterBroadcastChannel")) {
         return this._ClusterBroadcastChannel;
      } else if (name.equals("ClusterMessagingMode")) {
         return this._ClusterMessagingMode;
      } else if (name.equals("ClusterType")) {
         return this._ClusterType;
      } else if (name.equals("CoherenceClusterSystemResource")) {
         return this._CoherenceClusterSystemResource;
      } else if (name.equals("CoherenceTier")) {
         return this._CoherenceTier;
      } else if (name.equals("ConcurrentSingletonActivationEnabled")) {
         return new Boolean(this._ConcurrentSingletonActivationEnabled);
      } else if (name.equals("ConsensusParticipants")) {
         return new Integer(this._ConsensusParticipants);
      } else if (name.equals("DataSourceForAutomaticMigration")) {
         return this._DataSourceForAutomaticMigration;
      } else if (name.equals("DataSourceForJobScheduler")) {
         return this._DataSourceForJobScheduler;
      } else if (name.equals("DataSourceForSessionPersistence")) {
         return this._DataSourceForSessionPersistence;
      } else if (name.equals("DatabaseLeasingBasisConnectionRetryCount")) {
         return new Integer(this._DatabaseLeasingBasisConnectionRetryCount);
      } else if (name.equals("DatabaseLeasingBasisConnectionRetryDelay")) {
         return new Long(this._DatabaseLeasingBasisConnectionRetryDelay);
      } else if (name.equals("DatabaseLessLeasingBasis")) {
         return this._DatabaseLessLeasingBasis;
      } else if (name.equals("DeathDetectorHeartbeatPeriod")) {
         return new Integer(this._DeathDetectorHeartbeatPeriod);
      } else if (name.equals("DefaultLoadAlgorithm")) {
         return this._DefaultLoadAlgorithm;
      } else if (name.equals("DynamicServers")) {
         return this._DynamicServers;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FencingGracePeriodMillis")) {
         return new Integer(this._FencingGracePeriodMillis);
      } else if (name.equals("FrontendHTTPPort")) {
         return new Integer(this._FrontendHTTPPort);
      } else if (name.equals("FrontendHTTPSPort")) {
         return new Integer(this._FrontendHTTPSPort);
      } else if (name.equals("FrontendHost")) {
         return this._FrontendHost;
      } else if (name.equals("GreedySessionFlushInterval")) {
         return new Integer(this._GreedySessionFlushInterval);
      } else if (name.equals("HTTPPingRetryCount")) {
         return new Integer(this._HTTPPingRetryCount);
      } else if (name.equals("HealthCheckIntervalMillis")) {
         return new Integer(this._HealthCheckIntervalMillis);
      } else if (name.equals("HealthCheckPeriodsUntilFencing")) {
         return new Integer(this._HealthCheckPeriodsUntilFencing);
      } else if (name.equals("HttpTraceSupportEnabled")) {
         return new Boolean(this._HttpTraceSupportEnabled);
      } else if (name.equals("IdlePeriodsUntilTimeout")) {
         return new Integer(this._IdlePeriodsUntilTimeout);
      } else if (name.equals("InterClusterCommLinkHealthCheckInterval")) {
         return new Integer(this._InterClusterCommLinkHealthCheckInterval);
      } else if (name.equals("JTACluster")) {
         return this._JTACluster;
      } else if (name.equals("JobSchedulerTableName")) {
         return this._JobSchedulerTableName;
      } else if (name.equals("MaxServerCountForHttpPing")) {
         return new Integer(this._MaxServerCountForHttpPing);
      } else if (name.equals("MemberDeathDetectorEnabled")) {
         return new Boolean(this._MemberDeathDetectorEnabled);
      } else if (name.equals("MemberWarmupTimeoutSeconds")) {
         return new Integer(this._MemberWarmupTimeoutSeconds);
      } else if (name.equals("MessageOrderingEnabled")) {
         return new Boolean(this._MessageOrderingEnabled);
      } else if (name.equals("MigratableTargets")) {
         return this._MigratableTargets;
      } else if (name.equals("MigrationBasis")) {
         return this._MigrationBasis;
      } else if (name.equals("MillisToSleepBetweenAutoMigrationAttempts")) {
         return new Long(this._MillisToSleepBetweenAutoMigrationAttempts);
      } else if (name.equals("MulticastAddress")) {
         return this._MulticastAddress;
      } else if (name.equals("MulticastBufferSize")) {
         return new Integer(this._MulticastBufferSize);
      } else if (name.equals("MulticastDataEncryption")) {
         return new Boolean(this._MulticastDataEncryption);
      } else if (name.equals("MulticastPort")) {
         return new Integer(this._MulticastPort);
      } else if (name.equals("MulticastSendDelay")) {
         return new Integer(this._MulticastSendDelay);
      } else if (name.equals("MulticastTTL")) {
         return new Integer(this._MulticastTTL);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NumberOfServersInClusterAddress")) {
         return new Integer(this._NumberOfServersInClusterAddress);
      } else if (name.equals("OneWayRmiForReplicationEnabled")) {
         return new Boolean(this._OneWayRmiForReplicationEnabled);
      } else if (name.equals("OverloadProtection")) {
         return this._OverloadProtection;
      } else if (name.equals("PersistSessionsOnShutdown")) {
         return new Boolean(this._PersistSessionsOnShutdown);
      } else if (name.equals("RemoteClusterAddress")) {
         return this._RemoteClusterAddress;
      } else if (name.equals("ReplicationChannel")) {
         return this._ReplicationChannel;
      } else if (name.equals("ReplicationTimeoutEnabled")) {
         return new Boolean(this._ReplicationTimeoutEnabled);
      } else if (name.equals("SecureReplicationEnabled")) {
         return new Boolean(this._SecureReplicationEnabled);
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("Servers")) {
         return this._Servers;
      } else if (name.equals("ServiceActivationRequestResponseTimeout")) {
         return new Integer(this._ServiceActivationRequestResponseTimeout);
      } else if (name.equals("ServiceAgeThresholdSeconds")) {
         return new Integer(this._ServiceAgeThresholdSeconds);
      } else if (name.equals("SessionFlushInterval")) {
         return new Integer(this._SessionFlushInterval);
      } else if (name.equals("SessionFlushThreshold")) {
         return new Integer(this._SessionFlushThreshold);
      } else if (name.equals("SessionLazyDeserializationEnabled")) {
         return new Boolean(this._SessionLazyDeserializationEnabled);
      } else if (name.equals("SessionStateQueryProtocolEnabled")) {
         return new Boolean(this._SessionStateQueryProtocolEnabled);
      } else if (name.equals("SessionStateQueryRequestTimeout")) {
         return new Integer(this._SessionStateQueryRequestTimeout);
      } else if (name.equals("SingletonSQLQueryHelper")) {
         return this._SingletonSQLQueryHelper;
      } else if (name.equals("SingletonServiceRequestTimeout")) {
         return new Integer(this._SingletonServiceRequestTimeout);
      } else if (name.equals("SiteName")) {
         return this._SiteName;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TxnAffinityEnabled")) {
         return new Boolean(this._TxnAffinityEnabled);
      } else if (name.equals("UnicastDiscoveryPeriodMillis")) {
         return new Integer(this._UnicastDiscoveryPeriodMillis);
      } else if (name.equals("UnicastReadTimeout")) {
         return new Integer(this._UnicastReadTimeout);
      } else if (name.equals("WANSessionPersistenceTableName")) {
         return this._WANSessionPersistenceTableName;
      } else if (name.equals("WeblogicPluginEnabled")) {
         return new Boolean(this._WeblogicPluginEnabled);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends TargetMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 7:
            case 8:
            case 10:
            case 16:
            case 32:
            case 38:
            case 41:
            case 42:
            case 44:
            case 46:
            default:
               break;
            case 6:
               if (s.equals("server")) {
                  return 11;
               }
               break;
            case 9:
               if (s.equals("site-name")) {
                  return 84;
               }
               break;
            case 11:
               if (s.equals("jta-cluster")) {
                  return 74;
               }
               break;
            case 12:
               if (s.equals("cluster-type")) {
                  return 50;
               }

               if (s.equals("multicastttl")) {
                  return 16;
               }

               if (s.equals("server-names")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("frontend-host")) {
                  return 27;
               }
               break;
            case 14:
               if (s.equals("coherence-tier")) {
                  return 73;
               }

               if (s.equals("multicast-port")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("cluster-address")) {
                  return 12;
               }

               if (s.equals("dynamic-servers")) {
                  return 75;
               }

               if (s.equals("migration-basis")) {
                  return 57;
               }
               break;
            case 17:
               if (s.equals("frontendhttp-port")) {
                  return 28;
               }

               if (s.equals("migratable-target")) {
                  return 24;
               }

               if (s.equals("multicast-address")) {
                  return 13;
               }
               break;
            case 18:
               if (s.equals("frontendhttps-port")) {
                  return 29;
               }
               break;
            case 19:
               if (s.equals("overload-protection")) {
                  return 60;
               }

               if (s.equals("replication-channel")) {
                  return 33;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("multicast-send-delay")) {
                  return 17;
               }

               if (s.equals("txn-affinity-enabled")) {
                  return 76;
               }

               if (s.equals("unicast-read-timeout")) {
                  return 66;
               }
               break;
            case 21:
               if (s.equals("http-ping-retry-count")) {
                  return 62;
               }

               if (s.equals("multicast-buffer-size")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("cluster-messaging-mode")) {
                  return 19;
               }

               if (s.equals("consensus-participants")) {
                  return 58;
               }

               if (s.equals("default-load-algorithm")) {
                  return 18;
               }

               if (s.equals("remote-cluster-address")) {
                  return 31;
               }

               if (s.equals("session-flush-interval")) {
                  return 41;
               }
               break;
            case 23:
               if (s.equals("session-flush-threshold")) {
                  return 42;
               }

               if (s.equals("weblogic-plugin-enabled")) {
                  return 23;
               }
               break;
            case 24:
               if (s.equals("job-scheduler-table-name")) {
                  return 37;
               }

               if (s.equals("message-ordering-enabled")) {
                  return 67;
               }
               break;
            case 25:
               if (s.equals("auto-migration-table-name")) {
                  return 52;
               }

               if (s.equals("cluster-broadcast-channel")) {
                  return 20;
               }

               if (s.equals("multicast-data-encryption")) {
                  return 51;
               }

               if (s.equals("client-cert-proxy-enabled")) {
                  return 22;
               }
               break;
            case 26:
               if (s.equals("idle-periods-until-timeout")) {
                  return 30;
               }

               if (s.equals("singleton-sql-query-helper")) {
                  return 48;
               }

               if (s.equals("http-trace-support-enabled")) {
                  return 26;
               }

               if (s.equals("secure-replication-enabled")) {
                  return 64;
               }
               break;
            case 27:
               if (s.equals("async-session-queue-timeout")) {
                  return 39;
               }

               if (s.equals("database-less-leasing-basis")) {
                  return 61;
               }

               if (s.equals("fencing-grace-period-millis")) {
                  return 47;
               }

               if (s.equals("replication-timeout-enabled")) {
                  return 59;
               }
               break;
            case 28:
               if (s.equals("health-check-interval-millis")) {
                  return 45;
               }

               if (s.equals("persist-sessions-on-shutdown")) {
                  return 38;
               }
               break;
            case 29:
               if (s.equals("data-source-for-job-scheduler")) {
                  return 36;
               }

               if (s.equals("greedy-session-flush-interval")) {
                  return 40;
               }

               if (s.equals("member-warmup-timeout-seconds")) {
                  return 25;
               }

               if (s.equals("service-age-threshold-seconds")) {
                  return 21;
               }

               if (s.equals("member-death-detector-enabled")) {
                  return 71;
               }
               break;
            case 30:
               if (s.equals("max-server-count-for-http-ping")) {
                  return 63;
               }
               break;
            case 31:
               if (s.equals("death-detector-heartbeat-period")) {
                  return 70;
               }

               if (s.equals("unicast-discovery-period-millis")) {
                  return 65;
               }
               break;
            case 33:
               if (s.equals("coherence-cluster-system-resource")) {
                  return 72;
               }

               if (s.equals("singleton-service-request-timeout")) {
                  return 82;
               }
               break;
            case 34:
               if (s.equals("additional-auto-migration-attempts")) {
                  return 55;
               }

               if (s.equals("health-check-periods-until-fencing")) {
                  return 46;
               }

               if (s.equals("wan-session-persistence-table-name")) {
                  return 32;
               }
               break;
            case 35:
               if (s.equals("data-source-for-automatic-migration")) {
                  return 44;
               }

               if (s.equals("data-source-for-session-persistence")) {
                  return 35;
               }

               if (s.equals("session-state-query-request-timeout")) {
                  return 80;
               }

               if (s.equals("one-way-rmi-for-replication-enabled")) {
                  return 68;
               }
               break;
            case 36:
               if (s.equals("auto-migration-table-creation-policy")) {
                  return 53;
               }

               if (s.equals("number-of-servers-in-cluster-address")) {
                  return 49;
               }

               if (s.equals("session-lazy-deserialization-enabled")) {
                  return 69;
               }

               if (s.equals("session-state-query-protocol-enabled")) {
                  return 79;
               }
               break;
            case 37:
               if (s.equals("auto-migration-table-creationddl-file")) {
                  return 54;
               }
               break;
            case 39:
               if (s.equals("concurrent-singleton-activation-enabled")) {
                  return 83;
               }
               break;
            case 40:
               if (s.equals("candidate-machines-for-migratable-server")) {
                  return 43;
               }
               break;
            case 43:
               if (s.equals("service-activation-request-response-timeout")) {
                  return 81;
               }
               break;
            case 45:
               if (s.equals("database-leasing-basis-connection-retry-count")) {
                  return 77;
               }

               if (s.equals("database-leasing-basis-connection-retry-delay")) {
                  return 78;
               }

               if (s.equals("inter-cluster-comm-link-health-check-interval")) {
                  return 34;
               }
               break;
            case 47:
               if (s.equals("millis-to-sleep-between-auto-migration-attempts")) {
                  return 56;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 60:
               return new OverloadProtectionMBeanImpl.SchemaHelper2();
            case 61:
               return new DatabaseLessLeasingBasisMBeanImpl.SchemaHelper2();
            case 73:
               return new CoherenceTierMBeanImpl.SchemaHelper2();
            case 74:
               return new JTAClusterMBeanImpl.SchemaHelper2();
            case 75:
               return new DynamicServersMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "server-names";
            case 11:
               return "server";
            case 12:
               return "cluster-address";
            case 13:
               return "multicast-address";
            case 14:
               return "multicast-buffer-size";
            case 15:
               return "multicast-port";
            case 16:
               return "multicastttl";
            case 17:
               return "multicast-send-delay";
            case 18:
               return "default-load-algorithm";
            case 19:
               return "cluster-messaging-mode";
            case 20:
               return "cluster-broadcast-channel";
            case 21:
               return "service-age-threshold-seconds";
            case 22:
               return "client-cert-proxy-enabled";
            case 23:
               return "weblogic-plugin-enabled";
            case 24:
               return "migratable-target";
            case 25:
               return "member-warmup-timeout-seconds";
            case 26:
               return "http-trace-support-enabled";
            case 27:
               return "frontend-host";
            case 28:
               return "frontendhttp-port";
            case 29:
               return "frontendhttps-port";
            case 30:
               return "idle-periods-until-timeout";
            case 31:
               return "remote-cluster-address";
            case 32:
               return "wan-session-persistence-table-name";
            case 33:
               return "replication-channel";
            case 34:
               return "inter-cluster-comm-link-health-check-interval";
            case 35:
               return "data-source-for-session-persistence";
            case 36:
               return "data-source-for-job-scheduler";
            case 37:
               return "job-scheduler-table-name";
            case 38:
               return "persist-sessions-on-shutdown";
            case 39:
               return "async-session-queue-timeout";
            case 40:
               return "greedy-session-flush-interval";
            case 41:
               return "session-flush-interval";
            case 42:
               return "session-flush-threshold";
            case 43:
               return "candidate-machines-for-migratable-server";
            case 44:
               return "data-source-for-automatic-migration";
            case 45:
               return "health-check-interval-millis";
            case 46:
               return "health-check-periods-until-fencing";
            case 47:
               return "fencing-grace-period-millis";
            case 48:
               return "singleton-sql-query-helper";
            case 49:
               return "number-of-servers-in-cluster-address";
            case 50:
               return "cluster-type";
            case 51:
               return "multicast-data-encryption";
            case 52:
               return "auto-migration-table-name";
            case 53:
               return "auto-migration-table-creation-policy";
            case 54:
               return "auto-migration-table-creationddl-file";
            case 55:
               return "additional-auto-migration-attempts";
            case 56:
               return "millis-to-sleep-between-auto-migration-attempts";
            case 57:
               return "migration-basis";
            case 58:
               return "consensus-participants";
            case 59:
               return "replication-timeout-enabled";
            case 60:
               return "overload-protection";
            case 61:
               return "database-less-leasing-basis";
            case 62:
               return "http-ping-retry-count";
            case 63:
               return "max-server-count-for-http-ping";
            case 64:
               return "secure-replication-enabled";
            case 65:
               return "unicast-discovery-period-millis";
            case 66:
               return "unicast-read-timeout";
            case 67:
               return "message-ordering-enabled";
            case 68:
               return "one-way-rmi-for-replication-enabled";
            case 69:
               return "session-lazy-deserialization-enabled";
            case 70:
               return "death-detector-heartbeat-period";
            case 71:
               return "member-death-detector-enabled";
            case 72:
               return "coherence-cluster-system-resource";
            case 73:
               return "coherence-tier";
            case 74:
               return "jta-cluster";
            case 75:
               return "dynamic-servers";
            case 76:
               return "txn-affinity-enabled";
            case 77:
               return "database-leasing-basis-connection-retry-count";
            case 78:
               return "database-leasing-basis-connection-retry-delay";
            case 79:
               return "session-state-query-protocol-enabled";
            case 80:
               return "session-state-query-request-timeout";
            case 81:
               return "service-activation-request-response-timeout";
            case 82:
               return "singleton-service-request-timeout";
            case 83:
               return "concurrent-singleton-activation-enabled";
            case 84:
               return "site-name";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 11:
               return true;
            case 24:
               return true;
            case 43:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 60:
               return true;
            case 61:
               return true;
            case 73:
               return true;
            case 74:
               return true;
            case 75:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 60:
            case 61:
            case 63:
            case 66:
            case 67:
            case 68:
            case 69:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            default:
               return super.isConfigurable(propIndex);
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 57:
               return true;
            case 58:
               return true;
            case 62:
               return true;
            case 64:
               return true;
            case 65:
               return true;
            case 70:
               return true;
            case 84:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends TargetMBeanImpl.Helper {
      private ClusterMBeanImpl bean;

      protected Helper(ClusterMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "ServerNames";
            case 11:
               return "Servers";
            case 12:
               return "ClusterAddress";
            case 13:
               return "MulticastAddress";
            case 14:
               return "MulticastBufferSize";
            case 15:
               return "MulticastPort";
            case 16:
               return "MulticastTTL";
            case 17:
               return "MulticastSendDelay";
            case 18:
               return "DefaultLoadAlgorithm";
            case 19:
               return "ClusterMessagingMode";
            case 20:
               return "ClusterBroadcastChannel";
            case 21:
               return "ServiceAgeThresholdSeconds";
            case 22:
               return "ClientCertProxyEnabled";
            case 23:
               return "WeblogicPluginEnabled";
            case 24:
               return "MigratableTargets";
            case 25:
               return "MemberWarmupTimeoutSeconds";
            case 26:
               return "HttpTraceSupportEnabled";
            case 27:
               return "FrontendHost";
            case 28:
               return "FrontendHTTPPort";
            case 29:
               return "FrontendHTTPSPort";
            case 30:
               return "IdlePeriodsUntilTimeout";
            case 31:
               return "RemoteClusterAddress";
            case 32:
               return "WANSessionPersistenceTableName";
            case 33:
               return "ReplicationChannel";
            case 34:
               return "InterClusterCommLinkHealthCheckInterval";
            case 35:
               return "DataSourceForSessionPersistence";
            case 36:
               return "DataSourceForJobScheduler";
            case 37:
               return "JobSchedulerTableName";
            case 38:
               return "PersistSessionsOnShutdown";
            case 39:
               return "AsyncSessionQueueTimeout";
            case 40:
               return "GreedySessionFlushInterval";
            case 41:
               return "SessionFlushInterval";
            case 42:
               return "SessionFlushThreshold";
            case 43:
               return "CandidateMachinesForMigratableServers";
            case 44:
               return "DataSourceForAutomaticMigration";
            case 45:
               return "HealthCheckIntervalMillis";
            case 46:
               return "HealthCheckPeriodsUntilFencing";
            case 47:
               return "FencingGracePeriodMillis";
            case 48:
               return "SingletonSQLQueryHelper";
            case 49:
               return "NumberOfServersInClusterAddress";
            case 50:
               return "ClusterType";
            case 51:
               return "MulticastDataEncryption";
            case 52:
               return "AutoMigrationTableName";
            case 53:
               return "AutoMigrationTableCreationPolicy";
            case 54:
               return "AutoMigrationTableCreationDDLFile";
            case 55:
               return "AdditionalAutoMigrationAttempts";
            case 56:
               return "MillisToSleepBetweenAutoMigrationAttempts";
            case 57:
               return "MigrationBasis";
            case 58:
               return "ConsensusParticipants";
            case 59:
               return "ReplicationTimeoutEnabled";
            case 60:
               return "OverloadProtection";
            case 61:
               return "DatabaseLessLeasingBasis";
            case 62:
               return "HTTPPingRetryCount";
            case 63:
               return "MaxServerCountForHttpPing";
            case 64:
               return "SecureReplicationEnabled";
            case 65:
               return "UnicastDiscoveryPeriodMillis";
            case 66:
               return "UnicastReadTimeout";
            case 67:
               return "MessageOrderingEnabled";
            case 68:
               return "OneWayRmiForReplicationEnabled";
            case 69:
               return "SessionLazyDeserializationEnabled";
            case 70:
               return "DeathDetectorHeartbeatPeriod";
            case 71:
               return "MemberDeathDetectorEnabled";
            case 72:
               return "CoherenceClusterSystemResource";
            case 73:
               return "CoherenceTier";
            case 74:
               return "JTACluster";
            case 75:
               return "DynamicServers";
            case 76:
               return "TxnAffinityEnabled";
            case 77:
               return "DatabaseLeasingBasisConnectionRetryCount";
            case 78:
               return "DatabaseLeasingBasisConnectionRetryDelay";
            case 79:
               return "SessionStateQueryProtocolEnabled";
            case 80:
               return "SessionStateQueryRequestTimeout";
            case 81:
               return "ServiceActivationRequestResponseTimeout";
            case 82:
               return "SingletonServiceRequestTimeout";
            case 83:
               return "ConcurrentSingletonActivationEnabled";
            case 84:
               return "SiteName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdditionalAutoMigrationAttempts")) {
            return 55;
         } else if (propName.equals("AsyncSessionQueueTimeout")) {
            return 39;
         } else if (propName.equals("AutoMigrationTableCreationDDLFile")) {
            return 54;
         } else if (propName.equals("AutoMigrationTableCreationPolicy")) {
            return 53;
         } else if (propName.equals("AutoMigrationTableName")) {
            return 52;
         } else if (propName.equals("CandidateMachinesForMigratableServers")) {
            return 43;
         } else if (propName.equals("ClusterAddress")) {
            return 12;
         } else if (propName.equals("ClusterBroadcastChannel")) {
            return 20;
         } else if (propName.equals("ClusterMessagingMode")) {
            return 19;
         } else if (propName.equals("ClusterType")) {
            return 50;
         } else if (propName.equals("CoherenceClusterSystemResource")) {
            return 72;
         } else if (propName.equals("CoherenceTier")) {
            return 73;
         } else if (propName.equals("ConsensusParticipants")) {
            return 58;
         } else if (propName.equals("DataSourceForAutomaticMigration")) {
            return 44;
         } else if (propName.equals("DataSourceForJobScheduler")) {
            return 36;
         } else if (propName.equals("DataSourceForSessionPersistence")) {
            return 35;
         } else if (propName.equals("DatabaseLeasingBasisConnectionRetryCount")) {
            return 77;
         } else if (propName.equals("DatabaseLeasingBasisConnectionRetryDelay")) {
            return 78;
         } else if (propName.equals("DatabaseLessLeasingBasis")) {
            return 61;
         } else if (propName.equals("DeathDetectorHeartbeatPeriod")) {
            return 70;
         } else if (propName.equals("DefaultLoadAlgorithm")) {
            return 18;
         } else if (propName.equals("DynamicServers")) {
            return 75;
         } else if (propName.equals("FencingGracePeriodMillis")) {
            return 47;
         } else if (propName.equals("FrontendHTTPPort")) {
            return 28;
         } else if (propName.equals("FrontendHTTPSPort")) {
            return 29;
         } else if (propName.equals("FrontendHost")) {
            return 27;
         } else if (propName.equals("GreedySessionFlushInterval")) {
            return 40;
         } else if (propName.equals("HTTPPingRetryCount")) {
            return 62;
         } else if (propName.equals("HealthCheckIntervalMillis")) {
            return 45;
         } else if (propName.equals("HealthCheckPeriodsUntilFencing")) {
            return 46;
         } else if (propName.equals("IdlePeriodsUntilTimeout")) {
            return 30;
         } else if (propName.equals("InterClusterCommLinkHealthCheckInterval")) {
            return 34;
         } else if (propName.equals("JTACluster")) {
            return 74;
         } else if (propName.equals("JobSchedulerTableName")) {
            return 37;
         } else if (propName.equals("MaxServerCountForHttpPing")) {
            return 63;
         } else if (propName.equals("MemberWarmupTimeoutSeconds")) {
            return 25;
         } else if (propName.equals("MigratableTargets")) {
            return 24;
         } else if (propName.equals("MigrationBasis")) {
            return 57;
         } else if (propName.equals("MillisToSleepBetweenAutoMigrationAttempts")) {
            return 56;
         } else if (propName.equals("MulticastAddress")) {
            return 13;
         } else if (propName.equals("MulticastBufferSize")) {
            return 14;
         } else if (propName.equals("MulticastDataEncryption")) {
            return 51;
         } else if (propName.equals("MulticastPort")) {
            return 15;
         } else if (propName.equals("MulticastSendDelay")) {
            return 17;
         } else if (propName.equals("MulticastTTL")) {
            return 16;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NumberOfServersInClusterAddress")) {
            return 49;
         } else if (propName.equals("OverloadProtection")) {
            return 60;
         } else if (propName.equals("PersistSessionsOnShutdown")) {
            return 38;
         } else if (propName.equals("RemoteClusterAddress")) {
            return 31;
         } else if (propName.equals("ReplicationChannel")) {
            return 33;
         } else if (propName.equals("ServerNames")) {
            return 10;
         } else if (propName.equals("Servers")) {
            return 11;
         } else if (propName.equals("ServiceActivationRequestResponseTimeout")) {
            return 81;
         } else if (propName.equals("ServiceAgeThresholdSeconds")) {
            return 21;
         } else if (propName.equals("SessionFlushInterval")) {
            return 41;
         } else if (propName.equals("SessionFlushThreshold")) {
            return 42;
         } else if (propName.equals("SessionStateQueryRequestTimeout")) {
            return 80;
         } else if (propName.equals("SingletonSQLQueryHelper")) {
            return 48;
         } else if (propName.equals("SingletonServiceRequestTimeout")) {
            return 82;
         } else if (propName.equals("SiteName")) {
            return 84;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TxnAffinityEnabled")) {
            return 76;
         } else if (propName.equals("UnicastDiscoveryPeriodMillis")) {
            return 65;
         } else if (propName.equals("UnicastReadTimeout")) {
            return 66;
         } else if (propName.equals("WANSessionPersistenceTableName")) {
            return 32;
         } else if (propName.equals("ClientCertProxyEnabled")) {
            return 22;
         } else if (propName.equals("ConcurrentSingletonActivationEnabled")) {
            return 83;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("HttpTraceSupportEnabled")) {
            return 26;
         } else if (propName.equals("MemberDeathDetectorEnabled")) {
            return 71;
         } else if (propName.equals("MessageOrderingEnabled")) {
            return 67;
         } else if (propName.equals("OneWayRmiForReplicationEnabled")) {
            return 68;
         } else if (propName.equals("ReplicationTimeoutEnabled")) {
            return 59;
         } else if (propName.equals("SecureReplicationEnabled")) {
            return 64;
         } else if (propName.equals("SessionLazyDeserializationEnabled")) {
            return 69;
         } else if (propName.equals("SessionStateQueryProtocolEnabled")) {
            return 79;
         } else {
            return propName.equals("WeblogicPluginEnabled") ? 23 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCoherenceTier() != null) {
            iterators.add(new ArrayIterator(new CoherenceTierMBean[]{this.bean.getCoherenceTier()}));
         }

         if (this.bean.getDatabaseLessLeasingBasis() != null) {
            iterators.add(new ArrayIterator(new DatabaseLessLeasingBasisMBean[]{this.bean.getDatabaseLessLeasingBasis()}));
         }

         if (this.bean.getDynamicServers() != null) {
            iterators.add(new ArrayIterator(new DynamicServersMBean[]{this.bean.getDynamicServers()}));
         }

         if (this.bean.getJTACluster() != null) {
            iterators.add(new ArrayIterator(new JTAClusterMBean[]{this.bean.getJTACluster()}));
         }

         if (this.bean.getOverloadProtection() != null) {
            iterators.add(new ArrayIterator(new OverloadProtectionMBean[]{this.bean.getOverloadProtection()}));
         }

         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isAdditionalAutoMigrationAttemptsSet()) {
               buf.append("AdditionalAutoMigrationAttempts");
               buf.append(String.valueOf(this.bean.getAdditionalAutoMigrationAttempts()));
            }

            if (this.bean.isAsyncSessionQueueTimeoutSet()) {
               buf.append("AsyncSessionQueueTimeout");
               buf.append(String.valueOf(this.bean.getAsyncSessionQueueTimeout()));
            }

            if (this.bean.isAutoMigrationTableCreationDDLFileSet()) {
               buf.append("AutoMigrationTableCreationDDLFile");
               buf.append(String.valueOf(this.bean.getAutoMigrationTableCreationDDLFile()));
            }

            if (this.bean.isAutoMigrationTableCreationPolicySet()) {
               buf.append("AutoMigrationTableCreationPolicy");
               buf.append(String.valueOf(this.bean.getAutoMigrationTableCreationPolicy()));
            }

            if (this.bean.isAutoMigrationTableNameSet()) {
               buf.append("AutoMigrationTableName");
               buf.append(String.valueOf(this.bean.getAutoMigrationTableName()));
            }

            if (this.bean.isCandidateMachinesForMigratableServersSet()) {
               buf.append("CandidateMachinesForMigratableServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCandidateMachinesForMigratableServers())));
            }

            if (this.bean.isClusterAddressSet()) {
               buf.append("ClusterAddress");
               buf.append(String.valueOf(this.bean.getClusterAddress()));
            }

            if (this.bean.isClusterBroadcastChannelSet()) {
               buf.append("ClusterBroadcastChannel");
               buf.append(String.valueOf(this.bean.getClusterBroadcastChannel()));
            }

            if (this.bean.isClusterMessagingModeSet()) {
               buf.append("ClusterMessagingMode");
               buf.append(String.valueOf(this.bean.getClusterMessagingMode()));
            }

            if (this.bean.isClusterTypeSet()) {
               buf.append("ClusterType");
               buf.append(String.valueOf(this.bean.getClusterType()));
            }

            if (this.bean.isCoherenceClusterSystemResourceSet()) {
               buf.append("CoherenceClusterSystemResource");
               buf.append(String.valueOf(this.bean.getCoherenceClusterSystemResource()));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceTier());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConsensusParticipantsSet()) {
               buf.append("ConsensusParticipants");
               buf.append(String.valueOf(this.bean.getConsensusParticipants()));
            }

            if (this.bean.isDataSourceForAutomaticMigrationSet()) {
               buf.append("DataSourceForAutomaticMigration");
               buf.append(String.valueOf(this.bean.getDataSourceForAutomaticMigration()));
            }

            if (this.bean.isDataSourceForJobSchedulerSet()) {
               buf.append("DataSourceForJobScheduler");
               buf.append(String.valueOf(this.bean.getDataSourceForJobScheduler()));
            }

            if (this.bean.isDataSourceForSessionPersistenceSet()) {
               buf.append("DataSourceForSessionPersistence");
               buf.append(String.valueOf(this.bean.getDataSourceForSessionPersistence()));
            }

            if (this.bean.isDatabaseLeasingBasisConnectionRetryCountSet()) {
               buf.append("DatabaseLeasingBasisConnectionRetryCount");
               buf.append(String.valueOf(this.bean.getDatabaseLeasingBasisConnectionRetryCount()));
            }

            if (this.bean.isDatabaseLeasingBasisConnectionRetryDelaySet()) {
               buf.append("DatabaseLeasingBasisConnectionRetryDelay");
               buf.append(String.valueOf(this.bean.getDatabaseLeasingBasisConnectionRetryDelay()));
            }

            childValue = this.computeChildHashValue(this.bean.getDatabaseLessLeasingBasis());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDeathDetectorHeartbeatPeriodSet()) {
               buf.append("DeathDetectorHeartbeatPeriod");
               buf.append(String.valueOf(this.bean.getDeathDetectorHeartbeatPeriod()));
            }

            if (this.bean.isDefaultLoadAlgorithmSet()) {
               buf.append("DefaultLoadAlgorithm");
               buf.append(String.valueOf(this.bean.getDefaultLoadAlgorithm()));
            }

            childValue = this.computeChildHashValue(this.bean.getDynamicServers());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFencingGracePeriodMillisSet()) {
               buf.append("FencingGracePeriodMillis");
               buf.append(String.valueOf(this.bean.getFencingGracePeriodMillis()));
            }

            if (this.bean.isFrontendHTTPPortSet()) {
               buf.append("FrontendHTTPPort");
               buf.append(String.valueOf(this.bean.getFrontendHTTPPort()));
            }

            if (this.bean.isFrontendHTTPSPortSet()) {
               buf.append("FrontendHTTPSPort");
               buf.append(String.valueOf(this.bean.getFrontendHTTPSPort()));
            }

            if (this.bean.isFrontendHostSet()) {
               buf.append("FrontendHost");
               buf.append(String.valueOf(this.bean.getFrontendHost()));
            }

            if (this.bean.isGreedySessionFlushIntervalSet()) {
               buf.append("GreedySessionFlushInterval");
               buf.append(String.valueOf(this.bean.getGreedySessionFlushInterval()));
            }

            if (this.bean.isHTTPPingRetryCountSet()) {
               buf.append("HTTPPingRetryCount");
               buf.append(String.valueOf(this.bean.getHTTPPingRetryCount()));
            }

            if (this.bean.isHealthCheckIntervalMillisSet()) {
               buf.append("HealthCheckIntervalMillis");
               buf.append(String.valueOf(this.bean.getHealthCheckIntervalMillis()));
            }

            if (this.bean.isHealthCheckPeriodsUntilFencingSet()) {
               buf.append("HealthCheckPeriodsUntilFencing");
               buf.append(String.valueOf(this.bean.getHealthCheckPeriodsUntilFencing()));
            }

            if (this.bean.isIdlePeriodsUntilTimeoutSet()) {
               buf.append("IdlePeriodsUntilTimeout");
               buf.append(String.valueOf(this.bean.getIdlePeriodsUntilTimeout()));
            }

            if (this.bean.isInterClusterCommLinkHealthCheckIntervalSet()) {
               buf.append("InterClusterCommLinkHealthCheckInterval");
               buf.append(String.valueOf(this.bean.getInterClusterCommLinkHealthCheckInterval()));
            }

            childValue = this.computeChildHashValue(this.bean.getJTACluster());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJobSchedulerTableNameSet()) {
               buf.append("JobSchedulerTableName");
               buf.append(String.valueOf(this.bean.getJobSchedulerTableName()));
            }

            if (this.bean.isMaxServerCountForHttpPingSet()) {
               buf.append("MaxServerCountForHttpPing");
               buf.append(String.valueOf(this.bean.getMaxServerCountForHttpPing()));
            }

            if (this.bean.isMemberWarmupTimeoutSecondsSet()) {
               buf.append("MemberWarmupTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getMemberWarmupTimeoutSeconds()));
            }

            if (this.bean.isMigratableTargetsSet()) {
               buf.append("MigratableTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMigratableTargets())));
            }

            if (this.bean.isMigrationBasisSet()) {
               buf.append("MigrationBasis");
               buf.append(String.valueOf(this.bean.getMigrationBasis()));
            }

            if (this.bean.isMillisToSleepBetweenAutoMigrationAttemptsSet()) {
               buf.append("MillisToSleepBetweenAutoMigrationAttempts");
               buf.append(String.valueOf(this.bean.getMillisToSleepBetweenAutoMigrationAttempts()));
            }

            if (this.bean.isMulticastAddressSet()) {
               buf.append("MulticastAddress");
               buf.append(String.valueOf(this.bean.getMulticastAddress()));
            }

            if (this.bean.isMulticastBufferSizeSet()) {
               buf.append("MulticastBufferSize");
               buf.append(String.valueOf(this.bean.getMulticastBufferSize()));
            }

            if (this.bean.isMulticastDataEncryptionSet()) {
               buf.append("MulticastDataEncryption");
               buf.append(String.valueOf(this.bean.getMulticastDataEncryption()));
            }

            if (this.bean.isMulticastPortSet()) {
               buf.append("MulticastPort");
               buf.append(String.valueOf(this.bean.getMulticastPort()));
            }

            if (this.bean.isMulticastSendDelaySet()) {
               buf.append("MulticastSendDelay");
               buf.append(String.valueOf(this.bean.getMulticastSendDelay()));
            }

            if (this.bean.isMulticastTTLSet()) {
               buf.append("MulticastTTL");
               buf.append(String.valueOf(this.bean.getMulticastTTL()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNumberOfServersInClusterAddressSet()) {
               buf.append("NumberOfServersInClusterAddress");
               buf.append(String.valueOf(this.bean.getNumberOfServersInClusterAddress()));
            }

            childValue = this.computeChildHashValue(this.bean.getOverloadProtection());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPersistSessionsOnShutdownSet()) {
               buf.append("PersistSessionsOnShutdown");
               buf.append(String.valueOf(this.bean.getPersistSessionsOnShutdown()));
            }

            if (this.bean.isRemoteClusterAddressSet()) {
               buf.append("RemoteClusterAddress");
               buf.append(String.valueOf(this.bean.getRemoteClusterAddress()));
            }

            if (this.bean.isReplicationChannelSet()) {
               buf.append("ReplicationChannel");
               buf.append(String.valueOf(this.bean.getReplicationChannel()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            if (this.bean.isServersSet()) {
               buf.append("Servers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getServers())));
            }

            if (this.bean.isServiceActivationRequestResponseTimeoutSet()) {
               buf.append("ServiceActivationRequestResponseTimeout");
               buf.append(String.valueOf(this.bean.getServiceActivationRequestResponseTimeout()));
            }

            if (this.bean.isServiceAgeThresholdSecondsSet()) {
               buf.append("ServiceAgeThresholdSeconds");
               buf.append(String.valueOf(this.bean.getServiceAgeThresholdSeconds()));
            }

            if (this.bean.isSessionFlushIntervalSet()) {
               buf.append("SessionFlushInterval");
               buf.append(String.valueOf(this.bean.getSessionFlushInterval()));
            }

            if (this.bean.isSessionFlushThresholdSet()) {
               buf.append("SessionFlushThreshold");
               buf.append(String.valueOf(this.bean.getSessionFlushThreshold()));
            }

            if (this.bean.isSessionStateQueryRequestTimeoutSet()) {
               buf.append("SessionStateQueryRequestTimeout");
               buf.append(String.valueOf(this.bean.getSessionStateQueryRequestTimeout()));
            }

            if (this.bean.isSingletonSQLQueryHelperSet()) {
               buf.append("SingletonSQLQueryHelper");
               buf.append(String.valueOf(this.bean.getSingletonSQLQueryHelper()));
            }

            if (this.bean.isSingletonServiceRequestTimeoutSet()) {
               buf.append("SingletonServiceRequestTimeout");
               buf.append(String.valueOf(this.bean.getSingletonServiceRequestTimeout()));
            }

            if (this.bean.isSiteNameSet()) {
               buf.append("SiteName");
               buf.append(String.valueOf(this.bean.getSiteName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTxnAffinityEnabledSet()) {
               buf.append("TxnAffinityEnabled");
               buf.append(String.valueOf(this.bean.getTxnAffinityEnabled()));
            }

            if (this.bean.isUnicastDiscoveryPeriodMillisSet()) {
               buf.append("UnicastDiscoveryPeriodMillis");
               buf.append(String.valueOf(this.bean.getUnicastDiscoveryPeriodMillis()));
            }

            if (this.bean.isUnicastReadTimeoutSet()) {
               buf.append("UnicastReadTimeout");
               buf.append(String.valueOf(this.bean.getUnicastReadTimeout()));
            }

            if (this.bean.isWANSessionPersistenceTableNameSet()) {
               buf.append("WANSessionPersistenceTableName");
               buf.append(String.valueOf(this.bean.getWANSessionPersistenceTableName()));
            }

            if (this.bean.isClientCertProxyEnabledSet()) {
               buf.append("ClientCertProxyEnabled");
               buf.append(String.valueOf(this.bean.isClientCertProxyEnabled()));
            }

            if (this.bean.isConcurrentSingletonActivationEnabledSet()) {
               buf.append("ConcurrentSingletonActivationEnabled");
               buf.append(String.valueOf(this.bean.isConcurrentSingletonActivationEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isHttpTraceSupportEnabledSet()) {
               buf.append("HttpTraceSupportEnabled");
               buf.append(String.valueOf(this.bean.isHttpTraceSupportEnabled()));
            }

            if (this.bean.isMemberDeathDetectorEnabledSet()) {
               buf.append("MemberDeathDetectorEnabled");
               buf.append(String.valueOf(this.bean.isMemberDeathDetectorEnabled()));
            }

            if (this.bean.isMessageOrderingEnabledSet()) {
               buf.append("MessageOrderingEnabled");
               buf.append(String.valueOf(this.bean.isMessageOrderingEnabled()));
            }

            if (this.bean.isOneWayRmiForReplicationEnabledSet()) {
               buf.append("OneWayRmiForReplicationEnabled");
               buf.append(String.valueOf(this.bean.isOneWayRmiForReplicationEnabled()));
            }

            if (this.bean.isReplicationTimeoutEnabledSet()) {
               buf.append("ReplicationTimeoutEnabled");
               buf.append(String.valueOf(this.bean.isReplicationTimeoutEnabled()));
            }

            if (this.bean.isSecureReplicationEnabledSet()) {
               buf.append("SecureReplicationEnabled");
               buf.append(String.valueOf(this.bean.isSecureReplicationEnabled()));
            }

            if (this.bean.isSessionLazyDeserializationEnabledSet()) {
               buf.append("SessionLazyDeserializationEnabled");
               buf.append(String.valueOf(this.bean.isSessionLazyDeserializationEnabled()));
            }

            if (this.bean.isSessionStateQueryProtocolEnabledSet()) {
               buf.append("SessionStateQueryProtocolEnabled");
               buf.append(String.valueOf(this.bean.isSessionStateQueryProtocolEnabled()));
            }

            if (this.bean.isWeblogicPluginEnabledSet()) {
               buf.append("WeblogicPluginEnabled");
               buf.append(String.valueOf(this.bean.isWeblogicPluginEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            ClusterMBeanImpl otherTyped = (ClusterMBeanImpl)other;
            this.computeDiff("AdditionalAutoMigrationAttempts", this.bean.getAdditionalAutoMigrationAttempts(), otherTyped.getAdditionalAutoMigrationAttempts(), false);
            this.computeDiff("AsyncSessionQueueTimeout", this.bean.getAsyncSessionQueueTimeout(), otherTyped.getAsyncSessionQueueTimeout(), true);
            this.computeDiff("AutoMigrationTableCreationDDLFile", this.bean.getAutoMigrationTableCreationDDLFile(), otherTyped.getAutoMigrationTableCreationDDLFile(), false);
            this.computeDiff("AutoMigrationTableCreationPolicy", this.bean.getAutoMigrationTableCreationPolicy(), otherTyped.getAutoMigrationTableCreationPolicy(), false);
            this.computeDiff("AutoMigrationTableName", this.bean.getAutoMigrationTableName(), otherTyped.getAutoMigrationTableName(), false);
            this.computeDiff("CandidateMachinesForMigratableServers", this.bean.getCandidateMachinesForMigratableServers(), otherTyped.getCandidateMachinesForMigratableServers(), false);
            this.computeDiff("ClusterAddress", this.bean.getClusterAddress(), otherTyped.getClusterAddress(), false);
            this.computeDiff("ClusterBroadcastChannel", this.bean.getClusterBroadcastChannel(), otherTyped.getClusterBroadcastChannel(), false);
            this.computeDiff("ClusterMessagingMode", this.bean.getClusterMessagingMode(), otherTyped.getClusterMessagingMode(), false);
            this.computeDiff("ClusterType", this.bean.getClusterType(), otherTyped.getClusterType(), false);
            this.computeDiff("CoherenceClusterSystemResource", this.bean.getCoherenceClusterSystemResource(), otherTyped.getCoherenceClusterSystemResource(), false);
            this.computeSubDiff("CoherenceTier", this.bean.getCoherenceTier(), otherTyped.getCoherenceTier());
            this.computeDiff("ConsensusParticipants", this.bean.getConsensusParticipants(), otherTyped.getConsensusParticipants(), false);
            this.computeDiff("DataSourceForAutomaticMigration", this.bean.getDataSourceForAutomaticMigration(), otherTyped.getDataSourceForAutomaticMigration(), false);
            this.computeDiff("DataSourceForJobScheduler", this.bean.getDataSourceForJobScheduler(), otherTyped.getDataSourceForJobScheduler(), false);
            this.computeDiff("DataSourceForSessionPersistence", this.bean.getDataSourceForSessionPersistence(), otherTyped.getDataSourceForSessionPersistence(), false);
            this.computeDiff("DatabaseLeasingBasisConnectionRetryCount", this.bean.getDatabaseLeasingBasisConnectionRetryCount(), otherTyped.getDatabaseLeasingBasisConnectionRetryCount(), false);
            this.computeDiff("DatabaseLeasingBasisConnectionRetryDelay", this.bean.getDatabaseLeasingBasisConnectionRetryDelay(), otherTyped.getDatabaseLeasingBasisConnectionRetryDelay(), false);
            this.computeSubDiff("DatabaseLessLeasingBasis", this.bean.getDatabaseLessLeasingBasis(), otherTyped.getDatabaseLessLeasingBasis());
            this.computeDiff("DeathDetectorHeartbeatPeriod", this.bean.getDeathDetectorHeartbeatPeriod(), otherTyped.getDeathDetectorHeartbeatPeriod(), false);
            this.computeDiff("DefaultLoadAlgorithm", this.bean.getDefaultLoadAlgorithm(), otherTyped.getDefaultLoadAlgorithm(), true);
            this.computeSubDiff("DynamicServers", this.bean.getDynamicServers(), otherTyped.getDynamicServers());
            this.computeDiff("FencingGracePeriodMillis", this.bean.getFencingGracePeriodMillis(), otherTyped.getFencingGracePeriodMillis(), false);
            this.computeDiff("FrontendHTTPPort", this.bean.getFrontendHTTPPort(), otherTyped.getFrontendHTTPPort(), false);
            this.computeDiff("FrontendHTTPSPort", this.bean.getFrontendHTTPSPort(), otherTyped.getFrontendHTTPSPort(), false);
            this.computeDiff("FrontendHost", this.bean.getFrontendHost(), otherTyped.getFrontendHost(), false);
            this.computeDiff("GreedySessionFlushInterval", this.bean.getGreedySessionFlushInterval(), otherTyped.getGreedySessionFlushInterval(), true);
            this.computeDiff("HTTPPingRetryCount", this.bean.getHTTPPingRetryCount(), otherTyped.getHTTPPingRetryCount(), true);
            this.computeDiff("HealthCheckIntervalMillis", this.bean.getHealthCheckIntervalMillis(), otherTyped.getHealthCheckIntervalMillis(), false);
            this.computeDiff("HealthCheckPeriodsUntilFencing", this.bean.getHealthCheckPeriodsUntilFencing(), otherTyped.getHealthCheckPeriodsUntilFencing(), false);
            this.computeDiff("IdlePeriodsUntilTimeout", this.bean.getIdlePeriodsUntilTimeout(), otherTyped.getIdlePeriodsUntilTimeout(), false);
            this.computeDiff("InterClusterCommLinkHealthCheckInterval", this.bean.getInterClusterCommLinkHealthCheckInterval(), otherTyped.getInterClusterCommLinkHealthCheckInterval(), true);
            this.computeSubDiff("JTACluster", this.bean.getJTACluster(), otherTyped.getJTACluster());
            this.computeDiff("JobSchedulerTableName", this.bean.getJobSchedulerTableName(), otherTyped.getJobSchedulerTableName(), false);
            this.computeDiff("MaxServerCountForHttpPing", this.bean.getMaxServerCountForHttpPing(), otherTyped.getMaxServerCountForHttpPing(), true);
            this.computeDiff("MemberWarmupTimeoutSeconds", this.bean.getMemberWarmupTimeoutSeconds(), otherTyped.getMemberWarmupTimeoutSeconds(), true);
            this.computeDiff("MigrationBasis", this.bean.getMigrationBasis(), otherTyped.getMigrationBasis(), false);
            this.computeDiff("MillisToSleepBetweenAutoMigrationAttempts", this.bean.getMillisToSleepBetweenAutoMigrationAttempts(), otherTyped.getMillisToSleepBetweenAutoMigrationAttempts(), false);
            this.computeDiff("MulticastAddress", this.bean.getMulticastAddress(), otherTyped.getMulticastAddress(), false);
            this.computeDiff("MulticastBufferSize", this.bean.getMulticastBufferSize(), otherTyped.getMulticastBufferSize(), false);
            this.computeDiff("MulticastDataEncryption", this.bean.getMulticastDataEncryption(), otherTyped.getMulticastDataEncryption(), false);
            this.computeDiff("MulticastPort", this.bean.getMulticastPort(), otherTyped.getMulticastPort(), false);
            this.computeDiff("MulticastSendDelay", this.bean.getMulticastSendDelay(), otherTyped.getMulticastSendDelay(), false);
            this.computeDiff("MulticastTTL", this.bean.getMulticastTTL(), otherTyped.getMulticastTTL(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NumberOfServersInClusterAddress", this.bean.getNumberOfServersInClusterAddress(), otherTyped.getNumberOfServersInClusterAddress(), false);
            this.computeSubDiff("OverloadProtection", this.bean.getOverloadProtection(), otherTyped.getOverloadProtection());
            this.computeDiff("PersistSessionsOnShutdown", this.bean.getPersistSessionsOnShutdown(), otherTyped.getPersistSessionsOnShutdown(), false);
            this.computeDiff("RemoteClusterAddress", this.bean.getRemoteClusterAddress(), otherTyped.getRemoteClusterAddress(), false);
            this.computeDiff("ReplicationChannel", this.bean.getReplicationChannel(), otherTyped.getReplicationChannel(), false);
            this.computeDiff("ServiceActivationRequestResponseTimeout", this.bean.getServiceActivationRequestResponseTimeout(), otherTyped.getServiceActivationRequestResponseTimeout(), false);
            this.computeDiff("ServiceAgeThresholdSeconds", this.bean.getServiceAgeThresholdSeconds(), otherTyped.getServiceAgeThresholdSeconds(), true);
            this.computeDiff("SessionFlushInterval", this.bean.getSessionFlushInterval(), otherTyped.getSessionFlushInterval(), true);
            this.computeDiff("SessionFlushThreshold", this.bean.getSessionFlushThreshold(), otherTyped.getSessionFlushThreshold(), true);
            this.computeDiff("SessionStateQueryRequestTimeout", this.bean.getSessionStateQueryRequestTimeout(), otherTyped.getSessionStateQueryRequestTimeout(), true);
            this.computeDiff("SingletonSQLQueryHelper", this.bean.getSingletonSQLQueryHelper(), otherTyped.getSingletonSQLQueryHelper(), false);
            this.computeDiff("SingletonServiceRequestTimeout", this.bean.getSingletonServiceRequestTimeout(), otherTyped.getSingletonServiceRequestTimeout(), true);
            this.computeDiff("SiteName", this.bean.getSiteName(), otherTyped.getSiteName(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("TxnAffinityEnabled", this.bean.getTxnAffinityEnabled(), otherTyped.getTxnAffinityEnabled(), false);
            this.computeDiff("UnicastDiscoveryPeriodMillis", this.bean.getUnicastDiscoveryPeriodMillis(), otherTyped.getUnicastDiscoveryPeriodMillis(), true);
            this.computeDiff("UnicastReadTimeout", this.bean.getUnicastReadTimeout(), otherTyped.getUnicastReadTimeout(), false);
            this.computeDiff("WANSessionPersistenceTableName", this.bean.getWANSessionPersistenceTableName(), otherTyped.getWANSessionPersistenceTableName(), false);
            this.computeDiff("ClientCertProxyEnabled", this.bean.isClientCertProxyEnabled(), otherTyped.isClientCertProxyEnabled(), false);
            this.computeDiff("ConcurrentSingletonActivationEnabled", this.bean.isConcurrentSingletonActivationEnabled(), otherTyped.isConcurrentSingletonActivationEnabled(), false);
            this.computeDiff("HttpTraceSupportEnabled", this.bean.isHttpTraceSupportEnabled(), otherTyped.isHttpTraceSupportEnabled(), false);
            this.computeDiff("MemberDeathDetectorEnabled", this.bean.isMemberDeathDetectorEnabled(), otherTyped.isMemberDeathDetectorEnabled(), false);
            this.computeDiff("MessageOrderingEnabled", this.bean.isMessageOrderingEnabled(), otherTyped.isMessageOrderingEnabled(), false);
            this.computeDiff("OneWayRmiForReplicationEnabled", this.bean.isOneWayRmiForReplicationEnabled(), otherTyped.isOneWayRmiForReplicationEnabled(), false);
            this.computeDiff("ReplicationTimeoutEnabled", this.bean.isReplicationTimeoutEnabled(), otherTyped.isReplicationTimeoutEnabled(), false);
            this.computeDiff("SecureReplicationEnabled", this.bean.isSecureReplicationEnabled(), otherTyped.isSecureReplicationEnabled(), false);
            this.computeDiff("SessionLazyDeserializationEnabled", this.bean.isSessionLazyDeserializationEnabled(), otherTyped.isSessionLazyDeserializationEnabled(), true);
            this.computeDiff("SessionStateQueryProtocolEnabled", this.bean.isSessionStateQueryProtocolEnabled(), otherTyped.isSessionStateQueryProtocolEnabled(), true);
            this.computeDiff("WeblogicPluginEnabled", this.bean.isWeblogicPluginEnabled(), otherTyped.isWeblogicPluginEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClusterMBeanImpl original = (ClusterMBeanImpl)event.getSourceBean();
            ClusterMBeanImpl proposed = (ClusterMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdditionalAutoMigrationAttempts")) {
                  original.setAdditionalAutoMigrationAttempts(proposed.getAdditionalAutoMigrationAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 55);
               } else if (prop.equals("AsyncSessionQueueTimeout")) {
                  original.setAsyncSessionQueueTimeout(proposed.getAsyncSessionQueueTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("AutoMigrationTableCreationDDLFile")) {
                  original.setAutoMigrationTableCreationDDLFile(proposed.getAutoMigrationTableCreationDDLFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 54);
               } else if (prop.equals("AutoMigrationTableCreationPolicy")) {
                  original.setAutoMigrationTableCreationPolicy(proposed.getAutoMigrationTableCreationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 53);
               } else if (prop.equals("AutoMigrationTableName")) {
                  original.setAutoMigrationTableName(proposed.getAutoMigrationTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("CandidateMachinesForMigratableServers")) {
                  original.setCandidateMachinesForMigratableServersAsString(proposed.getCandidateMachinesForMigratableServersAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("ClusterAddress")) {
                  original.setClusterAddress(proposed.getClusterAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ClusterBroadcastChannel")) {
                  original.setClusterBroadcastChannel(proposed.getClusterBroadcastChannel());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ClusterMessagingMode")) {
                  original.setClusterMessagingMode(proposed.getClusterMessagingMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ClusterType")) {
                  original.setClusterType(proposed.getClusterType());
                  original._conditionalUnset(update.isUnsetUpdate(), 50);
               } else if (prop.equals("CoherenceClusterSystemResource")) {
                  original.setCoherenceClusterSystemResourceAsString(proposed.getCoherenceClusterSystemResourceAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 72);
               } else if (prop.equals("CoherenceTier")) {
                  if (type == 2) {
                     original.setCoherenceTier((CoherenceTierMBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceTier()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceTier", original.getCoherenceTier());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 73);
               } else if (prop.equals("ConsensusParticipants")) {
                  original.setConsensusParticipants(proposed.getConsensusParticipants());
                  original._conditionalUnset(update.isUnsetUpdate(), 58);
               } else if (prop.equals("DataSourceForAutomaticMigration")) {
                  original.setDataSourceForAutomaticMigrationAsString(proposed.getDataSourceForAutomaticMigrationAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("DataSourceForJobScheduler")) {
                  original.setDataSourceForJobSchedulerAsString(proposed.getDataSourceForJobSchedulerAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("DataSourceForSessionPersistence")) {
                  original.setDataSourceForSessionPersistenceAsString(proposed.getDataSourceForSessionPersistenceAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("DatabaseLeasingBasisConnectionRetryCount")) {
                  original.setDatabaseLeasingBasisConnectionRetryCount(proposed.getDatabaseLeasingBasisConnectionRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 77);
               } else if (prop.equals("DatabaseLeasingBasisConnectionRetryDelay")) {
                  original.setDatabaseLeasingBasisConnectionRetryDelay(proposed.getDatabaseLeasingBasisConnectionRetryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 78);
               } else if (prop.equals("DatabaseLessLeasingBasis")) {
                  if (type == 2) {
                     original.setDatabaseLessLeasingBasis((DatabaseLessLeasingBasisMBean)this.createCopy((AbstractDescriptorBean)proposed.getDatabaseLessLeasingBasis()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DatabaseLessLeasingBasis", original.getDatabaseLessLeasingBasis());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 61);
               } else if (prop.equals("DeathDetectorHeartbeatPeriod")) {
                  original.setDeathDetectorHeartbeatPeriod(proposed.getDeathDetectorHeartbeatPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 70);
               } else if (prop.equals("DefaultLoadAlgorithm")) {
                  original.setDefaultLoadAlgorithm(proposed.getDefaultLoadAlgorithm());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DynamicServers")) {
                  if (type == 2) {
                     original.setDynamicServers((DynamicServersMBean)this.createCopy((AbstractDescriptorBean)proposed.getDynamicServers()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DynamicServers", original.getDynamicServers());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 75);
               } else if (prop.equals("FencingGracePeriodMillis")) {
                  original.setFencingGracePeriodMillis(proposed.getFencingGracePeriodMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 47);
               } else if (prop.equals("FrontendHTTPPort")) {
                  original.setFrontendHTTPPort(proposed.getFrontendHTTPPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("FrontendHTTPSPort")) {
                  original.setFrontendHTTPSPort(proposed.getFrontendHTTPSPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("FrontendHost")) {
                  original.setFrontendHost(proposed.getFrontendHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("GreedySessionFlushInterval")) {
                  original.setGreedySessionFlushInterval(proposed.getGreedySessionFlushInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("HTTPPingRetryCount")) {
                  original.setHTTPPingRetryCount(proposed.getHTTPPingRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 62);
               } else if (prop.equals("HealthCheckIntervalMillis")) {
                  original.setHealthCheckIntervalMillis(proposed.getHealthCheckIntervalMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("HealthCheckPeriodsUntilFencing")) {
                  original.setHealthCheckPeriodsUntilFencing(proposed.getHealthCheckPeriodsUntilFencing());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("IdlePeriodsUntilTimeout")) {
                  original.setIdlePeriodsUntilTimeout(proposed.getIdlePeriodsUntilTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("InterClusterCommLinkHealthCheckInterval")) {
                  original.setInterClusterCommLinkHealthCheckInterval(proposed.getInterClusterCommLinkHealthCheckInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("JTACluster")) {
                  if (type == 2) {
                     original.setJTACluster((JTAClusterMBean)this.createCopy((AbstractDescriptorBean)proposed.getJTACluster()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JTACluster", original.getJTACluster());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 74);
               } else if (prop.equals("JobSchedulerTableName")) {
                  original.setJobSchedulerTableName(proposed.getJobSchedulerTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("MaxServerCountForHttpPing")) {
                  original.setMaxServerCountForHttpPing(proposed.getMaxServerCountForHttpPing());
                  original._conditionalUnset(update.isUnsetUpdate(), 63);
               } else if (prop.equals("MemberWarmupTimeoutSeconds")) {
                  original.setMemberWarmupTimeoutSeconds(proposed.getMemberWarmupTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (!prop.equals("MigratableTargets")) {
                  if (prop.equals("MigrationBasis")) {
                     original.setMigrationBasis(proposed.getMigrationBasis());
                     original._conditionalUnset(update.isUnsetUpdate(), 57);
                  } else if (prop.equals("MillisToSleepBetweenAutoMigrationAttempts")) {
                     original.setMillisToSleepBetweenAutoMigrationAttempts(proposed.getMillisToSleepBetweenAutoMigrationAttempts());
                     original._conditionalUnset(update.isUnsetUpdate(), 56);
                  } else if (prop.equals("MulticastAddress")) {
                     original.setMulticastAddress(proposed.getMulticastAddress());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("MulticastBufferSize")) {
                     original.setMulticastBufferSize(proposed.getMulticastBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("MulticastDataEncryption")) {
                     original.setMulticastDataEncryption(proposed.getMulticastDataEncryption());
                     original._conditionalUnset(update.isUnsetUpdate(), 51);
                  } else if (prop.equals("MulticastPort")) {
                     original.setMulticastPort(proposed.getMulticastPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("MulticastSendDelay")) {
                     original.setMulticastSendDelay(proposed.getMulticastSendDelay());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("MulticastTTL")) {
                     original.setMulticastTTL(proposed.getMulticastTTL());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("NumberOfServersInClusterAddress")) {
                     original.setNumberOfServersInClusterAddress(proposed.getNumberOfServersInClusterAddress());
                     original._conditionalUnset(update.isUnsetUpdate(), 49);
                  } else if (prop.equals("OverloadProtection")) {
                     if (type == 2) {
                        original.setOverloadProtection((OverloadProtectionMBean)this.createCopy((AbstractDescriptorBean)proposed.getOverloadProtection()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("OverloadProtection", original.getOverloadProtection());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 60);
                  } else if (prop.equals("PersistSessionsOnShutdown")) {
                     original.setPersistSessionsOnShutdown(proposed.getPersistSessionsOnShutdown());
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
                  } else if (prop.equals("RemoteClusterAddress")) {
                     original.setRemoteClusterAddress(proposed.getRemoteClusterAddress());
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  } else if (prop.equals("ReplicationChannel")) {
                     original.setReplicationChannel(proposed.getReplicationChannel());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (!prop.equals("ServerNames") && !prop.equals("Servers")) {
                     if (prop.equals("ServiceActivationRequestResponseTimeout")) {
                        original.setServiceActivationRequestResponseTimeout(proposed.getServiceActivationRequestResponseTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 81);
                     } else if (prop.equals("ServiceAgeThresholdSeconds")) {
                        original.setServiceAgeThresholdSeconds(proposed.getServiceAgeThresholdSeconds());
                        original._conditionalUnset(update.isUnsetUpdate(), 21);
                     } else if (prop.equals("SessionFlushInterval")) {
                        original.setSessionFlushInterval(proposed.getSessionFlushInterval());
                        original._conditionalUnset(update.isUnsetUpdate(), 41);
                     } else if (prop.equals("SessionFlushThreshold")) {
                        original.setSessionFlushThreshold(proposed.getSessionFlushThreshold());
                        original._conditionalUnset(update.isUnsetUpdate(), 42);
                     } else if (prop.equals("SessionStateQueryRequestTimeout")) {
                        original.setSessionStateQueryRequestTimeout(proposed.getSessionStateQueryRequestTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 80);
                     } else if (prop.equals("SingletonSQLQueryHelper")) {
                        original.setSingletonSQLQueryHelper(proposed.getSingletonSQLQueryHelper());
                        original._conditionalUnset(update.isUnsetUpdate(), 48);
                     } else if (prop.equals("SingletonServiceRequestTimeout")) {
                        original.setSingletonServiceRequestTimeout(proposed.getSingletonServiceRequestTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 82);
                     } else if (prop.equals("SiteName")) {
                        original.setSiteName(proposed.getSiteName());
                        original._conditionalUnset(update.isUnsetUpdate(), 84);
                     } else if (prop.equals("Tags")) {
                        if (type == 2) {
                           update.resetAddedObject(update.getAddedObject());
                           original.addTag((String)update.getAddedObject());
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeTag((String)update.getRemovedObject());
                        }

                        if (original.getTags() == null || original.getTags().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 9);
                        }
                     } else if (prop.equals("TxnAffinityEnabled")) {
                        original.setTxnAffinityEnabled(proposed.getTxnAffinityEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 76);
                     } else if (prop.equals("UnicastDiscoveryPeriodMillis")) {
                        original.setUnicastDiscoveryPeriodMillis(proposed.getUnicastDiscoveryPeriodMillis());
                        original._conditionalUnset(update.isUnsetUpdate(), 65);
                     } else if (prop.equals("UnicastReadTimeout")) {
                        original.setUnicastReadTimeout(proposed.getUnicastReadTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 66);
                     } else if (prop.equals("WANSessionPersistenceTableName")) {
                        original.setWANSessionPersistenceTableName(proposed.getWANSessionPersistenceTableName());
                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("ClientCertProxyEnabled")) {
                        original.setClientCertProxyEnabled(proposed.isClientCertProxyEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("ConcurrentSingletonActivationEnabled")) {
                        original.setConcurrentSingletonActivationEnabled(proposed.isConcurrentSingletonActivationEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 83);
                     } else if (!prop.equals("DynamicallyCreated")) {
                        if (prop.equals("HttpTraceSupportEnabled")) {
                           original.setHttpTraceSupportEnabled(proposed.isHttpTraceSupportEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 26);
                        } else if (prop.equals("MemberDeathDetectorEnabled")) {
                           original.setMemberDeathDetectorEnabled(proposed.isMemberDeathDetectorEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 71);
                        } else if (prop.equals("MessageOrderingEnabled")) {
                           original.setMessageOrderingEnabled(proposed.isMessageOrderingEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 67);
                        } else if (prop.equals("OneWayRmiForReplicationEnabled")) {
                           original.setOneWayRmiForReplicationEnabled(proposed.isOneWayRmiForReplicationEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 68);
                        } else if (prop.equals("ReplicationTimeoutEnabled")) {
                           original.setReplicationTimeoutEnabled(proposed.isReplicationTimeoutEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 59);
                        } else if (prop.equals("SecureReplicationEnabled")) {
                           original.setSecureReplicationEnabled(proposed.isSecureReplicationEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 64);
                        } else if (prop.equals("SessionLazyDeserializationEnabled")) {
                           original.setSessionLazyDeserializationEnabled(proposed.isSessionLazyDeserializationEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 69);
                        } else if (prop.equals("SessionStateQueryProtocolEnabled")) {
                           original.setSessionStateQueryProtocolEnabled(proposed.isSessionStateQueryProtocolEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 79);
                        } else if (prop.equals("WeblogicPluginEnabled")) {
                           original.setWeblogicPluginEnabled(proposed.isWeblogicPluginEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 23);
                        } else {
                           super.applyPropertyUpdate(event, update);
                        }
                     }
                  }
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            ClusterMBeanImpl copy = (ClusterMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdditionalAutoMigrationAttempts")) && this.bean.isAdditionalAutoMigrationAttemptsSet()) {
               copy.setAdditionalAutoMigrationAttempts(this.bean.getAdditionalAutoMigrationAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("AsyncSessionQueueTimeout")) && this.bean.isAsyncSessionQueueTimeoutSet()) {
               copy.setAsyncSessionQueueTimeout(this.bean.getAsyncSessionQueueTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoMigrationTableCreationDDLFile")) && this.bean.isAutoMigrationTableCreationDDLFileSet()) {
               copy.setAutoMigrationTableCreationDDLFile(this.bean.getAutoMigrationTableCreationDDLFile());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoMigrationTableCreationPolicy")) && this.bean.isAutoMigrationTableCreationPolicySet()) {
               copy.setAutoMigrationTableCreationPolicy(this.bean.getAutoMigrationTableCreationPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoMigrationTableName")) && this.bean.isAutoMigrationTableNameSet()) {
               copy.setAutoMigrationTableName(this.bean.getAutoMigrationTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("CandidateMachinesForMigratableServers")) && this.bean.isCandidateMachinesForMigratableServersSet()) {
               copy._unSet(copy, 43);
               copy.setCandidateMachinesForMigratableServersAsString(this.bean.getCandidateMachinesForMigratableServersAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterAddress")) && this.bean.isClusterAddressSet()) {
               copy.setClusterAddress(this.bean.getClusterAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterBroadcastChannel")) && this.bean.isClusterBroadcastChannelSet()) {
               copy.setClusterBroadcastChannel(this.bean.getClusterBroadcastChannel());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterMessagingMode")) && this.bean.isClusterMessagingModeSet()) {
               copy.setClusterMessagingMode(this.bean.getClusterMessagingMode());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterType")) && this.bean.isClusterTypeSet()) {
               copy.setClusterType(this.bean.getClusterType());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterSystemResource")) && this.bean.isCoherenceClusterSystemResourceSet()) {
               copy._unSet(copy, 72);
               copy.setCoherenceClusterSystemResourceAsString(this.bean.getCoherenceClusterSystemResourceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceTier")) && this.bean.isCoherenceTierSet() && !copy._isSet(73)) {
               Object o = this.bean.getCoherenceTier();
               copy.setCoherenceTier((CoherenceTierMBean)null);
               copy.setCoherenceTier(o == null ? null : (CoherenceTierMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConsensusParticipants")) && this.bean.isConsensusParticipantsSet()) {
               copy.setConsensusParticipants(this.bean.getConsensusParticipants());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceForAutomaticMigration")) && this.bean.isDataSourceForAutomaticMigrationSet()) {
               copy._unSet(copy, 44);
               copy.setDataSourceForAutomaticMigrationAsString(this.bean.getDataSourceForAutomaticMigrationAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceForJobScheduler")) && this.bean.isDataSourceForJobSchedulerSet()) {
               copy._unSet(copy, 36);
               copy.setDataSourceForJobSchedulerAsString(this.bean.getDataSourceForJobSchedulerAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceForSessionPersistence")) && this.bean.isDataSourceForSessionPersistenceSet()) {
               copy._unSet(copy, 35);
               copy.setDataSourceForSessionPersistenceAsString(this.bean.getDataSourceForSessionPersistenceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DatabaseLeasingBasisConnectionRetryCount")) && this.bean.isDatabaseLeasingBasisConnectionRetryCountSet()) {
               copy.setDatabaseLeasingBasisConnectionRetryCount(this.bean.getDatabaseLeasingBasisConnectionRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("DatabaseLeasingBasisConnectionRetryDelay")) && this.bean.isDatabaseLeasingBasisConnectionRetryDelaySet()) {
               copy.setDatabaseLeasingBasisConnectionRetryDelay(this.bean.getDatabaseLeasingBasisConnectionRetryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("DatabaseLessLeasingBasis")) && this.bean.isDatabaseLessLeasingBasisSet() && !copy._isSet(61)) {
               Object o = this.bean.getDatabaseLessLeasingBasis();
               copy.setDatabaseLessLeasingBasis((DatabaseLessLeasingBasisMBean)null);
               copy.setDatabaseLessLeasingBasis(o == null ? null : (DatabaseLessLeasingBasisMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeathDetectorHeartbeatPeriod")) && this.bean.isDeathDetectorHeartbeatPeriodSet()) {
               copy.setDeathDetectorHeartbeatPeriod(this.bean.getDeathDetectorHeartbeatPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultLoadAlgorithm")) && this.bean.isDefaultLoadAlgorithmSet()) {
               copy.setDefaultLoadAlgorithm(this.bean.getDefaultLoadAlgorithm());
            }

            if ((excludeProps == null || !excludeProps.contains("DynamicServers")) && this.bean.isDynamicServersSet() && !copy._isSet(75)) {
               Object o = this.bean.getDynamicServers();
               copy.setDynamicServers((DynamicServersMBean)null);
               copy.setDynamicServers(o == null ? null : (DynamicServersMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FencingGracePeriodMillis")) && this.bean.isFencingGracePeriodMillisSet()) {
               copy.setFencingGracePeriodMillis(this.bean.getFencingGracePeriodMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("FrontendHTTPPort")) && this.bean.isFrontendHTTPPortSet()) {
               copy.setFrontendHTTPPort(this.bean.getFrontendHTTPPort());
            }

            if ((excludeProps == null || !excludeProps.contains("FrontendHTTPSPort")) && this.bean.isFrontendHTTPSPortSet()) {
               copy.setFrontendHTTPSPort(this.bean.getFrontendHTTPSPort());
            }

            if ((excludeProps == null || !excludeProps.contains("FrontendHost")) && this.bean.isFrontendHostSet()) {
               copy.setFrontendHost(this.bean.getFrontendHost());
            }

            if ((excludeProps == null || !excludeProps.contains("GreedySessionFlushInterval")) && this.bean.isGreedySessionFlushIntervalSet()) {
               copy.setGreedySessionFlushInterval(this.bean.getGreedySessionFlushInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("HTTPPingRetryCount")) && this.bean.isHTTPPingRetryCountSet()) {
               copy.setHTTPPingRetryCount(this.bean.getHTTPPingRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("HealthCheckIntervalMillis")) && this.bean.isHealthCheckIntervalMillisSet()) {
               copy.setHealthCheckIntervalMillis(this.bean.getHealthCheckIntervalMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("HealthCheckPeriodsUntilFencing")) && this.bean.isHealthCheckPeriodsUntilFencingSet()) {
               copy.setHealthCheckPeriodsUntilFencing(this.bean.getHealthCheckPeriodsUntilFencing());
            }

            if ((excludeProps == null || !excludeProps.contains("IdlePeriodsUntilTimeout")) && this.bean.isIdlePeriodsUntilTimeoutSet()) {
               copy.setIdlePeriodsUntilTimeout(this.bean.getIdlePeriodsUntilTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("InterClusterCommLinkHealthCheckInterval")) && this.bean.isInterClusterCommLinkHealthCheckIntervalSet()) {
               copy.setInterClusterCommLinkHealthCheckInterval(this.bean.getInterClusterCommLinkHealthCheckInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("JTACluster")) && this.bean.isJTAClusterSet() && !copy._isSet(74)) {
               Object o = this.bean.getJTACluster();
               copy.setJTACluster((JTAClusterMBean)null);
               copy.setJTACluster(o == null ? null : (JTAClusterMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JobSchedulerTableName")) && this.bean.isJobSchedulerTableNameSet()) {
               copy.setJobSchedulerTableName(this.bean.getJobSchedulerTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxServerCountForHttpPing")) && this.bean.isMaxServerCountForHttpPingSet()) {
               copy.setMaxServerCountForHttpPing(this.bean.getMaxServerCountForHttpPing());
            }

            if ((excludeProps == null || !excludeProps.contains("MemberWarmupTimeoutSeconds")) && this.bean.isMemberWarmupTimeoutSecondsSet()) {
               copy.setMemberWarmupTimeoutSeconds(this.bean.getMemberWarmupTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("MigrationBasis")) && this.bean.isMigrationBasisSet()) {
               copy.setMigrationBasis(this.bean.getMigrationBasis());
            }

            if ((excludeProps == null || !excludeProps.contains("MillisToSleepBetweenAutoMigrationAttempts")) && this.bean.isMillisToSleepBetweenAutoMigrationAttemptsSet()) {
               copy.setMillisToSleepBetweenAutoMigrationAttempts(this.bean.getMillisToSleepBetweenAutoMigrationAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastAddress")) && this.bean.isMulticastAddressSet()) {
               copy.setMulticastAddress(this.bean.getMulticastAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastBufferSize")) && this.bean.isMulticastBufferSizeSet()) {
               copy.setMulticastBufferSize(this.bean.getMulticastBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastDataEncryption")) && this.bean.isMulticastDataEncryptionSet()) {
               copy.setMulticastDataEncryption(this.bean.getMulticastDataEncryption());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastPort")) && this.bean.isMulticastPortSet()) {
               copy.setMulticastPort(this.bean.getMulticastPort());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastSendDelay")) && this.bean.isMulticastSendDelaySet()) {
               copy.setMulticastSendDelay(this.bean.getMulticastSendDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastTTL")) && this.bean.isMulticastTTLSet()) {
               copy.setMulticastTTL(this.bean.getMulticastTTL());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NumberOfServersInClusterAddress")) && this.bean.isNumberOfServersInClusterAddressSet()) {
               copy.setNumberOfServersInClusterAddress(this.bean.getNumberOfServersInClusterAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("OverloadProtection")) && this.bean.isOverloadProtectionSet() && !copy._isSet(60)) {
               Object o = this.bean.getOverloadProtection();
               copy.setOverloadProtection((OverloadProtectionMBean)null);
               copy.setOverloadProtection(o == null ? null : (OverloadProtectionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PersistSessionsOnShutdown")) && this.bean.isPersistSessionsOnShutdownSet()) {
               copy.setPersistSessionsOnShutdown(this.bean.getPersistSessionsOnShutdown());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteClusterAddress")) && this.bean.isRemoteClusterAddressSet()) {
               copy.setRemoteClusterAddress(this.bean.getRemoteClusterAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplicationChannel")) && this.bean.isReplicationChannelSet()) {
               copy.setReplicationChannel(this.bean.getReplicationChannel());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceActivationRequestResponseTimeout")) && this.bean.isServiceActivationRequestResponseTimeoutSet()) {
               copy.setServiceActivationRequestResponseTimeout(this.bean.getServiceActivationRequestResponseTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceAgeThresholdSeconds")) && this.bean.isServiceAgeThresholdSecondsSet()) {
               copy.setServiceAgeThresholdSeconds(this.bean.getServiceAgeThresholdSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionFlushInterval")) && this.bean.isSessionFlushIntervalSet()) {
               copy.setSessionFlushInterval(this.bean.getSessionFlushInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionFlushThreshold")) && this.bean.isSessionFlushThresholdSet()) {
               copy.setSessionFlushThreshold(this.bean.getSessionFlushThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionStateQueryRequestTimeout")) && this.bean.isSessionStateQueryRequestTimeoutSet()) {
               copy.setSessionStateQueryRequestTimeout(this.bean.getSessionStateQueryRequestTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SingletonSQLQueryHelper")) && this.bean.isSingletonSQLQueryHelperSet()) {
               copy.setSingletonSQLQueryHelper(this.bean.getSingletonSQLQueryHelper());
            }

            if ((excludeProps == null || !excludeProps.contains("SingletonServiceRequestTimeout")) && this.bean.isSingletonServiceRequestTimeoutSet()) {
               copy.setSingletonServiceRequestTimeout(this.bean.getSingletonServiceRequestTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SiteName")) && this.bean.isSiteNameSet()) {
               copy.setSiteName(this.bean.getSiteName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("TxnAffinityEnabled")) && this.bean.isTxnAffinityEnabledSet()) {
               copy.setTxnAffinityEnabled(this.bean.getTxnAffinityEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastDiscoveryPeriodMillis")) && this.bean.isUnicastDiscoveryPeriodMillisSet()) {
               copy.setUnicastDiscoveryPeriodMillis(this.bean.getUnicastDiscoveryPeriodMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastReadTimeout")) && this.bean.isUnicastReadTimeoutSet()) {
               copy.setUnicastReadTimeout(this.bean.getUnicastReadTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("WANSessionPersistenceTableName")) && this.bean.isWANSessionPersistenceTableNameSet()) {
               copy.setWANSessionPersistenceTableName(this.bean.getWANSessionPersistenceTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertProxyEnabled")) && this.bean.isClientCertProxyEnabledSet()) {
               copy.setClientCertProxyEnabled(this.bean.isClientCertProxyEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ConcurrentSingletonActivationEnabled")) && this.bean.isConcurrentSingletonActivationEnabledSet()) {
               copy.setConcurrentSingletonActivationEnabled(this.bean.isConcurrentSingletonActivationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpTraceSupportEnabled")) && this.bean.isHttpTraceSupportEnabledSet()) {
               copy.setHttpTraceSupportEnabled(this.bean.isHttpTraceSupportEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MemberDeathDetectorEnabled")) && this.bean.isMemberDeathDetectorEnabledSet()) {
               copy.setMemberDeathDetectorEnabled(this.bean.isMemberDeathDetectorEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageOrderingEnabled")) && this.bean.isMessageOrderingEnabledSet()) {
               copy.setMessageOrderingEnabled(this.bean.isMessageOrderingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OneWayRmiForReplicationEnabled")) && this.bean.isOneWayRmiForReplicationEnabledSet()) {
               copy.setOneWayRmiForReplicationEnabled(this.bean.isOneWayRmiForReplicationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplicationTimeoutEnabled")) && this.bean.isReplicationTimeoutEnabledSet()) {
               copy.setReplicationTimeoutEnabled(this.bean.isReplicationTimeoutEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SecureReplicationEnabled")) && this.bean.isSecureReplicationEnabledSet()) {
               copy.setSecureReplicationEnabled(this.bean.isSecureReplicationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionLazyDeserializationEnabled")) && this.bean.isSessionLazyDeserializationEnabledSet()) {
               copy.setSessionLazyDeserializationEnabled(this.bean.isSessionLazyDeserializationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionStateQueryProtocolEnabled")) && this.bean.isSessionStateQueryProtocolEnabledSet()) {
               copy.setSessionStateQueryProtocolEnabled(this.bean.isSessionStateQueryProtocolEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicPluginEnabled")) && this.bean.isWeblogicPluginEnabledSet()) {
               copy.setWeblogicPluginEnabled(this.bean.isWeblogicPluginEnabled());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getCandidateMachinesForMigratableServers(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterSystemResource(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceTier(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSourceForAutomaticMigration(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSourceForJobScheduler(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSourceForSessionPersistence(), clazz, annotation);
         this.inferSubTree(this.bean.getDatabaseLessLeasingBasis(), clazz, annotation);
         this.inferSubTree(this.bean.getDynamicServers(), clazz, annotation);
         this.inferSubTree(this.bean.getJTACluster(), clazz, annotation);
         this.inferSubTree(this.bean.getMigratableTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getOverloadProtection(), clazz, annotation);
         this.inferSubTree(this.bean.getServers(), clazz, annotation);
      }
   }
}
