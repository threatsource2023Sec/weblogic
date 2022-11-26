package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.AdminObjectGroupType;
import com.oracle.xmlns.weblogic.weblogicConnector.AdminObjectsType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertiesType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AdminObjectsTypeImpl extends XmlComplexContentImpl implements AdminObjectsType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTPROPERTIES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-properties");
   private static final QName ADMINOBJECTGROUP$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "admin-object-group");
   private static final QName ID$4 = new QName("", "id");

   public AdminObjectsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ConfigPropertiesType getDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(DEFAULTPROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTPROPERTIES$0) != 0;
      }
   }

   public void setDefaultProperties(ConfigPropertiesType defaultProperties) {
      this.generatedSetterHelperImpl(defaultProperties, DEFAULTPROPERTIES$0, 0, (short)1);
   }

   public ConfigPropertiesType addNewDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(DEFAULTPROPERTIES$0);
         return target;
      }
   }

   public void unsetDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTPROPERTIES$0, 0);
      }
   }

   public AdminObjectGroupType[] getAdminObjectGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINOBJECTGROUP$2, targetList);
         AdminObjectGroupType[] result = new AdminObjectGroupType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdminObjectGroupType getAdminObjectGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectGroupType target = null;
         target = (AdminObjectGroupType)this.get_store().find_element_user(ADMINOBJECTGROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAdminObjectGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINOBJECTGROUP$2);
      }
   }

   public void setAdminObjectGroupArray(AdminObjectGroupType[] adminObjectGroupArray) {
      this.check_orphaned();
      this.arraySetterHelper(adminObjectGroupArray, ADMINOBJECTGROUP$2);
   }

   public void setAdminObjectGroupArray(int i, AdminObjectGroupType adminObjectGroup) {
      this.generatedSetterHelperImpl(adminObjectGroup, ADMINOBJECTGROUP$2, i, (short)2);
   }

   public AdminObjectGroupType insertNewAdminObjectGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectGroupType target = null;
         target = (AdminObjectGroupType)this.get_store().insert_element_user(ADMINOBJECTGROUP$2, i);
         return target;
      }
   }

   public AdminObjectGroupType addNewAdminObjectGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectGroupType target = null;
         target = (AdminObjectGroupType)this.get_store().add_element_user(ADMINOBJECTGROUP$2);
         return target;
      }
   }

   public void removeAdminObjectGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINOBJECTGROUP$2, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
