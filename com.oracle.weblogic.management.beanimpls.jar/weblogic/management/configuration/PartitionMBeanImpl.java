package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Partition;
import weblogic.management.security.RealmMBean;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PartitionMBeanImpl extends ConfigurationPropertiesMBeanImpl implements PartitionMBean, Serializable {
   private AdminVirtualTargetMBean _AdminVirtualTarget;
   private TargetMBean[] _AvailableTargets;
   private String _BatchJobsDataSourceJndiName;
   private String _BatchJobsExecutorServiceName;
   private CoherencePartitionCacheConfigMBean[] _CoherencePartitionCacheConfigs;
   private JDBCSystemResourceMBean _DataSourceForJobScheduler;
   private DataSourcePartitionMBean _DataSourcePartition;
   private TargetMBean[] _DefaultTargets;
   private boolean _DynamicallyCreated;
   private boolean _EagerTrackingOfResourceMetricsEnabled;
   private ForeignJNDIProviderOverrideMBean[] _ForeignJNDIProviderOverrides;
   private int _GracefulShutdownTimeout;
   private boolean _IgnoreSessionsDuringShutdown;
   private AppDeploymentMBean[] _InternalAppDeployments;
   private LibraryMBean[] _InternalLibraries;
   private JDBCSystemResourceOverrideMBean[] _JDBCSystemResourceOverrides;
   private JMSSystemResourceOverrideMBean[] _JMSSystemResourceOverrides;
   private JTAPartitionMBean _JTAPartition;
   private String _JobSchedulerTableName;
   private MailSessionOverrideMBean[] _MailSessionOverrides;
   private ManagedExecutorServiceTemplateMBean[] _ManagedExecutorServiceTemplates;
   private ManagedScheduledExecutorServiceTemplateMBean[] _ManagedScheduledExecutorServiceTemplates;
   private ManagedThreadFactoryTemplateMBean[] _ManagedThreadFactoryTemplates;
   private int _MaxConcurrentLongRunningRequests;
   private int _MaxConcurrentNewThreads;
   private String _Name;
   private boolean _ParallelDeployApplicationModules;
   private boolean _ParallelDeployApplications;
   private String _PartitionID;
   private int _PartitionLifeCycleTimeoutVal;
   private PartitionLogMBean _PartitionLog;
   private PartitionWorkManagerMBean _PartitionWorkManager;
   private PartitionWorkManagerMBean _PartitionWorkManagerRef;
   private String _PrimaryIdentityDomain;
   private int _RCMHistoricalDataBufferLimit;
   private RealmMBean _Realm;
   private byte[] _ResourceDeploymentPlan;
   private ResourceDeploymentPlanBean _ResourceDeploymentPlanDescriptor;
   private byte[] _ResourceDeploymentPlanExternalDescriptors;
   private String _ResourceDeploymentPlanPath;
   private ResourceGroupMBean[] _ResourceGroups;
   private ResourceManagerMBean _ResourceManager;
   private ResourceManagerMBean _ResourceManagerRef;
   private SelfTuningMBean _SelfTuning;
   private int _StartupTimeout;
   private PartitionFileSystemMBean _SystemFileSystem;
   private String[] _Tags;
   private String _UploadDirectoryName;
   private PartitionUserFileSystemMBean _UserFileSystem;
   private WebServiceMBean _WebService;
   private transient Partition _customizer;
   private static SchemaHelper2 _schemaHelper;

   public PartitionMBeanImpl() {
      try {
         this._customizer = new Partition(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public PartitionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Partition(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public PartitionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Partition(this);
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

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void addResourceGroup(ResourceGroupMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ResourceGroupMBean[] _new;
         if (this._isSet(11)) {
            _new = (ResourceGroupMBean[])((ResourceGroupMBean[])this._getHelper()._extendArray(this.getResourceGroups(), ResourceGroupMBean.class, param0));
         } else {
            _new = new ResourceGroupMBean[]{param0};
         }

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
      return this._ResourceGroups;
   }

   public boolean isResourceGroupsInherited() {
      return false;
   }

   public boolean isResourceGroupsSet() {
      return this._isSet(11);
   }

   public void removeResourceGroup(ResourceGroupMBean param0) {
      DomainValidator.validateDestroyResourceGroup(param0);
      this.destroyResourceGroup(param0);
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

   public void setResourceGroups(ResourceGroupMBean[] param0) throws InvalidAttributeValueException {
      ResourceGroupMBean[] param0 = param0 == null ? new ResourceGroupMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ResourceGroupMBean[] _oldVal = this._ResourceGroups;
      this._ResourceGroups = (ResourceGroupMBean[])param0;
      this._postSet(11, _oldVal, param0);
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

   public ResourceGroupMBean createResourceGroup(String param0) {
      ResourceGroupMBeanImpl lookup = (ResourceGroupMBeanImpl)this.lookupResourceGroup(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ResourceGroupMBeanImpl _val = new ResourceGroupMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addResourceGroup(_val);
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

   public void destroyResourceGroup(ResourceGroupMBean param0) {
      try {
         DomainValidator.validateDestroyResourceGroup(param0);
         this._checkIsPotentialChild(param0, 11);
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

   public void addJDBCSystemResourceOverride(JDBCSystemResourceOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         JDBCSystemResourceOverrideMBean[] _new;
         if (this._isSet(12)) {
            _new = (JDBCSystemResourceOverrideMBean[])((JDBCSystemResourceOverrideMBean[])this._getHelper()._extendArray(this.getJDBCSystemResourceOverrides(), JDBCSystemResourceOverrideMBean.class, param0));
         } else {
            _new = new JDBCSystemResourceOverrideMBean[]{param0};
         }

         try {
            this.setJDBCSystemResourceOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCSystemResourceOverrideMBean[] getJDBCSystemResourceOverrides() {
      return this._JDBCSystemResourceOverrides;
   }

   public boolean isJDBCSystemResourceOverridesInherited() {
      return false;
   }

   public boolean isJDBCSystemResourceOverridesSet() {
      return this._isSet(12);
   }

   public void removeJDBCSystemResourceOverride(JDBCSystemResourceOverrideMBean param0) {
      this.destroyJDBCSystemResourceOverride(param0);
   }

   public void setJDBCSystemResourceOverrides(JDBCSystemResourceOverrideMBean[] param0) throws InvalidAttributeValueException {
      JDBCSystemResourceOverrideMBean[] param0 = param0 == null ? new JDBCSystemResourceOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JDBCSystemResourceOverrideMBean[] _oldVal = this._JDBCSystemResourceOverrides;
      this._JDBCSystemResourceOverrides = (JDBCSystemResourceOverrideMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public JDBCSystemResourceOverrideMBean lookupJDBCSystemResourceOverride(String param0) {
      Object[] aary = (Object[])this._JDBCSystemResourceOverrides;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCSystemResourceOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCSystemResourceOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JDBCSystemResourceOverrideMBean createJDBCSystemResourceOverride(String param0) {
      JDBCSystemResourceOverrideMBeanImpl lookup = (JDBCSystemResourceOverrideMBeanImpl)this.lookupJDBCSystemResourceOverride(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCSystemResourceOverrideMBeanImpl _val = new JDBCSystemResourceOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCSystemResourceOverride(_val);
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

   public void destroyJDBCSystemResourceOverride(JDBCSystemResourceOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         JDBCSystemResourceOverrideMBean[] _old = this.getJDBCSystemResourceOverrides();
         JDBCSystemResourceOverrideMBean[] _new = (JDBCSystemResourceOverrideMBean[])((JDBCSystemResourceOverrideMBean[])this._getHelper()._removeElement(_old, JDBCSystemResourceOverrideMBean.class, param0));
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
               this.setJDBCSystemResourceOverrides(_new);
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

   public void addMailSessionOverride(MailSessionOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         MailSessionOverrideMBean[] _new;
         if (this._isSet(13)) {
            _new = (MailSessionOverrideMBean[])((MailSessionOverrideMBean[])this._getHelper()._extendArray(this.getMailSessionOverrides(), MailSessionOverrideMBean.class, param0));
         } else {
            _new = new MailSessionOverrideMBean[]{param0};
         }

         try {
            this.setMailSessionOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MailSessionOverrideMBean[] getMailSessionOverrides() {
      return this._MailSessionOverrides;
   }

   public boolean isMailSessionOverridesInherited() {
      return false;
   }

   public boolean isMailSessionOverridesSet() {
      return this._isSet(13);
   }

   public void removeMailSessionOverride(MailSessionOverrideMBean param0) {
      this.destroyMailSessionOverride(param0);
   }

   public void setMailSessionOverrides(MailSessionOverrideMBean[] param0) throws InvalidAttributeValueException {
      MailSessionOverrideMBean[] param0 = param0 == null ? new MailSessionOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MailSessionOverrideMBean[] _oldVal = this._MailSessionOverrides;
      this._MailSessionOverrides = (MailSessionOverrideMBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public MailSessionOverrideMBean lookupMailSessionOverride(String param0) {
      Object[] aary = (Object[])this._MailSessionOverrides;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MailSessionOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MailSessionOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public MailSessionOverrideMBean createMailSessionOverride(String param0) {
      MailSessionOverrideMBeanImpl lookup = (MailSessionOverrideMBeanImpl)this.lookupMailSessionOverride(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MailSessionOverrideMBeanImpl _val = new MailSessionOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMailSessionOverride(_val);
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

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void destroyMailSessionOverride(MailSessionOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         MailSessionOverrideMBean[] _old = this.getMailSessionOverrides();
         MailSessionOverrideMBean[] _new = (MailSessionOverrideMBean[])((MailSessionOverrideMBean[])this._getHelper()._removeElement(_old, MailSessionOverrideMBean.class, param0));
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
               this.setMailSessionOverrides(_new);
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

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void addForeignJNDIProviderOverride(ForeignJNDIProviderOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         ForeignJNDIProviderOverrideMBean[] _new;
         if (this._isSet(14)) {
            _new = (ForeignJNDIProviderOverrideMBean[])((ForeignJNDIProviderOverrideMBean[])this._getHelper()._extendArray(this.getForeignJNDIProviderOverrides(), ForeignJNDIProviderOverrideMBean.class, param0));
         } else {
            _new = new ForeignJNDIProviderOverrideMBean[]{param0};
         }

         try {
            this.setForeignJNDIProviderOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJNDIProviderOverrideMBean[] getForeignJNDIProviderOverrides() {
      return this._ForeignJNDIProviderOverrides;
   }

   public boolean isForeignJNDIProviderOverridesInherited() {
      return false;
   }

   public boolean isForeignJNDIProviderOverridesSet() {
      return this._isSet(14);
   }

   public void removeForeignJNDIProviderOverride(ForeignJNDIProviderOverrideMBean param0) {
      this.destroyForeignJNDIProviderOverride(param0);
   }

   public void setForeignJNDIProviderOverrides(ForeignJNDIProviderOverrideMBean[] param0) throws InvalidAttributeValueException {
      ForeignJNDIProviderOverrideMBean[] param0 = param0 == null ? new ForeignJNDIProviderOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignJNDIProviderOverrideMBean[] _oldVal = this._ForeignJNDIProviderOverrides;
      this._ForeignJNDIProviderOverrides = (ForeignJNDIProviderOverrideMBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public ForeignJNDIProviderOverrideMBean lookupForeignJNDIProviderOverride(String param0) {
      Object[] aary = (Object[])this._ForeignJNDIProviderOverrides;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJNDIProviderOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJNDIProviderOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJNDIProviderOverrideMBean createForeignJNDIProviderOverride(String param0) {
      ForeignJNDIProviderOverrideMBeanImpl lookup = (ForeignJNDIProviderOverrideMBeanImpl)this.lookupForeignJNDIProviderOverride(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJNDIProviderOverrideMBeanImpl _val = new ForeignJNDIProviderOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJNDIProviderOverride(_val);
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

   public void destroyForeignJNDIProviderOverride(ForeignJNDIProviderOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         ForeignJNDIProviderOverrideMBean[] _old = this.getForeignJNDIProviderOverrides();
         ForeignJNDIProviderOverrideMBean[] _new = (ForeignJNDIProviderOverrideMBean[])((ForeignJNDIProviderOverrideMBean[])this._getHelper()._removeElement(_old, ForeignJNDIProviderOverrideMBean.class, param0));
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
               this.setForeignJNDIProviderOverrides(_new);
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

   public TargetMBean[] findEffectiveTargets() {
      try {
         return this._customizer.findEffectiveTargets();
      } catch (Exception var2) {
         throw new UndeclaredThrowableException(var2);
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

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public String[] findEffectiveServerNames() {
      return this._customizer.findEffectiveServerNames();
   }

   public TargetMBean[] findEffectiveAdminTargets() {
      try {
         return this._customizer.findEffectiveAdminTargets();
      } catch (Exception var2) {
         throw new UndeclaredThrowableException(var2);
      }
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

   public ResourceGroupMBean[] findAdminResourceGroupsTargeted(String param0) {
      return this._customizer.findAdminResourceGroupsTargeted(param0);
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

   public TargetMBean[] getDefaultTargets() {
      return this._DefaultTargets;
   }

   public String getDefaultTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getDefaultTargets());
   }

   public boolean isDefaultTargetsInherited() {
      return false;
   }

   public boolean isDefaultTargetsSet() {
      return this._isSet(15);
   }

   public void setDefaultTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._DefaultTargets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 15, param0) {
                  public void resolveReference(Object value) {
                     try {
                        PartitionMBeanImpl.this.addDefaultTarget((TargetMBean)value);
                        PartitionMBeanImpl.this._getHelper().reorderArrayObjects((Object[])PartitionMBeanImpl.this._DefaultTargets, this.getHandbackObject());
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
               TargetMBean[] var6 = this._DefaultTargets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeDefaultTarget(member);
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
         TargetMBean[] _oldVal = this._DefaultTargets;
         this._initializeProperty(15);
         this._postSet(15, _oldVal, this._DefaultTargets);
      }
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

   public void setDefaultTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));
      PartitionMBeanValidator.validateSetDefaultTarget(this, param0);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 15, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return PartitionMBeanImpl.this.getDefaultTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this._DefaultTargets;
      this._DefaultTargets = param0;
      this._postSet(15, _oldVal, param0);
   }

   public void addDefaultTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         TargetMBean[] _new;
         if (this._isSet(15)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getDefaultTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

         try {
            this.setDefaultTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void removeDefaultTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getDefaultTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDefaultTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public TargetMBean[] getAvailableTargets() {
      return this._AvailableTargets;
   }

   public String getAvailableTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getAvailableTargets());
   }

   public boolean isAvailableTargetsInherited() {
      return false;
   }

   public boolean isAvailableTargetsSet() {
      return this._isSet(16);
   }

   public void setAvailableTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._AvailableTargets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 16, param0) {
                  public void resolveReference(Object value) {
                     try {
                        PartitionMBeanImpl.this.addAvailableTarget((TargetMBean)value);
                        PartitionMBeanImpl.this._getHelper().reorderArrayObjects((Object[])PartitionMBeanImpl.this._AvailableTargets, this.getHandbackObject());
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
               TargetMBean[] var6 = this._AvailableTargets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeAvailableTarget(member);
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
         TargetMBean[] _oldVal = this._AvailableTargets;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._AvailableTargets);
      }
   }

   public TargetMBean lookupAvailableTarget(String param0) {
      return this._customizer.lookupAvailableTarget(param0);
   }

   public void addAvailableTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         TargetMBean[] _new;
         if (this._isSet(16)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getAvailableTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

         try {
            this.setAvailableTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void removeAvailableTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getAvailableTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setAvailableTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAvailableTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));
      PartitionMBeanValidator.validateSetAvailableTargets(this, param0);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return PartitionMBeanImpl.this.getAvailableTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this._AvailableTargets;
      this._AvailableTargets = param0;
      this._postSet(16, _oldVal, param0);
   }

   public SelfTuningMBean getSelfTuning() {
      return this._SelfTuning;
   }

   public boolean isSelfTuningInherited() {
      return false;
   }

   public boolean isSelfTuningSet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getSelfTuning());
   }

   public void setSelfTuning(SelfTuningMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      SelfTuningMBean _oldVal = this._SelfTuning;
      this._SelfTuning = param0;
      this._postSet(17, _oldVal, param0);
   }

   public RealmMBean getRealm() {
      return this._Realm;
   }

   public String getRealmAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getRealm();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isRealmInherited() {
      return false;
   }

   public boolean isRealmSet() {
      return this._isSet(18);
   }

   public void setRealmAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, RealmMBean.class, new ReferenceManager.Resolver(this, 18) {
            public void resolveReference(Object value) {
               try {
                  PartitionMBeanImpl.this.setRealm((RealmMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         RealmMBean _oldVal = this._Realm;
         this._initializeProperty(18);
         this._postSet(18, _oldVal, this._Realm);
      }

   }

   public void setRealm(RealmMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 18, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return PartitionMBeanImpl.this.getRealm();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      RealmMBean _oldVal = this._Realm;
      this._Realm = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getPartitionID() {
      return this._PartitionID;
   }

   public boolean isPartitionIDInherited() {
      return false;
   }

   public boolean isPartitionIDSet() {
      return this._isSet(19);
   }

   public void setPartitionID(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PartitionID;
      this._PartitionID = param0;
      this._postSet(19, _oldVal, param0);
   }

   public PartitionLogMBean getPartitionLog() {
      return this._PartitionLog;
   }

   public boolean isPartitionLogInherited() {
      return false;
   }

   public boolean isPartitionLogSet() {
      return this._isSet(20) || this._isAnythingSet((AbstractDescriptorBean)this.getPartitionLog());
   }

   public void setPartitionLog(PartitionLogMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 20)) {
         this._postCreate(_child);
      }

      PartitionLogMBean _oldVal = this._PartitionLog;
      this._PartitionLog = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getPrimaryIdentityDomain() {
      if (!this._isSet(21)) {
         try {
            return ((DomainMBean)this.getParent()).getSecurityConfiguration().isIdentityDomainDefaultEnabled() ? "idd_" + this.getName() : null;
         } catch (NullPointerException var2) {
         }
      }

      return this._PrimaryIdentityDomain;
   }

   public boolean isPrimaryIdentityDomainInherited() {
      return false;
   }

   public boolean isPrimaryIdentityDomainSet() {
      return this._isSet(21);
   }

   public void setPrimaryIdentityDomain(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrimaryIdentityDomain;
      this._PrimaryIdentityDomain = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getMaxConcurrentNewThreads() {
      return this._MaxConcurrentNewThreads;
   }

   public boolean isMaxConcurrentNewThreadsInherited() {
      return false;
   }

   public boolean isMaxConcurrentNewThreadsSet() {
      return this._isSet(22);
   }

   public void setMaxConcurrentNewThreads(int param0) {
      LegalChecks.checkInRange("MaxConcurrentNewThreads", (long)param0, 0L, 65534L);
      int _oldVal = this._MaxConcurrentNewThreads;
      this._MaxConcurrentNewThreads = param0;
      this._postSet(22, _oldVal, param0);
   }

   public int getMaxConcurrentLongRunningRequests() {
      return this._MaxConcurrentLongRunningRequests;
   }

   public boolean isMaxConcurrentLongRunningRequestsInherited() {
      return false;
   }

   public boolean isMaxConcurrentLongRunningRequestsSet() {
      return this._isSet(23);
   }

   public void setMaxConcurrentLongRunningRequests(int param0) {
      LegalChecks.checkInRange("MaxConcurrentLongRunningRequests", (long)param0, 0L, 65534L);
      int _oldVal = this._MaxConcurrentLongRunningRequests;
      this._MaxConcurrentLongRunningRequests = param0;
      this._postSet(23, _oldVal, param0);
   }

   public void addManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         ManagedExecutorServiceTemplateMBean[] _new;
         if (this._isSet(24)) {
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
      return this._isSet(24);
   }

   public void removeManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean param0) {
      this.destroyManagedExecutorServiceTemplate(param0);
   }

   public void setManagedExecutorServiceTemplates(ManagedExecutorServiceTemplateMBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceTemplateMBean[] param0 = param0 == null ? new ManagedExecutorServiceTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedExecutorServiceTemplateMBean[] _oldVal = this._ManagedExecutorServiceTemplates;
      this._ManagedExecutorServiceTemplates = (ManagedExecutorServiceTemplateMBean[])param0;
      this._postSet(24, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 24);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 25)) {
         ManagedScheduledExecutorServiceTemplateMBean[] _new;
         if (this._isSet(25)) {
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
      return this._isSet(25);
   }

   public void removeManagedScheduledExecutorServiceTemplate(ManagedScheduledExecutorServiceTemplateMBean param0) {
      this.destroyManagedScheduledExecutorServiceTemplate(param0);
   }

   public void setManagedScheduledExecutorServiceTemplates(ManagedScheduledExecutorServiceTemplateMBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceTemplateMBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 25)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedScheduledExecutorServiceTemplateMBean[] _oldVal = this._ManagedScheduledExecutorServiceTemplates;
      this._ManagedScheduledExecutorServiceTemplates = (ManagedScheduledExecutorServiceTemplateMBean[])param0;
      this._postSet(25, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 25);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         ManagedThreadFactoryTemplateMBean[] _new;
         if (this._isSet(26)) {
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
      return this._isSet(26);
   }

   public void removeManagedThreadFactoryTemplate(ManagedThreadFactoryTemplateMBean param0) {
      this.destroyManagedThreadFactoryTemplate(param0);
   }

   public void setManagedThreadFactoryTemplates(ManagedThreadFactoryTemplateMBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryTemplateMBean[] param0 = param0 == null ? new ManagedThreadFactoryTemplateMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 26)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ManagedThreadFactoryTemplateMBean[] _oldVal = this._ManagedThreadFactoryTemplates;
      this._ManagedThreadFactoryTemplates = (ManagedThreadFactoryTemplateMBean[])param0;
      this._postSet(26, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 26);
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

   public JTAPartitionMBean getJTAPartition() {
      return this._JTAPartition;
   }

   public boolean isJTAPartitionInherited() {
      return false;
   }

   public boolean isJTAPartitionSet() {
      return this._isSet(27) || this._isAnythingSet((AbstractDescriptorBean)this.getJTAPartition());
   }

   public void setJTAPartition(JTAPartitionMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 27)) {
         this._postCreate(_child);
      }

      JTAPartitionMBean _oldVal = this._JTAPartition;
      this._JTAPartition = param0;
      this._postSet(27, _oldVal, param0);
   }

   public void setDataSourceForJobScheduler(JDBCSystemResourceMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 28, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return PartitionMBeanImpl.this.getDataSourceForJobScheduler();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      JDBCSystemResourceMBean _oldVal = this._DataSourceForJobScheduler;
      this._DataSourceForJobScheduler = param0;
      this._postSet(28, _oldVal, param0);
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
      return this._isSet(28);
   }

   public void setDataSourceForJobSchedulerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JDBCSystemResourceMBean.class, new ReferenceManager.Resolver(this, 28) {
            public void resolveReference(Object value) {
               try {
                  PartitionMBeanImpl.this.setDataSourceForJobScheduler((JDBCSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JDBCSystemResourceMBean _oldVal = this._DataSourceForJobScheduler;
         this._initializeProperty(28);
         this._postSet(28, _oldVal, this._DataSourceForJobScheduler);
      }

   }

   public String getJobSchedulerTableName() {
      return this._JobSchedulerTableName;
   }

   public boolean isJobSchedulerTableNameInherited() {
      return false;
   }

   public boolean isJobSchedulerTableNameSet() {
      return this._isSet(29);
   }

   public void setJobSchedulerTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JobSchedulerTableName;
      this._JobSchedulerTableName = param0;
      this._postSet(29, _oldVal, param0);
   }

   public ResourceManagerMBean getResourceManager() {
      return this._ResourceManager;
   }

   public boolean isResourceManagerInherited() {
      return false;
   }

   public boolean isResourceManagerSet() {
      return this._isSet(30);
   }

   public void setResourceManager(ResourceManagerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getResourceManager() != null && param0 != this.getResourceManager()) {
         throw new BeanAlreadyExistsException(this.getResourceManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 30)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         ResourceManagerMBean _oldVal = this._ResourceManager;
         this._ResourceManager = param0;
         this._postSet(30, _oldVal, param0);
      }
   }

   public ResourceManagerMBean createResourceManager(String param0) {
      ResourceManagerMBeanImpl _val = new ResourceManagerMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setResourceManager(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyResourceManager(ResourceManagerMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ResourceManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResourceManager((ResourceManagerMBean)null);
               this._unSet(30);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public ResourceManagerMBean getResourceManagerRef() {
      return this._ResourceManagerRef;
   }

   public String getResourceManagerRefAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getResourceManagerRef();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isResourceManagerRefInherited() {
      return false;
   }

   public boolean isResourceManagerRefSet() {
      return this._isSet(31);
   }

   public void setResourceManagerRefAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ResourceManagerMBean.class, new ReferenceManager.Resolver(this, 31) {
            public void resolveReference(Object value) {
               try {
                  PartitionMBeanImpl.this.setResourceManagerRef((ResourceManagerMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ResourceManagerMBean _oldVal = this._ResourceManagerRef;
         this._initializeProperty(31);
         this._postSet(31, _oldVal, this._ResourceManagerRef);
      }

   }

   public void setResourceManagerRef(ResourceManagerMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 31, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return PartitionMBeanImpl.this.getResourceManagerRef();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ResourceManagerMBean _oldVal = this._ResourceManagerRef;
      this._ResourceManagerRef = param0;
      this._postSet(31, _oldVal, param0);
   }

   public void destroyResourceManagerRef(ResourceManagerMBean param0) {
      try {
         this.setResourceManagerRef((ResourceManagerMBean)null);
         this._unSet(31);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DataSourcePartitionMBean getDataSourcePartition() {
      return this._DataSourcePartition;
   }

   public boolean isDataSourcePartitionInherited() {
      return false;
   }

   public boolean isDataSourcePartitionSet() {
      return this._isSet(32) || this._isAnythingSet((AbstractDescriptorBean)this.getDataSourcePartition());
   }

   public void setDataSourcePartition(DataSourcePartitionMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 32)) {
         this._postCreate(_child);
      }

      DataSourcePartitionMBean _oldVal = this._DataSourcePartition;
      this._DataSourcePartition = param0;
      this._postSet(32, _oldVal, param0);
   }

   public void addCoherencePartitionCacheConfig(CoherencePartitionCacheConfigMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 33)) {
         CoherencePartitionCacheConfigMBean[] _new;
         if (this._isSet(33)) {
            _new = (CoherencePartitionCacheConfigMBean[])((CoherencePartitionCacheConfigMBean[])this._getHelper()._extendArray(this.getCoherencePartitionCacheConfigs(), CoherencePartitionCacheConfigMBean.class, param0));
         } else {
            _new = new CoherencePartitionCacheConfigMBean[]{param0};
         }

         try {
            this.setCoherencePartitionCacheConfigs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherencePartitionCacheConfigMBean[] getCoherencePartitionCacheConfigs() {
      return this._CoherencePartitionCacheConfigs;
   }

   public boolean isCoherencePartitionCacheConfigsInherited() {
      return false;
   }

   public boolean isCoherencePartitionCacheConfigsSet() {
      return this._isSet(33);
   }

   public void removeCoherencePartitionCacheConfig(CoherencePartitionCacheConfigMBean param0) {
      this.destroyCoherencePartitionCacheConfig(param0);
   }

   public void setCoherencePartitionCacheConfigs(CoherencePartitionCacheConfigMBean[] param0) throws InvalidAttributeValueException {
      CoherencePartitionCacheConfigMBean[] param0 = param0 == null ? new CoherencePartitionCacheConfigMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 33)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherencePartitionCacheConfigMBean[] _oldVal = this._CoherencePartitionCacheConfigs;
      this._CoherencePartitionCacheConfigs = (CoherencePartitionCacheConfigMBean[])param0;
      this._postSet(33, _oldVal, param0);
   }

   public CoherencePartitionCacheConfigMBean lookupCoherencePartitionCacheConfig(String param0) {
      Object[] aary = (Object[])this._CoherencePartitionCacheConfigs;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherencePartitionCacheConfigMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherencePartitionCacheConfigMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public CoherencePartitionCacheConfigMBean createCoherencePartitionCacheConfig(String param0) {
      CoherencePartitionCacheConfigMBeanImpl lookup = (CoherencePartitionCacheConfigMBeanImpl)this.lookupCoherencePartitionCacheConfig(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherencePartitionCacheConfigMBeanImpl _val = new CoherencePartitionCacheConfigMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherencePartitionCacheConfig(_val);
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

   public void destroyCoherencePartitionCacheConfig(CoherencePartitionCacheConfigMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 33);
         CoherencePartitionCacheConfigMBean[] _old = this.getCoherencePartitionCacheConfigs();
         CoherencePartitionCacheConfigMBean[] _new = (CoherencePartitionCacheConfigMBean[])((CoherencePartitionCacheConfigMBean[])this._getHelper()._removeElement(_old, CoherencePartitionCacheConfigMBean.class, param0));
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
               this.setCoherencePartitionCacheConfigs(_new);
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

   public PartitionFileSystemMBean getSystemFileSystem() {
      return this._SystemFileSystem;
   }

   public boolean isSystemFileSystemInherited() {
      return false;
   }

   public boolean isSystemFileSystemSet() {
      return this._isSet(34) || this._isAnythingSet((AbstractDescriptorBean)this.getSystemFileSystem());
   }

   public void setSystemFileSystem(PartitionFileSystemMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 34)) {
         this._postCreate(_child);
      }

      PartitionFileSystemMBean _oldVal = this._SystemFileSystem;
      this._SystemFileSystem = param0;
      this._postSet(34, _oldVal, param0);
   }

   public PartitionUserFileSystemMBean getUserFileSystem() {
      return this._UserFileSystem;
   }

   public boolean isUserFileSystemInherited() {
      return false;
   }

   public boolean isUserFileSystemSet() {
      return this._isSet(35) || this._isAnythingSet((AbstractDescriptorBean)this.getUserFileSystem());
   }

   public void setUserFileSystem(PartitionUserFileSystemMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 35)) {
         this._postCreate(_child);
      }

      PartitionUserFileSystemMBean _oldVal = this._UserFileSystem;
      this._UserFileSystem = param0;
      this._postSet(35, _oldVal, param0);
   }

   public String getResourceDeploymentPlanPath() {
      return this._ResourceDeploymentPlanPath;
   }

   public boolean isResourceDeploymentPlanPathInherited() {
      return false;
   }

   public boolean isResourceDeploymentPlanPathSet() {
      return this._isSet(36);
   }

   public void setResourceDeploymentPlanPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceDeploymentPlanPath;
      this._ResourceDeploymentPlanPath = param0;
      this._postSet(36, _oldVal, param0);
   }

   public byte[] getResourceDeploymentPlan() {
      return this._customizer.getResourceDeploymentPlan();
   }

   public boolean isResourceDeploymentPlanInherited() {
      return false;
   }

   public boolean isResourceDeploymentPlanSet() {
      return this._isSet(37);
   }

   public void setResourceDeploymentPlan(byte[] param0) throws InvalidAttributeValueException {
      this._ResourceDeploymentPlan = param0;
   }

   public byte[] getResourceDeploymentPlanExternalDescriptors() {
      return this._customizer.getResourceDeploymentPlanExternalDescriptors();
   }

   public boolean isResourceDeploymentPlanExternalDescriptorsInherited() {
      return false;
   }

   public boolean isResourceDeploymentPlanExternalDescriptorsSet() {
      return this._isSet(38);
   }

   public void setResourceDeploymentPlanExternalDescriptors(byte[] param0) throws InvalidAttributeValueException {
      this._ResourceDeploymentPlanExternalDescriptors = param0;
   }

   public ResourceDeploymentPlanBean getResourceDeploymentPlanDescriptor() {
      return this._customizer.getResourceDeploymentPlanDescriptor();
   }

   public boolean isResourceDeploymentPlanDescriptorInherited() {
      return false;
   }

   public boolean isResourceDeploymentPlanDescriptorSet() {
      return this._isSet(39);
   }

   public void setResourceDeploymentPlanDescriptor(ResourceDeploymentPlanBean param0) throws InvalidAttributeValueException {
      this._ResourceDeploymentPlanDescriptor = param0;
   }

   public void addJMSSystemResourceOverride(JMSSystemResourceOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 40)) {
         JMSSystemResourceOverrideMBean[] _new;
         if (this._isSet(40)) {
            _new = (JMSSystemResourceOverrideMBean[])((JMSSystemResourceOverrideMBean[])this._getHelper()._extendArray(this.getJMSSystemResourceOverrides(), JMSSystemResourceOverrideMBean.class, param0));
         } else {
            _new = new JMSSystemResourceOverrideMBean[]{param0};
         }

         try {
            this.setJMSSystemResourceOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSSystemResourceOverrideMBean[] getJMSSystemResourceOverrides() {
      return this._JMSSystemResourceOverrides;
   }

   public boolean isJMSSystemResourceOverridesInherited() {
      return false;
   }

   public boolean isJMSSystemResourceOverridesSet() {
      return this._isSet(40);
   }

   public void removeJMSSystemResourceOverride(JMSSystemResourceOverrideMBean param0) {
      this.destroyJMSSystemResourceOverride(param0);
   }

   public void setJMSSystemResourceOverrides(JMSSystemResourceOverrideMBean[] param0) throws InvalidAttributeValueException {
      JMSSystemResourceOverrideMBean[] param0 = param0 == null ? new JMSSystemResourceOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 40)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMSSystemResourceOverrideMBean[] _oldVal = this._JMSSystemResourceOverrides;
      this._JMSSystemResourceOverrides = (JMSSystemResourceOverrideMBean[])param0;
      this._postSet(40, _oldVal, param0);
   }

   public JMSSystemResourceOverrideMBean lookupJMSSystemResourceOverride(String param0) {
      Object[] aary = (Object[])this._JMSSystemResourceOverrides;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSSystemResourceOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSSystemResourceOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSSystemResourceOverrideMBean createJMSSystemResourceOverride(String param0) {
      JMSSystemResourceOverrideMBeanImpl lookup = (JMSSystemResourceOverrideMBeanImpl)this.lookupJMSSystemResourceOverride(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSystemResourceOverrideMBeanImpl _val = new JMSSystemResourceOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSSystemResourceOverride(_val);
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

   public void destroyJMSSystemResourceOverride(JMSSystemResourceOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 40);
         JMSSystemResourceOverrideMBean[] _old = this.getJMSSystemResourceOverrides();
         JMSSystemResourceOverrideMBean[] _new = (JMSSystemResourceOverrideMBean[])((JMSSystemResourceOverrideMBean[])this._getHelper()._removeElement(_old, JMSSystemResourceOverrideMBean.class, param0));
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
               this.setJMSSystemResourceOverrides(_new);
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

   public void addInternalAppDeployment(AppDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 41)) {
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

   public AppDeploymentMBean[] getInternalAppDeployments() {
      return this._InternalAppDeployments;
   }

   public boolean isInternalAppDeploymentsInherited() {
      return false;
   }

   public boolean isInternalAppDeploymentsSet() {
      return this._isSet(41);
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

   public void destroyInternalAppDeployment(AppDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 41);
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

   public void setInternalAppDeployments(AppDeploymentMBean[] param0) {
      AppDeploymentMBean[] param0 = param0 == null ? new AppDeploymentMBeanImpl[0] : param0;
      this._InternalAppDeployments = (AppDeploymentMBean[])param0;
   }

   public void addInternalLibrary(LibraryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 42)) {
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

   public LibraryMBean[] getInternalLibraries() {
      return this._InternalLibraries;
   }

   public boolean isInternalLibrariesInherited() {
      return false;
   }

   public boolean isInternalLibrariesSet() {
      return this._isSet(42);
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

   public LibraryMBean createInternalLibrary(String param0, String param1) throws IllegalArgumentException {
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
            } else if (var6 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void setInternalLibraries(LibraryMBean[] param0) {
      LibraryMBean[] param0 = param0 == null ? new LibraryMBeanImpl[0] : param0;
      this._InternalLibraries = (LibraryMBean[])param0;
   }

   public PartitionWorkManagerMBean createPartitionWorkManager(String param0) {
      PartitionWorkManagerMBeanImpl _val = new PartitionWorkManagerMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setPartitionWorkManager(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public PartitionWorkManagerMBean getPartitionWorkManager() {
      return this._PartitionWorkManager;
   }

   public boolean isPartitionWorkManagerInherited() {
      return false;
   }

   public boolean isPartitionWorkManagerSet() {
      return this._isSet(43);
   }

   public void setPartitionWorkManager(PartitionWorkManagerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPartitionWorkManager() != null && param0 != this.getPartitionWorkManager()) {
         throw new BeanAlreadyExistsException(this.getPartitionWorkManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 43)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         PartitionWorkManagerMBean _oldVal = this._PartitionWorkManager;
         this._PartitionWorkManager = param0;
         this._postSet(43, _oldVal, param0);
      }
   }

   public void destroyPartitionWorkManager(PartitionWorkManagerMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PartitionWorkManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPartitionWorkManager((PartitionWorkManagerMBean)null);
               this._unSet(43);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setPartitionWorkManagerRef(PartitionWorkManagerMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 44, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return PartitionMBeanImpl.this.getPartitionWorkManagerRef();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      PartitionWorkManagerMBean _oldVal = this._PartitionWorkManagerRef;
      this._PartitionWorkManagerRef = param0;
      this._postSet(44, _oldVal, param0);
   }

   public PartitionWorkManagerMBean getPartitionWorkManagerRef() {
      return this._PartitionWorkManagerRef;
   }

   public String getPartitionWorkManagerRefAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getPartitionWorkManagerRef();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isPartitionWorkManagerRefInherited() {
      return false;
   }

   public boolean isPartitionWorkManagerRefSet() {
      return this._isSet(44);
   }

   public void setPartitionWorkManagerRefAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, PartitionWorkManagerMBean.class, new ReferenceManager.Resolver(this, 44) {
            public void resolveReference(Object value) {
               try {
                  PartitionMBeanImpl.this.setPartitionWorkManagerRef((PartitionWorkManagerMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         PartitionWorkManagerMBean _oldVal = this._PartitionWorkManagerRef;
         this._initializeProperty(44);
         this._postSet(44, _oldVal, this._PartitionWorkManagerRef);
      }

   }

   public void destroyPartitionWorkManagerRef(PartitionWorkManagerMBean param0) {
      try {
         this.setPartitionWorkManagerRef((PartitionWorkManagerMBean)null);
         this._unSet(44);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getBatchJobsDataSourceJndiName() {
      return this._BatchJobsDataSourceJndiName;
   }

   public boolean isBatchJobsDataSourceJndiNameInherited() {
      return false;
   }

   public boolean isBatchJobsDataSourceJndiNameSet() {
      return this._isSet(45);
   }

   public void setBatchJobsDataSourceJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BatchJobsDataSourceJndiName;
      this._BatchJobsDataSourceJndiName = param0;
      this._postSet(45, _oldVal, param0);
   }

   public String getBatchJobsExecutorServiceName() {
      return this._BatchJobsExecutorServiceName;
   }

   public boolean isBatchJobsExecutorServiceNameInherited() {
      return false;
   }

   public boolean isBatchJobsExecutorServiceNameSet() {
      return this._isSet(46);
   }

   public void setBatchJobsExecutorServiceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BatchJobsExecutorServiceName;
      this._BatchJobsExecutorServiceName = param0;
      this._postSet(46, _oldVal, param0);
   }

   public boolean isParallelDeployApplications() {
      return this._ParallelDeployApplications;
   }

   public boolean isParallelDeployApplicationsInherited() {
      return false;
   }

   public boolean isParallelDeployApplicationsSet() {
      return this._isSet(47);
   }

   public void setParallelDeployApplications(boolean param0) {
      boolean _oldVal = this._ParallelDeployApplications;
      this._ParallelDeployApplications = param0;
      this._postSet(47, _oldVal, param0);
   }

   public boolean isParallelDeployApplicationModules() {
      return this._ParallelDeployApplicationModules;
   }

   public boolean isParallelDeployApplicationModulesInherited() {
      return false;
   }

   public boolean isParallelDeployApplicationModulesSet() {
      return this._isSet(48);
   }

   public void setParallelDeployApplicationModules(boolean param0) {
      boolean _oldVal = this._ParallelDeployApplicationModules;
      this._ParallelDeployApplicationModules = param0;
      this._postSet(48, _oldVal, param0);
   }

   public String getUploadDirectoryName() {
      return this._customizer.getUploadDirectoryName();
   }

   public boolean isUploadDirectoryNameInherited() {
      return false;
   }

   public boolean isUploadDirectoryNameSet() {
      return this._isSet(49);
   }

   public void setUploadDirectoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getUploadDirectoryName();
      this._customizer.setUploadDirectoryName(param0);
      this._postSet(49, _oldVal, param0);
   }

   public void setPartitionLifeCycleTimeoutVal(int param0) {
      LegalChecks.checkMin("PartitionLifeCycleTimeoutVal", param0, 0);
      int _oldVal = this._PartitionLifeCycleTimeoutVal;
      this._PartitionLifeCycleTimeoutVal = param0;
      this._postSet(50, _oldVal, param0);
   }

   public int getPartitionLifeCycleTimeoutVal() {
      if (!this._isSet(50)) {
         return this._isProductionModeEnabled() ? 120 : 30;
      } else {
         return this._PartitionLifeCycleTimeoutVal;
      }
   }

   public boolean isPartitionLifeCycleTimeoutValInherited() {
      return false;
   }

   public boolean isPartitionLifeCycleTimeoutValSet() {
      return this._isSet(50);
   }

   public void setGracefulShutdownTimeout(int param0) {
      LegalChecks.checkMin("GracefulShutdownTimeout", param0, 0);
      int _oldVal = this._GracefulShutdownTimeout;
      this._GracefulShutdownTimeout = param0;
      this._postSet(52, _oldVal, param0);
   }

   public void setStartupTimeout(int param0) {
      LegalChecks.checkMin("StartupTimeout", param0, 0);
      int _oldVal = this._StartupTimeout;
      this._StartupTimeout = param0;
      this._postSet(51, _oldVal, param0);
   }

   public int getStartupTimeout() {
      if (!this._isSet(51)) {
         return this._isProductionModeEnabled() ? 0 : 0;
      } else {
         return this._StartupTimeout;
      }
   }

   public boolean isStartupTimeoutInherited() {
      return false;
   }

   public boolean isStartupTimeoutSet() {
      return this._isSet(51);
   }

   public int getGracefulShutdownTimeout() {
      return this._GracefulShutdownTimeout;
   }

   public boolean isGracefulShutdownTimeoutInherited() {
      return false;
   }

   public boolean isGracefulShutdownTimeoutSet() {
      return this._isSet(52);
   }

   public boolean isIgnoreSessionsDuringShutdown() {
      return this._IgnoreSessionsDuringShutdown;
   }

   public boolean isIgnoreSessionsDuringShutdownInherited() {
      return false;
   }

   public boolean isIgnoreSessionsDuringShutdownSet() {
      return this._isSet(53);
   }

   public void setIgnoreSessionsDuringShutdown(boolean param0) {
      boolean _oldVal = this._IgnoreSessionsDuringShutdown;
      this._IgnoreSessionsDuringShutdown = param0;
      this._postSet(53, _oldVal, param0);
   }

   public int getRCMHistoricalDataBufferLimit() {
      return this._RCMHistoricalDataBufferLimit;
   }

   public boolean isRCMHistoricalDataBufferLimitInherited() {
      return false;
   }

   public boolean isRCMHistoricalDataBufferLimitSet() {
      return this._isSet(54);
   }

   public void setRCMHistoricalDataBufferLimit(int param0) {
      LegalChecks.checkInRange("RCMHistoricalDataBufferLimit", (long)param0, 1L, 5000L);
      int _oldVal = this._RCMHistoricalDataBufferLimit;
      this._RCMHistoricalDataBufferLimit = param0;
      this._postSet(54, _oldVal, param0);
   }

   public WebServiceMBean getWebService() {
      return this._WebService;
   }

   public boolean isWebServiceInherited() {
      return false;
   }

   public boolean isWebServiceSet() {
      return this._isSet(55) || this._isAnythingSet((AbstractDescriptorBean)this.getWebService());
   }

   public void setWebService(WebServiceMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 55)) {
         this._postCreate(_child);
      }

      WebServiceMBean _oldVal = this._WebService;
      this._WebService = param0;
      this._postSet(55, _oldVal, param0);
   }

   public boolean isEagerTrackingOfResourceMetricsEnabled() {
      return this._EagerTrackingOfResourceMetricsEnabled;
   }

   public boolean isEagerTrackingOfResourceMetricsEnabledInherited() {
      return false;
   }

   public boolean isEagerTrackingOfResourceMetricsEnabledSet() {
      return this._isSet(56);
   }

   public void setEagerTrackingOfResourceMetricsEnabled(boolean param0) {
      boolean _oldVal = this._EagerTrackingOfResourceMetricsEnabled;
      this._EagerTrackingOfResourceMetricsEnabled = param0;
      this._postSet(56, _oldVal, param0);
   }

   public AdminVirtualTargetMBean getAdminVirtualTarget() {
      return this._AdminVirtualTarget;
   }

   public boolean isAdminVirtualTargetInherited() {
      return false;
   }

   public boolean isAdminVirtualTargetSet() {
      return this._isSet(57);
   }

   public void setAdminVirtualTarget(AdminVirtualTargetMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAdminVirtualTarget() != null && param0 != this.getAdminVirtualTarget()) {
         throw new BeanAlreadyExistsException(this.getAdminVirtualTarget() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 57)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         AdminVirtualTargetMBean _oldVal = this._AdminVirtualTarget;
         this._AdminVirtualTarget = param0;
         this._postSet(57, _oldVal, param0);
      }
   }

   public AdminVirtualTargetMBean createAdminVirtualTarget(String param0) {
      AdminVirtualTargetMBeanImpl _val = new AdminVirtualTargetMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setAdminVirtualTarget(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyAdminVirtualTarget(AdminVirtualTargetMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AdminVirtualTarget;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAdminVirtualTarget((AdminVirtualTargetMBean)null);
               this._unSet(57);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public TargetMBean[] findEffectiveAvailableTargets() {
      try {
         return this._customizer.findEffectiveAvailableTargets();
      } catch (Exception var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public TargetMBean lookupEffectiveAvailableTarget(String param0) {
      try {
         return this._customizer.lookupEffectiveAvailableTarget(param0);
      } catch (Exception var3) {
         throw new UndeclaredThrowableException(var3);
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      PartitionMBeanValidator.validatePartitionMBean(this);
   }

   protected void _postCreate() {
      this._customizer._postCreate();
   }

   protected void _preDestroy() {
      this._customizer._preDestroy();
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
      return super._isAnythingSet() || this.isDataSourcePartitionSet() || this.isJTAPartitionSet() || this.isPartitionLogSet() || this.isSelfTuningSet() || this.isSystemFileSystemSet() || this.isUserFileSystemSet() || this.isWebServiceSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 57;
      }

      try {
         switch (idx) {
            case 57:
               this._AdminVirtualTarget = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._AvailableTargets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 45:
               this._BatchJobsDataSourceJndiName = null;
               if (initOne) {
                  break;
               }
            case 46:
               this._BatchJobsExecutorServiceName = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._CoherencePartitionCacheConfigs = new CoherencePartitionCacheConfigMBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._DataSourceForJobScheduler = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._DataSourcePartition = new DataSourcePartitionMBeanImpl(this, 32);
               this._postCreate((AbstractDescriptorBean)this._DataSourcePartition);
               if (initOne) {
                  break;
               }
            case 15:
               this._DefaultTargets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._ForeignJNDIProviderOverrides = new ForeignJNDIProviderOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 52:
               this._GracefulShutdownTimeout = 0;
               if (initOne) {
                  break;
               }
            case 41:
               this._InternalAppDeployments = new AppDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 42:
               this._InternalLibraries = new LibraryMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._JDBCSystemResourceOverrides = new JDBCSystemResourceOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 40:
               this._JMSSystemResourceOverrides = new JMSSystemResourceOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._JTAPartition = new JTAPartitionMBeanImpl(this, 27);
               this._postCreate((AbstractDescriptorBean)this._JTAPartition);
               if (initOne) {
                  break;
               }
            case 29:
               this._JobSchedulerTableName = "WEBLOGIC_TIMERS";
               if (initOne) {
                  break;
               }
            case 13:
               this._MailSessionOverrides = new MailSessionOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._ManagedExecutorServiceTemplates = new ManagedExecutorServiceTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._ManagedScheduledExecutorServiceTemplates = new ManagedScheduledExecutorServiceTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._ManagedThreadFactoryTemplates = new ManagedThreadFactoryTemplateMBean[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._MaxConcurrentLongRunningRequests = 50;
               if (initOne) {
                  break;
               }
            case 22:
               this._MaxConcurrentNewThreads = 50;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 19:
               this._PartitionID = null;
               if (initOne) {
                  break;
               }
            case 50:
               this._PartitionLifeCycleTimeoutVal = 30;
               if (initOne) {
                  break;
               }
            case 20:
               this._PartitionLog = new PartitionLogMBeanImpl(this, 20);
               this._postCreate((AbstractDescriptorBean)this._PartitionLog);
               if (initOne) {
                  break;
               }
            case 43:
               this._PartitionWorkManager = null;
               if (initOne) {
                  break;
               }
            case 44:
               this._PartitionWorkManagerRef = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._PrimaryIdentityDomain = null;
               if (initOne) {
                  break;
               }
            case 54:
               this._RCMHistoricalDataBufferLimit = 250;
               if (initOne) {
                  break;
               }
            case 18:
               this._Realm = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._ResourceDeploymentPlan = new byte[0];
               if (initOne) {
                  break;
               }
            case 39:
               this._ResourceDeploymentPlanDescriptor = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._ResourceDeploymentPlanExternalDescriptors = new byte[0];
               if (initOne) {
                  break;
               }
            case 36:
               this._ResourceDeploymentPlanPath = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ResourceGroups = new ResourceGroupMBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._ResourceManager = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._ResourceManagerRef = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._SelfTuning = new SelfTuningMBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._SelfTuning);
               if (initOne) {
                  break;
               }
            case 51:
               this._StartupTimeout = 0;
               if (initOne) {
                  break;
               }
            case 34:
               this._SystemFileSystem = new PartitionFileSystemMBeanImpl(this, 34);
               this._postCreate((AbstractDescriptorBean)this._SystemFileSystem);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 49:
               this._customizer.setUploadDirectoryName((String)null);
               if (initOne) {
                  break;
               }
            case 35:
               this._UserFileSystem = new PartitionUserFileSystemMBeanImpl(this, 35);
               this._postCreate((AbstractDescriptorBean)this._UserFileSystem);
               if (initOne) {
                  break;
               }
            case 55:
               this._WebService = new WebServiceMBeanImpl(this, 55);
               this._postCreate((AbstractDescriptorBean)this._WebService);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 56:
               this._EagerTrackingOfResourceMetricsEnabled = false;
               if (initOne) {
                  break;
               }
            case 53:
               this._IgnoreSessionsDuringShutdown = false;
               if (initOne) {
                  break;
               }
            case 48:
               this._ParallelDeployApplicationModules = false;
               if (initOne) {
                  break;
               }
            case 47:
               this._ParallelDeployApplications = true;
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
      return "Partition";
   }

   public void putValue(String name, Object v) {
      if (name.equals("AdminVirtualTarget")) {
         AdminVirtualTargetMBean oldVal = this._AdminVirtualTarget;
         this._AdminVirtualTarget = (AdminVirtualTargetMBean)v;
         this._postSet(57, oldVal, this._AdminVirtualTarget);
      } else {
         TargetMBean[] oldVal;
         if (name.equals("AvailableTargets")) {
            oldVal = this._AvailableTargets;
            this._AvailableTargets = (TargetMBean[])((TargetMBean[])v);
            this._postSet(16, oldVal, this._AvailableTargets);
         } else {
            String oldVal;
            if (name.equals("BatchJobsDataSourceJndiName")) {
               oldVal = this._BatchJobsDataSourceJndiName;
               this._BatchJobsDataSourceJndiName = (String)v;
               this._postSet(45, oldVal, this._BatchJobsDataSourceJndiName);
            } else if (name.equals("BatchJobsExecutorServiceName")) {
               oldVal = this._BatchJobsExecutorServiceName;
               this._BatchJobsExecutorServiceName = (String)v;
               this._postSet(46, oldVal, this._BatchJobsExecutorServiceName);
            } else if (name.equals("CoherencePartitionCacheConfigs")) {
               CoherencePartitionCacheConfigMBean[] oldVal = this._CoherencePartitionCacheConfigs;
               this._CoherencePartitionCacheConfigs = (CoherencePartitionCacheConfigMBean[])((CoherencePartitionCacheConfigMBean[])v);
               this._postSet(33, oldVal, this._CoherencePartitionCacheConfigs);
            } else if (name.equals("DataSourceForJobScheduler")) {
               JDBCSystemResourceMBean oldVal = this._DataSourceForJobScheduler;
               this._DataSourceForJobScheduler = (JDBCSystemResourceMBean)v;
               this._postSet(28, oldVal, this._DataSourceForJobScheduler);
            } else if (name.equals("DataSourcePartition")) {
               DataSourcePartitionMBean oldVal = this._DataSourcePartition;
               this._DataSourcePartition = (DataSourcePartitionMBean)v;
               this._postSet(32, oldVal, this._DataSourcePartition);
            } else if (name.equals("DefaultTargets")) {
               oldVal = this._DefaultTargets;
               this._DefaultTargets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(15, oldVal, this._DefaultTargets);
            } else {
               boolean oldVal;
               if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("EagerTrackingOfResourceMetricsEnabled")) {
                  oldVal = this._EagerTrackingOfResourceMetricsEnabled;
                  this._EagerTrackingOfResourceMetricsEnabled = (Boolean)v;
                  this._postSet(56, oldVal, this._EagerTrackingOfResourceMetricsEnabled);
               } else if (name.equals("ForeignJNDIProviderOverrides")) {
                  ForeignJNDIProviderOverrideMBean[] oldVal = this._ForeignJNDIProviderOverrides;
                  this._ForeignJNDIProviderOverrides = (ForeignJNDIProviderOverrideMBean[])((ForeignJNDIProviderOverrideMBean[])v);
                  this._postSet(14, oldVal, this._ForeignJNDIProviderOverrides);
               } else {
                  int oldVal;
                  if (name.equals("GracefulShutdownTimeout")) {
                     oldVal = this._GracefulShutdownTimeout;
                     this._GracefulShutdownTimeout = (Integer)v;
                     this._postSet(52, oldVal, this._GracefulShutdownTimeout);
                  } else if (name.equals("IgnoreSessionsDuringShutdown")) {
                     oldVal = this._IgnoreSessionsDuringShutdown;
                     this._IgnoreSessionsDuringShutdown = (Boolean)v;
                     this._postSet(53, oldVal, this._IgnoreSessionsDuringShutdown);
                  } else if (name.equals("InternalAppDeployments")) {
                     AppDeploymentMBean[] oldVal = this._InternalAppDeployments;
                     this._InternalAppDeployments = (AppDeploymentMBean[])((AppDeploymentMBean[])v);
                     this._postSet(41, oldVal, this._InternalAppDeployments);
                  } else if (name.equals("InternalLibraries")) {
                     LibraryMBean[] oldVal = this._InternalLibraries;
                     this._InternalLibraries = (LibraryMBean[])((LibraryMBean[])v);
                     this._postSet(42, oldVal, this._InternalLibraries);
                  } else if (name.equals("JDBCSystemResourceOverrides")) {
                     JDBCSystemResourceOverrideMBean[] oldVal = this._JDBCSystemResourceOverrides;
                     this._JDBCSystemResourceOverrides = (JDBCSystemResourceOverrideMBean[])((JDBCSystemResourceOverrideMBean[])v);
                     this._postSet(12, oldVal, this._JDBCSystemResourceOverrides);
                  } else if (name.equals("JMSSystemResourceOverrides")) {
                     JMSSystemResourceOverrideMBean[] oldVal = this._JMSSystemResourceOverrides;
                     this._JMSSystemResourceOverrides = (JMSSystemResourceOverrideMBean[])((JMSSystemResourceOverrideMBean[])v);
                     this._postSet(40, oldVal, this._JMSSystemResourceOverrides);
                  } else if (name.equals("JTAPartition")) {
                     JTAPartitionMBean oldVal = this._JTAPartition;
                     this._JTAPartition = (JTAPartitionMBean)v;
                     this._postSet(27, oldVal, this._JTAPartition);
                  } else if (name.equals("JobSchedulerTableName")) {
                     oldVal = this._JobSchedulerTableName;
                     this._JobSchedulerTableName = (String)v;
                     this._postSet(29, oldVal, this._JobSchedulerTableName);
                  } else if (name.equals("MailSessionOverrides")) {
                     MailSessionOverrideMBean[] oldVal = this._MailSessionOverrides;
                     this._MailSessionOverrides = (MailSessionOverrideMBean[])((MailSessionOverrideMBean[])v);
                     this._postSet(13, oldVal, this._MailSessionOverrides);
                  } else if (name.equals("ManagedExecutorServiceTemplates")) {
                     ManagedExecutorServiceTemplateMBean[] oldVal = this._ManagedExecutorServiceTemplates;
                     this._ManagedExecutorServiceTemplates = (ManagedExecutorServiceTemplateMBean[])((ManagedExecutorServiceTemplateMBean[])v);
                     this._postSet(24, oldVal, this._ManagedExecutorServiceTemplates);
                  } else if (name.equals("ManagedScheduledExecutorServiceTemplates")) {
                     ManagedScheduledExecutorServiceTemplateMBean[] oldVal = this._ManagedScheduledExecutorServiceTemplates;
                     this._ManagedScheduledExecutorServiceTemplates = (ManagedScheduledExecutorServiceTemplateMBean[])((ManagedScheduledExecutorServiceTemplateMBean[])v);
                     this._postSet(25, oldVal, this._ManagedScheduledExecutorServiceTemplates);
                  } else if (name.equals("ManagedThreadFactoryTemplates")) {
                     ManagedThreadFactoryTemplateMBean[] oldVal = this._ManagedThreadFactoryTemplates;
                     this._ManagedThreadFactoryTemplates = (ManagedThreadFactoryTemplateMBean[])((ManagedThreadFactoryTemplateMBean[])v);
                     this._postSet(26, oldVal, this._ManagedThreadFactoryTemplates);
                  } else if (name.equals("MaxConcurrentLongRunningRequests")) {
                     oldVal = this._MaxConcurrentLongRunningRequests;
                     this._MaxConcurrentLongRunningRequests = (Integer)v;
                     this._postSet(23, oldVal, this._MaxConcurrentLongRunningRequests);
                  } else if (name.equals("MaxConcurrentNewThreads")) {
                     oldVal = this._MaxConcurrentNewThreads;
                     this._MaxConcurrentNewThreads = (Integer)v;
                     this._postSet(22, oldVal, this._MaxConcurrentNewThreads);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("ParallelDeployApplicationModules")) {
                     oldVal = this._ParallelDeployApplicationModules;
                     this._ParallelDeployApplicationModules = (Boolean)v;
                     this._postSet(48, oldVal, this._ParallelDeployApplicationModules);
                  } else if (name.equals("ParallelDeployApplications")) {
                     oldVal = this._ParallelDeployApplications;
                     this._ParallelDeployApplications = (Boolean)v;
                     this._postSet(47, oldVal, this._ParallelDeployApplications);
                  } else if (name.equals("PartitionID")) {
                     oldVal = this._PartitionID;
                     this._PartitionID = (String)v;
                     this._postSet(19, oldVal, this._PartitionID);
                  } else if (name.equals("PartitionLifeCycleTimeoutVal")) {
                     oldVal = this._PartitionLifeCycleTimeoutVal;
                     this._PartitionLifeCycleTimeoutVal = (Integer)v;
                     this._postSet(50, oldVal, this._PartitionLifeCycleTimeoutVal);
                  } else if (name.equals("PartitionLog")) {
                     PartitionLogMBean oldVal = this._PartitionLog;
                     this._PartitionLog = (PartitionLogMBean)v;
                     this._postSet(20, oldVal, this._PartitionLog);
                  } else {
                     PartitionWorkManagerMBean oldVal;
                     if (name.equals("PartitionWorkManager")) {
                        oldVal = this._PartitionWorkManager;
                        this._PartitionWorkManager = (PartitionWorkManagerMBean)v;
                        this._postSet(43, oldVal, this._PartitionWorkManager);
                     } else if (name.equals("PartitionWorkManagerRef")) {
                        oldVal = this._PartitionWorkManagerRef;
                        this._PartitionWorkManagerRef = (PartitionWorkManagerMBean)v;
                        this._postSet(44, oldVal, this._PartitionWorkManagerRef);
                     } else if (name.equals("PrimaryIdentityDomain")) {
                        oldVal = this._PrimaryIdentityDomain;
                        this._PrimaryIdentityDomain = (String)v;
                        this._postSet(21, oldVal, this._PrimaryIdentityDomain);
                     } else if (name.equals("RCMHistoricalDataBufferLimit")) {
                        oldVal = this._RCMHistoricalDataBufferLimit;
                        this._RCMHistoricalDataBufferLimit = (Integer)v;
                        this._postSet(54, oldVal, this._RCMHistoricalDataBufferLimit);
                     } else if (name.equals("Realm")) {
                        RealmMBean oldVal = this._Realm;
                        this._Realm = (RealmMBean)v;
                        this._postSet(18, oldVal, this._Realm);
                     } else {
                        byte[] oldVal;
                        if (name.equals("ResourceDeploymentPlan")) {
                           oldVal = this._ResourceDeploymentPlan;
                           this._ResourceDeploymentPlan = (byte[])((byte[])v);
                           this._postSet(37, oldVal, this._ResourceDeploymentPlan);
                        } else if (name.equals("ResourceDeploymentPlanDescriptor")) {
                           ResourceDeploymentPlanBean oldVal = this._ResourceDeploymentPlanDescriptor;
                           this._ResourceDeploymentPlanDescriptor = (ResourceDeploymentPlanBean)v;
                           this._postSet(39, oldVal, this._ResourceDeploymentPlanDescriptor);
                        } else if (name.equals("ResourceDeploymentPlanExternalDescriptors")) {
                           oldVal = this._ResourceDeploymentPlanExternalDescriptors;
                           this._ResourceDeploymentPlanExternalDescriptors = (byte[])((byte[])v);
                           this._postSet(38, oldVal, this._ResourceDeploymentPlanExternalDescriptors);
                        } else if (name.equals("ResourceDeploymentPlanPath")) {
                           oldVal = this._ResourceDeploymentPlanPath;
                           this._ResourceDeploymentPlanPath = (String)v;
                           this._postSet(36, oldVal, this._ResourceDeploymentPlanPath);
                        } else if (name.equals("ResourceGroups")) {
                           ResourceGroupMBean[] oldVal = this._ResourceGroups;
                           this._ResourceGroups = (ResourceGroupMBean[])((ResourceGroupMBean[])v);
                           this._postSet(11, oldVal, this._ResourceGroups);
                        } else {
                           ResourceManagerMBean oldVal;
                           if (name.equals("ResourceManager")) {
                              oldVal = this._ResourceManager;
                              this._ResourceManager = (ResourceManagerMBean)v;
                              this._postSet(30, oldVal, this._ResourceManager);
                           } else if (name.equals("ResourceManagerRef")) {
                              oldVal = this._ResourceManagerRef;
                              this._ResourceManagerRef = (ResourceManagerMBean)v;
                              this._postSet(31, oldVal, this._ResourceManagerRef);
                           } else if (name.equals("SelfTuning")) {
                              SelfTuningMBean oldVal = this._SelfTuning;
                              this._SelfTuning = (SelfTuningMBean)v;
                              this._postSet(17, oldVal, this._SelfTuning);
                           } else if (name.equals("StartupTimeout")) {
                              oldVal = this._StartupTimeout;
                              this._StartupTimeout = (Integer)v;
                              this._postSet(51, oldVal, this._StartupTimeout);
                           } else if (name.equals("SystemFileSystem")) {
                              PartitionFileSystemMBean oldVal = this._SystemFileSystem;
                              this._SystemFileSystem = (PartitionFileSystemMBean)v;
                              this._postSet(34, oldVal, this._SystemFileSystem);
                           } else if (name.equals("Tags")) {
                              String[] oldVal = this._Tags;
                              this._Tags = (String[])((String[])v);
                              this._postSet(9, oldVal, this._Tags);
                           } else if (name.equals("UploadDirectoryName")) {
                              oldVal = this._UploadDirectoryName;
                              this._UploadDirectoryName = (String)v;
                              this._postSet(49, oldVal, this._UploadDirectoryName);
                           } else if (name.equals("UserFileSystem")) {
                              PartitionUserFileSystemMBean oldVal = this._UserFileSystem;
                              this._UserFileSystem = (PartitionUserFileSystemMBean)v;
                              this._postSet(35, oldVal, this._UserFileSystem);
                           } else if (name.equals("WebService")) {
                              WebServiceMBean oldVal = this._WebService;
                              this._WebService = (WebServiceMBean)v;
                              this._postSet(55, oldVal, this._WebService);
                           } else if (name.equals("customizer")) {
                              Partition oldVal = this._customizer;
                              this._customizer = (Partition)v;
                           } else {
                              super.putValue(name, v);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AdminVirtualTarget")) {
         return this._AdminVirtualTarget;
      } else if (name.equals("AvailableTargets")) {
         return this._AvailableTargets;
      } else if (name.equals("BatchJobsDataSourceJndiName")) {
         return this._BatchJobsDataSourceJndiName;
      } else if (name.equals("BatchJobsExecutorServiceName")) {
         return this._BatchJobsExecutorServiceName;
      } else if (name.equals("CoherencePartitionCacheConfigs")) {
         return this._CoherencePartitionCacheConfigs;
      } else if (name.equals("DataSourceForJobScheduler")) {
         return this._DataSourceForJobScheduler;
      } else if (name.equals("DataSourcePartition")) {
         return this._DataSourcePartition;
      } else if (name.equals("DefaultTargets")) {
         return this._DefaultTargets;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EagerTrackingOfResourceMetricsEnabled")) {
         return new Boolean(this._EagerTrackingOfResourceMetricsEnabled);
      } else if (name.equals("ForeignJNDIProviderOverrides")) {
         return this._ForeignJNDIProviderOverrides;
      } else if (name.equals("GracefulShutdownTimeout")) {
         return new Integer(this._GracefulShutdownTimeout);
      } else if (name.equals("IgnoreSessionsDuringShutdown")) {
         return new Boolean(this._IgnoreSessionsDuringShutdown);
      } else if (name.equals("InternalAppDeployments")) {
         return this._InternalAppDeployments;
      } else if (name.equals("InternalLibraries")) {
         return this._InternalLibraries;
      } else if (name.equals("JDBCSystemResourceOverrides")) {
         return this._JDBCSystemResourceOverrides;
      } else if (name.equals("JMSSystemResourceOverrides")) {
         return this._JMSSystemResourceOverrides;
      } else if (name.equals("JTAPartition")) {
         return this._JTAPartition;
      } else if (name.equals("JobSchedulerTableName")) {
         return this._JobSchedulerTableName;
      } else if (name.equals("MailSessionOverrides")) {
         return this._MailSessionOverrides;
      } else if (name.equals("ManagedExecutorServiceTemplates")) {
         return this._ManagedExecutorServiceTemplates;
      } else if (name.equals("ManagedScheduledExecutorServiceTemplates")) {
         return this._ManagedScheduledExecutorServiceTemplates;
      } else if (name.equals("ManagedThreadFactoryTemplates")) {
         return this._ManagedThreadFactoryTemplates;
      } else if (name.equals("MaxConcurrentLongRunningRequests")) {
         return new Integer(this._MaxConcurrentLongRunningRequests);
      } else if (name.equals("MaxConcurrentNewThreads")) {
         return new Integer(this._MaxConcurrentNewThreads);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("ParallelDeployApplicationModules")) {
         return new Boolean(this._ParallelDeployApplicationModules);
      } else if (name.equals("ParallelDeployApplications")) {
         return new Boolean(this._ParallelDeployApplications);
      } else if (name.equals("PartitionID")) {
         return this._PartitionID;
      } else if (name.equals("PartitionLifeCycleTimeoutVal")) {
         return new Integer(this._PartitionLifeCycleTimeoutVal);
      } else if (name.equals("PartitionLog")) {
         return this._PartitionLog;
      } else if (name.equals("PartitionWorkManager")) {
         return this._PartitionWorkManager;
      } else if (name.equals("PartitionWorkManagerRef")) {
         return this._PartitionWorkManagerRef;
      } else if (name.equals("PrimaryIdentityDomain")) {
         return this._PrimaryIdentityDomain;
      } else if (name.equals("RCMHistoricalDataBufferLimit")) {
         return new Integer(this._RCMHistoricalDataBufferLimit);
      } else if (name.equals("Realm")) {
         return this._Realm;
      } else if (name.equals("ResourceDeploymentPlan")) {
         return this._ResourceDeploymentPlan;
      } else if (name.equals("ResourceDeploymentPlanDescriptor")) {
         return this._ResourceDeploymentPlanDescriptor;
      } else if (name.equals("ResourceDeploymentPlanExternalDescriptors")) {
         return this._ResourceDeploymentPlanExternalDescriptors;
      } else if (name.equals("ResourceDeploymentPlanPath")) {
         return this._ResourceDeploymentPlanPath;
      } else if (name.equals("ResourceGroups")) {
         return this._ResourceGroups;
      } else if (name.equals("ResourceManager")) {
         return this._ResourceManager;
      } else if (name.equals("ResourceManagerRef")) {
         return this._ResourceManagerRef;
      } else if (name.equals("SelfTuning")) {
         return this._SelfTuning;
      } else if (name.equals("StartupTimeout")) {
         return new Integer(this._StartupTimeout);
      } else if (name.equals("SystemFileSystem")) {
         return this._SystemFileSystem;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UploadDirectoryName")) {
         return this._UploadDirectoryName;
      } else if (name.equals("UserFileSystem")) {
         return this._UserFileSystem;
      } else if (name.equals("WebService")) {
         return this._WebService;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationPropertiesMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
               break;
            case 5:
               if (s.equals("realm")) {
                  return 18;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 17:
            case 27:
            case 34:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            default:
               break;
            case 11:
               if (s.equals("partitionid")) {
                  return 19;
               }

               if (s.equals("self-tuning")) {
                  return 17;
               }

               if (s.equals("web-service")) {
                  return 55;
               }
               break;
            case 13:
               if (s.equals("jta-partition")) {
                  return 27;
               }

               if (s.equals("partition-log")) {
                  return 20;
               }
               break;
            case 14:
               if (s.equals("default-target")) {
                  return 15;
               }

               if (s.equals("resource-group")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("startup-timeout")) {
                  return 51;
               }
               break;
            case 16:
               if (s.equals("available-target")) {
                  return 16;
               }

               if (s.equals("internal-library")) {
                  return 42;
               }

               if (s.equals("resource-manager")) {
                  return 30;
               }

               if (s.equals("user-file-system")) {
                  return 35;
               }
               break;
            case 18:
               if (s.equals("system-file-system")) {
                  return 34;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("admin-virtual-target")) {
                  return 57;
               }

               if (s.equals("resource-manager-ref")) {
                  return 31;
               }
               break;
            case 21:
               if (s.equals("data-source-partition")) {
                  return 32;
               }

               if (s.equals("mail-session-override")) {
                  return 13;
               }

               if (s.equals("upload-directory-name")) {
                  return 49;
               }
               break;
            case 22:
               if (s.equals("partition-work-manager")) {
                  return 43;
               }
               break;
            case 23:
               if (s.equals("internal-app-deployment")) {
                  return 41;
               }

               if (s.equals("primary-identity-domain")) {
                  return 21;
               }
               break;
            case 24:
               if (s.equals("job-scheduler-table-name")) {
                  return 29;
               }

               if (s.equals("resource-deployment-plan")) {
                  return 37;
               }
               break;
            case 25:
               if (s.equals("graceful-shutdown-timeout")) {
                  return 52;
               }
               break;
            case 26:
               if (s.equals("max-concurrent-new-threads")) {
                  return 22;
               }

               if (s.equals("partition-work-manager-ref")) {
                  return 44;
               }
               break;
            case 28:
               if (s.equals("jms-system-resource-override")) {
                  return 40;
               }

               if (s.equals("parallel-deploy-applications")) {
                  return 47;
               }
               break;
            case 29:
               if (s.equals("data-source-for-job-scheduler")) {
                  return 28;
               }

               if (s.equals("jdbc-system-resource-override")) {
                  return 12;
               }

               if (s.equals("resource-deployment-plan-path")) {
                  return 36;
               }
               break;
            case 30:
               if (s.equals("foreign-jndi-provider-override")) {
                  return 14;
               }
               break;
            case 31:
               if (s.equals("managed-thread-factory-template")) {
                  return 26;
               }

               if (s.equals("ignore-sessions-during-shutdown")) {
                  return 53;
               }
               break;
            case 32:
               if (s.equals("batch-jobs-data-source-jndi-name")) {
                  return 45;
               }

               if (s.equals("batch-jobs-executor-service-name")) {
                  return 46;
               }

               if (s.equals("coherence-partition-cache-config")) {
                  return 33;
               }

               if (s.equals("partition-life-cycle-timeout-val")) {
                  return 50;
               }

               if (s.equals("rcm-historical-data-buffer-limit")) {
                  return 54;
               }
               break;
            case 33:
               if (s.equals("managed-executor-service-template")) {
                  return 24;
               }
               break;
            case 35:
               if (s.equals("resource-deployment-plan-descriptor")) {
                  return 39;
               }

               if (s.equals("parallel-deploy-application-modules")) {
                  return 48;
               }
               break;
            case 36:
               if (s.equals("max-concurrent-long-running-requests")) {
                  return 23;
               }
               break;
            case 42:
               if (s.equals("eager-tracking-of-resource-metrics-enabled")) {
                  return 56;
               }
               break;
            case 43:
               if (s.equals("managed-scheduled-executor-service-template")) {
                  return 25;
               }
               break;
            case 44:
               if (s.equals("resource-deployment-plan-external-descriptor")) {
                  return 38;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            case 11:
               return new ResourceGroupMBeanImpl.SchemaHelper2();
            case 12:
               return new JDBCSystemResourceOverrideMBeanImpl.SchemaHelper2();
            case 13:
               return new MailSessionOverrideMBeanImpl.SchemaHelper2();
            case 14:
               return new ForeignJNDIProviderOverrideMBeanImpl.SchemaHelper2();
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 28:
            case 29:
            case 31:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 42:
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
            case 56:
            default:
               return super.getSchemaHelper(propIndex);
            case 17:
               return new SelfTuningMBeanImpl.SchemaHelper2();
            case 20:
               return new PartitionLogMBeanImpl.SchemaHelper2();
            case 24:
               return new ManagedExecutorServiceTemplateMBeanImpl.SchemaHelper2();
            case 25:
               return new ManagedScheduledExecutorServiceTemplateMBeanImpl.SchemaHelper2();
            case 26:
               return new ManagedThreadFactoryTemplateMBeanImpl.SchemaHelper2();
            case 27:
               return new JTAPartitionMBeanImpl.SchemaHelper2();
            case 30:
               return new ResourceManagerMBeanImpl.SchemaHelper2();
            case 32:
               return new DataSourcePartitionMBeanImpl.SchemaHelper2();
            case 33:
               return new CoherencePartitionCacheConfigMBeanImpl.SchemaHelper2();
            case 34:
               return new PartitionFileSystemMBeanImpl.SchemaHelper2();
            case 35:
               return new PartitionUserFileSystemMBeanImpl.SchemaHelper2();
            case 40:
               return new JMSSystemResourceOverrideMBeanImpl.SchemaHelper2();
            case 43:
               return new PartitionWorkManagerMBeanImpl.SchemaHelper2();
            case 55:
               return new WebServiceMBeanImpl.SchemaHelper2();
            case 57:
               return new AdminVirtualTargetMBeanImpl.SchemaHelper2();
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 11:
               return "resource-group";
            case 12:
               return "jdbc-system-resource-override";
            case 13:
               return "mail-session-override";
            case 14:
               return "foreign-jndi-provider-override";
            case 15:
               return "default-target";
            case 16:
               return "available-target";
            case 17:
               return "self-tuning";
            case 18:
               return "realm";
            case 19:
               return "partitionid";
            case 20:
               return "partition-log";
            case 21:
               return "primary-identity-domain";
            case 22:
               return "max-concurrent-new-threads";
            case 23:
               return "max-concurrent-long-running-requests";
            case 24:
               return "managed-executor-service-template";
            case 25:
               return "managed-scheduled-executor-service-template";
            case 26:
               return "managed-thread-factory-template";
            case 27:
               return "jta-partition";
            case 28:
               return "data-source-for-job-scheduler";
            case 29:
               return "job-scheduler-table-name";
            case 30:
               return "resource-manager";
            case 31:
               return "resource-manager-ref";
            case 32:
               return "data-source-partition";
            case 33:
               return "coherence-partition-cache-config";
            case 34:
               return "system-file-system";
            case 35:
               return "user-file-system";
            case 36:
               return "resource-deployment-plan-path";
            case 37:
               return "resource-deployment-plan";
            case 38:
               return "resource-deployment-plan-external-descriptor";
            case 39:
               return "resource-deployment-plan-descriptor";
            case 40:
               return "jms-system-resource-override";
            case 41:
               return "internal-app-deployment";
            case 42:
               return "internal-library";
            case 43:
               return "partition-work-manager";
            case 44:
               return "partition-work-manager-ref";
            case 45:
               return "batch-jobs-data-source-jndi-name";
            case 46:
               return "batch-jobs-executor-service-name";
            case 47:
               return "parallel-deploy-applications";
            case 48:
               return "parallel-deploy-application-modules";
            case 49:
               return "upload-directory-name";
            case 50:
               return "partition-life-cycle-timeout-val";
            case 51:
               return "startup-timeout";
            case 52:
               return "graceful-shutdown-timeout";
            case 53:
               return "ignore-sessions-during-shutdown";
            case 54:
               return "rcm-historical-data-buffer-limit";
            case 55:
               return "web-service";
            case 56:
               return "eager-tracking-of-resource-metrics-enabled";
            case 57:
               return "admin-virtual-target";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
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
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
            case 36:
            case 39:
            default:
               return super.isArray(propIndex);
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 33:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 40:
               return true;
            case 41:
               return true;
            case 42:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 28:
            case 29:
            case 31:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 42:
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
            case 56:
            default:
               return super.isBean(propIndex);
            case 17:
               return true;
            case 20:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 30:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 35:
               return true;
            case 40:
               return true;
            case 43:
               return true;
            case 55:
               return true;
            case 57:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
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
      private PartitionMBeanImpl bean;

      protected Helper(PartitionMBeanImpl bean) {
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
               return "ResourceGroups";
            case 12:
               return "JDBCSystemResourceOverrides";
            case 13:
               return "MailSessionOverrides";
            case 14:
               return "ForeignJNDIProviderOverrides";
            case 15:
               return "DefaultTargets";
            case 16:
               return "AvailableTargets";
            case 17:
               return "SelfTuning";
            case 18:
               return "Realm";
            case 19:
               return "PartitionID";
            case 20:
               return "PartitionLog";
            case 21:
               return "PrimaryIdentityDomain";
            case 22:
               return "MaxConcurrentNewThreads";
            case 23:
               return "MaxConcurrentLongRunningRequests";
            case 24:
               return "ManagedExecutorServiceTemplates";
            case 25:
               return "ManagedScheduledExecutorServiceTemplates";
            case 26:
               return "ManagedThreadFactoryTemplates";
            case 27:
               return "JTAPartition";
            case 28:
               return "DataSourceForJobScheduler";
            case 29:
               return "JobSchedulerTableName";
            case 30:
               return "ResourceManager";
            case 31:
               return "ResourceManagerRef";
            case 32:
               return "DataSourcePartition";
            case 33:
               return "CoherencePartitionCacheConfigs";
            case 34:
               return "SystemFileSystem";
            case 35:
               return "UserFileSystem";
            case 36:
               return "ResourceDeploymentPlanPath";
            case 37:
               return "ResourceDeploymentPlan";
            case 38:
               return "ResourceDeploymentPlanExternalDescriptors";
            case 39:
               return "ResourceDeploymentPlanDescriptor";
            case 40:
               return "JMSSystemResourceOverrides";
            case 41:
               return "InternalAppDeployments";
            case 42:
               return "InternalLibraries";
            case 43:
               return "PartitionWorkManager";
            case 44:
               return "PartitionWorkManagerRef";
            case 45:
               return "BatchJobsDataSourceJndiName";
            case 46:
               return "BatchJobsExecutorServiceName";
            case 47:
               return "ParallelDeployApplications";
            case 48:
               return "ParallelDeployApplicationModules";
            case 49:
               return "UploadDirectoryName";
            case 50:
               return "PartitionLifeCycleTimeoutVal";
            case 51:
               return "StartupTimeout";
            case 52:
               return "GracefulShutdownTimeout";
            case 53:
               return "IgnoreSessionsDuringShutdown";
            case 54:
               return "RCMHistoricalDataBufferLimit";
            case 55:
               return "WebService";
            case 56:
               return "EagerTrackingOfResourceMetricsEnabled";
            case 57:
               return "AdminVirtualTarget";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdminVirtualTarget")) {
            return 57;
         } else if (propName.equals("AvailableTargets")) {
            return 16;
         } else if (propName.equals("BatchJobsDataSourceJndiName")) {
            return 45;
         } else if (propName.equals("BatchJobsExecutorServiceName")) {
            return 46;
         } else if (propName.equals("CoherencePartitionCacheConfigs")) {
            return 33;
         } else if (propName.equals("DataSourceForJobScheduler")) {
            return 28;
         } else if (propName.equals("DataSourcePartition")) {
            return 32;
         } else if (propName.equals("DefaultTargets")) {
            return 15;
         } else if (propName.equals("ForeignJNDIProviderOverrides")) {
            return 14;
         } else if (propName.equals("GracefulShutdownTimeout")) {
            return 52;
         } else if (propName.equals("InternalAppDeployments")) {
            return 41;
         } else if (propName.equals("InternalLibraries")) {
            return 42;
         } else if (propName.equals("JDBCSystemResourceOverrides")) {
            return 12;
         } else if (propName.equals("JMSSystemResourceOverrides")) {
            return 40;
         } else if (propName.equals("JTAPartition")) {
            return 27;
         } else if (propName.equals("JobSchedulerTableName")) {
            return 29;
         } else if (propName.equals("MailSessionOverrides")) {
            return 13;
         } else if (propName.equals("ManagedExecutorServiceTemplates")) {
            return 24;
         } else if (propName.equals("ManagedScheduledExecutorServiceTemplates")) {
            return 25;
         } else if (propName.equals("ManagedThreadFactoryTemplates")) {
            return 26;
         } else if (propName.equals("MaxConcurrentLongRunningRequests")) {
            return 23;
         } else if (propName.equals("MaxConcurrentNewThreads")) {
            return 22;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PartitionID")) {
            return 19;
         } else if (propName.equals("PartitionLifeCycleTimeoutVal")) {
            return 50;
         } else if (propName.equals("PartitionLog")) {
            return 20;
         } else if (propName.equals("PartitionWorkManager")) {
            return 43;
         } else if (propName.equals("PartitionWorkManagerRef")) {
            return 44;
         } else if (propName.equals("PrimaryIdentityDomain")) {
            return 21;
         } else if (propName.equals("RCMHistoricalDataBufferLimit")) {
            return 54;
         } else if (propName.equals("Realm")) {
            return 18;
         } else if (propName.equals("ResourceDeploymentPlan")) {
            return 37;
         } else if (propName.equals("ResourceDeploymentPlanDescriptor")) {
            return 39;
         } else if (propName.equals("ResourceDeploymentPlanExternalDescriptors")) {
            return 38;
         } else if (propName.equals("ResourceDeploymentPlanPath")) {
            return 36;
         } else if (propName.equals("ResourceGroups")) {
            return 11;
         } else if (propName.equals("ResourceManager")) {
            return 30;
         } else if (propName.equals("ResourceManagerRef")) {
            return 31;
         } else if (propName.equals("SelfTuning")) {
            return 17;
         } else if (propName.equals("StartupTimeout")) {
            return 51;
         } else if (propName.equals("SystemFileSystem")) {
            return 34;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UploadDirectoryName")) {
            return 49;
         } else if (propName.equals("UserFileSystem")) {
            return 35;
         } else if (propName.equals("WebService")) {
            return 55;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("EagerTrackingOfResourceMetricsEnabled")) {
            return 56;
         } else if (propName.equals("IgnoreSessionsDuringShutdown")) {
            return 53;
         } else if (propName.equals("ParallelDeployApplicationModules")) {
            return 48;
         } else {
            return propName.equals("ParallelDeployApplications") ? 47 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAdminVirtualTarget() != null) {
            iterators.add(new ArrayIterator(new AdminVirtualTargetMBean[]{this.bean.getAdminVirtualTarget()}));
         }

         iterators.add(new ArrayIterator(this.bean.getCoherencePartitionCacheConfigs()));
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         if (this.bean.getDataSourcePartition() != null) {
            iterators.add(new ArrayIterator(new DataSourcePartitionMBean[]{this.bean.getDataSourcePartition()}));
         }

         iterators.add(new ArrayIterator(this.bean.getForeignJNDIProviderOverrides()));
         iterators.add(new ArrayIterator(this.bean.getJDBCSystemResourceOverrides()));
         iterators.add(new ArrayIterator(this.bean.getJMSSystemResourceOverrides()));
         if (this.bean.getJTAPartition() != null) {
            iterators.add(new ArrayIterator(new JTAPartitionMBean[]{this.bean.getJTAPartition()}));
         }

         iterators.add(new ArrayIterator(this.bean.getMailSessionOverrides()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServiceTemplates()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServiceTemplates()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactoryTemplates()));
         if (this.bean.getPartitionLog() != null) {
            iterators.add(new ArrayIterator(new PartitionLogMBean[]{this.bean.getPartitionLog()}));
         }

         if (this.bean.getPartitionWorkManager() != null) {
            iterators.add(new ArrayIterator(new PartitionWorkManagerMBean[]{this.bean.getPartitionWorkManager()}));
         }

         iterators.add(new ArrayIterator(this.bean.getResourceGroups()));
         if (this.bean.getResourceManager() != null) {
            iterators.add(new ArrayIterator(new ResourceManagerMBean[]{this.bean.getResourceManager()}));
         }

         if (this.bean.getSelfTuning() != null) {
            iterators.add(new ArrayIterator(new SelfTuningMBean[]{this.bean.getSelfTuning()}));
         }

         if (this.bean.getSystemFileSystem() != null) {
            iterators.add(new ArrayIterator(new PartitionFileSystemMBean[]{this.bean.getSystemFileSystem()}));
         }

         if (this.bean.getUserFileSystem() != null) {
            iterators.add(new ArrayIterator(new PartitionUserFileSystemMBean[]{this.bean.getUserFileSystem()}));
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
            childValue = this.computeChildHashValue(this.bean.getAdminVirtualTarget());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAvailableTargetsSet()) {
               buf.append("AvailableTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAvailableTargets())));
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

            int i;
            for(i = 0; i < this.bean.getCoherencePartitionCacheConfigs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherencePartitionCacheConfigs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDataSourceForJobSchedulerSet()) {
               buf.append("DataSourceForJobScheduler");
               buf.append(String.valueOf(this.bean.getDataSourceForJobScheduler()));
            }

            childValue = this.computeChildHashValue(this.bean.getDataSourcePartition());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDefaultTargetsSet()) {
               buf.append("DefaultTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultTargets())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJNDIProviderOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJNDIProviderOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isGracefulShutdownTimeoutSet()) {
               buf.append("GracefulShutdownTimeout");
               buf.append(String.valueOf(this.bean.getGracefulShutdownTimeout()));
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

            for(i = 0; i < this.bean.getJDBCSystemResourceOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCSystemResourceOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSSystemResourceOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSSystemResourceOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJTAPartition());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJobSchedulerTableNameSet()) {
               buf.append("JobSchedulerTableName");
               buf.append(String.valueOf(this.bean.getJobSchedulerTableName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessionOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessionOverrides()[i]);
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

            for(i = 0; i < this.bean.getManagedScheduledExecutorServiceTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedScheduledExecutorServiceTemplates()[i]);
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

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPartitionIDSet()) {
               buf.append("PartitionID");
               buf.append(String.valueOf(this.bean.getPartitionID()));
            }

            if (this.bean.isPartitionLifeCycleTimeoutValSet()) {
               buf.append("PartitionLifeCycleTimeoutVal");
               buf.append(String.valueOf(this.bean.getPartitionLifeCycleTimeoutVal()));
            }

            childValue = this.computeChildHashValue(this.bean.getPartitionLog());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPartitionWorkManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPartitionWorkManagerRefSet()) {
               buf.append("PartitionWorkManagerRef");
               buf.append(String.valueOf(this.bean.getPartitionWorkManagerRef()));
            }

            if (this.bean.isPrimaryIdentityDomainSet()) {
               buf.append("PrimaryIdentityDomain");
               buf.append(String.valueOf(this.bean.getPrimaryIdentityDomain()));
            }

            if (this.bean.isRCMHistoricalDataBufferLimitSet()) {
               buf.append("RCMHistoricalDataBufferLimit");
               buf.append(String.valueOf(this.bean.getRCMHistoricalDataBufferLimit()));
            }

            if (this.bean.isRealmSet()) {
               buf.append("Realm");
               buf.append(String.valueOf(this.bean.getRealm()));
            }

            if (this.bean.isResourceDeploymentPlanSet()) {
               buf.append("ResourceDeploymentPlan");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getResourceDeploymentPlan())));
            }

            if (this.bean.isResourceDeploymentPlanDescriptorSet()) {
               buf.append("ResourceDeploymentPlanDescriptor");
               buf.append(String.valueOf(this.bean.getResourceDeploymentPlanDescriptor()));
            }

            if (this.bean.isResourceDeploymentPlanExternalDescriptorsSet()) {
               buf.append("ResourceDeploymentPlanExternalDescriptors");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getResourceDeploymentPlanExternalDescriptors())));
            }

            if (this.bean.isResourceDeploymentPlanPathSet()) {
               buf.append("ResourceDeploymentPlanPath");
               buf.append(String.valueOf(this.bean.getResourceDeploymentPlanPath()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceGroups().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceGroups()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getResourceManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResourceManagerRefSet()) {
               buf.append("ResourceManagerRef");
               buf.append(String.valueOf(this.bean.getResourceManagerRef()));
            }

            childValue = this.computeChildHashValue(this.bean.getSelfTuning());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStartupTimeoutSet()) {
               buf.append("StartupTimeout");
               buf.append(String.valueOf(this.bean.getStartupTimeout()));
            }

            childValue = this.computeChildHashValue(this.bean.getSystemFileSystem());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUploadDirectoryNameSet()) {
               buf.append("UploadDirectoryName");
               buf.append(String.valueOf(this.bean.getUploadDirectoryName()));
            }

            childValue = this.computeChildHashValue(this.bean.getUserFileSystem());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebService());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEagerTrackingOfResourceMetricsEnabledSet()) {
               buf.append("EagerTrackingOfResourceMetricsEnabled");
               buf.append(String.valueOf(this.bean.isEagerTrackingOfResourceMetricsEnabled()));
            }

            if (this.bean.isIgnoreSessionsDuringShutdownSet()) {
               buf.append("IgnoreSessionsDuringShutdown");
               buf.append(String.valueOf(this.bean.isIgnoreSessionsDuringShutdown()));
            }

            if (this.bean.isParallelDeployApplicationModulesSet()) {
               buf.append("ParallelDeployApplicationModules");
               buf.append(String.valueOf(this.bean.isParallelDeployApplicationModules()));
            }

            if (this.bean.isParallelDeployApplicationsSet()) {
               buf.append("ParallelDeployApplications");
               buf.append(String.valueOf(this.bean.isParallelDeployApplications()));
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
            PartitionMBeanImpl otherTyped = (PartitionMBeanImpl)other;
            this.computeChildDiff("AdminVirtualTarget", this.bean.getAdminVirtualTarget(), otherTyped.getAdminVirtualTarget(), true);
            this.computeDiff("AvailableTargets", this.bean.getAvailableTargets(), otherTyped.getAvailableTargets(), true);
            this.computeDiff("BatchJobsDataSourceJndiName", this.bean.getBatchJobsDataSourceJndiName(), otherTyped.getBatchJobsDataSourceJndiName(), true);
            this.computeDiff("BatchJobsExecutorServiceName", this.bean.getBatchJobsExecutorServiceName(), otherTyped.getBatchJobsExecutorServiceName(), true);
            this.computeChildDiff("CoherencePartitionCacheConfigs", this.bean.getCoherencePartitionCacheConfigs(), otherTyped.getCoherencePartitionCacheConfigs(), true);
            this.computeDiff("DataSourceForJobScheduler", this.bean.getDataSourceForJobScheduler(), otherTyped.getDataSourceForJobScheduler(), true);
            this.computeSubDiff("DataSourcePartition", this.bean.getDataSourcePartition(), otherTyped.getDataSourcePartition());
            this.computeDiff("DefaultTargets", this.bean.getDefaultTargets(), otherTyped.getDefaultTargets(), true);
            this.computeChildDiff("ForeignJNDIProviderOverrides", this.bean.getForeignJNDIProviderOverrides(), otherTyped.getForeignJNDIProviderOverrides(), true);
            this.computeDiff("GracefulShutdownTimeout", this.bean.getGracefulShutdownTimeout(), otherTyped.getGracefulShutdownTimeout(), true);
            this.computeChildDiff("JDBCSystemResourceOverrides", this.bean.getJDBCSystemResourceOverrides(), otherTyped.getJDBCSystemResourceOverrides(), true);
            this.computeChildDiff("JMSSystemResourceOverrides", this.bean.getJMSSystemResourceOverrides(), otherTyped.getJMSSystemResourceOverrides(), true);
            this.computeSubDiff("JTAPartition", this.bean.getJTAPartition(), otherTyped.getJTAPartition());
            this.computeDiff("JobSchedulerTableName", this.bean.getJobSchedulerTableName(), otherTyped.getJobSchedulerTableName(), true);
            this.computeChildDiff("MailSessionOverrides", this.bean.getMailSessionOverrides(), otherTyped.getMailSessionOverrides(), true);
            this.computeChildDiff("ManagedExecutorServiceTemplates", this.bean.getManagedExecutorServiceTemplates(), otherTyped.getManagedExecutorServiceTemplates(), true);
            this.computeChildDiff("ManagedScheduledExecutorServiceTemplates", this.bean.getManagedScheduledExecutorServiceTemplates(), otherTyped.getManagedScheduledExecutorServiceTemplates(), true);
            this.computeChildDiff("ManagedThreadFactoryTemplates", this.bean.getManagedThreadFactoryTemplates(), otherTyped.getManagedThreadFactoryTemplates(), true);
            this.computeDiff("MaxConcurrentLongRunningRequests", this.bean.getMaxConcurrentLongRunningRequests(), otherTyped.getMaxConcurrentLongRunningRequests(), true);
            this.computeDiff("MaxConcurrentNewThreads", this.bean.getMaxConcurrentNewThreads(), otherTyped.getMaxConcurrentNewThreads(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PartitionID", this.bean.getPartitionID(), otherTyped.getPartitionID(), false);
            this.computeDiff("PartitionLifeCycleTimeoutVal", this.bean.getPartitionLifeCycleTimeoutVal(), otherTyped.getPartitionLifeCycleTimeoutVal(), true);
            this.computeSubDiff("PartitionLog", this.bean.getPartitionLog(), otherTyped.getPartitionLog());
            this.computeChildDiff("PartitionWorkManager", this.bean.getPartitionWorkManager(), otherTyped.getPartitionWorkManager(), true);
            this.computeDiff("PartitionWorkManagerRef", this.bean.getPartitionWorkManagerRef(), otherTyped.getPartitionWorkManagerRef(), true);
            this.computeDiff("PrimaryIdentityDomain", this.bean.getPrimaryIdentityDomain(), otherTyped.getPrimaryIdentityDomain(), true);
            this.computeDiff("RCMHistoricalDataBufferLimit", this.bean.getRCMHistoricalDataBufferLimit(), otherTyped.getRCMHistoricalDataBufferLimit(), true);
            this.computeDiff("Realm", this.bean.getRealm(), otherTyped.getRealm(), true);
            this.computeDiff("ResourceDeploymentPlanPath", this.bean.getResourceDeploymentPlanPath(), otherTyped.getResourceDeploymentPlanPath(), true);
            this.computeChildDiff("ResourceGroups", this.bean.getResourceGroups(), otherTyped.getResourceGroups(), true);
            this.computeChildDiff("ResourceManager", this.bean.getResourceManager(), otherTyped.getResourceManager(), true);
            this.computeDiff("ResourceManagerRef", this.bean.getResourceManagerRef(), otherTyped.getResourceManagerRef(), true);
            this.computeSubDiff("SelfTuning", this.bean.getSelfTuning(), otherTyped.getSelfTuning());
            this.computeDiff("StartupTimeout", this.bean.getStartupTimeout(), otherTyped.getStartupTimeout(), false);
            this.computeSubDiff("SystemFileSystem", this.bean.getSystemFileSystem(), otherTyped.getSystemFileSystem());
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UploadDirectoryName", this.bean.getUploadDirectoryName(), otherTyped.getUploadDirectoryName(), true);
            this.computeSubDiff("UserFileSystem", this.bean.getUserFileSystem(), otherTyped.getUserFileSystem());
            this.computeSubDiff("WebService", this.bean.getWebService(), otherTyped.getWebService());
            this.computeDiff("EagerTrackingOfResourceMetricsEnabled", this.bean.isEagerTrackingOfResourceMetricsEnabled(), otherTyped.isEagerTrackingOfResourceMetricsEnabled(), false);
            this.computeDiff("IgnoreSessionsDuringShutdown", this.bean.isIgnoreSessionsDuringShutdown(), otherTyped.isIgnoreSessionsDuringShutdown(), true);
            this.computeDiff("ParallelDeployApplicationModules", this.bean.isParallelDeployApplicationModules(), otherTyped.isParallelDeployApplicationModules(), true);
            this.computeDiff("ParallelDeployApplications", this.bean.isParallelDeployApplications(), otherTyped.isParallelDeployApplications(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PartitionMBeanImpl original = (PartitionMBeanImpl)event.getSourceBean();
            PartitionMBeanImpl proposed = (PartitionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdminVirtualTarget")) {
                  if (type == 2) {
                     original.setAdminVirtualTarget((AdminVirtualTargetMBean)this.createCopy((AbstractDescriptorBean)proposed.getAdminVirtualTarget()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AdminVirtualTarget", original.getAdminVirtualTarget());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 57);
               } else if (prop.equals("AvailableTargets")) {
                  original.setAvailableTargetsAsString(proposed.getAvailableTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("BatchJobsDataSourceJndiName")) {
                  original.setBatchJobsDataSourceJndiName(proposed.getBatchJobsDataSourceJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("BatchJobsExecutorServiceName")) {
                  original.setBatchJobsExecutorServiceName(proposed.getBatchJobsExecutorServiceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("CoherencePartitionCacheConfigs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherencePartitionCacheConfig((CoherencePartitionCacheConfigMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherencePartitionCacheConfig((CoherencePartitionCacheConfigMBean)update.getRemovedObject());
                  }

                  if (original.getCoherencePartitionCacheConfigs() == null || original.getCoherencePartitionCacheConfigs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  }
               } else if (prop.equals("DataSourceForJobScheduler")) {
                  original.setDataSourceForJobSchedulerAsString(proposed.getDataSourceForJobSchedulerAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("DataSourcePartition")) {
                  if (type == 2) {
                     original.setDataSourcePartition((DataSourcePartitionMBean)this.createCopy((AbstractDescriptorBean)proposed.getDataSourcePartition()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DataSourcePartition", original.getDataSourcePartition());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("DefaultTargets")) {
                  original.setDefaultTargetsAsString(proposed.getDefaultTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ForeignJNDIProviderOverrides")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignJNDIProviderOverride((ForeignJNDIProviderOverrideMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignJNDIProviderOverride((ForeignJNDIProviderOverrideMBean)update.getRemovedObject());
                  }

                  if (original.getForeignJNDIProviderOverrides() == null || original.getForeignJNDIProviderOverrides().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("GracefulShutdownTimeout")) {
                  original.setGracefulShutdownTimeout(proposed.getGracefulShutdownTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (!prop.equals("InternalAppDeployments") && !prop.equals("InternalLibraries")) {
                  if (prop.equals("JDBCSystemResourceOverrides")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addJDBCSystemResourceOverride((JDBCSystemResourceOverrideMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeJDBCSystemResourceOverride((JDBCSystemResourceOverrideMBean)update.getRemovedObject());
                     }

                     if (original.getJDBCSystemResourceOverrides() == null || original.getJDBCSystemResourceOverrides().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     }
                  } else if (prop.equals("JMSSystemResourceOverrides")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addJMSSystemResourceOverride((JMSSystemResourceOverrideMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeJMSSystemResourceOverride((JMSSystemResourceOverrideMBean)update.getRemovedObject());
                     }

                     if (original.getJMSSystemResourceOverrides() == null || original.getJMSSystemResourceOverrides().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 40);
                     }
                  } else if (prop.equals("JTAPartition")) {
                     if (type == 2) {
                        original.setJTAPartition((JTAPartitionMBean)this.createCopy((AbstractDescriptorBean)proposed.getJTAPartition()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("JTAPartition", original.getJTAPartition());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("JobSchedulerTableName")) {
                     original.setJobSchedulerTableName(proposed.getJobSchedulerTableName());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else if (prop.equals("MailSessionOverrides")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addMailSessionOverride((MailSessionOverrideMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeMailSessionOverride((MailSessionOverrideMBean)update.getRemovedObject());
                     }

                     if (original.getMailSessionOverrides() == null || original.getMailSessionOverrides().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
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
                        original._conditionalUnset(update.isUnsetUpdate(), 24);
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
                        original._conditionalUnset(update.isUnsetUpdate(), 25);
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
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     }
                  } else if (prop.equals("MaxConcurrentLongRunningRequests")) {
                     original.setMaxConcurrentLongRunningRequests(proposed.getMaxConcurrentLongRunningRequests());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("MaxConcurrentNewThreads")) {
                     original.setMaxConcurrentNewThreads(proposed.getMaxConcurrentNewThreads());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("PartitionID")) {
                     original.setPartitionID(proposed.getPartitionID());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("PartitionLifeCycleTimeoutVal")) {
                     original.setPartitionLifeCycleTimeoutVal(proposed.getPartitionLifeCycleTimeoutVal());
                     original._conditionalUnset(update.isUnsetUpdate(), 50);
                  } else if (prop.equals("PartitionLog")) {
                     if (type == 2) {
                        original.setPartitionLog((PartitionLogMBean)this.createCopy((AbstractDescriptorBean)proposed.getPartitionLog()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("PartitionLog", original.getPartitionLog());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("PartitionWorkManager")) {
                     if (type == 2) {
                        original.setPartitionWorkManager((PartitionWorkManagerMBean)this.createCopy((AbstractDescriptorBean)proposed.getPartitionWorkManager()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("PartitionWorkManager", original.getPartitionWorkManager());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 43);
                  } else if (prop.equals("PartitionWorkManagerRef")) {
                     original.setPartitionWorkManagerRefAsString(proposed.getPartitionWorkManagerRefAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 44);
                  } else if (prop.equals("PrimaryIdentityDomain")) {
                     original.setPrimaryIdentityDomain(proposed.getPrimaryIdentityDomain());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("RCMHistoricalDataBufferLimit")) {
                     original.setRCMHistoricalDataBufferLimit(proposed.getRCMHistoricalDataBufferLimit());
                     original._conditionalUnset(update.isUnsetUpdate(), 54);
                  } else if (prop.equals("Realm")) {
                     original.setRealmAsString(proposed.getRealmAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (!prop.equals("ResourceDeploymentPlan") && !prop.equals("ResourceDeploymentPlanDescriptor") && !prop.equals("ResourceDeploymentPlanExternalDescriptors")) {
                     if (prop.equals("ResourceDeploymentPlanPath")) {
                        original.setResourceDeploymentPlanPath(proposed.getResourceDeploymentPlanPath());
                        original._conditionalUnset(update.isUnsetUpdate(), 36);
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
                           original._conditionalUnset(update.isUnsetUpdate(), 11);
                        }
                     } else if (prop.equals("ResourceManager")) {
                        if (type == 2) {
                           original.setResourceManager((ResourceManagerMBean)this.createCopy((AbstractDescriptorBean)proposed.getResourceManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ResourceManager", original.getResourceManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (prop.equals("ResourceManagerRef")) {
                        original.setResourceManagerRefAsString(proposed.getResourceManagerRefAsString());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (prop.equals("SelfTuning")) {
                        if (type == 2) {
                           original.setSelfTuning((SelfTuningMBean)this.createCopy((AbstractDescriptorBean)proposed.getSelfTuning()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SelfTuning", original.getSelfTuning());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 17);
                     } else if (prop.equals("StartupTimeout")) {
                        original.setStartupTimeout(proposed.getStartupTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 51);
                     } else if (prop.equals("SystemFileSystem")) {
                        if (type == 2) {
                           original.setSystemFileSystem((PartitionFileSystemMBean)this.createCopy((AbstractDescriptorBean)proposed.getSystemFileSystem()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SystemFileSystem", original.getSystemFileSystem());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 34);
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
                     } else if (prop.equals("UploadDirectoryName")) {
                        original.setUploadDirectoryName(proposed.getUploadDirectoryName());
                        original._conditionalUnset(update.isUnsetUpdate(), 49);
                     } else if (prop.equals("UserFileSystem")) {
                        if (type == 2) {
                           original.setUserFileSystem((PartitionUserFileSystemMBean)this.createCopy((AbstractDescriptorBean)proposed.getUserFileSystem()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("UserFileSystem", original.getUserFileSystem());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 35);
                     } else if (prop.equals("WebService")) {
                        if (type == 2) {
                           original.setWebService((WebServiceMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebService()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("WebService", original.getWebService());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 55);
                     } else if (!prop.equals("DynamicallyCreated")) {
                        if (prop.equals("EagerTrackingOfResourceMetricsEnabled")) {
                           original.setEagerTrackingOfResourceMetricsEnabled(proposed.isEagerTrackingOfResourceMetricsEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 56);
                        } else if (prop.equals("IgnoreSessionsDuringShutdown")) {
                           original.setIgnoreSessionsDuringShutdown(proposed.isIgnoreSessionsDuringShutdown());
                           original._conditionalUnset(update.isUnsetUpdate(), 53);
                        } else if (prop.equals("ParallelDeployApplicationModules")) {
                           original.setParallelDeployApplicationModules(proposed.isParallelDeployApplicationModules());
                           original._conditionalUnset(update.isUnsetUpdate(), 48);
                        } else if (prop.equals("ParallelDeployApplications")) {
                           original.setParallelDeployApplications(proposed.isParallelDeployApplications());
                           original._conditionalUnset(update.isUnsetUpdate(), 47);
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
            PartitionMBeanImpl copy = (PartitionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdminVirtualTarget")) && this.bean.isAdminVirtualTargetSet() && !copy._isSet(57)) {
               Object o = this.bean.getAdminVirtualTarget();
               copy.setAdminVirtualTarget((AdminVirtualTargetMBean)null);
               copy.setAdminVirtualTarget(o == null ? null : (AdminVirtualTargetMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AvailableTargets")) && this.bean.isAvailableTargetsSet()) {
               copy._unSet(copy, 16);
               copy.setAvailableTargetsAsString(this.bean.getAvailableTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("BatchJobsDataSourceJndiName")) && this.bean.isBatchJobsDataSourceJndiNameSet()) {
               copy.setBatchJobsDataSourceJndiName(this.bean.getBatchJobsDataSourceJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("BatchJobsExecutorServiceName")) && this.bean.isBatchJobsExecutorServiceNameSet()) {
               copy.setBatchJobsExecutorServiceName(this.bean.getBatchJobsExecutorServiceName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("CoherencePartitionCacheConfigs")) && this.bean.isCoherencePartitionCacheConfigsSet() && !copy._isSet(33)) {
               CoherencePartitionCacheConfigMBean[] oldCoherencePartitionCacheConfigs = this.bean.getCoherencePartitionCacheConfigs();
               CoherencePartitionCacheConfigMBean[] newCoherencePartitionCacheConfigs = new CoherencePartitionCacheConfigMBean[oldCoherencePartitionCacheConfigs.length];

               for(i = 0; i < newCoherencePartitionCacheConfigs.length; ++i) {
                  newCoherencePartitionCacheConfigs[i] = (CoherencePartitionCacheConfigMBean)((CoherencePartitionCacheConfigMBean)this.createCopy((AbstractDescriptorBean)oldCoherencePartitionCacheConfigs[i], includeObsolete));
               }

               copy.setCoherencePartitionCacheConfigs(newCoherencePartitionCacheConfigs);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceForJobScheduler")) && this.bean.isDataSourceForJobSchedulerSet()) {
               copy._unSet(copy, 28);
               copy.setDataSourceForJobSchedulerAsString(this.bean.getDataSourceForJobSchedulerAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourcePartition")) && this.bean.isDataSourcePartitionSet() && !copy._isSet(32)) {
               Object o = this.bean.getDataSourcePartition();
               copy.setDataSourcePartition((DataSourcePartitionMBean)null);
               copy.setDataSourcePartition(o == null ? null : (DataSourcePartitionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTargets")) && this.bean.isDefaultTargetsSet()) {
               copy._unSet(copy, 15);
               copy.setDefaultTargetsAsString(this.bean.getDefaultTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignJNDIProviderOverrides")) && this.bean.isForeignJNDIProviderOverridesSet() && !copy._isSet(14)) {
               ForeignJNDIProviderOverrideMBean[] oldForeignJNDIProviderOverrides = this.bean.getForeignJNDIProviderOverrides();
               ForeignJNDIProviderOverrideMBean[] newForeignJNDIProviderOverrides = new ForeignJNDIProviderOverrideMBean[oldForeignJNDIProviderOverrides.length];

               for(i = 0; i < newForeignJNDIProviderOverrides.length; ++i) {
                  newForeignJNDIProviderOverrides[i] = (ForeignJNDIProviderOverrideMBean)((ForeignJNDIProviderOverrideMBean)this.createCopy((AbstractDescriptorBean)oldForeignJNDIProviderOverrides[i], includeObsolete));
               }

               copy.setForeignJNDIProviderOverrides(newForeignJNDIProviderOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("GracefulShutdownTimeout")) && this.bean.isGracefulShutdownTimeoutSet()) {
               copy.setGracefulShutdownTimeout(this.bean.getGracefulShutdownTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCSystemResourceOverrides")) && this.bean.isJDBCSystemResourceOverridesSet() && !copy._isSet(12)) {
               JDBCSystemResourceOverrideMBean[] oldJDBCSystemResourceOverrides = this.bean.getJDBCSystemResourceOverrides();
               JDBCSystemResourceOverrideMBean[] newJDBCSystemResourceOverrides = new JDBCSystemResourceOverrideMBean[oldJDBCSystemResourceOverrides.length];

               for(i = 0; i < newJDBCSystemResourceOverrides.length; ++i) {
                  newJDBCSystemResourceOverrides[i] = (JDBCSystemResourceOverrideMBean)((JDBCSystemResourceOverrideMBean)this.createCopy((AbstractDescriptorBean)oldJDBCSystemResourceOverrides[i], includeObsolete));
               }

               copy.setJDBCSystemResourceOverrides(newJDBCSystemResourceOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSSystemResourceOverrides")) && this.bean.isJMSSystemResourceOverridesSet() && !copy._isSet(40)) {
               JMSSystemResourceOverrideMBean[] oldJMSSystemResourceOverrides = this.bean.getJMSSystemResourceOverrides();
               JMSSystemResourceOverrideMBean[] newJMSSystemResourceOverrides = new JMSSystemResourceOverrideMBean[oldJMSSystemResourceOverrides.length];

               for(i = 0; i < newJMSSystemResourceOverrides.length; ++i) {
                  newJMSSystemResourceOverrides[i] = (JMSSystemResourceOverrideMBean)((JMSSystemResourceOverrideMBean)this.createCopy((AbstractDescriptorBean)oldJMSSystemResourceOverrides[i], includeObsolete));
               }

               copy.setJMSSystemResourceOverrides(newJMSSystemResourceOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("JTAPartition")) && this.bean.isJTAPartitionSet() && !copy._isSet(27)) {
               Object o = this.bean.getJTAPartition();
               copy.setJTAPartition((JTAPartitionMBean)null);
               copy.setJTAPartition(o == null ? null : (JTAPartitionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JobSchedulerTableName")) && this.bean.isJobSchedulerTableNameSet()) {
               copy.setJobSchedulerTableName(this.bean.getJobSchedulerTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessionOverrides")) && this.bean.isMailSessionOverridesSet() && !copy._isSet(13)) {
               MailSessionOverrideMBean[] oldMailSessionOverrides = this.bean.getMailSessionOverrides();
               MailSessionOverrideMBean[] newMailSessionOverrides = new MailSessionOverrideMBean[oldMailSessionOverrides.length];

               for(i = 0; i < newMailSessionOverrides.length; ++i) {
                  newMailSessionOverrides[i] = (MailSessionOverrideMBean)((MailSessionOverrideMBean)this.createCopy((AbstractDescriptorBean)oldMailSessionOverrides[i], includeObsolete));
               }

               copy.setMailSessionOverrides(newMailSessionOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServiceTemplates")) && this.bean.isManagedExecutorServiceTemplatesSet() && !copy._isSet(24)) {
               ManagedExecutorServiceTemplateMBean[] oldManagedExecutorServiceTemplates = this.bean.getManagedExecutorServiceTemplates();
               ManagedExecutorServiceTemplateMBean[] newManagedExecutorServiceTemplates = new ManagedExecutorServiceTemplateMBean[oldManagedExecutorServiceTemplates.length];

               for(i = 0; i < newManagedExecutorServiceTemplates.length; ++i) {
                  newManagedExecutorServiceTemplates[i] = (ManagedExecutorServiceTemplateMBean)((ManagedExecutorServiceTemplateMBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServiceTemplates[i], includeObsolete));
               }

               copy.setManagedExecutorServiceTemplates(newManagedExecutorServiceTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServiceTemplates")) && this.bean.isManagedScheduledExecutorServiceTemplatesSet() && !copy._isSet(25)) {
               ManagedScheduledExecutorServiceTemplateMBean[] oldManagedScheduledExecutorServiceTemplates = this.bean.getManagedScheduledExecutorServiceTemplates();
               ManagedScheduledExecutorServiceTemplateMBean[] newManagedScheduledExecutorServiceTemplates = new ManagedScheduledExecutorServiceTemplateMBean[oldManagedScheduledExecutorServiceTemplates.length];

               for(i = 0; i < newManagedScheduledExecutorServiceTemplates.length; ++i) {
                  newManagedScheduledExecutorServiceTemplates[i] = (ManagedScheduledExecutorServiceTemplateMBean)((ManagedScheduledExecutorServiceTemplateMBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServiceTemplates[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServiceTemplates(newManagedScheduledExecutorServiceTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactoryTemplates")) && this.bean.isManagedThreadFactoryTemplatesSet() && !copy._isSet(26)) {
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

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionID")) && this.bean.isPartitionIDSet()) {
               copy.setPartitionID(this.bean.getPartitionID());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionLifeCycleTimeoutVal")) && this.bean.isPartitionLifeCycleTimeoutValSet()) {
               copy.setPartitionLifeCycleTimeoutVal(this.bean.getPartitionLifeCycleTimeoutVal());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionLog")) && this.bean.isPartitionLogSet() && !copy._isSet(20)) {
               Object o = this.bean.getPartitionLog();
               copy.setPartitionLog((PartitionLogMBean)null);
               copy.setPartitionLog(o == null ? null : (PartitionLogMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionWorkManager")) && this.bean.isPartitionWorkManagerSet() && !copy._isSet(43)) {
               Object o = this.bean.getPartitionWorkManager();
               copy.setPartitionWorkManager((PartitionWorkManagerMBean)null);
               copy.setPartitionWorkManager(o == null ? null : (PartitionWorkManagerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionWorkManagerRef")) && this.bean.isPartitionWorkManagerRefSet()) {
               copy._unSet(copy, 44);
               copy.setPartitionWorkManagerRefAsString(this.bean.getPartitionWorkManagerRefAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("PrimaryIdentityDomain")) && this.bean.isPrimaryIdentityDomainSet()) {
               copy.setPrimaryIdentityDomain(this.bean.getPrimaryIdentityDomain());
            }

            if ((excludeProps == null || !excludeProps.contains("RCMHistoricalDataBufferLimit")) && this.bean.isRCMHistoricalDataBufferLimitSet()) {
               copy.setRCMHistoricalDataBufferLimit(this.bean.getRCMHistoricalDataBufferLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("Realm")) && this.bean.isRealmSet()) {
               copy._unSet(copy, 18);
               copy.setRealmAsString(this.bean.getRealmAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceDeploymentPlanPath")) && this.bean.isResourceDeploymentPlanPathSet()) {
               copy.setResourceDeploymentPlanPath(this.bean.getResourceDeploymentPlanPath());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceGroups")) && this.bean.isResourceGroupsSet() && !copy._isSet(11)) {
               ResourceGroupMBean[] oldResourceGroups = this.bean.getResourceGroups();
               ResourceGroupMBean[] newResourceGroups = new ResourceGroupMBean[oldResourceGroups.length];

               for(i = 0; i < newResourceGroups.length; ++i) {
                  newResourceGroups[i] = (ResourceGroupMBean)((ResourceGroupMBean)this.createCopy((AbstractDescriptorBean)oldResourceGroups[i], includeObsolete));
               }

               copy.setResourceGroups(newResourceGroups);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceManager")) && this.bean.isResourceManagerSet() && !copy._isSet(30)) {
               Object o = this.bean.getResourceManager();
               copy.setResourceManager((ResourceManagerMBean)null);
               copy.setResourceManager(o == null ? null : (ResourceManagerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceManagerRef")) && this.bean.isResourceManagerRefSet()) {
               copy._unSet(copy, 31);
               copy.setResourceManagerRefAsString(this.bean.getResourceManagerRefAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("SelfTuning")) && this.bean.isSelfTuningSet() && !copy._isSet(17)) {
               Object o = this.bean.getSelfTuning();
               copy.setSelfTuning((SelfTuningMBean)null);
               copy.setSelfTuning(o == null ? null : (SelfTuningMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StartupTimeout")) && this.bean.isStartupTimeoutSet()) {
               copy.setStartupTimeout(this.bean.getStartupTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemFileSystem")) && this.bean.isSystemFileSystemSet() && !copy._isSet(34)) {
               Object o = this.bean.getSystemFileSystem();
               copy.setSystemFileSystem((PartitionFileSystemMBean)null);
               copy.setSystemFileSystem(o == null ? null : (PartitionFileSystemMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UploadDirectoryName")) && this.bean.isUploadDirectoryNameSet()) {
               copy.setUploadDirectoryName(this.bean.getUploadDirectoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("UserFileSystem")) && this.bean.isUserFileSystemSet() && !copy._isSet(35)) {
               Object o = this.bean.getUserFileSystem();
               copy.setUserFileSystem((PartitionUserFileSystemMBean)null);
               copy.setUserFileSystem(o == null ? null : (PartitionUserFileSystemMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebService")) && this.bean.isWebServiceSet() && !copy._isSet(55)) {
               Object o = this.bean.getWebService();
               copy.setWebService((WebServiceMBean)null);
               copy.setWebService(o == null ? null : (WebServiceMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EagerTrackingOfResourceMetricsEnabled")) && this.bean.isEagerTrackingOfResourceMetricsEnabledSet()) {
               copy.setEagerTrackingOfResourceMetricsEnabled(this.bean.isEagerTrackingOfResourceMetricsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreSessionsDuringShutdown")) && this.bean.isIgnoreSessionsDuringShutdownSet()) {
               copy.setIgnoreSessionsDuringShutdown(this.bean.isIgnoreSessionsDuringShutdown());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelDeployApplicationModules")) && this.bean.isParallelDeployApplicationModulesSet()) {
               copy.setParallelDeployApplicationModules(this.bean.isParallelDeployApplicationModules());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelDeployApplications")) && this.bean.isParallelDeployApplicationsSet()) {
               copy.setParallelDeployApplications(this.bean.isParallelDeployApplications());
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
         this.inferSubTree(this.bean.getAdminVirtualTarget(), clazz, annotation);
         this.inferSubTree(this.bean.getAvailableTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherencePartitionCacheConfigs(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSourceForJobScheduler(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSourcePartition(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJNDIProviderOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getInternalAppDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getInternalLibraries(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCSystemResourceOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSSystemResourceOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getJTAPartition(), clazz, annotation);
         this.inferSubTree(this.bean.getMailSessionOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServiceTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServiceTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactoryTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getPartitionLog(), clazz, annotation);
         this.inferSubTree(this.bean.getPartitionWorkManager(), clazz, annotation);
         this.inferSubTree(this.bean.getPartitionWorkManagerRef(), clazz, annotation);
         this.inferSubTree(this.bean.getRealm(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceGroups(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceManager(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceManagerRef(), clazz, annotation);
         this.inferSubTree(this.bean.getSelfTuning(), clazz, annotation);
         this.inferSubTree(this.bean.getSystemFileSystem(), clazz, annotation);
         this.inferSubTree(this.bean.getUserFileSystem(), clazz, annotation);
         this.inferSubTree(this.bean.getWebService(), clazz, annotation);
      }
   }
}
