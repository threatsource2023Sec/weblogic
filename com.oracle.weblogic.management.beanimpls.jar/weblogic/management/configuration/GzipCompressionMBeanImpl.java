package weblogic.management.configuration;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class GzipCompressionMBeanImpl extends ConfigurationMBeanImpl implements GzipCompressionMBean, Serializable {
   private String[] _GzipCompressionContentType;
   private boolean _GzipCompressionEnabled;
   private long _GzipCompressionMinContentLength;
   private static SchemaHelper2 _schemaHelper;

   public GzipCompressionMBeanImpl() {
      this._initializeProperty(-1);
   }

   public GzipCompressionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GzipCompressionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isGzipCompressionEnabled() {
      return this._GzipCompressionEnabled;
   }

   public boolean isGzipCompressionEnabledInherited() {
      return false;
   }

   public boolean isGzipCompressionEnabledSet() {
      return this._isSet(10);
   }

   public void setGzipCompressionEnabled(boolean param0) {
      boolean _oldVal = this._GzipCompressionEnabled;
      this._GzipCompressionEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getGzipCompressionMinContentLength() {
      return this._GzipCompressionMinContentLength;
   }

   public boolean isGzipCompressionMinContentLengthInherited() {
      return false;
   }

   public boolean isGzipCompressionMinContentLengthSet() {
      return this._isSet(11);
   }

   public void setGzipCompressionMinContentLength(long param0) {
      long _oldVal = this._GzipCompressionMinContentLength;
      this._GzipCompressionMinContentLength = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String[] getGzipCompressionContentType() {
      return this._GzipCompressionContentType;
   }

   public boolean isGzipCompressionContentTypeInherited() {
      return false;
   }

   public boolean isGzipCompressionContentTypeSet() {
      return this._isSet(12);
   }

   public void setGzipCompressionContentType(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._GzipCompressionContentType;
      this._GzipCompressionContentType = param0;
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._GzipCompressionContentType = new String[]{"text/html", "text/xml", "text/plain"};
               if (initOne) {
                  break;
               }
            case 11:
               this._GzipCompressionMinContentLength = 2048L;
               if (initOne) {
                  break;
               }
            case 10:
               this._GzipCompressionEnabled = false;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "GzipCompression";
   }

   public void putValue(String name, Object v) {
      if (name.equals("GzipCompressionContentType")) {
         String[] oldVal = this._GzipCompressionContentType;
         this._GzipCompressionContentType = (String[])((String[])v);
         this._postSet(12, oldVal, this._GzipCompressionContentType);
      } else if (name.equals("GzipCompressionEnabled")) {
         boolean oldVal = this._GzipCompressionEnabled;
         this._GzipCompressionEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._GzipCompressionEnabled);
      } else if (name.equals("GzipCompressionMinContentLength")) {
         long oldVal = this._GzipCompressionMinContentLength;
         this._GzipCompressionMinContentLength = (Long)v;
         this._postSet(11, oldVal, this._GzipCompressionMinContentLength);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("GzipCompressionContentType")) {
         return this._GzipCompressionContentType;
      } else if (name.equals("GzipCompressionEnabled")) {
         return new Boolean(this._GzipCompressionEnabled);
      } else {
         return name.equals("GzipCompressionMinContentLength") ? new Long(this._GzipCompressionMinContentLength) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 24:
               if (s.equals("gzip-compression-enabled")) {
                  return 10;
               }
               break;
            case 29:
               if (s.equals("gzip-compression-content-type")) {
                  return 12;
               }
               break;
            case 35:
               if (s.equals("gzip-compression-min-content-length")) {
                  return 11;
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
            case 10:
               return "gzip-compression-enabled";
            case 11:
               return "gzip-compression-min-content-length";
            case 12:
               return "gzip-compression-content-type";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private GzipCompressionMBeanImpl bean;

      protected Helper(GzipCompressionMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "GzipCompressionEnabled";
            case 11:
               return "GzipCompressionMinContentLength";
            case 12:
               return "GzipCompressionContentType";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("GzipCompressionContentType")) {
            return 12;
         } else if (propName.equals("GzipCompressionMinContentLength")) {
            return 11;
         } else {
            return propName.equals("GzipCompressionEnabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isGzipCompressionContentTypeSet()) {
               buf.append("GzipCompressionContentType");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getGzipCompressionContentType())));
            }

            if (this.bean.isGzipCompressionMinContentLengthSet()) {
               buf.append("GzipCompressionMinContentLength");
               buf.append(String.valueOf(this.bean.getGzipCompressionMinContentLength()));
            }

            if (this.bean.isGzipCompressionEnabledSet()) {
               buf.append("GzipCompressionEnabled");
               buf.append(String.valueOf(this.bean.isGzipCompressionEnabled()));
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
            GzipCompressionMBeanImpl otherTyped = (GzipCompressionMBeanImpl)other;
            this.computeDiff("GzipCompressionContentType", this.bean.getGzipCompressionContentType(), otherTyped.getGzipCompressionContentType(), true);
            this.computeDiff("GzipCompressionMinContentLength", this.bean.getGzipCompressionMinContentLength(), otherTyped.getGzipCompressionMinContentLength(), true);
            this.computeDiff("GzipCompressionEnabled", this.bean.isGzipCompressionEnabled(), otherTyped.isGzipCompressionEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GzipCompressionMBeanImpl original = (GzipCompressionMBeanImpl)event.getSourceBean();
            GzipCompressionMBeanImpl proposed = (GzipCompressionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("GzipCompressionContentType")) {
                  original.setGzipCompressionContentType(proposed.getGzipCompressionContentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("GzipCompressionMinContentLength")) {
                  original.setGzipCompressionMinContentLength(proposed.getGzipCompressionMinContentLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("GzipCompressionEnabled")) {
                  original.setGzipCompressionEnabled(proposed.isGzipCompressionEnabled());
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
            GzipCompressionMBeanImpl copy = (GzipCompressionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("GzipCompressionContentType")) && this.bean.isGzipCompressionContentTypeSet()) {
               Object o = this.bean.getGzipCompressionContentType();
               copy.setGzipCompressionContentType(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("GzipCompressionMinContentLength")) && this.bean.isGzipCompressionMinContentLengthSet()) {
               copy.setGzipCompressionMinContentLength(this.bean.getGzipCompressionMinContentLength());
            }

            if ((excludeProps == null || !excludeProps.contains("GzipCompressionEnabled")) && this.bean.isGzipCompressionEnabledSet()) {
               copy.setGzipCompressionEnabled(this.bean.isGzipCompressionEnabled());
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
