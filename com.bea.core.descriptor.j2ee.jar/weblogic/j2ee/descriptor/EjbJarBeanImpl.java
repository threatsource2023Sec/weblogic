package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.ejb.Stateless;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.customizers.JavaEEModuleNameCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EjbJarBeanImpl extends JavaEEModuleNameBeanImpl implements EjbJarBean, Serializable {
   private AssemblyDescriptorBean _AssemblyDescriptor;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private String _EjbClientJar;
   private EnterpriseBeansBean _EnterpriseBeans;
   private IconBean[] _Icons;
   private String _Id;
   private InterceptorsBean _Interceptors;
   private String _JavaEEModuleName;
   private boolean _MetadataComplete;
   private String _ModuleName;
   private RelationshipsBean _Relationships;
   private String _Version;
   private transient JavaEEModuleNameCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public EjbJarBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new JavaEEModuleNameCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public EjbJarBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new JavaEEModuleNameCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public EjbJarBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new JavaEEModuleNameCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getJavaEEModuleName() {
      return this._customizer.getJavaEEModuleName();
   }

   public String getModuleName() {
      return this._ModuleName;
   }

   public boolean isJavaEEModuleNameInherited() {
      return false;
   }

   public boolean isJavaEEModuleNameSet() {
      return this._isSet(0);
   }

   public boolean isModuleNameInherited() {
      return false;
   }

   public boolean isModuleNameSet() {
      return this._isSet(1);
   }

   public void setJavaEEModuleName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaEEModuleName;
      this._JavaEEModuleName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setModuleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleName;
      this._ModuleName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(2);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(2)) {
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
      this._postSet(2, _oldVal, param0);
   }

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(3);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
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
      this._postSet(3, _oldVal, param0);
   }

   public void addIcon(IconBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         IconBean[] _new;
         if (this._isSet(4)) {
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
      return this._isSet(4);
   }

   public void removeIcon(IconBean param0) {
      this.destroyIcon(param0);
   }

   public void setIcons(IconBean[] param0) throws InvalidAttributeValueException {
      IconBean[] param0 = param0 == null ? new IconBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      IconBean[] _oldVal = this._Icons;
      this._Icons = (IconBean[])param0;
      this._postSet(4, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 4);
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

   public EnterpriseBeansBean getEnterpriseBeans() {
      return this._EnterpriseBeans;
   }

   public boolean isEnterpriseBeansInherited() {
      return false;
   }

   public boolean isEnterpriseBeansSet() {
      return this._isSet(5);
   }

   public void setEnterpriseBeans(EnterpriseBeansBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getEnterpriseBeans() != null && param0 != this.getEnterpriseBeans()) {
         throw new BeanAlreadyExistsException(this.getEnterpriseBeans() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EnterpriseBeansBean _oldVal = this._EnterpriseBeans;
         this._EnterpriseBeans = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public EnterpriseBeansBean createEnterpriseBeans() {
      EnterpriseBeansBeanImpl _val = new EnterpriseBeansBeanImpl(this, -1);

      try {
         this.setEnterpriseBeans(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEnterpriseBeans(EnterpriseBeansBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._EnterpriseBeans;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setEnterpriseBeans((EnterpriseBeansBean)null);
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

   public InterceptorsBean getInterceptors() {
      return this._Interceptors;
   }

   public boolean isInterceptorsInherited() {
      return false;
   }

   public boolean isInterceptorsSet() {
      return this._isSet(6);
   }

   public void setInterceptors(InterceptorsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInterceptors() != null && param0 != this.getInterceptors()) {
         throw new BeanAlreadyExistsException(this.getInterceptors() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 6)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InterceptorsBean _oldVal = this._Interceptors;
         this._Interceptors = param0;
         this._postSet(6, _oldVal, param0);
      }
   }

   public InterceptorsBean createInterceptors() {
      InterceptorsBeanImpl _val = new InterceptorsBeanImpl(this, -1);

      try {
         this.setInterceptors(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInterceptors(InterceptorsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Interceptors;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInterceptors((InterceptorsBean)null);
               this._unSet(6);
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

   public RelationshipsBean getRelationships() {
      return this._Relationships;
   }

   public boolean isRelationshipsInherited() {
      return false;
   }

   public boolean isRelationshipsSet() {
      return this._isSet(7);
   }

   public void setRelationships(RelationshipsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRelationships() != null && param0 != this.getRelationships()) {
         throw new BeanAlreadyExistsException(this.getRelationships() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 7)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         RelationshipsBean _oldVal = this._Relationships;
         this._Relationships = param0;
         this._postSet(7, _oldVal, param0);
      }
   }

   public RelationshipsBean createRelationships() {
      RelationshipsBeanImpl _val = new RelationshipsBeanImpl(this, -1);

      try {
         this.setRelationships(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRelationships(RelationshipsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Relationships;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRelationships((RelationshipsBean)null);
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

   public AssemblyDescriptorBean getAssemblyDescriptor() {
      return this._AssemblyDescriptor;
   }

   public boolean isAssemblyDescriptorInherited() {
      return false;
   }

   public boolean isAssemblyDescriptorSet() {
      return this._isSet(8);
   }

   public void setAssemblyDescriptor(AssemblyDescriptorBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAssemblyDescriptor() != null && param0 != this.getAssemblyDescriptor()) {
         throw new BeanAlreadyExistsException(this.getAssemblyDescriptor() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AssemblyDescriptorBean _oldVal = this._AssemblyDescriptor;
         this._AssemblyDescriptor = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public AssemblyDescriptorBean createAssemblyDescriptor() {
      AssemblyDescriptorBeanImpl _val = new AssemblyDescriptorBeanImpl(this, -1);

      try {
         this.setAssemblyDescriptor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAssemblyDescriptor(AssemblyDescriptorBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AssemblyDescriptor;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAssemblyDescriptor((AssemblyDescriptorBean)null);
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

   public String getEjbClientJar() {
      return this._EjbClientJar;
   }

   public boolean isEjbClientJarInherited() {
      return false;
   }

   public boolean isEjbClientJarSet() {
      return this._isSet(9);
   }

   public void setEjbClientJar(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbClientJar;
      this._EjbClientJar = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(10);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isMetadataComplete() {
      return this._MetadataComplete;
   }

   public boolean isMetadataCompleteInherited() {
      return false;
   }

   public boolean isMetadataCompleteSet() {
      return this._isSet(11);
   }

   public void setMetadataComplete(boolean param0) {
      boolean _oldVal = this._MetadataComplete;
      this._MetadataComplete = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(12);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(12, _oldVal, param0);
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._AssemblyDescriptor = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._EjbClientJar = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._EnterpriseBeans = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Interceptors = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JavaEEModuleName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ModuleName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Relationships = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Version = "3.2";
               if (initOne) {
                  break;
               }
            case 11:
               this._MetadataComplete = false;
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

   public static class SchemaHelper2 extends JavaEEModuleNameBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 12;
               }
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 15:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 4;
               }
               break;
            case 7:
               if (s.equals("version")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 2;
               }

               if (s.equals("module-name")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 3;
               }

               if (s.equals("interceptors")) {
                  return 6;
               }
               break;
            case 13:
               if (s.equals("relationships")) {
                  return 7;
               }
               break;
            case 14:
               if (s.equals("ejb-client-jar")) {
                  return 9;
               }
               break;
            case 16:
               if (s.equals("enterprise-beans")) {
                  return 5;
               }
               break;
            case 17:
               if (s.equals("metadata-complete")) {
                  return 11;
               }
               break;
            case 18:
               if (s.equals("javaee-module-name")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("assembly-descriptor")) {
                  return 8;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new IconBeanImpl.SchemaHelper2();
            case 5:
               return new EnterpriseBeansBeanImpl.SchemaHelper2();
            case 6:
               return new InterceptorsBeanImpl.SchemaHelper2();
            case 7:
               return new RelationshipsBeanImpl.SchemaHelper2();
            case 8:
               return new AssemblyDescriptorBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "ejb-jar";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "javaee-module-name";
            case 1:
               return "module-name";
            case 2:
               return "description";
            case 3:
               return "display-name";
            case 4:
               return "icon";
            case 5:
               return "enterprise-beans";
            case 6:
               return "interceptors";
            case 7:
               return "relationships";
            case 8:
               return "assembly-descriptor";
            case 9:
               return "ejb-client-jar";
            case 10:
               return "version";
            case 11:
               return "metadata-complete";
            case 12:
               return "id";
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
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends JavaEEModuleNameBeanImpl.Helper {
      private EjbJarBeanImpl bean;

      protected Helper(EjbJarBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JavaEEModuleName";
            case 1:
               return "ModuleName";
            case 2:
               return "Descriptions";
            case 3:
               return "DisplayNames";
            case 4:
               return "Icons";
            case 5:
               return "EnterpriseBeans";
            case 6:
               return "Interceptors";
            case 7:
               return "Relationships";
            case 8:
               return "AssemblyDescriptor";
            case 9:
               return "EjbClientJar";
            case 10:
               return "Version";
            case 11:
               return "MetadataComplete";
            case 12:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AssemblyDescriptor")) {
            return 8;
         } else if (propName.equals("Descriptions")) {
            return 2;
         } else if (propName.equals("DisplayNames")) {
            return 3;
         } else if (propName.equals("EjbClientJar")) {
            return 9;
         } else if (propName.equals("EnterpriseBeans")) {
            return 5;
         } else if (propName.equals("Icons")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 12;
         } else if (propName.equals("Interceptors")) {
            return 6;
         } else if (propName.equals("JavaEEModuleName")) {
            return 0;
         } else if (propName.equals("ModuleName")) {
            return 1;
         } else if (propName.equals("Relationships")) {
            return 7;
         } else if (propName.equals("Version")) {
            return 10;
         } else {
            return propName.equals("MetadataComplete") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAssemblyDescriptor() != null) {
            iterators.add(new ArrayIterator(new AssemblyDescriptorBean[]{this.bean.getAssemblyDescriptor()}));
         }

         if (this.bean.getEnterpriseBeans() != null) {
            iterators.add(new ArrayIterator(new EnterpriseBeansBean[]{this.bean.getEnterpriseBeans()}));
         }

         iterators.add(new ArrayIterator(this.bean.getIcons()));
         if (this.bean.getInterceptors() != null) {
            iterators.add(new ArrayIterator(new InterceptorsBean[]{this.bean.getInterceptors()}));
         }

         if (this.bean.getRelationships() != null) {
            iterators.add(new ArrayIterator(new RelationshipsBean[]{this.bean.getRelationships()}));
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
            childValue = this.computeChildHashValue(this.bean.getAssemblyDescriptor());
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

            if (this.bean.isEjbClientJarSet()) {
               buf.append("EjbClientJar");
               buf.append(String.valueOf(this.bean.getEjbClientJar()));
            }

            childValue = this.computeChildHashValue(this.bean.getEnterpriseBeans());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getIcons().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getIcons()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getInterceptors());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJavaEEModuleNameSet()) {
               buf.append("JavaEEModuleName");
               buf.append(String.valueOf(this.bean.getJavaEEModuleName()));
            }

            if (this.bean.isModuleNameSet()) {
               buf.append("ModuleName");
               buf.append(String.valueOf(this.bean.getModuleName()));
            }

            childValue = this.computeChildHashValue(this.bean.getRelationships());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            if (this.bean.isMetadataCompleteSet()) {
               buf.append("MetadataComplete");
               buf.append(String.valueOf(this.bean.isMetadataComplete()));
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
            EjbJarBeanImpl otherTyped = (EjbJarBeanImpl)other;
            this.computeChildDiff("AssemblyDescriptor", this.bean.getAssemblyDescriptor(), otherTyped.getAssemblyDescriptor(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeDiff("EjbClientJar", this.bean.getEjbClientJar(), otherTyped.getEjbClientJar(), false);
            this.computeChildDiff("EnterpriseBeans", this.bean.getEnterpriseBeans(), otherTyped.getEnterpriseBeans(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("Interceptors", this.bean.getInterceptors(), otherTyped.getInterceptors(), false);
            this.computeDiff("JavaEEModuleName", this.bean.getJavaEEModuleName(), otherTyped.getJavaEEModuleName(), false);
            this.computeDiff("ModuleName", this.bean.getModuleName(), otherTyped.getModuleName(), false);
            this.computeChildDiff("Relationships", this.bean.getRelationships(), otherTyped.getRelationships(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeDiff("MetadataComplete", this.bean.isMetadataComplete(), otherTyped.isMetadataComplete(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EjbJarBeanImpl original = (EjbJarBeanImpl)event.getSourceBean();
            EjbJarBeanImpl proposed = (EjbJarBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AssemblyDescriptor")) {
                  if (type == 2) {
                     original.setAssemblyDescriptor((AssemblyDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getAssemblyDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AssemblyDescriptor", (DescriptorBean)original.getAssemblyDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("EjbClientJar")) {
                  original.setEjbClientJar(proposed.getEjbClientJar());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("EnterpriseBeans")) {
                  if (type == 2) {
                     original.setEnterpriseBeans((EnterpriseBeansBean)this.createCopy((AbstractDescriptorBean)proposed.getEnterpriseBeans()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EnterpriseBeans", (DescriptorBean)original.getEnterpriseBeans());
                  }

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
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Interceptors")) {
                  if (type == 2) {
                     original.setInterceptors((InterceptorsBean)this.createCopy((AbstractDescriptorBean)proposed.getInterceptors()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Interceptors", (DescriptorBean)original.getInterceptors());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("JavaEEModuleName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ModuleName")) {
                  original.setModuleName(proposed.getModuleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Relationships")) {
                  if (type == 2) {
                     original.setRelationships((RelationshipsBean)this.createCopy((AbstractDescriptorBean)proposed.getRelationships()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Relationships", (DescriptorBean)original.getRelationships());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MetadataComplete")) {
                  original.setMetadataComplete(proposed.isMetadataComplete());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            EjbJarBeanImpl copy = (EjbJarBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AssemblyDescriptor")) && this.bean.isAssemblyDescriptorSet() && !copy._isSet(8)) {
               Object o = this.bean.getAssemblyDescriptor();
               copy.setAssemblyDescriptor((AssemblyDescriptorBean)null);
               copy.setAssemblyDescriptor(o == null ? null : (AssemblyDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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

            if ((excludeProps == null || !excludeProps.contains("EjbClientJar")) && this.bean.isEjbClientJarSet()) {
               copy.setEjbClientJar(this.bean.getEjbClientJar());
            }

            if ((excludeProps == null || !excludeProps.contains("EnterpriseBeans")) && this.bean.isEnterpriseBeansSet() && !copy._isSet(5)) {
               Object o = this.bean.getEnterpriseBeans();
               copy.setEnterpriseBeans((EnterpriseBeansBean)null);
               copy.setEnterpriseBeans(o == null ? null : (EnterpriseBeansBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(4)) {
               IconBean[] oldIcons = this.bean.getIcons();
               IconBean[] newIcons = new IconBean[oldIcons.length];

               for(int i = 0; i < newIcons.length; ++i) {
                  newIcons[i] = (IconBean)((IconBean)this.createCopy((AbstractDescriptorBean)oldIcons[i], includeObsolete));
               }

               copy.setIcons(newIcons);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Interceptors")) && this.bean.isInterceptorsSet() && !copy._isSet(6)) {
               Object o = this.bean.getInterceptors();
               copy.setInterceptors((InterceptorsBean)null);
               copy.setInterceptors(o == null ? null : (InterceptorsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JavaEEModuleName")) && this.bean.isJavaEEModuleNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleName")) && this.bean.isModuleNameSet()) {
               copy.setModuleName(this.bean.getModuleName());
            }

            if ((excludeProps == null || !excludeProps.contains("Relationships")) && this.bean.isRelationshipsSet() && !copy._isSet(7)) {
               Object o = this.bean.getRelationships();
               copy.setRelationships((RelationshipsBean)null);
               copy.setRelationships(o == null ? null : (RelationshipsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("MetadataComplete")) && this.bean.isMetadataCompleteSet()) {
               copy.setMetadataComplete(this.bean.isMetadataComplete());
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
         this.inferSubTree(this.bean.getAssemblyDescriptor(), clazz, annotation);
         if (clazz.isAnnotationPresent(Stateless.class)) {
            Stateless EnterpriseBeansAnno = (Stateless)clazz.getAnnotation(Stateless.class);
            if (!this.bean.isEnterpriseBeansSet()) {
               this.bean.createEnterpriseBeans();
            }

            Object property = this.bean.getEnterpriseBeans();
            this.inferSubTree(property, clazz, EnterpriseBeansAnno);
         }

         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getInterceptors(), clazz, annotation);
         this.inferSubTree(this.bean.getRelationships(), clazz, annotation);
      }
   }
}
