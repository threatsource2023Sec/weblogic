package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.EnumDefinitionBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EnumDefinitionBeanImpl extends XmlComplexContentImpl implements EnumDefinitionBean {
   private static final long serialVersionUID = 1L;
   private static final QName ENUMCLASSNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "enum-class-name");
   private static final QName ENUMVALUE1$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "enum-value");

   public EnumDefinitionBeanImpl(SchemaType sType) {
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

   public String[] getEnumValue1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENUMVALUE1$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getEnumValue1Array(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENUMVALUE1$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetEnumValue1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENUMVALUE1$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetEnumValue1Array(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENUMVALUE1$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEnumValue1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENUMVALUE1$2);
      }
   }

   public void setEnumValue1Array(String[] enumValue1Array) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(enumValue1Array, ENUMVALUE1$2);
      }
   }

   public void setEnumValue1Array(int i, String enumValue1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENUMVALUE1$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(enumValue1);
         }
      }
   }

   public void xsetEnumValue1Array(XmlString[] enumValue1Array) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(enumValue1Array, ENUMVALUE1$2);
      }
   }

   public void xsetEnumValue1Array(int i, XmlString enumValue1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENUMVALUE1$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(enumValue1);
         }
      }
   }

   public void insertEnumValue1(int i, String enumValue1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(ENUMVALUE1$2, i);
         target.setStringValue(enumValue1);
      }
   }

   public void addEnumValue1(String enumValue1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(ENUMVALUE1$2);
         target.setStringValue(enumValue1);
      }
   }

   public XmlString insertNewEnumValue1(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(ENUMVALUE1$2, i);
         return target;
      }
   }

   public XmlString addNewEnumValue1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(ENUMVALUE1$2);
         return target;
      }
   }

   public void removeEnumValue1(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENUMVALUE1$2, i);
      }
   }
}
