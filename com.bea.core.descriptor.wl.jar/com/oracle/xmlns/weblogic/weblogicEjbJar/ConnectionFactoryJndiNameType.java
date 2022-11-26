package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ConnectionFactoryJndiNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionFactoryJndiNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectionfactoryjndinametyped9d3type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConnectionFactoryJndiNameType newInstance() {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType newInstance(XmlOptions options) {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(String xmlAsString) throws XmlException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(File file) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(URL u) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(Reader r) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryJndiNameType.type, options);
      }

      public static ConnectionFactoryJndiNameType parse(Node node) throws XmlException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryJndiNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryJndiNameType.type, options);
      }

      /** @deprecated */
      public static ConnectionFactoryJndiNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionFactoryJndiNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionFactoryJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryJndiNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryJndiNameType.type, options);
      }

      private Factory() {
      }
   }
}
