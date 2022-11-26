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

public interface DefaultConnectionPropertiesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultConnectionPropertiesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultconnectionproperties17f2doctype");

   ConnectionDefinitionPropertiesType getDefaultConnectionProperties();

   void setDefaultConnectionProperties(ConnectionDefinitionPropertiesType var1);

   ConnectionDefinitionPropertiesType addNewDefaultConnectionProperties();

   public static final class Factory {
      public static DefaultConnectionPropertiesDocument newInstance() {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument newInstance(XmlOptions options) {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(String xmlAsString) throws XmlException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(File file) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(URL u) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(InputStream is) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(Reader r) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(XMLStreamReader sr) throws XmlException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, DefaultConnectionPropertiesDocument.type, options);
      }

      public static DefaultConnectionPropertiesDocument parse(Node node) throws XmlException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultConnectionPropertiesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, DefaultConnectionPropertiesDocument.type, options);
      }

      /** @deprecated */
      public static DefaultConnectionPropertiesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultConnectionPropertiesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, DefaultConnectionPropertiesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultConnectionPropertiesDocument.type, options);
      }

      private Factory() {
      }
   }
}
