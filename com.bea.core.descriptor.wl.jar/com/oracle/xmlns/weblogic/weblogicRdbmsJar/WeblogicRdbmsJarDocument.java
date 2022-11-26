package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface WeblogicRdbmsJarDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicRdbmsJarDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicrdbmsjar9686doctype");

   WeblogicRdbmsJarType getWeblogicRdbmsJar();

   void setWeblogicRdbmsJar(WeblogicRdbmsJarType var1);

   WeblogicRdbmsJarType addNewWeblogicRdbmsJar();

   public static final class Factory {
      public static WeblogicRdbmsJarDocument newInstance() {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument newInstance(XmlOptions options) {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(File file) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsJarDocument.type, options);
      }

      public static WeblogicRdbmsJarDocument parse(Node node) throws XmlException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsJarDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicRdbmsJarDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicRdbmsJarDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsJarDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsJarDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsJarDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsJarDocument.type, options);
      }

      private Factory() {
      }
   }
}
