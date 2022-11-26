package weblogic.j2ee.descriptor;

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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PersistenceUnitBeanImpl extends AbstractDescriptorBean implements PersistenceUnitBean, Serializable {
   private String[] _Classes;
   private String _Description;
   private boolean _ExcludeUnlistedClasses;
   private String[] _JarFiles;
   private String _JtaDataSource;
   private String[] _MappingFiles;
   private String _Name;
   private String _NonJtaDataSource;
   private PersistencePropertiesBean _Properties;
   private String _Provider;
   private String _SharedCacheMode;
   private String _TransactionType;
   private String _ValidationMode;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceUnitBeanImpl() {
      this._initializeProperty(-1);
   }

   public PersistenceUnitBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PersistenceUnitBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(1);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getProvider() {
      return this._Provider;
   }

   public boolean isProviderInherited() {
      return false;
   }

   public boolean isProviderSet() {
      return this._isSet(2);
   }

   public void setProvider(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Provider;
      this._Provider = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getJtaDataSource() {
      return this._JtaDataSource;
   }

   public boolean isJtaDataSourceInherited() {
      return false;
   }

   public boolean isJtaDataSourceSet() {
      return this._isSet(3);
   }

   public void setJtaDataSource(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JtaDataSource;
      this._JtaDataSource = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getNonJtaDataSource() {
      return this._NonJtaDataSource;
   }

   public boolean isNonJtaDataSourceInherited() {
      return false;
   }

   public boolean isNonJtaDataSourceSet() {
      return this._isSet(4);
   }

   public void setNonJtaDataSource(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NonJtaDataSource;
      this._NonJtaDataSource = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String[] getMappingFiles() {
      return this._MappingFiles;
   }

   public boolean isMappingFilesInherited() {
      return false;
   }

   public boolean isMappingFilesSet() {
      return this._isSet(5);
   }

   public void setMappingFiles(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._MappingFiles;
      this._MappingFiles = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String[] getJarFiles() {
      return this._JarFiles;
   }

   public boolean isJarFilesInherited() {
      return false;
   }

   public boolean isJarFilesSet() {
      return this._isSet(6);
   }

   public void setJarFiles(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._JarFiles;
      this._JarFiles = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String[] getClasses() {
      return this._Classes;
   }

   public boolean isClassesInherited() {
      return false;
   }

   public boolean isClassesSet() {
      return this._isSet(7);
   }

   public void setClasses(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Classes;
      this._Classes = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean getExcludeUnlistedClasses() {
      return this._ExcludeUnlistedClasses;
   }

   public boolean isExcludeUnlistedClassesInherited() {
      return false;
   }

   public boolean isExcludeUnlistedClassesSet() {
      return this._isSet(8);
   }

   public void setExcludeUnlistedClasses(boolean param0) {
      boolean _oldVal = this._ExcludeUnlistedClasses;
      this._ExcludeUnlistedClasses = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getSharedCacheMode() {
      return this._SharedCacheMode;
   }

   public boolean isSharedCacheModeInherited() {
      return false;
   }

   public boolean isSharedCacheModeSet() {
      return this._isSet(9);
   }

   public void setSharedCacheMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ALL", "NONE", "ENABLE_SELECTIVE", "DISABLE_SELECTIVE", "UNSPECIFIED"};
      param0 = LegalChecks.checkInEnum("SharedCacheMode", param0, _set);
      String _oldVal = this._SharedCacheMode;
      this._SharedCacheMode = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getValidationMode() {
      return this._ValidationMode;
   }

   public boolean isValidationModeInherited() {
      return false;
   }

   public boolean isValidationModeSet() {
      return this._isSet(10);
   }

   public void setValidationMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"AUTO", "CALLBACK", "NONE"};
      param0 = LegalChecks.checkInEnum("ValidationMode", param0, _set);
      String _oldVal = this._ValidationMode;
      this._ValidationMode = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getTransactionType() {
      return this._TransactionType;
   }

   public boolean isTransactionTypeInherited() {
      return false;
   }

   public boolean isTransactionTypeSet() {
      return this._isSet(11);
   }

   public void setTransactionType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"JTA", "RESOURCE_LOCAL"};
      param0 = LegalChecks.checkInEnum("TransactionType", param0, _set);
      String _oldVal = this._TransactionType;
      this._TransactionType = param0;
      this._postSet(11, _oldVal, param0);
   }

   public PersistencePropertiesBean getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(12) || this._isAnythingSet((AbstractDescriptorBean)this.getProperties());
   }

   public void setProperties(PersistencePropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 12)) {
         this._postCreate(_child);
      }

      PersistencePropertiesBean _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(12, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
      return super._isAnythingSet() || this.isPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 7;
      }

      try {
         switch (idx) {
            case 7:
               this._Classes = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._ExcludeUnlistedClasses = true;
               if (initOne) {
                  break;
               }
            case 6:
               this._JarFiles = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._JtaDataSource = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._MappingFiles = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._NonJtaDataSource = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Properties = new PersistencePropertiesBeanImpl(this, 12);
               this._postCreate((AbstractDescriptorBean)this._Properties);
               if (initOne) {
                  break;
               }
            case 2:
               this._Provider = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._SharedCacheMode = "UNSPECIFIED";
               if (initOne) {
                  break;
               }
            case 11:
               this._TransactionType = "JTA";
               if (initOne) {
                  break;
               }
            case 10:
               this._ValidationMode = "AUTO";
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
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 5:
               if (s.equals("class")) {
                  return 7;
               }
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 8:
               if (s.equals("jar-file")) {
                  return 6;
               }

               if (s.equals("provider")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("properties")) {
                  return 12;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("mapping-file")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("jta-data-source")) {
                  return 3;
               }

               if (s.equals("validation-mode")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("transaction-type")) {
                  return 11;
               }
               break;
            case 17:
               if (s.equals("shared-cache-mode")) {
                  return 9;
               }
               break;
            case 19:
               if (s.equals("non-jta-data-source")) {
                  return 4;
               }
               break;
            case 24:
               if (s.equals("exclude-unlisted-classes")) {
                  return 8;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new PersistencePropertiesBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "description";
            case 2:
               return "provider";
            case 3:
               return "jta-data-source";
            case 4:
               return "non-jta-data-source";
            case 5:
               return "mapping-file";
            case 6:
               return "jar-file";
            case 7:
               return "class";
            case 8:
               return "exclude-unlisted-classes";
            case 9:
               return "shared-cache-mode";
            case 10:
               return "validation-mode";
            case 11:
               return "transaction-type";
            case 12:
               return "properties";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 11:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PersistenceUnitBeanImpl bean;

      protected Helper(PersistenceUnitBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "Description";
            case 2:
               return "Provider";
            case 3:
               return "JtaDataSource";
            case 4:
               return "NonJtaDataSource";
            case 5:
               return "MappingFiles";
            case 6:
               return "JarFiles";
            case 7:
               return "Classes";
            case 8:
               return "ExcludeUnlistedClasses";
            case 9:
               return "SharedCacheMode";
            case 10:
               return "ValidationMode";
            case 11:
               return "TransactionType";
            case 12:
               return "Properties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Classes")) {
            return 7;
         } else if (propName.equals("Description")) {
            return 1;
         } else if (propName.equals("ExcludeUnlistedClasses")) {
            return 8;
         } else if (propName.equals("JarFiles")) {
            return 6;
         } else if (propName.equals("JtaDataSource")) {
            return 3;
         } else if (propName.equals("MappingFiles")) {
            return 5;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("NonJtaDataSource")) {
            return 4;
         } else if (propName.equals("Properties")) {
            return 12;
         } else if (propName.equals("Provider")) {
            return 2;
         } else if (propName.equals("SharedCacheMode")) {
            return 9;
         } else if (propName.equals("TransactionType")) {
            return 11;
         } else {
            return propName.equals("ValidationMode") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getProperties() != null) {
            iterators.add(new ArrayIterator(new PersistencePropertiesBean[]{this.bean.getProperties()}));
         }

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
            if (this.bean.isClassesSet()) {
               buf.append("Classes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getClasses())));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isExcludeUnlistedClassesSet()) {
               buf.append("ExcludeUnlistedClasses");
               buf.append(String.valueOf(this.bean.getExcludeUnlistedClasses()));
            }

            if (this.bean.isJarFilesSet()) {
               buf.append("JarFiles");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJarFiles())));
            }

            if (this.bean.isJtaDataSourceSet()) {
               buf.append("JtaDataSource");
               buf.append(String.valueOf(this.bean.getJtaDataSource()));
            }

            if (this.bean.isMappingFilesSet()) {
               buf.append("MappingFiles");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMappingFiles())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNonJtaDataSourceSet()) {
               buf.append("NonJtaDataSource");
               buf.append(String.valueOf(this.bean.getNonJtaDataSource()));
            }

            childValue = this.computeChildHashValue(this.bean.getProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isProviderSet()) {
               buf.append("Provider");
               buf.append(String.valueOf(this.bean.getProvider()));
            }

            if (this.bean.isSharedCacheModeSet()) {
               buf.append("SharedCacheMode");
               buf.append(String.valueOf(this.bean.getSharedCacheMode()));
            }

            if (this.bean.isTransactionTypeSet()) {
               buf.append("TransactionType");
               buf.append(String.valueOf(this.bean.getTransactionType()));
            }

            if (this.bean.isValidationModeSet()) {
               buf.append("ValidationMode");
               buf.append(String.valueOf(this.bean.getValidationMode()));
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
            PersistenceUnitBeanImpl otherTyped = (PersistenceUnitBeanImpl)other;
            this.computeDiff("Classes", this.bean.getClasses(), otherTyped.getClasses(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("ExcludeUnlistedClasses", this.bean.getExcludeUnlistedClasses(), otherTyped.getExcludeUnlistedClasses(), false);
            this.computeDiff("JarFiles", this.bean.getJarFiles(), otherTyped.getJarFiles(), false);
            this.computeDiff("JtaDataSource", this.bean.getJtaDataSource(), otherTyped.getJtaDataSource(), false);
            this.computeDiff("MappingFiles", this.bean.getMappingFiles(), otherTyped.getMappingFiles(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NonJtaDataSource", this.bean.getNonJtaDataSource(), otherTyped.getNonJtaDataSource(), false);
            this.computeSubDiff("Properties", this.bean.getProperties(), otherTyped.getProperties());
            this.computeDiff("Provider", this.bean.getProvider(), otherTyped.getProvider(), false);
            this.computeDiff("SharedCacheMode", this.bean.getSharedCacheMode(), otherTyped.getSharedCacheMode(), false);
            this.computeDiff("TransactionType", this.bean.getTransactionType(), otherTyped.getTransactionType(), false);
            this.computeDiff("ValidationMode", this.bean.getValidationMode(), otherTyped.getValidationMode(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceUnitBeanImpl original = (PersistenceUnitBeanImpl)event.getSourceBean();
            PersistenceUnitBeanImpl proposed = (PersistenceUnitBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Classes")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ExcludeUnlistedClasses")) {
                  original.setExcludeUnlistedClasses(proposed.getExcludeUnlistedClasses());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("JarFiles")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("JtaDataSource")) {
                  original.setJtaDataSource(proposed.getJtaDataSource());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MappingFiles")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Name")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("NonJtaDataSource")) {
                  original.setNonJtaDataSource(proposed.getNonJtaDataSource());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     original.setProperties((PersistencePropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Properties", (DescriptorBean)original.getProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Provider")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SharedCacheMode")) {
                  original.setSharedCacheMode(proposed.getSharedCacheMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("TransactionType")) {
                  original.setTransactionType(proposed.getTransactionType());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ValidationMode")) {
                  original.setValidationMode(proposed.getValidationMode());
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
            PersistenceUnitBeanImpl copy = (PersistenceUnitBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Classes")) && this.bean.isClassesSet()) {
               o = this.bean.getClasses();
               copy.setClasses(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludeUnlistedClasses")) && this.bean.isExcludeUnlistedClassesSet()) {
               copy.setExcludeUnlistedClasses(this.bean.getExcludeUnlistedClasses());
            }

            if ((excludeProps == null || !excludeProps.contains("JarFiles")) && this.bean.isJarFilesSet()) {
               o = this.bean.getJarFiles();
               copy.setJarFiles(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("JtaDataSource")) && this.bean.isJtaDataSourceSet()) {
               copy.setJtaDataSource(this.bean.getJtaDataSource());
            }

            if ((excludeProps == null || !excludeProps.contains("MappingFiles")) && this.bean.isMappingFilesSet()) {
               o = this.bean.getMappingFiles();
               copy.setMappingFiles(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("NonJtaDataSource")) && this.bean.isNonJtaDataSourceSet()) {
               copy.setNonJtaDataSource(this.bean.getNonJtaDataSource());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(12)) {
               Object o = this.bean.getProperties();
               copy.setProperties((PersistencePropertiesBean)null);
               copy.setProperties(o == null ? null : (PersistencePropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Provider")) && this.bean.isProviderSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("SharedCacheMode")) && this.bean.isSharedCacheModeSet()) {
               copy.setSharedCacheMode(this.bean.getSharedCacheMode());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionType")) && this.bean.isTransactionTypeSet()) {
               copy.setTransactionType(this.bean.getTransactionType());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidationMode")) && this.bean.isValidationModeSet()) {
               copy.setValidationMode(this.bean.getValidationMode());
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
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}
