package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Properties;
import org.jcp.xmlns.xml.ns.javaee.Property;

public class PropertiesImpl extends XmlComplexContentImpl implements Properties {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTY$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "property");
   private static final QName PARTITION$2 = new QName("", "partition");

   public PropertiesImpl(SchemaType sType) {
      super(sType);
   }

   public Property[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$0, targetList);
         Property[] result = new Property[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Property getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Property target = null;
         target = (Property)this.get_store().find_element_user(PROPERTY$0, i);
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

   public void setPropertyArray(Property[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$0);
   }

   public void setPropertyArray(int i, Property property) {
      this.generatedSetterHelperImpl(property, PROPERTY$0, i, (short)2);
   }

   public Property insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Property target = null;
         target = (Property)this.get_store().insert_element_user(PROPERTY$0, i);
         return target;
      }
   }

   public Property addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Property target = null;
         target = (Property)this.get_store().add_element_user(PROPERTY$0);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$0, i);
      }
   }

   public String getPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PARTITION$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PARTITION$2);
         return target;
      }
   }

   public boolean isSetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(PARTITION$2) != null;
      }
   }

   public void setPartition(String partition) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PARTITION$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(PARTITION$2);
         }

         target.setStringValue(partition);
      }
   }

   public void xsetPartition(XmlString partition) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PARTITION$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(PARTITION$2);
         }

         target.set(partition);
      }
   }

   public void unsetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(PARTITION$2);
      }
   }
}
