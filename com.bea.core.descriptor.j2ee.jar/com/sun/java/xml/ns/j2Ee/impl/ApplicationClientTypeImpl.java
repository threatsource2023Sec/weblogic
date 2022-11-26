package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ApplicationClientType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DeweyVersionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.EjbRefType;
import com.sun.java.xml.ns.j2Ee.EnvEntryType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationRefType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationType;
import com.sun.java.xml.ns.j2Ee.ResourceEnvRefType;
import com.sun.java.xml.ns.j2Ee.ResourceRefType;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ApplicationClientTypeImpl extends XmlComplexContentImpl implements ApplicationClientType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName ENVENTRY$6 = new QName("http://java.sun.com/xml/ns/j2ee", "env-entry");
   private static final QName EJBREF$8 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ref");
   private static final QName SERVICEREF$10 = new QName("http://java.sun.com/xml/ns/j2ee", "service-ref");
   private static final QName RESOURCEREF$12 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-ref");
   private static final QName RESOURCEENVREF$14 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$16 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-ref");
   private static final QName CALLBACKHANDLER$18 = new QName("http://java.sun.com/xml/ns/j2ee", "callback-handler");
   private static final QName MESSAGEDESTINATION$20 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination");
   private static final QName VERSION$22 = new QName("", "version");
   private static final QName ID$24 = new QName("", "id");

   public ApplicationClientTypeImpl(SchemaType sType) {
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

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$6, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$6, i);
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
         return this.get_store().count_elements(ENVENTRY$6);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$6);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$6, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$6, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$6);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$6, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$8, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$8, i);
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
         return this.get_store().count_elements(EJBREF$8);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$8);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$8, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$8, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$8);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$8, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$10, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$10, i);
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
         return this.get_store().count_elements(SERVICEREF$10);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$10);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$10, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$10, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$10);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$10, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$12, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$12, i);
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
         return this.get_store().count_elements(RESOURCEREF$12);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$12);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$12, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$12, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$12);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$12, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$14, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$14, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$14);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$14);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$14, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$14, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$14);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$14, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$16, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$16, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$16);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$16);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$16, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$16, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$16);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$16, i);
      }
   }

   public FullyQualifiedClassType getCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CALLBACKHANDLER$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLBACKHANDLER$18) != 0;
      }
   }

   public void setCallbackHandler(FullyQualifiedClassType callbackHandler) {
      this.generatedSetterHelperImpl(callbackHandler, CALLBACKHANDLER$18, 0, (short)1);
   }

   public FullyQualifiedClassType addNewCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CALLBACKHANDLER$18);
         return target;
      }
   }

   public void unsetCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLBACKHANDLER$18, 0);
      }
   }

   public MessageDestinationType[] getMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATION$20, targetList);
         MessageDestinationType[] result = new MessageDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationType getMessageDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().find_element_user(MESSAGEDESTINATION$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATION$20);
      }
   }

   public void setMessageDestinationArray(MessageDestinationType[] messageDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationArray, MESSAGEDESTINATION$20);
   }

   public void setMessageDestinationArray(int i, MessageDestinationType messageDestination) {
      this.generatedSetterHelperImpl(messageDestination, MESSAGEDESTINATION$20, i, (short)2);
   }

   public MessageDestinationType insertNewMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().insert_element_user(MESSAGEDESTINATION$20, i);
         return target;
      }
   }

   public MessageDestinationType addNewMessageDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().add_element_user(MESSAGEDESTINATION$20);
         return target;
      }
   }

   public void removeMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATION$20, i);
      }
   }

   public BigDecimal getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$22);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$22);
         }

         return target == null ? null : target.getBigDecimalValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$22);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$22);
         }

         return target;
      }
   }

   public void setVersion(BigDecimal version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$22);
         }

         target.setBigDecimalValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$22);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$22);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$24);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$24);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$24) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$24);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$24);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$24);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$24);
      }
   }
}
