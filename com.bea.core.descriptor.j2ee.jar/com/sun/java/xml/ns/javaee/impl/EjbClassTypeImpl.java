package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.EjbClassType;
import javax.xml.namespace.QName;

public class EjbClassTypeImpl extends JavaStringHolderEx implements EjbClassType {
   private static final long serialVersionUID = 1L;
   private static final QName ID$0 = new QName("", "id");

   public EjbClassTypeImpl(SchemaType sType) {
      super(sType, true);
   }

   protected EjbClassTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$0);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$0) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$0);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$0);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$0);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$0);
      }
   }
}
