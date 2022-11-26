package com.oracle.xmlns.weblogic.weblogicInterception;

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

public interface WeblogicInterceptionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicInterceptionDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicinterception566edoctype");

   InterceptionType getWeblogicInterception();

   void setWeblogicInterception(InterceptionType var1);

   InterceptionType addNewWeblogicInterception();

   public static final class Factory {
      public static WeblogicInterceptionDocument newInstance() {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument newInstance(XmlOptions options) {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(File file) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicInterceptionDocument.type, options);
      }

      public static WeblogicInterceptionDocument parse(Node node) throws XmlException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      public static WeblogicInterceptionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicInterceptionDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicInterceptionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicInterceptionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicInterceptionDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicInterceptionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicInterceptionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicInterceptionDocument.type, options);
      }

      private Factory() {
      }
   }
}
