package com.oracle.xmlns.weblogic.weblogicConnector;

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

public interface PropertyDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PropertyDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("property27a3doctype");

   ConfigPropertyType getProperty();

   void setProperty(ConfigPropertyType var1);

   ConfigPropertyType addNewProperty();

   public static final class Factory {
      public static PropertyDocument newInstance() {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().newInstance(PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument newInstance(XmlOptions options) {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().newInstance(PropertyDocument.type, options);
      }

      public static PropertyDocument parse(String xmlAsString) throws XmlException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyDocument.type, options);
      }

      public static PropertyDocument parse(File file) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(file, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(file, PropertyDocument.type, options);
      }

      public static PropertyDocument parse(URL u) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(u, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(u, PropertyDocument.type, options);
      }

      public static PropertyDocument parse(InputStream is) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(is, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(is, PropertyDocument.type, options);
      }

      public static PropertyDocument parse(Reader r) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(r, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(r, PropertyDocument.type, options);
      }

      public static PropertyDocument parse(XMLStreamReader sr) throws XmlException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(sr, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(sr, PropertyDocument.type, options);
      }

      public static PropertyDocument parse(Node node) throws XmlException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(node, PropertyDocument.type, (XmlOptions)null);
      }

      public static PropertyDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(node, PropertyDocument.type, options);
      }

      /** @deprecated */
      public static PropertyDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(xis, PropertyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PropertyDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PropertyDocument)XmlBeans.getContextTypeLoader().parse(xis, PropertyDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyDocument.type, options);
      }

      private Factory() {
      }
   }
}
