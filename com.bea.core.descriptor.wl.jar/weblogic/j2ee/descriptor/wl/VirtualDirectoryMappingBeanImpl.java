package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.CompoundKey;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.servlet.internal.WebXmlValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class VirtualDirectoryMappingBeanImpl extends AbstractDescriptorBean implements VirtualDirectoryMappingBean, Serializable {
   private String _Id;
   private String _LocalPath;
   private String[] _UrlPatterns;
   private static SchemaHelper2 _schemaHelper;

   public VirtualDirectoryMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public VirtualDirectoryMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public VirtualDirectoryMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLocalPath() {
      return this._LocalPath;
   }

   public boolean isLocalPathInherited() {
      return false;
   }

   public boolean isLocalPathSet() {
      return this._isSet(0);
   }

   public void setLocalPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalPath;
      this._LocalPath = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getUrlPatterns() {
      return this._UrlPatterns;
   }

   public boolean isUrlPatternsInherited() {
      return false;
   }

   public boolean isUrlPatternsSet() {
      return this._isSet(1);
   }

   public void addUrlPattern(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
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
      this._postSet(1, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return new CompoundKey(this.getUrlPatterns());
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
         case 11:
            if (s.equals("url-pattern")) {
               return info.compareXpaths(this._getPropertyXpath("url-pattern"));
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._LocalPath = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._UrlPatterns = new String[0];
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
                  return 2;
               }
               break;
            case 10:
               if (s.equals("local-path")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("url-pattern")) {
                  return 1;
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
               return "local-path";
            case 1:
               return "url-pattern";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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
         indices.add("url-pattern");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private VirtualDirectoryMappingBeanImpl bean;

      protected Helper(VirtualDirectoryMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LocalPath";
            case 1:
               return "UrlPatterns";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("LocalPath")) {
            return 0;
         } else {
            return propName.equals("UrlPatterns") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isLocalPathSet()) {
               buf.append("LocalPath");
               buf.append(String.valueOf(this.bean.getLocalPath()));
            }

            if (this.bean.isUrlPatternsSet()) {
               buf.append("UrlPatterns");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUrlPatterns())));
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
            VirtualDirectoryMappingBeanImpl otherTyped = (VirtualDirectoryMappingBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("LocalPath", this.bean.getLocalPath(), otherTyped.getLocalPath(), false);
            this.computeDiff("UrlPatterns", this.bean.getUrlPatterns(), otherTyped.getUrlPatterns(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            VirtualDirectoryMappingBeanImpl original = (VirtualDirectoryMappingBeanImpl)event.getSourceBean();
            VirtualDirectoryMappingBeanImpl proposed = (VirtualDirectoryMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("LocalPath")) {
                  original.setLocalPath(proposed.getLocalPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            VirtualDirectoryMappingBeanImpl copy = (VirtualDirectoryMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalPath")) && this.bean.isLocalPathSet()) {
               copy.setLocalPath(this.bean.getLocalPath());
            }

            if ((excludeProps == null || !excludeProps.contains("UrlPatterns")) && this.bean.isUrlPatternsSet()) {
               Object o = this.bean.getUrlPatterns();
               copy.setUrlPatterns(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
