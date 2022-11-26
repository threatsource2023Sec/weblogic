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
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.jms.module.validators.JMSModuleValidator;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JMSTemplate;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JMSTemplateMBeanImpl extends JMSDestCommonMBeanImpl implements JMSTemplateMBean, Serializable {
   private long _BytesMaximum;
   private boolean _BytesPagingEnabled;
   private long _BytesThresholdHigh;
   private long _BytesThresholdLow;
   private String _DeliveryModeOverride;
   private JMSDestinationMBean[] _Destinations;
   private boolean _DynamicallyCreated;
   private JMSDestinationMBean _ErrorDestination;
   private String _ExpirationLoggingPolicy;
   private String _ExpirationPolicy;
   private int _MaximumMessageSize;
   private long _MessagesMaximum;
   private boolean _MessagesPagingEnabled;
   private long _MessagesThresholdHigh;
   private long _MessagesThresholdLow;
   private String _Name;
   private String _Notes;
   private int _PriorityOverride;
   private long _RedeliveryDelayOverride;
   private int _RedeliveryLimit;
   private String[] _Tags;
   private String _TimeToDeliverOverride;
   private long _TimeToLiveOverride;
   private transient JMSTemplate _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JMSTemplateMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JMSTemplateMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JMSTemplateMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JMSTemplateMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JMSTemplateMBeanImpl delegate) {
      JMSTemplateMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._ErrorDestination instanceof JMSDestinationMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getErrorDestination() != null) {
            this._getReferenceManager().unregisterBean((JMSDestinationMBeanImpl)oldDelegate.getErrorDestination());
         }

         if (delegate != null && delegate.getErrorDestination() != null) {
            this._getReferenceManager().registerBean((JMSDestinationMBeanImpl)delegate.getErrorDestination(), false);
         }

         ((JMSDestinationMBeanImpl)this._ErrorDestination)._setDelegateBean((JMSDestinationMBeanImpl)((JMSDestinationMBeanImpl)(delegate == null ? null : delegate.getErrorDestination())));
      }

   }

   public JMSTemplateMBeanImpl() {
      try {
         this._customizer = new JMSTemplate(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMSTemplateMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMSTemplate(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMSTemplateMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMSTemplate(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public JMSDestinationMBean[] getDestinations() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getDestinations() : this._Destinations;
   }

   public String getDestinationsAsString() {
      return this._getHelper()._serializeKeyList(this.getDestinations());
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

   public boolean isDestinationsInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isDestinationsSet() {
      return this._isSet(28);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setDestinationsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Destinations);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, JMSDestinationMBean.class, new ReferenceManager.Resolver(this, 28, param0) {
                  public void resolveReference(Object value) {
                     try {
                        JMSTemplateMBeanImpl.this.addDestination((JMSDestinationMBean)value);
                        JMSTemplateMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JMSTemplateMBeanImpl.this._Destinations, this.getHandbackObject());
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
               JMSDestinationMBean[] var6 = this._Destinations;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  JMSDestinationMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeDestination(member);
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
         JMSDestinationMBean[] _oldVal = this._Destinations;
         this._initializeProperty(28);
         this._postSet(28, _oldVal, this._Destinations);
      }
   }

   public void setDestinations(JMSDestinationMBean[] param0) throws InvalidAttributeValueException {
      JMSDestinationMBean[] param0 = param0 == null ? new JMSDestinationMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      JMSDestinationMBean[] _oldVal = this._Destinations;
      this._Destinations = (JMSDestinationMBean[])param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addDestination(JMSDestinationMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         JMSDestinationMBean[] _new;
         if (this._isSet(28)) {
            _new = (JMSDestinationMBean[])((JMSDestinationMBean[])this._getHelper()._extendArray(this.getDestinations(), JMSDestinationMBean.class, param0));
         } else {
            _new = new JMSDestinationMBean[]{param0};
         }

         try {
            this.setDestinations(_new);
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
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public String getNotes() {
      return !this._isSet(3) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(3) ? this._performMacroSubstitution(this._getDelegateBean().getNotes(), this) : this._customizer.getNotes();
   }

   public boolean isNotesInherited() {
      return !this._isSet(3) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(3);
   }

   public boolean isNotesSet() {
      return this._isSet(3);
   }

   public boolean removeDestination(JMSDestinationMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      JMSDestinationMBean[] _old = this.getDestinations();
      JMSDestinationMBean[] _new = (JMSDestinationMBean[])((JMSDestinationMBean[])this._getHelper()._removeElement(_old, JMSDestinationMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDestinations(_new);
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

   public boolean isMessagesPagingEnabled() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().isMessagesPagingEnabled() : this._MessagesPagingEnabled;
   }

   public boolean isMessagesPagingEnabledInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isMessagesPagingEnabledSet() {
      return this._isSet(29);
   }

   public void setNotes(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(3);
      String _oldVal = this.getNotes();
      this._customizer.setNotes(param0);
      this._postSet(3, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(3)) {
            source._postSetFirePropertyChange(3, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesMaximum() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getBytesMaximum() : this._customizer.getBytesMaximum();
   }

   public boolean isBytesMaximumInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isBytesMaximumSet() {
      return this._isSet(11);
   }

   public void setMessagesPagingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      boolean _oldVal = this._MessagesPagingEnabled;
      this._MessagesPagingEnabled = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isBytesPagingEnabled() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().isBytesPagingEnabled() : this._BytesPagingEnabled;
   }

   public boolean isBytesPagingEnabledInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isBytesPagingEnabledSet() {
      return this._isSet(30);
   }

   public void setBytesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BytesMaximum", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(11);
      long _oldVal = this.getBytesMaximum();
      this._customizer.setBytesMaximum(param0);
      this._postSet(11, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesThresholdHigh() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getBytesThresholdHigh() : this._customizer.getBytesThresholdHigh();
   }

   public boolean isBytesThresholdHighInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isBytesThresholdHighSet() {
      return this._isSet(12);
   }

   public void setBytesPagingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._BytesPagingEnabled;
      this._BytesPagingEnabled = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public void setBytesThresholdHigh(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BytesThresholdHigh", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(12);
      long _oldVal = this.getBytesThresholdHigh();
      this._customizer.setBytesThresholdHigh(param0);
      this._postSet(12, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public void useDelegates(DomainMBean param0, JMSBean param1, TemplateBean param2) {
      this._customizer.useDelegates(param0, param1, param2);
   }

   public long getBytesThresholdLow() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getBytesThresholdLow() : this._customizer.getBytesThresholdLow();
   }

   public boolean isBytesThresholdLowInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isBytesThresholdLowSet() {
      return this._isSet(13);
   }

   public void setBytesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BytesThresholdLow", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(13);
      long _oldVal = this.getBytesThresholdLow();
      this._customizer.setBytesThresholdLow(param0);
      this._postSet(13, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesMaximum() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getMessagesMaximum() : this._customizer.getMessagesMaximum();
   }

   public boolean isMessagesMaximumInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(14);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMessagesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesMaximum", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(14);
      long _oldVal = this.getMessagesMaximum();
      this._customizer.setMessagesMaximum(param0);
      this._postSet(14, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesThresholdHigh() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getMessagesThresholdHigh() : this._customizer.getMessagesThresholdHigh();
   }

   public boolean isMessagesThresholdHighInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isMessagesThresholdHighSet() {
      return this._isSet(15);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMessagesThresholdHigh(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesThresholdHigh", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(15);
      long _oldVal = this.getMessagesThresholdHigh();
      this._customizer.setMessagesThresholdHigh(param0);
      this._postSet(15, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesThresholdLow() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getMessagesThresholdLow() : this._customizer.getMessagesThresholdLow();
   }

   public boolean isMessagesThresholdLowInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isMessagesThresholdLowSet() {
      return this._isSet(16);
   }

   public void setMessagesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesThresholdLow", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(16);
      long _oldVal = this.getMessagesThresholdLow();
      this._customizer.setMessagesThresholdLow(param0);
      this._postSet(16, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPriorityOverride() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getPriorityOverride() : this._customizer.getPriorityOverride();
   }

   public boolean isPriorityOverrideInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isPriorityOverrideSet() {
      return this._isSet(17);
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

   public void setPriorityOverride(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("PriorityOverride", (long)param0, -1L, 9L);
      boolean wasSet = this._isSet(17);
      int _oldVal = this.getPriorityOverride();
      this._customizer.setPriorityOverride(param0);
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTimeToDeliverOverride() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getTimeToDeliverOverride(), this) : this._customizer.getTimeToDeliverOverride();
   }

   public boolean isTimeToDeliverOverrideInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isTimeToDeliverOverrideSet() {
      return this._isSet(18);
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

   public void setTimeToDeliverOverride(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      JMSModuleValidator.validateTimeToDeliverOverride(param0);
      boolean wasSet = this._isSet(18);
      String _oldVal = this.getTimeToDeliverOverride();
      this._customizer.setTimeToDeliverOverride(param0);
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public long getRedeliveryDelayOverride() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getRedeliveryDelayOverride() : this._customizer.getRedeliveryDelayOverride();
   }

   public boolean isRedeliveryDelayOverrideInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isRedeliveryDelayOverrideSet() {
      return this._isSet(19);
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
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
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

   public void setRedeliveryDelayOverride(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RedeliveryDelayOverride", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(19);
      long _oldVal = this.getRedeliveryDelayOverride();
      this._customizer.setRedeliveryDelayOverride(param0);
      this._postSet(19, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
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

   public void setErrorDestination(JMSDestinationMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      JMSDestinationMBean _oldVal = this.getErrorDestination();
      this._customizer.setErrorDestination(param0);
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSDestinationMBean getErrorDestination() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getErrorDestination() : this._customizer.getErrorDestination();
   }

   public String getErrorDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getErrorDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isErrorDestinationInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isErrorDestinationSet() {
      return this._isSet(20);
   }

   public void setErrorDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSDestinationMBean.class, new ReferenceManager.Resolver(this, 20) {
            public void resolveReference(Object value) {
               try {
                  JMSTemplateMBeanImpl.this.setErrorDestination((JMSDestinationMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSDestinationMBean _oldVal = this._ErrorDestination;
         this._initializeProperty(20);
         this._postSet(20, _oldVal, this._ErrorDestination);
      }

   }

   public int getRedeliveryLimit() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getRedeliveryLimit() : this._customizer.getRedeliveryLimit();
   }

   public boolean isRedeliveryLimitInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isRedeliveryLimitSet() {
      return this._isSet(21);
   }

   public void setRedeliveryLimit(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RedeliveryLimit", (long)param0, -1L, 2147483647L);
      boolean wasSet = this._isSet(21);
      int _oldVal = this.getRedeliveryLimit();
      this._customizer.setRedeliveryLimit(param0);
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public long getTimeToLiveOverride() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getTimeToLiveOverride() : this._customizer.getTimeToLiveOverride();
   }

   public boolean isTimeToLiveOverrideInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isTimeToLiveOverrideSet() {
      return this._isSet(22);
   }

   public void setTimeToLiveOverride(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("TimeToLiveOverride", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(22);
      long _oldVal = this.getTimeToLiveOverride();
      this._customizer.setTimeToLiveOverride(param0);
      this._postSet(22, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var6.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDeliveryModeOverride() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getDeliveryModeOverride(), this) : this._customizer.getDeliveryModeOverride();
   }

   public boolean isDeliveryModeOverrideInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isDeliveryModeOverrideSet() {
      return this._isSet(23);
   }

   public void setDeliveryModeOverride(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Persistent", "Non-Persistent", "No-Delivery"};
      param0 = LegalChecks.checkInEnum("DeliveryModeOverride", param0, _set);
      boolean wasSet = this._isSet(23);
      String _oldVal = this.getDeliveryModeOverride();
      this._customizer.setDeliveryModeOverride(param0);
      this._postSet(23, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public void setExpirationPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Discard", "Log", "Redirect"};
      param0 = LegalChecks.checkInEnum("ExpirationPolicy", param0, _set);
      boolean wasSet = this._isSet(24);
      String _oldVal = this.getExpirationPolicy();
      this._customizer.setExpirationPolicy(param0);
      this._postSet(24, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var5.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExpirationPolicy() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getExpirationPolicy(), this) : this._customizer.getExpirationPolicy();
   }

   public boolean isExpirationPolicyInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isExpirationPolicySet() {
      return this._isSet(24);
   }

   public void setExpirationLoggingPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      String _oldVal = this.getExpirationLoggingPolicy();
      this._customizer.setExpirationLoggingPolicy(param0);
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExpirationLoggingPolicy() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getExpirationLoggingPolicy(), this) : this._customizer.getExpirationLoggingPolicy();
   }

   public boolean isExpirationLoggingPolicyInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isExpirationLoggingPolicySet() {
      return this._isSet(25);
   }

   public int getMaximumMessageSize() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getMaximumMessageSize() : this._customizer.getMaximumMessageSize();
   }

   public boolean isMaximumMessageSizeInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(26);
   }

   public void setMaximumMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaximumMessageSize", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(26);
      int _oldVal = this.getMaximumMessageSize();
      this._customizer.setMaximumMessageSize(param0);
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSTemplateMBeanImpl source = (JMSTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._customizer.setBytesMaximum(-1L);
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setBytesThresholdHigh(-1L);
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setBytesThresholdLow(-1L);
               if (initOne) {
                  break;
               }
            case 23:
               this._customizer.setDeliveryModeOverride("No-Delivery");
               if (initOne) {
                  break;
               }
            case 28:
               this._Destinations = new JMSDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._customizer.setErrorDestination((JMSDestinationMBean)null);
               if (initOne) {
                  break;
               }
            case 25:
               this._customizer.setExpirationLoggingPolicy((String)null);
               if (initOne) {
                  break;
               }
            case 24:
               this._customizer.setExpirationPolicy("Discard");
               if (initOne) {
                  break;
               }
            case 26:
               this._customizer.setMaximumMessageSize(Integer.MAX_VALUE);
               if (initOne) {
                  break;
               }
            case 14:
               this._customizer.setMessagesMaximum(-1L);
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setMessagesThresholdHigh(-1L);
               if (initOne) {
                  break;
               }
            case 16:
               this._customizer.setMessagesThresholdLow(-1L);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 3:
               this._customizer.setNotes((String)null);
               if (initOne) {
                  break;
               }
            case 17:
               this._customizer.setPriorityOverride(-1);
               if (initOne) {
                  break;
               }
            case 19:
               this._customizer.setRedeliveryDelayOverride(-1L);
               if (initOne) {
                  break;
               }
            case 21:
               this._customizer.setRedeliveryLimit(-1);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 18:
               this._customizer.setTimeToDeliverOverride("-1");
               if (initOne) {
                  break;
               }
            case 22:
               this._customizer.setTimeToLiveOverride(-1L);
               if (initOne) {
                  break;
               }
            case 30:
               this._BytesPagingEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._MessagesPagingEnabled = false;
               if (initOne) {
                  break;
               }
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 27:
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
      return "JMSTemplate";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("BytesMaximum")) {
         oldVal = this._BytesMaximum;
         this._BytesMaximum = (Long)v;
         this._postSet(11, oldVal, this._BytesMaximum);
      } else {
         boolean oldVal;
         if (name.equals("BytesPagingEnabled")) {
            oldVal = this._BytesPagingEnabled;
            this._BytesPagingEnabled = (Boolean)v;
            this._postSet(30, oldVal, this._BytesPagingEnabled);
         } else if (name.equals("BytesThresholdHigh")) {
            oldVal = this._BytesThresholdHigh;
            this._BytesThresholdHigh = (Long)v;
            this._postSet(12, oldVal, this._BytesThresholdHigh);
         } else if (name.equals("BytesThresholdLow")) {
            oldVal = this._BytesThresholdLow;
            this._BytesThresholdLow = (Long)v;
            this._postSet(13, oldVal, this._BytesThresholdLow);
         } else {
            String oldVal;
            if (name.equals("DeliveryModeOverride")) {
               oldVal = this._DeliveryModeOverride;
               this._DeliveryModeOverride = (String)v;
               this._postSet(23, oldVal, this._DeliveryModeOverride);
            } else if (name.equals("Destinations")) {
               JMSDestinationMBean[] oldVal = this._Destinations;
               this._Destinations = (JMSDestinationMBean[])((JMSDestinationMBean[])v);
               this._postSet(28, oldVal, this._Destinations);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("ErrorDestination")) {
               JMSDestinationMBean oldVal = this._ErrorDestination;
               this._ErrorDestination = (JMSDestinationMBean)v;
               this._postSet(20, oldVal, this._ErrorDestination);
            } else if (name.equals("ExpirationLoggingPolicy")) {
               oldVal = this._ExpirationLoggingPolicy;
               this._ExpirationLoggingPolicy = (String)v;
               this._postSet(25, oldVal, this._ExpirationLoggingPolicy);
            } else if (name.equals("ExpirationPolicy")) {
               oldVal = this._ExpirationPolicy;
               this._ExpirationPolicy = (String)v;
               this._postSet(24, oldVal, this._ExpirationPolicy);
            } else {
               int oldVal;
               if (name.equals("MaximumMessageSize")) {
                  oldVal = this._MaximumMessageSize;
                  this._MaximumMessageSize = (Integer)v;
                  this._postSet(26, oldVal, this._MaximumMessageSize);
               } else if (name.equals("MessagesMaximum")) {
                  oldVal = this._MessagesMaximum;
                  this._MessagesMaximum = (Long)v;
                  this._postSet(14, oldVal, this._MessagesMaximum);
               } else if (name.equals("MessagesPagingEnabled")) {
                  oldVal = this._MessagesPagingEnabled;
                  this._MessagesPagingEnabled = (Boolean)v;
                  this._postSet(29, oldVal, this._MessagesPagingEnabled);
               } else if (name.equals("MessagesThresholdHigh")) {
                  oldVal = this._MessagesThresholdHigh;
                  this._MessagesThresholdHigh = (Long)v;
                  this._postSet(15, oldVal, this._MessagesThresholdHigh);
               } else if (name.equals("MessagesThresholdLow")) {
                  oldVal = this._MessagesThresholdLow;
                  this._MessagesThresholdLow = (Long)v;
                  this._postSet(16, oldVal, this._MessagesThresholdLow);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("Notes")) {
                  oldVal = this._Notes;
                  this._Notes = (String)v;
                  this._postSet(3, oldVal, this._Notes);
               } else if (name.equals("PriorityOverride")) {
                  oldVal = this._PriorityOverride;
                  this._PriorityOverride = (Integer)v;
                  this._postSet(17, oldVal, this._PriorityOverride);
               } else if (name.equals("RedeliveryDelayOverride")) {
                  oldVal = this._RedeliveryDelayOverride;
                  this._RedeliveryDelayOverride = (Long)v;
                  this._postSet(19, oldVal, this._RedeliveryDelayOverride);
               } else if (name.equals("RedeliveryLimit")) {
                  oldVal = this._RedeliveryLimit;
                  this._RedeliveryLimit = (Integer)v;
                  this._postSet(21, oldVal, this._RedeliveryLimit);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("TimeToDeliverOverride")) {
                  oldVal = this._TimeToDeliverOverride;
                  this._TimeToDeliverOverride = (String)v;
                  this._postSet(18, oldVal, this._TimeToDeliverOverride);
               } else if (name.equals("TimeToLiveOverride")) {
                  oldVal = this._TimeToLiveOverride;
                  this._TimeToLiveOverride = (Long)v;
                  this._postSet(22, oldVal, this._TimeToLiveOverride);
               } else if (name.equals("customizer")) {
                  JMSTemplate oldVal = this._customizer;
                  this._customizer = (JMSTemplate)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("BytesMaximum")) {
         return new Long(this._BytesMaximum);
      } else if (name.equals("BytesPagingEnabled")) {
         return new Boolean(this._BytesPagingEnabled);
      } else if (name.equals("BytesThresholdHigh")) {
         return new Long(this._BytesThresholdHigh);
      } else if (name.equals("BytesThresholdLow")) {
         return new Long(this._BytesThresholdLow);
      } else if (name.equals("DeliveryModeOverride")) {
         return this._DeliveryModeOverride;
      } else if (name.equals("Destinations")) {
         return this._Destinations;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ErrorDestination")) {
         return this._ErrorDestination;
      } else if (name.equals("ExpirationLoggingPolicy")) {
         return this._ExpirationLoggingPolicy;
      } else if (name.equals("ExpirationPolicy")) {
         return this._ExpirationPolicy;
      } else if (name.equals("MaximumMessageSize")) {
         return new Integer(this._MaximumMessageSize);
      } else if (name.equals("MessagesMaximum")) {
         return new Long(this._MessagesMaximum);
      } else if (name.equals("MessagesPagingEnabled")) {
         return new Boolean(this._MessagesPagingEnabled);
      } else if (name.equals("MessagesThresholdHigh")) {
         return new Long(this._MessagesThresholdHigh);
      } else if (name.equals("MessagesThresholdLow")) {
         return new Long(this._MessagesThresholdLow);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Notes")) {
         return this._Notes;
      } else if (name.equals("PriorityOverride")) {
         return new Integer(this._PriorityOverride);
      } else if (name.equals("RedeliveryDelayOverride")) {
         return new Long(this._RedeliveryDelayOverride);
      } else if (name.equals("RedeliveryLimit")) {
         return new Integer(this._RedeliveryLimit);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TimeToDeliverOverride")) {
         return this._TimeToDeliverOverride;
      } else if (name.equals("TimeToLiveOverride")) {
         return new Long(this._TimeToLiveOverride);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends JMSDestCommonMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
               if (s.equals("notes")) {
                  return 3;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 18:
            default:
               break;
            case 11:
               if (s.equals("destination")) {
                  return 28;
               }
               break;
            case 13:
               if (s.equals("bytes-maximum")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("messages-maximum")) {
                  return 14;
               }

               if (s.equals("redelivery-limit")) {
                  return 21;
               }
               break;
            case 17:
               if (s.equals("error-destination")) {
                  return 20;
               }

               if (s.equals("expiration-policy")) {
                  return 24;
               }

               if (s.equals("priority-override")) {
                  return 17;
               }
               break;
            case 19:
               if (s.equals("bytes-threshold-low")) {
                  return 13;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("bytes-threshold-high")) {
                  return 12;
               }

               if (s.equals("maximum-message-size")) {
                  return 26;
               }

               if (s.equals("bytes-paging-enabled")) {
                  return 30;
               }
               break;
            case 21:
               if (s.equals("time-to-live-override")) {
                  return 22;
               }
               break;
            case 22:
               if (s.equals("delivery-mode-override")) {
                  return 23;
               }

               if (s.equals("messages-threshold-low")) {
                  return 16;
               }
               break;
            case 23:
               if (s.equals("messages-threshold-high")) {
                  return 15;
               }

               if (s.equals("messages-paging-enabled")) {
                  return 29;
               }
               break;
            case 24:
               if (s.equals("time-to-deliver-override")) {
                  return 18;
               }
               break;
            case 25:
               if (s.equals("expiration-logging-policy")) {
                  return 25;
               }

               if (s.equals("redelivery-delay-override")) {
                  return 19;
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
               return "notes";
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 27:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 11:
               return "bytes-maximum";
            case 12:
               return "bytes-threshold-high";
            case 13:
               return "bytes-threshold-low";
            case 14:
               return "messages-maximum";
            case 15:
               return "messages-threshold-high";
            case 16:
               return "messages-threshold-low";
            case 17:
               return "priority-override";
            case 18:
               return "time-to-deliver-override";
            case 19:
               return "redelivery-delay-override";
            case 20:
               return "error-destination";
            case 21:
               return "redelivery-limit";
            case 22:
               return "time-to-live-override";
            case 23:
               return "delivery-mode-override";
            case 24:
               return "expiration-policy";
            case 25:
               return "expiration-logging-policy";
            case 26:
               return "maximum-message-size";
            case 28:
               return "destination";
            case 29:
               return "messages-paging-enabled";
            case 30:
               return "bytes-paging-enabled";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 28:
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

   protected static class Helper extends JMSDestCommonMBeanImpl.Helper {
      private JMSTemplateMBeanImpl bean;

      protected Helper(JMSTemplateMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
               return "Notes";
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 27:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 11:
               return "BytesMaximum";
            case 12:
               return "BytesThresholdHigh";
            case 13:
               return "BytesThresholdLow";
            case 14:
               return "MessagesMaximum";
            case 15:
               return "MessagesThresholdHigh";
            case 16:
               return "MessagesThresholdLow";
            case 17:
               return "PriorityOverride";
            case 18:
               return "TimeToDeliverOverride";
            case 19:
               return "RedeliveryDelayOverride";
            case 20:
               return "ErrorDestination";
            case 21:
               return "RedeliveryLimit";
            case 22:
               return "TimeToLiveOverride";
            case 23:
               return "DeliveryModeOverride";
            case 24:
               return "ExpirationPolicy";
            case 25:
               return "ExpirationLoggingPolicy";
            case 26:
               return "MaximumMessageSize";
            case 28:
               return "Destinations";
            case 29:
               return "MessagesPagingEnabled";
            case 30:
               return "BytesPagingEnabled";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BytesMaximum")) {
            return 11;
         } else if (propName.equals("BytesThresholdHigh")) {
            return 12;
         } else if (propName.equals("BytesThresholdLow")) {
            return 13;
         } else if (propName.equals("DeliveryModeOverride")) {
            return 23;
         } else if (propName.equals("Destinations")) {
            return 28;
         } else if (propName.equals("ErrorDestination")) {
            return 20;
         } else if (propName.equals("ExpirationLoggingPolicy")) {
            return 25;
         } else if (propName.equals("ExpirationPolicy")) {
            return 24;
         } else if (propName.equals("MaximumMessageSize")) {
            return 26;
         } else if (propName.equals("MessagesMaximum")) {
            return 14;
         } else if (propName.equals("MessagesThresholdHigh")) {
            return 15;
         } else if (propName.equals("MessagesThresholdLow")) {
            return 16;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Notes")) {
            return 3;
         } else if (propName.equals("PriorityOverride")) {
            return 17;
         } else if (propName.equals("RedeliveryDelayOverride")) {
            return 19;
         } else if (propName.equals("RedeliveryLimit")) {
            return 21;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TimeToDeliverOverride")) {
            return 18;
         } else if (propName.equals("TimeToLiveOverride")) {
            return 22;
         } else if (propName.equals("BytesPagingEnabled")) {
            return 30;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("MessagesPagingEnabled") ? 29 : super.getPropertyIndex(propName);
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
            if (this.bean.isBytesMaximumSet()) {
               buf.append("BytesMaximum");
               buf.append(String.valueOf(this.bean.getBytesMaximum()));
            }

            if (this.bean.isBytesThresholdHighSet()) {
               buf.append("BytesThresholdHigh");
               buf.append(String.valueOf(this.bean.getBytesThresholdHigh()));
            }

            if (this.bean.isBytesThresholdLowSet()) {
               buf.append("BytesThresholdLow");
               buf.append(String.valueOf(this.bean.getBytesThresholdLow()));
            }

            if (this.bean.isDeliveryModeOverrideSet()) {
               buf.append("DeliveryModeOverride");
               buf.append(String.valueOf(this.bean.getDeliveryModeOverride()));
            }

            if (this.bean.isDestinationsSet()) {
               buf.append("Destinations");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDestinations())));
            }

            if (this.bean.isErrorDestinationSet()) {
               buf.append("ErrorDestination");
               buf.append(String.valueOf(this.bean.getErrorDestination()));
            }

            if (this.bean.isExpirationLoggingPolicySet()) {
               buf.append("ExpirationLoggingPolicy");
               buf.append(String.valueOf(this.bean.getExpirationLoggingPolicy()));
            }

            if (this.bean.isExpirationPolicySet()) {
               buf.append("ExpirationPolicy");
               buf.append(String.valueOf(this.bean.getExpirationPolicy()));
            }

            if (this.bean.isMaximumMessageSizeSet()) {
               buf.append("MaximumMessageSize");
               buf.append(String.valueOf(this.bean.getMaximumMessageSize()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isMessagesThresholdHighSet()) {
               buf.append("MessagesThresholdHigh");
               buf.append(String.valueOf(this.bean.getMessagesThresholdHigh()));
            }

            if (this.bean.isMessagesThresholdLowSet()) {
               buf.append("MessagesThresholdLow");
               buf.append(String.valueOf(this.bean.getMessagesThresholdLow()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isPriorityOverrideSet()) {
               buf.append("PriorityOverride");
               buf.append(String.valueOf(this.bean.getPriorityOverride()));
            }

            if (this.bean.isRedeliveryDelayOverrideSet()) {
               buf.append("RedeliveryDelayOverride");
               buf.append(String.valueOf(this.bean.getRedeliveryDelayOverride()));
            }

            if (this.bean.isRedeliveryLimitSet()) {
               buf.append("RedeliveryLimit");
               buf.append(String.valueOf(this.bean.getRedeliveryLimit()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTimeToDeliverOverrideSet()) {
               buf.append("TimeToDeliverOverride");
               buf.append(String.valueOf(this.bean.getTimeToDeliverOverride()));
            }

            if (this.bean.isTimeToLiveOverrideSet()) {
               buf.append("TimeToLiveOverride");
               buf.append(String.valueOf(this.bean.getTimeToLiveOverride()));
            }

            if (this.bean.isBytesPagingEnabledSet()) {
               buf.append("BytesPagingEnabled");
               buf.append(String.valueOf(this.bean.isBytesPagingEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isMessagesPagingEnabledSet()) {
               buf.append("MessagesPagingEnabled");
               buf.append(String.valueOf(this.bean.isMessagesPagingEnabled()));
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
            JMSTemplateMBeanImpl otherTyped = (JMSTemplateMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesMaximum", this.bean.getBytesMaximum(), otherTyped.getBytesMaximum(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesThresholdHigh", this.bean.getBytesThresholdHigh(), otherTyped.getBytesThresholdHigh(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesThresholdLow", this.bean.getBytesThresholdLow(), otherTyped.getBytesThresholdLow(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DeliveryModeOverride", this.bean.getDeliveryModeOverride(), otherTyped.getDeliveryModeOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Destinations", this.bean.getDestinations(), otherTyped.getDestinations(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ErrorDestination", this.bean.getErrorDestination(), otherTyped.getErrorDestination(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ExpirationLoggingPolicy", this.bean.getExpirationLoggingPolicy(), otherTyped.getExpirationLoggingPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ExpirationPolicy", this.bean.getExpirationPolicy(), otherTyped.getExpirationPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesThresholdHigh", this.bean.getMessagesThresholdHigh(), otherTyped.getMessagesThresholdHigh(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesThresholdLow", this.bean.getMessagesThresholdLow(), otherTyped.getMessagesThresholdLow(), true);
            }

            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("PriorityOverride", this.bean.getPriorityOverride(), otherTyped.getPriorityOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RedeliveryDelayOverride", this.bean.getRedeliveryDelayOverride(), otherTyped.getRedeliveryDelayOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RedeliveryLimit", this.bean.getRedeliveryLimit(), otherTyped.getRedeliveryLimit(), true);
            }

            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TimeToDeliverOverride", this.bean.getTimeToDeliverOverride(), otherTyped.getTimeToDeliverOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TimeToLiveOverride", this.bean.getTimeToLiveOverride(), otherTyped.getTimeToLiveOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesPagingEnabled", this.bean.isBytesPagingEnabled(), otherTyped.isBytesPagingEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesPagingEnabled", this.bean.isMessagesPagingEnabled(), otherTyped.isMessagesPagingEnabled(), false);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSTemplateMBeanImpl original = (JMSTemplateMBeanImpl)event.getSourceBean();
            JMSTemplateMBeanImpl proposed = (JMSTemplateMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BytesMaximum")) {
                  original.setBytesMaximum(proposed.getBytesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("BytesThresholdHigh")) {
                  original.setBytesThresholdHigh(proposed.getBytesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("BytesThresholdLow")) {
                  original.setBytesThresholdLow(proposed.getBytesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DeliveryModeOverride")) {
                  original.setDeliveryModeOverride(proposed.getDeliveryModeOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("Destinations")) {
                  original.setDestinationsAsString(proposed.getDestinationsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("ErrorDestination")) {
                  original.setErrorDestinationAsString(proposed.getErrorDestinationAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ExpirationLoggingPolicy")) {
                  original.setExpirationLoggingPolicy(proposed.getExpirationLoggingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("ExpirationPolicy")) {
                  original.setExpirationPolicy(proposed.getExpirationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("MaximumMessageSize")) {
                  original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MessagesThresholdHigh")) {
                  original.setMessagesThresholdHigh(proposed.getMessagesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MessagesThresholdLow")) {
                  original.setMessagesThresholdLow(proposed.getMessagesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PriorityOverride")) {
                  original.setPriorityOverride(proposed.getPriorityOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("RedeliveryDelayOverride")) {
                  original.setRedeliveryDelayOverride(proposed.getRedeliveryDelayOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("RedeliveryLimit")) {
                  original.setRedeliveryLimit(proposed.getRedeliveryLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
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
               } else if (prop.equals("TimeToDeliverOverride")) {
                  original.setTimeToDeliverOverride(proposed.getTimeToDeliverOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("TimeToLiveOverride")) {
                  original.setTimeToLiveOverride(proposed.getTimeToLiveOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("BytesPagingEnabled")) {
                  original.setBytesPagingEnabled(proposed.isBytesPagingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("MessagesPagingEnabled")) {
                     original.setMessagesPagingEnabled(proposed.isMessagesPagingEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else {
                     super.applyPropertyUpdate(event, update);
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
            JMSTemplateMBeanImpl copy = (JMSTemplateMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesMaximum")) && this.bean.isBytesMaximumSet()) {
               copy.setBytesMaximum(this.bean.getBytesMaximum());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesThresholdHigh")) && this.bean.isBytesThresholdHighSet()) {
               copy.setBytesThresholdHigh(this.bean.getBytesThresholdHigh());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesThresholdLow")) && this.bean.isBytesThresholdLowSet()) {
               copy.setBytesThresholdLow(this.bean.getBytesThresholdLow());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DeliveryModeOverride")) && this.bean.isDeliveryModeOverrideSet()) {
               copy.setDeliveryModeOverride(this.bean.getDeliveryModeOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Destinations")) && this.bean.isDestinationsSet()) {
               copy._unSet(copy, 28);
               copy.setDestinationsAsString(this.bean.getDestinationsAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ErrorDestination")) && this.bean.isErrorDestinationSet()) {
               copy._unSet(copy, 20);
               copy.setErrorDestinationAsString(this.bean.getErrorDestinationAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ExpirationLoggingPolicy")) && this.bean.isExpirationLoggingPolicySet()) {
               copy.setExpirationLoggingPolicy(this.bean.getExpirationLoggingPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ExpirationPolicy")) && this.bean.isExpirationPolicySet()) {
               copy.setExpirationPolicy(this.bean.getExpirationPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MaximumMessageSize")) && this.bean.isMaximumMessageSizeSet()) {
               copy.setMaximumMessageSize(this.bean.getMaximumMessageSize());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesThresholdHigh")) && this.bean.isMessagesThresholdHighSet()) {
               copy.setMessagesThresholdHigh(this.bean.getMessagesThresholdHigh());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesThresholdLow")) && this.bean.isMessagesThresholdLowSet()) {
               copy.setMessagesThresholdLow(this.bean.getMessagesThresholdLow());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PriorityOverride")) && this.bean.isPriorityOverrideSet()) {
               copy.setPriorityOverride(this.bean.getPriorityOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RedeliveryDelayOverride")) && this.bean.isRedeliveryDelayOverrideSet()) {
               copy.setRedeliveryDelayOverride(this.bean.getRedeliveryDelayOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RedeliveryLimit")) && this.bean.isRedeliveryLimitSet()) {
               copy.setRedeliveryLimit(this.bean.getRedeliveryLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TimeToDeliverOverride")) && this.bean.isTimeToDeliverOverrideSet()) {
               copy.setTimeToDeliverOverride(this.bean.getTimeToDeliverOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TimeToLiveOverride")) && this.bean.isTimeToLiveOverrideSet()) {
               copy.setTimeToLiveOverride(this.bean.getTimeToLiveOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesPagingEnabled")) && this.bean.isBytesPagingEnabledSet()) {
               copy.setBytesPagingEnabled(this.bean.isBytesPagingEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesPagingEnabled")) && this.bean.isMessagesPagingEnabledSet()) {
               copy.setMessagesPagingEnabled(this.bean.isMessagesPagingEnabled());
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
         this.inferSubTree(this.bean.getDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getErrorDestination(), clazz, annotation);
      }
   }
}
