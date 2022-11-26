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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ClientSAFBeanImpl extends SettableBeanImpl implements ClientSAFBean, Serializable {
   private JMSConnectionFactoryBean[] _ConnectionFactories;
   private DefaultPersistentStoreBean _PersistentStore;
   private DefaultSAFAgentBean _SAFAgent;
   private SAFErrorHandlingBean[] _SAFErrorHandlings;
   private SAFImportedDestinationsBean[] _SAFImportedDestinations;
   private SAFRemoteContextBean[] _SAFRemoteContexts;
   private static SchemaHelper2 _schemaHelper;

   public ClientSAFBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ClientSAFBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ClientSAFBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public DefaultPersistentStoreBean getPersistentStore() {
      return this._PersistentStore;
   }

   public boolean isPersistentStoreInherited() {
      return false;
   }

   public boolean isPersistentStoreSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getPersistentStore());
   }

   public void setPersistentStore(DefaultPersistentStoreBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      DefaultPersistentStoreBean _oldVal = this._PersistentStore;
      this._PersistentStore = param0;
      this._postSet(0, _oldVal, param0);
   }

   public DefaultSAFAgentBean getSAFAgent() {
      return this._SAFAgent;
   }

   public boolean isSAFAgentInherited() {
      return false;
   }

   public boolean isSAFAgentSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getSAFAgent());
   }

   public void setSAFAgent(DefaultSAFAgentBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      DefaultSAFAgentBean _oldVal = this._SAFAgent;
      this._SAFAgent = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addConnectionFactory(JMSConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         JMSConnectionFactoryBean[] _new;
         if (this._isSet(2)) {
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
      return this._isSet(2);
   }

   public void removeConnectionFactory(JMSConnectionFactoryBean param0) {
      this.destroyConnectionFactory(param0);
   }

   public void setConnectionFactories(JMSConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JMSConnectionFactoryBean[] param0 = param0 == null ? new JMSConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMSConnectionFactoryBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (JMSConnectionFactoryBean[])param0;
      this._postSet(2, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 2);
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

   public void addSAFImportedDestination(SAFImportedDestinationsBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         SAFImportedDestinationsBean[] _new;
         if (this._isSet(3)) {
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
      return this._isSet(3);
   }

   public void removeSAFImportedDestination(SAFImportedDestinationsBean param0) {
      this.destroySAFImportedDestinations(param0);
   }

   public void setSAFImportedDestinations(SAFImportedDestinationsBean[] param0) throws InvalidAttributeValueException {
      SAFImportedDestinationsBean[] param0 = param0 == null ? new SAFImportedDestinationsBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SAFImportedDestinationsBean[] _oldVal = this._SAFImportedDestinations;
      this._SAFImportedDestinations = (SAFImportedDestinationsBean[])param0;
      this._postSet(3, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 3);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         SAFRemoteContextBean[] _new;
         if (this._isSet(4)) {
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
      return this._isSet(4);
   }

   public void removeSAFRemoteContext(SAFRemoteContextBean param0) {
      this.destroySAFRemoteContext(param0);
   }

   public void setSAFRemoteContexts(SAFRemoteContextBean[] param0) throws InvalidAttributeValueException {
      SAFRemoteContextBean[] param0 = param0 == null ? new SAFRemoteContextBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFRemoteContextBean[] _oldVal = this._SAFRemoteContexts;
      this._SAFRemoteContexts = (SAFRemoteContextBean[])param0;
      this._postSet(4, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 4);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         SAFErrorHandlingBean[] _new;
         if (this._isSet(5)) {
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
      return this._isSet(5);
   }

   public void removeSAFErrorHandling(SAFErrorHandlingBean param0) {
      this.destroySAFErrorHandling(param0);
   }

   public void setSAFErrorHandlings(SAFErrorHandlingBean[] param0) throws InvalidAttributeValueException {
      SAFErrorHandlingBean[] param0 = param0 == null ? new SAFErrorHandlingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFErrorHandlingBean[] _oldVal = this._SAFErrorHandlings;
      this._SAFErrorHandlings = (SAFErrorHandlingBean[])param0;
      this._postSet(5, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 5);
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
      return super._isAnythingSet() || this.isPersistentStoreSet() || this.isSAFAgentSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ConnectionFactories = new JMSConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._PersistentStore = new DefaultPersistentStoreBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._PersistentStore);
               if (initOne) {
                  break;
               }
            case 1:
               this._SAFAgent = new DefaultSAFAgentBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._SAFAgent);
               if (initOne) {
                  break;
               }
            case 5:
               this._SAFErrorHandlings = new SAFErrorHandlingBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._SAFImportedDestinations = new SAFImportedDestinationsBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._SAFRemoteContexts = new SAFRemoteContextBean[0];
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
            case 9:
               if (s.equals("saf-agent")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("persistent-store")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 2;
               }

               if (s.equals("saf-error-handling")) {
                  return 5;
               }

               if (s.equals("saf-remote-context")) {
                  return 4;
               }
               break;
            case 25:
               if (s.equals("saf-imported-destinations")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new DefaultPersistentStoreBeanImpl.SchemaHelper2();
            case 1:
               return new DefaultSAFAgentBeanImpl.SchemaHelper2();
            case 2:
               return new JMSConnectionFactoryBeanImpl.SchemaHelper2();
            case 3:
               return new SAFImportedDestinationsBeanImpl.SchemaHelper2();
            case 4:
               return new SAFRemoteContextBeanImpl.SchemaHelper2();
            case 5:
               return new SAFErrorHandlingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-client-jms";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "persistent-store";
            case 1:
               return "saf-agent";
            case 2:
               return "connection-factory";
            case 3:
               return "saf-imported-destinations";
            case 4:
               return "saf-remote-context";
            case 5:
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
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private ClientSAFBeanImpl bean;

      protected Helper(ClientSAFBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PersistentStore";
            case 1:
               return "SAFAgent";
            case 2:
               return "ConnectionFactories";
            case 3:
               return "SAFImportedDestinations";
            case 4:
               return "SAFRemoteContexts";
            case 5:
               return "SAFErrorHandlings";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactories")) {
            return 2;
         } else if (propName.equals("PersistentStore")) {
            return 0;
         } else if (propName.equals("SAFAgent")) {
            return 1;
         } else if (propName.equals("SAFErrorHandlings")) {
            return 5;
         } else if (propName.equals("SAFImportedDestinations")) {
            return 3;
         } else {
            return propName.equals("SAFRemoteContexts") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConnectionFactories()));
         if (this.bean.getPersistentStore() != null) {
            iterators.add(new ArrayIterator(new DefaultPersistentStoreBean[]{this.bean.getPersistentStore()}));
         }

         if (this.bean.getSAFAgent() != null) {
            iterators.add(new ArrayIterator(new DefaultSAFAgentBean[]{this.bean.getSAFAgent()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSAFErrorHandlings()));
         iterators.add(new ArrayIterator(this.bean.getSAFImportedDestinations()));
         iterators.add(new ArrayIterator(this.bean.getSAFRemoteContexts()));
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

            childValue = this.computeChildHashValue(this.bean.getPersistentStore());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSAFAgent());
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

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            ClientSAFBeanImpl otherTyped = (ClientSAFBeanImpl)other;
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), true);
            this.computeSubDiff("PersistentStore", this.bean.getPersistentStore(), otherTyped.getPersistentStore());
            this.computeSubDiff("SAFAgent", this.bean.getSAFAgent(), otherTyped.getSAFAgent());
            this.computeChildDiff("SAFErrorHandlings", this.bean.getSAFErrorHandlings(), otherTyped.getSAFErrorHandlings(), true);
            this.computeChildDiff("SAFImportedDestinations", this.bean.getSAFImportedDestinations(), otherTyped.getSAFImportedDestinations(), true);
            this.computeChildDiff("SAFRemoteContexts", this.bean.getSAFRemoteContexts(), otherTyped.getSAFRemoteContexts(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClientSAFBeanImpl original = (ClientSAFBeanImpl)event.getSourceBean();
            ClientSAFBeanImpl proposed = (ClientSAFBeanImpl)event.getProposedBean();
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
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("PersistentStore")) {
                  if (type == 2) {
                     original.setPersistentStore((DefaultPersistentStoreBean)this.createCopy((AbstractDescriptorBean)proposed.getPersistentStore()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PersistentStore", (DescriptorBean)original.getPersistentStore());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SAFAgent")) {
                  if (type == 2) {
                     original.setSAFAgent((DefaultSAFAgentBean)this.createCopy((AbstractDescriptorBean)proposed.getSAFAgent()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SAFAgent", (DescriptorBean)original.getSAFAgent());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
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
            ClientSAFBeanImpl copy = (ClientSAFBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(2)) {
               JMSConnectionFactoryBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               JMSConnectionFactoryBean[] newConnectionFactories = new JMSConnectionFactoryBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (JMSConnectionFactoryBean)((JMSConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStore")) && this.bean.isPersistentStoreSet() && !copy._isSet(0)) {
               Object o = this.bean.getPersistentStore();
               copy.setPersistentStore((DefaultPersistentStoreBean)null);
               copy.setPersistentStore(o == null ? null : (DefaultPersistentStoreBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SAFAgent")) && this.bean.isSAFAgentSet() && !copy._isSet(1)) {
               Object o = this.bean.getSAFAgent();
               copy.setSAFAgent((DefaultSAFAgentBean)null);
               copy.setSAFAgent(o == null ? null : (DefaultSAFAgentBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SAFErrorHandlings")) && this.bean.isSAFErrorHandlingsSet() && !copy._isSet(5)) {
               SAFErrorHandlingBean[] oldSAFErrorHandlings = this.bean.getSAFErrorHandlings();
               SAFErrorHandlingBean[] newSAFErrorHandlings = new SAFErrorHandlingBean[oldSAFErrorHandlings.length];

               for(i = 0; i < newSAFErrorHandlings.length; ++i) {
                  newSAFErrorHandlings[i] = (SAFErrorHandlingBean)((SAFErrorHandlingBean)this.createCopy((AbstractDescriptorBean)oldSAFErrorHandlings[i], includeObsolete));
               }

               copy.setSAFErrorHandlings(newSAFErrorHandlings);
            }

            if ((excludeProps == null || !excludeProps.contains("SAFImportedDestinations")) && this.bean.isSAFImportedDestinationsSet() && !copy._isSet(3)) {
               SAFImportedDestinationsBean[] oldSAFImportedDestinations = this.bean.getSAFImportedDestinations();
               SAFImportedDestinationsBean[] newSAFImportedDestinations = new SAFImportedDestinationsBean[oldSAFImportedDestinations.length];

               for(i = 0; i < newSAFImportedDestinations.length; ++i) {
                  newSAFImportedDestinations[i] = (SAFImportedDestinationsBean)((SAFImportedDestinationsBean)this.createCopy((AbstractDescriptorBean)oldSAFImportedDestinations[i], includeObsolete));
               }

               copy.setSAFImportedDestinations(newSAFImportedDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("SAFRemoteContexts")) && this.bean.isSAFRemoteContextsSet() && !copy._isSet(4)) {
               SAFRemoteContextBean[] oldSAFRemoteContexts = this.bean.getSAFRemoteContexts();
               SAFRemoteContextBean[] newSAFRemoteContexts = new SAFRemoteContextBean[oldSAFRemoteContexts.length];

               for(i = 0; i < newSAFRemoteContexts.length; ++i) {
                  newSAFRemoteContexts[i] = (SAFRemoteContextBean)((SAFRemoteContextBean)this.createCopy((AbstractDescriptorBean)oldSAFRemoteContexts[i], includeObsolete));
               }

               copy.setSAFRemoteContexts(newSAFRemoteContexts);
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
         this.inferSubTree(this.bean.getPersistentStore(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFAgent(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFErrorHandlings(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFImportedDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFRemoteContexts(), clazz, annotation);
      }
   }
}
