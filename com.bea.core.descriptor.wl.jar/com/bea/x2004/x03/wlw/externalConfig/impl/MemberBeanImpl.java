package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.MemberBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class MemberBeanImpl extends XmlComplexContentImpl implements MemberBean {
   private static final long serialVersionUID = 1L;
   private static final QName MEMBERNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-name");
   private static final QName MEMBERVALUE$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-value");
   private static final QName OVERRIDEVALUE$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "override-value");
   private static final QName REQUIRESENCRYPTION$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "requires-encryption");
   private static final QName CLEARTEXTOVERRIDEVALUE$8 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "cleartext-override-value");
   private static final QName SECUREDOVERRIDEVALUEENCRYPTED$10 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "secured-override-value-encrypted");

   public MemberBeanImpl(SchemaType sType) {
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

   public String getMemberValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERVALUE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMemberValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERVALUE$2, 0);
         return target;
      }
   }

   public boolean isSetMemberValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEMBERVALUE$2) != 0;
      }
   }

   public void setMemberValue(String memberValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERVALUE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MEMBERVALUE$2);
         }

         target.setStringValue(memberValue);
      }
   }

   public void xsetMemberValue(XmlString memberValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERVALUE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MEMBERVALUE$2);
         }

         target.set(memberValue);
      }
   }

   public void unsetMemberValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEMBERVALUE$2, 0);
      }
   }

   public String getOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OVERRIDEVALUE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OVERRIDEVALUE$4, 0);
         return target;
      }
   }

   public boolean isSetOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OVERRIDEVALUE$4) != 0;
      }
   }

   public void setOverrideValue(String overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OVERRIDEVALUE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OVERRIDEVALUE$4);
         }

         target.setStringValue(overrideValue);
      }
   }

   public void xsetOverrideValue(XmlString overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OVERRIDEVALUE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(OVERRIDEVALUE$4);
         }

         target.set(overrideValue);
      }
   }

   public void unsetOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OVERRIDEVALUE$4, 0);
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

   public String getCleartextOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCleartextOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, 0);
         return target;
      }
   }

   public boolean isSetCleartextOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLEARTEXTOVERRIDEVALUE$8) != 0;
      }
   }

   public void setCleartextOverrideValue(String cleartextOverrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLEARTEXTOVERRIDEVALUE$8);
         }

         target.setStringValue(cleartextOverrideValue);
      }
   }

   public void xsetCleartextOverrideValue(XmlString cleartextOverrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLEARTEXTOVERRIDEVALUE$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLEARTEXTOVERRIDEVALUE$8);
         }

         target.set(cleartextOverrideValue);
      }
   }

   public void unsetCleartextOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLEARTEXTOVERRIDEVALUE$8, 0);
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
