package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
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
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.CoherenceClusterSystemResource;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceClusterSystemResourceMBeanImpl extends SystemResourceMBeanImpl implements CoherenceClusterSystemResourceMBean, Serializable {
   private String[] _ClusterHosts;
   private CoherenceCacheConfigMBean[] _CoherenceCacheConfigs;
   private WeblogicCoherenceBean _CoherenceClusterResource;
   private String _CustomClusterConfigurationFileName;
   private long _CustomConfigFileLastUpdatedTime;
   private String _DescriptorFileName;
   private boolean _DynamicallyCreated;
   private int _FederationRemoteClusterListenPort;
   private String _FederationRemoteClusterName;
   private String[] _FederationRemoteParticipantHosts;
   private String _FederationTopology;
   private String _Name;
   private String _PersistenceActiveDirectory;
   private String _PersistenceDefaultMode;
   private String _PersistenceSnapshotDirectory;
   private String _PersistenceTrashDirectory;
   private String _ReportGroupFile;
   private DescriptorBean _Resource;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private boolean _UsingCustomClusterConfigurationFile;
   private transient CoherenceClusterSystemResource _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private CoherenceClusterSystemResourceMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(CoherenceClusterSystemResourceMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(CoherenceClusterSystemResourceMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public CoherenceClusterSystemResourceMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(CoherenceClusterSystemResourceMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      CoherenceClusterSystemResourceMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public CoherenceClusterSystemResourceMBeanImpl() {
      try {
         this._customizer = new CoherenceClusterSystemResource(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public CoherenceClusterSystemResourceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new CoherenceClusterSystemResource(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public CoherenceClusterSystemResourceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new CoherenceClusterSystemResource(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDescriptorFileName() {
      if (!this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18)) {
         return this._performMacroSubstitution(this._getDelegateBean().getDescriptorFileName(), this);
      } else {
         if (!this._isSet(18)) {
            try {
               return CoherenceClusterSystemResource.constructDefaultCoherenceSystemFilename(this.getName());
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getDescriptorFileName();
      }
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public boolean isDescriptorFileNameInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isDescriptorFileNameSet() {
      return this._isSet(18);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public DescriptorBean getResource() {
      return this._customizer.getResource();
   }

   public boolean isResourceInherited() {
      return false;
   }

   public boolean isResourceSet() {
      return this._isSet(19);
   }

   public void setDescriptorFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      String _oldVal = this.getDescriptorFileName();
      this._customizer.setDescriptorFileName(param0);
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setResource(DescriptorBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Resource = param0;
   }

   public String getCustomClusterConfigurationFileName() {
      return this._customizer.getCustomClusterConfigurationFileName();
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isCustomClusterConfigurationFileNameInherited() {
      return false;
   }

   public boolean isCustomClusterConfigurationFileNameSet() {
      return this._isSet(20);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setCustomClusterConfigurationFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      String _oldVal = this._CustomClusterConfigurationFileName;
      this._CustomClusterConfigurationFileName = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        CoherenceClusterSystemResourceMBeanImpl.this.addTarget((TargetMBean)value);
                        CoherenceClusterSystemResourceMBeanImpl.this._getHelper().reorderArrayObjects((Object[])CoherenceClusterSystemResourceMBeanImpl.this._Targets, this.getHandbackObject());
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
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
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
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public boolean isUsingCustomClusterConfigurationFile() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().isUsingCustomClusterConfigurationFile() : this._customizer.isUsingCustomClusterConfigurationFile();
   }

   public boolean isUsingCustomClusterConfigurationFileInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isUsingCustomClusterConfigurationFileSet() {
      return this._isSet(21);
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));
      DomainTargetHelper.validateTargets(this);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return CoherenceClusterSystemResourceMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(10);
      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public void addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void importCustomClusterConfigurationFile(String param0) throws ManagementException {
      this._customizer.importCustomClusterConfigurationFile(param0);
   }

   public WeblogicCoherenceBean getCoherenceClusterResource() {
      return this._customizer.getCoherenceClusterResource();
   }

   public boolean isCoherenceClusterResourceInherited() {
      return false;
   }

   public boolean isCoherenceClusterResourceSet() {
      return this._isSet(22);
   }

   public void removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            }

            if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setCoherenceClusterResource(WeblogicCoherenceBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._CoherenceClusterResource = param0;
   }

   public void setUsingCustomClusterConfigurationFile(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this.isUsingCustomClusterConfigurationFile();
      this._customizer.setUsingCustomClusterConfigurationFile(param0);
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public void addCoherenceCacheConfig(CoherenceCacheConfigMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         CoherenceCacheConfigMBean[] _new;
         if (this._isSet(23)) {
            _new = (CoherenceCacheConfigMBean[])((CoherenceCacheConfigMBean[])this._getHelper()._extendArray(this.getCoherenceCacheConfigs(), CoherenceCacheConfigMBean.class, param0));
         } else {
            _new = new CoherenceCacheConfigMBean[]{param0};
         }

         try {
            this.setCoherenceCacheConfigs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceCacheConfigMBean[] getCoherenceCacheConfigs() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getCoherenceCacheConfigs() : this._CoherenceCacheConfigs;
   }

   public boolean isCoherenceCacheConfigsInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isCoherenceCacheConfigsSet() {
      return this._isSet(23);
   }

   public void removeCoherenceCacheConfig(CoherenceCacheConfigMBean param0) {
      this.destroyCoherenceCacheConfig(param0);
   }

   public void setCoherenceCacheConfigs(CoherenceCacheConfigMBean[] param0) throws InvalidAttributeValueException {
      CoherenceCacheConfigMBean[] param0 = param0 == null ? new CoherenceCacheConfigMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(23);
      CoherenceCacheConfigMBean[] _oldVal = this._CoherenceCacheConfigs;
      this._CoherenceCacheConfigs = (CoherenceCacheConfigMBean[])param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public CoherenceCacheConfigMBean createCoherenceCacheConfig(String param0) {
      CoherenceCacheConfigMBeanImpl lookup = (CoherenceCacheConfigMBeanImpl)this.lookupCoherenceCacheConfig(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceCacheConfigMBeanImpl _val = new CoherenceCacheConfigMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceCacheConfig(_val);
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

   public void destroyCoherenceCacheConfig(CoherenceCacheConfigMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 23);
         CoherenceCacheConfigMBean[] _old = this.getCoherenceCacheConfigs();
         CoherenceCacheConfigMBean[] _new = (CoherenceCacheConfigMBean[])((CoherenceCacheConfigMBean[])this._getHelper()._removeElement(_old, CoherenceCacheConfigMBean.class, param0));
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
                  CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var6.next();
                  CoherenceCacheConfigMBeanImpl childImpl = (CoherenceCacheConfigMBeanImpl)_child;
                  CoherenceCacheConfigMBeanImpl lookup = (CoherenceCacheConfigMBeanImpl)source.lookupCoherenceCacheConfig(childImpl.getName());
                  if (lookup != null) {
                     source.destroyCoherenceCacheConfig(lookup);
                  }
               }

               this.setCoherenceCacheConfigs(_new);
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

   public CoherenceCacheConfigMBean lookupCoherenceCacheConfig(String param0) {
      Object[] aary = (Object[])this._CoherenceCacheConfigs;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceCacheConfigMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceCacheConfigMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getReportGroupFile() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getReportGroupFile(), this) : this._ReportGroupFile;
   }

   public boolean isReportGroupFileInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isReportGroupFileSet() {
      return this._isSet(24);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setReportGroupFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      String _oldVal = this._ReportGroupFile;
      this._ReportGroupFile = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getClusterHosts() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getClusterHosts() : this._customizer.getClusterHosts();
   }

   public boolean isClusterHostsInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isClusterHostsSet() {
      return this._isSet(25);
   }

   public void setClusterHosts(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ClusterHosts = param0;
   }

   public long getCustomConfigFileLastUpdatedTime() {
      return this._customizer.getCustomConfigFileLastUpdatedTime();
   }

   public boolean isCustomConfigFileLastUpdatedTimeInherited() {
      return false;
   }

   public boolean isCustomConfigFileLastUpdatedTimeSet() {
      return this._isSet(26);
   }

   public void setCustomConfigFileLastUpdatedTime(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      long _oldVal = this._CustomConfigFileLastUpdatedTime;
      this._CustomConfigFileLastUpdatedTime = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var6.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPersistenceDefaultMode() {
      return this._customizer.getPersistenceDefaultMode();
   }

   public boolean isPersistenceDefaultModeInherited() {
      return false;
   }

   public boolean isPersistenceDefaultModeSet() {
      return this._isSet(27);
   }

   public void setPersistenceDefaultMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      String _oldVal = this._PersistenceDefaultMode;
      this._PersistenceDefaultMode = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPersistenceActiveDirectory() {
      return this._customizer.getPersistenceActiveDirectory();
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

   public boolean isPersistenceActiveDirectoryInherited() {
      return false;
   }

   public boolean isPersistenceActiveDirectorySet() {
      return this._isSet(28);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setPersistenceActiveDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      String _oldVal = this._PersistenceActiveDirectory;
      this._PersistenceActiveDirectory = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPersistenceSnapshotDirectory() {
      return this._customizer.getPersistenceSnapshotDirectory();
   }

   public boolean isPersistenceSnapshotDirectoryInherited() {
      return false;
   }

   public boolean isPersistenceSnapshotDirectorySet() {
      return this._isSet(29);
   }

   public void setPersistenceSnapshotDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      String _oldVal = this._PersistenceSnapshotDirectory;
      this._PersistenceSnapshotDirectory = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPersistenceTrashDirectory() {
      return this._customizer.getPersistenceTrashDirectory();
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isPersistenceTrashDirectoryInherited() {
      return false;
   }

   public boolean isPersistenceTrashDirectorySet() {
      return this._isSet(30);
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setPersistenceTrashDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      String _oldVal = this._PersistenceTrashDirectory;
      this._PersistenceTrashDirectory = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFederationTopology() {
      return this._customizer.getFederationTopology();
   }

   public boolean isFederationTopologyInherited() {
      return false;
   }

   public boolean isFederationTopologySet() {
      return this._isSet(31);
   }

   public void setFederationTopology(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      String _oldVal = this._FederationTopology;
      this._FederationTopology = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

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
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
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

   public String[] getFederationRemoteParticipantHosts() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getFederationRemoteParticipantHosts() : this._customizer.getFederationRemoteParticipantHosts();
   }

   public boolean isFederationRemoteParticipantHostsInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isFederationRemoteParticipantHostsSet() {
      return this._isSet(32);
   }

   public void setFederationRemoteParticipantHosts(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      String[] _oldVal = this._FederationRemoteParticipantHosts;
      this._FederationRemoteParticipantHosts = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFederationRemoteClusterName() {
      return this._customizer.getFederationRemoteClusterName();
   }

   public boolean isFederationRemoteClusterNameInherited() {
      return false;
   }

   public boolean isFederationRemoteClusterNameSet() {
      return this._isSet(33);
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

   public void setFederationRemoteClusterName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      String _oldVal = this._FederationRemoteClusterName;
      this._FederationRemoteClusterName = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFederationRemoteClusterListenPort() {
      return this._customizer.getFederationRemoteClusterListenPort();
   }

   public boolean isFederationRemoteClusterListenPortInherited() {
      return false;
   }

   public boolean isFederationRemoteClusterListenPortSet() {
      return this._isSet(34);
   }

   public void setFederationRemoteClusterListenPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      int _oldVal = this._FederationRemoteClusterListenPort;
      this._FederationRemoteClusterListenPort = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceClusterSystemResourceMBeanImpl source = (CoherenceClusterSystemResourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 25;
      }

      try {
         switch (idx) {
            case 25:
               this._ClusterHosts = new String[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._CoherenceCacheConfigs = new CoherenceCacheConfigMBean[0];
               if (initOne) {
                  break;
               }
            case 22:
               this._CoherenceClusterResource = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._CustomClusterConfigurationFileName = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._CustomConfigFileLastUpdatedTime = 0L;
               if (initOne) {
                  break;
               }
            case 18:
               this._customizer.setDescriptorFileName((String)null);
               if (initOne) {
                  break;
               }
            case 34:
               this._FederationRemoteClusterListenPort = 0;
               if (initOne) {
                  break;
               }
            case 33:
               this._FederationRemoteClusterName = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._FederationRemoteParticipantHosts = new String[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._FederationTopology = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 28:
               this._PersistenceActiveDirectory = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._PersistenceDefaultMode = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._PersistenceSnapshotDirectory = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._PersistenceTrashDirectory = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._ReportGroupFile = "em/metadata/reports/coherence/report-group.xml";
               if (initOne) {
                  break;
               }
            case 19:
               this._Resource = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._customizer.setUsingCustomClusterConfigurationFile(false);
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
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
      return "CoherenceClusterSystemResource";
   }

   public void putValue(String name, Object v) {
      String[] oldVal;
      if (name.equals("ClusterHosts")) {
         oldVal = this._ClusterHosts;
         this._ClusterHosts = (String[])((String[])v);
         this._postSet(25, oldVal, this._ClusterHosts);
      } else if (name.equals("CoherenceCacheConfigs")) {
         CoherenceCacheConfigMBean[] oldVal = this._CoherenceCacheConfigs;
         this._CoherenceCacheConfigs = (CoherenceCacheConfigMBean[])((CoherenceCacheConfigMBean[])v);
         this._postSet(23, oldVal, this._CoherenceCacheConfigs);
      } else if (name.equals("CoherenceClusterResource")) {
         WeblogicCoherenceBean oldVal = this._CoherenceClusterResource;
         this._CoherenceClusterResource = (WeblogicCoherenceBean)v;
         this._postSet(22, oldVal, this._CoherenceClusterResource);
      } else {
         String oldVal;
         if (name.equals("CustomClusterConfigurationFileName")) {
            oldVal = this._CustomClusterConfigurationFileName;
            this._CustomClusterConfigurationFileName = (String)v;
            this._postSet(20, oldVal, this._CustomClusterConfigurationFileName);
         } else if (name.equals("CustomConfigFileLastUpdatedTime")) {
            long oldVal = this._CustomConfigFileLastUpdatedTime;
            this._CustomConfigFileLastUpdatedTime = (Long)v;
            this._postSet(26, oldVal, this._CustomConfigFileLastUpdatedTime);
         } else if (name.equals("DescriptorFileName")) {
            oldVal = this._DescriptorFileName;
            this._DescriptorFileName = (String)v;
            this._postSet(18, oldVal, this._DescriptorFileName);
         } else {
            boolean oldVal;
            if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("FederationRemoteClusterListenPort")) {
               int oldVal = this._FederationRemoteClusterListenPort;
               this._FederationRemoteClusterListenPort = (Integer)v;
               this._postSet(34, oldVal, this._FederationRemoteClusterListenPort);
            } else if (name.equals("FederationRemoteClusterName")) {
               oldVal = this._FederationRemoteClusterName;
               this._FederationRemoteClusterName = (String)v;
               this._postSet(33, oldVal, this._FederationRemoteClusterName);
            } else if (name.equals("FederationRemoteParticipantHosts")) {
               oldVal = this._FederationRemoteParticipantHosts;
               this._FederationRemoteParticipantHosts = (String[])((String[])v);
               this._postSet(32, oldVal, this._FederationRemoteParticipantHosts);
            } else if (name.equals("FederationTopology")) {
               oldVal = this._FederationTopology;
               this._FederationTopology = (String)v;
               this._postSet(31, oldVal, this._FederationTopology);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("PersistenceActiveDirectory")) {
               oldVal = this._PersistenceActiveDirectory;
               this._PersistenceActiveDirectory = (String)v;
               this._postSet(28, oldVal, this._PersistenceActiveDirectory);
            } else if (name.equals("PersistenceDefaultMode")) {
               oldVal = this._PersistenceDefaultMode;
               this._PersistenceDefaultMode = (String)v;
               this._postSet(27, oldVal, this._PersistenceDefaultMode);
            } else if (name.equals("PersistenceSnapshotDirectory")) {
               oldVal = this._PersistenceSnapshotDirectory;
               this._PersistenceSnapshotDirectory = (String)v;
               this._postSet(29, oldVal, this._PersistenceSnapshotDirectory);
            } else if (name.equals("PersistenceTrashDirectory")) {
               oldVal = this._PersistenceTrashDirectory;
               this._PersistenceTrashDirectory = (String)v;
               this._postSet(30, oldVal, this._PersistenceTrashDirectory);
            } else if (name.equals("ReportGroupFile")) {
               oldVal = this._ReportGroupFile;
               this._ReportGroupFile = (String)v;
               this._postSet(24, oldVal, this._ReportGroupFile);
            } else if (name.equals("Resource")) {
               DescriptorBean oldVal = this._Resource;
               this._Resource = (DescriptorBean)v;
               this._postSet(19, oldVal, this._Resource);
            } else if (name.equals("Tags")) {
               oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("Targets")) {
               TargetMBean[] oldVal = this._Targets;
               this._Targets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(10, oldVal, this._Targets);
            } else if (name.equals("UsingCustomClusterConfigurationFile")) {
               oldVal = this._UsingCustomClusterConfigurationFile;
               this._UsingCustomClusterConfigurationFile = (Boolean)v;
               this._postSet(21, oldVal, this._UsingCustomClusterConfigurationFile);
            } else if (name.equals("customizer")) {
               CoherenceClusterSystemResource oldVal = this._customizer;
               this._customizer = (CoherenceClusterSystemResource)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ClusterHosts")) {
         return this._ClusterHosts;
      } else if (name.equals("CoherenceCacheConfigs")) {
         return this._CoherenceCacheConfigs;
      } else if (name.equals("CoherenceClusterResource")) {
         return this._CoherenceClusterResource;
      } else if (name.equals("CustomClusterConfigurationFileName")) {
         return this._CustomClusterConfigurationFileName;
      } else if (name.equals("CustomConfigFileLastUpdatedTime")) {
         return new Long(this._CustomConfigFileLastUpdatedTime);
      } else if (name.equals("DescriptorFileName")) {
         return this._DescriptorFileName;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FederationRemoteClusterListenPort")) {
         return new Integer(this._FederationRemoteClusterListenPort);
      } else if (name.equals("FederationRemoteClusterName")) {
         return this._FederationRemoteClusterName;
      } else if (name.equals("FederationRemoteParticipantHosts")) {
         return this._FederationRemoteParticipantHosts;
      } else if (name.equals("FederationTopology")) {
         return this._FederationTopology;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PersistenceActiveDirectory")) {
         return this._PersistenceActiveDirectory;
      } else if (name.equals("PersistenceDefaultMode")) {
         return this._PersistenceDefaultMode;
      } else if (name.equals("PersistenceSnapshotDirectory")) {
         return this._PersistenceSnapshotDirectory;
      } else if (name.equals("PersistenceTrashDirectory")) {
         return this._PersistenceTrashDirectory;
      } else if (name.equals("ReportGroupFile")) {
         return this._ReportGroupFile;
      } else if (name.equals("Resource")) {
         return this._Resource;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("UsingCustomClusterConfigurationFile")) {
         return new Boolean(this._UsingCustomClusterConfigurationFile);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SystemResourceMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 21:
            case 23:
            case 25:
            case 29:
            case 31:
            case 32:
            case 33:
            case 35:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("resource")) {
                  return 19;
               }
               break;
            case 12:
               if (s.equals("cluster-host")) {
                  return 25;
               }
               break;
            case 17:
               if (s.equals("report-group-file")) {
                  return 24;
               }
               break;
            case 19:
               if (s.equals("federation-topology")) {
                  return 31;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("descriptor-file-name")) {
                  return 18;
               }
               break;
            case 22:
               if (s.equals("coherence-cache-config")) {
                  return 23;
               }
               break;
            case 24:
               if (s.equals("persistence-default-mode")) {
                  return 27;
               }
               break;
            case 26:
               if (s.equals("coherence-cluster-resource")) {
                  return 22;
               }
               break;
            case 27:
               if (s.equals("persistence-trash-directory")) {
                  return 30;
               }
               break;
            case 28:
               if (s.equals("persistence-active-directory")) {
                  return 28;
               }
               break;
            case 30:
               if (s.equals("federation-remote-cluster-name")) {
                  return 33;
               }

               if (s.equals("persistence-snapshot-directory")) {
                  return 29;
               }
               break;
            case 34:
               if (s.equals("federation-remote-participant-host")) {
                  return 32;
               }
               break;
            case 36:
               if (s.equals("custom-config-file-last-updated-time")) {
                  return 26;
               }
               break;
            case 37:
               if (s.equals("federation-remote-cluster-listen-port")) {
                  return 34;
               }
               break;
            case 38:
               if (s.equals("custom-cluster-configuration-file-name")) {
                  return 20;
               }
               break;
            case 39:
               if (s.equals("using-custom-cluster-configuration-file")) {
                  return 21;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 14:
               return new SubDeploymentMBeanImpl.SchemaHelper2();
            case 23:
               return new CoherenceCacheConfigMBeanImpl.SchemaHelper2();
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
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 18:
               return "descriptor-file-name";
            case 19:
               return "resource";
            case 20:
               return "custom-cluster-configuration-file-name";
            case 21:
               return "using-custom-cluster-configuration-file";
            case 22:
               return "coherence-cluster-resource";
            case 23:
               return "coherence-cache-config";
            case 24:
               return "report-group-file";
            case 25:
               return "cluster-host";
            case 26:
               return "custom-config-file-last-updated-time";
            case 27:
               return "persistence-default-mode";
            case 28:
               return "persistence-active-directory";
            case 29:
               return "persistence-snapshot-directory";
            case 30:
               return "persistence-trash-directory";
            case 31:
               return "federation-topology";
            case 32:
               return "federation-remote-participant-host";
            case 33:
               return "federation-remote-cluster-name";
            case 34:
               return "federation-remote-cluster-listen-port";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 14:
               return true;
            case 23:
               return true;
            case 25:
               return true;
            case 32:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 14:
               return true;
            case 23:
               return true;
            default:
               return super.isBean(propIndex);
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

   protected static class Helper extends SystemResourceMBeanImpl.Helper {
      private CoherenceClusterSystemResourceMBeanImpl bean;

      protected Helper(CoherenceClusterSystemResourceMBeanImpl bean) {
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
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 18:
               return "DescriptorFileName";
            case 19:
               return "Resource";
            case 20:
               return "CustomClusterConfigurationFileName";
            case 21:
               return "UsingCustomClusterConfigurationFile";
            case 22:
               return "CoherenceClusterResource";
            case 23:
               return "CoherenceCacheConfigs";
            case 24:
               return "ReportGroupFile";
            case 25:
               return "ClusterHosts";
            case 26:
               return "CustomConfigFileLastUpdatedTime";
            case 27:
               return "PersistenceDefaultMode";
            case 28:
               return "PersistenceActiveDirectory";
            case 29:
               return "PersistenceSnapshotDirectory";
            case 30:
               return "PersistenceTrashDirectory";
            case 31:
               return "FederationTopology";
            case 32:
               return "FederationRemoteParticipantHosts";
            case 33:
               return "FederationRemoteClusterName";
            case 34:
               return "FederationRemoteClusterListenPort";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClusterHosts")) {
            return 25;
         } else if (propName.equals("CoherenceCacheConfigs")) {
            return 23;
         } else if (propName.equals("CoherenceClusterResource")) {
            return 22;
         } else if (propName.equals("CustomClusterConfigurationFileName")) {
            return 20;
         } else if (propName.equals("CustomConfigFileLastUpdatedTime")) {
            return 26;
         } else if (propName.equals("DescriptorFileName")) {
            return 18;
         } else if (propName.equals("FederationRemoteClusterListenPort")) {
            return 34;
         } else if (propName.equals("FederationRemoteClusterName")) {
            return 33;
         } else if (propName.equals("FederationRemoteParticipantHosts")) {
            return 32;
         } else if (propName.equals("FederationTopology")) {
            return 31;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PersistenceActiveDirectory")) {
            return 28;
         } else if (propName.equals("PersistenceDefaultMode")) {
            return 27;
         } else if (propName.equals("PersistenceSnapshotDirectory")) {
            return 29;
         } else if (propName.equals("PersistenceTrashDirectory")) {
            return 30;
         } else if (propName.equals("ReportGroupFile")) {
            return 24;
         } else if (propName.equals("Resource")) {
            return 19;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("UsingCustomClusterConfigurationFile") ? 21 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherenceCacheConfigs()));
         iterators.add(new ArrayIterator(this.bean.getSubDeployments()));
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
            if (this.bean.isClusterHostsSet()) {
               buf.append("ClusterHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getClusterHosts())));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getCoherenceCacheConfigs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceCacheConfigs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCoherenceClusterResourceSet()) {
               buf.append("CoherenceClusterResource");
               buf.append(String.valueOf(this.bean.getCoherenceClusterResource()));
            }

            if (this.bean.isCustomClusterConfigurationFileNameSet()) {
               buf.append("CustomClusterConfigurationFileName");
               buf.append(String.valueOf(this.bean.getCustomClusterConfigurationFileName()));
            }

            if (this.bean.isCustomConfigFileLastUpdatedTimeSet()) {
               buf.append("CustomConfigFileLastUpdatedTime");
               buf.append(String.valueOf(this.bean.getCustomConfigFileLastUpdatedTime()));
            }

            if (this.bean.isDescriptorFileNameSet()) {
               buf.append("DescriptorFileName");
               buf.append(String.valueOf(this.bean.getDescriptorFileName()));
            }

            if (this.bean.isFederationRemoteClusterListenPortSet()) {
               buf.append("FederationRemoteClusterListenPort");
               buf.append(String.valueOf(this.bean.getFederationRemoteClusterListenPort()));
            }

            if (this.bean.isFederationRemoteClusterNameSet()) {
               buf.append("FederationRemoteClusterName");
               buf.append(String.valueOf(this.bean.getFederationRemoteClusterName()));
            }

            if (this.bean.isFederationRemoteParticipantHostsSet()) {
               buf.append("FederationRemoteParticipantHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getFederationRemoteParticipantHosts())));
            }

            if (this.bean.isFederationTopologySet()) {
               buf.append("FederationTopology");
               buf.append(String.valueOf(this.bean.getFederationTopology()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPersistenceActiveDirectorySet()) {
               buf.append("PersistenceActiveDirectory");
               buf.append(String.valueOf(this.bean.getPersistenceActiveDirectory()));
            }

            if (this.bean.isPersistenceDefaultModeSet()) {
               buf.append("PersistenceDefaultMode");
               buf.append(String.valueOf(this.bean.getPersistenceDefaultMode()));
            }

            if (this.bean.isPersistenceSnapshotDirectorySet()) {
               buf.append("PersistenceSnapshotDirectory");
               buf.append(String.valueOf(this.bean.getPersistenceSnapshotDirectory()));
            }

            if (this.bean.isPersistenceTrashDirectorySet()) {
               buf.append("PersistenceTrashDirectory");
               buf.append(String.valueOf(this.bean.getPersistenceTrashDirectory()));
            }

            if (this.bean.isReportGroupFileSet()) {
               buf.append("ReportGroupFile");
               buf.append(String.valueOf(this.bean.getReportGroupFile()));
            }

            if (this.bean.isResourceSet()) {
               buf.append("Resource");
               buf.append(String.valueOf(this.bean.getResource()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isUsingCustomClusterConfigurationFileSet()) {
               buf.append("UsingCustomClusterConfigurationFile");
               buf.append(String.valueOf(this.bean.isUsingCustomClusterConfigurationFile()));
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
            CoherenceClusterSystemResourceMBeanImpl otherTyped = (CoherenceClusterSystemResourceMBeanImpl)other;
            this.computeChildDiff("CoherenceCacheConfigs", this.bean.getCoherenceCacheConfigs(), otherTyped.getCoherenceCacheConfigs(), false);
            this.computeDiff("CustomClusterConfigurationFileName", this.bean.getCustomClusterConfigurationFileName(), otherTyped.getCustomClusterConfigurationFileName(), false);
            this.computeDiff("CustomConfigFileLastUpdatedTime", this.bean.getCustomConfigFileLastUpdatedTime(), otherTyped.getCustomConfigFileLastUpdatedTime(), false);
            this.computeDiff("DescriptorFileName", this.bean.getDescriptorFileName(), otherTyped.getDescriptorFileName(), false);
            this.computeDiff("FederationRemoteClusterListenPort", this.bean.getFederationRemoteClusterListenPort(), otherTyped.getFederationRemoteClusterListenPort(), false);
            this.computeDiff("FederationRemoteClusterName", this.bean.getFederationRemoteClusterName(), otherTyped.getFederationRemoteClusterName(), false);
            this.computeDiff("FederationRemoteParticipantHosts", this.bean.getFederationRemoteParticipantHosts(), otherTyped.getFederationRemoteParticipantHosts(), false);
            this.computeDiff("FederationTopology", this.bean.getFederationTopology(), otherTyped.getFederationTopology(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PersistenceActiveDirectory", this.bean.getPersistenceActiveDirectory(), otherTyped.getPersistenceActiveDirectory(), false);
            this.computeDiff("PersistenceDefaultMode", this.bean.getPersistenceDefaultMode(), otherTyped.getPersistenceDefaultMode(), false);
            this.computeDiff("PersistenceSnapshotDirectory", this.bean.getPersistenceSnapshotDirectory(), otherTyped.getPersistenceSnapshotDirectory(), false);
            this.computeDiff("PersistenceTrashDirectory", this.bean.getPersistenceTrashDirectory(), otherTyped.getPersistenceTrashDirectory(), false);
            this.computeDiff("ReportGroupFile", this.bean.getReportGroupFile(), otherTyped.getReportGroupFile(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("UsingCustomClusterConfigurationFile", this.bean.isUsingCustomClusterConfigurationFile(), otherTyped.isUsingCustomClusterConfigurationFile(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceClusterSystemResourceMBeanImpl original = (CoherenceClusterSystemResourceMBeanImpl)event.getSourceBean();
            CoherenceClusterSystemResourceMBeanImpl proposed = (CoherenceClusterSystemResourceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ClusterHosts")) {
                  if (prop.equals("CoherenceCacheConfigs")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addCoherenceCacheConfig((CoherenceCacheConfigMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeCoherenceCacheConfig((CoherenceCacheConfigMBean)update.getRemovedObject());
                     }

                     if (original.getCoherenceCacheConfigs() == null || original.getCoherenceCacheConfigs().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 23);
                     }
                  } else if (!prop.equals("CoherenceClusterResource")) {
                     if (prop.equals("CustomClusterConfigurationFileName")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else if (prop.equals("CustomConfigFileLastUpdatedTime")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("DescriptorFileName")) {
                        original.setDescriptorFileName(proposed.getDescriptorFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 18);
                     } else if (prop.equals("FederationRemoteClusterListenPort")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 34);
                     } else if (prop.equals("FederationRemoteClusterName")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (prop.equals("FederationRemoteParticipantHosts")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("FederationTopology")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("PersistenceActiveDirectory")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 28);
                     } else if (prop.equals("PersistenceDefaultMode")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 27);
                     } else if (prop.equals("PersistenceSnapshotDirectory")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("PersistenceTrashDirectory")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (prop.equals("ReportGroupFile")) {
                        original.setReportGroupFile(proposed.getReportGroupFile());
                        original._conditionalUnset(update.isUnsetUpdate(), 24);
                     } else if (!prop.equals("Resource")) {
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
                        } else if (prop.equals("Targets")) {
                           original.setTargetsAsString(proposed.getTargetsAsString());
                           original._conditionalUnset(update.isUnsetUpdate(), 10);
                        } else if (!prop.equals("DynamicallyCreated")) {
                           if (prop.equals("UsingCustomClusterConfigurationFile")) {
                              original.setUsingCustomClusterConfigurationFile(proposed.isUsingCustomClusterConfigurationFile());
                              original._conditionalUnset(update.isUnsetUpdate(), 21);
                           } else {
                              super.applyPropertyUpdate(event, update);
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
            CoherenceClusterSystemResourceMBeanImpl copy = (CoherenceClusterSystemResourceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceCacheConfigs")) && this.bean.isCoherenceCacheConfigsSet() && !copy._isSet(23)) {
               CoherenceCacheConfigMBean[] oldCoherenceCacheConfigs = this.bean.getCoherenceCacheConfigs();
               CoherenceCacheConfigMBean[] newCoherenceCacheConfigs = new CoherenceCacheConfigMBean[oldCoherenceCacheConfigs.length];

               for(int i = 0; i < newCoherenceCacheConfigs.length; ++i) {
                  newCoherenceCacheConfigs[i] = (CoherenceCacheConfigMBean)((CoherenceCacheConfigMBean)this.createCopy((AbstractDescriptorBean)oldCoherenceCacheConfigs[i], includeObsolete));
               }

               copy.setCoherenceCacheConfigs(newCoherenceCacheConfigs);
            }

            if ((excludeProps == null || !excludeProps.contains("CustomClusterConfigurationFileName")) && this.bean.isCustomClusterConfigurationFileNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("CustomConfigFileLastUpdatedTime")) && this.bean.isCustomConfigFileLastUpdatedTimeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("DescriptorFileName")) && this.bean.isDescriptorFileNameSet()) {
               copy.setDescriptorFileName(this.bean.getDescriptorFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("FederationRemoteClusterListenPort")) && this.bean.isFederationRemoteClusterListenPortSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("FederationRemoteClusterName")) && this.bean.isFederationRemoteClusterNameSet()) {
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("FederationRemoteParticipantHosts")) && this.bean.isFederationRemoteParticipantHostsSet()) {
               o = this.bean.getFederationRemoteParticipantHosts();
               copy.setFederationRemoteParticipantHosts(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("FederationTopology")) && this.bean.isFederationTopologySet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceActiveDirectory")) && this.bean.isPersistenceActiveDirectorySet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceDefaultMode")) && this.bean.isPersistenceDefaultModeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceSnapshotDirectory")) && this.bean.isPersistenceSnapshotDirectorySet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceTrashDirectory")) && this.bean.isPersistenceTrashDirectorySet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ReportGroupFile")) && this.bean.isReportGroupFileSet()) {
               copy.setReportGroupFile(this.bean.getReportGroupFile());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("UsingCustomClusterConfigurationFile")) && this.bean.isUsingCustomClusterConfigurationFileSet()) {
               copy.setUsingCustomClusterConfigurationFile(this.bean.isUsingCustomClusterConfigurationFile());
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
         this.inferSubTree(this.bean.getCoherenceCacheConfigs(), clazz, annotation);
         this.inferSubTree(this.bean.getResource(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
