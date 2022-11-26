package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CreateAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.DispatchPolicyType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EjbReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IiopSecurityDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.JndiBindingType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MessageDrivenDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PassivateAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.RemoveAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ResourceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ResourceEnvDescriptionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.RunAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ServiceReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SingletonSessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatefulSessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatelessSessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TransactionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicEnterpriseBeanType;
import com.sun.java.xml.ns.javaee.EjbNameType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicEnterpriseBeanTypeImpl extends XmlComplexContentImpl implements WeblogicEnterpriseBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName EJBNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "ejb-name");
   private static final QName ENTITYDESCRIPTOR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "entity-descriptor");
   private static final QName STATELESSSESSIONDESCRIPTOR$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateless-session-descriptor");
   private static final QName STATEFULSESSIONDESCRIPTOR$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateful-session-descriptor");
   private static final QName SINGLETONSESSIONDESCRIPTOR$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "singleton-session-descriptor");
   private static final QName MESSAGEDRIVENDESCRIPTOR$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "message-driven-descriptor");
   private static final QName TRANSACTIONDESCRIPTOR$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "transaction-descriptor");
   private static final QName IIOPSECURITYDESCRIPTOR$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "iiop-security-descriptor");
   private static final QName RESOURCEDESCRIPTION$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "resource-description");
   private static final QName RESOURCEENVDESCRIPTION$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "resource-env-description");
   private static final QName EJBREFERENCEDESCRIPTION$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "ejb-reference-description");
   private static final QName SERVICEREFERENCEDESCRIPTION$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "service-reference-description");
   private static final QName ENABLECALLBYREFERENCE$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "enable-call-by-reference");
   private static final QName NETWORKACCESSPOINT$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "network-access-point");
   private static final QName CLIENTSONSAMESERVER$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "clients-on-same-server");
   private static final QName RUNASPRINCIPALNAME$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "run-as-principal-name");
   private static final QName CREATEASPRINCIPALNAME$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "create-as-principal-name");
   private static final QName REMOVEASPRINCIPALNAME$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "remove-as-principal-name");
   private static final QName PASSIVATEASPRINCIPALNAME$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "passivate-as-principal-name");
   private static final QName JNDINAME$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "jndi-name");
   private static final QName LOCALJNDINAME$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "local-jndi-name");
   private static final QName DISPATCHPOLICY$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "dispatch-policy");
   private static final QName REMOTECLIENTTIMEOUT$44 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "remote-client-timeout");
   private static final QName STICKTOFIRSTSERVER$46 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stick-to-first-server");
   private static final QName JNDIBINDING$48 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "jndi-binding");
   private static final QName ID$50 = new QName("", "id");

   public WeblogicEnterpriseBeanTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$0, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$0);
         return target;
      }
   }

   public EntityDescriptorType getEntityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityDescriptorType target = null;
         target = (EntityDescriptorType)this.get_store().find_element_user(ENTITYDESCRIPTOR$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEntityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYDESCRIPTOR$2) != 0;
      }
   }

   public void setEntityDescriptor(EntityDescriptorType entityDescriptor) {
      this.generatedSetterHelperImpl(entityDescriptor, ENTITYDESCRIPTOR$2, 0, (short)1);
   }

   public EntityDescriptorType addNewEntityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityDescriptorType target = null;
         target = (EntityDescriptorType)this.get_store().add_element_user(ENTITYDESCRIPTOR$2);
         return target;
      }
   }

   public void unsetEntityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYDESCRIPTOR$2, 0);
      }
   }

   public StatelessSessionDescriptorType getStatelessSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatelessSessionDescriptorType target = null;
         target = (StatelessSessionDescriptorType)this.get_store().find_element_user(STATELESSSESSIONDESCRIPTOR$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatelessSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATELESSSESSIONDESCRIPTOR$4) != 0;
      }
   }

   public void setStatelessSessionDescriptor(StatelessSessionDescriptorType statelessSessionDescriptor) {
      this.generatedSetterHelperImpl(statelessSessionDescriptor, STATELESSSESSIONDESCRIPTOR$4, 0, (short)1);
   }

   public StatelessSessionDescriptorType addNewStatelessSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatelessSessionDescriptorType target = null;
         target = (StatelessSessionDescriptorType)this.get_store().add_element_user(STATELESSSESSIONDESCRIPTOR$4);
         return target;
      }
   }

   public void unsetStatelessSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATELESSSESSIONDESCRIPTOR$4, 0);
      }
   }

   public StatefulSessionDescriptorType getStatefulSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulSessionDescriptorType target = null;
         target = (StatefulSessionDescriptorType)this.get_store().find_element_user(STATEFULSESSIONDESCRIPTOR$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatefulSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATEFULSESSIONDESCRIPTOR$6) != 0;
      }
   }

   public void setStatefulSessionDescriptor(StatefulSessionDescriptorType statefulSessionDescriptor) {
      this.generatedSetterHelperImpl(statefulSessionDescriptor, STATEFULSESSIONDESCRIPTOR$6, 0, (short)1);
   }

   public StatefulSessionDescriptorType addNewStatefulSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulSessionDescriptorType target = null;
         target = (StatefulSessionDescriptorType)this.get_store().add_element_user(STATEFULSESSIONDESCRIPTOR$6);
         return target;
      }
   }

   public void unsetStatefulSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATEFULSESSIONDESCRIPTOR$6, 0);
      }
   }

   public SingletonSessionDescriptorType getSingletonSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonSessionDescriptorType target = null;
         target = (SingletonSessionDescriptorType)this.get_store().find_element_user(SINGLETONSESSIONDESCRIPTOR$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSingletonSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONSESSIONDESCRIPTOR$8) != 0;
      }
   }

   public void setSingletonSessionDescriptor(SingletonSessionDescriptorType singletonSessionDescriptor) {
      this.generatedSetterHelperImpl(singletonSessionDescriptor, SINGLETONSESSIONDESCRIPTOR$8, 0, (short)1);
   }

   public SingletonSessionDescriptorType addNewSingletonSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonSessionDescriptorType target = null;
         target = (SingletonSessionDescriptorType)this.get_store().add_element_user(SINGLETONSESSIONDESCRIPTOR$8);
         return target;
      }
   }

   public void unsetSingletonSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONSESSIONDESCRIPTOR$8, 0);
      }
   }

   public MessageDrivenDescriptorType getMessageDrivenDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDrivenDescriptorType target = null;
         target = (MessageDrivenDescriptorType)this.get_store().find_element_user(MESSAGEDRIVENDESCRIPTOR$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDrivenDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDRIVENDESCRIPTOR$10) != 0;
      }
   }

   public void setMessageDrivenDescriptor(MessageDrivenDescriptorType messageDrivenDescriptor) {
      this.generatedSetterHelperImpl(messageDrivenDescriptor, MESSAGEDRIVENDESCRIPTOR$10, 0, (short)1);
   }

   public MessageDrivenDescriptorType addNewMessageDrivenDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDrivenDescriptorType target = null;
         target = (MessageDrivenDescriptorType)this.get_store().add_element_user(MESSAGEDRIVENDESCRIPTOR$10);
         return target;
      }
   }

   public void unsetMessageDrivenDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDRIVENDESCRIPTOR$10, 0);
      }
   }

   public TransactionDescriptorType getTransactionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionDescriptorType target = null;
         target = (TransactionDescriptorType)this.get_store().find_element_user(TRANSACTIONDESCRIPTOR$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONDESCRIPTOR$12) != 0;
      }
   }

   public void setTransactionDescriptor(TransactionDescriptorType transactionDescriptor) {
      this.generatedSetterHelperImpl(transactionDescriptor, TRANSACTIONDESCRIPTOR$12, 0, (short)1);
   }

   public TransactionDescriptorType addNewTransactionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionDescriptorType target = null;
         target = (TransactionDescriptorType)this.get_store().add_element_user(TRANSACTIONDESCRIPTOR$12);
         return target;
      }
   }

   public void unsetTransactionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONDESCRIPTOR$12, 0);
      }
   }

   public IiopSecurityDescriptorType getIiopSecurityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IiopSecurityDescriptorType target = null;
         target = (IiopSecurityDescriptorType)this.get_store().find_element_user(IIOPSECURITYDESCRIPTOR$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIiopSecurityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IIOPSECURITYDESCRIPTOR$14) != 0;
      }
   }

   public void setIiopSecurityDescriptor(IiopSecurityDescriptorType iiopSecurityDescriptor) {
      this.generatedSetterHelperImpl(iiopSecurityDescriptor, IIOPSECURITYDESCRIPTOR$14, 0, (short)1);
   }

   public IiopSecurityDescriptorType addNewIiopSecurityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IiopSecurityDescriptorType target = null;
         target = (IiopSecurityDescriptorType)this.get_store().add_element_user(IIOPSECURITYDESCRIPTOR$14);
         return target;
      }
   }

   public void unsetIiopSecurityDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IIOPSECURITYDESCRIPTOR$14, 0);
      }
   }

   public ResourceDescriptionType[] getResourceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEDESCRIPTION$16, targetList);
         ResourceDescriptionType[] result = new ResourceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceDescriptionType getResourceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().find_element_user(RESOURCEDESCRIPTION$16, i);
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
         return this.get_store().count_elements(RESOURCEDESCRIPTION$16);
      }
   }

   public void setResourceDescriptionArray(ResourceDescriptionType[] resourceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceDescriptionArray, RESOURCEDESCRIPTION$16);
   }

   public void setResourceDescriptionArray(int i, ResourceDescriptionType resourceDescription) {
      this.generatedSetterHelperImpl(resourceDescription, RESOURCEDESCRIPTION$16, i, (short)2);
   }

   public ResourceDescriptionType insertNewResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().insert_element_user(RESOURCEDESCRIPTION$16, i);
         return target;
      }
   }

   public ResourceDescriptionType addNewResourceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().add_element_user(RESOURCEDESCRIPTION$16);
         return target;
      }
   }

   public void removeResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEDESCRIPTION$16, i);
      }
   }

   public ResourceEnvDescriptionType[] getResourceEnvDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVDESCRIPTION$18, targetList);
         ResourceEnvDescriptionType[] result = new ResourceEnvDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvDescriptionType getResourceEnvDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().find_element_user(RESOURCEENVDESCRIPTION$18, i);
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
         return this.get_store().count_elements(RESOURCEENVDESCRIPTION$18);
      }
   }

   public void setResourceEnvDescriptionArray(ResourceEnvDescriptionType[] resourceEnvDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvDescriptionArray, RESOURCEENVDESCRIPTION$18);
   }

   public void setResourceEnvDescriptionArray(int i, ResourceEnvDescriptionType resourceEnvDescription) {
      this.generatedSetterHelperImpl(resourceEnvDescription, RESOURCEENVDESCRIPTION$18, i, (short)2);
   }

   public ResourceEnvDescriptionType insertNewResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().insert_element_user(RESOURCEENVDESCRIPTION$18, i);
         return target;
      }
   }

   public ResourceEnvDescriptionType addNewResourceEnvDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().add_element_user(RESOURCEENVDESCRIPTION$18);
         return target;
      }
   }

   public void removeResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVDESCRIPTION$18, i);
      }
   }

   public EjbReferenceDescriptionType[] getEjbReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREFERENCEDESCRIPTION$20, targetList);
         EjbReferenceDescriptionType[] result = new EjbReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbReferenceDescriptionType getEjbReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().find_element_user(EJBREFERENCEDESCRIPTION$20, i);
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
         return this.get_store().count_elements(EJBREFERENCEDESCRIPTION$20);
      }
   }

   public void setEjbReferenceDescriptionArray(EjbReferenceDescriptionType[] ejbReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbReferenceDescriptionArray, EJBREFERENCEDESCRIPTION$20);
   }

   public void setEjbReferenceDescriptionArray(int i, EjbReferenceDescriptionType ejbReferenceDescription) {
      this.generatedSetterHelperImpl(ejbReferenceDescription, EJBREFERENCEDESCRIPTION$20, i, (short)2);
   }

   public EjbReferenceDescriptionType insertNewEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().insert_element_user(EJBREFERENCEDESCRIPTION$20, i);
         return target;
      }
   }

   public EjbReferenceDescriptionType addNewEjbReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().add_element_user(EJBREFERENCEDESCRIPTION$20);
         return target;
      }
   }

   public void removeEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREFERENCEDESCRIPTION$20, i);
      }
   }

   public ServiceReferenceDescriptionType[] getServiceReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREFERENCEDESCRIPTION$22, targetList);
         ServiceReferenceDescriptionType[] result = new ServiceReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceReferenceDescriptionType getServiceReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().find_element_user(SERVICEREFERENCEDESCRIPTION$22, i);
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
         return this.get_store().count_elements(SERVICEREFERENCEDESCRIPTION$22);
      }
   }

   public void setServiceReferenceDescriptionArray(ServiceReferenceDescriptionType[] serviceReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceReferenceDescriptionArray, SERVICEREFERENCEDESCRIPTION$22);
   }

   public void setServiceReferenceDescriptionArray(int i, ServiceReferenceDescriptionType serviceReferenceDescription) {
      this.generatedSetterHelperImpl(serviceReferenceDescription, SERVICEREFERENCEDESCRIPTION$22, i, (short)2);
   }

   public ServiceReferenceDescriptionType insertNewServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().insert_element_user(SERVICEREFERENCEDESCRIPTION$22, i);
         return target;
      }
   }

   public ServiceReferenceDescriptionType addNewServiceReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().add_element_user(SERVICEREFERENCEDESCRIPTION$22);
         return target;
      }
   }

   public void removeServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREFERENCEDESCRIPTION$22, i);
      }
   }

   public TrueFalseType getEnableCallByReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLECALLBYREFERENCE$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableCallByReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLECALLBYREFERENCE$24) != 0;
      }
   }

   public void setEnableCallByReference(TrueFalseType enableCallByReference) {
      this.generatedSetterHelperImpl(enableCallByReference, ENABLECALLBYREFERENCE$24, 0, (short)1);
   }

   public TrueFalseType addNewEnableCallByReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLECALLBYREFERENCE$24);
         return target;
      }
   }

   public void unsetEnableCallByReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLECALLBYREFERENCE$24, 0);
      }
   }

   public XsdStringType getNetworkAccessPoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(NETWORKACCESSPOINT$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetNetworkAccessPoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NETWORKACCESSPOINT$26) != 0;
      }
   }

   public void setNetworkAccessPoint(XsdStringType networkAccessPoint) {
      this.generatedSetterHelperImpl(networkAccessPoint, NETWORKACCESSPOINT$26, 0, (short)1);
   }

   public XsdStringType addNewNetworkAccessPoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(NETWORKACCESSPOINT$26);
         return target;
      }
   }

   public void unsetNetworkAccessPoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NETWORKACCESSPOINT$26, 0);
      }
   }

   public TrueFalseType getClientsOnSameServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CLIENTSONSAMESERVER$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClientsOnSameServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTSONSAMESERVER$28) != 0;
      }
   }

   public void setClientsOnSameServer(TrueFalseType clientsOnSameServer) {
      this.generatedSetterHelperImpl(clientsOnSameServer, CLIENTSONSAMESERVER$28, 0, (short)1);
   }

   public TrueFalseType addNewClientsOnSameServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CLIENTSONSAMESERVER$28);
         return target;
      }
   }

   public void unsetClientsOnSameServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTSONSAMESERVER$28, 0);
      }
   }

   public RunAsPrincipalNameType getRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsPrincipalNameType target = null;
         target = (RunAsPrincipalNameType)this.get_store().find_element_user(RUNASPRINCIPALNAME$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASPRINCIPALNAME$30) != 0;
      }
   }

   public void setRunAsPrincipalName(RunAsPrincipalNameType runAsPrincipalName) {
      this.generatedSetterHelperImpl(runAsPrincipalName, RUNASPRINCIPALNAME$30, 0, (short)1);
   }

   public RunAsPrincipalNameType addNewRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsPrincipalNameType target = null;
         target = (RunAsPrincipalNameType)this.get_store().add_element_user(RUNASPRINCIPALNAME$30);
         return target;
      }
   }

   public void unsetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASPRINCIPALNAME$30, 0);
      }
   }

   public CreateAsPrincipalNameType getCreateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CreateAsPrincipalNameType target = null;
         target = (CreateAsPrincipalNameType)this.get_store().find_element_user(CREATEASPRINCIPALNAME$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCreateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CREATEASPRINCIPALNAME$32) != 0;
      }
   }

   public void setCreateAsPrincipalName(CreateAsPrincipalNameType createAsPrincipalName) {
      this.generatedSetterHelperImpl(createAsPrincipalName, CREATEASPRINCIPALNAME$32, 0, (short)1);
   }

   public CreateAsPrincipalNameType addNewCreateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CreateAsPrincipalNameType target = null;
         target = (CreateAsPrincipalNameType)this.get_store().add_element_user(CREATEASPRINCIPALNAME$32);
         return target;
      }
   }

   public void unsetCreateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CREATEASPRINCIPALNAME$32, 0);
      }
   }

   public RemoveAsPrincipalNameType getRemoveAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoveAsPrincipalNameType target = null;
         target = (RemoveAsPrincipalNameType)this.get_store().find_element_user(REMOVEASPRINCIPALNAME$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRemoveAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOVEASPRINCIPALNAME$34) != 0;
      }
   }

   public void setRemoveAsPrincipalName(RemoveAsPrincipalNameType removeAsPrincipalName) {
      this.generatedSetterHelperImpl(removeAsPrincipalName, REMOVEASPRINCIPALNAME$34, 0, (short)1);
   }

   public RemoveAsPrincipalNameType addNewRemoveAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoveAsPrincipalNameType target = null;
         target = (RemoveAsPrincipalNameType)this.get_store().add_element_user(REMOVEASPRINCIPALNAME$34);
         return target;
      }
   }

   public void unsetRemoveAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOVEASPRINCIPALNAME$34, 0);
      }
   }

   public PassivateAsPrincipalNameType getPassivateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PassivateAsPrincipalNameType target = null;
         target = (PassivateAsPrincipalNameType)this.get_store().find_element_user(PASSIVATEASPRINCIPALNAME$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPassivateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSIVATEASPRINCIPALNAME$36) != 0;
      }
   }

   public void setPassivateAsPrincipalName(PassivateAsPrincipalNameType passivateAsPrincipalName) {
      this.generatedSetterHelperImpl(passivateAsPrincipalName, PASSIVATEASPRINCIPALNAME$36, 0, (short)1);
   }

   public PassivateAsPrincipalNameType addNewPassivateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PassivateAsPrincipalNameType target = null;
         target = (PassivateAsPrincipalNameType)this.get_store().add_element_user(PASSIVATEASPRINCIPALNAME$36);
         return target;
      }
   }

   public void unsetPassivateAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSIVATEASPRINCIPALNAME$36, 0);
      }
   }

   public JndiNameType getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(JNDINAME$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDINAME$38) != 0;
      }
   }

   public void setJndiName(JndiNameType jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$38, 0, (short)1);
   }

   public JndiNameType addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(JNDINAME$38);
         return target;
      }
   }

   public void unsetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDINAME$38, 0);
      }
   }

   public JndiNameType getLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(LOCALJNDINAME$40, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJNDINAME$40) != 0;
      }
   }

   public void setLocalJndiName(JndiNameType localJndiName) {
      this.generatedSetterHelperImpl(localJndiName, LOCALJNDINAME$40, 0, (short)1);
   }

   public JndiNameType addNewLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(LOCALJNDINAME$40);
         return target;
      }
   }

   public void unsetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJNDINAME$40, 0);
      }
   }

   public DispatchPolicyType getDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().find_element_user(DISPATCHPOLICY$42, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPATCHPOLICY$42) != 0;
      }
   }

   public void setDispatchPolicy(DispatchPolicyType dispatchPolicy) {
      this.generatedSetterHelperImpl(dispatchPolicy, DISPATCHPOLICY$42, 0, (short)1);
   }

   public DispatchPolicyType addNewDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().add_element_user(DISPATCHPOLICY$42);
         return target;
      }
   }

   public void unsetDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPATCHPOLICY$42, 0);
      }
   }

   public XsdNonNegativeIntegerType getRemoteClientTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(REMOTECLIENTTIMEOUT$44, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRemoteClientTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOTECLIENTTIMEOUT$44) != 0;
      }
   }

   public void setRemoteClientTimeout(XsdNonNegativeIntegerType remoteClientTimeout) {
      this.generatedSetterHelperImpl(remoteClientTimeout, REMOTECLIENTTIMEOUT$44, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewRemoteClientTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(REMOTECLIENTTIMEOUT$44);
         return target;
      }
   }

   public void unsetRemoteClientTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOTECLIENTTIMEOUT$44, 0);
      }
   }

   public TrueFalseType getStickToFirstServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(STICKTOFIRSTSERVER$46, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStickToFirstServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STICKTOFIRSTSERVER$46) != 0;
      }
   }

   public void setStickToFirstServer(TrueFalseType stickToFirstServer) {
      this.generatedSetterHelperImpl(stickToFirstServer, STICKTOFIRSTSERVER$46, 0, (short)1);
   }

   public TrueFalseType addNewStickToFirstServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(STICKTOFIRSTSERVER$46);
         return target;
      }
   }

   public void unsetStickToFirstServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STICKTOFIRSTSERVER$46, 0);
      }
   }

   public JndiBindingType[] getJndiBindingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JNDIBINDING$48, targetList);
         JndiBindingType[] result = new JndiBindingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JndiBindingType getJndiBindingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiBindingType target = null;
         target = (JndiBindingType)this.get_store().find_element_user(JNDIBINDING$48, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJndiBindingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDIBINDING$48);
      }
   }

   public void setJndiBindingArray(JndiBindingType[] jndiBindingArray) {
      this.check_orphaned();
      this.arraySetterHelper(jndiBindingArray, JNDIBINDING$48);
   }

   public void setJndiBindingArray(int i, JndiBindingType jndiBinding) {
      this.generatedSetterHelperImpl(jndiBinding, JNDIBINDING$48, i, (short)2);
   }

   public JndiBindingType insertNewJndiBinding(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiBindingType target = null;
         target = (JndiBindingType)this.get_store().insert_element_user(JNDIBINDING$48, i);
         return target;
      }
   }

   public JndiBindingType addNewJndiBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiBindingType target = null;
         target = (JndiBindingType)this.get_store().add_element_user(JNDIBINDING$48);
         return target;
      }
   }

   public void removeJndiBinding(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDIBINDING$48, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$50);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$50);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$50) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$50);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$50);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$50);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$50);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$50);
      }
   }
}
