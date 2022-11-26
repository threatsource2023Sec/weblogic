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

public interface MethodNameType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MethodNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("methodnametypeed66type");

   public static final class Factory {
      public static MethodNameType newInstance() {
         return (MethodNameType)XmlBeans.getContextTypeLoader().newInstance(MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType newInstance(XmlOptions options) {
         return (MethodNameType)XmlBeans.getContextTypeLoader().newInstance(MethodNameType.type, options);
      }

      public static MethodNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodNameType.type, options);
      }

      public static MethodNameType parse(File file) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(file, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(file, MethodNameType.type, options);
      }

      public static MethodNameType parse(URL u) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(u, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(u, MethodNameType.type, options);
      }

      public static MethodNameType parse(InputStream is) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(is, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(is, MethodNameType.type, options);
      }

      public static MethodNameType parse(Reader r) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(r, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(r, MethodNameType.type, options);
      }

      public static MethodNameType parse(XMLStreamReader sr) throws XmlException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(sr, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(sr, MethodNameType.type, options);
      }

      public static MethodNameType parse(Node node) throws XmlException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(node, MethodNameType.type, (XmlOptions)null);
      }

      public static MethodNameType parse(Node node, XmlOptions options) throws XmlException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(node, MethodNameType.type, options);
      }

      /** @deprecated */
      public static MethodNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(xis, MethodNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MethodNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MethodNameType)XmlBeans.getContextTypeLoader().parse(xis, MethodNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodNameType.type, options);
      }

      private Factory() {
      }
   }
}
