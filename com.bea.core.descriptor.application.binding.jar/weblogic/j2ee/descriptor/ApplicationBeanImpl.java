package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.customizers.ApplicationBeanCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ApplicationBeanImpl extends AbstractDescriptorBean implements ApplicationBean, Serializable {
   private AdministeredObjectBean[] _AdministeredObjects;
   private String _ApplicationName;
   private ConnectionFactoryResourceBean[] _ConnectionFactories;
   private DataSourceBean[] _DataSources;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private EjbLocalRefBean[] _EjbLocalRefs;
   private EjbRefBean[] _EjbRefs;
   private EnvEntryBean[] _EnvEntries;
   private IconBean[] _Icons;
   private String _Id;
   private String _InitializeInOrder;
   private JmsConnectionFactoryBean[] _JmsConnectionFactories;
   private JmsDestinationBean[] _JmsDestinations;
   private String _LibraryDirectory;
   private MailSessionBean[] _MailSessions;
   private MessageDestinationRefBean[] _MessageDestinationRefs;
   private MessageDestinationBean[] _MessageDestinations;
   private ModuleBean[] _Modules;
   private PersistenceContextRefBean[] _PersistenceContextRefs;
   private PersistenceUnitRefBean[] _PersistenceUnitRefs;
   private ResourceEnvRefBean[] _ResourceEnvRefs;
   private ResourceRefBean[] _ResourceRefs;
   private SecurityRoleBean[] _SecurityRoles;
   private ServiceRefBean[] _ServiceRefs;
   private String _Version;
   private transient ApplicationBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ApplicationBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.descriptor.customizers.ApplicationBeanCustomizerFactory");
         this._customizer = (ApplicationBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ApplicationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.descriptor.customizers.ApplicationBeanCustomizerFactory");
         this._customizer = (ApplicationBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ApplicationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.descriptor.customizers.ApplicationBeanCustomizerFactory");
         this._customizer = (ApplicationBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getApplicationName() {
      return this._ApplicationName;
   }

   public boolean isApplicationNameInherited() {
      return false;
   }

   public boolean isApplicationNameSet() {
      return this._isSet(0);
   }

   public void setApplicationName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationName;
      this._ApplicationName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(1);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
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
      this._postSet(1, _oldVal, param0);
   }

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(2);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(2)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDisplayNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDisplayNames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDisplayName(String param0) {
      String[] _old = this.getDisplayNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDisplayNames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDisplayNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DisplayNames;
      this._DisplayNames = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addIcon(IconBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         IconBean[] _new;
         if (this._isSet(3)) {
            _new = (IconBean[])((IconBean[])this._getHelper()._extendArray(this.getIcons(), IconBean.class, param0));
         } else {
            _new = new IconBean[]{param0};
         }

         try {
            this.setIcons(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public IconBean[] getIcons() {
      return this._Icons;
   }

   public boolean isIconsInherited() {
      return false;
   }

   public boolean isIconsSet() {
      return this._isSet(3);
   }

   public void removeIcon(IconBean param0) {
      this.destroyIcon(param0);
   }

   public void setIcons(IconBean[] param0) throws InvalidAttributeValueException {
      IconBean[] param0 = param0 == null ? new IconBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      IconBean[] _oldVal = this._Icons;
      this._Icons = (IconBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public IconBean createIcon() {
      IconBeanImpl _val = new IconBeanImpl(this, -1);

      try {
         this.addIcon(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyIcon(IconBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         IconBean[] _old = this.getIcons();
         IconBean[] _new = (IconBean[])((IconBean[])this._getHelper()._removeElement(_old, IconBean.class, param0));
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
               this.setIcons(_new);
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

   public String getInitializeInOrder() {
      return this._InitializeInOrder;
   }

   public boolean isInitializeInOrderInherited() {
      return false;
   }

   public boolean isInitializeInOrderSet() {
      return this._isSet(4);
   }

   public void setInitializeInOrder(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitializeInOrder;
      this._InitializeInOrder = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addModule(ModuleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         ModuleBean[] _new;
         if (this._isSet(5)) {
            _new = (ModuleBean[])((ModuleBean[])this._getHelper()._extendArray(this.getModules(), ModuleBean.class, param0));
         } else {
            _new = new ModuleBean[]{param0};
         }

         try {
            this.setModules(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ModuleBean[] getModules() {
      return this._Modules;
   }

   public boolean isModulesInherited() {
      return false;
   }

   public boolean isModulesSet() {
      return this._isSet(5);
   }

   public void removeModule(ModuleBean param0) {
      this.destroyModule(param0);
   }

   public void setModules(ModuleBean[] param0) throws InvalidAttributeValueException {
      ModuleBean[] param0 = param0 == null ? new ModuleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ModuleBean[] _oldVal = this._Modules;
      this._Modules = (ModuleBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public ModuleBean createModule() {
      ModuleBeanImpl _val = new ModuleBeanImpl(this, -1);

      try {
         this.addModule(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyModule(ModuleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         ModuleBean[] _old = this.getModules();
         ModuleBean[] _new = (ModuleBean[])((ModuleBean[])this._getHelper()._removeElement(_old, ModuleBean.class, param0));
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
               this.setModules(_new);
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

   public void addSecurityRole(SecurityRoleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         SecurityRoleBean[] _new;
         if (this._isSet(6)) {
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
      return this._isSet(6);
   }

   public void removeSecurityRole(SecurityRoleBean param0) {
      this.destroySecurityRole(param0);
   }

   public void setSecurityRoles(SecurityRoleBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleBean[] param0 = param0 == null ? new SecurityRoleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleBean[] _oldVal = this._SecurityRoles;
      this._SecurityRoles = (SecurityRoleBean[])param0;
      this._postSet(6, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 6);
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

   public String getLibraryDirectory() {
      return this._LibraryDirectory;
   }

   public boolean isLibraryDirectoryInherited() {
      return false;
   }

   public boolean isLibraryDirectorySet() {
      return this._isSet(7);
   }

   public void setLibraryDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LibraryDirectory;
      this._LibraryDirectory = param0;
      this._postSet(7, _oldVal, param0);
   }

   public void addEnvEntry(EnvEntryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         EnvEntryBean[] _new;
         if (this._isSet(8)) {
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
      return this._isSet(8);
   }

   public void removeEnvEntry(EnvEntryBean param0) {
      EnvEntryBean[] _old = this.getEnvEntries();
      EnvEntryBean[] _new = (EnvEntryBean[])((EnvEntryBean[])this._getHelper()._removeElement(_old, EnvEntryBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setEnvEntries(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEnvEntries(EnvEntryBean[] param0) throws InvalidAttributeValueException {
      EnvEntryBean[] param0 = param0 == null ? new EnvEntryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._postCreate(_child);
         }
      }

      EnvEntryBean[] _oldVal = this._EnvEntries;
      this._EnvEntries = (EnvEntryBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public void addEjbRef(EjbRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         EjbRefBean[] _new;
         if (this._isSet(9)) {
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
      return this._isSet(9);
   }

   public void removeEjbRef(EjbRefBean param0) {
      EjbRefBean[] _old = this.getEjbRefs();
      EjbRefBean[] _new = (EjbRefBean[])((EjbRefBean[])this._getHelper()._removeElement(_old, EjbRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setEjbRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEjbRefs(EjbRefBean[] param0) throws InvalidAttributeValueException {
      EjbRefBean[] param0 = param0 == null ? new EjbRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._postCreate(_child);
         }
      }

      EjbRefBean[] _oldVal = this._EjbRefs;
      this._EjbRefs = (EjbRefBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public void addEjbLocalRef(EjbLocalRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         EjbLocalRefBean[] _new;
         if (this._isSet(10)) {
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
      return this._isSet(10);
   }

   public void removeEjbLocalRef(EjbLocalRefBean param0) {
      EjbLocalRefBean[] _old = this.getEjbLocalRefs();
      EjbLocalRefBean[] _new = (EjbLocalRefBean[])((EjbLocalRefBean[])this._getHelper()._removeElement(_old, EjbLocalRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setEjbLocalRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEjbLocalRefs(EjbLocalRefBean[] param0) throws InvalidAttributeValueException {
      EjbLocalRefBean[] param0 = param0 == null ? new EjbLocalRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._postCreate(_child);
         }
      }

      EjbLocalRefBean[] _oldVal = this._EjbLocalRefs;
      this._EjbLocalRefs = (EjbLocalRefBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public void addServiceRef(ServiceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ServiceRefBean[] _new;
         if (this._isSet(11)) {
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
      return this._isSet(11);
   }

   public void removeServiceRef(ServiceRefBean param0) {
      ServiceRefBean[] _old = this.getServiceRefs();
      ServiceRefBean[] _new = (ServiceRefBean[])((ServiceRefBean[])this._getHelper()._removeElement(_old, ServiceRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setServiceRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setServiceRefs(ServiceRefBean[] param0) throws InvalidAttributeValueException {
      ServiceRefBean[] param0 = param0 == null ? new ServiceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._postCreate(_child);
         }
      }

      ServiceRefBean[] _oldVal = this._ServiceRefs;
      this._ServiceRefs = (ServiceRefBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public void addResourceRef(ResourceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         ResourceRefBean[] _new;
         if (this._isSet(12)) {
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
      return this._isSet(12);
   }

   public void removeResourceRef(ResourceRefBean param0) {
      ResourceRefBean[] _old = this.getResourceRefs();
      ResourceRefBean[] _new = (ResourceRefBean[])((ResourceRefBean[])this._getHelper()._removeElement(_old, ResourceRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setResourceRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setResourceRefs(ResourceRefBean[] param0) throws InvalidAttributeValueException {
      ResourceRefBean[] param0 = param0 == null ? new ResourceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._postCreate(_child);
         }
      }

      ResourceRefBean[] _oldVal = this._ResourceRefs;
      this._ResourceRefs = (ResourceRefBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public void addResourceEnvRef(ResourceEnvRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         ResourceEnvRefBean[] _new;
         if (this._isSet(13)) {
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
      return this._isSet(13);
   }

   public void removeResourceEnvRef(ResourceEnvRefBean param0) {
      ResourceEnvRefBean[] _old = this.getResourceEnvRefs();
      ResourceEnvRefBean[] _new = (ResourceEnvRefBean[])((ResourceEnvRefBean[])this._getHelper()._removeElement(_old, ResourceEnvRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setResourceEnvRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setResourceEnvRefs(ResourceEnvRefBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvRefBean[] param0 = param0 == null ? new ResourceEnvRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._postCreate(_child);
         }
      }

      ResourceEnvRefBean[] _oldVal = this._ResourceEnvRefs;
      this._ResourceEnvRefs = (ResourceEnvRefBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public void addMessageDestinationRef(MessageDestinationRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         MessageDestinationRefBean[] _new;
         if (this._isSet(14)) {
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
      return this._isSet(14);
   }

   public void removeMessageDestinationRef(MessageDestinationRefBean param0) {
      MessageDestinationRefBean[] _old = this.getMessageDestinationRefs();
      MessageDestinationRefBean[] _new = (MessageDestinationRefBean[])((MessageDestinationRefBean[])this._getHelper()._removeElement(_old, MessageDestinationRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMessageDestinationRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMessageDestinationRefs(MessageDestinationRefBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationRefBean[] param0 = param0 == null ? new MessageDestinationRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._postCreate(_child);
         }
      }

      MessageDestinationRefBean[] _oldVal = this._MessageDestinationRefs;
      this._MessageDestinationRefs = (MessageDestinationRefBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public void addPersistenceContextRef(PersistenceContextRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         PersistenceContextRefBean[] _new;
         if (this._isSet(15)) {
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
      return this._isSet(15);
   }

   public void removePersistenceContextRef(PersistenceContextRefBean param0) {
      PersistenceContextRefBean[] _old = this.getPersistenceContextRefs();
      PersistenceContextRefBean[] _new = (PersistenceContextRefBean[])((PersistenceContextRefBean[])this._getHelper()._removeElement(_old, PersistenceContextRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setPersistenceContextRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setPersistenceContextRefs(PersistenceContextRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceContextRefBean[] param0 = param0 == null ? new PersistenceContextRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._postCreate(_child);
         }
      }

      PersistenceContextRefBean[] _oldVal = this._PersistenceContextRefs;
      this._PersistenceContextRefs = (PersistenceContextRefBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public void addPersistenceUnitRef(PersistenceUnitRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         PersistenceUnitRefBean[] _new;
         if (this._isSet(16)) {
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
      return this._isSet(16);
   }

   public void removePersistenceUnitRef(PersistenceUnitRefBean param0) {
      PersistenceUnitRefBean[] _old = this.getPersistenceUnitRefs();
      PersistenceUnitRefBean[] _new = (PersistenceUnitRefBean[])((PersistenceUnitRefBean[])this._getHelper()._removeElement(_old, PersistenceUnitRefBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setPersistenceUnitRefs(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setPersistenceUnitRefs(PersistenceUnitRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceUnitRefBean[] param0 = param0 == null ? new PersistenceUnitRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._postCreate(_child);
         }
      }

      PersistenceUnitRefBean[] _oldVal = this._PersistenceUnitRefs;
      this._PersistenceUnitRefs = (PersistenceUnitRefBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public void addMessageDestination(MessageDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         MessageDestinationBean[] _new;
         if (this._isSet(17)) {
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
      return this._isSet(17);
   }

   public void removeMessageDestination(MessageDestinationBean param0) {
      MessageDestinationBean[] _old = this.getMessageDestinations();
      MessageDestinationBean[] _new = (MessageDestinationBean[])((MessageDestinationBean[])this._getHelper()._removeElement(_old, MessageDestinationBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMessageDestinations(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMessageDestinations(MessageDestinationBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationBean[] param0 = param0 == null ? new MessageDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._postCreate(_child);
         }
      }

      MessageDestinationBean[] _oldVal = this._MessageDestinations;
      this._MessageDestinations = (MessageDestinationBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public void addDataSource(DataSourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         DataSourceBean[] _new;
         if (this._isSet(18)) {
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
      return this._isSet(18);
   }

   public void removeDataSource(DataSourceBean param0) {
      DataSourceBean[] _old = this.getDataSources();
      DataSourceBean[] _new = (DataSourceBean[])((DataSourceBean[])this._getHelper()._removeElement(_old, DataSourceBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setDataSources(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDataSources(DataSourceBean[] param0) throws InvalidAttributeValueException {
      DataSourceBean[] param0 = param0 == null ? new DataSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._postCreate(_child);
         }
      }

      DataSourceBean[] _oldVal = this._DataSources;
      this._DataSources = (DataSourceBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public void addJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         JmsConnectionFactoryBean[] _new;
         if (this._isSet(19)) {
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
      return this._isSet(19);
   }

   public void removeJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      JmsConnectionFactoryBean[] _old = this.getJmsConnectionFactories();
      JmsConnectionFactoryBean[] _new = (JmsConnectionFactoryBean[])((JmsConnectionFactoryBean[])this._getHelper()._removeElement(_old, JmsConnectionFactoryBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setJmsConnectionFactories(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setJmsConnectionFactories(JmsConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JmsConnectionFactoryBean[] param0 = param0 == null ? new JmsConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._postCreate(_child);
         }
      }

      JmsConnectionFactoryBean[] _oldVal = this._JmsConnectionFactories;
      this._JmsConnectionFactories = (JmsConnectionFactoryBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public void addJmsDestination(JmsDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         JmsDestinationBean[] _new;
         if (this._isSet(20)) {
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
      return this._isSet(20);
   }

   public void removeJmsDestination(JmsDestinationBean param0) {
      JmsDestinationBean[] _old = this.getJmsDestinations();
      JmsDestinationBean[] _new = (JmsDestinationBean[])((JmsDestinationBean[])this._getHelper()._removeElement(_old, JmsDestinationBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setJmsDestinations(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setJmsDestinations(JmsDestinationBean[] param0) throws InvalidAttributeValueException {
      JmsDestinationBean[] param0 = param0 == null ? new JmsDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._postCreate(_child);
         }
      }

      JmsDestinationBean[] _oldVal = this._JmsDestinations;
      this._JmsDestinations = (JmsDestinationBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public void addMailSession(MailSessionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         MailSessionBean[] _new;
         if (this._isSet(21)) {
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
      return this._isSet(21);
   }

   public void removeMailSession(MailSessionBean param0) {
      MailSessionBean[] _old = this.getMailSessions();
      MailSessionBean[] _new = (MailSessionBean[])((MailSessionBean[])this._getHelper()._removeElement(_old, MailSessionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMailSessions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMailSessions(MailSessionBean[] param0) throws InvalidAttributeValueException {
      MailSessionBean[] param0 = param0 == null ? new MailSessionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._postCreate(_child);
         }
      }

      MailSessionBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionBean[])param0;
      this._postSet(21, _oldVal, param0);
   }

   public void addConnectionFactory(ConnectionFactoryResourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         ConnectionFactoryResourceBean[] _new;
         if (this._isSet(22)) {
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
      return this._isSet(22);
   }

   public void removeConnectionFactory(ConnectionFactoryResourceBean param0) {
      ConnectionFactoryResourceBean[] _old = this.getConnectionFactories();
      ConnectionFactoryResourceBean[] _new = (ConnectionFactoryResourceBean[])((ConnectionFactoryResourceBean[])this._getHelper()._removeElement(_old, ConnectionFactoryResourceBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setConnectionFactories(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setConnectionFactories(ConnectionFactoryResourceBean[] param0) throws InvalidAttributeValueException {
      ConnectionFactoryResourceBean[] param0 = param0 == null ? new ConnectionFactoryResourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._postCreate(_child);
         }
      }

      ConnectionFactoryResourceBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (ConnectionFactoryResourceBean[])param0;
      this._postSet(22, _oldVal, param0);
   }

   public void addAdministeredObject(AdministeredObjectBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         AdministeredObjectBean[] _new;
         if (this._isSet(23)) {
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
      return this._isSet(23);
   }

   public void removeAdministeredObject(AdministeredObjectBean param0) {
      AdministeredObjectBean[] _old = this.getAdministeredObjects();
      AdministeredObjectBean[] _new = (AdministeredObjectBean[])((AdministeredObjectBean[])this._getHelper()._removeElement(_old, AdministeredObjectBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setAdministeredObjects(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAdministeredObjects(AdministeredObjectBean[] param0) throws InvalidAttributeValueException {
      AdministeredObjectBean[] param0 = param0 == null ? new AdministeredObjectBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._postCreate(_child);
         }
      }

      AdministeredObjectBean[] _oldVal = this._AdministeredObjects;
      this._AdministeredObjects = (AdministeredObjectBean[])param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(24);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(24, _oldVal, param0);
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

   public J2eeEnvironmentBean convertToJ2eeEnvironmentBean() {
      return this._customizer.convertToJ2eeEnvironmentBean();
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _isChildPropertyAKey(String s) {
      switch (s.length()) {
         case 4:
            if (s.equals("icon")) {
               return true;
            }

            return false;
         default:
            return false;
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
      return super._isAnythingSet() || this.isAdministeredObjectsSet() || this.isConnectionFactoriesSet() || this.isDataSourcesSet() || this.isEjbLocalRefsSet() || this.isEjbRefsSet() || this.isEnvEntriesSet() || this.isJmsConnectionFactoriesSet() || this.isJmsDestinationsSet() || this.isMailSessionsSet() || this.isMessageDestinationRefsSet() || this.isMessageDestinationsSet() || this.isPersistenceContextRefsSet() || this.isPersistenceUnitRefsSet() || this.isResourceEnvRefsSet() || this.isResourceRefsSet() || this.isServiceRefsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 23;
      }

      try {
         switch (idx) {
            case 23:
               this._AdministeredObjects = new AdministeredObjectBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ApplicationName = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._ConnectionFactories = new ConnectionFactoryResourceBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._DataSources = new DataSourceBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._EjbLocalRefs = new EjbLocalRefBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._EjbRefs = new EjbRefBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._EnvEntries = new EnvEntryBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._InitializeInOrder = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._JmsConnectionFactories = new JmsConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._JmsDestinations = new JmsDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._LibraryDirectory = "lib";
               if (initOne) {
                  break;
               }
            case 21:
               this._MailSessions = new MailSessionBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._MessageDestinationRefs = new MessageDestinationRefBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._MessageDestinations = new MessageDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Modules = new ModuleBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._PersistenceContextRefs = new PersistenceContextRefBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._PersistenceUnitRefs = new PersistenceUnitRefBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._ResourceEnvRefs = new ResourceEnvRefBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ResourceRefs = new ResourceRefBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._SecurityRoles = new SecurityRoleBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._ServiceRefs = new ServiceRefBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._Version = "8";
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
            case 5:
            case 8:
            case 10:
            case 14:
            case 21:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 3;
               }
               break;
            case 6:
               if (s.equals("module")) {
                  return 5;
               }
               break;
            case 7:
               if (s.equals("ejb-ref")) {
                  return 9;
               }

               if (s.equals("version")) {
                  return 24;
               }
               break;
            case 9:
               if (s.equals("env-entry")) {
                  return 8;
               }
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 18;
               }

               if (s.equals("description")) {
                  return 1;
               }

               if (s.equals("service-ref")) {
                  return 11;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 2;
               }

               if (s.equals("mail-session")) {
                  return 21;
               }

               if (s.equals("resource-ref")) {
                  return 12;
               }
               break;
            case 13:
               if (s.equals("ejb-local-ref")) {
                  return 10;
               }

               if (s.equals("security-role")) {
                  return 6;
               }
               break;
            case 15:
               if (s.equals("jms-destination")) {
                  return 20;
               }
               break;
            case 16:
               if (s.equals("application-name")) {
                  return 0;
               }

               if (s.equals("resource-env-ref")) {
                  return 13;
               }
               break;
            case 17:
               if (s.equals("library-directory")) {
                  return 7;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 22;
               }
               break;
            case 19:
               if (s.equals("administered-object")) {
                  return 23;
               }

               if (s.equals("initialize-in-order")) {
                  return 4;
               }

               if (s.equals("message-destination")) {
                  return 17;
               }
               break;
            case 20:
               if (s.equals("persistence-unit-ref")) {
                  return 16;
               }
               break;
            case 22:
               if (s.equals("jms-connection-factory")) {
                  return 19;
               }
               break;
            case 23:
               if (s.equals("message-destination-ref")) {
                  return 14;
               }

               if (s.equals("persistence-context-ref")) {
                  return 15;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new IconBeanImpl.SchemaHelper2();
            case 4:
            case 7:
            default:
               return super.getSchemaHelper(propIndex);
            case 5:
               return new ModuleBeanImpl.SchemaHelper2();
            case 6:
               return new SecurityRoleBeanImpl.SchemaHelper2();
            case 8:
               return new EnvEntryBeanImpl.SchemaHelper2();
            case 9:
               return new EjbRefBeanImpl.SchemaHelper2();
            case 10:
               return new EjbLocalRefBeanImpl.SchemaHelper2();
            case 11:
               return new ServiceRefBeanImpl.SchemaHelper2();
            case 12:
               return new ResourceRefBeanImpl.SchemaHelper2();
            case 13:
               return new ResourceEnvRefBeanImpl.SchemaHelper2();
            case 14:
               return new MessageDestinationRefBeanImpl.SchemaHelper2();
            case 15:
               return new PersistenceContextRefBeanImpl.SchemaHelper2();
            case 16:
               return new PersistenceUnitRefBeanImpl.SchemaHelper2();
            case 17:
               return new MessageDestinationBeanImpl.SchemaHelper2();
            case 18:
               return new DataSourceBeanImpl.SchemaHelper2();
            case 19:
               return new JmsConnectionFactoryBeanImpl.SchemaHelper2();
            case 20:
               return new JmsDestinationBeanImpl.SchemaHelper2();
            case 21:
               return new MailSessionBeanImpl.SchemaHelper2();
            case 22:
               return new ConnectionFactoryResourceBeanImpl.SchemaHelper2();
            case 23:
               return new AdministeredObjectBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "application";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "application-name";
            case 1:
               return "description";
            case 2:
               return "display-name";
            case 3:
               return "icon";
            case 4:
               return "initialize-in-order";
            case 5:
               return "module";
            case 6:
               return "security-role";
            case 7:
               return "library-directory";
            case 8:
               return "env-entry";
            case 9:
               return "ejb-ref";
            case 10:
               return "ejb-local-ref";
            case 11:
               return "service-ref";
            case 12:
               return "resource-ref";
            case 13:
               return "resource-env-ref";
            case 14:
               return "message-destination-ref";
            case 15:
               return "persistence-context-ref";
            case 16:
               return "persistence-unit-ref";
            case 17:
               return "message-destination";
            case 18:
               return "data-source";
            case 19:
               return "jms-connection-factory";
            case 20:
               return "jms-destination";
            case 21:
               return "mail-session";
            case 22:
               return "connection-factory";
            case 23:
               return "administered-object";
            case 24:
               return "version";
            case 25:
               return "id";
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
            default:
               return super.isArray(propIndex);
            case 5:
               return true;
            case 6:
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
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
            case 7:
            default:
               return super.isBean(propIndex);
            case 5:
               return true;
            case 6:
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
         }
      }

      public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isMergeRuleIgnoreTargetDefined(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ApplicationBeanImpl bean;

      protected Helper(ApplicationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ApplicationName";
            case 1:
               return "Descriptions";
            case 2:
               return "DisplayNames";
            case 3:
               return "Icons";
            case 4:
               return "InitializeInOrder";
            case 5:
               return "Modules";
            case 6:
               return "SecurityRoles";
            case 7:
               return "LibraryDirectory";
            case 8:
               return "EnvEntries";
            case 9:
               return "EjbRefs";
            case 10:
               return "EjbLocalRefs";
            case 11:
               return "ServiceRefs";
            case 12:
               return "ResourceRefs";
            case 13:
               return "ResourceEnvRefs";
            case 14:
               return "MessageDestinationRefs";
            case 15:
               return "PersistenceContextRefs";
            case 16:
               return "PersistenceUnitRefs";
            case 17:
               return "MessageDestinations";
            case 18:
               return "DataSources";
            case 19:
               return "JmsConnectionFactories";
            case 20:
               return "JmsDestinations";
            case 21:
               return "MailSessions";
            case 22:
               return "ConnectionFactories";
            case 23:
               return "AdministeredObjects";
            case 24:
               return "Version";
            case 25:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdministeredObjects")) {
            return 23;
         } else if (propName.equals("ApplicationName")) {
            return 0;
         } else if (propName.equals("ConnectionFactories")) {
            return 22;
         } else if (propName.equals("DataSources")) {
            return 18;
         } else if (propName.equals("Descriptions")) {
            return 1;
         } else if (propName.equals("DisplayNames")) {
            return 2;
         } else if (propName.equals("EjbLocalRefs")) {
            return 10;
         } else if (propName.equals("EjbRefs")) {
            return 9;
         } else if (propName.equals("EnvEntries")) {
            return 8;
         } else if (propName.equals("Icons")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 25;
         } else if (propName.equals("InitializeInOrder")) {
            return 4;
         } else if (propName.equals("JmsConnectionFactories")) {
            return 19;
         } else if (propName.equals("JmsDestinations")) {
            return 20;
         } else if (propName.equals("LibraryDirectory")) {
            return 7;
         } else if (propName.equals("MailSessions")) {
            return 21;
         } else if (propName.equals("MessageDestinationRefs")) {
            return 14;
         } else if (propName.equals("MessageDestinations")) {
            return 17;
         } else if (propName.equals("Modules")) {
            return 5;
         } else if (propName.equals("PersistenceContextRefs")) {
            return 15;
         } else if (propName.equals("PersistenceUnitRefs")) {
            return 16;
         } else if (propName.equals("ResourceEnvRefs")) {
            return 13;
         } else if (propName.equals("ResourceRefs")) {
            return 12;
         } else if (propName.equals("SecurityRoles")) {
            return 6;
         } else if (propName.equals("ServiceRefs")) {
            return 11;
         } else {
            return propName.equals("Version") ? 24 : super.getPropertyIndex(propName);
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
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         iterators.add(new ArrayIterator(this.bean.getJmsConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getJmsDestinations()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationRefs()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinations()));
         iterators.add(new ArrayIterator(this.bean.getModules()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceContextRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceRefs()));
         iterators.add(new ArrayIterator(this.bean.getSecurityRoles()));
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

            if (this.bean.isApplicationNameSet()) {
               buf.append("ApplicationName");
               buf.append(String.valueOf(this.bean.getApplicationName()));
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

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isDisplayNamesSet()) {
               buf.append("DisplayNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisplayNames())));
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

            for(i = 0; i < this.bean.getIcons().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getIcons()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInitializeInOrderSet()) {
               buf.append("InitializeInOrder");
               buf.append(String.valueOf(this.bean.getInitializeInOrder()));
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

            if (this.bean.isLibraryDirectorySet()) {
               buf.append("LibraryDirectory");
               buf.append(String.valueOf(this.bean.getLibraryDirectory()));
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

            for(i = 0; i < this.bean.getMessageDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getModules().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getModules()[i]);
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

            for(i = 0; i < this.bean.getSecurityRoles().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoles()[i]);
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
            ApplicationBeanImpl otherTyped = (ApplicationBeanImpl)other;
            this.computeChildDiff("AdministeredObjects", this.bean.getAdministeredObjects(), otherTyped.getAdministeredObjects(), false);
            this.computeDiff("ApplicationName", this.bean.getApplicationName(), otherTyped.getApplicationName(), false);
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), false);
            this.computeChildDiff("DataSources", this.bean.getDataSources(), otherTyped.getDataSources(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeChildDiff("EjbLocalRefs", this.bean.getEjbLocalRefs(), otherTyped.getEjbLocalRefs(), false);
            this.computeChildDiff("EjbRefs", this.bean.getEjbRefs(), otherTyped.getEjbRefs(), false);
            this.computeChildDiff("EnvEntries", this.bean.getEnvEntries(), otherTyped.getEnvEntries(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InitializeInOrder", this.bean.getInitializeInOrder(), otherTyped.getInitializeInOrder(), false);
            this.computeChildDiff("JmsConnectionFactories", this.bean.getJmsConnectionFactories(), otherTyped.getJmsConnectionFactories(), false);
            this.computeChildDiff("JmsDestinations", this.bean.getJmsDestinations(), otherTyped.getJmsDestinations(), false);
            this.computeDiff("LibraryDirectory", this.bean.getLibraryDirectory(), otherTyped.getLibraryDirectory(), false);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), false);
            this.computeChildDiff("MessageDestinationRefs", this.bean.getMessageDestinationRefs(), otherTyped.getMessageDestinationRefs(), false);
            this.computeChildDiff("MessageDestinations", this.bean.getMessageDestinations(), otherTyped.getMessageDestinations(), false);
            this.computeChildDiff("Modules", this.bean.getModules(), otherTyped.getModules(), false);
            this.computeChildDiff("PersistenceContextRefs", this.bean.getPersistenceContextRefs(), otherTyped.getPersistenceContextRefs(), false);
            this.computeChildDiff("PersistenceUnitRefs", this.bean.getPersistenceUnitRefs(), otherTyped.getPersistenceUnitRefs(), false);
            this.computeChildDiff("ResourceEnvRefs", this.bean.getResourceEnvRefs(), otherTyped.getResourceEnvRefs(), false);
            this.computeChildDiff("ResourceRefs", this.bean.getResourceRefs(), otherTyped.getResourceRefs(), false);
            this.computeChildDiff("SecurityRoles", this.bean.getSecurityRoles(), otherTyped.getSecurityRoles(), false);
            this.computeChildDiff("ServiceRefs", this.bean.getServiceRefs(), otherTyped.getServiceRefs(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ApplicationBeanImpl original = (ApplicationBeanImpl)event.getSourceBean();
            ApplicationBeanImpl proposed = (ApplicationBeanImpl)event.getProposedBean();
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
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  }
               } else if (prop.equals("ApplicationName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("DisplayNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDisplayName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDisplayName((String)update.getRemovedObject());
                  }

                  if (original.getDisplayNames() == null || original.getDisplayNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("Icons")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addIcon((IconBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeIcon((IconBean)update.getRemovedObject());
                  }

                  if (original.getIcons() == null || original.getIcons().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("InitializeInOrder")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  }
               } else if (prop.equals("LibraryDirectory")) {
                  original.setLibraryDirectory(proposed.getLibraryDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("Modules")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addModule((ModuleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeModule((ModuleBean)update.getRemovedObject());
                  }

                  if (original.getModules() == null || original.getModules().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
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
            ApplicationBeanImpl copy = (ApplicationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AdministeredObjects")) && this.bean.isAdministeredObjectsSet() && !copy._isSet(23)) {
               AdministeredObjectBean[] oldAdministeredObjects = this.bean.getAdministeredObjects();
               AdministeredObjectBean[] newAdministeredObjects = new AdministeredObjectBean[oldAdministeredObjects.length];

               for(i = 0; i < newAdministeredObjects.length; ++i) {
                  newAdministeredObjects[i] = (AdministeredObjectBean)((AdministeredObjectBean)this.createCopy((AbstractDescriptorBean)oldAdministeredObjects[i], includeObsolete));
               }

               copy.setAdministeredObjects(newAdministeredObjects);
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationName")) && this.bean.isApplicationNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(22)) {
               ConnectionFactoryResourceBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               ConnectionFactoryResourceBean[] newConnectionFactories = new ConnectionFactoryResourceBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (ConnectionFactoryResourceBean)((ConnectionFactoryResourceBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSources")) && this.bean.isDataSourcesSet() && !copy._isSet(18)) {
               DataSourceBean[] oldDataSources = this.bean.getDataSources();
               DataSourceBean[] newDataSources = new DataSourceBean[oldDataSources.length];

               for(i = 0; i < newDataSources.length; ++i) {
                  newDataSources[i] = (DataSourceBean)((DataSourceBean)this.createCopy((AbstractDescriptorBean)oldDataSources[i], includeObsolete));
               }

               copy.setDataSources(newDataSources);
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbLocalRefs")) && this.bean.isEjbLocalRefsSet() && !copy._isSet(10)) {
               EjbLocalRefBean[] oldEjbLocalRefs = this.bean.getEjbLocalRefs();
               EjbLocalRefBean[] newEjbLocalRefs = new EjbLocalRefBean[oldEjbLocalRefs.length];

               for(i = 0; i < newEjbLocalRefs.length; ++i) {
                  newEjbLocalRefs[i] = (EjbLocalRefBean)((EjbLocalRefBean)this.createCopy((AbstractDescriptorBean)oldEjbLocalRefs[i], includeObsolete));
               }

               copy.setEjbLocalRefs(newEjbLocalRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRefs")) && this.bean.isEjbRefsSet() && !copy._isSet(9)) {
               EjbRefBean[] oldEjbRefs = this.bean.getEjbRefs();
               EjbRefBean[] newEjbRefs = new EjbRefBean[oldEjbRefs.length];

               for(i = 0; i < newEjbRefs.length; ++i) {
                  newEjbRefs[i] = (EjbRefBean)((EjbRefBean)this.createCopy((AbstractDescriptorBean)oldEjbRefs[i], includeObsolete));
               }

               copy.setEjbRefs(newEjbRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntries")) && this.bean.isEnvEntriesSet() && !copy._isSet(8)) {
               EnvEntryBean[] oldEnvEntries = this.bean.getEnvEntries();
               EnvEntryBean[] newEnvEntries = new EnvEntryBean[oldEnvEntries.length];

               for(i = 0; i < newEnvEntries.length; ++i) {
                  newEnvEntries[i] = (EnvEntryBean)((EnvEntryBean)this.createCopy((AbstractDescriptorBean)oldEnvEntries[i], includeObsolete));
               }

               copy.setEnvEntries(newEnvEntries);
            }

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(3)) {
               IconBean[] oldIcons = this.bean.getIcons();
               IconBean[] newIcons = new IconBean[oldIcons.length];

               for(i = 0; i < newIcons.length; ++i) {
                  newIcons[i] = (IconBean)((IconBean)this.createCopy((AbstractDescriptorBean)oldIcons[i], includeObsolete));
               }

               copy.setIcons(newIcons);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InitializeInOrder")) && this.bean.isInitializeInOrderSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("JmsConnectionFactories")) && this.bean.isJmsConnectionFactoriesSet() && !copy._isSet(19)) {
               JmsConnectionFactoryBean[] oldJmsConnectionFactories = this.bean.getJmsConnectionFactories();
               JmsConnectionFactoryBean[] newJmsConnectionFactories = new JmsConnectionFactoryBean[oldJmsConnectionFactories.length];

               for(i = 0; i < newJmsConnectionFactories.length; ++i) {
                  newJmsConnectionFactories[i] = (JmsConnectionFactoryBean)((JmsConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldJmsConnectionFactories[i], includeObsolete));
               }

               copy.setJmsConnectionFactories(newJmsConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsDestinations")) && this.bean.isJmsDestinationsSet() && !copy._isSet(20)) {
               JmsDestinationBean[] oldJmsDestinations = this.bean.getJmsDestinations();
               JmsDestinationBean[] newJmsDestinations = new JmsDestinationBean[oldJmsDestinations.length];

               for(i = 0; i < newJmsDestinations.length; ++i) {
                  newJmsDestinations[i] = (JmsDestinationBean)((JmsDestinationBean)this.createCopy((AbstractDescriptorBean)oldJmsDestinations[i], includeObsolete));
               }

               copy.setJmsDestinations(newJmsDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("LibraryDirectory")) && this.bean.isLibraryDirectorySet()) {
               copy.setLibraryDirectory(this.bean.getLibraryDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(21)) {
               MailSessionBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionBean[] newMailSessions = new MailSessionBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionBean)((MailSessionBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationRefs")) && this.bean.isMessageDestinationRefsSet() && !copy._isSet(14)) {
               MessageDestinationRefBean[] oldMessageDestinationRefs = this.bean.getMessageDestinationRefs();
               MessageDestinationRefBean[] newMessageDestinationRefs = new MessageDestinationRefBean[oldMessageDestinationRefs.length];

               for(i = 0; i < newMessageDestinationRefs.length; ++i) {
                  newMessageDestinationRefs[i] = (MessageDestinationRefBean)((MessageDestinationRefBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationRefs[i], includeObsolete));
               }

               copy.setMessageDestinationRefs(newMessageDestinationRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinations")) && this.bean.isMessageDestinationsSet() && !copy._isSet(17)) {
               MessageDestinationBean[] oldMessageDestinations = this.bean.getMessageDestinations();
               MessageDestinationBean[] newMessageDestinations = new MessageDestinationBean[oldMessageDestinations.length];

               for(i = 0; i < newMessageDestinations.length; ++i) {
                  newMessageDestinations[i] = (MessageDestinationBean)((MessageDestinationBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinations[i], includeObsolete));
               }

               copy.setMessageDestinations(newMessageDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("Modules")) && this.bean.isModulesSet() && !copy._isSet(5)) {
               ModuleBean[] oldModules = this.bean.getModules();
               ModuleBean[] newModules = new ModuleBean[oldModules.length];

               for(i = 0; i < newModules.length; ++i) {
                  newModules[i] = (ModuleBean)((ModuleBean)this.createCopy((AbstractDescriptorBean)oldModules[i], includeObsolete));
               }

               copy.setModules(newModules);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextRefs")) && this.bean.isPersistenceContextRefsSet() && !copy._isSet(15)) {
               PersistenceContextRefBean[] oldPersistenceContextRefs = this.bean.getPersistenceContextRefs();
               PersistenceContextRefBean[] newPersistenceContextRefs = new PersistenceContextRefBean[oldPersistenceContextRefs.length];

               for(i = 0; i < newPersistenceContextRefs.length; ++i) {
                  newPersistenceContextRefs[i] = (PersistenceContextRefBean)((PersistenceContextRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceContextRefs[i], includeObsolete));
               }

               copy.setPersistenceContextRefs(newPersistenceContextRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitRefs")) && this.bean.isPersistenceUnitRefsSet() && !copy._isSet(16)) {
               PersistenceUnitRefBean[] oldPersistenceUnitRefs = this.bean.getPersistenceUnitRefs();
               PersistenceUnitRefBean[] newPersistenceUnitRefs = new PersistenceUnitRefBean[oldPersistenceUnitRefs.length];

               for(i = 0; i < newPersistenceUnitRefs.length; ++i) {
                  newPersistenceUnitRefs[i] = (PersistenceUnitRefBean)((PersistenceUnitRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceUnitRefs[i], includeObsolete));
               }

               copy.setPersistenceUnitRefs(newPersistenceUnitRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvRefs")) && this.bean.isResourceEnvRefsSet() && !copy._isSet(13)) {
               ResourceEnvRefBean[] oldResourceEnvRefs = this.bean.getResourceEnvRefs();
               ResourceEnvRefBean[] newResourceEnvRefs = new ResourceEnvRefBean[oldResourceEnvRefs.length];

               for(i = 0; i < newResourceEnvRefs.length; ++i) {
                  newResourceEnvRefs[i] = (ResourceEnvRefBean)((ResourceEnvRefBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvRefs[i], includeObsolete));
               }

               copy.setResourceEnvRefs(newResourceEnvRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceRefs")) && this.bean.isResourceRefsSet() && !copy._isSet(12)) {
               ResourceRefBean[] oldResourceRefs = this.bean.getResourceRefs();
               ResourceRefBean[] newResourceRefs = new ResourceRefBean[oldResourceRefs.length];

               for(i = 0; i < newResourceRefs.length; ++i) {
                  newResourceRefs[i] = (ResourceRefBean)((ResourceRefBean)this.createCopy((AbstractDescriptorBean)oldResourceRefs[i], includeObsolete));
               }

               copy.setResourceRefs(newResourceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoles")) && this.bean.isSecurityRolesSet() && !copy._isSet(6)) {
               SecurityRoleBean[] oldSecurityRoles = this.bean.getSecurityRoles();
               SecurityRoleBean[] newSecurityRoles = new SecurityRoleBean[oldSecurityRoles.length];

               for(i = 0; i < newSecurityRoles.length; ++i) {
                  newSecurityRoles[i] = (SecurityRoleBean)((SecurityRoleBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoles[i], includeObsolete));
               }

               copy.setSecurityRoles(newSecurityRoles);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefs")) && this.bean.isServiceRefsSet() && !copy._isSet(11)) {
               ServiceRefBean[] oldServiceRefs = this.bean.getServiceRefs();
               ServiceRefBean[] newServiceRefs = new ServiceRefBean[oldServiceRefs.length];

               for(i = 0; i < newServiceRefs.length; ++i) {
                  newServiceRefs[i] = (ServiceRefBean)((ServiceRefBean)this.createCopy((AbstractDescriptorBean)oldServiceRefs[i], includeObsolete));
               }

               copy.setServiceRefs(newServiceRefs);
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
         this.inferSubTree(this.bean.getAdministeredObjects(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSources(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbLocalRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEnvEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getMailSessions(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getModules(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceContextRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceUnitRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoles(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceRefs(), clazz, annotation);
      }
   }
}
