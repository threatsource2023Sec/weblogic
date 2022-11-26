package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.JavaTypeType;
import com.sun.java.xml.ns.j2Ee.MethodParamPartsMappingType;
import com.sun.java.xml.ns.j2Ee.WsdlMessageMappingType;
import com.sun.java.xml.ns.j2Ee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class MethodParamPartsMappingTypeImpl extends XmlComplexContentImpl implements MethodParamPartsMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName PARAMPOSITION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "param-position");
   private static final QName PARAMTYPE$2 = new QName("http://java.sun.com/xml/ns/j2ee", "param-type");
   private static final QName WSDLMESSAGEMAPPING$4 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message-mapping");
   private static final QName ID$6 = new QName("", "id");

   public MethodParamPartsMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getParamPosition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(PARAMPOSITION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setParamPosition(XsdNonNegativeIntegerType paramPosition) {
      this.generatedSetterHelperImpl(paramPosition, PARAMPOSITION$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewParamPosition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(PARAMPOSITION$0);
         return target;
      }
   }

   public JavaTypeType getParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().find_element_user(PARAMTYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setParamType(JavaTypeType paramType) {
      this.generatedSetterHelperImpl(paramType, PARAMTYPE$2, 0, (short)1);
   }

   public JavaTypeType addNewParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().add_element_user(PARAMTYPE$2);
         return target;
      }
   }

   public WsdlMessageMappingType getWsdlMessageMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessageMappingType target = null;
         target = (WsdlMessageMappingType)this.get_store().find_element_user(WSDLMESSAGEMAPPING$4, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlMessageMapping(WsdlMessageMappingType wsdlMessageMapping) {
      this.generatedSetterHelperImpl(wsdlMessageMapping, WSDLMESSAGEMAPPING$4, 0, (short)1);
   }

   public WsdlMessageMappingType addNewWsdlMessageMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessageMappingType target = null;
         target = (WsdlMessageMappingType)this.get_store().add_element_user(WSDLMESSAGEMAPPING$4);
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
