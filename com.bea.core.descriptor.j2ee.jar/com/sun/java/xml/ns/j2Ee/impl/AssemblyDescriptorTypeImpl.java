package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.AssemblyDescriptorType;
import com.sun.java.xml.ns.j2Ee.ContainerTransactionType;
import com.sun.java.xml.ns.j2Ee.ExcludeListType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationType;
import com.sun.java.xml.ns.j2Ee.MethodPermissionType;
import com.sun.java.xml.ns.j2Ee.SecurityRoleType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AssemblyDescriptorTypeImpl extends XmlComplexContentImpl implements AssemblyDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName SECURITYROLE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "security-role");
   private static final QName METHODPERMISSION$2 = new QName("http://java.sun.com/xml/ns/j2ee", "method-permission");
   private static final QName CONTAINERTRANSACTION$4 = new QName("http://java.sun.com/xml/ns/j2ee", "container-transaction");
   private static final QName MESSAGEDESTINATION$6 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination");
   private static final QName EXCLUDELIST$8 = new QName("http://java.sun.com/xml/ns/j2ee", "exclude-list");
   private static final QName ID$10 = new QName("", "id");

   public AssemblyDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SecurityRoleType[] getSecurityRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLE$0, targetList);
         SecurityRoleType[] result = new SecurityRoleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleType getSecurityRoleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().find_element_user(SECURITYROLE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLE$0);
      }
   }

   public void setSecurityRoleArray(SecurityRoleType[] securityRoleArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleArray, SECURITYROLE$0);
   }

   public void setSecurityRoleArray(int i, SecurityRoleType securityRole) {
      this.generatedSetterHelperImpl(securityRole, SECURITYROLE$0, i, (short)2);
   }

   public SecurityRoleType insertNewSecurityRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().insert_element_user(SECURITYROLE$0, i);
         return target;
      }
   }

   public SecurityRoleType addNewSecurityRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().add_element_user(SECURITYROLE$0);
         return target;
      }
   }

   public void removeSecurityRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLE$0, i);
      }
   }

   public MethodPermissionType[] getMethodPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHODPERMISSION$2, targetList);
         MethodPermissionType[] result = new MethodPermissionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MethodPermissionType getMethodPermissionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodPermissionType target = null;
         target = (MethodPermissionType)this.get_store().find_element_user(METHODPERMISSION$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMethodPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHODPERMISSION$2);
      }
   }

   public void setMethodPermissionArray(MethodPermissionType[] methodPermissionArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodPermissionArray, METHODPERMISSION$2);
   }

   public void setMethodPermissionArray(int i, MethodPermissionType methodPermission) {
      this.generatedSetterHelperImpl(methodPermission, METHODPERMISSION$2, i, (short)2);
   }

   public MethodPermissionType insertNewMethodPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodPermissionType target = null;
         target = (MethodPermissionType)this.get_store().insert_element_user(METHODPERMISSION$2, i);
         return target;
      }
   }

   public MethodPermissionType addNewMethodPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodPermissionType target = null;
         target = (MethodPermissionType)this.get_store().add_element_user(METHODPERMISSION$2);
         return target;
      }
   }

   public void removeMethodPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHODPERMISSION$2, i);
      }
   }

   public ContainerTransactionType[] getContainerTransactionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTAINERTRANSACTION$4, targetList);
         ContainerTransactionType[] result = new ContainerTransactionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ContainerTransactionType getContainerTransactionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContainerTransactionType target = null;
         target = (ContainerTransactionType)this.get_store().find_element_user(CONTAINERTRANSACTION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContainerTransactionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTAINERTRANSACTION$4);
      }
   }

   public void setContainerTransactionArray(ContainerTransactionType[] containerTransactionArray) {
      this.check_orphaned();
      this.arraySetterHelper(containerTransactionArray, CONTAINERTRANSACTION$4);
   }

   public void setContainerTransactionArray(int i, ContainerTransactionType containerTransaction) {
      this.generatedSetterHelperImpl(containerTransaction, CONTAINERTRANSACTION$4, i, (short)2);
   }

   public ContainerTransactionType insertNewContainerTransaction(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContainerTransactionType target = null;
         target = (ContainerTransactionType)this.get_store().insert_element_user(CONTAINERTRANSACTION$4, i);
         return target;
      }
   }

   public ContainerTransactionType addNewContainerTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContainerTransactionType target = null;
         target = (ContainerTransactionType)this.get_store().add_element_user(CONTAINERTRANSACTION$4);
         return target;
      }
   }

   public void removeContainerTransaction(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTAINERTRANSACTION$4, i);
      }
   }

   public MessageDestinationType[] getMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATION$6, targetList);
         MessageDestinationType[] result = new MessageDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationType getMessageDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().find_element_user(MESSAGEDESTINATION$6, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATION$6);
      }
   }

   public void setMessageDestinationArray(MessageDestinationType[] messageDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationArray, MESSAGEDESTINATION$6);
   }

   public void setMessageDestinationArray(int i, MessageDestinationType messageDestination) {
      this.generatedSetterHelperImpl(messageDestination, MESSAGEDESTINATION$6, i, (short)2);
   }

   public MessageDestinationType insertNewMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().insert_element_user(MESSAGEDESTINATION$6, i);
         return target;
      }
   }

   public MessageDestinationType addNewMessageDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().add_element_user(MESSAGEDESTINATION$6);
         return target;
      }
   }

   public void removeMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATION$6, i);
      }
   }

   public ExcludeListType getExcludeList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExcludeListType target = null;
         target = (ExcludeListType)this.get_store().find_element_user(EXCLUDELIST$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetExcludeList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDELIST$8) != 0;
      }
   }

   public void setExcludeList(ExcludeListType excludeList) {
      this.generatedSetterHelperImpl(excludeList, EXCLUDELIST$8, 0, (short)1);
   }

   public ExcludeListType addNewExcludeList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExcludeListType target = null;
         target = (ExcludeListType)this.get_store().add_element_user(EXCLUDELIST$8);
         return target;
      }
   }

   public void unsetExcludeList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDELIST$8, 0);
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
