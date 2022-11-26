package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JDBCStoreMBeanImpl extends GenericJDBCStoreMBeanImpl implements JDBCStoreMBean, Serializable {
   private String _ConnectionCachingPolicy;
   private JDBCSystemResourceMBean _DataSource;
   private int _DeletesPerBatchMaximum;
   private int _DeletesPerStatementMaximum;
   private int _DeploymentOrder;
   private String _DistributionPolicy;
   private int _FailOverLimit;
   private long _FailbackDelaySeconds;
   private long _InitialBootDelaySeconds;
   private int _InsertsPerBatchMaximum;
   private String _LogicalName;
   private String _MigrationPolicy;
   private int _NumberOfRestartAttempts;
   private boolean _OraclePiggybackCommitEnabled;
   private long _PartialClusterStabilityDelaySeconds;
   private int _ReconnectRetryIntervalMillis;
   private int _ReconnectRetryPeriodMillis;
   private boolean _RestartInPlace;
   private int _SecondsBetweenRestarts;
   private TargetMBean[] _Targets;
   private int _ThreeStepThreshold;
   private int _WorkerCount;
   private int _WorkerPreferredBatchSize;
   private String _XAResourceName;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JDBCStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JDBCStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JDBCStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JDBCStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JDBCStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      JDBCStoreMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._DataSource instanceof JDBCSystemResourceMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDataSource() != null) {
            this._getReferenceManager().unregisterBean((JDBCSystemResourceMBeanImpl)oldDelegate.getDataSource());
         }

         if (delegate != null && delegate.getDataSource() != null) {
            this._getReferenceManager().registerBean((JDBCSystemResourceMBeanImpl)delegate.getDataSource(), false);
         }

         ((JDBCSystemResourceMBeanImpl)this._DataSource)._setDelegateBean((JDBCSystemResourceMBeanImpl)((JDBCSystemResourceMBeanImpl)(delegate == null ? null : delegate.getDataSource())));
      }

   }

   public JDBCStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public JDBCSystemResourceMBean getDataSource() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getDataSource() : this._DataSource;
   }

   public String getDataSourceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDataSource();
      return bean == null ? null : bean._getKey().toString();
   }

   public String getDistributionPolicy() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getDistributionPolicy(), this) : this._DistributionPolicy;
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getTargets() : this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isDataSourceInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isDataSourceSet() {
      return this._isSet(25);
   }

   public boolean isDistributionPolicyInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isDistributionPolicySet() {
      return this._isSet(14);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isTargetsSet() {
      return this._isSet(12);
   }

   public void setDataSourceAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JDBCSystemResourceMBean.class, new ReferenceManager.Resolver(this, 25) {
            public void resolveReference(Object value) {
               try {
                  JDBCStoreMBeanImpl.this.setDataSource((JDBCSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JDBCSystemResourceMBean _oldVal = this._DataSource;
         this._initializeProperty(25);
         this._postSet(25, _oldVal, this._DataSource);
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
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 12, param0) {
                  public void resolveReference(Object value) {
                     try {
                        JDBCStoreMBeanImpl.this.addTarget((TargetMBean)value);
                        JDBCStoreMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JDBCStoreMBeanImpl.this._Targets, this.getHandbackObject());
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
         this._initializeProperty(12);
         this._postSet(12, _oldVal, this._Targets);
      }
   }

   public void setDataSource(JDBCSystemResourceMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 25, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return JDBCStoreMBeanImpl.this.getDataSource();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(25);
      JDBCSystemResourceMBean _oldVal = this._DataSource;
      this._DataSource = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public void setDistributionPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Distributed", "Singleton"};
      param0 = LegalChecks.checkInEnum("DistributionPolicy", param0, _set);
      boolean wasSet = this._isSet(14);
      String _oldVal = this._DistributionPolicy;
      this._DistributionPolicy = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));
      JMSLegalHelper.validateSingleServerTargets(this, param0);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 12, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return JDBCStoreMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(12);
      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         TargetMBean[] _new;
         if (this._isSet(12)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

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

      return true;
   }

   public int getDeletesPerBatchMaximum() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getDeletesPerBatchMaximum() : this._DeletesPerBatchMaximum;
   }

   public String getMigrationPolicy() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getMigrationPolicy(), this) : this._MigrationPolicy;
   }

   public boolean isDeletesPerBatchMaximumInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isDeletesPerBatchMaximumSet() {
      return this._isSet(26);
   }

   public boolean isMigrationPolicyInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isMigrationPolicySet() {
      return this._isSet(15);
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setDeletesPerBatchMaximum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DeletesPerBatchMaximum", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(26);
      int _oldVal = this._DeletesPerBatchMaximum;
      this._DeletesPerBatchMaximum = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public void setMigrationPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Off", "On-Failure", "Always"};
      param0 = LegalChecks.checkInEnum("MigrationPolicy", param0, _set);
      boolean wasSet = this._isSet(15);
      String _oldVal = this._MigrationPolicy;
      this._MigrationPolicy = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDeploymentOrder() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getDeploymentOrder() : this._DeploymentOrder;
   }

   public int getInsertsPerBatchMaximum() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().getInsertsPerBatchMaximum() : this._InsertsPerBatchMaximum;
   }

   public String getLogicalName() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getLogicalName(), this) : this._LogicalName;
   }

   public boolean getRestartInPlace() {
      if (!this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16)) {
         return this._getDelegateBean().getRestartInPlace();
      } else {
         if (!this._isSet(16)) {
            try {
               return !this.getMigrationPolicy().equals("Off");
            } catch (NullPointerException var2) {
            }
         }

         return this._RestartInPlace;
      }
   }

   public boolean isDeploymentOrderInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isDeploymentOrderSet() {
      return this._isSet(13);
   }

   public boolean isInsertsPerBatchMaximumInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isInsertsPerBatchMaximumSet() {
      return this._isSet(27);
   }

   public boolean isLogicalNameInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isLogicalNameSet() {
      return this._isSet(23);
   }

   public boolean isRestartInPlaceInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isRestartInPlaceSet() {
      return this._isSet(16);
   }

   public void setDeploymentOrder(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DeploymentOrder", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._DeploymentOrder;
      this._DeploymentOrder = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public void setInsertsPerBatchMaximum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("InsertsPerBatchMaximum", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(27);
      int _oldVal = this._InsertsPerBatchMaximum;
      this._InsertsPerBatchMaximum = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public void setLogicalName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      String _oldVal = this._LogicalName;
      this._LogicalName = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public void setRestartInPlace(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      boolean _oldVal = this._RestartInPlace;
      this._RestartInPlace = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDeletesPerStatementMaximum() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getDeletesPerStatementMaximum() : this._DeletesPerStatementMaximum;
   }

   public int getSecondsBetweenRestarts() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getSecondsBetweenRestarts() : this._SecondsBetweenRestarts;
   }

   public String getXAResourceName() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getXAResourceName(), this) : this._XAResourceName;
   }

   public boolean isDeletesPerStatementMaximumInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isDeletesPerStatementMaximumSet() {
      return this._isSet(28);
   }

   public boolean isSecondsBetweenRestartsInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isSecondsBetweenRestartsSet() {
      return this._isSet(17);
   }

   public boolean isXAResourceNameInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isXAResourceNameSet() {
      return this._isSet(24);
   }

   public void setDeletesPerStatementMaximum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DeletesPerStatementMaximum", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(28);
      int _oldVal = this._DeletesPerStatementMaximum;
      this._DeletesPerStatementMaximum = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSecondsBetweenRestarts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("SecondsBetweenRestarts", param0, 1);
      boolean wasSet = this._isSet(17);
      int _oldVal = this._SecondsBetweenRestarts;
      this._SecondsBetweenRestarts = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public void setXAResourceName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      String _oldVal = this._XAResourceName;
      this._XAResourceName = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNumberOfRestartAttempts() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getNumberOfRestartAttempts() : this._NumberOfRestartAttempts;
   }

   public int getWorkerCount() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getWorkerCount() : this._WorkerCount;
   }

   public boolean isNumberOfRestartAttemptsInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isNumberOfRestartAttemptsSet() {
      return this._isSet(18);
   }

   public boolean isWorkerCountInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isWorkerCountSet() {
      return this._isSet(29);
   }

   public void setNumberOfRestartAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("NumberOfRestartAttempts", param0, -1);
      boolean wasSet = this._isSet(18);
      int _oldVal = this._NumberOfRestartAttempts;
      this._NumberOfRestartAttempts = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public void setWorkerCount(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("WorkerCount", (long)param0, 1L, 1000L);
      JMSLegalHelper.validateWorkerCount(this, param0);
      boolean wasSet = this._isSet(29);
      int _oldVal = this._WorkerCount;
      this._WorkerCount = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public long getInitialBootDelaySeconds() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getInitialBootDelaySeconds() : this._InitialBootDelaySeconds;
   }

   public int getWorkerPreferredBatchSize() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getWorkerPreferredBatchSize() : this._WorkerPreferredBatchSize;
   }

   public boolean isInitialBootDelaySecondsInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isInitialBootDelaySecondsSet() {
      return this._isSet(19);
   }

   public boolean isWorkerPreferredBatchSizeInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isWorkerPreferredBatchSizeSet() {
      return this._isSet(30);
   }

   public void setInitialBootDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      long _oldVal = this._InitialBootDelaySeconds;
      this._InitialBootDelaySeconds = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public void setWorkerPreferredBatchSize(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("WorkerPreferredBatchSize", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(30);
      int _oldVal = this._WorkerPreferredBatchSize;
      this._WorkerPreferredBatchSize = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public long getPartialClusterStabilityDelaySeconds() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getPartialClusterStabilityDelaySeconds() : this._PartialClusterStabilityDelaySeconds;
   }

   public int getThreeStepThreshold() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getThreeStepThreshold() : this._ThreeStepThreshold;
   }

   public boolean isPartialClusterStabilityDelaySecondsInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isPartialClusterStabilityDelaySecondsSet() {
      return this._isSet(20);
   }

   public boolean isThreeStepThresholdInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isThreeStepThresholdSet() {
      return this._isSet(31);
   }

   public void setPartialClusterStabilityDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      long _oldVal = this._PartialClusterStabilityDelaySeconds;
      this._PartialClusterStabilityDelaySeconds = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public void setThreeStepThreshold(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ThreeStepThreshold", (long)param0, 4000L, 2147483647L);
      boolean wasSet = this._isSet(31);
      int _oldVal = this._ThreeStepThreshold;
      this._ThreeStepThreshold = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public long getFailbackDelaySeconds() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getFailbackDelaySeconds() : this._FailbackDelaySeconds;
   }

   public boolean isFailbackDelaySecondsInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isFailbackDelaySecondsSet() {
      return this._isSet(21);
   }

   public void setOraclePiggybackCommitEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      boolean _oldVal = this._OraclePiggybackCommitEnabled;
      this._OraclePiggybackCommitEnabled = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isOraclePiggybackCommitEnabled() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isOraclePiggybackCommitEnabled() : this._OraclePiggybackCommitEnabled;
   }

   public boolean isOraclePiggybackCommitEnabledInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isOraclePiggybackCommitEnabledSet() {
      return this._isSet(32);
   }

   public void setFailbackDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      long _oldVal = this._FailbackDelaySeconds;
      this._FailbackDelaySeconds = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFailOverLimit() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getFailOverLimit() : this._FailOverLimit;
   }

   public boolean isFailOverLimitInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isFailOverLimitSet() {
      return this._isSet(22);
   }

   public void setReconnectRetryPeriodMillis(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectRetryPeriodMillis", (long)param0, 200L, 300000L);
      boolean wasSet = this._isSet(33);
      int _oldVal = this._ReconnectRetryPeriodMillis;
      this._ReconnectRetryPeriodMillis = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public int getReconnectRetryPeriodMillis() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getReconnectRetryPeriodMillis() : this._ReconnectRetryPeriodMillis;
   }

   public boolean isReconnectRetryPeriodMillisInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isReconnectRetryPeriodMillisSet() {
      return this._isSet(33);
   }

   public void setFailOverLimit(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("FailOverLimit", param0, -1);
      boolean wasSet = this._isSet(22);
      int _oldVal = this._FailOverLimit;
      this._FailOverLimit = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public void setReconnectRetryIntervalMillis(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectRetryIntervalMillis", (long)param0, 100L, 10000L);
      boolean wasSet = this._isSet(34);
      int _oldVal = this._ReconnectRetryIntervalMillis;
      this._ReconnectRetryIntervalMillis = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public int getReconnectRetryIntervalMillis() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getReconnectRetryIntervalMillis() : this._ReconnectRetryIntervalMillis;
   }

   public boolean isReconnectRetryIntervalMillisInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isReconnectRetryIntervalMillisSet() {
      return this._isSet(34);
   }

   public String getConnectionCachingPolicy() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionCachingPolicy(), this) : this._ConnectionCachingPolicy;
   }

   public boolean isConnectionCachingPolicyInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isConnectionCachingPolicySet() {
      return this._isSet(35);
   }

   public void setConnectionCachingPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"DEFAULT", "MINIMAL", "NONE"};
      param0 = LegalChecks.checkInEnum("ConnectionCachingPolicy", param0, _set);
      JMSLegalHelper.validateConnectionCachePolicy(this, param0);
      boolean wasSet = this._isSet(35);
      String _oldVal = this._ConnectionCachingPolicy;
      this._ConnectionCachingPolicy = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JDBCStoreMBeanImpl source = (JDBCStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSLegalHelper.validateJDBCStore(this);
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
         idx = 35;
      }

      try {
         switch (idx) {
            case 35:
               this._ConnectionCachingPolicy = "DEFAULT";
               if (initOne) {
                  break;
               }
            case 25:
               this._DataSource = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._DeletesPerBatchMaximum = 20;
               if (initOne) {
                  break;
               }
            case 28:
               this._DeletesPerStatementMaximum = 20;
               if (initOne) {
                  break;
               }
            case 13:
               this._DeploymentOrder = 1000;
               if (initOne) {
                  break;
               }
            case 14:
               this._DistributionPolicy = "Distributed";
               if (initOne) {
                  break;
               }
            case 22:
               this._FailOverLimit = -1;
               if (initOne) {
                  break;
               }
            case 21:
               this._FailbackDelaySeconds = -1L;
               if (initOne) {
                  break;
               }
            case 19:
               this._InitialBootDelaySeconds = 60L;
               if (initOne) {
                  break;
               }
            case 27:
               this._InsertsPerBatchMaximum = 20;
               if (initOne) {
                  break;
               }
            case 23:
               this._LogicalName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._MigrationPolicy = "Off";
               if (initOne) {
                  break;
               }
            case 18:
               this._NumberOfRestartAttempts = 6;
               if (initOne) {
                  break;
               }
            case 20:
               this._PartialClusterStabilityDelaySeconds = 240L;
               if (initOne) {
                  break;
               }
            case 34:
               this._ReconnectRetryIntervalMillis = 200;
               if (initOne) {
                  break;
               }
            case 33:
               this._ReconnectRetryPeriodMillis = 1000;
               if (initOne) {
                  break;
               }
            case 16:
               this._RestartInPlace = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._SecondsBetweenRestarts = 30;
               if (initOne) {
                  break;
               }
            case 12:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._ThreeStepThreshold = 200000;
               if (initOne) {
                  break;
               }
            case 29:
               this._WorkerCount = 1;
               if (initOne) {
                  break;
               }
            case 30:
               this._WorkerPreferredBatchSize = 10;
               if (initOne) {
                  break;
               }
            case 24:
               this._XAResourceName = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._OraclePiggybackCommitEnabled = false;
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
      return "JDBCStore";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ConnectionCachingPolicy")) {
         oldVal = this._ConnectionCachingPolicy;
         this._ConnectionCachingPolicy = (String)v;
         this._postSet(35, oldVal, this._ConnectionCachingPolicy);
      } else if (name.equals("DataSource")) {
         JDBCSystemResourceMBean oldVal = this._DataSource;
         this._DataSource = (JDBCSystemResourceMBean)v;
         this._postSet(25, oldVal, this._DataSource);
      } else {
         int oldVal;
         if (name.equals("DeletesPerBatchMaximum")) {
            oldVal = this._DeletesPerBatchMaximum;
            this._DeletesPerBatchMaximum = (Integer)v;
            this._postSet(26, oldVal, this._DeletesPerBatchMaximum);
         } else if (name.equals("DeletesPerStatementMaximum")) {
            oldVal = this._DeletesPerStatementMaximum;
            this._DeletesPerStatementMaximum = (Integer)v;
            this._postSet(28, oldVal, this._DeletesPerStatementMaximum);
         } else if (name.equals("DeploymentOrder")) {
            oldVal = this._DeploymentOrder;
            this._DeploymentOrder = (Integer)v;
            this._postSet(13, oldVal, this._DeploymentOrder);
         } else if (name.equals("DistributionPolicy")) {
            oldVal = this._DistributionPolicy;
            this._DistributionPolicy = (String)v;
            this._postSet(14, oldVal, this._DistributionPolicy);
         } else if (name.equals("FailOverLimit")) {
            oldVal = this._FailOverLimit;
            this._FailOverLimit = (Integer)v;
            this._postSet(22, oldVal, this._FailOverLimit);
         } else {
            long oldVal;
            if (name.equals("FailbackDelaySeconds")) {
               oldVal = this._FailbackDelaySeconds;
               this._FailbackDelaySeconds = (Long)v;
               this._postSet(21, oldVal, this._FailbackDelaySeconds);
            } else if (name.equals("InitialBootDelaySeconds")) {
               oldVal = this._InitialBootDelaySeconds;
               this._InitialBootDelaySeconds = (Long)v;
               this._postSet(19, oldVal, this._InitialBootDelaySeconds);
            } else if (name.equals("InsertsPerBatchMaximum")) {
               oldVal = this._InsertsPerBatchMaximum;
               this._InsertsPerBatchMaximum = (Integer)v;
               this._postSet(27, oldVal, this._InsertsPerBatchMaximum);
            } else if (name.equals("LogicalName")) {
               oldVal = this._LogicalName;
               this._LogicalName = (String)v;
               this._postSet(23, oldVal, this._LogicalName);
            } else if (name.equals("MigrationPolicy")) {
               oldVal = this._MigrationPolicy;
               this._MigrationPolicy = (String)v;
               this._postSet(15, oldVal, this._MigrationPolicy);
            } else if (name.equals("NumberOfRestartAttempts")) {
               oldVal = this._NumberOfRestartAttempts;
               this._NumberOfRestartAttempts = (Integer)v;
               this._postSet(18, oldVal, this._NumberOfRestartAttempts);
            } else {
               boolean oldVal;
               if (name.equals("OraclePiggybackCommitEnabled")) {
                  oldVal = this._OraclePiggybackCommitEnabled;
                  this._OraclePiggybackCommitEnabled = (Boolean)v;
                  this._postSet(32, oldVal, this._OraclePiggybackCommitEnabled);
               } else if (name.equals("PartialClusterStabilityDelaySeconds")) {
                  oldVal = this._PartialClusterStabilityDelaySeconds;
                  this._PartialClusterStabilityDelaySeconds = (Long)v;
                  this._postSet(20, oldVal, this._PartialClusterStabilityDelaySeconds);
               } else if (name.equals("ReconnectRetryIntervalMillis")) {
                  oldVal = this._ReconnectRetryIntervalMillis;
                  this._ReconnectRetryIntervalMillis = (Integer)v;
                  this._postSet(34, oldVal, this._ReconnectRetryIntervalMillis);
               } else if (name.equals("ReconnectRetryPeriodMillis")) {
                  oldVal = this._ReconnectRetryPeriodMillis;
                  this._ReconnectRetryPeriodMillis = (Integer)v;
                  this._postSet(33, oldVal, this._ReconnectRetryPeriodMillis);
               } else if (name.equals("RestartInPlace")) {
                  oldVal = this._RestartInPlace;
                  this._RestartInPlace = (Boolean)v;
                  this._postSet(16, oldVal, this._RestartInPlace);
               } else if (name.equals("SecondsBetweenRestarts")) {
                  oldVal = this._SecondsBetweenRestarts;
                  this._SecondsBetweenRestarts = (Integer)v;
                  this._postSet(17, oldVal, this._SecondsBetweenRestarts);
               } else if (name.equals("Targets")) {
                  TargetMBean[] oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(12, oldVal, this._Targets);
               } else if (name.equals("ThreeStepThreshold")) {
                  oldVal = this._ThreeStepThreshold;
                  this._ThreeStepThreshold = (Integer)v;
                  this._postSet(31, oldVal, this._ThreeStepThreshold);
               } else if (name.equals("WorkerCount")) {
                  oldVal = this._WorkerCount;
                  this._WorkerCount = (Integer)v;
                  this._postSet(29, oldVal, this._WorkerCount);
               } else if (name.equals("WorkerPreferredBatchSize")) {
                  oldVal = this._WorkerPreferredBatchSize;
                  this._WorkerPreferredBatchSize = (Integer)v;
                  this._postSet(30, oldVal, this._WorkerPreferredBatchSize);
               } else if (name.equals("XAResourceName")) {
                  oldVal = this._XAResourceName;
                  this._XAResourceName = (String)v;
                  this._postSet(24, oldVal, this._XAResourceName);
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionCachingPolicy")) {
         return this._ConnectionCachingPolicy;
      } else if (name.equals("DataSource")) {
         return this._DataSource;
      } else if (name.equals("DeletesPerBatchMaximum")) {
         return new Integer(this._DeletesPerBatchMaximum);
      } else if (name.equals("DeletesPerStatementMaximum")) {
         return new Integer(this._DeletesPerStatementMaximum);
      } else if (name.equals("DeploymentOrder")) {
         return new Integer(this._DeploymentOrder);
      } else if (name.equals("DistributionPolicy")) {
         return this._DistributionPolicy;
      } else if (name.equals("FailOverLimit")) {
         return new Integer(this._FailOverLimit);
      } else if (name.equals("FailbackDelaySeconds")) {
         return new Long(this._FailbackDelaySeconds);
      } else if (name.equals("InitialBootDelaySeconds")) {
         return new Long(this._InitialBootDelaySeconds);
      } else if (name.equals("InsertsPerBatchMaximum")) {
         return new Integer(this._InsertsPerBatchMaximum);
      } else if (name.equals("LogicalName")) {
         return this._LogicalName;
      } else if (name.equals("MigrationPolicy")) {
         return this._MigrationPolicy;
      } else if (name.equals("NumberOfRestartAttempts")) {
         return new Integer(this._NumberOfRestartAttempts);
      } else if (name.equals("OraclePiggybackCommitEnabled")) {
         return new Boolean(this._OraclePiggybackCommitEnabled);
      } else if (name.equals("PartialClusterStabilityDelaySeconds")) {
         return new Long(this._PartialClusterStabilityDelaySeconds);
      } else if (name.equals("ReconnectRetryIntervalMillis")) {
         return new Integer(this._ReconnectRetryIntervalMillis);
      } else if (name.equals("ReconnectRetryPeriodMillis")) {
         return new Integer(this._ReconnectRetryPeriodMillis);
      } else if (name.equals("RestartInPlace")) {
         return new Boolean(this._RestartInPlace);
      } else if (name.equals("SecondsBetweenRestarts")) {
         return new Integer(this._SecondsBetweenRestarts);
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("ThreeStepThreshold")) {
         return new Integer(this._ThreeStepThreshold);
      } else if (name.equals("WorkerCount")) {
         return new Integer(this._WorkerCount);
      } else if (name.equals("WorkerPreferredBatchSize")) {
         return new Integer(this._WorkerPreferredBatchSize);
      } else {
         return name.equals("XAResourceName") ? this._XAResourceName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends GenericJDBCStoreMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("target")) {
                  return 12;
               }
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 17:
            case 18:
            case 21:
            case 23:
            case 28:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            default:
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 25;
               }
               break;
            case 12:
               if (s.equals("logical-name")) {
                  return 23;
               }

               if (s.equals("worker-count")) {
                  return 29;
               }
               break;
            case 15:
               if (s.equals("fail-over-limit")) {
                  return 22;
               }
               break;
            case 16:
               if (s.equals("deployment-order")) {
                  return 13;
               }

               if (s.equals("migration-policy")) {
                  return 15;
               }

               if (s.equals("restart-in-place")) {
                  return 16;
               }

               if (s.equals("xa-resource-name")) {
                  return 24;
               }
               break;
            case 19:
               if (s.equals("distribution-policy")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("three-step-threshold")) {
                  return 31;
               }
               break;
            case 22:
               if (s.equals("failback-delay-seconds")) {
                  return 21;
               }
               break;
            case 24:
               if (s.equals("seconds-between-restarts")) {
                  return 17;
               }
               break;
            case 25:
               if (s.equals("connection-caching-policy")) {
                  return 35;
               }

               if (s.equals("deletes-per-batch-maximum")) {
                  return 26;
               }

               if (s.equals("inserts-per-batch-maximum")) {
                  return 27;
               }
               break;
            case 26:
               if (s.equals("initial-boot-delay-seconds")) {
                  return 19;
               }

               if (s.equals("number-of-restart-attempts")) {
                  return 18;
               }
               break;
            case 27:
               if (s.equals("worker-preferred-batch-size")) {
                  return 30;
               }
               break;
            case 29:
               if (s.equals("deletes-per-statement-maximum")) {
                  return 28;
               }

               if (s.equals("reconnect-retry-period-millis")) {
                  return 33;
               }
               break;
            case 31:
               if (s.equals("reconnect-retry-interval-millis")) {
                  return 34;
               }

               if (s.equals("oracle-piggyback-commit-enabled")) {
                  return 32;
               }
               break;
            case 39:
               if (s.equals("partial-cluster-stability-delay-seconds")) {
                  return 20;
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
               return "target";
            case 13:
               return "deployment-order";
            case 14:
               return "distribution-policy";
            case 15:
               return "migration-policy";
            case 16:
               return "restart-in-place";
            case 17:
               return "seconds-between-restarts";
            case 18:
               return "number-of-restart-attempts";
            case 19:
               return "initial-boot-delay-seconds";
            case 20:
               return "partial-cluster-stability-delay-seconds";
            case 21:
               return "failback-delay-seconds";
            case 22:
               return "fail-over-limit";
            case 23:
               return "logical-name";
            case 24:
               return "xa-resource-name";
            case 25:
               return "data-source";
            case 26:
               return "deletes-per-batch-maximum";
            case 27:
               return "inserts-per-batch-maximum";
            case 28:
               return "deletes-per-statement-maximum";
            case 29:
               return "worker-count";
            case 30:
               return "worker-preferred-batch-size";
            case 31:
               return "three-step-threshold";
            case 32:
               return "oracle-piggyback-commit-enabled";
            case 33:
               return "reconnect-retry-period-millis";
            case 34:
               return "reconnect-retry-interval-millis";
            case 35:
               return "connection-caching-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
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

   protected static class Helper extends GenericJDBCStoreMBeanImpl.Helper {
      private JDBCStoreMBeanImpl bean;

      protected Helper(JDBCStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "Targets";
            case 13:
               return "DeploymentOrder";
            case 14:
               return "DistributionPolicy";
            case 15:
               return "MigrationPolicy";
            case 16:
               return "RestartInPlace";
            case 17:
               return "SecondsBetweenRestarts";
            case 18:
               return "NumberOfRestartAttempts";
            case 19:
               return "InitialBootDelaySeconds";
            case 20:
               return "PartialClusterStabilityDelaySeconds";
            case 21:
               return "FailbackDelaySeconds";
            case 22:
               return "FailOverLimit";
            case 23:
               return "LogicalName";
            case 24:
               return "XAResourceName";
            case 25:
               return "DataSource";
            case 26:
               return "DeletesPerBatchMaximum";
            case 27:
               return "InsertsPerBatchMaximum";
            case 28:
               return "DeletesPerStatementMaximum";
            case 29:
               return "WorkerCount";
            case 30:
               return "WorkerPreferredBatchSize";
            case 31:
               return "ThreeStepThreshold";
            case 32:
               return "OraclePiggybackCommitEnabled";
            case 33:
               return "ReconnectRetryPeriodMillis";
            case 34:
               return "ReconnectRetryIntervalMillis";
            case 35:
               return "ConnectionCachingPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionCachingPolicy")) {
            return 35;
         } else if (propName.equals("DataSource")) {
            return 25;
         } else if (propName.equals("DeletesPerBatchMaximum")) {
            return 26;
         } else if (propName.equals("DeletesPerStatementMaximum")) {
            return 28;
         } else if (propName.equals("DeploymentOrder")) {
            return 13;
         } else if (propName.equals("DistributionPolicy")) {
            return 14;
         } else if (propName.equals("FailOverLimit")) {
            return 22;
         } else if (propName.equals("FailbackDelaySeconds")) {
            return 21;
         } else if (propName.equals("InitialBootDelaySeconds")) {
            return 19;
         } else if (propName.equals("InsertsPerBatchMaximum")) {
            return 27;
         } else if (propName.equals("LogicalName")) {
            return 23;
         } else if (propName.equals("MigrationPolicy")) {
            return 15;
         } else if (propName.equals("NumberOfRestartAttempts")) {
            return 18;
         } else if (propName.equals("PartialClusterStabilityDelaySeconds")) {
            return 20;
         } else if (propName.equals("ReconnectRetryIntervalMillis")) {
            return 34;
         } else if (propName.equals("ReconnectRetryPeriodMillis")) {
            return 33;
         } else if (propName.equals("RestartInPlace")) {
            return 16;
         } else if (propName.equals("SecondsBetweenRestarts")) {
            return 17;
         } else if (propName.equals("Targets")) {
            return 12;
         } else if (propName.equals("ThreeStepThreshold")) {
            return 31;
         } else if (propName.equals("WorkerCount")) {
            return 29;
         } else if (propName.equals("WorkerPreferredBatchSize")) {
            return 30;
         } else if (propName.equals("XAResourceName")) {
            return 24;
         } else {
            return propName.equals("OraclePiggybackCommitEnabled") ? 32 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionCachingPolicySet()) {
               buf.append("ConnectionCachingPolicy");
               buf.append(String.valueOf(this.bean.getConnectionCachingPolicy()));
            }

            if (this.bean.isDataSourceSet()) {
               buf.append("DataSource");
               buf.append(String.valueOf(this.bean.getDataSource()));
            }

            if (this.bean.isDeletesPerBatchMaximumSet()) {
               buf.append("DeletesPerBatchMaximum");
               buf.append(String.valueOf(this.bean.getDeletesPerBatchMaximum()));
            }

            if (this.bean.isDeletesPerStatementMaximumSet()) {
               buf.append("DeletesPerStatementMaximum");
               buf.append(String.valueOf(this.bean.getDeletesPerStatementMaximum()));
            }

            if (this.bean.isDeploymentOrderSet()) {
               buf.append("DeploymentOrder");
               buf.append(String.valueOf(this.bean.getDeploymentOrder()));
            }

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

            if (this.bean.isInsertsPerBatchMaximumSet()) {
               buf.append("InsertsPerBatchMaximum");
               buf.append(String.valueOf(this.bean.getInsertsPerBatchMaximum()));
            }

            if (this.bean.isLogicalNameSet()) {
               buf.append("LogicalName");
               buf.append(String.valueOf(this.bean.getLogicalName()));
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

            if (this.bean.isReconnectRetryIntervalMillisSet()) {
               buf.append("ReconnectRetryIntervalMillis");
               buf.append(String.valueOf(this.bean.getReconnectRetryIntervalMillis()));
            }

            if (this.bean.isReconnectRetryPeriodMillisSet()) {
               buf.append("ReconnectRetryPeriodMillis");
               buf.append(String.valueOf(this.bean.getReconnectRetryPeriodMillis()));
            }

            if (this.bean.isRestartInPlaceSet()) {
               buf.append("RestartInPlace");
               buf.append(String.valueOf(this.bean.getRestartInPlace()));
            }

            if (this.bean.isSecondsBetweenRestartsSet()) {
               buf.append("SecondsBetweenRestarts");
               buf.append(String.valueOf(this.bean.getSecondsBetweenRestarts()));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isThreeStepThresholdSet()) {
               buf.append("ThreeStepThreshold");
               buf.append(String.valueOf(this.bean.getThreeStepThreshold()));
            }

            if (this.bean.isWorkerCountSet()) {
               buf.append("WorkerCount");
               buf.append(String.valueOf(this.bean.getWorkerCount()));
            }

            if (this.bean.isWorkerPreferredBatchSizeSet()) {
               buf.append("WorkerPreferredBatchSize");
               buf.append(String.valueOf(this.bean.getWorkerPreferredBatchSize()));
            }

            if (this.bean.isXAResourceNameSet()) {
               buf.append("XAResourceName");
               buf.append(String.valueOf(this.bean.getXAResourceName()));
            }

            if (this.bean.isOraclePiggybackCommitEnabledSet()) {
               buf.append("OraclePiggybackCommitEnabled");
               buf.append(String.valueOf(this.bean.isOraclePiggybackCommitEnabled()));
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
            JDBCStoreMBeanImpl otherTyped = (JDBCStoreMBeanImpl)other;
            this.computeDiff("ConnectionCachingPolicy", this.bean.getConnectionCachingPolicy(), otherTyped.getConnectionCachingPolicy(), false);
            this.computeDiff("DataSource", this.bean.getDataSource(), otherTyped.getDataSource(), false);
            this.computeDiff("DeletesPerBatchMaximum", this.bean.getDeletesPerBatchMaximum(), otherTyped.getDeletesPerBatchMaximum(), false);
            this.computeDiff("DeletesPerStatementMaximum", this.bean.getDeletesPerStatementMaximum(), otherTyped.getDeletesPerStatementMaximum(), false);
            this.computeDiff("DeploymentOrder", this.bean.getDeploymentOrder(), otherTyped.getDeploymentOrder(), true);
            this.computeDiff("DistributionPolicy", this.bean.getDistributionPolicy(), otherTyped.getDistributionPolicy(), false);
            this.computeDiff("FailOverLimit", this.bean.getFailOverLimit(), otherTyped.getFailOverLimit(), false);
            this.computeDiff("FailbackDelaySeconds", this.bean.getFailbackDelaySeconds(), otherTyped.getFailbackDelaySeconds(), false);
            this.computeDiff("InitialBootDelaySeconds", this.bean.getInitialBootDelaySeconds(), otherTyped.getInitialBootDelaySeconds(), false);
            this.computeDiff("InsertsPerBatchMaximum", this.bean.getInsertsPerBatchMaximum(), otherTyped.getInsertsPerBatchMaximum(), false);
            this.computeDiff("LogicalName", this.bean.getLogicalName(), otherTyped.getLogicalName(), true);
            this.computeDiff("MigrationPolicy", this.bean.getMigrationPolicy(), otherTyped.getMigrationPolicy(), false);
            this.computeDiff("NumberOfRestartAttempts", this.bean.getNumberOfRestartAttempts(), otherTyped.getNumberOfRestartAttempts(), false);
            this.computeDiff("PartialClusterStabilityDelaySeconds", this.bean.getPartialClusterStabilityDelaySeconds(), otherTyped.getPartialClusterStabilityDelaySeconds(), false);
            this.computeDiff("ReconnectRetryIntervalMillis", this.bean.getReconnectRetryIntervalMillis(), otherTyped.getReconnectRetryIntervalMillis(), false);
            this.computeDiff("ReconnectRetryPeriodMillis", this.bean.getReconnectRetryPeriodMillis(), otherTyped.getReconnectRetryPeriodMillis(), false);
            this.computeDiff("RestartInPlace", this.bean.getRestartInPlace(), otherTyped.getRestartInPlace(), false);
            this.computeDiff("SecondsBetweenRestarts", this.bean.getSecondsBetweenRestarts(), otherTyped.getSecondsBetweenRestarts(), false);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("ThreeStepThreshold", this.bean.getThreeStepThreshold(), otherTyped.getThreeStepThreshold(), false);
            this.computeDiff("WorkerCount", this.bean.getWorkerCount(), otherTyped.getWorkerCount(), false);
            this.computeDiff("WorkerPreferredBatchSize", this.bean.getWorkerPreferredBatchSize(), otherTyped.getWorkerPreferredBatchSize(), false);
            this.computeDiff("XAResourceName", this.bean.getXAResourceName(), otherTyped.getXAResourceName(), false);
            this.computeDiff("OraclePiggybackCommitEnabled", this.bean.isOraclePiggybackCommitEnabled(), otherTyped.isOraclePiggybackCommitEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCStoreMBeanImpl original = (JDBCStoreMBeanImpl)event.getSourceBean();
            JDBCStoreMBeanImpl proposed = (JDBCStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionCachingPolicy")) {
                  original.setConnectionCachingPolicy(proposed.getConnectionCachingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("DataSource")) {
                  original.setDataSourceAsString(proposed.getDataSourceAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("DeletesPerBatchMaximum")) {
                  original.setDeletesPerBatchMaximum(proposed.getDeletesPerBatchMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("DeletesPerStatementMaximum")) {
                  original.setDeletesPerStatementMaximum(proposed.getDeletesPerStatementMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("DeploymentOrder")) {
                  original.setDeploymentOrder(proposed.getDeploymentOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DistributionPolicy")) {
                  original.setDistributionPolicy(proposed.getDistributionPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("FailOverLimit")) {
                  original.setFailOverLimit(proposed.getFailOverLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("FailbackDelaySeconds")) {
                  original.setFailbackDelaySeconds(proposed.getFailbackDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("InitialBootDelaySeconds")) {
                  original.setInitialBootDelaySeconds(proposed.getInitialBootDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("InsertsPerBatchMaximum")) {
                  original.setInsertsPerBatchMaximum(proposed.getInsertsPerBatchMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("LogicalName")) {
                  original.setLogicalName(proposed.getLogicalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("MigrationPolicy")) {
                  original.setMigrationPolicy(proposed.getMigrationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("NumberOfRestartAttempts")) {
                  original.setNumberOfRestartAttempts(proposed.getNumberOfRestartAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("PartialClusterStabilityDelaySeconds")) {
                  original.setPartialClusterStabilityDelaySeconds(proposed.getPartialClusterStabilityDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ReconnectRetryIntervalMillis")) {
                  original.setReconnectRetryIntervalMillis(proposed.getReconnectRetryIntervalMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("ReconnectRetryPeriodMillis")) {
                  original.setReconnectRetryPeriodMillis(proposed.getReconnectRetryPeriodMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("RestartInPlace")) {
                  original.setRestartInPlace(proposed.getRestartInPlace());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("SecondsBetweenRestarts")) {
                  original.setSecondsBetweenRestarts(proposed.getSecondsBetweenRestarts());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Targets")) {
                  original.setTargetsAsString(proposed.getTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ThreeStepThreshold")) {
                  original.setThreeStepThreshold(proposed.getThreeStepThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("WorkerCount")) {
                  original.setWorkerCount(proposed.getWorkerCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("WorkerPreferredBatchSize")) {
                  original.setWorkerPreferredBatchSize(proposed.getWorkerPreferredBatchSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("XAResourceName")) {
                  original.setXAResourceName(proposed.getXAResourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("OraclePiggybackCommitEnabled")) {
                  original.setOraclePiggybackCommitEnabled(proposed.isOraclePiggybackCommitEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
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
            JDBCStoreMBeanImpl copy = (JDBCStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionCachingPolicy")) && this.bean.isConnectionCachingPolicySet()) {
               copy.setConnectionCachingPolicy(this.bean.getConnectionCachingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSource")) && this.bean.isDataSourceSet()) {
               copy._unSet(copy, 25);
               copy.setDataSourceAsString(this.bean.getDataSourceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DeletesPerBatchMaximum")) && this.bean.isDeletesPerBatchMaximumSet()) {
               copy.setDeletesPerBatchMaximum(this.bean.getDeletesPerBatchMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("DeletesPerStatementMaximum")) && this.bean.isDeletesPerStatementMaximumSet()) {
               copy.setDeletesPerStatementMaximum(this.bean.getDeletesPerStatementMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentOrder")) && this.bean.isDeploymentOrderSet()) {
               copy.setDeploymentOrder(this.bean.getDeploymentOrder());
            }

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

            if ((excludeProps == null || !excludeProps.contains("InsertsPerBatchMaximum")) && this.bean.isInsertsPerBatchMaximumSet()) {
               copy.setInsertsPerBatchMaximum(this.bean.getInsertsPerBatchMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("LogicalName")) && this.bean.isLogicalNameSet()) {
               copy.setLogicalName(this.bean.getLogicalName());
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

            if ((excludeProps == null || !excludeProps.contains("ReconnectRetryIntervalMillis")) && this.bean.isReconnectRetryIntervalMillisSet()) {
               copy.setReconnectRetryIntervalMillis(this.bean.getReconnectRetryIntervalMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("ReconnectRetryPeriodMillis")) && this.bean.isReconnectRetryPeriodMillisSet()) {
               copy.setReconnectRetryPeriodMillis(this.bean.getReconnectRetryPeriodMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartInPlace")) && this.bean.isRestartInPlaceSet()) {
               copy.setRestartInPlace(this.bean.getRestartInPlace());
            }

            if ((excludeProps == null || !excludeProps.contains("SecondsBetweenRestarts")) && this.bean.isSecondsBetweenRestartsSet()) {
               copy.setSecondsBetweenRestarts(this.bean.getSecondsBetweenRestarts());
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 12);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreeStepThreshold")) && this.bean.isThreeStepThresholdSet()) {
               copy.setThreeStepThreshold(this.bean.getThreeStepThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkerCount")) && this.bean.isWorkerCountSet()) {
               copy.setWorkerCount(this.bean.getWorkerCount());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkerPreferredBatchSize")) && this.bean.isWorkerPreferredBatchSizeSet()) {
               copy.setWorkerPreferredBatchSize(this.bean.getWorkerPreferredBatchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("XAResourceName")) && this.bean.isXAResourceNameSet()) {
               copy.setXAResourceName(this.bean.getXAResourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("OraclePiggybackCommitEnabled")) && this.bean.isOraclePiggybackCommitEnabledSet()) {
               copy.setOraclePiggybackCommitEnabled(this.bean.isOraclePiggybackCommitEnabled());
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
         this.inferSubTree(this.bean.getDataSource(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
