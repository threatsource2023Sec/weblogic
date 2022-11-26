package com.sun.java.xml.ns.javaee;

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

public interface CookieDomainType extends NonEmptyStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CookieDomainType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cookiedomaintype4588type");

   public static final class Factory {
      public static CookieDomainType newInstance() {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().newInstance(CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType newInstance(XmlOptions options) {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().newInstance(CookieDomainType.type, options);
      }

      public static CookieDomainType parse(java.lang.String xmlAsString) throws XmlException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieDomainType.type, options);
      }

      public static CookieDomainType parse(File file) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(file, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(file, CookieDomainType.type, options);
      }

      public static CookieDomainType parse(URL u) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(u, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(u, CookieDomainType.type, options);
      }

      public static CookieDomainType parse(InputStream is) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(is, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(is, CookieDomainType.type, options);
      }

      public static CookieDomainType parse(Reader r) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(r, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(r, CookieDomainType.type, options);
      }

      public static CookieDomainType parse(XMLStreamReader sr) throws XmlException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(sr, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(sr, CookieDomainType.type, options);
      }

      public static CookieDomainType parse(Node node) throws XmlException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(node, CookieDomainType.type, (XmlOptions)null);
      }

      public static CookieDomainType parse(Node node, XmlOptions options) throws XmlException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(node, CookieDomainType.type, options);
      }

      /** @deprecated */
      public static CookieDomainType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(xis, CookieDomainType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CookieDomainType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CookieDomainType)XmlBeans.getContextTypeLoader().parse(xis, CookieDomainType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieDomainType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieDomainType.type, options);
      }

      private Factory() {
      }
   }
}
