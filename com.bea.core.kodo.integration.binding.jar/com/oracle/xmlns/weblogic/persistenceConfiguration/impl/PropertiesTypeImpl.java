package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PropertiesType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PropertyType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PropertiesTypeImpl extends XmlComplexContentImpl implements PropertiesType {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTY$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "property");

   public PropertiesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$0, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTY$0);
      }
   }

   public void setPropertyArray(PropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$0);
   }

   public void setPropertyArray(int i, PropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$0, i, (short)2);
   }

   public void setNilPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public PropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PROPERTY$0, i);
         return target;
      }
   }

   public PropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PROPERTY$0);
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
