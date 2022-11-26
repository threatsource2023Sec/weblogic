package org.apache.xmlbeans.impl.xb.xmlschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xmlschema.BaseAttribute;

public class BaseAttributeImpl extends XmlComplexContentImpl implements BaseAttribute {
   private static final QName BASE$0 = new QName("http://www.w3.org/XML/1998/namespace", "base");

   public BaseAttributeImpl(SchemaType sType) {
      super(sType);
   }

   public String getBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BASE$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlAnyURI xgetBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_attribute_user(BASE$0);
         return target;
      }
   }

   public boolean isSetBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(BASE$0) != null;
      }
   }

   public void setBase(String base) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BASE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(BASE$0);
         }

         target.setStringValue(base);
      }
   }

   public void xsetBase(XmlAnyURI base) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_attribute_user(BASE$0);
         if (target == null) {
            target = (XmlAnyURI)this.get_store().add_attribute_user(BASE$0);
         }

         target.set(base);
      }
   }

   public void unsetBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(BASE$0);
      }
   }
}
