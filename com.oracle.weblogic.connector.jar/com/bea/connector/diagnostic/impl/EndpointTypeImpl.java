package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.EndpointType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class EndpointTypeImpl extends XmlComplexContentImpl implements EndpointType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/connector/diagnostic", "name");
   private static final QName MSGTYPE$2 = new QName("http://www.bea.com/connector/diagnostic", "msgType");

   public EndpointTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getMsgType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MSGTYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMsgType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MSGTYPE$2, 0);
         return target;
      }
   }

   public void setMsgType(String msgType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MSGTYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MSGTYPE$2);
         }

         target.setStringValue(msgType);
      }
   }

   public void xsetMsgType(XmlString msgType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MSGTYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MSGTYPE$2);
         }

         target.set(msgType);
      }
   }
}
