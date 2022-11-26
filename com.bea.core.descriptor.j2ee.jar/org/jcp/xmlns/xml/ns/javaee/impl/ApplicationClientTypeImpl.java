package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AdministeredObjectType;
import org.jcp.xmlns.xml.ns.javaee.ApplicationClientType;
import org.jcp.xmlns.xml.ns.javaee.ConnectionFactoryResourceType;
import org.jcp.xmlns.xml.ns.javaee.DataSourceType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DeweyVersionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefType;
import org.jcp.xmlns.xml.ns.javaee.EnvEntryType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.JmsConnectionFactoryType;
import org.jcp.xmlns.xml.ns.javaee.JmsDestinationType;
import org.jcp.xmlns.xml.ns.javaee.LifecycleCallbackType;
import org.jcp.xmlns.xml.ns.javaee.MailSessionType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationRefType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceUnitRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceEnvRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceRefType;
import org.jcp.xmlns.xml.ns.javaee.ServiceRefType;
import org.jcp.xmlns.xml.ns.javaee.String;

public class ApplicationClientTypeImpl extends XmlComplexContentImpl implements ApplicationClientType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULENAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "module-name");
   private static final QName DESCRIPTION$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName ENVENTRY$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "env-entry");
   private static final QName EJBREF$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-ref");
   private static final QName SERVICEREF$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-ref");
   private static final QName RESOURCEREF$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-ref");
   private static final QName RESOURCEENVREF$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-ref");
   private static final QName PERSISTENCEUNITREF$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-unit-ref");
   private static final QName POSTCONSTRUCT$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "post-construct");
   private static final QName PREDESTROY$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "pre-destroy");
   private static final QName CALLBACKHANDLER$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "callback-handler");
   private static final QName MESSAGEDESTINATION$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination");
   private static final QName DATASOURCE$30 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "data-source");
   private static final QName JMSCONNECTIONFACTORY$32 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-connection-factory");
   private static final QName JMSDESTINATION$34 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-destination");
   private static final QName MAILSESSION$36 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mail-session");
   private static final QName CONNECTIONFACTORY$38 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "connection-factory");
   private static final QName ADMINISTEREDOBJECT$40 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "administered-object");
   private static final QName VERSION$42 = new QName("", "version");
   private static final QName METADATACOMPLETE$44 = new QName("", "metadata-complete");
   private static final QName ID$46 = new QName("", "id");

   public ApplicationClientTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MODULENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULENAME$0) != 0;
      }
   }

   public void setModuleName(String moduleName) {
      this.generatedSetterHelperImpl(moduleName, MODULENAME$0, 0, (short)1);
   }

   public String addNewModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MODULENAME$0);
         return target;
      }
   }

   public void unsetModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULENAME$0, 0);
      }
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$2, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$2, i);
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
         return this.get_store().count_elements(DESCRIPTION$2);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$2);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$2, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$2, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$2);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$2, i);
      }
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$4, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$4, i);
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
         return this.get_store().count_elements(DISPLAYNAME$4);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$4);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$4, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$4, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$4);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$4, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$6, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$6, i);
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
         return this.get_store().count_elements(ICON$6);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$6);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$6, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$6, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$6);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$6, i);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$8, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$8, i);
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
         return this.get_store().count_elements(ENVENTRY$8);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$8);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$8, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$8, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$8);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$8, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$10, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$10, i);
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
         return this.get_store().count_elements(EJBREF$10);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$10);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$10, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$10, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$10);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$10, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$12, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$12, i);
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
         return this.get_store().count_elements(SERVICEREF$12);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$12);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$12, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$12, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$12);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$12, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$14, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$14, i);
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
         return this.get_store().count_elements(RESOURCEREF$14);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$14);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$14, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$14, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$14);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$14, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$16, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$16, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$16);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$16);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$16, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$16, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$16);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$16, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$18, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$18, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$18);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$18);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$18, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$18, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$18);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$18, i);
      }
   }

   public PersistenceUnitRefType[] getPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITREF$20, targetList);
         PersistenceUnitRefType[] result = new PersistenceUnitRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitRefType getPersistenceUnitRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().find_element_user(PERSISTENCEUNITREF$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUNITREF$20);
      }
   }

   public void setPersistenceUnitRefArray(PersistenceUnitRefType[] persistenceUnitRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitRefArray, PERSISTENCEUNITREF$20);
   }

   public void setPersistenceUnitRefArray(int i, PersistenceUnitRefType persistenceUnitRef) {
      this.generatedSetterHelperImpl(persistenceUnitRef, PERSISTENCEUNITREF$20, i, (short)2);
   }

   public PersistenceUnitRefType insertNewPersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().insert_element_user(PERSISTENCEUNITREF$20, i);
         return target;
      }
   }

   public PersistenceUnitRefType addNewPersistenceUnitRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().add_element_user(PERSISTENCEUNITREF$20);
         return target;
      }
   }

   public void removePersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITREF$20, i);
      }
   }

   public LifecycleCallbackType[] getPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTCONSTRUCT$22, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTCONSTRUCT$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POSTCONSTRUCT$22);
      }
   }

   public void setPostConstructArray(LifecycleCallbackType[] postConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(postConstructArray, POSTCONSTRUCT$22);
   }

   public void setPostConstructArray(int i, LifecycleCallbackType postConstruct) {
      this.generatedSetterHelperImpl(postConstruct, POSTCONSTRUCT$22, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTCONSTRUCT$22, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTCONSTRUCT$22);
         return target;
      }
   }

   public void removePostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTCONSTRUCT$22, i);
      }
   }

   public LifecycleCallbackType[] getPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREDESTROY$24, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPreDestroyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREDESTROY$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREDESTROY$24);
      }
   }

   public void setPreDestroyArray(LifecycleCallbackType[] preDestroyArray) {
      this.check_orphaned();
      this.arraySetterHelper(preDestroyArray, PREDESTROY$24);
   }

   public void setPreDestroyArray(int i, LifecycleCallbackType preDestroy) {
      this.generatedSetterHelperImpl(preDestroy, PREDESTROY$24, i, (short)2);
   }

   public LifecycleCallbackType insertNewPreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREDESTROY$24, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPreDestroy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREDESTROY$24);
         return target;
      }
   }

   public void removePreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREDESTROY$24, i);
      }
   }

   public FullyQualifiedClassType getCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CALLBACKHANDLER$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLBACKHANDLER$26) != 0;
      }
   }

   public void setCallbackHandler(FullyQualifiedClassType callbackHandler) {
      this.generatedSetterHelperImpl(callbackHandler, CALLBACKHANDLER$26, 0, (short)1);
   }

   public FullyQualifiedClassType addNewCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CALLBACKHANDLER$26);
         return target;
      }
   }

   public void unsetCallbackHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLBACKHANDLER$26, 0);
      }
   }

   public MessageDestinationType[] getMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATION$28, targetList);
         MessageDestinationType[] result = new MessageDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationType getMessageDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().find_element_user(MESSAGEDESTINATION$28, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATION$28);
      }
   }

   public void setMessageDestinationArray(MessageDestinationType[] messageDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationArray, MESSAGEDESTINATION$28);
   }

   public void setMessageDestinationArray(int i, MessageDestinationType messageDestination) {
      this.generatedSetterHelperImpl(messageDestination, MESSAGEDESTINATION$28, i, (short)2);
   }

   public MessageDestinationType insertNewMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().insert_element_user(MESSAGEDESTINATION$28, i);
         return target;
      }
   }

   public MessageDestinationType addNewMessageDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().add_element_user(MESSAGEDESTINATION$28);
         return target;
      }
   }

   public void removeMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATION$28, i);
      }
   }

   public DataSourceType[] getDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATASOURCE$30, targetList);
         DataSourceType[] result = new DataSourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DataSourceType getDataSourceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().find_element_user(DATASOURCE$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATASOURCE$30);
      }
   }

   public void setDataSourceArray(DataSourceType[] dataSourceArray) {
      this.check_orphaned();
      this.arraySetterHelper(dataSourceArray, DATASOURCE$30);
   }

   public void setDataSourceArray(int i, DataSourceType dataSource) {
      this.generatedSetterHelperImpl(dataSource, DATASOURCE$30, i, (short)2);
   }

   public DataSourceType insertNewDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().insert_element_user(DATASOURCE$30, i);
         return target;
      }
   }

   public DataSourceType addNewDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().add_element_user(DATASOURCE$30);
         return target;
      }
   }

   public void removeDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCE$30, i);
      }
   }

   public JmsConnectionFactoryType[] getJmsConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSCONNECTIONFACTORY$32, targetList);
         JmsConnectionFactoryType[] result = new JmsConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsConnectionFactoryType getJmsConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().find_element_user(JMSCONNECTIONFACTORY$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJmsConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSCONNECTIONFACTORY$32);
      }
   }

   public void setJmsConnectionFactoryArray(JmsConnectionFactoryType[] jmsConnectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsConnectionFactoryArray, JMSCONNECTIONFACTORY$32);
   }

   public void setJmsConnectionFactoryArray(int i, JmsConnectionFactoryType jmsConnectionFactory) {
      this.generatedSetterHelperImpl(jmsConnectionFactory, JMSCONNECTIONFACTORY$32, i, (short)2);
   }

   public JmsConnectionFactoryType insertNewJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().insert_element_user(JMSCONNECTIONFACTORY$32, i);
         return target;
      }
   }

   public JmsConnectionFactoryType addNewJmsConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().add_element_user(JMSCONNECTIONFACTORY$32);
         return target;
      }
   }

   public void removeJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSCONNECTIONFACTORY$32, i);
      }
   }

   public JmsDestinationType[] getJmsDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSDESTINATION$34, targetList);
         JmsDestinationType[] result = new JmsDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsDestinationType getJmsDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().find_element_user(JMSDESTINATION$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJmsDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSDESTINATION$34);
      }
   }

   public void setJmsDestinationArray(JmsDestinationType[] jmsDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsDestinationArray, JMSDESTINATION$34);
   }

   public void setJmsDestinationArray(int i, JmsDestinationType jmsDestination) {
      this.generatedSetterHelperImpl(jmsDestination, JMSDESTINATION$34, i, (short)2);
   }

   public JmsDestinationType insertNewJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().insert_element_user(JMSDESTINATION$34, i);
         return target;
      }
   }

   public JmsDestinationType addNewJmsDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().add_element_user(JMSDESTINATION$34);
         return target;
      }
   }

   public void removeJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSDESTINATION$34, i);
      }
   }

   public MailSessionType[] getMailSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAILSESSION$36, targetList);
         MailSessionType[] result = new MailSessionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MailSessionType getMailSessionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().find_element_user(MAILSESSION$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMailSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAILSESSION$36);
      }
   }

   public void setMailSessionArray(MailSessionType[] mailSessionArray) {
      this.check_orphaned();
      this.arraySetterHelper(mailSessionArray, MAILSESSION$36);
   }

   public void setMailSessionArray(int i, MailSessionType mailSession) {
      this.generatedSetterHelperImpl(mailSession, MAILSESSION$36, i, (short)2);
   }

   public MailSessionType insertNewMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().insert_element_user(MAILSESSION$36, i);
         return target;
      }
   }

   public MailSessionType addNewMailSession() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().add_element_user(MAILSESSION$36);
         return target;
      }
   }

   public void removeMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAILSESSION$36, i);
      }
   }

   public ConnectionFactoryResourceType[] getConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONFACTORY$38, targetList);
         ConnectionFactoryResourceType[] result = new ConnectionFactoryResourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionFactoryResourceType getConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().find_element_user(CONNECTIONFACTORY$38, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORY$38);
      }
   }

   public void setConnectionFactoryArray(ConnectionFactoryResourceType[] connectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionFactoryArray, CONNECTIONFACTORY$38);
   }

   public void setConnectionFactoryArray(int i, ConnectionFactoryResourceType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$38, i, (short)2);
   }

   public ConnectionFactoryResourceType insertNewConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().insert_element_user(CONNECTIONFACTORY$38, i);
         return target;
      }
   }

   public ConnectionFactoryResourceType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().add_element_user(CONNECTIONFACTORY$38);
         return target;
      }
   }

   public void removeConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY$38, i);
      }
   }

   public AdministeredObjectType[] getAdministeredObjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINISTEREDOBJECT$40, targetList);
         AdministeredObjectType[] result = new AdministeredObjectType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdministeredObjectType getAdministeredObjectArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().find_element_user(ADMINISTEREDOBJECT$40, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAdministeredObjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINISTEREDOBJECT$40);
      }
   }

   public void setAdministeredObjectArray(AdministeredObjectType[] administeredObjectArray) {
      this.check_orphaned();
      this.arraySetterHelper(administeredObjectArray, ADMINISTEREDOBJECT$40);
   }

   public void setAdministeredObjectArray(int i, AdministeredObjectType administeredObject) {
      this.generatedSetterHelperImpl(administeredObject, ADMINISTEREDOBJECT$40, i, (short)2);
   }

   public AdministeredObjectType insertNewAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().insert_element_user(ADMINISTEREDOBJECT$40, i);
         return target;
      }
   }

   public AdministeredObjectType addNewAdministeredObject() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().add_element_user(ADMINISTEREDOBJECT$40);
         return target;
      }
   }

   public void removeAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINISTEREDOBJECT$40, i);
      }
   }

   public java.lang.String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$42);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$42);
         }

         return target == null ? null : target.getStringValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$42);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$42);
         }

         return target;
      }
   }

   public void setVersion(java.lang.String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$42);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$42);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$42);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$42);
         }

         target.set(version);
      }
   }

   public boolean getMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$44);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$44);
         return target;
      }
   }

   public boolean isSetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(METADATACOMPLETE$44) != null;
      }
   }

   public void setMetadataComplete(boolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$44);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(METADATACOMPLETE$44);
         }

         target.setBooleanValue(metadataComplete);
      }
   }

   public void xsetMetadataComplete(XmlBoolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$44);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(METADATACOMPLETE$44);
         }

         target.set(metadataComplete);
      }
   }

   public void unsetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(METADATACOMPLETE$44);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$46);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$46);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$46) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$46);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$46);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$46);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$46);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$46);
      }
   }
}
