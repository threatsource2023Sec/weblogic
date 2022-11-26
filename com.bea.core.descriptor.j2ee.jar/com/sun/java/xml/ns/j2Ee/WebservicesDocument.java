package com.sun.java.xml.ns.j2Ee;

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

public interface WebservicesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebservicesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webservicesd829doctype");

   WebservicesType getWebservices();

   void setWebservices(WebservicesType var1);

   WebservicesType addNewWebservices();

   public static final class Factory {
      public static WebservicesDocument newInstance() {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().newInstance(WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument newInstance(XmlOptions options) {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().newInstance(WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(File file) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(file, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(file, WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(URL u) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(u, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(u, WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(InputStream is) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(is, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(is, WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(Reader r) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(r, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(r, WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(XMLStreamReader sr) throws XmlException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(sr, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(sr, WebservicesDocument.type, options);
      }

      public static WebservicesDocument parse(Node node) throws XmlException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(node, WebservicesDocument.type, (XmlOptions)null);
      }

      public static WebservicesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(node, WebservicesDocument.type, options);
      }

      /** @deprecated */
      public static WebservicesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(xis, WebservicesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebservicesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebservicesDocument)XmlBeans.getContextTypeLoader().parse(xis, WebservicesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicesDocument.type, options);
      }

      private Factory() {
      }
   }
}
