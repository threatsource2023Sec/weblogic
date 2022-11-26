package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConnectorWorkManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorWorkManagerType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectorworkmanagertype3e35type");

   BigInteger getMaxConcurrentLongRunningRequests();

   XmlInteger xgetMaxConcurrentLongRunningRequests();

   boolean isSetMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(BigInteger var1);

   void xsetMaxConcurrentLongRunningRequests(XmlInteger var1);

   void unsetMaxConcurrentLongRunningRequests();

   public static final class Factory {
      public static ConnectorWorkManagerType newInstance() {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().newInstance(ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType newInstance(XmlOptions options) {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().newInstance(ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(String xmlAsString) throws XmlException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(File file) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(file, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(file, ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(URL u) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(u, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(u, ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(InputStream is) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(is, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(is, ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(Reader r) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(r, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(r, ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(sr, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(sr, ConnectorWorkManagerType.type, options);
      }

      public static ConnectorWorkManagerType parse(Node node) throws XmlException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(node, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(node, ConnectorWorkManagerType.type, options);
      }

      /** @deprecated */
      public static ConnectorWorkManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(xis, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorWorkManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorWorkManagerType)XmlBeans.getContextTypeLoader().parse(xis, ConnectorWorkManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorWorkManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorWorkManagerType.type, options);
      }

      private Factory() {
      }
   }
}
