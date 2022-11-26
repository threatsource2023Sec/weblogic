package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XmlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("xmltype13c0type");

   ParserFactoryType getParserFactory();

   boolean isSetParserFactory();

   void setParserFactory(ParserFactoryType var1);

   ParserFactoryType addNewParserFactory();

   void unsetParserFactory();

   EntityMappingType[] getEntityMappingArray();

   EntityMappingType getEntityMappingArray(int var1);

   int sizeOfEntityMappingArray();

   void setEntityMappingArray(EntityMappingType[] var1);

   void setEntityMappingArray(int var1, EntityMappingType var2);

   EntityMappingType insertNewEntityMapping(int var1);

   EntityMappingType addNewEntityMapping();

   void removeEntityMapping(int var1);

   public static final class Factory {
      public static XmlType newInstance() {
         return (XmlType)XmlBeans.getContextTypeLoader().newInstance(XmlType.type, (XmlOptions)null);
      }

      public static XmlType newInstance(XmlOptions options) {
         return (XmlType)XmlBeans.getContextTypeLoader().newInstance(XmlType.type, options);
      }

      public static XmlType parse(String xmlAsString) throws XmlException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XmlType.type, options);
      }

      public static XmlType parse(File file) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(file, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(file, XmlType.type, options);
      }

      public static XmlType parse(URL u) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(u, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(u, XmlType.type, options);
      }

      public static XmlType parse(InputStream is) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(is, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(is, XmlType.type, options);
      }

      public static XmlType parse(Reader r) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(r, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(r, XmlType.type, options);
      }

      public static XmlType parse(XMLStreamReader sr) throws XmlException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(sr, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(sr, XmlType.type, options);
      }

      public static XmlType parse(Node node) throws XmlException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(node, XmlType.type, (XmlOptions)null);
      }

      public static XmlType parse(Node node, XmlOptions options) throws XmlException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(node, XmlType.type, options);
      }

      /** @deprecated */
      public static XmlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(xis, XmlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlType)XmlBeans.getContextTypeLoader().parse(xis, XmlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlType.type, options);
      }

      private Factory() {
      }
   }
}
