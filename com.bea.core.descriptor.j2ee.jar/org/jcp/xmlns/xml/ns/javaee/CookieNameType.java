package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface CookieNameType extends NonEmptyStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CookieNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cookienametype2e69type");

   public static final class Factory {
      public static CookieNameType newInstance() {
         return (CookieNameType)XmlBeans.getContextTypeLoader().newInstance(CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType newInstance(XmlOptions options) {
         return (CookieNameType)XmlBeans.getContextTypeLoader().newInstance(CookieNameType.type, options);
      }

      public static CookieNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieNameType.type, options);
      }

      public static CookieNameType parse(File file) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(file, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(file, CookieNameType.type, options);
      }

      public static CookieNameType parse(URL u) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(u, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(u, CookieNameType.type, options);
      }

      public static CookieNameType parse(InputStream is) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(is, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(is, CookieNameType.type, options);
      }

      public static CookieNameType parse(Reader r) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(r, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(r, CookieNameType.type, options);
      }

      public static CookieNameType parse(XMLStreamReader sr) throws XmlException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(sr, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(sr, CookieNameType.type, options);
      }

      public static CookieNameType parse(Node node) throws XmlException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(node, CookieNameType.type, (XmlOptions)null);
      }

      public static CookieNameType parse(Node node, XmlOptions options) throws XmlException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(node, CookieNameType.type, options);
      }

      /** @deprecated */
      public static CookieNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(xis, CookieNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CookieNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CookieNameType)XmlBeans.getContextTypeLoader().parse(xis, CookieNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieNameType.type, options);
      }

      private Factory() {
      }
   }
}
