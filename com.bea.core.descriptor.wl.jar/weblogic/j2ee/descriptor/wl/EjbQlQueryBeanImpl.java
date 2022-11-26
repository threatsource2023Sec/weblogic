package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
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
import weblogic.utils.collections.CombinedIterator;

public class EjbQlQueryBeanImpl extends AbstractDescriptorBean implements EjbQlQueryBean, Serializable {
   private String _CachingName;
   private String _GroupName;
   private String _WeblogicQl;
   private static SchemaHelper2 _schemaHelper;

   public EjbQlQueryBeanImpl() {
      this._initializeProperty(-1);
   }

   public EjbQlQueryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EjbQlQueryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWeblogicQl() {
      return this._WeblogicQl;
   }

   public boolean isWeblogicQlInherited() {
      return false;
   }

   public boolean isWeblogicQlSet() {
      return this._isSet(0);
   }

   public void setWeblogicQl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WeblogicQl;
      this._WeblogicQl = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getGroupName() {
      return this._GroupName;
   }

   public boolean isGroupNameInherited() {
      return false;
   }

   public boolean isGroupNameSet() {
      return this._isSet(1);
   }

   public void setGroupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GroupName;
      this._GroupName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getCachingName() {
      return this._CachingName;
   }

   public boolean isCachingNameInherited() {
      return false;
   }

   public boolean isCachingNameSet() {
      return this._isSet(2);
   }

   public void setCachingName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CachingName;
      this._CachingName = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._CachingName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._GroupName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._WeblogicQl = null;
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
            case 10:
               if (s.equals("group-name")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("weblogic-ql")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("caching-name")) {
                  return 2;
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
               return "weblogic-ql";
            case 1:
               return "group-name";
            case 2:
               return "caching-name";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EjbQlQueryBeanImpl bean;

      protected Helper(EjbQlQueryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WeblogicQl";
            case 1:
               return "GroupName";
            case 2:
               return "CachingName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CachingName")) {
            return 2;
         } else if (propName.equals("GroupName")) {
            return 1;
         } else {
            return propName.equals("WeblogicQl") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isCachingNameSet()) {
               buf.append("CachingName");
               buf.append(String.valueOf(this.bean.getCachingName()));
            }

            if (this.bean.isGroupNameSet()) {
               buf.append("GroupName");
               buf.append(String.valueOf(this.bean.getGroupName()));
            }

            if (this.bean.isWeblogicQlSet()) {
               buf.append("WeblogicQl");
               buf.append(String.valueOf(this.bean.getWeblogicQl()));
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
            EjbQlQueryBeanImpl otherTyped = (EjbQlQueryBeanImpl)other;
            this.computeDiff("CachingName", this.bean.getCachingName(), otherTyped.getCachingName(), false);
            this.computeDiff("GroupName", this.bean.getGroupName(), otherTyped.getGroupName(), false);
            this.computeDiff("WeblogicQl", this.bean.getWeblogicQl(), otherTyped.getWeblogicQl(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EjbQlQueryBeanImpl original = (EjbQlQueryBeanImpl)event.getSourceBean();
            EjbQlQueryBeanImpl proposed = (EjbQlQueryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CachingName")) {
                  original.setCachingName(proposed.getCachingName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("GroupName")) {
                  original.setGroupName(proposed.getGroupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WeblogicQl")) {
                  original.setWeblogicQl(proposed.getWeblogicQl());
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
            EjbQlQueryBeanImpl copy = (EjbQlQueryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CachingName")) && this.bean.isCachingNameSet()) {
               copy.setCachingName(this.bean.getCachingName());
            }

            if ((excludeProps == null || !excludeProps.contains("GroupName")) && this.bean.isGroupNameSet()) {
               copy.setGroupName(this.bean.getGroupName());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicQl")) && this.bean.isWeblogicQlSet()) {
               copy.setWeblogicQl(this.bean.getWeblogicQl());
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
