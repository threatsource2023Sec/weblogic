package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface DefaultUpdateManagerType extends UpdateManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultUpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultupdatemanagertype1ca5type");

   public static final class Factory {
      public static DefaultUpdateManagerType newInstance() {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType newInstance(XmlOptions options) {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(String xmlAsString) throws XmlException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(File file) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(URL u) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultUpdateManagerType.type, options);
      }

      public static DefaultUpdateManagerType parse(Node node) throws XmlException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      public static DefaultUpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static DefaultUpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultUpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultUpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
