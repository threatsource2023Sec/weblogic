package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.InboundResourceadapterType;
import org.jcp.xmlns.xml.ns.javaee.MessageadapterType;

public class InboundResourceadapterTypeImpl extends XmlComplexContentImpl implements InboundResourceadapterType {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGEADAPTER$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "messageadapter");
   private static final QName ID$2 = new QName("", "id");

   public InboundResourceadapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public MessageadapterType getMessageadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageadapterType target = null;
         target = (MessageadapterType)this.get_store().find_element_user(MESSAGEADAPTER$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEADAPTER$0) != 0;
      }
   }

   public void setMessageadapter(MessageadapterType messageadapter) {
      this.generatedSetterHelperImpl(messageadapter, MESSAGEADAPTER$0, 0, (short)1);
   }

   public MessageadapterType addNewMessageadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageadapterType target = null;
         target = (MessageadapterType)this.get_store().add_element_user(MESSAGEADAPTER$0);
         return target;
      }
   }

   public void unsetMessageadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEADAPTER$0, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
