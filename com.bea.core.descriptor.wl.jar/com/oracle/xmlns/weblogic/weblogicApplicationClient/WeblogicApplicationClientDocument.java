package com.oracle.xmlns.weblogic.weblogicApplicationClient;

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

public interface WeblogicApplicationClientDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicApplicationClientDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicapplicationclientd32edoctype");

   WeblogicApplicationClientType getWeblogicApplicationClient();

   void setWeblogicApplicationClient(WeblogicApplicationClientType var1);

   WeblogicApplicationClientType addNewWeblogicApplicationClient();

   public static final class Factory {
      public static WeblogicApplicationClientDocument newInstance() {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument newInstance(XmlOptions options) {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(File file) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationClientDocument.type, options);
      }

      public static WeblogicApplicationClientDocument parse(Node node) throws XmlException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationClientDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicApplicationClientDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicApplicationClientDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationClientDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationClientDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationClientDocument.type, options);
      }

      private Factory() {
      }
   }
}
