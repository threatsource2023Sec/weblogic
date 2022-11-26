package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface TcpTransportType extends PersistenceServerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TcpTransportType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tcptransporttypedacftype");

   int getSoTimeout();

   XmlInt xgetSoTimeout();

   boolean isSetSoTimeout();

   void setSoTimeout(int var1);

   void xsetSoTimeout(XmlInt var1);

   void unsetSoTimeout();

   String getHost();

   XmlString xgetHost();

   boolean isNilHost();

   boolean isSetHost();

   void setHost(String var1);

   void xsetHost(XmlString var1);

   void setNilHost();

   void unsetHost();

   int getPort();

   XmlInt xgetPort();

   boolean isSetPort();

   void setPort(int var1);

   void xsetPort(XmlInt var1);

   void unsetPort();

   public static final class Factory {
      public static TcpTransportType newInstance() {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().newInstance(TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType newInstance(XmlOptions options) {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().newInstance(TcpTransportType.type, options);
      }

      public static TcpTransportType parse(String xmlAsString) throws XmlException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TcpTransportType.type, options);
      }

      public static TcpTransportType parse(File file) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(file, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(file, TcpTransportType.type, options);
      }

      public static TcpTransportType parse(URL u) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(u, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(u, TcpTransportType.type, options);
      }

      public static TcpTransportType parse(InputStream is) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(is, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(is, TcpTransportType.type, options);
      }

      public static TcpTransportType parse(Reader r) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(r, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(r, TcpTransportType.type, options);
      }

      public static TcpTransportType parse(XMLStreamReader sr) throws XmlException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(sr, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(sr, TcpTransportType.type, options);
      }

      public static TcpTransportType parse(Node node) throws XmlException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(node, TcpTransportType.type, (XmlOptions)null);
      }

      public static TcpTransportType parse(Node node, XmlOptions options) throws XmlException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(node, TcpTransportType.type, options);
      }

      /** @deprecated */
      public static TcpTransportType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(xis, TcpTransportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TcpTransportType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TcpTransportType)XmlBeans.getContextTypeLoader().parse(xis, TcpTransportType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TcpTransportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TcpTransportType.type, options);
      }

      private Factory() {
      }
   }
}
