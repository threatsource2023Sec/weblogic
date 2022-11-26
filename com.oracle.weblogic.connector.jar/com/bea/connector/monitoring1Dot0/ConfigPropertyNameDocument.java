package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConfigPropertyNameDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigPropertyNameDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("configpropertyname6e76doctype");

   String getConfigPropertyName();

   XmlString xgetConfigPropertyName();

   void setConfigPropertyName(String var1);

   void xsetConfigPropertyName(XmlString var1);

   public static final class Factory {
      public static ConfigPropertyNameDocument newInstance() {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument newInstance(XmlOptions options) {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(String xmlAsString) throws XmlException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(File file) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(URL u) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(InputStream is) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(Reader r) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyNameDocument.type, options);
      }

      public static ConfigPropertyNameDocument parse(Node node) throws XmlException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      public static ConfigPropertyNameDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyNameDocument.type, options);
      }

      /** @deprecated */
      public static ConfigPropertyNameDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigPropertyNameDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigPropertyNameDocument)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyNameDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyNameDocument.type, options);
      }

      private Factory() {
      }
   }
}
