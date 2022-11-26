package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.AdminObjectGroupType;
import com.oracle.xmlns.weblogic.weblogicConnector.AdminObjectInstanceType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertiesType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AdminObjectGroupTypeImpl extends XmlComplexContentImpl implements AdminObjectGroupType {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTINTERFACE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "admin-object-interface");
   private static final QName ADMINOBJECTCLASS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "admin-object-class");
   private static final QName DEFAULTPROPERTIES$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-properties");
   private static final QName ADMINOBJECTINSTANCE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "admin-object-instance");

   public AdminObjectGroupTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getAdminObjectInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAdminObjectInterface(String adminObjectInterface) {
      this.generatedSetterHelperImpl(adminObjectInterface, ADMINOBJECTINTERFACE$0, 0, (short)1);
   }

   public String addNewAdminObjectInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ADMINOBJECTINTERFACE$0);
         return target;
      }
   }

   public String getAdminObjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(ADMINOBJECTCLASS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAdminObjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINOBJECTCLASS$2) != 0;
      }
   }

   public void setAdminObjectClass(String adminObjectClass) {
      this.generatedSetterHelperImpl(adminObjectClass, ADMINOBJECTCLASS$2, 0, (short)1);
   }

   public String addNewAdminObjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ADMINOBJECTCLASS$2);
         return target;
      }
   }

   public void unsetAdminObjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINOBJECTCLASS$2, 0);
      }
   }

   public ConfigPropertiesType getDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(DEFAULTPROPERTIES$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTPROPERTIES$4) != 0;
      }
   }

   public void setDefaultProperties(ConfigPropertiesType defaultProperties) {
      this.generatedSetterHelperImpl(defaultProperties, DEFAULTPROPERTIES$4, 0, (short)1);
   }

   public ConfigPropertiesType addNewDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(DEFAULTPROPERTIES$4);
         return target;
      }
   }

   public void unsetDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTPROPERTIES$4, 0);
      }
   }

   public AdminObjectInstanceType[] getAdminObjectInstanceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINOBJECTINSTANCE$6, targetList);
         AdminObjectInstanceType[] result = new AdminObjectInstanceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdminObjectInstanceType getAdminObjectInstanceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectInstanceType target = null;
         target = (AdminObjectInstanceType)this.get_store().find_element_user(ADMINOBJECTINSTANCE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAdminObjectInstanceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINOBJECTINSTANCE$6);
      }
   }

   public void setAdminObjectInstanceArray(AdminObjectInstanceType[] adminObjectInstanceArray) {
      this.check_orphaned();
      this.arraySetterHelper(adminObjectInstanceArray, ADMINOBJECTINSTANCE$6);
   }

   public void setAdminObjectInstanceArray(int i, AdminObjectInstanceType adminObjectInstance) {
      this.generatedSetterHelperImpl(adminObjectInstance, ADMINOBJECTINSTANCE$6, i, (short)2);
   }

   public AdminObjectInstanceType insertNewAdminObjectInstance(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectInstanceType target = null;
         target = (AdminObjectInstanceType)this.get_store().insert_element_user(ADMINOBJECTINSTANCE$6, i);
         return target;
      }
   }

   public AdminObjectInstanceType addNewAdminObjectInstance() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectInstanceType target = null;
         target = (AdminObjectInstanceType)this.get_store().add_element_user(ADMINOBJECTINSTANCE$6);
         return target;
      }
   }

   public void removeAdminObjectInstance(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINOBJECTINSTANCE$6, i);
      }
   }
}
