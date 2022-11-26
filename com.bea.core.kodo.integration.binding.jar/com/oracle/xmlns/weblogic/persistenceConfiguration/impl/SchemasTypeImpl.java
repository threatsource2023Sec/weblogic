package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SchemasType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SchemasTypeImpl extends XmlComplexContentImpl implements SchemasType {
   private static final long serialVersionUID = 1L;
   private static final QName SCHEMA$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "schema");

   public SchemasTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getSchemaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SCHEMA$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getSchemaArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMA$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetSchemaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SCHEMA$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetSchemaArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilSchemaArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfSchemaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMA$0);
      }
   }

   public void setSchemaArray(String[] schemaArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(schemaArray, SCHEMA$0);
      }
   }

   public void setSchemaArray(int i, String schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMA$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(schema);
         }
      }
   }

   public void xsetSchemaArray(XmlString[] schemaArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(schemaArray, SCHEMA$0);
      }
   }

   public void xsetSchemaArray(int i, XmlString schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(schema);
         }
      }
   }

   public void setNilSchemaArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public void insertSchema(int i, String schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(SCHEMA$0, i);
         target.setStringValue(schema);
      }
   }

   public void addSchema(String schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(SCHEMA$0);
         target.setStringValue(schema);
      }
   }

   public XmlString insertNewSchema(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(SCHEMA$0, i);
         return target;
      }
   }

   public XmlString addNewSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(SCHEMA$0);
         return target;
      }
   }

   public void removeSchema(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMA$0, i);
      }
   }
}
