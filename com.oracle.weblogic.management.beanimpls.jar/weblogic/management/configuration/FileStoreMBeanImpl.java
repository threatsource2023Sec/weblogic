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
import weblogic.management.ManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class FileStoreMBeanImpl extends GenericFileStoreMBeanImpl implements FileStoreMBean, Serializable {
   private int _DeploymentOrder;
   private String _DistributionPolicy;
   private int _FailOverLimit;
   private long _FailbackDelaySeconds;
   private long _InitialBootDelaySeconds;
   private String _LogicalName;
   private String _MigrationPolicy;
   private String _Name;
   private int _NumberOfRestartAttempts;
   private long _PartialClusterStabilityDelaySeconds;
   private boolean _RestartInPlace;
   private int _SecondsBetweenRestarts;
   private TargetMBean[] _Targets;
   private String _XAResourceName;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private FileStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(FileStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(FileStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public FileStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(FileStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      FileStoreMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public FileStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public FileStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FileStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDistributionPolicy() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getDistributionPolicy(), this) : this._DistributionPolicy;
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

         return this._Name;
      }
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getTargets() : this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isDistributionPolicyInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isDistributionPolicySet() {
      return this._isSet(22);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isTargetsSet() {
      return this._isSet(20);
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
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 20, param0) {
                  public void resolveReference(Object value) {
                     try {
                        FileStoreMBeanImpl.this.addTarget((TargetMBean)value);
                        FileStoreMBeanImpl.this._getHelper().reorderArrayObjects((Object[])FileStoreMBeanImpl.this._Targets, this.getHandbackObject());
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
         this._initializeProperty(20);
         this._postSet(20, _oldVal, this._Targets);
      }
   }

   public void setDistributionPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Distributed", "Singleton"};
      param0 = LegalChecks.checkInEnum("DistributionPolicy", param0, _set);
      boolean wasSet = this._isSet(22);
      String _oldVal = this._DistributionPolicy;
      this._DistributionPolicy = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
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
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
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
            ResolvedReference _ref = new ResolvedReference(this, 20, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return FileStoreMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(20);
      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         TargetMBean[] _new;
         if (this._isSet(20)) {
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

   public String getMigrationPolicy() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getMigrationPolicy(), this) : this._MigrationPolicy;
   }

   public boolean isMigrationPolicyInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isMigrationPolicySet() {
      return this._isSet(23);
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

   public void setMigrationPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Off", "On-Failure", "Always"};
      param0 = LegalChecks.checkInEnum("MigrationPolicy", param0, _set);
      boolean wasSet = this._isSet(23);
      String _oldVal = this._MigrationPolicy;
      this._MigrationPolicy = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDeploymentOrder() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getDeploymentOrder() : this._DeploymentOrder;
   }

   public String getLogicalName() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._performMacroSubstitution(this._getDelegateBean().getLogicalName(), this) : this._LogicalName;
   }

   public boolean getRestartInPlace() {
      if (!this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24)) {
         return this._getDelegateBean().getRestartInPlace();
      } else {
         if (!this._isSet(24)) {
            try {
               return !this.getMigrationPolicy().equals("Off");
            } catch (NullPointerException var2) {
            }
         }

         return this._RestartInPlace;
      }
   }

   public boolean isDeploymentOrderInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isDeploymentOrderSet() {
      return this._isSet(21);
   }

   public boolean isLogicalNameInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isLogicalNameSet() {
      return this._isSet(31);
   }

   public boolean isRestartInPlaceInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isRestartInPlaceSet() {
      return this._isSet(24);
   }

   public void setDeploymentOrder(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DeploymentOrder", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(21);
      int _oldVal = this._DeploymentOrder;
      this._DeploymentOrder = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public void setLogicalName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      String _oldVal = this._LogicalName;
      this._LogicalName = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public void setRestartInPlace(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      boolean _oldVal = this._RestartInPlace;
      this._RestartInPlace = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSecondsBetweenRestarts() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getSecondsBetweenRestarts() : this._SecondsBetweenRestarts;
   }

   public String getXAResourceName() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._performMacroSubstitution(this._getDelegateBean().getXAResourceName(), this) : this._XAResourceName;
   }

   public boolean isSecondsBetweenRestartsInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isSecondsBetweenRestartsSet() {
      return this._isSet(25);
   }

   public boolean isXAResourceNameInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isXAResourceNameSet() {
      return this._isSet(32);
   }

   public void setSecondsBetweenRestarts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("SecondsBetweenRestarts", param0, 1);
      boolean wasSet = this._isSet(25);
      int _oldVal = this._SecondsBetweenRestarts;
      this._SecondsBetweenRestarts = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public void setXAResourceName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      String _oldVal = this._XAResourceName;
      this._XAResourceName = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNumberOfRestartAttempts() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getNumberOfRestartAttempts() : this._NumberOfRestartAttempts;
   }

   public boolean isNumberOfRestartAttemptsInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isNumberOfRestartAttemptsSet() {
      return this._isSet(26);
   }

   public void setNumberOfRestartAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("NumberOfRestartAttempts", param0, -1);
      boolean wasSet = this._isSet(26);
      int _oldVal = this._NumberOfRestartAttempts;
      this._NumberOfRestartAttempts = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public long getInitialBootDelaySeconds() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().getInitialBootDelaySeconds() : this._InitialBootDelaySeconds;
   }

   public boolean isInitialBootDelaySecondsInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isInitialBootDelaySecondsSet() {
      return this._isSet(27);
   }

   public void setInitialBootDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      long _oldVal = this._InitialBootDelaySeconds;
      this._InitialBootDelaySeconds = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public long getPartialClusterStabilityDelaySeconds() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getPartialClusterStabilityDelaySeconds() : this._PartialClusterStabilityDelaySeconds;
   }

   public boolean isPartialClusterStabilityDelaySecondsInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isPartialClusterStabilityDelaySecondsSet() {
      return this._isSet(28);
   }

   public void setPartialClusterStabilityDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      long _oldVal = this._PartialClusterStabilityDelaySeconds;
      this._PartialClusterStabilityDelaySeconds = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public long getFailbackDelaySeconds() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getFailbackDelaySeconds() : this._FailbackDelaySeconds;
   }

   public boolean isFailbackDelaySecondsInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isFailbackDelaySecondsSet() {
      return this._isSet(29);
   }

   public void setFailbackDelaySeconds(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      long _oldVal = this._FailbackDelaySeconds;
      this._FailbackDelaySeconds = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFailOverLimit() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getFailOverLimit() : this._FailOverLimit;
   }

   public boolean isFailOverLimitInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isFailOverLimitSet() {
      return this._isSet(30);
   }

   public void setFailOverLimit(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("FailOverLimit", param0, -1);
      boolean wasSet = this._isSet(30);
      int _oldVal = this._FailOverLimit;
      this._FailOverLimit = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FileStoreMBeanImpl source = (FileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSLegalHelper.validatePersistentStore(this);
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
         idx = 21;
      }

      try {
         switch (idx) {
            case 21:
               this._DeploymentOrder = 1000;
               if (initOne) {
                  break;
               }
            case 22:
               this._DistributionPolicy = "Distributed";
               if (initOne) {
                  break;
               }
            case 30:
               this._FailOverLimit = -1;
               if (initOne) {
                  break;
               }
            case 29:
               this._FailbackDelaySeconds = -1L;
               if (initOne) {
                  break;
               }
            case 27:
               this._InitialBootDelaySeconds = 60L;
               if (initOne) {
                  break;
               }
            case 31:
               this._LogicalName = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._MigrationPolicy = "Off";
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._NumberOfRestartAttempts = 6;
               if (initOne) {
                  break;
               }
            case 28:
               this._PartialClusterStabilityDelaySeconds = 240L;
               if (initOne) {
                  break;
               }
            case 24:
               this._RestartInPlace = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._SecondsBetweenRestarts = 30;
               if (initOne) {
                  break;
               }
            case 20:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._XAResourceName = null;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
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
      return "FileStore";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("DeploymentOrder")) {
         oldVal = this._DeploymentOrder;
         this._DeploymentOrder = (Integer)v;
         this._postSet(21, oldVal, this._DeploymentOrder);
      } else {
         String oldVal;
         if (name.equals("DistributionPolicy")) {
            oldVal = this._DistributionPolicy;
            this._DistributionPolicy = (String)v;
            this._postSet(22, oldVal, this._DistributionPolicy);
         } else if (name.equals("FailOverLimit")) {
            oldVal = this._FailOverLimit;
            this._FailOverLimit = (Integer)v;
            this._postSet(30, oldVal, this._FailOverLimit);
         } else {
            long oldVal;
            if (name.equals("FailbackDelaySeconds")) {
               oldVal = this._FailbackDelaySeconds;
               this._FailbackDelaySeconds = (Long)v;
               this._postSet(29, oldVal, this._FailbackDelaySeconds);
            } else if (name.equals("InitialBootDelaySeconds")) {
               oldVal = this._InitialBootDelaySeconds;
               this._InitialBootDelaySeconds = (Long)v;
               this._postSet(27, oldVal, this._InitialBootDelaySeconds);
            } else if (name.equals("LogicalName")) {
               oldVal = this._LogicalName;
               this._LogicalName = (String)v;
               this._postSet(31, oldVal, this._LogicalName);
            } else if (name.equals("MigrationPolicy")) {
               oldVal = this._MigrationPolicy;
               this._MigrationPolicy = (String)v;
               this._postSet(23, oldVal, this._MigrationPolicy);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("NumberOfRestartAttempts")) {
               oldVal = this._NumberOfRestartAttempts;
               this._NumberOfRestartAttempts = (Integer)v;
               this._postSet(26, oldVal, this._NumberOfRestartAttempts);
            } else if (name.equals("PartialClusterStabilityDelaySeconds")) {
               oldVal = this._PartialClusterStabilityDelaySeconds;
               this._PartialClusterStabilityDelaySeconds = (Long)v;
               this._postSet(28, oldVal, this._PartialClusterStabilityDelaySeconds);
            } else if (name.equals("RestartInPlace")) {
               boolean oldVal = this._RestartInPlace;
               this._RestartInPlace = (Boolean)v;
               this._postSet(24, oldVal, this._RestartInPlace);
            } else if (name.equals("SecondsBetweenRestarts")) {
               oldVal = this._SecondsBetweenRestarts;
               this._SecondsBetweenRestarts = (Integer)v;
               this._postSet(25, oldVal, this._SecondsBetweenRestarts);
            } else if (name.equals("Targets")) {
               TargetMBean[] oldVal = this._Targets;
               this._Targets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(20, oldVal, this._Targets);
            } else if (name.equals("XAResourceName")) {
               oldVal = this._XAResourceName;
               this._XAResourceName = (String)v;
               this._postSet(32, oldVal, this._XAResourceName);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DeploymentOrder")) {
         return new Integer(this._DeploymentOrder);
      } else if (name.equals("DistributionPolicy")) {
         return this._DistributionPolicy;
      } else if (name.equals("FailOverLimit")) {
         return new Integer(this._FailOverLimit);
      } else if (name.equals("FailbackDelaySeconds")) {
         return new Long(this._FailbackDelaySeconds);
      } else if (name.equals("InitialBootDelaySeconds")) {
         return new Long(this._InitialBootDelaySeconds);
      } else if (name.equals("LogicalName")) {
         return this._LogicalName;
      } else if (name.equals("MigrationPolicy")) {
         return this._MigrationPolicy;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NumberOfRestartAttempts")) {
         return new Integer(this._NumberOfRestartAttempts);
      } else if (name.equals("PartialClusterStabilityDelaySeconds")) {
         return new Long(this._PartialClusterStabilityDelaySeconds);
      } else if (name.equals("RestartInPlace")) {
         return new Boolean(this._RestartInPlace);
      } else if (name.equals("SecondsBetweenRestarts")) {
         return new Integer(this._SecondsBetweenRestarts);
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else {
         return name.equals("XAResourceName") ? this._XAResourceName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends GenericFileStoreMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
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
            case 6:
               if (s.equals("target")) {
                  return 20;
               }
               break;
            case 12:
               if (s.equals("logical-name")) {
                  return 31;
               }
               break;
            case 15:
               if (s.equals("fail-over-limit")) {
                  return 30;
               }
               break;
            case 16:
               if (s.equals("deployment-order")) {
                  return 21;
               }

               if (s.equals("migration-policy")) {
                  return 23;
               }

               if (s.equals("restart-in-place")) {
                  return 24;
               }

               if (s.equals("xa-resource-name")) {
                  return 32;
               }
               break;
            case 19:
               if (s.equals("distribution-policy")) {
                  return 22;
               }
               break;
            case 22:
               if (s.equals("failback-delay-seconds")) {
                  return 29;
               }
               break;
            case 24:
               if (s.equals("seconds-between-restarts")) {
                  return 25;
               }
               break;
            case 26:
               if (s.equals("initial-boot-delay-seconds")) {
                  return 27;
               }

               if (s.equals("number-of-restart-attempts")) {
                  return 26;
               }
               break;
            case 39:
               if (s.equals("partial-cluster-stability-delay-seconds")) {
                  return 28;
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
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               return super.getElementName(propIndex);
            case 20:
               return "target";
            case 21:
               return "deployment-order";
            case 22:
               return "distribution-policy";
            case 23:
               return "migration-policy";
            case 24:
               return "restart-in-place";
            case 25:
               return "seconds-between-restarts";
            case 26:
               return "number-of-restart-attempts";
            case 27:
               return "initial-boot-delay-seconds";
            case 28:
               return "partial-cluster-stability-delay-seconds";
            case 29:
               return "failback-delay-seconds";
            case 30:
               return "fail-over-limit";
            case 31:
               return "logical-name";
            case 32:
               return "xa-resource-name";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 20:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends GenericFileStoreMBeanImpl.Helper {
      private FileStoreMBeanImpl bean;

      protected Helper(FileStoreMBeanImpl bean) {
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
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               return super.getPropertyName(propIndex);
            case 20:
               return "Targets";
            case 21:
               return "DeploymentOrder";
            case 22:
               return "DistributionPolicy";
            case 23:
               return "MigrationPolicy";
            case 24:
               return "RestartInPlace";
            case 25:
               return "SecondsBetweenRestarts";
            case 26:
               return "NumberOfRestartAttempts";
            case 27:
               return "InitialBootDelaySeconds";
            case 28:
               return "PartialClusterStabilityDelaySeconds";
            case 29:
               return "FailbackDelaySeconds";
            case 30:
               return "FailOverLimit";
            case 31:
               return "LogicalName";
            case 32:
               return "XAResourceName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DeploymentOrder")) {
            return 21;
         } else if (propName.equals("DistributionPolicy")) {
            return 22;
         } else if (propName.equals("FailOverLimit")) {
            return 30;
         } else if (propName.equals("FailbackDelaySeconds")) {
            return 29;
         } else if (propName.equals("InitialBootDelaySeconds")) {
            return 27;
         } else if (propName.equals("LogicalName")) {
            return 31;
         } else if (propName.equals("MigrationPolicy")) {
            return 23;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NumberOfRestartAttempts")) {
            return 26;
         } else if (propName.equals("PartialClusterStabilityDelaySeconds")) {
            return 28;
         } else if (propName.equals("RestartInPlace")) {
            return 24;
         } else if (propName.equals("SecondsBetweenRestarts")) {
            return 25;
         } else if (propName.equals("Targets")) {
            return 20;
         } else {
            return propName.equals("XAResourceName") ? 32 : super.getPropertyIndex(propName);
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

            if (this.bean.isLogicalNameSet()) {
               buf.append("LogicalName");
               buf.append(String.valueOf(this.bean.getLogicalName()));
            }

            if (this.bean.isMigrationPolicySet()) {
               buf.append("MigrationPolicy");
               buf.append(String.valueOf(this.bean.getMigrationPolicy()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isXAResourceNameSet()) {
               buf.append("XAResourceName");
               buf.append(String.valueOf(this.bean.getXAResourceName()));
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
            FileStoreMBeanImpl otherTyped = (FileStoreMBeanImpl)other;
            this.computeDiff("DeploymentOrder", this.bean.getDeploymentOrder(), otherTyped.getDeploymentOrder(), true);
            this.computeDiff("DistributionPolicy", this.bean.getDistributionPolicy(), otherTyped.getDistributionPolicy(), false);
            this.computeDiff("FailOverLimit", this.bean.getFailOverLimit(), otherTyped.getFailOverLimit(), false);
            this.computeDiff("FailbackDelaySeconds", this.bean.getFailbackDelaySeconds(), otherTyped.getFailbackDelaySeconds(), false);
            this.computeDiff("InitialBootDelaySeconds", this.bean.getInitialBootDelaySeconds(), otherTyped.getInitialBootDelaySeconds(), false);
            this.computeDiff("LogicalName", this.bean.getLogicalName(), otherTyped.getLogicalName(), true);
            this.computeDiff("MigrationPolicy", this.bean.getMigrationPolicy(), otherTyped.getMigrationPolicy(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NumberOfRestartAttempts", this.bean.getNumberOfRestartAttempts(), otherTyped.getNumberOfRestartAttempts(), false);
            this.computeDiff("PartialClusterStabilityDelaySeconds", this.bean.getPartialClusterStabilityDelaySeconds(), otherTyped.getPartialClusterStabilityDelaySeconds(), false);
            this.computeDiff("RestartInPlace", this.bean.getRestartInPlace(), otherTyped.getRestartInPlace(), false);
            this.computeDiff("SecondsBetweenRestarts", this.bean.getSecondsBetweenRestarts(), otherTyped.getSecondsBetweenRestarts(), false);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("XAResourceName", this.bean.getXAResourceName(), otherTyped.getXAResourceName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FileStoreMBeanImpl original = (FileStoreMBeanImpl)event.getSourceBean();
            FileStoreMBeanImpl proposed = (FileStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DeploymentOrder")) {
                  original.setDeploymentOrder(proposed.getDeploymentOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("DistributionPolicy")) {
                  original.setDistributionPolicy(proposed.getDistributionPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("FailOverLimit")) {
                  original.setFailOverLimit(proposed.getFailOverLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("FailbackDelaySeconds")) {
                  original.setFailbackDelaySeconds(proposed.getFailbackDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("InitialBootDelaySeconds")) {
                  original.setInitialBootDelaySeconds(proposed.getInitialBootDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("LogicalName")) {
                  original.setLogicalName(proposed.getLogicalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("MigrationPolicy")) {
                  original.setMigrationPolicy(proposed.getMigrationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("NumberOfRestartAttempts")) {
                  original.setNumberOfRestartAttempts(proposed.getNumberOfRestartAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("PartialClusterStabilityDelaySeconds")) {
                  original.setPartialClusterStabilityDelaySeconds(proposed.getPartialClusterStabilityDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("RestartInPlace")) {
                  original.setRestartInPlace(proposed.getRestartInPlace());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("SecondsBetweenRestarts")) {
                  original.setSecondsBetweenRestarts(proposed.getSecondsBetweenRestarts());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("Targets")) {
                  original.setTargetsAsString(proposed.getTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("XAResourceName")) {
                  original.setXAResourceName(proposed.getXAResourceName());
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
            FileStoreMBeanImpl copy = (FileStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
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

            if ((excludeProps == null || !excludeProps.contains("LogicalName")) && this.bean.isLogicalNameSet()) {
               copy.setLogicalName(this.bean.getLogicalName());
            }

            if ((excludeProps == null || !excludeProps.contains("MigrationPolicy")) && this.bean.isMigrationPolicySet()) {
               copy.setMigrationPolicy(this.bean.getMigrationPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 20);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("XAResourceName")) && this.bean.isXAResourceNameSet()) {
               copy.setXAResourceName(this.bean.getXAResourceName());
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
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
