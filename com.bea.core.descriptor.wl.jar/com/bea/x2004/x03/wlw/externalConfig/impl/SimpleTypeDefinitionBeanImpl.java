package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.MemberConstraintBean;
import com.bea.x2004.x03.wlw.externalConfig.SimpleTypeDefinitionBean;
import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SimpleTypeDefinitionBeanImpl extends XmlComplexContentImpl implements SimpleTypeDefinitionBean {
   private static final long serialVersionUID = 1L;
   private static final QName BASETYPE$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "base-type");
   private static final QName CONSTRAINT$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "constraint");
   private static final QName REQUIRESENCRYPTION$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "requires-encryption");
   private static final QName DEFAULTVALUE$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "default-value");

   public SimpleTypeDefinitionBeanImpl(SchemaType sType) {
      super(sType);
   }

   public SimpleTypeDefinitionBean.BaseType.Enum getBaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASETYPE$0, 0);
         return target == null ? null : (SimpleTypeDefinitionBean.BaseType.Enum)target.getEnumValue();
      }
   }

   public SimpleTypeDefinitionBean.BaseType xgetBaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleTypeDefinitionBean.BaseType target = null;
         target = (SimpleTypeDefinitionBean.BaseType)this.get_store().find_element_user(BASETYPE$0, 0);
         return target;
      }
   }

   public void setBaseType(SimpleTypeDefinitionBean.BaseType.Enum baseType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASETYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASETYPE$0);
         }

         target.setEnumValue(baseType);
      }
   }

   public void xsetBaseType(SimpleTypeDefinitionBean.BaseType baseType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleTypeDefinitionBean.BaseType target = null;
         target = (SimpleTypeDefinitionBean.BaseType)this.get_store().find_element_user(BASETYPE$0, 0);
         if (target == null) {
            target = (SimpleTypeDefinitionBean.BaseType)this.get_store().add_element_user(BASETYPE$0);
         }

         target.set(baseType);
      }
   }

   public MemberConstraintBean getConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberConstraintBean target = null;
         target = (MemberConstraintBean)this.get_store().find_element_user(CONSTRAINT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSTRAINT$2) != 0;
      }
   }

   public void setConstraint(MemberConstraintBean constraint) {
      this.generatedSetterHelperImpl(constraint, CONSTRAINT$2, 0, (short)1);
   }

   public MemberConstraintBean addNewConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberConstraintBean target = null;
         target = (MemberConstraintBean)this.get_store().add_element_user(CONSTRAINT$2);
         return target;
      }
   }

   public void unsetConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSTRAINT$2, 0);
      }
   }

   public boolean getRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESENCRYPTION$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESENCRYPTION$4, 0);
         return target;
      }
   }

   public boolean isSetRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESENCRYPTION$4) != 0;
      }
   }

   public void setRequiresEncryption(boolean requiresEncryption) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESENCRYPTION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESENCRYPTION$4);
         }

         target.setBooleanValue(requiresEncryption);
      }
   }

   public void xsetRequiresEncryption(XmlBoolean requiresEncryption) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESENCRYPTION$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESENCRYPTION$4);
         }

         target.set(requiresEncryption);
      }
   }

   public void unsetRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESENCRYPTION$4, 0);
      }
   }

   public String[] getDefaultValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DEFAULTVALUE$6, targetList);
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
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTVALUE$6, i);
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
         this.get_store().find_all_element_users(DEFAULTVALUE$6, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetDefaultValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTVALUE$6, i);
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
         return this.get_store().count_elements(DEFAULTVALUE$6);
      }
   }

   public void setDefaultValueArray(String[] defaultValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(defaultValueArray, DEFAULTVALUE$6);
      }
   }

   public void setDefaultValueArray(int i, String defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTVALUE$6, i);
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
         this.arraySetterHelper(defaultValueArray, DEFAULTVALUE$6);
      }
   }

   public void xsetDefaultValueArray(int i, XmlString defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTVALUE$6, i);
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
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DEFAULTVALUE$6, i);
         target.setStringValue(defaultValue);
      }
   }

   public void addDefaultValue(String defaultValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DEFAULTVALUE$6);
         target.setStringValue(defaultValue);
      }
   }

   public XmlString insertNewDefaultValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(DEFAULTVALUE$6, i);
         return target;
      }
   }

   public XmlString addNewDefaultValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(DEFAULTVALUE$6);
         return target;
      }
   }

   public void removeDefaultValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTVALUE$6, i);
      }
   }

   public static class BaseTypeImpl extends JavaStringEnumerationHolderEx implements SimpleTypeDefinitionBean.BaseType {
      private static final long serialVersionUID = 1L;

      public BaseTypeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected BaseTypeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
