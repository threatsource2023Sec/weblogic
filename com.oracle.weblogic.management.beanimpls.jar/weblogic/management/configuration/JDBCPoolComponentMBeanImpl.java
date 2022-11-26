package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
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
import weblogic.management.mbeans.custom.JDBCPoolComponent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JDBCPoolComponentMBeanImpl extends ComponentMBeanImpl implements JDBCPoolComponentMBean, Serializable {
   private TargetMBean[] _ActivatedTargets;
   private ApplicationMBean _Application;
   private int _CacheSize;
   private int _CapacityIncrement;
   private boolean _CheckOnCreateEnabled;
   private boolean _CheckOnReleaseEnabled;
   private boolean _CheckOnReserveEnabled;
   private int _ConnectionCreationRetryFrequencySeconds;
   private int _ConnectionReserveTimeoutSeconds;
   private boolean _DynamicallyCreated;
   private int _HighestNumUnavailable;
   private int _HighestNumWaiters;
   private int _InactiveConnectionTimeoutSeconds;
   private int _InitialCapacity;
   private boolean _LoggingEnabled;
   private int _MaxCapacity;
   private int _MaxIdleTime;
   private String _Name;
   private boolean _ProfilingEnabled;
   private int _ShrinkFrequencySeconds;
   private boolean _ShrinkingEnabled;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private int _TestFrequencySeconds;
   private transient JDBCPoolComponent _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JDBCPoolComponentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JDBCPoolComponentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JDBCPoolComponentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JDBCPoolComponentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JDBCPoolComponentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      JDBCPoolComponentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._Application instanceof ApplicationMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getApplication() != null) {
            this._getReferenceManager().unregisterBean((ApplicationMBeanImpl)oldDelegate.getApplication());
         }

         if (delegate != null && delegate.getApplication() != null) {
            this._getReferenceManager().registerBean((ApplicationMBeanImpl)delegate.getApplication(), false);
         }

         ((ApplicationMBeanImpl)this._Application)._setDelegateBean((ApplicationMBeanImpl)((ApplicationMBeanImpl)(delegate == null ? null : delegate.getApplication())));
      }

   }

   public JDBCPoolComponentMBeanImpl() {
      try {
         this._customizer = new JDBCPoolComponent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JDBCPoolComponentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JDBCPoolComponent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JDBCPoolComponentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JDBCPoolComponent(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ApplicationMBean getApplication() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getApplication() : this._customizer.getApplication();
   }

   public int getInitialCapacity() throws ManagementException {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getInitialCapacity() : this._InitialCapacity;
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

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isApplicationInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isApplicationSet() {
      return this._isSet(12);
   }

   public boolean isInitialCapacityInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isInitialCapacitySet() {
      return this._isSet(15);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
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
                        JDBCPoolComponentMBeanImpl.this.addTarget((TargetMBean)value);
                        JDBCPoolComponentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JDBCPoolComponentMBeanImpl.this._Targets, this.getHandbackObject());
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

   public void setApplication(ApplicationMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setApplication(param0);
   }

   public void setInitialCapacity(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("InitialCapacity", (long)param0, 0L, 2147483647L);
      this._InitialCapacity = param0;
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
         JDBCPoolComponentMBeanImpl source = (JDBCPoolComponentMBeanImpl)var4.next();
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

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return JDBCPoolComponentMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(10);
      TargetMBean[] _oldVal = this.getTargets();
      this._customizer.setTargets(param0);
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JDBCPoolComponentMBeanImpl source = (JDBCPoolComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
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

      return true;
   }

   public int getMaxCapacity() throws ManagementException {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getMaxCapacity() : this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(16);
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

   public void setMaxCapacity(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxCapacity", (long)param0, 1L, 2147483647L);
      this._MaxCapacity = param0;
   }

   public TargetMBean[] getActivatedTargets() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getActivatedTargets() : this._customizer.getActivatedTargets();
   }

   public int getCapacityIncrement() throws ManagementException {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getCapacityIncrement() : this._CapacityIncrement;
   }

   public boolean isActivatedTargetsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isActivatedTargetsSet() {
      return this._isSet(14);
   }

   public boolean isCapacityIncrementInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isCapacityIncrementSet() {
      return this._isSet(17);
   }

   public void addActivatedTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getActivatedTargets(), TargetMBean.class, param0));

         try {
            this.setActivatedTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void setCapacityIncrement(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CapacityIncrement", (long)param0, 1L, 2147483647L);
      this._CapacityIncrement = param0;
   }

   public int getHighestNumWaiters() throws ManagementException {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getHighestNumWaiters() : this._HighestNumWaiters;
   }

   public boolean isHighestNumWaitersInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isHighestNumWaitersSet() {
      return this._isSet(18);
   }

   public void removeActivatedTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getActivatedTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setActivatedTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setActivatedTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ActivatedTargets = (TargetMBean[])param0;
   }

   public void setHighestNumWaiters(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("HighestNumWaiters", (long)param0, 0L, 2147483647L);
      this._HighestNumWaiters = param0;
   }

   public boolean activated(TargetMBean param0) {
      return this._customizer.activated(param0);
   }

   public int getHighestNumUnavailable() throws ManagementException {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getHighestNumUnavailable() : this._HighestNumUnavailable;
   }

   public boolean isHighestNumUnavailableInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isHighestNumUnavailableSet() {
      return this._isSet(19);
   }

   public void refreshDDsIfNeeded(String[] param0) {
      this._customizer.refreshDDsIfNeeded(param0);
   }

   public void setHighestNumUnavailable(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("HighestNumUnavailable", (long)param0, 0L, 2147483647L);
      this._HighestNumUnavailable = param0;
   }

   public int getInactiveConnectionTimeoutSeconds() throws ManagementException {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getInactiveConnectionTimeoutSeconds() : this._InactiveConnectionTimeoutSeconds;
   }

   public boolean isInactiveConnectionTimeoutSecondsInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isInactiveConnectionTimeoutSecondsSet() {
      return this._isSet(20);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setInactiveConnectionTimeoutSeconds(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("InactiveConnectionTimeoutSeconds", (long)param0, 0L, 2147483647L);
      this._InactiveConnectionTimeoutSeconds = param0;
   }

   public int getConnectionCreationRetryFrequencySeconds() throws ManagementException {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getConnectionCreationRetryFrequencySeconds() : this._ConnectionCreationRetryFrequencySeconds;
   }

   public boolean isConnectionCreationRetryFrequencySecondsInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isConnectionCreationRetryFrequencySecondsSet() {
      return this._isSet(21);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setConnectionCreationRetryFrequencySeconds(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ConnectionCreationRetryFrequencySeconds", (long)param0, 600L, 2147483647L);
      this._ConnectionCreationRetryFrequencySeconds = param0;
   }

   public int getConnectionReserveTimeoutSeconds() throws ManagementException {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getConnectionReserveTimeoutSeconds() : this._ConnectionReserveTimeoutSeconds;
   }

   public boolean isConnectionReserveTimeoutSecondsInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isConnectionReserveTimeoutSecondsSet() {
      return this._isSet(22);
   }

   public void setConnectionReserveTimeoutSeconds(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ConnectionReserveTimeoutSeconds", (long)param0, -1L, 2147483647L);
      this._ConnectionReserveTimeoutSeconds = param0;
   }

   public int getShrinkFrequencySeconds() throws ManagementException {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getShrinkFrequencySeconds() : this._ShrinkFrequencySeconds;
   }

   public boolean isShrinkFrequencySecondsInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isShrinkFrequencySecondsSet() {
      return this._isSet(23);
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

   public void setShrinkFrequencySeconds(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ShrinkFrequencySeconds", (long)param0, 0L, 2147483647L);
      this._ShrinkFrequencySeconds = param0;
   }

   public int getTestFrequencySeconds() throws ManagementException {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getTestFrequencySeconds() : this._TestFrequencySeconds;
   }

   public boolean isTestFrequencySecondsInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isTestFrequencySecondsSet() {
      return this._isSet(24);
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

   public void setTestFrequencySeconds(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("TestFrequencySeconds", (long)param0, 0L, 2147483647L);
      this._TestFrequencySeconds = param0;
   }

   public int getCacheSize() throws ManagementException {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getCacheSize() : this._CacheSize;
   }

   public boolean isCacheSizeInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isCacheSizeSet() {
      return this._isSet(25);
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
         JDBCPoolComponentMBeanImpl source = (JDBCPoolComponentMBeanImpl)var4.next();
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

   public void setCacheSize(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CacheSize", (long)param0, 0L, 1024L);
      this._CacheSize = param0;
   }

   public boolean isShrinkingEnabled() throws ManagementException {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().isShrinkingEnabled() : this._ShrinkingEnabled;
   }

   public boolean isShrinkingEnabledInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isShrinkingEnabledSet() {
      return this._isSet(26);
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

   public void setShrinkingEnabled(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ShrinkingEnabled = param0;
   }

   public boolean isCheckOnReserveEnabled() throws ManagementException {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isCheckOnReserveEnabled() : this._CheckOnReserveEnabled;
   }

   public boolean isCheckOnReserveEnabledInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isCheckOnReserveEnabledSet() {
      return this._isSet(27);
   }

   public void setCheckOnReserveEnabled(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._CheckOnReserveEnabled = param0;
   }

   public boolean isCheckOnReleaseEnabled() throws ManagementException {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().isCheckOnReleaseEnabled() : this._CheckOnReleaseEnabled;
   }

   public boolean isCheckOnReleaseEnabledInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isCheckOnReleaseEnabledSet() {
      return this._isSet(28);
   }

   public void setCheckOnReleaseEnabled(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._CheckOnReleaseEnabled = param0;
   }

   public boolean isCheckOnCreateEnabled() throws ManagementException {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().isCheckOnCreateEnabled() : this._CheckOnCreateEnabled;
   }

   public boolean isCheckOnCreateEnabledInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isCheckOnCreateEnabledSet() {
      return this._isSet(29);
   }

   public void setCheckOnCreateEnabled(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._CheckOnCreateEnabled = param0;
   }

   public int getMaxIdleTime() throws ManagementException {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getMaxIdleTime() : this._MaxIdleTime;
   }

   public boolean isMaxIdleTimeInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isMaxIdleTimeSet() {
      return this._isSet(30);
   }

   public void setMaxIdleTime(int param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._MaxIdleTime = param0;
   }

   public boolean isProfilingEnabled() throws ManagementException {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().isProfilingEnabled() : this._ProfilingEnabled;
   }

   public boolean isProfilingEnabledInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isProfilingEnabledSet() {
      return this._isSet(31);
   }

   public void setProfilingEnabled(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ProfilingEnabled = param0;
   }

   public boolean isLoggingEnabled() throws ManagementException {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isLoggingEnabled() : this._LoggingEnabled;
   }

   public boolean isLoggingEnabledInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(32);
   }

   public void setLoggingEnabled(boolean param0) throws ManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LoggingEnabled = param0;
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._ActivatedTargets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setApplication((ApplicationMBean)null);
               if (initOne) {
                  break;
               }
            case 25:
               this._CacheSize = 10;
               if (initOne) {
                  break;
               }
            case 17:
               this._CapacityIncrement = 1;
               if (initOne) {
                  break;
               }
            case 21:
               this._ConnectionCreationRetryFrequencySeconds = 3600;
               if (initOne) {
                  break;
               }
            case 22:
               this._ConnectionReserveTimeoutSeconds = 10;
               if (initOne) {
                  break;
               }
            case 19:
               this._HighestNumUnavailable = 0;
               if (initOne) {
                  break;
               }
            case 18:
               this._HighestNumWaiters = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 20:
               this._InactiveConnectionTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 15:
               this._InitialCapacity = 1;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxCapacity = 15;
               if (initOne) {
                  break;
               }
            case 30:
               this._MaxIdleTime = -1;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 23:
               this._ShrinkFrequencySeconds = 900;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setTargets(new TargetMBean[0]);
               if (initOne) {
                  break;
               }
            case 24:
               this._TestFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 29:
               this._CheckOnCreateEnabled = false;
               if (initOne) {
                  break;
               }
            case 28:
               this._CheckOnReleaseEnabled = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._CheckOnReserveEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 32:
               this._LoggingEnabled = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._ProfilingEnabled = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._ShrinkingEnabled = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            case 13:
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
      return "JDBCPoolComponent";
   }

   public void putValue(String name, Object v) {
      TargetMBean[] oldVal;
      if (name.equals("ActivatedTargets")) {
         oldVal = this._ActivatedTargets;
         this._ActivatedTargets = (TargetMBean[])((TargetMBean[])v);
         this._postSet(14, oldVal, this._ActivatedTargets);
      } else if (name.equals("Application")) {
         ApplicationMBean oldVal = this._Application;
         this._Application = (ApplicationMBean)v;
         this._postSet(12, oldVal, this._Application);
      } else {
         int oldVal;
         if (name.equals("CacheSize")) {
            oldVal = this._CacheSize;
            this._CacheSize = (Integer)v;
            this._postSet(25, oldVal, this._CacheSize);
         } else if (name.equals("CapacityIncrement")) {
            oldVal = this._CapacityIncrement;
            this._CapacityIncrement = (Integer)v;
            this._postSet(17, oldVal, this._CapacityIncrement);
         } else {
            boolean oldVal;
            if (name.equals("CheckOnCreateEnabled")) {
               oldVal = this._CheckOnCreateEnabled;
               this._CheckOnCreateEnabled = (Boolean)v;
               this._postSet(29, oldVal, this._CheckOnCreateEnabled);
            } else if (name.equals("CheckOnReleaseEnabled")) {
               oldVal = this._CheckOnReleaseEnabled;
               this._CheckOnReleaseEnabled = (Boolean)v;
               this._postSet(28, oldVal, this._CheckOnReleaseEnabled);
            } else if (name.equals("CheckOnReserveEnabled")) {
               oldVal = this._CheckOnReserveEnabled;
               this._CheckOnReserveEnabled = (Boolean)v;
               this._postSet(27, oldVal, this._CheckOnReserveEnabled);
            } else if (name.equals("ConnectionCreationRetryFrequencySeconds")) {
               oldVal = this._ConnectionCreationRetryFrequencySeconds;
               this._ConnectionCreationRetryFrequencySeconds = (Integer)v;
               this._postSet(21, oldVal, this._ConnectionCreationRetryFrequencySeconds);
            } else if (name.equals("ConnectionReserveTimeoutSeconds")) {
               oldVal = this._ConnectionReserveTimeoutSeconds;
               this._ConnectionReserveTimeoutSeconds = (Integer)v;
               this._postSet(22, oldVal, this._ConnectionReserveTimeoutSeconds);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("HighestNumUnavailable")) {
               oldVal = this._HighestNumUnavailable;
               this._HighestNumUnavailable = (Integer)v;
               this._postSet(19, oldVal, this._HighestNumUnavailable);
            } else if (name.equals("HighestNumWaiters")) {
               oldVal = this._HighestNumWaiters;
               this._HighestNumWaiters = (Integer)v;
               this._postSet(18, oldVal, this._HighestNumWaiters);
            } else if (name.equals("InactiveConnectionTimeoutSeconds")) {
               oldVal = this._InactiveConnectionTimeoutSeconds;
               this._InactiveConnectionTimeoutSeconds = (Integer)v;
               this._postSet(20, oldVal, this._InactiveConnectionTimeoutSeconds);
            } else if (name.equals("InitialCapacity")) {
               oldVal = this._InitialCapacity;
               this._InitialCapacity = (Integer)v;
               this._postSet(15, oldVal, this._InitialCapacity);
            } else if (name.equals("LoggingEnabled")) {
               oldVal = this._LoggingEnabled;
               this._LoggingEnabled = (Boolean)v;
               this._postSet(32, oldVal, this._LoggingEnabled);
            } else if (name.equals("MaxCapacity")) {
               oldVal = this._MaxCapacity;
               this._MaxCapacity = (Integer)v;
               this._postSet(16, oldVal, this._MaxCapacity);
            } else if (name.equals("MaxIdleTime")) {
               oldVal = this._MaxIdleTime;
               this._MaxIdleTime = (Integer)v;
               this._postSet(30, oldVal, this._MaxIdleTime);
            } else if (name.equals("Name")) {
               String oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("ProfilingEnabled")) {
               oldVal = this._ProfilingEnabled;
               this._ProfilingEnabled = (Boolean)v;
               this._postSet(31, oldVal, this._ProfilingEnabled);
            } else if (name.equals("ShrinkFrequencySeconds")) {
               oldVal = this._ShrinkFrequencySeconds;
               this._ShrinkFrequencySeconds = (Integer)v;
               this._postSet(23, oldVal, this._ShrinkFrequencySeconds);
            } else if (name.equals("ShrinkingEnabled")) {
               oldVal = this._ShrinkingEnabled;
               this._ShrinkingEnabled = (Boolean)v;
               this._postSet(26, oldVal, this._ShrinkingEnabled);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("Targets")) {
               oldVal = this._Targets;
               this._Targets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(10, oldVal, this._Targets);
            } else if (name.equals("TestFrequencySeconds")) {
               oldVal = this._TestFrequencySeconds;
               this._TestFrequencySeconds = (Integer)v;
               this._postSet(24, oldVal, this._TestFrequencySeconds);
            } else if (name.equals("customizer")) {
               JDBCPoolComponent oldVal = this._customizer;
               this._customizer = (JDBCPoolComponent)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ActivatedTargets")) {
         return this._ActivatedTargets;
      } else if (name.equals("Application")) {
         return this._Application;
      } else if (name.equals("CacheSize")) {
         return new Integer(this._CacheSize);
      } else if (name.equals("CapacityIncrement")) {
         return new Integer(this._CapacityIncrement);
      } else if (name.equals("CheckOnCreateEnabled")) {
         return new Boolean(this._CheckOnCreateEnabled);
      } else if (name.equals("CheckOnReleaseEnabled")) {
         return new Boolean(this._CheckOnReleaseEnabled);
      } else if (name.equals("CheckOnReserveEnabled")) {
         return new Boolean(this._CheckOnReserveEnabled);
      } else if (name.equals("ConnectionCreationRetryFrequencySeconds")) {
         return new Integer(this._ConnectionCreationRetryFrequencySeconds);
      } else if (name.equals("ConnectionReserveTimeoutSeconds")) {
         return new Integer(this._ConnectionReserveTimeoutSeconds);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("HighestNumUnavailable")) {
         return new Integer(this._HighestNumUnavailable);
      } else if (name.equals("HighestNumWaiters")) {
         return new Integer(this._HighestNumWaiters);
      } else if (name.equals("InactiveConnectionTimeoutSeconds")) {
         return new Integer(this._InactiveConnectionTimeoutSeconds);
      } else if (name.equals("InitialCapacity")) {
         return new Integer(this._InitialCapacity);
      } else if (name.equals("LoggingEnabled")) {
         return new Boolean(this._LoggingEnabled);
      } else if (name.equals("MaxCapacity")) {
         return new Integer(this._MaxCapacity);
      } else if (name.equals("MaxIdleTime")) {
         return new Integer(this._MaxIdleTime);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("ProfilingEnabled")) {
         return new Boolean(this._ProfilingEnabled);
      } else if (name.equals("ShrinkFrequencySeconds")) {
         return new Integer(this._ShrinkFrequencySeconds);
      } else if (name.equals("ShrinkingEnabled")) {
         return new Boolean(this._ShrinkingEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("TestFrequencySeconds")) {
         return new Integer(this._TestFrequencySeconds);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ComponentMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 9:
            case 14:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 10:
               if (s.equals("cache-size")) {
                  return 25;
               }
               break;
            case 11:
               if (s.equals("application")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("max-capacity")) {
                  return 16;
               }
               break;
            case 13:
               if (s.equals("max-idle-time")) {
                  return 30;
               }
               break;
            case 15:
               if (s.equals("logging-enabled")) {
                  return 32;
               }
               break;
            case 16:
               if (s.equals("activated-target")) {
                  return 14;
               }

               if (s.equals("initial-capacity")) {
                  return 15;
               }
               break;
            case 17:
               if (s.equals("profiling-enabled")) {
                  return 31;
               }

               if (s.equals("shrinking-enabled")) {
                  return 26;
               }
               break;
            case 18:
               if (s.equals("capacity-increment")) {
                  return 17;
               }
               break;
            case 19:
               if (s.equals("highest-num-waiters")) {
                  return 18;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 22:
               if (s.equals("test-frequency-seconds")) {
                  return 24;
               }
               break;
            case 23:
               if (s.equals("highest-num-unavailable")) {
                  return 19;
               }

               if (s.equals("check-on-create-enabled")) {
                  return 29;
               }
               break;
            case 24:
               if (s.equals("shrink-frequency-seconds")) {
                  return 23;
               }

               if (s.equals("check-on-release-enabled")) {
                  return 28;
               }

               if (s.equals("check-on-reserve-enabled")) {
                  return 27;
               }
               break;
            case 34:
               if (s.equals("connection-reserve-timeout-seconds")) {
                  return 22;
               }
               break;
            case 35:
               if (s.equals("inactive-connection-timeout-seconds")) {
                  return 20;
               }
               break;
            case 43:
               if (s.equals("connection-creation-retry-frequency-seconds")) {
                  return 21;
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
            case 8:
            case 11:
            case 13:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "application";
            case 14:
               return "activated-target";
            case 15:
               return "initial-capacity";
            case 16:
               return "max-capacity";
            case 17:
               return "capacity-increment";
            case 18:
               return "highest-num-waiters";
            case 19:
               return "highest-num-unavailable";
            case 20:
               return "inactive-connection-timeout-seconds";
            case 21:
               return "connection-creation-retry-frequency-seconds";
            case 22:
               return "connection-reserve-timeout-seconds";
            case 23:
               return "shrink-frequency-seconds";
            case 24:
               return "test-frequency-seconds";
            case 25:
               return "cache-size";
            case 26:
               return "shrinking-enabled";
            case 27:
               return "check-on-reserve-enabled";
            case 28:
               return "check-on-release-enabled";
            case 29:
               return "check-on-create-enabled";
            case 30:
               return "max-idle-time";
            case 31:
               return "profiling-enabled";
            case 32:
               return "logging-enabled";
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

   protected static class Helper extends ComponentMBeanImpl.Helper {
      private JDBCPoolComponentMBeanImpl bean;

      protected Helper(JDBCPoolComponentMBeanImpl bean) {
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
            case 13:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "Application";
            case 14:
               return "ActivatedTargets";
            case 15:
               return "InitialCapacity";
            case 16:
               return "MaxCapacity";
            case 17:
               return "CapacityIncrement";
            case 18:
               return "HighestNumWaiters";
            case 19:
               return "HighestNumUnavailable";
            case 20:
               return "InactiveConnectionTimeoutSeconds";
            case 21:
               return "ConnectionCreationRetryFrequencySeconds";
            case 22:
               return "ConnectionReserveTimeoutSeconds";
            case 23:
               return "ShrinkFrequencySeconds";
            case 24:
               return "TestFrequencySeconds";
            case 25:
               return "CacheSize";
            case 26:
               return "ShrinkingEnabled";
            case 27:
               return "CheckOnReserveEnabled";
            case 28:
               return "CheckOnReleaseEnabled";
            case 29:
               return "CheckOnCreateEnabled";
            case 30:
               return "MaxIdleTime";
            case 31:
               return "ProfilingEnabled";
            case 32:
               return "LoggingEnabled";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivatedTargets")) {
            return 14;
         } else if (propName.equals("Application")) {
            return 12;
         } else if (propName.equals("CacheSize")) {
            return 25;
         } else if (propName.equals("CapacityIncrement")) {
            return 17;
         } else if (propName.equals("ConnectionCreationRetryFrequencySeconds")) {
            return 21;
         } else if (propName.equals("ConnectionReserveTimeoutSeconds")) {
            return 22;
         } else if (propName.equals("HighestNumUnavailable")) {
            return 19;
         } else if (propName.equals("HighestNumWaiters")) {
            return 18;
         } else if (propName.equals("InactiveConnectionTimeoutSeconds")) {
            return 20;
         } else if (propName.equals("InitialCapacity")) {
            return 15;
         } else if (propName.equals("MaxCapacity")) {
            return 16;
         } else if (propName.equals("MaxIdleTime")) {
            return 30;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("ShrinkFrequencySeconds")) {
            return 23;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("TestFrequencySeconds")) {
            return 24;
         } else if (propName.equals("CheckOnCreateEnabled")) {
            return 29;
         } else if (propName.equals("CheckOnReleaseEnabled")) {
            return 28;
         } else if (propName.equals("CheckOnReserveEnabled")) {
            return 27;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("LoggingEnabled")) {
            return 32;
         } else if (propName.equals("ProfilingEnabled")) {
            return 31;
         } else {
            return propName.equals("ShrinkingEnabled") ? 26 : super.getPropertyIndex(propName);
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
            if (this.bean.isActivatedTargetsSet()) {
               buf.append("ActivatedTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getActivatedTargets())));
            }

            if (this.bean.isApplicationSet()) {
               buf.append("Application");
               buf.append(String.valueOf(this.bean.getApplication()));
            }

            if (this.bean.isCacheSizeSet()) {
               buf.append("CacheSize");
               buf.append(String.valueOf(this.bean.getCacheSize()));
            }

            if (this.bean.isCapacityIncrementSet()) {
               buf.append("CapacityIncrement");
               buf.append(String.valueOf(this.bean.getCapacityIncrement()));
            }

            if (this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               buf.append("ConnectionCreationRetryFrequencySeconds");
               buf.append(String.valueOf(this.bean.getConnectionCreationRetryFrequencySeconds()));
            }

            if (this.bean.isConnectionReserveTimeoutSecondsSet()) {
               buf.append("ConnectionReserveTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getConnectionReserveTimeoutSeconds()));
            }

            if (this.bean.isHighestNumUnavailableSet()) {
               buf.append("HighestNumUnavailable");
               buf.append(String.valueOf(this.bean.getHighestNumUnavailable()));
            }

            if (this.bean.isHighestNumWaitersSet()) {
               buf.append("HighestNumWaiters");
               buf.append(String.valueOf(this.bean.getHighestNumWaiters()));
            }

            if (this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               buf.append("InactiveConnectionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getInactiveConnectionTimeoutSeconds()));
            }

            if (this.bean.isInitialCapacitySet()) {
               buf.append("InitialCapacity");
               buf.append(String.valueOf(this.bean.getInitialCapacity()));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isMaxIdleTimeSet()) {
               buf.append("MaxIdleTime");
               buf.append(String.valueOf(this.bean.getMaxIdleTime()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isShrinkFrequencySecondsSet()) {
               buf.append("ShrinkFrequencySeconds");
               buf.append(String.valueOf(this.bean.getShrinkFrequencySeconds()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isTestFrequencySecondsSet()) {
               buf.append("TestFrequencySeconds");
               buf.append(String.valueOf(this.bean.getTestFrequencySeconds()));
            }

            if (this.bean.isCheckOnCreateEnabledSet()) {
               buf.append("CheckOnCreateEnabled");
               buf.append(String.valueOf(this.bean.isCheckOnCreateEnabled()));
            }

            if (this.bean.isCheckOnReleaseEnabledSet()) {
               buf.append("CheckOnReleaseEnabled");
               buf.append(String.valueOf(this.bean.isCheckOnReleaseEnabled()));
            }

            if (this.bean.isCheckOnReserveEnabledSet()) {
               buf.append("CheckOnReserveEnabled");
               buf.append(String.valueOf(this.bean.isCheckOnReserveEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isLoggingEnabledSet()) {
               buf.append("LoggingEnabled");
               buf.append(String.valueOf(this.bean.isLoggingEnabled()));
            }

            if (this.bean.isProfilingEnabledSet()) {
               buf.append("ProfilingEnabled");
               buf.append(String.valueOf(this.bean.isProfilingEnabled()));
            }

            if (this.bean.isShrinkingEnabledSet()) {
               buf.append("ShrinkingEnabled");
               buf.append(String.valueOf(this.bean.isShrinkingEnabled()));
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
            JDBCPoolComponentMBeanImpl otherTyped = (JDBCPoolComponentMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCPoolComponentMBeanImpl original = (JDBCPoolComponentMBeanImpl)event.getSourceBean();
            JDBCPoolComponentMBeanImpl proposed = (JDBCPoolComponentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ActivatedTargets") && !prop.equals("Application") && !prop.equals("CacheSize") && !prop.equals("CapacityIncrement") && !prop.equals("ConnectionCreationRetryFrequencySeconds") && !prop.equals("ConnectionReserveTimeoutSeconds") && !prop.equals("HighestNumUnavailable") && !prop.equals("HighestNumWaiters") && !prop.equals("InactiveConnectionTimeoutSeconds") && !prop.equals("InitialCapacity") && !prop.equals("MaxCapacity") && !prop.equals("MaxIdleTime")) {
                  if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (!prop.equals("ShrinkFrequencySeconds")) {
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
                     } else if (!prop.equals("TestFrequencySeconds") && !prop.equals("CheckOnCreateEnabled") && !prop.equals("CheckOnReleaseEnabled") && !prop.equals("CheckOnReserveEnabled") && !prop.equals("DynamicallyCreated") && !prop.equals("LoggingEnabled") && !prop.equals("ProfilingEnabled") && !prop.equals("ShrinkingEnabled")) {
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
            JDBCPoolComponentMBeanImpl copy = (JDBCPoolComponentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
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
         this.inferSubTree(this.bean.getActivatedTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getApplication(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
