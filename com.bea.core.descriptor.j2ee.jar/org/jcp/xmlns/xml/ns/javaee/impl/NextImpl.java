package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Next;

public class NextImpl extends XmlComplexContentImpl implements Next {
   private static final long serialVersionUID = 1L;
   private static final QName ON$0 = new QName("", "on");
   private static final QName TO$2 = new QName("", "to");

   public NextImpl(SchemaType sType) {
      super(sType);
   }

   public String getOn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ON$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ON$0);
         return target;
      }
   }

   public void setOn(String on) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ON$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ON$0);
         }

         target.setStringValue(on);
      }
   }

   public void xsetOn(XmlString on) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ON$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ON$0);
         }

         target.set(on);
      }
   }

   public String getTo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TO$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TO$2);
         return target;
      }
   }

   public void setTo(String to) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TO$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TO$2);
         }

         target.setStringValue(to);
      }
   }

   public void xsetTo(XmlString to) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TO$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(TO$2);
         }

         target.set(to);
      }
   }
}
