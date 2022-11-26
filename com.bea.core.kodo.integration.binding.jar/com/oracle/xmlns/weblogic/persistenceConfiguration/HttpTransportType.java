package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface HttpTransportType extends PersistenceServerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HttpTransportType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("httptransporttyped956type");

   String getUrl();

   XmlString xgetUrl();

   boolean isNilUrl();

   boolean isSetUrl();

   void setUrl(String var1);

   void xsetUrl(XmlString var1);

   void setNilUrl();

   void unsetUrl();

   public static final class Factory {
      public static HttpTransportType newInstance() {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().newInstance(HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType newInstance(XmlOptions options) {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().newInstance(HttpTransportType.type, options);
      }

      public static HttpTransportType parse(String xmlAsString) throws XmlException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HttpTransportType.type, options);
      }

      public static HttpTransportType parse(File file) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(file, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(file, HttpTransportType.type, options);
      }

      public static HttpTransportType parse(URL u) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(u, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(u, HttpTransportType.type, options);
      }

      public static HttpTransportType parse(InputStream is) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(is, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(is, HttpTransportType.type, options);
      }

      public static HttpTransportType parse(Reader r) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(r, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(r, HttpTransportType.type, options);
      }

      public static HttpTransportType parse(XMLStreamReader sr) throws XmlException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(sr, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(sr, HttpTransportType.type, options);
      }

      public static HttpTransportType parse(Node node) throws XmlException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(node, HttpTransportType.type, (XmlOptions)null);
      }

      public static HttpTransportType parse(Node node, XmlOptions options) throws XmlException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(node, HttpTransportType.type, options);
      }

      /** @deprecated */
      public static HttpTransportType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(xis, HttpTransportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HttpTransportType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HttpTransportType)XmlBeans.getContextTypeLoader().parse(xis, HttpTransportType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HttpTransportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HttpTransportType.type, options);
      }

      private Factory() {
      }
   }
}
