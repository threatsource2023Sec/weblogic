package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import com.sun.java.xml.ns.j2Ee.MethodParamPartsMappingType;
import com.sun.java.xml.ns.j2Ee.ServiceEndpointMethodMappingType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.WsdlReturnValueMappingType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServiceEndpointMethodMappingTypeImpl extends XmlComplexContentImpl implements ServiceEndpointMethodMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName JAVAMETHODNAME$0 = new QName("http://java.sun.com/xml/ns/j2ee", "java-method-name");
   private static final QName WSDLOPERATION$2 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-operation");
   private static final QName WRAPPEDELEMENT$4 = new QName("http://java.sun.com/xml/ns/j2ee", "wrapped-element");
   private static final QName METHODPARAMPARTSMAPPING$6 = new QName("http://java.sun.com/xml/ns/j2ee", "method-param-parts-mapping");
   private static final QName WSDLRETURNVALUEMAPPING$8 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-return-value-mapping");
   private static final QName ID$10 = new QName("", "id");

   public ServiceEndpointMethodMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getJavaMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JAVAMETHODNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJavaMethodName(String javaMethodName) {
      this.generatedSetterHelperImpl(javaMethodName, JAVAMETHODNAME$0, 0, (short)1);
   }

   public String addNewJavaMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JAVAMETHODNAME$0);
         return target;
      }
   }

   public String getWsdlOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WSDLOPERATION$2, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlOperation(String wsdlOperation) {
      this.generatedSetterHelperImpl(wsdlOperation, WSDLOPERATION$2, 0, (short)1);
   }

   public String addNewWsdlOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WSDLOPERATION$2);
         return target;
      }
   }

   public EmptyType getWrappedElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(WRAPPEDELEMENT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWrappedElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WRAPPEDELEMENT$4) != 0;
      }
   }

   public void setWrappedElement(EmptyType wrappedElement) {
      this.generatedSetterHelperImpl(wrappedElement, WRAPPEDELEMENT$4, 0, (short)1);
   }

   public EmptyType addNewWrappedElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(WRAPPEDELEMENT$4);
         return target;
      }
   }

   public void unsetWrappedElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WRAPPEDELEMENT$4, 0);
      }
   }

   public MethodParamPartsMappingType[] getMethodParamPartsMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHODPARAMPARTSMAPPING$6, targetList);
         MethodParamPartsMappingType[] result = new MethodParamPartsMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MethodParamPartsMappingType getMethodParamPartsMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamPartsMappingType target = null;
         target = (MethodParamPartsMappingType)this.get_store().find_element_user(METHODPARAMPARTSMAPPING$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMethodParamPartsMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHODPARAMPARTSMAPPING$6);
      }
   }

   public void setMethodParamPartsMappingArray(MethodParamPartsMappingType[] methodParamPartsMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodParamPartsMappingArray, METHODPARAMPARTSMAPPING$6);
   }

   public void setMethodParamPartsMappingArray(int i, MethodParamPartsMappingType methodParamPartsMapping) {
      this.generatedSetterHelperImpl(methodParamPartsMapping, METHODPARAMPARTSMAPPING$6, i, (short)2);
   }

   public MethodParamPartsMappingType insertNewMethodParamPartsMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamPartsMappingType target = null;
         target = (MethodParamPartsMappingType)this.get_store().insert_element_user(METHODPARAMPARTSMAPPING$6, i);
         return target;
      }
   }

   public MethodParamPartsMappingType addNewMethodParamPartsMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamPartsMappingType target = null;
         target = (MethodParamPartsMappingType)this.get_store().add_element_user(METHODPARAMPARTSMAPPING$6);
         return target;
      }
   }

   public void removeMethodParamPartsMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHODPARAMPARTSMAPPING$6, i);
      }
   }

   public WsdlReturnValueMappingType getWsdlReturnValueMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlReturnValueMappingType target = null;
         target = (WsdlReturnValueMappingType)this.get_store().find_element_user(WSDLRETURNVALUEMAPPING$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlReturnValueMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLRETURNVALUEMAPPING$8) != 0;
      }
   }

   public void setWsdlReturnValueMapping(WsdlReturnValueMappingType wsdlReturnValueMapping) {
      this.generatedSetterHelperImpl(wsdlReturnValueMapping, WSDLRETURNVALUEMAPPING$8, 0, (short)1);
   }

   public WsdlReturnValueMappingType addNewWsdlReturnValueMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlReturnValueMappingType target = null;
         target = (WsdlReturnValueMappingType)this.get_store().add_element_user(WSDLRETURNVALUEMAPPING$8);
         return target;
      }
   }

   public void unsetWsdlReturnValueMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLRETURNVALUEMAPPING$8, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
