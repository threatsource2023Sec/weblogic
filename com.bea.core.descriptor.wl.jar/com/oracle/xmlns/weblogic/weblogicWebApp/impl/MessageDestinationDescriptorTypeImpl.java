package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicWebApp.DestinationJndiNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.InitialContextFactoryType;
import com.oracle.xmlns.weblogic.weblogicWebApp.MessageDestinationDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.MessageDestinationNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ProviderUrlType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class MessageDestinationDescriptorTypeImpl extends XmlComplexContentImpl implements MessageDestinationDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGEDESTINATIONNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "message-destination-name");
   private static final QName DESTINATIONJNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "destination-jndi-name");
   private static final QName INITIALCONTEXTFACTORY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "initial-context-factory");
   private static final QName PROVIDERURL$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "provider-url");
   private static final QName DESTINATIONRESOURCELINK$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "destination-resource-link");
   private static final QName ID$10 = new QName("", "id");

   public MessageDestinationDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public MessageDestinationNameType getMessageDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationNameType target = null;
         target = (MessageDestinationNameType)this.get_store().find_element_user(MESSAGEDESTINATIONNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMessageDestinationName(MessageDestinationNameType messageDestinationName) {
      this.generatedSetterHelperImpl(messageDestinationName, MESSAGEDESTINATIONNAME$0, 0, (short)1);
   }

   public MessageDestinationNameType addNewMessageDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationNameType target = null;
         target = (MessageDestinationNameType)this.get_store().add_element_user(MESSAGEDESTINATIONNAME$0);
         return target;
      }
   }

   public DestinationJndiNameType getDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationJndiNameType target = null;
         target = (DestinationJndiNameType)this.get_store().find_element_user(DESTINATIONJNDINAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONJNDINAME$2) != 0;
      }
   }

   public void setDestinationJndiName(DestinationJndiNameType destinationJndiName) {
      this.generatedSetterHelperImpl(destinationJndiName, DESTINATIONJNDINAME$2, 0, (short)1);
   }

   public DestinationJndiNameType addNewDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationJndiNameType target = null;
         target = (DestinationJndiNameType)this.get_store().add_element_user(DESTINATIONJNDINAME$2);
         return target;
      }
   }

   public void unsetDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONJNDINAME$2, 0);
      }
   }

   public InitialContextFactoryType getInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitialContextFactoryType target = null;
         target = (InitialContextFactoryType)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALCONTEXTFACTORY$4) != 0;
      }
   }

   public void setInitialContextFactory(InitialContextFactoryType initialContextFactory) {
      this.generatedSetterHelperImpl(initialContextFactory, INITIALCONTEXTFACTORY$4, 0, (short)1);
   }

   public InitialContextFactoryType addNewInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitialContextFactoryType target = null;
         target = (InitialContextFactoryType)this.get_store().add_element_user(INITIALCONTEXTFACTORY$4);
         return target;
      }
   }

   public void unsetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALCONTEXTFACTORY$4, 0);
      }
   }

   public ProviderUrlType getProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProviderUrlType target = null;
         target = (ProviderUrlType)this.get_store().find_element_user(PROVIDERURL$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROVIDERURL$6) != 0;
      }
   }

   public void setProviderUrl(ProviderUrlType providerUrl) {
      this.generatedSetterHelperImpl(providerUrl, PROVIDERURL$6, 0, (short)1);
   }

   public ProviderUrlType addNewProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProviderUrlType target = null;
         target = (ProviderUrlType)this.get_store().add_element_user(PROVIDERURL$6);
         return target;
      }
   }

   public void unsetProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROVIDERURL$6, 0);
      }
   }

   public String getDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONRESOURCELINK$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONRESOURCELINK$8) != 0;
      }
   }

   public void setDestinationResourceLink(String destinationResourceLink) {
      this.generatedSetterHelperImpl(destinationResourceLink, DESTINATIONRESOURCELINK$8, 0, (short)1);
   }

   public String addNewDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DESTINATIONRESOURCELINK$8);
         return target;
      }
   }

   public void unsetDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONRESOURCELINK$8, 0);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
