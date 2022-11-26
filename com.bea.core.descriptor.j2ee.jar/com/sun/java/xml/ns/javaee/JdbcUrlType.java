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

public interface JdbcUrlType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdbcUrlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("jdbcurltypef88atype");

   public static final class Factory {
      public static JdbcUrlType newInstance() {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().newInstance(JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType newInstance(XmlOptions options) {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().newInstance(JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(java.lang.String xmlAsString) throws XmlException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(File file) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(file, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(file, JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(URL u) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(u, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(u, JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(InputStream is) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(is, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(is, JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(Reader r) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(r, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(r, JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(XMLStreamReader sr) throws XmlException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(sr, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(sr, JdbcUrlType.type, options);
      }

      public static JdbcUrlType parse(Node node) throws XmlException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(node, JdbcUrlType.type, (XmlOptions)null);
      }

      public static JdbcUrlType parse(Node node, XmlOptions options) throws XmlException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(node, JdbcUrlType.type, options);
      }

      /** @deprecated */
      public static JdbcUrlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(xis, JdbcUrlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdbcUrlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdbcUrlType)XmlBeans.getContextTypeLoader().parse(xis, JdbcUrlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcUrlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcUrlType.type, options);
      }

      private Factory() {
      }
   }
}
