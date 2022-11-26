package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.WsdlMessagePartNameType;
import com.sun.java.xml.ns.j2Ee.WsdlMessageType;
import com.sun.java.xml.ns.j2Ee.WsdlReturnValueMappingType;
import javax.xml.namespace.QName;

public class WsdlReturnValueMappingTypeImpl extends XmlComplexContentImpl implements WsdlReturnValueMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName METHODRETURNVALUE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "method-return-value");
   private static final QName WSDLMESSAGE$2 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message");
   private static final QName WSDLMESSAGEPARTNAME$4 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-message-part-name");
   private static final QName ID$6 = new QName("", "id");

   public WsdlReturnValueMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getMethodReturnValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(METHODRETURNVALUE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMethodReturnValue(FullyQualifiedClassType methodReturnValue) {
      this.generatedSetterHelperImpl(methodReturnValue, METHODRETURNVALUE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewMethodReturnValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(METHODRETURNVALUE$0);
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
