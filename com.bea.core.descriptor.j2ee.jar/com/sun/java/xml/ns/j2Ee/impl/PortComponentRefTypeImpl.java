package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.PortComponentRefType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class PortComponentRefTypeImpl extends XmlComplexContentImpl implements PortComponentRefType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICEENDPOINTINTERFACE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "service-endpoint-interface");
   private static final QName PORTCOMPONENTLINK$2 = new QName("http://java.sun.com/xml/ns/j2ee", "port-component-link");
   private static final QName ID$4 = new QName("", "id");

   public PortComponentRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEENDPOINTINTERFACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceEndpointInterface(FullyQualifiedClassType serviceEndpointInterface) {
      this.generatedSetterHelperImpl(serviceEndpointInterface, SERVICEENDPOINTINTERFACE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEENDPOINTINTERFACE$0);
         return target;
      }
   }

   public String getPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTCOMPONENTLINK$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTCOMPONENTLINK$2) != 0;
      }
   }

   public void setPortComponentLink(String portComponentLink) {
      this.generatedSetterHelperImpl(portComponentLink, PORTCOMPONENTLINK$2, 0, (short)1);
   }

   public String addNewPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTCOMPONENTLINK$2);
         return target;
      }
   }

   public void unsetPortComponentLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTCOMPONENTLINK$2, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
