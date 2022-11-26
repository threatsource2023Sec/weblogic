package com.oracle.xmlns.weblogic.weblogicExtension;

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

public interface WeblogicExtensionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicExtensionDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("weblogicextensionebbcdoctype");

   WeblogicExtensionType getWeblogicExtension();

   void setWeblogicExtension(WeblogicExtensionType var1);

   WeblogicExtensionType addNewWeblogicExtension();

   public static final class Factory {
      public static WeblogicExtensionDocument newInstance() {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument newInstance(XmlOptions options) {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(File file) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicExtensionDocument.type, options);
      }

      public static WeblogicExtensionDocument parse(Node node) throws XmlException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicExtensionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicExtensionDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicExtensionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicExtensionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicExtensionDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicExtensionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicExtensionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicExtensionDocument.type, options);
      }

      private Factory() {
      }
   }
}
