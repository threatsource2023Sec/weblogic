package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicQlType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicQlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicqltype6a7btype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicQlType newInstance() {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().newInstance(WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType newInstance(XmlOptions options) {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().newInstance(WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(String xmlAsString) throws XmlException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(File file) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(file, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(file, WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(URL u) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(u, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(u, WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(is, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(is, WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(Reader r) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(r, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(r, WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicQlType.type, options);
      }

      public static WeblogicQlType parse(Node node) throws XmlException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(node, WeblogicQlType.type, (XmlOptions)null);
      }

      public static WeblogicQlType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(node, WeblogicQlType.type, options);
      }

      /** @deprecated */
      public static WeblogicQlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicQlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicQlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicQlType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicQlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicQlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicQlType.type, options);
      }

      private Factory() {
      }
   }
}
