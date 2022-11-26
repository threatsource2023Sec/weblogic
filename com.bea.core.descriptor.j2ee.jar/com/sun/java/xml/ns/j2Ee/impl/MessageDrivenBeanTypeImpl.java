package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ActivationConfigType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.EjbClassType;
import com.sun.java.xml.ns.j2Ee.EjbLocalRefType;
import com.sun.java.xml.ns.j2Ee.EjbNameType;
import com.sun.java.xml.ns.j2Ee.EjbRefType;
import com.sun.java.xml.ns.j2Ee.EnvEntryType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationLinkType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationRefType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationTypeType;
import com.sun.java.xml.ns.j2Ee.MessageDrivenBeanType;
import com.sun.java.xml.ns.j2Ee.ResourceEnvRefType;
import com.sun.java.xml.ns.j2Ee.ResourceRefType;
import com.sun.java.xml.ns.j2Ee.SecurityIdentityType;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import com.sun.java.xml.ns.j2Ee.TransactionTypeType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MessageDrivenBeanTypeImpl extends XmlComplexContentImpl implements MessageDrivenBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName EJBNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-name");
   private static final QName EJBCLASS$8 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-class");
   private static final QName MESSAGINGTYPE$10 = new QName("http://java.sun.com/xml/ns/j2ee", "messaging-type");
   private static final QName TRANSACTIONTYPE$12 = new QName("http://java.sun.com/xml/ns/j2ee", "transaction-type");
   private static final QName MESSAGEDESTINATIONTYPE$14 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-type");
   private static final QName MESSAGEDESTINATIONLINK$16 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-link");
   private static final QName ACTIVATIONCONFIG$18 = new QName("http://java.sun.com/xml/ns/j2ee", "activation-config");
   private static final QName ENVENTRY$20 = new QName("http://java.sun.com/xml/ns/j2ee", "env-entry");
   private static final QName EJBREF$22 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ref");
   private static final QName EJBLOCALREF$24 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-local-ref");
   private static final QName SERVICEREF$26 = new QName("http://java.sun.com/xml/ns/j2ee", "service-ref");
   private static final QName RESOURCEREF$28 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-ref");
   private static final QName RESOURCEENVREF$30 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$32 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-ref");
   private static final QName SECURITYIDENTITY$34 = new QName("http://java.sun.com/xml/ns/j2ee", "security-identity");
   private static final QName ID$36 = new QName("", "id");

   public MessageDrivenBeanTypeImpl(SchemaType sType) {
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

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$2, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$2);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$2, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$4, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$4);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$4, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, i);
      }
   }

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$6, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$6);
         return target;
      }
   }

   public EjbClassType getEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().find_element_user(EJBCLASS$8, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbClass(EjbClassType ejbClass) {
      this.generatedSetterHelperImpl(ejbClass, EJBCLASS$8, 0, (short)1);
   }

   public EjbClassType addNewEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().add_element_user(EJBCLASS$8);
         return target;
      }
   }

   public FullyQualifiedClassType getMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(MESSAGINGTYPE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGINGTYPE$10) != 0;
      }
   }

   public void setMessagingType(FullyQualifiedClassType messagingType) {
      this.generatedSetterHelperImpl(messagingType, MESSAGINGTYPE$10, 0, (short)1);
   }

   public FullyQualifiedClassType addNewMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(MESSAGINGTYPE$10);
         return target;
      }
   }

   public void unsetMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGINGTYPE$10, 0);
      }
   }

   public TransactionTypeType getTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionTypeType target = null;
         target = (TransactionTypeType)this.get_store().find_element_user(TRANSACTIONTYPE$12, 0);
         return target == null ? null : target;
      }
   }

   public void setTransactionType(TransactionTypeType transactionType) {
      this.generatedSetterHelperImpl(transactionType, TRANSACTIONTYPE$12, 0, (short)1);
   }

   public TransactionTypeType addNewTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionTypeType target = null;
         target = (TransactionTypeType)this.get_store().add_element_user(TRANSACTIONTYPE$12);
         return target;
      }
   }

   public MessageDestinationTypeType getMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().find_element_user(MESSAGEDESTINATIONTYPE$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONTYPE$14) != 0;
      }
   }

   public void setMessageDestinationType(MessageDestinationTypeType messageDestinationType) {
      this.generatedSetterHelperImpl(messageDestinationType, MESSAGEDESTINATIONTYPE$14, 0, (short)1);
   }

   public MessageDestinationTypeType addNewMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().add_element_user(MESSAGEDESTINATIONTYPE$14);
         return target;
      }
   }

   public void unsetMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONTYPE$14, 0);
      }
   }

   public MessageDestinationLinkType getMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().find_element_user(MESSAGEDESTINATIONLINK$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONLINK$16) != 0;
      }
   }

   public void setMessageDestinationLink(MessageDestinationLinkType messageDestinationLink) {
      this.generatedSetterHelperImpl(messageDestinationLink, MESSAGEDESTINATIONLINK$16, 0, (short)1);
   }

   public MessageDestinationLinkType addNewMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().add_element_user(MESSAGEDESTINATIONLINK$16);
         return target;
      }
   }

   public void unsetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONLINK$16, 0);
      }
   }

   public ActivationConfigType getActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigType target = null;
         target = (ActivationConfigType)this.get_store().find_element_user(ACTIVATIONCONFIG$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACTIVATIONCONFIG$18) != 0;
      }
   }

   public void setActivationConfig(ActivationConfigType activationConfig) {
      this.generatedSetterHelperImpl(activationConfig, ACTIVATIONCONFIG$18, 0, (short)1);
   }

   public ActivationConfigType addNewActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigType target = null;
         target = (ActivationConfigType)this.get_store().add_element_user(ACTIVATIONCONFIG$18);
         return target;
      }
   }

   public void unsetActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACTIVATIONCONFIG$18, 0);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$20, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENVENTRY$20);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$20);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$20, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$20, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$20);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$20, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$22, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBREF$22);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$22);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$22, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$22, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$22);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$22, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$24, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBLOCALREF$24);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$24);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$24, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$24, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$24);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$24, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$26, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEREF$26);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$26);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$26, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$26, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$26);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$26, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$28, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEREF$28);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$28);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$28, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$28, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$28);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$28, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$30, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEENVREF$30);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$30);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$30, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$30, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$30);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$30, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$32, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$32);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$32);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$32, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$32, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$32);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$32, i);
      }
   }

   public SecurityIdentityType getSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().find_element_user(SECURITYIDENTITY$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYIDENTITY$34) != 0;
      }
   }

   public void setSecurityIdentity(SecurityIdentityType securityIdentity) {
      this.generatedSetterHelperImpl(securityIdentity, SECURITYIDENTITY$34, 0, (short)1);
   }

   public SecurityIdentityType addNewSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().add_element_user(SECURITYIDENTITY$34);
         return target;
      }
   }

   public void unsetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYIDENTITY$34, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$36);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$36);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$36) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$36);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$36);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$36);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$36);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$36);
      }
   }
}
