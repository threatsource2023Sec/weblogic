package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.PortMappingType;
import com.sun.java.xml.ns.j2Ee.ServiceInterfaceMappingType;
import com.sun.java.xml.ns.j2Ee.XsdQNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServiceInterfaceMappingTypeImpl extends XmlComplexContentImpl implements ServiceInterfaceMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICEINTERFACE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "service-interface");
   private static final QName WSDLSERVICENAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-service-name");
   private static final QName PORTMAPPING$4 = new QName("http://java.sun.com/xml/ns/j2ee", "port-mapping");
   private static final QName ID$6 = new QName("", "id");

   public ServiceInterfaceMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getServiceInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEINTERFACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceInterface(FullyQualifiedClassType serviceInterface) {
      this.generatedSetterHelperImpl(serviceInterface, SERVICEINTERFACE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEINTERFACE$0);
         return target;
      }
   }

   public XsdQNameType getWsdlServiceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(WSDLSERVICENAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlServiceName(XsdQNameType wsdlServiceName) {
      this.generatedSetterHelperImpl(wsdlServiceName, WSDLSERVICENAME$2, 0, (short)1);
   }

   public XsdQNameType addNewWsdlServiceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(WSDLSERVICENAME$2);
         return target;
      }
   }

   public PortMappingType[] getPortMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTMAPPING$4, targetList);
         PortMappingType[] result = new PortMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortMappingType getPortMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortMappingType target = null;
         target = (PortMappingType)this.get_store().find_element_user(PORTMAPPING$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTMAPPING$4);
      }
   }

   public void setPortMappingArray(PortMappingType[] portMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(portMappingArray, PORTMAPPING$4);
   }

   public void setPortMappingArray(int i, PortMappingType portMapping) {
      this.generatedSetterHelperImpl(portMapping, PORTMAPPING$4, i, (short)2);
   }

   public PortMappingType insertNewPortMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortMappingType target = null;
         target = (PortMappingType)this.get_store().insert_element_user(PORTMAPPING$4, i);
         return target;
      }
   }

   public PortMappingType addNewPortMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortMappingType target = null;
         target = (PortMappingType)this.get_store().add_element_user(PORTMAPPING$4);
         return target;
      }
   }

   public void removePortMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTMAPPING$4, i);
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
