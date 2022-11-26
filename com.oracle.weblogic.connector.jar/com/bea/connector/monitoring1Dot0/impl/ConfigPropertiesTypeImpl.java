package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.connector.monitoring1Dot0.ConfigPropertyType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConfigPropertiesTypeImpl extends XmlComplexContentImpl implements ConfigPropertiesType {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTY$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "property");

   public ConfigPropertiesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ConfigPropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$0, targetList);
         ConfigPropertyType[] result = new ConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfigPropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().find_element_user(PROPERTY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTY$0);
      }
   }

   public void setPropertyArray(ConfigPropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$0);
   }

   public void setPropertyArray(int i, ConfigPropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$0, i, (short)2);
   }

   public ConfigPropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().insert_element_user(PROPERTY$0, i);
         return target;
      }
   }

   public ConfigPropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().add_element_user(PROPERTY$0);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$0, i);
      }
   }
}
