package com.sun.java.xml.ns.j2Ee;

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

public interface LocalHomeType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalHomeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("localhometype5e14type");

   public static final class Factory {
      public static LocalHomeType newInstance() {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().newInstance(LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType newInstance(XmlOptions options) {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().newInstance(LocalHomeType.type, options);
      }

      public static LocalHomeType parse(java.lang.String xmlAsString) throws XmlException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalHomeType.type, options);
      }

      public static LocalHomeType parse(File file) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(file, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(file, LocalHomeType.type, options);
      }

      public static LocalHomeType parse(URL u) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(u, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(u, LocalHomeType.type, options);
      }

      public static LocalHomeType parse(InputStream is) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(is, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(is, LocalHomeType.type, options);
      }

      public static LocalHomeType parse(Reader r) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(r, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(r, LocalHomeType.type, options);
      }

      public static LocalHomeType parse(XMLStreamReader sr) throws XmlException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(sr, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(sr, LocalHomeType.type, options);
      }

      public static LocalHomeType parse(Node node) throws XmlException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(node, LocalHomeType.type, (XmlOptions)null);
      }

      public static LocalHomeType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(node, LocalHomeType.type, options);
      }

      /** @deprecated */
      public static LocalHomeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(xis, LocalHomeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalHomeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalHomeType)XmlBeans.getContextTypeLoader().parse(xis, LocalHomeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalHomeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalHomeType.type, options);
      }

      private Factory() {
      }
   }
}
