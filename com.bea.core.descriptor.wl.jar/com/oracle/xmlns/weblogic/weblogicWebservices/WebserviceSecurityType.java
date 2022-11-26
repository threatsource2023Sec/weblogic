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

public interface WebserviceSecurityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebserviceSecurityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("webservicesecuritytype7677type");

   String getMbeanName();

   void setMbeanName(String var1);

   String addNewMbeanName();

   public static final class Factory {
      public static WebserviceSecurityType newInstance() {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().newInstance(WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType newInstance(XmlOptions options) {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().newInstance(WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(File file) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(file, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(file, WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(URL u) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(u, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(u, WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(InputStream is) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(is, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(is, WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(Reader r) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(r, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(r, WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(XMLStreamReader sr) throws XmlException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceSecurityType.type, options);
      }

      public static WebserviceSecurityType parse(Node node) throws XmlException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(node, WebserviceSecurityType.type, (XmlOptions)null);
      }

      public static WebserviceSecurityType parse(Node node, XmlOptions options) throws XmlException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(node, WebserviceSecurityType.type, options);
      }

      /** @deprecated */
      public static WebserviceSecurityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceSecurityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebserviceSecurityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebserviceSecurityType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceSecurityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceSecurityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceSecurityType.type, options);
      }

      private Factory() {
      }
   }
}
