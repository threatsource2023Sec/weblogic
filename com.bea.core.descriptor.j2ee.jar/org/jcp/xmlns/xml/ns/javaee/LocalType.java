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

public interface LocalType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("localtype9632type");

   public static final class Factory {
      public static LocalType newInstance() {
         return (LocalType)XmlBeans.getContextTypeLoader().newInstance(LocalType.type, (XmlOptions)null);
      }

      public static LocalType newInstance(XmlOptions options) {
         return (LocalType)XmlBeans.getContextTypeLoader().newInstance(LocalType.type, options);
      }

      public static LocalType parse(java.lang.String xmlAsString) throws XmlException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalType.type, options);
      }

      public static LocalType parse(File file) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(file, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(file, LocalType.type, options);
      }

      public static LocalType parse(URL u) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(u, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(u, LocalType.type, options);
      }

      public static LocalType parse(InputStream is) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(is, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(is, LocalType.type, options);
      }

      public static LocalType parse(Reader r) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(r, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(r, LocalType.type, options);
      }

      public static LocalType parse(XMLStreamReader sr) throws XmlException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(sr, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(sr, LocalType.type, options);
      }

      public static LocalType parse(Node node) throws XmlException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(node, LocalType.type, (XmlOptions)null);
      }

      public static LocalType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(node, LocalType.type, options);
      }

      /** @deprecated */
      public static LocalType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(xis, LocalType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalType)XmlBeans.getContextTypeLoader().parse(xis, LocalType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalType.type, options);
      }

      private Factory() {
      }
   }
}
