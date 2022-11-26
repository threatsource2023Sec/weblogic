package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface WeblogicEjbJarDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicEjbJarDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicejbjar1254doctype");

   WeblogicEjbJarType getWeblogicEjbJar();

   void setWeblogicEjbJar(WeblogicEjbJarType var1);

   WeblogicEjbJarType addNewWeblogicEjbJar();

   public static final class Factory {
      public static WeblogicEjbJarDocument newInstance() {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument newInstance(XmlOptions options) {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(File file) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicEjbJarDocument.type, options);
      }

      public static WeblogicEjbJarDocument parse(Node node) throws XmlException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      public static WeblogicEjbJarDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicEjbJarDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicEjbJarDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicEjbJarDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicEjbJarDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicEjbJarDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicEjbJarDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicEjbJarDocument.type, options);
      }

      private Factory() {
      }
   }
}
