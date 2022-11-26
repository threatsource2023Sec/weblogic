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

public interface ServletLinkType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServletLinkType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("servletlinktyped32ftype");

   public static final class Factory {
      public static ServletLinkType newInstance() {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().newInstance(ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType newInstance(XmlOptions options) {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().newInstance(ServletLinkType.type, options);
      }

      public static ServletLinkType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletLinkType.type, options);
      }

      public static ServletLinkType parse(File file) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(file, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(file, ServletLinkType.type, options);
      }

      public static ServletLinkType parse(URL u) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(u, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(u, ServletLinkType.type, options);
      }

      public static ServletLinkType parse(InputStream is) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(is, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(is, ServletLinkType.type, options);
      }

      public static ServletLinkType parse(Reader r) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(r, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(r, ServletLinkType.type, options);
      }

      public static ServletLinkType parse(XMLStreamReader sr) throws XmlException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(sr, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(sr, ServletLinkType.type, options);
      }

      public static ServletLinkType parse(Node node) throws XmlException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(node, ServletLinkType.type, (XmlOptions)null);
      }

      public static ServletLinkType parse(Node node, XmlOptions options) throws XmlException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(node, ServletLinkType.type, options);
      }

      /** @deprecated */
      public static ServletLinkType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(xis, ServletLinkType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServletLinkType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServletLinkType)XmlBeans.getContextTypeLoader().parse(xis, ServletLinkType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletLinkType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletLinkType.type, options);
      }

      private Factory() {
      }
   }
}
