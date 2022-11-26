package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.servlet.internal.WebXmlValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WebResourceCollectionBeanImpl extends AbstractDescriptorBean implements WebResourceCollectionBean, Serializable {
   private String[] _Descriptions;
   private String[] _HttpMethodOmissions;
   private String[] _HttpMethods;
   private String _Id;
   private String[] _UrlPatterns;
   private String _WebResourceName;
   private static SchemaHelper2 _schemaHelper;

   public WebResourceCollectionBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebResourceCollectionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebResourceCollectionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWebResourceName() {
      return this._WebResourceName;
   }

   public boolean isWebResourceNameInherited() {
      return false;
   }

   public boolean isWebResourceNameSet() {
      return this._isSet(0);
   }

   public void setWebResourceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WebResourceName;
      this._WebResourceName = param0;
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

   public String[] getUrlPatterns() {
      return this._UrlPatterns;
   }

   public boolean isUrlPatternsInherited() {
      return false;
   }

   public boolean isUrlPatternsSet() {
      return this._isSet(2);
   }

   public void addUrlPattern(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(2)) {
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
      WebXmlValidator.validateURLPatterns(param0);
      String[] _oldVal = this._UrlPatterns;
      this._UrlPatterns = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getHttpMethods() {
      return this._HttpMethods;
   }

   public boolean isHttpMethodsInherited() {
      return false;
   }

   public boolean isHttpMethodsSet() {
      return this._isSet(3);
   }

   public void addHttpMethod(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getHttpMethods(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setHttpMethods(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeHttpMethod(String param0) {
      String[] _old = this.getHttpMethods();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setHttpMethods(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setHttpMethods(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._HttpMethods;
      this._HttpMethods = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String[] getHttpMethodOmissions() {
      return this._HttpMethodOmissions;
   }

   public boolean isHttpMethodOmissionsInherited() {
      return false;
   }

   public boolean isHttpMethodOmissionsSet() {
      return this._isSet(4);
   }

   public void addHttpMethodOmission(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(4)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getHttpMethodOmissions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setHttpMethodOmissions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeHttpMethodOmission(String param0) {
      String[] _old = this.getHttpMethodOmissions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setHttpMethodOmissions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setHttpMethodOmissions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._HttpMethodOmissions;
      this._HttpMethodOmissions = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getWebResourceName();
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
         case 17:
            if (s.equals("web-resource-name")) {
               return info.compareXpaths(this._getPropertyXpath("web-resource-name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._HttpMethodOmissions = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._HttpMethods = new String[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._UrlPatterns = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._WebResourceName = null;
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
                  return 5;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 1;
               }

               if (s.equals("http-method")) {
                  return 3;
               }

               if (s.equals("url-pattern")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("web-resource-name")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("http-method-omission")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "web-resource-name";
            case 1:
               return "description";
            case 2:
               return "url-pattern";
            case 3:
               return "http-method";
            case 4:
               return "http-method-omission";
            case 5:
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
               return true;
            default:
               return super.isArray(propIndex);
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
         indices.add("web-resource-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WebResourceCollectionBeanImpl bean;

      protected Helper(WebResourceCollectionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WebResourceName";
            case 1:
               return "Descriptions";
            case 2:
               return "UrlPatterns";
            case 3:
               return "HttpMethods";
            case 4:
               return "HttpMethodOmissions";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 1;
         } else if (propName.equals("HttpMethodOmissions")) {
            return 4;
         } else if (propName.equals("HttpMethods")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("UrlPatterns")) {
            return 2;
         } else {
            return propName.equals("WebResourceName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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

            if (this.bean.isHttpMethodOmissionsSet()) {
               buf.append("HttpMethodOmissions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHttpMethodOmissions())));
            }

            if (this.bean.isHttpMethodsSet()) {
               buf.append("HttpMethods");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHttpMethods())));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isUrlPatternsSet()) {
               buf.append("UrlPatterns");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUrlPatterns())));
            }

            if (this.bean.isWebResourceNameSet()) {
               buf.append("WebResourceName");
               buf.append(String.valueOf(this.bean.getWebResourceName()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WebResourceCollectionBeanImpl otherTyped = (WebResourceCollectionBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("HttpMethodOmissions", this.bean.getHttpMethodOmissions(), otherTyped.getHttpMethodOmissions(), false);
            this.computeDiff("HttpMethods", this.bean.getHttpMethods(), otherTyped.getHttpMethods(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("UrlPatterns", this.bean.getUrlPatterns(), otherTyped.getUrlPatterns(), false);
            this.computeDiff("WebResourceName", this.bean.getWebResourceName(), otherTyped.getWebResourceName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebResourceCollectionBeanImpl original = (WebResourceCollectionBeanImpl)event.getSourceBean();
            WebResourceCollectionBeanImpl proposed = (WebResourceCollectionBeanImpl)event.getProposedBean();
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
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("HttpMethodOmissions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addHttpMethodOmission((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHttpMethodOmission((String)update.getRemovedObject());
                  }

                  if (original.getHttpMethodOmissions() == null || original.getHttpMethodOmissions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("HttpMethods")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addHttpMethod((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHttpMethod((String)update.getRemovedObject());
                  }

                  if (original.getHttpMethods() == null || original.getHttpMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
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
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("WebResourceName")) {
                  original.setWebResourceName(proposed.getWebResourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            WebResourceCollectionBeanImpl copy = (WebResourceCollectionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("HttpMethodOmissions")) && this.bean.isHttpMethodOmissionsSet()) {
               o = this.bean.getHttpMethodOmissions();
               copy.setHttpMethodOmissions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("HttpMethods")) && this.bean.isHttpMethodsSet()) {
               o = this.bean.getHttpMethods();
               copy.setHttpMethods(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("UrlPatterns")) && this.bean.isUrlPatternsSet()) {
               o = this.bean.getUrlPatterns();
               copy.setUrlPatterns(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WebResourceName")) && this.bean.isWebResourceNameSet()) {
               copy.setWebResourceName(this.bean.getWebResourceName());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
