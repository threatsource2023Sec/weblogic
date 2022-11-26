package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ActivationConfigPropertyType;
import org.jcp.xmlns.xml.ns.javaee.ActivationConfigType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;

public class ActivationConfigTypeImpl extends XmlComplexContentImpl implements ActivationConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName ACTIVATIONCONFIGPROPERTY$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "activation-config-property");
   private static final QName ID$4 = new QName("", "id");

   public ActivationConfigTypeImpl(SchemaType sType) {
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

   public ActivationConfigPropertyType[] getActivationConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ACTIVATIONCONFIGPROPERTY$2, targetList);
         ActivationConfigPropertyType[] result = new ActivationConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ActivationConfigPropertyType getActivationConfigPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigPropertyType target = null;
         target = (ActivationConfigPropertyType)this.get_store().find_element_user(ACTIVATIONCONFIGPROPERTY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfActivationConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACTIVATIONCONFIGPROPERTY$2);
      }
   }

   public void setActivationConfigPropertyArray(ActivationConfigPropertyType[] activationConfigPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(activationConfigPropertyArray, ACTIVATIONCONFIGPROPERTY$2);
   }

   public void setActivationConfigPropertyArray(int i, ActivationConfigPropertyType activationConfigProperty) {
      this.generatedSetterHelperImpl(activationConfigProperty, ACTIVATIONCONFIGPROPERTY$2, i, (short)2);
   }

   public ActivationConfigPropertyType insertNewActivationConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigPropertyType target = null;
         target = (ActivationConfigPropertyType)this.get_store().insert_element_user(ACTIVATIONCONFIGPROPERTY$2, i);
         return target;
      }
   }

   public ActivationConfigPropertyType addNewActivationConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigPropertyType target = null;
         target = (ActivationConfigPropertyType)this.get_store().add_element_user(ACTIVATIONCONFIGPROPERTY$2);
         return target;
      }
   }

   public void removeActivationConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACTIVATIONCONFIGPROPERTY$2, i);
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
