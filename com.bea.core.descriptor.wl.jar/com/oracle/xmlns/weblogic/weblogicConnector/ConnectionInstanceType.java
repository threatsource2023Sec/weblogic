package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConnectionInstanceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionInstanceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectioninstancetypea84atype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   ConnectionDefinitionPropertiesType getConnectionProperties();

   boolean isSetConnectionProperties();

   void setConnectionProperties(ConnectionDefinitionPropertiesType var1);

   ConnectionDefinitionPropertiesType addNewConnectionProperties();

   void unsetConnectionProperties();

   public static final class Factory {
      public static ConnectionInstanceType newInstance() {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().newInstance(ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType newInstance(XmlOptions options) {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().newInstance(ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(String xmlAsString) throws XmlException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(File file) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(file, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(file, ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(URL u) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(u, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(u, ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(is, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(is, ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(Reader r) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(r, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(r, ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionInstanceType.type, options);
      }

      public static ConnectionInstanceType parse(Node node) throws XmlException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(node, ConnectionInstanceType.type, (XmlOptions)null);
      }

      public static ConnectionInstanceType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(node, ConnectionInstanceType.type, options);
      }

      /** @deprecated */
      public static ConnectionInstanceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionInstanceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionInstanceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionInstanceType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionInstanceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionInstanceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionInstanceType.type, options);
      }

      private Factory() {
      }
   }
}
