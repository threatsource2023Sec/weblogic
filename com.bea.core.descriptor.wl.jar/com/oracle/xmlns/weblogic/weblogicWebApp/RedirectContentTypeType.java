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

public interface RedirectContentTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RedirectContentTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("redirectcontenttypetype3320type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RedirectContentTypeType newInstance() {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().newInstance(RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType newInstance(XmlOptions options) {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().newInstance(RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(String xmlAsString) throws XmlException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(File file) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(file, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(file, RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(URL u) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(u, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(u, RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(InputStream is) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(is, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(is, RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(Reader r) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(r, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(r, RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(XMLStreamReader sr) throws XmlException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(sr, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(sr, RedirectContentTypeType.type, options);
      }

      public static RedirectContentTypeType parse(Node node) throws XmlException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(node, RedirectContentTypeType.type, (XmlOptions)null);
      }

      public static RedirectContentTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(node, RedirectContentTypeType.type, options);
      }

      /** @deprecated */
      public static RedirectContentTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(xis, RedirectContentTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RedirectContentTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RedirectContentTypeType)XmlBeans.getContextTypeLoader().parse(xis, RedirectContentTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RedirectContentTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RedirectContentTypeType.type, options);
      }

      private Factory() {
      }
   }
}
