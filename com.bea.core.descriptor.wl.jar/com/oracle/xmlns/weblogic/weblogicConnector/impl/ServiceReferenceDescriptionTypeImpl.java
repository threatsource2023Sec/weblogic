package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.PortInfoType;
import com.oracle.xmlns.weblogic.weblogicConnector.PropertyNamevalueType;
import com.oracle.xmlns.weblogic.weblogicConnector.ServiceReferenceDescriptionType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServiceReferenceDescriptionTypeImpl extends XmlComplexContentImpl implements ServiceReferenceDescriptionType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICEREFNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "service-ref-name");
   private static final QName WSDLURL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "wsdl-url");
   private static final QName CALLPROPERTY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "call-property");
   private static final QName PORTINFO$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "port-info");
   private static final QName ID$8 = new QName("", "id");

   public ServiceReferenceDescriptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JndiNameType getServiceRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(SERVICEREFNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceRefName(JndiNameType serviceRefName) {
      this.generatedSetterHelperImpl(serviceRefName, SERVICEREFNAME$0, 0, (short)1);
   }

   public JndiNameType addNewServiceRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(SERVICEREFNAME$0);
         return target;
      }
   }

   public String getWsdlUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WSDLURL$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLURL$2) != 0;
      }
   }

   public void setWsdlUrl(String wsdlUrl) {
      this.generatedSetterHelperImpl(wsdlUrl, WSDLURL$2, 0, (short)1);
   }

   public String addNewWsdlUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WSDLURL$2);
         return target;
      }
   }

   public void unsetWsdlUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLURL$2, 0);
      }
   }

   public PropertyNamevalueType[] getCallPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CALLPROPERTY$4, targetList);
         PropertyNamevalueType[] result = new PropertyNamevalueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyNamevalueType getCallPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().find_element_user(CALLPROPERTY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCallPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLPROPERTY$4);
      }
   }

   public void setCallPropertyArray(PropertyNamevalueType[] callPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(callPropertyArray, CALLPROPERTY$4);
   }

   public void setCallPropertyArray(int i, PropertyNamevalueType callProperty) {
      this.generatedSetterHelperImpl(callProperty, CALLPROPERTY$4, i, (short)2);
   }

   public PropertyNamevalueType insertNewCallProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().insert_element_user(CALLPROPERTY$4, i);
         return target;
      }
   }

   public PropertyNamevalueType addNewCallProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().add_element_user(CALLPROPERTY$4);
         return target;
      }
   }

   public void removeCallProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLPROPERTY$4, i);
      }
   }

   public PortInfoType[] getPortInfoArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTINFO$6, targetList);
         PortInfoType[] result = new PortInfoType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortInfoType getPortInfoArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortInfoType target = null;
         target = (PortInfoType)this.get_store().find_element_user(PORTINFO$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortInfoArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTINFO$6);
      }
   }

   public void setPortInfoArray(PortInfoType[] portInfoArray) {
      this.check_orphaned();
      this.arraySetterHelper(portInfoArray, PORTINFO$6);
   }

   public void setPortInfoArray(int i, PortInfoType portInfo) {
      this.generatedSetterHelperImpl(portInfo, PORTINFO$6, i, (short)2);
   }

   public PortInfoType insertNewPortInfo(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortInfoType target = null;
         target = (PortInfoType)this.get_store().insert_element_user(PORTINFO$6, i);
         return target;
      }
   }

   public PortInfoType addNewPortInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortInfoType target = null;
         target = (PortInfoType)this.get_store().add_element_user(PORTINFO$6);
         return target;
      }
   }

   public void removePortInfo(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTINFO$6, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
