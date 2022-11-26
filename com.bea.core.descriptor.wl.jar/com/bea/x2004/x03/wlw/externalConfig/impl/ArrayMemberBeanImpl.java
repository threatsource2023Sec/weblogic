package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.ArrayMemberBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ArrayMemberBeanImpl extends XmlComplexContentImpl implements ArrayMemberBean {
   private static final long serialVersionUID = 1L;
   private static final QName MEMBERNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-name");
   private static final QName MEMBERVALUE$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-value");
   private static final QName OVERRIDEVALUE$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "override-value");
   private static final QName REQUIRESENCRYPTION$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "requires-encryption");
   private static final QName CLEARTEXTOVERRIDEVALUE$8 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "cleartext-override-value");
   private static final QName SECUREDOVERRIDEVALUEENCRYPTED$10 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "secured-override-value-encrypted");

   public ArrayMemberBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getMemberName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMemberName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERNAME$0, 0);
         return target;
      }
   }

   public void setMemberName(String memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MEMBERNAME$0);
         }

         target.setStringValue(memberName);
      }
   }

   public void xsetMemberName(XmlString memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MEMBERNAME$0);
         }

         target.set(memberName);
      }
   }

   public String[] getMemberValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MEMBERVALUE$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getMemberValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetMemberValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MEMBERVALUE$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetMemberValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMemberValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEMBERVALUE$2);
      }
   }

   public void setMemberValueArray(String[] memberValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(memberValueArray, MEMBERVALUE$2);
      }
   }

   public void setMemberValueArray(int i, String memberValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(memberValue);
         }
      }
   }

   public void xsetMemberValueArray(XmlString[] memberValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(memberValueArray, MEMBERVALUE$2);
      }
   }

   public void xsetMemberValueArray(int i, XmlString memberValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERVALUE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(memberValue);
         }
      }
   }

   public void insertMemberValue(int i, String memberValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(MEMBERVALUE$2, i);
         target.setStringValue(memberValue);
      }
   }

   public void addMemberValue(String memberValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(MEMBERVALUE$2);
         target.setStringValue(memberValue);
      }
   }

   public XmlString insertNewMemberValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(MEMBERVALUE$2, i);
         return target;
      }
   }

   public XmlString addNewMemberValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(MEMBERVALUE$2);
         return target;
      }
   }

   public void removeMemberValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEMBERVALUE$2, i);
      }
   }

   public String[] getOverrideValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OVERRIDEVALUE$4, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getOverrideValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OVERRIDEVALUE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetOverrideValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OVERRIDEVALUE$4, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetOverrideValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OVERRIDEVALUE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOverrideValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OVERRIDEVALUE$4);
      }
   }

   public void setOverrideValueArray(String[] overrideValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(overrideValueArray, OVERRIDEVALUE$4);
      }
   }

   public void setOverrideValueArray(int i, String overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OVERRIDEVALUE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(overrideValue);
         }
      }
   }

   public void xsetOverrideValueArray(XmlString[] overrideValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(overrideValueArray, OVERRIDEVALUE$4);
      }
   }

   public void xsetOverrideValueArray(int i, XmlString overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OVERRIDEVALUE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(overrideValue);
         }
      }
   }

   public void insertOverrideValue(int i, String overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(OVERRIDEVALUE$4, i);
         target.setStringValue(overrideValue);
      }
   }

   public void addOverrideValue(String overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(OVERRIDEVALUE$4);
         target.setStringValue(overrideValue);
      }
   }

   public XmlString insertNewOverrideValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(OVERRIDEVALUE$4, i);
         return target;
      }
   }

   public XmlString addNewOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(OVERRIDEVALUE$4);
         return target;
      }
   }

   public void removeOverrideValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OVERRIDEVALUE$4, i);
      }
   }

   public boolean getRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESENCRYPTION$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESENCRYPTION$6, 0);
         return target;
      }
   }

   public boolean isSetRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESENCRYPTION$6) != 0;
      }
   }

   public void setRequiresEncryption(boolean requiresEncryption) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESENCRYPTION$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESENCRYPTION$6);
         }

         target.setBooleanValue(requiresEncryption);
      }
   }

   public void xsetRequiresEncryption(XmlBoolean requiresEncryption) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESENCRYPTION$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESENCRYPTION$6);
         }

         target.set(requiresEncryption);
      }
   }

   public void unsetRequiresEncryption() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESENCRYPTION$6, 0);
      }
   }

   public String[] getCleartextOverrideValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CLEARTEXTOVERRIDEVALUE$8, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getCleartextOverrideValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetCleartextOverrideValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CLEARTEXTOVERRIDEVALUE$8, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetCleartextOverrideValueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCleartextOverrideValueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLEARTEXTOVERRIDEVALUE$8);
      }
   }

   public void setCleartextOverrideValueArray(String[] cleartextOverrideValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(cleartextOverrideValueArray, CLEARTEXTOVERRIDEVALUE$8);
      }
   }

   public void setCleartextOverrideValueArray(int i, String cleartextOverrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(cleartextOverrideValue);
         }
      }
   }

   public void xsetCleartextOverrideValueArray(XmlString[] cleartextOverrideValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(cleartextOverrideValueArray, CLEARTEXTOVERRIDEVALUE$8);
      }
   }

   public void xsetCleartextOverrideValueArray(int i, XmlString cleartextOverrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(cleartextOverrideValue);
         }
      }
   }

   public void insertCleartextOverrideValue(int i, String cleartextOverrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(CLEARTEXTOVERRIDEVALUE$8, i);
         target.setStringValue(cleartextOverrideValue);
      }
   }

   public void addCleartextOverrideValue(String cleartextOverrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(CLEARTEXTOVERRIDEVALUE$8);
         target.setStringValue(cleartextOverrideValue);
      }
   }

   public XmlString insertNewCleartextOverrideValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(CLEARTEXTOVERRIDEVALUE$8, i);
         return target;
      }
   }

   public XmlString addNewCleartextOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(CLEARTEXTOVERRIDEVALUE$8);
         return target;
      }
   }

   public void removeCleartextOverrideValue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLEARTEXTOVERRIDEVALUE$8, i);
      }
   }

   public String getSecuredOverrideValueEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SECUREDOVERRIDEVALUEENCRYPTED$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSecuredOverrideValueEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SECUREDOVERRIDEVALUEENCRYPTED$10, 0);
         return target;
      }
   }

   public boolean isSetSecuredOverrideValueEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECUREDOVERRIDEVALUEENCRYPTED$10) != 0;
      }
   }

   public void setSecuredOverrideValueEncrypted(String securedOverrideValueEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SECUREDOVERRIDEVALUEENCRYPTED$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SECUREDOVERRIDEVALUEENCRYPTED$10);
         }

         target.setStringValue(securedOverrideValueEncrypted);
      }
   }

   public void xsetSecuredOverrideValueEncrypted(XmlString securedOverrideValueEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SECUREDOVERRIDEVALUEENCRYPTED$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SECUREDOVERRIDEVALUEENCRYPTED$10);
         }

         target.set(securedOverrideValueEncrypted);
      }
   }

   public void unsetSecuredOverrideValueEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECUREDOVERRIDEVALUEENCRYPTED$10, 0);
      }
   }
}
