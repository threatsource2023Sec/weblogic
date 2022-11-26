package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DynamicDeploymentMBeanImpl extends DeploymentMBeanImpl implements DynamicDeploymentMBean, Serializable {
   private String _DistributionPolicy;
   private int _FailOverLimit;
   private long _FailbackDelaySeconds;
   private long _InitialBootDelaySeconds;
   private String _MigrationPolicy;
   private int _NumberOfRestartAttempts;
   private long _PartialClusterStabilityDelaySeconds;
   private boolean _RestartInPlace;
   private int _SecondsBetweenRestarts;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private DynamicDeploymentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(DynamicDeploymentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(DynamicDeploymentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public DynamicDeploymentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(DynamicDeploymentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      DynamicDeploymentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public DynamicDeploymentMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DynamicDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DynamicDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDistributionPolicy() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getDistributionPolicy(), this) : this._DistributionPolicy;
   }

   public boolean isDistributionPolicyInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isDistributionPolicySet() {
      return this._isSet(12);
   }

   public void setDistributionPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Distributed", "Singleton"};
      param0 = LegalChecks.checkInEnum("DistributionPolicy", param0, _set);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._DistributionPolicy;
      this._DistributionPolicy = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMigrationPolicy() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getMigrationPolicy(), this) : this._MigrationPolicy;
   }

   public boolean isMigrationPolicyInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isMigrationPolicySet() {
      return this._isSet(13);
   }

   public void setMigrationPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Off", "On-Failure", "Always"};
      param0 = LegalChecks.checkInEnum("MigrationPolicy", param0, _set);
      boolean wasSet = this._isSet(13);
      String _oldVal = this._MigrationPolicy;
      this._MigrationPolicy = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var5.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getRestartInPlace() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._getDelegateBean().getRestartInPlace();
      } else {
         if (!this._isSet(14)) {
            try {
               return !this.getMigrationPolicy().equals("Off");
            } catch (NullPointerException var2) {
            }
         }

         return this._RestartInPlace;
      }
   }

   public boolean isRestartInPlaceInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isRestartInPlaceSet() {
      return this._isSet(14);
   }

   public void setRestartInPlace(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._RestartInPlace;
      this._RestartInPlace = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSecondsBetweenRestarts() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getSecondsBetweenRestarts() : this._SecondsBetweenRestarts;
   }

   public boolean isSecondsBetweenRestartsInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isSecondsBetweenRestartsSet() {
      return this._isSet(15);
   }

   public void setSecondsBetweenRestarts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("SecondsBetweenRestarts", param0, 1);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._SecondsBetweenRestarts;
      this._SecondsBetweenRestarts = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNumberOfRestartAttempts() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getNumberOfRestartAttempts() : this._NumberOfRestartAttempts;
   }

   public boolean isNumberOfRestartAttemptsInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isNumberOfRestartAttemptsSet() {
      return this._isSet(16);
   }

   public void setNumberOfRestartAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("NumberOfRestartAttempts", param0, -1);
      boolean wasSet = this._isSet(16);
      int _oldVal = this._NumberOfRestartAttempts;
      this._NumberOfRestartAttempts = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public long getInitialBootDelaySeconds() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getInitialBootDelaySeconds() : this._InitialBootDelaySeconds;
   }

   public boolean isInitialBootDelaySecondsInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isInitialBootDelaySecondsSet() {
      return this._isSet(17);
   }

   public void setInitialBootDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      long _oldVal = this._InitialBootDelaySeconds;
      this._InitialBootDelaySeconds = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var6.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public long getPartialClusterStabilityDelaySeconds() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getPartialClusterStabilityDelaySeconds() : this._PartialClusterStabilityDelaySeconds;
   }

   public boolean isPartialClusterStabilityDelaySecondsInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isPartialClusterStabilityDelaySecondsSet() {
      return this._isSet(18);
   }

   public void setPartialClusterStabilityDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      long _oldVal = this._PartialClusterStabilityDelaySeconds;
      this._PartialClusterStabilityDelaySeconds = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var6.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public long getFailbackDelaySeconds() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getFailbackDelaySeconds() : this._FailbackDelaySeconds;
   }

   public boolean isFailbackDelaySecondsInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isFailbackDelaySecondsSet() {
      return this._isSet(19);
   }

   public void setFailbackDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      long _oldVal = this._FailbackDelaySeconds;
      this._FailbackDelaySeconds = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var6.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFailOverLimit() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getFailOverLimit() : this._FailOverLimit;
   }

   public boolean isFailOverLimitInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isFailOverLimitSet() {
      return this._isSet(20);
   }

   public void setFailOverLimit(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("FailOverLimit", param0, -1);
      boolean wasSet = this._isSet(20);
      int _oldVal = this._FailOverLimit;
      this._FailOverLimit = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DynamicDeploymentMBeanImpl source = (DynamicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._DistributionPolicy = "Distributed";
               if (initOne) {
                  break;
               }
            case 20:
               this._FailOverLimit = -1;
               if (initOne) {
                  break;
               }
            case 19:
               this._FailbackDelaySeconds = -1L;
               if (initOne) {
                  break;
               }
            case 17:
               this._InitialBootDelaySeconds = 60L;
               if (initOne) {
                  break;
               }
            case 13:
               this._MigrationPolicy = "Off";
               if (initOne) {
                  break;
               }
            case 16:
               this._NumberOfRestartAttempts = 6;
               if (initOne) {
                  break;
               }
            case 18:
               this._PartialClusterStabilityDelaySeconds = 240L;
               if (initOne) {
                  break;
               }
            case 14:
               this._RestartInPlace = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._SecondsBetweenRestarts = 30;
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
      return "DynamicDeployment";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("DistributionPolicy")) {
         oldVal = this._DistributionPolicy;
         this._DistributionPolicy = (String)v;
         this._postSet(12, oldVal, this._DistributionPolicy);
      } else {
         int oldVal;
         if (name.equals("FailOverLimit")) {
            oldVal = this._FailOverLimit;
            this._FailOverLimit = (Integer)v;
            this._postSet(20, oldVal, this._FailOverLimit);
         } else {
            long oldVal;
            if (name.equals("FailbackDelaySeconds")) {
               oldVal = this._FailbackDelaySeconds;
               this._FailbackDelaySeconds = (Long)v;
               this._postSet(19, oldVal, this._FailbackDelaySeconds);
            } else if (name.equals("InitialBootDelaySeconds")) {
               oldVal = this._InitialBootDelaySeconds;
               this._InitialBootDelaySeconds = (Long)v;
               this._postSet(17, oldVal, this._InitialBootDelaySeconds);
            } else if (name.equals("MigrationPolicy")) {
               oldVal = this._MigrationPolicy;
               this._MigrationPolicy = (String)v;
               this._postSet(13, oldVal, this._MigrationPolicy);
            } else if (name.equals("NumberOfRestartAttempts")) {
               oldVal = this._NumberOfRestartAttempts;
               this._NumberOfRestartAttempts = (Integer)v;
               this._postSet(16, oldVal, this._NumberOfRestartAttempts);
            } else if (name.equals("PartialClusterStabilityDelaySeconds")) {
               oldVal = this._PartialClusterStabilityDelaySeconds;
               this._PartialClusterStabilityDelaySeconds = (Long)v;
               this._postSet(18, oldVal, this._PartialClusterStabilityDelaySeconds);
            } else if (name.equals("RestartInPlace")) {
               boolean oldVal = this._RestartInPlace;
               this._RestartInPlace = (Boolean)v;
               this._postSet(14, oldVal, this._RestartInPlace);
            } else if (name.equals("SecondsBetweenRestarts")) {
               oldVal = this._SecondsBetweenRestarts;
               this._SecondsBetweenRestarts = (Integer)v;
               this._postSet(15, oldVal, this._SecondsBetweenRestarts);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DistributionPolicy")) {
         return this._DistributionPolicy;
      } else if (name.equals("FailOverLimit")) {
         return new Integer(this._FailOverLimit);
      } else if (name.equals("FailbackDelaySeconds")) {
         return new Long(this._FailbackDelaySeconds);
      } else if (name.equals("InitialBootDelaySeconds")) {
         return new Long(this._InitialBootDelaySeconds);
      } else if (name.equals("MigrationPolicy")) {
         return this._MigrationPolicy;
      } else if (name.equals("NumberOfRestartAttempts")) {
         return new Integer(this._NumberOfRestartAttempts);
      } else if (name.equals("PartialClusterStabilityDelaySeconds")) {
         return new Long(this._PartialClusterStabilityDelaySeconds);
      } else if (name.equals("RestartInPlace")) {
         return new Boolean(this._RestartInPlace);
      } else {
         return name.equals("SecondsBetweenRestarts") ? new Integer(this._SecondsBetweenRestarts) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 15:
               if (s.equals("fail-over-limit")) {
                  return 20;
               }
               break;
            case 16:
               if (s.equals("migration-policy")) {
                  return 13;
               }

               if (s.equals("restart-in-place")) {
                  return 14;
               }
            case 17:
            case 18:
            case 20:
            case 21:
            case 23:
            case 25:
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
            default:
               break;
            case 19:
               if (s.equals("distribution-policy")) {
                  return 12;
               }
               break;
            case 22:
               if (s.equals("failback-delay-seconds")) {
                  return 19;
               }
               break;
            case 24:
               if (s.equals("seconds-between-restarts")) {
                  return 15;
               }
               break;
            case 26:
               if (s.equals("initial-boot-delay-seconds")) {
                  return 17;
               }

               if (s.equals("number-of-restart-attempts")) {
                  return 16;
               }
               break;
            case 39:
               if (s.equals("partial-cluster-stability-delay-seconds")) {
                  return 18;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "distribution-policy";
            case 13:
               return "migration-policy";
            case 14:
               return "restart-in-place";
            case 15:
               return "seconds-between-restarts";
            case 16:
               return "number-of-restart-attempts";
            case 17:
               return "initial-boot-delay-seconds";
            case 18:
               return "partial-cluster-stability-delay-seconds";
            case 19:
               return "failback-delay-seconds";
            case 20:
               return "fail-over-limit";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private DynamicDeploymentMBeanImpl bean;

      protected Helper(DynamicDeploymentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "DistributionPolicy";
            case 13:
               return "MigrationPolicy";
            case 14:
               return "RestartInPlace";
            case 15:
               return "SecondsBetweenRestarts";
            case 16:
               return "NumberOfRestartAttempts";
            case 17:
               return "InitialBootDelaySeconds";
            case 18:
               return "PartialClusterStabilityDelaySeconds";
            case 19:
               return "FailbackDelaySeconds";
            case 20:
               return "FailOverLimit";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DistributionPolicy")) {
            return 12;
         } else if (propName.equals("FailOverLimit")) {
            return 20;
         } else if (propName.equals("FailbackDelaySeconds")) {
            return 19;
         } else if (propName.equals("InitialBootDelaySeconds")) {
            return 17;
         } else if (propName.equals("MigrationPolicy")) {
            return 13;
         } else if (propName.equals("NumberOfRestartAttempts")) {
            return 16;
         } else if (propName.equals("PartialClusterStabilityDelaySeconds")) {
            return 18;
         } else if (propName.equals("RestartInPlace")) {
            return 14;
         } else {
            return propName.equals("SecondsBetweenRestarts") ? 15 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isDistributionPolicySet()) {
               buf.append("DistributionPolicy");
               buf.append(String.valueOf(this.bean.getDistributionPolicy()));
            }

            if (this.bean.isFailOverLimitSet()) {
               buf.append("FailOverLimit");
               buf.append(String.valueOf(this.bean.getFailOverLimit()));
            }

            if (this.bean.isFailbackDelaySecondsSet()) {
               buf.append("FailbackDelaySeconds");
               buf.append(String.valueOf(this.bean.getFailbackDelaySeconds()));
            }

            if (this.bean.isInitialBootDelaySecondsSet()) {
               buf.append("InitialBootDelaySeconds");
               buf.append(String.valueOf(this.bean.getInitialBootDelaySeconds()));
            }

            if (this.bean.isMigrationPolicySet()) {
               buf.append("MigrationPolicy");
               buf.append(String.valueOf(this.bean.getMigrationPolicy()));
            }

            if (this.bean.isNumberOfRestartAttemptsSet()) {
               buf.append("NumberOfRestartAttempts");
               buf.append(String.valueOf(this.bean.getNumberOfRestartAttempts()));
            }

            if (this.bean.isPartialClusterStabilityDelaySecondsSet()) {
               buf.append("PartialClusterStabilityDelaySeconds");
               buf.append(String.valueOf(this.bean.getPartialClusterStabilityDelaySeconds()));
            }

            if (this.bean.isRestartInPlaceSet()) {
               buf.append("RestartInPlace");
               buf.append(String.valueOf(this.bean.getRestartInPlace()));
            }

            if (this.bean.isSecondsBetweenRestartsSet()) {
               buf.append("SecondsBetweenRestarts");
               buf.append(String.valueOf(this.bean.getSecondsBetweenRestarts()));
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
            DynamicDeploymentMBeanImpl otherTyped = (DynamicDeploymentMBeanImpl)other;
            this.computeDiff("DistributionPolicy", this.bean.getDistributionPolicy(), otherTyped.getDistributionPolicy(), false);
            this.computeDiff("FailOverLimit", this.bean.getFailOverLimit(), otherTyped.getFailOverLimit(), false);
            this.computeDiff("FailbackDelaySeconds", this.bean.getFailbackDelaySeconds(), otherTyped.getFailbackDelaySeconds(), false);
            this.computeDiff("InitialBootDelaySeconds", this.bean.getInitialBootDelaySeconds(), otherTyped.getInitialBootDelaySeconds(), false);
            this.computeDiff("MigrationPolicy", this.bean.getMigrationPolicy(), otherTyped.getMigrationPolicy(), false);
            this.computeDiff("NumberOfRestartAttempts", this.bean.getNumberOfRestartAttempts(), otherTyped.getNumberOfRestartAttempts(), false);
            this.computeDiff("PartialClusterStabilityDelaySeconds", this.bean.getPartialClusterStabilityDelaySeconds(), otherTyped.getPartialClusterStabilityDelaySeconds(), false);
            this.computeDiff("RestartInPlace", this.bean.getRestartInPlace(), otherTyped.getRestartInPlace(), false);
            this.computeDiff("SecondsBetweenRestarts", this.bean.getSecondsBetweenRestarts(), otherTyped.getSecondsBetweenRestarts(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DynamicDeploymentMBeanImpl original = (DynamicDeploymentMBeanImpl)event.getSourceBean();
            DynamicDeploymentMBeanImpl proposed = (DynamicDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DistributionPolicy")) {
                  original.setDistributionPolicy(proposed.getDistributionPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("FailOverLimit")) {
                  original.setFailOverLimit(proposed.getFailOverLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("FailbackDelaySeconds")) {
                  original.setFailbackDelaySeconds(proposed.getFailbackDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("InitialBootDelaySeconds")) {
                  original.setInitialBootDelaySeconds(proposed.getInitialBootDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MigrationPolicy")) {
                  original.setMigrationPolicy(proposed.getMigrationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("NumberOfRestartAttempts")) {
                  original.setNumberOfRestartAttempts(proposed.getNumberOfRestartAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("PartialClusterStabilityDelaySeconds")) {
                  original.setPartialClusterStabilityDelaySeconds(proposed.getPartialClusterStabilityDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("RestartInPlace")) {
                  original.setRestartInPlace(proposed.getRestartInPlace());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("SecondsBetweenRestarts")) {
                  original.setSecondsBetweenRestarts(proposed.getSecondsBetweenRestarts());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
            DynamicDeploymentMBeanImpl copy = (DynamicDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DistributionPolicy")) && this.bean.isDistributionPolicySet()) {
               copy.setDistributionPolicy(this.bean.getDistributionPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("FailOverLimit")) && this.bean.isFailOverLimitSet()) {
               copy.setFailOverLimit(this.bean.getFailOverLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("FailbackDelaySeconds")) && this.bean.isFailbackDelaySecondsSet()) {
               copy.setFailbackDelaySeconds(this.bean.getFailbackDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialBootDelaySeconds")) && this.bean.isInitialBootDelaySecondsSet()) {
               copy.setInitialBootDelaySeconds(this.bean.getInitialBootDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("MigrationPolicy")) && this.bean.isMigrationPolicySet()) {
               copy.setMigrationPolicy(this.bean.getMigrationPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("NumberOfRestartAttempts")) && this.bean.isNumberOfRestartAttemptsSet()) {
               copy.setNumberOfRestartAttempts(this.bean.getNumberOfRestartAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("PartialClusterStabilityDelaySeconds")) && this.bean.isPartialClusterStabilityDelaySecondsSet()) {
               copy.setPartialClusterStabilityDelaySeconds(this.bean.getPartialClusterStabilityDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartInPlace")) && this.bean.isRestartInPlaceSet()) {
               copy.setRestartInPlace(this.bean.getRestartInPlace());
            }

            if ((excludeProps == null || !excludeProps.contains("SecondsBetweenRestarts")) && this.bean.isSecondsBetweenRestartsSet()) {
               copy.setSecondsBetweenRestarts(this.bean.getSecondsBetweenRestarts());
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
      }
   }
}
