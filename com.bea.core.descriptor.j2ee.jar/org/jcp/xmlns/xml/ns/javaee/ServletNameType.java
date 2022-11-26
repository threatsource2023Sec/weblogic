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

public interface ServletNameType extends NonEmptyStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServletNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("servletnametypeb198type");

   public static final class Factory {
      public static ServletNameType newInstance() {
         return (ServletNameType)XmlBeans.getContextTypeLoader().newInstance(ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType newInstance(XmlOptions options) {
         return (ServletNameType)XmlBeans.getContextTypeLoader().newInstance(ServletNameType.type, options);
      }

      public static ServletNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletNameType.type, options);
      }

      public static ServletNameType parse(File file) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(file, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(file, ServletNameType.type, options);
      }

      public static ServletNameType parse(URL u) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(u, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(u, ServletNameType.type, options);
      }

      public static ServletNameType parse(InputStream is) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(is, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(is, ServletNameType.type, options);
      }

      public static ServletNameType parse(Reader r) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(r, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(r, ServletNameType.type, options);
      }

      public static ServletNameType parse(XMLStreamReader sr) throws XmlException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(sr, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(sr, ServletNameType.type, options);
      }

      public static ServletNameType parse(Node node) throws XmlException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(node, ServletNameType.type, (XmlOptions)null);
      }

      public static ServletNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(node, ServletNameType.type, options);
      }

      /** @deprecated */
      public static ServletNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(xis, ServletNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServletNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServletNameType)XmlBeans.getContextTypeLoader().parse(xis, ServletNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletNameType.type, options);
      }

      private Factory() {
      }
   }
}
