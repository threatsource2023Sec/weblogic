package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
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
import weblogic.management.mbeans.custom.JMSServer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMSServerMBeanImpl extends DeploymentMBeanImpl implements JMSServerMBean, Serializable {
   private boolean _AllowsPersistentDowngrade;
   private String _BlockingSendPolicy;
   private long _BytesMaximum;
   private boolean _BytesPagingEnabled;
   private long _BytesThresholdHigh;
   private long _BytesThresholdLow;
   private String _ConsumptionPausedAtStartup;
   private JMSDestinationMBean[] _Destinations;
   private boolean _DynamicallyCreated;
   private int _ExpirationScanInterval;
   private boolean _HostingTemporaryDestinations;
   private String _InsertionPausedAtStartup;
   private boolean _JDBCStoreUpgradeEnabled;
   private JMSMessageLogFileMBean _JMSMessageLogFile;
   private JMSQueueMBean[] _JMSQueues;
   private JMSSessionPoolMBean[] _JMSSessionPools;
   private JMSTopicMBean[] _JMSTopics;
   private int _MaximumMessageSize;
   private long _MessageBufferSize;
   private String _MessageCompressionOptions;
   private String _MessageCompressionOptionsOverride;
   private long _MessagesMaximum;
   private boolean _MessagesPagingEnabled;
   private long _MessagesThresholdHigh;
   private long _MessagesThresholdLow;
   private String _Name;
   private int _PagingBlockSize;
   private String _PagingDirectory;
   private boolean _PagingFileLockingEnabled;
   private int _PagingIoBufferSize;
   private long _PagingMaxFileSize;
   private int _PagingMaxWindowBufferSize;
   private boolean _PagingMessageCompressionEnabled;
   private int _PagingMinWindowBufferSize;
   private JMSStoreMBean _PagingStore;
   private PersistentStoreMBean _PersistentStore;
   private String _ProductionPausedAtStartup;
   private Set _ServerNames;
   private JMSSessionPoolMBean[] _SessionPools;
   private JMSStoreMBean _Store;
   private boolean _StoreEnabled;
   private boolean _StoreMessageCompressionEnabled;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private JMSTemplateMBean _TemporaryTemplate;
   private String _TemporaryTemplateName;
   private String _TemporaryTemplateResource;
   private transient JMSServer _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JMSServerMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JMSServerMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JMSServerMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JMSServerMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JMSServerMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      JMSServerMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._JMSMessageLogFile instanceof JMSMessageLogFileMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getJMSMessageLogFile() != null) {
            this._getReferenceManager().unregisterBean((JMSMessageLogFileMBeanImpl)oldDelegate.getJMSMessageLogFile());
         }

         if (delegate != null && delegate.getJMSMessageLogFile() != null) {
            this._getReferenceManager().registerBean((JMSMessageLogFileMBeanImpl)delegate.getJMSMessageLogFile(), false);
         }

         ((JMSMessageLogFileMBeanImpl)this._JMSMessageLogFile)._setDelegateBean((JMSMessageLogFileMBeanImpl)((JMSMessageLogFileMBeanImpl)(delegate == null ? null : delegate.getJMSMessageLogFile())));
      }

      if (this._PagingStore instanceof JMSStoreMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getPagingStore() != null) {
            this._getReferenceManager().unregisterBean((JMSStoreMBeanImpl)oldDelegate.getPagingStore());
         }

         if (delegate != null && delegate.getPagingStore() != null) {
            this._getReferenceManager().registerBean((JMSStoreMBeanImpl)delegate.getPagingStore(), false);
         }

         ((JMSStoreMBeanImpl)this._PagingStore)._setDelegateBean((JMSStoreMBeanImpl)((JMSStoreMBeanImpl)(delegate == null ? null : delegate.getPagingStore())));
      }

      if (this._PersistentStore instanceof PersistentStoreMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getPersistentStore() != null) {
            this._getReferenceManager().unregisterBean((PersistentStoreMBeanImpl)oldDelegate.getPersistentStore());
         }

         if (delegate != null && delegate.getPersistentStore() != null) {
            this._getReferenceManager().registerBean((PersistentStoreMBeanImpl)delegate.getPersistentStore(), false);
         }

         ((PersistentStoreMBeanImpl)this._PersistentStore)._setDelegateBean((PersistentStoreMBeanImpl)((PersistentStoreMBeanImpl)(delegate == null ? null : delegate.getPersistentStore())));
      }

      if (this._Store instanceof JMSStoreMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getStore() != null) {
            this._getReferenceManager().unregisterBean((JMSStoreMBeanImpl)oldDelegate.getStore());
         }

         if (delegate != null && delegate.getStore() != null) {
            this._getReferenceManager().registerBean((JMSStoreMBeanImpl)delegate.getStore(), false);
         }

         ((JMSStoreMBeanImpl)this._Store)._setDelegateBean((JMSStoreMBeanImpl)((JMSStoreMBeanImpl)(delegate == null ? null : delegate.getStore())));
      }

      if (this._TemporaryTemplate instanceof JMSTemplateMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getTemporaryTemplate() != null) {
            this._getReferenceManager().unregisterBean((JMSTemplateMBeanImpl)oldDelegate.getTemporaryTemplate());
         }

         if (delegate != null && delegate.getTemporaryTemplate() != null) {
            this._getReferenceManager().registerBean((JMSTemplateMBeanImpl)delegate.getTemporaryTemplate(), false);
         }

         ((JMSTemplateMBeanImpl)this._TemporaryTemplate)._setDelegateBean((JMSTemplateMBeanImpl)((JMSTemplateMBeanImpl)(delegate == null ? null : delegate.getTemporaryTemplate())));
      }

   }

   public JMSServerMBeanImpl() {
      try {
         this._customizer = new JMSServer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMSServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMSServer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMSServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMSServer(this);
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
                        JMSServerMBeanImpl.this.addTarget((TargetMBean)value);
                        JMSServerMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JMSServerMBeanImpl.this._Targets, this.getHandbackObject());
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
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
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
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return JMSServerMBeanImpl.this.getTargets();
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
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
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

   public JMSSessionPoolMBean[] getSessionPools() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getSessionPools() : this._customizer.getSessionPools();
   }

   public boolean isSessionPoolsInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isSessionPoolsSet() {
      return this._isSet(13);
   }

   public void setSessionPools(JMSSessionPoolMBean[] param0) throws InvalidAttributeValueException {
      JMSSessionPoolMBean[] param0 = param0 == null ? new JMSSessionPoolMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setSessionPools((JMSSessionPoolMBean[])param0);
   }

   public boolean addSessionPool(JMSSessionPoolMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.addSessionPool(param0);
   }

   public boolean removeSessionPool(JMSSessionPoolMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.removeSessionPool(param0);
   }

   public void addJMSSessionPool(JMSSessionPoolMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         JMSSessionPoolMBean[] _new;
         if (this._isSet(14)) {
            _new = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])this._getHelper()._extendArray(this.getJMSSessionPools(), JMSSessionPoolMBean.class, param0));
         } else {
            _new = new JMSSessionPoolMBean[]{param0};
         }

         try {
            this.setJMSSessionPools(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSSessionPoolMBean[] getJMSSessionPools() {
      JMSSessionPoolMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         delegateArray = this._getDelegateBean().getJMSSessionPools();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JMSSessionPools.length; ++j) {
               if (delegateArray[i].getName().equals(this._JMSSessionPools[j].getName())) {
                  ((JMSSessionPoolMBeanImpl)this._JMSSessionPools[j])._setDelegateBean((JMSSessionPoolMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JMSSessionPoolMBeanImpl mbean = new JMSSessionPoolMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 14);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JMSSessionPoolMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(14)) {
                     this.setJMSSessionPools((JMSSessionPoolMBean[])((JMSSessionPoolMBean[])this._getHelper()._extendArray(this._JMSSessionPools, JMSSessionPoolMBean.class, mbean)));
                  } else {
                     this.setJMSSessionPools(new JMSSessionPoolMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JMSSessionPoolMBean[0];
      }

      if (this._JMSSessionPools != null) {
         List removeList = new ArrayList();
         JMSSessionPoolMBean[] var18 = this._JMSSessionPools;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JMSSessionPoolMBean bn = var18[var5];
            JMSSessionPoolMBeanImpl bni = (JMSSessionPoolMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JMSSessionPoolMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JMSSessionPoolMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            JMSSessionPoolMBean removeIt = (JMSSessionPoolMBean)var19.next();
            JMSSessionPoolMBeanImpl removeItImpl = (JMSSessionPoolMBeanImpl)removeIt;
            JMSSessionPoolMBean[] _new = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])this._getHelper()._removeElement(this._JMSSessionPools, JMSSessionPoolMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJMSSessionPools(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JMSSessionPools;
   }

   public boolean isJMSSessionPoolsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         JMSSessionPoolMBean[] elements = this.getJMSSessionPools();
         JMSSessionPoolMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isJMSSessionPoolsSet() {
      return this._isSet(14);
   }

   public void removeJMSSessionPool(JMSSessionPoolMBean param0) {
      this.destroyJMSSessionPool(param0);
   }

   public void setJMSSessionPools(JMSSessionPoolMBean[] param0) throws InvalidAttributeValueException {
      JMSSessionPoolMBean[] param0 = param0 == null ? new JMSSessionPoolMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JMSSessionPools, (Object[])param0, handler, new Comparator() {
            public int compare(JMSSessionPoolMBean o1, JMSSessionPoolMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JMSSessionPoolMBean bean = (JMSSessionPoolMBean)var3.next();
            JMSSessionPoolMBeanImpl beanImpl = (JMSSessionPoolMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(14);
      JMSSessionPoolMBean[] _oldVal = this._JMSSessionPools;
      this._JMSSessionPools = (JMSSessionPoolMBean[])param0;
      this._postSet(14, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var11.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSSessionPoolMBean createJMSSessionPool(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      JMSSessionPoolMBeanImpl lookup = (JMSSessionPoolMBeanImpl)this.lookupJMSSessionPool(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSessionPoolMBeanImpl _val = new JMSSessionPoolMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSSessionPool(_val);
            return _val;
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
      }
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public JMSSessionPoolMBean createJMSSessionPool(String param0, JMSSessionPoolMBean param1) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.createJMSSessionPool(param0, param1);
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void destroyJMSSessionPool(JMSSessionPoolMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         JMSSessionPoolMBean[] _old = this.getJMSSessionPools();
         JMSSessionPoolMBean[] _new = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])this._getHelper()._removeElement(_old, JMSSessionPoolMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
                  JMSSessionPoolMBeanImpl childImpl = (JMSSessionPoolMBeanImpl)_child;
                  JMSSessionPoolMBeanImpl lookup = (JMSSessionPoolMBeanImpl)source.lookupJMSSessionPool(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSSessionPool(lookup);
                  }
               }

               this.setJMSSessionPools(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public JMSSessionPoolMBean lookupJMSSessionPool(String param0) {
      Object[] aary = (Object[])this.getJMSSessionPools();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSSessionPoolMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSSessionPoolMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSDestinationMBean[] getDestinations() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getDestinations() : this._customizer.getDestinations();
   }

   public boolean isDestinationsInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isDestinationsSet() {
      return this._isSet(15);
   }

   public void setDestinations(JMSDestinationMBean[] param0) throws InvalidAttributeValueException {
      JMSDestinationMBean[] param0 = param0 == null ? new JMSDestinationMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setDestinations((JMSDestinationMBean[])param0);
   }

   public boolean addDestination(JMSDestinationMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.addDestination(param0);
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

   public boolean removeDestination(JMSDestinationMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.removeDestination(param0);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public JMSStoreMBean getStore() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getStore() : this._Store;
   }

   public String getStoreAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getStore();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isStoreInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isStoreSet() {
      return this._isSet(16);
   }

   public void setStoreAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSStoreMBean.class, new ReferenceManager.Resolver(this, 16) {
            public void resolveReference(Object value) {
               try {
                  JMSServerMBeanImpl.this.setStore((JMSStoreMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSStoreMBean _oldVal = this._Store;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._Store);
      }

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

   public void setStore(JMSStoreMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      JMSStoreMBean _oldVal = this._Store;
      this._Store = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public PersistentStoreMBean getPersistentStore() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getPersistentStore() : this._PersistentStore;
   }

   public String getPersistentStoreAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getPersistentStore();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isPersistentStoreInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isPersistentStoreSet() {
      return this._isSet(17);
   }

   public void setPersistentStoreAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, PersistentStoreMBean.class, new ReferenceManager.Resolver(this, 17) {
            public void resolveReference(Object value) {
               try {
                  JMSServerMBeanImpl.this.setPersistentStore((PersistentStoreMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         PersistentStoreMBean _oldVal = this._PersistentStore;
         this._initializeProperty(17);
         this._postSet(17, _oldVal, this._PersistentStore);
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
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
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

   public void setPersistentStore(PersistentStoreMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 17, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return JMSServerMBeanImpl.this.getPersistentStore();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(17);
      PersistentStoreMBean _oldVal = this._PersistentStore;
      this._PersistentStore = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getStoreEnabled() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getStoreEnabled() : this._StoreEnabled;
   }

   public boolean isStoreEnabledInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isStoreEnabledSet() {
      return this._isSet(18);
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

   public void setStoreEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      boolean _oldVal = this._StoreEnabled;
      this._StoreEnabled = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAllowsPersistentDowngrade() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().isAllowsPersistentDowngrade() : this._AllowsPersistentDowngrade;
   }

   public boolean isAllowsPersistentDowngradeInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isAllowsPersistentDowngradeSet() {
      return this._isSet(19);
   }

   public void setAllowsPersistentDowngrade(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._AllowsPersistentDowngrade;
      this._AllowsPersistentDowngrade = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSTemplateMBean getTemporaryTemplate() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getTemporaryTemplate() : this._customizer.getTemporaryTemplate();
   }

   public String getTemporaryTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTemporaryTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTemporaryTemplateInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isTemporaryTemplateSet() {
      return this._isSet(20);
   }

   public void setTemporaryTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSTemplateMBean.class, new ReferenceManager.Resolver(this, 20) {
            public void resolveReference(Object value) {
               try {
                  JMSServerMBeanImpl.this.setTemporaryTemplate((JMSTemplateMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSTemplateMBean _oldVal = this._TemporaryTemplate;
         this._initializeProperty(20);
         this._postSet(20, _oldVal, this._TemporaryTemplate);
      }

   }

   public void setTemporaryTemplate(JMSTemplateMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      JMSTemplateMBean _oldVal = this.getTemporaryTemplate();
      this._customizer.setTemporaryTemplate(param0);
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHostingTemporaryDestinations() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().isHostingTemporaryDestinations() : this._HostingTemporaryDestinations;
   }

   public boolean isHostingTemporaryDestinationsInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isHostingTemporaryDestinationsSet() {
      return this._isSet(21);
   }

   public void setHostingTemporaryDestinations(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this._HostingTemporaryDestinations;
      this._HostingTemporaryDestinations = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTemporaryTemplateResource() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getTemporaryTemplateResource(), this) : this._TemporaryTemplateResource;
   }

   public boolean isTemporaryTemplateResourceInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isTemporaryTemplateResourceSet() {
      return this._isSet(22);
   }

   public void setTemporaryTemplateResource(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      String _oldVal = this._TemporaryTemplateResource;
      this._TemporaryTemplateResource = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTemporaryTemplateName() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getTemporaryTemplateName(), this) : this._TemporaryTemplateName;
   }

   public boolean isTemporaryTemplateNameInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isTemporaryTemplateNameSet() {
      return this._isSet(23);
   }

   public void setTemporaryTemplateName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      String _oldVal = this._TemporaryTemplateName;
      this._TemporaryTemplateName = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesMaximum() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getBytesMaximum() : this._BytesMaximum;
   }

   public boolean isBytesMaximumInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isBytesMaximumSet() {
      return this._isSet(24);
   }

   public void setBytesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BytesMaximum", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(24);
      long _oldVal = this._BytesMaximum;
      this._BytesMaximum = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesThresholdHigh() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getBytesThresholdHigh() : this._BytesThresholdHigh;
   }

   public boolean isBytesThresholdHighInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isBytesThresholdHighSet() {
      return this._isSet(25);
   }

   public void setBytesThresholdHigh(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BytesThresholdHigh", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(25);
      long _oldVal = this._BytesThresholdHigh;
      this._BytesThresholdHigh = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public long getBytesThresholdLow() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getBytesThresholdLow() : this._BytesThresholdLow;
   }

   public boolean isBytesThresholdLowInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isBytesThresholdLowSet() {
      return this._isSet(26);
   }

   public void setBytesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BytesThresholdLow", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(26);
      long _oldVal = this._BytesThresholdLow;
      this._BytesThresholdLow = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesMaximum() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().getMessagesMaximum() : this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(27);
   }

   public void setMessagesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesMaximum", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(27);
      long _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesThresholdHigh() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getMessagesThresholdHigh() : this._MessagesThresholdHigh;
   }

   public boolean isMessagesThresholdHighInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isMessagesThresholdHighSet() {
      return this._isSet(28);
   }

   public void setMessagesThresholdHigh(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesThresholdHigh", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(28);
      long _oldVal = this._MessagesThresholdHigh;
      this._MessagesThresholdHigh = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessagesThresholdLow() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getMessagesThresholdLow() : this._MessagesThresholdLow;
   }

   public boolean isMessagesThresholdLowInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isMessagesThresholdLowSet() {
      return this._isSet(29);
   }

   public void setMessagesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesThresholdLow", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(29);
      long _oldVal = this._MessagesThresholdLow;
      this._MessagesThresholdLow = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJDBCStoreUpgradeEnabled() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().isJDBCStoreUpgradeEnabled() : this._JDBCStoreUpgradeEnabled;
   }

   public boolean isJDBCStoreUpgradeEnabledInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isJDBCStoreUpgradeEnabledSet() {
      return this._isSet(30);
   }

   public void setJDBCStoreUpgradeEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._JDBCStoreUpgradeEnabled;
      this._JDBCStoreUpgradeEnabled = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSStoreMBean getPagingStore() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getPagingStore() : this._PagingStore;
   }

   public String getPagingStoreAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getPagingStore();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isPagingStoreInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isPagingStoreSet() {
      return this._isSet(31);
   }

   public void setPagingStoreAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSStoreMBean.class, new ReferenceManager.Resolver(this, 31) {
            public void resolveReference(Object value) {
               try {
                  JMSServerMBeanImpl.this.setPagingStore((JMSStoreMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSStoreMBean _oldVal = this._PagingStore;
         this._initializeProperty(31);
         this._postSet(31, _oldVal, this._PagingStore);
      }

   }

   public void setPagingStore(JMSStoreMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      JMSStoreMBean _oldVal = this._PagingStore;
      this._PagingStore = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isMessagesPagingEnabled() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isMessagesPagingEnabled() : this._MessagesPagingEnabled;
   }

   public boolean isMessagesPagingEnabledInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isMessagesPagingEnabledSet() {
      return this._isSet(32);
   }

   public void setMessagesPagingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      boolean _oldVal = this._MessagesPagingEnabled;
      this._MessagesPagingEnabled = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isBytesPagingEnabled() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().isBytesPagingEnabled() : this._BytesPagingEnabled;
   }

   public boolean isBytesPagingEnabledInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isBytesPagingEnabledSet() {
      return this._isSet(33);
   }

   public void setBytesPagingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      boolean _oldVal = this._BytesPagingEnabled;
      this._BytesPagingEnabled = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMessageBufferSize() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getMessageBufferSize() : this._MessageBufferSize;
   }

   public boolean isMessageBufferSizeInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isMessageBufferSizeSet() {
      return this._isSet(34);
   }

   public void setMessageBufferSize(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessageBufferSize", param0, -1L, Long.MAX_VALUE);
      boolean wasSet = this._isSet(34);
      long _oldVal = this._MessageBufferSize;
      this._MessageBufferSize = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPagingDirectory() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._performMacroSubstitution(this._getDelegateBean().getPagingDirectory(), this) : this._PagingDirectory;
   }

   public boolean isPagingDirectoryInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isPagingDirectorySet() {
      return this._isSet(35);
   }

   public void setPagingDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(35);
      String _oldVal = this._PagingDirectory;
      this._PagingDirectory = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPagingFileLockingEnabled() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().isPagingFileLockingEnabled() : this._PagingFileLockingEnabled;
   }

   public boolean isPagingFileLockingEnabledInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isPagingFileLockingEnabledSet() {
      return this._isSet(36);
   }

   public void setPagingFileLockingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._PagingFileLockingEnabled;
      this._PagingFileLockingEnabled = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPagingMinWindowBufferSize() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getPagingMinWindowBufferSize() : this._PagingMinWindowBufferSize;
   }

   public boolean isPagingMinWindowBufferSizeInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isPagingMinWindowBufferSizeSet() {
      return this._isSet(37);
   }

   public void setPagingMinWindowBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("PagingMinWindowBufferSize", (long)param0, -1L, 1073741824L);
      boolean wasSet = this._isSet(37);
      int _oldVal = this._PagingMinWindowBufferSize;
      this._PagingMinWindowBufferSize = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPagingMaxWindowBufferSize() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().getPagingMaxWindowBufferSize() : this._PagingMaxWindowBufferSize;
   }

   public boolean isPagingMaxWindowBufferSizeInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isPagingMaxWindowBufferSizeSet() {
      return this._isSet(38);
   }

   public void setPagingMaxWindowBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("PagingMaxWindowBufferSize", (long)param0, -1L, 1073741824L);
      boolean wasSet = this._isSet(38);
      int _oldVal = this._PagingMaxWindowBufferSize;
      this._PagingMaxWindowBufferSize = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPagingIoBufferSize() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getPagingIoBufferSize() : this._PagingIoBufferSize;
   }

   public boolean isPagingIoBufferSizeInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isPagingIoBufferSizeSet() {
      return this._isSet(39);
   }

   public void setPagingIoBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("PagingIoBufferSize", (long)param0, -1L, 67108864L);
      boolean wasSet = this._isSet(39);
      int _oldVal = this._PagingIoBufferSize;
      this._PagingIoBufferSize = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public long getPagingMaxFileSize() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().getPagingMaxFileSize() : this._PagingMaxFileSize;
   }

   public boolean isPagingMaxFileSizeInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isPagingMaxFileSizeSet() {
      return this._isSet(40);
   }

   public void setPagingMaxFileSize(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("PagingMaxFileSize", param0, 10485760L);
      boolean wasSet = this._isSet(40);
      long _oldVal = this._PagingMaxFileSize;
      this._PagingMaxFileSize = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPagingBlockSize() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().getPagingBlockSize() : this._PagingBlockSize;
   }

   public boolean isPagingBlockSizeInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isPagingBlockSizeSet() {
      return this._isSet(41);
   }

   public void setPagingBlockSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("PagingBlockSize", (long)param0, -1L, 8192L);
      boolean wasSet = this._isSet(41);
      int _oldVal = this._PagingBlockSize;
      this._PagingBlockSize = param0;
      this._postSet(41, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(41)) {
            source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
         }
      }

   }

   public void setExpirationScanInterval(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ExpirationScanInterval", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(42);
      int _oldVal = this._ExpirationScanInterval;
      this._ExpirationScanInterval = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public int getExpirationScanInterval() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._getDelegateBean().getExpirationScanInterval() : this._ExpirationScanInterval;
   }

   public boolean isExpirationScanIntervalInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isExpirationScanIntervalSet() {
      return this._isSet(42);
   }

   public int getMaximumMessageSize() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._getDelegateBean().getMaximumMessageSize() : this._MaximumMessageSize;
   }

   public boolean isMaximumMessageSizeInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(43);
   }

   public void setMaximumMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaximumMessageSize", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(43);
      int _oldVal = this._MaximumMessageSize;
      this._MaximumMessageSize = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public String getBlockingSendPolicy() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._performMacroSubstitution(this._getDelegateBean().getBlockingSendPolicy(), this) : this._BlockingSendPolicy;
   }

   public boolean isBlockingSendPolicyInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isBlockingSendPolicySet() {
      return this._isSet(44);
   }

   public void setBlockingSendPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"FIFO", "Preemptive"};
      param0 = LegalChecks.checkInEnum("BlockingSendPolicy", param0, _set);
      boolean wasSet = this._isSet(44);
      String _oldVal = this._BlockingSendPolicy;
      this._BlockingSendPolicy = param0;
      this._postSet(44, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var5.next();
         if (source != null && !source._isSet(44)) {
            source._postSetFirePropertyChange(44, wasSet, _oldVal, param0);
         }
      }

   }

   public void setProductionPausedAtStartup(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(45);
      String _oldVal = this._ProductionPausedAtStartup;
      this._ProductionPausedAtStartup = param0;
      this._postSet(45, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public String getProductionPausedAtStartup() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._performMacroSubstitution(this._getDelegateBean().getProductionPausedAtStartup(), this) : this._ProductionPausedAtStartup;
   }

   public boolean isProductionPausedAtStartupInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isProductionPausedAtStartupSet() {
      return this._isSet(45);
   }

   public void setInsertionPausedAtStartup(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(46);
      String _oldVal = this._InsertionPausedAtStartup;
      this._InsertionPausedAtStartup = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public String getInsertionPausedAtStartup() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._performMacroSubstitution(this._getDelegateBean().getInsertionPausedAtStartup(), this) : this._InsertionPausedAtStartup;
   }

   public boolean isInsertionPausedAtStartupInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isInsertionPausedAtStartupSet() {
      return this._isSet(46);
   }

   public void setConsumptionPausedAtStartup(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(47);
      String _oldVal = this._ConsumptionPausedAtStartup;
      this._ConsumptionPausedAtStartup = param0;
      this._postSet(47, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(47)) {
            source._postSetFirePropertyChange(47, wasSet, _oldVal, param0);
         }
      }

   }

   public String getConsumptionPausedAtStartup() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47) ? this._performMacroSubstitution(this._getDelegateBean().getConsumptionPausedAtStartup(), this) : this._ConsumptionPausedAtStartup;
   }

   public boolean isConsumptionPausedAtStartupInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isConsumptionPausedAtStartupSet() {
      return this._isSet(47);
   }

   public void addJMSQueue(JMSQueueMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 48)) {
         JMSQueueMBean[] _new;
         if (this._isSet(48)) {
            _new = (JMSQueueMBean[])((JMSQueueMBean[])this._getHelper()._extendArray(this.getJMSQueues(), JMSQueueMBean.class, param0));
         } else {
            _new = new JMSQueueMBean[]{param0};
         }

         try {
            this.setJMSQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSQueueMBean[] getJMSQueues() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().getJMSQueues() : this._JMSQueues;
   }

   public boolean isJMSQueuesInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isJMSQueuesSet() {
      return this._isSet(48);
   }

   public void removeJMSQueue(JMSQueueMBean param0) {
      this.destroyJMSQueue(param0);
   }

   public void setJMSQueues(JMSQueueMBean[] param0) throws InvalidAttributeValueException {
      JMSQueueMBean[] param0 = param0 == null ? new JMSQueueMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 48)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(48);
      JMSQueueMBean[] _oldVal = this._JMSQueues;
      this._JMSQueues = (JMSQueueMBean[])param0;
      this._postSet(48, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(48)) {
            source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSQueueMBean createJMSQueue(String param0) {
      JMSQueueMBeanImpl lookup = (JMSQueueMBeanImpl)this.lookupJMSQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSQueueMBeanImpl _val = new JMSQueueMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSQueue(_val);
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

   public JMSQueueMBean createJMSQueue(String param0, JMSQueueMBean param1) {
      return this._customizer.createJMSQueue(param0, param1);
   }

   public void destroyJMSQueue(JMSQueueMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 48);
         JMSQueueMBean[] _old = this.getJMSQueues();
         JMSQueueMBean[] _new = (JMSQueueMBean[])((JMSQueueMBean[])this._getHelper()._removeElement(_old, JMSQueueMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
                  JMSQueueMBeanImpl childImpl = (JMSQueueMBeanImpl)_child;
                  JMSQueueMBeanImpl lookup = (JMSQueueMBeanImpl)source.lookupJMSQueue(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSQueue(lookup);
                  }
               }

               this.setJMSQueues(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public JMSQueueMBean lookupJMSQueue(String param0) {
      Object[] aary = (Object[])this._JMSQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSQueueMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSQueueMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSTopic(JMSTopicMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 49)) {
         JMSTopicMBean[] _new;
         if (this._isSet(49)) {
            _new = (JMSTopicMBean[])((JMSTopicMBean[])this._getHelper()._extendArray(this.getJMSTopics(), JMSTopicMBean.class, param0));
         } else {
            _new = new JMSTopicMBean[]{param0};
         }

         try {
            this.setJMSTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSTopicMBean[] getJMSTopics() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().getJMSTopics() : this._JMSTopics;
   }

   public boolean isJMSTopicsInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isJMSTopicsSet() {
      return this._isSet(49);
   }

   public void removeJMSTopic(JMSTopicMBean param0) {
      this.destroyJMSTopic(param0);
   }

   public void setJMSTopics(JMSTopicMBean[] param0) throws InvalidAttributeValueException {
      JMSTopicMBean[] param0 = param0 == null ? new JMSTopicMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 49)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(49);
      JMSTopicMBean[] _oldVal = this._JMSTopics;
      this._JMSTopics = (JMSTopicMBean[])param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSTopicMBean createJMSTopic(String param0) {
      JMSTopicMBeanImpl lookup = (JMSTopicMBeanImpl)this.lookupJMSTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSTopicMBeanImpl _val = new JMSTopicMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSTopic(_val);
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

   public JMSTopicMBean createJMSTopic(String param0, JMSTopicMBean param1) {
      return this._customizer.createJMSTopic(param0, param1);
   }

   public void destroyJMSTopic(JMSTopicMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 49);
         JMSTopicMBean[] _old = this.getJMSTopics();
         JMSTopicMBean[] _new = (JMSTopicMBean[])((JMSTopicMBean[])this._getHelper()._removeElement(_old, JMSTopicMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  JMSServerMBeanImpl source = (JMSServerMBeanImpl)var6.next();
                  JMSTopicMBeanImpl childImpl = (JMSTopicMBeanImpl)_child;
                  JMSTopicMBeanImpl lookup = (JMSTopicMBeanImpl)source.lookupJMSTopic(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSTopic(lookup);
                  }
               }

               this.setJMSTopics(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public JMSTopicMBean lookupJMSTopic(String param0) {
      Object[] aary = (Object[])this._JMSTopics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSTopicMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSTopicMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSMessageLogFileMBean getJMSMessageLogFile() {
      return this._JMSMessageLogFile;
   }

   public boolean isJMSMessageLogFileInherited() {
      return false;
   }

   public boolean isJMSMessageLogFileSet() {
      return this._isSet(50) || this._isAnythingSet((AbstractDescriptorBean)this.getJMSMessageLogFile());
   }

   public void setJMSMessageLogFile(JMSMessageLogFileMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 50)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(50);
      JMSMessageLogFileMBean _oldVal = this._JMSMessageLogFile;
      this._JMSMessageLogFile = param0;
      this._postSet(50, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var5.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

   }

   public void useDelegates(DomainMBean param0) {
      this._customizer.useDelegates(param0);
   }

   public boolean isStoreMessageCompressionEnabled() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51) ? this._getDelegateBean().isStoreMessageCompressionEnabled() : this._StoreMessageCompressionEnabled;
   }

   public boolean isStoreMessageCompressionEnabledInherited() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51);
   }

   public boolean isStoreMessageCompressionEnabledSet() {
      return this._isSet(51);
   }

   public void setStoreMessageCompressionEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(51);
      boolean _oldVal = this._StoreMessageCompressionEnabled;
      this._StoreMessageCompressionEnabled = param0;
      this._postSet(51, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(51)) {
            source._postSetFirePropertyChange(51, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPagingMessageCompressionEnabled() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._getDelegateBean().isPagingMessageCompressionEnabled() : this._PagingMessageCompressionEnabled;
   }

   public boolean isPagingMessageCompressionEnabledInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isPagingMessageCompressionEnabledSet() {
      return this._isSet(52);
   }

   public void setPagingMessageCompressionEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(52);
      boolean _oldVal = this._PagingMessageCompressionEnabled;
      this._PagingMessageCompressionEnabled = param0;
      this._postSet(52, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(52)) {
            source._postSetFirePropertyChange(52, wasSet, _oldVal, param0);
         }
      }

   }

   public void setMessageCompressionOptionsOverride(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(53);
      String _oldVal = this._MessageCompressionOptionsOverride;
      this._MessageCompressionOptionsOverride = param0;
      this._postSet(53, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(53)) {
            source._postSetFirePropertyChange(53, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessageCompressionOptionsOverride() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53) ? this._performMacroSubstitution(this._getDelegateBean().getMessageCompressionOptionsOverride(), this) : this._MessageCompressionOptionsOverride;
   }

   public boolean isMessageCompressionOptionsOverrideInherited() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53);
   }

   public boolean isMessageCompressionOptionsOverrideSet() {
      return this._isSet(53);
   }

   public void setMessageCompressionOptions(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"GZIP_DEFAULT_COMPRESSION", "GZIP_BEST_COMPRESSION", "GZIP_BEST_SPEED", "LZF"};
      param0 = LegalChecks.checkInEnum("MessageCompressionOptions", param0, _set);
      boolean wasSet = this._isSet(54);
      String _oldVal = this._MessageCompressionOptions;
      this._MessageCompressionOptions = param0;
      this._postSet(54, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSServerMBeanImpl source = (JMSServerMBeanImpl)var5.next();
         if (source != null && !source._isSet(54)) {
            source._postSetFirePropertyChange(54, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessageCompressionOptions() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54) ? this._performMacroSubstitution(this._getDelegateBean().getMessageCompressionOptions(), this) : this._MessageCompressionOptions;
   }

   public boolean isMessageCompressionOptionsInherited() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54);
   }

   public boolean isMessageCompressionOptionsSet() {
      return this._isSet(54);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSLegalHelper.validateJMSServer(this);
      JMSLegalHelper.validateServerBytesValues(this);
      JMSLegalHelper.validateServerMessagesValues(this);
   }

   protected void _preDestroy() {
      this._customizer._preDestroy();
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
      return super._isAnythingSet() || this.isJMSMessageLogFileSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 44;
      }

      try {
         switch (idx) {
            case 44:
               this._BlockingSendPolicy = "FIFO";
               if (initOne) {
                  break;
               }
            case 24:
               this._BytesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 25:
               this._BytesThresholdHigh = -1L;
               if (initOne) {
                  break;
               }
            case 26:
               this._BytesThresholdLow = -1L;
               if (initOne) {
                  break;
               }
            case 47:
               this._ConsumptionPausedAtStartup = "default";
               if (initOne) {
                  break;
               }
            case 15:
               this._Destinations = new JMSDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 42:
               this._ExpirationScanInterval = 30;
               if (initOne) {
                  break;
               }
            case 46:
               this._InsertionPausedAtStartup = "default";
               if (initOne) {
                  break;
               }
            case 50:
               this._JMSMessageLogFile = new JMSMessageLogFileMBeanImpl(this, 50);
               this._postCreate((AbstractDescriptorBean)this._JMSMessageLogFile);
               if (initOne) {
                  break;
               }
            case 48:
               this._JMSQueues = new JMSQueueMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._JMSSessionPools = new JMSSessionPoolMBean[0];
               if (initOne) {
                  break;
               }
            case 49:
               this._JMSTopics = new JMSTopicMBean[0];
               if (initOne) {
                  break;
               }
            case 43:
               this._MaximumMessageSize = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 34:
               this._MessageBufferSize = -1L;
               if (initOne) {
                  break;
               }
            case 54:
               this._MessageCompressionOptions = "GZIP_DEFAULT_COMPRESSION";
               if (initOne) {
                  break;
               }
            case 53:
               this._MessageCompressionOptionsOverride = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._MessagesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 28:
               this._MessagesThresholdHigh = -1L;
               if (initOne) {
                  break;
               }
            case 29:
               this._MessagesThresholdLow = -1L;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 41:
               this._PagingBlockSize = -1;
               if (initOne) {
                  break;
               }
            case 35:
               this._PagingDirectory = null;
               if (initOne) {
                  break;
               }
            case 39:
               this._PagingIoBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 40:
               this._PagingMaxFileSize = 1342177280L;
               if (initOne) {
                  break;
               }
            case 38:
               this._PagingMaxWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 37:
               this._PagingMinWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 31:
               this._PagingStore = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._PersistentStore = null;
               if (initOne) {
                  break;
               }
            case 45:
               this._ProductionPausedAtStartup = "default";
               if (initOne) {
                  break;
               }
            case 12:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setSessionPools(new JMSSessionPoolMBean[0]);
               if (initOne) {
                  break;
               }
            case 16:
               this._Store = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._StoreEnabled = true;
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
            case 20:
               this._customizer.setTemporaryTemplate((JMSTemplateMBean)null);
               if (initOne) {
                  break;
               }
            case 23:
               this._TemporaryTemplateName = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._TemporaryTemplateResource = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._AllowsPersistentDowngrade = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._BytesPagingEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._HostingTemporaryDestinations = true;
               if (initOne) {
                  break;
               }
            case 30:
               this._JDBCStoreUpgradeEnabled = true;
               if (initOne) {
                  break;
               }
            case 32:
               this._MessagesPagingEnabled = false;
               if (initOne) {
                  break;
               }
            case 36:
               this._PagingFileLockingEnabled = true;
               if (initOne) {
                  break;
               }
            case 52:
               this._PagingMessageCompressionEnabled = false;
               if (initOne) {
                  break;
               }
            case 51:
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
      return "JMSServer";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AllowsPersistentDowngrade")) {
         oldVal = this._AllowsPersistentDowngrade;
         this._AllowsPersistentDowngrade = (Boolean)v;
         this._postSet(19, oldVal, this._AllowsPersistentDowngrade);
      } else {
         String oldVal;
         if (name.equals("BlockingSendPolicy")) {
            oldVal = this._BlockingSendPolicy;
            this._BlockingSendPolicy = (String)v;
            this._postSet(44, oldVal, this._BlockingSendPolicy);
         } else {
            long oldVal;
            if (name.equals("BytesMaximum")) {
               oldVal = this._BytesMaximum;
               this._BytesMaximum = (Long)v;
               this._postSet(24, oldVal, this._BytesMaximum);
            } else if (name.equals("BytesPagingEnabled")) {
               oldVal = this._BytesPagingEnabled;
               this._BytesPagingEnabled = (Boolean)v;
               this._postSet(33, oldVal, this._BytesPagingEnabled);
            } else if (name.equals("BytesThresholdHigh")) {
               oldVal = this._BytesThresholdHigh;
               this._BytesThresholdHigh = (Long)v;
               this._postSet(25, oldVal, this._BytesThresholdHigh);
            } else if (name.equals("BytesThresholdLow")) {
               oldVal = this._BytesThresholdLow;
               this._BytesThresholdLow = (Long)v;
               this._postSet(26, oldVal, this._BytesThresholdLow);
            } else if (name.equals("ConsumptionPausedAtStartup")) {
               oldVal = this._ConsumptionPausedAtStartup;
               this._ConsumptionPausedAtStartup = (String)v;
               this._postSet(47, oldVal, this._ConsumptionPausedAtStartup);
            } else if (name.equals("Destinations")) {
               JMSDestinationMBean[] oldVal = this._Destinations;
               this._Destinations = (JMSDestinationMBean[])((JMSDestinationMBean[])v);
               this._postSet(15, oldVal, this._Destinations);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else {
               int oldVal;
               if (name.equals("ExpirationScanInterval")) {
                  oldVal = this._ExpirationScanInterval;
                  this._ExpirationScanInterval = (Integer)v;
                  this._postSet(42, oldVal, this._ExpirationScanInterval);
               } else if (name.equals("HostingTemporaryDestinations")) {
                  oldVal = this._HostingTemporaryDestinations;
                  this._HostingTemporaryDestinations = (Boolean)v;
                  this._postSet(21, oldVal, this._HostingTemporaryDestinations);
               } else if (name.equals("InsertionPausedAtStartup")) {
                  oldVal = this._InsertionPausedAtStartup;
                  this._InsertionPausedAtStartup = (String)v;
                  this._postSet(46, oldVal, this._InsertionPausedAtStartup);
               } else if (name.equals("JDBCStoreUpgradeEnabled")) {
                  oldVal = this._JDBCStoreUpgradeEnabled;
                  this._JDBCStoreUpgradeEnabled = (Boolean)v;
                  this._postSet(30, oldVal, this._JDBCStoreUpgradeEnabled);
               } else if (name.equals("JMSMessageLogFile")) {
                  JMSMessageLogFileMBean oldVal = this._JMSMessageLogFile;
                  this._JMSMessageLogFile = (JMSMessageLogFileMBean)v;
                  this._postSet(50, oldVal, this._JMSMessageLogFile);
               } else if (name.equals("JMSQueues")) {
                  JMSQueueMBean[] oldVal = this._JMSQueues;
                  this._JMSQueues = (JMSQueueMBean[])((JMSQueueMBean[])v);
                  this._postSet(48, oldVal, this._JMSQueues);
               } else {
                  JMSSessionPoolMBean[] oldVal;
                  if (name.equals("JMSSessionPools")) {
                     oldVal = this._JMSSessionPools;
                     this._JMSSessionPools = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])v);
                     this._postSet(14, oldVal, this._JMSSessionPools);
                  } else if (name.equals("JMSTopics")) {
                     JMSTopicMBean[] oldVal = this._JMSTopics;
                     this._JMSTopics = (JMSTopicMBean[])((JMSTopicMBean[])v);
                     this._postSet(49, oldVal, this._JMSTopics);
                  } else if (name.equals("MaximumMessageSize")) {
                     oldVal = this._MaximumMessageSize;
                     this._MaximumMessageSize = (Integer)v;
                     this._postSet(43, oldVal, this._MaximumMessageSize);
                  } else if (name.equals("MessageBufferSize")) {
                     oldVal = this._MessageBufferSize;
                     this._MessageBufferSize = (Long)v;
                     this._postSet(34, oldVal, this._MessageBufferSize);
                  } else if (name.equals("MessageCompressionOptions")) {
                     oldVal = this._MessageCompressionOptions;
                     this._MessageCompressionOptions = (String)v;
                     this._postSet(54, oldVal, this._MessageCompressionOptions);
                  } else if (name.equals("MessageCompressionOptionsOverride")) {
                     oldVal = this._MessageCompressionOptionsOverride;
                     this._MessageCompressionOptionsOverride = (String)v;
                     this._postSet(53, oldVal, this._MessageCompressionOptionsOverride);
                  } else if (name.equals("MessagesMaximum")) {
                     oldVal = this._MessagesMaximum;
                     this._MessagesMaximum = (Long)v;
                     this._postSet(27, oldVal, this._MessagesMaximum);
                  } else if (name.equals("MessagesPagingEnabled")) {
                     oldVal = this._MessagesPagingEnabled;
                     this._MessagesPagingEnabled = (Boolean)v;
                     this._postSet(32, oldVal, this._MessagesPagingEnabled);
                  } else if (name.equals("MessagesThresholdHigh")) {
                     oldVal = this._MessagesThresholdHigh;
                     this._MessagesThresholdHigh = (Long)v;
                     this._postSet(28, oldVal, this._MessagesThresholdHigh);
                  } else if (name.equals("MessagesThresholdLow")) {
                     oldVal = this._MessagesThresholdLow;
                     this._MessagesThresholdLow = (Long)v;
                     this._postSet(29, oldVal, this._MessagesThresholdLow);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("PagingBlockSize")) {
                     oldVal = this._PagingBlockSize;
                     this._PagingBlockSize = (Integer)v;
                     this._postSet(41, oldVal, this._PagingBlockSize);
                  } else if (name.equals("PagingDirectory")) {
                     oldVal = this._PagingDirectory;
                     this._PagingDirectory = (String)v;
                     this._postSet(35, oldVal, this._PagingDirectory);
                  } else if (name.equals("PagingFileLockingEnabled")) {
                     oldVal = this._PagingFileLockingEnabled;
                     this._PagingFileLockingEnabled = (Boolean)v;
                     this._postSet(36, oldVal, this._PagingFileLockingEnabled);
                  } else if (name.equals("PagingIoBufferSize")) {
                     oldVal = this._PagingIoBufferSize;
                     this._PagingIoBufferSize = (Integer)v;
                     this._postSet(39, oldVal, this._PagingIoBufferSize);
                  } else if (name.equals("PagingMaxFileSize")) {
                     oldVal = this._PagingMaxFileSize;
                     this._PagingMaxFileSize = (Long)v;
                     this._postSet(40, oldVal, this._PagingMaxFileSize);
                  } else if (name.equals("PagingMaxWindowBufferSize")) {
                     oldVal = this._PagingMaxWindowBufferSize;
                     this._PagingMaxWindowBufferSize = (Integer)v;
                     this._postSet(38, oldVal, this._PagingMaxWindowBufferSize);
                  } else if (name.equals("PagingMessageCompressionEnabled")) {
                     oldVal = this._PagingMessageCompressionEnabled;
                     this._PagingMessageCompressionEnabled = (Boolean)v;
                     this._postSet(52, oldVal, this._PagingMessageCompressionEnabled);
                  } else if (name.equals("PagingMinWindowBufferSize")) {
                     oldVal = this._PagingMinWindowBufferSize;
                     this._PagingMinWindowBufferSize = (Integer)v;
                     this._postSet(37, oldVal, this._PagingMinWindowBufferSize);
                  } else {
                     JMSStoreMBean oldVal;
                     if (name.equals("PagingStore")) {
                        oldVal = this._PagingStore;
                        this._PagingStore = (JMSStoreMBean)v;
                        this._postSet(31, oldVal, this._PagingStore);
                     } else if (name.equals("PersistentStore")) {
                        PersistentStoreMBean oldVal = this._PersistentStore;
                        this._PersistentStore = (PersistentStoreMBean)v;
                        this._postSet(17, oldVal, this._PersistentStore);
                     } else if (name.equals("ProductionPausedAtStartup")) {
                        oldVal = this._ProductionPausedAtStartup;
                        this._ProductionPausedAtStartup = (String)v;
                        this._postSet(45, oldVal, this._ProductionPausedAtStartup);
                     } else if (name.equals("ServerNames")) {
                        Set oldVal = this._ServerNames;
                        this._ServerNames = (Set)v;
                        this._postSet(12, oldVal, this._ServerNames);
                     } else if (name.equals("SessionPools")) {
                        oldVal = this._SessionPools;
                        this._SessionPools = (JMSSessionPoolMBean[])((JMSSessionPoolMBean[])v);
                        this._postSet(13, oldVal, this._SessionPools);
                     } else if (name.equals("Store")) {
                        oldVal = this._Store;
                        this._Store = (JMSStoreMBean)v;
                        this._postSet(16, oldVal, this._Store);
                     } else if (name.equals("StoreEnabled")) {
                        oldVal = this._StoreEnabled;
                        this._StoreEnabled = (Boolean)v;
                        this._postSet(18, oldVal, this._StoreEnabled);
                     } else if (name.equals("StoreMessageCompressionEnabled")) {
                        oldVal = this._StoreMessageCompressionEnabled;
                        this._StoreMessageCompressionEnabled = (Boolean)v;
                        this._postSet(51, oldVal, this._StoreMessageCompressionEnabled);
                     } else if (name.equals("Tags")) {
                        String[] oldVal = this._Tags;
                        this._Tags = (String[])((String[])v);
                        this._postSet(9, oldVal, this._Tags);
                     } else if (name.equals("Targets")) {
                        TargetMBean[] oldVal = this._Targets;
                        this._Targets = (TargetMBean[])((TargetMBean[])v);
                        this._postSet(10, oldVal, this._Targets);
                     } else if (name.equals("TemporaryTemplate")) {
                        JMSTemplateMBean oldVal = this._TemporaryTemplate;
                        this._TemporaryTemplate = (JMSTemplateMBean)v;
                        this._postSet(20, oldVal, this._TemporaryTemplate);
                     } else if (name.equals("TemporaryTemplateName")) {
                        oldVal = this._TemporaryTemplateName;
                        this._TemporaryTemplateName = (String)v;
                        this._postSet(23, oldVal, this._TemporaryTemplateName);
                     } else if (name.equals("TemporaryTemplateResource")) {
                        oldVal = this._TemporaryTemplateResource;
                        this._TemporaryTemplateResource = (String)v;
                        this._postSet(22, oldVal, this._TemporaryTemplateResource);
                     } else if (name.equals("customizer")) {
                        JMSServer oldVal = this._customizer;
                        this._customizer = (JMSServer)v;
                     } else {
                        super.putValue(name, v);
                     }
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllowsPersistentDowngrade")) {
         return new Boolean(this._AllowsPersistentDowngrade);
      } else if (name.equals("BlockingSendPolicy")) {
         return this._BlockingSendPolicy;
      } else if (name.equals("BytesMaximum")) {
         return new Long(this._BytesMaximum);
      } else if (name.equals("BytesPagingEnabled")) {
         return new Boolean(this._BytesPagingEnabled);
      } else if (name.equals("BytesThresholdHigh")) {
         return new Long(this._BytesThresholdHigh);
      } else if (name.equals("BytesThresholdLow")) {
         return new Long(this._BytesThresholdLow);
      } else if (name.equals("ConsumptionPausedAtStartup")) {
         return this._ConsumptionPausedAtStartup;
      } else if (name.equals("Destinations")) {
         return this._Destinations;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ExpirationScanInterval")) {
         return new Integer(this._ExpirationScanInterval);
      } else if (name.equals("HostingTemporaryDestinations")) {
         return new Boolean(this._HostingTemporaryDestinations);
      } else if (name.equals("InsertionPausedAtStartup")) {
         return this._InsertionPausedAtStartup;
      } else if (name.equals("JDBCStoreUpgradeEnabled")) {
         return new Boolean(this._JDBCStoreUpgradeEnabled);
      } else if (name.equals("JMSMessageLogFile")) {
         return this._JMSMessageLogFile;
      } else if (name.equals("JMSQueues")) {
         return this._JMSQueues;
      } else if (name.equals("JMSSessionPools")) {
         return this._JMSSessionPools;
      } else if (name.equals("JMSTopics")) {
         return this._JMSTopics;
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
      } else if (name.equals("MessagesPagingEnabled")) {
         return new Boolean(this._MessagesPagingEnabled);
      } else if (name.equals("MessagesThresholdHigh")) {
         return new Long(this._MessagesThresholdHigh);
      } else if (name.equals("MessagesThresholdLow")) {
         return new Long(this._MessagesThresholdLow);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PagingBlockSize")) {
         return new Integer(this._PagingBlockSize);
      } else if (name.equals("PagingDirectory")) {
         return this._PagingDirectory;
      } else if (name.equals("PagingFileLockingEnabled")) {
         return new Boolean(this._PagingFileLockingEnabled);
      } else if (name.equals("PagingIoBufferSize")) {
         return new Integer(this._PagingIoBufferSize);
      } else if (name.equals("PagingMaxFileSize")) {
         return new Long(this._PagingMaxFileSize);
      } else if (name.equals("PagingMaxWindowBufferSize")) {
         return new Integer(this._PagingMaxWindowBufferSize);
      } else if (name.equals("PagingMessageCompressionEnabled")) {
         return new Boolean(this._PagingMessageCompressionEnabled);
      } else if (name.equals("PagingMinWindowBufferSize")) {
         return new Integer(this._PagingMinWindowBufferSize);
      } else if (name.equals("PagingStore")) {
         return this._PagingStore;
      } else if (name.equals("PersistentStore")) {
         return this._PersistentStore;
      } else if (name.equals("ProductionPausedAtStartup")) {
         return this._ProductionPausedAtStartup;
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("SessionPools")) {
         return this._SessionPools;
      } else if (name.equals("Store")) {
         return this._Store;
      } else if (name.equals("StoreEnabled")) {
         return new Boolean(this._StoreEnabled);
      } else if (name.equals("StoreMessageCompressionEnabled")) {
         return new Boolean(this._StoreMessageCompressionEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("TemporaryTemplate")) {
         return this._TemporaryTemplate;
      } else if (name.equals("TemporaryTemplateName")) {
         return this._TemporaryTemplateName;
      } else if (name.equals("TemporaryTemplateResource")) {
         return this._TemporaryTemplateResource;
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
                  return 16;
               }
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
            case 7:
            case 8:
            case 10:
            case 14:
            case 15:
            case 25:
            case 31:
            case 32:
            case 35:
            default:
               break;
            case 9:
               if (s.equals("jms-queue")) {
                  return 48;
               }

               if (s.equals("jms-topic")) {
                  return 49;
               }
               break;
            case 11:
               if (s.equals("destination")) {
                  return 15;
               }
               break;
            case 12:
               if (s.equals("paging-store")) {
                  return 31;
               }

               if (s.equals("server-names")) {
                  return 12;
               }

               if (s.equals("session-pool")) {
                  return 13;
               }
               break;
            case 13:
               if (s.equals("bytes-maximum")) {
                  return 24;
               }

               if (s.equals("store-enabled")) {
                  return 18;
               }
               break;
            case 16:
               if (s.equals("jms-session-pool")) {
                  return 14;
               }

               if (s.equals("messages-maximum")) {
                  return 27;
               }

               if (s.equals("paging-directory")) {
                  return 35;
               }

               if (s.equals("persistent-store")) {
                  return 17;
               }
               break;
            case 17:
               if (s.equals("paging-block-size")) {
                  return 41;
               }
               break;
            case 18:
               if (s.equals("temporary-template")) {
                  return 20;
               }
               break;
            case 19:
               if (s.equals("bytes-threshold-low")) {
                  return 26;
               }

               if (s.equals("message-buffer-size")) {
                  return 34;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("blocking-send-policy")) {
                  return 44;
               }

               if (s.equals("bytes-threshold-high")) {
                  return 25;
               }

               if (s.equals("jms-message-log-file")) {
                  return 50;
               }

               if (s.equals("maximum-message-size")) {
                  return 43;
               }

               if (s.equals("paging-max-file-size")) {
                  return 40;
               }

               if (s.equals("bytes-paging-enabled")) {
                  return 33;
               }
               break;
            case 21:
               if (s.equals("paging-io-buffer-size")) {
                  return 39;
               }
               break;
            case 22:
               if (s.equals("messages-threshold-low")) {
                  return 29;
               }
               break;
            case 23:
               if (s.equals("messages-threshold-high")) {
                  return 28;
               }

               if (s.equals("temporary-template-name")) {
                  return 23;
               }

               if (s.equals("messages-paging-enabled")) {
                  return 32;
               }
               break;
            case 24:
               if (s.equals("expiration-scan-interval")) {
                  return 42;
               }
               break;
            case 26:
               if (s.equals("jdbc-store-upgrade-enabled")) {
                  return 30;
               }
               break;
            case 27:
               if (s.equals("insertion-paused-at-startup")) {
                  return 46;
               }

               if (s.equals("message-compression-options")) {
                  return 54;
               }

               if (s.equals("temporary-template-resource")) {
                  return 22;
               }

               if (s.equals("allows-persistent-downgrade")) {
                  return 19;
               }

               if (s.equals("paging-file-locking-enabled")) {
                  return 36;
               }
               break;
            case 28:
               if (s.equals("production-paused-at-startup")) {
                  return 45;
               }
               break;
            case 29:
               if (s.equals("consumption-paused-at-startup")) {
                  return 47;
               }

               if (s.equals("paging-max-window-buffer-size")) {
                  return 38;
               }

               if (s.equals("paging-min-window-buffer-size")) {
                  return 37;
               }
               break;
            case 30:
               if (s.equals("hosting-temporary-destinations")) {
                  return 21;
               }
               break;
            case 33:
               if (s.equals("store-message-compression-enabled")) {
                  return 51;
               }
               break;
            case 34:
               if (s.equals("paging-message-compression-enabled")) {
                  return 52;
               }
               break;
            case 36:
               if (s.equals("message-compression-options-override")) {
                  return 53;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 14:
               return new JMSSessionPoolMBeanImpl.SchemaHelper2();
            case 48:
               return new JMSQueueMBeanImpl.SchemaHelper2();
            case 49:
               return new JMSTopicMBeanImpl.SchemaHelper2();
            case 50:
               return new JMSMessageLogFileMBeanImpl.SchemaHelper2();
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
               return "session-pool";
            case 14:
               return "jms-session-pool";
            case 15:
               return "destination";
            case 16:
               return "store";
            case 17:
               return "persistent-store";
            case 18:
               return "store-enabled";
            case 19:
               return "allows-persistent-downgrade";
            case 20:
               return "temporary-template";
            case 21:
               return "hosting-temporary-destinations";
            case 22:
               return "temporary-template-resource";
            case 23:
               return "temporary-template-name";
            case 24:
               return "bytes-maximum";
            case 25:
               return "bytes-threshold-high";
            case 26:
               return "bytes-threshold-low";
            case 27:
               return "messages-maximum";
            case 28:
               return "messages-threshold-high";
            case 29:
               return "messages-threshold-low";
            case 30:
               return "jdbc-store-upgrade-enabled";
            case 31:
               return "paging-store";
            case 32:
               return "messages-paging-enabled";
            case 33:
               return "bytes-paging-enabled";
            case 34:
               return "message-buffer-size";
            case 35:
               return "paging-directory";
            case 36:
               return "paging-file-locking-enabled";
            case 37:
               return "paging-min-window-buffer-size";
            case 38:
               return "paging-max-window-buffer-size";
            case 39:
               return "paging-io-buffer-size";
            case 40:
               return "paging-max-file-size";
            case 41:
               return "paging-block-size";
            case 42:
               return "expiration-scan-interval";
            case 43:
               return "maximum-message-size";
            case 44:
               return "blocking-send-policy";
            case 45:
               return "production-paused-at-startup";
            case 46:
               return "insertion-paused-at-startup";
            case 47:
               return "consumption-paused-at-startup";
            case 48:
               return "jms-queue";
            case 49:
               return "jms-topic";
            case 50:
               return "jms-message-log-file";
            case 51:
               return "store-message-compression-enabled";
            case 52:
               return "paging-message-compression-enabled";
            case 53:
               return "message-compression-options-override";
            case 54:
               return "message-compression-options";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 14:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
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
      private JMSServerMBeanImpl bean;

      protected Helper(JMSServerMBeanImpl bean) {
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
               return "SessionPools";
            case 14:
               return "JMSSessionPools";
            case 15:
               return "Destinations";
            case 16:
               return "Store";
            case 17:
               return "PersistentStore";
            case 18:
               return "StoreEnabled";
            case 19:
               return "AllowsPersistentDowngrade";
            case 20:
               return "TemporaryTemplate";
            case 21:
               return "HostingTemporaryDestinations";
            case 22:
               return "TemporaryTemplateResource";
            case 23:
               return "TemporaryTemplateName";
            case 24:
               return "BytesMaximum";
            case 25:
               return "BytesThresholdHigh";
            case 26:
               return "BytesThresholdLow";
            case 27:
               return "MessagesMaximum";
            case 28:
               return "MessagesThresholdHigh";
            case 29:
               return "MessagesThresholdLow";
            case 30:
               return "JDBCStoreUpgradeEnabled";
            case 31:
               return "PagingStore";
            case 32:
               return "MessagesPagingEnabled";
            case 33:
               return "BytesPagingEnabled";
            case 34:
               return "MessageBufferSize";
            case 35:
               return "PagingDirectory";
            case 36:
               return "PagingFileLockingEnabled";
            case 37:
               return "PagingMinWindowBufferSize";
            case 38:
               return "PagingMaxWindowBufferSize";
            case 39:
               return "PagingIoBufferSize";
            case 40:
               return "PagingMaxFileSize";
            case 41:
               return "PagingBlockSize";
            case 42:
               return "ExpirationScanInterval";
            case 43:
               return "MaximumMessageSize";
            case 44:
               return "BlockingSendPolicy";
            case 45:
               return "ProductionPausedAtStartup";
            case 46:
               return "InsertionPausedAtStartup";
            case 47:
               return "ConsumptionPausedAtStartup";
            case 48:
               return "JMSQueues";
            case 49:
               return "JMSTopics";
            case 50:
               return "JMSMessageLogFile";
            case 51:
               return "StoreMessageCompressionEnabled";
            case 52:
               return "PagingMessageCompressionEnabled";
            case 53:
               return "MessageCompressionOptionsOverride";
            case 54:
               return "MessageCompressionOptions";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BlockingSendPolicy")) {
            return 44;
         } else if (propName.equals("BytesMaximum")) {
            return 24;
         } else if (propName.equals("BytesThresholdHigh")) {
            return 25;
         } else if (propName.equals("BytesThresholdLow")) {
            return 26;
         } else if (propName.equals("ConsumptionPausedAtStartup")) {
            return 47;
         } else if (propName.equals("Destinations")) {
            return 15;
         } else if (propName.equals("ExpirationScanInterval")) {
            return 42;
         } else if (propName.equals("InsertionPausedAtStartup")) {
            return 46;
         } else if (propName.equals("JMSMessageLogFile")) {
            return 50;
         } else if (propName.equals("JMSQueues")) {
            return 48;
         } else if (propName.equals("JMSSessionPools")) {
            return 14;
         } else if (propName.equals("JMSTopics")) {
            return 49;
         } else if (propName.equals("MaximumMessageSize")) {
            return 43;
         } else if (propName.equals("MessageBufferSize")) {
            return 34;
         } else if (propName.equals("MessageCompressionOptions")) {
            return 54;
         } else if (propName.equals("MessageCompressionOptionsOverride")) {
            return 53;
         } else if (propName.equals("MessagesMaximum")) {
            return 27;
         } else if (propName.equals("MessagesThresholdHigh")) {
            return 28;
         } else if (propName.equals("MessagesThresholdLow")) {
            return 29;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PagingBlockSize")) {
            return 41;
         } else if (propName.equals("PagingDirectory")) {
            return 35;
         } else if (propName.equals("PagingIoBufferSize")) {
            return 39;
         } else if (propName.equals("PagingMaxFileSize")) {
            return 40;
         } else if (propName.equals("PagingMaxWindowBufferSize")) {
            return 38;
         } else if (propName.equals("PagingMinWindowBufferSize")) {
            return 37;
         } else if (propName.equals("PagingStore")) {
            return 31;
         } else if (propName.equals("PersistentStore")) {
            return 17;
         } else if (propName.equals("ProductionPausedAtStartup")) {
            return 45;
         } else if (propName.equals("ServerNames")) {
            return 12;
         } else if (propName.equals("SessionPools")) {
            return 13;
         } else if (propName.equals("Store")) {
            return 16;
         } else if (propName.equals("StoreEnabled")) {
            return 18;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("TemporaryTemplate")) {
            return 20;
         } else if (propName.equals("TemporaryTemplateName")) {
            return 23;
         } else if (propName.equals("TemporaryTemplateResource")) {
            return 22;
         } else if (propName.equals("AllowsPersistentDowngrade")) {
            return 19;
         } else if (propName.equals("BytesPagingEnabled")) {
            return 33;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("HostingTemporaryDestinations")) {
            return 21;
         } else if (propName.equals("JDBCStoreUpgradeEnabled")) {
            return 30;
         } else if (propName.equals("MessagesPagingEnabled")) {
            return 32;
         } else if (propName.equals("PagingFileLockingEnabled")) {
            return 36;
         } else if (propName.equals("PagingMessageCompressionEnabled")) {
            return 52;
         } else {
            return propName.equals("StoreMessageCompressionEnabled") ? 51 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getJMSMessageLogFile() != null) {
            iterators.add(new ArrayIterator(new JMSMessageLogFileMBean[]{this.bean.getJMSMessageLogFile()}));
         }

         iterators.add(new ArrayIterator(this.bean.getJMSQueues()));
         iterators.add(new ArrayIterator(this.bean.getJMSSessionPools()));
         iterators.add(new ArrayIterator(this.bean.getJMSTopics()));
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
            if (this.bean.isBlockingSendPolicySet()) {
               buf.append("BlockingSendPolicy");
               buf.append(String.valueOf(this.bean.getBlockingSendPolicy()));
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

            if (this.bean.isConsumptionPausedAtStartupSet()) {
               buf.append("ConsumptionPausedAtStartup");
               buf.append(String.valueOf(this.bean.getConsumptionPausedAtStartup()));
            }

            if (this.bean.isDestinationsSet()) {
               buf.append("Destinations");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDestinations())));
            }

            if (this.bean.isExpirationScanIntervalSet()) {
               buf.append("ExpirationScanInterval");
               buf.append(String.valueOf(this.bean.getExpirationScanInterval()));
            }

            if (this.bean.isInsertionPausedAtStartupSet()) {
               buf.append("InsertionPausedAtStartup");
               buf.append(String.valueOf(this.bean.getInsertionPausedAtStartup()));
            }

            childValue = this.computeChildHashValue(this.bean.getJMSMessageLogFile());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getJMSQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSSessionPools().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSSessionPools()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSTopics()[i]);
            }

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

            if (this.bean.isPagingBlockSizeSet()) {
               buf.append("PagingBlockSize");
               buf.append(String.valueOf(this.bean.getPagingBlockSize()));
            }

            if (this.bean.isPagingDirectorySet()) {
               buf.append("PagingDirectory");
               buf.append(String.valueOf(this.bean.getPagingDirectory()));
            }

            if (this.bean.isPagingIoBufferSizeSet()) {
               buf.append("PagingIoBufferSize");
               buf.append(String.valueOf(this.bean.getPagingIoBufferSize()));
            }

            if (this.bean.isPagingMaxFileSizeSet()) {
               buf.append("PagingMaxFileSize");
               buf.append(String.valueOf(this.bean.getPagingMaxFileSize()));
            }

            if (this.bean.isPagingMaxWindowBufferSizeSet()) {
               buf.append("PagingMaxWindowBufferSize");
               buf.append(String.valueOf(this.bean.getPagingMaxWindowBufferSize()));
            }

            if (this.bean.isPagingMinWindowBufferSizeSet()) {
               buf.append("PagingMinWindowBufferSize");
               buf.append(String.valueOf(this.bean.getPagingMinWindowBufferSize()));
            }

            if (this.bean.isPagingStoreSet()) {
               buf.append("PagingStore");
               buf.append(String.valueOf(this.bean.getPagingStore()));
            }

            if (this.bean.isPersistentStoreSet()) {
               buf.append("PersistentStore");
               buf.append(String.valueOf(this.bean.getPersistentStore()));
            }

            if (this.bean.isProductionPausedAtStartupSet()) {
               buf.append("ProductionPausedAtStartup");
               buf.append(String.valueOf(this.bean.getProductionPausedAtStartup()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            if (this.bean.isSessionPoolsSet()) {
               buf.append("SessionPools");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSessionPools())));
            }

            if (this.bean.isStoreSet()) {
               buf.append("Store");
               buf.append(String.valueOf(this.bean.getStore()));
            }

            if (this.bean.isStoreEnabledSet()) {
               buf.append("StoreEnabled");
               buf.append(String.valueOf(this.bean.getStoreEnabled()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isTemporaryTemplateSet()) {
               buf.append("TemporaryTemplate");
               buf.append(String.valueOf(this.bean.getTemporaryTemplate()));
            }

            if (this.bean.isTemporaryTemplateNameSet()) {
               buf.append("TemporaryTemplateName");
               buf.append(String.valueOf(this.bean.getTemporaryTemplateName()));
            }

            if (this.bean.isTemporaryTemplateResourceSet()) {
               buf.append("TemporaryTemplateResource");
               buf.append(String.valueOf(this.bean.getTemporaryTemplateResource()));
            }

            if (this.bean.isAllowsPersistentDowngradeSet()) {
               buf.append("AllowsPersistentDowngrade");
               buf.append(String.valueOf(this.bean.isAllowsPersistentDowngrade()));
            }

            if (this.bean.isBytesPagingEnabledSet()) {
               buf.append("BytesPagingEnabled");
               buf.append(String.valueOf(this.bean.isBytesPagingEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isHostingTemporaryDestinationsSet()) {
               buf.append("HostingTemporaryDestinations");
               buf.append(String.valueOf(this.bean.isHostingTemporaryDestinations()));
            }

            if (this.bean.isJDBCStoreUpgradeEnabledSet()) {
               buf.append("JDBCStoreUpgradeEnabled");
               buf.append(String.valueOf(this.bean.isJDBCStoreUpgradeEnabled()));
            }

            if (this.bean.isMessagesPagingEnabledSet()) {
               buf.append("MessagesPagingEnabled");
               buf.append(String.valueOf(this.bean.isMessagesPagingEnabled()));
            }

            if (this.bean.isPagingFileLockingEnabledSet()) {
               buf.append("PagingFileLockingEnabled");
               buf.append(String.valueOf(this.bean.isPagingFileLockingEnabled()));
            }

            if (this.bean.isPagingMessageCompressionEnabledSet()) {
               buf.append("PagingMessageCompressionEnabled");
               buf.append(String.valueOf(this.bean.isPagingMessageCompressionEnabled()));
            }

            if (this.bean.isStoreMessageCompressionEnabledSet()) {
               buf.append("StoreMessageCompressionEnabled");
               buf.append(String.valueOf(this.bean.isStoreMessageCompressionEnabled()));
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
            JMSServerMBeanImpl otherTyped = (JMSServerMBeanImpl)other;
            this.computeDiff("BlockingSendPolicy", this.bean.getBlockingSendPolicy(), otherTyped.getBlockingSendPolicy(), true);
            this.computeDiff("BytesMaximum", this.bean.getBytesMaximum(), otherTyped.getBytesMaximum(), true);
            this.computeDiff("BytesThresholdHigh", this.bean.getBytesThresholdHigh(), otherTyped.getBytesThresholdHigh(), true);
            this.computeDiff("BytesThresholdLow", this.bean.getBytesThresholdLow(), otherTyped.getBytesThresholdLow(), true);
            this.computeDiff("ConsumptionPausedAtStartup", this.bean.getConsumptionPausedAtStartup(), otherTyped.getConsumptionPausedAtStartup(), false);
            this.computeDiff("ExpirationScanInterval", this.bean.getExpirationScanInterval(), otherTyped.getExpirationScanInterval(), true);
            this.computeDiff("InsertionPausedAtStartup", this.bean.getInsertionPausedAtStartup(), otherTyped.getInsertionPausedAtStartup(), false);
            this.computeSubDiff("JMSMessageLogFile", this.bean.getJMSMessageLogFile(), otherTyped.getJMSMessageLogFile());
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSQueues", this.bean.getJMSQueues(), otherTyped.getJMSQueues(), false);
            }

            this.computeChildDiff("JMSSessionPools", this.bean.getJMSSessionPools(), otherTyped.getJMSSessionPools(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSTopics", this.bean.getJMSTopics(), otherTyped.getJMSTopics(), true);
            }

            this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            this.computeDiff("MessageBufferSize", this.bean.getMessageBufferSize(), otherTyped.getMessageBufferSize(), true);
            this.computeDiff("MessageCompressionOptions", this.bean.getMessageCompressionOptions(), otherTyped.getMessageCompressionOptions(), true);
            this.computeDiff("MessageCompressionOptionsOverride", this.bean.getMessageCompressionOptionsOverride(), otherTyped.getMessageCompressionOptionsOverride(), true);
            this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            this.computeDiff("MessagesThresholdHigh", this.bean.getMessagesThresholdHigh(), otherTyped.getMessagesThresholdHigh(), true);
            this.computeDiff("MessagesThresholdLow", this.bean.getMessagesThresholdLow(), otherTyped.getMessagesThresholdLow(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PagingBlockSize", this.bean.getPagingBlockSize(), otherTyped.getPagingBlockSize(), false);
            this.computeDiff("PagingDirectory", this.bean.getPagingDirectory(), otherTyped.getPagingDirectory(), false);
            this.computeDiff("PagingIoBufferSize", this.bean.getPagingIoBufferSize(), otherTyped.getPagingIoBufferSize(), false);
            this.computeDiff("PagingMaxFileSize", this.bean.getPagingMaxFileSize(), otherTyped.getPagingMaxFileSize(), false);
            this.computeDiff("PagingMaxWindowBufferSize", this.bean.getPagingMaxWindowBufferSize(), otherTyped.getPagingMaxWindowBufferSize(), false);
            this.computeDiff("PagingMinWindowBufferSize", this.bean.getPagingMinWindowBufferSize(), otherTyped.getPagingMinWindowBufferSize(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("PagingStore", this.bean.getPagingStore(), otherTyped.getPagingStore(), false);
            }

            this.computeDiff("PersistentStore", this.bean.getPersistentStore(), otherTyped.getPersistentStore(), false);
            this.computeDiff("ProductionPausedAtStartup", this.bean.getProductionPausedAtStartup(), otherTyped.getProductionPausedAtStartup(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Store", this.bean.getStore(), otherTyped.getStore(), false);
            }

            this.computeDiff("StoreEnabled", this.bean.getStoreEnabled(), otherTyped.getStoreEnabled(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TemporaryTemplate", this.bean.getTemporaryTemplate(), otherTyped.getTemporaryTemplate(), false);
            }

            this.computeDiff("TemporaryTemplateName", this.bean.getTemporaryTemplateName(), otherTyped.getTemporaryTemplateName(), true);
            this.computeDiff("TemporaryTemplateResource", this.bean.getTemporaryTemplateResource(), otherTyped.getTemporaryTemplateResource(), true);
            this.computeDiff("AllowsPersistentDowngrade", this.bean.isAllowsPersistentDowngrade(), otherTyped.isAllowsPersistentDowngrade(), true);
            this.computeDiff("BytesPagingEnabled", this.bean.isBytesPagingEnabled(), otherTyped.isBytesPagingEnabled(), false);
            this.computeDiff("HostingTemporaryDestinations", this.bean.isHostingTemporaryDestinations(), otherTyped.isHostingTemporaryDestinations(), true);
            this.computeDiff("JDBCStoreUpgradeEnabled", this.bean.isJDBCStoreUpgradeEnabled(), otherTyped.isJDBCStoreUpgradeEnabled(), false);
            this.computeDiff("MessagesPagingEnabled", this.bean.isMessagesPagingEnabled(), otherTyped.isMessagesPagingEnabled(), false);
            this.computeDiff("PagingFileLockingEnabled", this.bean.isPagingFileLockingEnabled(), otherTyped.isPagingFileLockingEnabled(), false);
            this.computeDiff("PagingMessageCompressionEnabled", this.bean.isPagingMessageCompressionEnabled(), otherTyped.isPagingMessageCompressionEnabled(), true);
            this.computeDiff("StoreMessageCompressionEnabled", this.bean.isStoreMessageCompressionEnabled(), otherTyped.isStoreMessageCompressionEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSServerMBeanImpl original = (JMSServerMBeanImpl)event.getSourceBean();
            JMSServerMBeanImpl proposed = (JMSServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BlockingSendPolicy")) {
                  original.setBlockingSendPolicy(proposed.getBlockingSendPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("BytesMaximum")) {
                  original.setBytesMaximum(proposed.getBytesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("BytesThresholdHigh")) {
                  original.setBytesThresholdHigh(proposed.getBytesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("BytesThresholdLow")) {
                  original.setBytesThresholdLow(proposed.getBytesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("ConsumptionPausedAtStartup")) {
                  original.setConsumptionPausedAtStartup(proposed.getConsumptionPausedAtStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 47);
               } else if (!prop.equals("Destinations")) {
                  if (prop.equals("ExpirationScanInterval")) {
                     original.setExpirationScanInterval(proposed.getExpirationScanInterval());
                     original._conditionalUnset(update.isUnsetUpdate(), 42);
                  } else if (prop.equals("InsertionPausedAtStartup")) {
                     original.setInsertionPausedAtStartup(proposed.getInsertionPausedAtStartup());
                     original._conditionalUnset(update.isUnsetUpdate(), 46);
                  } else if (prop.equals("JMSMessageLogFile")) {
                     if (type == 2) {
                        original.setJMSMessageLogFile((JMSMessageLogFileMBean)this.createCopy((AbstractDescriptorBean)proposed.getJMSMessageLogFile()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("JMSMessageLogFile", original.getJMSMessageLogFile());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 50);
                  } else if (prop.equals("JMSQueues")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addJMSQueue((JMSQueueMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeJMSQueue((JMSQueueMBean)update.getRemovedObject());
                     }

                     if (original.getJMSQueues() == null || original.getJMSQueues().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 48);
                     }
                  } else if (prop.equals("JMSSessionPools")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addJMSSessionPool((JMSSessionPoolMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeJMSSessionPool((JMSSessionPoolMBean)update.getRemovedObject());
                     }

                     if (original.getJMSSessionPools() == null || original.getJMSSessionPools().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     }
                  } else if (prop.equals("JMSTopics")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addJMSTopic((JMSTopicMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeJMSTopic((JMSTopicMBean)update.getRemovedObject());
                     }

                     if (original.getJMSTopics() == null || original.getJMSTopics().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 49);
                     }
                  } else if (prop.equals("MaximumMessageSize")) {
                     original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 43);
                  } else if (prop.equals("MessageBufferSize")) {
                     original.setMessageBufferSize(proposed.getMessageBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  } else if (prop.equals("MessageCompressionOptions")) {
                     original.setMessageCompressionOptions(proposed.getMessageCompressionOptions());
                     original._conditionalUnset(update.isUnsetUpdate(), 54);
                  } else if (prop.equals("MessageCompressionOptionsOverride")) {
                     original.setMessageCompressionOptionsOverride(proposed.getMessageCompressionOptionsOverride());
                     original._conditionalUnset(update.isUnsetUpdate(), 53);
                  } else if (prop.equals("MessagesMaximum")) {
                     original.setMessagesMaximum(proposed.getMessagesMaximum());
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("MessagesThresholdHigh")) {
                     original.setMessagesThresholdHigh(proposed.getMessagesThresholdHigh());
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  } else if (prop.equals("MessagesThresholdLow")) {
                     original.setMessagesThresholdLow(proposed.getMessagesThresholdLow());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("PagingBlockSize")) {
                     original.setPagingBlockSize(proposed.getPagingBlockSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 41);
                  } else if (prop.equals("PagingDirectory")) {
                     original.setPagingDirectory(proposed.getPagingDirectory());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("PagingIoBufferSize")) {
                     original.setPagingIoBufferSize(proposed.getPagingIoBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 39);
                  } else if (prop.equals("PagingMaxFileSize")) {
                     original.setPagingMaxFileSize(proposed.getPagingMaxFileSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 40);
                  } else if (prop.equals("PagingMaxWindowBufferSize")) {
                     original.setPagingMaxWindowBufferSize(proposed.getPagingMaxWindowBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
                  } else if (prop.equals("PagingMinWindowBufferSize")) {
                     original.setPagingMinWindowBufferSize(proposed.getPagingMinWindowBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
                  } else if (prop.equals("PagingStore")) {
                     original.setPagingStoreAsString(proposed.getPagingStoreAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  } else if (prop.equals("PersistentStore")) {
                     original.setPersistentStoreAsString(proposed.getPersistentStoreAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("ProductionPausedAtStartup")) {
                     original.setProductionPausedAtStartup(proposed.getProductionPausedAtStartup());
                     original._conditionalUnset(update.isUnsetUpdate(), 45);
                  } else if (!prop.equals("ServerNames") && !prop.equals("SessionPools")) {
                     if (prop.equals("Store")) {
                        original.setStoreAsString(proposed.getStoreAsString());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (prop.equals("StoreEnabled")) {
                        original.setStoreEnabled(proposed.getStoreEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 18);
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
                     } else if (prop.equals("TemporaryTemplate")) {
                        original.setTemporaryTemplateAsString(proposed.getTemporaryTemplateAsString());
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else if (prop.equals("TemporaryTemplateName")) {
                        original.setTemporaryTemplateName(proposed.getTemporaryTemplateName());
                        original._conditionalUnset(update.isUnsetUpdate(), 23);
                     } else if (prop.equals("TemporaryTemplateResource")) {
                        original.setTemporaryTemplateResource(proposed.getTemporaryTemplateResource());
                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("AllowsPersistentDowngrade")) {
                        original.setAllowsPersistentDowngrade(proposed.isAllowsPersistentDowngrade());
                        original._conditionalUnset(update.isUnsetUpdate(), 19);
                     } else if (prop.equals("BytesPagingEnabled")) {
                        original.setBytesPagingEnabled(proposed.isBytesPagingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (!prop.equals("DynamicallyCreated")) {
                        if (prop.equals("HostingTemporaryDestinations")) {
                           original.setHostingTemporaryDestinations(proposed.isHostingTemporaryDestinations());
                           original._conditionalUnset(update.isUnsetUpdate(), 21);
                        } else if (prop.equals("JDBCStoreUpgradeEnabled")) {
                           original.setJDBCStoreUpgradeEnabled(proposed.isJDBCStoreUpgradeEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 30);
                        } else if (prop.equals("MessagesPagingEnabled")) {
                           original.setMessagesPagingEnabled(proposed.isMessagesPagingEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 32);
                        } else if (prop.equals("PagingFileLockingEnabled")) {
                           original.setPagingFileLockingEnabled(proposed.isPagingFileLockingEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 36);
                        } else if (prop.equals("PagingMessageCompressionEnabled")) {
                           original.setPagingMessageCompressionEnabled(proposed.isPagingMessageCompressionEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 52);
                        } else if (prop.equals("StoreMessageCompressionEnabled")) {
                           original.setStoreMessageCompressionEnabled(proposed.isStoreMessageCompressionEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 51);
                        } else {
                           super.applyPropertyUpdate(event, update);
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
            JMSServerMBeanImpl copy = (JMSServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BlockingSendPolicy")) && this.bean.isBlockingSendPolicySet()) {
               copy.setBlockingSendPolicy(this.bean.getBlockingSendPolicy());
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

            if ((excludeProps == null || !excludeProps.contains("ConsumptionPausedAtStartup")) && this.bean.isConsumptionPausedAtStartupSet()) {
               copy.setConsumptionPausedAtStartup(this.bean.getConsumptionPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("ExpirationScanInterval")) && this.bean.isExpirationScanIntervalSet()) {
               copy.setExpirationScanInterval(this.bean.getExpirationScanInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("InsertionPausedAtStartup")) && this.bean.isInsertionPausedAtStartupSet()) {
               copy.setInsertionPausedAtStartup(this.bean.getInsertionPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSMessageLogFile")) && this.bean.isJMSMessageLogFileSet() && !copy._isSet(50)) {
               Object o = this.bean.getJMSMessageLogFile();
               copy.setJMSMessageLogFile((JMSMessageLogFileMBean)null);
               copy.setJMSMessageLogFile(o == null ? null : (JMSMessageLogFileMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSQueues")) && this.bean.isJMSQueuesSet() && !copy._isSet(48)) {
               JMSQueueMBean[] oldJMSQueues = this.bean.getJMSQueues();
               JMSQueueMBean[] newJMSQueues = new JMSQueueMBean[oldJMSQueues.length];

               for(i = 0; i < newJMSQueues.length; ++i) {
                  newJMSQueues[i] = (JMSQueueMBean)((JMSQueueMBean)this.createCopy((AbstractDescriptorBean)oldJMSQueues[i], includeObsolete));
               }

               copy.setJMSQueues(newJMSQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSSessionPools")) && this.bean.isJMSSessionPoolsSet() && !copy._isSet(14)) {
               JMSSessionPoolMBean[] oldJMSSessionPools = this.bean.getJMSSessionPools();
               JMSSessionPoolMBean[] newJMSSessionPools = new JMSSessionPoolMBean[oldJMSSessionPools.length];

               for(i = 0; i < newJMSSessionPools.length; ++i) {
                  newJMSSessionPools[i] = (JMSSessionPoolMBean)((JMSSessionPoolMBean)this.createCopy((AbstractDescriptorBean)oldJMSSessionPools[i], includeObsolete));
               }

               copy.setJMSSessionPools(newJMSSessionPools);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSTopics")) && this.bean.isJMSTopicsSet() && !copy._isSet(49)) {
               JMSTopicMBean[] oldJMSTopics = this.bean.getJMSTopics();
               JMSTopicMBean[] newJMSTopics = new JMSTopicMBean[oldJMSTopics.length];

               for(i = 0; i < newJMSTopics.length; ++i) {
                  newJMSTopics[i] = (JMSTopicMBean)((JMSTopicMBean)this.createCopy((AbstractDescriptorBean)oldJMSTopics[i], includeObsolete));
               }

               copy.setJMSTopics(newJMSTopics);
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

            if ((excludeProps == null || !excludeProps.contains("PagingBlockSize")) && this.bean.isPagingBlockSizeSet()) {
               copy.setPagingBlockSize(this.bean.getPagingBlockSize());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingDirectory")) && this.bean.isPagingDirectorySet()) {
               copy.setPagingDirectory(this.bean.getPagingDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingIoBufferSize")) && this.bean.isPagingIoBufferSizeSet()) {
               copy.setPagingIoBufferSize(this.bean.getPagingIoBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingMaxFileSize")) && this.bean.isPagingMaxFileSizeSet()) {
               copy.setPagingMaxFileSize(this.bean.getPagingMaxFileSize());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingMaxWindowBufferSize")) && this.bean.isPagingMaxWindowBufferSizeSet()) {
               copy.setPagingMaxWindowBufferSize(this.bean.getPagingMaxWindowBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingMinWindowBufferSize")) && this.bean.isPagingMinWindowBufferSizeSet()) {
               copy.setPagingMinWindowBufferSize(this.bean.getPagingMinWindowBufferSize());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PagingStore")) && this.bean.isPagingStoreSet()) {
               copy._unSet(copy, 31);
               copy.setPagingStoreAsString(this.bean.getPagingStoreAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStore")) && this.bean.isPersistentStoreSet()) {
               copy._unSet(copy, 17);
               copy.setPersistentStoreAsString(this.bean.getPersistentStoreAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ProductionPausedAtStartup")) && this.bean.isProductionPausedAtStartupSet()) {
               copy.setProductionPausedAtStartup(this.bean.getProductionPausedAtStartup());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Store")) && this.bean.isStoreSet()) {
               copy._unSet(copy, 16);
               copy.setStoreAsString(this.bean.getStoreAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreEnabled")) && this.bean.isStoreEnabledSet()) {
               copy.setStoreEnabled(this.bean.getStoreEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TemporaryTemplate")) && this.bean.isTemporaryTemplateSet()) {
               copy._unSet(copy, 20);
               copy.setTemporaryTemplateAsString(this.bean.getTemporaryTemplateAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("TemporaryTemplateName")) && this.bean.isTemporaryTemplateNameSet()) {
               copy.setTemporaryTemplateName(this.bean.getTemporaryTemplateName());
            }

            if ((excludeProps == null || !excludeProps.contains("TemporaryTemplateResource")) && this.bean.isTemporaryTemplateResourceSet()) {
               copy.setTemporaryTemplateResource(this.bean.getTemporaryTemplateResource());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowsPersistentDowngrade")) && this.bean.isAllowsPersistentDowngradeSet()) {
               copy.setAllowsPersistentDowngrade(this.bean.isAllowsPersistentDowngrade());
            }

            if ((excludeProps == null || !excludeProps.contains("BytesPagingEnabled")) && this.bean.isBytesPagingEnabledSet()) {
               copy.setBytesPagingEnabled(this.bean.isBytesPagingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HostingTemporaryDestinations")) && this.bean.isHostingTemporaryDestinationsSet()) {
               copy.setHostingTemporaryDestinations(this.bean.isHostingTemporaryDestinations());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCStoreUpgradeEnabled")) && this.bean.isJDBCStoreUpgradeEnabledSet()) {
               copy.setJDBCStoreUpgradeEnabled(this.bean.isJDBCStoreUpgradeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesPagingEnabled")) && this.bean.isMessagesPagingEnabledSet()) {
               copy.setMessagesPagingEnabled(this.bean.isMessagesPagingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingFileLockingEnabled")) && this.bean.isPagingFileLockingEnabledSet()) {
               copy.setPagingFileLockingEnabled(this.bean.isPagingFileLockingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingMessageCompressionEnabled")) && this.bean.isPagingMessageCompressionEnabledSet()) {
               copy.setPagingMessageCompressionEnabled(this.bean.isPagingMessageCompressionEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreMessageCompressionEnabled")) && this.bean.isStoreMessageCompressionEnabledSet()) {
               copy.setStoreMessageCompressionEnabled(this.bean.isStoreMessageCompressionEnabled());
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
         this.inferSubTree(this.bean.getDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSMessageLogFile(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSSessionPools(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSTopics(), clazz, annotation);
         this.inferSubTree(this.bean.getPagingStore(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistentStore(), clazz, annotation);
         this.inferSubTree(this.bean.getSessionPools(), clazz, annotation);
         this.inferSubTree(this.bean.getStore(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getTemporaryTemplate(), clazz, annotation);
      }
   }
}
