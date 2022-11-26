package weblogic.management.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ImportMBeanImpl extends AbstractCommoConfigurationBean implements ImportMBean, Serializable {
   private String[] _SupportedImportConstraints;
   private String[] _SupportedImportFormats;
   private static SchemaHelper2 _schemaHelper;

   public ImportMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ImportMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ImportMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getSupportedImportFormats() {
      return this._SupportedImportFormats;
   }

   public boolean isSupportedImportFormatsInherited() {
      return false;
   }

   public boolean isSupportedImportFormatsSet() {
      return this._isSet(2);
   }

   public void setSupportedImportFormats(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._SupportedImportFormats = param0;
   }

   public String[] getSupportedImportConstraints() {
      return this._SupportedImportConstraints;
   }

   public boolean isSupportedImportConstraintsInherited() {
      return false;
   }

   public boolean isSupportedImportConstraintsSet() {
      return this._isSet(3);
   }

   public void setSupportedImportConstraints(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._SupportedImportConstraints = param0;
   }

   public void importData(String param0, String param1, Properties param2) throws InvalidParameterException, ErrorCollectionException {
      throw new AssertionError("Method not implemented");
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
               this._SupportedImportConstraints = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._SupportedImportFormats = new String[0];
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.ImportMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 23:
               if (s.equals("supported-import-format")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("supported-import-constraint")) {
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
            case 2:
               return "supported-import-format";
            case 3:
               return "supported-import-constraint";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private ImportMBeanImpl bean;

      protected Helper(ImportMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "SupportedImportFormats";
            case 3:
               return "SupportedImportConstraints";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("SupportedImportConstraints")) {
            return 3;
         } else {
            return propName.equals("SupportedImportFormats") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isSupportedImportConstraintsSet()) {
               buf.append("SupportedImportConstraints");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSupportedImportConstraints())));
            }

            if (this.bean.isSupportedImportFormatsSet()) {
               buf.append("SupportedImportFormats");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSupportedImportFormats())));
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
            ImportMBeanImpl var2 = (ImportMBeanImpl)other;
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ImportMBeanImpl original = (ImportMBeanImpl)event.getSourceBean();
            ImportMBeanImpl proposed = (ImportMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("SupportedImportConstraints") && !prop.equals("SupportedImportFormats")) {
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
            ImportMBeanImpl copy = (ImportMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
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
