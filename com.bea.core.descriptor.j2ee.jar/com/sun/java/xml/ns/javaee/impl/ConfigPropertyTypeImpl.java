package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.ConfigPropertyNameType;
import com.sun.java.xml.ns.javaee.ConfigPropertyType;
import com.sun.java.xml.ns.javaee.ConfigPropertyTypeType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConfigPropertyTypeImpl extends XmlComplexContentImpl implements ConfigPropertyType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName CONFIGPROPERTYNAME$2 = new QName("http://java.sun.com/xml/ns/javaee", "config-property-name");
   private static final QName CONFIGPROPERTYTYPE$4 = new QName("http://java.sun.com/xml/ns/javaee", "config-property-type");
   private static final QName CONFIGPROPERTYVALUE$6 = new QName("http://java.sun.com/xml/ns/javaee", "config-property-value");
   private static final QName CONFIGPROPERTYIGNORE$8 = new QName("http://java.sun.com/xml/ns/javaee", "config-property-ignore");
   private static final QName CONFIGPROPERTYSUPPORTSDYNAMICUPDATES$10 = new QName("http://java.sun.com/xml/ns/javaee", "config-property-supports-dynamic-updates");
   private static final QName CONFIGPROPERTYCONFIDENTIAL$12 = new QName("http://java.sun.com/xml/ns/javaee", "config-property-confidential");
   private static final QName ID$14 = new QName("", "id");

   public ConfigPropertyTypeImpl(SchemaType sType) {
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

   public ConfigPropertyNameType getConfigPropertyName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyNameType target = null;
         target = (ConfigPropertyNameType)this.get_store().find_element_user(CONFIGPROPERTYNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setConfigPropertyName(ConfigPropertyNameType configPropertyName) {
      this.generatedSetterHelperImpl(configPropertyName, CONFIGPROPERTYNAME$2, 0, (short)1);
   }

   public ConfigPropertyNameType addNewConfigPropertyName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyNameType target = null;
         target = (ConfigPropertyNameType)this.get_store().add_element_user(CONFIGPROPERTYNAME$2);
         return target;
      }
   }

   public ConfigPropertyTypeType getConfigPropertyType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyTypeType target = null;
         target = (ConfigPropertyTypeType)this.get_store().find_element_user(CONFIGPROPERTYTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setConfigPropertyType(ConfigPropertyTypeType configPropertyType) {
      this.generatedSetterHelperImpl(configPropertyType, CONFIGPROPERTYTYPE$4, 0, (short)1);
   }

   public ConfigPropertyTypeType addNewConfigPropertyType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyTypeType target = null;
         target = (ConfigPropertyTypeType)this.get_store().add_element_user(CONFIGPROPERTYTYPE$4);
         return target;
      }
   }

   public XsdStringType getConfigPropertyValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(CONFIGPROPERTYVALUE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConfigPropertyValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPROPERTYVALUE$6) != 0;
      }
   }

   public void setConfigPropertyValue(XsdStringType configPropertyValue) {
      this.generatedSetterHelperImpl(configPropertyValue, CONFIGPROPERTYVALUE$6, 0, (short)1);
   }

   public XsdStringType addNewConfigPropertyValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(CONFIGPROPERTYVALUE$6);
         return target;
      }
   }

   public void unsetConfigPropertyValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTYVALUE$6, 0);
      }
   }

   public TrueFalseType getConfigPropertyIgnore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CONFIGPROPERTYIGNORE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConfigPropertyIgnore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPROPERTYIGNORE$8) != 0;
      }
   }

   public void setConfigPropertyIgnore(TrueFalseType configPropertyIgnore) {
      this.generatedSetterHelperImpl(configPropertyIgnore, CONFIGPROPERTYIGNORE$8, 0, (short)1);
   }

   public TrueFalseType addNewConfigPropertyIgnore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CONFIGPROPERTYIGNORE$8);
         return target;
      }
   }

   public void unsetConfigPropertyIgnore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTYIGNORE$8, 0);
      }
   }

   public TrueFalseType getConfigPropertySupportsDynamicUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CONFIGPROPERTYSUPPORTSDYNAMICUPDATES$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConfigPropertySupportsDynamicUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPROPERTYSUPPORTSDYNAMICUPDATES$10) != 0;
      }
   }

   public void setConfigPropertySupportsDynamicUpdates(TrueFalseType configPropertySupportsDynamicUpdates) {
      this.generatedSetterHelperImpl(configPropertySupportsDynamicUpdates, CONFIGPROPERTYSUPPORTSDYNAMICUPDATES$10, 0, (short)1);
   }

   public TrueFalseType addNewConfigPropertySupportsDynamicUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CONFIGPROPERTYSUPPORTSDYNAMICUPDATES$10);
         return target;
      }
   }

   public void unsetConfigPropertySupportsDynamicUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTYSUPPORTSDYNAMICUPDATES$10, 0);
      }
   }

   public TrueFalseType getConfigPropertyConfidential() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CONFIGPROPERTYCONFIDENTIAL$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConfigPropertyConfidential() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPROPERTYCONFIDENTIAL$12) != 0;
      }
   }

   public void setConfigPropertyConfidential(TrueFalseType configPropertyConfidential) {
      this.generatedSetterHelperImpl(configPropertyConfidential, CONFIGPROPERTYCONFIDENTIAL$12, 0, (short)1);
   }

   public TrueFalseType addNewConfigPropertyConfidential() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CONFIGPROPERTYCONFIDENTIAL$12);
         return target;
      }
   }

   public void unsetConfigPropertyConfidential() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTYCONFIDENTIAL$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
