package weblogic.diagnostics.image.descriptor;

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

public class FailedImageSourceBeanImpl extends AbstractDescriptorBean implements FailedImageSourceBean, Serializable {
   private String _FailureExceptionStackTrace;
   private String _ImageSource;
   private static SchemaHelper2 _schemaHelper;

   public FailedImageSourceBeanImpl() {
      this._initializeProperty(-1);
   }

   public FailedImageSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FailedImageSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getImageSource() {
      return this._ImageSource;
   }

   public boolean isImageSourceInherited() {
      return false;
   }

   public boolean isImageSourceSet() {
      return this._isSet(0);
   }

   public void setImageSource(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ImageSource;
      this._ImageSource = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setFailureExceptionStackTrace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FailureExceptionStackTrace;
      this._FailureExceptionStackTrace = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getFailureExceptionStackTrace() {
      return this._FailureExceptionStackTrace;
   }

   public boolean isFailureExceptionStackTraceInherited() {
      return false;
   }

   public boolean isFailureExceptionStackTraceSet() {
      return this._isSet(1);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._FailureExceptionStackTrace = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ImageSource = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
            case 12:
               if (s.equals("image-source")) {
                  return 0;
               }
               break;
            case 29:
               if (s.equals("failure-exception-stack-trace")) {
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
               return "image-source";
            case 1:
               return "failure-exception-stack-trace";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private FailedImageSourceBeanImpl bean;

      protected Helper(FailedImageSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ImageSource";
            case 1:
               return "FailureExceptionStackTrace";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FailureExceptionStackTrace")) {
            return 1;
         } else {
            return propName.equals("ImageSource") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isFailureExceptionStackTraceSet()) {
               buf.append("FailureExceptionStackTrace");
               buf.append(String.valueOf(this.bean.getFailureExceptionStackTrace()));
            }

            if (this.bean.isImageSourceSet()) {
               buf.append("ImageSource");
               buf.append(String.valueOf(this.bean.getImageSource()));
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
            FailedImageSourceBeanImpl otherTyped = (FailedImageSourceBeanImpl)other;
            this.computeDiff("FailureExceptionStackTrace", this.bean.getFailureExceptionStackTrace(), otherTyped.getFailureExceptionStackTrace(), false);
            this.computeDiff("ImageSource", this.bean.getImageSource(), otherTyped.getImageSource(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FailedImageSourceBeanImpl original = (FailedImageSourceBeanImpl)event.getSourceBean();
            FailedImageSourceBeanImpl proposed = (FailedImageSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FailureExceptionStackTrace")) {
                  original.setFailureExceptionStackTrace(proposed.getFailureExceptionStackTrace());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ImageSource")) {
                  original.setImageSource(proposed.getImageSource());
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
            FailedImageSourceBeanImpl copy = (FailedImageSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FailureExceptionStackTrace")) && this.bean.isFailureExceptionStackTraceSet()) {
               copy.setFailureExceptionStackTrace(this.bean.getFailureExceptionStackTrace());
            }

            if ((excludeProps == null || !excludeProps.contains("ImageSource")) && this.bean.isImageSourceSet()) {
               copy.setImageSource(this.bean.getImageSource());
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
