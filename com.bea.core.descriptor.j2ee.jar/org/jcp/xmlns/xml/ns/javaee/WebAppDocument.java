package org.jcp.xmlns.xml.ns.javaee;

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

public interface WebAppDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebAppDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webapp28bfdoctype");

   WebAppType getWebApp();

   void setWebApp(WebAppType var1);

   WebAppType addNewWebApp();

   public static final class Factory {
      public static WebAppDocument newInstance() {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().newInstance(WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument newInstance(XmlOptions options) {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().newInstance(WebAppDocument.type, options);
      }

      public static WebAppDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebAppDocument.type, options);
      }

      public static WebAppDocument parse(File file) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(file, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(file, WebAppDocument.type, options);
      }

      public static WebAppDocument parse(URL u) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(u, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(u, WebAppDocument.type, options);
      }

      public static WebAppDocument parse(InputStream is) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(is, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(is, WebAppDocument.type, options);
      }

      public static WebAppDocument parse(Reader r) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(r, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(r, WebAppDocument.type, options);
      }

      public static WebAppDocument parse(XMLStreamReader sr) throws XmlException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(sr, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(sr, WebAppDocument.type, options);
      }

      public static WebAppDocument parse(Node node) throws XmlException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(node, WebAppDocument.type, (XmlOptions)null);
      }

      public static WebAppDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(node, WebAppDocument.type, options);
      }

      /** @deprecated */
      public static WebAppDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(xis, WebAppDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebAppDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebAppDocument)XmlBeans.getContextTypeLoader().parse(xis, WebAppDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebAppDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebAppDocument.type, options);
      }

      private Factory() {
      }
   }
}
