package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.AdminobjectType;
import com.sun.java.xml.ns.javaee.ConfigPropertyType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.InboundResourceadapterType;
import com.sun.java.xml.ns.javaee.OutboundResourceadapterType;
import com.sun.java.xml.ns.javaee.ResourceadapterType;
import com.sun.java.xml.ns.javaee.SecurityPermissionType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ResourceadapterTypeImpl extends XmlComplexContentImpl implements ResourceadapterType {
   private static final long serialVersionUID = 1L;
   private static final QName RESOURCEADAPTERCLASS$0 = new QName("http://java.sun.com/xml/ns/javaee", "resourceadapter-class");
   private static final QName CONFIGPROPERTY$2 = new QName("http://java.sun.com/xml/ns/javaee", "config-property");
   private static final QName OUTBOUNDRESOURCEADAPTER$4 = new QName("http://java.sun.com/xml/ns/javaee", "outbound-resourceadapter");
   private static final QName INBOUNDRESOURCEADAPTER$6 = new QName("http://java.sun.com/xml/ns/javaee", "inbound-resourceadapter");
   private static final QName ADMINOBJECT$8 = new QName("http://java.sun.com/xml/ns/javaee", "adminobject");
   private static final QName SECURITYPERMISSION$10 = new QName("http://java.sun.com/xml/ns/javaee", "security-permission");
   private static final QName ID$12 = new QName("", "id");

   public ResourceadapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getResourceadapterClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(RESOURCEADAPTERCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceadapterClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEADAPTERCLASS$0) != 0;
      }
   }

   public void setResourceadapterClass(FullyQualifiedClassType resourceadapterClass) {
      this.generatedSetterHelperImpl(resourceadapterClass, RESOURCEADAPTERCLASS$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewResourceadapterClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(RESOURCEADAPTERCLASS$0);
         return target;
      }
   }

   public void unsetResourceadapterClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEADAPTERCLASS$0, 0);
      }
   }

   public ConfigPropertyType[] getConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONFIGPROPERTY$2, targetList);
         ConfigPropertyType[] result = new ConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfigPropertyType getConfigPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().find_element_user(CONFIGPROPERTY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPROPERTY$2);
      }
   }

   public void setConfigPropertyArray(ConfigPropertyType[] configPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(configPropertyArray, CONFIGPROPERTY$2);
   }

   public void setConfigPropertyArray(int i, ConfigPropertyType configProperty) {
      this.generatedSetterHelperImpl(configProperty, CONFIGPROPERTY$2, i, (short)2);
   }

   public ConfigPropertyType insertNewConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().insert_element_user(CONFIGPROPERTY$2, i);
         return target;
      }
   }

   public ConfigPropertyType addNewConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().add_element_user(CONFIGPROPERTY$2);
         return target;
      }
   }

   public void removeConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTY$2, i);
      }
   }

   public OutboundResourceadapterType getOutboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundResourceadapterType target = null;
         target = (OutboundResourceadapterType)this.get_store().find_element_user(OUTBOUNDRESOURCEADAPTER$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOutboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OUTBOUNDRESOURCEADAPTER$4) != 0;
      }
   }

   public void setOutboundResourceadapter(OutboundResourceadapterType outboundResourceadapter) {
      this.generatedSetterHelperImpl(outboundResourceadapter, OUTBOUNDRESOURCEADAPTER$4, 0, (short)1);
   }

   public OutboundResourceadapterType addNewOutboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundResourceadapterType target = null;
         target = (OutboundResourceadapterType)this.get_store().add_element_user(OUTBOUNDRESOURCEADAPTER$4);
         return target;
      }
   }

   public void unsetOutboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OUTBOUNDRESOURCEADAPTER$4, 0);
      }
   }

   public InboundResourceadapterType getInboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundResourceadapterType target = null;
         target = (InboundResourceadapterType)this.get_store().find_element_user(INBOUNDRESOURCEADAPTER$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INBOUNDRESOURCEADAPTER$6) != 0;
      }
   }

   public void setInboundResourceadapter(InboundResourceadapterType inboundResourceadapter) {
      this.generatedSetterHelperImpl(inboundResourceadapter, INBOUNDRESOURCEADAPTER$6, 0, (short)1);
   }

   public InboundResourceadapterType addNewInboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundResourceadapterType target = null;
         target = (InboundResourceadapterType)this.get_store().add_element_user(INBOUNDRESOURCEADAPTER$6);
         return target;
      }
   }

   public void unsetInboundResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INBOUNDRESOURCEADAPTER$6, 0);
      }
   }

   public AdminobjectType[] getAdminobjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINOBJECT$8, targetList);
         AdminobjectType[] result = new AdminobjectType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdminobjectType getAdminobjectArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminobjectType target = null;
         target = (AdminobjectType)this.get_store().find_element_user(ADMINOBJECT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAdminobjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINOBJECT$8);
      }
   }

   public void setAdminobjectArray(AdminobjectType[] adminobjectArray) {
      this.check_orphaned();
      this.arraySetterHelper(adminobjectArray, ADMINOBJECT$8);
   }

   public void setAdminobjectArray(int i, AdminobjectType adminobject) {
      this.generatedSetterHelperImpl(adminobject, ADMINOBJECT$8, i, (short)2);
   }

   public AdminobjectType insertNewAdminobject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminobjectType target = null;
         target = (AdminobjectType)this.get_store().insert_element_user(ADMINOBJECT$8, i);
         return target;
      }
   }

   public AdminobjectType addNewAdminobject() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminobjectType target = null;
         target = (AdminobjectType)this.get_store().add_element_user(ADMINOBJECT$8);
         return target;
      }
   }

   public void removeAdminobject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINOBJECT$8, i);
      }
   }

   public SecurityPermissionType[] getSecurityPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYPERMISSION$10, targetList);
         SecurityPermissionType[] result = new SecurityPermissionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityPermissionType getSecurityPermissionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().find_element_user(SECURITYPERMISSION$10, i);
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
         return this.get_store().count_elements(SECURITYPERMISSION$10);
      }
   }

   public void setSecurityPermissionArray(SecurityPermissionType[] securityPermissionArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityPermissionArray, SECURITYPERMISSION$10);
   }

   public void setSecurityPermissionArray(int i, SecurityPermissionType securityPermission) {
      this.generatedSetterHelperImpl(securityPermission, SECURITYPERMISSION$10, i, (short)2);
   }

   public SecurityPermissionType insertNewSecurityPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().insert_element_user(SECURITYPERMISSION$10, i);
         return target;
      }
   }

   public SecurityPermissionType addNewSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().add_element_user(SECURITYPERMISSION$10);
         return target;
      }
   }

   public void removeSecurityPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYPERMISSION$10, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
