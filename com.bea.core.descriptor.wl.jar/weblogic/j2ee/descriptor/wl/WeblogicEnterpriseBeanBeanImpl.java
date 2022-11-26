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

public class WeblogicEnterpriseBeanBeanImpl extends AbstractDescriptorBean implements WeblogicEnterpriseBeanBean, Serializable {
   private boolean _ClientsOnSameServer;
   private String _CreateAsPrincipalName;
   private String _DispatchPolicy;
   private String _EjbName;
   private EjbReferenceDescriptionBean[] _EjbReferenceDescriptions;
   private boolean _EnableCallByReference;
   private EntityDescriptorBean _EntityDescriptor;
   private String _Id;
   private IiopSecurityDescriptorBean _IiopSecurityDescriptor;
   private String _JNDIName;
   private JndiBindingBean[] _JndiBinding;
   private String _LocalJNDIName;
   private MessageDrivenDescriptorBean _MessageDrivenDescriptor;
   private String _NetworkAccessPoint;
   private String _PassivateAsPrincipalName;
   private int _RemoteClientTimeout;
   private String _RemoveAsPrincipalName;
   private ResourceDescriptionBean[] _ResourceDescriptions;
   private ResourceEnvDescriptionBean[] _ResourceEnvDescriptions;
   private String _RunAsPrincipalName;
   private ServiceReferenceDescriptionBean[] _ServiceReferenceDescriptions;
   private SingletonSessionDescriptorBean _SingletonSessionDescriptor;
   private StatefulSessionDescriptorBean _StatefulSessionDescriptor;
   private StatelessSessionDescriptorBean _StatelessSessionDescriptor;
   private boolean _StickToFirstServer;
   private TransactionDescriptorBean _TransactionDescriptor;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicEnterpriseBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public WeblogicEnterpriseBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WeblogicEnterpriseBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(0);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public EntityDescriptorBean getEntityDescriptor() {
      return this._EntityDescriptor;
   }

   public boolean isEntityDescriptorInherited() {
      return false;
   }

