package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.PlatformConstants;
import weblogic.utils.collections.CombinedIterator;

public class WLDFImageNotificationBeanImpl extends WLDFNotificationBeanImpl implements WLDFImageNotificationBean, Serializable {
   private String _ImageDirectory;
   private int _ImageLockout;
   private static SchemaHelper2 _schemaHelper;

   public WLDFImageNotificationBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFImageNotificationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFImageNotificationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getImageDirectory() {
      if (!this._isSet(4)) {
         try {
            return "logs" + PlatformConstants.FILE_SEP + "diagnostic_images";
         } catch (NullPointerException var2) {
         }
      }

      return this._ImageDirectory;
   }

   public boolean isImageDirectoryInherited() {
      return false;
   }

   public boolean isImageDirectorySet() {
      return this._isSet(4);
   }

   public void setImageDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ImageDirectory;
      this._ImageDirectory = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getImageLockout() {
      return this._ImageLockout;
   }

   public boolean isImageLockoutInherited() {
      return false;
   }

   public boolean isImageLockoutSet() {
      return this._isSet(5);
   }

   public void setImageLockout(int param0) {
      LegalChecks.checkMin("ImageLockout", param0, 0);
      int _oldVal = this._ImageLockout;
      this._ImageLockout = param0;
      this._postSet(5, _oldVal, param0);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ImageDirectory = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ImageLockout = 0;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("image-lockout")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("image-directory")) {
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
            case 4:
               return "image-directory";
            case 5:
               return "image-lockout";
            default:
               return super.getElementName(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFImageNotificationBeanImpl bean;

      protected Helper(WLDFImageNotificationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "ImageDirectory";
            case 5:
               return "ImageLockout";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ImageDirectory")) {
            return 4;
         } else {
            return propName.equals("ImageLockout") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isImageDirectorySet()) {
               buf.append("ImageDirectory");
               buf.append(String.valueOf(this.bean.getImageDirectory()));
            }

            if (this.bean.isImageLockoutSet()) {
               buf.append("ImageLockout");
               buf.append(String.valueOf(this.bean.getImageLockout()));
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
            WLDFImageNotificationBeanImpl otherTyped = (WLDFImageNotificationBeanImpl)other;
            this.computeDiff("ImageDirectory", this.bean.getImageDirectory(), otherTyped.getImageDirectory(), true);
            this.computeDiff("ImageLockout", this.bean.getImageLockout(), otherTyped.getImageLockout(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFImageNotificationBeanImpl original = (WLDFImageNotificationBeanImpl)event.getSourceBean();
            WLDFImageNotificationBeanImpl proposed = (WLDFImageNotificationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ImageDirectory")) {
                  original.setImageDirectory(proposed.getImageDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ImageLockout")) {
                  original.setImageLockout(proposed.getImageLockout());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            WLDFImageNotificationBeanImpl copy = (WLDFImageNotificationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ImageDirectory")) && this.bean.isImageDirectorySet()) {
               copy.setImageDirectory(this.bean.getImageDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("ImageLockout")) && this.bean.isImageLockoutSet()) {
               copy.setImageLockout(this.bean.getImageLockout());
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
