package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AccessTimeoutType;
import org.jcp.xmlns.xml.ns.javaee.TimeUnitTypeType;
import org.jcp.xmlns.xml.ns.javaee.XsdIntegerType;

public class AccessTimeoutTypeImpl extends XmlComplexContentImpl implements AccessTimeoutType {
   private static final long serialVersionUID = 1L;
   private static final QName TIMEOUT$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "timeout");
   private static final QName UNIT$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "unit");
   private static final QName ID$4 = new QName("", "id");

   public AccessTimeoutTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdIntegerType getTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(TIMEOUT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setTimeout(XsdIntegerType timeout) {
      this.generatedSetterHelperImpl(timeout, TIMEOUT$0, 0, (short)1);
   }

   public XsdIntegerType addNewTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(TIMEOUT$0);
         return target;
      }
   }

   public TimeUnitTypeType getUnit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimeUnitTypeType target = null;
         target = (TimeUnitTypeType)this.get_store().find_element_user(UNIT$2, 0);
         return target == null ? null : target;
      }
   }

   public void setUnit(TimeUnitTypeType unit) {
      this.generatedSetterHelperImpl(unit, UNIT$2, 0, (short)1);
   }

   public TimeUnitTypeType addNewUnit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimeUnitTypeType target = null;
         target = (TimeUnitTypeType)this.get_store().add_element_user(UNIT$2);
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