   public boolean isEntityDescriptorSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getEntityDescriptor());
   }

   public void setEntityDescriptor(EntityDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      EntityDescriptorBean _oldVal = this._EntityDescriptor;
      this._EntityDescriptor = param0;
      this._postSet(1, _oldVal, param0);
   }

   public StatelessSessionDescriptorBean getStatelessSessionDescriptor() {
      return this._StatelessSessionDescriptor;
   }

   public boolean isStatelessSessionDescriptorInherited() {
      return false;
   }

   public boolean isStatelessSessionDescriptorSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getStatelessSessionDescriptor());
   }

   public void setStatelessSessionDescriptor(StatelessSessionDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      StatelessSessionDescriptorBean _oldVal = this._StatelessSessionDescriptor;
      this._StatelessSessionDescriptor = param0;
      this._postSet(2, _oldVal, param0);
   }

   public StatefulSessionDescriptorBean getStatefulSessionDescriptor() {
      return this._StatefulSessionDescriptor;
   }

   public boolean isStatefulSessionDescriptorInherited() {
      return false;
   }

   public boolean isStatefulSessionDescriptorSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getStatefulSessionDescriptor());
   }

   public void setStatefulSessionDescriptor(StatefulSessionDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      StatefulSessionDescriptorBean _oldVal = this._StatefulSessionDescriptor;
      this._StatefulSessionDescriptor = param0;
      this._postSet(3, _oldVal, param0);
   }

   public SingletonSessionDescriptorBean getSingletonSessionDescriptor() {
      return this._SingletonSessionDescriptor;
   }

   public boolean isSingletonSessionDescriptorInherited() {
      return false;
   }

   public boolean isSingletonSessionDescriptorSet() {
      return this._isSet(4) || this._isAnythingSet((AbstractDescriptorBean)this.getSingletonSessionDescriptor());
   }

   public void setSingletonSessionDescriptor(SingletonSessionDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 4)) {
         this._postCreate(_child);
      }

      SingletonSessionDescriptorBean _oldVal = this._SingletonSessionDescriptor;
      this._SingletonSessionDescriptor = param0;
      this._postSet(4, _oldVal, param0);
   }

   public MessageDrivenDescriptorBean getMessageDrivenDescriptor() {
      return this._MessageDrivenDescriptor;
   }

   public boolean isMessageDrivenDescriptorInherited() {
      return false;
   }

   public boolean isMessageDrivenDescriptorSet() {
      return this._isSet(5) || this._isAnythingSet((AbstractDescriptorBean)this.getMessageDrivenDescriptor());
   }

   public void setMessageDrivenDescriptor(MessageDrivenDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 5)) {
         this._postCreate(_child);
      }

      MessageDrivenDescriptorBean _oldVal = this._MessageDrivenDescriptor;
      this._MessageDrivenDescriptor = param0;
      this._postSet(5, _oldVal, param0);
   }

   public TransactionDescriptorBean getTransactionDescriptor() {
      return this._TransactionDescriptor;
   }

   public boolean isTransactionDescriptorInherited() {
      return false;
   }

   public boolean isTransactionDescriptorSet() {
      return this._isSet(6) || this._isAnythingSet((AbstractDescriptorBean)this.getTransactionDescriptor());
   }

   public void setTransactionDescriptor(TransactionDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 6)) {
         this._postCreate(_child);
      }

      TransactionDescriptorBean _oldVal = this._TransactionDescriptor;
      this._TransactionDescriptor = param0;
      this._postSet(6, _oldVal, param0);
   }

   public IiopSecurityDescriptorBean getIiopSecurityDescriptor() {
      return this._IiopSecurityDescriptor;
   }

   public boolean isIiopSecurityDescriptorInherited() {
      return false;
   }

   public boolean isIiopSecurityDescriptorSet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getIiopSecurityDescriptor());
   }

   public void setIiopSecurityDescriptor(IiopSecurityDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      IiopSecurityDescriptorBean _oldVal = this._IiopSecurityDescriptor;
      this._IiopSecurityDescriptor = param0;
      this._postSet(7, _oldVal, param0);
   }

   public void addResourceDescription(ResourceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         ResourceDescriptionBean[] _new;
         if (this._isSet(8)) {
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
      return this._isSet(8);
   }

   public void removeResourceDescription(ResourceDescriptionBean param0) {
      this.destroyResourceDescription(param0);
   }

   public void setResourceDescriptions(ResourceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceDescriptionBean[] param0 = param0 == null ? new ResourceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceDescriptionBean[] _oldVal = this._ResourceDescriptions;
      this._ResourceDescriptions = (ResourceDescriptionBean[])param0;
      this._postSet(8, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 8);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         ResourceEnvDescriptionBean[] _new;
         if (this._isSet(9)) {
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
      return this._isSet(9);
   }

   public void removeResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      this.destroyResourceEnvDescription(param0);
   }

   public void setResourceEnvDescriptions(ResourceEnvDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvDescriptionBean[] param0 = param0 == null ? new ResourceEnvDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvDescriptionBean[] _oldVal = this._ResourceEnvDescriptions;
      this._ResourceEnvDescriptions = (ResourceEnvDescriptionBean[])param0;
      this._postSet(9, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 9);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         EjbReferenceDescriptionBean[] _new;
         if (this._isSet(10)) {
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
      return this._isSet(10);
   }

   public void removeEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      this.destroyEjbReferenceDescription(param0);
   }

   public void setEjbReferenceDescriptions(EjbReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      EjbReferenceDescriptionBean[] param0 = param0 == null ? new EjbReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbReferenceDescriptionBean[] _oldVal = this._EjbReferenceDescriptions;
      this._EjbReferenceDescriptions = (EjbReferenceDescriptionBean[])param0;
      this._postSet(10, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 10);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ServiceReferenceDescriptionBean[] _new;
         if (this._isSet(11)) {
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
      return this._isSet(11);
   }

   public void removeServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      this.destroyServiceReferenceDescription(param0);
   }

   public void setServiceReferenceDescriptions(ServiceReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ServiceReferenceDescriptionBean[] param0 = param0 == null ? new ServiceReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceReferenceDescriptionBean[] _oldVal = this._ServiceReferenceDescriptions;
      this._ServiceReferenceDescriptions = (ServiceReferenceDescriptionBean[])param0;
      this._postSet(11, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 11);
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

   public boolean isEnableCallByReference() {
      return this._EnableCallByReference;
   }

   public boolean isEnableCallByReferenceInherited() {
      return false;
   }

   public boolean isEnableCallByReferenceSet() {
      return this._isSet(12);
   }

   public void setEnableCallByReference(boolean param0) {
      boolean _oldVal = this._EnableCallByReference;
      this._EnableCallByReference = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getNetworkAccessPoint() {
      return this._NetworkAccessPoint;
   }

   public boolean isNetworkAccessPointInherited() {
      return false;
   }

   public boolean isNetworkAccessPointSet() {
      return this._isSet(13);
   }

   public void setNetworkAccessPoint(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NetworkAccessPoint;
      this._NetworkAccessPoint = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isClientsOnSameServer() {
      return this._ClientsOnSameServer;
   }

   public boolean isClientsOnSameServerInherited() {
      return false;
   }

   public boolean isClientsOnSameServerSet() {
      return this._isSet(14);
   }

   public void setClientsOnSameServer(boolean param0) {
      boolean _oldVal = this._ClientsOnSameServer;
      this._ClientsOnSameServer = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getRunAsPrincipalName() {
      return this._RunAsPrincipalName;
   }

   public boolean isRunAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRunAsPrincipalNameSet() {
      return this._isSet(15);
   }

   public void setRunAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunAsPrincipalName;
      this._RunAsPrincipalName = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getCreateAsPrincipalName() {
      return this._CreateAsPrincipalName;
   }

   public boolean isCreateAsPrincipalNameInherited() {
      return false;
   }

   public boolean isCreateAsPrincipalNameSet() {
      return this._isSet(16);
   }

   public void setCreateAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CreateAsPrincipalName;
      this._CreateAsPrincipalName = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getRemoveAsPrincipalName() {
      return this._RemoveAsPrincipalName;
   }

   public boolean isRemoveAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRemoveAsPrincipalNameSet() {
      return this._isSet(17);
   }

   public void setRemoveAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoveAsPrincipalName;
      this._RemoveAsPrincipalName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getPassivateAsPrincipalName() {
      return this._PassivateAsPrincipalName;
   }

   public boolean isPassivateAsPrincipalNameInherited() {
      return false;
   }

   public boolean isPassivateAsPrincipalNameSet() {
      return this._isSet(18);
   }

   public void setPassivateAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PassivateAsPrincipalName;
      this._PassivateAsPrincipalName = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(19);
   }

   public void setJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getLocalJNDIName() {
      return this._LocalJNDIName;
   }

   public boolean isLocalJNDINameInherited() {
      return false;
   }

   public boolean isLocalJNDINameSet() {
      return this._isSet(20);
   }

   public void setLocalJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalJNDIName;
      this._LocalJNDIName = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getDispatchPolicy() {
      return this._DispatchPolicy;
   }

   public boolean isDispatchPolicyInherited() {
      return false;
   }

   public boolean isDispatchPolicySet() {
      return this._isSet(21);
   }

   public void setDispatchPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DispatchPolicy;
      this._DispatchPolicy = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getRemoteClientTimeout() {
      return this._RemoteClientTimeout;
   }

   public boolean isRemoteClientTimeoutInherited() {
      return false;
   }

   public boolean isRemoteClientTimeoutSet() {
      return this._isSet(22);
   }

   public void setRemoteClientTimeout(int param0) {
      int _oldVal = this._RemoteClientTimeout;
      this._RemoteClientTimeout = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isStickToFirstServer() {
      return this._StickToFirstServer;
   }

   public boolean isStickToFirstServerInherited() {
      return false;
   }

   public boolean isStickToFirstServerSet() {
      return this._isSet(23);
   }

   public void setStickToFirstServer(boolean param0) {
      boolean _oldVal = this._StickToFirstServer;
      this._StickToFirstServer = param0;
      this._postSet(23, _oldVal, param0);
   }

   public void addJndiBinding(JndiBindingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         JndiBindingBean[] _new;
         if (this._isSet(24)) {
            _new = (JndiBindingBean[])((JndiBindingBean[])this._getHelper()._extendArray(this.getJndiBinding(), JndiBindingBean.class, param0));
         } else {
            _new = new JndiBindingBean[]{param0};
         }

         try {
            this.setJndiBinding(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JndiBindingBean[] getJndiBinding() {
      return this._JndiBinding;
   }

   public boolean isJndiBindingInherited() {
      return false;
   }

   public boolean isJndiBindingSet() {
      return this._isSet(24);
   }

   public void removeJndiBinding(JndiBindingBean param0) {
      this.destroyJndiBinding(param0);
   }

   public void setJndiBinding(JndiBindingBean[] param0) throws InvalidAttributeValueException {
      JndiBindingBean[] param0 = param0 == null ? new JndiBindingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JndiBindingBean[] _oldVal = this._JndiBinding;
      this._JndiBinding = (JndiBindingBean[])param0;
      this._postSet(24, _oldVal, param0);
   }

   public JndiBindingBean createJndiBinding() {
      JndiBindingBeanImpl _val = new JndiBindingBeanImpl(this, -1);

      try {
         this.addJndiBinding(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJndiBinding(JndiBindingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 24);
         JndiBindingBean[] _old = this.getJndiBinding();
         JndiBindingBean[] _new = (JndiBindingBean[])((JndiBindingBean[])this._getHelper()._removeElement(_old, JndiBindingBean.class, param0));
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
               this.setJndiBinding(_new);
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

   public JndiBindingBean lookupJndiBinding(String param0) {
      Object[] aary = (Object[])this._JndiBinding;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JndiBindingBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JndiBindingBeanImpl)it.previous();
      } while(!bean.getClassName().equals(param0));

      return bean;
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(25);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(25, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEjbName();
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
         case 8:
            if (s.equals("ejb-name")) {
               return info.compareXpaths(this._getPropertyXpath("ejb-name"));
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
      return super._isAnythingSet() || this.isEntityDescriptorSet() || this.isIiopSecurityDescriptorSet() || this.isMessageDrivenDescriptorSet() || this.isSingletonSessionDescriptorSet() || this.isStatefulSessionDescriptorSet() || this.isStatelessSessionDescriptorSet() || this.isTransactionDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._CreateAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._DispatchPolicy = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._EjbReferenceDescriptions = new EjbReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._EntityDescriptor = new EntityDescriptorBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._EntityDescriptor);
               if (initOne) {
                  break;
               }
            case 25:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._IiopSecurityDescriptor = new IiopSecurityDescriptorBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._IiopSecurityDescriptor);
               if (initOne) {
                  break;
               }
            case 19:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._JndiBinding = new JndiBindingBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._LocalJNDIName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._MessageDrivenDescriptor = new MessageDrivenDescriptorBeanImpl(this, 5);
               this._postCreate((AbstractDescriptorBean)this._MessageDrivenDescriptor);
               if (initOne) {
                  break;
               }
            case 13:
               this._NetworkAccessPoint = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._PassivateAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._RemoteClientTimeout = 0;
               if (initOne) {
                  break;
               }
            case 17:
               this._RemoveAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._ResourceDescriptions = new ResourceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._ResourceEnvDescriptions = new ResourceEnvDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._RunAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._SingletonSessionDescriptor = new SingletonSessionDescriptorBeanImpl(this, 4);
               this._postCreate((AbstractDescriptorBean)this._SingletonSessionDescriptor);
               if (initOne) {
                  break;
               }
            case 3:
               this._StatefulSessionDescriptor = new StatefulSessionDescriptorBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._StatefulSessionDescriptor);
               if (initOne) {
                  break;
               }
            case 2:
               this._StatelessSessionDescriptor = new StatelessSessionDescriptorBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._StatelessSessionDescriptor);
               if (initOne) {
                  break;
               }
            case 6:
               this._TransactionDescriptor = new TransactionDescriptorBeanImpl(this, 6);
               this._postCreate((AbstractDescriptorBean)this._TransactionDescriptor);
               if (initOne) {
                  break;
               }
            case 14:
               this._ClientsOnSameServer = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._EnableCallByReference = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._StickToFirstServer = false;
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
            case 2:
               if (s.equals("id")) {
                  return 25;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 13:
            case 14:
            case 16:
            case 18:
            case 19:
            case 23:
            case 26:
            default:
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 0;
               }
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 19;
               }
               break;
            case 12:
               if (s.equals("jndi-binding")) {
                  return 24;
               }
               break;
            case 15:
               if (s.equals("dispatch-policy")) {
                  return 21;
               }

               if (s.equals("local-jndi-name")) {
                  return 20;
               }
               break;
            case 17:
               if (s.equals("entity-descriptor")) {
                  return 1;
               }
               break;
            case 20:
               if (s.equals("network-access-point")) {
                  return 13;
               }

               if (s.equals("resource-description")) {
                  return 8;
               }
               break;
            case 21:
               if (s.equals("remote-client-timeout")) {
                  return 22;
               }

               if (s.equals("run-as-principal-name")) {
                  return 15;
               }

               if (s.equals("stick-to-first-server")) {
                  return 23;
               }
               break;
            case 22:
               if (s.equals("transaction-descriptor")) {
                  return 6;
               }

               if (s.equals("clients-on-same-server")) {
                  return 14;
               }
               break;
            case 24:
               if (s.equals("create-as-principal-name")) {
                  return 16;
               }

               if (s.equals("iiop-security-descriptor")) {
                  return 7;
               }

               if (s.equals("remove-as-principal-name")) {
                  return 17;
               }

               if (s.equals("resource-env-description")) {
                  return 9;
               }

               if (s.equals("enable-call-by-reference")) {
                  return 12;
               }
               break;
            case 25:
               if (s.equals("ejb-reference-description")) {
                  return 10;
               }

               if (s.equals("message-driven-descriptor")) {
                  return 5;
               }
               break;
            case 27:
               if (s.equals("passivate-as-principal-name")) {
                  return 18;
               }

               if (s.equals("stateful-session-descriptor")) {
                  return 3;
               }
               break;
            case 28:
               if (s.equals("singleton-session-descriptor")) {
                  return 4;
               }

               if (s.equals("stateless-session-descriptor")) {
                  return 2;
               }
               break;
            case 29:
               if (s.equals("service-reference-description")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new EntityDescriptorBeanImpl.SchemaHelper2();
            case 2:
               return new StatelessSessionDescriptorBeanImpl.SchemaHelper2();
            case 3:
               return new StatefulSessionDescriptorBeanImpl.SchemaHelper2();
            case 4:
               return new SingletonSessionDescriptorBeanImpl.SchemaHelper2();
            case 5:
               return new MessageDrivenDescriptorBeanImpl.SchemaHelper2();
            case 6:
               return new TransactionDescriptorBeanImpl.SchemaHelper2();
            case 7:
               return new IiopSecurityDescriptorBeanImpl.SchemaHelper2();
            case 8:
               return new ResourceDescriptionBeanImpl.SchemaHelper2();
            case 9:
               return new ResourceEnvDescriptionBeanImpl.SchemaHelper2();
            case 10:
               return new EjbReferenceDescriptionBeanImpl.SchemaHelper2();
            case 11:
               return new ServiceReferenceDescriptionBeanImpl.SchemaHelper2();
            case 12:
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
            default:
               return super.getSchemaHelper(propIndex);
            case 24:
               return new JndiBindingBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ejb-name";
            case 1:
               return "entity-descriptor";
            case 2:
               return "stateless-session-descriptor";
            case 3:
               return "stateful-session-descriptor";
            case 4:
               return "singleton-session-descriptor";
            case 5:
               return "message-driven-descriptor";
            case 6:
               return "transaction-descriptor";
            case 7:
               return "iiop-security-descriptor";
            case 8:
               return "resource-description";
            case 9:
               return "resource-env-description";
            case 10:
               return "ejb-reference-description";
            case 11:
               return "service-reference-description";
            case 12:
               return "enable-call-by-reference";
            case 13:
               return "network-access-point";
            case 14:
               return "clients-on-same-server";
            case 15:
               return "run-as-principal-name";
            case 16:
               return "create-as-principal-name";
            case 17:
               return "remove-as-principal-name";
            case 18:
               return "passivate-as-principal-name";
            case 19:
               return "jndi-name";
            case 20:
               return "local-jndi-name";
            case 21:
               return "dispatch-policy";
            case 22:
               return "remote-client-timeout";
            case 23:
               return "stick-to-first-server";
            case 24:
               return "jndi-binding";
            case 25:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 24:
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
            default:
               return super.isBean(propIndex);
            case 24:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            case 14:
            case 19:
            case 20:
            default:
               return super.isConfigurable(propIndex);
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 21:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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
         indices.add("ejb-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicEnterpriseBeanBeanImpl bean;

      protected Helper(WeblogicEnterpriseBeanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EjbName";
            case 1:
               return "EntityDescriptor";
            case 2:
               return "StatelessSessionDescriptor";
            case 3:
               return "StatefulSessionDescriptor";
            case 4:
               return "SingletonSessionDescriptor";
            case 5:
               return "MessageDrivenDescriptor";
            case 6:
               return "TransactionDescriptor";
            case 7:
               return "IiopSecurityDescriptor";
            case 8:
               return "ResourceDescriptions";
            case 9:
               return "ResourceEnvDescriptions";
            case 10:
               return "EjbReferenceDescriptions";
            case 11:
               return "ServiceReferenceDescriptions";
            case 12:
               return "EnableCallByReference";
            case 13:
               return "NetworkAccessPoint";
            case 14:
               return "ClientsOnSameServer";
            case 15:
               return "RunAsPrincipalName";
            case 16:
               return "CreateAsPrincipalName";
            case 17:
               return "RemoveAsPrincipalName";
            case 18:
               return "PassivateAsPrincipalName";
            case 19:
               return "JNDIName";
            case 20:
               return "LocalJNDIName";
            case 21:
               return "DispatchPolicy";
            case 22:
               return "RemoteClientTimeout";
            case 23:
               return "StickToFirstServer";
            case 24:
               return "JndiBinding";
            case 25:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CreateAsPrincipalName")) {
            return 16;
         } else if (propName.equals("DispatchPolicy")) {
            return 21;
         } else if (propName.equals("EjbName")) {
            return 0;
         } else if (propName.equals("EjbReferenceDescriptions")) {
            return 10;
         } else if (propName.equals("EntityDescriptor")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 25;
         } else if (propName.equals("IiopSecurityDescriptor")) {
            return 7;
         } else if (propName.equals("JNDIName")) {
            return 19;
         } else if (propName.equals("JndiBinding")) {
            return 24;
         } else if (propName.equals("LocalJNDIName")) {
            return 20;
         } else if (propName.equals("MessageDrivenDescriptor")) {
            return 5;
         } else if (propName.equals("NetworkAccessPoint")) {
            return 13;
         } else if (propName.equals("PassivateAsPrincipalName")) {
            return 18;
         } else if (propName.equals("RemoteClientTimeout")) {
            return 22;
         } else if (propName.equals("RemoveAsPrincipalName")) {
            return 17;
         } else if (propName.equals("ResourceDescriptions")) {
            return 8;
         } else if (propName.equals("ResourceEnvDescriptions")) {
            return 9;
         } else if (propName.equals("RunAsPrincipalName")) {
            return 15;
         } else if (propName.equals("ServiceReferenceDescriptions")) {
            return 11;
         } else if (propName.equals("SingletonSessionDescriptor")) {
            return 4;
         } else if (propName.equals("StatefulSessionDescriptor")) {
            return 3;
         } else if (propName.equals("StatelessSessionDescriptor")) {
            return 2;
         } else if (propName.equals("TransactionDescriptor")) {
            return 6;
         } else if (propName.equals("ClientsOnSameServer")) {
            return 14;
         } else if (propName.equals("EnableCallByReference")) {
            return 12;
         } else {
            return propName.equals("StickToFirstServer") ? 23 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getEjbReferenceDescriptions()));
         if (this.bean.getEntityDescriptor() != null) {
            iterators.add(new ArrayIterator(new EntityDescriptorBean[]{this.bean.getEntityDescriptor()}));
         }

         if (this.bean.getIiopSecurityDescriptor() != null) {
            iterators.add(new ArrayIterator(new IiopSecurityDescriptorBean[]{this.bean.getIiopSecurityDescriptor()}));
         }

         iterators.add(new ArrayIterator(this.bean.getJndiBinding()));
         if (this.bean.getMessageDrivenDescriptor() != null) {
            iterators.add(new ArrayIterator(new MessageDrivenDescriptorBean[]{this.bean.getMessageDrivenDescriptor()}));
         }

         iterators.add(new ArrayIterator(this.bean.getResourceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getServiceReferenceDescriptions()));
         if (this.bean.getSingletonSessionDescriptor() != null) {
            iterators.add(new ArrayIterator(new SingletonSessionDescriptorBean[]{this.bean.getSingletonSessionDescriptor()}));
         }

         if (this.bean.getStatefulSessionDescriptor() != null) {
            iterators.add(new ArrayIterator(new StatefulSessionDescriptorBean[]{this.bean.getStatefulSessionDescriptor()}));
         }

         if (this.bean.getStatelessSessionDescriptor() != null) {
            iterators.add(new ArrayIterator(new StatelessSessionDescriptorBean[]{this.bean.getStatelessSessionDescriptor()}));
         }

         if (this.bean.getTransactionDescriptor() != null) {
            iterators.add(new ArrayIterator(new TransactionDescriptorBean[]{this.bean.getTransactionDescriptor()}));
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
            if (this.bean.isCreateAsPrincipalNameSet()) {
               buf.append("CreateAsPrincipalName");
               buf.append(String.valueOf(this.bean.getCreateAsPrincipalName()));
            }

            if (this.bean.isDispatchPolicySet()) {
               buf.append("DispatchPolicy");
               buf.append(String.valueOf(this.bean.getDispatchPolicy()));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getEjbReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getEntityDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getIiopSecurityDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJndiBinding().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJndiBinding()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLocalJNDINameSet()) {
               buf.append("LocalJNDIName");
               buf.append(String.valueOf(this.bean.getLocalJNDIName()));
            }

            childValue = this.computeChildHashValue(this.bean.getMessageDrivenDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNetworkAccessPointSet()) {
               buf.append("NetworkAccessPoint");
               buf.append(String.valueOf(this.bean.getNetworkAccessPoint()));
            }

            if (this.bean.isPassivateAsPrincipalNameSet()) {
               buf.append("PassivateAsPrincipalName");
               buf.append(String.valueOf(this.bean.getPassivateAsPrincipalName()));
            }

            if (this.bean.isRemoteClientTimeoutSet()) {
               buf.append("RemoteClientTimeout");
               buf.append(String.valueOf(this.bean.getRemoteClientTimeout()));
            }

            if (this.bean.isRemoveAsPrincipalNameSet()) {
               buf.append("RemoveAsPrincipalName");
               buf.append(String.valueOf(this.bean.getRemoveAsPrincipalName()));
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

            if (this.bean.isRunAsPrincipalNameSet()) {
               buf.append("RunAsPrincipalName");
               buf.append(String.valueOf(this.bean.getRunAsPrincipalName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSingletonSessionDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getStatefulSessionDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getStatelessSessionDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTransactionDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isClientsOnSameServerSet()) {
               buf.append("ClientsOnSameServer");
               buf.append(String.valueOf(this.bean.isClientsOnSameServer()));
            }

            if (this.bean.isEnableCallByReferenceSet()) {
               buf.append("EnableCallByReference");
               buf.append(String.valueOf(this.bean.isEnableCallByReference()));
            }

            if (this.bean.isStickToFirstServerSet()) {
               buf.append("StickToFirstServer");
               buf.append(String.valueOf(this.bean.isStickToFirstServer()));
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
            WeblogicEnterpriseBeanBeanImpl otherTyped = (WeblogicEnterpriseBeanBeanImpl)other;
            this.computeDiff("CreateAsPrincipalName", this.bean.getCreateAsPrincipalName(), otherTyped.getCreateAsPrincipalName(), true);
            this.computeDiff("DispatchPolicy", this.bean.getDispatchPolicy(), otherTyped.getDispatchPolicy(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeChildDiff("EjbReferenceDescriptions", this.bean.getEjbReferenceDescriptions(), otherTyped.getEjbReferenceDescriptions(), false);
            this.computeSubDiff("EntityDescriptor", this.bean.getEntityDescriptor(), otherTyped.getEntityDescriptor());
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("IiopSecurityDescriptor", this.bean.getIiopSecurityDescriptor(), otherTyped.getIiopSecurityDescriptor());
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            this.computeChildDiff("JndiBinding", this.bean.getJndiBinding(), otherTyped.getJndiBinding(), false);
            this.computeDiff("LocalJNDIName", this.bean.getLocalJNDIName(), otherTyped.getLocalJNDIName(), false);
            this.computeSubDiff("MessageDrivenDescriptor", this.bean.getMessageDrivenDescriptor(), otherTyped.getMessageDrivenDescriptor());
            this.computeDiff("NetworkAccessPoint", this.bean.getNetworkAccessPoint(), otherTyped.getNetworkAccessPoint(), true);
            this.computeDiff("PassivateAsPrincipalName", this.bean.getPassivateAsPrincipalName(), otherTyped.getPassivateAsPrincipalName(), true);
            this.computeDiff("RemoteClientTimeout", this.bean.getRemoteClientTimeout(), otherTyped.getRemoteClientTimeout(), false);
            this.computeDiff("RemoveAsPrincipalName", this.bean.getRemoveAsPrincipalName(), otherTyped.getRemoveAsPrincipalName(), true);
            this.computeChildDiff("ResourceDescriptions", this.bean.getResourceDescriptions(), otherTyped.getResourceDescriptions(), false);
            this.computeChildDiff("ResourceEnvDescriptions", this.bean.getResourceEnvDescriptions(), otherTyped.getResourceEnvDescriptions(), false);
            this.computeDiff("RunAsPrincipalName", this.bean.getRunAsPrincipalName(), otherTyped.getRunAsPrincipalName(), true);
            this.computeChildDiff("ServiceReferenceDescriptions", this.bean.getServiceReferenceDescriptions(), otherTyped.getServiceReferenceDescriptions(), false);
            this.computeSubDiff("SingletonSessionDescriptor", this.bean.getSingletonSessionDescriptor(), otherTyped.getSingletonSessionDescriptor());
            this.computeSubDiff("StatefulSessionDescriptor", this.bean.getStatefulSessionDescriptor(), otherTyped.getStatefulSessionDescriptor());
            this.computeSubDiff("StatelessSessionDescriptor", this.bean.getStatelessSessionDescriptor(), otherTyped.getStatelessSessionDescriptor());
            this.computeSubDiff("TransactionDescriptor", this.bean.getTransactionDescriptor(), otherTyped.getTransactionDescriptor());
            this.computeDiff("ClientsOnSameServer", this.bean.isClientsOnSameServer(), otherTyped.isClientsOnSameServer(), false);
            this.computeDiff("EnableCallByReference", this.bean.isEnableCallByReference(), otherTyped.isEnableCallByReference(), false);
            this.computeDiff("StickToFirstServer", this.bean.isStickToFirstServer(), otherTyped.isStickToFirstServer(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicEnterpriseBeanBeanImpl original = (WeblogicEnterpriseBeanBeanImpl)event.getSourceBean();
            WeblogicEnterpriseBeanBeanImpl proposed = (WeblogicEnterpriseBeanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CreateAsPrincipalName")) {
                  original.setCreateAsPrincipalName(proposed.getCreateAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DispatchPolicy")) {
                  original.setDispatchPolicy(proposed.getDispatchPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("EntityDescriptor")) {
                  if (type == 2) {
                     original.setEntityDescriptor((EntityDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getEntityDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EntityDescriptor", (DescriptorBean)original.getEntityDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("IiopSecurityDescriptor")) {
                  if (type == 2) {
                     original.setIiopSecurityDescriptor((IiopSecurityDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getIiopSecurityDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("IiopSecurityDescriptor", (DescriptorBean)original.getIiopSecurityDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("JndiBinding")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJndiBinding((JndiBindingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJndiBinding((JndiBindingBean)update.getRemovedObject());
                  }

                  if (original.getJndiBinding() == null || original.getJndiBinding().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  }
               } else if (prop.equals("LocalJNDIName")) {
                  original.setLocalJNDIName(proposed.getLocalJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("MessageDrivenDescriptor")) {
                  if (type == 2) {
                     original.setMessageDrivenDescriptor((MessageDrivenDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getMessageDrivenDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MessageDrivenDescriptor", (DescriptorBean)original.getMessageDrivenDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("NetworkAccessPoint")) {
                  original.setNetworkAccessPoint(proposed.getNetworkAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("PassivateAsPrincipalName")) {
                  original.setPassivateAsPrincipalName(proposed.getPassivateAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("RemoteClientTimeout")) {
                  original.setRemoteClientTimeout(proposed.getRemoteClientTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("RemoveAsPrincipalName")) {
                  original.setRemoveAsPrincipalName(proposed.getRemoveAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("RunAsPrincipalName")) {
                  original.setRunAsPrincipalName(proposed.getRunAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("SingletonSessionDescriptor")) {
                  if (type == 2) {
                     original.setSingletonSessionDescriptor((SingletonSessionDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getSingletonSessionDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SingletonSessionDescriptor", (DescriptorBean)original.getSingletonSessionDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("StatefulSessionDescriptor")) {
                  if (type == 2) {
                     original.setStatefulSessionDescriptor((StatefulSessionDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getStatefulSessionDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StatefulSessionDescriptor", (DescriptorBean)original.getStatefulSessionDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("StatelessSessionDescriptor")) {
                  if (type == 2) {
                     original.setStatelessSessionDescriptor((StatelessSessionDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getStatelessSessionDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StatelessSessionDescriptor", (DescriptorBean)original.getStatelessSessionDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TransactionDescriptor")) {
                  if (type == 2) {
                     original.setTransactionDescriptor((TransactionDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getTransactionDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TransactionDescriptor", (DescriptorBean)original.getTransactionDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ClientsOnSameServer")) {
                  original.setClientsOnSameServer(proposed.isClientsOnSameServer());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("EnableCallByReference")) {
                  original.setEnableCallByReference(proposed.isEnableCallByReference());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("StickToFirstServer")) {
                  original.setStickToFirstServer(proposed.isStickToFirstServer());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
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
            WeblogicEnterpriseBeanBeanImpl copy = (WeblogicEnterpriseBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CreateAsPrincipalName")) && this.bean.isCreateAsPrincipalNameSet()) {
               copy.setCreateAsPrincipalName(this.bean.getCreateAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("DispatchPolicy")) && this.bean.isDispatchPolicySet()) {
               copy.setDispatchPolicy(this.bean.getDispatchPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("EjbReferenceDescriptions")) && this.bean.isEjbReferenceDescriptionsSet() && !copy._isSet(10)) {
               EjbReferenceDescriptionBean[] oldEjbReferenceDescriptions = this.bean.getEjbReferenceDescriptions();
               EjbReferenceDescriptionBean[] newEjbReferenceDescriptions = new EjbReferenceDescriptionBean[oldEjbReferenceDescriptions.length];

               for(i = 0; i < newEjbReferenceDescriptions.length; ++i) {
                  newEjbReferenceDescriptions[i] = (EjbReferenceDescriptionBean)((EjbReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldEjbReferenceDescriptions[i], includeObsolete));
               }

               copy.setEjbReferenceDescriptions(newEjbReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("EntityDescriptor")) && this.bean.isEntityDescriptorSet() && !copy._isSet(1)) {
               Object o = this.bean.getEntityDescriptor();
               copy.setEntityDescriptor((EntityDescriptorBean)null);
               copy.setEntityDescriptor(o == null ? null : (EntityDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IiopSecurityDescriptor")) && this.bean.isIiopSecurityDescriptorSet() && !copy._isSet(7)) {
               Object o = this.bean.getIiopSecurityDescriptor();
               copy.setIiopSecurityDescriptor((IiopSecurityDescriptorBean)null);
               copy.setIiopSecurityDescriptor(o == null ? null : (IiopSecurityDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("JndiBinding")) && this.bean.isJndiBindingSet() && !copy._isSet(24)) {
               JndiBindingBean[] oldJndiBinding = this.bean.getJndiBinding();
               JndiBindingBean[] newJndiBinding = new JndiBindingBean[oldJndiBinding.length];

               for(i = 0; i < newJndiBinding.length; ++i) {
                  newJndiBinding[i] = (JndiBindingBean)((JndiBindingBean)this.createCopy((AbstractDescriptorBean)oldJndiBinding[i], includeObsolete));
               }

               copy.setJndiBinding(newJndiBinding);
            }

            if ((excludeProps == null || !excludeProps.contains("LocalJNDIName")) && this.bean.isLocalJNDINameSet()) {
               copy.setLocalJNDIName(this.bean.getLocalJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDrivenDescriptor")) && this.bean.isMessageDrivenDescriptorSet() && !copy._isSet(5)) {
               Object o = this.bean.getMessageDrivenDescriptor();
               copy.setMessageDrivenDescriptor((MessageDrivenDescriptorBean)null);
               copy.setMessageDrivenDescriptor(o == null ? null : (MessageDrivenDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NetworkAccessPoint")) && this.bean.isNetworkAccessPointSet()) {
               copy.setNetworkAccessPoint(this.bean.getNetworkAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("PassivateAsPrincipalName")) && this.bean.isPassivateAsPrincipalNameSet()) {
               copy.setPassivateAsPrincipalName(this.bean.getPassivateAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteClientTimeout")) && this.bean.isRemoteClientTimeoutSet()) {
               copy.setRemoteClientTimeout(this.bean.getRemoteClientTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoveAsPrincipalName")) && this.bean.isRemoveAsPrincipalNameSet()) {
               copy.setRemoveAsPrincipalName(this.bean.getRemoveAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceDescriptions")) && this.bean.isResourceDescriptionsSet() && !copy._isSet(8)) {
               ResourceDescriptionBean[] oldResourceDescriptions = this.bean.getResourceDescriptions();
               ResourceDescriptionBean[] newResourceDescriptions = new ResourceDescriptionBean[oldResourceDescriptions.length];

               for(i = 0; i < newResourceDescriptions.length; ++i) {
                  newResourceDescriptions[i] = (ResourceDescriptionBean)((ResourceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceDescriptions[i], includeObsolete));
               }

               copy.setResourceDescriptions(newResourceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvDescriptions")) && this.bean.isResourceEnvDescriptionsSet() && !copy._isSet(9)) {
               ResourceEnvDescriptionBean[] oldResourceEnvDescriptions = this.bean.getResourceEnvDescriptions();
               ResourceEnvDescriptionBean[] newResourceEnvDescriptions = new ResourceEnvDescriptionBean[oldResourceEnvDescriptions.length];

               for(i = 0; i < newResourceEnvDescriptions.length; ++i) {
                  newResourceEnvDescriptions[i] = (ResourceEnvDescriptionBean)((ResourceEnvDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvDescriptions[i], includeObsolete));
               }

               copy.setResourceEnvDescriptions(newResourceEnvDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsPrincipalName")) && this.bean.isRunAsPrincipalNameSet()) {
               copy.setRunAsPrincipalName(this.bean.getRunAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceReferenceDescriptions")) && this.bean.isServiceReferenceDescriptionsSet() && !copy._isSet(11)) {
               ServiceReferenceDescriptionBean[] oldServiceReferenceDescriptions = this.bean.getServiceReferenceDescriptions();
               ServiceReferenceDescriptionBean[] newServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[oldServiceReferenceDescriptions.length];

               for(i = 0; i < newServiceReferenceDescriptions.length; ++i) {
                  newServiceReferenceDescriptions[i] = (ServiceReferenceDescriptionBean)((ServiceReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldServiceReferenceDescriptions[i], includeObsolete));
               }

               copy.setServiceReferenceDescriptions(newServiceReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("SingletonSessionDescriptor")) && this.bean.isSingletonSessionDescriptorSet() && !copy._isSet(4)) {
               Object o = this.bean.getSingletonSessionDescriptor();
               copy.setSingletonSessionDescriptor((SingletonSessionDescriptorBean)null);
               copy.setSingletonSessionDescriptor(o == null ? null : (SingletonSessionDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StatefulSessionDescriptor")) && this.bean.isStatefulSessionDescriptorSet() && !copy._isSet(3)) {
               Object o = this.bean.getStatefulSessionDescriptor();
               copy.setStatefulSessionDescriptor((StatefulSessionDescriptorBean)null);
               copy.setStatefulSessionDescriptor(o == null ? null : (StatefulSessionDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StatelessSessionDescriptor")) && this.bean.isStatelessSessionDescriptorSet() && !copy._isSet(2)) {
               Object o = this.bean.getStatelessSessionDescriptor();
               copy.setStatelessSessionDescriptor((StatelessSessionDescriptorBean)null);
               copy.setStatelessSessionDescriptor(o == null ? null : (StatelessSessionDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionDescriptor")) && this.bean.isTransactionDescriptorSet() && !copy._isSet(6)) {
               Object o = this.bean.getTransactionDescriptor();
               copy.setTransactionDescriptor((TransactionDescriptorBean)null);
               copy.setTransactionDescriptor(o == null ? null : (TransactionDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClientsOnSameServer")) && this.bean.isClientsOnSameServerSet()) {
               copy.setClientsOnSameServer(this.bean.isClientsOnSameServer());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableCallByReference")) && this.bean.isEnableCallByReferenceSet()) {
               copy.setEnableCallByReference(this.bean.isEnableCallByReference());
            }

            if ((excludeProps == null || !excludeProps.contains("StickToFirstServer")) && this.bean.isStickToFirstServerSet()) {
               copy.setStickToFirstServer(this.bean.isStickToFirstServer());
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
         this.inferSubTree(this.bean.getEjbReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getEntityDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getIiopSecurityDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getJndiBinding(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDrivenDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getSingletonSessionDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getStatefulSessionDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getStatelessSessionDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactionDescriptor(), clazz, annotation);
      }
   }
}
