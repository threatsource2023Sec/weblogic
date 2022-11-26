package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicWebApp.ContextCaseType;
import com.oracle.xmlns.weblogic.weblogicWebApp.FairShareRequestClassType;
import com.oracle.xmlns.weblogic.weblogicWebApp.GroupNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ResponseTimeRequestClassType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import javax.xml.namespace.QName;

public class ContextCaseTypeImpl extends XmlComplexContentImpl implements ContextCaseType {
   private static final long serialVersionUID = 1L;
   private static final QName USERNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "user-name");
   private static final QName GROUPNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "group-name");
   private static final QName RESPONSETIMEREQUESTCLASS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "response-time-request-class");
   private static final QName FAIRSHAREREQUESTCLASS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "fair-share-request-class");
   private static final QName REQUESTCLASSNAME$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "request-class-name");
   private static final QName ID$10 = new QName("", "id");

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

   public ResponseTimeRequestClassType getResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().find_element_user(RESPONSETIMEREQUESTCLASS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPONSETIMEREQUESTCLASS$4) != 0;
      }
   }

   public void setResponseTimeRequestClass(ResponseTimeRequestClassType responseTimeRequestClass) {
      this.generatedSetterHelperImpl(responseTimeRequestClass, RESPONSETIMEREQUESTCLASS$4, 0, (short)1);
   }

   public ResponseTimeRequestClassType addNewResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().add_element_user(RESPONSETIMEREQUESTCLASS$4);
         return target;
      }
   }

   public void unsetResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPONSETIMEREQUESTCLASS$4, 0);
      }
   }

   public FairShareRequestClassType getFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().find_element_user(FAIRSHAREREQUESTCLASS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FAIRSHAREREQUESTCLASS$6) != 0;
      }
   }

   public void setFairShareRequestClass(FairShareRequestClassType fairShareRequestClass) {
      this.generatedSetterHelperImpl(fairShareRequestClass, FAIRSHAREREQUESTCLASS$6, 0, (short)1);
   }

   public FairShareRequestClassType addNewFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().add_element_user(FAIRSHAREREQUESTCLASS$6);
         return target;
      }
   }

   public void unsetFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAIRSHAREREQUESTCLASS$6, 0);
      }
   }

   public XsdStringType getRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(REQUESTCLASSNAME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUESTCLASSNAME$8) != 0;
      }
   }

   public void setRequestClassName(XsdStringType requestClassName) {
      this.generatedSetterHelperImpl(requestClassName, REQUESTCLASSNAME$8, 0, (short)1);
   }

   public XsdStringType addNewRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(REQUESTCLASSNAME$8);
         return target;
      }
   }

   public void unsetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUESTCLASSNAME$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
