package weblogic.j2ee.descriptor;

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

public class ServletBeanImpl extends AbstractDescriptorBean implements ServletBean, Serializable {
   private boolean _AsyncSupported;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private boolean _Enabled;
   private IconBean[] _Icons;
   private String _Id;
   private ParamValueBean[] _InitParams;
   private String _JspFile;
   private String _LoadOnStartup;
   private MultipartConfigBean _MultipartConfig;
   private RunAsBean _RunAs;
   private SecurityRoleRefBean[] _SecurityRoleRefs;
   private String _ServletClass;
   private String _ServletName;
   private static SchemaHelper2 _schemaHelper;

   public ServletBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServletBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServletBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getServletName() {
      return this._ServletName;
   }

   public boolean isServletNameInherited() {
      return false;
   }

   public boolean isServletNameSet() {
      return this._isSet(3);
   }

   public void setServletName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServletName;
      this._ServletName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getServletClass() {
      return this._ServletClass;
   }

   public boolean isServletClassInherited() {
      return false;
   }

   public boolean isServletClassSet() {
      return this._isSet(4);
   }

   public void setServletClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServletClass;
      this._ServletClass = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getJspFile() {
      return this._JspFile;
   }

   public boolean isJspFileInherited() {
      return false;
   }

   public boolean isJspFileSet() {
      return this._isSet(5);
   }

