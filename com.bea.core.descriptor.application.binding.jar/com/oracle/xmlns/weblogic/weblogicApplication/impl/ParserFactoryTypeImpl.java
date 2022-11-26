package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ParserFactoryType;
import javax.xml.namespace.QName;

public class ParserFactoryTypeImpl extends XmlComplexContentImpl implements ParserFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName SAXPARSERFACTORY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "saxparser-factory");
   private static final QName DOCUMENTBUILDERFACTORY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "document-builder-factory");
   private static final QName TRANSFORMERFACTORY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "transformer-factory");
   private static final QName XPATHFACTORY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xpath-factory");
   private static final QName SCHEMAFACTORY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "schema-factory");
   private static final QName XMLINPUTFACTORY$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xml-input-factory");
   private static final QName XMLOUTPUTFACTORY$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xml-output-factory");
   private static final QName XMLEVENTFACTORY$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xml-event-factory");

   public ParserFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getSaxparserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAXPARSERFACTORY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSaxparserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAXPARSERFACTORY$0, 0);
         return target;
      }
   }

   public boolean isSetSaxparserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAXPARSERFACTORY$0) != 0;
      }
   }

   public void setSaxparserFactory(String saxparserFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAXPARSERFACTORY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAXPARSERFACTORY$0);
         }

         target.setStringValue(saxparserFactory);
      }
   }

   public void xsetSaxparserFactory(XmlString saxparserFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAXPARSERFACTORY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAXPARSERFACTORY$0);
         }

         target.set(saxparserFactory);
      }
   }

   public void unsetSaxparserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAXPARSERFACTORY$0, 0);
      }
   }

   public String getDocumentBuilderFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DOCUMENTBUILDERFACTORY$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDocumentBuilderFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DOCUMENTBUILDERFACTORY$2, 0);
         return target;
      }
   }

   public boolean isSetDocumentBuilderFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DOCUMENTBUILDERFACTORY$2) != 0;
      }
   }

   public void setDocumentBuilderFactory(String documentBuilderFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DOCUMENTBUILDERFACTORY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DOCUMENTBUILDERFACTORY$2);
         }

         target.setStringValue(documentBuilderFactory);
      }
   }

   public void xsetDocumentBuilderFactory(XmlString documentBuilderFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DOCUMENTBUILDERFACTORY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DOCUMENTBUILDERFACTORY$2);
         }

         target.set(documentBuilderFactory);
      }
   }

   public void unsetDocumentBuilderFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DOCUMENTBUILDERFACTORY$2, 0);
      }
   }

   public String getTransformerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSFORMERFACTORY$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTransformerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRANSFORMERFACTORY$4, 0);
         return target;
      }
   }

   public boolean isSetTransformerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSFORMERFACTORY$4) != 0;
      }
   }

   public void setTransformerFactory(String transformerFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSFORMERFACTORY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSFORMERFACTORY$4);
         }

         target.setStringValue(transformerFactory);
      }
   }

   public void xsetTransformerFactory(XmlString transformerFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRANSFORMERFACTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRANSFORMERFACTORY$4);
         }

         target.set(transformerFactory);
      }
   }

   public void unsetTransformerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSFORMERFACTORY$4, 0);
      }
   }

   public String getXpathFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XPATHFACTORY$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetXpathFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XPATHFACTORY$6, 0);
         return target;
      }
   }

   public boolean isSetXpathFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XPATHFACTORY$6) != 0;
      }
   }

   public void setXpathFactory(String xpathFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XPATHFACTORY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XPATHFACTORY$6);
         }

         target.setStringValue(xpathFactory);
      }
   }

   public void xsetXpathFactory(XmlString xpathFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XPATHFACTORY$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(XPATHFACTORY$6);
         }

         target.set(xpathFactory);
      }
   }

   public void unsetXpathFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XPATHFACTORY$6, 0);
      }
   }

   public String getSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMAFACTORY$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMAFACTORY$8, 0);
         return target;
      }
   }

   public boolean isSetSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMAFACTORY$8) != 0;
      }
   }

   public void setSchemaFactory(String schemaFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMAFACTORY$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SCHEMAFACTORY$8);
         }

         target.setStringValue(schemaFactory);
      }
   }

   public void xsetSchemaFactory(XmlString schemaFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMAFACTORY$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMAFACTORY$8);
         }

         target.set(schemaFactory);
      }
   }

   public void unsetSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMAFACTORY$8, 0);
      }
   }

   public String getXmlInputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLINPUTFACTORY$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetXmlInputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XMLINPUTFACTORY$10, 0);
         return target;
      }
   }

   public boolean isSetXmlInputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XMLINPUTFACTORY$10) != 0;
      }
   }

   public void setXmlInputFactory(String xmlInputFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLINPUTFACTORY$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XMLINPUTFACTORY$10);
         }

         target.setStringValue(xmlInputFactory);
      }
   }

   public void xsetXmlInputFactory(XmlString xmlInputFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XMLINPUTFACTORY$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(XMLINPUTFACTORY$10);
         }

         target.set(xmlInputFactory);
      }
   }

   public void unsetXmlInputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XMLINPUTFACTORY$10, 0);
      }
   }

   public String getXmlOutputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLOUTPUTFACTORY$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetXmlOutputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XMLOUTPUTFACTORY$12, 0);
         return target;
      }
   }

   public boolean isSetXmlOutputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XMLOUTPUTFACTORY$12) != 0;
      }
   }

   public void setXmlOutputFactory(String xmlOutputFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLOUTPUTFACTORY$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XMLOUTPUTFACTORY$12);
         }

         target.setStringValue(xmlOutputFactory);
      }
   }

   public void xsetXmlOutputFactory(XmlString xmlOutputFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XMLOUTPUTFACTORY$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(XMLOUTPUTFACTORY$12);
         }

         target.set(xmlOutputFactory);
      }
   }

   public void unsetXmlOutputFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XMLOUTPUTFACTORY$12, 0);
      }
   }

   public String getXmlEventFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLEVENTFACTORY$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetXmlEventFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XMLEVENTFACTORY$14, 0);
         return target;
      }
   }

   public boolean isSetXmlEventFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XMLEVENTFACTORY$14) != 0;
      }
   }

   public void setXmlEventFactory(String xmlEventFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLEVENTFACTORY$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XMLEVENTFACTORY$14);
         }

         target.setStringValue(xmlEventFactory);
      }
   }

   public void xsetXmlEventFactory(XmlString xmlEventFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(XMLEVENTFACTORY$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(XMLEVENTFACTORY$14);
         }

         target.set(xmlEventFactory);
      }
   }

   public void unsetXmlEventFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XMLEVENTFACTORY$14, 0);
      }
   }
}
