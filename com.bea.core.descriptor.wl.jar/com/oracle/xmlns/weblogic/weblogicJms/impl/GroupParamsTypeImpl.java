package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.GroupParamsType;
import javax.xml.namespace.QName;

public class GroupParamsTypeImpl extends XmlComplexContentImpl implements GroupParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName ERRORDESTINATION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "error-destination");
   private static final QName SUBDEPLOYMENTNAME$2 = new QName("", "sub-deployment-name");

   public GroupParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         return target;
      }
   }

   public boolean isNilErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ERRORDESTINATION$0) != 0;
      }
   }

   public void setErrorDestination(String errorDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ERRORDESTINATION$0);
         }

         target.setStringValue(errorDestination);
      }
   }

   public void xsetErrorDestination(XmlString errorDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ERRORDESTINATION$0);
         }

         target.set(errorDestination);
      }
   }

   public void setNilErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ERRORDESTINATION$0);
         }

         target.setNil();
      }
   }

   public void unsetErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ERRORDESTINATION$0, 0);
      }
   }

   public String getSubDeploymentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SUBDEPLOYMENTNAME$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSubDeploymentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(SUBDEPLOYMENTNAME$2);
         return target;
      }
   }

   public void setSubDeploymentName(String subDeploymentName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SUBDEPLOYMENTNAME$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(SUBDEPLOYMENTNAME$2);
         }

         target.setStringValue(subDeploymentName);
      }
   }

   public void xsetSubDeploymentName(XmlString subDeploymentName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(SUBDEPLOYMENTNAME$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(SUBDEPLOYMENTNAME$2);
         }

         target.set(subDeploymentName);
      }
   }
}
