package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.JavaWsdlMappingDocument;
import com.sun.java.xml.ns.j2Ee.JavaWsdlMappingType;
import javax.xml.namespace.QName;

public class JavaWsdlMappingDocumentImpl extends XmlComplexContentImpl implements JavaWsdlMappingDocument {
   private static final long serialVersionUID = 1L;
   private static final QName JAVAWSDLMAPPING$0 = new QName("http://java.sun.com/xml/ns/j2ee", "java-wsdl-mapping");

   public JavaWsdlMappingDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public JavaWsdlMappingType getJavaWsdlMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaWsdlMappingType target = null;
         target = (JavaWsdlMappingType)this.get_store().find_element_user(JAVAWSDLMAPPING$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJavaWsdlMapping(JavaWsdlMappingType javaWsdlMapping) {
      this.generatedSetterHelperImpl(javaWsdlMapping, JAVAWSDLMAPPING$0, 0, (short)1);
   }

   public JavaWsdlMappingType addNewJavaWsdlMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaWsdlMappingType target = null;
         target = (JavaWsdlMappingType)this.get_store().add_element_user(JAVAWSDLMAPPING$0);
         return target;
      }
   }
}
