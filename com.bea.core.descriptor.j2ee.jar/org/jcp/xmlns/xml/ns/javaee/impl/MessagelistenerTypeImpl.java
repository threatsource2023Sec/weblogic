package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ActivationspecType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.MessagelistenerType;

public class MessagelistenerTypeImpl extends XmlComplexContentImpl implements MessagelistenerType {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGELISTENERTYPE$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "messagelistener-type");
   private static final QName ACTIVATIONSPEC$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "activationspec");
   private static final QName ID$4 = new QName("", "id");

   public MessagelistenerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getMessagelistenerType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(MESSAGELISTENERTYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMessagelistenerType(FullyQualifiedClassType messagelistenerType) {
      this.generatedSetterHelperImpl(messagelistenerType, MESSAGELISTENERTYPE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewMessagelistenerType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(MESSAGELISTENERTYPE$0);
         return target;
      }
   }

   public ActivationspecType getActivationspec() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationspecType target = null;
         target = (ActivationspecType)this.get_store().find_element_user(ACTIVATIONSPEC$2, 0);
         return target == null ? null : target;
      }
   }

   public void setActivationspec(ActivationspecType activationspec) {
      this.generatedSetterHelperImpl(activationspec, ACTIVATIONSPEC$2, 0, (short)1);
   }

   public ActivationspecType addNewActivationspec() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationspecType target = null;
         target = (ActivationspecType)this.get_store().add_element_user(ACTIVATIONSPEC$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
