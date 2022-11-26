package weblogic.j2ee.descriptor;

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

public class MultipartConfigBeanImpl extends AbstractDescriptorBean implements MultipartConfigBean, Serializable {
   private int _FileSizeThreshold;
   private String _Location;
   private long _MaxFileSize;
   private long _MaxRequestSize;
   private static SchemaHelper2 _schemaHelper;

   public MultipartConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public MultipartConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MultipartConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLocation() {
      return this._Location;
   }

   public boolean isLocationInherited() {
      return false;
   }

   public boolean isLocationSet() {
      return this._isSet(0);
   }

   public void setLocation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Location;
      this._Location = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getMaxFileSize() {
      return this._MaxFileSize;
   }

   public boolean isMaxFileSizeInherited() {
      return false;
   }

   public boolean isMaxFileSizeSet() {
      return this._isSet(1);
   }

   public void setMaxFileSize(long param0) {
      long _oldVal = this._MaxFileSize;
      this._MaxFileSize = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getMaxRequestSize() {
      return this._MaxRequestSize;
   }

   public boolean isMaxRequestSizeInherited() {
      return false;
   }

   public boolean isMaxRequestSizeSet() {
      return this._isSet(2);
   }

   public void setMaxRequestSize(long param0) {
      long _oldVal = this._MaxRequestSize;
      this._MaxRequestSize = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getFileSizeThreshold() {
      return this._FileSizeThreshold;
   }

   public boolean isFileSizeThresholdInherited() {
      return false;
   }

   public boolean isFileSizeThresholdSet() {
      return this._isSet(3);
   }

   public void setFileSizeThreshold(int param0) {
      int _oldVal = this._FileSizeThreshold;
      this._FileSizeThreshold = param0;
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
               this._FileSizeThreshold = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._Location = "";
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxFileSize = -1L;
               if (initOne) {
                  break;
               }
            case 2:
               this._MaxRequestSize = -1L;
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
            case 8:
               if (s.equals("location")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("max-file-size")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("max-request-size")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("file-size-threshold")) {
                  return 3;
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
               return "location";
            case 1:
               return "max-file-size";
            case 2:
               return "max-request-size";
            case 3:
               return "file-size-threshold";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MultipartConfigBeanImpl bean;

      protected Helper(MultipartConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Location";
            case 1:
               return "MaxFileSize";
            case 2:
               return "MaxRequestSize";
            case 3:
               return "FileSizeThreshold";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FileSizeThreshold")) {
            return 3;
         } else if (propName.equals("Location")) {
            return 0;
         } else if (propName.equals("MaxFileSize")) {
            return 1;
         } else {
            return propName.equals("MaxRequestSize") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isFileSizeThresholdSet()) {
               buf.append("FileSizeThreshold");
               buf.append(String.valueOf(this.bean.getFileSizeThreshold()));
            }

            if (this.bean.isLocationSet()) {
               buf.append("Location");
               buf.append(String.valueOf(this.bean.getLocation()));
            }

            if (this.bean.isMaxFileSizeSet()) {
               buf.append("MaxFileSize");
               buf.append(String.valueOf(this.bean.getMaxFileSize()));
            }

            if (this.bean.isMaxRequestSizeSet()) {
               buf.append("MaxRequestSize");
               buf.append(String.valueOf(this.bean.getMaxRequestSize()));
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
            MultipartConfigBeanImpl otherTyped = (MultipartConfigBeanImpl)other;
            this.computeDiff("FileSizeThreshold", this.bean.getFileSizeThreshold(), otherTyped.getFileSizeThreshold(), false);
            this.computeDiff("Location", this.bean.getLocation(), otherTyped.getLocation(), false);
            this.computeDiff("MaxFileSize", this.bean.getMaxFileSize(), otherTyped.getMaxFileSize(), false);
            this.computeDiff("MaxRequestSize", this.bean.getMaxRequestSize(), otherTyped.getMaxRequestSize(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MultipartConfigBeanImpl original = (MultipartConfigBeanImpl)event.getSourceBean();
            MultipartConfigBeanImpl proposed = (MultipartConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FileSizeThreshold")) {
                  original.setFileSizeThreshold(proposed.getFileSizeThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Location")) {
                  original.setLocation(proposed.getLocation());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MaxFileSize")) {
                  original.setMaxFileSize(proposed.getMaxFileSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MaxRequestSize")) {
                  original.setMaxRequestSize(proposed.getMaxRequestSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            MultipartConfigBeanImpl copy = (MultipartConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FileSizeThreshold")) && this.bean.isFileSizeThresholdSet()) {
               copy.setFileSizeThreshold(this.bean.getFileSizeThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("Location")) && this.bean.isLocationSet()) {
               copy.setLocation(this.bean.getLocation());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxFileSize")) && this.bean.isMaxFileSizeSet()) {
               copy.setMaxFileSize(this.bean.getMaxFileSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRequestSize")) && this.bean.isMaxRequestSizeSet()) {
               copy.setMaxRequestSize(this.bean.getMaxRequestSize());
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
