package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ConstructorParameterOrderType;
import com.sun.java.xml.ns.j2Ee.ExceptionMappingType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.WsdlMessagePartNameType;
import com.sun.java.xml.ns.j2Ee.WsdlMessageType;
import javax.xml.namespace.QName;

public class ExceptionMappingTypeImpl extends XmlComplexContentImpl implements ExceptionMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName EXCEPTIONTYPE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "exception-type");
   private static final QName WSDLMESSAGE$2 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message");
   private static final QName WSDLMESSAGEPARTNAME$4 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message-part-name");
   private static final QName CONSTRUCTORPARAMETERORDER$6 = new QName("http://java.sun.com/xml/ns/j2ee", "constructor-parameter-order");
   private static final QName ID$8 = new QName("", "id");

   public ExceptionMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getExceptionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(EXCEPTIONTYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setExceptionType(FullyQualifiedClassType exceptionType) {
      this.generatedSetterHelperImpl(exceptionType, EXCEPTIONTYPE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewExceptionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(EXCEPTIONTYPE$0);
         return target;
      }
   }

   public WsdlMessageType getWsdlMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessageType target = null;
         target = (WsdlMessageType)this.get_store().find_element_user(WSDLMESSAGE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlMessage(WsdlMessageType wsdlMessage) {
      this.generatedSetterHelperImpl(wsdlMessage, WSDLMESSAGE$2, 0, (short)1);
   }

   public WsdlMessageType addNewWsdlMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessageType target = null;
         target = (WsdlMessageType)this.get_store().add_element_user(WSDLMESSAGE$2);
         return target;
      }
   }

   public WsdlMessagePartNameType getWsdlMessagePartName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessagePartNameType target = null;
         target = (WsdlMessagePartNameType)this.get_store().find_element_user(WSDLMESSAGEPARTNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlMessagePartName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLMESSAGEPARTNAME$4) != 0;
      }
   }

   public void setWsdlMessagePartName(WsdlMessagePartNameType wsdlMessagePartName) {
      this.generatedSetterHelperImpl(wsdlMessagePartName, WSDLMESSAGEPARTNAME$4, 0, (short)1);
   }

   public WsdlMessagePartNameType addNewWsdlMessagePartName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlMessagePartNameType target = null;
         target = (WsdlMessagePartNameType)this.get_store().add_element_user(WSDLMESSAGEPARTNAME$4);
         return target;
      }
   }

   public void unsetWsdlMessagePartName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLMESSAGEPARTNAME$4, 0);
      }
   }

   public ConstructorParameterOrderType getConstructorParameterOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConstructorParameterOrderType target = null;
         target = (ConstructorParameterOrderType)this.get_store().find_element_user(CONSTRUCTORPARAMETERORDER$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConstructorParameterOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSTRUCTORPARAMETERORDER$6) != 0;
      }
   }

   public void setConstructorParameterOrder(ConstructorParameterOrderType constructorParameterOrder) {
      this.generatedSetterHelperImpl(constructorParameterOrder, CONSTRUCTORPARAMETERORDER$6, 0, (short)1);
   }

   public ConstructorParameterOrderType addNewConstructorParameterOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConstructorParameterOrderType target = null;
         target = (ConstructorParameterOrderType)this.get_store().add_element_user(CONSTRUCTORPARAMETERORDER$6);
         return target;
      }
   }

   public void unsetConstructorParameterOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSTRUCTORPARAMETERORDER$6, 0);
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
