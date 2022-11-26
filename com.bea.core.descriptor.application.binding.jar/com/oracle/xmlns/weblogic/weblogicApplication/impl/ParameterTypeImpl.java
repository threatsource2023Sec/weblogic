package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ParameterType;
import javax.xml.namespace.QName;

public class ParameterTypeImpl extends XmlComplexContentImpl implements ParameterType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "description");
   private static final QName PARAMNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "param-name");
   private static final QName PARAMVALUE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "param-value");

   public ParameterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.setStringValue(description);
      }
   }

   public void xsetDescription(XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.set(description);
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public String getParamName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetParamName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMNAME$2, 0);
         return target;
      }
   }

   public void setParamName(String paramName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PARAMNAME$2);
         }

         target.setStringValue(paramName);
      }
   }

   public void xsetParamName(XmlString paramName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARAMNAME$2);
         }

         target.set(paramName);
      }
   }

   public String getParamValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMVALUE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetParamValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMVALUE$4, 0);
         return target;
      }
   }

   public void setParamValue(String paramValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMVALUE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PARAMVALUE$4);
         }

         target.setStringValue(paramValue);
      }
   }

   public void xsetParamValue(XmlString paramValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMVALUE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARAMVALUE$4);
         }

         target.set(paramValue);
      }
   }
}
