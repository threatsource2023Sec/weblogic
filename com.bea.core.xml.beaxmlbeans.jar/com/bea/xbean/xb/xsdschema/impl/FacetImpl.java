package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class FacetImpl extends AnnotatedImpl implements Facet {
   private static final QName VALUE$0 = new QName("", "value");
   private static final QName FIXED$2 = new QName("", "fixed");

   public FacetImpl(SchemaType sType) {
      super(sType);
   }

   public XmlAnySimpleType getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnySimpleType target = null;
         target = (XmlAnySimpleType)this.get_store().find_attribute_user(VALUE$0);
         return target == null ? null : target;
      }
   }

   public void setValue(XmlAnySimpleType value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnySimpleType target = null;
         target = (XmlAnySimpleType)this.get_store().find_attribute_user(VALUE$0);
         if (target == null) {
            target = (XmlAnySimpleType)this.get_store().add_attribute_user(VALUE$0);
         }

         target.set(value);
      }
   }

   public XmlAnySimpleType addNewValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnySimpleType target = null;
         target = (XmlAnySimpleType)this.get_store().add_attribute_user(VALUE$0);
         return target;
      }
   }

   public boolean getFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FIXED$2);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(FIXED$2);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(FIXED$2);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(FIXED$2);
         }

         return target;
      }
   }

   public boolean isSetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FIXED$2) != null;
      }
   }

   public void setFixed(boolean fixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FIXED$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FIXED$2);
         }

         target.setBooleanValue(fixed);
      }
   }

   public void xsetFixed(XmlBoolean fixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(FIXED$2);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(FIXED$2);
         }

         target.set(fixed);
      }
   }

   public void unsetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FIXED$2);
      }
   }
}
