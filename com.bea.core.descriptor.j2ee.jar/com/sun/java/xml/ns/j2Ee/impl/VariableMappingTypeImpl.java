package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.VariableMappingType;
import javax.xml.namespace.QName;

public class VariableMappingTypeImpl extends XmlComplexContentImpl implements VariableMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName JAVAVARIABLENAME$0 = new QName("http://java.sun.com/xml/ns/j2ee", "java-variable-name");
   private static final QName DATAMEMBER$2 = new QName("http://java.sun.com/xml/ns/j2ee", "data-member");
   private static final QName XMLATTRIBUTENAME$4 = new QName("http://java.sun.com/xml/ns/j2ee", "xml-attribute-name");
   private static final QName XMLELEMENTNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "xml-element-name");
   private static final QName XMLWILDCARD$8 = new QName("http://java.sun.com/xml/ns/j2ee", "xml-wildcard");
   private static final QName ID$10 = new QName("", "id");

   public VariableMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getJavaVariableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JAVAVARIABLENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJavaVariableName(String javaVariableName) {
      this.generatedSetterHelperImpl(javaVariableName, JAVAVARIABLENAME$0, 0, (short)1);
   }

   public String addNewJavaVariableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JAVAVARIABLENAME$0);
         return target;
      }
   }

   public EmptyType getDataMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(DATAMEMBER$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDataMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATAMEMBER$2) != 0;
      }
   }

   public void setDataMember(EmptyType dataMember) {
      this.generatedSetterHelperImpl(dataMember, DATAMEMBER$2, 0, (short)1);
   }

   public EmptyType addNewDataMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(DATAMEMBER$2);
         return target;
      }
   }

   public void unsetDataMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATAMEMBER$2, 0);
      }
   }

   public String getXmlAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(XMLATTRIBUTENAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetXmlAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XMLATTRIBUTENAME$4) != 0;
      }
   }

   public void setXmlAttributeName(String xmlAttributeName) {
      this.generatedSetterHelperImpl(xmlAttributeName, XMLATTRIBUTENAME$4, 0, (short)1);
   }

   public String addNewXmlAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(XMLATTRIBUTENAME$4);
         return target;
      }
   }

   public void unsetXmlAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XMLATTRIBUTENAME$4, 0);
      }
   }

   public String getXmlElementName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(XMLELEMENTNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetXmlElementName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XMLELEMENTNAME$6) != 0;
      }
   }

   public void setXmlElementName(String xmlElementName) {
      this.generatedSetterHelperImpl(xmlElementName, XMLELEMENTNAME$6, 0, (short)1);
   }

   public String addNewXmlElementName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(XMLELEMENTNAME$6);
         return target;
      }
   }

   public void unsetXmlElementName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XMLELEMENTNAME$6, 0);
      }
   }

   public EmptyType getXmlWildcard() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(XMLWILDCARD$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetXmlWildcard() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XMLWILDCARD$8) != 0;
      }
   }

   public void setXmlWildcard(EmptyType xmlWildcard) {
      this.generatedSetterHelperImpl(xmlWildcard, XMLWILDCARD$8, 0, (short)1);
   }

   public EmptyType addNewXmlWildcard() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(XMLWILDCARD$8);
         return target;
      }
   }

   public void unsetXmlWildcard() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XMLWILDCARD$8, 0);
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
