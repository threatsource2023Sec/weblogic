package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface JdbcListenerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdbcListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jdbclistenertyped2a6type");

   public static final class Factory {
      public static JdbcListenerType newInstance() {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().newInstance(JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType newInstance(XmlOptions options) {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().newInstance(JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(String xmlAsString) throws XmlException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(File file) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(file, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(file, JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(URL u) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(u, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(u, JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(InputStream is) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(is, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(is, JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(Reader r) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(r, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(r, JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(XMLStreamReader sr) throws XmlException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(sr, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(sr, JdbcListenerType.type, options);
      }

      public static JdbcListenerType parse(Node node) throws XmlException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(node, JdbcListenerType.type, (XmlOptions)null);
      }

      public static JdbcListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(node, JdbcListenerType.type, options);
      }

      /** @deprecated */
      public static JdbcListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(xis, JdbcListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdbcListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdbcListenerType)XmlBeans.getContextTypeLoader().parse(xis, JdbcListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcListenerType.type, options);
      }

      private Factory() {
      }
   }
}
