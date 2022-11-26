package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.cluster.ClusterValidator;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.mbeans.custom.ServerTemplate;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ServerTemplateMBeanImpl extends KernelMBeanImpl implements ServerTemplateMBean, Serializable {
   private String _81StyleDefaultStagingDirName;
   private int _AcceptBacklog;
   private String _ActiveDirectoryName;
   private int _AdminReconnectIntervalSeconds;
   private int _AdministrationPort;
   private boolean _AdministrationPortEnabled;
   private String _AutoJDBCConnectionClose;
   private boolean _AutoKillIfFailed;
   private boolean _AutoMigrationEnabled;
   private boolean _AutoRestart;
   private String _BuzzAddress;
   private boolean _BuzzEnabled;
   private int _BuzzPort;
   private COMMBean _COM;
   private boolean _COMEnabled;
   private MachineMBean[] _CandidateMachines;
   private boolean _ClasspathServletDisabled;
   private boolean _ClasspathServletSecureModeEnabled;
   private boolean _CleanupOrphanedSessionsEnabled;
   private boolean _ClientCertProxyEnabled;
   private ClusterMBean _Cluster;
   private int _ClusterWeight;
   private CoherenceClusterSystemResourceMBean _CoherenceClusterSystemResource;
   private CoherenceMemberConfigMBean _CoherenceMemberConfig;
   private ConfigurationPropertyMBean[] _ConfigurationProperties;
   private int _ConsensusProcessIdentifier;
   private boolean _ConsoleInputEnabled;
   private String _CustomIdentityKeyStoreFileName;
   private String _CustomIdentityKeyStorePassPhrase;
   private byte[] _CustomIdentityKeyStorePassPhraseEncrypted;
   private String _CustomIdentityKeyStoreType;
   private String _CustomTrustKeyStoreFileName;
   private String _CustomTrustKeyStorePassPhrase;
   private byte[] _CustomTrustKeyStorePassPhraseEncrypted;
   private String _CustomTrustKeyStoreType;
   private DataSourceMBean _DataSource;
   private DefaultFileStoreMBean _DefaultFileStore;
   private String _DefaultIIOPPassword;
   private byte[] _DefaultIIOPPasswordEncrypted;
   private String _DefaultIIOPUser;
   private boolean _DefaultInternalServletsDisabled;
   private String _DefaultStagingDirName;
   private String _DefaultTGIOPPassword;
   private byte[] _DefaultTGIOPPasswordEncrypted;
   private String _DefaultTGIOPUser;
   private boolean _DynamicallyCreated;
   private boolean _ExpectedToRun;
   private String _ExternalDNSName;
   private String _ExtraEjbcOptions;
   private String _ExtraRmicOptions;
   private FederationServicesMBean _FederationServices;
   private int _GracefulShutdownTimeout;
   private int _HealthCheckIntervalSeconds;
   private int _HealthCheckStartDelaySeconds;
   private int _HealthCheckTimeoutSeconds;
   private boolean _HostsMigratableServices;
   private boolean _HttpTraceSupportEnabled;
   private boolean _HttpdEnabled;
   private Map _IIOPConnectionPools;
   private boolean _IIOPEnabled;
   private boolean _IgnoreSessionsDuringShutdown;
   private String _InterfaceAddress;
   private boolean _J2EE12OnlyModeEnabled;
   private boolean _J2EE13WarningEnabled;
   private String _JDBCLLRTableName;
   private int _JDBCLLRTablePoolColumnSize;
   private int _JDBCLLRTableRecordColumnSize;
   private int _JDBCLLRTableXIDColumnSize;
   private String _JDBCLogFileName;
   private boolean _JDBCLoggingEnabled;
   private int _JDBCLoginTimeoutSeconds;
   private String _JMSConnectionFactoryUnmappedResRefMode;
   private boolean _JMSDefaultConnectionFactoriesEnabled;
   private String[] _JNDITransportableObjectFactoryList;
   private boolean _JRMPEnabled;
   private JTAMigratableTargetMBean _JTAMigratableTarget;
   private String _JavaCompiler;
   private String _JavaCompilerPostClassPath;
   private String _JavaCompilerPreClassPath;
   private String _JavaStandardTrustKeyStorePassPhrase;
   private byte[] _JavaStandardTrustKeyStorePassPhraseEncrypted;
   private KernelDebugMBean _KernelDebug;
   private String _KeyStores;
   private String _ListenAddress;
   private int _ListenDelaySecs;
   private int _ListenPort;
   private boolean _ListenPortEnabled;
   private int _ListenThreadStartDelaySecs;
   private boolean _ListenersBindEarly;
   private int _LoginTimeout;
   private int _LoginTimeoutMillis;
   private int _LowMemoryGCThreshold;
   private int _LowMemoryGranularityLevel;
   private int _LowMemorySampleSize;
   private int _LowMemoryTimeInterval;
   private boolean _MSIFileReplicationEnabled;
   private MachineMBean _Machine;
   private boolean _ManagedServerIndependenceEnabled;
   private int _MaxBackoffBetweenFailures;
   private int _MaxConcurrentLongRunningRequests;
   private int _MaxConcurrentNewThreads;
   private boolean _MessageIdPrefixEnabled;
   private int _NMSocketCreateTimeoutInMillis;
   private String _Name;
   private NetworkAccessPointMBean[] _NetworkAccessPoints;
   private boolean _NetworkClassLoadingEnabled;
   private int _NumOfRetriesBeforeMSIMode;
   private OverloadProtectionMBean _OverloadProtection;
   private WebLogicMBean _Parent;
   private String _PreferredSecondaryGroup;
   private WSReliableDeliveryPolicyMBean _ReliableDeliveryPolicy;
   private String _ReplicationGroup;
   private String _ReplicationPorts;
   private boolean _ResolveDNSName;
   private int _RestartDelaySeconds;
   private int _RestartIntervalSeconds;
   private int _RestartMax;
   private int _RetryIntervalBeforeMSIMode;
   private String _RootDirectory;
   private ServerDebugMBean _ServerDebug;
   private WLDFServerDiagnosticMBean _ServerDiagnosticConfig;
   private int _ServerLifeCycleTimeoutVal;
   private Set _ServerNames;
   private ServerStartMBean _ServerStart;
   private String _ServerVersion;
   private boolean _SessionReplicationOnShutdownEnabled;
   private SingleSignOnServicesMBean _SingleSignOnServices;
   private int _SitConfigPollingInterval;
   private boolean _SitConfigRequired;
   private String _StagingDirectoryName;
   private String _StagingMode;
   private String _StartupMode;
   private int _StartupTimeout;
   private boolean _StdoutDebugEnabled;
   private boolean _StdoutEnabled;
   private String _StdoutFormat;
   private boolean _StdoutLogStack;
   private int _StdoutSeverityLevel;
   private String[] _SupportedProtocols;
   private String _SystemPassword;
   private byte[] _SystemPasswordEncrypted;
   private boolean _TGIOPEnabled;
   private String[] _Tags;
   private int _ThreadPoolSize;
   private String _TransactionLogFilePrefix;
   private String _TransactionLogFileWritePolicy;
   private TransactionLogJDBCStoreMBean _TransactionLogJDBCStore;
   private String _TransactionPrimaryChannelName;
   private String _TransactionPublicChannelName;
   private String _TransactionPublicSecureChannelName;
   private String _TransactionSecureChannelName;
   private int _TunnelingClientPingSecs;
   private int _TunnelingClientTimeoutSecs;
   private boolean _TunnelingEnabled;
   private String _UploadDirectoryName;
   private boolean _UseFusionForLLR;
   private String _VerboseEJBDeploymentEnabled;
   private String _VirtualMachineName;
   private WebServerMBean _WebServer;
   private WebServiceMBean _WebService;
   private boolean _WeblogicPluginEnabled;
   private XMLEntityCacheMBean _XMLEntityCache;
   private XMLRegistryMBean _XMLRegistry;
   private transient ServerTemplate _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ServerTemplateMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ServerTemplateMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ServerTemplateMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ServerTemplateMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ServerTemplateMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ServerTemplateMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._COM instanceof COMMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getCOM() != null) {
            this._getReferenceManager().unregisterBean((COMMBeanImpl)oldDelegate.getCOM());
         }

         if (delegate != null && delegate.getCOM() != null) {
            this._getReferenceManager().registerBean((COMMBeanImpl)delegate.getCOM(), false);
         }

         ((COMMBeanImpl)this._COM)._setDelegateBean((COMMBeanImpl)((COMMBeanImpl)(delegate == null ? null : delegate.getCOM())));
      }

      if (this._CoherenceClusterSystemResource instanceof CoherenceClusterSystemResourceMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getCoherenceClusterSystemResource() != null) {
            this._getReferenceManager().unregisterBean((CoherenceClusterSystemResourceMBeanImpl)oldDelegate.getCoherenceClusterSystemResource());
         }

         if (delegate != null && delegate.getCoherenceClusterSystemResource() != null) {
            this._getReferenceManager().registerBean((CoherenceClusterSystemResourceMBeanImpl)delegate.getCoherenceClusterSystemResource(), false);
         }

         ((CoherenceClusterSystemResourceMBeanImpl)this._CoherenceClusterSystemResource)._setDelegateBean((CoherenceClusterSystemResourceMBeanImpl)((CoherenceClusterSystemResourceMBeanImpl)(delegate == null ? null : delegate.getCoherenceClusterSystemResource())));
      }

      if (this._CoherenceMemberConfig instanceof CoherenceMemberConfigMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getCoherenceMemberConfig() != null) {
            this._getReferenceManager().unregisterBean((CoherenceMemberConfigMBeanImpl)oldDelegate.getCoherenceMemberConfig());
         }

         if (delegate != null && delegate.getCoherenceMemberConfig() != null) {
            this._getReferenceManager().registerBean((CoherenceMemberConfigMBeanImpl)delegate.getCoherenceMemberConfig(), false);
         }

         ((CoherenceMemberConfigMBeanImpl)this._CoherenceMemberConfig)._setDelegateBean((CoherenceMemberConfigMBeanImpl)((CoherenceMemberConfigMBeanImpl)(delegate == null ? null : delegate.getCoherenceMemberConfig())));
      }

      if (this._DataSource instanceof DataSourceMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDataSource() != null) {
            this._getReferenceManager().unregisterBean((DataSourceMBeanImpl)oldDelegate.getDataSource());
         }

         if (delegate != null && delegate.getDataSource() != null) {
            this._getReferenceManager().registerBean((DataSourceMBeanImpl)delegate.getDataSource(), false);
         }

         ((DataSourceMBeanImpl)this._DataSource)._setDelegateBean((DataSourceMBeanImpl)((DataSourceMBeanImpl)(delegate == null ? null : delegate.getDataSource())));
      }

      if (this._DefaultFileStore instanceof DefaultFileStoreMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDefaultFileStore() != null) {
            this._getReferenceManager().unregisterBean((DefaultFileStoreMBeanImpl)oldDelegate.getDefaultFileStore());
         }

         if (delegate != null && delegate.getDefaultFileStore() != null) {
            this._getReferenceManager().registerBean((DefaultFileStoreMBeanImpl)delegate.getDefaultFileStore(), false);
         }

         ((DefaultFileStoreMBeanImpl)this._DefaultFileStore)._setDelegateBean((DefaultFileStoreMBeanImpl)((DefaultFileStoreMBeanImpl)(delegate == null ? null : delegate.getDefaultFileStore())));
      }

      if (this._FederationServices instanceof FederationServicesMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getFederationServices() != null) {
            this._getReferenceManager().unregisterBean((FederationServicesMBeanImpl)oldDelegate.getFederationServices());
         }

         if (delegate != null && delegate.getFederationServices() != null) {
            this._getReferenceManager().registerBean((FederationServicesMBeanImpl)delegate.getFederationServices(), false);
         }

         ((FederationServicesMBeanImpl)this._FederationServices)._setDelegateBean((FederationServicesMBeanImpl)((FederationServicesMBeanImpl)(delegate == null ? null : delegate.getFederationServices())));
      }

      if (this._JTAMigratableTarget instanceof JTAMigratableTargetMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getJTAMigratableTarget() != null) {
            this._getReferenceManager().unregisterBean((JTAMigratableTargetMBeanImpl)oldDelegate.getJTAMigratableTarget());
         }

         if (delegate != null && delegate.getJTAMigratableTarget() != null) {
            this._getReferenceManager().registerBean((JTAMigratableTargetMBeanImpl)delegate.getJTAMigratableTarget(), false);
         }

         ((JTAMigratableTargetMBeanImpl)this._JTAMigratableTarget)._setDelegateBean((JTAMigratableTargetMBeanImpl)((JTAMigratableTargetMBeanImpl)(delegate == null ? null : delegate.getJTAMigratableTarget())));
      }

      if (this._KernelDebug instanceof KernelDebugMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getKernelDebug() != null) {
            this._getReferenceManager().unregisterBean((KernelDebugMBeanImpl)oldDelegate.getKernelDebug());
         }

         if (delegate != null && delegate.getKernelDebug() != null) {
            this._getReferenceManager().registerBean((KernelDebugMBeanImpl)delegate.getKernelDebug(), false);
         }

         ((KernelDebugMBeanImpl)this._KernelDebug)._setDelegateBean((KernelDebugMBeanImpl)((KernelDebugMBeanImpl)(delegate == null ? null : delegate.getKernelDebug())));
      }

      if (this._OverloadProtection instanceof OverloadProtectionMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getOverloadProtection() != null) {
            this._getReferenceManager().unregisterBean((OverloadProtectionMBeanImpl)oldDelegate.getOverloadProtection());
         }

         if (delegate != null && delegate.getOverloadProtection() != null) {
            this._getReferenceManager().registerBean((OverloadProtectionMBeanImpl)delegate.getOverloadProtection(), false);
         }

         ((OverloadProtectionMBeanImpl)this._OverloadProtection)._setDelegateBean((OverloadProtectionMBeanImpl)((OverloadProtectionMBeanImpl)(delegate == null ? null : delegate.getOverloadProtection())));
      }

      if (this._ServerDebug instanceof ServerDebugMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getServerDebug() != null) {
            this._getReferenceManager().unregisterBean((ServerDebugMBeanImpl)oldDelegate.getServerDebug());
         }

         if (delegate != null && delegate.getServerDebug() != null) {
            this._getReferenceManager().registerBean((ServerDebugMBeanImpl)delegate.getServerDebug(), false);
         }

         ((ServerDebugMBeanImpl)this._ServerDebug)._setDelegateBean((ServerDebugMBeanImpl)((ServerDebugMBeanImpl)(delegate == null ? null : delegate.getServerDebug())));
      }

      if (this._ServerDiagnosticConfig instanceof WLDFServerDiagnosticMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getServerDiagnosticConfig() != null) {
            this._getReferenceManager().unregisterBean((WLDFServerDiagnosticMBeanImpl)oldDelegate.getServerDiagnosticConfig());
         }

         if (delegate != null && delegate.getServerDiagnosticConfig() != null) {
            this._getReferenceManager().registerBean((WLDFServerDiagnosticMBeanImpl)delegate.getServerDiagnosticConfig(), false);
         }

         ((WLDFServerDiagnosticMBeanImpl)this._ServerDiagnosticConfig)._setDelegateBean((WLDFServerDiagnosticMBeanImpl)((WLDFServerDiagnosticMBeanImpl)(delegate == null ? null : delegate.getServerDiagnosticConfig())));
      }

      if (this._ServerStart instanceof ServerStartMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getServerStart() != null) {
            this._getReferenceManager().unregisterBean((ServerStartMBeanImpl)oldDelegate.getServerStart());
         }

         if (delegate != null && delegate.getServerStart() != null) {
            this._getReferenceManager().registerBean((ServerStartMBeanImpl)delegate.getServerStart(), false);
         }

         ((ServerStartMBeanImpl)this._ServerStart)._setDelegateBean((ServerStartMBeanImpl)((ServerStartMBeanImpl)(delegate == null ? null : delegate.getServerStart())));
      }

      if (this._SingleSignOnServices instanceof SingleSignOnServicesMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getSingleSignOnServices() != null) {
            this._getReferenceManager().unregisterBean((SingleSignOnServicesMBeanImpl)oldDelegate.getSingleSignOnServices());
         }

         if (delegate != null && delegate.getSingleSignOnServices() != null) {
            this._getReferenceManager().registerBean((SingleSignOnServicesMBeanImpl)delegate.getSingleSignOnServices(), false);
         }

         ((SingleSignOnServicesMBeanImpl)this._SingleSignOnServices)._setDelegateBean((SingleSignOnServicesMBeanImpl)((SingleSignOnServicesMBeanImpl)(delegate == null ? null : delegate.getSingleSignOnServices())));
      }

      if (this._TransactionLogJDBCStore instanceof TransactionLogJDBCStoreMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getTransactionLogJDBCStore() != null) {
            this._getReferenceManager().unregisterBean((TransactionLogJDBCStoreMBeanImpl)oldDelegate.getTransactionLogJDBCStore());
         }

         if (delegate != null && delegate.getTransactionLogJDBCStore() != null) {
            this._getReferenceManager().registerBean((TransactionLogJDBCStoreMBeanImpl)delegate.getTransactionLogJDBCStore(), false);
         }

         ((TransactionLogJDBCStoreMBeanImpl)this._TransactionLogJDBCStore)._setDelegateBean((TransactionLogJDBCStoreMBeanImpl)((TransactionLogJDBCStoreMBeanImpl)(delegate == null ? null : delegate.getTransactionLogJDBCStore())));
      }

      if (this._WebServer instanceof WebServerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebServer() != null) {
            this._getReferenceManager().unregisterBean((WebServerMBeanImpl)oldDelegate.getWebServer());
         }

         if (delegate != null && delegate.getWebServer() != null) {
            this._getReferenceManager().registerBean((WebServerMBeanImpl)delegate.getWebServer(), false);
         }

         ((WebServerMBeanImpl)this._WebServer)._setDelegateBean((WebServerMBeanImpl)((WebServerMBeanImpl)(delegate == null ? null : delegate.getWebServer())));
      }

      if (this._WebService instanceof WebServiceMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebService() != null) {
            this._getReferenceManager().unregisterBean((WebServiceMBeanImpl)oldDelegate.getWebService());
         }

         if (delegate != null && delegate.getWebService() != null) {
            this._getReferenceManager().registerBean((WebServiceMBeanImpl)delegate.getWebService(), false);
         }

         ((WebServiceMBeanImpl)this._WebService)._setDelegateBean((WebServiceMBeanImpl)((WebServiceMBeanImpl)(delegate == null ? null : delegate.getWebService())));
      }

   }

   public ServerTemplateMBeanImpl() {
      try {
         this._customizer = new ServerTemplate(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ServerTemplateMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ServerTemplate(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ServerTemplateMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ServerTemplate(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public void addConfigurationProperty(ConfigurationPropertyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 89)) {
         ConfigurationPropertyMBean[] _new;
         if (this._isSet(89)) {
            _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._extendArray(this.getConfigurationProperties(), ConfigurationPropertyMBean.class, param0));
         } else {
            _new = new ConfigurationPropertyMBean[]{param0};
         }

         try {
            this.setConfigurationProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigurationPropertyMBean[] getConfigurationProperties() {
      ConfigurationPropertyMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(89)) {
         delegateArray = this._getDelegateBean().getConfigurationProperties();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ConfigurationProperties.length; ++j) {
               if (delegateArray[i].getName().equals(this._ConfigurationProperties[j].getName())) {
                  ((ConfigurationPropertyMBeanImpl)this._ConfigurationProperties[j])._setDelegateBean((ConfigurationPropertyMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ConfigurationPropertyMBeanImpl mbean = new ConfigurationPropertyMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 89);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ConfigurationPropertyMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(89)) {
                     this.setConfigurationProperties((ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._extendArray(this._ConfigurationProperties, ConfigurationPropertyMBean.class, mbean)));
                  } else {
                     this.setConfigurationProperties(new ConfigurationPropertyMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ConfigurationPropertyMBean[0];
      }

      if (this._ConfigurationProperties != null) {
         List removeList = new ArrayList();
         ConfigurationPropertyMBean[] var18 = this._ConfigurationProperties;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ConfigurationPropertyMBean bn = var18[var5];
            ConfigurationPropertyMBeanImpl bni = (ConfigurationPropertyMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ConfigurationPropertyMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ConfigurationPropertyMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            ConfigurationPropertyMBean removeIt = (ConfigurationPropertyMBean)var19.next();
            ConfigurationPropertyMBeanImpl removeItImpl = (ConfigurationPropertyMBeanImpl)removeIt;
            ConfigurationPropertyMBean[] _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._removeElement(this._ConfigurationProperties, ConfigurationPropertyMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setConfigurationProperties(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ConfigurationProperties;
   }

   public String getName() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2) ? this._performMacroSubstitution(this._getDelegateBean().getName(), this) : this._customizer.getName();
   }

   public String getRootDirectory() {
      return !this._isSet(90) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(90) ? this._performMacroSubstitution(this._getDelegateBean().getRootDirectory(), this) : this._customizer.getRootDirectory();
   }

   public Set getServerNames() {
      return this._customizer.getServerNames();
   }

   public boolean isConfigurationPropertiesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(89)) {
         ConfigurationPropertyMBean[] elements = this.getConfigurationProperties();
         ConfigurationPropertyMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isConfigurationPropertiesSet() {
      return this._isSet(89);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isRootDirectoryInherited() {
      return !this._isSet(90) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(90);
   }

   public boolean isRootDirectorySet() {
      return this._isSet(90);
   }

   public boolean isServerNamesInherited() {
      return false;
   }

   public boolean isServerNamesSet() {
      return this._isSet(88);
   }

   public void removeConfigurationProperty(ConfigurationPropertyMBean param0) {
      this.destroyConfigurationProperty(param0);
   }

   public void setConfigurationProperties(ConfigurationPropertyMBean[] param0) throws InvalidAttributeValueException {
      ConfigurationPropertyMBean[] param0 = param0 == null ? new ConfigurationPropertyMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ConfigurationProperties, (Object[])param0, handler, new Comparator() {
            public int compare(ConfigurationPropertyMBean o1, ConfigurationPropertyMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ConfigurationPropertyMBean bean = (ConfigurationPropertyMBean)var3.next();
            ConfigurationPropertyMBeanImpl beanImpl = (ConfigurationPropertyMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 89)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(89);
      ConfigurationPropertyMBean[] _oldVal = this._ConfigurationProperties;
      this._ConfigurationProperties = (ConfigurationPropertyMBean[])param0;
      this._postSet(89, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(89)) {
            source._postSetFirePropertyChange(89, wasSet, _oldVal, param0);
         }
      }

   }

   public void setServerNames(Set param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ServerNames = param0;
   }

   public ConfigurationPropertyMBean lookupConfigurationProperty(String param0) {
      Object[] aary = (Object[])this.getConfigurationProperties();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ConfigurationPropertyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ConfigurationPropertyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      ConfigurationValidator.validateNameUsedInDirectory(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setRootDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._RootDirectory = param0;
   }

   public ConfigurationPropertyMBean createConfigurationProperty(String param0) {
      ConfigurationPropertyMBeanImpl lookup = (ConfigurationPropertyMBeanImpl)this.lookupConfigurationProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ConfigurationPropertyMBeanImpl _val = new ConfigurationPropertyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addConfigurationProperty(_val);
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

   public MachineMBean getMachine() {
      return !this._isSet(91) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(91) ? this._getDelegateBean().getMachine() : this._Machine;
   }

   public String getMachineAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getMachine();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isMachineInherited() {
      return !this._isSet(91) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(91);
   }

   public boolean isMachineSet() {
      return this._isSet(91);
   }

   public void setMachineAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, MachineMBean.class, new ReferenceManager.Resolver(this, 91) {
            public void resolveReference(Object value) {
               try {
                  ServerTemplateMBeanImpl.this.setMachine((MachineMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         MachineMBean _oldVal = this._Machine;
         this._initializeProperty(91);
         this._postSet(91, _oldVal, this._Machine);
      }

   }

   public void destroyConfigurationProperty(ConfigurationPropertyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 89);
         ConfigurationPropertyMBean[] _old = this.getConfigurationProperties();
         ConfigurationPropertyMBean[] _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._removeElement(_old, ConfigurationPropertyMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var6.next();
                  ConfigurationPropertyMBeanImpl childImpl = (ConfigurationPropertyMBeanImpl)_child;
                  ConfigurationPropertyMBeanImpl lookup = (ConfigurationPropertyMBeanImpl)source.lookupConfigurationProperty(childImpl.getName());
                  if (lookup != null) {
                     source.destroyConfigurationProperty(lookup);
                  }
               }

               this.setConfigurationProperties(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public void setMachine(MachineMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 91, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerTemplateMBeanImpl.this.getMachine();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(91);
      MachineMBean _oldVal = this._Machine;
      this._Machine = param0;
      this._postSet(91, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(91)) {
            source._postSetFirePropertyChange(91, wasSet, _oldVal, param0);
         }
      }

   }

   public int getListenPort() {
      return !this._isSet(92) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(92) ? this._getDelegateBean().getListenPort() : this._ListenPort;
   }

   public boolean isListenPortInherited() {
      return !this._isSet(92) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(92);
   }

   public boolean isListenPortSet() {
      return this._isSet(92);
   }

   public void setListenPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ListenPort", (long)param0, 1L, 65535L);
      boolean wasSet = this._isSet(92);
      int _oldVal = this._ListenPort;
      this._ListenPort = param0;
      this._postSet(92, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(92)) {
            source._postSetFirePropertyChange(92, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isListenPortEnabled() {
      if (!this._isSet(93) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(93)) {
         return this._getDelegateBean().isListenPortEnabled();
      } else if (!this._isSet(93)) {
         return !this._isSecureModeEnabled();
      } else {
         return this._ListenPortEnabled;
      }
   }

   public boolean isListenPortEnabledInherited() {
      return !this._isSet(93) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(93);
   }

   public boolean isListenPortEnabledSet() {
      return this._isSet(93);
   }

   public void setListenPortEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(93);
      boolean _oldVal = this._ListenPortEnabled;
      this._ListenPortEnabled = param0;
      this._postSet(93, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(93)) {
            source._postSetFirePropertyChange(93, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLoginTimeout() {
      return !this._isSet(94) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(94) ? this._getDelegateBean().getLoginTimeout() : this._LoginTimeout;
   }

   public boolean isLoginTimeoutInherited() {
      return !this._isSet(94) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(94);
   }

   public boolean isLoginTimeoutSet() {
      return this._isSet(94);
   }

   public int getThreadPoolSize() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getThreadPoolSize() : this._customizer.getThreadPoolSize();
   }

   public boolean isThreadPoolSizeInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isThreadPoolSizeSet() {
      return this._isSet(15);
   }

   public void setLoginTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(94);
      int _oldVal = this._LoginTimeout;
      this._LoginTimeout = param0;
      this._postSet(94, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(94)) {
            source._postSetFirePropertyChange(94, wasSet, _oldVal, param0);
         }
      }

   }

   public ClusterMBean getCluster() {
      return !this._isSet(95) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(95) ? this._getDelegateBean().getCluster() : this._customizer.getCluster();
   }

   public String getClusterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCluster();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isClusterInherited() {
      return !this._isSet(95) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(95);
   }

   public boolean isClusterSet() {
      return this._isSet(95);
   }

   public void setClusterAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ClusterMBean.class, new ReferenceManager.Resolver(this, 95) {
            public void resolveReference(Object value) {
               try {
                  ServerTemplateMBeanImpl.this.setCluster((ClusterMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ClusterMBean _oldVal = this._Cluster;
         this._initializeProperty(95);
         this._postSet(95, _oldVal, this._Cluster);
      }

   }

   public void setThreadPoolSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ThreadPoolSize", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(15);
      int _oldVal = this.getThreadPoolSize();
      this._customizer.setThreadPoolSize(param0);
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setCluster(ClusterMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 95, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerTemplateMBeanImpl.this.getCluster();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(95);
      ClusterMBean _oldVal = this.getCluster();
      this._customizer.setCluster(param0);
      this._postSet(95, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(95)) {
            source._postSetFirePropertyChange(95, wasSet, _oldVal, param0);
         }
      }

   }

   public int getClusterWeight() {
      return !this._isSet(96) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(96) ? this._getDelegateBean().getClusterWeight() : this._ClusterWeight;
   }

   public boolean isClusterWeightInherited() {
      return !this._isSet(96) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(96);
   }

   public boolean isClusterWeightSet() {
      return this._isSet(96);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setClusterWeight(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ClusterWeight", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(96);
      int _oldVal = this._ClusterWeight;
      this._ClusterWeight = param0;
      this._postSet(96, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(96)) {
            source._postSetFirePropertyChange(96, wasSet, _oldVal, param0);
         }
      }

   }

   public String getReplicationGroup() {
      return !this._isSet(97) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(97) ? this._performMacroSubstitution(this._getDelegateBean().getReplicationGroup(), this) : this._ReplicationGroup;
   }

   public boolean isReplicationGroupInherited() {
      return !this._isSet(97) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(97);
   }

   public boolean isReplicationGroupSet() {
      return this._isSet(97);
   }

   public void setReplicationGroup(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(97);
      String _oldVal = this._ReplicationGroup;
      this._ReplicationGroup = param0;
      this._postSet(97, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(97)) {
            source._postSetFirePropertyChange(97, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPreferredSecondaryGroup() {
      return !this._isSet(98) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(98) ? this._performMacroSubstitution(this._getDelegateBean().getPreferredSecondaryGroup(), this) : this._PreferredSecondaryGroup;
   }

   public boolean isPreferredSecondaryGroupInherited() {
      return !this._isSet(98) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(98);
   }

   public boolean isPreferredSecondaryGroupSet() {
      return this._isSet(98);
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

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setPreferredSecondaryGroup(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(98);
      String _oldVal = this._PreferredSecondaryGroup;
      this._PreferredSecondaryGroup = param0;
      this._postSet(98, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(98)) {
            source._postSetFirePropertyChange(98, wasSet, _oldVal, param0);
         }
      }

   }

   public int getConsensusProcessIdentifier() {
      return !this._isSet(99) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(99) ? this._getDelegateBean().getConsensusProcessIdentifier() : this._ConsensusProcessIdentifier;
   }

   public boolean isConsensusProcessIdentifierInherited() {
      return !this._isSet(99) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(99);
   }

   public boolean isConsensusProcessIdentifierSet() {
      return this._isSet(99);
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setConsensusProcessIdentifier(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ConsensusProcessIdentifier", (long)param0, -1L, 65535L);
      boolean wasSet = this._isSet(99);
      int _oldVal = this._ConsensusProcessIdentifier;
      this._ConsensusProcessIdentifier = param0;
      this._postSet(99, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(99)) {
            source._postSetFirePropertyChange(99, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAutoMigrationEnabled() {
      return !this._isSet(100) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(100) ? this._getDelegateBean().isAutoMigrationEnabled() : this._AutoMigrationEnabled;
   }

   public boolean isAutoMigrationEnabledInherited() {
      return !this._isSet(100) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(100);
   }

   public boolean isAutoMigrationEnabledSet() {
      return this._isSet(100);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

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

   public void setAutoMigrationEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      ClusterValidator.validateAutoMigration(param0);
      boolean wasSet = this._isSet(100);
      boolean _oldVal = this._AutoMigrationEnabled;
      this._AutoMigrationEnabled = param0;
      this._postSet(100, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(100)) {
            source._postSetFirePropertyChange(100, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServerMBean getWebServer() {
      return this._WebServer;
   }

   public boolean isWebServerInherited() {
      return false;
   }

   public boolean isWebServerSet() {
      return this._isSet(101) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServer());
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

   public void setWebServer(WebServerMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 101)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(101);
      WebServerMBean _oldVal = this._WebServer;
      this._WebServer = param0;
      this._postSet(101, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(101)) {
            source._postSetFirePropertyChange(101, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getExpectedToRun() {
      return !this._isSet(102) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(102) ? this._getDelegateBean().getExpectedToRun() : this._ExpectedToRun;
   }

   public boolean isExpectedToRunInherited() {
      return !this._isSet(102) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(102);
   }

   public boolean isExpectedToRunSet() {
      return this._isSet(102);
   }

   public void setExpectedToRun(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ExpectedToRun = param0;
   }

   public String synchronousStart() {
      return this._customizer.synchronousStart();
   }

   public String synchronousKill() {
      return this._customizer.synchronousKill();
   }

   public boolean isJDBCLoggingEnabled() {
      return !this._isSet(103) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(103) ? this._getDelegateBean().isJDBCLoggingEnabled() : this._JDBCLoggingEnabled;
   }

   public boolean isJDBCLoggingEnabledInherited() {
      return !this._isSet(103) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(103);
   }

   public boolean isJDBCLoggingEnabledSet() {
      return this._isSet(103);
   }

   public void setJDBCLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(103);
      boolean _oldVal = this._JDBCLoggingEnabled;
      this._JDBCLoggingEnabled = param0;
      this._postSet(103, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(103)) {
            source._postSetFirePropertyChange(103, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJDBCLogFileName() {
      return !this._isSet(104) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(104) ? this._performMacroSubstitution(this._getDelegateBean().getJDBCLogFileName(), this) : this._JDBCLogFileName;
   }

   public boolean isJDBCLogFileNameInherited() {
      return !this._isSet(104) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(104);
   }

   public boolean isJDBCLogFileNameSet() {
      return this._isSet(104);
   }

   public void setJDBCLogFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(104);
      String _oldVal = this._JDBCLogFileName;
      this._JDBCLogFileName = param0;
      this._postSet(104, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(104)) {
            source._postSetFirePropertyChange(104, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJ2EE12OnlyModeEnabled() {
      return !this._isSet(105) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(105) ? this._getDelegateBean().isJ2EE12OnlyModeEnabled() : this._J2EE12OnlyModeEnabled;
   }

   public boolean isJ2EE12OnlyModeEnabledInherited() {
      return !this._isSet(105) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(105);
   }

   public boolean isJ2EE12OnlyModeEnabledSet() {
      return this._isSet(105);
   }

   public void setJ2EE12OnlyModeEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(105);
      boolean _oldVal = this._J2EE12OnlyModeEnabled;
      this._J2EE12OnlyModeEnabled = param0;
      this._postSet(105, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(105)) {
            source._postSetFirePropertyChange(105, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJ2EE13WarningEnabled() {
      return !this._isSet(106) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(106) ? this._getDelegateBean().isJ2EE13WarningEnabled() : this._J2EE13WarningEnabled;
   }

   public boolean isJ2EE13WarningEnabledInherited() {
      return !this._isSet(106) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(106);
   }

   public boolean isJ2EE13WarningEnabledSet() {
      return this._isSet(106);
   }

   public void setJ2EE13WarningEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(106);
      boolean _oldVal = this._J2EE13WarningEnabled;
      this._J2EE13WarningEnabled = param0;
      this._postSet(106, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(106)) {
            source._postSetFirePropertyChange(106, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIIOPEnabled() {
      return !this._isSet(107) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(107) ? this._getDelegateBean().isIIOPEnabled() : this._IIOPEnabled;
   }

   public boolean isIIOPEnabledInherited() {
      return !this._isSet(107) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(107);
   }

   public boolean isIIOPEnabledSet() {
      return this._isSet(107);
   }

   public void setIIOPEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(107);
      boolean _oldVal = this._IIOPEnabled;
      this._IIOPEnabled = param0;
      this._postSet(107, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(107)) {
            source._postSetFirePropertyChange(107, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultIIOPUser() {
      return !this._isSet(108) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(108) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultIIOPUser(), this) : this._DefaultIIOPUser;
   }

   public boolean isDefaultIIOPUserInherited() {
      return !this._isSet(108) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(108);
   }

   public boolean isDefaultIIOPUserSet() {
      return this._isSet(108);
   }

   public void setDefaultIIOPUser(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(108);
      String _oldVal = this._DefaultIIOPUser;
      this._DefaultIIOPUser = param0;
      this._postSet(108, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(108)) {
            source._postSetFirePropertyChange(108, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultIIOPPassword() {
      if (!this._isSet(109) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(109)) {
         return this._performMacroSubstitution(this._getDelegateBean().getDefaultIIOPPassword(), this);
      } else {
         byte[] bEncrypted = this.getDefaultIIOPPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("DefaultIIOPPassword", bEncrypted);
      }
   }

   public boolean isDefaultIIOPPasswordInherited() {
      return !this._isSet(109) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(109);
   }

   public boolean isDefaultIIOPPasswordSet() {
      return this.isDefaultIIOPPasswordEncryptedSet();
   }

   public void setDefaultIIOPPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setDefaultIIOPPasswordEncrypted(param0 == null ? null : this._encrypt("DefaultIIOPPassword", param0));
   }

   public byte[] getDefaultIIOPPasswordEncrypted() {
      return !this._isSet(110) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(110) ? this._getDelegateBean().getDefaultIIOPPasswordEncrypted() : this._getHelper()._cloneArray(this._DefaultIIOPPasswordEncrypted);
   }

   public String getDefaultIIOPPasswordEncryptedAsString() {
      byte[] obj = this.getDefaultIIOPPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isDefaultIIOPPasswordEncryptedInherited() {
      return !this._isSet(110) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(110);
   }

   public boolean isDefaultIIOPPasswordEncryptedSet() {
      return this._isSet(110);
   }

   public void setDefaultIIOPPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setDefaultIIOPPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isTGIOPEnabled() {
      return !this._isSet(111) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(111) ? this._getDelegateBean().isTGIOPEnabled() : this._TGIOPEnabled;
   }

   public boolean isTGIOPEnabledInherited() {
      return !this._isSet(111) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(111);
   }

   public boolean isTGIOPEnabledSet() {
      return this._isSet(111);
   }

   public void setTGIOPEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(111);
      boolean _oldVal = this._TGIOPEnabled;
      this._TGIOPEnabled = param0;
      this._postSet(111, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(111)) {
            source._postSetFirePropertyChange(111, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultTGIOPUser() {
      return !this._isSet(112) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(112) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultTGIOPUser(), this) : this._DefaultTGIOPUser;
   }

   public boolean isDefaultTGIOPUserInherited() {
      return !this._isSet(112) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(112);
   }

   public boolean isDefaultTGIOPUserSet() {
      return this._isSet(112);
   }

   public void setDefaultTGIOPUser(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(112);
      String _oldVal = this._DefaultTGIOPUser;
      this._DefaultTGIOPUser = param0;
      this._postSet(112, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(112)) {
            source._postSetFirePropertyChange(112, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultTGIOPPassword() {
      if (!this._isSet(113) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(113)) {
         return this._performMacroSubstitution(this._getDelegateBean().getDefaultTGIOPPassword(), this);
      } else {
         byte[] bEncrypted = this.getDefaultTGIOPPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("DefaultTGIOPPassword", bEncrypted);
      }
   }

   public boolean isDefaultTGIOPPasswordInherited() {
      return !this._isSet(113) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(113);
   }

   public boolean isDefaultTGIOPPasswordSet() {
      return this.isDefaultTGIOPPasswordEncryptedSet();
   }

   public void setDefaultTGIOPPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setDefaultTGIOPPasswordEncrypted(param0 == null ? null : this._encrypt("DefaultTGIOPPassword", param0));
   }

   public byte[] getDefaultTGIOPPasswordEncrypted() {
      return !this._isSet(114) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(114) ? this._getDelegateBean().getDefaultTGIOPPasswordEncrypted() : this._getHelper()._cloneArray(this._DefaultTGIOPPasswordEncrypted);
   }

   public String getDefaultTGIOPPasswordEncryptedAsString() {
      byte[] obj = this.getDefaultTGIOPPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isDefaultTGIOPPasswordEncryptedInherited() {
      return !this._isSet(114) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(114);
   }

   public boolean isDefaultTGIOPPasswordEncryptedSet() {
      return this._isSet(114);
   }

   public void setDefaultTGIOPPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setDefaultTGIOPPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isCOMEnabled() {
      return !this._isSet(115) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(115) ? this._getDelegateBean().isCOMEnabled() : this._COMEnabled;
   }

   public boolean isCOMEnabledInherited() {
      return !this._isSet(115) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(115);
   }

   public boolean isCOMEnabledSet() {
      return this._isSet(115);
   }

   public void setCOMEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(115);
      boolean _oldVal = this._COMEnabled;
      this._COMEnabled = param0;
      this._postSet(115, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(115)) {
            source._postSetFirePropertyChange(115, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJRMPEnabled() {
      return !this._isSet(116) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(116) ? this._getDelegateBean().isJRMPEnabled() : this._JRMPEnabled;
   }

   public boolean isJRMPEnabledInherited() {
      return !this._isSet(116) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(116);
   }

   public boolean isJRMPEnabledSet() {
      return this._isSet(116);
   }

   public void setJRMPEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(116);
      boolean _oldVal = this._JRMPEnabled;
      this._JRMPEnabled = param0;
      this._postSet(116, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(116)) {
            source._postSetFirePropertyChange(116, wasSet, _oldVal, param0);
         }
      }

   }

   public COMMBean getCOM() {
      return this._COM;
   }

   public boolean isCOMInherited() {
      return false;
   }

   public boolean isCOMSet() {
      return this._isSet(117) || this._isAnythingSet((AbstractDescriptorBean)this.getCOM());
   }

   public void setCOM(COMMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 117)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(117);
      COMMBean _oldVal = this._COM;
      this._COM = param0;
      this._postSet(117, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(117)) {
            source._postSetFirePropertyChange(117, wasSet, _oldVal, param0);
         }
      }

   }

   public ServerDebugMBean getServerDebug() {
      return this._ServerDebug;
   }

   public boolean isServerDebugInherited() {
      return false;
   }

   public boolean isServerDebugSet() {
      return this._isSet(118) || this._isAnythingSet((AbstractDescriptorBean)this.getServerDebug());
   }

   public void setServerDebug(ServerDebugMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 118)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(118);
      ServerDebugMBean _oldVal = this._ServerDebug;
      this._ServerDebug = param0;
      this._postSet(118, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(118)) {
            source._postSetFirePropertyChange(118, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHttpdEnabled() {
      return !this._isSet(119) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(119) ? this._getDelegateBean().isHttpdEnabled() : this._HttpdEnabled;
   }

   public boolean isHttpdEnabledInherited() {
      return !this._isSet(119) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(119);
   }

   public boolean isHttpdEnabledSet() {
      return this._isSet(119);
   }

   public void setHttpdEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(119);
      boolean _oldVal = this._HttpdEnabled;
      this._HttpdEnabled = param0;
      this._postSet(119, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(119)) {
            source._postSetFirePropertyChange(119, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSystemPassword() {
      if (!this._isSet(120) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(120)) {
         return this._performMacroSubstitution(this._getDelegateBean().getSystemPassword(), this);
      } else {
         byte[] bEncrypted = this.getSystemPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("SystemPassword", bEncrypted);
      }
   }

   public boolean isSystemPasswordInherited() {
      return !this._isSet(120) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(120);
   }

   public boolean isSystemPasswordSet() {
      return this.isSystemPasswordEncryptedSet();
   }

   public void setSystemPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setSystemPasswordEncrypted(param0 == null ? null : this._encrypt("SystemPassword", param0));
   }

   public byte[] getSystemPasswordEncrypted() {
      return !this._isSet(121) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(121) ? this._getDelegateBean().getSystemPasswordEncrypted() : this._getHelper()._cloneArray(this._SystemPasswordEncrypted);
   }

   public String getSystemPasswordEncryptedAsString() {
      byte[] obj = this.getSystemPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSystemPasswordEncryptedInherited() {
      return !this._isSet(121) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(121);
   }

   public boolean isSystemPasswordEncryptedSet() {
      return this._isSet(121);
   }

   public void setSystemPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSystemPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isConsoleInputEnabled() {
      return !this._isSet(122) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(122) ? this._getDelegateBean().isConsoleInputEnabled() : this._ConsoleInputEnabled;
   }

   public boolean isConsoleInputEnabledInherited() {
      return !this._isSet(122) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(122);
   }

   public boolean isConsoleInputEnabledSet() {
      return this._isSet(122);
   }

   public void setConsoleInputEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(122);
      boolean _oldVal = this._ConsoleInputEnabled;
      this._ConsoleInputEnabled = param0;
      this._postSet(122, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(122)) {
            source._postSetFirePropertyChange(122, wasSet, _oldVal, param0);
         }
      }

   }

   public int getListenThreadStartDelaySecs() {
      return !this._isSet(123) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(123) ? this._getDelegateBean().getListenThreadStartDelaySecs() : this._ListenThreadStartDelaySecs;
   }

   public boolean isListenThreadStartDelaySecsInherited() {
      return !this._isSet(123) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(123);
   }

   public boolean isListenThreadStartDelaySecsSet() {
      return this._isSet(123);
   }

   public void setListenThreadStartDelaySecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(123);
      int _oldVal = this._ListenThreadStartDelaySecs;
      this._ListenThreadStartDelaySecs = param0;
      this._postSet(123, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(123)) {
            source._postSetFirePropertyChange(123, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getListenersBindEarly() {
      return !this._isSet(124) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(124) ? this._getDelegateBean().getListenersBindEarly() : this._ListenersBindEarly;
   }

   public boolean isListenersBindEarlyInherited() {
      return !this._isSet(124) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(124);
   }

   public boolean isListenersBindEarlySet() {
      return this._isSet(124);
   }

   public void setListenersBindEarly(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(124);
      boolean _oldVal = this._ListenersBindEarly;
      this._ListenersBindEarly = param0;
      this._postSet(124, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(124)) {
            source._postSetFirePropertyChange(124, wasSet, _oldVal, param0);
         }
      }

   }

   public String getListenAddress() {
      return !this._isSet(125) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(125) ? this._performMacroSubstitution(this._getDelegateBean().getListenAddress(), this) : this._ListenAddress;
   }

   public boolean isListenAddressInherited() {
      return !this._isSet(125) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(125);
   }

   public boolean isListenAddressSet() {
      return this._isSet(125);
   }

   public void setListenAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(125);
      String _oldVal = this._ListenAddress;
      this._ListenAddress = param0;
      this._postSet(125, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(125)) {
            source._postSetFirePropertyChange(125, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExternalDNSName() {
      return !this._isSet(126) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(126) ? this._performMacroSubstitution(this._getDelegateBean().getExternalDNSName(), this) : this._ExternalDNSName;
   }

   public boolean isExternalDNSNameInherited() {
      return !this._isSet(126) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(126);
   }

   public boolean isExternalDNSNameSet() {
      return this._isSet(126);
   }

   public void setExternalDNSName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(126);
      String _oldVal = this._ExternalDNSName;
      this._ExternalDNSName = param0;
      this._postSet(126, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(126)) {
            source._postSetFirePropertyChange(126, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getResolveDNSName() {
      return !this._isSet(127) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(127) ? this._getDelegateBean().getResolveDNSName() : this._ResolveDNSName;
   }

   public boolean isResolveDNSNameInherited() {
      return !this._isSet(127) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(127);
   }

   public boolean isResolveDNSNameSet() {
      return this._isSet(127);
   }

   public void setResolveDNSName(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(127);
      boolean _oldVal = this._ResolveDNSName;
      this._ResolveDNSName = param0;
      this._postSet(127, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(127)) {
            source._postSetFirePropertyChange(127, wasSet, _oldVal, param0);
         }
      }

   }

   public String getInterfaceAddress() {
      return !this._isSet(128) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(128) ? this._performMacroSubstitution(this._getDelegateBean().getInterfaceAddress(), this) : this._InterfaceAddress;
   }

   public boolean isInterfaceAddressInherited() {
      return !this._isSet(128) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(128);
   }

   public boolean isInterfaceAddressSet() {
      return this._isSet(128);
   }

   public void setInterfaceAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(128);
      String _oldVal = this._InterfaceAddress;
      this._InterfaceAddress = param0;
      this._postSet(128, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(128)) {
            source._postSetFirePropertyChange(128, wasSet, _oldVal, param0);
         }
      }

   }

   public NetworkAccessPointMBean[] getNetworkAccessPoints() {
      NetworkAccessPointMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(129)) {
         delegateArray = this._getDelegateBean().getNetworkAccessPoints();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._NetworkAccessPoints.length; ++j) {
               if (delegateArray[i].getName().equals(this._NetworkAccessPoints[j].getName())) {
                  ((NetworkAccessPointMBeanImpl)this._NetworkAccessPoints[j])._setDelegateBean((NetworkAccessPointMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  NetworkAccessPointMBeanImpl mbean = new NetworkAccessPointMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 129);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((NetworkAccessPointMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(129)) {
                     this.setNetworkAccessPoints((NetworkAccessPointMBean[])((NetworkAccessPointMBean[])this._getHelper()._extendArray(this._NetworkAccessPoints, NetworkAccessPointMBean.class, mbean)));
                  } else {
                     this.setNetworkAccessPoints(new NetworkAccessPointMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new NetworkAccessPointMBean[0];
      }

      if (this._NetworkAccessPoints != null) {
         List removeList = new ArrayList();
         NetworkAccessPointMBean[] var18 = this._NetworkAccessPoints;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            NetworkAccessPointMBean bn = var18[var5];
            NetworkAccessPointMBeanImpl bni = (NetworkAccessPointMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               NetworkAccessPointMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  NetworkAccessPointMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            NetworkAccessPointMBean removeIt = (NetworkAccessPointMBean)var19.next();
            NetworkAccessPointMBeanImpl removeItImpl = (NetworkAccessPointMBeanImpl)removeIt;
            NetworkAccessPointMBean[] _new = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])this._getHelper()._removeElement(this._NetworkAccessPoints, NetworkAccessPointMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setNetworkAccessPoints(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._NetworkAccessPoints;
   }

   public boolean isNetworkAccessPointsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(129)) {
         NetworkAccessPointMBean[] elements = this.getNetworkAccessPoints();
         NetworkAccessPointMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isNetworkAccessPointsSet() {
      return this._isSet(129);
   }

   public NetworkAccessPointMBean lookupNetworkAccessPoint(String param0) {
      Object[] aary = (Object[])this.getNetworkAccessPoints();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      NetworkAccessPointMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (NetworkAccessPointMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public NetworkAccessPointMBean createNetworkAccessPoint(String param0) {
      NetworkAccessPointMBeanImpl lookup = (NetworkAccessPointMBeanImpl)this.lookupNetworkAccessPoint(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         NetworkAccessPointMBeanImpl _val = new NetworkAccessPointMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addNetworkAccessPoint(_val);
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

   public void destroyNetworkAccessPoint(NetworkAccessPointMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 129);
         NetworkAccessPointMBean[] _old = this.getNetworkAccessPoints();
         NetworkAccessPointMBean[] _new = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])this._getHelper()._removeElement(_old, NetworkAccessPointMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var6.next();
                  NetworkAccessPointMBeanImpl childImpl = (NetworkAccessPointMBeanImpl)_child;
                  NetworkAccessPointMBeanImpl lookup = (NetworkAccessPointMBeanImpl)source.lookupNetworkAccessPoint(childImpl.getName());
                  if (lookup != null) {
                     source.destroyNetworkAccessPoint(lookup);
                  }
               }

               this.setNetworkAccessPoints(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public KernelDebugMBean getKernelDebug() {
      return this._customizer.getKernelDebug();
   }

   public boolean isKernelDebugInherited() {
      return false;
   }

   public boolean isKernelDebugSet() {
      return this._isSet(51);
   }

   public void setKernelDebug(KernelDebugMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._KernelDebug = param0;
   }

   public void setNetworkAccessPoints(NetworkAccessPointMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      NetworkAccessPointMBean[] param0 = param0 == null ? new NetworkAccessPointMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._NetworkAccessPoints, (Object[])param0, handler, new Comparator() {
            public int compare(NetworkAccessPointMBean o1, NetworkAccessPointMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            NetworkAccessPointMBean bean = (NetworkAccessPointMBean)var3.next();
            NetworkAccessPointMBeanImpl beanImpl = (NetworkAccessPointMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 129)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(129);
      NetworkAccessPointMBean[] _oldVal = this._NetworkAccessPoints;
      this._NetworkAccessPoints = (NetworkAccessPointMBean[])param0;
      this._postSet(129, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(129)) {
            source._postSetFirePropertyChange(129, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addNetworkAccessPoint(NetworkAccessPointMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 129)) {
         NetworkAccessPointMBean[] _new;
         if (this._isSet(129)) {
            _new = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])this._getHelper()._extendArray(this.getNetworkAccessPoints(), NetworkAccessPointMBean.class, param0));
         } else {
            _new = new NetworkAccessPointMBean[]{param0};
         }

         try {
            this.setNetworkAccessPoints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeNetworkAccessPoint(NetworkAccessPointMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroyNetworkAccessPoint(param0);
      return true;
   }

   public int getAcceptBacklog() {
      return !this._isSet(130) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(130) ? this._getDelegateBean().getAcceptBacklog() : this._AcceptBacklog;
   }

   public boolean isAcceptBacklogInherited() {
      return !this._isSet(130) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(130);
   }

   public boolean isAcceptBacklogSet() {
      return this._isSet(130);
   }

   public void setAcceptBacklog(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("AcceptBacklog", param0, 0);
      boolean wasSet = this._isSet(130);
      int _oldVal = this._AcceptBacklog;
      this._AcceptBacklog = param0;
      this._postSet(130, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(130)) {
            source._postSetFirePropertyChange(130, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxBackoffBetweenFailures() {
      return !this._isSet(131) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(131) ? this._getDelegateBean().getMaxBackoffBetweenFailures() : this._MaxBackoffBetweenFailures;
   }

   public boolean isMaxBackoffBetweenFailuresInherited() {
      return !this._isSet(131) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(131);
   }

   public boolean isMaxBackoffBetweenFailuresSet() {
      return this._isSet(131);
   }

   public boolean isStdoutEnabled() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56) ? this._getDelegateBean().isStdoutEnabled() : this._customizer.isStdoutEnabled();
   }

   public boolean isStdoutEnabledInherited() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56);
   }

   public boolean isStdoutEnabledSet() {
      return this._isSet(56);
   }

   public void setMaxBackoffBetweenFailures(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MaxBackoffBetweenFailures", param0, 0);
      boolean wasSet = this._isSet(131);
      int _oldVal = this._MaxBackoffBetweenFailures;
      this._MaxBackoffBetweenFailures = param0;
      this._postSet(131, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(131)) {
            source._postSetFirePropertyChange(131, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLoginTimeoutMillis() {
      return !this._isSet(132) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(132) ? this._getDelegateBean().getLoginTimeoutMillis() : this._LoginTimeoutMillis;
   }

   public boolean isLoginTimeoutMillisInherited() {
      return !this._isSet(132) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(132);
   }

   public boolean isLoginTimeoutMillisSet() {
      return this._isSet(132);
   }

   public void setStdoutEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(56);
      boolean _oldVal = this.isStdoutEnabled();
      this._customizer.setStdoutEnabled(param0);
      this._postSet(56, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(56)) {
            source._postSetFirePropertyChange(56, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStdoutSeverityLevel() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57) ? this._getDelegateBean().getStdoutSeverityLevel() : this._customizer.getStdoutSeverityLevel();
   }

   public boolean isStdoutSeverityLevelInherited() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57);
   }

   public boolean isStdoutSeverityLevelSet() {
      return this._isSet(57);
   }

   public void setLoginTimeoutMillis(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LoginTimeoutMillis", (long)param0, 0L, 100000L);
      boolean wasSet = this._isSet(132);
      int _oldVal = this._LoginTimeoutMillis;
      this._LoginTimeoutMillis = param0;
      this._postSet(132, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(132)) {
            source._postSetFirePropertyChange(132, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAdministrationPortEnabled() {
      if (!this._isSet(133) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(133)) {
         return this._getDelegateBean().isAdministrationPortEnabled();
      } else {
         if (!this._isSet(133)) {
            try {
               return ((DomainMBean)this.getParent()).isAdministrationPortEnabled();
            } catch (NullPointerException var2) {
            }
         }

         return this._AdministrationPortEnabled;
      }
   }

   public boolean isAdministrationPortEnabledInherited() {
      return !this._isSet(133) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(133);
   }

   public boolean isAdministrationPortEnabledSet() {
      return this._isSet(133);
   }

   public void setStdoutSeverityLevel(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      int[] _set = new int[]{256, 128, 64, 16, 8, 32, 4, 2, 1, 0};
      param0 = LegalChecks.checkInEnum("StdoutSeverityLevel", param0, _set);
      boolean wasSet = this._isSet(57);
      int _oldVal = this.getStdoutSeverityLevel();
      this._customizer.setStdoutSeverityLevel(param0);
      this._postSet(57, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(57)) {
            source._postSetFirePropertyChange(57, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStdoutDebugEnabled() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58) ? this._getDelegateBean().isStdoutDebugEnabled() : this._customizer.isStdoutDebugEnabled();
   }

   public boolean isStdoutDebugEnabledInherited() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58);
   }

   public boolean isStdoutDebugEnabledSet() {
      return this._isSet(58);
   }

   public void setAdministrationPortEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(133);
      boolean _oldVal = this._AdministrationPortEnabled;
      this._AdministrationPortEnabled = param0;
      this._postSet(133, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(133)) {
            source._postSetFirePropertyChange(133, wasSet, _oldVal, param0);
         }
      }

   }

   public int getAdministrationPort() {
      if (!this._isSet(134) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(134)) {
         return this._getDelegateBean().getAdministrationPort();
      } else {
         if (!this._isSet(134)) {
            try {
               return ((DomainMBean)this.getParent()).getAdministrationPort();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getAdministrationPort();
      }
   }

   public boolean isAdministrationPortInherited() {
      return !this._isSet(134) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(134);
   }

   public boolean isAdministrationPortSet() {
      return this._isSet(134);
   }

   public void setStdoutDebugEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(58);
      boolean _oldVal = this.isStdoutDebugEnabled();
      this._customizer.setStdoutDebugEnabled(param0);
      this._postSet(58, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(58)) {
            source._postSetFirePropertyChange(58, wasSet, _oldVal, param0);
         }
      }

   }

   public void setAdministrationPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("AdministrationPort", (long)param0, 0L, 65535L);
      boolean wasSet = this._isSet(134);
      int _oldVal = this.getAdministrationPort();
      this._customizer.setAdministrationPort(param0);
      this._postSet(134, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(134)) {
            source._postSetFirePropertyChange(134, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getJNDITransportableObjectFactoryList() {
      return !this._isSet(135) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(135) ? this._getDelegateBean().getJNDITransportableObjectFactoryList() : this._JNDITransportableObjectFactoryList;
   }

   public boolean isJNDITransportableObjectFactoryListInherited() {
      return !this._isSet(135) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(135);
   }

   public boolean isJNDITransportableObjectFactoryListSet() {
      return this._isSet(135);
   }

   public void setJNDITransportableObjectFactoryList(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(135);
      String[] _oldVal = this._JNDITransportableObjectFactoryList;
      this._JNDITransportableObjectFactoryList = param0;
      this._postSet(135, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(135)) {
            source._postSetFirePropertyChange(135, wasSet, _oldVal, param0);
         }
      }

   }

   public Map getIIOPConnectionPools() {
      return !this._isSet(136) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(136) ? this._getDelegateBean().getIIOPConnectionPools() : this._IIOPConnectionPools;
   }

   public String getIIOPConnectionPoolsAsString() {
      return StringHelper.objectToString(this.getIIOPConnectionPools());
   }

   public boolean isIIOPConnectionPoolsInherited() {
      return !this._isSet(136) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(136);
   }

   public boolean isIIOPConnectionPoolsSet() {
      return this._isSet(136);
   }

   public void setIIOPConnectionPoolsAsString(String param0) {
      try {
         this.setIIOPConnectionPools(StringHelper.stringToMap(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setIIOPConnectionPools(Map param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(136);
      Map _oldVal = this._IIOPConnectionPools;
      this._IIOPConnectionPools = param0;
      this._postSet(136, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(136)) {
            source._postSetFirePropertyChange(136, wasSet, _oldVal, param0);
         }
      }

   }

   public XMLRegistryMBean getXMLRegistry() {
      return !this._isSet(137) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(137) ? this._getDelegateBean().getXMLRegistry() : this._XMLRegistry;
   }

   public String getXMLRegistryAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getXMLRegistry();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isXMLRegistryInherited() {
      return !this._isSet(137) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(137);
   }

   public boolean isXMLRegistrySet() {
      return this._isSet(137);
   }

   public void setXMLRegistryAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, XMLRegistryMBean.class, new ReferenceManager.Resolver(this, 137) {
            public void resolveReference(Object value) {
               try {
                  ServerTemplateMBeanImpl.this.setXMLRegistry((XMLRegistryMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         XMLRegistryMBean _oldVal = this._XMLRegistry;
         this._initializeProperty(137);
         this._postSet(137, _oldVal, this._XMLRegistry);
      }

   }

   public void setXMLEntityCache(XMLEntityCacheMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 138, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerTemplateMBeanImpl.this.getXMLEntityCache();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(138);
      XMLEntityCacheMBean _oldVal = this._XMLEntityCache;
      this._XMLEntityCache = param0;
      this._postSet(138, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(138)) {
            source._postSetFirePropertyChange(138, wasSet, _oldVal, param0);
         }
      }

   }

   public XMLEntityCacheMBean getXMLEntityCache() {
      return !this._isSet(138) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(138) ? this._getDelegateBean().getXMLEntityCache() : this._XMLEntityCache;
   }

   public String getXMLEntityCacheAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getXMLEntityCache();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isXMLEntityCacheInherited() {
      return !this._isSet(138) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(138);
   }

   public boolean isXMLEntityCacheSet() {
      return this._isSet(138);
   }

   public void setXMLEntityCacheAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, XMLEntityCacheMBean.class, new ReferenceManager.Resolver(this, 138) {
            public void resolveReference(Object value) {
               try {
                  ServerTemplateMBeanImpl.this.setXMLEntityCache((XMLEntityCacheMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         XMLEntityCacheMBean _oldVal = this._XMLEntityCache;
         this._initializeProperty(138);
         this._postSet(138, _oldVal, this._XMLEntityCache);
      }

   }

   public void setXMLRegistry(XMLRegistryMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 137, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerTemplateMBeanImpl.this.getXMLRegistry();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(137);
      XMLRegistryMBean _oldVal = this._XMLRegistry;
      this._XMLRegistry = param0;
      this._postSet(137, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(137)) {
            source._postSetFirePropertyChange(137, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJavaCompiler() {
      return !this._isSet(139) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(139) ? this._performMacroSubstitution(this._getDelegateBean().getJavaCompiler(), this) : this._JavaCompiler;
   }

   public String getStdoutFormat() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64) ? this._performMacroSubstitution(this._getDelegateBean().getStdoutFormat(), this) : this._customizer.getStdoutFormat();
   }

   public boolean isJavaCompilerInherited() {
      return !this._isSet(139) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(139);
   }

   public boolean isJavaCompilerSet() {
      return this._isSet(139);
   }

   public boolean isStdoutFormatInherited() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64);
   }

   public boolean isStdoutFormatSet() {
      return this._isSet(64);
   }

   public void setJavaCompiler(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(139);
      String _oldVal = this._JavaCompiler;
      this._JavaCompiler = param0;
      this._postSet(139, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(139)) {
            source._postSetFirePropertyChange(139, wasSet, _oldVal, param0);
         }
      }

   }

   public void setStdoutFormat(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"standard", "noid"};
      param0 = LegalChecks.checkInEnum("StdoutFormat", param0, _set);
      boolean wasSet = this._isSet(64);
      String _oldVal = this.getStdoutFormat();
      this._customizer.setStdoutFormat(param0);
      this._postSet(64, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(64)) {
            source._postSetFirePropertyChange(64, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJavaCompilerPreClassPath() {
      return !this._isSet(140) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(140) ? this._performMacroSubstitution(this._getDelegateBean().getJavaCompilerPreClassPath(), this) : this._JavaCompilerPreClassPath;
   }

   public boolean isJavaCompilerPreClassPathInherited() {
      return !this._isSet(140) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(140);
   }

   public boolean isJavaCompilerPreClassPathSet() {
      return this._isSet(140);
   }

   public boolean isStdoutLogStack() {
      return !this._isSet(65) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(65) ? this._getDelegateBean().isStdoutLogStack() : this._customizer.isStdoutLogStack();
   }

   public boolean isStdoutLogStackInherited() {
      return !this._isSet(65) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(65);
   }

   public boolean isStdoutLogStackSet() {
      return this._isSet(65);
   }

   public void setJavaCompilerPreClassPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(140);
      String _oldVal = this._JavaCompilerPreClassPath;
      this._JavaCompilerPreClassPath = param0;
      this._postSet(140, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(140)) {
            source._postSetFirePropertyChange(140, wasSet, _oldVal, param0);
         }
      }

   }

   public void setStdoutLogStack(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(65);
      boolean _oldVal = this.isStdoutLogStack();
      this._customizer.setStdoutLogStack(param0);
      this._postSet(65, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(65)) {
            source._postSetFirePropertyChange(65, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJavaCompilerPostClassPath() {
      return !this._isSet(141) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(141) ? this._performMacroSubstitution(this._getDelegateBean().getJavaCompilerPostClassPath(), this) : this._JavaCompilerPostClassPath;
   }

   public boolean isJavaCompilerPostClassPathInherited() {
      return !this._isSet(141) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(141);
   }

   public boolean isJavaCompilerPostClassPathSet() {
      return this._isSet(141);
   }

   public void setJavaCompilerPostClassPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(141);
      String _oldVal = this._JavaCompilerPostClassPath;
      this._JavaCompilerPostClassPath = param0;
      this._postSet(141, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(141)) {
            source._postSetFirePropertyChange(141, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExtraRmicOptions() {
      return !this._isSet(142) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(142) ? this._performMacroSubstitution(this._getDelegateBean().getExtraRmicOptions(), this) : this._ExtraRmicOptions;
   }

   public boolean isExtraRmicOptionsInherited() {
      return !this._isSet(142) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(142);
   }

   public boolean isExtraRmicOptionsSet() {
      return this._isSet(142);
   }

   public void setExtraRmicOptions(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(142);
      String _oldVal = this._ExtraRmicOptions;
      this._ExtraRmicOptions = param0;
      this._postSet(142, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(142)) {
            source._postSetFirePropertyChange(142, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExtraEjbcOptions() {
      return !this._isSet(143) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(143) ? this._performMacroSubstitution(this._getDelegateBean().getExtraEjbcOptions(), this) : this._ExtraEjbcOptions;
   }

   public boolean isExtraEjbcOptionsInherited() {
      return !this._isSet(143) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(143);
   }

   public boolean isExtraEjbcOptionsSet() {
      return this._isSet(143);
   }

   public void setExtraEjbcOptions(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(143);
      String _oldVal = this._ExtraEjbcOptions;
      this._ExtraEjbcOptions = param0;
      this._postSet(143, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(143)) {
            source._postSetFirePropertyChange(143, wasSet, _oldVal, param0);
         }
      }

   }

   public String getVerboseEJBDeploymentEnabled() {
      return !this._isSet(144) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(144) ? this._performMacroSubstitution(this._getDelegateBean().getVerboseEJBDeploymentEnabled(), this) : this._VerboseEJBDeploymentEnabled;
   }

   public boolean isVerboseEJBDeploymentEnabledInherited() {
      return !this._isSet(144) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(144);
   }

   public boolean isVerboseEJBDeploymentEnabledSet() {
      return this._isSet(144);
   }

   public void setVerboseEJBDeploymentEnabled(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(144);
      String _oldVal = this._VerboseEJBDeploymentEnabled;
      this._VerboseEJBDeploymentEnabled = param0;
      this._postSet(144, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(144)) {
            source._postSetFirePropertyChange(144, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransactionLogFilePrefix() {
      return !this._isSet(145) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(145) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionLogFilePrefix(), this) : this._TransactionLogFilePrefix;
   }

   public boolean isTransactionLogFilePrefixInherited() {
      return !this._isSet(145) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(145);
   }

   public boolean isTransactionLogFilePrefixSet() {
      return this._isSet(145);
   }

   public void setTransactionLogFilePrefix(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(145);
      String _oldVal = this._TransactionLogFilePrefix;
      this._TransactionLogFilePrefix = param0;
      this._postSet(145, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(145)) {
            source._postSetFirePropertyChange(145, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransactionLogFileWritePolicy() {
      return !this._isSet(146) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(146) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionLogFileWritePolicy(), this) : this._TransactionLogFileWritePolicy;
   }

   public boolean isTransactionLogFileWritePolicyInherited() {
      return !this._isSet(146) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(146);
   }

   public boolean isTransactionLogFileWritePolicySet() {
      return this._isSet(146);
   }

   public void setTransactionLogFileWritePolicy(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Cache-Flush", "Direct-Write"};
      param0 = LegalChecks.checkInEnum("TransactionLogFileWritePolicy", param0, _set);
      LegalChecks.checkNonNull("TransactionLogFileWritePolicy", param0);
      boolean wasSet = this._isSet(146);
      String _oldVal = this._TransactionLogFileWritePolicy;
      this._TransactionLogFileWritePolicy = param0;
      this._postSet(146, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(146)) {
            source._postSetFirePropertyChange(146, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isNetworkClassLoadingEnabled() {
      return !this._isSet(147) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(147) ? this._getDelegateBean().isNetworkClassLoadingEnabled() : this._NetworkClassLoadingEnabled;
   }

   public boolean isNetworkClassLoadingEnabledInherited() {
      return !this._isSet(147) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(147);
   }

   public boolean isNetworkClassLoadingEnabledSet() {
      return this._isSet(147);
   }

   public void setNetworkClassLoadingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(147);
      boolean _oldVal = this._NetworkClassLoadingEnabled;
      this._NetworkClassLoadingEnabled = param0;
      this._postSet(147, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(147)) {
            source._postSetFirePropertyChange(147, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isTunnelingEnabled() {
      return !this._isSet(148) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(148) ? this._getDelegateBean().isTunnelingEnabled() : this._TunnelingEnabled;
   }

   public boolean isTunnelingEnabledInherited() {
      return !this._isSet(148) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(148);
   }

   public boolean isTunnelingEnabledSet() {
      return this._isSet(148);
   }

   public void setTunnelingEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(148);
      boolean _oldVal = this._TunnelingEnabled;
      this._TunnelingEnabled = param0;
      this._postSet(148, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(148)) {
            source._postSetFirePropertyChange(148, wasSet, _oldVal, param0);
         }
      }

   }

   public int getTunnelingClientPingSecs() {
      return !this._isSet(149) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(149) ? this._getDelegateBean().getTunnelingClientPingSecs() : this._TunnelingClientPingSecs;
   }

   public boolean isTunnelingClientPingSecsInherited() {
      return !this._isSet(149) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(149);
   }

   public boolean isTunnelingClientPingSecsSet() {
      return this._isSet(149);
   }

   public void setTunnelingClientPingSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("TunnelingClientPingSecs", param0, 1);
      boolean wasSet = this._isSet(149);
      int _oldVal = this._TunnelingClientPingSecs;
      this._TunnelingClientPingSecs = param0;
      this._postSet(149, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(149)) {
            source._postSetFirePropertyChange(149, wasSet, _oldVal, param0);
         }
      }

   }

   public int getTunnelingClientTimeoutSecs() {
      return !this._isSet(150) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(150) ? this._getDelegateBean().getTunnelingClientTimeoutSecs() : this._TunnelingClientTimeoutSecs;
   }

   public boolean isTunnelingClientTimeoutSecsInherited() {
      return !this._isSet(150) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(150);
   }

   public boolean isTunnelingClientTimeoutSecsSet() {
      return this._isSet(150);
   }

   public void setTunnelingClientTimeoutSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("TunnelingClientTimeoutSecs", param0, 1);
      boolean wasSet = this._isSet(150);
      int _oldVal = this._TunnelingClientTimeoutSecs;
      this._TunnelingClientTimeoutSecs = param0;
      this._postSet(150, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(150)) {
            source._postSetFirePropertyChange(150, wasSet, _oldVal, param0);
         }
      }

   }

   public int getAdminReconnectIntervalSeconds() {
      return !this._isSet(151) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(151) ? this._getDelegateBean().getAdminReconnectIntervalSeconds() : this._AdminReconnectIntervalSeconds;
   }

   public boolean isAdminReconnectIntervalSecondsInherited() {
      return !this._isSet(151) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(151);
   }

   public boolean isAdminReconnectIntervalSecondsSet() {
      return this._isSet(151);
   }

   public void setAdminReconnectIntervalSeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("AdminReconnectIntervalSeconds", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(151);
      int _oldVal = this._AdminReconnectIntervalSeconds;
      this._AdminReconnectIntervalSeconds = param0;
      this._postSet(151, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(151)) {
            source._postSetFirePropertyChange(151, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJMSDefaultConnectionFactoriesEnabled() {
      return !this._isSet(152) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(152) ? this._getDelegateBean().isJMSDefaultConnectionFactoriesEnabled() : this._JMSDefaultConnectionFactoriesEnabled;
   }

   public boolean isJMSDefaultConnectionFactoriesEnabledInherited() {
      return !this._isSet(152) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(152);
   }

   public boolean isJMSDefaultConnectionFactoriesEnabledSet() {
      return this._isSet(152);
   }

   public void setJMSDefaultConnectionFactoriesEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(152);
      boolean _oldVal = this._JMSDefaultConnectionFactoriesEnabled;
      this._JMSDefaultConnectionFactoriesEnabled = param0;
      this._postSet(152, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(152)) {
            source._postSetFirePropertyChange(152, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJMSConnectionFactoryUnmappedResRefMode() {
      return !this._isSet(153) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(153) ? this._performMacroSubstitution(this._getDelegateBean().getJMSConnectionFactoryUnmappedResRefMode(), this) : this._JMSConnectionFactoryUnmappedResRefMode;
   }

   public boolean isJMSConnectionFactoryUnmappedResRefModeInherited() {
      return !this._isSet(153) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(153);
   }

   public boolean isJMSConnectionFactoryUnmappedResRefModeSet() {
      return this._isSet(153);
   }

   public void setJMSConnectionFactoryUnmappedResRefMode(String param0) throws DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"ReturnDefault", "FailSafe"};
      param0 = LegalChecks.checkInEnum("JMSConnectionFactoryUnmappedResRefMode", param0, _set);
      boolean wasSet = this._isSet(153);
      String _oldVal = this._JMSConnectionFactoryUnmappedResRefMode;
      this._JMSConnectionFactoryUnmappedResRefMode = param0;
      this._postSet(153, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(153)) {
            source._postSetFirePropertyChange(153, wasSet, _oldVal, param0);
         }
      }

   }

   public ServerStartMBean getServerStart() {
      return this._ServerStart;
   }

   public boolean isServerStartInherited() {
      return false;
   }

   public boolean isServerStartSet() {
      return this._isSet(154) || this._isAnythingSet((AbstractDescriptorBean)this.getServerStart());
   }

   public void setServerStart(ServerStartMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 154)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(154);
      ServerStartMBean _oldVal = this._ServerStart;
      this._ServerStart = param0;
      this._postSet(154, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(154)) {
            source._postSetFirePropertyChange(154, wasSet, _oldVal, param0);
         }
      }

   }

   public int getListenDelaySecs() {
      return !this._isSet(155) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(155) ? this._getDelegateBean().getListenDelaySecs() : this._ListenDelaySecs;
   }

   public boolean isListenDelaySecsInherited() {
      return !this._isSet(155) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(155);
   }

   public boolean isListenDelaySecsSet() {
      return this._isSet(155);
   }

   public void setListenDelaySecs(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(155);
      int _oldVal = this._ListenDelaySecs;
      this._ListenDelaySecs = param0;
      this._postSet(155, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(155)) {
            source._postSetFirePropertyChange(155, wasSet, _oldVal, param0);
         }
      }

   }

   public JTAMigratableTargetMBean getJTAMigratableTarget() {
      return this._JTAMigratableTarget;
   }

   public boolean isJTAMigratableTargetInherited() {
      return false;
   }

   public boolean isJTAMigratableTargetSet() {
      return this._isSet(156);
   }

   public void setJTAMigratableTarget(JTAMigratableTargetMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJTAMigratableTarget() != null && param0 != this.getJTAMigratableTarget()) {
         throw new BeanAlreadyExistsException(this.getJTAMigratableTarget() + " has already been created");
      } else {
         if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
            this._untransient();
         }

         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 156)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         boolean wasSet = this._isSet(156);
         JTAMigratableTargetMBean _oldVal = this._JTAMigratableTarget;
         this._JTAMigratableTarget = param0;
         this._postSet(156, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(156)) {
               source._postSetFirePropertyChange(156, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public JTAMigratableTargetMBean createJTAMigratableTarget() {
      JTAMigratableTargetMBeanImpl _val = new JTAMigratableTargetMBeanImpl(this, -1);

      try {
         this.setJTAMigratableTarget(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJTAMigratableTarget() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JTAMigratableTarget;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJTAMigratableTarget((JTAMigratableTargetMBean)null);
               this._unSet(156);
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

   public int getLowMemoryTimeInterval() {
      return !this._isSet(157) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(157) ? this._getDelegateBean().getLowMemoryTimeInterval() : this._LowMemoryTimeInterval;
   }

   public boolean isLowMemoryTimeIntervalInherited() {
      return !this._isSet(157) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(157);
   }

   public boolean isLowMemoryTimeIntervalSet() {
      return this._isSet(157);
   }

   public void setLowMemoryTimeInterval(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LowMemoryTimeInterval", (long)param0, 300L, 2147483647L);
      boolean wasSet = this._isSet(157);
      int _oldVal = this._LowMemoryTimeInterval;
      this._LowMemoryTimeInterval = param0;
      this._postSet(157, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(157)) {
            source._postSetFirePropertyChange(157, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLowMemorySampleSize() {
      return !this._isSet(158) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(158) ? this._getDelegateBean().getLowMemorySampleSize() : this._LowMemorySampleSize;
   }

   public boolean isLowMemorySampleSizeInherited() {
      return !this._isSet(158) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(158);
   }

   public boolean isLowMemorySampleSizeSet() {
      return this._isSet(158);
   }

   public void setLowMemorySampleSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LowMemorySampleSize", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(158);
      int _oldVal = this._LowMemorySampleSize;
      this._LowMemorySampleSize = param0;
      this._postSet(158, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(158)) {
            source._postSetFirePropertyChange(158, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLowMemoryGranularityLevel() {
      return !this._isSet(159) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(159) ? this._getDelegateBean().getLowMemoryGranularityLevel() : this._LowMemoryGranularityLevel;
   }

   public boolean isLowMemoryGranularityLevelInherited() {
      return !this._isSet(159) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(159);
   }

   public boolean isLowMemoryGranularityLevelSet() {
      return this._isSet(159);
   }

   public void setLowMemoryGranularityLevel(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LowMemoryGranularityLevel", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(159);
      int _oldVal = this._LowMemoryGranularityLevel;
      this._LowMemoryGranularityLevel = param0;
      this._postSet(159, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(159)) {
            source._postSetFirePropertyChange(159, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLowMemoryGCThreshold() {
      return !this._isSet(160) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(160) ? this._getDelegateBean().getLowMemoryGCThreshold() : this._LowMemoryGCThreshold;
   }

   public boolean isLowMemoryGCThresholdInherited() {
      return !this._isSet(160) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(160);
   }

   public boolean isLowMemoryGCThresholdSet() {
      return this._isSet(160);
   }

   public void setLowMemoryGCThreshold(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LowMemoryGCThreshold", (long)param0, 0L, 99L);
      boolean wasSet = this._isSet(160);
      int _oldVal = this._LowMemoryGCThreshold;
      this._LowMemoryGCThreshold = param0;
      this._postSet(160, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(160)) {
            source._postSetFirePropertyChange(160, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStagingDirectoryName() {
      return !this._isSet(161) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(161) ? this._performMacroSubstitution(this._getDelegateBean().getStagingDirectoryName(), this) : this._customizer.getStagingDirectoryName();
   }

   public boolean isStagingDirectoryNameInherited() {
      return !this._isSet(161) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(161);
   }

   public boolean isStagingDirectoryNameSet() {
      return this._isSet(161);
   }

   public void setStagingDirectoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(161);
      String _oldVal = this.getStagingDirectoryName();
      this._customizer.setStagingDirectoryName(param0);
      this._postSet(161, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(161)) {
            source._postSetFirePropertyChange(161, wasSet, _oldVal, param0);
         }
      }

   }

   public String getUploadDirectoryName() {
      return !this._isSet(162) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(162) ? this._performMacroSubstitution(this._getDelegateBean().getUploadDirectoryName(), this) : this._customizer.getUploadDirectoryName();
   }

   public boolean isUploadDirectoryNameInherited() {
      return !this._isSet(162) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(162);
   }

   public boolean isUploadDirectoryNameSet() {
      return this._isSet(162);
   }

   public void setUploadDirectoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(162);
      String _oldVal = this.getUploadDirectoryName();
      this._customizer.setUploadDirectoryName(param0);
      this._postSet(162, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(162)) {
            source._postSetFirePropertyChange(162, wasSet, _oldVal, param0);
         }
      }

   }

   public String getActiveDirectoryName() {
      return !this._isSet(163) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(163) ? this._performMacroSubstitution(this._getDelegateBean().getActiveDirectoryName(), this) : this._customizer.getActiveDirectoryName();
   }

   public boolean isActiveDirectoryNameInherited() {
      return !this._isSet(163) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(163);
   }

   public boolean isActiveDirectoryNameSet() {
      return this._isSet(163);
   }

   public void setActiveDirectoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(163);
      String _oldVal = this.getActiveDirectoryName();
      this._customizer.setActiveDirectoryName(param0);
      this._postSet(163, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(163)) {
            source._postSetFirePropertyChange(163, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStagingMode() {
      if (!this._isSet(164) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(164)) {
         return this._performMacroSubstitution(this._getDelegateBean().getStagingMode(), this);
      } else {
         if (!this._isSet(164)) {
            try {
               return DeployHelper.determineDefaultStagingMode(this.getName());
            } catch (NullPointerException var2) {
            }
         }

         return this._StagingMode;
      }
   }

   public boolean isStagingModeInherited() {
      return !this._isSet(164) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(164);
   }

   public boolean isStagingModeSet() {
      return this._isSet(164);
   }

   public void setStagingMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{ServerMBean.DEFAULT_STAGE, "stage", "nostage", "external_stage"};
      param0 = LegalChecks.checkInEnum("StagingMode", param0, _set);
      boolean wasSet = this._isSet(164);
      String _oldVal = this._StagingMode;
      this._StagingMode = param0;
      this._postSet(164, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(164)) {
            source._postSetFirePropertyChange(164, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getAutoRestart() {
      return !this._isSet(165) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(165) ? this._getDelegateBean().getAutoRestart() : this._AutoRestart;
   }

   public boolean isAutoRestartInherited() {
      return !this._isSet(165) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(165);
   }

   public boolean isAutoRestartSet() {
      return this._isSet(165);
   }

   public void setAutoRestart(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(165);
      boolean _oldVal = this._AutoRestart;
      this._AutoRestart = param0;
      this._postSet(165, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(165)) {
            source._postSetFirePropertyChange(165, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getAutoKillIfFailed() {
      return !this._isSet(166) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(166) ? this._getDelegateBean().getAutoKillIfFailed() : this._AutoKillIfFailed;
   }

   public boolean isAutoKillIfFailedInherited() {
      return !this._isSet(166) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(166);
   }

   public boolean isAutoKillIfFailedSet() {
      return this._isSet(166);
   }

   public void setAutoKillIfFailed(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(166);
      boolean _oldVal = this._AutoKillIfFailed;
      this._AutoKillIfFailed = param0;
      this._postSet(166, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(166)) {
            source._postSetFirePropertyChange(166, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRestartIntervalSeconds() {
      return !this._isSet(167) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(167) ? this._getDelegateBean().getRestartIntervalSeconds() : this._RestartIntervalSeconds;
   }

   public boolean isRestartIntervalSecondsInherited() {
      return !this._isSet(167) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(167);
   }

   public boolean isRestartIntervalSecondsSet() {
      return this._isSet(167);
   }

   public void setRestartIntervalSeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RestartIntervalSeconds", (long)param0, 300L, 2147483647L);
      boolean wasSet = this._isSet(167);
      int _oldVal = this._RestartIntervalSeconds;
      this._RestartIntervalSeconds = param0;
      this._postSet(167, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(167)) {
            source._postSetFirePropertyChange(167, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRestartMax() {
      return !this._isSet(168) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(168) ? this._getDelegateBean().getRestartMax() : this._RestartMax;
   }

   public boolean isRestartMaxInherited() {
      return !this._isSet(168) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(168);
   }

   public boolean isRestartMaxSet() {
      return this._isSet(168);
   }

   public void setRestartMax(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RestartMax", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(168);
      int _oldVal = this._RestartMax;
      this._RestartMax = param0;
      this._postSet(168, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(168)) {
            source._postSetFirePropertyChange(168, wasSet, _oldVal, param0);
         }
      }

   }

   public int getHealthCheckIntervalSeconds() {
      return !this._isSet(169) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(169) ? this._getDelegateBean().getHealthCheckIntervalSeconds() : this._HealthCheckIntervalSeconds;
   }

   public boolean isHealthCheckIntervalSecondsInherited() {
      return !this._isSet(169) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(169);
   }

   public boolean isHealthCheckIntervalSecondsSet() {
      return this._isSet(169);
   }

   public void setHealthCheckIntervalSeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("HealthCheckIntervalSeconds", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(169);
      int _oldVal = this._HealthCheckIntervalSeconds;
      this._HealthCheckIntervalSeconds = param0;
      this._postSet(169, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(169)) {
            source._postSetFirePropertyChange(169, wasSet, _oldVal, param0);
         }
      }

   }

   public int getHealthCheckTimeoutSeconds() {
      return !this._isSet(170) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(170) ? this._getDelegateBean().getHealthCheckTimeoutSeconds() : this._HealthCheckTimeoutSeconds;
   }

   public boolean isHealthCheckTimeoutSecondsInherited() {
      return !this._isSet(170) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(170);
   }

   public boolean isHealthCheckTimeoutSecondsSet() {
      return this._isSet(170);
   }

   public void setHealthCheckTimeoutSeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("HealthCheckTimeoutSeconds", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(170);
      int _oldVal = this._HealthCheckTimeoutSeconds;
      this._HealthCheckTimeoutSeconds = param0;
      this._postSet(170, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(170)) {
            source._postSetFirePropertyChange(170, wasSet, _oldVal, param0);
         }
      }

   }

   public int getHealthCheckStartDelaySeconds() {
      return !this._isSet(171) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(171) ? this._getDelegateBean().getHealthCheckStartDelaySeconds() : this._HealthCheckStartDelaySeconds;
   }

   public boolean isHealthCheckStartDelaySecondsInherited() {
      return !this._isSet(171) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(171);
   }

   public boolean isHealthCheckStartDelaySecondsSet() {
      return this._isSet(171);
   }

   public void setHealthCheckStartDelaySeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("HealthCheckStartDelaySeconds", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(171);
      int _oldVal = this._HealthCheckStartDelaySeconds;
      this._HealthCheckStartDelaySeconds = param0;
      this._postSet(171, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(171)) {
            source._postSetFirePropertyChange(171, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRestartDelaySeconds() {
      return !this._isSet(172) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(172) ? this._getDelegateBean().getRestartDelaySeconds() : this._RestartDelaySeconds;
   }

   public boolean isRestartDelaySecondsInherited() {
      return !this._isSet(172) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(172);
   }

   public boolean isRestartDelaySecondsSet() {
      return this._isSet(172);
   }

   public void setRestartDelaySeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RestartDelaySeconds", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(172);
      int _oldVal = this._RestartDelaySeconds;
      this._RestartDelaySeconds = param0;
      this._postSet(172, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(172)) {
            source._postSetFirePropertyChange(172, wasSet, _oldVal, param0);
         }
      }

   }

   public void setClasspathServletDisabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(173);
      boolean _oldVal = this._ClasspathServletDisabled;
      this._ClasspathServletDisabled = param0;
      this._postSet(173, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(173)) {
            source._postSetFirePropertyChange(173, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isClasspathServletDisabled() {
      return !this._isSet(173) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(173) ? this._getDelegateBean().isClasspathServletDisabled() : this._ClasspathServletDisabled;
   }

   public boolean isClasspathServletDisabledInherited() {
      return !this._isSet(173) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(173);
   }

   public boolean isClasspathServletDisabledSet() {
      return this._isSet(173);
   }

   public void setDefaultInternalServletsDisabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(174);
      boolean _oldVal = this._DefaultInternalServletsDisabled;
      this._DefaultInternalServletsDisabled = param0;
      this._postSet(174, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(174)) {
            source._postSetFirePropertyChange(174, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDefaultInternalServletsDisabled() {
      if (!this._isSet(174) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(174)) {
         return this._getDelegateBean().isDefaultInternalServletsDisabled();
      } else if (!this._isSet(174)) {
         return this._isSecureModeEnabled();
      } else {
         return this._DefaultInternalServletsDisabled;
      }
   }

   public boolean isDefaultInternalServletsDisabledInherited() {
      return !this._isSet(174) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(174);
   }

   public boolean isDefaultInternalServletsDisabledSet() {
      return this._isSet(174);
   }

   public String getServerVersion() {
      return !this._isSet(175) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(175) ? this._performMacroSubstitution(this._getDelegateBean().getServerVersion(), this) : this._ServerVersion;
   }

   public boolean isServerVersionInherited() {
      return !this._isSet(175) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(175);
   }

   public boolean isServerVersionSet() {
      return this._isSet(175);
   }

   public void setServerVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(175);
      String _oldVal = this._ServerVersion;
      this._ServerVersion = param0;
      this._postSet(175, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(175)) {
            source._postSetFirePropertyChange(175, wasSet, _oldVal, param0);
         }
      }

   }

   public void setStartupMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(176);
      String _oldVal = this.getStartupMode();
      this._customizer.setStartupMode(param0);
      this._postSet(176, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(176)) {
            source._postSetFirePropertyChange(176, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStartupMode() {
      return !this._isSet(176) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(176) ? this._performMacroSubstitution(this._getDelegateBean().getStartupMode(), this) : this._customizer.getStartupMode();
   }

   public boolean isStartupModeInherited() {
      return !this._isSet(176) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(176);
   }

   public boolean isStartupModeSet() {
      return this._isSet(176);
   }

   public void setServerLifeCycleTimeoutVal(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("ServerLifeCycleTimeoutVal", param0, 0);
      boolean wasSet = this._isSet(177);
      int _oldVal = this._ServerLifeCycleTimeoutVal;
      this._ServerLifeCycleTimeoutVal = param0;
      this._postSet(177, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(177)) {
            source._postSetFirePropertyChange(177, wasSet, _oldVal, param0);
         }
      }

   }

   public int getServerLifeCycleTimeoutVal() {
      if (!this._isSet(177) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(177)) {
         return this._getDelegateBean().getServerLifeCycleTimeoutVal();
      } else if (!this._isSet(177)) {
         return this._isProductionModeEnabled() ? 120 : 30;
      } else {
         return this._ServerLifeCycleTimeoutVal;
      }
   }

   public boolean isServerLifeCycleTimeoutValInherited() {
      return !this._isSet(177) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(177);
   }

   public boolean isServerLifeCycleTimeoutValSet() {
      return this._isSet(177);
   }

   public void setGracefulShutdownTimeout(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("GracefulShutdownTimeout", param0, 0);
      boolean wasSet = this._isSet(179);
      int _oldVal = this._GracefulShutdownTimeout;
      this._GracefulShutdownTimeout = param0;
      this._postSet(179, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(179)) {
            source._postSetFirePropertyChange(179, wasSet, _oldVal, param0);
         }
      }

   }

   public void setStartupTimeout(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("StartupTimeout", param0, 0);
      boolean wasSet = this._isSet(178);
      int _oldVal = this._StartupTimeout;
      this._StartupTimeout = param0;
      this._postSet(178, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(178)) {
            source._postSetFirePropertyChange(178, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStartupTimeout() {
      if (!this._isSet(178) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(178)) {
         return this._getDelegateBean().getStartupTimeout();
      } else if (!this._isSet(178)) {
         return this._isProductionModeEnabled() ? 0 : 0;
      } else {
         return this._StartupTimeout;
      }
   }

   public boolean isStartupTimeoutInherited() {
      return !this._isSet(178) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(178);
   }

   public boolean isStartupTimeoutSet() {
      return this._isSet(178);
   }

   public int getGracefulShutdownTimeout() {
      return !this._isSet(179) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(179) ? this._getDelegateBean().getGracefulShutdownTimeout() : this._GracefulShutdownTimeout;
   }

   public boolean isGracefulShutdownTimeoutInherited() {
      return !this._isSet(179) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(179);
   }

   public boolean isGracefulShutdownTimeoutSet() {
      return this._isSet(179);
   }

   public boolean isIgnoreSessionsDuringShutdown() {
      return !this._isSet(180) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(180) ? this._getDelegateBean().isIgnoreSessionsDuringShutdown() : this._IgnoreSessionsDuringShutdown;
   }

   public boolean isIgnoreSessionsDuringShutdownInherited() {
      return !this._isSet(180) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(180);
   }

   public boolean isIgnoreSessionsDuringShutdownSet() {
      return this._isSet(180);
   }

   public void setIgnoreSessionsDuringShutdown(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(180);
      boolean _oldVal = this._IgnoreSessionsDuringShutdown;
      this._IgnoreSessionsDuringShutdown = param0;
      this._postSet(180, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(180)) {
            source._postSetFirePropertyChange(180, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isManagedServerIndependenceEnabled() {
      return !this._isSet(181) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(181) ? this._getDelegateBean().isManagedServerIndependenceEnabled() : this._ManagedServerIndependenceEnabled;
   }

   public boolean isManagedServerIndependenceEnabledInherited() {
      return !this._isSet(181) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(181);
   }

   public boolean isManagedServerIndependenceEnabledSet() {
      return this._isSet(181);
   }

   public void setManagedServerIndependenceEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(181);
      boolean _oldVal = this._ManagedServerIndependenceEnabled;
      this._ManagedServerIndependenceEnabled = param0;
      this._postSet(181, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(181)) {
            source._postSetFirePropertyChange(181, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isMSIFileReplicationEnabled() {
      return !this._isSet(182) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(182) ? this._getDelegateBean().isMSIFileReplicationEnabled() : this._MSIFileReplicationEnabled;
   }

   public boolean isMSIFileReplicationEnabledInherited() {
      return !this._isSet(182) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(182);
   }

   public boolean isMSIFileReplicationEnabledSet() {
      return this._isSet(182);
   }

   public void setMSIFileReplicationEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(182);
      boolean _oldVal = this._MSIFileReplicationEnabled;
      this._MSIFileReplicationEnabled = param0;
      this._postSet(182, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(182)) {
            source._postSetFirePropertyChange(182, wasSet, _oldVal, param0);
         }
      }

   }

   public void setClientCertProxyEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(183);
      boolean _oldVal = this._ClientCertProxyEnabled;
      this._ClientCertProxyEnabled = param0;
      this._postSet(183, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(183)) {
            source._postSetFirePropertyChange(183, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isClientCertProxyEnabled() {
      return !this._isSet(183) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(183) ? this._getDelegateBean().isClientCertProxyEnabled() : this._ClientCertProxyEnabled;
   }

   public boolean isClientCertProxyEnabledInherited() {
      return !this._isSet(183) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(183);
   }

   public boolean isClientCertProxyEnabledSet() {
      return this._isSet(183);
   }

   public void setWeblogicPluginEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(184);
      boolean _oldVal = this._WeblogicPluginEnabled;
      this._WeblogicPluginEnabled = param0;
      this._postSet(184, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(184)) {
            source._postSetFirePropertyChange(184, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isWeblogicPluginEnabled() {
      return !this._isSet(184) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(184) ? this._getDelegateBean().isWeblogicPluginEnabled() : this._WeblogicPluginEnabled;
   }

   public boolean isWeblogicPluginEnabledInherited() {
      return !this._isSet(184) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(184);
   }

   public boolean isWeblogicPluginEnabledSet() {
      return this._isSet(184);
   }

   public void setHostsMigratableServices(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(185);
      boolean _oldVal = this._HostsMigratableServices;
      this._HostsMigratableServices = param0;
      this._postSet(185, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(185)) {
            source._postSetFirePropertyChange(185, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getHostsMigratableServices() {
      return !this._isSet(185) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(185) ? this._getDelegateBean().getHostsMigratableServices() : this._HostsMigratableServices;
   }

   public boolean isHostsMigratableServicesInherited() {
      return !this._isSet(185) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(185);
   }

   public boolean isHostsMigratableServicesSet() {
      return this._isSet(185);
   }

   public void setHttpTraceSupportEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(186);
      boolean _oldVal = this._HttpTraceSupportEnabled;
      this._HttpTraceSupportEnabled = param0;
      this._postSet(186, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(186)) {
            source._postSetFirePropertyChange(186, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHttpTraceSupportEnabled() {
      return !this._isSet(186) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(186) ? this._getDelegateBean().isHttpTraceSupportEnabled() : this._HttpTraceSupportEnabled;
   }

   public boolean isHttpTraceSupportEnabledInherited() {
      return !this._isSet(186) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(186);
   }

   public boolean isHttpTraceSupportEnabledSet() {
      return this._isSet(186);
   }

   public String getKeyStores() {
      return !this._isSet(187) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(187) ? this._performMacroSubstitution(this._getDelegateBean().getKeyStores(), this) : this._KeyStores;
   }

   public boolean isKeyStoresInherited() {
      return !this._isSet(187) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(187);
   }

   public boolean isKeyStoresSet() {
      return this._isSet(187);
   }

   public void setKeyStores(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"DemoIdentityAndDemoTrust", "CustomIdentityAndJavaStandardTrust", "CustomIdentityAndCustomTrust", "CustomIdentityAndCommandLineTrust"};
      param0 = LegalChecks.checkInEnum("KeyStores", param0, _set);
      boolean wasSet = this._isSet(187);
      String _oldVal = this._KeyStores;
      this._KeyStores = param0;
      this._postSet(187, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(187)) {
            source._postSetFirePropertyChange(187, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomIdentityKeyStoreFileName() {
      return !this._isSet(188) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(188) ? this._performMacroSubstitution(this._getDelegateBean().getCustomIdentityKeyStoreFileName(), this) : this._CustomIdentityKeyStoreFileName;
   }

   public boolean isCustomIdentityKeyStoreFileNameInherited() {
      return !this._isSet(188) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(188);
   }

   public boolean isCustomIdentityKeyStoreFileNameSet() {
      return this._isSet(188);
   }

   public void setCustomIdentityKeyStoreFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(188);
      String _oldVal = this._CustomIdentityKeyStoreFileName;
      this._CustomIdentityKeyStoreFileName = param0;
      this._postSet(188, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(188)) {
            source._postSetFirePropertyChange(188, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomIdentityKeyStoreType() {
      return !this._isSet(189) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(189) ? this._performMacroSubstitution(this._getDelegateBean().getCustomIdentityKeyStoreType(), this) : this._CustomIdentityKeyStoreType;
   }

   public boolean isCustomIdentityKeyStoreTypeInherited() {
      return !this._isSet(189) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(189);
   }

   public boolean isCustomIdentityKeyStoreTypeSet() {
      return this._isSet(189);
   }

   public void setCustomIdentityKeyStoreType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(189);
      String _oldVal = this._CustomIdentityKeyStoreType;
      this._CustomIdentityKeyStoreType = param0;
      this._postSet(189, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(189)) {
            source._postSetFirePropertyChange(189, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomIdentityKeyStorePassPhrase() {
      if (!this._isSet(190) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(190)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomIdentityKeyStorePassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getCustomIdentityKeyStorePassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("CustomIdentityKeyStorePassPhrase", bEncrypted);
      }
   }

   public boolean isCustomIdentityKeyStorePassPhraseInherited() {
      return !this._isSet(190) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(190);
   }

   public boolean isCustomIdentityKeyStorePassPhraseSet() {
      return this.isCustomIdentityKeyStorePassPhraseEncryptedSet();
   }

   public void setCustomIdentityKeyStorePassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setCustomIdentityKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("CustomIdentityKeyStorePassPhrase", param0));
   }

   public byte[] getCustomIdentityKeyStorePassPhraseEncrypted() {
      return !this._isSet(191) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(191) ? this._getDelegateBean().getCustomIdentityKeyStorePassPhraseEncrypted() : this._getHelper()._cloneArray(this._CustomIdentityKeyStorePassPhraseEncrypted);
   }

   public String getCustomIdentityKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getCustomIdentityKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCustomIdentityKeyStorePassPhraseEncryptedInherited() {
      return !this._isSet(191) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(191);
   }

   public boolean isCustomIdentityKeyStorePassPhraseEncryptedSet() {
      return this._isSet(191);
   }

   public void setCustomIdentityKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCustomIdentityKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getCustomTrustKeyStoreFileName() {
      return !this._isSet(192) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(192) ? this._performMacroSubstitution(this._getDelegateBean().getCustomTrustKeyStoreFileName(), this) : this._CustomTrustKeyStoreFileName;
   }

   public boolean isCustomTrustKeyStoreFileNameInherited() {
      return !this._isSet(192) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(192);
   }

   public boolean isCustomTrustKeyStoreFileNameSet() {
      return this._isSet(192);
   }

   public void setCustomTrustKeyStoreFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(192);
      String _oldVal = this._CustomTrustKeyStoreFileName;
      this._CustomTrustKeyStoreFileName = param0;
      this._postSet(192, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(192)) {
            source._postSetFirePropertyChange(192, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomTrustKeyStoreType() {
      return !this._isSet(193) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(193) ? this._performMacroSubstitution(this._getDelegateBean().getCustomTrustKeyStoreType(), this) : this._CustomTrustKeyStoreType;
   }

   public boolean isCustomTrustKeyStoreTypeInherited() {
      return !this._isSet(193) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(193);
   }

   public boolean isCustomTrustKeyStoreTypeSet() {
      return this._isSet(193);
   }

   public void setCustomTrustKeyStoreType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(193);
      String _oldVal = this._CustomTrustKeyStoreType;
      this._CustomTrustKeyStoreType = param0;
      this._postSet(193, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(193)) {
            source._postSetFirePropertyChange(193, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomTrustKeyStorePassPhrase() {
      if (!this._isSet(194) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(194)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomTrustKeyStorePassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getCustomTrustKeyStorePassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("CustomTrustKeyStorePassPhrase", bEncrypted);
      }
   }

   public boolean isCustomTrustKeyStorePassPhraseInherited() {
      return !this._isSet(194) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(194);
   }

   public boolean isCustomTrustKeyStorePassPhraseSet() {
      return this.isCustomTrustKeyStorePassPhraseEncryptedSet();
   }

   public void setCustomTrustKeyStorePassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setCustomTrustKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("CustomTrustKeyStorePassPhrase", param0));
   }

   public byte[] getCustomTrustKeyStorePassPhraseEncrypted() {
      return !this._isSet(195) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(195) ? this._getDelegateBean().getCustomTrustKeyStorePassPhraseEncrypted() : this._getHelper()._cloneArray(this._CustomTrustKeyStorePassPhraseEncrypted);
   }

   public String getCustomTrustKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getCustomTrustKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCustomTrustKeyStorePassPhraseEncryptedInherited() {
      return !this._isSet(195) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(195);
   }

   public boolean isCustomTrustKeyStorePassPhraseEncryptedSet() {
      return this._isSet(195);
   }

   public void setCustomTrustKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCustomTrustKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getJavaStandardTrustKeyStorePassPhrase() {
      if (!this._isSet(196) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(196)) {
         return this._performMacroSubstitution(this._getDelegateBean().getJavaStandardTrustKeyStorePassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getJavaStandardTrustKeyStorePassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("JavaStandardTrustKeyStorePassPhrase", bEncrypted);
      }
   }

   public boolean isJavaStandardTrustKeyStorePassPhraseInherited() {
      return !this._isSet(196) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(196);
   }

   public boolean isJavaStandardTrustKeyStorePassPhraseSet() {
      return this.isJavaStandardTrustKeyStorePassPhraseEncryptedSet();
   }

   public void setJavaStandardTrustKeyStorePassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setJavaStandardTrustKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("JavaStandardTrustKeyStorePassPhrase", param0));
   }

   public byte[] getJavaStandardTrustKeyStorePassPhraseEncrypted() {
      return !this._isSet(197) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(197) ? this._getDelegateBean().getJavaStandardTrustKeyStorePassPhraseEncrypted() : this._getHelper()._cloneArray(this._JavaStandardTrustKeyStorePassPhraseEncrypted);
   }

   public String getJavaStandardTrustKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getJavaStandardTrustKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isJavaStandardTrustKeyStorePassPhraseEncryptedInherited() {
      return !this._isSet(197) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(197);
   }

   public boolean isJavaStandardTrustKeyStorePassPhraseEncryptedSet() {
      return this._isSet(197);
   }

   public void setJavaStandardTrustKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setJavaStandardTrustKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setReliableDeliveryPolicy(WSReliableDeliveryPolicyMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 198, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerTemplateMBeanImpl.this.getReliableDeliveryPolicy();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(198);
      WSReliableDeliveryPolicyMBean _oldVal = this._ReliableDeliveryPolicy;
      this._ReliableDeliveryPolicy = param0;
      this._postSet(198, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(198)) {
            source._postSetFirePropertyChange(198, wasSet, _oldVal, param0);
         }
      }

   }

   public WSReliableDeliveryPolicyMBean getReliableDeliveryPolicy() {
      return !this._isSet(198) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(198) ? this._getDelegateBean().getReliableDeliveryPolicy() : this._ReliableDeliveryPolicy;
   }

   public String getReliableDeliveryPolicyAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getReliableDeliveryPolicy();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isReliableDeliveryPolicyInherited() {
      return !this._isSet(198) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(198);
   }

   public boolean isReliableDeliveryPolicySet() {
      return this._isSet(198);
   }

   public void setReliableDeliveryPolicyAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, WSReliableDeliveryPolicyMBean.class, new ReferenceManager.Resolver(this, 198) {
            public void resolveReference(Object value) {
               try {
                  ServerTemplateMBeanImpl.this.setReliableDeliveryPolicy((WSReliableDeliveryPolicyMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         WSReliableDeliveryPolicyMBean _oldVal = this._ReliableDeliveryPolicy;
         this._initializeProperty(198);
         this._postSet(198, _oldVal, this._ReliableDeliveryPolicy);
      }

   }

   public boolean isMessageIdPrefixEnabled() {
      return !this._isSet(199) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(199) ? this._getDelegateBean().isMessageIdPrefixEnabled() : this._MessageIdPrefixEnabled;
   }

   public boolean isMessageIdPrefixEnabledInherited() {
      return !this._isSet(199) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(199);
   }

   public boolean isMessageIdPrefixEnabledSet() {
      return this._isSet(199);
   }

   public void setMessageIdPrefixEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(199);
      boolean _oldVal = this.isMessageIdPrefixEnabled();
      this._customizer.setMessageIdPrefixEnabled(param0);
      this._postSet(199, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(199)) {
            source._postSetFirePropertyChange(199, wasSet, _oldVal, param0);
         }
      }

   }

   public DefaultFileStoreMBean getDefaultFileStore() {
      return this._DefaultFileStore;
   }

   public boolean isDefaultFileStoreInherited() {
      return false;
   }

   public boolean isDefaultFileStoreSet() {
      return this._isSet(200) || this._isAnythingSet((AbstractDescriptorBean)this.getDefaultFileStore());
   }

   public void setDefaultFileStore(DefaultFileStoreMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 200)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(200);
      DefaultFileStoreMBean _oldVal = this._DefaultFileStore;
      this._DefaultFileStore = param0;
      this._postSet(200, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(200)) {
            source._postSetFirePropertyChange(200, wasSet, _oldVal, param0);
         }
      }

   }

   public void addCandidateMachine(MachineMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 201)) {
         MachineMBean[] _new;
         if (this._isSet(201)) {
            _new = (MachineMBean[])((MachineMBean[])this._getHelper()._extendArray(this.getCandidateMachines(), MachineMBean.class, param0));
         } else {
            _new = new MachineMBean[]{param0};
         }

         try {
            this.setCandidateMachines(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MachineMBean[] getCandidateMachines() {
      return !this._isSet(201) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(201) ? this._getDelegateBean().getCandidateMachines() : this._CandidateMachines;
   }

   public String getCandidateMachinesAsString() {
      return this._getHelper()._serializeKeyList(this.getCandidateMachines());
   }

   public boolean isCandidateMachinesInherited() {
      return !this._isSet(201) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(201);
   }

   public boolean isCandidateMachinesSet() {
      return this._isSet(201);
   }

   public void removeCandidateMachine(MachineMBean param0) {
      MachineMBean[] _old = this.getCandidateMachines();
      MachineMBean[] _new = (MachineMBean[])((MachineMBean[])this._getHelper()._removeElement(_old, MachineMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setCandidateMachines(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setCandidateMachinesAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._CandidateMachines);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, MachineMBean.class, new ReferenceManager.Resolver(this, 201, param0) {
                  public void resolveReference(Object value) {
                     try {
                        ServerTemplateMBeanImpl.this.addCandidateMachine((MachineMBean)value);
                        ServerTemplateMBeanImpl.this._getHelper().reorderArrayObjects((Object[])ServerTemplateMBeanImpl.this._CandidateMachines, this.getHandbackObject());
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
               MachineMBean[] var6 = this._CandidateMachines;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  MachineMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeCandidateMachine(member);
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
         MachineMBean[] _oldVal = this._CandidateMachines;
         this._initializeProperty(201);
         this._postSet(201, _oldVal, this._CandidateMachines);
      }
   }

   public void setCandidateMachines(MachineMBean[] param0) {
      MachineMBean[] param0 = param0 == null ? new MachineMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (MachineMBean[])((MachineMBean[])this._getHelper()._cleanAndValidateArray(param0, MachineMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 201, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return ServerTemplateMBeanImpl.this.getCandidateMachines();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(201);
      MachineMBean[] _oldVal = this._CandidateMachines;
      this._CandidateMachines = param0;
      this._postSet(201, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(201)) {
            source._postSetFirePropertyChange(201, wasSet, _oldVal, param0);
         }
      }

   }

   public OverloadProtectionMBean getOverloadProtection() {
      return this._OverloadProtection;
   }

   public boolean isOverloadProtectionInherited() {
      return false;
   }

   public boolean isOverloadProtectionSet() {
      return this._isSet(202) || this._isAnythingSet((AbstractDescriptorBean)this.getOverloadProtection());
   }

   public void setOverloadProtection(OverloadProtectionMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 202)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(202);
      OverloadProtectionMBean _oldVal = this._OverloadProtection;
      this._OverloadProtection = param0;
      this._postSet(202, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(202)) {
            source._postSetFirePropertyChange(202, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJDBCLLRTableName() {
      return !this._isSet(203) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(203) ? this._performMacroSubstitution(this._getDelegateBean().getJDBCLLRTableName(), this) : this._JDBCLLRTableName;
   }

   public boolean isJDBCLLRTableNameInherited() {
      return !this._isSet(203) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(203);
   }

   public boolean isJDBCLLRTableNameSet() {
      return this._isSet(203);
   }

   public void setJDBCLLRTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(203);
      String _oldVal = this._JDBCLLRTableName;
      this._JDBCLLRTableName = param0;
      this._postSet(203, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(203)) {
            source._postSetFirePropertyChange(203, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseFusionForLLR() {
      return !this._isSet(204) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(204) ? this._getDelegateBean().isUseFusionForLLR() : this._UseFusionForLLR;
   }

   public boolean isUseFusionForLLRInherited() {
      return !this._isSet(204) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(204);
   }

   public boolean isUseFusionForLLRSet() {
      return this._isSet(204);
   }

   public void setUseFusionForLLR(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(204);
      boolean _oldVal = this._UseFusionForLLR;
      this._UseFusionForLLR = param0;
      this._postSet(204, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(204)) {
            source._postSetFirePropertyChange(204, wasSet, _oldVal, param0);
         }
      }

   }

   public int getJDBCLLRTableXIDColumnSize() {
      return !this._isSet(205) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(205) ? this._getDelegateBean().getJDBCLLRTableXIDColumnSize() : this._JDBCLLRTableXIDColumnSize;
   }

   public boolean isJDBCLLRTableXIDColumnSizeInherited() {
      return !this._isSet(205) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(205);
   }

   public boolean isJDBCLLRTableXIDColumnSizeSet() {
      return this._isSet(205);
   }

   public void setJDBCLLRTableXIDColumnSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(205);
      int _oldVal = this._JDBCLLRTableXIDColumnSize;
      this._JDBCLLRTableXIDColumnSize = param0;
      this._postSet(205, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(205)) {
            source._postSetFirePropertyChange(205, wasSet, _oldVal, param0);
         }
      }

   }

   public int getJDBCLLRTablePoolColumnSize() {
      return !this._isSet(206) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(206) ? this._getDelegateBean().getJDBCLLRTablePoolColumnSize() : this._JDBCLLRTablePoolColumnSize;
   }

   public boolean isJDBCLLRTablePoolColumnSizeInherited() {
      return !this._isSet(206) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(206);
   }

   public boolean isJDBCLLRTablePoolColumnSizeSet() {
      return this._isSet(206);
   }

   public void setJDBCLLRTablePoolColumnSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(206);
      int _oldVal = this._JDBCLLRTablePoolColumnSize;
      this._JDBCLLRTablePoolColumnSize = param0;
      this._postSet(206, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(206)) {
            source._postSetFirePropertyChange(206, wasSet, _oldVal, param0);
         }
      }

   }

   public int getJDBCLLRTableRecordColumnSize() {
      return !this._isSet(207) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(207) ? this._getDelegateBean().getJDBCLLRTableRecordColumnSize() : this._JDBCLLRTableRecordColumnSize;
   }

   public boolean isJDBCLLRTableRecordColumnSizeInherited() {
      return !this._isSet(207) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(207);
   }

   public boolean isJDBCLLRTableRecordColumnSizeSet() {
      return this._isSet(207);
   }

   public void setJDBCLLRTableRecordColumnSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(207);
      int _oldVal = this._JDBCLLRTableRecordColumnSize;
      this._JDBCLLRTableRecordColumnSize = param0;
      this._postSet(207, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(207)) {
            source._postSetFirePropertyChange(207, wasSet, _oldVal, param0);
         }
      }

   }

   public int getJDBCLoginTimeoutSeconds() {
      return !this._isSet(208) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(208) ? this._getDelegateBean().getJDBCLoginTimeoutSeconds() : this._JDBCLoginTimeoutSeconds;
   }

   public boolean isJDBCLoginTimeoutSecondsInherited() {
      return !this._isSet(208) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(208);
   }

   public boolean isJDBCLoginTimeoutSecondsSet() {
      return this._isSet(208);
   }

   public void setJDBCLoginTimeoutSeconds(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("JDBCLoginTimeoutSeconds", (long)param0, 0L, 300L);
      boolean wasSet = this._isSet(208);
      int _oldVal = this._JDBCLoginTimeoutSeconds;
      this._JDBCLoginTimeoutSeconds = param0;
      this._postSet(208, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(208)) {
            source._postSetFirePropertyChange(208, wasSet, _oldVal, param0);
         }
      }

   }

   public WLDFServerDiagnosticMBean getServerDiagnosticConfig() {
      return this._ServerDiagnosticConfig;
   }

   public boolean isServerDiagnosticConfigInherited() {
      return false;
   }

   public boolean isServerDiagnosticConfigSet() {
      return this._isSet(209) || this._isAnythingSet((AbstractDescriptorBean)this.getServerDiagnosticConfig());
   }

   public void setServerDiagnosticConfig(WLDFServerDiagnosticMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 209)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(209);
      WLDFServerDiagnosticMBean _oldVal = this._ServerDiagnosticConfig;
      this._ServerDiagnosticConfig = param0;
      this._postSet(209, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(209)) {
            source._postSetFirePropertyChange(209, wasSet, _oldVal, param0);
         }
      }

   }

   public void setAutoJDBCConnectionClose(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(210);
      String _oldVal = this._AutoJDBCConnectionClose;
      this._AutoJDBCConnectionClose = param0;
      this._postSet(210, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(210)) {
            source._postSetFirePropertyChange(210, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAutoJDBCConnectionClose() {
      return !this._isSet(210) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(210) ? this._performMacroSubstitution(this._getDelegateBean().getAutoJDBCConnectionClose(), this) : this._AutoJDBCConnectionClose;
   }

   public boolean isAutoJDBCConnectionCloseInherited() {
      return !this._isSet(210) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(210);
   }

   public boolean isAutoJDBCConnectionCloseSet() {
      return this._isSet(210);
   }

   public String[] getSupportedProtocols() {
      return !this._isSet(211) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(211) ? this._getDelegateBean().getSupportedProtocols() : this._customizer.getSupportedProtocols();
   }

   public boolean isSupportedProtocolsInherited() {
      return !this._isSet(211) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(211);
   }

   public boolean isSupportedProtocolsSet() {
      return this._isSet(211);
   }

   public void setSupportedProtocols(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(211);
      String[] _oldVal = this._SupportedProtocols;
      this._SupportedProtocols = param0;
      this._postSet(211, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(211)) {
            source._postSetFirePropertyChange(211, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultStagingDirName() {
      return this._customizer.getDefaultStagingDirName();
   }

   public boolean isDefaultStagingDirNameInherited() {
      return false;
   }

   public boolean isDefaultStagingDirNameSet() {
      return this._isSet(212);
   }

   public void setDefaultStagingDirName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DefaultStagingDirName = param0;
   }

   public String get81StyleDefaultStagingDirName() {
      return this._customizer.get81StyleDefaultStagingDirName();
   }

   public boolean is81StyleDefaultStagingDirNameInherited() {
      return false;
   }

   public boolean is81StyleDefaultStagingDirNameSet() {
      return this._isSet(213);
   }

   public void set81StyleDefaultStagingDirName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._81StyleDefaultStagingDirName = param0;
   }

   public FederationServicesMBean getFederationServices() {
      return this._FederationServices;
   }

   public boolean isFederationServicesInherited() {
      return false;
   }

   public boolean isFederationServicesSet() {
      return this._isSet(214) || this._isAnythingSet((AbstractDescriptorBean)this.getFederationServices());
   }

   public void setFederationServices(FederationServicesMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 214)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(214);
      FederationServicesMBean _oldVal = this._FederationServices;
      this._FederationServices = param0;
      this._postSet(214, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(214)) {
            source._postSetFirePropertyChange(214, wasSet, _oldVal, param0);
         }
      }

   }

   public SingleSignOnServicesMBean getSingleSignOnServices() {
      return this._SingleSignOnServices;
   }

   public boolean isSingleSignOnServicesInherited() {
      return false;
   }

   public boolean isSingleSignOnServicesSet() {
      return this._isSet(215) || this._isAnythingSet((AbstractDescriptorBean)this.getSingleSignOnServices());
   }

   public void setSingleSignOnServices(SingleSignOnServicesMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 215)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(215);
      SingleSignOnServicesMBean _oldVal = this._SingleSignOnServices;
      this._SingleSignOnServices = param0;
      this._postSet(215, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(215)) {
            source._postSetFirePropertyChange(215, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceMBean getWebService() {
      return this._WebService;
   }

   public boolean isWebServiceInherited() {
      return false;
   }

   public boolean isWebServiceSet() {
      return this._isSet(216) || this._isAnythingSet((AbstractDescriptorBean)this.getWebService());
   }

   public void setWebService(WebServiceMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 216)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(216);
      WebServiceMBean _oldVal = this._WebService;
      this._WebService = param0;
      this._postSet(216, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(216)) {
            source._postSetFirePropertyChange(216, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNMSocketCreateTimeoutInMillis() {
      return !this._isSet(217) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(217) ? this._getDelegateBean().getNMSocketCreateTimeoutInMillis() : this._NMSocketCreateTimeoutInMillis;
   }

   public boolean isNMSocketCreateTimeoutInMillisInherited() {
      return !this._isSet(217) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(217);
   }

   public boolean isNMSocketCreateTimeoutInMillisSet() {
      return this._isSet(217);
   }

   public void setNMSocketCreateTimeoutInMillis(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("NMSocketCreateTimeoutInMillis", param0, 0);
      boolean wasSet = this._isSet(217);
      int _oldVal = this._NMSocketCreateTimeoutInMillis;
      this._NMSocketCreateTimeoutInMillis = param0;
      this._postSet(217, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(217)) {
            source._postSetFirePropertyChange(217, wasSet, _oldVal, param0);
         }
      }

   }

   public void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 218, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerTemplateMBeanImpl.this.getCoherenceClusterSystemResource();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(218);
      CoherenceClusterSystemResourceMBean _oldVal = this.getCoherenceClusterSystemResource();
      this._customizer.setCoherenceClusterSystemResource(param0);
      this._postSet(218, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(218)) {
            source._postSetFirePropertyChange(218, wasSet, _oldVal, param0);
         }
      }

   }

   public CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource() {
      return !this._isSet(218) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(218) ? this._getDelegateBean().getCoherenceClusterSystemResource() : this._customizer.getCoherenceClusterSystemResource();
   }

   public String getCoherenceClusterSystemResourceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCoherenceClusterSystemResource();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isCoherenceClusterSystemResourceInherited() {
      return !this._isSet(218) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(218);
   }

   public boolean isCoherenceClusterSystemResourceSet() {
      return this._isSet(218);
   }

   public void setCoherenceClusterSystemResourceAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, CoherenceClusterSystemResourceMBean.class, new ReferenceManager.Resolver(this, 218) {
            public void resolveReference(Object value) {
               try {
                  ServerTemplateMBeanImpl.this.setCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         CoherenceClusterSystemResourceMBean _oldVal = this._CoherenceClusterSystemResource;
         this._initializeProperty(218);
         this._postSet(218, _oldVal, this._CoherenceClusterSystemResource);
      }

   }

   public void setVirtualMachineName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(219);
      String _oldVal = this._VirtualMachineName;
      this._VirtualMachineName = param0;
      this._postSet(219, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(219)) {
            source._postSetFirePropertyChange(219, wasSet, _oldVal, param0);
         }
      }

   }

   public String getVirtualMachineName() {
      if (!this._isSet(219) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(219)) {
         return this._performMacroSubstitution(this._getDelegateBean().getVirtualMachineName(), this);
      } else {
         if (!this._isSet(219)) {
            try {
               return ((DomainMBean)this.getParent()).getName() + "_" + this.getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._VirtualMachineName;
      }
   }

   public boolean isVirtualMachineNameInherited() {
      return !this._isSet(219) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(219);
   }

   public boolean isVirtualMachineNameSet() {
      return this._isSet(219);
   }

   public String getReplicationPorts() {
      return !this._isSet(220) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(220) ? this._performMacroSubstitution(this._getDelegateBean().getReplicationPorts(), this) : this._ReplicationPorts;
   }

   public boolean isReplicationPortsInherited() {
      return !this._isSet(220) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(220);
   }

   public boolean isReplicationPortsSet() {
      return this._isSet(220);
   }

   public void setReplicationPorts(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(220);
      String _oldVal = this._ReplicationPorts;
      this._ReplicationPorts = param0;
      this._postSet(220, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(220)) {
            source._postSetFirePropertyChange(220, wasSet, _oldVal, param0);
         }
      }

   }

   public TransactionLogJDBCStoreMBean getTransactionLogJDBCStore() {
      return this._TransactionLogJDBCStore;
   }

   public boolean isTransactionLogJDBCStoreInherited() {
      return false;
   }

   public boolean isTransactionLogJDBCStoreSet() {
      return this._isSet(221) || this._isAnythingSet((AbstractDescriptorBean)this.getTransactionLogJDBCStore());
   }

   public void setTransactionLogJDBCStore(TransactionLogJDBCStoreMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 221)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(221);
      TransactionLogJDBCStoreMBean _oldVal = this._TransactionLogJDBCStore;
      this._TransactionLogJDBCStore = param0;
      this._postSet(221, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(221)) {
            source._postSetFirePropertyChange(221, wasSet, _oldVal, param0);
         }
      }

   }

   public DataSourceMBean getDataSource() {
      return this._DataSource;
   }

   public boolean isDataSourceInherited() {
      return false;
   }

   public boolean isDataSourceSet() {
      return this._isSet(222) || this._isAnythingSet((AbstractDescriptorBean)this.getDataSource());
   }

   public void setDataSource(DataSourceMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 222)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(222);
      DataSourceMBean _oldVal = this._DataSource;
      this._DataSource = param0;
      this._postSet(222, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(222)) {
            source._postSetFirePropertyChange(222, wasSet, _oldVal, param0);
         }
      }

   }

   public CoherenceMemberConfigMBean getCoherenceMemberConfig() {
      return this._CoherenceMemberConfig;
   }

   public boolean isCoherenceMemberConfigInherited() {
      return false;
   }

   public boolean isCoherenceMemberConfigSet() {
      return this._isSet(223) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceMemberConfig());
   }

   public void setCoherenceMemberConfig(CoherenceMemberConfigMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 223)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(223);
      CoherenceMemberConfigMBean _oldVal = this._CoherenceMemberConfig;
      this._CoherenceMemberConfig = param0;
      this._postSet(223, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(223)) {
            source._postSetFirePropertyChange(223, wasSet, _oldVal, param0);
         }
      }

   }

   public int getBuzzPort() {
      return !this._isSet(224) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(224) ? this._getDelegateBean().getBuzzPort() : this._BuzzPort;
   }

   public boolean isBuzzPortInherited() {
      return !this._isSet(224) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(224);
   }

   public boolean isBuzzPortSet() {
      return this._isSet(224);
   }

   public void setBuzzPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(224);
      int _oldVal = this._BuzzPort;
      this._BuzzPort = param0;
      this._postSet(224, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(224)) {
            source._postSetFirePropertyChange(224, wasSet, _oldVal, param0);
         }
      }

   }

   public String getBuzzAddress() {
      return !this._isSet(225) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(225) ? this._performMacroSubstitution(this._getDelegateBean().getBuzzAddress(), this) : this._BuzzAddress;
   }

   public boolean isBuzzAddressInherited() {
      return !this._isSet(225) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(225);
   }

   public boolean isBuzzAddressSet() {
      return this._isSet(225);
   }

   public void setBuzzAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(225);
      String _oldVal = this._BuzzAddress;
      this._BuzzAddress = param0;
      this._postSet(225, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(225)) {
            source._postSetFirePropertyChange(225, wasSet, _oldVal, param0);
         }
      }

   }

   public void setBuzzEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(226);
      boolean _oldVal = this._BuzzEnabled;
      this._BuzzEnabled = param0;
      this._postSet(226, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(226)) {
            source._postSetFirePropertyChange(226, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isBuzzEnabled() {
      return !this._isSet(226) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(226) ? this._getDelegateBean().isBuzzEnabled() : this._BuzzEnabled;
   }

   public boolean isBuzzEnabledInherited() {
      return !this._isSet(226) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(226);
   }

   public boolean isBuzzEnabledSet() {
      return this._isSet(226);
   }

   public int getMaxConcurrentNewThreads() {
      return !this._isSet(227) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(227) ? this._getDelegateBean().getMaxConcurrentNewThreads() : this._MaxConcurrentNewThreads;
   }

   public boolean isMaxConcurrentNewThreadsInherited() {
      return !this._isSet(227) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(227);
   }

   public boolean isMaxConcurrentNewThreadsSet() {
      return this._isSet(227);
   }

   public void setMaxConcurrentNewThreads(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxConcurrentNewThreads", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(227);
      int _oldVal = this._MaxConcurrentNewThreads;
      this._MaxConcurrentNewThreads = param0;
      this._postSet(227, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(227)) {
            source._postSetFirePropertyChange(227, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxConcurrentLongRunningRequests() {
      return !this._isSet(228) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(228) ? this._getDelegateBean().getMaxConcurrentLongRunningRequests() : this._MaxConcurrentLongRunningRequests;
   }

   public boolean isMaxConcurrentLongRunningRequestsInherited() {
      return !this._isSet(228) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(228);
   }

   public boolean isMaxConcurrentLongRunningRequestsSet() {
      return this._isSet(228);
   }

   public void setMaxConcurrentLongRunningRequests(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxConcurrentLongRunningRequests", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(228);
      int _oldVal = this._MaxConcurrentLongRunningRequests;
      this._MaxConcurrentLongRunningRequests = param0;
      this._postSet(228, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(228)) {
            source._postSetFirePropertyChange(228, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNumOfRetriesBeforeMSIMode() {
      return !this._isSet(229) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(229) ? this._getDelegateBean().getNumOfRetriesBeforeMSIMode() : this._NumOfRetriesBeforeMSIMode;
   }

   public boolean isNumOfRetriesBeforeMSIModeInherited() {
      return !this._isSet(229) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(229);
   }

   public boolean isNumOfRetriesBeforeMSIModeSet() {
      return this._isSet(229);
   }

   public void setNumOfRetriesBeforeMSIMode(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("NumOfRetriesBeforeMSIMode", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(229);
      int _oldVal = this._NumOfRetriesBeforeMSIMode;
      this._NumOfRetriesBeforeMSIMode = param0;
      this._postSet(229, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(229)) {
            source._postSetFirePropertyChange(229, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRetryIntervalBeforeMSIMode() {
      return !this._isSet(230) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(230) ? this._getDelegateBean().getRetryIntervalBeforeMSIMode() : this._RetryIntervalBeforeMSIMode;
   }

   public boolean isRetryIntervalBeforeMSIModeInherited() {
      return !this._isSet(230) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(230);
   }

   public boolean isRetryIntervalBeforeMSIModeSet() {
      return this._isSet(230);
   }

   public void setRetryIntervalBeforeMSIMode(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RetryIntervalBeforeMSIMode", (long)param0, 1L, 65534L);
      boolean wasSet = this._isSet(230);
      int _oldVal = this._RetryIntervalBeforeMSIMode;
      this._RetryIntervalBeforeMSIMode = param0;
      this._postSet(230, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(230)) {
            source._postSetFirePropertyChange(230, wasSet, _oldVal, param0);
         }
      }

   }

   public WebLogicMBean getParent() {
      return !this._isSet(231) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(231) ? this._getDelegateBean().getParent() : this._customizer.getParent();
   }

   public boolean isParentInherited() {
      return !this._isSet(231) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(231);
   }

   public boolean isParentSet() {
      return this._isSet(231);
   }

   public void setParent(WebLogicMBean param0) throws ConfigurationException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      this._customizer.setParent(param0);
   }

   public void setClasspathServletSecureModeEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(232);
      boolean _oldVal = this._ClasspathServletSecureModeEnabled;
      this._ClasspathServletSecureModeEnabled = param0;
      this._postSet(232, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(232)) {
            source._postSetFirePropertyChange(232, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isClasspathServletSecureModeEnabled() {
      if (!this._isSet(232) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(232)) {
         return this._getDelegateBean().isClasspathServletSecureModeEnabled();
      } else if (!this._isSet(232)) {
         return this._isSecureModeEnabled();
      } else {
         return this._ClasspathServletSecureModeEnabled;
      }
   }

   public boolean isClasspathServletSecureModeEnabledInherited() {
      return !this._isSet(232) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(232);
   }

   public boolean isClasspathServletSecureModeEnabledSet() {
      return this._isSet(232);
   }

   public void setSitConfigPollingInterval(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SitConfigPollingInterval", (long)param0, 1L, 65534L);
      boolean wasSet = this._isSet(233);
      int _oldVal = this._SitConfigPollingInterval;
      this._SitConfigPollingInterval = param0;
      this._postSet(233, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(233)) {
            source._postSetFirePropertyChange(233, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSitConfigPollingInterval() {
      return !this._isSet(233) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(233) ? this._getDelegateBean().getSitConfigPollingInterval() : this._SitConfigPollingInterval;
   }

   public boolean isSitConfigPollingIntervalInherited() {
      return !this._isSet(233) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(233);
   }

   public boolean isSitConfigPollingIntervalSet() {
      return this._isSet(233);
   }

   public void setSessionReplicationOnShutdownEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(234);
      boolean _oldVal = this._SessionReplicationOnShutdownEnabled;
      this._SessionReplicationOnShutdownEnabled = param0;
      this._postSet(234, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(234)) {
            source._postSetFirePropertyChange(234, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSessionReplicationOnShutdownEnabled() {
      return !this._isSet(234) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(234) ? this._getDelegateBean().isSessionReplicationOnShutdownEnabled() : this._SessionReplicationOnShutdownEnabled;
   }

   public boolean isSessionReplicationOnShutdownEnabledInherited() {
      return !this._isSet(234) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(234);
   }

   public boolean isSessionReplicationOnShutdownEnabledSet() {
      return this._isSet(234);
   }

   public void setCleanupOrphanedSessionsEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(235);
      boolean _oldVal = this._CleanupOrphanedSessionsEnabled;
      this._CleanupOrphanedSessionsEnabled = param0;
      this._postSet(235, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(235)) {
            source._postSetFirePropertyChange(235, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isCleanupOrphanedSessionsEnabled() {
      return !this._isSet(235) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(235) ? this._getDelegateBean().isCleanupOrphanedSessionsEnabled() : this._CleanupOrphanedSessionsEnabled;
   }

   public boolean isCleanupOrphanedSessionsEnabledInherited() {
      return !this._isSet(235) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(235);
   }

   public boolean isCleanupOrphanedSessionsEnabledSet() {
      return this._isSet(235);
   }

   public String getTransactionPrimaryChannelName() {
      return !this._isSet(236) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(236) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionPrimaryChannelName(), this) : this._TransactionPrimaryChannelName;
   }

   public boolean isTransactionPrimaryChannelNameInherited() {
      return !this._isSet(236) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(236);
   }

   public boolean isTransactionPrimaryChannelNameSet() {
      return this._isSet(236);
   }

   public void setTransactionPrimaryChannelName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(236);
      String _oldVal = this._TransactionPrimaryChannelName;
      this._TransactionPrimaryChannelName = param0;
      this._postSet(236, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(236)) {
            source._postSetFirePropertyChange(236, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransactionSecureChannelName() {
      return !this._isSet(237) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(237) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionSecureChannelName(), this) : this._TransactionSecureChannelName;
   }

   public boolean isTransactionSecureChannelNameInherited() {
      return !this._isSet(237) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(237);
   }

   public boolean isTransactionSecureChannelNameSet() {
      return this._isSet(237);
   }

   public void setTransactionSecureChannelName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(237);
      String _oldVal = this._TransactionSecureChannelName;
      this._TransactionSecureChannelName = param0;
      this._postSet(237, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(237)) {
            source._postSetFirePropertyChange(237, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransactionPublicChannelName() {
      return !this._isSet(238) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(238) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionPublicChannelName(), this) : this._TransactionPublicChannelName;
   }

   public boolean isTransactionPublicChannelNameInherited() {
      return !this._isSet(238) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(238);
   }

   public boolean isTransactionPublicChannelNameSet() {
      return this._isSet(238);
   }

   public void setTransactionPublicChannelName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(238);
      String _oldVal = this._TransactionPublicChannelName;
      this._TransactionPublicChannelName = param0;
      this._postSet(238, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(238)) {
            source._postSetFirePropertyChange(238, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransactionPublicSecureChannelName() {
      return !this._isSet(239) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(239) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionPublicSecureChannelName(), this) : this._TransactionPublicSecureChannelName;
   }

   public boolean isTransactionPublicSecureChannelNameInherited() {
      return !this._isSet(239) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(239);
   }

   public boolean isTransactionPublicSecureChannelNameSet() {
      return this._isSet(239);
   }

   public void setTransactionPublicSecureChannelName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(239);
      String _oldVal = this._TransactionPublicSecureChannelName;
      this._TransactionPublicSecureChannelName = param0;
      this._postSet(239, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(239)) {
            source._postSetFirePropertyChange(239, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSitConfigRequired(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(240);
      boolean _oldVal = this._SitConfigRequired;
      this._SitConfigRequired = param0;
      this._postSet(240, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(240)) {
            source._postSetFirePropertyChange(240, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSitConfigRequired() {
      return !this._isSet(240) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(240) ? this._getDelegateBean().isSitConfigRequired() : this._SitConfigRequired;
   }

   public boolean isSitConfigRequiredInherited() {
      return !this._isSet(240) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(240);
   }

   public boolean isSitConfigRequiredSet() {
      return this._isSet(240);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ServerLegalHelper.validateServer(this);
      ClusterValidator.validateUnicastCluster(this, this.getCluster());
      LegalHelper.validateListenPorts(this);
   }

   public void setCustomIdentityKeyStorePassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(191);
      byte[] _oldVal = this._CustomIdentityKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CustomIdentityKeyStorePassPhraseEncrypted of ServerTemplateMBean");
      } else {
         this._getHelper()._clearArray(this._CustomIdentityKeyStorePassPhraseEncrypted);
         this._CustomIdentityKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(191, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(191)) {
               source._postSetFirePropertyChange(191, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setCustomTrustKeyStorePassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(195);
      byte[] _oldVal = this._CustomTrustKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CustomTrustKeyStorePassPhraseEncrypted of ServerTemplateMBean");
      } else {
         this._getHelper()._clearArray(this._CustomTrustKeyStorePassPhraseEncrypted);
         this._CustomTrustKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(195, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(195)) {
               source._postSetFirePropertyChange(195, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setDefaultIIOPPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(110);
      byte[] _oldVal = this._DefaultIIOPPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: DefaultIIOPPasswordEncrypted of ServerTemplateMBean");
      } else {
         this._getHelper()._clearArray(this._DefaultIIOPPasswordEncrypted);
         this._DefaultIIOPPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(110, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(110)) {
               source._postSetFirePropertyChange(110, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setDefaultTGIOPPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(114);
      byte[] _oldVal = this._DefaultTGIOPPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: DefaultTGIOPPasswordEncrypted of ServerTemplateMBean");
      } else {
         this._getHelper()._clearArray(this._DefaultTGIOPPasswordEncrypted);
         this._DefaultTGIOPPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(114, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(114)) {
               source._postSetFirePropertyChange(114, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setJavaStandardTrustKeyStorePassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(197);
      byte[] _oldVal = this._JavaStandardTrustKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: JavaStandardTrustKeyStorePassPhraseEncrypted of ServerTemplateMBean");
      } else {
         this._getHelper()._clearArray(this._JavaStandardTrustKeyStorePassPhraseEncrypted);
         this._JavaStandardTrustKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(197, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(197)) {
               source._postSetFirePropertyChange(197, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setSystemPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(121);
      byte[] _oldVal = this._SystemPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SystemPasswordEncrypted of ServerTemplateMBean");
      } else {
         this._getHelper()._clearArray(this._SystemPasswordEncrypted);
         this._SystemPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(121, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerTemplateMBeanImpl source = (ServerTemplateMBeanImpl)var4.next();
            if (source != null && !source._isSet(121)) {
               source._postSetFirePropertyChange(121, wasSet, _oldVal, param0);
            }
         }

      }
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
         if (idx == 190) {
            this._markSet(191, false);
         }

         if (idx == 194) {
            this._markSet(195, false);
         }

         if (idx == 109) {
            this._markSet(110, false);
         }

         if (idx == 113) {
            this._markSet(114, false);
         }

         if (idx == 196) {
            this._markSet(197, false);
         }

         if (idx == 120) {
            this._markSet(121, false);
         }
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet() || this.isCOMSet() || this.isCoherenceMemberConfigSet() || this.isDataSourceSet() || this.isDefaultFileStoreSet() || this.isFederationServicesSet() || this.isOverloadProtectionSet() || this.isServerDebugSet() || this.isServerDiagnosticConfigSet() || this.isServerStartSet() || this.isSingleSignOnServicesSet() || this.isTransactionLogJDBCStoreSet() || this.isWebServerSet() || this.isWebServiceSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 213;
      }

      try {
         switch (idx) {
            case 213:
               this._81StyleDefaultStagingDirName = null;
               if (initOne) {
                  break;
               }
            case 130:
               this._AcceptBacklog = 300;
               if (initOne) {
                  break;
               }
            case 163:
               this._customizer.setActiveDirectoryName((String)null);
               if (initOne) {
                  break;
               }
            case 151:
               this._AdminReconnectIntervalSeconds = 10;
               if (initOne) {
                  break;
               }
            case 134:
               this._customizer.setAdministrationPort(0);
               if (initOne) {
                  break;
               }
            case 210:
               this._AutoJDBCConnectionClose = "false";
               if (initOne) {
                  break;
               }
            case 166:
               this._AutoKillIfFailed = false;
               if (initOne) {
                  break;
               }
            case 165:
               this._AutoRestart = true;
               if (initOne) {
                  break;
               }
            case 225:
               this._BuzzAddress = null;
               if (initOne) {
                  break;
               }
            case 224:
               this._BuzzPort = 0;
               if (initOne) {
                  break;
               }
            case 117:
               this._COM = new COMMBeanImpl(this, 117);
               this._postCreate((AbstractDescriptorBean)this._COM);
               if (initOne) {
                  break;
               }
            case 201:
               this._CandidateMachines = new MachineMBean[0];
               if (initOne) {
                  break;
               }
            case 95:
               this._customizer.setCluster((ClusterMBean)null);
               if (initOne) {
                  break;
               }
            case 96:
               this._ClusterWeight = 100;
               if (initOne) {
                  break;
               }
            case 218:
               this._customizer.setCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)null);
               if (initOne) {
                  break;
               }
            case 223:
               this._CoherenceMemberConfig = new CoherenceMemberConfigMBeanImpl(this, 223);
               this._postCreate((AbstractDescriptorBean)this._CoherenceMemberConfig);
               if (initOne) {
                  break;
               }
            case 89:
               this._ConfigurationProperties = new ConfigurationPropertyMBean[0];
               if (initOne) {
                  break;
               }
            case 99:
               this._ConsensusProcessIdentifier = -1;
               if (initOne) {
                  break;
               }
            case 188:
               this._CustomIdentityKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 190:
               this._CustomIdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 191:
               this._CustomIdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 189:
               this._CustomIdentityKeyStoreType = null;
               if (initOne) {
                  break;
               }
            case 192:
               this._CustomTrustKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 194:
               this._CustomTrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 195:
               this._CustomTrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 193:
               this._CustomTrustKeyStoreType = null;
               if (initOne) {
                  break;
               }
            case 222:
               this._DataSource = new DataSourceMBeanImpl(this, 222);
               this._postCreate((AbstractDescriptorBean)this._DataSource);
               if (initOne) {
                  break;
               }
            case 200:
               this._DefaultFileStore = new DefaultFileStoreMBeanImpl(this, 200);
               this._postCreate((AbstractDescriptorBean)this._DefaultFileStore);
               if (initOne) {
                  break;
               }
            case 109:
               this._DefaultIIOPPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 110:
               this._DefaultIIOPPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 108:
               this._DefaultIIOPUser = null;
               if (initOne) {
                  break;
               }
            case 212:
               this._DefaultStagingDirName = null;
               if (initOne) {
                  break;
               }
            case 113:
               this._DefaultTGIOPPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 114:
               this._DefaultTGIOPPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 112:
               this._DefaultTGIOPUser = "guest";
               if (initOne) {
                  break;
               }
            case 102:
               this._ExpectedToRun = true;
               if (initOne) {
                  break;
               }
            case 126:
               this._ExternalDNSName = null;
               if (initOne) {
                  break;
               }
            case 143:
               this._ExtraEjbcOptions = null;
               if (initOne) {
                  break;
               }
            case 142:
               this._ExtraRmicOptions = null;
               if (initOne) {
                  break;
               }
            case 214:
               this._FederationServices = new FederationServicesMBeanImpl(this, 214);
               this._postCreate((AbstractDescriptorBean)this._FederationServices);
               if (initOne) {
                  break;
               }
            case 179:
               this._GracefulShutdownTimeout = 0;
               if (initOne) {
                  break;
               }
            case 169:
               this._HealthCheckIntervalSeconds = 180;
               if (initOne) {
                  break;
               }
            case 171:
               this._HealthCheckStartDelaySeconds = 120;
               if (initOne) {
                  break;
               }
            case 170:
               this._HealthCheckTimeoutSeconds = 60;
               if (initOne) {
                  break;
               }
            case 185:
               this._HostsMigratableServices = true;
               if (initOne) {
                  break;
               }
            case 136:
               this._IIOPConnectionPools = null;
               if (initOne) {
                  break;
               }
            case 128:
               this._InterfaceAddress = null;
               if (initOne) {
                  break;
               }
            case 203:
               this._JDBCLLRTableName = null;
               if (initOne) {
                  break;
               }
            case 206:
               this._JDBCLLRTablePoolColumnSize = 64;
               if (initOne) {
                  break;
               }
            case 207:
               this._JDBCLLRTableRecordColumnSize = 1000;
               if (initOne) {
                  break;
               }
            case 205:
               this._JDBCLLRTableXIDColumnSize = 40;
               if (initOne) {
                  break;
               }
            case 104:
               this._JDBCLogFileName = "jdbc.log";
               if (initOne) {
                  break;
               }
            case 208:
               this._JDBCLoginTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 153:
               this._JMSConnectionFactoryUnmappedResRefMode = "ReturnDefault";
               if (initOne) {
                  break;
               }
            case 135:
               this._JNDITransportableObjectFactoryList = new String[0];
               if (initOne) {
                  break;
               }
            case 156:
               this._JTAMigratableTarget = null;
               if (initOne) {
                  break;
               }
            case 139:
               this._JavaCompiler = "javac";
               if (initOne) {
                  break;
               }
            case 141:
               this._JavaCompilerPostClassPath = null;
               if (initOne) {
                  break;
               }
            case 140:
               this._JavaCompilerPreClassPath = null;
               if (initOne) {
                  break;
               }
            case 196:
               this._JavaStandardTrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 197:
               this._JavaStandardTrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 51:
               this._KernelDebug = null;
               if (initOne) {
                  break;
               }
            case 187:
               this._KeyStores = "DemoIdentityAndDemoTrust";
               if (initOne) {
                  break;
               }
            case 125:
               this._ListenAddress = null;
               if (initOne) {
                  break;
               }
            case 155:
               this._ListenDelaySecs = 0;
               if (initOne) {
                  break;
               }
            case 92:
               this._ListenPort = 7001;
               if (initOne) {
                  break;
               }
            case 123:
               this._ListenThreadStartDelaySecs = 60;
               if (initOne) {
                  break;
               }
            case 124:
               this._ListenersBindEarly = false;
               if (initOne) {
                  break;
               }
            case 94:
               this._LoginTimeout = 1000;
               if (initOne) {
                  break;
               }
            case 132:
               this._LoginTimeoutMillis = 5000;
               if (initOne) {
                  break;
               }
            case 160:
               this._LowMemoryGCThreshold = 5;
               if (initOne) {
                  break;
               }
            case 159:
               this._LowMemoryGranularityLevel = 5;
               if (initOne) {
                  break;
               }
            case 158:
               this._LowMemorySampleSize = 10;
               if (initOne) {
                  break;
               }
            case 157:
               this._LowMemoryTimeInterval = 3600;
               if (initOne) {
                  break;
               }
            case 91:
               this._Machine = null;
               if (initOne) {
                  break;
               }
            case 131:
               this._MaxBackoffBetweenFailures = 10000;
               if (initOne) {
                  break;
               }
            case 228:
               this._MaxConcurrentLongRunningRequests = 100;
               if (initOne) {
                  break;
               }
            case 227:
               this._MaxConcurrentNewThreads = 100;
               if (initOne) {
                  break;
               }
            case 217:
               this._NMSocketCreateTimeoutInMillis = 180000;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 129:
               this._NetworkAccessPoints = new NetworkAccessPointMBean[0];
               if (initOne) {
                  break;
               }
            case 229:
               this._NumOfRetriesBeforeMSIMode = 3;
               if (initOne) {
                  break;
               }
            case 202:
               this._OverloadProtection = new OverloadProtectionMBeanImpl(this, 202);
               this._postCreate((AbstractDescriptorBean)this._OverloadProtection);
               if (initOne) {
                  break;
               }
            case 231:
               this._customizer.setParent((WebLogicMBean)null);
               if (initOne) {
                  break;
               }
            case 98:
               this._PreferredSecondaryGroup = null;
               if (initOne) {
                  break;
               }
            case 198:
               this._ReliableDeliveryPolicy = null;
               if (initOne) {
                  break;
               }
            case 97:
               this._ReplicationGroup = null;
               if (initOne) {
                  break;
               }
            case 220:
               this._ReplicationPorts = null;
               if (initOne) {
                  break;
               }
            case 127:
               this._ResolveDNSName = false;
               if (initOne) {
                  break;
               }
            case 172:
               this._RestartDelaySeconds = 0;
               if (initOne) {
                  break;
               }
            case 167:
               this._RestartIntervalSeconds = 3600;
               if (initOne) {
                  break;
               }
            case 168:
               this._RestartMax = 2;
               if (initOne) {
                  break;
               }
            case 230:
               this._RetryIntervalBeforeMSIMode = 5;
               if (initOne) {
                  break;
               }
            case 90:
               this._RootDirectory = ".";
               if (initOne) {
                  break;
               }
            case 118:
               this._ServerDebug = new ServerDebugMBeanImpl(this, 118);
               this._postCreate((AbstractDescriptorBean)this._ServerDebug);
               if (initOne) {
                  break;
               }
            case 209:
               this._ServerDiagnosticConfig = new WLDFServerDiagnosticMBeanImpl(this, 209);
               this._postCreate((AbstractDescriptorBean)this._ServerDiagnosticConfig);
               if (initOne) {
                  break;
               }
            case 177:
               this._ServerLifeCycleTimeoutVal = 30;
               if (initOne) {
                  break;
               }
            case 88:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 154:
               this._ServerStart = new ServerStartMBeanImpl(this, 154);
               this._postCreate((AbstractDescriptorBean)this._ServerStart);
               if (initOne) {
                  break;
               }
            case 175:
               this._ServerVersion = "unknown";
               if (initOne) {
                  break;
               }
            case 215:
               this._SingleSignOnServices = new SingleSignOnServicesMBeanImpl(this, 215);
               this._postCreate((AbstractDescriptorBean)this._SingleSignOnServices);
               if (initOne) {
                  break;
               }
            case 233:
               this._SitConfigPollingInterval = 5;
               if (initOne) {
                  break;
               }
            case 161:
               this._customizer.setStagingDirectoryName((String)null);
               if (initOne) {
                  break;
               }
            case 164:
               this._StagingMode = null;
               if (initOne) {
                  break;
               }
            case 176:
               this._customizer.setStartupMode("RUNNING");
               if (initOne) {
                  break;
               }
            case 178:
               this._StartupTimeout = 0;
               if (initOne) {
                  break;
               }
            case 64:
               this._customizer.setStdoutFormat("standard");
               if (initOne) {
                  break;
               }
            case 57:
               this._customizer.setStdoutSeverityLevel(32);
               if (initOne) {
                  break;
               }
            case 211:
               this._SupportedProtocols = new String[0];
               if (initOne) {
                  break;
               }
            case 120:
               this._SystemPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 121:
               this._SystemPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setThreadPoolSize(15);
               if (initOne) {
                  break;
               }
            case 145:
               this._TransactionLogFilePrefix = "./";
               if (initOne) {
                  break;
               }
            case 146:
               this._TransactionLogFileWritePolicy = "Direct-Write";
               if (initOne) {
                  break;
               }
            case 221:
               this._TransactionLogJDBCStore = new TransactionLogJDBCStoreMBeanImpl(this, 221);
               this._postCreate((AbstractDescriptorBean)this._TransactionLogJDBCStore);
               if (initOne) {
                  break;
               }
            case 236:
               this._TransactionPrimaryChannelName = null;
               if (initOne) {
                  break;
               }
            case 238:
               this._TransactionPublicChannelName = null;
               if (initOne) {
                  break;
               }
            case 239:
               this._TransactionPublicSecureChannelName = null;
               if (initOne) {
                  break;
               }
            case 237:
               this._TransactionSecureChannelName = null;
               if (initOne) {
                  break;
               }
            case 149:
               this._TunnelingClientPingSecs = 45;
               if (initOne) {
                  break;
               }
            case 150:
               this._TunnelingClientTimeoutSecs = 40;
               if (initOne) {
                  break;
               }
            case 162:
               this._customizer.setUploadDirectoryName((String)null);
               if (initOne) {
                  break;
               }
            case 144:
               this._VerboseEJBDeploymentEnabled = "false";
               if (initOne) {
                  break;
               }
            case 219:
               this._VirtualMachineName = null;
               if (initOne) {
                  break;
               }
            case 101:
               this._WebServer = new WebServerMBeanImpl(this, 101);
               this._postCreate((AbstractDescriptorBean)this._WebServer);
               if (initOne) {
                  break;
               }
            case 216:
               this._WebService = new WebServiceMBeanImpl(this, 216);
               this._postCreate((AbstractDescriptorBean)this._WebService);
               if (initOne) {
                  break;
               }
            case 138:
               this._XMLEntityCache = null;
               if (initOne) {
                  break;
               }
            case 137:
               this._XMLRegistry = null;
               if (initOne) {
                  break;
               }
            case 133:
               this._AdministrationPortEnabled = false;
               if (initOne) {
                  break;
               }
            case 100:
               this._AutoMigrationEnabled = false;
               if (initOne) {
                  break;
               }
            case 226:
               this._BuzzEnabled = false;
               if (initOne) {
                  break;
               }
            case 115:
               this._COMEnabled = false;
               if (initOne) {
                  break;
               }
            case 173:
               this._ClasspathServletDisabled = false;
               if (initOne) {
                  break;
               }
            case 232:
               this._ClasspathServletSecureModeEnabled = false;
               if (initOne) {
                  break;
               }
            case 235:
               this._CleanupOrphanedSessionsEnabled = false;
               if (initOne) {
                  break;
               }
            case 183:
               this._ClientCertProxyEnabled = false;
               if (initOne) {
                  break;
               }
            case 122:
               this._ConsoleInputEnabled = false;
               if (initOne) {
                  break;
               }
            case 174:
               this._DefaultInternalServletsDisabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 186:
               this._HttpTraceSupportEnabled = false;
               if (initOne) {
                  break;
               }
            case 119:
               this._HttpdEnabled = true;
               if (initOne) {
                  break;
               }
            case 107:
               this._IIOPEnabled = true;
               if (initOne) {
                  break;
               }
            case 180:
               this._IgnoreSessionsDuringShutdown = false;
               if (initOne) {
                  break;
               }
            case 105:
               this._J2EE12OnlyModeEnabled = false;
               if (initOne) {
                  break;
               }
            case 106:
               this._J2EE13WarningEnabled = false;
               if (initOne) {
                  break;
               }
            case 103:
               this._JDBCLoggingEnabled = false;
               if (initOne) {
                  break;
               }
            case 152:
               this._JMSDefaultConnectionFactoriesEnabled = true;
               if (initOne) {
                  break;
               }
            case 116:
               this._JRMPEnabled = false;
               if (initOne) {
                  break;
               }
            case 93:
               this._ListenPortEnabled = true;
               if (initOne) {
                  break;
               }
            case 182:
               this._MSIFileReplicationEnabled = false;
               if (initOne) {
                  break;
               }
            case 181:
               this._ManagedServerIndependenceEnabled = true;
               if (initOne) {
                  break;
               }
            case 199:
               this._customizer.setMessageIdPrefixEnabled(true);
               if (initOne) {
                  break;
               }
            case 147:
               this._NetworkClassLoadingEnabled = false;
               if (initOne) {
                  break;
               }
            case 234:
               this._SessionReplicationOnShutdownEnabled = false;
               if (initOne) {
                  break;
               }
            case 240:
               this._SitConfigRequired = false;
               if (initOne) {
                  break;
               }
            case 58:
               this._customizer.setStdoutDebugEnabled(false);
               if (initOne) {
                  break;
               }
            case 56:
               this._customizer.setStdoutEnabled(true);
               if (initOne) {
                  break;
               }
            case 65:
               this._customizer.setStdoutLogStack(true);
               if (initOne) {
                  break;
               }
            case 111:
               this._TGIOPEnabled = true;
               if (initOne) {
                  break;
               }
            case 148:
               this._TunnelingEnabled = false;
               if (initOne) {
                  break;
               }
            case 204:
               this._UseFusionForLLR = false;
               if (initOne) {
                  break;
               }
            case 184:
               this._WeblogicPluginEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
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
            case 52:
            case 53:
            case 54:
            case 55:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
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
            case 84:
            case 85:
            case 86:
            case 87:
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
      return "ServerTemplate";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("81StyleDefaultStagingDirName")) {
         oldVal = this._81StyleDefaultStagingDirName;
         this._81StyleDefaultStagingDirName = (String)v;
         this._postSet(213, oldVal, this._81StyleDefaultStagingDirName);
      } else {
         int oldVal;
         if (name.equals("AcceptBacklog")) {
            oldVal = this._AcceptBacklog;
            this._AcceptBacklog = (Integer)v;
            this._postSet(130, oldVal, this._AcceptBacklog);
         } else if (name.equals("ActiveDirectoryName")) {
            oldVal = this._ActiveDirectoryName;
            this._ActiveDirectoryName = (String)v;
            this._postSet(163, oldVal, this._ActiveDirectoryName);
         } else if (name.equals("AdminReconnectIntervalSeconds")) {
            oldVal = this._AdminReconnectIntervalSeconds;
            this._AdminReconnectIntervalSeconds = (Integer)v;
            this._postSet(151, oldVal, this._AdminReconnectIntervalSeconds);
         } else if (name.equals("AdministrationPort")) {
            oldVal = this._AdministrationPort;
            this._AdministrationPort = (Integer)v;
            this._postSet(134, oldVal, this._AdministrationPort);
         } else {
            boolean oldVal;
            if (name.equals("AdministrationPortEnabled")) {
               oldVal = this._AdministrationPortEnabled;
               this._AdministrationPortEnabled = (Boolean)v;
               this._postSet(133, oldVal, this._AdministrationPortEnabled);
            } else if (name.equals("AutoJDBCConnectionClose")) {
               oldVal = this._AutoJDBCConnectionClose;
               this._AutoJDBCConnectionClose = (String)v;
               this._postSet(210, oldVal, this._AutoJDBCConnectionClose);
            } else if (name.equals("AutoKillIfFailed")) {
               oldVal = this._AutoKillIfFailed;
               this._AutoKillIfFailed = (Boolean)v;
               this._postSet(166, oldVal, this._AutoKillIfFailed);
            } else if (name.equals("AutoMigrationEnabled")) {
               oldVal = this._AutoMigrationEnabled;
               this._AutoMigrationEnabled = (Boolean)v;
               this._postSet(100, oldVal, this._AutoMigrationEnabled);
            } else if (name.equals("AutoRestart")) {
               oldVal = this._AutoRestart;
               this._AutoRestart = (Boolean)v;
               this._postSet(165, oldVal, this._AutoRestart);
            } else if (name.equals("BuzzAddress")) {
               oldVal = this._BuzzAddress;
               this._BuzzAddress = (String)v;
               this._postSet(225, oldVal, this._BuzzAddress);
            } else if (name.equals("BuzzEnabled")) {
               oldVal = this._BuzzEnabled;
               this._BuzzEnabled = (Boolean)v;
               this._postSet(226, oldVal, this._BuzzEnabled);
            } else if (name.equals("BuzzPort")) {
               oldVal = this._BuzzPort;
               this._BuzzPort = (Integer)v;
               this._postSet(224, oldVal, this._BuzzPort);
            } else if (name.equals("COM")) {
               COMMBean oldVal = this._COM;
               this._COM = (COMMBean)v;
               this._postSet(117, oldVal, this._COM);
            } else if (name.equals("COMEnabled")) {
               oldVal = this._COMEnabled;
               this._COMEnabled = (Boolean)v;
               this._postSet(115, oldVal, this._COMEnabled);
            } else if (name.equals("CandidateMachines")) {
               MachineMBean[] oldVal = this._CandidateMachines;
               this._CandidateMachines = (MachineMBean[])((MachineMBean[])v);
               this._postSet(201, oldVal, this._CandidateMachines);
            } else if (name.equals("ClasspathServletDisabled")) {
               oldVal = this._ClasspathServletDisabled;
               this._ClasspathServletDisabled = (Boolean)v;
               this._postSet(173, oldVal, this._ClasspathServletDisabled);
            } else if (name.equals("ClasspathServletSecureModeEnabled")) {
               oldVal = this._ClasspathServletSecureModeEnabled;
               this._ClasspathServletSecureModeEnabled = (Boolean)v;
               this._postSet(232, oldVal, this._ClasspathServletSecureModeEnabled);
            } else if (name.equals("CleanupOrphanedSessionsEnabled")) {
               oldVal = this._CleanupOrphanedSessionsEnabled;
               this._CleanupOrphanedSessionsEnabled = (Boolean)v;
               this._postSet(235, oldVal, this._CleanupOrphanedSessionsEnabled);
            } else if (name.equals("ClientCertProxyEnabled")) {
               oldVal = this._ClientCertProxyEnabled;
               this._ClientCertProxyEnabled = (Boolean)v;
               this._postSet(183, oldVal, this._ClientCertProxyEnabled);
            } else if (name.equals("Cluster")) {
               ClusterMBean oldVal = this._Cluster;
               this._Cluster = (ClusterMBean)v;
               this._postSet(95, oldVal, this._Cluster);
            } else if (name.equals("ClusterWeight")) {
               oldVal = this._ClusterWeight;
               this._ClusterWeight = (Integer)v;
               this._postSet(96, oldVal, this._ClusterWeight);
            } else if (name.equals("CoherenceClusterSystemResource")) {
               CoherenceClusterSystemResourceMBean oldVal = this._CoherenceClusterSystemResource;
               this._CoherenceClusterSystemResource = (CoherenceClusterSystemResourceMBean)v;
               this._postSet(218, oldVal, this._CoherenceClusterSystemResource);
            } else if (name.equals("CoherenceMemberConfig")) {
               CoherenceMemberConfigMBean oldVal = this._CoherenceMemberConfig;
               this._CoherenceMemberConfig = (CoherenceMemberConfigMBean)v;
               this._postSet(223, oldVal, this._CoherenceMemberConfig);
            } else if (name.equals("ConfigurationProperties")) {
               ConfigurationPropertyMBean[] oldVal = this._ConfigurationProperties;
               this._ConfigurationProperties = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])v);
               this._postSet(89, oldVal, this._ConfigurationProperties);
            } else if (name.equals("ConsensusProcessIdentifier")) {
               oldVal = this._ConsensusProcessIdentifier;
               this._ConsensusProcessIdentifier = (Integer)v;
               this._postSet(99, oldVal, this._ConsensusProcessIdentifier);
            } else if (name.equals("ConsoleInputEnabled")) {
               oldVal = this._ConsoleInputEnabled;
               this._ConsoleInputEnabled = (Boolean)v;
               this._postSet(122, oldVal, this._ConsoleInputEnabled);
            } else if (name.equals("CustomIdentityKeyStoreFileName")) {
               oldVal = this._CustomIdentityKeyStoreFileName;
               this._CustomIdentityKeyStoreFileName = (String)v;
               this._postSet(188, oldVal, this._CustomIdentityKeyStoreFileName);
            } else if (name.equals("CustomIdentityKeyStorePassPhrase")) {
               oldVal = this._CustomIdentityKeyStorePassPhrase;
               this._CustomIdentityKeyStorePassPhrase = (String)v;
               this._postSet(190, oldVal, this._CustomIdentityKeyStorePassPhrase);
            } else {
               byte[] oldVal;
               if (name.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
                  oldVal = this._CustomIdentityKeyStorePassPhraseEncrypted;
                  this._CustomIdentityKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(191, oldVal, this._CustomIdentityKeyStorePassPhraseEncrypted);
               } else if (name.equals("CustomIdentityKeyStoreType")) {
                  oldVal = this._CustomIdentityKeyStoreType;
                  this._CustomIdentityKeyStoreType = (String)v;
                  this._postSet(189, oldVal, this._CustomIdentityKeyStoreType);
               } else if (name.equals("CustomTrustKeyStoreFileName")) {
                  oldVal = this._CustomTrustKeyStoreFileName;
                  this._CustomTrustKeyStoreFileName = (String)v;
                  this._postSet(192, oldVal, this._CustomTrustKeyStoreFileName);
               } else if (name.equals("CustomTrustKeyStorePassPhrase")) {
                  oldVal = this._CustomTrustKeyStorePassPhrase;
                  this._CustomTrustKeyStorePassPhrase = (String)v;
                  this._postSet(194, oldVal, this._CustomTrustKeyStorePassPhrase);
               } else if (name.equals("CustomTrustKeyStorePassPhraseEncrypted")) {
                  oldVal = this._CustomTrustKeyStorePassPhraseEncrypted;
                  this._CustomTrustKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(195, oldVal, this._CustomTrustKeyStorePassPhraseEncrypted);
               } else if (name.equals("CustomTrustKeyStoreType")) {
                  oldVal = this._CustomTrustKeyStoreType;
                  this._CustomTrustKeyStoreType = (String)v;
                  this._postSet(193, oldVal, this._CustomTrustKeyStoreType);
               } else if (name.equals("DataSource")) {
                  DataSourceMBean oldVal = this._DataSource;
                  this._DataSource = (DataSourceMBean)v;
                  this._postSet(222, oldVal, this._DataSource);
               } else if (name.equals("DefaultFileStore")) {
                  DefaultFileStoreMBean oldVal = this._DefaultFileStore;
                  this._DefaultFileStore = (DefaultFileStoreMBean)v;
                  this._postSet(200, oldVal, this._DefaultFileStore);
               } else if (name.equals("DefaultIIOPPassword")) {
                  oldVal = this._DefaultIIOPPassword;
                  this._DefaultIIOPPassword = (String)v;
                  this._postSet(109, oldVal, this._DefaultIIOPPassword);
               } else if (name.equals("DefaultIIOPPasswordEncrypted")) {
                  oldVal = this._DefaultIIOPPasswordEncrypted;
                  this._DefaultIIOPPasswordEncrypted = (byte[])((byte[])v);
                  this._postSet(110, oldVal, this._DefaultIIOPPasswordEncrypted);
               } else if (name.equals("DefaultIIOPUser")) {
                  oldVal = this._DefaultIIOPUser;
                  this._DefaultIIOPUser = (String)v;
                  this._postSet(108, oldVal, this._DefaultIIOPUser);
               } else if (name.equals("DefaultInternalServletsDisabled")) {
                  oldVal = this._DefaultInternalServletsDisabled;
                  this._DefaultInternalServletsDisabled = (Boolean)v;
                  this._postSet(174, oldVal, this._DefaultInternalServletsDisabled);
               } else if (name.equals("DefaultStagingDirName")) {
                  oldVal = this._DefaultStagingDirName;
                  this._DefaultStagingDirName = (String)v;
                  this._postSet(212, oldVal, this._DefaultStagingDirName);
               } else if (name.equals("DefaultTGIOPPassword")) {
                  oldVal = this._DefaultTGIOPPassword;
                  this._DefaultTGIOPPassword = (String)v;
                  this._postSet(113, oldVal, this._DefaultTGIOPPassword);
               } else if (name.equals("DefaultTGIOPPasswordEncrypted")) {
                  oldVal = this._DefaultTGIOPPasswordEncrypted;
                  this._DefaultTGIOPPasswordEncrypted = (byte[])((byte[])v);
                  this._postSet(114, oldVal, this._DefaultTGIOPPasswordEncrypted);
               } else if (name.equals("DefaultTGIOPUser")) {
                  oldVal = this._DefaultTGIOPUser;
                  this._DefaultTGIOPUser = (String)v;
                  this._postSet(112, oldVal, this._DefaultTGIOPUser);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("ExpectedToRun")) {
                  oldVal = this._ExpectedToRun;
                  this._ExpectedToRun = (Boolean)v;
                  this._postSet(102, oldVal, this._ExpectedToRun);
               } else if (name.equals("ExternalDNSName")) {
                  oldVal = this._ExternalDNSName;
                  this._ExternalDNSName = (String)v;
                  this._postSet(126, oldVal, this._ExternalDNSName);
               } else if (name.equals("ExtraEjbcOptions")) {
                  oldVal = this._ExtraEjbcOptions;
                  this._ExtraEjbcOptions = (String)v;
                  this._postSet(143, oldVal, this._ExtraEjbcOptions);
               } else if (name.equals("ExtraRmicOptions")) {
                  oldVal = this._ExtraRmicOptions;
                  this._ExtraRmicOptions = (String)v;
                  this._postSet(142, oldVal, this._ExtraRmicOptions);
               } else if (name.equals("FederationServices")) {
                  FederationServicesMBean oldVal = this._FederationServices;
                  this._FederationServices = (FederationServicesMBean)v;
                  this._postSet(214, oldVal, this._FederationServices);
               } else if (name.equals("GracefulShutdownTimeout")) {
                  oldVal = this._GracefulShutdownTimeout;
                  this._GracefulShutdownTimeout = (Integer)v;
                  this._postSet(179, oldVal, this._GracefulShutdownTimeout);
               } else if (name.equals("HealthCheckIntervalSeconds")) {
                  oldVal = this._HealthCheckIntervalSeconds;
                  this._HealthCheckIntervalSeconds = (Integer)v;
                  this._postSet(169, oldVal, this._HealthCheckIntervalSeconds);
               } else if (name.equals("HealthCheckStartDelaySeconds")) {
                  oldVal = this._HealthCheckStartDelaySeconds;
                  this._HealthCheckStartDelaySeconds = (Integer)v;
                  this._postSet(171, oldVal, this._HealthCheckStartDelaySeconds);
               } else if (name.equals("HealthCheckTimeoutSeconds")) {
                  oldVal = this._HealthCheckTimeoutSeconds;
                  this._HealthCheckTimeoutSeconds = (Integer)v;
                  this._postSet(170, oldVal, this._HealthCheckTimeoutSeconds);
               } else if (name.equals("HostsMigratableServices")) {
                  oldVal = this._HostsMigratableServices;
                  this._HostsMigratableServices = (Boolean)v;
                  this._postSet(185, oldVal, this._HostsMigratableServices);
               } else if (name.equals("HttpTraceSupportEnabled")) {
                  oldVal = this._HttpTraceSupportEnabled;
                  this._HttpTraceSupportEnabled = (Boolean)v;
                  this._postSet(186, oldVal, this._HttpTraceSupportEnabled);
               } else if (name.equals("HttpdEnabled")) {
                  oldVal = this._HttpdEnabled;
                  this._HttpdEnabled = (Boolean)v;
                  this._postSet(119, oldVal, this._HttpdEnabled);
               } else if (name.equals("IIOPConnectionPools")) {
                  Map oldVal = this._IIOPConnectionPools;
                  this._IIOPConnectionPools = (Map)v;
                  this._postSet(136, oldVal, this._IIOPConnectionPools);
               } else if (name.equals("IIOPEnabled")) {
                  oldVal = this._IIOPEnabled;
                  this._IIOPEnabled = (Boolean)v;
                  this._postSet(107, oldVal, this._IIOPEnabled);
               } else if (name.equals("IgnoreSessionsDuringShutdown")) {
                  oldVal = this._IgnoreSessionsDuringShutdown;
                  this._IgnoreSessionsDuringShutdown = (Boolean)v;
                  this._postSet(180, oldVal, this._IgnoreSessionsDuringShutdown);
               } else if (name.equals("InterfaceAddress")) {
                  oldVal = this._InterfaceAddress;
                  this._InterfaceAddress = (String)v;
                  this._postSet(128, oldVal, this._InterfaceAddress);
               } else if (name.equals("J2EE12OnlyModeEnabled")) {
                  oldVal = this._J2EE12OnlyModeEnabled;
                  this._J2EE12OnlyModeEnabled = (Boolean)v;
                  this._postSet(105, oldVal, this._J2EE12OnlyModeEnabled);
               } else if (name.equals("J2EE13WarningEnabled")) {
                  oldVal = this._J2EE13WarningEnabled;
                  this._J2EE13WarningEnabled = (Boolean)v;
                  this._postSet(106, oldVal, this._J2EE13WarningEnabled);
               } else if (name.equals("JDBCLLRTableName")) {
                  oldVal = this._JDBCLLRTableName;
                  this._JDBCLLRTableName = (String)v;
                  this._postSet(203, oldVal, this._JDBCLLRTableName);
               } else if (name.equals("JDBCLLRTablePoolColumnSize")) {
                  oldVal = this._JDBCLLRTablePoolColumnSize;
                  this._JDBCLLRTablePoolColumnSize = (Integer)v;
                  this._postSet(206, oldVal, this._JDBCLLRTablePoolColumnSize);
               } else if (name.equals("JDBCLLRTableRecordColumnSize")) {
                  oldVal = this._JDBCLLRTableRecordColumnSize;
                  this._JDBCLLRTableRecordColumnSize = (Integer)v;
                  this._postSet(207, oldVal, this._JDBCLLRTableRecordColumnSize);
               } else if (name.equals("JDBCLLRTableXIDColumnSize")) {
                  oldVal = this._JDBCLLRTableXIDColumnSize;
                  this._JDBCLLRTableXIDColumnSize = (Integer)v;
                  this._postSet(205, oldVal, this._JDBCLLRTableXIDColumnSize);
               } else if (name.equals("JDBCLogFileName")) {
                  oldVal = this._JDBCLogFileName;
                  this._JDBCLogFileName = (String)v;
                  this._postSet(104, oldVal, this._JDBCLogFileName);
               } else if (name.equals("JDBCLoggingEnabled")) {
                  oldVal = this._JDBCLoggingEnabled;
                  this._JDBCLoggingEnabled = (Boolean)v;
                  this._postSet(103, oldVal, this._JDBCLoggingEnabled);
               } else if (name.equals("JDBCLoginTimeoutSeconds")) {
                  oldVal = this._JDBCLoginTimeoutSeconds;
                  this._JDBCLoginTimeoutSeconds = (Integer)v;
                  this._postSet(208, oldVal, this._JDBCLoginTimeoutSeconds);
               } else if (name.equals("JMSConnectionFactoryUnmappedResRefMode")) {
                  oldVal = this._JMSConnectionFactoryUnmappedResRefMode;
                  this._JMSConnectionFactoryUnmappedResRefMode = (String)v;
                  this._postSet(153, oldVal, this._JMSConnectionFactoryUnmappedResRefMode);
               } else if (name.equals("JMSDefaultConnectionFactoriesEnabled")) {
                  oldVal = this._JMSDefaultConnectionFactoriesEnabled;
                  this._JMSDefaultConnectionFactoriesEnabled = (Boolean)v;
                  this._postSet(152, oldVal, this._JMSDefaultConnectionFactoriesEnabled);
               } else {
                  String[] oldVal;
                  if (name.equals("JNDITransportableObjectFactoryList")) {
                     oldVal = this._JNDITransportableObjectFactoryList;
                     this._JNDITransportableObjectFactoryList = (String[])((String[])v);
                     this._postSet(135, oldVal, this._JNDITransportableObjectFactoryList);
                  } else if (name.equals("JRMPEnabled")) {
                     oldVal = this._JRMPEnabled;
                     this._JRMPEnabled = (Boolean)v;
                     this._postSet(116, oldVal, this._JRMPEnabled);
                  } else if (name.equals("JTAMigratableTarget")) {
                     JTAMigratableTargetMBean oldVal = this._JTAMigratableTarget;
                     this._JTAMigratableTarget = (JTAMigratableTargetMBean)v;
                     this._postSet(156, oldVal, this._JTAMigratableTarget);
                  } else if (name.equals("JavaCompiler")) {
                     oldVal = this._JavaCompiler;
                     this._JavaCompiler = (String)v;
                     this._postSet(139, oldVal, this._JavaCompiler);
                  } else if (name.equals("JavaCompilerPostClassPath")) {
                     oldVal = this._JavaCompilerPostClassPath;
                     this._JavaCompilerPostClassPath = (String)v;
                     this._postSet(141, oldVal, this._JavaCompilerPostClassPath);
                  } else if (name.equals("JavaCompilerPreClassPath")) {
                     oldVal = this._JavaCompilerPreClassPath;
                     this._JavaCompilerPreClassPath = (String)v;
                     this._postSet(140, oldVal, this._JavaCompilerPreClassPath);
                  } else if (name.equals("JavaStandardTrustKeyStorePassPhrase")) {
                     oldVal = this._JavaStandardTrustKeyStorePassPhrase;
                     this._JavaStandardTrustKeyStorePassPhrase = (String)v;
                     this._postSet(196, oldVal, this._JavaStandardTrustKeyStorePassPhrase);
                  } else if (name.equals("JavaStandardTrustKeyStorePassPhraseEncrypted")) {
                     oldVal = this._JavaStandardTrustKeyStorePassPhraseEncrypted;
                     this._JavaStandardTrustKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(197, oldVal, this._JavaStandardTrustKeyStorePassPhraseEncrypted);
                  } else if (name.equals("KernelDebug")) {
                     KernelDebugMBean oldVal = this._KernelDebug;
                     this._KernelDebug = (KernelDebugMBean)v;
                     this._postSet(51, oldVal, this._KernelDebug);
                  } else if (name.equals("KeyStores")) {
                     oldVal = this._KeyStores;
                     this._KeyStores = (String)v;
                     this._postSet(187, oldVal, this._KeyStores);
                  } else if (name.equals("ListenAddress")) {
                     oldVal = this._ListenAddress;
                     this._ListenAddress = (String)v;
                     this._postSet(125, oldVal, this._ListenAddress);
                  } else if (name.equals("ListenDelaySecs")) {
                     oldVal = this._ListenDelaySecs;
                     this._ListenDelaySecs = (Integer)v;
                     this._postSet(155, oldVal, this._ListenDelaySecs);
                  } else if (name.equals("ListenPort")) {
                     oldVal = this._ListenPort;
                     this._ListenPort = (Integer)v;
                     this._postSet(92, oldVal, this._ListenPort);
                  } else if (name.equals("ListenPortEnabled")) {
                     oldVal = this._ListenPortEnabled;
                     this._ListenPortEnabled = (Boolean)v;
                     this._postSet(93, oldVal, this._ListenPortEnabled);
                  } else if (name.equals("ListenThreadStartDelaySecs")) {
                     oldVal = this._ListenThreadStartDelaySecs;
                     this._ListenThreadStartDelaySecs = (Integer)v;
                     this._postSet(123, oldVal, this._ListenThreadStartDelaySecs);
                  } else if (name.equals("ListenersBindEarly")) {
                     oldVal = this._ListenersBindEarly;
                     this._ListenersBindEarly = (Boolean)v;
                     this._postSet(124, oldVal, this._ListenersBindEarly);
                  } else if (name.equals("LoginTimeout")) {
                     oldVal = this._LoginTimeout;
                     this._LoginTimeout = (Integer)v;
                     this._postSet(94, oldVal, this._LoginTimeout);
                  } else if (name.equals("LoginTimeoutMillis")) {
                     oldVal = this._LoginTimeoutMillis;
                     this._LoginTimeoutMillis = (Integer)v;
                     this._postSet(132, oldVal, this._LoginTimeoutMillis);
                  } else if (name.equals("LowMemoryGCThreshold")) {
                     oldVal = this._LowMemoryGCThreshold;
                     this._LowMemoryGCThreshold = (Integer)v;
                     this._postSet(160, oldVal, this._LowMemoryGCThreshold);
                  } else if (name.equals("LowMemoryGranularityLevel")) {
                     oldVal = this._LowMemoryGranularityLevel;
                     this._LowMemoryGranularityLevel = (Integer)v;
                     this._postSet(159, oldVal, this._LowMemoryGranularityLevel);
                  } else if (name.equals("LowMemorySampleSize")) {
                     oldVal = this._LowMemorySampleSize;
                     this._LowMemorySampleSize = (Integer)v;
                     this._postSet(158, oldVal, this._LowMemorySampleSize);
                  } else if (name.equals("LowMemoryTimeInterval")) {
                     oldVal = this._LowMemoryTimeInterval;
                     this._LowMemoryTimeInterval = (Integer)v;
                     this._postSet(157, oldVal, this._LowMemoryTimeInterval);
                  } else if (name.equals("MSIFileReplicationEnabled")) {
                     oldVal = this._MSIFileReplicationEnabled;
                     this._MSIFileReplicationEnabled = (Boolean)v;
                     this._postSet(182, oldVal, this._MSIFileReplicationEnabled);
                  } else if (name.equals("Machine")) {
                     MachineMBean oldVal = this._Machine;
                     this._Machine = (MachineMBean)v;
                     this._postSet(91, oldVal, this._Machine);
                  } else if (name.equals("ManagedServerIndependenceEnabled")) {
                     oldVal = this._ManagedServerIndependenceEnabled;
                     this._ManagedServerIndependenceEnabled = (Boolean)v;
                     this._postSet(181, oldVal, this._ManagedServerIndependenceEnabled);
                  } else if (name.equals("MaxBackoffBetweenFailures")) {
                     oldVal = this._MaxBackoffBetweenFailures;
                     this._MaxBackoffBetweenFailures = (Integer)v;
                     this._postSet(131, oldVal, this._MaxBackoffBetweenFailures);
                  } else if (name.equals("MaxConcurrentLongRunningRequests")) {
                     oldVal = this._MaxConcurrentLongRunningRequests;
                     this._MaxConcurrentLongRunningRequests = (Integer)v;
                     this._postSet(228, oldVal, this._MaxConcurrentLongRunningRequests);
                  } else if (name.equals("MaxConcurrentNewThreads")) {
                     oldVal = this._MaxConcurrentNewThreads;
                     this._MaxConcurrentNewThreads = (Integer)v;
                     this._postSet(227, oldVal, this._MaxConcurrentNewThreads);
                  } else if (name.equals("MessageIdPrefixEnabled")) {
                     oldVal = this._MessageIdPrefixEnabled;
                     this._MessageIdPrefixEnabled = (Boolean)v;
                     this._postSet(199, oldVal, this._MessageIdPrefixEnabled);
                  } else if (name.equals("NMSocketCreateTimeoutInMillis")) {
                     oldVal = this._NMSocketCreateTimeoutInMillis;
                     this._NMSocketCreateTimeoutInMillis = (Integer)v;
                     this._postSet(217, oldVal, this._NMSocketCreateTimeoutInMillis);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("NetworkAccessPoints")) {
                     NetworkAccessPointMBean[] oldVal = this._NetworkAccessPoints;
                     this._NetworkAccessPoints = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])v);
                     this._postSet(129, oldVal, this._NetworkAccessPoints);
                  } else if (name.equals("NetworkClassLoadingEnabled")) {
                     oldVal = this._NetworkClassLoadingEnabled;
                     this._NetworkClassLoadingEnabled = (Boolean)v;
                     this._postSet(147, oldVal, this._NetworkClassLoadingEnabled);
                  } else if (name.equals("NumOfRetriesBeforeMSIMode")) {
                     oldVal = this._NumOfRetriesBeforeMSIMode;
                     this._NumOfRetriesBeforeMSIMode = (Integer)v;
                     this._postSet(229, oldVal, this._NumOfRetriesBeforeMSIMode);
                  } else if (name.equals("OverloadProtection")) {
                     OverloadProtectionMBean oldVal = this._OverloadProtection;
                     this._OverloadProtection = (OverloadProtectionMBean)v;
                     this._postSet(202, oldVal, this._OverloadProtection);
                  } else if (name.equals("Parent")) {
                     WebLogicMBean oldVal = this._Parent;
                     this._Parent = (WebLogicMBean)v;
                     this._postSet(231, oldVal, this._Parent);
                  } else if (name.equals("PreferredSecondaryGroup")) {
                     oldVal = this._PreferredSecondaryGroup;
                     this._PreferredSecondaryGroup = (String)v;
                     this._postSet(98, oldVal, this._PreferredSecondaryGroup);
                  } else if (name.equals("ReliableDeliveryPolicy")) {
                     WSReliableDeliveryPolicyMBean oldVal = this._ReliableDeliveryPolicy;
                     this._ReliableDeliveryPolicy = (WSReliableDeliveryPolicyMBean)v;
                     this._postSet(198, oldVal, this._ReliableDeliveryPolicy);
                  } else if (name.equals("ReplicationGroup")) {
                     oldVal = this._ReplicationGroup;
                     this._ReplicationGroup = (String)v;
                     this._postSet(97, oldVal, this._ReplicationGroup);
                  } else if (name.equals("ReplicationPorts")) {
                     oldVal = this._ReplicationPorts;
                     this._ReplicationPorts = (String)v;
                     this._postSet(220, oldVal, this._ReplicationPorts);
                  } else if (name.equals("ResolveDNSName")) {
                     oldVal = this._ResolveDNSName;
                     this._ResolveDNSName = (Boolean)v;
                     this._postSet(127, oldVal, this._ResolveDNSName);
                  } else if (name.equals("RestartDelaySeconds")) {
                     oldVal = this._RestartDelaySeconds;
                     this._RestartDelaySeconds = (Integer)v;
                     this._postSet(172, oldVal, this._RestartDelaySeconds);
                  } else if (name.equals("RestartIntervalSeconds")) {
                     oldVal = this._RestartIntervalSeconds;
                     this._RestartIntervalSeconds = (Integer)v;
                     this._postSet(167, oldVal, this._RestartIntervalSeconds);
                  } else if (name.equals("RestartMax")) {
                     oldVal = this._RestartMax;
                     this._RestartMax = (Integer)v;
                     this._postSet(168, oldVal, this._RestartMax);
                  } else if (name.equals("RetryIntervalBeforeMSIMode")) {
                     oldVal = this._RetryIntervalBeforeMSIMode;
                     this._RetryIntervalBeforeMSIMode = (Integer)v;
                     this._postSet(230, oldVal, this._RetryIntervalBeforeMSIMode);
                  } else if (name.equals("RootDirectory")) {
                     oldVal = this._RootDirectory;
                     this._RootDirectory = (String)v;
                     this._postSet(90, oldVal, this._RootDirectory);
                  } else if (name.equals("ServerDebug")) {
                     ServerDebugMBean oldVal = this._ServerDebug;
                     this._ServerDebug = (ServerDebugMBean)v;
                     this._postSet(118, oldVal, this._ServerDebug);
                  } else if (name.equals("ServerDiagnosticConfig")) {
                     WLDFServerDiagnosticMBean oldVal = this._ServerDiagnosticConfig;
                     this._ServerDiagnosticConfig = (WLDFServerDiagnosticMBean)v;
                     this._postSet(209, oldVal, this._ServerDiagnosticConfig);
                  } else if (name.equals("ServerLifeCycleTimeoutVal")) {
                     oldVal = this._ServerLifeCycleTimeoutVal;
                     this._ServerLifeCycleTimeoutVal = (Integer)v;
                     this._postSet(177, oldVal, this._ServerLifeCycleTimeoutVal);
                  } else if (name.equals("ServerNames")) {
                     Set oldVal = this._ServerNames;
                     this._ServerNames = (Set)v;
                     this._postSet(88, oldVal, this._ServerNames);
                  } else if (name.equals("ServerStart")) {
                     ServerStartMBean oldVal = this._ServerStart;
                     this._ServerStart = (ServerStartMBean)v;
                     this._postSet(154, oldVal, this._ServerStart);
                  } else if (name.equals("ServerVersion")) {
                     oldVal = this._ServerVersion;
                     this._ServerVersion = (String)v;
                     this._postSet(175, oldVal, this._ServerVersion);
                  } else if (name.equals("SessionReplicationOnShutdownEnabled")) {
                     oldVal = this._SessionReplicationOnShutdownEnabled;
                     this._SessionReplicationOnShutdownEnabled = (Boolean)v;
                     this._postSet(234, oldVal, this._SessionReplicationOnShutdownEnabled);
                  } else if (name.equals("SingleSignOnServices")) {
                     SingleSignOnServicesMBean oldVal = this._SingleSignOnServices;
                     this._SingleSignOnServices = (SingleSignOnServicesMBean)v;
                     this._postSet(215, oldVal, this._SingleSignOnServices);
                  } else if (name.equals("SitConfigPollingInterval")) {
                     oldVal = this._SitConfigPollingInterval;
                     this._SitConfigPollingInterval = (Integer)v;
                     this._postSet(233, oldVal, this._SitConfigPollingInterval);
                  } else if (name.equals("SitConfigRequired")) {
                     oldVal = this._SitConfigRequired;
                     this._SitConfigRequired = (Boolean)v;
                     this._postSet(240, oldVal, this._SitConfigRequired);
                  } else if (name.equals("StagingDirectoryName")) {
                     oldVal = this._StagingDirectoryName;
                     this._StagingDirectoryName = (String)v;
                     this._postSet(161, oldVal, this._StagingDirectoryName);
                  } else if (name.equals("StagingMode")) {
                     oldVal = this._StagingMode;
                     this._StagingMode = (String)v;
                     this._postSet(164, oldVal, this._StagingMode);
                  } else if (name.equals("StartupMode")) {
                     oldVal = this._StartupMode;
                     this._StartupMode = (String)v;
                     this._postSet(176, oldVal, this._StartupMode);
                  } else if (name.equals("StartupTimeout")) {
                     oldVal = this._StartupTimeout;
                     this._StartupTimeout = (Integer)v;
                     this._postSet(178, oldVal, this._StartupTimeout);
                  } else if (name.equals("StdoutDebugEnabled")) {
                     oldVal = this._StdoutDebugEnabled;
                     this._StdoutDebugEnabled = (Boolean)v;
                     this._postSet(58, oldVal, this._StdoutDebugEnabled);
                  } else if (name.equals("StdoutEnabled")) {
                     oldVal = this._StdoutEnabled;
                     this._StdoutEnabled = (Boolean)v;
                     this._postSet(56, oldVal, this._StdoutEnabled);
                  } else if (name.equals("StdoutFormat")) {
                     oldVal = this._StdoutFormat;
                     this._StdoutFormat = (String)v;
                     this._postSet(64, oldVal, this._StdoutFormat);
                  } else if (name.equals("StdoutLogStack")) {
                     oldVal = this._StdoutLogStack;
                     this._StdoutLogStack = (Boolean)v;
                     this._postSet(65, oldVal, this._StdoutLogStack);
                  } else if (name.equals("StdoutSeverityLevel")) {
                     oldVal = this._StdoutSeverityLevel;
                     this._StdoutSeverityLevel = (Integer)v;
                     this._postSet(57, oldVal, this._StdoutSeverityLevel);
                  } else if (name.equals("SupportedProtocols")) {
                     oldVal = this._SupportedProtocols;
                     this._SupportedProtocols = (String[])((String[])v);
                     this._postSet(211, oldVal, this._SupportedProtocols);
                  } else if (name.equals("SystemPassword")) {
                     oldVal = this._SystemPassword;
                     this._SystemPassword = (String)v;
                     this._postSet(120, oldVal, this._SystemPassword);
                  } else if (name.equals("SystemPasswordEncrypted")) {
                     oldVal = this._SystemPasswordEncrypted;
                     this._SystemPasswordEncrypted = (byte[])((byte[])v);
                     this._postSet(121, oldVal, this._SystemPasswordEncrypted);
                  } else if (name.equals("TGIOPEnabled")) {
                     oldVal = this._TGIOPEnabled;
                     this._TGIOPEnabled = (Boolean)v;
                     this._postSet(111, oldVal, this._TGIOPEnabled);
                  } else if (name.equals("Tags")) {
                     oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("ThreadPoolSize")) {
                     oldVal = this._ThreadPoolSize;
                     this._ThreadPoolSize = (Integer)v;
                     this._postSet(15, oldVal, this._ThreadPoolSize);
                  } else if (name.equals("TransactionLogFilePrefix")) {
                     oldVal = this._TransactionLogFilePrefix;
                     this._TransactionLogFilePrefix = (String)v;
                     this._postSet(145, oldVal, this._TransactionLogFilePrefix);
                  } else if (name.equals("TransactionLogFileWritePolicy")) {
                     oldVal = this._TransactionLogFileWritePolicy;
                     this._TransactionLogFileWritePolicy = (String)v;
                     this._postSet(146, oldVal, this._TransactionLogFileWritePolicy);
                  } else if (name.equals("TransactionLogJDBCStore")) {
                     TransactionLogJDBCStoreMBean oldVal = this._TransactionLogJDBCStore;
                     this._TransactionLogJDBCStore = (TransactionLogJDBCStoreMBean)v;
                     this._postSet(221, oldVal, this._TransactionLogJDBCStore);
                  } else if (name.equals("TransactionPrimaryChannelName")) {
                     oldVal = this._TransactionPrimaryChannelName;
                     this._TransactionPrimaryChannelName = (String)v;
                     this._postSet(236, oldVal, this._TransactionPrimaryChannelName);
                  } else if (name.equals("TransactionPublicChannelName")) {
                     oldVal = this._TransactionPublicChannelName;
                     this._TransactionPublicChannelName = (String)v;
                     this._postSet(238, oldVal, this._TransactionPublicChannelName);
                  } else if (name.equals("TransactionPublicSecureChannelName")) {
                     oldVal = this._TransactionPublicSecureChannelName;
                     this._TransactionPublicSecureChannelName = (String)v;
                     this._postSet(239, oldVal, this._TransactionPublicSecureChannelName);
                  } else if (name.equals("TransactionSecureChannelName")) {
                     oldVal = this._TransactionSecureChannelName;
                     this._TransactionSecureChannelName = (String)v;
                     this._postSet(237, oldVal, this._TransactionSecureChannelName);
                  } else if (name.equals("TunnelingClientPingSecs")) {
                     oldVal = this._TunnelingClientPingSecs;
                     this._TunnelingClientPingSecs = (Integer)v;
                     this._postSet(149, oldVal, this._TunnelingClientPingSecs);
                  } else if (name.equals("TunnelingClientTimeoutSecs")) {
                     oldVal = this._TunnelingClientTimeoutSecs;
                     this._TunnelingClientTimeoutSecs = (Integer)v;
                     this._postSet(150, oldVal, this._TunnelingClientTimeoutSecs);
                  } else if (name.equals("TunnelingEnabled")) {
                     oldVal = this._TunnelingEnabled;
                     this._TunnelingEnabled = (Boolean)v;
                     this._postSet(148, oldVal, this._TunnelingEnabled);
                  } else if (name.equals("UploadDirectoryName")) {
                     oldVal = this._UploadDirectoryName;
                     this._UploadDirectoryName = (String)v;
                     this._postSet(162, oldVal, this._UploadDirectoryName);
                  } else if (name.equals("UseFusionForLLR")) {
                     oldVal = this._UseFusionForLLR;
                     this._UseFusionForLLR = (Boolean)v;
                     this._postSet(204, oldVal, this._UseFusionForLLR);
                  } else if (name.equals("VerboseEJBDeploymentEnabled")) {
                     oldVal = this._VerboseEJBDeploymentEnabled;
                     this._VerboseEJBDeploymentEnabled = (String)v;
                     this._postSet(144, oldVal, this._VerboseEJBDeploymentEnabled);
                  } else if (name.equals("VirtualMachineName")) {
                     oldVal = this._VirtualMachineName;
                     this._VirtualMachineName = (String)v;
                     this._postSet(219, oldVal, this._VirtualMachineName);
                  } else if (name.equals("WebServer")) {
                     WebServerMBean oldVal = this._WebServer;
                     this._WebServer = (WebServerMBean)v;
                     this._postSet(101, oldVal, this._WebServer);
                  } else if (name.equals("WebService")) {
                     WebServiceMBean oldVal = this._WebService;
                     this._WebService = (WebServiceMBean)v;
                     this._postSet(216, oldVal, this._WebService);
                  } else if (name.equals("WeblogicPluginEnabled")) {
                     oldVal = this._WeblogicPluginEnabled;
                     this._WeblogicPluginEnabled = (Boolean)v;
                     this._postSet(184, oldVal, this._WeblogicPluginEnabled);
                  } else if (name.equals("XMLEntityCache")) {
                     XMLEntityCacheMBean oldVal = this._XMLEntityCache;
                     this._XMLEntityCache = (XMLEntityCacheMBean)v;
                     this._postSet(138, oldVal, this._XMLEntityCache);
                  } else if (name.equals("XMLRegistry")) {
                     XMLRegistryMBean oldVal = this._XMLRegistry;
                     this._XMLRegistry = (XMLRegistryMBean)v;
                     this._postSet(137, oldVal, this._XMLRegistry);
                  } else if (name.equals("customizer")) {
                     ServerTemplate oldVal = this._customizer;
                     this._customizer = (ServerTemplate)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("81StyleDefaultStagingDirName")) {
         return this._81StyleDefaultStagingDirName;
      } else if (name.equals("AcceptBacklog")) {
         return new Integer(this._AcceptBacklog);
      } else if (name.equals("ActiveDirectoryName")) {
         return this._ActiveDirectoryName;
      } else if (name.equals("AdminReconnectIntervalSeconds")) {
         return new Integer(this._AdminReconnectIntervalSeconds);
      } else if (name.equals("AdministrationPort")) {
         return new Integer(this._AdministrationPort);
      } else if (name.equals("AdministrationPortEnabled")) {
         return new Boolean(this._AdministrationPortEnabled);
      } else if (name.equals("AutoJDBCConnectionClose")) {
         return this._AutoJDBCConnectionClose;
      } else if (name.equals("AutoKillIfFailed")) {
         return new Boolean(this._AutoKillIfFailed);
      } else if (name.equals("AutoMigrationEnabled")) {
         return new Boolean(this._AutoMigrationEnabled);
      } else if (name.equals("AutoRestart")) {
         return new Boolean(this._AutoRestart);
      } else if (name.equals("BuzzAddress")) {
         return this._BuzzAddress;
      } else if (name.equals("BuzzEnabled")) {
         return new Boolean(this._BuzzEnabled);
      } else if (name.equals("BuzzPort")) {
         return new Integer(this._BuzzPort);
      } else if (name.equals("COM")) {
         return this._COM;
      } else if (name.equals("COMEnabled")) {
         return new Boolean(this._COMEnabled);
      } else if (name.equals("CandidateMachines")) {
         return this._CandidateMachines;
      } else if (name.equals("ClasspathServletDisabled")) {
         return new Boolean(this._ClasspathServletDisabled);
      } else if (name.equals("ClasspathServletSecureModeEnabled")) {
         return new Boolean(this._ClasspathServletSecureModeEnabled);
      } else if (name.equals("CleanupOrphanedSessionsEnabled")) {
         return new Boolean(this._CleanupOrphanedSessionsEnabled);
      } else if (name.equals("ClientCertProxyEnabled")) {
         return new Boolean(this._ClientCertProxyEnabled);
      } else if (name.equals("Cluster")) {
         return this._Cluster;
      } else if (name.equals("ClusterWeight")) {
         return new Integer(this._ClusterWeight);
      } else if (name.equals("CoherenceClusterSystemResource")) {
         return this._CoherenceClusterSystemResource;
      } else if (name.equals("CoherenceMemberConfig")) {
         return this._CoherenceMemberConfig;
      } else if (name.equals("ConfigurationProperties")) {
         return this._ConfigurationProperties;
      } else if (name.equals("ConsensusProcessIdentifier")) {
         return new Integer(this._ConsensusProcessIdentifier);
      } else if (name.equals("ConsoleInputEnabled")) {
         return new Boolean(this._ConsoleInputEnabled);
      } else if (name.equals("CustomIdentityKeyStoreFileName")) {
         return this._CustomIdentityKeyStoreFileName;
      } else if (name.equals("CustomIdentityKeyStorePassPhrase")) {
         return this._CustomIdentityKeyStorePassPhrase;
      } else if (name.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
         return this._CustomIdentityKeyStorePassPhraseEncrypted;
      } else if (name.equals("CustomIdentityKeyStoreType")) {
         return this._CustomIdentityKeyStoreType;
      } else if (name.equals("CustomTrustKeyStoreFileName")) {
         return this._CustomTrustKeyStoreFileName;
      } else if (name.equals("CustomTrustKeyStorePassPhrase")) {
         return this._CustomTrustKeyStorePassPhrase;
      } else if (name.equals("CustomTrustKeyStorePassPhraseEncrypted")) {
         return this._CustomTrustKeyStorePassPhraseEncrypted;
      } else if (name.equals("CustomTrustKeyStoreType")) {
         return this._CustomTrustKeyStoreType;
      } else if (name.equals("DataSource")) {
         return this._DataSource;
      } else if (name.equals("DefaultFileStore")) {
         return this._DefaultFileStore;
      } else if (name.equals("DefaultIIOPPassword")) {
         return this._DefaultIIOPPassword;
      } else if (name.equals("DefaultIIOPPasswordEncrypted")) {
         return this._DefaultIIOPPasswordEncrypted;
      } else if (name.equals("DefaultIIOPUser")) {
         return this._DefaultIIOPUser;
      } else if (name.equals("DefaultInternalServletsDisabled")) {
         return new Boolean(this._DefaultInternalServletsDisabled);
      } else if (name.equals("DefaultStagingDirName")) {
         return this._DefaultStagingDirName;
      } else if (name.equals("DefaultTGIOPPassword")) {
         return this._DefaultTGIOPPassword;
      } else if (name.equals("DefaultTGIOPPasswordEncrypted")) {
         return this._DefaultTGIOPPasswordEncrypted;
      } else if (name.equals("DefaultTGIOPUser")) {
         return this._DefaultTGIOPUser;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ExpectedToRun")) {
         return new Boolean(this._ExpectedToRun);
      } else if (name.equals("ExternalDNSName")) {
         return this._ExternalDNSName;
      } else if (name.equals("ExtraEjbcOptions")) {
         return this._ExtraEjbcOptions;
      } else if (name.equals("ExtraRmicOptions")) {
         return this._ExtraRmicOptions;
      } else if (name.equals("FederationServices")) {
         return this._FederationServices;
      } else if (name.equals("GracefulShutdownTimeout")) {
         return new Integer(this._GracefulShutdownTimeout);
      } else if (name.equals("HealthCheckIntervalSeconds")) {
         return new Integer(this._HealthCheckIntervalSeconds);
      } else if (name.equals("HealthCheckStartDelaySeconds")) {
         return new Integer(this._HealthCheckStartDelaySeconds);
      } else if (name.equals("HealthCheckTimeoutSeconds")) {
         return new Integer(this._HealthCheckTimeoutSeconds);
      } else if (name.equals("HostsMigratableServices")) {
         return new Boolean(this._HostsMigratableServices);
      } else if (name.equals("HttpTraceSupportEnabled")) {
         return new Boolean(this._HttpTraceSupportEnabled);
      } else if (name.equals("HttpdEnabled")) {
         return new Boolean(this._HttpdEnabled);
      } else if (name.equals("IIOPConnectionPools")) {
         return this._IIOPConnectionPools;
      } else if (name.equals("IIOPEnabled")) {
         return new Boolean(this._IIOPEnabled);
      } else if (name.equals("IgnoreSessionsDuringShutdown")) {
         return new Boolean(this._IgnoreSessionsDuringShutdown);
      } else if (name.equals("InterfaceAddress")) {
         return this._InterfaceAddress;
      } else if (name.equals("J2EE12OnlyModeEnabled")) {
         return new Boolean(this._J2EE12OnlyModeEnabled);
      } else if (name.equals("J2EE13WarningEnabled")) {
         return new Boolean(this._J2EE13WarningEnabled);
      } else if (name.equals("JDBCLLRTableName")) {
         return this._JDBCLLRTableName;
      } else if (name.equals("JDBCLLRTablePoolColumnSize")) {
         return new Integer(this._JDBCLLRTablePoolColumnSize);
      } else if (name.equals("JDBCLLRTableRecordColumnSize")) {
         return new Integer(this._JDBCLLRTableRecordColumnSize);
      } else if (name.equals("JDBCLLRTableXIDColumnSize")) {
         return new Integer(this._JDBCLLRTableXIDColumnSize);
      } else if (name.equals("JDBCLogFileName")) {
         return this._JDBCLogFileName;
      } else if (name.equals("JDBCLoggingEnabled")) {
         return new Boolean(this._JDBCLoggingEnabled);
      } else if (name.equals("JDBCLoginTimeoutSeconds")) {
         return new Integer(this._JDBCLoginTimeoutSeconds);
      } else if (name.equals("JMSConnectionFactoryUnmappedResRefMode")) {
         return this._JMSConnectionFactoryUnmappedResRefMode;
      } else if (name.equals("JMSDefaultConnectionFactoriesEnabled")) {
         return new Boolean(this._JMSDefaultConnectionFactoriesEnabled);
      } else if (name.equals("JNDITransportableObjectFactoryList")) {
         return this._JNDITransportableObjectFactoryList;
      } else if (name.equals("JRMPEnabled")) {
         return new Boolean(this._JRMPEnabled);
      } else if (name.equals("JTAMigratableTarget")) {
         return this._JTAMigratableTarget;
      } else if (name.equals("JavaCompiler")) {
         return this._JavaCompiler;
      } else if (name.equals("JavaCompilerPostClassPath")) {
         return this._JavaCompilerPostClassPath;
      } else if (name.equals("JavaCompilerPreClassPath")) {
         return this._JavaCompilerPreClassPath;
      } else if (name.equals("JavaStandardTrustKeyStorePassPhrase")) {
         return this._JavaStandardTrustKeyStorePassPhrase;
      } else if (name.equals("JavaStandardTrustKeyStorePassPhraseEncrypted")) {
         return this._JavaStandardTrustKeyStorePassPhraseEncrypted;
      } else if (name.equals("KernelDebug")) {
         return this._KernelDebug;
      } else if (name.equals("KeyStores")) {
         return this._KeyStores;
      } else if (name.equals("ListenAddress")) {
         return this._ListenAddress;
      } else if (name.equals("ListenDelaySecs")) {
         return new Integer(this._ListenDelaySecs);
      } else if (name.equals("ListenPort")) {
         return new Integer(this._ListenPort);
      } else if (name.equals("ListenPortEnabled")) {
         return new Boolean(this._ListenPortEnabled);
      } else if (name.equals("ListenThreadStartDelaySecs")) {
         return new Integer(this._ListenThreadStartDelaySecs);
      } else if (name.equals("ListenersBindEarly")) {
         return new Boolean(this._ListenersBindEarly);
      } else if (name.equals("LoginTimeout")) {
         return new Integer(this._LoginTimeout);
      } else if (name.equals("LoginTimeoutMillis")) {
         return new Integer(this._LoginTimeoutMillis);
      } else if (name.equals("LowMemoryGCThreshold")) {
         return new Integer(this._LowMemoryGCThreshold);
      } else if (name.equals("LowMemoryGranularityLevel")) {
         return new Integer(this._LowMemoryGranularityLevel);
      } else if (name.equals("LowMemorySampleSize")) {
         return new Integer(this._LowMemorySampleSize);
      } else if (name.equals("LowMemoryTimeInterval")) {
         return new Integer(this._LowMemoryTimeInterval);
      } else if (name.equals("MSIFileReplicationEnabled")) {
         return new Boolean(this._MSIFileReplicationEnabled);
      } else if (name.equals("Machine")) {
         return this._Machine;
      } else if (name.equals("ManagedServerIndependenceEnabled")) {
         return new Boolean(this._ManagedServerIndependenceEnabled);
      } else if (name.equals("MaxBackoffBetweenFailures")) {
         return new Integer(this._MaxBackoffBetweenFailures);
      } else if (name.equals("MaxConcurrentLongRunningRequests")) {
         return new Integer(this._MaxConcurrentLongRunningRequests);
      } else if (name.equals("MaxConcurrentNewThreads")) {
         return new Integer(this._MaxConcurrentNewThreads);
      } else if (name.equals("MessageIdPrefixEnabled")) {
         return new Boolean(this._MessageIdPrefixEnabled);
      } else if (name.equals("NMSocketCreateTimeoutInMillis")) {
         return new Integer(this._NMSocketCreateTimeoutInMillis);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NetworkAccessPoints")) {
         return this._NetworkAccessPoints;
      } else if (name.equals("NetworkClassLoadingEnabled")) {
         return new Boolean(this._NetworkClassLoadingEnabled);
      } else if (name.equals("NumOfRetriesBeforeMSIMode")) {
         return new Integer(this._NumOfRetriesBeforeMSIMode);
      } else if (name.equals("OverloadProtection")) {
         return this._OverloadProtection;
      } else if (name.equals("Parent")) {
         return this._Parent;
      } else if (name.equals("PreferredSecondaryGroup")) {
         return this._PreferredSecondaryGroup;
      } else if (name.equals("ReliableDeliveryPolicy")) {
         return this._ReliableDeliveryPolicy;
      } else if (name.equals("ReplicationGroup")) {
         return this._ReplicationGroup;
      } else if (name.equals("ReplicationPorts")) {
         return this._ReplicationPorts;
      } else if (name.equals("ResolveDNSName")) {
         return new Boolean(this._ResolveDNSName);
      } else if (name.equals("RestartDelaySeconds")) {
         return new Integer(this._RestartDelaySeconds);
      } else if (name.equals("RestartIntervalSeconds")) {
         return new Integer(this._RestartIntervalSeconds);
      } else if (name.equals("RestartMax")) {
         return new Integer(this._RestartMax);
      } else if (name.equals("RetryIntervalBeforeMSIMode")) {
         return new Integer(this._RetryIntervalBeforeMSIMode);
      } else if (name.equals("RootDirectory")) {
         return this._RootDirectory;
      } else if (name.equals("ServerDebug")) {
         return this._ServerDebug;
      } else if (name.equals("ServerDiagnosticConfig")) {
         return this._ServerDiagnosticConfig;
      } else if (name.equals("ServerLifeCycleTimeoutVal")) {
         return new Integer(this._ServerLifeCycleTimeoutVal);
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("ServerStart")) {
         return this._ServerStart;
      } else if (name.equals("ServerVersion")) {
         return this._ServerVersion;
      } else if (name.equals("SessionReplicationOnShutdownEnabled")) {
         return new Boolean(this._SessionReplicationOnShutdownEnabled);
      } else if (name.equals("SingleSignOnServices")) {
         return this._SingleSignOnServices;
      } else if (name.equals("SitConfigPollingInterval")) {
         return new Integer(this._SitConfigPollingInterval);
      } else if (name.equals("SitConfigRequired")) {
         return new Boolean(this._SitConfigRequired);
      } else if (name.equals("StagingDirectoryName")) {
         return this._StagingDirectoryName;
      } else if (name.equals("StagingMode")) {
         return this._StagingMode;
      } else if (name.equals("StartupMode")) {
         return this._StartupMode;
      } else if (name.equals("StartupTimeout")) {
         return new Integer(this._StartupTimeout);
      } else if (name.equals("StdoutDebugEnabled")) {
         return new Boolean(this._StdoutDebugEnabled);
      } else if (name.equals("StdoutEnabled")) {
         return new Boolean(this._StdoutEnabled);
      } else if (name.equals("StdoutFormat")) {
         return this._StdoutFormat;
      } else if (name.equals("StdoutLogStack")) {
         return new Boolean(this._StdoutLogStack);
      } else if (name.equals("StdoutSeverityLevel")) {
         return new Integer(this._StdoutSeverityLevel);
      } else if (name.equals("SupportedProtocols")) {
         return this._SupportedProtocols;
      } else if (name.equals("SystemPassword")) {
         return this._SystemPassword;
      } else if (name.equals("SystemPasswordEncrypted")) {
         return this._SystemPasswordEncrypted;
      } else if (name.equals("TGIOPEnabled")) {
         return new Boolean(this._TGIOPEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("ThreadPoolSize")) {
         return new Integer(this._ThreadPoolSize);
      } else if (name.equals("TransactionLogFilePrefix")) {
         return this._TransactionLogFilePrefix;
      } else if (name.equals("TransactionLogFileWritePolicy")) {
         return this._TransactionLogFileWritePolicy;
      } else if (name.equals("TransactionLogJDBCStore")) {
         return this._TransactionLogJDBCStore;
      } else if (name.equals("TransactionPrimaryChannelName")) {
         return this._TransactionPrimaryChannelName;
      } else if (name.equals("TransactionPublicChannelName")) {
         return this._TransactionPublicChannelName;
      } else if (name.equals("TransactionPublicSecureChannelName")) {
         return this._TransactionPublicSecureChannelName;
      } else if (name.equals("TransactionSecureChannelName")) {
         return this._TransactionSecureChannelName;
      } else if (name.equals("TunnelingClientPingSecs")) {
         return new Integer(this._TunnelingClientPingSecs);
      } else if (name.equals("TunnelingClientTimeoutSecs")) {
         return new Integer(this._TunnelingClientTimeoutSecs);
      } else if (name.equals("TunnelingEnabled")) {
         return new Boolean(this._TunnelingEnabled);
      } else if (name.equals("UploadDirectoryName")) {
         return this._UploadDirectoryName;
      } else if (name.equals("UseFusionForLLR")) {
         return new Boolean(this._UseFusionForLLR);
      } else if (name.equals("VerboseEJBDeploymentEnabled")) {
         return this._VerboseEJBDeploymentEnabled;
      } else if (name.equals("VirtualMachineName")) {
         return this._VirtualMachineName;
      } else if (name.equals("WebServer")) {
         return this._WebServer;
      } else if (name.equals("WebService")) {
         return this._WebService;
      } else if (name.equals("WeblogicPluginEnabled")) {
         return new Boolean(this._WeblogicPluginEnabled);
      } else if (name.equals("XMLEntityCache")) {
         return this._XMLEntityCache;
      } else if (name.equals("XMLRegistry")) {
         return this._XMLRegistry;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("TransactionLogFileWritePolicy", "Direct-Write");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property TransactionLogFileWritePolicy in ServerTemplateMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends KernelMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("com")) {
                  return 117;
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
            case 42:
            case 43:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            default:
               break;
            case 6:
               if (s.equals("parent")) {
                  return 231;
               }
               break;
            case 7:
               if (s.equals("cluster")) {
                  return 95;
               }

               if (s.equals("machine")) {
                  return 91;
               }
               break;
            case 9:
               if (s.equals("buzz-port")) {
                  return 224;
               }
               break;
            case 10:
               if (s.equals("key-stores")) {
                  return 187;
               }

               if (s.equals("web-server")) {
                  return 101;
               }
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 222;
               }

               if (s.equals("listen-port")) {
                  return 92;
               }

               if (s.equals("restart-max")) {
                  return 168;
               }

               if (s.equals("web-service")) {
                  return 216;
               }

               if (s.equals("com-enabled")) {
                  return 115;
               }
               break;
            case 12:
               if (s.equals("auto-restart")) {
                  return 165;
               }

               if (s.equals("buzz-address")) {
                  return 225;
               }

               if (s.equals("kernel-debug")) {
                  return 51;
               }

               if (s.equals("server-debug")) {
                  return 118;
               }

               if (s.equals("server-names")) {
                  return 88;
               }

               if (s.equals("server-start")) {
                  return 154;
               }

               if (s.equals("staging-mode")) {
                  return 164;
               }

               if (s.equals("startup-mode")) {
                  return 176;
               }

               if (s.equals("xml-registry")) {
                  return 137;
               }

               if (s.equals("buzz-enabled")) {
                  return 226;
               }

               if (s.equals("iiop-enabled")) {
                  return 107;
               }

               if (s.equals("jrmp-enabled")) {
                  return 116;
               }
               break;
            case 13:
               if (s.equals("java-compiler")) {
                  return 139;
               }

               if (s.equals("login-timeout")) {
                  return 94;
               }

               if (s.equals("stdout-format")) {
                  return 64;
               }

               if (s.equals("httpd-enabled")) {
                  return 119;
               }

               if (s.equals("tgiop-enabled")) {
                  return 111;
               }
               break;
            case 14:
               if (s.equals("accept-backlog")) {
                  return 130;
               }

               if (s.equals("cluster-weight")) {
                  return 96;
               }

               if (s.equals("listen-address")) {
                  return 125;
               }

               if (s.equals("root-directory")) {
                  return 90;
               }

               if (s.equals("server-version")) {
                  return 175;
               }

               if (s.equals("stdout-enabled")) {
                  return 56;
               }
               break;
            case 15:
               if (s.equals("expected-to-run")) {
                  return 102;
               }

               if (s.equals("resolvedns-name")) {
                  return 127;
               }

               if (s.equals("startup-timeout")) {
                  return 178;
               }

               if (s.equals("system-password")) {
                  return 120;
               }
               break;
            case 16:
               if (s.equals("defaultiiop-user")) {
                  return 108;
               }

               if (s.equals("externaldns-name")) {
                  return 126;
               }

               if (s.equals("thread-pool-size")) {
                  return 15;
               }

               if (s.equals("xml-entity-cache")) {
                  return 138;
               }

               if (s.equals("stdout-log-stack")) {
                  return 65;
               }
               break;
            case 17:
               if (s.equals("candidate-machine")) {
                  return 201;
               }

               if (s.equals("defaulttgiop-user")) {
                  return 112;
               }

               if (s.equals("interface-address")) {
                  return 128;
               }

               if (s.equals("listen-delay-secs")) {
                  return 155;
               }

               if (s.equals("replication-group")) {
                  return 97;
               }

               if (s.equals("replication-ports")) {
                  return 220;
               }

               if (s.equals("tunneling-enabled")) {
                  return 148;
               }

               if (s.equals("use-fusion-forllr")) {
                  return 204;
               }
               break;
            case 18:
               if (s.equals("default-file-store")) {
                  return 200;
               }

               if (s.equals("extra-ejbc-options")) {
                  return 143;
               }

               if (s.equals("extra-rmic-options")) {
                  return 142;
               }

               if (s.equals("jdbcllr-table-name")) {
                  return 203;
               }

               if (s.equals("jdbc-log-file-name")) {
                  return 104;
               }

               if (s.equals("supported-protocol")) {
                  return 211;
               }
               break;
            case 19:
               if (s.equals("administration-port")) {
                  return 134;
               }

               if (s.equals("auto-kill-if-failed")) {
                  return 166;
               }

               if (s.equals("federation-services")) {
                  return 214;
               }

               if (s.equals("overload-protection")) {
                  return 202;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("listen-port-enabled")) {
                  return 93;
               }

               if (s.equals("sit-config-required")) {
                  return 240;
               }
               break;
            case 20:
               if (s.equals("defaultiiop-password")) {
                  return 109;
               }

               if (s.equals("listeners-bind-early")) {
                  return 124;
               }

               if (s.equals("login-timeout-millis")) {
                  return 132;
               }

               if (s.equals("network-access-point")) {
                  return 129;
               }

               if (s.equals("virtual-machine-name")) {
                  return 219;
               }

               if (s.equals("jdbc-logging-enabled")) {
                  return 103;
               }

               if (s.equals("stdout-debug-enabled")) {
                  return 58;
               }
               break;
            case 21:
               if (s.equals("active-directory-name")) {
                  return 163;
               }

               if (s.equals("defaulttgiop-password")) {
                  return 113;
               }

               if (s.equals("iiop-connection-pools")) {
                  return 136;
               }

               if (s.equals("jta-migratable-target")) {
                  return 156;
               }

               if (s.equals("restart-delay-seconds")) {
                  return 172;
               }

               if (s.equals("stdout-severity-level")) {
                  return 57;
               }

               if (s.equals("upload-directory-name")) {
                  return 162;
               }

               if (s.equals("console-input-enabled")) {
                  return 122;
               }
               break;
            case 22:
               if (s.equals("configuration-property")) {
                  return 89;
               }

               if (s.equals("low-memorygc-threshold")) {
                  return 160;
               }

               if (s.equals("low-memory-sample-size")) {
                  return 158;
               }

               if (s.equals("staging-directory-name")) {
                  return 161;
               }

               if (s.equals("auto-migration-enabled")) {
                  return 100;
               }

               if (s.equals("j2ee13-warning-enabled")) {
                  return 106;
               }
               break;
            case 23:
               if (s.equals("coherence-member-config")) {
                  return 223;
               }

               if (s.equals("single-sign-on-services")) {
                  return 215;
               }

               if (s.equals("weblogic-plugin-enabled")) {
                  return 184;
               }
               break;
            case 24:
               if (s.equals("default-staging-dir-name")) {
                  return 212;
               }

               if (s.equals("low-memory-time-interval")) {
                  return 157;
               }

               if (s.equals("reliable-delivery-policy")) {
                  return 198;
               }

               if (s.equals("restart-interval-seconds")) {
                  return 167;
               }

               if (s.equals("server-diagnostic-config")) {
                  return 209;
               }

               if (s.equals("j2ee12-only-mode-enabled")) {
                  return 105;
               }
               break;
            case 25:
               if (s.equals("graceful-shutdown-timeout")) {
                  return 179;
               }

               if (s.equals("hosts-migratable-services")) {
                  return 185;
               }

               if (s.equals("preferred-secondary-group")) {
                  return 98;
               }

               if (s.equals("system-password-encrypted")) {
                  return 121;
               }

               if (s.equals("client-cert-proxy-enabled")) {
                  return 183;
               }

               if (s.equals("message-id-prefix-enabled")) {
                  return 199;
               }
               break;
            case 26:
               if (s.equals("auto-jdbc-connection-close")) {
                  return 210;
               }

               if (s.equals("jdbc-login-timeout-seconds")) {
                  return 208;
               }

               if (s.equals("max-concurrent-new-threads")) {
                  return 227;
               }

               if (s.equals("transaction-log-jdbc-store")) {
                  return 221;
               }

               if (s.equals("tunneling-client-ping-secs")) {
                  return 149;
               }

               if (s.equals("classpath-servlet-disabled")) {
                  return 173;
               }

               if (s.equals("http-trace-support-enabled")) {
                  return 186;
               }
               break;
            case 27:
               if (s.equals("custom-trust-key-store-type")) {
                  return 193;
               }

               if (s.equals("sit-config-polling-interval")) {
                  return 233;
               }

               if (s.equals("transaction-log-file-prefix")) {
                  return 145;
               }

               if (s.equals("administration-port-enabled")) {
                  return 133;
               }
               break;
            case 28:
               if (s.equals("consensus-process-identifier")) {
                  return 99;
               }

               if (s.equals("health-check-timeout-seconds")) {
                  return 170;
               }

               if (s.equals("jdbcllr-tablexid-column-size")) {
                  return 205;
               }

               if (s.equals("java-compiler-pre-class-path")) {
                  return 140;
               }

               if (s.equals("low-memory-granularity-level")) {
                  return 159;
               }

               if (s.equals("max-backoff-between-failures")) {
                  return 131;
               }

               if (s.equals("msi-file-replication-enabled")) {
                  return 182;
               }
               break;
            case 29:
               if (s.equals("health-check-interval-seconds")) {
                  return 169;
               }

               if (s.equals("java-compiler-post-class-path")) {
                  return 141;
               }

               if (s.equals("num-of-retries-beforemsi-mode")) {
                  return 229;
               }

               if (s.equals("retry-interval-beforemsi-mode")) {
                  return 230;
               }

               if (s.equals("server-life-cycle-timeout-val")) {
                  return 177;
               }

               if (s.equals("tunneling-client-timeout-secs")) {
                  return 150;
               }

               if (s.equals("verboseejb-deployment-enabled")) {
                  return 144;
               }

               if (s.equals("network-class-loading-enabled")) {
                  return 147;
               }
               break;
            case 30:
               if (s.equals("custom-identity-key-store-type")) {
                  return 189;
               }

               if (s.equals("defaultiiop-password-encrypted")) {
                  return 110;
               }

               if (s.equals("jdbcllr-table-pool-column-size")) {
                  return 206;
               }

               if (s.equals("listen-thread-start-delay-secs")) {
                  return 123;
               }
               break;
            case 31:
               if (s.equals("defaulttgiop-password-encrypted")) {
                  return 114;
               }

               if (s.equals("transaction-public-channel-name")) {
                  return 238;
               }

               if (s.equals("transaction-secure-channel-name")) {
                  return 237;
               }

               if (s.equals("ignore-sessions-during-shutdown")) {
                  return 180;
               }
               break;
            case 32:
               if (s.equals("admin-reconnect-interval-seconds")) {
                  return 151;
               }

               if (s.equals("custom-trust-key-store-file-name")) {
                  return 192;
               }

               if (s.equals("health-check-start-delay-seconds")) {
                  return 171;
               }

               if (s.equals("jdbcllr-table-record-column-size")) {
                  return 207;
               }

               if (s.equals("transaction-primary-channel-name")) {
                  return 236;
               }
               break;
            case 33:
               if (s.equals("81-style-default-staging-dir-name")) {
                  return 213;
               }

               if (s.equals("coherence-cluster-system-resource")) {
                  return 218;
               }

               if (s.equals("transaction-log-file-write-policy")) {
                  return 146;
               }

               if (s.equals("cleanup-orphaned-sessions-enabled")) {
                  return 235;
               }
               break;
            case 34:
               if (s.equals("custom-trust-key-store-pass-phrase")) {
                  return 194;
               }

               if (s.equals("nm-socket-create-timeout-in-millis")) {
                  return 217;
               }

               if (s.equals("default-internal-servlets-disabled")) {
                  return 174;
               }
               break;
            case 35:
               if (s.equals("custom-identity-key-store-file-name")) {
                  return 188;
               }

               if (s.equals("managed-server-independence-enabled")) {
                  return 181;
               }
               break;
            case 36:
               if (s.equals("max-concurrent-long-running-requests")) {
                  return 228;
               }
               break;
            case 37:
               if (s.equals("custom-identity-key-store-pass-phrase")) {
                  return 190;
               }

               if (s.equals("classpath-servlet-secure-mode-enabled")) {
                  return 232;
               }
               break;
            case 38:
               if (s.equals("jndi-transportable-object-factory-list")) {
                  return 135;
               }

               if (s.equals("transaction-public-secure-channel-name")) {
                  return 239;
               }
               break;
            case 39:
               if (s.equals("session-replication-on-shutdown-enabled")) {
                  return 234;
               }
               break;
            case 40:
               if (s.equals("jms-default-connection-factories-enabled")) {
                  return 152;
               }
               break;
            case 41:
               if (s.equals("java-standard-trust-key-store-pass-phrase")) {
                  return 196;
               }
               break;
            case 44:
               if (s.equals("custom-trust-key-store-pass-phrase-encrypted")) {
                  return 195;
               }

               if (s.equals("jms-connection-factory-unmapped-res-ref-mode")) {
                  return 153;
               }
               break;
            case 47:
               if (s.equals("custom-identity-key-store-pass-phrase-encrypted")) {
                  return 191;
               }
               break;
            case 51:
               if (s.equals("java-standard-trust-key-store-pass-phrase-encrypted")) {
                  return 197;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 53:
               return new SSLMBeanImpl.SchemaHelper2();
            case 54:
               return new IIOPMBeanImpl.SchemaHelper2();
            case 55:
               return new LogMBeanImpl.SchemaHelper2();
            case 62:
               return new ExecuteQueueMBeanImpl.SchemaHelper2();
            case 89:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            case 101:
               return new WebServerMBeanImpl.SchemaHelper2();
            case 117:
               return new COMMBeanImpl.SchemaHelper2();
            case 118:
               return new ServerDebugMBeanImpl.SchemaHelper2();
            case 129:
               return new NetworkAccessPointMBeanImpl.SchemaHelper2();
            case 154:
               return new ServerStartMBeanImpl.SchemaHelper2();
            case 156:
               return new JTAMigratableTargetMBeanImpl.SchemaHelper2();
            case 200:
               return new DefaultFileStoreMBeanImpl.SchemaHelper2();
            case 202:
               return new OverloadProtectionMBeanImpl.SchemaHelper2();
            case 209:
               return new WLDFServerDiagnosticMBeanImpl.SchemaHelper2();
            case 214:
               return new FederationServicesMBeanImpl.SchemaHelper2();
            case 215:
               return new SingleSignOnServicesMBeanImpl.SchemaHelper2();
            case 216:
               return new WebServiceMBeanImpl.SchemaHelper2();
            case 221:
               return new TransactionLogJDBCStoreMBeanImpl.SchemaHelper2();
            case 222:
               return new DataSourceMBeanImpl.SchemaHelper2();
            case 223:
               return new CoherenceMemberConfigMBeanImpl.SchemaHelper2();
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
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
            case 52:
            case 53:
            case 54:
            case 55:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
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
            case 84:
            case 85:
            case 86:
            case 87:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 15:
               return "thread-pool-size";
            case 51:
               return "kernel-debug";
            case 56:
               return "stdout-enabled";
            case 57:
               return "stdout-severity-level";
            case 58:
               return "stdout-debug-enabled";
            case 64:
               return "stdout-format";
            case 65:
               return "stdout-log-stack";
            case 88:
               return "server-names";
            case 89:
               return "configuration-property";
            case 90:
               return "root-directory";
            case 91:
               return "machine";
            case 92:
               return "listen-port";
            case 93:
               return "listen-port-enabled";
            case 94:
               return "login-timeout";
            case 95:
               return "cluster";
            case 96:
               return "cluster-weight";
            case 97:
               return "replication-group";
            case 98:
               return "preferred-secondary-group";
            case 99:
               return "consensus-process-identifier";
            case 100:
               return "auto-migration-enabled";
            case 101:
               return "web-server";
            case 102:
               return "expected-to-run";
            case 103:
               return "jdbc-logging-enabled";
            case 104:
               return "jdbc-log-file-name";
            case 105:
               return "j2ee12-only-mode-enabled";
            case 106:
               return "j2ee13-warning-enabled";
            case 107:
               return "iiop-enabled";
            case 108:
               return "defaultiiop-user";
            case 109:
               return "defaultiiop-password";
            case 110:
               return "defaultiiop-password-encrypted";
            case 111:
               return "tgiop-enabled";
            case 112:
               return "defaulttgiop-user";
            case 113:
               return "defaulttgiop-password";
            case 114:
               return "defaulttgiop-password-encrypted";
            case 115:
               return "com-enabled";
            case 116:
               return "jrmp-enabled";
            case 117:
               return "com";
            case 118:
               return "server-debug";
            case 119:
               return "httpd-enabled";
            case 120:
               return "system-password";
            case 121:
               return "system-password-encrypted";
            case 122:
               return "console-input-enabled";
            case 123:
               return "listen-thread-start-delay-secs";
            case 124:
               return "listeners-bind-early";
            case 125:
               return "listen-address";
            case 126:
               return "externaldns-name";
            case 127:
               return "resolvedns-name";
            case 128:
               return "interface-address";
            case 129:
               return "network-access-point";
            case 130:
               return "accept-backlog";
            case 131:
               return "max-backoff-between-failures";
            case 132:
               return "login-timeout-millis";
            case 133:
               return "administration-port-enabled";
            case 134:
               return "administration-port";
            case 135:
               return "jndi-transportable-object-factory-list";
            case 136:
               return "iiop-connection-pools";
            case 137:
               return "xml-registry";
            case 138:
               return "xml-entity-cache";
            case 139:
               return "java-compiler";
            case 140:
               return "java-compiler-pre-class-path";
            case 141:
               return "java-compiler-post-class-path";
            case 142:
               return "extra-rmic-options";
            case 143:
               return "extra-ejbc-options";
            case 144:
               return "verboseejb-deployment-enabled";
            case 145:
               return "transaction-log-file-prefix";
            case 146:
               return "transaction-log-file-write-policy";
            case 147:
               return "network-class-loading-enabled";
            case 148:
               return "tunneling-enabled";
            case 149:
               return "tunneling-client-ping-secs";
            case 150:
               return "tunneling-client-timeout-secs";
            case 151:
               return "admin-reconnect-interval-seconds";
            case 152:
               return "jms-default-connection-factories-enabled";
            case 153:
               return "jms-connection-factory-unmapped-res-ref-mode";
            case 154:
               return "server-start";
            case 155:
               return "listen-delay-secs";
            case 156:
               return "jta-migratable-target";
            case 157:
               return "low-memory-time-interval";
            case 158:
               return "low-memory-sample-size";
            case 159:
               return "low-memory-granularity-level";
            case 160:
               return "low-memorygc-threshold";
            case 161:
               return "staging-directory-name";
            case 162:
               return "upload-directory-name";
            case 163:
               return "active-directory-name";
            case 164:
               return "staging-mode";
            case 165:
               return "auto-restart";
            case 166:
               return "auto-kill-if-failed";
            case 167:
               return "restart-interval-seconds";
            case 168:
               return "restart-max";
            case 169:
               return "health-check-interval-seconds";
            case 170:
               return "health-check-timeout-seconds";
            case 171:
               return "health-check-start-delay-seconds";
            case 172:
               return "restart-delay-seconds";
            case 173:
               return "classpath-servlet-disabled";
            case 174:
               return "default-internal-servlets-disabled";
            case 175:
               return "server-version";
            case 176:
               return "startup-mode";
            case 177:
               return "server-life-cycle-timeout-val";
            case 178:
               return "startup-timeout";
            case 179:
               return "graceful-shutdown-timeout";
            case 180:
               return "ignore-sessions-during-shutdown";
            case 181:
               return "managed-server-independence-enabled";
            case 182:
               return "msi-file-replication-enabled";
            case 183:
               return "client-cert-proxy-enabled";
            case 184:
               return "weblogic-plugin-enabled";
            case 185:
               return "hosts-migratable-services";
            case 186:
               return "http-trace-support-enabled";
            case 187:
               return "key-stores";
            case 188:
               return "custom-identity-key-store-file-name";
            case 189:
               return "custom-identity-key-store-type";
            case 190:
               return "custom-identity-key-store-pass-phrase";
            case 191:
               return "custom-identity-key-store-pass-phrase-encrypted";
            case 192:
               return "custom-trust-key-store-file-name";
            case 193:
               return "custom-trust-key-store-type";
            case 194:
               return "custom-trust-key-store-pass-phrase";
            case 195:
               return "custom-trust-key-store-pass-phrase-encrypted";
            case 196:
               return "java-standard-trust-key-store-pass-phrase";
            case 197:
               return "java-standard-trust-key-store-pass-phrase-encrypted";
            case 198:
               return "reliable-delivery-policy";
            case 199:
               return "message-id-prefix-enabled";
            case 200:
               return "default-file-store";
            case 201:
               return "candidate-machine";
            case 202:
               return "overload-protection";
            case 203:
               return "jdbcllr-table-name";
            case 204:
               return "use-fusion-forllr";
            case 205:
               return "jdbcllr-tablexid-column-size";
            case 206:
               return "jdbcllr-table-pool-column-size";
            case 207:
               return "jdbcllr-table-record-column-size";
            case 208:
               return "jdbc-login-timeout-seconds";
            case 209:
               return "server-diagnostic-config";
            case 210:
               return "auto-jdbc-connection-close";
            case 211:
               return "supported-protocol";
            case 212:
               return "default-staging-dir-name";
            case 213:
               return "81-style-default-staging-dir-name";
            case 214:
               return "federation-services";
            case 215:
               return "single-sign-on-services";
            case 216:
               return "web-service";
            case 217:
               return "nm-socket-create-timeout-in-millis";
            case 218:
               return "coherence-cluster-system-resource";
            case 219:
               return "virtual-machine-name";
            case 220:
               return "replication-ports";
            case 221:
               return "transaction-log-jdbc-store";
            case 222:
               return "data-source";
            case 223:
               return "coherence-member-config";
            case 224:
               return "buzz-port";
            case 225:
               return "buzz-address";
            case 226:
               return "buzz-enabled";
            case 227:
               return "max-concurrent-new-threads";
            case 228:
               return "max-concurrent-long-running-requests";
            case 229:
               return "num-of-retries-beforemsi-mode";
            case 230:
               return "retry-interval-beforemsi-mode";
            case 231:
               return "parent";
            case 232:
               return "classpath-servlet-secure-mode-enabled";
            case 233:
               return "sit-config-polling-interval";
            case 234:
               return "session-replication-on-shutdown-enabled";
            case 235:
               return "cleanup-orphaned-sessions-enabled";
            case 236:
               return "transaction-primary-channel-name";
            case 237:
               return "transaction-secure-channel-name";
            case 238:
               return "transaction-public-channel-name";
            case 239:
               return "transaction-public-secure-channel-name";
            case 240:
               return "sit-config-required";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 62:
               return true;
            case 89:
               return true;
            case 129:
               return true;
            case 135:
               return true;
            case 201:
               return true;
            case 211:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 62:
               return true;
            case 89:
               return true;
            case 101:
               return true;
            case 117:
               return true;
            case 118:
               return true;
            case 129:
               return true;
            case 154:
               return true;
            case 156:
               return true;
            case 200:
               return true;
            case 202:
               return true;
            case 209:
               return true;
            case 214:
               return true;
            case 215:
               return true;
            case 216:
               return true;
            case 221:
               return true;
            case 222:
               return true;
            case 223:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 31:
               return true;
            case 91:
               return true;
            case 92:
               return true;
            case 95:
               return true;
            case 96:
               return true;
            case 99:
               return true;
            case 102:
               return true;
            case 126:
               return true;
            case 127:
               return true;
            case 128:
               return true;
            case 137:
               return true;
            case 138:
               return true;
            case 147:
               return true;
            case 151:
               return true;
            case 155:
               return true;
            case 157:
               return true;
            case 158:
               return true;
            case 159:
               return true;
            case 160:
               return true;
            case 165:
               return true;
            case 166:
               return true;
            case 167:
               return true;
            case 168:
               return true;
            case 169:
               return true;
            case 170:
               return true;
            case 171:
               return true;
            case 172:
               return true;
            case 176:
               return true;
            case 177:
               return true;
            case 178:
               return true;
            case 179:
               return true;
            case 180:
               return true;
            case 185:
               return true;
            case 198:
               return true;
            case 199:
               return true;
            case 221:
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

   protected static class Helper extends KernelMBeanImpl.Helper {
      private ServerTemplateMBeanImpl bean;

      protected Helper(ServerTemplateMBeanImpl bean) {
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
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
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
            case 52:
            case 53:
            case 54:
            case 55:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
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
            case 84:
            case 85:
            case 86:
            case 87:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 15:
               return "ThreadPoolSize";
            case 51:
               return "KernelDebug";
            case 56:
               return "StdoutEnabled";
            case 57:
               return "StdoutSeverityLevel";
            case 58:
               return "StdoutDebugEnabled";
            case 64:
               return "StdoutFormat";
            case 65:
               return "StdoutLogStack";
            case 88:
               return "ServerNames";
            case 89:
               return "ConfigurationProperties";
            case 90:
               return "RootDirectory";
            case 91:
               return "Machine";
            case 92:
               return "ListenPort";
            case 93:
               return "ListenPortEnabled";
            case 94:
               return "LoginTimeout";
            case 95:
               return "Cluster";
            case 96:
               return "ClusterWeight";
            case 97:
               return "ReplicationGroup";
            case 98:
               return "PreferredSecondaryGroup";
            case 99:
               return "ConsensusProcessIdentifier";
            case 100:
               return "AutoMigrationEnabled";
            case 101:
               return "WebServer";
            case 102:
               return "ExpectedToRun";
            case 103:
               return "JDBCLoggingEnabled";
            case 104:
               return "JDBCLogFileName";
            case 105:
               return "J2EE12OnlyModeEnabled";
            case 106:
               return "J2EE13WarningEnabled";
            case 107:
               return "IIOPEnabled";
            case 108:
               return "DefaultIIOPUser";
            case 109:
               return "DefaultIIOPPassword";
            case 110:
               return "DefaultIIOPPasswordEncrypted";
            case 111:
               return "TGIOPEnabled";
            case 112:
               return "DefaultTGIOPUser";
            case 113:
               return "DefaultTGIOPPassword";
            case 114:
               return "DefaultTGIOPPasswordEncrypted";
            case 115:
               return "COMEnabled";
            case 116:
               return "JRMPEnabled";
            case 117:
               return "COM";
            case 118:
               return "ServerDebug";
            case 119:
               return "HttpdEnabled";
            case 120:
               return "SystemPassword";
            case 121:
               return "SystemPasswordEncrypted";
            case 122:
               return "ConsoleInputEnabled";
            case 123:
               return "ListenThreadStartDelaySecs";
            case 124:
               return "ListenersBindEarly";
            case 125:
               return "ListenAddress";
            case 126:
               return "ExternalDNSName";
            case 127:
               return "ResolveDNSName";
            case 128:
               return "InterfaceAddress";
            case 129:
               return "NetworkAccessPoints";
            case 130:
               return "AcceptBacklog";
            case 131:
               return "MaxBackoffBetweenFailures";
            case 132:
               return "LoginTimeoutMillis";
            case 133:
               return "AdministrationPortEnabled";
            case 134:
               return "AdministrationPort";
            case 135:
               return "JNDITransportableObjectFactoryList";
            case 136:
               return "IIOPConnectionPools";
            case 137:
               return "XMLRegistry";
            case 138:
               return "XMLEntityCache";
            case 139:
               return "JavaCompiler";
            case 140:
               return "JavaCompilerPreClassPath";
            case 141:
               return "JavaCompilerPostClassPath";
            case 142:
               return "ExtraRmicOptions";
            case 143:
               return "ExtraEjbcOptions";
            case 144:
               return "VerboseEJBDeploymentEnabled";
            case 145:
               return "TransactionLogFilePrefix";
            case 146:
               return "TransactionLogFileWritePolicy";
            case 147:
               return "NetworkClassLoadingEnabled";
            case 148:
               return "TunnelingEnabled";
            case 149:
               return "TunnelingClientPingSecs";
            case 150:
               return "TunnelingClientTimeoutSecs";
            case 151:
               return "AdminReconnectIntervalSeconds";
            case 152:
               return "JMSDefaultConnectionFactoriesEnabled";
            case 153:
               return "JMSConnectionFactoryUnmappedResRefMode";
            case 154:
               return "ServerStart";
            case 155:
               return "ListenDelaySecs";
            case 156:
               return "JTAMigratableTarget";
            case 157:
               return "LowMemoryTimeInterval";
            case 158:
               return "LowMemorySampleSize";
            case 159:
               return "LowMemoryGranularityLevel";
            case 160:
               return "LowMemoryGCThreshold";
            case 161:
               return "StagingDirectoryName";
            case 162:
               return "UploadDirectoryName";
            case 163:
               return "ActiveDirectoryName";
            case 164:
               return "StagingMode";
            case 165:
               return "AutoRestart";
            case 166:
               return "AutoKillIfFailed";
            case 167:
               return "RestartIntervalSeconds";
            case 168:
               return "RestartMax";
            case 169:
               return "HealthCheckIntervalSeconds";
            case 170:
               return "HealthCheckTimeoutSeconds";
            case 171:
               return "HealthCheckStartDelaySeconds";
            case 172:
               return "RestartDelaySeconds";
            case 173:
               return "ClasspathServletDisabled";
            case 174:
               return "DefaultInternalServletsDisabled";
            case 175:
               return "ServerVersion";
            case 176:
               return "StartupMode";
            case 177:
               return "ServerLifeCycleTimeoutVal";
            case 178:
               return "StartupTimeout";
            case 179:
               return "GracefulShutdownTimeout";
            case 180:
               return "IgnoreSessionsDuringShutdown";
            case 181:
               return "ManagedServerIndependenceEnabled";
            case 182:
               return "MSIFileReplicationEnabled";
            case 183:
               return "ClientCertProxyEnabled";
            case 184:
               return "WeblogicPluginEnabled";
            case 185:
               return "HostsMigratableServices";
            case 186:
               return "HttpTraceSupportEnabled";
            case 187:
               return "KeyStores";
            case 188:
               return "CustomIdentityKeyStoreFileName";
            case 189:
               return "CustomIdentityKeyStoreType";
            case 190:
               return "CustomIdentityKeyStorePassPhrase";
            case 191:
               return "CustomIdentityKeyStorePassPhraseEncrypted";
            case 192:
               return "CustomTrustKeyStoreFileName";
            case 193:
               return "CustomTrustKeyStoreType";
            case 194:
               return "CustomTrustKeyStorePassPhrase";
            case 195:
               return "CustomTrustKeyStorePassPhraseEncrypted";
            case 196:
               return "JavaStandardTrustKeyStorePassPhrase";
            case 197:
               return "JavaStandardTrustKeyStorePassPhraseEncrypted";
            case 198:
               return "ReliableDeliveryPolicy";
            case 199:
               return "MessageIdPrefixEnabled";
            case 200:
               return "DefaultFileStore";
            case 201:
               return "CandidateMachines";
            case 202:
               return "OverloadProtection";
            case 203:
               return "JDBCLLRTableName";
            case 204:
               return "UseFusionForLLR";
            case 205:
               return "JDBCLLRTableXIDColumnSize";
            case 206:
               return "JDBCLLRTablePoolColumnSize";
            case 207:
               return "JDBCLLRTableRecordColumnSize";
            case 208:
               return "JDBCLoginTimeoutSeconds";
            case 209:
               return "ServerDiagnosticConfig";
            case 210:
               return "AutoJDBCConnectionClose";
            case 211:
               return "SupportedProtocols";
            case 212:
               return "DefaultStagingDirName";
            case 213:
               return "81StyleDefaultStagingDirName";
            case 214:
               return "FederationServices";
            case 215:
               return "SingleSignOnServices";
            case 216:
               return "WebService";
            case 217:
               return "NMSocketCreateTimeoutInMillis";
            case 218:
               return "CoherenceClusterSystemResource";
            case 219:
               return "VirtualMachineName";
            case 220:
               return "ReplicationPorts";
            case 221:
               return "TransactionLogJDBCStore";
            case 222:
               return "DataSource";
            case 223:
               return "CoherenceMemberConfig";
            case 224:
               return "BuzzPort";
            case 225:
               return "BuzzAddress";
            case 226:
               return "BuzzEnabled";
            case 227:
               return "MaxConcurrentNewThreads";
            case 228:
               return "MaxConcurrentLongRunningRequests";
            case 229:
               return "NumOfRetriesBeforeMSIMode";
            case 230:
               return "RetryIntervalBeforeMSIMode";
            case 231:
               return "Parent";
            case 232:
               return "ClasspathServletSecureModeEnabled";
            case 233:
               return "SitConfigPollingInterval";
            case 234:
               return "SessionReplicationOnShutdownEnabled";
            case 235:
               return "CleanupOrphanedSessionsEnabled";
            case 236:
               return "TransactionPrimaryChannelName";
            case 237:
               return "TransactionSecureChannelName";
            case 238:
               return "TransactionPublicChannelName";
            case 239:
               return "TransactionPublicSecureChannelName";
            case 240:
               return "SitConfigRequired";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("81StyleDefaultStagingDirName")) {
            return 213;
         } else if (propName.equals("AcceptBacklog")) {
            return 130;
         } else if (propName.equals("ActiveDirectoryName")) {
            return 163;
         } else if (propName.equals("AdminReconnectIntervalSeconds")) {
            return 151;
         } else if (propName.equals("AdministrationPort")) {
            return 134;
         } else if (propName.equals("AutoJDBCConnectionClose")) {
            return 210;
         } else if (propName.equals("AutoKillIfFailed")) {
            return 166;
         } else if (propName.equals("AutoRestart")) {
            return 165;
         } else if (propName.equals("BuzzAddress")) {
            return 225;
         } else if (propName.equals("BuzzPort")) {
            return 224;
         } else if (propName.equals("COM")) {
            return 117;
         } else if (propName.equals("CandidateMachines")) {
            return 201;
         } else if (propName.equals("Cluster")) {
            return 95;
         } else if (propName.equals("ClusterWeight")) {
            return 96;
         } else if (propName.equals("CoherenceClusterSystemResource")) {
            return 218;
         } else if (propName.equals("CoherenceMemberConfig")) {
            return 223;
         } else if (propName.equals("ConfigurationProperties")) {
            return 89;
         } else if (propName.equals("ConsensusProcessIdentifier")) {
            return 99;
         } else if (propName.equals("CustomIdentityKeyStoreFileName")) {
            return 188;
         } else if (propName.equals("CustomIdentityKeyStorePassPhrase")) {
            return 190;
         } else if (propName.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
            return 191;
         } else if (propName.equals("CustomIdentityKeyStoreType")) {
            return 189;
         } else if (propName.equals("CustomTrustKeyStoreFileName")) {
            return 192;
         } else if (propName.equals("CustomTrustKeyStorePassPhrase")) {
            return 194;
         } else if (propName.equals("CustomTrustKeyStorePassPhraseEncrypted")) {
            return 195;
         } else if (propName.equals("CustomTrustKeyStoreType")) {
            return 193;
         } else if (propName.equals("DataSource")) {
            return 222;
         } else if (propName.equals("DefaultFileStore")) {
            return 200;
         } else if (propName.equals("DefaultIIOPPassword")) {
            return 109;
         } else if (propName.equals("DefaultIIOPPasswordEncrypted")) {
            return 110;
         } else if (propName.equals("DefaultIIOPUser")) {
            return 108;
         } else if (propName.equals("DefaultStagingDirName")) {
            return 212;
         } else if (propName.equals("DefaultTGIOPPassword")) {
            return 113;
         } else if (propName.equals("DefaultTGIOPPasswordEncrypted")) {
            return 114;
         } else if (propName.equals("DefaultTGIOPUser")) {
            return 112;
         } else if (propName.equals("ExpectedToRun")) {
            return 102;
         } else if (propName.equals("ExternalDNSName")) {
            return 126;
         } else if (propName.equals("ExtraEjbcOptions")) {
            return 143;
         } else if (propName.equals("ExtraRmicOptions")) {
            return 142;
         } else if (propName.equals("FederationServices")) {
            return 214;
         } else if (propName.equals("GracefulShutdownTimeout")) {
            return 179;
         } else if (propName.equals("HealthCheckIntervalSeconds")) {
            return 169;
         } else if (propName.equals("HealthCheckStartDelaySeconds")) {
            return 171;
         } else if (propName.equals("HealthCheckTimeoutSeconds")) {
            return 170;
         } else if (propName.equals("HostsMigratableServices")) {
            return 185;
         } else if (propName.equals("IIOPConnectionPools")) {
            return 136;
         } else if (propName.equals("InterfaceAddress")) {
            return 128;
         } else if (propName.equals("JDBCLLRTableName")) {
            return 203;
         } else if (propName.equals("JDBCLLRTablePoolColumnSize")) {
            return 206;
         } else if (propName.equals("JDBCLLRTableRecordColumnSize")) {
            return 207;
         } else if (propName.equals("JDBCLLRTableXIDColumnSize")) {
            return 205;
         } else if (propName.equals("JDBCLogFileName")) {
            return 104;
         } else if (propName.equals("JDBCLoginTimeoutSeconds")) {
            return 208;
         } else if (propName.equals("JMSConnectionFactoryUnmappedResRefMode")) {
            return 153;
         } else if (propName.equals("JNDITransportableObjectFactoryList")) {
            return 135;
         } else if (propName.equals("JTAMigratableTarget")) {
            return 156;
         } else if (propName.equals("JavaCompiler")) {
            return 139;
         } else if (propName.equals("JavaCompilerPostClassPath")) {
            return 141;
         } else if (propName.equals("JavaCompilerPreClassPath")) {
            return 140;
         } else if (propName.equals("JavaStandardTrustKeyStorePassPhrase")) {
            return 196;
         } else if (propName.equals("JavaStandardTrustKeyStorePassPhraseEncrypted")) {
            return 197;
         } else if (propName.equals("KernelDebug")) {
            return 51;
         } else if (propName.equals("KeyStores")) {
            return 187;
         } else if (propName.equals("ListenAddress")) {
            return 125;
         } else if (propName.equals("ListenDelaySecs")) {
            return 155;
         } else if (propName.equals("ListenPort")) {
            return 92;
         } else if (propName.equals("ListenThreadStartDelaySecs")) {
            return 123;
         } else if (propName.equals("ListenersBindEarly")) {
            return 124;
         } else if (propName.equals("LoginTimeout")) {
            return 94;
         } else if (propName.equals("LoginTimeoutMillis")) {
            return 132;
         } else if (propName.equals("LowMemoryGCThreshold")) {
            return 160;
         } else if (propName.equals("LowMemoryGranularityLevel")) {
            return 159;
         } else if (propName.equals("LowMemorySampleSize")) {
            return 158;
         } else if (propName.equals("LowMemoryTimeInterval")) {
            return 157;
         } else if (propName.equals("Machine")) {
            return 91;
         } else if (propName.equals("MaxBackoffBetweenFailures")) {
            return 131;
         } else if (propName.equals("MaxConcurrentLongRunningRequests")) {
            return 228;
         } else if (propName.equals("MaxConcurrentNewThreads")) {
            return 227;
         } else if (propName.equals("NMSocketCreateTimeoutInMillis")) {
            return 217;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NetworkAccessPoints")) {
            return 129;
         } else if (propName.equals("NumOfRetriesBeforeMSIMode")) {
            return 229;
         } else if (propName.equals("OverloadProtection")) {
            return 202;
         } else if (propName.equals("Parent")) {
            return 231;
         } else if (propName.equals("PreferredSecondaryGroup")) {
            return 98;
         } else if (propName.equals("ReliableDeliveryPolicy")) {
            return 198;
         } else if (propName.equals("ReplicationGroup")) {
            return 97;
         } else if (propName.equals("ReplicationPorts")) {
            return 220;
         } else if (propName.equals("ResolveDNSName")) {
            return 127;
         } else if (propName.equals("RestartDelaySeconds")) {
            return 172;
         } else if (propName.equals("RestartIntervalSeconds")) {
            return 167;
         } else if (propName.equals("RestartMax")) {
            return 168;
         } else if (propName.equals("RetryIntervalBeforeMSIMode")) {
            return 230;
         } else if (propName.equals("RootDirectory")) {
            return 90;
         } else if (propName.equals("ServerDebug")) {
            return 118;
         } else if (propName.equals("ServerDiagnosticConfig")) {
            return 209;
         } else if (propName.equals("ServerLifeCycleTimeoutVal")) {
            return 177;
         } else if (propName.equals("ServerNames")) {
            return 88;
         } else if (propName.equals("ServerStart")) {
            return 154;
         } else if (propName.equals("ServerVersion")) {
            return 175;
         } else if (propName.equals("SingleSignOnServices")) {
            return 215;
         } else if (propName.equals("SitConfigPollingInterval")) {
            return 233;
         } else if (propName.equals("StagingDirectoryName")) {
            return 161;
         } else if (propName.equals("StagingMode")) {
            return 164;
         } else if (propName.equals("StartupMode")) {
            return 176;
         } else if (propName.equals("StartupTimeout")) {
            return 178;
         } else if (propName.equals("StdoutFormat")) {
            return 64;
         } else if (propName.equals("StdoutSeverityLevel")) {
            return 57;
         } else if (propName.equals("SupportedProtocols")) {
            return 211;
         } else if (propName.equals("SystemPassword")) {
            return 120;
         } else if (propName.equals("SystemPasswordEncrypted")) {
            return 121;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("ThreadPoolSize")) {
            return 15;
         } else if (propName.equals("TransactionLogFilePrefix")) {
            return 145;
         } else if (propName.equals("TransactionLogFileWritePolicy")) {
            return 146;
         } else if (propName.equals("TransactionLogJDBCStore")) {
            return 221;
         } else if (propName.equals("TransactionPrimaryChannelName")) {
            return 236;
         } else if (propName.equals("TransactionPublicChannelName")) {
            return 238;
         } else if (propName.equals("TransactionPublicSecureChannelName")) {
            return 239;
         } else if (propName.equals("TransactionSecureChannelName")) {
            return 237;
         } else if (propName.equals("TunnelingClientPingSecs")) {
            return 149;
         } else if (propName.equals("TunnelingClientTimeoutSecs")) {
            return 150;
         } else if (propName.equals("UploadDirectoryName")) {
            return 162;
         } else if (propName.equals("VerboseEJBDeploymentEnabled")) {
            return 144;
         } else if (propName.equals("VirtualMachineName")) {
            return 219;
         } else if (propName.equals("WebServer")) {
            return 101;
         } else if (propName.equals("WebService")) {
            return 216;
         } else if (propName.equals("XMLEntityCache")) {
            return 138;
         } else if (propName.equals("XMLRegistry")) {
            return 137;
         } else if (propName.equals("AdministrationPortEnabled")) {
            return 133;
         } else if (propName.equals("AutoMigrationEnabled")) {
            return 100;
         } else if (propName.equals("BuzzEnabled")) {
            return 226;
         } else if (propName.equals("COMEnabled")) {
            return 115;
         } else if (propName.equals("ClasspathServletDisabled")) {
            return 173;
         } else if (propName.equals("ClasspathServletSecureModeEnabled")) {
            return 232;
         } else if (propName.equals("CleanupOrphanedSessionsEnabled")) {
            return 235;
         } else if (propName.equals("ClientCertProxyEnabled")) {
            return 183;
         } else if (propName.equals("ConsoleInputEnabled")) {
            return 122;
         } else if (propName.equals("DefaultInternalServletsDisabled")) {
            return 174;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("HttpTraceSupportEnabled")) {
            return 186;
         } else if (propName.equals("HttpdEnabled")) {
            return 119;
         } else if (propName.equals("IIOPEnabled")) {
            return 107;
         } else if (propName.equals("IgnoreSessionsDuringShutdown")) {
            return 180;
         } else if (propName.equals("J2EE12OnlyModeEnabled")) {
            return 105;
         } else if (propName.equals("J2EE13WarningEnabled")) {
            return 106;
         } else if (propName.equals("JDBCLoggingEnabled")) {
            return 103;
         } else if (propName.equals("JMSDefaultConnectionFactoriesEnabled")) {
            return 152;
         } else if (propName.equals("JRMPEnabled")) {
            return 116;
         } else if (propName.equals("ListenPortEnabled")) {
            return 93;
         } else if (propName.equals("MSIFileReplicationEnabled")) {
            return 182;
         } else if (propName.equals("ManagedServerIndependenceEnabled")) {
            return 181;
         } else if (propName.equals("MessageIdPrefixEnabled")) {
            return 199;
         } else if (propName.equals("NetworkClassLoadingEnabled")) {
            return 147;
         } else if (propName.equals("SessionReplicationOnShutdownEnabled")) {
            return 234;
         } else if (propName.equals("SitConfigRequired")) {
            return 240;
         } else if (propName.equals("StdoutDebugEnabled")) {
            return 58;
         } else if (propName.equals("StdoutEnabled")) {
            return 56;
         } else if (propName.equals("StdoutLogStack")) {
            return 65;
         } else if (propName.equals("TGIOPEnabled")) {
            return 111;
         } else if (propName.equals("TunnelingEnabled")) {
            return 148;
         } else if (propName.equals("UseFusionForLLR")) {
            return 204;
         } else {
            return propName.equals("WeblogicPluginEnabled") ? 184 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCOM() != null) {
            iterators.add(new ArrayIterator(new COMMBean[]{this.bean.getCOM()}));
         }

         if (this.bean.getCoherenceMemberConfig() != null) {
            iterators.add(new ArrayIterator(new CoherenceMemberConfigMBean[]{this.bean.getCoherenceMemberConfig()}));
         }

         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         if (this.bean.getDataSource() != null) {
            iterators.add(new ArrayIterator(new DataSourceMBean[]{this.bean.getDataSource()}));
         }

         if (this.bean.getDefaultFileStore() != null) {
            iterators.add(new ArrayIterator(new DefaultFileStoreMBean[]{this.bean.getDefaultFileStore()}));
         }

         iterators.add(new ArrayIterator(this.bean.getExecuteQueues()));
         if (this.bean.getFederationServices() != null) {
            iterators.add(new ArrayIterator(new FederationServicesMBean[]{this.bean.getFederationServices()}));
         }

         if (this.bean.getIIOP() != null) {
            iterators.add(new ArrayIterator(new IIOPMBean[]{this.bean.getIIOP()}));
         }

         if (this.bean.getJTAMigratableTarget() != null) {
            iterators.add(new ArrayIterator(new JTAMigratableTargetMBean[]{this.bean.getJTAMigratableTarget()}));
         }

         if (this.bean.getLog() != null) {
            iterators.add(new ArrayIterator(new LogMBean[]{this.bean.getLog()}));
         }

         iterators.add(new ArrayIterator(this.bean.getNetworkAccessPoints()));
         if (this.bean.getOverloadProtection() != null) {
            iterators.add(new ArrayIterator(new OverloadProtectionMBean[]{this.bean.getOverloadProtection()}));
         }

         if (this.bean.getSSL() != null) {
            iterators.add(new ArrayIterator(new SSLMBean[]{this.bean.getSSL()}));
         }

         if (this.bean.getServerDebug() != null) {
            iterators.add(new ArrayIterator(new ServerDebugMBean[]{this.bean.getServerDebug()}));
         }

         if (this.bean.getServerDiagnosticConfig() != null) {
            iterators.add(new ArrayIterator(new WLDFServerDiagnosticMBean[]{this.bean.getServerDiagnosticConfig()}));
         }

         if (this.bean.getServerStart() != null) {
            iterators.add(new ArrayIterator(new ServerStartMBean[]{this.bean.getServerStart()}));
         }

         if (this.bean.getSingleSignOnServices() != null) {
            iterators.add(new ArrayIterator(new SingleSignOnServicesMBean[]{this.bean.getSingleSignOnServices()}));
         }

         if (this.bean.getTransactionLogJDBCStore() != null) {
            iterators.add(new ArrayIterator(new TransactionLogJDBCStoreMBean[]{this.bean.getTransactionLogJDBCStore()}));
         }

         if (this.bean.getWebServer() != null) {
            iterators.add(new ArrayIterator(new WebServerMBean[]{this.bean.getWebServer()}));
         }

         if (this.bean.getWebService() != null) {
            iterators.add(new ArrayIterator(new WebServiceMBean[]{this.bean.getWebService()}));
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
            if (this.bean.is81StyleDefaultStagingDirNameSet()) {
               buf.append("81StyleDefaultStagingDirName");
               buf.append(String.valueOf(this.bean.get81StyleDefaultStagingDirName()));
            }

            if (this.bean.isAcceptBacklogSet()) {
               buf.append("AcceptBacklog");
               buf.append(String.valueOf(this.bean.getAcceptBacklog()));
            }

            if (this.bean.isActiveDirectoryNameSet()) {
               buf.append("ActiveDirectoryName");
               buf.append(String.valueOf(this.bean.getActiveDirectoryName()));
            }

            if (this.bean.isAdminReconnectIntervalSecondsSet()) {
               buf.append("AdminReconnectIntervalSeconds");
               buf.append(String.valueOf(this.bean.getAdminReconnectIntervalSeconds()));
            }

            if (this.bean.isAdministrationPortSet()) {
               buf.append("AdministrationPort");
               buf.append(String.valueOf(this.bean.getAdministrationPort()));
            }

            if (this.bean.isAutoJDBCConnectionCloseSet()) {
               buf.append("AutoJDBCConnectionClose");
               buf.append(String.valueOf(this.bean.getAutoJDBCConnectionClose()));
            }

            if (this.bean.isAutoKillIfFailedSet()) {
               buf.append("AutoKillIfFailed");
               buf.append(String.valueOf(this.bean.getAutoKillIfFailed()));
            }

            if (this.bean.isAutoRestartSet()) {
               buf.append("AutoRestart");
               buf.append(String.valueOf(this.bean.getAutoRestart()));
            }

            if (this.bean.isBuzzAddressSet()) {
               buf.append("BuzzAddress");
               buf.append(String.valueOf(this.bean.getBuzzAddress()));
            }

            if (this.bean.isBuzzPortSet()) {
               buf.append("BuzzPort");
               buf.append(String.valueOf(this.bean.getBuzzPort()));
            }

            childValue = this.computeChildHashValue(this.bean.getCOM());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCandidateMachinesSet()) {
               buf.append("CandidateMachines");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCandidateMachines())));
            }

            if (this.bean.isClusterSet()) {
               buf.append("Cluster");
               buf.append(String.valueOf(this.bean.getCluster()));
            }

            if (this.bean.isClusterWeightSet()) {
               buf.append("ClusterWeight");
               buf.append(String.valueOf(this.bean.getClusterWeight()));
            }

            if (this.bean.isCoherenceClusterSystemResourceSet()) {
               buf.append("CoherenceClusterSystemResource");
               buf.append(String.valueOf(this.bean.getCoherenceClusterSystemResource()));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceMemberConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getConfigurationProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigurationProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConsensusProcessIdentifierSet()) {
               buf.append("ConsensusProcessIdentifier");
               buf.append(String.valueOf(this.bean.getConsensusProcessIdentifier()));
            }

            if (this.bean.isCustomIdentityKeyStoreFileNameSet()) {
               buf.append("CustomIdentityKeyStoreFileName");
               buf.append(String.valueOf(this.bean.getCustomIdentityKeyStoreFileName()));
            }

            if (this.bean.isCustomIdentityKeyStorePassPhraseSet()) {
               buf.append("CustomIdentityKeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getCustomIdentityKeyStorePassPhrase()));
            }

            if (this.bean.isCustomIdentityKeyStorePassPhraseEncryptedSet()) {
               buf.append("CustomIdentityKeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCustomIdentityKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isCustomIdentityKeyStoreTypeSet()) {
               buf.append("CustomIdentityKeyStoreType");
               buf.append(String.valueOf(this.bean.getCustomIdentityKeyStoreType()));
            }

            if (this.bean.isCustomTrustKeyStoreFileNameSet()) {
               buf.append("CustomTrustKeyStoreFileName");
               buf.append(String.valueOf(this.bean.getCustomTrustKeyStoreFileName()));
            }

            if (this.bean.isCustomTrustKeyStorePassPhraseSet()) {
               buf.append("CustomTrustKeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getCustomTrustKeyStorePassPhrase()));
            }

            if (this.bean.isCustomTrustKeyStorePassPhraseEncryptedSet()) {
               buf.append("CustomTrustKeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCustomTrustKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isCustomTrustKeyStoreTypeSet()) {
               buf.append("CustomTrustKeyStoreType");
               buf.append(String.valueOf(this.bean.getCustomTrustKeyStoreType()));
            }

            childValue = this.computeChildHashValue(this.bean.getDataSource());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultFileStore());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDefaultIIOPPasswordSet()) {
               buf.append("DefaultIIOPPassword");
               buf.append(String.valueOf(this.bean.getDefaultIIOPPassword()));
            }

            if (this.bean.isDefaultIIOPPasswordEncryptedSet()) {
               buf.append("DefaultIIOPPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultIIOPPasswordEncrypted())));
            }

            if (this.bean.isDefaultIIOPUserSet()) {
               buf.append("DefaultIIOPUser");
               buf.append(String.valueOf(this.bean.getDefaultIIOPUser()));
            }

            if (this.bean.isDefaultStagingDirNameSet()) {
               buf.append("DefaultStagingDirName");
               buf.append(String.valueOf(this.bean.getDefaultStagingDirName()));
            }

            if (this.bean.isDefaultTGIOPPasswordSet()) {
               buf.append("DefaultTGIOPPassword");
               buf.append(String.valueOf(this.bean.getDefaultTGIOPPassword()));
            }

            if (this.bean.isDefaultTGIOPPasswordEncryptedSet()) {
               buf.append("DefaultTGIOPPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultTGIOPPasswordEncrypted())));
            }

            if (this.bean.isDefaultTGIOPUserSet()) {
               buf.append("DefaultTGIOPUser");
               buf.append(String.valueOf(this.bean.getDefaultTGIOPUser()));
            }

            if (this.bean.isExpectedToRunSet()) {
               buf.append("ExpectedToRun");
               buf.append(String.valueOf(this.bean.getExpectedToRun()));
            }

            if (this.bean.isExternalDNSNameSet()) {
               buf.append("ExternalDNSName");
               buf.append(String.valueOf(this.bean.getExternalDNSName()));
            }

            if (this.bean.isExtraEjbcOptionsSet()) {
               buf.append("ExtraEjbcOptions");
               buf.append(String.valueOf(this.bean.getExtraEjbcOptions()));
            }

            if (this.bean.isExtraRmicOptionsSet()) {
               buf.append("ExtraRmicOptions");
               buf.append(String.valueOf(this.bean.getExtraRmicOptions()));
            }

            childValue = this.computeChildHashValue(this.bean.getFederationServices());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isGracefulShutdownTimeoutSet()) {
               buf.append("GracefulShutdownTimeout");
               buf.append(String.valueOf(this.bean.getGracefulShutdownTimeout()));
            }

            if (this.bean.isHealthCheckIntervalSecondsSet()) {
               buf.append("HealthCheckIntervalSeconds");
               buf.append(String.valueOf(this.bean.getHealthCheckIntervalSeconds()));
            }

            if (this.bean.isHealthCheckStartDelaySecondsSet()) {
               buf.append("HealthCheckStartDelaySeconds");
               buf.append(String.valueOf(this.bean.getHealthCheckStartDelaySeconds()));
            }

            if (this.bean.isHealthCheckTimeoutSecondsSet()) {
               buf.append("HealthCheckTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getHealthCheckTimeoutSeconds()));
            }

            if (this.bean.isHostsMigratableServicesSet()) {
               buf.append("HostsMigratableServices");
               buf.append(String.valueOf(this.bean.getHostsMigratableServices()));
            }

            if (this.bean.isIIOPConnectionPoolsSet()) {
               buf.append("IIOPConnectionPools");
               buf.append(String.valueOf(this.bean.getIIOPConnectionPools()));
            }

            if (this.bean.isInterfaceAddressSet()) {
               buf.append("InterfaceAddress");
               buf.append(String.valueOf(this.bean.getInterfaceAddress()));
            }

            if (this.bean.isJDBCLLRTableNameSet()) {
               buf.append("JDBCLLRTableName");
               buf.append(String.valueOf(this.bean.getJDBCLLRTableName()));
            }

            if (this.bean.isJDBCLLRTablePoolColumnSizeSet()) {
               buf.append("JDBCLLRTablePoolColumnSize");
               buf.append(String.valueOf(this.bean.getJDBCLLRTablePoolColumnSize()));
            }

            if (this.bean.isJDBCLLRTableRecordColumnSizeSet()) {
               buf.append("JDBCLLRTableRecordColumnSize");
               buf.append(String.valueOf(this.bean.getJDBCLLRTableRecordColumnSize()));
            }

            if (this.bean.isJDBCLLRTableXIDColumnSizeSet()) {
               buf.append("JDBCLLRTableXIDColumnSize");
               buf.append(String.valueOf(this.bean.getJDBCLLRTableXIDColumnSize()));
            }

            if (this.bean.isJDBCLogFileNameSet()) {
               buf.append("JDBCLogFileName");
               buf.append(String.valueOf(this.bean.getJDBCLogFileName()));
            }

            if (this.bean.isJDBCLoginTimeoutSecondsSet()) {
               buf.append("JDBCLoginTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getJDBCLoginTimeoutSeconds()));
            }

            if (this.bean.isJMSConnectionFactoryUnmappedResRefModeSet()) {
               buf.append("JMSConnectionFactoryUnmappedResRefMode");
               buf.append(String.valueOf(this.bean.getJMSConnectionFactoryUnmappedResRefMode()));
            }

            if (this.bean.isJNDITransportableObjectFactoryListSet()) {
               buf.append("JNDITransportableObjectFactoryList");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJNDITransportableObjectFactoryList())));
            }

            childValue = this.computeChildHashValue(this.bean.getJTAMigratableTarget());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJavaCompilerSet()) {
               buf.append("JavaCompiler");
               buf.append(String.valueOf(this.bean.getJavaCompiler()));
            }

            if (this.bean.isJavaCompilerPostClassPathSet()) {
               buf.append("JavaCompilerPostClassPath");
               buf.append(String.valueOf(this.bean.getJavaCompilerPostClassPath()));
            }

            if (this.bean.isJavaCompilerPreClassPathSet()) {
               buf.append("JavaCompilerPreClassPath");
               buf.append(String.valueOf(this.bean.getJavaCompilerPreClassPath()));
            }

            if (this.bean.isJavaStandardTrustKeyStorePassPhraseSet()) {
               buf.append("JavaStandardTrustKeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getJavaStandardTrustKeyStorePassPhrase()));
            }

            if (this.bean.isJavaStandardTrustKeyStorePassPhraseEncryptedSet()) {
               buf.append("JavaStandardTrustKeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJavaStandardTrustKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isKernelDebugSet()) {
               buf.append("KernelDebug");
               buf.append(String.valueOf(this.bean.getKernelDebug()));
            }

            if (this.bean.isKeyStoresSet()) {
               buf.append("KeyStores");
               buf.append(String.valueOf(this.bean.getKeyStores()));
            }

            if (this.bean.isListenAddressSet()) {
               buf.append("ListenAddress");
               buf.append(String.valueOf(this.bean.getListenAddress()));
            }

            if (this.bean.isListenDelaySecsSet()) {
               buf.append("ListenDelaySecs");
               buf.append(String.valueOf(this.bean.getListenDelaySecs()));
            }

            if (this.bean.isListenPortSet()) {
               buf.append("ListenPort");
               buf.append(String.valueOf(this.bean.getListenPort()));
            }

            if (this.bean.isListenThreadStartDelaySecsSet()) {
               buf.append("ListenThreadStartDelaySecs");
               buf.append(String.valueOf(this.bean.getListenThreadStartDelaySecs()));
            }

            if (this.bean.isListenersBindEarlySet()) {
               buf.append("ListenersBindEarly");
               buf.append(String.valueOf(this.bean.getListenersBindEarly()));
            }

            if (this.bean.isLoginTimeoutSet()) {
               buf.append("LoginTimeout");
               buf.append(String.valueOf(this.bean.getLoginTimeout()));
            }

            if (this.bean.isLoginTimeoutMillisSet()) {
               buf.append("LoginTimeoutMillis");
               buf.append(String.valueOf(this.bean.getLoginTimeoutMillis()));
            }

            if (this.bean.isLowMemoryGCThresholdSet()) {
               buf.append("LowMemoryGCThreshold");
               buf.append(String.valueOf(this.bean.getLowMemoryGCThreshold()));
            }

            if (this.bean.isLowMemoryGranularityLevelSet()) {
               buf.append("LowMemoryGranularityLevel");
               buf.append(String.valueOf(this.bean.getLowMemoryGranularityLevel()));
            }

            if (this.bean.isLowMemorySampleSizeSet()) {
               buf.append("LowMemorySampleSize");
               buf.append(String.valueOf(this.bean.getLowMemorySampleSize()));
            }

            if (this.bean.isLowMemoryTimeIntervalSet()) {
               buf.append("LowMemoryTimeInterval");
               buf.append(String.valueOf(this.bean.getLowMemoryTimeInterval()));
            }

            if (this.bean.isMachineSet()) {
               buf.append("Machine");
               buf.append(String.valueOf(this.bean.getMachine()));
            }

            if (this.bean.isMaxBackoffBetweenFailuresSet()) {
               buf.append("MaxBackoffBetweenFailures");
               buf.append(String.valueOf(this.bean.getMaxBackoffBetweenFailures()));
            }

            if (this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               buf.append("MaxConcurrentLongRunningRequests");
               buf.append(String.valueOf(this.bean.getMaxConcurrentLongRunningRequests()));
            }

            if (this.bean.isMaxConcurrentNewThreadsSet()) {
               buf.append("MaxConcurrentNewThreads");
               buf.append(String.valueOf(this.bean.getMaxConcurrentNewThreads()));
            }

            if (this.bean.isNMSocketCreateTimeoutInMillisSet()) {
               buf.append("NMSocketCreateTimeoutInMillis");
               buf.append(String.valueOf(this.bean.getNMSocketCreateTimeoutInMillis()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getNetworkAccessPoints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getNetworkAccessPoints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNumOfRetriesBeforeMSIModeSet()) {
               buf.append("NumOfRetriesBeforeMSIMode");
               buf.append(String.valueOf(this.bean.getNumOfRetriesBeforeMSIMode()));
            }

            childValue = this.computeChildHashValue(this.bean.getOverloadProtection());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isParentSet()) {
               buf.append("Parent");
               buf.append(String.valueOf(this.bean.getParent()));
            }

            if (this.bean.isPreferredSecondaryGroupSet()) {
               buf.append("PreferredSecondaryGroup");
               buf.append(String.valueOf(this.bean.getPreferredSecondaryGroup()));
            }

            if (this.bean.isReliableDeliveryPolicySet()) {
               buf.append("ReliableDeliveryPolicy");
               buf.append(String.valueOf(this.bean.getReliableDeliveryPolicy()));
            }

            if (this.bean.isReplicationGroupSet()) {
               buf.append("ReplicationGroup");
               buf.append(String.valueOf(this.bean.getReplicationGroup()));
            }

            if (this.bean.isReplicationPortsSet()) {
               buf.append("ReplicationPorts");
               buf.append(String.valueOf(this.bean.getReplicationPorts()));
            }

            if (this.bean.isResolveDNSNameSet()) {
               buf.append("ResolveDNSName");
               buf.append(String.valueOf(this.bean.getResolveDNSName()));
            }

            if (this.bean.isRestartDelaySecondsSet()) {
               buf.append("RestartDelaySeconds");
               buf.append(String.valueOf(this.bean.getRestartDelaySeconds()));
            }

            if (this.bean.isRestartIntervalSecondsSet()) {
               buf.append("RestartIntervalSeconds");
               buf.append(String.valueOf(this.bean.getRestartIntervalSeconds()));
            }

            if (this.bean.isRestartMaxSet()) {
               buf.append("RestartMax");
               buf.append(String.valueOf(this.bean.getRestartMax()));
            }

            if (this.bean.isRetryIntervalBeforeMSIModeSet()) {
               buf.append("RetryIntervalBeforeMSIMode");
               buf.append(String.valueOf(this.bean.getRetryIntervalBeforeMSIMode()));
            }

            if (this.bean.isRootDirectorySet()) {
               buf.append("RootDirectory");
               buf.append(String.valueOf(this.bean.getRootDirectory()));
            }

            childValue = this.computeChildHashValue(this.bean.getServerDebug());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getServerDiagnosticConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServerLifeCycleTimeoutValSet()) {
               buf.append("ServerLifeCycleTimeoutVal");
               buf.append(String.valueOf(this.bean.getServerLifeCycleTimeoutVal()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            childValue = this.computeChildHashValue(this.bean.getServerStart());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServerVersionSet()) {
               buf.append("ServerVersion");
               buf.append(String.valueOf(this.bean.getServerVersion()));
            }

            childValue = this.computeChildHashValue(this.bean.getSingleSignOnServices());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSitConfigPollingIntervalSet()) {
               buf.append("SitConfigPollingInterval");
               buf.append(String.valueOf(this.bean.getSitConfigPollingInterval()));
            }

            if (this.bean.isStagingDirectoryNameSet()) {
               buf.append("StagingDirectoryName");
               buf.append(String.valueOf(this.bean.getStagingDirectoryName()));
            }

            if (this.bean.isStagingModeSet()) {
               buf.append("StagingMode");
               buf.append(String.valueOf(this.bean.getStagingMode()));
            }

            if (this.bean.isStartupModeSet()) {
               buf.append("StartupMode");
               buf.append(String.valueOf(this.bean.getStartupMode()));
            }

            if (this.bean.isStartupTimeoutSet()) {
               buf.append("StartupTimeout");
               buf.append(String.valueOf(this.bean.getStartupTimeout()));
            }

            if (this.bean.isStdoutFormatSet()) {
               buf.append("StdoutFormat");
               buf.append(String.valueOf(this.bean.getStdoutFormat()));
            }

            if (this.bean.isStdoutSeverityLevelSet()) {
               buf.append("StdoutSeverityLevel");
               buf.append(String.valueOf(this.bean.getStdoutSeverityLevel()));
            }

            if (this.bean.isSupportedProtocolsSet()) {
               buf.append("SupportedProtocols");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSupportedProtocols())));
            }

            if (this.bean.isSystemPasswordSet()) {
               buf.append("SystemPassword");
               buf.append(String.valueOf(this.bean.getSystemPassword()));
            }

            if (this.bean.isSystemPasswordEncryptedSet()) {
               buf.append("SystemPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSystemPasswordEncrypted())));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isThreadPoolSizeSet()) {
               buf.append("ThreadPoolSize");
               buf.append(String.valueOf(this.bean.getThreadPoolSize()));
            }

            if (this.bean.isTransactionLogFilePrefixSet()) {
               buf.append("TransactionLogFilePrefix");
               buf.append(String.valueOf(this.bean.getTransactionLogFilePrefix()));
            }

            if (this.bean.isTransactionLogFileWritePolicySet()) {
               buf.append("TransactionLogFileWritePolicy");
               buf.append(String.valueOf(this.bean.getTransactionLogFileWritePolicy()));
            }

            childValue = this.computeChildHashValue(this.bean.getTransactionLogJDBCStore());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTransactionPrimaryChannelNameSet()) {
               buf.append("TransactionPrimaryChannelName");
               buf.append(String.valueOf(this.bean.getTransactionPrimaryChannelName()));
            }

            if (this.bean.isTransactionPublicChannelNameSet()) {
               buf.append("TransactionPublicChannelName");
               buf.append(String.valueOf(this.bean.getTransactionPublicChannelName()));
            }

            if (this.bean.isTransactionPublicSecureChannelNameSet()) {
               buf.append("TransactionPublicSecureChannelName");
               buf.append(String.valueOf(this.bean.getTransactionPublicSecureChannelName()));
            }

            if (this.bean.isTransactionSecureChannelNameSet()) {
               buf.append("TransactionSecureChannelName");
               buf.append(String.valueOf(this.bean.getTransactionSecureChannelName()));
            }

            if (this.bean.isTunnelingClientPingSecsSet()) {
               buf.append("TunnelingClientPingSecs");
               buf.append(String.valueOf(this.bean.getTunnelingClientPingSecs()));
            }

            if (this.bean.isTunnelingClientTimeoutSecsSet()) {
               buf.append("TunnelingClientTimeoutSecs");
               buf.append(String.valueOf(this.bean.getTunnelingClientTimeoutSecs()));
            }

            if (this.bean.isUploadDirectoryNameSet()) {
               buf.append("UploadDirectoryName");
               buf.append(String.valueOf(this.bean.getUploadDirectoryName()));
            }

            if (this.bean.isVerboseEJBDeploymentEnabledSet()) {
               buf.append("VerboseEJBDeploymentEnabled");
               buf.append(String.valueOf(this.bean.getVerboseEJBDeploymentEnabled()));
            }

            if (this.bean.isVirtualMachineNameSet()) {
               buf.append("VirtualMachineName");
               buf.append(String.valueOf(this.bean.getVirtualMachineName()));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServer());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebService());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isXMLEntityCacheSet()) {
               buf.append("XMLEntityCache");
               buf.append(String.valueOf(this.bean.getXMLEntityCache()));
            }

            if (this.bean.isXMLRegistrySet()) {
               buf.append("XMLRegistry");
               buf.append(String.valueOf(this.bean.getXMLRegistry()));
            }

            if (this.bean.isAdministrationPortEnabledSet()) {
               buf.append("AdministrationPortEnabled");
               buf.append(String.valueOf(this.bean.isAdministrationPortEnabled()));
            }

            if (this.bean.isAutoMigrationEnabledSet()) {
               buf.append("AutoMigrationEnabled");
               buf.append(String.valueOf(this.bean.isAutoMigrationEnabled()));
            }

            if (this.bean.isBuzzEnabledSet()) {
               buf.append("BuzzEnabled");
               buf.append(String.valueOf(this.bean.isBuzzEnabled()));
            }

            if (this.bean.isCOMEnabledSet()) {
               buf.append("COMEnabled");
               buf.append(String.valueOf(this.bean.isCOMEnabled()));
            }

            if (this.bean.isClasspathServletDisabledSet()) {
               buf.append("ClasspathServletDisabled");
               buf.append(String.valueOf(this.bean.isClasspathServletDisabled()));
            }

            if (this.bean.isClasspathServletSecureModeEnabledSet()) {
               buf.append("ClasspathServletSecureModeEnabled");
               buf.append(String.valueOf(this.bean.isClasspathServletSecureModeEnabled()));
            }

            if (this.bean.isCleanupOrphanedSessionsEnabledSet()) {
               buf.append("CleanupOrphanedSessionsEnabled");
               buf.append(String.valueOf(this.bean.isCleanupOrphanedSessionsEnabled()));
            }

            if (this.bean.isClientCertProxyEnabledSet()) {
               buf.append("ClientCertProxyEnabled");
               buf.append(String.valueOf(this.bean.isClientCertProxyEnabled()));
            }

            if (this.bean.isConsoleInputEnabledSet()) {
               buf.append("ConsoleInputEnabled");
               buf.append(String.valueOf(this.bean.isConsoleInputEnabled()));
            }

            if (this.bean.isDefaultInternalServletsDisabledSet()) {
               buf.append("DefaultInternalServletsDisabled");
               buf.append(String.valueOf(this.bean.isDefaultInternalServletsDisabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isHttpTraceSupportEnabledSet()) {
               buf.append("HttpTraceSupportEnabled");
               buf.append(String.valueOf(this.bean.isHttpTraceSupportEnabled()));
            }

            if (this.bean.isHttpdEnabledSet()) {
               buf.append("HttpdEnabled");
               buf.append(String.valueOf(this.bean.isHttpdEnabled()));
            }

            if (this.bean.isIIOPEnabledSet()) {
               buf.append("IIOPEnabled");
               buf.append(String.valueOf(this.bean.isIIOPEnabled()));
            }

            if (this.bean.isIgnoreSessionsDuringShutdownSet()) {
               buf.append("IgnoreSessionsDuringShutdown");
               buf.append(String.valueOf(this.bean.isIgnoreSessionsDuringShutdown()));
            }

            if (this.bean.isJ2EE12OnlyModeEnabledSet()) {
               buf.append("J2EE12OnlyModeEnabled");
               buf.append(String.valueOf(this.bean.isJ2EE12OnlyModeEnabled()));
            }

            if (this.bean.isJ2EE13WarningEnabledSet()) {
               buf.append("J2EE13WarningEnabled");
               buf.append(String.valueOf(this.bean.isJ2EE13WarningEnabled()));
            }

            if (this.bean.isJDBCLoggingEnabledSet()) {
               buf.append("JDBCLoggingEnabled");
               buf.append(String.valueOf(this.bean.isJDBCLoggingEnabled()));
            }

            if (this.bean.isJMSDefaultConnectionFactoriesEnabledSet()) {
               buf.append("JMSDefaultConnectionFactoriesEnabled");
               buf.append(String.valueOf(this.bean.isJMSDefaultConnectionFactoriesEnabled()));
            }

            if (this.bean.isJRMPEnabledSet()) {
               buf.append("JRMPEnabled");
               buf.append(String.valueOf(this.bean.isJRMPEnabled()));
            }

            if (this.bean.isListenPortEnabledSet()) {
               buf.append("ListenPortEnabled");
               buf.append(String.valueOf(this.bean.isListenPortEnabled()));
            }

            if (this.bean.isMSIFileReplicationEnabledSet()) {
               buf.append("MSIFileReplicationEnabled");
               buf.append(String.valueOf(this.bean.isMSIFileReplicationEnabled()));
            }

            if (this.bean.isManagedServerIndependenceEnabledSet()) {
               buf.append("ManagedServerIndependenceEnabled");
               buf.append(String.valueOf(this.bean.isManagedServerIndependenceEnabled()));
            }

            if (this.bean.isMessageIdPrefixEnabledSet()) {
               buf.append("MessageIdPrefixEnabled");
               buf.append(String.valueOf(this.bean.isMessageIdPrefixEnabled()));
            }

            if (this.bean.isNetworkClassLoadingEnabledSet()) {
               buf.append("NetworkClassLoadingEnabled");
               buf.append(String.valueOf(this.bean.isNetworkClassLoadingEnabled()));
            }

            if (this.bean.isSessionReplicationOnShutdownEnabledSet()) {
               buf.append("SessionReplicationOnShutdownEnabled");
               buf.append(String.valueOf(this.bean.isSessionReplicationOnShutdownEnabled()));
            }

            if (this.bean.isSitConfigRequiredSet()) {
               buf.append("SitConfigRequired");
               buf.append(String.valueOf(this.bean.isSitConfigRequired()));
            }

            if (this.bean.isStdoutDebugEnabledSet()) {
               buf.append("StdoutDebugEnabled");
               buf.append(String.valueOf(this.bean.isStdoutDebugEnabled()));
            }

            if (this.bean.isStdoutEnabledSet()) {
               buf.append("StdoutEnabled");
               buf.append(String.valueOf(this.bean.isStdoutEnabled()));
            }

            if (this.bean.isStdoutLogStackSet()) {
               buf.append("StdoutLogStack");
               buf.append(String.valueOf(this.bean.isStdoutLogStack()));
            }

            if (this.bean.isTGIOPEnabledSet()) {
               buf.append("TGIOPEnabled");
               buf.append(String.valueOf(this.bean.isTGIOPEnabled()));
            }

            if (this.bean.isTunnelingEnabledSet()) {
               buf.append("TunnelingEnabled");
               buf.append(String.valueOf(this.bean.isTunnelingEnabled()));
            }

            if (this.bean.isUseFusionForLLRSet()) {
               buf.append("UseFusionForLLR");
               buf.append(String.valueOf(this.bean.isUseFusionForLLR()));
            }

            if (this.bean.isWeblogicPluginEnabledSet()) {
               buf.append("WeblogicPluginEnabled");
               buf.append(String.valueOf(this.bean.isWeblogicPluginEnabled()));
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
            ServerTemplateMBeanImpl otherTyped = (ServerTemplateMBeanImpl)other;
            this.computeDiff("AcceptBacklog", this.bean.getAcceptBacklog(), otherTyped.getAcceptBacklog(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ActiveDirectoryName", this.bean.getActiveDirectoryName(), otherTyped.getActiveDirectoryName(), false);
            }

            this.computeDiff("AdminReconnectIntervalSeconds", this.bean.getAdminReconnectIntervalSeconds(), otherTyped.getAdminReconnectIntervalSeconds(), true);
            this.computeDiff("AdministrationPort", this.bean.getAdministrationPort(), otherTyped.getAdministrationPort(), true);
            this.computeDiff("AutoJDBCConnectionClose", this.bean.getAutoJDBCConnectionClose(), otherTyped.getAutoJDBCConnectionClose(), false);
            this.computeDiff("AutoKillIfFailed", this.bean.getAutoKillIfFailed(), otherTyped.getAutoKillIfFailed(), true);
            this.computeDiff("AutoRestart", this.bean.getAutoRestart(), otherTyped.getAutoRestart(), true);
            this.computeDiff("BuzzAddress", this.bean.getBuzzAddress(), otherTyped.getBuzzAddress(), false);
            this.computeDiff("BuzzPort", this.bean.getBuzzPort(), otherTyped.getBuzzPort(), false);
            this.computeSubDiff("COM", this.bean.getCOM(), otherTyped.getCOM());
            this.computeDiff("CandidateMachines", this.bean.getCandidateMachines(), otherTyped.getCandidateMachines(), false, true);
            this.computeDiff("Cluster", this.bean.getCluster(), otherTyped.getCluster(), false);
            this.computeDiff("ClusterWeight", this.bean.getClusterWeight(), otherTyped.getClusterWeight(), false);
            this.computeDiff("CoherenceClusterSystemResource", this.bean.getCoherenceClusterSystemResource(), otherTyped.getCoherenceClusterSystemResource(), false);
            this.computeSubDiff("CoherenceMemberConfig", this.bean.getCoherenceMemberConfig(), otherTyped.getCoherenceMemberConfig());
            this.computeChildDiff("ConfigurationProperties", this.bean.getConfigurationProperties(), otherTyped.getConfigurationProperties(), true);
            this.computeDiff("ConsensusProcessIdentifier", this.bean.getConsensusProcessIdentifier(), otherTyped.getConsensusProcessIdentifier(), false);
            this.computeDiff("CustomIdentityKeyStoreFileName", this.bean.getCustomIdentityKeyStoreFileName(), otherTyped.getCustomIdentityKeyStoreFileName(), true);
            this.computeDiff("CustomIdentityKeyStorePassPhraseEncrypted", this.bean.getCustomIdentityKeyStorePassPhraseEncrypted(), otherTyped.getCustomIdentityKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("CustomIdentityKeyStoreType", this.bean.getCustomIdentityKeyStoreType(), otherTyped.getCustomIdentityKeyStoreType(), true);
            this.computeDiff("CustomTrustKeyStoreFileName", this.bean.getCustomTrustKeyStoreFileName(), otherTyped.getCustomTrustKeyStoreFileName(), true);
            this.computeDiff("CustomTrustKeyStorePassPhraseEncrypted", this.bean.getCustomTrustKeyStorePassPhraseEncrypted(), otherTyped.getCustomTrustKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("CustomTrustKeyStoreType", this.bean.getCustomTrustKeyStoreType(), otherTyped.getCustomTrustKeyStoreType(), true);
            this.computeSubDiff("DataSource", this.bean.getDataSource(), otherTyped.getDataSource());
            this.computeSubDiff("DefaultFileStore", this.bean.getDefaultFileStore(), otherTyped.getDefaultFileStore());
            this.computeDiff("DefaultIIOPPasswordEncrypted", this.bean.getDefaultIIOPPasswordEncrypted(), otherTyped.getDefaultIIOPPasswordEncrypted(), false);
            this.computeDiff("DefaultIIOPUser", this.bean.getDefaultIIOPUser(), otherTyped.getDefaultIIOPUser(), false);
            this.computeDiff("DefaultTGIOPPasswordEncrypted", this.bean.getDefaultTGIOPPasswordEncrypted(), otherTyped.getDefaultTGIOPPasswordEncrypted(), false);
            this.computeDiff("DefaultTGIOPUser", this.bean.getDefaultTGIOPUser(), otherTyped.getDefaultTGIOPUser(), false);
            this.computeDiff("ExternalDNSName", this.bean.getExternalDNSName(), otherTyped.getExternalDNSName(), false);
            this.computeDiff("ExtraEjbcOptions", this.bean.getExtraEjbcOptions(), otherTyped.getExtraEjbcOptions(), false);
            this.computeDiff("ExtraRmicOptions", this.bean.getExtraRmicOptions(), otherTyped.getExtraRmicOptions(), false);
            this.computeSubDiff("FederationServices", this.bean.getFederationServices(), otherTyped.getFederationServices());
            this.computeDiff("GracefulShutdownTimeout", this.bean.getGracefulShutdownTimeout(), otherTyped.getGracefulShutdownTimeout(), true);
            this.computeDiff("HealthCheckIntervalSeconds", this.bean.getHealthCheckIntervalSeconds(), otherTyped.getHealthCheckIntervalSeconds(), true);
            this.computeDiff("HealthCheckStartDelaySeconds", this.bean.getHealthCheckStartDelaySeconds(), otherTyped.getHealthCheckStartDelaySeconds(), true);
            this.computeDiff("HealthCheckTimeoutSeconds", this.bean.getHealthCheckTimeoutSeconds(), otherTyped.getHealthCheckTimeoutSeconds(), true);
            this.computeDiff("HostsMigratableServices", this.bean.getHostsMigratableServices(), otherTyped.getHostsMigratableServices(), false);
            this.computeDiff("IIOPConnectionPools", this.bean.getIIOPConnectionPools(), otherTyped.getIIOPConnectionPools(), false);
            this.computeDiff("InterfaceAddress", this.bean.getInterfaceAddress(), otherTyped.getInterfaceAddress(), false);
            this.computeDiff("JDBCLLRTableName", this.bean.getJDBCLLRTableName(), otherTyped.getJDBCLLRTableName(), false);
            this.computeDiff("JDBCLLRTablePoolColumnSize", this.bean.getJDBCLLRTablePoolColumnSize(), otherTyped.getJDBCLLRTablePoolColumnSize(), false);
            this.computeDiff("JDBCLLRTableRecordColumnSize", this.bean.getJDBCLLRTableRecordColumnSize(), otherTyped.getJDBCLLRTableRecordColumnSize(), false);
            this.computeDiff("JDBCLLRTableXIDColumnSize", this.bean.getJDBCLLRTableXIDColumnSize(), otherTyped.getJDBCLLRTableXIDColumnSize(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JDBCLogFileName", this.bean.getJDBCLogFileName(), otherTyped.getJDBCLogFileName(), false);
            }

            this.computeDiff("JDBCLoginTimeoutSeconds", this.bean.getJDBCLoginTimeoutSeconds(), otherTyped.getJDBCLoginTimeoutSeconds(), false);
            this.computeDiff("JMSConnectionFactoryUnmappedResRefMode", this.bean.getJMSConnectionFactoryUnmappedResRefMode(), otherTyped.getJMSConnectionFactoryUnmappedResRefMode(), true);
            this.computeDiff("JNDITransportableObjectFactoryList", this.bean.getJNDITransportableObjectFactoryList(), otherTyped.getJNDITransportableObjectFactoryList(), false);
            this.computeChildDiff("JTAMigratableTarget", this.bean.getJTAMigratableTarget(), otherTyped.getJTAMigratableTarget(), false);
            this.computeDiff("JavaCompiler", this.bean.getJavaCompiler(), otherTyped.getJavaCompiler(), true);
            this.computeDiff("JavaCompilerPostClassPath", this.bean.getJavaCompilerPostClassPath(), otherTyped.getJavaCompilerPostClassPath(), false);
            this.computeDiff("JavaCompilerPreClassPath", this.bean.getJavaCompilerPreClassPath(), otherTyped.getJavaCompilerPreClassPath(), false);
            this.computeDiff("JavaStandardTrustKeyStorePassPhraseEncrypted", this.bean.getJavaStandardTrustKeyStorePassPhraseEncrypted(), otherTyped.getJavaStandardTrustKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("KeyStores", this.bean.getKeyStores(), otherTyped.getKeyStores(), true);
            this.computeDiff("ListenAddress", this.bean.getListenAddress(), otherTyped.getListenAddress(), false);
            this.computeDiff("ListenDelaySecs", this.bean.getListenDelaySecs(), otherTyped.getListenDelaySecs(), false);
            this.computeDiff("ListenPort", this.bean.getListenPort(), otherTyped.getListenPort(), true);
            this.computeDiff("ListenThreadStartDelaySecs", this.bean.getListenThreadStartDelaySecs(), otherTyped.getListenThreadStartDelaySecs(), false);
            this.computeDiff("ListenersBindEarly", this.bean.getListenersBindEarly(), otherTyped.getListenersBindEarly(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LoginTimeout", this.bean.getLoginTimeout(), otherTyped.getLoginTimeout(), false);
            }

            this.computeDiff("LoginTimeoutMillis", this.bean.getLoginTimeoutMillis(), otherTyped.getLoginTimeoutMillis(), true);
            this.computeDiff("LowMemoryGCThreshold", this.bean.getLowMemoryGCThreshold(), otherTyped.getLowMemoryGCThreshold(), false);
            this.computeDiff("LowMemoryGranularityLevel", this.bean.getLowMemoryGranularityLevel(), otherTyped.getLowMemoryGranularityLevel(), false);
            this.computeDiff("LowMemorySampleSize", this.bean.getLowMemorySampleSize(), otherTyped.getLowMemorySampleSize(), false);
            this.computeDiff("LowMemoryTimeInterval", this.bean.getLowMemoryTimeInterval(), otherTyped.getLowMemoryTimeInterval(), false);
            this.computeDiff("Machine", this.bean.getMachine(), otherTyped.getMachine(), false);
            this.computeDiff("MaxBackoffBetweenFailures", this.bean.getMaxBackoffBetweenFailures(), otherTyped.getMaxBackoffBetweenFailures(), false);
            this.computeDiff("MaxConcurrentLongRunningRequests", this.bean.getMaxConcurrentLongRunningRequests(), otherTyped.getMaxConcurrentLongRunningRequests(), true);
            this.computeDiff("MaxConcurrentNewThreads", this.bean.getMaxConcurrentNewThreads(), otherTyped.getMaxConcurrentNewThreads(), true);
            this.computeDiff("NMSocketCreateTimeoutInMillis", this.bean.getNMSocketCreateTimeoutInMillis(), otherTyped.getNMSocketCreateTimeoutInMillis(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("NetworkAccessPoints", this.bean.getNetworkAccessPoints(), otherTyped.getNetworkAccessPoints(), true);
            this.computeDiff("NumOfRetriesBeforeMSIMode", this.bean.getNumOfRetriesBeforeMSIMode(), otherTyped.getNumOfRetriesBeforeMSIMode(), true);
            this.computeSubDiff("OverloadProtection", this.bean.getOverloadProtection(), otherTyped.getOverloadProtection());
            this.computeDiff("PreferredSecondaryGroup", this.bean.getPreferredSecondaryGroup(), otherTyped.getPreferredSecondaryGroup(), false);
            this.computeDiff("ReliableDeliveryPolicy", this.bean.getReliableDeliveryPolicy(), otherTyped.getReliableDeliveryPolicy(), false);
            this.computeDiff("ReplicationGroup", this.bean.getReplicationGroup(), otherTyped.getReplicationGroup(), false);
            this.computeDiff("ReplicationPorts", this.bean.getReplicationPorts(), otherTyped.getReplicationPorts(), false);
            this.computeDiff("ResolveDNSName", this.bean.getResolveDNSName(), otherTyped.getResolveDNSName(), false);
            this.computeDiff("RestartDelaySeconds", this.bean.getRestartDelaySeconds(), otherTyped.getRestartDelaySeconds(), true);
            this.computeDiff("RestartIntervalSeconds", this.bean.getRestartIntervalSeconds(), otherTyped.getRestartIntervalSeconds(), true);
            this.computeDiff("RestartMax", this.bean.getRestartMax(), otherTyped.getRestartMax(), true);
            this.computeDiff("RetryIntervalBeforeMSIMode", this.bean.getRetryIntervalBeforeMSIMode(), otherTyped.getRetryIntervalBeforeMSIMode(), true);
            this.computeSubDiff("ServerDebug", this.bean.getServerDebug(), otherTyped.getServerDebug());
            this.computeSubDiff("ServerDiagnosticConfig", this.bean.getServerDiagnosticConfig(), otherTyped.getServerDiagnosticConfig());
            this.computeDiff("ServerLifeCycleTimeoutVal", this.bean.getServerLifeCycleTimeoutVal(), otherTyped.getServerLifeCycleTimeoutVal(), true);
            this.computeSubDiff("ServerStart", this.bean.getServerStart(), otherTyped.getServerStart());
            this.computeDiff("ServerVersion", this.bean.getServerVersion(), otherTyped.getServerVersion(), false);
            this.computeSubDiff("SingleSignOnServices", this.bean.getSingleSignOnServices(), otherTyped.getSingleSignOnServices());
            this.computeDiff("SitConfigPollingInterval", this.bean.getSitConfigPollingInterval(), otherTyped.getSitConfigPollingInterval(), true);
            this.computeDiff("StagingDirectoryName", this.bean.getStagingDirectoryName(), otherTyped.getStagingDirectoryName(), false);
            this.computeDiff("StagingMode", this.bean.getStagingMode(), otherTyped.getStagingMode(), false);
            this.computeDiff("StartupMode", this.bean.getStartupMode(), otherTyped.getStartupMode(), true);
            this.computeDiff("StartupTimeout", this.bean.getStartupTimeout(), otherTyped.getStartupTimeout(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutFormat", this.bean.getStdoutFormat(), otherTyped.getStdoutFormat(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutSeverityLevel", this.bean.getStdoutSeverityLevel(), otherTyped.getStdoutSeverityLevel(), true);
            }

            this.computeDiff("SupportedProtocols", this.bean.getSupportedProtocols(), otherTyped.getSupportedProtocols(), false);
            this.computeDiff("SystemPasswordEncrypted", this.bean.getSystemPasswordEncrypted(), otherTyped.getSystemPasswordEncrypted(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("ThreadPoolSize", this.bean.getThreadPoolSize(), otherTyped.getThreadPoolSize(), false);
            this.computeDiff("TransactionLogFilePrefix", this.bean.getTransactionLogFilePrefix(), otherTyped.getTransactionLogFilePrefix(), false);
            this.computeDiff("TransactionLogFileWritePolicy", this.bean.getTransactionLogFileWritePolicy(), otherTyped.getTransactionLogFileWritePolicy(), false);
            this.computeSubDiff("TransactionLogJDBCStore", this.bean.getTransactionLogJDBCStore(), otherTyped.getTransactionLogJDBCStore());
            this.computeDiff("TransactionPrimaryChannelName", this.bean.getTransactionPrimaryChannelName(), otherTyped.getTransactionPrimaryChannelName(), false);
            this.computeDiff("TransactionPublicChannelName", this.bean.getTransactionPublicChannelName(), otherTyped.getTransactionPublicChannelName(), false);
            this.computeDiff("TransactionPublicSecureChannelName", this.bean.getTransactionPublicSecureChannelName(), otherTyped.getTransactionPublicSecureChannelName(), false);
            this.computeDiff("TransactionSecureChannelName", this.bean.getTransactionSecureChannelName(), otherTyped.getTransactionSecureChannelName(), false);
            this.computeDiff("TunnelingClientPingSecs", this.bean.getTunnelingClientPingSecs(), otherTyped.getTunnelingClientPingSecs(), true);
            this.computeDiff("TunnelingClientTimeoutSecs", this.bean.getTunnelingClientTimeoutSecs(), otherTyped.getTunnelingClientTimeoutSecs(), true);
            this.computeDiff("UploadDirectoryName", this.bean.getUploadDirectoryName(), otherTyped.getUploadDirectoryName(), true);
            this.computeDiff("VerboseEJBDeploymentEnabled", this.bean.getVerboseEJBDeploymentEnabled(), otherTyped.getVerboseEJBDeploymentEnabled(), false);
            this.computeDiff("VirtualMachineName", this.bean.getVirtualMachineName(), otherTyped.getVirtualMachineName(), true);
            this.computeSubDiff("WebServer", this.bean.getWebServer(), otherTyped.getWebServer());
            this.computeSubDiff("WebService", this.bean.getWebService(), otherTyped.getWebService());
            this.computeDiff("XMLEntityCache", this.bean.getXMLEntityCache(), otherTyped.getXMLEntityCache(), false);
            this.computeDiff("XMLRegistry", this.bean.getXMLRegistry(), otherTyped.getXMLRegistry(), false);
            this.computeDiff("AdministrationPortEnabled", this.bean.isAdministrationPortEnabled(), otherTyped.isAdministrationPortEnabled(), true);
            this.computeDiff("AutoMigrationEnabled", this.bean.isAutoMigrationEnabled(), otherTyped.isAutoMigrationEnabled(), false);
            this.computeDiff("BuzzEnabled", this.bean.isBuzzEnabled(), otherTyped.isBuzzEnabled(), false);
            this.computeDiff("COMEnabled", this.bean.isCOMEnabled(), otherTyped.isCOMEnabled(), false);
            this.computeDiff("ClasspathServletDisabled", this.bean.isClasspathServletDisabled(), otherTyped.isClasspathServletDisabled(), false);
            this.computeDiff("ClasspathServletSecureModeEnabled", this.bean.isClasspathServletSecureModeEnabled(), otherTyped.isClasspathServletSecureModeEnabled(), false);
            this.computeDiff("CleanupOrphanedSessionsEnabled", this.bean.isCleanupOrphanedSessionsEnabled(), otherTyped.isCleanupOrphanedSessionsEnabled(), true);
            this.computeDiff("ClientCertProxyEnabled", this.bean.isClientCertProxyEnabled(), otherTyped.isClientCertProxyEnabled(), false);
            this.computeDiff("ConsoleInputEnabled", this.bean.isConsoleInputEnabled(), otherTyped.isConsoleInputEnabled(), false);
            this.computeDiff("DefaultInternalServletsDisabled", this.bean.isDefaultInternalServletsDisabled(), otherTyped.isDefaultInternalServletsDisabled(), false);
            this.computeDiff("HttpTraceSupportEnabled", this.bean.isHttpTraceSupportEnabled(), otherTyped.isHttpTraceSupportEnabled(), false);
            this.computeDiff("HttpdEnabled", this.bean.isHttpdEnabled(), otherTyped.isHttpdEnabled(), false);
            this.computeDiff("IIOPEnabled", this.bean.isIIOPEnabled(), otherTyped.isIIOPEnabled(), false);
            this.computeDiff("IgnoreSessionsDuringShutdown", this.bean.isIgnoreSessionsDuringShutdown(), otherTyped.isIgnoreSessionsDuringShutdown(), true);
            this.computeDiff("J2EE12OnlyModeEnabled", this.bean.isJ2EE12OnlyModeEnabled(), otherTyped.isJ2EE12OnlyModeEnabled(), false);
            this.computeDiff("J2EE13WarningEnabled", this.bean.isJ2EE13WarningEnabled(), otherTyped.isJ2EE13WarningEnabled(), false);
            this.computeDiff("JDBCLoggingEnabled", this.bean.isJDBCLoggingEnabled(), otherTyped.isJDBCLoggingEnabled(), false);
            this.computeDiff("JMSDefaultConnectionFactoriesEnabled", this.bean.isJMSDefaultConnectionFactoriesEnabled(), otherTyped.isJMSDefaultConnectionFactoriesEnabled(), true);
            this.computeDiff("JRMPEnabled", this.bean.isJRMPEnabled(), otherTyped.isJRMPEnabled(), false);
            this.computeDiff("ListenPortEnabled", this.bean.isListenPortEnabled(), otherTyped.isListenPortEnabled(), true);
            this.computeDiff("MSIFileReplicationEnabled", this.bean.isMSIFileReplicationEnabled(), otherTyped.isMSIFileReplicationEnabled(), false);
            this.computeDiff("ManagedServerIndependenceEnabled", this.bean.isManagedServerIndependenceEnabled(), otherTyped.isManagedServerIndependenceEnabled(), false);
            this.computeDiff("MessageIdPrefixEnabled", this.bean.isMessageIdPrefixEnabled(), otherTyped.isMessageIdPrefixEnabled(), false);
            this.computeDiff("NetworkClassLoadingEnabled", this.bean.isNetworkClassLoadingEnabled(), otherTyped.isNetworkClassLoadingEnabled(), false);
            this.computeDiff("SessionReplicationOnShutdownEnabled", this.bean.isSessionReplicationOnShutdownEnabled(), otherTyped.isSessionReplicationOnShutdownEnabled(), true);
            this.computeDiff("SitConfigRequired", this.bean.isSitConfigRequired(), otherTyped.isSitConfigRequired(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutDebugEnabled", this.bean.isStdoutDebugEnabled(), otherTyped.isStdoutDebugEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutEnabled", this.bean.isStdoutEnabled(), otherTyped.isStdoutEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutLogStack", this.bean.isStdoutLogStack(), otherTyped.isStdoutLogStack(), true);
            }

            this.computeDiff("TGIOPEnabled", this.bean.isTGIOPEnabled(), otherTyped.isTGIOPEnabled(), false);
            this.computeDiff("TunnelingEnabled", this.bean.isTunnelingEnabled(), otherTyped.isTunnelingEnabled(), true);
            this.computeDiff("UseFusionForLLR", this.bean.isUseFusionForLLR(), otherTyped.isUseFusionForLLR(), false);
            this.computeDiff("WeblogicPluginEnabled", this.bean.isWeblogicPluginEnabled(), otherTyped.isWeblogicPluginEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServerTemplateMBeanImpl original = (ServerTemplateMBeanImpl)event.getSourceBean();
            ServerTemplateMBeanImpl proposed = (ServerTemplateMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("81StyleDefaultStagingDirName")) {
                  if (prop.equals("AcceptBacklog")) {
                     original.setAcceptBacklog(proposed.getAcceptBacklog());
                     original._conditionalUnset(update.isUnsetUpdate(), 130);
                  } else if (prop.equals("ActiveDirectoryName")) {
                     original.setActiveDirectoryName(proposed.getActiveDirectoryName());
                     original._conditionalUnset(update.isUnsetUpdate(), 163);
                  } else if (prop.equals("AdminReconnectIntervalSeconds")) {
                     original.setAdminReconnectIntervalSeconds(proposed.getAdminReconnectIntervalSeconds());
                     original._conditionalUnset(update.isUnsetUpdate(), 151);
                  } else if (prop.equals("AdministrationPort")) {
                     original.setAdministrationPort(proposed.getAdministrationPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 134);
                  } else if (prop.equals("AutoJDBCConnectionClose")) {
                     original.setAutoJDBCConnectionClose(proposed.getAutoJDBCConnectionClose());
                     original._conditionalUnset(update.isUnsetUpdate(), 210);
                  } else if (prop.equals("AutoKillIfFailed")) {
                     original.setAutoKillIfFailed(proposed.getAutoKillIfFailed());
                     original._conditionalUnset(update.isUnsetUpdate(), 166);
                  } else if (prop.equals("AutoRestart")) {
                     original.setAutoRestart(proposed.getAutoRestart());
                     original._conditionalUnset(update.isUnsetUpdate(), 165);
                  } else if (prop.equals("BuzzAddress")) {
                     original.setBuzzAddress(proposed.getBuzzAddress());
                     original._conditionalUnset(update.isUnsetUpdate(), 225);
                  } else if (prop.equals("BuzzPort")) {
                     original.setBuzzPort(proposed.getBuzzPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 224);
                  } else if (prop.equals("COM")) {
                     if (type == 2) {
                        original.setCOM((COMMBean)this.createCopy((AbstractDescriptorBean)proposed.getCOM()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("COM", original.getCOM());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 117);
                  } else if (prop.equals("CandidateMachines")) {
                     original.setCandidateMachinesAsString(proposed.getCandidateMachinesAsString());
                     this.reorderArrayObjects(original.getCandidateMachines(), proposed.getCandidateMachines());
                     original._conditionalUnset(update.isUnsetUpdate(), 201);
                  } else if (prop.equals("Cluster")) {
                     original.setClusterAsString(proposed.getClusterAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 95);
                  } else if (prop.equals("ClusterWeight")) {
                     original.setClusterWeight(proposed.getClusterWeight());
                     original._conditionalUnset(update.isUnsetUpdate(), 96);
                  } else if (prop.equals("CoherenceClusterSystemResource")) {
                     original.setCoherenceClusterSystemResourceAsString(proposed.getCoherenceClusterSystemResourceAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 218);
                  } else if (prop.equals("CoherenceMemberConfig")) {
                     if (type == 2) {
                        original.setCoherenceMemberConfig((CoherenceMemberConfigMBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceMemberConfig()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("CoherenceMemberConfig", original.getCoherenceMemberConfig());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 223);
                  } else if (prop.equals("ConfigurationProperties")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addConfigurationProperty((ConfigurationPropertyMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeConfigurationProperty((ConfigurationPropertyMBean)update.getRemovedObject());
                     }

                     if (original.getConfigurationProperties() == null || original.getConfigurationProperties().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 89);
                     }
                  } else if (prop.equals("ConsensusProcessIdentifier")) {
                     original.setConsensusProcessIdentifier(proposed.getConsensusProcessIdentifier());
                     original._conditionalUnset(update.isUnsetUpdate(), 99);
                  } else if (prop.equals("CustomIdentityKeyStoreFileName")) {
                     original.setCustomIdentityKeyStoreFileName(proposed.getCustomIdentityKeyStoreFileName());
                     original._conditionalUnset(update.isUnsetUpdate(), 188);
                  } else if (!prop.equals("CustomIdentityKeyStorePassPhrase")) {
                     if (prop.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
                        original.setCustomIdentityKeyStorePassPhraseEncrypted(proposed.getCustomIdentityKeyStorePassPhraseEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 191);
                     } else if (prop.equals("CustomIdentityKeyStoreType")) {
                        original.setCustomIdentityKeyStoreType(proposed.getCustomIdentityKeyStoreType());
                        original._conditionalUnset(update.isUnsetUpdate(), 189);
                     } else if (prop.equals("CustomTrustKeyStoreFileName")) {
                        original.setCustomTrustKeyStoreFileName(proposed.getCustomTrustKeyStoreFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 192);
                     } else if (!prop.equals("CustomTrustKeyStorePassPhrase")) {
                        if (prop.equals("CustomTrustKeyStorePassPhraseEncrypted")) {
                           original.setCustomTrustKeyStorePassPhraseEncrypted(proposed.getCustomTrustKeyStorePassPhraseEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 195);
                        } else if (prop.equals("CustomTrustKeyStoreType")) {
                           original.setCustomTrustKeyStoreType(proposed.getCustomTrustKeyStoreType());
                           original._conditionalUnset(update.isUnsetUpdate(), 193);
                        } else if (prop.equals("DataSource")) {
                           if (type == 2) {
                              original.setDataSource((DataSourceMBean)this.createCopy((AbstractDescriptorBean)proposed.getDataSource()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("DataSource", original.getDataSource());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 222);
                        } else if (prop.equals("DefaultFileStore")) {
                           if (type == 2) {
                              original.setDefaultFileStore((DefaultFileStoreMBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultFileStore()));
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original._destroySingleton("DefaultFileStore", original.getDefaultFileStore());
                           }

                           original._conditionalUnset(update.isUnsetUpdate(), 200);
                        } else if (!prop.equals("DefaultIIOPPassword")) {
                           if (prop.equals("DefaultIIOPPasswordEncrypted")) {
                              original.setDefaultIIOPPasswordEncrypted(proposed.getDefaultIIOPPasswordEncrypted());
                              original._conditionalUnset(update.isUnsetUpdate(), 110);
                           } else if (prop.equals("DefaultIIOPUser")) {
                              original.setDefaultIIOPUser(proposed.getDefaultIIOPUser());
                              original._conditionalUnset(update.isUnsetUpdate(), 108);
                           } else if (!prop.equals("DefaultStagingDirName") && !prop.equals("DefaultTGIOPPassword")) {
                              if (prop.equals("DefaultTGIOPPasswordEncrypted")) {
                                 original.setDefaultTGIOPPasswordEncrypted(proposed.getDefaultTGIOPPasswordEncrypted());
                                 original._conditionalUnset(update.isUnsetUpdate(), 114);
                              } else if (prop.equals("DefaultTGIOPUser")) {
                                 original.setDefaultTGIOPUser(proposed.getDefaultTGIOPUser());
                                 original._conditionalUnset(update.isUnsetUpdate(), 112);
                              } else if (!prop.equals("ExpectedToRun")) {
                                 if (prop.equals("ExternalDNSName")) {
                                    original.setExternalDNSName(proposed.getExternalDNSName());
                                    original._conditionalUnset(update.isUnsetUpdate(), 126);
                                 } else if (prop.equals("ExtraEjbcOptions")) {
                                    original.setExtraEjbcOptions(proposed.getExtraEjbcOptions());
                                    original._conditionalUnset(update.isUnsetUpdate(), 143);
                                 } else if (prop.equals("ExtraRmicOptions")) {
                                    original.setExtraRmicOptions(proposed.getExtraRmicOptions());
                                    original._conditionalUnset(update.isUnsetUpdate(), 142);
                                 } else if (prop.equals("FederationServices")) {
                                    if (type == 2) {
                                       original.setFederationServices((FederationServicesMBean)this.createCopy((AbstractDescriptorBean)proposed.getFederationServices()));
                                    } else {
                                       if (type != 3) {
                                          throw new AssertionError("Invalid type: " + type);
                                       }

                                       original._destroySingleton("FederationServices", original.getFederationServices());
                                    }

                                    original._conditionalUnset(update.isUnsetUpdate(), 214);
                                 } else if (prop.equals("GracefulShutdownTimeout")) {
                                    original.setGracefulShutdownTimeout(proposed.getGracefulShutdownTimeout());
                                    original._conditionalUnset(update.isUnsetUpdate(), 179);
                                 } else if (prop.equals("HealthCheckIntervalSeconds")) {
                                    original.setHealthCheckIntervalSeconds(proposed.getHealthCheckIntervalSeconds());
                                    original._conditionalUnset(update.isUnsetUpdate(), 169);
                                 } else if (prop.equals("HealthCheckStartDelaySeconds")) {
                                    original.setHealthCheckStartDelaySeconds(proposed.getHealthCheckStartDelaySeconds());
                                    original._conditionalUnset(update.isUnsetUpdate(), 171);
                                 } else if (prop.equals("HealthCheckTimeoutSeconds")) {
                                    original.setHealthCheckTimeoutSeconds(proposed.getHealthCheckTimeoutSeconds());
                                    original._conditionalUnset(update.isUnsetUpdate(), 170);
                                 } else if (prop.equals("HostsMigratableServices")) {
                                    original.setHostsMigratableServices(proposed.getHostsMigratableServices());
                                    original._conditionalUnset(update.isUnsetUpdate(), 185);
                                 } else if (prop.equals("IIOPConnectionPools")) {
                                    original.setIIOPConnectionPools(proposed.getIIOPConnectionPools());
                                    original._conditionalUnset(update.isUnsetUpdate(), 136);
                                 } else if (prop.equals("InterfaceAddress")) {
                                    original.setInterfaceAddress(proposed.getInterfaceAddress());
                                    original._conditionalUnset(update.isUnsetUpdate(), 128);
                                 } else if (prop.equals("JDBCLLRTableName")) {
                                    original.setJDBCLLRTableName(proposed.getJDBCLLRTableName());
                                    original._conditionalUnset(update.isUnsetUpdate(), 203);
                                 } else if (prop.equals("JDBCLLRTablePoolColumnSize")) {
                                    original.setJDBCLLRTablePoolColumnSize(proposed.getJDBCLLRTablePoolColumnSize());
                                    original._conditionalUnset(update.isUnsetUpdate(), 206);
                                 } else if (prop.equals("JDBCLLRTableRecordColumnSize")) {
                                    original.setJDBCLLRTableRecordColumnSize(proposed.getJDBCLLRTableRecordColumnSize());
                                    original._conditionalUnset(update.isUnsetUpdate(), 207);
                                 } else if (prop.equals("JDBCLLRTableXIDColumnSize")) {
                                    original.setJDBCLLRTableXIDColumnSize(proposed.getJDBCLLRTableXIDColumnSize());
                                    original._conditionalUnset(update.isUnsetUpdate(), 205);
                                 } else if (prop.equals("JDBCLogFileName")) {
                                    original.setJDBCLogFileName(proposed.getJDBCLogFileName());
                                    original._conditionalUnset(update.isUnsetUpdate(), 104);
                                 } else if (prop.equals("JDBCLoginTimeoutSeconds")) {
                                    original.setJDBCLoginTimeoutSeconds(proposed.getJDBCLoginTimeoutSeconds());
                                    original._conditionalUnset(update.isUnsetUpdate(), 208);
                                 } else if (prop.equals("JMSConnectionFactoryUnmappedResRefMode")) {
                                    original.setJMSConnectionFactoryUnmappedResRefMode(proposed.getJMSConnectionFactoryUnmappedResRefMode());
                                    original._conditionalUnset(update.isUnsetUpdate(), 153);
                                 } else if (prop.equals("JNDITransportableObjectFactoryList")) {
                                    original.setJNDITransportableObjectFactoryList(proposed.getJNDITransportableObjectFactoryList());
                                    original._conditionalUnset(update.isUnsetUpdate(), 135);
                                 } else if (prop.equals("JTAMigratableTarget")) {
                                    if (type == 2) {
                                       original.setJTAMigratableTarget((JTAMigratableTargetMBean)this.createCopy((AbstractDescriptorBean)proposed.getJTAMigratableTarget()));
                                    } else {
                                       if (type != 3) {
                                          throw new AssertionError("Invalid type: " + type);
                                       }

                                       original._destroySingleton("JTAMigratableTarget", original.getJTAMigratableTarget());
                                    }

                                    original._conditionalUnset(update.isUnsetUpdate(), 156);
                                 } else if (prop.equals("JavaCompiler")) {
                                    original.setJavaCompiler(proposed.getJavaCompiler());
                                    original._conditionalUnset(update.isUnsetUpdate(), 139);
                                 } else if (prop.equals("JavaCompilerPostClassPath")) {
                                    original.setJavaCompilerPostClassPath(proposed.getJavaCompilerPostClassPath());
                                    original._conditionalUnset(update.isUnsetUpdate(), 141);
                                 } else if (prop.equals("JavaCompilerPreClassPath")) {
                                    original.setJavaCompilerPreClassPath(proposed.getJavaCompilerPreClassPath());
                                    original._conditionalUnset(update.isUnsetUpdate(), 140);
                                 } else if (!prop.equals("JavaStandardTrustKeyStorePassPhrase")) {
                                    if (prop.equals("JavaStandardTrustKeyStorePassPhraseEncrypted")) {
                                       original.setJavaStandardTrustKeyStorePassPhraseEncrypted(proposed.getJavaStandardTrustKeyStorePassPhraseEncrypted());
                                       original._conditionalUnset(update.isUnsetUpdate(), 197);
                                    } else if (!prop.equals("KernelDebug")) {
                                       if (prop.equals("KeyStores")) {
                                          original.setKeyStores(proposed.getKeyStores());
                                          original._conditionalUnset(update.isUnsetUpdate(), 187);
                                       } else if (prop.equals("ListenAddress")) {
                                          original.setListenAddress(proposed.getListenAddress());
                                          original._conditionalUnset(update.isUnsetUpdate(), 125);
                                       } else if (prop.equals("ListenDelaySecs")) {
                                          original.setListenDelaySecs(proposed.getListenDelaySecs());
                                          original._conditionalUnset(update.isUnsetUpdate(), 155);
                                       } else if (prop.equals("ListenPort")) {
                                          original.setListenPort(proposed.getListenPort());
                                          original._conditionalUnset(update.isUnsetUpdate(), 92);
                                       } else if (prop.equals("ListenThreadStartDelaySecs")) {
                                          original.setListenThreadStartDelaySecs(proposed.getListenThreadStartDelaySecs());
                                          original._conditionalUnset(update.isUnsetUpdate(), 123);
                                       } else if (prop.equals("ListenersBindEarly")) {
                                          original.setListenersBindEarly(proposed.getListenersBindEarly());
                                          original._conditionalUnset(update.isUnsetUpdate(), 124);
                                       } else if (prop.equals("LoginTimeout")) {
                                          original.setLoginTimeout(proposed.getLoginTimeout());
                                          original._conditionalUnset(update.isUnsetUpdate(), 94);
                                       } else if (prop.equals("LoginTimeoutMillis")) {
                                          original.setLoginTimeoutMillis(proposed.getLoginTimeoutMillis());
                                          original._conditionalUnset(update.isUnsetUpdate(), 132);
                                       } else if (prop.equals("LowMemoryGCThreshold")) {
                                          original.setLowMemoryGCThreshold(proposed.getLowMemoryGCThreshold());
                                          original._conditionalUnset(update.isUnsetUpdate(), 160);
                                       } else if (prop.equals("LowMemoryGranularityLevel")) {
                                          original.setLowMemoryGranularityLevel(proposed.getLowMemoryGranularityLevel());
                                          original._conditionalUnset(update.isUnsetUpdate(), 159);
                                       } else if (prop.equals("LowMemorySampleSize")) {
                                          original.setLowMemorySampleSize(proposed.getLowMemorySampleSize());
                                          original._conditionalUnset(update.isUnsetUpdate(), 158);
                                       } else if (prop.equals("LowMemoryTimeInterval")) {
                                          original.setLowMemoryTimeInterval(proposed.getLowMemoryTimeInterval());
                                          original._conditionalUnset(update.isUnsetUpdate(), 157);
                                       } else if (prop.equals("Machine")) {
                                          original.setMachineAsString(proposed.getMachineAsString());
                                          original._conditionalUnset(update.isUnsetUpdate(), 91);
                                       } else if (prop.equals("MaxBackoffBetweenFailures")) {
                                          original.setMaxBackoffBetweenFailures(proposed.getMaxBackoffBetweenFailures());
                                          original._conditionalUnset(update.isUnsetUpdate(), 131);
                                       } else if (prop.equals("MaxConcurrentLongRunningRequests")) {
                                          original.setMaxConcurrentLongRunningRequests(proposed.getMaxConcurrentLongRunningRequests());
                                          original._conditionalUnset(update.isUnsetUpdate(), 228);
                                       } else if (prop.equals("MaxConcurrentNewThreads")) {
                                          original.setMaxConcurrentNewThreads(proposed.getMaxConcurrentNewThreads());
                                          original._conditionalUnset(update.isUnsetUpdate(), 227);
                                       } else if (prop.equals("NMSocketCreateTimeoutInMillis")) {
                                          original.setNMSocketCreateTimeoutInMillis(proposed.getNMSocketCreateTimeoutInMillis());
                                          original._conditionalUnset(update.isUnsetUpdate(), 217);
                                       } else if (prop.equals("Name")) {
                                          original.setName(proposed.getName());
                                          original._conditionalUnset(update.isUnsetUpdate(), 2);
                                       } else if (prop.equals("NetworkAccessPoints")) {
                                          if (type == 2) {
                                             if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                                update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                                original.addNetworkAccessPoint((NetworkAccessPointMBean)update.getAddedObject());
                                             }
                                          } else {
                                             if (type != 3) {
                                                throw new AssertionError("Invalid type: " + type);
                                             }

                                             original.removeNetworkAccessPoint((NetworkAccessPointMBean)update.getRemovedObject());
                                          }

                                          if (original.getNetworkAccessPoints() == null || original.getNetworkAccessPoints().length == 0) {
                                             original._conditionalUnset(update.isUnsetUpdate(), 129);
                                          }
                                       } else if (prop.equals("NumOfRetriesBeforeMSIMode")) {
                                          original.setNumOfRetriesBeforeMSIMode(proposed.getNumOfRetriesBeforeMSIMode());
                                          original._conditionalUnset(update.isUnsetUpdate(), 229);
                                       } else if (prop.equals("OverloadProtection")) {
                                          if (type == 2) {
                                             original.setOverloadProtection((OverloadProtectionMBean)this.createCopy((AbstractDescriptorBean)proposed.getOverloadProtection()));
                                          } else {
                                             if (type != 3) {
                                                throw new AssertionError("Invalid type: " + type);
                                             }

                                             original._destroySingleton("OverloadProtection", original.getOverloadProtection());
                                          }

                                          original._conditionalUnset(update.isUnsetUpdate(), 202);
                                       } else if (!prop.equals("Parent")) {
                                          if (prop.equals("PreferredSecondaryGroup")) {
                                             original.setPreferredSecondaryGroup(proposed.getPreferredSecondaryGroup());
                                             original._conditionalUnset(update.isUnsetUpdate(), 98);
                                          } else if (prop.equals("ReliableDeliveryPolicy")) {
                                             original.setReliableDeliveryPolicyAsString(proposed.getReliableDeliveryPolicyAsString());
                                             original._conditionalUnset(update.isUnsetUpdate(), 198);
                                          } else if (prop.equals("ReplicationGroup")) {
                                             original.setReplicationGroup(proposed.getReplicationGroup());
                                             original._conditionalUnset(update.isUnsetUpdate(), 97);
                                          } else if (prop.equals("ReplicationPorts")) {
                                             original.setReplicationPorts(proposed.getReplicationPorts());
                                             original._conditionalUnset(update.isUnsetUpdate(), 220);
                                          } else if (prop.equals("ResolveDNSName")) {
                                             original.setResolveDNSName(proposed.getResolveDNSName());
                                             original._conditionalUnset(update.isUnsetUpdate(), 127);
                                          } else if (prop.equals("RestartDelaySeconds")) {
                                             original.setRestartDelaySeconds(proposed.getRestartDelaySeconds());
                                             original._conditionalUnset(update.isUnsetUpdate(), 172);
                                          } else if (prop.equals("RestartIntervalSeconds")) {
                                             original.setRestartIntervalSeconds(proposed.getRestartIntervalSeconds());
                                             original._conditionalUnset(update.isUnsetUpdate(), 167);
                                          } else if (prop.equals("RestartMax")) {
                                             original.setRestartMax(proposed.getRestartMax());
                                             original._conditionalUnset(update.isUnsetUpdate(), 168);
                                          } else if (prop.equals("RetryIntervalBeforeMSIMode")) {
                                             original.setRetryIntervalBeforeMSIMode(proposed.getRetryIntervalBeforeMSIMode());
                                             original._conditionalUnset(update.isUnsetUpdate(), 230);
                                          } else if (!prop.equals("RootDirectory")) {
                                             if (prop.equals("ServerDebug")) {
                                                if (type == 2) {
                                                   original.setServerDebug((ServerDebugMBean)this.createCopy((AbstractDescriptorBean)proposed.getServerDebug()));
                                                } else {
                                                   if (type != 3) {
                                                      throw new AssertionError("Invalid type: " + type);
                                                   }

                                                   original._destroySingleton("ServerDebug", original.getServerDebug());
                                                }

                                                original._conditionalUnset(update.isUnsetUpdate(), 118);
                                             } else if (prop.equals("ServerDiagnosticConfig")) {
                                                if (type == 2) {
                                                   original.setServerDiagnosticConfig((WLDFServerDiagnosticMBean)this.createCopy((AbstractDescriptorBean)proposed.getServerDiagnosticConfig()));
                                                } else {
                                                   if (type != 3) {
                                                      throw new AssertionError("Invalid type: " + type);
                                                   }

                                                   original._destroySingleton("ServerDiagnosticConfig", original.getServerDiagnosticConfig());
                                                }

                                                original._conditionalUnset(update.isUnsetUpdate(), 209);
                                             } else if (prop.equals("ServerLifeCycleTimeoutVal")) {
                                                original.setServerLifeCycleTimeoutVal(proposed.getServerLifeCycleTimeoutVal());
                                                original._conditionalUnset(update.isUnsetUpdate(), 177);
                                             } else if (!prop.equals("ServerNames")) {
                                                if (prop.equals("ServerStart")) {
                                                   if (type == 2) {
                                                      original.setServerStart((ServerStartMBean)this.createCopy((AbstractDescriptorBean)proposed.getServerStart()));
                                                   } else {
                                                      if (type != 3) {
                                                         throw new AssertionError("Invalid type: " + type);
                                                      }

                                                      original._destroySingleton("ServerStart", original.getServerStart());
                                                   }

                                                   original._conditionalUnset(update.isUnsetUpdate(), 154);
                                                } else if (prop.equals("ServerVersion")) {
                                                   original.setServerVersion(proposed.getServerVersion());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 175);
                                                } else if (prop.equals("SingleSignOnServices")) {
                                                   if (type == 2) {
                                                      original.setSingleSignOnServices((SingleSignOnServicesMBean)this.createCopy((AbstractDescriptorBean)proposed.getSingleSignOnServices()));
                                                   } else {
                                                      if (type != 3) {
                                                         throw new AssertionError("Invalid type: " + type);
                                                      }

                                                      original._destroySingleton("SingleSignOnServices", original.getSingleSignOnServices());
                                                   }

                                                   original._conditionalUnset(update.isUnsetUpdate(), 215);
                                                } else if (prop.equals("SitConfigPollingInterval")) {
                                                   original.setSitConfigPollingInterval(proposed.getSitConfigPollingInterval());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 233);
                                                } else if (prop.equals("StagingDirectoryName")) {
                                                   original.setStagingDirectoryName(proposed.getStagingDirectoryName());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 161);
                                                } else if (prop.equals("StagingMode")) {
                                                   original.setStagingMode(proposed.getStagingMode());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 164);
                                                } else if (prop.equals("StartupMode")) {
                                                   original.setStartupMode(proposed.getStartupMode());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 176);
                                                } else if (prop.equals("StartupTimeout")) {
                                                   original.setStartupTimeout(proposed.getStartupTimeout());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 178);
                                                } else if (prop.equals("StdoutFormat")) {
                                                   original.setStdoutFormat(proposed.getStdoutFormat());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 64);
                                                } else if (prop.equals("StdoutSeverityLevel")) {
                                                   original.setStdoutSeverityLevel(proposed.getStdoutSeverityLevel());
                                                   original._conditionalUnset(update.isUnsetUpdate(), 57);
                                                } else if (prop.equals("SupportedProtocols")) {
                                                   original._conditionalUnset(update.isUnsetUpdate(), 211);
                                                } else if (!prop.equals("SystemPassword")) {
                                                   if (prop.equals("SystemPasswordEncrypted")) {
                                                      original.setSystemPasswordEncrypted(proposed.getSystemPasswordEncrypted());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 121);
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
                                                   } else if (prop.equals("ThreadPoolSize")) {
                                                      original.setThreadPoolSize(proposed.getThreadPoolSize());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 15);
                                                   } else if (prop.equals("TransactionLogFilePrefix")) {
                                                      original.setTransactionLogFilePrefix(proposed.getTransactionLogFilePrefix());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 145);
                                                   } else if (prop.equals("TransactionLogFileWritePolicy")) {
                                                      original.setTransactionLogFileWritePolicy(proposed.getTransactionLogFileWritePolicy());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 146);
                                                   } else if (prop.equals("TransactionLogJDBCStore")) {
                                                      if (type == 2) {
                                                         original.setTransactionLogJDBCStore((TransactionLogJDBCStoreMBean)this.createCopy((AbstractDescriptorBean)proposed.getTransactionLogJDBCStore()));
                                                      } else {
                                                         if (type != 3) {
                                                            throw new AssertionError("Invalid type: " + type);
                                                         }

                                                         original._destroySingleton("TransactionLogJDBCStore", original.getTransactionLogJDBCStore());
                                                      }

                                                      original._conditionalUnset(update.isUnsetUpdate(), 221);
                                                   } else if (prop.equals("TransactionPrimaryChannelName")) {
                                                      original.setTransactionPrimaryChannelName(proposed.getTransactionPrimaryChannelName());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 236);
                                                   } else if (prop.equals("TransactionPublicChannelName")) {
                                                      original.setTransactionPublicChannelName(proposed.getTransactionPublicChannelName());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 238);
                                                   } else if (prop.equals("TransactionPublicSecureChannelName")) {
                                                      original.setTransactionPublicSecureChannelName(proposed.getTransactionPublicSecureChannelName());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 239);
                                                   } else if (prop.equals("TransactionSecureChannelName")) {
                                                      original.setTransactionSecureChannelName(proposed.getTransactionSecureChannelName());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 237);
                                                   } else if (prop.equals("TunnelingClientPingSecs")) {
                                                      original.setTunnelingClientPingSecs(proposed.getTunnelingClientPingSecs());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 149);
                                                   } else if (prop.equals("TunnelingClientTimeoutSecs")) {
                                                      original.setTunnelingClientTimeoutSecs(proposed.getTunnelingClientTimeoutSecs());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 150);
                                                   } else if (prop.equals("UploadDirectoryName")) {
                                                      original.setUploadDirectoryName(proposed.getUploadDirectoryName());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 162);
                                                   } else if (prop.equals("VerboseEJBDeploymentEnabled")) {
                                                      original.setVerboseEJBDeploymentEnabled(proposed.getVerboseEJBDeploymentEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 144);
                                                   } else if (prop.equals("VirtualMachineName")) {
                                                      original.setVirtualMachineName(proposed.getVirtualMachineName());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 219);
                                                   } else if (prop.equals("WebServer")) {
                                                      if (type == 2) {
                                                         original.setWebServer((WebServerMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServer()));
                                                      } else {
                                                         if (type != 3) {
                                                            throw new AssertionError("Invalid type: " + type);
                                                         }

                                                         original._destroySingleton("WebServer", original.getWebServer());
                                                      }

                                                      original._conditionalUnset(update.isUnsetUpdate(), 101);
                                                   } else if (prop.equals("WebService")) {
                                                      if (type == 2) {
                                                         original.setWebService((WebServiceMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebService()));
                                                      } else {
                                                         if (type != 3) {
                                                            throw new AssertionError("Invalid type: " + type);
                                                         }

                                                         original._destroySingleton("WebService", original.getWebService());
                                                      }

                                                      original._conditionalUnset(update.isUnsetUpdate(), 216);
                                                   } else if (prop.equals("XMLEntityCache")) {
                                                      original.setXMLEntityCacheAsString(proposed.getXMLEntityCacheAsString());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 138);
                                                   } else if (prop.equals("XMLRegistry")) {
                                                      original.setXMLRegistryAsString(proposed.getXMLRegistryAsString());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 137);
                                                   } else if (prop.equals("AdministrationPortEnabled")) {
                                                      original.setAdministrationPortEnabled(proposed.isAdministrationPortEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 133);
                                                   } else if (prop.equals("AutoMigrationEnabled")) {
                                                      original.setAutoMigrationEnabled(proposed.isAutoMigrationEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 100);
                                                   } else if (prop.equals("BuzzEnabled")) {
                                                      original.setBuzzEnabled(proposed.isBuzzEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 226);
                                                   } else if (prop.equals("COMEnabled")) {
                                                      original.setCOMEnabled(proposed.isCOMEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 115);
                                                   } else if (prop.equals("ClasspathServletDisabled")) {
                                                      original.setClasspathServletDisabled(proposed.isClasspathServletDisabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 173);
                                                   } else if (prop.equals("ClasspathServletSecureModeEnabled")) {
                                                      original.setClasspathServletSecureModeEnabled(proposed.isClasspathServletSecureModeEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 232);
                                                   } else if (prop.equals("CleanupOrphanedSessionsEnabled")) {
                                                      original.setCleanupOrphanedSessionsEnabled(proposed.isCleanupOrphanedSessionsEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 235);
                                                   } else if (prop.equals("ClientCertProxyEnabled")) {
                                                      original.setClientCertProxyEnabled(proposed.isClientCertProxyEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 183);
                                                   } else if (prop.equals("ConsoleInputEnabled")) {
                                                      original.setConsoleInputEnabled(proposed.isConsoleInputEnabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 122);
                                                   } else if (prop.equals("DefaultInternalServletsDisabled")) {
                                                      original.setDefaultInternalServletsDisabled(proposed.isDefaultInternalServletsDisabled());
                                                      original._conditionalUnset(update.isUnsetUpdate(), 174);
                                                   } else if (!prop.equals("DynamicallyCreated")) {
                                                      if (prop.equals("HttpTraceSupportEnabled")) {
                                                         original.setHttpTraceSupportEnabled(proposed.isHttpTraceSupportEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 186);
                                                      } else if (prop.equals("HttpdEnabled")) {
                                                         original.setHttpdEnabled(proposed.isHttpdEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 119);
                                                      } else if (prop.equals("IIOPEnabled")) {
                                                         original.setIIOPEnabled(proposed.isIIOPEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 107);
                                                      } else if (prop.equals("IgnoreSessionsDuringShutdown")) {
                                                         original.setIgnoreSessionsDuringShutdown(proposed.isIgnoreSessionsDuringShutdown());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 180);
                                                      } else if (prop.equals("J2EE12OnlyModeEnabled")) {
                                                         original.setJ2EE12OnlyModeEnabled(proposed.isJ2EE12OnlyModeEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 105);
                                                      } else if (prop.equals("J2EE13WarningEnabled")) {
                                                         original.setJ2EE13WarningEnabled(proposed.isJ2EE13WarningEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 106);
                                                      } else if (prop.equals("JDBCLoggingEnabled")) {
                                                         original.setJDBCLoggingEnabled(proposed.isJDBCLoggingEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 103);
                                                      } else if (prop.equals("JMSDefaultConnectionFactoriesEnabled")) {
                                                         original.setJMSDefaultConnectionFactoriesEnabled(proposed.isJMSDefaultConnectionFactoriesEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 152);
                                                      } else if (prop.equals("JRMPEnabled")) {
                                                         original.setJRMPEnabled(proposed.isJRMPEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 116);
                                                      } else if (prop.equals("ListenPortEnabled")) {
                                                         original.setListenPortEnabled(proposed.isListenPortEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 93);
                                                      } else if (prop.equals("MSIFileReplicationEnabled")) {
                                                         original.setMSIFileReplicationEnabled(proposed.isMSIFileReplicationEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 182);
                                                      } else if (prop.equals("ManagedServerIndependenceEnabled")) {
                                                         original.setManagedServerIndependenceEnabled(proposed.isManagedServerIndependenceEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 181);
                                                      } else if (prop.equals("MessageIdPrefixEnabled")) {
                                                         original.setMessageIdPrefixEnabled(proposed.isMessageIdPrefixEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 199);
                                                      } else if (prop.equals("NetworkClassLoadingEnabled")) {
                                                         original.setNetworkClassLoadingEnabled(proposed.isNetworkClassLoadingEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 147);
                                                      } else if (prop.equals("SessionReplicationOnShutdownEnabled")) {
                                                         original.setSessionReplicationOnShutdownEnabled(proposed.isSessionReplicationOnShutdownEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 234);
                                                      } else if (prop.equals("SitConfigRequired")) {
                                                         original.setSitConfigRequired(proposed.isSitConfigRequired());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 240);
                                                      } else if (prop.equals("StdoutDebugEnabled")) {
                                                         original.setStdoutDebugEnabled(proposed.isStdoutDebugEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 58);
                                                      } else if (prop.equals("StdoutEnabled")) {
                                                         original.setStdoutEnabled(proposed.isStdoutEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 56);
                                                      } else if (prop.equals("StdoutLogStack")) {
                                                         original.setStdoutLogStack(proposed.isStdoutLogStack());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 65);
                                                      } else if (prop.equals("TGIOPEnabled")) {
                                                         original.setTGIOPEnabled(proposed.isTGIOPEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 111);
                                                      } else if (prop.equals("TunnelingEnabled")) {
                                                         original.setTunnelingEnabled(proposed.isTunnelingEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 148);
                                                      } else if (prop.equals("UseFusionForLLR")) {
                                                         original.setUseFusionForLLR(proposed.isUseFusionForLLR());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 204);
                                                      } else if (prop.equals("WeblogicPluginEnabled")) {
                                                         original.setWeblogicPluginEnabled(proposed.isWeblogicPluginEnabled());
                                                         original._conditionalUnset(update.isUnsetUpdate(), 184);
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
            ServerTemplateMBeanImpl copy = (ServerTemplateMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcceptBacklog")) && this.bean.isAcceptBacklogSet()) {
               copy.setAcceptBacklog(this.bean.getAcceptBacklog());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ActiveDirectoryName")) && this.bean.isActiveDirectoryNameSet()) {
               copy.setActiveDirectoryName(this.bean.getActiveDirectoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("AdminReconnectIntervalSeconds")) && this.bean.isAdminReconnectIntervalSecondsSet()) {
               copy.setAdminReconnectIntervalSeconds(this.bean.getAdminReconnectIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("AdministrationPort")) && this.bean.isAdministrationPortSet()) {
               copy.setAdministrationPort(this.bean.getAdministrationPort());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoJDBCConnectionClose")) && this.bean.isAutoJDBCConnectionCloseSet()) {
               copy.setAutoJDBCConnectionClose(this.bean.getAutoJDBCConnectionClose());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoKillIfFailed")) && this.bean.isAutoKillIfFailedSet()) {
               copy.setAutoKillIfFailed(this.bean.getAutoKillIfFailed());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoRestart")) && this.bean.isAutoRestartSet()) {
               copy.setAutoRestart(this.bean.getAutoRestart());
            }

            if ((excludeProps == null || !excludeProps.contains("BuzzAddress")) && this.bean.isBuzzAddressSet()) {
               copy.setBuzzAddress(this.bean.getBuzzAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("BuzzPort")) && this.bean.isBuzzPortSet()) {
               copy.setBuzzPort(this.bean.getBuzzPort());
            }

            if ((excludeProps == null || !excludeProps.contains("COM")) && this.bean.isCOMSet() && !copy._isSet(117)) {
               Object o = this.bean.getCOM();
               copy.setCOM((COMMBean)null);
               copy.setCOM(o == null ? null : (COMMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CandidateMachines")) && this.bean.isCandidateMachinesSet()) {
               copy._unSet(copy, 201);
               copy.setCandidateMachinesAsString(this.bean.getCandidateMachinesAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Cluster")) && this.bean.isClusterSet()) {
               copy._unSet(copy, 95);
               copy.setClusterAsString(this.bean.getClusterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterWeight")) && this.bean.isClusterWeightSet()) {
               copy.setClusterWeight(this.bean.getClusterWeight());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterSystemResource")) && this.bean.isCoherenceClusterSystemResourceSet()) {
               copy._unSet(copy, 218);
               copy.setCoherenceClusterSystemResourceAsString(this.bean.getCoherenceClusterSystemResourceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceMemberConfig")) && this.bean.isCoherenceMemberConfigSet() && !copy._isSet(223)) {
               Object o = this.bean.getCoherenceMemberConfig();
               copy.setCoherenceMemberConfig((CoherenceMemberConfigMBean)null);
               copy.setCoherenceMemberConfig(o == null ? null : (CoherenceMemberConfigMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ConfigurationProperties")) && this.bean.isConfigurationPropertiesSet() && !copy._isSet(89)) {
               ConfigurationPropertyMBean[] oldConfigurationProperties = this.bean.getConfigurationProperties();
               ConfigurationPropertyMBean[] newConfigurationProperties = new ConfigurationPropertyMBean[oldConfigurationProperties.length];

               for(i = 0; i < newConfigurationProperties.length; ++i) {
                  newConfigurationProperties[i] = (ConfigurationPropertyMBean)((ConfigurationPropertyMBean)this.createCopy((AbstractDescriptorBean)oldConfigurationProperties[i], includeObsolete));
               }

               copy.setConfigurationProperties(newConfigurationProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("ConsensusProcessIdentifier")) && this.bean.isConsensusProcessIdentifierSet()) {
               copy.setConsensusProcessIdentifier(this.bean.getConsensusProcessIdentifier());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomIdentityKeyStoreFileName")) && this.bean.isCustomIdentityKeyStoreFileNameSet()) {
               copy.setCustomIdentityKeyStoreFileName(this.bean.getCustomIdentityKeyStoreFileName());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("CustomIdentityKeyStorePassPhraseEncrypted")) && this.bean.isCustomIdentityKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getCustomIdentityKeyStorePassPhraseEncrypted();
               copy.setCustomIdentityKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomIdentityKeyStoreType")) && this.bean.isCustomIdentityKeyStoreTypeSet()) {
               copy.setCustomIdentityKeyStoreType(this.bean.getCustomIdentityKeyStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomTrustKeyStoreFileName")) && this.bean.isCustomTrustKeyStoreFileNameSet()) {
               copy.setCustomTrustKeyStoreFileName(this.bean.getCustomTrustKeyStoreFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomTrustKeyStorePassPhraseEncrypted")) && this.bean.isCustomTrustKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getCustomTrustKeyStorePassPhraseEncrypted();
               copy.setCustomTrustKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomTrustKeyStoreType")) && this.bean.isCustomTrustKeyStoreTypeSet()) {
               copy.setCustomTrustKeyStoreType(this.bean.getCustomTrustKeyStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSource")) && this.bean.isDataSourceSet() && !copy._isSet(222)) {
               Object o = this.bean.getDataSource();
               copy.setDataSource((DataSourceMBean)null);
               copy.setDataSource(o == null ? null : (DataSourceMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultFileStore")) && this.bean.isDefaultFileStoreSet() && !copy._isSet(200)) {
               Object o = this.bean.getDefaultFileStore();
               copy.setDefaultFileStore((DefaultFileStoreMBean)null);
               copy.setDefaultFileStore(o == null ? null : (DefaultFileStoreMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultIIOPPasswordEncrypted")) && this.bean.isDefaultIIOPPasswordEncryptedSet()) {
               o = this.bean.getDefaultIIOPPasswordEncrypted();
               copy.setDefaultIIOPPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultIIOPUser")) && this.bean.isDefaultIIOPUserSet()) {
               copy.setDefaultIIOPUser(this.bean.getDefaultIIOPUser());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTGIOPPasswordEncrypted")) && this.bean.isDefaultTGIOPPasswordEncryptedSet()) {
               o = this.bean.getDefaultTGIOPPasswordEncrypted();
               copy.setDefaultTGIOPPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTGIOPUser")) && this.bean.isDefaultTGIOPUserSet()) {
               copy.setDefaultTGIOPUser(this.bean.getDefaultTGIOPUser());
            }

            if ((excludeProps == null || !excludeProps.contains("ExternalDNSName")) && this.bean.isExternalDNSNameSet()) {
               copy.setExternalDNSName(this.bean.getExternalDNSName());
            }

            if ((excludeProps == null || !excludeProps.contains("ExtraEjbcOptions")) && this.bean.isExtraEjbcOptionsSet()) {
               copy.setExtraEjbcOptions(this.bean.getExtraEjbcOptions());
            }

            if ((excludeProps == null || !excludeProps.contains("ExtraRmicOptions")) && this.bean.isExtraRmicOptionsSet()) {
               copy.setExtraRmicOptions(this.bean.getExtraRmicOptions());
            }

            if ((excludeProps == null || !excludeProps.contains("FederationServices")) && this.bean.isFederationServicesSet() && !copy._isSet(214)) {
               Object o = this.bean.getFederationServices();
               copy.setFederationServices((FederationServicesMBean)null);
               copy.setFederationServices(o == null ? null : (FederationServicesMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GracefulShutdownTimeout")) && this.bean.isGracefulShutdownTimeoutSet()) {
               copy.setGracefulShutdownTimeout(this.bean.getGracefulShutdownTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("HealthCheckIntervalSeconds")) && this.bean.isHealthCheckIntervalSecondsSet()) {
               copy.setHealthCheckIntervalSeconds(this.bean.getHealthCheckIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("HealthCheckStartDelaySeconds")) && this.bean.isHealthCheckStartDelaySecondsSet()) {
               copy.setHealthCheckStartDelaySeconds(this.bean.getHealthCheckStartDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("HealthCheckTimeoutSeconds")) && this.bean.isHealthCheckTimeoutSecondsSet()) {
               copy.setHealthCheckTimeoutSeconds(this.bean.getHealthCheckTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("HostsMigratableServices")) && this.bean.isHostsMigratableServicesSet()) {
               copy.setHostsMigratableServices(this.bean.getHostsMigratableServices());
            }

            if ((excludeProps == null || !excludeProps.contains("IIOPConnectionPools")) && this.bean.isIIOPConnectionPoolsSet()) {
               copy.setIIOPConnectionPools(this.bean.getIIOPConnectionPools());
            }

            if ((excludeProps == null || !excludeProps.contains("InterfaceAddress")) && this.bean.isInterfaceAddressSet()) {
               copy.setInterfaceAddress(this.bean.getInterfaceAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCLLRTableName")) && this.bean.isJDBCLLRTableNameSet()) {
               copy.setJDBCLLRTableName(this.bean.getJDBCLLRTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCLLRTablePoolColumnSize")) && this.bean.isJDBCLLRTablePoolColumnSizeSet()) {
               copy.setJDBCLLRTablePoolColumnSize(this.bean.getJDBCLLRTablePoolColumnSize());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCLLRTableRecordColumnSize")) && this.bean.isJDBCLLRTableRecordColumnSizeSet()) {
               copy.setJDBCLLRTableRecordColumnSize(this.bean.getJDBCLLRTableRecordColumnSize());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCLLRTableXIDColumnSize")) && this.bean.isJDBCLLRTableXIDColumnSizeSet()) {
               copy.setJDBCLLRTableXIDColumnSize(this.bean.getJDBCLLRTableXIDColumnSize());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JDBCLogFileName")) && this.bean.isJDBCLogFileNameSet()) {
               copy.setJDBCLogFileName(this.bean.getJDBCLogFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCLoginTimeoutSeconds")) && this.bean.isJDBCLoginTimeoutSecondsSet()) {
               copy.setJDBCLoginTimeoutSeconds(this.bean.getJDBCLoginTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSConnectionFactoryUnmappedResRefMode")) && this.bean.isJMSConnectionFactoryUnmappedResRefModeSet()) {
               copy.setJMSConnectionFactoryUnmappedResRefMode(this.bean.getJMSConnectionFactoryUnmappedResRefMode());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("JNDITransportableObjectFactoryList")) && this.bean.isJNDITransportableObjectFactoryListSet()) {
               o = this.bean.getJNDITransportableObjectFactoryList();
               copy.setJNDITransportableObjectFactoryList(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("JTAMigratableTarget")) && this.bean.isJTAMigratableTargetSet() && !copy._isSet(156)) {
               Object o = this.bean.getJTAMigratableTarget();
               copy.setJTAMigratableTarget((JTAMigratableTargetMBean)null);
               copy.setJTAMigratableTarget(o == null ? null : (JTAMigratableTargetMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JavaCompiler")) && this.bean.isJavaCompilerSet()) {
               copy.setJavaCompiler(this.bean.getJavaCompiler());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaCompilerPostClassPath")) && this.bean.isJavaCompilerPostClassPathSet()) {
               copy.setJavaCompilerPostClassPath(this.bean.getJavaCompilerPostClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaCompilerPreClassPath")) && this.bean.isJavaCompilerPreClassPathSet()) {
               copy.setJavaCompilerPreClassPath(this.bean.getJavaCompilerPreClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaStandardTrustKeyStorePassPhraseEncrypted")) && this.bean.isJavaStandardTrustKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getJavaStandardTrustKeyStorePassPhraseEncrypted();
               copy.setJavaStandardTrustKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("KeyStores")) && this.bean.isKeyStoresSet()) {
               copy.setKeyStores(this.bean.getKeyStores());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenAddress")) && this.bean.isListenAddressSet()) {
               copy.setListenAddress(this.bean.getListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenDelaySecs")) && this.bean.isListenDelaySecsSet()) {
               copy.setListenDelaySecs(this.bean.getListenDelaySecs());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPort")) && this.bean.isListenPortSet()) {
               copy.setListenPort(this.bean.getListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenThreadStartDelaySecs")) && this.bean.isListenThreadStartDelaySecsSet()) {
               copy.setListenThreadStartDelaySecs(this.bean.getListenThreadStartDelaySecs());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenersBindEarly")) && this.bean.isListenersBindEarlySet()) {
               copy.setListenersBindEarly(this.bean.getListenersBindEarly());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LoginTimeout")) && this.bean.isLoginTimeoutSet()) {
               copy.setLoginTimeout(this.bean.getLoginTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginTimeoutMillis")) && this.bean.isLoginTimeoutMillisSet()) {
               copy.setLoginTimeoutMillis(this.bean.getLoginTimeoutMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("LowMemoryGCThreshold")) && this.bean.isLowMemoryGCThresholdSet()) {
               copy.setLowMemoryGCThreshold(this.bean.getLowMemoryGCThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("LowMemoryGranularityLevel")) && this.bean.isLowMemoryGranularityLevelSet()) {
               copy.setLowMemoryGranularityLevel(this.bean.getLowMemoryGranularityLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("LowMemorySampleSize")) && this.bean.isLowMemorySampleSizeSet()) {
               copy.setLowMemorySampleSize(this.bean.getLowMemorySampleSize());
            }

            if ((excludeProps == null || !excludeProps.contains("LowMemoryTimeInterval")) && this.bean.isLowMemoryTimeIntervalSet()) {
               copy.setLowMemoryTimeInterval(this.bean.getLowMemoryTimeInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("Machine")) && this.bean.isMachineSet()) {
               copy._unSet(copy, 91);
               copy.setMachineAsString(this.bean.getMachineAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxBackoffBetweenFailures")) && this.bean.isMaxBackoffBetweenFailuresSet()) {
               copy.setMaxBackoffBetweenFailures(this.bean.getMaxBackoffBetweenFailures());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentLongRunningRequests")) && this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               copy.setMaxConcurrentLongRunningRequests(this.bean.getMaxConcurrentLongRunningRequests());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentNewThreads")) && this.bean.isMaxConcurrentNewThreadsSet()) {
               copy.setMaxConcurrentNewThreads(this.bean.getMaxConcurrentNewThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("NMSocketCreateTimeoutInMillis")) && this.bean.isNMSocketCreateTimeoutInMillisSet()) {
               copy.setNMSocketCreateTimeoutInMillis(this.bean.getNMSocketCreateTimeoutInMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NetworkAccessPoints")) && this.bean.isNetworkAccessPointsSet() && !copy._isSet(129)) {
               NetworkAccessPointMBean[] oldNetworkAccessPoints = this.bean.getNetworkAccessPoints();
               NetworkAccessPointMBean[] newNetworkAccessPoints = new NetworkAccessPointMBean[oldNetworkAccessPoints.length];

               for(i = 0; i < newNetworkAccessPoints.length; ++i) {
                  newNetworkAccessPoints[i] = (NetworkAccessPointMBean)((NetworkAccessPointMBean)this.createCopy((AbstractDescriptorBean)oldNetworkAccessPoints[i], includeObsolete));
               }

               copy.setNetworkAccessPoints(newNetworkAccessPoints);
            }

            if ((excludeProps == null || !excludeProps.contains("NumOfRetriesBeforeMSIMode")) && this.bean.isNumOfRetriesBeforeMSIModeSet()) {
               copy.setNumOfRetriesBeforeMSIMode(this.bean.getNumOfRetriesBeforeMSIMode());
            }

            if ((excludeProps == null || !excludeProps.contains("OverloadProtection")) && this.bean.isOverloadProtectionSet() && !copy._isSet(202)) {
               Object o = this.bean.getOverloadProtection();
               copy.setOverloadProtection((OverloadProtectionMBean)null);
               copy.setOverloadProtection(o == null ? null : (OverloadProtectionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PreferredSecondaryGroup")) && this.bean.isPreferredSecondaryGroupSet()) {
               copy.setPreferredSecondaryGroup(this.bean.getPreferredSecondaryGroup());
            }

            if ((excludeProps == null || !excludeProps.contains("ReliableDeliveryPolicy")) && this.bean.isReliableDeliveryPolicySet()) {
               copy._unSet(copy, 198);
               copy.setReliableDeliveryPolicyAsString(this.bean.getReliableDeliveryPolicyAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplicationGroup")) && this.bean.isReplicationGroupSet()) {
               copy.setReplicationGroup(this.bean.getReplicationGroup());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplicationPorts")) && this.bean.isReplicationPortsSet()) {
               copy.setReplicationPorts(this.bean.getReplicationPorts());
            }

            if ((excludeProps == null || !excludeProps.contains("ResolveDNSName")) && this.bean.isResolveDNSNameSet()) {
               copy.setResolveDNSName(this.bean.getResolveDNSName());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartDelaySeconds")) && this.bean.isRestartDelaySecondsSet()) {
               copy.setRestartDelaySeconds(this.bean.getRestartDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartIntervalSeconds")) && this.bean.isRestartIntervalSecondsSet()) {
               copy.setRestartIntervalSeconds(this.bean.getRestartIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartMax")) && this.bean.isRestartMaxSet()) {
               copy.setRestartMax(this.bean.getRestartMax());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryIntervalBeforeMSIMode")) && this.bean.isRetryIntervalBeforeMSIModeSet()) {
               copy.setRetryIntervalBeforeMSIMode(this.bean.getRetryIntervalBeforeMSIMode());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerDebug")) && this.bean.isServerDebugSet() && !copy._isSet(118)) {
               Object o = this.bean.getServerDebug();
               copy.setServerDebug((ServerDebugMBean)null);
               copy.setServerDebug(o == null ? null : (ServerDebugMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServerDiagnosticConfig")) && this.bean.isServerDiagnosticConfigSet() && !copy._isSet(209)) {
               Object o = this.bean.getServerDiagnosticConfig();
               copy.setServerDiagnosticConfig((WLDFServerDiagnosticMBean)null);
               copy.setServerDiagnosticConfig(o == null ? null : (WLDFServerDiagnosticMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServerLifeCycleTimeoutVal")) && this.bean.isServerLifeCycleTimeoutValSet()) {
               copy.setServerLifeCycleTimeoutVal(this.bean.getServerLifeCycleTimeoutVal());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerStart")) && this.bean.isServerStartSet() && !copy._isSet(154)) {
               Object o = this.bean.getServerStart();
               copy.setServerStart((ServerStartMBean)null);
               copy.setServerStart(o == null ? null : (ServerStartMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServerVersion")) && this.bean.isServerVersionSet()) {
               copy.setServerVersion(this.bean.getServerVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("SingleSignOnServices")) && this.bean.isSingleSignOnServicesSet() && !copy._isSet(215)) {
               Object o = this.bean.getSingleSignOnServices();
               copy.setSingleSignOnServices((SingleSignOnServicesMBean)null);
               copy.setSingleSignOnServices(o == null ? null : (SingleSignOnServicesMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SitConfigPollingInterval")) && this.bean.isSitConfigPollingIntervalSet()) {
               copy.setSitConfigPollingInterval(this.bean.getSitConfigPollingInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("StagingDirectoryName")) && this.bean.isStagingDirectoryNameSet()) {
               copy.setStagingDirectoryName(this.bean.getStagingDirectoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("StagingMode")) && this.bean.isStagingModeSet()) {
               copy.setStagingMode(this.bean.getStagingMode());
            }

            if ((excludeProps == null || !excludeProps.contains("StartupMode")) && this.bean.isStartupModeSet()) {
               copy.setStartupMode(this.bean.getStartupMode());
            }

            if ((excludeProps == null || !excludeProps.contains("StartupTimeout")) && this.bean.isStartupTimeoutSet()) {
               copy.setStartupTimeout(this.bean.getStartupTimeout());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutFormat")) && this.bean.isStdoutFormatSet()) {
               copy.setStdoutFormat(this.bean.getStdoutFormat());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutSeverityLevel")) && this.bean.isStdoutSeverityLevelSet()) {
               copy.setStdoutSeverityLevel(this.bean.getStdoutSeverityLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportedProtocols")) && this.bean.isSupportedProtocolsSet()) {
               o = this.bean.getSupportedProtocols();
               copy.setSupportedProtocols(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("SystemPasswordEncrypted")) && this.bean.isSystemPasswordEncryptedSet()) {
               o = this.bean.getSystemPasswordEncrypted();
               copy.setSystemPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadPoolSize")) && this.bean.isThreadPoolSizeSet()) {
               copy.setThreadPoolSize(this.bean.getThreadPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionLogFilePrefix")) && this.bean.isTransactionLogFilePrefixSet()) {
               copy.setTransactionLogFilePrefix(this.bean.getTransactionLogFilePrefix());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionLogFileWritePolicy")) && this.bean.isTransactionLogFileWritePolicySet()) {
               copy.setTransactionLogFileWritePolicy(this.bean.getTransactionLogFileWritePolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionLogJDBCStore")) && this.bean.isTransactionLogJDBCStoreSet() && !copy._isSet(221)) {
               Object o = this.bean.getTransactionLogJDBCStore();
               copy.setTransactionLogJDBCStore((TransactionLogJDBCStoreMBean)null);
               copy.setTransactionLogJDBCStore(o == null ? null : (TransactionLogJDBCStoreMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionPrimaryChannelName")) && this.bean.isTransactionPrimaryChannelNameSet()) {
               copy.setTransactionPrimaryChannelName(this.bean.getTransactionPrimaryChannelName());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionPublicChannelName")) && this.bean.isTransactionPublicChannelNameSet()) {
               copy.setTransactionPublicChannelName(this.bean.getTransactionPublicChannelName());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionPublicSecureChannelName")) && this.bean.isTransactionPublicSecureChannelNameSet()) {
               copy.setTransactionPublicSecureChannelName(this.bean.getTransactionPublicSecureChannelName());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionSecureChannelName")) && this.bean.isTransactionSecureChannelNameSet()) {
               copy.setTransactionSecureChannelName(this.bean.getTransactionSecureChannelName());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingClientPingSecs")) && this.bean.isTunnelingClientPingSecsSet()) {
               copy.setTunnelingClientPingSecs(this.bean.getTunnelingClientPingSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingClientTimeoutSecs")) && this.bean.isTunnelingClientTimeoutSecsSet()) {
               copy.setTunnelingClientTimeoutSecs(this.bean.getTunnelingClientTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("UploadDirectoryName")) && this.bean.isUploadDirectoryNameSet()) {
               copy.setUploadDirectoryName(this.bean.getUploadDirectoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("VerboseEJBDeploymentEnabled")) && this.bean.isVerboseEJBDeploymentEnabledSet()) {
               copy.setVerboseEJBDeploymentEnabled(this.bean.getVerboseEJBDeploymentEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("VirtualMachineName")) && this.bean.isVirtualMachineNameSet()) {
               copy.setVirtualMachineName(this.bean.getVirtualMachineName());
            }

            if ((excludeProps == null || !excludeProps.contains("WebServer")) && this.bean.isWebServerSet() && !copy._isSet(101)) {
               Object o = this.bean.getWebServer();
               copy.setWebServer((WebServerMBean)null);
               copy.setWebServer(o == null ? null : (WebServerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebService")) && this.bean.isWebServiceSet() && !copy._isSet(216)) {
               Object o = this.bean.getWebService();
               copy.setWebService((WebServiceMBean)null);
               copy.setWebService(o == null ? null : (WebServiceMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("XMLEntityCache")) && this.bean.isXMLEntityCacheSet()) {
               copy._unSet(copy, 138);
               copy.setXMLEntityCacheAsString(this.bean.getXMLEntityCacheAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("XMLRegistry")) && this.bean.isXMLRegistrySet()) {
               copy._unSet(copy, 137);
               copy.setXMLRegistryAsString(this.bean.getXMLRegistryAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("AdministrationPortEnabled")) && this.bean.isAdministrationPortEnabledSet()) {
               copy.setAdministrationPortEnabled(this.bean.isAdministrationPortEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoMigrationEnabled")) && this.bean.isAutoMigrationEnabledSet()) {
               copy.setAutoMigrationEnabled(this.bean.isAutoMigrationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("BuzzEnabled")) && this.bean.isBuzzEnabledSet()) {
               copy.setBuzzEnabled(this.bean.isBuzzEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("COMEnabled")) && this.bean.isCOMEnabledSet()) {
               copy.setCOMEnabled(this.bean.isCOMEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ClasspathServletDisabled")) && this.bean.isClasspathServletDisabledSet()) {
               copy.setClasspathServletDisabled(this.bean.isClasspathServletDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ClasspathServletSecureModeEnabled")) && this.bean.isClasspathServletSecureModeEnabledSet()) {
               copy.setClasspathServletSecureModeEnabled(this.bean.isClasspathServletSecureModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CleanupOrphanedSessionsEnabled")) && this.bean.isCleanupOrphanedSessionsEnabledSet()) {
               copy.setCleanupOrphanedSessionsEnabled(this.bean.isCleanupOrphanedSessionsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertProxyEnabled")) && this.bean.isClientCertProxyEnabledSet()) {
               copy.setClientCertProxyEnabled(this.bean.isClientCertProxyEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ConsoleInputEnabled")) && this.bean.isConsoleInputEnabledSet()) {
               copy.setConsoleInputEnabled(this.bean.isConsoleInputEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultInternalServletsDisabled")) && this.bean.isDefaultInternalServletsDisabledSet()) {
               copy.setDefaultInternalServletsDisabled(this.bean.isDefaultInternalServletsDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpTraceSupportEnabled")) && this.bean.isHttpTraceSupportEnabledSet()) {
               copy.setHttpTraceSupportEnabled(this.bean.isHttpTraceSupportEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpdEnabled")) && this.bean.isHttpdEnabledSet()) {
               copy.setHttpdEnabled(this.bean.isHttpdEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IIOPEnabled")) && this.bean.isIIOPEnabledSet()) {
               copy.setIIOPEnabled(this.bean.isIIOPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreSessionsDuringShutdown")) && this.bean.isIgnoreSessionsDuringShutdownSet()) {
               copy.setIgnoreSessionsDuringShutdown(this.bean.isIgnoreSessionsDuringShutdown());
            }

            if ((excludeProps == null || !excludeProps.contains("J2EE12OnlyModeEnabled")) && this.bean.isJ2EE12OnlyModeEnabledSet()) {
               copy.setJ2EE12OnlyModeEnabled(this.bean.isJ2EE12OnlyModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("J2EE13WarningEnabled")) && this.bean.isJ2EE13WarningEnabledSet()) {
               copy.setJ2EE13WarningEnabled(this.bean.isJ2EE13WarningEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCLoggingEnabled")) && this.bean.isJDBCLoggingEnabledSet()) {
               copy.setJDBCLoggingEnabled(this.bean.isJDBCLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSDefaultConnectionFactoriesEnabled")) && this.bean.isJMSDefaultConnectionFactoriesEnabledSet()) {
               copy.setJMSDefaultConnectionFactoriesEnabled(this.bean.isJMSDefaultConnectionFactoriesEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JRMPEnabled")) && this.bean.isJRMPEnabledSet()) {
               copy.setJRMPEnabled(this.bean.isJRMPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPortEnabled")) && this.bean.isListenPortEnabledSet()) {
               copy.setListenPortEnabled(this.bean.isListenPortEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MSIFileReplicationEnabled")) && this.bean.isMSIFileReplicationEnabledSet()) {
               copy.setMSIFileReplicationEnabled(this.bean.isMSIFileReplicationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedServerIndependenceEnabled")) && this.bean.isManagedServerIndependenceEnabledSet()) {
               copy.setManagedServerIndependenceEnabled(this.bean.isManagedServerIndependenceEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageIdPrefixEnabled")) && this.bean.isMessageIdPrefixEnabledSet()) {
               copy.setMessageIdPrefixEnabled(this.bean.isMessageIdPrefixEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("NetworkClassLoadingEnabled")) && this.bean.isNetworkClassLoadingEnabledSet()) {
               copy.setNetworkClassLoadingEnabled(this.bean.isNetworkClassLoadingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionReplicationOnShutdownEnabled")) && this.bean.isSessionReplicationOnShutdownEnabledSet()) {
               copy.setSessionReplicationOnShutdownEnabled(this.bean.isSessionReplicationOnShutdownEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SitConfigRequired")) && this.bean.isSitConfigRequiredSet()) {
               copy.setSitConfigRequired(this.bean.isSitConfigRequired());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutDebugEnabled")) && this.bean.isStdoutDebugEnabledSet()) {
               copy.setStdoutDebugEnabled(this.bean.isStdoutDebugEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutEnabled")) && this.bean.isStdoutEnabledSet()) {
               copy.setStdoutEnabled(this.bean.isStdoutEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutLogStack")) && this.bean.isStdoutLogStackSet()) {
               copy.setStdoutLogStack(this.bean.isStdoutLogStack());
            }

            if ((excludeProps == null || !excludeProps.contains("TGIOPEnabled")) && this.bean.isTGIOPEnabledSet()) {
               copy.setTGIOPEnabled(this.bean.isTGIOPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingEnabled")) && this.bean.isTunnelingEnabledSet()) {
               copy.setTunnelingEnabled(this.bean.isTunnelingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UseFusionForLLR")) && this.bean.isUseFusionForLLRSet()) {
               copy.setUseFusionForLLR(this.bean.isUseFusionForLLR());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicPluginEnabled")) && this.bean.isWeblogicPluginEnabledSet()) {
               copy.setWeblogicPluginEnabled(this.bean.isWeblogicPluginEnabled());
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
         this.inferSubTree(this.bean.getCOM(), clazz, annotation);
         this.inferSubTree(this.bean.getCandidateMachines(), clazz, annotation);
         this.inferSubTree(this.bean.getCluster(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterSystemResource(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceMemberConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getConfigurationProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSource(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultFileStore(), clazz, annotation);
         this.inferSubTree(this.bean.getFederationServices(), clazz, annotation);
         this.inferSubTree(this.bean.getJTAMigratableTarget(), clazz, annotation);
         this.inferSubTree(this.bean.getKernelDebug(), clazz, annotation);
         this.inferSubTree(this.bean.getMachine(), clazz, annotation);
         this.inferSubTree(this.bean.getNetworkAccessPoints(), clazz, annotation);
         this.inferSubTree(this.bean.getOverloadProtection(), clazz, annotation);
         this.inferSubTree(this.bean.getReliableDeliveryPolicy(), clazz, annotation);
         this.inferSubTree(this.bean.getServerDebug(), clazz, annotation);
         this.inferSubTree(this.bean.getServerDiagnosticConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getServerStart(), clazz, annotation);
         this.inferSubTree(this.bean.getSingleSignOnServices(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactionLogJDBCStore(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServer(), clazz, annotation);
         this.inferSubTree(this.bean.getWebService(), clazz, annotation);
         this.inferSubTree(this.bean.getXMLEntityCache(), clazz, annotation);
         this.inferSubTree(this.bean.getXMLRegistry(), clazz, annotation);
      }
   }
}
