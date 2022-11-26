package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ConnectionFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("connectionfactorytypefa76type");

   String getFactoryName();

   XmlString xgetFactoryName();

   boolean isSetFactoryName();

   void setFactoryName(String var1);

   void xsetFactoryName(XmlString var1);

   void unsetFactoryName();

   ConnectionPropertiesType getConnectionProperties();

   boolean isSetConnectionProperties();

   void setConnectionProperties(ConnectionPropertiesType var1);

   ConnectionPropertiesType addNewConnectionProperties();

   void unsetConnectionProperties();

   public static final class Factory {
      public static ConnectionFactoryType newInstance() {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType newInstance(XmlOptions options) {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(String xmlAsString) throws XmlException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(File file) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(URL u) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(Reader r) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryType.type, options);
      }

      public static ConnectionFactoryType parse(Node node) throws XmlException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ConnectionFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static ConnectionFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
