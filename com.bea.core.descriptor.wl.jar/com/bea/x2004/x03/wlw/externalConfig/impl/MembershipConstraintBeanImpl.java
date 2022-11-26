package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.MembershipConstraintBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MembershipConstraintBeanImpl extends XmlComplexContentImpl implements MembershipConstraintBean {
   private static final long serialVersionUID = 1L;
   private static final QName MEMBERSHIPRULE$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "membership-rule");
   private static final QName MEMBERNAME$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-name");

   public MembershipConstraintBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getMembershipRule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERSHIPRULE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMembershipRule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERSHIPRULE$0, 0);
         return target;
      }
   }

   public void setMembershipRule(String membershipRule) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERSHIPRULE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MEMBERSHIPRULE$0);
         }

         target.setStringValue(membershipRule);
      }
   }

   public void xsetMembershipRule(XmlString membershipRule) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERSHIPRULE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MEMBERSHIPRULE$0);
         }

         target.set(membershipRule);
      }
   }

   public String[] getMemberNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MEMBERNAME$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getMemberNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetMemberNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MEMBERNAME$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetMemberNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMemberNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEMBERNAME$2);
      }
   }

   public void setMemberNameArray(String[] memberNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(memberNameArray, MEMBERNAME$2);
      }
   }

   public void setMemberNameArray(int i, String memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(memberName);
         }
      }
   }

   public void xsetMemberNameArray(XmlString[] memberNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(memberNameArray, MEMBERNAME$2);
      }
   }

   public void xsetMemberNameArray(int i, XmlString memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(memberName);
         }
      }
   }

   public void insertMemberName(int i, String memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(MEMBERNAME$2, i);
         target.setStringValue(memberName);
      }
   }

   public void addMemberName(String memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(MEMBERNAME$2);
         target.setStringValue(memberName);
      }
   }

   public XmlString insertNewMemberName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(MEMBERNAME$2, i);
         return target;
      }
   }

   public XmlString addNewMemberName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(MEMBERNAME$2);
         return target;
      }
   }

   public void removeMemberName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEMBERNAME$2, i);
      }
   }
}
