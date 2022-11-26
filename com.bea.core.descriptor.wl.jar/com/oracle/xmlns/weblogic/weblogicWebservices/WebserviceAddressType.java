package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WebserviceAddressType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebserviceAddressType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("webserviceaddresstype1885type");

   String getWebserviceContextpath();

   void setWebserviceContextpath(String var1);

   String addNewWebserviceContextpath();

   String getWebserviceServiceuri();

   void setWebserviceServiceuri(String var1);

   String addNewWebserviceServiceuri();

   public static final class Factory {
      public static WebserviceAddressType newInstance() {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().newInstance(WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType newInstance(XmlOptions options) {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().newInstance(WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(File file) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(file, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(file, WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(URL u) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(u, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(u, WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(InputStream is) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(is, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(is, WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(Reader r) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(r, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(r, WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(XMLStreamReader sr) throws XmlException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceAddressType.type, options);
      }

      public static WebserviceAddressType parse(Node node) throws XmlException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(node, WebserviceAddressType.type, (XmlOptions)null);
      }

      public static WebserviceAddressType parse(Node node, XmlOptions options) throws XmlException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(node, WebserviceAddressType.type, options);
      }

      /** @deprecated */
      public static WebserviceAddressType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebserviceAddressType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebserviceAddressType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceAddressType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceAddressType.type, options);
      }

      private Factory() {
      }
   }
}
