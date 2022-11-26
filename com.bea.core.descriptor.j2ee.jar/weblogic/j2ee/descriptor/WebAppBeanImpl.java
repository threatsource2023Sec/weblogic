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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.customizers.WebAppBeanCustomizer;
import weblogic.servlet.internal.WebXmlValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebAppBeanImpl extends WebAppBaseBeanImpl implements WebAppBean, Serializable {
   private AbsoluteOrderingBean[] _AbsoluteOrderings;
   private String[] _DefaultContextPaths;
   private EmptyBean[] _DenyUncoveredHttpMethods;
   private String _JavaEEModuleName;
   private String[] _ModuleNames;
   private String[] _RequestCharacterEncodings;
   private String[] _ResponseCharacterEncodings;
   private String _Version;
   private transient WebAppBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WebAppBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new WebAppBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WebAppBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new WebAppBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WebAppBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new WebAppBeanCustomizer(this);
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

   public boolean isJavaEEModuleNameInherited() {
      return false;
   }

   public boolean isJavaEEModuleNameSet() {
      return this._isSet(40);
   }

   public void setJavaEEModuleName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaEEModuleName;
      this._JavaEEModuleName = param0;
      this._postSet(40, _oldVal, param0);
   }

   public String[] getModuleNames() {
      return this._ModuleNames;
   }

   public boolean isModuleNamesInherited() {
      return false;
   }

   public boolean isModuleNamesSet() {
      return this._isSet(41);
   }

   public void setModuleNames(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ModuleNames;
      this._ModuleNames = param0;
      this._postSet(41, _oldVal, param0);
   }

   public void addDenyUncoveredHttpMethod(EmptyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 42)) {
         EmptyBean[] _new;
         if (this._isSet(42)) {
            _new = (EmptyBean[])((EmptyBean[])this._getHelper()._extendArray(this.getDenyUncoveredHttpMethods(), EmptyBean.class, param0));
         } else {
            _new = new EmptyBean[]{param0};
         }

         try {
            this.setDenyUncoveredHttpMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EmptyBean[] getDenyUncoveredHttpMethods() {
      return this._DenyUncoveredHttpMethods;
   }

   public boolean isDenyUncoveredHttpMethodsInherited() {
      return false;
   }

   public boolean isDenyUncoveredHttpMethodsSet() {
      return this._isSet(42);
   }

   public void removeDenyUncoveredHttpMethod(EmptyBean param0) {
      this.destroyDenyUncoveredHttpMethods(param0);
   }

   public void setDenyUncoveredHttpMethods(EmptyBean[] param0) throws InvalidAttributeValueException {
      EmptyBean[] param0 = param0 == null ? new EmptyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 42)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      EmptyBean[] _oldVal = this._DenyUncoveredHttpMethods;
      this._DenyUncoveredHttpMethods = (EmptyBean[])param0;
      this._postSet(42, _oldVal, param0);
   }

   public EmptyBean createDenyUncoveredHttpMethods() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.addDenyUncoveredHttpMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDenyUncoveredHttpMethods(EmptyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 42);
         EmptyBean[] _old = this.getDenyUncoveredHttpMethods();
         EmptyBean[] _new = (EmptyBean[])((EmptyBean[])this._getHelper()._removeElement(_old, EmptyBean.class, param0));
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
               this.setDenyUncoveredHttpMethods(_new);
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

   public boolean isDenyUncoveredHttpMethods() {
      return this._customizer.isDenyUncoveredHttpMethods();
   }

   public void addAbsoluteOrdering(AbsoluteOrderingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 43)) {
         AbsoluteOrderingBean[] _new;
         if (this._isSet(43)) {
            _new = (AbsoluteOrderingBean[])((AbsoluteOrderingBean[])this._getHelper()._extendArray(this.getAbsoluteOrderings(), AbsoluteOrderingBean.class, param0));
         } else {
            _new = new AbsoluteOrderingBean[]{param0};
         }

         try {
            this.setAbsoluteOrderings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AbsoluteOrderingBean[] getAbsoluteOrderings() {
      return this._AbsoluteOrderings;
   }

   public boolean isAbsoluteOrderingsInherited() {
      return false;
   }

   public boolean isAbsoluteOrderingsSet() {
      return this._isSet(43);
   }

   public void removeAbsoluteOrdering(AbsoluteOrderingBean param0) {
      this.destroyAbsoluteOrdering(param0);
   }

   public void setAbsoluteOrderings(AbsoluteOrderingBean[] param0) throws InvalidAttributeValueException {
      AbsoluteOrderingBean[] param0 = param0 == null ? new AbsoluteOrderingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 43)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AbsoluteOrderingBean[] _oldVal = this._AbsoluteOrderings;
      this._AbsoluteOrderings = (AbsoluteOrderingBean[])param0;
      this._postSet(43, _oldVal, param0);
   }

   public AbsoluteOrderingBean createAbsoluteOrdering() {
      AbsoluteOrderingBeanImpl _val = new AbsoluteOrderingBeanImpl(this, -1);

      try {
         this.addAbsoluteOrdering(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAbsoluteOrdering(AbsoluteOrderingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 43);
         AbsoluteOrderingBean[] _old = this.getAbsoluteOrderings();
         AbsoluteOrderingBean[] _new = (AbsoluteOrderingBean[])((AbsoluteOrderingBean[])this._getHelper()._removeElement(_old, AbsoluteOrderingBean.class, param0));
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
               this.setAbsoluteOrderings(_new);
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

   public String[] getDefaultContextPaths() {
      return this._DefaultContextPaths;
   }

   public boolean isDefaultContextPathsInherited() {
      return false;
   }

   public boolean isDefaultContextPathsSet() {
      return this._isSet(44);
   }

   public void addDefaultContextPath(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(44)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDefaultContextPaths(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDefaultContextPaths(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDefaultContextPath(String param0) {
      String[] _old = this.getDefaultContextPaths();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDefaultContextPaths(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDefaultContextPaths(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DefaultContextPaths;
      this._DefaultContextPaths = param0;
      this._postSet(44, _oldVal, param0);
   }

   public String[] getRequestCharacterEncodings() {
      return this._RequestCharacterEncodings;
   }

   public boolean isRequestCharacterEncodingsInherited() {
      return false;
   }

   public boolean isRequestCharacterEncodingsSet() {
      return this._isSet(45);
   }

   public void addRequestCharacterEncoding(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(45)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getRequestCharacterEncodings(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setRequestCharacterEncodings(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeRequestCharacterEncoding(String param0) {
      String[] _old = this.getRequestCharacterEncodings();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setRequestCharacterEncodings(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setRequestCharacterEncodings(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._RequestCharacterEncodings;
      this._RequestCharacterEncodings = param0;
      this._postSet(45, _oldVal, param0);
   }

   public String[] getResponseCharacterEncodings() {
      return this._ResponseCharacterEncodings;
   }

   public boolean isResponseCharacterEncodingsInherited() {
      return false;
   }

   public boolean isResponseCharacterEncodingsSet() {
      return this._isSet(46);
   }

   public void addResponseCharacterEncoding(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(46)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getResponseCharacterEncodings(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setResponseCharacterEncodings(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeResponseCharacterEncoding(String param0) {
      String[] _old = this.getResponseCharacterEncodings();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setResponseCharacterEncodings(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setResponseCharacterEncodings(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ResponseCharacterEncodings;
      this._ResponseCharacterEncodings = param0;
      this._postSet(46, _oldVal, param0);
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

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WebXmlValidator.validateSingletons(this);
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
         idx = 43;
      }

      try {
         switch (idx) {
            case 43:
               this._AbsoluteOrderings = new AbsoluteOrderingBean[0];
               if (initOne) {
                  break;
               }
            case 44:
               this._DefaultContextPaths = new String[0];
               if (initOne) {
                  break;
               }
            case 42:
               this._DenyUncoveredHttpMethods = new EmptyBean[0];
               if (initOne) {
                  break;
               }
            case 40:
               this._JavaEEModuleName = null;
               if (initOne) {
                  break;
               }
            case 41:
               this._ModuleNames = new String[0];
               if (initOne) {
                  break;
               }
            case 45:
               this._RequestCharacterEncodings = new String[0];
               if (initOne) {
                  break;
               }
            case 46:
               this._ResponseCharacterEncodings = new String[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._Version = "4.0";
               if (initOne) {
                  break;
               }
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
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

   public static class SchemaHelper2 extends WebAppBaseBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("version")) {
                  return 32;
               }
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 19:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 11:
               if (s.equals("module-name")) {
                  return 41;
               }
               break;
            case 17:
               if (s.equals("absolute-ordering")) {
                  return 43;
               }
               break;
            case 18:
               if (s.equals("javaee-module-name")) {
                  return 40;
               }
               break;
            case 20:
               if (s.equals("default-context-path")) {
                  return 44;
               }
               break;
            case 26:
               if (s.equals("request-character-encoding")) {
                  return 45;
               }
               break;
            case 27:
               if (s.equals("deny-uncovered-http-methods")) {
                  return 42;
               }

               if (s.equals("response-character-encoding")) {
                  return 46;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            case 3:
               return new EmptyBeanImpl.SchemaHelper2();
            case 4:
               return new ParamValueBeanImpl.SchemaHelper2();
            case 5:
               return new FilterBeanImpl.SchemaHelper2();
            case 6:
               return new FilterMappingBeanImpl.SchemaHelper2();
            case 7:
               return new ListenerBeanImpl.SchemaHelper2();
            case 8:
               return new ServletBeanImpl.SchemaHelper2();
            case 9:
               return new ServletMappingBeanImpl.SchemaHelper2();
            case 10:
               return new SessionConfigBeanImpl.SchemaHelper2();
            case 11:
               return new MimeMappingBeanImpl.SchemaHelper2();
            case 12:
               return new WelcomeFileListBeanImpl.SchemaHelper2();
            case 13:
               return new ErrorPageBeanImpl.SchemaHelper2();
            case 14:
               return new JspConfigBeanImpl.SchemaHelper2();
            case 15:
               return new SecurityConstraintBeanImpl.SchemaHelper2();
            case 16:
               return new LoginConfigBeanImpl.SchemaHelper2();
            case 17:
               return new SecurityRoleBeanImpl.SchemaHelper2();
            case 18:
               return new EnvEntryBeanImpl.SchemaHelper2();
            case 19:
               return new EjbRefBeanImpl.SchemaHelper2();
            case 20:
               return new EjbLocalRefBeanImpl.SchemaHelper2();
            case 21:
               return new ServiceRefBeanImpl.SchemaHelper2();
            case 22:
               return new ResourceRefBeanImpl.SchemaHelper2();
            case 23:
               return new ResourceEnvRefBeanImpl.SchemaHelper2();
            case 24:
               return new MessageDestinationRefBeanImpl.SchemaHelper2();
            case 25:
               return new PersistenceContextRefBeanImpl.SchemaHelper2();
            case 26:
               return new PersistenceUnitRefBeanImpl.SchemaHelper2();
            case 27:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 28:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 29:
               return new DataSourceBeanImpl.SchemaHelper2();
            case 30:
               return new MessageDestinationBeanImpl.SchemaHelper2();
            case 31:
               return new LocaleEncodingMappingListBeanImpl.SchemaHelper2();
            case 32:
            case 33:
            case 34:
            case 40:
            case 41:
            default:
               return super.getSchemaHelper(propIndex);
            case 35:
               return new JmsConnectionFactoryBeanImpl.SchemaHelper2();
            case 36:
               return new JmsDestinationBeanImpl.SchemaHelper2();
            case 37:
               return new MailSessionBeanImpl.SchemaHelper2();
            case 38:
               return new ConnectionFactoryResourceBeanImpl.SchemaHelper2();
            case 39:
               return new AdministeredObjectBeanImpl.SchemaHelper2();
            case 42:
               return new EmptyBeanImpl.SchemaHelper2();
            case 43:
               return new AbsoluteOrderingBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "web-app";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 32:
               return "version";
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            default:
               return super.getElementName(propIndex);
            case 40:
               return "javaee-module-name";
            case 41:
               return "module-name";
            case 42:
               return "deny-uncovered-http-methods";
            case 43:
               return "absolute-ordering";
            case 44:
               return "default-context-path";
            case 45:
               return "request-character-encoding";
            case 46:
               return "response-character-encoding";
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
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
            case 33:
            case 34:
            case 40:
            default:
               return super.isArray(propIndex);
            case 35:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
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
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
            case 33:
            case 34:
            case 40:
            case 41:
            default:
               return super.isBean(propIndex);
            case 35:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
            case 42:
               return true;
            case 43:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isMergeRulePrependDefined(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            default:
               return super.isMergeRulePrependDefined(propIndex);
         }
      }

      public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 4:
               return true;
            case 10:
               return true;
            case 12:
               return true;
            case 14:
               return true;
            case 16:
               return true;
            case 41:
               return true;
            default:
               return super.isMergeRuleIgnoreTargetDefined(propIndex);
         }
      }
   }

   protected static class Helper extends WebAppBaseBeanImpl.Helper {
      private WebAppBeanImpl bean;

      protected Helper(WebAppBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 32:
               return "Version";
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            default:
               return super.getPropertyName(propIndex);
            case 40:
               return "JavaEEModuleName";
            case 41:
               return "ModuleNames";
            case 42:
               return "DenyUncoveredHttpMethods";
            case 43:
               return "AbsoluteOrderings";
            case 44:
               return "DefaultContextPaths";
            case 45:
               return "RequestCharacterEncodings";
            case 46:
               return "ResponseCharacterEncodings";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AbsoluteOrderings")) {
            return 43;
         } else if (propName.equals("DefaultContextPaths")) {
            return 44;
         } else if (propName.equals("DenyUncoveredHttpMethods")) {
            return 42;
         } else if (propName.equals("JavaEEModuleName")) {
            return 40;
         } else if (propName.equals("ModuleNames")) {
            return 41;
         } else if (propName.equals("RequestCharacterEncodings")) {
            return 45;
         } else if (propName.equals("ResponseCharacterEncodings")) {
            return 46;
         } else {
            return propName.equals("Version") ? 32 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAbsoluteOrderings()));
         iterators.add(new ArrayIterator(this.bean.getAdministeredObjects()));
         iterators.add(new ArrayIterator(this.bean.getConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getContextParams()));
         iterators.add(new ArrayIterator(this.bean.getDataSources()));
         iterators.add(new ArrayIterator(this.bean.getDenyUncoveredHttpMethods()));
         iterators.add(new ArrayIterator(this.bean.getDistributables()));
         iterators.add(new ArrayIterator(this.bean.getEjbLocalRefs()));
         iterators.add(new ArrayIterator(this.bean.getEjbRefs()));
         iterators.add(new ArrayIterator(this.bean.getEnvEntries()));
         iterators.add(new ArrayIterator(this.bean.getErrorPages()));
         iterators.add(new ArrayIterator(this.bean.getFilterMappings()));
         iterators.add(new ArrayIterator(this.bean.getFilters()));
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         iterators.add(new ArrayIterator(this.bean.getJmsConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getJmsDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJspConfigs()));
         iterators.add(new ArrayIterator(this.bean.getListeners()));
         iterators.add(new ArrayIterator(this.bean.getLocaleEncodingMappingLists()));
         iterators.add(new ArrayIterator(this.bean.getLoginConfigs()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationRefs()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinations()));
         iterators.add(new ArrayIterator(this.bean.getMimeMappings()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceContextRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitRefs()));
         iterators.add(new ArrayIterator(this.bean.getPostConstructs()));
         iterators.add(new ArrayIterator(this.bean.getPreDestroys()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceRefs()));
         iterators.add(new ArrayIterator(this.bean.getSecurityConstraints()));
         iterators.add(new ArrayIterator(this.bean.getSecurityRoles()));
         iterators.add(new ArrayIterator(this.bean.getServiceRefs()));
         iterators.add(new ArrayIterator(this.bean.getServletMappings()));
         iterators.add(new ArrayIterator(this.bean.getServlets()));
         iterators.add(new ArrayIterator(this.bean.getSessionConfigs()));
         iterators.add(new ArrayIterator(this.bean.getWelcomeFileLists()));
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
            for(i = 0; i < this.bean.getAbsoluteOrderings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAbsoluteOrderings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDefaultContextPathsSet()) {
               buf.append("DefaultContextPaths");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultContextPaths())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDenyUncoveredHttpMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDenyUncoveredHttpMethods()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJavaEEModuleNameSet()) {
               buf.append("JavaEEModuleName");
               buf.append(String.valueOf(this.bean.getJavaEEModuleName()));
            }

            if (this.bean.isModuleNamesSet()) {
               buf.append("ModuleNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getModuleNames())));
            }

            if (this.bean.isRequestCharacterEncodingsSet()) {
               buf.append("RequestCharacterEncodings");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRequestCharacterEncodings())));
            }

            if (this.bean.isResponseCharacterEncodingsSet()) {
               buf.append("ResponseCharacterEncodings");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getResponseCharacterEncodings())));
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
            if (!(other instanceof WebAppBaseBeanImpl) || other instanceof WebAppBeanImpl) {
               WebAppBeanImpl otherTyped = (WebAppBeanImpl)other;
               this.computeChildDiff("AbsoluteOrderings", this.bean.getAbsoluteOrderings(), otherTyped.getAbsoluteOrderings(), false);
               this.computeDiff("DefaultContextPaths", this.bean.getDefaultContextPaths(), otherTyped.getDefaultContextPaths(), false);
               this.computeChildDiff("DenyUncoveredHttpMethods", this.bean.getDenyUncoveredHttpMethods(), otherTyped.getDenyUncoveredHttpMethods(), false);
               this.computeDiff("JavaEEModuleName", this.bean.getJavaEEModuleName(), otherTyped.getJavaEEModuleName(), false);
               this.computeDiff("ModuleNames", this.bean.getModuleNames(), otherTyped.getModuleNames(), false);
               this.computeDiff("RequestCharacterEncodings", this.bean.getRequestCharacterEncodings(), otherTyped.getRequestCharacterEncodings(), false);
               this.computeDiff("ResponseCharacterEncodings", this.bean.getResponseCharacterEncodings(), otherTyped.getResponseCharacterEncodings(), false);
               this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            }
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebAppBeanImpl original = (WebAppBeanImpl)event.getSourceBean();
            WebAppBeanImpl proposed = (WebAppBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AbsoluteOrderings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAbsoluteOrdering((AbsoluteOrderingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAbsoluteOrdering((AbsoluteOrderingBean)update.getRemovedObject());
                  }

                  if (original.getAbsoluteOrderings() == null || original.getAbsoluteOrderings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 43);
                  }
               } else if (prop.equals("DefaultContextPaths")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDefaultContextPath((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDefaultContextPath((String)update.getRemovedObject());
                  }

                  if (original.getDefaultContextPaths() == null || original.getDefaultContextPaths().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 44);
                  }
               } else if (prop.equals("DenyUncoveredHttpMethods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDenyUncoveredHttpMethod((EmptyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDenyUncoveredHttpMethod((EmptyBean)update.getRemovedObject());
                  }

                  if (original.getDenyUncoveredHttpMethods() == null || original.getDenyUncoveredHttpMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 42);
                  }
               } else if (prop.equals("JavaEEModuleName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("ModuleNames")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("RequestCharacterEncodings")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addRequestCharacterEncoding((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRequestCharacterEncoding((String)update.getRemovedObject());
                  }

                  if (original.getRequestCharacterEncodings() == null || original.getRequestCharacterEncodings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 45);
                  }
               } else if (prop.equals("ResponseCharacterEncodings")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addResponseCharacterEncoding((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResponseCharacterEncoding((String)update.getRemovedObject());
                  }

                  if (original.getResponseCharacterEncodings() == null || original.getResponseCharacterEncodings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 46);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
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
            WebAppBeanImpl copy = (WebAppBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AbsoluteOrderings")) && this.bean.isAbsoluteOrderingsSet() && !copy._isSet(43)) {
               AbsoluteOrderingBean[] oldAbsoluteOrderings = this.bean.getAbsoluteOrderings();
               AbsoluteOrderingBean[] newAbsoluteOrderings = new AbsoluteOrderingBean[oldAbsoluteOrderings.length];

               for(i = 0; i < newAbsoluteOrderings.length; ++i) {
                  newAbsoluteOrderings[i] = (AbsoluteOrderingBean)((AbsoluteOrderingBean)this.createCopy((AbstractDescriptorBean)oldAbsoluteOrderings[i], includeObsolete));
               }

               copy.setAbsoluteOrderings(newAbsoluteOrderings);
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("DefaultContextPaths")) && this.bean.isDefaultContextPathsSet()) {
               o = this.bean.getDefaultContextPaths();
               copy.setDefaultContextPaths(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DenyUncoveredHttpMethods")) && this.bean.isDenyUncoveredHttpMethodsSet() && !copy._isSet(42)) {
               EmptyBean[] oldDenyUncoveredHttpMethods = this.bean.getDenyUncoveredHttpMethods();
               EmptyBean[] newDenyUncoveredHttpMethods = new EmptyBean[oldDenyUncoveredHttpMethods.length];

               for(i = 0; i < newDenyUncoveredHttpMethods.length; ++i) {
                  newDenyUncoveredHttpMethods[i] = (EmptyBean)((EmptyBean)this.createCopy((AbstractDescriptorBean)oldDenyUncoveredHttpMethods[i], includeObsolete));
               }

               copy.setDenyUncoveredHttpMethods(newDenyUncoveredHttpMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("JavaEEModuleName")) && this.bean.isJavaEEModuleNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleNames")) && this.bean.isModuleNamesSet()) {
               o = this.bean.getModuleNames();
               copy.setModuleNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RequestCharacterEncodings")) && this.bean.isRequestCharacterEncodingsSet()) {
               o = this.bean.getRequestCharacterEncodings();
               copy.setRequestCharacterEncodings(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseCharacterEncodings")) && this.bean.isResponseCharacterEncodingsSet()) {
               o = this.bean.getResponseCharacterEncodings();
               copy.setResponseCharacterEncodings(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
         this.inferSubTree(this.bean.getAbsoluteOrderings(), clazz, annotation);
         this.inferSubTree(this.bean.getDenyUncoveredHttpMethods(), clazz, annotation);
      }
   }
}
