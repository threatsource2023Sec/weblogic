package org.apache.xmlbeans.impl.xb.ltgfmt.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;

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
