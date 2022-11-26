package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AdministeredObjectType;
import org.jcp.xmlns.xml.ns.javaee.AroundInvokeType;
import org.jcp.xmlns.xml.ns.javaee.AroundTimeoutType;
import org.jcp.xmlns.xml.ns.javaee.ConnectionFactoryResourceType;
import org.jcp.xmlns.xml.ns.javaee.DataSourceType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.EjbLocalRefType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefType;
import org.jcp.xmlns.xml.ns.javaee.EnvEntryType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.InterceptorType;
import org.jcp.xmlns.xml.ns.javaee.JmsConnectionFactoryType;
import org.jcp.xmlns.xml.ns.javaee.JmsDestinationType;
import org.jcp.xmlns.xml.ns.javaee.LifecycleCallbackType;
import org.jcp.xmlns.xml.ns.javaee.MailSessionType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationRefType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceContextRefType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceUnitRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceEnvRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceRefType;
import org.jcp.xmlns.xml.ns.javaee.ServiceRefType;

public class InterceptorTypeImpl extends XmlComplexContentImpl implements InterceptorType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName INTERCEPTORCLASS$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "interceptor-class");
   private static final QName AROUNDINVOKE$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "around-invoke");
   private static final QName AROUNDTIMEOUT$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "around-timeout");
   private static final QName AROUNDCONSTRUCT$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "around-construct");
   private static final QName ENVENTRY$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "env-entry");
   private static final QName EJBREF$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-ref");
   private static final QName EJBLOCALREF$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-local-ref");
   private static final QName SERVICEREF$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-ref");
   private static final QName RESOURCEREF$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-ref");
   private static final QName RESOURCEENVREF$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-ref");
   private static final QName PERSISTENCECONTEXTREF$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-context-ref");
   private static final QName PERSISTENCEUNITREF$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-unit-ref");
   private static final QName POSTCONSTRUCT$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "post-construct");
   private static final QName PREDESTROY$30 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "pre-destroy");
   private static final QName DATASOURCE$32 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "data-source");
   private static final QName JMSCONNECTIONFACTORY$34 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-connection-factory");
   private static final QName JMSDESTINATION$36 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-destination");
   private static final QName MAILSESSION$38 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mail-session");
   private static final QName CONNECTIONFACTORY$40 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "connection-factory");
   private static final QName ADMINISTEREDOBJECT$42 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "administered-object");
   private static final QName POSTACTIVATE$44 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "post-activate");
   private static final QName PREPASSIVATE$46 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "pre-passivate");
   private static final QName ID$48 = new QName("", "id");

   public InterceptorTypeImpl(SchemaType sType) {
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

   public FullyQualifiedClassType getInterceptorClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(INTERCEPTORCLASS$2, 0);
         return target == null ? null : target;
      }
   }

   public void setInterceptorClass(FullyQualifiedClassType interceptorClass) {
      this.generatedSetterHelperImpl(interceptorClass, INTERCEPTORCLASS$2, 0, (short)1);
   }

   public FullyQualifiedClassType addNewInterceptorClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(INTERCEPTORCLASS$2);
         return target;
      }
   }

   public AroundInvokeType[] getAroundInvokeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDINVOKE$4, targetList);
         AroundInvokeType[] result = new AroundInvokeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AroundInvokeType getAroundInvokeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().find_element_user(AROUNDINVOKE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundInvokeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDINVOKE$4);
      }
   }

   public void setAroundInvokeArray(AroundInvokeType[] aroundInvokeArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundInvokeArray, AROUNDINVOKE$4);
   }

   public void setAroundInvokeArray(int i, AroundInvokeType aroundInvoke) {
      this.generatedSetterHelperImpl(aroundInvoke, AROUNDINVOKE$4, i, (short)2);
   }

   public AroundInvokeType insertNewAroundInvoke(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().insert_element_user(AROUNDINVOKE$4, i);
         return target;
      }
   }

   public AroundInvokeType addNewAroundInvoke() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().add_element_user(AROUNDINVOKE$4);
         return target;
      }
   }

   public void removeAroundInvoke(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDINVOKE$4, i);
      }
   }

   public AroundTimeoutType[] getAroundTimeoutArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDTIMEOUT$6, targetList);
         AroundTimeoutType[] result = new AroundTimeoutType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AroundTimeoutType getAroundTimeoutArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().find_element_user(AROUNDTIMEOUT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundTimeoutArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDTIMEOUT$6);
      }
   }

   public void setAroundTimeoutArray(AroundTimeoutType[] aroundTimeoutArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundTimeoutArray, AROUNDTIMEOUT$6);
   }

   public void setAroundTimeoutArray(int i, AroundTimeoutType aroundTimeout) {
      this.generatedSetterHelperImpl(aroundTimeout, AROUNDTIMEOUT$6, i, (short)2);
   }

   public AroundTimeoutType insertNewAroundTimeout(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().insert_element_user(AROUNDTIMEOUT$6, i);
         return target;
      }
   }

   public AroundTimeoutType addNewAroundTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().add_element_user(AROUNDTIMEOUT$6);
         return target;
      }
   }

   public void removeAroundTimeout(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDTIMEOUT$6, i);
      }
   }

   public LifecycleCallbackType[] getAroundConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDCONSTRUCT$8, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getAroundConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(AROUNDCONSTRUCT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDCONSTRUCT$8);
      }
   }

   public void setAroundConstructArray(LifecycleCallbackType[] aroundConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundConstructArray, AROUNDCONSTRUCT$8);
   }

   public void setAroundConstructArray(int i, LifecycleCallbackType aroundConstruct) {
      this.generatedSetterHelperImpl(aroundConstruct, AROUNDCONSTRUCT$8, i, (short)2);
   }

   public LifecycleCallbackType insertNewAroundConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(AROUNDCONSTRUCT$8, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewAroundConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(AROUNDCONSTRUCT$8);
         return target;
      }
   }

   public void removeAroundConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDCONSTRUCT$8, i);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$10, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$10, i);
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
         return this.get_store().count_elements(ENVENTRY$10);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$10);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$10, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$10, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$10);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$10, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$12, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$12, i);
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
         return this.get_store().count_elements(EJBREF$12);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$12);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$12, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$12, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$12);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$12, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$14, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$14, i);
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
         return this.get_store().count_elements(EJBLOCALREF$14);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$14);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$14, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$14, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$14);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$14, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$16, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$16, i);
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
         return this.get_store().count_elements(SERVICEREF$16);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$16);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$16, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$16, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$16);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$16, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$18, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$18, i);
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
         return this.get_store().count_elements(RESOURCEREF$18);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$18);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$18, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$18, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$18);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$18, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$20, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$20, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$20);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$20);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$20, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$20, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$20);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$20, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$22, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$22, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$22);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$22);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$22, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$22, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$22);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$22, i);
      }
   }

   public PersistenceContextRefType[] getPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCECONTEXTREF$24, targetList);
         PersistenceContextRefType[] result = new PersistenceContextRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceContextRefType getPersistenceContextRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().find_element_user(PERSISTENCECONTEXTREF$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCECONTEXTREF$24);
      }
   }

   public void setPersistenceContextRefArray(PersistenceContextRefType[] persistenceContextRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceContextRefArray, PERSISTENCECONTEXTREF$24);
   }

   public void setPersistenceContextRefArray(int i, PersistenceContextRefType persistenceContextRef) {
      this.generatedSetterHelperImpl(persistenceContextRef, PERSISTENCECONTEXTREF$24, i, (short)2);
   }

   public PersistenceContextRefType insertNewPersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().insert_element_user(PERSISTENCECONTEXTREF$24, i);
         return target;
      }
   }

   public PersistenceContextRefType addNewPersistenceContextRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().add_element_user(PERSISTENCECONTEXTREF$24);
         return target;
      }
   }

   public void removePersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTREF$24, i);
      }
   }

   public PersistenceUnitRefType[] getPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITREF$26, targetList);
         PersistenceUnitRefType[] result = new PersistenceUnitRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitRefType getPersistenceUnitRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().find_element_user(PERSISTENCEUNITREF$26, i);
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
         return this.get_store().count_elements(PERSISTENCEUNITREF$26);
      }
   }

   public void setPersistenceUnitRefArray(PersistenceUnitRefType[] persistenceUnitRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitRefArray, PERSISTENCEUNITREF$26);
   }

   public void setPersistenceUnitRefArray(int i, PersistenceUnitRefType persistenceUnitRef) {
      this.generatedSetterHelperImpl(persistenceUnitRef, PERSISTENCEUNITREF$26, i, (short)2);
   }

   public PersistenceUnitRefType insertNewPersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().insert_element_user(PERSISTENCEUNITREF$26, i);
         return target;
      }
   }

   public PersistenceUnitRefType addNewPersistenceUnitRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().add_element_user(PERSISTENCEUNITREF$26);
         return target;
      }
   }

   public void removePersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITREF$26, i);
      }
   }

   public LifecycleCallbackType[] getPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTCONSTRUCT$28, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTCONSTRUCT$28, i);
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
         return this.get_store().count_elements(POSTCONSTRUCT$28);
      }
   }

   public void setPostConstructArray(LifecycleCallbackType[] postConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(postConstructArray, POSTCONSTRUCT$28);
   }

   public void setPostConstructArray(int i, LifecycleCallbackType postConstruct) {
      this.generatedSetterHelperImpl(postConstruct, POSTCONSTRUCT$28, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTCONSTRUCT$28, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTCONSTRUCT$28);
         return target;
      }
   }

   public void removePostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTCONSTRUCT$28, i);
      }
   }

   public LifecycleCallbackType[] getPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREDESTROY$30, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPreDestroyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREDESTROY$30, i);
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
         return this.get_store().count_elements(PREDESTROY$30);
      }
   }

   public void setPreDestroyArray(LifecycleCallbackType[] preDestroyArray) {
      this.check_orphaned();
      this.arraySetterHelper(preDestroyArray, PREDESTROY$30);
   }

   public void setPreDestroyArray(int i, LifecycleCallbackType preDestroy) {
      this.generatedSetterHelperImpl(preDestroy, PREDESTROY$30, i, (short)2);
   }

   public LifecycleCallbackType insertNewPreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREDESTROY$30, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPreDestroy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREDESTROY$30);
         return target;
      }
   }

   public void removePreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREDESTROY$30, i);
      }
   }

   public DataSourceType[] getDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATASOURCE$32, targetList);
         DataSourceType[] result = new DataSourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DataSourceType getDataSourceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().find_element_user(DATASOURCE$32, i);
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
         return this.get_store().count_elements(DATASOURCE$32);
      }
   }

   public void setDataSourceArray(DataSourceType[] dataSourceArray) {
      this.check_orphaned();
      this.arraySetterHelper(dataSourceArray, DATASOURCE$32);
   }

   public void setDataSourceArray(int i, DataSourceType dataSource) {
      this.generatedSetterHelperImpl(dataSource, DATASOURCE$32, i, (short)2);
   }

   public DataSourceType insertNewDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().insert_element_user(DATASOURCE$32, i);
         return target;
      }
   }

   public DataSourceType addNewDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().add_element_user(DATASOURCE$32);
         return target;
      }
   }

   public void removeDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCE$32, i);
      }
   }

   public JmsConnectionFactoryType[] getJmsConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSCONNECTIONFACTORY$34, targetList);
         JmsConnectionFactoryType[] result = new JmsConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsConnectionFactoryType getJmsConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().find_element_user(JMSCONNECTIONFACTORY$34, i);
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
         return this.get_store().count_elements(JMSCONNECTIONFACTORY$34);
      }
   }

   public void setJmsConnectionFactoryArray(JmsConnectionFactoryType[] jmsConnectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsConnectionFactoryArray, JMSCONNECTIONFACTORY$34);
   }

   public void setJmsConnectionFactoryArray(int i, JmsConnectionFactoryType jmsConnectionFactory) {
      this.generatedSetterHelperImpl(jmsConnectionFactory, JMSCONNECTIONFACTORY$34, i, (short)2);
   }

   public JmsConnectionFactoryType insertNewJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().insert_element_user(JMSCONNECTIONFACTORY$34, i);
         return target;
      }
   }

   public JmsConnectionFactoryType addNewJmsConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().add_element_user(JMSCONNECTIONFACTORY$34);
         return target;
      }
   }

   public void removeJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSCONNECTIONFACTORY$34, i);
      }
   }

   public JmsDestinationType[] getJmsDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSDESTINATION$36, targetList);
         JmsDestinationType[] result = new JmsDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsDestinationType getJmsDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().find_element_user(JMSDESTINATION$36, i);
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
         return this.get_store().count_elements(JMSDESTINATION$36);
      }
   }

   public void setJmsDestinationArray(JmsDestinationType[] jmsDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsDestinationArray, JMSDESTINATION$36);
   }

   public void setJmsDestinationArray(int i, JmsDestinationType jmsDestination) {
      this.generatedSetterHelperImpl(jmsDestination, JMSDESTINATION$36, i, (short)2);
   }

   public JmsDestinationType insertNewJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().insert_element_user(JMSDESTINATION$36, i);
         return target;
      }
   }

   public JmsDestinationType addNewJmsDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().add_element_user(JMSDESTINATION$36);
         return target;
      }
   }

   public void removeJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSDESTINATION$36, i);
      }
   }

   public MailSessionType[] getMailSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAILSESSION$38, targetList);
         MailSessionType[] result = new MailSessionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MailSessionType getMailSessionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().find_element_user(MAILSESSION$38, i);
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
         return this.get_store().count_elements(MAILSESSION$38);
      }
   }

   public void setMailSessionArray(MailSessionType[] mailSessionArray) {
      this.check_orphaned();
      this.arraySetterHelper(mailSessionArray, MAILSESSION$38);
   }

   public void setMailSessionArray(int i, MailSessionType mailSession) {
      this.generatedSetterHelperImpl(mailSession, MAILSESSION$38, i, (short)2);
   }

   public MailSessionType insertNewMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().insert_element_user(MAILSESSION$38, i);
         return target;
      }
   }

   public MailSessionType addNewMailSession() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().add_element_user(MAILSESSION$38);
         return target;
      }
   }

   public void removeMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAILSESSION$38, i);
      }
   }

   public ConnectionFactoryResourceType[] getConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONFACTORY$40, targetList);
         ConnectionFactoryResourceType[] result = new ConnectionFactoryResourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionFactoryResourceType getConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().find_element_user(CONNECTIONFACTORY$40, i);
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
         return this.get_store().count_elements(CONNECTIONFACTORY$40);
      }
   }

   public void setConnectionFactoryArray(ConnectionFactoryResourceType[] connectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionFactoryArray, CONNECTIONFACTORY$40);
   }

   public void setConnectionFactoryArray(int i, ConnectionFactoryResourceType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$40, i, (short)2);
   }

   public ConnectionFactoryResourceType insertNewConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().insert_element_user(CONNECTIONFACTORY$40, i);
         return target;
      }
   }

   public ConnectionFactoryResourceType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().add_element_user(CONNECTIONFACTORY$40);
         return target;
      }
   }

   public void removeConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY$40, i);
      }
   }

   public AdministeredObjectType[] getAdministeredObjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINISTEREDOBJECT$42, targetList);
         AdministeredObjectType[] result = new AdministeredObjectType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdministeredObjectType getAdministeredObjectArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().find_element_user(ADMINISTEREDOBJECT$42, i);
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
         return this.get_store().count_elements(ADMINISTEREDOBJECT$42);
      }
   }

   public void setAdministeredObjectArray(AdministeredObjectType[] administeredObjectArray) {
      this.check_orphaned();
      this.arraySetterHelper(administeredObjectArray, ADMINISTEREDOBJECT$42);
   }

   public void setAdministeredObjectArray(int i, AdministeredObjectType administeredObject) {
      this.generatedSetterHelperImpl(administeredObject, ADMINISTEREDOBJECT$42, i, (short)2);
   }

   public AdministeredObjectType insertNewAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().insert_element_user(ADMINISTEREDOBJECT$42, i);
         return target;
      }
   }

   public AdministeredObjectType addNewAdministeredObject() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().add_element_user(ADMINISTEREDOBJECT$42);
         return target;
      }
   }

   public void removeAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINISTEREDOBJECT$42, i);
      }
   }

   public LifecycleCallbackType[] getPostActivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTACTIVATE$44, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostActivateArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTACTIVATE$44, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPostActivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POSTACTIVATE$44);
      }
   }

   public void setPostActivateArray(LifecycleCallbackType[] postActivateArray) {
      this.check_orphaned();
      this.arraySetterHelper(postActivateArray, POSTACTIVATE$44);
   }

   public void setPostActivateArray(int i, LifecycleCallbackType postActivate) {
      this.generatedSetterHelperImpl(postActivate, POSTACTIVATE$44, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostActivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTACTIVATE$44, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostActivate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTACTIVATE$44);
         return target;
      }
   }

   public void removePostActivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTACTIVATE$44, i);
      }
   }

   public LifecycleCallbackType[] getPrePassivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREPASSIVATE$46, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPrePassivateArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREPASSIVATE$46, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPrePassivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREPASSIVATE$46);
      }
   }

   public void setPrePassivateArray(LifecycleCallbackType[] prePassivateArray) {
      this.check_orphaned();
      this.arraySetterHelper(prePassivateArray, PREPASSIVATE$46);
   }

   public void setPrePassivateArray(int i, LifecycleCallbackType prePassivate) {
      this.generatedSetterHelperImpl(prePassivate, PREPASSIVATE$46, i, (short)2);
   }

   public LifecycleCallbackType insertNewPrePassivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREPASSIVATE$46, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPrePassivate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREPASSIVATE$46);
         return target;
      }
   }

   public void removePrePassivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREPASSIVATE$46, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$48);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$48);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$48) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$48);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$48);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$48);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$48);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$48);
      }
   }
}
