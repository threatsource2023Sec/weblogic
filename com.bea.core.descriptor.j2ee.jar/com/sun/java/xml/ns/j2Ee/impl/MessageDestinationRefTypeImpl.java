package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.JndiNameType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationLinkType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationRefType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationTypeType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationUsageType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MessageDestinationRefTypeImpl extends XmlComplexContentImpl implements MessageDestinationRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName MESSAGEDESTINATIONREFNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-ref-name");
   private static final QName MESSAGEDESTINATIONTYPE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-type");
   private static final QName MESSAGEDESTINATIONUSAGE$6 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-usage");
   private static final QName MESSAGEDESTINATIONLINK$8 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-link");
   private static final QName ID$10 = new QName("", "id");

   public MessageDestinationRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public JndiNameType getMessageDestinationRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(MESSAGEDESTINATIONREFNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setMessageDestinationRefName(JndiNameType messageDestinationRefName) {
      this.generatedSetterHelperImpl(messageDestinationRefName, MESSAGEDESTINATIONREFNAME$2, 0, (short)1);
   }

   public JndiNameType addNewMessageDestinationRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(MESSAGEDESTINATIONREFNAME$2);
         return target;
      }
   }

   public MessageDestinationTypeType getMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().find_element_user(MESSAGEDESTINATIONTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setMessageDestinationType(MessageDestinationTypeType messageDestinationType) {
      this.generatedSetterHelperImpl(messageDestinationType, MESSAGEDESTINATIONTYPE$4, 0, (short)1);
   }

   public MessageDestinationTypeType addNewMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().add_element_user(MESSAGEDESTINATIONTYPE$4);
         return target;
      }
   }

   public MessageDestinationUsageType getMessageDestinationUsage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationUsageType target = null;
         target = (MessageDestinationUsageType)this.get_store().find_element_user(MESSAGEDESTINATIONUSAGE$6, 0);
         return target == null ? null : target;
      }
   }

   public void setMessageDestinationUsage(MessageDestinationUsageType messageDestinationUsage) {
      this.generatedSetterHelperImpl(messageDestinationUsage, MESSAGEDESTINATIONUSAGE$6, 0, (short)1);
   }

   public MessageDestinationUsageType addNewMessageDestinationUsage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationUsageType target = null;
         target = (MessageDestinationUsageType)this.get_store().add_element_user(MESSAGEDESTINATIONUSAGE$6);
         return target;
      }
   }

   public MessageDestinationLinkType getMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().find_element_user(MESSAGEDESTINATIONLINK$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONLINK$8) != 0;
      }
   }

   public void setMessageDestinationLink(MessageDestinationLinkType messageDestinationLink) {
      this.generatedSetterHelperImpl(messageDestinationLink, MESSAGEDESTINATIONLINK$8, 0, (short)1);
   }

   public MessageDestinationLinkType addNewMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().add_element_user(MESSAGEDESTINATIONLINK$8);
         return target;
      }
   }

   public void unsetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONLINK$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
