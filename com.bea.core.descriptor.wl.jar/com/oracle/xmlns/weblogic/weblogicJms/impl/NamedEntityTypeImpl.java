package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.NamedEntityType;
import javax.xml.namespace.QName;

public class NamedEntityTypeImpl extends XmlComplexContentImpl implements NamedEntityType {
   private static final long serialVersionUID = 1L;
   private static final QName NOTES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "notes");
   private static final QName ID$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "id");
   private static final QName NAME$4 = new QName("", "name");

   public NamedEntityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         return target;
      }
   }

   public boolean isNilNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NOTES$0) != 0;
      }
   }

   public void setNotes(String notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NOTES$0);
         }

         target.setStringValue(notes);
      }
   }

   public void xsetNotes(XmlString notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$0);
         }

         target.set(notes);
      }
   }

   public void setNilNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$0);
         }

         target.setNil();
      }
   }

   public void unsetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NOTES$0, 0);
      }
   }

   public long getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ID$2, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(ID$2, 0);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ID$2) != 0;
      }
   }

   public void setId(long id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ID$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ID$2);
         }

         target.setLongValue(id);
      }
   }

   public void xsetId(XmlLong id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(ID$2, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ID$2, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$4);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$4);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NAME$4);
         }

         target.set(name);
      }
   }
}
