package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import weblogic.management.mbeans.custom.SAFAgent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SAFAgentMBeanImpl extends DeploymentMBeanImpl implements SAFAgentMBean, Serializable {
   private long _AcknowledgeInterval;
   private long _BytesMaximum;
   private long _BytesThresholdHigh;
   private long _BytesThresholdLow;
   private long _ConversationIdleTimeMaximum;
   private long _DefaultRetryDelayBase;
   private long _DefaultRetryDelayMaximum;
   private double _DefaultRetryDelayMultiplier;
   private long _DefaultTimeToLive;
   private boolean _DynamicallyCreated;
   private boolean _ForwardingPausedAtStartup;
   private boolean _IncomingPausedAtStartup;
   private JMSSAFMessageLogFileMBean _JMSSAFMessageLogFile;
   private boolean _LoggingEnabled;
   private int _MaximumMessageSize;
   private long _MessageBufferSize;
   private String _MessageCompressionOptions;
   private String _MessageCompressionOptionsOverride;
   private long _MessagesMaximum;
   private long _MessagesThresholdHigh;
   private long _MessagesThresholdLow;
   private String _Name;
   private String _PagingDirectory;
   private boolean _PagingMessageCompressionEnabled;
   private boolean _ReceivingPausedAtStartup;
   private Set _ServerNames;
   private String _ServiceType;
   private PersistentStoreMBean _Store;
   private boolean _StoreMessageCompressionEnabled;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private long _WindowInterval;
   private int _WindowSize;
   private transient SAFAgent _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private SAFAgentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(SAFAgentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(SAFAgentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public SAFAgentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(SAFAgentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      SAFAgentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._JMSSAFMessageLogFile instanceof JMSSAFMessageLogFileMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getJMSSAFMessageLogFile() != null) {
            this._getReferenceManager().unregisterBean((JMSSAFMessageLogFileMBeanImpl)oldDelegate.getJMSSAFMessageLogFile());
         }

         if (delegate != null && delegate.getJMSSAFMessageLogFile() != null) {
            this._getReferenceManager().registerBean((JMSSAFMessageLogFileMBeanImpl)delegate.getJMSSAFMessageLogFile(), false);
         }

         ((JMSSAFMessageLogFileMBeanImpl)this._JMSSAFMessageLogFile)._setDelegateBean((JMSSAFMessageLogFileMBeanImpl)((JMSSAFMessageLogFileMBeanImpl)(delegate == null ? null : delegate.getJMSSAFMessageLogFile())));
      }

      if (this._Store instanceof PersistentStoreMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getStore() != null) {
            this._getReferenceManager().unregisterBean((PersistentStoreMBeanImpl)oldDelegate.getStore());
         }

         if (delegate != null && delegate.getStore() != null) {
            this._getReferenceManager().registerBean((PersistentStoreMBeanImpl)delegate.getStore(), false);
         }

         ((PersistentStoreMBeanImpl)this._Store)._setDelegateBean((PersistentStoreMBeanImpl)((PersistentStoreMBeanImpl)(delegate == null ? null : delegate.getStore())));
      }

   }

   public SAFAgentMBeanImpl() {
      try {
         this._customizer = new SAFAgent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SAFAgentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SAFAgent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SAFAgentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SAFAgent(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

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

         return this._customizer.getName();
      }
   }

   public Set getServerNames() {
      return this._customizer.getServerNames();
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isServerNamesInherited() {
      return false;
   }

   public boolean isServerNamesSet() {
      return this._isSet(12);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setServerNames(Set param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ServerNames = param0;
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
                        SAFAgentMBeanImpl.this.addTarget((TargetMBean)value);
                        SAFAgentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SAFAgentMBeanImpl.this._Targets, this.getHandbackObject());
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
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
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
      JMSLegalHelper.validateSAFAgentTargets(param0);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return SAFAgentMBeanImpl.this.getTargets();
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
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new;
         if (this._isSet(10)) {
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

   public PersistentStoreMBean getStore() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getStore() : this._Store;
   }

   public String getStoreAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getStore();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isStoreInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isStoreSet() {
      return this._isSet(13);
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

   public void setStoreAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, PersistentStoreMBean.class, new ReferenceManager.Resolver(this, 13) {
            public void resolveReference(Object value) {
               try {
                  SAFAgentMBeanImpl.this.setStore((PersistentStoreMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         PersistentStoreMBean _oldVal = this._Store;
         this._initializeProperty(13);
         this._postSet(13, _oldVal, this._Store);
      }

   }

   public void setStore(PersistentStoreMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 13, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SAFAgentMBeanImpl.this.getStore();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(13);
      PersistentStoreMBean _oldVal = this._Store;
      this._Store = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesMaximum() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getBytesMaximum() : this._BytesMaximum;
   }

   public boolean isBytesMaximumInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isBytesMaximumSet() {
      return this._isSet(14);
   }

   public void setBytesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("BytesMaximum", param0, -1L);
      boolean wasSet = this._isSet(14);
      long _oldVal = this._BytesMaximum;
      this._BytesMaximum = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesThresholdHigh() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getBytesThresholdHigh() : this._BytesThresholdHigh;
   }

   public boolean isBytesThresholdHighInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isBytesThresholdHighSet() {
      return this._isSet(15);
   }

   public void setBytesThresholdHigh(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("BytesThresholdHigh", param0, -1L);
      boolean wasSet = this._isSet(15);
      long _oldVal = this._BytesThresholdHigh;
      this._BytesThresholdHigh = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesThresholdLow() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getBytesThresholdLow() : this._BytesThresholdLow;
   }

   public boolean isBytesThresholdLowInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isBytesThresholdLowSet() {
      return this._isSet(16);
   }

   public void setBytesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("BytesThresholdLow", param0, -1L);
      boolean wasSet = this._isSet(16);
      long _oldVal = this._BytesThresholdLow;
      this._BytesThresholdLow = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public long getMessagesMaximum() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getMessagesMaximum() : this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(17);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMessagesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MessagesMaximum", param0, -1L);
      boolean wasSet = this._isSet(17);
      long _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesThresholdHigh() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getMessagesThresholdHigh() : this._MessagesThresholdHigh;
   }

   public boolean isMessagesThresholdHighInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isMessagesThresholdHighSet() {
      return this._isSet(18);
   }

   public void setMessagesThresholdHigh(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MessagesThresholdHigh", param0, -1L);
      boolean wasSet = this._isSet(18);
      long _oldVal = this._MessagesThresholdHigh;
      this._MessagesThresholdHigh = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesThresholdLow() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getMessagesThresholdLow() : this._MessagesThresholdLow;
   }

   public boolean isMessagesThresholdLowInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isMessagesThresholdLowSet() {
      return this._isSet(19);
   }

   public void setMessagesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MessagesThresholdLow", param0, -1L);
      boolean wasSet = this._isSet(19);
      long _oldVal = this._MessagesThresholdLow;
      this._MessagesThresholdLow = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaximumMessageSize() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getMaximumMessageSize() : this._MaximumMessageSize;
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

   public boolean isMaximumMessageSizeInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(20);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setMaximumMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MaximumMessageSize", param0, 0);
      boolean wasSet = this._isSet(20);
      int _oldVal = this._MaximumMessageSize;
      this._MaximumMessageSize = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public long getDefaultRetryDelayBase() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getDefaultRetryDelayBase() : this._DefaultRetryDelayBase;
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isDefaultRetryDelayBaseInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isDefaultRetryDelayBaseSet() {
      return this._isSet(21);
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setDefaultRetryDelayBase(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("DefaultRetryDelayBase", param0, 1L);
      boolean wasSet = this._isSet(21);
      long _oldVal = this._DefaultRetryDelayBase;
      this._DefaultRetryDelayBase = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
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
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
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

   public long getDefaultRetryDelayMaximum() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getDefaultRetryDelayMaximum() : this._DefaultRetryDelayMaximum;
   }

   public boolean isDefaultRetryDelayMaximumInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isDefaultRetryDelayMaximumSet() {
      return this._isSet(22);
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

   public void setDefaultRetryDelayMaximum(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("DefaultRetryDelayMaximum", param0, 1L);
      boolean wasSet = this._isSet(22);
      long _oldVal = this._DefaultRetryDelayMaximum;
      this._DefaultRetryDelayMaximum = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public double getDefaultRetryDelayMultiplier() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getDefaultRetryDelayMultiplier() : this._DefaultRetryDelayMultiplier;
   }

   public boolean isDefaultRetryDelayMultiplierInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isDefaultRetryDelayMultiplierSet() {
      return this._isSet(23);
   }

   public void setDefaultRetryDelayMultiplier(double param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("DefaultRetryDelayMultiplier", param0, 1.0);
      boolean wasSet = this._isSet(23);
      double _oldVal = this._DefaultRetryDelayMultiplier;
      this._DefaultRetryDelayMultiplier = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public String getServiceType() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getServiceType(), this) : this._ServiceType;
   }

   public boolean isServiceTypeInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isServiceTypeSet() {
      return this._isSet(24);
   }

   public void setServiceType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Both", "Sending-only", "Receiving-only"};
      param0 = LegalChecks.checkInEnum("ServiceType", param0, _set);
      boolean wasSet = this._isSet(24);
      String _oldVal = this._ServiceType;
      this._ServiceType = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var5.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getWindowSize() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getWindowSize() : this._WindowSize;
   }

   public boolean isWindowSizeInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isWindowSizeSet() {
      return this._isSet(25);
   }

   public void setWindowSize(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("WindowSize", param0, 1);
      boolean wasSet = this._isSet(25);
      int _oldVal = this._WindowSize;
      this._WindowSize = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLoggingEnabled() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().isLoggingEnabled() : this._LoggingEnabled;
   }

   public boolean isLoggingEnabledInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(26);
   }

   public void setLoggingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._LoggingEnabled;
      this._LoggingEnabled = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public long getConversationIdleTimeMaximum() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().getConversationIdleTimeMaximum() : this._ConversationIdleTimeMaximum;
   }

   public boolean isConversationIdleTimeMaximumInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isConversationIdleTimeMaximumSet() {
      return this._isSet(27);
   }

   public void setConversationIdleTimeMaximum(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("ConversationIdleTimeMaximum", param0, 0L);
      boolean wasSet = this._isSet(27);
      long _oldVal = this._ConversationIdleTimeMaximum;
      this._ConversationIdleTimeMaximum = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public long getAcknowledgeInterval() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getAcknowledgeInterval() : this._AcknowledgeInterval;
   }

   public boolean isAcknowledgeIntervalInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isAcknowledgeIntervalSet() {
      return this._isSet(28);
   }

   public void setAcknowledgeInterval(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      JMSLegalHelper.validateAcknowledgeIntervalValue(param0);
      boolean wasSet = this._isSet(28);
      long _oldVal = this._AcknowledgeInterval;
      this._AcknowledgeInterval = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public long getDefaultTimeToLive() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getDefaultTimeToLive() : this._DefaultTimeToLive;
   }

   public boolean isDefaultTimeToLiveInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isDefaultTimeToLiveSet() {
      return this._isSet(29);
   }

   public void setDefaultTimeToLive(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("DefaultTimeToLive", param0, 0L);
      boolean wasSet = this._isSet(29);
      long _oldVal = this._DefaultTimeToLive;
      this._DefaultTimeToLive = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIncomingPausedAtStartup() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().isIncomingPausedAtStartup() : this._IncomingPausedAtStartup;
   }

   public boolean isIncomingPausedAtStartupInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isIncomingPausedAtStartupSet() {
      return this._isSet(30);
   }

   public void setIncomingPausedAtStartup(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._IncomingPausedAtStartup;
      this._IncomingPausedAtStartup = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isForwardingPausedAtStartup() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().isForwardingPausedAtStartup() : this._ForwardingPausedAtStartup;
   }

   public boolean isForwardingPausedAtStartupInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isForwardingPausedAtStartupSet() {
      return this._isSet(31);
   }

   public void setForwardingPausedAtStartup(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      boolean _oldVal = this._ForwardingPausedAtStartup;
      this._ForwardingPausedAtStartup = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isReceivingPausedAtStartup() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isReceivingPausedAtStartup() : this._ReceivingPausedAtStartup;
   }

   public boolean isReceivingPausedAtStartupInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isReceivingPausedAtStartupSet() {
      return this._isSet(32);
   }

   public void setReceivingPausedAtStartup(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      boolean _oldVal = this._ReceivingPausedAtStartup;
      this._ReceivingPausedAtStartup = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessageBufferSize() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getMessageBufferSize() : this._MessageBufferSize;
   }

   public boolean isMessageBufferSizeInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isMessageBufferSizeSet() {
      return this._isSet(33);
   }

   public void setMessageBufferSize(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MessageBufferSize", param0, -1L);
      boolean wasSet = this._isSet(33);
      long _oldVal = this._MessageBufferSize;
      this._MessageBufferSize = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPagingDirectory() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._performMacroSubstitution(this._getDelegateBean().getPagingDirectory(), this) : this._PagingDirectory;
   }

   public boolean isPagingDirectoryInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isPagingDirectorySet() {
      return this._isSet(34);
   }

   public void setPagingDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      String _oldVal = this._PagingDirectory;
      this._PagingDirectory = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public long getWindowInterval() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().getWindowInterval() : this._WindowInterval;
   }

   public boolean isWindowIntervalInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isWindowIntervalSet() {
      return this._isSet(35);
   }

   public void setWindowInterval(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("WindowInterval", param0, 0L);
      boolean wasSet = this._isSet(35);
      long _oldVal = this._WindowInterval;
      this._WindowInterval = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var6.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSSAFMessageLogFileMBean getJMSSAFMessageLogFile() {
      return this._JMSSAFMessageLogFile;
   }

   public boolean isJMSSAFMessageLogFileInherited() {
      return false;
   }

   public boolean isJMSSAFMessageLogFileSet() {
      return this._isSet(36) || this._isAnythingSet((AbstractDescriptorBean)this.getJMSSAFMessageLogFile());
   }

   public void setJMSSAFMessageLogFile(JMSSAFMessageLogFileMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 36)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(36);
      JMSSAFMessageLogFileMBean _oldVal = this._JMSSAFMessageLogFile;
      this._JMSSAFMessageLogFile = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var5.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStoreMessageCompressionEnabled() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().isStoreMessageCompressionEnabled() : this._StoreMessageCompressionEnabled;
   }

   public boolean isStoreMessageCompressionEnabledInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isStoreMessageCompressionEnabledSet() {
      return this._isSet(37);
   }

   public void setStoreMessageCompressionEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(37);
      boolean _oldVal = this._StoreMessageCompressionEnabled;
      this._StoreMessageCompressionEnabled = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPagingMessageCompressionEnabled() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().isPagingMessageCompressionEnabled() : this._PagingMessageCompressionEnabled;
   }

   public boolean isPagingMessageCompressionEnabledInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isPagingMessageCompressionEnabledSet() {
      return this._isSet(38);
   }

   public void setPagingMessageCompressionEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(38);
      boolean _oldVal = this._PagingMessageCompressionEnabled;
      this._PagingMessageCompressionEnabled = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public void setMessageCompressionOptionsOverride(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(39);
      String _oldVal = this._MessageCompressionOptionsOverride;
      this._MessageCompressionOptionsOverride = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessageCompressionOptionsOverride() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._performMacroSubstitution(this._getDelegateBean().getMessageCompressionOptionsOverride(), this) : this._MessageCompressionOptionsOverride;
   }

   public boolean isMessageCompressionOptionsOverrideInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isMessageCompressionOptionsOverrideSet() {
      return this._isSet(39);
   }

   public void setMessageCompressionOptions(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"GZIP_DEFAULT_COMPRESSION", "GZIP_BEST_COMPRESSION", "GZIP_BEST_SPEED", "LZF"};
      param0 = LegalChecks.checkInEnum("MessageCompressionOptions", param0, _set);
      boolean wasSet = this._isSet(40);
      String _oldVal = this._MessageCompressionOptions;
      this._MessageCompressionOptions = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SAFAgentMBeanImpl source = (SAFAgentMBeanImpl)var5.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessageCompressionOptions() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._performMacroSubstitution(this._getDelegateBean().getMessageCompressionOptions(), this) : this._MessageCompressionOptions;
   }

   public boolean isMessageCompressionOptionsInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isMessageCompressionOptionsSet() {
      return this._isSet(40);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSLegalHelper.validateSAFAgent(this);
      JMSLegalHelper.validateServerBytesValues(this);
      JMSLegalHelper.validateRetryBaseAndMax(this);
      JMSLegalHelper.validateServerMessagesValues(this);
      JMSLegalHelper.validateSAFAgentServiceTypeInRG(this, this.getServiceType());
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
      return super._isAnythingSet() || this.isJMSSAFMessageLogFileSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 28;
      }

      try {
         switch (idx) {
            case 28:
               this._AcknowledgeInterval = -1L;
               if (initOne) {
                  break;
               }
            case 14:
               this._BytesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 15:
               this._BytesThresholdHigh = -1L;
               if (initOne) {
                  break;
               }
            case 16:
               this._BytesThresholdLow = -1L;
               if (initOne) {
                  break;
               }
            case 27:
               this._ConversationIdleTimeMaximum = 0L;
               if (initOne) {
                  break;
               }
            case 21:
               this._DefaultRetryDelayBase = 20000L;
               if (initOne) {
                  break;
               }
            case 22:
               this._DefaultRetryDelayMaximum = 180000L;
               if (initOne) {
                  break;
               }
            case 23:
               this._DefaultRetryDelayMultiplier = 1.0;
               if (initOne) {
                  break;
               }
            case 29:
               this._DefaultTimeToLive = 0L;
               if (initOne) {
                  break;
               }
            case 36:
               this._JMSSAFMessageLogFile = new JMSSAFMessageLogFileMBeanImpl(this, 36);
               this._postCreate((AbstractDescriptorBean)this._JMSSAFMessageLogFile);
               if (initOne) {
                  break;
               }
            case 20:
               this._MaximumMessageSize = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 33:
               this._MessageBufferSize = -1L;
               if (initOne) {
                  break;
               }
            case 40:
               this._MessageCompressionOptions = "GZIP_DEFAULT_COMPRESSION";
               if (initOne) {
                  break;
               }
            case 39:
               this._MessageCompressionOptionsOverride = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._MessagesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 18:
               this._MessagesThresholdHigh = -1L;
               if (initOne) {
                  break;
               }
            case 19:
               this._MessagesThresholdLow = -1L;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 34:
               this._PagingDirectory = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._ServiceType = "Both";
               if (initOne) {
                  break;
               }
            case 13:
               this._Store = null;
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
            case 35:
               this._WindowInterval = 0L;
               if (initOne) {
                  break;
               }
            case 25:
               this._WindowSize = 10;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._ForwardingPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 30:
               this._IncomingPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._LoggingEnabled = true;
               if (initOne) {
                  break;
               }
            case 38:
               this._PagingMessageCompressionEnabled = false;
               if (initOne) {
                  break;
               }
            case 32:
               this._ReceivingPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 37:
               this._StoreMessageCompressionEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
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
      return "SAFAgent";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("AcknowledgeInterval")) {
         oldVal = this._AcknowledgeInterval;
         this._AcknowledgeInterval = (Long)v;
         this._postSet(28, oldVal, this._AcknowledgeInterval);
      } else if (name.equals("BytesMaximum")) {
         oldVal = this._BytesMaximum;
         this._BytesMaximum = (Long)v;
         this._postSet(14, oldVal, this._BytesMaximum);
      } else if (name.equals("BytesThresholdHigh")) {
         oldVal = this._BytesThresholdHigh;
         this._BytesThresholdHigh = (Long)v;
         this._postSet(15, oldVal, this._BytesThresholdHigh);
      } else if (name.equals("BytesThresholdLow")) {
         oldVal = this._BytesThresholdLow;
         this._BytesThresholdLow = (Long)v;
         this._postSet(16, oldVal, this._BytesThresholdLow);
      } else if (name.equals("ConversationIdleTimeMaximum")) {
         oldVal = this._ConversationIdleTimeMaximum;
         this._ConversationIdleTimeMaximum = (Long)v;
         this._postSet(27, oldVal, this._ConversationIdleTimeMaximum);
      } else if (name.equals("DefaultRetryDelayBase")) {
         oldVal = this._DefaultRetryDelayBase;
         this._DefaultRetryDelayBase = (Long)v;
         this._postSet(21, oldVal, this._DefaultRetryDelayBase);
      } else if (name.equals("DefaultRetryDelayMaximum")) {
         oldVal = this._DefaultRetryDelayMaximum;
         this._DefaultRetryDelayMaximum = (Long)v;
         this._postSet(22, oldVal, this._DefaultRetryDelayMaximum);
      } else if (name.equals("DefaultRetryDelayMultiplier")) {
         double oldVal = this._DefaultRetryDelayMultiplier;
         this._DefaultRetryDelayMultiplier = (Double)v;
         this._postSet(23, oldVal, this._DefaultRetryDelayMultiplier);
      } else if (name.equals("DefaultTimeToLive")) {
         oldVal = this._DefaultTimeToLive;
         this._DefaultTimeToLive = (Long)v;
         this._postSet(29, oldVal, this._DefaultTimeToLive);
      } else {
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("ForwardingPausedAtStartup")) {
            oldVal = this._ForwardingPausedAtStartup;
            this._ForwardingPausedAtStartup = (Boolean)v;
            this._postSet(31, oldVal, this._ForwardingPausedAtStartup);
         } else if (name.equals("IncomingPausedAtStartup")) {
            oldVal = this._IncomingPausedAtStartup;
            this._IncomingPausedAtStartup = (Boolean)v;
            this._postSet(30, oldVal, this._IncomingPausedAtStartup);
         } else if (name.equals("JMSSAFMessageLogFile")) {
            JMSSAFMessageLogFileMBean oldVal = this._JMSSAFMessageLogFile;
            this._JMSSAFMessageLogFile = (JMSSAFMessageLogFileMBean)v;
            this._postSet(36, oldVal, this._JMSSAFMessageLogFile);
         } else if (name.equals("LoggingEnabled")) {
            oldVal = this._LoggingEnabled;
            this._LoggingEnabled = (Boolean)v;
            this._postSet(26, oldVal, this._LoggingEnabled);
         } else {
            int oldVal;
            if (name.equals("MaximumMessageSize")) {
               oldVal = this._MaximumMessageSize;
               this._MaximumMessageSize = (Integer)v;
               this._postSet(20, oldVal, this._MaximumMessageSize);
            } else if (name.equals("MessageBufferSize")) {
               oldVal = this._MessageBufferSize;
               this._MessageBufferSize = (Long)v;
               this._postSet(33, oldVal, this._MessageBufferSize);
            } else {
               String oldVal;
               if (name.equals("MessageCompressionOptions")) {
                  oldVal = this._MessageCompressionOptions;
                  this._MessageCompressionOptions = (String)v;
                  this._postSet(40, oldVal, this._MessageCompressionOptions);
               } else if (name.equals("MessageCompressionOptionsOverride")) {
                  oldVal = this._MessageCompressionOptionsOverride;
                  this._MessageCompressionOptionsOverride = (String)v;
                  this._postSet(39, oldVal, this._MessageCompressionOptionsOverride);
               } else if (name.equals("MessagesMaximum")) {
                  oldVal = this._MessagesMaximum;
                  this._MessagesMaximum = (Long)v;
                  this._postSet(17, oldVal, this._MessagesMaximum);
               } else if (name.equals("MessagesThresholdHigh")) {
                  oldVal = this._MessagesThresholdHigh;
                  this._MessagesThresholdHigh = (Long)v;
                  this._postSet(18, oldVal, this._MessagesThresholdHigh);
               } else if (name.equals("MessagesThresholdLow")) {
                  oldVal = this._MessagesThresholdLow;
                  this._MessagesThresholdLow = (Long)v;
                  this._postSet(19, oldVal, this._MessagesThresholdLow);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("PagingDirectory")) {
                  oldVal = this._PagingDirectory;
                  this._PagingDirectory = (String)v;
                  this._postSet(34, oldVal, this._PagingDirectory);
               } else if (name.equals("PagingMessageCompressionEnabled")) {
                  oldVal = this._PagingMessageCompressionEnabled;
                  this._PagingMessageCompressionEnabled = (Boolean)v;
                  this._postSet(38, oldVal, this._PagingMessageCompressionEnabled);
               } else if (name.equals("ReceivingPausedAtStartup")) {
                  oldVal = this._ReceivingPausedAtStartup;
                  this._ReceivingPausedAtStartup = (Boolean)v;
                  this._postSet(32, oldVal, this._ReceivingPausedAtStartup);
               } else if (name.equals("ServerNames")) {
                  Set oldVal = this._ServerNames;
                  this._ServerNames = (Set)v;
                  this._postSet(12, oldVal, this._ServerNames);
               } else if (name.equals("ServiceType")) {
                  oldVal = this._ServiceType;
                  this._ServiceType = (String)v;
                  this._postSet(24, oldVal, this._ServiceType);
               } else if (name.equals("Store")) {
                  PersistentStoreMBean oldVal = this._Store;
                  this._Store = (PersistentStoreMBean)v;
                  this._postSet(13, oldVal, this._Store);
               } else if (name.equals("StoreMessageCompressionEnabled")) {
                  oldVal = this._StoreMessageCompressionEnabled;
                  this._StoreMessageCompressionEnabled = (Boolean)v;
                  this._postSet(37, oldVal, this._StoreMessageCompressionEnabled);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Targets")) {
                  TargetMBean[] oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(10, oldVal, this._Targets);
               } else if (name.equals("WindowInterval")) {
                  oldVal = this._WindowInterval;
                  this._WindowInterval = (Long)v;
                  this._postSet(35, oldVal, this._WindowInterval);
               } else if (name.equals("WindowSize")) {
                  oldVal = this._WindowSize;
                  this._WindowSize = (Integer)v;
                  this._postSet(25, oldVal, this._WindowSize);
               } else if (name.equals("customizer")) {
                  SAFAgent oldVal = this._customizer;
                  this._customizer = (SAFAgent)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcknowledgeInterval")) {
         return new Long(this._AcknowledgeInterval);
      } else if (name.equals("BytesMaximum")) {
         return new Long(this._BytesMaximum);
      } else if (name.equals("BytesThresholdHigh")) {
         return new Long(this._BytesThresholdHigh);
      } else if (name.equals("BytesThresholdLow")) {
         return new Long(this._BytesThresholdLow);
      } else if (name.equals("ConversationIdleTimeMaximum")) {
         return new Long(this._ConversationIdleTimeMaximum);
      } else if (name.equals("DefaultRetryDelayBase")) {
         return new Long(this._DefaultRetryDelayBase);
      } else if (name.equals("DefaultRetryDelayMaximum")) {
         return new Long(this._DefaultRetryDelayMaximum);
      } else if (name.equals("DefaultRetryDelayMultiplier")) {
         return new Double(this._DefaultRetryDelayMultiplier);
      } else if (name.equals("DefaultTimeToLive")) {
         return new Long(this._DefaultTimeToLive);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ForwardingPausedAtStartup")) {
         return new Boolean(this._ForwardingPausedAtStartup);
      } else if (name.equals("IncomingPausedAtStartup")) {
         return new Boolean(this._IncomingPausedAtStartup);
      } else if (name.equals("JMSSAFMessageLogFile")) {
         return this._JMSSAFMessageLogFile;
      } else if (name.equals("LoggingEnabled")) {
         return new Boolean(this._LoggingEnabled);
      } else if (name.equals("MaximumMessageSize")) {
         return new Integer(this._MaximumMessageSize);
      } else if (name.equals("MessageBufferSize")) {
         return new Long(this._MessageBufferSize);
      } else if (name.equals("MessageCompressionOptions")) {
         return this._MessageCompressionOptions;
      } else if (name.equals("MessageCompressionOptionsOverride")) {
         return this._MessageCompressionOptionsOverride;
      } else if (name.equals("MessagesMaximum")) {
         return new Long(this._MessagesMaximum);
      } else if (name.equals("MessagesThresholdHigh")) {
         return new Long(this._MessagesThresholdHigh);
      } else if (name.equals("MessagesThresholdLow")) {
         return new Long(this._MessagesThresholdLow);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PagingDirectory")) {
         return this._PagingDirectory;
      } else if (name.equals("PagingMessageCompressionEnabled")) {
         return new Boolean(this._PagingMessageCompressionEnabled);
      } else if (name.equals("ReceivingPausedAtStartup")) {
         return new Boolean(this._ReceivingPausedAtStartup);
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("ServiceType")) {
         return this._ServiceType;
      } else if (name.equals("Store")) {
         return this._Store;
      } else if (name.equals("StoreMessageCompressionEnabled")) {
         return new Boolean(this._StoreMessageCompressionEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("WindowInterval")) {
         return new Long(this._WindowInterval);
      } else if (name.equals("WindowSize")) {
         return new Integer(this._WindowSize);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
               if (s.equals("store")) {
                  return 13;
               }
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
            case 7:
            case 8:
            case 9:
            case 10:
            case 14:
            case 17:
            case 18:
            case 21:
            case 25:
            case 29:
            case 31:
            case 32:
            case 35:
            default:
               break;
            case 11:
               if (s.equals("window-size")) {
                  return 25;
               }
               break;
            case 12:
               if (s.equals("server-names")) {
                  return 12;
               }

               if (s.equals("service-type")) {
                  return 24;
               }
               break;
            case 13:
               if (s.equals("bytes-maximum")) {
                  return 14;
               }
               break;
            case 15:
               if (s.equals("window-interval")) {
                  return 35;
               }

               if (s.equals("logging-enabled")) {
                  return 26;
               }
               break;
            case 16:
               if (s.equals("messages-maximum")) {
                  return 17;
               }

               if (s.equals("paging-directory")) {
                  return 34;
               }
               break;
            case 19:
               if (s.equals("bytes-threshold-low")) {
                  return 16;
               }

               if (s.equals("message-buffer-size")) {
                  return 33;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("acknowledge-interval")) {
                  return 28;
               }

               if (s.equals("bytes-threshold-high")) {
                  return 15;
               }

               if (s.equals("default-time-to-live")) {
                  return 29;
               }

               if (s.equals("maximum-message-size")) {
                  return 20;
               }
               break;
            case 22:
               if (s.equals("messages-threshold-low")) {
                  return 19;
               }
               break;
            case 23:
               if (s.equals("messages-threshold-high")) {
                  return 18;
               }
               break;
            case 24:
               if (s.equals("default-retry-delay-base")) {
                  return 21;
               }

               if (s.equals("jms-saf-message-log-file")) {
                  return 36;
               }
               break;
            case 26:
               if (s.equals("incoming-paused-at-startup")) {
                  return 30;
               }
               break;
            case 27:
               if (s.equals("default-retry-delay-maximum")) {
                  return 22;
               }

               if (s.equals("message-compression-options")) {
                  return 40;
               }

               if (s.equals("receiving-paused-at-startup")) {
                  return 32;
               }
               break;
            case 28:
               if (s.equals("forwarding-paused-at-startup")) {
                  return 31;
               }
               break;
            case 30:
               if (s.equals("conversation-idle-time-maximum")) {
                  return 27;
               }

               if (s.equals("default-retry-delay-multiplier")) {
                  return 23;
               }
               break;
            case 33:
               if (s.equals("store-message-compression-enabled")) {
                  return 37;
               }
               break;
            case 34:
               if (s.equals("paging-message-compression-enabled")) {
                  return 38;
               }
               break;
            case 36:
               if (s.equals("message-compression-options-override")) {
                  return 39;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 36:
               return new JMSSAFMessageLogFileMBeanImpl.SchemaHelper2();
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "server-names";
            case 13:
               return "store";
            case 14:
               return "bytes-maximum";
            case 15:
               return "bytes-threshold-high";
            case 16:
               return "bytes-threshold-low";
            case 17:
               return "messages-maximum";
            case 18:
               return "messages-threshold-high";
            case 19:
               return "messages-threshold-low";
            case 20:
               return "maximum-message-size";
            case 21:
               return "default-retry-delay-base";
            case 22:
               return "default-retry-delay-maximum";
            case 23:
               return "default-retry-delay-multiplier";
            case 24:
               return "service-type";
            case 25:
               return "window-size";
            case 26:
               return "logging-enabled";
            case 27:
               return "conversation-idle-time-maximum";
            case 28:
               return "acknowledge-interval";
            case 29:
               return "default-time-to-live";
            case 30:
               return "incoming-paused-at-startup";
            case 31:
               return "forwarding-paused-at-startup";
            case 32:
               return "receiving-paused-at-startup";
            case 33:
               return "message-buffer-size";
            case 34:
               return "paging-directory";
            case 35:
               return "window-interval";
            case 36:
               return "jms-saf-message-log-file";
            case 37:
               return "store-message-compression-enabled";
            case 38:
               return "paging-message-compression-enabled";
            case 39:
               return "message-compression-options-override";
            case 40:
               return "message-compression-options";
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
            case 36:
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private SAFAgentMBeanImpl bean;

      protected Helper(SAFAgentMBeanImpl bean) {
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
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "ServerNames";
            case 13:
               return "Store";
            case 14:
               return "BytesMaximum";
            case 15:
               return "BytesThresholdHigh";
            case 16:
               return "BytesThresholdLow";
            case 17:
               return "MessagesMaximum";
            case 18:
               return "MessagesThresholdHigh";
            case 19:
               return "MessagesThresholdLow";
            case 20:
               return "MaximumMessageSize";
            case 21:
               return "DefaultRetryDelayBase";
            case 22:
               return "DefaultRetryDelayMaximum";
            case 23:
               return "DefaultRetryDelayMultiplier";
            case 24:
               return "ServiceType";
            case 25:
               return "WindowSize";
            case 26:
               return "LoggingEnabled";
            case 27:
               return "ConversationIdleTimeMaximum";
            case 28:
               return "AcknowledgeInterval";
            case 29:
               return "DefaultTimeToLive";
            case 30:
               return "IncomingPausedAtStartup";
            case 31:
               return "ForwardingPausedAtStartup";
            case 32:
               return "ReceivingPausedAtStartup";
            case 33:
               return "MessageBufferSize";
            case 34:
               return "PagingDirectory";
            case 35:
               return "WindowInterval";
            case 36:
               return "JMSSAFMessageLogFile";
            case 37:
               return "StoreMessageCompressionEnabled";
            case 38:
               return "PagingMessageCompressionEnabled";
            case 39:
               return "MessageCompressionOptionsOverride";
            case 40:
               return "MessageCompressionOptions";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcknowledgeInterval")) {
            return 28;
         } else if (propName.equals("BytesMaximum")) {
            return 14;
         } else if (propName.equals("BytesThresholdHigh")) {
            return 15;
         } else if (propName.equals("BytesThresholdLow")) {
            return 16;
         } else if (propName.equals("ConversationIdleTimeMaximum")) {
            return 27;
         } else if (propName.equals("DefaultRetryDelayBase")) {
            return 21;
         } else if (propName.equals("DefaultRetryDelayMaximum")) {
            return 22;
         } else if (propName.equals("DefaultRetryDelayMultiplier")) {
            return 23;
         } else if (propName.equals("DefaultTimeToLive")) {
            return 29;
         } else if (propName.equals("JMSSAFMessageLogFile")) {
            return 36;
         } else if (propName.equals("MaximumMessageSize")) {
            return 20;
         } else if (propName.equals("MessageBufferSize")) {
            return 33;
         } else if (propName.equals("MessageCompressionOptions")) {
            return 40;
         } else if (propName.equals("MessageCompressionOptionsOverride")) {
            return 39;
         } else if (propName.equals("MessagesMaximum")) {
            return 17;
         } else if (propName.equals("MessagesThresholdHigh")) {
            return 18;
         } else if (propName.equals("MessagesThresholdLow")) {
            return 19;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PagingDirectory")) {
            return 34;
         } else if (propName.equals("ServerNames")) {
            return 12;
         } else if (propName.equals("ServiceType")) {
            return 24;
         } else if (propName.equals("Store")) {
            return 13;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("WindowInterval")) {
            return 35;
         } else if (propName.equals("WindowSize")) {
            return 25;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("ForwardingPausedAtStartup")) {
            return 31;
         } else if (propName.equals("IncomingPausedAtStartup")) {
            return 30;
         } else if (propName.equals("LoggingEnabled")) {
            return 26;
         } else if (propName.equals("PagingMessageCompressionEnabled")) {
            return 38;
         } else if (propName.equals("ReceivingPausedAtStartup")) {
            return 32;
         } else {
            return propName.equals("StoreMessageCompressionEnabled") ? 37 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getJMSSAFMessageLogFile() != null) {
            iterators.add(new ArrayIterator(new JMSSAFMessageLogFileMBean[]{this.bean.getJMSSAFMessageLogFile()}));
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
            if (this.bean.isAcknowledgeIntervalSet()) {
               buf.append("AcknowledgeInterval");
               buf.append(String.valueOf(this.bean.getAcknowledgeInterval()));
            }

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

            if (this.bean.isConversationIdleTimeMaximumSet()) {
               buf.append("ConversationIdleTimeMaximum");
               buf.append(String.valueOf(this.bean.getConversationIdleTimeMaximum()));
            }

            if (this.bean.isDefaultRetryDelayBaseSet()) {
               buf.append("DefaultRetryDelayBase");
               buf.append(String.valueOf(this.bean.getDefaultRetryDelayBase()));
            }

            if (this.bean.isDefaultRetryDelayMaximumSet()) {
               buf.append("DefaultRetryDelayMaximum");
               buf.append(String.valueOf(this.bean.getDefaultRetryDelayMaximum()));
            }

            if (this.bean.isDefaultRetryDelayMultiplierSet()) {
               buf.append("DefaultRetryDelayMultiplier");
               buf.append(String.valueOf(this.bean.getDefaultRetryDelayMultiplier()));
            }

            if (this.bean.isDefaultTimeToLiveSet()) {
               buf.append("DefaultTimeToLive");
               buf.append(String.valueOf(this.bean.getDefaultTimeToLive()));
            }

            childValue = this.computeChildHashValue(this.bean.getJMSSAFMessageLogFile());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaximumMessageSizeSet()) {
               buf.append("MaximumMessageSize");
               buf.append(String.valueOf(this.bean.getMaximumMessageSize()));
            }

            if (this.bean.isMessageBufferSizeSet()) {
               buf.append("MessageBufferSize");
               buf.append(String.valueOf(this.bean.getMessageBufferSize()));
            }

            if (this.bean.isMessageCompressionOptionsSet()) {
               buf.append("MessageCompressionOptions");
               buf.append(String.valueOf(this.bean.getMessageCompressionOptions()));
            }

            if (this.bean.isMessageCompressionOptionsOverrideSet()) {
               buf.append("MessageCompressionOptionsOverride");
               buf.append(String.valueOf(this.bean.getMessageCompressionOptionsOverride()));
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

            if (this.bean.isPagingDirectorySet()) {
               buf.append("PagingDirectory");
               buf.append(String.valueOf(this.bean.getPagingDirectory()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            if (this.bean.isServiceTypeSet()) {
               buf.append("ServiceType");
               buf.append(String.valueOf(this.bean.getServiceType()));
            }

            if (this.bean.isStoreSet()) {
               buf.append("Store");
               buf.append(String.valueOf(this.bean.getStore()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isWindowIntervalSet()) {
               buf.append("WindowInterval");
               buf.append(String.valueOf(this.bean.getWindowInterval()));
            }

            if (this.bean.isWindowSizeSet()) {
               buf.append("WindowSize");
               buf.append(String.valueOf(this.bean.getWindowSize()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isForwardingPausedAtStartupSet()) {
               buf.append("ForwardingPausedAtStartup");
               buf.append(String.valueOf(this.bean.isForwardingPausedAtStartup()));
            }

            if (this.bean.isIncomingPausedAtStartupSet()) {
               buf.append("IncomingPausedAtStartup");
               buf.append(String.valueOf(this.bean.isIncomingPausedAtStartup()));
            }

            if (this.bean.isLoggingEnabledSet()) {
               buf.append("LoggingEnabled");
               buf.append(String.valueOf(this.bean.isLoggingEnabled()));
            }

            if (this.bean.isPagingMessageCompressionEnabledSet()) {
               buf.append("PagingMessageCompressionEnabled");
               buf.append(String.valueOf(this.bean.isPagingMessageCompressionEnabled()));
            }

            if (this.bean.isReceivingPausedAtStartupSet()) {
               buf.append("ReceivingPausedAtStartup");
               buf.append(String.valueOf(this.bean.isReceivingPausedAtStartup()));
            }

            if (this.bean.isStoreMessageCompressionEnabledSet()) {
               buf.append("StoreMessageCompressionEnabled");
               buf.append(String.valueOf(this.bean.isStoreMessageCompressionEnabled()));
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
            SAFAgentMBeanImpl otherTyped = (SAFAgentMBeanImpl)other;
            this.computeDiff("AcknowledgeInterval", this.bean.getAcknowledgeInterval(), otherTyped.getAcknowledgeInterval(), true);
            this.computeDiff("BytesMaximum", this.bean.getBytesMaximum(), otherTyped.getBytesMaximum(), true);
            this.computeDiff("BytesThresholdHigh", this.bean.getBytesThresholdHigh(), otherTyped.getBytesThresholdHigh(), true);
            this.computeDiff("BytesThresholdLow", this.bean.getBytesThresholdLow(), otherTyped.getBytesThresholdLow(), true);
            this.computeDiff("ConversationIdleTimeMaximum", this.bean.getConversationIdleTimeMaximum(), otherTyped.getConversationIdleTimeMaximum(), true);
            this.computeDiff("DefaultRetryDelayBase", this.bean.getDefaultRetryDelayBase(), otherTyped.getDefaultRetryDelayBase(), true);
            this.computeDiff("DefaultRetryDelayMaximum", this.bean.getDefaultRetryDelayMaximum(), otherTyped.getDefaultRetryDelayMaximum(), true);
            this.computeDiff("DefaultRetryDelayMultiplier", this.bean.getDefaultRetryDelayMultiplier(), otherTyped.getDefaultRetryDelayMultiplier(), true);
            this.computeDiff("DefaultTimeToLive", this.bean.getDefaultTimeToLive(), otherTyped.getDefaultTimeToLive(), true);
            this.computeSubDiff("JMSSAFMessageLogFile", this.bean.getJMSSAFMessageLogFile(), otherTyped.getJMSSAFMessageLogFile());
            this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            this.computeDiff("MessageBufferSize", this.bean.getMessageBufferSize(), otherTyped.getMessageBufferSize(), true);
            this.computeDiff("MessageCompressionOptions", this.bean.getMessageCompressionOptions(), otherTyped.getMessageCompressionOptions(), true);
            this.computeDiff("MessageCompressionOptionsOverride", this.bean.getMessageCompressionOptionsOverride(), otherTyped.getMessageCompressionOptionsOverride(), true);
            this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            this.computeDiff("MessagesThresholdHigh", this.bean.getMessagesThresholdHigh(), otherTyped.getMessagesThresholdHigh(), true);
            this.computeDiff("MessagesThresholdLow", this.bean.getMessagesThresholdLow(), otherTyped.getMessagesThresholdLow(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PagingDirectory", this.bean.getPagingDirectory(), otherTyped.getPagingDirectory(), false);
            this.computeDiff("ServiceType", this.bean.getServiceType(), otherTyped.getServiceType(), false);
            this.computeDiff("Store", this.bean.getStore(), otherTyped.getStore(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("WindowInterval", this.bean.getWindowInterval(), otherTyped.getWindowInterval(), true);
            this.computeDiff("WindowSize", this.bean.getWindowSize(), otherTyped.getWindowSize(), true);
            this.computeDiff("ForwardingPausedAtStartup", this.bean.isForwardingPausedAtStartup(), otherTyped.isForwardingPausedAtStartup(), false);
            this.computeDiff("IncomingPausedAtStartup", this.bean.isIncomingPausedAtStartup(), otherTyped.isIncomingPausedAtStartup(), false);
            this.computeDiff("LoggingEnabled", this.bean.isLoggingEnabled(), otherTyped.isLoggingEnabled(), true);
            this.computeDiff("PagingMessageCompressionEnabled", this.bean.isPagingMessageCompressionEnabled(), otherTyped.isPagingMessageCompressionEnabled(), true);
            this.computeDiff("ReceivingPausedAtStartup", this.bean.isReceivingPausedAtStartup(), otherTyped.isReceivingPausedAtStartup(), false);
            this.computeDiff("StoreMessageCompressionEnabled", this.bean.isStoreMessageCompressionEnabled(), otherTyped.isStoreMessageCompressionEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SAFAgentMBeanImpl original = (SAFAgentMBeanImpl)event.getSourceBean();
            SAFAgentMBeanImpl proposed = (SAFAgentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcknowledgeInterval")) {
                  original.setAcknowledgeInterval(proposed.getAcknowledgeInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("BytesMaximum")) {
                  original.setBytesMaximum(proposed.getBytesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("BytesThresholdHigh")) {
                  original.setBytesThresholdHigh(proposed.getBytesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("BytesThresholdLow")) {
                  original.setBytesThresholdLow(proposed.getBytesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("ConversationIdleTimeMaximum")) {
                  original.setConversationIdleTimeMaximum(proposed.getConversationIdleTimeMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("DefaultRetryDelayBase")) {
                  original.setDefaultRetryDelayBase(proposed.getDefaultRetryDelayBase());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("DefaultRetryDelayMaximum")) {
                  original.setDefaultRetryDelayMaximum(proposed.getDefaultRetryDelayMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("DefaultRetryDelayMultiplier")) {
                  original.setDefaultRetryDelayMultiplier(proposed.getDefaultRetryDelayMultiplier());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("DefaultTimeToLive")) {
                  original.setDefaultTimeToLive(proposed.getDefaultTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("JMSSAFMessageLogFile")) {
                  if (type == 2) {
                     original.setJMSSAFMessageLogFile((JMSSAFMessageLogFileMBean)this.createCopy((AbstractDescriptorBean)proposed.getJMSSAFMessageLogFile()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JMSSAFMessageLogFile", original.getJMSSAFMessageLogFile());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("MaximumMessageSize")) {
                  original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("MessageBufferSize")) {
                  original.setMessageBufferSize(proposed.getMessageBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("MessageCompressionOptions")) {
                  original.setMessageCompressionOptions(proposed.getMessageCompressionOptions());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("MessageCompressionOptionsOverride")) {
                  original.setMessageCompressionOptionsOverride(proposed.getMessageCompressionOptionsOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MessagesThresholdHigh")) {
                  original.setMessagesThresholdHigh(proposed.getMessagesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("MessagesThresholdLow")) {
                  original.setMessagesThresholdLow(proposed.getMessagesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PagingDirectory")) {
                  original.setPagingDirectory(proposed.getPagingDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (!prop.equals("ServerNames")) {
                  if (prop.equals("ServiceType")) {
                     original.setServiceType(proposed.getServiceType());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("Store")) {
                     original.setStoreAsString(proposed.getStoreAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
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
                  } else if (prop.equals("Targets")) {
                     original.setTargetsAsString(proposed.getTargetsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("WindowInterval")) {
                     original.setWindowInterval(proposed.getWindowInterval());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("WindowSize")) {
                     original.setWindowSize(proposed.getWindowSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("ForwardingPausedAtStartup")) {
                        original.setForwardingPausedAtStartup(proposed.isForwardingPausedAtStartup());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (prop.equals("IncomingPausedAtStartup")) {
                        original.setIncomingPausedAtStartup(proposed.isIncomingPausedAtStartup());
                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (prop.equals("LoggingEnabled")) {
                        original.setLoggingEnabled(proposed.isLoggingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("PagingMessageCompressionEnabled")) {
                        original.setPagingMessageCompressionEnabled(proposed.isPagingMessageCompressionEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 38);
                     } else if (prop.equals("ReceivingPausedAtStartup")) {
                        original.setReceivingPausedAtStartup(proposed.isReceivingPausedAtStartup());
                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("StoreMessageCompressionEnabled")) {
                        original.setStoreMessageCompressionEnabled(proposed.isStoreMessageCompressionEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 37);
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
            SAFAgentMBeanImpl copy = (SAFAgentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcknowledgeInterval")) && this.bean.isAcknowledgeIntervalSet()) {
               copy.setAcknowledgeInterval(this.bean.getAcknowledgeInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("BytesMaximum")) && this.bean.isBytesMaximumSet()) {
               copy.setBytesMaximum(this.bean.getBytesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("BytesThresholdHigh")) && this.bean.isBytesThresholdHighSet()) {
               copy.setBytesThresholdHigh(this.bean.getBytesThresholdHigh());
            }

            if ((excludeProps == null || !excludeProps.contains("BytesThresholdLow")) && this.bean.isBytesThresholdLowSet()) {
               copy.setBytesThresholdLow(this.bean.getBytesThresholdLow());
            }

            if ((excludeProps == null || !excludeProps.contains("ConversationIdleTimeMaximum")) && this.bean.isConversationIdleTimeMaximumSet()) {
               copy.setConversationIdleTimeMaximum(this.bean.getConversationIdleTimeMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryDelayBase")) && this.bean.isDefaultRetryDelayBaseSet()) {
               copy.setDefaultRetryDelayBase(this.bean.getDefaultRetryDelayBase());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryDelayMaximum")) && this.bean.isDefaultRetryDelayMaximumSet()) {
               copy.setDefaultRetryDelayMaximum(this.bean.getDefaultRetryDelayMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryDelayMultiplier")) && this.bean.isDefaultRetryDelayMultiplierSet()) {
               copy.setDefaultRetryDelayMultiplier(this.bean.getDefaultRetryDelayMultiplier());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTimeToLive")) && this.bean.isDefaultTimeToLiveSet()) {
               copy.setDefaultTimeToLive(this.bean.getDefaultTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSSAFMessageLogFile")) && this.bean.isJMSSAFMessageLogFileSet() && !copy._isSet(36)) {
               Object o = this.bean.getJMSSAFMessageLogFile();
               copy.setJMSSAFMessageLogFile((JMSSAFMessageLogFileMBean)null);
               copy.setJMSSAFMessageLogFile(o == null ? null : (JMSSAFMessageLogFileMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumMessageSize")) && this.bean.isMaximumMessageSizeSet()) {
               copy.setMaximumMessageSize(this.bean.getMaximumMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageBufferSize")) && this.bean.isMessageBufferSizeSet()) {
               copy.setMessageBufferSize(this.bean.getMessageBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageCompressionOptions")) && this.bean.isMessageCompressionOptionsSet()) {
               copy.setMessageCompressionOptions(this.bean.getMessageCompressionOptions());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageCompressionOptionsOverride")) && this.bean.isMessageCompressionOptionsOverrideSet()) {
               copy.setMessageCompressionOptionsOverride(this.bean.getMessageCompressionOptionsOverride());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesThresholdHigh")) && this.bean.isMessagesThresholdHighSet()) {
               copy.setMessagesThresholdHigh(this.bean.getMessagesThresholdHigh());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesThresholdLow")) && this.bean.isMessagesThresholdLowSet()) {
               copy.setMessagesThresholdLow(this.bean.getMessagesThresholdLow());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingDirectory")) && this.bean.isPagingDirectorySet()) {
               copy.setPagingDirectory(this.bean.getPagingDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceType")) && this.bean.isServiceTypeSet()) {
               copy.setServiceType(this.bean.getServiceType());
            }

            if ((excludeProps == null || !excludeProps.contains("Store")) && this.bean.isStoreSet()) {
               copy._unSet(copy, 13);
               copy.setStoreAsString(this.bean.getStoreAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("WindowInterval")) && this.bean.isWindowIntervalSet()) {
               copy.setWindowInterval(this.bean.getWindowInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("WindowSize")) && this.bean.isWindowSizeSet()) {
               copy.setWindowSize(this.bean.getWindowSize());
            }

            if ((excludeProps == null || !excludeProps.contains("ForwardingPausedAtStartup")) && this.bean.isForwardingPausedAtStartupSet()) {
               copy.setForwardingPausedAtStartup(this.bean.isForwardingPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("IncomingPausedAtStartup")) && this.bean.isIncomingPausedAtStartupSet()) {
               copy.setIncomingPausedAtStartup(this.bean.isIncomingPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggingEnabled")) && this.bean.isLoggingEnabledSet()) {
               copy.setLoggingEnabled(this.bean.isLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingMessageCompressionEnabled")) && this.bean.isPagingMessageCompressionEnabledSet()) {
               copy.setPagingMessageCompressionEnabled(this.bean.isPagingMessageCompressionEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ReceivingPausedAtStartup")) && this.bean.isReceivingPausedAtStartupSet()) {
               copy.setReceivingPausedAtStartup(this.bean.isReceivingPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreMessageCompressionEnabled")) && this.bean.isStoreMessageCompressionEnabledSet()) {
               copy.setStoreMessageCompressionEnabled(this.bean.isStoreMessageCompressionEnabled());
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
         this.inferSubTree(this.bean.getJMSSAFMessageLogFile(), clazz, annotation);
         this.inferSubTree(this.bean.getStore(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
