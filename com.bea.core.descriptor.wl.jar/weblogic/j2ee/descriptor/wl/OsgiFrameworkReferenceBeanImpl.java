package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class OsgiFrameworkReferenceBeanImpl extends AbstractDescriptorBean implements OsgiFrameworkReferenceBean, Serializable {
   private String _ApplicationBundleSymbolicName;
   private String _ApplicationBundleVersion;
   private String _BundlesDirectory;
   private String _Name;
   private static SchemaHelper2 _schemaHelper;

   public OsgiFrameworkReferenceBeanImpl() {
      this._initializeProperty(-1);
   }

   public OsgiFrameworkReferenceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OsgiFrameworkReferenceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getBundlesDirectory() {
      return this._BundlesDirectory;
   }

   public boolean isBundlesDirectoryInherited() {
      return false;
   }

   public boolean isBundlesDirectorySet() {
      return this._isSet(1);
   }

   public void setBundlesDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("BundlesDirectory", param0);
      String _oldVal = this._BundlesDirectory;
      this._BundlesDirectory = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getApplicationBundleSymbolicName() {
      return this._ApplicationBundleSymbolicName;
   }

   public boolean isApplicationBundleSymbolicNameInherited() {
      return false;
   }

   public boolean isApplicationBundleSymbolicNameSet() {
      return this._isSet(2);
   }

   public void setApplicationBundleSymbolicName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationBundleSymbolicName;
      this._ApplicationBundleSymbolicName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getApplicationBundleVersion() {
      return this._ApplicationBundleVersion;
   }

   public boolean isApplicationBundleVersionInherited() {
      return false;
   }

   public boolean isApplicationBundleVersionSet() {
      return this._isSet(3);
   }

   public void setApplicationBundleVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationBundleVersion;
      this._ApplicationBundleVersion = param0;
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ApplicationBundleSymbolicName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ApplicationBundleVersion = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._BundlesDirectory = "osgi-lib";
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
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

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("BundlesDirectory", "osgi-lib");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property BundlesDirectory in OsgiFrameworkReferenceBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("bundles-directory")) {
                  return 1;
               }
               break;
            case 26:
               if (s.equals("application-bundle-version")) {
                  return 3;
               }
               break;
            case 32:
               if (s.equals("application-bundle-symbolic-name")) {
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
               return "name";
            case 1:
               return "bundles-directory";
            case 2:
               return "application-bundle-symbolic-name";
            case 3:
               return "application-bundle-version";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private OsgiFrameworkReferenceBeanImpl bean;

      protected Helper(OsgiFrameworkReferenceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "BundlesDirectory";
            case 2:
               return "ApplicationBundleSymbolicName";
            case 3:
               return "ApplicationBundleVersion";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationBundleSymbolicName")) {
            return 2;
         } else if (propName.equals("ApplicationBundleVersion")) {
            return 3;
         } else if (propName.equals("BundlesDirectory")) {
            return 1;
         } else {
            return propName.equals("Name") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isApplicationBundleSymbolicNameSet()) {
               buf.append("ApplicationBundleSymbolicName");
               buf.append(String.valueOf(this.bean.getApplicationBundleSymbolicName()));
            }

            if (this.bean.isApplicationBundleVersionSet()) {
               buf.append("ApplicationBundleVersion");
               buf.append(String.valueOf(this.bean.getApplicationBundleVersion()));
            }

            if (this.bean.isBundlesDirectorySet()) {
               buf.append("BundlesDirectory");
               buf.append(String.valueOf(this.bean.getBundlesDirectory()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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
            OsgiFrameworkReferenceBeanImpl otherTyped = (OsgiFrameworkReferenceBeanImpl)other;
            this.computeDiff("ApplicationBundleSymbolicName", this.bean.getApplicationBundleSymbolicName(), otherTyped.getApplicationBundleSymbolicName(), false);
            this.computeDiff("ApplicationBundleVersion", this.bean.getApplicationBundleVersion(), otherTyped.getApplicationBundleVersion(), false);
            this.computeDiff("BundlesDirectory", this.bean.getBundlesDirectory(), otherTyped.getBundlesDirectory(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OsgiFrameworkReferenceBeanImpl original = (OsgiFrameworkReferenceBeanImpl)event.getSourceBean();
            OsgiFrameworkReferenceBeanImpl proposed = (OsgiFrameworkReferenceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationBundleSymbolicName")) {
                  original.setApplicationBundleSymbolicName(proposed.getApplicationBundleSymbolicName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ApplicationBundleVersion")) {
                  original.setApplicationBundleVersion(proposed.getApplicationBundleVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BundlesDirectory")) {
                  original.setBundlesDirectory(proposed.getBundlesDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
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
            OsgiFrameworkReferenceBeanImpl copy = (OsgiFrameworkReferenceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicationBundleSymbolicName")) && this.bean.isApplicationBundleSymbolicNameSet()) {
               copy.setApplicationBundleSymbolicName(this.bean.getApplicationBundleSymbolicName());
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationBundleVersion")) && this.bean.isApplicationBundleVersionSet()) {
               copy.setApplicationBundleVersion(this.bean.getApplicationBundleVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("BundlesDirectory")) && this.bean.isBundlesDirectorySet()) {
               copy.setBundlesDirectory(this.bean.getBundlesDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
