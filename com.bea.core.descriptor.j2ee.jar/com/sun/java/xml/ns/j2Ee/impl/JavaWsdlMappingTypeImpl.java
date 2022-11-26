package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DeweyVersionType;
import com.sun.java.xml.ns.j2Ee.ExceptionMappingType;
import com.sun.java.xml.ns.j2Ee.JavaWsdlMappingType;
import com.sun.java.xml.ns.j2Ee.JavaXmlTypeMappingType;
import com.sun.java.xml.ns.j2Ee.PackageMappingType;
import com.sun.java.xml.ns.j2Ee.ServiceEndpointInterfaceMappingType;
import com.sun.java.xml.ns.j2Ee.ServiceInterfaceMappingType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JavaWsdlMappingTypeImpl extends XmlComplexContentImpl implements JavaWsdlMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName PACKAGEMAPPING$0 = new QName("http://java.sun.com/xml/ns/j2ee", "package-mapping");
   private static final QName JAVAXMLTYPEMAPPING$2 = new QName("http://java.sun.com/xml/ns/j2ee", "java-xml-type-mapping");
   private static final QName EXCEPTIONMAPPING$4 = new QName("http://java.sun.com/xml/ns/j2ee", "exception-mapping");
   private static final QName SERVICEINTERFACEMAPPING$6 = new QName("http://java.sun.com/xml/ns/j2ee", "service-interface-mapping");
   private static final QName SERVICEENDPOINTINTERFACEMAPPING$8 = new QName("http://java.sun.com/xml/ns/j2ee", "service-endpoint-interface-mapping");
   private static final QName VERSION$10 = new QName("", "version");
   private static final QName ID$12 = new QName("", "id");

   public JavaWsdlMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PackageMappingType[] getPackageMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PACKAGEMAPPING$0, targetList);
         PackageMappingType[] result = new PackageMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PackageMappingType getPackageMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PackageMappingType target = null;
         target = (PackageMappingType)this.get_store().find_element_user(PACKAGEMAPPING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPackageMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PACKAGEMAPPING$0);
      }
   }

   public void setPackageMappingArray(PackageMappingType[] packageMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(packageMappingArray, PACKAGEMAPPING$0);
   }

   public void setPackageMappingArray(int i, PackageMappingType packageMapping) {
      this.generatedSetterHelperImpl(packageMapping, PACKAGEMAPPING$0, i, (short)2);
   }

   public PackageMappingType insertNewPackageMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PackageMappingType target = null;
         target = (PackageMappingType)this.get_store().insert_element_user(PACKAGEMAPPING$0, i);
         return target;
      }
   }

   public PackageMappingType addNewPackageMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PackageMappingType target = null;
         target = (PackageMappingType)this.get_store().add_element_user(PACKAGEMAPPING$0);
         return target;
      }
   }

   public void removePackageMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PACKAGEMAPPING$0, i);
      }
   }

   public JavaXmlTypeMappingType[] getJavaXmlTypeMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JAVAXMLTYPEMAPPING$2, targetList);
         JavaXmlTypeMappingType[] result = new JavaXmlTypeMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JavaXmlTypeMappingType getJavaXmlTypeMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaXmlTypeMappingType target = null;
         target = (JavaXmlTypeMappingType)this.get_store().find_element_user(JAVAXMLTYPEMAPPING$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJavaXmlTypeMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JAVAXMLTYPEMAPPING$2);
      }
   }

   public void setJavaXmlTypeMappingArray(JavaXmlTypeMappingType[] javaXmlTypeMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(javaXmlTypeMappingArray, JAVAXMLTYPEMAPPING$2);
   }

   public void setJavaXmlTypeMappingArray(int i, JavaXmlTypeMappingType javaXmlTypeMapping) {
      this.generatedSetterHelperImpl(javaXmlTypeMapping, JAVAXMLTYPEMAPPING$2, i, (short)2);
   }

   public JavaXmlTypeMappingType insertNewJavaXmlTypeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaXmlTypeMappingType target = null;
         target = (JavaXmlTypeMappingType)this.get_store().insert_element_user(JAVAXMLTYPEMAPPING$2, i);
         return target;
      }
   }

   public JavaXmlTypeMappingType addNewJavaXmlTypeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaXmlTypeMappingType target = null;
         target = (JavaXmlTypeMappingType)this.get_store().add_element_user(JAVAXMLTYPEMAPPING$2);
         return target;
      }
   }

   public void removeJavaXmlTypeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JAVAXMLTYPEMAPPING$2, i);
      }
   }

   public ExceptionMappingType[] getExceptionMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXCEPTIONMAPPING$4, targetList);
         ExceptionMappingType[] result = new ExceptionMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExceptionMappingType getExceptionMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionMappingType target = null;
         target = (ExceptionMappingType)this.get_store().find_element_user(EXCEPTIONMAPPING$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfExceptionMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCEPTIONMAPPING$4);
      }
   }

   public void setExceptionMappingArray(ExceptionMappingType[] exceptionMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(exceptionMappingArray, EXCEPTIONMAPPING$4);
   }

   public void setExceptionMappingArray(int i, ExceptionMappingType exceptionMapping) {
      this.generatedSetterHelperImpl(exceptionMapping, EXCEPTIONMAPPING$4, i, (short)2);
   }

   public ExceptionMappingType insertNewExceptionMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionMappingType target = null;
         target = (ExceptionMappingType)this.get_store().insert_element_user(EXCEPTIONMAPPING$4, i);
         return target;
      }
   }

   public ExceptionMappingType addNewExceptionMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionMappingType target = null;
         target = (ExceptionMappingType)this.get_store().add_element_user(EXCEPTIONMAPPING$4);
         return target;
      }
   }

   public void removeExceptionMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCEPTIONMAPPING$4, i);
      }
   }

   public ServiceInterfaceMappingType[] getServiceInterfaceMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEINTERFACEMAPPING$6, targetList);
         ServiceInterfaceMappingType[] result = new ServiceInterfaceMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceInterfaceMappingType getServiceInterfaceMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceInterfaceMappingType target = null;
         target = (ServiceInterfaceMappingType)this.get_store().find_element_user(SERVICEINTERFACEMAPPING$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceInterfaceMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEINTERFACEMAPPING$6);
      }
   }

   public void setServiceInterfaceMappingArray(ServiceInterfaceMappingType[] serviceInterfaceMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceInterfaceMappingArray, SERVICEINTERFACEMAPPING$6);
   }

   public void setServiceInterfaceMappingArray(int i, ServiceInterfaceMappingType serviceInterfaceMapping) {
      this.generatedSetterHelperImpl(serviceInterfaceMapping, SERVICEINTERFACEMAPPING$6, i, (short)2);
   }

   public ServiceInterfaceMappingType insertNewServiceInterfaceMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceInterfaceMappingType target = null;
         target = (ServiceInterfaceMappingType)this.get_store().insert_element_user(SERVICEINTERFACEMAPPING$6, i);
         return target;
      }
   }

   public ServiceInterfaceMappingType addNewServiceInterfaceMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceInterfaceMappingType target = null;
         target = (ServiceInterfaceMappingType)this.get_store().add_element_user(SERVICEINTERFACEMAPPING$6);
         return target;
      }
   }

   public void removeServiceInterfaceMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEINTERFACEMAPPING$6, i);
      }
   }

   public ServiceEndpointInterfaceMappingType[] getServiceEndpointInterfaceMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEENDPOINTINTERFACEMAPPING$8, targetList);
         ServiceEndpointInterfaceMappingType[] result = new ServiceEndpointInterfaceMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceEndpointInterfaceMappingType getServiceEndpointInterfaceMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceEndpointInterfaceMappingType target = null;
         target = (ServiceEndpointInterfaceMappingType)this.get_store().find_element_user(SERVICEENDPOINTINTERFACEMAPPING$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceEndpointInterfaceMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEENDPOINTINTERFACEMAPPING$8);
      }
   }

   public void setServiceEndpointInterfaceMappingArray(ServiceEndpointInterfaceMappingType[] serviceEndpointInterfaceMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceEndpointInterfaceMappingArray, SERVICEENDPOINTINTERFACEMAPPING$8);
   }

   public void setServiceEndpointInterfaceMappingArray(int i, ServiceEndpointInterfaceMappingType serviceEndpointInterfaceMapping) {
      this.generatedSetterHelperImpl(serviceEndpointInterfaceMapping, SERVICEENDPOINTINTERFACEMAPPING$8, i, (short)2);
   }

   public ServiceEndpointInterfaceMappingType insertNewServiceEndpointInterfaceMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceEndpointInterfaceMappingType target = null;
         target = (ServiceEndpointInterfaceMappingType)this.get_store().insert_element_user(SERVICEENDPOINTINTERFACEMAPPING$8, i);
         return target;
      }
   }

   public ServiceEndpointInterfaceMappingType addNewServiceEndpointInterfaceMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceEndpointInterfaceMappingType target = null;
         target = (ServiceEndpointInterfaceMappingType)this.get_store().add_element_user(SERVICEENDPOINTINTERFACEMAPPING$8);
         return target;
      }
   }

   public void removeServiceEndpointInterfaceMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEENDPOINTINTERFACEMAPPING$8, i);
      }
   }

   public BigDecimal getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$10);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$10);
         }

         return target == null ? null : target.getBigDecimalValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$10);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$10);
         }

         return target;
      }
   }

   public void setVersion(BigDecimal version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$10);
         }

         target.setBigDecimalValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$10);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$10);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
