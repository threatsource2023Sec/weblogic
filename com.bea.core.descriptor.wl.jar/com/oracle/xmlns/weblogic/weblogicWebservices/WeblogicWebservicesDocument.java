package com.oracle.xmlns.weblogic.weblogicWebservices;

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

public interface WeblogicWebservicesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWebservicesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwebservicesada2doctype");

   WeblogicWebservicesType getWeblogicWebservices();

   void setWeblogicWebservices(WeblogicWebservicesType var1);

   WeblogicWebservicesType addNewWeblogicWebservices();

   public static final class Factory {
      public static WeblogicWebservicesDocument newInstance() {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument newInstance(XmlOptions options) {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(File file) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebservicesDocument.type, options);
      }

      public static WeblogicWebservicesDocument parse(Node node) throws XmlException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebservicesDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicWebservicesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWebservicesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWebservicesDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebservicesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebservicesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebservicesDocument.type, options);
      }

      private Factory() {
      }
   }
}
