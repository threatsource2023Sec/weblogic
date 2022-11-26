package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConnectionDefinitionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionDefinitionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectiondefinitiontype4d88type");

   String getConnectionFactoryInterface();

   void setConnectionFactoryInterface(String var1);

   String addNewConnectionFactoryInterface();

   ConnectionDefinitionPropertiesType getDefaultConnectionProperties();

   boolean isSetDefaultConnectionProperties();

   void setDefaultConnectionProperties(ConnectionDefinitionPropertiesType var1);

   ConnectionDefinitionPropertiesType addNewDefaultConnectionProperties();

   void unsetDefaultConnectionProperties();

   ConnectionInstanceType[] getConnectionInstanceArray();

   ConnectionInstanceType getConnectionInstanceArray(int var1);

   int sizeOfConnectionInstanceArray();

   void setConnectionInstanceArray(ConnectionInstanceType[] var1);

   void setConnectionInstanceArray(int var1, ConnectionInstanceType var2);

   ConnectionInstanceType insertNewConnectionInstance(int var1);

   ConnectionInstanceType addNewConnectionInstance();

   void removeConnectionInstance(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConnectionDefinitionType newInstance() {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType newInstance(XmlOptions options) {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(File file) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(URL u) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(Reader r) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDefinitionType.type, options);
      }

      public static ConnectionDefinitionType parse(Node node) throws XmlException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDefinitionType.type, options);
      }

      /** @deprecated */
      public static ConnectionDefinitionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionDefinitionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionDefinitionType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDefinitionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDefinitionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDefinitionType.type, options);
      }

      private Factory() {
      }
   }
}
