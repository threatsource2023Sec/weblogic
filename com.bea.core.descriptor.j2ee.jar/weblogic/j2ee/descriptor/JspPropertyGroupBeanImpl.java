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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.CompoundKey;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.servlet.internal.WebXmlValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JspPropertyGroupBeanImpl extends AbstractDescriptorBean implements JspPropertyGroupBean, Serializable {
   private String _Buffer;
   private String _DefaultContentType;
   private boolean _DeferredSyntaxAllowedAsLiteral;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private boolean _ElIgnored;
   private boolean _ErrorOnUndeclaredNamespace;
   private IconBean[] _Icons;
   private String _Id;
   private String[] _IncludeCodas;
   private String[] _IncludePreludes;
   private boolean _IsXml;
   private String _PageEncoding;
   private boolean _ScriptingInvalid;
   private boolean _TrimDirectiveWhitespaces;
   private String[] _UrlPatterns;
   private static SchemaHelper2 _schemaHelper;

   public JspPropertyGroupBeanImpl() {
      this._initializeProperty(-1);
   }

   public JspPropertyGroupBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JspPropertyGroupBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String[] getUrlPatterns() {
      return this._UrlPatterns;
   }

   public boolean isUrlPatternsInherited() {
      return false;
   }

   public boolean isUrlPatternsSet() {
      return this._isSet(3);
   }

   public void addUrlPattern(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getUrlPatterns(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setUrlPatterns(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeUrlPattern(String param0) {
      String[] _old = this.getUrlPatterns();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setUrlPatterns(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setUrlPatterns(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      WebXmlValidator.validateJspConfigURLPatterns(param0);
      String[] _oldVal = this._UrlPatterns;
      this._UrlPatterns = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isElIgnored() {
      return this._ElIgnored;
   }

   public boolean isElIgnoredInherited() {
      return false;
   }

   public boolean isElIgnoredSet() {
      return this._isSet(4);
   }

   public void setElIgnored(boolean param0) {
      boolean _oldVal = this._ElIgnored;
      this._ElIgnored = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getPageEncoding() {
      return this._PageEncoding;
   }

   public boolean isPageEncodingInherited() {
      return false;
   }

   public boolean isPageEncodingSet() {
      return this._isSet(5);
   }

   public void setPageEncoding(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PageEncoding;
      this._PageEncoding = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isScriptingInvalid() {
      return this._ScriptingInvalid;
   }

   public boolean isScriptingInvalidInherited() {
      return false;
   }

   public boolean isScriptingInvalidSet() {
      return this._isSet(6);
   }

   public void setScriptingInvalid(boolean param0) {
      boolean _oldVal = this._ScriptingInvalid;
      this._ScriptingInvalid = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean isIsXml() {
      return this._IsXml;
   }

   public boolean isIsXmlInherited() {
      return false;
   }

   public boolean isIsXmlSet() {
      return this._isSet(7);
   }

   public void setIsXml(boolean param0) {
      boolean _oldVal = this._IsXml;
      this._IsXml = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String[] getIncludePreludes() {
      return this._IncludePreludes;
   }

   public boolean isIncludePreludesInherited() {
      return false;
   }

   public boolean isIncludePreludesSet() {
      return this._isSet(8);
   }

   public void addIncludePrelude(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(8)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getIncludePreludes(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setIncludePreludes(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeIncludePrelude(String param0) {
      String[] _old = this.getIncludePreludes();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setIncludePreludes(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setIncludePreludes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._IncludePreludes;
      this._IncludePreludes = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String[] getIncludeCodas() {
      return this._IncludeCodas;
   }

   public boolean isIncludeCodasInherited() {
      return false;
   }

   public boolean isIncludeCodasSet() {
      return this._isSet(9);
   }

   public void addIncludeCoda(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(9)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getIncludeCodas(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setIncludeCodas(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeIncludeCoda(String param0) {
      String[] _old = this.getIncludeCodas();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setIncludeCodas(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setIncludeCodas(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._IncludeCodas;
      this._IncludeCodas = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isDeferredSyntaxAllowedAsLiteral() {
      return this._DeferredSyntaxAllowedAsLiteral;
   }

   public boolean isDeferredSyntaxAllowedAsLiteralInherited() {
      return false;
   }

   public boolean isDeferredSyntaxAllowedAsLiteralSet() {
      return this._isSet(10);
   }

   public void setDeferredSyntaxAllowedAsLiteral(boolean param0) {
      boolean _oldVal = this._DeferredSyntaxAllowedAsLiteral;
      this._DeferredSyntaxAllowedAsLiteral = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isTrimDirectiveWhitespaces() {
      return this._TrimDirectiveWhitespaces;
   }

   public boolean isTrimDirectiveWhitespacesInherited() {
      return false;
   }

   public boolean isTrimDirectiveWhitespacesSet() {
      return this._isSet(11);
   }

   public void setTrimDirectiveWhitespaces(boolean param0) {
      boolean _oldVal = this._TrimDirectiveWhitespaces;
      this._TrimDirectiveWhitespaces = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getDefaultContentType() {
      return this._DefaultContentType;
   }

   public boolean isDefaultContentTypeInherited() {
      return false;
   }

   public boolean isDefaultContentTypeSet() {
      return this._isSet(12);
   }

   public void setDefaultContentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultContentType;
      this._DefaultContentType = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getBuffer() {
      return this._Buffer;
   }

   public boolean isBufferInherited() {
      return false;
   }

   public boolean isBufferSet() {
      return this._isSet(13);
   }

   public void setBuffer(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Buffer;
      this._Buffer = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isErrorOnUndeclaredNamespace() {
      return this._ErrorOnUndeclaredNamespace;
   }

   public boolean isErrorOnUndeclaredNamespaceInherited() {
      return false;
   }

   public boolean isErrorOnUndeclaredNamespaceSet() {
      return this._isSet(14);
   }

   public void setErrorOnUndeclaredNamespace(boolean param0) {
      boolean _oldVal = this._ErrorOnUndeclaredNamespace;
      this._ErrorOnUndeclaredNamespace = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(15);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(15, _oldVal, param0);
   }

   public Object _getKey() {
      return new CompoundKey(new Object[]{this.getBuffer(), this.getDefaultContentType(), new CompoundKey(this.getIncludeCodas()), new CompoundKey(this.getIncludePreludes()), this.getPageEncoding(), new CompoundKey(this.getUrlPatterns()), !this._isSet(10) ? null : new Boolean(this.isDeferredSyntaxAllowedAsLiteral()), !this._isSet(4) ? null : new Boolean(this.isElIgnored()), !this._isSet(14) ? null : new Boolean(this.isErrorOnUndeclaredNamespace()), !this._isSet(7) ? null : new Boolean(this.isIsXml()), !this._isSet(6) ? null : new Boolean(this.isScriptingInvalid()), !this._isSet(11) ? null : new Boolean(this.isTrimDirectiveWhitespaces())});
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._Buffer = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._DefaultContentType = null;
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
            case 2:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._IncludeCodas = new String[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._IncludePreludes = new String[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._PageEncoding = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._UrlPatterns = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._DeferredSyntaxAllowedAsLiteral = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._ElIgnored = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._ErrorOnUndeclaredNamespace = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._IsXml = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._ScriptingInvalid = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._TrimDirectiveWhitespaces = false;
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
                  return 15;
               }
            case 3:
            case 5:
            case 7:
            case 8:
            case 9:
            case 14:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 6:
               if (s.equals("buffer")) {
                  return 13;
               }

               if (s.equals("is-xml")) {
                  return 7;
               }
               break;
            case 10:
               if (s.equals("el-ignored")) {
                  return 4;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("url-pattern")) {
                  return 3;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }

               if (s.equals("include-coda")) {
                  return 9;
               }
               break;
            case 13:
               if (s.equals("page-encoding")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("include-prelude")) {
                  return 8;
               }
               break;
            case 17:
               if (s.equals("scripting-invalid")) {
                  return 6;
               }
               break;
            case 20:
               if (s.equals("default-content-type")) {
                  return 12;
               }
               break;
            case 26:
               if (s.equals("trim-directive-whitespaces")) {
                  return 11;
               }
               break;
            case 29:
               if (s.equals("error-on-undeclared-namespace")) {
                  return 14;
               }
               break;
            case 34:
               if (s.equals("deferred-syntax-allowed-as-literal")) {
                  return 10;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
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
               return "url-pattern";
            case 4:
               return "el-ignored";
            case 5:
               return "page-encoding";
            case 6:
               return "scripting-invalid";
            case 7:
               return "is-xml";
            case 8:
               return "include-prelude";
            case 9:
               return "include-coda";
            case 10:
               return "deferred-syntax-allowed-as-literal";
            case 11:
               return "trim-directive-whitespaces";
            case 12:
               return "default-content-type";
            case 13:
               return "buffer";
            case 14:
               return "error-on-undeclared-namespace";
            case 15:
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
            case 5:
            case 6:
            case 7:
            default:
               return super.isArray(propIndex);
            case 8:
               return true;
            case 9:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKeyComponent(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isKeyComponent(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("buffer");
         indices.add("default-content-type");
         indices.add("include-coda");
         indices.add("include-prelude");
         indices.add("page-encoding");
         indices.add("url-pattern");
         indices.add("deferred-syntax-allowed-as-literal");
         indices.add("el-ignored");
         indices.add("error-on-undeclared-namespace");
         indices.add("is-xml");
         indices.add("scripting-invalid");
         indices.add("trim-directive-whitespaces");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JspPropertyGroupBeanImpl bean;

      protected Helper(JspPropertyGroupBeanImpl bean) {
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
               return "UrlPatterns";
            case 4:
               return "ElIgnored";
            case 5:
               return "PageEncoding";
            case 6:
               return "ScriptingInvalid";
            case 7:
               return "IsXml";
            case 8:
               return "IncludePreludes";
            case 9:
               return "IncludeCodas";
            case 10:
               return "DeferredSyntaxAllowedAsLiteral";
            case 11:
               return "TrimDirectiveWhitespaces";
            case 12:
               return "DefaultContentType";
            case 13:
               return "Buffer";
            case 14:
               return "ErrorOnUndeclaredNamespace";
            case 15:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Buffer")) {
            return 13;
         } else if (propName.equals("DefaultContentType")) {
            return 12;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("DisplayNames")) {
            return 1;
         } else if (propName.equals("Icons")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 15;
         } else if (propName.equals("IncludeCodas")) {
            return 9;
         } else if (propName.equals("IncludePreludes")) {
            return 8;
         } else if (propName.equals("PageEncoding")) {
            return 5;
         } else if (propName.equals("UrlPatterns")) {
            return 3;
         } else if (propName.equals("DeferredSyntaxAllowedAsLiteral")) {
            return 10;
         } else if (propName.equals("ElIgnored")) {
            return 4;
         } else if (propName.equals("ErrorOnUndeclaredNamespace")) {
            return 14;
         } else if (propName.equals("IsXml")) {
            return 7;
         } else if (propName.equals("ScriptingInvalid")) {
            return 6;
         } else {
            return propName.equals("TrimDirectiveWhitespaces") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getIcons()));
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
            if (this.bean.isBufferSet()) {
               buf.append("Buffer");
               buf.append(String.valueOf(this.bean.getBuffer()));
            }

            if (this.bean.isDefaultContentTypeSet()) {
               buf.append("DefaultContentType");
               buf.append(String.valueOf(this.bean.getDefaultContentType()));
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

            if (this.bean.isIncludeCodasSet()) {
               buf.append("IncludeCodas");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIncludeCodas())));
            }

            if (this.bean.isIncludePreludesSet()) {
               buf.append("IncludePreludes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIncludePreludes())));
            }

            if (this.bean.isPageEncodingSet()) {
               buf.append("PageEncoding");
               buf.append(String.valueOf(this.bean.getPageEncoding()));
            }

            if (this.bean.isUrlPatternsSet()) {
               buf.append("UrlPatterns");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUrlPatterns())));
            }

            if (this.bean.isDeferredSyntaxAllowedAsLiteralSet()) {
               buf.append("DeferredSyntaxAllowedAsLiteral");
               buf.append(String.valueOf(this.bean.isDeferredSyntaxAllowedAsLiteral()));
            }

            if (this.bean.isElIgnoredSet()) {
               buf.append("ElIgnored");
               buf.append(String.valueOf(this.bean.isElIgnored()));
            }

            if (this.bean.isErrorOnUndeclaredNamespaceSet()) {
               buf.append("ErrorOnUndeclaredNamespace");
               buf.append(String.valueOf(this.bean.isErrorOnUndeclaredNamespace()));
            }

            if (this.bean.isIsXmlSet()) {
               buf.append("IsXml");
               buf.append(String.valueOf(this.bean.isIsXml()));
            }

            if (this.bean.isScriptingInvalidSet()) {
               buf.append("ScriptingInvalid");
               buf.append(String.valueOf(this.bean.isScriptingInvalid()));
            }

            if (this.bean.isTrimDirectiveWhitespacesSet()) {
               buf.append("TrimDirectiveWhitespaces");
               buf.append(String.valueOf(this.bean.isTrimDirectiveWhitespaces()));
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
            JspPropertyGroupBeanImpl otherTyped = (JspPropertyGroupBeanImpl)other;
            this.computeDiff("Buffer", this.bean.getBuffer(), otherTyped.getBuffer(), false);
            this.computeDiff("DefaultContentType", this.bean.getDefaultContentType(), otherTyped.getDefaultContentType(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IncludeCodas", this.bean.getIncludeCodas(), otherTyped.getIncludeCodas(), false);
            this.computeDiff("IncludePreludes", this.bean.getIncludePreludes(), otherTyped.getIncludePreludes(), false);
            this.computeDiff("PageEncoding", this.bean.getPageEncoding(), otherTyped.getPageEncoding(), false);
            this.computeDiff("UrlPatterns", this.bean.getUrlPatterns(), otherTyped.getUrlPatterns(), false);
            this.computeDiff("DeferredSyntaxAllowedAsLiteral", this.bean.isDeferredSyntaxAllowedAsLiteral(), otherTyped.isDeferredSyntaxAllowedAsLiteral(), false);
            this.computeDiff("ElIgnored", this.bean.isElIgnored(), otherTyped.isElIgnored(), false);
            this.computeDiff("ErrorOnUndeclaredNamespace", this.bean.isErrorOnUndeclaredNamespace(), otherTyped.isErrorOnUndeclaredNamespace(), false);
            this.computeDiff("IsXml", this.bean.isIsXml(), otherTyped.isIsXml(), false);
            this.computeDiff("ScriptingInvalid", this.bean.isScriptingInvalid(), otherTyped.isScriptingInvalid(), false);
            this.computeDiff("TrimDirectiveWhitespaces", this.bean.isTrimDirectiveWhitespaces(), otherTyped.isTrimDirectiveWhitespaces(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JspPropertyGroupBeanImpl original = (JspPropertyGroupBeanImpl)event.getSourceBean();
            JspPropertyGroupBeanImpl proposed = (JspPropertyGroupBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Buffer")) {
                  original.setBuffer(proposed.getBuffer());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DefaultContentType")) {
                  original.setDefaultContentType(proposed.getDefaultContentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("IncludeCodas")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addIncludeCoda((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeIncludeCoda((String)update.getRemovedObject());
                  }

                  if (original.getIncludeCodas() == null || original.getIncludeCodas().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("IncludePreludes")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addIncludePrelude((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeIncludePrelude((String)update.getRemovedObject());
                  }

                  if (original.getIncludePreludes() == null || original.getIncludePreludes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("PageEncoding")) {
                  original.setPageEncoding(proposed.getPageEncoding());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("UrlPatterns")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addUrlPattern((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeUrlPattern((String)update.getRemovedObject());
                  }

                  if (original.getUrlPatterns() == null || original.getUrlPatterns().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("DeferredSyntaxAllowedAsLiteral")) {
                  original.setDeferredSyntaxAllowedAsLiteral(proposed.isDeferredSyntaxAllowedAsLiteral());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ElIgnored")) {
                  original.setElIgnored(proposed.isElIgnored());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ErrorOnUndeclaredNamespace")) {
                  original.setErrorOnUndeclaredNamespace(proposed.isErrorOnUndeclaredNamespace());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("IsXml")) {
                  original.setIsXml(proposed.isIsXml());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ScriptingInvalid")) {
                  original.setScriptingInvalid(proposed.isScriptingInvalid());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("TrimDirectiveWhitespaces")) {
                  original.setTrimDirectiveWhitespaces(proposed.isTrimDirectiveWhitespaces());
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
            JspPropertyGroupBeanImpl copy = (JspPropertyGroupBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Buffer")) && this.bean.isBufferSet()) {
               copy.setBuffer(this.bean.getBuffer());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultContentType")) && this.bean.isDefaultContentTypeSet()) {
               copy.setDefaultContentType(this.bean.getDefaultContentType());
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

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(2)) {
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

            if ((excludeProps == null || !excludeProps.contains("IncludeCodas")) && this.bean.isIncludeCodasSet()) {
               o = this.bean.getIncludeCodas();
               copy.setIncludeCodas(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("IncludePreludes")) && this.bean.isIncludePreludesSet()) {
               o = this.bean.getIncludePreludes();
               copy.setIncludePreludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("PageEncoding")) && this.bean.isPageEncodingSet()) {
               copy.setPageEncoding(this.bean.getPageEncoding());
            }

            if ((excludeProps == null || !excludeProps.contains("UrlPatterns")) && this.bean.isUrlPatternsSet()) {
               o = this.bean.getUrlPatterns();
               copy.setUrlPatterns(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DeferredSyntaxAllowedAsLiteral")) && this.bean.isDeferredSyntaxAllowedAsLiteralSet()) {
               copy.setDeferredSyntaxAllowedAsLiteral(this.bean.isDeferredSyntaxAllowedAsLiteral());
            }

            if ((excludeProps == null || !excludeProps.contains("ElIgnored")) && this.bean.isElIgnoredSet()) {
               copy.setElIgnored(this.bean.isElIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("ErrorOnUndeclaredNamespace")) && this.bean.isErrorOnUndeclaredNamespaceSet()) {
               copy.setErrorOnUndeclaredNamespace(this.bean.isErrorOnUndeclaredNamespace());
            }

            if ((excludeProps == null || !excludeProps.contains("IsXml")) && this.bean.isIsXmlSet()) {
               copy.setIsXml(this.bean.isIsXml());
            }

            if ((excludeProps == null || !excludeProps.contains("ScriptingInvalid")) && this.bean.isScriptingInvalidSet()) {
               copy.setScriptingInvalid(this.bean.isScriptingInvalid());
            }

            if ((excludeProps == null || !excludeProps.contains("TrimDirectiveWhitespaces")) && this.bean.isTrimDirectiveWhitespacesSet()) {
               copy.setTrimDirectiveWhitespaces(this.bean.isTrimDirectiveWhitespaces());
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
      }
   }
}
