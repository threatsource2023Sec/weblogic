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

public interface PathType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PathType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("pathtyped0f8type");

   public static final class Factory {
      public static PathType newInstance() {
         return (PathType)XmlBeans.getContextTypeLoader().newInstance(PathType.type, (XmlOptions)null);
      }

      public static PathType newInstance(XmlOptions options) {
         return (PathType)XmlBeans.getContextTypeLoader().newInstance(PathType.type, options);
      }

      public static PathType parse(java.lang.String xmlAsString) throws XmlException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PathType.type, options);
      }

      public static PathType parse(File file) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(file, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(file, PathType.type, options);
      }

      public static PathType parse(URL u) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(u, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(u, PathType.type, options);
      }

      public static PathType parse(InputStream is) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(is, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(is, PathType.type, options);
      }

      public static PathType parse(Reader r) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(r, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(r, PathType.type, options);
      }

      public static PathType parse(XMLStreamReader sr) throws XmlException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(sr, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(sr, PathType.type, options);
      }

      public static PathType parse(Node node) throws XmlException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(node, PathType.type, (XmlOptions)null);
      }

      public static PathType parse(Node node, XmlOptions options) throws XmlException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(node, PathType.type, options);
      }

      /** @deprecated */
      public static PathType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(xis, PathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PathType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PathType)XmlBeans.getContextTypeLoader().parse(xis, PathType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PathType.type, options);
      }

      private Factory() {
      }
   }
}
