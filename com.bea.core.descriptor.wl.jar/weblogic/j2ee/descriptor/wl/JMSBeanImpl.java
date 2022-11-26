package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMSBeanImpl extends SettableBeanImpl implements JMSBean, Serializable {
   private JMSConnectionFactoryBean[] _ConnectionFactories;
   private DestinationKeyBean[] _DestinationKeys;
   private DistributedQueueBean[] _DistributedQueues;
   private DistributedTopicBean[] _DistributedTopics;
   private ForeignServerBean[] _ForeignServers;
   private String _Notes;
   private QueueBean[] _Queues;
   private QuotaBean[] _Quotas;
   private SAFErrorHandlingBean[] _SAFErrorHandlings;
   private SAFImportedDestinationsBean[] _SAFImportedDestinations;
   private SAFRemoteContextBean[] _SAFRemoteContexts;
   private TemplateBean[] _Templates;
   private TopicBean[] _Topics;
   private UniformDistributedQueueBean[] _UniformDistributedQueues;
   private UniformDistributedTopicBean[] _UniformDistributedTopics;
   private int _Version;
   private static SchemaHelper2 _schemaHelper;

   public JMSBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JMSBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JMSBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public int getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(0);
   }

   public void setVersion(int param0) {
      int _oldVal = this._Version;
      this._Version = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getNotes() {
      return this._Notes;
   }

   public boolean isNotesInherited() {
      return false;
   }

   public boolean isNotesSet() {
      return this._isSet(1);
   }

   public void setNotes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Notes;
      this._Notes = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addQuota(QuotaBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         QuotaBean[] _new;
         if (this._isSet(2)) {
            _new = (QuotaBean[])((QuotaBean[])this._getHelper()._extendArray(this.getQuotas(), QuotaBean.class, param0));
         } else {
            _new = new QuotaBean[]{param0};
         }

         try {
            this.setQuotas(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public QuotaBean[] getQuotas() {
      return this._Quotas;
   }

   public boolean isQuotasInherited() {
      return false;
   }

   public boolean isQuotasSet() {
      return this._isSet(2);
   }

   public void removeQuota(QuotaBean param0) {
      this.destroyQuota(param0);
   }

   public void setQuotas(QuotaBean[] param0) throws InvalidAttributeValueException {
      QuotaBean[] param0 = param0 == null ? new QuotaBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      QuotaBean[] _oldVal = this._Quotas;
      this._Quotas = (QuotaBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public QuotaBean createQuota(String param0) {
      QuotaBeanImpl lookup = (QuotaBeanImpl)this.lookupQuota(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         QuotaBeanImpl _val = new QuotaBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addQuota(_val);
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

   public void destroyQuota(QuotaBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         QuotaBean[] _old = this.getQuotas();
         QuotaBean[] _new = (QuotaBean[])((QuotaBean[])this._getHelper()._removeElement(_old, QuotaBean.class, param0));
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
               this.setQuotas(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public QuotaBean lookupQuota(String param0) {
      Object[] aary = (Object[])this._Quotas;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      QuotaBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (QuotaBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addTemplate(TemplateBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         TemplateBean[] _new;
         if (this._isSet(3)) {
            _new = (TemplateBean[])((TemplateBean[])this._getHelper()._extendArray(this.getTemplates(), TemplateBean.class, param0));
         } else {
            _new = new TemplateBean[]{param0};
         }

         try {
            this.setTemplates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TemplateBean[] getTemplates() {
      return this._Templates;
   }

   public boolean isTemplatesInherited() {
      return false;
   }

   public boolean isTemplatesSet() {
      return this._isSet(3);
   }

   public void removeTemplate(TemplateBean param0) {
      this.destroyTemplate(param0);
   }

   public void setTemplates(TemplateBean[] param0) throws InvalidAttributeValueException {
      TemplateBean[] param0 = param0 == null ? new TemplateBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      TemplateBean[] _oldVal = this._Templates;
      this._Templates = (TemplateBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public TemplateBean createTemplate(String param0) {
      TemplateBeanImpl lookup = (TemplateBeanImpl)this.lookupTemplate(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         TemplateBeanImpl _val = new TemplateBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addTemplate(_val);
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

   public void destroyTemplate(TemplateBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         TemplateBean[] _old = this.getTemplates();
         TemplateBean[] _new = (TemplateBean[])((TemplateBean[])this._getHelper()._removeElement(_old, TemplateBean.class, param0));
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
               this.setTemplates(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public TemplateBean lookupTemplate(String param0) {
      Object[] aary = (Object[])this._Templates;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      TemplateBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (TemplateBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addDestinationKey(DestinationKeyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         DestinationKeyBean[] _new;
         if (this._isSet(4)) {
            _new = (DestinationKeyBean[])((DestinationKeyBean[])this._getHelper()._extendArray(this.getDestinationKeys(), DestinationKeyBean.class, param0));
         } else {
            _new = new DestinationKeyBean[]{param0};
         }

         try {
            this.setDestinationKeys(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DestinationKeyBean[] getDestinationKeys() {
      return this._DestinationKeys;
   }

   public boolean isDestinationKeysInherited() {
      return false;
   }

   public boolean isDestinationKeysSet() {
      return this._isSet(4);
   }

   public void removeDestinationKey(DestinationKeyBean param0) {
      this.destroyDestinationKey(param0);
   }

   public void setDestinationKeys(DestinationKeyBean[] param0) throws InvalidAttributeValueException {
      DestinationKeyBean[] param0 = param0 == null ? new DestinationKeyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DestinationKeyBean[] _oldVal = this._DestinationKeys;
      this._DestinationKeys = (DestinationKeyBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public DestinationKeyBean createDestinationKey(String param0) {
      DestinationKeyBeanImpl lookup = (DestinationKeyBeanImpl)this.lookupDestinationKey(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DestinationKeyBeanImpl _val = new DestinationKeyBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addDestinationKey(_val);
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

   public void destroyDestinationKey(DestinationKeyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         DestinationKeyBean[] _old = this.getDestinationKeys();
         DestinationKeyBean[] _new = (DestinationKeyBean[])((DestinationKeyBean[])this._getHelper()._removeElement(_old, DestinationKeyBean.class, param0));
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
               this.setDestinationKeys(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public DestinationKeyBean lookupDestinationKey(String param0) {
      Object[] aary = (Object[])this._DestinationKeys;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      DestinationKeyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (DestinationKeyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addConnectionFactory(JMSConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         JMSConnectionFactoryBean[] _new;
         if (this._isSet(5)) {
            _new = (JMSConnectionFactoryBean[])((JMSConnectionFactoryBean[])this._getHelper()._extendArray(this.getConnectionFactories(), JMSConnectionFactoryBean.class, param0));
         } else {
            _new = new JMSConnectionFactoryBean[]{param0};
         }

         try {
            this.setConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSConnectionFactoryBean[] getConnectionFactories() {
      return this._ConnectionFactories;
   }

   public boolean isConnectionFactoriesInherited() {
      return false;
   }

   public boolean isConnectionFactoriesSet() {
      return this._isSet(5);
   }

   public void removeConnectionFactory(JMSConnectionFactoryBean param0) {
      this.destroyConnectionFactory(param0);
   }

   public void setConnectionFactories(JMSConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JMSConnectionFactoryBean[] param0 = param0 == null ? new JMSConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMSConnectionFactoryBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (JMSConnectionFactoryBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public JMSConnectionFactoryBean createConnectionFactory(String param0) {
      JMSConnectionFactoryBeanImpl lookup = (JMSConnectionFactoryBeanImpl)this.lookupConnectionFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSConnectionFactoryBeanImpl _val = new JMSConnectionFactoryBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addConnectionFactory(_val);
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

   public void destroyConnectionFactory(JMSConnectionFactoryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         JMSConnectionFactoryBean[] _old = this.getConnectionFactories();
         JMSConnectionFactoryBean[] _new = (JMSConnectionFactoryBean[])((JMSConnectionFactoryBean[])this._getHelper()._removeElement(_old, JMSConnectionFactoryBean.class, param0));
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
               this.setConnectionFactories(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public JMSConnectionFactoryBean lookupConnectionFactory(String param0) {
      Object[] aary = (Object[])this._ConnectionFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSConnectionFactoryBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSConnectionFactoryBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addForeignServer(ForeignServerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         ForeignServerBean[] _new;
         if (this._isSet(6)) {
            _new = (ForeignServerBean[])((ForeignServerBean[])this._getHelper()._extendArray(this.getForeignServers(), ForeignServerBean.class, param0));
         } else {
            _new = new ForeignServerBean[]{param0};
         }

         try {
            this.setForeignServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignServerBean[] getForeignServers() {
      return this._ForeignServers;
   }

   public boolean isForeignServersInherited() {
      return false;
   }

   public boolean isForeignServersSet() {
      return this._isSet(6);
   }

   public void removeForeignServer(ForeignServerBean param0) {
      this.destroyForeignServer(param0);
   }

   public void setForeignServers(ForeignServerBean[] param0) throws InvalidAttributeValueException {
      ForeignServerBean[] param0 = param0 == null ? new ForeignServerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignServerBean[] _oldVal = this._ForeignServers;
      this._ForeignServers = (ForeignServerBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public ForeignServerBean createForeignServer(String param0) {
      ForeignServerBeanImpl lookup = (ForeignServerBeanImpl)this.lookupForeignServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignServerBeanImpl _val = new ForeignServerBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignServer(_val);
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

   public void destroyForeignServer(ForeignServerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         ForeignServerBean[] _old = this.getForeignServers();
         ForeignServerBean[] _new = (ForeignServerBean[])((ForeignServerBean[])this._getHelper()._removeElement(_old, ForeignServerBean.class, param0));
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
               this.setForeignServers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ForeignServerBean lookupForeignServer(String param0) {
      Object[] aary = (Object[])this._ForeignServers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignServerBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignServerBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addQueue(QueueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         QueueBean[] _new;
         if (this._isSet(7)) {
            _new = (QueueBean[])((QueueBean[])this._getHelper()._extendArray(this.getQueues(), QueueBean.class, param0));
         } else {
            _new = new QueueBean[]{param0};
         }

         try {
            this.setQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public QueueBean[] getQueues() {
      return this._Queues;
   }

   public boolean isQueuesInherited() {
      return false;
   }

   public boolean isQueuesSet() {
      return this._isSet(7);
   }

   public void removeQueue(QueueBean param0) {
      this.destroyQueue(param0);
   }

   public void setQueues(QueueBean[] param0) throws InvalidAttributeValueException {
      QueueBean[] param0 = param0 == null ? new QueueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      QueueBean[] _oldVal = this._Queues;
      this._Queues = (QueueBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public QueueBean createQueue(String param0) {
      QueueBeanImpl lookup = (QueueBeanImpl)this.lookupQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         QueueBeanImpl _val = new QueueBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addQueue(_val);
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

   public void destroyQueue(QueueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         QueueBean[] _old = this.getQueues();
         QueueBean[] _new = (QueueBean[])((QueueBean[])this._getHelper()._removeElement(_old, QueueBean.class, param0));
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
               this.setQueues(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public QueueBean lookupQueue(String param0) {
      Object[] aary = (Object[])this._Queues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      QueueBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (QueueBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addTopic(TopicBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         TopicBean[] _new;
         if (this._isSet(8)) {
            _new = (TopicBean[])((TopicBean[])this._getHelper()._extendArray(this.getTopics(), TopicBean.class, param0));
         } else {
            _new = new TopicBean[]{param0};
         }

         try {
            this.setTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TopicBean[] getTopics() {
      return this._Topics;
   }

   public boolean isTopicsInherited() {
      return false;
   }

   public boolean isTopicsSet() {
      return this._isSet(8);
   }

   public void removeTopic(TopicBean param0) {
      this.destroyTopic(param0);
   }

   public void setTopics(TopicBean[] param0) throws InvalidAttributeValueException {
      TopicBean[] param0 = param0 == null ? new TopicBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      TopicBean[] _oldVal = this._Topics;
      this._Topics = (TopicBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public TopicBean createTopic(String param0) {
      TopicBeanImpl lookup = (TopicBeanImpl)this.lookupTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         TopicBeanImpl _val = new TopicBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addTopic(_val);
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

   public void destroyTopic(TopicBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         TopicBean[] _old = this.getTopics();
         TopicBean[] _new = (TopicBean[])((TopicBean[])this._getHelper()._removeElement(_old, TopicBean.class, param0));
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
               this.setTopics(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public TopicBean lookupTopic(String param0) {
      Object[] aary = (Object[])this._Topics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      TopicBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (TopicBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addDistributedQueue(DistributedQueueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         DistributedQueueBean[] _new;
         if (this._isSet(9)) {
            _new = (DistributedQueueBean[])((DistributedQueueBean[])this._getHelper()._extendArray(this.getDistributedQueues(), DistributedQueueBean.class, param0));
         } else {
            _new = new DistributedQueueBean[]{param0};
         }

         try {
            this.setDistributedQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DistributedQueueBean[] getDistributedQueues() {
      return this._DistributedQueues;
   }

   public boolean isDistributedQueuesInherited() {
      return false;
   }

   public boolean isDistributedQueuesSet() {
      return this._isSet(9);
   }

   public void removeDistributedQueue(DistributedQueueBean param0) {
      this.destroyDistributedQueue(param0);
   }

   public void setDistributedQueues(DistributedQueueBean[] param0) throws InvalidAttributeValueException {
      DistributedQueueBean[] param0 = param0 == null ? new DistributedQueueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DistributedQueueBean[] _oldVal = this._DistributedQueues;
      this._DistributedQueues = (DistributedQueueBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public DistributedQueueBean createDistributedQueue(String param0) {
      DistributedQueueBeanImpl lookup = (DistributedQueueBeanImpl)this.lookupDistributedQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DistributedQueueBeanImpl _val = new DistributedQueueBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addDistributedQueue(_val);
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

   public void destroyDistributedQueue(DistributedQueueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         DistributedQueueBean[] _old = this.getDistributedQueues();
         DistributedQueueBean[] _new = (DistributedQueueBean[])((DistributedQueueBean[])this._getHelper()._removeElement(_old, DistributedQueueBean.class, param0));
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
               this.setDistributedQueues(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public DistributedQueueBean lookupDistributedQueue(String param0) {
      Object[] aary = (Object[])this._DistributedQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      DistributedQueueBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (DistributedQueueBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addDistributedTopic(DistributedTopicBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         DistributedTopicBean[] _new;
         if (this._isSet(10)) {
            _new = (DistributedTopicBean[])((DistributedTopicBean[])this._getHelper()._extendArray(this.getDistributedTopics(), DistributedTopicBean.class, param0));
         } else {
            _new = new DistributedTopicBean[]{param0};
         }

         try {
            this.setDistributedTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DistributedTopicBean[] getDistributedTopics() {
      return this._DistributedTopics;
   }

   public boolean isDistributedTopicsInherited() {
      return false;
   }

   public boolean isDistributedTopicsSet() {
      return this._isSet(10);
   }

   public void removeDistributedTopic(DistributedTopicBean param0) {
      this.destroyDistributedTopic(param0);
   }

   public void setDistributedTopics(DistributedTopicBean[] param0) throws InvalidAttributeValueException {
      DistributedTopicBean[] param0 = param0 == null ? new DistributedTopicBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DistributedTopicBean[] _oldVal = this._DistributedTopics;
      this._DistributedTopics = (DistributedTopicBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public DistributedTopicBean createDistributedTopic(String param0) {
      DistributedTopicBeanImpl lookup = (DistributedTopicBeanImpl)this.lookupDistributedTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DistributedTopicBeanImpl _val = new DistributedTopicBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addDistributedTopic(_val);
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

   public void destroyDistributedTopic(DistributedTopicBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         DistributedTopicBean[] _old = this.getDistributedTopics();
         DistributedTopicBean[] _new = (DistributedTopicBean[])((DistributedTopicBean[])this._getHelper()._removeElement(_old, DistributedTopicBean.class, param0));
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
               this.setDistributedTopics(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public DistributedTopicBean lookupDistributedTopic(String param0) {
      Object[] aary = (Object[])this._DistributedTopics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      DistributedTopicBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (DistributedTopicBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addUniformDistributedQueue(UniformDistributedQueueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         UniformDistributedQueueBean[] _new;
         if (this._isSet(11)) {
            _new = (UniformDistributedQueueBean[])((UniformDistributedQueueBean[])this._getHelper()._extendArray(this.getUniformDistributedQueues(), UniformDistributedQueueBean.class, param0));
         } else {
            _new = new UniformDistributedQueueBean[]{param0};
         }

         try {
            this.setUniformDistributedQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public UniformDistributedQueueBean[] getUniformDistributedQueues() {
      return this._UniformDistributedQueues;
   }

   public boolean isUniformDistributedQueuesInherited() {
      return false;
   }

   public boolean isUniformDistributedQueuesSet() {
      return this._isSet(11);
   }

   public void removeUniformDistributedQueue(UniformDistributedQueueBean param0) {
      this.destroyUniformDistributedQueue(param0);
   }

   public void setUniformDistributedQueues(UniformDistributedQueueBean[] param0) throws InvalidAttributeValueException {
      UniformDistributedQueueBean[] param0 = param0 == null ? new UniformDistributedQueueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      UniformDistributedQueueBean[] _oldVal = this._UniformDistributedQueues;
      this._UniformDistributedQueues = (UniformDistributedQueueBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public UniformDistributedQueueBean createUniformDistributedQueue(String param0) {
      UniformDistributedQueueBeanImpl lookup = (UniformDistributedQueueBeanImpl)this.lookupUniformDistributedQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         UniformDistributedQueueBeanImpl _val = new UniformDistributedQueueBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addUniformDistributedQueue(_val);
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

   public void destroyUniformDistributedQueue(UniformDistributedQueueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         UniformDistributedQueueBean[] _old = this.getUniformDistributedQueues();
         UniformDistributedQueueBean[] _new = (UniformDistributedQueueBean[])((UniformDistributedQueueBean[])this._getHelper()._removeElement(_old, UniformDistributedQueueBean.class, param0));
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
               this.setUniformDistributedQueues(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public UniformDistributedQueueBean lookupUniformDistributedQueue(String param0) {
      Object[] aary = (Object[])this._UniformDistributedQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      UniformDistributedQueueBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (UniformDistributedQueueBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addUniformDistributedTopic(UniformDistributedTopicBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         UniformDistributedTopicBean[] _new;
         if (this._isSet(12)) {
            _new = (UniformDistributedTopicBean[])((UniformDistributedTopicBean[])this._getHelper()._extendArray(this.getUniformDistributedTopics(), UniformDistributedTopicBean.class, param0));
         } else {
            _new = new UniformDistributedTopicBean[]{param0};
         }

         try {
            this.setUniformDistributedTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public UniformDistributedTopicBean[] getUniformDistributedTopics() {
      return this._UniformDistributedTopics;
   }

   public boolean isUniformDistributedTopicsInherited() {
      return false;
   }

   public boolean isUniformDistributedTopicsSet() {
      return this._isSet(12);
   }

   public void removeUniformDistributedTopic(UniformDistributedTopicBean param0) {
      this.destroyUniformDistributedTopic(param0);
   }

   public void setUniformDistributedTopics(UniformDistributedTopicBean[] param0) throws InvalidAttributeValueException {
      UniformDistributedTopicBean[] param0 = param0 == null ? new UniformDistributedTopicBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      UniformDistributedTopicBean[] _oldVal = this._UniformDistributedTopics;
      this._UniformDistributedTopics = (UniformDistributedTopicBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public UniformDistributedTopicBean createUniformDistributedTopic(String param0) {
      UniformDistributedTopicBeanImpl lookup = (UniformDistributedTopicBeanImpl)this.lookupUniformDistributedTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         UniformDistributedTopicBeanImpl _val = new UniformDistributedTopicBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addUniformDistributedTopic(_val);
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

   public void destroyUniformDistributedTopic(UniformDistributedTopicBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         UniformDistributedTopicBean[] _old = this.getUniformDistributedTopics();
         UniformDistributedTopicBean[] _new = (UniformDistributedTopicBean[])((UniformDistributedTopicBean[])this._getHelper()._removeElement(_old, UniformDistributedTopicBean.class, param0));
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
               this.setUniformDistributedTopics(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public UniformDistributedTopicBean lookupUniformDistributedTopic(String param0) {
      Object[] aary = (Object[])this._UniformDistributedTopics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      UniformDistributedTopicBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (UniformDistributedTopicBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSAFImportedDestination(SAFImportedDestinationsBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         SAFImportedDestinationsBean[] _new;
         if (this._isSet(13)) {
            _new = (SAFImportedDestinationsBean[])((SAFImportedDestinationsBean[])this._getHelper()._extendArray(this.getSAFImportedDestinations(), SAFImportedDestinationsBean.class, param0));
         } else {
            _new = new SAFImportedDestinationsBean[]{param0};
         }

         try {
            this.setSAFImportedDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SAFImportedDestinationsBean[] getSAFImportedDestinations() {
      return this._SAFImportedDestinations;
   }

   public boolean isSAFImportedDestinationsInherited() {
      return false;
   }

   public boolean isSAFImportedDestinationsSet() {
      return this._isSet(13);
   }

   public void removeSAFImportedDestination(SAFImportedDestinationsBean param0) {
      this.destroySAFImportedDestinations(param0);
   }

   public void setSAFImportedDestinations(SAFImportedDestinationsBean[] param0) throws InvalidAttributeValueException {
      SAFImportedDestinationsBean[] param0 = param0 == null ? new SAFImportedDestinationsBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SAFImportedDestinationsBean[] _oldVal = this._SAFImportedDestinations;
      this._SAFImportedDestinations = (SAFImportedDestinationsBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public SAFImportedDestinationsBean createSAFImportedDestinations(String param0) {
      SAFImportedDestinationsBeanImpl lookup = (SAFImportedDestinationsBeanImpl)this.lookupSAFImportedDestinations(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFImportedDestinationsBeanImpl _val = new SAFImportedDestinationsBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFImportedDestination(_val);
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

   public void destroySAFImportedDestinations(SAFImportedDestinationsBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         SAFImportedDestinationsBean[] _old = this.getSAFImportedDestinations();
         SAFImportedDestinationsBean[] _new = (SAFImportedDestinationsBean[])((SAFImportedDestinationsBean[])this._getHelper()._removeElement(_old, SAFImportedDestinationsBean.class, param0));
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
               this.setSAFImportedDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SAFImportedDestinationsBean lookupSAFImportedDestinations(String param0) {
      Object[] aary = (Object[])this._SAFImportedDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFImportedDestinationsBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFImportedDestinationsBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSAFRemoteContext(SAFRemoteContextBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         SAFRemoteContextBean[] _new;
         if (this._isSet(14)) {
            _new = (SAFRemoteContextBean[])((SAFRemoteContextBean[])this._getHelper()._extendArray(this.getSAFRemoteContexts(), SAFRemoteContextBean.class, param0));
         } else {
            _new = new SAFRemoteContextBean[]{param0};
         }

         try {
            this.setSAFRemoteContexts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SAFRemoteContextBean[] getSAFRemoteContexts() {
      return this._SAFRemoteContexts;
   }

   public boolean isSAFRemoteContextsInherited() {
      return false;
   }

   public boolean isSAFRemoteContextsSet() {
      return this._isSet(14);
   }

   public void removeSAFRemoteContext(SAFRemoteContextBean param0) {
      this.destroySAFRemoteContext(param0);
   }

   public void setSAFRemoteContexts(SAFRemoteContextBean[] param0) throws InvalidAttributeValueException {
      SAFRemoteContextBean[] param0 = param0 == null ? new SAFRemoteContextBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFRemoteContextBean[] _oldVal = this._SAFRemoteContexts;
      this._SAFRemoteContexts = (SAFRemoteContextBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public SAFRemoteContextBean createSAFRemoteContext(String param0) {
      SAFRemoteContextBeanImpl lookup = (SAFRemoteContextBeanImpl)this.lookupSAFRemoteContext(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFRemoteContextBeanImpl _val = new SAFRemoteContextBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFRemoteContext(_val);
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

   public void destroySAFRemoteContext(SAFRemoteContextBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         SAFRemoteContextBean[] _old = this.getSAFRemoteContexts();
         SAFRemoteContextBean[] _new = (SAFRemoteContextBean[])((SAFRemoteContextBean[])this._getHelper()._removeElement(_old, SAFRemoteContextBean.class, param0));
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
               this.setSAFRemoteContexts(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SAFRemoteContextBean lookupSAFRemoteContext(String param0) {
      Object[] aary = (Object[])this._SAFRemoteContexts;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFRemoteContextBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFRemoteContextBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSAFErrorHandling(SAFErrorHandlingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         SAFErrorHandlingBean[] _new;
         if (this._isSet(15)) {
            _new = (SAFErrorHandlingBean[])((SAFErrorHandlingBean[])this._getHelper()._extendArray(this.getSAFErrorHandlings(), SAFErrorHandlingBean.class, param0));
         } else {
            _new = new SAFErrorHandlingBean[]{param0};
         }

         try {
            this.setSAFErrorHandlings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SAFErrorHandlingBean[] getSAFErrorHandlings() {
      return this._SAFErrorHandlings;
   }

   public boolean isSAFErrorHandlingsInherited() {
      return false;
   }

   public boolean isSAFErrorHandlingsSet() {
      return this._isSet(15);
   }

   public void removeSAFErrorHandling(SAFErrorHandlingBean param0) {
      this.destroySAFErrorHandling(param0);
   }

   public void setSAFErrorHandlings(SAFErrorHandlingBean[] param0) throws InvalidAttributeValueException {
      SAFErrorHandlingBean[] param0 = param0 == null ? new SAFErrorHandlingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFErrorHandlingBean[] _oldVal = this._SAFErrorHandlings;
      this._SAFErrorHandlings = (SAFErrorHandlingBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public SAFErrorHandlingBean createSAFErrorHandling(String param0) {
      SAFErrorHandlingBeanImpl lookup = (SAFErrorHandlingBeanImpl)this.lookupSAFErrorHandling(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFErrorHandlingBeanImpl _val = new SAFErrorHandlingBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFErrorHandling(_val);
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

   public void destroySAFErrorHandling(SAFErrorHandlingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         SAFErrorHandlingBean[] _old = this.getSAFErrorHandlings();
         SAFErrorHandlingBean[] _new = (SAFErrorHandlingBean[])((SAFErrorHandlingBean[])this._getHelper()._removeElement(_old, SAFErrorHandlingBean.class, param0));
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
               this.setSAFErrorHandlings(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SAFErrorHandlingBean lookupSAFErrorHandling(String param0) {
      Object[] aary = (Object[])this._SAFErrorHandlings;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFErrorHandlingBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFErrorHandlingBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSModuleValidator.validateJMSModule(this);
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._ConnectionFactories = new JMSConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._DestinationKeys = new DestinationKeyBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._DistributedQueues = new DistributedQueueBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._DistributedTopics = new DistributedTopicBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._ForeignServers = new ForeignServerBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Notes = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Queues = new QueueBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Quotas = new QuotaBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._SAFErrorHandlings = new SAFErrorHandlingBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._SAFImportedDestinations = new SAFImportedDestinationsBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._SAFRemoteContexts = new SAFRemoteContextBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Templates = new TemplateBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._Topics = new TopicBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._UniformDistributedQueues = new UniformDistributedQueueBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._UniformDistributedTopics = new UniformDistributedTopicBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Version = 1;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-jms/1.1/weblogic-jms.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-jms";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("notes")) {
                  return 1;
               }

               if (s.equals("queue")) {
                  return 7;
               }

               if (s.equals("quota")) {
                  return 2;
               }

               if (s.equals("topic")) {
                  return 8;
               }
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               break;
            case 7:
               if (s.equals("version")) {
                  return 0;
               }
               break;
            case 8:
               if (s.equals("template")) {
                  return 3;
               }
               break;
            case 14:
               if (s.equals("foreign-server")) {
                  return 6;
               }
               break;
            case 15:
               if (s.equals("destination-key")) {
                  return 4;
               }
               break;
            case 17:
               if (s.equals("distributed-queue")) {
                  return 9;
               }

               if (s.equals("distributed-topic")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 5;
               }

               if (s.equals("saf-error-handling")) {
                  return 15;
               }

               if (s.equals("saf-remote-context")) {
                  return 14;
               }
               break;
            case 25:
               if (s.equals("saf-imported-destinations")) {
                  return 13;
               }

               if (s.equals("uniform-distributed-queue")) {
                  return 11;
               }

               if (s.equals("uniform-distributed-topic")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new QuotaBeanImpl.SchemaHelper2();
            case 3:
               return new TemplateBeanImpl.SchemaHelper2();
            case 4:
               return new DestinationKeyBeanImpl.SchemaHelper2();
            case 5:
               return new JMSConnectionFactoryBeanImpl.SchemaHelper2();
            case 6:
               return new ForeignServerBeanImpl.SchemaHelper2();
            case 7:
               return new QueueBeanImpl.SchemaHelper2();
            case 8:
               return new TopicBeanImpl.SchemaHelper2();
            case 9:
               return new DistributedQueueBeanImpl.SchemaHelper2();
            case 10:
               return new DistributedTopicBeanImpl.SchemaHelper2();
            case 11:
               return new UniformDistributedQueueBeanImpl.SchemaHelper2();
            case 12:
               return new UniformDistributedTopicBeanImpl.SchemaHelper2();
            case 13:
               return new SAFImportedDestinationsBeanImpl.SchemaHelper2();
            case 14:
               return new SAFRemoteContextBeanImpl.SchemaHelper2();
            case 15:
               return new SAFErrorHandlingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-jms";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "version";
            case 1:
               return "notes";
            case 2:
               return "quota";
            case 3:
               return "template";
            case 4:
               return "destination-key";
            case 5:
               return "connection-factory";
            case 6:
               return "foreign-server";
            case 7:
               return "queue";
            case 8:
               return "topic";
            case 9:
               return "distributed-queue";
            case 10:
               return "distributed-topic";
            case 11:
               return "uniform-distributed-queue";
            case 12:
               return "uniform-distributed-topic";
            case 13:
               return "saf-imported-destinations";
            case 14:
               return "saf-remote-context";
            case 15:
               return "saf-error-handling";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JMSBeanImpl bean;

      protected Helper(JMSBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Version";
            case 1:
               return "Notes";
            case 2:
               return "Quotas";
            case 3:
               return "Templates";
            case 4:
               return "DestinationKeys";
            case 5:
               return "ConnectionFactories";
            case 6:
               return "ForeignServers";
            case 7:
               return "Queues";
            case 8:
               return "Topics";
            case 9:
               return "DistributedQueues";
            case 10:
               return "DistributedTopics";
            case 11:
               return "UniformDistributedQueues";
            case 12:
               return "UniformDistributedTopics";
            case 13:
               return "SAFImportedDestinations";
            case 14:
               return "SAFRemoteContexts";
            case 15:
               return "SAFErrorHandlings";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactories")) {
            return 5;
         } else if (propName.equals("DestinationKeys")) {
            return 4;
         } else if (propName.equals("DistributedQueues")) {
            return 9;
         } else if (propName.equals("DistributedTopics")) {
            return 10;
         } else if (propName.equals("ForeignServers")) {
            return 6;
         } else if (propName.equals("Notes")) {
            return 1;
         } else if (propName.equals("Queues")) {
            return 7;
         } else if (propName.equals("Quotas")) {
            return 2;
         } else if (propName.equals("SAFErrorHandlings")) {
            return 15;
         } else if (propName.equals("SAFImportedDestinations")) {
            return 13;
         } else if (propName.equals("SAFRemoteContexts")) {
            return 14;
         } else if (propName.equals("Templates")) {
            return 3;
         } else if (propName.equals("Topics")) {
            return 8;
         } else if (propName.equals("UniformDistributedQueues")) {
            return 11;
         } else if (propName.equals("UniformDistributedTopics")) {
            return 12;
         } else {
            return propName.equals("Version") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getDestinationKeys()));
         iterators.add(new ArrayIterator(this.bean.getDistributedQueues()));
         iterators.add(new ArrayIterator(this.bean.getDistributedTopics()));
         iterators.add(new ArrayIterator(this.bean.getForeignServers()));
         iterators.add(new ArrayIterator(this.bean.getQueues()));
         iterators.add(new ArrayIterator(this.bean.getQuotas()));
         iterators.add(new ArrayIterator(this.bean.getSAFErrorHandlings()));
         iterators.add(new ArrayIterator(this.bean.getSAFImportedDestinations()));
         iterators.add(new ArrayIterator(this.bean.getSAFRemoteContexts()));
         iterators.add(new ArrayIterator(this.bean.getTemplates()));
         iterators.add(new ArrayIterator(this.bean.getTopics()));
         iterators.add(new ArrayIterator(this.bean.getUniformDistributedQueues()));
         iterators.add(new ArrayIterator(this.bean.getUniformDistributedTopics()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDestinationKeys().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDestinationKeys()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDistributedQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDistributedQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDistributedTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDistributedTopics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getQuotas().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getQuotas()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSAFErrorHandlings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFErrorHandlings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSAFImportedDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFImportedDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSAFRemoteContexts().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFRemoteContexts()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTemplates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTemplates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTopics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getUniformDistributedQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getUniformDistributedQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getUniformDistributedTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getUniformDistributedTopics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
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
            JMSBeanImpl otherTyped = (JMSBeanImpl)other;
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), true);
            this.computeChildDiff("DestinationKeys", this.bean.getDestinationKeys(), otherTyped.getDestinationKeys(), true);
            this.computeChildDiff("DistributedQueues", this.bean.getDistributedQueues(), otherTyped.getDistributedQueues(), true);
            this.computeChildDiff("DistributedTopics", this.bean.getDistributedTopics(), otherTyped.getDistributedTopics(), true);
            this.computeChildDiff("ForeignServers", this.bean.getForeignServers(), otherTyped.getForeignServers(), true);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            this.computeChildDiff("Queues", this.bean.getQueues(), otherTyped.getQueues(), true);
            this.computeChildDiff("Quotas", this.bean.getQuotas(), otherTyped.getQuotas(), true);
            this.computeChildDiff("SAFErrorHandlings", this.bean.getSAFErrorHandlings(), otherTyped.getSAFErrorHandlings(), true);
            this.computeChildDiff("SAFImportedDestinations", this.bean.getSAFImportedDestinations(), otherTyped.getSAFImportedDestinations(), true);
            this.computeChildDiff("SAFRemoteContexts", this.bean.getSAFRemoteContexts(), otherTyped.getSAFRemoteContexts(), true);
            this.computeChildDiff("Templates", this.bean.getTemplates(), otherTyped.getTemplates(), true);
            this.computeChildDiff("Topics", this.bean.getTopics(), otherTyped.getTopics(), true);
            this.computeChildDiff("UniformDistributedQueues", this.bean.getUniformDistributedQueues(), otherTyped.getUniformDistributedQueues(), true);
            this.computeChildDiff("UniformDistributedTopics", this.bean.getUniformDistributedTopics(), otherTyped.getUniformDistributedTopics(), true);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSBeanImpl original = (JMSBeanImpl)event.getSourceBean();
            JMSBeanImpl proposed = (JMSBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConnectionFactory((JMSConnectionFactoryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConnectionFactory((JMSConnectionFactoryBean)update.getRemovedObject());
                  }

                  if (original.getConnectionFactories() == null || original.getConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("DestinationKeys")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDestinationKey((DestinationKeyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDestinationKey((DestinationKeyBean)update.getRemovedObject());
                  }

                  if (original.getDestinationKeys() == null || original.getDestinationKeys().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("DistributedQueues")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDistributedQueue((DistributedQueueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDistributedQueue((DistributedQueueBean)update.getRemovedObject());
                  }

                  if (original.getDistributedQueues() == null || original.getDistributedQueues().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("DistributedTopics")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDistributedTopic((DistributedTopicBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDistributedTopic((DistributedTopicBean)update.getRemovedObject());
                  }

                  if (original.getDistributedTopics() == null || original.getDistributedTopics().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("ForeignServers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignServer((ForeignServerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignServer((ForeignServerBean)update.getRemovedObject());
                  }

                  if (original.getForeignServers() == null || original.getForeignServers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Queues")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addQueue((QueueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeQueue((QueueBean)update.getRemovedObject());
                  }

                  if (original.getQueues() == null || original.getQueues().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("Quotas")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addQuota((QuotaBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeQuota((QuotaBean)update.getRemovedObject());
                  }

                  if (original.getQuotas() == null || original.getQuotas().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("SAFErrorHandlings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSAFErrorHandling((SAFErrorHandlingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSAFErrorHandling((SAFErrorHandlingBean)update.getRemovedObject());
                  }

                  if (original.getSAFErrorHandlings() == null || original.getSAFErrorHandlings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("SAFImportedDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSAFImportedDestination((SAFImportedDestinationsBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSAFImportedDestination((SAFImportedDestinationsBean)update.getRemovedObject());
                  }

                  if (original.getSAFImportedDestinations() == null || original.getSAFImportedDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("SAFRemoteContexts")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSAFRemoteContext((SAFRemoteContextBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSAFRemoteContext((SAFRemoteContextBean)update.getRemovedObject());
                  }

                  if (original.getSAFRemoteContexts() == null || original.getSAFRemoteContexts().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("Templates")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTemplate((TemplateBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTemplate((TemplateBean)update.getRemovedObject());
                  }

                  if (original.getTemplates() == null || original.getTemplates().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Topics")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTopic((TopicBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTopic((TopicBean)update.getRemovedObject());
                  }

                  if (original.getTopics() == null || original.getTopics().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("UniformDistributedQueues")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addUniformDistributedQueue((UniformDistributedQueueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeUniformDistributedQueue((UniformDistributedQueueBean)update.getRemovedObject());
                  }

                  if (original.getUniformDistributedQueues() == null || original.getUniformDistributedQueues().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("UniformDistributedTopics")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addUniformDistributedTopic((UniformDistributedTopicBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeUniformDistributedTopic((UniformDistributedTopicBean)update.getRemovedObject());
                  }

                  if (original.getUniformDistributedTopics() == null || original.getUniformDistributedTopics().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            JMSBeanImpl copy = (JMSBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(5)) {
               JMSConnectionFactoryBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               JMSConnectionFactoryBean[] newConnectionFactories = new JMSConnectionFactoryBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (JMSConnectionFactoryBean)((JMSConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationKeys")) && this.bean.isDestinationKeysSet() && !copy._isSet(4)) {
               DestinationKeyBean[] oldDestinationKeys = this.bean.getDestinationKeys();
               DestinationKeyBean[] newDestinationKeys = new DestinationKeyBean[oldDestinationKeys.length];

               for(i = 0; i < newDestinationKeys.length; ++i) {
                  newDestinationKeys[i] = (DestinationKeyBean)((DestinationKeyBean)this.createCopy((AbstractDescriptorBean)oldDestinationKeys[i], includeObsolete));
               }

               copy.setDestinationKeys(newDestinationKeys);
            }

            if ((excludeProps == null || !excludeProps.contains("DistributedQueues")) && this.bean.isDistributedQueuesSet() && !copy._isSet(9)) {
               DistributedQueueBean[] oldDistributedQueues = this.bean.getDistributedQueues();
               DistributedQueueBean[] newDistributedQueues = new DistributedQueueBean[oldDistributedQueues.length];

               for(i = 0; i < newDistributedQueues.length; ++i) {
                  newDistributedQueues[i] = (DistributedQueueBean)((DistributedQueueBean)this.createCopy((AbstractDescriptorBean)oldDistributedQueues[i], includeObsolete));
               }

               copy.setDistributedQueues(newDistributedQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("DistributedTopics")) && this.bean.isDistributedTopicsSet() && !copy._isSet(10)) {
               DistributedTopicBean[] oldDistributedTopics = this.bean.getDistributedTopics();
               DistributedTopicBean[] newDistributedTopics = new DistributedTopicBean[oldDistributedTopics.length];

               for(i = 0; i < newDistributedTopics.length; ++i) {
                  newDistributedTopics[i] = (DistributedTopicBean)((DistributedTopicBean)this.createCopy((AbstractDescriptorBean)oldDistributedTopics[i], includeObsolete));
               }

               copy.setDistributedTopics(newDistributedTopics);
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignServers")) && this.bean.isForeignServersSet() && !copy._isSet(6)) {
               ForeignServerBean[] oldForeignServers = this.bean.getForeignServers();
               ForeignServerBean[] newForeignServers = new ForeignServerBean[oldForeignServers.length];

               for(i = 0; i < newForeignServers.length; ++i) {
                  newForeignServers[i] = (ForeignServerBean)((ForeignServerBean)this.createCopy((AbstractDescriptorBean)oldForeignServers[i], includeObsolete));
               }

               copy.setForeignServers(newForeignServers);
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if ((excludeProps == null || !excludeProps.contains("Queues")) && this.bean.isQueuesSet() && !copy._isSet(7)) {
               QueueBean[] oldQueues = this.bean.getQueues();
               QueueBean[] newQueues = new QueueBean[oldQueues.length];

               for(i = 0; i < newQueues.length; ++i) {
                  newQueues[i] = (QueueBean)((QueueBean)this.createCopy((AbstractDescriptorBean)oldQueues[i], includeObsolete));
               }

               copy.setQueues(newQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("Quotas")) && this.bean.isQuotasSet() && !copy._isSet(2)) {
               QuotaBean[] oldQuotas = this.bean.getQuotas();
               QuotaBean[] newQuotas = new QuotaBean[oldQuotas.length];

               for(i = 0; i < newQuotas.length; ++i) {
                  newQuotas[i] = (QuotaBean)((QuotaBean)this.createCopy((AbstractDescriptorBean)oldQuotas[i], includeObsolete));
               }

               copy.setQuotas(newQuotas);
            }

            if ((excludeProps == null || !excludeProps.contains("SAFErrorHandlings")) && this.bean.isSAFErrorHandlingsSet() && !copy._isSet(15)) {
               SAFErrorHandlingBean[] oldSAFErrorHandlings = this.bean.getSAFErrorHandlings();
               SAFErrorHandlingBean[] newSAFErrorHandlings = new SAFErrorHandlingBean[oldSAFErrorHandlings.length];

               for(i = 0; i < newSAFErrorHandlings.length; ++i) {
                  newSAFErrorHandlings[i] = (SAFErrorHandlingBean)((SAFErrorHandlingBean)this.createCopy((AbstractDescriptorBean)oldSAFErrorHandlings[i], includeObsolete));
               }

               copy.setSAFErrorHandlings(newSAFErrorHandlings);
            }

            if ((excludeProps == null || !excludeProps.contains("SAFImportedDestinations")) && this.bean.isSAFImportedDestinationsSet() && !copy._isSet(13)) {
               SAFImportedDestinationsBean[] oldSAFImportedDestinations = this.bean.getSAFImportedDestinations();
               SAFImportedDestinationsBean[] newSAFImportedDestinations = new SAFImportedDestinationsBean[oldSAFImportedDestinations.length];

               for(i = 0; i < newSAFImportedDestinations.length; ++i) {
                  newSAFImportedDestinations[i] = (SAFImportedDestinationsBean)((SAFImportedDestinationsBean)this.createCopy((AbstractDescriptorBean)oldSAFImportedDestinations[i], includeObsolete));
               }

               copy.setSAFImportedDestinations(newSAFImportedDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("SAFRemoteContexts")) && this.bean.isSAFRemoteContextsSet() && !copy._isSet(14)) {
               SAFRemoteContextBean[] oldSAFRemoteContexts = this.bean.getSAFRemoteContexts();
               SAFRemoteContextBean[] newSAFRemoteContexts = new SAFRemoteContextBean[oldSAFRemoteContexts.length];

               for(i = 0; i < newSAFRemoteContexts.length; ++i) {
                  newSAFRemoteContexts[i] = (SAFRemoteContextBean)((SAFRemoteContextBean)this.createCopy((AbstractDescriptorBean)oldSAFRemoteContexts[i], includeObsolete));
               }

               copy.setSAFRemoteContexts(newSAFRemoteContexts);
            }

            if ((excludeProps == null || !excludeProps.contains("Templates")) && this.bean.isTemplatesSet() && !copy._isSet(3)) {
               TemplateBean[] oldTemplates = this.bean.getTemplates();
               TemplateBean[] newTemplates = new TemplateBean[oldTemplates.length];

               for(i = 0; i < newTemplates.length; ++i) {
                  newTemplates[i] = (TemplateBean)((TemplateBean)this.createCopy((AbstractDescriptorBean)oldTemplates[i], includeObsolete));
               }

               copy.setTemplates(newTemplates);
            }

            if ((excludeProps == null || !excludeProps.contains("Topics")) && this.bean.isTopicsSet() && !copy._isSet(8)) {
               TopicBean[] oldTopics = this.bean.getTopics();
               TopicBean[] newTopics = new TopicBean[oldTopics.length];

               for(i = 0; i < newTopics.length; ++i) {
                  newTopics[i] = (TopicBean)((TopicBean)this.createCopy((AbstractDescriptorBean)oldTopics[i], includeObsolete));
               }

               copy.setTopics(newTopics);
            }

            if ((excludeProps == null || !excludeProps.contains("UniformDistributedQueues")) && this.bean.isUniformDistributedQueuesSet() && !copy._isSet(11)) {
               UniformDistributedQueueBean[] oldUniformDistributedQueues = this.bean.getUniformDistributedQueues();
               UniformDistributedQueueBean[] newUniformDistributedQueues = new UniformDistributedQueueBean[oldUniformDistributedQueues.length];

               for(i = 0; i < newUniformDistributedQueues.length; ++i) {
                  newUniformDistributedQueues[i] = (UniformDistributedQueueBean)((UniformDistributedQueueBean)this.createCopy((AbstractDescriptorBean)oldUniformDistributedQueues[i], includeObsolete));
               }

               copy.setUniformDistributedQueues(newUniformDistributedQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("UniformDistributedTopics")) && this.bean.isUniformDistributedTopicsSet() && !copy._isSet(12)) {
               UniformDistributedTopicBean[] oldUniformDistributedTopics = this.bean.getUniformDistributedTopics();
               UniformDistributedTopicBean[] newUniformDistributedTopics = new UniformDistributedTopicBean[oldUniformDistributedTopics.length];

               for(i = 0; i < newUniformDistributedTopics.length; ++i) {
                  newUniformDistributedTopics[i] = (UniformDistributedTopicBean)((UniformDistributedTopicBean)this.createCopy((AbstractDescriptorBean)oldUniformDistributedTopics[i], includeObsolete));
               }

               copy.setUniformDistributedTopics(newUniformDistributedTopics);
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
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
         this.inferSubTree(this.bean.getConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getDestinationKeys(), clazz, annotation);
         this.inferSubTree(this.bean.getDistributedQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getDistributedTopics(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignServers(), clazz, annotation);
         this.inferSubTree(this.bean.getQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getQuotas(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFErrorHandlings(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFImportedDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFRemoteContexts(), clazz, annotation);
         this.inferSubTree(this.bean.getTemplates(), clazz, annotation);
         this.inferSubTree(this.bean.getTopics(), clazz, annotation);
         this.inferSubTree(this.bean.getUniformDistributedQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getUniformDistributedTopics(), clazz, annotation);
      }
   }
}
