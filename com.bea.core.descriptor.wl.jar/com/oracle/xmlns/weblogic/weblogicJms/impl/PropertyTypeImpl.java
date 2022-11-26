package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.PropertyType;
import javax.xml.namespace.QName;

public class PropertyTypeImpl extends XmlComplexContentImpl implements PropertyType {
   private static final long serialVersionUID = 1L;
   private static final QName KEY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "key");
   private static final QName VALUE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "value");

   public PropertyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(KEY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(KEY$0, 0);
         return target;
      }
   }

   public void setKey(String key) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(KEY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(KEY$0);
         }

         target.setStringValue(key);
      }
   }

   public void xsetKey(XmlString key) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(KEY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(KEY$0);
         }

         target.set(key);
      }
   }

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALUE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$2, 0);
         return target;
      }
   }

   public void setValue(String value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALUE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALUE$2);
         }

         target.setStringValue(value);
      }
   }

   public void xsetValue(XmlString value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VALUE$2);
         }

         target.set(value);
      }
   }
}
