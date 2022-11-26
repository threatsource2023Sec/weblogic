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
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicApplicationClientBeanImpl extends AbstractDescriptorBean implements WeblogicApplicationClientBean, Serializable {
   private CdiDescriptorBean _CdiDescriptor;
   private EjbReferenceDescriptionBean[] _EjbReferenceDescriptions;
   private String _Id;
   private MessageDestinationDescriptorBean[] _MessageDestinationDescriptors;
   private ResourceDescriptionBean[] _ResourceDescriptions;
   private ResourceEnvDescriptionBean[] _ResourceEnvDescriptions;
   private String _ServerApplicationName;
   private ServiceReferenceDescriptionBean[] _ServiceReferenceDescriptions;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicApplicationClientBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicApplicationClientBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicApplicationClientBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getServerApplicationName() {
      return this._ServerApplicationName;
   }

   public boolean isServerApplicationNameInherited() {
      return false;
   }

   public boolean isServerApplicationNameSet() {
      return this._isSet(0);
   }

   public void setServerApplicationName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServerApplicationName;
      this._ServerApplicationName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addResourceDescription(ResourceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         ResourceDescriptionBean[] _new;
         if (this._isSet(1)) {
            _new = (ResourceDescriptionBean[])((ResourceDescriptionBean[])this._getHelper()._extendArray(this.getResourceDescriptions(), ResourceDescriptionBean.class, param0));
         } else {
            _new = new ResourceDescriptionBean[]{param0};
         }

         try {
            this.setResourceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceDescriptionBean[] getResourceDescriptions() {
      return this._ResourceDescriptions;
   }

   public boolean isResourceDescriptionsInherited() {
      return false;
   }

   public boolean isResourceDescriptionsSet() {
      return this._isSet(1);
   }

   public void removeResourceDescription(ResourceDescriptionBean param0) {
      this.destroyResourceDescription(param0);
   }

   public void setResourceDescriptions(ResourceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceDescriptionBean[] param0 = param0 == null ? new ResourceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceDescriptionBean[] _oldVal = this._ResourceDescriptions;
      this._ResourceDescriptions = (ResourceDescriptionBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public ResourceDescriptionBean createResourceDescription() {
      ResourceDescriptionBeanImpl _val = new ResourceDescriptionBeanImpl(this, -1);

      try {
         this.addResourceDescription(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceDescription(ResourceDescriptionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         ResourceDescriptionBean[] _old = this.getResourceDescriptions();
         ResourceDescriptionBean[] _new = (ResourceDescriptionBean[])((ResourceDescriptionBean[])this._getHelper()._removeElement(_old, ResourceDescriptionBean.class, param0));
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
               this.setResourceDescriptions(_new);
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

   public void addResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ResourceEnvDescriptionBean[] _new;
         if (this._isSet(2)) {
            _new = (ResourceEnvDescriptionBean[])((ResourceEnvDescriptionBean[])this._getHelper()._extendArray(this.getResourceEnvDescriptions(), ResourceEnvDescriptionBean.class, param0));
         } else {
            _new = new ResourceEnvDescriptionBean[]{param0};
         }

         try {
            this.setResourceEnvDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceEnvDescriptionBean[] getResourceEnvDescriptions() {
      return this._ResourceEnvDescriptions;
   }

   public boolean isResourceEnvDescriptionsInherited() {
      return false;
   }

   public boolean isResourceEnvDescriptionsSet() {
      return this._isSet(2);
   }

   public void removeResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      this.destroyResourceEnvDescription(param0);
   }

   public void setResourceEnvDescriptions(ResourceEnvDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvDescriptionBean[] param0 = param0 == null ? new ResourceEnvDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvDescriptionBean[] _oldVal = this._ResourceEnvDescriptions;
      this._ResourceEnvDescriptions = (ResourceEnvDescriptionBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ResourceEnvDescriptionBean createResourceEnvDescription() {
      ResourceEnvDescriptionBeanImpl _val = new ResourceEnvDescriptionBeanImpl(this, -1);

      try {
         this.addResourceEnvDescription(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         ResourceEnvDescriptionBean[] _old = this.getResourceEnvDescriptions();
         ResourceEnvDescriptionBean[] _new = (ResourceEnvDescriptionBean[])((ResourceEnvDescriptionBean[])this._getHelper()._removeElement(_old, ResourceEnvDescriptionBean.class, param0));
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
               this.setResourceEnvDescriptions(_new);
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

   public void addEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         EjbReferenceDescriptionBean[] _new;
         if (this._isSet(3)) {
            _new = (EjbReferenceDescriptionBean[])((EjbReferenceDescriptionBean[])this._getHelper()._extendArray(this.getEjbReferenceDescriptions(), EjbReferenceDescriptionBean.class, param0));
         } else {
            _new = new EjbReferenceDescriptionBean[]{param0};
         }

         try {
            this.setEjbReferenceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbReferenceDescriptionBean[] getEjbReferenceDescriptions() {
      return this._EjbReferenceDescriptions;
   }

   public boolean isEjbReferenceDescriptionsInherited() {
      return false;
   }

   public boolean isEjbReferenceDescriptionsSet() {
      return this._isSet(3);
   }

   public void removeEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      this.destroyEjbReferenceDescription(param0);
   }

   public void setEjbReferenceDescriptions(EjbReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      EjbReferenceDescriptionBean[] param0 = param0 == null ? new EjbReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbReferenceDescriptionBean[] _oldVal = this._EjbReferenceDescriptions;
      this._EjbReferenceDescriptions = (EjbReferenceDescriptionBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public EjbReferenceDescriptionBean createEjbReferenceDescription() {
      EjbReferenceDescriptionBeanImpl _val = new EjbReferenceDescriptionBeanImpl(this, -1);

      try {
         this.addEjbReferenceDescription(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         EjbReferenceDescriptionBean[] _old = this.getEjbReferenceDescriptions();
         EjbReferenceDescriptionBean[] _new = (EjbReferenceDescriptionBean[])((EjbReferenceDescriptionBean[])this._getHelper()._removeElement(_old, EjbReferenceDescriptionBean.class, param0));
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
               this.setEjbReferenceDescriptions(_new);
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

   public void addServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ServiceReferenceDescriptionBean[] _new;
         if (this._isSet(4)) {
            _new = (ServiceReferenceDescriptionBean[])((ServiceReferenceDescriptionBean[])this._getHelper()._extendArray(this.getServiceReferenceDescriptions(), ServiceReferenceDescriptionBean.class, param0));
         } else {
            _new = new ServiceReferenceDescriptionBean[]{param0};
         }

         try {
            this.setServiceReferenceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions() {
      return this._ServiceReferenceDescriptions;
   }

   public boolean isServiceReferenceDescriptionsInherited() {
      return false;
   }

   public boolean isServiceReferenceDescriptionsSet() {
      return this._isSet(4);
   }

   public void removeServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      this.destroyServiceReferenceDescription(param0);
   }

   public void setServiceReferenceDescriptions(ServiceReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ServiceReferenceDescriptionBean[] param0 = param0 == null ? new ServiceReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceReferenceDescriptionBean[] _oldVal = this._ServiceReferenceDescriptions;
      this._ServiceReferenceDescriptions = (ServiceReferenceDescriptionBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ServiceReferenceDescriptionBean createServiceReferenceDescription() {
      ServiceReferenceDescriptionBeanImpl _val = new ServiceReferenceDescriptionBeanImpl(this, -1);

      try {
         this.addServiceReferenceDescription(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ServiceReferenceDescriptionBean[] _old = this.getServiceReferenceDescriptions();
         ServiceReferenceDescriptionBean[] _new = (ServiceReferenceDescriptionBean[])((ServiceReferenceDescriptionBean[])this._getHelper()._removeElement(_old, ServiceReferenceDescriptionBean.class, param0));
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
               this.setServiceReferenceDescriptions(_new);
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

   public void addMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         MessageDestinationDescriptorBean[] _new;
         if (this._isSet(5)) {
            _new = (MessageDestinationDescriptorBean[])((MessageDestinationDescriptorBean[])this._getHelper()._extendArray(this.getMessageDestinationDescriptors(), MessageDestinationDescriptorBean.class, param0));
         } else {
            _new = new MessageDestinationDescriptorBean[]{param0};
         }

         try {
            this.setMessageDestinationDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDestinationDescriptorBean[] getMessageDestinationDescriptors() {
      return this._MessageDestinationDescriptors;
   }

   public boolean isMessageDestinationDescriptorsInherited() {
      return false;
   }

   public boolean isMessageDestinationDescriptorsSet() {
      return this._isSet(5);
   }

   public void removeMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      this.destroyMessageDestinationDescriptor(param0);
   }

   public void setMessageDestinationDescriptors(MessageDestinationDescriptorBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationDescriptorBean[] param0 = param0 == null ? new MessageDestinationDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationDescriptorBean[] _oldVal = this._MessageDestinationDescriptors;
      this._MessageDestinationDescriptors = (MessageDestinationDescriptorBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public MessageDestinationDescriptorBean createMessageDestinationDescriptor() {
      MessageDestinationDescriptorBeanImpl _val = new MessageDestinationDescriptorBeanImpl(this, -1);

      try {
         this.addMessageDestinationDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         MessageDestinationDescriptorBean[] _old = this.getMessageDestinationDescriptors();
         MessageDestinationDescriptorBean[] _new = (MessageDestinationDescriptorBean[])((MessageDestinationDescriptorBean[])this._getHelper()._removeElement(_old, MessageDestinationDescriptorBean.class, param0));
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
               this.setMessageDestinationDescriptors(_new);
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

   public MessageDestinationDescriptorBean lookupMessageDestinationDescriptor(String param0) {
      Object[] aary = (Object[])this._MessageDestinationDescriptors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MessageDestinationDescriptorBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MessageDestinationDescriptorBeanImpl)it.previous();
      } while(!bean.getMessageDestinationName().equals(param0));

      return bean;
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(6);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(7);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(7, _oldVal, param0);
   }

   public CdiDescriptorBean getCdiDescriptor() {
      return this._CdiDescriptor;
   }

   public boolean isCdiDescriptorInherited() {
      return false;
   }

   public boolean isCdiDescriptorSet() {
      return this._isSet(8) || this._isAnythingSet((AbstractDescriptorBean)this.getCdiDescriptor());
   }

   public void setCdiDescriptor(CdiDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 8)) {
         this._postCreate(_child);
      }

      CdiDescriptorBean _oldVal = this._CdiDescriptor;
      this._CdiDescriptor = param0;
      this._postSet(8, _oldVal, param0);
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
      return super._isAnythingSet() || this.isCdiDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._CdiDescriptor = new CdiDescriptorBeanImpl(this, 8);
               this._postCreate((AbstractDescriptorBean)this._CdiDescriptor);
               if (initOne) {
                  break;
               }
            case 3:
               this._EjbReferenceDescriptions = new EjbReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._MessageDestinationDescriptors = new MessageDestinationDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._ResourceDescriptions = new ResourceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._ResourceEnvDescriptions = new ResourceEnvDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ServerApplicationName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._Version = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-application-client/1.3/weblogic-application-client.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-application-client";
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
            case 2:
               if (s.equals("id")) {
                  return 6;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 26:
            case 27:
            case 28:
            default:
               break;
            case 7:
               if (s.equals("version")) {
                  return 7;
               }
               break;
            case 14:
               if (s.equals("cdi-descriptor")) {
                  return 8;
               }
               break;
            case 20:
               if (s.equals("resource-description")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("server-application-name")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("resource-env-description")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("ejb-reference-description")) {
                  return 3;
               }
               break;
            case 29:
               if (s.equals("service-reference-description")) {
                  return 4;
               }
               break;
            case 30:
               if (s.equals("message-destination-descriptor")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ResourceDescriptionBeanImpl.SchemaHelper2();
            case 2:
               return new ResourceEnvDescriptionBeanImpl.SchemaHelper2();
            case 3:
               return new EjbReferenceDescriptionBeanImpl.SchemaHelper2();
            case 4:
               return new ServiceReferenceDescriptionBeanImpl.SchemaHelper2();
            case 5:
               return new MessageDestinationDescriptorBeanImpl.SchemaHelper2();
            case 6:
            case 7:
            default:
               return super.getSchemaHelper(propIndex);
            case 8:
               return new CdiDescriptorBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "weblogic-application-client";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "server-application-name";
            case 1:
               return "resource-description";
            case 2:
               return "resource-env-description";
            case 3:
               return "ejb-reference-description";
            case 4:
               return "service-reference-description";
            case 5:
               return "message-destination-descriptor";
            case 6:
               return "id";
            case 7:
               return "version";
            case 8:
               return "cdi-descriptor";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
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
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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
            case 7:
            default:
               return super.isBean(propIndex);
            case 8:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicApplicationClientBeanImpl bean;

      protected Helper(WeblogicApplicationClientBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServerApplicationName";
            case 1:
               return "ResourceDescriptions";
            case 2:
               return "ResourceEnvDescriptions";
            case 3:
               return "EjbReferenceDescriptions";
            case 4:
               return "ServiceReferenceDescriptions";
            case 5:
               return "MessageDestinationDescriptors";
            case 6:
               return "Id";
            case 7:
               return "Version";
            case 8:
               return "CdiDescriptor";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CdiDescriptor")) {
            return 8;
         } else if (propName.equals("EjbReferenceDescriptions")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("MessageDestinationDescriptors")) {
            return 5;
         } else if (propName.equals("ResourceDescriptions")) {
            return 1;
         } else if (propName.equals("ResourceEnvDescriptions")) {
            return 2;
         } else if (propName.equals("ServerApplicationName")) {
            return 0;
         } else if (propName.equals("ServiceReferenceDescriptions")) {
            return 4;
         } else {
            return propName.equals("Version") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCdiDescriptor() != null) {
            iterators.add(new ArrayIterator(new CdiDescriptorBean[]{this.bean.getCdiDescriptor()}));
         }

         iterators.add(new ArrayIterator(this.bean.getEjbReferenceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getResourceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getServiceReferenceDescriptions()));
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
            childValue = this.computeChildHashValue(this.bean.getCdiDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getEjbReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinationDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinationDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceEnvDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceEnvDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServerApplicationNameSet()) {
               buf.append("ServerApplicationName");
               buf.append(String.valueOf(this.bean.getServerApplicationName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceReferenceDescriptions()[i]);
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
            WeblogicApplicationClientBeanImpl otherTyped = (WeblogicApplicationClientBeanImpl)other;
            this.computeSubDiff("CdiDescriptor", this.bean.getCdiDescriptor(), otherTyped.getCdiDescriptor());
            this.computeChildDiff("EjbReferenceDescriptions", this.bean.getEjbReferenceDescriptions(), otherTyped.getEjbReferenceDescriptions(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("MessageDestinationDescriptors", this.bean.getMessageDestinationDescriptors(), otherTyped.getMessageDestinationDescriptors(), false);
            this.computeChildDiff("ResourceDescriptions", this.bean.getResourceDescriptions(), otherTyped.getResourceDescriptions(), false);
            this.computeChildDiff("ResourceEnvDescriptions", this.bean.getResourceEnvDescriptions(), otherTyped.getResourceEnvDescriptions(), false);
            this.computeDiff("ServerApplicationName", this.bean.getServerApplicationName(), otherTyped.getServerApplicationName(), false);
            this.computeChildDiff("ServiceReferenceDescriptions", this.bean.getServiceReferenceDescriptions(), otherTyped.getServiceReferenceDescriptions(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicApplicationClientBeanImpl original = (WeblogicApplicationClientBeanImpl)event.getSourceBean();
            WeblogicApplicationClientBeanImpl proposed = (WeblogicApplicationClientBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CdiDescriptor")) {
                  if (type == 2) {
                     original.setCdiDescriptor((CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getCdiDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CdiDescriptor", (DescriptorBean)original.getCdiDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("EjbReferenceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbReferenceDescription((EjbReferenceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbReferenceDescription((EjbReferenceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getEjbReferenceDescriptions() == null || original.getEjbReferenceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("MessageDestinationDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDestinationDescriptor((MessageDestinationDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDestinationDescriptor((MessageDestinationDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getMessageDestinationDescriptors() == null || original.getMessageDestinationDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("ResourceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceDescription((ResourceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceDescription((ResourceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getResourceDescriptions() == null || original.getResourceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("ResourceEnvDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceEnvDescription((ResourceEnvDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceEnvDescription((ResourceEnvDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getResourceEnvDescriptions() == null || original.getResourceEnvDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("ServerApplicationName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ServiceReferenceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceReferenceDescription((ServiceReferenceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceReferenceDescription((ServiceReferenceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getServiceReferenceDescriptions() == null || original.getServiceReferenceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            WeblogicApplicationClientBeanImpl copy = (WeblogicApplicationClientBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CdiDescriptor")) && this.bean.isCdiDescriptorSet() && !copy._isSet(8)) {
               Object o = this.bean.getCdiDescriptor();
               copy.setCdiDescriptor((CdiDescriptorBean)null);
               copy.setCdiDescriptor(o == null ? null : (CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("EjbReferenceDescriptions")) && this.bean.isEjbReferenceDescriptionsSet() && !copy._isSet(3)) {
               EjbReferenceDescriptionBean[] oldEjbReferenceDescriptions = this.bean.getEjbReferenceDescriptions();
               EjbReferenceDescriptionBean[] newEjbReferenceDescriptions = new EjbReferenceDescriptionBean[oldEjbReferenceDescriptions.length];

               for(i = 0; i < newEjbReferenceDescriptions.length; ++i) {
                  newEjbReferenceDescriptions[i] = (EjbReferenceDescriptionBean)((EjbReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldEjbReferenceDescriptions[i], includeObsolete));
               }

               copy.setEjbReferenceDescriptions(newEjbReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationDescriptors")) && this.bean.isMessageDestinationDescriptorsSet() && !copy._isSet(5)) {
               MessageDestinationDescriptorBean[] oldMessageDestinationDescriptors = this.bean.getMessageDestinationDescriptors();
               MessageDestinationDescriptorBean[] newMessageDestinationDescriptors = new MessageDestinationDescriptorBean[oldMessageDestinationDescriptors.length];

               for(i = 0; i < newMessageDestinationDescriptors.length; ++i) {
                  newMessageDestinationDescriptors[i] = (MessageDestinationDescriptorBean)((MessageDestinationDescriptorBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationDescriptors[i], includeObsolete));
               }

               copy.setMessageDestinationDescriptors(newMessageDestinationDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceDescriptions")) && this.bean.isResourceDescriptionsSet() && !copy._isSet(1)) {
               ResourceDescriptionBean[] oldResourceDescriptions = this.bean.getResourceDescriptions();
               ResourceDescriptionBean[] newResourceDescriptions = new ResourceDescriptionBean[oldResourceDescriptions.length];

               for(i = 0; i < newResourceDescriptions.length; ++i) {
                  newResourceDescriptions[i] = (ResourceDescriptionBean)((ResourceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceDescriptions[i], includeObsolete));
               }

               copy.setResourceDescriptions(newResourceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvDescriptions")) && this.bean.isResourceEnvDescriptionsSet() && !copy._isSet(2)) {
               ResourceEnvDescriptionBean[] oldResourceEnvDescriptions = this.bean.getResourceEnvDescriptions();
               ResourceEnvDescriptionBean[] newResourceEnvDescriptions = new ResourceEnvDescriptionBean[oldResourceEnvDescriptions.length];

               for(i = 0; i < newResourceEnvDescriptions.length; ++i) {
                  newResourceEnvDescriptions[i] = (ResourceEnvDescriptionBean)((ResourceEnvDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvDescriptions[i], includeObsolete));
               }

               copy.setResourceEnvDescriptions(newResourceEnvDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ServerApplicationName")) && this.bean.isServerApplicationNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceReferenceDescriptions")) && this.bean.isServiceReferenceDescriptionsSet() && !copy._isSet(4)) {
               ServiceReferenceDescriptionBean[] oldServiceReferenceDescriptions = this.bean.getServiceReferenceDescriptions();
               ServiceReferenceDescriptionBean[] newServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[oldServiceReferenceDescriptions.length];

               for(i = 0; i < newServiceReferenceDescriptions.length; ++i) {
                  newServiceReferenceDescriptions[i] = (ServiceReferenceDescriptionBean)((ServiceReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldServiceReferenceDescriptions[i], includeObsolete));
               }

               copy.setServiceReferenceDescriptions(newServiceReferenceDescriptions);
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
         this.inferSubTree(this.bean.getCdiDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceReferenceDescriptions(), clazz, annotation);
      }
   }
}
