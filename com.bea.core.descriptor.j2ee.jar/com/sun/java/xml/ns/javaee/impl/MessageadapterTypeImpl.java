package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.MessageadapterType;
import com.sun.java.xml.ns.javaee.MessagelistenerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MessageadapterTypeImpl extends XmlComplexContentImpl implements MessageadapterType {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGELISTENER$0 = new QName("http://java.sun.com/xml/ns/javaee", "messagelistener");
   private static final QName ID$2 = new QName("", "id");

   public MessageadapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public MessagelistenerType[] getMessagelistenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGELISTENER$0, targetList);
         MessagelistenerType[] result = new MessagelistenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessagelistenerType getMessagelistenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessagelistenerType target = null;
         target = (MessagelistenerType)this.get_store().find_element_user(MESSAGELISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessagelistenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELISTENER$0);
      }
   }

   public void setMessagelistenerArray(MessagelistenerType[] messagelistenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(messagelistenerArray, MESSAGELISTENER$0);
   }

   public void setMessagelistenerArray(int i, MessagelistenerType messagelistener) {
      this.generatedSetterHelperImpl(messagelistener, MESSAGELISTENER$0, i, (short)2);
   }

   public MessagelistenerType insertNewMessagelistener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessagelistenerType target = null;
         target = (MessagelistenerType)this.get_store().insert_element_user(MESSAGELISTENER$0, i);
         return target;
      }
   }

   public MessagelistenerType addNewMessagelistener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessagelistenerType target = null;
         target = (MessagelistenerType)this.get_store().add_element_user(MESSAGELISTENER$0);
         return target;
      }
   }

   public void removeMessagelistener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELISTENER$0, i);
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
