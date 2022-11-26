package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
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
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class MessagingBridgeMBeanImpl extends DynamicDeploymentMBeanImpl implements MessagingBridgeMBean, Serializable {
   private boolean _AsyncDisabled;
   private boolean _AsyncEnabled;
   private long _BatchInterval;
   private int _BatchSize;
   private boolean _DurabilityDisabled;
   private boolean _DurabilityEnabled;
   private String _ForwardingPolicy;
   private int _IdleTimeMaximum;
   private long _MaximumIdleTimeMilliseconds;
   private String _Name;
   private boolean _PreserveMsgProperty;
   private boolean _QOSDegradationAllowed;
   private String _QualityOfService;
   private int _ReconnectDelayIncrease;
   private long _ReconnectDelayIncrement;
   private long _ReconnectDelayInitialMilliseconds;
   private int _ReconnectDelayMaximum;
   private long _ReconnectDelayMaximumMilliseconds;
   private int _ReconnectDelayMinimum;
   private long _ScanUnitMilliseconds;
   private String _ScheduleTime;
   private String _Selector;
   private BridgeDestinationCommonMBean _SourceDestination;
   private boolean _Started;
   private BridgeDestinationCommonMBean _TargetDestination;
   private int _TransactionTimeout;
   private int _TransactionTimeoutSeconds;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private MessagingBridgeMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(MessagingBridgeMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(MessagingBridgeMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public MessagingBridgeMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(MessagingBridgeMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      MessagingBridgeMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._SourceDestination instanceof BridgeDestinationCommonMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getSourceDestination() != null) {
            this._getReferenceManager().unregisterBean((BridgeDestinationCommonMBeanImpl)oldDelegate.getSourceDestination());
         }

         if (delegate != null && delegate.getSourceDestination() != null) {
            this._getReferenceManager().registerBean((BridgeDestinationCommonMBeanImpl)delegate.getSourceDestination(), false);
         }

         ((BridgeDestinationCommonMBeanImpl)this._SourceDestination)._setDelegateBean((BridgeDestinationCommonMBeanImpl)((BridgeDestinationCommonMBeanImpl)(delegate == null ? null : delegate.getSourceDestination())));
      }

      if (this._TargetDestination instanceof BridgeDestinationCommonMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getTargetDestination() != null) {
            this._getReferenceManager().unregisterBean((BridgeDestinationCommonMBeanImpl)oldDelegate.getTargetDestination());
         }

         if (delegate != null && delegate.getTargetDestination() != null) {
            this._getReferenceManager().registerBean((BridgeDestinationCommonMBeanImpl)delegate.getTargetDestination(), false);
         }

         ((BridgeDestinationCommonMBeanImpl)this._TargetDestination)._setDelegateBean((BridgeDestinationCommonMBeanImpl)((BridgeDestinationCommonMBeanImpl)(delegate == null ? null : delegate.getTargetDestination())));
      }

   }

   public MessagingBridgeMBeanImpl() {
      this._initializeProperty(-1);
   }

   public MessagingBridgeMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MessagingBridgeMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
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

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public BridgeDestinationCommonMBean getSourceDestination() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getSourceDestination() : this._SourceDestination;
   }

   public String getSourceDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getSourceDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isSourceDestinationInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isSourceDestinationSet() {
      return this._isSet(21);
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
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSourceDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, BridgeDestinationCommonMBean.class, new ReferenceManager.Resolver(this, 21) {
            public void resolveReference(Object value) {
               try {
                  MessagingBridgeMBeanImpl.this.setSourceDestination((BridgeDestinationCommonMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         BridgeDestinationCommonMBean _oldVal = this._SourceDestination;
         this._initializeProperty(21);
         this._postSet(21, _oldVal, this._SourceDestination);
      }

   }

   public void setSourceDestination(BridgeDestinationCommonMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 21, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return MessagingBridgeMBeanImpl.this.getSourceDestination();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(21);
      BridgeDestinationCommonMBean _oldVal = this._SourceDestination;
      this._SourceDestination = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public BridgeDestinationCommonMBean getTargetDestination() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getTargetDestination() : this._TargetDestination;
   }

   public String getTargetDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTargetDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTargetDestinationInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isTargetDestinationSet() {
      return this._isSet(22);
   }

   public void setTargetDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, BridgeDestinationCommonMBean.class, new ReferenceManager.Resolver(this, 22) {
            public void resolveReference(Object value) {
               try {
                  MessagingBridgeMBeanImpl.this.setTargetDestination((BridgeDestinationCommonMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         BridgeDestinationCommonMBean _oldVal = this._TargetDestination;
         this._initializeProperty(22);
         this._postSet(22, _oldVal, this._TargetDestination);
      }

   }

   public void setTargetDestination(BridgeDestinationCommonMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 22, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return MessagingBridgeMBeanImpl.this.getTargetDestination();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(22);
      BridgeDestinationCommonMBean _oldVal = this._TargetDestination;
      this._TargetDestination = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSelector() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getSelector(), this) : this._Selector;
   }

   public boolean isSelectorInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isSelectorSet() {
      return this._isSet(23);
   }

   public void setSelector(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      String _oldVal = this._Selector;
      this._Selector = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public String getForwardingPolicy() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getForwardingPolicy(), this) : this._ForwardingPolicy;
   }

   public boolean isForwardingPolicyInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isForwardingPolicySet() {
      return this._isSet(24);
   }

   public void setForwardingPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Automatic", "Manual", "Scheduled"};
      param0 = LegalChecks.checkInEnum("ForwardingPolicy", param0, _set);
      this._ForwardingPolicy = param0;
   }

   public String getScheduleTime() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getScheduleTime(), this) : this._ScheduleTime;
   }

   public boolean isScheduleTimeInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isScheduleTimeSet() {
      return this._isSet(25);
   }

   public void setScheduleTime(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ScheduleTime = param0;
   }

   public String getQualityOfService() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._performMacroSubstitution(this._getDelegateBean().getQualityOfService(), this) : this._QualityOfService;
   }

   public boolean isQualityOfServiceInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isQualityOfServiceSet() {
      return this._isSet(26);
   }

   public void setQualityOfService(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Exactly-once", "Atmost-once", "Duplicate-okay"};
      param0 = LegalChecks.checkInEnum("QualityOfService", param0, _set);
      boolean wasSet = this._isSet(26);
      String _oldVal = this._QualityOfService;
      this._QualityOfService = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var5.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isQOSDegradationAllowed() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isQOSDegradationAllowed() : this._QOSDegradationAllowed;
   }

   public boolean isQOSDegradationAllowedInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isQOSDegradationAllowedSet() {
      return this._isSet(27);
   }

   public void setQOSDegradationAllowed(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._QOSDegradationAllowed;
      this._QOSDegradationAllowed = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDurabilityDisabled() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().isDurabilityDisabled() : this._DurabilityDisabled;
   }

   public boolean isDurabilityDisabledInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isDurabilityDisabledSet() {
      return this._isSet(28);
   }

   public void setDurabilityDisabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DurabilityDisabled = param0;
   }

   public boolean isDurabilityEnabled() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().isDurabilityEnabled() : this._DurabilityEnabled;
   }

   public boolean isDurabilityEnabledInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isDurabilityEnabledSet() {
      return this._isSet(29);
   }

   public void setDurabilityEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      boolean _oldVal = this._DurabilityEnabled;
      this._DurabilityEnabled = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public long getReconnectDelayInitialMilliseconds() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getReconnectDelayInitialMilliseconds() : this._ReconnectDelayInitialMilliseconds;
   }

   public boolean isReconnectDelayInitialMillisecondsInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isReconnectDelayInitialMillisecondsSet() {
      return this._isSet(30);
   }

   public void setReconnectDelayInitialMilliseconds(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectDelayInitialMilliseconds", param0, 0L, Long.MAX_VALUE);
      this._ReconnectDelayInitialMilliseconds = param0;
   }

   public int getReconnectDelayMinimum() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getReconnectDelayMinimum() : this._ReconnectDelayMinimum;
   }

   public boolean isReconnectDelayMinimumInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isReconnectDelayMinimumSet() {
      return this._isSet(31);
   }

   public void setReconnectDelayMinimum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectDelayMinimum", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(31);
      int _oldVal = this._ReconnectDelayMinimum;
      this._ReconnectDelayMinimum = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public long getReconnectDelayIncrement() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getReconnectDelayIncrement() : this._ReconnectDelayIncrement;
   }

   public boolean isReconnectDelayIncrementInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isReconnectDelayIncrementSet() {
      return this._isSet(32);
   }

   public void setReconnectDelayIncrement(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectDelayIncrement", param0, 0L, Long.MAX_VALUE);
      this._ReconnectDelayIncrement = param0;
   }

   public int getReconnectDelayIncrease() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getReconnectDelayIncrease() : this._ReconnectDelayIncrease;
   }

   public boolean isReconnectDelayIncreaseInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isReconnectDelayIncreaseSet() {
      return this._isSet(33);
   }

   public void setReconnectDelayIncrease(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectDelayIncrease", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(33);
      int _oldVal = this._ReconnectDelayIncrease;
      this._ReconnectDelayIncrease = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public long getReconnectDelayMaximumMilliseconds() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getReconnectDelayMaximumMilliseconds() : this._ReconnectDelayMaximumMilliseconds;
   }

   public boolean isReconnectDelayMaximumMillisecondsInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isReconnectDelayMaximumMillisecondsSet() {
      return this._isSet(34);
   }

   public void setReconnectDelayMaximumMilliseconds(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectDelayMaximumMilliseconds", param0, 0L, Long.MAX_VALUE);
      this._ReconnectDelayMaximumMilliseconds = param0;
   }

   public int getReconnectDelayMaximum() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().getReconnectDelayMaximum() : this._ReconnectDelayMaximum;
   }

   public boolean isReconnectDelayMaximumInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isReconnectDelayMaximumSet() {
      return this._isSet(35);
   }

   public void setReconnectDelayMaximum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ReconnectDelayMaximum", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(35);
      int _oldVal = this._ReconnectDelayMaximum;
      this._ReconnectDelayMaximum = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMaximumIdleTimeMilliseconds() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().getMaximumIdleTimeMilliseconds() : this._MaximumIdleTimeMilliseconds;
   }

   public boolean isMaximumIdleTimeMillisecondsInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isMaximumIdleTimeMillisecondsSet() {
      return this._isSet(36);
   }

   public void setMaximumIdleTimeMilliseconds(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaximumIdleTimeMilliseconds", param0, 0L, Long.MAX_VALUE);
      this._MaximumIdleTimeMilliseconds = param0;
   }

   public int getIdleTimeMaximum() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getIdleTimeMaximum() : this._IdleTimeMaximum;
   }

   public boolean isIdleTimeMaximumInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isIdleTimeMaximumSet() {
      return this._isSet(37);
   }

   public void setIdleTimeMaximum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("IdleTimeMaximum", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(37);
      int _oldVal = this._IdleTimeMaximum;
      this._IdleTimeMaximum = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public long getScanUnitMilliseconds() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().getScanUnitMilliseconds() : this._ScanUnitMilliseconds;
   }

   public boolean isScanUnitMillisecondsInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isScanUnitMillisecondsSet() {
      return this._isSet(38);
   }

   public void setScanUnitMilliseconds(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ScanUnitMilliseconds", param0, 0L, Long.MAX_VALUE);
      this._ScanUnitMilliseconds = param0;
   }

   public int getTransactionTimeoutSeconds() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getTransactionTimeoutSeconds() : this._TransactionTimeoutSeconds;
   }

   public boolean isTransactionTimeoutSecondsInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isTransactionTimeoutSecondsSet() {
      return this._isSet(39);
   }

   public void setTransactionTimeoutSeconds(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("TransactionTimeoutSeconds", (long)param0, 0L, 2147483647L);
      this._TransactionTimeoutSeconds = param0;
   }

   public int getTransactionTimeout() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().getTransactionTimeout() : this._TransactionTimeout;
   }

   public boolean isTransactionTimeoutInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isTransactionTimeoutSet() {
      return this._isSet(40);
   }

   public void setTransactionTimeout(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("TransactionTimeout", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(40);
      int _oldVal = this._TransactionTimeout;
      this._TransactionTimeout = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAsyncDisabled() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().isAsyncDisabled() : this._AsyncDisabled;
   }

   public boolean isAsyncDisabledInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isAsyncDisabledSet() {
      return this._isSet(41);
   }

   public void setAsyncDisabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AsyncDisabled = param0;
   }

   public boolean isAsyncEnabled() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._getDelegateBean().isAsyncEnabled() : this._AsyncEnabled;
   }

   public boolean isAsyncEnabledInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isAsyncEnabledSet() {
      return this._isSet(42);
   }

   public void setAsyncEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(42);
      boolean _oldVal = this._AsyncEnabled;
      this._AsyncEnabled = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStarted() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._getDelegateBean().isStarted() : this._Started;
   }

   public boolean isStartedInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isStartedSet() {
      return this._isSet(43);
   }

   public void setStarted(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(43);
      boolean _oldVal = this._Started;
      this._Started = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public int getBatchSize() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._getDelegateBean().getBatchSize() : this._BatchSize;
   }

   public boolean isBatchSizeInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isBatchSizeSet() {
      return this._isSet(44);
   }

   public void setBatchSize(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("BatchSize", param0, 0);
      boolean wasSet = this._isSet(44);
      int _oldVal = this._BatchSize;
      this._BatchSize = param0;
      this._postSet(44, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(44)) {
            source._postSetFirePropertyChange(44, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBatchInterval() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._getDelegateBean().getBatchInterval() : this._BatchInterval;
   }

   public boolean isBatchIntervalInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isBatchIntervalSet() {
      return this._isSet(45);
   }

   public void setBatchInterval(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(45);
      long _oldVal = this._BatchInterval;
      this._BatchInterval = param0;
      this._postSet(45, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var6.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getPreserveMsgProperty() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._getDelegateBean().getPreserveMsgProperty() : this._PreserveMsgProperty;
   }

   public boolean isPreserveMsgPropertyInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isPreserveMsgPropertySet() {
      return this._isSet(46);
   }

   public void setPreserveMsgProperty(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(46);
      boolean _oldVal = this._PreserveMsgProperty;
      this._PreserveMsgProperty = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MessagingBridgeMBeanImpl source = (MessagingBridgeMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      BridgeLegalHelper.validateBridge(this);
      BridgeLegalHelper.validateBridgeDestinations(this);
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
         idx = 45;
      }

      try {
         switch (idx) {
            case 45:
               this._BatchInterval = -1L;
               if (initOne) {
                  break;
               }
            case 44:
               this._BatchSize = 10;
               if (initOne) {
                  break;
               }
            case 24:
               this._ForwardingPolicy = "Automatic";
               if (initOne) {
                  break;
               }
            case 37:
               this._IdleTimeMaximum = 60;
               if (initOne) {
                  break;
               }
            case 36:
               this._MaximumIdleTimeMilliseconds = 1800000L;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 46:
               this._PreserveMsgProperty = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._QualityOfService = "Exactly-once";
               if (initOne) {
                  break;
               }
            case 33:
               this._ReconnectDelayIncrease = 5;
               if (initOne) {
                  break;
               }
            case 32:
               this._ReconnectDelayIncrement = 5000L;
               if (initOne) {
                  break;
               }
            case 30:
               this._ReconnectDelayInitialMilliseconds = 15000L;
               if (initOne) {
                  break;
               }
            case 35:
               this._ReconnectDelayMaximum = 60;
               if (initOne) {
                  break;
               }
            case 34:
               this._ReconnectDelayMaximumMilliseconds = 50000L;
               if (initOne) {
                  break;
               }
            case 31:
               this._ReconnectDelayMinimum = 15;
               if (initOne) {
                  break;
               }
            case 38:
               this._ScanUnitMilliseconds = 5000L;
               if (initOne) {
                  break;
               }
            case 25:
               this._ScheduleTime = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._Selector = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._SourceDestination = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._TargetDestination = null;
               if (initOne) {
                  break;
               }
            case 40:
               this._TransactionTimeout = 30;
               if (initOne) {
                  break;
               }
            case 39:
               this._TransactionTimeoutSeconds = 30;
               if (initOne) {
                  break;
               }
            case 41:
               this._AsyncDisabled = false;
               if (initOne) {
                  break;
               }
            case 42:
               this._AsyncEnabled = true;
               if (initOne) {
                  break;
               }
            case 28:
               this._DurabilityDisabled = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._DurabilityEnabled = true;
               if (initOne) {
                  break;
               }
            case 27:
               this._QOSDegradationAllowed = false;
               if (initOne) {
                  break;
               }
            case 43:
               this._Started = true;
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
            case 20:
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
      return "MessagingBridge";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AsyncDisabled")) {
         oldVal = this._AsyncDisabled;
         this._AsyncDisabled = (Boolean)v;
         this._postSet(41, oldVal, this._AsyncDisabled);
      } else if (name.equals("AsyncEnabled")) {
         oldVal = this._AsyncEnabled;
         this._AsyncEnabled = (Boolean)v;
         this._postSet(42, oldVal, this._AsyncEnabled);
      } else {
         long oldVal;
         if (name.equals("BatchInterval")) {
            oldVal = this._BatchInterval;
            this._BatchInterval = (Long)v;
            this._postSet(45, oldVal, this._BatchInterval);
         } else {
            int oldVal;
            if (name.equals("BatchSize")) {
               oldVal = this._BatchSize;
               this._BatchSize = (Integer)v;
               this._postSet(44, oldVal, this._BatchSize);
            } else if (name.equals("DurabilityDisabled")) {
               oldVal = this._DurabilityDisabled;
               this._DurabilityDisabled = (Boolean)v;
               this._postSet(28, oldVal, this._DurabilityDisabled);
            } else if (name.equals("DurabilityEnabled")) {
               oldVal = this._DurabilityEnabled;
               this._DurabilityEnabled = (Boolean)v;
               this._postSet(29, oldVal, this._DurabilityEnabled);
            } else {
               String oldVal;
               if (name.equals("ForwardingPolicy")) {
                  oldVal = this._ForwardingPolicy;
                  this._ForwardingPolicy = (String)v;
                  this._postSet(24, oldVal, this._ForwardingPolicy);
               } else if (name.equals("IdleTimeMaximum")) {
                  oldVal = this._IdleTimeMaximum;
                  this._IdleTimeMaximum = (Integer)v;
                  this._postSet(37, oldVal, this._IdleTimeMaximum);
               } else if (name.equals("MaximumIdleTimeMilliseconds")) {
                  oldVal = this._MaximumIdleTimeMilliseconds;
                  this._MaximumIdleTimeMilliseconds = (Long)v;
                  this._postSet(36, oldVal, this._MaximumIdleTimeMilliseconds);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("PreserveMsgProperty")) {
                  oldVal = this._PreserveMsgProperty;
                  this._PreserveMsgProperty = (Boolean)v;
                  this._postSet(46, oldVal, this._PreserveMsgProperty);
               } else if (name.equals("QOSDegradationAllowed")) {
                  oldVal = this._QOSDegradationAllowed;
                  this._QOSDegradationAllowed = (Boolean)v;
                  this._postSet(27, oldVal, this._QOSDegradationAllowed);
               } else if (name.equals("QualityOfService")) {
                  oldVal = this._QualityOfService;
                  this._QualityOfService = (String)v;
                  this._postSet(26, oldVal, this._QualityOfService);
               } else if (name.equals("ReconnectDelayIncrease")) {
                  oldVal = this._ReconnectDelayIncrease;
                  this._ReconnectDelayIncrease = (Integer)v;
                  this._postSet(33, oldVal, this._ReconnectDelayIncrease);
               } else if (name.equals("ReconnectDelayIncrement")) {
                  oldVal = this._ReconnectDelayIncrement;
                  this._ReconnectDelayIncrement = (Long)v;
                  this._postSet(32, oldVal, this._ReconnectDelayIncrement);
               } else if (name.equals("ReconnectDelayInitialMilliseconds")) {
                  oldVal = this._ReconnectDelayInitialMilliseconds;
                  this._ReconnectDelayInitialMilliseconds = (Long)v;
                  this._postSet(30, oldVal, this._ReconnectDelayInitialMilliseconds);
               } else if (name.equals("ReconnectDelayMaximum")) {
                  oldVal = this._ReconnectDelayMaximum;
                  this._ReconnectDelayMaximum = (Integer)v;
                  this._postSet(35, oldVal, this._ReconnectDelayMaximum);
               } else if (name.equals("ReconnectDelayMaximumMilliseconds")) {
                  oldVal = this._ReconnectDelayMaximumMilliseconds;
                  this._ReconnectDelayMaximumMilliseconds = (Long)v;
                  this._postSet(34, oldVal, this._ReconnectDelayMaximumMilliseconds);
               } else if (name.equals("ReconnectDelayMinimum")) {
                  oldVal = this._ReconnectDelayMinimum;
                  this._ReconnectDelayMinimum = (Integer)v;
                  this._postSet(31, oldVal, this._ReconnectDelayMinimum);
               } else if (name.equals("ScanUnitMilliseconds")) {
                  oldVal = this._ScanUnitMilliseconds;
                  this._ScanUnitMilliseconds = (Long)v;
                  this._postSet(38, oldVal, this._ScanUnitMilliseconds);
               } else if (name.equals("ScheduleTime")) {
                  oldVal = this._ScheduleTime;
                  this._ScheduleTime = (String)v;
                  this._postSet(25, oldVal, this._ScheduleTime);
               } else if (name.equals("Selector")) {
                  oldVal = this._Selector;
                  this._Selector = (String)v;
                  this._postSet(23, oldVal, this._Selector);
               } else {
                  BridgeDestinationCommonMBean oldVal;
                  if (name.equals("SourceDestination")) {
                     oldVal = this._SourceDestination;
                     this._SourceDestination = (BridgeDestinationCommonMBean)v;
                     this._postSet(21, oldVal, this._SourceDestination);
                  } else if (name.equals("Started")) {
                     oldVal = this._Started;
                     this._Started = (Boolean)v;
                     this._postSet(43, oldVal, this._Started);
                  } else if (name.equals("TargetDestination")) {
                     oldVal = this._TargetDestination;
                     this._TargetDestination = (BridgeDestinationCommonMBean)v;
                     this._postSet(22, oldVal, this._TargetDestination);
                  } else if (name.equals("TransactionTimeout")) {
                     oldVal = this._TransactionTimeout;
                     this._TransactionTimeout = (Integer)v;
                     this._postSet(40, oldVal, this._TransactionTimeout);
                  } else if (name.equals("TransactionTimeoutSeconds")) {
                     oldVal = this._TransactionTimeoutSeconds;
                     this._TransactionTimeoutSeconds = (Integer)v;
                     this._postSet(39, oldVal, this._TransactionTimeoutSeconds);
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AsyncDisabled")) {
         return new Boolean(this._AsyncDisabled);
      } else if (name.equals("AsyncEnabled")) {
         return new Boolean(this._AsyncEnabled);
      } else if (name.equals("BatchInterval")) {
         return new Long(this._BatchInterval);
      } else if (name.equals("BatchSize")) {
         return new Integer(this._BatchSize);
      } else if (name.equals("DurabilityDisabled")) {
         return new Boolean(this._DurabilityDisabled);
      } else if (name.equals("DurabilityEnabled")) {
         return new Boolean(this._DurabilityEnabled);
      } else if (name.equals("ForwardingPolicy")) {
         return this._ForwardingPolicy;
      } else if (name.equals("IdleTimeMaximum")) {
         return new Integer(this._IdleTimeMaximum);
      } else if (name.equals("MaximumIdleTimeMilliseconds")) {
         return new Long(this._MaximumIdleTimeMilliseconds);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PreserveMsgProperty")) {
         return new Boolean(this._PreserveMsgProperty);
      } else if (name.equals("QOSDegradationAllowed")) {
         return new Boolean(this._QOSDegradationAllowed);
      } else if (name.equals("QualityOfService")) {
         return this._QualityOfService;
      } else if (name.equals("ReconnectDelayIncrease")) {
         return new Integer(this._ReconnectDelayIncrease);
      } else if (name.equals("ReconnectDelayIncrement")) {
         return new Long(this._ReconnectDelayIncrement);
      } else if (name.equals("ReconnectDelayInitialMilliseconds")) {
         return new Long(this._ReconnectDelayInitialMilliseconds);
      } else if (name.equals("ReconnectDelayMaximum")) {
         return new Integer(this._ReconnectDelayMaximum);
      } else if (name.equals("ReconnectDelayMaximumMilliseconds")) {
         return new Long(this._ReconnectDelayMaximumMilliseconds);
      } else if (name.equals("ReconnectDelayMinimum")) {
         return new Integer(this._ReconnectDelayMinimum);
      } else if (name.equals("ScanUnitMilliseconds")) {
         return new Long(this._ScanUnitMilliseconds);
      } else if (name.equals("ScheduleTime")) {
         return this._ScheduleTime;
      } else if (name.equals("Selector")) {
         return this._Selector;
      } else if (name.equals("SourceDestination")) {
         return this._SourceDestination;
      } else if (name.equals("Started")) {
         return new Boolean(this._Started);
      } else if (name.equals("TargetDestination")) {
         return this._TargetDestination;
      } else if (name.equals("TransactionTimeout")) {
         return new Integer(this._TransactionTimeout);
      } else {
         return name.equals("TransactionTimeoutSeconds") ? new Integer(this._TransactionTimeoutSeconds) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DynamicDeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 9:
            case 11:
            case 12:
            case 15:
            case 16:
            case 20:
            case 26:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            default:
               break;
            case 7:
               if (s.equals("started")) {
                  return 43;
               }
               break;
            case 8:
               if (s.equals("selector")) {
                  return 23;
               }
               break;
            case 10:
               if (s.equals("batch-size")) {
                  return 44;
               }
               break;
            case 13:
               if (s.equals("schedule-time")) {
                  return 25;
               }

               if (s.equals("async-enabled")) {
                  return 42;
               }
               break;
            case 14:
               if (s.equals("batch-interval")) {
                  return 45;
               }

               if (s.equals("async-disabled")) {
                  return 41;
               }
               break;
            case 17:
               if (s.equals("forwarding-policy")) {
                  return 24;
               }

               if (s.equals("idle-time-maximum")) {
                  return 37;
               }
               break;
            case 18:
               if (s.equals("quality-of-service")) {
                  return 26;
               }

               if (s.equals("source-destination")) {
                  return 21;
               }

               if (s.equals("target-destination")) {
                  return 22;
               }

               if (s.equals("durability-enabled")) {
                  return 29;
               }
               break;
            case 19:
               if (s.equals("transaction-timeout")) {
                  return 40;
               }

               if (s.equals("durability-disabled")) {
                  return 28;
               }
               break;
            case 21:
               if (s.equals("preserve-msg-property")) {
                  return 46;
               }
               break;
            case 22:
               if (s.equals("scan-unit-milliseconds")) {
                  return 38;
               }
               break;
            case 23:
               if (s.equals("reconnect-delay-maximum")) {
                  return 35;
               }

               if (s.equals("reconnect-delay-minimum")) {
                  return 31;
               }

               if (s.equals("qos-degradation-allowed")) {
                  return 27;
               }
               break;
            case 24:
               if (s.equals("reconnect-delay-increase")) {
                  return 33;
               }
               break;
            case 25:
               if (s.equals("reconnect-delay-increment")) {
                  return 32;
               }
               break;
            case 27:
               if (s.equals("transaction-timeout-seconds")) {
                  return 39;
               }
               break;
            case 30:
               if (s.equals("maximum-idle-time-milliseconds")) {
                  return 36;
               }
               break;
            case 36:
               if (s.equals("reconnect-delay-initial-milliseconds")) {
                  return 30;
               }

               if (s.equals("reconnect-delay-maximum-milliseconds")) {
                  return 34;
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
            case 20:
            default:
               return super.getElementName(propIndex);
            case 21:
               return "source-destination";
            case 22:
               return "target-destination";
            case 23:
               return "selector";
            case 24:
               return "forwarding-policy";
            case 25:
               return "schedule-time";
            case 26:
               return "quality-of-service";
            case 27:
               return "qos-degradation-allowed";
            case 28:
               return "durability-disabled";
            case 29:
               return "durability-enabled";
            case 30:
               return "reconnect-delay-initial-milliseconds";
            case 31:
               return "reconnect-delay-minimum";
            case 32:
               return "reconnect-delay-increment";
            case 33:
               return "reconnect-delay-increase";
            case 34:
               return "reconnect-delay-maximum-milliseconds";
            case 35:
               return "reconnect-delay-maximum";
            case 36:
               return "maximum-idle-time-milliseconds";
            case 37:
               return "idle-time-maximum";
            case 38:
               return "scan-unit-milliseconds";
            case 39:
               return "transaction-timeout-seconds";
            case 40:
               return "transaction-timeout";
            case 41:
               return "async-disabled";
            case 42:
               return "async-enabled";
            case 43:
               return "started";
            case 44:
               return "batch-size";
            case 45:
               return "batch-interval";
            case 46:
               return "preserve-msg-property";
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DynamicDeploymentMBeanImpl.Helper {
      private MessagingBridgeMBeanImpl bean;

      protected Helper(MessagingBridgeMBeanImpl bean) {
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
            case 20:
            default:
               return super.getPropertyName(propIndex);
            case 21:
               return "SourceDestination";
            case 22:
               return "TargetDestination";
            case 23:
               return "Selector";
            case 24:
               return "ForwardingPolicy";
            case 25:
               return "ScheduleTime";
            case 26:
               return "QualityOfService";
            case 27:
               return "QOSDegradationAllowed";
            case 28:
               return "DurabilityDisabled";
            case 29:
               return "DurabilityEnabled";
            case 30:
               return "ReconnectDelayInitialMilliseconds";
            case 31:
               return "ReconnectDelayMinimum";
            case 32:
               return "ReconnectDelayIncrement";
            case 33:
               return "ReconnectDelayIncrease";
            case 34:
               return "ReconnectDelayMaximumMilliseconds";
            case 35:
               return "ReconnectDelayMaximum";
            case 36:
               return "MaximumIdleTimeMilliseconds";
            case 37:
               return "IdleTimeMaximum";
            case 38:
               return "ScanUnitMilliseconds";
            case 39:
               return "TransactionTimeoutSeconds";
            case 40:
               return "TransactionTimeout";
            case 41:
               return "AsyncDisabled";
            case 42:
               return "AsyncEnabled";
            case 43:
               return "Started";
            case 44:
               return "BatchSize";
            case 45:
               return "BatchInterval";
            case 46:
               return "PreserveMsgProperty";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BatchInterval")) {
            return 45;
         } else if (propName.equals("BatchSize")) {
            return 44;
         } else if (propName.equals("ForwardingPolicy")) {
            return 24;
         } else if (propName.equals("IdleTimeMaximum")) {
            return 37;
         } else if (propName.equals("MaximumIdleTimeMilliseconds")) {
            return 36;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PreserveMsgProperty")) {
            return 46;
         } else if (propName.equals("QualityOfService")) {
            return 26;
         } else if (propName.equals("ReconnectDelayIncrease")) {
            return 33;
         } else if (propName.equals("ReconnectDelayIncrement")) {
            return 32;
         } else if (propName.equals("ReconnectDelayInitialMilliseconds")) {
            return 30;
         } else if (propName.equals("ReconnectDelayMaximum")) {
            return 35;
         } else if (propName.equals("ReconnectDelayMaximumMilliseconds")) {
            return 34;
         } else if (propName.equals("ReconnectDelayMinimum")) {
            return 31;
         } else if (propName.equals("ScanUnitMilliseconds")) {
            return 38;
         } else if (propName.equals("ScheduleTime")) {
            return 25;
         } else if (propName.equals("Selector")) {
            return 23;
         } else if (propName.equals("SourceDestination")) {
            return 21;
         } else if (propName.equals("TargetDestination")) {
            return 22;
         } else if (propName.equals("TransactionTimeout")) {
            return 40;
         } else if (propName.equals("TransactionTimeoutSeconds")) {
            return 39;
         } else if (propName.equals("AsyncDisabled")) {
            return 41;
         } else if (propName.equals("AsyncEnabled")) {
            return 42;
         } else if (propName.equals("DurabilityDisabled")) {
            return 28;
         } else if (propName.equals("DurabilityEnabled")) {
            return 29;
         } else if (propName.equals("QOSDegradationAllowed")) {
            return 27;
         } else {
            return propName.equals("Started") ? 43 : super.getPropertyIndex(propName);
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
            if (this.bean.isBatchIntervalSet()) {
               buf.append("BatchInterval");
               buf.append(String.valueOf(this.bean.getBatchInterval()));
            }

            if (this.bean.isBatchSizeSet()) {
               buf.append("BatchSize");
               buf.append(String.valueOf(this.bean.getBatchSize()));
            }

            if (this.bean.isForwardingPolicySet()) {
               buf.append("ForwardingPolicy");
               buf.append(String.valueOf(this.bean.getForwardingPolicy()));
            }

            if (this.bean.isIdleTimeMaximumSet()) {
               buf.append("IdleTimeMaximum");
               buf.append(String.valueOf(this.bean.getIdleTimeMaximum()));
            }

            if (this.bean.isMaximumIdleTimeMillisecondsSet()) {
               buf.append("MaximumIdleTimeMilliseconds");
               buf.append(String.valueOf(this.bean.getMaximumIdleTimeMilliseconds()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPreserveMsgPropertySet()) {
               buf.append("PreserveMsgProperty");
               buf.append(String.valueOf(this.bean.getPreserveMsgProperty()));
            }

            if (this.bean.isQualityOfServiceSet()) {
               buf.append("QualityOfService");
               buf.append(String.valueOf(this.bean.getQualityOfService()));
            }

            if (this.bean.isReconnectDelayIncreaseSet()) {
               buf.append("ReconnectDelayIncrease");
               buf.append(String.valueOf(this.bean.getReconnectDelayIncrease()));
            }

            if (this.bean.isReconnectDelayIncrementSet()) {
               buf.append("ReconnectDelayIncrement");
               buf.append(String.valueOf(this.bean.getReconnectDelayIncrement()));
            }

            if (this.bean.isReconnectDelayInitialMillisecondsSet()) {
               buf.append("ReconnectDelayInitialMilliseconds");
               buf.append(String.valueOf(this.bean.getReconnectDelayInitialMilliseconds()));
            }

            if (this.bean.isReconnectDelayMaximumSet()) {
               buf.append("ReconnectDelayMaximum");
               buf.append(String.valueOf(this.bean.getReconnectDelayMaximum()));
            }

            if (this.bean.isReconnectDelayMaximumMillisecondsSet()) {
               buf.append("ReconnectDelayMaximumMilliseconds");
               buf.append(String.valueOf(this.bean.getReconnectDelayMaximumMilliseconds()));
            }

            if (this.bean.isReconnectDelayMinimumSet()) {
               buf.append("ReconnectDelayMinimum");
               buf.append(String.valueOf(this.bean.getReconnectDelayMinimum()));
            }

            if (this.bean.isScanUnitMillisecondsSet()) {
               buf.append("ScanUnitMilliseconds");
               buf.append(String.valueOf(this.bean.getScanUnitMilliseconds()));
            }

            if (this.bean.isScheduleTimeSet()) {
               buf.append("ScheduleTime");
               buf.append(String.valueOf(this.bean.getScheduleTime()));
            }

            if (this.bean.isSelectorSet()) {
               buf.append("Selector");
               buf.append(String.valueOf(this.bean.getSelector()));
            }

            if (this.bean.isSourceDestinationSet()) {
               buf.append("SourceDestination");
               buf.append(String.valueOf(this.bean.getSourceDestination()));
            }

            if (this.bean.isTargetDestinationSet()) {
               buf.append("TargetDestination");
               buf.append(String.valueOf(this.bean.getTargetDestination()));
            }

            if (this.bean.isTransactionTimeoutSet()) {
               buf.append("TransactionTimeout");
               buf.append(String.valueOf(this.bean.getTransactionTimeout()));
            }

            if (this.bean.isTransactionTimeoutSecondsSet()) {
               buf.append("TransactionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getTransactionTimeoutSeconds()));
            }

            if (this.bean.isAsyncDisabledSet()) {
               buf.append("AsyncDisabled");
               buf.append(String.valueOf(this.bean.isAsyncDisabled()));
            }

            if (this.bean.isAsyncEnabledSet()) {
               buf.append("AsyncEnabled");
               buf.append(String.valueOf(this.bean.isAsyncEnabled()));
            }

            if (this.bean.isDurabilityDisabledSet()) {
               buf.append("DurabilityDisabled");
               buf.append(String.valueOf(this.bean.isDurabilityDisabled()));
            }

            if (this.bean.isDurabilityEnabledSet()) {
               buf.append("DurabilityEnabled");
               buf.append(String.valueOf(this.bean.isDurabilityEnabled()));
            }

            if (this.bean.isQOSDegradationAllowedSet()) {
               buf.append("QOSDegradationAllowed");
               buf.append(String.valueOf(this.bean.isQOSDegradationAllowed()));
            }

            if (this.bean.isStartedSet()) {
               buf.append("Started");
               buf.append(String.valueOf(this.bean.isStarted()));
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
            MessagingBridgeMBeanImpl otherTyped = (MessagingBridgeMBeanImpl)other;
            this.computeDiff("BatchInterval", this.bean.getBatchInterval(), otherTyped.getBatchInterval(), true);
            this.computeDiff("BatchSize", this.bean.getBatchSize(), otherTyped.getBatchSize(), true);
            this.computeDiff("IdleTimeMaximum", this.bean.getIdleTimeMaximum(), otherTyped.getIdleTimeMaximum(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PreserveMsgProperty", this.bean.getPreserveMsgProperty(), otherTyped.getPreserveMsgProperty(), true);
            this.computeDiff("QualityOfService", this.bean.getQualityOfService(), otherTyped.getQualityOfService(), false);
            this.computeDiff("ReconnectDelayIncrease", this.bean.getReconnectDelayIncrease(), otherTyped.getReconnectDelayIncrease(), true);
            this.computeDiff("ReconnectDelayMaximum", this.bean.getReconnectDelayMaximum(), otherTyped.getReconnectDelayMaximum(), true);
            this.computeDiff("ReconnectDelayMinimum", this.bean.getReconnectDelayMinimum(), otherTyped.getReconnectDelayMinimum(), true);
            this.computeDiff("Selector", this.bean.getSelector(), otherTyped.getSelector(), false);
            this.computeDiff("SourceDestination", this.bean.getSourceDestination(), otherTyped.getSourceDestination(), false);
            this.computeDiff("TargetDestination", this.bean.getTargetDestination(), otherTyped.getTargetDestination(), false);
            this.computeDiff("TransactionTimeout", this.bean.getTransactionTimeout(), otherTyped.getTransactionTimeout(), true);
            this.computeDiff("AsyncEnabled", this.bean.isAsyncEnabled(), otherTyped.isAsyncEnabled(), false);
            this.computeDiff("DurabilityEnabled", this.bean.isDurabilityEnabled(), otherTyped.isDurabilityEnabled(), false);
            this.computeDiff("QOSDegradationAllowed", this.bean.isQOSDegradationAllowed(), otherTyped.isQOSDegradationAllowed(), false);
            this.computeDiff("Started", this.bean.isStarted(), otherTyped.isStarted(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MessagingBridgeMBeanImpl original = (MessagingBridgeMBeanImpl)event.getSourceBean();
            MessagingBridgeMBeanImpl proposed = (MessagingBridgeMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BatchInterval")) {
                  original.setBatchInterval(proposed.getBatchInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("BatchSize")) {
                  original.setBatchSize(proposed.getBatchSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (!prop.equals("ForwardingPolicy")) {
                  if (prop.equals("IdleTimeMaximum")) {
                     original.setIdleTimeMaximum(proposed.getIdleTimeMaximum());
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
                  } else if (!prop.equals("MaximumIdleTimeMilliseconds")) {
                     if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("PreserveMsgProperty")) {
                        original.setPreserveMsgProperty(proposed.getPreserveMsgProperty());
                        original._conditionalUnset(update.isUnsetUpdate(), 46);
                     } else if (prop.equals("QualityOfService")) {
                        original.setQualityOfService(proposed.getQualityOfService());
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("ReconnectDelayIncrease")) {
                        original.setReconnectDelayIncrease(proposed.getReconnectDelayIncrease());
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (!prop.equals("ReconnectDelayIncrement") && !prop.equals("ReconnectDelayInitialMilliseconds")) {
                        if (prop.equals("ReconnectDelayMaximum")) {
                           original.setReconnectDelayMaximum(proposed.getReconnectDelayMaximum());
                           original._conditionalUnset(update.isUnsetUpdate(), 35);
                        } else if (!prop.equals("ReconnectDelayMaximumMilliseconds")) {
                           if (prop.equals("ReconnectDelayMinimum")) {
                              original.setReconnectDelayMinimum(proposed.getReconnectDelayMinimum());
                              original._conditionalUnset(update.isUnsetUpdate(), 31);
                           } else if (!prop.equals("ScanUnitMilliseconds") && !prop.equals("ScheduleTime")) {
                              if (prop.equals("Selector")) {
                                 original.setSelector(proposed.getSelector());
                                 original._conditionalUnset(update.isUnsetUpdate(), 23);
                              } else if (prop.equals("SourceDestination")) {
                                 original.setSourceDestinationAsString(proposed.getSourceDestinationAsString());
                                 original._conditionalUnset(update.isUnsetUpdate(), 21);
                              } else if (prop.equals("TargetDestination")) {
                                 original.setTargetDestinationAsString(proposed.getTargetDestinationAsString());
                                 original._conditionalUnset(update.isUnsetUpdate(), 22);
                              } else if (prop.equals("TransactionTimeout")) {
                                 original.setTransactionTimeout(proposed.getTransactionTimeout());
                                 original._conditionalUnset(update.isUnsetUpdate(), 40);
                              } else if (!prop.equals("TransactionTimeoutSeconds") && !prop.equals("AsyncDisabled")) {
                                 if (prop.equals("AsyncEnabled")) {
                                    original.setAsyncEnabled(proposed.isAsyncEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 42);
                                 } else if (!prop.equals("DurabilityDisabled")) {
                                    if (prop.equals("DurabilityEnabled")) {
                                       original.setDurabilityEnabled(proposed.isDurabilityEnabled());
                                       original._conditionalUnset(update.isUnsetUpdate(), 29);
                                    } else if (prop.equals("QOSDegradationAllowed")) {
                                       original.setQOSDegradationAllowed(proposed.isQOSDegradationAllowed());
                                       original._conditionalUnset(update.isUnsetUpdate(), 27);
                                    } else if (prop.equals("Started")) {
                                       original.setStarted(proposed.isStarted());
                                       original._conditionalUnset(update.isUnsetUpdate(), 43);
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
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            MessagingBridgeMBeanImpl copy = (MessagingBridgeMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BatchInterval")) && this.bean.isBatchIntervalSet()) {
               copy.setBatchInterval(this.bean.getBatchInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("BatchSize")) && this.bean.isBatchSizeSet()) {
               copy.setBatchSize(this.bean.getBatchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleTimeMaximum")) && this.bean.isIdleTimeMaximumSet()) {
               copy.setIdleTimeMaximum(this.bean.getIdleTimeMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PreserveMsgProperty")) && this.bean.isPreserveMsgPropertySet()) {
               copy.setPreserveMsgProperty(this.bean.getPreserveMsgProperty());
            }

            if ((excludeProps == null || !excludeProps.contains("QualityOfService")) && this.bean.isQualityOfServiceSet()) {
               copy.setQualityOfService(this.bean.getQualityOfService());
            }

            if ((excludeProps == null || !excludeProps.contains("ReconnectDelayIncrease")) && this.bean.isReconnectDelayIncreaseSet()) {
               copy.setReconnectDelayIncrease(this.bean.getReconnectDelayIncrease());
            }

            if ((excludeProps == null || !excludeProps.contains("ReconnectDelayMaximum")) && this.bean.isReconnectDelayMaximumSet()) {
               copy.setReconnectDelayMaximum(this.bean.getReconnectDelayMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("ReconnectDelayMinimum")) && this.bean.isReconnectDelayMinimumSet()) {
               copy.setReconnectDelayMinimum(this.bean.getReconnectDelayMinimum());
            }

            if ((excludeProps == null || !excludeProps.contains("Selector")) && this.bean.isSelectorSet()) {
               copy.setSelector(this.bean.getSelector());
            }

            if ((excludeProps == null || !excludeProps.contains("SourceDestination")) && this.bean.isSourceDestinationSet()) {
               copy._unSet(copy, 21);
               copy.setSourceDestinationAsString(this.bean.getSourceDestinationAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetDestination")) && this.bean.isTargetDestinationSet()) {
               copy._unSet(copy, 22);
               copy.setTargetDestinationAsString(this.bean.getTargetDestinationAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionTimeout")) && this.bean.isTransactionTimeoutSet()) {
               copy.setTransactionTimeout(this.bean.getTransactionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("AsyncEnabled")) && this.bean.isAsyncEnabledSet()) {
               copy.setAsyncEnabled(this.bean.isAsyncEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DurabilityEnabled")) && this.bean.isDurabilityEnabledSet()) {
               copy.setDurabilityEnabled(this.bean.isDurabilityEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("QOSDegradationAllowed")) && this.bean.isQOSDegradationAllowedSet()) {
               copy.setQOSDegradationAllowed(this.bean.isQOSDegradationAllowed());
            }

            if ((excludeProps == null || !excludeProps.contains("Started")) && this.bean.isStartedSet()) {
               copy.setStarted(this.bean.isStarted());
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
         this.inferSubTree(this.bean.getSourceDestination(), clazz, annotation);
         this.inferSubTree(this.bean.getTargetDestination(), clazz, annotation);
      }
   }
}
