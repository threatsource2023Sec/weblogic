package com.bea.ns.weblogic.x60.impl;

import com.bea.ns.weblogic.x60.CmpFieldType;
import com.bea.ns.weblogic.x60.DbmsColumnType;
import com.bea.ns.weblogic.x60.FieldMapType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;

public class FieldMapTypeImpl extends XmlComplexContentImpl implements FieldMapType {
   private static final long serialVersionUID = 1L;
   private static final QName CMPFIELD$0 = new QName("http://www.bea.com/ns/weblogic/60", "cmp-field");
   private static final QName DBMSCOLUMN$2 = new QName("http://www.bea.com/ns/weblogic/60", "dbms-column");
   private static final QName ID$4 = new QName("", "id");

   public FieldMapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CmpFieldType getCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().find_element_user(CMPFIELD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCmpField(CmpFieldType cmpField) {
      this.generatedSetterHelperImpl(cmpField, CMPFIELD$0, 0, (short)1);
   }

   public CmpFieldType addNewCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().add_element_user(CMPFIELD$0);
         return target;
      }
   }

   public DbmsColumnType getDbmsColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().find_element_user(DBMSCOLUMN$2, 0);
         return target == null ? null : target;
      }
   }

   public void setDbmsColumn(DbmsColumnType dbmsColumn) {
      this.generatedSetterHelperImpl(dbmsColumn, DBMSCOLUMN$2, 0, (short)1);
   }

   public DbmsColumnType addNewDbmsColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().add_element_user(DBMSCOLUMN$2);
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
