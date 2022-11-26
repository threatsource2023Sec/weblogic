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

public class GzipCompressionBeanImpl extends AbstractDescriptorBean implements GzipCompressionBean, Serializable {
   private String[] _ContentType;
   private boolean _Enabled;
   private long _MinContentLength;
   private static SchemaHelper2 _schemaHelper;

   public GzipCompressionBeanImpl() {
      this._initializeProperty(-1);
   }

   public GzipCompressionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GzipCompressionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(0);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getMinContentLength() {
      return this._MinContentLength;
   }

   public boolean isMinContentLengthInherited() {
      return false;
   }

   public boolean isMinContentLengthSet() {
      return this._isSet(1);
   }

   public void setMinContentLength(long param0) {
      long _oldVal = this._MinContentLength;
      this._MinContentLength = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getContentType() {
      return this._ContentType;
   }

   public boolean isContentTypeInherited() {
      return false;
   }

   public boolean isContentTypeSet() {
      return this._isSet(2);
   }

   public void setContentType(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ContentType;
      this._ContentType = param0;
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
               this._ContentType = new String[]{"text/html", "text/xml", "text/plain"};
               if (initOne) {
                  break;
               }
            case 1:
               this._MinContentLength = 2048L;
               if (initOne) {
                  break;
               }
            case 0:
               this._Enabled = false;
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
            case 7:
               if (s.equals("enabled")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("content-type")) {
                  return 2;
               }
               break;
            case 18:
               if (s.equals("min-content-length")) {
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
               return "enabled";
            case 1:
               return "min-content-length";
            case 2:
               return "content-type";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private GzipCompressionBeanImpl bean;

      protected Helper(GzipCompressionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Enabled";
            case 1:
               return "MinContentLength";
            case 2:
               return "ContentType";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ContentType")) {
            return 2;
         } else if (propName.equals("MinContentLength")) {
            return 1;
         } else {
            return propName.equals("Enabled") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isContentTypeSet()) {
               buf.append("ContentType");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getContentType())));
            }

            if (this.bean.isMinContentLengthSet()) {
               buf.append("MinContentLength");
               buf.append(String.valueOf(this.bean.getMinContentLength()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            GzipCompressionBeanImpl otherTyped = (GzipCompressionBeanImpl)other;
            this.computeDiff("ContentType", this.bean.getContentType(), otherTyped.getContentType(), true);
            this.computeDiff("MinContentLength", this.bean.getMinContentLength(), otherTyped.getMinContentLength(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GzipCompressionBeanImpl original = (GzipCompressionBeanImpl)event.getSourceBean();
            GzipCompressionBeanImpl proposed = (GzipCompressionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ContentType")) {
                  original.setContentType(proposed.getContentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MinContentLength")) {
                  original.setMinContentLength(proposed.getMinContentLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
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
            GzipCompressionBeanImpl copy = (GzipCompressionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ContentType")) && this.bean.isContentTypeSet()) {
               Object o = this.bean.getContentType();
               copy.setContentType(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("MinContentLength")) && this.bean.isMinContentLengthSet()) {
               copy.setMinContentLength(this.bean.getMinContentLength());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
