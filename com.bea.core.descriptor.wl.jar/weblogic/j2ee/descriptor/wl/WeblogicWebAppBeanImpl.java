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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicWebAppBeanImpl extends AbstractDescriptorBean implements WeblogicWebAppBean, Serializable {
   private AsyncDescriptorBean[] _AsyncDescriptors;
   private String[] _AuthFilters;
   private CdiDescriptorBean _CdiDescriptor;
   private CharsetParamsBean[] _CharsetParams;
   private CoherenceClusterRefBean _CoherenceClusterRef;
   private String[] _ComponentFactoryClassName;
   private ContainerDescriptorBean[] _ContainerDescriptors;
   private String[] _ContextRoots;
   private String[] _Descriptions;
   private EjbReferenceDescriptionBean[] _EjbReferenceDescriptions;
   private FastSwapBean _FastSwap;
   private String _Id;
   private JASPICProviderBean _JASPICProvider;
   private JspDescriptorBean[] _JspDescriptors;
   private LibraryRefBean[] _LibraryRefs;
   private LoggingBean[] _Loggings;
   private ManagedExecutorServiceBean[] _ManagedExecutorServices;
   private ManagedScheduledExecutorServiceBean[] _ManagedScheduledExecutorServices;
   private ManagedThreadFactoryBean[] _ManagedThreadFactories;
   private MessageDestinationDescriptorBean[] _MessageDestinationDescriptors;
   private OsgiFrameworkReferenceBean _OsgiFrameworkReference;
   private String _ReadyRegistration;
   private ResourceDescriptionBean[] _ResourceDescriptions;
   private ResourceEnvDescriptionBean[] _ResourceEnvDescriptions;
   private RunAsRoleAssignmentBean[] _RunAsRoleAssignments;
   private SecurityPermissionBean[] _SecurityPermissions;
   private SecurityRoleAssignmentBean[] _SecurityRoleAssignments;
   private ServiceReferenceDescriptionBean[] _ServiceReferenceDescriptions;
   private ServletDescriptorBean[] _ServletDescriptors;
   private SessionDescriptorBean[] _SessionDescriptors;
   private String[] _UrlMatchMaps;
   private String _Version;
   private VirtualDirectoryMappingBean[] _VirtualDirectoryMappings;
   private String[] _WeblogicVersions;
   private String[] _WlDispatchPolicies;
   private WorkManagerBean[] _WorkManagers;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicWebAppBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicWebAppBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicWebAppBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getWeblogicVersions() {
      return this._WeblogicVersions;
   }

   public boolean isWeblogicVersionsInherited() {
      return false;
   }

   public boolean isWeblogicVersionsSet() {
      return this._isSet(1);
   }

   public void addWeblogicVersion(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getWeblogicVersions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setWeblogicVersions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeWeblogicVersion(String param0) {
      String[] _old = this.getWeblogicVersions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setWeblogicVersions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setWeblogicVersions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._WeblogicVersions;
      this._WeblogicVersions = param0;
      this._postSet(1, _oldVal, param0);
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

   public void addResourceDescription(ResourceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ResourceDescriptionBean[] _new;
         if (this._isSet(4)) {
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
      return this._isSet(4);
   }

   public void removeResourceDescription(ResourceDescriptionBean param0) {
      this.destroyResourceDescription(param0);
   }

   public void setResourceDescriptions(ResourceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceDescriptionBean[] param0 = param0 == null ? new ResourceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceDescriptionBean[] _oldVal = this._ResourceDescriptions;
      this._ResourceDescriptions = (ResourceDescriptionBean[])param0;
      this._postSet(4, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 4);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         ResourceEnvDescriptionBean[] _new;
         if (this._isSet(5)) {
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
      return this._isSet(5);
   }

   public void removeResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      this.destroyResourceEnvDescription(param0);
   }

   public void setResourceEnvDescriptions(ResourceEnvDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvDescriptionBean[] param0 = param0 == null ? new ResourceEnvDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvDescriptionBean[] _oldVal = this._ResourceEnvDescriptions;
      this._ResourceEnvDescriptions = (ResourceEnvDescriptionBean[])param0;
      this._postSet(5, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 5);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         EjbReferenceDescriptionBean[] _new;
         if (this._isSet(6)) {
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
      return this._isSet(6);
   }

   public void removeEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      this.destroyEjbReferenceDescription(param0);
   }

   public void setEjbReferenceDescriptions(EjbReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      EjbReferenceDescriptionBean[] param0 = param0 == null ? new EjbReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbReferenceDescriptionBean[] _oldVal = this._EjbReferenceDescriptions;
      this._EjbReferenceDescriptions = (EjbReferenceDescriptionBean[])param0;
      this._postSet(6, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 6);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         ServiceReferenceDescriptionBean[] _new;
         if (this._isSet(7)) {
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
      return this._isSet(7);
   }

   public void removeServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      this.destroyServiceReferenceDescription(param0);
   }

   public void setServiceReferenceDescriptions(ServiceReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ServiceReferenceDescriptionBean[] param0 = param0 == null ? new ServiceReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceReferenceDescriptionBean[] _oldVal = this._ServiceReferenceDescriptions;
      this._ServiceReferenceDescriptions = (ServiceReferenceDescriptionBean[])param0;
      this._postSet(7, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 7);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         MessageDestinationDescriptorBean[] _new;
         if (this._isSet(8)) {
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
      return this._isSet(8);
   }

   public void removeMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      this.destroyMessageDestinationDescriptor(param0);
   }

   public void setMessageDestinationDescriptors(MessageDestinationDescriptorBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationDescriptorBean[] param0 = param0 == null ? new MessageDestinationDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationDescriptorBean[] _oldVal = this._MessageDestinationDescriptors;
      this._MessageDestinationDescriptors = (MessageDestinationDescriptorBean[])param0;
      this._postSet(8, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 8);
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

   public void addSessionDescriptor(SessionDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         SessionDescriptorBean[] _new;
         if (this._isSet(9)) {
            _new = (SessionDescriptorBean[])((SessionDescriptorBean[])this._getHelper()._extendArray(this.getSessionDescriptors(), SessionDescriptorBean.class, param0));
         } else {
            _new = new SessionDescriptorBean[]{param0};
         }

         try {
            this.setSessionDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SessionDescriptorBean[] getSessionDescriptors() {
      return this._SessionDescriptors;
   }

   public boolean isSessionDescriptorsInherited() {
      return false;
   }

   public boolean isSessionDescriptorsSet() {
      return this._isSet(9);
   }

   public void removeSessionDescriptor(SessionDescriptorBean param0) {
      this.destroySessionDescriptor(param0);
   }

   public void setSessionDescriptors(SessionDescriptorBean[] param0) throws InvalidAttributeValueException {
      SessionDescriptorBean[] param0 = param0 == null ? new SessionDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SessionDescriptorBean[] _oldVal = this._SessionDescriptors;
      this._SessionDescriptors = (SessionDescriptorBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public SessionDescriptorBean createSessionDescriptor() {
      SessionDescriptorBeanImpl _val = new SessionDescriptorBeanImpl(this, -1);

      try {
         this.addSessionDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySessionDescriptor(SessionDescriptorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         SessionDescriptorBean[] _old = this.getSessionDescriptors();
         SessionDescriptorBean[] _new = (SessionDescriptorBean[])((SessionDescriptorBean[])this._getHelper()._removeElement(_old, SessionDescriptorBean.class, param0));
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
               this.setSessionDescriptors(_new);
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

   public void addAsyncDescriptor(AsyncDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         AsyncDescriptorBean[] _new;
         if (this._isSet(10)) {
            _new = (AsyncDescriptorBean[])((AsyncDescriptorBean[])this._getHelper()._extendArray(this.getAsyncDescriptors(), AsyncDescriptorBean.class, param0));
         } else {
            _new = new AsyncDescriptorBean[]{param0};
         }

         try {
            this.setAsyncDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AsyncDescriptorBean[] getAsyncDescriptors() {
      return this._AsyncDescriptors;
   }

   public boolean isAsyncDescriptorsInherited() {
      return false;
   }

   public boolean isAsyncDescriptorsSet() {
      return this._isSet(10);
   }

   public void removeAsyncDescriptor(AsyncDescriptorBean param0) {
      this.destroyAsyncDescriptor(param0);
   }

   public void setAsyncDescriptors(AsyncDescriptorBean[] param0) throws InvalidAttributeValueException {
      AsyncDescriptorBean[] param0 = param0 == null ? new AsyncDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AsyncDescriptorBean[] _oldVal = this._AsyncDescriptors;
      this._AsyncDescriptors = (AsyncDescriptorBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public AsyncDescriptorBean createAsyncDescriptor() {
      AsyncDescriptorBeanImpl _val = new AsyncDescriptorBeanImpl(this, -1);

      try {
         this.addAsyncDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAsyncDescriptor(AsyncDescriptorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         AsyncDescriptorBean[] _old = this.getAsyncDescriptors();
         AsyncDescriptorBean[] _new = (AsyncDescriptorBean[])((AsyncDescriptorBean[])this._getHelper()._removeElement(_old, AsyncDescriptorBean.class, param0));
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
               this.setAsyncDescriptors(_new);
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

   public void addJspDescriptor(JspDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         JspDescriptorBean[] _new;
         if (this._isSet(11)) {
            _new = (JspDescriptorBean[])((JspDescriptorBean[])this._getHelper()._extendArray(this.getJspDescriptors(), JspDescriptorBean.class, param0));
         } else {
            _new = new JspDescriptorBean[]{param0};
         }

         try {
            this.setJspDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JspDescriptorBean[] getJspDescriptors() {
      return this._JspDescriptors;
   }

   public boolean isJspDescriptorsInherited() {
      return false;
   }

   public boolean isJspDescriptorsSet() {
      return this._isSet(11);
   }

   public void removeJspDescriptor(JspDescriptorBean param0) {
      this.destroyJspDescriptor(param0);
   }

   public void setJspDescriptors(JspDescriptorBean[] param0) throws InvalidAttributeValueException {
      JspDescriptorBean[] param0 = param0 == null ? new JspDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JspDescriptorBean[] _oldVal = this._JspDescriptors;
      this._JspDescriptors = (JspDescriptorBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public JspDescriptorBean createJspDescriptor() {
      JspDescriptorBeanImpl _val = new JspDescriptorBeanImpl(this, -1);

      try {
         this.addJspDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJspDescriptor(JspDescriptorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         JspDescriptorBean[] _old = this.getJspDescriptors();
         JspDescriptorBean[] _new = (JspDescriptorBean[])((JspDescriptorBean[])this._getHelper()._removeElement(_old, JspDescriptorBean.class, param0));
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
               this.setJspDescriptors(_new);
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

   public void addContainerDescriptor(ContainerDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         ContainerDescriptorBean[] _new;
         if (this._isSet(12)) {
            _new = (ContainerDescriptorBean[])((ContainerDescriptorBean[])this._getHelper()._extendArray(this.getContainerDescriptors(), ContainerDescriptorBean.class, param0));
         } else {
            _new = new ContainerDescriptorBean[]{param0};
         }

         try {
            this.setContainerDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ContainerDescriptorBean[] getContainerDescriptors() {
      return this._ContainerDescriptors;
   }

   public boolean isContainerDescriptorsInherited() {
      return false;
   }

   public boolean isContainerDescriptorsSet() {
      return this._isSet(12);
   }

   public void removeContainerDescriptor(ContainerDescriptorBean param0) {
      this.destroyContainerDescriptor(param0);
   }

   public void setContainerDescriptors(ContainerDescriptorBean[] param0) throws InvalidAttributeValueException {
      ContainerDescriptorBean[] param0 = param0 == null ? new ContainerDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ContainerDescriptorBean[] _oldVal = this._ContainerDescriptors;
      this._ContainerDescriptors = (ContainerDescriptorBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public ContainerDescriptorBean createContainerDescriptor() {
      ContainerDescriptorBeanImpl _val = new ContainerDescriptorBeanImpl(this, -1);

      try {
         this.addContainerDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyContainerDescriptor(ContainerDescriptorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         ContainerDescriptorBean[] _old = this.getContainerDescriptors();
         ContainerDescriptorBean[] _new = (ContainerDescriptorBean[])((ContainerDescriptorBean[])this._getHelper()._removeElement(_old, ContainerDescriptorBean.class, param0));
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
               this.setContainerDescriptors(_new);
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

   public String[] getAuthFilters() {
      return this._AuthFilters;
   }

   public boolean isAuthFiltersInherited() {
      return false;
   }

   public boolean isAuthFiltersSet() {
      return this._isSet(13);
   }

   public void addAuthFilter(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(13)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getAuthFilters(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setAuthFilters(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeAuthFilter(String param0) {
      String[] _old = this.getAuthFilters();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setAuthFilters(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAuthFilters(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._AuthFilters;
      this._AuthFilters = param0;
      this._postSet(13, _oldVal, param0);
   }

   public void addCharsetParams(CharsetParamsBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         CharsetParamsBean[] _new;
         if (this._isSet(14)) {
            _new = (CharsetParamsBean[])((CharsetParamsBean[])this._getHelper()._extendArray(this.getCharsetParams(), CharsetParamsBean.class, param0));
         } else {
            _new = new CharsetParamsBean[]{param0};
         }

         try {
            this.setCharsetParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CharsetParamsBean[] getCharsetParams() {
      return this._CharsetParams;
   }

   public boolean isCharsetParamsInherited() {
      return false;
   }

   public boolean isCharsetParamsSet() {
      return this._isSet(14);
   }

   public void removeCharsetParams(CharsetParamsBean param0) {
      this.destroyCharsetParams(param0);
   }

   public void setCharsetParams(CharsetParamsBean[] param0) throws InvalidAttributeValueException {
      CharsetParamsBean[] param0 = param0 == null ? new CharsetParamsBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CharsetParamsBean[] _oldVal = this._CharsetParams;
      this._CharsetParams = (CharsetParamsBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public CharsetParamsBean createCharsetParams() {
      CharsetParamsBeanImpl _val = new CharsetParamsBeanImpl(this, -1);

      try {
         this.addCharsetParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCharsetParams(CharsetParamsBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         CharsetParamsBean[] _old = this.getCharsetParams();
         CharsetParamsBean[] _new = (CharsetParamsBean[])((CharsetParamsBean[])this._getHelper()._removeElement(_old, CharsetParamsBean.class, param0));
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
               this.setCharsetParams(_new);
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

   public void addVirtualDirectoryMapping(VirtualDirectoryMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         VirtualDirectoryMappingBean[] _new;
         if (this._isSet(15)) {
            _new = (VirtualDirectoryMappingBean[])((VirtualDirectoryMappingBean[])this._getHelper()._extendArray(this.getVirtualDirectoryMappings(), VirtualDirectoryMappingBean.class, param0));
         } else {
            _new = new VirtualDirectoryMappingBean[]{param0};
         }

         try {
            this.setVirtualDirectoryMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VirtualDirectoryMappingBean[] getVirtualDirectoryMappings() {
      return this._VirtualDirectoryMappings;
   }

   public boolean isVirtualDirectoryMappingsInherited() {
      return false;
   }

   public boolean isVirtualDirectoryMappingsSet() {
      return this._isSet(15);
   }

   public void removeVirtualDirectoryMapping(VirtualDirectoryMappingBean param0) {
      this.destroyVirtualDirectoryMapping(param0);
   }

   public void setVirtualDirectoryMappings(VirtualDirectoryMappingBean[] param0) throws InvalidAttributeValueException {
      VirtualDirectoryMappingBean[] param0 = param0 == null ? new VirtualDirectoryMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      VirtualDirectoryMappingBean[] _oldVal = this._VirtualDirectoryMappings;
      this._VirtualDirectoryMappings = (VirtualDirectoryMappingBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public VirtualDirectoryMappingBean createVirtualDirectoryMapping() {
      VirtualDirectoryMappingBeanImpl _val = new VirtualDirectoryMappingBeanImpl(this, -1);

      try {
         this.addVirtualDirectoryMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyVirtualDirectoryMapping(VirtualDirectoryMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         VirtualDirectoryMappingBean[] _old = this.getVirtualDirectoryMappings();
         VirtualDirectoryMappingBean[] _new = (VirtualDirectoryMappingBean[])((VirtualDirectoryMappingBean[])this._getHelper()._removeElement(_old, VirtualDirectoryMappingBean.class, param0));
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
               this.setVirtualDirectoryMappings(_new);
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

   public String[] getUrlMatchMaps() {
      return this._UrlMatchMaps;
   }

   public boolean isUrlMatchMapsInherited() {
      return false;
   }

   public boolean isUrlMatchMapsSet() {
      return this._isSet(16);
   }

   public void addUrlMatchMap(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(16)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getUrlMatchMaps(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setUrlMatchMaps(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeUrlMatchMap(String param0) {
      String[] _old = this.getUrlMatchMaps();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setUrlMatchMaps(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setUrlMatchMaps(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._UrlMatchMaps;
      this._UrlMatchMaps = param0;
      this._postSet(16, _oldVal, param0);
   }

   public void addSecurityPermission(SecurityPermissionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         SecurityPermissionBean[] _new;
         if (this._isSet(17)) {
            _new = (SecurityPermissionBean[])((SecurityPermissionBean[])this._getHelper()._extendArray(this.getSecurityPermissions(), SecurityPermissionBean.class, param0));
         } else {
            _new = new SecurityPermissionBean[]{param0};
         }

         try {
            this.setSecurityPermissions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityPermissionBean[] getSecurityPermissions() {
      return this._SecurityPermissions;
   }

   public boolean isSecurityPermissionsInherited() {
      return false;
   }

   public boolean isSecurityPermissionsSet() {
      return this._isSet(17);
   }

   public void removeSecurityPermission(SecurityPermissionBean param0) {
      this.destroySecurityPermission(param0);
   }

   public void setSecurityPermissions(SecurityPermissionBean[] param0) throws InvalidAttributeValueException {
      SecurityPermissionBean[] param0 = param0 == null ? new SecurityPermissionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityPermissionBean[] _oldVal = this._SecurityPermissions;
      this._SecurityPermissions = (SecurityPermissionBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public SecurityPermissionBean createSecurityPermission() {
      SecurityPermissionBeanImpl _val = new SecurityPermissionBeanImpl(this, -1);

      try {
         this.addSecurityPermission(_val);
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
         this._checkIsPotentialChild(param0, 17);
         SecurityPermissionBean[] _old = this.getSecurityPermissions();
         SecurityPermissionBean[] _new = (SecurityPermissionBean[])((SecurityPermissionBean[])this._getHelper()._removeElement(_old, SecurityPermissionBean.class, param0));
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
               this.setSecurityPermissions(_new);
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

   public String[] getContextRoots() {
      return this._ContextRoots;
   }

   public boolean isContextRootsInherited() {
      return false;
   }

   public boolean isContextRootsSet() {
      return this._isSet(18);
   }

   public void addContextRoot(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(18)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getContextRoots(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setContextRoots(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeContextRoot(String param0) {
      String[] _old = this.getContextRoots();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setContextRoots(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setContextRoots(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ContextRoots;
      this._ContextRoots = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String[] getWlDispatchPolicies() {
      return this._WlDispatchPolicies;
   }

   public boolean isWlDispatchPoliciesInherited() {
      return false;
   }

   public boolean isWlDispatchPoliciesSet() {
      return this._isSet(19);
   }

   public void addWlDispatchPolicy(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(19)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getWlDispatchPolicies(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setWlDispatchPolicies(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeWlDispatchPolicy(String param0) {
      String[] _old = this.getWlDispatchPolicies();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setWlDispatchPolicies(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setWlDispatchPolicies(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._WlDispatchPolicies;
      this._WlDispatchPolicies = param0;
      this._postSet(19, _oldVal, param0);
   }

   public void addServletDescriptor(ServletDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         ServletDescriptorBean[] _new;
         if (this._isSet(20)) {
            _new = (ServletDescriptorBean[])((ServletDescriptorBean[])this._getHelper()._extendArray(this.getServletDescriptors(), ServletDescriptorBean.class, param0));
         } else {
            _new = new ServletDescriptorBean[]{param0};
         }

         try {
            this.setServletDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServletDescriptorBean[] getServletDescriptors() {
      return this._ServletDescriptors;
   }

   public boolean isServletDescriptorsInherited() {
      return false;
   }

   public boolean isServletDescriptorsSet() {
      return this._isSet(20);
   }

   public void removeServletDescriptor(ServletDescriptorBean param0) {
      this.destroyServletDescriptor(param0);
   }

   public void setServletDescriptors(ServletDescriptorBean[] param0) throws InvalidAttributeValueException {
      ServletDescriptorBean[] param0 = param0 == null ? new ServletDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServletDescriptorBean[] _oldVal = this._ServletDescriptors;
      this._ServletDescriptors = (ServletDescriptorBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public ServletDescriptorBean createServletDescriptor() {
      ServletDescriptorBeanImpl _val = new ServletDescriptorBeanImpl(this, -1);

      try {
         this.addServletDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ServletDescriptorBean lookupServletDescriptor(String param0) {
      Object[] aary = (Object[])this._ServletDescriptors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ServletDescriptorBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ServletDescriptorBeanImpl)it.previous();
      } while(!bean.getServletName().equals(param0));

      return bean;
   }

   public void destroyServletDescriptor(ServletDescriptorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         ServletDescriptorBean[] _old = this.getServletDescriptors();
         ServletDescriptorBean[] _new = (ServletDescriptorBean[])((ServletDescriptorBean[])this._getHelper()._removeElement(_old, ServletDescriptorBean.class, param0));
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
               this.setServletDescriptors(_new);
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

   public void addWorkManager(WorkManagerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         WorkManagerBean[] _new;
         if (this._isSet(21)) {
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
      return this._isSet(21);
   }

   public void removeWorkManager(WorkManagerBean param0) {
      this.destroyWorkManager(param0);
   }

   public void setWorkManagers(WorkManagerBean[] param0) throws InvalidAttributeValueException {
      WorkManagerBean[] param0 = param0 == null ? new WorkManagerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WorkManagerBean[] _oldVal = this._WorkManagers;
      this._WorkManagers = (WorkManagerBean[])param0;
      this._postSet(21, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 21);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         ManagedExecutorServiceBean[] _new;
         if (this._isSet(22)) {
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
      return this._isSet(22);
   }

   public void removeManagedExecutorService(ManagedExecutorServiceBean param0) {
      this.destroyManagedExecutorService(param0);
   }

   public void setManagedExecutorServices(ManagedExecutorServiceBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceBean[] param0 = param0 == null ? new ManagedExecutorServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedExecutorServiceBean[] _oldVal = this._ManagedExecutorServices;
      this._ManagedExecutorServices = (ManagedExecutorServiceBean[])param0;
      this._postSet(22, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 22);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         ManagedScheduledExecutorServiceBean[] _new;
         if (this._isSet(23)) {
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
      return this._isSet(23);
   }

   public void removeManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      this.destroyManagedScheduledExecutorService(param0);
   }

   public void setManagedScheduledExecutorServices(ManagedScheduledExecutorServiceBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedScheduledExecutorServiceBean[] _oldVal = this._ManagedScheduledExecutorServices;
      this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceBean[])param0;
      this._postSet(23, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 23);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         ManagedThreadFactoryBean[] _new;
         if (this._isSet(24)) {
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
      return this._isSet(24);
   }

   public void removeManagedThreadFactory(ManagedThreadFactoryBean param0) {
      this.destroyManagedThreadFactory(param0);
   }

   public void setManagedThreadFactories(ManagedThreadFactoryBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryBean[] param0 = param0 == null ? new ManagedThreadFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedThreadFactoryBean[] _oldVal = this._ManagedThreadFactories;
      this._ManagedThreadFactories = (ManagedThreadFactoryBean[])param0;
      this._postSet(24, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 24);
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

   public String[] getComponentFactoryClassName() {
      return this._ComponentFactoryClassName;
   }

   public boolean isComponentFactoryClassNameInherited() {
      return false;
   }

   public boolean isComponentFactoryClassNameSet() {
      return this._isSet(25);
   }

   public void addComponentFactoryClassName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(25)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getComponentFactoryClassName(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setComponentFactoryClassName(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeComponentFactoryClassName(String param0) {
      String[] _old = this.getComponentFactoryClassName();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setComponentFactoryClassName(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setComponentFactoryClassName(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ComponentFactoryClassName;
      this._ComponentFactoryClassName = param0;
      this._postSet(25, _oldVal, param0);
   }

   public void addLogging(LoggingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         LoggingBean[] _new;
         if (this._isSet(26)) {
            _new = (LoggingBean[])((LoggingBean[])this._getHelper()._extendArray(this.getLoggings(), LoggingBean.class, param0));
         } else {
            _new = new LoggingBean[]{param0};
         }

         try {
            this.setLoggings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LoggingBean[] getLoggings() {
      return this._Loggings;
   }

   public boolean isLoggingsInherited() {
      return false;
   }

   public boolean isLoggingsSet() {
      return this._isSet(26);
   }

   public void removeLogging(LoggingBean param0) {
      this.destroyLogging(param0);
   }

   public void setLoggings(LoggingBean[] param0) throws InvalidAttributeValueException {
      LoggingBean[] param0 = param0 == null ? new LoggingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 26)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LoggingBean[] _oldVal = this._Loggings;
      this._Loggings = (LoggingBean[])param0;
      this._postSet(26, _oldVal, param0);
   }

   public LoggingBean createLogging() {
      LoggingBeanImpl _val = new LoggingBeanImpl(this, -1);

      try {
         this.addLogging(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLogging(LoggingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 26);
         LoggingBean[] _old = this.getLoggings();
         LoggingBean[] _new = (LoggingBean[])((LoggingBean[])this._getHelper()._removeElement(_old, LoggingBean.class, param0));
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
               this.setLoggings(_new);
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

   public void addLibraryRef(LibraryRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         LibraryRefBean[] _new;
         if (this._isSet(27)) {
            _new = (LibraryRefBean[])((LibraryRefBean[])this._getHelper()._extendArray(this.getLibraryRefs(), LibraryRefBean.class, param0));
         } else {
            _new = new LibraryRefBean[]{param0};
         }

         try {
            this.setLibraryRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LibraryRefBean[] getLibraryRefs() {
      return this._LibraryRefs;
   }

   public boolean isLibraryRefsInherited() {
      return false;
   }

   public boolean isLibraryRefsSet() {
      return this._isSet(27);
   }

   public void removeLibraryRef(LibraryRefBean param0) {
      this.destroyLibraryRef(param0);
   }

   public void setLibraryRefs(LibraryRefBean[] param0) throws InvalidAttributeValueException {
      LibraryRefBean[] param0 = param0 == null ? new LibraryRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 27)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LibraryRefBean[] _oldVal = this._LibraryRefs;
      this._LibraryRefs = (LibraryRefBean[])param0;
      this._postSet(27, _oldVal, param0);
   }

   public LibraryRefBean createLibraryRef() {
      LibraryRefBeanImpl _val = new LibraryRefBeanImpl(this, -1);

      try {
         this.addLibraryRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLibraryRef(LibraryRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 27);
         LibraryRefBean[] _old = this.getLibraryRefs();
         LibraryRefBean[] _new = (LibraryRefBean[])((LibraryRefBean[])this._getHelper()._removeElement(_old, LibraryRefBean.class, param0));
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
               this.setLibraryRefs(_new);
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

   public JASPICProviderBean getJASPICProvider() {
      return this._JASPICProvider;
   }

   public boolean isJASPICProviderInherited() {
      return false;
   }

   public boolean isJASPICProviderSet() {
      return this._isSet(28) || this._isAnythingSet((AbstractDescriptorBean)this.getJASPICProvider());
   }

   public void setJASPICProvider(JASPICProviderBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 28)) {
         this._postCreate(_child);
      }

      JASPICProviderBean _oldVal = this._JASPICProvider;
      this._JASPICProvider = param0;
      this._postSet(28, _oldVal, param0);
   }

   public FastSwapBean getFastSwap() {
      return this._FastSwap;
   }

   public boolean isFastSwapInherited() {
      return false;
   }

   public boolean isFastSwapSet() {
      return this._isSet(29);
   }

   public void setFastSwap(FastSwapBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFastSwap() != null && param0 != this.getFastSwap()) {
         throw new BeanAlreadyExistsException(this.getFastSwap() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 29)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         FastSwapBean _oldVal = this._FastSwap;
         this._FastSwap = param0;
         this._postSet(29, _oldVal, param0);
      }
   }

   public FastSwapBean createFastSwap() {
      FastSwapBeanImpl _val = new FastSwapBeanImpl(this, -1);

      try {
         this.setFastSwap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFastSwap() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FastSwap;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFastSwap((FastSwapBean)null);
               this._unSet(29);
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

   public CoherenceClusterRefBean getCoherenceClusterRef() {
      return this._CoherenceClusterRef;
   }

   public boolean isCoherenceClusterRefInherited() {
      return false;
   }

   public boolean isCoherenceClusterRefSet() {
      return this._isSet(30);
   }

   public void setCoherenceClusterRef(CoherenceClusterRefBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCoherenceClusterRef() != null && param0 != this.getCoherenceClusterRef()) {
         throw new BeanAlreadyExistsException(this.getCoherenceClusterRef() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 30)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CoherenceClusterRefBean _oldVal = this._CoherenceClusterRef;
         this._CoherenceClusterRef = param0;
         this._postSet(30, _oldVal, param0);
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
               this._unSet(30);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(31);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(31, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(32);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(32, _oldVal, param0);
   }

   public OsgiFrameworkReferenceBean getOsgiFrameworkReference() {
      return this._OsgiFrameworkReference;
   }

   public boolean isOsgiFrameworkReferenceInherited() {
      return false;
   }

   public boolean isOsgiFrameworkReferenceSet() {
      return this._isSet(33);
   }

   public void setOsgiFrameworkReference(OsgiFrameworkReferenceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getOsgiFrameworkReference() != null && param0 != this.getOsgiFrameworkReference()) {
         throw new BeanAlreadyExistsException(this.getOsgiFrameworkReference() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 33)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OsgiFrameworkReferenceBean _oldVal = this._OsgiFrameworkReference;
         this._OsgiFrameworkReference = param0;
         this._postSet(33, _oldVal, param0);
      }
   }

   public OsgiFrameworkReferenceBean createOsgiFrameworkReference() {
      OsgiFrameworkReferenceBeanImpl _val = new OsgiFrameworkReferenceBeanImpl(this, -1);

      try {
         this.setOsgiFrameworkReference(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOsgiFrameworkReference() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._OsgiFrameworkReference;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOsgiFrameworkReference((OsgiFrameworkReferenceBean)null);
               this._unSet(33);
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

   public String getReadyRegistration() {
      return this._ReadyRegistration;
   }

   public boolean isReadyRegistrationInherited() {
      return false;
   }

   public boolean isReadyRegistrationSet() {
      return this._isSet(34);
   }

   public void setReadyRegistration(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReadyRegistration;
      this._ReadyRegistration = param0;
      this._postSet(34, _oldVal, param0);
   }

   public CdiDescriptorBean getCdiDescriptor() {
      return this._CdiDescriptor;
   }

   public boolean isCdiDescriptorInherited() {
      return false;
   }

   public boolean isCdiDescriptorSet() {
      return this._isSet(35) || this._isAnythingSet((AbstractDescriptorBean)this.getCdiDescriptor());
   }

   public void setCdiDescriptor(CdiDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 35)) {
         this._postCreate(_child);
      }

      CdiDescriptorBean _oldVal = this._CdiDescriptor;
      this._CdiDescriptor = param0;
      this._postSet(35, _oldVal, param0);
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
      return super._isAnythingSet() || this.isCdiDescriptorSet() || this.isJASPICProviderSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._AsyncDescriptors = new AsyncDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._AuthFilters = new String[0];
               if (initOne) {
                  break;
               }
            case 35:
               this._CdiDescriptor = new CdiDescriptorBeanImpl(this, 35);
               this._postCreate((AbstractDescriptorBean)this._CdiDescriptor);
               if (initOne) {
                  break;
               }
            case 14:
               this._CharsetParams = new CharsetParamsBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._CoherenceClusterRef = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ComponentFactoryClassName = new String[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ContainerDescriptors = new ContainerDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._ContextRoots = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._EjbReferenceDescriptions = new EjbReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 29:
               this._FastSwap = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._JASPICProvider = new JASPICProviderBeanImpl(this, 28);
               this._postCreate((AbstractDescriptorBean)this._JASPICProvider);
               if (initOne) {
                  break;
               }
            case 11:
               this._JspDescriptors = new JspDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._LibraryRefs = new LibraryRefBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._Loggings = new LoggingBean[0];
               if (initOne) {
                  break;
               }
            case 22:
               this._ManagedExecutorServices = new ManagedExecutorServiceBean[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._ManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._ManagedThreadFactories = new ManagedThreadFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._MessageDestinationDescriptors = new MessageDestinationDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 33:
               this._OsgiFrameworkReference = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._ReadyRegistration = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ResourceDescriptions = new ResourceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._ResourceEnvDescriptions = new ResourceEnvDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._RunAsRoleAssignments = new RunAsRoleAssignmentBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._SecurityPermissions = new SecurityPermissionBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._SecurityRoleAssignments = new SecurityRoleAssignmentBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._ServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ServletDescriptors = new ServletDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._SessionDescriptors = new SessionDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._UrlMatchMaps = new String[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._VirtualDirectoryMappings = new VirtualDirectoryMappingBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._WeblogicVersions = new String[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._WlDispatchPolicies = new String[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._WorkManagers = new WorkManagerBean[0];
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
      return "http://xmlns.oracle.com/weblogic/weblogic-web-app/1.6/weblogic-web-app.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-web-app";
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
                  return 31;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 17:
            case 23:
            case 26:
            case 27:
            case 31:
            case 32:
            case 33:
            default:
               break;
            case 7:
               if (s.equals("logging")) {
                  return 26;
               }

               if (s.equals("version")) {
                  return 32;
               }
               break;
            case 9:
               if (s.equals("fast-swap")) {
                  return 29;
               }
               break;
            case 11:
               if (s.equals("auth-filter")) {
                  return 13;
               }

               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("library-ref")) {
                  return 27;
               }
               break;
            case 12:
               if (s.equals("context-root")) {
                  return 18;
               }

               if (s.equals("work-manager")) {
                  return 21;
               }
               break;
            case 13:
               if (s.equals("charset-param")) {
                  return 14;
               }

               if (s.equals("url-match-map")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("cdi-descriptor")) {
                  return 35;
               }

               if (s.equals("jsp-descriptor")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("jaspic-provider")) {
                  return 28;
               }
               break;
            case 16:
               if (s.equals("async-descriptor")) {
                  return 10;
               }

               if (s.equals("weblogic-version")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("ready-registration")) {
                  return 34;
               }

               if (s.equals("servlet-descriptor")) {
                  return 20;
               }

               if (s.equals("session-descriptor")) {
                  return 9;
               }

               if (s.equals("wl-dispatch-policy")) {
                  return 19;
               }
               break;
            case 19:
               if (s.equals("security-permission")) {
                  return 17;
               }
               break;
            case 20:
               if (s.equals("container-descriptor")) {
                  return 12;
               }

               if (s.equals("resource-description")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("coherence-cluster-ref")) {
                  return 30;
               }
               break;
            case 22:
               if (s.equals("managed-thread-factory")) {
                  return 24;
               }

               if (s.equals("run-as-role-assignment")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("managed-executor-service")) {
                  return 22;
               }

               if (s.equals("osgi-framework-reference")) {
                  return 33;
               }

               if (s.equals("resource-env-description")) {
                  return 5;
               }

               if (s.equals("security-role-assignment")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("ejb-reference-description")) {
                  return 6;
               }

               if (s.equals("virtual-directory-mapping")) {
                  return 15;
               }
               break;
            case 28:
               if (s.equals("component-factory-class-name")) {
                  return 25;
               }
               break;
            case 29:
               if (s.equals("service-reference-description")) {
                  return 7;
               }
               break;
            case 30:
               if (s.equals("message-destination-descriptor")) {
                  return 8;
               }
               break;
            case 34:
               if (s.equals("managed-scheduled-executor-service")) {
                  return 23;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new SecurityRoleAssignmentBeanImpl.SchemaHelper2();
            case 3:
               return new RunAsRoleAssignmentBeanImpl.SchemaHelper2();
            case 4:
               return new ResourceDescriptionBeanImpl.SchemaHelper2();
            case 5:
               return new ResourceEnvDescriptionBeanImpl.SchemaHelper2();
            case 6:
               return new EjbReferenceDescriptionBeanImpl.SchemaHelper2();
            case 7:
               return new ServiceReferenceDescriptionBeanImpl.SchemaHelper2();
            case 8:
               return new MessageDestinationDescriptorBeanImpl.SchemaHelper2();
            case 9:
               return new SessionDescriptorBeanImpl.SchemaHelper2();
            case 10:
               return new AsyncDescriptorBeanImpl.SchemaHelper2();
            case 11:
               return new JspDescriptorBeanImpl.SchemaHelper2();
            case 12:
               return new ContainerDescriptorBeanImpl.SchemaHelper2();
            case 13:
            case 16:
            case 18:
            case 19:
            case 25:
            case 31:
            case 32:
            case 34:
            default:
               return super.getSchemaHelper(propIndex);
            case 14:
               return new CharsetParamsBeanImpl.SchemaHelper2();
            case 15:
               return new VirtualDirectoryMappingBeanImpl.SchemaHelper2();
            case 17:
               return new SecurityPermissionBeanImpl.SchemaHelper2();
            case 20:
               return new ServletDescriptorBeanImpl.SchemaHelper2();
            case 21:
               return new WorkManagerBeanImpl.SchemaHelper2();
            case 22:
               return new ManagedExecutorServiceBeanImpl.SchemaHelper2();
            case 23:
               return new ManagedScheduledExecutorServiceBeanImpl.SchemaHelper2();
            case 24:
               return new ManagedThreadFactoryBeanImpl.SchemaHelper2();
            case 26:
               return new LoggingBeanImpl.SchemaHelper2();
            case 27:
               return new LibraryRefBeanImpl.SchemaHelper2();
            case 28:
               return new JASPICProviderBeanImpl.SchemaHelper2();
            case 29:
               return new FastSwapBeanImpl.SchemaHelper2();
            case 30:
               return new CoherenceClusterRefBeanImpl.SchemaHelper2();
            case 33:
               return new OsgiFrameworkReferenceBeanImpl.SchemaHelper2();
            case 35:
               return new CdiDescriptorBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "weblogic-web-app";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "weblogic-version";
            case 2:
               return "security-role-assignment";
            case 3:
               return "run-as-role-assignment";
            case 4:
               return "resource-description";
            case 5:
               return "resource-env-description";
            case 6:
               return "ejb-reference-description";
            case 7:
               return "service-reference-description";
            case 8:
               return "message-destination-descriptor";
            case 9:
               return "session-descriptor";
            case 10:
               return "async-descriptor";
            case 11:
               return "jsp-descriptor";
            case 12:
               return "container-descriptor";
            case 13:
               return "auth-filter";
            case 14:
               return "charset-param";
            case 15:
               return "virtual-directory-mapping";
            case 16:
               return "url-match-map";
            case 17:
               return "security-permission";
            case 18:
               return "context-root";
            case 19:
               return "wl-dispatch-policy";
            case 20:
               return "servlet-descriptor";
            case 21:
               return "work-manager";
            case 22:
               return "managed-executor-service";
            case 23:
               return "managed-scheduled-executor-service";
            case 24:
               return "managed-thread-factory";
            case 25:
               return "component-factory-class-name";
            case 26:
               return "logging";
            case 27:
               return "library-ref";
            case 28:
               return "jaspic-provider";
            case 29:
               return "fast-swap";
            case 30:
               return "coherence-cluster-ref";
            case 31:
               return "id";
            case 32:
               return "version";
            case 33:
               return "osgi-framework-reference";
            case 34:
               return "ready-registration";
            case 35:
               return "cdi-descriptor";
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
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
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
            case 16:
            case 18:
            case 19:
            case 25:
            case 31:
            case 32:
            case 34:
            default:
               return super.isBean(propIndex);
            case 14:
               return true;
            case 15:
               return true;
            case 17:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 33:
               return true;
            case 35:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 20:
            default:
               return super.isConfigurable(propIndex);
            case 18:
               return true;
            case 19:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
         }
      }

      public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isMergeRuleIgnoreTargetDefined(propIndex);
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicWebAppBeanImpl bean;

      protected Helper(WeblogicWebAppBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "WeblogicVersions";
            case 2:
               return "SecurityRoleAssignments";
            case 3:
               return "RunAsRoleAssignments";
            case 4:
               return "ResourceDescriptions";
            case 5:
               return "ResourceEnvDescriptions";
            case 6:
               return "EjbReferenceDescriptions";
            case 7:
               return "ServiceReferenceDescriptions";
            case 8:
               return "MessageDestinationDescriptors";
            case 9:
               return "SessionDescriptors";
            case 10:
               return "AsyncDescriptors";
            case 11:
               return "JspDescriptors";
            case 12:
               return "ContainerDescriptors";
            case 13:
               return "AuthFilters";
            case 14:
               return "CharsetParams";
            case 15:
               return "VirtualDirectoryMappings";
            case 16:
               return "UrlMatchMaps";
            case 17:
               return "SecurityPermissions";
            case 18:
               return "ContextRoots";
            case 19:
               return "WlDispatchPolicies";
            case 20:
               return "ServletDescriptors";
            case 21:
               return "WorkManagers";
            case 22:
               return "ManagedExecutorServices";
            case 23:
               return "ManagedScheduledExecutorServices";
            case 24:
               return "ManagedThreadFactories";
            case 25:
               return "ComponentFactoryClassName";
            case 26:
               return "Loggings";
            case 27:
               return "LibraryRefs";
            case 28:
               return "JASPICProvider";
            case 29:
               return "FastSwap";
            case 30:
               return "CoherenceClusterRef";
            case 31:
               return "Id";
            case 32:
               return "Version";
            case 33:
               return "OsgiFrameworkReference";
            case 34:
               return "ReadyRegistration";
            case 35:
               return "CdiDescriptor";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AsyncDescriptors")) {
            return 10;
         } else if (propName.equals("AuthFilters")) {
            return 13;
         } else if (propName.equals("CdiDescriptor")) {
            return 35;
         } else if (propName.equals("CharsetParams")) {
            return 14;
         } else if (propName.equals("CoherenceClusterRef")) {
            return 30;
         } else if (propName.equals("ComponentFactoryClassName")) {
            return 25;
         } else if (propName.equals("ContainerDescriptors")) {
            return 12;
         } else if (propName.equals("ContextRoots")) {
            return 18;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("EjbReferenceDescriptions")) {
            return 6;
         } else if (propName.equals("FastSwap")) {
            return 29;
         } else if (propName.equals("Id")) {
            return 31;
         } else if (propName.equals("JASPICProvider")) {
            return 28;
         } else if (propName.equals("JspDescriptors")) {
            return 11;
         } else if (propName.equals("LibraryRefs")) {
            return 27;
         } else if (propName.equals("Loggings")) {
            return 26;
         } else if (propName.equals("ManagedExecutorServices")) {
            return 22;
         } else if (propName.equals("ManagedScheduledExecutorServices")) {
            return 23;
         } else if (propName.equals("ManagedThreadFactories")) {
            return 24;
         } else if (propName.equals("MessageDestinationDescriptors")) {
            return 8;
         } else if (propName.equals("OsgiFrameworkReference")) {
            return 33;
         } else if (propName.equals("ReadyRegistration")) {
            return 34;
         } else if (propName.equals("ResourceDescriptions")) {
            return 4;
         } else if (propName.equals("ResourceEnvDescriptions")) {
            return 5;
         } else if (propName.equals("RunAsRoleAssignments")) {
            return 3;
         } else if (propName.equals("SecurityPermissions")) {
            return 17;
         } else if (propName.equals("SecurityRoleAssignments")) {
            return 2;
         } else if (propName.equals("ServiceReferenceDescriptions")) {
            return 7;
         } else if (propName.equals("ServletDescriptors")) {
            return 20;
         } else if (propName.equals("SessionDescriptors")) {
            return 9;
         } else if (propName.equals("UrlMatchMaps")) {
            return 16;
         } else if (propName.equals("Version")) {
            return 32;
         } else if (propName.equals("VirtualDirectoryMappings")) {
            return 15;
         } else if (propName.equals("WeblogicVersions")) {
            return 1;
         } else if (propName.equals("WlDispatchPolicies")) {
            return 19;
         } else {
            return propName.equals("WorkManagers") ? 21 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAsyncDescriptors()));
         if (this.bean.getCdiDescriptor() != null) {
            iterators.add(new ArrayIterator(new CdiDescriptorBean[]{this.bean.getCdiDescriptor()}));
         }

         iterators.add(new ArrayIterator(this.bean.getCharsetParams()));
         if (this.bean.getCoherenceClusterRef() != null) {
            iterators.add(new ArrayIterator(new CoherenceClusterRefBean[]{this.bean.getCoherenceClusterRef()}));
         }

         iterators.add(new ArrayIterator(this.bean.getContainerDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getEjbReferenceDescriptions()));
         if (this.bean.getFastSwap() != null) {
            iterators.add(new ArrayIterator(new FastSwapBean[]{this.bean.getFastSwap()}));
         }

         if (this.bean.getJASPICProvider() != null) {
            iterators.add(new ArrayIterator(new JASPICProviderBean[]{this.bean.getJASPICProvider()}));
         }

         iterators.add(new ArrayIterator(this.bean.getJspDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getLibraryRefs()));
         iterators.add(new ArrayIterator(this.bean.getLoggings()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactories()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationDescriptors()));
         if (this.bean.getOsgiFrameworkReference() != null) {
            iterators.add(new ArrayIterator(new OsgiFrameworkReferenceBean[]{this.bean.getOsgiFrameworkReference()}));
         }

         iterators.add(new ArrayIterator(this.bean.getResourceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getRunAsRoleAssignments()));
         iterators.add(new ArrayIterator(this.bean.getSecurityPermissions()));
         iterators.add(new ArrayIterator(this.bean.getSecurityRoleAssignments()));
         iterators.add(new ArrayIterator(this.bean.getServiceReferenceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getServletDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getSessionDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getVirtualDirectoryMappings()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getAsyncDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAsyncDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAuthFiltersSet()) {
               buf.append("AuthFilters");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAuthFilters())));
            }

            childValue = this.computeChildHashValue(this.bean.getCdiDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCharsetParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCharsetParams()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceClusterRef());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isComponentFactoryClassNameSet()) {
               buf.append("ComponentFactoryClassName");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getComponentFactoryClassName())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getContainerDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getContainerDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isContextRootsSet()) {
               buf.append("ContextRoots");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getContextRoots())));
            }

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFastSwap());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getJASPICProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJspDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJspDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLibraryRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLibraryRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLoggings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLoggings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

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

            childValue = this.computeChildHashValue(this.bean.getOsgiFrameworkReference());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isReadyRegistrationSet()) {
               buf.append("ReadyRegistration");
               buf.append(String.valueOf(this.bean.getReadyRegistration()));
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

            childValue = 0L;

            for(i = 0; i < this.bean.getRunAsRoleAssignments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRunAsRoleAssignments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityPermissions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityPermissions()[i]);
            }

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

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServletDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServletDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSessionDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSessionDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isUrlMatchMapsSet()) {
               buf.append("UrlMatchMaps");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUrlMatchMaps())));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getVirtualDirectoryMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getVirtualDirectoryMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWeblogicVersionsSet()) {
               buf.append("WeblogicVersions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getWeblogicVersions())));
            }

            if (this.bean.isWlDispatchPoliciesSet()) {
               buf.append("WlDispatchPolicies");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getWlDispatchPolicies())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWorkManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWorkManagers()[i]);
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
            WeblogicWebAppBeanImpl otherTyped = (WeblogicWebAppBeanImpl)other;
            this.computeChildDiff("AsyncDescriptors", this.bean.getAsyncDescriptors(), otherTyped.getAsyncDescriptors(), false);
            this.computeDiff("AuthFilters", this.bean.getAuthFilters(), otherTyped.getAuthFilters(), false);
            this.computeSubDiff("CdiDescriptor", this.bean.getCdiDescriptor(), otherTyped.getCdiDescriptor());
            this.computeChildDiff("CharsetParams", this.bean.getCharsetParams(), otherTyped.getCharsetParams(), false);
            this.computeChildDiff("CoherenceClusterRef", this.bean.getCoherenceClusterRef(), otherTyped.getCoherenceClusterRef(), false);
            this.computeDiff("ComponentFactoryClassName", this.bean.getComponentFactoryClassName(), otherTyped.getComponentFactoryClassName(), false);
            this.computeChildDiff("ContainerDescriptors", this.bean.getContainerDescriptors(), otherTyped.getContainerDescriptors(), true);
            this.computeDiff("ContextRoots", this.bean.getContextRoots(), otherTyped.getContextRoots(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeChildDiff("EjbReferenceDescriptions", this.bean.getEjbReferenceDescriptions(), otherTyped.getEjbReferenceDescriptions(), false);
            this.computeChildDiff("FastSwap", this.bean.getFastSwap(), otherTyped.getFastSwap(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("JASPICProvider", this.bean.getJASPICProvider(), otherTyped.getJASPICProvider());
            this.computeChildDiff("JspDescriptors", this.bean.getJspDescriptors(), otherTyped.getJspDescriptors(), true);
            this.computeChildDiff("LibraryRefs", this.bean.getLibraryRefs(), otherTyped.getLibraryRefs(), false);
            this.computeChildDiff("Loggings", this.bean.getLoggings(), otherTyped.getLoggings(), true);
            this.computeChildDiff("ManagedExecutorServices", this.bean.getManagedExecutorServices(), otherTyped.getManagedExecutorServices(), false);
            this.computeChildDiff("ManagedScheduledExecutorServices", this.bean.getManagedScheduledExecutorServices(), otherTyped.getManagedScheduledExecutorServices(), false);
            this.computeChildDiff("ManagedThreadFactories", this.bean.getManagedThreadFactories(), otherTyped.getManagedThreadFactories(), false);
            this.computeChildDiff("MessageDestinationDescriptors", this.bean.getMessageDestinationDescriptors(), otherTyped.getMessageDestinationDescriptors(), false);
            this.computeChildDiff("OsgiFrameworkReference", this.bean.getOsgiFrameworkReference(), otherTyped.getOsgiFrameworkReference(), false);
            this.computeDiff("ReadyRegistration", this.bean.getReadyRegistration(), otherTyped.getReadyRegistration(), false);
            this.computeChildDiff("ResourceDescriptions", this.bean.getResourceDescriptions(), otherTyped.getResourceDescriptions(), false);
            this.computeChildDiff("ResourceEnvDescriptions", this.bean.getResourceEnvDescriptions(), otherTyped.getResourceEnvDescriptions(), false);
            this.computeChildDiff("RunAsRoleAssignments", this.bean.getRunAsRoleAssignments(), otherTyped.getRunAsRoleAssignments(), false);
            this.computeChildDiff("SecurityPermissions", this.bean.getSecurityPermissions(), otherTyped.getSecurityPermissions(), false);
            this.computeChildDiff("SecurityRoleAssignments", this.bean.getSecurityRoleAssignments(), otherTyped.getSecurityRoleAssignments(), false);
            this.computeChildDiff("ServiceReferenceDescriptions", this.bean.getServiceReferenceDescriptions(), otherTyped.getServiceReferenceDescriptions(), false);
            this.computeChildDiff("ServletDescriptors", this.bean.getServletDescriptors(), otherTyped.getServletDescriptors(), false);
            this.computeChildDiff("SessionDescriptors", this.bean.getSessionDescriptors(), otherTyped.getSessionDescriptors(), true);
            this.computeDiff("UrlMatchMaps", this.bean.getUrlMatchMaps(), otherTyped.getUrlMatchMaps(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeChildDiff("VirtualDirectoryMappings", this.bean.getVirtualDirectoryMappings(), otherTyped.getVirtualDirectoryMappings(), false);
            this.computeDiff("WeblogicVersions", this.bean.getWeblogicVersions(), otherTyped.getWeblogicVersions(), false);
            this.computeDiff("WlDispatchPolicies", this.bean.getWlDispatchPolicies(), otherTyped.getWlDispatchPolicies(), false);
            this.computeChildDiff("WorkManagers", this.bean.getWorkManagers(), otherTyped.getWorkManagers(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicWebAppBeanImpl original = (WeblogicWebAppBeanImpl)event.getSourceBean();
            WeblogicWebAppBeanImpl proposed = (WeblogicWebAppBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AsyncDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAsyncDescriptor((AsyncDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAsyncDescriptor((AsyncDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getAsyncDescriptors() == null || original.getAsyncDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("AuthFilters")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addAuthFilter((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAuthFilter((String)update.getRemovedObject());
                  }

                  if (original.getAuthFilters() == null || original.getAuthFilters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("CdiDescriptor")) {
                  if (type == 2) {
                     original.setCdiDescriptor((CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getCdiDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CdiDescriptor", (DescriptorBean)original.getCdiDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("CharsetParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCharsetParams((CharsetParamsBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCharsetParams((CharsetParamsBean)update.getRemovedObject());
                  }

                  if (original.getCharsetParams() == null || original.getCharsetParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("CoherenceClusterRef")) {
                  if (type == 2) {
                     original.setCoherenceClusterRef((CoherenceClusterRefBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceClusterRef()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceClusterRef", (DescriptorBean)original.getCoherenceClusterRef());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("ComponentFactoryClassName")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addComponentFactoryClassName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeComponentFactoryClassName((String)update.getRemovedObject());
                  }

                  if (original.getComponentFactoryClassName() == null || original.getComponentFactoryClassName().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  }
               } else if (prop.equals("ContainerDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addContainerDescriptor((ContainerDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeContainerDescriptor((ContainerDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getContainerDescriptors() == null || original.getContainerDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("ContextRoots")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addContextRoot((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeContextRoot((String)update.getRemovedObject());
                  }

                  if (original.getContextRoots() == null || original.getContextRoots().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
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
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("FastSwap")) {
                  if (type == 2) {
                     original.setFastSwap((FastSwapBean)this.createCopy((AbstractDescriptorBean)proposed.getFastSwap()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FastSwap", (DescriptorBean)original.getFastSwap());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("JASPICProvider")) {
                  if (type == 2) {
                     original.setJASPICProvider((JASPICProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getJASPICProvider()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JASPICProvider", (DescriptorBean)original.getJASPICProvider());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("JspDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJspDescriptor((JspDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJspDescriptor((JspDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getJspDescriptors() == null || original.getJspDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("LibraryRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLibraryRef((LibraryRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLibraryRef((LibraryRefBean)update.getRemovedObject());
                  }

                  if (original.getLibraryRefs() == null || original.getLibraryRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  }
               } else if (prop.equals("Loggings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLogging((LoggingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLogging((LoggingBean)update.getRemovedObject());
                  }

                  if (original.getLoggings() == null || original.getLoggings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  }
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
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("OsgiFrameworkReference")) {
                  if (type == 2) {
                     original.setOsgiFrameworkReference((OsgiFrameworkReferenceBean)this.createCopy((AbstractDescriptorBean)proposed.getOsgiFrameworkReference()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("OsgiFrameworkReference", (DescriptorBean)original.getOsgiFrameworkReference());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("ReadyRegistration")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
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
               } else if (prop.equals("SecurityPermissions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityPermission((SecurityPermissionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityPermission((SecurityPermissionBean)update.getRemovedObject());
                  }

                  if (original.getSecurityPermissions() == null || original.getSecurityPermissions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
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
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("ServletDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServletDescriptor((ServletDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServletDescriptor((ServletDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getServletDescriptors() == null || original.getServletDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  }
               } else if (prop.equals("SessionDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSessionDescriptor((SessionDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSessionDescriptor((SessionDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getSessionDescriptors() == null || original.getSessionDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("UrlMatchMaps")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addUrlMatchMap((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeUrlMatchMap((String)update.getRemovedObject());
                  }

                  if (original.getUrlMatchMaps() == null || original.getUrlMatchMaps().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("VirtualDirectoryMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addVirtualDirectoryMapping((VirtualDirectoryMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeVirtualDirectoryMapping((VirtualDirectoryMappingBean)update.getRemovedObject());
                  }

                  if (original.getVirtualDirectoryMappings() == null || original.getVirtualDirectoryMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("WeblogicVersions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addWeblogicVersion((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWeblogicVersion((String)update.getRemovedObject());
                  }

                  if (original.getWeblogicVersions() == null || original.getWeblogicVersions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("WlDispatchPolicies")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addWlDispatchPolicy((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWlDispatchPolicy((String)update.getRemovedObject());
                  }

                  if (original.getWlDispatchPolicies() == null || original.getWlDispatchPolicies().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
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
            WeblogicWebAppBeanImpl copy = (WeblogicWebAppBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AsyncDescriptors")) && this.bean.isAsyncDescriptorsSet() && !copy._isSet(10)) {
               AsyncDescriptorBean[] oldAsyncDescriptors = this.bean.getAsyncDescriptors();
               AsyncDescriptorBean[] newAsyncDescriptors = new AsyncDescriptorBean[oldAsyncDescriptors.length];

               for(i = 0; i < newAsyncDescriptors.length; ++i) {
                  newAsyncDescriptors[i] = (AsyncDescriptorBean)((AsyncDescriptorBean)this.createCopy((AbstractDescriptorBean)oldAsyncDescriptors[i], includeObsolete));
               }

               copy.setAsyncDescriptors(newAsyncDescriptors);
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("AuthFilters")) && this.bean.isAuthFiltersSet()) {
               o = this.bean.getAuthFilters();
               copy.setAuthFilters(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CdiDescriptor")) && this.bean.isCdiDescriptorSet() && !copy._isSet(35)) {
               Object o = this.bean.getCdiDescriptor();
               copy.setCdiDescriptor((CdiDescriptorBean)null);
               copy.setCdiDescriptor(o == null ? null : (CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CharsetParams")) && this.bean.isCharsetParamsSet() && !copy._isSet(14)) {
               CharsetParamsBean[] oldCharsetParams = this.bean.getCharsetParams();
               CharsetParamsBean[] newCharsetParams = new CharsetParamsBean[oldCharsetParams.length];

               for(i = 0; i < newCharsetParams.length; ++i) {
                  newCharsetParams[i] = (CharsetParamsBean)((CharsetParamsBean)this.createCopy((AbstractDescriptorBean)oldCharsetParams[i], includeObsolete));
               }

               copy.setCharsetParams(newCharsetParams);
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterRef")) && this.bean.isCoherenceClusterRefSet() && !copy._isSet(30)) {
               Object o = this.bean.getCoherenceClusterRef();
               copy.setCoherenceClusterRef((CoherenceClusterRefBean)null);
               copy.setCoherenceClusterRef(o == null ? null : (CoherenceClusterRefBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ComponentFactoryClassName")) && this.bean.isComponentFactoryClassNameSet()) {
               o = this.bean.getComponentFactoryClassName();
               copy.setComponentFactoryClassName(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ContainerDescriptors")) && this.bean.isContainerDescriptorsSet() && !copy._isSet(12)) {
               ContainerDescriptorBean[] oldContainerDescriptors = this.bean.getContainerDescriptors();
               ContainerDescriptorBean[] newContainerDescriptors = new ContainerDescriptorBean[oldContainerDescriptors.length];

               for(i = 0; i < newContainerDescriptors.length; ++i) {
                  newContainerDescriptors[i] = (ContainerDescriptorBean)((ContainerDescriptorBean)this.createCopy((AbstractDescriptorBean)oldContainerDescriptors[i], includeObsolete));
               }

               copy.setContainerDescriptors(newContainerDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("ContextRoots")) && this.bean.isContextRootsSet()) {
               o = this.bean.getContextRoots();
               copy.setContextRoots(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbReferenceDescriptions")) && this.bean.isEjbReferenceDescriptionsSet() && !copy._isSet(6)) {
               EjbReferenceDescriptionBean[] oldEjbReferenceDescriptions = this.bean.getEjbReferenceDescriptions();
               EjbReferenceDescriptionBean[] newEjbReferenceDescriptions = new EjbReferenceDescriptionBean[oldEjbReferenceDescriptions.length];

               for(i = 0; i < newEjbReferenceDescriptions.length; ++i) {
                  newEjbReferenceDescriptions[i] = (EjbReferenceDescriptionBean)((EjbReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldEjbReferenceDescriptions[i], includeObsolete));
               }

               copy.setEjbReferenceDescriptions(newEjbReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("FastSwap")) && this.bean.isFastSwapSet() && !copy._isSet(29)) {
               Object o = this.bean.getFastSwap();
               copy.setFastSwap((FastSwapBean)null);
               copy.setFastSwap(o == null ? null : (FastSwapBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JASPICProvider")) && this.bean.isJASPICProviderSet() && !copy._isSet(28)) {
               Object o = this.bean.getJASPICProvider();
               copy.setJASPICProvider((JASPICProviderBean)null);
               copy.setJASPICProvider(o == null ? null : (JASPICProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JspDescriptors")) && this.bean.isJspDescriptorsSet() && !copy._isSet(11)) {
               JspDescriptorBean[] oldJspDescriptors = this.bean.getJspDescriptors();
               JspDescriptorBean[] newJspDescriptors = new JspDescriptorBean[oldJspDescriptors.length];

               for(i = 0; i < newJspDescriptors.length; ++i) {
                  newJspDescriptors[i] = (JspDescriptorBean)((JspDescriptorBean)this.createCopy((AbstractDescriptorBean)oldJspDescriptors[i], includeObsolete));
               }

               copy.setJspDescriptors(newJspDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("LibraryRefs")) && this.bean.isLibraryRefsSet() && !copy._isSet(27)) {
               LibraryRefBean[] oldLibraryRefs = this.bean.getLibraryRefs();
               LibraryRefBean[] newLibraryRefs = new LibraryRefBean[oldLibraryRefs.length];

               for(i = 0; i < newLibraryRefs.length; ++i) {
                  newLibraryRefs[i] = (LibraryRefBean)((LibraryRefBean)this.createCopy((AbstractDescriptorBean)oldLibraryRefs[i], includeObsolete));
               }

               copy.setLibraryRefs(newLibraryRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("Loggings")) && this.bean.isLoggingsSet() && !copy._isSet(26)) {
               LoggingBean[] oldLoggings = this.bean.getLoggings();
               LoggingBean[] newLoggings = new LoggingBean[oldLoggings.length];

               for(i = 0; i < newLoggings.length; ++i) {
                  newLoggings[i] = (LoggingBean)((LoggingBean)this.createCopy((AbstractDescriptorBean)oldLoggings[i], includeObsolete));
               }

               copy.setLoggings(newLoggings);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServices")) && this.bean.isManagedExecutorServicesSet() && !copy._isSet(22)) {
               ManagedExecutorServiceBean[] oldManagedExecutorServices = this.bean.getManagedExecutorServices();
               ManagedExecutorServiceBean[] newManagedExecutorServices = new ManagedExecutorServiceBean[oldManagedExecutorServices.length];

               for(i = 0; i < newManagedExecutorServices.length; ++i) {
                  newManagedExecutorServices[i] = (ManagedExecutorServiceBean)((ManagedExecutorServiceBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServices[i], includeObsolete));
               }

               copy.setManagedExecutorServices(newManagedExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServices")) && this.bean.isManagedScheduledExecutorServicesSet() && !copy._isSet(23)) {
               ManagedScheduledExecutorServiceBean[] oldManagedScheduledExecutorServices = this.bean.getManagedScheduledExecutorServices();
               ManagedScheduledExecutorServiceBean[] newManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceBean[oldManagedScheduledExecutorServices.length];

               for(i = 0; i < newManagedScheduledExecutorServices.length; ++i) {
                  newManagedScheduledExecutorServices[i] = (ManagedScheduledExecutorServiceBean)((ManagedScheduledExecutorServiceBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServices[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServices(newManagedScheduledExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactories")) && this.bean.isManagedThreadFactoriesSet() && !copy._isSet(24)) {
               ManagedThreadFactoryBean[] oldManagedThreadFactories = this.bean.getManagedThreadFactories();
               ManagedThreadFactoryBean[] newManagedThreadFactories = new ManagedThreadFactoryBean[oldManagedThreadFactories.length];

               for(i = 0; i < newManagedThreadFactories.length; ++i) {
                  newManagedThreadFactories[i] = (ManagedThreadFactoryBean)((ManagedThreadFactoryBean)this.createCopy((AbstractDescriptorBean)oldManagedThreadFactories[i], includeObsolete));
               }

               copy.setManagedThreadFactories(newManagedThreadFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationDescriptors")) && this.bean.isMessageDestinationDescriptorsSet() && !copy._isSet(8)) {
               MessageDestinationDescriptorBean[] oldMessageDestinationDescriptors = this.bean.getMessageDestinationDescriptors();
               MessageDestinationDescriptorBean[] newMessageDestinationDescriptors = new MessageDestinationDescriptorBean[oldMessageDestinationDescriptors.length];

               for(i = 0; i < newMessageDestinationDescriptors.length; ++i) {
                  newMessageDestinationDescriptors[i] = (MessageDestinationDescriptorBean)((MessageDestinationDescriptorBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationDescriptors[i], includeObsolete));
               }

               copy.setMessageDestinationDescriptors(newMessageDestinationDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("OsgiFrameworkReference")) && this.bean.isOsgiFrameworkReferenceSet() && !copy._isSet(33)) {
               Object o = this.bean.getOsgiFrameworkReference();
               copy.setOsgiFrameworkReference((OsgiFrameworkReferenceBean)null);
               copy.setOsgiFrameworkReference(o == null ? null : (OsgiFrameworkReferenceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ReadyRegistration")) && this.bean.isReadyRegistrationSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceDescriptions")) && this.bean.isResourceDescriptionsSet() && !copy._isSet(4)) {
               ResourceDescriptionBean[] oldResourceDescriptions = this.bean.getResourceDescriptions();
               ResourceDescriptionBean[] newResourceDescriptions = new ResourceDescriptionBean[oldResourceDescriptions.length];

               for(i = 0; i < newResourceDescriptions.length; ++i) {
                  newResourceDescriptions[i] = (ResourceDescriptionBean)((ResourceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceDescriptions[i], includeObsolete));
               }

               copy.setResourceDescriptions(newResourceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvDescriptions")) && this.bean.isResourceEnvDescriptionsSet() && !copy._isSet(5)) {
               ResourceEnvDescriptionBean[] oldResourceEnvDescriptions = this.bean.getResourceEnvDescriptions();
               ResourceEnvDescriptionBean[] newResourceEnvDescriptions = new ResourceEnvDescriptionBean[oldResourceEnvDescriptions.length];

               for(i = 0; i < newResourceEnvDescriptions.length; ++i) {
                  newResourceEnvDescriptions[i] = (ResourceEnvDescriptionBean)((ResourceEnvDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvDescriptions[i], includeObsolete));
               }

               copy.setResourceEnvDescriptions(newResourceEnvDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsRoleAssignments")) && this.bean.isRunAsRoleAssignmentsSet() && !copy._isSet(3)) {
               RunAsRoleAssignmentBean[] oldRunAsRoleAssignments = this.bean.getRunAsRoleAssignments();
               RunAsRoleAssignmentBean[] newRunAsRoleAssignments = new RunAsRoleAssignmentBean[oldRunAsRoleAssignments.length];

               for(i = 0; i < newRunAsRoleAssignments.length; ++i) {
                  newRunAsRoleAssignments[i] = (RunAsRoleAssignmentBean)((RunAsRoleAssignmentBean)this.createCopy((AbstractDescriptorBean)oldRunAsRoleAssignments[i], includeObsolete));
               }

               copy.setRunAsRoleAssignments(newRunAsRoleAssignments);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityPermissions")) && this.bean.isSecurityPermissionsSet() && !copy._isSet(17)) {
               SecurityPermissionBean[] oldSecurityPermissions = this.bean.getSecurityPermissions();
               SecurityPermissionBean[] newSecurityPermissions = new SecurityPermissionBean[oldSecurityPermissions.length];

               for(i = 0; i < newSecurityPermissions.length; ++i) {
                  newSecurityPermissions[i] = (SecurityPermissionBean)((SecurityPermissionBean)this.createCopy((AbstractDescriptorBean)oldSecurityPermissions[i], includeObsolete));
               }

               copy.setSecurityPermissions(newSecurityPermissions);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoleAssignments")) && this.bean.isSecurityRoleAssignmentsSet() && !copy._isSet(2)) {
               SecurityRoleAssignmentBean[] oldSecurityRoleAssignments = this.bean.getSecurityRoleAssignments();
               SecurityRoleAssignmentBean[] newSecurityRoleAssignments = new SecurityRoleAssignmentBean[oldSecurityRoleAssignments.length];

               for(i = 0; i < newSecurityRoleAssignments.length; ++i) {
                  newSecurityRoleAssignments[i] = (SecurityRoleAssignmentBean)((SecurityRoleAssignmentBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoleAssignments[i], includeObsolete));
               }

               copy.setSecurityRoleAssignments(newSecurityRoleAssignments);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceReferenceDescriptions")) && this.bean.isServiceReferenceDescriptionsSet() && !copy._isSet(7)) {
               ServiceReferenceDescriptionBean[] oldServiceReferenceDescriptions = this.bean.getServiceReferenceDescriptions();
               ServiceReferenceDescriptionBean[] newServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[oldServiceReferenceDescriptions.length];

               for(i = 0; i < newServiceReferenceDescriptions.length; ++i) {
                  newServiceReferenceDescriptions[i] = (ServiceReferenceDescriptionBean)((ServiceReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldServiceReferenceDescriptions[i], includeObsolete));
               }

               copy.setServiceReferenceDescriptions(newServiceReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ServletDescriptors")) && this.bean.isServletDescriptorsSet() && !copy._isSet(20)) {
               ServletDescriptorBean[] oldServletDescriptors = this.bean.getServletDescriptors();
               ServletDescriptorBean[] newServletDescriptors = new ServletDescriptorBean[oldServletDescriptors.length];

               for(i = 0; i < newServletDescriptors.length; ++i) {
                  newServletDescriptors[i] = (ServletDescriptorBean)((ServletDescriptorBean)this.createCopy((AbstractDescriptorBean)oldServletDescriptors[i], includeObsolete));
               }

               copy.setServletDescriptors(newServletDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("SessionDescriptors")) && this.bean.isSessionDescriptorsSet() && !copy._isSet(9)) {
               SessionDescriptorBean[] oldSessionDescriptors = this.bean.getSessionDescriptors();
               SessionDescriptorBean[] newSessionDescriptors = new SessionDescriptorBean[oldSessionDescriptors.length];

               for(i = 0; i < newSessionDescriptors.length; ++i) {
                  newSessionDescriptors[i] = (SessionDescriptorBean)((SessionDescriptorBean)this.createCopy((AbstractDescriptorBean)oldSessionDescriptors[i], includeObsolete));
               }

               copy.setSessionDescriptors(newSessionDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("UrlMatchMaps")) && this.bean.isUrlMatchMapsSet()) {
               o = this.bean.getUrlMatchMaps();
               copy.setUrlMatchMaps(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("VirtualDirectoryMappings")) && this.bean.isVirtualDirectoryMappingsSet() && !copy._isSet(15)) {
               VirtualDirectoryMappingBean[] oldVirtualDirectoryMappings = this.bean.getVirtualDirectoryMappings();
               VirtualDirectoryMappingBean[] newVirtualDirectoryMappings = new VirtualDirectoryMappingBean[oldVirtualDirectoryMappings.length];

               for(i = 0; i < newVirtualDirectoryMappings.length; ++i) {
                  newVirtualDirectoryMappings[i] = (VirtualDirectoryMappingBean)((VirtualDirectoryMappingBean)this.createCopy((AbstractDescriptorBean)oldVirtualDirectoryMappings[i], includeObsolete));
               }

               copy.setVirtualDirectoryMappings(newVirtualDirectoryMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicVersions")) && this.bean.isWeblogicVersionsSet()) {
               o = this.bean.getWeblogicVersions();
               copy.setWeblogicVersions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WlDispatchPolicies")) && this.bean.isWlDispatchPoliciesSet()) {
               o = this.bean.getWlDispatchPolicies();
               copy.setWlDispatchPolicies(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagers")) && this.bean.isWorkManagersSet() && !copy._isSet(21)) {
               WorkManagerBean[] oldWorkManagers = this.bean.getWorkManagers();
               WorkManagerBean[] newWorkManagers = new WorkManagerBean[oldWorkManagers.length];

               for(i = 0; i < newWorkManagers.length; ++i) {
                  newWorkManagers[i] = (WorkManagerBean)((WorkManagerBean)this.createCopy((AbstractDescriptorBean)oldWorkManagers[i], includeObsolete));
               }

               copy.setWorkManagers(newWorkManagers);
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
         this.inferSubTree(this.bean.getAsyncDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getCdiDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getCharsetParams(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterRef(), clazz, annotation);
         this.inferSubTree(this.bean.getContainerDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getFastSwap(), clazz, annotation);
         this.inferSubTree(this.bean.getJASPICProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getJspDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getLibraryRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getLoggings(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getOsgiFrameworkReference(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getRunAsRoleAssignments(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityPermissions(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoleAssignments(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getServletDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getSessionDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getVirtualDirectoryMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getWorkManagers(), clazz, annotation);
      }
   }
}
