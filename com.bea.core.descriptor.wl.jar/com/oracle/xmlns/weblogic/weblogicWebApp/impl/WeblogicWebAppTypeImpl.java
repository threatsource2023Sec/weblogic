package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebApp.AsyncDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.AuthFilterType;
import com.oracle.xmlns.weblogic.weblogicWebApp.CdiDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.CharsetParamsType;
import com.oracle.xmlns.weblogic.weblogicWebApp.CoherenceClusterRefType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ContainerDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ContextRootType;
import com.oracle.xmlns.weblogic.weblogicWebApp.DescriptionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.EjbReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.FastSwapType;
import com.oracle.xmlns.weblogic.weblogicWebApp.JaspicProviderType;
import com.oracle.xmlns.weblogic.weblogicWebApp.JspDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.LibraryRefType;
import com.oracle.xmlns.weblogic.weblogicWebApp.LoggingType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ManagedExecutorServiceType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ManagedScheduledExecutorServiceType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ManagedThreadFactoryType;
import com.oracle.xmlns.weblogic.weblogicWebApp.MessageDestinationDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.OsgiFrameworkReferenceType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ResourceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ResourceEnvDescriptionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.RunAsRoleAssignmentType;
import com.oracle.xmlns.weblogic.weblogicWebApp.SecurityPermissionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.SecurityRoleAssignmentType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ServiceReferenceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ServletDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.SessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.UrlMatchMapType;
import com.oracle.xmlns.weblogic.weblogicWebApp.VirtualDirectoryMappingType;
import com.oracle.xmlns.weblogic.weblogicWebApp.WeblogicVersionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.WeblogicWebAppType;
import com.oracle.xmlns.weblogic.weblogicWebApp.WlDispatchPolicyType;
import com.oracle.xmlns.weblogic.weblogicWebApp.WorkManagerType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicWebAppTypeImpl extends XmlComplexContentImpl implements WeblogicWebAppType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "description");
   private static final QName WEBLOGICVERSION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "weblogic-version");
   private static final QName SECURITYROLEASSIGNMENT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "security-role-assignment");
   private static final QName RUNASROLEASSIGNMENT$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "run-as-role-assignment");
   private static final QName RESOURCEDESCRIPTION$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "resource-description");
   private static final QName RESOURCEENVDESCRIPTION$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "resource-env-description");
   private static final QName EJBREFERENCEDESCRIPTION$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "ejb-reference-description");
   private static final QName SERVICEREFERENCEDESCRIPTION$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "service-reference-description");
   private static final QName MESSAGEDESTINATIONDESCRIPTOR$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "message-destination-descriptor");
   private static final QName SESSIONDESCRIPTOR$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "session-descriptor");
   private static final QName JSPDESCRIPTOR$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "jsp-descriptor");
   private static final QName AUTHFILTER$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "auth-filter");
   private static final QName CONTAINERDESCRIPTOR$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "container-descriptor");
   private static final QName ASYNCDESCRIPTOR$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "async-descriptor");
   private static final QName CHARSETPARAMS$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "charset-params");
   private static final QName VIRTUALDIRECTORYMAPPING$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "virtual-directory-mapping");
   private static final QName URLMATCHMAP$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "url-match-map");
   private static final QName SECURITYPERMISSION$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "security-permission");
   private static final QName CONTEXTROOT$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "context-root");
   private static final QName WLDISPATCHPOLICY$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "wl-dispatch-policy");
   private static final QName SERVLETDESCRIPTOR$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "servlet-descriptor");
   private static final QName WORKMANAGER$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "work-manager");
   private static final QName MANAGEDEXECUTORSERVICE$44 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "managed-executor-service");
   private static final QName MANAGEDSCHEDULEDEXECUTORSERVICE$46 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "managed-scheduled-executor-service");
   private static final QName MANAGEDTHREADFACTORY$48 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "managed-thread-factory");
   private static final QName COMPONENTFACTORYCLASSNAME$50 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "component-factory-class-name");
   private static final QName LOGGING$52 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "logging");
   private static final QName LIBRARYREF$54 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "library-ref");
   private static final QName JASPICPROVIDER$56 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "jaspic-provider");
   private static final QName FASTSWAP$58 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "fast-swap");
   private static final QName COHERENCECLUSTERREF$60 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "coherence-cluster-ref");
   private static final QName OSGIFRAMEWORKREFERENCE$62 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "osgi-framework-reference");
   private static final QName READYREGISTRATION$64 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "ready-registration");
   private static final QName CDIDESCRIPTOR$66 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "cdi-descriptor");
   private static final QName ID$68 = new QName("", "id");
   private static final QName VERSION$70 = new QName("", "version");

   public WeblogicWebAppTypeImpl(SchemaType sType) {
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

   public WeblogicVersionType[] getWeblogicVersionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBLOGICVERSION$2, targetList);
         WeblogicVersionType[] result = new WeblogicVersionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicVersionType getWeblogicVersionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicVersionType target = null;
         target = (WeblogicVersionType)this.get_store().find_element_user(WEBLOGICVERSION$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWeblogicVersionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICVERSION$2);
      }
   }

   public void setWeblogicVersionArray(WeblogicVersionType[] weblogicVersionArray) {
      this.check_orphaned();
      this.arraySetterHelper(weblogicVersionArray, WEBLOGICVERSION$2);
   }

   public void setWeblogicVersionArray(int i, WeblogicVersionType weblogicVersion) {
      this.generatedSetterHelperImpl(weblogicVersion, WEBLOGICVERSION$2, i, (short)2);
   }

   public WeblogicVersionType insertNewWeblogicVersion(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicVersionType target = null;
         target = (WeblogicVersionType)this.get_store().insert_element_user(WEBLOGICVERSION$2, i);
         return target;
      }
   }

   public WeblogicVersionType addNewWeblogicVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicVersionType target = null;
         target = (WeblogicVersionType)this.get_store().add_element_user(WEBLOGICVERSION$2);
         return target;
      }
   }

   public void removeWeblogicVersion(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICVERSION$2, i);
      }
   }

   public SecurityRoleAssignmentType[] getSecurityRoleAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEASSIGNMENT$4, targetList);
         SecurityRoleAssignmentType[] result = new SecurityRoleAssignmentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleAssignmentType getSecurityRoleAssignmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleAssignmentType target = null;
         target = (SecurityRoleAssignmentType)this.get_store().find_element_user(SECURITYROLEASSIGNMENT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLEASSIGNMENT$4);
      }
   }

   public void setSecurityRoleAssignmentArray(SecurityRoleAssignmentType[] securityRoleAssignmentArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleAssignmentArray, SECURITYROLEASSIGNMENT$4);
   }

   public void setSecurityRoleAssignmentArray(int i, SecurityRoleAssignmentType securityRoleAssignment) {
      this.generatedSetterHelperImpl(securityRoleAssignment, SECURITYROLEASSIGNMENT$4, i, (short)2);
   }

   public SecurityRoleAssignmentType insertNewSecurityRoleAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleAssignmentType target = null;
         target = (SecurityRoleAssignmentType)this.get_store().insert_element_user(SECURITYROLEASSIGNMENT$4, i);
         return target;
      }
   }

   public SecurityRoleAssignmentType addNewSecurityRoleAssignment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleAssignmentType target = null;
         target = (SecurityRoleAssignmentType)this.get_store().add_element_user(SECURITYROLEASSIGNMENT$4);
         return target;
      }
   }

   public void removeSecurityRoleAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEASSIGNMENT$4, i);
      }
   }

   public RunAsRoleAssignmentType[] getRunAsRoleAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RUNASROLEASSIGNMENT$6, targetList);
         RunAsRoleAssignmentType[] result = new RunAsRoleAssignmentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public RunAsRoleAssignmentType getRunAsRoleAssignmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsRoleAssignmentType target = null;
         target = (RunAsRoleAssignmentType)this.get_store().find_element_user(RUNASROLEASSIGNMENT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfRunAsRoleAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASROLEASSIGNMENT$6);
      }
   }

   public void setRunAsRoleAssignmentArray(RunAsRoleAssignmentType[] runAsRoleAssignmentArray) {
      this.check_orphaned();
      this.arraySetterHelper(runAsRoleAssignmentArray, RUNASROLEASSIGNMENT$6);
   }

   public void setRunAsRoleAssignmentArray(int i, RunAsRoleAssignmentType runAsRoleAssignment) {
      this.generatedSetterHelperImpl(runAsRoleAssignment, RUNASROLEASSIGNMENT$6, i, (short)2);
   }

   public RunAsRoleAssignmentType insertNewRunAsRoleAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsRoleAssignmentType target = null;
         target = (RunAsRoleAssignmentType)this.get_store().insert_element_user(RUNASROLEASSIGNMENT$6, i);
         return target;
      }
   }

   public RunAsRoleAssignmentType addNewRunAsRoleAssignment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsRoleAssignmentType target = null;
         target = (RunAsRoleAssignmentType)this.get_store().add_element_user(RUNASROLEASSIGNMENT$6);
         return target;
      }
   }

   public void removeRunAsRoleAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASROLEASSIGNMENT$6, i);
      }
   }

   public ResourceDescriptionType[] getResourceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEDESCRIPTION$8, targetList);
         ResourceDescriptionType[] result = new ResourceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceDescriptionType getResourceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().find_element_user(RESOURCEDESCRIPTION$8, i);
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
         return this.get_store().count_elements(RESOURCEDESCRIPTION$8);
      }
   }

   public void setResourceDescriptionArray(ResourceDescriptionType[] resourceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceDescriptionArray, RESOURCEDESCRIPTION$8);
   }

   public void setResourceDescriptionArray(int i, ResourceDescriptionType resourceDescription) {
      this.generatedSetterHelperImpl(resourceDescription, RESOURCEDESCRIPTION$8, i, (short)2);
   }

   public ResourceDescriptionType insertNewResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().insert_element_user(RESOURCEDESCRIPTION$8, i);
         return target;
      }
   }

   public ResourceDescriptionType addNewResourceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDescriptionType target = null;
         target = (ResourceDescriptionType)this.get_store().add_element_user(RESOURCEDESCRIPTION$8);
         return target;
      }
   }

   public void removeResourceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEDESCRIPTION$8, i);
      }
   }

   public ResourceEnvDescriptionType[] getResourceEnvDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVDESCRIPTION$10, targetList);
         ResourceEnvDescriptionType[] result = new ResourceEnvDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvDescriptionType getResourceEnvDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().find_element_user(RESOURCEENVDESCRIPTION$10, i);
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
         return this.get_store().count_elements(RESOURCEENVDESCRIPTION$10);
      }
   }

   public void setResourceEnvDescriptionArray(ResourceEnvDescriptionType[] resourceEnvDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvDescriptionArray, RESOURCEENVDESCRIPTION$10);
   }

   public void setResourceEnvDescriptionArray(int i, ResourceEnvDescriptionType resourceEnvDescription) {
      this.generatedSetterHelperImpl(resourceEnvDescription, RESOURCEENVDESCRIPTION$10, i, (short)2);
   }

   public ResourceEnvDescriptionType insertNewResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().insert_element_user(RESOURCEENVDESCRIPTION$10, i);
         return target;
      }
   }

   public ResourceEnvDescriptionType addNewResourceEnvDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvDescriptionType target = null;
         target = (ResourceEnvDescriptionType)this.get_store().add_element_user(RESOURCEENVDESCRIPTION$10);
         return target;
      }
   }

   public void removeResourceEnvDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVDESCRIPTION$10, i);
      }
   }

   public EjbReferenceDescriptionType[] getEjbReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREFERENCEDESCRIPTION$12, targetList);
         EjbReferenceDescriptionType[] result = new EjbReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbReferenceDescriptionType getEjbReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().find_element_user(EJBREFERENCEDESCRIPTION$12, i);
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
         return this.get_store().count_elements(EJBREFERENCEDESCRIPTION$12);
      }
   }

   public void setEjbReferenceDescriptionArray(EjbReferenceDescriptionType[] ejbReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbReferenceDescriptionArray, EJBREFERENCEDESCRIPTION$12);
   }

   public void setEjbReferenceDescriptionArray(int i, EjbReferenceDescriptionType ejbReferenceDescription) {
      this.generatedSetterHelperImpl(ejbReferenceDescription, EJBREFERENCEDESCRIPTION$12, i, (short)2);
   }

   public EjbReferenceDescriptionType insertNewEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().insert_element_user(EJBREFERENCEDESCRIPTION$12, i);
         return target;
      }
   }

   public EjbReferenceDescriptionType addNewEjbReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbReferenceDescriptionType target = null;
         target = (EjbReferenceDescriptionType)this.get_store().add_element_user(EJBREFERENCEDESCRIPTION$12);
         return target;
      }
   }

   public void removeEjbReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREFERENCEDESCRIPTION$12, i);
      }
   }

   public ServiceReferenceDescriptionType[] getServiceReferenceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREFERENCEDESCRIPTION$14, targetList);
         ServiceReferenceDescriptionType[] result = new ServiceReferenceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceReferenceDescriptionType getServiceReferenceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().find_element_user(SERVICEREFERENCEDESCRIPTION$14, i);
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
         return this.get_store().count_elements(SERVICEREFERENCEDESCRIPTION$14);
      }
   }

   public void setServiceReferenceDescriptionArray(ServiceReferenceDescriptionType[] serviceReferenceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceReferenceDescriptionArray, SERVICEREFERENCEDESCRIPTION$14);
   }

   public void setServiceReferenceDescriptionArray(int i, ServiceReferenceDescriptionType serviceReferenceDescription) {
      this.generatedSetterHelperImpl(serviceReferenceDescription, SERVICEREFERENCEDESCRIPTION$14, i, (short)2);
   }

   public ServiceReferenceDescriptionType insertNewServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().insert_element_user(SERVICEREFERENCEDESCRIPTION$14, i);
         return target;
      }
   }

   public ServiceReferenceDescriptionType addNewServiceReferenceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceReferenceDescriptionType target = null;
         target = (ServiceReferenceDescriptionType)this.get_store().add_element_user(SERVICEREFERENCEDESCRIPTION$14);
         return target;
      }
   }

   public void removeServiceReferenceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREFERENCEDESCRIPTION$14, i);
      }
   }

   public MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONDESCRIPTOR$16, targetList);
         MessageDestinationDescriptorType[] result = new MessageDestinationDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().find_element_user(MESSAGEDESTINATIONDESCRIPTOR$16, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONDESCRIPTOR$16);
      }
   }

   public void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] messageDestinationDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationDescriptorArray, MESSAGEDESTINATIONDESCRIPTOR$16);
   }

   public void setMessageDestinationDescriptorArray(int i, MessageDestinationDescriptorType messageDestinationDescriptor) {
      this.generatedSetterHelperImpl(messageDestinationDescriptor, MESSAGEDESTINATIONDESCRIPTOR$16, i, (short)2);
   }

   public MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().insert_element_user(MESSAGEDESTINATIONDESCRIPTOR$16, i);
         return target;
      }
   }

   public MessageDestinationDescriptorType addNewMessageDestinationDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().add_element_user(MESSAGEDESTINATIONDESCRIPTOR$16);
         return target;
      }
   }

   public void removeMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONDESCRIPTOR$16, i);
      }
   }

   public SessionDescriptorType[] getSessionDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SESSIONDESCRIPTOR$18, targetList);
         SessionDescriptorType[] result = new SessionDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SessionDescriptorType getSessionDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionDescriptorType target = null;
         target = (SessionDescriptorType)this.get_store().find_element_user(SESSIONDESCRIPTOR$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSessionDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONDESCRIPTOR$18);
      }
   }

   public void setSessionDescriptorArray(SessionDescriptorType[] sessionDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(sessionDescriptorArray, SESSIONDESCRIPTOR$18);
   }

   public void setSessionDescriptorArray(int i, SessionDescriptorType sessionDescriptor) {
      this.generatedSetterHelperImpl(sessionDescriptor, SESSIONDESCRIPTOR$18, i, (short)2);
   }

   public SessionDescriptorType insertNewSessionDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionDescriptorType target = null;
         target = (SessionDescriptorType)this.get_store().insert_element_user(SESSIONDESCRIPTOR$18, i);
         return target;
      }
   }

   public SessionDescriptorType addNewSessionDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionDescriptorType target = null;
         target = (SessionDescriptorType)this.get_store().add_element_user(SESSIONDESCRIPTOR$18);
         return target;
      }
   }

   public void removeSessionDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONDESCRIPTOR$18, i);
      }
   }

   public JspDescriptorType[] getJspDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JSPDESCRIPTOR$20, targetList);
         JspDescriptorType[] result = new JspDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JspDescriptorType getJspDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspDescriptorType target = null;
         target = (JspDescriptorType)this.get_store().find_element_user(JSPDESCRIPTOR$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJspDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JSPDESCRIPTOR$20);
      }
   }

   public void setJspDescriptorArray(JspDescriptorType[] jspDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(jspDescriptorArray, JSPDESCRIPTOR$20);
   }

   public void setJspDescriptorArray(int i, JspDescriptorType jspDescriptor) {
      this.generatedSetterHelperImpl(jspDescriptor, JSPDESCRIPTOR$20, i, (short)2);
   }

   public JspDescriptorType insertNewJspDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspDescriptorType target = null;
         target = (JspDescriptorType)this.get_store().insert_element_user(JSPDESCRIPTOR$20, i);
         return target;
      }
   }

   public JspDescriptorType addNewJspDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspDescriptorType target = null;
         target = (JspDescriptorType)this.get_store().add_element_user(JSPDESCRIPTOR$20);
         return target;
      }
   }

   public void removeJspDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JSPDESCRIPTOR$20, i);
      }
   }

   public AuthFilterType[] getAuthFilterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AUTHFILTER$22, targetList);
         AuthFilterType[] result = new AuthFilterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AuthFilterType getAuthFilterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthFilterType target = null;
         target = (AuthFilterType)this.get_store().find_element_user(AUTHFILTER$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAuthFilterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHFILTER$22);
      }
   }

   public void setAuthFilterArray(AuthFilterType[] authFilterArray) {
      this.check_orphaned();
      this.arraySetterHelper(authFilterArray, AUTHFILTER$22);
   }

   public void setAuthFilterArray(int i, AuthFilterType authFilter) {
      this.generatedSetterHelperImpl(authFilter, AUTHFILTER$22, i, (short)2);
   }

   public AuthFilterType insertNewAuthFilter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthFilterType target = null;
         target = (AuthFilterType)this.get_store().insert_element_user(AUTHFILTER$22, i);
         return target;
      }
   }

   public AuthFilterType addNewAuthFilter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthFilterType target = null;
         target = (AuthFilterType)this.get_store().add_element_user(AUTHFILTER$22);
         return target;
      }
   }

   public void removeAuthFilter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHFILTER$22, i);
      }
   }

   public ContainerDescriptorType[] getContainerDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTAINERDESCRIPTOR$24, targetList);
         ContainerDescriptorType[] result = new ContainerDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ContainerDescriptorType getContainerDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContainerDescriptorType target = null;
         target = (ContainerDescriptorType)this.get_store().find_element_user(CONTAINERDESCRIPTOR$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContainerDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTAINERDESCRIPTOR$24);
      }
   }

   public void setContainerDescriptorArray(ContainerDescriptorType[] containerDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(containerDescriptorArray, CONTAINERDESCRIPTOR$24);
   }

   public void setContainerDescriptorArray(int i, ContainerDescriptorType containerDescriptor) {
      this.generatedSetterHelperImpl(containerDescriptor, CONTAINERDESCRIPTOR$24, i, (short)2);
   }

   public ContainerDescriptorType insertNewContainerDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContainerDescriptorType target = null;
         target = (ContainerDescriptorType)this.get_store().insert_element_user(CONTAINERDESCRIPTOR$24, i);
         return target;
      }
   }

   public ContainerDescriptorType addNewContainerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContainerDescriptorType target = null;
         target = (ContainerDescriptorType)this.get_store().add_element_user(CONTAINERDESCRIPTOR$24);
         return target;
      }
   }

   public void removeContainerDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTAINERDESCRIPTOR$24, i);
      }
   }

   public AsyncDescriptorType[] getAsyncDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ASYNCDESCRIPTOR$26, targetList);
         AsyncDescriptorType[] result = new AsyncDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AsyncDescriptorType getAsyncDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsyncDescriptorType target = null;
         target = (AsyncDescriptorType)this.get_store().find_element_user(ASYNCDESCRIPTOR$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAsyncDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASYNCDESCRIPTOR$26);
      }
   }

   public void setAsyncDescriptorArray(AsyncDescriptorType[] asyncDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(asyncDescriptorArray, ASYNCDESCRIPTOR$26);
   }

   public void setAsyncDescriptorArray(int i, AsyncDescriptorType asyncDescriptor) {
      this.generatedSetterHelperImpl(asyncDescriptor, ASYNCDESCRIPTOR$26, i, (short)2);
   }

   public AsyncDescriptorType insertNewAsyncDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsyncDescriptorType target = null;
         target = (AsyncDescriptorType)this.get_store().insert_element_user(ASYNCDESCRIPTOR$26, i);
         return target;
      }
   }

   public AsyncDescriptorType addNewAsyncDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsyncDescriptorType target = null;
         target = (AsyncDescriptorType)this.get_store().add_element_user(ASYNCDESCRIPTOR$26);
         return target;
      }
   }

   public void removeAsyncDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASYNCDESCRIPTOR$26, i);
      }
   }

   public CharsetParamsType[] getCharsetParamsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CHARSETPARAMS$28, targetList);
         CharsetParamsType[] result = new CharsetParamsType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CharsetParamsType getCharsetParamsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharsetParamsType target = null;
         target = (CharsetParamsType)this.get_store().find_element_user(CHARSETPARAMS$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCharsetParamsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHARSETPARAMS$28);
      }
   }

   public void setCharsetParamsArray(CharsetParamsType[] charsetParamsArray) {
      this.check_orphaned();
      this.arraySetterHelper(charsetParamsArray, CHARSETPARAMS$28);
   }

   public void setCharsetParamsArray(int i, CharsetParamsType charsetParams) {
      this.generatedSetterHelperImpl(charsetParams, CHARSETPARAMS$28, i, (short)2);
   }

   public CharsetParamsType insertNewCharsetParams(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharsetParamsType target = null;
         target = (CharsetParamsType)this.get_store().insert_element_user(CHARSETPARAMS$28, i);
         return target;
      }
   }

   public CharsetParamsType addNewCharsetParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharsetParamsType target = null;
         target = (CharsetParamsType)this.get_store().add_element_user(CHARSETPARAMS$28);
         return target;
      }
   }

   public void removeCharsetParams(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHARSETPARAMS$28, i);
      }
   }

   public VirtualDirectoryMappingType[] getVirtualDirectoryMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(VIRTUALDIRECTORYMAPPING$30, targetList);
         VirtualDirectoryMappingType[] result = new VirtualDirectoryMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public VirtualDirectoryMappingType getVirtualDirectoryMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VirtualDirectoryMappingType target = null;
         target = (VirtualDirectoryMappingType)this.get_store().find_element_user(VIRTUALDIRECTORYMAPPING$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfVirtualDirectoryMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VIRTUALDIRECTORYMAPPING$30);
      }
   }

   public void setVirtualDirectoryMappingArray(VirtualDirectoryMappingType[] virtualDirectoryMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(virtualDirectoryMappingArray, VIRTUALDIRECTORYMAPPING$30);
   }

   public void setVirtualDirectoryMappingArray(int i, VirtualDirectoryMappingType virtualDirectoryMapping) {
      this.generatedSetterHelperImpl(virtualDirectoryMapping, VIRTUALDIRECTORYMAPPING$30, i, (short)2);
   }

   public VirtualDirectoryMappingType insertNewVirtualDirectoryMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VirtualDirectoryMappingType target = null;
         target = (VirtualDirectoryMappingType)this.get_store().insert_element_user(VIRTUALDIRECTORYMAPPING$30, i);
         return target;
      }
   }

   public VirtualDirectoryMappingType addNewVirtualDirectoryMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VirtualDirectoryMappingType target = null;
         target = (VirtualDirectoryMappingType)this.get_store().add_element_user(VIRTUALDIRECTORYMAPPING$30);
         return target;
      }
   }

   public void removeVirtualDirectoryMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VIRTUALDIRECTORYMAPPING$30, i);
      }
   }

   public UrlMatchMapType[] getUrlMatchMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(URLMATCHMAP$32, targetList);
         UrlMatchMapType[] result = new UrlMatchMapType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public UrlMatchMapType getUrlMatchMapArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlMatchMapType target = null;
         target = (UrlMatchMapType)this.get_store().find_element_user(URLMATCHMAP$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfUrlMatchMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URLMATCHMAP$32);
      }
   }

   public void setUrlMatchMapArray(UrlMatchMapType[] urlMatchMapArray) {
      this.check_orphaned();
      this.arraySetterHelper(urlMatchMapArray, URLMATCHMAP$32);
   }

   public void setUrlMatchMapArray(int i, UrlMatchMapType urlMatchMap) {
      this.generatedSetterHelperImpl(urlMatchMap, URLMATCHMAP$32, i, (short)2);
   }

   public UrlMatchMapType insertNewUrlMatchMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlMatchMapType target = null;
         target = (UrlMatchMapType)this.get_store().insert_element_user(URLMATCHMAP$32, i);
         return target;
      }
   }

   public UrlMatchMapType addNewUrlMatchMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlMatchMapType target = null;
         target = (UrlMatchMapType)this.get_store().add_element_user(URLMATCHMAP$32);
         return target;
      }
   }

   public void removeUrlMatchMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URLMATCHMAP$32, i);
      }
   }

   public SecurityPermissionType[] getSecurityPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYPERMISSION$34, targetList);
         SecurityPermissionType[] result = new SecurityPermissionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityPermissionType getSecurityPermissionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().find_element_user(SECURITYPERMISSION$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYPERMISSION$34);
      }
   }

   public void setSecurityPermissionArray(SecurityPermissionType[] securityPermissionArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityPermissionArray, SECURITYPERMISSION$34);
   }

   public void setSecurityPermissionArray(int i, SecurityPermissionType securityPermission) {
      this.generatedSetterHelperImpl(securityPermission, SECURITYPERMISSION$34, i, (short)2);
   }

   public SecurityPermissionType insertNewSecurityPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().insert_element_user(SECURITYPERMISSION$34, i);
         return target;
      }
   }

   public SecurityPermissionType addNewSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().add_element_user(SECURITYPERMISSION$34);
         return target;
      }
   }

   public void removeSecurityPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYPERMISSION$34, i);
      }
   }

   public ContextRootType[] getContextRootArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTEXTROOT$36, targetList);
         ContextRootType[] result = new ContextRootType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ContextRootType getContextRootArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRootType target = null;
         target = (ContextRootType)this.get_store().find_element_user(CONTEXTROOT$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContextRootArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTEXTROOT$36);
      }
   }

   public void setContextRootArray(ContextRootType[] contextRootArray) {
      this.check_orphaned();
      this.arraySetterHelper(contextRootArray, CONTEXTROOT$36);
   }

   public void setContextRootArray(int i, ContextRootType contextRoot) {
      this.generatedSetterHelperImpl(contextRoot, CONTEXTROOT$36, i, (short)2);
   }

   public ContextRootType insertNewContextRoot(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRootType target = null;
         target = (ContextRootType)this.get_store().insert_element_user(CONTEXTROOT$36, i);
         return target;
      }
   }

   public ContextRootType addNewContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRootType target = null;
         target = (ContextRootType)this.get_store().add_element_user(CONTEXTROOT$36);
         return target;
      }
   }

   public void removeContextRoot(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTROOT$36, i);
      }
   }

   public WlDispatchPolicyType[] getWlDispatchPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLDISPATCHPOLICY$38, targetList);
         WlDispatchPolicyType[] result = new WlDispatchPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlDispatchPolicyType getWlDispatchPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlDispatchPolicyType target = null;
         target = (WlDispatchPolicyType)this.get_store().find_element_user(WLDISPATCHPOLICY$38, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlDispatchPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLDISPATCHPOLICY$38);
      }
   }

   public void setWlDispatchPolicyArray(WlDispatchPolicyType[] wlDispatchPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlDispatchPolicyArray, WLDISPATCHPOLICY$38);
   }

   public void setWlDispatchPolicyArray(int i, WlDispatchPolicyType wlDispatchPolicy) {
      this.generatedSetterHelperImpl(wlDispatchPolicy, WLDISPATCHPOLICY$38, i, (short)2);
   }

   public WlDispatchPolicyType insertNewWlDispatchPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlDispatchPolicyType target = null;
         target = (WlDispatchPolicyType)this.get_store().insert_element_user(WLDISPATCHPOLICY$38, i);
         return target;
      }
   }

   public WlDispatchPolicyType addNewWlDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlDispatchPolicyType target = null;
         target = (WlDispatchPolicyType)this.get_store().add_element_user(WLDISPATCHPOLICY$38);
         return target;
      }
   }

   public void removeWlDispatchPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLDISPATCHPOLICY$38, i);
      }
   }

   public ServletDescriptorType[] getServletDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVLETDESCRIPTOR$40, targetList);
         ServletDescriptorType[] result = new ServletDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServletDescriptorType getServletDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletDescriptorType target = null;
         target = (ServletDescriptorType)this.get_store().find_element_user(SERVLETDESCRIPTOR$40, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServletDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLETDESCRIPTOR$40);
      }
   }

   public void setServletDescriptorArray(ServletDescriptorType[] servletDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(servletDescriptorArray, SERVLETDESCRIPTOR$40);
   }

   public void setServletDescriptorArray(int i, ServletDescriptorType servletDescriptor) {
      this.generatedSetterHelperImpl(servletDescriptor, SERVLETDESCRIPTOR$40, i, (short)2);
   }

   public ServletDescriptorType insertNewServletDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletDescriptorType target = null;
         target = (ServletDescriptorType)this.get_store().insert_element_user(SERVLETDESCRIPTOR$40, i);
         return target;
      }
   }

   public ServletDescriptorType addNewServletDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletDescriptorType target = null;
         target = (ServletDescriptorType)this.get_store().add_element_user(SERVLETDESCRIPTOR$40);
         return target;
      }
   }

   public void removeServletDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETDESCRIPTOR$40, i);
      }
   }

   public WorkManagerType[] getWorkManagerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WORKMANAGER$42, targetList);
         WorkManagerType[] result = new WorkManagerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WorkManagerType getWorkManagerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$42, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWorkManagerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WORKMANAGER$42);
      }
   }

   public void setWorkManagerArray(WorkManagerType[] workManagerArray) {
      this.check_orphaned();
      this.arraySetterHelper(workManagerArray, WORKMANAGER$42);
   }

   public void setWorkManagerArray(int i, WorkManagerType workManager) {
      this.generatedSetterHelperImpl(workManager, WORKMANAGER$42, i, (short)2);
   }

   public WorkManagerType insertNewWorkManager(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().insert_element_user(WORKMANAGER$42, i);
         return target;
      }
   }

   public WorkManagerType addNewWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$42);
         return target;
      }
   }

   public void removeWorkManager(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKMANAGER$42, i);
      }
   }

   public ManagedExecutorServiceType[] getManagedExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDEXECUTORSERVICE$44, targetList);
         ManagedExecutorServiceType[] result = new ManagedExecutorServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedExecutorServiceType getManagedExecutorServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().find_element_user(MANAGEDEXECUTORSERVICE$44, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDEXECUTORSERVICE$44);
      }
   }

   public void setManagedExecutorServiceArray(ManagedExecutorServiceType[] managedExecutorServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedExecutorServiceArray, MANAGEDEXECUTORSERVICE$44);
   }

   public void setManagedExecutorServiceArray(int i, ManagedExecutorServiceType managedExecutorService) {
      this.generatedSetterHelperImpl(managedExecutorService, MANAGEDEXECUTORSERVICE$44, i, (short)2);
   }

   public ManagedExecutorServiceType insertNewManagedExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().insert_element_user(MANAGEDEXECUTORSERVICE$44, i);
         return target;
      }
   }

   public ManagedExecutorServiceType addNewManagedExecutorService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().add_element_user(MANAGEDEXECUTORSERVICE$44);
         return target;
      }
   }

   public void removeManagedExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDEXECUTORSERVICE$44, i);
      }
   }

   public ManagedScheduledExecutorServiceType[] getManagedScheduledExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDSCHEDULEDEXECUTORSERVICE$46, targetList);
         ManagedScheduledExecutorServiceType[] result = new ManagedScheduledExecutorServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedScheduledExecutorServiceType getManagedScheduledExecutorServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().find_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$46, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedScheduledExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDSCHEDULEDEXECUTORSERVICE$46);
      }
   }

   public void setManagedScheduledExecutorServiceArray(ManagedScheduledExecutorServiceType[] managedScheduledExecutorServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedScheduledExecutorServiceArray, MANAGEDSCHEDULEDEXECUTORSERVICE$46);
   }

   public void setManagedScheduledExecutorServiceArray(int i, ManagedScheduledExecutorServiceType managedScheduledExecutorService) {
      this.generatedSetterHelperImpl(managedScheduledExecutorService, MANAGEDSCHEDULEDEXECUTORSERVICE$46, i, (short)2);
   }

   public ManagedScheduledExecutorServiceType insertNewManagedScheduledExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().insert_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$46, i);
         return target;
      }
   }

   public ManagedScheduledExecutorServiceType addNewManagedScheduledExecutorService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().add_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$46);
         return target;
      }
   }

   public void removeManagedScheduledExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDSCHEDULEDEXECUTORSERVICE$46, i);
      }
   }

   public ManagedThreadFactoryType[] getManagedThreadFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDTHREADFACTORY$48, targetList);
         ManagedThreadFactoryType[] result = new ManagedThreadFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedThreadFactoryType getManagedThreadFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().find_element_user(MANAGEDTHREADFACTORY$48, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedThreadFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDTHREADFACTORY$48);
      }
   }

   public void setManagedThreadFactoryArray(ManagedThreadFactoryType[] managedThreadFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedThreadFactoryArray, MANAGEDTHREADFACTORY$48);
   }

   public void setManagedThreadFactoryArray(int i, ManagedThreadFactoryType managedThreadFactory) {
      this.generatedSetterHelperImpl(managedThreadFactory, MANAGEDTHREADFACTORY$48, i, (short)2);
   }

   public ManagedThreadFactoryType insertNewManagedThreadFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().insert_element_user(MANAGEDTHREADFACTORY$48, i);
         return target;
      }
   }

   public ManagedThreadFactoryType addNewManagedThreadFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().add_element_user(MANAGEDTHREADFACTORY$48);
         return target;
      }
   }

   public void removeManagedThreadFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDTHREADFACTORY$48, i);
      }
   }

   public XsdStringType[] getComponentFactoryClassNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COMPONENTFACTORYCLASSNAME$50, targetList);
         XsdStringType[] result = new XsdStringType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XsdStringType getComponentFactoryClassNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(COMPONENTFACTORYCLASSNAME$50, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfComponentFactoryClassNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPONENTFACTORYCLASSNAME$50);
      }
   }

   public void setComponentFactoryClassNameArray(XsdStringType[] componentFactoryClassNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(componentFactoryClassNameArray, COMPONENTFACTORYCLASSNAME$50);
   }

   public void setComponentFactoryClassNameArray(int i, XsdStringType componentFactoryClassName) {
      this.generatedSetterHelperImpl(componentFactoryClassName, COMPONENTFACTORYCLASSNAME$50, i, (short)2);
   }

   public XsdStringType insertNewComponentFactoryClassName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().insert_element_user(COMPONENTFACTORYCLASSNAME$50, i);
         return target;
      }
   }

   public XsdStringType addNewComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(COMPONENTFACTORYCLASSNAME$50);
         return target;
      }
   }

   public void removeComponentFactoryClassName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPONENTFACTORYCLASSNAME$50, i);
      }
   }

   public LoggingType[] getLoggingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LOGGING$52, targetList);
         LoggingType[] result = new LoggingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LoggingType getLoggingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().find_element_user(LOGGING$52, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLoggingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGING$52);
      }
   }

   public void setLoggingArray(LoggingType[] loggingArray) {
      this.check_orphaned();
      this.arraySetterHelper(loggingArray, LOGGING$52);
   }

   public void setLoggingArray(int i, LoggingType logging) {
      this.generatedSetterHelperImpl(logging, LOGGING$52, i, (short)2);
   }

   public LoggingType insertNewLogging(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().insert_element_user(LOGGING$52, i);
         return target;
      }
   }

   public LoggingType addNewLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().add_element_user(LOGGING$52);
         return target;
      }
   }

   public void removeLogging(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGGING$52, i);
      }
   }

   public LibraryRefType[] getLibraryRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LIBRARYREF$54, targetList);
         LibraryRefType[] result = new LibraryRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LibraryRefType getLibraryRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryRefType target = null;
         target = (LibraryRefType)this.get_store().find_element_user(LIBRARYREF$54, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLibraryRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LIBRARYREF$54);
      }
   }

   public void setLibraryRefArray(LibraryRefType[] libraryRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(libraryRefArray, LIBRARYREF$54);
   }

   public void setLibraryRefArray(int i, LibraryRefType libraryRef) {
      this.generatedSetterHelperImpl(libraryRef, LIBRARYREF$54, i, (short)2);
   }

   public LibraryRefType insertNewLibraryRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryRefType target = null;
         target = (LibraryRefType)this.get_store().insert_element_user(LIBRARYREF$54, i);
         return target;
      }
   }

   public LibraryRefType addNewLibraryRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryRefType target = null;
         target = (LibraryRefType)this.get_store().add_element_user(LIBRARYREF$54);
         return target;
      }
   }

   public void removeLibraryRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LIBRARYREF$54, i);
      }
   }

   public JaspicProviderType getJaspicProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JaspicProviderType target = null;
         target = (JaspicProviderType)this.get_store().find_element_user(JASPICPROVIDER$56, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJaspicProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JASPICPROVIDER$56) != 0;
      }
   }

   public void setJaspicProvider(JaspicProviderType jaspicProvider) {
      this.generatedSetterHelperImpl(jaspicProvider, JASPICPROVIDER$56, 0, (short)1);
   }

   public JaspicProviderType addNewJaspicProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JaspicProviderType target = null;
         target = (JaspicProviderType)this.get_store().add_element_user(JASPICPROVIDER$56);
         return target;
      }
   }

   public void unsetJaspicProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JASPICPROVIDER$56, 0);
      }
   }

   public FastSwapType getFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FastSwapType target = null;
         target = (FastSwapType)this.get_store().find_element_user(FASTSWAP$58, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FASTSWAP$58) != 0;
      }
   }

   public void setFastSwap(FastSwapType fastSwap) {
      this.generatedSetterHelperImpl(fastSwap, FASTSWAP$58, 0, (short)1);
   }

   public FastSwapType addNewFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FastSwapType target = null;
         target = (FastSwapType)this.get_store().add_element_user(FASTSWAP$58);
         return target;
      }
   }

   public void unsetFastSwap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FASTSWAP$58, 0);
      }
   }

   public CoherenceClusterRefType getCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterRefType target = null;
         target = (CoherenceClusterRefType)this.get_store().find_element_user(COHERENCECLUSTERREF$60, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERREF$60) != 0;
      }
   }

   public void setCoherenceClusterRef(CoherenceClusterRefType coherenceClusterRef) {
      this.generatedSetterHelperImpl(coherenceClusterRef, COHERENCECLUSTERREF$60, 0, (short)1);
   }

   public CoherenceClusterRefType addNewCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterRefType target = null;
         target = (CoherenceClusterRefType)this.get_store().add_element_user(COHERENCECLUSTERREF$60);
         return target;
      }
   }

   public void unsetCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERREF$60, 0);
      }
   }

   public OsgiFrameworkReferenceType getOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OsgiFrameworkReferenceType target = null;
         target = (OsgiFrameworkReferenceType)this.get_store().find_element_user(OSGIFRAMEWORKREFERENCE$62, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OSGIFRAMEWORKREFERENCE$62) != 0;
      }
   }

   public void setOsgiFrameworkReference(OsgiFrameworkReferenceType osgiFrameworkReference) {
      this.generatedSetterHelperImpl(osgiFrameworkReference, OSGIFRAMEWORKREFERENCE$62, 0, (short)1);
   }

   public OsgiFrameworkReferenceType addNewOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OsgiFrameworkReferenceType target = null;
         target = (OsgiFrameworkReferenceType)this.get_store().add_element_user(OSGIFRAMEWORKREFERENCE$62);
         return target;
      }
   }

   public void unsetOsgiFrameworkReference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OSGIFRAMEWORKREFERENCE$62, 0);
      }
   }

   public String getReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(READYREGISTRATION$64, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(READYREGISTRATION$64, 0);
         return target;
      }
   }

   public boolean isSetReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(READYREGISTRATION$64) != 0;
      }
   }

   public void setReadyRegistration(String readyRegistration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(READYREGISTRATION$64, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(READYREGISTRATION$64);
         }

         target.setStringValue(readyRegistration);
      }
   }

   public void xsetReadyRegistration(XmlString readyRegistration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(READYREGISTRATION$64, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(READYREGISTRATION$64);
         }

         target.set(readyRegistration);
      }
   }

   public void unsetReadyRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(READYREGISTRATION$64, 0);
      }
   }

   public CdiDescriptorType getCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().find_element_user(CDIDESCRIPTOR$66, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CDIDESCRIPTOR$66) != 0;
      }
   }

   public void setCdiDescriptor(CdiDescriptorType cdiDescriptor) {
      this.generatedSetterHelperImpl(cdiDescriptor, CDIDESCRIPTOR$66, 0, (short)1);
   }

   public CdiDescriptorType addNewCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().add_element_user(CDIDESCRIPTOR$66);
         return target;
      }
   }

   public void unsetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CDIDESCRIPTOR$66, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$68);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$68);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$68) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$68);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$68);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$68);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$68);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$68);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$70);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$70);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$70) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$70);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$70);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$70);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$70);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$70);
      }
   }
}
