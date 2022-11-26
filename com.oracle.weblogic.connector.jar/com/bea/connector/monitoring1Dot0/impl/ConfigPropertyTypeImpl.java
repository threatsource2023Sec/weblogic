package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConfigPropertyType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConfigPropertyTypeImpl extends XmlComplexContentImpl implements ConfigPropertyType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "description");
   private static final QName NAME$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "name");
   private static final QName TYPE$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "type");
   private static final QName VALUE$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "value");
   private static final QName IGNORE$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "ignore");
   private static final QName SUPPORTSDYNAMICUPDATES$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "supports-dynamic-updates");
   private static final QName CONFIDENTIAL$12 = new QName("http://www.bea.com/connector/monitoring1dot0", "confidential");

   public ConfigPropertyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, i);
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

   public void setDescriptionArray(String[] descriptionArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(int i, String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(description);
         }
      }
   }

   public void xsetDescriptionArray(XmlString[] descriptionArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
      }
   }

   public void xsetDescriptionArray(int i, XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(description);
         }
      }
   }

   public void insertDescription(int i, String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DESCRIPTION$0, i);
         target.setStringValue(description);
      }
   }

   public void addDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         target.setStringValue(description);
      }
   }

   public XmlString insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public XmlString addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$2, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$2);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$2);
         }

         target.set(name);
      }
   }

   public String getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPE$4, 0);
         return target;
      }
   }

   public void setType(String type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPE$4);
         }

         target.setStringValue(type);
      }
   }

   public void xsetType(XmlString type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPE$4);
         }

         target.set(type);
      }
   }

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALUE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$6, 0);
         return target;
      }
   }

   public void setValue(String value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALUE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALUE$6);
         }

         target.setStringValue(value);
      }
   }

   public void xsetValue(XmlString value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VALUE$6);
         }

         target.set(value);
      }
   }

   public boolean getIgnore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORE$8, 0);
         return target;
      }
   }

   public void setIgnore(boolean ignore) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNORE$8);
         }

         target.setBooleanValue(ignore);
      }
   }

   public void xsetIgnore(XmlBoolean ignore) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNORE$8);
         }

         target.set(ignore);
      }
   }

   public boolean getSupportsDynamicUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDYNAMICUPDATES$10, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsDynamicUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDYNAMICUPDATES$10, 0);
         return target;
      }
   }

   public void setSupportsDynamicUpdates(boolean supportsDynamicUpdates) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDYNAMICUPDATES$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSDYNAMICUPDATES$10);
         }

         target.setBooleanValue(supportsDynamicUpdates);
      }
   }

   public void xsetSupportsDynamicUpdates(XmlBoolean supportsDynamicUpdates) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDYNAMICUPDATES$10, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSDYNAMICUPDATES$10);
         }

         target.set(supportsDynamicUpdates);
      }
   }

   public boolean getConfidential() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIDENTIAL$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetConfidential() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONFIDENTIAL$12, 0);
         return target;
      }
   }

   public void setConfidential(boolean confidential) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIDENTIAL$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIDENTIAL$12);
         }

         target.setBooleanValue(confidential);
      }
   }

   public void xsetConfidential(XmlBoolean confidential) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONFIDENTIAL$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CONFIDENTIAL$12);
         }

         target.set(confidential);
      }
   }
}
