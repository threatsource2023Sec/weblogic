package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import com.sun.java.xml.ns.j2Ee.ParameterModeType;
import com.sun.java.xml.ns.j2Ee.WsdlMessageMappingType;
import com.sun.java.xml.ns.j2Ee.WsdlMessagePartNameType;
import com.sun.java.xml.ns.j2Ee.WsdlMessageType;
import javax.xml.namespace.QName;

public class WsdlMessageMappingTypeImpl extends XmlComplexContentImpl implements WsdlMessageMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName WSDLMESSAGE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message");
   private static final QName WSDLMESSAGEPARTNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message-part-name");
   private static final QName PARAMETERMODE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "parameter-mode");
   private static final QName SOAPHEADER$6 = new QName("http://java.sun.com/xml/ns/j2ee", "soap-header");
   private static final QName ID$8 = new QName("", "id");

   public WsdlMessageMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public WsdlMessageType getWsdlMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessageType target = null;
         target = (WsdlMessageType)this.get_store().find_element_user(WSDLMESSAGE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlMessage(WsdlMessageType wsdlMessage) {
      this.generatedSetterHelperImpl(wsdlMessage, WSDLMESSAGE$0, 0, (short)1);
   }

   public WsdlMessageType addNewWsdlMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessageType target = null;
         target = (WsdlMessageType)this.get_store().add_element_user(WSDLMESSAGE$0);
         return target;
      }
   }

   public WsdlMessagePartNameType getWsdlMessagePartName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessagePartNameType target = null;
         target = (WsdlMessagePartNameType)this.get_store().find_element_user(WSDLMESSAGEPARTNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlMessagePartName(WsdlMessagePartNameType wsdlMessagePartName) {
      this.generatedSetterHelperImpl(wsdlMessagePartName, WSDLMESSAGEPARTNAME$2, 0, (short)1);
   }

   public WsdlMessagePartNameType addNewWsdlMessagePartName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessagePartNameType target = null;
         target = (WsdlMessagePartNameType)this.get_store().add_element_user(WSDLMESSAGEPARTNAME$2);
         return target;
      }
   }

   public ParameterModeType getParameterMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParameterModeType target = null;
         target = (ParameterModeType)this.get_store().find_element_user(PARAMETERMODE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setParameterMode(ParameterModeType parameterMode) {
      this.generatedSetterHelperImpl(parameterMode, PARAMETERMODE$4, 0, (short)1);
   }

   public ParameterModeType addNewParameterMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParameterModeType target = null;
         target = (ParameterModeType)this.get_store().add_element_user(PARAMETERMODE$4);
         return target;
      }
   }

   public EmptyType getSoapHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(SOAPHEADER$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSoapHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOAPHEADER$6) != 0;
      }
   }

   public void setSoapHeader(EmptyType soapHeader) {
      this.generatedSetterHelperImpl(soapHeader, SOAPHEADER$6, 0, (short)1);
   }

   public EmptyType addNewSoapHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(SOAPHEADER$6);
         return target;
      }
   }

   public void unsetSoapHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOAPHEADER$6, 0);
      }
   }

   public String getId() {
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

   public void setId(String id) {
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
