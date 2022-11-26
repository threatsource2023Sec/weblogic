package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ConnectionDefinitionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionDefinitionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("connectiondefinitiontype861dtype");

   FullyQualifiedClassType getManagedconnectionfactoryClass();

   void setManagedconnectionfactoryClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewManagedconnectionfactoryClass();

   ConfigPropertyType[] getConfigPropertyArray();

   ConfigPropertyType getConfigPropertyArray(int var1);

   int sizeOfConfigPropertyArray();

   void setConfigPropertyArray(ConfigPropertyType[] var1);

   void setConfigPropertyArray(int var1, ConfigPropertyType var2);

   ConfigPropertyType insertNewConfigProperty(int var1);

   ConfigPropertyType addNewConfigProperty();

   void removeConfigProperty(int var1);

   FullyQualifiedClassType getConnectionfactoryInterface();

   void setConnectionfactoryInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewConnectionfactoryInterface();

   FullyQualifiedClassType getConnectionfactoryImplClass();

   void setConnectionfactoryImplClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewConnectionfactoryImplClass();

   FullyQualifiedClassType getConnectionInterface();

   void setConnectionInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewConnectionInterface();

   FullyQualifiedClassType getConnectionImplClass();

   void setConnectionImplClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewConnectionImplClass();

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
