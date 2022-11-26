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

public interface JndiNameType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JndiNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("jndinametype2f8etype");

   public static final class Factory {
      public static JndiNameType newInstance() {
         return (JndiNameType)XmlBeans.getContextTypeLoader().newInstance(JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType newInstance(XmlOptions options) {
         return (JndiNameType)XmlBeans.getContextTypeLoader().newInstance(JndiNameType.type, options);
      }

      public static JndiNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JndiNameType.type, options);
      }

      public static JndiNameType parse(File file) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(file, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(file, JndiNameType.type, options);
      }

      public static JndiNameType parse(URL u) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(u, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(u, JndiNameType.type, options);
      }

      public static JndiNameType parse(InputStream is) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(is, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(is, JndiNameType.type, options);
      }

      public static JndiNameType parse(Reader r) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(r, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(r, JndiNameType.type, options);
      }

      public static JndiNameType parse(XMLStreamReader sr) throws XmlException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(sr, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(sr, JndiNameType.type, options);
      }

      public static JndiNameType parse(Node node) throws XmlException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(node, JndiNameType.type, (XmlOptions)null);
      }

      public static JndiNameType parse(Node node, XmlOptions options) throws XmlException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(node, JndiNameType.type, options);
      }

      /** @deprecated */
      public static JndiNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(xis, JndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JndiNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JndiNameType)XmlBeans.getContextTypeLoader().parse(xis, JndiNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JndiNameType.type, options);
      }

      private Factory() {
      }
   }
}
