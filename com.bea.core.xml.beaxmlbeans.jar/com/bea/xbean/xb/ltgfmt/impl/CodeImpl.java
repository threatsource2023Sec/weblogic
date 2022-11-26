package com.bea.xbean.xb.ltgfmt.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.ltgfmt.Code;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlToken;
import javax.xml.namespace.QName;

public class CodeImpl extends XmlComplexContentImpl implements Code {
   private static final QName ID$0 = new QName("", "ID");

   public CodeImpl(SchemaType sType) {
      super(sType);
   }

   public String getID() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetID() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(ID$0);
         return target;
      }
   }

   public boolean isSetID() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$0) != null;
      }
   }

   public void setID(String id) {
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

   public void xsetID(XmlToken id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(ID$0);
         if (target == null) {
            target = (XmlToken)this.get_store().add_attribute_user(ID$0);
         }

         target.set(id);
      }
   }

   public void unsetID() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$0);
      }
   }
}
