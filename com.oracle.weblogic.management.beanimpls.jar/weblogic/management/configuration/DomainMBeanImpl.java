package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Domain;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DomainMBeanImpl extends ConfigurationPropertiesMBeanImpl implements DomainMBean, Serializable {
   private boolean _Active;
   private AdminConsoleMBean _AdminConsole;
   private AdminServerMBean _AdminServerMBean;
   private String _AdminServerName;
   private boolean _AdministrationMBeanAuditingEnabled;
   private int _AdministrationPort;
   private boolean _AdministrationPortEnabled;
   private String _AdministrationProtocol;
   private AppDeploymentMBean[] _AppDeployments;
   private ApplicationMBean[] _Applications;
   private int _ArchiveConfigurationCount;
   private boolean _AutoConfigurationSaveEnabled;
   private boolean _AutoDeployForSubmodulesEnabled;
   private BatchConfigMBean _BatchConfig;
   private String _BatchJobsDataSourceJndiName;
   private String _BatchJobsExecutorServiceName;
   private BridgeDestinationMBean[] _BridgeDestinations;
   private CalloutMBean[] _Callouts;
   private CdiContainerMBean _CdiContainer;
   private boolean _ClusterConstraintsEnabled;
   private ClusterMBean[] _Clusters;
   private CoherenceClusterSystemResourceMBean[] _CoherenceClusterSystemResources;
   private CoherenceManagementClusterMBean[] _CoherenceManagementClusters;
   private CoherenceServerMBean[] _CoherenceServers;
   private boolean _ConfigBackupEnabled;
   private String _ConfigurationAuditType;
   private String _ConfigurationVersion;
   private String _ConsoleContextPath;
   private boolean _ConsoleEnabled;
   private String _ConsoleExtensionDirectory;
   private CustomResourceMBean[] _CustomResources;
   private boolean _DBPassiveMode;
   private int _DBPassiveModeGracePeriodSeconds;
   private DebugPatchesMBean _DebugPatches;
   private DeploymentConfigurationMBean _DeploymentConfiguration;
   private DeploymentMBean[] _Deployments;
   private boolean _DiagnosticContextCompatibilityModeEnabled;
   private DomainLibraryMBean[] _DomainLibraries;
   private String _DomainVersion;
   private boolean _DynamicallyCreated;
   private EJBContainerMBean _EJBContainer;
   private EmbeddedLDAPMBean _EmbeddedLDAP;
   private boolean _EnableEECompliantClassloadingForEmbeddedAdapters;
   private boolean _ExalogicOptimizationsEnabled;
   private FileStoreMBean[] _FileStores;
   private FileT3MBean[] _FileT3s;
   private ForeignJMSConnectionFactoryMBean[] _ForeignJMSConnectionFactories;
   private ForeignJMSDestinationMBean[] _ForeignJMSDestinations;
   private ForeignJMSServerMBean[] _ForeignJMSServers;
   private ForeignJNDIProviderMBean[] _ForeignJNDIProviders;
   private boolean _GuardianEnabled;
   private String _InstalledSoftwareVersion;
   private InterceptorsMBean _Interceptors;
   private AppDeploymentMBean[] _InternalAppDeployments;
   private boolean _InternalAppsDeployOnDemandEnabled;
   private LibraryMBean[] _InternalLibraries;
   private JDBCStoreMBean[] _JDBCStores;
   private JDBCSystemResourceMBean[] _JDBCSystemResources;
   private JMSBridgeDestinationMBean[] _JMSBridgeDestinations;
   private JMSConnectionConsumerMBean[] _JMSConnectionConsumers;
   private JMSConnectionFactoryMBean[] _JMSConnectionFactories;
   private JMSDestinationKeyMBean[] _JMSDestinationKeys;
   private JMSDestinationMBean[] _JMSDestinations;
   private JMSDistributedQueueMemberMBean[] _JMSDistributedQueueMembers;
   private JMSDistributedQueueMBean[] _JMSDistributedQueues;
   private JMSDistributedTopicMemberMBean[] _JMSDistributedTopicMembers;
   private JMSDistributedTopicMBean[] _JMSDistributedTopics;
   private JMSFileStoreMBean[] _JMSFileStores;
   private JMSJDBCStoreMBean[] _JMSJDBCStores;
   private JMSQueueMBean[] _JMSQueues;
   private JMSServerMBean[] _JMSServers;
   private JMSSessionPoolMBean[] _JMSSessionPools;
   private JMSStoreMBean[] _JMSStores;
   private JMSSystemResourceMBean[] _JMSSystemResources;
   private JMSTemplateMBean[] _JMSTemplates;
   private JMSTopicMBean[] _JMSTopics;
   private JMXMBean _JMX;
   private JPAMBean _JPA;
   private JTAMBean _JTA;
   private boolean _JavaServiceConsoleEnabled;
   private boolean _JavaServiceEnabled;
   private JoltConnectionPoolMBean[] _JoltConnectionPools;
   private long _LastModificationTime;
   private LibraryMBean[] _Libraries;
   private LifecycleManagerConfigMBean _LifecycleManagerConfig;
   private LifecycleManagerEndPointMBean[] _LifecycleManagerEndPoints;
   private LogMBean _Log;
   private LogFilterMBean[] _LogFilters;
   private boolean _LogFormatCompatibilityEnabled;
   private MachineMBean[] _Machines;
   private MailSessionMBean[] _MailSessions;
   private ManagedExecutorServiceTemplateMBean[] _ManagedExecutorServiceTemplates;
   private ManagedExecutorServiceMBean[] _ManagedExecutorServices;
   private ManagedScheduledExecutorServiceTemplateMBean[] _ManagedScheduledExecutorServiceTemplates;
   private ManagedScheduledExecutorServiceMBean[] _ManagedScheduledExecutorServices;
   private ManagedThreadFactoryMBean[] _ManagedThreadFactories;
   private ManagedThreadFactoryTemplateMBean[] _ManagedThreadFactoryTemplates;
   private int _MaxConcurrentLongRunningRequests;
   private int _MaxConcurrentNewThreads;
   private MessagingBridgeMBean[] _MessagingBridges;
   private MigratableRMIServiceMBean[] _MigratableRMIServices;
   private MigratableTargetMBean[] _MigratableTargets;
   private boolean _MsgIdPrefixCompatibilityEnabled;
   private String _Name;
   private NetworkChannelMBean[] _NetworkChannels;
   private boolean _OCMEnabled;
   private OptionalFeatureDeploymentMBean _OptionalFeatureDeployment;
   private OsgiFrameworkMBean[] _OsgiFrameworks;
   private boolean _ParallelDeployApplicationModules;
   private boolean _ParallelDeployApplications;
   private PartitionTemplateMBean[] _PartitionTemplates;
   private String _PartitionUriSpace;
   private PartitionWorkManagerMBean[] _PartitionWorkManagers;
   private PartitionMBean[] _Partitions;
   private PathServiceMBean[] _PathServices;
   private boolean _ProductionModeEnabled;
   private ReplicatedStoreMBean[] _ReplicatedStores;
   private ResourceGroupTemplateMBean[] _ResourceGroupTemplates;
   private ResourceGroupMBean[] _ResourceGroups;
   private ResourceManagementMBean _ResourceManagement;
   private RestfulManagementServicesMBean _RestfulManagementServices;
   private String _RootDirectory;
   private SAFAgentMBean[] _SAFAgents;
   private SNMPAgentMBean _SNMPAgent;
   private SNMPAgentDeploymentMBean[] _SNMPAgentDeployments;
   private SNMPAttributeChangeMBean[] _SNMPAttributeChanges;
   private SNMPCounterMonitorMBean[] _SNMPCounterMonitors;
   private SNMPGaugeMonitorMBean[] _SNMPGaugeMonitors;
   private SNMPLogFilterMBean[] _SNMPLogFilters;
   private SNMPProxyMBean[] _SNMPProxies;
   private SNMPStringMonitorMBean[] _SNMPStringMonitors;
   private SNMPTrapDestinationMBean[] _SNMPTrapDestinations;
   private SecurityConfigurationMBean _SecurityConfiguration;
   private SelfTuningMBean _SelfTuning;
   private int _ServerMigrationHistorySize;
   private ServerTemplateMBean[] _ServerTemplates;
   private ServerMBean[] _Servers;
   private int _ServiceMigrationHistorySize;
   private ShutdownClassMBean[] _ShutdownClasses;
   private SingletonServiceMBean[] _SingletonServices;
   private String _SiteName;
   private StartupClassMBean[] _StartupClasses;
   private SystemComponentConfigurationMBean[] _SystemComponentConfigurations;
   private SystemComponentMBean[] _SystemComponents;
   private SystemResourceMBean[] _SystemResources;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private VirtualHostMBean[] _VirtualHosts;
   private VirtualTargetMBean[] _VirtualTargets;
   private WLDFSystemResourceMBean[] _WLDFSystemResources;
   private WSReliableDeliveryPolicyMBean[] _WSReliableDeliveryPolicies;
   private WTCServerMBean[] _WTCServers;
   private WebAppContainerMBean _WebAppContainer;
   private WebserviceSecurityMBean[] _WebserviceSecurities;
   private WebserviceTestpageMBean _WebserviceTestpage;
   private XMLEntityCacheMBean[] _XMLEntityCaches;
   private XMLRegistryMBean[] _XMLRegistries;
   private transient Domain _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DomainMBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new Domain(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DomainMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new Domain(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DomainMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new Domain(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDomainVersion() {
      return this._DomainVersion;
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

   public boolean isDomainVersionInherited() {
      return false;
   }

   public boolean isDomainVersionSet() {
      return this._isSet(11);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setDomainVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      DomainValidator.validateVersionString(param0);
      String _oldVal = this._DomainVersion;
      this._DomainVersion = param0;
      this._postSet(11, _oldVal, param0);
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

   public long getLastModificationTime() {
      return this._LastModificationTime;
   }

   public boolean isLastModificationTimeInherited() {
      return false;
   }

   public boolean isLastModificationTimeSet() {
      return this._isSet(12);
   }

   public void setLastModificationTime(long param0) throws InvalidAttributeValueException {
      long _oldVal = this._LastModificationTime;
      this._LastModificationTime = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isActive() {
      return this._Active;
   }

   public boolean isActiveInherited() {
      return false;
   }

   public boolean isActiveSet() {
      return this._isSet(13);
   }

   public void setActive(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._Active;
      this._Active = param0;
      this._postSet(13, _oldVal, param0);
   }

   public SecurityConfigurationMBean getSecurityConfiguration() {
      return this._SecurityConfiguration;
   }

   public boolean isSecurityConfigurationInherited() {
      return false;
   }

   public boolean isSecurityConfigurationSet() {
      return this._isSet(14) || this._isAnythingSet((AbstractDescriptorBean)this.getSecurityConfiguration());
   }

   public void setSecurityConfiguration(SecurityConfigurationMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 14)) {
         this._postCreate(_child);
      }

      SecurityConfigurationMBean _oldVal = this._SecurityConfiguration;
      this._SecurityConfiguration = param0;
      this._postSet(14, _oldVal, param0);
   }

   public JTAMBean getJTA() {
      return this._JTA;
   }

   public boolean isJTAInherited() {
      return false;
   }

   public boolean isJTASet() {
      return this._isSet(15) || this._isAnythingSet((AbstractDescriptorBean)this.getJTA());
   }

   public void setJTA(JTAMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 15)) {
         this._postCreate(_child);
      }

      JTAMBean _oldVal = this._JTA;
      this._JTA = param0;
      this._postSet(15, _oldVal, param0);
   }

   public JPAMBean getJPA() {
      return this._JPA;
   }

   public boolean isJPAInherited() {
      return false;
   }

   public boolean isJPASet() {
      return this._isSet(16) || this._isAnythingSet((AbstractDescriptorBean)this.getJPA());
   }

   public void setJPA(JPAMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 16)) {
         this._postCreate(_child);
      }

      JPAMBean _oldVal = this._JPA;
      this._JPA = param0;
      this._postSet(16, _oldVal, param0);
   }

   public DeploymentConfigurationMBean getDeploymentConfiguration() {
      return this._DeploymentConfiguration;
   }

   public boolean isDeploymentConfigurationInherited() {
      return false;
   }

   public boolean isDeploymentConfigurationSet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getDeploymentConfiguration());
   }

   public void setDeploymentConfiguration(DeploymentConfigurationMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      DeploymentConfigurationMBean _oldVal = this._DeploymentConfiguration;
      this._DeploymentConfiguration = param0;
      this._postSet(17, _oldVal, param0);
   }

   public void addWTCServer(WTCServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         WTCServerMBean[] _new;
         if (this._isSet(18)) {
            _new = (WTCServerMBean[])((WTCServerMBean[])this._getHelper()._extendArray(this.getWTCServers(), WTCServerMBean.class, param0));
         } else {
            _new = new WTCServerMBean[]{param0};
         }

         try {
            this.setWTCServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCServerMBean[] getWTCServers() {
      return this._WTCServers;
   }

   public boolean isWTCServersInherited() {
      return false;
   }

   public boolean isWTCServersSet() {
      return this._isSet(18);
   }

   public void removeWTCServer(WTCServerMBean param0) {
      this.destroyWTCServer(param0);
   }

   public void setWTCServers(WTCServerMBean[] param0) throws InvalidAttributeValueException {
      WTCServerMBean[] param0 = param0 == null ? new WTCServerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WTCServerMBean[] _oldVal = this._WTCServers;
      this._WTCServers = (WTCServerMBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public WTCServerMBean createWTCServer(String param0) {
      WTCServerMBeanImpl lookup = (WTCServerMBeanImpl)this.lookupWTCServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCServerMBeanImpl _val = new WTCServerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCServer(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWTCServer(WTCServerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         WTCServerMBean[] _old = this.getWTCServers();
         WTCServerMBean[] _new = (WTCServerMBean[])((WTCServerMBean[])this._getHelper()._removeElement(_old, WTCServerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWTCServers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public WTCServerMBean lookupWTCServer(String param0) {
      Object[] aary = (Object[])this._WTCServers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCServerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCServerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public LogMBean getLog() {
      return this._Log;
   }

   public boolean isLogInherited() {
      return false;
   }

   public boolean isLogSet() {
      return this._isSet(19) || this._isAnythingSet((AbstractDescriptorBean)this.getLog());
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setLog(LogMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 19)) {
         this._postCreate(_child);
      }

      LogMBean _oldVal = this._Log;
      this._Log = param0;
      this._postSet(19, _oldVal, param0);
   }

   public SNMPAgentMBean getSNMPAgent() {
      return this._SNMPAgent;
   }

   public boolean isSNMPAgentInherited() {
      return false;
   }

   public boolean isSNMPAgentSet() {
      return this._isSet(20) || this._isAnythingSet((AbstractDescriptorBean)this.getSNMPAgent());
   }

   public void setSNMPAgent(SNMPAgentMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 20)) {
         this._postCreate(_child);
      }

      SNMPAgentMBean _oldVal = this._SNMPAgent;
      this._SNMPAgent = param0;
      this._postSet(20, _oldVal, param0);
   }

   public void addSNMPAgentDeployment(SNMPAgentDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         SNMPAgentDeploymentMBean[] _new;
         if (this._isSet(21)) {
            _new = (SNMPAgentDeploymentMBean[])((SNMPAgentDeploymentMBean[])this._getHelper()._extendArray(this.getSNMPAgentDeployments(), SNMPAgentDeploymentMBean.class, param0));
         } else {
            _new = new SNMPAgentDeploymentMBean[]{param0};
         }

         try {
            this.setSNMPAgentDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPAgentDeploymentMBean[] getSNMPAgentDeployments() {
      return this._SNMPAgentDeployments;
   }

   public boolean isSNMPAgentDeploymentsInherited() {
      return false;
   }

   public boolean isSNMPAgentDeploymentsSet() {
      return this._isSet(21);
   }

   public void removeSNMPAgentDeployment(SNMPAgentDeploymentMBean param0) {
      this.destroySNMPAgentDeployment(param0);
   }

   public void setSNMPAgentDeployments(SNMPAgentDeploymentMBean[] param0) throws InvalidAttributeValueException {
      SNMPAgentDeploymentMBean[] param0 = param0 == null ? new SNMPAgentDeploymentMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SNMPAgentDeploymentMBean[] _oldVal = this._SNMPAgentDeployments;
      this._SNMPAgentDeployments = (SNMPAgentDeploymentMBean[])param0;
      this._postSet(21, _oldVal, param0);
   }

   public SNMPAgentDeploymentMBean createSNMPAgentDeployment(String param0) {
      SNMPAgentDeploymentMBeanImpl lookup = (SNMPAgentDeploymentMBeanImpl)this.lookupSNMPAgentDeployment(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPAgentDeploymentMBeanImpl _val = new SNMPAgentDeploymentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPAgentDeployment(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroySNMPAgentDeployment(SNMPAgentDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 21);
         SNMPAgentDeploymentMBean[] _old = this.getSNMPAgentDeployments();
         SNMPAgentDeploymentMBean[] _new = (SNMPAgentDeploymentMBean[])((SNMPAgentDeploymentMBean[])this._getHelper()._removeElement(_old, SNMPAgentDeploymentMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPAgentDeployments(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
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

   public SNMPAgentDeploymentMBean lookupSNMPAgentDeployment(String param0) {
      Object[] aary = (Object[])this._SNMPAgentDeployments;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPAgentDeploymentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPAgentDeploymentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public String getRootDirectory() {
      return this._customizer.getRootDirectory();
   }

   public boolean isRootDirectoryInherited() {
      return false;
   }

   public boolean isRootDirectorySet() {
      return this._isSet(22);
   }

   public void setRootDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RootDirectory;
      this._RootDirectory = param0;
      this._postSet(22, _oldVal, param0);
   }

   public void discoverManagedServers() {
      this._customizer.discoverManagedServers();
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

   public boolean discoverManagedServer(String param0) {
      return this._customizer.discoverManagedServer(param0);
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

   public Object[] getDisconnectedManagedServers() {
      return this._customizer.getDisconnectedManagedServers();
   }

   public boolean isConsoleEnabled() {
      return this._ConsoleEnabled;
   }

   public boolean isConsoleEnabledInherited() {
      return false;
   }

   public boolean isConsoleEnabledSet() {
      return this._isSet(23);
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

   public void setConsoleEnabled(boolean param0) {
      boolean _oldVal = this._ConsoleEnabled;
      this._ConsoleEnabled = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isJavaServiceConsoleEnabled() {
      if (!this._isSet(24)) {
         try {
            return this.isJavaServiceEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._JavaServiceConsoleEnabled;
   }

   public boolean isJavaServiceConsoleEnabledInherited() {
      return false;
   }

   public boolean isJavaServiceConsoleEnabledSet() {
      return this._isSet(24);
   }

   public void setJavaServiceConsoleEnabled(boolean param0) {
      boolean _oldVal = this._JavaServiceConsoleEnabled;
      this._JavaServiceConsoleEnabled = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getConsoleContextPath() {
      return this._ConsoleContextPath;
   }

   public boolean isConsoleContextPathInherited() {
      return false;
   }

   public boolean isConsoleContextPathSet() {
      return this._isSet(25);
   }

   public void setConsoleContextPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConsoleContextPath;
      this._ConsoleContextPath = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getConsoleExtensionDirectory() {
      return this._ConsoleExtensionDirectory;
   }

   public boolean isConsoleExtensionDirectoryInherited() {
      return false;
   }

   public boolean isConsoleExtensionDirectorySet() {
      return this._isSet(26);
   }

   public void setConsoleExtensionDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConsoleExtensionDirectory;
      this._ConsoleExtensionDirectory = param0;
      this._postSet(26, _oldVal, param0);
   }

   public boolean isAutoConfigurationSaveEnabled() {
      return this._AutoConfigurationSaveEnabled;
   }

   public boolean isAutoConfigurationSaveEnabledInherited() {
      return false;
   }

   public boolean isAutoConfigurationSaveEnabledSet() {
      return this._isSet(27);
   }

   public void setAutoConfigurationSaveEnabled(boolean param0) {
      boolean _oldVal = this._AutoConfigurationSaveEnabled;
      this._AutoConfigurationSaveEnabled = param0;
      this._postSet(27, _oldVal, param0);
   }

   public void addServer(ServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         ServerMBean[] _new;
         if (this._isSet(28)) {
            _new = (ServerMBean[])((ServerMBean[])this._getHelper()._extendArray(this.getServers(), ServerMBean.class, param0));
         } else {
            _new = new ServerMBean[]{param0};
         }

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
      return this._Servers;
   }

   public boolean isServersInherited() {
      return false;
   }

   public boolean isServersSet() {
      return this._isSet(28);
   }

   public void removeServer(ServerMBean param0) {
      this.destroyServer(param0);
   }

   public void setServers(ServerMBean[] param0) throws InvalidAttributeValueException {
      ServerMBean[] param0 = param0 == null ? new ServerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 28)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ServerMBean[] _oldVal = this._Servers;
      this._Servers = (ServerMBean[])param0;
      this._postSet(28, _oldVal, param0);
   }

   public ServerMBean createServer(String param0) {
      ServerMBeanImpl lookup = (ServerMBeanImpl)this.lookupServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ServerMBeanImpl _val = new ServerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addServer(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyServer(ServerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 28);
         ServerMBean[] _old = this.getServers();
         ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._removeElement(_old, ServerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setServers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ServerMBean lookupServer(String param0) {
      Object[] aary = (Object[])this._Servers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ServerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ServerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addServerTemplate(ServerTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 29)) {
         ServerTemplateMBean[] _new;
         if (this._isSet(29)) {
            _new = (ServerTemplateMBean[])((ServerTemplateMBean[])this._getHelper()._extendArray(this.getServerTemplates(), ServerTemplateMBean.class, param0));
         } else {
            _new = new ServerTemplateMBean[]{param0};
         }

         try {
            this.setServerTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServerTemplateMBean[] getServerTemplates() {
      return this._ServerTemplates;
   }

   public boolean isServerTemplatesInherited() {
      return false;
   }

   public boolean isServerTemplatesSet() {
      return this._isSet(29);
   }

   public void removeServerTemplate(ServerTemplateMBean param0) {
      this.destroyServerTemplate(param0);
   }

   public void setServerTemplates(ServerTemplateMBean[] param0) throws InvalidAttributeValueException {
      ServerTemplateMBean[] param0 = param0 == null ? new ServerTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 29)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ServerTemplateMBean[] _oldVal = this._ServerTemplates;
      this._ServerTemplates = (ServerTemplateMBean[])param0;
      this._postSet(29, _oldVal, param0);
   }

   public ServerTemplateMBean createServerTemplate(String param0) {
      ServerTemplateMBeanImpl lookup = (ServerTemplateMBeanImpl)this.lookupServerTemplate(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ServerTemplateMBeanImpl _val = new ServerTemplateMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addServerTemplate(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyServerTemplate(ServerTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 29);
         ServerTemplateMBean[] _old = this.getServerTemplates();
         ServerTemplateMBean[] _new = (ServerTemplateMBean[])((ServerTemplateMBean[])this._getHelper()._removeElement(_old, ServerTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setServerTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ServerTemplateMBean lookupServerTemplate(String param0) {
      Object[] aary = (Object[])this._ServerTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ServerTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ServerTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addCoherenceServer(CoherenceServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 30)) {
         CoherenceServerMBean[] _new;
         if (this._isSet(30)) {
            _new = (CoherenceServerMBean[])((CoherenceServerMBean[])this._getHelper()._extendArray(this.getCoherenceServers(), CoherenceServerMBean.class, param0));
         } else {
            _new = new CoherenceServerMBean[]{param0};
         }

         try {
            this.setCoherenceServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceServerMBean[] getCoherenceServers() {
      return this._CoherenceServers;
   }

   public boolean isCoherenceServersInherited() {
      return false;
   }

   public boolean isCoherenceServersSet() {
      return this._isSet(30);
   }

   public void removeCoherenceServer(CoherenceServerMBean param0) {
      this.destroyCoherenceServer(param0);
   }

   public void setCoherenceServers(CoherenceServerMBean[] param0) throws InvalidAttributeValueException {
      CoherenceServerMBean[] param0 = param0 == null ? new CoherenceServerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 30)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherenceServerMBean[] _oldVal = this._CoherenceServers;
      this._CoherenceServers = (CoherenceServerMBean[])param0;
      this._postSet(30, _oldVal, param0);
   }

   public CoherenceServerMBean createCoherenceServer(String param0) {
      CoherenceServerMBeanImpl lookup = (CoherenceServerMBeanImpl)this.lookupCoherenceServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceServerMBeanImpl _val = new CoherenceServerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceServer(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCoherenceServer(CoherenceServerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 30);
         CoherenceServerMBean[] _old = this.getCoherenceServers();
         CoherenceServerMBean[] _new = (CoherenceServerMBean[])((CoherenceServerMBean[])this._getHelper()._removeElement(_old, CoherenceServerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCoherenceServers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public CoherenceServerMBean lookupCoherenceServer(String param0) {
      Object[] aary = (Object[])this._CoherenceServers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceServerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceServerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addCluster(ClusterMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 31)) {
         ClusterMBean[] _new;
         if (this._isSet(31)) {
            _new = (ClusterMBean[])((ClusterMBean[])this._getHelper()._extendArray(this.getClusters(), ClusterMBean.class, param0));
         } else {
            _new = new ClusterMBean[]{param0};
         }

         try {
            this.setClusters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ClusterMBean[] getClusters() {
      return this._Clusters;
   }

   public boolean isClustersInherited() {
      return false;
   }

   public boolean isClustersSet() {
      return this._isSet(31);
   }

   public void removeCluster(ClusterMBean param0) {
      this.destroyCluster(param0);
   }

   public void setClusters(ClusterMBean[] param0) throws InvalidAttributeValueException {
      ClusterMBean[] param0 = param0 == null ? new ClusterMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 31)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ClusterMBean[] _oldVal = this._Clusters;
      this._Clusters = (ClusterMBean[])param0;
      this._postSet(31, _oldVal, param0);
   }

   public ClusterMBean createCluster(String param0) {
      ClusterMBeanImpl lookup = (ClusterMBeanImpl)this.lookupCluster(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ClusterMBeanImpl _val = new ClusterMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCluster(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCluster(ClusterMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 31);
         ClusterMBean[] _old = this.getClusters();
         ClusterMBean[] _new = (ClusterMBean[])((ClusterMBean[])this._getHelper()._removeElement(_old, ClusterMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setClusters(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ClusterMBean lookupCluster(String param0) {
      Object[] aary = (Object[])this._Clusters;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ClusterMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ClusterMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addDeployment(DeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 32)) {
         DeploymentMBean[] _new = (DeploymentMBean[])((DeploymentMBean[])this._getHelper()._extendArray(this.getDeployments(), DeploymentMBean.class, param0));

         try {
            this.setDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DeploymentMBean[] getDeployments() {
      return this._customizer.getDeployments();
   }

   public boolean isDeploymentsInherited() {
      return false;
   }

   public boolean isDeploymentsSet() {
      return this._isSet(32);
   }

   public void removeDeployment(DeploymentMBean param0) {
      DeploymentMBean[] _old = this.getDeployments();
      DeploymentMBean[] _new = (DeploymentMBean[])((DeploymentMBean[])this._getHelper()._removeElement(_old, DeploymentMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDeployments(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDeployments(DeploymentMBean[] param0) throws InvalidAttributeValueException {
      DeploymentMBean[] param0 = param0 == null ? new DeploymentMBeanImpl[0] : param0;
      this._Deployments = (DeploymentMBean[])param0;
   }

   public void addFileT3(FileT3MBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 33)) {
         FileT3MBean[] _new;
         if (this._isSet(33)) {
            _new = (FileT3MBean[])((FileT3MBean[])this._getHelper()._extendArray(this.getFileT3s(), FileT3MBean.class, param0));
         } else {
            _new = new FileT3MBean[]{param0};
         }

         try {
            this.setFileT3s(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FileT3MBean[] getFileT3s() {
      return this._FileT3s;
   }

   public boolean isFileT3sInherited() {
      return false;
   }

   public boolean isFileT3sSet() {
      return this._isSet(33);
   }

   public void removeFileT3(FileT3MBean param0) {
      this.destroyFileT3(param0);
   }

   public void setFileT3s(FileT3MBean[] param0) throws InvalidAttributeValueException {
      FileT3MBean[] param0 = param0 == null ? new FileT3MBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 33)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      FileT3MBean[] _oldVal = this._FileT3s;
      this._FileT3s = (FileT3MBean[])param0;
      this._postSet(33, _oldVal, param0);
   }

   public FileT3MBean createFileT3(String param0) {
      FileT3MBeanImpl lookup = (FileT3MBeanImpl)this.lookupFileT3(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         FileT3MBeanImpl _val = new FileT3MBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addFileT3(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyFileT3(FileT3MBean param0) {
      try {
         this._checkIsPotentialChild(param0, 33);
         FileT3MBean[] _old = this.getFileT3s();
         FileT3MBean[] _new = (FileT3MBean[])((FileT3MBean[])this._getHelper()._removeElement(_old, FileT3MBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFileT3s(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public FileT3MBean lookupFileT3(String param0) {
      Object[] aary = (Object[])this._FileT3s;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      FileT3MBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (FileT3MBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public MessagingBridgeMBean[] getMessagingBridges() {
      return this._MessagingBridges;
   }

   public boolean isMessagingBridgesInherited() {
      return false;
   }

   public boolean isMessagingBridgesSet() {
      return this._isSet(34);
   }

   public void removeMessagingBridge(MessagingBridgeMBean param0) {
      this.destroyMessagingBridge(param0);
   }

   public void setMessagingBridges(MessagingBridgeMBean[] param0) throws InvalidAttributeValueException {
      MessagingBridgeMBean[] param0 = param0 == null ? new MessagingBridgeMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 34)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MessagingBridgeMBean[] _oldVal = this._MessagingBridges;
      this._MessagingBridges = (MessagingBridgeMBean[])param0;
      this._postSet(34, _oldVal, param0);
   }

   public MessagingBridgeMBean createMessagingBridge(String param0) {
      MessagingBridgeMBeanImpl lookup = (MessagingBridgeMBeanImpl)this.lookupMessagingBridge(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MessagingBridgeMBeanImpl _val = new MessagingBridgeMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMessagingBridge(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMessagingBridge(MessagingBridgeMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 34);
         MessagingBridgeMBean[] _old = this.getMessagingBridges();
         MessagingBridgeMBean[] _new = (MessagingBridgeMBean[])((MessagingBridgeMBean[])this._getHelper()._removeElement(_old, MessagingBridgeMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMessagingBridges(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public MessagingBridgeMBean lookupMessagingBridge(String param0) {
      Object[] aary = (Object[])this._MessagingBridges;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MessagingBridgeMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MessagingBridgeMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMessagingBridge(MessagingBridgeMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 34)) {
         MessagingBridgeMBean[] _new;
         if (this._isSet(34)) {
            _new = (MessagingBridgeMBean[])((MessagingBridgeMBean[])this._getHelper()._extendArray(this.getMessagingBridges(), MessagingBridgeMBean.class, param0));
         } else {
            _new = new MessagingBridgeMBean[]{param0};
         }

         try {
            this.setMessagingBridges(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public HashMap start() {
      return this._customizer.start();
   }

   public HashMap kill() {
      return this._customizer.kill();
   }

   public void setProductionModeEnabled(boolean param0) {
      boolean _oldVal = this.isProductionModeEnabled();
      this._customizer.setProductionModeEnabled(param0);
      this._postSet(35, _oldVal, param0);
   }

   public boolean isProductionModeEnabled() {
      return this._customizer.isProductionModeEnabled();
   }

   public boolean isProductionModeEnabledInherited() {
      return false;
   }

   public boolean isProductionModeEnabledSet() {
      return this._isSet(35);
   }

   public EmbeddedLDAPMBean getEmbeddedLDAP() {
      return this._EmbeddedLDAP;
   }

   public boolean isEmbeddedLDAPInherited() {
      return false;
   }

   public boolean isEmbeddedLDAPSet() {
      return this._isSet(36) || this._isAnythingSet((AbstractDescriptorBean)this.getEmbeddedLDAP());
   }

   public void setEmbeddedLDAP(EmbeddedLDAPMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 36)) {
         this._postCreate(_child);
      }

      EmbeddedLDAPMBean _oldVal = this._EmbeddedLDAP;
      this._EmbeddedLDAP = param0;
      this._postSet(36, _oldVal, param0);
   }

   public boolean isAdministrationPortEnabled() {
      if (!this._isSet(37)) {
         return this._isSecureModeEnabled();
      } else {
         return this._AdministrationPortEnabled;
      }
   }

   public boolean isAdministrationPortEnabledInherited() {
      return false;
   }

   public boolean isAdministrationPortEnabledSet() {
      return this._isSet(37);
   }

   public void setAdministrationPortEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._AdministrationPortEnabled;
      this._AdministrationPortEnabled = param0;
      this._postSet(37, _oldVal, param0);
   }

   public int getAdministrationPort() {
      return this._AdministrationPort;
   }

   public boolean isAdministrationPortInherited() {
      return false;
   }

   public boolean isAdministrationPortSet() {
      return this._isSet(38);
   }

   public void setExalogicOptimizationsEnabled(boolean param0) {
      boolean _oldVal = this._ExalogicOptimizationsEnabled;
      this._ExalogicOptimizationsEnabled = param0;
      this._postSet(39, _oldVal, param0);
   }

   public boolean isExalogicOptimizationsEnabled() {
      return this._ExalogicOptimizationsEnabled;
   }

   public boolean isExalogicOptimizationsEnabledInherited() {
      return false;
   }

   public boolean isExalogicOptimizationsEnabledSet() {
      return this._isSet(39);
   }

   public void setJavaServiceEnabled(boolean param0) {
      boolean _oldVal = this._JavaServiceEnabled;
      this._JavaServiceEnabled = param0;
      this._postSet(40, _oldVal, param0);
   }

   public boolean isJavaServiceEnabled() {
      return this._JavaServiceEnabled;
   }

   public boolean isJavaServiceEnabledInherited() {
      return false;
   }

   public boolean isJavaServiceEnabledSet() {
      return this._isSet(40);
   }

   public void setAdministrationPort(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("AdministrationPort", (long)param0, 1L, 65535L);
      int _oldVal = this._AdministrationPort;
      this._AdministrationPort = param0;
      this._postSet(38, _oldVal, param0);
   }

   public int getArchiveConfigurationCount() {
      return this._ArchiveConfigurationCount;
   }

   public boolean isArchiveConfigurationCountInherited() {
      return false;
   }

   public boolean isArchiveConfigurationCountSet() {
      return this._isSet(41);
   }

   public void setArchiveConfigurationCount(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ArchiveConfigurationCount", (long)param0, 0L, 2147483647L);
      int _oldVal = this._ArchiveConfigurationCount;
      this._ArchiveConfigurationCount = param0;
      this._postSet(41, _oldVal, param0);
   }

   public boolean isConfigBackupEnabled() {
      return this._ConfigBackupEnabled;
   }

   public boolean isConfigBackupEnabledInherited() {
      return false;
   }

   public boolean isConfigBackupEnabledSet() {
      return this._isSet(42);
   }

   public void setConfigBackupEnabled(boolean param0) {
      boolean _oldVal = this._ConfigBackupEnabled;
      this._ConfigBackupEnabled = param0;
      this._postSet(42, _oldVal, param0);
   }

   public String getConfigurationVersion() {
      return this._ConfigurationVersion;
   }

   public boolean isConfigurationVersionInherited() {
      return false;
   }

   public boolean isConfigurationVersionSet() {
      return this._isSet(43);
   }

   public void setConfigurationVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigurationVersion;
      this._ConfigurationVersion = param0;
      this._postSet(43, _oldVal, param0);
   }

   public boolean isAdministrationMBeanAuditingEnabled() {
      return this._AdministrationMBeanAuditingEnabled;
   }

   public boolean isAdministrationMBeanAuditingEnabledInherited() {
      return false;
   }

   public boolean isAdministrationMBeanAuditingEnabledSet() {
      return this._isSet(44);
   }

   public void setAdministrationMBeanAuditingEnabled(boolean param0) {
      boolean _oldVal = this._AdministrationMBeanAuditingEnabled;
      this._AdministrationMBeanAuditingEnabled = param0;
      this._postSet(44, _oldVal, param0);
   }

   public String getConfigurationAuditType() {
      if (!this._isSet(45)) {
         return this._isSecureModeEnabled() ? "audit" : "none";
      } else {
         return this._ConfigurationAuditType;
      }
   }

   public boolean isConfigurationAuditTypeInherited() {
      return false;
   }

   public boolean isConfigurationAuditTypeSet() {
      return this._isSet(45);
   }

   public void setConfigurationAuditType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "log", "audit", "logaudit"};
      param0 = LegalChecks.checkInEnum("ConfigurationAuditType", param0, _set);
      String _oldVal = this._ConfigurationAuditType;
      this._ConfigurationAuditType = param0;
      this._postSet(45, _oldVal, param0);
   }

   public boolean isClusterConstraintsEnabled() {
      return this._customizer.isClusterConstraintsEnabled();
   }

   public boolean isClusterConstraintsEnabledInherited() {
      return false;
   }

   public boolean isClusterConstraintsEnabledSet() {
      return this._isSet(46);
   }

   public void setClusterConstraintsEnabled(boolean param0) {
      boolean _oldVal = this.isClusterConstraintsEnabled();
      this._customizer.setClusterConstraintsEnabled(param0);
      this._postSet(46, _oldVal, param0);
   }

   public void addApplication(ApplicationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 47)) {
         ApplicationMBean[] _new;
         if (this._isSet(47)) {
            _new = (ApplicationMBean[])((ApplicationMBean[])this._getHelper()._extendArray(this.getApplications(), ApplicationMBean.class, param0));
         } else {
            _new = new ApplicationMBean[]{param0};
         }

         try {
            this.setApplications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ApplicationMBean[] getApplications() {
      return this._Applications;
   }

   public boolean isApplicationsInherited() {
      return false;
   }

   public boolean isApplicationsSet() {
      return this._isSet(47);
   }

   public void removeApplication(ApplicationMBean param0) {
      this.destroyApplication(param0);
   }

   public void setApplications(ApplicationMBean[] param0) throws InvalidAttributeValueException {
      ApplicationMBean[] param0 = param0 == null ? new ApplicationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 47)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ApplicationMBean[] _oldVal = this._Applications;
      this._Applications = (ApplicationMBean[])param0;
      this._postSet(47, _oldVal, param0);
   }

   public ApplicationMBean createApplication(String param0) {
      ApplicationMBeanImpl lookup = (ApplicationMBeanImpl)this.lookupApplication(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ApplicationMBeanImpl _val = new ApplicationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addApplication(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyApplication(ApplicationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 47);
         ApplicationMBean[] _old = this.getApplications();
         ApplicationMBean[] _new = (ApplicationMBean[])((ApplicationMBean[])this._getHelper()._removeElement(_old, ApplicationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setApplications(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ApplicationMBean lookupApplication(String param0) {
      Object[] aary = (Object[])this._Applications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ApplicationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ApplicationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public AppDeploymentMBean[] getAppDeployments() {
      return this._AppDeployments;
   }

   public boolean isAppDeploymentsInherited() {
      return false;
   }

   public boolean isAppDeploymentsSet() {
      return this._isSet(48);
   }

   public void removeAppDeployment(AppDeploymentMBean param0) {
      this.destroyAppDeployment(param0);
   }

   public void setAppDeployments(AppDeploymentMBean[] param0) throws InvalidAttributeValueException {
      AppDeploymentMBean[] param0 = param0 == null ? new AppDeploymentMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 48)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AppDeploymentMBean[] _oldVal = this._AppDeployments;
      this._AppDeployments = (AppDeploymentMBean[])param0;
      this._postSet(48, _oldVal, param0);
   }

   public AppDeploymentMBean lookupAppDeployment(String param0) {
      Object[] aary = (Object[])this._AppDeployments;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AppDeploymentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AppDeploymentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public AppDeploymentMBean createAppDeployment(String param0, String param1) throws IllegalArgumentException {
      AppDeploymentMBeanImpl lookup = (AppDeploymentMBeanImpl)this.lookupAppDeployment(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         AppDeploymentMBeanImpl _val = new AppDeploymentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addAppDeployment(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else if (var6 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyAppDeployment(AppDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 48);
         AppDeploymentMBean[] _old = this.getAppDeployments();
         AppDeploymentMBean[] _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._removeElement(_old, AppDeploymentMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAppDeployments(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addAppDeployment(AppDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 48)) {
         AppDeploymentMBean[] _new;
         if (this._isSet(48)) {
            _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._extendArray(this.getAppDeployments(), AppDeploymentMBean.class, param0));
         } else {
            _new = new AppDeploymentMBean[]{param0};
         }

         try {
            this.setAppDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AppDeploymentMBean[] getInternalAppDeployments() {
      return this._InternalAppDeployments;
   }

   public boolean isInternalAppDeploymentsInherited() {
      return false;
   }

   public boolean isInternalAppDeploymentsSet() {
      return this._isSet(49);
   }

   public void removeInternalAppDeployment(AppDeploymentMBean param0) {
      AppDeploymentMBean[] _old = this.getInternalAppDeployments();
      AppDeploymentMBean[] _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._removeElement(_old, AppDeploymentMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setInternalAppDeployments(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public AppDeploymentMBean lookupInternalAppDeployment(String param0) {
      Object[] aary = (Object[])this._InternalAppDeployments;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AppDeploymentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AppDeploymentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setInternalAppDeployments(AppDeploymentMBean[] param0) {
      AppDeploymentMBean[] param0 = param0 == null ? new AppDeploymentMBeanImpl[0] : param0;
      this._InternalAppDeployments = (AppDeploymentMBean[])param0;
   }

   public AppDeploymentMBean createInternalAppDeployment(String param0, String param1) throws IllegalArgumentException {
      AppDeploymentMBeanImpl lookup = (AppDeploymentMBeanImpl)this.lookupInternalAppDeployment(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         AppDeploymentMBeanImpl _val = new AppDeploymentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addInternalAppDeployment(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else if (var6 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void addInternalAppDeployment(AppDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 49)) {
         AppDeploymentMBean[] _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._extendArray(this.getInternalAppDeployments(), AppDeploymentMBean.class, param0));

         try {
            this.setInternalAppDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void destroyInternalAppDeployment(AppDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 49);
         AppDeploymentMBean[] _old = this.getInternalAppDeployments();
         AppDeploymentMBean[] _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._removeElement(_old, AppDeploymentMBean.class, param0));
         if (_old.length != _new.length) {
         }

      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public LibraryMBean[] getLibraries() {
      return this._Libraries;
   }

   public boolean isLibrariesInherited() {
      return false;
   }

   public boolean isLibrariesSet() {
      return this._isSet(50);
   }

   public void removeLibrary(LibraryMBean param0) {
      this.destroyLibrary(param0);
   }

   public void setLibraries(LibraryMBean[] param0) throws InvalidAttributeValueException {
      LibraryMBean[] param0 = param0 == null ? new LibraryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 50)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LibraryMBean[] _oldVal = this._Libraries;
      this._Libraries = (LibraryMBean[])param0;
      this._postSet(50, _oldVal, param0);
   }

   public LibraryMBean lookupLibrary(String param0) {
      Object[] aary = (Object[])this._Libraries;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      LibraryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (LibraryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public LibraryMBean createLibrary(String param0, String param1) {
      LibraryMBeanImpl lookup = (LibraryMBeanImpl)this.lookupLibrary(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         LibraryMBeanImpl _val = new LibraryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addLibrary(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyLibrary(LibraryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 50);
         LibraryMBean[] _old = this.getLibraries();
         LibraryMBean[] _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._removeElement(_old, LibraryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLibraries(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addLibrary(LibraryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 50)) {
         LibraryMBean[] _new;
         if (this._isSet(50)) {
            _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._extendArray(this.getLibraries(), LibraryMBean.class, param0));
         } else {
            _new = new LibraryMBean[]{param0};
         }

         try {
            this.setLibraries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addDomainLibrary(DomainLibraryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 51)) {
         DomainLibraryMBean[] _new;
         if (this._isSet(51)) {
            _new = (DomainLibraryMBean[])((DomainLibraryMBean[])this._getHelper()._extendArray(this.getDomainLibraries(), DomainLibraryMBean.class, param0));
         } else {
            _new = new DomainLibraryMBean[]{param0};
         }

         try {
            this.setDomainLibraries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DomainLibraryMBean[] getDomainLibraries() {
      return this._DomainLibraries;
   }

   public boolean isDomainLibrariesInherited() {
      return false;
   }

   public boolean isDomainLibrariesSet() {
      return this._isSet(51);
   }

   public void removeDomainLibrary(DomainLibraryMBean param0) {
      this.destroyDomainLibrary(param0);
   }

   public void setDomainLibraries(DomainLibraryMBean[] param0) throws InvalidAttributeValueException {
      DomainLibraryMBean[] param0 = param0 == null ? new DomainLibraryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 51)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DomainLibraryMBean[] _oldVal = this._DomainLibraries;
      this._DomainLibraries = (DomainLibraryMBean[])param0;
      this._postSet(51, _oldVal, param0);
   }

   public DomainLibraryMBean lookupDomainLibrary(String param0) {
      Object[] aary = (Object[])this._DomainLibraries;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      DomainLibraryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (DomainLibraryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public DomainLibraryMBean createDomainLibrary(String param0, String param1) {
      DomainLibraryMBeanImpl lookup = (DomainLibraryMBeanImpl)this.lookupDomainLibrary(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DomainLibraryMBeanImpl _val = new DomainLibraryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addDomainLibrary(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyDomainLibrary(DomainLibraryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 51);
         DomainLibraryMBean[] _old = this.getDomainLibraries();
         DomainLibraryMBean[] _new = (DomainLibraryMBean[])((DomainLibraryMBean[])this._getHelper()._removeElement(_old, DomainLibraryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDomainLibraries(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public LibraryMBean[] getInternalLibraries() {
      return this._InternalLibraries;
   }

   public boolean isInternalLibrariesInherited() {
      return false;
   }

   public boolean isInternalLibrariesSet() {
      return this._isSet(52);
   }

   public void removeInternalLibrary(LibraryMBean param0) {
      LibraryMBean[] _old = this.getInternalLibraries();
      LibraryMBean[] _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._removeElement(_old, LibraryMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setInternalLibraries(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public LibraryMBean lookupInternalLibrary(String param0) {
      Object[] aary = (Object[])this._InternalLibraries;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      LibraryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (LibraryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public LibraryMBean createInternalLibrary(String param0, String param1) {
      LibraryMBeanImpl lookup = (LibraryMBeanImpl)this.lookupInternalLibrary(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         LibraryMBeanImpl _val = new LibraryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addInternalLibrary(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void addInternalLibrary(LibraryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 52)) {
         LibraryMBean[] _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._extendArray(this.getInternalLibraries(), LibraryMBean.class, param0));

         try {
            this.setInternalLibraries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void destroyInternalLibrary(LibraryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 52);
         LibraryMBean[] _old = this.getInternalLibraries();
         LibraryMBean[] _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._removeElement(_old, LibraryMBean.class, param0));
         if (_old.length != _new.length) {
         }

      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setInternalLibraries(LibraryMBean[] param0) {
      LibraryMBean[] param0 = param0 == null ? new LibraryMBeanImpl[0] : param0;
      this._InternalLibraries = (LibraryMBean[])param0;
   }

   public BasicDeploymentMBean[] getBasicDeployments() {
      return this._customizer.getBasicDeployments();
   }

   public void addWSReliableDeliveryPolicy(WSReliableDeliveryPolicyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 53)) {
         WSReliableDeliveryPolicyMBean[] _new;
         if (this._isSet(53)) {
            _new = (WSReliableDeliveryPolicyMBean[])((WSReliableDeliveryPolicyMBean[])this._getHelper()._extendArray(this.getWSReliableDeliveryPolicies(), WSReliableDeliveryPolicyMBean.class, param0));
         } else {
            _new = new WSReliableDeliveryPolicyMBean[]{param0};
         }

         try {
            this.setWSReliableDeliveryPolicies(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WSReliableDeliveryPolicyMBean[] getWSReliableDeliveryPolicies() {
      return this._WSReliableDeliveryPolicies;
   }

   public boolean isWSReliableDeliveryPoliciesInherited() {
      return false;
   }

   public boolean isWSReliableDeliveryPoliciesSet() {
      return this._isSet(53);
   }

   public void removeWSReliableDeliveryPolicy(WSReliableDeliveryPolicyMBean param0) {
      this.destroyWSReliableDeliveryPolicy(param0);
   }

   public void setWSReliableDeliveryPolicies(WSReliableDeliveryPolicyMBean[] param0) throws InvalidAttributeValueException {
      WSReliableDeliveryPolicyMBean[] param0 = param0 == null ? new WSReliableDeliveryPolicyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 53)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WSReliableDeliveryPolicyMBean[] _oldVal = this._WSReliableDeliveryPolicies;
      this._WSReliableDeliveryPolicies = (WSReliableDeliveryPolicyMBean[])param0;
      this._postSet(53, _oldVal, param0);
   }

   public WSReliableDeliveryPolicyMBean lookupWSReliableDeliveryPolicy(String param0) {
      Object[] aary = (Object[])this._WSReliableDeliveryPolicies;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WSReliableDeliveryPolicyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WSReliableDeliveryPolicyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WSReliableDeliveryPolicyMBean createWSReliableDeliveryPolicy(String param0) {
      WSReliableDeliveryPolicyMBeanImpl lookup = (WSReliableDeliveryPolicyMBeanImpl)this.lookupWSReliableDeliveryPolicy(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WSReliableDeliveryPolicyMBeanImpl _val = new WSReliableDeliveryPolicyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWSReliableDeliveryPolicy(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWSReliableDeliveryPolicy(WSReliableDeliveryPolicyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 53);
         WSReliableDeliveryPolicyMBean[] _old = this.getWSReliableDeliveryPolicies();
         WSReliableDeliveryPolicyMBean[] _new = (WSReliableDeliveryPolicyMBean[])((WSReliableDeliveryPolicyMBean[])this._getHelper()._removeElement(_old, WSReliableDeliveryPolicyMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWSReliableDeliveryPolicies(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addMachine(MachineMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 54)) {
         MachineMBean[] _new;
         if (this._isSet(54)) {
            _new = (MachineMBean[])((MachineMBean[])this._getHelper()._extendArray(this.getMachines(), MachineMBean.class, param0));
         } else {
            _new = new MachineMBean[]{param0};
         }

         try {
            this.setMachines(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MachineMBean[] getMachines() {
      return this._Machines;
   }

   public boolean isMachinesInherited() {
      return false;
   }

   public boolean isMachinesSet() {
      return this._isSet(54);
   }

   public void removeMachine(MachineMBean param0) {
      this.destroyMachine(param0);
   }

   public void setMachines(MachineMBean[] param0) throws InvalidAttributeValueException {
      MachineMBean[] param0 = param0 == null ? new MachineMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 54)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MachineMBean[] _oldVal = this._Machines;
      this._Machines = (MachineMBean[])param0;
      this._postSet(54, _oldVal, param0);
   }

   public MachineMBean createMachine(String param0) {
      MachineMBeanImpl lookup = (MachineMBeanImpl)this.lookupMachine(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MachineMBeanImpl _val = new MachineMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMachine(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public UnixMachineMBean createUnixMachine(String param0) {
      UnixMachineMBeanImpl lookup = (UnixMachineMBeanImpl)this.lookupMachine(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         UnixMachineMBeanImpl _val = new UnixMachineMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMachine(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMachine(MachineMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 54);
         MachineMBean[] _old = this.getMachines();
         MachineMBean[] _new = (MachineMBean[])((MachineMBean[])this._getHelper()._removeElement(_old, MachineMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMachines(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public MachineMBean lookupMachine(String param0) {
      Object[] aary = (Object[])this._Machines;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MachineMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MachineMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addXMLEntityCache(XMLEntityCacheMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 55)) {
         XMLEntityCacheMBean[] _new;
         if (this._isSet(55)) {
            _new = (XMLEntityCacheMBean[])((XMLEntityCacheMBean[])this._getHelper()._extendArray(this.getXMLEntityCaches(), XMLEntityCacheMBean.class, param0));
         } else {
            _new = new XMLEntityCacheMBean[]{param0};
         }

         try {
            this.setXMLEntityCaches(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public XMLEntityCacheMBean[] getXMLEntityCaches() {
      return this._XMLEntityCaches;
   }

   public boolean isXMLEntityCachesInherited() {
      return false;
   }

   public boolean isXMLEntityCachesSet() {
      return this._isSet(55);
   }

   public void removeXMLEntityCache(XMLEntityCacheMBean param0) {
      this.destroyXMLEntityCache(param0);
   }

   public void setXMLEntityCaches(XMLEntityCacheMBean[] param0) throws InvalidAttributeValueException {
      XMLEntityCacheMBean[] param0 = param0 == null ? new XMLEntityCacheMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 55)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      XMLEntityCacheMBean[] _oldVal = this._XMLEntityCaches;
      this._XMLEntityCaches = (XMLEntityCacheMBean[])param0;
      this._postSet(55, _oldVal, param0);
   }

   public XMLEntityCacheMBean createXMLEntityCache(String param0) {
      XMLEntityCacheMBeanImpl lookup = (XMLEntityCacheMBeanImpl)this.lookupXMLEntityCache(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         XMLEntityCacheMBeanImpl _val = new XMLEntityCacheMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addXMLEntityCache(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public XMLEntityCacheMBean lookupXMLEntityCache(String param0) {
      Object[] aary = (Object[])this._XMLEntityCaches;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      XMLEntityCacheMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (XMLEntityCacheMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyXMLEntityCache(XMLEntityCacheMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 55);
         XMLEntityCacheMBean[] _old = this.getXMLEntityCaches();
         XMLEntityCacheMBean[] _new = (XMLEntityCacheMBean[])((XMLEntityCacheMBean[])this._getHelper()._removeElement(_old, XMLEntityCacheMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setXMLEntityCaches(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addXMLRegistry(XMLRegistryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 56)) {
         XMLRegistryMBean[] _new;
         if (this._isSet(56)) {
            _new = (XMLRegistryMBean[])((XMLRegistryMBean[])this._getHelper()._extendArray(this.getXMLRegistries(), XMLRegistryMBean.class, param0));
         } else {
            _new = new XMLRegistryMBean[]{param0};
         }

         try {
            this.setXMLRegistries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public XMLRegistryMBean[] getXMLRegistries() {
      return this._XMLRegistries;
   }

   public boolean isXMLRegistriesInherited() {
      return false;
   }

   public boolean isXMLRegistriesSet() {
      return this._isSet(56);
   }

   public void removeXMLRegistry(XMLRegistryMBean param0) {
      this.destroyXMLRegistry(param0);
   }

   public void setXMLRegistries(XMLRegistryMBean[] param0) throws InvalidAttributeValueException {
      XMLRegistryMBean[] param0 = param0 == null ? new XMLRegistryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 56)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      XMLRegistryMBean[] _oldVal = this._XMLRegistries;
      this._XMLRegistries = (XMLRegistryMBean[])param0;
      this._postSet(56, _oldVal, param0);
   }

   public XMLRegistryMBean createXMLRegistry(String param0) {
      XMLRegistryMBeanImpl lookup = (XMLRegistryMBeanImpl)this.lookupXMLRegistry(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         XMLRegistryMBeanImpl _val = new XMLRegistryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addXMLRegistry(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyXMLRegistry(XMLRegistryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 56);
         XMLRegistryMBean[] _old = this.getXMLRegistries();
         XMLRegistryMBean[] _new = (XMLRegistryMBean[])((XMLRegistryMBean[])this._getHelper()._removeElement(_old, XMLRegistryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setXMLRegistries(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public XMLRegistryMBean lookupXMLRegistry(String param0) {
      Object[] aary = (Object[])this._XMLRegistries;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      XMLRegistryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (XMLRegistryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 57)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TargetMBean[] getTargets() {
      return this._customizer.getTargets();
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(57);
   }

   public void removeTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      this._Targets = (TargetMBean[])param0;
   }

   public TargetMBean lookupTarget(String param0) throws IllegalArgumentException {
      return this._customizer.lookupTarget(param0);
   }

   public JMSServerMBean[] getJMSServers() {
      return this._JMSServers;
   }

   public boolean isJMSServersInherited() {
      return false;
   }

   public boolean isJMSServersSet() {
      return this._isSet(58);
   }

   public void removeJMSServer(JMSServerMBean param0) {
      this.destroyJMSServer(param0);
   }

   public void setJMSServers(JMSServerMBean[] param0) throws InvalidAttributeValueException {
      JMSServerMBean[] param0 = param0 == null ? new JMSServerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 58)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSServerMBean[] _oldVal = this._JMSServers;
      this._JMSServers = (JMSServerMBean[])param0;
      this._postSet(58, _oldVal, param0);
   }

   public JMSServerMBean createJMSServer(String param0) {
      JMSServerMBeanImpl lookup = (JMSServerMBeanImpl)this.lookupJMSServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSServerMBeanImpl _val = new JMSServerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSServer(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSServer(JMSServerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 58);
         JMSServerMBean[] _old = this.getJMSServers();
         JMSServerMBean[] _new = (JMSServerMBean[])((JMSServerMBean[])this._getHelper()._removeElement(_old, JMSServerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSServers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSServerMBean lookupJMSServer(String param0) {
      Object[] aary = (Object[])this._JMSServers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSServerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSServerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSServer(JMSServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 58)) {
         JMSServerMBean[] _new;
         if (this._isSet(58)) {
            _new = (JMSServerMBean[])((JMSServerMBean[])this._getHelper()._extendArray(this.getJMSServers(), JMSServerMBean.class, param0));
         } else {
            _new = new JMSServerMBean[]{param0};
         }

         try {
            this.setJMSServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addJMSStore(JMSStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 59)) {
         JMSStoreMBean[] _new = (JMSStoreMBean[])((JMSStoreMBean[])this._getHelper()._extendArray(this.getJMSStores(), JMSStoreMBean.class, param0));

         try {
            this.setJMSStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSStoreMBean[] getJMSStores() {
      return this._JMSStores;
   }

   public boolean isJMSStoresInherited() {
      return false;
   }

   public boolean isJMSStoresSet() {
      return this._isSet(59);
   }

   public void removeJMSStore(JMSStoreMBean param0) {
      JMSStoreMBean[] _old = this.getJMSStores();
      JMSStoreMBean[] _new = (JMSStoreMBean[])((JMSStoreMBean[])this._getHelper()._removeElement(_old, JMSStoreMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setJMSStores(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setJMSStores(JMSStoreMBean[] param0) throws InvalidAttributeValueException {
      JMSStoreMBean[] param0 = param0 == null ? new JMSStoreMBeanImpl[0] : param0;
      this._JMSStores = (JMSStoreMBean[])param0;
   }

   public JMSStoreMBean lookupJMSStore(String param0) {
      Object[] aary = (Object[])this._JMSStores;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSJDBCStore(JMSJDBCStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 60)) {
         JMSJDBCStoreMBean[] _new;
         if (this._isSet(60)) {
            _new = (JMSJDBCStoreMBean[])((JMSJDBCStoreMBean[])this._getHelper()._extendArray(this.getJMSJDBCStores(), JMSJDBCStoreMBean.class, param0));
         } else {
            _new = new JMSJDBCStoreMBean[]{param0};
         }

         try {
            this.setJMSJDBCStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSJDBCStoreMBean[] getJMSJDBCStores() {
      return this._JMSJDBCStores;
   }

   public boolean isJMSJDBCStoresInherited() {
      return false;
   }

   public boolean isJMSJDBCStoresSet() {
      return this._isSet(60);
   }

   public void removeJMSJDBCStore(JMSJDBCStoreMBean param0) {
      this.destroyJMSJDBCStore(param0);
   }

   public void setJMSJDBCStores(JMSJDBCStoreMBean[] param0) throws InvalidAttributeValueException {
      JMSJDBCStoreMBean[] param0 = param0 == null ? new JMSJDBCStoreMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 60)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSJDBCStoreMBean[] _oldVal = this._JMSJDBCStores;
      this._JMSJDBCStores = (JMSJDBCStoreMBean[])param0;
      this._postSet(60, _oldVal, param0);
   }

   public JMSJDBCStoreMBean createJMSJDBCStore(String param0) {
      JMSJDBCStoreMBeanImpl lookup = (JMSJDBCStoreMBeanImpl)this.lookupJMSJDBCStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSJDBCStoreMBeanImpl _val = new JMSJDBCStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSJDBCStore(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSJDBCStore(JMSJDBCStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 60);
         JMSJDBCStoreMBean[] _old = this.getJMSJDBCStores();
         JMSJDBCStoreMBean[] _new = (JMSJDBCStoreMBean[])((JMSJDBCStoreMBean[])this._getHelper()._removeElement(_old, JMSJDBCStoreMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSJDBCStores(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSJDBCStoreMBean lookupJMSJDBCStore(String param0) {
      Object[] aary = (Object[])this._JMSJDBCStores;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSJDBCStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSJDBCStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSFileStore(JMSFileStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 61)) {
         JMSFileStoreMBean[] _new;
         if (this._isSet(61)) {
            _new = (JMSFileStoreMBean[])((JMSFileStoreMBean[])this._getHelper()._extendArray(this.getJMSFileStores(), JMSFileStoreMBean.class, param0));
         } else {
            _new = new JMSFileStoreMBean[]{param0};
         }

         try {
            this.setJMSFileStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSFileStoreMBean[] getJMSFileStores() {
      return this._JMSFileStores;
   }

   public boolean isJMSFileStoresInherited() {
      return false;
   }

   public boolean isJMSFileStoresSet() {
      return this._isSet(61);
   }

   public void removeJMSFileStore(JMSFileStoreMBean param0) {
      this.destroyJMSFileStore(param0);
   }

   public void setJMSFileStores(JMSFileStoreMBean[] param0) throws InvalidAttributeValueException {
      JMSFileStoreMBean[] param0 = param0 == null ? new JMSFileStoreMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 61)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSFileStoreMBean[] _oldVal = this._JMSFileStores;
      this._JMSFileStores = (JMSFileStoreMBean[])param0;
      this._postSet(61, _oldVal, param0);
   }

   public JMSFileStoreMBean createJMSFileStore(String param0) {
      JMSFileStoreMBeanImpl lookup = (JMSFileStoreMBeanImpl)this.lookupJMSFileStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSFileStoreMBeanImpl _val = new JMSFileStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSFileStore(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSFileStore(JMSFileStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 61);
         JMSFileStoreMBean[] _old = this.getJMSFileStores();
         JMSFileStoreMBean[] _new = (JMSFileStoreMBean[])((JMSFileStoreMBean[])this._getHelper()._removeElement(_old, JMSFileStoreMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSFileStores(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSFileStoreMBean lookupJMSFileStore(String param0) {
      Object[] aary = (Object[])this._JMSFileStores;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSFileStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSFileStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSDestination(JMSDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 62)) {
         JMSDestinationMBean[] _new;
         if (this._isSet(62)) {
            _new = (JMSDestinationMBean[])((JMSDestinationMBean[])this._getHelper()._extendArray(this.getJMSDestinations(), JMSDestinationMBean.class, param0));
         } else {
            _new = new JMSDestinationMBean[]{param0};
         }

         try {
            this.setJMSDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDestinationMBean[] getJMSDestinations() {
      return this._JMSDestinations;
   }

   public boolean isJMSDestinationsInherited() {
      return false;
   }

   public boolean isJMSDestinationsSet() {
      return this._isSet(62);
   }

   public void removeJMSDestination(JMSDestinationMBean param0) {
      JMSDestinationMBean[] _old = this.getJMSDestinations();
      JMSDestinationMBean[] _new = (JMSDestinationMBean[])((JMSDestinationMBean[])this._getHelper()._removeElement(_old, JMSDestinationMBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setJMSDestinations(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setJMSDestinations(JMSDestinationMBean[] param0) throws InvalidAttributeValueException {
      JMSDestinationMBean[] param0 = param0 == null ? new JMSDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 62)) {
            this._postCreate(_child);
         }
      }

      JMSDestinationMBean[] _oldVal = this._JMSDestinations;
      this._JMSDestinations = (JMSDestinationMBean[])param0;
      this._postSet(62, _oldVal, param0);
   }

   public JMSDestinationMBean lookupJMSDestination(String param0) {
      Object[] aary = (Object[])this._JMSDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSQueue(JMSQueueMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 63)) {
         JMSQueueMBean[] _new;
         if (this._isSet(63)) {
            _new = (JMSQueueMBean[])((JMSQueueMBean[])this._getHelper()._extendArray(this.getJMSQueues(), JMSQueueMBean.class, param0));
         } else {
            _new = new JMSQueueMBean[]{param0};
         }

         try {
            this.setJMSQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSQueueMBean[] getJMSQueues() {
      return this._JMSQueues;
   }

   public boolean isJMSQueuesInherited() {
      return false;
   }

   public boolean isJMSQueuesSet() {
      return this._isSet(63);
   }

   public void removeJMSQueue(JMSQueueMBean param0) {
      this.destroyJMSQueue(param0);
   }

   public void setJMSQueues(JMSQueueMBean[] param0) throws InvalidAttributeValueException {
      JMSQueueMBean[] param0 = param0 == null ? new JMSQueueMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 63)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSQueueMBean[] _oldVal = this._JMSQueues;
      this._JMSQueues = (JMSQueueMBean[])param0;
      this._postSet(63, _oldVal, param0);
   }

   public JMSQueueMBean createJMSQueue(String param0) {
      JMSQueueMBeanImpl lookup = (JMSQueueMBeanImpl)this.lookupJMSQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSQueueMBeanImpl _val = new JMSQueueMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSQueue(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSQueue(JMSQueueMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 63);
         JMSQueueMBean[] _old = this.getJMSQueues();
         JMSQueueMBean[] _new = (JMSQueueMBean[])((JMSQueueMBean[])this._getHelper()._removeElement(_old, JMSQueueMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSQueues(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSQueueMBean lookupJMSQueue(String param0) {
      Object[] aary = (Object[])this._JMSQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSQueueMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSQueueMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSTopic(JMSTopicMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 64)) {
         JMSTopicMBean[] _new;
         if (this._isSet(64)) {
            _new = (JMSTopicMBean[])((JMSTopicMBean[])this._getHelper()._extendArray(this.getJMSTopics(), JMSTopicMBean.class, param0));
         } else {
            _new = new JMSTopicMBean[]{param0};
         }

         try {
            this.setJMSTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSTopicMBean[] getJMSTopics() {
      return this._JMSTopics;
   }

   public boolean isJMSTopicsInherited() {
      return false;
   }

   public boolean isJMSTopicsSet() {
      return this._isSet(64);
   }

   public void removeJMSTopic(JMSTopicMBean param0) {
      this.destroyJMSTopic(param0);
   }

   public void setJMSTopics(JMSTopicMBean[] param0) throws InvalidAttributeValueException {
      JMSTopicMBean[] param0 = param0 == null ? new JMSTopicMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 64)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSTopicMBean[] _oldVal = this._JMSTopics;
      this._JMSTopics = (JMSTopicMBean[])param0;
      this._postSet(64, _oldVal, param0);
   }

   public JMSTopicMBean createJMSTopic(String param0) {
      JMSTopicMBeanImpl lookup = (JMSTopicMBeanImpl)this.lookupJMSTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSTopicMBeanImpl _val = new JMSTopicMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSTopic(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSTopic(JMSTopicMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 64);
         JMSTopicMBean[] _old = this.getJMSTopics();
         JMSTopicMBean[] _new = (JMSTopicMBean[])((JMSTopicMBean[])this._getHelper()._removeElement(_old, JMSTopicMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSTopics(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSTopicMBean lookupJMSTopic(String param0) {
      Object[] aary = (Object[])this._JMSTopics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSTopicMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSTopicMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSDistributedQueue(JMSDistributedQueueMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 65)) {
         JMSDistributedQueueMBean[] _new;
         if (this._isSet(65)) {
            _new = (JMSDistributedQueueMBean[])((JMSDistributedQueueMBean[])this._getHelper()._extendArray(this.getJMSDistributedQueues(), JMSDistributedQueueMBean.class, param0));
         } else {
            _new = new JMSDistributedQueueMBean[]{param0};
         }

         try {
            this.setJMSDistributedQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDistributedQueueMBean[] getJMSDistributedQueues() {
      return this._JMSDistributedQueues;
   }

   public boolean isJMSDistributedQueuesInherited() {
      return false;
   }

   public boolean isJMSDistributedQueuesSet() {
      return this._isSet(65);
   }

   public void removeJMSDistributedQueue(JMSDistributedQueueMBean param0) {
      this.destroyJMSDistributedQueue(param0);
   }

   public void setJMSDistributedQueues(JMSDistributedQueueMBean[] param0) throws InvalidAttributeValueException {
      JMSDistributedQueueMBean[] param0 = param0 == null ? new JMSDistributedQueueMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 65)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSDistributedQueueMBean[] _oldVal = this._JMSDistributedQueues;
      this._JMSDistributedQueues = (JMSDistributedQueueMBean[])param0;
      this._postSet(65, _oldVal, param0);
   }

   public JMSDistributedQueueMBean createJMSDistributedQueue(String param0) {
      JMSDistributedQueueMBeanImpl lookup = (JMSDistributedQueueMBeanImpl)this.lookupJMSDistributedQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSDistributedQueueMBeanImpl _val = new JMSDistributedQueueMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSDistributedQueue(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSDistributedQueue(JMSDistributedQueueMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 65);
         JMSDistributedQueueMBean[] _old = this.getJMSDistributedQueues();
         JMSDistributedQueueMBean[] _new = (JMSDistributedQueueMBean[])((JMSDistributedQueueMBean[])this._getHelper()._removeElement(_old, JMSDistributedQueueMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSDistributedQueues(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSDistributedQueueMBean lookupJMSDistributedQueue(String param0) {
      Object[] aary = (Object[])this._JMSDistributedQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDistributedQueueMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDistributedQueueMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSDistributedTopic(JMSDistributedTopicMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 66)) {
         JMSDistributedTopicMBean[] _new;
         if (this._isSet(66)) {
            _new = (JMSDistributedTopicMBean[])((JMSDistributedTopicMBean[])this._getHelper()._extendArray(this.getJMSDistributedTopics(), JMSDistributedTopicMBean.class, param0));
         } else {
            _new = new JMSDistributedTopicMBean[]{param0};
         }

         try {
            this.setJMSDistributedTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDistributedTopicMBean[] getJMSDistributedTopics() {
      return this._JMSDistributedTopics;
   }

   public boolean isJMSDistributedTopicsInherited() {
      return false;
   }

   public boolean isJMSDistributedTopicsSet() {
      return this._isSet(66);
   }

   public void removeJMSDistributedTopic(JMSDistributedTopicMBean param0) {
      this.destroyJMSDistributedTopic(param0);
   }

   public void setJMSDistributedTopics(JMSDistributedTopicMBean[] param0) throws InvalidAttributeValueException {
      JMSDistributedTopicMBean[] param0 = param0 == null ? new JMSDistributedTopicMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 66)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSDistributedTopicMBean[] _oldVal = this._JMSDistributedTopics;
      this._JMSDistributedTopics = (JMSDistributedTopicMBean[])param0;
      this._postSet(66, _oldVal, param0);
   }

   public JMSDistributedTopicMBean createJMSDistributedTopic(String param0) {
      JMSDistributedTopicMBeanImpl lookup = (JMSDistributedTopicMBeanImpl)this.lookupJMSDistributedTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSDistributedTopicMBeanImpl _val = new JMSDistributedTopicMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSDistributedTopic(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSDistributedTopic(JMSDistributedTopicMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 66);
         JMSDistributedTopicMBean[] _old = this.getJMSDistributedTopics();
         JMSDistributedTopicMBean[] _new = (JMSDistributedTopicMBean[])((JMSDistributedTopicMBean[])this._getHelper()._removeElement(_old, JMSDistributedTopicMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSDistributedTopics(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSDistributedTopicMBean lookupJMSDistributedTopic(String param0) {
      Object[] aary = (Object[])this._JMSDistributedTopics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDistributedTopicMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDistributedTopicMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSTemplate(JMSTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 67)) {
         JMSTemplateMBean[] _new;
         if (this._isSet(67)) {
            _new = (JMSTemplateMBean[])((JMSTemplateMBean[])this._getHelper()._extendArray(this.getJMSTemplates(), JMSTemplateMBean.class, param0));
         } else {
            _new = new JMSTemplateMBean[]{param0};
         }

         try {
            this.setJMSTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSTemplateMBean[] getJMSTemplates() {
      return this._JMSTemplates;
   }

   public boolean isJMSTemplatesInherited() {
      return false;
   }

   public boolean isJMSTemplatesSet() {
      return this._isSet(67);
   }

   public void removeJMSTemplate(JMSTemplateMBean param0) {
      this.destroyJMSTemplate(param0);
   }

   public void setJMSTemplates(JMSTemplateMBean[] param0) throws InvalidAttributeValueException {
      JMSTemplateMBean[] param0 = param0 == null ? new JMSTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 67)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSTemplateMBean[] _oldVal = this._JMSTemplates;
      this._JMSTemplates = (JMSTemplateMBean[])param0;
      this._postSet(67, _oldVal, param0);
   }

   public JMSTemplateMBean createJMSTemplate(String param0) {
      JMSTemplateMBeanImpl lookup = (JMSTemplateMBeanImpl)this.lookupJMSTemplate(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSTemplateMBeanImpl _val = new JMSTemplateMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSTemplate(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSTemplate(JMSTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 67);
         JMSTemplateMBean[] _old = this.getJMSTemplates();
         JMSTemplateMBean[] _new = (JMSTemplateMBean[])((JMSTemplateMBean[])this._getHelper()._removeElement(_old, JMSTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSTemplateMBean lookupJMSTemplate(String param0) {
      Object[] aary = (Object[])this._JMSTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addNetworkChannel(NetworkChannelMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 68)) {
         NetworkChannelMBean[] _new;
         if (this._isSet(68)) {
            _new = (NetworkChannelMBean[])((NetworkChannelMBean[])this._getHelper()._extendArray(this.getNetworkChannels(), NetworkChannelMBean.class, param0));
         } else {
            _new = new NetworkChannelMBean[]{param0};
         }

         try {
            this.setNetworkChannels(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public NetworkChannelMBean[] getNetworkChannels() {
      return this._NetworkChannels;
   }

   public boolean isNetworkChannelsInherited() {
      return false;
   }

   public boolean isNetworkChannelsSet() {
      return this._isSet(68);
   }

   public void removeNetworkChannel(NetworkChannelMBean param0) {
      this.destroyNetworkChannel(param0);
   }

   public void setNetworkChannels(NetworkChannelMBean[] param0) throws InvalidAttributeValueException {
      NetworkChannelMBean[] param0 = param0 == null ? new NetworkChannelMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 68)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      NetworkChannelMBean[] _oldVal = this._NetworkChannels;
      this._NetworkChannels = (NetworkChannelMBean[])param0;
      this._postSet(68, _oldVal, param0);
   }

   public NetworkChannelMBean createNetworkChannel(String param0) {
      NetworkChannelMBeanImpl lookup = (NetworkChannelMBeanImpl)this.lookupNetworkChannel(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         NetworkChannelMBeanImpl _val = new NetworkChannelMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addNetworkChannel(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyNetworkChannel(NetworkChannelMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 68);
         NetworkChannelMBean[] _old = this.getNetworkChannels();
         NetworkChannelMBean[] _new = (NetworkChannelMBean[])((NetworkChannelMBean[])this._getHelper()._removeElement(_old, NetworkChannelMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNetworkChannels(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public NetworkChannelMBean lookupNetworkChannel(String param0) {
      Object[] aary = (Object[])this._NetworkChannels;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      NetworkChannelMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (NetworkChannelMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addVirtualHost(VirtualHostMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 69)) {
         VirtualHostMBean[] _new;
         if (this._isSet(69)) {
            _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._extendArray(this.getVirtualHosts(), VirtualHostMBean.class, param0));
         } else {
            _new = new VirtualHostMBean[]{param0};
         }

         try {
            this.setVirtualHosts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VirtualHostMBean[] getVirtualHosts() {
      return this._VirtualHosts;
   }

   public boolean isVirtualHostsInherited() {
      return false;
   }

   public boolean isVirtualHostsSet() {
      return this._isSet(69);
   }

   public void removeVirtualHost(VirtualHostMBean param0) {
      this.destroyVirtualHost(param0);
   }

   public void setVirtualHosts(VirtualHostMBean[] param0) throws InvalidAttributeValueException {
      VirtualHostMBean[] param0 = param0 == null ? new VirtualHostMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 69)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      VirtualHostMBean[] _oldVal = this._VirtualHosts;
      this._VirtualHosts = (VirtualHostMBean[])param0;
      this._postSet(69, _oldVal, param0);
   }

   public VirtualHostMBean createVirtualHost(String param0) {
      VirtualHostMBeanImpl lookup = (VirtualHostMBeanImpl)this.lookupVirtualHost(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         VirtualHostMBeanImpl _val = new VirtualHostMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addVirtualHost(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyVirtualHost(VirtualHostMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 69);
         VirtualHostMBean[] _old = this.getVirtualHosts();
         VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._removeElement(_old, VirtualHostMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setVirtualHosts(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public VirtualHostMBean lookupVirtualHost(String param0) {
      Object[] aary = (Object[])this._VirtualHosts;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      VirtualHostMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (VirtualHostMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addVirtualTarget(VirtualTargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 70)) {
         VirtualTargetMBean[] _new = (VirtualTargetMBean[])((VirtualTargetMBean[])this._getHelper()._extendArray(this.getVirtualTargets(), VirtualTargetMBean.class, param0));

         try {
            this.setVirtualTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VirtualTargetMBean[] getVirtualTargets() {
      return this._customizer.getVirtualTargets();
   }

   public boolean isVirtualTargetsInherited() {
      return false;
   }

   public boolean isVirtualTargetsSet() {
      return this._isSet(70);
   }

   public void removeVirtualTarget(VirtualTargetMBean param0) {
      this.destroyVirtualTarget(param0);
   }

   public void setVirtualTargets(VirtualTargetMBean[] param0) throws InvalidAttributeValueException {
      VirtualTargetMBean[] param0 = param0 == null ? new VirtualTargetMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 70)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      VirtualTargetMBean[] _oldVal = this._VirtualTargets;
      this._VirtualTargets = (VirtualTargetMBean[])param0;
      this._postSet(70, _oldVal, param0);
   }

   public VirtualTargetMBean createVirtualTarget(String param0) {
      return this._customizer.createVirtualTarget(param0);
   }

   public void destroyVirtualTarget(VirtualTargetMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 70);
         VirtualTargetMBean[] _old = this.getVirtualTargets();
         VirtualTargetMBean[] _new = (VirtualTargetMBean[])((VirtualTargetMBean[])this._getHelper()._removeElement(_old, VirtualTargetMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setVirtualTargets(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public VirtualTargetMBean lookupVirtualTarget(String param0) {
      Object[] aary = (Object[])this._VirtualTargets;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      VirtualTargetMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (VirtualTargetMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMigratableTarget(MigratableTargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 71)) {
         MigratableTargetMBean[] _new;
         if (this._isSet(71)) {
            _new = (MigratableTargetMBean[])((MigratableTargetMBean[])this._getHelper()._extendArray(this.getMigratableTargets(), MigratableTargetMBean.class, param0));
         } else {
            _new = new MigratableTargetMBean[]{param0};
         }

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
      return this._MigratableTargets;
   }

   public boolean isMigratableTargetsInherited() {
      return false;
   }

   public boolean isMigratableTargetsSet() {
      return this._isSet(71);
   }

   public void removeMigratableTarget(MigratableTargetMBean param0) {
      this.destroyMigratableTarget(param0);
   }

   public void setMigratableTargets(MigratableTargetMBean[] param0) throws InvalidAttributeValueException {
      MigratableTargetMBean[] param0 = param0 == null ? new MigratableTargetMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 71)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MigratableTargetMBean[] _oldVal = this._MigratableTargets;
      this._MigratableTargets = (MigratableTargetMBean[])param0;
      this._postSet(71, _oldVal, param0);
   }

   public MigratableTargetMBean createMigratableTarget(String param0) {
      MigratableTargetMBeanImpl lookup = (MigratableTargetMBeanImpl)this.lookupMigratableTarget(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MigratableTargetMBeanImpl _val = new MigratableTargetMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMigratableTarget(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMigratableTarget(MigratableTargetMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 71);
         MigratableTargetMBean[] _old = this.getMigratableTargets();
         MigratableTargetMBean[] _new = (MigratableTargetMBean[])((MigratableTargetMBean[])this._getHelper()._removeElement(_old, MigratableTargetMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMigratableTargets(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public MigratableTargetMBean lookupMigratableTarget(String param0) {
      Object[] aary = (Object[])this._MigratableTargets;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MigratableTargetMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MigratableTargetMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public EJBContainerMBean getEJBContainer() {
      return this._EJBContainer;
   }

   public boolean isEJBContainerInherited() {
      return false;
   }

   public boolean isEJBContainerSet() {
      return this._isSet(72);
   }

   public void setEJBContainer(EJBContainerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getEJBContainer() != null && param0 != this.getEJBContainer()) {
         throw new BeanAlreadyExistsException(this.getEJBContainer() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 72)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EJBContainerMBean _oldVal = this._EJBContainer;
         this._EJBContainer = param0;
         this._postSet(72, _oldVal, param0);
      }
   }

   public EJBContainerMBean createEJBContainer() {
      EJBContainerMBeanImpl _val = new EJBContainerMBeanImpl(this, -1);

      try {
         this.setEJBContainer(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEJBContainer() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._EJBContainer;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setEJBContainer((EJBContainerMBean)null);
               this._unSet(72);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WebAppContainerMBean getWebAppContainer() {
      return this._WebAppContainer;
   }

   public boolean isWebAppContainerInherited() {
      return false;
   }

   public boolean isWebAppContainerSet() {
      return this._isSet(73) || this._isAnythingSet((AbstractDescriptorBean)this.getWebAppContainer());
   }

   public void setWebAppContainer(WebAppContainerMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 73)) {
         this._postCreate(_child);
      }

      WebAppContainerMBean _oldVal = this._WebAppContainer;
      this._WebAppContainer = param0;
      this._postSet(73, _oldVal, param0);
   }

   public CdiContainerMBean getCdiContainer() {
      return this._CdiContainer;
   }

   public boolean isCdiContainerInherited() {
      return false;
   }

   public boolean isCdiContainerSet() {
      return this._isSet(74) || this._isAnythingSet((AbstractDescriptorBean)this.getCdiContainer());
   }

   public void setCdiContainer(CdiContainerMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 74)) {
         this._postCreate(_child);
      }

      CdiContainerMBean _oldVal = this._CdiContainer;
      this._CdiContainer = param0;
      this._postSet(74, _oldVal, param0);
   }

   public JMXMBean getJMX() {
      return this._JMX;
   }

   public boolean isJMXInherited() {
      return false;
   }

   public boolean isJMXSet() {
      return this._isSet(75) || this._isAnythingSet((AbstractDescriptorBean)this.getJMX());
   }

   public void setJMX(JMXMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 75)) {
         this._postCreate(_child);
      }

      JMXMBean _oldVal = this._JMX;
      this._JMX = param0;
      this._postSet(75, _oldVal, param0);
   }

   public SelfTuningMBean getSelfTuning() {
      return this._SelfTuning;
   }

   public boolean isSelfTuningInherited() {
      return false;
   }

   public boolean isSelfTuningSet() {
      return this._isSet(76) || this._isAnythingSet((AbstractDescriptorBean)this.getSelfTuning());
   }

   public void setSelfTuning(SelfTuningMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 76)) {
         this._postCreate(_child);
      }

      SelfTuningMBean _oldVal = this._SelfTuning;
      this._SelfTuning = param0;
      this._postSet(76, _oldVal, param0);
   }

   public ResourceManagementMBean getResourceManagement() {
      return this._customizer.getResourceManagement();
   }

   public boolean isResourceManagementInherited() {
      return false;
   }

   public boolean isResourceManagementSet() {
      return this._isSet(77) || this._isAnythingSet((AbstractDescriptorBean)this.getResourceManagement());
   }

   public void setResourceManagement(ResourceManagementMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 77)) {
         this._postCreate(_child);
      }

      ResourceManagementMBean _oldVal = this._ResourceManagement;
      this._ResourceManagement = param0;
      this._postSet(77, _oldVal, param0);
   }

   public PathServiceMBean[] getPathServices() {
      return this._PathServices;
   }

   public boolean isPathServicesInherited() {
      return false;
   }

   public boolean isPathServicesSet() {
      return this._isSet(78);
   }

   public void removePathService(PathServiceMBean param0) {
      this.destroyPathService(param0);
   }

   public void setPathServices(PathServiceMBean[] param0) throws InvalidAttributeValueException {
      PathServiceMBean[] param0 = param0 == null ? new PathServiceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 78)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      PathServiceMBean[] _oldVal = this._PathServices;
      this._PathServices = (PathServiceMBean[])param0;
      this._postSet(78, _oldVal, param0);
   }

   public PathServiceMBean createPathService(String param0) {
      PathServiceMBeanImpl lookup = (PathServiceMBeanImpl)this.lookupPathService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         PathServiceMBeanImpl _val = new PathServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addPathService(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyPathService(PathServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 78);
         PathServiceMBean[] _old = this.getPathServices();
         PathServiceMBean[] _new = (PathServiceMBean[])((PathServiceMBean[])this._getHelper()._removeElement(_old, PathServiceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPathServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public PathServiceMBean lookupPathService(String param0) {
      Object[] aary = (Object[])this._PathServices;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PathServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PathServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addPathService(PathServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 78)) {
         PathServiceMBean[] _new;
         if (this._isSet(78)) {
            _new = (PathServiceMBean[])((PathServiceMBean[])this._getHelper()._extendArray(this.getPathServices(), PathServiceMBean.class, param0));
         } else {
            _new = new PathServiceMBean[]{param0};
         }

         try {
            this.setPathServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addJMSDestinationKey(JMSDestinationKeyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 79)) {
         JMSDestinationKeyMBean[] _new;
         if (this._isSet(79)) {
            _new = (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])this._getHelper()._extendArray(this.getJMSDestinationKeys(), JMSDestinationKeyMBean.class, param0));
         } else {
            _new = new JMSDestinationKeyMBean[]{param0};
         }

         try {
            this.setJMSDestinationKeys(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDestinationKeyMBean[] getJMSDestinationKeys() {
      return this._JMSDestinationKeys;
   }

   public boolean isJMSDestinationKeysInherited() {
      return false;
   }

   public boolean isJMSDestinationKeysSet() {
      return this._isSet(79);
   }

   public void removeJMSDestinationKey(JMSDestinationKeyMBean param0) {
      this.destroyJMSDestinationKey(param0);
   }

   public void setJMSDestinationKeys(JMSDestinationKeyMBean[] param0) throws InvalidAttributeValueException {
      JMSDestinationKeyMBean[] param0 = param0 == null ? new JMSDestinationKeyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 79)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSDestinationKeyMBean[] _oldVal = this._JMSDestinationKeys;
      this._JMSDestinationKeys = (JMSDestinationKeyMBean[])param0;
      this._postSet(79, _oldVal, param0);
   }

   public JMSDestinationKeyMBean createJMSDestinationKey(String param0) {
      JMSDestinationKeyMBeanImpl lookup = (JMSDestinationKeyMBeanImpl)this.lookupJMSDestinationKey(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSDestinationKeyMBeanImpl _val = new JMSDestinationKeyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSDestinationKey(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSDestinationKey(JMSDestinationKeyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 79);
         JMSDestinationKeyMBean[] _old = this.getJMSDestinationKeys();
         JMSDestinationKeyMBean[] _new = (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])this._getHelper()._removeElement(_old, JMSDestinationKeyMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSDestinationKeys(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSDestinationKeyMBean lookupJMSDestinationKey(String param0) {
      Object[] aary = (Object[])this._JMSDestinationKeys;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDestinationKeyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDestinationKeyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSConnectionFactory(JMSConnectionFactoryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 80)) {
         JMSConnectionFactoryMBean[] _new;
         if (this._isSet(80)) {
            _new = (JMSConnectionFactoryMBean[])((JMSConnectionFactoryMBean[])this._getHelper()._extendArray(this.getJMSConnectionFactories(), JMSConnectionFactoryMBean.class, param0));
         } else {
            _new = new JMSConnectionFactoryMBean[]{param0};
         }

         try {
            this.setJMSConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSConnectionFactoryMBean[] getJMSConnectionFactories() {
      return this._JMSConnectionFactories;
   }

   public boolean isJMSConnectionFactoriesInherited() {
      return false;
   }

   public boolean isJMSConnectionFactoriesSet() {
      return this._isSet(80);
   }

   public void removeJMSConnectionFactory(JMSConnectionFactoryMBean param0) {
      this.destroyJMSConnectionFactory(param0);
   }

   public void setJMSConnectionFactories(JMSConnectionFactoryMBean[] param0) throws InvalidAttributeValueException {
      JMSConnectionFactoryMBean[] param0 = param0 == null ? new JMSConnectionFactoryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 80)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSConnectionFactoryMBean[] _oldVal = this._JMSConnectionFactories;
      this._JMSConnectionFactories = (JMSConnectionFactoryMBean[])param0;
      this._postSet(80, _oldVal, param0);
   }

   public JMSConnectionFactoryMBean createJMSConnectionFactory(String param0) {
      JMSConnectionFactoryMBeanImpl lookup = (JMSConnectionFactoryMBeanImpl)this.lookupJMSConnectionFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSConnectionFactoryMBeanImpl _val = new JMSConnectionFactoryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSConnectionFactory(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSConnectionFactory(JMSConnectionFactoryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 80);
         JMSConnectionFactoryMBean[] _old = this.getJMSConnectionFactories();
         JMSConnectionFactoryMBean[] _new = (JMSConnectionFactoryMBean[])((JMSConnectionFactoryMBean[])this._getHelper()._removeElement(_old, JMSConnectionFactoryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSConnectionFactories(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSConnectionFactoryMBean lookupJMSConnectionFactory(String param0) {
      Object[] aary = (Object[])this._JMSConnectionFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSConnectionFactoryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSConnectionFactoryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSSessionPool(JMSSessionPoolMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 81)) {
         JMSSessionPoolMBean[] _new;
         if (this._isSet(81)) {
            _new = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])this._getHelper()._extendArray(this.getJMSSessionPools(), JMSSessionPoolMBean.class, param0));
         } else {
            _new = new JMSSessionPoolMBean[]{param0};
         }

         try {
            this.setJMSSessionPools(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSSessionPoolMBean[] getJMSSessionPools() {
      return this._JMSSessionPools;
   }

   public boolean isJMSSessionPoolsInherited() {
      return false;
   }

   public boolean isJMSSessionPoolsSet() {
      return this._isSet(81);
   }

   public void removeJMSSessionPool(JMSSessionPoolMBean param0) {
      this.destroyJMSSessionPool(param0);
   }

   public void setJMSSessionPools(JMSSessionPoolMBean[] param0) throws InvalidAttributeValueException {
      JMSSessionPoolMBean[] param0 = param0 == null ? new JMSSessionPoolMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 81)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMSSessionPoolMBean[] _oldVal = this._JMSSessionPools;
      this._JMSSessionPools = (JMSSessionPoolMBean[])param0;
      this._postSet(81, _oldVal, param0);
   }

   public JMSSessionPoolMBean createJMSSessionPool(String param0) {
      JMSSessionPoolMBeanImpl lookup = (JMSSessionPoolMBeanImpl)this.lookupJMSSessionPool(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSessionPoolMBeanImpl _val = new JMSSessionPoolMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSSessionPool(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public JMSSessionPoolMBean createJMSSessionPool(String param0, JMSSessionPoolMBean param1) {
      return this._customizer.createJMSSessionPool(param0, param1);
   }

   public void destroyJMSSessionPool(JMSSessionPoolMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 81);
         JMSSessionPoolMBean[] _old = this.getJMSSessionPools();
         JMSSessionPoolMBean[] _new = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])this._getHelper()._removeElement(_old, JMSSessionPoolMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSSessionPools(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSSessionPoolMBean lookupJMSSessionPool(String param0) {
      Object[] aary = (Object[])this._JMSSessionPools;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSSessionPoolMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSSessionPoolMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSBridgeDestinationMBean createJMSBridgeDestination(String param0) {
      JMSBridgeDestinationMBeanImpl lookup = (JMSBridgeDestinationMBeanImpl)this.lookupJMSBridgeDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSBridgeDestinationMBeanImpl _val = new JMSBridgeDestinationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSBridgeDestination(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSBridgeDestination(JMSBridgeDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 82);
         JMSBridgeDestinationMBean[] _old = this.getJMSBridgeDestinations();
         JMSBridgeDestinationMBean[] _new = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])this._getHelper()._removeElement(_old, JMSBridgeDestinationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSBridgeDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSBridgeDestinationMBean lookupJMSBridgeDestination(String param0) {
      Object[] aary = (Object[])this._JMSBridgeDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSBridgeDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSBridgeDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSBridgeDestinationMBean[] getJMSBridgeDestinations() {
      return this._JMSBridgeDestinations;
   }

   public boolean isJMSBridgeDestinationsInherited() {
      return false;
   }

   public boolean isJMSBridgeDestinationsSet() {
      return this._isSet(82);
   }

   public void removeJMSBridgeDestination(JMSBridgeDestinationMBean param0) {
      this.destroyJMSBridgeDestination(param0);
   }

   public void setJMSBridgeDestinations(JMSBridgeDestinationMBean[] param0) throws InvalidAttributeValueException {
      JMSBridgeDestinationMBean[] param0 = param0 == null ? new JMSBridgeDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 82)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSBridgeDestinationMBean[] _oldVal = this._JMSBridgeDestinations;
      this._JMSBridgeDestinations = (JMSBridgeDestinationMBean[])param0;
      this._postSet(82, _oldVal, param0);
   }

   public void addJMSBridgeDestination(JMSBridgeDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 82)) {
         JMSBridgeDestinationMBean[] _new;
         if (this._isSet(82)) {
            _new = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])this._getHelper()._extendArray(this.getJMSBridgeDestinations(), JMSBridgeDestinationMBean.class, param0));
         } else {
            _new = new JMSBridgeDestinationMBean[]{param0};
         }

         try {
            this.setJMSBridgeDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public BridgeDestinationMBean createBridgeDestination(String param0) {
      BridgeDestinationMBeanImpl lookup = (BridgeDestinationMBeanImpl)this.lookupBridgeDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         BridgeDestinationMBeanImpl _val = new BridgeDestinationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addBridgeDestination(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyBridgeDestination(BridgeDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 83);
         BridgeDestinationMBean[] _old = this.getBridgeDestinations();
         BridgeDestinationMBean[] _new = (BridgeDestinationMBean[])((BridgeDestinationMBean[])this._getHelper()._removeElement(_old, BridgeDestinationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setBridgeDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public BridgeDestinationMBean lookupBridgeDestination(String param0) {
      Object[] aary = (Object[])this._BridgeDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      BridgeDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (BridgeDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addBridgeDestination(BridgeDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 83)) {
         BridgeDestinationMBean[] _new;
         if (this._isSet(83)) {
            _new = (BridgeDestinationMBean[])((BridgeDestinationMBean[])this._getHelper()._extendArray(this.getBridgeDestinations(), BridgeDestinationMBean.class, param0));
         } else {
            _new = new BridgeDestinationMBean[]{param0};
         }

         try {
            this.setBridgeDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public BridgeDestinationMBean[] getBridgeDestinations() {
      return this._BridgeDestinations;
   }

   public boolean isBridgeDestinationsInherited() {
      return false;
   }

   public boolean isBridgeDestinationsSet() {
      return this._isSet(83);
   }

   public void removeBridgeDestination(BridgeDestinationMBean param0) {
      this.destroyBridgeDestination(param0);
   }

   public void setBridgeDestinations(BridgeDestinationMBean[] param0) throws InvalidAttributeValueException {
      BridgeDestinationMBean[] param0 = param0 == null ? new BridgeDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 83)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      BridgeDestinationMBean[] _oldVal = this._BridgeDestinations;
      this._BridgeDestinations = (BridgeDestinationMBean[])param0;
      this._postSet(83, _oldVal, param0);
   }

   public void addForeignJMSServer(ForeignJMSServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 84)) {
         ForeignJMSServerMBean[] _new;
         if (this._isSet(84)) {
            _new = (ForeignJMSServerMBean[])((ForeignJMSServerMBean[])this._getHelper()._extendArray(this.getForeignJMSServers(), ForeignJMSServerMBean.class, param0));
         } else {
            _new = new ForeignJMSServerMBean[]{param0};
         }

         try {
            this.setForeignJMSServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJMSServerMBean[] getForeignJMSServers() {
      return this._ForeignJMSServers;
   }

   public boolean isForeignJMSServersInherited() {
      return false;
   }

   public boolean isForeignJMSServersSet() {
      return this._isSet(84);
   }

   public void removeForeignJMSServer(ForeignJMSServerMBean param0) {
      this.destroyForeignJMSServer(param0);
   }

   public void setForeignJMSServers(ForeignJMSServerMBean[] param0) throws InvalidAttributeValueException {
      ForeignJMSServerMBean[] param0 = param0 == null ? new ForeignJMSServerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 84)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ForeignJMSServerMBean[] _oldVal = this._ForeignJMSServers;
      this._ForeignJMSServers = (ForeignJMSServerMBean[])param0;
      this._postSet(84, _oldVal, param0);
   }

   public ForeignJMSServerMBean createForeignJMSServer(String param0) {
      ForeignJMSServerMBeanImpl lookup = (ForeignJMSServerMBeanImpl)this.lookupForeignJMSServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJMSServerMBeanImpl _val = new ForeignJMSServerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJMSServer(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyForeignJMSServer(ForeignJMSServerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 84);
         ForeignJMSServerMBean[] _old = this.getForeignJMSServers();
         ForeignJMSServerMBean[] _new = (ForeignJMSServerMBean[])((ForeignJMSServerMBean[])this._getHelper()._removeElement(_old, ForeignJMSServerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setForeignJMSServers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ForeignJMSServerMBean lookupForeignJMSServer(String param0) {
      Object[] aary = (Object[])this._ForeignJMSServers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJMSServerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJMSServerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addShutdownClass(ShutdownClassMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 85)) {
         ShutdownClassMBean[] _new;
         if (this._isSet(85)) {
            _new = (ShutdownClassMBean[])((ShutdownClassMBean[])this._getHelper()._extendArray(this.getShutdownClasses(), ShutdownClassMBean.class, param0));
         } else {
            _new = new ShutdownClassMBean[]{param0};
         }

         try {
            this.setShutdownClasses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ShutdownClassMBean[] getShutdownClasses() {
      return this._ShutdownClasses;
   }

   public boolean isShutdownClassesInherited() {
      return false;
   }

   public boolean isShutdownClassesSet() {
      return this._isSet(85);
   }

   public void removeShutdownClass(ShutdownClassMBean param0) {
      this.destroyShutdownClass(param0);
   }

   public void setShutdownClasses(ShutdownClassMBean[] param0) throws InvalidAttributeValueException {
      ShutdownClassMBean[] param0 = param0 == null ? new ShutdownClassMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 85)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ShutdownClassMBean[] _oldVal = this._ShutdownClasses;
      this._ShutdownClasses = (ShutdownClassMBean[])param0;
      this._postSet(85, _oldVal, param0);
   }

   public ShutdownClassMBean createShutdownClass(String param0) {
      ShutdownClassMBeanImpl lookup = (ShutdownClassMBeanImpl)this.lookupShutdownClass(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ShutdownClassMBeanImpl _val = new ShutdownClassMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addShutdownClass(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyShutdownClass(ShutdownClassMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 85);
         ShutdownClassMBean[] _old = this.getShutdownClasses();
         ShutdownClassMBean[] _new = (ShutdownClassMBean[])((ShutdownClassMBean[])this._getHelper()._removeElement(_old, ShutdownClassMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setShutdownClasses(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ShutdownClassMBean lookupShutdownClass(String param0) {
      Object[] aary = (Object[])this._ShutdownClasses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ShutdownClassMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ShutdownClassMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addStartupClass(StartupClassMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 86)) {
         StartupClassMBean[] _new;
         if (this._isSet(86)) {
            _new = (StartupClassMBean[])((StartupClassMBean[])this._getHelper()._extendArray(this.getStartupClasses(), StartupClassMBean.class, param0));
         } else {
            _new = new StartupClassMBean[]{param0};
         }

         try {
            this.setStartupClasses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public StartupClassMBean[] getStartupClasses() {
      return this._StartupClasses;
   }

   public boolean isStartupClassesInherited() {
      return false;
   }

   public boolean isStartupClassesSet() {
      return this._isSet(86);
   }

   public void removeStartupClass(StartupClassMBean param0) {
      this.destroyStartupClass(param0);
   }

   public void setStartupClasses(StartupClassMBean[] param0) throws InvalidAttributeValueException {
      StartupClassMBean[] param0 = param0 == null ? new StartupClassMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 86)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      StartupClassMBean[] _oldVal = this._StartupClasses;
      this._StartupClasses = (StartupClassMBean[])param0;
      this._postSet(86, _oldVal, param0);
   }

   public StartupClassMBean createStartupClass(String param0) {
      StartupClassMBeanImpl lookup = (StartupClassMBeanImpl)this.lookupStartupClass(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         StartupClassMBeanImpl _val = new StartupClassMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addStartupClass(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyStartupClass(StartupClassMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 86);
         StartupClassMBean[] _old = this.getStartupClasses();
         StartupClassMBean[] _new = (StartupClassMBean[])((StartupClassMBean[])this._getHelper()._removeElement(_old, StartupClassMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setStartupClasses(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public StartupClassMBean lookupStartupClass(String param0) {
      Object[] aary = (Object[])this._StartupClasses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      StartupClassMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (StartupClassMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSingletonService(SingletonServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 87)) {
         SingletonServiceMBean[] _new;
         if (this._isSet(87)) {
            _new = (SingletonServiceMBean[])((SingletonServiceMBean[])this._getHelper()._extendArray(this.getSingletonServices(), SingletonServiceMBean.class, param0));
         } else {
            _new = new SingletonServiceMBean[]{param0};
         }

         try {
            this.setSingletonServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SingletonServiceMBean[] getSingletonServices() {
      return this._SingletonServices;
   }

   public boolean isSingletonServicesInherited() {
      return false;
   }

   public boolean isSingletonServicesSet() {
      return this._isSet(87);
   }

   public void removeSingletonService(SingletonServiceMBean param0) {
      this.destroySingletonService(param0);
   }

   public void setSingletonServices(SingletonServiceMBean[] param0) throws InvalidAttributeValueException {
      SingletonServiceMBean[] param0 = param0 == null ? new SingletonServiceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 87)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SingletonServiceMBean[] _oldVal = this._SingletonServices;
      this._SingletonServices = (SingletonServiceMBean[])param0;
      this._postSet(87, _oldVal, param0);
   }

   public SingletonServiceMBean createSingletonService(String param0) {
      SingletonServiceMBeanImpl lookup = (SingletonServiceMBeanImpl)this.lookupSingletonService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SingletonServiceMBeanImpl _val = new SingletonServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSingletonService(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroySingletonService(SingletonServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 87);
         SingletonServiceMBean[] _old = this.getSingletonServices();
         SingletonServiceMBean[] _new = (SingletonServiceMBean[])((SingletonServiceMBean[])this._getHelper()._removeElement(_old, SingletonServiceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSingletonServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SingletonServiceMBean lookupSingletonService(String param0) {
      Object[] aary = (Object[])this._SingletonServices;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SingletonServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SingletonServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public MailSessionMBean[] getMailSessions() {
      return this._MailSessions;
   }

   public boolean isMailSessionsInherited() {
      return false;
   }

   public boolean isMailSessionsSet() {
      return this._isSet(88);
   }

   public void removeMailSession(MailSessionMBean param0) {
      this.destroyMailSession(param0);
   }

   public void setMailSessions(MailSessionMBean[] param0) throws InvalidAttributeValueException {
      MailSessionMBean[] param0 = param0 == null ? new MailSessionMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 88)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MailSessionMBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionMBean[])param0;
      this._postSet(88, _oldVal, param0);
   }

   public MailSessionMBean createMailSession(String param0) {
      MailSessionMBeanImpl lookup = (MailSessionMBeanImpl)this.lookupMailSession(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MailSessionMBeanImpl _val = new MailSessionMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMailSession(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMailSession(MailSessionMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 88);
         MailSessionMBean[] _old = this.getMailSessions();
         MailSessionMBean[] _new = (MailSessionMBean[])((MailSessionMBean[])this._getHelper()._removeElement(_old, MailSessionMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMailSessions(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public MailSessionMBean lookupMailSession(String param0) {
      Object[] aary = (Object[])this._MailSessions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MailSessionMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MailSessionMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMailSession(MailSessionMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 88)) {
         MailSessionMBean[] _new;
         if (this._isSet(88)) {
            _new = (MailSessionMBean[])((MailSessionMBean[])this._getHelper()._extendArray(this.getMailSessions(), MailSessionMBean.class, param0));
         } else {
            _new = new MailSessionMBean[]{param0};
         }

         try {
            this.setMailSessions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addJoltConnectionPool(JoltConnectionPoolMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 89)) {
         JoltConnectionPoolMBean[] _new;
         if (this._isSet(89)) {
            _new = (JoltConnectionPoolMBean[])((JoltConnectionPoolMBean[])this._getHelper()._extendArray(this.getJoltConnectionPools(), JoltConnectionPoolMBean.class, param0));
         } else {
            _new = new JoltConnectionPoolMBean[]{param0};
         }

         try {
            this.setJoltConnectionPools(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JoltConnectionPoolMBean[] getJoltConnectionPools() {
      return this._JoltConnectionPools;
   }

   public boolean isJoltConnectionPoolsInherited() {
      return false;
   }

   public boolean isJoltConnectionPoolsSet() {
      return this._isSet(89);
   }

   public void removeJoltConnectionPool(JoltConnectionPoolMBean param0) {
      this.destroyJoltConnectionPool(param0);
   }

   public void setJoltConnectionPools(JoltConnectionPoolMBean[] param0) throws InvalidAttributeValueException {
      JoltConnectionPoolMBean[] param0 = param0 == null ? new JoltConnectionPoolMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 89)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JoltConnectionPoolMBean[] _oldVal = this._JoltConnectionPools;
      this._JoltConnectionPools = (JoltConnectionPoolMBean[])param0;
      this._postSet(89, _oldVal, param0);
   }

   public JoltConnectionPoolMBean createJoltConnectionPool(String param0) {
      JoltConnectionPoolMBeanImpl lookup = (JoltConnectionPoolMBeanImpl)this.lookupJoltConnectionPool(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JoltConnectionPoolMBeanImpl _val = new JoltConnectionPoolMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJoltConnectionPool(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJoltConnectionPool(JoltConnectionPoolMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 89);
         JoltConnectionPoolMBean[] _old = this.getJoltConnectionPools();
         JoltConnectionPoolMBean[] _new = (JoltConnectionPoolMBean[])((JoltConnectionPoolMBean[])this._getHelper()._removeElement(_old, JoltConnectionPoolMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJoltConnectionPools(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JoltConnectionPoolMBean lookupJoltConnectionPool(String param0) {
      Object[] aary = (Object[])this._JoltConnectionPools;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JoltConnectionPoolMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JoltConnectionPoolMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addLogFilter(LogFilterMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 90)) {
         LogFilterMBean[] _new;
         if (this._isSet(90)) {
            _new = (LogFilterMBean[])((LogFilterMBean[])this._getHelper()._extendArray(this.getLogFilters(), LogFilterMBean.class, param0));
         } else {
            _new = new LogFilterMBean[]{param0};
         }

         try {
            this.setLogFilters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LogFilterMBean[] getLogFilters() {
      return this._LogFilters;
   }

   public boolean isLogFiltersInherited() {
      return false;
   }

   public boolean isLogFiltersSet() {
      return this._isSet(90);
   }

   public void removeLogFilter(LogFilterMBean param0) {
      this.destroyLogFilter(param0);
   }

   public void setLogFilters(LogFilterMBean[] param0) throws InvalidAttributeValueException {
      LogFilterMBean[] param0 = param0 == null ? new LogFilterMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 90)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      LogFilterMBean[] _oldVal = this._LogFilters;
      this._LogFilters = (LogFilterMBean[])param0;
      this._postSet(90, _oldVal, param0);
   }

   public LogFilterMBean createLogFilter(String param0) {
      LogFilterMBeanImpl lookup = (LogFilterMBeanImpl)this.lookupLogFilter(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         LogFilterMBeanImpl _val = new LogFilterMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addLogFilter(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyLogFilter(LogFilterMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 90);
         LogFilterMBean[] _old = this.getLogFilters();
         LogFilterMBean[] _new = (LogFilterMBean[])((LogFilterMBean[])this._getHelper()._removeElement(_old, LogFilterMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLogFilters(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public LogFilterMBean lookupLogFilter(String param0) {
      Object[] aary = (Object[])this._LogFilters;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      LogFilterMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (LogFilterMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public FileStoreMBean[] getFileStores() {
      return this._FileStores;
   }

   public boolean isFileStoresInherited() {
      return false;
   }

   public boolean isFileStoresSet() {
      return this._isSet(91);
   }

   public void removeFileStore(FileStoreMBean param0) {
      this.destroyFileStore(param0);
   }

   public void setFileStores(FileStoreMBean[] param0) throws InvalidAttributeValueException {
      FileStoreMBean[] param0 = param0 == null ? new FileStoreMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 91)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      FileStoreMBean[] _oldVal = this._FileStores;
      this._FileStores = (FileStoreMBean[])param0;
      this._postSet(91, _oldVal, param0);
   }

   public FileStoreMBean createFileStore(String param0) {
      FileStoreMBeanImpl lookup = (FileStoreMBeanImpl)this.lookupFileStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         FileStoreMBeanImpl _val = new FileStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addFileStore(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyFileStore(FileStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 91);
         FileStoreMBean[] _old = this.getFileStores();
         FileStoreMBean[] _new = (FileStoreMBean[])((FileStoreMBean[])this._getHelper()._removeElement(_old, FileStoreMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFileStores(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public FileStoreMBean lookupFileStore(String param0) {
      Object[] aary = (Object[])this._FileStores;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      FileStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (FileStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addFileStore(FileStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 91)) {
         FileStoreMBean[] _new;
         if (this._isSet(91)) {
            _new = (FileStoreMBean[])((FileStoreMBean[])this._getHelper()._extendArray(this.getFileStores(), FileStoreMBean.class, param0));
         } else {
            _new = new FileStoreMBean[]{param0};
         }

         try {
            this.setFileStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addReplicatedStore(ReplicatedStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 92)) {
         ReplicatedStoreMBean[] _new;
         if (this._isSet(92)) {
            _new = (ReplicatedStoreMBean[])((ReplicatedStoreMBean[])this._getHelper()._extendArray(this.getReplicatedStores(), ReplicatedStoreMBean.class, param0));
         } else {
            _new = new ReplicatedStoreMBean[]{param0};
         }

         try {
            this.setReplicatedStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ReplicatedStoreMBean[] getReplicatedStores() {
      return this._ReplicatedStores;
   }

   public boolean isReplicatedStoresInherited() {
      return false;
   }

   public boolean isReplicatedStoresSet() {
      return this._isSet(92);
   }

   public void removeReplicatedStore(ReplicatedStoreMBean param0) {
      this.destroyReplicatedStore(param0);
   }

   public void setReplicatedStores(ReplicatedStoreMBean[] param0) throws InvalidAttributeValueException {
      ReplicatedStoreMBean[] param0 = param0 == null ? new ReplicatedStoreMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 92)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ReplicatedStoreMBean[] _oldVal = this._ReplicatedStores;
      this._ReplicatedStores = (ReplicatedStoreMBean[])param0;
      this._postSet(92, _oldVal, param0);
   }

   public ReplicatedStoreMBean createReplicatedStore(String param0) {
      ReplicatedStoreMBeanImpl lookup = (ReplicatedStoreMBeanImpl)this.lookupReplicatedStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ReplicatedStoreMBeanImpl _val = new ReplicatedStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addReplicatedStore(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyReplicatedStore(ReplicatedStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 92);
         ReplicatedStoreMBean[] _old = this.getReplicatedStores();
         ReplicatedStoreMBean[] _new = (ReplicatedStoreMBean[])((ReplicatedStoreMBean[])this._getHelper()._removeElement(_old, ReplicatedStoreMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setReplicatedStores(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ReplicatedStoreMBean lookupReplicatedStore(String param0) {
      Object[] aary = (Object[])this._ReplicatedStores;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ReplicatedStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ReplicatedStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JDBCStoreMBean[] getJDBCStores() {
      return this._JDBCStores;
   }

   public boolean isJDBCStoresInherited() {
      return false;
   }

   public boolean isJDBCStoresSet() {
      return this._isSet(93);
   }

   public void removeJDBCStore(JDBCStoreMBean param0) {
      this.destroyJDBCStore(param0);
   }

   public void setJDBCStores(JDBCStoreMBean[] param0) throws InvalidAttributeValueException {
      JDBCStoreMBean[] param0 = param0 == null ? new JDBCStoreMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 93)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JDBCStoreMBean[] _oldVal = this._JDBCStores;
      this._JDBCStores = (JDBCStoreMBean[])param0;
      this._postSet(93, _oldVal, param0);
   }

   public JDBCStoreMBean createJDBCStore(String param0) {
      JDBCStoreMBeanImpl lookup = (JDBCStoreMBeanImpl)this.lookupJDBCStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCStoreMBeanImpl _val = new JDBCStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCStore(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJDBCStore(JDBCStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 93);
         JDBCStoreMBean[] _old = this.getJDBCStores();
         JDBCStoreMBean[] _new = (JDBCStoreMBean[])((JDBCStoreMBean[])this._getHelper()._removeElement(_old, JDBCStoreMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDBCStores(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JDBCStoreMBean lookupJDBCStore(String param0) {
      Object[] aary = (Object[])this._JDBCStores;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJDBCStore(JDBCStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 93)) {
         JDBCStoreMBean[] _new;
         if (this._isSet(93)) {
            _new = (JDBCStoreMBean[])((JDBCStoreMBean[])this._getHelper()._extendArray(this.getJDBCStores(), JDBCStoreMBean.class, param0));
         } else {
            _new = new JDBCStoreMBean[]{param0};
         }

         try {
            this.setJDBCStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSSystemResourceMBean[] getJMSSystemResources() {
      return this._JMSSystemResources;
   }

   public boolean isJMSSystemResourcesInherited() {
      return false;
   }

   public boolean isJMSSystemResourcesSet() {
      return this._isSet(94);
   }

   public void removeJMSSystemResource(JMSSystemResourceMBean param0) {
      this.destroyJMSSystemResource(param0);
   }

   public void setJMSSystemResources(JMSSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      JMSSystemResourceMBean[] param0 = param0 == null ? new JMSSystemResourceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 94)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMSSystemResourceMBean[] _oldVal = this._JMSSystemResources;
      this._JMSSystemResources = (JMSSystemResourceMBean[])param0;
      this._postSet(94, _oldVal, param0);
   }

   public JMSSystemResourceMBean createJMSSystemResource(String param0) {
      JMSSystemResourceMBeanImpl lookup = (JMSSystemResourceMBeanImpl)this.lookupJMSSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSystemResourceMBeanImpl _val = new JMSSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSSystemResource(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public JMSSystemResourceMBean createJMSSystemResource(String param0, String param1) {
      JMSSystemResourceMBeanImpl lookup = (JMSSystemResourceMBeanImpl)this.lookupJMSSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSystemResourceMBeanImpl _val = new JMSSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setDescriptorFileName(param1);
            this.addJMSSystemResource(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyJMSSystemResource(JMSSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 94);
         JMSSystemResourceMBean[] _old = this.getJMSSystemResources();
         JMSSystemResourceMBean[] _new = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])this._getHelper()._removeElement(_old, JMSSystemResourceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSSystemResources(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSSystemResourceMBean lookupJMSSystemResource(String param0) {
      Object[] aary = (Object[])this._JMSSystemResources;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSSystemResource(JMSSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 94)) {
         JMSSystemResourceMBean[] _new;
         if (this._isSet(94)) {
            _new = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])this._getHelper()._extendArray(this.getJMSSystemResources(), JMSSystemResourceMBean.class, param0));
         } else {
            _new = new JMSSystemResourceMBean[]{param0};
         }

         try {
            this.setJMSSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addCustomResource(CustomResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 95)) {
         CustomResourceMBean[] _new;
         if (this._isSet(95)) {
            _new = (CustomResourceMBean[])((CustomResourceMBean[])this._getHelper()._extendArray(this.getCustomResources(), CustomResourceMBean.class, param0));
         } else {
            _new = new CustomResourceMBean[]{param0};
         }

         try {
            this.setCustomResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CustomResourceMBean[] getCustomResources() {
      return this._CustomResources;
   }

   public boolean isCustomResourcesInherited() {
      return false;
   }

   public boolean isCustomResourcesSet() {
      return this._isSet(95);
   }

   public void removeCustomResource(CustomResourceMBean param0) {
      this.destroyCustomResource(param0);
   }

   public void setCustomResources(CustomResourceMBean[] param0) throws InvalidAttributeValueException {
      CustomResourceMBean[] param0 = param0 == null ? new CustomResourceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 95)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CustomResourceMBean[] _oldVal = this._CustomResources;
      this._CustomResources = (CustomResourceMBean[])param0;
      this._postSet(95, _oldVal, param0);
   }

   public CustomResourceMBean createCustomResource(String param0, String param1, String param2) {
      CustomResourceMBeanImpl lookup = (CustomResourceMBeanImpl)this.lookupCustomResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CustomResourceMBeanImpl _val = new CustomResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setResourceClass(param1);
            _val.setDescriptorBeanClass(param2);
            this.addCustomResource(_val);
            return _val;
         } catch (Exception var7) {
            if (var7 instanceof RuntimeException) {
               throw (RuntimeException)var7;
            } else {
               throw new UndeclaredThrowableException(var7);
            }
         }
      }
   }

   public CustomResourceMBean createCustomResource(String param0, String param1, String param2, String param3) {
      CustomResourceMBeanImpl lookup = (CustomResourceMBeanImpl)this.lookupCustomResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CustomResourceMBeanImpl _val = new CustomResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setResourceClass(param1);
            _val.setDescriptorBeanClass(param2);
            _val.setDescriptorFileName(param3);
            this.addCustomResource(_val);
            return _val;
         } catch (Exception var8) {
            if (var8 instanceof RuntimeException) {
               throw (RuntimeException)var8;
            } else {
               throw new UndeclaredThrowableException(var8);
            }
         }
      }
   }

   public void destroyCustomResource(CustomResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 95);
         CustomResourceMBean[] _old = this.getCustomResources();
         CustomResourceMBean[] _new = (CustomResourceMBean[])((CustomResourceMBean[])this._getHelper()._removeElement(_old, CustomResourceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomResources(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public CustomResourceMBean lookupCustomResource(String param0) {
      Object[] aary = (Object[])this._CustomResources;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CustomResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CustomResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJNDIProviderMBean[] getForeignJNDIProviders() {
      return this._ForeignJNDIProviders;
   }

   public boolean isForeignJNDIProvidersInherited() {
      return false;
   }

   public boolean isForeignJNDIProvidersSet() {
      return this._isSet(96);
   }

   public void removeForeignJNDIProvider(ForeignJNDIProviderMBean param0) {
      this.destroyForeignJNDIProvider(param0);
   }

   public void setForeignJNDIProviders(ForeignJNDIProviderMBean[] param0) throws InvalidAttributeValueException {
      ForeignJNDIProviderMBean[] param0 = param0 == null ? new ForeignJNDIProviderMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 96)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ForeignJNDIProviderMBean[] _oldVal = this._ForeignJNDIProviders;
      this._ForeignJNDIProviders = (ForeignJNDIProviderMBean[])param0;
      this._postSet(96, _oldVal, param0);
   }

   public ForeignJNDIProviderMBean lookupForeignJNDIProvider(String param0) {
      Object[] aary = (Object[])this._ForeignJNDIProviders;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJNDIProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJNDIProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJNDIProviderMBean createForeignJNDIProvider(String param0) {
      ForeignJNDIProviderMBeanImpl lookup = (ForeignJNDIProviderMBeanImpl)this.lookupForeignJNDIProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJNDIProviderMBeanImpl _val = new ForeignJNDIProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJNDIProvider(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void addForeignJNDIProvider(ForeignJNDIProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 96)) {
         ForeignJNDIProviderMBean[] _new;
         if (this._isSet(96)) {
            _new = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])this._getHelper()._extendArray(this.getForeignJNDIProviders(), ForeignJNDIProviderMBean.class, param0));
         } else {
            _new = new ForeignJNDIProviderMBean[]{param0};
         }

         try {
            this.setForeignJNDIProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void destroyForeignJNDIProvider(ForeignJNDIProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 96);
         ForeignJNDIProviderMBean[] _old = this.getForeignJNDIProviders();
         ForeignJNDIProviderMBean[] _new = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])this._getHelper()._removeElement(_old, ForeignJNDIProviderMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setForeignJNDIProviders(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public String getAdminServerName() {
      return this._AdminServerName;
   }

   public boolean isAdminServerNameInherited() {
      return false;
   }

   public boolean isAdminServerNameSet() {
      return this._isSet(97);
   }

   public void setAdminServerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdminServerName;
      this._AdminServerName = param0;
      this._postSet(97, _oldVal, param0);
   }

   public String getAdministrationProtocol() {
      return this._AdministrationProtocol;
   }

   public boolean isAdministrationProtocolInherited() {
      return false;
   }

   public boolean isAdministrationProtocolSet() {
      return this._isSet(98);
   }

   public void setAdministrationProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      DomainValidator.validateAdministrationProtocol(param0);
      String _oldVal = this._AdministrationProtocol;
      this._AdministrationProtocol = param0;
      this._postSet(98, _oldVal, param0);
   }

   public WLDFSystemResourceMBean[] getWLDFSystemResources() {
      return this._WLDFSystemResources;
   }

   public boolean isWLDFSystemResourcesInherited() {
      return false;
   }

   public boolean isWLDFSystemResourcesSet() {
      return this._isSet(99);
   }

   public void removeWLDFSystemResource(WLDFSystemResourceMBean param0) {
      this.destroyWLDFSystemResource(param0);
   }

   public void setWLDFSystemResources(WLDFSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      WLDFSystemResourceMBean[] param0 = param0 == null ? new WLDFSystemResourceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 99)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WLDFSystemResourceMBean[] _oldVal = this._WLDFSystemResources;
      this._WLDFSystemResources = (WLDFSystemResourceMBean[])param0;
      this._postSet(99, _oldVal, param0);
   }

   public WLDFSystemResourceMBean createWLDFSystemResource(String param0) {
      WLDFSystemResourceMBeanImpl lookup = (WLDFSystemResourceMBeanImpl)this.lookupWLDFSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFSystemResourceMBeanImpl _val = new WLDFSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWLDFSystemResource(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public WLDFSystemResourceMBean createWLDFSystemResource(String param0, String param1) {
      WLDFSystemResourceMBeanImpl lookup = (WLDFSystemResourceMBeanImpl)this.lookupWLDFSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFSystemResourceMBeanImpl _val = new WLDFSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setDescriptorFileName(param1);
            this.addWLDFSystemResource(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public WLDFSystemResourceMBean createWLDFSystemResourceFromBuiltin(String param0, String param1) {
      return this._customizer.createWLDFSystemResourceFromBuiltin(param0, param1);
   }

   public void destroyWLDFSystemResource(WLDFSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 99);
         WLDFSystemResourceMBean[] _old = this.getWLDFSystemResources();
         WLDFSystemResourceMBean[] _new = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])this._getHelper()._removeElement(_old, WLDFSystemResourceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWLDFSystemResources(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public WLDFSystemResourceMBean lookupWLDFSystemResource(String param0) {
      Object[] aary = (Object[])this._WLDFSystemResources;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addWLDFSystemResource(WLDFSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 99)) {
         WLDFSystemResourceMBean[] _new;
         if (this._isSet(99)) {
            _new = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])this._getHelper()._extendArray(this.getWLDFSystemResources(), WLDFSystemResourceMBean.class, param0));
         } else {
            _new = new WLDFSystemResourceMBean[]{param0};
         }

         try {
            this.setWLDFSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCSystemResourceMBean[] getJDBCSystemResources() {
      return this._JDBCSystemResources;
   }

   public boolean isJDBCSystemResourcesInherited() {
      return false;
   }

   public boolean isJDBCSystemResourcesSet() {
      return this._isSet(100);
   }

   public void removeJDBCSystemResource(JDBCSystemResourceMBean param0) {
      this.destroyJDBCSystemResource(param0);
   }

   public void setJDBCSystemResources(JDBCSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      JDBCSystemResourceMBean[] param0 = param0 == null ? new JDBCSystemResourceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 100)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JDBCSystemResourceMBean[] _oldVal = this._JDBCSystemResources;
      this._JDBCSystemResources = (JDBCSystemResourceMBean[])param0;
      this._postSet(100, _oldVal, param0);
   }

   public JDBCSystemResourceMBean createJDBCSystemResource(String param0) {
      JDBCSystemResourceMBeanImpl lookup = (JDBCSystemResourceMBeanImpl)this.lookupJDBCSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCSystemResourceMBeanImpl _val = new JDBCSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCSystemResource(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public JDBCSystemResourceMBean createJDBCSystemResource(String param0, String param1) {
      JDBCSystemResourceMBeanImpl lookup = (JDBCSystemResourceMBeanImpl)this.lookupJDBCSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCSystemResourceMBeanImpl _val = new JDBCSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setDescriptorFileName(param1);
            this.addJDBCSystemResource(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public JDBCSystemResourceMBean lookupJDBCSystemResource(String param0) {
      Object[] aary = (Object[])this._JDBCSystemResources;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyJDBCSystemResource(JDBCSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 100);
         JDBCSystemResourceMBean[] _old = this.getJDBCSystemResources();
         JDBCSystemResourceMBean[] _new = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])this._getHelper()._removeElement(_old, JDBCSystemResourceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDBCSystemResources(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addJDBCSystemResource(JDBCSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 100)) {
         JDBCSystemResourceMBean[] _new;
         if (this._isSet(100)) {
            _new = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])this._getHelper()._extendArray(this.getJDBCSystemResources(), JDBCSystemResourceMBean.class, param0));
         } else {
            _new = new JDBCSystemResourceMBean[]{param0};
         }

         try {
            this.setJDBCSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addSystemResource(SystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 101)) {
         SystemResourceMBean[] _new = (SystemResourceMBean[])((SystemResourceMBean[])this._getHelper()._extendArray(this.getSystemResources(), SystemResourceMBean.class, param0));

         try {
            this.setSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SystemResourceMBean[] getSystemResources() {
      return this._customizer.getSystemResources();
   }

   public boolean isSystemResourcesInherited() {
      return false;
   }

   public boolean isSystemResourcesSet() {
      return this._isSet(101);
   }

   public void removeSystemResource(SystemResourceMBean param0) {
      SystemResourceMBean[] _old = this.getSystemResources();
      SystemResourceMBean[] _new = (SystemResourceMBean[])((SystemResourceMBean[])this._getHelper()._removeElement(_old, SystemResourceMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setSystemResources(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setSystemResources(SystemResourceMBean[] param0) throws InvalidAttributeValueException {
      SystemResourceMBean[] param0 = param0 == null ? new SystemResourceMBeanImpl[0] : param0;
      this._SystemResources = (SystemResourceMBean[])param0;
   }

   public SystemResourceMBean lookupSystemResource(String param0) {
      return this._customizer.lookupSystemResource(param0);
   }

   public SAFAgentMBean[] getSAFAgents() {
      return this._SAFAgents;
   }

   public boolean isSAFAgentsInherited() {
      return false;
   }

   public boolean isSAFAgentsSet() {
      return this._isSet(102);
   }

   public void removeSAFAgent(SAFAgentMBean param0) {
      this.destroySAFAgent(param0);
   }

   public void setSAFAgents(SAFAgentMBean[] param0) throws InvalidAttributeValueException {
      SAFAgentMBean[] param0 = param0 == null ? new SAFAgentMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 102)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFAgentMBean[] _oldVal = this._SAFAgents;
      this._SAFAgents = (SAFAgentMBean[])param0;
      this._postSet(102, _oldVal, param0);
   }

   public SAFAgentMBean createSAFAgent(String param0) {
      SAFAgentMBeanImpl lookup = (SAFAgentMBeanImpl)this.lookupSAFAgent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFAgentMBeanImpl _val = new SAFAgentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFAgent(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroySAFAgent(SAFAgentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 102);
         SAFAgentMBean[] _old = this.getSAFAgents();
         SAFAgentMBean[] _new = (SAFAgentMBean[])((SAFAgentMBean[])this._getHelper()._removeElement(_old, SAFAgentMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSAFAgents(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SAFAgentMBean lookupSAFAgent(String param0) {
      Object[] aary = (Object[])this._SAFAgents;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFAgentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFAgentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSAFAgent(SAFAgentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 102)) {
         SAFAgentMBean[] _new;
         if (this._isSet(102)) {
            _new = (SAFAgentMBean[])((SAFAgentMBean[])this._getHelper()._extendArray(this.getSAFAgents(), SAFAgentMBean.class, param0));
         } else {
            _new = new SAFAgentMBean[]{param0};
         }

         try {
            this.setSAFAgents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addMigratableRMIService(MigratableRMIServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 103)) {
         MigratableRMIServiceMBean[] _new;
         if (this._isSet(103)) {
            _new = (MigratableRMIServiceMBean[])((MigratableRMIServiceMBean[])this._getHelper()._extendArray(this.getMigratableRMIServices(), MigratableRMIServiceMBean.class, param0));
         } else {
            _new = new MigratableRMIServiceMBean[]{param0};
         }

         try {
            this.setMigratableRMIServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MigratableRMIServiceMBean[] getMigratableRMIServices() {
      return this._MigratableRMIServices;
   }

   public boolean isMigratableRMIServicesInherited() {
      return false;
   }

   public boolean isMigratableRMIServicesSet() {
      return this._isSet(103);
   }

   public void removeMigratableRMIService(MigratableRMIServiceMBean param0) {
      this.destroyMigratableRMIService(param0);
   }

   public void setMigratableRMIServices(MigratableRMIServiceMBean[] param0) throws InvalidAttributeValueException {
      MigratableRMIServiceMBean[] param0 = param0 == null ? new MigratableRMIServiceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 103)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MigratableRMIServiceMBean[] _oldVal = this._MigratableRMIServices;
      this._MigratableRMIServices = (MigratableRMIServiceMBean[])param0;
      this._postSet(103, _oldVal, param0);
   }

   public MigratableRMIServiceMBean createMigratableRMIService(String param0) {
      MigratableRMIServiceMBeanImpl lookup = (MigratableRMIServiceMBeanImpl)this.lookupMigratableRMIService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MigratableRMIServiceMBeanImpl _val = new MigratableRMIServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMigratableRMIService(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMigratableRMIService(MigratableRMIServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 103);
         MigratableRMIServiceMBean[] _old = this.getMigratableRMIServices();
         MigratableRMIServiceMBean[] _new = (MigratableRMIServiceMBean[])((MigratableRMIServiceMBean[])this._getHelper()._removeElement(_old, MigratableRMIServiceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMigratableRMIServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public MigratableRMIServiceMBean lookupMigratableRMIService(String param0) {
      Object[] aary = (Object[])this._MigratableRMIServices;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MigratableRMIServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MigratableRMIServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public AdminServerMBean getAdminServerMBean() {
      return this._AdminServerMBean;
   }

   public boolean isAdminServerMBeanInherited() {
      return false;
   }

   public boolean isAdminServerMBeanSet() {
      return this._isSet(104);
   }

   public void setAdminServerMBean(AdminServerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAdminServerMBean() != null && param0 != this.getAdminServerMBean()) {
         throw new BeanAlreadyExistsException(this.getAdminServerMBean() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 104)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AdminServerMBean _oldVal = this._AdminServerMBean;
         this._AdminServerMBean = param0;
         this._postSet(104, _oldVal, param0);
      }
   }

   public AdminServerMBean createAdminServerMBean() {
      AdminServerMBeanImpl _val = new AdminServerMBeanImpl(this, -1);

      try {
         this.setAdminServerMBean(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAdminServerMBean() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AdminServerMBean;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAdminServerMBean((AdminServerMBean)null);
               this._unSet(104);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addJMSDistributedQueueMember(JMSDistributedQueueMemberMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 105)) {
         JMSDistributedQueueMemberMBean[] _new;
         if (this._isSet(105)) {
            _new = (JMSDistributedQueueMemberMBean[])((JMSDistributedQueueMemberMBean[])this._getHelper()._extendArray(this.getJMSDistributedQueueMembers(), JMSDistributedQueueMemberMBean.class, param0));
         } else {
            _new = new JMSDistributedQueueMemberMBean[]{param0};
         }

         try {
            this.setJMSDistributedQueueMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDistributedQueueMemberMBean[] getJMSDistributedQueueMembers() {
      return this._JMSDistributedQueueMembers;
   }

   public boolean isJMSDistributedQueueMembersInherited() {
      return false;
   }

   public boolean isJMSDistributedQueueMembersSet() {
      return this._isSet(105);
   }

   public void removeJMSDistributedQueueMember(JMSDistributedQueueMemberMBean param0) {
      this.destroyJMSDistributedQueueMember(param0);
   }

   public void setJMSDistributedQueueMembers(JMSDistributedQueueMemberMBean[] param0) throws InvalidAttributeValueException {
      JMSDistributedQueueMemberMBean[] param0 = param0 == null ? new JMSDistributedQueueMemberMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 105)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSDistributedQueueMemberMBean[] _oldVal = this._JMSDistributedQueueMembers;
      this._JMSDistributedQueueMembers = (JMSDistributedQueueMemberMBean[])param0;
      this._postSet(105, _oldVal, param0);
   }

   public JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String param0) {
      JMSDistributedQueueMemberMBeanImpl lookup = (JMSDistributedQueueMemberMBeanImpl)this.lookupJMSDistributedQueueMember(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSDistributedQueueMemberMBeanImpl _val = new JMSDistributedQueueMemberMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSDistributedQueueMember(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSDistributedQueueMember(JMSDistributedQueueMemberMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 105);
         JMSDistributedQueueMemberMBean[] _old = this.getJMSDistributedQueueMembers();
         JMSDistributedQueueMemberMBean[] _new = (JMSDistributedQueueMemberMBean[])((JMSDistributedQueueMemberMBean[])this._getHelper()._removeElement(_old, JMSDistributedQueueMemberMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSDistributedQueueMembers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSDistributedQueueMemberMBean lookupJMSDistributedQueueMember(String param0) {
      Object[] aary = (Object[])this._JMSDistributedQueueMembers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDistributedQueueMemberMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDistributedQueueMemberMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSDistributedTopicMember(JMSDistributedTopicMemberMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 106)) {
         JMSDistributedTopicMemberMBean[] _new;
         if (this._isSet(106)) {
            _new = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])this._getHelper()._extendArray(this.getJMSDistributedTopicMembers(), JMSDistributedTopicMemberMBean.class, param0));
         } else {
            _new = new JMSDistributedTopicMemberMBean[]{param0};
         }

         try {
            this.setJMSDistributedTopicMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDistributedTopicMemberMBean[] getJMSDistributedTopicMembers() {
      return this._JMSDistributedTopicMembers;
   }

   public boolean isJMSDistributedTopicMembersInherited() {
      return false;
   }

   public boolean isJMSDistributedTopicMembersSet() {
      return this._isSet(106);
   }

   public void removeJMSDistributedTopicMember(JMSDistributedTopicMemberMBean param0) {
      this.destroyJMSDistributedTopicMember(param0);
   }

   public void setJMSDistributedTopicMembers(JMSDistributedTopicMemberMBean[] param0) throws InvalidAttributeValueException {
      JMSDistributedTopicMemberMBean[] param0 = param0 == null ? new JMSDistributedTopicMemberMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 106)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSDistributedTopicMemberMBean[] _oldVal = this._JMSDistributedTopicMembers;
      this._JMSDistributedTopicMembers = (JMSDistributedTopicMemberMBean[])param0;
      this._postSet(106, _oldVal, param0);
   }

   public JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String param0) {
      JMSDistributedTopicMemberMBeanImpl lookup = (JMSDistributedTopicMemberMBeanImpl)this.lookupJMSDistributedTopicMember(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSDistributedTopicMemberMBeanImpl _val = new JMSDistributedTopicMemberMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSDistributedTopicMember(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSDistributedTopicMember(JMSDistributedTopicMemberMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 106);
         JMSDistributedTopicMemberMBean[] _old = this.getJMSDistributedTopicMembers();
         JMSDistributedTopicMemberMBean[] _new = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])this._getHelper()._removeElement(_old, JMSDistributedTopicMemberMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSDistributedTopicMembers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSDistributedTopicMemberMBean lookupJMSDistributedTopicMember(String param0) {
      Object[] aary = (Object[])this._JMSDistributedTopicMembers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDistributedTopicMemberMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDistributedTopicMemberMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPTrapDestinationMBean createSNMPTrapDestination(String param0) {
      SNMPTrapDestinationMBeanImpl _val = new SNMPTrapDestinationMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPTrapDestination(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPTrapDestination(SNMPTrapDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 107);
         SNMPTrapDestinationMBean[] _old = this.getSNMPTrapDestinations();
         SNMPTrapDestinationMBean[] _new = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])this._getHelper()._removeElement(_old, SNMPTrapDestinationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPTrapDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSNMPTrapDestination(SNMPTrapDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 107)) {
         SNMPTrapDestinationMBean[] _new;
         if (this._isSet(107)) {
            _new = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])this._getHelper()._extendArray(this.getSNMPTrapDestinations(), SNMPTrapDestinationMBean.class, param0));
         } else {
            _new = new SNMPTrapDestinationMBean[]{param0};
         }

         try {
            this.setSNMPTrapDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPTrapDestinationMBean[] getSNMPTrapDestinations() {
      return this._SNMPTrapDestinations;
   }

   public boolean isSNMPTrapDestinationsInherited() {
      return false;
   }

   public boolean isSNMPTrapDestinationsSet() {
      return this._isSet(107);
   }

   public void removeSNMPTrapDestination(SNMPTrapDestinationMBean param0) {
      this.destroySNMPTrapDestination(param0);
   }

   public void setSNMPTrapDestinations(SNMPTrapDestinationMBean[] param0) throws InvalidAttributeValueException {
      SNMPTrapDestinationMBean[] param0 = param0 == null ? new SNMPTrapDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 107)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SNMPTrapDestinationMBean[] _oldVal = this._SNMPTrapDestinations;
      this._SNMPTrapDestinations = (SNMPTrapDestinationMBean[])param0;
      this._postSet(107, _oldVal, param0);
   }

   public void addSNMPProxy(SNMPProxyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 108)) {
         SNMPProxyMBean[] _new;
         if (this._isSet(108)) {
            _new = (SNMPProxyMBean[])((SNMPProxyMBean[])this._getHelper()._extendArray(this.getSNMPProxies(), SNMPProxyMBean.class, param0));
         } else {
            _new = new SNMPProxyMBean[]{param0};
         }

         try {
            this.setSNMPProxies(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPProxyMBean[] getSNMPProxies() {
      return this._SNMPProxies;
   }

   public boolean isSNMPProxiesInherited() {
      return false;
   }

   public boolean isSNMPProxiesSet() {
      return this._isSet(108);
   }

   public void removeSNMPProxy(SNMPProxyMBean param0) {
      this.destroySNMPProxy(param0);
   }

   public void setSNMPProxies(SNMPProxyMBean[] param0) throws InvalidAttributeValueException {
      SNMPProxyMBean[] param0 = param0 == null ? new SNMPProxyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 108)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPProxyMBean[] _oldVal = this._SNMPProxies;
      this._SNMPProxies = (SNMPProxyMBean[])param0;
      this._postSet(108, _oldVal, param0);
   }

   public SNMPProxyMBean createSNMPProxy(String param0) {
      SNMPProxyMBeanImpl _val = new SNMPProxyMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPProxy(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPProxy(SNMPProxyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 108);
         SNMPProxyMBean[] _old = this.getSNMPProxies();
         SNMPProxyMBean[] _new = (SNMPProxyMBean[])((SNMPProxyMBean[])this._getHelper()._removeElement(_old, SNMPProxyMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPProxies(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSNMPGaugeMonitor(SNMPGaugeMonitorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 109)) {
         SNMPGaugeMonitorMBean[] _new;
         if (this._isSet(109)) {
            _new = (SNMPGaugeMonitorMBean[])((SNMPGaugeMonitorMBean[])this._getHelper()._extendArray(this.getSNMPGaugeMonitors(), SNMPGaugeMonitorMBean.class, param0));
         } else {
            _new = new SNMPGaugeMonitorMBean[]{param0};
         }

         try {
            this.setSNMPGaugeMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPGaugeMonitorMBean[] getSNMPGaugeMonitors() {
      return this._SNMPGaugeMonitors;
   }

   public boolean isSNMPGaugeMonitorsInherited() {
      return false;
   }

   public boolean isSNMPGaugeMonitorsSet() {
      return this._isSet(109);
   }

   public void removeSNMPGaugeMonitor(SNMPGaugeMonitorMBean param0) {
      this.destroySNMPGaugeMonitor(param0);
   }

   public void setSNMPGaugeMonitors(SNMPGaugeMonitorMBean[] param0) throws InvalidAttributeValueException {
      SNMPGaugeMonitorMBean[] param0 = param0 == null ? new SNMPGaugeMonitorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 109)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPGaugeMonitorMBean[] _oldVal = this._SNMPGaugeMonitors;
      this._SNMPGaugeMonitors = (SNMPGaugeMonitorMBean[])param0;
      this._postSet(109, _oldVal, param0);
   }

   public SNMPGaugeMonitorMBean createSNMPGaugeMonitor(String param0) {
      SNMPGaugeMonitorMBeanImpl _val = new SNMPGaugeMonitorMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPGaugeMonitor(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPGaugeMonitor(SNMPGaugeMonitorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 109);
         SNMPGaugeMonitorMBean[] _old = this.getSNMPGaugeMonitors();
         SNMPGaugeMonitorMBean[] _new = (SNMPGaugeMonitorMBean[])((SNMPGaugeMonitorMBean[])this._getHelper()._removeElement(_old, SNMPGaugeMonitorMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPGaugeMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSNMPStringMonitor(SNMPStringMonitorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 110)) {
         SNMPStringMonitorMBean[] _new;
         if (this._isSet(110)) {
            _new = (SNMPStringMonitorMBean[])((SNMPStringMonitorMBean[])this._getHelper()._extendArray(this.getSNMPStringMonitors(), SNMPStringMonitorMBean.class, param0));
         } else {
            _new = new SNMPStringMonitorMBean[]{param0};
         }

         try {
            this.setSNMPStringMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPStringMonitorMBean[] getSNMPStringMonitors() {
      return this._SNMPStringMonitors;
   }

   public boolean isSNMPStringMonitorsInherited() {
      return false;
   }

   public boolean isSNMPStringMonitorsSet() {
      return this._isSet(110);
   }

   public void removeSNMPStringMonitor(SNMPStringMonitorMBean param0) {
      this.destroySNMPStringMonitor(param0);
   }

   public void setSNMPStringMonitors(SNMPStringMonitorMBean[] param0) throws InvalidAttributeValueException {
      SNMPStringMonitorMBean[] param0 = param0 == null ? new SNMPStringMonitorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 110)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPStringMonitorMBean[] _oldVal = this._SNMPStringMonitors;
      this._SNMPStringMonitors = (SNMPStringMonitorMBean[])param0;
      this._postSet(110, _oldVal, param0);
   }

   public SNMPStringMonitorMBean createSNMPStringMonitor(String param0) {
      SNMPStringMonitorMBeanImpl _val = new SNMPStringMonitorMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPStringMonitor(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPStringMonitor(SNMPStringMonitorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 110);
         SNMPStringMonitorMBean[] _old = this.getSNMPStringMonitors();
         SNMPStringMonitorMBean[] _new = (SNMPStringMonitorMBean[])((SNMPStringMonitorMBean[])this._getHelper()._removeElement(_old, SNMPStringMonitorMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPStringMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSNMPCounterMonitor(SNMPCounterMonitorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 111)) {
         SNMPCounterMonitorMBean[] _new;
         if (this._isSet(111)) {
            _new = (SNMPCounterMonitorMBean[])((SNMPCounterMonitorMBean[])this._getHelper()._extendArray(this.getSNMPCounterMonitors(), SNMPCounterMonitorMBean.class, param0));
         } else {
            _new = new SNMPCounterMonitorMBean[]{param0};
         }

         try {
            this.setSNMPCounterMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPCounterMonitorMBean[] getSNMPCounterMonitors() {
      return this._SNMPCounterMonitors;
   }

   public boolean isSNMPCounterMonitorsInherited() {
      return false;
   }

   public boolean isSNMPCounterMonitorsSet() {
      return this._isSet(111);
   }

   public void removeSNMPCounterMonitor(SNMPCounterMonitorMBean param0) {
      this.destroySNMPCounterMonitor(param0);
   }

   public void setSNMPCounterMonitors(SNMPCounterMonitorMBean[] param0) throws InvalidAttributeValueException {
      SNMPCounterMonitorMBean[] param0 = param0 == null ? new SNMPCounterMonitorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 111)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPCounterMonitorMBean[] _oldVal = this._SNMPCounterMonitors;
      this._SNMPCounterMonitors = (SNMPCounterMonitorMBean[])param0;
      this._postSet(111, _oldVal, param0);
   }

   public SNMPCounterMonitorMBean createSNMPCounterMonitor(String param0) {
      SNMPCounterMonitorMBeanImpl _val = new SNMPCounterMonitorMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPCounterMonitor(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPCounterMonitor(SNMPCounterMonitorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 111);
         SNMPCounterMonitorMBean[] _old = this.getSNMPCounterMonitors();
         SNMPCounterMonitorMBean[] _new = (SNMPCounterMonitorMBean[])((SNMPCounterMonitorMBean[])this._getHelper()._removeElement(_old, SNMPCounterMonitorMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPCounterMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSNMPLogFilter(SNMPLogFilterMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 112)) {
         SNMPLogFilterMBean[] _new;
         if (this._isSet(112)) {
            _new = (SNMPLogFilterMBean[])((SNMPLogFilterMBean[])this._getHelper()._extendArray(this.getSNMPLogFilters(), SNMPLogFilterMBean.class, param0));
         } else {
            _new = new SNMPLogFilterMBean[]{param0};
         }

         try {
            this.setSNMPLogFilters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPLogFilterMBean[] getSNMPLogFilters() {
      return this._SNMPLogFilters;
   }

   public boolean isSNMPLogFiltersInherited() {
      return false;
   }

   public boolean isSNMPLogFiltersSet() {
      return this._isSet(112);
   }

   public void removeSNMPLogFilter(SNMPLogFilterMBean param0) {
      this.destroySNMPLogFilter(param0);
   }

   public void setSNMPLogFilters(SNMPLogFilterMBean[] param0) throws InvalidAttributeValueException {
      SNMPLogFilterMBean[] param0 = param0 == null ? new SNMPLogFilterMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 112)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPLogFilterMBean[] _oldVal = this._SNMPLogFilters;
      this._SNMPLogFilters = (SNMPLogFilterMBean[])param0;
      this._postSet(112, _oldVal, param0);
   }

   public SNMPLogFilterMBean createSNMPLogFilter(String param0) {
      SNMPLogFilterMBeanImpl _val = new SNMPLogFilterMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPLogFilter(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPLogFilter(SNMPLogFilterMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 112);
         SNMPLogFilterMBean[] _old = this.getSNMPLogFilters();
         SNMPLogFilterMBean[] _new = (SNMPLogFilterMBean[])((SNMPLogFilterMBean[])this._getHelper()._removeElement(_old, SNMPLogFilterMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPLogFilters(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSNMPAttributeChange(SNMPAttributeChangeMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 113)) {
         SNMPAttributeChangeMBean[] _new;
         if (this._isSet(113)) {
            _new = (SNMPAttributeChangeMBean[])((SNMPAttributeChangeMBean[])this._getHelper()._extendArray(this.getSNMPAttributeChanges(), SNMPAttributeChangeMBean.class, param0));
         } else {
            _new = new SNMPAttributeChangeMBean[]{param0};
         }

         try {
            this.setSNMPAttributeChanges(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SNMPAttributeChangeMBean[] getSNMPAttributeChanges() {
      return this._SNMPAttributeChanges;
   }

   public boolean isSNMPAttributeChangesInherited() {
      return false;
   }

   public boolean isSNMPAttributeChangesSet() {
      return this._isSet(113);
   }

   public void removeSNMPAttributeChange(SNMPAttributeChangeMBean param0) {
      this.destroySNMPAttributeChange(param0);
   }

   public void setSNMPAttributeChanges(SNMPAttributeChangeMBean[] param0) throws InvalidAttributeValueException {
      SNMPAttributeChangeMBean[] param0 = param0 == null ? new SNMPAttributeChangeMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 113)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPAttributeChangeMBean[] _oldVal = this._SNMPAttributeChanges;
      this._SNMPAttributeChanges = (SNMPAttributeChangeMBean[])param0;
      this._postSet(113, _oldVal, param0);
   }

   public SNMPAttributeChangeMBean createSNMPAttributeChange(String param0) {
      SNMPAttributeChangeMBeanImpl _val = new SNMPAttributeChangeMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addSNMPAttributeChange(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySNMPAttributeChange(SNMPAttributeChangeMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 113);
         SNMPAttributeChangeMBean[] _old = this.getSNMPAttributeChanges();
         SNMPAttributeChangeMBean[] _new = (SNMPAttributeChangeMBean[])((SNMPAttributeChangeMBean[])this._getHelper()._removeElement(_old, SNMPAttributeChangeMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPAttributeChanges(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addWebserviceSecurity(WebserviceSecurityMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 114)) {
         WebserviceSecurityMBean[] _new;
         if (this._isSet(114)) {
            _new = (WebserviceSecurityMBean[])((WebserviceSecurityMBean[])this._getHelper()._extendArray(this.getWebserviceSecurities(), WebserviceSecurityMBean.class, param0));
         } else {
            _new = new WebserviceSecurityMBean[]{param0};
         }

         try {
            this.setWebserviceSecurities(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebserviceSecurityMBean[] getWebserviceSecurities() {
      return this._WebserviceSecurities;
   }

   public boolean isWebserviceSecuritiesInherited() {
      return false;
   }

   public boolean isWebserviceSecuritiesSet() {
      return this._isSet(114);
   }

   public void removeWebserviceSecurity(WebserviceSecurityMBean param0) {
      this.destroyWebserviceSecurity(param0);
   }

   public void setWebserviceSecurities(WebserviceSecurityMBean[] param0) throws InvalidAttributeValueException {
      WebserviceSecurityMBean[] param0 = param0 == null ? new WebserviceSecurityMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 114)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WebserviceSecurityMBean[] _oldVal = this._WebserviceSecurities;
      this._WebserviceSecurities = (WebserviceSecurityMBean[])param0;
      this._postSet(114, _oldVal, param0);
   }

   public WebserviceSecurityMBean lookupWebserviceSecurity(String param0) {
      Object[] aary = (Object[])this._WebserviceSecurities;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebserviceSecurityMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebserviceSecurityMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WebserviceSecurityMBean createWebserviceSecurity(String param0) {
      WebserviceSecurityMBeanImpl lookup = (WebserviceSecurityMBeanImpl)this.lookupWebserviceSecurity(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebserviceSecurityMBeanImpl _val = new WebserviceSecurityMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebserviceSecurity(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWebserviceSecurity(WebserviceSecurityMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 114);
         WebserviceSecurityMBean[] _old = this.getWebserviceSecurities();
         WebserviceSecurityMBean[] _new = (WebserviceSecurityMBean[])((WebserviceSecurityMBean[])this._getHelper()._removeElement(_old, WebserviceSecurityMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWebserviceSecurities(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 115)) {
         ForeignJMSConnectionFactoryMBean[] _new;
         if (this._isSet(115)) {
            _new = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])this._getHelper()._extendArray(this.getForeignJMSConnectionFactories(), ForeignJMSConnectionFactoryMBean.class, param0));
         } else {
            _new = new ForeignJMSConnectionFactoryMBean[]{param0};
         }

         try {
            this.setForeignJMSConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJMSConnectionFactoryMBean[] getForeignJMSConnectionFactories() {
      return this._ForeignJMSConnectionFactories;
   }

   public boolean isForeignJMSConnectionFactoriesInherited() {
      return false;
   }

   public boolean isForeignJMSConnectionFactoriesSet() {
      return this._isSet(115);
   }

   public void removeForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      this.destroyForeignJMSConnectionFactory(param0);
   }

   public void setForeignJMSConnectionFactories(ForeignJMSConnectionFactoryMBean[] param0) throws InvalidAttributeValueException {
      ForeignJMSConnectionFactoryMBean[] param0 = param0 == null ? new ForeignJMSConnectionFactoryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 115)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignJMSConnectionFactoryMBean[] _oldVal = this._ForeignJMSConnectionFactories;
      this._ForeignJMSConnectionFactories = (ForeignJMSConnectionFactoryMBean[])param0;
      this._postSet(115, _oldVal, param0);
   }

   public ForeignJMSConnectionFactoryMBean lookupForeignJMSConnectionFactory(String param0) {
      Object[] aary = (Object[])this._ForeignJMSConnectionFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJMSConnectionFactoryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJMSConnectionFactoryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String param0) {
      ForeignJMSConnectionFactoryMBeanImpl lookup = (ForeignJMSConnectionFactoryMBeanImpl)this.lookupForeignJMSConnectionFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJMSConnectionFactoryMBeanImpl _val = new ForeignJMSConnectionFactoryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJMSConnectionFactory(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 115);
         ForeignJMSConnectionFactoryMBean[] _old = this.getForeignJMSConnectionFactories();
         ForeignJMSConnectionFactoryMBean[] _new = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])this._getHelper()._removeElement(_old, ForeignJMSConnectionFactoryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setForeignJMSConnectionFactories(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addForeignJMSDestination(ForeignJMSDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 116)) {
         ForeignJMSDestinationMBean[] _new;
         if (this._isSet(116)) {
            _new = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])this._getHelper()._extendArray(this.getForeignJMSDestinations(), ForeignJMSDestinationMBean.class, param0));
         } else {
            _new = new ForeignJMSDestinationMBean[]{param0};
         }

         try {
            this.setForeignJMSDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJMSDestinationMBean[] getForeignJMSDestinations() {
      return this._ForeignJMSDestinations;
   }

   public boolean isForeignJMSDestinationsInherited() {
      return false;
   }

   public boolean isForeignJMSDestinationsSet() {
      return this._isSet(116);
   }

   public void removeForeignJMSDestination(ForeignJMSDestinationMBean param0) {
      this.destroyForeignJMSDestination(param0);
   }

   public void setForeignJMSDestinations(ForeignJMSDestinationMBean[] param0) throws InvalidAttributeValueException {
      ForeignJMSDestinationMBean[] param0 = param0 == null ? new ForeignJMSDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 116)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignJMSDestinationMBean[] _oldVal = this._ForeignJMSDestinations;
      this._ForeignJMSDestinations = (ForeignJMSDestinationMBean[])param0;
      this._postSet(116, _oldVal, param0);
   }

   public ForeignJMSDestinationMBean lookupForeignJMSDestination(String param0) {
      Object[] aary = (Object[])this._ForeignJMSDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJMSDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJMSDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJMSDestinationMBean createForeignJMSDestination(String param0) {
      ForeignJMSDestinationMBeanImpl lookup = (ForeignJMSDestinationMBeanImpl)this.lookupForeignJMSDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJMSDestinationMBeanImpl _val = new ForeignJMSDestinationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJMSDestination(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyForeignJMSDestination(ForeignJMSDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 116);
         ForeignJMSDestinationMBean[] _old = this.getForeignJMSDestinations();
         ForeignJMSDestinationMBean[] _new = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])this._getHelper()._removeElement(_old, ForeignJMSDestinationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setForeignJMSDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addJMSConnectionConsumer(JMSConnectionConsumerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 117)) {
         JMSConnectionConsumerMBean[] _new;
         if (this._isSet(117)) {
            _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._extendArray(this.getJMSConnectionConsumers(), JMSConnectionConsumerMBean.class, param0));
         } else {
            _new = new JMSConnectionConsumerMBean[]{param0};
         }

         try {
            this.setJMSConnectionConsumers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSConnectionConsumerMBean[] getJMSConnectionConsumers() {
      return this._JMSConnectionConsumers;
   }

   public boolean isJMSConnectionConsumersInherited() {
      return false;
   }

   public boolean isJMSConnectionConsumersSet() {
      return this._isSet(117);
   }

   public void removeJMSConnectionConsumer(JMSConnectionConsumerMBean param0) {
      this.destroyJMSConnectionConsumer(param0);
   }

   public void setJMSConnectionConsumers(JMSConnectionConsumerMBean[] param0) throws InvalidAttributeValueException {
      JMSConnectionConsumerMBean[] param0 = param0 == null ? new JMSConnectionConsumerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 117)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMSConnectionConsumerMBean[] _oldVal = this._JMSConnectionConsumers;
      this._JMSConnectionConsumers = (JMSConnectionConsumerMBean[])param0;
      this._postSet(117, _oldVal, param0);
   }

   public JMSConnectionConsumerMBean lookupJMSConnectionConsumer(String param0) {
      Object[] aary = (Object[])this._JMSConnectionConsumers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSConnectionConsumerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSConnectionConsumerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSConnectionConsumerMBean createJMSConnectionConsumer(String param0) {
      JMSConnectionConsumerMBeanImpl lookup = (JMSConnectionConsumerMBeanImpl)this.lookupJMSConnectionConsumer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSConnectionConsumerMBeanImpl _val = new JMSConnectionConsumerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSConnectionConsumer(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyJMSConnectionConsumer(JMSConnectionConsumerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 117);
         JMSConnectionConsumerMBean[] _old = this.getJMSConnectionConsumers();
         JMSConnectionConsumerMBean[] _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._removeElement(_old, JMSConnectionConsumerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSConnectionConsumers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ForeignJMSDestinationMBean createForeignJMSDestination(String param0, ForeignJMSDestinationMBean param1) {
      return this._customizer.createForeignJMSDestination(param0, param1);
   }

   public ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String param0, ForeignJMSConnectionFactoryMBean param1) {
      return this._customizer.createForeignJMSConnectionFactory(param0, param1);
   }

   public JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String param0, JMSDistributedQueueMemberMBean param1) {
      return this._customizer.createJMSDistributedQueueMember(param0, param1);
   }

   public JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String param0, JMSDistributedTopicMemberMBean param1) {
      return this._customizer.createJMSDistributedTopicMember(param0, param1);
   }

   public JMSTopicMBean createJMSTopic(String param0, JMSTopicMBean param1) {
      return this._customizer.createJMSTopic(param0, param1);
   }

   public JMSQueueMBean createJMSQueue(String param0, JMSQueueMBean param1) {
      return this._customizer.createJMSQueue(param0, param1);
   }

   public boolean isAutoDeployForSubmodulesEnabled() {
      return this._AutoDeployForSubmodulesEnabled;
   }

   public boolean isAutoDeployForSubmodulesEnabledInherited() {
      return false;
   }

   public boolean isAutoDeployForSubmodulesEnabledSet() {
      return this._isSet(118);
   }

   public void setAutoDeployForSubmodulesEnabled(boolean param0) {
      boolean _oldVal = this._AutoDeployForSubmodulesEnabled;
      this._AutoDeployForSubmodulesEnabled = param0;
      this._postSet(118, _oldVal, param0);
   }

   public AdminConsoleMBean getAdminConsole() {
      return this._AdminConsole;
   }

   public boolean isAdminConsoleInherited() {
      return false;
   }

   public boolean isAdminConsoleSet() {
      return this._isSet(119) || this._isAnythingSet((AbstractDescriptorBean)this.getAdminConsole());
   }

   public void setAdminConsole(AdminConsoleMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 119)) {
         this._postCreate(_child);
      }

      AdminConsoleMBean _oldVal = this._AdminConsole;
      this._AdminConsole = param0;
      this._postSet(119, _oldVal, param0);
   }

   public boolean isInternalAppsDeployOnDemandEnabled() {
      return this._InternalAppsDeployOnDemandEnabled;
   }

   public boolean isInternalAppsDeployOnDemandEnabledInherited() {
      return false;
   }

   public boolean isInternalAppsDeployOnDemandEnabledSet() {
      return this._isSet(120);
   }

   public void setInternalAppsDeployOnDemandEnabled(boolean param0) {
      boolean _oldVal = this._InternalAppsDeployOnDemandEnabled;
      this._InternalAppsDeployOnDemandEnabled = param0;
      this._postSet(120, _oldVal, param0);
   }

   public boolean isGuardianEnabled() {
      return this._GuardianEnabled;
   }

   public boolean isGuardianEnabledInherited() {
      return false;
   }

   public boolean isGuardianEnabledSet() {
      return this._isSet(121);
   }

   public void setGuardianEnabled(boolean param0) {
      boolean _oldVal = this._GuardianEnabled;
      this._GuardianEnabled = param0;
      this._postSet(121, _oldVal, param0);
   }

   public boolean isOCMEnabled() {
      return this._OCMEnabled;
   }

   public boolean isOCMEnabledInherited() {
      return false;
   }

   public boolean isOCMEnabledSet() {
      return this._isSet(122);
   }

   public void setOCMEnabled(boolean param0) {
      boolean _oldVal = this._OCMEnabled;
      this._OCMEnabled = param0;
      this._postSet(122, _oldVal, param0);
   }

   public boolean isMsgIdPrefixCompatibilityEnabled() {
      return this._MsgIdPrefixCompatibilityEnabled;
   }

   public boolean isMsgIdPrefixCompatibilityEnabledInherited() {
      return false;
   }

   public boolean isMsgIdPrefixCompatibilityEnabledSet() {
      return this._isSet(123);
   }

   public void setMsgIdPrefixCompatibilityEnabled(boolean param0) {
      boolean _oldVal = this._MsgIdPrefixCompatibilityEnabled;
      this._MsgIdPrefixCompatibilityEnabled = param0;
      this._postSet(123, _oldVal, param0);
   }

   public boolean isLogFormatCompatibilityEnabled() {
      return this._LogFormatCompatibilityEnabled;
   }

   public boolean isLogFormatCompatibilityEnabledInherited() {
      return false;
   }

   public boolean isLogFormatCompatibilityEnabledSet() {
      return this._isSet(124);
   }

   public void setLogFormatCompatibilityEnabled(boolean param0) {
      boolean _oldVal = this._LogFormatCompatibilityEnabled;
      this._LogFormatCompatibilityEnabled = param0;
      this._postSet(124, _oldVal, param0);
   }

   public CoherenceClusterSystemResourceMBean[] getCoherenceClusterSystemResources() {
      return this._CoherenceClusterSystemResources;
   }

   public boolean isCoherenceClusterSystemResourcesInherited() {
      return false;
   }

   public boolean isCoherenceClusterSystemResourcesSet() {
      return this._isSet(125);
   }

   public void removeCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      this.destroyCoherenceClusterSystemResource(param0);
   }

   public void setCoherenceClusterSystemResources(CoherenceClusterSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      CoherenceClusterSystemResourceMBean[] param0 = param0 == null ? new CoherenceClusterSystemResourceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 125)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      CoherenceClusterSystemResourceMBean[] _oldVal = this._CoherenceClusterSystemResources;
      this._CoherenceClusterSystemResources = (CoherenceClusterSystemResourceMBean[])param0;
      this._postSet(125, _oldVal, param0);
   }

   public CoherenceClusterSystemResourceMBean createCoherenceClusterSystemResource(String param0) {
      CoherenceClusterSystemResourceMBeanImpl lookup = (CoherenceClusterSystemResourceMBeanImpl)this.lookupCoherenceClusterSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceClusterSystemResourceMBeanImpl _val = new CoherenceClusterSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceClusterSystemResource(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 125);
         CoherenceClusterSystemResourceMBean[] _old = this.getCoherenceClusterSystemResources();
         CoherenceClusterSystemResourceMBean[] _new = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])this._getHelper()._removeElement(_old, CoherenceClusterSystemResourceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCoherenceClusterSystemResources(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public CoherenceClusterSystemResourceMBean lookupCoherenceClusterSystemResource(String param0) {
      Object[] aary = (Object[])this._CoherenceClusterSystemResources;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceClusterSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceClusterSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 125)) {
         CoherenceClusterSystemResourceMBean[] _new;
         if (this._isSet(125)) {
            _new = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])this._getHelper()._extendArray(this.getCoherenceClusterSystemResources(), CoherenceClusterSystemResourceMBean.class, param0));
         } else {
            _new = new CoherenceClusterSystemResourceMBean[]{param0};
         }

         try {
            this.setCoherenceClusterSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RestfulManagementServicesMBean getRestfulManagementServices() {
      return this._RestfulManagementServices;
   }

   public boolean isRestfulManagementServicesInherited() {
      return false;
   }

   public boolean isRestfulManagementServicesSet() {
      return this._isSet(126) || this._isAnythingSet((AbstractDescriptorBean)this.getRestfulManagementServices());
   }

   public void setRestfulManagementServices(RestfulManagementServicesMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 126)) {
         this._postCreate(_child);
      }

      RestfulManagementServicesMBean _oldVal = this._RestfulManagementServices;
      this._RestfulManagementServices = param0;
      this._postSet(126, _oldVal, param0);
   }

   public void addSystemComponent(SystemComponentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 127)) {
         SystemComponentMBean[] _new;
         if (this._isSet(127)) {
            _new = (SystemComponentMBean[])((SystemComponentMBean[])this._getHelper()._extendArray(this.getSystemComponents(), SystemComponentMBean.class, param0));
         } else {
            _new = new SystemComponentMBean[]{param0};
         }

         try {
            this.setSystemComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SystemComponentMBean[] getSystemComponents() {
      return this._SystemComponents;
   }

   public boolean isSystemComponentsInherited() {
      return false;
   }

   public boolean isSystemComponentsSet() {
      return this._isSet(127);
   }

   public void removeSystemComponent(SystemComponentMBean param0) {
      this.destroySystemComponent(param0);
   }

   public void setSystemComponents(SystemComponentMBean[] param0) throws InvalidAttributeValueException {
      SystemComponentMBean[] param0 = param0 == null ? new SystemComponentMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 127)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SystemComponentMBean[] _oldVal = this._SystemComponents;
      this._SystemComponents = (SystemComponentMBean[])param0;
      this._postSet(127, _oldVal, param0);
   }

   public SystemComponentMBean createSystemComponent(String param0, String param1) {
      SystemComponentMBeanImpl lookup = (SystemComponentMBeanImpl)this.lookupSystemComponent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SystemComponentMBeanImpl _val = new SystemComponentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setComponentType(param1);
            this.addSystemComponent(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroySystemComponent(SystemComponentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 127);
         SystemComponentMBean[] _old = this.getSystemComponents();
         SystemComponentMBean[] _new = (SystemComponentMBean[])((SystemComponentMBean[])this._getHelper()._removeElement(_old, SystemComponentMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSystemComponents(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SystemComponentMBean lookupSystemComponent(String param0) {
      Object[] aary = (Object[])this._SystemComponents;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SystemComponentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SystemComponentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSystemComponentConfiguration(SystemComponentConfigurationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 128)) {
         SystemComponentConfigurationMBean[] _new;
         if (this._isSet(128)) {
            _new = (SystemComponentConfigurationMBean[])((SystemComponentConfigurationMBean[])this._getHelper()._extendArray(this.getSystemComponentConfigurations(), SystemComponentConfigurationMBean.class, param0));
         } else {
            _new = new SystemComponentConfigurationMBean[]{param0};
         }

         try {
            this.setSystemComponentConfigurations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SystemComponentConfigurationMBean[] getSystemComponentConfigurations() {
      return this._SystemComponentConfigurations;
   }

   public boolean isSystemComponentConfigurationsInherited() {
      return false;
   }

   public boolean isSystemComponentConfigurationsSet() {
      return this._isSet(128);
   }

   public void removeSystemComponentConfiguration(SystemComponentConfigurationMBean param0) {
      this.destroySystemComponentConfiguration(param0);
   }

   public void setSystemComponentConfigurations(SystemComponentConfigurationMBean[] param0) throws InvalidAttributeValueException {
      SystemComponentConfigurationMBean[] param0 = param0 == null ? new SystemComponentConfigurationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 128)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SystemComponentConfigurationMBean[] _oldVal = this._SystemComponentConfigurations;
      this._SystemComponentConfigurations = (SystemComponentConfigurationMBean[])param0;
      this._postSet(128, _oldVal, param0);
   }

   public SystemComponentConfigurationMBean createSystemComponentConfiguration(String param0, String param1) {
      SystemComponentConfigurationMBeanImpl lookup = (SystemComponentConfigurationMBeanImpl)this.lookupSystemComponentConfiguration(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SystemComponentConfigurationMBeanImpl _val = new SystemComponentConfigurationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setComponentType(param1);
            this.addSystemComponentConfiguration(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroySystemComponentConfiguration(SystemComponentConfigurationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 128);
         SystemComponentConfigurationMBean[] _old = this.getSystemComponentConfigurations();
         SystemComponentConfigurationMBean[] _new = (SystemComponentConfigurationMBean[])((SystemComponentConfigurationMBean[])this._getHelper()._removeElement(_old, SystemComponentConfigurationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSystemComponentConfigurations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SystemComponentConfigurationMBean lookupSystemComponentConfiguration(String param0) {
      Object[] aary = (Object[])this._SystemComponentConfigurations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SystemComponentConfigurationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SystemComponentConfigurationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public OsgiFrameworkMBean[] getOsgiFrameworks() {
      return this._OsgiFrameworks;
   }

   public boolean isOsgiFrameworksInherited() {
      return false;
   }

   public boolean isOsgiFrameworksSet() {
      return this._isSet(129);
   }

   public void removeOsgiFramework(OsgiFrameworkMBean param0) {
      this.destroyOsgiFramework(param0);
   }

   public void setOsgiFrameworks(OsgiFrameworkMBean[] param0) throws InvalidAttributeValueException {
      OsgiFrameworkMBean[] param0 = param0 == null ? new OsgiFrameworkMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 129)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      OsgiFrameworkMBean[] _oldVal = this._OsgiFrameworks;
      this._OsgiFrameworks = (OsgiFrameworkMBean[])param0;
      this._postSet(129, _oldVal, param0);
   }

   public OsgiFrameworkMBean lookupOsgiFramework(String param0) {
      Object[] aary = (Object[])this._OsgiFrameworks;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      OsgiFrameworkMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (OsgiFrameworkMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addOsgiFramework(OsgiFrameworkMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 129)) {
         OsgiFrameworkMBean[] _new;
         if (this._isSet(129)) {
            _new = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])this._getHelper()._extendArray(this.getOsgiFrameworks(), OsgiFrameworkMBean.class, param0));
         } else {
            _new = new OsgiFrameworkMBean[]{param0};
         }

         try {
            this.setOsgiFrameworks(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OsgiFrameworkMBean createOsgiFramework(String param0) {
      OsgiFrameworkMBeanImpl lookup = (OsgiFrameworkMBeanImpl)this.lookupOsgiFramework(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         OsgiFrameworkMBeanImpl _val = new OsgiFrameworkMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addOsgiFramework(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyOsgiFramework(OsgiFrameworkMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 129);
         OsgiFrameworkMBean[] _old = this.getOsgiFrameworks();
         OsgiFrameworkMBean[] _new = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])this._getHelper()._removeElement(_old, OsgiFrameworkMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOsgiFrameworks(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public WebserviceTestpageMBean getWebserviceTestpage() {
      return this._WebserviceTestpage;
   }

   public boolean isWebserviceTestpageInherited() {
      return false;
   }

   public boolean isWebserviceTestpageSet() {
      return this._isSet(130) || this._isAnythingSet((AbstractDescriptorBean)this.getWebserviceTestpage());
   }

   public void setWebserviceTestpage(WebserviceTestpageMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 130)) {
         this._postCreate(_child);
      }

      WebserviceTestpageMBean _oldVal = this._WebserviceTestpage;
      this._WebserviceTestpage = param0;
      this._postSet(130, _oldVal, param0);
   }

   public int getServerMigrationHistorySize() {
      return this._ServerMigrationHistorySize;
   }

   public boolean isServerMigrationHistorySizeInherited() {
      return false;
   }

   public boolean isServerMigrationHistorySizeSet() {
      return this._isSet(131);
   }

   public void setServerMigrationHistorySize(int param0) {
      LegalChecks.checkInRange("ServerMigrationHistorySize", (long)param0, -1L, 2147483647L);
      int _oldVal = this._ServerMigrationHistorySize;
      this._ServerMigrationHistorySize = param0;
      this._postSet(131, _oldVal, param0);
   }

   public int getServiceMigrationHistorySize() {
      return this._ServiceMigrationHistorySize;
   }

   public boolean isServiceMigrationHistorySizeInherited() {
      return false;
   }

   public boolean isServiceMigrationHistorySizeSet() {
      return this._isSet(132);
   }

   public void setServiceMigrationHistorySize(int param0) {
      LegalChecks.checkInRange("ServiceMigrationHistorySize", (long)param0, -1L, 2147483647L);
      int _oldVal = this._ServiceMigrationHistorySize;
      this._ServiceMigrationHistorySize = param0;
      this._postSet(132, _oldVal, param0);
   }

   public void addCoherenceManagementCluster(CoherenceManagementClusterMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 133)) {
         CoherenceManagementClusterMBean[] _new;
         if (this._isSet(133)) {
            _new = (CoherenceManagementClusterMBean[])((CoherenceManagementClusterMBean[])this._getHelper()._extendArray(this.getCoherenceManagementClusters(), CoherenceManagementClusterMBean.class, param0));
         } else {
            _new = new CoherenceManagementClusterMBean[]{param0};
         }

         try {
            this.setCoherenceManagementClusters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceManagementClusterMBean[] getCoherenceManagementClusters() {
      return this._CoherenceManagementClusters;
   }

   public boolean isCoherenceManagementClustersInherited() {
      return false;
   }

   public boolean isCoherenceManagementClustersSet() {
      return this._isSet(133);
   }

   public void removeCoherenceManagementCluster(CoherenceManagementClusterMBean param0) {
      this.destroyCoherenceManagementCluster(param0);
   }

   public void setCoherenceManagementClusters(CoherenceManagementClusterMBean[] param0) throws InvalidAttributeValueException {
      CoherenceManagementClusterMBean[] param0 = param0 == null ? new CoherenceManagementClusterMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 133)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      CoherenceManagementClusterMBean[] _oldVal = this._CoherenceManagementClusters;
      this._CoherenceManagementClusters = (CoherenceManagementClusterMBean[])param0;
      this._postSet(133, _oldVal, param0);
   }

   public CoherenceManagementClusterMBean createCoherenceManagementCluster(String param0) {
      CoherenceManagementClusterMBeanImpl lookup = (CoherenceManagementClusterMBeanImpl)this.lookupCoherenceManagementCluster(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceManagementClusterMBeanImpl _val = new CoherenceManagementClusterMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceManagementCluster(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public CoherenceManagementClusterMBean lookupCoherenceManagementCluster(String param0) {
      Object[] aary = (Object[])this._CoherenceManagementClusters;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceManagementClusterMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceManagementClusterMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceManagementCluster(CoherenceManagementClusterMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 133);
         CoherenceManagementClusterMBean[] _old = this.getCoherenceManagementClusters();
         CoherenceManagementClusterMBean[] _new = (CoherenceManagementClusterMBean[])((CoherenceManagementClusterMBean[])this._getHelper()._removeElement(_old, CoherenceManagementClusterMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCoherenceManagementClusters(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addPartition(PartitionMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 134)) {
         PartitionMBean[] _new = (PartitionMBean[])((PartitionMBean[])this._getHelper()._extendArray(this.getPartitions(), PartitionMBean.class, param0));

         try {
            this.setPartitions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PartitionMBean[] getPartitions() {
      return this._customizer.getPartitions();
   }

   public boolean isPartitionsInherited() {
      return false;
   }

   public boolean isPartitionsSet() {
      return this._isSet(134);
   }

   public void removePartition(PartitionMBean param0) {
      DomainValidator.validateDestroyPartition(param0);
      this.destroyPartition(param0);
   }

   public void setPartitions(PartitionMBean[] param0) throws InvalidAttributeValueException {
      PartitionMBean[] param0 = param0 == null ? new PartitionMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 134)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PartitionMBean[] _oldVal = this._Partitions;
      this._Partitions = (PartitionMBean[])param0;
      this._postSet(134, _oldVal, param0);
   }

   public PartitionMBean lookupPartition(String param0) {
      Object[] aary = (Object[])this._Partitions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PartitionMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PartitionMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public PartitionMBean findPartitionByID(String param0) {
      return this._customizer.findPartitionByID(param0);
   }

   public PartitionMBean createPartition(String param0) {
      return this._customizer.createPartition(param0);
   }

   public PartitionMBean createPartition(String param0, String param1) {
      return this._customizer.createPartition(param0, param1);
   }

   public void destroyPartition(PartitionMBean param0) {
      try {
         DomainValidator.validateDestroyPartition(param0);
         this._checkIsPotentialChild(param0, 134);
         PartitionMBean[] _old = this.getPartitions();
         PartitionMBean[] _new = (PartitionMBean[])((PartitionMBean[])this._getHelper()._removeElement(_old, PartitionMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPartitions(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public boolean arePartitionsPresent() {
      return this._customizer.arePartitionsPresent();
   }

   public String getPartitionUriSpace() {
      return this._customizer.getPartitionUriSpace();
   }

   public boolean isPartitionUriSpaceInherited() {
      return false;
   }

   public boolean isPartitionUriSpaceSet() {
      return this._isSet(135);
   }

   public void setPartitionUriSpace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PartitionUriSpace;
      this._PartitionUriSpace = param0;
      this._postSet(135, _oldVal, param0);
   }

   public void addResourceGroup(ResourceGroupMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 136)) {
         ResourceGroupMBean[] _new = (ResourceGroupMBean[])((ResourceGroupMBean[])this._getHelper()._extendArray(this.getResourceGroups(), ResourceGroupMBean.class, param0));

         try {
            this.setResourceGroups(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceGroupMBean[] getResourceGroups() {
      return this._customizer.getResourceGroups();
   }

   public boolean isResourceGroupsInherited() {
      return false;
   }

   public boolean isResourceGroupsSet() {
      return this._isSet(136);
   }

   public void removeResourceGroup(ResourceGroupMBean param0) {
      DomainValidator.validateDestroyResourceGroup(param0);
      this.destroyResourceGroup(param0);
   }

   public void setResourceGroups(ResourceGroupMBean[] param0) throws InvalidAttributeValueException {
      ResourceGroupMBean[] param0 = param0 == null ? new ResourceGroupMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 136)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ResourceGroupMBean[] _oldVal = this._ResourceGroups;
      this._ResourceGroups = (ResourceGroupMBean[])param0;
      this._postSet(136, _oldVal, param0);
   }

   public ResourceGroupMBean createResourceGroup(String param0) {
      return this._customizer.createResourceGroup(param0);
   }

   public ResourceGroupMBean lookupResourceGroup(String param0) {
      Object[] aary = (Object[])this._ResourceGroups;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ResourceGroupMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ResourceGroupMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyResourceGroup(ResourceGroupMBean param0) {
      try {
         DomainValidator.validateDestroyResourceGroup(param0);
         this._checkIsPotentialChild(param0, 136);
         ResourceGroupMBean[] _old = this.getResourceGroups();
         ResourceGroupMBean[] _new = (ResourceGroupMBean[])((ResourceGroupMBean[])this._getHelper()._removeElement(_old, ResourceGroupMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResourceGroups(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addResourceGroupTemplate(ResourceGroupTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 137)) {
         ResourceGroupTemplateMBean[] _new = (ResourceGroupTemplateMBean[])((ResourceGroupTemplateMBean[])this._getHelper()._extendArray(this.getResourceGroupTemplates(), ResourceGroupTemplateMBean.class, param0));

         try {
            this.setResourceGroupTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceGroupTemplateMBean[] getResourceGroupTemplates() {
      return this._customizer.getResourceGroupTemplates();
   }

   public boolean isResourceGroupTemplatesInherited() {
      return false;
   }

   public boolean isResourceGroupTemplatesSet() {
      return this._isSet(137);
   }

   public void removeResourceGroupTemplate(ResourceGroupTemplateMBean param0) {
      this.destroyResourceGroupTemplate(param0);
   }

   public void setResourceGroupTemplates(ResourceGroupTemplateMBean[] param0) throws InvalidAttributeValueException {
      ResourceGroupTemplateMBean[] param0 = param0 == null ? new ResourceGroupTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 137)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ResourceGroupTemplateMBean[] _oldVal = this._ResourceGroupTemplates;
      this._ResourceGroupTemplates = (ResourceGroupTemplateMBean[])param0;
      this._postSet(137, _oldVal, param0);
   }

   public ResourceGroupTemplateMBean lookupResourceGroupTemplate(String param0) {
      Object[] aary = (Object[])this._ResourceGroupTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ResourceGroupTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ResourceGroupTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ResourceGroupTemplateMBean createResourceGroupTemplate(String param0) {
      return this._customizer.createResourceGroupTemplate(param0);
   }

   public void destroyResourceGroupTemplate(ResourceGroupTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 137);
         ResourceGroupTemplateMBean[] _old = this.getResourceGroupTemplates();
         ResourceGroupTemplateMBean[] _new = (ResourceGroupTemplateMBean[])((ResourceGroupTemplateMBean[])this._getHelper()._removeElement(_old, ResourceGroupTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResourceGroupTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ConfigurationMBean[] findConfigBeansWithTags(String param0, String[] param1) {
      return this._customizer.findConfigBeansWithTags(param0, param1);
   }

   public ConfigurationMBean[] findConfigBeansWithTags(String param0, boolean param1, String[] param2) {
      return this._customizer.findConfigBeansWithTags(param0, param1, param2);
   }

   public String[] listTags(String param0) {
      return this._customizer.listTags(param0);
   }

   public int getMaxConcurrentNewThreads() {
      return this._MaxConcurrentNewThreads;
   }

   public boolean isMaxConcurrentNewThreadsInherited() {
      return false;
   }

   public boolean isMaxConcurrentNewThreadsSet() {
      return this._isSet(138);
   }

   public void setMaxConcurrentNewThreads(int param0) {
      LegalChecks.checkInRange("MaxConcurrentNewThreads", (long)param0, 0L, 65534L);
      int _oldVal = this._MaxConcurrentNewThreads;
      this._MaxConcurrentNewThreads = param0;
      this._postSet(138, _oldVal, param0);
   }

   public int getMaxConcurrentLongRunningRequests() {
      return this._MaxConcurrentLongRunningRequests;
   }

   public boolean isMaxConcurrentLongRunningRequestsInherited() {
      return false;
   }

   public boolean isMaxConcurrentLongRunningRequestsSet() {
      return this._isSet(139);
   }

   public void setMaxConcurrentLongRunningRequests(int param0) {
      LegalChecks.checkInRange("MaxConcurrentLongRunningRequests", (long)param0, 0L, 65534L);
      int _oldVal = this._MaxConcurrentLongRunningRequests;
      this._MaxConcurrentLongRunningRequests = param0;
      this._postSet(139, _oldVal, param0);
   }

   public boolean isParallelDeployApplications() {
      if (!this._isSet(140)) {
         try {
            return !LegalHelper.versionEarlierThan(this.getDomainVersion(), "12.2.1.0");
         } catch (NullPointerException var2) {
         }
      }

      return this._ParallelDeployApplications;
   }

   public boolean isParallelDeployApplicationsInherited() {
      return false;
   }

   public boolean isParallelDeployApplicationsSet() {
      return this._isSet(140);
   }

   public void setParallelDeployApplications(boolean param0) {
      boolean _oldVal = this._ParallelDeployApplications;
      this._ParallelDeployApplications = param0;
      this._postSet(140, _oldVal, param0);
   }

   public boolean isParallelDeployApplicationModules() {
      return this._ParallelDeployApplicationModules;
   }

   public boolean isParallelDeployApplicationModulesInherited() {
      return false;
   }

   public boolean isParallelDeployApplicationModulesSet() {
      return this._isSet(141);
   }

   public void setParallelDeployApplicationModules(boolean param0) {
      boolean _oldVal = this._ParallelDeployApplicationModules;
      this._ParallelDeployApplicationModules = param0;
      this._postSet(141, _oldVal, param0);
   }

   public void addManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 142)) {
         ManagedExecutorServiceTemplateMBean[] _new;
         if (this._isSet(142)) {
            _new = (ManagedExecutorServiceTemplateMBean[])((ManagedExecutorServiceTemplateMBean[])this._getHelper()._extendArray(this.getManagedExecutorServiceTemplates(), ManagedExecutorServiceTemplateMBean.class, param0));
         } else {
            _new = new ManagedExecutorServiceTemplateMBean[]{param0};
         }

         try {
            this.setManagedExecutorServiceTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedExecutorServiceTemplateMBean[] getManagedExecutorServiceTemplates() {
      return this._ManagedExecutorServiceTemplates;
   }

   public boolean isManagedExecutorServiceTemplatesInherited() {
      return false;
   }

   public boolean isManagedExecutorServiceTemplatesSet() {
      return this._isSet(142);
   }

   public void removeManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean param0) {
      this.destroyManagedExecutorServiceTemplate(param0);
   }

   public void setManagedExecutorServiceTemplates(ManagedExecutorServiceTemplateMBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceTemplateMBean[] param0 = param0 == null ? new ManagedExecutorServiceTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 142)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedExecutorServiceTemplateMBean[] _oldVal = this._ManagedExecutorServiceTemplates;
      this._ManagedExecutorServiceTemplates = (ManagedExecutorServiceTemplateMBean[])param0;
      this._postSet(142, _oldVal, param0);
   }

   public ManagedExecutorServiceTemplateMBean createManagedExecutorServiceTemplate(String param0) {
      ManagedExecutorServiceTemplateMBeanImpl lookup = (ManagedExecutorServiceTemplateMBeanImpl)this.lookupManagedExecutorServiceTemplate(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedExecutorServiceTemplateMBeanImpl _val = new ManagedExecutorServiceTemplateMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedExecutorServiceTemplate(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 142);
         ManagedExecutorServiceTemplateMBean[] _old = this.getManagedExecutorServiceTemplates();
         ManagedExecutorServiceTemplateMBean[] _new = (ManagedExecutorServiceTemplateMBean[])((ManagedExecutorServiceTemplateMBean[])this._getHelper()._removeElement(_old, ManagedExecutorServiceTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedExecutorServiceTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ManagedExecutorServiceTemplateMBean lookupManagedExecutorServiceTemplate(String param0) {
      Object[] aary = (Object[])this._ManagedExecutorServiceTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedExecutorServiceTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedExecutorServiceTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedScheduledExecutorServiceTemplate(ManagedScheduledExecutorServiceTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 143)) {
         ManagedScheduledExecutorServiceTemplateMBean[] _new;
         if (this._isSet(143)) {
            _new = (ManagedScheduledExecutorServiceTemplateMBean[])((ManagedScheduledExecutorServiceTemplateMBean[])this._getHelper()._extendArray(this.getManagedScheduledExecutorServiceTemplates(), ManagedScheduledExecutorServiceTemplateMBean.class, param0));
         } else {
            _new = new ManagedScheduledExecutorServiceTemplateMBean[]{param0};
         }

         try {
            this.setManagedScheduledExecutorServiceTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedScheduledExecutorServiceTemplateMBean[] getManagedScheduledExecutorServiceTemplates() {
      return this._ManagedScheduledExecutorServiceTemplates;
   }

   public boolean isManagedScheduledExecutorServiceTemplatesInherited() {
      return false;
   }

   public boolean isManagedScheduledExecutorServiceTemplatesSet() {
      return this._isSet(143);
   }

   public void removeManagedScheduledExecutorServiceTemplate(ManagedScheduledExecutorServiceTemplateMBean param0) {
      this.destroyManagedScheduledExecutorServiceTemplate(param0);
   }

   public void setManagedScheduledExecutorServiceTemplates(ManagedScheduledExecutorServiceTemplateMBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceTemplateMBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 143)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedScheduledExecutorServiceTemplateMBean[] _oldVal = this._ManagedScheduledExecutorServiceTemplates;
      this._ManagedScheduledExecutorServiceTemplates = (ManagedScheduledExecutorServiceTemplateMBean[])param0;
      this._postSet(143, _oldVal, param0);
   }

   public ManagedScheduledExecutorServiceTemplateMBean createManagedScheduledExecutorServiceTemplate(String param0) {
      ManagedScheduledExecutorServiceTemplateMBeanImpl lookup = (ManagedScheduledExecutorServiceTemplateMBeanImpl)this.lookupManagedScheduledExecutorServiceTemplate(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedScheduledExecutorServiceTemplateMBeanImpl _val = new ManagedScheduledExecutorServiceTemplateMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedScheduledExecutorServiceTemplate(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyManagedScheduledExecutorServiceTemplate(ManagedScheduledExecutorServiceTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 143);
         ManagedScheduledExecutorServiceTemplateMBean[] _old = this.getManagedScheduledExecutorServiceTemplates();
         ManagedScheduledExecutorServiceTemplateMBean[] _new = (ManagedScheduledExecutorServiceTemplateMBean[])((ManagedScheduledExecutorServiceTemplateMBean[])this._getHelper()._removeElement(_old, ManagedScheduledExecutorServiceTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedScheduledExecutorServiceTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ManagedScheduledExecutorServiceTemplateMBean lookupManagedScheduledExecutorServiceTemplate(String param0) {
      Object[] aary = (Object[])this._ManagedScheduledExecutorServiceTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedScheduledExecutorServiceTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedScheduledExecutorServiceTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedThreadFactoryTemplate(ManagedThreadFactoryTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 144)) {
         ManagedThreadFactoryTemplateMBean[] _new;
         if (this._isSet(144)) {
            _new = (ManagedThreadFactoryTemplateMBean[])((ManagedThreadFactoryTemplateMBean[])this._getHelper()._extendArray(this.getManagedThreadFactoryTemplates(), ManagedThreadFactoryTemplateMBean.class, param0));
         } else {
            _new = new ManagedThreadFactoryTemplateMBean[]{param0};
         }

         try {
            this.setManagedThreadFactoryTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedThreadFactoryTemplateMBean[] getManagedThreadFactoryTemplates() {
      return this._ManagedThreadFactoryTemplates;
   }

   public boolean isManagedThreadFactoryTemplatesInherited() {
      return false;
   }

   public boolean isManagedThreadFactoryTemplatesSet() {
      return this._isSet(144);
   }

   public void removeManagedThreadFactoryTemplate(ManagedThreadFactoryTemplateMBean param0) {
      this.destroyManagedThreadFactoryTemplate(param0);
   }

   public void setManagedThreadFactoryTemplates(ManagedThreadFactoryTemplateMBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryTemplateMBean[] param0 = param0 == null ? new ManagedThreadFactoryTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 144)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedThreadFactoryTemplateMBean[] _oldVal = this._ManagedThreadFactoryTemplates;
      this._ManagedThreadFactoryTemplates = (ManagedThreadFactoryTemplateMBean[])param0;
      this._postSet(144, _oldVal, param0);
   }

   public ManagedThreadFactoryTemplateMBean createManagedThreadFactoryTemplate(String param0) {
      ManagedThreadFactoryTemplateMBeanImpl lookup = (ManagedThreadFactoryTemplateMBeanImpl)this.lookupManagedThreadFactoryTemplate(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedThreadFactoryTemplateMBeanImpl _val = new ManagedThreadFactoryTemplateMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedThreadFactoryTemplate(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyManagedThreadFactoryTemplate(ManagedThreadFactoryTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 144);
         ManagedThreadFactoryTemplateMBean[] _old = this.getManagedThreadFactoryTemplates();
         ManagedThreadFactoryTemplateMBean[] _new = (ManagedThreadFactoryTemplateMBean[])((ManagedThreadFactoryTemplateMBean[])this._getHelper()._removeElement(_old, ManagedThreadFactoryTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedThreadFactoryTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ManagedThreadFactoryTemplateMBean lookupManagedThreadFactoryTemplate(String param0) {
      Object[] aary = (Object[])this._ManagedThreadFactoryTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedThreadFactoryTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedThreadFactoryTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ManagedExecutorServiceMBean[] getManagedExecutorServices() {
      return this._ManagedExecutorServices;
   }

   public boolean isManagedExecutorServicesInherited() {
      return false;
   }

   public boolean isManagedExecutorServicesSet() {
      return this._isSet(145);
   }

   public void removeManagedExecutorService(ManagedExecutorServiceMBean param0) {
      this.destroyManagedExecutorService(param0);
   }

   public void setManagedExecutorServices(ManagedExecutorServiceMBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceMBean[] param0 = param0 == null ? new ManagedExecutorServiceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 145)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedExecutorServiceMBean[] _oldVal = this._ManagedExecutorServices;
      this._ManagedExecutorServices = (ManagedExecutorServiceMBean[])param0;
      this._postSet(145, _oldVal, param0);
   }

   public ManagedExecutorServiceMBean createManagedExecutorService(String param0) {
      ManagedExecutorServiceMBeanImpl lookup = (ManagedExecutorServiceMBeanImpl)this.lookupManagedExecutorService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedExecutorServiceMBeanImpl _val = new ManagedExecutorServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedExecutorService(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyManagedExecutorService(ManagedExecutorServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 145);
         ManagedExecutorServiceMBean[] _old = this.getManagedExecutorServices();
         ManagedExecutorServiceMBean[] _new = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])this._getHelper()._removeElement(_old, ManagedExecutorServiceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedExecutorServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ManagedExecutorServiceMBean lookupManagedExecutorService(String param0) {
      Object[] aary = (Object[])this._ManagedExecutorServices;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedExecutorServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedExecutorServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedExecutorService(ManagedExecutorServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 145)) {
         ManagedExecutorServiceMBean[] _new;
         if (this._isSet(145)) {
            _new = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])this._getHelper()._extendArray(this.getManagedExecutorServices(), ManagedExecutorServiceMBean.class, param0));
         } else {
            _new = new ManagedExecutorServiceMBean[]{param0};
         }

         try {
            this.setManagedExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedScheduledExecutorServiceMBean[] getManagedScheduledExecutorServices() {
      return this._ManagedScheduledExecutorServices;
   }

   public boolean isManagedScheduledExecutorServicesInherited() {
      return false;
   }

   public boolean isManagedScheduledExecutorServicesSet() {
      return this._isSet(146);
   }

   public void removeManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean param0) {
      this.destroyManagedScheduledExecutorService(param0);
   }

   public void setManagedScheduledExecutorServices(ManagedScheduledExecutorServiceMBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceMBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 146)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedScheduledExecutorServiceMBean[] _oldVal = this._ManagedScheduledExecutorServices;
      this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceMBean[])param0;
      this._postSet(146, _oldVal, param0);
   }

   public ManagedScheduledExecutorServiceMBean createManagedScheduledExecutorService(String param0) {
      ManagedScheduledExecutorServiceMBeanImpl lookup = (ManagedScheduledExecutorServiceMBeanImpl)this.lookupManagedScheduledExecutorService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedScheduledExecutorServiceMBeanImpl _val = new ManagedScheduledExecutorServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedScheduledExecutorService(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 146);
         ManagedScheduledExecutorServiceMBean[] _old = this.getManagedScheduledExecutorServices();
         ManagedScheduledExecutorServiceMBean[] _new = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])this._getHelper()._removeElement(_old, ManagedScheduledExecutorServiceMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedScheduledExecutorServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ManagedScheduledExecutorServiceMBean lookupManagedScheduledExecutorService(String param0) {
      Object[] aary = (Object[])this._ManagedScheduledExecutorServices;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedScheduledExecutorServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedScheduledExecutorServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 146)) {
         ManagedScheduledExecutorServiceMBean[] _new;
         if (this._isSet(146)) {
            _new = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])this._getHelper()._extendArray(this.getManagedScheduledExecutorServices(), ManagedScheduledExecutorServiceMBean.class, param0));
         } else {
            _new = new ManagedScheduledExecutorServiceMBean[]{param0};
         }

         try {
            this.setManagedScheduledExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedThreadFactoryMBean[] getManagedThreadFactories() {
      return this._ManagedThreadFactories;
   }

   public boolean isManagedThreadFactoriesInherited() {
      return false;
   }

   public boolean isManagedThreadFactoriesSet() {
      return this._isSet(147);
   }

   public void removeManagedThreadFactory(ManagedThreadFactoryMBean param0) {
      this.destroyManagedThreadFactory(param0);
   }

   public void setManagedThreadFactories(ManagedThreadFactoryMBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryMBean[] param0 = param0 == null ? new ManagedThreadFactoryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 147)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedThreadFactoryMBean[] _oldVal = this._ManagedThreadFactories;
      this._ManagedThreadFactories = (ManagedThreadFactoryMBean[])param0;
      this._postSet(147, _oldVal, param0);
   }

   public ManagedThreadFactoryMBean createManagedThreadFactory(String param0) {
      ManagedThreadFactoryMBeanImpl lookup = (ManagedThreadFactoryMBeanImpl)this.lookupManagedThreadFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedThreadFactoryMBeanImpl _val = new ManagedThreadFactoryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedThreadFactory(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyManagedThreadFactory(ManagedThreadFactoryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 147);
         ManagedThreadFactoryMBean[] _old = this.getManagedThreadFactories();
         ManagedThreadFactoryMBean[] _new = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])this._getHelper()._removeElement(_old, ManagedThreadFactoryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedThreadFactories(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ManagedThreadFactoryMBean lookupManagedThreadFactory(String param0) {
      Object[] aary = (Object[])this._ManagedThreadFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedThreadFactoryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedThreadFactoryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedThreadFactory(ManagedThreadFactoryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 147)) {
         ManagedThreadFactoryMBean[] _new;
         if (this._isSet(147)) {
            _new = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])this._getHelper()._extendArray(this.getManagedThreadFactories(), ManagedThreadFactoryMBean.class, param0));
         } else {
            _new = new ManagedThreadFactoryMBean[]{param0};
         }

         try {
            this.setManagedThreadFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void addPartitionTemplate(PartitionTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 148)) {
         PartitionTemplateMBean[] _new = (PartitionTemplateMBean[])((PartitionTemplateMBean[])this._getHelper()._extendArray(this.getPartitionTemplates(), PartitionTemplateMBean.class, param0));

         try {
            this.setPartitionTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PartitionTemplateMBean[] getPartitionTemplates() {
      return this._customizer.getPartitionTemplates();
   }

   public boolean isPartitionTemplatesInherited() {
      return false;
   }

   public boolean isPartitionTemplatesSet() {
      return this._isSet(148);
   }

   public void removePartitionTemplate(PartitionTemplateMBean param0) {
      this.destroyPartitionTemplate(param0);
   }

   public void setPartitionTemplates(PartitionTemplateMBean[] param0) throws InvalidAttributeValueException {
      PartitionTemplateMBean[] param0 = param0 == null ? new PartitionTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 148)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PartitionTemplateMBean[] _oldVal = this._PartitionTemplates;
      this._PartitionTemplates = (PartitionTemplateMBean[])param0;
      this._postSet(148, _oldVal, param0);
   }

   public PartitionTemplateMBean lookupPartitionTemplate(String param0) {
      Object[] aary = (Object[])this._PartitionTemplates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PartitionTemplateMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PartitionTemplateMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public PartitionTemplateMBean createPartitionTemplate(String param0) {
      return this._customizer.createPartitionTemplate(param0);
   }

   public void destroyPartitionTemplate(PartitionTemplateMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 148);
         PartitionTemplateMBean[] _old = this.getPartitionTemplates();
         PartitionTemplateMBean[] _new = (PartitionTemplateMBean[])((PartitionTemplateMBean[])this._getHelper()._removeElement(_old, PartitionTemplateMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPartitionTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public PartitionMBean createPartitionFromTemplate(String param0, PartitionTemplateMBean param1) {
      return this._customizer.createPartitionFromTemplate(param0, param1);
   }

   public void addLifecycleManagerEndPoint(LifecycleManagerEndPointMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 149)) {
         LifecycleManagerEndPointMBean[] _new;
         if (this._isSet(149)) {
            _new = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])this._getHelper()._extendArray(this.getLifecycleManagerEndPoints(), LifecycleManagerEndPointMBean.class, param0));
         } else {
            _new = new LifecycleManagerEndPointMBean[]{param0};
         }

         try {
            this.setLifecycleManagerEndPoints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleManagerEndPointMBean[] getLifecycleManagerEndPoints() {
      return this._LifecycleManagerEndPoints;
   }

   public boolean isLifecycleManagerEndPointsInherited() {
      return false;
   }

   public boolean isLifecycleManagerEndPointsSet() {
      return this._isSet(149);
   }

   public void removeLifecycleManagerEndPoint(LifecycleManagerEndPointMBean param0) {
      this.destroyLifecycleManagerEndPoint(param0);
   }

   public void setLifecycleManagerEndPoints(LifecycleManagerEndPointMBean[] param0) throws InvalidAttributeValueException {
      LifecycleManagerEndPointMBean[] param0 = param0 == null ? new LifecycleManagerEndPointMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 149)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleManagerEndPointMBean[] _oldVal = this._LifecycleManagerEndPoints;
      this._LifecycleManagerEndPoints = (LifecycleManagerEndPointMBean[])param0;
      this._postSet(149, _oldVal, param0);
   }

   public LifecycleManagerEndPointMBean lookupLifecycleManagerEndPoint(String param0) {
      Object[] aary = (Object[])this._LifecycleManagerEndPoints;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      LifecycleManagerEndPointMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (LifecycleManagerEndPointMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public LifecycleManagerEndPointMBean createLifecycleManagerEndPoint(String param0) {
      LifecycleManagerEndPointMBeanImpl lookup = (LifecycleManagerEndPointMBeanImpl)this.lookupLifecycleManagerEndPoint(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         LifecycleManagerEndPointMBeanImpl _val = new LifecycleManagerEndPointMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addLifecycleManagerEndPoint(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyLifecycleManagerEndPoint(LifecycleManagerEndPointMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 149);
         LifecycleManagerEndPointMBean[] _old = this.getLifecycleManagerEndPoints();
         LifecycleManagerEndPointMBean[] _new = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])this._getHelper()._removeElement(_old, LifecycleManagerEndPointMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLifecycleManagerEndPoints(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public InterceptorsMBean getInterceptors() {
      return this._Interceptors;
   }

   public boolean isInterceptorsInherited() {
      return false;
   }

   public boolean isInterceptorsSet() {
      return this._isSet(150) || this._isAnythingSet((AbstractDescriptorBean)this.getInterceptors());
   }

   public void setInterceptors(InterceptorsMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 150)) {
         this._postCreate(_child);
      }

      InterceptorsMBean _oldVal = this._Interceptors;
      this._Interceptors = param0;
      this._postSet(150, _oldVal, param0);
   }

   public BatchConfigMBean getBatchConfig() {
      return this._BatchConfig;
   }

   public boolean isBatchConfigInherited() {
      return false;
   }

   public boolean isBatchConfigSet() {
      return this._isSet(151) || this._isAnythingSet((AbstractDescriptorBean)this.getBatchConfig());
   }

   public void setBatchConfig(BatchConfigMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 151)) {
         this._postCreate(_child);
      }

      BatchConfigMBean _oldVal = this._BatchConfig;
      this._BatchConfig = param0;
      this._postSet(151, _oldVal, param0);
   }

   public DebugPatchesMBean getDebugPatches() {
      return this._DebugPatches;
   }

   public boolean isDebugPatchesInherited() {
      return false;
   }

   public boolean isDebugPatchesSet() {
      return this._isSet(152) || this._isAnythingSet((AbstractDescriptorBean)this.getDebugPatches());
   }

   public void setDebugPatches(DebugPatchesMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 152)) {
         this._postCreate(_child);
      }

      DebugPatchesMBean _oldVal = this._DebugPatches;
      this._DebugPatches = param0;
      this._postSet(152, _oldVal, param0);
   }

   public void addPartitionWorkManager(PartitionWorkManagerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 153)) {
         PartitionWorkManagerMBean[] _new = (PartitionWorkManagerMBean[])((PartitionWorkManagerMBean[])this._getHelper()._extendArray(this.getPartitionWorkManagers(), PartitionWorkManagerMBean.class, param0));

         try {
            this.setPartitionWorkManagers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PartitionWorkManagerMBean[] getPartitionWorkManagers() {
      return this._customizer.getPartitionWorkManagers();
   }

   public boolean isPartitionWorkManagersInherited() {
      return false;
   }

   public boolean isPartitionWorkManagersSet() {
      return this._isSet(153);
   }

   public void removePartitionWorkManager(PartitionWorkManagerMBean param0) {
      this.destroyPartitionWorkManager(param0);
   }

   public void setPartitionWorkManagers(PartitionWorkManagerMBean[] param0) throws InvalidAttributeValueException {
      PartitionWorkManagerMBean[] param0 = param0 == null ? new PartitionWorkManagerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 153)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      PartitionWorkManagerMBean[] _oldVal = this._PartitionWorkManagers;
      this._PartitionWorkManagers = (PartitionWorkManagerMBean[])param0;
      this._postSet(153, _oldVal, param0);
   }

   public PartitionWorkManagerMBean createPartitionWorkManager(String param0) {
      return this._customizer.createPartitionWorkManager(param0);
   }

   public void destroyPartitionWorkManager(PartitionWorkManagerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 153);
         PartitionWorkManagerMBean[] _old = this.getPartitionWorkManagers();
         PartitionWorkManagerMBean[] _new = (PartitionWorkManagerMBean[])((PartitionWorkManagerMBean[])this._getHelper()._removeElement(_old, PartitionWorkManagerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPartitionWorkManagers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public PartitionWorkManagerMBean lookupPartitionWorkManager(String param0) {
      Object[] aary = (Object[])this._PartitionWorkManagers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PartitionWorkManagerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PartitionWorkManagerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setDiagnosticContextCompatibilityModeEnabled(boolean param0) {
      boolean _oldVal = this._DiagnosticContextCompatibilityModeEnabled;
      this._DiagnosticContextCompatibilityModeEnabled = param0;
      this._postSet(154, _oldVal, param0);
   }

   public boolean isDiagnosticContextCompatibilityModeEnabled() {
      return this._DiagnosticContextCompatibilityModeEnabled;
   }

   public boolean isDiagnosticContextCompatibilityModeEnabledInherited() {
      return false;
   }

   public boolean isDiagnosticContextCompatibilityModeEnabledSet() {
      return this._isSet(154);
   }

   public String getBatchJobsDataSourceJndiName() {
      return this._BatchJobsDataSourceJndiName;
   }

   public boolean isBatchJobsDataSourceJndiNameInherited() {
      return false;
   }

   public boolean isBatchJobsDataSourceJndiNameSet() {
      return this._isSet(155);
   }

   public void setBatchJobsDataSourceJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BatchJobsDataSourceJndiName;
      this._BatchJobsDataSourceJndiName = param0;
      this._postSet(155, _oldVal, param0);
   }

   public String getBatchJobsExecutorServiceName() {
      return this._BatchJobsExecutorServiceName;
   }

   public boolean isBatchJobsExecutorServiceNameInherited() {
      return false;
   }

   public boolean isBatchJobsExecutorServiceNameSet() {
      return this._isSet(156);
   }

   public void setBatchJobsExecutorServiceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BatchJobsExecutorServiceName;
      this._BatchJobsExecutorServiceName = param0;
      this._postSet(156, _oldVal, param0);
   }

   public OptionalFeatureDeploymentMBean getOptionalFeatureDeployment() {
      return this._OptionalFeatureDeployment;
   }

   public boolean isOptionalFeatureDeploymentInherited() {
      return false;
   }

   public boolean isOptionalFeatureDeploymentSet() {
      return this._isSet(157) || this._isAnythingSet((AbstractDescriptorBean)this.getOptionalFeatureDeployment());
   }

   public void setOptionalFeatureDeployment(OptionalFeatureDeploymentMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 157)) {
         this._postCreate(_child);
      }

      OptionalFeatureDeploymentMBean _oldVal = this._OptionalFeatureDeployment;
      this._OptionalFeatureDeployment = param0;
      this._postSet(157, _oldVal, param0);
   }

   public LifecycleManagerConfigMBean getLifecycleManagerConfig() {
      return this._LifecycleManagerConfig;
   }

   public boolean isLifecycleManagerConfigInherited() {
      return false;
   }

   public boolean isLifecycleManagerConfigSet() {
      return this._isSet(158) || this._isAnythingSet((AbstractDescriptorBean)this.getLifecycleManagerConfig());
   }

   public void setLifecycleManagerConfig(LifecycleManagerConfigMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 158)) {
         this._postCreate(_child);
      }

      LifecycleManagerConfigMBean _oldVal = this._LifecycleManagerConfig;
      this._LifecycleManagerConfig = param0;
      this._postSet(158, _oldVal, param0);
   }

   public String getSiteName() {
      return this._SiteName;
   }

   public boolean isSiteNameInherited() {
      return false;
   }

   public boolean isSiteNameSet() {
      return this._isSet(159);
   }

   public void setSiteName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SiteName;
      this._SiteName = param0;
      this._postSet(159, _oldVal, param0);
   }

   public boolean isEnableEECompliantClassloadingForEmbeddedAdapters() {
      if (!this._isSet(160)) {
         try {
            return !LegalHelper.versionEarlierThan(this.getDomainVersion(), "12.2.1.0");
         } catch (NullPointerException var2) {
         }
      }

      return this._EnableEECompliantClassloadingForEmbeddedAdapters;
   }

   public boolean isEnableEECompliantClassloadingForEmbeddedAdaptersInherited() {
      return false;
   }

   public boolean isEnableEECompliantClassloadingForEmbeddedAdaptersSet() {
      return this._isSet(160);
   }

   public void setEnableEECompliantClassloadingForEmbeddedAdapters(boolean param0) {
      boolean _oldVal = this._EnableEECompliantClassloadingForEmbeddedAdapters;
      this._EnableEECompliantClassloadingForEmbeddedAdapters = param0;
      this._postSet(160, _oldVal, param0);
   }

   public VirtualTargetMBean[] findAllVirtualTargets() {
      return this._customizer.findAllVirtualTargets();
   }

   public VirtualTargetMBean lookupInAllVirtualTargets(String param0) {
      return this._customizer.lookupInAllVirtualTargets(param0);
   }

   public TargetMBean[] findAllTargets() {
      return this._customizer.findAllTargets();
   }

   public TargetMBean lookupInAllTargets(String param0) {
      return this._customizer.lookupInAllTargets(param0);
   }

   public boolean isDBPassiveMode() {
      return this._DBPassiveMode;
   }

   public boolean isDBPassiveModeInherited() {
      return false;
   }

   public boolean isDBPassiveModeSet() {
      return this._isSet(161);
   }

   public void setDBPassiveMode(boolean param0) {
      boolean _oldVal = this._DBPassiveMode;
      this._DBPassiveMode = param0;
      this._postSet(161, _oldVal, param0);
   }

   public int getDBPassiveModeGracePeriodSeconds() {
      return this._DBPassiveModeGracePeriodSeconds;
   }

   public boolean isDBPassiveModeGracePeriodSecondsInherited() {
      return false;
   }

   public boolean isDBPassiveModeGracePeriodSecondsSet() {
      return this._isSet(162);
   }

   public void setDBPassiveModeGracePeriodSeconds(int param0) {
      LegalChecks.checkInRange("DBPassiveModeGracePeriodSeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._DBPassiveModeGracePeriodSeconds;
      this._DBPassiveModeGracePeriodSeconds = param0;
      this._postSet(162, _oldVal, param0);
   }

   public String getInstalledSoftwareVersion() {
      return this._InstalledSoftwareVersion;
   }

   public boolean isInstalledSoftwareVersionInherited() {
      return false;
   }

   public boolean isInstalledSoftwareVersionSet() {
      return this._isSet(163);
   }

   public void setInstalledSoftwareVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InstalledSoftwareVersion;
      this._InstalledSoftwareVersion = param0;
      this._postSet(163, _oldVal, param0);
   }

   public void addCallout(CalloutMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 164)) {
         CalloutMBean[] _new;
         if (this._isSet(164)) {
            _new = (CalloutMBean[])((CalloutMBean[])this._getHelper()._extendArray(this.getCallouts(), CalloutMBean.class, param0));
         } else {
            _new = new CalloutMBean[]{param0};
         }

         try {
            this.setCallouts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CalloutMBean[] getCallouts() {
      return this._Callouts;
   }

   public boolean isCalloutsInherited() {
      return false;
   }

   public boolean isCalloutsSet() {
      return this._isSet(164);
   }

   public void removeCallout(CalloutMBean param0) {
      this.destroyCallout(param0);
   }

   public void setCallouts(CalloutMBean[] param0) throws InvalidAttributeValueException {
      CalloutMBean[] param0 = param0 == null ? new CalloutMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 164)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CalloutMBean[] _oldVal = this._Callouts;
      this._Callouts = (CalloutMBean[])param0;
      this._postSet(164, _oldVal, param0);
   }

   public CalloutMBean createCallout(String param0) {
      CalloutMBeanImpl lookup = (CalloutMBeanImpl)this.lookupCallout(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CalloutMBeanImpl _val = new CalloutMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCallout(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCallout(CalloutMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 164);
         CalloutMBean[] _old = this.getCallouts();
         CalloutMBean[] _new = (CalloutMBean[])((CalloutMBean[])this._getHelper()._removeElement(_old, CalloutMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCallouts(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      DomainValidator.validateDomain(this);
      LegalChecks.checkIsSet("DomainVersion", this.isDomainVersionSet());
   }

   public CalloutMBean lookupCallout(String param0) {
      Object[] aary = (Object[])this._Callouts;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CalloutMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CalloutMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
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
      return super._isAnythingSet() || this.isAdminConsoleSet() || this.isBatchConfigSet() || this.isCdiContainerSet() || this.isDebugPatchesSet() || this.isDeploymentConfigurationSet() || this.isEmbeddedLDAPSet() || this.isInterceptorsSet() || this.isJMSDestinationsSet() || this.isJMXSet() || this.isJPASet() || this.isJTASet() || this.isLifecycleManagerConfigSet() || this.isLogSet() || this.isOptionalFeatureDeploymentSet() || this.isResourceManagementSet() || this.isRestfulManagementServicesSet() || this.isSNMPAgentSet() || this.isSecurityConfigurationSet() || this.isSelfTuningSet() || this.isWebAppContainerSet() || this.isWebserviceTestpageSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 119;
      }

      try {
         switch (idx) {
            case 119:
               this._AdminConsole = new AdminConsoleMBeanImpl(this, 119);
               this._postCreate((AbstractDescriptorBean)this._AdminConsole);
               if (initOne) {
                  break;
               }
            case 104:
               this._AdminServerMBean = null;
               if (initOne) {
                  break;
               }
            case 97:
               this._AdminServerName = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._AdministrationPort = 9002;
               if (initOne) {
                  break;
               }
            case 98:
               this._AdministrationProtocol = "t3s";
               if (initOne) {
                  break;
               }
            case 48:
               this._AppDeployments = new AppDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 47:
               this._Applications = new ApplicationMBean[0];
               if (initOne) {
                  break;
               }
            case 41:
               this._ArchiveConfigurationCount = 0;
               if (initOne) {
                  break;
               }
            case 151:
               this._BatchConfig = new BatchConfigMBeanImpl(this, 151);
               this._postCreate((AbstractDescriptorBean)this._BatchConfig);
               if (initOne) {
                  break;
               }
            case 155:
               this._BatchJobsDataSourceJndiName = null;
               if (initOne) {
                  break;
               }
            case 156:
               this._BatchJobsExecutorServiceName = null;
               if (initOne) {
                  break;
               }
            case 83:
               this._BridgeDestinations = new BridgeDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 164:
               this._Callouts = new CalloutMBean[0];
               if (initOne) {
                  break;
               }
            case 74:
               this._CdiContainer = new CdiContainerMBeanImpl(this, 74);
               this._postCreate((AbstractDescriptorBean)this._CdiContainer);
               if (initOne) {
                  break;
               }
            case 31:
               this._Clusters = new ClusterMBean[0];
               if (initOne) {
                  break;
               }
            case 125:
               this._CoherenceClusterSystemResources = new CoherenceClusterSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 133:
               this._CoherenceManagementClusters = new CoherenceManagementClusterMBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._CoherenceServers = new CoherenceServerMBean[0];
               if (initOne) {
                  break;
               }
            case 45:
               this._ConfigurationAuditType = "none";
               if (initOne) {
                  break;
               }
            case 43:
               this._ConfigurationVersion = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ConsoleContextPath = "console";
               if (initOne) {
                  break;
               }
            case 26:
               this._ConsoleExtensionDirectory = "console-ext";
               if (initOne) {
                  break;
               }
            case 95:
               this._CustomResources = new CustomResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 162:
               this._DBPassiveModeGracePeriodSeconds = 30;
               if (initOne) {
                  break;
               }
            case 152:
               this._DebugPatches = new DebugPatchesMBeanImpl(this, 152);
               this._postCreate((AbstractDescriptorBean)this._DebugPatches);
               if (initOne) {
                  break;
               }
            case 17:
               this._DeploymentConfiguration = new DeploymentConfigurationMBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._DeploymentConfiguration);
               if (initOne) {
                  break;
               }
            case 32:
               this._Deployments = new DeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 51:
               this._DomainLibraries = new DomainLibraryMBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._DomainVersion = null;
               if (initOne) {
                  break;
               }
            case 72:
               this._EJBContainer = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._EmbeddedLDAP = new EmbeddedLDAPMBeanImpl(this, 36);
               this._postCreate((AbstractDescriptorBean)this._EmbeddedLDAP);
               if (initOne) {
                  break;
               }
            case 91:
               this._FileStores = new FileStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 33:
               this._FileT3s = new FileT3MBean[0];
               if (initOne) {
                  break;
               }
            case 115:
               this._ForeignJMSConnectionFactories = new ForeignJMSConnectionFactoryMBean[0];
               if (initOne) {
                  break;
               }
            case 116:
               this._ForeignJMSDestinations = new ForeignJMSDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 84:
               this._ForeignJMSServers = new ForeignJMSServerMBean[0];
               if (initOne) {
                  break;
               }
            case 96:
               this._ForeignJNDIProviders = new ForeignJNDIProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 163:
               this._InstalledSoftwareVersion = null;
               if (initOne) {
                  break;
               }
            case 150:
               this._Interceptors = new InterceptorsMBeanImpl(this, 150);
               this._postCreate((AbstractDescriptorBean)this._Interceptors);
               if (initOne) {
                  break;
               }
            case 49:
               this._InternalAppDeployments = new AppDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 52:
               this._InternalLibraries = new LibraryMBean[0];
               if (initOne) {
                  break;
               }
            case 93:
               this._JDBCStores = new JDBCStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 100:
               this._JDBCSystemResources = new JDBCSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 82:
               this._JMSBridgeDestinations = new JMSBridgeDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 117:
               this._JMSConnectionConsumers = new JMSConnectionConsumerMBean[0];
               if (initOne) {
                  break;
               }
            case 80:
               this._JMSConnectionFactories = new JMSConnectionFactoryMBean[0];
               if (initOne) {
                  break;
               }
            case 79:
               this._JMSDestinationKeys = new JMSDestinationKeyMBean[0];
               if (initOne) {
                  break;
               }
            case 62:
               this._JMSDestinations = new JMSDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 105:
               this._JMSDistributedQueueMembers = new JMSDistributedQueueMemberMBean[0];
               if (initOne) {
                  break;
               }
            case 65:
               this._JMSDistributedQueues = new JMSDistributedQueueMBean[0];
               if (initOne) {
                  break;
               }
            case 106:
               this._JMSDistributedTopicMembers = new JMSDistributedTopicMemberMBean[0];
               if (initOne) {
                  break;
               }
            case 66:
               this._JMSDistributedTopics = new JMSDistributedTopicMBean[0];
               if (initOne) {
                  break;
               }
            case 61:
               this._JMSFileStores = new JMSFileStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 60:
               this._JMSJDBCStores = new JMSJDBCStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 63:
               this._JMSQueues = new JMSQueueMBean[0];
               if (initOne) {
                  break;
               }
            case 58:
               this._JMSServers = new JMSServerMBean[0];
               if (initOne) {
                  break;
               }
            case 81:
               this._JMSSessionPools = new JMSSessionPoolMBean[0];
               if (initOne) {
                  break;
               }
            case 59:
               this._JMSStores = new JMSStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 94:
               this._JMSSystemResources = new JMSSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 67:
               this._JMSTemplates = new JMSTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 64:
               this._JMSTopics = new JMSTopicMBean[0];
               if (initOne) {
                  break;
               }
            case 75:
               this._JMX = new JMXMBeanImpl(this, 75);
               this._postCreate((AbstractDescriptorBean)this._JMX);
               if (initOne) {
                  break;
               }
            case 16:
               this._JPA = new JPAMBeanImpl(this, 16);
               this._postCreate((AbstractDescriptorBean)this._JPA);
               if (initOne) {
                  break;
               }
            case 15:
               this._JTA = new JTAMBeanImpl(this, 15);
               this._postCreate((AbstractDescriptorBean)this._JTA);
               if (initOne) {
                  break;
               }
            case 89:
               this._JoltConnectionPools = new JoltConnectionPoolMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._LastModificationTime = 0L;
               if (initOne) {
                  break;
               }
            case 50:
               this._Libraries = new LibraryMBean[0];
               if (initOne) {
                  break;
               }
            case 158:
               this._LifecycleManagerConfig = new LifecycleManagerConfigMBeanImpl(this, 158);
               this._postCreate((AbstractDescriptorBean)this._LifecycleManagerConfig);
               if (initOne) {
                  break;
               }
            case 149:
               this._LifecycleManagerEndPoints = new LifecycleManagerEndPointMBean[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._Log = new LogMBeanImpl(this, 19);
               this._postCreate((AbstractDescriptorBean)this._Log);
               if (initOne) {
                  break;
               }
            case 90:
               this._LogFilters = new LogFilterMBean[0];
               if (initOne) {
                  break;
               }
            case 54:
               this._Machines = new MachineMBean[0];
               if (initOne) {
                  break;
               }
            case 88:
               this._MailSessions = new MailSessionMBean[0];
               if (initOne) {
                  break;
               }
            case 142:
               this._ManagedExecutorServiceTemplates = new ManagedExecutorServiceTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 145:
               this._ManagedExecutorServices = new ManagedExecutorServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 143:
               this._ManagedScheduledExecutorServiceTemplates = new ManagedScheduledExecutorServiceTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 146:
               this._ManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 147:
               this._ManagedThreadFactories = new ManagedThreadFactoryMBean[0];
               if (initOne) {
                  break;
               }
            case 144:
               this._ManagedThreadFactoryTemplates = new ManagedThreadFactoryTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 139:
               this._MaxConcurrentLongRunningRequests = 50;
               if (initOne) {
                  break;
               }
            case 138:
               this._MaxConcurrentNewThreads = 50;
               if (initOne) {
                  break;
               }
            case 34:
               this._MessagingBridges = new MessagingBridgeMBean[0];
               if (initOne) {
                  break;
               }
            case 103:
               this._MigratableRMIServices = new MigratableRMIServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 71:
               this._MigratableTargets = new MigratableTargetMBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 68:
               this._NetworkChannels = new NetworkChannelMBean[0];
               if (initOne) {
                  break;
               }
            case 157:
               this._OptionalFeatureDeployment = new OptionalFeatureDeploymentMBeanImpl(this, 157);
               this._postCreate((AbstractDescriptorBean)this._OptionalFeatureDeployment);
               if (initOne) {
                  break;
               }
            case 129:
               this._OsgiFrameworks = new OsgiFrameworkMBean[0];
               if (initOne) {
                  break;
               }
            case 148:
               this._PartitionTemplates = new PartitionTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 135:
               this._PartitionUriSpace = null;
               if (initOne) {
                  break;
               }
            case 153:
               this._PartitionWorkManagers = new PartitionWorkManagerMBean[0];
               if (initOne) {
                  break;
               }
            case 134:
               this._Partitions = new PartitionMBean[0];
               if (initOne) {
                  break;
               }
            case 78:
               this._PathServices = new PathServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 92:
               this._ReplicatedStores = new ReplicatedStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 137:
               this._ResourceGroupTemplates = new ResourceGroupTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 136:
               this._ResourceGroups = new ResourceGroupMBean[0];
               if (initOne) {
                  break;
               }
            case 77:
               this._ResourceManagement = new ResourceManagementMBeanImpl(this, 77);
               this._postCreate((AbstractDescriptorBean)this._ResourceManagement);
               if (initOne) {
                  break;
               }
            case 126:
               this._RestfulManagementServices = new RestfulManagementServicesMBeanImpl(this, 126);
               this._postCreate((AbstractDescriptorBean)this._RestfulManagementServices);
               if (initOne) {
                  break;
               }
            case 22:
               this._RootDirectory = null;
               if (initOne) {
                  break;
               }
            case 102:
               this._SAFAgents = new SAFAgentMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._SNMPAgent = new SNMPAgentMBeanImpl(this, 20);
               this._postCreate((AbstractDescriptorBean)this._SNMPAgent);
               if (initOne) {
                  break;
               }
            case 21:
               this._SNMPAgentDeployments = new SNMPAgentDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 113:
               this._SNMPAttributeChanges = new SNMPAttributeChangeMBean[0];
               if (initOne) {
                  break;
               }
            case 111:
               this._SNMPCounterMonitors = new SNMPCounterMonitorMBean[0];
               if (initOne) {
                  break;
               }
            case 109:
               this._SNMPGaugeMonitors = new SNMPGaugeMonitorMBean[0];
               if (initOne) {
                  break;
               }
            case 112:
               this._SNMPLogFilters = new SNMPLogFilterMBean[0];
               if (initOne) {
                  break;
               }
            case 108:
               this._SNMPProxies = new SNMPProxyMBean[0];
               if (initOne) {
                  break;
               }
            case 110:
               this._SNMPStringMonitors = new SNMPStringMonitorMBean[0];
               if (initOne) {
                  break;
               }
            case 107:
               this._SNMPTrapDestinations = new SNMPTrapDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._SecurityConfiguration = new SecurityConfigurationMBeanImpl(this, 14);
               this._postCreate((AbstractDescriptorBean)this._SecurityConfiguration);
               if (initOne) {
                  break;
               }
            case 76:
               this._SelfTuning = new SelfTuningMBeanImpl(this, 76);
               this._postCreate((AbstractDescriptorBean)this._SelfTuning);
               if (initOne) {
                  break;
               }
            case 131:
               this._ServerMigrationHistorySize = 100;
               if (initOne) {
                  break;
               }
            case 29:
               this._ServerTemplates = new ServerTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._Servers = new ServerMBean[0];
               if (initOne) {
                  break;
               }
            case 132:
               this._ServiceMigrationHistorySize = 100;
               if (initOne) {
                  break;
               }
            case 85:
               this._ShutdownClasses = new ShutdownClassMBean[0];
               if (initOne) {
                  break;
               }
            case 87:
               this._SingletonServices = new SingletonServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 159:
               this._SiteName = null;
               if (initOne) {
                  break;
               }
            case 86:
               this._StartupClasses = new StartupClassMBean[0];
               if (initOne) {
                  break;
               }
            case 128:
               this._SystemComponentConfigurations = new SystemComponentConfigurationMBean[0];
               if (initOne) {
                  break;
               }
            case 127:
               this._SystemComponents = new SystemComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 101:
               this._SystemResources = new SystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 57:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 69:
               this._VirtualHosts = new VirtualHostMBean[0];
               if (initOne) {
                  break;
               }
            case 70:
               this._VirtualTargets = new VirtualTargetMBean[0];
               if (initOne) {
                  break;
               }
            case 99:
               this._WLDFSystemResources = new WLDFSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 53:
               this._WSReliableDeliveryPolicies = new WSReliableDeliveryPolicyMBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._WTCServers = new WTCServerMBean[0];
               if (initOne) {
                  break;
               }
            case 73:
               this._WebAppContainer = new WebAppContainerMBeanImpl(this, 73);
               this._postCreate((AbstractDescriptorBean)this._WebAppContainer);
               if (initOne) {
                  break;
               }
            case 114:
               this._WebserviceSecurities = new WebserviceSecurityMBean[0];
               if (initOne) {
                  break;
               }
            case 130:
               this._WebserviceTestpage = new WebserviceTestpageMBeanImpl(this, 130);
               this._postCreate((AbstractDescriptorBean)this._WebserviceTestpage);
               if (initOne) {
                  break;
               }
            case 55:
               this._XMLEntityCaches = new XMLEntityCacheMBean[0];
               if (initOne) {
                  break;
               }
            case 56:
               this._XMLRegistries = new XMLRegistryMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._Active = false;
               if (initOne) {
                  break;
               }
            case 44:
               this._AdministrationMBeanAuditingEnabled = false;
               if (initOne) {
                  break;
               }
            case 37:
               this._AdministrationPortEnabled = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._AutoConfigurationSaveEnabled = true;
               if (initOne) {
                  break;
               }
            case 118:
               this._AutoDeployForSubmodulesEnabled = true;
               if (initOne) {
                  break;
               }
            case 46:
               this._customizer.setClusterConstraintsEnabled(false);
               if (initOne) {
                  break;
               }
            case 42:
               this._ConfigBackupEnabled = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._ConsoleEnabled = true;
               if (initOne) {
                  break;
               }
            case 161:
               this._DBPassiveMode = false;
               if (initOne) {
                  break;
               }
            case 154:
               this._DiagnosticContextCompatibilityModeEnabled = true;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 160:
               this._EnableEECompliantClassloadingForEmbeddedAdapters = false;
               if (initOne) {
                  break;
               }
            case 39:
               this._ExalogicOptimizationsEnabled = false;
               if (initOne) {
                  break;
               }
            case 121:
               this._GuardianEnabled = false;
               if (initOne) {
                  break;
               }
            case 120:
               this._InternalAppsDeployOnDemandEnabled = true;
               if (initOne) {
                  break;
               }
            case 24:
               this._JavaServiceConsoleEnabled = false;
               if (initOne) {
                  break;
               }
            case 40:
               this._JavaServiceEnabled = false;
               if (initOne) {
                  break;
               }
            case 124:
               this._LogFormatCompatibilityEnabled = false;
               if (initOne) {
                  break;
               }
            case 123:
               this._MsgIdPrefixCompatibilityEnabled = true;
               if (initOne) {
                  break;
               }
            case 122:
               this._OCMEnabled = true;
               if (initOne) {
                  break;
               }
            case 141:
               this._ParallelDeployApplicationModules = false;
               if (initOne) {
                  break;
               }
            case 140:
               this._ParallelDeployApplications = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._customizer.setProductionModeEnabled(false);
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
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
      return "Domain";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("Active")) {
         oldVal = this._Active;
         this._Active = (Boolean)v;
         this._postSet(13, oldVal, this._Active);
      } else if (name.equals("AdminConsole")) {
         AdminConsoleMBean oldVal = this._AdminConsole;
         this._AdminConsole = (AdminConsoleMBean)v;
         this._postSet(119, oldVal, this._AdminConsole);
      } else if (name.equals("AdminServerMBean")) {
         AdminServerMBean oldVal = this._AdminServerMBean;
         this._AdminServerMBean = (AdminServerMBean)v;
         this._postSet(104, oldVal, this._AdminServerMBean);
      } else {
         String oldVal;
         if (name.equals("AdminServerName")) {
            oldVal = this._AdminServerName;
            this._AdminServerName = (String)v;
            this._postSet(97, oldVal, this._AdminServerName);
         } else if (name.equals("AdministrationMBeanAuditingEnabled")) {
            oldVal = this._AdministrationMBeanAuditingEnabled;
            this._AdministrationMBeanAuditingEnabled = (Boolean)v;
            this._postSet(44, oldVal, this._AdministrationMBeanAuditingEnabled);
         } else {
            int oldVal;
            if (name.equals("AdministrationPort")) {
               oldVal = this._AdministrationPort;
               this._AdministrationPort = (Integer)v;
               this._postSet(38, oldVal, this._AdministrationPort);
            } else if (name.equals("AdministrationPortEnabled")) {
               oldVal = this._AdministrationPortEnabled;
               this._AdministrationPortEnabled = (Boolean)v;
               this._postSet(37, oldVal, this._AdministrationPortEnabled);
            } else if (name.equals("AdministrationProtocol")) {
               oldVal = this._AdministrationProtocol;
               this._AdministrationProtocol = (String)v;
               this._postSet(98, oldVal, this._AdministrationProtocol);
            } else {
               AppDeploymentMBean[] oldVal;
               if (name.equals("AppDeployments")) {
                  oldVal = this._AppDeployments;
                  this._AppDeployments = (AppDeploymentMBean[])((AppDeploymentMBean[])v);
                  this._postSet(48, oldVal, this._AppDeployments);
               } else if (name.equals("Applications")) {
                  ApplicationMBean[] oldVal = this._Applications;
                  this._Applications = (ApplicationMBean[])((ApplicationMBean[])v);
                  this._postSet(47, oldVal, this._Applications);
               } else if (name.equals("ArchiveConfigurationCount")) {
                  oldVal = this._ArchiveConfigurationCount;
                  this._ArchiveConfigurationCount = (Integer)v;
                  this._postSet(41, oldVal, this._ArchiveConfigurationCount);
               } else if (name.equals("AutoConfigurationSaveEnabled")) {
                  oldVal = this._AutoConfigurationSaveEnabled;
                  this._AutoConfigurationSaveEnabled = (Boolean)v;
                  this._postSet(27, oldVal, this._AutoConfigurationSaveEnabled);
               } else if (name.equals("AutoDeployForSubmodulesEnabled")) {
                  oldVal = this._AutoDeployForSubmodulesEnabled;
                  this._AutoDeployForSubmodulesEnabled = (Boolean)v;
                  this._postSet(118, oldVal, this._AutoDeployForSubmodulesEnabled);
               } else if (name.equals("BatchConfig")) {
                  BatchConfigMBean oldVal = this._BatchConfig;
                  this._BatchConfig = (BatchConfigMBean)v;
                  this._postSet(151, oldVal, this._BatchConfig);
               } else if (name.equals("BatchJobsDataSourceJndiName")) {
                  oldVal = this._BatchJobsDataSourceJndiName;
                  this._BatchJobsDataSourceJndiName = (String)v;
                  this._postSet(155, oldVal, this._BatchJobsDataSourceJndiName);
               } else if (name.equals("BatchJobsExecutorServiceName")) {
                  oldVal = this._BatchJobsExecutorServiceName;
                  this._BatchJobsExecutorServiceName = (String)v;
                  this._postSet(156, oldVal, this._BatchJobsExecutorServiceName);
               } else if (name.equals("BridgeDestinations")) {
                  BridgeDestinationMBean[] oldVal = this._BridgeDestinations;
                  this._BridgeDestinations = (BridgeDestinationMBean[])((BridgeDestinationMBean[])v);
                  this._postSet(83, oldVal, this._BridgeDestinations);
               } else if (name.equals("Callouts")) {
                  CalloutMBean[] oldVal = this._Callouts;
                  this._Callouts = (CalloutMBean[])((CalloutMBean[])v);
                  this._postSet(164, oldVal, this._Callouts);
               } else if (name.equals("CdiContainer")) {
                  CdiContainerMBean oldVal = this._CdiContainer;
                  this._CdiContainer = (CdiContainerMBean)v;
                  this._postSet(74, oldVal, this._CdiContainer);
               } else if (name.equals("ClusterConstraintsEnabled")) {
                  oldVal = this._ClusterConstraintsEnabled;
                  this._ClusterConstraintsEnabled = (Boolean)v;
                  this._postSet(46, oldVal, this._ClusterConstraintsEnabled);
               } else if (name.equals("Clusters")) {
                  ClusterMBean[] oldVal = this._Clusters;
                  this._Clusters = (ClusterMBean[])((ClusterMBean[])v);
                  this._postSet(31, oldVal, this._Clusters);
               } else if (name.equals("CoherenceClusterSystemResources")) {
                  CoherenceClusterSystemResourceMBean[] oldVal = this._CoherenceClusterSystemResources;
                  this._CoherenceClusterSystemResources = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])v);
                  this._postSet(125, oldVal, this._CoherenceClusterSystemResources);
               } else if (name.equals("CoherenceManagementClusters")) {
                  CoherenceManagementClusterMBean[] oldVal = this._CoherenceManagementClusters;
                  this._CoherenceManagementClusters = (CoherenceManagementClusterMBean[])((CoherenceManagementClusterMBean[])v);
                  this._postSet(133, oldVal, this._CoherenceManagementClusters);
               } else if (name.equals("CoherenceServers")) {
                  CoherenceServerMBean[] oldVal = this._CoherenceServers;
                  this._CoherenceServers = (CoherenceServerMBean[])((CoherenceServerMBean[])v);
                  this._postSet(30, oldVal, this._CoherenceServers);
               } else if (name.equals("ConfigBackupEnabled")) {
                  oldVal = this._ConfigBackupEnabled;
                  this._ConfigBackupEnabled = (Boolean)v;
                  this._postSet(42, oldVal, this._ConfigBackupEnabled);
               } else if (name.equals("ConfigurationAuditType")) {
                  oldVal = this._ConfigurationAuditType;
                  this._ConfigurationAuditType = (String)v;
                  this._postSet(45, oldVal, this._ConfigurationAuditType);
               } else if (name.equals("ConfigurationVersion")) {
                  oldVal = this._ConfigurationVersion;
                  this._ConfigurationVersion = (String)v;
                  this._postSet(43, oldVal, this._ConfigurationVersion);
               } else if (name.equals("ConsoleContextPath")) {
                  oldVal = this._ConsoleContextPath;
                  this._ConsoleContextPath = (String)v;
                  this._postSet(25, oldVal, this._ConsoleContextPath);
               } else if (name.equals("ConsoleEnabled")) {
                  oldVal = this._ConsoleEnabled;
                  this._ConsoleEnabled = (Boolean)v;
                  this._postSet(23, oldVal, this._ConsoleEnabled);
               } else if (name.equals("ConsoleExtensionDirectory")) {
                  oldVal = this._ConsoleExtensionDirectory;
                  this._ConsoleExtensionDirectory = (String)v;
                  this._postSet(26, oldVal, this._ConsoleExtensionDirectory);
               } else if (name.equals("CustomResources")) {
                  CustomResourceMBean[] oldVal = this._CustomResources;
                  this._CustomResources = (CustomResourceMBean[])((CustomResourceMBean[])v);
                  this._postSet(95, oldVal, this._CustomResources);
               } else if (name.equals("DBPassiveMode")) {
                  oldVal = this._DBPassiveMode;
                  this._DBPassiveMode = (Boolean)v;
                  this._postSet(161, oldVal, this._DBPassiveMode);
               } else if (name.equals("DBPassiveModeGracePeriodSeconds")) {
                  oldVal = this._DBPassiveModeGracePeriodSeconds;
                  this._DBPassiveModeGracePeriodSeconds = (Integer)v;
                  this._postSet(162, oldVal, this._DBPassiveModeGracePeriodSeconds);
               } else if (name.equals("DebugPatches")) {
                  DebugPatchesMBean oldVal = this._DebugPatches;
                  this._DebugPatches = (DebugPatchesMBean)v;
                  this._postSet(152, oldVal, this._DebugPatches);
               } else if (name.equals("DeploymentConfiguration")) {
                  DeploymentConfigurationMBean oldVal = this._DeploymentConfiguration;
                  this._DeploymentConfiguration = (DeploymentConfigurationMBean)v;
                  this._postSet(17, oldVal, this._DeploymentConfiguration);
               } else if (name.equals("Deployments")) {
                  DeploymentMBean[] oldVal = this._Deployments;
                  this._Deployments = (DeploymentMBean[])((DeploymentMBean[])v);
                  this._postSet(32, oldVal, this._Deployments);
               } else if (name.equals("DiagnosticContextCompatibilityModeEnabled")) {
                  oldVal = this._DiagnosticContextCompatibilityModeEnabled;
                  this._DiagnosticContextCompatibilityModeEnabled = (Boolean)v;
                  this._postSet(154, oldVal, this._DiagnosticContextCompatibilityModeEnabled);
               } else if (name.equals("DomainLibraries")) {
                  DomainLibraryMBean[] oldVal = this._DomainLibraries;
                  this._DomainLibraries = (DomainLibraryMBean[])((DomainLibraryMBean[])v);
                  this._postSet(51, oldVal, this._DomainLibraries);
               } else if (name.equals("DomainVersion")) {
                  oldVal = this._DomainVersion;
                  this._DomainVersion = (String)v;
                  this._postSet(11, oldVal, this._DomainVersion);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("EJBContainer")) {
                  EJBContainerMBean oldVal = this._EJBContainer;
                  this._EJBContainer = (EJBContainerMBean)v;
                  this._postSet(72, oldVal, this._EJBContainer);
               } else if (name.equals("EmbeddedLDAP")) {
                  EmbeddedLDAPMBean oldVal = this._EmbeddedLDAP;
                  this._EmbeddedLDAP = (EmbeddedLDAPMBean)v;
                  this._postSet(36, oldVal, this._EmbeddedLDAP);
               } else if (name.equals("EnableEECompliantClassloadingForEmbeddedAdapters")) {
                  oldVal = this._EnableEECompliantClassloadingForEmbeddedAdapters;
                  this._EnableEECompliantClassloadingForEmbeddedAdapters = (Boolean)v;
                  this._postSet(160, oldVal, this._EnableEECompliantClassloadingForEmbeddedAdapters);
               } else if (name.equals("ExalogicOptimizationsEnabled")) {
                  oldVal = this._ExalogicOptimizationsEnabled;
                  this._ExalogicOptimizationsEnabled = (Boolean)v;
                  this._postSet(39, oldVal, this._ExalogicOptimizationsEnabled);
               } else if (name.equals("FileStores")) {
                  FileStoreMBean[] oldVal = this._FileStores;
                  this._FileStores = (FileStoreMBean[])((FileStoreMBean[])v);
                  this._postSet(91, oldVal, this._FileStores);
               } else if (name.equals("FileT3s")) {
                  FileT3MBean[] oldVal = this._FileT3s;
                  this._FileT3s = (FileT3MBean[])((FileT3MBean[])v);
                  this._postSet(33, oldVal, this._FileT3s);
               } else if (name.equals("ForeignJMSConnectionFactories")) {
                  ForeignJMSConnectionFactoryMBean[] oldVal = this._ForeignJMSConnectionFactories;
                  this._ForeignJMSConnectionFactories = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])v);
                  this._postSet(115, oldVal, this._ForeignJMSConnectionFactories);
               } else if (name.equals("ForeignJMSDestinations")) {
                  ForeignJMSDestinationMBean[] oldVal = this._ForeignJMSDestinations;
                  this._ForeignJMSDestinations = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])v);
                  this._postSet(116, oldVal, this._ForeignJMSDestinations);
               } else if (name.equals("ForeignJMSServers")) {
                  ForeignJMSServerMBean[] oldVal = this._ForeignJMSServers;
                  this._ForeignJMSServers = (ForeignJMSServerMBean[])((ForeignJMSServerMBean[])v);
                  this._postSet(84, oldVal, this._ForeignJMSServers);
               } else if (name.equals("ForeignJNDIProviders")) {
                  ForeignJNDIProviderMBean[] oldVal = this._ForeignJNDIProviders;
                  this._ForeignJNDIProviders = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])v);
                  this._postSet(96, oldVal, this._ForeignJNDIProviders);
               } else if (name.equals("GuardianEnabled")) {
                  oldVal = this._GuardianEnabled;
                  this._GuardianEnabled = (Boolean)v;
                  this._postSet(121, oldVal, this._GuardianEnabled);
               } else if (name.equals("InstalledSoftwareVersion")) {
                  oldVal = this._InstalledSoftwareVersion;
                  this._InstalledSoftwareVersion = (String)v;
                  this._postSet(163, oldVal, this._InstalledSoftwareVersion);
               } else if (name.equals("Interceptors")) {
                  InterceptorsMBean oldVal = this._Interceptors;
                  this._Interceptors = (InterceptorsMBean)v;
                  this._postSet(150, oldVal, this._Interceptors);
               } else if (name.equals("InternalAppDeployments")) {
                  oldVal = this._InternalAppDeployments;
                  this._InternalAppDeployments = (AppDeploymentMBean[])((AppDeploymentMBean[])v);
                  this._postSet(49, oldVal, this._InternalAppDeployments);
               } else if (name.equals("InternalAppsDeployOnDemandEnabled")) {
                  oldVal = this._InternalAppsDeployOnDemandEnabled;
                  this._InternalAppsDeployOnDemandEnabled = (Boolean)v;
                  this._postSet(120, oldVal, this._InternalAppsDeployOnDemandEnabled);
               } else {
                  LibraryMBean[] oldVal;
                  if (name.equals("InternalLibraries")) {
                     oldVal = this._InternalLibraries;
                     this._InternalLibraries = (LibraryMBean[])((LibraryMBean[])v);
                     this._postSet(52, oldVal, this._InternalLibraries);
                  } else if (name.equals("JDBCStores")) {
                     JDBCStoreMBean[] oldVal = this._JDBCStores;
                     this._JDBCStores = (JDBCStoreMBean[])((JDBCStoreMBean[])v);
                     this._postSet(93, oldVal, this._JDBCStores);
                  } else if (name.equals("JDBCSystemResources")) {
                     JDBCSystemResourceMBean[] oldVal = this._JDBCSystemResources;
                     this._JDBCSystemResources = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])v);
                     this._postSet(100, oldVal, this._JDBCSystemResources);
                  } else if (name.equals("JMSBridgeDestinations")) {
                     JMSBridgeDestinationMBean[] oldVal = this._JMSBridgeDestinations;
                     this._JMSBridgeDestinations = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])v);
                     this._postSet(82, oldVal, this._JMSBridgeDestinations);
                  } else if (name.equals("JMSConnectionConsumers")) {
                     JMSConnectionConsumerMBean[] oldVal = this._JMSConnectionConsumers;
                     this._JMSConnectionConsumers = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])v);
                     this._postSet(117, oldVal, this._JMSConnectionConsumers);
                  } else if (name.equals("JMSConnectionFactories")) {
                     JMSConnectionFactoryMBean[] oldVal = this._JMSConnectionFactories;
                     this._JMSConnectionFactories = (JMSConnectionFactoryMBean[])((JMSConnectionFactoryMBean[])v);
                     this._postSet(80, oldVal, this._JMSConnectionFactories);
                  } else if (name.equals("JMSDestinationKeys")) {
                     JMSDestinationKeyMBean[] oldVal = this._JMSDestinationKeys;
                     this._JMSDestinationKeys = (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])v);
                     this._postSet(79, oldVal, this._JMSDestinationKeys);
                  } else if (name.equals("JMSDestinations")) {
                     JMSDestinationMBean[] oldVal = this._JMSDestinations;
                     this._JMSDestinations = (JMSDestinationMBean[])((JMSDestinationMBean[])v);
                     this._postSet(62, oldVal, this._JMSDestinations);
                  } else if (name.equals("JMSDistributedQueueMembers")) {
                     JMSDistributedQueueMemberMBean[] oldVal = this._JMSDistributedQueueMembers;
                     this._JMSDistributedQueueMembers = (JMSDistributedQueueMemberMBean[])((JMSDistributedQueueMemberMBean[])v);
                     this._postSet(105, oldVal, this._JMSDistributedQueueMembers);
                  } else if (name.equals("JMSDistributedQueues")) {
                     JMSDistributedQueueMBean[] oldVal = this._JMSDistributedQueues;
                     this._JMSDistributedQueues = (JMSDistributedQueueMBean[])((JMSDistributedQueueMBean[])v);
                     this._postSet(65, oldVal, this._JMSDistributedQueues);
                  } else if (name.equals("JMSDistributedTopicMembers")) {
                     JMSDistributedTopicMemberMBean[] oldVal = this._JMSDistributedTopicMembers;
                     this._JMSDistributedTopicMembers = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])v);
                     this._postSet(106, oldVal, this._JMSDistributedTopicMembers);
                  } else if (name.equals("JMSDistributedTopics")) {
                     JMSDistributedTopicMBean[] oldVal = this._JMSDistributedTopics;
                     this._JMSDistributedTopics = (JMSDistributedTopicMBean[])((JMSDistributedTopicMBean[])v);
                     this._postSet(66, oldVal, this._JMSDistributedTopics);
                  } else if (name.equals("JMSFileStores")) {
                     JMSFileStoreMBean[] oldVal = this._JMSFileStores;
                     this._JMSFileStores = (JMSFileStoreMBean[])((JMSFileStoreMBean[])v);
                     this._postSet(61, oldVal, this._JMSFileStores);
                  } else if (name.equals("JMSJDBCStores")) {
                     JMSJDBCStoreMBean[] oldVal = this._JMSJDBCStores;
                     this._JMSJDBCStores = (JMSJDBCStoreMBean[])((JMSJDBCStoreMBean[])v);
                     this._postSet(60, oldVal, this._JMSJDBCStores);
                  } else if (name.equals("JMSQueues")) {
                     JMSQueueMBean[] oldVal = this._JMSQueues;
                     this._JMSQueues = (JMSQueueMBean[])((JMSQueueMBean[])v);
                     this._postSet(63, oldVal, this._JMSQueues);
                  } else if (name.equals("JMSServers")) {
                     JMSServerMBean[] oldVal = this._JMSServers;
                     this._JMSServers = (JMSServerMBean[])((JMSServerMBean[])v);
                     this._postSet(58, oldVal, this._JMSServers);
                  } else if (name.equals("JMSSessionPools")) {
                     JMSSessionPoolMBean[] oldVal = this._JMSSessionPools;
                     this._JMSSessionPools = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])v);
                     this._postSet(81, oldVal, this._JMSSessionPools);
                  } else if (name.equals("JMSStores")) {
                     JMSStoreMBean[] oldVal = this._JMSStores;
                     this._JMSStores = (JMSStoreMBean[])((JMSStoreMBean[])v);
                     this._postSet(59, oldVal, this._JMSStores);
                  } else if (name.equals("JMSSystemResources")) {
                     JMSSystemResourceMBean[] oldVal = this._JMSSystemResources;
                     this._JMSSystemResources = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])v);
                     this._postSet(94, oldVal, this._JMSSystemResources);
                  } else if (name.equals("JMSTemplates")) {
                     JMSTemplateMBean[] oldVal = this._JMSTemplates;
                     this._JMSTemplates = (JMSTemplateMBean[])((JMSTemplateMBean[])v);
                     this._postSet(67, oldVal, this._JMSTemplates);
                  } else if (name.equals("JMSTopics")) {
                     JMSTopicMBean[] oldVal = this._JMSTopics;
                     this._JMSTopics = (JMSTopicMBean[])((JMSTopicMBean[])v);
                     this._postSet(64, oldVal, this._JMSTopics);
                  } else if (name.equals("JMX")) {
                     JMXMBean oldVal = this._JMX;
                     this._JMX = (JMXMBean)v;
                     this._postSet(75, oldVal, this._JMX);
                  } else if (name.equals("JPA")) {
                     JPAMBean oldVal = this._JPA;
                     this._JPA = (JPAMBean)v;
                     this._postSet(16, oldVal, this._JPA);
                  } else if (name.equals("JTA")) {
                     JTAMBean oldVal = this._JTA;
                     this._JTA = (JTAMBean)v;
                     this._postSet(15, oldVal, this._JTA);
                  } else if (name.equals("JavaServiceConsoleEnabled")) {
                     oldVal = this._JavaServiceConsoleEnabled;
                     this._JavaServiceConsoleEnabled = (Boolean)v;
                     this._postSet(24, oldVal, this._JavaServiceConsoleEnabled);
                  } else if (name.equals("JavaServiceEnabled")) {
                     oldVal = this._JavaServiceEnabled;
                     this._JavaServiceEnabled = (Boolean)v;
                     this._postSet(40, oldVal, this._JavaServiceEnabled);
                  } else if (name.equals("JoltConnectionPools")) {
                     JoltConnectionPoolMBean[] oldVal = this._JoltConnectionPools;
                     this._JoltConnectionPools = (JoltConnectionPoolMBean[])((JoltConnectionPoolMBean[])v);
                     this._postSet(89, oldVal, this._JoltConnectionPools);
                  } else if (name.equals("LastModificationTime")) {
                     long oldVal = this._LastModificationTime;
                     this._LastModificationTime = (Long)v;
                     this._postSet(12, oldVal, this._LastModificationTime);
                  } else if (name.equals("Libraries")) {
                     oldVal = this._Libraries;
                     this._Libraries = (LibraryMBean[])((LibraryMBean[])v);
                     this._postSet(50, oldVal, this._Libraries);
                  } else if (name.equals("LifecycleManagerConfig")) {
                     LifecycleManagerConfigMBean oldVal = this._LifecycleManagerConfig;
                     this._LifecycleManagerConfig = (LifecycleManagerConfigMBean)v;
                     this._postSet(158, oldVal, this._LifecycleManagerConfig);
                  } else if (name.equals("LifecycleManagerEndPoints")) {
                     LifecycleManagerEndPointMBean[] oldVal = this._LifecycleManagerEndPoints;
                     this._LifecycleManagerEndPoints = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])v);
                     this._postSet(149, oldVal, this._LifecycleManagerEndPoints);
                  } else if (name.equals("Log")) {
                     LogMBean oldVal = this._Log;
                     this._Log = (LogMBean)v;
                     this._postSet(19, oldVal, this._Log);
                  } else if (name.equals("LogFilters")) {
                     LogFilterMBean[] oldVal = this._LogFilters;
                     this._LogFilters = (LogFilterMBean[])((LogFilterMBean[])v);
                     this._postSet(90, oldVal, this._LogFilters);
                  } else if (name.equals("LogFormatCompatibilityEnabled")) {
                     oldVal = this._LogFormatCompatibilityEnabled;
                     this._LogFormatCompatibilityEnabled = (Boolean)v;
                     this._postSet(124, oldVal, this._LogFormatCompatibilityEnabled);
                  } else if (name.equals("Machines")) {
                     MachineMBean[] oldVal = this._Machines;
                     this._Machines = (MachineMBean[])((MachineMBean[])v);
                     this._postSet(54, oldVal, this._Machines);
                  } else if (name.equals("MailSessions")) {
                     MailSessionMBean[] oldVal = this._MailSessions;
                     this._MailSessions = (MailSessionMBean[])((MailSessionMBean[])v);
                     this._postSet(88, oldVal, this._MailSessions);
                  } else if (name.equals("ManagedExecutorServiceTemplates")) {
                     ManagedExecutorServiceTemplateMBean[] oldVal = this._ManagedExecutorServiceTemplates;
                     this._ManagedExecutorServiceTemplates = (ManagedExecutorServiceTemplateMBean[])((ManagedExecutorServiceTemplateMBean[])v);
                     this._postSet(142, oldVal, this._ManagedExecutorServiceTemplates);
                  } else if (name.equals("ManagedExecutorServices")) {
                     ManagedExecutorServiceMBean[] oldVal = this._ManagedExecutorServices;
                     this._ManagedExecutorServices = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])v);
                     this._postSet(145, oldVal, this._ManagedExecutorServices);
                  } else if (name.equals("ManagedScheduledExecutorServiceTemplates")) {
                     ManagedScheduledExecutorServiceTemplateMBean[] oldVal = this._ManagedScheduledExecutorServiceTemplates;
                     this._ManagedScheduledExecutorServiceTemplates = (ManagedScheduledExecutorServiceTemplateMBean[])((ManagedScheduledExecutorServiceTemplateMBean[])v);
                     this._postSet(143, oldVal, this._ManagedScheduledExecutorServiceTemplates);
                  } else if (name.equals("ManagedScheduledExecutorServices")) {
                     ManagedScheduledExecutorServiceMBean[] oldVal = this._ManagedScheduledExecutorServices;
                     this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])v);
                     this._postSet(146, oldVal, this._ManagedScheduledExecutorServices);
                  } else if (name.equals("ManagedThreadFactories")) {
                     ManagedThreadFactoryMBean[] oldVal = this._ManagedThreadFactories;
                     this._ManagedThreadFactories = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])v);
                     this._postSet(147, oldVal, this._ManagedThreadFactories);
                  } else if (name.equals("ManagedThreadFactoryTemplates")) {
                     ManagedThreadFactoryTemplateMBean[] oldVal = this._ManagedThreadFactoryTemplates;
                     this._ManagedThreadFactoryTemplates = (ManagedThreadFactoryTemplateMBean[])((ManagedThreadFactoryTemplateMBean[])v);
                     this._postSet(144, oldVal, this._ManagedThreadFactoryTemplates);
                  } else if (name.equals("MaxConcurrentLongRunningRequests")) {
                     oldVal = this._MaxConcurrentLongRunningRequests;
                     this._MaxConcurrentLongRunningRequests = (Integer)v;
                     this._postSet(139, oldVal, this._MaxConcurrentLongRunningRequests);
                  } else if (name.equals("MaxConcurrentNewThreads")) {
                     oldVal = this._MaxConcurrentNewThreads;
                     this._MaxConcurrentNewThreads = (Integer)v;
                     this._postSet(138, oldVal, this._MaxConcurrentNewThreads);
                  } else if (name.equals("MessagingBridges")) {
                     MessagingBridgeMBean[] oldVal = this._MessagingBridges;
                     this._MessagingBridges = (MessagingBridgeMBean[])((MessagingBridgeMBean[])v);
                     this._postSet(34, oldVal, this._MessagingBridges);
                  } else if (name.equals("MigratableRMIServices")) {
                     MigratableRMIServiceMBean[] oldVal = this._MigratableRMIServices;
                     this._MigratableRMIServices = (MigratableRMIServiceMBean[])((MigratableRMIServiceMBean[])v);
                     this._postSet(103, oldVal, this._MigratableRMIServices);
                  } else if (name.equals("MigratableTargets")) {
                     MigratableTargetMBean[] oldVal = this._MigratableTargets;
                     this._MigratableTargets = (MigratableTargetMBean[])((MigratableTargetMBean[])v);
                     this._postSet(71, oldVal, this._MigratableTargets);
                  } else if (name.equals("MsgIdPrefixCompatibilityEnabled")) {
                     oldVal = this._MsgIdPrefixCompatibilityEnabled;
                     this._MsgIdPrefixCompatibilityEnabled = (Boolean)v;
                     this._postSet(123, oldVal, this._MsgIdPrefixCompatibilityEnabled);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("NetworkChannels")) {
                     NetworkChannelMBean[] oldVal = this._NetworkChannels;
                     this._NetworkChannels = (NetworkChannelMBean[])((NetworkChannelMBean[])v);
                     this._postSet(68, oldVal, this._NetworkChannels);
                  } else if (name.equals("OCMEnabled")) {
                     oldVal = this._OCMEnabled;
                     this._OCMEnabled = (Boolean)v;
                     this._postSet(122, oldVal, this._OCMEnabled);
                  } else if (name.equals("OptionalFeatureDeployment")) {
                     OptionalFeatureDeploymentMBean oldVal = this._OptionalFeatureDeployment;
                     this._OptionalFeatureDeployment = (OptionalFeatureDeploymentMBean)v;
                     this._postSet(157, oldVal, this._OptionalFeatureDeployment);
                  } else if (name.equals("OsgiFrameworks")) {
                     OsgiFrameworkMBean[] oldVal = this._OsgiFrameworks;
                     this._OsgiFrameworks = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])v);
                     this._postSet(129, oldVal, this._OsgiFrameworks);
                  } else if (name.equals("ParallelDeployApplicationModules")) {
                     oldVal = this._ParallelDeployApplicationModules;
                     this._ParallelDeployApplicationModules = (Boolean)v;
                     this._postSet(141, oldVal, this._ParallelDeployApplicationModules);
                  } else if (name.equals("ParallelDeployApplications")) {
                     oldVal = this._ParallelDeployApplications;
                     this._ParallelDeployApplications = (Boolean)v;
                     this._postSet(140, oldVal, this._ParallelDeployApplications);
                  } else if (name.equals("PartitionTemplates")) {
                     PartitionTemplateMBean[] oldVal = this._PartitionTemplates;
                     this._PartitionTemplates = (PartitionTemplateMBean[])((PartitionTemplateMBean[])v);
                     this._postSet(148, oldVal, this._PartitionTemplates);
                  } else if (name.equals("PartitionUriSpace")) {
                     oldVal = this._PartitionUriSpace;
                     this._PartitionUriSpace = (String)v;
                     this._postSet(135, oldVal, this._PartitionUriSpace);
                  } else if (name.equals("PartitionWorkManagers")) {
                     PartitionWorkManagerMBean[] oldVal = this._PartitionWorkManagers;
                     this._PartitionWorkManagers = (PartitionWorkManagerMBean[])((PartitionWorkManagerMBean[])v);
                     this._postSet(153, oldVal, this._PartitionWorkManagers);
                  } else if (name.equals("Partitions")) {
                     PartitionMBean[] oldVal = this._Partitions;
                     this._Partitions = (PartitionMBean[])((PartitionMBean[])v);
                     this._postSet(134, oldVal, this._Partitions);
                  } else if (name.equals("PathServices")) {
                     PathServiceMBean[] oldVal = this._PathServices;
                     this._PathServices = (PathServiceMBean[])((PathServiceMBean[])v);
                     this._postSet(78, oldVal, this._PathServices);
                  } else if (name.equals("ProductionModeEnabled")) {
                     oldVal = this._ProductionModeEnabled;
                     this._ProductionModeEnabled = (Boolean)v;
                     this._postSet(35, oldVal, this._ProductionModeEnabled);
                  } else if (name.equals("ReplicatedStores")) {
                     ReplicatedStoreMBean[] oldVal = this._ReplicatedStores;
                     this._ReplicatedStores = (ReplicatedStoreMBean[])((ReplicatedStoreMBean[])v);
                     this._postSet(92, oldVal, this._ReplicatedStores);
                  } else if (name.equals("ResourceGroupTemplates")) {
                     ResourceGroupTemplateMBean[] oldVal = this._ResourceGroupTemplates;
                     this._ResourceGroupTemplates = (ResourceGroupTemplateMBean[])((ResourceGroupTemplateMBean[])v);
                     this._postSet(137, oldVal, this._ResourceGroupTemplates);
                  } else if (name.equals("ResourceGroups")) {
                     ResourceGroupMBean[] oldVal = this._ResourceGroups;
                     this._ResourceGroups = (ResourceGroupMBean[])((ResourceGroupMBean[])v);
                     this._postSet(136, oldVal, this._ResourceGroups);
                  } else if (name.equals("ResourceManagement")) {
                     ResourceManagementMBean oldVal = this._ResourceManagement;
                     this._ResourceManagement = (ResourceManagementMBean)v;
                     this._postSet(77, oldVal, this._ResourceManagement);
                  } else if (name.equals("RestfulManagementServices")) {
                     RestfulManagementServicesMBean oldVal = this._RestfulManagementServices;
                     this._RestfulManagementServices = (RestfulManagementServicesMBean)v;
                     this._postSet(126, oldVal, this._RestfulManagementServices);
                  } else if (name.equals("RootDirectory")) {
                     oldVal = this._RootDirectory;
                     this._RootDirectory = (String)v;
                     this._postSet(22, oldVal, this._RootDirectory);
                  } else if (name.equals("SAFAgents")) {
                     SAFAgentMBean[] oldVal = this._SAFAgents;
                     this._SAFAgents = (SAFAgentMBean[])((SAFAgentMBean[])v);
                     this._postSet(102, oldVal, this._SAFAgents);
                  } else if (name.equals("SNMPAgent")) {
                     SNMPAgentMBean oldVal = this._SNMPAgent;
                     this._SNMPAgent = (SNMPAgentMBean)v;
                     this._postSet(20, oldVal, this._SNMPAgent);
                  } else if (name.equals("SNMPAgentDeployments")) {
                     SNMPAgentDeploymentMBean[] oldVal = this._SNMPAgentDeployments;
                     this._SNMPAgentDeployments = (SNMPAgentDeploymentMBean[])((SNMPAgentDeploymentMBean[])v);
                     this._postSet(21, oldVal, this._SNMPAgentDeployments);
                  } else if (name.equals("SNMPAttributeChanges")) {
                     SNMPAttributeChangeMBean[] oldVal = this._SNMPAttributeChanges;
                     this._SNMPAttributeChanges = (SNMPAttributeChangeMBean[])((SNMPAttributeChangeMBean[])v);
                     this._postSet(113, oldVal, this._SNMPAttributeChanges);
                  } else if (name.equals("SNMPCounterMonitors")) {
                     SNMPCounterMonitorMBean[] oldVal = this._SNMPCounterMonitors;
                     this._SNMPCounterMonitors = (SNMPCounterMonitorMBean[])((SNMPCounterMonitorMBean[])v);
                     this._postSet(111, oldVal, this._SNMPCounterMonitors);
                  } else if (name.equals("SNMPGaugeMonitors")) {
                     SNMPGaugeMonitorMBean[] oldVal = this._SNMPGaugeMonitors;
                     this._SNMPGaugeMonitors = (SNMPGaugeMonitorMBean[])((SNMPGaugeMonitorMBean[])v);
                     this._postSet(109, oldVal, this._SNMPGaugeMonitors);
                  } else if (name.equals("SNMPLogFilters")) {
                     SNMPLogFilterMBean[] oldVal = this._SNMPLogFilters;
                     this._SNMPLogFilters = (SNMPLogFilterMBean[])((SNMPLogFilterMBean[])v);
                     this._postSet(112, oldVal, this._SNMPLogFilters);
                  } else if (name.equals("SNMPProxies")) {
                     SNMPProxyMBean[] oldVal = this._SNMPProxies;
                     this._SNMPProxies = (SNMPProxyMBean[])((SNMPProxyMBean[])v);
                     this._postSet(108, oldVal, this._SNMPProxies);
                  } else if (name.equals("SNMPStringMonitors")) {
                     SNMPStringMonitorMBean[] oldVal = this._SNMPStringMonitors;
                     this._SNMPStringMonitors = (SNMPStringMonitorMBean[])((SNMPStringMonitorMBean[])v);
                     this._postSet(110, oldVal, this._SNMPStringMonitors);
                  } else if (name.equals("SNMPTrapDestinations")) {
                     SNMPTrapDestinationMBean[] oldVal = this._SNMPTrapDestinations;
                     this._SNMPTrapDestinations = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])v);
                     this._postSet(107, oldVal, this._SNMPTrapDestinations);
                  } else if (name.equals("SecurityConfiguration")) {
                     SecurityConfigurationMBean oldVal = this._SecurityConfiguration;
                     this._SecurityConfiguration = (SecurityConfigurationMBean)v;
                     this._postSet(14, oldVal, this._SecurityConfiguration);
                  } else if (name.equals("SelfTuning")) {
                     SelfTuningMBean oldVal = this._SelfTuning;
                     this._SelfTuning = (SelfTuningMBean)v;
                     this._postSet(76, oldVal, this._SelfTuning);
                  } else if (name.equals("ServerMigrationHistorySize")) {
                     oldVal = this._ServerMigrationHistorySize;
                     this._ServerMigrationHistorySize = (Integer)v;
                     this._postSet(131, oldVal, this._ServerMigrationHistorySize);
                  } else if (name.equals("ServerTemplates")) {
                     ServerTemplateMBean[] oldVal = this._ServerTemplates;
                     this._ServerTemplates = (ServerTemplateMBean[])((ServerTemplateMBean[])v);
                     this._postSet(29, oldVal, this._ServerTemplates);
                  } else if (name.equals("Servers")) {
                     ServerMBean[] oldVal = this._Servers;
                     this._Servers = (ServerMBean[])((ServerMBean[])v);
                     this._postSet(28, oldVal, this._Servers);
                  } else if (name.equals("ServiceMigrationHistorySize")) {
                     oldVal = this._ServiceMigrationHistorySize;
                     this._ServiceMigrationHistorySize = (Integer)v;
                     this._postSet(132, oldVal, this._ServiceMigrationHistorySize);
                  } else if (name.equals("ShutdownClasses")) {
                     ShutdownClassMBean[] oldVal = this._ShutdownClasses;
                     this._ShutdownClasses = (ShutdownClassMBean[])((ShutdownClassMBean[])v);
                     this._postSet(85, oldVal, this._ShutdownClasses);
                  } else if (name.equals("SingletonServices")) {
                     SingletonServiceMBean[] oldVal = this._SingletonServices;
                     this._SingletonServices = (SingletonServiceMBean[])((SingletonServiceMBean[])v);
                     this._postSet(87, oldVal, this._SingletonServices);
                  } else if (name.equals("SiteName")) {
                     oldVal = this._SiteName;
                     this._SiteName = (String)v;
                     this._postSet(159, oldVal, this._SiteName);
                  } else if (name.equals("StartupClasses")) {
                     StartupClassMBean[] oldVal = this._StartupClasses;
                     this._StartupClasses = (StartupClassMBean[])((StartupClassMBean[])v);
                     this._postSet(86, oldVal, this._StartupClasses);
                  } else if (name.equals("SystemComponentConfigurations")) {
                     SystemComponentConfigurationMBean[] oldVal = this._SystemComponentConfigurations;
                     this._SystemComponentConfigurations = (SystemComponentConfigurationMBean[])((SystemComponentConfigurationMBean[])v);
                     this._postSet(128, oldVal, this._SystemComponentConfigurations);
                  } else if (name.equals("SystemComponents")) {
                     SystemComponentMBean[] oldVal = this._SystemComponents;
                     this._SystemComponents = (SystemComponentMBean[])((SystemComponentMBean[])v);
                     this._postSet(127, oldVal, this._SystemComponents);
                  } else if (name.equals("SystemResources")) {
                     SystemResourceMBean[] oldVal = this._SystemResources;
                     this._SystemResources = (SystemResourceMBean[])((SystemResourceMBean[])v);
                     this._postSet(101, oldVal, this._SystemResources);
                  } else if (name.equals("Tags")) {
                     String[] oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("Targets")) {
                     TargetMBean[] oldVal = this._Targets;
                     this._Targets = (TargetMBean[])((TargetMBean[])v);
                     this._postSet(57, oldVal, this._Targets);
                  } else if (name.equals("VirtualHosts")) {
                     VirtualHostMBean[] oldVal = this._VirtualHosts;
                     this._VirtualHosts = (VirtualHostMBean[])((VirtualHostMBean[])v);
                     this._postSet(69, oldVal, this._VirtualHosts);
                  } else if (name.equals("VirtualTargets")) {
                     VirtualTargetMBean[] oldVal = this._VirtualTargets;
                     this._VirtualTargets = (VirtualTargetMBean[])((VirtualTargetMBean[])v);
                     this._postSet(70, oldVal, this._VirtualTargets);
                  } else if (name.equals("WLDFSystemResources")) {
                     WLDFSystemResourceMBean[] oldVal = this._WLDFSystemResources;
                     this._WLDFSystemResources = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])v);
                     this._postSet(99, oldVal, this._WLDFSystemResources);
                  } else if (name.equals("WSReliableDeliveryPolicies")) {
                     WSReliableDeliveryPolicyMBean[] oldVal = this._WSReliableDeliveryPolicies;
                     this._WSReliableDeliveryPolicies = (WSReliableDeliveryPolicyMBean[])((WSReliableDeliveryPolicyMBean[])v);
                     this._postSet(53, oldVal, this._WSReliableDeliveryPolicies);
                  } else if (name.equals("WTCServers")) {
                     WTCServerMBean[] oldVal = this._WTCServers;
                     this._WTCServers = (WTCServerMBean[])((WTCServerMBean[])v);
                     this._postSet(18, oldVal, this._WTCServers);
                  } else if (name.equals("WebAppContainer")) {
                     WebAppContainerMBean oldVal = this._WebAppContainer;
                     this._WebAppContainer = (WebAppContainerMBean)v;
                     this._postSet(73, oldVal, this._WebAppContainer);
                  } else if (name.equals("WebserviceSecurities")) {
                     WebserviceSecurityMBean[] oldVal = this._WebserviceSecurities;
                     this._WebserviceSecurities = (WebserviceSecurityMBean[])((WebserviceSecurityMBean[])v);
                     this._postSet(114, oldVal, this._WebserviceSecurities);
                  } else if (name.equals("WebserviceTestpage")) {
                     WebserviceTestpageMBean oldVal = this._WebserviceTestpage;
                     this._WebserviceTestpage = (WebserviceTestpageMBean)v;
                     this._postSet(130, oldVal, this._WebserviceTestpage);
                  } else if (name.equals("XMLEntityCaches")) {
                     XMLEntityCacheMBean[] oldVal = this._XMLEntityCaches;
                     this._XMLEntityCaches = (XMLEntityCacheMBean[])((XMLEntityCacheMBean[])v);
                     this._postSet(55, oldVal, this._XMLEntityCaches);
                  } else if (name.equals("XMLRegistries")) {
                     XMLRegistryMBean[] oldVal = this._XMLRegistries;
                     this._XMLRegistries = (XMLRegistryMBean[])((XMLRegistryMBean[])v);
                     this._postSet(56, oldVal, this._XMLRegistries);
                  } else if (name.equals("customizer")) {
                     Domain oldVal = this._customizer;
                     this._customizer = (Domain)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Active")) {
         return new Boolean(this._Active);
      } else if (name.equals("AdminConsole")) {
         return this._AdminConsole;
      } else if (name.equals("AdminServerMBean")) {
         return this._AdminServerMBean;
      } else if (name.equals("AdminServerName")) {
         return this._AdminServerName;
      } else if (name.equals("AdministrationMBeanAuditingEnabled")) {
         return new Boolean(this._AdministrationMBeanAuditingEnabled);
      } else if (name.equals("AdministrationPort")) {
         return new Integer(this._AdministrationPort);
      } else if (name.equals("AdministrationPortEnabled")) {
         return new Boolean(this._AdministrationPortEnabled);
      } else if (name.equals("AdministrationProtocol")) {
         return this._AdministrationProtocol;
      } else if (name.equals("AppDeployments")) {
         return this._AppDeployments;
      } else if (name.equals("Applications")) {
         return this._Applications;
      } else if (name.equals("ArchiveConfigurationCount")) {
         return new Integer(this._ArchiveConfigurationCount);
      } else if (name.equals("AutoConfigurationSaveEnabled")) {
         return new Boolean(this._AutoConfigurationSaveEnabled);
      } else if (name.equals("AutoDeployForSubmodulesEnabled")) {
         return new Boolean(this._AutoDeployForSubmodulesEnabled);
      } else if (name.equals("BatchConfig")) {
         return this._BatchConfig;
      } else if (name.equals("BatchJobsDataSourceJndiName")) {
         return this._BatchJobsDataSourceJndiName;
      } else if (name.equals("BatchJobsExecutorServiceName")) {
         return this._BatchJobsExecutorServiceName;
      } else if (name.equals("BridgeDestinations")) {
         return this._BridgeDestinations;
      } else if (name.equals("Callouts")) {
         return this._Callouts;
      } else if (name.equals("CdiContainer")) {
         return this._CdiContainer;
      } else if (name.equals("ClusterConstraintsEnabled")) {
         return new Boolean(this._ClusterConstraintsEnabled);
      } else if (name.equals("Clusters")) {
         return this._Clusters;
      } else if (name.equals("CoherenceClusterSystemResources")) {
         return this._CoherenceClusterSystemResources;
      } else if (name.equals("CoherenceManagementClusters")) {
         return this._CoherenceManagementClusters;
      } else if (name.equals("CoherenceServers")) {
         return this._CoherenceServers;
      } else if (name.equals("ConfigBackupEnabled")) {
         return new Boolean(this._ConfigBackupEnabled);
      } else if (name.equals("ConfigurationAuditType")) {
         return this._ConfigurationAuditType;
      } else if (name.equals("ConfigurationVersion")) {
         return this._ConfigurationVersion;
      } else if (name.equals("ConsoleContextPath")) {
         return this._ConsoleContextPath;
      } else if (name.equals("ConsoleEnabled")) {
         return new Boolean(this._ConsoleEnabled);
      } else if (name.equals("ConsoleExtensionDirectory")) {
         return this._ConsoleExtensionDirectory;
      } else if (name.equals("CustomResources")) {
         return this._CustomResources;
      } else if (name.equals("DBPassiveMode")) {
         return new Boolean(this._DBPassiveMode);
      } else if (name.equals("DBPassiveModeGracePeriodSeconds")) {
         return new Integer(this._DBPassiveModeGracePeriodSeconds);
      } else if (name.equals("DebugPatches")) {
         return this._DebugPatches;
      } else if (name.equals("DeploymentConfiguration")) {
         return this._DeploymentConfiguration;
      } else if (name.equals("Deployments")) {
         return this._Deployments;
      } else if (name.equals("DiagnosticContextCompatibilityModeEnabled")) {
         return new Boolean(this._DiagnosticContextCompatibilityModeEnabled);
      } else if (name.equals("DomainLibraries")) {
         return this._DomainLibraries;
      } else if (name.equals("DomainVersion")) {
         return this._DomainVersion;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EJBContainer")) {
         return this._EJBContainer;
      } else if (name.equals("EmbeddedLDAP")) {
         return this._EmbeddedLDAP;
      } else if (name.equals("EnableEECompliantClassloadingForEmbeddedAdapters")) {
         return new Boolean(this._EnableEECompliantClassloadingForEmbeddedAdapters);
      } else if (name.equals("ExalogicOptimizationsEnabled")) {
         return new Boolean(this._ExalogicOptimizationsEnabled);
      } else if (name.equals("FileStores")) {
         return this._FileStores;
      } else if (name.equals("FileT3s")) {
         return this._FileT3s;
      } else if (name.equals("ForeignJMSConnectionFactories")) {
         return this._ForeignJMSConnectionFactories;
      } else if (name.equals("ForeignJMSDestinations")) {
         return this._ForeignJMSDestinations;
      } else if (name.equals("ForeignJMSServers")) {
         return this._ForeignJMSServers;
      } else if (name.equals("ForeignJNDIProviders")) {
         return this._ForeignJNDIProviders;
      } else if (name.equals("GuardianEnabled")) {
         return new Boolean(this._GuardianEnabled);
      } else if (name.equals("InstalledSoftwareVersion")) {
         return this._InstalledSoftwareVersion;
      } else if (name.equals("Interceptors")) {
         return this._Interceptors;
      } else if (name.equals("InternalAppDeployments")) {
         return this._InternalAppDeployments;
      } else if (name.equals("InternalAppsDeployOnDemandEnabled")) {
         return new Boolean(this._InternalAppsDeployOnDemandEnabled);
      } else if (name.equals("InternalLibraries")) {
         return this._InternalLibraries;
      } else if (name.equals("JDBCStores")) {
         return this._JDBCStores;
      } else if (name.equals("JDBCSystemResources")) {
         return this._JDBCSystemResources;
      } else if (name.equals("JMSBridgeDestinations")) {
         return this._JMSBridgeDestinations;
      } else if (name.equals("JMSConnectionConsumers")) {
         return this._JMSConnectionConsumers;
      } else if (name.equals("JMSConnectionFactories")) {
         return this._JMSConnectionFactories;
      } else if (name.equals("JMSDestinationKeys")) {
         return this._JMSDestinationKeys;
      } else if (name.equals("JMSDestinations")) {
         return this._JMSDestinations;
      } else if (name.equals("JMSDistributedQueueMembers")) {
         return this._JMSDistributedQueueMembers;
      } else if (name.equals("JMSDistributedQueues")) {
         return this._JMSDistributedQueues;
      } else if (name.equals("JMSDistributedTopicMembers")) {
         return this._JMSDistributedTopicMembers;
      } else if (name.equals("JMSDistributedTopics")) {
         return this._JMSDistributedTopics;
      } else if (name.equals("JMSFileStores")) {
         return this._JMSFileStores;
      } else if (name.equals("JMSJDBCStores")) {
         return this._JMSJDBCStores;
      } else if (name.equals("JMSQueues")) {
         return this._JMSQueues;
      } else if (name.equals("JMSServers")) {
         return this._JMSServers;
      } else if (name.equals("JMSSessionPools")) {
         return this._JMSSessionPools;
      } else if (name.equals("JMSStores")) {
         return this._JMSStores;
      } else if (name.equals("JMSSystemResources")) {
         return this._JMSSystemResources;
      } else if (name.equals("JMSTemplates")) {
         return this._JMSTemplates;
      } else if (name.equals("JMSTopics")) {
         return this._JMSTopics;
      } else if (name.equals("JMX")) {
         return this._JMX;
      } else if (name.equals("JPA")) {
         return this._JPA;
      } else if (name.equals("JTA")) {
         return this._JTA;
      } else if (name.equals("JavaServiceConsoleEnabled")) {
         return new Boolean(this._JavaServiceConsoleEnabled);
      } else if (name.equals("JavaServiceEnabled")) {
         return new Boolean(this._JavaServiceEnabled);
      } else if (name.equals("JoltConnectionPools")) {
         return this._JoltConnectionPools;
      } else if (name.equals("LastModificationTime")) {
         return new Long(this._LastModificationTime);
      } else if (name.equals("Libraries")) {
         return this._Libraries;
      } else if (name.equals("LifecycleManagerConfig")) {
         return this._LifecycleManagerConfig;
      } else if (name.equals("LifecycleManagerEndPoints")) {
         return this._LifecycleManagerEndPoints;
      } else if (name.equals("Log")) {
         return this._Log;
      } else if (name.equals("LogFilters")) {
         return this._LogFilters;
      } else if (name.equals("LogFormatCompatibilityEnabled")) {
         return new Boolean(this._LogFormatCompatibilityEnabled);
      } else if (name.equals("Machines")) {
         return this._Machines;
      } else if (name.equals("MailSessions")) {
         return this._MailSessions;
      } else if (name.equals("ManagedExecutorServiceTemplates")) {
         return this._ManagedExecutorServiceTemplates;
      } else if (name.equals("ManagedExecutorServices")) {
         return this._ManagedExecutorServices;
      } else if (name.equals("ManagedScheduledExecutorServiceTemplates")) {
         return this._ManagedScheduledExecutorServiceTemplates;
      } else if (name.equals("ManagedScheduledExecutorServices")) {
         return this._ManagedScheduledExecutorServices;
      } else if (name.equals("ManagedThreadFactories")) {
         return this._ManagedThreadFactories;
      } else if (name.equals("ManagedThreadFactoryTemplates")) {
         return this._ManagedThreadFactoryTemplates;
      } else if (name.equals("MaxConcurrentLongRunningRequests")) {
         return new Integer(this._MaxConcurrentLongRunningRequests);
      } else if (name.equals("MaxConcurrentNewThreads")) {
         return new Integer(this._MaxConcurrentNewThreads);
      } else if (name.equals("MessagingBridges")) {
         return this._MessagingBridges;
      } else if (name.equals("MigratableRMIServices")) {
         return this._MigratableRMIServices;
      } else if (name.equals("MigratableTargets")) {
         return this._MigratableTargets;
      } else if (name.equals("MsgIdPrefixCompatibilityEnabled")) {
         return new Boolean(this._MsgIdPrefixCompatibilityEnabled);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NetworkChannels")) {
         return this._NetworkChannels;
      } else if (name.equals("OCMEnabled")) {
         return new Boolean(this._OCMEnabled);
      } else if (name.equals("OptionalFeatureDeployment")) {
         return this._OptionalFeatureDeployment;
      } else if (name.equals("OsgiFrameworks")) {
         return this._OsgiFrameworks;
      } else if (name.equals("ParallelDeployApplicationModules")) {
         return new Boolean(this._ParallelDeployApplicationModules);
      } else if (name.equals("ParallelDeployApplications")) {
         return new Boolean(this._ParallelDeployApplications);
      } else if (name.equals("PartitionTemplates")) {
         return this._PartitionTemplates;
      } else if (name.equals("PartitionUriSpace")) {
         return this._PartitionUriSpace;
      } else if (name.equals("PartitionWorkManagers")) {
         return this._PartitionWorkManagers;
      } else if (name.equals("Partitions")) {
         return this._Partitions;
      } else if (name.equals("PathServices")) {
         return this._PathServices;
      } else if (name.equals("ProductionModeEnabled")) {
         return new Boolean(this._ProductionModeEnabled);
      } else if (name.equals("ReplicatedStores")) {
         return this._ReplicatedStores;
      } else if (name.equals("ResourceGroupTemplates")) {
         return this._ResourceGroupTemplates;
      } else if (name.equals("ResourceGroups")) {
         return this._ResourceGroups;
      } else if (name.equals("ResourceManagement")) {
         return this._ResourceManagement;
      } else if (name.equals("RestfulManagementServices")) {
         return this._RestfulManagementServices;
      } else if (name.equals("RootDirectory")) {
         return this._RootDirectory;
      } else if (name.equals("SAFAgents")) {
         return this._SAFAgents;
      } else if (name.equals("SNMPAgent")) {
         return this._SNMPAgent;
      } else if (name.equals("SNMPAgentDeployments")) {
         return this._SNMPAgentDeployments;
      } else if (name.equals("SNMPAttributeChanges")) {
         return this._SNMPAttributeChanges;
      } else if (name.equals("SNMPCounterMonitors")) {
         return this._SNMPCounterMonitors;
      } else if (name.equals("SNMPGaugeMonitors")) {
         return this._SNMPGaugeMonitors;
      } else if (name.equals("SNMPLogFilters")) {
         return this._SNMPLogFilters;
      } else if (name.equals("SNMPProxies")) {
         return this._SNMPProxies;
      } else if (name.equals("SNMPStringMonitors")) {
         return this._SNMPStringMonitors;
      } else if (name.equals("SNMPTrapDestinations")) {
         return this._SNMPTrapDestinations;
      } else if (name.equals("SecurityConfiguration")) {
         return this._SecurityConfiguration;
      } else if (name.equals("SelfTuning")) {
         return this._SelfTuning;
      } else if (name.equals("ServerMigrationHistorySize")) {
         return new Integer(this._ServerMigrationHistorySize);
      } else if (name.equals("ServerTemplates")) {
         return this._ServerTemplates;
      } else if (name.equals("Servers")) {
         return this._Servers;
      } else if (name.equals("ServiceMigrationHistorySize")) {
         return new Integer(this._ServiceMigrationHistorySize);
      } else if (name.equals("ShutdownClasses")) {
         return this._ShutdownClasses;
      } else if (name.equals("SingletonServices")) {
         return this._SingletonServices;
      } else if (name.equals("SiteName")) {
         return this._SiteName;
      } else if (name.equals("StartupClasses")) {
         return this._StartupClasses;
      } else if (name.equals("SystemComponentConfigurations")) {
         return this._SystemComponentConfigurations;
      } else if (name.equals("SystemComponents")) {
         return this._SystemComponents;
      } else if (name.equals("SystemResources")) {
         return this._SystemResources;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("VirtualHosts")) {
         return this._VirtualHosts;
      } else if (name.equals("VirtualTargets")) {
         return this._VirtualTargets;
      } else if (name.equals("WLDFSystemResources")) {
         return this._WLDFSystemResources;
      } else if (name.equals("WSReliableDeliveryPolicies")) {
         return this._WSReliableDeliveryPolicies;
      } else if (name.equals("WTCServers")) {
         return this._WTCServers;
      } else if (name.equals("WebAppContainer")) {
         return this._WebAppContainer;
      } else if (name.equals("WebserviceSecurities")) {
         return this._WebserviceSecurities;
      } else if (name.equals("WebserviceTestpage")) {
         return this._WebserviceTestpage;
      } else if (name.equals("XMLEntityCaches")) {
         return this._XMLEntityCaches;
      } else if (name.equals("XMLRegistries")) {
         return this._XMLRegistries;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationPropertiesMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("jmx")) {
                  return 75;
               }

               if (s.equals("jpa")) {
                  return 16;
               }

               if (s.equals("jta")) {
                  return 15;
               }

               if (s.equals("log")) {
                  return 19;
               }

               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 8:
            case 25:
            case 39:
            case 40:
            case 41:
            case 42:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            default:
               break;
            case 6:
               if (s.equals("filet3")) {
                  return 33;
               }

               if (s.equals("server")) {
                  return 28;
               }

               if (s.equals("target")) {
                  return 57;
               }

               if (s.equals("active")) {
                  return 13;
               }
               break;
            case 7:
               if (s.equals("callout")) {
                  return 164;
               }

               if (s.equals("cluster")) {
                  return 31;
               }

               if (s.equals("library")) {
                  return 50;
               }

               if (s.equals("machine")) {
                  return 54;
               }
               break;
            case 9:
               if (s.equals("jms-queue")) {
                  return 63;
               }

               if (s.equals("jms-store")) {
                  return 59;
               }

               if (s.equals("jms-topic")) {
                  return 64;
               }

               if (s.equals("partition")) {
                  return 134;
               }

               if (s.equals("saf-agent")) {
                  return 102;
               }

               if (s.equals("site-name")) {
                  return 159;
               }
               break;
            case 10:
               if (s.equals("deployment")) {
                  return 32;
               }

               if (s.equals("file-store")) {
                  return 91;
               }

               if (s.equals("jdbc-store")) {
                  return 93;
               }

               if (s.equals("jms-server")) {
                  return 58;
               }

               if (s.equals("log-filter")) {
                  return 90;
               }

               if (s.equals("snmp-agent")) {
                  return 20;
               }

               if (s.equals("snmp-proxy")) {
                  return 108;
               }

               if (s.equals("wtc-server")) {
                  return 18;
               }
               break;
            case 11:
               if (s.equals("application")) {
                  return 47;
               }

               if (s.equals("self-tuning")) {
                  return 76;
               }

               if (s.equals("ocm-enabled")) {
                  return 122;
               }
               break;
            case 12:
               if (s.equals("batch-config")) {
                  return 151;
               }

               if (s.equals("embeddedldap")) {
                  return 36;
               }

               if (s.equals("interceptors")) {
                  return 150;
               }

               if (s.equals("jms-template")) {
                  return 67;
               }

               if (s.equals("mail-session")) {
                  return 88;
               }

               if (s.equals("path-service")) {
                  return 78;
               }

               if (s.equals("virtual-host")) {
                  return 69;
               }

               if (s.equals("xml-registry")) {
                  return 56;
               }
               break;
            case 13:
               if (s.equals("admin-console")) {
                  return 119;
               }

               if (s.equals("cdi-container")) {
                  return 74;
               }

               if (s.equals("debug-patches")) {
                  return 152;
               }

               if (s.equals("ejb-container")) {
                  return 72;
               }

               if (s.equals("startup-class")) {
                  return 86;
               }
               break;
            case 14:
               if (s.equals("app-deployment")) {
                  return 48;
               }

               if (s.equals("domain-library")) {
                  return 51;
               }

               if (s.equals("domain-version")) {
                  return 11;
               }

               if (s.equals("jms-file-store")) {
                  return 61;
               }

               if (s.equals("jms-jdbc-store")) {
                  return 60;
               }

               if (s.equals("osgi-framework")) {
                  return 129;
               }

               if (s.equals("resource-group")) {
                  return 136;
               }

               if (s.equals("root-directory")) {
                  return 22;
               }

               if (s.equals("shutdown-class")) {
                  return 85;
               }

               if (s.equals("virtual-target")) {
                  return 70;
               }
               break;
            case 15:
               if (s.equals("custom-resource")) {
                  return 95;
               }

               if (s.equals("jms-destination")) {
                  return 62;
               }

               if (s.equals("network-channel")) {
                  return 68;
               }

               if (s.equals("snmp-log-filter")) {
                  return 112;
               }

               if (s.equals("server-template")) {
                  return 29;
               }

               if (s.equals("system-resource")) {
                  return 101;
               }

               if (s.equals("console-enabled")) {
                  return 23;
               }

               if (s.equals("db-passive-mode")) {
                  return 161;
               }
               break;
            case 16:
               if (s.equals("coherence-server")) {
                  return 30;
               }

               if (s.equals("internal-library")) {
                  return 52;
               }

               if (s.equals("jms-session-pool")) {
                  return 81;
               }

               if (s.equals("messaging-bridge")) {
                  return 34;
               }

               if (s.equals("replicated-store")) {
                  return 92;
               }

               if (s.equals("system-component")) {
                  return 127;
               }

               if (s.equals("xml-entity-cache")) {
                  return 55;
               }

               if (s.equals("guardian-enabled")) {
                  return 121;
               }
               break;
            case 17:
               if (s.equals("admin-server-name")) {
                  return 97;
               }

               if (s.equals("migratable-target")) {
                  return 71;
               }

               if (s.equals("singleton-service")) {
                  return 87;
               }

               if (s.equals("web-app-container")) {
                  return 73;
               }
               break;
            case 18:
               if (s.equals("admin-serverm-bean")) {
                  return 104;
               }

               if (s.equals("bridge-destination")) {
                  return 83;
               }

               if (s.equals("foreign-jms-server")) {
                  return 84;
               }

               if (s.equals("partition-template")) {
                  return 148;
               }

               if (s.equals("snmp-gauge-monitor")) {
                  return 109;
               }
               break;
            case 19:
               if (s.equals("administration-port")) {
                  return 38;
               }

               if (s.equals("jms-destination-key")) {
                  return 79;
               }

               if (s.equals("jms-system-resource")) {
                  return 94;
               }

               if (s.equals("partition-uri-space")) {
                  return 135;
               }

               if (s.equals("resource-management")) {
                  return 77;
               }

               if (s.equals("snmp-string-monitor")) {
                  return 110;
               }

               if (s.equals("webservice-security")) {
                  return 114;
               }

               if (s.equals("webservice-testpage")) {
                  return 130;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("console-context-path")) {
                  return 25;
               }

               if (s.equals("jdbc-system-resource")) {
                  return 100;
               }

               if (s.equals("jolt-connection-pool")) {
                  return 89;
               }

               if (s.equals("snmp-counter-monitor")) {
                  return 111;
               }

               if (s.equals("wldf-system-resource")) {
                  return 99;
               }

               if (s.equals("java-service-enabled")) {
                  return 40;
               }
               break;
            case 21:
               if (s.equals("configuration-version")) {
                  return 43;
               }

               if (s.equals("foreign-jndi-provider")) {
                  return 96;
               }

               if (s.equals("jms-distributed-queue")) {
                  return 65;
               }

               if (s.equals("jms-distributed-topic")) {
                  return 66;
               }

               if (s.equals("migratablermi-service")) {
                  return 103;
               }

               if (s.equals("snmp-agent-deployment")) {
                  return 21;
               }

               if (s.equals("snmp-attribute-change")) {
                  return 113;
               }

               if (s.equals("snmp-trap-destination")) {
                  return 107;
               }

               if (s.equals("config-backup-enabled")) {
                  return 42;
               }
               break;
            case 22:
               if (s.equals("jms-bridge-destination")) {
                  return 82;
               }

               if (s.equals("jms-connection-factory")) {
                  return 80;
               }

               if (s.equals("last-modification-time")) {
                  return 12;
               }

               if (s.equals("managed-thread-factory")) {
                  return 147;
               }

               if (s.equals("partition-work-manager")) {
                  return 153;
               }

               if (s.equals("security-configuration")) {
                  return 14;
               }
               break;
            case 23:
               if (s.equals("administration-protocol")) {
                  return 98;
               }

               if (s.equals("foreign-jms-destination")) {
                  return 116;
               }

               if (s.equals("internal-app-deployment")) {
                  return 49;
               }

               if (s.equals("jms-connection-consumer")) {
                  return 117;
               }

               if (s.equals("resource-group-template")) {
                  return 137;
               }

               if (s.equals("production-mode-enabled")) {
                  return 35;
               }
               break;
            case 24:
               if (s.equals("configuration-audit-type")) {
                  return 45;
               }

               if (s.equals("deployment-configuration")) {
                  return 17;
               }

               if (s.equals("lifecycle-manager-config")) {
                  return 158;
               }

               if (s.equals("managed-executor-service")) {
                  return 145;
               }
               break;
            case 26:
               if (s.equals("installed-software-version")) {
                  return 163;
               }

               if (s.equals("max-concurrent-new-threads")) {
                  return 138;
               }
               break;
            case 27:
               if (s.equals("archive-configuration-count")) {
                  return 41;
               }

               if (s.equals("console-extension-directory")) {
                  return 26;
               }

               if (s.equals("lifecycle-manager-end-point")) {
                  return 149;
               }

               if (s.equals("optional-feature-deployment")) {
                  return 157;
               }

               if (s.equals("restful-management-services")) {
                  return 126;
               }

               if (s.equals("ws-reliable-delivery-policy")) {
                  return 53;
               }

               if (s.equals("administration-port-enabled")) {
                  return 37;
               }

               if (s.equals("cluster-constraints-enabled")) {
                  return 46;
               }
               break;
            case 28:
               if (s.equals("coherence-management-cluster")) {
                  return 133;
               }

               if (s.equals("jms-distributed-queue-member")) {
                  return 105;
               }

               if (s.equals("jms-distributed-topic-member")) {
                  return 106;
               }

               if (s.equals("java-service-console-enabled")) {
                  return 24;
               }

               if (s.equals("parallel-deploy-applications")) {
                  return 140;
               }
               break;
            case 29:
               if (s.equals("server-migration-history-size")) {
                  return 131;
               }
               break;
            case 30:
               if (s.equals("foreign-jms-connection-factory")) {
                  return 115;
               }

               if (s.equals("service-migration-history-size")) {
                  return 132;
               }

               if (s.equals("system-component-configuration")) {
                  return 128;
               }

               if (s.equals("exalogic-optimizations-enabled")) {
                  return 39;
               }
               break;
            case 31:
               if (s.equals("managed-thread-factory-template")) {
                  return 144;
               }

               if (s.equals("auto-configuration-save-enabled")) {
                  return 27;
               }
               break;
            case 32:
               if (s.equals("batch-jobs-data-source-jndi-name")) {
                  return 155;
               }

               if (s.equals("batch-jobs-executor-service-name")) {
                  return 156;
               }

               if (s.equals("log-format-compatibility-enabled")) {
                  return 124;
               }
               break;
            case 33:
               if (s.equals("coherence-cluster-system-resource")) {
                  return 125;
               }

               if (s.equals("managed-executor-service-template")) {
                  return 142;
               }
               break;
            case 34:
               if (s.equals("managed-scheduled-executor-service")) {
                  return 146;
               }

               if (s.equals("auto-deploy-for-submodules-enabled")) {
                  return 118;
               }
               break;
            case 35:
               if (s.equals("msg-id-prefix-compatibility-enabled")) {
                  return 123;
               }

               if (s.equals("parallel-deploy-application-modules")) {
                  return 141;
               }
               break;
            case 36:
               if (s.equals("db-passive-mode-grace-period-seconds")) {
                  return 162;
               }

               if (s.equals("max-concurrent-long-running-requests")) {
                  return 139;
               }
               break;
            case 37:
               if (s.equals("administrationm-bean-auditing-enabled")) {
                  return 44;
               }
               break;
            case 38:
               if (s.equals("internal-apps-deploy-on-demand-enabled")) {
                  return 120;
               }
               break;
            case 43:
               if (s.equals("managed-scheduled-executor-service-template")) {
                  return 143;
               }
               break;
            case 45:
               if (s.equals("diagnostic-context-compatibility-mode-enabled")) {
                  return 154;
               }
               break;
            case 53:
               if (s.equals("enableee-compliant-classloading-for-embedded-adapters")) {
                  return 160;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            case 11:
            case 12:
            case 13:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 32:
            case 35:
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
            case 49:
            case 52:
            case 57:
            case 59:
            case 97:
            case 98:
            case 101:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 131:
            case 132:
            case 135:
            case 138:
            case 139:
            case 140:
            case 141:
            case 154:
            case 155:
            case 156:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            default:
               return super.getSchemaHelper(propIndex);
            case 14:
               return new SecurityConfigurationMBeanImpl.SchemaHelper2();
            case 15:
               return new JTAMBeanImpl.SchemaHelper2();
            case 16:
               return new JPAMBeanImpl.SchemaHelper2();
            case 17:
               return new DeploymentConfigurationMBeanImpl.SchemaHelper2();
            case 18:
               return new WTCServerMBeanImpl.SchemaHelper2();
            case 19:
               return new LogMBeanImpl.SchemaHelper2();
            case 20:
               return new SNMPAgentMBeanImpl.SchemaHelper2();
            case 21:
               return new SNMPAgentDeploymentMBeanImpl.SchemaHelper2();
            case 28:
               return new ServerMBeanImpl.SchemaHelper2();
            case 29:
               return new ServerTemplateMBeanImpl.SchemaHelper2();
            case 30:
               return new CoherenceServerMBeanImpl.SchemaHelper2();
            case 31:
               return new ClusterMBeanImpl.SchemaHelper2();
            case 33:
               return new FileT3MBeanImpl.SchemaHelper2();
            case 34:
               return new MessagingBridgeMBeanImpl.SchemaHelper2();
            case 36:
               return new EmbeddedLDAPMBeanImpl.SchemaHelper2();
            case 47:
               return new ApplicationMBeanImpl.SchemaHelper2();
            case 48:
               return new AppDeploymentMBeanImpl.SchemaHelper2();
            case 50:
               return new LibraryMBeanImpl.SchemaHelper2();
            case 51:
               return new DomainLibraryMBeanImpl.SchemaHelper2();
            case 53:
               return new WSReliableDeliveryPolicyMBeanImpl.SchemaHelper2();
            case 54:
               return new MachineMBeanImpl.SchemaHelper2();
            case 55:
               return new XMLEntityCacheMBeanImpl.SchemaHelper2();
            case 56:
               return new XMLRegistryMBeanImpl.SchemaHelper2();
            case 58:
               return new JMSServerMBeanImpl.SchemaHelper2();
            case 60:
               return new JMSJDBCStoreMBeanImpl.SchemaHelper2();
            case 61:
               return new JMSFileStoreMBeanImpl.SchemaHelper2();
            case 62:
               return new JMSDestinationMBeanImpl.SchemaHelper2();
            case 63:
               return new JMSQueueMBeanImpl.SchemaHelper2();
            case 64:
               return new JMSTopicMBeanImpl.SchemaHelper2();
            case 65:
               return new JMSDistributedQueueMBeanImpl.SchemaHelper2();
            case 66:
               return new JMSDistributedTopicMBeanImpl.SchemaHelper2();
            case 67:
               return new JMSTemplateMBeanImpl.SchemaHelper2();
            case 68:
               return new NetworkChannelMBeanImpl.SchemaHelper2();
            case 69:
               return new VirtualHostMBeanImpl.SchemaHelper2();
            case 70:
               return new VirtualTargetMBeanImpl.SchemaHelper2();
            case 71:
               return new MigratableTargetMBeanImpl.SchemaHelper2();
            case 72:
               return new EJBContainerMBeanImpl.SchemaHelper2();
            case 73:
               return new WebAppContainerMBeanImpl.SchemaHelper2();
            case 74:
               return new CdiContainerMBeanImpl.SchemaHelper2();
            case 75:
               return new JMXMBeanImpl.SchemaHelper2();
            case 76:
               return new SelfTuningMBeanImpl.SchemaHelper2();
            case 77:
               return new ResourceManagementMBeanImpl.SchemaHelper2();
            case 78:
               return new PathServiceMBeanImpl.SchemaHelper2();
            case 79:
               return new JMSDestinationKeyMBeanImpl.SchemaHelper2();
            case 80:
               return new JMSConnectionFactoryMBeanImpl.SchemaHelper2();
            case 81:
               return new JMSSessionPoolMBeanImpl.SchemaHelper2();
            case 82:
               return new JMSBridgeDestinationMBeanImpl.SchemaHelper2();
            case 83:
               return new BridgeDestinationMBeanImpl.SchemaHelper2();
            case 84:
               return new ForeignJMSServerMBeanImpl.SchemaHelper2();
            case 85:
               return new ShutdownClassMBeanImpl.SchemaHelper2();
            case 86:
               return new StartupClassMBeanImpl.SchemaHelper2();
            case 87:
               return new SingletonServiceMBeanImpl.SchemaHelper2();
            case 88:
               return new MailSessionMBeanImpl.SchemaHelper2();
            case 89:
               return new JoltConnectionPoolMBeanImpl.SchemaHelper2();
            case 90:
               return new LogFilterMBeanImpl.SchemaHelper2();
            case 91:
               return new FileStoreMBeanImpl.SchemaHelper2();
            case 92:
               return new ReplicatedStoreMBeanImpl.SchemaHelper2();
            case 93:
               return new JDBCStoreMBeanImpl.SchemaHelper2();
            case 94:
               return new JMSSystemResourceMBeanImpl.SchemaHelper2();
            case 95:
               return new CustomResourceMBeanImpl.SchemaHelper2();
            case 96:
               return new ForeignJNDIProviderMBeanImpl.SchemaHelper2();
            case 99:
               return new WLDFSystemResourceMBeanImpl.SchemaHelper2();
            case 100:
               return new JDBCSystemResourceMBeanImpl.SchemaHelper2();
            case 102:
               return new SAFAgentMBeanImpl.SchemaHelper2();
            case 103:
               return new MigratableRMIServiceMBeanImpl.SchemaHelper2();
            case 104:
               return new AdminServerMBeanImpl.SchemaHelper2();
            case 105:
               return new JMSDistributedQueueMemberMBeanImpl.SchemaHelper2();
            case 106:
               return new JMSDistributedTopicMemberMBeanImpl.SchemaHelper2();
            case 107:
               return new SNMPTrapDestinationMBeanImpl.SchemaHelper2();
            case 108:
               return new SNMPProxyMBeanImpl.SchemaHelper2();
            case 109:
               return new SNMPGaugeMonitorMBeanImpl.SchemaHelper2();
            case 110:
               return new SNMPStringMonitorMBeanImpl.SchemaHelper2();
            case 111:
               return new SNMPCounterMonitorMBeanImpl.SchemaHelper2();
            case 112:
               return new SNMPLogFilterMBeanImpl.SchemaHelper2();
            case 113:
               return new SNMPAttributeChangeMBeanImpl.SchemaHelper2();
            case 114:
               return new WebserviceSecurityMBeanImpl.SchemaHelper2();
            case 115:
               return new ForeignJMSConnectionFactoryMBeanImpl.SchemaHelper2();
            case 116:
               return new ForeignJMSDestinationMBeanImpl.SchemaHelper2();
            case 117:
               return new JMSConnectionConsumerMBeanImpl.SchemaHelper2();
            case 119:
               return new AdminConsoleMBeanImpl.SchemaHelper2();
            case 125:
               return new CoherenceClusterSystemResourceMBeanImpl.SchemaHelper2();
            case 126:
               return new RestfulManagementServicesMBeanImpl.SchemaHelper2();
            case 127:
               return new SystemComponentMBeanImpl.SchemaHelper2();
            case 128:
               return new SystemComponentConfigurationMBeanImpl.SchemaHelper2();
            case 129:
               return new OsgiFrameworkMBeanImpl.SchemaHelper2();
            case 130:
               return new WebserviceTestpageMBeanImpl.SchemaHelper2();
            case 133:
               return new CoherenceManagementClusterMBeanImpl.SchemaHelper2();
            case 134:
               return new PartitionMBeanImpl.SchemaHelper2();
            case 136:
               return new ResourceGroupMBeanImpl.SchemaHelper2();
            case 137:
               return new ResourceGroupTemplateMBeanImpl.SchemaHelper2();
            case 142:
               return new ManagedExecutorServiceTemplateMBeanImpl.SchemaHelper2();
            case 143:
               return new ManagedScheduledExecutorServiceTemplateMBeanImpl.SchemaHelper2();
            case 144:
               return new ManagedThreadFactoryTemplateMBeanImpl.SchemaHelper2();
            case 145:
               return new ManagedExecutorServiceMBeanImpl.SchemaHelper2();
            case 146:
               return new ManagedScheduledExecutorServiceMBeanImpl.SchemaHelper2();
            case 147:
               return new ManagedThreadFactoryMBeanImpl.SchemaHelper2();
            case 148:
               return new PartitionTemplateMBeanImpl.SchemaHelper2();
            case 149:
               return new LifecycleManagerEndPointMBeanImpl.SchemaHelper2();
            case 150:
               return new InterceptorsMBeanImpl.SchemaHelper2();
            case 151:
               return new BatchConfigMBeanImpl.SchemaHelper2();
            case 152:
               return new DebugPatchesMBeanImpl.SchemaHelper2();
            case 153:
               return new PartitionWorkManagerMBeanImpl.SchemaHelper2();
            case 157:
               return new OptionalFeatureDeploymentMBeanImpl.SchemaHelper2();
            case 158:
               return new LifecycleManagerConfigMBeanImpl.SchemaHelper2();
            case 164:
               return new CalloutMBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "domainm";
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
            case 10:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 11:
               return "domain-version";
            case 12:
               return "last-modification-time";
            case 13:
               return "active";
            case 14:
               return "security-configuration";
            case 15:
               return "jta";
            case 16:
               return "jpa";
            case 17:
               return "deployment-configuration";
            case 18:
               return "wtc-server";
            case 19:
               return "log";
            case 20:
               return "snmp-agent";
            case 21:
               return "snmp-agent-deployment";
            case 22:
               return "root-directory";
            case 23:
               return "console-enabled";
            case 24:
               return "java-service-console-enabled";
            case 25:
               return "console-context-path";
            case 26:
               return "console-extension-directory";
            case 27:
               return "auto-configuration-save-enabled";
            case 28:
               return "server";
            case 29:
               return "server-template";
            case 30:
               return "coherence-server";
            case 31:
               return "cluster";
            case 32:
               return "deployment";
            case 33:
               return "filet3";
            case 34:
               return "messaging-bridge";
            case 35:
               return "production-mode-enabled";
            case 36:
               return "embeddedldap";
            case 37:
               return "administration-port-enabled";
            case 38:
               return "administration-port";
            case 39:
               return "exalogic-optimizations-enabled";
            case 40:
               return "java-service-enabled";
            case 41:
               return "archive-configuration-count";
            case 42:
               return "config-backup-enabled";
            case 43:
               return "configuration-version";
            case 44:
               return "administrationm-bean-auditing-enabled";
            case 45:
               return "configuration-audit-type";
            case 46:
               return "cluster-constraints-enabled";
            case 47:
               return "application";
            case 48:
               return "app-deployment";
            case 49:
               return "internal-app-deployment";
            case 50:
               return "library";
            case 51:
               return "domain-library";
            case 52:
               return "internal-library";
            case 53:
               return "ws-reliable-delivery-policy";
            case 54:
               return "machine";
            case 55:
               return "xml-entity-cache";
            case 56:
               return "xml-registry";
            case 57:
               return "target";
            case 58:
               return "jms-server";
            case 59:
               return "jms-store";
            case 60:
               return "jms-jdbc-store";
            case 61:
               return "jms-file-store";
            case 62:
               return "jms-destination";
            case 63:
               return "jms-queue";
            case 64:
               return "jms-topic";
            case 65:
               return "jms-distributed-queue";
            case 66:
               return "jms-distributed-topic";
            case 67:
               return "jms-template";
            case 68:
               return "network-channel";
            case 69:
               return "virtual-host";
            case 70:
               return "virtual-target";
            case 71:
               return "migratable-target";
            case 72:
               return "ejb-container";
            case 73:
               return "web-app-container";
            case 74:
               return "cdi-container";
            case 75:
               return "jmx";
            case 76:
               return "self-tuning";
            case 77:
               return "resource-management";
            case 78:
               return "path-service";
            case 79:
               return "jms-destination-key";
            case 80:
               return "jms-connection-factory";
            case 81:
               return "jms-session-pool";
            case 82:
               return "jms-bridge-destination";
            case 83:
               return "bridge-destination";
            case 84:
               return "foreign-jms-server";
            case 85:
               return "shutdown-class";
            case 86:
               return "startup-class";
            case 87:
               return "singleton-service";
            case 88:
               return "mail-session";
            case 89:
               return "jolt-connection-pool";
            case 90:
               return "log-filter";
            case 91:
               return "file-store";
            case 92:
               return "replicated-store";
            case 93:
               return "jdbc-store";
            case 94:
               return "jms-system-resource";
            case 95:
               return "custom-resource";
            case 96:
               return "foreign-jndi-provider";
            case 97:
               return "admin-server-name";
            case 98:
               return "administration-protocol";
            case 99:
               return "wldf-system-resource";
            case 100:
               return "jdbc-system-resource";
            case 101:
               return "system-resource";
            case 102:
               return "saf-agent";
            case 103:
               return "migratablermi-service";
            case 104:
               return "admin-serverm-bean";
            case 105:
               return "jms-distributed-queue-member";
            case 106:
               return "jms-distributed-topic-member";
            case 107:
               return "snmp-trap-destination";
            case 108:
               return "snmp-proxy";
            case 109:
               return "snmp-gauge-monitor";
            case 110:
               return "snmp-string-monitor";
            case 111:
               return "snmp-counter-monitor";
            case 112:
               return "snmp-log-filter";
            case 113:
               return "snmp-attribute-change";
            case 114:
               return "webservice-security";
            case 115:
               return "foreign-jms-connection-factory";
            case 116:
               return "foreign-jms-destination";
            case 117:
               return "jms-connection-consumer";
            case 118:
               return "auto-deploy-for-submodules-enabled";
            case 119:
               return "admin-console";
            case 120:
               return "internal-apps-deploy-on-demand-enabled";
            case 121:
               return "guardian-enabled";
            case 122:
               return "ocm-enabled";
            case 123:
               return "msg-id-prefix-compatibility-enabled";
            case 124:
               return "log-format-compatibility-enabled";
            case 125:
               return "coherence-cluster-system-resource";
            case 126:
               return "restful-management-services";
            case 127:
               return "system-component";
            case 128:
               return "system-component-configuration";
            case 129:
               return "osgi-framework";
            case 130:
               return "webservice-testpage";
            case 131:
               return "server-migration-history-size";
            case 132:
               return "service-migration-history-size";
            case 133:
               return "coherence-management-cluster";
            case 134:
               return "partition";
            case 135:
               return "partition-uri-space";
            case 136:
               return "resource-group";
            case 137:
               return "resource-group-template";
            case 138:
               return "max-concurrent-new-threads";
            case 139:
               return "max-concurrent-long-running-requests";
            case 140:
               return "parallel-deploy-applications";
            case 141:
               return "parallel-deploy-application-modules";
            case 142:
               return "managed-executor-service-template";
            case 143:
               return "managed-scheduled-executor-service-template";
            case 144:
               return "managed-thread-factory-template";
            case 145:
               return "managed-executor-service";
            case 146:
               return "managed-scheduled-executor-service";
            case 147:
               return "managed-thread-factory";
            case 148:
               return "partition-template";
            case 149:
               return "lifecycle-manager-end-point";
            case 150:
               return "interceptors";
            case 151:
               return "batch-config";
            case 152:
               return "debug-patches";
            case 153:
               return "partition-work-manager";
            case 154:
               return "diagnostic-context-compatibility-mode-enabled";
            case 155:
               return "batch-jobs-data-source-jndi-name";
            case 156:
               return "batch-jobs-executor-service-name";
            case 157:
               return "optional-feature-deployment";
            case 158:
               return "lifecycle-manager-config";
            case 159:
               return "site-name";
            case 160:
               return "enableee-compliant-classloading-for-embedded-adapters";
            case 161:
               return "db-passive-mode";
            case 162:
               return "db-passive-mode-grace-period-seconds";
            case 163:
               return "installed-software-version";
            case 164:
               return "callout";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
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
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 97:
            case 98:
            case 104:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 130:
            case 131:
            case 132:
            case 135:
            case 138:
            case 139:
            case 140:
            case 141:
            case 150:
            case 151:
            case 152:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            default:
               return super.isArray(propIndex);
            case 18:
               return true;
            case 21:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 56:
               return true;
            case 57:
               return true;
            case 58:
               return true;
            case 59:
               return true;
            case 60:
               return true;
            case 61:
               return true;
            case 62:
               return true;
            case 63:
               return true;
            case 64:
               return true;
            case 65:
               return true;
            case 66:
               return true;
            case 67:
               return true;
            case 68:
               return true;
            case 69:
               return true;
            case 70:
               return true;
            case 71:
               return true;
            case 78:
               return true;
            case 79:
               return true;
            case 80:
               return true;
            case 81:
               return true;
            case 82:
               return true;
            case 83:
               return true;
            case 84:
               return true;
            case 85:
               return true;
            case 86:
               return true;
            case 87:
               return true;
            case 88:
               return true;
            case 89:
               return true;
            case 90:
               return true;
            case 91:
               return true;
            case 92:
               return true;
            case 93:
               return true;
            case 94:
               return true;
            case 95:
               return true;
            case 96:
               return true;
            case 99:
               return true;
            case 100:
               return true;
            case 101:
               return true;
            case 102:
               return true;
            case 103:
               return true;
            case 105:
               return true;
            case 106:
               return true;
            case 107:
               return true;
            case 108:
               return true;
            case 109:
               return true;
            case 110:
               return true;
            case 111:
               return true;
            case 112:
               return true;
            case 113:
               return true;
            case 114:
               return true;
            case 115:
               return true;
            case 116:
               return true;
            case 117:
               return true;
            case 125:
               return true;
            case 127:
               return true;
            case 128:
               return true;
            case 129:
               return true;
            case 133:
               return true;
            case 134:
               return true;
            case 136:
               return true;
            case 137:
               return true;
            case 142:
               return true;
            case 143:
               return true;
            case 144:
               return true;
            case 145:
               return true;
            case 146:
               return true;
            case 147:
               return true;
            case 148:
               return true;
            case 149:
               return true;
            case 153:
               return true;
            case 164:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
            case 12:
            case 13:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 32:
            case 35:
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
            case 49:
            case 52:
            case 57:
            case 59:
            case 97:
            case 98:
            case 101:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 131:
            case 132:
            case 135:
            case 138:
            case 139:
            case 140:
            case 141:
            case 154:
            case 155:
            case 156:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            default:
               return super.isBean(propIndex);
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
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 36:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 50:
               return true;
            case 51:
               return true;
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 56:
               return true;
            case 58:
               return true;
            case 60:
               return true;
            case 61:
               return true;
            case 62:
               return true;
            case 63:
               return true;
            case 64:
               return true;
            case 65:
               return true;
            case 66:
               return true;
            case 67:
               return true;
            case 68:
               return true;
            case 69:
               return true;
            case 70:
               return true;
            case 71:
               return true;
            case 72:
               return true;
            case 73:
               return true;
            case 74:
               return true;
            case 75:
               return true;
            case 76:
               return true;
            case 77:
               return true;
            case 78:
               return true;
            case 79:
               return true;
            case 80:
               return true;
            case 81:
               return true;
            case 82:
               return true;
            case 83:
               return true;
            case 84:
               return true;
            case 85:
               return true;
            case 86:
               return true;
            case 87:
               return true;
            case 88:
               return true;
            case 89:
               return true;
            case 90:
               return true;
            case 91:
               return true;
            case 92:
               return true;
            case 93:
               return true;
            case 94:
               return true;
            case 95:
               return true;
            case 96:
               return true;
            case 99:
               return true;
            case 100:
               return true;
            case 102:
               return true;
            case 103:
               return true;
            case 104:
               return true;
            case 105:
               return true;
            case 106:
               return true;
            case 107:
               return true;
            case 108:
               return true;
            case 109:
               return true;
            case 110:
               return true;
            case 111:
               return true;
            case 112:
               return true;
            case 113:
               return true;
            case 114:
               return true;
            case 115:
               return true;
            case 116:
               return true;
            case 117:
               return true;
            case 119:
               return true;
            case 125:
               return true;
            case 126:
               return true;
            case 127:
               return true;
            case 128:
               return true;
            case 129:
               return true;
            case 130:
               return true;
            case 133:
               return true;
            case 134:
               return true;
            case 136:
               return true;
            case 137:
               return true;
            case 142:
               return true;
            case 143:
               return true;
            case 144:
               return true;
            case 145:
               return true;
            case 146:
               return true;
            case 147:
               return true;
            case 148:
               return true;
            case 149:
               return true;
            case 150:
               return true;
            case 151:
               return true;
            case 152:
               return true;
            case 153:
               return true;
            case 157:
               return true;
            case 158:
               return true;
            case 164:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 131:
               return true;
            case 132:
               return true;
            case 159:
               return true;
            case 161:
               return true;
            case 162:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends ConfigurationPropertiesMBeanImpl.Helper {
      private DomainMBeanImpl bean;

      protected Helper(DomainMBeanImpl bean) {
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
            case 10:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 11:
               return "DomainVersion";
            case 12:
               return "LastModificationTime";
            case 13:
               return "Active";
            case 14:
               return "SecurityConfiguration";
            case 15:
               return "JTA";
            case 16:
               return "JPA";
            case 17:
               return "DeploymentConfiguration";
            case 18:
               return "WTCServers";
            case 19:
               return "Log";
            case 20:
               return "SNMPAgent";
            case 21:
               return "SNMPAgentDeployments";
            case 22:
               return "RootDirectory";
            case 23:
               return "ConsoleEnabled";
            case 24:
               return "JavaServiceConsoleEnabled";
            case 25:
               return "ConsoleContextPath";
            case 26:
               return "ConsoleExtensionDirectory";
            case 27:
               return "AutoConfigurationSaveEnabled";
            case 28:
               return "Servers";
            case 29:
               return "ServerTemplates";
            case 30:
               return "CoherenceServers";
            case 31:
               return "Clusters";
            case 32:
               return "Deployments";
            case 33:
               return "FileT3s";
            case 34:
               return "MessagingBridges";
            case 35:
               return "ProductionModeEnabled";
            case 36:
               return "EmbeddedLDAP";
            case 37:
               return "AdministrationPortEnabled";
            case 38:
               return "AdministrationPort";
            case 39:
               return "ExalogicOptimizationsEnabled";
            case 40:
               return "JavaServiceEnabled";
            case 41:
               return "ArchiveConfigurationCount";
            case 42:
               return "ConfigBackupEnabled";
            case 43:
               return "ConfigurationVersion";
            case 44:
               return "AdministrationMBeanAuditingEnabled";
            case 45:
               return "ConfigurationAuditType";
            case 46:
               return "ClusterConstraintsEnabled";
            case 47:
               return "Applications";
            case 48:
               return "AppDeployments";
            case 49:
               return "InternalAppDeployments";
            case 50:
               return "Libraries";
            case 51:
               return "DomainLibraries";
            case 52:
               return "InternalLibraries";
            case 53:
               return "WSReliableDeliveryPolicies";
            case 54:
               return "Machines";
            case 55:
               return "XMLEntityCaches";
            case 56:
               return "XMLRegistries";
            case 57:
               return "Targets";
            case 58:
               return "JMSServers";
            case 59:
               return "JMSStores";
            case 60:
               return "JMSJDBCStores";
            case 61:
               return "JMSFileStores";
            case 62:
               return "JMSDestinations";
            case 63:
               return "JMSQueues";
            case 64:
               return "JMSTopics";
            case 65:
               return "JMSDistributedQueues";
            case 66:
               return "JMSDistributedTopics";
            case 67:
               return "JMSTemplates";
            case 68:
               return "NetworkChannels";
            case 69:
               return "VirtualHosts";
            case 70:
               return "VirtualTargets";
            case 71:
               return "MigratableTargets";
            case 72:
               return "EJBContainer";
            case 73:
               return "WebAppContainer";
            case 74:
               return "CdiContainer";
            case 75:
               return "JMX";
            case 76:
               return "SelfTuning";
            case 77:
               return "ResourceManagement";
            case 78:
               return "PathServices";
            case 79:
               return "JMSDestinationKeys";
            case 80:
               return "JMSConnectionFactories";
            case 81:
               return "JMSSessionPools";
            case 82:
               return "JMSBridgeDestinations";
            case 83:
               return "BridgeDestinations";
            case 84:
               return "ForeignJMSServers";
            case 85:
               return "ShutdownClasses";
            case 86:
               return "StartupClasses";
            case 87:
               return "SingletonServices";
            case 88:
               return "MailSessions";
            case 89:
               return "JoltConnectionPools";
            case 90:
               return "LogFilters";
            case 91:
               return "FileStores";
            case 92:
               return "ReplicatedStores";
            case 93:
               return "JDBCStores";
            case 94:
               return "JMSSystemResources";
            case 95:
               return "CustomResources";
            case 96:
               return "ForeignJNDIProviders";
            case 97:
               return "AdminServerName";
            case 98:
               return "AdministrationProtocol";
            case 99:
               return "WLDFSystemResources";
            case 100:
               return "JDBCSystemResources";
            case 101:
               return "SystemResources";
            case 102:
               return "SAFAgents";
            case 103:
               return "MigratableRMIServices";
            case 104:
               return "AdminServerMBean";
            case 105:
               return "JMSDistributedQueueMembers";
            case 106:
               return "JMSDistributedTopicMembers";
            case 107:
               return "SNMPTrapDestinations";
            case 108:
               return "SNMPProxies";
            case 109:
               return "SNMPGaugeMonitors";
            case 110:
               return "SNMPStringMonitors";
            case 111:
               return "SNMPCounterMonitors";
            case 112:
               return "SNMPLogFilters";
            case 113:
               return "SNMPAttributeChanges";
            case 114:
               return "WebserviceSecurities";
            case 115:
               return "ForeignJMSConnectionFactories";
            case 116:
               return "ForeignJMSDestinations";
            case 117:
               return "JMSConnectionConsumers";
            case 118:
               return "AutoDeployForSubmodulesEnabled";
            case 119:
               return "AdminConsole";
            case 120:
               return "InternalAppsDeployOnDemandEnabled";
            case 121:
               return "GuardianEnabled";
            case 122:
               return "OCMEnabled";
            case 123:
               return "MsgIdPrefixCompatibilityEnabled";
            case 124:
               return "LogFormatCompatibilityEnabled";
            case 125:
               return "CoherenceClusterSystemResources";
            case 126:
               return "RestfulManagementServices";
            case 127:
               return "SystemComponents";
            case 128:
               return "SystemComponentConfigurations";
            case 129:
               return "OsgiFrameworks";
            case 130:
               return "WebserviceTestpage";
            case 131:
               return "ServerMigrationHistorySize";
            case 132:
               return "ServiceMigrationHistorySize";
            case 133:
               return "CoherenceManagementClusters";
            case 134:
               return "Partitions";
            case 135:
               return "PartitionUriSpace";
            case 136:
               return "ResourceGroups";
            case 137:
               return "ResourceGroupTemplates";
            case 138:
               return "MaxConcurrentNewThreads";
            case 139:
               return "MaxConcurrentLongRunningRequests";
            case 140:
               return "ParallelDeployApplications";
            case 141:
               return "ParallelDeployApplicationModules";
            case 142:
               return "ManagedExecutorServiceTemplates";
            case 143:
               return "ManagedScheduledExecutorServiceTemplates";
            case 144:
               return "ManagedThreadFactoryTemplates";
            case 145:
               return "ManagedExecutorServices";
            case 146:
               return "ManagedScheduledExecutorServices";
            case 147:
               return "ManagedThreadFactories";
            case 148:
               return "PartitionTemplates";
            case 149:
               return "LifecycleManagerEndPoints";
            case 150:
               return "Interceptors";
            case 151:
               return "BatchConfig";
            case 152:
               return "DebugPatches";
            case 153:
               return "PartitionWorkManagers";
            case 154:
               return "DiagnosticContextCompatibilityModeEnabled";
            case 155:
               return "BatchJobsDataSourceJndiName";
            case 156:
               return "BatchJobsExecutorServiceName";
            case 157:
               return "OptionalFeatureDeployment";
            case 158:
               return "LifecycleManagerConfig";
            case 159:
               return "SiteName";
            case 160:
               return "EnableEECompliantClassloadingForEmbeddedAdapters";
            case 161:
               return "DBPassiveMode";
            case 162:
               return "DBPassiveModeGracePeriodSeconds";
            case 163:
               return "InstalledSoftwareVersion";
            case 164:
               return "Callouts";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdminConsole")) {
            return 119;
         } else if (propName.equals("AdminServerMBean")) {
            return 104;
         } else if (propName.equals("AdminServerName")) {
            return 97;
         } else if (propName.equals("AdministrationPort")) {
            return 38;
         } else if (propName.equals("AdministrationProtocol")) {
            return 98;
         } else if (propName.equals("AppDeployments")) {
            return 48;
         } else if (propName.equals("Applications")) {
            return 47;
         } else if (propName.equals("ArchiveConfigurationCount")) {
            return 41;
         } else if (propName.equals("BatchConfig")) {
            return 151;
         } else if (propName.equals("BatchJobsDataSourceJndiName")) {
            return 155;
         } else if (propName.equals("BatchJobsExecutorServiceName")) {
            return 156;
         } else if (propName.equals("BridgeDestinations")) {
            return 83;
         } else if (propName.equals("Callouts")) {
            return 164;
         } else if (propName.equals("CdiContainer")) {
            return 74;
         } else if (propName.equals("Clusters")) {
            return 31;
         } else if (propName.equals("CoherenceClusterSystemResources")) {
            return 125;
         } else if (propName.equals("CoherenceManagementClusters")) {
            return 133;
         } else if (propName.equals("CoherenceServers")) {
            return 30;
         } else if (propName.equals("ConfigurationAuditType")) {
            return 45;
         } else if (propName.equals("ConfigurationVersion")) {
            return 43;
         } else if (propName.equals("ConsoleContextPath")) {
            return 25;
         } else if (propName.equals("ConsoleExtensionDirectory")) {
            return 26;
         } else if (propName.equals("CustomResources")) {
            return 95;
         } else if (propName.equals("DBPassiveModeGracePeriodSeconds")) {
            return 162;
         } else if (propName.equals("DebugPatches")) {
            return 152;
         } else if (propName.equals("DeploymentConfiguration")) {
            return 17;
         } else if (propName.equals("Deployments")) {
            return 32;
         } else if (propName.equals("DomainLibraries")) {
            return 51;
         } else if (propName.equals("DomainVersion")) {
            return 11;
         } else if (propName.equals("EJBContainer")) {
            return 72;
         } else if (propName.equals("EmbeddedLDAP")) {
            return 36;
         } else if (propName.equals("FileStores")) {
            return 91;
         } else if (propName.equals("FileT3s")) {
            return 33;
         } else if (propName.equals("ForeignJMSConnectionFactories")) {
            return 115;
         } else if (propName.equals("ForeignJMSDestinations")) {
            return 116;
         } else if (propName.equals("ForeignJMSServers")) {
            return 84;
         } else if (propName.equals("ForeignJNDIProviders")) {
            return 96;
         } else if (propName.equals("InstalledSoftwareVersion")) {
            return 163;
         } else if (propName.equals("Interceptors")) {
            return 150;
         } else if (propName.equals("InternalAppDeployments")) {
            return 49;
         } else if (propName.equals("InternalLibraries")) {
            return 52;
         } else if (propName.equals("JDBCStores")) {
            return 93;
         } else if (propName.equals("JDBCSystemResources")) {
            return 100;
         } else if (propName.equals("JMSBridgeDestinations")) {
            return 82;
         } else if (propName.equals("JMSConnectionConsumers")) {
            return 117;
         } else if (propName.equals("JMSConnectionFactories")) {
            return 80;
         } else if (propName.equals("JMSDestinationKeys")) {
            return 79;
         } else if (propName.equals("JMSDestinations")) {
            return 62;
         } else if (propName.equals("JMSDistributedQueueMembers")) {
            return 105;
         } else if (propName.equals("JMSDistributedQueues")) {
            return 65;
         } else if (propName.equals("JMSDistributedTopicMembers")) {
            return 106;
         } else if (propName.equals("JMSDistributedTopics")) {
            return 66;
         } else if (propName.equals("JMSFileStores")) {
            return 61;
         } else if (propName.equals("JMSJDBCStores")) {
            return 60;
         } else if (propName.equals("JMSQueues")) {
            return 63;
         } else if (propName.equals("JMSServers")) {
            return 58;
         } else if (propName.equals("JMSSessionPools")) {
            return 81;
         } else if (propName.equals("JMSStores")) {
            return 59;
         } else if (propName.equals("JMSSystemResources")) {
            return 94;
         } else if (propName.equals("JMSTemplates")) {
            return 67;
         } else if (propName.equals("JMSTopics")) {
            return 64;
         } else if (propName.equals("JMX")) {
            return 75;
         } else if (propName.equals("JPA")) {
            return 16;
         } else if (propName.equals("JTA")) {
            return 15;
         } else if (propName.equals("JoltConnectionPools")) {
            return 89;
         } else if (propName.equals("LastModificationTime")) {
            return 12;
         } else if (propName.equals("Libraries")) {
            return 50;
         } else if (propName.equals("LifecycleManagerConfig")) {
            return 158;
         } else if (propName.equals("LifecycleManagerEndPoints")) {
            return 149;
         } else if (propName.equals("Log")) {
            return 19;
         } else if (propName.equals("LogFilters")) {
            return 90;
         } else if (propName.equals("Machines")) {
            return 54;
         } else if (propName.equals("MailSessions")) {
            return 88;
         } else if (propName.equals("ManagedExecutorServiceTemplates")) {
            return 142;
         } else if (propName.equals("ManagedExecutorServices")) {
            return 145;
         } else if (propName.equals("ManagedScheduledExecutorServiceTemplates")) {
            return 143;
         } else if (propName.equals("ManagedScheduledExecutorServices")) {
            return 146;
         } else if (propName.equals("ManagedThreadFactories")) {
            return 147;
         } else if (propName.equals("ManagedThreadFactoryTemplates")) {
            return 144;
         } else if (propName.equals("MaxConcurrentLongRunningRequests")) {
            return 139;
         } else if (propName.equals("MaxConcurrentNewThreads")) {
            return 138;
         } else if (propName.equals("MessagingBridges")) {
            return 34;
         } else if (propName.equals("MigratableRMIServices")) {
            return 103;
         } else if (propName.equals("MigratableTargets")) {
            return 71;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NetworkChannels")) {
            return 68;
         } else if (propName.equals("OptionalFeatureDeployment")) {
            return 157;
         } else if (propName.equals("OsgiFrameworks")) {
            return 129;
         } else if (propName.equals("PartitionTemplates")) {
            return 148;
         } else if (propName.equals("PartitionUriSpace")) {
            return 135;
         } else if (propName.equals("PartitionWorkManagers")) {
            return 153;
         } else if (propName.equals("Partitions")) {
            return 134;
         } else if (propName.equals("PathServices")) {
            return 78;
         } else if (propName.equals("ReplicatedStores")) {
            return 92;
         } else if (propName.equals("ResourceGroupTemplates")) {
            return 137;
         } else if (propName.equals("ResourceGroups")) {
            return 136;
         } else if (propName.equals("ResourceManagement")) {
            return 77;
         } else if (propName.equals("RestfulManagementServices")) {
            return 126;
         } else if (propName.equals("RootDirectory")) {
            return 22;
         } else if (propName.equals("SAFAgents")) {
            return 102;
         } else if (propName.equals("SNMPAgent")) {
            return 20;
         } else if (propName.equals("SNMPAgentDeployments")) {
            return 21;
         } else if (propName.equals("SNMPAttributeChanges")) {
            return 113;
         } else if (propName.equals("SNMPCounterMonitors")) {
            return 111;
         } else if (propName.equals("SNMPGaugeMonitors")) {
            return 109;
         } else if (propName.equals("SNMPLogFilters")) {
            return 112;
         } else if (propName.equals("SNMPProxies")) {
            return 108;
         } else if (propName.equals("SNMPStringMonitors")) {
            return 110;
         } else if (propName.equals("SNMPTrapDestinations")) {
            return 107;
         } else if (propName.equals("SecurityConfiguration")) {
            return 14;
         } else if (propName.equals("SelfTuning")) {
            return 76;
         } else if (propName.equals("ServerMigrationHistorySize")) {
            return 131;
         } else if (propName.equals("ServerTemplates")) {
            return 29;
         } else if (propName.equals("Servers")) {
            return 28;
         } else if (propName.equals("ServiceMigrationHistorySize")) {
            return 132;
         } else if (propName.equals("ShutdownClasses")) {
            return 85;
         } else if (propName.equals("SingletonServices")) {
            return 87;
         } else if (propName.equals("SiteName")) {
            return 159;
         } else if (propName.equals("StartupClasses")) {
            return 86;
         } else if (propName.equals("SystemComponentConfigurations")) {
            return 128;
         } else if (propName.equals("SystemComponents")) {
            return 127;
         } else if (propName.equals("SystemResources")) {
            return 101;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 57;
         } else if (propName.equals("VirtualHosts")) {
            return 69;
         } else if (propName.equals("VirtualTargets")) {
            return 70;
         } else if (propName.equals("WLDFSystemResources")) {
            return 99;
         } else if (propName.equals("WSReliableDeliveryPolicies")) {
            return 53;
         } else if (propName.equals("WTCServers")) {
            return 18;
         } else if (propName.equals("WebAppContainer")) {
            return 73;
         } else if (propName.equals("WebserviceSecurities")) {
            return 114;
         } else if (propName.equals("WebserviceTestpage")) {
            return 130;
         } else if (propName.equals("XMLEntityCaches")) {
            return 55;
         } else if (propName.equals("XMLRegistries")) {
            return 56;
         } else if (propName.equals("Active")) {
            return 13;
         } else if (propName.equals("AdministrationMBeanAuditingEnabled")) {
            return 44;
         } else if (propName.equals("AdministrationPortEnabled")) {
            return 37;
         } else if (propName.equals("AutoConfigurationSaveEnabled")) {
            return 27;
         } else if (propName.equals("AutoDeployForSubmodulesEnabled")) {
            return 118;
         } else if (propName.equals("ClusterConstraintsEnabled")) {
            return 46;
         } else if (propName.equals("ConfigBackupEnabled")) {
            return 42;
         } else if (propName.equals("ConsoleEnabled")) {
            return 23;
         } else if (propName.equals("DBPassiveMode")) {
            return 161;
         } else if (propName.equals("DiagnosticContextCompatibilityModeEnabled")) {
            return 154;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("EnableEECompliantClassloadingForEmbeddedAdapters")) {
            return 160;
         } else if (propName.equals("ExalogicOptimizationsEnabled")) {
            return 39;
         } else if (propName.equals("GuardianEnabled")) {
            return 121;
         } else if (propName.equals("InternalAppsDeployOnDemandEnabled")) {
            return 120;
         } else if (propName.equals("JavaServiceConsoleEnabled")) {
            return 24;
         } else if (propName.equals("JavaServiceEnabled")) {
            return 40;
         } else if (propName.equals("LogFormatCompatibilityEnabled")) {
            return 124;
         } else if (propName.equals("MsgIdPrefixCompatibilityEnabled")) {
            return 123;
         } else if (propName.equals("OCMEnabled")) {
            return 122;
         } else if (propName.equals("ParallelDeployApplicationModules")) {
            return 141;
         } else if (propName.equals("ParallelDeployApplications")) {
            return 140;
         } else {
            return propName.equals("ProductionModeEnabled") ? 35 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAdminConsole() != null) {
            iterators.add(new ArrayIterator(new AdminConsoleMBean[]{this.bean.getAdminConsole()}));
         }

         if (this.bean.getAdminServerMBean() != null) {
            iterators.add(new ArrayIterator(new AdminServerMBean[]{this.bean.getAdminServerMBean()}));
         }

         iterators.add(new ArrayIterator(this.bean.getAppDeployments()));
         iterators.add(new ArrayIterator(this.bean.getApplications()));
         if (this.bean.getBatchConfig() != null) {
            iterators.add(new ArrayIterator(new BatchConfigMBean[]{this.bean.getBatchConfig()}));
         }

         iterators.add(new ArrayIterator(this.bean.getBridgeDestinations()));
         iterators.add(new ArrayIterator(this.bean.getCallouts()));
         if (this.bean.getCdiContainer() != null) {
            iterators.add(new ArrayIterator(new CdiContainerMBean[]{this.bean.getCdiContainer()}));
         }

         iterators.add(new ArrayIterator(this.bean.getClusters()));
         iterators.add(new ArrayIterator(this.bean.getCoherenceClusterSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getCoherenceManagementClusters()));
         iterators.add(new ArrayIterator(this.bean.getCoherenceServers()));
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         iterators.add(new ArrayIterator(this.bean.getCustomResources()));
         if (this.bean.getDebugPatches() != null) {
            iterators.add(new ArrayIterator(new DebugPatchesMBean[]{this.bean.getDebugPatches()}));
         }

         if (this.bean.getDeploymentConfiguration() != null) {
            iterators.add(new ArrayIterator(new DeploymentConfigurationMBean[]{this.bean.getDeploymentConfiguration()}));
         }

         iterators.add(new ArrayIterator(this.bean.getDomainLibraries()));
         if (this.bean.getEJBContainer() != null) {
            iterators.add(new ArrayIterator(new EJBContainerMBean[]{this.bean.getEJBContainer()}));
         }

         if (this.bean.getEmbeddedLDAP() != null) {
            iterators.add(new ArrayIterator(new EmbeddedLDAPMBean[]{this.bean.getEmbeddedLDAP()}));
         }

         iterators.add(new ArrayIterator(this.bean.getFileStores()));
         iterators.add(new ArrayIterator(this.bean.getFileT3s()));
         iterators.add(new ArrayIterator(this.bean.getForeignJMSConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getForeignJMSDestinations()));
         iterators.add(new ArrayIterator(this.bean.getForeignJMSServers()));
         iterators.add(new ArrayIterator(this.bean.getForeignJNDIProviders()));
         if (this.bean.getInterceptors() != null) {
            iterators.add(new ArrayIterator(new InterceptorsMBean[]{this.bean.getInterceptors()}));
         }

         iterators.add(new ArrayIterator(this.bean.getJDBCStores()));
         iterators.add(new ArrayIterator(this.bean.getJDBCSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getJMSBridgeDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJMSConnectionConsumers()));
         iterators.add(new ArrayIterator(this.bean.getJMSConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getJMSDestinationKeys()));
         iterators.add(new ArrayIterator(this.bean.getJMSDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJMSDistributedQueueMembers()));
         iterators.add(new ArrayIterator(this.bean.getJMSDistributedQueues()));
         iterators.add(new ArrayIterator(this.bean.getJMSDistributedTopicMembers()));
         iterators.add(new ArrayIterator(this.bean.getJMSDistributedTopics()));
         iterators.add(new ArrayIterator(this.bean.getJMSFileStores()));
         iterators.add(new ArrayIterator(this.bean.getJMSJDBCStores()));
         iterators.add(new ArrayIterator(this.bean.getJMSQueues()));
         iterators.add(new ArrayIterator(this.bean.getJMSServers()));
         iterators.add(new ArrayIterator(this.bean.getJMSSessionPools()));
         iterators.add(new ArrayIterator(this.bean.getJMSSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getJMSTemplates()));
         iterators.add(new ArrayIterator(this.bean.getJMSTopics()));
         if (this.bean.getJMX() != null) {
            iterators.add(new ArrayIterator(new JMXMBean[]{this.bean.getJMX()}));
         }

         if (this.bean.getJPA() != null) {
            iterators.add(new ArrayIterator(new JPAMBean[]{this.bean.getJPA()}));
         }

         if (this.bean.getJTA() != null) {
            iterators.add(new ArrayIterator(new JTAMBean[]{this.bean.getJTA()}));
         }

         iterators.add(new ArrayIterator(this.bean.getJoltConnectionPools()));
         iterators.add(new ArrayIterator(this.bean.getLibraries()));
         if (this.bean.getLifecycleManagerConfig() != null) {
            iterators.add(new ArrayIterator(new LifecycleManagerConfigMBean[]{this.bean.getLifecycleManagerConfig()}));
         }

         iterators.add(new ArrayIterator(this.bean.getLifecycleManagerEndPoints()));
         if (this.bean.getLog() != null) {
            iterators.add(new ArrayIterator(new LogMBean[]{this.bean.getLog()}));
         }

         iterators.add(new ArrayIterator(this.bean.getLogFilters()));
         iterators.add(new ArrayIterator(this.bean.getMachines()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServiceTemplates()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServiceTemplates()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactories()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactoryTemplates()));
         iterators.add(new ArrayIterator(this.bean.getMessagingBridges()));
         iterators.add(new ArrayIterator(this.bean.getMigratableRMIServices()));
         iterators.add(new ArrayIterator(this.bean.getMigratableTargets()));
         iterators.add(new ArrayIterator(this.bean.getNetworkChannels()));
         if (this.bean.getOptionalFeatureDeployment() != null) {
            iterators.add(new ArrayIterator(new OptionalFeatureDeploymentMBean[]{this.bean.getOptionalFeatureDeployment()}));
         }

         iterators.add(new ArrayIterator(this.bean.getOsgiFrameworks()));
         iterators.add(new ArrayIterator(this.bean.getPartitionTemplates()));
         iterators.add(new ArrayIterator(this.bean.getPartitionWorkManagers()));
         iterators.add(new ArrayIterator(this.bean.getPartitions()));
         iterators.add(new ArrayIterator(this.bean.getPathServices()));
         iterators.add(new ArrayIterator(this.bean.getReplicatedStores()));
         iterators.add(new ArrayIterator(this.bean.getResourceGroupTemplates()));
         iterators.add(new ArrayIterator(this.bean.getResourceGroups()));
         if (this.bean.getResourceManagement() != null) {
            iterators.add(new ArrayIterator(new ResourceManagementMBean[]{this.bean.getResourceManagement()}));
         }

         if (this.bean.getRestfulManagementServices() != null) {
            iterators.add(new ArrayIterator(new RestfulManagementServicesMBean[]{this.bean.getRestfulManagementServices()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSAFAgents()));
         if (this.bean.getSNMPAgent() != null) {
            iterators.add(new ArrayIterator(new SNMPAgentMBean[]{this.bean.getSNMPAgent()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSNMPAgentDeployments()));
         iterators.add(new ArrayIterator(this.bean.getSNMPAttributeChanges()));
         iterators.add(new ArrayIterator(this.bean.getSNMPCounterMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPGaugeMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPLogFilters()));
         iterators.add(new ArrayIterator(this.bean.getSNMPProxies()));
         iterators.add(new ArrayIterator(this.bean.getSNMPStringMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPTrapDestinations()));
         if (this.bean.getSecurityConfiguration() != null) {
            iterators.add(new ArrayIterator(new SecurityConfigurationMBean[]{this.bean.getSecurityConfiguration()}));
         }

         if (this.bean.getSelfTuning() != null) {
            iterators.add(new ArrayIterator(new SelfTuningMBean[]{this.bean.getSelfTuning()}));
         }

         iterators.add(new ArrayIterator(this.bean.getServerTemplates()));
         iterators.add(new ArrayIterator(this.bean.getServers()));
         iterators.add(new ArrayIterator(this.bean.getShutdownClasses()));
         iterators.add(new ArrayIterator(this.bean.getSingletonServices()));
         iterators.add(new ArrayIterator(this.bean.getStartupClasses()));
         iterators.add(new ArrayIterator(this.bean.getSystemComponentConfigurations()));
         iterators.add(new ArrayIterator(this.bean.getSystemComponents()));
         iterators.add(new ArrayIterator(this.bean.getVirtualHosts()));
         iterators.add(new ArrayIterator(this.bean.getVirtualTargets()));
         iterators.add(new ArrayIterator(this.bean.getWLDFSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getWSReliableDeliveryPolicies()));
         iterators.add(new ArrayIterator(this.bean.getWTCServers()));
         if (this.bean.getWebAppContainer() != null) {
            iterators.add(new ArrayIterator(new WebAppContainerMBean[]{this.bean.getWebAppContainer()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWebserviceSecurities()));
         if (this.bean.getWebserviceTestpage() != null) {
            iterators.add(new ArrayIterator(new WebserviceTestpageMBean[]{this.bean.getWebserviceTestpage()}));
         }

         iterators.add(new ArrayIterator(this.bean.getXMLEntityCaches()));
         iterators.add(new ArrayIterator(this.bean.getXMLRegistries()));
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
            childValue = this.computeChildHashValue(this.bean.getAdminConsole());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getAdminServerMBean());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAdminServerNameSet()) {
               buf.append("AdminServerName");
               buf.append(String.valueOf(this.bean.getAdminServerName()));
            }

            if (this.bean.isAdministrationPortSet()) {
               buf.append("AdministrationPort");
               buf.append(String.valueOf(this.bean.getAdministrationPort()));
            }

            if (this.bean.isAdministrationProtocolSet()) {
               buf.append("AdministrationProtocol");
               buf.append(String.valueOf(this.bean.getAdministrationProtocol()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getAppDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAppDeployments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getApplications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getApplications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isArchiveConfigurationCountSet()) {
               buf.append("ArchiveConfigurationCount");
               buf.append(String.valueOf(this.bean.getArchiveConfigurationCount()));
            }

            childValue = this.computeChildHashValue(this.bean.getBatchConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isBatchJobsDataSourceJndiNameSet()) {
               buf.append("BatchJobsDataSourceJndiName");
               buf.append(String.valueOf(this.bean.getBatchJobsDataSourceJndiName()));
            }

            if (this.bean.isBatchJobsExecutorServiceNameSet()) {
               buf.append("BatchJobsExecutorServiceName");
               buf.append(String.valueOf(this.bean.getBatchJobsExecutorServiceName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getBridgeDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getBridgeDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCallouts().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCallouts()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCdiContainer());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getClusters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getClusters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCoherenceClusterSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceClusterSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCoherenceManagementClusters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceManagementClusters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCoherenceServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConfigurationAuditTypeSet()) {
               buf.append("ConfigurationAuditType");
               buf.append(String.valueOf(this.bean.getConfigurationAuditType()));
            }

            if (this.bean.isConfigurationVersionSet()) {
               buf.append("ConfigurationVersion");
               buf.append(String.valueOf(this.bean.getConfigurationVersion()));
            }

            if (this.bean.isConsoleContextPathSet()) {
               buf.append("ConsoleContextPath");
               buf.append(String.valueOf(this.bean.getConsoleContextPath()));
            }

            if (this.bean.isConsoleExtensionDirectorySet()) {
               buf.append("ConsoleExtensionDirectory");
               buf.append(String.valueOf(this.bean.getConsoleExtensionDirectory()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCustomResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCustomResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDBPassiveModeGracePeriodSecondsSet()) {
               buf.append("DBPassiveModeGracePeriodSeconds");
               buf.append(String.valueOf(this.bean.getDBPassiveModeGracePeriodSeconds()));
            }

            childValue = this.computeChildHashValue(this.bean.getDebugPatches());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDeploymentConfiguration());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDeploymentsSet()) {
               buf.append("Deployments");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeployments())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDomainLibraries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDomainLibraries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDomainVersionSet()) {
               buf.append("DomainVersion");
               buf.append(String.valueOf(this.bean.getDomainVersion()));
            }

            childValue = this.computeChildHashValue(this.bean.getEJBContainer());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getEmbeddedLDAP());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFileStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFileStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFileT3s().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFileT3s()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJMSConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJMSConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJMSDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJMSDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJMSServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJMSServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJNDIProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJNDIProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isInstalledSoftwareVersionSet()) {
               buf.append("InstalledSoftwareVersion");
               buf.append(String.valueOf(this.bean.getInstalledSoftwareVersion()));
            }

            childValue = this.computeChildHashValue(this.bean.getInterceptors());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isInternalAppDeploymentsSet()) {
               buf.append("InternalAppDeployments");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getInternalAppDeployments())));
            }

            if (this.bean.isInternalLibrariesSet()) {
               buf.append("InternalLibraries");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getInternalLibraries())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJDBCStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJDBCSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSBridgeDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSBridgeDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSConnectionConsumers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSConnectionConsumers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSDestinationKeys().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDestinationKeys()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSDistributedQueueMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDistributedQueueMembers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSDistributedQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDistributedQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSDistributedTopicMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDistributedTopicMembers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSDistributedTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDistributedTopics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSFileStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSFileStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSJDBCStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSJDBCStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSSessionPools().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSSessionPools()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJMSStoresSet()) {
               buf.append("JMSStores");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJMSStores())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSTopics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJPA());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJTA());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJoltConnectionPools().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJoltConnectionPools()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLastModificationTimeSet()) {
               buf.append("LastModificationTime");
               buf.append(String.valueOf(this.bean.getLastModificationTime()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLibraries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLibraries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLifecycleManagerConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLifecycleManagerEndPoints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLifecycleManagerEndPoints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLog());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLogFilters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLogFilters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMachines().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMachines()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedExecutorServiceTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedExecutorServiceTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedScheduledExecutorServiceTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedScheduledExecutorServiceTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedScheduledExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedScheduledExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedThreadFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedThreadFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedThreadFactoryTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedThreadFactoryTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               buf.append("MaxConcurrentLongRunningRequests");
               buf.append(String.valueOf(this.bean.getMaxConcurrentLongRunningRequests()));
            }

            if (this.bean.isMaxConcurrentNewThreadsSet()) {
               buf.append("MaxConcurrentNewThreads");
               buf.append(String.valueOf(this.bean.getMaxConcurrentNewThreads()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessagingBridges().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessagingBridges()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMigratableRMIServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMigratableRMIServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMigratableTargets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMigratableTargets()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getNetworkChannels().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getNetworkChannels()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getOptionalFeatureDeployment());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getOsgiFrameworks().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOsgiFrameworks()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPartitionTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPartitionTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPartitionUriSpaceSet()) {
               buf.append("PartitionUriSpace");
               buf.append(String.valueOf(this.bean.getPartitionUriSpace()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPartitionWorkManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPartitionWorkManagers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPartitions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPartitions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPathServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPathServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getReplicatedStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getReplicatedStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceGroupTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceGroupTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceGroups().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceGroups()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getResourceManagement());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getRestfulManagementServices());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRootDirectorySet()) {
               buf.append("RootDirectory");
               buf.append(String.valueOf(this.bean.getRootDirectory()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSAFAgents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFAgents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSNMPAgent());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPAgentDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPAgentDeployments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPAttributeChanges().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPAttributeChanges()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPCounterMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPCounterMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPGaugeMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPGaugeMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPLogFilters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPLogFilters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPProxies().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPProxies()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPStringMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPStringMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPTrapDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPTrapDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurityConfiguration());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSelfTuning());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServerMigrationHistorySizeSet()) {
               buf.append("ServerMigrationHistorySize");
               buf.append(String.valueOf(this.bean.getServerMigrationHistorySize()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServerTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServerTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServiceMigrationHistorySizeSet()) {
               buf.append("ServiceMigrationHistorySize");
               buf.append(String.valueOf(this.bean.getServiceMigrationHistorySize()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getShutdownClasses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getShutdownClasses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSingletonServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSingletonServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSiteNameSet()) {
               buf.append("SiteName");
               buf.append(String.valueOf(this.bean.getSiteName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getStartupClasses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getStartupClasses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSystemComponentConfigurations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSystemComponentConfigurations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSystemComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSystemComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSystemResourcesSet()) {
               buf.append("SystemResources");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSystemResources())));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getVirtualHosts().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getVirtualHosts()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getVirtualTargets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getVirtualTargets()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWLDFSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWLDFSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWSReliableDeliveryPolicies().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWSReliableDeliveryPolicies()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWTCServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebAppContainer());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWebserviceSecurities().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebserviceSecurities()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebserviceTestpage());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getXMLEntityCaches().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getXMLEntityCaches()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getXMLRegistries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getXMLRegistries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isActiveSet()) {
               buf.append("Active");
               buf.append(String.valueOf(this.bean.isActive()));
            }

            if (this.bean.isAdministrationMBeanAuditingEnabledSet()) {
               buf.append("AdministrationMBeanAuditingEnabled");
               buf.append(String.valueOf(this.bean.isAdministrationMBeanAuditingEnabled()));
            }

            if (this.bean.isAdministrationPortEnabledSet()) {
               buf.append("AdministrationPortEnabled");
               buf.append(String.valueOf(this.bean.isAdministrationPortEnabled()));
            }

            if (this.bean.isAutoConfigurationSaveEnabledSet()) {
               buf.append("AutoConfigurationSaveEnabled");
               buf.append(String.valueOf(this.bean.isAutoConfigurationSaveEnabled()));
            }

            if (this.bean.isAutoDeployForSubmodulesEnabledSet()) {
               buf.append("AutoDeployForSubmodulesEnabled");
               buf.append(String.valueOf(this.bean.isAutoDeployForSubmodulesEnabled()));
            }

            if (this.bean.isClusterConstraintsEnabledSet()) {
               buf.append("ClusterConstraintsEnabled");
               buf.append(String.valueOf(this.bean.isClusterConstraintsEnabled()));
            }

            if (this.bean.isConfigBackupEnabledSet()) {
               buf.append("ConfigBackupEnabled");
               buf.append(String.valueOf(this.bean.isConfigBackupEnabled()));
            }

            if (this.bean.isConsoleEnabledSet()) {
               buf.append("ConsoleEnabled");
               buf.append(String.valueOf(this.bean.isConsoleEnabled()));
            }

            if (this.bean.isDBPassiveModeSet()) {
               buf.append("DBPassiveMode");
               buf.append(String.valueOf(this.bean.isDBPassiveMode()));
            }

            if (this.bean.isDiagnosticContextCompatibilityModeEnabledSet()) {
               buf.append("DiagnosticContextCompatibilityModeEnabled");
               buf.append(String.valueOf(this.bean.isDiagnosticContextCompatibilityModeEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEnableEECompliantClassloadingForEmbeddedAdaptersSet()) {
               buf.append("EnableEECompliantClassloadingForEmbeddedAdapters");
               buf.append(String.valueOf(this.bean.isEnableEECompliantClassloadingForEmbeddedAdapters()));
            }

            if (this.bean.isExalogicOptimizationsEnabledSet()) {
               buf.append("ExalogicOptimizationsEnabled");
               buf.append(String.valueOf(this.bean.isExalogicOptimizationsEnabled()));
            }

            if (this.bean.isGuardianEnabledSet()) {
               buf.append("GuardianEnabled");
               buf.append(String.valueOf(this.bean.isGuardianEnabled()));
            }

            if (this.bean.isInternalAppsDeployOnDemandEnabledSet()) {
               buf.append("InternalAppsDeployOnDemandEnabled");
               buf.append(String.valueOf(this.bean.isInternalAppsDeployOnDemandEnabled()));
            }

            if (this.bean.isJavaServiceConsoleEnabledSet()) {
               buf.append("JavaServiceConsoleEnabled");
               buf.append(String.valueOf(this.bean.isJavaServiceConsoleEnabled()));
            }

            if (this.bean.isJavaServiceEnabledSet()) {
               buf.append("JavaServiceEnabled");
               buf.append(String.valueOf(this.bean.isJavaServiceEnabled()));
            }

            if (this.bean.isLogFormatCompatibilityEnabledSet()) {
               buf.append("LogFormatCompatibilityEnabled");
               buf.append(String.valueOf(this.bean.isLogFormatCompatibilityEnabled()));
            }

            if (this.bean.isMsgIdPrefixCompatibilityEnabledSet()) {
               buf.append("MsgIdPrefixCompatibilityEnabled");
               buf.append(String.valueOf(this.bean.isMsgIdPrefixCompatibilityEnabled()));
            }

            if (this.bean.isOCMEnabledSet()) {
               buf.append("OCMEnabled");
               buf.append(String.valueOf(this.bean.isOCMEnabled()));
            }

            if (this.bean.isParallelDeployApplicationModulesSet()) {
               buf.append("ParallelDeployApplicationModules");
               buf.append(String.valueOf(this.bean.isParallelDeployApplicationModules()));
            }

            if (this.bean.isParallelDeployApplicationsSet()) {
               buf.append("ParallelDeployApplications");
               buf.append(String.valueOf(this.bean.isParallelDeployApplications()));
            }

            if (this.bean.isProductionModeEnabledSet()) {
               buf.append("ProductionModeEnabled");
               buf.append(String.valueOf(this.bean.isProductionModeEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            DomainMBeanImpl otherTyped = (DomainMBeanImpl)other;
            this.computeSubDiff("AdminConsole", this.bean.getAdminConsole(), otherTyped.getAdminConsole());
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("AdminServerMBean", this.bean.getAdminServerMBean(), otherTyped.getAdminServerMBean(), false);
            }

            this.computeDiff("AdminServerName", this.bean.getAdminServerName(), otherTyped.getAdminServerName(), false);
            this.computeDiff("AdministrationPort", this.bean.getAdministrationPort(), otherTyped.getAdministrationPort(), true);
            this.computeDiff("AdministrationProtocol", this.bean.getAdministrationProtocol(), otherTyped.getAdministrationProtocol(), false);
            this.computeChildDiff("AppDeployments", this.bean.getAppDeployments(), otherTyped.getAppDeployments(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("Applications", this.bean.getApplications(), otherTyped.getApplications(), true);
            }

            this.computeDiff("ArchiveConfigurationCount", this.bean.getArchiveConfigurationCount(), otherTyped.getArchiveConfigurationCount(), false);
            this.computeSubDiff("BatchConfig", this.bean.getBatchConfig(), otherTyped.getBatchConfig());
            this.computeDiff("BatchJobsDataSourceJndiName", this.bean.getBatchJobsDataSourceJndiName(), otherTyped.getBatchJobsDataSourceJndiName(), true);
            this.computeDiff("BatchJobsExecutorServiceName", this.bean.getBatchJobsExecutorServiceName(), otherTyped.getBatchJobsExecutorServiceName(), true);
            this.computeChildDiff("BridgeDestinations", this.bean.getBridgeDestinations(), otherTyped.getBridgeDestinations(), false);
            this.computeChildDiff("Callouts", this.bean.getCallouts(), otherTyped.getCallouts(), true, true);
            this.computeSubDiff("CdiContainer", this.bean.getCdiContainer(), otherTyped.getCdiContainer());
            this.computeChildDiff("Clusters", this.bean.getClusters(), otherTyped.getClusters(), true);
            this.computeChildDiff("CoherenceClusterSystemResources", this.bean.getCoherenceClusterSystemResources(), otherTyped.getCoherenceClusterSystemResources(), true);
            this.computeChildDiff("CoherenceManagementClusters", this.bean.getCoherenceManagementClusters(), otherTyped.getCoherenceManagementClusters(), true);
            this.computeChildDiff("CoherenceServers", this.bean.getCoherenceServers(), otherTyped.getCoherenceServers(), true);
            this.computeDiff("ConfigurationAuditType", this.bean.getConfigurationAuditType(), otherTyped.getConfigurationAuditType(), true);
            this.computeDiff("ConfigurationVersion", this.bean.getConfigurationVersion(), otherTyped.getConfigurationVersion(), true);
            this.computeDiff("ConsoleContextPath", this.bean.getConsoleContextPath(), otherTyped.getConsoleContextPath(), false);
            this.computeDiff("ConsoleExtensionDirectory", this.bean.getConsoleExtensionDirectory(), otherTyped.getConsoleExtensionDirectory(), false);
            this.computeChildDiff("CustomResources", this.bean.getCustomResources(), otherTyped.getCustomResources(), true);
            this.computeDiff("DBPassiveModeGracePeriodSeconds", this.bean.getDBPassiveModeGracePeriodSeconds(), otherTyped.getDBPassiveModeGracePeriodSeconds(), true);
            this.computeSubDiff("DebugPatches", this.bean.getDebugPatches(), otherTyped.getDebugPatches());
            this.computeSubDiff("DeploymentConfiguration", this.bean.getDeploymentConfiguration(), otherTyped.getDeploymentConfiguration());
            this.computeChildDiff("DomainLibraries", this.bean.getDomainLibraries(), otherTyped.getDomainLibraries(), true);
            this.computeDiff("DomainVersion", this.bean.getDomainVersion(), otherTyped.getDomainVersion(), true);
            this.computeChildDiff("EJBContainer", this.bean.getEJBContainer(), otherTyped.getEJBContainer(), false);
            this.computeSubDiff("EmbeddedLDAP", this.bean.getEmbeddedLDAP(), otherTyped.getEmbeddedLDAP());
            this.computeChildDiff("FileStores", this.bean.getFileStores(), otherTyped.getFileStores(), true);
            this.computeChildDiff("FileT3s", this.bean.getFileT3s(), otherTyped.getFileT3s(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ForeignJMSConnectionFactories", this.bean.getForeignJMSConnectionFactories(), otherTyped.getForeignJMSConnectionFactories(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ForeignJMSDestinations", this.bean.getForeignJMSDestinations(), otherTyped.getForeignJMSDestinations(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ForeignJMSServers", this.bean.getForeignJMSServers(), otherTyped.getForeignJMSServers(), false);
            }

            this.computeChildDiff("ForeignJNDIProviders", this.bean.getForeignJNDIProviders(), otherTyped.getForeignJNDIProviders(), true);
            this.computeDiff("InstalledSoftwareVersion", this.bean.getInstalledSoftwareVersion(), otherTyped.getInstalledSoftwareVersion(), true);
            this.computeSubDiff("Interceptors", this.bean.getInterceptors(), otherTyped.getInterceptors());
            this.computeChildDiff("JDBCStores", this.bean.getJDBCStores(), otherTyped.getJDBCStores(), true);
            this.computeChildDiff("JDBCSystemResources", this.bean.getJDBCSystemResources(), otherTyped.getJDBCSystemResources(), true);
            this.computeChildDiff("JMSBridgeDestinations", this.bean.getJMSBridgeDestinations(), otherTyped.getJMSBridgeDestinations(), true);
            this.computeChildDiff("JMSConnectionConsumers", this.bean.getJMSConnectionConsumers(), otherTyped.getJMSConnectionConsumers(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSConnectionFactories", this.bean.getJMSConnectionFactories(), otherTyped.getJMSConnectionFactories(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDestinationKeys", this.bean.getJMSDestinationKeys(), otherTyped.getJMSDestinationKeys(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDestinations", this.bean.getJMSDestinations(), otherTyped.getJMSDestinations(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDistributedQueueMembers", this.bean.getJMSDistributedQueueMembers(), otherTyped.getJMSDistributedQueueMembers(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDistributedQueues", this.bean.getJMSDistributedQueues(), otherTyped.getJMSDistributedQueues(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDistributedTopicMembers", this.bean.getJMSDistributedTopicMembers(), otherTyped.getJMSDistributedTopicMembers(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDistributedTopics", this.bean.getJMSDistributedTopics(), otherTyped.getJMSDistributedTopics(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSFileStores", this.bean.getJMSFileStores(), otherTyped.getJMSFileStores(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSJDBCStores", this.bean.getJMSJDBCStores(), otherTyped.getJMSJDBCStores(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSQueues", this.bean.getJMSQueues(), otherTyped.getJMSQueues(), false);
            }

            this.computeChildDiff("JMSServers", this.bean.getJMSServers(), otherTyped.getJMSServers(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSSessionPools", this.bean.getJMSSessionPools(), otherTyped.getJMSSessionPools(), false);
            }

            this.computeChildDiff("JMSSystemResources", this.bean.getJMSSystemResources(), otherTyped.getJMSSystemResources(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSTemplates", this.bean.getJMSTemplates(), otherTyped.getJMSTemplates(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSTopics", this.bean.getJMSTopics(), otherTyped.getJMSTopics(), false);
            }

            this.computeSubDiff("JMX", this.bean.getJMX(), otherTyped.getJMX());
            this.computeSubDiff("JPA", this.bean.getJPA(), otherTyped.getJPA());
            this.computeSubDiff("JTA", this.bean.getJTA(), otherTyped.getJTA());
            this.computeChildDiff("JoltConnectionPools", this.bean.getJoltConnectionPools(), otherTyped.getJoltConnectionPools(), false);
            this.computeDiff("LastModificationTime", this.bean.getLastModificationTime(), otherTyped.getLastModificationTime(), false);
            this.computeChildDiff("Libraries", this.bean.getLibraries(), otherTyped.getLibraries(), true);
            this.computeSubDiff("LifecycleManagerConfig", this.bean.getLifecycleManagerConfig(), otherTyped.getLifecycleManagerConfig());
            this.computeChildDiff("LifecycleManagerEndPoints", this.bean.getLifecycleManagerEndPoints(), otherTyped.getLifecycleManagerEndPoints(), true);
            this.computeSubDiff("Log", this.bean.getLog(), otherTyped.getLog());
            this.computeChildDiff("LogFilters", this.bean.getLogFilters(), otherTyped.getLogFilters(), true);
            this.computeChildDiff("Machines", this.bean.getMachines(), otherTyped.getMachines(), true);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), true);
            this.computeChildDiff("ManagedExecutorServiceTemplates", this.bean.getManagedExecutorServiceTemplates(), otherTyped.getManagedExecutorServiceTemplates(), true);
            this.computeChildDiff("ManagedExecutorServices", this.bean.getManagedExecutorServices(), otherTyped.getManagedExecutorServices(), true);
            this.computeChildDiff("ManagedScheduledExecutorServiceTemplates", this.bean.getManagedScheduledExecutorServiceTemplates(), otherTyped.getManagedScheduledExecutorServiceTemplates(), true);
            this.computeChildDiff("ManagedScheduledExecutorServices", this.bean.getManagedScheduledExecutorServices(), otherTyped.getManagedScheduledExecutorServices(), true);
            this.computeChildDiff("ManagedThreadFactories", this.bean.getManagedThreadFactories(), otherTyped.getManagedThreadFactories(), true);
            this.computeChildDiff("ManagedThreadFactoryTemplates", this.bean.getManagedThreadFactoryTemplates(), otherTyped.getManagedThreadFactoryTemplates(), true);
            this.computeDiff("MaxConcurrentLongRunningRequests", this.bean.getMaxConcurrentLongRunningRequests(), otherTyped.getMaxConcurrentLongRunningRequests(), true);
            this.computeDiff("MaxConcurrentNewThreads", this.bean.getMaxConcurrentNewThreads(), otherTyped.getMaxConcurrentNewThreads(), true);
            this.computeChildDiff("MessagingBridges", this.bean.getMessagingBridges(), otherTyped.getMessagingBridges(), true);
            this.computeChildDiff("MigratableRMIServices", this.bean.getMigratableRMIServices(), otherTyped.getMigratableRMIServices(), true);
            this.computeChildDiff("MigratableTargets", this.bean.getMigratableTargets(), otherTyped.getMigratableTargets(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("NetworkChannels", this.bean.getNetworkChannels(), otherTyped.getNetworkChannels(), false);
            }

            this.computeSubDiff("OptionalFeatureDeployment", this.bean.getOptionalFeatureDeployment(), otherTyped.getOptionalFeatureDeployment());
            this.computeChildDiff("OsgiFrameworks", this.bean.getOsgiFrameworks(), otherTyped.getOsgiFrameworks(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("PartitionTemplates", this.bean.getPartitionTemplates(), otherTyped.getPartitionTemplates(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("PartitionUriSpace", this.bean.getPartitionUriSpace(), otherTyped.getPartitionUriSpace(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("PartitionWorkManagers", this.bean.getPartitionWorkManagers(), otherTyped.getPartitionWorkManagers(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("Partitions", this.bean.getPartitions(), otherTyped.getPartitions(), true);
            }

            this.computeChildDiff("PathServices", this.bean.getPathServices(), otherTyped.getPathServices(), true);
            this.computeChildDiff("ReplicatedStores", this.bean.getReplicatedStores(), otherTyped.getReplicatedStores(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ResourceGroupTemplates", this.bean.getResourceGroupTemplates(), otherTyped.getResourceGroupTemplates(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ResourceGroups", this.bean.getResourceGroups(), otherTyped.getResourceGroups(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeSubDiff("ResourceManagement", this.bean.getResourceManagement(), otherTyped.getResourceManagement());
            }

            this.computeSubDiff("RestfulManagementServices", this.bean.getRestfulManagementServices(), otherTyped.getRestfulManagementServices());
            this.computeDiff("RootDirectory", this.bean.getRootDirectory(), otherTyped.getRootDirectory(), false);
            this.computeChildDiff("SAFAgents", this.bean.getSAFAgents(), otherTyped.getSAFAgents(), true);
            this.computeSubDiff("SNMPAgent", this.bean.getSNMPAgent(), otherTyped.getSNMPAgent());
            this.computeChildDiff("SNMPAgentDeployments", this.bean.getSNMPAgentDeployments(), otherTyped.getSNMPAgentDeployments(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPAttributeChanges", this.bean.getSNMPAttributeChanges(), otherTyped.getSNMPAttributeChanges(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPCounterMonitors", this.bean.getSNMPCounterMonitors(), otherTyped.getSNMPCounterMonitors(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPGaugeMonitors", this.bean.getSNMPGaugeMonitors(), otherTyped.getSNMPGaugeMonitors(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPLogFilters", this.bean.getSNMPLogFilters(), otherTyped.getSNMPLogFilters(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPProxies", this.bean.getSNMPProxies(), otherTyped.getSNMPProxies(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPStringMonitors", this.bean.getSNMPStringMonitors(), otherTyped.getSNMPStringMonitors(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("SNMPTrapDestinations", this.bean.getSNMPTrapDestinations(), otherTyped.getSNMPTrapDestinations(), false);
            }

            this.computeSubDiff("SecurityConfiguration", this.bean.getSecurityConfiguration(), otherTyped.getSecurityConfiguration());
            this.computeSubDiff("SelfTuning", this.bean.getSelfTuning(), otherTyped.getSelfTuning());
            this.computeDiff("ServerMigrationHistorySize", this.bean.getServerMigrationHistorySize(), otherTyped.getServerMigrationHistorySize(), true);
            this.computeChildDiff("ServerTemplates", this.bean.getServerTemplates(), otherTyped.getServerTemplates(), true);
            this.computeChildDiff("Servers", this.bean.getServers(), otherTyped.getServers(), true);
            this.computeDiff("ServiceMigrationHistorySize", this.bean.getServiceMigrationHistorySize(), otherTyped.getServiceMigrationHistorySize(), true);
            this.computeChildDiff("ShutdownClasses", this.bean.getShutdownClasses(), otherTyped.getShutdownClasses(), true);
            this.computeChildDiff("SingletonServices", this.bean.getSingletonServices(), otherTyped.getSingletonServices(), true);
            this.computeDiff("SiteName", this.bean.getSiteName(), otherTyped.getSiteName(), true);
            this.computeChildDiff("StartupClasses", this.bean.getStartupClasses(), otherTyped.getStartupClasses(), true);
            this.computeChildDiff("SystemComponentConfigurations", this.bean.getSystemComponentConfigurations(), otherTyped.getSystemComponentConfigurations(), true);
            this.computeChildDiff("SystemComponents", this.bean.getSystemComponents(), otherTyped.getSystemComponents(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeChildDiff("VirtualHosts", this.bean.getVirtualHosts(), otherTyped.getVirtualHosts(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("VirtualTargets", this.bean.getVirtualTargets(), otherTyped.getVirtualTargets(), true);
            }

            this.computeChildDiff("WLDFSystemResources", this.bean.getWLDFSystemResources(), otherTyped.getWLDFSystemResources(), true);
            this.computeChildDiff("WSReliableDeliveryPolicies", this.bean.getWSReliableDeliveryPolicies(), otherTyped.getWSReliableDeliveryPolicies(), true);
            this.computeChildDiff("WTCServers", this.bean.getWTCServers(), otherTyped.getWTCServers(), true);
            this.computeSubDiff("WebAppContainer", this.bean.getWebAppContainer(), otherTyped.getWebAppContainer());
            this.computeChildDiff("WebserviceSecurities", this.bean.getWebserviceSecurities(), otherTyped.getWebserviceSecurities(), true);
            this.computeSubDiff("WebserviceTestpage", this.bean.getWebserviceTestpage(), otherTyped.getWebserviceTestpage());
            this.computeChildDiff("XMLEntityCaches", this.bean.getXMLEntityCaches(), otherTyped.getXMLEntityCaches(), false);
            this.computeChildDiff("XMLRegistries", this.bean.getXMLRegistries(), otherTyped.getXMLRegistries(), true);
            this.computeDiff("Active", this.bean.isActive(), otherTyped.isActive(), false);
            this.computeDiff("AdministrationMBeanAuditingEnabled", this.bean.isAdministrationMBeanAuditingEnabled(), otherTyped.isAdministrationMBeanAuditingEnabled(), true);
            this.computeDiff("AdministrationPortEnabled", this.bean.isAdministrationPortEnabled(), otherTyped.isAdministrationPortEnabled(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("AutoConfigurationSaveEnabled", this.bean.isAutoConfigurationSaveEnabled(), otherTyped.isAutoConfigurationSaveEnabled(), false);
            }

            this.computeDiff("AutoDeployForSubmodulesEnabled", this.bean.isAutoDeployForSubmodulesEnabled(), otherTyped.isAutoDeployForSubmodulesEnabled(), false);
            this.computeDiff("ClusterConstraintsEnabled", this.bean.isClusterConstraintsEnabled(), otherTyped.isClusterConstraintsEnabled(), false);
            this.computeDiff("ConfigBackupEnabled", this.bean.isConfigBackupEnabled(), otherTyped.isConfigBackupEnabled(), false);
            this.computeDiff("ConsoleEnabled", this.bean.isConsoleEnabled(), otherTyped.isConsoleEnabled(), false);
            this.computeDiff("DBPassiveMode", this.bean.isDBPassiveMode(), otherTyped.isDBPassiveMode(), true);
            this.computeDiff("DiagnosticContextCompatibilityModeEnabled", this.bean.isDiagnosticContextCompatibilityModeEnabled(), otherTyped.isDiagnosticContextCompatibilityModeEnabled(), true);
            this.computeDiff("EnableEECompliantClassloadingForEmbeddedAdapters", this.bean.isEnableEECompliantClassloadingForEmbeddedAdapters(), otherTyped.isEnableEECompliantClassloadingForEmbeddedAdapters(), true);
            this.computeDiff("ExalogicOptimizationsEnabled", this.bean.isExalogicOptimizationsEnabled(), otherTyped.isExalogicOptimizationsEnabled(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("GuardianEnabled", this.bean.isGuardianEnabled(), otherTyped.isGuardianEnabled(), false);
            }

            this.computeDiff("InternalAppsDeployOnDemandEnabled", this.bean.isInternalAppsDeployOnDemandEnabled(), otherTyped.isInternalAppsDeployOnDemandEnabled(), false);
            this.computeDiff("JavaServiceConsoleEnabled", this.bean.isJavaServiceConsoleEnabled(), otherTyped.isJavaServiceConsoleEnabled(), false);
            this.computeDiff("JavaServiceEnabled", this.bean.isJavaServiceEnabled(), otherTyped.isJavaServiceEnabled(), false);
            this.computeDiff("LogFormatCompatibilityEnabled", this.bean.isLogFormatCompatibilityEnabled(), otherTyped.isLogFormatCompatibilityEnabled(), false);
            this.computeDiff("MsgIdPrefixCompatibilityEnabled", this.bean.isMsgIdPrefixCompatibilityEnabled(), otherTyped.isMsgIdPrefixCompatibilityEnabled(), true);
            this.computeDiff("OCMEnabled", this.bean.isOCMEnabled(), otherTyped.isOCMEnabled(), false);
            this.computeDiff("ParallelDeployApplicationModules", this.bean.isParallelDeployApplicationModules(), otherTyped.isParallelDeployApplicationModules(), true);
            this.computeDiff("ParallelDeployApplications", this.bean.isParallelDeployApplications(), otherTyped.isParallelDeployApplications(), true);
            this.computeDiff("ProductionModeEnabled", this.bean.isProductionModeEnabled(), otherTyped.isProductionModeEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DomainMBeanImpl original = (DomainMBeanImpl)event.getSourceBean();
            DomainMBeanImpl proposed = (DomainMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdminConsole")) {
                  if (type == 2) {
                     original.setAdminConsole((AdminConsoleMBean)this.createCopy((AbstractDescriptorBean)proposed.getAdminConsole()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AdminConsole", original.getAdminConsole());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 119);
               } else if (prop.equals("AdminServerMBean")) {
                  if (type == 2) {
                     original.setAdminServerMBean((AdminServerMBean)this.createCopy((AbstractDescriptorBean)proposed.getAdminServerMBean()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AdminServerMBean", original.getAdminServerMBean());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 104);
               } else if (prop.equals("AdminServerName")) {
                  original.setAdminServerName(proposed.getAdminServerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 97);
               } else if (prop.equals("AdministrationPort")) {
                  original.setAdministrationPort(proposed.getAdministrationPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("AdministrationProtocol")) {
                  original.setAdministrationProtocol(proposed.getAdministrationProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("AppDeployments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAppDeployment((AppDeploymentMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAppDeployment((AppDeploymentMBean)update.getRemovedObject());
                  }

                  if (original.getAppDeployments() == null || original.getAppDeployments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 48);
                  }
               } else if (prop.equals("Applications")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addApplication((ApplicationMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeApplication((ApplicationMBean)update.getRemovedObject());
                  }

                  if (original.getApplications() == null || original.getApplications().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 47);
                  }
               } else if (prop.equals("ArchiveConfigurationCount")) {
                  original.setArchiveConfigurationCount(proposed.getArchiveConfigurationCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("BatchConfig")) {
                  if (type == 2) {
                     original.setBatchConfig((BatchConfigMBean)this.createCopy((AbstractDescriptorBean)proposed.getBatchConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("BatchConfig", original.getBatchConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 151);
               } else if (prop.equals("BatchJobsDataSourceJndiName")) {
                  original.setBatchJobsDataSourceJndiName(proposed.getBatchJobsDataSourceJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 155);
               } else if (prop.equals("BatchJobsExecutorServiceName")) {
                  original.setBatchJobsExecutorServiceName(proposed.getBatchJobsExecutorServiceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 156);
               } else if (prop.equals("BridgeDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addBridgeDestination((BridgeDestinationMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeBridgeDestination((BridgeDestinationMBean)update.getRemovedObject());
                  }

                  if (original.getBridgeDestinations() == null || original.getBridgeDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 83);
                  }
               } else if (prop.equals("Callouts")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCallout((CalloutMBean)update.getAddedObject());
                     }

                     this.reorderArrayObjects(original.getCallouts(), proposed.getCallouts());
                  } else if (type == 3) {
                     original.removeCallout((CalloutMBean)update.getRemovedObject());
                  } else {
                     this.reorderArrayObjects(original.getCallouts(), proposed.getCallouts());
                  }

                  if (original.getCallouts() == null || original.getCallouts().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 164);
                  }
               } else if (prop.equals("CdiContainer")) {
                  if (type == 2) {
                     original.setCdiContainer((CdiContainerMBean)this.createCopy((AbstractDescriptorBean)proposed.getCdiContainer()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CdiContainer", original.getCdiContainer());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 74);
               } else if (prop.equals("Clusters")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCluster((ClusterMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCluster((ClusterMBean)update.getRemovedObject());
                  }

                  if (original.getClusters() == null || original.getClusters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  }
               } else if (prop.equals("CoherenceClusterSystemResources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceClusterSystemResources() == null || original.getCoherenceClusterSystemResources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 125);
                  }
               } else if (prop.equals("CoherenceManagementClusters")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceManagementCluster((CoherenceManagementClusterMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceManagementCluster((CoherenceManagementClusterMBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceManagementClusters() == null || original.getCoherenceManagementClusters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 133);
                  }
               } else if (prop.equals("CoherenceServers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceServer((CoherenceServerMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceServer((CoherenceServerMBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceServers() == null || original.getCoherenceServers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  }
               } else if (prop.equals("ConfigurationAuditType")) {
                  original.setConfigurationAuditType(proposed.getConfigurationAuditType());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("ConfigurationVersion")) {
                  original.setConfigurationVersion(proposed.getConfigurationVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("ConsoleContextPath")) {
                  original.setConsoleContextPath(proposed.getConsoleContextPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("ConsoleExtensionDirectory")) {
                  original.setConsoleExtensionDirectory(proposed.getConsoleExtensionDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("CustomResources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCustomResource((CustomResourceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCustomResource((CustomResourceMBean)update.getRemovedObject());
                  }

                  if (original.getCustomResources() == null || original.getCustomResources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 95);
                  }
               } else if (prop.equals("DBPassiveModeGracePeriodSeconds")) {
                  original.setDBPassiveModeGracePeriodSeconds(proposed.getDBPassiveModeGracePeriodSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 162);
               } else if (prop.equals("DebugPatches")) {
                  if (type == 2) {
                     original.setDebugPatches((DebugPatchesMBean)this.createCopy((AbstractDescriptorBean)proposed.getDebugPatches()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DebugPatches", original.getDebugPatches());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 152);
               } else if (prop.equals("DeploymentConfiguration")) {
                  if (type == 2) {
                     original.setDeploymentConfiguration((DeploymentConfigurationMBean)this.createCopy((AbstractDescriptorBean)proposed.getDeploymentConfiguration()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeploymentConfiguration", original.getDeploymentConfiguration());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (!prop.equals("Deployments")) {
                  if (prop.equals("DomainLibraries")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addDomainLibrary((DomainLibraryMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeDomainLibrary((DomainLibraryMBean)update.getRemovedObject());
                     }

                     if (original.getDomainLibraries() == null || original.getDomainLibraries().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 51);
                     }
                  } else if (prop.equals("DomainVersion")) {
                     original.setDomainVersion(proposed.getDomainVersion());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("EJBContainer")) {
                     if (type == 2) {
                        original.setEJBContainer((EJBContainerMBean)this.createCopy((AbstractDescriptorBean)proposed.getEJBContainer()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("EJBContainer", original.getEJBContainer());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 72);
                  } else if (prop.equals("EmbeddedLDAP")) {
                     if (type == 2) {
                        original.setEmbeddedLDAP((EmbeddedLDAPMBean)this.createCopy((AbstractDescriptorBean)proposed.getEmbeddedLDAP()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("EmbeddedLDAP", original.getEmbeddedLDAP());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  } else if (prop.equals("FileStores")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addFileStore((FileStoreMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeFileStore((FileStoreMBean)update.getRemovedObject());
                     }

                     if (original.getFileStores() == null || original.getFileStores().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 91);
                     }
                  } else if (prop.equals("FileT3s")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addFileT3((FileT3MBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeFileT3((FileT3MBean)update.getRemovedObject());
                     }

                     if (original.getFileT3s() == null || original.getFileT3s().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     }
                  } else if (prop.equals("ForeignJMSConnectionFactories")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addForeignJMSConnectionFactory((ForeignJMSConnectionFactoryMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeForeignJMSConnectionFactory((ForeignJMSConnectionFactoryMBean)update.getRemovedObject());
                     }

                     if (original.getForeignJMSConnectionFactories() == null || original.getForeignJMSConnectionFactories().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 115);
                     }
                  } else if (prop.equals("ForeignJMSDestinations")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addForeignJMSDestination((ForeignJMSDestinationMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeForeignJMSDestination((ForeignJMSDestinationMBean)update.getRemovedObject());
                     }

                     if (original.getForeignJMSDestinations() == null || original.getForeignJMSDestinations().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 116);
                     }
                  } else if (prop.equals("ForeignJMSServers")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addForeignJMSServer((ForeignJMSServerMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeForeignJMSServer((ForeignJMSServerMBean)update.getRemovedObject());
                     }

                     if (original.getForeignJMSServers() == null || original.getForeignJMSServers().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 84);
                     }
                  } else if (prop.equals("ForeignJNDIProviders")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addForeignJNDIProvider((ForeignJNDIProviderMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeForeignJNDIProvider((ForeignJNDIProviderMBean)update.getRemovedObject());
                     }

                     if (original.getForeignJNDIProviders() == null || original.getForeignJNDIProviders().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 96);
                     }
                  } else if (prop.equals("InstalledSoftwareVersion")) {
                     original.setInstalledSoftwareVersion(proposed.getInstalledSoftwareVersion());
                     original._conditionalUnset(update.isUnsetUpdate(), 163);
                  } else if (prop.equals("Interceptors")) {
                     if (type == 2) {
                        original.setInterceptors((InterceptorsMBean)this.createCopy((AbstractDescriptorBean)proposed.getInterceptors()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("Interceptors", original.getInterceptors());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 150);
                  } else if (!prop.equals("InternalAppDeployments") && !prop.equals("InternalLibraries")) {
                     if (prop.equals("JDBCStores")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJDBCStore((JDBCStoreMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJDBCStore((JDBCStoreMBean)update.getRemovedObject());
                        }

                        if (original.getJDBCStores() == null || original.getJDBCStores().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 93);
                        }
                     } else if (prop.equals("JDBCSystemResources")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJDBCSystemResource((JDBCSystemResourceMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJDBCSystemResource((JDBCSystemResourceMBean)update.getRemovedObject());
                        }

                        if (original.getJDBCSystemResources() == null || original.getJDBCSystemResources().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 100);
                        }
                     } else if (prop.equals("JMSBridgeDestinations")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSBridgeDestination((JMSBridgeDestinationMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSBridgeDestination((JMSBridgeDestinationMBean)update.getRemovedObject());
                        }

                        if (original.getJMSBridgeDestinations() == null || original.getJMSBridgeDestinations().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 82);
                        }
                     } else if (prop.equals("JMSConnectionConsumers")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSConnectionConsumer((JMSConnectionConsumerMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSConnectionConsumer((JMSConnectionConsumerMBean)update.getRemovedObject());
                        }

                        if (original.getJMSConnectionConsumers() == null || original.getJMSConnectionConsumers().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 117);
                        }
                     } else if (prop.equals("JMSConnectionFactories")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSConnectionFactory((JMSConnectionFactoryMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSConnectionFactory((JMSConnectionFactoryMBean)update.getRemovedObject());
                        }

                        if (original.getJMSConnectionFactories() == null || original.getJMSConnectionFactories().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 80);
                        }
                     } else if (prop.equals("JMSDestinationKeys")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSDestinationKey((JMSDestinationKeyMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSDestinationKey((JMSDestinationKeyMBean)update.getRemovedObject());
                        }

                        if (original.getJMSDestinationKeys() == null || original.getJMSDestinationKeys().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 79);
                        }
                     } else if (prop.equals("JMSDestinations")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSDestination((JMSDestinationMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSDestination((JMSDestinationMBean)update.getRemovedObject());
                        }

                        if (original.getJMSDestinations() == null || original.getJMSDestinations().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 62);
                        }
                     } else if (prop.equals("JMSDistributedQueueMembers")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSDistributedQueueMember((JMSDistributedQueueMemberMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSDistributedQueueMember((JMSDistributedQueueMemberMBean)update.getRemovedObject());
                        }

                        if (original.getJMSDistributedQueueMembers() == null || original.getJMSDistributedQueueMembers().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 105);
                        }
                     } else if (prop.equals("JMSDistributedQueues")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSDistributedQueue((JMSDistributedQueueMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSDistributedQueue((JMSDistributedQueueMBean)update.getRemovedObject());
                        }

                        if (original.getJMSDistributedQueues() == null || original.getJMSDistributedQueues().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 65);
                        }
                     } else if (prop.equals("JMSDistributedTopicMembers")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSDistributedTopicMember((JMSDistributedTopicMemberMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSDistributedTopicMember((JMSDistributedTopicMemberMBean)update.getRemovedObject());
                        }

                        if (original.getJMSDistributedTopicMembers() == null || original.getJMSDistributedTopicMembers().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 106);
                        }
                     } else if (prop.equals("JMSDistributedTopics")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSDistributedTopic((JMSDistributedTopicMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSDistributedTopic((JMSDistributedTopicMBean)update.getRemovedObject());
                        }

                        if (original.getJMSDistributedTopics() == null || original.getJMSDistributedTopics().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 66);
                        }
                     } else if (prop.equals("JMSFileStores")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSFileStore((JMSFileStoreMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSFileStore((JMSFileStoreMBean)update.getRemovedObject());
                        }

                        if (original.getJMSFileStores() == null || original.getJMSFileStores().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 61);
                        }
                     } else if (prop.equals("JMSJDBCStores")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSJDBCStore((JMSJDBCStoreMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSJDBCStore((JMSJDBCStoreMBean)update.getRemovedObject());
                        }

                        if (original.getJMSJDBCStores() == null || original.getJMSJDBCStores().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 60);
                        }
                     } else if (prop.equals("JMSQueues")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSQueue((JMSQueueMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSQueue((JMSQueueMBean)update.getRemovedObject());
                        }

                        if (original.getJMSQueues() == null || original.getJMSQueues().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 63);
                        }
                     } else if (prop.equals("JMSServers")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSServer((JMSServerMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSServer((JMSServerMBean)update.getRemovedObject());
                        }

                        if (original.getJMSServers() == null || original.getJMSServers().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 58);
                        }
                     } else if (prop.equals("JMSSessionPools")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addJMSSessionPool((JMSSessionPoolMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeJMSSessionPool((JMSSessionPoolMBean)update.getRemovedObject());
                        }

                        if (original.getJMSSessionPools() == null || original.getJMSSessionPools().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 81);
                        }
                     } else if (!prop.equals("JMSStores")) {
                        if (prop.equals("JMSSystemResources")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addJMSSystemResource((JMSSystemResourceMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeJMSSystemResource((JMSSystemResourceMBean)update.getRemovedObject());
                           }

                           if (original.getJMSSystemResources() == null || original.getJMSSystemResources().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 94);
                           }
                        } else if (prop.equals("JMSTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addJMSTemplate((JMSTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeJMSTemplate((JMSTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getJMSTemplates() == null || original.getJMSTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 67);
                           }
                        } else if (prop.equals("JMSTopics")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addJMSTopic((JMSTopicMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeJMSTopic((JMSTopicMBean)update.getRemovedObject());
                           }

                           if (original.getJMSTopics() == null || original.getJMSTopics().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 64);
                           }
                        } else if (prop.equals("JMX")) {
                           if (type == 2) {
                              original.setJMX((JMXMBean)this.createCopy((AbstractDescriptorBean)proposed.getJMX()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("JMX", original.getJMX());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 75);
                        } else if (prop.equals("JPA")) {
                           if (type == 2) {
                              original.setJPA((JPAMBean)this.createCopy((AbstractDescriptorBean)proposed.getJPA()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("JPA", original.getJPA());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 16);
                        } else if (prop.equals("JTA")) {
                           if (type == 2) {
                              original.setJTA((JTAMBean)this.createCopy((AbstractDescriptorBean)proposed.getJTA()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("JTA", original.getJTA());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 15);
                        } else if (prop.equals("JoltConnectionPools")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addJoltConnectionPool((JoltConnectionPoolMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeJoltConnectionPool((JoltConnectionPoolMBean)update.getRemovedObject());
                           }

                           if (original.getJoltConnectionPools() == null || original.getJoltConnectionPools().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 89);
                           }
                        } else if (prop.equals("LastModificationTime")) {
                           original._conditionalUnset(update.isUnsetUpdate(), 12);
                        } else if (prop.equals("Libraries")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addLibrary((LibraryMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeLibrary((LibraryMBean)update.getRemovedObject());
                           }

                           if (original.getLibraries() == null || original.getLibraries().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 50);
                           }
                        } else if (prop.equals("LifecycleManagerConfig")) {
                           if (type == 2) {
                              original.setLifecycleManagerConfig((LifecycleManagerConfigMBean)this.createCopy((AbstractDescriptorBean)proposed.getLifecycleManagerConfig()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("LifecycleManagerConfig", original.getLifecycleManagerConfig());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 158);
                        } else if (prop.equals("LifecycleManagerEndPoints")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addLifecycleManagerEndPoint((LifecycleManagerEndPointMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeLifecycleManagerEndPoint((LifecycleManagerEndPointMBean)update.getRemovedObject());
                           }

                           if (original.getLifecycleManagerEndPoints() == null || original.getLifecycleManagerEndPoints().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 149);
                           }
                        } else if (prop.equals("Log")) {
                           if (type == 2) {
                              original.setLog((LogMBean)this.createCopy((AbstractDescriptorBean)proposed.getLog()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("Log", original.getLog());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 19);
                        } else if (prop.equals("LogFilters")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addLogFilter((LogFilterMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeLogFilter((LogFilterMBean)update.getRemovedObject());
                           }

                           if (original.getLogFilters() == null || original.getLogFilters().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 90);
                           }
                        } else if (prop.equals("Machines")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addMachine((MachineMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeMachine((MachineMBean)update.getRemovedObject());
                           }

                           if (original.getMachines() == null || original.getMachines().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 54);
                           }
                        } else if (prop.equals("MailSessions")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addMailSession((MailSessionMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeMailSession((MailSessionMBean)update.getRemovedObject());
                           }

                           if (original.getMailSessions() == null || original.getMailSessions().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 88);
                           }
                        } else if (prop.equals("ManagedExecutorServiceTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addManagedExecutorServiceTemplate((ManagedExecutorServiceTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeManagedExecutorServiceTemplate((ManagedExecutorServiceTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getManagedExecutorServiceTemplates() == null || original.getManagedExecutorServiceTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 142);
                           }
                        } else if (prop.equals("ManagedExecutorServices")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addManagedExecutorService((ManagedExecutorServiceMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeManagedExecutorService((ManagedExecutorServiceMBean)update.getRemovedObject());
                           }

                           if (original.getManagedExecutorServices() == null || original.getManagedExecutorServices().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 145);
                           }
                        } else if (prop.equals("ManagedScheduledExecutorServiceTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addManagedScheduledExecutorServiceTemplate((ManagedScheduledExecutorServiceTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeManagedScheduledExecutorServiceTemplate((ManagedScheduledExecutorServiceTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getManagedScheduledExecutorServiceTemplates() == null || original.getManagedScheduledExecutorServiceTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 143);
                           }
                        } else if (prop.equals("ManagedScheduledExecutorServices")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addManagedScheduledExecutorService((ManagedScheduledExecutorServiceMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeManagedScheduledExecutorService((ManagedScheduledExecutorServiceMBean)update.getRemovedObject());
                           }

                           if (original.getManagedScheduledExecutorServices() == null || original.getManagedScheduledExecutorServices().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 146);
                           }
                        } else if (prop.equals("ManagedThreadFactories")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addManagedThreadFactory((ManagedThreadFactoryMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeManagedThreadFactory((ManagedThreadFactoryMBean)update.getRemovedObject());
                           }

                           if (original.getManagedThreadFactories() == null || original.getManagedThreadFactories().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 147);
                           }
                        } else if (prop.equals("ManagedThreadFactoryTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addManagedThreadFactoryTemplate((ManagedThreadFactoryTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeManagedThreadFactoryTemplate((ManagedThreadFactoryTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getManagedThreadFactoryTemplates() == null || original.getManagedThreadFactoryTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 144);
                           }
                        } else if (prop.equals("MaxConcurrentLongRunningRequests")) {
                           original.setMaxConcurrentLongRunningRequests(proposed.getMaxConcurrentLongRunningRequests());
                           original._conditionalUnset(update.isUnsetUpdate(), 139);
                        } else if (prop.equals("MaxConcurrentNewThreads")) {
                           original.setMaxConcurrentNewThreads(proposed.getMaxConcurrentNewThreads());
                           original._conditionalUnset(update.isUnsetUpdate(), 138);
                        } else if (prop.equals("MessagingBridges")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addMessagingBridge((MessagingBridgeMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeMessagingBridge((MessagingBridgeMBean)update.getRemovedObject());
                           }

                           if (original.getMessagingBridges() == null || original.getMessagingBridges().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 34);
                           }
                        } else if (prop.equals("MigratableRMIServices")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addMigratableRMIService((MigratableRMIServiceMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeMigratableRMIService((MigratableRMIServiceMBean)update.getRemovedObject());
                           }

                           if (original.getMigratableRMIServices() == null || original.getMigratableRMIServices().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 103);
                           }
                        } else if (prop.equals("MigratableTargets")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addMigratableTarget((MigratableTargetMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeMigratableTarget((MigratableTargetMBean)update.getRemovedObject());
                           }

                           if (original.getMigratableTargets() == null || original.getMigratableTargets().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 71);
                           }
                        } else if (prop.equals("Name")) {
                           original.setName(proposed.getName());
                           original._conditionalUnset(update.isUnsetUpdate(), 2);
                        } else if (prop.equals("NetworkChannels")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addNetworkChannel((NetworkChannelMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeNetworkChannel((NetworkChannelMBean)update.getRemovedObject());
                           }

                           if (original.getNetworkChannels() == null || original.getNetworkChannels().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 68);
                           }
                        } else if (prop.equals("OptionalFeatureDeployment")) {
                           if (type == 2) {
                              original.setOptionalFeatureDeployment((OptionalFeatureDeploymentMBean)this.createCopy((AbstractDescriptorBean)proposed.getOptionalFeatureDeployment()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("OptionalFeatureDeployment", original.getOptionalFeatureDeployment());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 157);
                        } else if (prop.equals("OsgiFrameworks")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addOsgiFramework((OsgiFrameworkMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeOsgiFramework((OsgiFrameworkMBean)update.getRemovedObject());
                           }

                           if (original.getOsgiFrameworks() == null || original.getOsgiFrameworks().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 129);
                           }
                        } else if (prop.equals("PartitionTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addPartitionTemplate((PartitionTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removePartitionTemplate((PartitionTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getPartitionTemplates() == null || original.getPartitionTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 148);
                           }
                        } else if (prop.equals("PartitionUriSpace")) {
                           original.setPartitionUriSpace(proposed.getPartitionUriSpace());
                           original._conditionalUnset(update.isUnsetUpdate(), 135);
                        } else if (prop.equals("PartitionWorkManagers")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addPartitionWorkManager((PartitionWorkManagerMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removePartitionWorkManager((PartitionWorkManagerMBean)update.getRemovedObject());
                           }

                           if (original.getPartitionWorkManagers() == null || original.getPartitionWorkManagers().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 153);
                           }
                        } else if (prop.equals("Partitions")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addPartition((PartitionMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removePartition((PartitionMBean)update.getRemovedObject());
                           }

                           if (original.getPartitions() == null || original.getPartitions().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 134);
                           }
                        } else if (prop.equals("PathServices")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addPathService((PathServiceMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removePathService((PathServiceMBean)update.getRemovedObject());
                           }

                           if (original.getPathServices() == null || original.getPathServices().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 78);
                           }
                        } else if (prop.equals("ReplicatedStores")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addReplicatedStore((ReplicatedStoreMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeReplicatedStore((ReplicatedStoreMBean)update.getRemovedObject());
                           }

                           if (original.getReplicatedStores() == null || original.getReplicatedStores().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 92);
                           }
                        } else if (prop.equals("ResourceGroupTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addResourceGroupTemplate((ResourceGroupTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeResourceGroupTemplate((ResourceGroupTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getResourceGroupTemplates() == null || original.getResourceGroupTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 137);
                           }
                        } else if (prop.equals("ResourceGroups")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addResourceGroup((ResourceGroupMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeResourceGroup((ResourceGroupMBean)update.getRemovedObject());
                           }

                           if (original.getResourceGroups() == null || original.getResourceGroups().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 136);
                           }
                        } else if (prop.equals("ResourceManagement")) {
                           if (type == 2) {
                              original.setResourceManagement((ResourceManagementMBean)this.createCopy((AbstractDescriptorBean)proposed.getResourceManagement()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("ResourceManagement", original.getResourceManagement());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 77);
                        } else if (prop.equals("RestfulManagementServices")) {
                           if (type == 2) {
                              original.setRestfulManagementServices((RestfulManagementServicesMBean)this.createCopy((AbstractDescriptorBean)proposed.getRestfulManagementServices()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("RestfulManagementServices", original.getRestfulManagementServices());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 126);
                        } else if (prop.equals("RootDirectory")) {
                           original._conditionalUnset(update.isUnsetUpdate(), 22);
                        } else if (prop.equals("SAFAgents")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSAFAgent((SAFAgentMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSAFAgent((SAFAgentMBean)update.getRemovedObject());
                           }

                           if (original.getSAFAgents() == null || original.getSAFAgents().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 102);
                           }
                        } else if (prop.equals("SNMPAgent")) {
                           if (type == 2) {
                              original.setSNMPAgent((SNMPAgentMBean)this.createCopy((AbstractDescriptorBean)proposed.getSNMPAgent()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("SNMPAgent", original.getSNMPAgent());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 20);
                        } else if (prop.equals("SNMPAgentDeployments")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPAgentDeployment((SNMPAgentDeploymentMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPAgentDeployment((SNMPAgentDeploymentMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPAgentDeployments() == null || original.getSNMPAgentDeployments().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 21);
                           }
                        } else if (prop.equals("SNMPAttributeChanges")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPAttributeChange((SNMPAttributeChangeMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPAttributeChange((SNMPAttributeChangeMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPAttributeChanges() == null || original.getSNMPAttributeChanges().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 113);
                           }
                        } else if (prop.equals("SNMPCounterMonitors")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPCounterMonitor((SNMPCounterMonitorMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPCounterMonitor((SNMPCounterMonitorMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPCounterMonitors() == null || original.getSNMPCounterMonitors().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 111);
                           }
                        } else if (prop.equals("SNMPGaugeMonitors")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPGaugeMonitor((SNMPGaugeMonitorMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPGaugeMonitor((SNMPGaugeMonitorMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPGaugeMonitors() == null || original.getSNMPGaugeMonitors().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 109);
                           }
                        } else if (prop.equals("SNMPLogFilters")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPLogFilter((SNMPLogFilterMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPLogFilter((SNMPLogFilterMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPLogFilters() == null || original.getSNMPLogFilters().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 112);
                           }
                        } else if (prop.equals("SNMPProxies")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPProxy((SNMPProxyMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPProxy((SNMPProxyMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPProxies() == null || original.getSNMPProxies().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 108);
                           }
                        } else if (prop.equals("SNMPStringMonitors")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPStringMonitor((SNMPStringMonitorMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPStringMonitor((SNMPStringMonitorMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPStringMonitors() == null || original.getSNMPStringMonitors().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 110);
                           }
                        } else if (prop.equals("SNMPTrapDestinations")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSNMPTrapDestination((SNMPTrapDestinationMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSNMPTrapDestination((SNMPTrapDestinationMBean)update.getRemovedObject());
                           }

                           if (original.getSNMPTrapDestinations() == null || original.getSNMPTrapDestinations().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 107);
                           }
                        } else if (prop.equals("SecurityConfiguration")) {
                           if (type == 2) {
                              original.setSecurityConfiguration((SecurityConfigurationMBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityConfiguration()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("SecurityConfiguration", original.getSecurityConfiguration());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 14);
                        } else if (prop.equals("SelfTuning")) {
                           if (type == 2) {
                              original.setSelfTuning((SelfTuningMBean)this.createCopy((AbstractDescriptorBean)proposed.getSelfTuning()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("SelfTuning", original.getSelfTuning());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 76);
                        } else if (prop.equals("ServerMigrationHistorySize")) {
                           original.setServerMigrationHistorySize(proposed.getServerMigrationHistorySize());
                           original._conditionalUnset(update.isUnsetUpdate(), 131);
                        } else if (prop.equals("ServerTemplates")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addServerTemplate((ServerTemplateMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeServerTemplate((ServerTemplateMBean)update.getRemovedObject());
                           }

                           if (original.getServerTemplates() == null || original.getServerTemplates().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 29);
                           }
                        } else if (prop.equals("Servers")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addServer((ServerMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeServer((ServerMBean)update.getRemovedObject());
                           }

                           if (original.getServers() == null || original.getServers().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 28);
                           }
                        } else if (prop.equals("ServiceMigrationHistorySize")) {
                           original.setServiceMigrationHistorySize(proposed.getServiceMigrationHistorySize());
                           original._conditionalUnset(update.isUnsetUpdate(), 132);
                        } else if (prop.equals("ShutdownClasses")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addShutdownClass((ShutdownClassMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeShutdownClass((ShutdownClassMBean)update.getRemovedObject());
                           }

                           if (original.getShutdownClasses() == null || original.getShutdownClasses().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 85);
                           }
                        } else if (prop.equals("SingletonServices")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSingletonService((SingletonServiceMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSingletonService((SingletonServiceMBean)update.getRemovedObject());
                           }

                           if (original.getSingletonServices() == null || original.getSingletonServices().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 87);
                           }
                        } else if (prop.equals("SiteName")) {
                           original.setSiteName(proposed.getSiteName());
                           original._conditionalUnset(update.isUnsetUpdate(), 159);
                        } else if (prop.equals("StartupClasses")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addStartupClass((StartupClassMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeStartupClass((StartupClassMBean)update.getRemovedObject());
                           }

                           if (original.getStartupClasses() == null || original.getStartupClasses().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 86);
                           }
                        } else if (prop.equals("SystemComponentConfigurations")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSystemComponentConfiguration((SystemComponentConfigurationMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSystemComponentConfiguration((SystemComponentConfigurationMBean)update.getRemovedObject());
                           }

                           if (original.getSystemComponentConfigurations() == null || original.getSystemComponentConfigurations().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 128);
                           }
                        } else if (prop.equals("SystemComponents")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addSystemComponent((SystemComponentMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeSystemComponent((SystemComponentMBean)update.getRemovedObject());
                           }

                           if (original.getSystemComponents() == null || original.getSystemComponents().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 127);
                           }
                        } else if (!prop.equals("SystemResources")) {
                           if (prop.equals("Tags")) {
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
                           } else if (!prop.equals("Targets")) {
                              if (prop.equals("VirtualHosts")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addVirtualHost((VirtualHostMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeVirtualHost((VirtualHostMBean)update.getRemovedObject());
                                 }

                                 if (original.getVirtualHosts() == null || original.getVirtualHosts().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 69);
                                 }
                              } else if (prop.equals("VirtualTargets")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addVirtualTarget((VirtualTargetMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeVirtualTarget((VirtualTargetMBean)update.getRemovedObject());
                                 }

                                 if (original.getVirtualTargets() == null || original.getVirtualTargets().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 70);
                                 }
                              } else if (prop.equals("WLDFSystemResources")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addWLDFSystemResource((WLDFSystemResourceMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeWLDFSystemResource((WLDFSystemResourceMBean)update.getRemovedObject());
                                 }

                                 if (original.getWLDFSystemResources() == null || original.getWLDFSystemResources().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 99);
                                 }
                              } else if (prop.equals("WSReliableDeliveryPolicies")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addWSReliableDeliveryPolicy((WSReliableDeliveryPolicyMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeWSReliableDeliveryPolicy((WSReliableDeliveryPolicyMBean)update.getRemovedObject());
                                 }

                                 if (original.getWSReliableDeliveryPolicies() == null || original.getWSReliableDeliveryPolicies().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 53);
                                 }
                              } else if (prop.equals("WTCServers")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addWTCServer((WTCServerMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeWTCServer((WTCServerMBean)update.getRemovedObject());
                                 }

                                 if (original.getWTCServers() == null || original.getWTCServers().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 18);
                                 }
                              } else if (prop.equals("WebAppContainer")) {
                                 if (type == 2) {
                                    original.setWebAppContainer((WebAppContainerMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebAppContainer()));
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original._destroySingleton("WebAppContainer", original.getWebAppContainer());
                                 }

                                 original._conditionalUnset(update.isUnsetUpdate(), 73);
                              } else if (prop.equals("WebserviceSecurities")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addWebserviceSecurity((WebserviceSecurityMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeWebserviceSecurity((WebserviceSecurityMBean)update.getRemovedObject());
                                 }

                                 if (original.getWebserviceSecurities() == null || original.getWebserviceSecurities().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 114);
                                 }
                              } else if (prop.equals("WebserviceTestpage")) {
                                 if (type == 2) {
                                    original.setWebserviceTestpage((WebserviceTestpageMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebserviceTestpage()));
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original._destroySingleton("WebserviceTestpage", original.getWebserviceTestpage());
                                 }

                                 original._conditionalUnset(update.isUnsetUpdate(), 130);
                              } else if (prop.equals("XMLEntityCaches")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addXMLEntityCache((XMLEntityCacheMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeXMLEntityCache((XMLEntityCacheMBean)update.getRemovedObject());
                                 }

                                 if (original.getXMLEntityCaches() == null || original.getXMLEntityCaches().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 55);
                                 }
                              } else if (prop.equals("XMLRegistries")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addXMLRegistry((XMLRegistryMBean)update.getAddedObject());
                                    }
                                 } else {
                                    if (type != 3) {
                                       throw new AssertionError("Invalid type: " + type);
                                    }

                                    original.removeXMLRegistry((XMLRegistryMBean)update.getRemovedObject());
                                 }

                                 if (original.getXMLRegistries() == null || original.getXMLRegistries().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 56);
                                 }
                              } else if (prop.equals("Active")) {
                                 original._conditionalUnset(update.isUnsetUpdate(), 13);
                              } else if (prop.equals("AdministrationMBeanAuditingEnabled")) {
                                 original.setAdministrationMBeanAuditingEnabled(proposed.isAdministrationMBeanAuditingEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 44);
                              } else if (prop.equals("AdministrationPortEnabled")) {
                                 original.setAdministrationPortEnabled(proposed.isAdministrationPortEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 37);
                              } else if (prop.equals("AutoConfigurationSaveEnabled")) {
                                 original.setAutoConfigurationSaveEnabled(proposed.isAutoConfigurationSaveEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 27);
                              } else if (prop.equals("AutoDeployForSubmodulesEnabled")) {
                                 original.setAutoDeployForSubmodulesEnabled(proposed.isAutoDeployForSubmodulesEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 118);
                              } else if (prop.equals("ClusterConstraintsEnabled")) {
                                 original.setClusterConstraintsEnabled(proposed.isClusterConstraintsEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 46);
                              } else if (prop.equals("ConfigBackupEnabled")) {
                                 original.setConfigBackupEnabled(proposed.isConfigBackupEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 42);
                              } else if (prop.equals("ConsoleEnabled")) {
                                 original.setConsoleEnabled(proposed.isConsoleEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 23);
                              } else if (prop.equals("DBPassiveMode")) {
                                 original.setDBPassiveMode(proposed.isDBPassiveMode());
                                 original._conditionalUnset(update.isUnsetUpdate(), 161);
                              } else if (prop.equals("DiagnosticContextCompatibilityModeEnabled")) {
                                 original.setDiagnosticContextCompatibilityModeEnabled(proposed.isDiagnosticContextCompatibilityModeEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 154);
                              } else if (!prop.equals("DynamicallyCreated")) {
                                 if (prop.equals("EnableEECompliantClassloadingForEmbeddedAdapters")) {
                                    original.setEnableEECompliantClassloadingForEmbeddedAdapters(proposed.isEnableEECompliantClassloadingForEmbeddedAdapters());
                                    original._conditionalUnset(update.isUnsetUpdate(), 160);
                                 } else if (prop.equals("ExalogicOptimizationsEnabled")) {
                                    original.setExalogicOptimizationsEnabled(proposed.isExalogicOptimizationsEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 39);
                                 } else if (prop.equals("GuardianEnabled")) {
                                    original.setGuardianEnabled(proposed.isGuardianEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 121);
                                 } else if (prop.equals("InternalAppsDeployOnDemandEnabled")) {
                                    original.setInternalAppsDeployOnDemandEnabled(proposed.isInternalAppsDeployOnDemandEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 120);
                                 } else if (prop.equals("JavaServiceConsoleEnabled")) {
                                    original.setJavaServiceConsoleEnabled(proposed.isJavaServiceConsoleEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 24);
                                 } else if (prop.equals("JavaServiceEnabled")) {
                                    original.setJavaServiceEnabled(proposed.isJavaServiceEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 40);
                                 } else if (prop.equals("LogFormatCompatibilityEnabled")) {
                                    original.setLogFormatCompatibilityEnabled(proposed.isLogFormatCompatibilityEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 124);
                                 } else if (prop.equals("MsgIdPrefixCompatibilityEnabled")) {
                                    original.setMsgIdPrefixCompatibilityEnabled(proposed.isMsgIdPrefixCompatibilityEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 123);
                                 } else if (prop.equals("OCMEnabled")) {
                                    original.setOCMEnabled(proposed.isOCMEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 122);
                                 } else if (prop.equals("ParallelDeployApplicationModules")) {
                                    original.setParallelDeployApplicationModules(proposed.isParallelDeployApplicationModules());
                                    original._conditionalUnset(update.isUnsetUpdate(), 141);
                                 } else if (prop.equals("ParallelDeployApplications")) {
                                    original.setParallelDeployApplications(proposed.isParallelDeployApplications());
                                    original._conditionalUnset(update.isUnsetUpdate(), 140);
                                 } else if (prop.equals("ProductionModeEnabled")) {
                                    original.setProductionModeEnabled(proposed.isProductionModeEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 35);
                                 } else {
                                    super.applyPropertyUpdate(event, update);
                                 }
                              }
                           }
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
            DomainMBeanImpl copy = (DomainMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdminConsole")) && this.bean.isAdminConsoleSet() && !copy._isSet(119)) {
               Object o = this.bean.getAdminConsole();
               copy.setAdminConsole((AdminConsoleMBean)null);
               copy.setAdminConsole(o == null ? null : (AdminConsoleMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("AdminServerMBean")) && this.bean.isAdminServerMBeanSet() && !copy._isSet(104)) {
               Object o = this.bean.getAdminServerMBean();
               copy.setAdminServerMBean((AdminServerMBean)null);
               copy.setAdminServerMBean(o == null ? null : (AdminServerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AdminServerName")) && this.bean.isAdminServerNameSet()) {
               copy.setAdminServerName(this.bean.getAdminServerName());
            }

            if ((excludeProps == null || !excludeProps.contains("AdministrationPort")) && this.bean.isAdministrationPortSet()) {
               copy.setAdministrationPort(this.bean.getAdministrationPort());
            }

            if ((excludeProps == null || !excludeProps.contains("AdministrationProtocol")) && this.bean.isAdministrationProtocolSet()) {
               copy.setAdministrationProtocol(this.bean.getAdministrationProtocol());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("AppDeployments")) && this.bean.isAppDeploymentsSet() && !copy._isSet(48)) {
               AppDeploymentMBean[] oldAppDeployments = this.bean.getAppDeployments();
               AppDeploymentMBean[] newAppDeployments = new AppDeploymentMBean[oldAppDeployments.length];

               for(i = 0; i < newAppDeployments.length; ++i) {
                  newAppDeployments[i] = (AppDeploymentMBean)((AppDeploymentMBean)this.createCopy((AbstractDescriptorBean)oldAppDeployments[i], includeObsolete));
               }

               copy.setAppDeployments(newAppDeployments);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Applications")) && this.bean.isApplicationsSet() && !copy._isSet(47)) {
               ApplicationMBean[] oldApplications = this.bean.getApplications();
               ApplicationMBean[] newApplications = new ApplicationMBean[oldApplications.length];

               for(i = 0; i < newApplications.length; ++i) {
                  newApplications[i] = (ApplicationMBean)((ApplicationMBean)this.createCopy((AbstractDescriptorBean)oldApplications[i], includeObsolete));
               }

               copy.setApplications(newApplications);
            }

            if ((excludeProps == null || !excludeProps.contains("ArchiveConfigurationCount")) && this.bean.isArchiveConfigurationCountSet()) {
               copy.setArchiveConfigurationCount(this.bean.getArchiveConfigurationCount());
            }

            if ((excludeProps == null || !excludeProps.contains("BatchConfig")) && this.bean.isBatchConfigSet() && !copy._isSet(151)) {
               Object o = this.bean.getBatchConfig();
               copy.setBatchConfig((BatchConfigMBean)null);
               copy.setBatchConfig(o == null ? null : (BatchConfigMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("BatchJobsDataSourceJndiName")) && this.bean.isBatchJobsDataSourceJndiNameSet()) {
               copy.setBatchJobsDataSourceJndiName(this.bean.getBatchJobsDataSourceJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("BatchJobsExecutorServiceName")) && this.bean.isBatchJobsExecutorServiceNameSet()) {
               copy.setBatchJobsExecutorServiceName(this.bean.getBatchJobsExecutorServiceName());
            }

            if ((excludeProps == null || !excludeProps.contains("BridgeDestinations")) && this.bean.isBridgeDestinationsSet() && !copy._isSet(83)) {
               BridgeDestinationMBean[] oldBridgeDestinations = this.bean.getBridgeDestinations();
               BridgeDestinationMBean[] newBridgeDestinations = new BridgeDestinationMBean[oldBridgeDestinations.length];

               for(i = 0; i < newBridgeDestinations.length; ++i) {
                  newBridgeDestinations[i] = (BridgeDestinationMBean)((BridgeDestinationMBean)this.createCopy((AbstractDescriptorBean)oldBridgeDestinations[i], includeObsolete));
               }

               copy.setBridgeDestinations(newBridgeDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("Callouts")) && this.bean.isCalloutsSet() && !copy._isSet(164)) {
               CalloutMBean[] oldCallouts = this.bean.getCallouts();
               CalloutMBean[] newCallouts = new CalloutMBean[oldCallouts.length];

               for(i = 0; i < newCallouts.length; ++i) {
                  newCallouts[i] = (CalloutMBean)((CalloutMBean)this.createCopy((AbstractDescriptorBean)oldCallouts[i], includeObsolete));
               }

               copy.setCallouts(newCallouts);
            }

            if ((excludeProps == null || !excludeProps.contains("CdiContainer")) && this.bean.isCdiContainerSet() && !copy._isSet(74)) {
               Object o = this.bean.getCdiContainer();
               copy.setCdiContainer((CdiContainerMBean)null);
               copy.setCdiContainer(o == null ? null : (CdiContainerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Clusters")) && this.bean.isClustersSet() && !copy._isSet(31)) {
               ClusterMBean[] oldClusters = this.bean.getClusters();
               ClusterMBean[] newClusters = new ClusterMBean[oldClusters.length];

               for(i = 0; i < newClusters.length; ++i) {
                  newClusters[i] = (ClusterMBean)((ClusterMBean)this.createCopy((AbstractDescriptorBean)oldClusters[i], includeObsolete));
               }

               copy.setClusters(newClusters);
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterSystemResources")) && this.bean.isCoherenceClusterSystemResourcesSet() && !copy._isSet(125)) {
               CoherenceClusterSystemResourceMBean[] oldCoherenceClusterSystemResources = this.bean.getCoherenceClusterSystemResources();
               CoherenceClusterSystemResourceMBean[] newCoherenceClusterSystemResources = new CoherenceClusterSystemResourceMBean[oldCoherenceClusterSystemResources.length];

               for(i = 0; i < newCoherenceClusterSystemResources.length; ++i) {
                  newCoherenceClusterSystemResources[i] = (CoherenceClusterSystemResourceMBean)((CoherenceClusterSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldCoherenceClusterSystemResources[i], includeObsolete));
               }

               copy.setCoherenceClusterSystemResources(newCoherenceClusterSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceManagementClusters")) && this.bean.isCoherenceManagementClustersSet() && !copy._isSet(133)) {
               CoherenceManagementClusterMBean[] oldCoherenceManagementClusters = this.bean.getCoherenceManagementClusters();
               CoherenceManagementClusterMBean[] newCoherenceManagementClusters = new CoherenceManagementClusterMBean[oldCoherenceManagementClusters.length];

               for(i = 0; i < newCoherenceManagementClusters.length; ++i) {
                  newCoherenceManagementClusters[i] = (CoherenceManagementClusterMBean)((CoherenceManagementClusterMBean)this.createCopy((AbstractDescriptorBean)oldCoherenceManagementClusters[i], includeObsolete));
               }

               copy.setCoherenceManagementClusters(newCoherenceManagementClusters);
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceServers")) && this.bean.isCoherenceServersSet() && !copy._isSet(30)) {
               CoherenceServerMBean[] oldCoherenceServers = this.bean.getCoherenceServers();
               CoherenceServerMBean[] newCoherenceServers = new CoherenceServerMBean[oldCoherenceServers.length];

               for(i = 0; i < newCoherenceServers.length; ++i) {
                  newCoherenceServers[i] = (CoherenceServerMBean)((CoherenceServerMBean)this.createCopy((AbstractDescriptorBean)oldCoherenceServers[i], includeObsolete));
               }

               copy.setCoherenceServers(newCoherenceServers);
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigurationAuditType")) && this.bean.isConfigurationAuditTypeSet()) {
               copy.setConfigurationAuditType(this.bean.getConfigurationAuditType());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigurationVersion")) && this.bean.isConfigurationVersionSet()) {
               copy.setConfigurationVersion(this.bean.getConfigurationVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("ConsoleContextPath")) && this.bean.isConsoleContextPathSet()) {
               copy.setConsoleContextPath(this.bean.getConsoleContextPath());
            }

            if ((excludeProps == null || !excludeProps.contains("ConsoleExtensionDirectory")) && this.bean.isConsoleExtensionDirectorySet()) {
               copy.setConsoleExtensionDirectory(this.bean.getConsoleExtensionDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomResources")) && this.bean.isCustomResourcesSet() && !copy._isSet(95)) {
               CustomResourceMBean[] oldCustomResources = this.bean.getCustomResources();
               CustomResourceMBean[] newCustomResources = new CustomResourceMBean[oldCustomResources.length];

               for(i = 0; i < newCustomResources.length; ++i) {
                  newCustomResources[i] = (CustomResourceMBean)((CustomResourceMBean)this.createCopy((AbstractDescriptorBean)oldCustomResources[i], includeObsolete));
               }

               copy.setCustomResources(newCustomResources);
            }

            if ((excludeProps == null || !excludeProps.contains("DBPassiveModeGracePeriodSeconds")) && this.bean.isDBPassiveModeGracePeriodSecondsSet()) {
               copy.setDBPassiveModeGracePeriodSeconds(this.bean.getDBPassiveModeGracePeriodSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugPatches")) && this.bean.isDebugPatchesSet() && !copy._isSet(152)) {
               Object o = this.bean.getDebugPatches();
               copy.setDebugPatches((DebugPatchesMBean)null);
               copy.setDebugPatches(o == null ? null : (DebugPatchesMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentConfiguration")) && this.bean.isDeploymentConfigurationSet() && !copy._isSet(17)) {
               Object o = this.bean.getDeploymentConfiguration();
               copy.setDeploymentConfiguration((DeploymentConfigurationMBean)null);
               copy.setDeploymentConfiguration(o == null ? null : (DeploymentConfigurationMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DomainLibraries")) && this.bean.isDomainLibrariesSet() && !copy._isSet(51)) {
               DomainLibraryMBean[] oldDomainLibraries = this.bean.getDomainLibraries();
               DomainLibraryMBean[] newDomainLibraries = new DomainLibraryMBean[oldDomainLibraries.length];

               for(i = 0; i < newDomainLibraries.length; ++i) {
                  newDomainLibraries[i] = (DomainLibraryMBean)((DomainLibraryMBean)this.createCopy((AbstractDescriptorBean)oldDomainLibraries[i], includeObsolete));
               }

               copy.setDomainLibraries(newDomainLibraries);
            }

            if ((excludeProps == null || !excludeProps.contains("DomainVersion")) && this.bean.isDomainVersionSet()) {
               copy.setDomainVersion(this.bean.getDomainVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("EJBContainer")) && this.bean.isEJBContainerSet() && !copy._isSet(72)) {
               Object o = this.bean.getEJBContainer();
               copy.setEJBContainer((EJBContainerMBean)null);
               copy.setEJBContainer(o == null ? null : (EJBContainerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EmbeddedLDAP")) && this.bean.isEmbeddedLDAPSet() && !copy._isSet(36)) {
               Object o = this.bean.getEmbeddedLDAP();
               copy.setEmbeddedLDAP((EmbeddedLDAPMBean)null);
               copy.setEmbeddedLDAP(o == null ? null : (EmbeddedLDAPMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FileStores")) && this.bean.isFileStoresSet() && !copy._isSet(91)) {
               FileStoreMBean[] oldFileStores = this.bean.getFileStores();
               FileStoreMBean[] newFileStores = new FileStoreMBean[oldFileStores.length];

               for(i = 0; i < newFileStores.length; ++i) {
                  newFileStores[i] = (FileStoreMBean)((FileStoreMBean)this.createCopy((AbstractDescriptorBean)oldFileStores[i], includeObsolete));
               }

               copy.setFileStores(newFileStores);
            }

            if ((excludeProps == null || !excludeProps.contains("FileT3s")) && this.bean.isFileT3sSet() && !copy._isSet(33)) {
               FileT3MBean[] oldFileT3s = this.bean.getFileT3s();
               FileT3MBean[] newFileT3s = new FileT3MBean[oldFileT3s.length];

               for(i = 0; i < newFileT3s.length; ++i) {
                  newFileT3s[i] = (FileT3MBean)((FileT3MBean)this.createCopy((AbstractDescriptorBean)oldFileT3s[i], includeObsolete));
               }

               copy.setFileT3s(newFileT3s);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ForeignJMSConnectionFactories")) && this.bean.isForeignJMSConnectionFactoriesSet() && !copy._isSet(115)) {
               ForeignJMSConnectionFactoryMBean[] oldForeignJMSConnectionFactories = this.bean.getForeignJMSConnectionFactories();
               ForeignJMSConnectionFactoryMBean[] newForeignJMSConnectionFactories = new ForeignJMSConnectionFactoryMBean[oldForeignJMSConnectionFactories.length];

               for(i = 0; i < newForeignJMSConnectionFactories.length; ++i) {
                  newForeignJMSConnectionFactories[i] = (ForeignJMSConnectionFactoryMBean)((ForeignJMSConnectionFactoryMBean)this.createCopy((AbstractDescriptorBean)oldForeignJMSConnectionFactories[i], includeObsolete));
               }

               copy.setForeignJMSConnectionFactories(newForeignJMSConnectionFactories);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ForeignJMSDestinations")) && this.bean.isForeignJMSDestinationsSet() && !copy._isSet(116)) {
               ForeignJMSDestinationMBean[] oldForeignJMSDestinations = this.bean.getForeignJMSDestinations();
               ForeignJMSDestinationMBean[] newForeignJMSDestinations = new ForeignJMSDestinationMBean[oldForeignJMSDestinations.length];

               for(i = 0; i < newForeignJMSDestinations.length; ++i) {
                  newForeignJMSDestinations[i] = (ForeignJMSDestinationMBean)((ForeignJMSDestinationMBean)this.createCopy((AbstractDescriptorBean)oldForeignJMSDestinations[i], includeObsolete));
               }

               copy.setForeignJMSDestinations(newForeignJMSDestinations);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ForeignJMSServers")) && this.bean.isForeignJMSServersSet() && !copy._isSet(84)) {
               ForeignJMSServerMBean[] oldForeignJMSServers = this.bean.getForeignJMSServers();
               ForeignJMSServerMBean[] newForeignJMSServers = new ForeignJMSServerMBean[oldForeignJMSServers.length];

               for(i = 0; i < newForeignJMSServers.length; ++i) {
                  newForeignJMSServers[i] = (ForeignJMSServerMBean)((ForeignJMSServerMBean)this.createCopy((AbstractDescriptorBean)oldForeignJMSServers[i], includeObsolete));
               }

               copy.setForeignJMSServers(newForeignJMSServers);
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignJNDIProviders")) && this.bean.isForeignJNDIProvidersSet() && !copy._isSet(96)) {
               ForeignJNDIProviderMBean[] oldForeignJNDIProviders = this.bean.getForeignJNDIProviders();
               ForeignJNDIProviderMBean[] newForeignJNDIProviders = new ForeignJNDIProviderMBean[oldForeignJNDIProviders.length];

               for(i = 0; i < newForeignJNDIProviders.length; ++i) {
                  newForeignJNDIProviders[i] = (ForeignJNDIProviderMBean)((ForeignJNDIProviderMBean)this.createCopy((AbstractDescriptorBean)oldForeignJNDIProviders[i], includeObsolete));
               }

               copy.setForeignJNDIProviders(newForeignJNDIProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("InstalledSoftwareVersion")) && this.bean.isInstalledSoftwareVersionSet()) {
               copy.setInstalledSoftwareVersion(this.bean.getInstalledSoftwareVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("Interceptors")) && this.bean.isInterceptorsSet() && !copy._isSet(150)) {
               Object o = this.bean.getInterceptors();
               copy.setInterceptors((InterceptorsMBean)null);
               copy.setInterceptors(o == null ? null : (InterceptorsMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCStores")) && this.bean.isJDBCStoresSet() && !copy._isSet(93)) {
               JDBCStoreMBean[] oldJDBCStores = this.bean.getJDBCStores();
               JDBCStoreMBean[] newJDBCStores = new JDBCStoreMBean[oldJDBCStores.length];

               for(i = 0; i < newJDBCStores.length; ++i) {
                  newJDBCStores[i] = (JDBCStoreMBean)((JDBCStoreMBean)this.createCopy((AbstractDescriptorBean)oldJDBCStores[i], includeObsolete));
               }

               copy.setJDBCStores(newJDBCStores);
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCSystemResources")) && this.bean.isJDBCSystemResourcesSet() && !copy._isSet(100)) {
               JDBCSystemResourceMBean[] oldJDBCSystemResources = this.bean.getJDBCSystemResources();
               JDBCSystemResourceMBean[] newJDBCSystemResources = new JDBCSystemResourceMBean[oldJDBCSystemResources.length];

               for(i = 0; i < newJDBCSystemResources.length; ++i) {
                  newJDBCSystemResources[i] = (JDBCSystemResourceMBean)((JDBCSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldJDBCSystemResources[i], includeObsolete));
               }

               copy.setJDBCSystemResources(newJDBCSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSBridgeDestinations")) && this.bean.isJMSBridgeDestinationsSet() && !copy._isSet(82)) {
               JMSBridgeDestinationMBean[] oldJMSBridgeDestinations = this.bean.getJMSBridgeDestinations();
               JMSBridgeDestinationMBean[] newJMSBridgeDestinations = new JMSBridgeDestinationMBean[oldJMSBridgeDestinations.length];

               for(i = 0; i < newJMSBridgeDestinations.length; ++i) {
                  newJMSBridgeDestinations[i] = (JMSBridgeDestinationMBean)((JMSBridgeDestinationMBean)this.createCopy((AbstractDescriptorBean)oldJMSBridgeDestinations[i], includeObsolete));
               }

               copy.setJMSBridgeDestinations(newJMSBridgeDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSConnectionConsumers")) && this.bean.isJMSConnectionConsumersSet() && !copy._isSet(117)) {
               JMSConnectionConsumerMBean[] oldJMSConnectionConsumers = this.bean.getJMSConnectionConsumers();
               JMSConnectionConsumerMBean[] newJMSConnectionConsumers = new JMSConnectionConsumerMBean[oldJMSConnectionConsumers.length];

               for(i = 0; i < newJMSConnectionConsumers.length; ++i) {
                  newJMSConnectionConsumers[i] = (JMSConnectionConsumerMBean)((JMSConnectionConsumerMBean)this.createCopy((AbstractDescriptorBean)oldJMSConnectionConsumers[i], includeObsolete));
               }

               copy.setJMSConnectionConsumers(newJMSConnectionConsumers);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSConnectionFactories")) && this.bean.isJMSConnectionFactoriesSet() && !copy._isSet(80)) {
               JMSConnectionFactoryMBean[] oldJMSConnectionFactories = this.bean.getJMSConnectionFactories();
               JMSConnectionFactoryMBean[] newJMSConnectionFactories = new JMSConnectionFactoryMBean[oldJMSConnectionFactories.length];

               for(i = 0; i < newJMSConnectionFactories.length; ++i) {
                  newJMSConnectionFactories[i] = (JMSConnectionFactoryMBean)((JMSConnectionFactoryMBean)this.createCopy((AbstractDescriptorBean)oldJMSConnectionFactories[i], includeObsolete));
               }

               copy.setJMSConnectionFactories(newJMSConnectionFactories);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDestinationKeys")) && this.bean.isJMSDestinationKeysSet() && !copy._isSet(79)) {
               JMSDestinationKeyMBean[] oldJMSDestinationKeys = this.bean.getJMSDestinationKeys();
               JMSDestinationKeyMBean[] newJMSDestinationKeys = new JMSDestinationKeyMBean[oldJMSDestinationKeys.length];

               for(i = 0; i < newJMSDestinationKeys.length; ++i) {
                  newJMSDestinationKeys[i] = (JMSDestinationKeyMBean)((JMSDestinationKeyMBean)this.createCopy((AbstractDescriptorBean)oldJMSDestinationKeys[i], includeObsolete));
               }

               copy.setJMSDestinationKeys(newJMSDestinationKeys);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDestinations")) && this.bean.isJMSDestinationsSet() && !copy._isSet(62)) {
               JMSDestinationMBean[] oldJMSDestinations = this.bean.getJMSDestinations();
               JMSDestinationMBean[] newJMSDestinations = new JMSDestinationMBean[oldJMSDestinations.length];

               for(i = 0; i < newJMSDestinations.length; ++i) {
                  newJMSDestinations[i] = (JMSDestinationMBean)((JMSDestinationMBean)this.createCopy((AbstractDescriptorBean)oldJMSDestinations[i], includeObsolete));
               }

               copy.setJMSDestinations(newJMSDestinations);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDistributedQueueMembers")) && this.bean.isJMSDistributedQueueMembersSet() && !copy._isSet(105)) {
               JMSDistributedQueueMemberMBean[] oldJMSDistributedQueueMembers = this.bean.getJMSDistributedQueueMembers();
               JMSDistributedQueueMemberMBean[] newJMSDistributedQueueMembers = new JMSDistributedQueueMemberMBean[oldJMSDistributedQueueMembers.length];

               for(i = 0; i < newJMSDistributedQueueMembers.length; ++i) {
                  newJMSDistributedQueueMembers[i] = (JMSDistributedQueueMemberMBean)((JMSDistributedQueueMemberMBean)this.createCopy((AbstractDescriptorBean)oldJMSDistributedQueueMembers[i], includeObsolete));
               }

               copy.setJMSDistributedQueueMembers(newJMSDistributedQueueMembers);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDistributedQueues")) && this.bean.isJMSDistributedQueuesSet() && !copy._isSet(65)) {
               JMSDistributedQueueMBean[] oldJMSDistributedQueues = this.bean.getJMSDistributedQueues();
               JMSDistributedQueueMBean[] newJMSDistributedQueues = new JMSDistributedQueueMBean[oldJMSDistributedQueues.length];

               for(i = 0; i < newJMSDistributedQueues.length; ++i) {
                  newJMSDistributedQueues[i] = (JMSDistributedQueueMBean)((JMSDistributedQueueMBean)this.createCopy((AbstractDescriptorBean)oldJMSDistributedQueues[i], includeObsolete));
               }

               copy.setJMSDistributedQueues(newJMSDistributedQueues);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDistributedTopicMembers")) && this.bean.isJMSDistributedTopicMembersSet() && !copy._isSet(106)) {
               JMSDistributedTopicMemberMBean[] oldJMSDistributedTopicMembers = this.bean.getJMSDistributedTopicMembers();
               JMSDistributedTopicMemberMBean[] newJMSDistributedTopicMembers = new JMSDistributedTopicMemberMBean[oldJMSDistributedTopicMembers.length];

               for(i = 0; i < newJMSDistributedTopicMembers.length; ++i) {
                  newJMSDistributedTopicMembers[i] = (JMSDistributedTopicMemberMBean)((JMSDistributedTopicMemberMBean)this.createCopy((AbstractDescriptorBean)oldJMSDistributedTopicMembers[i], includeObsolete));
               }

               copy.setJMSDistributedTopicMembers(newJMSDistributedTopicMembers);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDistributedTopics")) && this.bean.isJMSDistributedTopicsSet() && !copy._isSet(66)) {
               JMSDistributedTopicMBean[] oldJMSDistributedTopics = this.bean.getJMSDistributedTopics();
               JMSDistributedTopicMBean[] newJMSDistributedTopics = new JMSDistributedTopicMBean[oldJMSDistributedTopics.length];

               for(i = 0; i < newJMSDistributedTopics.length; ++i) {
                  newJMSDistributedTopics[i] = (JMSDistributedTopicMBean)((JMSDistributedTopicMBean)this.createCopy((AbstractDescriptorBean)oldJMSDistributedTopics[i], includeObsolete));
               }

               copy.setJMSDistributedTopics(newJMSDistributedTopics);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSFileStores")) && this.bean.isJMSFileStoresSet() && !copy._isSet(61)) {
               JMSFileStoreMBean[] oldJMSFileStores = this.bean.getJMSFileStores();
               JMSFileStoreMBean[] newJMSFileStores = new JMSFileStoreMBean[oldJMSFileStores.length];

               for(i = 0; i < newJMSFileStores.length; ++i) {
                  newJMSFileStores[i] = (JMSFileStoreMBean)((JMSFileStoreMBean)this.createCopy((AbstractDescriptorBean)oldJMSFileStores[i], includeObsolete));
               }

               copy.setJMSFileStores(newJMSFileStores);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSJDBCStores")) && this.bean.isJMSJDBCStoresSet() && !copy._isSet(60)) {
               JMSJDBCStoreMBean[] oldJMSJDBCStores = this.bean.getJMSJDBCStores();
               JMSJDBCStoreMBean[] newJMSJDBCStores = new JMSJDBCStoreMBean[oldJMSJDBCStores.length];

               for(i = 0; i < newJMSJDBCStores.length; ++i) {
                  newJMSJDBCStores[i] = (JMSJDBCStoreMBean)((JMSJDBCStoreMBean)this.createCopy((AbstractDescriptorBean)oldJMSJDBCStores[i], includeObsolete));
               }

               copy.setJMSJDBCStores(newJMSJDBCStores);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSQueues")) && this.bean.isJMSQueuesSet() && !copy._isSet(63)) {
               JMSQueueMBean[] oldJMSQueues = this.bean.getJMSQueues();
               JMSQueueMBean[] newJMSQueues = new JMSQueueMBean[oldJMSQueues.length];

               for(i = 0; i < newJMSQueues.length; ++i) {
                  newJMSQueues[i] = (JMSQueueMBean)((JMSQueueMBean)this.createCopy((AbstractDescriptorBean)oldJMSQueues[i], includeObsolete));
               }

               copy.setJMSQueues(newJMSQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSServers")) && this.bean.isJMSServersSet() && !copy._isSet(58)) {
               JMSServerMBean[] oldJMSServers = this.bean.getJMSServers();
               JMSServerMBean[] newJMSServers = new JMSServerMBean[oldJMSServers.length];

               for(i = 0; i < newJMSServers.length; ++i) {
                  newJMSServers[i] = (JMSServerMBean)((JMSServerMBean)this.createCopy((AbstractDescriptorBean)oldJMSServers[i], includeObsolete));
               }

               copy.setJMSServers(newJMSServers);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSSessionPools")) && this.bean.isJMSSessionPoolsSet() && !copy._isSet(81)) {
               JMSSessionPoolMBean[] oldJMSSessionPools = this.bean.getJMSSessionPools();
               JMSSessionPoolMBean[] newJMSSessionPools = new JMSSessionPoolMBean[oldJMSSessionPools.length];

               for(i = 0; i < newJMSSessionPools.length; ++i) {
                  newJMSSessionPools[i] = (JMSSessionPoolMBean)((JMSSessionPoolMBean)this.createCopy((AbstractDescriptorBean)oldJMSSessionPools[i], includeObsolete));
               }

               copy.setJMSSessionPools(newJMSSessionPools);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSSystemResources")) && this.bean.isJMSSystemResourcesSet() && !copy._isSet(94)) {
               JMSSystemResourceMBean[] oldJMSSystemResources = this.bean.getJMSSystemResources();
               JMSSystemResourceMBean[] newJMSSystemResources = new JMSSystemResourceMBean[oldJMSSystemResources.length];

               for(i = 0; i < newJMSSystemResources.length; ++i) {
                  newJMSSystemResources[i] = (JMSSystemResourceMBean)((JMSSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldJMSSystemResources[i], includeObsolete));
               }

               copy.setJMSSystemResources(newJMSSystemResources);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSTemplates")) && this.bean.isJMSTemplatesSet() && !copy._isSet(67)) {
               JMSTemplateMBean[] oldJMSTemplates = this.bean.getJMSTemplates();
               JMSTemplateMBean[] newJMSTemplates = new JMSTemplateMBean[oldJMSTemplates.length];

               for(i = 0; i < newJMSTemplates.length; ++i) {
                  newJMSTemplates[i] = (JMSTemplateMBean)((JMSTemplateMBean)this.createCopy((AbstractDescriptorBean)oldJMSTemplates[i], includeObsolete));
               }

               copy.setJMSTemplates(newJMSTemplates);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSTopics")) && this.bean.isJMSTopicsSet() && !copy._isSet(64)) {
               JMSTopicMBean[] oldJMSTopics = this.bean.getJMSTopics();
               JMSTopicMBean[] newJMSTopics = new JMSTopicMBean[oldJMSTopics.length];

               for(i = 0; i < newJMSTopics.length; ++i) {
                  newJMSTopics[i] = (JMSTopicMBean)((JMSTopicMBean)this.createCopy((AbstractDescriptorBean)oldJMSTopics[i], includeObsolete));
               }

               copy.setJMSTopics(newJMSTopics);
            }

            if ((excludeProps == null || !excludeProps.contains("JMX")) && this.bean.isJMXSet() && !copy._isSet(75)) {
               Object o = this.bean.getJMX();
               copy.setJMX((JMXMBean)null);
               copy.setJMX(o == null ? null : (JMXMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JPA")) && this.bean.isJPASet() && !copy._isSet(16)) {
               Object o = this.bean.getJPA();
               copy.setJPA((JPAMBean)null);
               copy.setJPA(o == null ? null : (JPAMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JTA")) && this.bean.isJTASet() && !copy._isSet(15)) {
               Object o = this.bean.getJTA();
               copy.setJTA((JTAMBean)null);
               copy.setJTA(o == null ? null : (JTAMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JoltConnectionPools")) && this.bean.isJoltConnectionPoolsSet() && !copy._isSet(89)) {
               JoltConnectionPoolMBean[] oldJoltConnectionPools = this.bean.getJoltConnectionPools();
               JoltConnectionPoolMBean[] newJoltConnectionPools = new JoltConnectionPoolMBean[oldJoltConnectionPools.length];

               for(i = 0; i < newJoltConnectionPools.length; ++i) {
                  newJoltConnectionPools[i] = (JoltConnectionPoolMBean)((JoltConnectionPoolMBean)this.createCopy((AbstractDescriptorBean)oldJoltConnectionPools[i], includeObsolete));
               }

               copy.setJoltConnectionPools(newJoltConnectionPools);
            }

            if ((excludeProps == null || !excludeProps.contains("LastModificationTime")) && this.bean.isLastModificationTimeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("Libraries")) && this.bean.isLibrariesSet() && !copy._isSet(50)) {
               LibraryMBean[] oldLibraries = this.bean.getLibraries();
               LibraryMBean[] newLibraries = new LibraryMBean[oldLibraries.length];

               for(i = 0; i < newLibraries.length; ++i) {
                  newLibraries[i] = (LibraryMBean)((LibraryMBean)this.createCopy((AbstractDescriptorBean)oldLibraries[i], includeObsolete));
               }

               copy.setLibraries(newLibraries);
            }

            if ((excludeProps == null || !excludeProps.contains("LifecycleManagerConfig")) && this.bean.isLifecycleManagerConfigSet() && !copy._isSet(158)) {
               Object o = this.bean.getLifecycleManagerConfig();
               copy.setLifecycleManagerConfig((LifecycleManagerConfigMBean)null);
               copy.setLifecycleManagerConfig(o == null ? null : (LifecycleManagerConfigMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LifecycleManagerEndPoints")) && this.bean.isLifecycleManagerEndPointsSet() && !copy._isSet(149)) {
               LifecycleManagerEndPointMBean[] oldLifecycleManagerEndPoints = this.bean.getLifecycleManagerEndPoints();
               LifecycleManagerEndPointMBean[] newLifecycleManagerEndPoints = new LifecycleManagerEndPointMBean[oldLifecycleManagerEndPoints.length];

               for(i = 0; i < newLifecycleManagerEndPoints.length; ++i) {
                  newLifecycleManagerEndPoints[i] = (LifecycleManagerEndPointMBean)((LifecycleManagerEndPointMBean)this.createCopy((AbstractDescriptorBean)oldLifecycleManagerEndPoints[i], includeObsolete));
               }

               copy.setLifecycleManagerEndPoints(newLifecycleManagerEndPoints);
            }

            if ((excludeProps == null || !excludeProps.contains("Log")) && this.bean.isLogSet() && !copy._isSet(19)) {
               Object o = this.bean.getLog();
               copy.setLog((LogMBean)null);
               copy.setLog(o == null ? null : (LogMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LogFilters")) && this.bean.isLogFiltersSet() && !copy._isSet(90)) {
               LogFilterMBean[] oldLogFilters = this.bean.getLogFilters();
               LogFilterMBean[] newLogFilters = new LogFilterMBean[oldLogFilters.length];

               for(i = 0; i < newLogFilters.length; ++i) {
                  newLogFilters[i] = (LogFilterMBean)((LogFilterMBean)this.createCopy((AbstractDescriptorBean)oldLogFilters[i], includeObsolete));
               }

               copy.setLogFilters(newLogFilters);
            }

            if ((excludeProps == null || !excludeProps.contains("Machines")) && this.bean.isMachinesSet() && !copy._isSet(54)) {
               MachineMBean[] oldMachines = this.bean.getMachines();
               MachineMBean[] newMachines = new MachineMBean[oldMachines.length];

               for(i = 0; i < newMachines.length; ++i) {
                  newMachines[i] = (MachineMBean)((MachineMBean)this.createCopy((AbstractDescriptorBean)oldMachines[i], includeObsolete));
               }

               copy.setMachines(newMachines);
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(88)) {
               MailSessionMBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionMBean[] newMailSessions = new MailSessionMBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionMBean)((MailSessionMBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServiceTemplates")) && this.bean.isManagedExecutorServiceTemplatesSet() && !copy._isSet(142)) {
               ManagedExecutorServiceTemplateMBean[] oldManagedExecutorServiceTemplates = this.bean.getManagedExecutorServiceTemplates();
               ManagedExecutorServiceTemplateMBean[] newManagedExecutorServiceTemplates = new ManagedExecutorServiceTemplateMBean[oldManagedExecutorServiceTemplates.length];

               for(i = 0; i < newManagedExecutorServiceTemplates.length; ++i) {
                  newManagedExecutorServiceTemplates[i] = (ManagedExecutorServiceTemplateMBean)((ManagedExecutorServiceTemplateMBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServiceTemplates[i], includeObsolete));
               }

               copy.setManagedExecutorServiceTemplates(newManagedExecutorServiceTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServices")) && this.bean.isManagedExecutorServicesSet() && !copy._isSet(145)) {
               ManagedExecutorServiceMBean[] oldManagedExecutorServices = this.bean.getManagedExecutorServices();
               ManagedExecutorServiceMBean[] newManagedExecutorServices = new ManagedExecutorServiceMBean[oldManagedExecutorServices.length];

               for(i = 0; i < newManagedExecutorServices.length; ++i) {
                  newManagedExecutorServices[i] = (ManagedExecutorServiceMBean)((ManagedExecutorServiceMBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServices[i], includeObsolete));
               }

               copy.setManagedExecutorServices(newManagedExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServiceTemplates")) && this.bean.isManagedScheduledExecutorServiceTemplatesSet() && !copy._isSet(143)) {
               ManagedScheduledExecutorServiceTemplateMBean[] oldManagedScheduledExecutorServiceTemplates = this.bean.getManagedScheduledExecutorServiceTemplates();
               ManagedScheduledExecutorServiceTemplateMBean[] newManagedScheduledExecutorServiceTemplates = new ManagedScheduledExecutorServiceTemplateMBean[oldManagedScheduledExecutorServiceTemplates.length];

               for(i = 0; i < newManagedScheduledExecutorServiceTemplates.length; ++i) {
                  newManagedScheduledExecutorServiceTemplates[i] = (ManagedScheduledExecutorServiceTemplateMBean)((ManagedScheduledExecutorServiceTemplateMBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServiceTemplates[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServiceTemplates(newManagedScheduledExecutorServiceTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServices")) && this.bean.isManagedScheduledExecutorServicesSet() && !copy._isSet(146)) {
               ManagedScheduledExecutorServiceMBean[] oldManagedScheduledExecutorServices = this.bean.getManagedScheduledExecutorServices();
               ManagedScheduledExecutorServiceMBean[] newManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceMBean[oldManagedScheduledExecutorServices.length];

               for(i = 0; i < newManagedScheduledExecutorServices.length; ++i) {
                  newManagedScheduledExecutorServices[i] = (ManagedScheduledExecutorServiceMBean)((ManagedScheduledExecutorServiceMBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServices[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServices(newManagedScheduledExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactories")) && this.bean.isManagedThreadFactoriesSet() && !copy._isSet(147)) {
               ManagedThreadFactoryMBean[] oldManagedThreadFactories = this.bean.getManagedThreadFactories();
               ManagedThreadFactoryMBean[] newManagedThreadFactories = new ManagedThreadFactoryMBean[oldManagedThreadFactories.length];

               for(i = 0; i < newManagedThreadFactories.length; ++i) {
                  newManagedThreadFactories[i] = (ManagedThreadFactoryMBean)((ManagedThreadFactoryMBean)this.createCopy((AbstractDescriptorBean)oldManagedThreadFactories[i], includeObsolete));
               }

               copy.setManagedThreadFactories(newManagedThreadFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactoryTemplates")) && this.bean.isManagedThreadFactoryTemplatesSet() && !copy._isSet(144)) {
               ManagedThreadFactoryTemplateMBean[] oldManagedThreadFactoryTemplates = this.bean.getManagedThreadFactoryTemplates();
               ManagedThreadFactoryTemplateMBean[] newManagedThreadFactoryTemplates = new ManagedThreadFactoryTemplateMBean[oldManagedThreadFactoryTemplates.length];

               for(i = 0; i < newManagedThreadFactoryTemplates.length; ++i) {
                  newManagedThreadFactoryTemplates[i] = (ManagedThreadFactoryTemplateMBean)((ManagedThreadFactoryTemplateMBean)this.createCopy((AbstractDescriptorBean)oldManagedThreadFactoryTemplates[i], includeObsolete));
               }

               copy.setManagedThreadFactoryTemplates(newManagedThreadFactoryTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentLongRunningRequests")) && this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               copy.setMaxConcurrentLongRunningRequests(this.bean.getMaxConcurrentLongRunningRequests());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentNewThreads")) && this.bean.isMaxConcurrentNewThreadsSet()) {
               copy.setMaxConcurrentNewThreads(this.bean.getMaxConcurrentNewThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingBridges")) && this.bean.isMessagingBridgesSet() && !copy._isSet(34)) {
               MessagingBridgeMBean[] oldMessagingBridges = this.bean.getMessagingBridges();
               MessagingBridgeMBean[] newMessagingBridges = new MessagingBridgeMBean[oldMessagingBridges.length];

               for(i = 0; i < newMessagingBridges.length; ++i) {
                  newMessagingBridges[i] = (MessagingBridgeMBean)((MessagingBridgeMBean)this.createCopy((AbstractDescriptorBean)oldMessagingBridges[i], includeObsolete));
               }

               copy.setMessagingBridges(newMessagingBridges);
            }

            if ((excludeProps == null || !excludeProps.contains("MigratableRMIServices")) && this.bean.isMigratableRMIServicesSet() && !copy._isSet(103)) {
               MigratableRMIServiceMBean[] oldMigratableRMIServices = this.bean.getMigratableRMIServices();
               MigratableRMIServiceMBean[] newMigratableRMIServices = new MigratableRMIServiceMBean[oldMigratableRMIServices.length];

               for(i = 0; i < newMigratableRMIServices.length; ++i) {
                  newMigratableRMIServices[i] = (MigratableRMIServiceMBean)((MigratableRMIServiceMBean)this.createCopy((AbstractDescriptorBean)oldMigratableRMIServices[i], includeObsolete));
               }

               copy.setMigratableRMIServices(newMigratableRMIServices);
            }

            if ((excludeProps == null || !excludeProps.contains("MigratableTargets")) && this.bean.isMigratableTargetsSet() && !copy._isSet(71)) {
               MigratableTargetMBean[] oldMigratableTargets = this.bean.getMigratableTargets();
               MigratableTargetMBean[] newMigratableTargets = new MigratableTargetMBean[oldMigratableTargets.length];

               for(i = 0; i < newMigratableTargets.length; ++i) {
                  newMigratableTargets[i] = (MigratableTargetMBean)((MigratableTargetMBean)this.createCopy((AbstractDescriptorBean)oldMigratableTargets[i], includeObsolete));
               }

               copy.setMigratableTargets(newMigratableTargets);
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("NetworkChannels")) && this.bean.isNetworkChannelsSet() && !copy._isSet(68)) {
               NetworkChannelMBean[] oldNetworkChannels = this.bean.getNetworkChannels();
               NetworkChannelMBean[] newNetworkChannels = new NetworkChannelMBean[oldNetworkChannels.length];

               for(i = 0; i < newNetworkChannels.length; ++i) {
                  newNetworkChannels[i] = (NetworkChannelMBean)((NetworkChannelMBean)this.createCopy((AbstractDescriptorBean)oldNetworkChannels[i], includeObsolete));
               }

               copy.setNetworkChannels(newNetworkChannels);
            }

            if ((excludeProps == null || !excludeProps.contains("OptionalFeatureDeployment")) && this.bean.isOptionalFeatureDeploymentSet() && !copy._isSet(157)) {
               Object o = this.bean.getOptionalFeatureDeployment();
               copy.setOptionalFeatureDeployment((OptionalFeatureDeploymentMBean)null);
               copy.setOptionalFeatureDeployment(o == null ? null : (OptionalFeatureDeploymentMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("OsgiFrameworks")) && this.bean.isOsgiFrameworksSet() && !copy._isSet(129)) {
               OsgiFrameworkMBean[] oldOsgiFrameworks = this.bean.getOsgiFrameworks();
               OsgiFrameworkMBean[] newOsgiFrameworks = new OsgiFrameworkMBean[oldOsgiFrameworks.length];

               for(i = 0; i < newOsgiFrameworks.length; ++i) {
                  newOsgiFrameworks[i] = (OsgiFrameworkMBean)((OsgiFrameworkMBean)this.createCopy((AbstractDescriptorBean)oldOsgiFrameworks[i], includeObsolete));
               }

               copy.setOsgiFrameworks(newOsgiFrameworks);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PartitionTemplates")) && this.bean.isPartitionTemplatesSet() && !copy._isSet(148)) {
               PartitionTemplateMBean[] oldPartitionTemplates = this.bean.getPartitionTemplates();
               PartitionTemplateMBean[] newPartitionTemplates = new PartitionTemplateMBean[oldPartitionTemplates.length];

               for(i = 0; i < newPartitionTemplates.length; ++i) {
                  newPartitionTemplates[i] = (PartitionTemplateMBean)((PartitionTemplateMBean)this.createCopy((AbstractDescriptorBean)oldPartitionTemplates[i], includeObsolete));
               }

               copy.setPartitionTemplates(newPartitionTemplates);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PartitionUriSpace")) && this.bean.isPartitionUriSpaceSet()) {
               copy.setPartitionUriSpace(this.bean.getPartitionUriSpace());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PartitionWorkManagers")) && this.bean.isPartitionWorkManagersSet() && !copy._isSet(153)) {
               PartitionWorkManagerMBean[] oldPartitionWorkManagers = this.bean.getPartitionWorkManagers();
               PartitionWorkManagerMBean[] newPartitionWorkManagers = new PartitionWorkManagerMBean[oldPartitionWorkManagers.length];

               for(i = 0; i < newPartitionWorkManagers.length; ++i) {
                  newPartitionWorkManagers[i] = (PartitionWorkManagerMBean)((PartitionWorkManagerMBean)this.createCopy((AbstractDescriptorBean)oldPartitionWorkManagers[i], includeObsolete));
               }

               copy.setPartitionWorkManagers(newPartitionWorkManagers);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Partitions")) && this.bean.isPartitionsSet() && !copy._isSet(134)) {
               PartitionMBean[] oldPartitions = this.bean.getPartitions();
               PartitionMBean[] newPartitions = new PartitionMBean[oldPartitions.length];

               for(i = 0; i < newPartitions.length; ++i) {
                  newPartitions[i] = (PartitionMBean)((PartitionMBean)this.createCopy((AbstractDescriptorBean)oldPartitions[i], includeObsolete));
               }

               copy.setPartitions(newPartitions);
            }

            if ((excludeProps == null || !excludeProps.contains("PathServices")) && this.bean.isPathServicesSet() && !copy._isSet(78)) {
               PathServiceMBean[] oldPathServices = this.bean.getPathServices();
               PathServiceMBean[] newPathServices = new PathServiceMBean[oldPathServices.length];

               for(i = 0; i < newPathServices.length; ++i) {
                  newPathServices[i] = (PathServiceMBean)((PathServiceMBean)this.createCopy((AbstractDescriptorBean)oldPathServices[i], includeObsolete));
               }

               copy.setPathServices(newPathServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ReplicatedStores")) && this.bean.isReplicatedStoresSet() && !copy._isSet(92)) {
               ReplicatedStoreMBean[] oldReplicatedStores = this.bean.getReplicatedStores();
               ReplicatedStoreMBean[] newReplicatedStores = new ReplicatedStoreMBean[oldReplicatedStores.length];

               for(i = 0; i < newReplicatedStores.length; ++i) {
                  newReplicatedStores[i] = (ReplicatedStoreMBean)((ReplicatedStoreMBean)this.createCopy((AbstractDescriptorBean)oldReplicatedStores[i], includeObsolete));
               }

               copy.setReplicatedStores(newReplicatedStores);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ResourceGroupTemplates")) && this.bean.isResourceGroupTemplatesSet() && !copy._isSet(137)) {
               ResourceGroupTemplateMBean[] oldResourceGroupTemplates = this.bean.getResourceGroupTemplates();
               ResourceGroupTemplateMBean[] newResourceGroupTemplates = new ResourceGroupTemplateMBean[oldResourceGroupTemplates.length];

               for(i = 0; i < newResourceGroupTemplates.length; ++i) {
                  newResourceGroupTemplates[i] = (ResourceGroupTemplateMBean)((ResourceGroupTemplateMBean)this.createCopy((AbstractDescriptorBean)oldResourceGroupTemplates[i], includeObsolete));
               }

               copy.setResourceGroupTemplates(newResourceGroupTemplates);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ResourceGroups")) && this.bean.isResourceGroupsSet() && !copy._isSet(136)) {
               ResourceGroupMBean[] oldResourceGroups = this.bean.getResourceGroups();
               ResourceGroupMBean[] newResourceGroups = new ResourceGroupMBean[oldResourceGroups.length];

               for(i = 0; i < newResourceGroups.length; ++i) {
                  newResourceGroups[i] = (ResourceGroupMBean)((ResourceGroupMBean)this.createCopy((AbstractDescriptorBean)oldResourceGroups[i], includeObsolete));
               }

               copy.setResourceGroups(newResourceGroups);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ResourceManagement")) && this.bean.isResourceManagementSet() && !copy._isSet(77)) {
               Object o = this.bean.getResourceManagement();
               copy.setResourceManagement((ResourceManagementMBean)null);
               copy.setResourceManagement(o == null ? null : (ResourceManagementMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RestfulManagementServices")) && this.bean.isRestfulManagementServicesSet() && !copy._isSet(126)) {
               Object o = this.bean.getRestfulManagementServices();
               copy.setRestfulManagementServices((RestfulManagementServicesMBean)null);
               copy.setRestfulManagementServices(o == null ? null : (RestfulManagementServicesMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RootDirectory")) && this.bean.isRootDirectorySet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("SAFAgents")) && this.bean.isSAFAgentsSet() && !copy._isSet(102)) {
               SAFAgentMBean[] oldSAFAgents = this.bean.getSAFAgents();
               SAFAgentMBean[] newSAFAgents = new SAFAgentMBean[oldSAFAgents.length];

               for(i = 0; i < newSAFAgents.length; ++i) {
                  newSAFAgents[i] = (SAFAgentMBean)((SAFAgentMBean)this.createCopy((AbstractDescriptorBean)oldSAFAgents[i], includeObsolete));
               }

               copy.setSAFAgents(newSAFAgents);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPAgent")) && this.bean.isSNMPAgentSet() && !copy._isSet(20)) {
               Object o = this.bean.getSNMPAgent();
               copy.setSNMPAgent((SNMPAgentMBean)null);
               copy.setSNMPAgent(o == null ? null : (SNMPAgentMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPAgentDeployments")) && this.bean.isSNMPAgentDeploymentsSet() && !copy._isSet(21)) {
               SNMPAgentDeploymentMBean[] oldSNMPAgentDeployments = this.bean.getSNMPAgentDeployments();
               SNMPAgentDeploymentMBean[] newSNMPAgentDeployments = new SNMPAgentDeploymentMBean[oldSNMPAgentDeployments.length];

               for(i = 0; i < newSNMPAgentDeployments.length; ++i) {
                  newSNMPAgentDeployments[i] = (SNMPAgentDeploymentMBean)((SNMPAgentDeploymentMBean)this.createCopy((AbstractDescriptorBean)oldSNMPAgentDeployments[i], includeObsolete));
               }

               copy.setSNMPAgentDeployments(newSNMPAgentDeployments);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPAttributeChanges")) && this.bean.isSNMPAttributeChangesSet() && !copy._isSet(113)) {
               SNMPAttributeChangeMBean[] oldSNMPAttributeChanges = this.bean.getSNMPAttributeChanges();
               SNMPAttributeChangeMBean[] newSNMPAttributeChanges = new SNMPAttributeChangeMBean[oldSNMPAttributeChanges.length];

               for(i = 0; i < newSNMPAttributeChanges.length; ++i) {
                  newSNMPAttributeChanges[i] = (SNMPAttributeChangeMBean)((SNMPAttributeChangeMBean)this.createCopy((AbstractDescriptorBean)oldSNMPAttributeChanges[i], includeObsolete));
               }

               copy.setSNMPAttributeChanges(newSNMPAttributeChanges);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPCounterMonitors")) && this.bean.isSNMPCounterMonitorsSet() && !copy._isSet(111)) {
               SNMPCounterMonitorMBean[] oldSNMPCounterMonitors = this.bean.getSNMPCounterMonitors();
               SNMPCounterMonitorMBean[] newSNMPCounterMonitors = new SNMPCounterMonitorMBean[oldSNMPCounterMonitors.length];

               for(i = 0; i < newSNMPCounterMonitors.length; ++i) {
                  newSNMPCounterMonitors[i] = (SNMPCounterMonitorMBean)((SNMPCounterMonitorMBean)this.createCopy((AbstractDescriptorBean)oldSNMPCounterMonitors[i], includeObsolete));
               }

               copy.setSNMPCounterMonitors(newSNMPCounterMonitors);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPGaugeMonitors")) && this.bean.isSNMPGaugeMonitorsSet() && !copy._isSet(109)) {
               SNMPGaugeMonitorMBean[] oldSNMPGaugeMonitors = this.bean.getSNMPGaugeMonitors();
               SNMPGaugeMonitorMBean[] newSNMPGaugeMonitors = new SNMPGaugeMonitorMBean[oldSNMPGaugeMonitors.length];

               for(i = 0; i < newSNMPGaugeMonitors.length; ++i) {
                  newSNMPGaugeMonitors[i] = (SNMPGaugeMonitorMBean)((SNMPGaugeMonitorMBean)this.createCopy((AbstractDescriptorBean)oldSNMPGaugeMonitors[i], includeObsolete));
               }

               copy.setSNMPGaugeMonitors(newSNMPGaugeMonitors);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPLogFilters")) && this.bean.isSNMPLogFiltersSet() && !copy._isSet(112)) {
               SNMPLogFilterMBean[] oldSNMPLogFilters = this.bean.getSNMPLogFilters();
               SNMPLogFilterMBean[] newSNMPLogFilters = new SNMPLogFilterMBean[oldSNMPLogFilters.length];

               for(i = 0; i < newSNMPLogFilters.length; ++i) {
                  newSNMPLogFilters[i] = (SNMPLogFilterMBean)((SNMPLogFilterMBean)this.createCopy((AbstractDescriptorBean)oldSNMPLogFilters[i], includeObsolete));
               }

               copy.setSNMPLogFilters(newSNMPLogFilters);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPProxies")) && this.bean.isSNMPProxiesSet() && !copy._isSet(108)) {
               SNMPProxyMBean[] oldSNMPProxies = this.bean.getSNMPProxies();
               SNMPProxyMBean[] newSNMPProxies = new SNMPProxyMBean[oldSNMPProxies.length];

               for(i = 0; i < newSNMPProxies.length; ++i) {
                  newSNMPProxies[i] = (SNMPProxyMBean)((SNMPProxyMBean)this.createCopy((AbstractDescriptorBean)oldSNMPProxies[i], includeObsolete));
               }

               copy.setSNMPProxies(newSNMPProxies);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPStringMonitors")) && this.bean.isSNMPStringMonitorsSet() && !copy._isSet(110)) {
               SNMPStringMonitorMBean[] oldSNMPStringMonitors = this.bean.getSNMPStringMonitors();
               SNMPStringMonitorMBean[] newSNMPStringMonitors = new SNMPStringMonitorMBean[oldSNMPStringMonitors.length];

               for(i = 0; i < newSNMPStringMonitors.length; ++i) {
                  newSNMPStringMonitors[i] = (SNMPStringMonitorMBean)((SNMPStringMonitorMBean)this.createCopy((AbstractDescriptorBean)oldSNMPStringMonitors[i], includeObsolete));
               }

               copy.setSNMPStringMonitors(newSNMPStringMonitors);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SNMPTrapDestinations")) && this.bean.isSNMPTrapDestinationsSet() && !copy._isSet(107)) {
               SNMPTrapDestinationMBean[] oldSNMPTrapDestinations = this.bean.getSNMPTrapDestinations();
               SNMPTrapDestinationMBean[] newSNMPTrapDestinations = new SNMPTrapDestinationMBean[oldSNMPTrapDestinations.length];

               for(i = 0; i < newSNMPTrapDestinations.length; ++i) {
                  newSNMPTrapDestinations[i] = (SNMPTrapDestinationMBean)((SNMPTrapDestinationMBean)this.createCopy((AbstractDescriptorBean)oldSNMPTrapDestinations[i], includeObsolete));
               }

               copy.setSNMPTrapDestinations(newSNMPTrapDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityConfiguration")) && this.bean.isSecurityConfigurationSet() && !copy._isSet(14)) {
               Object o = this.bean.getSecurityConfiguration();
               copy.setSecurityConfiguration((SecurityConfigurationMBean)null);
               copy.setSecurityConfiguration(o == null ? null : (SecurityConfigurationMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SelfTuning")) && this.bean.isSelfTuningSet() && !copy._isSet(76)) {
               Object o = this.bean.getSelfTuning();
               copy.setSelfTuning((SelfTuningMBean)null);
               copy.setSelfTuning(o == null ? null : (SelfTuningMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServerMigrationHistorySize")) && this.bean.isServerMigrationHistorySizeSet()) {
               copy.setServerMigrationHistorySize(this.bean.getServerMigrationHistorySize());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerTemplates")) && this.bean.isServerTemplatesSet() && !copy._isSet(29)) {
               ServerTemplateMBean[] oldServerTemplates = this.bean.getServerTemplates();
               ServerTemplateMBean[] newServerTemplates = new ServerTemplateMBean[oldServerTemplates.length];

               for(i = 0; i < newServerTemplates.length; ++i) {
                  newServerTemplates[i] = (ServerTemplateMBean)((ServerTemplateMBean)this.createCopy((AbstractDescriptorBean)oldServerTemplates[i], includeObsolete));
               }

               copy.setServerTemplates(newServerTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("Servers")) && this.bean.isServersSet() && !copy._isSet(28)) {
               ServerMBean[] oldServers = this.bean.getServers();
               ServerMBean[] newServers = new ServerMBean[oldServers.length];

               for(i = 0; i < newServers.length; ++i) {
                  newServers[i] = (ServerMBean)((ServerMBean)this.createCopy((AbstractDescriptorBean)oldServers[i], includeObsolete));
               }

               copy.setServers(newServers);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceMigrationHistorySize")) && this.bean.isServiceMigrationHistorySizeSet()) {
               copy.setServiceMigrationHistorySize(this.bean.getServiceMigrationHistorySize());
            }

            if ((excludeProps == null || !excludeProps.contains("ShutdownClasses")) && this.bean.isShutdownClassesSet() && !copy._isSet(85)) {
               ShutdownClassMBean[] oldShutdownClasses = this.bean.getShutdownClasses();
               ShutdownClassMBean[] newShutdownClasses = new ShutdownClassMBean[oldShutdownClasses.length];

               for(i = 0; i < newShutdownClasses.length; ++i) {
                  newShutdownClasses[i] = (ShutdownClassMBean)((ShutdownClassMBean)this.createCopy((AbstractDescriptorBean)oldShutdownClasses[i], includeObsolete));
               }

               copy.setShutdownClasses(newShutdownClasses);
            }

            if ((excludeProps == null || !excludeProps.contains("SingletonServices")) && this.bean.isSingletonServicesSet() && !copy._isSet(87)) {
               SingletonServiceMBean[] oldSingletonServices = this.bean.getSingletonServices();
               SingletonServiceMBean[] newSingletonServices = new SingletonServiceMBean[oldSingletonServices.length];

               for(i = 0; i < newSingletonServices.length; ++i) {
                  newSingletonServices[i] = (SingletonServiceMBean)((SingletonServiceMBean)this.createCopy((AbstractDescriptorBean)oldSingletonServices[i], includeObsolete));
               }

               copy.setSingletonServices(newSingletonServices);
            }

            if ((excludeProps == null || !excludeProps.contains("SiteName")) && this.bean.isSiteNameSet()) {
               copy.setSiteName(this.bean.getSiteName());
            }

            if ((excludeProps == null || !excludeProps.contains("StartupClasses")) && this.bean.isStartupClassesSet() && !copy._isSet(86)) {
               StartupClassMBean[] oldStartupClasses = this.bean.getStartupClasses();
               StartupClassMBean[] newStartupClasses = new StartupClassMBean[oldStartupClasses.length];

               for(i = 0; i < newStartupClasses.length; ++i) {
                  newStartupClasses[i] = (StartupClassMBean)((StartupClassMBean)this.createCopy((AbstractDescriptorBean)oldStartupClasses[i], includeObsolete));
               }

               copy.setStartupClasses(newStartupClasses);
            }

            if ((excludeProps == null || !excludeProps.contains("SystemComponentConfigurations")) && this.bean.isSystemComponentConfigurationsSet() && !copy._isSet(128)) {
               SystemComponentConfigurationMBean[] oldSystemComponentConfigurations = this.bean.getSystemComponentConfigurations();
               SystemComponentConfigurationMBean[] newSystemComponentConfigurations = new SystemComponentConfigurationMBean[oldSystemComponentConfigurations.length];

               for(i = 0; i < newSystemComponentConfigurations.length; ++i) {
                  newSystemComponentConfigurations[i] = (SystemComponentConfigurationMBean)((SystemComponentConfigurationMBean)this.createCopy((AbstractDescriptorBean)oldSystemComponentConfigurations[i], includeObsolete));
               }

               copy.setSystemComponentConfigurations(newSystemComponentConfigurations);
            }

            if ((excludeProps == null || !excludeProps.contains("SystemComponents")) && this.bean.isSystemComponentsSet() && !copy._isSet(127)) {
               SystemComponentMBean[] oldSystemComponents = this.bean.getSystemComponents();
               SystemComponentMBean[] newSystemComponents = new SystemComponentMBean[oldSystemComponents.length];

               for(i = 0; i < newSystemComponents.length; ++i) {
                  newSystemComponents[i] = (SystemComponentMBean)((SystemComponentMBean)this.createCopy((AbstractDescriptorBean)oldSystemComponents[i], includeObsolete));
               }

               copy.setSystemComponents(newSystemComponents);
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("VirtualHosts")) && this.bean.isVirtualHostsSet() && !copy._isSet(69)) {
               VirtualHostMBean[] oldVirtualHosts = this.bean.getVirtualHosts();
               VirtualHostMBean[] newVirtualHosts = new VirtualHostMBean[oldVirtualHosts.length];

               for(i = 0; i < newVirtualHosts.length; ++i) {
                  newVirtualHosts[i] = (VirtualHostMBean)((VirtualHostMBean)this.createCopy((AbstractDescriptorBean)oldVirtualHosts[i], includeObsolete));
               }

               copy.setVirtualHosts(newVirtualHosts);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("VirtualTargets")) && this.bean.isVirtualTargetsSet() && !copy._isSet(70)) {
               VirtualTargetMBean[] oldVirtualTargets = this.bean.getVirtualTargets();
               VirtualTargetMBean[] newVirtualTargets = new VirtualTargetMBean[oldVirtualTargets.length];

               for(i = 0; i < newVirtualTargets.length; ++i) {
                  newVirtualTargets[i] = (VirtualTargetMBean)((VirtualTargetMBean)this.createCopy((AbstractDescriptorBean)oldVirtualTargets[i], includeObsolete));
               }

               copy.setVirtualTargets(newVirtualTargets);
            }

            if ((excludeProps == null || !excludeProps.contains("WLDFSystemResources")) && this.bean.isWLDFSystemResourcesSet() && !copy._isSet(99)) {
               WLDFSystemResourceMBean[] oldWLDFSystemResources = this.bean.getWLDFSystemResources();
               WLDFSystemResourceMBean[] newWLDFSystemResources = new WLDFSystemResourceMBean[oldWLDFSystemResources.length];

               for(i = 0; i < newWLDFSystemResources.length; ++i) {
                  newWLDFSystemResources[i] = (WLDFSystemResourceMBean)((WLDFSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldWLDFSystemResources[i], includeObsolete));
               }

               copy.setWLDFSystemResources(newWLDFSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("WSReliableDeliveryPolicies")) && this.bean.isWSReliableDeliveryPoliciesSet() && !copy._isSet(53)) {
               WSReliableDeliveryPolicyMBean[] oldWSReliableDeliveryPolicies = this.bean.getWSReliableDeliveryPolicies();
               WSReliableDeliveryPolicyMBean[] newWSReliableDeliveryPolicies = new WSReliableDeliveryPolicyMBean[oldWSReliableDeliveryPolicies.length];

               for(i = 0; i < newWSReliableDeliveryPolicies.length; ++i) {
                  newWSReliableDeliveryPolicies[i] = (WSReliableDeliveryPolicyMBean)((WSReliableDeliveryPolicyMBean)this.createCopy((AbstractDescriptorBean)oldWSReliableDeliveryPolicies[i], includeObsolete));
               }

               copy.setWSReliableDeliveryPolicies(newWSReliableDeliveryPolicies);
            }

            if ((excludeProps == null || !excludeProps.contains("WTCServers")) && this.bean.isWTCServersSet() && !copy._isSet(18)) {
               WTCServerMBean[] oldWTCServers = this.bean.getWTCServers();
               WTCServerMBean[] newWTCServers = new WTCServerMBean[oldWTCServers.length];

               for(i = 0; i < newWTCServers.length; ++i) {
                  newWTCServers[i] = (WTCServerMBean)((WTCServerMBean)this.createCopy((AbstractDescriptorBean)oldWTCServers[i], includeObsolete));
               }

               copy.setWTCServers(newWTCServers);
            }

            if ((excludeProps == null || !excludeProps.contains("WebAppContainer")) && this.bean.isWebAppContainerSet() && !copy._isSet(73)) {
               Object o = this.bean.getWebAppContainer();
               copy.setWebAppContainer((WebAppContainerMBean)null);
               copy.setWebAppContainer(o == null ? null : (WebAppContainerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceSecurities")) && this.bean.isWebserviceSecuritiesSet() && !copy._isSet(114)) {
               WebserviceSecurityMBean[] oldWebserviceSecurities = this.bean.getWebserviceSecurities();
               WebserviceSecurityMBean[] newWebserviceSecurities = new WebserviceSecurityMBean[oldWebserviceSecurities.length];

               for(i = 0; i < newWebserviceSecurities.length; ++i) {
                  newWebserviceSecurities[i] = (WebserviceSecurityMBean)((WebserviceSecurityMBean)this.createCopy((AbstractDescriptorBean)oldWebserviceSecurities[i], includeObsolete));
               }

               copy.setWebserviceSecurities(newWebserviceSecurities);
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceTestpage")) && this.bean.isWebserviceTestpageSet() && !copy._isSet(130)) {
               Object o = this.bean.getWebserviceTestpage();
               copy.setWebserviceTestpage((WebserviceTestpageMBean)null);
               copy.setWebserviceTestpage(o == null ? null : (WebserviceTestpageMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("XMLEntityCaches")) && this.bean.isXMLEntityCachesSet() && !copy._isSet(55)) {
               XMLEntityCacheMBean[] oldXMLEntityCaches = this.bean.getXMLEntityCaches();
               XMLEntityCacheMBean[] newXMLEntityCaches = new XMLEntityCacheMBean[oldXMLEntityCaches.length];

               for(i = 0; i < newXMLEntityCaches.length; ++i) {
                  newXMLEntityCaches[i] = (XMLEntityCacheMBean)((XMLEntityCacheMBean)this.createCopy((AbstractDescriptorBean)oldXMLEntityCaches[i], includeObsolete));
               }

               copy.setXMLEntityCaches(newXMLEntityCaches);
            }

            if ((excludeProps == null || !excludeProps.contains("XMLRegistries")) && this.bean.isXMLRegistriesSet() && !copy._isSet(56)) {
               XMLRegistryMBean[] oldXMLRegistries = this.bean.getXMLRegistries();
               XMLRegistryMBean[] newXMLRegistries = new XMLRegistryMBean[oldXMLRegistries.length];

               for(i = 0; i < newXMLRegistries.length; ++i) {
                  newXMLRegistries[i] = (XMLRegistryMBean)((XMLRegistryMBean)this.createCopy((AbstractDescriptorBean)oldXMLRegistries[i], includeObsolete));
               }

               copy.setXMLRegistries(newXMLRegistries);
            }

            if ((excludeProps == null || !excludeProps.contains("Active")) && this.bean.isActiveSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("AdministrationMBeanAuditingEnabled")) && this.bean.isAdministrationMBeanAuditingEnabledSet()) {
               copy.setAdministrationMBeanAuditingEnabled(this.bean.isAdministrationMBeanAuditingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("AdministrationPortEnabled")) && this.bean.isAdministrationPortEnabledSet()) {
               copy.setAdministrationPortEnabled(this.bean.isAdministrationPortEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("AutoConfigurationSaveEnabled")) && this.bean.isAutoConfigurationSaveEnabledSet()) {
               copy.setAutoConfigurationSaveEnabled(this.bean.isAutoConfigurationSaveEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoDeployForSubmodulesEnabled")) && this.bean.isAutoDeployForSubmodulesEnabledSet()) {
               copy.setAutoDeployForSubmodulesEnabled(this.bean.isAutoDeployForSubmodulesEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterConstraintsEnabled")) && this.bean.isClusterConstraintsEnabledSet()) {
               copy.setClusterConstraintsEnabled(this.bean.isClusterConstraintsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigBackupEnabled")) && this.bean.isConfigBackupEnabledSet()) {
               copy.setConfigBackupEnabled(this.bean.isConfigBackupEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ConsoleEnabled")) && this.bean.isConsoleEnabledSet()) {
               copy.setConsoleEnabled(this.bean.isConsoleEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DBPassiveMode")) && this.bean.isDBPassiveModeSet()) {
               copy.setDBPassiveMode(this.bean.isDBPassiveMode());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticContextCompatibilityModeEnabled")) && this.bean.isDiagnosticContextCompatibilityModeEnabledSet()) {
               copy.setDiagnosticContextCompatibilityModeEnabled(this.bean.isDiagnosticContextCompatibilityModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableEECompliantClassloadingForEmbeddedAdapters")) && this.bean.isEnableEECompliantClassloadingForEmbeddedAdaptersSet()) {
               copy.setEnableEECompliantClassloadingForEmbeddedAdapters(this.bean.isEnableEECompliantClassloadingForEmbeddedAdapters());
            }

            if ((excludeProps == null || !excludeProps.contains("ExalogicOptimizationsEnabled")) && this.bean.isExalogicOptimizationsEnabledSet()) {
               copy.setExalogicOptimizationsEnabled(this.bean.isExalogicOptimizationsEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("GuardianEnabled")) && this.bean.isGuardianEnabledSet()) {
               copy.setGuardianEnabled(this.bean.isGuardianEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("InternalAppsDeployOnDemandEnabled")) && this.bean.isInternalAppsDeployOnDemandEnabledSet()) {
               copy.setInternalAppsDeployOnDemandEnabled(this.bean.isInternalAppsDeployOnDemandEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaServiceConsoleEnabled")) && this.bean.isJavaServiceConsoleEnabledSet()) {
               copy.setJavaServiceConsoleEnabled(this.bean.isJavaServiceConsoleEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaServiceEnabled")) && this.bean.isJavaServiceEnabledSet()) {
               copy.setJavaServiceEnabled(this.bean.isJavaServiceEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("LogFormatCompatibilityEnabled")) && this.bean.isLogFormatCompatibilityEnabledSet()) {
               copy.setLogFormatCompatibilityEnabled(this.bean.isLogFormatCompatibilityEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MsgIdPrefixCompatibilityEnabled")) && this.bean.isMsgIdPrefixCompatibilityEnabledSet()) {
               copy.setMsgIdPrefixCompatibilityEnabled(this.bean.isMsgIdPrefixCompatibilityEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OCMEnabled")) && this.bean.isOCMEnabledSet()) {
               copy.setOCMEnabled(this.bean.isOCMEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelDeployApplicationModules")) && this.bean.isParallelDeployApplicationModulesSet()) {
               copy.setParallelDeployApplicationModules(this.bean.isParallelDeployApplicationModules());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelDeployApplications")) && this.bean.isParallelDeployApplicationsSet()) {
               copy.setParallelDeployApplications(this.bean.isParallelDeployApplications());
            }

            if ((excludeProps == null || !excludeProps.contains("ProductionModeEnabled")) && this.bean.isProductionModeEnabledSet()) {
               copy.setProductionModeEnabled(this.bean.isProductionModeEnabled());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getAdminConsole(), clazz, annotation);
         this.inferSubTree(this.bean.getAdminServerMBean(), clazz, annotation);
         this.inferSubTree(this.bean.getAppDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getApplications(), clazz, annotation);
         this.inferSubTree(this.bean.getBatchConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getBridgeDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getCallouts(), clazz, annotation);
         this.inferSubTree(this.bean.getCdiContainer(), clazz, annotation);
         this.inferSubTree(this.bean.getClusters(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceManagementClusters(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceServers(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomResources(), clazz, annotation);
         this.inferSubTree(this.bean.getDebugPatches(), clazz, annotation);
         this.inferSubTree(this.bean.getDeploymentConfiguration(), clazz, annotation);
         this.inferSubTree(this.bean.getDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getDomainLibraries(), clazz, annotation);
         this.inferSubTree(this.bean.getEJBContainer(), clazz, annotation);
         this.inferSubTree(this.bean.getEmbeddedLDAP(), clazz, annotation);
         this.inferSubTree(this.bean.getFileStores(), clazz, annotation);
         this.inferSubTree(this.bean.getFileT3s(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJMSConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJMSDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJMSServers(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJNDIProviders(), clazz, annotation);
         this.inferSubTree(this.bean.getInterceptors(), clazz, annotation);
         this.inferSubTree(this.bean.getInternalAppDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getInternalLibraries(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCStores(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSBridgeDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSConnectionConsumers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSDestinationKeys(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSDistributedQueueMembers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSDistributedQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSDistributedTopicMembers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSDistributedTopics(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSFileStores(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSJDBCStores(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSServers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSSessionPools(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSStores(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSTopics(), clazz, annotation);
         this.inferSubTree(this.bean.getJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getJPA(), clazz, annotation);
         this.inferSubTree(this.bean.getJTA(), clazz, annotation);
         this.inferSubTree(this.bean.getJoltConnectionPools(), clazz, annotation);
         this.inferSubTree(this.bean.getLibraries(), clazz, annotation);
         this.inferSubTree(this.bean.getLifecycleManagerConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getLifecycleManagerEndPoints(), clazz, annotation);
         this.inferSubTree(this.bean.getLog(), clazz, annotation);
         this.inferSubTree(this.bean.getLogFilters(), clazz, annotation);
         this.inferSubTree(this.bean.getMachines(), clazz, annotation);
         this.inferSubTree(this.bean.getMailSessions(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServiceTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServiceTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactoryTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getMessagingBridges(), clazz, annotation);
         this.inferSubTree(this.bean.getMigratableRMIServices(), clazz, annotation);
         this.inferSubTree(this.bean.getMigratableTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getNetworkChannels(), clazz, annotation);
         this.inferSubTree(this.bean.getOptionalFeatureDeployment(), clazz, annotation);
         this.inferSubTree(this.bean.getOsgiFrameworks(), clazz, annotation);
         this.inferSubTree(this.bean.getPartitionTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getPartitionWorkManagers(), clazz, annotation);
         this.inferSubTree(this.bean.getPartitions(), clazz, annotation);
         this.inferSubTree(this.bean.getPathServices(), clazz, annotation);
         this.inferSubTree(this.bean.getReplicatedStores(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceGroupTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceGroups(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceManagement(), clazz, annotation);
         this.inferSubTree(this.bean.getRestfulManagementServices(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFAgents(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPAgent(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPAgentDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPAttributeChanges(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPCounterMonitors(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPGaugeMonitors(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPLogFilters(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPProxies(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPStringMonitors(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPTrapDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityConfiguration(), clazz, annotation);
         this.inferSubTree(this.bean.getSelfTuning(), clazz, annotation);
         this.inferSubTree(this.bean.getServerTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getServers(), clazz, annotation);
         this.inferSubTree(this.bean.getShutdownClasses(), clazz, annotation);
         this.inferSubTree(this.bean.getSingletonServices(), clazz, annotation);
         this.inferSubTree(this.bean.getStartupClasses(), clazz, annotation);
         this.inferSubTree(this.bean.getSystemComponentConfigurations(), clazz, annotation);
         this.inferSubTree(this.bean.getSystemComponents(), clazz, annotation);
         this.inferSubTree(this.bean.getSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getVirtualHosts(), clazz, annotation);
         this.inferSubTree(this.bean.getVirtualTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getWLDFSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getWSReliableDeliveryPolicies(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCServers(), clazz, annotation);
         this.inferSubTree(this.bean.getWebAppContainer(), clazz, annotation);
         this.inferSubTree(this.bean.getWebserviceSecurities(), clazz, annotation);
         this.inferSubTree(this.bean.getWebserviceTestpage(), clazz, annotation);
         this.inferSubTree(this.bean.getXMLEntityCaches(), clazz, annotation);
         this.inferSubTree(this.bean.getXMLRegistries(), clazz, annotation);
      }
   }
}
