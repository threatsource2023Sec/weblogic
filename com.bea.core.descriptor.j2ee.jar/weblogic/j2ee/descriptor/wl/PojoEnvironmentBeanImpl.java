package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.AdministeredObjectBeanImpl;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBeanImpl;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.DataSourceBeanImpl;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbLocalRefBeanImpl;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EjbRefBeanImpl;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.EnvEntryBeanImpl;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBeanImpl;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.JmsDestinationBeanImpl;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBeanImpl;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MailSessionBeanImpl;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBeanImpl;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBeanImpl;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBeanImpl;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBeanImpl;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ResourceRefBeanImpl;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PojoEnvironmentBeanImpl extends AbstractDescriptorBean implements PojoEnvironmentBean, Serializable {
   private AdministeredObjectBean[] _AdministeredObjects;
   private ConnectionFactoryResourceBean[] _ConnectionFactories;
   private DataSourceBean[] _DataSources;
   private EjbLocalRefBean[] _EjbLocalRefs;
   private EjbRefBean[] _EjbRefs;
   private EnvEntryBean[] _EnvEntries;
   private JmsConnectionFactoryBean[] _JmsConnectionFactories;
   private JmsDestinationBean[] _JmsDestinations;
   private MailSessionBean[] _MailSessions;
   private MessageDestinationRefBean[] _MessageDestinationRefs;
   private PersistenceContextRefBean[] _PersistenceContextRefs;
   private PersistenceUnitRefBean[] _PersistenceUnitRefs;
   private LifecycleCallbackBean[] _PostConstructs;
   private LifecycleCallbackBean[] _PreDestroys;
   private ResourceEnvRefBean[] _ResourceEnvRefs;
   private ResourceRefBean[] _ResourceRefs;
   private ServiceRefBean[] _ServiceRefs;
   private static SchemaHelper2 _schemaHelper;

   public PojoEnvironmentBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public PojoEnvironmentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public PojoEnvironmentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addEjbLocalRef(EjbLocalRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         EjbLocalRefBean[] _new;
         if (this._isSet(0)) {
            _new = (EjbLocalRefBean[])((EjbLocalRefBean[])this._getHelper()._extendArray(this.getEjbLocalRefs(), EjbLocalRefBean.class, param0));
         } else {
            _new = new EjbLocalRefBean[]{param0};
         }

         try {
            this.setEjbLocalRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbLocalRefBean[] getEjbLocalRefs() {
      return this._EjbLocalRefs;
   }

   public boolean isEjbLocalRefsInherited() {
      return false;
   }

   public boolean isEjbLocalRefsSet() {
      return this._isSet(0);
   }

   public void removeEjbLocalRef(EjbLocalRefBean param0) {
      this.destroyEjbLocalRef(param0);
   }

   public void setEjbLocalRefs(EjbLocalRefBean[] param0) throws InvalidAttributeValueException {
      EjbLocalRefBean[] param0 = param0 == null ? new EjbLocalRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbLocalRefBean[] _oldVal = this._EjbLocalRefs;
      this._EjbLocalRefs = (EjbLocalRefBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public EjbLocalRefBean createEjbLocalRef() {
      EjbLocalRefBeanImpl _val = new EjbLocalRefBeanImpl(this, -1);

      try {
         this.addEjbLocalRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbLocalRef(EjbLocalRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         EjbLocalRefBean[] _old = this.getEjbLocalRefs();
         EjbLocalRefBean[] _new = (EjbLocalRefBean[])((EjbLocalRefBean[])this._getHelper()._removeElement(_old, EjbLocalRefBean.class, param0));
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
               this.setEjbLocalRefs(_new);
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

   public void addPersistenceContextRef(PersistenceContextRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         PersistenceContextRefBean[] _new;
         if (this._isSet(1)) {
            _new = (PersistenceContextRefBean[])((PersistenceContextRefBean[])this._getHelper()._extendArray(this.getPersistenceContextRefs(), PersistenceContextRefBean.class, param0));
         } else {
            _new = new PersistenceContextRefBean[]{param0};
         }

         try {
            this.setPersistenceContextRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PersistenceContextRefBean[] getPersistenceContextRefs() {
      return this._PersistenceContextRefs;
   }

   public boolean isPersistenceContextRefsInherited() {
      return false;
   }

   public boolean isPersistenceContextRefsSet() {
      return this._isSet(1);
   }

   public void removePersistenceContextRef(PersistenceContextRefBean param0) {
      this.destroyPersistenceContextRef(param0);
   }

   public void setPersistenceContextRefs(PersistenceContextRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceContextRefBean[] param0 = param0 == null ? new PersistenceContextRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceContextRefBean[] _oldVal = this._PersistenceContextRefs;
      this._PersistenceContextRefs = (PersistenceContextRefBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public PersistenceContextRefBean createPersistenceContextRef() {
      PersistenceContextRefBeanImpl _val = new PersistenceContextRefBeanImpl(this, -1);

      try {
         this.addPersistenceContextRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceContextRef(PersistenceContextRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         PersistenceContextRefBean[] _old = this.getPersistenceContextRefs();
         PersistenceContextRefBean[] _new = (PersistenceContextRefBean[])((PersistenceContextRefBean[])this._getHelper()._removeElement(_old, PersistenceContextRefBean.class, param0));
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
               this.setPersistenceContextRefs(_new);
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

   public void addEnvEntry(EnvEntryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         EnvEntryBean[] _new;
         if (this._isSet(2)) {
            _new = (EnvEntryBean[])((EnvEntryBean[])this._getHelper()._extendArray(this.getEnvEntries(), EnvEntryBean.class, param0));
         } else {
            _new = new EnvEntryBean[]{param0};
         }

         try {
            this.setEnvEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EnvEntryBean[] getEnvEntries() {
      return this._EnvEntries;
   }

   public boolean isEnvEntriesInherited() {
      return false;
   }

   public boolean isEnvEntriesSet() {
      return this._isSet(2);
   }

   public void removeEnvEntry(EnvEntryBean param0) {
      this.destroyEnvEntry(param0);
   }

   public void setEnvEntries(EnvEntryBean[] param0) throws InvalidAttributeValueException {
      EnvEntryBean[] param0 = param0 == null ? new EnvEntryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EnvEntryBean[] _oldVal = this._EnvEntries;
      this._EnvEntries = (EnvEntryBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public EnvEntryBean createEnvEntry() {
      EnvEntryBeanImpl _val = new EnvEntryBeanImpl(this, -1);

      try {
         this.addEnvEntry(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEnvEntry(EnvEntryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         EnvEntryBean[] _old = this.getEnvEntries();
         EnvEntryBean[] _new = (EnvEntryBean[])((EnvEntryBean[])this._getHelper()._removeElement(_old, EnvEntryBean.class, param0));
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
               this.setEnvEntries(_new);
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

   public void addEjbRef(EjbRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         EjbRefBean[] _new;
         if (this._isSet(3)) {
            _new = (EjbRefBean[])((EjbRefBean[])this._getHelper()._extendArray(this.getEjbRefs(), EjbRefBean.class, param0));
         } else {
            _new = new EjbRefBean[]{param0};
         }

         try {
            this.setEjbRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbRefBean[] getEjbRefs() {
      return this._EjbRefs;
   }

   public boolean isEjbRefsInherited() {
      return false;
   }

   public boolean isEjbRefsSet() {
      return this._isSet(3);
   }

   public void removeEjbRef(EjbRefBean param0) {
      this.destroyEjbRef(param0);
   }

   public void setEjbRefs(EjbRefBean[] param0) throws InvalidAttributeValueException {
      EjbRefBean[] param0 = param0 == null ? new EjbRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbRefBean[] _oldVal = this._EjbRefs;
      this._EjbRefs = (EjbRefBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public EjbRefBean createEjbRef() {
      EjbRefBeanImpl _val = new EjbRefBeanImpl(this, -1);

      try {
         this.addEjbRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbRef(EjbRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         EjbRefBean[] _old = this.getEjbRefs();
         EjbRefBean[] _new = (EjbRefBean[])((EjbRefBean[])this._getHelper()._removeElement(_old, EjbRefBean.class, param0));
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
               this.setEjbRefs(_new);
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

   public void addServiceRef(ServiceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ServiceRefBean[] _new;
         if (this._isSet(4)) {
            _new = (ServiceRefBean[])((ServiceRefBean[])this._getHelper()._extendArray(this.getServiceRefs(), ServiceRefBean.class, param0));
         } else {
            _new = new ServiceRefBean[]{param0};
         }

         try {
            this.setServiceRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceRefBean[] getServiceRefs() {
      return this._ServiceRefs;
   }

   public boolean isServiceRefsInherited() {
      return false;
   }

   public boolean isServiceRefsSet() {
      return this._isSet(4);
   }

   public void removeServiceRef(ServiceRefBean param0) {
      this.destroyServiceRef(param0);
   }

   public void setServiceRefs(ServiceRefBean[] param0) throws InvalidAttributeValueException {
      ServiceRefBean[] param0 = param0 == null ? new ServiceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceRefBean[] _oldVal = this._ServiceRefs;
      this._ServiceRefs = (ServiceRefBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ServiceRefBean createServiceRef() {
      ServiceRefBeanImpl _val = new ServiceRefBeanImpl(this, -1);

      try {
         this.addServiceRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceRef(ServiceRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ServiceRefBean[] _old = this.getServiceRefs();
         ServiceRefBean[] _new = (ServiceRefBean[])((ServiceRefBean[])this._getHelper()._removeElement(_old, ServiceRefBean.class, param0));
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
               this.setServiceRefs(_new);
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

   public void addResourceRef(ResourceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         ResourceRefBean[] _new;
         if (this._isSet(5)) {
            _new = (ResourceRefBean[])((ResourceRefBean[])this._getHelper()._extendArray(this.getResourceRefs(), ResourceRefBean.class, param0));
         } else {
            _new = new ResourceRefBean[]{param0};
         }

         try {
            this.setResourceRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceRefBean[] getResourceRefs() {
      return this._ResourceRefs;
   }

   public boolean isResourceRefsInherited() {
      return false;
   }

   public boolean isResourceRefsSet() {
      return this._isSet(5);
   }

   public void removeResourceRef(ResourceRefBean param0) {
      this.destroyResourceRef(param0);
   }

   public void setResourceRefs(ResourceRefBean[] param0) throws InvalidAttributeValueException {
      ResourceRefBean[] param0 = param0 == null ? new ResourceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceRefBean[] _oldVal = this._ResourceRefs;
      this._ResourceRefs = (ResourceRefBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public ResourceRefBean createResourceRef() {
      ResourceRefBeanImpl _val = new ResourceRefBeanImpl(this, -1);

      try {
         this.addResourceRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceRef(ResourceRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         ResourceRefBean[] _old = this.getResourceRefs();
         ResourceRefBean[] _new = (ResourceRefBean[])((ResourceRefBean[])this._getHelper()._removeElement(_old, ResourceRefBean.class, param0));
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
               this.setResourceRefs(_new);
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

   public void addResourceEnvRef(ResourceEnvRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         ResourceEnvRefBean[] _new;
         if (this._isSet(6)) {
            _new = (ResourceEnvRefBean[])((ResourceEnvRefBean[])this._getHelper()._extendArray(this.getResourceEnvRefs(), ResourceEnvRefBean.class, param0));
         } else {
            _new = new ResourceEnvRefBean[]{param0};
         }

         try {
            this.setResourceEnvRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceEnvRefBean[] getResourceEnvRefs() {
      return this._ResourceEnvRefs;
   }

   public boolean isResourceEnvRefsInherited() {
      return false;
   }

   public boolean isResourceEnvRefsSet() {
      return this._isSet(6);
   }

   public void removeResourceEnvRef(ResourceEnvRefBean param0) {
      this.destroyResourceEnvRef(param0);
   }

   public void setResourceEnvRefs(ResourceEnvRefBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvRefBean[] param0 = param0 == null ? new ResourceEnvRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvRefBean[] _oldVal = this._ResourceEnvRefs;
      this._ResourceEnvRefs = (ResourceEnvRefBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public ResourceEnvRefBean createResourceEnvRef() {
      ResourceEnvRefBeanImpl _val = new ResourceEnvRefBeanImpl(this, -1);

      try {
         this.addResourceEnvRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceEnvRef(ResourceEnvRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         ResourceEnvRefBean[] _old = this.getResourceEnvRefs();
         ResourceEnvRefBean[] _new = (ResourceEnvRefBean[])((ResourceEnvRefBean[])this._getHelper()._removeElement(_old, ResourceEnvRefBean.class, param0));
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
               this.setResourceEnvRefs(_new);
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

   public void addMessageDestinationRef(MessageDestinationRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         MessageDestinationRefBean[] _new;
         if (this._isSet(7)) {
            _new = (MessageDestinationRefBean[])((MessageDestinationRefBean[])this._getHelper()._extendArray(this.getMessageDestinationRefs(), MessageDestinationRefBean.class, param0));
         } else {
            _new = new MessageDestinationRefBean[]{param0};
         }

         try {
            this.setMessageDestinationRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDestinationRefBean[] getMessageDestinationRefs() {
      return this._MessageDestinationRefs;
   }

   public boolean isMessageDestinationRefsInherited() {
      return false;
   }

   public boolean isMessageDestinationRefsSet() {
      return this._isSet(7);
   }

   public void removeMessageDestinationRef(MessageDestinationRefBean param0) {
      this.destroyMessageDestinationRef(param0);
   }

   public void setMessageDestinationRefs(MessageDestinationRefBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationRefBean[] param0 = param0 == null ? new MessageDestinationRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationRefBean[] _oldVal = this._MessageDestinationRefs;
      this._MessageDestinationRefs = (MessageDestinationRefBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public MessageDestinationRefBean createMessageDestinationRef() {
      MessageDestinationRefBeanImpl _val = new MessageDestinationRefBeanImpl(this, -1);

      try {
         this.addMessageDestinationRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMessageDestinationRef(MessageDestinationRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         MessageDestinationRefBean[] _old = this.getMessageDestinationRefs();
         MessageDestinationRefBean[] _new = (MessageDestinationRefBean[])((MessageDestinationRefBean[])this._getHelper()._removeElement(_old, MessageDestinationRefBean.class, param0));
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
               this.setMessageDestinationRefs(_new);
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

   public void addPersistenceUnitRef(PersistenceUnitRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         PersistenceUnitRefBean[] _new;
         if (this._isSet(8)) {
            _new = (PersistenceUnitRefBean[])((PersistenceUnitRefBean[])this._getHelper()._extendArray(this.getPersistenceUnitRefs(), PersistenceUnitRefBean.class, param0));
         } else {
            _new = new PersistenceUnitRefBean[]{param0};
         }

         try {
            this.setPersistenceUnitRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
      return this._PersistenceUnitRefs;
   }

   public boolean isPersistenceUnitRefsInherited() {
      return false;
   }

   public boolean isPersistenceUnitRefsSet() {
      return this._isSet(8);
   }

   public void removePersistenceUnitRef(PersistenceUnitRefBean param0) {
      this.destroyPersistenceUnitRef(param0);
   }

   public void setPersistenceUnitRefs(PersistenceUnitRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceUnitRefBean[] param0 = param0 == null ? new PersistenceUnitRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceUnitRefBean[] _oldVal = this._PersistenceUnitRefs;
      this._PersistenceUnitRefs = (PersistenceUnitRefBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public PersistenceUnitRefBean createPersistenceUnitRef() {
      PersistenceUnitRefBeanImpl _val = new PersistenceUnitRefBeanImpl(this, -1);

      try {
         this.addPersistenceUnitRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceUnitRef(PersistenceUnitRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         PersistenceUnitRefBean[] _old = this.getPersistenceUnitRefs();
         PersistenceUnitRefBean[] _new = (PersistenceUnitRefBean[])((PersistenceUnitRefBean[])this._getHelper()._removeElement(_old, PersistenceUnitRefBean.class, param0));
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
               this.setPersistenceUnitRefs(_new);
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

   public void addPostConstruct(LifecycleCallbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(9)) {
            _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._extendArray(this.getPostConstructs(), LifecycleCallbackBean.class, param0));
         } else {
            _new = new LifecycleCallbackBean[]{param0};
         }

         try {
            this.setPostConstructs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleCallbackBean[] getPostConstructs() {
      return this._PostConstructs;
   }

   public boolean isPostConstructsInherited() {
      return false;
   }

   public boolean isPostConstructsSet() {
      return this._isSet(9);
   }

   public void removePostConstruct(LifecycleCallbackBean param0) {
      this.destroyPostConstruct(param0);
   }

   public void setPostConstructs(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PostConstructs;
      this._PostConstructs = (LifecycleCallbackBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public LifecycleCallbackBean createPostConstruct() {
      LifecycleCallbackBeanImpl _val = new LifecycleCallbackBeanImpl(this, -1);

      try {
         this.addPostConstruct(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPostConstruct(LifecycleCallbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         LifecycleCallbackBean[] _old = this.getPostConstructs();
         LifecycleCallbackBean[] _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._removeElement(_old, LifecycleCallbackBean.class, param0));
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
               this.setPostConstructs(_new);
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

   public void addPreDestroy(LifecycleCallbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(10)) {
            _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._extendArray(this.getPreDestroys(), LifecycleCallbackBean.class, param0));
         } else {
            _new = new LifecycleCallbackBean[]{param0};
         }

         try {
            this.setPreDestroys(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleCallbackBean[] getPreDestroys() {
      return this._PreDestroys;
   }

   public boolean isPreDestroysInherited() {
      return false;
   }

   public boolean isPreDestroysSet() {
      return this._isSet(10);
   }

   public void removePreDestroy(LifecycleCallbackBean param0) {
      this.destroyPreDestroy(param0);
   }

   public void setPreDestroys(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PreDestroys;
      this._PreDestroys = (LifecycleCallbackBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public LifecycleCallbackBean createPreDestroy() {
      LifecycleCallbackBeanImpl _val = new LifecycleCallbackBeanImpl(this, -1);

      try {
         this.addPreDestroy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPreDestroy(LifecycleCallbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         LifecycleCallbackBean[] _old = this.getPreDestroys();
         LifecycleCallbackBean[] _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._removeElement(_old, LifecycleCallbackBean.class, param0));
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
               this.setPreDestroys(_new);
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

   public void addDataSource(DataSourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         DataSourceBean[] _new;
         if (this._isSet(11)) {
            _new = (DataSourceBean[])((DataSourceBean[])this._getHelper()._extendArray(this.getDataSources(), DataSourceBean.class, param0));
         } else {
            _new = new DataSourceBean[]{param0};
         }

         try {
            this.setDataSources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DataSourceBean[] getDataSources() {
      return this._DataSources;
   }

   public boolean isDataSourcesInherited() {
      return false;
   }

   public boolean isDataSourcesSet() {
      return this._isSet(11);
   }

   public void removeDataSource(DataSourceBean param0) {
      this.destroyDataSource(param0);
   }

   public void setDataSources(DataSourceBean[] param0) throws InvalidAttributeValueException {
      DataSourceBean[] param0 = param0 == null ? new DataSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DataSourceBean[] _oldVal = this._DataSources;
      this._DataSources = (DataSourceBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public DataSourceBean createDataSource() {
      DataSourceBeanImpl _val = new DataSourceBeanImpl(this, -1);

      try {
         this.addDataSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDataSource(DataSourceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         DataSourceBean[] _old = this.getDataSources();
         DataSourceBean[] _new = (DataSourceBean[])((DataSourceBean[])this._getHelper()._removeElement(_old, DataSourceBean.class, param0));
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
               this.setDataSources(_new);
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

   public void addJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         JmsConnectionFactoryBean[] _new;
         if (this._isSet(12)) {
            _new = (JmsConnectionFactoryBean[])((JmsConnectionFactoryBean[])this._getHelper()._extendArray(this.getJmsConnectionFactories(), JmsConnectionFactoryBean.class, param0));
         } else {
            _new = new JmsConnectionFactoryBean[]{param0};
         }

         try {
            this.setJmsConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JmsConnectionFactoryBean[] getJmsConnectionFactories() {
      return this._JmsConnectionFactories;
   }

   public boolean isJmsConnectionFactoriesInherited() {
      return false;
   }

   public boolean isJmsConnectionFactoriesSet() {
      return this._isSet(12);
   }

   public void removeJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this.destroyJmsConnectionFactory(param0);
   }

   public void setJmsConnectionFactories(JmsConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JmsConnectionFactoryBean[] param0 = param0 == null ? new JmsConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsConnectionFactoryBean[] _oldVal = this._JmsConnectionFactories;
      this._JmsConnectionFactories = (JmsConnectionFactoryBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public JmsConnectionFactoryBean createJmsConnectionFactory() {
      JmsConnectionFactoryBeanImpl _val = new JmsConnectionFactoryBeanImpl(this, -1);

      try {
         this.addJmsConnectionFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         JmsConnectionFactoryBean[] _old = this.getJmsConnectionFactories();
         JmsConnectionFactoryBean[] _new = (JmsConnectionFactoryBean[])((JmsConnectionFactoryBean[])this._getHelper()._removeElement(_old, JmsConnectionFactoryBean.class, param0));
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
               this.setJmsConnectionFactories(_new);
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

   public void addJmsDestination(JmsDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         JmsDestinationBean[] _new;
         if (this._isSet(13)) {
            _new = (JmsDestinationBean[])((JmsDestinationBean[])this._getHelper()._extendArray(this.getJmsDestinations(), JmsDestinationBean.class, param0));
         } else {
            _new = new JmsDestinationBean[]{param0};
         }

         try {
            this.setJmsDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JmsDestinationBean[] getJmsDestinations() {
      return this._JmsDestinations;
   }

   public boolean isJmsDestinationsInherited() {
      return false;
   }

   public boolean isJmsDestinationsSet() {
      return this._isSet(13);
   }

   public void removeJmsDestination(JmsDestinationBean param0) {
      this.destroyJmsDestination(param0);
   }

   public void setJmsDestinations(JmsDestinationBean[] param0) throws InvalidAttributeValueException {
      JmsDestinationBean[] param0 = param0 == null ? new JmsDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsDestinationBean[] _oldVal = this._JmsDestinations;
      this._JmsDestinations = (JmsDestinationBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public JmsDestinationBean createJmsDestination() {
      JmsDestinationBeanImpl _val = new JmsDestinationBeanImpl(this, -1);

      try {
         this.addJmsDestination(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJmsDestination(JmsDestinationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         JmsDestinationBean[] _old = this.getJmsDestinations();
         JmsDestinationBean[] _new = (JmsDestinationBean[])((JmsDestinationBean[])this._getHelper()._removeElement(_old, JmsDestinationBean.class, param0));
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
               this.setJmsDestinations(_new);
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

   public void addMailSession(MailSessionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         MailSessionBean[] _new;
         if (this._isSet(14)) {
            _new = (MailSessionBean[])((MailSessionBean[])this._getHelper()._extendArray(this.getMailSessions(), MailSessionBean.class, param0));
         } else {
            _new = new MailSessionBean[]{param0};
         }

         try {
            this.setMailSessions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MailSessionBean[] getMailSessions() {
      return this._MailSessions;
   }

   public boolean isMailSessionsInherited() {
      return false;
   }

   public boolean isMailSessionsSet() {
      return this._isSet(14);
   }

   public void removeMailSession(MailSessionBean param0) {
      this.destroyMailSession(param0);
   }

   public void setMailSessions(MailSessionBean[] param0) throws InvalidAttributeValueException {
      MailSessionBean[] param0 = param0 == null ? new MailSessionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MailSessionBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public MailSessionBean createMailSession() {
      MailSessionBeanImpl _val = new MailSessionBeanImpl(this, -1);

      try {
         this.addMailSession(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMailSession(MailSessionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         MailSessionBean[] _old = this.getMailSessions();
         MailSessionBean[] _new = (MailSessionBean[])((MailSessionBean[])this._getHelper()._removeElement(_old, MailSessionBean.class, param0));
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
               this.setMailSessions(_new);
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

   public void addConnectionFactory(ConnectionFactoryResourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         ConnectionFactoryResourceBean[] _new;
         if (this._isSet(15)) {
            _new = (ConnectionFactoryResourceBean[])((ConnectionFactoryResourceBean[])this._getHelper()._extendArray(this.getConnectionFactories(), ConnectionFactoryResourceBean.class, param0));
         } else {
            _new = new ConnectionFactoryResourceBean[]{param0};
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

   public ConnectionFactoryResourceBean[] getConnectionFactories() {
      return this._ConnectionFactories;
   }

   public boolean isConnectionFactoriesInherited() {
      return false;
   }

   public boolean isConnectionFactoriesSet() {
      return this._isSet(15);
   }

   public void removeConnectionFactory(ConnectionFactoryResourceBean param0) {
      this.destroyConnectionFactory(param0);
   }

   public void setConnectionFactories(ConnectionFactoryResourceBean[] param0) throws InvalidAttributeValueException {
      ConnectionFactoryResourceBean[] param0 = param0 == null ? new ConnectionFactoryResourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionFactoryResourceBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (ConnectionFactoryResourceBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public ConnectionFactoryResourceBean createConnectionFactoryResourceBean() {
      ConnectionFactoryResourceBeanImpl _val = new ConnectionFactoryResourceBeanImpl(this, -1);

      try {
         this.addConnectionFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionFactory(ConnectionFactoryResourceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         ConnectionFactoryResourceBean[] _old = this.getConnectionFactories();
         ConnectionFactoryResourceBean[] _new = (ConnectionFactoryResourceBean[])((ConnectionFactoryResourceBean[])this._getHelper()._removeElement(_old, ConnectionFactoryResourceBean.class, param0));
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

   public void addAdministeredObject(AdministeredObjectBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         AdministeredObjectBean[] _new;
         if (this._isSet(16)) {
            _new = (AdministeredObjectBean[])((AdministeredObjectBean[])this._getHelper()._extendArray(this.getAdministeredObjects(), AdministeredObjectBean.class, param0));
         } else {
            _new = new AdministeredObjectBean[]{param0};
         }

         try {
            this.setAdministeredObjects(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AdministeredObjectBean[] getAdministeredObjects() {
      return this._AdministeredObjects;
   }

   public boolean isAdministeredObjectsInherited() {
      return false;
   }

   public boolean isAdministeredObjectsSet() {
      return this._isSet(16);
   }

   public void removeAdministeredObject(AdministeredObjectBean param0) {
      this.destroyAdministeredObject(param0);
   }

   public void setAdministeredObjects(AdministeredObjectBean[] param0) throws InvalidAttributeValueException {
      AdministeredObjectBean[] param0 = param0 == null ? new AdministeredObjectBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AdministeredObjectBean[] _oldVal = this._AdministeredObjects;
      this._AdministeredObjects = (AdministeredObjectBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public AdministeredObjectBean createAdministeredObjectBean() {
      AdministeredObjectBeanImpl _val = new AdministeredObjectBeanImpl(this, -1);

      try {
         this.addAdministeredObject(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAdministeredObject(AdministeredObjectBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         AdministeredObjectBean[] _old = this.getAdministeredObjects();
         AdministeredObjectBean[] _new = (AdministeredObjectBean[])((AdministeredObjectBean[])this._getHelper()._removeElement(_old, AdministeredObjectBean.class, param0));
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
               this.setAdministeredObjects(_new);
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._AdministeredObjects = new AdministeredObjectBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._ConnectionFactories = new ConnectionFactoryResourceBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._DataSources = new DataSourceBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._EjbLocalRefs = new EjbLocalRefBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._EjbRefs = new EjbRefBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._EnvEntries = new EnvEntryBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._JmsConnectionFactories = new JmsConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._JmsDestinations = new JmsDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._MailSessions = new MailSessionBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._MessageDestinationRefs = new MessageDestinationRefBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._PersistenceContextRefs = new PersistenceContextRefBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._PersistenceUnitRefs = new PersistenceUnitRefBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._PostConstructs = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._PreDestroys = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._ResourceEnvRefs = new ResourceEnvRefBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._ResourceRefs = new ResourceRefBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._ServiceRefs = new ServiceRefBean[0];
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("ejb-ref")) {
                  return 3;
               }
            case 8:
            case 10:
            case 17:
            case 21:
            default:
               break;
            case 9:
               if (s.equals("env-entry")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 11;
               }

               if (s.equals("pre-destroy")) {
                  return 10;
               }

               if (s.equals("service-ref")) {
                  return 4;
               }
               break;
            case 12:
               if (s.equals("mail-session")) {
                  return 14;
               }

               if (s.equals("resource-ref")) {
                  return 5;
               }
               break;
            case 13:
               if (s.equals("ejb-local-ref")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("post-construct")) {
                  return 9;
               }
               break;
            case 15:
               if (s.equals("jms-destination")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("resource-env-ref")) {
                  return 6;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 15;
               }
               break;
            case 19:
               if (s.equals("administered-object")) {
                  return 16;
               }
               break;
            case 20:
               if (s.equals("persistence-unit-ref")) {
                  return 8;
               }
               break;
            case 22:
               if (s.equals("jms-connection-factory")) {
                  return 12;
               }
               break;
            case 23:
               if (s.equals("message-destination-ref")) {
                  return 7;
               }

               if (s.equals("persistence-context-ref")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new EjbLocalRefBeanImpl.SchemaHelper2();
            case 1:
               return new PersistenceContextRefBeanImpl.SchemaHelper2();
            case 2:
               return new EnvEntryBeanImpl.SchemaHelper2();
            case 3:
               return new EjbRefBeanImpl.SchemaHelper2();
            case 4:
               return new ServiceRefBeanImpl.SchemaHelper2();
            case 5:
               return new ResourceRefBeanImpl.SchemaHelper2();
            case 6:
               return new ResourceEnvRefBeanImpl.SchemaHelper2();
            case 7:
               return new MessageDestinationRefBeanImpl.SchemaHelper2();
            case 8:
               return new PersistenceUnitRefBeanImpl.SchemaHelper2();
            case 9:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 10:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 11:
               return new DataSourceBeanImpl.SchemaHelper2();
            case 12:
               return new JmsConnectionFactoryBeanImpl.SchemaHelper2();
            case 13:
               return new JmsDestinationBeanImpl.SchemaHelper2();
            case 14:
               return new MailSessionBeanImpl.SchemaHelper2();
            case 15:
               return new ConnectionFactoryResourceBeanImpl.SchemaHelper2();
            case 16:
               return new AdministeredObjectBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "pojo-environment";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ejb-local-ref";
            case 1:
               return "persistence-context-ref";
            case 2:
               return "env-entry";
            case 3:
               return "ejb-ref";
            case 4:
               return "service-ref";
            case 5:
               return "resource-ref";
            case 6:
               return "resource-env-ref";
            case 7:
               return "message-destination-ref";
            case 8:
               return "persistence-unit-ref";
            case 9:
               return "post-construct";
            case 10:
               return "pre-destroy";
            case 11:
               return "data-source";
            case 12:
               return "jms-connection-factory";
            case 13:
               return "jms-destination";
            case 14:
               return "mail-session";
            case 15:
               return "connection-factory";
            case 16:
               return "administered-object";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
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
            case 16:
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
            case 16:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PojoEnvironmentBeanImpl bean;

      protected Helper(PojoEnvironmentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EjbLocalRefs";
            case 1:
               return "PersistenceContextRefs";
            case 2:
               return "EnvEntries";
            case 3:
               return "EjbRefs";
            case 4:
               return "ServiceRefs";
            case 5:
               return "ResourceRefs";
            case 6:
               return "ResourceEnvRefs";
            case 7:
               return "MessageDestinationRefs";
            case 8:
               return "PersistenceUnitRefs";
            case 9:
               return "PostConstructs";
            case 10:
               return "PreDestroys";
            case 11:
               return "DataSources";
            case 12:
               return "JmsConnectionFactories";
            case 13:
               return "JmsDestinations";
            case 14:
               return "MailSessions";
            case 15:
               return "ConnectionFactories";
            case 16:
               return "AdministeredObjects";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdministeredObjects")) {
            return 16;
         } else if (propName.equals("ConnectionFactories")) {
            return 15;
         } else if (propName.equals("DataSources")) {
            return 11;
         } else if (propName.equals("EjbLocalRefs")) {
            return 0;
         } else if (propName.equals("EjbRefs")) {
            return 3;
         } else if (propName.equals("EnvEntries")) {
            return 2;
         } else if (propName.equals("JmsConnectionFactories")) {
            return 12;
         } else if (propName.equals("JmsDestinations")) {
            return 13;
         } else if (propName.equals("MailSessions")) {
            return 14;
         } else if (propName.equals("MessageDestinationRefs")) {
            return 7;
         } else if (propName.equals("PersistenceContextRefs")) {
            return 1;
         } else if (propName.equals("PersistenceUnitRefs")) {
            return 8;
         } else if (propName.equals("PostConstructs")) {
            return 9;
         } else if (propName.equals("PreDestroys")) {
            return 10;
         } else if (propName.equals("ResourceEnvRefs")) {
            return 6;
         } else if (propName.equals("ResourceRefs")) {
            return 5;
         } else {
            return propName.equals("ServiceRefs") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAdministeredObjects()));
         iterators.add(new ArrayIterator(this.bean.getConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getDataSources()));
         iterators.add(new ArrayIterator(this.bean.getEjbLocalRefs()));
         iterators.add(new ArrayIterator(this.bean.getEjbRefs()));
         iterators.add(new ArrayIterator(this.bean.getEnvEntries()));
         iterators.add(new ArrayIterator(this.bean.getJmsConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getJmsDestinations()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceContextRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitRefs()));
         iterators.add(new ArrayIterator(this.bean.getPostConstructs()));
         iterators.add(new ArrayIterator(this.bean.getPreDestroys()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceRefs()));
         iterators.add(new ArrayIterator(this.bean.getServiceRefs()));
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
            for(i = 0; i < this.bean.getAdministeredObjects().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAdministeredObjects()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDataSources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDataSources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbLocalRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbLocalRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEnvEntries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEnvEntries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJmsConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJmsConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJmsDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJmsDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinationRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinationRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPersistenceContextRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPersistenceContextRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPersistenceUnitRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPersistenceUnitRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPostConstructs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPostConstructs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPreDestroys().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPreDestroys()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceEnvRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceEnvRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceRefs()[i]);
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
            PojoEnvironmentBeanImpl otherTyped = (PojoEnvironmentBeanImpl)other;
            this.computeChildDiff("AdministeredObjects", this.bean.getAdministeredObjects(), otherTyped.getAdministeredObjects(), false);
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), false);
            this.computeChildDiff("DataSources", this.bean.getDataSources(), otherTyped.getDataSources(), false);
            this.computeChildDiff("EjbLocalRefs", this.bean.getEjbLocalRefs(), otherTyped.getEjbLocalRefs(), false);
            this.computeChildDiff("EjbRefs", this.bean.getEjbRefs(), otherTyped.getEjbRefs(), false);
            this.computeChildDiff("EnvEntries", this.bean.getEnvEntries(), otherTyped.getEnvEntries(), false);
            this.computeChildDiff("JmsConnectionFactories", this.bean.getJmsConnectionFactories(), otherTyped.getJmsConnectionFactories(), false);
            this.computeChildDiff("JmsDestinations", this.bean.getJmsDestinations(), otherTyped.getJmsDestinations(), false);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), false);
            this.computeChildDiff("MessageDestinationRefs", this.bean.getMessageDestinationRefs(), otherTyped.getMessageDestinationRefs(), false);
            this.computeChildDiff("PersistenceContextRefs", this.bean.getPersistenceContextRefs(), otherTyped.getPersistenceContextRefs(), false);
            this.computeChildDiff("PersistenceUnitRefs", this.bean.getPersistenceUnitRefs(), otherTyped.getPersistenceUnitRefs(), false);
            this.computeChildDiff("PostConstructs", this.bean.getPostConstructs(), otherTyped.getPostConstructs(), false);
            this.computeChildDiff("PreDestroys", this.bean.getPreDestroys(), otherTyped.getPreDestroys(), false);
            this.computeChildDiff("ResourceEnvRefs", this.bean.getResourceEnvRefs(), otherTyped.getResourceEnvRefs(), false);
            this.computeChildDiff("ResourceRefs", this.bean.getResourceRefs(), otherTyped.getResourceRefs(), false);
            this.computeChildDiff("ServiceRefs", this.bean.getServiceRefs(), otherTyped.getServiceRefs(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PojoEnvironmentBeanImpl original = (PojoEnvironmentBeanImpl)event.getSourceBean();
            PojoEnvironmentBeanImpl proposed = (PojoEnvironmentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdministeredObjects")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAdministeredObject((AdministeredObjectBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAdministeredObject((AdministeredObjectBean)update.getRemovedObject());
                  }

                  if (original.getAdministeredObjects() == null || original.getAdministeredObjects().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
               } else if (prop.equals("ConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConnectionFactory((ConnectionFactoryResourceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConnectionFactory((ConnectionFactoryResourceBean)update.getRemovedObject());
                  }

                  if (original.getConnectionFactories() == null || original.getConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("DataSources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDataSource((DataSourceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDataSource((DataSourceBean)update.getRemovedObject());
                  }

                  if (original.getDataSources() == null || original.getDataSources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("EjbLocalRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbLocalRef((EjbLocalRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbLocalRef((EjbLocalRefBean)update.getRemovedObject());
                  }

                  if (original.getEjbLocalRefs() == null || original.getEjbLocalRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("EjbRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbRef((EjbRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbRef((EjbRefBean)update.getRemovedObject());
                  }

                  if (original.getEjbRefs() == null || original.getEjbRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("EnvEntries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEnvEntry((EnvEntryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEnvEntry((EnvEntryBean)update.getRemovedObject());
                  }

                  if (original.getEnvEntries() == null || original.getEnvEntries().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("JmsConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJmsConnectionFactory((JmsConnectionFactoryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJmsConnectionFactory((JmsConnectionFactoryBean)update.getRemovedObject());
                  }

                  if (original.getJmsConnectionFactories() == null || original.getJmsConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("JmsDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJmsDestination((JmsDestinationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJmsDestination((JmsDestinationBean)update.getRemovedObject());
                  }

                  if (original.getJmsDestinations() == null || original.getJmsDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("MailSessions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMailSession((MailSessionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMailSession((MailSessionBean)update.getRemovedObject());
                  }

                  if (original.getMailSessions() == null || original.getMailSessions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("MessageDestinationRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDestinationRef((MessageDestinationRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDestinationRef((MessageDestinationRefBean)update.getRemovedObject());
                  }

                  if (original.getMessageDestinationRefs() == null || original.getMessageDestinationRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("PersistenceContextRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPersistenceContextRef((PersistenceContextRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePersistenceContextRef((PersistenceContextRefBean)update.getRemovedObject());
                  }

                  if (original.getPersistenceContextRefs() == null || original.getPersistenceContextRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("PersistenceUnitRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPersistenceUnitRef((PersistenceUnitRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePersistenceUnitRef((PersistenceUnitRefBean)update.getRemovedObject());
                  }

                  if (original.getPersistenceUnitRefs() == null || original.getPersistenceUnitRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("PostConstructs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPostConstruct((LifecycleCallbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePostConstruct((LifecycleCallbackBean)update.getRemovedObject());
                  }

                  if (original.getPostConstructs() == null || original.getPostConstructs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("PreDestroys")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPreDestroy((LifecycleCallbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePreDestroy((LifecycleCallbackBean)update.getRemovedObject());
                  }

                  if (original.getPreDestroys() == null || original.getPreDestroys().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("ResourceEnvRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceEnvRef((ResourceEnvRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceEnvRef((ResourceEnvRefBean)update.getRemovedObject());
                  }

                  if (original.getResourceEnvRefs() == null || original.getResourceEnvRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("ResourceRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceRef((ResourceRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceRef((ResourceRefBean)update.getRemovedObject());
                  }

                  if (original.getResourceRefs() == null || original.getResourceRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("ServiceRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceRef((ServiceRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceRef((ServiceRefBean)update.getRemovedObject());
                  }

                  if (original.getServiceRefs() == null || original.getServiceRefs().length == 0) {
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
            PojoEnvironmentBeanImpl copy = (PojoEnvironmentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AdministeredObjects")) && this.bean.isAdministeredObjectsSet() && !copy._isSet(16)) {
               AdministeredObjectBean[] oldAdministeredObjects = this.bean.getAdministeredObjects();
               AdministeredObjectBean[] newAdministeredObjects = new AdministeredObjectBean[oldAdministeredObjects.length];

               for(i = 0; i < newAdministeredObjects.length; ++i) {
                  newAdministeredObjects[i] = (AdministeredObjectBean)((AdministeredObjectBean)this.createCopy((AbstractDescriptorBean)oldAdministeredObjects[i], includeObsolete));
               }

               copy.setAdministeredObjects(newAdministeredObjects);
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(15)) {
               ConnectionFactoryResourceBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               ConnectionFactoryResourceBean[] newConnectionFactories = new ConnectionFactoryResourceBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (ConnectionFactoryResourceBean)((ConnectionFactoryResourceBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSources")) && this.bean.isDataSourcesSet() && !copy._isSet(11)) {
               DataSourceBean[] oldDataSources = this.bean.getDataSources();
               DataSourceBean[] newDataSources = new DataSourceBean[oldDataSources.length];

               for(i = 0; i < newDataSources.length; ++i) {
                  newDataSources[i] = (DataSourceBean)((DataSourceBean)this.createCopy((AbstractDescriptorBean)oldDataSources[i], includeObsolete));
               }

               copy.setDataSources(newDataSources);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbLocalRefs")) && this.bean.isEjbLocalRefsSet() && !copy._isSet(0)) {
               EjbLocalRefBean[] oldEjbLocalRefs = this.bean.getEjbLocalRefs();
               EjbLocalRefBean[] newEjbLocalRefs = new EjbLocalRefBean[oldEjbLocalRefs.length];

               for(i = 0; i < newEjbLocalRefs.length; ++i) {
                  newEjbLocalRefs[i] = (EjbLocalRefBean)((EjbLocalRefBean)this.createCopy((AbstractDescriptorBean)oldEjbLocalRefs[i], includeObsolete));
               }

               copy.setEjbLocalRefs(newEjbLocalRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRefs")) && this.bean.isEjbRefsSet() && !copy._isSet(3)) {
               EjbRefBean[] oldEjbRefs = this.bean.getEjbRefs();
               EjbRefBean[] newEjbRefs = new EjbRefBean[oldEjbRefs.length];

               for(i = 0; i < newEjbRefs.length; ++i) {
                  newEjbRefs[i] = (EjbRefBean)((EjbRefBean)this.createCopy((AbstractDescriptorBean)oldEjbRefs[i], includeObsolete));
               }

               copy.setEjbRefs(newEjbRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntries")) && this.bean.isEnvEntriesSet() && !copy._isSet(2)) {
               EnvEntryBean[] oldEnvEntries = this.bean.getEnvEntries();
               EnvEntryBean[] newEnvEntries = new EnvEntryBean[oldEnvEntries.length];

               for(i = 0; i < newEnvEntries.length; ++i) {
                  newEnvEntries[i] = (EnvEntryBean)((EnvEntryBean)this.createCopy((AbstractDescriptorBean)oldEnvEntries[i], includeObsolete));
               }

               copy.setEnvEntries(newEnvEntries);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsConnectionFactories")) && this.bean.isJmsConnectionFactoriesSet() && !copy._isSet(12)) {
               JmsConnectionFactoryBean[] oldJmsConnectionFactories = this.bean.getJmsConnectionFactories();
               JmsConnectionFactoryBean[] newJmsConnectionFactories = new JmsConnectionFactoryBean[oldJmsConnectionFactories.length];

               for(i = 0; i < newJmsConnectionFactories.length; ++i) {
                  newJmsConnectionFactories[i] = (JmsConnectionFactoryBean)((JmsConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldJmsConnectionFactories[i], includeObsolete));
               }

               copy.setJmsConnectionFactories(newJmsConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsDestinations")) && this.bean.isJmsDestinationsSet() && !copy._isSet(13)) {
               JmsDestinationBean[] oldJmsDestinations = this.bean.getJmsDestinations();
               JmsDestinationBean[] newJmsDestinations = new JmsDestinationBean[oldJmsDestinations.length];

               for(i = 0; i < newJmsDestinations.length; ++i) {
                  newJmsDestinations[i] = (JmsDestinationBean)((JmsDestinationBean)this.createCopy((AbstractDescriptorBean)oldJmsDestinations[i], includeObsolete));
               }

               copy.setJmsDestinations(newJmsDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(14)) {
               MailSessionBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionBean[] newMailSessions = new MailSessionBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionBean)((MailSessionBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationRefs")) && this.bean.isMessageDestinationRefsSet() && !copy._isSet(7)) {
               MessageDestinationRefBean[] oldMessageDestinationRefs = this.bean.getMessageDestinationRefs();
               MessageDestinationRefBean[] newMessageDestinationRefs = new MessageDestinationRefBean[oldMessageDestinationRefs.length];

               for(i = 0; i < newMessageDestinationRefs.length; ++i) {
                  newMessageDestinationRefs[i] = (MessageDestinationRefBean)((MessageDestinationRefBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationRefs[i], includeObsolete));
               }

               copy.setMessageDestinationRefs(newMessageDestinationRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextRefs")) && this.bean.isPersistenceContextRefsSet() && !copy._isSet(1)) {
               PersistenceContextRefBean[] oldPersistenceContextRefs = this.bean.getPersistenceContextRefs();
               PersistenceContextRefBean[] newPersistenceContextRefs = new PersistenceContextRefBean[oldPersistenceContextRefs.length];

               for(i = 0; i < newPersistenceContextRefs.length; ++i) {
                  newPersistenceContextRefs[i] = (PersistenceContextRefBean)((PersistenceContextRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceContextRefs[i], includeObsolete));
               }

               copy.setPersistenceContextRefs(newPersistenceContextRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitRefs")) && this.bean.isPersistenceUnitRefsSet() && !copy._isSet(8)) {
               PersistenceUnitRefBean[] oldPersistenceUnitRefs = this.bean.getPersistenceUnitRefs();
               PersistenceUnitRefBean[] newPersistenceUnitRefs = new PersistenceUnitRefBean[oldPersistenceUnitRefs.length];

               for(i = 0; i < newPersistenceUnitRefs.length; ++i) {
                  newPersistenceUnitRefs[i] = (PersistenceUnitRefBean)((PersistenceUnitRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceUnitRefs[i], includeObsolete));
               }

               copy.setPersistenceUnitRefs(newPersistenceUnitRefs);
            }

            LifecycleCallbackBean[] oldPreDestroys;
            LifecycleCallbackBean[] newPreDestroys;
            if ((excludeProps == null || !excludeProps.contains("PostConstructs")) && this.bean.isPostConstructsSet() && !copy._isSet(9)) {
               oldPreDestroys = this.bean.getPostConstructs();
               newPreDestroys = new LifecycleCallbackBean[oldPreDestroys.length];

               for(i = 0; i < newPreDestroys.length; ++i) {
                  newPreDestroys[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPreDestroys[i], includeObsolete));
               }

               copy.setPostConstructs(newPreDestroys);
            }

            if ((excludeProps == null || !excludeProps.contains("PreDestroys")) && this.bean.isPreDestroysSet() && !copy._isSet(10)) {
               oldPreDestroys = this.bean.getPreDestroys();
               newPreDestroys = new LifecycleCallbackBean[oldPreDestroys.length];

               for(i = 0; i < newPreDestroys.length; ++i) {
                  newPreDestroys[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPreDestroys[i], includeObsolete));
               }

               copy.setPreDestroys(newPreDestroys);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvRefs")) && this.bean.isResourceEnvRefsSet() && !copy._isSet(6)) {
               ResourceEnvRefBean[] oldResourceEnvRefs = this.bean.getResourceEnvRefs();
               ResourceEnvRefBean[] newResourceEnvRefs = new ResourceEnvRefBean[oldResourceEnvRefs.length];

               for(i = 0; i < newResourceEnvRefs.length; ++i) {
                  newResourceEnvRefs[i] = (ResourceEnvRefBean)((ResourceEnvRefBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvRefs[i], includeObsolete));
               }

               copy.setResourceEnvRefs(newResourceEnvRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceRefs")) && this.bean.isResourceRefsSet() && !copy._isSet(5)) {
               ResourceRefBean[] oldResourceRefs = this.bean.getResourceRefs();
               ResourceRefBean[] newResourceRefs = new ResourceRefBean[oldResourceRefs.length];

               for(i = 0; i < newResourceRefs.length; ++i) {
                  newResourceRefs[i] = (ResourceRefBean)((ResourceRefBean)this.createCopy((AbstractDescriptorBean)oldResourceRefs[i], includeObsolete));
               }

               copy.setResourceRefs(newResourceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefs")) && this.bean.isServiceRefsSet() && !copy._isSet(4)) {
               ServiceRefBean[] oldServiceRefs = this.bean.getServiceRefs();
               ServiceRefBean[] newServiceRefs = new ServiceRefBean[oldServiceRefs.length];

               for(i = 0; i < newServiceRefs.length; ++i) {
                  newServiceRefs[i] = (ServiceRefBean)((ServiceRefBean)this.createCopy((AbstractDescriptorBean)oldServiceRefs[i], includeObsolete));
               }

               copy.setServiceRefs(newServiceRefs);
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
         this.inferSubTree(this.bean.getAdministeredObjects(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSources(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbLocalRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEnvEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getMailSessions(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceContextRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceUnitRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPostConstructs(), clazz, annotation);
         this.inferSubTree(this.bean.getPreDestroys(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceRefs(), clazz, annotation);
      }
   }
}
