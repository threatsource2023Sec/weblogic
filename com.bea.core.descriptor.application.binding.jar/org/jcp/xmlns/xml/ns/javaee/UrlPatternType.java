package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface UrlPatternType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UrlPatternType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("urlpatterntype4bcbtype");

   public static final class Factory {
      public static UrlPatternType newInstance() {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().newInstance(UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType newInstance(XmlOptions options) {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().newInstance(UrlPatternType.type, options);
      }

      public static UrlPatternType parse(java.lang.String xmlAsString) throws XmlException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UrlPatternType.type, options);
      }

      public static UrlPatternType parse(File file) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(file, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(file, UrlPatternType.type, options);
      }

      public static UrlPatternType parse(URL u) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(u, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(u, UrlPatternType.type, options);
      }

      public static UrlPatternType parse(InputStream is) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(is, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(is, UrlPatternType.type, options);
      }

      public static UrlPatternType parse(Reader r) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(r, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(r, UrlPatternType.type, options);
      }

      public static UrlPatternType parse(XMLStreamReader sr) throws XmlException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(sr, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(sr, UrlPatternType.type, options);
      }

      public static UrlPatternType parse(Node node) throws XmlException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(node, UrlPatternType.type, (XmlOptions)null);
      }

      public static UrlPatternType parse(Node node, XmlOptions options) throws XmlException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(node, UrlPatternType.type, options);
      }

      /** @deprecated */
      public static UrlPatternType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(xis, UrlPatternType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UrlPatternType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UrlPatternType)XmlBeans.getContextTypeLoader().parse(xis, UrlPatternType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UrlPatternType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UrlPatternType.type, options);
      }

      private Factory() {
      }
   }
}
