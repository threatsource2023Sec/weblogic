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

public interface WeblogicJmsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicJmsDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicjms46dedoctype");

   JmsType getWeblogicJms();

   void setWeblogicJms(JmsType var1);

   JmsType addNewWeblogicJms();

   public static final class Factory {
      public static WeblogicJmsDocument newInstance() {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument newInstance(XmlOptions options) {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(File file) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicJmsDocument.type, options);
      }

      public static WeblogicJmsDocument parse(Node node) throws XmlException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      public static WeblogicJmsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicJmsDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicJmsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicJmsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicJmsDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicJmsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicJmsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicJmsDocument.type, options);
      }

      private Factory() {
      }
   }
}
