package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface WeblogicApplicationDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicApplicationDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("weblogicapplication065edoctype");

   WeblogicApplicationType getWeblogicApplication();

   void setWeblogicApplication(WeblogicApplicationType var1);

   WeblogicApplicationType addNewWeblogicApplication();

   public static final class Factory {
      public static WeblogicApplicationDocument newInstance() {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument newInstance(XmlOptions options) {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(File file) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationDocument.type, options);
      }

      public static WeblogicApplicationDocument parse(Node node) throws XmlException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      public static WeblogicApplicationDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicApplicationDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicApplicationDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicApplicationDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationDocument.type, options);
      }

      private Factory() {
      }
   }
}
