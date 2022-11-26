package com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.ServiceRefHandlerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicWseeClientHandlerChainType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWseeClientHandlerChainType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwseeclienthandlerchaintype532etype");

   ServiceRefHandlerType[] getHandlerArray();

   ServiceRefHandlerType getHandlerArray(int var1);

   int sizeOfHandlerArray();

   void setHandlerArray(ServiceRefHandlerType[] var1);

   void setHandlerArray(int var1, ServiceRefHandlerType var2);

   ServiceRefHandlerType insertNewHandler(int var1);

   ServiceRefHandlerType addNewHandler();

   void removeHandler(int var1);

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicWseeClientHandlerChainType newInstance() {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType newInstance(XmlOptions options) {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(String xmlAsString) throws XmlException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(File file) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(URL u) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(Reader r) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeClientHandlerChainType.type, options);
      }

      public static WeblogicWseeClientHandlerChainType parse(Node node) throws XmlException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      public static WeblogicWseeClientHandlerChainType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeClientHandlerChainType.type, options);
      }

      /** @deprecated */
      public static WeblogicWseeClientHandlerChainType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWseeClientHandlerChainType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWseeClientHandlerChainType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeClientHandlerChainType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeClientHandlerChainType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeClientHandlerChainType.type, options);
      }

      private Factory() {
      }
   }
}
