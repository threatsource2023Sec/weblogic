package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationDefinitionBean;
import com.bea.x2004.x03.wlw.externalConfig.MemberDefinitionBean;
import com.bea.x2004.x03.wlw.externalConfig.MembershipConstraintBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotationDefinitionBeanImpl extends XmlComplexContentImpl implements AnnotationDefinitionBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATIONCLASSNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation-class-name");
   private static final QName MEMBERSHIPCONSTRAINT$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "membership-constraint");
   private static final QName ALLOWEDONDECLARATION$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "allowed-on-declaration");
   private static final QName MEMBERDEFINITION$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-definition");

   public AnnotationDefinitionBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getAnnotationClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAnnotationClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         return target;
      }
   }

   public void setAnnotationClassName(String annotationClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ANNOTATIONCLASSNAME$0);
         }

         target.setStringValue(annotationClassName);
      }
   }

   public void xsetAnnotationClassName(XmlString annotationClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ANNOTATIONCLASSNAME$0);
         }

         target.set(annotationClassName);
      }
   }

   public MembershipConstraintBean getMembershipConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MembershipConstraintBean target = null;
         target = (MembershipConstraintBean)this.get_store().find_element_user(MEMBERSHIPCONSTRAINT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMembershipConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEMBERSHIPCONSTRAINT$2) != 0;
      }
   }

   public void setMembershipConstraint(MembershipConstraintBean membershipConstraint) {
      this.generatedSetterHelperImpl(membershipConstraint, MEMBERSHIPCONSTRAINT$2, 0, (short)1);
   }

   public MembershipConstraintBean addNewMembershipConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MembershipConstraintBean target = null;
         target = (MembershipConstraintBean)this.get_store().add_element_user(MEMBERSHIPCONSTRAINT$2);
         return target;
      }
   }

   public void unsetMembershipConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEMBERSHIPCONSTRAINT$2, 0);
      }
   }

   public boolean getAllowedOnDeclaration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWEDONDECLARATION$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAllowedOnDeclaration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWEDONDECLARATION$4, 0);
         return target;
      }
   }

   public void setAllowedOnDeclaration(boolean allowedOnDeclaration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWEDONDECLARATION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLOWEDONDECLARATION$4);
         }

         target.setBooleanValue(allowedOnDeclaration);
      }
   }

   public void xsetAllowedOnDeclaration(XmlBoolean allowedOnDeclaration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWEDONDECLARATION$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ALLOWEDONDECLARATION$4);
         }

         target.set(allowedOnDeclaration);
      }
   }

   public MemberDefinitionBean[] getMemberDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MEMBERDEFINITION$6, targetList);
         MemberDefinitionBean[] result = new MemberDefinitionBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MemberDefinitionBean getMemberDefinitionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberDefinitionBean target = null;
         target = (MemberDefinitionBean)this.get_store().find_element_user(MEMBERDEFINITION$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMemberDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEMBERDEFINITION$6);
      }
   }

   public void setMemberDefinitionArray(MemberDefinitionBean[] memberDefinitionArray) {
      this.check_orphaned();
      this.arraySetterHelper(memberDefinitionArray, MEMBERDEFINITION$6);
   }

   public void setMemberDefinitionArray(int i, MemberDefinitionBean memberDefinition) {
      this.generatedSetterHelperImpl(memberDefinition, MEMBERDEFINITION$6, i, (short)2);
   }

   public MemberDefinitionBean insertNewMemberDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberDefinitionBean target = null;
         target = (MemberDefinitionBean)this.get_store().insert_element_user(MEMBERDEFINITION$6, i);
         return target;
      }
   }

   public MemberDefinitionBean addNewMemberDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberDefinitionBean target = null;
         target = (MemberDefinitionBean)this.get_store().add_element_user(MEMBERDEFINITION$6);
         return target;
      }
   }

   public void removeMemberDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEMBERDEFINITION$6, i);
      }
   }
}
