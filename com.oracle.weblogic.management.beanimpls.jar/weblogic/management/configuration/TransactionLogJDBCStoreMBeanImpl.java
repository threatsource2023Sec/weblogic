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
import weblogic.management.mbeans.custom.TransactionLogJDBCStoreMBeanCustomizer;
import weblogic.transaction.internal.JTAValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class TransactionLogJDBCStoreMBeanImpl extends JDBCStoreMBeanImpl implements TransactionLogJDBCStoreMBean, Serializable {
   private JDBCSystemResourceMBean _DataSource;
   private boolean _DynamicallyCreated;
   private boolean _Enabled;
   private int _MaxRetrySecondsBeforeTLOGFail;
   private int _MaxRetrySecondsBeforeTXException;
   private String _Name;
   private String _PrefixName;
   private int _RetryIntervalSeconds;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private transient TransactionLogJDBCStoreMBeanCustomizer _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private TransactionLogJDBCStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(TransactionLogJDBCStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(TransactionLogJDBCStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public TransactionLogJDBCStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(TransactionLogJDBCStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      TransactionLogJDBCStoreMBeanImpl oldDelegate = this._DelegateBean;
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

   public TransactionLogJDBCStoreMBeanImpl() {
      try {
         this._customizer = new TransactionLogJDBCStoreMBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public TransactionLogJDBCStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new TransactionLogJDBCStoreMBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public TransactionLogJDBCStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new TransactionLogJDBCStoreMBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public JDBCSystemResourceMBean getDataSource() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getDataSource() : this._DataSource;
   }

   public String getDataSourceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDataSource();
      return bean == null ? null : bean._getKey().toString();
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

   public String getPrefixName() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getPrefixName(), this) : this._customizer.getPrefixName();
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

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isPrefixNameInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isPrefixNameSet() {
      return this._isSet(10);
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
                  TransactionLogJDBCStoreMBeanImpl.this.setDataSource((JDBCSystemResourceMBean)value);
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
                        TransactionLogJDBCStoreMBeanImpl.this.addTarget((TargetMBean)value);
                        TransactionLogJDBCStoreMBeanImpl.this._getHelper().reorderArrayObjects((Object[])TransactionLogJDBCStoreMBeanImpl.this._Targets, this.getHandbackObject());
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

      JTAValidator.validateJdbcTlogDataSourceGlobal(param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 25, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return TransactionLogJDBCStoreMBeanImpl.this.getDataSource();
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
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
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
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPrefixName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      JMSLegalHelper.validateJDBCPrefix(param0);
      boolean wasSet = this._isSet(10);
      String _oldVal = this.getPrefixName();
      this._customizer.setPrefixName(param0);
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
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
            ResolvedReference _ref = new ResolvedReference(this, 12, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return TransactionLogJDBCStoreMBeanImpl.this.getTargets();
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
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
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

   public boolean isEnabled() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().isEnabled() : this._Enabled;
   }

   public boolean isEnabledInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isEnabledSet() {
      return this._isSet(36);
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

   public void setEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxRetrySecondsBeforeTLOGFail() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getMaxRetrySecondsBeforeTLOGFail() : this._MaxRetrySecondsBeforeTLOGFail;
   }

   public boolean isMaxRetrySecondsBeforeTLOGFailInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isMaxRetrySecondsBeforeTLOGFailSet() {
      return this._isSet(37);
   }

   public void setMaxRetrySecondsBeforeTLOGFail(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxRetrySecondsBeforeTLOGFail", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(37);
      int _oldVal = this._MaxRetrySecondsBeforeTLOGFail;
      this._MaxRetrySecondsBeforeTLOGFail = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxRetrySecondsBeforeTXException() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().getMaxRetrySecondsBeforeTXException() : this._MaxRetrySecondsBeforeTXException;
   }

   public boolean isMaxRetrySecondsBeforeTXExceptionInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isMaxRetrySecondsBeforeTXExceptionSet() {
      return this._isSet(38);
   }

   public void setMaxRetrySecondsBeforeTXException(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxRetrySecondsBeforeTXException", (long)param0, 0L, 300L);
      boolean wasSet = this._isSet(38);
      int _oldVal = this._MaxRetrySecondsBeforeTXException;
      this._MaxRetrySecondsBeforeTXException = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRetryIntervalSeconds() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getRetryIntervalSeconds() : this._RetryIntervalSeconds;
   }

   public boolean isRetryIntervalSecondsInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isRetryIntervalSecondsSet() {
      return this._isSet(39);
   }

   public void setRetryIntervalSeconds(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RetryIntervalSeconds", (long)param0, 1L, 60L);
      boolean wasSet = this._isSet(39);
      int _oldVal = this._RetryIntervalSeconds;
      this._RetryIntervalSeconds = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
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

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
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
         TransactionLogJDBCStoreMBeanImpl source = (TransactionLogJDBCStoreMBeanImpl)var4.next();
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
         idx = 25;
      }

      try {
         switch (idx) {
            case 25:
               this._DataSource = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._MaxRetrySecondsBeforeTLOGFail = 300;
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxRetrySecondsBeforeTXException = 60;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setPrefixName((String)null);
               if (initOne) {
                  break;
               }
            case 39:
               this._RetryIntervalSeconds = 5;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 12:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 36:
               this._Enabled = false;
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
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
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
      return "TransactionLogJDBCStore";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DataSource")) {
         JDBCSystemResourceMBean oldVal = this._DataSource;
         this._DataSource = (JDBCSystemResourceMBean)v;
         this._postSet(25, oldVal, this._DataSource);
      } else {
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("Enabled")) {
            oldVal = this._Enabled;
            this._Enabled = (Boolean)v;
            this._postSet(36, oldVal, this._Enabled);
         } else {
            int oldVal;
            if (name.equals("MaxRetrySecondsBeforeTLOGFail")) {
               oldVal = this._MaxRetrySecondsBeforeTLOGFail;
               this._MaxRetrySecondsBeforeTLOGFail = (Integer)v;
               this._postSet(37, oldVal, this._MaxRetrySecondsBeforeTLOGFail);
            } else if (name.equals("MaxRetrySecondsBeforeTXException")) {
               oldVal = this._MaxRetrySecondsBeforeTXException;
               this._MaxRetrySecondsBeforeTXException = (Integer)v;
               this._postSet(38, oldVal, this._MaxRetrySecondsBeforeTXException);
            } else {
               String oldVal;
               if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("PrefixName")) {
                  oldVal = this._PrefixName;
                  this._PrefixName = (String)v;
                  this._postSet(10, oldVal, this._PrefixName);
               } else if (name.equals("RetryIntervalSeconds")) {
                  oldVal = this._RetryIntervalSeconds;
                  this._RetryIntervalSeconds = (Integer)v;
                  this._postSet(39, oldVal, this._RetryIntervalSeconds);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Targets")) {
                  TargetMBean[] oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(12, oldVal, this._Targets);
               } else if (name.equals("customizer")) {
                  TransactionLogJDBCStoreMBeanCustomizer oldVal = this._customizer;
                  this._customizer = (TransactionLogJDBCStoreMBeanCustomizer)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DataSource")) {
         return this._DataSource;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("MaxRetrySecondsBeforeTLOGFail")) {
         return new Integer(this._MaxRetrySecondsBeforeTLOGFail);
      } else if (name.equals("MaxRetrySecondsBeforeTXException")) {
         return new Integer(this._MaxRetrySecondsBeforeTXException);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PrefixName")) {
         return this._PrefixName;
      } else if (name.equals("RetryIntervalSeconds")) {
         return new Integer(this._RetryIntervalSeconds);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends JDBCStoreMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 12;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 36;
               }
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 25;
               }

               if (s.equals("prefix-name")) {
                  return 10;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 22:
               if (s.equals("retry-interval-seconds")) {
                  return 39;
               }
               break;
            case 33:
               if (s.equals("max-retry-seconds-beforetlog-fail")) {
                  return 37;
               }
               break;
            case 36:
               if (s.equals("max-retry-seconds-beforetx-exception")) {
                  return 38;
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
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "prefix-name";
            case 12:
               return "target";
            case 25:
               return "data-source";
            case 36:
               return "enabled";
            case 37:
               return "max-retry-seconds-beforetlog-fail";
            case 38:
               return "max-retry-seconds-beforetx-exception";
            case 39:
               return "retry-interval-seconds";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends JDBCStoreMBeanImpl.Helper {
      private TransactionLogJDBCStoreMBeanImpl bean;

      protected Helper(TransactionLogJDBCStoreMBeanImpl bean) {
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
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "PrefixName";
            case 12:
               return "Targets";
            case 25:
               return "DataSource";
            case 36:
               return "Enabled";
            case 37:
               return "MaxRetrySecondsBeforeTLOGFail";
            case 38:
               return "MaxRetrySecondsBeforeTXException";
            case 39:
               return "RetryIntervalSeconds";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DataSource")) {
            return 25;
         } else if (propName.equals("MaxRetrySecondsBeforeTLOGFail")) {
            return 37;
         } else if (propName.equals("MaxRetrySecondsBeforeTXException")) {
            return 38;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PrefixName")) {
            return 10;
         } else if (propName.equals("RetryIntervalSeconds")) {
            return 39;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 12;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("Enabled") ? 36 : super.getPropertyIndex(propName);
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
            if (this.bean.isDataSourceSet()) {
               buf.append("DataSource");
               buf.append(String.valueOf(this.bean.getDataSource()));
            }

            if (this.bean.isMaxRetrySecondsBeforeTLOGFailSet()) {
               buf.append("MaxRetrySecondsBeforeTLOGFail");
               buf.append(String.valueOf(this.bean.getMaxRetrySecondsBeforeTLOGFail()));
            }

            if (this.bean.isMaxRetrySecondsBeforeTXExceptionSet()) {
               buf.append("MaxRetrySecondsBeforeTXException");
               buf.append(String.valueOf(this.bean.getMaxRetrySecondsBeforeTXException()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPrefixNameSet()) {
               buf.append("PrefixName");
               buf.append(String.valueOf(this.bean.getPrefixName()));
            }

            if (this.bean.isRetryIntervalSecondsSet()) {
               buf.append("RetryIntervalSeconds");
               buf.append(String.valueOf(this.bean.getRetryIntervalSeconds()));
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

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            TransactionLogJDBCStoreMBeanImpl otherTyped = (TransactionLogJDBCStoreMBeanImpl)other;
            this.computeDiff("DataSource", this.bean.getDataSource(), otherTyped.getDataSource(), false);
            this.computeDiff("MaxRetrySecondsBeforeTLOGFail", this.bean.getMaxRetrySecondsBeforeTLOGFail(), otherTyped.getMaxRetrySecondsBeforeTLOGFail(), true);
            this.computeDiff("MaxRetrySecondsBeforeTXException", this.bean.getMaxRetrySecondsBeforeTXException(), otherTyped.getMaxRetrySecondsBeforeTXException(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PrefixName", this.bean.getPrefixName(), otherTyped.getPrefixName(), true);
            this.computeDiff("RetryIntervalSeconds", this.bean.getRetryIntervalSeconds(), otherTyped.getRetryIntervalSeconds(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TransactionLogJDBCStoreMBeanImpl original = (TransactionLogJDBCStoreMBeanImpl)event.getSourceBean();
            TransactionLogJDBCStoreMBeanImpl proposed = (TransactionLogJDBCStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DataSource")) {
                  original.setDataSourceAsString(proposed.getDataSourceAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("MaxRetrySecondsBeforeTLOGFail")) {
                  original.setMaxRetrySecondsBeforeTLOGFail(proposed.getMaxRetrySecondsBeforeTLOGFail());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("MaxRetrySecondsBeforeTXException")) {
                  original.setMaxRetrySecondsBeforeTXException(proposed.getMaxRetrySecondsBeforeTXException());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PrefixName")) {
                  original.setPrefixName(proposed.getPrefixName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RetryIntervalSeconds")) {
                  original.setRetryIntervalSeconds(proposed.getRetryIntervalSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
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
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("Enabled")) {
                     original.setEnabled(proposed.isEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
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
            TransactionLogJDBCStoreMBeanImpl copy = (TransactionLogJDBCStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DataSource")) && this.bean.isDataSourceSet()) {
               copy._unSet(copy, 25);
               copy.setDataSourceAsString(this.bean.getDataSourceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRetrySecondsBeforeTLOGFail")) && this.bean.isMaxRetrySecondsBeforeTLOGFailSet()) {
               copy.setMaxRetrySecondsBeforeTLOGFail(this.bean.getMaxRetrySecondsBeforeTLOGFail());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRetrySecondsBeforeTXException")) && this.bean.isMaxRetrySecondsBeforeTXExceptionSet()) {
               copy.setMaxRetrySecondsBeforeTXException(this.bean.getMaxRetrySecondsBeforeTXException());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PrefixName")) && this.bean.isPrefixNameSet()) {
               copy.setPrefixName(this.bean.getPrefixName());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryIntervalSeconds")) && this.bean.isRetryIntervalSecondsSet()) {
               copy.setRetryIntervalSeconds(this.bean.getRetryIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 12);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
