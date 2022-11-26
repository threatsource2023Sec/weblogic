package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitCachingType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitTransactionType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitValidationModeType;
import org.jcp.xmlns.xml.ns.persistence.PropertiesType;

public class PersistenceUnitTypeImpl extends XmlComplexContentImpl implements PersistenceUnitType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "description");
   private static final QName PROVIDER$2 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "provider");
   private static final QName JTADATASOURCE$4 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "jta-data-source");
   private static final QName NONJTADATASOURCE$6 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "non-jta-data-source");
   private static final QName MAPPINGFILE$8 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "mapping-file");
   private static final QName JARFILE$10 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "jar-file");
   private static final QName CLASS1$12 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "class");
   private static final QName EXCLUDEUNLISTEDCLASSES$14 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "exclude-unlisted-classes");
   private static final QName SHAREDCACHEMODE$16 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "shared-cache-mode");
   private static final QName VALIDATIONMODE$18 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "validation-mode");
   private static final QName PROPERTIES$20 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "properties");
   private static final QName NAME$22 = new QName("", "name");
   private static final QName TRANSACTIONTYPE$24 = new QName("", "transaction-type");

   public PersistenceUnitTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.setStringValue(description);
      }
   }

   public void xsetDescription(XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.set(description);
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public String getProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROVIDER$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROVIDER$2, 0);
         return target;
      }
   }

   public boolean isSetProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROVIDER$2) != 0;
      }
   }

   public void setProvider(String provider) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROVIDER$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PROVIDER$2);
         }

         target.setStringValue(provider);
      }
   }

   public void xsetProvider(XmlString provider) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROVIDER$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PROVIDER$2);
         }

         target.set(provider);
      }
   }

   public void unsetProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROVIDER$2, 0);
      }
   }

   public String getJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JTADATASOURCE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JTADATASOURCE$4, 0);
         return target;
      }
   }

   public boolean isSetJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JTADATASOURCE$4) != 0;
      }
   }

   public void setJtaDataSource(String jtaDataSource) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JTADATASOURCE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JTADATASOURCE$4);
         }

         target.setStringValue(jtaDataSource);
      }
   }

   public void xsetJtaDataSource(XmlString jtaDataSource) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JTADATASOURCE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JTADATASOURCE$4);
         }

         target.set(jtaDataSource);
      }
   }

   public void unsetJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JTADATASOURCE$4, 0);
      }
   }

   public String getNonJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONJTADATASOURCE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNonJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NONJTADATASOURCE$6, 0);
         return target;
      }
   }

   public boolean isSetNonJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONJTADATASOURCE$6) != 0;
      }
   }

   public void setNonJtaDataSource(String nonJtaDataSource) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONJTADATASOURCE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONJTADATASOURCE$6);
         }

         target.setStringValue(nonJtaDataSource);
      }
   }

   public void xsetNonJtaDataSource(XmlString nonJtaDataSource) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NONJTADATASOURCE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NONJTADATASOURCE$6);
         }

         target.set(nonJtaDataSource);
      }
   }

   public void unsetNonJtaDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONJTADATASOURCE$6, 0);
      }
   }

   public String[] getMappingFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAPPINGFILE$8, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getMappingFileArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPINGFILE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetMappingFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAPPINGFILE$8, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetMappingFileArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGFILE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMappingFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPINGFILE$8);
      }
   }

   public void setMappingFileArray(String[] mappingFileArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(mappingFileArray, MAPPINGFILE$8);
      }
   }

   public void setMappingFileArray(int i, String mappingFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPINGFILE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(mappingFile);
         }
      }
   }

   public void xsetMappingFileArray(XmlString[] mappingFileArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(mappingFileArray, MAPPINGFILE$8);
      }
   }

   public void xsetMappingFileArray(int i, XmlString mappingFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGFILE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(mappingFile);
         }
      }
   }

   public void insertMappingFile(int i, String mappingFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(MAPPINGFILE$8, i);
         target.setStringValue(mappingFile);
      }
   }

   public void addMappingFile(String mappingFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(MAPPINGFILE$8);
         target.setStringValue(mappingFile);
      }
   }

   public XmlString insertNewMappingFile(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(MAPPINGFILE$8, i);
         return target;
      }
   }

   public XmlString addNewMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(MAPPINGFILE$8);
         return target;
      }
   }

   public void removeMappingFile(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPINGFILE$8, i);
      }
   }

   public String[] getJarFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JARFILE$10, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getJarFileArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JARFILE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetJarFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JARFILE$10, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetJarFileArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JARFILE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJarFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JARFILE$10);
      }
   }

   public void setJarFileArray(String[] jarFileArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(jarFileArray, JARFILE$10);
      }
   }

   public void setJarFileArray(int i, String jarFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JARFILE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(jarFile);
         }
      }
   }

   public void xsetJarFileArray(XmlString[] jarFileArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(jarFileArray, JARFILE$10);
      }
   }

   public void xsetJarFileArray(int i, XmlString jarFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JARFILE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(jarFile);
         }
      }
   }

   public void insertJarFile(int i, String jarFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(JARFILE$10, i);
         target.setStringValue(jarFile);
      }
   }

   public void addJarFile(String jarFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(JARFILE$10);
         target.setStringValue(jarFile);
      }
   }

   public XmlString insertNewJarFile(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(JARFILE$10, i);
         return target;
      }
   }

   public XmlString addNewJarFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(JARFILE$10);
         return target;
      }
   }

   public void removeJarFile(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JARFILE$10, i);
      }
   }

   public String[] getClass1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CLASS1$12, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getClass1Array(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASS1$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetClass1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CLASS1$12, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetClass1Array(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASS1$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfClass1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASS1$12);
      }
   }

   public void setClass1Array(String[] class1Array) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(class1Array, CLASS1$12);
      }
   }

   public void setClass1Array(int i, String class1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASS1$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(class1);
         }
      }
   }

   public void xsetClass1Array(XmlString[] class1Array) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(class1Array, CLASS1$12);
      }
   }

   public void xsetClass1Array(int i, XmlString class1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASS1$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(class1);
         }
      }
   }

   public void insertClass1(int i, String class1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(CLASS1$12, i);
         target.setStringValue(class1);
      }
   }

   public void addClass1(String class1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(CLASS1$12);
         target.setStringValue(class1);
      }
   }

   public XmlString insertNewClass1(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(CLASS1$12, i);
         return target;
      }
   }

   public XmlString addNewClass1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(CLASS1$12);
         return target;
      }
   }

   public void removeClass1(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASS1$12, i);
      }
   }

   public boolean getExcludeUnlistedClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCLUDEUNLISTEDCLASSES$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetExcludeUnlistedClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EXCLUDEUNLISTEDCLASSES$14, 0);
         return target;
      }
   }

   public boolean isSetExcludeUnlistedClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDEUNLISTEDCLASSES$14) != 0;
      }
   }

   public void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCLUDEUNLISTEDCLASSES$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXCLUDEUNLISTEDCLASSES$14);
         }

         target.setBooleanValue(excludeUnlistedClasses);
      }
   }

   public void xsetExcludeUnlistedClasses(XmlBoolean excludeUnlistedClasses) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EXCLUDEUNLISTEDCLASSES$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(EXCLUDEUNLISTEDCLASSES$14);
         }

         target.set(excludeUnlistedClasses);
      }
   }

   public void unsetExcludeUnlistedClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDEUNLISTEDCLASSES$14, 0);
      }
   }

   public PersistenceUnitCachingType.Enum getSharedCacheMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHAREDCACHEMODE$16, 0);
         return target == null ? null : (PersistenceUnitCachingType.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitCachingType xgetSharedCacheMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitCachingType target = null;
         target = (PersistenceUnitCachingType)this.get_store().find_element_user(SHAREDCACHEMODE$16, 0);
         return target;
      }
   }

   public boolean isSetSharedCacheMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHAREDCACHEMODE$16) != 0;
      }
   }

   public void setSharedCacheMode(PersistenceUnitCachingType.Enum sharedCacheMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHAREDCACHEMODE$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHAREDCACHEMODE$16);
         }

         target.setEnumValue(sharedCacheMode);
      }
   }

   public void xsetSharedCacheMode(PersistenceUnitCachingType sharedCacheMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitCachingType target = null;
         target = (PersistenceUnitCachingType)this.get_store().find_element_user(SHAREDCACHEMODE$16, 0);
         if (target == null) {
            target = (PersistenceUnitCachingType)this.get_store().add_element_user(SHAREDCACHEMODE$16);
         }

         target.set(sharedCacheMode);
      }
   }

   public void unsetSharedCacheMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHAREDCACHEMODE$16, 0);
      }
   }

   public PersistenceUnitValidationModeType.Enum getValidationMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATIONMODE$18, 0);
         return target == null ? null : (PersistenceUnitValidationModeType.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitValidationModeType xgetValidationMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitValidationModeType target = null;
         target = (PersistenceUnitValidationModeType)this.get_store().find_element_user(VALIDATIONMODE$18, 0);
         return target;
      }
   }

   public boolean isSetValidationMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATIONMODE$18) != 0;
      }
   }

   public void setValidationMode(PersistenceUnitValidationModeType.Enum validationMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATIONMODE$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALIDATIONMODE$18);
         }

         target.setEnumValue(validationMode);
      }
   }

   public void xsetValidationMode(PersistenceUnitValidationModeType validationMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitValidationModeType target = null;
         target = (PersistenceUnitValidationModeType)this.get_store().find_element_user(VALIDATIONMODE$18, 0);
         if (target == null) {
            target = (PersistenceUnitValidationModeType)this.get_store().add_element_user(VALIDATIONMODE$18);
         }

         target.set(validationMode);
      }
   }

   public void unsetValidationMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATIONMODE$18, 0);
      }
   }

   public PropertiesType getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(PROPERTIES$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$20) != 0;
      }
   }

   public void setProperties(PropertiesType properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$20, 0, (short)1);
   }

   public PropertiesType addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().add_element_user(PROPERTIES$20);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$20, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$22);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$22);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$22);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NAME$22);
         }

         target.set(name);
      }
   }

   public PersistenceUnitTransactionType.Enum getTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TRANSACTIONTYPE$24);
         return target == null ? null : (PersistenceUnitTransactionType.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitTransactionType xgetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitTransactionType target = null;
         target = (PersistenceUnitTransactionType)this.get_store().find_attribute_user(TRANSACTIONTYPE$24);
         return target;
      }
   }

   public boolean isSetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TRANSACTIONTYPE$24) != null;
      }
   }

   public void setTransactionType(PersistenceUnitTransactionType.Enum transactionType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TRANSACTIONTYPE$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TRANSACTIONTYPE$24);
         }

         target.setEnumValue(transactionType);
      }
   }

   public void xsetTransactionType(PersistenceUnitTransactionType transactionType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitTransactionType target = null;
         target = (PersistenceUnitTransactionType)this.get_store().find_attribute_user(TRANSACTIONTYPE$24);
         if (target == null) {
            target = (PersistenceUnitTransactionType)this.get_store().add_attribute_user(TRANSACTIONTYPE$24);
         }

         target.set(transactionType);
      }
   }

   public void unsetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TRANSACTIONTYPE$24);
      }
   }
}
