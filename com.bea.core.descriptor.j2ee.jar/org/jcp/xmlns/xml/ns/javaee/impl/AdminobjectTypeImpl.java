package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AdminobjectType;
import org.jcp.xmlns.xml.ns.javaee.ConfigPropertyType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;

public class AdminobjectTypeImpl extends XmlComplexContentImpl implements AdminobjectType {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTINTERFACE$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "adminobject-interface");
   private static final QName ADMINOBJECTCLASS$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "adminobject-class");
   private static final QName CONFIGPROPERTY$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "config-property");
   private static final QName ID$6 = new QName("", "id");

   public AdminobjectTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getAdminobjectInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAdminobjectInterface(FullyQualifiedClassType adminobjectInterface) {
      this.generatedSetterHelperImpl(adminobjectInterface, ADMINOBJECTINTERFACE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewAdminobjectInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(ADMINOBJECTINTERFACE$0);
         return target;
      }
   }

   public FullyQualifiedClassType getAdminobjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(ADMINOBJECTCLASS$2, 0);
         return target == null ? null : target;
      }
   }

   public void setAdminobjectClass(FullyQualifiedClassType adminobjectClass) {
      this.generatedSetterHelperImpl(adminobjectClass, ADMINOBJECTCLASS$2, 0, (short)1);
   }

   public FullyQualifiedClassType addNewAdminobjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(ADMINOBJECTCLASS$2);
         return target;
      }
   }

   public ConfigPropertyType[] getConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONFIGPROPERTY$4, targetList);
         ConfigPropertyType[] result = new ConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfigPropertyType getConfigPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().find_element_user(CONFIGPROPERTY$4, i);
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
         return this.get_store().count_elements(CONFIGPROPERTY$4);
      }
   }

   public void setConfigPropertyArray(ConfigPropertyType[] configPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(configPropertyArray, CONFIGPROPERTY$4);
   }

   public void setConfigPropertyArray(int i, ConfigPropertyType configProperty) {
      this.generatedSetterHelperImpl(configProperty, CONFIGPROPERTY$4, i, (short)2);
   }

   public ConfigPropertyType insertNewConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().insert_element_user(CONFIGPROPERTY$4, i);
         return target;
      }
   }

   public ConfigPropertyType addNewConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().add_element_user(CONFIGPROPERTY$4);
         return target;
      }
   }

   public void removeConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTY$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
