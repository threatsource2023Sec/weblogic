package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ContextCaseType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ContextCaseTypeImpl extends XmlComplexContentImpl implements ContextCaseType {
   private static final long serialVersionUID = 1L;
   private static final QName USERNAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "user-name");
   private static final QName GROUPNAME$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "group-name");
   private static final QName REQUESTCLASSNAME$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "request-class-name");

   public ContextCaseTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         return target;
      }
   }

   public boolean isSetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USERNAME$0) != 0;
      }
   }

   public void setUserName(String userName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USERNAME$0);
         }

         target.setStringValue(userName);
      }
   }

   public void xsetUserName(XmlString userName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(USERNAME$0);
         }

         target.set(userName);
      }
   }

   public void unsetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USERNAME$0, 0);
      }
   }

   public String getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GROUPNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GROUPNAME$2, 0);
         return target;
      }
   }

   public boolean isSetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPNAME$2) != 0;
      }
   }

   public void setGroupName(String groupName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GROUPNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(GROUPNAME$2);
         }

         target.setStringValue(groupName);
      }
   }

   public void xsetGroupName(XmlString groupName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GROUPNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(GROUPNAME$2);
         }

         target.set(groupName);
      }
   }

   public void unsetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPNAME$2, 0);
      }
   }

   public String getRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUESTCLASSNAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REQUESTCLASSNAME$4, 0);
         return target;
      }
   }

   public void setRequestClassName(String requestClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUESTCLASSNAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUESTCLASSNAME$4);
         }

         target.setStringValue(requestClassName);
      }
   }

   public void xsetRequestClassName(XmlString requestClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REQUESTCLASSNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REQUESTCLASSNAME$4);
         }

         target.set(requestClassName);
      }
   }
}
