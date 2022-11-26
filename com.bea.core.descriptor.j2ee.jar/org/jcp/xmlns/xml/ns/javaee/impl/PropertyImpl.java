package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Property;

public class PropertyImpl extends XmlComplexContentImpl implements Property {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("", "name");
   private static final QName VALUE$2 = new QName("", "value");

   public PropertyImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VALUE$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VALUE$2);
         return target;
      }
   }

   public void setValue(String value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VALUE$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VALUE$2);
         }

         target.setStringValue(value);
      }
   }

   public void xsetValue(XmlString value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VALUE$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VALUE$2);
         }

         target.set(value);
      }
   }
}
