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

public interface WebFragmentDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebFragmentDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webfragment824cdoctype");

   WebFragmentType getWebFragment();

   void setWebFragment(WebFragmentType var1);

   WebFragmentType addNewWebFragment();

   public static final class Factory {
      public static WebFragmentDocument newInstance() {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().newInstance(WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument newInstance(XmlOptions options) {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().newInstance(WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(File file) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(file, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(file, WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(URL u) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(u, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(u, WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(InputStream is) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(is, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(is, WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(Reader r) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(r, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(r, WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(XMLStreamReader sr) throws XmlException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(sr, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(sr, WebFragmentDocument.type, options);
      }

      public static WebFragmentDocument parse(Node node) throws XmlException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(node, WebFragmentDocument.type, (XmlOptions)null);
      }

      public static WebFragmentDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(node, WebFragmentDocument.type, options);
      }

      /** @deprecated */
      public static WebFragmentDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(xis, WebFragmentDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebFragmentDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebFragmentDocument)XmlBeans.getContextTypeLoader().parse(xis, WebFragmentDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebFragmentDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebFragmentDocument.type, options);
      }

      private Factory() {
      }
   }
}
