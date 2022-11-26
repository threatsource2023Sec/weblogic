package com.bea.ns.weblogic.x90.impl;

import com.bea.ns.weblogic.x90.ContextCaseType;
import com.bea.ns.weblogic.x90.GroupNameType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import javax.xml.namespace.QName;

public class ContextCaseTypeImpl extends XmlComplexContentImpl implements ContextCaseType {
   private static final long serialVersionUID = 1L;
   private static final QName USERNAME$0 = new QName("http://www.bea.com/ns/weblogic/90", "user-name");
   private static final QName GROUPNAME$2 = new QName("http://www.bea.com/ns/weblogic/90", "group-name");
   private static final QName REQUESTCLASSNAME$4 = new QName("http://www.bea.com/ns/weblogic/90", "request-class-name");
   private static final QName ID$6 = new QName("", "id");

   public ContextCaseTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(USERNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USERNAME$0) != 0;
      }
   }

   public void setUserName(XsdStringType userName) {
      this.generatedSetterHelperImpl(userName, USERNAME$0, 0, (short)1);
   }

   public XsdStringType addNewUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(USERNAME$0);
         return target;
      }
   }

   public void unsetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USERNAME$0, 0);
      }
   }

   public GroupNameType getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().find_element_user(GROUPNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPNAME$2) != 0;
      }
   }

   public void setGroupName(GroupNameType groupName) {
      this.generatedSetterHelperImpl(groupName, GROUPNAME$2, 0, (short)1);
   }

   public GroupNameType addNewGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().add_element_user(GROUPNAME$2);
         return target;
      }
   }

   public void unsetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPNAME$2, 0);
      }
   }

   public XsdStringType getRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(REQUESTCLASSNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public void setRequestClassName(XsdStringType requestClassName) {
      this.generatedSetterHelperImpl(requestClassName, REQUESTCLASSNAME$4, 0, (short)1);
   }

   public XsdStringType addNewRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(REQUESTCLASSNAME$4);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
