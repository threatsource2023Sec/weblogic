package com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain;

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

public interface WeblogicWseeClientHandlerChainDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWseeClientHandlerChainDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwseeclienthandlerchain1ce4doctype");

   WeblogicWseeClientHandlerChainType getWeblogicWseeClientHandlerChain();

   void setWeblogicWseeClientHandlerChain(WeblogicWseeClientHandlerChainType var1);

   WeblogicWseeClientHandlerChainType addNewWeblogicWseeClientHandlerChain();

   public static final class Factory {
      public static WeblogicWseeClientHandlerChainDocument newInstance() {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument newInstance(XmlOptions options) {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(File file) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(Node node) throws XmlException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicWseeClientHandlerChainDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWseeClientHandlerChainDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWseeClientHandlerChainDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeClientHandlerChainDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeClientHandlerChainDocument.type, options);
      }

      private Factory() {
      }
   }
}
