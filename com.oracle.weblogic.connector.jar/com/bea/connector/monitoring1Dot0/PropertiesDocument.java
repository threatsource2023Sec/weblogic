package com.bea.connector.monitoring1Dot0;

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

public interface PropertiesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PropertiesDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("propertiesc61bdoctype");

   ConfigPropertiesType getProperties();

   void setProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewProperties();

   public static final class Factory {
      public static PropertiesDocument newInstance() {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument newInstance(XmlOptions options) {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(String xmlAsString) throws XmlException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(File file) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(URL u) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(InputStream is) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(Reader r) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(XMLStreamReader sr) throws XmlException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, PropertiesDocument.type, options);
      }

      public static PropertiesDocument parse(Node node) throws XmlException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, PropertiesDocument.type, (XmlOptions)null);
      }

      public static PropertiesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, PropertiesDocument.type, options);
      }

      /** @deprecated */
      public static PropertiesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, PropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PropertiesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, PropertiesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertiesDocument.type, options);
      }

      private Factory() {
      }
   }
}
