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
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.LifecycleManagerConfig;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class LifecycleManagerConfigMBeanImpl extends ConfigurationMBeanImpl implements LifecycleManagerConfigMBean, Serializable {
   private long _ConfigFileLockTimeout;
   private LifecycleManagerEndPointMBean[] _ConfiguredEndPoints;
   private String _DataSourceName;
   private String _DeploymentType;
   private boolean _DynamicallyCreated;
   private boolean _Enabled;
   private LifecycleManagerEndPointMBean[] _EndPoints;
   private int _LCMInitiatedConnectTimeout;
   private int _LCMInitiatedConnectTimeoutForElasticity;
   private int _LCMInitiatedReadTimeout;
   private int _LCMInitiatedReadTimeoutForElasticity;
   private String _Name;
   private boolean _OutOfBandEnabled;
   private int _PeriodicSyncInterval;
   private String _PersistenceType;
   private long _PropagationActivateTimeout;
   private long _ServerReadyTimeout;
   private long _ServerRuntimeTimeout;
   private String[] _Tags;
   private TargetMBean _Target;
   private transient LifecycleManagerConfig _customizer;
   private static SchemaHelper2 _schemaHelper;

   public LifecycleManagerConfigMBeanImpl() {
      try {
         this._customizer = new LifecycleManagerConfig(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public LifecycleManagerConfigMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new LifecycleManagerConfig(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public LifecycleManagerConfigMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new LifecycleManagerConfig(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public int getLCMInitiatedConnectTimeout() {
      return this._LCMInitiatedConnectTimeout;
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

   public boolean isLCMInitiatedConnectTimeoutInherited() {
      return false;
   }

   public boolean isLCMInitiatedConnectTimeoutSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setLCMInitiatedConnectTimeout(int param0) {
      int _oldVal = this._LCMInitiatedConnectTimeout;
      this._LCMInitiatedConnectTimeout = param0;
      this._postSet(10, _oldVal, param0);
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

   public int getLCMInitiatedReadTimeout() {
      return this._LCMInitiatedReadTimeout;
   }

   public boolean isLCMInitiatedReadTimeoutInherited() {
      return false;
   }

   public boolean isLCMInitiatedReadTimeoutSet() {
      return this._isSet(11);
   }

   public void setLCMInitiatedReadTimeout(int param0) {
      int _oldVal = this._LCMInitiatedReadTimeout;
      this._LCMInitiatedReadTimeout = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getLCMInitiatedConnectTimeoutForElasticity() {
      return this._LCMInitiatedConnectTimeoutForElasticity;
   }

   public boolean isLCMInitiatedConnectTimeoutForElasticityInherited() {
      return false;
   }

   public boolean isLCMInitiatedConnectTimeoutForElasticitySet() {
      return this._isSet(12);
   }

   public void setLCMInitiatedConnectTimeoutForElasticity(int param0) {
      int _oldVal = this._LCMInitiatedConnectTimeoutForElasticity;
      this._LCMInitiatedConnectTimeoutForElasticity = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getLCMInitiatedReadTimeoutForElasticity() {
      return this._LCMInitiatedReadTimeoutForElasticity;
   }

   public boolean isLCMInitiatedReadTimeoutForElasticityInherited() {
      return false;
   }

   public boolean isLCMInitiatedReadTimeoutForElasticitySet() {
      return this._isSet(13);
   }

   public void setLCMInitiatedReadTimeoutForElasticity(int param0) {
      int _oldVal = this._LCMInitiatedReadTimeoutForElasticity;
      this._LCMInitiatedReadTimeoutForElasticity = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getDeploymentType() {
      return this._DeploymentType;
   }

   public boolean isDeploymentTypeInherited() {
      return false;
   }

   public boolean isDeploymentTypeSet() {
      return this._isSet(14);
   }

   public void setDeploymentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "admin", "cluster"};
      param0 = LegalChecks.checkInEnum("DeploymentType", param0, _set);
      String _oldVal = this._DeploymentType;
      this._DeploymentType = param0;
      this._postSet(14, _oldVal, param0);
   }

   public TargetMBean getTarget() {
      return this._Target;
   }

   public String getTargetAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTarget();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTargetInherited() {
      return false;
   }

   public boolean isTargetSet() {
      return this._isSet(15);
   }

   public void setTargetAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, TargetMBean.class, new ReferenceManager.Resolver(this, 15) {
            public void resolveReference(Object value) {
               try {
                  LifecycleManagerConfigMBeanImpl.this.setTarget((TargetMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         TargetMBean _oldVal = this._Target;
         this._initializeProperty(15);
         this._postSet(15, _oldVal, this._Target);
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 15, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return LifecycleManagerConfigMBeanImpl.this.getTarget();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TargetMBean _oldVal = this._Target;
      this._Target = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getPersistenceType() {
      return this._PersistenceType;
   }

   public boolean isPersistenceTypeInherited() {
      return false;
   }

   public boolean isPersistenceTypeSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setPersistenceType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"XML", "database"};
      param0 = LegalChecks.checkInEnum("PersistenceType", param0, _set);
      String _oldVal = this._PersistenceType;
      this._PersistenceType = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getDataSourceName() {
      return this._DataSourceName;
   }

   public boolean isDataSourceNameInherited() {
      return false;
   }

   public boolean isDataSourceNameSet() {
      return this._isSet(17);
   }

   public void setDataSourceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceName;
      this._DataSourceName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public void addEndPoint(LifecycleManagerEndPointMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         LifecycleManagerEndPointMBean[] _new = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])this._getHelper()._extendArray(this.getEndPoints(), LifecycleManagerEndPointMBean.class, param0));

         try {
            this.setEndPoints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleManagerEndPointMBean[] getEndPoints() {
      return this._customizer.getEndPoints();
   }

   public boolean isEndPointsInherited() {
      return false;
   }

   public boolean isEndPointsSet() {
      return this._isSet(18);
   }

   public void removeEndPoint(LifecycleManagerEndPointMBean param0) {
      LifecycleManagerEndPointMBean[] _old = this.getEndPoints();
      LifecycleManagerEndPointMBean[] _new = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])this._getHelper()._removeElement(_old, LifecycleManagerEndPointMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setEndPoints(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEndPoints(LifecycleManagerEndPointMBean[] param0) throws InvalidAttributeValueException {
      LifecycleManagerEndPointMBean[] param0 = param0 == null ? new LifecycleManagerEndPointMBeanImpl[0] : param0;
      this._EndPoints = (LifecycleManagerEndPointMBean[])param0;
   }

   public void addConfiguredEndPoint(LifecycleManagerEndPointMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         LifecycleManagerEndPointMBean[] _new;
         if (this._isSet(19)) {
            _new = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])this._getHelper()._extendArray(this.getConfiguredEndPoints(), LifecycleManagerEndPointMBean.class, param0));
         } else {
            _new = new LifecycleManagerEndPointMBean[]{param0};
         }

         try {
            this.setConfiguredEndPoints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleManagerEndPointMBean[] getConfiguredEndPoints() {
      return this._ConfiguredEndPoints;
   }

   public boolean isConfiguredEndPointsInherited() {
      return false;
   }

   public boolean isConfiguredEndPointsSet() {
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

   public void removeConfiguredEndPoint(LifecycleManagerEndPointMBean param0) {
      this.destroyConfiguredEndPoint(param0);
   }

   public void setConfiguredEndPoints(LifecycleManagerEndPointMBean[] param0) throws InvalidAttributeValueException {
      LifecycleManagerEndPointMBean[] param0 = param0 == null ? new LifecycleManagerEndPointMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleManagerEndPointMBean[] _oldVal = this._ConfiguredEndPoints;
      this._ConfiguredEndPoints = (LifecycleManagerEndPointMBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public LifecycleManagerEndPointMBean lookupConfiguredEndPoint(String param0) {
      Object[] aary = (Object[])this._ConfiguredEndPoints;
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

   public LifecycleManagerEndPointMBean createConfiguredEndPoint(String param0) {
      LifecycleManagerEndPointMBeanImpl lookup = (LifecycleManagerEndPointMBeanImpl)this.lookupConfiguredEndPoint(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         LifecycleManagerEndPointMBeanImpl _val = new LifecycleManagerEndPointMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addConfiguredEndPoint(_val);
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

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void destroyConfiguredEndPoint(LifecycleManagerEndPointMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         LifecycleManagerEndPointMBean[] _old = this.getConfiguredEndPoints();
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
               this.setConfiguredEndPoints(_new);
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

   public boolean isEnabled() {
      return this._customizer.isEnabled();
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(20);
   }

   public void setEnabled(boolean param0) throws InvalidAttributeValueException {
      this._Enabled = param0;
   }

   public boolean isOutOfBandEnabled() {
      return this._OutOfBandEnabled;
   }

   public boolean isOutOfBandEnabledInherited() {
      return false;
   }

   public boolean isOutOfBandEnabledSet() {
      return this._isSet(21);
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

   public void setOutOfBandEnabled(boolean param0) {
      boolean _oldVal = this._OutOfBandEnabled;
      this._OutOfBandEnabled = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getPeriodicSyncInterval() {
      return this._PeriodicSyncInterval;
   }

   public boolean isPeriodicSyncIntervalInherited() {
      return false;
   }

   public boolean isPeriodicSyncIntervalSet() {
      return this._isSet(22);
   }

   public void setPeriodicSyncInterval(int param0) {
      int _oldVal = this._PeriodicSyncInterval;
      this._PeriodicSyncInterval = param0;
      this._postSet(22, _oldVal, param0);
   }

   public long getConfigFileLockTimeout() {
      return this._ConfigFileLockTimeout;
   }

   public boolean isConfigFileLockTimeoutInherited() {
      return false;
   }

   public boolean isConfigFileLockTimeoutSet() {
      return this._isSet(23);
   }

   public void setConfigFileLockTimeout(long param0) {
      long _oldVal = this._ConfigFileLockTimeout;
      this._ConfigFileLockTimeout = param0;
      this._postSet(23, _oldVal, param0);
   }

   public long getPropagationActivateTimeout() {
      return this._PropagationActivateTimeout;
   }

   public boolean isPropagationActivateTimeoutInherited() {
      return false;
   }

   public boolean isPropagationActivateTimeoutSet() {
      return this._isSet(24);
   }

   public void setPropagationActivateTimeout(long param0) {
      long _oldVal = this._PropagationActivateTimeout;
      this._PropagationActivateTimeout = param0;
      this._postSet(24, _oldVal, param0);
   }

   public long getServerRuntimeTimeout() {
      return this._ServerRuntimeTimeout;
   }

   public boolean isServerRuntimeTimeoutInherited() {
      return false;
   }

   public boolean isServerRuntimeTimeoutSet() {
      return this._isSet(25);
   }

   public void setServerRuntimeTimeout(long param0) {
      long _oldVal = this._ServerRuntimeTimeout;
      this._ServerRuntimeTimeout = param0;
      this._postSet(25, _oldVal, param0);
   }

   public long getServerReadyTimeout() {
      return this._ServerReadyTimeout;
   }

   public boolean isServerReadyTimeoutInherited() {
      return false;
   }

   public boolean isServerReadyTimeoutSet() {
      return this._isSet(26);
   }

   public void setServerReadyTimeout(long param0) {
      long _oldVal = this._ServerReadyTimeout;
      this._ServerReadyTimeout = param0;
      this._postSet(26, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LifecycleManagerConfigValidator.validateConfig(this);
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
         idx = 23;
      }

      try {
         switch (idx) {
            case 23:
               this._ConfigFileLockTimeout = 120000L;
               if (initOne) {
                  break;
               }
            case 19:
               this._ConfiguredEndPoints = new LifecycleManagerEndPointMBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._DataSourceName = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._DeploymentType = "none";
               if (initOne) {
                  break;
               }
            case 18:
               this._EndPoints = new LifecycleManagerEndPointMBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._LCMInitiatedConnectTimeout = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._LCMInitiatedConnectTimeoutForElasticity = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._LCMInitiatedReadTimeout = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._LCMInitiatedReadTimeoutForElasticity = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 22:
               this._PeriodicSyncInterval = 2;
               if (initOne) {
                  break;
               }
            case 16:
               this._PersistenceType = "XML";
               if (initOne) {
                  break;
               }
            case 24:
               this._PropagationActivateTimeout = 180000L;
               if (initOne) {
                  break;
               }
            case 26:
               this._ServerReadyTimeout = 60000L;
               if (initOne) {
                  break;
               }
            case 25:
               this._ServerRuntimeTimeout = 600000L;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 15:
               this._Target = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._Enabled = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._OutOfBandEnabled = false;
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
      return "LifecycleManagerConfig";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("ConfigFileLockTimeout")) {
         oldVal = this._ConfigFileLockTimeout;
         this._ConfigFileLockTimeout = (Long)v;
         this._postSet(23, oldVal, this._ConfigFileLockTimeout);
      } else {
         LifecycleManagerEndPointMBean[] oldVal;
         if (name.equals("ConfiguredEndPoints")) {
            oldVal = this._ConfiguredEndPoints;
            this._ConfiguredEndPoints = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])v);
            this._postSet(19, oldVal, this._ConfiguredEndPoints);
         } else {
            String oldVal;
            if (name.equals("DataSourceName")) {
               oldVal = this._DataSourceName;
               this._DataSourceName = (String)v;
               this._postSet(17, oldVal, this._DataSourceName);
            } else if (name.equals("DeploymentType")) {
               oldVal = this._DeploymentType;
               this._DeploymentType = (String)v;
               this._postSet(14, oldVal, this._DeploymentType);
            } else {
               boolean oldVal;
               if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("Enabled")) {
                  oldVal = this._Enabled;
                  this._Enabled = (Boolean)v;
                  this._postSet(20, oldVal, this._Enabled);
               } else if (name.equals("EndPoints")) {
                  oldVal = this._EndPoints;
                  this._EndPoints = (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])v);
                  this._postSet(18, oldVal, this._EndPoints);
               } else {
                  int oldVal;
                  if (name.equals("LCMInitiatedConnectTimeout")) {
                     oldVal = this._LCMInitiatedConnectTimeout;
                     this._LCMInitiatedConnectTimeout = (Integer)v;
                     this._postSet(10, oldVal, this._LCMInitiatedConnectTimeout);
                  } else if (name.equals("LCMInitiatedConnectTimeoutForElasticity")) {
                     oldVal = this._LCMInitiatedConnectTimeoutForElasticity;
                     this._LCMInitiatedConnectTimeoutForElasticity = (Integer)v;
                     this._postSet(12, oldVal, this._LCMInitiatedConnectTimeoutForElasticity);
                  } else if (name.equals("LCMInitiatedReadTimeout")) {
                     oldVal = this._LCMInitiatedReadTimeout;
                     this._LCMInitiatedReadTimeout = (Integer)v;
                     this._postSet(11, oldVal, this._LCMInitiatedReadTimeout);
                  } else if (name.equals("LCMInitiatedReadTimeoutForElasticity")) {
                     oldVal = this._LCMInitiatedReadTimeoutForElasticity;
                     this._LCMInitiatedReadTimeoutForElasticity = (Integer)v;
                     this._postSet(13, oldVal, this._LCMInitiatedReadTimeoutForElasticity);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("OutOfBandEnabled")) {
                     oldVal = this._OutOfBandEnabled;
                     this._OutOfBandEnabled = (Boolean)v;
                     this._postSet(21, oldVal, this._OutOfBandEnabled);
                  } else if (name.equals("PeriodicSyncInterval")) {
                     oldVal = this._PeriodicSyncInterval;
                     this._PeriodicSyncInterval = (Integer)v;
                     this._postSet(22, oldVal, this._PeriodicSyncInterval);
                  } else if (name.equals("PersistenceType")) {
                     oldVal = this._PersistenceType;
                     this._PersistenceType = (String)v;
                     this._postSet(16, oldVal, this._PersistenceType);
                  } else if (name.equals("PropagationActivateTimeout")) {
                     oldVal = this._PropagationActivateTimeout;
                     this._PropagationActivateTimeout = (Long)v;
                     this._postSet(24, oldVal, this._PropagationActivateTimeout);
                  } else if (name.equals("ServerReadyTimeout")) {
                     oldVal = this._ServerReadyTimeout;
                     this._ServerReadyTimeout = (Long)v;
                     this._postSet(26, oldVal, this._ServerReadyTimeout);
                  } else if (name.equals("ServerRuntimeTimeout")) {
                     oldVal = this._ServerRuntimeTimeout;
                     this._ServerRuntimeTimeout = (Long)v;
                     this._postSet(25, oldVal, this._ServerRuntimeTimeout);
                  } else if (name.equals("Tags")) {
                     String[] oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("Target")) {
                     TargetMBean oldVal = this._Target;
                     this._Target = (TargetMBean)v;
                     this._postSet(15, oldVal, this._Target);
                  } else if (name.equals("customizer")) {
                     LifecycleManagerConfig oldVal = this._customizer;
                     this._customizer = (LifecycleManagerConfig)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConfigFileLockTimeout")) {
         return new Long(this._ConfigFileLockTimeout);
      } else if (name.equals("ConfiguredEndPoints")) {
         return this._ConfiguredEndPoints;
      } else if (name.equals("DataSourceName")) {
         return this._DataSourceName;
      } else if (name.equals("DeploymentType")) {
         return this._DeploymentType;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("EndPoints")) {
         return this._EndPoints;
      } else if (name.equals("LCMInitiatedConnectTimeout")) {
         return new Integer(this._LCMInitiatedConnectTimeout);
      } else if (name.equals("LCMInitiatedConnectTimeoutForElasticity")) {
         return new Integer(this._LCMInitiatedConnectTimeoutForElasticity);
      } else if (name.equals("LCMInitiatedReadTimeout")) {
         return new Integer(this._LCMInitiatedReadTimeout);
      } else if (name.equals("LCMInitiatedReadTimeoutForElasticity")) {
         return new Integer(this._LCMInitiatedReadTimeoutForElasticity);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OutOfBandEnabled")) {
         return new Boolean(this._OutOfBandEnabled);
      } else if (name.equals("PeriodicSyncInterval")) {
         return new Integer(this._PeriodicSyncInterval);
      } else if (name.equals("PersistenceType")) {
         return this._PersistenceType;
      } else if (name.equals("PropagationActivateTimeout")) {
         return new Long(this._PropagationActivateTimeout);
      } else if (name.equals("ServerReadyTimeout")) {
         return new Long(this._ServerReadyTimeout);
      } else if (name.equals("ServerRuntimeTimeout")) {
         return new Long(this._ServerRuntimeTimeout);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Target")) {
         return this._Target;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            case 21:
            case 23:
            case 25:
            case 27:
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
            case 42:
            case 43:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 15;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 20;
               }
               break;
            case 9:
               if (s.equals("end-point")) {
                  return 18;
               }
               break;
            case 15:
               if (s.equals("deployment-type")) {
                  return 14;
               }
               break;
            case 16:
               if (s.equals("data-source-name")) {
                  return 17;
               }

               if (s.equals("persistence-type")) {
                  return 16;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("out-of-band-enabled")) {
                  return 21;
               }
               break;
            case 20:
               if (s.equals("configured-end-point")) {
                  return 19;
               }

               if (s.equals("server-ready-timeout")) {
                  return 26;
               }
               break;
            case 22:
               if (s.equals("periodic-sync-interval")) {
                  return 22;
               }

               if (s.equals("server-runtime-timeout")) {
                  return 25;
               }
               break;
            case 24:
               if (s.equals("config-file-lock-timeout")) {
                  return 23;
               }
               break;
            case 26:
               if (s.equals("lcm-initiated-read-timeout")) {
                  return 11;
               }
               break;
            case 28:
               if (s.equals("propagation-activate-timeout")) {
                  return 24;
               }
               break;
            case 29:
               if (s.equals("lcm-initiated-connect-timeout")) {
                  return 10;
               }
               break;
            case 41:
               if (s.equals("lcm-initiated-read-timeout-for-elasticity")) {
                  return 13;
               }
               break;
            case 44:
               if (s.equals("lcm-initiated-connect-timeout-for-elasticity")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 19:
               return new LifecycleManagerEndPointMBeanImpl.SchemaHelper2();
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
               return "lcm-initiated-connect-timeout";
            case 11:
               return "lcm-initiated-read-timeout";
            case 12:
               return "lcm-initiated-connect-timeout-for-elasticity";
            case 13:
               return "lcm-initiated-read-timeout-for-elasticity";
            case 14:
               return "deployment-type";
            case 15:
               return "target";
            case 16:
               return "persistence-type";
            case 17:
               return "data-source-name";
            case 18:
               return "end-point";
            case 19:
               return "configured-end-point";
            case 20:
               return "enabled";
            case 21:
               return "out-of-band-enabled";
            case 22:
               return "periodic-sync-interval";
            case 23:
               return "config-file-lock-timeout";
            case 24:
               return "propagation-activate-timeout";
            case 25:
               return "server-runtime-timeout";
            case 26:
               return "server-ready-timeout";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 19:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private LifecycleManagerConfigMBeanImpl bean;

      protected Helper(LifecycleManagerConfigMBeanImpl bean) {
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
               return "LCMInitiatedConnectTimeout";
            case 11:
               return "LCMInitiatedReadTimeout";
            case 12:
               return "LCMInitiatedConnectTimeoutForElasticity";
            case 13:
               return "LCMInitiatedReadTimeoutForElasticity";
            case 14:
               return "DeploymentType";
            case 15:
               return "Target";
            case 16:
               return "PersistenceType";
            case 17:
               return "DataSourceName";
            case 18:
               return "EndPoints";
            case 19:
               return "ConfiguredEndPoints";
            case 20:
               return "Enabled";
            case 21:
               return "OutOfBandEnabled";
            case 22:
               return "PeriodicSyncInterval";
            case 23:
               return "ConfigFileLockTimeout";
            case 24:
               return "PropagationActivateTimeout";
            case 25:
               return "ServerRuntimeTimeout";
            case 26:
               return "ServerReadyTimeout";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConfigFileLockTimeout")) {
            return 23;
         } else if (propName.equals("ConfiguredEndPoints")) {
            return 19;
         } else if (propName.equals("DataSourceName")) {
            return 17;
         } else if (propName.equals("DeploymentType")) {
            return 14;
         } else if (propName.equals("EndPoints")) {
            return 18;
         } else if (propName.equals("LCMInitiatedConnectTimeout")) {
            return 10;
         } else if (propName.equals("LCMInitiatedConnectTimeoutForElasticity")) {
            return 12;
         } else if (propName.equals("LCMInitiatedReadTimeout")) {
            return 11;
         } else if (propName.equals("LCMInitiatedReadTimeoutForElasticity")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PeriodicSyncInterval")) {
            return 22;
         } else if (propName.equals("PersistenceType")) {
            return 16;
         } else if (propName.equals("PropagationActivateTimeout")) {
            return 24;
         } else if (propName.equals("ServerReadyTimeout")) {
            return 26;
         } else if (propName.equals("ServerRuntimeTimeout")) {
            return 25;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Target")) {
            return 15;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("Enabled")) {
            return 20;
         } else {
            return propName.equals("OutOfBandEnabled") ? 21 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfiguredEndPoints()));
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
            if (this.bean.isConfigFileLockTimeoutSet()) {
               buf.append("ConfigFileLockTimeout");
               buf.append(String.valueOf(this.bean.getConfigFileLockTimeout()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getConfiguredEndPoints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfiguredEndPoints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDataSourceNameSet()) {
               buf.append("DataSourceName");
               buf.append(String.valueOf(this.bean.getDataSourceName()));
            }

            if (this.bean.isDeploymentTypeSet()) {
               buf.append("DeploymentType");
               buf.append(String.valueOf(this.bean.getDeploymentType()));
            }

            if (this.bean.isEndPointsSet()) {
               buf.append("EndPoints");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEndPoints())));
            }

            if (this.bean.isLCMInitiatedConnectTimeoutSet()) {
               buf.append("LCMInitiatedConnectTimeout");
               buf.append(String.valueOf(this.bean.getLCMInitiatedConnectTimeout()));
            }

            if (this.bean.isLCMInitiatedConnectTimeoutForElasticitySet()) {
               buf.append("LCMInitiatedConnectTimeoutForElasticity");
               buf.append(String.valueOf(this.bean.getLCMInitiatedConnectTimeoutForElasticity()));
            }

            if (this.bean.isLCMInitiatedReadTimeoutSet()) {
               buf.append("LCMInitiatedReadTimeout");
               buf.append(String.valueOf(this.bean.getLCMInitiatedReadTimeout()));
            }

            if (this.bean.isLCMInitiatedReadTimeoutForElasticitySet()) {
               buf.append("LCMInitiatedReadTimeoutForElasticity");
               buf.append(String.valueOf(this.bean.getLCMInitiatedReadTimeoutForElasticity()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPeriodicSyncIntervalSet()) {
               buf.append("PeriodicSyncInterval");
               buf.append(String.valueOf(this.bean.getPeriodicSyncInterval()));
            }

            if (this.bean.isPersistenceTypeSet()) {
               buf.append("PersistenceType");
               buf.append(String.valueOf(this.bean.getPersistenceType()));
            }

            if (this.bean.isPropagationActivateTimeoutSet()) {
               buf.append("PropagationActivateTimeout");
               buf.append(String.valueOf(this.bean.getPropagationActivateTimeout()));
            }

            if (this.bean.isServerReadyTimeoutSet()) {
               buf.append("ServerReadyTimeout");
               buf.append(String.valueOf(this.bean.getServerReadyTimeout()));
            }

            if (this.bean.isServerRuntimeTimeoutSet()) {
               buf.append("ServerRuntimeTimeout");
               buf.append(String.valueOf(this.bean.getServerRuntimeTimeout()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetSet()) {
               buf.append("Target");
               buf.append(String.valueOf(this.bean.getTarget()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isOutOfBandEnabledSet()) {
               buf.append("OutOfBandEnabled");
               buf.append(String.valueOf(this.bean.isOutOfBandEnabled()));
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
            LifecycleManagerConfigMBeanImpl otherTyped = (LifecycleManagerConfigMBeanImpl)other;
            this.computeDiff("ConfigFileLockTimeout", this.bean.getConfigFileLockTimeout(), otherTyped.getConfigFileLockTimeout(), true);
            this.computeChildDiff("ConfiguredEndPoints", this.bean.getConfiguredEndPoints(), otherTyped.getConfiguredEndPoints(), true);
            this.computeDiff("DataSourceName", this.bean.getDataSourceName(), otherTyped.getDataSourceName(), true);
            this.computeDiff("DeploymentType", this.bean.getDeploymentType(), otherTyped.getDeploymentType(), false);
            this.computeDiff("LCMInitiatedConnectTimeout", this.bean.getLCMInitiatedConnectTimeout(), otherTyped.getLCMInitiatedConnectTimeout(), true);
            this.computeDiff("LCMInitiatedConnectTimeoutForElasticity", this.bean.getLCMInitiatedConnectTimeoutForElasticity(), otherTyped.getLCMInitiatedConnectTimeoutForElasticity(), true);
            this.computeDiff("LCMInitiatedReadTimeout", this.bean.getLCMInitiatedReadTimeout(), otherTyped.getLCMInitiatedReadTimeout(), true);
            this.computeDiff("LCMInitiatedReadTimeoutForElasticity", this.bean.getLCMInitiatedReadTimeoutForElasticity(), otherTyped.getLCMInitiatedReadTimeoutForElasticity(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PeriodicSyncInterval", this.bean.getPeriodicSyncInterval(), otherTyped.getPeriodicSyncInterval(), false);
            this.computeDiff("PersistenceType", this.bean.getPersistenceType(), otherTyped.getPersistenceType(), false);
            this.computeDiff("PropagationActivateTimeout", this.bean.getPropagationActivateTimeout(), otherTyped.getPropagationActivateTimeout(), true);
            this.computeDiff("ServerReadyTimeout", this.bean.getServerReadyTimeout(), otherTyped.getServerReadyTimeout(), true);
            this.computeDiff("ServerRuntimeTimeout", this.bean.getServerRuntimeTimeout(), otherTyped.getServerRuntimeTimeout(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Target", this.bean.getTarget(), otherTyped.getTarget(), false);
            this.computeDiff("OutOfBandEnabled", this.bean.isOutOfBandEnabled(), otherTyped.isOutOfBandEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LifecycleManagerConfigMBeanImpl original = (LifecycleManagerConfigMBeanImpl)event.getSourceBean();
            LifecycleManagerConfigMBeanImpl proposed = (LifecycleManagerConfigMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConfigFileLockTimeout")) {
                  original.setConfigFileLockTimeout(proposed.getConfigFileLockTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ConfiguredEndPoints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfiguredEndPoint((LifecycleManagerEndPointMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfiguredEndPoint((LifecycleManagerEndPointMBean)update.getRemovedObject());
                  }

                  if (original.getConfiguredEndPoints() == null || original.getConfiguredEndPoints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  }
               } else if (prop.equals("DataSourceName")) {
                  original.setDataSourceName(proposed.getDataSourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("DeploymentType")) {
                  original.setDeploymentType(proposed.getDeploymentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (!prop.equals("EndPoints")) {
                  if (prop.equals("LCMInitiatedConnectTimeout")) {
                     original.setLCMInitiatedConnectTimeout(proposed.getLCMInitiatedConnectTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("LCMInitiatedConnectTimeoutForElasticity")) {
                     original.setLCMInitiatedConnectTimeoutForElasticity(proposed.getLCMInitiatedConnectTimeoutForElasticity());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("LCMInitiatedReadTimeout")) {
                     original.setLCMInitiatedReadTimeout(proposed.getLCMInitiatedReadTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("LCMInitiatedReadTimeoutForElasticity")) {
                     original.setLCMInitiatedReadTimeoutForElasticity(proposed.getLCMInitiatedReadTimeoutForElasticity());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("PeriodicSyncInterval")) {
                     original.setPeriodicSyncInterval(proposed.getPeriodicSyncInterval());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("PersistenceType")) {
                     original.setPersistenceType(proposed.getPersistenceType());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("PropagationActivateTimeout")) {
                     original.setPropagationActivateTimeout(proposed.getPropagationActivateTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("ServerReadyTimeout")) {
                     original.setServerReadyTimeout(proposed.getServerReadyTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (prop.equals("ServerRuntimeTimeout")) {
                     original.setServerRuntimeTimeout(proposed.getServerRuntimeTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
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
                  } else if (prop.equals("Target")) {
                     original.setTargetAsString(proposed.getTargetAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (!prop.equals("DynamicallyCreated") && !prop.equals("Enabled")) {
                     if (prop.equals("OutOfBandEnabled")) {
                        original.setOutOfBandEnabled(proposed.isOutOfBandEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 21);
                     } else {
                        super.applyPropertyUpdate(event, update);
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
            LifecycleManagerConfigMBeanImpl copy = (LifecycleManagerConfigMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConfigFileLockTimeout")) && this.bean.isConfigFileLockTimeoutSet()) {
               copy.setConfigFileLockTimeout(this.bean.getConfigFileLockTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfiguredEndPoints")) && this.bean.isConfiguredEndPointsSet() && !copy._isSet(19)) {
               LifecycleManagerEndPointMBean[] oldConfiguredEndPoints = this.bean.getConfiguredEndPoints();
               LifecycleManagerEndPointMBean[] newConfiguredEndPoints = new LifecycleManagerEndPointMBean[oldConfiguredEndPoints.length];

               for(int i = 0; i < newConfiguredEndPoints.length; ++i) {
                  newConfiguredEndPoints[i] = (LifecycleManagerEndPointMBean)((LifecycleManagerEndPointMBean)this.createCopy((AbstractDescriptorBean)oldConfiguredEndPoints[i], includeObsolete));
               }

               copy.setConfiguredEndPoints(newConfiguredEndPoints);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceName")) && this.bean.isDataSourceNameSet()) {
               copy.setDataSourceName(this.bean.getDataSourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentType")) && this.bean.isDeploymentTypeSet()) {
               copy.setDeploymentType(this.bean.getDeploymentType());
            }

            if ((excludeProps == null || !excludeProps.contains("LCMInitiatedConnectTimeout")) && this.bean.isLCMInitiatedConnectTimeoutSet()) {
               copy.setLCMInitiatedConnectTimeout(this.bean.getLCMInitiatedConnectTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("LCMInitiatedConnectTimeoutForElasticity")) && this.bean.isLCMInitiatedConnectTimeoutForElasticitySet()) {
               copy.setLCMInitiatedConnectTimeoutForElasticity(this.bean.getLCMInitiatedConnectTimeoutForElasticity());
            }

            if ((excludeProps == null || !excludeProps.contains("LCMInitiatedReadTimeout")) && this.bean.isLCMInitiatedReadTimeoutSet()) {
               copy.setLCMInitiatedReadTimeout(this.bean.getLCMInitiatedReadTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("LCMInitiatedReadTimeoutForElasticity")) && this.bean.isLCMInitiatedReadTimeoutForElasticitySet()) {
               copy.setLCMInitiatedReadTimeoutForElasticity(this.bean.getLCMInitiatedReadTimeoutForElasticity());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PeriodicSyncInterval")) && this.bean.isPeriodicSyncIntervalSet()) {
               copy.setPeriodicSyncInterval(this.bean.getPeriodicSyncInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceType")) && this.bean.isPersistenceTypeSet()) {
               copy.setPersistenceType(this.bean.getPersistenceType());
            }

            if ((excludeProps == null || !excludeProps.contains("PropagationActivateTimeout")) && this.bean.isPropagationActivateTimeoutSet()) {
               copy.setPropagationActivateTimeout(this.bean.getPropagationActivateTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerReadyTimeout")) && this.bean.isServerReadyTimeoutSet()) {
               copy.setServerReadyTimeout(this.bean.getServerReadyTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerRuntimeTimeout")) && this.bean.isServerRuntimeTimeoutSet()) {
               copy.setServerRuntimeTimeout(this.bean.getServerRuntimeTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Target")) && this.bean.isTargetSet()) {
               copy._unSet(copy, 15);
               copy.setTargetAsString(this.bean.getTargetAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("OutOfBandEnabled")) && this.bean.isOutOfBandEnabledSet()) {
               copy.setOutOfBandEnabled(this.bean.isOutOfBandEnabled());
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
         this.inferSubTree(this.bean.getConfiguredEndPoints(), clazz, annotation);
         this.inferSubTree(this.bean.getEndPoints(), clazz, annotation);
         this.inferSubTree(this.bean.getTarget(), clazz, annotation);
      }
   }
}
