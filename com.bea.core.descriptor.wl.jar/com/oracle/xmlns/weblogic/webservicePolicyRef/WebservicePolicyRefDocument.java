package com.oracle.xmlns.weblogic.webservicePolicyRef;

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

public interface WebservicePolicyRefDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebservicePolicyRefDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("webservicepolicyref880edoctype");

   WebservicePolicyRefType getWebservicePolicyRef();

   void setWebservicePolicyRef(WebservicePolicyRefType var1);

   WebservicePolicyRefType addNewWebservicePolicyRef();

   public static final class Factory {
      public static WebservicePolicyRefDocument newInstance() {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().newInstance(WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument newInstance(XmlOptions options) {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().newInstance(WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(String xmlAsString) throws XmlException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(File file) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(file, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(file, WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(URL u) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(u, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(u, WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(InputStream is) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(is, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(is, WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(Reader r) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(r, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(r, WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(XMLStreamReader sr) throws XmlException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(sr, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(sr, WebservicePolicyRefDocument.type, options);
      }

      public static WebservicePolicyRefDocument parse(Node node) throws XmlException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(node, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(node, WebservicePolicyRefDocument.type, options);
      }

      /** @deprecated */
      public static WebservicePolicyRefDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(xis, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebservicePolicyRefDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebservicePolicyRefDocument)XmlBeans.getContextTypeLoader().parse(xis, WebservicePolicyRefDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicePolicyRefDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicePolicyRefDocument.type, options);
      }

      private Factory() {
      }
   }
}
