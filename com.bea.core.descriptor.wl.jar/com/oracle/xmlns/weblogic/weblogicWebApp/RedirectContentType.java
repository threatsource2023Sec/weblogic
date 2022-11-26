package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface RedirectContentType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RedirectContentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("redirectcontenttype193btype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RedirectContentType newInstance() {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().newInstance(RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType newInstance(XmlOptions options) {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().newInstance(RedirectContentType.type, options);
      }

      public static RedirectContentType parse(String xmlAsString) throws XmlException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RedirectContentType.type, options);
      }

      public static RedirectContentType parse(File file) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(file, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(file, RedirectContentType.type, options);
      }

      public static RedirectContentType parse(URL u) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(u, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(u, RedirectContentType.type, options);
      }

      public static RedirectContentType parse(InputStream is) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(is, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(is, RedirectContentType.type, options);
      }

      public static RedirectContentType parse(Reader r) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(r, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(r, RedirectContentType.type, options);
      }

      public static RedirectContentType parse(XMLStreamReader sr) throws XmlException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(sr, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(sr, RedirectContentType.type, options);
      }

      public static RedirectContentType parse(Node node) throws XmlException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(node, RedirectContentType.type, (XmlOptions)null);
      }

      public static RedirectContentType parse(Node node, XmlOptions options) throws XmlException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(node, RedirectContentType.type, options);
      }

      /** @deprecated */
      public static RedirectContentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(xis, RedirectContentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RedirectContentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RedirectContentType)XmlBeans.getContextTypeLoader().parse(xis, RedirectContentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RedirectContentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RedirectContentType.type, options);
      }

      private Factory() {
      }
   }
}
