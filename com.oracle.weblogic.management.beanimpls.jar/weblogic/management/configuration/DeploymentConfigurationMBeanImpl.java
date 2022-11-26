package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DeploymentConfigurationMBeanImpl extends ConfigurationMBeanImpl implements DeploymentConfigurationMBean, Serializable {
   private int _DefaultMultiVersionAppRetireTimeout;
   private DeploymentConfigOverridesMBean _DeploymentConfigOverrides;
   private int _DeploymentServiceMessageRetryCount;
   private int _DeploymentServiceMessageRetryInterval;
   private DeploymentValidationPluginMBean _DeploymentValidationPlugin;
   private int _LongRunningRetireThreadDumpCount;
   private long _LongRunningRetireThreadDumpInterval;
   private long _LongRunningRetireThreadDumpStartTime;
   private int _MaxAppVersions;
   private int _MaxRetiredTasks;
   private boolean _RemoteDeployerEJBEnabled;
   private boolean _RestageOnlyOnRedeploy;
   private static SchemaHelper2 _schemaHelper;

   public DeploymentConfigurationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DeploymentConfigurationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DeploymentConfigurationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxAppVersions() {
      return this._MaxAppVersions;
   }

   public boolean isMaxAppVersionsInherited() {
      return false;
   }

   public boolean isMaxAppVersionsSet() {
      return this._isSet(10);
   }

   public void setMaxAppVersions(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxAppVersions", (long)param0, 1L, 65534L);
      int _oldVal = this._MaxAppVersions;
      this._MaxAppVersions = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isRemoteDeployerEJBEnabled() {
      return this._RemoteDeployerEJBEnabled;
   }

   public boolean isRemoteDeployerEJBEnabledInherited() {
      return false;
   }

   public boolean isRemoteDeployerEJBEnabledSet() {
      return this._isSet(11);
   }

   public void setRemoteDeployerEJBEnabled(boolean param0) {
      boolean _oldVal = this._RemoteDeployerEJBEnabled;
      this._RemoteDeployerEJBEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isRestageOnlyOnRedeploy() {
      return this._RestageOnlyOnRedeploy;
   }

   public boolean isRestageOnlyOnRedeployInherited() {
      return false;
   }

   public boolean isRestageOnlyOnRedeploySet() {
      return this._isSet(12);
   }

   public void setRestageOnlyOnRedeploy(boolean param0) {
      boolean _oldVal = this._RestageOnlyOnRedeploy;
      this._RestageOnlyOnRedeploy = param0;
      this._postSet(12, _oldVal, param0);
   }

   public DeploymentValidationPluginMBean getDeploymentValidationPlugin() {
      return this._DeploymentValidationPlugin;
   }

   public boolean isDeploymentValidationPluginInherited() {
      return false;
   }

   public boolean isDeploymentValidationPluginSet() {
      return this._isSet(13) || this._isAnythingSet((AbstractDescriptorBean)this.getDeploymentValidationPlugin());
   }

   public void setDeploymentValidationPlugin(DeploymentValidationPluginMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 13)) {
         this._postCreate(_child);
      }

      DeploymentValidationPluginMBean _oldVal = this._DeploymentValidationPlugin;
      this._DeploymentValidationPlugin = param0;
      this._postSet(13, _oldVal, param0);
   }

   public void setMaxRetiredTasks(int param0) {
      int _oldVal = this._MaxRetiredTasks;
      this._MaxRetiredTasks = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMaxRetiredTasks() {
      return this._MaxRetiredTasks;
   }

   public boolean isMaxRetiredTasksInherited() {
      return false;
   }

   public boolean isMaxRetiredTasksSet() {
      return this._isSet(14);
   }

   public int getDeploymentServiceMessageRetryCount() {
      return this._DeploymentServiceMessageRetryCount;
   }

   public boolean isDeploymentServiceMessageRetryCountInherited() {
      return false;
   }

   public boolean isDeploymentServiceMessageRetryCountSet() {
      return this._isSet(15);
   }

   public void setDeploymentServiceMessageRetryCount(int param0) {
      LegalChecks.checkMin("DeploymentServiceMessageRetryCount", param0, 0);
      int _oldVal = this._DeploymentServiceMessageRetryCount;
      this._DeploymentServiceMessageRetryCount = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getDeploymentServiceMessageRetryInterval() {
      return this._DeploymentServiceMessageRetryInterval;
   }

   public boolean isDeploymentServiceMessageRetryIntervalInherited() {
      return false;
   }

   public boolean isDeploymentServiceMessageRetryIntervalSet() {
      return this._isSet(16);
   }

   public void setDeploymentServiceMessageRetryInterval(int param0) {
      LegalChecks.checkMin("DeploymentServiceMessageRetryInterval", param0, 0);
      int _oldVal = this._DeploymentServiceMessageRetryInterval;
      this._DeploymentServiceMessageRetryInterval = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getLongRunningRetireThreadDumpStartTime() {
      return this._LongRunningRetireThreadDumpStartTime;
   }

   public boolean isLongRunningRetireThreadDumpStartTimeInherited() {
      return false;
   }

   public boolean isLongRunningRetireThreadDumpStartTimeSet() {
      return this._isSet(17);
   }

   public void setLongRunningRetireThreadDumpStartTime(long param0) {
      LegalChecks.checkMin("LongRunningRetireThreadDumpStartTime", param0, 0L);
      long _oldVal = this._LongRunningRetireThreadDumpStartTime;
      this._LongRunningRetireThreadDumpStartTime = param0;
      this._postSet(17, _oldVal, param0);
   }

   public long getLongRunningRetireThreadDumpInterval() {
      return this._LongRunningRetireThreadDumpInterval;
   }

   public boolean isLongRunningRetireThreadDumpIntervalInherited() {
      return false;
   }

   public boolean isLongRunningRetireThreadDumpIntervalSet() {
      return this._isSet(18);
   }

   public void setLongRunningRetireThreadDumpInterval(long param0) {
      LegalChecks.checkMin("LongRunningRetireThreadDumpInterval", param0, 0L);
      long _oldVal = this._LongRunningRetireThreadDumpInterval;
      this._LongRunningRetireThreadDumpInterval = param0;
      this._postSet(18, _oldVal, param0);
   }

   public int getLongRunningRetireThreadDumpCount() {
      return this._LongRunningRetireThreadDumpCount;
   }

   public boolean isLongRunningRetireThreadDumpCountInherited() {
      return false;
   }

   public boolean isLongRunningRetireThreadDumpCountSet() {
      return this._isSet(19);
   }

   public void setLongRunningRetireThreadDumpCount(int param0) {
      LegalChecks.checkMin("LongRunningRetireThreadDumpCount", param0, 0);
      int _oldVal = this._LongRunningRetireThreadDumpCount;
      this._LongRunningRetireThreadDumpCount = param0;
      this._postSet(19, _oldVal, param0);
   }

   public DeploymentConfigOverridesMBean getDeploymentConfigOverrides() {
      return this._DeploymentConfigOverrides;
   }

   public boolean isDeploymentConfigOverridesInherited() {
      return false;
   }

   public boolean isDeploymentConfigOverridesSet() {
      return this._isSet(20) || this._isAnythingSet((AbstractDescriptorBean)this.getDeploymentConfigOverrides());
   }

   public void setDeploymentConfigOverrides(DeploymentConfigOverridesMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 20)) {
         this._postCreate(_child);
      }

      DeploymentConfigOverridesMBean _oldVal = this._DeploymentConfigOverrides;
      this._DeploymentConfigOverrides = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getDefaultMultiVersionAppRetireTimeout() {
      return this._DefaultMultiVersionAppRetireTimeout;
   }

   public boolean isDefaultMultiVersionAppRetireTimeoutInherited() {
      return false;
   }

   public boolean isDefaultMultiVersionAppRetireTimeoutSet() {
      return this._isSet(21);
   }

   public void setDefaultMultiVersionAppRetireTimeout(int param0) {
      LegalChecks.checkMin("DefaultMultiVersionAppRetireTimeout", param0, 0);
      int _oldVal = this._DefaultMultiVersionAppRetireTimeout;
      this._DefaultMultiVersionAppRetireTimeout = param0;
      this._postSet(21, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
      return super._isAnythingSet() || this.isDeploymentConfigOverridesSet() || this.isDeploymentValidationPluginSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 21;
      }

      try {
         switch (idx) {
            case 21:
               this._DefaultMultiVersionAppRetireTimeout = 300;
               if (initOne) {
                  break;
               }
            case 20:
               this._DeploymentConfigOverrides = new DeploymentConfigOverridesMBeanImpl(this, 20);
               this._postCreate((AbstractDescriptorBean)this._DeploymentConfigOverrides);
               if (initOne) {
                  break;
               }
            case 15:
               this._DeploymentServiceMessageRetryCount = 60;
               if (initOne) {
                  break;
               }
            case 16:
               this._DeploymentServiceMessageRetryInterval = 5000;
               if (initOne) {
                  break;
               }
            case 13:
               this._DeploymentValidationPlugin = new DeploymentValidationPluginMBeanImpl(this, 13);
               this._postCreate((AbstractDescriptorBean)this._DeploymentValidationPlugin);
               if (initOne) {
                  break;
               }
            case 19:
               this._LongRunningRetireThreadDumpCount = 3;
               if (initOne) {
                  break;
               }
            case 18:
               this._LongRunningRetireThreadDumpInterval = 60000L;
               if (initOne) {
                  break;
               }
            case 17:
               this._LongRunningRetireThreadDumpStartTime = 1200000L;
               if (initOne) {
                  break;
               }
            case 10:
               this._MaxAppVersions = 2;
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxRetiredTasks = 20;
               if (initOne) {
                  break;
               }
            case 11:
               this._RemoteDeployerEJBEnabled = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._RestageOnlyOnRedeploy = false;
               if (initOne) {
                  break;
               }
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
      return "DeploymentConfiguration";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("DefaultMultiVersionAppRetireTimeout")) {
         oldVal = this._DefaultMultiVersionAppRetireTimeout;
         this._DefaultMultiVersionAppRetireTimeout = (Integer)v;
         this._postSet(21, oldVal, this._DefaultMultiVersionAppRetireTimeout);
      } else if (name.equals("DeploymentConfigOverrides")) {
         DeploymentConfigOverridesMBean oldVal = this._DeploymentConfigOverrides;
         this._DeploymentConfigOverrides = (DeploymentConfigOverridesMBean)v;
         this._postSet(20, oldVal, this._DeploymentConfigOverrides);
      } else if (name.equals("DeploymentServiceMessageRetryCount")) {
         oldVal = this._DeploymentServiceMessageRetryCount;
         this._DeploymentServiceMessageRetryCount = (Integer)v;
         this._postSet(15, oldVal, this._DeploymentServiceMessageRetryCount);
      } else if (name.equals("DeploymentServiceMessageRetryInterval")) {
         oldVal = this._DeploymentServiceMessageRetryInterval;
         this._DeploymentServiceMessageRetryInterval = (Integer)v;
         this._postSet(16, oldVal, this._DeploymentServiceMessageRetryInterval);
      } else if (name.equals("DeploymentValidationPlugin")) {
         DeploymentValidationPluginMBean oldVal = this._DeploymentValidationPlugin;
         this._DeploymentValidationPlugin = (DeploymentValidationPluginMBean)v;
         this._postSet(13, oldVal, this._DeploymentValidationPlugin);
      } else if (name.equals("LongRunningRetireThreadDumpCount")) {
         oldVal = this._LongRunningRetireThreadDumpCount;
         this._LongRunningRetireThreadDumpCount = (Integer)v;
         this._postSet(19, oldVal, this._LongRunningRetireThreadDumpCount);
      } else {
         long oldVal;
         if (name.equals("LongRunningRetireThreadDumpInterval")) {
            oldVal = this._LongRunningRetireThreadDumpInterval;
            this._LongRunningRetireThreadDumpInterval = (Long)v;
            this._postSet(18, oldVal, this._LongRunningRetireThreadDumpInterval);
         } else if (name.equals("LongRunningRetireThreadDumpStartTime")) {
            oldVal = this._LongRunningRetireThreadDumpStartTime;
            this._LongRunningRetireThreadDumpStartTime = (Long)v;
            this._postSet(17, oldVal, this._LongRunningRetireThreadDumpStartTime);
         } else if (name.equals("MaxAppVersions")) {
            oldVal = this._MaxAppVersions;
            this._MaxAppVersions = (Integer)v;
            this._postSet(10, oldVal, this._MaxAppVersions);
         } else if (name.equals("MaxRetiredTasks")) {
            oldVal = this._MaxRetiredTasks;
            this._MaxRetiredTasks = (Integer)v;
            this._postSet(14, oldVal, this._MaxRetiredTasks);
         } else {
            boolean oldVal;
            if (name.equals("RemoteDeployerEJBEnabled")) {
               oldVal = this._RemoteDeployerEJBEnabled;
               this._RemoteDeployerEJBEnabled = (Boolean)v;
               this._postSet(11, oldVal, this._RemoteDeployerEJBEnabled);
            } else if (name.equals("RestageOnlyOnRedeploy")) {
               oldVal = this._RestageOnlyOnRedeploy;
               this._RestageOnlyOnRedeploy = (Boolean)v;
               this._postSet(12, oldVal, this._RestageOnlyOnRedeploy);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DefaultMultiVersionAppRetireTimeout")) {
         return new Integer(this._DefaultMultiVersionAppRetireTimeout);
      } else if (name.equals("DeploymentConfigOverrides")) {
         return this._DeploymentConfigOverrides;
      } else if (name.equals("DeploymentServiceMessageRetryCount")) {
         return new Integer(this._DeploymentServiceMessageRetryCount);
      } else if (name.equals("DeploymentServiceMessageRetryInterval")) {
         return new Integer(this._DeploymentServiceMessageRetryInterval);
      } else if (name.equals("DeploymentValidationPlugin")) {
         return this._DeploymentValidationPlugin;
      } else if (name.equals("LongRunningRetireThreadDumpCount")) {
         return new Integer(this._LongRunningRetireThreadDumpCount);
      } else if (name.equals("LongRunningRetireThreadDumpInterval")) {
         return new Long(this._LongRunningRetireThreadDumpInterval);
      } else if (name.equals("LongRunningRetireThreadDumpStartTime")) {
         return new Long(this._LongRunningRetireThreadDumpStartTime);
      } else if (name.equals("MaxAppVersions")) {
         return new Integer(this._MaxAppVersions);
      } else if (name.equals("MaxRetiredTasks")) {
         return new Integer(this._MaxRetiredTasks);
      } else if (name.equals("RemoteDeployerEJBEnabled")) {
         return new Boolean(this._RemoteDeployerEJBEnabled);
      } else {
         return name.equals("RestageOnlyOnRedeploy") ? new Boolean(this._RestageOnlyOnRedeploy) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 16:
               if (s.equals("max-app-versions")) {
                  return 10;
               }
               break;
            case 17:
               if (s.equals("max-retired-tasks")) {
                  return 14;
               }
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 39:
            default:
               break;
            case 24:
               if (s.equals("restage-only-on-redeploy")) {
                  return 12;
               }
               break;
            case 26:
               if (s.equals("remote-deployerejb-enabled")) {
                  return 11;
               }
               break;
            case 27:
               if (s.equals("deployment-config-overrides")) {
                  return 20;
               }
               break;
            case 28:
               if (s.equals("deployment-validation-plugin")) {
                  return 13;
               }
               break;
            case 37:
               if (s.equals("long-running-retire-thread-dump-count")) {
                  return 19;
               }
               break;
            case 38:
               if (s.equals("deployment-service-message-retry-count")) {
                  return 15;
               }
               break;
            case 40:
               if (s.equals("default-multi-version-app-retire-timeout")) {
                  return 21;
               }

               if (s.equals("long-running-retire-thread-dump-interval")) {
                  return 18;
               }
               break;
            case 41:
               if (s.equals("deployment-service-message-retry-interval")) {
                  return 16;
               }
               break;
            case 42:
               if (s.equals("long-running-retire-thread-dump-start-time")) {
                  return 17;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 13:
               return new DeploymentValidationPluginMBeanImpl.SchemaHelper2();
            case 20:
               return new DeploymentConfigOverridesMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "max-app-versions";
            case 11:
               return "remote-deployerejb-enabled";
            case 12:
               return "restage-only-on-redeploy";
            case 13:
               return "deployment-validation-plugin";
            case 14:
               return "max-retired-tasks";
            case 15:
               return "deployment-service-message-retry-count";
            case 16:
               return "deployment-service-message-retry-interval";
            case 17:
               return "long-running-retire-thread-dump-start-time";
            case 18:
               return "long-running-retire-thread-dump-interval";
            case 19:
               return "long-running-retire-thread-dump-count";
            case 20:
               return "deployment-config-overrides";
            case 21:
               return "default-multi-version-app-retire-timeout";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            case 20:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private DeploymentConfigurationMBeanImpl bean;

      protected Helper(DeploymentConfigurationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "MaxAppVersions";
            case 11:
               return "RemoteDeployerEJBEnabled";
            case 12:
               return "RestageOnlyOnRedeploy";
            case 13:
               return "DeploymentValidationPlugin";
            case 14:
               return "MaxRetiredTasks";
            case 15:
               return "DeploymentServiceMessageRetryCount";
            case 16:
               return "DeploymentServiceMessageRetryInterval";
            case 17:
               return "LongRunningRetireThreadDumpStartTime";
            case 18:
               return "LongRunningRetireThreadDumpInterval";
            case 19:
               return "LongRunningRetireThreadDumpCount";
            case 20:
               return "DeploymentConfigOverrides";
            case 21:
               return "DefaultMultiVersionAppRetireTimeout";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultMultiVersionAppRetireTimeout")) {
            return 21;
         } else if (propName.equals("DeploymentConfigOverrides")) {
            return 20;
         } else if (propName.equals("DeploymentServiceMessageRetryCount")) {
            return 15;
         } else if (propName.equals("DeploymentServiceMessageRetryInterval")) {
            return 16;
         } else if (propName.equals("DeploymentValidationPlugin")) {
            return 13;
         } else if (propName.equals("LongRunningRetireThreadDumpCount")) {
            return 19;
         } else if (propName.equals("LongRunningRetireThreadDumpInterval")) {
            return 18;
         } else if (propName.equals("LongRunningRetireThreadDumpStartTime")) {
            return 17;
         } else if (propName.equals("MaxAppVersions")) {
            return 10;
         } else if (propName.equals("MaxRetiredTasks")) {
            return 14;
         } else if (propName.equals("RemoteDeployerEJBEnabled")) {
            return 11;
         } else {
            return propName.equals("RestageOnlyOnRedeploy") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDeploymentConfigOverrides() != null) {
            iterators.add(new ArrayIterator(new DeploymentConfigOverridesMBean[]{this.bean.getDeploymentConfigOverrides()}));
         }

         if (this.bean.getDeploymentValidationPlugin() != null) {
            iterators.add(new ArrayIterator(new DeploymentValidationPluginMBean[]{this.bean.getDeploymentValidationPlugin()}));
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
            if (this.bean.isDefaultMultiVersionAppRetireTimeoutSet()) {
               buf.append("DefaultMultiVersionAppRetireTimeout");
               buf.append(String.valueOf(this.bean.getDefaultMultiVersionAppRetireTimeout()));
            }

            childValue = this.computeChildHashValue(this.bean.getDeploymentConfigOverrides());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDeploymentServiceMessageRetryCountSet()) {
               buf.append("DeploymentServiceMessageRetryCount");
               buf.append(String.valueOf(this.bean.getDeploymentServiceMessageRetryCount()));
            }

            if (this.bean.isDeploymentServiceMessageRetryIntervalSet()) {
               buf.append("DeploymentServiceMessageRetryInterval");
               buf.append(String.valueOf(this.bean.getDeploymentServiceMessageRetryInterval()));
            }

            childValue = this.computeChildHashValue(this.bean.getDeploymentValidationPlugin());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLongRunningRetireThreadDumpCountSet()) {
               buf.append("LongRunningRetireThreadDumpCount");
               buf.append(String.valueOf(this.bean.getLongRunningRetireThreadDumpCount()));
            }

            if (this.bean.isLongRunningRetireThreadDumpIntervalSet()) {
               buf.append("LongRunningRetireThreadDumpInterval");
               buf.append(String.valueOf(this.bean.getLongRunningRetireThreadDumpInterval()));
            }

            if (this.bean.isLongRunningRetireThreadDumpStartTimeSet()) {
               buf.append("LongRunningRetireThreadDumpStartTime");
               buf.append(String.valueOf(this.bean.getLongRunningRetireThreadDumpStartTime()));
            }

            if (this.bean.isMaxAppVersionsSet()) {
               buf.append("MaxAppVersions");
               buf.append(String.valueOf(this.bean.getMaxAppVersions()));
            }

            if (this.bean.isMaxRetiredTasksSet()) {
               buf.append("MaxRetiredTasks");
               buf.append(String.valueOf(this.bean.getMaxRetiredTasks()));
            }

            if (this.bean.isRemoteDeployerEJBEnabledSet()) {
               buf.append("RemoteDeployerEJBEnabled");
               buf.append(String.valueOf(this.bean.isRemoteDeployerEJBEnabled()));
            }

            if (this.bean.isRestageOnlyOnRedeploySet()) {
               buf.append("RestageOnlyOnRedeploy");
               buf.append(String.valueOf(this.bean.isRestageOnlyOnRedeploy()));
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
            DeploymentConfigurationMBeanImpl otherTyped = (DeploymentConfigurationMBeanImpl)other;
            this.computeDiff("DefaultMultiVersionAppRetireTimeout", this.bean.getDefaultMultiVersionAppRetireTimeout(), otherTyped.getDefaultMultiVersionAppRetireTimeout(), true);
            this.computeSubDiff("DeploymentConfigOverrides", this.bean.getDeploymentConfigOverrides(), otherTyped.getDeploymentConfigOverrides());
            this.computeDiff("DeploymentServiceMessageRetryCount", this.bean.getDeploymentServiceMessageRetryCount(), otherTyped.getDeploymentServiceMessageRetryCount(), true);
            this.computeDiff("DeploymentServiceMessageRetryInterval", this.bean.getDeploymentServiceMessageRetryInterval(), otherTyped.getDeploymentServiceMessageRetryInterval(), true);
            this.computeSubDiff("DeploymentValidationPlugin", this.bean.getDeploymentValidationPlugin(), otherTyped.getDeploymentValidationPlugin());
            this.computeDiff("LongRunningRetireThreadDumpCount", this.bean.getLongRunningRetireThreadDumpCount(), otherTyped.getLongRunningRetireThreadDumpCount(), true);
            this.computeDiff("LongRunningRetireThreadDumpInterval", this.bean.getLongRunningRetireThreadDumpInterval(), otherTyped.getLongRunningRetireThreadDumpInterval(), true);
            this.computeDiff("LongRunningRetireThreadDumpStartTime", this.bean.getLongRunningRetireThreadDumpStartTime(), otherTyped.getLongRunningRetireThreadDumpStartTime(), true);
            this.computeDiff("MaxAppVersions", this.bean.getMaxAppVersions(), otherTyped.getMaxAppVersions(), true);
            this.computeDiff("MaxRetiredTasks", this.bean.getMaxRetiredTasks(), otherTyped.getMaxRetiredTasks(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RemoteDeployerEJBEnabled", this.bean.isRemoteDeployerEJBEnabled(), otherTyped.isRemoteDeployerEJBEnabled(), false);
            }

            this.computeDiff("RestageOnlyOnRedeploy", this.bean.isRestageOnlyOnRedeploy(), otherTyped.isRestageOnlyOnRedeploy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeploymentConfigurationMBeanImpl original = (DeploymentConfigurationMBeanImpl)event.getSourceBean();
            DeploymentConfigurationMBeanImpl proposed = (DeploymentConfigurationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultMultiVersionAppRetireTimeout")) {
                  original.setDefaultMultiVersionAppRetireTimeout(proposed.getDefaultMultiVersionAppRetireTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("DeploymentConfigOverrides")) {
                  if (type == 2) {
                     original.setDeploymentConfigOverrides((DeploymentConfigOverridesMBean)this.createCopy((AbstractDescriptorBean)proposed.getDeploymentConfigOverrides()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeploymentConfigOverrides", original.getDeploymentConfigOverrides());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("DeploymentServiceMessageRetryCount")) {
                  original.setDeploymentServiceMessageRetryCount(proposed.getDeploymentServiceMessageRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("DeploymentServiceMessageRetryInterval")) {
                  original.setDeploymentServiceMessageRetryInterval(proposed.getDeploymentServiceMessageRetryInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DeploymentValidationPlugin")) {
                  if (type == 2) {
                     original.setDeploymentValidationPlugin((DeploymentValidationPluginMBean)this.createCopy((AbstractDescriptorBean)proposed.getDeploymentValidationPlugin()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeploymentValidationPlugin", original.getDeploymentValidationPlugin());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("LongRunningRetireThreadDumpCount")) {
                  original.setLongRunningRetireThreadDumpCount(proposed.getLongRunningRetireThreadDumpCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("LongRunningRetireThreadDumpInterval")) {
                  original.setLongRunningRetireThreadDumpInterval(proposed.getLongRunningRetireThreadDumpInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("LongRunningRetireThreadDumpStartTime")) {
                  original.setLongRunningRetireThreadDumpStartTime(proposed.getLongRunningRetireThreadDumpStartTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MaxAppVersions")) {
                  original.setMaxAppVersions(proposed.getMaxAppVersions());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MaxRetiredTasks")) {
                  original.setMaxRetiredTasks(proposed.getMaxRetiredTasks());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("RemoteDeployerEJBEnabled")) {
                  original.setRemoteDeployerEJBEnabled(proposed.isRemoteDeployerEJBEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RestageOnlyOnRedeploy")) {
                  original.setRestageOnlyOnRedeploy(proposed.isRestageOnlyOnRedeploy());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else {
                  super.applyPropertyUpdate(event, update);
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
            DeploymentConfigurationMBeanImpl copy = (DeploymentConfigurationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultMultiVersionAppRetireTimeout")) && this.bean.isDefaultMultiVersionAppRetireTimeoutSet()) {
               copy.setDefaultMultiVersionAppRetireTimeout(this.bean.getDefaultMultiVersionAppRetireTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentConfigOverrides")) && this.bean.isDeploymentConfigOverridesSet() && !copy._isSet(20)) {
               Object o = this.bean.getDeploymentConfigOverrides();
               copy.setDeploymentConfigOverrides((DeploymentConfigOverridesMBean)null);
               copy.setDeploymentConfigOverrides(o == null ? null : (DeploymentConfigOverridesMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentServiceMessageRetryCount")) && this.bean.isDeploymentServiceMessageRetryCountSet()) {
               copy.setDeploymentServiceMessageRetryCount(this.bean.getDeploymentServiceMessageRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentServiceMessageRetryInterval")) && this.bean.isDeploymentServiceMessageRetryIntervalSet()) {
               copy.setDeploymentServiceMessageRetryInterval(this.bean.getDeploymentServiceMessageRetryInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentValidationPlugin")) && this.bean.isDeploymentValidationPluginSet() && !copy._isSet(13)) {
               Object o = this.bean.getDeploymentValidationPlugin();
               copy.setDeploymentValidationPlugin((DeploymentValidationPluginMBean)null);
               copy.setDeploymentValidationPlugin(o == null ? null : (DeploymentValidationPluginMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LongRunningRetireThreadDumpCount")) && this.bean.isLongRunningRetireThreadDumpCountSet()) {
               copy.setLongRunningRetireThreadDumpCount(this.bean.getLongRunningRetireThreadDumpCount());
            }

            if ((excludeProps == null || !excludeProps.contains("LongRunningRetireThreadDumpInterval")) && this.bean.isLongRunningRetireThreadDumpIntervalSet()) {
               copy.setLongRunningRetireThreadDumpInterval(this.bean.getLongRunningRetireThreadDumpInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("LongRunningRetireThreadDumpStartTime")) && this.bean.isLongRunningRetireThreadDumpStartTimeSet()) {
               copy.setLongRunningRetireThreadDumpStartTime(this.bean.getLongRunningRetireThreadDumpStartTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxAppVersions")) && this.bean.isMaxAppVersionsSet()) {
               copy.setMaxAppVersions(this.bean.getMaxAppVersions());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRetiredTasks")) && this.bean.isMaxRetiredTasksSet()) {
               copy.setMaxRetiredTasks(this.bean.getMaxRetiredTasks());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RemoteDeployerEJBEnabled")) && this.bean.isRemoteDeployerEJBEnabledSet()) {
               copy.setRemoteDeployerEJBEnabled(this.bean.isRemoteDeployerEJBEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RestageOnlyOnRedeploy")) && this.bean.isRestageOnlyOnRedeploySet()) {
               copy.setRestageOnlyOnRedeploy(this.bean.isRestageOnlyOnRedeploy());
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
         this.inferSubTree(this.bean.getDeploymentConfigOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getDeploymentValidationPlugin(), clazz, annotation);
      }
   }
}
