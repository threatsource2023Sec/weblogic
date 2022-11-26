package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CdiDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CoherenceClusterRefType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.DisableWarningType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IdempotentMethodsType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ManagedExecutorServiceType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ManagedScheduledExecutorServiceType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ManagedThreadFactoryType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MessageDestinationDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.RetryMethodsOnRollbackType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.RunAsRoleAssignmentType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SecurityPermissionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SecurityRoleAssignmentType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SkipStateReplicationMethodsType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TimerImplementationType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TransactionIsolationType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicCompatibilityType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicEjbJarType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicEnterpriseBeanType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WorkManagerType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicEjbJarTypeImpl extends XmlComplexContentImpl implements WeblogicEjbJarType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "description");
   private static final QName WEBLOGICENTERPRISEBEAN$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "weblogic-enterprise-bean");
   private static final QName SECURITYROLEASSIGNMENT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "security-role-assignment");
   private static final QName RUNASROLEASSIGNMENT$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "run-as-role-assignment");
   private static final QName SECURITYPERMISSION$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "security-permission");
   private static final QName TRANSACTIONISOLATION$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "transaction-isolation");
   private static final QName MESSAGEDESTINATIONDESCRIPTOR$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "message-destination-descriptor");
   private static final QName IDEMPOTENTMETHODS$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "idempotent-methods");
   private static final QName SKIPSTATEREPLICATIONMETHODS$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "skip-state-replication-methods");
   private static final QName RETRYMETHODSONROLLBACK$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "retry-methods-on-rollback");
   private static final QName ENABLEBEANCLASSREDEPLOY$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "enable-bean-class-redeploy");
   private static final QName TIMERIMPLEMENTATION$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "timer-implementation");
   private static final QName DISABLEWARNING$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "disable-warning");
   private static final QName WORKMANAGER$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "work-manager");
   private static final QName MANAGEDEXECUTORSERVICE$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "managed-executor-service");
   private static final QName MANAGEDSCHEDULEDEXECUTORSERVICE$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "managed-scheduled-executor-service");
   private static final QName MANAGEDTHREADFACTORY$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "managed-thread-factory");
   private static final QName COMPONENTFACTORYCLASSNAME$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "component-factory-class-name");
   private static final QName WEBLOGICCOMPATIBILITY$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "weblogic-compatibility");
   private static final QName COHERENCECLUSTERREF$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "coherence-cluster-ref");
   private static final QName CDIDESCRIPTOR$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "cdi-descriptor");
   private static final QName ID$42 = new QName("", "id");
   private static final QName VERSION$44 = new QName("", "version");

   public WeblogicEjbJarTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public WeblogicEnterpriseBeanType[] getWeblogicEnterpriseBeanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBLOGICENTERPRISEBEAN$2, targetList);
         WeblogicEnterpriseBeanType[] result = new WeblogicEnterpriseBeanType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicEnterpriseBeanType getWeblogicEnterpriseBeanArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicEnterpriseBeanType target = null;
         target = (WeblogicEnterpriseBeanType)this.get_store().find_element_user(WEBLOGICENTERPRISEBEAN$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWeblogicEnterpriseBeanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICENTERPRISEBEAN$2);
      }
   }

   public void setWeblogicEnterpriseBeanArray(WeblogicEnterpriseBeanType[] weblogicEnterpriseBeanArray) {
      this.check_orphaned();
      this.arraySetterHelper(weblogicEnterpriseBeanArray, WEBLOGICENTERPRISEBEAN$2);
   }

   public void setWeblogicEnterpriseBeanArray(int i, WeblogicEnterpriseBeanType weblogicEnterpriseBean) {
      this.generatedSetterHelperImpl(weblogicEnterpriseBean, WEBLOGICENTERPRISEBEAN$2, i, (short)2);
   }

   public WeblogicEnterpriseBeanType insertNewWeblogicEnterpriseBean(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicEnterpriseBeanType target = null;
         target = (WeblogicEnterpriseBeanType)this.get_store().insert_element_user(WEBLOGICENTERPRISEBEAN$2, i);
         return target;
      }
   }

   public WeblogicEnterpriseBeanType addNewWeblogicEnterpriseBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicEnterpriseBeanType target = null;
         target = (WeblogicEnterpriseBeanType)this.get_store().add_element_user(WEBLOGICENTERPRISEBEAN$2);
         return target;
      }
   }

   public void removeWeblogicEnterpriseBean(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICENTERPRISEBEAN$2, i);
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

   public SecurityPermissionType getSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().find_element_user(SECURITYPERMISSION$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYPERMISSION$8) != 0;
      }
   }

   public void setSecurityPermission(SecurityPermissionType securityPermission) {
      this.generatedSetterHelperImpl(securityPermission, SECURITYPERMISSION$8, 0, (short)1);
   }

   public SecurityPermissionType addNewSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().add_element_user(SECURITYPERMISSION$8);
         return target;
      }
   }

   public void unsetSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYPERMISSION$8, 0);
      }
   }

   public TransactionIsolationType[] getTransactionIsolationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TRANSACTIONISOLATION$10, targetList);
         TransactionIsolationType[] result = new TransactionIsolationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TransactionIsolationType getTransactionIsolationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionIsolationType target = null;
         target = (TransactionIsolationType)this.get_store().find_element_user(TRANSACTIONISOLATION$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTransactionIsolationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONISOLATION$10);
      }
   }

   public void setTransactionIsolationArray(TransactionIsolationType[] transactionIsolationArray) {
      this.check_orphaned();
      this.arraySetterHelper(transactionIsolationArray, TRANSACTIONISOLATION$10);
   }

   public void setTransactionIsolationArray(int i, TransactionIsolationType transactionIsolation) {
      this.generatedSetterHelperImpl(transactionIsolation, TRANSACTIONISOLATION$10, i, (short)2);
   }

   public TransactionIsolationType insertNewTransactionIsolation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionIsolationType target = null;
         target = (TransactionIsolationType)this.get_store().insert_element_user(TRANSACTIONISOLATION$10, i);
         return target;
      }
   }

   public TransactionIsolationType addNewTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionIsolationType target = null;
         target = (TransactionIsolationType)this.get_store().add_element_user(TRANSACTIONISOLATION$10);
         return target;
      }
   }

   public void removeTransactionIsolation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONISOLATION$10, i);
      }
   }

   public MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONDESCRIPTOR$12, targetList);
         MessageDestinationDescriptorType[] result = new MessageDestinationDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().find_element_user(MESSAGEDESTINATIONDESCRIPTOR$12, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONDESCRIPTOR$12);
      }
   }

   public void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] messageDestinationDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationDescriptorArray, MESSAGEDESTINATIONDESCRIPTOR$12);
   }

   public void setMessageDestinationDescriptorArray(int i, MessageDestinationDescriptorType messageDestinationDescriptor) {
      this.generatedSetterHelperImpl(messageDestinationDescriptor, MESSAGEDESTINATIONDESCRIPTOR$12, i, (short)2);
   }

   public MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().insert_element_user(MESSAGEDESTINATIONDESCRIPTOR$12, i);
         return target;
      }
   }

   public MessageDestinationDescriptorType addNewMessageDestinationDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationDescriptorType target = null;
         target = (MessageDestinationDescriptorType)this.get_store().add_element_user(MESSAGEDESTINATIONDESCRIPTOR$12);
         return target;
      }
   }

   public void removeMessageDestinationDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONDESCRIPTOR$12, i);
      }
   }

   public IdempotentMethodsType getIdempotentMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdempotentMethodsType target = null;
         target = (IdempotentMethodsType)this.get_store().find_element_user(IDEMPOTENTMETHODS$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIdempotentMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDEMPOTENTMETHODS$14) != 0;
      }
   }

   public void setIdempotentMethods(IdempotentMethodsType idempotentMethods) {
      this.generatedSetterHelperImpl(idempotentMethods, IDEMPOTENTMETHODS$14, 0, (short)1);
   }

   public IdempotentMethodsType addNewIdempotentMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdempotentMethodsType target = null;
         target = (IdempotentMethodsType)this.get_store().add_element_user(IDEMPOTENTMETHODS$14);
         return target;
      }
   }

   public void unsetIdempotentMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDEMPOTENTMETHODS$14, 0);
      }
   }

   public SkipStateReplicationMethodsType getSkipStateReplicationMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SkipStateReplicationMethodsType target = null;
         target = (SkipStateReplicationMethodsType)this.get_store().find_element_user(SKIPSTATEREPLICATIONMETHODS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSkipStateReplicationMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SKIPSTATEREPLICATIONMETHODS$16) != 0;
      }
   }

   public void setSkipStateReplicationMethods(SkipStateReplicationMethodsType skipStateReplicationMethods) {
      this.generatedSetterHelperImpl(skipStateReplicationMethods, SKIPSTATEREPLICATIONMETHODS$16, 0, (short)1);
   }

   public SkipStateReplicationMethodsType addNewSkipStateReplicationMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SkipStateReplicationMethodsType target = null;
         target = (SkipStateReplicationMethodsType)this.get_store().add_element_user(SKIPSTATEREPLICATIONMETHODS$16);
         return target;
      }
   }

   public void unsetSkipStateReplicationMethods() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SKIPSTATEREPLICATIONMETHODS$16, 0);
      }
   }

   public RetryMethodsOnRollbackType[] getRetryMethodsOnRollbackArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RETRYMETHODSONROLLBACK$18, targetList);
         RetryMethodsOnRollbackType[] result = new RetryMethodsOnRollbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public RetryMethodsOnRollbackType getRetryMethodsOnRollbackArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RetryMethodsOnRollbackType target = null;
         target = (RetryMethodsOnRollbackType)this.get_store().find_element_user(RETRYMETHODSONROLLBACK$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfRetryMethodsOnRollbackArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETRYMETHODSONROLLBACK$18);
      }
   }

   public void setRetryMethodsOnRollbackArray(RetryMethodsOnRollbackType[] retryMethodsOnRollbackArray) {
      this.check_orphaned();
      this.arraySetterHelper(retryMethodsOnRollbackArray, RETRYMETHODSONROLLBACK$18);
   }

   public void setRetryMethodsOnRollbackArray(int i, RetryMethodsOnRollbackType retryMethodsOnRollback) {
      this.generatedSetterHelperImpl(retryMethodsOnRollback, RETRYMETHODSONROLLBACK$18, i, (short)2);
   }

   public RetryMethodsOnRollbackType insertNewRetryMethodsOnRollback(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RetryMethodsOnRollbackType target = null;
         target = (RetryMethodsOnRollbackType)this.get_store().insert_element_user(RETRYMETHODSONROLLBACK$18, i);
         return target;
      }
   }

   public RetryMethodsOnRollbackType addNewRetryMethodsOnRollback() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RetryMethodsOnRollbackType target = null;
         target = (RetryMethodsOnRollbackType)this.get_store().add_element_user(RETRYMETHODSONROLLBACK$18);
         return target;
      }
   }

   public void removeRetryMethodsOnRollback(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETRYMETHODSONROLLBACK$18, i);
      }
   }

   public TrueFalseType getEnableBeanClassRedeploy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEBEANCLASSREDEPLOY$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableBeanClassRedeploy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEBEANCLASSREDEPLOY$20) != 0;
      }
   }

   public void setEnableBeanClassRedeploy(TrueFalseType enableBeanClassRedeploy) {
      this.generatedSetterHelperImpl(enableBeanClassRedeploy, ENABLEBEANCLASSREDEPLOY$20, 0, (short)1);
   }

   public TrueFalseType addNewEnableBeanClassRedeploy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEBEANCLASSREDEPLOY$20);
         return target;
      }
   }

   public void unsetEnableBeanClassRedeploy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEBEANCLASSREDEPLOY$20, 0);
      }
   }

   public TimerImplementationType getTimerImplementation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerImplementationType target = null;
         target = (TimerImplementationType)this.get_store().find_element_user(TIMERIMPLEMENTATION$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimerImplementation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMERIMPLEMENTATION$22) != 0;
      }
   }

   public void setTimerImplementation(TimerImplementationType timerImplementation) {
      this.generatedSetterHelperImpl(timerImplementation, TIMERIMPLEMENTATION$22, 0, (short)1);
   }

   public TimerImplementationType addNewTimerImplementation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerImplementationType target = null;
         target = (TimerImplementationType)this.get_store().add_element_user(TIMERIMPLEMENTATION$22);
         return target;
      }
   }

   public void unsetTimerImplementation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMERIMPLEMENTATION$22, 0);
      }
   }

   public DisableWarningType[] getDisableWarningArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISABLEWARNING$24, targetList);
         DisableWarningType[] result = new DisableWarningType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisableWarningType getDisableWarningArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisableWarningType target = null;
         target = (DisableWarningType)this.get_store().find_element_user(DISABLEWARNING$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisableWarningArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISABLEWARNING$24);
      }
   }

   public void setDisableWarningArray(DisableWarningType[] disableWarningArray) {
      this.check_orphaned();
      this.arraySetterHelper(disableWarningArray, DISABLEWARNING$24);
   }

   public void setDisableWarningArray(int i, DisableWarningType disableWarning) {
      this.generatedSetterHelperImpl(disableWarning, DISABLEWARNING$24, i, (short)2);
   }

   public DisableWarningType insertNewDisableWarning(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisableWarningType target = null;
         target = (DisableWarningType)this.get_store().insert_element_user(DISABLEWARNING$24, i);
         return target;
      }
   }

   public DisableWarningType addNewDisableWarning() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisableWarningType target = null;
         target = (DisableWarningType)this.get_store().add_element_user(DISABLEWARNING$24);
         return target;
      }
   }

   public void removeDisableWarning(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISABLEWARNING$24, i);
      }
   }

   public WorkManagerType[] getWorkManagerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WORKMANAGER$26, targetList);
         WorkManagerType[] result = new WorkManagerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WorkManagerType getWorkManagerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$26, i);
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
         return this.get_store().count_elements(WORKMANAGER$26);
      }
   }

   public void setWorkManagerArray(WorkManagerType[] workManagerArray) {
      this.check_orphaned();
      this.arraySetterHelper(workManagerArray, WORKMANAGER$26);
   }

   public void setWorkManagerArray(int i, WorkManagerType workManager) {
      this.generatedSetterHelperImpl(workManager, WORKMANAGER$26, i, (short)2);
   }

   public WorkManagerType insertNewWorkManager(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().insert_element_user(WORKMANAGER$26, i);
         return target;
      }
   }

   public WorkManagerType addNewWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$26);
         return target;
      }
   }

   public void removeWorkManager(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKMANAGER$26, i);
      }
   }

   public ManagedExecutorServiceType[] getManagedExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDEXECUTORSERVICE$28, targetList);
         ManagedExecutorServiceType[] result = new ManagedExecutorServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedExecutorServiceType getManagedExecutorServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().find_element_user(MANAGEDEXECUTORSERVICE$28, i);
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
         return this.get_store().count_elements(MANAGEDEXECUTORSERVICE$28);
      }
   }

   public void setManagedExecutorServiceArray(ManagedExecutorServiceType[] managedExecutorServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedExecutorServiceArray, MANAGEDEXECUTORSERVICE$28);
   }

   public void setManagedExecutorServiceArray(int i, ManagedExecutorServiceType managedExecutorService) {
      this.generatedSetterHelperImpl(managedExecutorService, MANAGEDEXECUTORSERVICE$28, i, (short)2);
   }

   public ManagedExecutorServiceType insertNewManagedExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().insert_element_user(MANAGEDEXECUTORSERVICE$28, i);
         return target;
      }
   }

   public ManagedExecutorServiceType addNewManagedExecutorService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedExecutorServiceType target = null;
         target = (ManagedExecutorServiceType)this.get_store().add_element_user(MANAGEDEXECUTORSERVICE$28);
         return target;
      }
   }

   public void removeManagedExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDEXECUTORSERVICE$28, i);
      }
   }

   public ManagedScheduledExecutorServiceType[] getManagedScheduledExecutorServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDSCHEDULEDEXECUTORSERVICE$30, targetList);
         ManagedScheduledExecutorServiceType[] result = new ManagedScheduledExecutorServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedScheduledExecutorServiceType getManagedScheduledExecutorServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().find_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$30, i);
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
         return this.get_store().count_elements(MANAGEDSCHEDULEDEXECUTORSERVICE$30);
      }
   }

   public void setManagedScheduledExecutorServiceArray(ManagedScheduledExecutorServiceType[] managedScheduledExecutorServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedScheduledExecutorServiceArray, MANAGEDSCHEDULEDEXECUTORSERVICE$30);
   }

   public void setManagedScheduledExecutorServiceArray(int i, ManagedScheduledExecutorServiceType managedScheduledExecutorService) {
      this.generatedSetterHelperImpl(managedScheduledExecutorService, MANAGEDSCHEDULEDEXECUTORSERVICE$30, i, (short)2);
   }

   public ManagedScheduledExecutorServiceType insertNewManagedScheduledExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().insert_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$30, i);
         return target;
      }
   }

   public ManagedScheduledExecutorServiceType addNewManagedScheduledExecutorService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedScheduledExecutorServiceType target = null;
         target = (ManagedScheduledExecutorServiceType)this.get_store().add_element_user(MANAGEDSCHEDULEDEXECUTORSERVICE$30);
         return target;
      }
   }

   public void removeManagedScheduledExecutorService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDSCHEDULEDEXECUTORSERVICE$30, i);
      }
   }

   public ManagedThreadFactoryType[] getManagedThreadFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDTHREADFACTORY$32, targetList);
         ManagedThreadFactoryType[] result = new ManagedThreadFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedThreadFactoryType getManagedThreadFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().find_element_user(MANAGEDTHREADFACTORY$32, i);
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
         return this.get_store().count_elements(MANAGEDTHREADFACTORY$32);
      }
   }

   public void setManagedThreadFactoryArray(ManagedThreadFactoryType[] managedThreadFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedThreadFactoryArray, MANAGEDTHREADFACTORY$32);
   }

   public void setManagedThreadFactoryArray(int i, ManagedThreadFactoryType managedThreadFactory) {
      this.generatedSetterHelperImpl(managedThreadFactory, MANAGEDTHREADFACTORY$32, i, (short)2);
   }

   public ManagedThreadFactoryType insertNewManagedThreadFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().insert_element_user(MANAGEDTHREADFACTORY$32, i);
         return target;
      }
   }

   public ManagedThreadFactoryType addNewManagedThreadFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedThreadFactoryType target = null;
         target = (ManagedThreadFactoryType)this.get_store().add_element_user(MANAGEDTHREADFACTORY$32);
         return target;
      }
   }

   public void removeManagedThreadFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDTHREADFACTORY$32, i);
      }
   }

   public XsdStringType getComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(COMPONENTFACTORYCLASSNAME$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPONENTFACTORYCLASSNAME$34) != 0;
      }
   }

   public void setComponentFactoryClassName(XsdStringType componentFactoryClassName) {
      this.generatedSetterHelperImpl(componentFactoryClassName, COMPONENTFACTORYCLASSNAME$34, 0, (short)1);
   }

   public XsdStringType addNewComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(COMPONENTFACTORYCLASSNAME$34);
         return target;
      }
   }

   public void unsetComponentFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPONENTFACTORYCLASSNAME$34, 0);
      }
   }

   public WeblogicCompatibilityType getWeblogicCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicCompatibilityType target = null;
         target = (WeblogicCompatibilityType)this.get_store().find_element_user(WEBLOGICCOMPATIBILITY$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWeblogicCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICCOMPATIBILITY$36) != 0;
      }
   }

   public void setWeblogicCompatibility(WeblogicCompatibilityType weblogicCompatibility) {
      this.generatedSetterHelperImpl(weblogicCompatibility, WEBLOGICCOMPATIBILITY$36, 0, (short)1);
   }

   public WeblogicCompatibilityType addNewWeblogicCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicCompatibilityType target = null;
         target = (WeblogicCompatibilityType)this.get_store().add_element_user(WEBLOGICCOMPATIBILITY$36);
         return target;
      }
   }

   public void unsetWeblogicCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICCOMPATIBILITY$36, 0);
      }
   }

   public CoherenceClusterRefType getCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterRefType target = null;
         target = (CoherenceClusterRefType)this.get_store().find_element_user(COHERENCECLUSTERREF$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERREF$38) != 0;
      }
   }

   public void setCoherenceClusterRef(CoherenceClusterRefType coherenceClusterRef) {
      this.generatedSetterHelperImpl(coherenceClusterRef, COHERENCECLUSTERREF$38, 0, (short)1);
   }

   public CoherenceClusterRefType addNewCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterRefType target = null;
         target = (CoherenceClusterRefType)this.get_store().add_element_user(COHERENCECLUSTERREF$38);
         return target;
      }
   }

   public void unsetCoherenceClusterRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERREF$38, 0);
      }
   }

   public CdiDescriptorType getCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().find_element_user(CDIDESCRIPTOR$40, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CDIDESCRIPTOR$40) != 0;
      }
   }

   public void setCdiDescriptor(CdiDescriptorType cdiDescriptor) {
      this.generatedSetterHelperImpl(cdiDescriptor, CDIDESCRIPTOR$40, 0, (short)1);
   }

   public CdiDescriptorType addNewCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CdiDescriptorType target = null;
         target = (CdiDescriptorType)this.get_store().add_element_user(CDIDESCRIPTOR$40);
         return target;
      }
   }

   public void unsetCdiDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CDIDESCRIPTOR$40, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$42);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$42);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$42) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$42);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$42);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$42);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$42);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$42);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$44);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$44);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$44) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$44);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$44);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$44);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$44);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$44);
      }
   }
}
