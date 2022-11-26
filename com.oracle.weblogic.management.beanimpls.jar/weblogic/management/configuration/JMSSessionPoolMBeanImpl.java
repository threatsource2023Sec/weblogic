package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.jms.module.validators.JMSModuleValidator;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JMSSessionPool;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMSSessionPoolMBeanImpl extends ConfigurationMBeanImpl implements JMSSessionPoolMBean, Serializable {
   private String _AcknowledgeMode;
   private JMSConnectionConsumerMBean[] _ConnectionConsumers;
   private String _ConnectionFactory;
   private boolean _DynamicallyCreated;
   private JMSConnectionConsumerMBean[] _JMSConnectionConsumers;
   private String _ListenerClass;
   private String _Name;
   private int _SessionsMaximum;
   private String[] _Tags;
   private boolean _Transacted;
   private transient JMSSessionPool _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JMSSessionPoolMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JMSSessionPoolMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JMSSessionPoolMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JMSSessionPoolMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JMSSessionPoolMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      JMSSessionPoolMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public JMSSessionPoolMBeanImpl() {
      try {
         this._customizer = new JMSSessionPool(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMSSessionPoolMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMSSessionPool(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMSSessionPoolMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMSSessionPool(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public JMSConnectionConsumerMBean[] getConnectionConsumers() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getConnectionConsumers() : this._customizer.getConnectionConsumers();
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

   public boolean isConnectionConsumersInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isConnectionConsumersSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setConnectionConsumers(JMSConnectionConsumerMBean[] param0) throws InvalidAttributeValueException {
      JMSConnectionConsumerMBean[] param0 = param0 == null ? new JMSConnectionConsumerMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionConsumers = (JMSConnectionConsumerMBean[])param0;
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
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addConnectionConsumer(JMSConnectionConsumerMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         JMSConnectionConsumerMBean[] _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._extendArray(this.getConnectionConsumers(), JMSConnectionConsumerMBean.class, param0));

         try {
            this.setConnectionConsumers(_new);
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

   public boolean removeConnectionConsumer(JMSConnectionConsumerMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      JMSConnectionConsumerMBean[] _old = this.getConnectionConsumers();
      JMSConnectionConsumerMBean[] _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._removeElement(_old, JMSConnectionConsumerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setConnectionConsumers(_new);
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

   public void addJMSConnectionConsumer(JMSConnectionConsumerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         JMSConnectionConsumerMBean[] _new;
         if (this._isSet(11)) {
            _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._extendArray(this.getJMSConnectionConsumers(), JMSConnectionConsumerMBean.class, param0));
         } else {
            _new = new JMSConnectionConsumerMBean[]{param0};
         }

         try {
            this.setJMSConnectionConsumers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSConnectionConsumerMBean[] getJMSConnectionConsumers() {
      JMSConnectionConsumerMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         delegateArray = this._getDelegateBean().getJMSConnectionConsumers();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JMSConnectionConsumers.length; ++j) {
               if (delegateArray[i].getName().equals(this._JMSConnectionConsumers[j].getName())) {
                  ((JMSConnectionConsumerMBeanImpl)this._JMSConnectionConsumers[j])._setDelegateBean((JMSConnectionConsumerMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JMSConnectionConsumerMBeanImpl mbean = new JMSConnectionConsumerMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 11);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JMSConnectionConsumerMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(11)) {
                     this.setJMSConnectionConsumers((JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._extendArray(this._JMSConnectionConsumers, JMSConnectionConsumerMBean.class, mbean)));
                  } else {
                     this.setJMSConnectionConsumers(new JMSConnectionConsumerMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JMSConnectionConsumerMBean[0];
      }

      if (this._JMSConnectionConsumers != null) {
         List removeList = new ArrayList();
         JMSConnectionConsumerMBean[] var18 = this._JMSConnectionConsumers;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JMSConnectionConsumerMBean bn = var18[var5];
            JMSConnectionConsumerMBeanImpl bni = (JMSConnectionConsumerMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JMSConnectionConsumerMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JMSConnectionConsumerMBean delegateTo = var10[var12];
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
            JMSConnectionConsumerMBean removeIt = (JMSConnectionConsumerMBean)var19.next();
            JMSConnectionConsumerMBeanImpl removeItImpl = (JMSConnectionConsumerMBeanImpl)removeIt;
            JMSConnectionConsumerMBean[] _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._removeElement(this._JMSConnectionConsumers, JMSConnectionConsumerMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJMSConnectionConsumers(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JMSConnectionConsumers;
   }

   public boolean isJMSConnectionConsumersInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         JMSConnectionConsumerMBean[] elements = this.getJMSConnectionConsumers();
         JMSConnectionConsumerMBean[] var2 = elements;
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

   public boolean isJMSConnectionConsumersSet() {
      return this._isSet(11);
   }

   public void removeJMSConnectionConsumer(JMSConnectionConsumerMBean param0) {
      this.destroyJMSConnectionConsumer(param0);
   }

   public void setJMSConnectionConsumers(JMSConnectionConsumerMBean[] param0) throws InvalidAttributeValueException {
      JMSConnectionConsumerMBean[] param0 = param0 == null ? new JMSConnectionConsumerMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JMSConnectionConsumers, (Object[])param0, handler, new Comparator() {
            public int compare(JMSConnectionConsumerMBean o1, JMSConnectionConsumerMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JMSConnectionConsumerMBean bean = (JMSConnectionConsumerMBean)var3.next();
            JMSConnectionConsumerMBeanImpl beanImpl = (JMSConnectionConsumerMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(11);
      JMSConnectionConsumerMBean[] _oldVal = this._JMSConnectionConsumers;
      this._JMSConnectionConsumers = (JMSConnectionConsumerMBean[])param0;
      this._postSet(11, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var11.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSConnectionConsumerMBean createJMSConnectionConsumer(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      JMSConnectionConsumerMBeanImpl lookup = (JMSConnectionConsumerMBeanImpl)this.lookupJMSConnectionConsumer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSConnectionConsumerMBeanImpl _val = new JMSConnectionConsumerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSConnectionConsumer(_val);
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

   public void destroyJMSConnectionConsumer(JMSConnectionConsumerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         JMSConnectionConsumerMBean[] _old = this.getJMSConnectionConsumers();
         JMSConnectionConsumerMBean[] _new = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])this._getHelper()._removeElement(_old, JMSConnectionConsumerMBean.class, param0));
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
                  JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var6.next();
                  JMSConnectionConsumerMBeanImpl childImpl = (JMSConnectionConsumerMBeanImpl)_child;
                  JMSConnectionConsumerMBeanImpl lookup = (JMSConnectionConsumerMBeanImpl)source.lookupJMSConnectionConsumer(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSConnectionConsumer(lookup);
                  }
               }

               this.setJMSConnectionConsumers(_new);
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

   public JMSConnectionConsumerMBean lookupJMSConnectionConsumer(String param0) {
      Object[] aary = (Object[])this.getJMSConnectionConsumers();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSConnectionConsumerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSConnectionConsumerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public String getConnectionFactory() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionFactory(), this) : this._ConnectionFactory;
   }

   public boolean isConnectionFactoryInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isConnectionFactorySet() {
      return this._isSet(12);
   }

   public void setConnectionFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._ConnectionFactory;
      this._ConnectionFactory = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSConnectionConsumerMBean createJMSConnectionConsumer(String param0, JMSConnectionConsumerMBean param1) {
      return this._customizer.createJMSConnectionConsumer(param0, param1);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void destroyJMSConnectionConsumer(String param0, JMSConnectionConsumerMBean param1) {
      this._customizer.destroyJMSConnectionConsumer(param0, param1);
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getListenerClass() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getListenerClass(), this) : this._ListenerClass;
   }

   public boolean isListenerClassInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isListenerClassSet() {
      return this._isSet(13);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setListenerClass(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._ListenerClass;
      this._ListenerClass = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAcknowledgeMode() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getAcknowledgeMode(), this) : this._AcknowledgeMode;
   }

   public boolean isAcknowledgeModeInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isAcknowledgeModeSet() {
      return this._isSet(14);
   }

   public void setAcknowledgeMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Auto", "Client", "Dups-Ok", "None"};
      param0 = LegalChecks.checkInEnum("AcknowledgeMode", param0, _set);
      boolean wasSet = this._isSet(14);
      String _oldVal = this._AcknowledgeMode;
      this._AcknowledgeMode = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var5.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSessionsMaximum() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getSessionsMaximum() : this._SessionsMaximum;
   }

   public boolean isSessionsMaximumInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isSessionsMaximumSet() {
      return this._isSet(15);
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

   public void setSessionsMaximum(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SessionsMaximum", (long)param0, -1L, 2147483647L);
      JMSModuleValidator.validateSessionPoolSessionsMaximum(param0);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._SessionsMaximum;
      this._SessionsMaximum = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isTransacted() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().isTransacted() : this._Transacted;
   }

   public boolean isTransactedInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isTransactedSet() {
      return this._isSet(16);
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

   public void setTransacted(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      boolean _oldVal = this._Transacted;
      this._Transacted = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
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
         JMSSessionPoolMBeanImpl source = (JMSSessionPoolMBeanImpl)var4.next();
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._AcknowledgeMode = "Auto";
               if (initOne) {
                  break;
               }
            case 10:
               this._ConnectionConsumers = new JMSConnectionConsumerMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ConnectionFactory = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._JMSConnectionConsumers = new JMSConnectionConsumerMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._ListenerClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 15:
               this._SessionsMaximum = -1;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._Transacted = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "JMSSessionPool";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AcknowledgeMode")) {
         oldVal = this._AcknowledgeMode;
         this._AcknowledgeMode = (String)v;
         this._postSet(14, oldVal, this._AcknowledgeMode);
      } else {
         JMSConnectionConsumerMBean[] oldVal;
         if (name.equals("ConnectionConsumers")) {
            oldVal = this._ConnectionConsumers;
            this._ConnectionConsumers = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])v);
            this._postSet(10, oldVal, this._ConnectionConsumers);
         } else if (name.equals("ConnectionFactory")) {
            oldVal = this._ConnectionFactory;
            this._ConnectionFactory = (String)v;
            this._postSet(12, oldVal, this._ConnectionFactory);
         } else {
            boolean oldVal;
            if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("JMSConnectionConsumers")) {
               oldVal = this._JMSConnectionConsumers;
               this._JMSConnectionConsumers = (JMSConnectionConsumerMBean[])((JMSConnectionConsumerMBean[])v);
               this._postSet(11, oldVal, this._JMSConnectionConsumers);
            } else if (name.equals("ListenerClass")) {
               oldVal = this._ListenerClass;
               this._ListenerClass = (String)v;
               this._postSet(13, oldVal, this._ListenerClass);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("SessionsMaximum")) {
               int oldVal = this._SessionsMaximum;
               this._SessionsMaximum = (Integer)v;
               this._postSet(15, oldVal, this._SessionsMaximum);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("Transacted")) {
               oldVal = this._Transacted;
               this._Transacted = (Boolean)v;
               this._postSet(16, oldVal, this._Transacted);
            } else if (name.equals("customizer")) {
               JMSSessionPool oldVal = this._customizer;
               this._customizer = (JMSSessionPool)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcknowledgeMode")) {
         return this._AcknowledgeMode;
      } else if (name.equals("ConnectionConsumers")) {
         return this._ConnectionConsumers;
      } else if (name.equals("ConnectionFactory")) {
         return this._ConnectionFactory;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("JMSConnectionConsumers")) {
         return this._JMSConnectionConsumers;
      } else if (name.equals("ListenerClass")) {
         return this._ListenerClass;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("SessionsMaximum")) {
         return new Integer(this._SessionsMaximum);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Transacted")) {
         return new Boolean(this._Transacted);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 15:
            case 17:
            case 20:
            case 21:
            case 22:
            default:
               break;
            case 10:
               if (s.equals("transacted")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("listener-class")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("acknowledge-mode")) {
                  return 14;
               }

               if (s.equals("sessions-maximum")) {
                  return 15;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 12;
               }
               break;
            case 19:
               if (s.equals("connection-consumer")) {
                  return 10;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 23:
               if (s.equals("jms-connection-consumer")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new JMSConnectionConsumerMBeanImpl.SchemaHelper2();
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "connection-consumer";
            case 11:
               return "jms-connection-consumer";
            case 12:
               return "connection-factory";
            case 13:
               return "listener-class";
            case 14:
               return "acknowledge-mode";
            case 15:
               return "sessions-maximum";
            case 16:
               return "transacted";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private JMSSessionPoolMBeanImpl bean;

      protected Helper(JMSSessionPoolMBeanImpl bean) {
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
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "ConnectionConsumers";
            case 11:
               return "JMSConnectionConsumers";
            case 12:
               return "ConnectionFactory";
            case 13:
               return "ListenerClass";
            case 14:
               return "AcknowledgeMode";
            case 15:
               return "SessionsMaximum";
            case 16:
               return "Transacted";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcknowledgeMode")) {
            return 14;
         } else if (propName.equals("ConnectionConsumers")) {
            return 10;
         } else if (propName.equals("ConnectionFactory")) {
            return 12;
         } else if (propName.equals("JMSConnectionConsumers")) {
            return 11;
         } else if (propName.equals("ListenerClass")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SessionsMaximum")) {
            return 15;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("Transacted") ? 16 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getJMSConnectionConsumers()));
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
            if (this.bean.isAcknowledgeModeSet()) {
               buf.append("AcknowledgeMode");
               buf.append(String.valueOf(this.bean.getAcknowledgeMode()));
            }

            if (this.bean.isConnectionConsumersSet()) {
               buf.append("ConnectionConsumers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConnectionConsumers())));
            }

            if (this.bean.isConnectionFactorySet()) {
               buf.append("ConnectionFactory");
               buf.append(String.valueOf(this.bean.getConnectionFactory()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getJMSConnectionConsumers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSConnectionConsumers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isListenerClassSet()) {
               buf.append("ListenerClass");
               buf.append(String.valueOf(this.bean.getListenerClass()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isSessionsMaximumSet()) {
               buf.append("SessionsMaximum");
               buf.append(String.valueOf(this.bean.getSessionsMaximum()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isTransactedSet()) {
               buf.append("Transacted");
               buf.append(String.valueOf(this.bean.isTransacted()));
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
            JMSSessionPoolMBeanImpl otherTyped = (JMSSessionPoolMBeanImpl)other;
            this.computeDiff("AcknowledgeMode", this.bean.getAcknowledgeMode(), otherTyped.getAcknowledgeMode(), false);
            this.computeDiff("ConnectionFactory", this.bean.getConnectionFactory(), otherTyped.getConnectionFactory(), false);
            this.computeChildDiff("JMSConnectionConsumers", this.bean.getJMSConnectionConsumers(), otherTyped.getJMSConnectionConsumers(), false);
            this.computeDiff("ListenerClass", this.bean.getListenerClass(), otherTyped.getListenerClass(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("SessionsMaximum", this.bean.getSessionsMaximum(), otherTyped.getSessionsMaximum(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Transacted", this.bean.isTransacted(), otherTyped.isTransacted(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSSessionPoolMBeanImpl original = (JMSSessionPoolMBeanImpl)event.getSourceBean();
            JMSSessionPoolMBeanImpl proposed = (JMSSessionPoolMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcknowledgeMode")) {
                  original.setAcknowledgeMode(proposed.getAcknowledgeMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (!prop.equals("ConnectionConsumers")) {
                  if (prop.equals("ConnectionFactory")) {
                     original.setConnectionFactory(proposed.getConnectionFactory());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("JMSConnectionConsumers")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addJMSConnectionConsumer((JMSConnectionConsumerMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeJMSConnectionConsumer((JMSConnectionConsumerMBean)update.getRemovedObject());
                     }

                     if (original.getJMSConnectionConsumers() == null || original.getJMSConnectionConsumers().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     }
                  } else if (prop.equals("ListenerClass")) {
                     original.setListenerClass(proposed.getListenerClass());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("SessionsMaximum")) {
                     original.setSessionsMaximum(proposed.getSessionsMaximum());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
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
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("Transacted")) {
                        original.setTransacted(proposed.isTransacted());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            JMSSessionPoolMBeanImpl copy = (JMSSessionPoolMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcknowledgeMode")) && this.bean.isAcknowledgeModeSet()) {
               copy.setAcknowledgeMode(this.bean.getAcknowledgeMode());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactory")) && this.bean.isConnectionFactorySet()) {
               copy.setConnectionFactory(this.bean.getConnectionFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSConnectionConsumers")) && this.bean.isJMSConnectionConsumersSet() && !copy._isSet(11)) {
               JMSConnectionConsumerMBean[] oldJMSConnectionConsumers = this.bean.getJMSConnectionConsumers();
               JMSConnectionConsumerMBean[] newJMSConnectionConsumers = new JMSConnectionConsumerMBean[oldJMSConnectionConsumers.length];

               for(int i = 0; i < newJMSConnectionConsumers.length; ++i) {
                  newJMSConnectionConsumers[i] = (JMSConnectionConsumerMBean)((JMSConnectionConsumerMBean)this.createCopy((AbstractDescriptorBean)oldJMSConnectionConsumers[i], includeObsolete));
               }

               copy.setJMSConnectionConsumers(newJMSConnectionConsumers);
            }

            if ((excludeProps == null || !excludeProps.contains("ListenerClass")) && this.bean.isListenerClassSet()) {
               copy.setListenerClass(this.bean.getListenerClass());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionsMaximum")) && this.bean.isSessionsMaximumSet()) {
               copy.setSessionsMaximum(this.bean.getSessionsMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Transacted")) && this.bean.isTransactedSet()) {
               copy.setTransacted(this.bean.isTransacted());
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
         this.inferSubTree(this.bean.getConnectionConsumers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSConnectionConsumers(), clazz, annotation);
      }
   }
}
