package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface WeblogicClientJmsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicClientJmsDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicclientjms6330doctype");

   ClientSafType getWeblogicClientJms();

   void setWeblogicClientJms(ClientSafType var1);

   ClientSafType addNewWeblogicClientJms();

   public static final class Factory {
      public static WeblogicClientJmsDocument newInstance() {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument newInstance(XmlOptions options) {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(File file) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicClientJmsDocument.type, options);
      }

      public static WeblogicClientJmsDocument parse(Node node) throws XmlException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicClientJmsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicClientJmsDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicClientJmsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicClientJmsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicClientJmsDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicClientJmsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicClientJmsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicClientJmsDocument.type, options);
      }

      private Factory() {
      }
   }
}
