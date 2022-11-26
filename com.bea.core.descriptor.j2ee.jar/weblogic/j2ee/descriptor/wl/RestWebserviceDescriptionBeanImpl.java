package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class RestWebserviceDescriptionBeanImpl extends AbstractDescriptorBean implements RestWebserviceDescriptionBean, Serializable {
   private String[] _ApplicationBaseUris;
   private String _ApplicationClassName;
   private String _FilterName;
   private String _ServletName;
   private static SchemaHelper2 _schemaHelper;

   public RestWebserviceDescriptionBeanImpl() {
      this._initializeProperty(-1);
   }

   public RestWebserviceDescriptionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RestWebserviceDescriptionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getApplicationClassName() {
      return this._ApplicationClassName;
   }

   public boolean isApplicationClassNameInherited() {
      return false;
   }

   public boolean isApplicationClassNameSet() {
      return this._isSet(0);
   }

   public void setApplicationClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationClassName;
      this._ApplicationClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getServletName() {
      return this._ServletName;
   }

   public boolean isServletNameInherited() {
      return false;
   }

   public boolean isServletNameSet() {
      return this._isSet(1);
   }

   public void setServletName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServletName;
      this._ServletName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getFilterName() {
      return this._FilterName;
   }

   public boolean isFilterNameInherited() {
      return false;
   }

   public boolean isFilterNameSet() {
      return this._isSet(2);
   }

   public void setFilterName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FilterName;
      this._FilterName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getApplicationBaseUris() {
      return this._ApplicationBaseUris;
   }

   public boolean isApplicationBaseUrisInherited() {
      return false;
   }

   public boolean isApplicationBaseUrisSet() {
      return this._isSet(3);
   }

   public void setApplicationBaseUris(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ApplicationBaseUris;
      this._ApplicationBaseUris = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._ApplicationBaseUris = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ApplicationClassName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._FilterName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ServletName = null;
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
            case 11:
               if (s.equals("filter-name")) {
                  return 2;
               }
               break;
            case 12:
               if (s.equals("servlet-name")) {
                  return 1;
               }
               break;
            case 20:
               if (s.equals("application-base-uri")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("application-class-name")) {
                  return 0;
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
               return "application-class-name";
            case 1:
               return "servlet-name";
            case 2:
               return "filter-name";
            case 3:
               return "application-base-uri";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private RestWebserviceDescriptionBeanImpl bean;

      protected Helper(RestWebserviceDescriptionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ApplicationClassName";
            case 1:
               return "ServletName";
            case 2:
               return "FilterName";
            case 3:
               return "ApplicationBaseUris";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationBaseUris")) {
            return 3;
         } else if (propName.equals("ApplicationClassName")) {
            return 0;
         } else if (propName.equals("FilterName")) {
            return 2;
         } else {
            return propName.equals("ServletName") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isApplicationBaseUrisSet()) {
               buf.append("ApplicationBaseUris");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getApplicationBaseUris())));
            }

            if (this.bean.isApplicationClassNameSet()) {
               buf.append("ApplicationClassName");
               buf.append(String.valueOf(this.bean.getApplicationClassName()));
            }

            if (this.bean.isFilterNameSet()) {
               buf.append("FilterName");
               buf.append(String.valueOf(this.bean.getFilterName()));
            }

            if (this.bean.isServletNameSet()) {
               buf.append("ServletName");
               buf.append(String.valueOf(this.bean.getServletName()));
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
            RestWebserviceDescriptionBeanImpl otherTyped = (RestWebserviceDescriptionBeanImpl)other;
            this.computeDiff("ApplicationBaseUris", this.bean.getApplicationBaseUris(), otherTyped.getApplicationBaseUris(), false);
            this.computeDiff("ApplicationClassName", this.bean.getApplicationClassName(), otherTyped.getApplicationClassName(), false);
            this.computeDiff("FilterName", this.bean.getFilterName(), otherTyped.getFilterName(), false);
            this.computeDiff("ServletName", this.bean.getServletName(), otherTyped.getServletName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RestWebserviceDescriptionBeanImpl original = (RestWebserviceDescriptionBeanImpl)event.getSourceBean();
            RestWebserviceDescriptionBeanImpl proposed = (RestWebserviceDescriptionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationBaseUris")) {
                  original.setApplicationBaseUris(proposed.getApplicationBaseUris());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ApplicationClassName")) {
                  original.setApplicationClassName(proposed.getApplicationClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("FilterName")) {
                  original.setFilterName(proposed.getFilterName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ServletName")) {
                  original.setServletName(proposed.getServletName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            RestWebserviceDescriptionBeanImpl copy = (RestWebserviceDescriptionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicationBaseUris")) && this.bean.isApplicationBaseUrisSet()) {
               Object o = this.bean.getApplicationBaseUris();
               copy.setApplicationBaseUris(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationClassName")) && this.bean.isApplicationClassNameSet()) {
               copy.setApplicationClassName(this.bean.getApplicationClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("FilterName")) && this.bean.isFilterNameSet()) {
               copy.setFilterName(this.bean.getFilterName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletName")) && this.bean.isServletNameSet()) {
               copy.setServletName(this.bean.getServletName());
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
