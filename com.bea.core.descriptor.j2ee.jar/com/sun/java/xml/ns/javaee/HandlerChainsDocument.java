package com.sun.java.xml.ns.javaee;

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

public interface HandlerChainsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HandlerChainsDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("handlerchains0a18doctype");

   HandlerChainsType getHandlerChains();

   void setHandlerChains(HandlerChainsType var1);

   HandlerChainsType addNewHandlerChains();

   public static final class Factory {
      public static HandlerChainsDocument newInstance() {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().newInstance(HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument newInstance(XmlOptions options) {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().newInstance(HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(File file) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(file, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(file, HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(URL u) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(u, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(u, HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(InputStream is) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(is, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(is, HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(Reader r) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(r, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(r, HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(XMLStreamReader sr) throws XmlException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(sr, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(sr, HandlerChainsDocument.type, options);
      }

      public static HandlerChainsDocument parse(Node node) throws XmlException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(node, HandlerChainsDocument.type, (XmlOptions)null);
      }

      public static HandlerChainsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(node, HandlerChainsDocument.type, options);
      }

      /** @deprecated */
      public static HandlerChainsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(xis, HandlerChainsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HandlerChainsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HandlerChainsDocument)XmlBeans.getContextTypeLoader().parse(xis, HandlerChainsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerChainsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerChainsDocument.type, options);
      }

      private Factory() {
      }
   }
}
