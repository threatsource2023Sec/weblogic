package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EntityBeanBeanImpl extends AbstractDescriptorBean implements EntityBeanBean, Serializable {
   private String _AbstractSchemaName;
   private AdministeredObjectBean[] _AdministeredObjects;
   private CmpFieldBean[] _CmpFields;
   private String _CmpVersion;
   private ConnectionFactoryResourceBean[] _ConnectionFactories;
   private DataSourceBean[] _DataSources;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private String _EjbClass;
   private EjbLocalRefBean[] _EjbLocalRefs;
   private String _EjbName;
   private EjbRefBean[] _EjbRefs;
   private EnvEntryBean[] _EnvEntries;
   private String _Home;
   private IconBean[] _Icons;
   private String _Id;
   private JmsConnectionFactoryBean[] _JmsConnectionFactories;
   private JmsDestinationBean[] _JmsDestinations;
   private String _Local;
   private String _LocalHome;
   private MailSessionBean[] _MailSessions;
   private String _MappedName;
   private MessageDestinationRefBean[] _MessageDestinationRefs;
   private PersistenceContextRefBean[] _PersistenceContextRefs;
   private String _PersistenceType;
   private PersistenceUnitRefBean[] _PersistenceUnitRefs;
   private LifecycleCallbackBean[] _PostConstructs;
   private LifecycleCallbackBean[] _PreDestroys;
   private String _PrimKeyClass;
   private String _PrimkeyField;
   private QueryBean[] _Queries;
   private boolean _Reentrant;
   private String _Remote;
   private ResourceEnvRefBean[] _ResourceEnvRefs;
   private ResourceRefBean[] _ResourceRefs;
   private SecurityIdentityBean _SecurityIdentity;
   private SecurityRoleRefBean[] _SecurityRoleRefs;
   private ServiceRefBean[] _ServiceRefs;
   private static SchemaHelper2 _schemaHelper;

   public EntityBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public EntityBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EntityBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
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

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(1);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
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
      this._postSet(1, _oldVal, param0);
   }

   public void addIcon(IconBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         IconBean[] _new;
         if (this._isSet(2)) {
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
      return this._isSet(2);
   }

   public void removeIcon(IconBean param0) {
      this.destroyIcon(param0);
   }

   public void setIcons(IconBean[] param0) throws InvalidAttributeValueException {
      IconBean[] param0 = param0 == null ? new IconBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      IconBean[] _oldVal = this._Icons;
      this._Icons = (IconBean[])param0;
      this._postSet(2, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 2);
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

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(3);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getMappedName() {
      return this._MappedName;
   }

   public boolean isMappedNameInherited() {
      return false;
   }

   public boolean isMappedNameSet() {
      return this._isSet(4);
   }

   public void setMappedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedName;
      this._MappedName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getHome() {
      return this._Home;
   }

   public boolean isHomeInherited() {
      return false;
   }

   public boolean isHomeSet() {
      return this._isSet(5);
   }

   public void setHome(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Home;
      this._Home = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getRemote() {
      return this._Remote;
   }

   public boolean isRemoteInherited() {
      return false;
   }

   public boolean isRemoteSet() {
      return this._isSet(6);
   }

   public void setRemote(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Remote;
      this._Remote = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getLocalHome() {
      return this._LocalHome;
   }

   public boolean isLocalHomeInherited() {
      return false;
   }

   public boolean isLocalHomeSet() {
      return this._isSet(7);
   }

   public void setLocalHome(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalHome;
      this._LocalHome = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getLocal() {
      return this._Local;
   }

   public boolean isLocalInherited() {
      return false;
   }

   public boolean isLocalSet() {
      return this._isSet(8);
   }

   public void setLocal(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Local;
      this._Local = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getEjbClass() {
      return this._EjbClass;
   }

   public boolean isEjbClassInherited() {
      return false;
   }

   public boolean isEjbClassSet() {
      return this._isSet(9);
   }

   public void setEjbClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbClass;
      this._EjbClass = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getPersistenceType() {
      return this._PersistenceType;
   }

   public boolean isPersistenceTypeInherited() {
      return false;
   }

   public boolean isPersistenceTypeSet() {
      return this._isSet(10);
   }

   public void setPersistenceType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Bean", "Container"};
      param0 = LegalChecks.checkInEnum("PersistenceType", param0, _set);
      String _oldVal = this._PersistenceType;
      this._PersistenceType = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getPrimKeyClass() {
      return this._PrimKeyClass;
   }

   public boolean isPrimKeyClassInherited() {
      return false;
   }

   public boolean isPrimKeyClassSet() {
      return this._isSet(11);
   }

   public void setPrimKeyClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrimKeyClass;
      this._PrimKeyClass = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isReentrant() {
      return this._Reentrant;
   }

   public boolean isReentrantInherited() {
      return false;
   }

   public boolean isReentrantSet() {
      return this._isSet(12);
   }

   public void setReentrant(boolean param0) {
      boolean _oldVal = this._Reentrant;
      this._Reentrant = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getCmpVersion() {
      return this._CmpVersion;
   }

   public boolean isCmpVersionInherited() {
      return false;
   }

   public boolean isCmpVersionSet() {
      return this._isSet(13);
   }

   public void setCmpVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"1.x", "2.x"};
      param0 = LegalChecks.checkInEnum("CmpVersion", param0, _set);
      String _oldVal = this._CmpVersion;
      this._CmpVersion = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getAbstractSchemaName() {
      return this._AbstractSchemaName;
   }

   public boolean isAbstractSchemaNameInherited() {
      return false;
   }

   public boolean isAbstractSchemaNameSet() {
      return this._isSet(14);
   }

   public void setAbstractSchemaName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AbstractSchemaName;
      this._AbstractSchemaName = param0;
      this._postSet(14, _oldVal, param0);
   }

   public void addCmpField(CmpFieldBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         CmpFieldBean[] _new;
         if (this._isSet(15)) {
            _new = (CmpFieldBean[])((CmpFieldBean[])this._getHelper()._extendArray(this.getCmpFields(), CmpFieldBean.class, param0));
         } else {
            _new = new CmpFieldBean[]{param0};
         }

         try {
            this.setCmpFields(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CmpFieldBean[] getCmpFields() {
      return this._CmpFields;
   }

   public boolean isCmpFieldsInherited() {
      return false;
   }

   public boolean isCmpFieldsSet() {
      return this._isSet(15);
   }

   public void removeCmpField(CmpFieldBean param0) {
      this.destroyCmpField(param0);
   }

   public void setCmpFields(CmpFieldBean[] param0) throws InvalidAttributeValueException {
      CmpFieldBean[] param0 = param0 == null ? new CmpFieldBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CmpFieldBean[] _oldVal = this._CmpFields;
      this._CmpFields = (CmpFieldBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public CmpFieldBean createCmpField() {
      CmpFieldBeanImpl _val = new CmpFieldBeanImpl(this, -1);

      try {
         this.addCmpField(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCmpField(CmpFieldBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         CmpFieldBean[] _old = this.getCmpFields();
         CmpFieldBean[] _new = (CmpFieldBean[])((CmpFieldBean[])this._getHelper()._removeElement(_old, CmpFieldBean.class, param0));
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
               this.setCmpFields(_new);
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

   public String getPrimkeyField() {
      return this._PrimkeyField;
   }

   public boolean isPrimkeyFieldInherited() {
      return false;
   }

   public boolean isPrimkeyFieldSet() {
      return this._isSet(16);
   }

   public void setPrimkeyField(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrimkeyField;
      this._PrimkeyField = param0;
      this._postSet(16, _oldVal, param0);
   }

   public void addEnvEntry(EnvEntryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         EnvEntryBean[] _new;
         if (this._isSet(17)) {
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
      return this._isSet(17);
   }

   public void removeEnvEntry(EnvEntryBean param0) {
      this.destroyEnvEntry(param0);
   }

   public void setEnvEntries(EnvEntryBean[] param0) throws InvalidAttributeValueException {
      EnvEntryBean[] param0 = param0 == null ? new EnvEntryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EnvEntryBean[] _oldVal = this._EnvEntries;
      this._EnvEntries = (EnvEntryBean[])param0;
      this._postSet(17, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 17);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         EjbRefBean[] _new;
         if (this._isSet(18)) {
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
      return this._isSet(18);
   }

   public void removeEjbRef(EjbRefBean param0) {
      this.destroyEjbRef(param0);
   }

   public void setEjbRefs(EjbRefBean[] param0) throws InvalidAttributeValueException {
      EjbRefBean[] param0 = param0 == null ? new EjbRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbRefBean[] _oldVal = this._EjbRefs;
      this._EjbRefs = (EjbRefBean[])param0;
      this._postSet(18, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 18);
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

   public void addEjbLocalRef(EjbLocalRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         EjbLocalRefBean[] _new;
         if (this._isSet(19)) {
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
      return this._isSet(19);
   }

   public void removeEjbLocalRef(EjbLocalRefBean param0) {
      this.destroyEjbLocalRef(param0);
   }

   public void setEjbLocalRefs(EjbLocalRefBean[] param0) throws InvalidAttributeValueException {
      EjbLocalRefBean[] param0 = param0 == null ? new EjbLocalRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbLocalRefBean[] _oldVal = this._EjbLocalRefs;
      this._EjbLocalRefs = (EjbLocalRefBean[])param0;
      this._postSet(19, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 19);
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

   public void addServiceRef(ServiceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         ServiceRefBean[] _new;
         if (this._isSet(20)) {
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
      return this._isSet(20);
   }

   public void removeServiceRef(ServiceRefBean param0) {
      this.destroyServiceRef(param0);
   }

   public void setServiceRefs(ServiceRefBean[] param0) throws InvalidAttributeValueException {
      ServiceRefBean[] param0 = param0 == null ? new ServiceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceRefBean[] _oldVal = this._ServiceRefs;
      this._ServiceRefs = (ServiceRefBean[])param0;
      this._postSet(20, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 20);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         ResourceRefBean[] _new;
         if (this._isSet(21)) {
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
      return this._isSet(21);
   }

   public void removeResourceRef(ResourceRefBean param0) {
      this.destroyResourceRef(param0);
   }

   public void setResourceRefs(ResourceRefBean[] param0) throws InvalidAttributeValueException {
      ResourceRefBean[] param0 = param0 == null ? new ResourceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceRefBean[] _oldVal = this._ResourceRefs;
      this._ResourceRefs = (ResourceRefBean[])param0;
      this._postSet(21, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 21);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         ResourceEnvRefBean[] _new;
         if (this._isSet(22)) {
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
      return this._isSet(22);
   }

   public void removeResourceEnvRef(ResourceEnvRefBean param0) {
      this.destroyResourceEnvRef(param0);
   }

   public void setResourceEnvRefs(ResourceEnvRefBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvRefBean[] param0 = param0 == null ? new ResourceEnvRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvRefBean[] _oldVal = this._ResourceEnvRefs;
      this._ResourceEnvRefs = (ResourceEnvRefBean[])param0;
      this._postSet(22, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 22);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         MessageDestinationRefBean[] _new;
         if (this._isSet(23)) {
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
      return this._isSet(23);
   }

   public void removeMessageDestinationRef(MessageDestinationRefBean param0) {
      this.destroyMessageDestinationRef(param0);
   }

   public void setMessageDestinationRefs(MessageDestinationRefBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationRefBean[] param0 = param0 == null ? new MessageDestinationRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationRefBean[] _oldVal = this._MessageDestinationRefs;
      this._MessageDestinationRefs = (MessageDestinationRefBean[])param0;
      this._postSet(23, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 23);
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

   public void addPersistenceContextRef(PersistenceContextRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         PersistenceContextRefBean[] _new;
         if (this._isSet(24)) {
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
      return this._isSet(24);
   }

   public void removePersistenceContextRef(PersistenceContextRefBean param0) {
      this.destroyPersistenceContextRef(param0);
   }

   public void setPersistenceContextRefs(PersistenceContextRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceContextRefBean[] param0 = param0 == null ? new PersistenceContextRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceContextRefBean[] _oldVal = this._PersistenceContextRefs;
      this._PersistenceContextRefs = (PersistenceContextRefBean[])param0;
      this._postSet(24, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 24);
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

   public void addPersistenceUnitRef(PersistenceUnitRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 25)) {
         PersistenceUnitRefBean[] _new;
         if (this._isSet(25)) {
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
      return this._isSet(25);
   }

   public void removePersistenceUnitRef(PersistenceUnitRefBean param0) {
      this.destroyPersistenceUnitRef(param0);
   }

   public void setPersistenceUnitRefs(PersistenceUnitRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceUnitRefBean[] param0 = param0 == null ? new PersistenceUnitRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 25)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceUnitRefBean[] _oldVal = this._PersistenceUnitRefs;
      this._PersistenceUnitRefs = (PersistenceUnitRefBean[])param0;
      this._postSet(25, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 25);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(26)) {
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
      return this._isSet(26);
   }

   public void removePostConstruct(LifecycleCallbackBean param0) {
      this.destroyPostConstruct(param0);
   }

   public void setPostConstructs(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 26)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PostConstructs;
      this._PostConstructs = (LifecycleCallbackBean[])param0;
      this._postSet(26, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 26);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(27)) {
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
      return this._isSet(27);
   }

   public void removePreDestroy(LifecycleCallbackBean param0) {
      this.destroyPreDestroy(param0);
   }

   public void setPreDestroys(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 27)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PreDestroys;
      this._PreDestroys = (LifecycleCallbackBean[])param0;
      this._postSet(27, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 27);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         DataSourceBean[] _new;
         if (this._isSet(28)) {
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
      return this._isSet(28);
   }

   public void removeDataSource(DataSourceBean param0) {
      this.destroyDataSource(param0);
   }

   public void setDataSources(DataSourceBean[] param0) throws InvalidAttributeValueException {
      DataSourceBean[] param0 = param0 == null ? new DataSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 28)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DataSourceBean[] _oldVal = this._DataSources;
      this._DataSources = (DataSourceBean[])param0;
      this._postSet(28, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 28);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 29)) {
         JmsConnectionFactoryBean[] _new;
         if (this._isSet(29)) {
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
      return this._isSet(29);
   }

   public void removeJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this.destroyJmsConnectionFactory(param0);
   }

   public void setJmsConnectionFactories(JmsConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JmsConnectionFactoryBean[] param0 = param0 == null ? new JmsConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 29)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsConnectionFactoryBean[] _oldVal = this._JmsConnectionFactories;
      this._JmsConnectionFactories = (JmsConnectionFactoryBean[])param0;
      this._postSet(29, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 29);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 30)) {
         JmsDestinationBean[] _new;
         if (this._isSet(30)) {
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
      return this._isSet(30);
   }

   public void removeJmsDestination(JmsDestinationBean param0) {
      this.destroyJmsDestination(param0);
   }

   public void setJmsDestinations(JmsDestinationBean[] param0) throws InvalidAttributeValueException {
      JmsDestinationBean[] param0 = param0 == null ? new JmsDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 30)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsDestinationBean[] _oldVal = this._JmsDestinations;
      this._JmsDestinations = (JmsDestinationBean[])param0;
      this._postSet(30, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 30);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 31)) {
         MailSessionBean[] _new;
         if (this._isSet(31)) {
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
      return this._isSet(31);
   }

   public void removeMailSession(MailSessionBean param0) {
      this.destroyMailSession(param0);
   }

   public void setMailSessions(MailSessionBean[] param0) throws InvalidAttributeValueException {
      MailSessionBean[] param0 = param0 == null ? new MailSessionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 31)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MailSessionBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionBean[])param0;
      this._postSet(31, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 31);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 32)) {
         ConnectionFactoryResourceBean[] _new;
         if (this._isSet(32)) {
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
      return this._isSet(32);
   }

   public void removeConnectionFactory(ConnectionFactoryResourceBean param0) {
      this.destroyConnectionFactory(param0);
   }

   public void setConnectionFactories(ConnectionFactoryResourceBean[] param0) throws InvalidAttributeValueException {
      ConnectionFactoryResourceBean[] param0 = param0 == null ? new ConnectionFactoryResourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 32)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionFactoryResourceBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (ConnectionFactoryResourceBean[])param0;
      this._postSet(32, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 32);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 33)) {
         AdministeredObjectBean[] _new;
         if (this._isSet(33)) {
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
      return this._isSet(33);
   }

   public void removeAdministeredObject(AdministeredObjectBean param0) {
      this.destroyAdministeredObject(param0);
   }

   public void setAdministeredObjects(AdministeredObjectBean[] param0) throws InvalidAttributeValueException {
      AdministeredObjectBean[] param0 = param0 == null ? new AdministeredObjectBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 33)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AdministeredObjectBean[] _oldVal = this._AdministeredObjects;
      this._AdministeredObjects = (AdministeredObjectBean[])param0;
      this._postSet(33, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 33);
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

   public void addSecurityRoleRef(SecurityRoleRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 34)) {
         SecurityRoleRefBean[] _new;
         if (this._isSet(34)) {
            _new = (SecurityRoleRefBean[])((SecurityRoleRefBean[])this._getHelper()._extendArray(this.getSecurityRoleRefs(), SecurityRoleRefBean.class, param0));
         } else {
            _new = new SecurityRoleRefBean[]{param0};
         }

         try {
            this.setSecurityRoleRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityRoleRefBean[] getSecurityRoleRefs() {
      return this._SecurityRoleRefs;
   }

   public boolean isSecurityRoleRefsInherited() {
      return false;
   }

   public boolean isSecurityRoleRefsSet() {
      return this._isSet(34);
   }

   public void removeSecurityRoleRef(SecurityRoleRefBean param0) {
      this.destroySecurityRoleRef(param0);
   }

   public void setSecurityRoleRefs(SecurityRoleRefBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleRefBean[] param0 = param0 == null ? new SecurityRoleRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 34)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleRefBean[] _oldVal = this._SecurityRoleRefs;
      this._SecurityRoleRefs = (SecurityRoleRefBean[])param0;
      this._postSet(34, _oldVal, param0);
   }

   public SecurityRoleRefBean createSecurityRoleRef() {
      SecurityRoleRefBeanImpl _val = new SecurityRoleRefBeanImpl(this, -1);

      try {
         this.addSecurityRoleRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityRoleRef(SecurityRoleRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 34);
         SecurityRoleRefBean[] _old = this.getSecurityRoleRefs();
         SecurityRoleRefBean[] _new = (SecurityRoleRefBean[])((SecurityRoleRefBean[])this._getHelper()._removeElement(_old, SecurityRoleRefBean.class, param0));
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
               this.setSecurityRoleRefs(_new);
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

   public SecurityIdentityBean getSecurityIdentity() {
      return this._SecurityIdentity;
   }

   public boolean isSecurityIdentityInherited() {
      return false;
   }

   public boolean isSecurityIdentitySet() {
      return this._isSet(35);
   }

   public void setSecurityIdentity(SecurityIdentityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSecurityIdentity() != null && param0 != this.getSecurityIdentity()) {
         throw new BeanAlreadyExistsException(this.getSecurityIdentity() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 35)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SecurityIdentityBean _oldVal = this._SecurityIdentity;
         this._SecurityIdentity = param0;
         this._postSet(35, _oldVal, param0);
      }
   }

   public SecurityIdentityBean createSecurityIdentity() {
      SecurityIdentityBeanImpl _val = new SecurityIdentityBeanImpl(this, -1);

      try {
         this.setSecurityIdentity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityIdentity(SecurityIdentityBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SecurityIdentity;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSecurityIdentity((SecurityIdentityBean)null);
               this._unSet(35);
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

   public void addQuery(QueryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 36)) {
         QueryBean[] _new;
         if (this._isSet(36)) {
            _new = (QueryBean[])((QueryBean[])this._getHelper()._extendArray(this.getQueries(), QueryBean.class, param0));
         } else {
            _new = new QueryBean[]{param0};
         }

         try {
            this.setQueries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public QueryBean[] getQueries() {
      return this._Queries;
   }

   public boolean isQueriesInherited() {
      return false;
   }

   public boolean isQueriesSet() {
      return this._isSet(36);
   }

   public void removeQuery(QueryBean param0) {
      this.destroyQuery(param0);
   }

   public void setQueries(QueryBean[] param0) throws InvalidAttributeValueException {
      QueryBean[] param0 = param0 == null ? new QueryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 36)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      QueryBean[] _oldVal = this._Queries;
      this._Queries = (QueryBean[])param0;
      this._postSet(36, _oldVal, param0);
   }

   public QueryBean createQuery() {
      QueryBeanImpl _val = new QueryBeanImpl(this, -1);

      try {
         this.addQuery(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyQuery(QueryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 36);
         QueryBean[] _old = this.getQueries();
         QueryBean[] _new = (QueryBean[])((QueryBean[])this._getHelper()._removeElement(_old, QueryBean.class, param0));
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
               this.setQueries(_new);
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
      return this._isSet(37);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(37, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEjbName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("PersistenceType", this.isPersistenceTypeSet());
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
               this._AbstractSchemaName = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._AdministeredObjects = new AdministeredObjectBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._CmpFields = new CmpFieldBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._CmpVersion = "2.x";
               if (initOne) {
                  break;
               }
            case 32:
               this._ConnectionFactories = new ConnectionFactoryResourceBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._DataSources = new DataSourceBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._EjbClass = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._EjbLocalRefs = new EjbLocalRefBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._EjbRefs = new EjbRefBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._EnvEntries = new EnvEntryBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Home = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 37:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._JmsConnectionFactories = new JmsConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._JmsDestinations = new JmsDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._Local = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._LocalHome = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._MailSessions = new MailSessionBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._MappedName = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._MessageDestinationRefs = new MessageDestinationRefBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._PersistenceContextRefs = new PersistenceContextRefBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._PersistenceType = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._PersistenceUnitRefs = new PersistenceUnitRefBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._PostConstructs = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._PreDestroys = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._PrimKeyClass = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._PrimkeyField = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._Queries = new QueryBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._Remote = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._ResourceEnvRefs = new ResourceEnvRefBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._ResourceRefs = new ResourceRefBean[0];
               if (initOne) {
                  break;
               }
            case 35:
               this._SecurityIdentity = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._SecurityRoleRefs = new SecurityRoleRefBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ServiceRefs = new ServiceRefBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._Reentrant = false;
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
                  return 37;
               }
            case 3:
            case 21:
            default:
               break;
            case 4:
               if (s.equals("home")) {
                  return 5;
               }

               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 5:
               if (s.equals("local")) {
                  return 8;
               }

               if (s.equals("query")) {
                  return 36;
               }
               break;
            case 6:
               if (s.equals("remote")) {
                  return 6;
               }
               break;
            case 7:
               if (s.equals("ejb-ref")) {
                  return 18;
               }
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 3;
               }
               break;
            case 9:
               if (s.equals("cmp-field")) {
                  return 15;
               }

               if (s.equals("ejb-class")) {
                  return 9;
               }

               if (s.equals("env-entry")) {
                  return 17;
               }

               if (s.equals("reentrant")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("local-home")) {
                  return 7;
               }
               break;
            case 11:
               if (s.equals("cmp-version")) {
                  return 13;
               }

               if (s.equals("data-source")) {
                  return 28;
               }

               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("mapped-name")) {
                  return 4;
               }

               if (s.equals("pre-destroy")) {
                  return 27;
               }

               if (s.equals("service-ref")) {
                  return 20;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }

               if (s.equals("mail-session")) {
                  return 31;
               }

               if (s.equals("resource-ref")) {
                  return 21;
               }
               break;
            case 13:
               if (s.equals("ejb-local-ref")) {
                  return 19;
               }

               if (s.equals("primkey-field")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("post-construct")) {
                  return 26;
               }

               if (s.equals("prim-key-class")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("jms-destination")) {
                  return 30;
               }
               break;
            case 16:
               if (s.equals("persistence-type")) {
                  return 10;
               }

               if (s.equals("resource-env-ref")) {
                  return 22;
               }
               break;
            case 17:
               if (s.equals("security-identity")) {
                  return 35;
               }

               if (s.equals("security-role-ref")) {
                  return 34;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 32;
               }
               break;
            case 19:
               if (s.equals("administered-object")) {
                  return 33;
               }
               break;
            case 20:
               if (s.equals("abstract-schema-name")) {
                  return 14;
               }

               if (s.equals("persistence-unit-ref")) {
                  return 25;
               }
               break;
            case 22:
               if (s.equals("jms-connection-factory")) {
                  return 29;
               }
               break;
            case 23:
               if (s.equals("message-destination-ref")) {
                  return 23;
               }

               if (s.equals("persistence-context-ref")) {
                  return 24;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            default:
               return super.getSchemaHelper(propIndex);
            case 15:
               return new CmpFieldBeanImpl.SchemaHelper2();
            case 17:
               return new EnvEntryBeanImpl.SchemaHelper2();
            case 18:
               return new EjbRefBeanImpl.SchemaHelper2();
            case 19:
               return new EjbLocalRefBeanImpl.SchemaHelper2();
            case 20:
               return new ServiceRefBeanImpl.SchemaHelper2();
            case 21:
               return new ResourceRefBeanImpl.SchemaHelper2();
            case 22:
               return new ResourceEnvRefBeanImpl.SchemaHelper2();
            case 23:
               return new MessageDestinationRefBeanImpl.SchemaHelper2();
            case 24:
               return new PersistenceContextRefBeanImpl.SchemaHelper2();
            case 25:
               return new PersistenceUnitRefBeanImpl.SchemaHelper2();
            case 26:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 27:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 28:
               return new DataSourceBeanImpl.SchemaHelper2();
            case 29:
               return new JmsConnectionFactoryBeanImpl.SchemaHelper2();
            case 30:
               return new JmsDestinationBeanImpl.SchemaHelper2();
            case 31:
               return new MailSessionBeanImpl.SchemaHelper2();
            case 32:
               return new ConnectionFactoryResourceBeanImpl.SchemaHelper2();
            case 33:
               return new AdministeredObjectBeanImpl.SchemaHelper2();
            case 34:
               return new SecurityRoleRefBeanImpl.SchemaHelper2();
            case 35:
               return new SecurityIdentityBeanImpl.SchemaHelper2();
            case 36:
               return new QueryBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "display-name";
            case 2:
               return "icon";
            case 3:
               return "ejb-name";
            case 4:
               return "mapped-name";
            case 5:
               return "home";
            case 6:
               return "remote";
            case 7:
               return "local-home";
            case 8:
               return "local";
            case 9:
               return "ejb-class";
            case 10:
               return "persistence-type";
            case 11:
               return "prim-key-class";
            case 12:
               return "reentrant";
            case 13:
               return "cmp-version";
            case 14:
               return "abstract-schema-name";
            case 15:
               return "cmp-field";
            case 16:
               return "primkey-field";
            case 17:
               return "env-entry";
            case 18:
               return "ejb-ref";
            case 19:
               return "ejb-local-ref";
            case 20:
               return "service-ref";
            case 21:
               return "resource-ref";
            case 22:
               return "resource-env-ref";
            case 23:
               return "message-destination-ref";
            case 24:
               return "persistence-context-ref";
            case 25:
               return "persistence-unit-ref";
            case 26:
               return "post-construct";
            case 27:
               return "pre-destroy";
            case 28:
               return "data-source";
            case 29:
               return "jms-connection-factory";
            case 30:
               return "jms-destination";
            case 31:
               return "mail-session";
            case 32:
               return "connection-factory";
            case 33:
               return "administered-object";
            case 34:
               return "security-role-ref";
            case 35:
               return "security-identity";
            case 36:
               return "query";
            case 37:
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
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 35:
            default:
               return super.isArray(propIndex);
            case 15:
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
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 36:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            default:
               return super.isBean(propIndex);
            case 15:
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
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 35:
               return true;
            case 36:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 3:
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
      private EntityBeanBeanImpl bean;

      protected Helper(EntityBeanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "DisplayNames";
            case 2:
               return "Icons";
            case 3:
               return "EjbName";
            case 4:
               return "MappedName";
            case 5:
               return "Home";
            case 6:
               return "Remote";
            case 7:
               return "LocalHome";
            case 8:
               return "Local";
            case 9:
               return "EjbClass";
            case 10:
               return "PersistenceType";
            case 11:
               return "PrimKeyClass";
            case 12:
               return "Reentrant";
            case 13:
               return "CmpVersion";
            case 14:
               return "AbstractSchemaName";
            case 15:
               return "CmpFields";
            case 16:
               return "PrimkeyField";
            case 17:
               return "EnvEntries";
            case 18:
               return "EjbRefs";
            case 19:
               return "EjbLocalRefs";
            case 20:
               return "ServiceRefs";
            case 21:
               return "ResourceRefs";
            case 22:
               return "ResourceEnvRefs";
            case 23:
               return "MessageDestinationRefs";
            case 24:
               return "PersistenceContextRefs";
            case 25:
               return "PersistenceUnitRefs";
            case 26:
               return "PostConstructs";
            case 27:
               return "PreDestroys";
            case 28:
               return "DataSources";
            case 29:
               return "JmsConnectionFactories";
            case 30:
               return "JmsDestinations";
            case 31:
               return "MailSessions";
            case 32:
               return "ConnectionFactories";
            case 33:
               return "AdministeredObjects";
            case 34:
               return "SecurityRoleRefs";
            case 35:
               return "SecurityIdentity";
            case 36:
               return "Queries";
            case 37:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AbstractSchemaName")) {
            return 14;
         } else if (propName.equals("AdministeredObjects")) {
            return 33;
         } else if (propName.equals("CmpFields")) {
            return 15;
         } else if (propName.equals("CmpVersion")) {
            return 13;
         } else if (propName.equals("ConnectionFactories")) {
            return 32;
         } else if (propName.equals("DataSources")) {
            return 28;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("DisplayNames")) {
            return 1;
         } else if (propName.equals("EjbClass")) {
            return 9;
         } else if (propName.equals("EjbLocalRefs")) {
            return 19;
         } else if (propName.equals("EjbName")) {
            return 3;
         } else if (propName.equals("EjbRefs")) {
            return 18;
         } else if (propName.equals("EnvEntries")) {
            return 17;
         } else if (propName.equals("Home")) {
            return 5;
         } else if (propName.equals("Icons")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 37;
         } else if (propName.equals("JmsConnectionFactories")) {
            return 29;
         } else if (propName.equals("JmsDestinations")) {
            return 30;
         } else if (propName.equals("Local")) {
            return 8;
         } else if (propName.equals("LocalHome")) {
            return 7;
         } else if (propName.equals("MailSessions")) {
            return 31;
         } else if (propName.equals("MappedName")) {
            return 4;
         } else if (propName.equals("MessageDestinationRefs")) {
            return 23;
         } else if (propName.equals("PersistenceContextRefs")) {
            return 24;
         } else if (propName.equals("PersistenceType")) {
            return 10;
         } else if (propName.equals("PersistenceUnitRefs")) {
            return 25;
         } else if (propName.equals("PostConstructs")) {
            return 26;
         } else if (propName.equals("PreDestroys")) {
            return 27;
         } else if (propName.equals("PrimKeyClass")) {
            return 11;
         } else if (propName.equals("PrimkeyField")) {
            return 16;
         } else if (propName.equals("Queries")) {
            return 36;
         } else if (propName.equals("Remote")) {
            return 6;
         } else if (propName.equals("ResourceEnvRefs")) {
            return 22;
         } else if (propName.equals("ResourceRefs")) {
            return 21;
         } else if (propName.equals("SecurityIdentity")) {
            return 35;
         } else if (propName.equals("SecurityRoleRefs")) {
            return 34;
         } else if (propName.equals("ServiceRefs")) {
            return 20;
         } else {
            return propName.equals("Reentrant") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAdministeredObjects()));
         iterators.add(new ArrayIterator(this.bean.getCmpFields()));
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
         iterators.add(new ArrayIterator(this.bean.getPersistenceContextRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitRefs()));
         iterators.add(new ArrayIterator(this.bean.getPostConstructs()));
         iterators.add(new ArrayIterator(this.bean.getPreDestroys()));
         iterators.add(new ArrayIterator(this.bean.getQueries()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceRefs()));
         if (this.bean.getSecurityIdentity() != null) {
            iterators.add(new ArrayIterator(new SecurityIdentityBean[]{this.bean.getSecurityIdentity()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSecurityRoleRefs()));
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
            if (this.bean.isAbstractSchemaNameSet()) {
               buf.append("AbstractSchemaName");
               buf.append(String.valueOf(this.bean.getAbstractSchemaName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getAdministeredObjects().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAdministeredObjects()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCmpFields().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCmpFields()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCmpVersionSet()) {
               buf.append("CmpVersion");
               buf.append(String.valueOf(this.bean.getCmpVersion()));
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

            if (this.bean.isEjbClassSet()) {
               buf.append("EjbClass");
               buf.append(String.valueOf(this.bean.getEjbClass()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbLocalRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbLocalRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
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

            if (this.bean.isHomeSet()) {
               buf.append("Home");
               buf.append(String.valueOf(this.bean.getHome()));
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

            if (this.bean.isLocalSet()) {
               buf.append("Local");
               buf.append(String.valueOf(this.bean.getLocal()));
            }

            if (this.bean.isLocalHomeSet()) {
               buf.append("LocalHome");
               buf.append(String.valueOf(this.bean.getLocalHome()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMappedNameSet()) {
               buf.append("MappedName");
               buf.append(String.valueOf(this.bean.getMappedName()));
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

            if (this.bean.isPersistenceTypeSet()) {
               buf.append("PersistenceType");
               buf.append(String.valueOf(this.bean.getPersistenceType()));
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

            if (this.bean.isPrimKeyClassSet()) {
               buf.append("PrimKeyClass");
               buf.append(String.valueOf(this.bean.getPrimKeyClass()));
            }

            if (this.bean.isPrimkeyFieldSet()) {
               buf.append("PrimkeyField");
               buf.append(String.valueOf(this.bean.getPrimkeyField()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getQueries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getQueries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRemoteSet()) {
               buf.append("Remote");
               buf.append(String.valueOf(this.bean.getRemote()));
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

            childValue = this.computeChildHashValue(this.bean.getSecurityIdentity());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityRoleRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoleRefs()[i]);
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

            if (this.bean.isReentrantSet()) {
               buf.append("Reentrant");
               buf.append(String.valueOf(this.bean.isReentrant()));
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
            EntityBeanBeanImpl otherTyped = (EntityBeanBeanImpl)other;
            this.computeDiff("AbstractSchemaName", this.bean.getAbstractSchemaName(), otherTyped.getAbstractSchemaName(), false);
            this.computeChildDiff("AdministeredObjects", this.bean.getAdministeredObjects(), otherTyped.getAdministeredObjects(), false);
            this.computeChildDiff("CmpFields", this.bean.getCmpFields(), otherTyped.getCmpFields(), false);
            this.computeDiff("CmpVersion", this.bean.getCmpVersion(), otherTyped.getCmpVersion(), false);
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), false);
            this.computeChildDiff("DataSources", this.bean.getDataSources(), otherTyped.getDataSources(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeDiff("EjbClass", this.bean.getEjbClass(), otherTyped.getEjbClass(), false);
            this.computeChildDiff("EjbLocalRefs", this.bean.getEjbLocalRefs(), otherTyped.getEjbLocalRefs(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeChildDiff("EjbRefs", this.bean.getEjbRefs(), otherTyped.getEjbRefs(), false);
            this.computeChildDiff("EnvEntries", this.bean.getEnvEntries(), otherTyped.getEnvEntries(), false);
            this.computeDiff("Home", this.bean.getHome(), otherTyped.getHome(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("JmsConnectionFactories", this.bean.getJmsConnectionFactories(), otherTyped.getJmsConnectionFactories(), false);
            this.computeChildDiff("JmsDestinations", this.bean.getJmsDestinations(), otherTyped.getJmsDestinations(), false);
            this.computeDiff("Local", this.bean.getLocal(), otherTyped.getLocal(), false);
            this.computeDiff("LocalHome", this.bean.getLocalHome(), otherTyped.getLocalHome(), false);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), false);
            this.computeDiff("MappedName", this.bean.getMappedName(), otherTyped.getMappedName(), false);
            this.computeChildDiff("MessageDestinationRefs", this.bean.getMessageDestinationRefs(), otherTyped.getMessageDestinationRefs(), false);
            this.computeChildDiff("PersistenceContextRefs", this.bean.getPersistenceContextRefs(), otherTyped.getPersistenceContextRefs(), false);
            this.computeDiff("PersistenceType", this.bean.getPersistenceType(), otherTyped.getPersistenceType(), false);
            this.computeChildDiff("PersistenceUnitRefs", this.bean.getPersistenceUnitRefs(), otherTyped.getPersistenceUnitRefs(), false);
            this.computeChildDiff("PostConstructs", this.bean.getPostConstructs(), otherTyped.getPostConstructs(), false);
            this.computeChildDiff("PreDestroys", this.bean.getPreDestroys(), otherTyped.getPreDestroys(), false);
            this.computeDiff("PrimKeyClass", this.bean.getPrimKeyClass(), otherTyped.getPrimKeyClass(), false);
            this.computeDiff("PrimkeyField", this.bean.getPrimkeyField(), otherTyped.getPrimkeyField(), false);
            this.computeChildDiff("Queries", this.bean.getQueries(), otherTyped.getQueries(), false);
            this.computeDiff("Remote", this.bean.getRemote(), otherTyped.getRemote(), false);
            this.computeChildDiff("ResourceEnvRefs", this.bean.getResourceEnvRefs(), otherTyped.getResourceEnvRefs(), false);
            this.computeChildDiff("ResourceRefs", this.bean.getResourceRefs(), otherTyped.getResourceRefs(), false);
            this.computeChildDiff("SecurityIdentity", this.bean.getSecurityIdentity(), otherTyped.getSecurityIdentity(), false);
            this.computeChildDiff("SecurityRoleRefs", this.bean.getSecurityRoleRefs(), otherTyped.getSecurityRoleRefs(), false);
            this.computeChildDiff("ServiceRefs", this.bean.getServiceRefs(), otherTyped.getServiceRefs(), false);
            this.computeDiff("Reentrant", this.bean.isReentrant(), otherTyped.isReentrant(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EntityBeanBeanImpl original = (EntityBeanBeanImpl)event.getSourceBean();
            EntityBeanBeanImpl proposed = (EntityBeanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AbstractSchemaName")) {
                  original.setAbstractSchemaName(proposed.getAbstractSchemaName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("AdministeredObjects")) {
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
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  }
               } else if (prop.equals("CmpFields")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCmpField((CmpFieldBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCmpField((CmpFieldBean)update.getRemovedObject());
                  }

                  if (original.getCmpFields() == null || original.getCmpFields().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("CmpVersion")) {
                  original.setCmpVersion(proposed.getCmpVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("EjbClass")) {
                  original.setEjbClass(proposed.getEjbClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  }
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("Home")) {
                  original.setHome(proposed.getHome());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  }
               } else if (prop.equals("Local")) {
                  original.setLocal(proposed.getLocal());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("LocalHome")) {
                  original.setLocalHome(proposed.getLocalHome());
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
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  }
               } else if (prop.equals("MappedName")) {
                  original.setMappedName(proposed.getMappedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  }
               } else if (prop.equals("PersistenceType")) {
                  original.setPersistenceType(proposed.getPersistenceType());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  }
               } else if (prop.equals("PrimKeyClass")) {
                  original.setPrimKeyClass(proposed.getPrimKeyClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("PrimkeyField")) {
                  original.setPrimkeyField(proposed.getPrimkeyField());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Queries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addQuery((QueryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeQuery((QueryBean)update.getRemovedObject());
                  }

                  if (original.getQueries() == null || original.getQueries().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  }
               } else if (prop.equals("Remote")) {
                  original.setRemote(proposed.getRemote());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  }
               } else if (prop.equals("SecurityIdentity")) {
                  if (type == 2) {
                     original.setSecurityIdentity((SecurityIdentityBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityIdentity()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SecurityIdentity", (DescriptorBean)original.getSecurityIdentity());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("SecurityRoleRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityRoleRef((SecurityRoleRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityRoleRef((SecurityRoleRefBean)update.getRemovedObject());
                  }

                  if (original.getSecurityRoleRefs() == null || original.getSecurityRoleRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  }
               } else if (prop.equals("Reentrant")) {
                  original.setReentrant(proposed.isReentrant());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            EntityBeanBeanImpl copy = (EntityBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AbstractSchemaName")) && this.bean.isAbstractSchemaNameSet()) {
               copy.setAbstractSchemaName(this.bean.getAbstractSchemaName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("AdministeredObjects")) && this.bean.isAdministeredObjectsSet() && !copy._isSet(33)) {
               AdministeredObjectBean[] oldAdministeredObjects = this.bean.getAdministeredObjects();
               AdministeredObjectBean[] newAdministeredObjects = new AdministeredObjectBean[oldAdministeredObjects.length];

               for(i = 0; i < newAdministeredObjects.length; ++i) {
                  newAdministeredObjects[i] = (AdministeredObjectBean)((AdministeredObjectBean)this.createCopy((AbstractDescriptorBean)oldAdministeredObjects[i], includeObsolete));
               }

               copy.setAdministeredObjects(newAdministeredObjects);
            }

            if ((excludeProps == null || !excludeProps.contains("CmpFields")) && this.bean.isCmpFieldsSet() && !copy._isSet(15)) {
               CmpFieldBean[] oldCmpFields = this.bean.getCmpFields();
               CmpFieldBean[] newCmpFields = new CmpFieldBean[oldCmpFields.length];

               for(i = 0; i < newCmpFields.length; ++i) {
                  newCmpFields[i] = (CmpFieldBean)((CmpFieldBean)this.createCopy((AbstractDescriptorBean)oldCmpFields[i], includeObsolete));
               }

               copy.setCmpFields(newCmpFields);
            }

            if ((excludeProps == null || !excludeProps.contains("CmpVersion")) && this.bean.isCmpVersionSet()) {
               copy.setCmpVersion(this.bean.getCmpVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(32)) {
               ConnectionFactoryResourceBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               ConnectionFactoryResourceBean[] newConnectionFactories = new ConnectionFactoryResourceBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (ConnectionFactoryResourceBean)((ConnectionFactoryResourceBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSources")) && this.bean.isDataSourcesSet() && !copy._isSet(28)) {
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

            if ((excludeProps == null || !excludeProps.contains("EjbClass")) && this.bean.isEjbClassSet()) {
               copy.setEjbClass(this.bean.getEjbClass());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbLocalRefs")) && this.bean.isEjbLocalRefsSet() && !copy._isSet(19)) {
               EjbLocalRefBean[] oldEjbLocalRefs = this.bean.getEjbLocalRefs();
               EjbLocalRefBean[] newEjbLocalRefs = new EjbLocalRefBean[oldEjbLocalRefs.length];

               for(i = 0; i < newEjbLocalRefs.length; ++i) {
                  newEjbLocalRefs[i] = (EjbLocalRefBean)((EjbLocalRefBean)this.createCopy((AbstractDescriptorBean)oldEjbLocalRefs[i], includeObsolete));
               }

               copy.setEjbLocalRefs(newEjbLocalRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRefs")) && this.bean.isEjbRefsSet() && !copy._isSet(18)) {
               EjbRefBean[] oldEjbRefs = this.bean.getEjbRefs();
               EjbRefBean[] newEjbRefs = new EjbRefBean[oldEjbRefs.length];

               for(i = 0; i < newEjbRefs.length; ++i) {
                  newEjbRefs[i] = (EjbRefBean)((EjbRefBean)this.createCopy((AbstractDescriptorBean)oldEjbRefs[i], includeObsolete));
               }

               copy.setEjbRefs(newEjbRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntries")) && this.bean.isEnvEntriesSet() && !copy._isSet(17)) {
               EnvEntryBean[] oldEnvEntries = this.bean.getEnvEntries();
               EnvEntryBean[] newEnvEntries = new EnvEntryBean[oldEnvEntries.length];

               for(i = 0; i < newEnvEntries.length; ++i) {
                  newEnvEntries[i] = (EnvEntryBean)((EnvEntryBean)this.createCopy((AbstractDescriptorBean)oldEnvEntries[i], includeObsolete));
               }

               copy.setEnvEntries(newEnvEntries);
            }

            if ((excludeProps == null || !excludeProps.contains("Home")) && this.bean.isHomeSet()) {
               copy.setHome(this.bean.getHome());
            }

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(2)) {
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

            if ((excludeProps == null || !excludeProps.contains("JmsConnectionFactories")) && this.bean.isJmsConnectionFactoriesSet() && !copy._isSet(29)) {
               JmsConnectionFactoryBean[] oldJmsConnectionFactories = this.bean.getJmsConnectionFactories();
               JmsConnectionFactoryBean[] newJmsConnectionFactories = new JmsConnectionFactoryBean[oldJmsConnectionFactories.length];

               for(i = 0; i < newJmsConnectionFactories.length; ++i) {
                  newJmsConnectionFactories[i] = (JmsConnectionFactoryBean)((JmsConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldJmsConnectionFactories[i], includeObsolete));
               }

               copy.setJmsConnectionFactories(newJmsConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsDestinations")) && this.bean.isJmsDestinationsSet() && !copy._isSet(30)) {
               JmsDestinationBean[] oldJmsDestinations = this.bean.getJmsDestinations();
               JmsDestinationBean[] newJmsDestinations = new JmsDestinationBean[oldJmsDestinations.length];

               for(i = 0; i < newJmsDestinations.length; ++i) {
                  newJmsDestinations[i] = (JmsDestinationBean)((JmsDestinationBean)this.createCopy((AbstractDescriptorBean)oldJmsDestinations[i], includeObsolete));
               }

               copy.setJmsDestinations(newJmsDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("Local")) && this.bean.isLocalSet()) {
               copy.setLocal(this.bean.getLocal());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalHome")) && this.bean.isLocalHomeSet()) {
               copy.setLocalHome(this.bean.getLocalHome());
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(31)) {
               MailSessionBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionBean[] newMailSessions = new MailSessionBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionBean)((MailSessionBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("MappedName")) && this.bean.isMappedNameSet()) {
               copy.setMappedName(this.bean.getMappedName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationRefs")) && this.bean.isMessageDestinationRefsSet() && !copy._isSet(23)) {
               MessageDestinationRefBean[] oldMessageDestinationRefs = this.bean.getMessageDestinationRefs();
               MessageDestinationRefBean[] newMessageDestinationRefs = new MessageDestinationRefBean[oldMessageDestinationRefs.length];

               for(i = 0; i < newMessageDestinationRefs.length; ++i) {
                  newMessageDestinationRefs[i] = (MessageDestinationRefBean)((MessageDestinationRefBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationRefs[i], includeObsolete));
               }

               copy.setMessageDestinationRefs(newMessageDestinationRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextRefs")) && this.bean.isPersistenceContextRefsSet() && !copy._isSet(24)) {
               PersistenceContextRefBean[] oldPersistenceContextRefs = this.bean.getPersistenceContextRefs();
               PersistenceContextRefBean[] newPersistenceContextRefs = new PersistenceContextRefBean[oldPersistenceContextRefs.length];

               for(i = 0; i < newPersistenceContextRefs.length; ++i) {
                  newPersistenceContextRefs[i] = (PersistenceContextRefBean)((PersistenceContextRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceContextRefs[i], includeObsolete));
               }

               copy.setPersistenceContextRefs(newPersistenceContextRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceType")) && this.bean.isPersistenceTypeSet()) {
               copy.setPersistenceType(this.bean.getPersistenceType());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitRefs")) && this.bean.isPersistenceUnitRefsSet() && !copy._isSet(25)) {
               PersistenceUnitRefBean[] oldPersistenceUnitRefs = this.bean.getPersistenceUnitRefs();
               PersistenceUnitRefBean[] newPersistenceUnitRefs = new PersistenceUnitRefBean[oldPersistenceUnitRefs.length];

               for(i = 0; i < newPersistenceUnitRefs.length; ++i) {
                  newPersistenceUnitRefs[i] = (PersistenceUnitRefBean)((PersistenceUnitRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceUnitRefs[i], includeObsolete));
               }

               copy.setPersistenceUnitRefs(newPersistenceUnitRefs);
            }

            LifecycleCallbackBean[] oldPreDestroys;
            LifecycleCallbackBean[] newPreDestroys;
            if ((excludeProps == null || !excludeProps.contains("PostConstructs")) && this.bean.isPostConstructsSet() && !copy._isSet(26)) {
               oldPreDestroys = this.bean.getPostConstructs();
               newPreDestroys = new LifecycleCallbackBean[oldPreDestroys.length];

               for(i = 0; i < newPreDestroys.length; ++i) {
                  newPreDestroys[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPreDestroys[i], includeObsolete));
               }

               copy.setPostConstructs(newPreDestroys);
            }

            if ((excludeProps == null || !excludeProps.contains("PreDestroys")) && this.bean.isPreDestroysSet() && !copy._isSet(27)) {
               oldPreDestroys = this.bean.getPreDestroys();
               newPreDestroys = new LifecycleCallbackBean[oldPreDestroys.length];

               for(i = 0; i < newPreDestroys.length; ++i) {
                  newPreDestroys[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPreDestroys[i], includeObsolete));
               }

               copy.setPreDestroys(newPreDestroys);
            }

            if ((excludeProps == null || !excludeProps.contains("PrimKeyClass")) && this.bean.isPrimKeyClassSet()) {
               copy.setPrimKeyClass(this.bean.getPrimKeyClass());
            }

            if ((excludeProps == null || !excludeProps.contains("PrimkeyField")) && this.bean.isPrimkeyFieldSet()) {
               copy.setPrimkeyField(this.bean.getPrimkeyField());
            }

            if ((excludeProps == null || !excludeProps.contains("Queries")) && this.bean.isQueriesSet() && !copy._isSet(36)) {
               QueryBean[] oldQueries = this.bean.getQueries();
               QueryBean[] newQueries = new QueryBean[oldQueries.length];

               for(i = 0; i < newQueries.length; ++i) {
                  newQueries[i] = (QueryBean)((QueryBean)this.createCopy((AbstractDescriptorBean)oldQueries[i], includeObsolete));
               }

               copy.setQueries(newQueries);
            }

            if ((excludeProps == null || !excludeProps.contains("Remote")) && this.bean.isRemoteSet()) {
               copy.setRemote(this.bean.getRemote());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvRefs")) && this.bean.isResourceEnvRefsSet() && !copy._isSet(22)) {
               ResourceEnvRefBean[] oldResourceEnvRefs = this.bean.getResourceEnvRefs();
               ResourceEnvRefBean[] newResourceEnvRefs = new ResourceEnvRefBean[oldResourceEnvRefs.length];

               for(i = 0; i < newResourceEnvRefs.length; ++i) {
                  newResourceEnvRefs[i] = (ResourceEnvRefBean)((ResourceEnvRefBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvRefs[i], includeObsolete));
               }

               copy.setResourceEnvRefs(newResourceEnvRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceRefs")) && this.bean.isResourceRefsSet() && !copy._isSet(21)) {
               ResourceRefBean[] oldResourceRefs = this.bean.getResourceRefs();
               ResourceRefBean[] newResourceRefs = new ResourceRefBean[oldResourceRefs.length];

               for(i = 0; i < newResourceRefs.length; ++i) {
                  newResourceRefs[i] = (ResourceRefBean)((ResourceRefBean)this.createCopy((AbstractDescriptorBean)oldResourceRefs[i], includeObsolete));
               }

               copy.setResourceRefs(newResourceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityIdentity")) && this.bean.isSecurityIdentitySet() && !copy._isSet(35)) {
               Object o = this.bean.getSecurityIdentity();
               copy.setSecurityIdentity((SecurityIdentityBean)null);
               copy.setSecurityIdentity(o == null ? null : (SecurityIdentityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoleRefs")) && this.bean.isSecurityRoleRefsSet() && !copy._isSet(34)) {
               SecurityRoleRefBean[] oldSecurityRoleRefs = this.bean.getSecurityRoleRefs();
               SecurityRoleRefBean[] newSecurityRoleRefs = new SecurityRoleRefBean[oldSecurityRoleRefs.length];

               for(i = 0; i < newSecurityRoleRefs.length; ++i) {
                  newSecurityRoleRefs[i] = (SecurityRoleRefBean)((SecurityRoleRefBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoleRefs[i], includeObsolete));
               }

               copy.setSecurityRoleRefs(newSecurityRoleRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefs")) && this.bean.isServiceRefsSet() && !copy._isSet(20)) {
               ServiceRefBean[] oldServiceRefs = this.bean.getServiceRefs();
               ServiceRefBean[] newServiceRefs = new ServiceRefBean[oldServiceRefs.length];

               for(i = 0; i < newServiceRefs.length; ++i) {
                  newServiceRefs[i] = (ServiceRefBean)((ServiceRefBean)this.createCopy((AbstractDescriptorBean)oldServiceRefs[i], includeObsolete));
               }

               copy.setServiceRefs(newServiceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("Reentrant")) && this.bean.isReentrantSet()) {
               copy.setReentrant(this.bean.isReentrant());
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
         this.inferSubTree(this.bean.getCmpFields(), clazz, annotation);
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
         this.inferSubTree(this.bean.getPersistenceContextRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceUnitRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPostConstructs(), clazz, annotation);
         this.inferSubTree(this.bean.getPreDestroys(), clazz, annotation);
         this.inferSubTree(this.bean.getQueries(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityIdentity(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoleRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceRefs(), clazz, annotation);
      }
   }
}
