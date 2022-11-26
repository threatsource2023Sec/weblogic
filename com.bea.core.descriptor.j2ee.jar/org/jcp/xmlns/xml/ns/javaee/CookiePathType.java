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

public interface CookiePathType extends NonEmptyStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CookiePathType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cookiepathtype0b8ftype");

   public static final class Factory {
      public static CookiePathType newInstance() {
         return (CookiePathType)XmlBeans.getContextTypeLoader().newInstance(CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType newInstance(XmlOptions options) {
         return (CookiePathType)XmlBeans.getContextTypeLoader().newInstance(CookiePathType.type, options);
      }

      public static CookiePathType parse(java.lang.String xmlAsString) throws XmlException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookiePathType.type, options);
      }

      public static CookiePathType parse(File file) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(file, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(file, CookiePathType.type, options);
      }

      public static CookiePathType parse(URL u) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(u, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(u, CookiePathType.type, options);
      }

      public static CookiePathType parse(InputStream is) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(is, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(is, CookiePathType.type, options);
      }

      public static CookiePathType parse(Reader r) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(r, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(r, CookiePathType.type, options);
      }

      public static CookiePathType parse(XMLStreamReader sr) throws XmlException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(sr, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(sr, CookiePathType.type, options);
      }

      public static CookiePathType parse(Node node) throws XmlException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(node, CookiePathType.type, (XmlOptions)null);
      }

      public static CookiePathType parse(Node node, XmlOptions options) throws XmlException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(node, CookiePathType.type, options);
      }

      /** @deprecated */
      public static CookiePathType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(xis, CookiePathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CookiePathType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CookiePathType)XmlBeans.getContextTypeLoader().parse(xis, CookiePathType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookiePathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookiePathType.type, options);
      }

      private Factory() {
      }
   }
}
