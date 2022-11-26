package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.ServiceEndpointInterfaceMappingType;
import com.sun.java.xml.ns.j2Ee.ServiceEndpointMethodMappingType;
import com.sun.java.xml.ns.j2Ee.XsdQNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServiceEndpointInterfaceMappingTypeImpl extends XmlComplexContentImpl implements ServiceEndpointInterfaceMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICEENDPOINTINTERFACE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "service-endpoint-interface");
   private static final QName WSDLPORTTYPE$2 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-port-type");
   private static final QName WSDLBINDING$4 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-binding");
   private static final QName SERVICEENDPOINTMETHODMAPPING$6 = new QName("http://java.sun.com/xml/ns/j2ee", "service-endpoint-method-mapping");
   private static final QName ID$8 = new QName("", "id");

   public ServiceEndpointInterfaceMappingTypeImpl(SchemaType sType) {
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

   public XsdQNameType getWsdlPortType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(WSDLPORTTYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlPortType(XsdQNameType wsdlPortType) {
      this.generatedSetterHelperImpl(wsdlPortType, WSDLPORTTYPE$2, 0, (short)1);
   }

   public XsdQNameType addNewWsdlPortType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(WSDLPORTTYPE$2);
         return target;
      }
   }

   public XsdQNameType getWsdlBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(WSDLBINDING$4, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlBinding(XsdQNameType wsdlBinding) {
      this.generatedSetterHelperImpl(wsdlBinding, WSDLBINDING$4, 0, (short)1);
   }

   public XsdQNameType addNewWsdlBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(WSDLBINDING$4);
         return target;
      }
   }

   public ServiceEndpointMethodMappingType[] getServiceEndpointMethodMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEENDPOINTMETHODMAPPING$6, targetList);
         ServiceEndpointMethodMappingType[] result = new ServiceEndpointMethodMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceEndpointMethodMappingType getServiceEndpointMethodMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceEndpointMethodMappingType target = null;
         target = (ServiceEndpointMethodMappingType)this.get_store().find_element_user(SERVICEENDPOINTMETHODMAPPING$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceEndpointMethodMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEENDPOINTMETHODMAPPING$6);
      }
   }

   public void setServiceEndpointMethodMappingArray(ServiceEndpointMethodMappingType[] serviceEndpointMethodMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceEndpointMethodMappingArray, SERVICEENDPOINTMETHODMAPPING$6);
   }

   public void setServiceEndpointMethodMappingArray(int i, ServiceEndpointMethodMappingType serviceEndpointMethodMapping) {
      this.generatedSetterHelperImpl(serviceEndpointMethodMapping, SERVICEENDPOINTMETHODMAPPING$6, i, (short)2);
   }

   public ServiceEndpointMethodMappingType insertNewServiceEndpointMethodMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceEndpointMethodMappingType target = null;
         target = (ServiceEndpointMethodMappingType)this.get_store().insert_element_user(SERVICEENDPOINTMETHODMAPPING$6, i);
         return target;
      }
   }

   public ServiceEndpointMethodMappingType addNewServiceEndpointMethodMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceEndpointMethodMappingType target = null;
         target = (ServiceEndpointMethodMappingType)this.get_store().add_element_user(SERVICEENDPOINTMETHODMAPPING$6);
         return target;
      }
   }

   public void removeServiceEndpointMethodMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEENDPOINTMETHODMAPPING$6, i);
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
