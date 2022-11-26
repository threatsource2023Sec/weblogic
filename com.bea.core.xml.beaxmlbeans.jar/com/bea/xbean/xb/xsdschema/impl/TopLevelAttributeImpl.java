package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.TopLevelAttribute;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNCName;
import javax.xml.namespace.QName;

public class TopLevelAttributeImpl extends AttributeImpl implements TopLevelAttribute {
   private static final QName NAME$0 = new QName("", "name");

   public TopLevelAttributeImpl(SchemaType sType) {
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

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$0);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$0) != null;
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

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$0);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$0);
      }
   }
}
