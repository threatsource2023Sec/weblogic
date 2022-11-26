package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PostgresDictionaryType;
import javax.xml.namespace.QName;

public class PostgresDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements PostgresDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName ALLSEQUENCESSQL$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "all-sequences-sql");
   private static final QName NAMEDSEQUENCESFROMALLSCHEMASSQL$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "named-sequences-from-all-schemas-sql");
   private static final QName ALLSEQUENCESFROMONESCHEMASQL$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "all-sequences-from-one-schema-sql");
   private static final QName NAMEDSEQUENCESFROMONESCHEMASQL$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "named-sequences-from-one-schema-sql");
   private static final QName SUPPORTSSETFETCHSIZE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-set-fetch-size");

   public PostgresDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getAllSequencesSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLSEQUENCESSQL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAllSequencesSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESSQL$0, 0);
         return target;
      }
   }

   public boolean isNilAllSequencesSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESSQL$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAllSequencesSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLSEQUENCESSQL$0) != 0;
      }
   }

   public void setAllSequencesSql(String allSequencesSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLSEQUENCESSQL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLSEQUENCESSQL$0);
         }

         target.setStringValue(allSequencesSql);
      }
   }

   public void xsetAllSequencesSql(XmlString allSequencesSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESSQL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ALLSEQUENCESSQL$0);
         }

         target.set(allSequencesSql);
      }
   }

   public void setNilAllSequencesSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESSQL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ALLSEQUENCESSQL$0);
         }

         target.setNil();
      }
   }

   public void unsetAllSequencesSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLSEQUENCESSQL$0, 0);
      }
   }

   public String getNamedSequencesFromAllSchemasSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNamedSequencesFromAllSchemasSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
         return target;
      }
   }

   public boolean isNilNamedSequencesFromAllSchemasSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNamedSequencesFromAllSchemasSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAMEDSEQUENCESFROMALLSCHEMASSQL$2) != 0;
      }
   }

   public void setNamedSequencesFromAllSchemasSql(String namedSequencesFromAllSchemasSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2);
         }

         target.setStringValue(namedSequencesFromAllSchemasSql);
      }
   }

   public void xsetNamedSequencesFromAllSchemasSql(XmlString namedSequencesFromAllSchemasSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2);
         }

         target.set(namedSequencesFromAllSchemasSql);
      }
   }

   public void setNilNamedSequencesFromAllSchemasSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAMEDSEQUENCESFROMALLSCHEMASSQL$2);
         }

         target.setNil();
      }
   }

   public void unsetNamedSequencesFromAllSchemasSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAMEDSEQUENCESFROMALLSCHEMASSQL$2, 0);
      }
   }

   public String getAllSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAllSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
         return target;
      }
   }

   public boolean isNilAllSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAllSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLSEQUENCESFROMONESCHEMASQL$4) != 0;
      }
   }

   public void setAllSequencesFromOneSchemaSql(String allSequencesFromOneSchemaSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLSEQUENCESFROMONESCHEMASQL$4);
         }

         target.setStringValue(allSequencesFromOneSchemaSql);
      }
   }

   public void xsetAllSequencesFromOneSchemaSql(XmlString allSequencesFromOneSchemaSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ALLSEQUENCESFROMONESCHEMASQL$4);
         }

         target.set(allSequencesFromOneSchemaSql);
      }
   }

   public void setNilAllSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ALLSEQUENCESFROMONESCHEMASQL$4);
         }

         target.setNil();
      }
   }

   public void unsetAllSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLSEQUENCESFROMONESCHEMASQL$4, 0);
      }
   }

   public String getNamedSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNamedSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
         return target;
      }
   }

   public boolean isNilNamedSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNamedSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAMEDSEQUENCESFROMONESCHEMASQL$6) != 0;
      }
   }

   public void setNamedSequencesFromOneSchemaSql(String namedSequencesFromOneSchemaSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6);
         }

         target.setStringValue(namedSequencesFromOneSchemaSql);
      }
   }

   public void xsetNamedSequencesFromOneSchemaSql(XmlString namedSequencesFromOneSchemaSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6);
         }

         target.set(namedSequencesFromOneSchemaSql);
      }
   }

   public void setNilNamedSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAMEDSEQUENCESFROMONESCHEMASQL$6);
         }

         target.setNil();
      }
   }

   public void unsetNamedSequencesFromOneSchemaSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAMEDSEQUENCESFROMONESCHEMASQL$6, 0);
      }
   }

   public boolean getSupportsSetFetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSETFETCHSIZE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSetFetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSETFETCHSIZE$8, 0);
         return target;
      }
   }

   public boolean isSetSupportsSetFetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSETFETCHSIZE$8) != 0;
      }
   }

   public void setSupportsSetFetchSize(boolean supportsSetFetchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSETFETCHSIZE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSETFETCHSIZE$8);
         }

         target.setBooleanValue(supportsSetFetchSize);
      }
   }

   public void xsetSupportsSetFetchSize(XmlBoolean supportsSetFetchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSETFETCHSIZE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSETFETCHSIZE$8);
         }

         target.set(supportsSetFetchSize);
      }
   }

   public void unsetSupportsSetFetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSETFETCHSIZE$8, 0);
      }
   }
}
