package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class AssemblyDescriptorBeanImpl extends AbstractDescriptorBean implements AssemblyDescriptorBean, Serializable {
   private ApplicationExceptionBean[] _ApplicationExceptions;
   private ContainerTransactionBean[] _ContainerTransactions;
   private ExcludeListBean _ExcludeList;
   private String _Id;
   private InterceptorBindingBean[] _InterceptorBindings;
   private MessageDestinationBean[] _MessageDestinations;
   private MethodPermissionBean[] _MethodPermissions;
   private SecurityRoleBean[] _SecurityRoles;
   private static SchemaHelper2 _schemaHelper;

   public AssemblyDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public AssemblyDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AssemblyDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addSecurityRole(SecurityRoleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         SecurityRoleBean[] _new;
         if (this._isSet(0)) {
            _new = (SecurityRoleBean[])((SecurityRoleBean[])this._getHelper()._extendArray(this.getSecurityRoles(), SecurityRoleBean.class, param0));
         } else {
            _new = new SecurityRoleBean[]{param0};
         }

         try {
            this.setSecurityRoles(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityRoleBean[] getSecurityRoles() {
      return this._SecurityRoles;
   }

   public boolean isSecurityRolesInherited() {
      return false;
   }

   public boolean isSecurityRolesSet() {
      return this._isSet(0);
   }

   public void removeSecurityRole(SecurityRoleBean param0) {
      this.destroySecurityRole(param0);
   }

   public void setSecurityRoles(SecurityRoleBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleBean[] param0 = param0 == null ? new SecurityRoleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleBean[] _oldVal = this._SecurityRoles;
      this._SecurityRoles = (SecurityRoleBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public SecurityRoleBean createSecurityRole() {
      SecurityRoleBeanImpl _val = new SecurityRoleBeanImpl(this, -1);

      try {
         this.addSecurityRole(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityRole(SecurityRoleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         SecurityRoleBean[] _old = this.getSecurityRoles();
         SecurityRoleBean[] _new = (SecurityRoleBean[])((SecurityRoleBean[])this._getHelper()._removeElement(_old, SecurityRoleBean.class, param0));
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
               this.setSecurityRoles(_new);
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

   public void addMethodPermission(MethodPermissionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         MethodPermissionBean[] _new;
         if (this._isSet(1)) {
            _new = (MethodPermissionBean[])((MethodPermissionBean[])this._getHelper()._extendArray(this.getMethodPermissions(), MethodPermissionBean.class, param0));
         } else {
            _new = new MethodPermissionBean[]{param0};
         }

         try {
            this.setMethodPermissions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MethodPermissionBean[] getMethodPermissions() {
      return this._MethodPermissions;
   }

   public boolean isMethodPermissionsInherited() {
      return false;
   }

   public boolean isMethodPermissionsSet() {
      return this._isSet(1);
   }

   public void removeMethodPermission(MethodPermissionBean param0) {
      this.destroyMethodPermission(param0);
   }

   public void setMethodPermissions(MethodPermissionBean[] param0) throws InvalidAttributeValueException {
      MethodPermissionBean[] param0 = param0 == null ? new MethodPermissionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MethodPermissionBean[] _oldVal = this._MethodPermissions;
      this._MethodPermissions = (MethodPermissionBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public MethodPermissionBean createMethodPermission() {
      MethodPermissionBeanImpl _val = new MethodPermissionBeanImpl(this, -1);

      try {
         this.addMethodPermission(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMethodPermission(MethodPermissionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         MethodPermissionBean[] _old = this.getMethodPermissions();
         MethodPermissionBean[] _new = (MethodPermissionBean[])((MethodPermissionBean[])this._getHelper()._removeElement(_old, MethodPermissionBean.class, param0));
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
               this.setMethodPermissions(_new);
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

   public void addContainerTransaction(ContainerTransactionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ContainerTransactionBean[] _new;
         if (this._isSet(2)) {
            _new = (ContainerTransactionBean[])((ContainerTransactionBean[])this._getHelper()._extendArray(this.getContainerTransactions(), ContainerTransactionBean.class, param0));
         } else {
            _new = new ContainerTransactionBean[]{param0};
         }

         try {
            this.setContainerTransactions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ContainerTransactionBean[] getContainerTransactions() {
      return this._ContainerTransactions;
   }

   public boolean isContainerTransactionsInherited() {
      return false;
   }

   public boolean isContainerTransactionsSet() {
      return this._isSet(2);
   }

   public void removeContainerTransaction(ContainerTransactionBean param0) {
      this.destroyContainerTransaction(param0);
   }

   public void setContainerTransactions(ContainerTransactionBean[] param0) throws InvalidAttributeValueException {
      ContainerTransactionBean[] param0 = param0 == null ? new ContainerTransactionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ContainerTransactionBean[] _oldVal = this._ContainerTransactions;
      this._ContainerTransactions = (ContainerTransactionBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ContainerTransactionBean createContainerTransaction() {
      ContainerTransactionBeanImpl _val = new ContainerTransactionBeanImpl(this, -1);

      try {
         this.addContainerTransaction(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyContainerTransaction(ContainerTransactionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         ContainerTransactionBean[] _old = this.getContainerTransactions();
         ContainerTransactionBean[] _new = (ContainerTransactionBean[])((ContainerTransactionBean[])this._getHelper()._removeElement(_old, ContainerTransactionBean.class, param0));
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
               this.setContainerTransactions(_new);
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

   public void addInterceptorBinding(InterceptorBindingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         InterceptorBindingBean[] _new;
         if (this._isSet(3)) {
            _new = (InterceptorBindingBean[])((InterceptorBindingBean[])this._getHelper()._extendArray(this.getInterceptorBindings(), InterceptorBindingBean.class, param0));
         } else {
            _new = new InterceptorBindingBean[]{param0};
         }

         try {
            this.setInterceptorBindings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InterceptorBindingBean[] getInterceptorBindings() {
      return this._InterceptorBindings;
   }

   public boolean isInterceptorBindingsInherited() {
      return false;
   }

   public boolean isInterceptorBindingsSet() {
      return this._isSet(3);
   }

   public void removeInterceptorBinding(InterceptorBindingBean param0) {
      this.destroyInterceptorBinding(param0);
   }

   public void setInterceptorBindings(InterceptorBindingBean[] param0) throws InvalidAttributeValueException {
      InterceptorBindingBean[] param0 = param0 == null ? new InterceptorBindingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InterceptorBindingBean[] _oldVal = this._InterceptorBindings;
      this._InterceptorBindings = (InterceptorBindingBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public InterceptorBindingBean createInterceptorBinding() {
      InterceptorBindingBeanImpl _val = new InterceptorBindingBeanImpl(this, -1);

      try {
         this.addInterceptorBinding(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInterceptorBinding(InterceptorBindingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         InterceptorBindingBean[] _old = this.getInterceptorBindings();
         InterceptorBindingBean[] _new = (InterceptorBindingBean[])((InterceptorBindingBean[])this._getHelper()._removeElement(_old, InterceptorBindingBean.class, param0));
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
               this.setInterceptorBindings(_new);
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

   public void addMessageDestination(MessageDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         MessageDestinationBean[] _new;
         if (this._isSet(4)) {
            _new = (MessageDestinationBean[])((MessageDestinationBean[])this._getHelper()._extendArray(this.getMessageDestinations(), MessageDestinationBean.class, param0));
         } else {
            _new = new MessageDestinationBean[]{param0};
         }

         try {
            this.setMessageDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDestinationBean[] getMessageDestinations() {
      return this._MessageDestinations;
   }

   public boolean isMessageDestinationsInherited() {
      return false;
   }

   public boolean isMessageDestinationsSet() {
      return this._isSet(4);
   }

   public void removeMessageDestination(MessageDestinationBean param0) {
      this.destroyMessageDestination(param0);
   }

   public void setMessageDestinations(MessageDestinationBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationBean[] param0 = param0 == null ? new MessageDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationBean[] _oldVal = this._MessageDestinations;
      this._MessageDestinations = (MessageDestinationBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public MessageDestinationBean createMessageDestination() {
      MessageDestinationBeanImpl _val = new MessageDestinationBeanImpl(this, -1);

      try {
         this.addMessageDestination(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMessageDestination(MessageDestinationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         MessageDestinationBean[] _old = this.getMessageDestinations();
         MessageDestinationBean[] _new = (MessageDestinationBean[])((MessageDestinationBean[])this._getHelper()._removeElement(_old, MessageDestinationBean.class, param0));
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
               this.setMessageDestinations(_new);
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

   public ExcludeListBean getExcludeList() {
      return this._ExcludeList;
   }

   public boolean isExcludeListInherited() {
      return false;
   }

   public boolean isExcludeListSet() {
      return this._isSet(5);
   }

   public void setExcludeList(ExcludeListBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getExcludeList() != null && param0 != this.getExcludeList()) {
         throw new BeanAlreadyExistsException(this.getExcludeList() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ExcludeListBean _oldVal = this._ExcludeList;
         this._ExcludeList = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public ExcludeListBean createExcludeList() {
      ExcludeListBeanImpl _val = new ExcludeListBeanImpl(this, -1);

      try {
         this.setExcludeList(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExcludeList(ExcludeListBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ExcludeList;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setExcludeList((ExcludeListBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addApplicationException(ApplicationExceptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         ApplicationExceptionBean[] _new;
         if (this._isSet(6)) {
            _new = (ApplicationExceptionBean[])((ApplicationExceptionBean[])this._getHelper()._extendArray(this.getApplicationExceptions(), ApplicationExceptionBean.class, param0));
         } else {
            _new = new ApplicationExceptionBean[]{param0};
         }

         try {
            this.setApplicationExceptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ApplicationExceptionBean[] getApplicationExceptions() {
      return this._ApplicationExceptions;
   }

   public boolean isApplicationExceptionsInherited() {
      return false;
   }

   public boolean isApplicationExceptionsSet() {
      return this._isSet(6);
   }

   public void removeApplicationException(ApplicationExceptionBean param0) {
      this.destroyApplicationException(param0);
   }

   public void setApplicationExceptions(ApplicationExceptionBean[] param0) throws InvalidAttributeValueException {
      ApplicationExceptionBean[] param0 = param0 == null ? new ApplicationExceptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ApplicationExceptionBean[] _oldVal = this._ApplicationExceptions;
      this._ApplicationExceptions = (ApplicationExceptionBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public ApplicationExceptionBean createApplicationException() {
      ApplicationExceptionBeanImpl _val = new ApplicationExceptionBeanImpl(this, -1);

      try {
         this.addApplicationException(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyApplicationException(ApplicationExceptionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         ApplicationExceptionBean[] _old = this.getApplicationExceptions();
         ApplicationExceptionBean[] _new = (ApplicationExceptionBean[])((ApplicationExceptionBean[])this._getHelper()._removeElement(_old, ApplicationExceptionBean.class, param0));
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
               this.setApplicationExceptions(_new);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._ApplicationExceptions = new ApplicationExceptionBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._ContainerTransactions = new ContainerTransactionBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._ExcludeList = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._InterceptorBindings = new InterceptorBindingBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._MessageDestinations = new MessageDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._MethodPermissions = new MethodPermissionBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._SecurityRoles = new SecurityRoleBean[0];
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
                  return 7;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            default:
               break;
            case 12:
               if (s.equals("exclude-list")) {
                  return 5;
               }
               break;
            case 13:
               if (s.equals("security-role")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("method-permission")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("interceptor-binding")) {
                  return 3;
               }

               if (s.equals("message-destination")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("application-exception")) {
                  return 6;
               }

               if (s.equals("container-transaction")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new SecurityRoleBeanImpl.SchemaHelper2();
            case 1:
               return new MethodPermissionBeanImpl.SchemaHelper2();
            case 2:
               return new ContainerTransactionBeanImpl.SchemaHelper2();
            case 3:
               return new InterceptorBindingBeanImpl.SchemaHelper2();
            case 4:
               return new MessageDestinationBeanImpl.SchemaHelper2();
            case 5:
               return new ExcludeListBeanImpl.SchemaHelper2();
            case 6:
               return new ApplicationExceptionBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "security-role";
            case 1:
               return "method-permission";
            case 2:
               return "container-transaction";
            case 3:
               return "interceptor-binding";
            case 4:
               return "message-destination";
            case 5:
               return "exclude-list";
            case 6:
               return "application-exception";
            case 7:
               return "id";
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
            default:
               return super.isArray(propIndex);
            case 6:
               return true;
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
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AssemblyDescriptorBeanImpl bean;

      protected Helper(AssemblyDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SecurityRoles";
            case 1:
               return "MethodPermissions";
            case 2:
               return "ContainerTransactions";
            case 3:
               return "InterceptorBindings";
            case 4:
               return "MessageDestinations";
            case 5:
               return "ExcludeList";
            case 6:
               return "ApplicationExceptions";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationExceptions")) {
            return 6;
         } else if (propName.equals("ContainerTransactions")) {
            return 2;
         } else if (propName.equals("ExcludeList")) {
            return 5;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("InterceptorBindings")) {
            return 3;
         } else if (propName.equals("MessageDestinations")) {
            return 4;
         } else if (propName.equals("MethodPermissions")) {
            return 1;
         } else {
            return propName.equals("SecurityRoles") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getApplicationExceptions()));
         iterators.add(new ArrayIterator(this.bean.getContainerTransactions()));
         if (this.bean.getExcludeList() != null) {
            iterators.add(new ArrayIterator(new ExcludeListBean[]{this.bean.getExcludeList()}));
         }

         iterators.add(new ArrayIterator(this.bean.getInterceptorBindings()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinations()));
         iterators.add(new ArrayIterator(this.bean.getMethodPermissions()));
         iterators.add(new ArrayIterator(this.bean.getSecurityRoles()));
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
            for(i = 0; i < this.bean.getApplicationExceptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getApplicationExceptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getContainerTransactions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getContainerTransactions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getExcludeList());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getInterceptorBindings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInterceptorBindings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMethodPermissions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMethodPermissions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityRoles().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoles()[i]);
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
            AssemblyDescriptorBeanImpl otherTyped = (AssemblyDescriptorBeanImpl)other;
            this.computeChildDiff("ApplicationExceptions", this.bean.getApplicationExceptions(), otherTyped.getApplicationExceptions(), false);
            this.computeChildDiff("ContainerTransactions", this.bean.getContainerTransactions(), otherTyped.getContainerTransactions(), false);
            this.computeChildDiff("ExcludeList", this.bean.getExcludeList(), otherTyped.getExcludeList(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InterceptorBindings", this.bean.getInterceptorBindings(), otherTyped.getInterceptorBindings(), false);
            this.computeChildDiff("MessageDestinations", this.bean.getMessageDestinations(), otherTyped.getMessageDestinations(), false);
            this.computeChildDiff("MethodPermissions", this.bean.getMethodPermissions(), otherTyped.getMethodPermissions(), false);
            this.computeChildDiff("SecurityRoles", this.bean.getSecurityRoles(), otherTyped.getSecurityRoles(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AssemblyDescriptorBeanImpl original = (AssemblyDescriptorBeanImpl)event.getSourceBean();
            AssemblyDescriptorBeanImpl proposed = (AssemblyDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationExceptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addApplicationException((ApplicationExceptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeApplicationException((ApplicationExceptionBean)update.getRemovedObject());
                  }

                  if (original.getApplicationExceptions() == null || original.getApplicationExceptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("ContainerTransactions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addContainerTransaction((ContainerTransactionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeContainerTransaction((ContainerTransactionBean)update.getRemovedObject());
                  }

                  if (original.getContainerTransactions() == null || original.getContainerTransactions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("ExcludeList")) {
                  if (type == 2) {
                     original.setExcludeList((ExcludeListBean)this.createCopy((AbstractDescriptorBean)proposed.getExcludeList()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ExcludeList", (DescriptorBean)original.getExcludeList());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("InterceptorBindings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInterceptorBinding((InterceptorBindingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInterceptorBinding((InterceptorBindingBean)update.getRemovedObject());
                  }

                  if (original.getInterceptorBindings() == null || original.getInterceptorBindings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("MessageDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDestination((MessageDestinationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDestination((MessageDestinationBean)update.getRemovedObject());
                  }

                  if (original.getMessageDestinations() == null || original.getMessageDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("MethodPermissions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMethodPermission((MethodPermissionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMethodPermission((MethodPermissionBean)update.getRemovedObject());
                  }

                  if (original.getMethodPermissions() == null || original.getMethodPermissions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("SecurityRoles")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityRole((SecurityRoleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityRole((SecurityRoleBean)update.getRemovedObject());
                  }

                  if (original.getSecurityRoles() == null || original.getSecurityRoles().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            AssemblyDescriptorBeanImpl copy = (AssemblyDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ApplicationExceptions")) && this.bean.isApplicationExceptionsSet() && !copy._isSet(6)) {
               ApplicationExceptionBean[] oldApplicationExceptions = this.bean.getApplicationExceptions();
               ApplicationExceptionBean[] newApplicationExceptions = new ApplicationExceptionBean[oldApplicationExceptions.length];

               for(i = 0; i < newApplicationExceptions.length; ++i) {
                  newApplicationExceptions[i] = (ApplicationExceptionBean)((ApplicationExceptionBean)this.createCopy((AbstractDescriptorBean)oldApplicationExceptions[i], includeObsolete));
               }

               copy.setApplicationExceptions(newApplicationExceptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ContainerTransactions")) && this.bean.isContainerTransactionsSet() && !copy._isSet(2)) {
               ContainerTransactionBean[] oldContainerTransactions = this.bean.getContainerTransactions();
               ContainerTransactionBean[] newContainerTransactions = new ContainerTransactionBean[oldContainerTransactions.length];

               for(i = 0; i < newContainerTransactions.length; ++i) {
                  newContainerTransactions[i] = (ContainerTransactionBean)((ContainerTransactionBean)this.createCopy((AbstractDescriptorBean)oldContainerTransactions[i], includeObsolete));
               }

               copy.setContainerTransactions(newContainerTransactions);
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludeList")) && this.bean.isExcludeListSet() && !copy._isSet(5)) {
               Object o = this.bean.getExcludeList();
               copy.setExcludeList((ExcludeListBean)null);
               copy.setExcludeList(o == null ? null : (ExcludeListBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptorBindings")) && this.bean.isInterceptorBindingsSet() && !copy._isSet(3)) {
               InterceptorBindingBean[] oldInterceptorBindings = this.bean.getInterceptorBindings();
               InterceptorBindingBean[] newInterceptorBindings = new InterceptorBindingBean[oldInterceptorBindings.length];

               for(i = 0; i < newInterceptorBindings.length; ++i) {
                  newInterceptorBindings[i] = (InterceptorBindingBean)((InterceptorBindingBean)this.createCopy((AbstractDescriptorBean)oldInterceptorBindings[i], includeObsolete));
               }

               copy.setInterceptorBindings(newInterceptorBindings);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinations")) && this.bean.isMessageDestinationsSet() && !copy._isSet(4)) {
               MessageDestinationBean[] oldMessageDestinations = this.bean.getMessageDestinations();
               MessageDestinationBean[] newMessageDestinations = new MessageDestinationBean[oldMessageDestinations.length];

               for(i = 0; i < newMessageDestinations.length; ++i) {
                  newMessageDestinations[i] = (MessageDestinationBean)((MessageDestinationBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinations[i], includeObsolete));
               }

               copy.setMessageDestinations(newMessageDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("MethodPermissions")) && this.bean.isMethodPermissionsSet() && !copy._isSet(1)) {
               MethodPermissionBean[] oldMethodPermissions = this.bean.getMethodPermissions();
               MethodPermissionBean[] newMethodPermissions = new MethodPermissionBean[oldMethodPermissions.length];

               for(i = 0; i < newMethodPermissions.length; ++i) {
                  newMethodPermissions[i] = (MethodPermissionBean)((MethodPermissionBean)this.createCopy((AbstractDescriptorBean)oldMethodPermissions[i], includeObsolete));
               }

               copy.setMethodPermissions(newMethodPermissions);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoles")) && this.bean.isSecurityRolesSet() && !copy._isSet(0)) {
               SecurityRoleBean[] oldSecurityRoles = this.bean.getSecurityRoles();
               SecurityRoleBean[] newSecurityRoles = new SecurityRoleBean[oldSecurityRoles.length];

               for(i = 0; i < newSecurityRoles.length; ++i) {
                  newSecurityRoles[i] = (SecurityRoleBean)((SecurityRoleBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoles[i], includeObsolete));
               }

               copy.setSecurityRoles(newSecurityRoles);
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
         this.inferSubTree(this.bean.getApplicationExceptions(), clazz, annotation);
         this.inferSubTree(this.bean.getContainerTransactions(), clazz, annotation);
         this.inferSubTree(this.bean.getExcludeList(), clazz, annotation);
         this.inferSubTree(this.bean.getInterceptorBindings(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getMethodPermissions(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoles(), clazz, annotation);
      }
   }
}
