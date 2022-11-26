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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WeblogicEjbJarBeanDescriptorValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicEjbJarBeanImpl extends AbstractDescriptorBean implements WeblogicEjbJarBean, Serializable {
   private CdiDescriptorBean _CdiDescriptor;
   private CoherenceClusterRefBean _CoherenceClusterRef;
   private String _ComponentFactoryClassName;
   private String _Description;
   private String[] _DisableWarnings;
   private boolean _EnableBeanClassRedeploy;
   private String _Id;
   private IdempotentMethodsBean _IdempotentMethods;
   private ManagedExecutorServiceBean[] _ManagedExecutorServices;
   private ManagedScheduledExecutorServiceBean[] _ManagedScheduledExecutorServices;
   private ManagedThreadFactoryBean[] _ManagedThreadFactories;
   private MessageDestinationDescriptorBean[] _MessageDestinationDescriptors;
   private RetryMethodsOnRollbackBean[] _RetryMethodsOnRollbacks;
   private RunAsRoleAssignmentBean[] _RunAsRoleAssignments;
   private SecurityPermissionBean _SecurityPermission;
   private SecurityRoleAssignmentBean[] _SecurityRoleAssignments;
   private SkipStateReplicationMethodsBean _SkipStateReplicationMethods;
   private String _TimerImplementation;
   private TransactionIsolationBean[] _TransactionIsolations;
   private String _Version;
   private WeblogicCompatibilityBean _WeblogicCompatibility;
   private WeblogicEnterpriseBeanBean[] _WeblogicEnterpriseBeans;
   private WorkManagerBean[] _WorkManagers;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicEjbJarBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicEjbJarBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicEjbJarBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addWeblogicEnterpriseBean(WeblogicEnterpriseBeanBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         WeblogicEnterpriseBeanBean[] _new;
         if (this._isSet(1)) {
            _new = (WeblogicEnterpriseBeanBean[])((WeblogicEnterpriseBeanBean[])this._getHelper()._extendArray(this.getWeblogicEnterpriseBeans(), WeblogicEnterpriseBeanBean.class, param0));
         } else {
            _new = new WeblogicEnterpriseBeanBean[]{param0};
         }

         try {
            this.setWeblogicEnterpriseBeans(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WeblogicEnterpriseBeanBean[] getWeblogicEnterpriseBeans() {
      return this._WeblogicEnterpriseBeans;
   }

   public boolean isWeblogicEnterpriseBeansInherited() {
      return false;
   }

   public boolean isWeblogicEnterpriseBeansSet() {
      return this._isSet(1);
   }

   public void removeWeblogicEnterpriseBean(WeblogicEnterpriseBeanBean param0) {
      this.destroyWeblogicEnterpriseBean(param0);
   }

   public void setWeblogicEnterpriseBeans(WeblogicEnterpriseBeanBean[] param0) throws InvalidAttributeValueException {
      WeblogicEnterpriseBeanBean[] param0 = param0 == null ? new WeblogicEnterpriseBeanBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WeblogicEnterpriseBeanBean[] _oldVal = this._WeblogicEnterpriseBeans;
      this._WeblogicEnterpriseBeans = (WeblogicEnterpriseBeanBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public WeblogicEnterpriseBeanBean createWeblogicEnterpriseBean() {
      WeblogicEnterpriseBeanBeanImpl _val = new WeblogicEnterpriseBeanBeanImpl(this, -1);

      try {
         this.addWeblogicEnterpriseBean(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWeblogicEnterpriseBean(WeblogicEnterpriseBeanBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         WeblogicEnterpriseBeanBean[] _old = this.getWeblogicEnterpriseBeans();
         WeblogicEnterpriseBeanBean[] _new = (WeblogicEnterpriseBeanBean[])((WeblogicEnterpriseBeanBean[])this._getHelper()._removeElement(_old, WeblogicEnterpriseBeanBean.class, param0));
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
               this.setWeblogicEnterpriseBeans(_new);
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

   public WeblogicEnterpriseBeanBean lookupWeblogicEnterpriseBean(String param0) {
      Object[] aary = (Object[])this._WeblogicEnterpriseBeans;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WeblogicEnterpriseBeanBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WeblogicEnterpriseBeanBeanImpl)it.previous();
      } while(!bean.getEjbName().equals(param0));

      return bean;
   }

   public void addSecurityRoleAssignment(SecurityRoleAssignmentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         SecurityRoleAssignmentBean[] _new;
         if (this._isSet(2)) {
            _new = (SecurityRoleAssignmentBean[])((SecurityRoleAssignmentBean[])this._getHelper()._extendArray(this.getSecurityRoleAssignments(), SecurityRoleAssignmentBean.class, param0));
         } else {
            _new = new SecurityRoleAssignmentBean[]{param0};
         }

         try {
            this.setSecurityRoleAssignments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityRoleAssignmentBean[] getSecurityRoleAssignments() {
      return this._SecurityRoleAssignments;
   }

   public boolean isSecurityRoleAssignmentsInherited() {
      return false;
   }

   public boolean isSecurityRoleAssignmentsSet() {
      return this._isSet(2);
   }

   public void removeSecurityRoleAssignment(SecurityRoleAssignmentBean param0) {
      this.destroySecurityRoleAssignment(param0);
   }

   public void setSecurityRoleAssignments(SecurityRoleAssignmentBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleAssignmentBean[] param0 = param0 == null ? new SecurityRoleAssignmentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleAssignmentBean[] _oldVal = this._SecurityRoleAssignments;
      this._SecurityRoleAssignments = (SecurityRoleAssignmentBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public SecurityRoleAssignmentBean createSecurityRoleAssignment() {
      SecurityRoleAssignmentBeanImpl _val = new SecurityRoleAssignmentBeanImpl(this, -1);

      try {
         this.addSecurityRoleAssignment(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityRoleAssignment(SecurityRoleAssignmentBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         SecurityRoleAssignmentBean[] _old = this.getSecurityRoleAssignments();
         SecurityRoleAssignmentBean[] _new = (SecurityRoleAssignmentBean[])((SecurityRoleAssignmentBean[])this._getHelper()._removeElement(_old, SecurityRoleAssignmentBean.class, param0));
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
               this.setSecurityRoleAssignments(_new);
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

   public void addRunAsRoleAssignment(RunAsRoleAssignmentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         RunAsRoleAssignmentBean[] _new;
         if (this._isSet(3)) {
            _new = (RunAsRoleAssignmentBean[])((RunAsRoleAssignmentBean[])this._getHelper()._extendArray(this.getRunAsRoleAssignments(), RunAsRoleAssignmentBean.class, param0));
         } else {
            _new = new RunAsRoleAssignmentBean[]{param0};
         }

         try {
            this.setRunAsRoleAssignments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RunAsRoleAssignmentBean[] getRunAsRoleAssignments() {
      return this._RunAsRoleAssignments;
   }

   public boolean isRunAsRoleAssignmentsInherited() {
      return false;
   }

   public boolean isRunAsRoleAssignmentsSet() {
      return this._isSet(3);
   }

   public void removeRunAsRoleAssignment(RunAsRoleAssignmentBean param0) {
      this.destroyRunAsRoleAssignment(param0);
   }

   public void setRunAsRoleAssignments(RunAsRoleAssignmentBean[] param0) throws InvalidAttributeValueException {
      RunAsRoleAssignmentBean[] param0 = param0 == null ? new RunAsRoleAssignmentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      RunAsRoleAssignmentBean[] _oldVal = this._RunAsRoleAssignments;
      this._RunAsRoleAssignments = (RunAsRoleAssignmentBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public RunAsRoleAssignmentBean createRunAsRoleAssignment() {
      RunAsRoleAssignmentBeanImpl _val = new RunAsRoleAssignmentBeanImpl(this, -1);

      try {
         this.addRunAsRoleAssignment(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRunAsRoleAssignment(RunAsRoleAssignmentBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         RunAsRoleAssignmentBean[] _old = this.getRunAsRoleAssignments();
         RunAsRoleAssignmentBean[] _new = (RunAsRoleAssignmentBean[])((RunAsRoleAssignmentBean[])this._getHelper()._removeElement(_old, RunAsRoleAssignmentBean.class, param0));
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
               this.setRunAsRoleAssignments(_new);
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

   public SecurityPermissionBean getSecurityPermission() {
      return this._SecurityPermission;
   }

   public boolean isSecurityPermissionInherited() {
      return false;
   }

   public boolean isSecurityPermissionSet() {
      return this._isSet(4);
   }

   public void setSecurityPermission(SecurityPermissionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSecurityPermission() != null && param0 != this.getSecurityPermission()) {
         throw new BeanAlreadyExistsException(this.getSecurityPermission() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SecurityPermissionBean _oldVal = this._SecurityPermission;
         this._SecurityPermission = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public SecurityPermissionBean createSecurityPermission() {
      SecurityPermissionBeanImpl _val = new SecurityPermissionBeanImpl(this, -1);

      try {
         this.setSecurityPermission(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityPermission(SecurityPermissionBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SecurityPermission;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSecurityPermission((SecurityPermissionBean)null);
               this._unSet(4);
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

   public void addTransactionIsolation(TransactionIsolationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         TransactionIsolationBean[] _new;
         if (this._isSet(5)) {
            _new = (TransactionIsolationBean[])((TransactionIsolationBean[])this._getHelper()._extendArray(this.getTransactionIsolations(), TransactionIsolationBean.class, param0));
         } else {
            _new = new TransactionIsolationBean[]{param0};
         }

         try {
            this.setTransactionIsolations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TransactionIsolationBean[] getTransactionIsolations() {
      return this._TransactionIsolations;
   }

   public boolean isTransactionIsolationsInherited() {
      return false;
   }

   public boolean isTransactionIsolationsSet() {
      return this._isSet(5);
   }

   public void removeTransactionIsolation(TransactionIsolationBean param0) {
      this.destroyTransactionIsolation(param0);
   }

   public void setTransactionIsolations(TransactionIsolationBean[] param0) throws InvalidAttributeValueException {
      TransactionIsolationBean[] param0 = param0 == null ? new TransactionIsolationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TransactionIsolationBean[] _oldVal = this._TransactionIsolations;
      this._TransactionIsolations = (TransactionIsolationBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public TransactionIsolationBean createTransactionIsolation() {
      TransactionIsolationBeanImpl _val = new TransactionIsolationBeanImpl(this, -1);

      try {
         this.addTransactionIsolation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTransactionIsolation(TransactionIsolationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         TransactionIsolationBean[] _old = this.getTransactionIsolations();
         TransactionIsolationBean[] _new = (TransactionIsolationBean[])((TransactionIsolationBean[])this._getHelper()._removeElement(_old, TransactionIsolationBean.class, param0));
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
               this.setTransactionIsolations(_new);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         MessageDestinationDescriptorBean[] _new;
         if (this._isSet(6)) {
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
      return this._isSet(6);
   }

   public void removeMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      this.destroyMessageDestinationDescriptor(param0);
   }

   public void setMessageDestinationDescriptors(MessageDestinationDescriptorBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationDescriptorBean[] param0 = param0 == null ? new MessageDestinationDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationDescriptorBean[] _oldVal = this._MessageDestinationDescriptors;
      this._MessageDestinationDescriptors = (MessageDestinationDescriptorBean[])param0;
      this._postSet(6, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 6);
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

   public IdempotentMethodsBean getIdempotentMethods() {
      return this._IdempotentMethods;
   }

   public boolean isIdempotentMethodsInherited() {
      return false;
   }

   public boolean isIdempotentMethodsSet() {
      return this._isSet(7);
   }

   public void setIdempotentMethods(IdempotentMethodsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getIdempotentMethods() != null && param0 != this.getIdempotentMethods()) {
         throw new BeanAlreadyExistsException(this.getIdempotentMethods() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 7)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         IdempotentMethodsBean _oldVal = this._IdempotentMethods;
         this._IdempotentMethods = param0;
         this._postSet(7, _oldVal, param0);
      }
   }

   public IdempotentMethodsBean createIdempotentMethods() {
      IdempotentMethodsBeanImpl _val = new IdempotentMethodsBeanImpl(this, -1);

      try {
         this.setIdempotentMethods(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyIdempotentMethods(IdempotentMethodsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._IdempotentMethods;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setIdempotentMethods((IdempotentMethodsBean)null);
               this._unSet(7);
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

   public SkipStateReplicationMethodsBean getSkipStateReplicationMethods() {
      return this._SkipStateReplicationMethods;
   }

   public boolean isSkipStateReplicationMethodsInherited() {
      return false;
   }

   public boolean isSkipStateReplicationMethodsSet() {
      return this._isSet(8);
   }

   public void setSkipStateReplicationMethods(SkipStateReplicationMethodsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSkipStateReplicationMethods() != null && param0 != this.getSkipStateReplicationMethods()) {
         throw new BeanAlreadyExistsException(this.getSkipStateReplicationMethods() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SkipStateReplicationMethodsBean _oldVal = this._SkipStateReplicationMethods;
         this._SkipStateReplicationMethods = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public SkipStateReplicationMethodsBean createSkipStateReplicationMethods() {
      SkipStateReplicationMethodsBeanImpl _val = new SkipStateReplicationMethodsBeanImpl(this, -1);

      try {
         this.setSkipStateReplicationMethods(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySkipStateReplicationMethods(SkipStateReplicationMethodsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SkipStateReplicationMethods;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSkipStateReplicationMethods((SkipStateReplicationMethodsBean)null);
               this._unSet(8);
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

   public void addRetryMethodsOnRollback(RetryMethodsOnRollbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         RetryMethodsOnRollbackBean[] _new;
         if (this._isSet(9)) {
            _new = (RetryMethodsOnRollbackBean[])((RetryMethodsOnRollbackBean[])this._getHelper()._extendArray(this.getRetryMethodsOnRollbacks(), RetryMethodsOnRollbackBean.class, param0));
         } else {
            _new = new RetryMethodsOnRollbackBean[]{param0};
         }

         try {
            this.setRetryMethodsOnRollbacks(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RetryMethodsOnRollbackBean[] getRetryMethodsOnRollbacks() {
      return this._RetryMethodsOnRollbacks;
   }

   public boolean isRetryMethodsOnRollbacksInherited() {
      return false;
   }

   public boolean isRetryMethodsOnRollbacksSet() {
      return this._isSet(9);
   }

   public void removeRetryMethodsOnRollback(RetryMethodsOnRollbackBean param0) {
      this.destroyRetryMethodsOnRollback(param0);
   }

   public void setRetryMethodsOnRollbacks(RetryMethodsOnRollbackBean[] param0) throws InvalidAttributeValueException {
      RetryMethodsOnRollbackBean[] param0 = param0 == null ? new RetryMethodsOnRollbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      RetryMethodsOnRollbackBean[] _oldVal = this._RetryMethodsOnRollbacks;
      this._RetryMethodsOnRollbacks = (RetryMethodsOnRollbackBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public RetryMethodsOnRollbackBean createRetryMethodsOnRollback() {
      RetryMethodsOnRollbackBeanImpl _val = new RetryMethodsOnRollbackBeanImpl(this, -1);

      try {
         this.addRetryMethodsOnRollback(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRetryMethodsOnRollback(RetryMethodsOnRollbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         RetryMethodsOnRollbackBean[] _old = this.getRetryMethodsOnRollbacks();
         RetryMethodsOnRollbackBean[] _new = (RetryMethodsOnRollbackBean[])((RetryMethodsOnRollbackBean[])this._getHelper()._removeElement(_old, RetryMethodsOnRollbackBean.class, param0));
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
               this.setRetryMethodsOnRollbacks(_new);
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

   public boolean isEnableBeanClassRedeploy() {
      return this._EnableBeanClassRedeploy;
   }

   public boolean isEnableBeanClassRedeployInherited() {
      return false;
   }

   public boolean isEnableBeanClassRedeploySet() {
      return this._isSet(10);
   }

   public void setEnableBeanClassRedeploy(boolean param0) {
      boolean _oldVal = this._EnableBeanClassRedeploy;
      this._EnableBeanClassRedeploy = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getTimerImplementation() {
      return this._TimerImplementation;
   }

   public boolean isTimerImplementationInherited() {
      return false;
   }

   public boolean isTimerImplementationSet() {
      return this._isSet(11);
   }

   public void setTimerImplementation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Local", "Clustered"};
      param0 = LegalChecks.checkInEnum("TimerImplementation", param0, _set);
      String _oldVal = this._TimerImplementation;
      this._TimerImplementation = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String[] getDisableWarnings() {
      return this._DisableWarnings;
   }

   public boolean isDisableWarningsInherited() {
      return false;
   }

   public boolean isDisableWarningsSet() {
      return this._isSet(12);
   }

   public void addDisableWarning(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(12)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDisableWarnings(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDisableWarnings(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDisableWarning(String param0) {
      String[] _old = this.getDisableWarnings();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDisableWarnings(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDisableWarnings(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      WeblogicEjbJarBeanDescriptorValidator.validateDisableWarnings(param0);
      String[] _oldVal = this._DisableWarnings;
      this._DisableWarnings = param0;
      this._postSet(12, _oldVal, param0);
   }

   public void addWorkManager(WorkManagerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         WorkManagerBean[] _new;
         if (this._isSet(13)) {
            _new = (WorkManagerBean[])((WorkManagerBean[])this._getHelper()._extendArray(this.getWorkManagers(), WorkManagerBean.class, param0));
         } else {
            _new = new WorkManagerBean[]{param0};
         }

         try {
            this.setWorkManagers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WorkManagerBean[] getWorkManagers() {
      return this._WorkManagers;
   }

   public boolean isWorkManagersInherited() {
      return false;
   }

   public boolean isWorkManagersSet() {
      return this._isSet(13);
   }

   public void removeWorkManager(WorkManagerBean param0) {
      this.destroyWorkManager(param0);
   }

   public void setWorkManagers(WorkManagerBean[] param0) throws InvalidAttributeValueException {
      WorkManagerBean[] param0 = param0 == null ? new WorkManagerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WorkManagerBean[] _oldVal = this._WorkManagers;
      this._WorkManagers = (WorkManagerBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public WorkManagerBean createWorkManager() {
      WorkManagerBeanImpl _val = new WorkManagerBeanImpl(this, -1);

      try {
         this.addWorkManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWorkManager(WorkManagerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         WorkManagerBean[] _old = this.getWorkManagers();
         WorkManagerBean[] _new = (WorkManagerBean[])((WorkManagerBean[])this._getHelper()._removeElement(_old, WorkManagerBean.class, param0));
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
               this.setWorkManagers(_new);
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

   public void addManagedExecutorService(ManagedExecutorServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         ManagedExecutorServiceBean[] _new;
         if (this._isSet(14)) {
            _new = (ManagedExecutorServiceBean[])((ManagedExecutorServiceBean[])this._getHelper()._extendArray(this.getManagedExecutorServices(), ManagedExecutorServiceBean.class, param0));
         } else {
            _new = new ManagedExecutorServiceBean[]{param0};
         }

         try {
            this.setManagedExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedExecutorServiceBean[] getManagedExecutorServices() {
      return this._ManagedExecutorServices;
   }

   public boolean isManagedExecutorServicesInherited() {
      return false;
   }

   public boolean isManagedExecutorServicesSet() {
      return this._isSet(14);
   }

   public void removeManagedExecutorService(ManagedExecutorServiceBean param0) {
      this.destroyManagedExecutorService(param0);
   }

   public void setManagedExecutorServices(ManagedExecutorServiceBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceBean[] param0 = param0 == null ? new ManagedExecutorServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedExecutorServiceBean[] _oldVal = this._ManagedExecutorServices;
      this._ManagedExecutorServices = (ManagedExecutorServiceBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public ManagedExecutorServiceBean createManagedExecutorService() {
      ManagedExecutorServiceBeanImpl _val = new ManagedExecutorServiceBeanImpl(this, -1);

      try {
         this.addManagedExecutorService(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManagedExecutorService(ManagedExecutorServiceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         ManagedExecutorServiceBean[] _old = this.getManagedExecutorServices();
         ManagedExecutorServiceBean[] _new = (ManagedExecutorServiceBean[])((ManagedExecutorServiceBean[])this._getHelper()._removeElement(_old, ManagedExecutorServiceBean.class, param0));
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
               this.setManagedExecutorServices(_new);
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

   public void addManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         ManagedScheduledExecutorServiceBean[] _new;
         if (this._isSet(15)) {
            _new = (ManagedScheduledExecutorServiceBean[])((ManagedScheduledExecutorServiceBean[])this._getHelper()._extendArray(this.getManagedScheduledExecutorServices(), ManagedScheduledExecutorServiceBean.class, param0));
         } else {
            _new = new ManagedScheduledExecutorServiceBean[]{param0};
         }

         try {
            this.setManagedScheduledExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorServices() {
      return this._ManagedScheduledExecutorServices;
   }

   public boolean isManagedScheduledExecutorServicesInherited() {
      return false;
   }

   public boolean isManagedScheduledExecutorServicesSet() {
      return this._isSet(15);
   }

   public void removeManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      this.destroyManagedScheduledExecutorService(param0);
   }

   public void setManagedScheduledExecutorServices(ManagedScheduledExecutorServiceBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedScheduledExecutorServiceBean[] _oldVal = this._ManagedScheduledExecutorServices;
      this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public ManagedScheduledExecutorServiceBean createManagedScheduledExecutorService() {
      ManagedScheduledExecutorServiceBeanImpl _val = new ManagedScheduledExecutorServiceBeanImpl(this, -1);

      try {
         this.addManagedScheduledExecutorService(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         ManagedScheduledExecutorServiceBean[] _old = this.getManagedScheduledExecutorServices();
         ManagedScheduledExecutorServiceBean[] _new = (ManagedScheduledExecutorServiceBean[])((ManagedScheduledExecutorServiceBean[])this._getHelper()._removeElement(_old, ManagedScheduledExecutorServiceBean.class, param0));
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
               this.setManagedScheduledExecutorServices(_new);
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

   public void addManagedThreadFactory(ManagedThreadFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         ManagedThreadFactoryBean[] _new;
         if (this._isSet(16)) {
            _new = (ManagedThreadFactoryBean[])((ManagedThreadFactoryBean[])this._getHelper()._extendArray(this.getManagedThreadFactories(), ManagedThreadFactoryBean.class, param0));
         } else {
            _new = new ManagedThreadFactoryBean[]{param0};
         }

         try {
            this.setManagedThreadFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedThreadFactoryBean[] getManagedThreadFactories() {
      return this._ManagedThreadFactories;
   }

   public boolean isManagedThreadFactoriesInherited() {
      return false;
   }

   public boolean isManagedThreadFactoriesSet() {
      return this._isSet(16);
   }

   public void removeManagedThreadFactory(ManagedThreadFactoryBean param0) {
      this.destroyManagedThreadFactory(param0);
   }

   public void setManagedThreadFactories(ManagedThreadFactoryBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryBean[] param0 = param0 == null ? new ManagedThreadFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedThreadFactoryBean[] _oldVal = this._ManagedThreadFactories;
      this._ManagedThreadFactories = (ManagedThreadFactoryBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public ManagedThreadFactoryBean createManagedThreadFactory() {
      ManagedThreadFactoryBeanImpl _val = new ManagedThreadFactoryBeanImpl(this, -1);

      try {
         this.addManagedThreadFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManagedThreadFactory(ManagedThreadFactoryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         ManagedThreadFactoryBean[] _old = this.getManagedThreadFactories();
         ManagedThreadFactoryBean[] _new = (ManagedThreadFactoryBean[])((ManagedThreadFactoryBean[])this._getHelper()._removeElement(_old, ManagedThreadFactoryBean.class, param0));
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
               this.setManagedThreadFactories(_new);
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

   public String getComponentFactoryClassName() {
      return this._ComponentFactoryClassName;
   }

   public boolean isComponentFactoryClassNameInherited() {
      return false;
   }

   public boolean isComponentFactoryClassNameSet() {
      return this._isSet(17);
   }

   public void setComponentFactoryClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ComponentFactoryClassName;
      this._ComponentFactoryClassName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public WeblogicCompatibilityBean getWeblogicCompatibility() {
      return this._WeblogicCompatibility;
   }

   public boolean isWeblogicCompatibilityInherited() {
      return false;
   }

   public boolean isWeblogicCompatibilitySet() {
      return this._isSet(18) || this._isAnythingSet((AbstractDescriptorBean)this.getWeblogicCompatibility());
   }

   public void setWeblogicCompatibility(WeblogicCompatibilityBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 18)) {
         this._postCreate(_child);
      }

      WeblogicCompatibilityBean _oldVal = this._WeblogicCompatibility;
      this._WeblogicCompatibility = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(19);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(19, _oldVal, param0);
   }

   public CoherenceClusterRefBean getCoherenceClusterRef() {
      return this._CoherenceClusterRef;
   }

   public boolean isCoherenceClusterRefInherited() {
      return false;
   }

   public boolean isCoherenceClusterRefSet() {
      return this._isSet(20);
   }

   public void setCoherenceClusterRef(CoherenceClusterRefBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCoherenceClusterRef() != null && param0 != this.getCoherenceClusterRef()) {
         throw new BeanAlreadyExistsException(this.getCoherenceClusterRef() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 20)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CoherenceClusterRefBean _oldVal = this._CoherenceClusterRef;
         this._CoherenceClusterRef = param0;
         this._postSet(20, _oldVal, param0);
      }
   }

   public CoherenceClusterRefBean createCoherenceClusterRef() {
      CoherenceClusterRefBeanImpl _val = new CoherenceClusterRefBeanImpl(this, -1);

      try {
         this.setCoherenceClusterRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCoherenceClusterRef() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CoherenceClusterRef;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCoherenceClusterRef((CoherenceClusterRefBean)null);
               this._unSet(20);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(21);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(21, _oldVal, param0);
   }

   public CdiDescriptorBean getCdiDescriptor() {
      return this._CdiDescriptor;
   }

   public boolean isCdiDescriptorInherited() {
      return false;
   }

   public boolean isCdiDescriptorSet() {
      return this._isSet(22) || this._isAnythingSet((AbstractDescriptorBean)this.getCdiDescriptor());
   }

   public void setCdiDescriptor(CdiDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 22)) {
         this._postCreate(_child);
      }

      CdiDescriptorBean _oldVal = this._CdiDescriptor;
      this._CdiDescriptor = param0;
      this._postSet(22, _oldVal, param0);
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
      return super._isAnythingSet() || this.isCdiDescriptorSet() || this.isWeblogicCompatibilitySet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 22;
      }

      try {
         switch (idx) {
            case 22:
               this._CdiDescriptor = new CdiDescriptorBeanImpl(this, 22);
               this._postCreate((AbstractDescriptorBean)this._CdiDescriptor);
               if (initOne) {
                  break;
               }
            case 20:
               this._CoherenceClusterRef = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._ComponentFactoryClassName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._DisableWarnings = new String[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._IdempotentMethods = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ManagedExecutorServices = new ManagedExecutorServiceBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._ManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._ManagedThreadFactories = new ManagedThreadFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._MessageDestinationDescriptors = new MessageDestinationDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._RetryMethodsOnRollbacks = new RetryMethodsOnRollbackBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._RunAsRoleAssignments = new RunAsRoleAssignmentBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._SecurityPermission = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._SecurityRoleAssignments = new SecurityRoleAssignmentBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._SkipStateReplicationMethods = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._TimerImplementation = "Local";
               if (initOne) {
                  break;
               }
            case 5:
               this._TransactionIsolations = new TransactionIsolationBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._WeblogicCompatibility = new WeblogicCompatibilityBeanImpl(this, 18);
               this._postCreate((AbstractDescriptorBean)this._WeblogicCompatibility);
               if (initOne) {
                  break;
               }
            case 1:
               this._WeblogicEnterpriseBeans = new WeblogicEnterpriseBeanBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._WorkManagers = new WorkManagerBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._EnableBeanClassRedeploy = false;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-ejb-jar/1.2/weblogic-ejb-jar.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-ejb-jar";
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
                  return 19;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 13:
            case 16:
            case 17:
            case 23:
            case 27:
            case 29:
            case 31:
            case 32:
            case 33:
            default:
               break;
            case 7:
               if (s.equals("version")) {
                  return 21;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("work-manager")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("cdi-descriptor")) {
                  return 22;
               }
               break;
            case 15:
               if (s.equals("disable-warning")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("idempotent-methods")) {
                  return 7;
               }
               break;
            case 19:
               if (s.equals("security-permission")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("timer-implementation")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("coherence-cluster-ref")) {
                  return 20;
               }

               if (s.equals("transaction-isolation")) {
                  return 5;
               }
               break;
            case 22:
               if (s.equals("managed-thread-factory")) {
                  return 16;
               }

               if (s.equals("run-as-role-assignment")) {
                  return 3;
               }

               if (s.equals("weblogic-compatibility")) {
                  return 18;
               }
               break;
            case 24:
               if (s.equals("managed-executor-service")) {
                  return 14;
               }

               if (s.equals("security-role-assignment")) {
                  return 2;
               }

               if (s.equals("weblogic-enterprise-bean")) {
                  return 1;
               }
               break;
            case 25:
               if (s.equals("retry-methods-on-rollback")) {
                  return 9;
               }
               break;
            case 26:
               if (s.equals("enable-bean-class-redeploy")) {
                  return 10;
               }
               break;
            case 28:
               if (s.equals("component-factory-class-name")) {
                  return 17;
               }
               break;
            case 30:
               if (s.equals("message-destination-descriptor")) {
                  return 6;
               }

               if (s.equals("skip-state-replication-methods")) {
                  return 8;
               }
               break;
            case 34:
               if (s.equals("managed-scheduled-executor-service")) {
                  return 15;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new WeblogicEnterpriseBeanBeanImpl.SchemaHelper2();
            case 2:
               return new SecurityRoleAssignmentBeanImpl.SchemaHelper2();
            case 3:
               return new RunAsRoleAssignmentBeanImpl.SchemaHelper2();
            case 4:
               return new SecurityPermissionBeanImpl.SchemaHelper2();
            case 5:
               return new TransactionIsolationBeanImpl.SchemaHelper2();
            case 6:
               return new MessageDestinationDescriptorBeanImpl.SchemaHelper2();
            case 7:
               return new IdempotentMethodsBeanImpl.SchemaHelper2();
            case 8:
               return new SkipStateReplicationMethodsBeanImpl.SchemaHelper2();
            case 9:
               return new RetryMethodsOnRollbackBeanImpl.SchemaHelper2();
            case 10:
            case 11:
            case 12:
            case 17:
            case 19:
            case 21:
            default:
               return super.getSchemaHelper(propIndex);
            case 13:
               return new WorkManagerBeanImpl.SchemaHelper2();
            case 14:
               return new ManagedExecutorServiceBeanImpl.SchemaHelper2();
            case 15:
               return new ManagedScheduledExecutorServiceBeanImpl.SchemaHelper2();
            case 16:
               return new ManagedThreadFactoryBeanImpl.SchemaHelper2();
            case 18:
               return new WeblogicCompatibilityBeanImpl.SchemaHelper2();
            case 20:
               return new CoherenceClusterRefBeanImpl.SchemaHelper2();
            case 22:
               return new CdiDescriptorBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "weblogic-ejb-jar";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "weblogic-enterprise-bean";
            case 2:
               return "security-role-assignment";
            case 3:
               return "run-as-role-assignment";
            case 4:
               return "security-permission";
            case 5:
               return "transaction-isolation";
            case 6:
               return "message-destination-descriptor";
            case 7:
               return "idempotent-methods";
            case 8:
               return "skip-state-replication-methods";
            case 9:
               return "retry-methods-on-rollback";
            case 10:
               return "enable-bean-class-redeploy";
            case 11:
               return "timer-implementation";
            case 12:
               return "disable-warning";
            case 13:
               return "work-manager";
            case 14:
               return "managed-executor-service";
            case 15:
               return "managed-scheduled-executor-service";
            case 16:
               return "managed-thread-factory";
            case 17:
               return "component-factory-class-name";
            case 18:
               return "weblogic-compatibility";
            case 19:
               return "id";
            case 20:
               return "coherence-cluster-ref";
            case 21:
               return "version";
            case 22:
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
            case 7:
            case 8:
            case 10:
            case 11:
            default:
               return super.isArray(propIndex);
            case 5:
               return true;
            case 6:
               return true;
            case 9:
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
            case 11:
            case 12:
            case 17:
            case 19:
            case 21:
            default:
               return super.isBean(propIndex);
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 18:
               return true;
            case 20:
               return true;
            case 22:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
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
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicEjbJarBeanImpl bean;

      protected Helper(WeblogicEjbJarBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "WeblogicEnterpriseBeans";
            case 2:
               return "SecurityRoleAssignments";
            case 3:
               return "RunAsRoleAssignments";
            case 4:
               return "SecurityPermission";
            case 5:
               return "TransactionIsolations";
            case 6:
               return "MessageDestinationDescriptors";
            case 7:
               return "IdempotentMethods";
            case 8:
               return "SkipStateReplicationMethods";
            case 9:
               return "RetryMethodsOnRollbacks";
            case 10:
               return "EnableBeanClassRedeploy";
            case 11:
               return "TimerImplementation";
            case 12:
               return "DisableWarnings";
            case 13:
               return "WorkManagers";
            case 14:
               return "ManagedExecutorServices";
            case 15:
               return "ManagedScheduledExecutorServices";
            case 16:
               return "ManagedThreadFactories";
            case 17:
               return "ComponentFactoryClassName";
            case 18:
               return "WeblogicCompatibility";
            case 19:
               return "Id";
            case 20:
               return "CoherenceClusterRef";
            case 21:
               return "Version";
            case 22:
               return "CdiDescriptor";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CdiDescriptor")) {
            return 22;
         } else if (propName.equals("CoherenceClusterRef")) {
            return 20;
         } else if (propName.equals("ComponentFactoryClassName")) {
            return 17;
         } else if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("DisableWarnings")) {
            return 12;
         } else if (propName.equals("Id")) {
            return 19;
         } else if (propName.equals("IdempotentMethods")) {
            return 7;
         } else if (propName.equals("ManagedExecutorServices")) {
            return 14;
         } else if (propName.equals("ManagedScheduledExecutorServices")) {
            return 15;
         } else if (propName.equals("ManagedThreadFactories")) {
            return 16;
         } else if (propName.equals("MessageDestinationDescriptors")) {
            return 6;
         } else if (propName.equals("RetryMethodsOnRollbacks")) {
            return 9;
         } else if (propName.equals("RunAsRoleAssignments")) {
            return 3;
         } else if (propName.equals("SecurityPermission")) {
            return 4;
         } else if (propName.equals("SecurityRoleAssignments")) {
            return 2;
         } else if (propName.equals("SkipStateReplicationMethods")) {
            return 8;
         } else if (propName.equals("TimerImplementation")) {
            return 11;
         } else if (propName.equals("TransactionIsolations")) {
            return 5;
         } else if (propName.equals("Version")) {
            return 21;
         } else if (propName.equals("WeblogicCompatibility")) {
            return 18;
         } else if (propName.equals("WeblogicEnterpriseBeans")) {
            return 1;
         } else if (propName.equals("WorkManagers")) {
            return 13;
         } else {
            return propName.equals("EnableBeanClassRedeploy") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCdiDescriptor() != null) {
            iterators.add(new ArrayIterator(new CdiDescriptorBean[]{this.bean.getCdiDescriptor()}));
         }

         if (this.bean.getCoherenceClusterRef() != null) {
            iterators.add(new ArrayIterator(new CoherenceClusterRefBean[]{this.bean.getCoherenceClusterRef()}));
         }

         if (this.bean.getIdempotentMethods() != null) {
            iterators.add(new ArrayIterator(new IdempotentMethodsBean[]{this.bean.getIdempotentMethods()}));
         }

         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactories()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getRetryMethodsOnRollbacks()));
         iterators.add(new ArrayIterator(this.bean.getRunAsRoleAssignments()));
         if (this.bean.getSecurityPermission() != null) {
            iterators.add(new ArrayIterator(new SecurityPermissionBean[]{this.bean.getSecurityPermission()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSecurityRoleAssignments()));
         if (this.bean.getSkipStateReplicationMethods() != null) {
            iterators.add(new ArrayIterator(new SkipStateReplicationMethodsBean[]{this.bean.getSkipStateReplicationMethods()}));
         }

         iterators.add(new ArrayIterator(this.bean.getTransactionIsolations()));
         if (this.bean.getWeblogicCompatibility() != null) {
            iterators.add(new ArrayIterator(new WeblogicCompatibilityBean[]{this.bean.getWeblogicCompatibility()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWeblogicEnterpriseBeans()));
         iterators.add(new ArrayIterator(this.bean.getWorkManagers()));
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

            childValue = this.computeChildHashValue(this.bean.getCoherenceClusterRef());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isComponentFactoryClassNameSet()) {
               buf.append("ComponentFactoryClassName");
               buf.append(String.valueOf(this.bean.getComponentFactoryClassName()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isDisableWarningsSet()) {
               buf.append("DisableWarnings");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisableWarnings())));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getIdempotentMethods());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getManagedExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedScheduledExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedScheduledExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedThreadFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedThreadFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinationDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinationDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRetryMethodsOnRollbacks().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRetryMethodsOnRollbacks()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRunAsRoleAssignments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRunAsRoleAssignments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurityPermission());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityRoleAssignments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoleAssignments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSkipStateReplicationMethods());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTimerImplementationSet()) {
               buf.append("TimerImplementation");
               buf.append(String.valueOf(this.bean.getTimerImplementation()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTransactionIsolations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTransactionIsolations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = this.computeChildHashValue(this.bean.getWeblogicCompatibility());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWeblogicEnterpriseBeans().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWeblogicEnterpriseBeans()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWorkManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWorkManagers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnableBeanClassRedeploySet()) {
               buf.append("EnableBeanClassRedeploy");
               buf.append(String.valueOf(this.bean.isEnableBeanClassRedeploy()));
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
            WeblogicEjbJarBeanImpl otherTyped = (WeblogicEjbJarBeanImpl)other;
            this.computeSubDiff("CdiDescriptor", this.bean.getCdiDescriptor(), otherTyped.getCdiDescriptor());
            this.computeChildDiff("CoherenceClusterRef", this.bean.getCoherenceClusterRef(), otherTyped.getCoherenceClusterRef(), false);
            this.computeDiff("ComponentFactoryClassName", this.bean.getComponentFactoryClassName(), otherTyped.getComponentFactoryClassName(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), true);
            this.computeDiff("DisableWarnings", this.bean.getDisableWarnings(), otherTyped.getDisableWarnings(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("IdempotentMethods", this.bean.getIdempotentMethods(), otherTyped.getIdempotentMethods(), false);
            this.computeChildDiff("ManagedExecutorServices", this.bean.getManagedExecutorServices(), otherTyped.getManagedExecutorServices(), false);
            this.computeChildDiff("ManagedScheduledExecutorServices", this.bean.getManagedScheduledExecutorServices(), otherTyped.getManagedScheduledExecutorServices(), false);
            this.computeChildDiff("ManagedThreadFactories", this.bean.getManagedThreadFactories(), otherTyped.getManagedThreadFactories(), false);
            this.computeChildDiff("MessageDestinationDescriptors", this.bean.getMessageDestinationDescriptors(), otherTyped.getMessageDestinationDescriptors(), false);
            this.computeChildDiff("RetryMethodsOnRollbacks", this.bean.getRetryMethodsOnRollbacks(), otherTyped.getRetryMethodsOnRollbacks(), false);
            this.computeChildDiff("RunAsRoleAssignments", this.bean.getRunAsRoleAssignments(), otherTyped.getRunAsRoleAssignments(), false);
            this.computeChildDiff("SecurityPermission", this.bean.getSecurityPermission(), otherTyped.getSecurityPermission(), false);
            this.computeChildDiff("SecurityRoleAssignments", this.bean.getSecurityRoleAssignments(), otherTyped.getSecurityRoleAssignments(), false);
            this.computeChildDiff("SkipStateReplicationMethods", this.bean.getSkipStateReplicationMethods(), otherTyped.getSkipStateReplicationMethods(), false);
            this.computeDiff("TimerImplementation", this.bean.getTimerImplementation(), otherTyped.getTimerImplementation(), false);
            this.computeChildDiff("TransactionIsolations", this.bean.getTransactionIsolations(), otherTyped.getTransactionIsolations(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeSubDiff("WeblogicCompatibility", this.bean.getWeblogicCompatibility(), otherTyped.getWeblogicCompatibility());
            this.computeChildDiff("WeblogicEnterpriseBeans", this.bean.getWeblogicEnterpriseBeans(), otherTyped.getWeblogicEnterpriseBeans(), false);
            this.computeChildDiff("WorkManagers", this.bean.getWorkManagers(), otherTyped.getWorkManagers(), false);
            this.computeDiff("EnableBeanClassRedeploy", this.bean.isEnableBeanClassRedeploy(), otherTyped.isEnableBeanClassRedeploy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicEjbJarBeanImpl original = (WeblogicEjbJarBeanImpl)event.getSourceBean();
            WeblogicEjbJarBeanImpl proposed = (WeblogicEjbJarBeanImpl)event.getProposedBean();
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

                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("CoherenceClusterRef")) {
                  if (type == 2) {
                     original.setCoherenceClusterRef((CoherenceClusterRefBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceClusterRef()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceClusterRef", (DescriptorBean)original.getCoherenceClusterRef());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ComponentFactoryClassName")) {
                  original.setComponentFactoryClassName(proposed.getComponentFactoryClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DisableWarnings")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDisableWarning((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDisableWarning((String)update.getRemovedObject());
                  }

                  if (original.getDisableWarnings() == null || original.getDisableWarnings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("IdempotentMethods")) {
                  if (type == 2) {
                     original.setIdempotentMethods((IdempotentMethodsBean)this.createCopy((AbstractDescriptorBean)proposed.getIdempotentMethods()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("IdempotentMethods", (DescriptorBean)original.getIdempotentMethods());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ManagedExecutorServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedExecutorService((ManagedExecutorServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedExecutorService((ManagedExecutorServiceBean)update.getRemovedObject());
                  }

                  if (original.getManagedExecutorServices() == null || original.getManagedExecutorServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("ManagedScheduledExecutorServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedScheduledExecutorService((ManagedScheduledExecutorServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedScheduledExecutorService((ManagedScheduledExecutorServiceBean)update.getRemovedObject());
                  }

                  if (original.getManagedScheduledExecutorServices() == null || original.getManagedScheduledExecutorServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("ManagedThreadFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedThreadFactory((ManagedThreadFactoryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedThreadFactory((ManagedThreadFactoryBean)update.getRemovedObject());
                  }

                  if (original.getManagedThreadFactories() == null || original.getManagedThreadFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
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
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("RetryMethodsOnRollbacks")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addRetryMethodsOnRollback((RetryMethodsOnRollbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRetryMethodsOnRollback((RetryMethodsOnRollbackBean)update.getRemovedObject());
                  }

                  if (original.getRetryMethodsOnRollbacks() == null || original.getRetryMethodsOnRollbacks().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("RunAsRoleAssignments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addRunAsRoleAssignment((RunAsRoleAssignmentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRunAsRoleAssignment((RunAsRoleAssignmentBean)update.getRemovedObject());
                  }

                  if (original.getRunAsRoleAssignments() == null || original.getRunAsRoleAssignments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("SecurityPermission")) {
                  if (type == 2) {
                     original.setSecurityPermission((SecurityPermissionBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityPermission()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SecurityPermission", (DescriptorBean)original.getSecurityPermission());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SecurityRoleAssignments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityRoleAssignment((SecurityRoleAssignmentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityRoleAssignment((SecurityRoleAssignmentBean)update.getRemovedObject());
                  }

                  if (original.getSecurityRoleAssignments() == null || original.getSecurityRoleAssignments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("SkipStateReplicationMethods")) {
                  if (type == 2) {
                     original.setSkipStateReplicationMethods((SkipStateReplicationMethodsBean)this.createCopy((AbstractDescriptorBean)proposed.getSkipStateReplicationMethods()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SkipStateReplicationMethods", (DescriptorBean)original.getSkipStateReplicationMethods());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("TimerImplementation")) {
                  original.setTimerImplementation(proposed.getTimerImplementation());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("TransactionIsolations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTransactionIsolation((TransactionIsolationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTransactionIsolation((TransactionIsolationBean)update.getRemovedObject());
                  }

                  if (original.getTransactionIsolations() == null || original.getTransactionIsolations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("WeblogicCompatibility")) {
                  if (type == 2) {
                     original.setWeblogicCompatibility((WeblogicCompatibilityBean)this.createCopy((AbstractDescriptorBean)proposed.getWeblogicCompatibility()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WeblogicCompatibility", (DescriptorBean)original.getWeblogicCompatibility());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("WeblogicEnterpriseBeans")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWeblogicEnterpriseBean((WeblogicEnterpriseBeanBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWeblogicEnterpriseBean((WeblogicEnterpriseBeanBean)update.getRemovedObject());
                  }

                  if (original.getWeblogicEnterpriseBeans() == null || original.getWeblogicEnterpriseBeans().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("WorkManagers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWorkManager((WorkManagerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWorkManager((WorkManagerBean)update.getRemovedObject());
                  }

                  if (original.getWorkManagers() == null || original.getWorkManagers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("EnableBeanClassRedeploy")) {
                  original.setEnableBeanClassRedeploy(proposed.isEnableBeanClassRedeploy());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            WeblogicEjbJarBeanImpl copy = (WeblogicEjbJarBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CdiDescriptor")) && this.bean.isCdiDescriptorSet() && !copy._isSet(22)) {
               Object o = this.bean.getCdiDescriptor();
               copy.setCdiDescriptor((CdiDescriptorBean)null);
               copy.setCdiDescriptor(o == null ? null : (CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterRef")) && this.bean.isCoherenceClusterRefSet() && !copy._isSet(20)) {
               Object o = this.bean.getCoherenceClusterRef();
               copy.setCoherenceClusterRef((CoherenceClusterRefBean)null);
               copy.setCoherenceClusterRef(o == null ? null : (CoherenceClusterRefBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ComponentFactoryClassName")) && this.bean.isComponentFactoryClassNameSet()) {
               copy.setComponentFactoryClassName(this.bean.getComponentFactoryClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("DisableWarnings")) && this.bean.isDisableWarningsSet()) {
               Object o = this.bean.getDisableWarnings();
               copy.setDisableWarnings(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IdempotentMethods")) && this.bean.isIdempotentMethodsSet() && !copy._isSet(7)) {
               Object o = this.bean.getIdempotentMethods();
               copy.setIdempotentMethods((IdempotentMethodsBean)null);
               copy.setIdempotentMethods(o == null ? null : (IdempotentMethodsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServices")) && this.bean.isManagedExecutorServicesSet() && !copy._isSet(14)) {
               ManagedExecutorServiceBean[] oldManagedExecutorServices = this.bean.getManagedExecutorServices();
               ManagedExecutorServiceBean[] newManagedExecutorServices = new ManagedExecutorServiceBean[oldManagedExecutorServices.length];

               for(i = 0; i < newManagedExecutorServices.length; ++i) {
                  newManagedExecutorServices[i] = (ManagedExecutorServiceBean)((ManagedExecutorServiceBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServices[i], includeObsolete));
               }

               copy.setManagedExecutorServices(newManagedExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServices")) && this.bean.isManagedScheduledExecutorServicesSet() && !copy._isSet(15)) {
               ManagedScheduledExecutorServiceBean[] oldManagedScheduledExecutorServices = this.bean.getManagedScheduledExecutorServices();
               ManagedScheduledExecutorServiceBean[] newManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceBean[oldManagedScheduledExecutorServices.length];

               for(i = 0; i < newManagedScheduledExecutorServices.length; ++i) {
                  newManagedScheduledExecutorServices[i] = (ManagedScheduledExecutorServiceBean)((ManagedScheduledExecutorServiceBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServices[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServices(newManagedScheduledExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactories")) && this.bean.isManagedThreadFactoriesSet() && !copy._isSet(16)) {
               ManagedThreadFactoryBean[] oldManagedThreadFactories = this.bean.getManagedThreadFactories();
               ManagedThreadFactoryBean[] newManagedThreadFactories = new ManagedThreadFactoryBean[oldManagedThreadFactories.length];

               for(i = 0; i < newManagedThreadFactories.length; ++i) {
                  newManagedThreadFactories[i] = (ManagedThreadFactoryBean)((ManagedThreadFactoryBean)this.createCopy((AbstractDescriptorBean)oldManagedThreadFactories[i], includeObsolete));
               }

               copy.setManagedThreadFactories(newManagedThreadFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationDescriptors")) && this.bean.isMessageDestinationDescriptorsSet() && !copy._isSet(6)) {
               MessageDestinationDescriptorBean[] oldMessageDestinationDescriptors = this.bean.getMessageDestinationDescriptors();
               MessageDestinationDescriptorBean[] newMessageDestinationDescriptors = new MessageDestinationDescriptorBean[oldMessageDestinationDescriptors.length];

               for(i = 0; i < newMessageDestinationDescriptors.length; ++i) {
                  newMessageDestinationDescriptors[i] = (MessageDestinationDescriptorBean)((MessageDestinationDescriptorBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationDescriptors[i], includeObsolete));
               }

               copy.setMessageDestinationDescriptors(newMessageDestinationDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("RetryMethodsOnRollbacks")) && this.bean.isRetryMethodsOnRollbacksSet() && !copy._isSet(9)) {
               RetryMethodsOnRollbackBean[] oldRetryMethodsOnRollbacks = this.bean.getRetryMethodsOnRollbacks();
               RetryMethodsOnRollbackBean[] newRetryMethodsOnRollbacks = new RetryMethodsOnRollbackBean[oldRetryMethodsOnRollbacks.length];

               for(i = 0; i < newRetryMethodsOnRollbacks.length; ++i) {
                  newRetryMethodsOnRollbacks[i] = (RetryMethodsOnRollbackBean)((RetryMethodsOnRollbackBean)this.createCopy((AbstractDescriptorBean)oldRetryMethodsOnRollbacks[i], includeObsolete));
               }

               copy.setRetryMethodsOnRollbacks(newRetryMethodsOnRollbacks);
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsRoleAssignments")) && this.bean.isRunAsRoleAssignmentsSet() && !copy._isSet(3)) {
               RunAsRoleAssignmentBean[] oldRunAsRoleAssignments = this.bean.getRunAsRoleAssignments();
               RunAsRoleAssignmentBean[] newRunAsRoleAssignments = new RunAsRoleAssignmentBean[oldRunAsRoleAssignments.length];

               for(i = 0; i < newRunAsRoleAssignments.length; ++i) {
                  newRunAsRoleAssignments[i] = (RunAsRoleAssignmentBean)((RunAsRoleAssignmentBean)this.createCopy((AbstractDescriptorBean)oldRunAsRoleAssignments[i], includeObsolete));
               }

               copy.setRunAsRoleAssignments(newRunAsRoleAssignments);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityPermission")) && this.bean.isSecurityPermissionSet() && !copy._isSet(4)) {
               Object o = this.bean.getSecurityPermission();
               copy.setSecurityPermission((SecurityPermissionBean)null);
               copy.setSecurityPermission(o == null ? null : (SecurityPermissionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoleAssignments")) && this.bean.isSecurityRoleAssignmentsSet() && !copy._isSet(2)) {
               SecurityRoleAssignmentBean[] oldSecurityRoleAssignments = this.bean.getSecurityRoleAssignments();
               SecurityRoleAssignmentBean[] newSecurityRoleAssignments = new SecurityRoleAssignmentBean[oldSecurityRoleAssignments.length];

               for(i = 0; i < newSecurityRoleAssignments.length; ++i) {
                  newSecurityRoleAssignments[i] = (SecurityRoleAssignmentBean)((SecurityRoleAssignmentBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoleAssignments[i], includeObsolete));
               }

               copy.setSecurityRoleAssignments(newSecurityRoleAssignments);
            }

            if ((excludeProps == null || !excludeProps.contains("SkipStateReplicationMethods")) && this.bean.isSkipStateReplicationMethodsSet() && !copy._isSet(8)) {
               Object o = this.bean.getSkipStateReplicationMethods();
               copy.setSkipStateReplicationMethods((SkipStateReplicationMethodsBean)null);
               copy.setSkipStateReplicationMethods(o == null ? null : (SkipStateReplicationMethodsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimerImplementation")) && this.bean.isTimerImplementationSet()) {
               copy.setTimerImplementation(this.bean.getTimerImplementation());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionIsolations")) && this.bean.isTransactionIsolationsSet() && !copy._isSet(5)) {
               TransactionIsolationBean[] oldTransactionIsolations = this.bean.getTransactionIsolations();
               TransactionIsolationBean[] newTransactionIsolations = new TransactionIsolationBean[oldTransactionIsolations.length];

               for(i = 0; i < newTransactionIsolations.length; ++i) {
                  newTransactionIsolations[i] = (TransactionIsolationBean)((TransactionIsolationBean)this.createCopy((AbstractDescriptorBean)oldTransactionIsolations[i], includeObsolete));
               }

               copy.setTransactionIsolations(newTransactionIsolations);
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicCompatibility")) && this.bean.isWeblogicCompatibilitySet() && !copy._isSet(18)) {
               Object o = this.bean.getWeblogicCompatibility();
               copy.setWeblogicCompatibility((WeblogicCompatibilityBean)null);
               copy.setWeblogicCompatibility(o == null ? null : (WeblogicCompatibilityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicEnterpriseBeans")) && this.bean.isWeblogicEnterpriseBeansSet() && !copy._isSet(1)) {
               WeblogicEnterpriseBeanBean[] oldWeblogicEnterpriseBeans = this.bean.getWeblogicEnterpriseBeans();
               WeblogicEnterpriseBeanBean[] newWeblogicEnterpriseBeans = new WeblogicEnterpriseBeanBean[oldWeblogicEnterpriseBeans.length];

               for(i = 0; i < newWeblogicEnterpriseBeans.length; ++i) {
                  newWeblogicEnterpriseBeans[i] = (WeblogicEnterpriseBeanBean)((WeblogicEnterpriseBeanBean)this.createCopy((AbstractDescriptorBean)oldWeblogicEnterpriseBeans[i], includeObsolete));
               }

               copy.setWeblogicEnterpriseBeans(newWeblogicEnterpriseBeans);
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagers")) && this.bean.isWorkManagersSet() && !copy._isSet(13)) {
               WorkManagerBean[] oldWorkManagers = this.bean.getWorkManagers();
               WorkManagerBean[] newWorkManagers = new WorkManagerBean[oldWorkManagers.length];

               for(i = 0; i < newWorkManagers.length; ++i) {
                  newWorkManagers[i] = (WorkManagerBean)((WorkManagerBean)this.createCopy((AbstractDescriptorBean)oldWorkManagers[i], includeObsolete));
               }

               copy.setWorkManagers(newWorkManagers);
            }

            if ((excludeProps == null || !excludeProps.contains("EnableBeanClassRedeploy")) && this.bean.isEnableBeanClassRedeploySet()) {
               copy.setEnableBeanClassRedeploy(this.bean.isEnableBeanClassRedeploy());
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
         this.inferSubTree(this.bean.getCoherenceClusterRef(), clazz, annotation);
         this.inferSubTree(this.bean.getIdempotentMethods(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getRetryMethodsOnRollbacks(), clazz, annotation);
         this.inferSubTree(this.bean.getRunAsRoleAssignments(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityPermission(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoleAssignments(), clazz, annotation);
         this.inferSubTree(this.bean.getSkipStateReplicationMethods(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactionIsolations(), clazz, annotation);
         this.inferSubTree(this.bean.getWeblogicCompatibility(), clazz, annotation);
         this.inferSubTree(this.bean.getWeblogicEnterpriseBeans(), clazz, annotation);
         this.inferSubTree(this.bean.getWorkManagers(), clazz, annotation);
      }
   }
}
