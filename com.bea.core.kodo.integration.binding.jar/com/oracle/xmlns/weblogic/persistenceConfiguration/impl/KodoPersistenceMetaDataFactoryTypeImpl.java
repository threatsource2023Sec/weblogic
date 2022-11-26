package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoPersistenceMetaDataFactoryType;
import javax.xml.namespace.QName;

public class KodoPersistenceMetaDataFactoryTypeImpl extends MetaDataFactoryTypeImpl implements KodoPersistenceMetaDataFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName URLS$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "urls");
   private static final QName FILES$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "files");
   private static final QName CLASSPATHSCAN$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "classpath-scan");
   private static final QName DEFAULTACCESSTYPE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-access-type");
   private static final QName FIELDOVERRIDE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "field-override");
   private static final QName TYPES$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "types");
   private static final QName STOREMODE$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "store-mode");
   private static final QName STRICT$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "strict");
   private static final QName RESOURCES$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "resources");

   public KodoPersistenceMetaDataFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUrls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URLS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUrls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URLS$0, 0);
         return target;
      }
   }

   public boolean isNilUrls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URLS$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetUrls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URLS$0) != 0;
      }
   }

   public void setUrls(String urls) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URLS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(URLS$0);
         }

         target.setStringValue(urls);
      }
   }

   public void xsetUrls(XmlString urls) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URLS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URLS$0);
         }

         target.set(urls);
      }
   }

   public void setNilUrls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URLS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URLS$0);
         }

         target.setNil();
      }
   }

   public void unsetUrls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URLS$0, 0);
      }
   }

   public String getFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILES$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILES$2, 0);
         return target;
      }
   }

   public boolean isNilFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILES$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILES$2) != 0;
      }
   }

   public void setFiles(String files) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILES$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILES$2);
         }

         target.setStringValue(files);
      }
   }

   public void xsetFiles(XmlString files) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILES$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILES$2);
         }

         target.set(files);
      }
   }

   public void setNilFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILES$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILES$2);
         }

         target.setNil();
      }
   }

   public void unsetFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILES$2, 0);
      }
   }

   public String getClasspathScan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSPATHSCAN$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClasspathScan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSPATHSCAN$4, 0);
         return target;
      }
   }

   public boolean isNilClasspathScan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSPATHSCAN$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClasspathScan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSPATHSCAN$4) != 0;
      }
   }

   public void setClasspathScan(String classpathScan) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSPATHSCAN$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLASSPATHSCAN$4);
         }

         target.setStringValue(classpathScan);
      }
   }

   public void xsetClasspathScan(XmlString classpathScan) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSPATHSCAN$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSPATHSCAN$4);
         }

         target.set(classpathScan);
      }
   }

   public void setNilClasspathScan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSPATHSCAN$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSPATHSCAN$4);
         }

         target.setNil();
      }
   }

   public void unsetClasspathScan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSPATHSCAN$4, 0);
      }
   }

   public String getDefaultAccessType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTACCESSTYPE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefaultAccessType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTACCESSTYPE$6, 0);
         return target;
      }
   }

   public boolean isNilDefaultAccessType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTACCESSTYPE$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultAccessType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTACCESSTYPE$6) != 0;
      }
   }

   public void setDefaultAccessType(String defaultAccessType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTACCESSTYPE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTACCESSTYPE$6);
         }

         target.setStringValue(defaultAccessType);
      }
   }

   public void xsetDefaultAccessType(XmlString defaultAccessType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTACCESSTYPE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTACCESSTYPE$6);
         }

         target.set(defaultAccessType);
      }
   }

   public void setNilDefaultAccessType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTACCESSTYPE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTACCESSTYPE$6);
         }

         target.setNil();
      }
   }

   public void unsetDefaultAccessType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTACCESSTYPE$6, 0);
      }
   }

   public boolean getFieldOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELDOVERRIDE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetFieldOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FIELDOVERRIDE$8, 0);
         return target;
      }
   }

   public boolean isSetFieldOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELDOVERRIDE$8) != 0;
      }
   }

   public void setFieldOverride(boolean fieldOverride) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELDOVERRIDE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FIELDOVERRIDE$8);
         }

         target.setBooleanValue(fieldOverride);
      }
   }

   public void xsetFieldOverride(XmlBoolean fieldOverride) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FIELDOVERRIDE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(FIELDOVERRIDE$8);
         }

         target.set(fieldOverride);
      }
   }

   public void unsetFieldOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELDOVERRIDE$8, 0);
      }
   }

   public String getTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPES$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$10, 0);
         return target;
      }
   }

   public boolean isNilTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TYPES$10) != 0;
      }
   }

   public void setTypes(String types) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPES$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPES$10);
         }

         target.setStringValue(types);
      }
   }

   public void xsetTypes(XmlString types) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPES$10);
         }

         target.set(types);
      }
   }

   public void setNilTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPES$10);
         }

         target.setNil();
      }
   }

   public void unsetTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TYPES$10, 0);
      }
   }

   public int getStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREMODE$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(STOREMODE$12, 0);
         return target;
      }
   }

   public boolean isSetStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOREMODE$12) != 0;
      }
   }

   public void setStoreMode(int storeMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREMODE$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STOREMODE$12);
         }

         target.setIntValue(storeMode);
      }
   }

   public void xsetStoreMode(XmlInt storeMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(STOREMODE$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(STOREMODE$12);
         }

         target.set(storeMode);
      }
   }

   public void unsetStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOREMODE$12, 0);
      }
   }

   public boolean getStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRICT$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STRICT$14, 0);
         return target;
      }
   }

   public boolean isSetStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRICT$14) != 0;
      }
   }

   public void setStrict(boolean strict) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRICT$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STRICT$14);
         }

         target.setBooleanValue(strict);
      }
   }

   public void xsetStrict(XmlBoolean strict) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STRICT$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STRICT$14);
         }

         target.set(strict);
      }
   }

   public void unsetStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRICT$14, 0);
      }
   }

   public String getResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCES$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCES$16, 0);
         return target;
      }
   }

   public boolean isNilResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCES$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCES$16) != 0;
      }
   }

   public void setResources(String resources) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCES$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOURCES$16);
         }

         target.setStringValue(resources);
      }
   }

   public void xsetResources(XmlString resources) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCES$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCES$16);
         }

         target.set(resources);
      }
   }

   public void setNilResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCES$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCES$16);
         }

         target.setNil();
      }
   }

   public void unsetResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCES$16, 0);
      }
   }
}
