package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.EnumRefBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EnumRefBeanImpl extends XmlComplexContentImpl implements EnumRefBean {
   private static final long serialVersionUID = 1L;
   private static final QName ENUMCLASSNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "enum-class-name");
   private static final QName DEFAULTVALUE$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "default-value");

   public EnumRefBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getEnumClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENUMCLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEnumClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENUMCLASSNAME$0, 0);
         return target;
      }
   }

   public void setEnumClassName(String enumClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENUMCLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENUMCLASSNAME$0);
         }

         target.setStringValue(enumClassName);
      }
   }

   public void xsetEnumClassName(XmlString enumClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENUMCLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ENUMCLASSNAME$0);
         }

         target.set(enumClassName);
      }
   }

   public String[] getDefaultValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DEFAULTVALUE$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getDefaultValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetDefaultValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DEFAULTVALUE$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetDefaultValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDefaultValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTVALUE$2);
      }
   }

   public void setDefaultValueArray(String[] defaultValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(defaultValueArray, DEFAULTVALUE$2);
      }
   }

   public void setDefaultValueArray(int i, String defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(defaultValue);
         }
      }
   }

   public void xsetDefaultValueArray(XmlString[] defaultValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(defaultValueArray, DEFAULTVALUE$2);
      }
   }

   public void xsetDefaultValueArray(int i, XmlString defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(defaultValue);
         }
      }
   }

   public void insertDefaultValue(int i, String defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DEFAULTVALUE$2, i);
         target.setStringValue(defaultValue);
      }
   }

   public void addDefaultValue(String defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DEFAULTVALUE$2);
         target.setStringValue(defaultValue);
      }
   }

   public XmlString insertNewDefaultValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(DEFAULTVALUE$2, i);
         return target;
      }
   }

   public XmlString addNewDefaultValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(DEFAULTVALUE$2);
         return target;
      }
   }

   public void removeDefaultValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTVALUE$2, i);
      }
   }
}
