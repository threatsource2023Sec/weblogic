package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface HandlerChainsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HandlerChainsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("handlerchainstype79e2type");

   HandlerChainType[] getHandlerChainArray();

   HandlerChainType getHandlerChainArray(int var1);

   int sizeOfHandlerChainArray();

   void setHandlerChainArray(HandlerChainType[] var1);

   void setHandlerChainArray(int var1, HandlerChainType var2);

   HandlerChainType insertNewHandlerChain(int var1);

   HandlerChainType addNewHandlerChain();

   void removeHandlerChain(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static HandlerChainsType newInstance() {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().newInstance(HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType newInstance(XmlOptions options) {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().newInstance(HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(java.lang.String xmlAsString) throws XmlException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(File file) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(file, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(file, HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(URL u) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(u, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(u, HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(InputStream is) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(is, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(is, HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(Reader r) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(r, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(r, HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(XMLStreamReader sr) throws XmlException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(sr, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(sr, HandlerChainsType.type, options);
      }

      public static HandlerChainsType parse(Node node) throws XmlException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(node, HandlerChainsType.type, (XmlOptions)null);
      }

      public static HandlerChainsType parse(Node node, XmlOptions options) throws XmlException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(node, HandlerChainsType.type, options);
      }

      /** @deprecated */
      public static HandlerChainsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(xis, HandlerChainsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HandlerChainsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HandlerChainsType)XmlBeans.getContextTypeLoader().parse(xis, HandlerChainsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerChainsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerChainsType.type, options);
      }

      private Factory() {
      }
   }
}
