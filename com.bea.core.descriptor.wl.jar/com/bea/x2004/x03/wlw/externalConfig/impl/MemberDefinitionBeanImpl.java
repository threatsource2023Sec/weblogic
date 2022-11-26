package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.EnumRefBean;
import com.bea.x2004.x03.wlw.externalConfig.MemberDefinitionBean;
import com.bea.x2004.x03.wlw.externalConfig.SimpleTypeDefinitionBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class MemberDefinitionBeanImpl extends XmlComplexContentImpl implements MemberDefinitionBean {
   private static final long serialVersionUID = 1L;
   private static final QName MEMBERNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-name");
   private static final QName ISARRAY1$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "is-array");
   private static final QName ISREQUIRED$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "is-required");
   private static final QName ANNOTATIONREF$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation-ref");
   private static final QName ENUMREF$8 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "enum-ref");
   private static final QName SIMPLETYPEDEFINITION$10 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "simple-type-definition");

   public MemberDefinitionBeanImpl(SchemaType sType) {
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

   public boolean getIsArray1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ISARRAY1$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIsArray1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ISARRAY1$2, 0);
         return target;
      }
   }

   public void setIsArray1(boolean isArray1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ISARRAY1$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ISARRAY1$2);
         }

         target.setBooleanValue(isArray1);
      }
   }

   public void xsetIsArray1(XmlBoolean isArray1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ISARRAY1$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ISARRAY1$2);
         }

         target.set(isArray1);
      }
   }

   public boolean getIsRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ISREQUIRED$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIsRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ISREQUIRED$4, 0);
         return target;
      }
   }

   public void setIsRequired(boolean isRequired) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ISREQUIRED$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ISREQUIRED$4);
         }

         target.setBooleanValue(isRequired);
      }
   }

   public void xsetIsRequired(XmlBoolean isRequired) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ISREQUIRED$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ISREQUIRED$4);
         }

         target.set(isRequired);
      }
   }

   public String getAnnotationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATIONREF$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAnnotationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATIONREF$6, 0);
         return target;
      }
   }

   public boolean isSetAnnotationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANNOTATIONREF$6) != 0;
      }
   }

   public void setAnnotationRef(String annotationRef) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATIONREF$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ANNOTATIONREF$6);
         }

         target.setStringValue(annotationRef);
      }
   }

   public void xsetAnnotationRef(XmlString annotationRef) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATIONREF$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ANNOTATIONREF$6);
         }

         target.set(annotationRef);
      }
   }

   public void unsetAnnotationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANNOTATIONREF$6, 0);
      }
   }

   public EnumRefBean getEnumRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnumRefBean target = null;
         target = (EnumRefBean)this.get_store().find_element_user(ENUMREF$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnumRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENUMREF$8) != 0;
      }
   }

   public void setEnumRef(EnumRefBean enumRef) {
      this.generatedSetterHelperImpl(enumRef, ENUMREF$8, 0, (short)1);
   }

   public EnumRefBean addNewEnumRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnumRefBean target = null;
         target = (EnumRefBean)this.get_store().add_element_user(ENUMREF$8);
         return target;
      }
   }

   public void unsetEnumRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENUMREF$8, 0);
      }
   }

   public SimpleTypeDefinitionBean getSimpleTypeDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleTypeDefinitionBean target = null;
         target = (SimpleTypeDefinitionBean)this.get_store().find_element_user(SIMPLETYPEDEFINITION$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSimpleTypeDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMPLETYPEDEFINITION$10) != 0;
      }
   }

   public void setSimpleTypeDefinition(SimpleTypeDefinitionBean simpleTypeDefinition) {
      this.generatedSetterHelperImpl(simpleTypeDefinition, SIMPLETYPEDEFINITION$10, 0, (short)1);
   }

   public SimpleTypeDefinitionBean addNewSimpleTypeDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleTypeDefinitionBean target = null;
         target = (SimpleTypeDefinitionBean)this.get_store().add_element_user(SIMPLETYPEDEFINITION$10);
         return target;
      }
   }

   public void unsetSimpleTypeDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SIMPLETYPEDEFINITION$10, 0);
      }
   }
}
