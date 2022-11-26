package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ShareableBeanImpl extends AbstractDescriptorBean implements ShareableBean, Serializable {
   private String _Dir;
   private String[] _Excludes;
   private String[] _Includes;
   private static SchemaHelper2 _schemaHelper;

   public ShareableBeanImpl() {
      this._initializeProperty(-1);
   }

   public ShareableBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ShareableBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDir() {
      return this._Dir;
   }

   public boolean isDirInherited() {
      return false;
   }

   public boolean isDirSet() {
      return this._isSet(0);
   }

   public void setDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"", "APP-INF-LIB", "LIB-DIR", "APP-INF-CLASSES", "WEB-INF-LIB", "WEB-INF-CLASSES"};
      param0 = LegalChecks.checkInEnum("Dir", param0, _set);
      String _oldVal = this._Dir;
      this._Dir = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getIncludes() {
      return this._Includes;
   }

   public boolean isIncludesInherited() {
      return false;
   }

   public boolean isIncludesSet() {
      return this._isSet(1);
   }

   public void setIncludes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Includes;
      this._Includes = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getExcludes() {
      return this._Excludes;
   }

   public boolean isExcludesInherited() {
      return false;
   }

   public boolean isExcludesSet() {
      return this._isSet(2);
   }

   public void setExcludes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Excludes;
      this._Excludes = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getDir();
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
         case 3:
            if (s.equals("dir")) {
               return info.compareXpaths(this._getPropertyXpath("dir"));
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
               this._Dir = "";
               if (initOne) {
                  break;
               }
            case 2:
               this._Excludes = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Includes = new String[0];
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
            case 3:
               if (s.equals("dir")) {
                  return 0;
               }
               break;
            case 7:
               if (s.equals("exclude")) {
                  return 2;
               }

               if (s.equals("include")) {
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
               return "dir";
            case 1:
               return "include";
            case 2:
               return "exclude";
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
         indices.add("dir");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ShareableBeanImpl bean;

      protected Helper(ShareableBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Dir";
            case 1:
               return "Includes";
            case 2:
               return "Excludes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Dir")) {
            return 0;
         } else if (propName.equals("Excludes")) {
            return 2;
         } else {
            return propName.equals("Includes") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isDirSet()) {
               buf.append("Dir");
               buf.append(String.valueOf(this.bean.getDir()));
            }

            if (this.bean.isExcludesSet()) {
               buf.append("Excludes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExcludes())));
            }

            if (this.bean.isIncludesSet()) {
               buf.append("Includes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIncludes())));
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
            ShareableBeanImpl otherTyped = (ShareableBeanImpl)other;
            this.computeDiff("Dir", this.bean.getDir(), otherTyped.getDir(), false);
            this.computeDiff("Excludes", this.bean.getExcludes(), otherTyped.getExcludes(), false);
            this.computeDiff("Includes", this.bean.getIncludes(), otherTyped.getIncludes(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ShareableBeanImpl original = (ShareableBeanImpl)event.getSourceBean();
            ShareableBeanImpl proposed = (ShareableBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Dir")) {
                  original.setDir(proposed.getDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Excludes")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Includes")) {
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
            ShareableBeanImpl copy = (ShareableBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Dir")) && this.bean.isDirSet()) {
               copy.setDir(this.bean.getDir());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Excludes")) && this.bean.isExcludesSet()) {
               o = this.bean.getExcludes();
               copy.setExcludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Includes")) && this.bean.isIncludesSet()) {
               o = this.bean.getIncludes();
               copy.setIncludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
