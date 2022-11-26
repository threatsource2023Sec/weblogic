package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.End;

public class EndImpl extends XmlComplexContentImpl implements End {
   private static final long serialVersionUID = 1L;
   private static final QName ON$0 = new QName("", "on");
   private static final QName EXITSTATUS$2 = new QName("", "exit-status");

   public EndImpl(SchemaType sType) {
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

   public String getExitStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(EXITSTATUS$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetExitStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(EXITSTATUS$2);
         return target;
      }
   }

   public boolean isSetExitStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(EXITSTATUS$2) != null;
      }
   }

   public void setExitStatus(String exitStatus) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(EXITSTATUS$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(EXITSTATUS$2);
         }

         target.setStringValue(exitStatus);
      }
   }

   public void xsetExitStatus(XmlString exitStatus) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(EXITSTATUS$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(EXITSTATUS$2);
         }

         target.set(exitStatus);
      }
   }

   public void unsetExitStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(EXITSTATUS$2);
      }
   }
}
