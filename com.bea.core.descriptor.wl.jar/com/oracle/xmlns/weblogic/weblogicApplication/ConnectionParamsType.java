package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ConnectionParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectionparamstype7d3etype");

   ParameterType[] getParameterArray();

   ParameterType getParameterArray(int var1);

   int sizeOfParameterArray();

   void setParameterArray(ParameterType[] var1);

   void setParameterArray(int var1, ParameterType var2);

   ParameterType insertNewParameter(int var1);

   ParameterType addNewParameter();

   void removeParameter(int var1);

   public static final class Factory {
      public static ConnectionParamsType newInstance() {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType newInstance(XmlOptions options) {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(String xmlAsString) throws XmlException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(File file) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(URL u) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(Reader r) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionParamsType.type, options);
      }

      public static ConnectionParamsType parse(Node node) throws XmlException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionParamsType.type, (XmlOptions)null);
      }

      public static ConnectionParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionParamsType.type, options);
      }

      /** @deprecated */
      public static ConnectionParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionParamsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionParamsType.type, options);
      }

      private Factory() {
      }
   }
}
