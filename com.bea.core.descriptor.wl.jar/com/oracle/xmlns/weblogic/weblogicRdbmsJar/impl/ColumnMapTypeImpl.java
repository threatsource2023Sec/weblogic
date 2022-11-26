package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ColumnMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ForeignKeyColumnType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.KeyColumnType;
import javax.xml.namespace.QName;

public class ColumnMapTypeImpl extends XmlComplexContentImpl implements ColumnMapType {
   private static final long serialVersionUID = 1L;
   private static final QName FOREIGNKEYCOLUMN$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "foreign-key-column");
   private static final QName KEYCOLUMN$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "key-column");
   private static final QName ID$4 = new QName("", "id");

   public ColumnMapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ForeignKeyColumnType getForeignKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignKeyColumnType target = null;
         target = (ForeignKeyColumnType)this.get_store().find_element_user(FOREIGNKEYCOLUMN$0, 0);
         return target == null ? null : target;
      }
   }

   public void setForeignKeyColumn(ForeignKeyColumnType foreignKeyColumn) {
      this.generatedSetterHelperImpl(foreignKeyColumn, FOREIGNKEYCOLUMN$0, 0, (short)1);
   }

   public ForeignKeyColumnType addNewForeignKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignKeyColumnType target = null;
         target = (ForeignKeyColumnType)this.get_store().add_element_user(FOREIGNKEYCOLUMN$0);
         return target;
      }
   }

   public KeyColumnType getKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyColumnType target = null;
         target = (KeyColumnType)this.get_store().find_element_user(KEYCOLUMN$2, 0);
         return target == null ? null : target;
      }
   }

   public void setKeyColumn(KeyColumnType keyColumn) {
      this.generatedSetterHelperImpl(keyColumn, KEYCOLUMN$2, 0, (short)1);
   }

   public KeyColumnType addNewKeyColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyColumnType target = null;
         target = (KeyColumnType)this.get_store().add_element_user(KEYCOLUMN$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
