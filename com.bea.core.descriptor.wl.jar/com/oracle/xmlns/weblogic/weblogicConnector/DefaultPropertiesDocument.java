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

public interface DefaultPropertiesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultPropertiesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultproperties05cddoctype");

   ConfigPropertiesType getDefaultProperties();

   void setDefaultProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewDefaultProperties();

   public static final class Factory {
      public static DefaultPropertiesDocument newInstance() {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument newInstance(XmlOptions options) {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(String xmlAsString) throws XmlException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(File file) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(URL u) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(InputStream is) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(Reader r) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(XMLStreamReader sr) throws XmlException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, DefaultPropertiesDocument.type, options);
      }

      public static DefaultPropertiesDocument parse(Node node) throws XmlException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      public static DefaultPropertiesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, DefaultPropertiesDocument.type, options);
      }

      /** @deprecated */
      public static DefaultPropertiesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultPropertiesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, DefaultPropertiesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultPropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultPropertiesDocument.type, options);
      }

      private Factory() {
      }
   }
}