   public void setJspFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JspFile;
      this._JspFile = param0;
      this._postSet(5, _oldVal, param0);
   }

   public void addInitParam(ParamValueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         ParamValueBean[] _new;
         if (this._isSet(6)) {
            _new = (ParamValueBean[])((ParamValueBean[])this._getHelper()._extendArray(this.getInitParams(), ParamValueBean.class, param0));
         } else {
            _new = new ParamValueBean[]{param0};
         }

         try {
            this.setInitParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ParamValueBean[] getInitParams() {
      return this._InitParams;
   }

   public boolean isInitParamsInherited() {
      return false;
   }

   public boolean isInitParamsSet() {
      return this._isSet(6);
   }

   public void removeInitParam(ParamValueBean param0) {
      this.destroyInitParam(param0);
   }

   public void setInitParams(ParamValueBean[] param0) throws InvalidAttributeValueException {
      ParamValueBean[] param0 = param0 == null ? new ParamValueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ParamValueBean[] _oldVal = this._InitParams;
      this._InitParams = (ParamValueBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public ParamValueBean createInitParam() {
      ParamValueBeanImpl _val = new ParamValueBeanImpl(this, -1);

      try {
         this.addInitParam(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInitParam(ParamValueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         ParamValueBean[] _old = this.getInitParams();
         ParamValueBean[] _new = (ParamValueBean[])((ParamValueBean[])this._getHelper()._removeElement(_old, ParamValueBean.class, param0));
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
               this.setInitParams(_new);
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

   public ParamValueBean lookupInitParam(String param0) {
      Object[] aary = (Object[])this._InitParams;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ParamValueBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ParamValueBeanImpl)it.previous();
      } while(!bean.getParamName().equals(param0));

      return bean;
   }

   public ParamValueBean createInitParam(String param0) {
      ParamValueBeanImpl _val = new ParamValueBeanImpl(this, -1);

      try {
         _val.setParamName(param0);
         this.addInitParam(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getLoadOnStartup() {
      return this._LoadOnStartup;
   }

   public boolean isLoadOnStartupInherited() {
      return false;
   }

   public boolean isLoadOnStartupSet() {
      return this._isSet(7);
   }

   public void setLoadOnStartup(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LoadOnStartup;
      this._LoadOnStartup = param0;
      this._postSet(7, _oldVal, param0);
   }

   public RunAsBean getRunAs() {
      return this._RunAs;
   }

   public boolean isRunAsInherited() {
      return false;
   }

   public boolean isRunAsSet() {
      return this._isSet(8);
   }

   public void setRunAs(RunAsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRunAs() != null && param0 != this.getRunAs()) {
         throw new BeanAlreadyExistsException(this.getRunAs() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         RunAsBean _oldVal = this._RunAs;
         this._RunAs = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public RunAsBean createRunAs() {
      RunAsBeanImpl _val = new RunAsBeanImpl(this, -1);

      try {
         this.setRunAs(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRunAs(RunAsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RunAs;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRunAs((RunAsBean)null);
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

   public void addSecurityRoleRef(SecurityRoleRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         SecurityRoleRefBean[] _new;
         if (this._isSet(9)) {
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
      return this._isSet(9);
   }

   public void removeSecurityRoleRef(SecurityRoleRefBean param0) {
      this.destroySecurityRoleRef(param0);
   }

   public void setSecurityRoleRefs(SecurityRoleRefBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleRefBean[] param0 = param0 == null ? new SecurityRoleRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleRefBean[] _oldVal = this._SecurityRoleRefs;
      this._SecurityRoleRefs = (SecurityRoleRefBean[])param0;
      this._postSet(9, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 9);
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

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isAsyncSupported() {
      return this._AsyncSupported;
   }

   public boolean isAsyncSupportedInherited() {
      return false;
   }

   public boolean isAsyncSupportedSet() {
      return this._isSet(11);
   }

   public void setAsyncSupported(boolean param0) {
      boolean _oldVal = this._AsyncSupported;
      this._AsyncSupported = param0;
      this._postSet(11, _oldVal, param0);
   }

   public MultipartConfigBean getMultipartConfig() {
      return this._MultipartConfig;
   }

   public boolean isMultipartConfigInherited() {
      return false;
   }

   public boolean isMultipartConfigSet() {
      return this._isSet(12);
   }

   public void setMultipartConfig(MultipartConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMultipartConfig() != null && param0 != this.getMultipartConfig()) {
         throw new BeanAlreadyExistsException(this.getMultipartConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 12)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MultipartConfigBean _oldVal = this._MultipartConfig;
         this._MultipartConfig = param0;
         this._postSet(12, _oldVal, param0);
      }
   }

   public MultipartConfigBean createMultipartConfig() {
      MultipartConfigBeanImpl _val = new MultipartConfigBeanImpl(this, -1);

      try {
         this.setMultipartConfig(_val);
         return _val;
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
      return this._isSet(13);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(13, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getServletName();
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
         case 12:
            if (s.equals("servlet-name")) {
               return info.compareXpaths(this._getPropertyXpath("servlet-name"));
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
         idx = 0;
      }

      try {
         switch (idx) {
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
            case 2:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._InitParams = new ParamValueBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._JspFile = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._LoadOnStartup = "-1";
               if (initOne) {
                  break;
               }
            case 12:
               this._MultipartConfig = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._RunAs = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._SecurityRoleRefs = new SecurityRoleRefBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._ServletClass = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ServletName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._AsyncSupported = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = true;
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
                  return 13;
               }
            case 3:
            case 5:
            case 9:
            case 14:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 6:
               if (s.equals("run-as")) {
                  return 8;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("jsp-file")) {
                  return 5;
               }
               break;
            case 10:
               if (s.equals("init-param")) {
                  return 6;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }

               if (s.equals("servlet-name")) {
                  return 3;
               }
               break;
            case 13:
               if (s.equals("servlet-class")) {
                  return 4;
               }
               break;
            case 15:
               if (s.equals("load-on-startup")) {
                  return 7;
               }

               if (s.equals("async-supported")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("multipart-config")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("security-role-ref")) {
                  return 9;
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
            case 7:
            case 10:
            case 11:
            default:
               return super.getSchemaHelper(propIndex);
            case 6:
               return new ParamValueBeanImpl.SchemaHelper2();
            case 8:
               return new RunAsBeanImpl.SchemaHelper2();
            case 9:
               return new SecurityRoleRefBeanImpl.SchemaHelper2();
            case 12:
               return new MultipartConfigBeanImpl.SchemaHelper2();
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
               return "servlet-name";
            case 4:
               return "servlet-class";
            case 5:
               return "jsp-file";
            case 6:
               return "init-param";
            case 7:
               return "load-on-startup";
            case 8:
               return "run-as";
            case 9:
               return "security-role-ref";
            case 10:
               return "enabled";
            case 11:
               return "async-supported";
            case 12:
               return "multipart-config";
            case 13:
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
            case 7:
            case 8:
            default:
               return super.isArray(propIndex);
            case 6:
               return true;
            case 9:
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
            case 7:
            case 10:
            case 11:
            default:
               return super.isBean(propIndex);
            case 6:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 12:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
         indices.add("servlet-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServletBeanImpl bean;

      protected Helper(ServletBeanImpl bean) {
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
               return "ServletName";
            case 4:
               return "ServletClass";
            case 5:
               return "JspFile";
            case 6:
               return "InitParams";
            case 7:
               return "LoadOnStartup";
            case 8:
               return "RunAs";
            case 9:
               return "SecurityRoleRefs";
            case 10:
               return "Enabled";
            case 11:
               return "AsyncSupported";
            case 12:
               return "MultipartConfig";
            case 13:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("DisplayNames")) {
            return 1;
         } else if (propName.equals("Icons")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 13;
         } else if (propName.equals("InitParams")) {
            return 6;
         } else if (propName.equals("JspFile")) {
            return 5;
         } else if (propName.equals("LoadOnStartup")) {
            return 7;
         } else if (propName.equals("MultipartConfig")) {
            return 12;
         } else if (propName.equals("RunAs")) {
            return 8;
         } else if (propName.equals("SecurityRoleRefs")) {
            return 9;
         } else if (propName.equals("ServletClass")) {
            return 4;
         } else if (propName.equals("ServletName")) {
            return 3;
         } else if (propName.equals("AsyncSupported")) {
            return 11;
         } else {
            return propName.equals("Enabled") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         iterators.add(new ArrayIterator(this.bean.getInitParams()));
         if (this.bean.getMultipartConfig() != null) {
            iterators.add(new ArrayIterator(new MultipartConfigBean[]{this.bean.getMultipartConfig()}));
         }

         if (this.bean.getRunAs() != null) {
            iterators.add(new ArrayIterator(new RunAsBean[]{this.bean.getRunAs()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSecurityRoleRefs()));
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
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isDisplayNamesSet()) {
               buf.append("DisplayNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisplayNames())));
            }

            childValue = 0L;

            int i;
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

            for(i = 0; i < this.bean.getInitParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInitParams()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJspFileSet()) {
               buf.append("JspFile");
               buf.append(String.valueOf(this.bean.getJspFile()));
            }

            if (this.bean.isLoadOnStartupSet()) {
               buf.append("LoadOnStartup");
               buf.append(String.valueOf(this.bean.getLoadOnStartup()));
            }

            childValue = this.computeChildHashValue(this.bean.getMultipartConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getRunAs());
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

            if (this.bean.isServletClassSet()) {
               buf.append("ServletClass");
               buf.append(String.valueOf(this.bean.getServletClass()));
            }

            if (this.bean.isServletNameSet()) {
               buf.append("ServletName");
               buf.append(String.valueOf(this.bean.getServletName()));
            }

            if (this.bean.isAsyncSupportedSet()) {
               buf.append("AsyncSupported");
               buf.append(String.valueOf(this.bean.isAsyncSupported()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            ServletBeanImpl otherTyped = (ServletBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InitParams", this.bean.getInitParams(), otherTyped.getInitParams(), false);
            this.computeDiff("JspFile", this.bean.getJspFile(), otherTyped.getJspFile(), false);
            this.computeDiff("LoadOnStartup", this.bean.getLoadOnStartup(), otherTyped.getLoadOnStartup(), false);
            this.computeChildDiff("MultipartConfig", this.bean.getMultipartConfig(), otherTyped.getMultipartConfig(), false);
            this.computeChildDiff("RunAs", this.bean.getRunAs(), otherTyped.getRunAs(), false);
            this.computeChildDiff("SecurityRoleRefs", this.bean.getSecurityRoleRefs(), otherTyped.getSecurityRoleRefs(), false);
            this.computeDiff("ServletClass", this.bean.getServletClass(), otherTyped.getServletClass(), false);
            this.computeDiff("ServletName", this.bean.getServletName(), otherTyped.getServletName(), false);
            this.computeDiff("AsyncSupported", this.bean.isAsyncSupported(), otherTyped.isAsyncSupported(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServletBeanImpl original = (ServletBeanImpl)event.getSourceBean();
            ServletBeanImpl proposed = (ServletBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
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
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("InitParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInitParam((ParamValueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInitParam((ParamValueBean)update.getRemovedObject());
                  }

                  if (original.getInitParams() == null || original.getInitParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("JspFile")) {
                  original.setJspFile(proposed.getJspFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("LoadOnStartup")) {
                  original.setLoadOnStartup(proposed.getLoadOnStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MultipartConfig")) {
                  if (type == 2) {
                     original.setMultipartConfig((MultipartConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getMultipartConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MultipartConfig", (DescriptorBean)original.getMultipartConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RunAs")) {
                  if (type == 2) {
                     original.setRunAs((RunAsBean)this.createCopy((AbstractDescriptorBean)proposed.getRunAs()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RunAs", (DescriptorBean)original.getRunAs());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("ServletClass")) {
                  original.setServletClass(proposed.getServletClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ServletName")) {
                  original.setServletName(proposed.getServletName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("AsyncSupported")) {
                  original.setAsyncSupported(proposed.isAsyncSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
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
            ServletBeanImpl copy = (ServletBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            int i;
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

            if ((excludeProps == null || !excludeProps.contains("InitParams")) && this.bean.isInitParamsSet() && !copy._isSet(6)) {
               ParamValueBean[] oldInitParams = this.bean.getInitParams();
               ParamValueBean[] newInitParams = new ParamValueBean[oldInitParams.length];

               for(i = 0; i < newInitParams.length; ++i) {
                  newInitParams[i] = (ParamValueBean)((ParamValueBean)this.createCopy((AbstractDescriptorBean)oldInitParams[i], includeObsolete));
               }

               copy.setInitParams(newInitParams);
            }

            if ((excludeProps == null || !excludeProps.contains("JspFile")) && this.bean.isJspFileSet()) {
               copy.setJspFile(this.bean.getJspFile());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadOnStartup")) && this.bean.isLoadOnStartupSet()) {
               copy.setLoadOnStartup(this.bean.getLoadOnStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("MultipartConfig")) && this.bean.isMultipartConfigSet() && !copy._isSet(12)) {
               Object o = this.bean.getMultipartConfig();
               copy.setMultipartConfig((MultipartConfigBean)null);
               copy.setMultipartConfig(o == null ? null : (MultipartConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RunAs")) && this.bean.isRunAsSet() && !copy._isSet(8)) {
               Object o = this.bean.getRunAs();
               copy.setRunAs((RunAsBean)null);
               copy.setRunAs(o == null ? null : (RunAsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoleRefs")) && this.bean.isSecurityRoleRefsSet() && !copy._isSet(9)) {
               SecurityRoleRefBean[] oldSecurityRoleRefs = this.bean.getSecurityRoleRefs();
               SecurityRoleRefBean[] newSecurityRoleRefs = new SecurityRoleRefBean[oldSecurityRoleRefs.length];

               for(i = 0; i < newSecurityRoleRefs.length; ++i) {
                  newSecurityRoleRefs[i] = (SecurityRoleRefBean)((SecurityRoleRefBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoleRefs[i], includeObsolete));
               }

               copy.setSecurityRoleRefs(newSecurityRoleRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ServletClass")) && this.bean.isServletClassSet()) {
               copy.setServletClass(this.bean.getServletClass());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletName")) && this.bean.isServletNameSet()) {
               copy.setServletName(this.bean.getServletName());
            }

            if ((excludeProps == null || !excludeProps.contains("AsyncSupported")) && this.bean.isAsyncSupportedSet()) {
               copy.setAsyncSupported(this.bean.isAsyncSupported());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getInitParams(), clazz, annotation);
         this.inferSubTree(this.bean.getMultipartConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getRunAs(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoleRefs(), clazz, annotation);
      }
   }
}
