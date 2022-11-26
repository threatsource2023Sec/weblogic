package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.CdiDescriptorType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.EjbReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.MessageDestinationDescriptorType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.ResourceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.ResourceEnvDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.ServiceReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.WeblogicApplicationClientType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicApplicationClientTypeImpl extends XmlComplexContentImpl implements WeblogicApplicationClientType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVERAPPLICATIONNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "server-application-name");
   private static final QName RESOURCEDESCRIPTION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "resource-description");
   private static final QName RESOURCEENVDESCRIPTION$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "resource-env-description");
   private static final QName EJBREFERENCEDESCRIPTION$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "ejb-reference-description");
   private static final QName SERVICEREFERENCEDESCRIPTION$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "service-reference-description");
   private static final QName MESSAGEDESTINATIONDESCRIPTOR$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "message-destination-descriptor");
   private static final QName CDIDESCRIPTOR$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cdi-descriptor");
   private static final QName ID$14 = new QName("", "id");
   private static final QName VERSION$16 = new QName("", "version");

   public WeblogicApplicationClientTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getServerApplicationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SERVERAPPLICATIONNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetServerApplicationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SERVERAPPLICATIONNAME$0, 0);
         return target;
      }
   }

   public boolean isSetServerApplicationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVERAPPLICATIONNAME$0) != 0;
      }
   }

   public void setServerApplicationName(String serverApplicationName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SERVERAPPLICATIONNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SERVERAPPLICATIONNAME$0);
         }

         target.setStringValue(serverApplicationName);
      }
   }

   public void xsetServerApplicationName(XmlString serverApplicationName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SERVERAPPLICATIONNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SERVERAPPLICATIONNAME$0);
         }

         target.set(serverApplicationName);
      }
   }

   public void unsetServerApplicationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVERAPPLICATIONNAME$0, 0);
      }
   }

   public ResourceDescriptionType[] getResourceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEDESCRIPTION$2, targetList);
         ResourceDescriptionType[] result = new ResourceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceDescriptionType getResourceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().find_element_user(RESOURCEDESCRIPTION$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEDESCRIPTION$2);
      }
   }

   public void setResourceDescriptionArray(ResourceDescriptionType[] resourceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceDescriptionArray, RESOURCEDESCRIPTION$2);
   }

   public void setResourceDescriptionArray(int i, ResourceDescriptionType resourceDescription) {
      this.generatedSetterHelperImpl(resourceDescription, RESOURCEDESCRIPTION$2, i, (short)2);
   }

   public ResourceDescriptionType insertNewResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().insert_element_user(RESOURCEDESCRIPTION$2, i);
         return target;
      }
   }

   public ResourceDescriptionType addNewResourceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().add_element_user(RESOURCEDESCRIPTION$2);
         return target;
      }
   }

   public void removeResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEDESCRIPTION$2, i);
      }
   }

   public ResourceEnvDescriptionType[] getResourceEnvDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVDESCRIPTION$4, targetList);
         ResourceEnvDescriptionType[] result = new ResourceEnvDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvDescriptionType getResourceEnvDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().find_element_user(RESOURCEENVDESCRIPTION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceEnvDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEENVDESCRIPTION$4);
      }
   }

   public void setResourceEnvDescriptionArray(ResourceEnvDescriptionType[] resourceEnvDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvDescriptionArray, RESOURCEENVDESCRIPTION$4);
   }

   public void setResourceEnvDescriptionArray(int i, ResourceEnvDescriptionType resourceEnvDescription) {
      this.generatedSetterHelperImpl(resourceEnvDescription, RESOURCEENVDESCRIPTION$4, i, (short)2);
   }

   public ResourceEnvDescriptionType insertNewResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().insert_element_user(RESOURCEENVDESCRIPTION$4, i);
         return target;
      }
   }

   public ResourceEnvDescriptionType addNewResourceEnvDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().add_element_user(RESOURCEENVDESCRIPTION$4);
         return target;
      }
   }

   public void removeResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVDESCRIPTION$4, i);
      }
   }

   public EjbReferenceDescriptionType[] getEjbReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREFERENCEDESCRIPTION$6, targetList);
         EjbReferenceDescriptionType[] result = new EjbReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbReferenceDescriptionType getEjbReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().find_element_user(EJBREFERENCEDESCRIPTION$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBREFERENCEDESCRIPTION$6);
      }
   }

   public void setEjbReferenceDescriptionArray(EjbReferenceDescriptionType[] ejbReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbReferenceDescriptionArray, EJBREFERENCEDESCRIPTION$6);
   }

   public void setEjbReferenceDescriptionArray(int i, EjbReferenceDescriptionType ejbReferenceDescription) {
      this.generatedSetterHelperImpl(ejbReferenceDescription, EJBREFERENCEDESCRIPTION$6, i, (short)2);
   }

   public EjbReferenceDescriptionType insertNewEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().insert_element_user(EJBREFERENCEDESCRIPTION$6, i);
         return target;
      }
   }

   public EjbReferenceDescriptionType addNewEjbReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().add_element_user(EJBREFERENCEDESCRIPTION$6);
         return target;
      }
   }

   public void removeEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREFERENCEDESCRIPTION$6, i);
      }
   }

   public ServiceReferenceDescriptionType[] getServiceReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREFERENCEDESCRIPTION$8, targetList);
         ServiceReferenceDescriptionType[] result = new ServiceReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceReferenceDescriptionType getServiceReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().find_element_user(SERVICEREFERENCEDESCRIPTION$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEREFERENCEDESCRIPTION$8);
      }
   }

   public void setServiceReferenceDescriptionArray(ServiceReferenceDescriptionType[] serviceReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceReferenceDescriptionArray, SERVICEREFERENCEDESCRIPTION$8);
   }

   public void setServiceReferenceDescriptionArray(int i, ServiceReferenceDescriptionType serviceReferenceDescription) {
      this.generatedSetterHelperImpl(serviceReferenceDescription, SERVICEREFERENCEDESCRIPTION$8, i, (short)2);
   }

   public ServiceReferenceDescriptionType insertNewServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().insert_element_user(SERVICEREFERENCEDESCRIPTION$8, i);
         return target;
      }
   }

   public ServiceReferenceDescriptionType addNewServiceReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().add_element_user(SERVICEREFERENCEDESCRIPTION$8);
         return target;
      }
   }

   public void removeServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREFERENCEDESCRIPTION$8, i);
      }
   }

   public MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONDESCRIPTOR$10, targetList);
         MessageDestinationDescriptorType[] result = new MessageDestinationDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().find_element_user(MESSAGEDESTINATIONDESCRIPTOR$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONDESCRIPTOR$10);
      }
   }

   public void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] messageDestinationDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationDescriptorArray, MESSAGEDESTINATIONDESCRIPTOR$10);
   }

   public void setMessageDestinationDescriptorArray(int i, MessageDestinationDescriptorType messageDestinationDescriptor) {
      this.generatedSetterHelperImpl(messageDestinationDescriptor, MESSAGEDESTINATIONDESCRIPTOR$10, i, (short)2);
   }

   public MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().insert_element_user(MESSAGEDESTINATIONDESCRIPTOR$10, i);
         return target;
      }
   }

   public MessageDestinationDescriptorType addNewMessageDestinationDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().add_element_user(MESSAGEDESTINATIONDESCRIPTOR$10);
         return target;
      }
   }

   public void removeMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONDESCRIPTOR$10, i);
      }
   }

   public CdiDescriptorType getCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().find_element_user(CDIDESCRIPTOR$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CDIDESCRIPTOR$12) != 0;
      }
   }

   public void setCdiDescriptor(CdiDescriptorType cdiDescriptor) {
      this.generatedSetterHelperImpl(cdiDescriptor, CDIDESCRIPTOR$12, 0, (short)1);
   }

   public CdiDescriptorType addNewCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().add_element_user(CDIDESCRIPTOR$12);
         return target;
      }
   }

   public void unsetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CDIDESCRIPTOR$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$16);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$16) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$16);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$16);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$16);
      }
   }
}
