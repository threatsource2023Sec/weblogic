package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FileSchemaFactoryType;
import javax.xml.namespace.QName;

public class FileSchemaFactoryTypeImpl extends SchemaFactoryTypeImpl implements FileSchemaFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName FILENAME$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "file-name");
   private static final QName FILE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "file");

   public FileSchemaFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILENAME$0, 0);
         return target;
      }
   }

   public boolean isNilFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILENAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILENAME$0) != 0;
      }
   }

   public void setFileName(String fileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILENAME$0);
         }

         target.setStringValue(fileName);
      }
   }

   public void xsetFileName(XmlString fileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILENAME$0);
         }

         target.set(fileName);
      }
   }

   public void setNilFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILENAME$0);
         }

         target.setNil();
      }
   }

   public void unsetFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILENAME$0, 0);
      }
   }

   public String getFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$2, 0);
         return target;
      }
   }

   public boolean isNilFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILE$2) != 0;
      }
   }

   public void setFile(String file) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILE$2);
         }

         target.setStringValue(file);
      }
   }

   public void xsetFile(XmlString file) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILE$2);
         }

         target.set(file);
      }
   }

   public void setNilFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILE$2);
         }

         target.setNil();
      }
   }

   public void unsetFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILE$2, 0);
      }
   }
}
